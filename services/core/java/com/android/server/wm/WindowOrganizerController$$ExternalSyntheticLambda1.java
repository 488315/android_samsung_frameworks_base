package com.android.server.wm;

import android.window.WindowContainerTransaction;
import com.android.server.wm.WindowOrganizerController;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class WindowOrganizerController$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ WindowOrganizerController f$0;
    public final /* synthetic */ WindowContainerTransaction f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ WindowOrganizerController.CallerInfo f$3;

    public /* synthetic */ WindowOrganizerController$$ExternalSyntheticLambda1(WindowOrganizerController windowOrganizerController, WindowContainerTransaction windowContainerTransaction, int i, WindowOrganizerController.CallerInfo callerInfo) {
        this.f$0 = windowOrganizerController;
        this.f$1 = windowContainerTransaction;
        this.f$2 = i;
        this.f$3 = callerInfo;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        WindowOrganizerController windowOrganizerController = this.f$0;
        WindowContainerTransaction windowContainerTransaction = this.f$1;
        int i = this.f$2;
        WindowOrganizerController.CallerInfo callerInfo = this.f$3;
        windowOrganizerController.getClass();
        windowOrganizerController.applyTransaction(windowContainerTransaction, i, (Transition) null, callerInfo, ((Boolean) obj).booleanValue());
        windowOrganizerController.setSyncReady(i);
    }
}
