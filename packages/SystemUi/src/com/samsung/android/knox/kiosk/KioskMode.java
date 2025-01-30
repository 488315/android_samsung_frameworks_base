package com.samsung.android.knox.kiosk;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import com.samsung.android.knox.AccessController;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EdmConstants;
import com.samsung.android.knox.EnterpriseDeviceAdminInfo;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.kiosk.IKioskMode;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.restriction.IRestrictionPolicy;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class KioskMode {
    public static final String ACTION_DISABLE_KIOSK_MODE_RESULT = "com.samsung.android.knox.intent.action.DISABLE_KIOSK_MODE_RESULT";
    public static final String ACTION_ENABLE_KIOSK_MODE_RESULT = "com.samsung.android.knox.intent.action.ENABLE_KIOSK_MODE_RESULT";
    public static final String ACTION_INFORMATION_STREAM_INTERNAL = "com.samsung.android.knox.intent.action.INFORMATION_STREAM_INTERNAL";
    public static String ACTION_REFRESH_HWKEY_INTERNAL = "com.samsung.android.knox.intent.action.REFRESH_HWKEY_CACHE_INTERNAL";
    public static final String ACTION_UNEXPECTED_KIOSK_BEHAVIOR = "com.samsung.android.knox.intent.action.UNEXPECTED_KIOSK_BEHAVIOR";
    public static String CONTROL_PANEL_PKGNAME = "com.sec.android.app.controlpanel";
    public static final String DEFAULT_KIOSK_PKG = "com.sec.android.kiosk";
    public static final int EDGE_FUNCTION_ALL = 31;
    public static final int EDGE_FUNCTION_APPSEDGE = 16;
    public static final int EDGE_FUNCTION_EDGELIGHTING = 8;
    public static final int EDGE_FUNCTION_INFORMATIONSTREAM = 4;
    public static final int EDGE_FUNCTION_NIGHTCLOCK = 1;
    public static final int EDGE_FUNCTION_PEOPLEEDGE = 2;
    public static final int ERROR_BUSY = -4;
    public static final int ERROR_KIOSK_ALREADY_ENABLED = -1;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_PACKAGE_NOT_FOUND = -3;
    public static final int ERROR_PERMISSION_DENIED = -2;
    public static final int ERROR_UNKNOWN = -2000;
    public static final String EXTRA_BLOCKED_STATUS = "com.samsung.android.knox.intent.extra.BLOCKED_STATUS";
    public static final String EXTRA_KIOSK_RESULT = "com.samsung.android.knox.intent.extra.KIOSK_RESULT";
    public static String MINI_TASK_MANAGER_PKGNAME = "com.sec.minimode.taskcloser";
    public static final String TAG = "KioskMode";
    public static String TASK_MANAGER_PKGNAME = "com.sec.android.app.taskmanager";
    public static final Object mSync = new Object();
    public final Context mContext;
    public ContextInfo mContextInfo;
    public IKioskMode mKioskService;
    public IRestrictionPolicy mRestrictionPolicy;

    public KioskMode(ContextInfo contextInfo, Context context) {
        this.mContext = context;
        this.mContextInfo = contextInfo;
    }

    public static KioskMode getInstance(Context context) {
        KioskMode kioskMode;
        synchronized (mSync) {
            kioskMode = new KioskMode(new ContextInfo(Process.myUid()), context.getApplicationContext());
        }
        return kioskMode;
    }

    public final boolean allowAirCommandMode(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "KioskMode.allowAirCommandMode");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mKioskService.allowAirCommandMode(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Kiosk mode service", e);
            return false;
        }
    }

    public final boolean allowAirViewMode(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "KioskMode.allowAirViewMode");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mKioskService.allowAirViewMode(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Kiosk mode service", e);
            return false;
        }
    }

    public final boolean allowEdgeScreen(int i, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "KioskMode.allowEdgeScreen");
        if (getService() == null || KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 14) {
            return false;
        }
        try {
            return this.mKioskService.allowEdgeScreen(this.mContextInfo, i, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Kiosk mode service", e);
            return false;
        }
    }

    public final List<Integer> allowHardwareKeys(List<Integer> list, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "KioskMode.allowHardwareKeys");
        if (getService() != null && list != null) {
            try {
                if (!list.isEmpty() && !list.contains(null)) {
                    int size = list.size();
                    int[] iArr = new int[size];
                    for (int i = 0; i < size; i++) {
                        iArr[i] = list.get(i).intValue();
                    }
                    int[] allowHardwareKeys = this.mKioskService.allowHardwareKeys(this.mContextInfo, iArr, z);
                    if (allowHardwareKeys != null) {
                        ArrayList arrayList = new ArrayList();
                        for (int i2 : allowHardwareKeys) {
                            arrayList.add(Integer.valueOf(i2));
                        }
                        return arrayList;
                    }
                }
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with kiosk mode service", e);
            }
        }
        return null;
    }

    public final boolean allowMultiWindowMode(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "KioskMode.allowMultiWindowMode");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mKioskService.allowMultiWindowMode(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with kiosk mode service", e);
            return false;
        }
    }

    public final boolean allowTaskManager(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "KioskMode.allowTaskManager");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mKioskService.allowTaskManager(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with kiosk mode service", e);
            return false;
        }
    }

    public final boolean clearAllNotifications() {
        EnterpriseLicenseManager.log(this.mContextInfo, "KioskMode.clearAllNotifications");
        if (getService() != null) {
            try {
                return this.mKioskService.clearAllNotifications(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed to clear notification bar", e);
            }
        }
        return false;
    }

    public final void disableKioskMode() {
        EnterpriseLicenseManager.log(this.mContextInfo, "KioskMode.disableKioskMode()");
        IKioskMode service = getService();
        if (service != null) {
            try {
                service.disableKioskMode(this.mContextInfo);
                return;
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with kiosk mode service", e);
            }
        }
        int userId = UserHandle.getUserId(this.mContextInfo.mCallerUid);
        Intent intent = new Intent(ACTION_DISABLE_KIOSK_MODE_RESULT);
        intent.putExtra(EXTRA_KIOSK_RESULT, -2000);
        intent.putExtra(EdmConstants.EXTRA_ADMIN_UID, userId);
        intent.setPackage(getCallingPackage());
        this.mContext.sendBroadcast(intent, EnterpriseDeviceAdminInfo.USES_POLICY_MDM_KIOSK_MODE_TAG);
    }

    public final void enableKioskMode() {
        AccessController.throwIfParentInstance(this.mContextInfo, "enableKioskMode");
        EnterpriseLicenseManager.log(this.mContextInfo, "KioskMode.enableKioskMode()");
        IKioskMode service = getService();
        if (service != null) {
            try {
                service.enableKioskMode(this.mContextInfo, DEFAULT_KIOSK_PKG);
                return;
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with kiosk mode service", e);
            }
        }
        int userId = UserHandle.getUserId(this.mContextInfo.mCallerUid);
        Intent intent = new Intent(ACTION_ENABLE_KIOSK_MODE_RESULT);
        intent.putExtra(EXTRA_KIOSK_RESULT, -2000);
        intent.putExtra(EdmConstants.EXTRA_ADMIN_UID, userId);
        intent.setPackage(getCallingPackage());
        this.mContext.sendBroadcast(intent, EnterpriseDeviceAdminInfo.USES_POLICY_MDM_KIOSK_MODE_TAG);
    }

    public final List<Integer> getAllBlockedHardwareKeys() {
        if (getService() != null) {
            try {
                return this.mKioskService.getAllBlockedHardwareKeys(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with kiosk mode service", e);
            }
        }
        return new ArrayList(0);
    }

    public final int getBlockedEdgeScreen() {
        if (getService() == null || KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 14) {
            return 0;
        }
        try {
            return this.mKioskService.getBlockedEdgeScreen(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Kiosk mode service", e);
            return 0;
        }
    }

    public final String getCallingPackage() {
        return this.mContext.getPackageManager().getPackagesForUid(Binder.getCallingUid())[0];
    }

    public final List<Integer> getHardwareKeyList() {
        if (getService() != null) {
            try {
                return this.mKioskService.getHardwareKeyList(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with kiosk mode service", e);
            }
        }
        return new ArrayList(0);
    }

    public final String getKioskHomePackage() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mKioskService.getKioskHomePackage(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with kiosk mode service", e);
            return null;
        }
    }

    public final IRestrictionPolicy getRestrictionService() {
        if (this.mRestrictionPolicy == null) {
            this.mRestrictionPolicy = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
        }
        return this.mRestrictionPolicy;
    }

    public final IKioskMode getService() {
        if (this.mKioskService == null) {
            this.mKioskService = IKioskMode.Stub.asInterface(ServiceManager.getService("kioskmode"));
        }
        return this.mKioskService;
    }

    public final boolean hideNavigationBar(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "KioskMode.hideNavigationBar");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mKioskService.hideNavigationBar(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with kiosk mode service", e);
            return false;
        }
    }

    public final boolean hideStatusBar(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "KioskMode.hideStatusBar");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mKioskService.hideStatusBar(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with kiosk mode service", e);
            return false;
        }
    }

    public final boolean hideSystemBar(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "KioskMode.hideSystemBar");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mKioskService.hideSystemBar(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with kiosk mode service", e);
            return false;
        }
    }

    public final boolean isAirCommandModeAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mKioskService.isAirCommandModeAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Kiosk mode service", e);
            return true;
        }
    }

    public final boolean isAirViewModeAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mKioskService.isAirViewModeAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Kiosk mode service", e);
            return true;
        }
    }

    public final boolean isEnableKioskModeAllowed() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mKioskService.isEnableKioskModeAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with kiosk mode service", e);
            return false;
        }
    }

    public final boolean isHardwareKeyAllowed(int i) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mKioskService.isHardwareKeyAllowed(this.mContextInfo, i, false);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with kiosk mode service", e);
            return true;
        }
    }

    public final boolean isKioskModeEnabled() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mKioskService.isKioskModeEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with kiosk mode service", e);
            return false;
        }
    }

    public final boolean isMultiWindowModeAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mKioskService.isMultiWindowModeAllowed(this.mContextInfo, false);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with kiosk mode service", e);
            return true;
        }
    }

    public final boolean isNavigationBarHidden() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mKioskService.isNavigationBarHidden(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with kiosk mode service", e);
            return false;
        }
    }

    public final boolean isStatusBarHidden() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mKioskService.isStatusBarHidden(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with kiosk mode service", e);
            return false;
        }
    }

    public final boolean isSystemBarHidden() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mKioskService.isSystemBarHidden(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with kiosk mode service", e);
            return false;
        }
    }

    public final boolean isTaskManagerAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mKioskService.isTaskManagerAllowed(this.mContextInfo, false);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with kiosk mode service", e);
            return true;
        }
    }

    public final boolean wipeRecentTasks() {
        EnterpriseLicenseManager.log(this.mContextInfo, "KioskMode.wipeRecentTasks");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mKioskService.wipeRecentTasks(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with kiosk mode service", e);
            return false;
        }
    }

    public final boolean isHardwareKeyAllowed(int i, boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mKioskService.isHardwareKeyAllowed(this.mContextInfo, i, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with kiosk mode service", e);
            return true;
        }
    }

    public final boolean isMultiWindowModeAllowed(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mKioskService.isMultiWindowModeAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with kiosk mode service", e);
            return true;
        }
    }

    public final boolean isTaskManagerAllowed(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mKioskService.isTaskManagerAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with kiosk mode service", e);
            return true;
        }
    }

    public static KioskMode getInstance(ContextInfo contextInfo, Context context) {
        KioskMode kioskMode;
        synchronized (mSync) {
            kioskMode = new KioskMode(contextInfo, context.getApplicationContext());
        }
        return kioskMode;
    }

    public final void disableKioskMode(final KioskSetting kioskSetting) {
        EnterpriseLicenseManager.log(this.mContextInfo, "KioskMode.disableKioskMode(KioskSetting)");
        final IKioskMode service = getService();
        if (service != null) {
            Thread thread = new Thread(new Runnable() { // from class: com.samsung.android.knox.kiosk.KioskMode.3
                @Override // java.lang.Runnable
                public final void run() {
                    try {
                        service.disableKioskMode(KioskMode.this.mContextInfo);
                    } catch (RemoteException e) {
                        Log.w(KioskMode.TAG, "Failed talking with kiosk mode service", e);
                    }
                    KioskSetting kioskSetting2 = kioskSetting;
                    if (kioskSetting2 == null) {
                        kioskSetting2 = new KioskSetting();
                        kioskSetting2.settingsChanges = true;
                        kioskSetting2.statusBarExpansion = true;
                        kioskSetting2.homeKey = true;
                        kioskSetting2.airCommand = true;
                        kioskSetting2.airView = true;
                        kioskSetting2.multiWindow = true;
                        kioskSetting2.smartClip = true;
                        kioskSetting2.taskManager = true;
                        kioskSetting2.clearAllNotifications = true;
                        kioskSetting2.navigationBar = false;
                        kioskSetting2.statusBar = false;
                        kioskSetting2.systemBar = false;
                        kioskSetting2.wipeRecentTasks = true;
                        kioskSetting2.blockedEdgeFunctions = 0;
                    }
                    KioskMode kioskMode = KioskMode.this;
                    String str = KioskMode.TAG;
                    IRestrictionPolicy restrictionService = kioskMode.getRestrictionService();
                    if (restrictionService == null) {
                        Log.w(KioskMode.TAG, "Failed talking with restriction service");
                    } else {
                        try {
                            if (!restrictionService.allowSettingsChanges(KioskMode.this.mContextInfo, kioskSetting2.settingsChanges)) {
                                Log.w(KioskMode.TAG, "allow settings changes failed");
                            }
                        } catch (RemoteException e2) {
                            Log.w(KioskMode.TAG, "Failed to allow settings changes", e2);
                        }
                        try {
                            if (!restrictionService.allowStatusBarExpansion(KioskMode.this.mContextInfo, kioskSetting2.statusBarExpansion)) {
                                Log.w(KioskMode.TAG, "allow status bar expansion failed");
                            }
                        } catch (RemoteException e3) {
                            Log.w(KioskMode.TAG, "Failed to allow status bar expansion", e3);
                        }
                        try {
                            if (!restrictionService.setHomeKeyState(KioskMode.this.mContextInfo, kioskSetting2.homeKey)) {
                                Log.w(KioskMode.TAG, "set home key state failed");
                            }
                        } catch (RemoteException e4) {
                            Log.w(KioskMode.TAG, "Failed to set home key state", e4);
                        }
                        try {
                            if (!restrictionService.allowSmartClipMode(KioskMode.this.mContextInfo, kioskSetting2.smartClip)) {
                                Log.w(KioskMode.TAG, "allow smart clip mode failed");
                            }
                        } catch (RemoteException e5) {
                            Log.w(KioskMode.TAG, "Failed to allow smart clip mode", e5);
                        }
                    }
                    try {
                        if (!service.allowAirCommandMode(KioskMode.this.mContextInfo, kioskSetting2.airCommand)) {
                            Log.w(KioskMode.TAG, "allow air command failed");
                        }
                    } catch (RemoteException e6) {
                        Log.w(KioskMode.TAG, "Failed to allow air command mode", e6);
                    }
                    try {
                        if (!service.allowAirViewMode(KioskMode.this.mContextInfo, kioskSetting2.airView)) {
                            Log.w(KioskMode.TAG, "allow air view failed");
                        }
                    } catch (RemoteException e7) {
                        Log.w(KioskMode.TAG, "Failed to allow air view mode", e7);
                    }
                    List<Integer> list = kioskSetting2.hardwareKey;
                    if (list != null) {
                        int[] iArr = new int[list.size()];
                        for (int i = 0; i < kioskSetting2.hardwareKey.size(); i++) {
                            iArr[i] = kioskSetting2.hardwareKey.get(i).intValue();
                        }
                        try {
                            if (service.allowHardwareKeys(KioskMode.this.mContextInfo, iArr, true) == null) {
                                Log.w(KioskMode.TAG, "allowHardwareKeys failed");
                            }
                        } catch (RemoteException e8) {
                            Log.w(KioskMode.TAG, "Failed to allow hardware keys", e8);
                        }
                    }
                    try {
                        if (!service.allowMultiWindowMode(KioskMode.this.mContextInfo, kioskSetting2.multiWindow)) {
                            Log.w(KioskMode.TAG, "set multiwindow mode failed");
                        }
                    } catch (RemoteException e9) {
                        Log.w(KioskMode.TAG, "Failed to allow multiwindow mode", e9);
                    }
                    try {
                        if (!service.allowTaskManager(KioskMode.this.mContextInfo, kioskSetting2.taskManager)) {
                            Log.w(KioskMode.TAG, "set task manager failed");
                        }
                    } catch (RemoteException e10) {
                        Log.w(KioskMode.TAG, "Failed to allow task manager", e10);
                    }
                    if (kioskSetting2.clearAllNotifications) {
                        try {
                            if (!service.clearAllNotifications(KioskMode.this.mContextInfo)) {
                                Log.w(KioskMode.TAG, "clear all notifications failed");
                            }
                        } catch (RemoteException e11) {
                            Log.w(KioskMode.TAG, "Failed to clear all notifications", e11);
                        }
                    }
                    try {
                        if (!service.hideNavigationBar(KioskMode.this.mContextInfo, kioskSetting2.navigationBar)) {
                            Log.w(KioskMode.TAG, "hide navigationbar failed");
                        }
                    } catch (RemoteException e12) {
                        Log.w(KioskMode.TAG, "Failed to hide navigationbar", e12);
                    }
                    try {
                        if (!service.hideStatusBar(KioskMode.this.mContextInfo, kioskSetting2.statusBar)) {
                            Log.w(KioskMode.TAG, "hide status bar failed");
                        }
                    } catch (RemoteException e13) {
                        Log.w(KioskMode.TAG, "Failed to hide status bar", e13);
                    }
                    try {
                        if (!service.hideSystemBar(KioskMode.this.mContextInfo, kioskSetting2.systemBar)) {
                            Log.w(KioskMode.TAG, "hide system bar failed");
                        }
                    } catch (RemoteException e14) {
                        Log.w(KioskMode.TAG, "Failed to hide system bar", e14);
                    }
                    if (kioskSetting2.wipeRecentTasks) {
                        try {
                            if (!service.wipeRecentTasks(KioskMode.this.mContextInfo)) {
                                Log.w(KioskMode.TAG, "wipe recent task failed");
                            }
                        } catch (RemoteException e15) {
                            Log.w(KioskMode.TAG, "Failed to wipe recent task", e15);
                        }
                    }
                    try {
                        if (service.allowEdgeScreen(KioskMode.this.mContextInfo, kioskSetting2.blockedEdgeFunctions, true)) {
                            return;
                        }
                        Log.w(KioskMode.TAG, "Allow edge functions failed");
                    } catch (RemoteException e16) {
                        Log.w(KioskMode.TAG, "Failed to Allow Edge Functions", e16);
                    }
                }
            });
            final AtomicReference atomicReference = new AtomicReference();
            thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: com.samsung.android.knox.kiosk.KioskMode.4
                @Override // java.lang.Thread.UncaughtExceptionHandler
                public final void uncaughtException(Thread thread2, Throwable th) {
                    atomicReference.set(th);
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                Log.w(TAG, "Interrupt occured while trying to disable kiosk mode", e);
            }
            Throwable th = (Throwable) atomicReference.get();
            if (th != null && (th instanceof SecurityException)) {
                throw ((SecurityException) th);
            }
        }
    }

    public final void enableKioskMode(String str) {
        AccessController.throwIfParentInstance(this.mContextInfo, "enableKioskMode");
        EnterpriseLicenseManager.log(this.mContextInfo, "KioskMode.enableKioskMode(String)");
        IKioskMode service = getService();
        if (service != null) {
            try {
                service.enableKioskMode(this.mContextInfo, str);
                return;
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with kiosk mode service", e);
            }
        }
        int userId = UserHandle.getUserId(this.mContextInfo.mCallerUid);
        Intent intent = new Intent(ACTION_ENABLE_KIOSK_MODE_RESULT);
        intent.putExtra(EXTRA_KIOSK_RESULT, -2000);
        intent.putExtra(EdmConstants.EXTRA_ADMIN_UID, userId);
        intent.setPackage(getCallingPackage());
        this.mContext.sendBroadcast(intent, EnterpriseDeviceAdminInfo.USES_POLICY_MDM_KIOSK_MODE_TAG);
    }

    public final void enableKioskMode(final KioskSetting kioskSetting) {
        EnterpriseLicenseManager.log(this.mContextInfo, "KioskMode.enableKioskMode(KioskSetting)");
        if (!isEnableKioskModeAllowed()) {
            Log.w(TAG, "call enableKioskMode is not allowed");
            return;
        }
        final IKioskMode service = getService();
        if (service != null) {
            Thread thread = new Thread(new Runnable() { // from class: com.samsung.android.knox.kiosk.KioskMode.1
                @Override // java.lang.Runnable
                public final void run() {
                    KioskSetting kioskSetting2 = kioskSetting;
                    if (kioskSetting2 == null) {
                        kioskSetting2 = new KioskSetting();
                        kioskSetting2.homeKey = false;
                        kioskSetting2.settingsChanges = false;
                        kioskSetting2.statusBarExpansion = false;
                        kioskSetting2.airCommand = false;
                        kioskSetting2.airView = false;
                        kioskSetting2.multiWindow = false;
                        kioskSetting2.smartClip = false;
                        kioskSetting2.taskManager = false;
                        kioskSetting2.clearAllNotifications = false;
                        kioskSetting2.navigationBar = true;
                        kioskSetting2.statusBar = true;
                        kioskSetting2.systemBar = true;
                        kioskSetting2.wipeRecentTasks = false;
                        kioskSetting2.blockedEdgeFunctions = 0;
                    }
                    KioskMode kioskMode = KioskMode.this;
                    String str = KioskMode.TAG;
                    IRestrictionPolicy restrictionService = kioskMode.getRestrictionService();
                    if (restrictionService == null) {
                        Log.w(KioskMode.TAG, "Failed talking with restriction service");
                    } else {
                        try {
                            if (!restrictionService.allowSettingsChanges(KioskMode.this.mContextInfo, kioskSetting2.settingsChanges)) {
                                Log.w(KioskMode.TAG, "allow settings changes failed");
                            }
                        } catch (RemoteException e) {
                            Log.w(KioskMode.TAG, "Failed to allow settings changes", e);
                        }
                        try {
                            if (!restrictionService.allowStatusBarExpansion(KioskMode.this.mContextInfo, kioskSetting2.statusBarExpansion)) {
                                Log.w(KioskMode.TAG, "allow status bar expansion failed");
                            }
                        } catch (RemoteException e2) {
                            Log.w(KioskMode.TAG, "Failed to allow status bar expansion", e2);
                        }
                        try {
                            if (!restrictionService.setHomeKeyState(KioskMode.this.mContextInfo, kioskSetting2.homeKey)) {
                                Log.w(KioskMode.TAG, "set home key state failed");
                            }
                        } catch (RemoteException e3) {
                            Log.w(KioskMode.TAG, "Failed to set home key state", e3);
                        }
                        try {
                            if (!restrictionService.allowSmartClipMode(KioskMode.this.mContextInfo, kioskSetting2.smartClip)) {
                                Log.w(KioskMode.TAG, "allow smart clip mode failed");
                            }
                        } catch (RemoteException e4) {
                            Log.w(KioskMode.TAG, "Failed to allow smart clip mode", e4);
                        }
                    }
                    try {
                        if (!service.allowAirCommandMode(KioskMode.this.mContextInfo, kioskSetting2.airCommand)) {
                            Log.w(KioskMode.TAG, "set air command mode failed");
                        }
                    } catch (RemoteException e5) {
                        Log.w(KioskMode.TAG, "Failed to allow air command mode", e5);
                    }
                    try {
                        if (!service.allowAirViewMode(KioskMode.this.mContextInfo, kioskSetting2.airView)) {
                            Log.w(KioskMode.TAG, "set air view mode failed");
                        }
                    } catch (RemoteException e6) {
                        Log.w(KioskMode.TAG, "Failed to allow air view mode", e6);
                    }
                    List<Integer> list = kioskSetting2.hardwareKey;
                    if (list != null) {
                        int[] iArr = new int[list.size()];
                        for (int i = 0; i < kioskSetting2.hardwareKey.size(); i++) {
                            iArr[i] = kioskSetting2.hardwareKey.get(i).intValue();
                        }
                        try {
                            if (service.allowHardwareKeys(KioskMode.this.mContextInfo, iArr, false) == null) {
                                Log.w(KioskMode.TAG, "allowHardwareKeys failed");
                            }
                        } catch (RemoteException e7) {
                            Log.w(KioskMode.TAG, "Failed to allow hardware keys", e7);
                        }
                    }
                    try {
                        if (!service.allowMultiWindowMode(KioskMode.this.mContextInfo, kioskSetting2.multiWindow)) {
                            Log.w(KioskMode.TAG, "set multiwindow mode failed");
                        }
                    } catch (RemoteException e8) {
                        Log.w(KioskMode.TAG, "Failed to allow multiwindow mode", e8);
                    }
                    try {
                        if (!service.allowTaskManager(KioskMode.this.mContextInfo, kioskSetting2.taskManager)) {
                            Log.w(KioskMode.TAG, "set task manager failed");
                        }
                    } catch (RemoteException e9) {
                        Log.w(KioskMode.TAG, "Failed to allow task manager", e9);
                    }
                    if (kioskSetting2.clearAllNotifications) {
                        try {
                            if (!service.clearAllNotifications(KioskMode.this.mContextInfo)) {
                                Log.w(KioskMode.TAG, "clear all notifications failed");
                            }
                        } catch (RemoteException e10) {
                            Log.w(KioskMode.TAG, "Failed to clear all notifications", e10);
                        }
                    }
                    try {
                        if (!service.hideSystemBar(KioskMode.this.mContextInfo, kioskSetting2.systemBar)) {
                            Log.w(KioskMode.TAG, "hide system bar failed");
                        }
                    } catch (RemoteException e11) {
                        Log.w(KioskMode.TAG, "Failed to hide system bar", e11);
                    }
                    try {
                        if (!service.hideNavigationBar(KioskMode.this.mContextInfo, kioskSetting2.navigationBar)) {
                            Log.w(KioskMode.TAG, "hide navigationbar failed");
                        }
                    } catch (RemoteException e12) {
                        Log.w(KioskMode.TAG, "Failed to hide navigationbar", e12);
                    }
                    try {
                        if (!service.hideStatusBar(KioskMode.this.mContextInfo, kioskSetting2.statusBar)) {
                            Log.w(KioskMode.TAG, "hide status bar failed");
                        }
                    } catch (RemoteException e13) {
                        Log.w(KioskMode.TAG, "Failed to hide status bar", e13);
                    }
                    if (kioskSetting2.wipeRecentTasks) {
                        try {
                            if (!service.wipeRecentTasks(KioskMode.this.mContextInfo)) {
                                Log.w(KioskMode.TAG, "wipe recent task failed");
                            }
                        } catch (RemoteException e14) {
                            Log.w(KioskMode.TAG, "Failed to wipe recent task", e14);
                        }
                    }
                    try {
                        if (!service.allowEdgeScreen(KioskMode.this.mContextInfo, kioskSetting2.blockedEdgeFunctions, false)) {
                            Log.w(KioskMode.TAG, "block edge functions failed");
                        }
                    } catch (RemoteException e15) {
                        Log.w(KioskMode.TAG, "Failed to Block Edge Functions", e15);
                    }
                    if (KioskMode.this.isKioskModeEnabled()) {
                        return;
                    }
                    try {
                        service.enableKioskMode(KioskMode.this.mContextInfo, KioskMode.DEFAULT_KIOSK_PKG);
                    } catch (RemoteException e16) {
                        Log.w(KioskMode.TAG, "Failed talking with kiosk mode service", e16);
                    }
                }
            });
            final AtomicReference atomicReference = new AtomicReference();
            thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: com.samsung.android.knox.kiosk.KioskMode.2
                @Override // java.lang.Thread.UncaughtExceptionHandler
                public final void uncaughtException(Thread thread2, Throwable th) {
                    atomicReference.set(th);
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                Log.w(TAG, "Interrupt occured while trying to enable kiosk mode", e);
            }
            Throwable th = (Throwable) atomicReference.get();
            if (th != null && (th instanceof SecurityException)) {
                throw ((SecurityException) th);
            }
        }
    }
}
