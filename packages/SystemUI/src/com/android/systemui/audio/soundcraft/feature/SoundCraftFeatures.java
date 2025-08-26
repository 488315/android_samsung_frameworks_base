package com.android.systemui.audio.soundcraft.feature;

import com.samsung.android.feature.SemFloatingFeature;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes.dex */
public final class SoundCraftFeatures {
    public static final SoundCraftFeatures INSTANCE = new SoundCraftFeatures();
    public static final boolean supportVoiceBoost = StringsKt__StringsKt.contains(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_AUDIO_CONFIG_SOUNDALIVE_VERSION"), "voice_boost", false);
    public static final boolean supportDolby = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_DOLBY_AUDIO");
    public static final boolean supportEQ = StringsKt__StringsKt.contains(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_AUDIO_CONFIG_SOUNDALIVE_VERSION"), "eq_custom", false);
    public static final boolean dolbyEnabled = StringsKt__StringsKt.contains(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_AUDIO_CONFIG_SOUNDALIVE_VERSION"), "spk_stereo", false);

    private SoundCraftFeatures() {
    }
}
