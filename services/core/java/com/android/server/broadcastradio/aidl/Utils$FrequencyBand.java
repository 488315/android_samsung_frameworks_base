package com.android.server.broadcastradio.aidl;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class Utils$FrequencyBand {
    public static final /* synthetic */ Utils$FrequencyBand[] $VALUES;
    public static final Utils$FrequencyBand AM_LW;
    public static final Utils$FrequencyBand AM_MW;
    public static final Utils$FrequencyBand AM_SW;
    public static final Utils$FrequencyBand FM;
    public static final Utils$FrequencyBand UNKNOWN;

    static {
        Utils$FrequencyBand utils$FrequencyBand = new Utils$FrequencyBand("UNKNOWN", 0);
        UNKNOWN = utils$FrequencyBand;
        Utils$FrequencyBand utils$FrequencyBand2 = new Utils$FrequencyBand("FM", 1);
        FM = utils$FrequencyBand2;
        Utils$FrequencyBand utils$FrequencyBand3 = new Utils$FrequencyBand("AM_LW", 2);
        AM_LW = utils$FrequencyBand3;
        Utils$FrequencyBand utils$FrequencyBand4 = new Utils$FrequencyBand("AM_MW", 3);
        AM_MW = utils$FrequencyBand4;
        Utils$FrequencyBand utils$FrequencyBand5 = new Utils$FrequencyBand("AM_SW", 4);
        AM_SW = utils$FrequencyBand5;
        $VALUES =
                new Utils$FrequencyBand[] {
                    utils$FrequencyBand,
                    utils$FrequencyBand2,
                    utils$FrequencyBand3,
                    utils$FrequencyBand4,
                    utils$FrequencyBand5
                };
    }

    public static Utils$FrequencyBand valueOf(String str) {
        return (Utils$FrequencyBand) Enum.valueOf(Utils$FrequencyBand.class, str);
    }

    public static Utils$FrequencyBand[] values() {
        return (Utils$FrequencyBand[]) $VALUES.clone();
    }
}
