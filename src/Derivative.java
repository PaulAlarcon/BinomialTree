abstract class Derivative {
    public double T;
    public double Strike;
    public double deltaT;
    public double u;
    public double d;
    public double dk;
    public double invDk;
    public double p;
    public double q;
    public double fugitAtN;

    public abstract void terminalCondition(Node n);// virtual function
    public abstract void valuationTest(Node n); // virtual function
    public void valulableParameters(double deltaT,  double u, double d,  double dk, double invDk, double p, double q, double fugitAtN){
        this.deltaT = deltaT;
        this.u = u;
        this.d = d;
        this.dk = dk;
        this.invDk = invDk;
        this.p = p;
        this.q = q;
        this.fugitAtN = fugitAtN;
    }
    public double max(double n1, double n2){ return (n1 > n2) ? n1 : n2; }
}
