package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public enum MixType {
    mix("mix"),
    mux("mux");

    private final String rawName;

    MixType(String str) {
        this.rawName = str;
    }

    public String getRawName() {
        return this.rawName;
    }

    static MixType fromString(String str) {
        for (MixType mixType : values()) {
            if (mixType.getRawName().equals(str)) {
                return mixType;
            }
        }
        throw new IllegalArgumentException(str);
    }
}
