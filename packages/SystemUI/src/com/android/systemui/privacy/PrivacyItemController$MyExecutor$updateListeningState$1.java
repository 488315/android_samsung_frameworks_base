package com.android.systemui.privacy;

import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PrivacyItemController$MyExecutor$updateListeningState$1 implements Runnable {
    public final /* synthetic */ PrivacyItemController this$0;

    public PrivacyItemController$MyExecutor$updateListeningState$1(PrivacyItemController privacyItemController) {
        this.this$0 = privacyItemController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        PrivacyItemController privacyItemController = this.this$0;
        boolean isEmpty = ((ArrayList) privacyItemController.callbacks).isEmpty();
        boolean z = !isEmpty;
        if (privacyItemController.listening == z) {
            return;
        }
        privacyItemController.listening = z;
        if (isEmpty) {
            Iterator it = privacyItemController.privacyItemMonitors.iterator();
            while (it.hasNext()) {
                AppOpsPrivacyItemMonitor appOpsPrivacyItemMonitor = (AppOpsPrivacyItemMonitor) ((PrivacyItemMonitor) it.next());
                synchronized (appOpsPrivacyItemMonitor.lock) {
                    appOpsPrivacyItemMonitor.callback = null;
                    appOpsPrivacyItemMonitor.setListeningStateLocked();
                    Unit unit = Unit.INSTANCE;
                }
            }
            privacyItemController.update$4$1();
            return;
        }
        for (PrivacyItemMonitor privacyItemMonitor : privacyItemController.privacyItemMonitors) {
            PrivacyItemController$privacyItemMonitorCallback$1 privacyItemController$privacyItemMonitorCallback$1 = privacyItemController.privacyItemMonitorCallback;
            AppOpsPrivacyItemMonitor appOpsPrivacyItemMonitor2 = (AppOpsPrivacyItemMonitor) privacyItemMonitor;
            synchronized (appOpsPrivacyItemMonitor2.lock) {
                appOpsPrivacyItemMonitor2.callback = privacyItemController$privacyItemMonitorCallback$1;
                appOpsPrivacyItemMonitor2.setListeningStateLocked();
                Unit unit2 = Unit.INSTANCE;
            }
        }
        privacyItemController.update$4$1();
    }
}
