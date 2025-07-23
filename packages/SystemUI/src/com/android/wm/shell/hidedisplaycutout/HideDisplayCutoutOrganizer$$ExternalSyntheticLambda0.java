package com.android.wm.shell.hidedisplaycutout;

import android.view.SurfaceControl;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import java.util.function.BiConsumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class HideDisplayCutoutOrganizer$$ExternalSyntheticLambda0 implements BiConsumer {
    public final /* synthetic */ HideDisplayCutoutOrganizer f$0;
    public final /* synthetic */ WindowContainerTransaction f$1;
    public final /* synthetic */ SurfaceControl.Transaction f$2;

    public /* synthetic */ HideDisplayCutoutOrganizer$$ExternalSyntheticLambda0(HideDisplayCutoutOrganizer hideDisplayCutoutOrganizer, WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) {
        this.f$0 = hideDisplayCutoutOrganizer;
        this.f$1 = windowContainerTransaction;
        this.f$2 = transaction;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        HideDisplayCutoutOrganizer hideDisplayCutoutOrganizer = this.f$0;
        WindowContainerTransaction windowContainerTransaction = this.f$1;
        SurfaceControl.Transaction transaction = this.f$2;
        int i = HideDisplayCutoutOrganizer.$r8$clinit;
        hideDisplayCutoutOrganizer.applyBoundsAndOffsets((WindowContainerToken) obj, (SurfaceControl) obj2, windowContainerTransaction, transaction);
    }
}
