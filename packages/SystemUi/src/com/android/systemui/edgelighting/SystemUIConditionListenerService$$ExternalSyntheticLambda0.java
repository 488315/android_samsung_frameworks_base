package com.android.systemui.edgelighting;

import com.android.systemui.Dependency;
import com.android.systemui.edgelighting.SystemUIConditionListenerService;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelParent;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStore;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class SystemUIConditionListenerService$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SystemUIConditionListenerService$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SystemUIConditionListenerService systemUIConditionListenerService = (SystemUIConditionListenerService) this.f$0;
                int i = SystemUIConditionListenerService.$r8$clinit;
                systemUIConditionListenerService.getClass();
                systemUIConditionListenerService.mEntries = ((NotifLiveDataStoreImpl) ((NotifLiveDataStore) Dependency.get(NotifLiveDataStore.class))).activeNotifList;
                break;
            default:
                NotificationEntry notificationEntry = (NotificationEntry) this.f$0;
                int i2 = SystemUIConditionListenerService.BinderC13051.$r8$clinit;
                SubscreenDeviceModelParent subscreenDeviceModelParent = ((SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class)).mDeviceModel;
                if (subscreenDeviceModelParent != null) {
                    subscreenDeviceModelParent.detailClicked(notificationEntry);
                    break;
                }
                break;
        }
    }
}
