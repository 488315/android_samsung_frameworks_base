package com.android.systemui.edgelighting;

import com.android.systemui.Dependency;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelParent;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStore;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SystemUIConditionListenerService$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SystemUIConditionListenerService$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                SystemUIConditionListenerService systemUIConditionListenerService = (SystemUIConditionListenerService) obj;
                int i2 = SystemUIConditionListenerService.$r8$clinit;
                systemUIConditionListenerService.getClass();
                systemUIConditionListenerService.mEntries = ((NotifLiveDataStoreImpl) ((NotifLiveDataStore) Dependency.sDependency.getDependencyInner(NotifLiveDataStore.class))).activeNotifList;
                break;
            default:
                NotificationEntry notificationEntry = (NotificationEntry) obj;
                SubscreenDeviceModelParent subscreenDeviceModelParent = ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel;
                if (subscreenDeviceModelParent != null) {
                    subscreenDeviceModelParent.detailClicked(notificationEntry);
                    break;
                }
                break;
        }
    }
}
