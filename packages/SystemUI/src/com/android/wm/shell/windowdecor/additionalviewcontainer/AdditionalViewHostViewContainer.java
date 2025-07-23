package com.android.wm.shell.windowdecor.additionalviewcontainer;

import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AdditionalViewHostViewContainer extends AdditionalViewContainer {
    public final Supplier transactionSupplier;
    public final SurfaceControl windowSurface;
    public final SurfaceControlViewHost windowViewHost;

    public AdditionalViewHostViewContainer(SurfaceControl surfaceControl, SurfaceControlViewHost surfaceControlViewHost, Supplier<SurfaceControl.Transaction> supplier) {
        this.windowSurface = surfaceControl;
        this.windowViewHost = surfaceControlViewHost;
        this.transactionSupplier = supplier;
    }

    @Override // com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalViewContainer
    public final View getView() {
        return this.windowViewHost.getView();
    }

    @Override // com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalViewContainer
    public final void releaseView() {
        this.windowViewHost.release();
        SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.transactionSupplier.get();
        transaction.remove(this.windowSurface);
        transaction.apply();
    }

    @Override // com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalViewContainer
    public final void setPosition(SurfaceControl.Transaction transaction, float f, float f2) {
        transaction.setPosition(this.windowSurface, f, f2);
    }
}
