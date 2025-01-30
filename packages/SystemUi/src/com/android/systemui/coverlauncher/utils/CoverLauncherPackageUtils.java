package com.android.systemui.coverlauncher.utils;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.android.systemui.coverlauncher.utils.CoverLauncherWidgetUtils;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CoverLauncherPackageUtils {
    public final Context mContext;
    public final PackageManager mPackageManager;
    public final ArrayList mAllowedPackageList = new ArrayList();
    public final Object mLock = new Object();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AppLabelComparator implements Comparator {
        public /* synthetic */ AppLabelComparator(CoverLauncherPackageUtils coverLauncherPackageUtils, int i) {
            this();
        }

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            CoverLauncherPackageInfo coverLauncherPackageInfo = (CoverLauncherPackageInfo) obj;
            CoverLauncherPackageInfo coverLauncherPackageInfo2 = (CoverLauncherPackageInfo) obj2;
            String applicationLabel = CoverLauncherPackageUtils.this.getApplicationLabel(coverLauncherPackageInfo.mPackageName);
            String applicationLabel2 = CoverLauncherPackageUtils.this.getApplicationLabel(coverLauncherPackageInfo2.mPackageName);
            if (applicationLabel == null || applicationLabel2 == null) {
                return 0;
            }
            Collator collator = Collator.getInstance(Locale.getDefault());
            collator.setStrength(0);
            int compare = collator.compare(applicationLabel, applicationLabel2);
            if (compare == 0 && (compare = coverLauncherPackageInfo.mPackageName.compareTo(coverLauncherPackageInfo2.mPackageName)) == 0) {
                compare = Integer.compare(coverLauncherPackageInfo.mProfileId, coverLauncherPackageInfo2.mProfileId);
            }
            return compare;
        }

        private AppLabelComparator() {
        }
    }

    public CoverLauncherPackageUtils(Context context) {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
    }

    public static ColorFilter getGrayFilter() {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        ColorMatrix colorMatrix2 = new ColorMatrix();
        float[] array = colorMatrix2.getArray();
        array[0] = 0.5f;
        array[6] = 0.5f;
        array[12] = 0.5f;
        array[4] = 127.5f;
        array[9] = 127.5f;
        array[14] = 127.5f;
        array[18] = 1.0f;
        colorMatrix.postConcat(colorMatrix2);
        return new ColorMatrixColorFilter(colorMatrix);
    }

    public final String getApplicationLabel(String str) {
        PackageManager packageManager = this.mPackageManager;
        try {
            return packageManager.getApplicationLabel(packageManager.getApplicationInfo(str, 0)).toString();
        } catch (Exception e) {
            Log.e("CoverLauncherPackageUtils", "Failed to get Application Label " + str, e);
            this.tryUpdateAppWidget();
            return null;
        }
    }

    public final void tryUpdateAppWidget() {
        Context context = this.mContext;
        if (context != null) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.android.systemui.coverlauncher.widget.CoverLauncherWidgetViewController.1
                public RunnableC12111() {
                }

                @Override // java.lang.Runnable
                public final void run() {
                    CoverLauncherWidgetViewController coverLauncherWidgetViewController = CoverLauncherWidgetViewController.this;
                    Context context2 = coverLauncherWidgetViewController.mContext;
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context2);
                    for (Class cls : CoverLauncherWidgetUtils.sWidgetClassArray) {
                        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context2, (Class<?>) cls));
                        if (appWidgetIds.length > 0) {
                            new Thread(coverLauncherWidgetViewController.new RunnableC12122(appWidgetIds, appWidgetManager)).start();
                        }
                    }
                }
            }, 500);
        }
    }
}
