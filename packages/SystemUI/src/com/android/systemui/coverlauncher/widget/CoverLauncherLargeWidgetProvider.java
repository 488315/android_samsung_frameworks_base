package com.android.systemui.coverlauncher.widget;

import android.app.ActivityTaskManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.coverlauncher.utils.CoverLauncherPackageUtils;
import com.android.systemui.coverlauncher.utils.CoverLauncherWidgetOptions;
import com.android.systemui.coverlauncher.widget.CoverLauncherWidgetViewController;
import com.samsung.android.core.CoreSaLogger;
import java.util.Arrays;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class CoverLauncherLargeWidgetProvider extends AppWidgetProvider {
    public static final CoverLauncherWidgetOptions sWidgetOptions;
    public CoverLauncherPackageUtils mPackageUtil;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        sWidgetOptions = new CoverLauncherWidgetOptions(false, null, 0, 7, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0083  */
    @Override // android.appwidget.AppWidgetProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onAppWidgetOptionsChanged(android.content.Context r9, android.appwidget.AppWidgetManager r10, int r11, android.os.Bundle r12) {
        /*
            r8 = this;
            java.lang.String r8 = "visible"
            r0 = 0
            boolean r8 = r12.getBoolean(r8, r0)
            com.android.systemui.coverlauncher.utils.CoverLauncherWidgetOptions r1 = com.android.systemui.coverlauncher.widget.CoverLauncherLargeWidgetProvider.sWidgetOptions
            boolean r2 = r1.visibleOption
            java.lang.String r3 = ", id="
            java.lang.String r4 = "CoverLauncherWidgetProvider"
            r5 = 1
            if (r8 == r2) goto L3e
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r6 = "visible changed to "
            r2.<init>(r6)
            r2.append(r8)
            r2.append(r3)
            r2.append(r11)
            java.lang.String r2 = r2.toString()
            android.util.Log.i(r4, r2)
            r1.visibleOption = r8
            if (r8 != 0) goto L31
        L2f:
            r8 = r5
            goto L3f
        L31:
            android.content.ContentResolver r8 = r9.getContentResolver()
            java.lang.String r2 = "notification_badging"
            int r8 = android.provider.Settings.Secure.getInt(r8, r2, r0)
            if (r8 == 0) goto L3e
            goto L2f
        L3e:
            r8 = r0
        L3f:
            java.lang.String r2 = "appIconPackageName"
            java.lang.String r2 = r12.getString(r2)
            if (r2 == 0) goto L5f
            java.lang.String r6 = r1.appIconPkgOption
            boolean r6 = r2.equals(r6)
            if (r6 != 0) goto L5f
            java.lang.String r8 = r1.appIconPkgOption
            java.lang.String r6 = "appIcon pkg is updated to "
            java.lang.String r7 = " from "
            java.lang.StringBuilder r8 = androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m(r6, r2, r7, r8, r3)
            androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0.m(r11, r4, r8)
            r1.appIconPkgOption = r2
            r8 = r5
        L5f:
            java.lang.String r2 = "config_ui_mode"
            int r12 = r12.getInt(r2, r0)
            int r0 = r1.uiModeOption
            if (r12 == r0) goto L83
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r0 = "Ui mode is changed to "
            r8.<init>(r0)
            r8.append(r12)
            r8.append(r3)
            r8.append(r11)
            java.lang.String r8 = r8.toString()
            android.util.Log.i(r4, r8)
            r1.uiModeOption = r12
            goto L84
        L83:
            r5 = r8
        L84:
            if (r5 == 0) goto La0
            int[] r8 = new int[]{r11}
            com.android.systemui.coverlauncher.widget.CoverLauncherWidgetViewController$Companion r11 = com.android.systemui.coverlauncher.widget.CoverLauncherWidgetViewController.Companion
            r11.getClass()
            com.android.systemui.coverlauncher.widget.CoverLauncherWidgetViewController r9 = com.android.systemui.coverlauncher.widget.CoverLauncherWidgetViewController.Companion.getInstance(r9)
            java.lang.Thread r11 = new java.lang.Thread
            com.android.systemui.coverlauncher.widget.CoverLauncherWidgetViewController$updateAppWidget$1 r12 = new com.android.systemui.coverlauncher.widget.CoverLauncherWidgetViewController$updateAppWidget$1
            r12.<init>(r8, r9, r10)
            r11.<init>(r12)
            r11.start()
        La0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.coverlauncher.widget.CoverLauncherLargeWidgetProvider.onAppWidgetOptionsChanged(android.content.Context, android.appwidget.AppWidgetManager, int, android.os.Bundle):void");
    }

    @Override // android.appwidget.AppWidgetProvider
    public final void onDeleted(Context context, int[] iArr) {
        Log.i("CoverLauncherWidgetProvider", "onDeleted, id=".concat(Arrays.toString(iArr)));
    }

    @Override // android.appwidget.AppWidgetProvider, android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        PackageInfo packageInfo;
        super.onReceive(context, intent);
        String action = intent.getAction();
        final String str = "CoverLauncherWidgetProvider";
        Log.i("CoverLauncherWidgetProvider", "onReceive : " + action);
        if (!"action_launch_app".equals(action)) {
            if ("com.samsung.settings.ACTION_UPDATE_WIDGET".equals(action)) {
                Log.i("CoverLauncherWidgetProvider", "update widget from settings");
                CoverLauncherWidgetViewController.Companion.getClass();
                CoverLauncherWidgetViewController.Companion.getInstance(context).updateAppWidget(false);
                return;
            }
            return;
        }
        if (this.mPackageUtil == null) {
            this.mPackageUtil = new CoverLauncherPackageUtils(context);
        }
        final String stringExtra = intent.getStringExtra("key_package_name");
        final int intExtra = intent.getIntExtra("key_profile_id", 0);
        final CoverLauncherPackageUtils coverLauncherPackageUtils = this.mPackageUtil;
        if (coverLauncherPackageUtils == null || stringExtra == null) {
            return;
        }
        try {
            packageInfo = coverLauncherPackageUtils.mPackageManager.getPackageInfo(stringExtra, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("CoverLauncherPackageUtils", "Failed to get packageInfo ".concat(stringExtra), e);
            coverLauncherPackageUtils.tryUpdateAppWidget();
            packageInfo = null;
        }
        if (packageInfo == null) {
            Log.i("CoverLauncherPackageUtils", "packageInfo is null : ".concat(stringExtra));
            coverLauncherPackageUtils.tryUpdateAppWidget();
            return;
        }
        HashMap hashMap = CoverLauncherPackageUtils.sDimension;
        hashMap.clear();
        hashMap.put("app_name", packageInfo.packageName);
        CoreSaLogger.logForSystemUI("CVSE1045", hashMap);
        new Thread(new Runnable() { // from class: com.android.systemui.coverlauncher.utils.CoverLauncherPackageUtils$startActivityForCoverLauncher$1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    ActivityTaskManager.getService().startActivityForCoverLauncherAsUser(CoverLauncherPackageUtils.this.mPackageManager.getLaunchIntentForPackage(stringExtra), str, intExtra);
                } catch (RemoteException e2) {
                    Log.e("CoverLauncherPackageUtils", "Failed to launch package " + stringExtra, e2);
                }
            }
        }).start();
    }

    @Override // android.appwidget.AppWidgetProvider
    public final void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] iArr) {
        Log.i("CoverLauncherWidgetProvider", "onUpdate, id=".concat(Arrays.toString(iArr)));
        CoverLauncherWidgetViewController.Companion.getClass();
        new Thread(new CoverLauncherWidgetViewController$updateAppWidget$1(iArr, CoverLauncherWidgetViewController.Companion.getInstance(context), appWidgetManager)).start();
    }
}
