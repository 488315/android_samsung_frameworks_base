package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public enum AudioGainMode {
    AUDIO_GAIN_MODE_JOINT("AUDIO_GAIN_MODE_JOINT"),
    AUDIO_GAIN_MODE_CHANNELS("AUDIO_GAIN_MODE_CHANNELS"),
    AUDIO_GAIN_MODE_RAMP("AUDIO_GAIN_MODE_RAMP");

    private final String rawName;

    AudioGainMode(String str) {
        this.rawName = str;
    }

    public String getRawName() {
        return this.rawName;
    }

    static AudioGainMode fromString(String str) {
        for (AudioGainMode audioGainMode : values()) {
            if (audioGainMode.getRawName().equals(str)) {
                return audioGainMode;
            }
        }
        throw new IllegalArgumentException(str);
    }
}
