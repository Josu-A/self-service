public class Sukaldaria extends Thread {
    private SelfService selfService;
    private Pantaila pantaila;

    public Sukaldaria(SelfService selfService, Pantaila pantaila) {
        this.selfService = selfService;
        this.pantaila = pantaila;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // SUKALDARIA = (eskaeraEntzun -> sukaldatu -> janariaAtera -> SUKALDARIA).
                SelfServiceApp.itxaron();
                this.selfService.eskaeraEntzun();

                SelfServiceApp.itxaron();
                this.pantaila.sukaldatu();

                SelfServiceApp.itxaron();
                this.selfService.janariaAtera();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
