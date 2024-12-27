package com.android.server.display.config;

public enum ThermalStatus {
    EF6("none"),
    EF15("light"),
    EF24("moderate"),
    EF33("severe"),
    EF42("critical"),
    EF51("emergency"),
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
