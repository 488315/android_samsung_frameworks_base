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

    public FilterInfo(Filter filter, float intensity) {
        this.filter = filter;
        this.intensity = intensity;
        this.filterOption = new FilterOption();
    }

    public FilterInfo(Filter filter, float intensity, FilterOption filterOption) {
        this.filter = filter;
        this.intensity = intensity;
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

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public FilterOption getFilterOption() {
        return this.filterOption;
    }

    public void setFilterOption(FilterOption filterOption) {
        this.filterOption = filterOption;
    }
}
