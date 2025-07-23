package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public enum AudioEncapsulationType {
    AUDIO_ENCAPSULATION_TYPE_NONE("AUDIO_ENCAPSULATION_TYPE_NONE"),
    AUDIO_ENCAPSULATION_TYPE_IEC61937("AUDIO_ENCAPSULATION_TYPE_IEC61937");

    private final String rawName;

    AudioEncapsulationType(String str) {
        this.rawName = str;
    }

    public String getRawName() {
        return this.rawName;
    }

    static AudioEncapsulationType fromString(String str) {
        for (AudioEncapsulationType audioEncapsulationType : values()) {
            if (audioEncapsulationType.getRawName().equals(str)) {
                return audioEncapsulationType;
            }
        }
        throw new IllegalArgumentException(str);
    }
}
