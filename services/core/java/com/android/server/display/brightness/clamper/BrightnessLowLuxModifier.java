package com.android.server.display.brightness.clamper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.hardware.display.DisplayManagerInternal;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Slog;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.KillPolicyManager$$ExternalSyntheticOutline0;
import com.android.server.chimera.AggressivePolicyHandler$$ExternalSyntheticOutline0;
import com.android.server.display.DisplayDeviceConfig;
import com.android.server.display.brightness.clamper.BrightnessClamperController;
import com.android.server.display.config.EvenDimmerBrightnessData;
import com.android.server.display.utils.DebugUtils;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BrightnessLowLuxModifier extends BrightnessModifier {
    public static final boolean DEBUG = DebugUtils.isDebuggable("BrightnessLowLuxModifier");
    public float mAmbientLux;
    public float mBrightnessLowerBound;
    public final BrightnessClamperController.ClamperChangeListener mChangeListener;
    public final ContentResolver mContentResolver;
    public final DisplayDeviceConfig mDisplayDeviceConfig;
    public boolean mIsActive;
    public float mMinNitsAllowed;
    public int mReason;
    public final SettingsObserver mSettingsObserver;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
            BrightnessLowLuxModifier.this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("even_dimmer_min_nits"), false, this);
            BrightnessLowLuxModifier.this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("even_dimmer_activated"), false, this);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            BrightnessLowLuxModifier.this.recalculateLowerBound();
        }
    }

    public BrightnessLowLuxModifier(Handler handler, BrightnessClamperController.ClamperChangeListener clamperChangeListener, Context context, DisplayDeviceConfig displayDeviceConfig) {
        this.mChangeListener = clamperChangeListener;
        this.mContentResolver = context.getContentResolver();
        this.mSettingsObserver = new SettingsObserver(handler);
        this.mDisplayDeviceConfig = displayDeviceConfig;
        handler.post(new Runnable() { // from class: com.android.server.display.brightness.clamper.BrightnessLowLuxModifier$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BrightnessLowLuxModifier.this.recalculateLowerBound();
            }
        });
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    public final void dump(PrintWriter printWriter) {
        AggressivePolicyHandler$$ExternalSyntheticOutline0.m(KillPolicyManager$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(KillPolicyManager$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "BrightnessLowLuxModifier:", "  mIsActive="), this.mIsActive, printWriter, "  mBrightnessLowerBound="), this.mBrightnessLowerBound, printWriter, "  mReason="), this.mReason, printWriter, "  mAmbientLux="), this.mAmbientLux, printWriter, "  mMinNitsAllowed="), this.mMinNitsAllowed, printWriter);
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    public final float getBrightnessAdjusted(float f, DisplayManagerInternal.DisplayPowerRequest displayPowerRequest) {
        return Math.max(this.mBrightnessLowerBound, f);
    }

    public float getBrightnessLowerBound() {
        return this.mBrightnessLowerBound;
    }

    public int getBrightnessReason() {
        return this.mReason;
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    public final int getModifier() {
        return this.mReason;
    }

    public boolean isActive() {
        return this.mIsActive;
    }

    public void recalculateLowerBound() {
        float f;
        float floatForUser = Settings.Secure.getFloatForUser(this.mContentResolver, "even_dimmer_min_nits", 0.2f, -2);
        int i = 0;
        boolean z = ((Settings.Secure.getFloatForUser(this.mContentResolver, "even_dimmer_activated", 1.0f, -2) > 1.0f ? 1 : (Settings.Secure.getFloatForUser(this.mContentResolver, "even_dimmer_activated", 1.0f, -2) == 1.0f ? 0 : -1)) == 0) && this.mAmbientLux != -1.0f;
        DisplayDeviceConfig displayDeviceConfig = this.mDisplayDeviceConfig;
        if (z) {
            float f2 = this.mAmbientLux;
            EvenDimmerBrightnessData evenDimmerBrightnessData = displayDeviceConfig.mEvenDimmerBrightnessData;
            r4 = evenDimmerBrightnessData != null ? evenDimmerBrightnessData.mMinLuxToNits.interpolate(f2) : -1.0f;
            float max = Math.max(floatForUser, r4);
            f = displayDeviceConfig.getBrightnessFromBacklight(displayDeviceConfig.getBacklightFromNits(max));
            int i2 = floatForUser > r4 ? 32 : 16;
            r4 = max;
            i = i2;
        } else {
            EvenDimmerBrightnessData evenDimmerBrightnessData2 = displayDeviceConfig.mEvenDimmerBrightnessData;
            f = evenDimmerBrightnessData2 == null ? FullScreenMagnificationGestureHandler.MAX_SCALE : evenDimmerBrightnessData2.mTransitionPoint;
        }
        if (this.mBrightnessLowerBound == f && this.mReason == i && this.mIsActive == z) {
            return;
        }
        this.mIsActive = z;
        this.mReason = i;
        if (DEBUG) {
            Slog.i("BrightnessLowLuxModifier", "isActive: " + z + ", minBrightnessAllowed: " + f + ", mAmbientLux: " + this.mAmbientLux + ", mReason: " + this.mReason + ", minNitsAllowed: " + r4);
        }
        this.mMinNitsAllowed = r4;
        this.mBrightnessLowerBound = f;
        this.mChangeListener.onChanged();
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    public void setAmbientLux(float f) {
        this.mAmbientLux = f;
        recalculateLowerBound();
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    public final boolean shouldApply(DisplayManagerInternal.DisplayPowerRequest displayPowerRequest) {
        return this.mIsActive;
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    public final boolean shouldListenToLightSensor() {
        return Settings.Secure.getFloatForUser(this.mContentResolver, "even_dimmer_activated", 1.0f, -2) == 1.0f;
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    public final void stop() {
        this.mContentResolver.unregisterContentObserver(this.mSettingsObserver);
    }
}
