package com.android.systemui.plugins.clocks;

import android.graphics.Rect;
import android.util.Log;
import com.android.systemui.plugins.PluginWrapper;
import com.android.systemui.plugins.ProtectedPluginListener;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ClockFaceEventsProtector implements ClockFaceEvents, PluginWrapper<ClockFaceEvents> {
    private static final String CLASS = "ClockFaceEvents";
    private boolean mHasError = false;
    private ClockFaceEvents mInstance;
    private ProtectedPluginListener mListener;

    private ClockFaceEventsProtector(ClockFaceEvents clockFaceEvents, ProtectedPluginListener protectedPluginListener) {
        this.mInstance = clockFaceEvents;
        this.mListener = protectedPluginListener;
    }

    public static ClockFaceEventsProtector protect(ClockFaceEvents clockFaceEvents, ProtectedPluginListener protectedPluginListener) {
        return clockFaceEvents instanceof ClockFaceEventsProtector ? (ClockFaceEventsProtector) clockFaceEvents : new ClockFaceEventsProtector(clockFaceEvents, protectedPluginListener);
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
    public void onFontSettingChanged(float f) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.onFontSettingChanged(f);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: onFontSettingChanged", e);
            this.mHasError = this.mListener.onFail(CLASS, "onFontSettingChanged", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: onFontSettingChanged", e2);
            this.mHasError = this.mListener.onFail(CLASS, "onFontSettingChanged", e2);
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
    public void onSecondaryDisplayChanged(boolean z) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.onSecondaryDisplayChanged(z);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: onSecondaryDisplayChanged", e);
            this.mHasError = this.mListener.onFail(CLASS, "onSecondaryDisplayChanged", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: onSecondaryDisplayChanged", e2);
            this.mHasError = this.mListener.onFail(CLASS, "onSecondaryDisplayChanged", e2);
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
    public void onTargetRegionChanged(Rect rect) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.onTargetRegionChanged(rect);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: onTargetRegionChanged", e);
            this.mHasError = this.mListener.onFail(CLASS, "onTargetRegionChanged", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: onTargetRegionChanged", e2);
            this.mHasError = this.mListener.onFail(CLASS, "onTargetRegionChanged", e2);
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
    public void onThemeChanged(ThemeConfig themeConfig) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.onThemeChanged(themeConfig);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: onThemeChanged", e);
            this.mHasError = this.mListener.onFail(CLASS, "onThemeChanged", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: onThemeChanged", e2);
            this.mHasError = this.mListener.onFail(CLASS, "onThemeChanged", e2);
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
    public void onTimeTick() {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.onTimeTick();
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: onTimeTick", e);
            this.mHasError = this.mListener.onFail(CLASS, "onTimeTick", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: onTimeTick", e2);
            this.mHasError = this.mListener.onFail(CLASS, "onTimeTick", e2);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.plugins.PluginWrapper
    public ClockFaceEvents getPlugin() {
        return this.mInstance;
    }
}
