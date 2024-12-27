package com.samsung.android.server.packagefeature;

import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.core.PackageFeatureManagerService;
import com.samsung.android.server.packagefeature.core.PackageFeatureManagerService$$ExternalSyntheticLambda0;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'DISPLAY_COMPAT' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
public final class PackageFeature {
    public static final /* synthetic */ PackageFeature[] $VALUES;
    public static final PackageFeature BROADCAST_RECEIVER_ALLOW_LIST;
    public static final PackageFeature DEX_LAUNCH_B;
    public static final PackageFeature DISPLAY_COMPAT;
    public static final PackageFeature FLEX_MODE_APP;
    public static final PackageFeature FLEX_PANEL_DEFAULT;
    public static final PackageFeature FULL_SCREEN;
    public static final PackageFeature HIGH_REFRESH_RATE;
    public static final PackageFeature IGNORE_APP_ROTATION;
    public static final PackageFeature IGNORE_APP_ROTATION_DISABLED;
    public static final PackageFeature LOW_REFRESH_RATE;
    public static final PackageFeature MIN_ASPECT_RATIO;
    public static final PackageFeature NAVIGATION_LOW_REFRESH_RATE;
    public static final PackageFeature TABLET_APP_ROTATION_COMPAT;
    public static final PackageFeature appcategory;
    public final PackageFeatureDebugCommand mDebugCommand;
    public final boolean mEnabled;
    public final PackageFeatureGroup mGroup;
    public final PackageFeatureMigrationListener mMigrationListener;
    public final String mName;

