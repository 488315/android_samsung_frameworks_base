package com.android.systemui.subscreen;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.SurfaceControl;
import com.android.systemui.plugins.subscreen.SubScreenSurfaceControl;

/* loaded from: classes3.dex */
public class SubScreenSurfaceControlImpl implements SubScreenSurfaceControl {
    public final IRemoteAnimationFinishedCallback mFinishedCallback;
    public final SurfaceControl mSurfaceControl;
    public final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();

    public SubScreenSurfaceControlImpl(SurfaceControl surfaceControl, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        this.mSurfaceControl = surfaceControl;
        this.mFinishedCallback = iRemoteAnimationFinishedCallback;
    }

    @Override // com.android.systemui.plugins.subscreen.SubScreenSurfaceControl
    public final void apply() {
        this.mTransaction.apply();
    }

    @Override // com.android.systemui.plugins.subscreen.SubScreenSurfaceControl
    public final void close() {
        this.mTransaction.close();
    }

    @Override // com.android.systemui.plugins.subscreen.SubScreenSurfaceControl
    public final void hide() {
        this.mTransaction.hide(this.mSurfaceControl);
    }

    @Override // com.android.systemui.plugins.subscreen.SubScreenSurfaceControl
    public final void onAnimationFinished() {
        this.mFinishedCallback.onAnimationFinished();
    }

    @Override // com.android.systemui.plugins.subscreen.SubScreenSurfaceControl
    public final void remove() {
        this.mTransaction.remove(this.mSurfaceControl);
    }

    @Override // com.android.systemui.plugins.subscreen.SubScreenSurfaceControl
    public final void setAlpha(float f) {
        this.mTransaction.setAlpha(this.mSurfaceControl, f);
    }

    @Override // com.android.systemui.plugins.subscreen.SubScreenSurfaceControl
    public final void setBackgroundBlurRadius(int i) {
        this.mTransaction.setBackgroundBlurRadius(this.mSurfaceControl, i);
    }

    @Override // com.android.systemui.plugins.subscreen.SubScreenSurfaceControl
    public final void setColor(float[] fArr) {
        this.mTransaction.setColor(this.mSurfaceControl, fArr);
    }

    @Override // com.android.systemui.plugins.subscreen.SubScreenSurfaceControl
    public final void setCornerRadius(float f) {
        this.mTransaction.setCornerRadius(this.mSurfaceControl, f);
    }

    @Override // com.android.systemui.plugins.subscreen.SubScreenSurfaceControl
    public final void setLayer(int i) {
        this.mTransaction.setLayer(this.mSurfaceControl, i);
    }

    @Override // com.android.systemui.plugins.subscreen.SubScreenSurfaceControl
    public final void setMatrix(Matrix matrix, float[] fArr) {
        this.mTransaction.setMatrix(this.mSurfaceControl, matrix, fArr);
    }

    @Override // com.android.systemui.plugins.subscreen.SubScreenSurfaceControl
    public final void setOpaque(boolean z) {
        this.mTransaction.setOpaque(this.mSurfaceControl, z);
    }

    @Override // com.android.systemui.plugins.subscreen.SubScreenSurfaceControl
    public final void setPosition(float f, float f2) {
        this.mTransaction.setPosition(this.mSurfaceControl, f, f2);
    }

    @Override // com.android.systemui.plugins.subscreen.SubScreenSurfaceControl
    public final void setScale(float f, float f2) {
        this.mTransaction.setScale(this.mSurfaceControl, f, f2);
    }

    @Override // com.android.systemui.plugins.subscreen.SubScreenSurfaceControl
    public final void setShadowRadius(float f) {
        this.mTransaction.setShadowRadius(this.mSurfaceControl, f);
    }

    @Override // com.android.systemui.plugins.subscreen.SubScreenSurfaceControl
    public final void setVisibility(boolean z) {
        this.mTransaction.setVisibility(this.mSurfaceControl, z);
    }

    @Override // com.android.systemui.plugins.subscreen.SubScreenSurfaceControl
    public final void setWindowCrop(Rect rect) {
        this.mTransaction.setWindowCrop(this.mSurfaceControl, rect);
    }

    @Override // com.android.systemui.plugins.subscreen.SubScreenSurfaceControl
    public final void show() {
        this.mTransaction.show(this.mSurfaceControl);
    }
}
