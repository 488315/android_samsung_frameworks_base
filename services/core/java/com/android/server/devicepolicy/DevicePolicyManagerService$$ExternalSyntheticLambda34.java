package com.android.server.devicepolicy;

import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.provider.Telephony;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.IntArray;

import com.android.internal.util.FunctionalUtils;
import com.android.server.utils.Slogf;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda34
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda34(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    public final Object getOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                return Integer.valueOf(
                        ((DevicePolicyManagerService) this.f$0)
                                .mContext
                                .getContentResolver()
                                .update(
                                        Telephony.Carriers.ENFORCE_MANAGED_URI,
                                        (ContentValues) this.f$1,
                                        null,
                                        null));
            case 1:
                PackageManager packageManager = (PackageManager) this.f$0;
                String str = (String) this.f$1;
                int i = DevicePolicyManagerService.LocalService.$r8$clinit;
                try {
                    return packageManager.getPackageInfo(str, 0);
                } catch (PackageManager.NameNotFoundException e) {
                    Slogf.e("DevicePolicyManager", "getPackageInfo error", e);
                    return null;
                }
            case 2:
                SubscriptionManager subscriptionManager = (SubscriptionManager) this.f$0;
                String str2 = (String) this.f$1;
                IntArray intArray = new IntArray();
                List availableSubscriptionInfoList =
                        subscriptionManager.getAvailableSubscriptionInfoList();
                int size =
                        availableSubscriptionInfoList != null
                                ? availableSubscriptionInfoList.size()
                                : 0;
                for (int i2 = 0; i2 < size; i2++) {
                    SubscriptionInfo subscriptionInfo =
                            (SubscriptionInfo) availableSubscriptionInfoList.get(i2);
                    if (subscriptionInfo.getGroupOwner().equals(str2)) {
                        intArray.add(subscriptionInfo.getSubscriptionId());
                    }
                }
                return intArray;
            case 3:
                return ((TelephonyManager) this.f$1)
                        .getDevicePolicyOverrideApns(
                                ((DevicePolicyManagerService) this.f$0).mContext);
            default:
                return ((DevicePolicyManagerService) this.f$0)
                        .mDeviceManagementResourcesProvider.getString((String) this.f$1);
        }
    }
}
