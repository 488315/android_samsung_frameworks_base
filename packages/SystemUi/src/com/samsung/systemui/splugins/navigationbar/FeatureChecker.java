package com.samsung.systemui.splugins.navigationbar;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface FeatureChecker {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class DefaultImpls {
        public static boolean isNavigationBarMigrated(FeatureChecker featureChecker) {
            return false;
        }

        public static boolean isSupportSearcle(FeatureChecker featureChecker) {
            return false;
        }
    }

    boolean isDeviceSupportLargeCoverScreen();

    boolean isDeviceSupportTaskbar();

    boolean isFoldableTypeFlip();

    boolean isFoldableTypeFold();

    boolean isNavigationBarMigrated();

    boolean isOpenThemeSupported();

    boolean isSupportSearcle();

    boolean isTablet();
}
