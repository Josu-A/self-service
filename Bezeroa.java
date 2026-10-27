public class Bezeroa extends Thread {
    private SelfService selfService;
    private Pantaila pantaila;
    private int bezeroZenbakia;

    public Bezeroa(int bezeroZenbakia, SelfService selfService, Pantaila pantaila) {
        this.bezeroZenbakia = bezeroZenbakia;
        this.selfService = selfService;
        this.pantaila = pantaila;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // BEZEROA = (sartu -> random[r:JR] -> BEZEROJAN[r]),
                SelfServiceApp.itxaron();
                this.selfService.sartu(this.bezeroZenbakia);

                SelfServiceApp.itxaron();
                int hartzekoPlaterKopurua = (int) (Math.random() * SelfServiceApp.PLATERAK_MAX) + 1;

                // BEZEROJAN[k:JR] = (
                //     hartu -> eseri -> jan -> if (k == 1)
                //     then (irten -> BEZEROA)
                //     else BEZEROJAN[k-1]
                // ).
                for (int i = hartzekoPlaterKopurua; i > 0; i--) {
                    SelfServiceApp.itxaron();
                    this.selfService.hartu(this.bezeroZenbakia);

                    SelfServiceApp.itxaron();
                    this.pantaila.eseri(this.bezeroZenbakia);

                    SelfServiceApp.itxaron();
                    this.pantaila.jan(this.bezeroZenbakia);
                }

                SelfServiceApp.itxaron();
                this.selfService.irten(this.bezeroZenbakia);

            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
