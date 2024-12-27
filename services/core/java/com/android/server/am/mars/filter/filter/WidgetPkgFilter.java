package com.android.server.am.mars.filter.filter;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArrayMap;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.filter.IFilter;
import com.samsung.android.app.SemDualAppManager;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class WidgetPkgFilter implements IFilter {
    public ArrayMap mBoundedWidgetPkgs;
    public Context mContext;
    public int mContextUserId;
    public ArrayMap mRunningWidgets;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WidgetPackages {
        public ArrayMap mMap;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class WidgetPkgFilterHolder {
        public static final WidgetPkgFilter INSTANCE;

        static {
            WidgetPkgFilter widgetPkgFilter = new WidgetPkgFilter();
            widgetPkgFilter.mContext = null;
            widgetPkgFilter.mContextUserId = 0;
            widgetPkgFilter.mRunningWidgets = new ArrayMap();
            widgetPkgFilter.mBoundedWidgetPkgs = new ArrayMap();
            INSTANCE = widgetPkgFilter;
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {
        synchronized (this.mBoundedWidgetPkgs) {
            this.mBoundedWidgetPkgs.clear();
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        ArrayList arrayList;
        int i4 = this.mContextUserId;
        if (i != i4 && (i4 != 0 || ((i < 150 || i > 160) && !SemDualAppManager.isDualAppId(i)))) {
            return 0;
        }
        boolean z = MARsUtils.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE;
        boolean z2 = MARsPolicyManager.MARs_ENABLE;
        if (MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.isFirstTimeTriggerAutorun() && i3 == 3) {
            synchronized (this.mBoundedWidgetPkgs) {
                try {
                    if (this.mBoundedWidgetPkgs.size() > 0 && (arrayList = (ArrayList) this.mBoundedWidgetPkgs.get(Integer.valueOf(i))) != null && arrayList.contains(str)) {
                        return 4;
                    }
                } finally {
                }
            }
        }
        synchronized (this.mRunningWidgets) {
            try {
                WidgetPackages widgetPackages = (WidgetPackages) this.mRunningWidgets.get(Integer.valueOf(i));
                return (widgetPackages == null || widgetPackages.mMap.get(str) == null) ? 0 : 4;
            } finally {
            }
        }
    }

    public final void getBoundAppWidgetPackages() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.mContext);
        for (UserHandle userHandle : ((UserManager) this.mContext.getSystemService(UserManager.class)).getUserProfiles()) {
            int identifier = userHandle.getIdentifier();
            int i = this.mContextUserId;
            if (identifier == i || (i == 0 && ((identifier >= 150 && identifier <= 160) || SemDualAppManager.isDualAppId(identifier)))) {
                List installedProvidersForProfile = appWidgetManager.getInstalledProvidersForProfile(3, userHandle);
                for (int i2 = 0; i2 < installedProvidersForProfile.size(); i2++) {
                    String packageName = ((AppWidgetProviderInfo) installedProvidersForProfile.get(i2)).provider.getPackageName();
                    if (appWidgetManager.isBoundWidgetPackage(packageName, identifier)) {
                        synchronized (this.mBoundedWidgetPkgs) {
                            try {
                                ArrayList arrayList = (ArrayList) this.mBoundedWidgetPkgs.get(Integer.valueOf(identifier));
                                if (arrayList == null) {
                                    arrayList = new ArrayList();
                                }
                                if (!arrayList.contains(packageName)) {
                                    arrayList.add(packageName);
                                    MARsUtils.addFilterDebugInfoToHistory("FILTER 4 bound", packageName);
                                }
                                this.mBoundedWidgetPkgs.put(Integer.valueOf(identifier), arrayList);
                            } finally {
                            }
                        }
                    }
                }
            }
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
        this.mContext = context;
        this.mContextUserId = context.getUserId();
    }

    public final void onAppWidgetDisabled(int i, String str) {
        synchronized (this.mRunningWidgets) {
            try {
                WidgetPackages widgetPackages = (WidgetPackages) this.mRunningWidgets.get(Integer.valueOf(i));
                if (widgetPackages != null && widgetPackages.mMap.get(str) != null) {
                    Integer num = (Integer) widgetPackages.mMap.get(str);
                    if (num != null) {
                        int intValue = num.intValue() - 1;
                        Integer valueOf = Integer.valueOf(intValue);
                        if (intValue <= 0) {
                            widgetPackages.mMap.remove(str);
                        } else {
                            widgetPackages.mMap.put(str, valueOf);
                        }
                    }
                    MARsUtils.addFilterDebugInfoToHistory("FILTER 4 remove", str);
                    this.mRunningWidgets.put(Integer.valueOf(i), widgetPackages);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onAppWidgetEnabled(int i, String str) {
        synchronized (this.mRunningWidgets) {
            try {
                WidgetPackages widgetPackages = (WidgetPackages) this.mRunningWidgets.get(Integer.valueOf(i));
                if (widgetPackages == null) {
                    widgetPackages = new WidgetPackages();
                    widgetPackages.mMap = new ArrayMap();
                }
                Integer num = (Integer) widgetPackages.mMap.get(str);
                if (num == null) {
                    num = 0;
                }
                widgetPackages.mMap.put(str, Integer.valueOf(num.intValue() + 1));
                MARsUtils.addFilterDebugInfoToHistory("FILTER 4", str);
                this.mRunningWidgets.put(Integer.valueOf(i), widgetPackages);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
