package com.android.server.am;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Slog;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.database.MARsVersionManager;

import java.util.ArrayList;
import java.util.List;

public final class BaseRestrictionMgr {
    public Context mContext = null;
    public final ArrayList mRestrictActivityTheme =
            new ArrayList() { // from class: com.android.server.am.BaseRestrictionMgr.1
                {
                    add(Integer.valueOf(R.style.Theme.Translucent.NoTitleBar));
                }
            };

    public abstract class BaseRestrictionMgrHolder {
        public static final BaseRestrictionMgr INSTANCE = new BaseRestrictionMgr();
    }

    public final Intent getLaunchIntentForPackage(String str, int i) {
        PackageManager packageManager = this.mContext.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.INFO");
        intent.setPackage(str);
        List queryIntentActivitiesAsUser = packageManager.queryIntentActivitiesAsUser(intent, 0, i);
        if (queryIntentActivitiesAsUser == null || queryIntentActivitiesAsUser.isEmpty()) {
            intent.removeCategory("android.intent.category.INFO");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setPackage(str);
            queryIntentActivitiesAsUser = packageManager.queryIntentActivitiesAsUser(intent, 0, i);
        }
        if (queryIntentActivitiesAsUser == null || queryIntentActivitiesAsUser.isEmpty()) {
            return null;
        }
        Intent intent2 = new Intent(intent);
        intent2.setFlags(268435456);
        intent2.setClassName(
                ((ResolveInfo) queryIntentActivitiesAsUser.get(0)).activityInfo.packageName,
                ((ResolveInfo) queryIntentActivitiesAsUser.get(0)).activityInfo.name);
        return intent2;
    }

    public final boolean isBlockAssociatedActivity(ActivityInfo activityInfo) {
        boolean z = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
        if (MARsPolicyManager.isChinaModel
                && activityInfo != null
                && activityInfo.taskAffinity != null
                && this.mRestrictActivityTheme.contains(Integer.valueOf(activityInfo.theme))) {
            String[][] strArr = MARsVersionManager.mMARsSettingsInfoDefault;
            MARsVersionManager mARsVersionManager =
                    MARsVersionManager.MARsVersionManagerHolder.INSTANCE;
            if (mARsVersionManager.isAdjustRestrictionMatch(
                    27, activityInfo.taskAffinity, null, null)) {
                return true;
            }
            String[] split = activityInfo.taskAffinity.split(":");
            if (split.length <= 1) {
                Slog.e(
                        "BaseRestrictionMgr",
                        "Failed to analyze taskAffinity: [" + activityInfo.taskAffinity + "]");
                return false;
            }
            if (mARsVersionManager.isAdjustRestrictionMatch(27, split[1], null, null)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isLauncherableApp(int i, String str) {
        if (!"com.baidu.searchbox_samsung".equals(str) && !"com.bst.floatingmsgproxy".equals(str)) {
            try {
                if (getLaunchIntentForPackage(str, i) == null) {
                    boolean z = MARsPolicyManager.MARs_ENABLE;
                    if (MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.isMARsTarget(i, str)) {
                        if (MARsDebugConfig.DEBUG_MARs) {
                            Slog.d(
                                    "BaseRestrictionMgr",
                                    "AutoRun Policy isLauncherableApp -- Not launcherable 3rd party"
                                        + " package:"
                                            + str);
                        }
                        return true;
                    }
                    if (!MARsDebugConfig.DEBUG_MARs) {
                        return false;
                    }
                    Slog.d(
                            "BaseRestrictionMgr",
                            "AutoRun Policy isLauncherableApp -- Not launcherable system package:"
                                    + str);
                    return false;
                }
            } catch (Exception e) {
                BootReceiver$$ExternalSyntheticOutline0.m(
                        e, "isLaucherableApp exception=", "BaseRestrictionMgr");
            }
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x021d, code lost:

       if (com.android.server.am.mars.database.MARsVersionManager.MARsVersionManagerHolder.INSTANCE.isAdjustRestrictionMatch(2, r7, r17, r0) != false) goto L135;
    */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x00c7, code lost:

       if (com.android.server.am.mars.database.MARsVersionManager.MARsVersionManagerHolder.INSTANCE.isAdjustRestrictionMatch(1, r7, r17, r12) != false) goto L63;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int isPolicyBlockedPackage(
            java.lang.String r17,
            int r18,
            android.content.ComponentName r19,
            java.lang.String r20,
            android.content.Intent r21,
            int r22,
            int r23) {
        /*
            Method dump skipped, instructions count: 831
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.BaseRestrictionMgr.isPolicyBlockedPackage(java.lang.String,"
                    + " int, android.content.ComponentName, java.lang.String,"
                    + " android.content.Intent, int, int):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:298:0x037c, code lost:

       if (isPolicyBlockedPackage(r24, r25, r23, r26, r27, r12, r28) > 0) goto L250;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isRestrictedPackage(
            android.content.ComponentName r23,
            java.lang.String r24,
            int r25,
            java.lang.String r26,
            android.content.Intent r27,
            int r28,
            boolean r29,
            android.content.pm.ActivityInfo r30,
            java.lang.String r31,
            int r32,
            int r33) {
        /*
            Method dump skipped, instructions count: 1675
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.BaseRestrictionMgr.isRestrictedPackage(android.content.ComponentName,"
                    + " java.lang.String, int, java.lang.String, android.content.Intent, int,"
                    + " boolean, android.content.pm.ActivityInfo, java.lang.String, int,"
                    + " int):boolean");
    }
}
