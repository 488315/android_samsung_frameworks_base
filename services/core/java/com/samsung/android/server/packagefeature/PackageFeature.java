package com.samsung.android.server.packagefeature;

import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'DISPLAY_COMPAT' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:372)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:337)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:322)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInvoke(EnumVisitor.java:293)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:266)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes2.dex */
public final class PackageFeature {
    public static final /* synthetic */ PackageFeature[] $VALUES;
    public static final PackageFeature ALLOW_MULTI_WINDOW;
    public static final PackageFeature ALLOW_NO_WAIT_ROTATION_FOR_4_1;
    public static final PackageFeature BROADCAST_RECEIVER_ALLOW_LIST;
    public static final PackageFeature COVER_LAUNCHER_WIDGET;
    public static final PackageFeature COVER_LAUNCHER_WIDGET_CHN;
    public static final PackageFeature DEX_LAUNCH_B;
    public static final PackageFeature DEX_LAUNCH_RESTART;
    public static final PackageFeature DISPLAY_COMPAT;
    public static final PackageFeature FIXED_ASPECT_RATIO;
    public static final PackageFeature FLEX_MODE_APP;
    public static final PackageFeature FLEX_PANEL_DEFAULT;
    public static final PackageFeature FULL_SCREEN;
    public static final PackageFeature HIGH_REFRESH_RATE;
    public static final PackageFeature IGNORE_APP_ROTATION;
    public static final PackageFeature IGNORE_APP_ROTATION_DISABLED;
    public static final PackageFeature LOW_REFRESH_RATE;
    public static final PackageFeature NAVIGATION_LOW_REFRESH_RATE;
    public static final PackageFeature REMOTE_CONTROL_FEATURES;
    public static final PackageFeature SPLIT_ACTIVITY;
    public static final PackageFeature TABLET_APP_ROTATION_COMPAT;
    public static final PackageFeature TABLET_MIN_ASPECT_RATIO;
    public static final PackageFeature TEST_PACKAGE_FEATURE;
    private PackageFeatureController mController;
    public final PackageFeatureDebugCommand mDebugCommand;
    public boolean mEnabled;
    public final PackageFeatureGroup mGroup;
    public final String mName;
    private List mTmpCallbacks;

    public static /* synthetic */ PackageFeature[] $values() {
        return new PackageFeature[]{DISPLAY_COMPAT, FIXED_ASPECT_RATIO, TABLET_MIN_ASPECT_RATIO, TABLET_APP_ROTATION_COMPAT, FULL_SCREEN, IGNORE_APP_ROTATION, IGNORE_APP_ROTATION_DISABLED, FLEX_MODE_APP, FLEX_PANEL_DEFAULT, ALLOW_MULTI_WINDOW, SPLIT_ACTIVITY, ALLOW_NO_WAIT_ROTATION_FOR_4_1, DEX_LAUNCH_B, DEX_LAUNCH_RESTART, COVER_LAUNCHER_WIDGET, COVER_LAUNCHER_WIDGET_CHN, REMOTE_CONTROL_FEATURES, LOW_REFRESH_RATE, HIGH_REFRESH_RATE, NAVIGATION_LOW_REFRESH_RATE, BROADCAST_RECEIVER_ALLOW_LIST, TEST_PACKAGE_FEATURE};
    }

    public static PackageFeature valueOf(String str) {
        return (PackageFeature) Enum.valueOf(PackageFeature.class, str);
    }

    public static PackageFeature[] values() {
        return (PackageFeature[]) $VALUES.clone();
    }