    static {
        PackageFeatureGroup packageFeatureGroup = PackageFeatureGroup.FOLDABLE_PACKAGE_FEATURE;
        PackageFeature packageFeature =
                new PackageFeature(
                        "DISPLAY_COMPAT",
                        0,
                        true,
                        packageFeatureGroup,
                        "displayCompat",
                        new DisplayCompatDebugCommand("-setForceDisplayCompatMode"));
        DISPLAY_COMPAT = packageFeature;
        PackageFeature packageFeature2 =
                new PackageFeature(
                        "MIN_ASPECT_RATIO",
                        1,
                        CoreRune.MT_APP_COMPAT_MIN_ASPECT_RATIO_LIST,
                        packageFeatureGroup,
                        "fixedAspectRatio",
                        new FoldMinAspectRatioDebugCommand("-setFixedAspectRatio", "-ratio"));
        MIN_ASPECT_RATIO = packageFeature2;
        PackageFeature packageFeature3 =
                new PackageFeature("FULL_SCREEN", 2, true, packageFeatureGroup, "fullScreen", null);
        FULL_SCREEN = packageFeature3;
        boolean z = CoreRune.MT_APP_COMPAT_LANDSCAPE_VIEW_FOR_PORTRAIT_APPS;
        PackageFeature packageFeature4 =
                new PackageFeature(
                        "IGNORE_APP_ROTATION",
                        3,
                        z,
                        packageFeatureGroup,
                        "ignoreAppRotation",
                        new PackageFeatureDebugCommand("-setIgnoreAppRotation"));
        IGNORE_APP_ROTATION = packageFeature4;
        PackageFeature packageFeature5 =
                new PackageFeature(
                        "IGNORE_APP_ROTATION_DISABLED",
                        4,
                        z,
                        packageFeatureGroup,
                        "ignoreAppRotationDisabled",
                        new PackageFeatureDebugCommand("-setIgnoreAppRotationDisabled"));
        IGNORE_APP_ROTATION_DISABLED = packageFeature5;
        PackageFeature packageFeature6 =
                new PackageFeature(
                        "TABLET_APP_ROTATION_COMPAT",
                        5,
                        z,
                        packageFeatureGroup,
                        "tabletAppRotationCompat",
                        new PackageFeatureDebugCommand("-setTabletAppRotationCompat"));
        TABLET_APP_ROTATION_COMPAT = packageFeature6;
        PackageFeature packageFeature7 =
                new PackageFeature(
                        "FLEX_MODE_APP",
                        6,
                        CoreRune.FW_FLEX_MODE_APP_LIST,
                        packageFeatureGroup,
                        "flexModeApp",
                        new PackageFeatureDebugCommand("-setFlexModeApp"));
        FLEX_MODE_APP = packageFeature7;
        PackageFeature packageFeature8 =
                new PackageFeature(
                        "FLEX_PANEL_DEFAULT",
                        7,
                        CoreRune.FW_FLEX_PANEL_DEFAULT_LIST,
                        packageFeatureGroup,
                        "flexPanelDefault",
                        new PackageFeatureDebugCommand("-setFlexPanelDefault"));
        FLEX_PANEL_DEFAULT = packageFeature8;
        PackageFeature packageFeature9 =
                new PackageFeature(
                        "SPLIT_ACTIVITY", 8, false, packageFeatureGroup, "splitActivity", null);
        PackageFeature packageFeature10 =
                new PackageFeature(
                        "DEX_LAUNCH_B", 9, true, packageFeatureGroup, "dexLaunchBlock", null);
        DEX_LAUNCH_B = packageFeature10;
        PackageFeature packageFeature11 =
                new PackageFeature(
                        "DEX_LAUNCH_RESTART",
                        10,
                        true,
                        packageFeatureGroup,
                        "dexLaunchRestart",
                        new PackageFeatureDebugCommand("-setDexRestart"));
        PackageFeature packageFeature12 =
                new PackageFeature(
                        "COVER_LAUNCHER_WIDGET",
                        11,
                        false,
                        packageFeatureGroup,
                        "coverLauncherWidget",
                        new CoverLauncherWidgetDebugCommand("-setCoverLauncherWidgetPackage"));
        PackageFeature packageFeature13 =
                new PackageFeature(
                        "COVER_LAUNCHER_WIDGET_CHN",
                        12,
                        CoreRune.FW_LARGE_FLIP_LAUNCHER_WIDGET_POLICY_CHN,
                        packageFeatureGroup,
                        "coverLauncherWidgetChn",
                        null);
        PackageFeature packageFeature14 =
                new PackageFeature(
                        "REMOTE_CONTROL_FEATURES",
                        13,
                        z,
                        packageFeatureGroup,
                        "remoteControlFeatures",
                        null);
        boolean z2 = CoreRune.FW_VRR_LOW_REFRESH_RATE_LIST;
        PackageFeatureGroup packageFeatureGroup2 = PackageFeatureGroup.REFRESH_RATE_PACKAGE_FEATURE;
        PackageFeature packageFeature15 =
                new PackageFeature(
                        "LOW_REFRESH_RATE",
                        14,
                        z2,
                        packageFeatureGroup2,
                        "lowRefreshRate",
                        new PackageFeatureDebugCommand("-setLowRefreshRate"));
        LOW_REFRESH_RATE = packageFeature15;
        PackageFeature packageFeature16 =
                new PackageFeature(
                        "HIGH_REFRESH_RATE",
                        15,
                        CoreRune.FW_VRR_HIGH_REFRESH_RATE_BLOCK_LIST,
                        packageFeatureGroup2,
                        "highRefreshRate",
                        new PackageFeatureDebugCommand("-setHighRefreshRate"));
        HIGH_REFRESH_RATE = packageFeature16;
        PackageFeature packageFeature17 =
                new PackageFeature(
                        "NAVIGATION_LOW_REFRESH_RATE",
                        16,
                        CoreRune.FW_VRR_NAVIGATION_LOW_REFRESH_RATE,
                        packageFeatureGroup2,
                        "naviAppLowRefreshRate",
                        new PackageFeatureDebugCommand("-setNaviAppLowRefreshRate"));
        NAVIGATION_LOW_REFRESH_RATE = packageFeature17;
        PackageFeature packageFeature18 =
                new PackageFeature(
                        "BROADCAST_RECEIVER_ALLOW_LIST",
                        17,
                        true,
                        PackageFeatureGroup.BROADCAST_RECEIVER_FEATURE,
                        "BroadcastReceiverAllowList",
                        null);
        BROADCAST_RECEIVER_ALLOW_LIST = packageFeature18;
        PackageFeature packageFeature19 =
                new PackageFeature(
                        "appcategory",
                        18,
                        true,
                        PackageFeatureGroup.APP_CATEGORY_FEATURE,
                        "appcategory",
                        null);
        appcategory = packageFeature19;
        $VALUES =
                new PackageFeature[] {
                    packageFeature,
                    packageFeature2,
                    packageFeature3,
                    packageFeature4,
                    packageFeature5,
                    packageFeature6,
                    packageFeature7,
                    packageFeature8,
                    packageFeature9,
                    packageFeature10,
                    packageFeature11,
                    packageFeature12,
                    packageFeature13,
                    packageFeature14,
                    packageFeature15,
                    packageFeature16,
                    packageFeature17,
                    packageFeature18,
                    packageFeature19,
                    new PackageFeature(
                            "TEST_PACKAGE_FEATURE",
                            19,
                            false,
                            PackageFeatureGroup.TEST_PACKAGE_FEATURE_GROUP,
                            "testPackageFeature",
                            null)
                };
    }

    public PackageFeature(
            String str,
            int i,
            boolean z,
            PackageFeatureGroup packageFeatureGroup,
            String str2,
            PackageFeatureDebugCommand packageFeatureDebugCommand) {
        this.mEnabled = z;
        this.mGroup = packageFeatureGroup;
        this.mName = str2;
        this.mDebugCommand = z ? packageFeatureDebugCommand : null;
    }

    public static PackageFeature valueOf(String str) {
        return (PackageFeature) Enum.valueOf(PackageFeature.class, str);
    }

    public static PackageFeature[] values() {
        return (PackageFeature[]) $VALUES.clone();
    }

    public final void registerCallback(PackageFeatureCallback packageFeatureCallback) {
        PackageFeatureManagerService packageFeatureManagerService =
                PackageFeatureManagerService.LazyHolder.sInstance;
        synchronized (packageFeatureManagerService) {
            try {
                Map map = packageFeatureManagerService.mTmpPackageFeatureCallbacks;
                if (map == null) {
                    packageFeatureManagerService.mPackageFeatureController.registerCallback(
                            this, packageFeatureCallback);
                } else {
                    ((List)
                                    ((HashMap) map)
                                            .computeIfAbsent(
                                                    this,
                                                    new PackageFeatureManagerService$$ExternalSyntheticLambda0()))
                            .add(packageFeatureCallback);
                }
            } finally {
            }
        }
    }
}
