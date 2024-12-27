package com.android.server.os;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IDeviceIdentifiersPolicyService;
import android.os.SystemProperties;
import android.os.UserHandle;

import com.android.internal.telephony.TelephonyPermissions;
import com.android.server.SystemService;

public final class DeviceIdentifiersPolicyService extends SystemService {

    public final class DeviceIdentifiersPolicy extends IDeviceIdentifiersPolicyService.Stub {
        public final Context mContext;

        public DeviceIdentifiersPolicy(Context context) {
            this.mContext = context;
        }

        public final String getSerial() {
            return !TelephonyPermissions.checkCallingOrSelfReadDeviceIdentifiers(
                            this.mContext, (String) null, (String) null, "getSerial")
                    ? "unknown"
                    : SystemProperties.get("ro.serialno", "unknown");
        }

        public final String getSerialForPackage(String str, String str2) {
            int callingUid = Binder.getCallingUid();
            try {
                if (this.mContext
                                .getPackageManager()
                                .getPackageUidAsUser(str, UserHandle.getUserId(callingUid))
                        == callingUid) {
                    return !TelephonyPermissions.checkCallingOrSelfReadDeviceIdentifiers(
                                    this.mContext, str, str2, "getSerial")
                            ? "unknown"
                            : SystemProperties.get("ro.serialno", "unknown");
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
            throw new IllegalArgumentException(
                    "Invalid callingPackage or callingPackage does not belong to caller's uid:"
                            + Binder.getCallingUid());
        }
    }

    public DeviceIdentifiersPolicyService(Context context) {
        super(context);
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("device_identifiers", new DeviceIdentifiersPolicy(getContext()));
    }
}