    static {
        boolean z = CoreRune.FW_DISPLAY_COMPAT_POLICIES;
        PackageFeatureGroup packageFeatureGroup = PackageFeatureGroup.FOLDABLE_PACKAGE_FEATURE;
        DISPLAY_COMPAT = new PackageFeature("DISPLAY_COMPAT", 0, true, packageFeatureGroup, "displayCompat", new PackageFeatureDebugCommand() { // from class: com.samsung.android.server.packagefeature.DisplayCompatDebugCommand
            {
                String[] strArr = {"-setForceDisplayCompatMode"};
            }

            @Override // com.samsung.android.server.packagefeature.PackageFeatureDebugCommand
            public boolean assertValidOptions(PrintWriter printWriter, String[] strArr, String str) {
                String str2;
                if (strArr.length != 2 || strArr[0] == null || (str2 = strArr[1]) == null) {
                    printOptions(printWriter, str, "blocklist|allowlist");
                    return false;
                }
                if ("allowlist".equals(str2) || "blocklist".equals(str2)) {
                    return true;
                }
                printOptions(printWriter, str, "blocklist|allowlist");
                return false;
            }

            @Override // com.samsung.android.server.packagefeature.PackageFeatureDebugCommand
            public String adjustExtra(String str, String str2) {
                return "blocklist".equals(str2) ? "b" : "w";
            }
        });
        FIXED_ASPECT_RATIO = new PackageFeature("FIXED_ASPECT_RATIO", 1, false, packageFeatureGroup, "fixedAspectRatio", new MinAspectRatioDebugCommand() { // from class: com.samsung.android.server.packagefeature.FoldMinAspectRatioDebugCommand
            @Override // com.samsung.android.server.packagefeature.PackageFeatureDebugCommand
            public String adjustExtra(String str, String str2) {
                return str2 != null ? str2 : "16:9";
            }
        });
        TABLET_MIN_ASPECT_RATIO = new PackageFeature("TABLET_MIN_ASPECT_RATIO", 2, CoreRune.FW_TABLET_MIN_ASPECT_RATIO_MODE, packageFeatureGroup, "tabletMinAspectRatio", new MinAspectRatioDebugCommand() { // from class: com.samsung.android.server.packagefeature.TabletMinAspectRatioDebugCommand
            @Override // com.samsung.android.server.packagefeature.PackageFeatureDebugCommand
            public String adjustExtra(String str, String str2) {
                return str2 != null ? str2 : "19.5:9";
            }
        });
        TABLET_APP_ROTATION_COMPAT = new PackageFeature("TABLET_APP_ROTATION_COMPAT", 3, CoreRune.FW_TABLET_ROTATION_COMPAT_LIST, packageFeatureGroup, "tabletAppRotationCompat", new PackageFeatureDebugCommand("-setTabletAppRotationCompat"));
        FULL_SCREEN = new PackageFeature("FULL_SCREEN", 4, true, packageFeatureGroup, "fullScreen");
        IGNORE_APP_ROTATION = new PackageFeature("IGNORE_APP_ROTATION", 5, CoreRune.FW_IGNORE_APP_ROTATION_LIST, packageFeatureGroup, "ignoreAppRotation", new PackageFeatureDebugCommand("-setIgnoreAppRotation"));
        IGNORE_APP_ROTATION_DISABLED = new PackageFeature("IGNORE_APP_ROTATION_DISABLED", 6, CoreRune.FW_IGNORE_APP_ROTATION_DISABLED_LIST, packageFeatureGroup, "ignoreAppRotationDisabled", new PackageFeatureDebugCommand("-setIgnoreAppRotationDisabled"));
        FLEX_MODE_APP = new PackageFeature("FLEX_MODE_APP", 7, false, packageFeatureGroup, "flexModeApp", new PackageFeatureDebugCommand("-setFlexModeApp"));
        FLEX_PANEL_DEFAULT = new PackageFeature("FLEX_PANEL_DEFAULT", 8, false, packageFeatureGroup, "flexPanelDefault", new PackageFeatureDebugCommand("-setFlexPanelDefault"));
        ALLOW_MULTI_WINDOW = new PackageFeature("ALLOW_MULTI_WINDOW", 9, true, packageFeatureGroup, "allowMultiWindow");
        SPLIT_ACTIVITY = new PackageFeature("SPLIT_ACTIVITY", 10, false, packageFeatureGroup, "splitActivity");
        ALLOW_NO_WAIT_ROTATION_FOR_4_1 = new PackageFeature("ALLOW_NO_WAIT_ROTATION_FOR_4_1", 11, false, packageFeatureGroup, "allowNoWaitRotationFor_4_1", new PackageFeatureDebugCommand("-setAllowNoWaitRotation"));
        DEX_LAUNCH_B = new PackageFeature("DEX_LAUNCH_B", 12, true, packageFeatureGroup, "dexLaunchBlock");
        DEX_LAUNCH_RESTART = new PackageFeature("DEX_LAUNCH_RESTART", 13, true, packageFeatureGroup, "dexLaunchRestart", new PackageFeatureDebugCommand("-setDexRestart"));
        COVER_LAUNCHER_WIDGET = new PackageFeature("COVER_LAUNCHER_WIDGET", 14, false, packageFeatureGroup, "coverLauncherWidget", new PackageFeatureDebugCommand() { // from class: com.samsung.android.server.packagefeature.CoverLauncherWidgetDebugCommand
            @Override // com.samsung.android.server.packagefeature.PackageFeatureDebugCommand
            public String adjustExtra(String str, String str2) {
                return str2;
            }

            {
                String[] strArr = {"-setCoverLauncherWidgetPackage"};
            }

            @Override // com.samsung.android.server.packagefeature.PackageFeatureDebugCommand
            public boolean assertValidOptions(PrintWriter printWriter, String[] strArr, String str) {
                if (strArr.length == 2 && strArr[0] != null && strArr[1] != null) {
                    return true;
                }
                printOptions(printWriter, str, "Properties");
                return false;
            }
        });
        COVER_LAUNCHER_WIDGET_CHN = new PackageFeature("COVER_LAUNCHER_WIDGET_CHN", 15, CoreRune.FW_LARGE_FLIP_LAUNCHER_WIDGET_POLICY_CHN, packageFeatureGroup, "coverLauncherWidgetChn");
        REMOTE_CONTROL_FEATURES = new PackageFeature("REMOTE_CONTROL_FEATURES", 16, CoreRune.FW_ORIENTATION_CONTROL_DEFAULT_ENABLED, packageFeatureGroup, "remoteControlFeatures");
        boolean z2 = CoreRune.FW_VRR_LOW_REFRESH_RATE_LIST;
        PackageFeatureGroup packageFeatureGroup2 = PackageFeatureGroup.REFRESH_RATE_PACKAGE_FEATURE;
        LOW_REFRESH_RATE = new PackageFeature("LOW_REFRESH_RATE", 17, z2, packageFeatureGroup2, "lowRefreshRate", new PackageFeatureDebugCommand("-setLowRefreshRate"));
        HIGH_REFRESH_RATE = new PackageFeature("HIGH_REFRESH_RATE", 18, CoreRune.FW_VRR_HIGH_REFRESH_RATE_BLOCK_LIST, packageFeatureGroup2, "highRefreshRate", new PackageFeatureDebugCommand("-setHighRefreshRate"));
        NAVIGATION_LOW_REFRESH_RATE = new PackageFeature("NAVIGATION_LOW_REFRESH_RATE", 19, CoreRune.FW_VRR_NAVIGATION_LOW_REFRESH_RATE, packageFeatureGroup2, "naviAppLowRefreshRate", new PackageFeatureDebugCommand("-setNaviAppLowRefreshRate"));
        BROADCAST_RECEIVER_ALLOW_LIST = new PackageFeature("BROADCAST_RECEIVER_ALLOW_LIST", 20, true, PackageFeatureGroup.BROADCAST_RECEIVER_FEATURE, "BroadcastReceiverAllowList");
        TEST_PACKAGE_FEATURE = new PackageFeature("TEST_PACKAGE_FEATURE", 21, CoreRune.SAFE_DEBUG, PackageFeatureGroup.TEST_PACKAGE_FEATURE_GROUP, "testPackageFeature");
        $VALUES = $values();
    }

