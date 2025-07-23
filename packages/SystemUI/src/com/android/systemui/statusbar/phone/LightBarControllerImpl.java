package com.android.systemui.statusbar.phone;

import android.graphics.Rect;
import android.os.Debug;
import android.view.InsetsFlags;
import android.view.ViewDebug;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.internal.view.AppearanceRegion;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.shared.statusbar.phone.BarTransitions;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.core.StatusBarConnectedDisplays;
import com.android.systemui.statusbar.data.repository.DarkIconDispatcherStore;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepository;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore;
import com.android.systemui.statusbar.layout.BoundsPair;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Consumer;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class LightBarControllerImpl implements BatteryController.BatteryStateChangeCallback, LightBarController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mAppearance;
    public final BatteryController mBatteryController;
    public final BiometricUnlockController mBiometricUnlockController;
    public boolean mBouncerVisible;
    public final CoroutineScope mCoroutineScope;
    public boolean mDirectReplying;
    public final int mDisplayId;
    public final DumpManager mDumpManager;
    public final String mDumpableName;
    public boolean mForceDarkForScrim;
    public boolean mForceLightForScrim;
    public boolean mGlobalActionsVisible;
    public boolean mHasLightNavigationBar;
    public boolean mIsCustomizingForBackNav;
    public final boolean mIsDefaultDisplay;
    public final CoroutineContext mMainContext;
    public final NavBarStateManager mNavBarStateManager;
    public final NavigationModeController mNavModeController;
    public boolean mNavbarColorManagedByIme;
    public LightBarTransitionsController mNavigationBarController;
    public int mNavigationBarMode;
    public boolean mNavigationLight;
    public int mNavigationMode;
    public final LightBarTransientObserver mObserver;
    public boolean mQsCustomizing;
    public boolean mQsExpanded;
    public final SamsungLightBarControlHelper mSamsungLightBarControlHelper;
    public final SamsungStatusBarGrayIconHelper mSamsungStatusBarGrayIconHelper;
    public final DarkIconDispatcherImpl mStatusBarIconController;
    public int mStatusBarMode;
    public final StatusBarModePerDisplayRepository mStatusBarModeRepository;
    public AppearanceRegion[] mAppearanceRegions = new AppearanceRegion[0];
    public BoundsPair mStatusBarBounds = new BoundsPair(new Rect(), new Rect());
    public final LightBarControllerImpl$$ExternalSyntheticLambda1 mNavigationModeListener = new NavigationModeController.ModeChangedListener() { // from class: com.android.systemui.statusbar.phone.LightBarControllerImpl$$ExternalSyntheticLambda1
        @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
        public final void onNavigationModeChanged(int i) {
            LightBarControllerImpl.this.mNavigationMode = i;
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        LightBarControllerImpl create(int i, CoroutineScope coroutineScope, DarkIconDispatcher darkIconDispatcher, StatusBarModePerDisplayRepository statusBarModePerDisplayRepository);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class LegacyFactory implements LightBarController.Factory {
        public final CoroutineScope mApplicationScope;
        public final DarkIconDispatcherStore mDarkIconDispatcherStore;
        public final Factory mFactory;
        public final StatusBarModeRepositoryStore mStatusBarModeRepositoryStore;

        public LegacyFactory(Factory factory, CoroutineScope coroutineScope, DarkIconDispatcherStore darkIconDispatcherStore, StatusBarModeRepositoryStore statusBarModeRepositoryStore) {
            this.mFactory = factory;
            this.mApplicationScope = coroutineScope;
            this.mDarkIconDispatcherStore = darkIconDispatcherStore;
            this.mStatusBarModeRepositoryStore = statusBarModeRepositoryStore;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class LightBarTransientObserver extends SystemBarObserver {
        public final ArrayList mList;

        public /* synthetic */ LightBarTransientObserver(int i) {
            this();
        }

        public final void notify(Consumer consumer) {
            this.mList.forEach(new LightBarControllerImpl$$ExternalSyntheticLambda2(consumer, 2));
        }

        private LightBarTransientObserver() {
            this.mList = new ArrayList();
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.phone.LightBarControllerImpl$$ExternalSyntheticLambda1] */
    public LightBarControllerImpl(int i, CoroutineScope coroutineScope, DarkIconDispatcher darkIconDispatcher, BatteryController batteryController, NavigationModeController navigationModeController, StatusBarModePerDisplayRepository statusBarModePerDisplayRepository, DumpManager dumpManager, CoroutineContext coroutineContext, BiometricUnlockController biometricUnlockController, DisplayTracker displayTracker, SamsungLightBarControlHelper samsungLightBarControlHelper, SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper) {
        int i2 = 0;
        this.mIsDefaultDisplay = false;
        if (BasicRune.NAVBAR_LIGHTBAR) {
            this.mNavBarStateManager = ((NavBarStoreImpl) ((NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class))).getNavStateManager(i);
            this.mSamsungLightBarControlHelper = samsungLightBarControlHelper;
            samsungLightBarControlHelper.getClass();
        }
        this.mDisplayId = i;
        this.mCoroutineScope = coroutineScope;
        this.mStatusBarIconController = (DarkIconDispatcherImpl) darkIconDispatcher;
        this.mBatteryController = batteryController;
        this.mNavModeController = navigationModeController;
        this.mDumpManager = dumpManager;
        this.mStatusBarModeRepository = statusBarModePerDisplayRepository;
        this.mMainContext = coroutineContext;
        this.mBiometricUnlockController = biometricUnlockController;
        this.mDumpableName = getClass().getSimpleName() + (i == 0 ? "" : String.valueOf(i));
        if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
            this.mObserver = new LightBarTransientObserver(i2);
        }
        this.mSamsungStatusBarGrayIconHelper = samsungStatusBarGrayIconHelper;
        displayTracker.getClass();
        if (i == 0) {
            this.mIsDefaultDisplay = true;
        }
    }

    public static boolean isLight(int i, int i2, int i3) {
        return (i2 == 0 || i2 == 6) && ((i & i3) != 0);
    }

    public final boolean animateChange() {
        BiometricUnlockController biometricUnlockController = this.mBiometricUnlockController;
        if (biometricUnlockController != null) {
            int i = biometricUnlockController.mMode;
            boolean z = (i == 2 || i == 1) ? false : true;
            boolean z2 = (((KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.sDependency.getDependencyInner(KeyguardStateController.class))).mKeyguardGoingAway || ((KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.sDependency.getDependencyInner(KeyguardStateController.class))).mKeyguardFadingAway) ? false : true;
            if (z && z2) {
                return true;
            }
        }
        return false;
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
        LightBarTransitionsController lightBarTransitionsController = this.mStatusBarIconController.mTransitionsController;
        if (lightBarTransitionsController != null) {
            printWriter.println(" StatusBarTransitionsController:");
            lightBarTransitionsController.dump(printWriter, strArr);
            printWriter.println();
        }
        if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
            printWriter.println(" NavigationBarTransitionsController:");
            this.mObserver.notify(new Consumer() { // from class: com.android.systemui.statusbar.phone.LightBarControllerImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PrintWriter printWriter2 = printWriter;
                    String[] strArr2 = strArr;
                    LightBarTransitionsController lightBarTransitionsController2 = (LightBarTransitionsController) obj;
                    int i2 = LightBarControllerImpl.$r8$clinit;
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
            Method dump skipped, instructions count: 271
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.LightBarControllerImpl.onNavigationBarAppearanceChanged(int, int, boolean, boolean, java.lang.String):void");
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
        int i = this.mDisplayId;
        if (i == 0) {
            this.mDumpManager.registerCriticalDumpable(this.mDumpableName, this);
        }
        ((BatteryControllerImpl) this.mBatteryController).addCallback(this);
        this.mNavigationMode = this.mNavModeController.addListener(this.mNavigationModeListener);
        if (BasicRune.NAVBAR_ENABLED) {
            int i2 = StatusBarConnectedDisplays.$r8$clinit;
            if (i != 0) {
                return;
            }
        }
        JavaAdapterKt.collectFlow(this.mCoroutineScope, this.mMainContext, ((StatusBarModePerDisplayRepositoryImpl) this.mStatusBarModeRepository).statusBarAppearance, new LightBarControllerImpl$$ExternalSyntheticLambda2(this, 1));
    }

    public final void stop() {
        this.mDumpManager.unregisterDumpable(this.mDumpableName);
        ((BatteryControllerImpl) this.mBatteryController).removeCallback(this);
        this.mNavModeController.removeListener(this.mNavigationModeListener);
        if (BasicRune.NAVBAR_LIGHTBAR) {
            this.mSamsungLightBarControlHelper.getClass();
        }
    }

    public final void updateNavigation() {
        if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
            this.mObserver.notify(new LightBarControllerImpl$$ExternalSyntheticLambda2(this, 0));
            return;
        }
        LightBarTransitionsController lightBarTransitionsController = this.mNavigationBarController;
        if (lightBarTransitionsController != null) {
            int i = this.mNavigationMode;
            if (BasicRune.NAVBAR_ENABLED || !QuickStepContract.isGesturalMode(i) || lightBarTransitionsController.mNavigationButtonsForcedVisible) {
                this.mNavigationBarController.setIconsDark(this.mNavigationLight, animateChange());
            }
        }
    }

    public final void updateStatus(AppearanceRegion[] appearanceRegionArr) {
        int length = appearanceRegionArr.length;
        ArrayList<Rect> arrayList = new ArrayList<>();
        for (AppearanceRegion appearanceRegion : appearanceRegionArr) {
            if (isLight(appearanceRegion.getAppearance(), this.mStatusBarMode, 8)) {
                arrayList.add(appearanceRegion.getBounds());
            }
        }
        SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper = this.mSamsungStatusBarGrayIconHelper;
        samsungStatusBarGrayIconHelper.getClass();
        PhoneStatusBarViewControllerExt$setUpBatteryView$1$1$1 phoneStatusBarViewControllerExt$setUpBatteryView$1$1$1 = samsungStatusBarGrayIconHelper.grayIconChangedCallback;
        if (phoneStatusBarViewControllerExt$setUpBatteryView$1$1$1 != null) {
            BatteryMeterView batteryMeterView = phoneStatusBarViewControllerExt$setUpBatteryView$1$1$1.$it;
            batteryMeterView.mIsGrayColor = false;
            batteryMeterView.mSamsungDrawable.shouldShowGrayIcon = false;
        }
        boolean isEmpty = arrayList.isEmpty();
        DarkIconDispatcherImpl darkIconDispatcherImpl = this.mStatusBarIconController;
        if (isEmpty) {
            if (length == 0) {
                boolean z = ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).mIsDreaming;
                boolean isKeyguardVisible = ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isKeyguardVisible();
                boolean z2 = ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).mGoingToSleep;
                if (z || isKeyguardVisible || z2) {
                    ActionBarContextView$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("SKIP updateStatus (white icon) dreaming:", ", keyguardVisible:", ", goingToSleep:", z, isKeyguardVisible), z2, "LightBarController");
                    return;
                }
            }
            LightBarTransitionsController lightBarTransitionsController = darkIconDispatcherImpl.mTransitionsController;
            lightBarTransitionsController.iconColorChanged = false;
            lightBarTransitionsController.setIconsDark(false, animateChange());
            this.mSamsungLightBarControlHelper.updateStatusBarModel("WHITE icon", length, arrayList, this.mStatusBarMode);
            return;
        }
        if (arrayList.size() == length) {
            darkIconDispatcherImpl.mTransitionsController.iconColorChanged = false;
            darkIconDispatcherImpl.setIconsDarkArea(null);
            darkIconDispatcherImpl.mTransitionsController.setIconsDark(true, animateChange());
            this.mSamsungLightBarControlHelper.updateStatusBarModel("BLACK icon", length, arrayList, this.mStatusBarMode);
            return;
        }
        darkIconDispatcherImpl.mTransitionsController.iconColorChanged = false;
        darkIconDispatcherImpl.setIconsDarkArea(arrayList);
        darkIconDispatcherImpl.mTransitionsController.setIconsDark(true, animateChange());
        this.mSamsungLightBarControlHelper.updateStatusBarModel("BLACK magic", length, arrayList, this.mStatusBarMode);
    }
}
