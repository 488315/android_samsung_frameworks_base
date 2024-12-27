package com.android.server.am.mars.filter.filter;

import android.content.Context;

import com.android.server.am.mars.filter.IFilter;

import java.util.ArrayList;

public final class RunningBroadcastFilter implements IFilter {
    public ArrayList mRunningBroadcastList;

    public abstract class RunningBroadcastFilterHolder {
        public static final RunningBroadcastFilter INSTANCE;

        static {
            RunningBroadcastFilter runningBroadcastFilter = new RunningBroadcastFilter();
            runningBroadcastFilter.mRunningBroadcastList = new ArrayList();
            INSTANCE = runningBroadcastFilter;
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {
        this.mRunningBroadcastList.clear();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        Integer valueOf = Integer.valueOf(i2);
        synchronized (this.mRunningBroadcastList) {
            try {
                return this.mRunningBroadcastList.contains(valueOf) ? 33 : 0;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {}
}
