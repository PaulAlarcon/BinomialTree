public class Main {
    //Commented for compiling purposes!!
    public static void main(String args[]){
        Library l = new Library();
        MarketData mD = new MarketData(100, 0.1, 50, 0); // for impliedvol we are not using sigma at all

        VanillaOption europeanPut = new VanillaOption(0.3, 100, VanillaOption.TYPE.PUT, VanillaOption.VERSION.EUROPE);
        VanillaOption americanPut = new VanillaOption(0.3, 100, VanillaOption.TYPE.PUT, VanillaOption.VERSION.AMERICAN);
        VanillaOption europeanCall = new VanillaOption(0.3, 100, VanillaOption.TYPE.CALL, VanillaOption.VERSION.EUROPE);
        VanillaOption americanCall = new VanillaOption(0.3, 100, VanillaOption.TYPE.CALL, VanillaOption.VERSION.AMERICAN);

        BermudanOption bermudanCall = new BermudanOption(0.3, 100, 0, 0, BermudanOption.TYPE.CALL);
        BermudanOption bermudanPut = new BermudanOption(0.3, 100, 0, 0, BermudanOption.TYPE.PUT);

        MarketData mDvol = new MarketData(100, 0.1, 50, 0);
        mDvol.setPrice(10.45);
        Output out = new Output();
        l.impvol(americanPut, mDvol, 3, 100, 0.000001, out );
    }
}
