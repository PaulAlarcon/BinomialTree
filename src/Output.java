final class Output {
    public double FV;      // fair value
    public double fugit;   // fugit
    public double impvol;  // implied volatility
    public int num_iter;   // number of iterations to compute implied volatility
    public boolean binom;

    public Output() {
    }

    public Output(double FV, double fugit) {
        binom = true;
        this.FV = Math.round(FV * 100.0)/100.0;
        this.fugit = Math.round(fugit * 100.0)/100.0;
    }

    //Output for impvol
    public Output(double impvol, int num_iter) {
        this.impvol =  Math.round(impvol * 100.0);
        this.num_iter = num_iter;
    }

    public void setImpVol(double impvol){
        this.impvol =  impvol * 100.0;
    }

    public String toString(){
        if(binom){
            return "Output Value for binom. Fair Value = " + FV + " ; Fugit = " + fugit;
        }
        else return "Output Value for impVal. Implied Volatility = " + impvol + " ; number of iterations = " + num_iter;
    }
}
