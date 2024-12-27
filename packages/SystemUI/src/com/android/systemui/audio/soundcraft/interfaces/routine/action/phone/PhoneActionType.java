package com.android.systemui.audio.soundcraft.interfaces.routine.action.phone;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PhoneActionType {
    public static final /* synthetic */ PhoneActionType[] $VALUES;
    public static final PhoneActionType DOLBY;
    public static final PhoneActionType EQUALIZER;
    public static final PhoneActionType VOICE_BOOST;
    public static final PhoneActionType VOLUME_NORMALIZATION;
    private final String actionTag;
    private final String paramTag;

    static {
        PhoneActionType phoneActionType = new PhoneActionType("DOLBY", 0, "dolby", "dolby_key");
        DOLBY = phoneActionType;
        PhoneActionType phoneActionType2 = new PhoneActionType("EQUALIZER", 1, "eqpreset", "eqpreset_key");
        EQUALIZER = phoneActionType2;
        PhoneActionType phoneActionType3 = new PhoneActionType("VOICE_BOOST", 2, "voice_boost", "voice_boost_key");
        VOICE_BOOST = phoneActionType3;
        PhoneActionType phoneActionType4 = new PhoneActionType("VOLUME_NORMALIZATION", 3, "volume_normalization", "volume_normalization_key");
        VOLUME_NORMALIZATION = phoneActionType4;
        PhoneActionType[] phoneActionTypeArr = {phoneActionType, phoneActionType2, phoneActionType3, phoneActionType4};
        $VALUES = phoneActionTypeArr;
        EnumEntriesKt.enumEntries(phoneActionTypeArr);
    }

    private PhoneActionType(String str, int i, String str2, String str3) {
        this.actionTag = str2;
        this.paramTag = str3;
    }

    public static PhoneActionType valueOf(String str) {
        return (PhoneActionType) Enum.valueOf(PhoneActionType.class, str);
    }

    public static PhoneActionType[] values() {
        return (PhoneActionType[]) $VALUES.clone();
    }

    public final String getActionTag() {
        return this.actionTag;
    }

    public final String getParamTag() {
        return this.paramTag;
    }
}
