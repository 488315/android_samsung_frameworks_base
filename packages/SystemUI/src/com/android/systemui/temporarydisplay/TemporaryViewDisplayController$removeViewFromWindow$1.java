package com.android.systemui.temporarydisplay;

import android.view.ViewGroup;
import com.android.systemui.temporarydisplay.TemporaryViewDisplayController;
import com.android.systemui.temporarydisplay.TemporaryViewLogger;
import com.android.systemui.util.wakelock.WakeLock;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class TemporaryViewDisplayController$removeViewFromWindow$1 implements Runnable {
    public final /* synthetic */ TemporaryViewDisplayController.DisplayInfo $displayInfo;
    public final /* synthetic */ ViewGroup $view;
    public final /* synthetic */ TemporaryViewDisplayController this$0;

    public TemporaryViewDisplayController$removeViewFromWindow$1(TemporaryViewDisplayController temporaryViewDisplayController, TemporaryViewDisplayController.DisplayInfo displayInfo, ViewGroup viewGroup) {
        this.this$0 = temporaryViewDisplayController;
        this.$displayInfo = displayInfo;
        this.$view = viewGroup;
    }

    @Override // java.lang.Runnable
    public final void run() {
        TemporaryViewLogger temporaryViewLogger = this.this$0.logger;
        TemporaryViewInfo temporaryViewInfo = this.$displayInfo.info;
        ViewGroup viewGroup = this.$view;
        TemporaryViewLogger.Companion companion = TemporaryViewLogger.Companion;
        temporaryViewLogger.logViewRemovedFromWindowManager(temporaryViewInfo, viewGroup, false);
        this.this$0.windowManager.removeView(this.$view);
        TemporaryViewDisplayController.DisplayInfo displayInfo = this.$displayInfo;
        WakeLock wakeLock = displayInfo.wakeLock;
        if (wakeLock != null) {
            wakeLock.release(displayInfo.info.getWakeReason());
        }
    }
}
