package com.android.server.enterprise.adapterlayer;

import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.enterprise.adapter.ISystemUIAdapter;
import com.android.server.enterprise.storage.EdmStorageProvider;

import com.samsung.android.knox.kiosk.IKioskMode;
import com.samsung.android.knox.restriction.IRestrictionPolicy;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SystemUIAdapter implements ISystemUIAdapter {
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
    public IStatusBarService mStatusBarService =
            IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SystemUIAdapterCallbackDeathRecipient implements IBinder.DeathRecipient {
        public final int key;

        public SystemUIAdapterCallbackDeathRecipient(int i) {
            this.key = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Log.e("SystemUIAdapter", "binderDied() key = " + this.key);
            if (SystemUIAdapter.this.mCallbacks.containsKey(Integer.valueOf(this.key))) {
                ((ISystemUIAdapterCallback)
                                SystemUIAdapter.this.mCallbacks.get(Integer.valueOf(this.key)))
                        .asBinder()
                        .unlinkToDeath(this, 0);
                SystemUIAdapter.this.mCallbacks.remove(Integer.valueOf(this.key));
            }
            if (SystemUIAdapter.this.mCallbacks.size() == 0) {
                SystemUIAdapter.this.isCallbackDied = true;
            }
        }
    }

    public static String[] getIccIdListByAdmin() {
        ArrayList arrayList =
                (ArrayList)
                        mEdmStorageProvider.getValues(
                                "SimTable", new String[] {"SimIccId"}, new ContentValues());
        int size = arrayList.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = ((ContentValues) arrayList.get(i)).getAsString("SimIccId");
        }
        return strArr;
    }

    public static synchronized SystemUIAdapter getInstance(Context context) {
        SystemUIAdapter systemUIAdapter;
        synchronized (SystemUIAdapter.class) {
            try {
                if (mInstance == null && context != null) {
                    mContext = context;
                    mEdmStorageProvider = new EdmStorageProvider(context);
                    mInstance = new SystemUIAdapter();
                }
                systemUIAdapter = mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return systemUIAdapter;
    }

    public static boolean isCalledFromSystem() {
        if (Binder.getCallingPid() == Process.myPid()) {
            return true;
        }
        Log.e(
                "SystemUIAdapter",
                "isCalledFromSystem() : no permission because non-system : "
                        + Binder.getCallingPid());
        return false;
    }

    public final boolean clearAllNotificationsAsUser(int i) {
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

    public final void disableNavBarForGesture(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                IStatusBarService statusBarService = getStatusBarService();
                if (statusBarService != null) {
                    statusBarService.disableForUser(
                            z ? 18874368 : 0,
                            this.mToken,
                            "knoxmdm_key_statusbar_disable_expansion : null",
                            0);
                }
            } catch (Exception e) {
                Log.e("SystemUIAdapter", "setStatusBarExpansionAllowedAsUser() failed.", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void disableStatusBar(String str, boolean z) {
        int i;
        int i2;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                IStatusBarService statusBarService = getStatusBarService();
                if (statusBarService != null) {
                    if (z) {
                        i = EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT;
                        i2 = 1;
                    } else {
                        i = 0;
                        i2 = 0;
                    }
                    statusBarService.disableForUser(
                            i, this.mToken, "knoxmdm_key_statusbar_disable_expansion : " + str, 0);
                    statusBarService.disable2ForUser(
                            i2, this.mToken, "knoxmdm_key_statusbar_disable_expansion : " + str, 0);
                }
            } catch (Exception e) {
                Log.e("SystemUIAdapter", "setStatusBarExpansionAllowedAsUser() failed.", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
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
            } catch (Exception unused2) {
                i = 0;
            }
            return i;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final IStatusBarService getStatusBarService() {
        if (this.mStatusBarService == null) {
            this.mStatusBarService =
                    IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        }
        return this.mStatusBarService;
    }

    public final void setAdminLockEnabled(int i, boolean z, boolean z2) {
        Log.d("SystemUIAdapter", "setAdminLockEnabled()");
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback =
                            (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
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

    public final void setAirplaneModeAllowedAsUser(int i, boolean z) {
        Log.d(
                "SystemUIAdapter",
                "setAirplaneModeAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback =
                            (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setAirplaneModeAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e(
                        "SystemUIAdapter",
                        "setAirplaneModeAllowedAsUser() Failed with RemoteException",
                        e);
            } catch (NullPointerException unused) {
                Log.e(
                        "SystemUIAdapter",
                        "setAirplaneModeAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public final void setApplicationNameControlEnabledAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setApplicationNameEnabledAsUser()");
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback =
                            (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setApplicationNameControlEnabled(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e(
                        "SystemUIAdapter",
                        "setApplicationNameEnabledAsUser() Failed with RemoteException",
                        e);
            } catch (NullPointerException unused) {
                Log.e(
                        "SystemUIAdapter",
                        "setApplicationNameEnabledAsUser() failed with NullPointerException.");
            } catch (Exception e2) {
                Log.e(
                        "SystemUIAdapter",
                        "setApplicationNameEnabledAsUser() Failed with Exception",
                        e2);
            }
        }
    }

    public final void setBluetoothAllowedAsUser(int i, boolean z) {
        Log.d(
                "SystemUIAdapter",
                "setBluetoothAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback =
                            (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setBluetoothAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e(
                        "SystemUIAdapter",
                        "setBluetoothAllowedAsUser() Failed with RemoteException",
                        e);
            } catch (NullPointerException unused) {
                Log.e(
                        "SystemUIAdapter",
                        "setBluetoothAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public final void setCameraAllowedAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setCameraAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback =
                            (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setCameraAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setCameraAllowedAsUser() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e(
                        "SystemUIAdapter",
                        "setCameraAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public final void setCellularDataAllowedAsUser(int i, boolean z) {
        Log.d(
                "SystemUIAdapter",
                "setCellularDataAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback =
                            (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setCellularDataAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e(
                        "SystemUIAdapter",
                        "setCellularDataAllowedAsUser() Failed with RemoteException",
                        e);
            } catch (NullPointerException unused) {
                Log.e(
                        "SystemUIAdapter",
                        "setCellularDataAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public final void setFaceRecognitionEvenCameraBlockedAllowedAsUser(int i, boolean z) {
        Log.d(
                "SystemUIAdapter",
                "setFaceRecognitionEvenCameraBlockedAllowedAsUser() userId = "
                        + i
                        + ", isAllowed = "
                        + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback =
                            (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setFaceRecognitionEvenCameraBlockedAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e(
                        "SystemUIAdapter",
                        "setFaceRecognitionEvenCameraBlockedAllowedAsUser() Failed with"
                            + " RemoteException",
                        e);
            } catch (NullPointerException unused) {
                Log.e(
                        "SystemUIAdapter",
                        "setFaceRecognitionEvenCameraBlockedAllowedAsUser() failed with"
                            + " NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public final void setGPSStateChangeAllowedAsUser(int i, boolean z) {
        Log.d(
                "SystemUIAdapter",
                "setGPSStateChangeAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback =
                            (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setGPSStateChangeAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e(
                        "SystemUIAdapter",
                        "setGPSStateChangeAllowedAsUser() Failed with RemoteException",
                        e);
            } catch (NullPointerException unused) {
                Log.e(
                        "SystemUIAdapter",
                        "setGPSStateChangeAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public final void setKioskModeEnabledAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setKioskModeEnabled() userId = " + i + ", enable = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback =
                            (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setKioskModeEnabled(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setKioskModeEnabled() failed with RemoteException", e);
            } catch (NullPointerException e2) {
                Log.e(
                        "SystemUIAdapter",
                        "setKioskModeEnabled() failed with NullPointerException.",
                        e2);
            } catch (Exception unused) {
            }
        }
    }

    public final void setLocationProviderAllowedAsUser(int i, String str, boolean z) {
        StringBuilder m =
                DirEncryptService$$ExternalSyntheticOutline0.m(
                        i,
                        "setLocationProviderAllowedAsUser() userId = ",
                        ", provider = ",
                        str,
                        ", isAllowed = ");
        m.append(z);
        Log.d("SystemUIAdapter", m.toString());
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback =
                            (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setLocationProviderAllowed(str, z);
                    }
                }
            } catch (RemoteException e) {
                Log.e(
                        "SystemUIAdapter",
                        "setLocationProviderAllowedAsUser() Failed with RemoteException",
                        e);
            } catch (NullPointerException unused) {
                Log.e(
                        "SystemUIAdapter",
                        "setLocationProviderAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public final void setLockedIccIdsAsUser(int i, String[] strArr) {
        Log.d("SystemUIAdapter", "setLockedIccIdsAsUser() userId = " + i);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback =
                            (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setLockedIccIds(strArr);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setLockedIccIdsAsUser() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e(
                        "SystemUIAdapter",
                        "setLockedIccIdsAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public final void setLockscreenInvisibleOverlayAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setLockscreenInvisibleOverlayAsUser() userId = " + i);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback =
                            (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setLockscreenInvisibleOverlay(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e(
                        "SystemUIAdapter",
                        "setLockscreenInvisibleOverlayAsUser() Failed with RemoteException",
                        e);
            } catch (NullPointerException unused) {
                Log.e(
                        "SystemUIAdapter",
                        "setLockscreenInvisibleOverlayAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public final void setLockscreenWallpaperAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setLockscreenWallpaperAsUser() userId = " + i);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback =
                            (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setLockscreenWallpaper(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e(
                        "SystemUIAdapter",
                        "setLockscreenWallpaperAsUser() Failed with RemoteException",
                        e);
            } catch (NullPointerException unused) {
                Log.e(
                        "SystemUIAdapter",
                        "setLockscreenWallpaperAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public final void setMaximumFailedPasswordsForDisableAsUser(int i, int i2, String str) {
        StringBuilder m =
                ArrayUtils$$ExternalSyntheticOutline0.m(
                        i,
                        i2,
                        "setMaximumFailedPasswordsForDisableAsUser() userId = ",
                        ", num = ",
                        ", pkgName = ");
        m.append(str);
        Log.d("SystemUIAdapter", m.toString());
        if (isCalledFromSystem()) {
            boolean isSeparateProfileChallengeEnabled =
                    new LockPatternUtils(mContext).isSeparateProfileChallengeEnabled(i);
            Log.d(
                    "SystemUIAdapter",
                    "setMaximumFailedPasswordsForDisableAsUser() isSeparateProfileChallengeEnabled"
                        + " = "
                            + isSeparateProfileChallengeEnabled);
            if (i == getCurrentUserId() || !isSeparateProfileChallengeEnabled) {
                try {
                    String str2 = SystemProperties.get("ro.organization_owned");
                    boolean z = str2 != null && str2.equals("true");
                    Iterator it = this.mCallbacks.entrySet().iterator();
                    while (it.hasNext()) {
                        ISystemUIAdapterCallback iSystemUIAdapterCallback =
                                (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                        if (iSystemUIAdapterCallback != null) {
                            if (i == 0 || isSeparateProfileChallengeEnabled || z) {
                                iSystemUIAdapterCallback.setMaximumFailedPasswordsForDisable(
                                        i2, str);
                            } else {
                                iSystemUIAdapterCallback.setMaximumFailedPasswordsForProfileDisable(
                                        i2);
                            }
                        }
                    }
                } catch (RemoteException e) {
                    Log.e(
                            "SystemUIAdapter",
                            "setMaximumFailedPasswordsForDisable() Failed with RemoteException",
                            e);
                } catch (NullPointerException unused) {
                    Log.e(
                            "SystemUIAdapter",
                            "setMaximumFailedPasswordsForDisable() failed with"
                                + " NullPointerException.");
                } catch (Exception unused2) {
                }
            }
        }
    }

    public final void setNFCStateChangeAllowedAsUser(int i, boolean z) {
        Log.d(
                "SystemUIAdapter",
                "setNFCStateChangeAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback =
                            (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setNFCStateChangeAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e(
                        "SystemUIAdapter",
                        "setNFCStateChangeAllowedAsUser() Failed with RemoteException",
                        e);
            } catch (NullPointerException unused) {
                Log.e(
                        "SystemUIAdapter",
                        "setNFCStateChangeAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public final void setNavigationBarHiddenAsUser(int i, boolean z) {
        Log.d(
                "SystemUIAdapter",
                "setNavigationBarHiddenAsUser() userId = " + i + ", hidden = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback =
                            (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setNavigationBarHidden(z);
                    }
                }
                disableNavBarForGesture(z);
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setNavigationBarHidden() failed with RemoteException", e);
            } catch (NullPointerException e2) {
                Log.e(
                        "SystemUIAdapter",
                        "setNavigationBarHidden() failed with NullPointerException.",
                        e2);
            } catch (Exception unused) {
            }
        }
    }

    public final void setRoamingDataAllowedAsUser(int i, boolean z) {
        Log.d(
                "SystemUIAdapter",
                "setRoamingDataAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback =
                            (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setRoamingAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e(
                        "SystemUIAdapter",
                        "setRoamingDataAllowedAsUser() Failed with RemoteException",
                        e);
            } catch (NullPointerException unused) {
                Log.e(
                        "SystemUIAdapter",
                        "setRoamingDataAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public final void setSettingsChangeAllowedAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setSettingsChangeAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback =
                            (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setSettingsChangeAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e(
                        "SystemUIAdapter",
                        "setSettingsChangeAsUser() Failed with RemoteException",
                        e);
            } catch (NullPointerException unused) {
                Log.e(
                        "SystemUIAdapter",
                        "setSettingsChangeAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public final void setStatusBarExpansionAllowedAsUser(int i, String str, boolean z) {
        Log.d(
                "SystemUIAdapter",
                "setStatusBarExpansionAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                if (z) {
                    if (this.mKioskModeService == null) {
                        this.mKioskModeService =
                                IKioskMode.Stub.asInterface(ServiceManager.getService("kioskmode"));
                    }
                    IKioskMode iKioskMode = this.mKioskModeService;
                    if (iKioskMode != null) {
                        if (iKioskMode == null) {
                            this.mKioskModeService =
                                    IKioskMode.Stub.asInterface(
                                            ServiceManager.getService("kioskmode"));
                        }
                        if (!this.mKioskModeService.isStatusBarHiddenAsUser(0)) {
                            disableStatusBar(str, false);
                        }
                    }
                } else {
                    disableStatusBar(str, true);
                }
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback =
                            (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setStatusBarExpansionAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e(
                        "SystemUIAdapter",
                        "setStatusBarExpansionAllowedAsUser() Failed with RemoteException",
                        e);
            } catch (NullPointerException unused) {
                Log.e(
                        "SystemUIAdapter",
                        "setStatusBarExpansionAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public final void setStatusBarHiddenAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setStatusBarHiddenAsUser() userId = " + i + ", hidden = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                if (z) {
                    disableStatusBar(null, true);
                } else {
                    if (this.mRestrictionPolicyService == null) {
                        this.mRestrictionPolicyService =
                                IRestrictionPolicy.Stub.asInterface(
                                        ServiceManager.getService("restriction_policy"));
                    }
                    IRestrictionPolicy iRestrictionPolicy = this.mRestrictionPolicyService;
                    if (iRestrictionPolicy != null) {
                        if (iRestrictionPolicy == null) {
                            this.mRestrictionPolicyService =
                                    IRestrictionPolicy.Stub.asInterface(
                                            ServiceManager.getService("restriction_policy"));
                        }
                        if (this.mRestrictionPolicyService.isStatusBarExpansionAllowedAsUser(
                                false, 0)) {
                            disableStatusBar(null, false);
                        }
                    }
                }
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback =
                            (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
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

    public final void setWifiAllowedAsUser(int i, boolean z) {
        Log.d("SystemUIAdapter", "setWifiAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback =
                            (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setWifiAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e("SystemUIAdapter", "setWifiAllowedAsUser() Failed with RemoteException", e);
            } catch (NullPointerException unused) {
                Log.e(
                        "SystemUIAdapter",
                        "setWifiAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public final void setWifiStateChangeAllowedAsUser(int i, boolean z) {
        Log.d(
                "SystemUIAdapter",
                "setWifiStateChangeAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback =
                            (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setWifiStateChangeAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e(
                        "SystemUIAdapter",
                        "setWifiStateChangeAllowedAsUser() Failed with RemoteException",
                        e);
            } catch (NullPointerException unused) {
                Log.e(
                        "SystemUIAdapter",
                        "setWifiStateChangeAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public final void setWifiTetheringAllowedAsUser(int i, boolean z) {
        Log.d(
                "SystemUIAdapter",
                "setWifiTetheringAllowedAsUser() userId = " + i + ", isAllowed = " + z);
        if (isCalledFromSystem() && i == getCurrentUserId()) {
            try {
                Iterator it = this.mCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    ISystemUIAdapterCallback iSystemUIAdapterCallback =
                            (ISystemUIAdapterCallback) ((Map.Entry) it.next()).getValue();
                    if (iSystemUIAdapterCallback != null) {
                        iSystemUIAdapterCallback.setWifiTetheringAllowed(z);
                    }
                }
            } catch (RemoteException e) {
                Log.e(
                        "SystemUIAdapter",
                        "setWifiTetheringAllowedAsUser() Failed with RemoteException",
                        e);
            } catch (NullPointerException unused) {
                Log.e(
                        "SystemUIAdapter",
                        "setWifiTetheringAllowedAsUser() failed with NullPointerException.");
            } catch (Exception unused2) {
            }
        }
    }

    public final void updateSystemUIMonitor(int i) {
        if (!isCalledFromSystem()) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                    i,
                    "updateSystemUIMonitor() has failed because not system call, userId = ",
                    "SystemUIAdapter");
            return;
        }
        if (i >= 0) {
            if (this.adapterUserId != i) {
                Log.d(
                        "SystemUIAdapter",
                        "updateSystemUIMonitor() userId has changed. "
                                + this.adapterUserId
                                + " -> "
                                + i);
            }
            this.adapterUserId = i;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (mContext != null) {
                    setLockedIccIdsAsUser(i, getIccIdListByAdmin());
                    Intent intent =
                            new Intent(
                                    "com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL");
                    intent.putExtra(
                            "com.samsung.android.knox.intent.extra.USER_ID_INTERNAL",
                            this.adapterUserId);
                    mContext.sendBroadcastAsUser(intent, new UserHandle(0));
                } else {
                    Log.d(
                            "SystemUIAdapter",
                            "updateSystemUIMonitor() cannot call because context is null. ");
                }
            } catch (Exception e) {
                Log.e("SystemUIAdapter", "updateSystemUIMonitor() userId = " + i, e);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }
}
