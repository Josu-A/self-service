public class PantailaCLI implements Pantaila {
    private final String spacing;
    private final int columnWidth = 15;

    public PantailaCLI() {
        this.spacing = " ".repeat(columnWidth);
        goiburua();
        hasierakoZerrendakMarraztu();
    }

    private String zutabeaIdatzi(String edukia) {
        return String.format("%-" + this.columnWidth + "s", edukia);
    }

    private void goiburua() {
        StringBuilder sbb = new StringBuilder();
        for (int i = 0; i < this.columnWidth; i++) {
            sbb.append("=");
        }
        String banatzailea = sbb.toString();

        StringBuilder sb = new StringBuilder(zutabeaIdatzi("SUKALDARIA"));
        for (int i = 0; i < SelfServiceApp.LANGILEAK_TOTAL; i++) {
            sb.append(zutabeaIdatzi("LANGILEA[" + i + "]"));
        }
        for (int i = 0; i < SelfServiceApp.BEZEROAK_TOTAL; i++) {
            sb.append(zutabeaIdatzi("BEZERO[" + i + "]"));
        }
        sb.append(zutabeaIdatzi("BEZEROAK"));
        sb.append(zutabeaIdatzi("PLATERAK"));
        sb.append("\n");
        int banatzaileLuzera = SelfServiceApp.LANGILEAK_TOTAL + SelfServiceApp.BEZEROAK_TOTAL + 3;
        for (int i = 0; i < banatzaileLuzera; i++) {
            sb.append(banatzailea);
        }
        System.out.println(sb.toString());
    }

    private void hasierakoZerrendakMarraztu() {
        int spacingLuzera = 1 + SelfServiceApp.LANGILEAK_TOTAL + SelfServiceApp.BEZEROAK_TOTAL;
        System.out.print(this.spacing.repeat(spacingLuzera));
        bezeroakMarraztu(0);
        System.out.print(this.spacing.repeat(spacingLuzera + 1));
        platerakMarraztu(SelfServiceApp.PLATERAK_TOTAL);
    }

    private synchronized void sukaldariaEkintza(String ekintza) {
        System.out.println(zutabeaIdatzi(ekintza));
    }

    public synchronized void eskaeraEntzun() {
        sukaldariaEkintza("eskaeraEntzun");
    }

    public synchronized void sukaldatu() {
        sukaldariaEkintza("sukaldatu");
    }

    public synchronized void janariaAtera() {
        sukaldariaEkintza("janariaAtera");
    }

    private synchronized void platerakMarraztu(int platerKopurua) {
        StringBuilder sb = new StringBuilder("[");
        sb.append("*".repeat(platerKopurua));
        sb.append(" ".repeat(SelfServiceApp.PLATERAK_TOTAL - platerKopurua));
        sb.append("]");
        System.out.println(sb.toString());
    }

    private synchronized void langileEkintza(String ekintza, int langileZenbakia) {
        String hasierakoHutsunea = this.spacing.repeat(langileZenbakia + 1);
        StringBuilder sb = new StringBuilder(hasierakoHutsunea);
        sb.append(zutabeaIdatzi(ekintza));
        System.out.println(sb.toString());
    }

    private synchronized void langileEkintzaPlaterduna(String ekintza, int ekintzaKopurua, int langileZenbakia, int platerKopurua) {
        String hasierakoHutsunea = this.spacing.repeat(langileZenbakia + 1);
        StringBuilder sb = new StringBuilder(hasierakoHutsunea);
        sb.append(zutabeaIdatzi(ekintza + ":" + ekintzaKopurua));
        sb.append(this.spacing.repeat(SelfServiceApp.LANGILEAK_TOTAL - langileZenbakia - 1));
        sb.append(this.spacing.repeat(SelfServiceApp.BEZEROAK_TOTAL));
        sb.append(this.spacing);
        System.out.print(sb.toString());
        platerakMarraztu(platerKopurua);
    }

    public synchronized void begiratu(int langileZenbakia, int platerKopurua) {
        langileEkintzaPlaterduna("begiratu", platerKopurua, langileZenbakia, platerKopurua);
    }

    public synchronized void eskatu(int langileZenbakia, int platerKopurua) {
        langileEkintza("eskatu:" + platerKopurua, langileZenbakia);
    }

    public synchronized void ekarri(int langileZenbakia, int ekarritakoPlaterKopurua, int platerKopurua) {
        langileEkintzaPlaterduna("ekarri", ekarritakoPlaterKopurua, langileZenbakia, platerKopurua);
    }

    public synchronized void itxaron(int langileZenbakia) {
        langileEkintza("itxaron", langileZenbakia);
    }

    private synchronized void bezeroakMarraztu(int bezeroKopurua) {
        StringBuilder sb = new StringBuilder("[");
        sb.append("*".repeat(bezeroKopurua));
        sb.append(" ".repeat(SelfServiceApp.BEZEROAK_MAX - bezeroKopurua));
        sb.append("]");
        System.out.println(sb.toString());
    }

    private synchronized void bezeroEkintza(String ekintza, int bezeroZenbakia) {
        String hasierakoHutsunea = this.spacing.repeat(bezeroZenbakia + SelfServiceApp.LANGILEAK_TOTAL + 1);
        StringBuilder sb = new StringBuilder(hasierakoHutsunea);
        sb.append(zutabeaIdatzi(ekintza));
        System.out.println(sb.toString());
    }

    private synchronized void bezeroEkintzaZerrendaduna(String ekintza, int bezeroZenbakia, int bezeroKopurua) {
        String hasierakoHutsunea = this.spacing.repeat(bezeroZenbakia + SelfServiceApp.LANGILEAK_TOTAL + 1);
        StringBuilder sb = new StringBuilder(hasierakoHutsunea);
        sb.append(zutabeaIdatzi(ekintza));
        sb.append(this.spacing.repeat(SelfServiceApp.BEZEROAK_TOTAL - bezeroZenbakia - 1));
        System.out.print(sb.toString());
        bezeroakMarraztu(bezeroKopurua);
    }

    private synchronized void bezeroEkintzaPlaterduna(String ekintza, int ekintzaKopurua, int bezeroZenbakia, int platerKopurua) {
        String hasierakoHutsunea = this.spacing.repeat(bezeroZenbakia + SelfServiceApp.LANGILEAK_TOTAL + 1);
        StringBuilder sb = new StringBuilder(hasierakoHutsunea);
        sb.append(zutabeaIdatzi(ekintza + ":" + ekintzaKopurua));
        sb.append(this.spacing.repeat(SelfServiceApp.BEZEROAK_TOTAL - bezeroZenbakia - 1));
        sb.append(this.spacing);
        System.out.print(sb.toString());
        platerakMarraztu(platerKopurua);
    }

    public synchronized void sartu(int bezeroZenbakia, int bezeroKopurua) {
        bezeroEkintzaZerrendaduna("sartu", bezeroZenbakia, bezeroKopurua);
    }

    public synchronized void irten(int bezeroZenbakia, int bezeroKopurua) {
        bezeroEkintzaZerrendaduna("irten", bezeroZenbakia, bezeroKopurua);
    }

    public synchronized void hartu(int bezeroZenbakia, int platerKopurua) {
        bezeroEkintzaPlaterduna("hartu", 1, bezeroZenbakia, platerKopurua);
    }

    public synchronized void eseri(int bezeroZenbakia) {
        bezeroEkintza("eseri", bezeroZenbakia);
    }

    public synchronized void jan(int bezeroZenbakia) {
        bezeroEkintza("jan", bezeroZenbakia);
    }
}
