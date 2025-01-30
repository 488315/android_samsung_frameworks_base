package com.android.server.enterprise.adapterlayer;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.samsung.android.knox.SemPersonaManager;
import java.util.List;

/* loaded from: classes2.dex */
public class PersonaManagerAdapter implements IPersonaManagerAdapter {
    public static IPersonaManagerAdapter sInstance;
    public Context mContext;
    public SemPersonaManager mPersonaMgr;
    public UserManager mUserMgr;

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public boolean isLegacyClId(int i) {
        return false;
    }

    public static synchronized IPersonaManagerAdapter getInstance(Context context) {
        IPersonaManagerAdapter iPersonaManagerAdapter;
        synchronized (PersonaManagerAdapter.class) {
            if (sInstance == null && context != null) {
                sInstance = new PersonaManagerAdapter(context);
            }
            iPersonaManagerAdapter = sInstance;
        }
        return iPersonaManagerAdapter;
    }

    public PersonaManagerAdapter(Context context) {
        this.mContext = context;
    }

    public final SemPersonaManager getPersonaManager() {
        if (this.mPersonaMgr == null) {
            this.mPersonaMgr = (SemPersonaManager) this.mContext.getSystemService("persona");
        }
        return this.mPersonaMgr;
    }

    public final UserManager getUserManager() {
        if (this.mUserMgr == null) {
            this.mUserMgr = (UserManager) this.mContext.getSystemService("user");
        }
        return this.mUserMgr;
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public boolean isSecureFolderId(int i) {
        return SemPersonaManager.isSecureFolderId(i);
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public boolean isValidKnoxId(int i) {
        return SemPersonaManager.isKnoxId(i);
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public boolean isDoEnabled(int i) {
        return SemPersonaManager.isDoEnabled(i);
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public boolean isOrganizationOwnedDeviceWithManagedProfile() {
        return ((DevicePolicyManager) this.mContext.getSystemService("device_policy")).isOrganizationOwnedDeviceWithManagedProfile();
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public Bundle getKnoxInfo() {
        return SemPersonaManager.getKnoxInfo();
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public String getKnoxContainerVersionString() {
        return SemPersonaManager.getKnoxContainerVersion().toString();
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public boolean isPersonaEnabled(int i) {
        UserInfo userInfo = getUserManager().getUserInfo(i);
        return userInfo != null && userInfo.isEnabled();
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public int getFocusedUserWithActivityManager() {
        int i;
        try {
            if (getPersonaManager() != null) {
                i = getPersonaManager().getFocusedKnoxId();
            } else {
                i = ((ActivityManager.RunningTaskInfo) ActivityManager.getService().getTasks(1).get(0)).numRunning;
            }
            return i;
        } catch (Exception e) {
            Log.e("PersonaManagerAdapter", "getFocusedUserWithActivityManager() failed. so return owner id", e);
            return 0;
        }
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public boolean isKnoxVersionSupported(int i) {
        return SemPersonaManager.isKnoxVersionSupported(i);
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public boolean isLegacyContainer(int i) {
        return SemPersonaManager.isSecureFolderId(i);
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public void postPwdChangeNotificationForDeviceOwner(int i) {
        getPersonaManager().postPwdChangeNotificationForDeviceOwner(i);
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public boolean isContainerService(int i) {
        return SemPersonaManager.isContainerService(this.mContext, i);
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public boolean isDeviceOrProfileOwnerEnabled() {
        return SemPersonaManager.isDeviceOrProfileOwnerEnabled();
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public boolean isKnoxId(int i) {
        return SemPersonaManager.isKnoxId(i);
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public boolean isContainerCore(String str) {
        return "com.samsung.android.knox.containercore".equals(str);
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public boolean isContainerDesktop(String str) {
        return "com.samsung.android.knox.containerdesktop".equals(str);
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public boolean isExternalStorageEnabled(int i) {
        return SemPersonaManager.isExternalStorageEnabled(i);
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public boolean isKnoxActivated() {
        return getPersonaManager().isKnoxActivated();
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public List getKnoxIds(boolean z) {
        return getPersonaManager().getKnoxIds(z);
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public boolean isPremiumContainer(int i) {
        return SemPersonaManager.isPremiumContainer(i);
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public int getAttributes(int i) {
        return SemPersonaManager.getAttributes(i);
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public boolean isAppSeparationUserId(int i) {
        return SemPersonaManager.isAppSeparationUserId(this.mContext, i);
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public boolean isDarDualEncryptionEnabled(int i) {
        return SemPersonaManager.isDarDualEncryptionEnabled(i);
    }

    @Override // com.android.server.enterprise.adapter.IPersonaManagerAdapter
    public boolean isSamsungWorkspace(int i) {
        return SemPersonaManager.isSamsungWorkspace(i);
    }
}
