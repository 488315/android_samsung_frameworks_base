package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public enum AudioContentType {
    AUDIO_CONTENT_TYPE_UNKNOWN("AUDIO_CONTENT_TYPE_UNKNOWN"),
    AUDIO_CONTENT_TYPE_SPEECH("AUDIO_CONTENT_TYPE_SPEECH"),
    AUDIO_CONTENT_TYPE_MUSIC("AUDIO_CONTENT_TYPE_MUSIC"),
    AUDIO_CONTENT_TYPE_MOVIE("AUDIO_CONTENT_TYPE_MOVIE"),
    AUDIO_CONTENT_TYPE_SONIFICATION("AUDIO_CONTENT_TYPE_SONIFICATION");

    private final String rawName;

    AudioContentType(String str) {
        this.rawName = str;
    }

    public String getRawName() {
        return this.rawName;
    }

    static AudioContentType fromString(String str) {
        for (AudioContentType audioContentType : values()) {
            if (audioContentType.getRawName().equals(str)) {
                return audioContentType;
            }
        }
        throw new IllegalArgumentException(str);
    }
}
