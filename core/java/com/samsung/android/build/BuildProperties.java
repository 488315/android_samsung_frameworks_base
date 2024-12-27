package com.samsung.android.build;

/* loaded from: classes5.dex */
public class BuildProperties {
    public static final boolean ENG = false;
    public static final String PROJECT_NAME = "e3q";
    public static final boolean SEC_FACTORY_BUILD = false;
    public static final String TARGET_BUILD_VARIANT = "user";
    public static final String TARGET_PRODUCT = "e3qxxx";
    public static final boolean USER = true;
    public static final boolean USERDEBUG = false;

    public static final boolean isProjectName(String s) {
        return PROJECT_NAME.equals(s);
    }

    public static final boolean isTargetBuildVariant(String s) {
        return "user".equals(s);
    }

    public static final boolean isTargetProduct(String s) {
        return TARGET_PRODUCT.equals(s);
    }
}
