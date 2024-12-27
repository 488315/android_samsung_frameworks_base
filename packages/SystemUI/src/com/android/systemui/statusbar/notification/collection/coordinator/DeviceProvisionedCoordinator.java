package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.pm.IPackageManager;
import android.os.RemoteException;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
/* loaded from: classes2.dex */
public class DeviceProvisionedCoordinator implements Coordinator {
    private static final String TAG = "DeviceProvisionedCoordinator";
    private final DeviceProvisionedController mDeviceProvisionedController;
    private final IPackageManager mIPackageManager;
    private final NotifFilter mNotifFilter = new NotifFilter(TAG) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.DeviceProvisionedCoordinator.1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            return (((DeviceProvisionedControllerImpl) DeviceProvisionedCoordinator.this.mDeviceProvisionedController).deviceProvisioned.get() || DeviceProvisionedCoordinator.this.showNotificationEvenIfUnprovisioned(notificationEntry.mSbn)) ? false : true;
        }
    };
    private final DeviceProvisionedController.DeviceProvisionedListener mDeviceProvisionedListener = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.DeviceProvisionedCoordinator.2
        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public void onDeviceProvisionedChanged() {
            DeviceProvisionedCoordinator.this.mNotifFilter.invalidateList("onDeviceProvisionedChanged");
        }

        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public void onUserSwitched() {
            onUserSetupChanged();
        }

        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public /* bridge */ /* synthetic */ void onUserSetupChanged() {
        }
    };

    public DeviceProvisionedCoordinator(DeviceProvisionedController deviceProvisionedController, IPackageManager iPackageManager) {
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mIPackageManager = iPackageManager;
    }

    private int checkUidPermission(String str, int i) {
        try {
            return this.mIPackageManager.checkUidPermission(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean showNotificationEvenIfUnprovisioned(StatusBarNotification statusBarNotification) {
        return statusBarNotification.getNotification().extras.getBoolean("android.allowDuringSetup");
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        ((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).addCallback(this.mDeviceProvisionedListener);
        notifPipeline.addPreGroupFilter(this.mNotifFilter);
    }
}
