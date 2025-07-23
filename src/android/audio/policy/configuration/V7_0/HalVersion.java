package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public enum HalVersion {
    _2_0("2.0"),
    _3_0("3.0");

    private final String rawName;

    HalVersion(String str) {
        this.rawName = str;
    }

    public String getRawName() {
        return this.rawName;
    }

    static HalVersion fromString(String str) {
        for (HalVersion halVersion : values()) {
            if (halVersion.getRawName().equals(str)) {
                return halVersion;
            }
        }
        throw new IllegalArgumentException(str);
    }
}
