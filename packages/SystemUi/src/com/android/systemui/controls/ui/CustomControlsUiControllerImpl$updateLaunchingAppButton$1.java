package com.android.systemui.controls.ui;

import android.app.PendingIntent;
import com.android.systemui.controls.management.adapter.MainControlAdapter;
import com.android.systemui.controls.management.model.MainComponentModel;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CustomControlsUiControllerImpl$updateLaunchingAppButton$1 implements Runnable {
    public final /* synthetic */ PendingIntent $pendingIntent;
    public final /* synthetic */ CustomControlsUiControllerImpl this$0;

    public CustomControlsUiControllerImpl$updateLaunchingAppButton$1(CustomControlsUiControllerImpl customControlsUiControllerImpl, PendingIntent pendingIntent) {
        this.this$0 = customControlsUiControllerImpl;
        this.$pendingIntent = pendingIntent;
    }

    @Override // java.lang.Runnable
    public final void run() {
        CustomControlsUiControllerImpl customControlsUiControllerImpl = this.this$0;
        PendingIntent pendingIntent = this.$pendingIntent;
        customControlsUiControllerImpl.launchingPendingIntent = pendingIntent;
        MainComponentModel mainComponentModel = customControlsUiControllerImpl.componentModel;
        mainComponentModel.showButton = pendingIntent != null;
        int indexOf = ((ArrayList) customControlsUiControllerImpl.models).indexOf(mainComponentModel);
        MainControlAdapter mainControlAdapter = customControlsUiControllerImpl.controlAdapter;
        if (mainControlAdapter != null) {
            mainControlAdapter.notifyItemChanged(indexOf);
        }
    }
}
