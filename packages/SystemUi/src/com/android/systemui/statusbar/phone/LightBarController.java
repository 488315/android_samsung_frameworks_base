package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;
import android.view.InsetsFlags;
import android.view.ViewDebug;
import com.android.internal.view.AppearanceRegion;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LightBarController implements BatteryController.BatteryStateChangeCallback, Dumpable {
    public static final float NAV_BAR_INVERSION_SCRIM_ALPHA_THRESHOLD;
    public int mAppearance;
    public AppearanceRegion[] mAppearanceRegions = new AppearanceRegion[0];
    public final BatteryController mBatteryController;
    public BiometricUnlockController mBiometricUnlockController;
    public boolean mBouncerVisible;
    public final int mDarkIconColor;
    public boolean mDirectReplying;
    public boolean mForceDarkForScrim;
    public boolean mForceLightForScrim;
    public boolean mGlobalActionsVisible;
    public boolean mHasLightNavigationBar;
    public boolean mIsCustomizingForBackNav;
    public final boolean mIsDefaultDisplay;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final int mLightIconColor;
    public final LightBarController$$ExternalSyntheticLambda2 mModeChangedListener;
    public final NavBarStateManager mNavBarStateManager;
    public boolean mNavbarColorManagedByIme;
    public LightBarTransitionsController mNavigationBarController;
    public int mNavigationBarMode;
    public boolean mNavigationLight;
    public int mNavigationMode;
    public final NavigationModeController mNavigationModeController;
    public final LightBarTransientObserver mObserver;
    public boolean mPanelExpanded;
    public boolean mPanelHasWhiteBg;
    public boolean mQsCustomizing;
    public boolean mQsExpanded;
    public final SamsungLightBarControlHelper mSamsungLightBarControlHelper;
    public final SamsungStatusBarGrayIconHelper mSamsungStatusBarGrayIconHelper;
    public final SysuiDarkIconDispatcher mStatusBarIconController;
    public int mStatusBarMode;
    public final boolean mUseNewLightBarLogic;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Factory {
        public final BatteryController mBatteryController;
        public final DarkIconDispatcher mDarkIconDispatcher;
        public final DisplayTracker mDisplayTracker;
        public final DumpManager mDumpManager;
        public final FeatureFlags mFeatureFlags;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final NavigationModeController mNavModeController;
        public final SamsungLightBarControlHelper mSamsungLightBarControlHelper;
        public final SamsungStatusBarGrayIconHelper mSamsungStatusBarGrayIconHelper;

        public Factory(DarkIconDispatcher darkIconDispatcher, BatteryController batteryController, NavigationModeController navigationModeController, FeatureFlags featureFlags, DumpManager dumpManager, DisplayTracker displayTracker, SamsungLightBarControlHelper samsungLightBarControlHelper, SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper, KeyguardUpdateMonitor keyguardUpdateMonitor) {
            this.mDarkIconDispatcher = darkIconDispatcher;
            this.mBatteryController = batteryController;
            this.mNavModeController = navigationModeController;
            this.mFeatureFlags = featureFlags;
            this.mDumpManager = dumpManager;
            this.mDisplayTracker = displayTracker;
            this.mSamsungLightBarControlHelper = samsungLightBarControlHelper;
            this.mSamsungStatusBarGrayIconHelper = samsungStatusBarGrayIconHelper;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LightBarTransientObserver extends SystemBarObserver {
        public final ArrayList mList;

        public /* synthetic */ LightBarTransientObserver(int i) {
            this();
        }

        public final void notify(Consumer consumer) {
            this.mList.forEach(new LightBarController$$ExternalSyntheticLambda0(consumer, 1));
        }

        private LightBarTransientObserver() {
            this.mList = new ArrayList();
        }
    }

    static {
        NAV_BAR_INVERSION_SCRIM_ALPHA_THRESHOLD = BasicRune.NAVBAR_LIGHTBAR ? 0.4f : 0.1f;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.navigationbar.NavigationModeController$ModeChangedListener, com.android.systemui.statusbar.phone.LightBarController$$ExternalSyntheticLambda2] */
    public LightBarController(Context context, DarkIconDispatcher darkIconDispatcher, BatteryController batteryController, NavigationModeController navigationModeController, FeatureFlags featureFlags, DumpManager dumpManager, DisplayTracker displayTracker, SamsungLightBarControlHelper samsungLightBarControlHelper, SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        boolean z = false;
        z = false;
        final int i = z ? 1 : 0;
        ?? r1 = new NavigationModeController.ModeChangedListener(this) { // from class: com.android.systemui.statusbar.phone.LightBarController$$ExternalSyntheticLambda2
            public final /* synthetic */ LightBarController f$0;

            {
                this.f$0 = this;
            }

            @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
            public final void onNavigationModeChanged(int i2) {
                int i3 = i;
                LightBarController lightBarController = this.f$0;
                switch (i3) {
                    case 0:
                        lightBarController.mNavigationMode = i2;
                        break;
                    default:
                        lightBarController.mNavigationMode = i2;
                        break;
                }
            }
        };
        this.mModeChangedListener = r1;
        this.mPanelExpanded = false;
        this.mPanelHasWhiteBg = false;
        this.mIsDefaultDisplay = false;
        boolean z2 = BasicRune.NAVBAR_LIGHTBAR;
        if (z2) {
            this.mNavBarStateManager = ((NavBarStoreImpl) ((NavBarStore) Dependency.get(NavBarStore.class))).getNavStateManager(context.getDisplayId());
        }
        if (BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY) {
            this.mObserver = new LightBarTransientObserver(z ? 1 : 0);
        }
        this.mSamsungLightBarControlHelper = samsungLightBarControlHelper;
        this.mSamsungStatusBarGrayIconHelper = samsungStatusBarGrayIconHelper;
        final int i2 = 1;
        if (((FeatureFlagsRelease) featureFlags).isEnabled(Flags.NEW_LIGHT_BAR_LOGIC) && !z2) {
            z = true;
        }
        this.mUseNewLightBarLogic = z;
        this.mDarkIconColor = context.getColor(R.color.dark_mode_icon_color_single_tone);
        this.mLightIconColor = context.getColor(R.color.light_mode_icon_color_single_tone);
        this.mStatusBarIconController = (SysuiDarkIconDispatcher) darkIconDispatcher;
        this.mBatteryController = batteryController;
        ((BatteryControllerImpl) batteryController).addCallback(this);
        if (BasicRune.NAVBAR_AOSP_BUG_FIX) {
            this.mNavigationModeController = navigationModeController;
            this.mNavigationBarMode = navigationModeController.addListener(r1);
        } else {
            this.mNavigationMode = navigationModeController.addListener(new NavigationModeController.ModeChangedListener(this) { // from class: com.android.systemui.statusbar.phone.LightBarController$$ExternalSyntheticLambda2
                public final /* synthetic */ LightBarController f$0;

                {
                    this.f$0 = this;
                }

                @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
                public final void onNavigationModeChanged(int i22) {
                    int i3 = i2;
                    LightBarController lightBarController = this.f$0;
                    switch (i3) {
                        case 0:
                            lightBarController.mNavigationMode = i22;
                            break;
                        default:
                            lightBarController.mNavigationMode = i22;
                            break;
                    }
                }
            });
        }
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        int displayId = context.getDisplayId();
        displayTracker.getClass();
        if (displayId == 0) {
            dumpManager.getClass();
            DumpManager.registerDumpable$default(dumpManager, "LightBarController", this);
            this.mIsDefaultDisplay = true;
        }
    }

    public static boolean isLight(int i, int i2, int i3) {
        return (i2 == 0 || i2 == 6) && ((i & i3) != 0);
    }

    public final boolean animateChange() {
        int i;
        BiometricUnlockController biometricUnlockController = this.mBiometricUnlockController;
        return (biometricUnlockController == null || (i = biometricUnlockController.mMode) == 2 || i == 1) ? false : true;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(final PrintWriter printWriter, final String[] strArr) {
        printWriter.println("LightBarController: ");
        printWriter.print(" mAppearance=");
        printWriter.println(ViewDebug.flagsToString(InsetsFlags.class, "appearance", this.mAppearance));
        int length = this.mAppearanceRegions.length;
        for (int i = 0; i < length; i++) {
            boolean isLight = isLight(this.mAppearanceRegions[i].getAppearance(), this.mStatusBarMode, 8);
            printWriter.print(" stack #");
            printWriter.print(i);
            printWriter.print(": ");
            printWriter.print(this.mAppearanceRegions[i].toString());
            printWriter.print(" isLight=");
            printWriter.println(isLight);
        }
        printWriter.print(" mNavigationLight=");
        printWriter.println(this.mNavigationLight);
        printWriter.print(" mHasLightNavigationBar=");
        printWriter.println(this.mHasLightNavigationBar);
        printWriter.println();
        printWriter.print(" mStatusBarMode=");
        printWriter.print(this.mStatusBarMode);
        printWriter.print(" mNavigationBarMode=");
        printWriter.println(this.mNavigationBarMode);
        printWriter.println();
        printWriter.print(" mForceDarkForScrim=");
        printWriter.println(this.mForceDarkForScrim);
        printWriter.print(" mForceLightForScrim=");
        printWriter.println(this.mForceLightForScrim);
        printWriter.println();
        printWriter.print(" mQsCustomizing=");
        printWriter.println(this.mQsCustomizing);
        printWriter.print(" mQsExpanded=");
        printWriter.println(this.mQsExpanded);
        printWriter.print(" mBouncerVisible=");
        printWriter.println(this.mBouncerVisible);
        printWriter.print(" mGlobalActionsVisible=");
        printWriter.println(this.mGlobalActionsVisible);
        printWriter.print(" mDirectReplying=");
        printWriter.println(this.mDirectReplying);
        printWriter.print(" mNavbarColorManagedByIme=");
        printWriter.println(this.mNavbarColorManagedByIme);
        printWriter.println();
        printWriter.println(" Recent Calculation Logs:");
        printWriter.print("   ");
        printWriter.println((String) null);
        printWriter.print("   ");
        printWriter.println((String) null);
        printWriter.println();
        LightBarTransitionsController lightBarTransitionsController = ((DarkIconDispatcherImpl) this.mStatusBarIconController).mTransitionsController;
        if (lightBarTransitionsController != null) {
            printWriter.println(" StatusBarTransitionsController:");
            lightBarTransitionsController.dump(printWriter, strArr);
            printWriter.println();
        }
        if (BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY) {
            printWriter.println(" NavigationBarTransitionsController:");
            this.mObserver.notify(new Consumer() { // from class: com.android.systemui.statusbar.phone.LightBarController$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PrintWriter printWriter2 = printWriter;
                    String[] strArr2 = strArr;
                    LightBarTransitionsController lightBarTransitionsController2 = (LightBarTransitionsController) obj;
                    if (lightBarTransitionsController2 != null) {
                        lightBarTransitionsController2.dump(printWriter2, strArr2);
                        printWriter2.println();
                    }
                }
            });
        } else if (this.mNavigationBarController != null) {
            printWriter.println(" NavigationBarTransitionsController:");
            this.mNavigationBarController.dump(printWriter, strArr);
            printWriter.println();
        }
        SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper = this.mSamsungStatusBarGrayIconHelper;
        if (samsungStatusBarGrayIconHelper != null) {
            samsungStatusBarGrayIconHelper.getClass();
            printWriter.println("SamsungStatusBarGrayIconHelper:");
            ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("  isGrayIcon=", samsungStatusBarGrayIconHelper.isGrayIcon, printWriter);
            int i2 = samsungStatusBarGrayIconHelper.homeIndicatorIconColor;
            printWriter.println("  homeIndicatorIconColor=" + i2 + "(0x" + Integer.toHexString(i2) + ")");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0086  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onNavigationBarAppearanceChanged(int i, int i2, String str, boolean z, boolean z2) {
        boolean z3;
        boolean z4;
        NavigationBarModel navigationBarModel;
        NavigationBarModel navigationBarModel2;
        if (((this.mAppearance ^ i) & 16) != 0 || z) {
            boolean z5 = BasicRune.NAVBAR_LIGHTBAR;
            NavBarStateManager navBarStateManager = this.mNavBarStateManager;
            if (z5) {
                if (navBarStateManager.isOpaqueNavigationBar() && !navBarStateManager.states.darkMode) {
                    z3 = true;
                    z4 = this.mNavigationLight;
                    boolean z6 = !isLight(i, i2, 16) || (z5 && z3);
                    this.mHasLightNavigationBar = z6;
                    if (this.mUseNewLightBarLogic) {
                        this.mNavigationLight = z6 && ((this.mDirectReplying && this.mNavbarColorManagedByIme) || !this.mForceDarkForScrim) && !this.mQsCustomizing;
                        if (z5 && this.mPanelExpanded && !this.mDirectReplying) {
                            this.mNavigationLight = this.mPanelHasWhiteBg || z3;
                        }
                    } else {
                        boolean z7 = this.mDirectReplying && this.mNavbarColorManagedByIme;
                        this.mNavigationLight = ((z6 && !(this.mForceDarkForScrim && !z7)) || (this.mForceLightForScrim && !z7)) && !(((this.mQsCustomizing || this.mQsExpanded) && !this.mBouncerVisible) || this.mGlobalActionsVisible);
                    }
                    boolean z8 = !z5 && navBarStateManager.states.regionSamplingEnabled;
                    if (this.mNavigationLight != z4 && z5 && !z8) {
                        updateNavigation();
                        String str2 = !this.mNavigationLight ? "BLACK button" : "WHITE button";
                        boolean isLight = isLight(i, i2, 16);
                        boolean z9 = this.mDirectReplying;
                        boolean z10 = this.mNavbarColorManagedByIme;
                        boolean z11 = this.mForceDarkForScrim;
                        boolean z12 = this.mQsCustomizing;
                        boolean z13 = this.mPanelHasWhiteBg;
                        SamsungLightBarControlHelper samsungLightBarControlHelper = this.mSamsungLightBarControlHelper;
                        samsungLightBarControlHelper.getClass();
                        navigationBarModel = new NavigationBarModel(str2, z3, isLight, z9, z10, z11, z12, z13, str);
                        navigationBarModel2 = samsungLightBarControlHelper.navigationBarModel;
                        if (navigationBarModel2 != null || !Intrinsics.areEqual(navigationBarModel2, navigationBarModel)) {
                            samsungLightBarControlHelper.navigationBarModel = navigationBarModel;
                            Log.d("SamsungLightBarControlHelper", "updateNavigationBar " + navigationBarModel);
                        }
                    }
                }
            }
            z3 = false;
            z4 = this.mNavigationLight;
            if (isLight(i, i2, 16)) {
            }
            this.mHasLightNavigationBar = z6;
            if (this.mUseNewLightBarLogic) {
            }
            if (z5) {
            }
            if (this.mNavigationLight != z4) {
                updateNavigation();
                String str22 = !this.mNavigationLight ? "BLACK button" : "WHITE button";
                boolean isLight2 = isLight(i, i2, 16);
                boolean z92 = this.mDirectReplying;
                boolean z102 = this.mNavbarColorManagedByIme;
                boolean z112 = this.mForceDarkForScrim;
                boolean z122 = this.mQsCustomizing;
                boolean z132 = this.mPanelHasWhiteBg;
                SamsungLightBarControlHelper samsungLightBarControlHelper2 = this.mSamsungLightBarControlHelper;
                samsungLightBarControlHelper2.getClass();
                navigationBarModel = new NavigationBarModel(str22, z3, isLight2, z92, z102, z112, z122, z132, str);
                navigationBarModel2 = samsungLightBarControlHelper2.navigationBarModel;
                if (navigationBarModel2 != null) {
                }
                samsungLightBarControlHelper2.navigationBarModel = navigationBarModel;
                Log.d("SamsungLightBarControlHelper", "updateNavigationBar " + navigationBarModel);
            }
        }
        this.mAppearance = i;
        this.mNavigationBarMode = i2;
        this.mNavbarColorManagedByIme = z2;
    }

    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
    public final void onPowerSaveChanged(boolean z) {
        reevaluate();
    }

    public final void onStatusBarAppearanceChanged(AppearanceRegion[] appearanceRegionArr, boolean z, int i, boolean z2, String str) {
        int length = appearanceRegionArr.length;
        boolean z3 = this.mAppearanceRegions.length != length;
        for (int i2 = 0; i2 < length && !z3; i2++) {
            z3 |= !appearanceRegionArr[i2].equals(this.mAppearanceRegions[i2]);
        }
        if (z3 || z || this.mIsCustomizingForBackNav || this.mSamsungStatusBarGrayIconHelper.grayAppearanceChanged) {
            this.mAppearanceRegions = appearanceRegionArr;
            if (z3) {
                this.mSamsungLightBarControlHelper.getClass();
                int i3 = SamsungLightBarControlHelperKt.$r8$clinit;
                StringBuilder sb = new StringBuilder("onStatusBarAppearanceChanged() -");
                sb.append("  sbModeChanged:" + z);
                sb.append(", statusBarMode:" + i);
                sb.append(", barState:".concat(BarTransitions.modeToString(i)));
                sb.append(", isKeyguardShowing:" + ((KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.get(KeyguardStateController.class))).mShowing);
                sb.append(", navbarColorManagedByIme:" + z2);
                sb.append(", stackAppearanceChanged:" + z3);
                sb.append(", (");
                for (AppearanceRegion appearanceRegion : appearanceRegionArr) {
                    if (appearanceRegion != null) {
                        sb.append(appearanceRegion + ", ");
                    }
                }
                sb.append(")");
                if (!StringsKt__StringsKt.contains(str, "com.att", false)) {
                    sb.append(", packageName:".concat(str));
                }
                Log.d("SamsungLightBarControlHelper", sb.toString());
            }
            this.mStatusBarMode = i;
            ((Handler) Dependency.get(Dependency.MAIN_HANDLER)).post(new LightBarController$$ExternalSyntheticLambda1(this, str));
            this.mIsCustomizingForBackNav = false;
        }
        this.mNavbarColorManagedByIme = z2;
    }

    public final void reevaluate() {
        if (this.mIsDefaultDisplay) {
            onStatusBarAppearanceChanged(this.mAppearanceRegions, true, this.mStatusBarMode, this.mNavbarColorManagedByIme, "reevaluate:" + Debug.getCallers(1));
        }
        onNavigationBarAppearanceChanged(this.mAppearance, this.mNavigationBarMode, "reevaluate:" + Debug.getCallers(1), true, this.mNavbarColorManagedByIme);
    }

    public final void updateNavigation() {
        if (BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY) {
            this.mObserver.notify(new LightBarController$$ExternalSyntheticLambda0(this, 0));
            return;
        }
        LightBarTransitionsController lightBarTransitionsController = this.mNavigationBarController;
        if (lightBarTransitionsController != null) {
            if (!BasicRune.NAVBAR_ENABLED) {
                int i = this.mNavigationMode;
                lightBarTransitionsController.getClass();
                if (!(!QuickStepContract.isGesturalMode(i))) {
                    return;
                }
            }
            this.mNavigationBarController.setIconsDark(this.mNavigationLight, animateChange());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0084  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateStatus(AppearanceRegion[] appearanceRegionArr, String str) {
        boolean z;
        PhoneStatusBarViewController$onViewAttached$3 phoneStatusBarViewController$onViewAttached$3;
        boolean z2;
        int length = appearanceRegionArr.length;
        ArrayList arrayList = new ArrayList();
        for (AppearanceRegion appearanceRegion : appearanceRegionArr) {
            if (isLight(appearanceRegion.getAppearance(), this.mStatusBarMode, 8)) {
                arrayList.add(appearanceRegion.getBounds());
            }
        }
        SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper = this.mSamsungStatusBarGrayIconHelper;
        if (samsungStatusBarGrayIconHelper.isGrayAppearance) {
            int i = samsungStatusBarGrayIconHelper.homeIndicatorIconColor;
            if (((i == 0 || i == 1) ? false : true) && ((StatusBarStateController) Dependency.get(StatusBarStateController.class)).getState() == 0) {
                z = true;
                samsungStatusBarGrayIconHelper.isGrayIcon = z;
                phoneStatusBarViewController$onViewAttached$3 = samsungStatusBarGrayIconHelper.grayIconChangedCallback;
                if (phoneStatusBarViewController$onViewAttached$3 != null) {
                    ((BatteryMeterView) ((PhoneStatusBarView) phoneStatusBarViewController$onViewAttached$3.this$0.mView).findViewById(R.id.battery)).mIsGrayColor = z;
                }
                z2 = samsungStatusBarGrayIconHelper.isGrayIcon;
                SysuiDarkIconDispatcher sysuiDarkIconDispatcher = this.mStatusBarIconController;
                if (!z2) {
                    LightBarTransitionsController lightBarTransitionsController = ((DarkIconDispatcherImpl) sysuiDarkIconDispatcher).mTransitionsController;
                    lightBarTransitionsController.iconColorChanged = !lightBarTransitionsController.needGrayIcon;
                    lightBarTransitionsController.needGrayIcon = true;
                    lightBarTransitionsController.setIconsDark(true, animateChange());
                    this.mSamsungLightBarControlHelper.updateStatusBarModel("GRAY icon", length, arrayList, this.mStatusBarMode, str);
                    return;
                }
                if (arrayList.isEmpty()) {
                    if (length == 0) {
                        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
                        if (keyguardUpdateMonitor.mIsDreaming || keyguardUpdateMonitor.isKeyguardVisible()) {
                            return;
                        }
                    }
                    LightBarTransitionsController lightBarTransitionsController2 = ((DarkIconDispatcherImpl) sysuiDarkIconDispatcher).mTransitionsController;
                    lightBarTransitionsController2.iconColorChanged = lightBarTransitionsController2.needGrayIcon;
                    lightBarTransitionsController2.needGrayIcon = false;
                    lightBarTransitionsController2.setIconsDark(false, animateChange());
                    this.mSamsungLightBarControlHelper.updateStatusBarModel("WHITE icon", length, arrayList, this.mStatusBarMode, str);
                    return;
                }
                if (arrayList.size() == length) {
                    DarkIconDispatcherImpl darkIconDispatcherImpl = (DarkIconDispatcherImpl) sysuiDarkIconDispatcher;
                    LightBarTransitionsController lightBarTransitionsController3 = darkIconDispatcherImpl.mTransitionsController;
                    lightBarTransitionsController3.iconColorChanged = lightBarTransitionsController3.needGrayIcon;
                    lightBarTransitionsController3.needGrayIcon = false;
                    darkIconDispatcherImpl.setIconsDarkArea(null);
                    darkIconDispatcherImpl.mTransitionsController.setIconsDark(true, animateChange());
                    this.mSamsungLightBarControlHelper.updateStatusBarModel("BLACK icon", length, arrayList, this.mStatusBarMode, str);
                    return;
                }
                DarkIconDispatcherImpl darkIconDispatcherImpl2 = (DarkIconDispatcherImpl) sysuiDarkIconDispatcher;
                LightBarTransitionsController lightBarTransitionsController4 = darkIconDispatcherImpl2.mTransitionsController;
                lightBarTransitionsController4.iconColorChanged = lightBarTransitionsController4.needGrayIcon;
                lightBarTransitionsController4.needGrayIcon = false;
                darkIconDispatcherImpl2.setIconsDarkArea(arrayList);
                darkIconDispatcherImpl2.mTransitionsController.setIconsDark(true, animateChange());
                this.mSamsungLightBarControlHelper.updateStatusBarModel("BLACK magic", length, arrayList, this.mStatusBarMode, str);
                return;
            }
        }
        z = false;
        samsungStatusBarGrayIconHelper.isGrayIcon = z;
        phoneStatusBarViewController$onViewAttached$3 = samsungStatusBarGrayIconHelper.grayIconChangedCallback;
        if (phoneStatusBarViewController$onViewAttached$3 != null) {
        }
        z2 = samsungStatusBarGrayIconHelper.isGrayIcon;
        SysuiDarkIconDispatcher sysuiDarkIconDispatcher2 = this.mStatusBarIconController;
        if (!z2) {
        }
    }
}
