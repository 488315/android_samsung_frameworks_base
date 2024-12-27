package com.android.server.broadcastradio.hal2;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
final class FrequencyBand {
    public static final /* synthetic */ FrequencyBand[] $VALUES;
    public static final FrequencyBand AM_LW;
    public static final FrequencyBand AM_MW;
    public static final FrequencyBand AM_SW;
    public static final FrequencyBand FM;
    public static final FrequencyBand UNKNOWN;

    static {
        FrequencyBand frequencyBand = new FrequencyBand("UNKNOWN", 0);
        UNKNOWN = frequencyBand;
        FrequencyBand frequencyBand2 = new FrequencyBand("FM", 1);
        FM = frequencyBand2;
        FrequencyBand frequencyBand3 = new FrequencyBand("AM_LW", 2);
        AM_LW = frequencyBand3;
        FrequencyBand frequencyBand4 = new FrequencyBand("AM_MW", 3);
        AM_MW = frequencyBand4;
        FrequencyBand frequencyBand5 = new FrequencyBand("AM_SW", 4);
        AM_SW = frequencyBand5;
        $VALUES =
                new FrequencyBand[] {
                    frequencyBand, frequencyBand2, frequencyBand3, frequencyBand4, frequencyBand5
                };
    }

    public static FrequencyBand valueOf(String str) {
        return (FrequencyBand) Enum.valueOf(FrequencyBand.class, str);
    }

    public static FrequencyBand[] values() {
        return (FrequencyBand[]) $VALUES.clone();
    }
}
