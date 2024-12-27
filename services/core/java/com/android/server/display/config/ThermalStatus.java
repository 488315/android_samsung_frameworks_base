package com.android.server.display.config;

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
