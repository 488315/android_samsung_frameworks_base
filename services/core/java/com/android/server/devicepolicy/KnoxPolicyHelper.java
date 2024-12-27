package com.android.server.devicepolicy;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.android.server.utils.Slogf;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.IEnterpriseDeviceManager;
import com.samsung.android.knox.IMiscPolicy;
import com.samsung.android.knox.net.wifi.IWifiPolicy;
import com.samsung.android.knox.restriction.IRestrictionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KnoxPolicyHelper {
    public final Context mContext;
    public DevicePolicyManager mDPM;
    public EnterpriseDeviceManager mEDM;
    public IEnterpriseDeviceManager mEdmService;
    public IMiscPolicy mMiscService;
    public IRestrictionPolicy mRestrictionService = getService();
    public final UserManager mUserManager;
    public IWifiPolicy mWifiService;

    public KnoxPolicyHelper(Context context) {
        this.mContext = context;
        if (this.mMiscService == null) {
            this.mMiscService = IMiscPolicy.Stub.asInterface(ServiceManager.getService("misc_policy"));
        }
        this.mMiscService = this.mMiscService;
        if (this.mWifiService == null) {
            this.mWifiService = IWifiPolicy.Stub.asInterface(ServiceManager.getService("wifi_policy"));
        }
        this.mWifiService = this.mWifiService;
        this.mEDM = getEDM();
        if (this.mDPM == null) {
            this.mDPM = (DevicePolicyManager) context.getSystemService("device_policy");
        }
        this.mDPM = this.mDPM;
        UserManager userManager = this.mUserManager;
        this.mUserManager = userManager == null ? UserManager.get(context) : userManager;
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final IEnterpriseDeviceManager getIEDMService() {
        if (this.mEdmService == null) {
            this.mEdmService = IEnterpriseDeviceManager.Stub.asInterface(ServiceManager.getService("enterprise_policy"));
        }
        return this.mEdmService;
    }

    public final IRestrictionPolicy getService() {
        if (this.mRestrictionService == null) {
            this.mRestrictionService = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
        }
        return this.mRestrictionService;
    }

    public final void handlePasswordExpirationNotification(int i, ActiveAdmin activeAdmin) {
        boolean z;
        if (getIEDMService() == null) {
            Log.i("KnoxPolicyHelper", "handlePasswordExpirationNotification() : edms is null");
            return;
        }
        try {
            z = this.mEdmService.isEmailAdminPkg(activeAdmin.info.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            z = false;
        }
        if (this.mDPM == null) {
            this.mDPM = (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        }
        DevicePolicyManager devicePolicyManager = this.mDPM;
        if (devicePolicyManager == null || z || devicePolicyManager.isDeviceOwnerApp(activeAdmin.info.getPackageName()) || this.mDPM.isProfileOwnerApp(activeAdmin.info.getPackageName())) {
            return;
        }
        Intent intent = new Intent("com.samsung.android.knox.intent.action.NOTIFICATION_PASSWORD_EXPIRED_INTERNAL");
        intent.putExtra("pkgName", activeAdmin.info.getPackageName());
        intent.putExtra("expiration", activeAdmin.passwordExpirationDate);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.of(i));
    }

    public final boolean isAdminRemovableInternal(ComponentName componentName, int i) {
        if (getIEDMService() == null) {
            Log.d("KnoxPolicyHelper", "removeActiveAdmin() : passed EDMS.isAdminRemovableInternal() because edms is null");
            return true;
        }
        try {
            return this.mEdmService.isAdminRemovableInternal(componentName, i);
        } catch (Exception e) {
            Log.e("KnoxPolicyHelper", "removeActiveAdmin() : failed to call EDMS.isAdminRemovableInternal()", e);
            return true;
        }
    }

    public final boolean isCalledFromMDMAdmin(int i) {
        boolean z = false;
        if (getEDM() != null) {
            try {
                this.mEDM.enforceActiveAdminPermissionByContext(new ContextInfo(Binder.getCallingUid(), i), new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_SECURITY")));
                z = true;
            } catch (SecurityException unused) {
                Log.i("KnoxPolicyHelper", "isCalledFromMDMAdmin() : Not a MDM client");
            } catch (Exception e) {
                Log.i("KnoxPolicyHelper", "isCalledFromMDMAdmin() : failed to check mdm admin", e);
            }
            if (z) {
                Log.i("KnoxPolicyHelper", "isCalledFromMDMAdmin() : called");
            }
        } else {
            Log.i("KnoxPolicyHelper", "EnterpriseDeviceManager is null");
        }
        return z;
    }

    public final void removeActiveAdminFromDpm(ArrayList arrayList, int i) {
        if (getIEDMService() == null) {
            Log.i("KnoxPolicyHelper", "removeUserData() : passed EDMS.removeActiveAdminFromDpm() because edms is null");
            return;
        }
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                try {
                    this.mEdmService.removeActiveAdminFromDpm(((ActiveAdmin) it.next()).info.getComponent(), i);
                } catch (RemoteException e) {
                    Slogf.e("KnoxPolicyHelper", "failed to remove active admin from edm database " + e.getMessage());
                }
            }
        }
    }
}
