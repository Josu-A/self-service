public class Langilea extends Thread {
    private SelfService selfService;
    private Pantaila pantaila;
    private int langileZenbakia;

    public Langilea(int langileZenbakia, SelfService selfService, Pantaila pantaila) {
        this.langileZenbakia = langileZenbakia;
        this.selfService = selfService;
        this.pantaila = pantaila;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // LANGILEA = (begiratu[pk:PR] -> if (pk < PK)
                SelfServiceApp.itxaron();
                int unekoPlaterKopurua = this.selfService.begiratu(this.langileZenbakia);

                SelfServiceApp.itxaron();
                //     then (random[r:1..(PK-pk)] -> eskatu -> ekarri[r] -> LANGILEA)
                if (unekoPlaterKopurua < SelfServiceApp.PLATERAK_TOTAL) {
                    int eskatzekoPlaterKopurua = eskatzekoPlaterKopuruaLortu(unekoPlaterKopurua);
                    this.selfService.eskatu(this.langileZenbakia, eskatzekoPlaterKopurua);

                    SelfServiceApp.itxaron();
                    this.selfService.ekarri(this.langileZenbakia, eskatzekoPlaterKopurua);
                }
                //     else (itxaron -> LANGILEA)).
                else {
                    this.pantaila.itxaron(this.langileZenbakia);
                }
                SelfServiceApp.itxaron();

            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int eskatzekoPlaterKopuruaLortu(int unekoPlaterak) {
        int platerDiferentzia = SelfServiceApp.PLATERAK_TOTAL - unekoPlaterak;
        return (int) (Math.random() * platerDiferentzia) + 1;
    }
}
