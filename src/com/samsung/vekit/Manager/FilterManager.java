package com.samsung.vekit.Manager;

import android.util.Log;
import com.samsung.vekit.Common.Object.Filter;
import com.samsung.vekit.Common.Type.ManagerType;
import com.samsung.vekit.Common.VEContext;

/* loaded from: classes6.dex */
public class FilterManager extends Manager<Filter> {
    public FilterManager(VEContext vEContext) {
        super(vEContext, ManagerType.FILTER);
        this.TAG = getClass().getSimpleName();
    }

    public Filter create(String str, String str2) {
        try {
            Filter filter = new Filter(this.context, generateUniqueId(), str, str2);
            add(filter);
            return filter;
        } catch (Exception e) {
            Log.e(this.TAG, "create: ", e);
            return null;
        }
    }
}
