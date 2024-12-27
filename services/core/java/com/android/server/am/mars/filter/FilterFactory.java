package com.android.server.am.mars.filter;

import android.content.Context;

import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class FilterFactory {
    public HashMap filterHashMap;
    public Context mContext;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class FilterFactoryHolder {
        public static final FilterFactory INSTANCE;

        static {
            FilterFactory filterFactory = new FilterFactory();
            filterFactory.filterHashMap = new HashMap();
            INSTANCE = filterFactory;
        }
    }

    public final IFilter getFilter(int i) {
        return (IFilter) this.filterHashMap.get(Integer.valueOf(i));
    }
}
