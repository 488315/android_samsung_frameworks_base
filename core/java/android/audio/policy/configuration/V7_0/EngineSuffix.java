package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public enum EngineSuffix {
    _default("default"),
    configurable("configurable");

    private final String rawName;

    EngineSuffix(String rawName) {
        this.rawName = rawName;
    }

    public String getRawName() {
        return this.rawName;
    }

    static EngineSuffix fromString(String rawString) {
        for (EngineSuffix _f : values()) {
            if (_f.getRawName().equals(rawString)) {
                return _f;
            }
        }
        throw new IllegalArgumentException(rawString);
    }
}
