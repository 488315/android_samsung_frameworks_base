package com.samsung.android.knox.application;

import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IUserManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.sec.enterprise.auditlog.AuditLog;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.util.Log;
import com.samsung.android.knox.AccessController;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EdmConstants;
import com.samsung.android.knox.EdmUtils;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.appconfig.ApplicationRestrictionsManager;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.restriction.IRestrictionPolicy;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ApplicationPolicy {
    public static final String ACTION_APPLICATION_FOCUS_CHANGE = "com.samsung.android.knox.intent.action.APPLICATION_FOCUS_CHANGE";
    public static final String ACTION_APP_AUTHORIZATION_SCOPES_CHANGED = "com.samsung.android.knox.intent.action.APP_AUTHORIZATION_SCOPES_CHANGED";
    public static final String ACTION_EDM_FORCE_LAUNCHER_REFRESH_INTERNAL = "com.samsung.android.knox.intent.action.EDM_FORCE_LAUNCHER_REFRESH_INTERNAL";
    public static final String ACTION_EDM_UNINSTALL_STATUS_INTERNAL = "com.samsung.android.knox.intent.action.EDM_UNINSTALL_STATUS_INTERNAL";
    public static final String ACTION_PREVENT_APPLICATION_START = "com.samsung.android.knox.intent.action.PREVENT_APPLICATION_START";
    public static final String ACTION_PREVENT_APPLICATION_STOP = "com.samsung.android.knox.intent.action.PREVENT_APPLICATION_STOP";
    public static final int APPLICATION_INSTALLATION_MODE_ALLOW = 1;
    public static final int APPLICATION_INSTALLATION_MODE_DISALLOW = 0;
    public static final int APPLICATION_UNINSTALLATION_MODE_ALLOW = 1;
    public static final int APPLICATION_UNINSTALLATION_MODE_DISALLOW = 0;
    public static final String AUTHORIZATION_PROVISION_CERT_ACME_SCEP = "authorization_provision_cert_acme_scep";
    public static final String AUTHORIZATION_REMOTE_CONTROL = "authorization_remote_control";
    public static final String DEFAULT_TYPE_AUDIO = "audio/*";
    public static final String DEFAULT_TYPE_PDF = "application/pdf";
    public static final int ERROR_CALLER_TARGET_SDK_NOT_SUPPORTED = -4;
    public static final int ERROR_INVALID_INPUT = -1;
    public static final int ERROR_INVALID_SCOPE = -7;
    public static final int ERROR_MULTI_ADMIN = -5;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_PROXY_NOT_INSTALLED = -5;
    public static final int ERROR_PROXY_NO_ADMIN_RECEIVER = -6;
    public static final int ERROR_SIGNATURE_MISMATCH = -3;
    public static final int ERROR_UNKNOWN = -2;
    public static final String EXTRA_ADMIN_PACKAGE_NAME = "com.samsung.android.knox.intent.extra.ADMIN_PACKAGE_NAME";
    public static final String EXTRA_APPLICATION_FOCUS_COMPONENT_NAME = "com.samsung.android.knox.intent.extra.APPLICATION_FOCUS_COMPONENT_NAME";
    public static final String EXTRA_APPLICATION_FOCUS_DEX_MODE = "com.samsung.android.knox.intent.extra.APPLICATION_FOCUS_DEX_MODE";
    public static final String EXTRA_APPLICATION_FOCUS_STATUS = "com.samsung.android.knox.intent.extra.APPLICATION_FOCUS_STATUS";
    public static final String EXTRA_APPLICATION_PACKAGE_NAME = "com.samsung.android.knox.intent.extra.APPLICATION_PACKAGE_NAME";
    public static final String EXTRA_AUTHORIZATION_SCOPES = "com.samsung.android.knox.intent.extra.AUTHORIZATION_SCOPES";
    public static final String EXTRA_ERROR_CLASS = "com.samsung.android.knox.intent.extra.ERROR_CLASS";
    public static final String EXTRA_ERROR_REASON = "com.samsung.android.knox.intent.extra.ERROR_REASON";
    public static final String EXTRA_ERROR_TYPE = "com.samsung.android.knox.intent.extra.ERROR_TYPE";
    public static final String EXTRA_PACKAGE_NAME = "com.samsung.android.knox.intent.extra.PACKAGE_NAME";
    public static final String EXTRA_USER_ID = "com.samsung.android.knox.intent.extra.USER_ID";
    public static final int FLAG_ALLOW_PROXY_FOR_PFW = 1;
    public static final int NOTIFICATION_MODE_BLOCK_ALL = 2;
    public static final int NOTIFICATION_MODE_BLOCK_TEXT = 3;
    public static final int NOTIFICATION_MODE_BLOCK_TEXT_AND_SOUND = 4;
    public static final int PERMISSION_POLICY_STATE_DEFAULT = 0;
    public static final int PERMISSION_POLICY_STATE_DENY = 2;
    public static final int PERMISSION_POLICY_STATE_GRANT = 1;
    public static final String PKGINFO_SIGNATURE = "package-app-signature";
    public static final String PKGINFO_VERSION = "package-app-version-code";
    public static final String PROXY_FLAGS = "proxy-flags";
    public static String TAG = "ApplicationPolicy";
    public static final int TYPE_APPROVED_APP_INSTALLER = 1;
    public static final int TYPE_CALL_RECORDING = 3;
    public static final int TYPE_EXTERNAL_STORAGE_ACCESS = 2;
    public final Context mContext;
    public final ContextInfo mContextInfo;
    public IRestrictionPolicy mRestrictionService;
    public IApplicationPolicy mService;
    public IUserManager mUm;
    public static final Intent SMS_MMS_TASK = createSmsMmsTask();
    public static final Intent LAUNCHER_TASK = createLauncherTask();
    public static final Intent OPEN_URL_TASK = createOpenURLTask();
    public static final Intent OPEN_PDF_TASK = createOpenPDFTask();
    public static final Intent OPEN_DIALER_TASK = createOpenDialerTask();
    public static final Intent PLAY_AUDIO_TASK = createAudioTask();
    public static final Intent PLAY_VIDEO_TASK = createVideoTask();
    public static final Intent DEVICE_ASSISTANCE_ACTIVITY_TASK = createAssistActivityTask();
    public static final Intent DEVICE_ASSISTANCE_SERVICE_TASK = createAssistServiceTask();

    public ApplicationPolicy(ContextInfo contextInfo, Context context) {
        this.mContextInfo = contextInfo;
        this.mContext = context;
    }

    public static Intent createAssistActivityTask() {
        return new Intent("android.intent.action.ASSIST");
    }

    public static Intent createAssistServiceTask() {
        return new Intent("android.service.voice.VoiceInteractionService");
    }

    public static Intent createAudioTask() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(Uri.parse("content://audio.mp3"), DEFAULT_TYPE_AUDIO);
        return intent;
    }

    public static Intent createLauncherTask() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.addCategory("android.intent.category.DEFAULT");
        return intent;
    }

    public static Intent createOpenDialerTask() {
        Intent intent = new Intent("android.intent.action.DIAL");
        intent.setData(Uri.parse("tel:"));
        return intent;
    }

    public static Intent createOpenPDFTask() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(new File("file.pdf")), DEFAULT_TYPE_PDF);
        return intent;
    }

    public static Intent createOpenURLTask() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.setData(Uri.parse("http://"));
        return intent;
    }

    public static Intent createSmsMmsTask() {
        Intent intent = new Intent("android.intent.action.SENDTO");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("smsto:"));
        return intent;
    }

    public static Intent createVideoTask() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setType("video/*");
        return intent;
    }

    public boolean addAppPackageNameToBlackList(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addAppPackageNameToBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addAppPackageNameToBlackList(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean addAppPackageNameToWhiteList(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addAppPackageNameToWhiteList(String)");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addAppPackageNameToWhiteList(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean addAppPermissionToBlackList(String str) {
        logUsage("ApplicationPolicy.addAppPermissionToBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addAppPermissionToBlackList(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean addAppSignatureToBlackList(String str) {
        logUsage("ApplicationPolicy.addAppSignatureToBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addAppSignatureToBlackList(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean addAppSignatureToWhiteList(String str) {
        logUsage("ApplicationPolicy.addAppSignatureToWhiteList(String)");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addAppSignatureToWhiteList(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public int addApplicationToCameraAllowList(AppIdentity appIdentity) {
        logUsage("ApplicationPolicy.addAppSignatureToCameraAllowList");
        if (getService() == null) {
            return -2;
        }
        try {
            return this.mService.addApplicationToCameraAllowList(this.mContextInfo, appIdentity);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return -2;
        }
    }

    public boolean addHomeShortcut(String str, String str2) {
        logUsage("ApplicationPolicy.addHomeShortcut");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addHomeShortcut(this.mContextInfo, str, str2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed addHomeShorcut!!!", e);
            return false;
        }
    }

    public boolean addNewAdminActivationAppWhiteList(List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addNewAdminActivationAppWhiteList");
        if (getRestrictionService() == null) {
            return false;
        }
        try {
            return this.mRestrictionService.addNewAdminActivationAppWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed addNewAdminActivationAppWhiteList", e);
            return false;
        }
    }

    public int addPackageToBatteryOptimizationWhiteList(AppIdentity appIdentity) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addPackageToBatteryOptimizationWhiteList");
        if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 17 || getService() == null) {
            return -2;
        }
        try {
            return this.mService.addPackageToBatteryOptimizationWhiteList(this.mContextInfo, appIdentity);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with ApplicationPolicy service", e);
            return -2;
        }
    }

    public int addPackageToBlackList(int i, AppIdentity appIdentity) {
        AccessController.throwIfParentInstance(this.mContextInfo, "addPackageToBlackList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addPackageToBlackList");
        if (getService() == null) {
            return -2;
        }
        try {
            return this.mService.addPackageToBlackList(this.mContextInfo, i, appIdentity);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to application policy", e);
            return -2;
        }
    }

    public int addPackageToWhiteList(int i, AppIdentity appIdentity) {
        AccessController.throwIfParentInstance(this.mContextInfo, "addPackageToWhiteList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addPackageToWhiteList");
        if (getService() == null) {
            return -2;
        }
        try {
            return this.mService.addPackageToWhiteList(this.mContextInfo, i, appIdentity);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to application policy", e);
            return -2;
        }
    }

    public boolean addPackagesToClearCacheBlackList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "addPackagesToClearCacheBlackList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addPackagesToClearCacheBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addPackagesToClearCacheBlackList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean addPackagesToClearCacheWhiteList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "addPackagesToClearCacheWhiteList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addPackagesToClearCacheWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addPackagesToClearCacheWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean addPackagesToClearDataBlackList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "addPackagesToClearDataBlackList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addPackagesToClearDataBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addPackagesToClearDataBlackList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean addPackagesToClearDataWhiteList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "addPackagesToClearDataWhiteList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addPackagesToClearDataWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addPackagesToClearDataWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean addPackagesToDisableClipboardBlackList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "addPackagesToDisableClipboardBlackList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addPackagesToDisableClipboardBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addPackagesToDisableClipboardBlackList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean addPackagesToDisableClipboardWhiteList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "addPackagesToDisableClipboardWhiteList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addPackagesToDisableClipboardWhiteList(List<String>");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addPackagesToDisableClipboardWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean addPackagesToDisableUpdateBlackList(List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addPackagesToDisableUpdateBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addPackagesToDisableUpdateBlackList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean addPackagesToDisableUpdateWhiteList(List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addPackagesToDisableUpdateWhiteList(List<String>)");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addPackagesToDisableUpdateWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean addPackagesToFocusMonitoringList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "addPackagesToFocusMonitoringList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addPackagesToFocusMonitoringList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addPackagesToFocusMonitoringList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean addPackagesToForceStopBlackList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "addPackagesToForceStopBlackList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addPackagesToForceStopBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addPackagesToForceStopBlackList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean addPackagesToForceStopWhiteList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "addPackagesToForceStopWhiteList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addPackagesToForceStopWhiteList(List<String>)");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addPackagesToForceStopWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean addPackagesToNotificationBlackList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "addPackagesToNotificationBlackList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addPackagesToNotificationBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addAppNotificationBlackList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean addPackagesToNotificationWhiteList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "addPackagesToNotificationWhiteList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addPackagesToNotificationWhiteList(List<String>)");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addAppNotificationWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public List<String> addPackagesToPreventStartBlackList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "addPackagesToPreventStartBlackList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addPackagesToPreventStartBlackList");
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.addPackagesToPreventStartBlackList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return null;
        }
    }

    public boolean addPackagesToWidgetBlackList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "addPackagesToWidgetBlackList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addPackagesToWidgetBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addPackagesToWidgetBlackList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean addPackagesToWidgetWhiteList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "addPackagesToWidgetWhiteList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addPackagesToWidgetWhiteList(List<String>)");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addPackagesToWidgetWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean addUsbDevicesForDefaultAccess(String str, List<UsbDeviceConfig> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.addUsbDevicesForDefaultAccess");
        try {
            if (getService() != null) {
                return this.mService.addUsbDevicesForDefaultAccess(this.mContextInfo, str, list);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to ApplicationPolicy service ", e);
            return false;
        }
    }

    public int applyRuntimePermissions(AppIdentity appIdentity, List<String> list, int i) {
        AccessController.throwIfParentInstance(this.mContextInfo, "applyRuntimePermissions");
        logUsage("ApplicationPolicy.applyRuntimePermissions");
        if (EdmConstants.getEnterpriseSdkVerInternal().compareTo(EdmConstants.EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_6) < 0) {
            Log.d(TAG, "applyRuntimePermissions : support above ENTERPRISE_SDK_VERSION_5_6");
            return -2;
        }
        if (getService() != null) {
            try {
                return this.mService.applyRuntimePermissions(this.mContextInfo, appIdentity, list, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return -2;
    }

    public boolean changeApplicationIcon(String str, byte[] bArr) {
        AccessController.throwIfParentInstance(this.mContextInfo, "changeApplicationIcon");
        Log.i(TAG, "changeApplicationIcon:packageName " + str);
        logUsage("ApplicationPolicy.changeApplicationIcon");
        if (getService() != null) {
            if (bArr != null) {
                try {
                    if (bArr.length > 1048576) {
                        Log.w(TAG, "changeApplicationIcon: Icon size is too big :" + bArr.length);
                        return false;
                    }
                } catch (RemoteException e) {
                    Log.w(TAG, "Failed talking with application policy", e);
                }
            }
            return this.mService.changeApplicationIcon(this.mContextInfo, str, bArr);
        }
        return false;
    }

    public boolean changeApplicationName(String str, String str2) {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "changeApplicationName", this, "ApplicationPolicy.changeApplicationName") == null) {
            return false;
        }
        try {
            return this.mService.changeApplicationName(this.mContextInfo, str, str2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public final void checkPathAccessSecured(String str) {
        try {
            if (!str.contains("sdcard") && !str.contains("storage")) {
                StructStat stat = Os.stat(str);
                int i = OsConstants.S_IWOTH;
                if (OsConstants.S_ISREG(stat.st_mode) && (stat.st_mode & i) == i) {
                    AuditLog.logEventAsUser(UserHandle.getCallingUserId(), 361, new Object[]{str});
                    return;
                }
                return;
            }
            AuditLog.logEventAsUser(UserHandle.getCallingUserId(), 361, new Object[]{str});
        } catch (Exception unused) {
        }
    }

    public boolean clearAppPackageNameFromList() {
        logUsage("ApplicationPolicy.clearAppPackageNameFromList");
        List<AppControlInfo> appPackageNamesAllBlackLists = getAppPackageNamesAllBlackLists();
        List<AppControlInfo> appPackageNamesAllWhiteLists = getAppPackageNamesAllWhiteLists();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (appPackageNamesAllBlackLists == null && appPackageNamesAllWhiteLists == null) {
            return false;
        }
        List<String> allUniqueAdminPackageNames = getAllUniqueAdminPackageNames(appPackageNamesAllBlackLists, arrayList);
        List<String> allUniqueAdminPackageNames2 = getAllUniqueAdminPackageNames(appPackageNamesAllWhiteLists, arrayList2);
        Iterator<String> it = allUniqueAdminPackageNames.iterator();
        boolean z = true;
        while (it.hasNext()) {
            if (!removeAppPackageNameFromBlackList(it.next())) {
                z = false;
            }
        }
        Iterator<String> it2 = allUniqueAdminPackageNames2.iterator();
        boolean z2 = true;
        while (it2.hasNext()) {
            if (!removeAppPackageNameFromWhiteList(it2.next())) {
                z2 = false;
            }
        }
        return z && z2;
    }

    public boolean clearAppSignatureFromList() {
        boolean z;
        boolean z2;
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.clearAppSignatureFromList ");
        String[] appSignaturesBlackList = getAppSignaturesBlackList();
        String[] appSignaturesWhiteList = getAppSignaturesWhiteList();
        if (appSignaturesBlackList != null) {
            z = true;
            for (String str : appSignaturesBlackList) {
                if (!removeAppSignatureFromBlackList(str)) {
                    z = false;
                }
            }
        } else {
            z = true;
        }
        if (appSignaturesWhiteList != null) {
            z2 = true;
            for (String str2 : appSignaturesWhiteList) {
                if (!removeAppSignatureFromWhiteList(str2)) {
                    z2 = false;
                }
            }
        } else {
            z2 = true;
        }
        return z && z2;
    }

    public boolean clearDisableClipboardBlackList() {
        AccessController.throwIfParentInstance(this.mContextInfo, "clearDisableClipboardBlackList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.clearDisableClipboardBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.clearDisableClipboardBlackList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean clearDisableClipboardWhiteList() {
        AccessController.throwIfParentInstance(this.mContextInfo, "clearDisableClipboardWhiteList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.clearDisableClipboardWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.clearDisableClipboardWhiteList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean clearDisableUpdateBlackList() {
        logUsage("ApplicationPolicy.clearDisableUpdateBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.clearDisableUpdateBlackList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean clearDisableUpdateWhiteList() {
        logUsage("ApplicationPolicy.clearDisableUpdateWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.clearDisableUpdateWhiteList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean clearFocusMonitoringList() {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "clearFocusMonitoringList", this, "ApplicationPolicy.clearFocusMonitoringList") == null) {
            return false;
        }
        try {
            return this.mService.clearFocusMonitoringList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean clearNewAdminActivationAppWhiteList() {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.clearNewAdminActivationAppWhiteList");
        if (getRestrictionService() == null) {
            return false;
        }
        try {
            return this.mRestrictionService.clearNewAdminActivationAppWhiteList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed clearNewAdminActivationAppWhiteList", e);
            return false;
        }
    }

    public boolean clearPackagesFromDisableClipboardList() {
        AccessController.throwIfParentInstance(this.mContextInfo, "clearPackagesFromDisableClipboardList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.clearPackagesFromDisableClipboardList");
        return clearDisableClipboardBlackList() && clearDisableClipboardWhiteList();
    }

    public boolean clearPackagesFromDisableUpdateList() {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.clearPackagesFromDisableUpdateList");
        return clearDisableUpdateBlackList() && clearDisableUpdateWhiteList();
    }

    public boolean clearPackagesFromForceStopList() {
        AccessController.throwIfParentInstance(this.mContextInfo, "clearPackagesFromForceStopList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.clearPackagesFromForceStopList");
        List<String> packagesFromForceStopBlackList = getPackagesFromForceStopBlackList();
        List<String> packagesFromForceStopWhiteList = getPackagesFromForceStopWhiteList();
        if (packagesFromForceStopBlackList != null && packagesFromForceStopWhiteList != null) {
            boolean removePackagesFromForceStopBlackList = removePackagesFromForceStopBlackList(packagesFromForceStopBlackList);
            boolean removePackagesFromForceStopWhiteList = removePackagesFromForceStopWhiteList(packagesFromForceStopWhiteList);
            if (removePackagesFromForceStopBlackList && removePackagesFromForceStopWhiteList) {
                return true;
            }
        }
        return false;
    }

    public boolean clearPackagesFromNotificationList() {
        AccessController.throwIfParentInstance(this.mContextInfo, "clearPackagesFromNotificationList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.clearPackagesFromNotificationList");
        List<String> packagesFromNotificationBlackList = getPackagesFromNotificationBlackList();
        List<String> packagesFromNotificationWhiteList = getPackagesFromNotificationWhiteList();
        if (packagesFromNotificationBlackList != null && packagesFromNotificationWhiteList != null) {
            boolean removePackagesFromNotificationBlackList = removePackagesFromNotificationBlackList(packagesFromNotificationBlackList);
            boolean removePackagesFromNotificationWhiteList = removePackagesFromNotificationWhiteList(packagesFromNotificationWhiteList);
            if (removePackagesFromNotificationBlackList && removePackagesFromNotificationWhiteList) {
                return true;
            }
        }
        return false;
    }

    public boolean clearPackagesFromWidgetList() {
        AccessController.throwIfParentInstance(this.mContextInfo, "clearPackagesFromWidgetList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.clearPackagesFromWidgetList ");
        List<String> packagesFromWidgetBlackList = getPackagesFromWidgetBlackList();
        List<String> packagesFromWidgetWhiteList = getPackagesFromWidgetWhiteList();
        if (packagesFromWidgetBlackList != null && packagesFromWidgetWhiteList != null) {
            boolean removePackagesFromWidgetBlackList = removePackagesFromWidgetBlackList(packagesFromWidgetBlackList);
            boolean removePackagesFromWidgetWhiteList = removePackagesFromWidgetWhiteList(packagesFromWidgetWhiteList);
            if (removePackagesFromWidgetBlackList && removePackagesFromWidgetWhiteList) {
                return true;
            }
        }
        return false;
    }

    public boolean clearPreventStartBlackList() {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "clearPreventStartBlackList", this, "ApplicationPolicy.clearPreventStartBlackList") == null) {
            return false;
        }
        try {
            return this.mService.clearPreventStartBlackList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean clearUsbDevicesForDefaultAccess(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.clearUsbDevicesForDefaultAccess");
        try {
            if (getService() != null) {
                return this.mService.clearUsbDevicesForDefaultAccess(this.mContextInfo, str);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to ApplicationPolicy service ", e);
            return false;
        }
    }

    public boolean deleteHomeShortcut(String str, String str2) {
        logUsage("ApplicationPolicy.deleteHomeShortcut");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.deleteHomeShortcut(this.mContextInfo, str, str2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed deleteHomeShorcut!!!", e);
            return false;
        }
    }

    public void disableAndroidBrowser() {
        logUsage("ApplicationPolicy.disableAndroidBrowser");
        setApplicationState("com.android.browser", false);
        setApplicationState("com.sec.webbrowserminiapp", false);
        setApplicationState("com.android.chrome", false);
        setApplicationState("com.sec.android.app.sbrowser", false);
        setApplicationState("com.android.browser.provider", false);
        Log.d(TAG, "set all browser as disable");
    }

    public void disableAndroidMarket() {
        logUsage("ApplicationPolicy.disableAndroidMarket");
        if (getService() != null) {
            try {
                this.mService.setAndroidMarketState(this.mContextInfo, false);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
    }

    public void disableVoiceDialer() {
        logUsage("ApplicationPolicy.disableVoiceDialer");
        setApplicationState("com.android.voicedialer", false);
        setApplicationState("com.vlingo.client.samsung", false);
        setApplicationState(RestrictionPolicy.SVOICE_PACKAGE1, false);
        setApplicationState("com.google.android.googlequicksearchbox", false);
        setApplicationState(RestrictionPolicy.SVOICE_PACKAGE2, false);
    }

    public void disableYouTube() {
        logUsage("ApplicationPolicy.disableYouTube");
        setApplicationState("com.google.android.youtube", false);
    }

    public void doSelfUninstall() {
        if (getService() != null) {
            try {
                Log.d(TAG, "doSelfUninstall");
                this.mService.doSelfUninstall(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking to ApplicationPolicy service", e);
            }
        }
    }

    public void enableAndroidBrowser() {
        logUsage("ApplicationPolicy.enableAndroidBrowser");
        setApplicationState("com.android.browser", true);
        setApplicationState("com.sec.webbrowserminiapp", true);
        setApplicationState("com.android.chrome", true);
        setApplicationState("com.sec.android.app.sbrowser", true);
        setApplicationState("com.android.browser.provider", true);
        Log.d(TAG, "set all browser as enable");
    }

    public void enableAndroidMarket() {
        logUsage("ApplicationPolicy.enableAndroidMarket");
        if (getService() != null) {
            try {
                this.mService.setAndroidMarketState(this.mContextInfo, true);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
    }

    public void enableVoiceDialer() {
        logUsage("ApplicationPolicy.enableVoiceDialer");
        setApplicationState("com.android.voicedialer", true);
        setApplicationState("com.vlingo.client.samsung", true);
        setApplicationState(RestrictionPolicy.SVOICE_PACKAGE1, true);
        setApplicationState("com.google.android.googlequicksearchbox", true);
        setApplicationState(RestrictionPolicy.SVOICE_PACKAGE2, true);
    }

    public void enableYouTube() {
        logUsage("ApplicationPolicy.enableYouTube");
        setApplicationState("com.google.android.youtube", true);
    }

    public boolean getAddHomeShorcutRequested() {
        if (getService() == null) {
            return false;
        }
        try {
            Log.d(TAG, "getAddHomeShorcutRequested");
            return this.mService.getAddHomeShorcutRequested();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to ApplicationPolicy service ", e);
            return false;
        }
    }

    public AppInfoLastUsage[] getAllAppLastUsage() {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "getAllAppLastUsage", this, "ApplicationPolicy.getAllAppLastUsage") != null) {
            try {
                return this.mService.getAllAppLastUsage(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new AppInfoLastUsage[0];
    }

    public List<DefaultAppConfiguration> getAllDefaultApplications() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getAllDefaultApplications");
        if (getService() == null) {
            return null;
        }
        try {
            Log.d(TAG, "getAllDefaultApplications");
            return this.mService.getAllDefaultApplications(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to ApplicationPolicy service ", e);
            return null;
        }
    }

    public final List<String> getAllUniqueAdminPackageNames(List<AppControlInfo> list, List<String> list2) {
        HashSet hashSet = new HashSet();
        if (list2 == null) {
            return new ArrayList();
        }
        if (list == null) {
            return list2;
        }
        hashSet.addAll(list2);
        Iterator<AppControlInfo> it = list.iterator();
        while (it.hasNext()) {
            List<String> list3 = it.next().entries;
            if (list3 != null) {
                hashSet.addAll(list3);
            }
        }
        list2.addAll(hashSet);
        return list2;
    }

    public Map<AppWidgetProviderInfo, ArrayList<Integer>> getAllWidgets(String str) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getAllWidgets");
        if (getService() != null) {
            try {
                return this.mService.getAllWidgets(this.mContextInfo, str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new HashMap();
    }

    public List<AppControlInfo> getAppPackageNamesAllBlackLists() {
        if (getService() != null) {
            try {
                return this.mService.getAppPackageNamesAllBlackLists(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new ArrayList();
    }

    public List<AppControlInfo> getAppPackageNamesAllWhiteLists() {
        if (getService() != null) {
            try {
                return this.mService.getAppPackageNamesAllWhiteLists(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new ArrayList();
    }

    public List<AppControlInfo> getAppPermissionsAllBlackLists() {
        if (getService() != null) {
            try {
                return this.mService.getAppPermissionsAllBlackLists(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new ArrayList();
    }

    public String[] getAppPermissionsBlackList() {
        if (getService() != null) {
            try {
                return this.mService.getAppPermissionsBlackList(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new String[0];
    }

    public List<AppControlInfo> getAppSignaturesAllBlackLists() {
        if (getService() != null) {
            try {
                return this.mService.getAppSignaturesAllBlackLists(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new ArrayList();
    }

    public List<AppControlInfo> getAppSignaturesAllWhiteLists() {
        if (getService() != null) {
            try {
                return this.mService.getAppSignaturesAllWhiteLists(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new ArrayList();
    }

    public String[] getAppSignaturesBlackList() {
        if (getService() != null) {
            try {
                return this.mService.getAppSignatureBlackList(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new String[0];
    }

    public String[] getAppSignaturesWhiteList() {
        if (getService() != null) {
            try {
                return this.mService.getAppSignaturesWhiteList(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new String[0];
    }

    public long getApplicationCacheSize(String str) {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "getApplicationCacheSize", this, "ApplicationPolicy.getApplicationCacheSize") == null) {
            return -1L;
        }
        try {
            return this.mService.getApplicationCacheSize(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return -1L;
        }
    }

    public long getApplicationCodeSize(String str) {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "getApplicationCodeSize", this, "ApplicationPolicy.getApplicationCodeSize") == null) {
            return -1L;
        }
        try {
            return this.mService.getApplicationCodeSize(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return -1L;
        }
    }

    public boolean getApplicationComponentState(ComponentName componentName) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getApplicationComponentState(this.mContextInfo, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return true;
        }
    }

    public long getApplicationCpuUsage(String str) {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "getApplicationCpuUsage", this, "ApplicationPolicy.getApplicationCpuUsage") == null) {
            return -1L;
        }
        try {
            return this.mService.getApplicationCpuUsage(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return -1L;
        }
    }

    public long getApplicationDataSize(String str) {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "getApplicationDataSize", this, "ApplicationPolicy.getApplicationDataSize") == null) {
            return -1L;
        }
        try {
            return this.mService.getApplicationDataSize(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return -1L;
        }
    }

    public List<String> getApplicationGrantedPermissions(String str) {
        logUsage("ApplicationPolicy.getApplicationGrantedPermissions", true);
        if (getService() != null) {
            try {
                return this.mService.getApplicationGrantedPermissions(this.mContextInfo, str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new ArrayList();
    }

    public byte[] getApplicationIconFromDb(String str) {
        if (getService() != null) {
            try {
                return this.mService.getApplicationIconFromDb(this.mContextInfo, str);
            } catch (RemoteException e) {
                Log.w(TAG, "getCustomApplicationIcon: Failed talking with Application control policy", e);
            }
        }
        return null;
    }

    public boolean getApplicationInstallationEnabled(String str) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getApplicationInstallationEnabled(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return true;
        }
    }

    public int getApplicationInstallationMode() {
        if (getService() == null) {
            return 1;
        }
        try {
            return this.mService.getAppInstallationMode(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return 1;
        }
    }

    public long getApplicationMemoryUsage(String str) {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "getApplicationMemoryUsage", this, "ApplicationPolicy.getApplicationMemoryUsage") == null) {
            return -1L;
        }
        try {
            return this.mService.getApplicationMemoryUsage(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return -1L;
        }
    }

    public String getApplicationName(String str) {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "getApplicationName", this, "ApplicationPolicy.getApplicationName") == null) {
            return null;
        }
        try {
            return this.mService.getApplicationName(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return null;
        }
    }

    public String getApplicationNameFromDb(String str, int i) {
        if (getService() != null) {
            try {
                return this.mService.getApplicationNameFromDb(str, i);
            } catch (RemoteException e) {
                Log.w(TAG, "getCustomApplicationName: Failed talking with Application control policy", e);
            }
        }
        return null;
    }

    public List<NetworkStats> getApplicationNetworkStats() {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "getApplicationNetworkStats", this, "ApplicationPolicy.getApplicationNetworkStats") == null) {
            return null;
        }
        try {
            return this.mService.getNetworkStats(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return null;
        }
    }

    public int getApplicationNotificationMode() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getApplicationNotificationMode");
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.getApplicationNotificationMode(this.mContextInfo, true);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return -1;
        }
    }

    public int getApplicationNotificationModeAsUser(int i) {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.getApplicationNotificationModeAsUser(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return -1;
        }
    }

    public List<String> getApplicationPackagesFromCameraAllowList() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getApplicationPackagesFromCameraAllowList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return null;
        }
    }

    public Bundle getApplicationRestrictions(ComponentName componentName, String str) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getApplicationRestrictions");
        if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 17 || getService() == null) {
            return null;
        }
        try {
            return this.mService.getApplicationRestrictions(componentName, str, EdmUtils.getCallingUserId(this.mContextInfo));
        } catch (RemoteException e) {
            Log.e(TAG, "Failed talking to Device Policy Manager service", e);
            return null;
        }
    }

    public boolean getApplicationStateEnabled(String str) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getApplicationStateEnabled(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return true;
        }
    }

    public String[] getApplicationStateList(boolean z) {
        if (getService() != null) {
            try {
                return this.mService.getApplicationStateList(this.mContextInfo, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with Application policy", e);
            }
        }
        return new String[0];
    }

    public long getApplicationTotalSize(String str) {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "getApplicationTotalSize", this, "ApplicationPolicy.getApplicationTotalSize") == null) {
            return -1L;
        }
        try {
            return this.mService.getApplicationTotalSize(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return -1L;
        }
    }

    public int getApplicationUid(String str) {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "getApplicationUid", this, "ApplicationPolicy.getApplicationUid") == null) {
            return -1;
        }
        try {
            return this.mService.getApplicationUid(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return -1;
        }
    }

    public boolean getApplicationUninstallationEnabled(String str) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getApplicationUninstallationEnabled");
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getApplicationUninstallationEnabled(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return true;
        }
    }

    public int getApplicationUninstallationMode() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getApplicationUninstallationMode");
        if (getService() == null) {
            return 1;
        }
        try {
            return this.mService.getApplicationUninstallationMode(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return 1;
        }
    }

    public String getApplicationVersion(String str) {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "getApplicationVersion", this, "ApplicationPolicy.getApplicationVersion") == null) {
            return null;
        }
        try {
            return this.mService.getApplicationVersion(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return null;
        }
    }

    public int getApplicationVersionCode(String str) {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "getApplicationVersionCode", this, "ApplicationPolicy.getApplicationVersionCode") == null) {
            return -1;
        }
        try {
            return this.mService.getApplicationVersionCode(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return -1;
        }
    }

    public List<String> getAuthorizedScopes(String str) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getAuthorizedScopes");
        logUsage("ApplicationPolicy.getAuthorizedScopes", true);
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getAuthorizedScopes(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return null;
        }
    }

    public AppInfoLastUsage[] getAvgNoAppUsagePerMonth() {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "AppInfoLastUsage", this, "ApplicationPolicy.getAvgNoAppUsagePerMonth") != null) {
            try {
                return this.mService.getAvgNoAppUsagePerMonth(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new AppInfoLastUsage[0];
    }

    public boolean getConcentrationMode() {
        try {
            if (getService() != null) {
                return this.mService.getConcentrationMode();
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, " Failed talking to ApplicationPolicy service ", e);
            return false;
        }
    }

    public ComponentName getDefaultApplication(Intent intent) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getDefaultApplication");
        if (getService() == null) {
            return null;
        }
        try {
            Log.d(TAG, "getDefaultApplication");
            return this.mService.getDefaultApplication(this.mContextInfo, intent);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to ApplicationPolicy service ", e);
            return null;
        }
    }

    public ComponentName getDefaultApplicationInternal(Intent intent, int i) {
        if (getService() == null) {
            return null;
        }
        try {
            Log.d(TAG, "getDefaultApplicationInternal");
            return this.mService.getDefaultApplicationInternal(intent, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to ApplicationPolicy service ", e);
            return null;
        }
    }

    public List<String> getDisabledPackages(int i) {
        logUsage("ApplicationPolicy.getDisabledPackages", true);
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getDisabledPackages(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to ApplicationPolicy service ", e);
            return null;
        }
    }

    public List<ComponentName> getHomeShortcuts(String str, boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getHomeShortcuts");
        if (getService() != null) {
            try {
                return this.mService.getHomeShortcuts(this.mContextInfo, str, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new ArrayList();
    }

    public String[] getInstalledApplicationsIDList() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getInstalledApplicationsIDList");
        if (getService() != null) {
            try {
                return this.mService.getInstalledApplicationsIDList(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new String[0];
    }

    public List<AppInfo> getMostCpuUsageApps(int i, boolean z) {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "getMostCpuUsageApps", this, "ApplicationPolicy.getMostCpuUsageApps") != null) {
            try {
                return this.mService.getTopNCPUUsageApp(this.mContextInfo, i, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new ArrayList();
    }

    public List<AppInfo> getMostDataUsageApps(int i) {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "getMostDataUsageApps", this, "ApplicationPolicy.getMostDataUsageApps") != null) {
            try {
                return this.mService.getTopNDataUsageApp(this.mContextInfo, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new ArrayList();
    }

    public List<AppInfo> getMostMemoryUsageApps(int i, boolean z) {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "getMostMemoryUsageApps", this, "ApplicationPolicy.getMostMemoryUsageApps") != null) {
            try {
                return this.mService.getTopNMemoryUsageApp(this.mContextInfo, i, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new ArrayList();
    }

    public String getMySinglePackageName() {
        return null;
    }

    public List<String> getNewAdminActivationAppWhiteList() {
        if (getRestrictionService() == null) {
            return null;
        }
        try {
            return this.mRestrictionService.getNewAdminActivationAppWhiteList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed getNewAdminActivationAppWhiteList", e);
            return null;
        }
    }

    public List<String> getPackagesFromBatteryOptimizationWhiteList() {
        if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 17 || getService() == null) {
            return null;
        }
        try {
            return this.mService.getPackagesFromBatteryOptimizationWhiteList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return null;
        }
    }

    public List<String> getPackagesFromBlackList(int i) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getPackagesFromBlackList");
        if (getService() != null) {
            try {
                return this.mService.getPackagesFromBlackList(this.mContextInfo, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking to application policy", e);
            }
        }
        return new ArrayList();
    }

    public List<String> getPackagesFromClearCacheBlackList() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getPackagesFromClearCacheBlackList");
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getPackagesFromClearCacheBlackList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return null;
        }
    }

    public List<String> getPackagesFromClearCacheWhiteList() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getPackagesFromClearCacheWhiteList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.getPackagesFromClearCacheWhiteList", true);
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getPackagesFromClearCacheWhiteList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return null;
        }
    }

    public List<String> getPackagesFromClearDataBlackList() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getPackagesFromClearDataBlackList");
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getPackagesFromClearDataBlackList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return null;
        }
    }

    public List<String> getPackagesFromClearDataWhiteList() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getPackagesFromClearDataWhiteList");
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getPackagesFromClearDataWhiteList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return null;
        }
    }

    public List<String> getPackagesFromDisableClipboardBlackList() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getPackagesFromDisableClipboardBlackList");
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getPackagesFromDisableClipboardBlackList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return null;
        }
    }

    public List<String> getPackagesFromDisableClipboardWhiteList() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getPackagesFromDisableClipboardWhiteList");
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getPackagesFromDisableClipboardWhiteList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return null;
        }
    }

    public List<String> getPackagesFromDisableUpdateBlackList() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getPackagesFromDisableUpdateBlackList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return null;
        }
    }

    public List<String> getPackagesFromDisableUpdateWhiteList() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getPackagesFromDisableUpdateWhiteList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return null;
        }
    }

    public List<String> getPackagesFromFocusMonitoringList() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getPackagesFromFocusMonitoringList");
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getPackagesFromFocusMonitoringList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return null;
        }
    }

    public List<String> getPackagesFromForceStopBlackList() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getPackagesFromForceStopBlackList");
        if (getService() != null) {
            try {
                return this.mService.getPackagesFromForceStopBlackList(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new ArrayList();
    }

    public List<String> getPackagesFromForceStopWhiteList() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getPackagesFromForceStopWhiteList");
        if (getService() != null) {
            try {
                return this.mService.getPackagesFromForceStopWhiteList(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new ArrayList();
    }

    public List<String> getPackagesFromNotificationBlackList() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getPackagesFromNotificationBlackList");
        if (getService() != null) {
            try {
                return this.mService.getAppNotificationBlackList(this.mContextInfo, true);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new ArrayList();
    }

    public List<String> getPackagesFromNotificationWhiteList() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getPackagesFromNotificationWhiteList");
        if (getService() != null) {
            try {
                return this.mService.getAppNotificationWhiteList(this.mContextInfo, true);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new ArrayList();
    }

    public List<String> getPackagesFromPreventStartBlackList() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getPackagesFromPreventStartBlackList");
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getPackagesFromPreventStartBlackList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return null;
        }
    }

    public List<String> getPackagesFromWhiteList(int i) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getPackagesFromWhiteList");
        if (getService() != null) {
            try {
                return this.mService.getPackagesFromWhiteList(this.mContextInfo, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking to application policy", e);
            }
        }
        return new ArrayList();
    }

    public List<String> getPackagesFromWidgetBlackList() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getPackagesFromWidgetBlackList");
        if (getService() != null) {
            try {
                return this.mService.getPackagesFromWidgetBlackList(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new ArrayList();
    }

    public List<String> getPackagesFromWidgetWhiteList() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getPackagesFromWidgetWhiteList");
        if (getService() != null) {
            try {
                return this.mService.getPackagesFromWidgetWhiteList(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new ArrayList();
    }

    public final IRestrictionPolicy getRestrictionService() {
        if (this.mRestrictionService == null) {
            this.mRestrictionService = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
        }
        return this.mRestrictionService;
    }

    public List<String> getRuntimePermissions(String str, int i) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getRuntimePermissions");
        if (EdmConstants.getEnterpriseSdkVerInternal().compareTo(EdmConstants.EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_6) < 0) {
            Log.d(TAG, "getRuntimePermissions : support above ENTERPRISE_SDK_VERSION_5_6");
            return null;
        }
        if (getService() != null) {
            try {
                return this.mService.getRuntimePermissions(this.mContextInfo, str, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return null;
    }

    public List<String> getRuntimePermissionsEnforced(int i, String str, int i2) {
        Log.d(TAG, "ApplicationPolicy.getRuntimePermissionsEnforced");
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getRuntimePermissionsEnforced(i, str, i2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return null;
        }
    }

    public final IApplicationPolicy getService() {
        if (this.mService == null) {
            this.mService = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
        }
        return this.mService;
    }

    public List<UsbDeviceConfig> getUsbDevicesForDefaultAccess(String str) {
        try {
            if (getService() != null) {
                return this.mService.getUsbDevicesForDefaultAccess(this.mContextInfo, str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to ApplicationPolicy service ", e);
            return null;
        }
    }

    public final IUserManager getUserService() {
        if (this.mUm == null) {
            this.mUm = IUserManager.Stub.asInterface(ServiceManager.getService("user"));
        }
        return this.mUm;
    }

    public boolean installApplication(String str, boolean z) {
        logUsage("ApplicationPolicy.installApplication(String, boolean)");
        if (getService() != null) {
            return installOrUpdateApplicationInternal(str, z, true);
        }
        return false;
    }

    public boolean installApplicationWithoutPermissions(String str, boolean z) {
        logUsage("ApplicationPolicy.installApplicationWithoutPermissions(String, boolean)");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.installExistingApplication(this.mContextInfo, str, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00ba A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:51:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00e1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean installOrUpdateApplicationInternal(java.lang.String r12, boolean r13, boolean r14) {
        /*
            Method dump skipped, instructions count: 267
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.application.ApplicationPolicy.installOrUpdateApplicationInternal(java.lang.String, boolean, boolean):boolean");
    }

    public boolean isApplicationClearCacheDisabled(String str, int i, boolean z) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isApplicationClearCacheDisabled(str, i, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean isApplicationClearDataDisabled(String str, int i, boolean z) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isApplicationClearDataDisabled(str, i, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean isApplicationFocusMonitoredAsUser(String str, int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isApplicationFocusMonitoredAsUser(str, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean isApplicationForceStopDisabled(String str, int i, String str2, String str3, String str4, boolean z) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isApplicationForceStopDisabled(str, i, str2, str3, str4, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean isApplicationInstalled(String str) {
        AccessController.throwIfParentInstance(this.mContextInfo, "isApplicationInstalled");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isApplicationInstalled(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean isApplicationRunning(String str) {
        AccessController.throwIfParentInstance(this.mContextInfo, "isApplicationRunning");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isApplicationRunning(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean isApplicationSetToDefault(String str, int i) {
        if (getService() == null) {
            return false;
        }
        try {
            Log.d(TAG, "isApplicationSetToDefault");
            return this.mService.isApplicationSetToDefault(str, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to ApplicationPolicy service ", e);
            return false;
        }
    }

    public boolean isApplicationStartDisabledAsUser(String str, int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isApplicationStartDisabledAsUser(str, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean isIntentDisabled(Intent intent) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isIntentDisabled(intent);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Application policy", e);
            return false;
        }
    }

    public boolean isNewAdminActivationEnabled(boolean z) {
        if (getRestrictionService() == null) {
            return false;
        }
        try {
            return this.mRestrictionService.isNewAdminActivationEnabled(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed isNewAdminActivationEnabled", e);
            return false;
        }
    }

    public boolean isNewAdminInstallationEnabled(boolean z) {
        if (getRestrictionService() == null) {
            return false;
        }
        try {
            return this.mRestrictionService.isNewAdminInstallationEnabled(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed isNewAdminInstallationEnabled", e);
            return false;
        }
    }

    public boolean isPackageClipboardAllowed(String str, int i) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isPackageClipboardAllowed(str, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return true;
        }
    }

    public boolean isPackageInBlacklistInternal(int i, int i2, int i3) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isPackageInBlacklistInternal(i, i2, i3);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed talking to application policy");
            return false;
        }
    }

    public boolean isPackageInWhitelistInternal(int i, int i2, int i3) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isPackageInWhitelistInternal(i, i2, i3);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed talking to application policy");
            return false;
        }
    }

    public boolean isPackageUpdateAllowed(String str, boolean z) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("Calling uid does not have permission to do this operation");
        }
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isPackageUpdateAllowed(str, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return true;
        }
    }

    public boolean isStatusBarNotificationAllowedAsUser(String str, int i) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isStatusBarNotificationAllowedAsUser(str, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return true;
        }
    }

    public void logUsage(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, str);
    }

    public boolean preventNewAdminActivation(boolean z) {
        logUsage("ApplicationPolicy.preventNewAdminActivation");
        if (getRestrictionService() == null) {
            return false;
        }
        try {
            return this.mRestrictionService.preventNewAdminActivation(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed preventNewAdminActivation", e);
            return false;
        }
    }

    public boolean preventNewAdminInstallation(boolean z) {
        logUsage("ApplicationPolicy.preventNewAdminInstallation");
        if (getRestrictionService() == null) {
            return false;
        }
        try {
            return this.mRestrictionService.preventNewAdminInstallation(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed preventNewAdminInstallation", e);
            return false;
        }
    }

    public void reapplyRuntimePermissions(int i) {
        Log.d(TAG, "ApplicationPolicy.reapplyRuntimePermissions");
        if (getService() != null) {
            try {
                this.mService.reapplyRuntimePermissions(i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
    }

    public boolean removeAppPackageNameFromBlackList(String str) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removeAppPackageNameFromBlackList(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean removeAppPackageNameFromWhiteList(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removeAppPackageNameFromWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removeAppPackageNameFromWhiteList(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean removeAppPermissionFromBlackList(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removeAppPermissionFromBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removeAppPermissionFromBlackList(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean removeAppSignatureFromBlackList(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removeAppSignatureFromBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removeAppSignatureFromBlackList(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean removeAppSignatureFromWhiteList(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removeAppSignatureFromWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removeAppSignatureFromWhiteList(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public int removeApplicationFromCameraAllowList(AppIdentity appIdentity) {
        logUsage("ApplicationPolicy.removeAppSignatureFromCameraAllowList");
        if (getService() == null) {
            return -2;
        }
        try {
            return this.mService.removeApplicationFromCameraAllowList(this.mContextInfo, appIdentity);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return -2;
        }
    }

    public boolean removeDefaultApplication(Intent intent, ComponentName componentName) {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "removeDefaultApplication", this, "ApplicationPolicy.removeDefaultApplication") == null) {
            return false;
        }
        try {
            Log.d(TAG, "removeDefaultApplication");
            return this.mService.removeDefaultApplication(this.mContextInfo, intent, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to ApplicationPolicy service ", e);
            return false;
        }
    }

    public int removePackageFromBatteryOptimizationWhiteList(AppIdentity appIdentity) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removePackageFromBatteryOptimizationWhiteList");
        if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 17 || getService() == null) {
            return -2;
        }
        try {
            return this.mService.removePackageFromBatteryOptimizationWhiteList(this.mContextInfo, appIdentity);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with ApplicationPolicy service", e);
            return -2;
        }
    }

    public int removePackageFromBlackList(int i, String str) {
        AccessController.throwIfParentInstance(this.mContextInfo, "removePackageFromBlackList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removePackageFromBlackList");
        if (getService() == null) {
            return -2;
        }
        try {
            return this.mService.removePackageFromBlackList(this.mContextInfo, i, str);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed talking to application policy");
            return -2;
        }
    }

    public int removePackageFromWhiteList(int i, String str) {
        AccessController.throwIfParentInstance(this.mContextInfo, "removePackageFromWhiteList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removePackageFromWhiteList");
        if (getService() == null) {
            return -2;
        }
        try {
            return this.mService.removePackageFromWhiteList(this.mContextInfo, i, str);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed talking to application policy");
            return -2;
        }
    }

    public boolean removePackagesFromClearCacheBlackList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "removePackagesFromClearCacheBlackList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removePackagesFromClearCacheBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removePackagesFromClearCacheBlackList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean removePackagesFromClearCacheWhiteList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "removePackagesFromClearCacheWhiteList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removePackagesFromClearCacheWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removePackagesFromClearCacheWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean removePackagesFromClearDataBlackList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "removePackagesFromClearDataBlackList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removePackagesFromClearDataBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removePackagesFromClearDataBlackList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean removePackagesFromClearDataWhiteList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "removePackagesFromClearDataWhiteList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removePackagesFromClearDataWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removePackagesFromClearDataWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean removePackagesFromDisableClipboardBlackList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "removePackagesFromDisableClipboardBlackList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removePackagesFromDisableClipboardBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removePackagesFromDisableClipboardBlackList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean removePackagesFromDisableClipboardWhiteList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "removePackagesFromDisableClipboardWhiteList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removePackagesFromDisableClipboardWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removePackagesFromDisableClipboardWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean removePackagesFromDisableUpdateBlackList(List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removePackagesFromDisableUpdateBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removePackagesFromDisableUpdateBlackList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean removePackagesFromDisableUpdateWhiteList(List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removePackagesFromDisableUpdateWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removePackagesFromDisableUpdateWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean removePackagesFromFocusMonitoringList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "removePackagesFromFocusMonitoringList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removePackagesFromFocusMonitoringList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removePackagesFromFocusMonitoringList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean removePackagesFromForceStopBlackList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "removePackagesFromForceStopBlackList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removePackagesFromForceStopBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removePackagesFromForceStopBlackList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean removePackagesFromForceStopWhiteList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "removePackagesFromForceStopWhiteList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removePackagesFromForceStopWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removePackagesFromForceStopWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean removePackagesFromNotificationBlackList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "removePackagesFromNotificationBlackList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removePackagesFromNotificationBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removeAppNotificationBlackList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean removePackagesFromNotificationWhiteList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "removePackagesFromNotificationWhiteList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removePackagesFromNotificationWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removeAppNotificationWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean removePackagesFromPreventStartBlackList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "removePackagesFromPreventStartBlackList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removePackagesFromPreventStartBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removePackagesFromPreventStartBlackList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean removePackagesFromWidgetBlackList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "removePackagesFromWidgetBlackList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removePackagesFromWidgetBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removePackagesFromWidgetBlackList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean removePackagesFromWidgetWhiteList(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "removePackagesFromWidgetWhiteList");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.removePackagesFromWidgetWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removePackagesFromWidgetWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public int setAfWProxy(boolean z, AppIdentity appIdentity, Bundle bundle) {
        logUsage("ApplicationPolicy.setAfWProxy");
        return -1;
    }

    public boolean setApplicationComponentState(ComponentName componentName, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.setApplicationComponentState");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setApplicationComponentState(this.mContextInfo, componentName, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public void setApplicationInstallationDisabled(String str, boolean z) {
        if (getService() != null) {
            try {
                this.mService.setApplicationInstallationDisabled(this.mContextInfo, str, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
    }

    public void setApplicationInstallationEnabled(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.setApplicationInstallationEnabled");
        if (getService() != null) {
            try {
                this.mService.setApplicationInstallationDisabled(this.mContextInfo, str, false);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
    }

    public boolean setApplicationInstallationMode(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.setApplicationInstallationMode");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setAppInstallationMode(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean setApplicationNotificationMode(int i) {
        AccessController.throwIfParentInstance(this.mContextInfo, "setApplicationNotificationMode");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.setApplicationNotificationMode");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setApplicationNotificationMode(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public void setApplicationRestrictions(ComponentName componentName, String str, Bundle bundle) {
        AccessController.throwIfParentInstance(this.mContextInfo, "setApplicationRestrictions");
        logUsage("ApplicationPolicy.setApplicationRestrictions");
        if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 17 || getService() == null) {
            return;
        }
        try {
            this.mService.setApplicationRestrictions(componentName, str, bundle, EdmUtils.getCallingUserId(this.mContextInfo));
        } catch (RemoteException e) {
            Log.e(TAG, "Failed talking to Device Policy Manager service", e);
        }
    }

    public boolean setApplicationState(String str, boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "setApplicationState");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setApplicationState(this.mContextInfo, str, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public String[] setApplicationStateList(String[] strArr, boolean z) {
        logUsage("ApplicationPolicy.setApplicationStateList");
        if (getService() != null) {
            try {
                return this.mService.setApplicationStateList(this.mContextInfo, strArr, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with Application policy", e);
            }
        }
        return new String[0];
    }

    public void setApplicationUninstallationDisabled(String str, boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "setApplicationUninstallationDisabled");
        if (getService() != null) {
            try {
                this.mService.setApplicationUninstallationDisabled(this.mContextInfo, str, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
    }

    public void setApplicationUninstallationEnabled(String str) {
        AccessController.throwIfParentInstance(this.mContextInfo, "setApplicationUninstallationEnabled");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.setApplicationUninstallationEnabled");
        if (getService() != null) {
            try {
                this.mService.setApplicationUninstallationDisabled(this.mContextInfo, str, false);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
    }

    public boolean setApplicationUninstallationMode(int i) {
        AccessController.throwIfParentInstance(this.mContextInfo, "setApplicationUninstallationMode");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.setApplicationUninstallationMode");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setApplicationUninstallationMode(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public int setAuthorizedScopes(AppIdentity appIdentity, List<String> list) {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "setAuthorizedScopes", this, "ApplicationPolicy.setAuthorizedScopes") == null) {
            return -2;
        }
        try {
            return this.mService.setAuthorizedScopes(appIdentity, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return -2;
        }
    }

    public boolean setConcentrationMode(List<String> list, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.setConcentrationMode");
        try {
            if (getService() != null) {
                return this.mService.setConcentrationMode(this.mContextInfo, list, z);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, " Failed talking to ApplicationPolicy service ", e);
            return false;
        }
    }

    public boolean setDefaultApplication(Intent intent, ComponentName componentName) {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "setDefaultApplication", this, "ApplicationPolicy.setDefaultApplication") == null) {
            return false;
        }
        try {
            Log.d(TAG, "setDefaultApplication");
            return this.mService.setDefaultApplication(this.mContextInfo, intent, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to ApplicationPolicy service ", e);
            return false;
        }
    }

    public boolean setDisableApplication(String str) {
        logUsage("ApplicationPolicy.setDisableApplication");
        if (getService() != null) {
            try {
                return this.mService.setApplicationState(this.mContextInfo, str, false);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return false;
    }

    public boolean setEnableApplication(String str) {
        logUsage("ApplicationPolicy.setEnableApplication");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setApplicationState(this.mContextInfo, str, true);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean setMySinglePackageName(String str) {
        return false;
    }

    public boolean startApp(String str, String str2) {
        logUsage("ApplicationPolicy.startApp");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.startApp(this.mContextInfo, str, str2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Application policy", e);
            return false;
        }
    }

    public boolean stopApp(String str) {
        logUsage("ApplicationPolicy.stopApp");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.stopApp(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Application policy", e);
            return false;
        }
    }

    public boolean uninstallApplication(String str, boolean z) {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "uninstallApplication", this, "ApplicationPolicy.uninstallApplication") == null) {
            return false;
        }
        try {
            return this.mService.uninstallApplication(this.mContextInfo, str, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public List<String> uninstallApplications(List<String> list) {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "uninstallApplications", this, "ApplicationPolicy.uninstallApplications") != null) {
            try {
                return this.mService.removeManagedApplications(this.mContextInfo, list);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
        return new ArrayList();
    }

    public boolean updateApplication(String str) {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "updateApplication", this, "ApplicationPolicy.updateApplication") != null) {
            return installOrUpdateApplicationInternal(str, false, true);
        }
        return false;
    }

    public boolean updateApplicationTable(int i, int i2, int i3) {
        logUsage("ApplicationPolicy.updateApplicationTable");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.updateApplicationTable(i, i2, i3);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking to ApplicationPolicy service ", e);
            return false;
        }
    }

    public boolean verifyRuntimePermissionPackageSignature(String str) {
        Log.d(TAG, "ApplicationPolicy.verifyRuntimePermissionPackageSignature");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.verifyRuntimePermissionPackageSignature(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public boolean wipeApplicationData(String str) {
        if (ApplicationPolicy$$ExternalSyntheticOutline0.m(this.mContextInfo, "wipeApplicationData", this, "ApplicationPolicy.wipeApplicationData") == null) {
            return false;
        }
        try {
            return this.mService.wipeApplicationData(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public void logUsage(String str, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, str, z);
    }

    public boolean installApplication(String str) {
        logUsage("ApplicationPolicy.installApplication(String)");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.installExistingApplication(this.mContextInfo, str, true);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public void setApplicationInstallationDisabled(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.setApplicationInstallationDisabled");
        if (getService() != null) {
            try {
                this.mService.setApplicationInstallationDisabled(this.mContextInfo, str, true);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean addAppPackageNameToWhiteList(java.lang.String r4, boolean r5) {
        /*
            r3 = this;
            com.samsung.android.knox.ContextInfo r0 = r3.mContextInfo
            java.lang.String r1 = "ApplicationPolicy.addAppPackageNameToWhiteList(String, boolean)"
            com.samsung.android.knox.license.EnterpriseLicenseManager.log(r0, r1)
            r0 = 0
            r1 = 1
            if (r5 == 0) goto L23
            com.samsung.android.knox.ContextInfo r5 = r3.mContextInfo
            java.lang.String r2 = "ApplicationPolicy.addAppPackageNameToWhiteList -> Adding Star in BlackList"
            com.samsung.android.knox.license.EnterpriseLicenseManager.log(r5, r2)
            java.lang.String r5 = ".*"
            boolean r5 = r3.addAppPackageNameToBlackList(r5)
            if (r5 != 0) goto L23
            java.lang.String r5 = com.samsung.android.knox.application.ApplicationPolicy.TAG
            java.lang.String r2 = "ApplicationPolicy.addAppPackageNameToWhiteList: failed to add .*"
            android.util.Log.d(r5, r2)
            r5 = r0
            goto L24
        L23:
            r5 = r1
        L24:
            com.samsung.android.knox.application.IApplicationPolicy r2 = r3.getService()
            if (r2 == 0) goto L40
            com.samsung.android.knox.application.IApplicationPolicy r2 = r3.mService     // Catch: android.os.RemoteException -> L38
            com.samsung.android.knox.ContextInfo r3 = r3.mContextInfo     // Catch: android.os.RemoteException -> L38
            boolean r3 = r2.addAppPackageNameToWhiteList(r3, r4)     // Catch: android.os.RemoteException -> L38
            if (r3 == 0) goto L37
            if (r5 == 0) goto L37
            return r1
        L37:
            return r0
        L38:
            r3 = move-exception
            java.lang.String r4 = com.samsung.android.knox.application.ApplicationPolicy.TAG
            java.lang.String r5 = "Failed talking with application policy"
            android.util.Log.w(r4, r5, r3)
        L40:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.application.ApplicationPolicy.addAppPackageNameToWhiteList(java.lang.String, boolean):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean addAppSignatureToWhiteList(java.lang.String r4, boolean r5) {
        /*
            r3 = this;
            com.samsung.android.knox.ContextInfo r0 = r3.mContextInfo
            java.lang.String r1 = "ApplicationPolicy.addAppSignatureToWhiteList(String, boolean)"
            com.samsung.android.knox.license.EnterpriseLicenseManager.log(r0, r1)
            r0 = 0
            r1 = 1
            if (r5 == 0) goto L23
            com.samsung.android.knox.ContextInfo r5 = r3.mContextInfo
            java.lang.String r2 = "ApplicationPolicy.addAppSignatureToWhiteList -> Adding Star in BlackList"
            com.samsung.android.knox.license.EnterpriseLicenseManager.log(r5, r2)
            java.lang.String r5 = "*"
            boolean r5 = r3.addAppSignatureToBlackList(r5)
            if (r5 != 0) goto L23
            java.lang.String r5 = com.samsung.android.knox.application.ApplicationPolicy.TAG
            java.lang.String r2 = "ApplicationPolicy.addAppSignatureToWhiteList: failed to add *"
            android.util.Log.d(r5, r2)
            r5 = r0
            goto L24
        L23:
            r5 = r1
        L24:
            com.samsung.android.knox.application.IApplicationPolicy r2 = r3.getService()
            if (r2 == 0) goto L40
            com.samsung.android.knox.application.IApplicationPolicy r2 = r3.mService     // Catch: android.os.RemoteException -> L38
            com.samsung.android.knox.ContextInfo r3 = r3.mContextInfo     // Catch: android.os.RemoteException -> L38
            boolean r3 = r2.addAppSignatureToWhiteList(r3, r4)     // Catch: android.os.RemoteException -> L38
            if (r3 == 0) goto L37
            if (r5 == 0) goto L37
            return r1
        L37:
            return r0
        L38:
            r3 = move-exception
            java.lang.String r4 = com.samsung.android.knox.application.ApplicationPolicy.TAG
            java.lang.String r5 = "Failed talking with application policy"
            android.util.Log.w(r4, r5, r3)
        L40:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.application.ApplicationPolicy.addAppSignatureToWhiteList(java.lang.String, boolean):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0032 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean addPackagesToDisableUpdateWhiteList(java.util.List<java.lang.String> r4, boolean r5) {
        /*
            r3 = this;
            com.samsung.android.knox.ContextInfo r0 = r3.mContextInfo
            java.lang.String r1 = "ApplicationPolicy.addPackagesToDisableUpdateWhiteList(List<String>, boolean)"
            com.samsung.android.knox.license.EnterpriseLicenseManager.log(r0, r1)
            r0 = 0
            r1 = 1
            if (r5 == 0) goto L2b
            com.samsung.android.knox.ContextInfo r5 = r3.mContextInfo
            java.lang.String r2 = "ApplicationPolicy.addPackagesToPermissionWhiteList -> Adding Star in BlackList"
            com.samsung.android.knox.license.EnterpriseLicenseManager.log(r5, r2)
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            java.lang.String r2 = "*"
            r5.add(r2)
            boolean r5 = r3.addPackagesToDisableUpdateBlackList(r5)
            if (r5 != 0) goto L2b
            java.lang.String r5 = com.samsung.android.knox.application.ApplicationPolicy.TAG
            java.lang.String r2 = "ApplicationPolicy.addPackagesToDisableUpdateWhiteList: failed to add *"
            android.util.Log.d(r5, r2)
            r5 = r0
            goto L2c
        L2b:
            r5 = r1
        L2c:
            com.samsung.android.knox.application.IApplicationPolicy r2 = r3.getService()
            if (r2 == 0) goto L48
            com.samsung.android.knox.application.IApplicationPolicy r2 = r3.mService     // Catch: android.os.RemoteException -> L40
            com.samsung.android.knox.ContextInfo r3 = r3.mContextInfo     // Catch: android.os.RemoteException -> L40
            boolean r3 = r2.addPackagesToDisableUpdateWhiteList(r3, r4)     // Catch: android.os.RemoteException -> L40
            if (r3 == 0) goto L3f
            if (r5 == 0) goto L3f
            return r1
        L3f:
            return r0
        L40:
            r3 = move-exception
            java.lang.String r4 = com.samsung.android.knox.application.ApplicationPolicy.TAG
            java.lang.String r5 = "Failed talking with application policy"
            android.util.Log.w(r4, r5, r3)
        L48:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.application.ApplicationPolicy.addPackagesToDisableUpdateWhiteList(java.util.List, boolean):boolean");
    }

    public boolean setApplicationState(String str, boolean z, String str2, String str3) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setApplicationState(this.mContextInfo, str, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with application policy", e);
            return false;
        }
    }

    public void setApplicationUninstallationDisabled(String str) {
        AccessController.throwIfParentInstance(this.mContextInfo, "setApplicationUninstallationDisabled");
        EnterpriseLicenseManager.log(this.mContextInfo, "ApplicationPolicy.setApplicationUninstallationDisabled");
        if (getService() != null) {
            try {
                this.mService.setApplicationUninstallationDisabled(this.mContextInfo, str, true);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with application policy", e);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0039 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean addPackagesToDisableClipboardWhiteList(java.util.List<java.lang.String> r4, boolean r5) {
        /*
            r3 = this;
            com.samsung.android.knox.ContextInfo r0 = r3.mContextInfo
            java.lang.String r1 = "addPackagesToDisableClipboardWhiteList"
            com.samsung.android.knox.AccessController.throwIfParentInstance(r0, r1)
            com.samsung.android.knox.ContextInfo r0 = r3.mContextInfo
            java.lang.String r1 = "ApplicationPolicy.addPackagesToDisableClipboardWhiteList(List<String>, boolean)"
            com.samsung.android.knox.license.EnterpriseLicenseManager.log(r0, r1)
            r0 = 0
            r1 = 1
            if (r5 == 0) goto L32
            com.samsung.android.knox.ContextInfo r5 = r3.mContextInfo
            java.lang.String r2 = "ApplicationPolicy.addPackagesToDisableClipboardWhiteList -> Adding Star in BlackList"
            com.samsung.android.knox.license.EnterpriseLicenseManager.log(r5, r2)
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            java.lang.String r2 = "*"
            r5.add(r2)
            boolean r5 = r3.addPackagesToDisableClipboardBlackList(r5)
            if (r5 != 0) goto L32
            java.lang.String r5 = com.samsung.android.knox.application.ApplicationPolicy.TAG
            java.lang.String r2 = "ApplicationPolicy.addPackagesToDisableClipboardWhiteList: failed to add *"
            android.util.Log.d(r5, r2)
            r5 = r0
            goto L33
        L32:
            r5 = r1
        L33:
            com.samsung.android.knox.application.IApplicationPolicy r2 = r3.getService()
            if (r2 == 0) goto L4f
            com.samsung.android.knox.application.IApplicationPolicy r2 = r3.mService     // Catch: android.os.RemoteException -> L47
            com.samsung.android.knox.ContextInfo r3 = r3.mContextInfo     // Catch: android.os.RemoteException -> L47
            boolean r3 = r2.addPackagesToDisableClipboardWhiteList(r3, r4)     // Catch: android.os.RemoteException -> L47
            if (r3 == 0) goto L46
            if (r5 == 0) goto L46
            return r1
        L46:
            return r0
        L47:
            r3 = move-exception
            java.lang.String r4 = com.samsung.android.knox.application.ApplicationPolicy.TAG
            java.lang.String r5 = "Failed talking with application policy"
            android.util.Log.w(r4, r5, r3)
        L4f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.application.ApplicationPolicy.addPackagesToDisableClipboardWhiteList(java.util.List, boolean):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0039 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean addPackagesToForceStopWhiteList(java.util.List<java.lang.String> r4, boolean r5) {
        /*
            r3 = this;
            com.samsung.android.knox.ContextInfo r0 = r3.mContextInfo
            java.lang.String r1 = "addPackagesToForceStopWhiteList"
            com.samsung.android.knox.AccessController.throwIfParentInstance(r0, r1)
            com.samsung.android.knox.ContextInfo r0 = r3.mContextInfo
            java.lang.String r1 = "ApplicationPolicy.addPackagesToForceStopWhiteList(List<String>, boolean)"
            com.samsung.android.knox.license.EnterpriseLicenseManager.log(r0, r1)
            r0 = 0
            r1 = 1
            if (r5 == 0) goto L32
            com.samsung.android.knox.ContextInfo r5 = r3.mContextInfo
            java.lang.String r2 = "ApplicationPolicy.addPackagesToPermissionWhiteList -> Adding Star in BlackList"
            com.samsung.android.knox.license.EnterpriseLicenseManager.log(r5, r2)
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            java.lang.String r2 = "*"
            r5.add(r2)
            boolean r5 = r3.addPackagesToForceStopBlackList(r5)
            if (r5 != 0) goto L32
            java.lang.String r5 = com.samsung.android.knox.application.ApplicationPolicy.TAG
            java.lang.String r2 = "ApplicationPolicy.addPackagesToForceStopWhiteList: failed to add *"
            android.util.Log.d(r5, r2)
            r5 = r0
            goto L33
        L32:
            r5 = r1
        L33:
            com.samsung.android.knox.application.IApplicationPolicy r2 = r3.getService()
            if (r2 == 0) goto L4f
            com.samsung.android.knox.application.IApplicationPolicy r2 = r3.mService     // Catch: android.os.RemoteException -> L47
            com.samsung.android.knox.ContextInfo r3 = r3.mContextInfo     // Catch: android.os.RemoteException -> L47
            boolean r3 = r2.addPackagesToForceStopWhiteList(r3, r4)     // Catch: android.os.RemoteException -> L47
            if (r3 == 0) goto L46
            if (r5 == 0) goto L46
            return r1
        L46:
            return r0
        L47:
            r3 = move-exception
            java.lang.String r4 = com.samsung.android.knox.application.ApplicationPolicy.TAG
            java.lang.String r5 = "Failed talking with application policy"
            android.util.Log.w(r4, r5, r3)
        L4f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.application.ApplicationPolicy.addPackagesToForceStopWhiteList(java.util.List, boolean):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0039 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean addPackagesToNotificationWhiteList(java.util.List<java.lang.String> r4, boolean r5) {
        /*
            r3 = this;
            com.samsung.android.knox.ContextInfo r0 = r3.mContextInfo
            java.lang.String r1 = "addPackagesToNotificationWhiteList"
            com.samsung.android.knox.AccessController.throwIfParentInstance(r0, r1)
            com.samsung.android.knox.ContextInfo r0 = r3.mContextInfo
            java.lang.String r1 = "ApplicationPolicy.addPackagesToNotificationWhiteList(List<String>, boolean)"
            com.samsung.android.knox.license.EnterpriseLicenseManager.log(r0, r1)
            r0 = 0
            r1 = 1
            if (r5 == 0) goto L32
            com.samsung.android.knox.ContextInfo r5 = r3.mContextInfo
            java.lang.String r2 = "ApplicationPolicy.addPackagesToNotificationWhiteList -> Adding Star in BlackList"
            com.samsung.android.knox.license.EnterpriseLicenseManager.log(r5, r2)
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            java.lang.String r2 = "*"
            r5.add(r2)
            boolean r5 = r3.addPackagesToNotificationBlackList(r5)
            if (r5 != 0) goto L32
            java.lang.String r5 = com.samsung.android.knox.application.ApplicationPolicy.TAG
            java.lang.String r2 = "ApplicationPolicy.addPackagesToNotificationWhiteList: failed to add *"
            android.util.Log.d(r5, r2)
            r5 = r0
            goto L33
        L32:
            r5 = r1
        L33:
            com.samsung.android.knox.application.IApplicationPolicy r2 = r3.getService()
            if (r2 == 0) goto L4f
            com.samsung.android.knox.application.IApplicationPolicy r2 = r3.mService     // Catch: android.os.RemoteException -> L47
            com.samsung.android.knox.ContextInfo r3 = r3.mContextInfo     // Catch: android.os.RemoteException -> L47
            boolean r3 = r2.addAppNotificationWhiteList(r3, r4)     // Catch: android.os.RemoteException -> L47
            if (r3 == 0) goto L46
            if (r5 == 0) goto L46
            return r1
        L46:
            return r0
        L47:
            r3 = move-exception
            java.lang.String r4 = com.samsung.android.knox.application.ApplicationPolicy.TAG
            java.lang.String r5 = "Failed talking with application policy"
            android.util.Log.w(r4, r5, r3)
        L4f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.application.ApplicationPolicy.addPackagesToNotificationWhiteList(java.util.List, boolean):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0039 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean addPackagesToWidgetWhiteList(java.util.List<java.lang.String> r4, boolean r5) {
        /*
            r3 = this;
            com.samsung.android.knox.ContextInfo r0 = r3.mContextInfo
            java.lang.String r1 = "addPackagesToWidgetWhiteList"
            com.samsung.android.knox.AccessController.throwIfParentInstance(r0, r1)
            com.samsung.android.knox.ContextInfo r0 = r3.mContextInfo
            java.lang.String r1 = "ApplicationPolicy.addPackagesToWidgetWhiteList(List<String>, boolean)"
            com.samsung.android.knox.license.EnterpriseLicenseManager.log(r0, r1)
            r0 = 0
            r1 = 1
            if (r5 == 0) goto L32
            com.samsung.android.knox.ContextInfo r5 = r3.mContextInfo
            java.lang.String r2 = "ApplicationPolicy.addPackagesToWidgetWhiteList -> Adding Star in BlackList"
            com.samsung.android.knox.license.EnterpriseLicenseManager.log(r5, r2)
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            java.lang.String r2 = "*"
            r5.add(r2)
            boolean r5 = r3.addPackagesToWidgetBlackList(r5)
            if (r5 != 0) goto L32
            java.lang.String r5 = com.samsung.android.knox.application.ApplicationPolicy.TAG
            java.lang.String r2 = "ApplicationPolicy.addPackagesToWidgetWhiteList: failed to add *"
            android.util.Log.d(r5, r2)
            r5 = r0
            goto L33
        L32:
            r5 = r1
        L33:
            com.samsung.android.knox.application.IApplicationPolicy r2 = r3.getService()
            if (r2 == 0) goto L4f
            com.samsung.android.knox.application.IApplicationPolicy r2 = r3.mService     // Catch: android.os.RemoteException -> L47
            com.samsung.android.knox.ContextInfo r3 = r3.mContextInfo     // Catch: android.os.RemoteException -> L47
            boolean r3 = r2.addPackagesToWidgetWhiteList(r3, r4)     // Catch: android.os.RemoteException -> L47
            if (r3 == 0) goto L46
            if (r5 == 0) goto L46
            return r1
        L46:
            return r0
        L47:
            r3 = move-exception
            java.lang.String r4 = com.samsung.android.knox.application.ApplicationPolicy.TAG
            java.lang.String r5 = "Failed talking with application policy"
            android.util.Log.w(r4, r5, r3)
        L4f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.application.ApplicationPolicy.addPackagesToWidgetWhiteList(java.util.List, boolean):boolean");
    }

    public boolean installApplicationWithoutPermissions(String str, boolean z, boolean z2) {
        logUsage("ApplicationPolicy.installApplicationWithoutPermissions(String, boolean, boolean)");
        if (getService() != null) {
            return installOrUpdateApplicationInternal(str, z, z2);
        }
        return false;
    }

    public Bundle getApplicationRestrictions(String str) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getApplicationRestrictions");
        ApplicationRestrictionsManager applicationRestrictionsManager = ApplicationRestrictionsManager.getInstance(this.mContext);
        if (applicationRestrictionsManager != null) {
            return applicationRestrictionsManager.getApplicationRestrictions(str, 0);
        }
        return new Bundle();
    }
}
