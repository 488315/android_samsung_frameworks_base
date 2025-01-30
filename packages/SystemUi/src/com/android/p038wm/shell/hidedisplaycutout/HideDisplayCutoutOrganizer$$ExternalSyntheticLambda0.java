package com.android.p038wm.shell.hidedisplaycutout;

import android.view.SurfaceControl;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import java.util.function.BiConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        this.f$0.applyBoundsAndOffsets((WindowContainerToken) obj, (SurfaceControl) obj2, this.f$1, this.f$2);
    }
}
