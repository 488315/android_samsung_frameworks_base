package com.android.wm.shell.windowdecor.additionalviewcontainer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.android.wm.shell.windowdecor.WindowManagerWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AdditionalSystemViewContainer extends AdditionalViewContainer {
    public final WindowManager.LayoutParams lp;
    public final View view;
    public final WindowManagerWrapper windowManagerWrapper;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Factory {
    }

    public /* synthetic */ AdditionalSystemViewContainer(WindowManagerWrapper windowManagerWrapper, int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z, View view, int i8, DefaultConstructorMarker defaultConstructorMarker) {
        this(windowManagerWrapper, i, i2, i3, i4, i5, i6, (i8 & 128) != 0 ? 0 : i7, (i8 & 256) != 0 ? false : z, view);
    }

    @Override // com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalViewContainer
    public final View getView() {
        return this.view;
    }

    @Override // com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalViewContainer
    public final void releaseView() {
        this.windowManagerWrapper.windowManager.removeViewImmediate(this.view);
    }

    @Override // com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalViewContainer
    public final void setPosition(SurfaceControl.Transaction transaction, float f, float f2) {
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.view.getLayoutParams();
        layoutParams.x = (int) f;
        layoutParams.y = (int) f2;
        this.windowManagerWrapper.windowManager.updateViewLayout(this.view, layoutParams);
    }

    public AdditionalSystemViewContainer(WindowManagerWrapper windowManagerWrapper, int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z, View view) {
        this.windowManagerWrapper = windowManagerWrapper;
        this.view = view;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(i4, i5, i2, i3, 2041, i6, -2);
        layoutParams.setTitle("Additional view container of Task=" + i);
        layoutParams.gravity = 51;
        layoutParams.setTrustedOverlay();
        layoutParams.forciblyShownTypes = i7;
        if (z) {
            layoutParams.setFitInsetsTypes(0);
            layoutParams.layoutInDisplayCutoutMode = 3;
        }
        this.lp = layoutParams;
        windowManagerWrapper.windowManager.addView(view, layoutParams);
    }

    public /* synthetic */ AdditionalSystemViewContainer(Context context, WindowManagerWrapper windowManagerWrapper, int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z, int i8, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, windowManagerWrapper, i, i2, i3, i4, i5, i6, i7, (i8 & 512) != 0 ? false : z);
    }

    public AdditionalSystemViewContainer(Context context, WindowManagerWrapper windowManagerWrapper, int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z) {
        this(windowManagerWrapper, i, i2, i3, i4, i5, i6, 0, z, LayoutInflater.from(context).inflate(i7, (ViewGroup) null), 128, (DefaultConstructorMarker) null);
    }

    public /* synthetic */ AdditionalSystemViewContainer(Context context, WindowManagerWrapper windowManagerWrapper, int i, int i2, int i3, int i4, int i5, int i6, boolean z, int i7, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, windowManagerWrapper, i, i2, i3, i4, i5, i6, (i7 & 256) != 0 ? false : z);
    }

    public AdditionalSystemViewContainer(Context context, WindowManagerWrapper windowManagerWrapper, int i, int i2, int i3, int i4, int i5, int i6, boolean z) {
        this(windowManagerWrapper, i, i2, i3, i4, i5, i6, 0, z, new View(context), 128, (DefaultConstructorMarker) null);
    }
}
