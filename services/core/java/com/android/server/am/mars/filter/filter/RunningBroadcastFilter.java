package com.android.server.am.mars.filter.filter;

import android.content.Context;

import com.android.server.am.mars.filter.IFilter;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RunningBroadcastFilter implements IFilter {
    public ArrayList mRunningBroadcastList;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
