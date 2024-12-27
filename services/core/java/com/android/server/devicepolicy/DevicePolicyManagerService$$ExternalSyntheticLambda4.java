package com.android.server.devicepolicy;

import android.app.admin.DevicePolicyState;
import android.app.admin.PolicyKey;
import android.content.ContentResolver;
import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.UserHandle;
import android.provider.Settings;

import com.android.internal.util.FunctionalUtils;

import java.util.HashMap;
import java.util.Map;

public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda4
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda4(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final Object getOrThrow() {
        DevicePolicyState devicePolicyState;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                DevicePolicyEngine devicePolicyEngine = (DevicePolicyEngine) obj;
                synchronized (devicePolicyEngine.mLock) {
                    try {
                        HashMap hashMap = new HashMap();
                        for (int i2 = 0; i2 < devicePolicyEngine.mLocalPolicies.size(); i2++) {
                            UserHandle of =
                                    UserHandle.of(devicePolicyEngine.mLocalPolicies.keyAt(i2));
                            hashMap.put(of, new HashMap());
                            for (PolicyKey policyKey :
                                    ((Map) devicePolicyEngine.mLocalPolicies.valueAt(i2))
                                            .keySet()) {
                                ((Map) hashMap.get(of))
                                        .put(
                                                policyKey,
                                                ((PolicyState)
                                                                ((Map)
                                                                                devicePolicyEngine
                                                                                        .mLocalPolicies
                                                                                        .valueAt(
                                                                                                i2))
                                                                        .get(policyKey))
                                                        .getParcelablePolicyState());
                            }
                        }
                        if (!((HashMap) devicePolicyEngine.mGlobalPolicies).isEmpty()) {
                            hashMap.put(UserHandle.ALL, new HashMap());
                            for (PolicyKey policyKey2 :
                                    ((HashMap) devicePolicyEngine.mGlobalPolicies).keySet()) {
                                ((Map) hashMap.get(UserHandle.ALL))
                                        .put(
                                                policyKey2,
                                                ((PolicyState)
                                                                ((HashMap)
                                                                                devicePolicyEngine
                                                                                        .mGlobalPolicies)
                                                                        .get(policyKey2))
                                                        .getParcelablePolicyState());
                            }
                        }
                        devicePolicyState = new DevicePolicyState(hashMap);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return devicePolicyState;
            case 1:
                Context context = (Context) obj;
                return Boolean.valueOf(
                        context.getSystemService(UsbManager.class) != null
                                && ((UsbManager) context.getSystemService(UsbManager.class))
                                                .getUsbHalVersion()
                                        >= 13);
            default:
                ContentResolver contentResolver = (ContentResolver) obj;
                return Boolean.valueOf(
                        Settings.Secure.getIntForUser(
                                        contentResolver,
                                        "managed_provisioning_dpc_downloaded",
                                        0,
                                        contentResolver.getUserId())
                                == 1);
        }
    }
}
