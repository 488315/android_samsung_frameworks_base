package com.android.systemui.controls.ui;

import android.app.PendingIntent;
import com.android.systemui.controls.management.adapter.StatefulControlAdapter;
import com.android.systemui.controls.management.model.MainComponentModel;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecControlsUiControllerImpl$updateLaunchingAppButton$1 implements Runnable {
    public final /* synthetic */ PendingIntent $pendingIntent;
    public final /* synthetic */ SecControlsUiControllerImpl this$0;

    public SecControlsUiControllerImpl$updateLaunchingAppButton$1(SecControlsUiControllerImpl secControlsUiControllerImpl, PendingIntent pendingIntent) {
        this.this$0 = secControlsUiControllerImpl;
        this.$pendingIntent = pendingIntent;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SecControlsUiControllerImpl secControlsUiControllerImpl = this.this$0;
        PendingIntent pendingIntent = this.$pendingIntent;
        secControlsUiControllerImpl.launchingPendingIntent = pendingIntent;
        MainComponentModel mainComponentModel = secControlsUiControllerImpl.componentModel;
        mainComponentModel.showButton = pendingIntent != null;
        int indexOf = ((ArrayList) secControlsUiControllerImpl.models).indexOf(mainComponentModel);
        StatefulControlAdapter statefulControlAdapter = secControlsUiControllerImpl.controlAdapter;
        if (statefulControlAdapter != null) {
            statefulControlAdapter.notifyItemChanged(indexOf);
        }
    }
}
