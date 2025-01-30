package com.android.systemui.p016qs.bar.soundcraft.interfaces.routine.extension;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public enum ActionType {
    SPATIAL_AUDIO { // from class: com.android.systemui.qs.bar.soundcraft.interfaces.routine.extension.ActionType.SPATIAL_AUDIO
        @Override // com.android.systemui.p016qs.bar.soundcraft.interfaces.routine.extension.ActionType
        public final String getTag(String str) {
            return str.concat("_spatial_audio");
        }
    },
    HEAD_TRACKING { // from class: com.android.systemui.qs.bar.soundcraft.interfaces.routine.extension.ActionType.HEAD_TRACKING
        @Override // com.android.systemui.p016qs.bar.soundcraft.interfaces.routine.extension.ActionType
        public final String getTag(String str) {
            return str.concat("_head_tracking");
        }
    },
    EQUALIZER { // from class: com.android.systemui.qs.bar.soundcraft.interfaces.routine.extension.ActionType.EQUALIZER
        @Override // com.android.systemui.p016qs.bar.soundcraft.interfaces.routine.extension.ActionType
        public final String getTag(String str) {
            return str.concat("_equalizer");
        }
    },
    VOICE_BOOST { // from class: com.android.systemui.qs.bar.soundcraft.interfaces.routine.extension.ActionType.VOICE_BOOST
        @Override // com.android.systemui.p016qs.bar.soundcraft.interfaces.routine.extension.ActionType
        public final String getTag(String str) {
            return str.concat("_boost_voice");
        }
    },
    VOLUME_NORMALIZATION { // from class: com.android.systemui.qs.bar.soundcraft.interfaces.routine.extension.ActionType.VOLUME_NORMALIZATION
        @Override // com.android.systemui.p016qs.bar.soundcraft.interfaces.routine.extension.ActionType
        public final String getTag(String str) {
            return str.concat("_loudness_normalization");
        }
    };

    /* synthetic */ ActionType(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract String getTag(String str);
}
