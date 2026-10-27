import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

public class IrudiKonstanteak {
    // Irudientzako karpeta
    public static final String IRUDIAK_PATH = "/images/";

    // Aurrizkiak
    public static final String A_SUKALDARI = "SUKALDARI";
    public static final String A_LANGILE = "LANGILE";
    public static final String A_BEZERO = "BEZERO";
    public static final String A_PLATER = "PLATER";
    public static final String A_CONCATENATE = "_";

    // Egoerak
    public static enum SukaldariEgoera {
        IDLE,
        ESKAERA_ENTZUN,
        SUKALDATU,
        JANARIA_ATERA
    }
    public static enum LangileEgoera {
        IDLE,
        BEGIRATU,
        ESKATU,
        EKARRI,
        ITXARON
    }
    public static enum BezeroEgoera {
        IDLE,
        SARTU,
        HARTU,
        ESERI,
        JAN,
        IRTEN
    }

    // Irudien helbideak gordetzeko HashMap-a
    private static final Map<String, String> IRUDI_MAP = new HashMap<>();

    static {
        // Sukaldari irudiak
        IRUDI_MAP.put(
            A_SUKALDARI + A_CONCATENATE + SukaldariEgoera.IDLE,
            "sukaldari_idle.png"
        );
        IRUDI_MAP.put(
            A_SUKALDARI + A_CONCATENATE + SukaldariEgoera.ESKAERA_ENTZUN,
            "sukaldari_eskaeraEntzun.png"
        );
        IRUDI_MAP.put(
            A_SUKALDARI + A_CONCATENATE + SukaldariEgoera.SUKALDATU,
            "sukaldari_sukaldatu.png"
        );
        IRUDI_MAP.put(
            A_SUKALDARI + A_CONCATENATE + SukaldariEgoera.JANARIA_ATERA,
            "sukaldari_janariaAtera.png"
        );

        // Langile irudiak
        IRUDI_MAP.put(
            A_LANGILE + A_CONCATENATE + LangileEgoera.IDLE,
            "langile_idle.png"
        );
        IRUDI_MAP.put(
            A_LANGILE + A_CONCATENATE + LangileEgoera.BEGIRATU,
            "langile_begiratu.png"
        );
        IRUDI_MAP.put(
            A_LANGILE + A_CONCATENATE + LangileEgoera.ESKATU,
            "langile_eskatu.png"
        );
        IRUDI_MAP.put(
            A_LANGILE + A_CONCATENATE + LangileEgoera.EKARRI,
            "langile_ekarri.png"
        );
        IRUDI_MAP.put(
            A_LANGILE + A_CONCATENATE + LangileEgoera.ITXARON,
            "langile_itxaron.png"
        );

        // Bezero irudiak
        IRUDI_MAP.put(
            A_BEZERO + A_CONCATENATE + BezeroEgoera.IDLE,
            "bezero_idle.png"
        );
        IRUDI_MAP.put(
            A_BEZERO + A_CONCATENATE + BezeroEgoera.SARTU,
            "bezero_sartu.png"
        );
        IRUDI_MAP.put(
            A_BEZERO + A_CONCATENATE + BezeroEgoera.HARTU,
            "bezero_hartu.png"
        );
        IRUDI_MAP.put(
            A_BEZERO + A_CONCATENATE + BezeroEgoera.ESERI,
            "bezero_eseri.png"
        );
        IRUDI_MAP.put(
            A_BEZERO + A_CONCATENATE + BezeroEgoera.JAN,
            "bezero_jan.png"
        );
        IRUDI_MAP.put(
            A_BEZERO + A_CONCATENATE + BezeroEgoera.IRTEN,
            "bezero_irten.png"
        );

        // Plater irudiak
        IRUDI_MAP.put(A_PLATER, "plater.png");
    }

    public static String getIrudia(String key) {
        return IRUDI_MAP.get(key);
    }

    public static Collection<String> getIrudiGakoak() {
        return IRUDI_MAP.keySet();
    }

    public static Collection<String> getIrudiFitxategiIzenak() {
        return IRUDI_MAP.values();
    }
}
