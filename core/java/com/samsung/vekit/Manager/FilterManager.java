package com.samsung.vekit.Manager;

import android.util.Log;
import com.samsung.vekit.Common.Object.Filter;
import com.samsung.vekit.Common.Type.ManagerType;
import com.samsung.vekit.Common.VEContext;

/* loaded from: classes6.dex */
public class FilterManager extends Manager<Filter> {
    public FilterManager(VEContext context) {
        super(context, ManagerType.FILTER);
        this.TAG = getClass().getSimpleName();
    }

    public Filter create(String name, String filterPath) {
        try {
            int uniqueId = generateUniqueId();
            Filter filter = new Filter(this.context, uniqueId, name, filterPath);
            add(filter);
            return filter;
        } catch (Exception e) {
            Log.m97e(this.TAG, "create: ", e);
            return null;
        }
    }
}
