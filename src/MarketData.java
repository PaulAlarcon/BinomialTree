class MarketData {
        public double Price;          // market price of security
        public double S;              // stock price
        public double r;              // interest rate
        public double sigma;          // volatility
        public double t0;             // current time

        public MarketData( double S, double r, double sigma, double t0) {
                this.S = S;
                this.r = r;
                this.sigma = sigma/100 ;
                this.t0 = t0;
        }

        public void setSigma(double sigma) {
                this.sigma = sigma;
        }

        public void setPrice(double price) {
                Price = price;
        }
}