    public PackageFeature(String str, int i, boolean z, PackageFeatureGroup packageFeatureGroup, String str2) {
        this(str, i, z, packageFeatureGroup, str2, null);
    }

    public PackageFeature(String str, int i, boolean z, PackageFeatureGroup packageFeatureGroup, String str2, PackageFeatureDebugCommand packageFeatureDebugCommand) {
        this.mTmpCallbacks = new ArrayList();
        this.mEnabled = z;
        this.mGroup = packageFeatureGroup;
        this.mName = str2;
        this.mDebugCommand = packageFeatureDebugCommand;
    }

    public void setPackageFeatureController(PackageFeatureController packageFeatureController) {
        if (packageFeatureController == null) {
            return;
        }
        synchronized (this.mTmpCallbacks) {
            this.mController = packageFeatureController;
            Iterator it = this.mTmpCallbacks.iterator();
            while (it.hasNext()) {
                packageFeatureController.registerCallback(this, (PackageFeatureCallback) it.next());
            }
            this.mTmpCallbacks.clear();
        }
    }

    public void registerCallback(PackageFeatureCallback packageFeatureCallback) {
        synchronized (this.mTmpCallbacks) {
            PackageFeatureController packageFeatureController = this.mController;
            if (packageFeatureController == null) {
                this.mTmpCallbacks.add(packageFeatureCallback);
            } else {
                packageFeatureController.registerCallback(this, packageFeatureCallback);
            }
        }
    }
}
