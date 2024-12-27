package com.android.server.display.config;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public enum ThermalStatus {
    /* JADX INFO: Fake field, exist only in values array */
    EF6("none"),
    /* JADX INFO: Fake field, exist only in values array */
    EF15("light"),
    /* JADX INFO: Fake field, exist only in values array */
    EF24("moderate"),
    /* JADX INFO: Fake field, exist only in values array */
    EF33("severe"),
    /* JADX INFO: Fake field, exist only in values array */
    EF42("critical"),
    /* JADX INFO: Fake field, exist only in values array */
    EF51("emergency"),
    /* JADX INFO: Fake field, exist only in values array */
    EF60("shutdown");

    private final String rawName;

    ThermalStatus(String str) {
        this.rawName = str;
    }

    public static ThermalStatus fromString(String str) {
        for (ThermalStatus thermalStatus : values()) {
            if (thermalStatus.rawName.equals(str)) {
                return thermalStatus;
            }
        }
        throw new IllegalArgumentException(str);
    }

    public final String getRawName() {
        return this.rawName;
    }
}
