package android.audio.policy.configuration.V7_0;

public enum MixType {
    mix("mix"),
    mux("mux");

    private final String rawName;

    MixType(String rawName) {
        this.rawName = rawName;
    }

    public String getRawName() {
        return this.rawName;
    }

    static MixType fromString(String rawString) {
        for (MixType _f : values()) {
            if (_f.getRawName().equals(rawString)) {
                return _f;
            }
        }
        throw new IllegalArgumentException(rawString);
    }
}
