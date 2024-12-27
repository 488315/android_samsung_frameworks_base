package com.samsung.android.camera.filter;

public abstract class SemFilter {
    public static final int FILTER_CATEGORY_REAR = 0;
    public static final int FILTER_CATEGORY_REAR_AND_SELFIE = 2;
    public static final int FILTER_CATEGORY_SELFIE = 1;
    public static final int TYPE_EFFECT_COLOR_EFFECT = -1;
    private int mCategory;
    private String mFilterName;
    private String mPackageName;
    private String mTitle;
    private String mVendor;
    private int mVersion;

    protected SemFilter(
            String packageName,
            String filterName,
            String title,
            String vendor2,
            int category,
            int version) {
        this.mFilterName = "";
        this.mPackageName = "";
        this.mTitle = "";
        this.mVendor = "";
        this.mCategory = 0;
        this.mVersion = 1;
        this.mPackageName = packageName;
        this.mFilterName = filterName;
        this.mTitle = title;
        this.mVendor = vendor2;
        this.mCategory = category;
        this.mVersion = version;
    }

    public String getFilterName() {
        return this.mFilterName;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getVendor() {
        return this.mVendor;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public int getCategory() {
        return this.mCategory;
    }
}
