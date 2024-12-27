package com.android.systemui.audio.soundcraft.interfaces.routine.action.wearable;

import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class BudsActionType {
    public static final /* synthetic */ BudsActionType[] $VALUES;
    public static final BudsActionType EQUALIZER;
    public static final BudsActionType SPATIAL_AUDIO;
    public static final BudsActionType VOICE_BOOST;
    public static final BudsActionType VOLUME_NORMALIZATION;

    static {
        BudsActionType budsActionType = new BudsActionType("SPATIAL_AUDIO", 0) { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.action.wearable.BudsActionType.SPATIAL_AUDIO
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.audio.soundcraft.interfaces.routine.action.wearable.BudsActionType
            public final String getTag(String str) {
                return str.concat("_spatial_audio");
            }
        };
        SPATIAL_AUDIO = budsActionType;
        BudsActionType budsActionType2 = new BudsActionType("HEAD_TRACKING", 1) { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.action.wearable.BudsActionType.HEAD_TRACKING
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.audio.soundcraft.interfaces.routine.action.wearable.BudsActionType
            public final String getTag(String str) {
                return str.concat("_head_tracking");
            }
        };
        BudsActionType budsActionType3 = new BudsActionType("EQUALIZER", 2) { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.action.wearable.BudsActionType.EQUALIZER
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.audio.soundcraft.interfaces.routine.action.wearable.BudsActionType
            public final String getTag(String str) {
                return str.concat("_equalizer");
            }
        };
        EQUALIZER = budsActionType3;
        BudsActionType budsActionType4 = new BudsActionType("VOICE_BOOST", 3) { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.action.wearable.BudsActionType.VOICE_BOOST
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.audio.soundcraft.interfaces.routine.action.wearable.BudsActionType
            public final String getTag(String str) {
                return str.concat("_boost_voice");
            }
        };
        VOICE_BOOST = budsActionType4;
        BudsActionType budsActionType5 = new BudsActionType("VOLUME_NORMALIZATION", 4) { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.action.wearable.BudsActionType.VOLUME_NORMALIZATION
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.audio.soundcraft.interfaces.routine.action.wearable.BudsActionType
            public final String getTag(String str) {
                return str.concat("_loudness_normalization");
            }
        };
        VOLUME_NORMALIZATION = budsActionType5;
        BudsActionType[] budsActionTypeArr = {budsActionType, budsActionType2, budsActionType3, budsActionType4, budsActionType5};
        $VALUES = budsActionTypeArr;
        EnumEntriesKt.enumEntries(budsActionTypeArr);
    }

    public /* synthetic */ BudsActionType(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i);
    }

    public static BudsActionType valueOf(String str) {
        return (BudsActionType) Enum.valueOf(BudsActionType.class, str);
    }

    public static BudsActionType[] values() {
        return (BudsActionType[]) $VALUES.clone();
    }

    public abstract String getTag(String str);

    private BudsActionType(String str, int i) {
    }
}
