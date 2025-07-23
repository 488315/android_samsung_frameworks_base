package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public enum DeviceCategory {
    DEVICE_CATEGORY_HEADSET("DEVICE_CATEGORY_HEADSET"),
    DEVICE_CATEGORY_SPEAKER("DEVICE_CATEGORY_SPEAKER"),
    DEVICE_CATEGORY_EARPIECE("DEVICE_CATEGORY_EARPIECE"),
    DEVICE_CATEGORY_EXT_MEDIA("DEVICE_CATEGORY_EXT_MEDIA"),
    DEVICE_CATEGORY_HEARING_AID("DEVICE_CATEGORY_HEARING_AID");

    private final String rawName;

    DeviceCategory(String str) {
        this.rawName = str;
    }

    public String getRawName() {
        return this.rawName;
    }

    static DeviceCategory fromString(String str) {
        for (DeviceCategory deviceCategory : values()) {
            if (deviceCategory.getRawName().equals(str)) {
                return deviceCategory;
            }
        }
        throw new IllegalArgumentException(str);
    }
}
