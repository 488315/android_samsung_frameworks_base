package com.android.server.devicepolicy;

import android.app.AppGlobals;
import android.app.admin.DevicePolicyEventLogger;
import android.app.role.RoleManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.telephony.TelephonyManager;
import android.telephony.data.ApnSetting;
import android.util.ArraySet;
import com.android.internal.util.FunctionalUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda153 implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda153(Object obj, Object obj2, Object obj3, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
    }

    public final Object getOrThrow() {
        ArrayList arrayList;
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = (DevicePolicyManagerService) this.f$0;
                CallerIdentity callerIdentity = (CallerIdentity) this.f$1;
                List list = (List) this.f$2;
                devicePolicyManagerService.getClass();
                int userId = UserHandle.getUserId(callerIdentity.mUid);
                synchronized (devicePolicyManagerService.getLockObject()) {
                    Set activeAdminPackagesLocked = devicePolicyManagerService.getActiveAdminPackagesLocked(userId);
                    arrayList = new ArrayList();
                    for (int size = list.size() - 1; size >= 0; size--) {
                        String str = (String) list.get(size);
                        if (((ArraySet) activeAdminPackagesLocked).contains(str)) {
                            arrayList.add(str);
                        } else {
                            try {
                                devicePolicyManagerService.mInjector.getClass();
                                if (!AppGlobals.getPackageManager().isPackageAvailable(str, userId)) {
                                    arrayList.add(str);
                                }
                            } catch (RemoteException unused) {
                            }
                        }
                    }
                    list.removeAll(arrayList);
                }
                synchronized (devicePolicyManagerService.getLockObject()) {
                    devicePolicyManagerService.getProfileOwnerOrDeviceOwnerLocked(UserHandle.getUserId(callerIdentity.mUid)).meteredDisabledPackages = list;
                    devicePolicyManagerService.saveSettingsLocked(UserHandle.getUserId(callerIdentity.mUid), false, false, false);
                }
                devicePolicyManagerService.pushMeteredDisabledPackages(UserHandle.getUserId(callerIdentity.mUid));
                return arrayList;
            case 1:
                DevicePolicyManagerService devicePolicyManagerService2 = (DevicePolicyManagerService) this.f$0;
                CallerIdentity callerIdentity2 = (CallerIdentity) this.f$1;
                Bundle bundle = (Bundle) this.f$2;
                String[] factoryMacAddresses = ((WifiManager) devicePolicyManagerService2.mInjector.mContext.getSystemService(WifiManager.class)).getFactoryMacAddresses();
                if (factoryMacAddresses == null) {
                    return null;
                }
                DevicePolicyEventLogger.createEvent(54).setAdmin(callerIdentity2.mPackageName).setKnoxBundleValue(bundle).write();
                if (factoryMacAddresses.length > 0) {
                    return factoryMacAddresses[0];
                }
                return null;
            case 2:
                List roleHoldersAsUser = ((RoleManager) this.f$0).getRoleHoldersAsUser((String) this.f$1, (UserHandle) this.f$2);
                if (roleHoldersAsUser.isEmpty()) {
                    return null;
                }
                return (String) roleHoldersAsUser.get(0);
            default:
                return Integer.valueOf(((TelephonyManager) this.f$1).addDevicePolicyOverrideApn(((DevicePolicyManagerService) this.f$0).mContext, (ApnSetting) this.f$2));
        }
    }
}
