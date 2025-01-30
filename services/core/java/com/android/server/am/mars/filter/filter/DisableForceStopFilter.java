package com.android.server.am.mars.filter.filter;

import android.content.Context;
import com.android.server.am.mars.filter.IFilter;

/* loaded from: classes.dex */
public class DisableForceStopFilter implements IFilter {

    public abstract class DisableForceStopFilterHolder {
        public static final DisableForceStopFilter INSTANCE = new DisableForceStopFilter();
    }

    public DisableForceStopFilter() {
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void deInit() {
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public int filter(String str, int i, int i2, int i3) {
        return 0;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void init(Context context) {
    }

    public static DisableForceStopFilter getInstance() {
        return DisableForceStopFilterHolder.INSTANCE;
    }
}
