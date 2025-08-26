package com.android.wm.shell.windowdecor.additionalviewcontainer;

import android.view.SurfaceControl;
import android.view.View;

/* loaded from: classes3.dex */
public abstract class AdditionalViewContainer {
    public abstract View getView();

    public abstract void releaseView();

    public abstract void setPosition(SurfaceControl.Transaction transaction, float f, float f2);
}
