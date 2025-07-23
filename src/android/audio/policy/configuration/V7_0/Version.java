package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public enum Version {
    _7_0("7.0");

    private final String rawName;

    Version(String str) {
        this.rawName = str;
    }

    public String getRawName() {
        return this.rawName;
    }

    static Version fromString(String str) {
        for (Version version : values()) {
            if (version.getRawName().equals(str)) {
                return version;
            }
        }
        throw new IllegalArgumentException(str);
    }
}
