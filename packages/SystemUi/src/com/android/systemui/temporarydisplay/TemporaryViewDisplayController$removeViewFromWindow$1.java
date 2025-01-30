package com.android.systemui.temporarydisplay;

import android.view.ViewGroup;
import com.android.systemui.temporarydisplay.TemporaryViewDisplayController;
import com.android.systemui.temporarydisplay.TemporaryViewLogger;
import com.android.systemui.util.wakelock.WakeLock;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
