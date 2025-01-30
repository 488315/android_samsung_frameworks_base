package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator$$ExternalSyntheticLambda3;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotifInflaterImpl implements NotifInflater {
    public final NotifInflationErrorManager mNotifErrorManager;
    public NotificationRowBinderImpl mNotificationRowBinder;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.notification.collection.NotifInflaterImpl$1 */
    public final class C27911 implements NotificationRowContentBinder.InflationCallback {
        public final /* synthetic */ NotifInflater.InflationCallback val$callback;

        public C27911(NotifInflater.InflationCallback inflationCallback) {
            this.val$callback = inflationCallback;
        }

        @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
        public final void handleInflationException(NotificationEntry notificationEntry, Exception exc) {
            NotifInflaterImpl.this.mNotifErrorManager.setInflationError(notificationEntry, exc);
        }

        @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
        public final void onAsyncInflationFinished(NotificationEntry notificationEntry) {
            NotifInflaterImpl.this.mNotifErrorManager.clearInflationError(notificationEntry);
            NotifInflater.InflationCallback inflationCallback = this.val$callback;
            if (inflationCallback != null) {
                PreparationCoordinator.$r8$lambda$T1DwXSSxf_XS7CenlmlbkE5FMFw(((PreparationCoordinator$$ExternalSyntheticLambda3) inflationCallback).f$0, notificationEntry, notificationEntry.mRowController);
            }
        }
    }

    public NotifInflaterImpl(NotifInflationErrorManager notifInflationErrorManager) {
        this.mNotifErrorManager = notifInflationErrorManager;
    }
}
