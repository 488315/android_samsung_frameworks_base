package com.android.systemui.statusbar.notification.row;

import android.util.ArrayMap;
import android.util.ArraySet;
import androidx.core.os.CancellationSignal;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;

public abstract class BindRequester {
    public NotifBindPipeline$$ExternalSyntheticLambda1 mBindRequestListener;

    public final CancellationSignal requestRebind(NotificationEntry notificationEntry, final NotifBindPipeline.BindCallback bindCallback) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        NotifBindPipeline$$ExternalSyntheticLambda1 notifBindPipeline$$ExternalSyntheticLambda1 = this.mBindRequestListener;
        if (notifBindPipeline$$ExternalSyntheticLambda1 != null) {
            NotifBindPipeline notifBindPipeline = notifBindPipeline$$ExternalSyntheticLambda1.f$0;
            NotifBindPipeline.BindEntry bindEntry = (NotifBindPipeline.BindEntry) ((ArrayMap) notifBindPipeline.mBindEntries).get(notificationEntry);
            if (bindEntry != null) {
                bindEntry.invalidated = true;
                if (bindCallback != null) {
                    final ArraySet arraySet = (ArraySet) bindEntry.callbacks;
                    arraySet.add(bindCallback);
                    cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipeline$$ExternalSyntheticLambda3
                        @Override // androidx.core.os.CancellationSignal.OnCancelListener
                        public final void onCancel() {
                            arraySet.remove(bindCallback);
                        }
                    });
                }
                notifBindPipeline.requestPipelineRun(notificationEntry);
            }
        }
        return cancellationSignal;
    }
}
