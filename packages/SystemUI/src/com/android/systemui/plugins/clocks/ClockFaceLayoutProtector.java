package com.android.systemui.plugins.clocks;

import android.util.Log;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.plugins.PluginWrapper;
import com.android.systemui.plugins.ProtectedPluginListener;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class ClockFaceLayoutProtector implements ClockFaceLayout, PluginWrapper<ClockFaceLayout> {
    private static final String CLASS = "ClockFaceLayout";
    private boolean mHasError = false;
    private ClockFaceLayout mInstance;
    private ProtectedPluginListener mListener;

    private ClockFaceLayoutProtector(ClockFaceLayout clockFaceLayout, ProtectedPluginListener protectedPluginListener) {
        this.mInstance = clockFaceLayout;
        this.mListener = protectedPluginListener;
    }

    public static ClockFaceLayoutProtector protect(ClockFaceLayout clockFaceLayout, ProtectedPluginListener protectedPluginListener) {
        return clockFaceLayout instanceof ClockFaceLayoutProtector ? (ClockFaceLayoutProtector) clockFaceLayout : new ClockFaceLayoutProtector(clockFaceLayout, protectedPluginListener);
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceLayout
    public void applyAodBurnIn(AodClockBurnInModel aodClockBurnInModel) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.applyAodBurnIn(aodClockBurnInModel);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: applyAodBurnIn", e);
            this.mHasError = this.mListener.onFail(CLASS, "applyAodBurnIn", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: applyAodBurnIn", e2);
            this.mHasError = this.mListener.onFail(CLASS, "applyAodBurnIn", e2);
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceLayout
    public ConstraintSet applyConstraints(ConstraintSet constraintSet) {
        if (this.mHasError) {
            return constraintSet;
        }
        try {
            return this.mInstance.applyConstraints(constraintSet);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: applyConstraints", e);
            this.mHasError = this.mListener.onFail(CLASS, "applyConstraints", e);
            return constraintSet;
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: applyConstraints", e2);
            this.mHasError = this.mListener.onFail(CLASS, "applyConstraints", e2);
            return constraintSet;
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceLayout
    public ConstraintSet applyPreviewConstraints(ClockPreviewConfig clockPreviewConfig, ConstraintSet constraintSet) {
        if (this.mHasError) {
            return constraintSet;
        }
        try {
            return this.mInstance.applyPreviewConstraints(clockPreviewConfig, constraintSet);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: applyPreviewConstraints", e);
            this.mHasError = this.mListener.onFail(CLASS, "applyPreviewConstraints", e);
            return constraintSet;
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: applyPreviewConstraints", e2);
            this.mHasError = this.mListener.onFail(CLASS, "applyPreviewConstraints", e2);
            return constraintSet;
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceLayout
    public List<View> getViews() {
        if (this.mHasError) {
            return new ArrayList();
        }
        try {
            return this.mInstance.getViews();
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: getViews", e);
            this.mHasError = this.mListener.onFail(CLASS, "getViews", e);
            return new ArrayList();
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: getViews", e2);
            this.mHasError = this.mListener.onFail(CLASS, "getViews", e2);
            return new ArrayList();
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.plugins.PluginWrapper
    public ClockFaceLayout getPlugin() {
        return this.mInstance;
    }
}
