package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.os.UserHandle;
import com.android.server.am.mars.filter.IFilter;

/* loaded from: classes.dex */
public class SystemFilter implements IFilter {

    public abstract class SystemFilterHolder {
        public static final SystemFilter INSTANCE = new SystemFilter();
    }

    public SystemFilter() {
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void deInit() {
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void init(Context context) {
    }

    public static SystemFilter getInstance() {
        return SystemFilterHolder.INSTANCE;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public int filter(String str, int i, int i2, int i3) {
        if (i != 0) {
            i2 = UserHandle.getAppId(i2);
        }
        if (i2 == 1000) {
            return 14;
        }
        return (i3 != 17 || i2 < 0 || i2 >= 10000) ? 0 : 14;
    }
}
