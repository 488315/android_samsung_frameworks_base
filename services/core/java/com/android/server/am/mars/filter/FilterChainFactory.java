package com.android.server.am.mars.filter;

import java.util.HashMap;

public final class FilterChainFactory {
    public HashMap filterHashMap;

    public abstract class FilterChainFactoryHolder {
        public static final FilterChainFactory INSTANCE;

        static {
            FilterChainFactory filterChainFactory = new FilterChainFactory();
            filterChainFactory.filterHashMap = new HashMap();
            INSTANCE = filterChainFactory;
        }
    }
}
