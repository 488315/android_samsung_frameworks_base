package com.android.server.am.mars.filter.filter;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArrayMap;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.filter.IFilter;
import com.samsung.android.app.SemDualAppManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class WidgetPkgFilter implements IFilter {
    public ArrayMap mBoundedWidgetPkgs;
    public Context mContext;
    public int mContextUserId;
    public ArrayMap mRunningWidgets;

    public abstract class WidgetPkgFilterHolder {
        public static final WidgetPkgFilter INSTANCE = new WidgetPkgFilter();
    }

    public WidgetPkgFilter() {
        this.mContext = null;
        this.mContextUserId = 0;
        this.mRunningWidgets = new ArrayMap();
        this.mBoundedWidgetPkgs = new ArrayMap();
    }

    public static WidgetPkgFilter getInstance() {
        return WidgetPkgFilterHolder.INSTANCE;
    }

    public final void setContext(Context context) {
        this.mContext = context;
        this.mContextUserId = context.getUserId();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void init(Context context) {
        setContext(context);
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void deInit() {
        synchronized (this.mBoundedWidgetPkgs) {
            this.mBoundedWidgetPkgs.clear();
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public int filter(String str, int i, int i2, int i3) {
        ArrayList arrayList;
        if (i != this.mContextUserId && !isSubUser(i)) {
            return 0;
        }
        if (MARsPolicyManager.getInstance().isFirstTimeTriggerAutorun() && i3 == 3) {
            synchronized (this.mBoundedWidgetPkgs) {
                if (this.mBoundedWidgetPkgs.size() > 0 && (arrayList = (ArrayList) this.mBoundedWidgetPkgs.get(Integer.valueOf(i))) != null && arrayList.contains(str)) {
                    return 4;
                }
            }
        }
        synchronized (this.mRunningWidgets) {
            WidgetPackages widgetPackages = (WidgetPackages) this.mRunningWidgets.get(Integer.valueOf(i));
            return (widgetPackages == null || !widgetPackages.contains(str)) ? 0 : 4;
        }
    }

    public final boolean isSubUser(int i) {
        if (this.mContextUserId != 0) {
            return false;
        }
        return (i >= 150 && i <= 160) || SemDualAppManager.isDualAppId(i);
    }

    public void getBoundAppWidgetPackages() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.mContext);
        for (UserHandle userHandle : ((UserManager) this.mContext.getSystemService(UserManager.class)).getUserProfiles()) {
            int identifier = userHandle.getIdentifier();
            if (identifier == this.mContextUserId || isSubUser(identifier)) {
                List installedProvidersForProfile = appWidgetManager.getInstalledProvidersForProfile(3, userHandle);
                for (int i = 0; i < installedProvidersForProfile.size(); i++) {
                    String packageName = ((AppWidgetProviderInfo) installedProvidersForProfile.get(i)).provider.getPackageName();
                    if (appWidgetManager.isBoundWidgetPackage(packageName, identifier)) {
                        synchronized (this.mBoundedWidgetPkgs) {
                            ArrayList arrayList = (ArrayList) this.mBoundedWidgetPkgs.get(Integer.valueOf(identifier));
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                            }
                            if (!arrayList.contains(packageName)) {
                                arrayList.add(packageName);
                                MARsPolicyManager.getInstance().addFilterDebugInfoToHistory("FILTER 4 bound", packageName);
                            }
                            this.mBoundedWidgetPkgs.put(Integer.valueOf(identifier), arrayList);
                        }
                    }
                }
            }
        }
    }

    public void onAppWidgetEnabled(String str, int i) {
        synchronized (this.mRunningWidgets) {
            WidgetPackages widgetPackages = (WidgetPackages) this.mRunningWidgets.get(Integer.valueOf(i));
            if (widgetPackages == null) {
                widgetPackages = new WidgetPackages();
            }
            widgetPackages.add(str);
            MARsPolicyManager.getInstance().addFilterDebugInfoToHistory("FILTER 4", str);
            this.mRunningWidgets.put(Integer.valueOf(i), widgetPackages);
        }
    }

    public void onAppWidgetDisabled(String str, int i) {
        synchronized (this.mRunningWidgets) {
            WidgetPackages widgetPackages = (WidgetPackages) this.mRunningWidgets.get(Integer.valueOf(i));
            if (widgetPackages != null && widgetPackages.contains(str)) {
                widgetPackages.remove(str);
                MARsPolicyManager.getInstance().addFilterDebugInfoToHistory("FILTER 4 remove", str);
                this.mRunningWidgets.put(Integer.valueOf(i), widgetPackages);
            }
        }
    }

    public class WidgetPackages {
        public ArrayMap mMap;

        public WidgetPackages() {
            this.mMap = new ArrayMap();
        }

        public boolean contains(String str) {
            return this.mMap.get(str) != null;
        }

        public void add(String str) {
            Integer num = (Integer) this.mMap.get(str);
            if (num == null) {
                num = 0;
            }
            this.mMap.put(str, Integer.valueOf(num.intValue() + 1));
        }

        public void remove(String str) {
            if (((Integer) this.mMap.get(str)) == null) {
                return;
            }
            Integer valueOf = Integer.valueOf(r0.intValue() - 1);
            if (valueOf.intValue() <= 0) {
                this.mMap.remove(str);
            } else {
                this.mMap.put(str, valueOf);
            }
        }
    }
}
