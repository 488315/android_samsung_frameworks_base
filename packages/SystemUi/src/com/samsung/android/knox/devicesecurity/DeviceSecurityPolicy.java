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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DeviceSecurityPolicy {
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

    public final boolean addClipboardTextData(String str) {
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

    public final boolean clearClipboardData() {
        AccessController.throwIfParentInstance(this.mContextInfo, "clearClipboardData");
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceSecurityPolicy.clearClipboardData");
        return false;
    }

    public final void enforceOwnerOnlyAndClipboardPermission(Context context) {
        AccessController.enforceOwnerOnlyAndActiveAdminPermission(this.mContextInfo, new ArrayList(Arrays.asList(EnterpriseDeviceAdminInfo.USES_POLICY_KNOX_CLIPBOARD_TAG)));
    }

    public final String getClipboardTextData() {
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

    public final boolean getRequireDeviceEncryption(ComponentName componentName) {
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

    public final boolean getRequireStorageCardEncryption(ComponentName componentName) {
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

    public final boolean isExternalStorageEncrypted() {
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

    public final boolean isInternalStorageEncrypted() {
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

    public final void setExternalStorageEncryption(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceSecurityPolicy.setExternalStorageEncryption");
        if (getSecurityService() != null) {
            try {
                this.mSecurityService.setExternalStorageEncryption(this.mContextInfo, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with DeviceSecurityPolicy", e);
            }
        }
    }

    public final void setInternalStorageEncryption(boolean z) {
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

    public final void setRequireDeviceEncryption(ComponentName componentName, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceSecurityPolicy.setRequireDeviceEncryption");
        if (getSecurityService() != null) {
            try {
                this.mSecurityService.setRequireDeviceEncryption(this.mContextInfo, componentName, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with DeviceSecurityPolicy", e);
            }
        }
    }

    public final void setRequireStorageCardEncryption(ComponentName componentName, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceSecurityPolicy.setRequireStorageCardEncryption");
        if (getSecurityService() != null) {
            try {
                this.mSecurityService.setRequireStorageCardEncryption(this.mContextInfo, componentName, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with DeviceSecurityPolicy", e);
            }
        }
    }

    public final boolean wipeDevice(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceSecurityPolicy.wipeDevice");
        if (getSecurityService() == null) {
            return false;
        }
        try {
            this.mSecurityService.wipeDevice(this.mContextInfo, i);
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with DeviceSecurityPolicy", e);
            return false;
        }
    }
}
