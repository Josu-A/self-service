public class SelfService {
    private Pantaila pantaila;
    private int bezeroKopurua;
    private int platerKopurua;
    private boolean[] eskaeraEgoerak;
    private boolean sukaldariaOkupatua;
    private int azkenekoLangileEskaera;

    public SelfService(Pantaila pantaila) {
        this.pantaila = pantaila;
        this.bezeroKopurua = 0;
        this.platerKopurua = SelfServiceApp.PLATERAK_TOTAL;
        this.eskaeraEgoerak = new boolean[SelfServiceApp.LANGILEAK_TOTAL];
        for (int i = 0; i < SelfServiceApp.LANGILEAK_TOTAL; i++) {
            this.eskaeraEgoerak[i] = false;
        }
        this.sukaldariaOkupatua = false;
        this.azkenekoLangileEskaera = -1;
    }

    // when (b < SK) b[BR].sartu -> SELF_SERVICE[b+1][p][so][ek]
    public synchronized void sartu(int bezeroZenbakia) throws InterruptedException {
        while (!(this.bezeroKopurua < SelfServiceApp.BEZEROAK_MAX)) {
            wait();
        }
        this.bezeroKopurua++;
        this.pantaila.sartu(bezeroZenbakia, this.bezeroKopurua);
        notifyAll();
    }

    // b[BR].irten -> SELF_SERVICE[b-1][p][so][ek]
    public synchronized void irten(int bezeroZenbakia) throws InterruptedException {
        this.bezeroKopurua--;
        this.pantaila.irten(bezeroZenbakia, this.bezeroKopurua);
        notifyAll();
    }

    // when (p > 0) b[BR].hartu -> SELF_SERVICE[b][p-1][so][ek]
    public synchronized void hartu(int bezeroZenbakia) throws InterruptedException {
        while (!(this.platerKopurua > 0)) {
            wait();
        }
        this.platerKopurua--;
        this.pantaila.hartu(bezeroZenbakia, this.platerKopurua);
        notifyAll();
    }

    // l[LR].begiratu[p] -> SELF_SERVICE[b][p][so][ek]
    public synchronized int begiratu(int langileZenbakia) {
        this.pantaila.begiratu(langileZenbakia, this.platerKopurua);
        return this.platerKopurua;
    }

    // when (p < PK && ek > 0) l[LR].ekarri[k:1..(PK-p)] -> SELF_SERVICE[b][p+k][so][ek-1]
    public synchronized void ekarri(int langileZenbakia, int platerKopurua) throws InterruptedException {
        while (!(this.platerKopurua + platerKopurua <= SelfServiceApp.PLATERAK_TOTAL
            && this.eskaeraEgoerak[langileZenbakia]
        )) {
            wait();
        }
        this.platerKopurua += platerKopurua;
        this.eskaeraEgoerak[langileZenbakia] = false;
        this.pantaila.ekarri(langileZenbakia, platerKopurua, this.platerKopurua);
        notifyAll();
    }

    // when (so == 0) l[LR].eskatu -> SELF_SERVICE[b][p][1][ek]
    public synchronized void eskatu(int langileZenbakia, int platerKopurua) throws InterruptedException {
        while (this.sukaldariaOkupatua) {
            wait();
        }
        this.sukaldariaOkupatua = true;
        this.azkenekoLangileEskaera = langileZenbakia;
        this.pantaila.eskatu(langileZenbakia, platerKopurua);
        notifyAll();
    }

    // when (so == 1) s.eskaeraEntzun -> SELF_SERVICE[b][p][so][ek]
    public synchronized void eskaeraEntzun() throws InterruptedException {
        while (!this.sukaldariaOkupatua) {
            wait();
        }
        this.pantaila.eskaeraEntzun();
    }

    // s.janariaAtera -> SELF_SERVICE[b][p][0][ek+1]
    public synchronized void janariaAtera() throws InterruptedException {
        this.sukaldariaOkupatua = false;
        this.eskaeraEgoerak[this.azkenekoLangileEskaera] = true;
        this.pantaila.janariaAtera();
        notifyAll();
    }
}
