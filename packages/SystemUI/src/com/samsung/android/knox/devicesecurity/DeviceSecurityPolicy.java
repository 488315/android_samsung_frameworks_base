package com.samsung.android.knox.devicesecurity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.AccessController;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceAdminInfo;
import com.samsung.android.knox.IMiscPolicy;
import com.samsung.android.knox.ISecurityPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class DeviceSecurityPolicy {
    public static String TAG = "DeviceSecurityPolicy";
    public static final int WIPE_EXTERNAL_MEMORY = 2;
    public static final int WIPE_INTERNAL_EXTERNAL_MEMORY = 3;
    public static final int WIPE_INTERNAL_MEMORY = 1;
    public Context mContext;
    public ContextInfo mContextInfo;
    public IMiscPolicy mMiscService;
    public ISecurityPolicy mSecurityService;

    public DeviceSecurityPolicy(ContextInfo contextInfo, Context context) {
        this.mContextInfo = contextInfo;
        this.mContext = context;
    }

    public boolean addClipboardTextData(String str) throws SecurityException {
        AccessController.throwIfParentInstance(this.mContextInfo, "addClipboardTextData");
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceSecurityPolicy.addClipboardTextData");
        Context context = this.mContext;
        if (context == null) {
            return false;
        }
        enforceOwnerOnlyAndClipboardPermission(context);
        ((ClipboardManager) this.mContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("simple text", str));
        return true;
    }

    public boolean clearClipboardData() {
        AccessController.throwIfParentInstance(this.mContextInfo, "clearClipboardData");
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceSecurityPolicy.clearClipboardData");
        return false;
    }

    public final void enforceOwnerOnlyAndClipboardPermission(Context context) throws SecurityException {
        AccessController.enforceOwnerOnlyAndActiveAdminPermission(this.mContextInfo, new ArrayList(Arrays.asList(EnterpriseDeviceAdminInfo.USES_POLICY_KNOX_CLIPBOARD_TAG)));
    }

    public String getClipboardTextData() throws SecurityException {
        AccessController.throwIfParentInstance(this.mContextInfo, "getClipboardTextData");
        Context context = this.mContext;
        if (context != null) {
            enforceOwnerOnlyAndClipboardPermission(context);
            ClipData primaryClip = ((ClipboardManager) this.mContext.getSystemService("clipboard")).getPrimaryClip();
            if (primaryClip != null && primaryClip.getItemAt(0) != null) {
                return primaryClip.getItemAt(0).getText().toString();
            }
        }
        return null;
    }

    public final IMiscPolicy getMiscService() {
        if (this.mMiscService == null) {
            this.mMiscService = IMiscPolicy.Stub.asInterface(ServiceManager.getService("misc_policy"));
        }
        return this.mMiscService;
    }

    public boolean getRequireDeviceEncryption(ComponentName componentName) {
        if (getSecurityService() == null) {
            return false;
        }
        try {
            return this.mSecurityService.getRequireDeviceEncryption(this.mContextInfo, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with DeviceSecurityPolicy", e);
            return false;
        }
    }

    public boolean getRequireStorageCardEncryption(ComponentName componentName) {
        if (getSecurityService() == null) {
            return false;
        }
        try {
            return this.mSecurityService.getRequireStorageCardEncryption(this.mContextInfo, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with DeviceSecurityPolicy", e);
            return false;
        }
    }

    public final ISecurityPolicy getSecurityService() {
        if (this.mSecurityService == null) {
            this.mSecurityService = ISecurityPolicy.Stub.asInterface(ServiceManager.getService("security_policy"));
        }
        return this.mSecurityService;
    }

    public boolean isExternalStorageEncrypted() {
        if (getSecurityService() == null) {
            return false;
        }
        try {
            return this.mSecurityService.isExternalStorageEncrypted(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with DeviceSecurityPolicy", e);
            return false;
        }
    }

    public boolean isInternalStorageEncrypted() {
        AccessController.throwIfParentInstance(this.mContextInfo, "isInternalStorageEncrypted");
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceSecurityPolicy.isInternalStorageEncrypted");
        if (getSecurityService() == null) {
            return false;
        }
        try {
            return this.mSecurityService.isInternalStorageEncrypted(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with DeviceSecurityPolicy", e);
            return false;
        }
    }

    public void setExternalStorageEncryption(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceSecurityPolicy.setExternalStorageEncryption");
        if (getSecurityService() != null) {
            try {
                this.mSecurityService.setExternalStorageEncryption(this.mContextInfo, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with DeviceSecurityPolicy", e);
            }
        }
    }

    public void setInternalStorageEncryption(boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "setInternalStorageEncryption");
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceSecurityPolicy.setInternalStorageEncryption");
        if (getSecurityService() != null) {
            try {
                this.mSecurityService.setInternalStorageEncryption(this.mContextInfo, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with DeviceSecurityPolicy", e);
            }
        }
    }

    public void setRequireDeviceEncryption(ComponentName componentName, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceSecurityPolicy.setRequireDeviceEncryption");
        if (getSecurityService() != null) {
            try {
                this.mSecurityService.setRequireDeviceEncryption(this.mContextInfo, componentName, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with DeviceSecurityPolicy", e);
            }
        }
    }

    public void setRequireStorageCardEncryption(ComponentName componentName, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceSecurityPolicy.setRequireStorageCardEncryption");
        if (getSecurityService() != null) {
            try {
                this.mSecurityService.setRequireStorageCardEncryption(this.mContextInfo, componentName, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with DeviceSecurityPolicy", e);
            }
        }
    }

    public boolean wipeDevice(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceSecurityPolicy.wipeDevice");
        if (getSecurityService() != null) {
            try {
                this.mSecurityService.wipeDevice(this.mContextInfo, i);
                return false;
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with DeviceSecurityPolicy", e);
            }
        }
        return false;
    }
}
