package com.android.server.am.mars.filter.filter;

import android.content.Context;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.filter.IFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ActiveSensorFilter implements IFilter {
    public final List mFilterTypeList = new ArrayList(Arrays.asList(1));
    public List mSensorList;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class ActiveSensorFilterHolder {
        public static final ActiveSensorFilter INSTANCE = new ActiveSensorFilter();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        List list;
        if (!MARsUtils.isChinaPolicyEnabled()) {
            boolean z = MARsPolicyManager.MARs_ENABLE;
            if (MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getForegroundServiceStartTime(i2) != 0 && (list = this.mSensorList) != null) {
                if (((ArrayList) list).contains(Integer.valueOf(i2))) {
                    return 23;
                }
            }
        }
        return 0;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
    }

    public final List parseActiveSensor(String str) {
        if (str == null) {
            return null;
        }
        HashSet hashSet = new HashSet();
        for (String str2 : str.split(":")) {
            String[] split = str2.split(" ");
            if (split.length > 1) {
                int parseInt = Integer.parseInt(split[0]);
                int parseInt2 = Integer.parseInt(split[1]);
                if (((ArrayList) this.mFilterTypeList).contains(Integer.valueOf(parseInt)) && parseInt2 != 1000) {
                    hashSet.add(Integer.valueOf(parseInt2));
                }
            }
        }
        return new ArrayList(hashSet);
    }
}
