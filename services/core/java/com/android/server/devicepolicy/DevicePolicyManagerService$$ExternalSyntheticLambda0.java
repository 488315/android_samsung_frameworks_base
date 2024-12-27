package com.android.server.devicepolicy;

import android.R;
import android.app.Notification;
import android.content.pm.PackageInfo;
import android.os.UserHandle;
import android.os.UserManager;
import com.android.internal.notification.SystemNotificationChannels;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda0(DevicePolicyManagerService devicePolicyManagerService, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = devicePolicyManagerService;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                int i = this.f$1;
                List<PackageInfo> installedPackages = devicePolicyManagerService.mInjector.getPackageManager(i).getInstalledPackages(786432);
                UserHandle of = UserHandle.of(i);
                Iterator<PackageInfo> it = installedPackages.iterator();
                while (it.hasNext()) {
                    UserManager.get(devicePolicyManagerService.mInjector.mContext).setApplicationRestrictions(it.next().packageName, null, of);
                }
                break;
            default:
                DevicePolicyManagerService devicePolicyManagerService2 = this.f$0;
                int i2 = this.f$1;
                String string = devicePolicyManagerService2.mContext.getString(17042485);
                devicePolicyManagerService2.mInjector.getNotificationManager().notifyAsUser(null, 1001, new Notification.Builder(devicePolicyManagerService2.mContext, SystemNotificationChannels.DEVICE_ADMIN).setSmallIcon(R.drawable.stat_sys_warning).setContentTitle(devicePolicyManagerService2.mContext.getString(17042484)).setContentText(string).setColor(devicePolicyManagerService2.mContext.getColor(R.color.system_notification_accent_color)).setStyle(new Notification.BigTextStyle().bigText(string)).build(), UserHandle.of(i2));
                break;
        }
    }
}
