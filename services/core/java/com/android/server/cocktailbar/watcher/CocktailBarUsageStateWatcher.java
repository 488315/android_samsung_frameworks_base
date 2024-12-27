package com.android.server.cocktailbar.watcher;

import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashSet;

public final class CocktailBarUsageStateWatcher {
    public ActivityManager mActivityManager;
    public ArrayList mComponentsToHideEdge;
    public Context mContext;
    public int mLevel;
    public OnCocktailBarWatcherListener mListener;
    public Object mLock;
    public String mMetaDataHideEdgeService;
    public HashSet mPackageHideEdgeServiceList;
    public AnonymousClass1 mUsageStatsWatcher;

    public interface OnCocktailBarWatcherListener {}

    public final String dump() {
        StringBuffer stringBuffer = new StringBuffer("[UsageStateWatcher]: ");
        synchronized (this.mLock) {
            try {
                ArrayList arrayList = this.mComponentsToHideEdge;
                if (arrayList != null) {
                    int size = arrayList.size();
                    for (int i = 0; i < size; i++) {
                        stringBuffer.append((String) this.mComponentsToHideEdge.get(i));
                        stringBuffer.append(" ");
                    }
                    stringBuffer.append("\n");
                } else {
                    stringBuffer.append("null\n");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return stringBuffer.toString();
    }
}
