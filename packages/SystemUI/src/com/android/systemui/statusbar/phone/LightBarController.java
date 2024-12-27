package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.graphics.Rect;
import android.os.Debug;
import android.view.InsetsFlags;
import android.view.ViewDebug;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.internal.view.AppearanceRegion;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.BasicRune;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryImpl;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.kotlin.JavaAdapter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class LightBarController implements BatteryController.BatteryStateChangeCallback, Dumpable, CoreStartable {
    public static final float NAV_BAR_INVERSION_SCRIM_ALPHA_THRESHOLD;
    public int mAppearance;
    public final BatteryController mBatteryController;
    public BiometricUnlockController mBiometricUnlockController;
    public boolean mBouncerVisible;
    public boolean mDirectReplying;
    public boolean mForceDarkForScrim;
    public boolean mForceLightForScrim;
    public boolean mGlobalActionsVisible;
    public boolean mHasLightNavigationBar;
    public boolean mIsCustomizingForBackNav;
    public final boolean mIsDefaultDisplay;
    public final JavaAdapter mJavaAdapter;
    public final LightBarController$$ExternalSyntheticLambda2 mModeChangedListener;
    public final NavBarStateManager mNavBarStateManager;
    public boolean mNavbarColorManagedByIme;
    public LightBarTransitionsController mNavigationBarController;
    public int mNavigationBarMode;
    public boolean mNavigationLight;
    public int mNavigationMode;
    public final NavigationModeController mNavigationModeController;
    public final LightBarTransientObserver mObserver;
    public boolean mQsCustomizing;
    public boolean mQsExpanded;
    public final SamsungLightBarControlHelper mSamsungLightBarControlHelper;
    public final SamsungStatusBarGrayIconHelper mSamsungStatusBarGrayIconHelper;
    public final SysuiDarkIconDispatcher mStatusBarIconController;
    public int mStatusBarMode;
    public final StatusBarModeRepositoryStore mStatusBarModeRepository;
    public AppearanceRegion[] mAppearanceRegions = new AppearanceRegion[0];
    public BoundsPair mStatusBarBounds = new BoundsPair(new Rect(), new Rect());

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Factory {
        public final BatteryController mBatteryController;
        public final DarkIconDispatcher mDarkIconDispatcher;
        public final DisplayTracker mDisplayTracker;
        public final DumpManager mDumpManager;
        public final JavaAdapter mJavaAdapter;
        public final NavigationModeController mNavModeController;
        public final SamsungLightBarControlHelper mSamsungLightBarControlHelper;
        public final SamsungStatusBarGrayIconHelper mSamsungStatusBarGrayIconHelper;
        public final StatusBarModeRepositoryStore mStatusBarModeRepository;

        public Factory(JavaAdapter javaAdapter, DarkIconDispatcher darkIconDispatcher, BatteryController batteryController, NavigationModeController navigationModeController, StatusBarModeRepositoryStore statusBarModeRepositoryStore, DumpManager dumpManager, DisplayTracker displayTracker, SamsungLightBarControlHelper samsungLightBarControlHelper, SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper) {
            this.mJavaAdapter = javaAdapter;
            this.mDarkIconDispatcher = darkIconDispatcher;
            this.mBatteryController = batteryController;
            this.mNavModeController = navigationModeController;
            this.mStatusBarModeRepository = statusBarModeRepositoryStore;
            this.mDumpManager = dumpManager;
            this.mDisplayTracker = displayTracker;
            this.mSamsungLightBarControlHelper = samsungLightBarControlHelper;
            this.mSamsungStatusBarGrayIconHelper = samsungStatusBarGrayIconHelper;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class LightBarTransientObserver extends SystemBarObserver {
        public final ArrayList mList;

        public /* synthetic */ LightBarTransientObserver(int i) {
            this();
        }

        public final void notify(Consumer consumer) {
            this.mList.forEach(new LightBarController$$ExternalSyntheticLambda0(consumer, 2));
        }

        private LightBarTransientObserver() {
            this.mList = new ArrayList();
        }
    }

    static {
        NAV_BAR_INVERSION_SCRIM_ALPHA_THRESHOLD = BasicRune.NAVBAR_LIGHTBAR ? 0.4f : 0.1f;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.navigationbar.NavigationModeController$ModeChangedListener, com.android.systemui.statusbar.phone.LightBarController$$ExternalSyntheticLambda2] */
    public LightBarController(Context context, JavaAdapter javaAdapter, DarkIconDispatcher darkIconDispatcher, BatteryController batteryController, NavigationModeController navigationModeController, StatusBarModeRepositoryStore statusBarModeRepositoryStore, DumpManager dumpManager, DisplayTracker displayTracker, SamsungLightBarControlHelper samsungLightBarControlHelper, SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper) {
        int i = 0;
        final int i2 = 0;
        ?? r1 = new NavigationModeController.ModeChangedListener(this) { // from class: com.android.systemui.statusbar.phone.LightBarController$$ExternalSyntheticLambda2
            public final /* synthetic */ LightBarController f$0;

            {
                this.f$0 = this;
            }

            @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
            public final void onNavigationModeChanged(int i3) {
                switch (i2) {
                    case 0:
                        this.f$0.mNavigationMode = i3;
                        break;
                    default:
                        this.f$0.mNavigationMode = i3;
                        break;
                }
            }
        };
        this.mModeChangedListener = r1;
        this.mIsDefaultDisplay = false;
        if (BasicRune.NAVBAR_LIGHTBAR) {
            this.mNavBarStateManager = ((NavBarStoreImpl) ((NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class))).getNavStateManager(context.getDisplayId());
            this.mSamsungLightBarControlHelper = samsungLightBarControlHelper;
            samsungLightBarControlHelper.getClass();
        }
        if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
            this.mObserver = new LightBarTransientObserver(i);
        }
        this.mJavaAdapter = javaAdapter;
        this.mStatusBarIconController = (SysuiDarkIconDispatcher) darkIconDispatcher;
        this.mBatteryController = batteryController;
        ((BatteryControllerImpl) batteryController).addCallback(this);
        this.mStatusBarModeRepository = statusBarModeRepositoryStore;
        if (BasicRune.NAVBAR_AOSP_BUG_FIX) {
            this.mNavigationModeController = navigationModeController;
            this.mNavigationBarMode = navigationModeController.addListener(r1);
        } else {
            final int i3 = 1;
            this.mNavigationMode = navigationModeController.addListener(new NavigationModeController.ModeChangedListener(this) { // from class: com.android.systemui.statusbar.phone.LightBarController$$ExternalSyntheticLambda2
                public final /* synthetic */ LightBarController f$0;

                {
                    this.f$0 = this;
                }

                @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
                public final void onNavigationModeChanged(int i32) {
                    switch (i3) {
                        case 0:
                            this.f$0.mNavigationMode = i32;
                            break;
                        default:
                            this.f$0.mNavigationMode = i32;
                            break;
                    }
                }
            });
        }
        this.mSamsungStatusBarGrayIconHelper = samsungStatusBarGrayIconHelper;
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

    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback, com.android.systemui.Dumpable
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
        if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
            printWriter.println(" NavigationBarTransitionsController:");
            this.mObserver.notify(new Consumer() { // from class: com.android.systemui.statusbar.phone.LightBarController$$ExternalSyntheticLambda1
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
            printWriter.println("  isGrayIcon=false");
            int i2 = samsungStatusBarGrayIconHelper.homeIndicatorIconColor;
            printWriter.println("  homeIndicatorIconColor=" + i2 + "(0x" + Integer.toHexString(i2) + ")");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00c1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onNavigationBarAppearanceChanged(int r21, int r22, boolean r23, boolean r24, java.lang.String r25) {
        /*
            Method dump skipped, instructions count: 272
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.LightBarController.onNavigationBarAppearanceChanged(int, int, boolean, boolean, java.lang.String):void");
    }

    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
    public final void onPowerSaveChanged(boolean z) {
        reevaluate();
    }

    public final void onStatusBarAppearanceChanged(AppearanceRegion[] appearanceRegionArr, boolean z, boolean z2, boolean z3) {
        int length = appearanceRegionArr.length;
        boolean z4 = this.mAppearanceRegions.length != length;
        for (int i = 0; i < length && !z4; i++) {
            z4 |= !appearanceRegionArr[i].equals(this.mAppearanceRegions[i]);
        }
        if (z4 || z || z2 || this.mIsCustomizingForBackNav) {
            this.mAppearanceRegions = appearanceRegionArr;
            updateStatus(appearanceRegionArr);
            this.mIsCustomizingForBackNav = false;
            if (z4) {
                int i2 = this.mStatusBarMode;
                SamsungLightBarControlHelper samsungLightBarControlHelper = this.mSamsungLightBarControlHelper;
                samsungLightBarControlHelper.getClass();
                int i3 = SamsungLightBarControlHelperKt.$r8$clinit;
                StringBuilder sb = new StringBuilder("onStatusBarAppearanceChanged() -");
                sb.append("  sbModeChanged:" + z);
                sb.append(", statusBarMode:" + i2);
                sb.append(", barState:".concat(BarTransitions.modeToString(i2)));
                sb.append(", isKeyguardShowing:" + ((KeyguardStateControllerImpl) samsungLightBarControlHelper.keyguardStateController).mShowing);
                sb.append(", navbarColorManagedByIme:" + z3);
                sb.append(", (");
                for (AppearanceRegion appearanceRegion : appearanceRegionArr) {
                    sb.append(appearanceRegion + ", ");
                }
                ExifInterface$$ExternalSyntheticOutline0.m(sb, ")", "SamsungLightBarControlHelper");
            }
        } else {
            this.mSamsungStatusBarGrayIconHelper.getClass();
        }
        this.mNavbarColorManagedByIme = z3;
    }

    public final void reevaluate() {
        if (this.mIsDefaultDisplay) {
            onStatusBarAppearanceChanged(this.mAppearanceRegions, true, true, this.mNavbarColorManagedByIme);
        }
        onNavigationBarAppearanceChanged(this.mAppearance, this.mNavigationBarMode, true, this.mNavbarColorManagedByIme, "reevaluate: " + Debug.getCallers(1));
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mJavaAdapter.alwaysCollectFlow(((StatusBarModeRepositoryImpl) this.mStatusBarModeRepository).defaultDisplay.statusBarAppearance, new LightBarController$$ExternalSyntheticLambda0(this, 1));
    }

    public final void updateNavigation() {
        if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
            this.mObserver.notify(new LightBarController$$ExternalSyntheticLambda0(this, 0));
            return;
        }
        LightBarTransitionsController lightBarTransitionsController = this.mNavigationBarController;
        if (lightBarTransitionsController != null) {
            int i = this.mNavigationMode;
            lightBarTransitionsController.getClass();
            if (!QuickStepContract.isGesturalMode(i) || lightBarTransitionsController.mNavigationButtonsForcedVisible) {
                this.mNavigationBarController.setIconsDark(this.mNavigationLight, animateChange());
            }
        }
    }

    public final void updateStatus(AppearanceRegion[] appearanceRegionArr) {
        int length = appearanceRegionArr.length;
        ArrayList arrayList = new ArrayList();
        for (AppearanceRegion appearanceRegion : appearanceRegionArr) {
            if (isLight(appearanceRegion.getAppearance(), this.mStatusBarMode, 8)) {
                arrayList.add(appearanceRegion.getBounds());
            }
        }
        SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper = this.mSamsungStatusBarGrayIconHelper;
        samsungStatusBarGrayIconHelper.getClass();
        PhoneStatusBarViewController$onViewAttached$3 phoneStatusBarViewController$onViewAttached$3 = samsungStatusBarGrayIconHelper.grayIconChangedCallback;
        if (phoneStatusBarViewController$onViewAttached$3 != null) {
            BatteryMeterView batteryMeterView = (BatteryMeterView) ((PhoneStatusBarView) ((ViewController) phoneStatusBarViewController$onViewAttached$3.this$0).mView).requireViewById(R.id.battery);
            batteryMeterView.mIsGrayColor = false;
            batteryMeterView.mSamsungDrawable.shouldShowGrayIcon = false;
        }
        boolean isEmpty = arrayList.isEmpty();
        SysuiDarkIconDispatcher sysuiDarkIconDispatcher = this.mStatusBarIconController;
        if (isEmpty) {
            if (length == 0 && (((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).mIsDreaming || ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isKeyguardVisible())) {
                return;
            }
            LightBarTransitionsController lightBarTransitionsController = ((DarkIconDispatcherImpl) sysuiDarkIconDispatcher).mTransitionsController;
            lightBarTransitionsController.iconColorChanged = lightBarTransitionsController.needGrayIcon;
            lightBarTransitionsController.needGrayIcon = false;
            lightBarTransitionsController.setIconsDark(false, animateChange());
            this.mSamsungLightBarControlHelper.updateStatusBarModel("WHITE icon", length, arrayList, this.mStatusBarMode);
            return;
        }
        if (arrayList.size() == length) {
            DarkIconDispatcherImpl darkIconDispatcherImpl = (DarkIconDispatcherImpl) sysuiDarkIconDispatcher;
            LightBarTransitionsController lightBarTransitionsController2 = darkIconDispatcherImpl.mTransitionsController;
            lightBarTransitionsController2.iconColorChanged = lightBarTransitionsController2.needGrayIcon;
            lightBarTransitionsController2.needGrayIcon = false;
            darkIconDispatcherImpl.setIconsDarkArea(null);
            darkIconDispatcherImpl.mTransitionsController.setIconsDark(true, animateChange());
            this.mSamsungLightBarControlHelper.updateStatusBarModel("BLACK icon", length, arrayList, this.mStatusBarMode);
            return;
        }
        DarkIconDispatcherImpl darkIconDispatcherImpl2 = (DarkIconDispatcherImpl) sysuiDarkIconDispatcher;
        LightBarTransitionsController lightBarTransitionsController3 = darkIconDispatcherImpl2.mTransitionsController;
        lightBarTransitionsController3.iconColorChanged = lightBarTransitionsController3.needGrayIcon;
        lightBarTransitionsController3.needGrayIcon = false;
        darkIconDispatcherImpl2.setIconsDarkArea(arrayList);
        darkIconDispatcherImpl2.mTransitionsController.setIconsDark(true, animateChange());
        this.mSamsungLightBarControlHelper.updateStatusBarModel("BLACK magic", length, arrayList, this.mStatusBarMode);
    }
}
