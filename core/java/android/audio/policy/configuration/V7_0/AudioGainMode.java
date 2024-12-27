package android.audio.policy.configuration.V7_0;

public enum AudioGainMode {
    AUDIO_GAIN_MODE_JOINT("AUDIO_GAIN_MODE_JOINT"),
    AUDIO_GAIN_MODE_CHANNELS("AUDIO_GAIN_MODE_CHANNELS"),
    AUDIO_GAIN_MODE_RAMP("AUDIO_GAIN_MODE_RAMP");

    private final String rawName;

    AudioGainMode(String rawName) {
        this.rawName = rawName;
    }

    public String getRawName() {
        return this.rawName;
    }

    static AudioGainMode fromString(String rawString) {
        for (AudioGainMode _f : values()) {
            if (_f.getRawName().equals(rawString)) {
                return _f;
            }
        }
        throw new IllegalArgumentException(rawString);
    }
}
