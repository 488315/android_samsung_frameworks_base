package com.android.systemui.coverlauncher.utils;

import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.content.Context;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.coverlauncher.utils.CoverLauncherPackageUtils;
import com.android.systemui.coverlauncher.widget.CoverLauncherLargeRemoteViewService;
import com.android.systemui.coverlauncher.widget.CoverLauncherLargeWidgetProvider;
import com.android.systemui.coverlauncher.widget.CoverLauncherMediumRemoteViewService;
import com.android.systemui.coverlauncher.widget.CoverLauncherMediumWidgetProvider;
import com.android.systemui.coverlauncher.widget.CoverLauncherSmallRemoteViewService;
import com.android.systemui.coverlauncher.widget.CoverLauncherSmallWidgetProvider;
import com.samsung.android.app.SemDualAppManager;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CoverLauncherWidgetUtils {
    public static ArrayList sAppList;
    public final Context mContext;
    public static final Class[] sWidgetClassArray = {CoverLauncherLargeWidgetProvider.class, CoverLauncherMediumWidgetProvider.class, CoverLauncherSmallWidgetProvider.class};
    public static final Class[] sRemoteViewsClassArray = {CoverLauncherLargeRemoteViewService.class, CoverLauncherMediumRemoteViewService.class, CoverLauncherSmallRemoteViewService.class};

    public CoverLauncherWidgetUtils(Context context) {
        this.mContext = context;
    }

    public static ArrayList getAppListFromDB(Context context, boolean z) {
        ArrayList arrayList;
        CoverLauncherPackageUtils coverLauncherPackageUtils = new CoverLauncherPackageUtils(context);
        if (z || sAppList == null) {
            try {
                IActivityTaskManager service = ActivityTaskManager.getService();
                ArrayList arrayList2 = new ArrayList(service.getCoverLauncherEnabledAppList(-2).keySet());
                ArrayList arrayList3 = new ArrayList();
                int i = 0;
                for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                    arrayList3.add(new CoverLauncherPackageInfo((String) arrayList2.get(i2), UserHandle.myUserId()));
                    Log.i("CoverLauncherPackageUtils", "add pkg : " + ((String) arrayList2.get(i2)) + ", " + UserHandle.myUserId());
                }
                int dualAppProfileId = SemDualAppManager.getDualAppProfileId();
                if (SemDualAppManager.isDualAppId(dualAppProfileId)) {
                    ArrayList arrayList4 = new ArrayList(service.getCoverLauncherEnabledAppList(dualAppProfileId).keySet());
                    for (int i3 = 0; i3 < arrayList4.size(); i3++) {
                        arrayList3.add(new CoverLauncherPackageInfo((String) arrayList4.get(i3), dualAppProfileId));
                        Log.i("CoverLauncherPackageUtils", "add pkg : " + ((String) arrayList4.get(i3)) + ", " + dualAppProfileId);
                    }
                }
                synchronized (coverLauncherPackageUtils.mLock) {
                    coverLauncherPackageUtils.mAllowedPackageList.clear();
                    coverLauncherPackageUtils.mAllowedPackageList.addAll(arrayList3);
                    coverLauncherPackageUtils.mAllowedPackageList.sort(new CoverLauncherPackageUtils.AppLabelComparator(coverLauncherPackageUtils, i));
                }
                arrayList = coverLauncherPackageUtils.mAllowedPackageList;
            } catch (Exception e) {
                Log.e("CoverLauncherPackageUtils", "Failed to get allowed package list ", e);
                coverLauncherPackageUtils.tryUpdateAppWidget();
                arrayList = null;
            }
            sAppList = arrayList;
        }
        return new ArrayList(sAppList);
    }
}
