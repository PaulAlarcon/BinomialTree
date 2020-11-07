/*
Program designed to build a binomial tree and calculate the fair value and fugit of an option
It also implements the bisection algorithm to find the implied volatility of a given option.
Colaborators :
- Paul Alarcon
- Mark Xia
*/

import static java.lang.Math.*;

final class Library {

    Output binom(final Derivative deriv, final MarketData mkt, int n) {

        if (n < 1) {
            System.out.println("Invalid number n.");
            return null;
        }

        double deltaT = (deriv.T - mkt.t0) / n;
        double u = (exp(mkt.sigma * sqrt(deltaT)));
        double d = 1 / u;
        double dk = exp(mkt.r * deltaT);
        double InvDk = 1 / dk;
        double p = (dk - d) / (u - d);
        double q = 1 - p;
        double fugitAtN = deriv.T - mkt.t0;
        deriv.valulableParameters(deltaT, u, d, dk, InvDk, p, q, fugitAtN);

        Node tree[][] = new Node[n + 1][n + 1];
        Node root = new Node(mkt.S, 0);
        tree[0][0] = root;

        for (int i = 1; i < n + 1; i++) {

            if (i == 1) {
                tree[i][0] = new Node(tree[0][0].stockPrice * d, 1); // sets
                tree[i][i] = new Node(tree[0][0].stockPrice * u, 1);
            }

            tree[i][0] = new Node(tree[i - 1][0].stockPrice * d, i); // down
            tree[i][i] = new Node(tree[i - 1][i - 1].stockPrice * u, i); // up

            for (int j = 1; j < i; j++) {
                tree[i][j] = new Node(tree[i - 2][j - 1].stockPrice, i);
            }
        }

        for (int i = 0; i < tree.length - 1; i++) {
            for (int j = 0; j < i + 1; j++) {
                tree[i][j].setChildren(tree[i + 1][j], tree[i + 1][j + 1]);
            }
        }

        for (int j = tree.length - 1; j >= 0; j--) {
            for (int i = 0; i <= j; i++) {
                if (j == tree.length - 1) {
                    deriv.terminalCondition(tree[j][i]);
                } else {
                    deriv.valuationTest(tree[j][i]);
                }
            }
        }

        return new Output(root.fairValue, root.fugit);

    }

    int impvol(final Derivative deriv, final MarketData mkt, int n, int max_iter, double tol, Output out) {

        if(mkt.S < 0){
            System.out.println("Wrong security price");
            return 1;
        }

        if(mkt.Price < 0.99*(mkt.S - deriv.Strike*exp(-deriv.T*mkt.r))) {
            System.out.println(0.99*(mkt.S - deriv.Strike*exp(-deriv.T*mkt.r)));
            out.impvol = 0.0;
            out.num_iter = 0;
            return 1;
        }

        final double highValue = exp(10);

         double sigma_low = 0.01; //1 %
         double sigma_high = 2.0; //200 %

        MarketData tempMkt = mkt;

        mkt.setSigma(sigma_high);
        double price = binom(deriv, tempMkt, n).FV;

        while(price < mkt.Price){
            sigma_high = 2.0 * sigma_high;
            mkt.setSigma(sigma_high);
            price = binom(deriv,  tempMkt, n ).FV;
            if(sigma_high > highValue) return 1;
        };

        for(int i = 1; i <= max_iter; i++){
            double sigma  = (sigma_low + sigma_high)*0.5;
            tempMkt.setSigma(sigma);
            price = binom(deriv, tempMkt, n).FV;

            double test  = (price - mkt.Price);
            if(abs(test) < tol){
                out.setImpVol(sigma);
                out.num_iter = i;
                return 0;
            }
            if(test < 0.0) {
                sigma_low = sigma;
            }
            else{
                sigma_high = sigma;
            }
        }

        return 1;
    }

}