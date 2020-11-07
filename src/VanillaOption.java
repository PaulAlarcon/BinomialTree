class VanillaOption extends Derivative {

    enum TYPE {PUT, CALL};
    enum VERSION{AMERICAN, EUROPE};

    public TYPE type;
    public VERSION version;

    public VanillaOption(double T, double Strike, TYPE type, VERSION version) {
        this.T = T;
        this.type = type;
        this.version = version;
        this.Strike = Strike;
    }

    @Override
    public void terminalCondition(Node n) {
        if(type == TYPE.CALL){
            n.fairValue = max( n.stockPrice - Strike , 0) ;
        }
        else if(type == TYPE.PUT){
            n.fairValue = max( Strike - n.stockPrice , 0) ;
        }
        n.fugit = fugitAtN;
    }

    @Override
    public void valuationTest(Node n) {
        if(version == VERSION.EUROPE){
            n.fairValue = invDk*(p*n.rChild.fairValue + q*n.lChild.fairValue);
            n.fugit = fugitAtN;
        }
        else if(version == VERSION.AMERICAN){
            if(type == TYPE.PUT){
                n.intrinsicValue = max(Strike - n.stockPrice, 0);
            }
            else if(type == TYPE.CALL){
                n.intrinsicValue = max(n.stockPrice - Strike, 0);
            }
            n.fairValue = invDk*(p*n.rChild.fairValue + q*n.lChild.fairValue);

            double tempFugit = 0;
            if(n.intrinsicValue > n.fairValue){
                n.EEX = true;
                n.fairValue = n.intrinsicValue;
                if(n.n != 0) {
                    tempFugit = n.n * deltaT;
                }
                else {
                    tempFugit = 0;
                }
            }
            else{
                tempFugit = (p*n.rChild.fugit + q*n.lChild.fugit);
            }
            n.fugit = tempFugit;
        }
    }

}

