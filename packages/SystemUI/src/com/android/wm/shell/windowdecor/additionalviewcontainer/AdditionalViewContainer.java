package com.android.wm.shell.windowdecor.additionalviewcontainer;

import android.view.SurfaceControl;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class AdditionalViewContainer {
    public abstract View getView();

    public abstract void releaseView();

    public abstract void setPosition(SurfaceControl.Transaction transaction, float f, float f2);
}
