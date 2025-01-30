package com.android.systemui.statusbar.notification.row;

import android.util.ArrayMap;
import android.util.ArraySet;
import androidx.core.os.CancellationSignal;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class BindRequester {
    public NotifBindPipeline$$ExternalSyntheticLambda0 mBindRequestListener;

    public final CancellationSignal requestRebind(NotificationEntry notificationEntry, final NotifBindPipeline.BindCallback bindCallback) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        NotifBindPipeline$$ExternalSyntheticLambda0 notifBindPipeline$$ExternalSyntheticLambda0 = this.mBindRequestListener;
        if (notifBindPipeline$$ExternalSyntheticLambda0 != null) {
            NotifBindPipeline notifBindPipeline = notifBindPipeline$$ExternalSyntheticLambda0.f$0;
            NotifBindPipeline.BindEntry bindEntry = (NotifBindPipeline.BindEntry) ((ArrayMap) notifBindPipeline.mBindEntries).get(notificationEntry);
            if (bindEntry != null) {
                bindEntry.invalidated = true;
                if (bindCallback != null) {
                    final ArraySet arraySet = (ArraySet) bindEntry.callbacks;
                    arraySet.add(bindCallback);
                    cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipeline$$ExternalSyntheticLambda1
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
