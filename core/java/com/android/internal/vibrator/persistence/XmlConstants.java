package com.android.internal.vibrator.persistence;

import android.os.VibrationEffect;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

/* loaded from: classes5.dex */
public final class XmlConstants {
    public static final String ATTRIBUTE_AMPLITUDE = "amplitude";
    public static final String ATTRIBUTE_DELAY_MS = "delayMs";
    public static final String ATTRIBUTE_DURATION_MS = "durationMs";
    public static final String ATTRIBUTE_FALLBACK = "fallback";
    public static final String ATTRIBUTE_NAME = "name";
    public static final String ATTRIBUTE_SCALE = "scale";
    public static final int FLAG_ALLOW_HIDDEN_APIS = 1;
    public static final String NAMESPACE = null;
    public static final String TAG_PREDEFINED_EFFECT = "predefined-effect";
    public static final String TAG_PRIMITIVE_EFFECT = "primitive-effect";
    public static final String TAG_REPEATING = "repeating";
    public static final String TAG_VIBRATION_EFFECT = "vibration-effect";
    public static final String TAG_VIBRATION_SELECT = "vibration-select";
    public static final String TAG_WAVEFORM_EFFECT = "waveform-effect";
    public static final String TAG_WAVEFORM_ENTRY = "waveform-entry";
    public static final String VALUE_AMPLITUDE_DEFAULT = "default";

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {}

    public enum PrimitiveEffectName {
        LOW_TICK(8),
        TICK(7),
        CLICK(1),
        SLOW_RISE(5),
        QUICK_RISE(4),
        QUICK_FALL(6),
        SPIN(3),
        THUD(2);

        private final int mPrimitiveId;

        PrimitiveEffectName(int id) {
            this.mPrimitiveId = id;
        }

        public static PrimitiveEffectName findById(int primitiveId) {
            for (PrimitiveEffectName name : values()) {
                if (name.mPrimitiveId == primitiveId) {
                    return name;
                }
            }
            return null;
        }

        public static PrimitiveEffectName findByName(String primitiveName) {
            try {
                return valueOf(primitiveName.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException e) {
                return null;
            }
        }

        public int getPrimitiveId() {
            return this.mPrimitiveId;
        }

        @Override // java.lang.Enum
        public String toString() {
            return name().toLowerCase(Locale.ROOT);
        }
    }

    public enum PredefinedEffectName {
        TICK(2, true),
        CLICK(0, true),
        HEAVY_CLICK(5, true),
        DOUBLE_CLICK(1, true),
        TEXTURE_TICK(21, false),
        THUD(3, false),
        POP(4, false),
        RINGTONE_1(VibrationEffect.RINGTONES[0], false),
        RINGTONE_2(VibrationEffect.RINGTONES[1], false),
        RINGTONE_3(VibrationEffect.RINGTONES[2], false),
        RINGTONE_4(VibrationEffect.RINGTONES[3], false),
        RINGTONE_5(VibrationEffect.RINGTONES[4], false),
        RINGTONE_6(VibrationEffect.RINGTONES[5], false),
        RINGTONE_7(VibrationEffect.RINGTONES[6], false),
        RINGTONE_8(VibrationEffect.RINGTONES[7], false),
        RINGTONE_9(VibrationEffect.RINGTONES[8], false),
        RINGTONE_10(VibrationEffect.RINGTONES[9], false),
        RINGTONE_11(VibrationEffect.RINGTONES[10], false),
        RINGTONE_12(VibrationEffect.RINGTONES[11], false),
        RINGTONE_13(VibrationEffect.RINGTONES[12], false),
        RINGTONE_14(VibrationEffect.RINGTONES[13], false),
        RINGTONE_15(VibrationEffect.RINGTONES[14], false);

        private final int mEffectId;
        private final boolean mIsPublic;

        PredefinedEffectName(int id, boolean isPublic) {
            this.mEffectId = id;
            this.mIsPublic = isPublic;
        }

        public static PredefinedEffectName findById(int effectId, int flags) {
            boolean allowHidden = (flags & 1) != 0;
            for (PredefinedEffectName name : values()) {
                if (name.mEffectId == effectId) {
                    if (name.mIsPublic || allowHidden) {
                        return name;
                    }
                    return null;
                }
            }
            return null;
        }

        public static PredefinedEffectName findByName(String effectName, int flags) {
            boolean allowHidden = (flags & 1) != 0;
            try {
                PredefinedEffectName name = valueOf(effectName.toUpperCase(Locale.ROOT));
                if (name.mIsPublic || allowHidden) {
                    return name;
                }
                return null;
            } catch (IllegalArgumentException e) {
                return null;
            }
        }

        public int getEffectId() {
            return this.mEffectId;
        }

        @Override // java.lang.Enum
        public String toString() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
