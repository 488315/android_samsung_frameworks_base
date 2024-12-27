package com.android.server.am.mars.filter.filter;

import android.content.Context;

import com.android.server.am.mars.filter.IFilter;

public final class DisableForceStopFilter implements IFilter {

    public abstract class DisableForceStopFilterHolder {
        public static final DisableForceStopFilter INSTANCE = new DisableForceStopFilter();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {}

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        return 0;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {}
}
