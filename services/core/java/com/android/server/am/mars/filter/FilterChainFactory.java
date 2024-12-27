package com.android.server.am.mars.filter;

import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class FilterChainFactory {
    public HashMap filterHashMap;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class FilterChainFactoryHolder {
        public static final FilterChainFactory INSTANCE;

        static {
            FilterChainFactory filterChainFactory = new FilterChainFactory();
            filterChainFactory.filterHashMap = new HashMap();
            INSTANCE = filterChainFactory;
        }
    }
}
