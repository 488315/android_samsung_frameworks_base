package com.android.server.notification;

import android.app.UiModeManager;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.ColorDisplayManager;
import android.os.Binder;
import android.os.PowerManager;
import android.service.notification.DeviceEffectsApplier;
import android.service.notification.ZenDeviceEffects;
import android.util.Slog;

import com.android.internal.util.FunctionalUtils;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DefaultDeviceEffectsApplier implements DeviceEffectsApplier {
    public static final IntentFilter SCREEN_OFF_INTENT_FILTER =
            new IntentFilter("android.intent.action.SCREEN_OFF");
    public final ColorDisplayManager mColorDisplayManager;
    public final Context mContext;
    public boolean mIsScreenOffReceiverRegistered;
    public boolean mPendingNightMode;
    public final PowerManager mPowerManager;
    public final UiModeManager mUiModeManager;
    public final WallpaperManager mWallpaperManager;
    public final Object mRegisterReceiverLock = new Object();
    public ZenDeviceEffects mLastAppliedEffects = new ZenDeviceEffects.Builder().build();
    public final AnonymousClass1 mNightModeWhenScreenOff =
            new BroadcastReceiver() { // from class:
                                      // com.android.server.notification.DefaultDeviceEffectsApplier.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    DefaultDeviceEffectsApplier.this.unregisterScreenOffReceiver();
                    DefaultDeviceEffectsApplier defaultDeviceEffectsApplier =
                            DefaultDeviceEffectsApplier.this;
                    Binder.withCleanCallingIdentity(
                            new DefaultDeviceEffectsApplier$$ExternalSyntheticLambda1(
                                    defaultDeviceEffectsApplier,
                                    defaultDeviceEffectsApplier.mPendingNightMode));
                }
            };

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.notification.DefaultDeviceEffectsApplier$1] */
    public DefaultDeviceEffectsApplier(Context context) {
        this.mContext = context;
        this.mColorDisplayManager =
                (ColorDisplayManager) context.getSystemService(ColorDisplayManager.class);
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mUiModeManager = (UiModeManager) context.getSystemService(UiModeManager.class);
        WallpaperManager wallpaperManager =
                (WallpaperManager) context.getSystemService(WallpaperManager.class);
        this.mWallpaperManager =
                (wallpaperManager == null || !wallpaperManager.isWallpaperSupported())
                        ? null
                        : wallpaperManager;
    }

    public final void apply(final ZenDeviceEffects zenDeviceEffects, final int i) {
        Binder.withCleanCallingIdentity(
                new FunctionalUtils
                        .ThrowingRunnable() { // from class:
                                              // com.android.server.notification.DefaultDeviceEffectsApplier$$ExternalSyntheticLambda0
                    public final void runOrThrow() {
                        WallpaperManager wallpaperManager;
                        ColorDisplayManager colorDisplayManager;
                        DefaultDeviceEffectsApplier defaultDeviceEffectsApplier =
                                DefaultDeviceEffectsApplier.this;
                        ZenDeviceEffects zenDeviceEffects2 = zenDeviceEffects;
                        int i2 = i;
                        if (defaultDeviceEffectsApplier.mLastAppliedEffects
                                        .shouldSuppressAmbientDisplay()
                                != zenDeviceEffects2.shouldSuppressAmbientDisplay()) {
                            try {
                                defaultDeviceEffectsApplier.mPowerManager.suppressAmbientDisplay(
                                        "DefaultDeviceEffectsApplier:SuppressAmbientDisplay",
                                        zenDeviceEffects2.shouldSuppressAmbientDisplay());
                            } catch (Exception e) {
                                Slog.e("DeviceEffectsApplier", "Could not change AOD override", e);
                            }
                        }
                        if (defaultDeviceEffectsApplier.mLastAppliedEffects.shouldDisplayGrayscale()
                                        != zenDeviceEffects2.shouldDisplayGrayscale()
                                && (colorDisplayManager =
                                                defaultDeviceEffectsApplier.mColorDisplayManager)
                                        != null) {
                            try {
                                colorDisplayManager.setSaturationLevel(
                                        zenDeviceEffects2.shouldDisplayGrayscale() ? 0 : 100);
                            } catch (Exception e2) {
                                Slog.e(
                                        "DeviceEffectsApplier",
                                        "Could not change grayscale override",
                                        e2);
                            }
                        }
                        if (defaultDeviceEffectsApplier.mLastAppliedEffects.shouldDimWallpaper()
                                        != zenDeviceEffects2.shouldDimWallpaper()
                                && (wallpaperManager =
                                                defaultDeviceEffectsApplier.mWallpaperManager)
                                        != null) {
                            try {
                                wallpaperManager.setWallpaperDimAmount(
                                        zenDeviceEffects2.shouldDimWallpaper()
                                                ? 0.6f
                                                : FullScreenMagnificationGestureHandler.MAX_SCALE);
                            } catch (Exception e3) {
                                Slog.e(
                                        "DeviceEffectsApplier",
                                        "Could not change wallpaper override",
                                        e3);
                            }
                        }
                        if (defaultDeviceEffectsApplier.mLastAppliedEffects.shouldUseNightMode()
                                != zenDeviceEffects2.shouldUseNightMode()) {
                            try {
                                defaultDeviceEffectsApplier.updateOrScheduleNightMode(
                                        i2, zenDeviceEffects2.shouldUseNightMode());
                            } catch (Exception e4) {
                                Slog.e(
                                        "DeviceEffectsApplier",
                                        "Could not change dark theme override",
                                        e4);
                            }
                        }
                    }
                });
        this.mLastAppliedEffects = zenDeviceEffects;
    }

    public final void unregisterScreenOffReceiver() {
        synchronized (this.mRegisterReceiverLock) {
            try {
                if (this.mIsScreenOffReceiverRegistered) {
                    this.mIsScreenOffReceiverRegistered = false;
                    this.mContext.unregisterReceiver(this.mNightModeWhenScreenOff);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateOrScheduleNightMode(int i, boolean z) {
        this.mPendingNightMode = z;
        if (i == 1 || i == 2 || i == 3 || !this.mPowerManager.isInteractive()) {
            unregisterScreenOffReceiver();
            Binder.withCleanCallingIdentity(
                    new DefaultDeviceEffectsApplier$$ExternalSyntheticLambda1(this, z));
            return;
        }
        synchronized (this.mRegisterReceiverLock) {
            try {
                if (!this.mIsScreenOffReceiverRegistered) {
                    this.mContext.registerReceiver(
                            this.mNightModeWhenScreenOff, SCREEN_OFF_INTENT_FILTER, 4);
                    this.mIsScreenOffReceiverRegistered = true;
                }
            } finally {
            }
        }
    }
}
