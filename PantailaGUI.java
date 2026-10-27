import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PantailaGUI extends JFrame implements Pantaila {
    private final String TITULUA = "Self Service";
    private final int LUZERA = 800;
    private final int ALTUERA = 600;
    private PantailaPanel panel;

    public PantailaGUI() {
        super();
        irudiakEgiaztatu();
        hasieratu();
    }

    private boolean existitzenDaIrudia(String filename) {
        URL url = getClass().getResource(IrudiKonstanteak.IRUDIAK_PATH + filename);
        return url != null;
    }

    private void irudiakEgiaztatu() {
        List<String> faltatakoIrudiak = new ArrayList<>();

        for (String irudiIzena : IrudiKonstanteak.getIrudiFitxategiIzenak()) {
            if (!existitzenDaIrudia(irudiIzena)) {
                faltatakoIrudiak.add(irudiIzena);
            }
        }

        if (!faltatakoIrudiak.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Hurrengo irudiak falta dira:\n");

            for (String missingImage : faltatakoIrudiak) {
                sb.append("- " + missingImage + "\n");
            }

            sb.append("\nSortu " + IrudiKonstanteak.IRUDIAK_PATH + " karpeta beharrezkoa bada eta bertan sartu falta diren irudiak.\n");

            JOptionPane.showMessageDialog(null, sb.toString(), "Irudi fitxategiak falta dira", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void hasieratu() {
        this.setTitle(this.TITULUA);
        this.setSize(this.LUZERA, this.ALTUERA);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Pantaila erdian kokatu

        this.setLayout(new BorderLayout());

        panel = new PantailaPanel();
        this.add(panel, BorderLayout.CENTER);

        this.setResizable(false);
        this.setVisible(true);
    }

    public synchronized void eskaeraEntzun() {
        this.panel.eskaeraEntzun();
    }

    public synchronized void sukaldatu() {
        this.panel.sukaldatu();
    }

    public synchronized void janariaAtera() {
        this.panel.janariaAtera();
    }

    public synchronized void begiratu(int langileZenbakia, int platerKopurua) {
        this.panel.begiratu(langileZenbakia, platerKopurua);
    }

    public synchronized void eskatu(int langileZenbakia, int platerKopurua) {
        this.panel.eskatu(langileZenbakia);
    }

    public synchronized void ekarri(int langileZenbakia, int ekarritakoPlaterKopurua, int platerKopurua) {
        this.panel.ekarri(ekarritakoPlaterKopurua, langileZenbakia, platerKopurua);
    }

    public synchronized void itxaron(int langileZenbakia) {
        this.panel.itxaron(langileZenbakia);
    }

    public synchronized void sartu(int bezeroZenbakia, int bezeroKopurua) {
        this.panel.sartu(bezeroZenbakia, bezeroKopurua);
    }

    public synchronized void irten(int bezeroZenbakia, int bezeroKopurua) {
        this.panel.irten(bezeroZenbakia, bezeroKopurua);
    }

    public synchronized void hartu(int bezeroZenbakia, int platerKopurua) {
        this.panel.hartu(1, bezeroZenbakia, platerKopurua);
    }

    public synchronized void eseri(int bezeroZenbakia) {
        this.panel.eseri(bezeroZenbakia);
    }

    public synchronized void jan(int bezeroZenbakia) {
        this.panel.jan(bezeroZenbakia);
    }
}
