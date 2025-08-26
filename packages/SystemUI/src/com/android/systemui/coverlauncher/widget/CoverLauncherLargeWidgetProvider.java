package com.android.systemui.coverlauncher.widget;

import android.app.ActivityTaskManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.coverlauncher.utils.CoverLauncherPackageUtils;
import com.android.systemui.coverlauncher.utils.CoverLauncherWidgetOptions;
import com.android.systemui.coverlauncher.widget.CoverLauncherWidgetViewController;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.CoreRune;
import java.util.Arrays;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$ObjectRef;

/* loaded from: classes2.dex */
public final class CoverLauncherLargeWidgetProvider extends AppWidgetProvider {
    public static final CoverLauncherWidgetOptions sWidgetOptions;
    public CoverLauncherPackageUtils mPackageUtil;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        sWidgetOptions = new CoverLauncherWidgetOptions(false, null, 0, 7, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003e  */
    @Override // android.appwidget.AppWidgetProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int i, Bundle bundle) {
        boolean z;
        boolean z2 = bundle.getBoolean("visible", false);
        CoverLauncherWidgetOptions coverLauncherWidgetOptions = sWidgetOptions;
        boolean z3 = true;
        if (z2 != coverLauncherWidgetOptions.visibleOption) {
            Log.i("CoverLauncherWidgetProvider", "visible changed to " + z2 + ", id=" + i);
            coverLauncherWidgetOptions.visibleOption = z2;
            z = (z2 && Settings.Secure.getInt(context.getContentResolver(), "notification_badging", 0) == 0) ? false : true;
        }
        String string = bundle.getString("appIconPackageName");
        if (string != null && !string.equals(coverLauncherWidgetOptions.appIconPkgOption)) {
            TooltipPopup$$ExternalSyntheticOutline0.m(i, "CoverLauncherWidgetProvider", SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("appIcon pkg is updated to ", string, " from ", coverLauncherWidgetOptions.appIconPkgOption, ", id="));
            coverLauncherWidgetOptions.appIconPkgOption = string;
            z = true;
        }
        int i2 = bundle.getInt("config_ui_mode", 0);
        if (i2 != coverLauncherWidgetOptions.uiModeOption) {
            Log.i("CoverLauncherWidgetProvider", "Ui mode is changed to " + i2 + ", id=" + i);
            coverLauncherWidgetOptions.uiModeOption = i2;
        } else {
            z3 = z;
        }
        if (z3) {
            CoverLauncherWidgetViewController.Companion.getClass();
            new Thread(new CoverLauncherWidgetViewController.AnonymousClass1(new int[]{i}, CoverLauncherWidgetViewController.Companion.getInstance(context), appWidgetManager)).start();
        }
    }

    @Override // android.appwidget.AppWidgetProvider
    public final void onDeleted(Context context, int[] iArr) {
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("onDeleted, id=", Arrays.toString(iArr), "CoverLauncherWidgetProvider");
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [T, android.content.Intent] */
    @Override // android.appwidget.AppWidgetProvider, android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) throws PackageManager.NameNotFoundException {
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
        CoverLauncherPackageUtils coverLauncherPackageUtils = this.mPackageUtil;
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
        HashMap map = CoverLauncherPackageUtils.sDimension;
        map.clear();
        map.put("app_name", packageInfo.packageName);
        CoreSaLogger.logForSystemUI("CVSE1045", map);
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ?? launchIntentForPackage = coverLauncherPackageUtils.mPackageManager.getLaunchIntentForPackage(stringExtra);
        ref$ObjectRef.element = launchIntentForPackage;
        if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APPS_CUTOUT && launchIntentForPackage != 0) {
            launchIntentForPackage.putExtra(coverLauncherPackageUtils.EXTRA_IS_LAUNCHED_FROM_APPS_COVER_LAUNCHER, true);
        }
        new Thread(new Runnable() { // from class: com.android.systemui.coverlauncher.utils.CoverLauncherPackageUtils$startActivityForCoverLauncher$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    ActivityTaskManager.getService().startActivityForCoverLauncherAsUser((Intent) ref$ObjectRef.element, str, intExtra);
                } catch (RemoteException e2) {
                    Log.e("CoverLauncherPackageUtils", "Failed to launch package " + stringExtra, e2);
                }
            }
        }).start();
    }

    @Override // android.appwidget.AppWidgetProvider
    public final void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] iArr) {
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("onUpdate, id=", Arrays.toString(iArr), "CoverLauncherWidgetProvider");
        CoverLauncherWidgetViewController.Companion.getClass();
        new Thread(new CoverLauncherWidgetViewController.AnonymousClass1(iArr, CoverLauncherWidgetViewController.Companion.getInstance(context), appWidgetManager)).start();
    }
}
