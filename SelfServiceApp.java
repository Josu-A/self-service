/*
    Praktika - Self-Service

    Self-Service jatetxe bateko bezeroek hainbat aldiz jateko aukera dute, baina
    jatetxean egon daitezkeen bezero kopurua mugatuta dago. Bezeroa jatetxera
    sartzen denean, zenbat aldiz jango duen erabakitzen du, eta, jan nahi duen
    bakoitzean, janaria hartuko du, eseri eta azkenik jan. Guztiz jan duenean,
    jatetxetik irtengo da.

    Bezeroak ez diren beste bi rol daude. Batetik, jatetxeko sukaldariak eskaerak
    jasoko ditu, eskaera hori dagokion jana sukaldatu eta aterako du.

    Bestetik, langileak ere badaude. Hauek, zenbat plater dauden begiratzerakoan
    eta ikusten badute batzuk falta direla, sukaldariari eskaera bat egingo dio.
    Sukaldariak platerak prest daudela esaterakoan, langileak hartu eta bezeroengana
    eramango du. Platerik ez bada falta, itxaron egingo dute plateren bat falta arte.

    Sukaldariak soilik eskaera bat aldi berean kudeatu dezake, baina hainbat plater
    utzi ditzake prest hartzeko.

    LTSAn jatetxeak ez du kudeatzen langileak hartutako prest dagoen platera berarena
    den, eta kontagailu huts batekin kudeatuko da. Java-n, ordea, listen bidez
    langile bakoitzaren eskaeraren egoera kudeatu beharko da.

    Kopuruak: (LTSA / Java)
        - jatetxekoBezeroMaximoa (2 / 4)
        - bezeroKopurua (3 / 6)
        - langileKopurua (2 / 3)
        - platerKopurua (3 / 6)
        - hartzekoPlaterMaximoa (2 / 3)

    Egilea: Josu Aguinaga
    Data: 2025-05-18
*/
public class SelfServiceApp {
    public static final int BEZEROAK_MAX = 4;
    public static final int BEZEROAK_TOTAL = 6;
    public static final int LANGILEAK_TOTAL = 3;
    public static final int PLATERAK_TOTAL = 6;
    public static final int PLATERAK_MAX = 3;

    public static void main(String[] args) {
        Pantaila pantaila = new PantailaGUI();
        SelfService selfService = new SelfService(pantaila);

        Sukaldaria sukaldaria = new Sukaldaria(selfService, pantaila);
        sukaldaria.start();

        Langilea[] langileak = new Langilea[LANGILEAK_TOTAL];
        for (int i = 0; i < LANGILEAK_TOTAL; i++) {
            langileak[i] = new Langilea(i, selfService, pantaila);
            langileak[i].start();
        }

        Bezeroa[] bezeroak = new Bezeroa[BEZEROAK_TOTAL];
        for (int i = 0; i < BEZEROAK_TOTAL; i++) {
            bezeroak[i] = new Bezeroa(i, selfService, pantaila);
            bezeroak[i].start();
        }
    }

    public static void itxaron() throws InterruptedException {
        int itxarotekoDenboraLehenetsiMaximoa = 5000;
        itxaron(itxarotekoDenboraLehenetsiMaximoa);
    }

    public static void itxaron(int denbora) throws InterruptedException {
        Thread.sleep((long) (Math.random() * denbora));
    }
}