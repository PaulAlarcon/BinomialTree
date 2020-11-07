public class BermudanOption extends Derivative {
    public double window_begin;
    public double window_end;
    TYPE type;

    enum TYPE {PUT, CALL};

    public BermudanOption(double T, double Strike, double window_begin, double window_end, TYPE type) {
        if(window_begin > window_end){
            System.out.println("Wrong window data, the window begins after the it ends");
        }
        this.T = T;
        this.Strike = Strike;
        this.window_begin = window_begin;
        this.window_end = window_end;
        this.type = type;
    }

    @Override
    public void terminalCondition(Node n) {
        if(type == BermudanOption.TYPE.CALL){
            n.fairValue = max( n.stockPrice - Strike , 0) ;
        }
        else if(type == BermudanOption.TYPE.PUT){
            n.fairValue = max( Strike - n.stockPrice , 0) ;
        }
        n.fugit = fugitAtN;
    }

    @Override
    public void valuationTest(Node n) {

        double timeatNode = n.n * deltaT;
        boolean open_window  = (timeatNode >= window_begin  && timeatNode <= window_end) ? true : false;

        if(open_window == false){
            n.fairValue = invDk*(p*n.rChild.fairValue + q*n.lChild.fairValue);
            n.fugit = fugitAtN;
        }
        else if(open_window == true){
            if(type == BermudanOption.TYPE.PUT){
                n.intrinsicValue = max(Strike - n.stockPrice, 0);
            }
            else if(type == BermudanOption.TYPE.CALL){
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
