package com.android.server.am.mars.filter;

import android.content.Context;
import java.util.HashMap;

/* loaded from: classes.dex */
public class FilterChainFactory {
    public HashMap filterHashMap;
    public Context mContext;

    public abstract class FilterChainFactoryHolder {
        public static final FilterChainFactory INSTANCE = new FilterChainFactory();
    }

    public FilterChainFactory() {
        this.filterHashMap = new HashMap();
    }

    public static FilterChainFactory getInstance() {
        return FilterChainFactoryHolder.INSTANCE;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void init(Context context) {
        setContext(context);
        this.filterHashMap.put(1, getForceFilterChain());
        this.filterHashMap.put(2, getAppLockerFilterChain());
        this.filterHashMap.put(3, getAutoRunFilterChain());
        this.filterHashMap.put(4, getFreecessFilterChain());
        this.filterHashMap.put(5, getUDSFilterChain());
        this.filterHashMap.put(6, getSBIKERunFilterChain());
        this.filterHashMap.put(7, getDisablerFilterChain());
        this.filterHashMap.put(8, getManualDisablerFilterChain());
        this.filterHashMap.put(9, getGameFilterChain());
        this.filterHashMap.put(18, getCalmModeFilterChain());
        this.filterHashMap.put(10, getDBUpdateFilterChain());
        this.filterHashMap.put(11, getOLAFFilterChain());
        this.filterHashMap.put(12, getEmergencyKillFilterChain());
        this.filterHashMap.put(13, getLevelFilterChain());
        this.filterHashMap.put(14, getBlockListFilterChain());
        this.filterHashMap.put(15, getEnhancedFilterChain());
        this.filterHashMap.put(16, getMPSMFilterChain());
        this.filterHashMap.put(19, getRecentKillFilterChain());
    }

    public void deInit() {
        this.filterHashMap.clear();
    }

    public FilterChain getFilterChain(int i) {
        return (FilterChain) this.filterHashMap.get(Integer.valueOf(i));
    }

    public final FilterChain getForceFilterChain() {
        FilterFactory filterFactory = FilterFactory.getInstance();
        return new FilterChainBuilder().add(filterFactory.getFilter(9)).add(filterFactory.getFilter(10)).add(filterFactory.getFilter(11)).add(filterFactory.getFilter(12)).add(filterFactory.getFilter(17)).build();
    }

    public final FilterChain getRecentKillFilterChain() {
        FilterFactory filterFactory = FilterFactory.getInstance();
        return new FilterChainBuilder().add(filterFactory.getFilter(9)).add(filterFactory.getFilter(10)).add(filterFactory.getFilter(11)).add(filterFactory.getFilter(12)).add(filterFactory.getFilter(17)).build();
    }

    public final FilterChain getAppLockerFilterChain() {
        FilterFactory filterFactory = FilterFactory.getInstance();
        return new FilterChainBuilder().add(filterFactory.getFilter(3)).add(filterFactory.getFilter(4)).add(filterFactory.getFilter(6)).add(filterFactory.getFilter(7)).add(filterFactory.getFilter(9)).add(filterFactory.getFilter(10)).add(filterFactory.getFilter(11)).add(filterFactory.getFilter(14)).add(filterFactory.getFilter(16)).add(filterFactory.getFilter(17)).build();
    }

    public final FilterChain getAutoRunFilterChain() {
        FilterFactory filterFactory = FilterFactory.getInstance();
        return new FilterChainBuilder().add(filterFactory.getFilter(1)).add(filterFactory.getFilter(4)).add(filterFactory.getFilter(5)).add(filterFactory.getFilter(6)).add(filterFactory.getFilter(7)).add(filterFactory.getFilter(8)).add(filterFactory.getFilter(9)).add(filterFactory.getFilter(10)).add(filterFactory.getFilter(11)).add(filterFactory.getFilter(12)).add(filterFactory.getFilter(13)).add(filterFactory.getFilter(14)).add(filterFactory.getFilter(15)).add(filterFactory.getFilter(16)).add(filterFactory.getFilter(17)).add(filterFactory.getFilter(27)).add(filterFactory.getFilter(28)).build();
    }

    public final FilterChain getFreecessFilterChain() {
        FilterFactory filterFactory = FilterFactory.getInstance();
        return new FilterChainBuilder().add(filterFactory.getFilter(1)).add(filterFactory.getFilter(6)).add(filterFactory.getFilter(7)).add(filterFactory.getFilter(8)).add(filterFactory.getFilter(9)).add(filterFactory.getFilter(11)).add(filterFactory.getFilter(12)).add(filterFactory.getFilter(14)).add(filterFactory.getFilter(17)).add(filterFactory.getFilter(18)).add(filterFactory.getFilter(26)).add(filterFactory.getFilter(27)).add(filterFactory.getFilter(28)).add(filterFactory.getFilter(31)).build();
    }

    public final FilterChain getUDSFilterChain() {
        FilterFactory filterFactory = FilterFactory.getInstance();
        return new FilterChainBuilder().add(filterFactory.getFilter(3)).add(filterFactory.getFilter(4)).add(filterFactory.getFilter(6)).add(filterFactory.getFilter(7)).add(filterFactory.getFilter(9)).add(filterFactory.getFilter(10)).add(filterFactory.getFilter(11)).add(filterFactory.getFilter(14)).add(filterFactory.getFilter(17)).build();
    }

    public final FilterChain getMPSMFilterChain() {
        FilterFactory filterFactory = FilterFactory.getInstance();
        return new FilterChainBuilder().add(filterFactory.getFilter(6)).add(filterFactory.getFilter(9)).add(filterFactory.getFilter(10)).add(filterFactory.getFilter(11)).add(filterFactory.getFilter(13)).build();
    }

    public final FilterChain getSBIKERunFilterChain() {
        FilterFactory filterFactory = FilterFactory.getInstance();
        return new FilterChainBuilder().add(filterFactory.getFilter(3)).add(filterFactory.getFilter(4)).add(filterFactory.getFilter(6)).add(filterFactory.getFilter(7)).add(filterFactory.getFilter(9)).add(filterFactory.getFilter(10)).add(filterFactory.getFilter(11)).add(filterFactory.getFilter(14)).add(filterFactory.getFilter(17)).build();
    }

    public final FilterChain getDisablerFilterChain() {
        FilterFactory filterFactory = FilterFactory.getInstance();
        return new FilterChainBuilder().add(filterFactory.getFilter(4)).add(filterFactory.getFilter(6)).add(filterFactory.getFilter(7)).add(filterFactory.getFilter(9)).add(filterFactory.getFilter(10)).add(filterFactory.getFilter(11)).add(filterFactory.getFilter(14)).add(filterFactory.getFilter(12)).add(filterFactory.getFilter(21)).add(filterFactory.getFilter(22)).add(filterFactory.getFilter(25)).add(filterFactory.getFilter(27)).add(filterFactory.getFilter(30)).build();
    }

    public final FilterChain getManualDisablerFilterChain() {
        FilterFactory filterFactory = FilterFactory.getInstance();
        return new FilterChainBuilder().add(filterFactory.getFilter(4)).add(filterFactory.getFilter(9)).add(filterFactory.getFilter(10)).add(filterFactory.getFilter(11)).add(filterFactory.getFilter(14)).add(filterFactory.getFilter(21)).add(filterFactory.getFilter(22)).add(filterFactory.getFilter(25)).add(filterFactory.getFilter(30)).build();
    }

    public final FilterChain getGameFilterChain() {
        FilterFactory filterFactory = FilterFactory.getInstance();
        return new FilterChainBuilder().add(filterFactory.getFilter(3)).add(filterFactory.getFilter(2)).add(filterFactory.getFilter(4)).add(filterFactory.getFilter(5)).add(filterFactory.getFilter(6)).add(filterFactory.getFilter(7)).add(filterFactory.getFilter(9)).add(filterFactory.getFilter(10)).add(filterFactory.getFilter(11)).add(filterFactory.getFilter(12)).add(filterFactory.getFilter(14)).add(filterFactory.getFilter(17)).add(filterFactory.getFilter(19)).add(filterFactory.getFilter(24)).build();
    }

    public final FilterChain getCalmModeFilterChain() {
        FilterFactory filterFactory = FilterFactory.getInstance();
        return new FilterChainBuilder().add(filterFactory.getFilter(6)).add(filterFactory.getFilter(2)).add(filterFactory.getFilter(7)).add(filterFactory.getFilter(9)).add(filterFactory.getFilter(11)).add(filterFactory.getFilter(12)).add(filterFactory.getFilter(18)).add(filterFactory.getFilter(19)).add(filterFactory.getFilter(24)).add(filterFactory.getFilter(26)).add(filterFactory.getFilter(10)).build();
    }

    public final FilterChain getDBUpdateFilterChain() {
        FilterFactory filterFactory = FilterFactory.getInstance();
        return new FilterChainBuilder().add(filterFactory.getFilter(3)).add(filterFactory.getFilter(4)).add(filterFactory.getFilter(6)).add(filterFactory.getFilter(9)).add(filterFactory.getFilter(10)).add(filterFactory.getFilter(11)).build();
    }

    public final FilterChain getOLAFFilterChain() {
        FilterFactory filterFactory = FilterFactory.getInstance();
        return new FilterChainBuilder().add(filterFactory.getFilter(6)).add(filterFactory.getFilter(7)).add(filterFactory.getFilter(11)).add(filterFactory.getFilter(2)).add(filterFactory.getFilter(31)).build();
    }

    public final FilterChain getEmergencyKillFilterChain() {
        FilterFactory filterFactory = FilterFactory.getInstance();
        return new FilterChainBuilder().add(filterFactory.getFilter(1)).add(filterFactory.getFilter(2)).add(filterFactory.getFilter(7)).add(filterFactory.getFilter(15)).add(filterFactory.getFilter(4)).add(filterFactory.getFilter(6)).add(filterFactory.getFilter(9)).add(filterFactory.getFilter(11)).add(filterFactory.getFilter(12)).add(filterFactory.getFilter(14)).add(filterFactory.getFilter(17)).build();
    }

    public final FilterChain getLevelFilterChain() {
        FilterFactory filterFactory = FilterFactory.getInstance();
        return new FilterChainBuilder().add(filterFactory.getFilter(14)).add(filterFactory.getFilter(5)).build();
    }

    public final FilterChain getBlockListFilterChain() {
        return new FilterChainBuilder().add(FilterFactory.getInstance().getFilter(1)).build();
    }

    public final FilterChain getEnhancedFilterChain() {
        FilterFactory filterFactory = FilterFactory.getInstance();
        return new FilterChainBuilder().add(filterFactory.getFilter(2)).add(filterFactory.getFilter(8)).add(filterFactory.getFilter(6)).add(filterFactory.getFilter(7)).add(filterFactory.getFilter(9)).add(filterFactory.getFilter(11)).add(filterFactory.getFilter(12)).add(filterFactory.getFilter(14)).add(filterFactory.getFilter(15)).add(filterFactory.getFilter(17)).add(filterFactory.getFilter(18)).add(filterFactory.getFilter(23)).add(filterFactory.getFilter(26)).add(filterFactory.getFilter(27)).add(filterFactory.getFilter(10)).add(filterFactory.getFilter(28)).add(filterFactory.getFilter(29)).add(filterFactory.getFilter(31)).build();
    }
}
