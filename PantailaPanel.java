import javax.swing.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PantailaPanel extends JPanel {
    private final int PERTSONA_LUZERA = 100;
    private final int PERTSONA_ALTUERA = 100;
    private final int SPACING = 20;
    private final int KONTAGAILUA_LUZERA = 30;
    private final int KONTAGAILUA_ALTUERA = 30;
    private final int TEXT_OFFSET = 10;

    private Map<String, Image> irudiak;

    private IrudiKonstanteak.SukaldariEgoera sukaldariEgoera;
    private IrudiKonstanteak.LangileEgoera[] langileEgoerak;
    private IrudiKonstanteak.BezeroEgoera[] bezeroEgoerak;
    private int bezeroKopurua = 0;
    private int platerKopurua = SelfServiceApp.PLATERAK_TOTAL;

    public PantailaPanel() {
        setBackground(Color.WHITE);
        egoerakHasieratu();
        irudiakKargatu();
    }

    private void egoerakHasieratu() {
        this.sukaldariEgoera = IrudiKonstanteak.SukaldariEgoera.IDLE;
        this.langileEgoerak = new IrudiKonstanteak.LangileEgoera[SelfServiceApp.LANGILEAK_TOTAL];
        for (int i = 0; i < this.langileEgoerak.length; i++) {
            this.langileEgoerak[i] = IrudiKonstanteak.LangileEgoera.IDLE;
        }
        this.bezeroEgoerak = new IrudiKonstanteak.BezeroEgoera[SelfServiceApp.BEZEROAK_TOTAL];
        for (int i = 0; i < this.bezeroEgoerak.length; i++) {
            this.bezeroEgoerak[i] = IrudiKonstanteak.BezeroEgoera.IDLE;
        }
    }

    private void irudiakKargatu() {
        irudiak = new HashMap<>();

        IrudiKonstanteak.getIrudiGakoak().forEach(gakoa -> {
            String fitxategiIzena = IrudiKonstanteak.getIrudia(gakoa);
            irudiaKargatu(gakoa, fitxategiIzena);
        });
    }

    private void irudiaKargatu(String gakoa, String fitxategiIzena) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(IrudiKonstanteak.IRUDIAK_PATH + fitxategiIzena));
            irudiak.put(gakoa, icon.getImage());
        }
        catch (Exception e) {
            irudiak.put(gakoa, sortuIrudiLehenetsia(gakoa));
        }
    }

    private Image sortuIrudiLehenetsia(String text) {
        int luzera = this.PERTSONA_LUZERA;
        int altuera = this.PERTSONA_ALTUERA;

        if (text.startsWith(IrudiKonstanteak.A_PLATER)) {
            luzera = this.KONTAGAILUA_LUZERA;
            altuera = this.KONTAGAILUA_ALTUERA;
        }

        Image irudia = createImage(luzera, altuera);
        Graphics g = irudia.getGraphics();

        if (text.startsWith(IrudiKonstanteak.A_SUKALDARI)) {
            g.setColor(Color.RED);
        }
        else if (text.startsWith(IrudiKonstanteak.A_LANGILE)) {
            g.setColor(Color.BLUE);
        }
        else if (text.startsWith(IrudiKonstanteak.A_BEZERO)) {
            g.setColor(Color.GREEN);
        }
        else {
            g.setColor(Color.ORANGE);
        }

        g.fillRect(0, 0, luzera, altuera);
        g.setColor(Color.BLACK);

        g.drawString(text, 5, altuera / 2);

        return irudia;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelWidth = getWidth();

        // Titulua marraztu
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Self Service", panelWidth / 2 - 50, 40);

        // Sukaldaria marraztu
        pertsonaMarraztu(
            g,
            IrudiKonstanteak.A_SUKALDARI + IrudiKonstanteak.A_CONCATENATE + this.sukaldariEgoera,
            50, 100,
            "Sukaldaria"
        );

        // Langileak marraztu
        int langileakPosY = 100;
        for (int i = 0; i < this.langileEgoerak.length; i++) {
            pertsonaMarraztu(
                g,
                IrudiKonstanteak.A_LANGILE + IrudiKonstanteak.A_CONCATENATE + this.langileEgoerak[i],
                200 + i * (this.PERTSONA_LUZERA + SPACING), langileakPosY,
                "Langilea[" + i + "]"
            );
        }

        // Bezeroak marraztu
        int bezeroakPosY = 250;
        for (int i = 0; i < this.bezeroEgoerak.length; i++) {
            pertsonaMarraztu(
                g,
                IrudiKonstanteak.A_BEZERO + IrudiKonstanteak.A_CONCATENATE + this.bezeroEgoerak[i],
                50 + i * (this.PERTSONA_LUZERA + SPACING), bezeroakPosY,
                "Bezeroa [" + i + "]"
            );
        }

        // Kontagailuak marraztu
        int kontagailuakPosY = 500;
        bezeroKopuruaMarraztu(
            g,
            "Bezeroak jatetxean",
            this.bezeroKopurua, SelfServiceApp.BEZEROAK_MAX,
            50, kontagailuakPosY
        );
        platerakMarraztu(
            g,
            "Platerak prest",
            this.platerKopurua, SelfServiceApp.PLATERAK_TOTAL,
            400, kontagailuakPosY
        );
    }

    private void pertsonaMarraztu(Graphics g, String irudiGakoa, int x, int y, String label) {
        Image img = irudiak.get(irudiGakoa);
        g.drawImage(img, x, y, this.PERTSONA_LUZERA, this.PERTSONA_ALTUERA, this);
        g.drawString(label, x, y + this.PERTSONA_ALTUERA + 30);
    }

    private void bezeroKopuruaMarraztu(Graphics g, String label, int oraingoa, int max, int x, int y) {
        kontagailuaMarraztu(g, label, oraingoa, max, x, y);

        // Okupatutako espazioa marraztu
        g.setColor(Color.ORANGE);
        g.fillRect(x, y + this.TEXT_OFFSET, this.KONTAGAILUA_LUZERA * oraingoa, this.KONTAGAILUA_ALTUERA);
    }

    private void platerakMarraztu(Graphics g, String label, int oraingoa, int max, int x, int y) {
        kontagailuaMarraztu(g, label, oraingoa, max, x, y);

        // Platerak marraztu
        for (int i = 0; i < oraingoa; i++) {
            Image img = irudiak.get(IrudiKonstanteak.A_PLATER);
            g.drawImage(img, x + i * this.KONTAGAILUA_LUZERA, y + this.TEXT_OFFSET, this.KONTAGAILUA_LUZERA, this.KONTAGAILUA_ALTUERA, this);
        }
    }

    private void kontagailuaMarraztu(Graphics g, String label, int oraingoa, int max, int x, int y) {
        // Kontagailu izena marraztu
        g.drawString(label + ":", x, y);

        // Kontagailu barra marraztu
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y + this.TEXT_OFFSET, this.KONTAGAILUA_LUZERA * max, this.KONTAGAILUA_ALTUERA);

        // Muga marraztu
        g.setColor(Color.BLACK);
        g.drawRect(x, y + this.TEXT_OFFSET, this.KONTAGAILUA_LUZERA * max, this.KONTAGAILUA_ALTUERA);

        // Kontua marraztu
        g.drawString(oraingoa + "/" + max, x + this.KONTAGAILUA_LUZERA * max + this.TEXT_OFFSET, y + this.TEXT_OFFSET + this.KONTAGAILUA_ALTUERA * 3 / 4);
    }

    public synchronized void eskaeraEntzun() {
        this.sukaldariEgoera = IrudiKonstanteak.SukaldariEgoera.ESKAERA_ENTZUN;
        repaint();
    }

    public synchronized void sukaldatu() {
        this.sukaldariEgoera = IrudiKonstanteak.SukaldariEgoera.SUKALDATU;
        repaint();
    }

    public synchronized void janariaAtera() {
        this.sukaldariEgoera = IrudiKonstanteak.SukaldariEgoera.JANARIA_ATERA;
        repaint();
    }

    public synchronized void begiratu(int langileZenbakia, int platerKopurua) {
        this.langileEgoerak[langileZenbakia] = IrudiKonstanteak.LangileEgoera.BEGIRATU;
        this.platerKopurua = platerKopurua;
        repaint();
    }

    public synchronized void eskatu(int langileZenbakia) {
        this.langileEgoerak[langileZenbakia] = IrudiKonstanteak.LangileEgoera.ESKATU;
        repaint();
    }

    public synchronized void ekarri(int ekarritakoPlaterKopurua, int langileZenbakia, int platerKopurua) {
        this.langileEgoerak[langileZenbakia] = IrudiKonstanteak.LangileEgoera.EKARRI;
        this.platerKopurua = platerKopurua;
        repaint();
    }

    public synchronized void itxaron(int langileZenbakia) {
        this.langileEgoerak[langileZenbakia] = IrudiKonstanteak.LangileEgoera.ITXARON;
        repaint();
    }

    public synchronized void sartu(int bezeroZenbakia, int bezeroKopurua) {
        this.bezeroEgoerak[bezeroZenbakia] = IrudiKonstanteak.BezeroEgoera.SARTU;
        this.bezeroKopurua = bezeroKopurua;
        repaint();
    }

    public synchronized void irten(int bezeroZenbakia, int bezeroKopurua) {
        this.bezeroEgoerak[bezeroZenbakia] = IrudiKonstanteak.BezeroEgoera.IRTEN;
        this.bezeroKopurua = bezeroKopurua;
        repaint();
    }

    public synchronized void hartu(int kopurua, int bezeroZenbakia, int platerKopurua) {
        this.bezeroEgoerak[bezeroZenbakia] = IrudiKonstanteak.BezeroEgoera.HARTU;
        this.platerKopurua = platerKopurua;
        repaint();
    }

    public synchronized void eseri(int bezeroZenbakia) {
        this.bezeroEgoerak[bezeroZenbakia] = IrudiKonstanteak.BezeroEgoera.ESERI;
        repaint();
    }

    public synchronized void jan(int bezeroZenbakia) {
        this.bezeroEgoerak[bezeroZenbakia] = IrudiKonstanteak.BezeroEgoera.JAN;
        repaint();
    }
}
