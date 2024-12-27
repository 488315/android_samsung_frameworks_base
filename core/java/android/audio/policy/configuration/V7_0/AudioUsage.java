package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public enum AudioUsage {
    AUDIO_USAGE_UNKNOWN("AUDIO_USAGE_UNKNOWN"),
    AUDIO_USAGE_MEDIA("AUDIO_USAGE_MEDIA"),
    AUDIO_USAGE_VOICE_COMMUNICATION("AUDIO_USAGE_VOICE_COMMUNICATION"),
    AUDIO_USAGE_VOICE_COMMUNICATION_SIGNALLING("AUDIO_USAGE_VOICE_COMMUNICATION_SIGNALLING"),
    AUDIO_USAGE_ALARM("AUDIO_USAGE_ALARM"),
    AUDIO_USAGE_NOTIFICATION("AUDIO_USAGE_NOTIFICATION"),
    AUDIO_USAGE_NOTIFICATION_TELEPHONY_RINGTONE("AUDIO_USAGE_NOTIFICATION_TELEPHONY_RINGTONE"),
    AUDIO_USAGE_ASSISTANCE_ACCESSIBILITY("AUDIO_USAGE_ASSISTANCE_ACCESSIBILITY"),
    AUDIO_USAGE_ASSISTANCE_NAVIGATION_GUIDANCE("AUDIO_USAGE_ASSISTANCE_NAVIGATION_GUIDANCE"),
    AUDIO_USAGE_ASSISTANCE_SONIFICATION("AUDIO_USAGE_ASSISTANCE_SONIFICATION"),
    AUDIO_USAGE_GAME("AUDIO_USAGE_GAME"),
    AUDIO_USAGE_VIRTUAL_SOURCE("AUDIO_USAGE_VIRTUAL_SOURCE"),
    AUDIO_USAGE_ASSISTANT("AUDIO_USAGE_ASSISTANT"),
    AUDIO_USAGE_CALL_ASSISTANT("AUDIO_USAGE_CALL_ASSISTANT"),
    AUDIO_USAGE_EMERGENCY("AUDIO_USAGE_EMERGENCY"),
    AUDIO_USAGE_SAFETY("AUDIO_USAGE_SAFETY"),
    AUDIO_USAGE_VEHICLE_STATUS("AUDIO_USAGE_VEHICLE_STATUS"),
    AUDIO_USAGE_ANNOUNCEMENT("AUDIO_USAGE_ANNOUNCEMENT");

    private final String rawName;

    AudioUsage(String rawName) {
        this.rawName = rawName;
    }

    public String getRawName() {
        return this.rawName;
    }

    static AudioUsage fromString(String rawString) {
        for (AudioUsage _f : values()) {
            if (_f.getRawName().equals(rawString)) {
                return _f;
            }
        }
        throw new IllegalArgumentException(rawString);
    }
}
