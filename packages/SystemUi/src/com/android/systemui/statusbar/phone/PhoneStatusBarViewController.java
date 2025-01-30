package com.android.systemui.statusbar.phone;

import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.widget.ImageView;
import androidx.recyclerview.widget.SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.biometrics.SideFpsController$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.log.SecTouchLogHelper;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.SecPanelBlockExpandingHelper;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.shade.ShadeLogger;
import com.android.systemui.shared.animation.UnfoldMoveFromCenterAnimator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import com.android.systemui.statusbar.events.ViewState;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.phone.IndicatorMarqueeGardener;
import com.android.systemui.statusbar.phone.PhoneStatusBarClockManager;
import com.android.systemui.statusbar.phone.knox.ui.binder.KnoxStatusBarControlBinder;
import com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarControlViewModel;
import com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarViewControl;
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserSwitcherContainer;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.NetspeedViewController;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;
import com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder;
import com.android.systemui.user.ui.viewmodel.StatusBarUserChipViewModel;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.view.ViewUtil;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PhoneStatusBarViewController extends ViewController implements IndicatorGarden, Dumpable {
    public final IndicatorGardenContainer centerContainer;
    public final CentralSurfaces centralSurfaces;
    public final ConfigurationController configurationController;
    public final PhoneStatusBarViewController$configurationListener$1 configurationListener;
    public final DumpManager dumpManager;
    public final PhoneStatusBarViewController$gardener$1 gardener;
    public final ViewGroup heightContainer;
    public final IndicatorCutoutUtil indicatorCutoutUtil;
    public final IndicatorGardenPresenter indicatorGardenPresenter;
    public final IndicatorMarqueeGardener indicatorMarqueeGardener;
    public final KnoxStatusBarControlViewModel knoxStateBarControlViewModel;
    public final IndicatorGardenContainer leftContainer;
    public final StatusBarMoveFromCenterAnimationController moveFromCenterAnimationController;
    public final NetspeedViewController netspeedViewController;
    public final SecPanelBlockExpandingHelper panelBlockExpandingHelper;
    public final PhoneStatusBarClockManager phoneStatusBarClockManager;
    public final PrivacyDotViewController privacyDotViewController;
    public final ScopedUnfoldTransitionProgressProvider progressProvider;
    public final IndicatorGardenContainer rightContainer;
    public final SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper;
    public final ShadeController shadeController;
    public final ShadeLogger shadeLogger;
    public final ViewGroup sidePaddingContainer;
    public final PhoneStatusBarViewController$statusIconContainerCallback$1 statusIconContainerCallback;
    public final StatusIconContainerController statusIconContainerController;
    public final TwoPhoneModeIconController twoPhoneModeIconController;
    public final IndicatorGardenViewTreeLogHelper viewTreeLogHelper;
    public final ViewUtil viewUtil;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Factory {
        public final CentralSurfaces centralSurfaces;
        public final ConfigurationController configurationController;
        public final DumpManager dumpManager;
        public final FeatureFlags featureFlags;
        public final IndicatorCutoutUtil indicatorCutoutUtil;
        public final IndicatorGardenPresenter indicatorGardenPresenter;
        public final IndicatorMarqueeGardener indicatorMarqueeGardener;
        public final KnoxStatusBarControlViewModel knoxStateBarViewModel;
        public final NetspeedViewController netspeedViewController;
        public final PhoneStatusBarClockManager phoneStatusBarClockManager;
        public final PrivacyDotViewController privacyDotViewController;
        public final Optional progressProvider;
        public final SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper;
        public final ShadeController shadeController;
        public final ShadeLogger shadeLogger;
        public final StatusIconContainerController statusIconContainerController;
        public final TwoPhoneModeIconController twoPhoneModeIconController;
        public final StatusBarUserChipViewModel userChipViewModel;
        public final IndicatorGardenViewTreeLogHelper viewTreeLogHelper;
        public final ViewUtil viewUtil;

        public Factory(Optional<SysUIUnfoldComponent> optional, Optional<ScopedUnfoldTransitionProgressProvider> optional2, FeatureFlags featureFlags, StatusBarUserChipViewModel statusBarUserChipViewModel, CentralSurfaces centralSurfaces, ShadeController shadeController, ShadeLogger shadeLogger, ViewUtil viewUtil, ConfigurationController configurationController, IndicatorGardenPresenter indicatorGardenPresenter, DumpManager dumpManager, IndicatorGardenViewTreeLogHelper indicatorGardenViewTreeLogHelper, NetspeedViewController netspeedViewController, KnoxStatusBarControlViewModel knoxStatusBarControlViewModel, StatusIconContainerController statusIconContainerController, PrivacyDotViewController privacyDotViewController, SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper, TwoPhoneModeIconController twoPhoneModeIconController, PhoneStatusBarClockManager phoneStatusBarClockManager, IndicatorCutoutUtil indicatorCutoutUtil, IndicatorMarqueeGardener indicatorMarqueeGardener) {
            this.progressProvider = optional2;
            this.featureFlags = featureFlags;
            this.userChipViewModel = statusBarUserChipViewModel;
            this.centralSurfaces = centralSurfaces;
            this.shadeController = shadeController;
            this.shadeLogger = shadeLogger;
            this.viewUtil = viewUtil;
            this.configurationController = configurationController;
            this.indicatorGardenPresenter = indicatorGardenPresenter;
            this.dumpManager = dumpManager;
            this.viewTreeLogHelper = indicatorGardenViewTreeLogHelper;
            this.netspeedViewController = netspeedViewController;
            this.knoxStateBarViewModel = knoxStatusBarControlViewModel;
            this.statusIconContainerController = statusIconContainerController;
            this.privacyDotViewController = privacyDotViewController;
            this.samsungStatusBarGrayIconHelper = samsungStatusBarGrayIconHelper;
            this.twoPhoneModeIconController = twoPhoneModeIconController;
            this.phoneStatusBarClockManager = phoneStatusBarClockManager;
            this.indicatorCutoutUtil = indicatorCutoutUtil;
            this.indicatorMarqueeGardener = indicatorMarqueeGardener;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PhoneStatusBarViewTouchHandler implements Gefingerpoken {
        public final SecTouchLogHelper touchLogHelper = new SecTouchLogHelper();

        public PhoneStatusBarViewTouchHandler() {
        }

        @Override // com.android.systemui.Gefingerpoken
        public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            int i = SecTouchLogHelper.$r8$clinit;
            this.touchLogHelper.printOnInterceptTouchEventLog(motionEvent, "PSBVC", "");
            PhoneStatusBarViewController.this.onTouch(motionEvent);
            return false;
        }

        @Override // com.android.systemui.Gefingerpoken
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            int i = SecTouchLogHelper.$r8$clinit;
            this.touchLogHelper.printOnTouchEventLog(motionEvent, "PSBVC", "");
            PhoneStatusBarViewController phoneStatusBarViewController = PhoneStatusBarViewController.this;
            phoneStatusBarViewController.onTouch(motionEvent);
            CentralSurfaces centralSurfaces = phoneStatusBarViewController.centralSurfaces;
            if (!((CentralSurfacesImpl) centralSurfaces).mCommandQueue.panelsEnabled()) {
                if (motionEvent.getAction() == 0) {
                    int i2 = StringCompanionObject.$r8$clinit;
                    String.format(SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0.m47m("onTouchForwardedFromStatusBar: panel disabled, ignoring touch at (", (int) motionEvent.getX(), ",", (int) motionEvent.getY(), ")"), Arrays.copyOf(new Object[0], 0));
                }
                return false;
            }
            if (motionEvent.getAction() != 0 || ((NotificationPanelViewController) ((CentralSurfacesImpl) centralSurfaces).mShadeSurface).mView.isEnabled()) {
                return ((NotificationPanelViewController) ((CentralSurfacesImpl) centralSurfaces).mShadeSurface).mTouchHandler.onTouchEvent(motionEvent);
            }
            phoneStatusBarViewController.shadeLogger.logMotionEvent(motionEvent, "onTouchForwardedFromStatusBar: panel view disabled");
            return true;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StatusBarViewsCenterProvider implements UnfoldMoveFromCenterAnimator.ViewCenterProvider {
        public static void getViewEdgeCenter(View view, Point point, boolean z) {
            boolean z2 = z ^ (view.getResources().getConfiguration().getLayoutDirection() == 1);
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            int i = iArr[0];
            int i2 = iArr[1];
            point.x = i + (z2 ? view.getHeight() / 2 : view.getWidth() - (view.getHeight() / 2));
            point.y = (view.getHeight() / 2) + i2;
        }

        @Override // com.android.systemui.shared.animation.UnfoldMoveFromCenterAnimator.ViewCenterProvider
        public final void getViewCenter(View view, Point point) {
            int id = view.getId();
            if (id == R.id.status_bar_start_side_except_heads_up) {
                getViewEdgeCenter(view, point, true);
            } else if (id == R.id.status_bar_end_side_content) {
                getViewEdgeCenter(view, point, false);
            } else {
                UnfoldMoveFromCenterAnimator.TranslationApplier.DefaultImpls.getViewCenter(view, point);
            }
        }
    }

    public /* synthetic */ PhoneStatusBarViewController(PhoneStatusBarView phoneStatusBarView, ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider, CentralSurfaces centralSurfaces, ShadeController shadeController, ShadeLogger shadeLogger, StatusBarMoveFromCenterAnimationController statusBarMoveFromCenterAnimationController, StatusBarUserChipViewModel statusBarUserChipViewModel, ViewUtil viewUtil, ConfigurationController configurationController, IndicatorGardenPresenter indicatorGardenPresenter, DumpManager dumpManager, IndicatorGardenViewTreeLogHelper indicatorGardenViewTreeLogHelper, NetspeedViewController netspeedViewController, KnoxStatusBarControlViewModel knoxStatusBarControlViewModel, StatusIconContainerController statusIconContainerController, PrivacyDotViewController privacyDotViewController, SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper, TwoPhoneModeIconController twoPhoneModeIconController, PhoneStatusBarClockManager phoneStatusBarClockManager, IndicatorCutoutUtil indicatorCutoutUtil, IndicatorMarqueeGardener indicatorMarqueeGardener, DefaultConstructorMarker defaultConstructorMarker) {
        this(phoneStatusBarView, scopedUnfoldTransitionProgressProvider, centralSurfaces, shadeController, shadeLogger, statusBarMoveFromCenterAnimationController, statusBarUserChipViewModel, viewUtil, configurationController, indicatorGardenPresenter, dumpManager, indicatorGardenViewTreeLogHelper, netspeedViewController, knoxStatusBarControlViewModel, statusIconContainerController, privacyDotViewController, samsungStatusBarGrayIconHelper, twoPhoneModeIconController, phoneStatusBarClockManager, indicatorCutoutUtil, indicatorMarqueeGardener);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder sb = new StringBuilder("IndicatorBasicGardener ");
        PhoneStatusBarViewController$gardener$1 phoneStatusBarViewController$gardener$1 = this.gardener;
        sb.append(phoneStatusBarViewController$gardener$1.gardenName);
        printWriter.println(new StringBuilder(sb.toString()));
        printWriter.println();
        IndicatorGardenModel indicatorGardenModel = phoneStatusBarViewController$gardener$1.currentGardenModel;
        indicatorGardenModel.getClass();
        printWriter.println("IndicatorGardenModel");
        SideFpsController$$ExternalSyntheticOutline0.m105m("  height:", indicatorGardenModel.totalHeight, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("  hasCameraTopMargin:", indicatorGardenModel.hasCameraTopMargin, printWriter);
        SideFpsController$$ExternalSyntheticOutline0.m105m("  cameraTopMargin:", indicatorGardenModel.cameraTopMargin, printWriter);
        SideFpsController$$ExternalSyntheticOutline0.m105m("  statusBarContentsHeight=", indicatorGardenModel.totalHeight - indicatorGardenModel.cameraTopMargin, printWriter);
        SideFpsController$$ExternalSyntheticOutline0.m105m("  leftPadding:", indicatorGardenModel.paddingLeft, printWriter);
        SideFpsController$$ExternalSyntheticOutline0.m105m("  rightPadding:", indicatorGardenModel.paddingRight, printWriter);
        SideFpsController$$ExternalSyntheticOutline0.m105m("  leftContainer:", indicatorGardenModel.maxWidthLeftContainer, printWriter);
        SideFpsController$$ExternalSyntheticOutline0.m105m("  centerContainer:", indicatorGardenModel.maxWidthCenterContainer, printWriter);
        printWriter.println("  rightContainer:" + indicatorGardenModel.maxWidthRightContainer);
        printWriter.println();
        ViewGroup viewGroup = (ViewGroup) this.mView;
        this.viewTreeLogHelper.getClass();
        printWriter.println("IndicatorGardenViewTreeLogHelper");
        IndicatorGardenViewTreeLogHelper.printDumpLog(printWriter, viewGroup, 0, 0);
        IndicatorGardenViewTreeLogHelper.printChildWidthRecursive(printWriter, viewGroup, 1);
        if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT) {
            this.statusIconContainerController.dump(printWriter);
        }
        KnoxStatusBarControlViewModel knoxStatusBarControlViewModel = this.knoxStateBarControlViewModel;
        knoxStatusBarControlViewModel.getClass();
        printWriter.println();
        printWriter.println("  KnoxStatusBarControlViewModel");
        printWriter.println("    statusBarHidden=" + knoxStatusBarControlViewModel.statusBarHidden.getValue());
        printWriter.println("    statusBarIconsEnabled=" + knoxStatusBarControlViewModel.statusBarIconsEnabled.getValue());
        printWriter.println("    knoxStatusBarCustomText=" + knoxStatusBarControlViewModel.knoxStatusBarCustomText.getValue());
        printWriter.println(" BasicRune.STATUS_LAYOUT_MARQUEE: true");
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final IndicatorGardenContainer getCenterContainer() {
        return this.centerContainer;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final int getEssentialLeftWidth() {
        return this.phoneStatusBarClockManager.getClockWidth();
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0031  */
    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getEssentialRightWidth() {
        int i;
        TwoPhoneModeIconController twoPhoneModeIconController;
        BatteryMeterView batteryMeterView = (BatteryMeterView) ((PhoneStatusBarView) this.mView).findViewById(R.id.battery);
        int i2 = 0;
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED) {
            View findViewById = ((PhoneStatusBarView) this.mView).findViewById(R.id.networkSpeed);
            if (findViewById.getVisibility() == 0) {
                i = findViewById.getMeasuredWidth();
                PhoneStatusBarClockManager phoneStatusBarClockManager = this.phoneStatusBarClockManager;
                int clockWidth = phoneStatusBarClockManager.mClockPosition != PhoneStatusBarClockManager.POSITION.RIGHT ? phoneStatusBarClockManager.getClockWidth() : 0;
                twoPhoneModeIconController = this.twoPhoneModeIconController;
                if (twoPhoneModeIconController.featureEnabled() && twoPhoneModeIconController.getViewWidth() > 0) {
                    i2 = twoPhoneModeIconController.getViewWidth();
                }
                return batteryMeterView.getMeasuredWidth() + i + clockWidth + i2;
            }
        }
        i = 0;
        PhoneStatusBarClockManager phoneStatusBarClockManager2 = this.phoneStatusBarClockManager;
        if (phoneStatusBarClockManager2.mClockPosition != PhoneStatusBarClockManager.POSITION.RIGHT) {
        }
        twoPhoneModeIconController = this.twoPhoneModeIconController;
        if (twoPhoneModeIconController.featureEnabled()) {
            i2 = twoPhoneModeIconController.getViewWidth();
        }
        return batteryMeterView.getMeasuredWidth() + i + clockWidth + i2;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final WindowInsets getGardenWindowInsets() {
        return ((PhoneStatusBarView) this.mView).getRootWindowInsets();
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final ViewGroup getHeightContainer() {
        return this.heightContainer;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final IndicatorGardenContainer getLeftContainer() {
        return this.leftContainer;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final IndicatorGardenContainer getRightContainer() {
        return this.rightContainer;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final ViewGroup getSidePaddingContainer() {
        return this.sidePaddingContainer;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        NetspeedViewController netspeedViewController;
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED && (netspeedViewController = this.netspeedViewController) != null) {
            netspeedViewController.init();
        }
        this.statusIconContainerController.init();
    }

    public final void onTouch(MotionEvent motionEvent) {
        if (!this.panelBlockExpandingHelper.mCommandQueue.panelsEnabled()) {
            return;
        }
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.centralSurfaces;
        if (centralSurfacesImpl.mStatusBarWindowState == 0) {
            centralSurfacesImpl.setInteracting(1, !(motionEvent.getAction() == 1 || motionEvent.getAction() == 3) || ((ShadeControllerImpl) this.shadeController).mExpandedVisible);
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        IndicatorGardenPresenter indicatorGardenPresenter = this.indicatorGardenPresenter;
        indicatorGardenPresenter.updateGardenWithNewModel(this);
        ConfigurationControllerImpl configurationControllerImpl = (ConfigurationControllerImpl) this.configurationController;
        PhoneStatusBarViewController$configurationListener$1 phoneStatusBarViewController$configurationListener$1 = this.configurationListener;
        configurationControllerImpl.addCallback(phoneStatusBarViewController$configurationListener$1);
        ((PhoneStatusBarView) this.mView).setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$onViewAttached$1
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                PhoneStatusBarViewController phoneStatusBarViewController = PhoneStatusBarViewController.this;
                phoneStatusBarViewController.indicatorGardenPresenter.onGardenApplyWindowInsets(phoneStatusBarViewController);
                PhoneStatusBarViewController.this.updatePaddingsForPrivacyDot(windowInsets);
                return view.onApplyWindowInsets(windowInsets);
            }
        });
        ((PhoneStatusBarView) this.mView).addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$onViewAttached$2
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                PhoneStatusBarViewController phoneStatusBarViewController = PhoneStatusBarViewController.this;
                IndicatorGardenPresenter indicatorGardenPresenter2 = phoneStatusBarViewController.indicatorGardenPresenter;
                indicatorGardenPresenter2.getClass();
                indicatorGardenPresenter2.mainHandler.post(new IndicatorGardenPresenter$onGardenOnLayout$1(indicatorGardenPresenter2, phoneStatusBarViewController));
            }
        });
        updatePaddingsForPrivacyDot(((PhoneStatusBarView) this.mView).getRootWindowInsets());
        IndicatorMarqueeGardener indicatorMarqueeGardener = this.indicatorMarqueeGardener;
        indicatorMarqueeGardener.wakefulnessLifecycle.addObserver(indicatorMarqueeGardener.wakefulnessLifecycleObserver);
        indicatorMarqueeGardener.updateMarqueeValues();
        KnoxStatusBarControlBinder.bind(this.knoxStateBarControlViewModel, (KnoxStatusBarViewControl) this.mView);
        PhoneStatusBarClockManager phoneStatusBarClockManager = this.phoneStatusBarClockManager;
        phoneStatusBarClockManager.mIndicatorGarden = this;
        ViewGroup viewGroup = phoneStatusBarClockManager.mGrandParentView;
        if (viewGroup != null) {
            phoneStatusBarClockManager.mLeftContainer = (ViewGroup) viewGroup.findViewById(R.id.left_clock_container);
            phoneStatusBarClockManager.mMiddleContainer = (ViewGroup) viewGroup.findViewById(R.id.middle_clock_container);
            phoneStatusBarClockManager.mRightContainer = (ViewGroup) viewGroup.findViewById(R.id.right_clock_container);
            ((SlimIndicatorViewMediatorImpl) phoneStatusBarClockManager.mSlimIndicatorViewMediator).registerSubscriber("[QuickStar]PhoneStatusBarClockManager", phoneStatusBarClockManager);
            phoneStatusBarClockManager.updateResources();
        } else {
            Log.e("[QuickStar]PhoneStatusBarClockManager", "onAttachedToWindow(), mGrandParentView is null");
        }
        this.dumpManager.registerNsDumpable("PhoneStatusBarViewController", this);
        PhoneStatusBarViewController$onViewAttached$3 phoneStatusBarViewController$onViewAttached$3 = new PhoneStatusBarViewController$onViewAttached$3(this);
        SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper = this.samsungStatusBarGrayIconHelper;
        samsungStatusBarGrayIconHelper.grayIconChangedCallback = phoneStatusBarViewController$onViewAttached$3;
        ((BatteryMeterView) ((PhoneStatusBarView) phoneStatusBarViewController$onViewAttached$3.this$0.mView).findViewById(R.id.battery)).mIsGrayColor = samsungStatusBarGrayIconHelper.isGrayIcon;
        View findViewById = ((PhoneStatusBarView) this.mView).findViewById(R.id.status_bar_start_side_except_heads_up);
        ViewGroup viewGroup2 = (ViewGroup) ((PhoneStatusBarView) this.mView).findViewById(R.id.status_bar_end_side_content);
        TwoPhoneModeIconController twoPhoneModeIconController = this.twoPhoneModeIconController;
        if (twoPhoneModeIconController.featureEnabled()) {
            ImageView imageView = new ImageView(twoPhoneModeIconController.context);
            twoPhoneModeIconController.modeIconView = imageView;
            imageView.setLayoutParams(new ViewGroup.LayoutParams(-2, -1));
            ImageView imageView2 = twoPhoneModeIconController.modeIconView;
            if (imageView2 == null) {
                imageView2 = null;
            }
            imageView2.setVisibility(8);
            ImageView imageView3 = twoPhoneModeIconController.modeIconView;
            if (imageView3 == null) {
                imageView3 = null;
            }
            viewGroup2.addView(imageView3);
            ImageView imageView4 = twoPhoneModeIconController.modeIconView;
            twoPhoneModeIconController.darkIconDispatcher.addDarkReceiver(imageView4 != null ? imageView4 : null);
            twoPhoneModeIconController.settingsHelper.registerCallback(twoPhoneModeIconController.settingsListener, Settings.Global.getUriFor("two_register"), Settings.Global.getUriFor("two_account"), Settings.Global.getUriFor("two_call_enabled"), Settings.Global.getUriFor("two_sms_enabled"));
            ((UserTrackerImpl) twoPhoneModeIconController.userTracker).addCallback(twoPhoneModeIconController.userTrackerCallback, twoPhoneModeIconController.executor);
            ((SlimIndicatorViewMediatorImpl) twoPhoneModeIconController.slimIndicatorViewMediator).registerSubscriber("TwoPhoneModeIconController", twoPhoneModeIconController.quickStarListener);
            ((ConfigurationControllerImpl) twoPhoneModeIconController.configurationController).addCallback(twoPhoneModeIconController.configurationListener);
            twoPhoneModeIconController.updateTwoPhoneMode();
        }
        if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT) {
            this.statusIconContainerController.view.mSidelingCutoutContainerInfo = new SidelingCutoutContainerInfo() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$onViewAttached$4
                @Override // com.android.systemui.statusbar.phone.SidelingCutoutContainerInfo
                public final int getRightSideAvailableWidth(Rect rect) {
                    PhoneStatusBarViewController phoneStatusBarViewController = PhoneStatusBarViewController.this;
                    BatteryMeterView batteryMeterView = (BatteryMeterView) ((PhoneStatusBarView) phoneStatusBarViewController.mView).findViewById(R.id.battery);
                    int paddingEnd = ((ViewGroup) ((PhoneStatusBarView) phoneStatusBarViewController.mView).findViewById(R.id.statusIcons)).getPaddingEnd();
                    int dimensionPixelSize = phoneStatusBarViewController.getResources().getDimensionPixelSize(17106182);
                    int width = phoneStatusBarViewController.getResources().getConfiguration().windowConfiguration.getBounds().width();
                    int dimensionPixelSize2 = phoneStatusBarViewController.getResources().getDimensionPixelSize(R.dimen.indicator_marquee_max_shift) + rect.right;
                    TwoPhoneModeIconController twoPhoneModeIconController2 = phoneStatusBarViewController.twoPhoneModeIconController;
                    int measuredWidth = batteryMeterView.getMeasuredWidth() + dimensionPixelSize + paddingEnd + ((!twoPhoneModeIconController2.featureEnabled() || twoPhoneModeIconController2.getViewWidth() <= 0) ? 0 : twoPhoneModeIconController2.getViewWidth());
                    PhoneStatusBarClockManager phoneStatusBarClockManager2 = phoneStatusBarViewController.phoneStatusBarClockManager;
                    if (phoneStatusBarClockManager2.mClockPosition == PhoneStatusBarClockManager.POSITION.RIGHT) {
                        measuredWidth += phoneStatusBarClockManager2.getClockWidth();
                    }
                    return (width - measuredWidth) - dimensionPixelSize2;
                }
            };
            final ViewGroup viewGroup3 = (ViewGroup) ((PhoneStatusBarView) this.mView).findViewById(R.id.system_icons);
            viewGroup3.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$onViewAttached$5
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    StatusIconContainer statusIconContainer;
                    if (PhoneStatusBarViewController.this.indicatorCutoutUtil.getDisplayCutoutAreaToExclude() == null || (statusIconContainer = (StatusIconContainer) viewGroup3.findViewById(R.id.statusIcons)) == null) {
                        return;
                    }
                    if (statusIconContainer.getWidth() != statusIconContainer.getMeasuredWidth() || statusIconContainer.getX() < 0.0f) {
                        statusIconContainer.requestLayout();
                    }
                }
            });
        }
        if (BasicRune.STATUS_LAYOUT_SHOW_ICONS_IN_UDC) {
            indicatorGardenPresenter.statusIconContainerCallbacks.add(this.statusIconContainerCallback);
        }
        if (this.moveFromCenterAnimationController == null) {
            return;
        }
        final View[] viewArr = {findViewById, viewGroup2};
        ((PhoneStatusBarView) this.mView).getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$onViewAttached$6
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                StatusBarMoveFromCenterAnimationController statusBarMoveFromCenterAnimationController = PhoneStatusBarViewController.this.moveFromCenterAnimationController;
                View[] viewArr2 = viewArr;
                UnfoldMoveFromCenterAnimator unfoldMoveFromCenterAnimator = statusBarMoveFromCenterAnimationController.moveFromCenterAnimator;
                UnfoldMoveFromCenterAnimator.updateDisplayProperties$default(unfoldMoveFromCenterAnimator);
                for (View view : viewArr2) {
                    UnfoldMoveFromCenterAnimator.AnimatedView animatedView = new UnfoldMoveFromCenterAnimator.AnimatedView(new WeakReference(view), 0.0f, 0.0f, 6, null);
                    unfoldMoveFromCenterAnimator.updateAnimatedView(animatedView, view);
                    ((ArrayList) unfoldMoveFromCenterAnimator.animatedViews).add(animatedView);
                }
                ((ArrayList) statusBarMoveFromCenterAnimationController.progressProvider.listeners).add(statusBarMoveFromCenterAnimationController.transitionListener);
                ((PhoneStatusBarView) PhoneStatusBarViewController.this.mView).getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
        ((PhoneStatusBarView) this.mView).addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$onViewAttached$7
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                if (i3 - i != i7 - i5) {
                    UnfoldMoveFromCenterAnimator unfoldMoveFromCenterAnimator = PhoneStatusBarViewController.this.moveFromCenterAnimationController.moveFromCenterAnimator;
                    UnfoldMoveFromCenterAnimator.updateDisplayProperties$default(unfoldMoveFromCenterAnimator);
                    Iterator it = ((ArrayList) unfoldMoveFromCenterAnimator.animatedViews).iterator();
                    while (it.hasNext()) {
                        UnfoldMoveFromCenterAnimator.AnimatedView animatedView = (UnfoldMoveFromCenterAnimator.AnimatedView) it.next();
                        View view2 = (View) animatedView.view.get();
                        if (view2 != null) {
                            unfoldMoveFromCenterAnimator.updateAnimatedView(animatedView, view2);
                        }
                    }
                    unfoldMoveFromCenterAnimator.onTransitionProgress(unfoldMoveFromCenterAnimator.lastAnimationProgress);
                }
            }
        });
        ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider = this.progressProvider;
        if (scopedUnfoldTransitionProgressProvider != null) {
            scopedUnfoldTransitionProgressProvider.setReadyToHandleTransition(true);
        }
        configurationControllerImpl.addCallback(phoneStatusBarViewController$configurationListener$1);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider = this.progressProvider;
        if (scopedUnfoldTransitionProgressProvider != null) {
            scopedUnfoldTransitionProgressProvider.setReadyToHandleTransition(false);
        }
        StatusBarMoveFromCenterAnimationController statusBarMoveFromCenterAnimationController = this.moveFromCenterAnimationController;
        if (statusBarMoveFromCenterAnimationController != null) {
            ((ArrayList) statusBarMoveFromCenterAnimationController.progressProvider.listeners).remove(statusBarMoveFromCenterAnimationController.transitionListener);
            UnfoldMoveFromCenterAnimator unfoldMoveFromCenterAnimator = statusBarMoveFromCenterAnimationController.moveFromCenterAnimator;
            unfoldMoveFromCenterAnimator.onTransitionProgress(1.0f);
            ((ArrayList) unfoldMoveFromCenterAnimator.animatedViews).clear();
        }
        ((ConfigurationControllerImpl) this.configurationController).removeCallback(this.configurationListener);
        DumpManager dumpManager = this.dumpManager;
        synchronized (dumpManager) {
            ((ArrayMap) dumpManager.nsDumpables).remove("PhoneStatusBarViewController");
        }
        this.samsungStatusBarGrayIconHelper.grayIconChangedCallback = null;
        if (this.twoPhoneModeIconController.featureEnabled()) {
            TwoPhoneModeIconController twoPhoneModeIconController = this.twoPhoneModeIconController;
            ImageView imageView = twoPhoneModeIconController.modeIconView;
            twoPhoneModeIconController.darkIconDispatcher.removeDarkReceiver(imageView != null ? imageView : null);
            twoPhoneModeIconController.settingsHelper.unregisterCallback(twoPhoneModeIconController.settingsListener);
            ((SlimIndicatorViewMediatorImpl) twoPhoneModeIconController.slimIndicatorViewMediator).unregisterSubscriber("TwoPhoneModeIconController");
            ((ConfigurationControllerImpl) twoPhoneModeIconController.configurationController).removeCallback(twoPhoneModeIconController.configurationListener);
            ((UserTrackerImpl) twoPhoneModeIconController.userTracker).removeCallback(twoPhoneModeIconController.userTrackerCallback);
        }
        PhoneStatusBarClockManager phoneStatusBarClockManager = this.phoneStatusBarClockManager;
        ((SlimIndicatorViewMediatorImpl) phoneStatusBarClockManager.mSlimIndicatorViewMediator).unregisterSubscriber("[QuickStar]PhoneStatusBarClockManager");
        phoneStatusBarClockManager.mClockPosition = PhoneStatusBarClockManager.POSITION.NONE;
        if (BasicRune.STATUS_LAYOUT_SHOW_ICONS_IN_UDC) {
            this.indicatorGardenPresenter.statusIconContainerCallbacks.remove(this.statusIconContainerCallback);
        }
        IndicatorMarqueeGardener indicatorMarqueeGardener = this.indicatorMarqueeGardener;
        indicatorMarqueeGardener.wakefulnessLifecycle.removeObserver(indicatorMarqueeGardener.wakefulnessLifecycleObserver);
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final void updateGarden(IndicatorGardenModel indicatorGardenModel, IndicatorGardenInputProperties indicatorGardenInputProperties) {
        updateGarden(indicatorGardenModel, indicatorGardenInputProperties);
    }

    public final void updatePaddingsForPrivacyDot(WindowInsets windowInsets) {
        PrivacyDotViewController privacyDotViewController = this.privacyDotViewController;
        int calculateLeftPadding = this.indicatorGardenPresenter.gardenAlgorithm.calculateLeftPadding();
        int calculateRightPadding = this.indicatorGardenPresenter.gardenAlgorithm.calculateRightPadding();
        synchronized (privacyDotViewController.lock) {
            privacyDotViewController.setNextViewState(ViewState.copy$default(privacyDotViewController.nextViewState, false, false, false, false, null, null, null, null, false, 0, privacyDotViewController.contentInsetsProvider.getStatusBarPaddingTop(), 0, null, null, calculateLeftPadding, calculateRightPadding, windowInsets.getInsets(WindowInsets.Type.systemBars()).left, windowInsets.getInsets(WindowInsets.Type.systemBars()).right, 15359));
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX WARN: Type inference failed for: r1v22, types: [com.android.systemui.statusbar.phone.PhoneStatusBarViewController$configurationListener$1] */
    /* JADX WARN: Type inference failed for: r1v23, types: [com.android.systemui.statusbar.phone.PhoneStatusBarViewController$statusIconContainerCallback$1] */
    /* JADX WARN: Type inference failed for: r1v30, types: [com.android.systemui.statusbar.phone.PhoneStatusBarViewController$gardener$1] */
    private PhoneStatusBarViewController(PhoneStatusBarView phoneStatusBarView, ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider, CentralSurfaces centralSurfaces, ShadeController shadeController, ShadeLogger shadeLogger, StatusBarMoveFromCenterAnimationController statusBarMoveFromCenterAnimationController, StatusBarUserChipViewModel statusBarUserChipViewModel, ViewUtil viewUtil, ConfigurationController configurationController, IndicatorGardenPresenter indicatorGardenPresenter, DumpManager dumpManager, IndicatorGardenViewTreeLogHelper indicatorGardenViewTreeLogHelper, NetspeedViewController netspeedViewController, KnoxStatusBarControlViewModel knoxStatusBarControlViewModel, StatusIconContainerController statusIconContainerController, PrivacyDotViewController privacyDotViewController, SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper, TwoPhoneModeIconController twoPhoneModeIconController, PhoneStatusBarClockManager phoneStatusBarClockManager, IndicatorCutoutUtil indicatorCutoutUtil, IndicatorMarqueeGardener indicatorMarqueeGardener) {
        super(phoneStatusBarView);
        this.progressProvider = scopedUnfoldTransitionProgressProvider;
        this.centralSurfaces = centralSurfaces;
        this.shadeController = shadeController;
        this.shadeLogger = shadeLogger;
        this.moveFromCenterAnimationController = statusBarMoveFromCenterAnimationController;
        this.viewUtil = viewUtil;
        this.configurationController = configurationController;
        this.indicatorGardenPresenter = indicatorGardenPresenter;
        this.dumpManager = dumpManager;
        this.viewTreeLogHelper = indicatorGardenViewTreeLogHelper;
        this.netspeedViewController = netspeedViewController;
        this.knoxStateBarControlViewModel = knoxStatusBarControlViewModel;
        this.statusIconContainerController = statusIconContainerController;
        this.privacyDotViewController = privacyDotViewController;
        this.samsungStatusBarGrayIconHelper = samsungStatusBarGrayIconHelper;
        this.twoPhoneModeIconController = twoPhoneModeIconController;
        this.phoneStatusBarClockManager = phoneStatusBarClockManager;
        this.indicatorCutoutUtil = indicatorCutoutUtil;
        this.indicatorMarqueeGardener = indicatorMarqueeGardener;
        this.panelBlockExpandingHelper = (SecPanelBlockExpandingHelper) Dependency.get(SecPanelBlockExpandingHelper.class);
        this.configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$configurationListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                PhoneStatusBarViewController phoneStatusBarViewController = PhoneStatusBarViewController.this;
                ((PhoneStatusBarView) phoneStatusBarViewController.mView).getResources().getDimensionPixelSize(R.dimen.display_cutout_margin_consumption);
                if (configuration != null) {
                    phoneStatusBarViewController.indicatorGardenPresenter.onGardenConfigurationChanged(phoneStatusBarViewController, configuration);
                }
                IndicatorMarqueeGardener indicatorMarqueeGardener2 = phoneStatusBarViewController.indicatorMarqueeGardener;
                indicatorMarqueeGardener2.context.getResources().getDimensionPixelSize(R.dimen.indicator_marquee_max_shift);
                if (configuration != null) {
                    int i = indicatorMarqueeGardener2.lastOrientation;
                    int i2 = configuration.orientation;
                    if (i != i2) {
                        if (i2 == 1 || i2 == 2) {
                            indicatorMarqueeGardener2.updateMarqueeValues();
                        }
                        indicatorMarqueeGardener2.lastOrientation = configuration.orientation;
                    }
                }
                phoneStatusBarViewController.phoneStatusBarClockManager.updateResources();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                IndicatorGardenInputProperties indicatorGardenInputProperties = PhoneStatusBarViewController.this.indicatorGardenPresenter.inputProperties;
                indicatorGardenInputProperties.updateWindowMetrics();
                indicatorGardenInputProperties.updatePaddingValues();
            }
        };
        this.statusIconContainerCallback = new IndicatorGardenPresenter.StatusIconContainerCallback() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$statusIconContainerCallback$1
            @Override // com.android.systemui.statusbar.phone.IndicatorGardenPresenter.StatusIconContainerCallback
            public final void updateStatusIconContainer() {
                ((ViewGroup) ((PhoneStatusBarView) PhoneStatusBarViewController.this.mView).findViewById(R.id.statusIcons)).requestLayout();
            }
        };
        ((PhoneStatusBarView) this.mView).mTouchEventHandler = new PhoneStatusBarViewTouchHandler();
        StatusBarUserChipViewBinder.bind((StatusBarUserSwitcherContainer) ((PhoneStatusBarView) this.mView).findViewById(R.id.user_switcher_container), statusBarUserChipViewModel, null);
        this.gardener = new IndicatorBasicGardener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$gardener$1
            {
                super(PhoneStatusBarViewController.this, "PhoneStatusBarViewController");
            }

            @Override // com.android.systemui.statusbar.phone.IndicatorBasicGardener
            public final ViewGroup.MarginLayoutParams getCameraTopMarginContainerMarginLayoutParams() {
                return (ViewGroup.MarginLayoutParams) ((PhoneStatusBarView) PhoneStatusBarViewController.this.mView).findViewById(R.id.status_bar_contents).getLayoutParams();
            }

            @Override // com.android.systemui.statusbar.phone.IndicatorBasicGardener
            public final boolean needToUpdatePaddings(IndicatorGardenModel indicatorGardenModel) {
                if (super.needToUpdatePaddings(indicatorGardenModel)) {
                    return true;
                }
                IndicatorMarqueeGardener indicatorMarqueeGardener2 = PhoneStatusBarViewController.this.indicatorMarqueeGardener;
                if (!indicatorMarqueeGardener2.hasSomethingChanged) {
                    return false;
                }
                indicatorMarqueeGardener2.marqueeModel.getClass();
                indicatorMarqueeGardener2.hasSomethingChanged = false;
                return true;
            }

            @Override // com.android.systemui.statusbar.phone.IndicatorBasicGardener
            public final void updateSidePadding(int i, int i2) {
                PhoneStatusBarViewController phoneStatusBarViewController = PhoneStatusBarViewController.this;
                IndicatorMarqueeGardener.MarqueeModel marqueeModel = phoneStatusBarViewController.indicatorMarqueeGardener.marqueeModel;
                int i3 = i + marqueeModel.shiftLeft;
                int i4 = marqueeModel.shiftTop;
                int i5 = i2 + marqueeModel.shiftRight;
                int i6 = marqueeModel.shiftBottom;
                ViewGroup viewGroup = phoneStatusBarViewController.sidePaddingContainer;
                if (viewGroup != null) {
                    viewGroup.setPadding(i3, i4, i5, i6);
                }
            }
        };
        View view = this.mView;
        this.heightContainer = (ViewGroup) view;
        this.sidePaddingContainer = (ViewGroup) ((PhoneStatusBarView) view).findViewById(R.id.status_bar_contents);
        this.leftContainer = (IndicatorGardenContainer) ((PhoneStatusBarView) this.mView).findViewById(R.id.status_bar_left_container);
        this.centerContainer = (IndicatorGardenContainer) ((PhoneStatusBarView) this.mView).findViewById(R.id.status_bar_center_container);
        this.rightContainer = (IndicatorGardenContainer) ((PhoneStatusBarView) this.mView).findViewById(R.id.system_icon_area);
    }
}
