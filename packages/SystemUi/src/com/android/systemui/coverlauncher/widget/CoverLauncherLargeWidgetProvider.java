package com.android.systemui.coverlauncher.widget;

import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.systemui.coverlauncher.utils.CoverLauncherPackageUtils;
import com.android.systemui.coverlauncher.utils.CoverLauncherWidgetOptions;
import com.android.systemui.coverlauncher.widget.CoverLauncherWidgetViewController.RunnableC12122;
import com.samsung.android.core.CoreSaLogger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class CoverLauncherLargeWidgetProvider extends AppWidgetProvider {
    public static final HashMap sDimension = new HashMap();
    public static final HashMap sWidgetOptions = new HashMap();
    public CoverLauncherPackageUtils mCoverLauncherPackageUtil;

    public static void updateAppWidgetView(Context context, AppWidgetManager appWidgetManager, int[] iArr) {
        CoverLauncherWidgetViewController coverLauncherWidgetViewController = CoverLauncherWidgetViewController.getInstance(context);
        coverLauncherWidgetViewController.getClass();
        new Thread(coverLauncherWidgetViewController.new RunnableC12122(iArr, appWidgetManager)).start();
    }

    public int getProviderType() {
        return 0;
    }

    public HashMap getWidgetOptions() {
        return sWidgetOptions;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0056, code lost:
    
        if ((android.provider.Settings.Secure.getInt(r12.getContentResolver(), "notification_badging", 0) != 0) != false) goto L15;
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0086  */
    @Override // android.appwidget.AppWidgetProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int i, Bundle bundle) {
        boolean z;
        String string;
        boolean z2;
        int i2;
        HashMap widgetOptions = getWidgetOptions();
        int providerType = getProviderType();
        CoverLauncherWidgetOptions coverLauncherWidgetOptions = (CoverLauncherWidgetOptions) widgetOptions.get(Integer.valueOf(i));
        if (coverLauncherWidgetOptions == null) {
            Integer valueOf = Integer.valueOf(i);
            CoverLauncherWidgetOptions coverLauncherWidgetOptions2 = new CoverLauncherWidgetOptions(false, null, 0, providerType);
            widgetOptions.put(valueOf, coverLauncherWidgetOptions2);
            coverLauncherWidgetOptions = coverLauncherWidgetOptions2;
        }
        boolean z3 = bundle.getBoolean("visible", false);
        boolean z4 = coverLauncherWidgetOptions.mVisibleOption;
        boolean z5 = true;
        int i3 = coverLauncherWidgetOptions.mType;
        if (z3 != z4) {
            TooltipPopup$$ExternalSyntheticOutline0.m13m(KeyguardFMMViewController$$ExternalSyntheticOutline0.m66m("visible changed to ", z3, ", id=", i, ", type="), i3, "CoverLauncherWidgetProvider");
            coverLauncherWidgetOptions.mVisibleOption = z3;
            if (z3) {
            }
            z = true;
            string = bundle.getString("appIconPackageName");
            if (Objects.equals(string, coverLauncherWidgetOptions.mAppIconPkgOption)) {
                StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("appIcon pkg is updated to ", string, " from");
                m4m.append(coverLauncherWidgetOptions.mAppIconPkgOption);
                m4m.append(", id=");
                m4m.append(i);
                m4m.append(", type=");
                TooltipPopup$$ExternalSyntheticOutline0.m13m(m4m, i3, "CoverLauncherWidgetProvider");
                coverLauncherWidgetOptions.mAppIconPkgOption = string;
                z2 = true;
            } else {
                z2 = false;
            }
            i2 = bundle.getInt("config_ui_mode", 0);
            if (i2 == coverLauncherWidgetOptions.mUiModeOption) {
                TooltipPopup$$ExternalSyntheticOutline0.m13m(GridLayoutManager$$ExternalSyntheticOutline0.m45m("Ui mode is changed to ", i2, ", id=", i, ", type="), i3, "CoverLauncherWidgetProvider");
                coverLauncherWidgetOptions.mUiModeOption = i2;
            } else {
                z5 = z2;
            }
            if (!z5) {
                updateAppWidgetViewWithProvider(context, appWidgetManager);
                return;
            } else {
                if (z) {
                    updateAppWidgetView(context, appWidgetManager, new int[]{i});
                    return;
                }
                return;
            }
        }
        z = false;
        string = bundle.getString("appIconPackageName");
        if (Objects.equals(string, coverLauncherWidgetOptions.mAppIconPkgOption)) {
        }
        i2 = bundle.getInt("config_ui_mode", 0);
        if (i2 == coverLauncherWidgetOptions.mUiModeOption) {
        }
        if (!z5) {
        }
    }

    @Override // android.appwidget.AppWidgetProvider
    public void onDeleted(Context context, int[] iArr) {
        Log.i("CoverLauncherWidgetProvider", "onDeleted, id=" + Arrays.toString(iArr) + ", type=" + getProviderType());
        for (int i : iArr) {
            getWidgetOptions().remove(Integer.valueOf(i));
        }
    }

    @Override // android.appwidget.AppWidgetProvider, android.content.BroadcastReceiver
    public void onReceive(Context context, final Intent intent) {
        Bundle extras;
        PackageInfo packageInfo;
        super.onReceive(context, intent);
        String action = intent.getAction();
        Log.i("CoverLauncherWidgetProvider", "onReceive : " + action);
        if (!"action_launch_app".equals(action)) {
            if (!"com.samsung.settings.ACTION_UPDATE_WIDGET".equals(action) || (extras = intent.getExtras()) == null) {
                return;
            }
            int[] intArray = extras.getIntArray("appWidgetIds");
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            Log.i("CoverLauncherWidgetProvider", "update widget from settings, id=" + Arrays.toString(intArray));
            updateAppWidgetView(context, appWidgetManager, intArray);
            return;
        }
        if (this.mCoverLauncherPackageUtil == null) {
            this.mCoverLauncherPackageUtil = new CoverLauncherPackageUtils(context);
        }
        final String stringExtra = intent.getStringExtra("key_package_name");
        CoverLauncherPackageUtils coverLauncherPackageUtils = this.mCoverLauncherPackageUtil;
        coverLauncherPackageUtils.getClass();
        try {
            packageInfo = coverLauncherPackageUtils.mPackageManager.getPackageInfo(stringExtra, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("CoverLauncherPackageUtils", "Failed to get packageInfo " + stringExtra, e);
            coverLauncherPackageUtils.tryUpdateAppWidget();
            packageInfo = null;
        }
        if (packageInfo == null) {
            Log.i("CoverLauncherWidgetProvider", "packageInfo is null : " + stringExtra);
            updateAppWidgetViewWithProvider(context, AppWidgetManager.getInstance(context));
            return;
        }
        HashMap hashMap = sDimension;
        hashMap.clear();
        hashMap.put("app_name", packageInfo.packageName);
        CoreSaLogger.logForSystemUI("CVSE1045", hashMap);
        new Thread(new Runnable() { // from class: com.android.systemui.coverlauncher.widget.CoverLauncherLargeWidgetProvider.1
            @Override // java.lang.Runnable
            public final void run() {
                IActivityTaskManager service = ActivityTaskManager.getService();
                try {
                    service.startActivityForCoverLauncherAsUser(CoverLauncherLargeWidgetProvider.this.mCoverLauncherPackageUtil.mPackageManager.getLaunchIntentForPackage(stringExtra), "CoverLauncherWidgetProvider", intent.getIntExtra("key_profile_id", 0));
                } catch (RemoteException e2) {
                    Log.e("CoverLauncherWidgetProvider", "Failed to launch package " + stringExtra, e2);
                }
            }
        }).start();
    }

    @Override // android.appwidget.AppWidgetProvider
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] iArr) {
        Log.i("CoverLauncherWidgetProvider", "onUpdate, id=" + Arrays.toString(iArr) + ", type=" + getProviderType());
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            int i2 = iArr[i];
            HashMap widgetOptions = getWidgetOptions();
            int providerType = getProviderType();
            if (((CoverLauncherWidgetOptions) widgetOptions.get(Integer.valueOf(i2))) == null) {
                widgetOptions.put(Integer.valueOf(i2), new CoverLauncherWidgetOptions(false, null, 0, providerType));
            }
        }
        updateAppWidgetView(context, appWidgetManager, iArr);
    }

    public void updateAppWidgetViewWithProvider(Context context, AppWidgetManager appWidgetManager) {
        updateAppWidgetView(context, appWidgetManager, appWidgetManager.getAppWidgetIds(new ComponentName(context, (Class<?>) CoverLauncherLargeWidgetProvider.class)));
    }
}
