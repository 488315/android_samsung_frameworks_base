package com.android.server.audio;

import android.media.AudioAttributes;
import android.media.FadeManagerConfiguration;
import android.media.VolumeShaper;
import android.media.audiopolicy.Flags;
import android.util.Slog;

import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class FadeConfigurations {
    public FadeManagerConfiguration mActiveFadeManagerConfig;
    public FadeManagerConfiguration mDefaultFadeManagerConfig;
    public final Object mLock = new Object();
    public FadeManagerConfiguration mTransientFadeManagerConfig;
    public FadeManagerConfiguration mUpdatedFadeManagerConfig;
    public static final List DEFAULT_UNFADEABLE_PLAYER_TYPES = List.of(13, 3);
    public static final List DEFAULT_UNFADEABLE_CONTENT_TYPES = List.of(1);
    public static final List DEFAULT_FADEABLE_USAGES = List.of(14, 1);
    public static final VolumeShaper.Configuration DEFAULT_FADEOUT_VSHAPE =
            new VolumeShaper.Configuration.Builder()
                    .setId(2)
                    .setCurve(
                            new float[] {
                                FullScreenMagnificationGestureHandler.MAX_SCALE, 0.25f, 1.0f
                            },
                            new float[] {
                                1.0f, 0.65f, FullScreenMagnificationGestureHandler.MAX_SCALE
                            })
                    .setOptionFlags(2)
                    .setDuration(2000)
                    .build();

    public final FadeManagerConfiguration getActiveFadeMgrConfigLocked() {
        FadeManagerConfiguration fadeManagerConfiguration = this.mTransientFadeManagerConfig;
        if (fadeManagerConfiguration != null) {
            return fadeManagerConfiguration;
        }
        FadeManagerConfiguration fadeManagerConfiguration2 = this.mUpdatedFadeManagerConfig;
        if (fadeManagerConfiguration2 != null) {
            return fadeManagerConfiguration2;
        }
        if (this.mDefaultFadeManagerConfig == null) {
            this.mDefaultFadeManagerConfig = new FadeManagerConfiguration.Builder().build();
        }
        return this.mDefaultFadeManagerConfig;
    }

    public final VolumeShaper.Configuration getFadeOutVolumeShaperConfig(
            AudioAttributes audioAttributes) {
        VolumeShaper.Configuration fadeOutVolumeShaperConfigForAudioAttributes;
        if (!Flags.enableFadeManagerConfiguration()) {
            return DEFAULT_FADEOUT_VSHAPE;
        }
        synchronized (this.mLock) {
            try {
                FadeManagerConfiguration updatedFadeManagerConfigLocked =
                        getUpdatedFadeManagerConfigLocked();
                fadeOutVolumeShaperConfigForAudioAttributes =
                        updatedFadeManagerConfigLocked
                                .getFadeOutVolumeShaperConfigForAudioAttributes(audioAttributes);
                if (fadeOutVolumeShaperConfigForAudioAttributes == null) {
                    fadeOutVolumeShaperConfigForAudioAttributes =
                            updatedFadeManagerConfigLocked.getFadeOutVolumeShaperConfigForUsage(
                                    audioAttributes.getSystemUsage());
                }
            } finally {
            }
        }
        return fadeOutVolumeShaperConfigForAudioAttributes;
    }

    public final FadeManagerConfiguration getUpdatedFadeManagerConfigLocked() {
        if (this.mActiveFadeManagerConfig == null) {
            this.mActiveFadeManagerConfig = getActiveFadeMgrConfigLocked();
        }
        return this.mActiveFadeManagerConfig;
    }

    public final boolean isFadeable(AudioAttributes audioAttributes, int i, int i2) {
        synchronized (this.mLock) {
            try {
                if (!Flags.enableFadeManagerConfiguration()
                        ? DEFAULT_UNFADEABLE_PLAYER_TYPES.contains(Integer.valueOf(i2))
                        : getUpdatedFadeManagerConfigLocked().isPlayerTypeUnfadeable(i2)) {
                    Slog.i("AS.FadeConfigurations", "not fadeable: player type:" + i2);
                    return false;
                }
                int contentType = audioAttributes.getContentType();
                if (!Flags.enableFadeManagerConfiguration()
                        ? DEFAULT_UNFADEABLE_CONTENT_TYPES.contains(Integer.valueOf(contentType))
                        : getUpdatedFadeManagerConfigLocked()
                                .isContentTypeUnfadeable(contentType)) {
                    Slog.i(
                            "AS.FadeConfigurations",
                            "not fadeable: content type:" + audioAttributes.getContentType());
                    return false;
                }
                int systemUsage = audioAttributes.getSystemUsage();
                if (!Flags.enableFadeManagerConfiguration()
                        ? DEFAULT_FADEABLE_USAGES.contains(Integer.valueOf(systemUsage))
                        : getUpdatedFadeManagerConfigLocked().isUsageFadeable(systemUsage)) {
                    return !isUnfadeableForFadeMgrConfigLocked(i, audioAttributes);
                }
                Slog.i(
                        "AS.FadeConfigurations",
                        "not fadeable: usage:" + audioAttributes.getUsage());
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isUnfadeableForFadeMgrConfigLocked(
            int i, AudioAttributes audioAttributes) {
        if (!Flags.enableFadeManagerConfiguration()
                ? false
                : getUpdatedFadeManagerConfigLocked()
                        .isAudioAttributesUnfadeable(audioAttributes)) {
            Slog.i("AS.FadeConfigurations", "not fadeable: aa:" + audioAttributes);
            return true;
        }
        if (!(!Flags.enableFadeManagerConfiguration()
                ? false
                : getUpdatedFadeManagerConfigLocked().isUidUnfadeable(i))) {
            return false;
        }
        HermesService$3$$ExternalSyntheticOutline0.m(
                i, "not fadeable: uid:", "AS.FadeConfigurations");
        return true;
    }
}
