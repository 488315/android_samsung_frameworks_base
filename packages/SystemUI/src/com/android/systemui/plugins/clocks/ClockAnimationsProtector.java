package com.android.systemui.plugins.clocks;

import android.util.Log;
import com.android.systemui.plugins.PluginWrapper;
import com.android.systemui.plugins.ProtectedPluginListener;

/* loaded from: classes2.dex */
public class ClockAnimationsProtector implements ClockAnimations, PluginWrapper<ClockAnimations> {
    private static final String CLASS = "ClockAnimations";
    private boolean mHasError = false;
    private ClockAnimations mInstance;
    private ProtectedPluginListener mListener;

    private ClockAnimationsProtector(ClockAnimations clockAnimations, ProtectedPluginListener protectedPluginListener) {
        this.mInstance = clockAnimations;
        this.mListener = protectedPluginListener;
    }

    public static ClockAnimationsProtector protect(ClockAnimations clockAnimations, ProtectedPluginListener protectedPluginListener) {
        return clockAnimations instanceof ClockAnimationsProtector ? (ClockAnimationsProtector) clockAnimations : new ClockAnimationsProtector(clockAnimations, protectedPluginListener);
    }

    @Override // com.android.systemui.plugins.clocks.ClockAnimations
    public void charge() {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.charge();
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: charge", e);
            this.mHasError = this.mListener.onFail(CLASS, "charge", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: charge", e2);
            this.mHasError = this.mListener.onFail(CLASS, "charge", e2);
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockAnimations
    public void doze(float f) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.doze(f);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: doze", e);
            this.mHasError = this.mListener.onFail(CLASS, "doze", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: doze", e2);
            this.mHasError = this.mListener.onFail(CLASS, "doze", e2);
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockAnimations
    public void enter() {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.enter();
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: enter", e);
            this.mHasError = this.mListener.onFail(CLASS, "enter", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: enter", e2);
            this.mHasError = this.mListener.onFail(CLASS, "enter", e2);
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockAnimations
    public void fold(float f) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.fold(f);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: fold", e);
            this.mHasError = this.mListener.onFail(CLASS, "fold", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: fold", e2);
            this.mHasError = this.mListener.onFail(CLASS, "fold", e2);
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockAnimations
    public void onFidgetTap(float f, float f2) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.onFidgetTap(f, f2);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: onFidgetTap", e);
            this.mHasError = this.mListener.onFail(CLASS, "onFidgetTap", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: onFidgetTap", e2);
            this.mHasError = this.mListener.onFail(CLASS, "onFidgetTap", e2);
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockAnimations
    public void onFontAxesChanged(ClockAxisStyle clockAxisStyle) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.onFontAxesChanged(clockAxisStyle);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: onFontAxesChanged", e);
            this.mHasError = this.mListener.onFail(CLASS, "onFontAxesChanged", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: onFontAxesChanged", e2);
            this.mHasError = this.mListener.onFail(CLASS, "onFontAxesChanged", e2);
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockAnimations
    public void onPickerCarouselSwiping(float f) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.onPickerCarouselSwiping(f);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: onPickerCarouselSwiping", e);
            this.mHasError = this.mListener.onFail(CLASS, "onPickerCarouselSwiping", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: onPickerCarouselSwiping", e2);
            this.mHasError = this.mListener.onFail(CLASS, "onPickerCarouselSwiping", e2);
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockAnimations
    public void onPositionUpdated(int i, int i2, float f) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.onPositionUpdated(i, i2, f);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: onPositionUpdated", e);
            this.mHasError = this.mListener.onFail(CLASS, "onPositionUpdated", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: onPositionUpdated", e2);
            this.mHasError = this.mListener.onFail(CLASS, "onPositionUpdated", e2);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.plugins.PluginWrapper
    public ClockAnimations getPlugin() {
        return this.mInstance;
    }

    @Override // com.android.systemui.plugins.clocks.ClockAnimations
    public void onPositionUpdated(float f, float f2) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.onPositionUpdated(f, f2);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: onPositionUpdated", e);
            this.mHasError = this.mListener.onFail(CLASS, "onPositionUpdated", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: onPositionUpdated", e2);
            this.mHasError = this.mListener.onFail(CLASS, "onPositionUpdated", e2);
        }
    }
}
