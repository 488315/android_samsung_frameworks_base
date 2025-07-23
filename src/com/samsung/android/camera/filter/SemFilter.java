package com.samsung.android.camera.filter;

/* loaded from: classes6.dex */
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

    protected SemFilter(String str, String str2, String str3, String str4, int i, int i2) {
        this.mPackageName = str;
        this.mFilterName = str2;
        this.mTitle = str3;
        this.mVendor = str4;
        this.mCategory = i;
        this.mVersion = i2;
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
