package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public enum EngineSuffix {
    _default("default"),
    configurable("configurable");

    private final String rawName;

    EngineSuffix(String str) {
        this.rawName = str;
    }

    public String getRawName() {
        return this.rawName;
    }

    static EngineSuffix fromString(String str) {
        for (EngineSuffix engineSuffix : values()) {
            if (engineSuffix.getRawName().equals(str)) {
                return engineSuffix;
            }
        }
        throw new IllegalArgumentException(str);
    }
}
