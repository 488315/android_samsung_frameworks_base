package com.android.systemui.coverlauncher.utils;

import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.coverlauncher.widget.CoverLauncherWidgetViewController;
import com.samsung.android.app.SemDualAppManager;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CoverLauncherPackageUtils {
    public static ArrayList sAppList;
    public static final HashMap sDimension;
    public final Context mContext;
    public final PackageManager mPackageManager;
    public final String EXTRA_IS_LAUNCHED_FROM_APPS_COVER_LAUNCHER = "com.sec.intent.extra.IS_LAUNCHED_FROM_APPS_COVER_LAUNCHER";
    public final ArrayList mAllowedPackageList = new ArrayList();
    public final Object mLock = new Object();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AppLabelComparator implements Comparator {
        public AppLabelComparator() {
        }

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            CoverLauncherPackageInfo coverLauncherPackageInfo = (CoverLauncherPackageInfo) obj;
            CoverLauncherPackageInfo coverLauncherPackageInfo2 = (CoverLauncherPackageInfo) obj2;
            String applicationLabel = CoverLauncherPackageUtils.this.getApplicationLabel(coverLauncherPackageInfo.packageName);
            String applicationLabel2 = CoverLauncherPackageUtils.this.getApplicationLabel(coverLauncherPackageInfo2.packageName);
            if (applicationLabel == null || applicationLabel2 == null) {
                return 0;
            }
            Collator collator = Collator.getInstance(Locale.getDefault());
            collator.setStrength(0);
            int compare = collator.compare(applicationLabel, applicationLabel2);
            if (compare != 0) {
                return compare;
            }
            int compareTo = coverLauncherPackageInfo.packageName.compareTo(coverLauncherPackageInfo2.packageName);
            return compareTo == 0 ? Intrinsics.compare(coverLauncherPackageInfo.profileId, coverLauncherPackageInfo2.profileId) : compareTo;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        sDimension = new HashMap();
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

    public final ArrayList getAppListFromDB(boolean z) {
        if (z || sAppList == null) {
            ArrayList arrayList = null;
            try {
                IActivityTaskManager service = ActivityTaskManager.getService();
                Map coverLauncherEnabledAppList = service.getCoverLauncherEnabledAppList(-2);
                if (coverLauncherEnabledAppList != null) {
                    ArrayList arrayList2 = new ArrayList(CollectionsKt___CollectionsKt.toList(coverLauncherEnabledAppList.keySet()));
                    ArrayList arrayList3 = new ArrayList();
                    int size = arrayList2.size();
                    for (int i = 0; i < size; i++) {
                        arrayList3.add(new CoverLauncherPackageInfo((String) arrayList2.get(i), UserHandle.myUserId()));
                        Log.i("CoverLauncherPackageUtils", "add pkg : " + arrayList2.get(i) + ", " + UserHandle.myUserId());
                    }
                    int dualAppProfileId = SemDualAppManager.getDualAppProfileId();
                    if (SemDualAppManager.isDualAppId(dualAppProfileId)) {
                        Map coverLauncherEnabledAppList2 = service.getCoverLauncherEnabledAppList(dualAppProfileId);
                        if (coverLauncherEnabledAppList2 != null) {
                            ArrayList arrayList4 = new ArrayList(CollectionsKt___CollectionsKt.toList(coverLauncherEnabledAppList2.keySet()));
                            int size2 = arrayList4.size();
                            for (int i2 = 0; i2 < size2; i2++) {
                                arrayList3.add(new CoverLauncherPackageInfo((String) arrayList4.get(i2), dualAppProfileId));
                                Log.i("CoverLauncherPackageUtils", "add pkg : " + arrayList4.get(i2) + ", " + dualAppProfileId);
                            }
                        }
                    }
                    synchronized (this.mLock) {
                        this.mAllowedPackageList.clear();
                        this.mAllowedPackageList.addAll(arrayList3);
                        CollectionsKt__MutableCollectionsJVMKt.sortWith(this.mAllowedPackageList, new AppLabelComparator());
                        Unit unit = Unit.INSTANCE;
                    }
                    arrayList = this.mAllowedPackageList;
                }
            } catch (Exception e) {
                Log.e("CoverLauncherPackageUtils", "Failed to get allowed package list ", e);
                tryUpdateAppWidget();
            }
            sAppList = arrayList;
        }
        return sAppList != null ? new ArrayList(sAppList) : new ArrayList();
    }

    public final String getApplicationLabel(String str) {
        try {
            return this.mPackageManager.getApplicationLabel(this.mPackageManager.getApplicationInfo(str, 0)).toString();
        } catch (Exception e) {
            Log.e("CoverLauncherPackageUtils", "Failed to get Application Label " + str, e);
            this.tryUpdateAppWidget();
            return null;
        }
    }

    public final void tryUpdateAppWidget() {
        CoverLauncherWidgetViewController.Companion companion = CoverLauncherWidgetViewController.Companion;
        Context context = this.mContext;
        companion.getClass();
        final CoverLauncherWidgetViewController companion2 = CoverLauncherWidgetViewController.Companion.getInstance(context);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.android.systemui.coverlauncher.widget.CoverLauncherWidgetViewController$updateAppWidgetDelayed$1
            @Override // java.lang.Runnable
            public final void run() {
                CoverLauncherWidgetViewController.this.updateAppWidget(false);
            }
        }, 500);
    }
}
