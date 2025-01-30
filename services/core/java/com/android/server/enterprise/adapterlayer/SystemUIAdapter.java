package com.android.server.enterprise.adapterlayer;

import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback;
import android.util.Log;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.jobs.XmlUtils;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.enterprise.adapter.ISystemUIAdapter;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.Utils;
import com.samsung.android.knox.ISecurityPolicy;
import com.samsung.android.knox.kiosk.IKioskMode;
import com.samsung.android.knox.restriction.IRestrictionPolicy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class SystemUIAdapter implements ISystemUIAdapter {
    public static Context mContext;
    public static EdmStorageProvider mEdmStorageProvider;
    public static SystemUIAdapter mInstance;
    public IKioskMode mKioskModeService;
    public IRestrictionPolicy mRestrictionPolicyService;
    public boolean isFistcalled = true;
    public int mRegisteredCount = 0;
    public boolean isCallbackDied = true;
    public int adapterUserId = 0;
    public final HashMap mCallbacks = new HashMap();
    public final IBinder mToken = new Binder();
    public IStatusBarService mStatusBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));

    public class SystemUIAdapterCallbackDeathRecipient implements IBinder.DeathRecipient {
        public final int key;

        public SystemUIAdapterCallbackDeathRecipient(int i) {
            this.key = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.e("SystemUIAdapter", "binderDied() key = " + this.key);
            if (SystemUIAdapter.this.mCallbacks.containsKey(Integer.valueOf(this.key))) {
                ((ISystemUIAdapterCallback) SystemUIAdapter.this.mCallbacks.get(Integer.valueOf(this.key))).asBinder().unlinkToDeath(this, 0);
                SystemUIAdapter.this.mCallbacks.remove(Integer.valueOf(this.key));
            }
            if (SystemUIAdapter.this.mCallbacks.size() == 0) {
                SystemUIAdapter.this.isCallbackDied = true;
            }
        }
    }

    public boolean registerSystemUICallback(ISystemUIAdapterCallback iSystemUIAdapterCallback) {
        Log.d("SystemUIAdapter", "registerSystemUICallback() is called " + iSystemUIAdapterCallback);
        if (!isCalledFromSystemUI()) {
            Log.d("SystemUIAdapter", "registerSystemUICallback() has failed because it's only allowed to call by SystemUI ");
            return false;
        }
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(null);
        if (callingOrCurrentUserId != 0) {
            Log.d("SystemUIAdapter", "registerSystemUICallback() has failed because it's only allowed user_system, but userId = " + callingOrCurrentUserId);
            return false;
        }
        if (iSystemUIAdapterCallback != null) {
            try {
                int i = this.mRegisteredCount + 1;
                this.mRegisteredCount = i;
                this.mCallbacks.put(Integer.valueOf(i), iSystemUIAdapterCallback);
                iSystemUIAdapterCallback.asBinder().linkToDeath(new SystemUIAdapterCallbackDeathRecipient(i), 0);
                Log.d("SystemUIAdapter", "registerSystemUICallback() successfully added");
            } catch (Exception unused) {
            }
            this.isCallbackDied = false;
            Log.d("SystemUIAdapter", "registerSystemUICallback() callback has registered. " + this.mRegisteredCount);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                updateSystemUIMonitor(0);
            } catch (Exception unused2) {
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            onKeyguardLaunched();
            return true;
        }
        Log.d("SystemUIAdapter", "registerSystemUICallback() has failed.");
        return false;
    }

    public int getRegisteredCount() {
        return this.mRegisteredCount;
    }

    public boolean isKnoxStateMonitorRegistered() {
        return !this.isCallbackDied && this.mCallbacks.size() > 0;
    }

    public final void onKeyguardLaunched() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (getSecurityPolicy() != null && this.isFistcalled) {
                    this.isFistcalled = false;
                    getSecurityPolicy().onKeyguardLaunched();
                }
            } catch (Exception e) {
                Log.e("SystemUIAdapter", "onKeyguardLaunched() has failed.", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void updateSystemUIMonitor(int i) {
        if (!isCalledFromSystem()) {
            Log.d("SystemUIAdapter", "updateSystemUIMonitor() has failed because not system call, userId = " + i);
            return;
        }
        if (i >= 0) {
            if (this.adapterUserId != i) {
                Log.d("SystemUIAdapter", "updateSystemUIMonitor() userId has changed. " + this.adapterUserId + " -> " + i);
            }
            this.adapterUserId = i;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (mContext != null) {
                    setLockedIccIdsAsUser(i, getIccIdListByAdmin());
                    Intent intent = new Intent("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL");
                    intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", this.adapterUserId);
                    mContext.sendBroadcastAsUser(intent, new UserHandle(0));
                } else {
                    Log.d("SystemUIAdapter", "updateSystemUIMonitor() cannot call because context is null. ");
                }
            } catch (Exception e) {
                Log.e("SystemUIAdapter", "updateSystemUIMonitor() userId = " + i, e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getAdapterId() {
        return this.adapterUserId;
    }

    public final IStatusBarService getStatusBarService() {
        if (this.mStatusBarService == null) {
            this.mStatusBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        }
        return this.mStatusBarService;
    }

    public final ISecurityPolicy getSecurityPolicy() {
        return ISecurityPolicy.Stub.asInterface(ServiceManager.getService("security_policy"));
    }

    public final IKioskMode getKioskMode() {
        if (this.mKioskModeService == null) {
            this.mKioskModeService = IKioskMode.Stub.asInterface(ServiceManager.getService("kioskmode"));
        }
        return this.mKioskModeService;
    }

    public final IRestrictionPolicy getRestrictionPolicy() {
        if (this.mRestrictionPolicyService == null) {
            this.mRestrictionPolicyService = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
        }
        return this.mRestrictionPolicyService;
    }

    public static synchronized SystemUIAdapter getInstance(Context context) {
        SystemUIAdapter systemUIAdapter;
        synchronized (SystemUIAdapter.class) {
            if (mInstance == null && context != null) {
                mContext = context;
                mEdmStorageProvider = new EdmStorageProvider(context);
                mInstance = new SystemUIAdapter();
            }
            systemUIAdapter = mInstance;
        }
        return systemUIAdapter;
    }

    public final boolean isCalledFromSystem() {
        if (Binder.getCallingPid() == Process.myPid()) {
            return true;
        }
        Log.e("SystemUIAdapter", "isCalledFromSystem() : no permission because non-system : " + Binder.getCallingPid());
        return false;
    }

    public final boolean isCalledFromSystemUI() {
        int i;
        int callingUid = Binder.getCallingUid();
        try {
            i = mContext.getPackageManager().getPackageUidAsUser("com.android.systemui", 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("SystemUIAdapter", "isCalledFromSystemUI() : Unable to resolve SystemUI's UID.", e);
            i = -1;
        }
        int appId = UserHandle.getAppId(callingUid);
        String nameForUid = mContext.getPackageManager().getNameForUid(callingUid);
        if (nameForUid == null) {
            return false;
        }
        int lastIndexOf = nameForUid.lastIndexOf(XmlUtils.STRING_ARRAY_SEPARATOR);
        if (lastIndexOf != -1) {
            nameForUid = nameForUid.substring(0, lastIndexOf);
        }
        return nameForUid.equals("android.uid.systemui") && appId == i;
    }

    public final int getCurrentUserId() {
        int i;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                i = ActivityManager.getCurrentUser();
                try {
                    if (i != this.adapterUserId) {
                        updateSystemUIMonitor(i);
                    }
                } catch (Exception unused) {
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Exception unused2) {
            i = 0;
        }
        return i;
    }

    public final String[] getIccIdListByAdmin() {
        List values = mEdmStorageProvider.getValues("SimTable", new String[]{"SimIccId"}, new ContentValues());
        int size = values.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = ((ContentValues) values.get(i)).getAsString("SimIccId");
        }
        return strArr;
    }

    public boolean clearAllNotificationsAsUser(int i) {
        Log.d("SystemUIAdapter", "clearAllNotificationsAsUser() userId = " + i);
        boolean z = false;
        if (!isCalledFromSystem()) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                IStatusBarService statusBarService = getStatusBarService();
                if (statusBarService != null) {
                    statusBarService.onClearAllNotifications(i);
                    z = true;
                }
            } catch (Exception e) {
                Log.e("SystemUIAdapter", "clearAllNotificationsAsUser failed with Exception", e);
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setStatusBarHiddenAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setStatusBarHiddenAsUser() userId = " + i + ", hidden = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                if (z) {
                    disableStatusBar(true, null);
                } else if (getRestrictionPolicy() != null && getRestrictionPolicy().isStatusBarExpansionAllowedAsUser(false, 0)) {
                    disableStatusBar(false, null);
                }
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setStatusBarHidden(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setStatusBarHidden() failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e("SystemUIAdapter", "setStatusBarHidden() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public void setNavigationBarHiddenAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setNavigationBarHiddenAsUser() userId = " + i + ", hidden = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setNavigationBarHidden(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setNavigationBarHidden() failed with RemoteException", e);
            } catch (NullPointerException e2) {
                Log.e("SystemUIAdapter", "setNavigationBarHidden() failed with NullPointerException.", e2);
            } catch (Exception unused) {
            }
        }
    }

    public void setKioskModeEnabledAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setKioskModeEnabled() userId = " + i + ", enable = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setKioskModeEnabled(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setKioskModeEnabled() failed with RemoteException", e);
            } catch (NullPointerException e2) {
                Log.e("SystemUIAdapter", "setKioskModeEnabled() failed with NullPointerException.", e2);
            } catch (Exception unused) {
            }
        }
    }

    public void setMaximumFailedPasswordsForDisableAsUser(int i, int i2, String str) {
        Log.d("SystemUIAdapter", "setMaximumFailedPasswordsForDisableAsUser() userId = " + i + ", num = " + i2 + ", pkgName = " + str);
        if (isCalledFromSystem()) {
            boolean isSeparateProfileChallengeEnabled = new LockPatternUtils(mContext).isSeparateProfileChallengeEnabled(i);
            Log.d("SystemUIAdapter", "setMaximumFailedPasswordsForDisableAsUser() isSeparateProfileChallengeEnabled = " + isSeparateProfileChallengeEnabled);
            if (i == getCurrentUserId() || !isSeparateProfileChallengeEnabled) {
                try {
                    String str2 = SystemProperties.get("ro.organization_owned");
                    boolean z = str2 != null && str2.equals("true");
                    Iterator it = this.mCallbacks.entrySet().iterator();
                    while (it.hasNext()) {
                        ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                        if (iSystemUIAdapterCallback != null) {
                            if (i != 0 && !isSeparateProfileChallengeEnabled && !z) {
                                iSystemUIAdapterCallback.setMaximumFailedPasswordsForProfileDisable(i2);
                            } else {
                                iSystemUIAdapterCallback.setMaximumFailedPasswordsForDisable(i2, str);
                            }
                        }
                    }
                } catch (RemoteException e) {
                    Log.e("SystemUIAdapter", "setMaximumFailedPasswordsForDisable() Failed with RemoteException", e);
                } catch (NullPointerException unused) {
                    Log.e("SystemUIAdapter", "setMaximumFailedPasswordsForDisable() failed with NullPointerException.");
                } catch (Exception unused2) {
                }
            }
        }
    }

    public void setPwdChangeRequestedAsUser(int i, int i2) {
        Log.d("SystemUIAdapter", "setPwdChangeRequestedAsUser() userId = " + i + ", flag = " + i2);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setPwdChangeRequested(i2);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setPwdChangeRequested() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e("SystemUIAdapter", "setPwdChangeRequested() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public void excludeExternalStorageForFailedPasswordsWipeAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "excludeExternalStorageForFailedPasswordsWipeAsUser() userId = " + i + ", exclude = " + z);
        if (isCalledFromSystem()) {
            boolean isSeparateProfileChallengeEnabled = new LockPatternUtils(mContext).isSeparateProfileChallengeEnabled(i);
            Log.d("SystemUIAdapter", "setMultifactorAuthEnabled() excludeExternalStorageForFailedPasswordsWipeAsUser = " + isSeparateProfileChallengeEnabled);
            if (i == getCurrentUserId() || !isSeparateProfileChallengeEnabled) {
                try {
                    Iterator it = this.mCallbacks.entrySet().iterator();
                    while (it.hasNext()) {
                        ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                        if (iSystemUIAdapterCallback != null) {
                            iSystemUIAdapterCallback.excludeExternalStorageForFailedPasswordsWipe(z);
                        }
                    }
                } catch (RemoteException e) {
                    Log.e("SystemUIAdapter", "excludeExternalStorageForFailedPasswordsWipe() Failed with RemoteException", e);
                } catch (NullPointerException unused) {
                    Log.e("SystemUIAdapter", "excludeExternalStorageForFailedPasswordsWipe() failed with NullPointerException.");
                } catch (Exception unused2) {
                }
            }
        }
    }

    public void setPasswordLockDelayAsUser(int i, int i2) {
        Log.d("SystemUIAdapter", "setPasswordLockDelayAsUser() userId = " + i + ", time = " + i2);
        if (isCalledFromSystem()) {
            boolean isSeparateProfileChallengeEnabled = new LockPatternUtils(mContext).isSeparateProfileChallengeEnabled(i);
            Log.d("SystemUIAdapter", "setMultifactorAuthEnabled() setPasswordLockDelayAsUser = " + isSeparateProfileChallengeEnabled);
            if (i == getCurrentUserId() || !isSeparateProfileChallengeEnabled) {
                try {
                    Iterator it = this.mCallbacks.entrySet().iterator();
                    while (it.hasNext()) {
                        ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                        if (iSystemUIAdapterCallback != null) {
                            iSystemUIAdapterCallback.setPasswordLockDelay(i2);
                        }
                    }
                } catch (RemoteException e) {
                    Log.e("SystemUIAdapter", "setPasswordLockDelay() Failed with RemoteException", e);
                } catch (NullPointerException unused) {
                    Log.e("SystemUIAdapter", "setPasswordLockDelay() failed with NullPointerException.");
                } catch (Exception unused2) {
                }
            }
        }
    }

    public void setPasswordVisibilityEnabledAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setPasswordVisibilityEnabledAsUser() userId = " + i + ", allow = " + z);
        if (isCalledFromSystem()) {
            boolean isSeparateProfileChallengeEnabled = new LockPatternUtils(mContext).isSeparateProfileChallengeEnabled(i);
            Log.d("SystemUIAdapter", "setPasswordVisibilityEnabledAsUser() isSeparateProfileChallengeEnabled = " + isSeparateProfileChallengeEnabled);
            if (i == getCurrentUserId() || !isSeparateProfileChallengeEnabled) {
                try {
                    Iterator it = this.mCallbacks.entrySet().iterator();
                    while (it.hasNext()) {
                        ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                        if (iSystemUIAdapterCallback != null) {
                            iSystemUIAdapterCallback.setPasswordVisibilityEnabled(z);
                        }
                    }
                } catch (RemoteException e) {
                    Log.e("SystemUIAdapter", "setPasswordVisibilityEnabled() Failed with RemoteException", e);
                } catch (NullPointerException unused) {
                    Log.e("SystemUIAdapter", "setPasswordVisibilityEnabled() failed with NullPointerException.");
                } catch (Exception unused2) {
                }
            }
        }
    }

    public void setSettingsChangeAllowedAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setSettingsChangeAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setSettingsChangeAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setSettingsChangeAsUser() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e("SystemUIAdapter", "setSettingsChangeAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public void setAirplaneModeAllowedAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setAirplaneModeAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setAirplaneModeAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setAirplaneModeAllowedAsUser() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e("SystemUIAdapter", "setAirplaneModeAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public void setStatusBarExpansionAllowedAsUser(int i, boolean z, String str) {
        Log.d("SystemUIAdapter", "setStatusBarExpansionAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                if (!z) {
                    disableStatusBar(true, str);
                } else if (getKioskMode() != null && !getKioskMode().isStatusBarHiddenAsUser(0)) {
                    disableStatusBar(false, str);
                }
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setStatusBarExpansionAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setStatusBarExpansionAllowedAsUser() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e("SystemUIAdapter", "setStatusBarExpansionAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public void setCellularDataAllowedAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setCellularDataAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setCellularDataAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setCellularDataAllowedAsUser() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e("SystemUIAdapter", "setCellularDataAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public void setWifiTetheringAllowedAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setWifiTetheringAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setWifiTetheringAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setWifiTetheringAllowedAsUser() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e("SystemUIAdapter", "setWifiTetheringAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public void setCameraAllowedAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setCameraAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setCameraAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setCameraAllowedAsUser() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e("SystemUIAdapter", "setCameraAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public void setFaceRecognitionEvenCameraBlockedAllowedAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setFaceRecognitionEvenCameraBlockedAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setFaceRecognitionEvenCameraBlockedAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setFaceRecognitionEvenCameraBlockedAllowedAsUser() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e("SystemUIAdapter", "setFaceRecognitionEvenCameraBlockedAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    @Override // com.android.server.enterprise.adapter.ISystemUIAdapter
    public void setBluetoothAllowedAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setBluetoothAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setBluetoothAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setBluetoothAllowedAsUser() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e("SystemUIAdapter", "setBluetoothAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public void setNFCStateChangeAllowedAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setNFCStateChangeAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setNFCStateChangeAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setNFCStateChangeAllowedAsUser() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e("SystemUIAdapter", "setNFCStateChangeAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public void setRoamingDataAllowedAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setRoamingDataAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setRoamingAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setRoamingDataAllowedAsUser() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e("SystemUIAdapter", "setRoamingDataAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public void setWifiStateChangeAllowedAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setWifiStateChangeAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setWifiStateChangeAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setWifiStateChangeAllowedAsUser() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e("SystemUIAdapter", "setWifiStateChangeAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public void setWifiAllowedAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setWifiAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setWifiAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setWifiAllowedAsUser() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e("SystemUIAdapter", "setWifiAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public void setLocationProviderAllowedAsUser(int i, String str, boolean z) {
        Log.d("SystemUIAdapter", "setLocationProviderAllowedAsUser() userId = " + i + ", provider = " + str + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setLocationProviderAllowed(str, z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setLocationProviderAllowedAsUser() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e("SystemUIAdapter", "setLocationProviderAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public void setGPSStateChangeAllowedAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setGPSStateChangeAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setGPSStateChangeAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setGPSStateChangeAllowedAsUser() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e("SystemUIAdapter", "setGPSStateChangeAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public void setLockedIccIdsAsUser(int i, String[] strArr) {
        Log.d("SystemUIAdapter", "setLockedIccIdsAsUser() userId = " + i);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setLockedIccIds(strArr);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setLockedIccIdsAsUser() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e("SystemUIAdapter", "setLockedIccIdsAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public void setLockscreenInvisibleOverlayAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setLockscreenInvisibleOverlayAsUser() userId = " + i);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setLockscreenInvisibleOverlay(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setLockscreenInvisibleOverlayAsUser() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e("SystemUIAdapter", "setLockscreenInvisibleOverlayAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public void setLockscreenWallpaperAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setLockscreenWallpaperAsUser() userId = " + i);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setLockscreenWallpaper(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setLockscreenWallpaperAsUser() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e("SystemUIAdapter", "setLockscreenWallpaperAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public void setApplicationNameControlEnabledAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setApplicationNameEnabledAsUser()");
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setApplicationNameControlEnabled(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setApplicationNameEnabledAsUser() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e("SystemUIAdapter", "setApplicationNameEnabledAsUser() failed with NullPointerException.");
            } catch (Exception e2) {
                Log.e("SystemUIAdapter", "setApplicationNameEnabledAsUser() Failed with Exception", e2);
            }
        }
    }

    public void setMultifactorAuthEnabled(int i, boolean z) {
        Log.d("SystemUIAdapter", "setMultifactorAuthEnabled()");
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setMultifactorAuthEnabled(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setMultifactorAuthEnabled() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e("SystemUIAdapter", "setMultifactorAuthEnabled() failed with NullPointerException.");
            } catch (Exception e2) {
                Log.e("SystemUIAdapter", "setMultifactorAuthEnabled() Failed with Exception", e2);
            }
        }
    }

    public void setAdminLockEnabled(int i, boolean z, boolean z2) {
        Log.d("SystemUIAdapter", "setAdminLockEnabled()");
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback = (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setAdminLock(z, z2);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setAdminLockEnabled() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e("SystemUIAdapter", "setAdminLockEnabled() failed with NullPointerException.");
            } catch (Exception e2) {
                Log.e("SystemUIAdapter", "setAdminLockEnabled() Failed with Exception", e2);
            }
        }
    }

    public final void disableStatusBar(boolean z, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                IStatusBarService statusBarService = getStatusBarService();
                if (statusBarService != null) {
                    if (!z) {
                        statusBarService.disableForUser(0, this.mToken, "knoxmdm_key_statusbar_disable_expansion : " + str, 0);
                        statusBarService.disable2ForUser(0, this.mToken, "knoxmdm_key_statusbar_disable_expansion : " + str, 0);
                    } else {
                        statusBarService.disableForUser(65536, this.mToken, "knoxmdm_key_statusbar_disable_expansion : " + str, 0);
                        statusBarService.disable2ForUser(1, this.mToken, "knoxmdm_key_statusbar_disable_expansion : " + str, 0);
                    }
                }
            } catch (Exception e) {
                Log.e("SystemUIAdapter", "setStatusBarExpansionAllowedAsUser() failed.", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
