package com.samsung.vekit.Common.Object;

/* loaded from: classes6.dex */
public class FilterInfo {
    private Filter filter;
    private FilterOption filterOption;
    private float intensity;

    public FilterInfo() {
        this.intensity = 0.0f;
        this.filterOption = new FilterOption();
    }

    public FilterInfo(Filter filter, float f) {
        this.filter = filter;
        this.intensity = f;
        this.filterOption = new FilterOption();
    }

    public FilterInfo(Filter filter, float f, FilterOption filterOption) {
        this.filter = filter;
        this.intensity = f;
        this.filterOption = filterOption;
    }

    public Filter getFilter() {
        return this.filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public float getIntensity() {
        return this.intensity;
    }

    public void setIntensity(float f) {
        this.intensity = f;
    }

    public FilterOption getFilterOption() {
        return this.filterOption;
    }

    public void setFilterOption(FilterOption filterOption) {
        this.filterOption = filterOption;
    }
}
