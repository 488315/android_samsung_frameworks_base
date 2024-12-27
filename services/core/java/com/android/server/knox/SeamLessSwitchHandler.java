package com.android.server.knox;

import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.KeyguardManager;
import android.app.SemStatusBarManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;

import com.android.internal.widget.LockPatternUtils;
import com.android.server.LocalServices;
import com.android.server.pm.PersonaManagerService;
import com.android.server.wm.ActivityTaskManagerService;

import com.samsung.android.knox.PersonaManagerInternal;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class SeamLessSwitchHandler {
    public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public static String packageExtraForSALog = null;
    public final ActivityManager am;
    public final Context c;
    public final KeyguardManager mKeyguardManager;
    public final LockPatternUtils mLockPatternUtils;
    public final PersonaManagerInternal mPersonaManagerInternal;
    public final UserManager mUserManager;
    public final PersonaManagerService personaManagerService;
    public final PackageManager pm;
    public final SemPersonaManager semPersonaManager;
    public final SemStatusBarManager statusBarManager;
    public final List SEAMLESS_NOTALLOWED_EXCEPTIONAL_LIST =
            new ArrayList(Arrays.asList("android"));
    public final List LAUNCHSF_HOME_LIST =
            new ArrayList(Arrays.asList(KnoxCustomManagerService.SETTING_PKG_NAME));

    public SeamLessSwitchHandler(Context context, PersonaManagerService personaManagerService) {
        this.c = context;
        this.pm = context.getPackageManager();
        this.personaManagerService = personaManagerService;
        this.am = (ActivityManager) context.getSystemService("activity");
        this.semPersonaManager = (SemPersonaManager) context.getSystemService("persona");
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.statusBarManager =
                (SemStatusBarManager) context.getSystemService(SemStatusBarManager.class);
        this.mKeyguardManager = (KeyguardManager) context.getSystemService("keyguard");
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mPersonaManagerInternal =
                (PersonaManagerInternal) LocalServices.getService(PersonaManagerInternal.class);
    }

    public static boolean isAllowedAppsInLockscreen(ComponentName componentName) {
        return SystemProperties.get("service.camera.running", "0").equals("1")
                || componentName.getPackageName().equals("com.sec.android.app.popupcalculator")
                || componentName.getPackageName().equals("com.sec.android.app.voicenote");
    }

    public final Intent getLaunchIntentForPackage(String str, int i) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.INFO");
        intent.setPackage(str);
        List queryIntentActivitiesAsUser = this.pm.queryIntentActivitiesAsUser(intent, 0, i);
        if (queryIntentActivitiesAsUser == null || queryIntentActivitiesAsUser.size() <= 0) {
            intent.removeCategory("android.intent.category.INFO");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setPackage(str);
            queryIntentActivitiesAsUser = this.pm.queryIntentActivitiesAsUser(intent, 0, i);
        }
        if (queryIntentActivitiesAsUser == null || queryIntentActivitiesAsUser.size() <= 0) {
            return null;
        }
        Intent intent2 = new Intent(intent);
        intent2.setFlags(268435456);
        intent2.setClassName(
                ((ResolveInfo) queryIntentActivitiesAsUser.get(0)).activityInfo.packageName,
                ((ResolveInfo) queryIntentActivitiesAsUser.get(0)).activityInfo.name);
        return intent2;
    }

    public final int getLaunchUserId(int i) {
        Iterator it = this.semPersonaManager.getKnoxIds(true).iterator();
        int i2 = 0;
        boolean z = false;
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            if (SemPersonaManager.isSecureFolderId(intValue)) {
                z = true;
                i2 = intValue;
            }
        }
        int i3 = i == 0 ? i2 : 0;
        if (z) {
            return i3;
        }
        return -1;
    }

    public final boolean isPackageEnabled(String str, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            if ("com.samsung.knox.securefolder".equals(str) && i == 0) {
                return false;
            }
            if (((ArrayList) this.LAUNCHSF_HOME_LIST).contains(str)) {
                return false;
            }
            PackageInfo packageInfo =
                    ActivityThread.getPackageManager().getPackageInfo(str, 786432L, i);
            if (packageInfo != null) {
                if (packageInfo.applicationInfo.enabled) {
                    z = true;
                }
            }
            return z;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isSecureFolderHidden() {
        return Settings.Secure.getIntForUser(
                        this.c.getContentResolver(), "hide_secure_folder_flag", 0, 0)
                == 1;
    }

    public final boolean isSettingsExceptionalCase(
            ActivityManager.RunningTaskInfo runningTaskInfo, ComponentName componentName, int i) {
        try {
            if (!SemPersonaManager.isSecureFolderId(i)
                    && this.personaManagerService.getFocusedUser() != 0) {
                return false;
            }
            if (!KnoxCustomManagerService.SETTING_PKG_NAME.equals(componentName.getPackageName())
                    || !((ActivityTaskManagerService) ServiceManager.getService("activity_task"))
                            .mPersonaActivityHelper.isQuickSwitchExceptionalCase(
                                    runningTaskInfo.id)) {
                return false;
            }
            Log.d("SeamLessSwitchHandler", "Exceptional case quick switch! securefolder keyguard");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final void launchFolderContainerOrHome(int i) {
        Intent intent = new Intent("android.intent.action.MAIN");
        if (SemPersonaManager.isSecureFolderId(i)) {
            intent.setClassName(
                    "com.samsung.knox.securefolder",
                    isSecureFolderHidden()
                            ? "com.samsung.knox.launcher.home.view.HomeActivity"
                            : "com.samsung.knox.securefolder.presentation.switcher.view.SecureFolderShortcutActivity");
            intent.putExtra("userId", i);
            if (!this.mPersonaManagerInternal.shouldConfirmCredentials(i)) {
                intent.putExtra("quick_switch", true);
            }
            intent.addFlags(268435456);
            intent.addFlags(49152);
            intent.addFlags(EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT);
            this.c.startActivityAsUser(intent, new UserHandle(isSecureFolderHidden() ? i : 0));
            new Handler(Looper.getMainLooper())
                    .postDelayed(
                            new SeamLessSwitchHandler$$ExternalSyntheticLambda0(this, i), 500L);
            packageExtraForSALog = "com.samsung.knox.securefolder";
            return;
        }
        intent.addCategory("android.intent.category.HOME");
        intent.setFlags(268435456);
        ResolveInfo resolveActivityAsUser =
                this.c
                        .getPackageManager()
                        .resolveActivityAsUser(
                                intent,
                                EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT,
                                0);
        if (resolveActivityAsUser == null) {
            return;
        }
        ActivityInfo activityInfo = resolveActivityAsUser.activityInfo;
        intent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
        intent.putExtra("android.intent.extra.FROM_HOME_KEY", true);
        this.c.startActivityAsUser(intent, new UserHandle(0));
        packageExtraForSALog = resolveActivityAsUser.activityInfo.packageName;
    }
}
