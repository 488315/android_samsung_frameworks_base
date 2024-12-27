package com.android.systemui.audio.soundcraft.feature;

import com.samsung.android.feature.SemFloatingFeature;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SoundCraftFeatures {
    public static final SoundCraftFeatures INSTANCE = new SoundCraftFeatures();
    public static final boolean supportVoiceBoost = StringsKt__StringsKt.contains(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_AUDIO_CONFIG_SOUNDALIVE_VERSION"), "voice_boost", false);
    public static final boolean supportDolby = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_DOLBY_AUDIO");
    public static final boolean supportEQ = StringsKt__StringsKt.contains(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_AUDIO_CONFIG_SOUNDALIVE_VERSION"), "eq_custom", false);

    private SoundCraftFeatures() {
    }
}
