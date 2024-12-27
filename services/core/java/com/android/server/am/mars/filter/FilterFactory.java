package com.android.server.am.mars.filter;

import android.content.Context;

import java.util.HashMap;

public final class FilterFactory {
    public HashMap filterHashMap;
    public Context mContext;

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
