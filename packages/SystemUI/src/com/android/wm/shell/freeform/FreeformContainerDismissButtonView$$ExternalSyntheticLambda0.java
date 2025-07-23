package com.android.wm.shell.freeform;

import android.util.Log;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class FreeformContainerDismissButtonView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ FreeformContainerDismissButtonView f$0;
    public final /* synthetic */ FreeformContainerViewController$$ExternalSyntheticLambda1 f$1;

    public /* synthetic */ FreeformContainerDismissButtonView$$ExternalSyntheticLambda0(FreeformContainerDismissButtonView freeformContainerDismissButtonView, FreeformContainerViewController$$ExternalSyntheticLambda1 freeformContainerViewController$$ExternalSyntheticLambda1) {
        this.f$0 = freeformContainerDismissButtonView;
        this.f$1 = freeformContainerViewController$$ExternalSyntheticLambda1;
    }

    @Override // java.lang.Runnable
    public final void run() {
        FreeformContainerDismissButtonView freeformContainerDismissButtonView = this.f$0;
        FreeformContainerViewController$$ExternalSyntheticLambda1 freeformContainerViewController$$ExternalSyntheticLambda1 = this.f$1;
        int i = FreeformContainerDismissButtonView.$r8$clinit;
        freeformContainerDismissButtonView.getClass();
        Log.i("FreeformContainer", "[FreeformContainerDismissButtonView] clear()");
        View view = freeformContainerDismissButtonView.mDismissingIconView;
        if (view != null) {
            view.clearAnimation();
            freeformContainerDismissButtonView.mDismissingIconView = null;
        }
        freeformContainerDismissButtonView.setVisibility(8);
        freeformContainerViewController$$ExternalSyntheticLambda1.run();
    }
}
