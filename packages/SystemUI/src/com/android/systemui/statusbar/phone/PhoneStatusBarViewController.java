package com.android.systemui.statusbar.phone;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.log.QuickPanelLogger;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.shade.BaseShadeControllerImpl;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeLogger;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shade.display.StatusBarTouchShadeDisplayPolicy;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import com.android.systemui.shared.animation.UnfoldMoveFromCenterAnimator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.data.repository.StatusBarContentInsetsProviderStore;
import com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.phone.IndicatorMarqueeGardener;
import com.android.systemui.statusbar.phone.PhoneStatusBarClockManager;
import com.android.systemui.statusbar.phone.knox.ui.binder.KnoxStatusBarControlBinder;
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserSwitcherContainer;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.NetspeedView;
import com.android.systemui.statusbar.policy.NetspeedViewController;
import com.android.systemui.statusbar.policy.QSClockIndicatorView;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;
import com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder;
import com.android.systemui.user.ui.viewmodel.StatusBarUserChipViewModel;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.view.ViewUtil;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Optional;
import javax.inject.Provider;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PhoneStatusBarViewController extends ViewController implements IndicatorGarden {
    public BatteryMeterView battery;
    public final IndicatorGardenContainer centerContainer;
    public final CentralSurfaces centralSurfaces;
    public QSClockIndicatorView clock;
    public final Lazy commandQueue$delegate;
    public final ConfigurationController configurationController;
    public final PhoneStatusBarViewController$configurationListener$1 configurationListener;
    public final DarkIconDispatcher darkIconDispatcher;
    public final PhoneStatusBarViewController$gardener$1 gardener;
    public final ViewGroup heightContainer;
    public final dagger.Lazy lazyStatusBarShadeDisplayPolicy;
    public final IndicatorGardenContainer leftContainer;
    public final StatusBarMoveFromCenterAnimationController moveFromCenterAnimationController;
    public final ScopedUnfoldTransitionProgressProvider progressProvider;
    public final Lazy quickPanelLogger$delegate;
    public final IndicatorGardenContainer rightContainer;
    public final dagger.Lazy samsungExtLazy;
    public final ShadeController shadeController;
    public final ShadeLogger shadeLogger;
    public final ShadeViewController shadeViewController;
    public final ViewGroup sidePaddingContainer;
    public final StatusBarWindowStateController statusBarWindowStateController;
    public View statusContainer;
    public final ViewUtil viewUtil;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Factory {
        public final CentralSurfaces centralSurfaces;
        public final ConfigurationController configurationController;
        public final DarkIconDispatcher darkIconDispatcher;
        public final dagger.Lazy ext;
        public final FeatureFlags featureFlags;
        public final dagger.Lazy lazyStatusBarShadeDisplayPolicy;
        public final PanelExpansionInteractor panelExpansionInteractor;
        public final Optional progressProvider;
        public final ShadeController shadeController;
        public final ShadeLogger shadeLogger;
        public final ShadeViewController shadeViewController;
        public final StatusBarContentInsetsProviderStore statusBarContentInsetsProviderStore;
        public final Provider statusBarLongPressGestureDetector;
        public final StatusBarWindowStateController statusBarWindowStateController;
        public final StatusOverlayHoverListenerFactory statusOverlayHoverListenerFactory;
        public final StatusBarUserChipViewModel userChipViewModel;
        public final ViewUtil viewUtil;
        public final Provider windowRootView;

        public Factory(Optional<SysUIUnfoldComponent> optional, dagger.Lazy lazy, Optional<ScopedUnfoldTransitionProgressProvider> optional2, FeatureFlags featureFlags, StatusBarUserChipViewModel statusBarUserChipViewModel, CentralSurfaces centralSurfaces, StatusBarWindowStateController statusBarWindowStateController, ShadeController shadeController, ShadeViewController shadeViewController, PanelExpansionInteractor panelExpansionInteractor, Provider provider, Provider provider2, ShadeLogger shadeLogger, ViewUtil viewUtil, ConfigurationController configurationController, StatusOverlayHoverListenerFactory statusOverlayHoverListenerFactory, DarkIconDispatcher darkIconDispatcher, StatusBarContentInsetsProviderStore statusBarContentInsetsProviderStore, dagger.Lazy lazy2) {
            this.ext = lazy;
            this.progressProvider = optional2;
            this.featureFlags = featureFlags;
            this.userChipViewModel = statusBarUserChipViewModel;
            this.centralSurfaces = centralSurfaces;
            this.statusBarWindowStateController = statusBarWindowStateController;
            this.shadeController = shadeController;
            this.shadeViewController = shadeViewController;
            this.panelExpansionInteractor = panelExpansionInteractor;
            this.statusBarLongPressGestureDetector = provider;
            this.windowRootView = provider2;
            this.shadeLogger = shadeLogger;
            this.viewUtil = viewUtil;
            this.configurationController = configurationController;
            this.statusOverlayHoverListenerFactory = statusOverlayHoverListenerFactory;
            this.darkIconDispatcher = darkIconDispatcher;
            this.statusBarContentInsetsProviderStore = statusBarContentInsetsProviderStore;
            this.lazyStatusBarShadeDisplayPolicy = lazy2;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PhoneStatusBarViewTouchHandler implements Gefingerpoken {
        public PhoneStatusBarViewTouchHandler() {
        }

        @Override // com.android.systemui.Gefingerpoken
        public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            PhoneStatusBarViewController phoneStatusBarViewController = PhoneStatusBarViewController.this;
            if (action == 0) {
                PhoneStatusBarViewController.access$dispatchEventToShadeDisplayPolicy(phoneStatusBarViewController, motionEvent);
            }
            QuickPanelLogger quickPanelLogger = phoneStatusBarViewController.getQuickPanelLogger();
            if (quickPanelLogger != null) {
                quickPanelLogger.quickPanelLoggerHelper.onInterceptTouchEventLogger.log(motionEvent, quickPanelLogger.tag, "");
            }
            phoneStatusBarViewController.onTouch$1(motionEvent);
            return false;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                super.getViewCenter(view, point);
            }
        }
    }

    public /* synthetic */ PhoneStatusBarViewController(PhoneStatusBarView phoneStatusBarView, dagger.Lazy lazy, ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider, CentralSurfaces centralSurfaces, StatusBarWindowStateController statusBarWindowStateController, ShadeController shadeController, ShadeViewController shadeViewController, PanelExpansionInteractor panelExpansionInteractor, Provider provider, Provider provider2, ShadeLogger shadeLogger, StatusBarMoveFromCenterAnimationController statusBarMoveFromCenterAnimationController, StatusBarUserChipViewModel statusBarUserChipViewModel, ViewUtil viewUtil, ConfigurationController configurationController, StatusOverlayHoverListenerFactory statusOverlayHoverListenerFactory, DarkIconDispatcher darkIconDispatcher, StatusBarContentInsetsProviderStore statusBarContentInsetsProviderStore, dagger.Lazy lazy2, DefaultConstructorMarker defaultConstructorMarker) {
        this(phoneStatusBarView, lazy, scopedUnfoldTransitionProgressProvider, centralSurfaces, statusBarWindowStateController, shadeController, shadeViewController, panelExpansionInteractor, provider, provider2, shadeLogger, statusBarMoveFromCenterAnimationController, statusBarUserChipViewModel, viewUtil, configurationController, statusOverlayHoverListenerFactory, darkIconDispatcher, statusBarContentInsetsProviderStore, lazy2);
    }

    public static final void access$dispatchEventToShadeDisplayPolicy(PhoneStatusBarViewController phoneStatusBarViewController, MotionEvent motionEvent) {
        phoneStatusBarViewController.getClass();
        ShadeWindowGoesAround.INSTANCE.getClass();
        if (ShadeWindowGoesAround.FLAG.isTrue()) {
            ((StatusBarTouchShadeDisplayPolicy) phoneStatusBarViewController.lazyStatusBarShadeDisplayPolicy.get()).onStatusBarTouched(motionEvent, ((PhoneStatusBarView) phoneStatusBarViewController.mView).getWidth());
        }
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final IndicatorGardenContainer getCenterContainer() {
        return this.centerContainer;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final int getEssentialLeftWidth() {
        return ((PhoneStatusBarViewControllerExt) this.samsungExtLazy.get()).phoneStatusBarClockManager.getClockWidth();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004c  */
    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getEssentialRightWidth() {
        /*
            r5 = this;
            T extends android.view.View r0 = r5.mView
            com.android.systemui.statusbar.phone.PhoneStatusBarView r0 = (com.android.systemui.statusbar.phone.PhoneStatusBarView) r0
            r1 = 2131362194(0x7f0a0192, float:1.8344162E38)
            android.view.View r0 = r0.requireViewById(r1)
            com.android.systemui.battery.BatteryMeterView r0 = (com.android.systemui.battery.BatteryMeterView) r0
            boolean r1 = com.android.systemui.BasicRune.STATUS_REAL_TIME_NETWORK_SPEED
            r2 = 0
            if (r1 == 0) goto L2e
            T extends android.view.View r1 = r5.mView
            com.android.systemui.statusbar.phone.PhoneStatusBarView r1 = (com.android.systemui.statusbar.phone.PhoneStatusBarView) r1
            if (r1 == 0) goto L20
            r3 = 2131363913(0x7f0a0849, float:1.8347648E38)
            android.view.View r1 = r1.findViewById(r3)
            goto L21
        L20:
            r1 = 0
        L21:
            if (r1 == 0) goto L2e
            int r3 = r1.getVisibility()
            if (r3 != 0) goto L2e
            int r1 = r1.getMeasuredWidth()
            goto L2f
        L2e:
            r1 = r2
        L2f:
            dagger.Lazy r5 = r5.samsungExtLazy
            java.lang.Object r3 = r5.get()
            com.android.systemui.statusbar.phone.PhoneStatusBarViewControllerExt r3 = (com.android.systemui.statusbar.phone.PhoneStatusBarViewControllerExt) r3
            com.android.systemui.statusbar.phone.PhoneStatusBarClockManager r3 = r3.phoneStatusBarClockManager
            com.android.systemui.statusbar.phone.PhoneStatusBarClockManager$POSITION r3 = r3.mClockPosition
            com.android.systemui.statusbar.phone.PhoneStatusBarClockManager$POSITION r4 = com.android.systemui.statusbar.phone.PhoneStatusBarClockManager.POSITION.RIGHT
            if (r3 != r4) goto L4c
            java.lang.Object r3 = r5.get()
            com.android.systemui.statusbar.phone.PhoneStatusBarViewControllerExt r3 = (com.android.systemui.statusbar.phone.PhoneStatusBarViewControllerExt) r3
            com.android.systemui.statusbar.phone.PhoneStatusBarClockManager r3 = r3.phoneStatusBarClockManager
            int r3 = r3.getClockWidth()
            goto L4d
        L4c:
            r3 = r2
        L4d:
            java.lang.Object r5 = r5.get()
            com.android.systemui.statusbar.phone.PhoneStatusBarViewControllerExt r5 = (com.android.systemui.statusbar.phone.PhoneStatusBarViewControllerExt) r5
            com.android.systemui.statusbar.phone.TwoPhoneModeIconController r5 = r5.twoPhoneModeIconController
            boolean r4 = r5.featureEnabled()
            if (r4 == 0) goto L5f
            int r2 = r5.getViewWidth()
        L5f:
            int r5 = r0.getMeasuredWidth()
            int r5 = r5 + r1
            int r5 = r5 + r3
            int r5 = r5 + r2
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.PhoneStatusBarViewController.getEssentialRightWidth():int");
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

    public final QuickPanelLogger getQuickPanelLogger() {
        return (QuickPanelLogger) this.quickPanelLogger$delegate.getValue();
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
        ((PhoneStatusBarViewControllerExt) this.samsungExtLazy.get()).statusIconContainerController.init();
    }

    public final void onTouch$1(MotionEvent motionEvent) {
        CommandQueue commandQueue = (CommandQueue) this.commandQueue$delegate.getValue();
        if (commandQueue != null) {
            if (commandQueue.panelsEnabled()) {
                commandQueue = null;
            }
            if (commandQueue != null) {
                QuickPanelLogger quickPanelLogger = getQuickPanelLogger();
                if (quickPanelLogger != null) {
                    quickPanelLogger.quickPanelLoggerHelper.onTouchEventLogger.log(motionEvent, quickPanelLogger.tag, "!panelsEnabled()");
                    return;
                }
                return;
            }
        }
        if (this.statusBarWindowStateController.windowState == 0) {
            ((CentralSurfacesImpl) this.centralSurfaces).setInteracting(1, !(motionEvent.getAction() == 1 || motionEvent.getAction() == 3) || this.shadeController.isExpandedVisible());
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        PhoneStatusBarView phoneStatusBarView;
        final ViewGroup viewGroup;
        PhoneStatusBarView phoneStatusBarView2;
        BatteryMeterView batteryMeterView;
        PhoneStatusBarView phoneStatusBarView3;
        final PhoneStatusBarViewControllerExt phoneStatusBarViewControllerExt = (PhoneStatusBarViewControllerExt) this.samsungExtLazy.get();
        PhoneStatusBarView phoneStatusBarView4 = (PhoneStatusBarView) this.mView;
        phoneStatusBarViewControllerExt.getClass();
        Log.d("PhoneStatusBarViewControllerExt", "onViewAttached()");
        phoneStatusBarViewControllerExt.phoneStatusBarView = phoneStatusBarView4;
        phoneStatusBarViewControllerExt.phoneStatusBarViewController = this;
        IndicatorGardenPresenter indicatorGardenPresenter = phoneStatusBarViewControllerExt.indicatorGardenPresenter;
        indicatorGardenPresenter.updateGardenWithNewModel(this);
        PhoneStatusBarView phoneStatusBarView5 = phoneStatusBarViewControllerExt.phoneStatusBarView;
        if (phoneStatusBarView5 != null) {
            phoneStatusBarView5.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewControllerExt$onViewAttached$1$1
                @Override // android.view.View.OnApplyWindowInsetsListener
                public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                    PhoneStatusBarViewControllerExt phoneStatusBarViewControllerExt2 = PhoneStatusBarViewControllerExt.this;
                    if (((KeyguardStateControllerImpl) phoneStatusBarViewControllerExt2.keyguardStateController).mShowing) {
                        return windowInsets;
                    }
                    phoneStatusBarViewControllerExt2.indicatorGardenPresenter.onGardenApplyWindowInsets(this);
                    PhoneStatusBarViewControllerExt phoneStatusBarViewControllerExt3 = PhoneStatusBarViewControllerExt.this;
                    IndicatorGardenPresenter indicatorGardenPresenter2 = phoneStatusBarViewControllerExt3.indicatorGardenPresenter;
                    ((PrivacyDotViewControllerImpl) phoneStatusBarViewControllerExt3.privacyDotViewController).updateGarden(indicatorGardenPresenter2.gardenAlgorithm.calculateLeftPadding(), indicatorGardenPresenter2.gardenAlgorithm.calculateRightPadding(), windowInsets);
                    return view.onApplyWindowInsets(windowInsets);
                }
            });
            phoneStatusBarView5.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewControllerExt$onViewAttached$1$2
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    IndicatorGardenPresenter indicatorGardenPresenter2 = PhoneStatusBarViewControllerExt.this.indicatorGardenPresenter;
                    PhoneStatusBarViewController phoneStatusBarViewController = this;
                    indicatorGardenPresenter2.getClass();
                    indicatorGardenPresenter2.mainHandler.post(new IndicatorGardenPresenter$onGardenOnLayout$1(indicatorGardenPresenter2, phoneStatusBarViewController));
                }
            });
            ((PrivacyDotViewControllerImpl) phoneStatusBarViewControllerExt.privacyDotViewController).updateGarden(indicatorGardenPresenter.gardenAlgorithm.calculateLeftPadding(), indicatorGardenPresenter.gardenAlgorithm.calculateRightPadding(), phoneStatusBarView5.getRootWindowInsets());
        }
        ((ConfigurationControllerImpl) phoneStatusBarViewControllerExt.configurationController).addCallback(phoneStatusBarViewControllerExt.configurationListener);
        boolean z = BasicRune.STATUS_REAL_TIME_NETWORK_SPEED;
        if (z && (phoneStatusBarView3 = phoneStatusBarViewControllerExt.phoneStatusBarView) != null) {
            ViewGroup viewGroup2 = (ViewGroup) phoneStatusBarView3.findViewById(R.id.system_icons);
            NetspeedView netspeedView = (NetspeedView) LayoutInflater.from(phoneStatusBarView3.getContext()).inflate(R.layout.samsung_status_bar_network_speed_view, (ViewGroup) null);
            phoneStatusBarViewControllerExt.netspeedView = netspeedView;
            if (netspeedView != null) {
                if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT) {
                    netspeedView.mInStatusBar = true;
                }
                viewGroup2.addView(netspeedView, 0);
                phoneStatusBarViewControllerExt.darkIconDispatcher.addDarkReceiver(netspeedView);
            }
        }
        PhoneStatusBarView phoneStatusBarView6 = phoneStatusBarViewControllerExt.phoneStatusBarView;
        if (phoneStatusBarView6 != null && (batteryMeterView = (BatteryMeterView) phoneStatusBarView6.findViewById(R.id.battery)) != null) {
            batteryMeterView.setTag("PhoneStatusBarViewControllerExt");
            PhoneStatusBarViewControllerExt$setUpBatteryView$1$1$1 phoneStatusBarViewControllerExt$setUpBatteryView$1$1$1 = new PhoneStatusBarViewControllerExt$setUpBatteryView$1$1$1(batteryMeterView);
            phoneStatusBarViewControllerExt.samsungStatusBarGrayIconHelper.grayIconChangedCallback = phoneStatusBarViewControllerExt$setUpBatteryView$1$1$1;
            BatteryMeterView batteryMeterView2 = phoneStatusBarViewControllerExt$setUpBatteryView$1$1$1.$it;
            batteryMeterView2.mIsGrayColor = false;
            batteryMeterView2.mSamsungDrawable.shouldShowGrayIcon = false;
        }
        PhoneStatusBarView phoneStatusBarView7 = phoneStatusBarViewControllerExt.phoneStatusBarView;
        if (phoneStatusBarView7 != null) {
            KnoxStatusBarControlBinder.bind(phoneStatusBarViewControllerExt.knoxStateBarControlViewModel, phoneStatusBarView7);
        }
        IndicatorMarqueeGardener indicatorMarqueeGardener = phoneStatusBarViewControllerExt.indicatorMarqueeGardener;
        indicatorMarqueeGardener.wakefulnessLifecycle.addObserver(indicatorMarqueeGardener.wakefulnessLifecycleObserver);
        indicatorMarqueeGardener.updateMarqueeValues();
        phoneStatusBarViewControllerExt.dumpManager.registerNormalDumpable("PhoneStatusBarViewControllerExt", phoneStatusBarViewControllerExt);
        if (z) {
            ((NetspeedViewController) phoneStatusBarViewControllerExt.netspeedViewControllerLazy.get()).init();
        }
        PhoneStatusBarClockManager phoneStatusBarClockManager = phoneStatusBarViewControllerExt.phoneStatusBarClockManager;
        phoneStatusBarClockManager.mIndicatorGarden = this;
        ViewGroup viewGroup3 = phoneStatusBarClockManager.mGrandParentView;
        if (viewGroup3 != null) {
            phoneStatusBarClockManager.mLeftContainer = (ViewGroup) viewGroup3.findViewById(R.id.left_clock_container);
            phoneStatusBarClockManager.mMiddleContainer = (ViewGroup) phoneStatusBarClockManager.mGrandParentView.findViewById(R.id.middle_clock_container);
            phoneStatusBarClockManager.mRightContainer = (ViewGroup) phoneStatusBarClockManager.mGrandParentView.findViewById(R.id.right_clock_container);
            ((SlimIndicatorViewMediatorImpl) phoneStatusBarClockManager.mSlimIndicatorViewMediator).registerSubscriber("[QuickStar]PhoneStatusBarClockManager", phoneStatusBarClockManager);
            phoneStatusBarClockManager.updateResources();
        } else {
            Log.e("[QuickStar]PhoneStatusBarClockManager", "onAttachedToWindow(), mGrandParentView is null");
        }
        TwoPhoneModeIconController twoPhoneModeIconController = phoneStatusBarViewControllerExt.twoPhoneModeIconController;
        if (twoPhoneModeIconController.featureEnabled() && (phoneStatusBarView2 = phoneStatusBarViewControllerExt.phoneStatusBarView) != null) {
            twoPhoneModeIconController.onViewAttached((ViewGroup) phoneStatusBarView2.requireViewById(R.id.status_bar_end_side_content));
        }
        if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT) {
            phoneStatusBarViewControllerExt.statusIconContainerController.view.mSidelingCutoutContainerInfo = new SidelingCutoutContainerInfo() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewControllerExt$onViewAttached$3
                @Override // com.android.systemui.statusbar.phone.SidelingCutoutContainerInfo
                public final int getRightSideAvailableWidth(Rect rect) {
                    PhoneStatusBarViewControllerExt phoneStatusBarViewControllerExt2 = PhoneStatusBarViewControllerExt.this;
                    PhoneStatusBarView phoneStatusBarView8 = phoneStatusBarViewControllerExt2.phoneStatusBarView;
                    if (phoneStatusBarView8 == null) {
                        return 0;
                    }
                    BatteryMeterView batteryMeterView3 = (BatteryMeterView) phoneStatusBarView8.requireViewById(R.id.battery);
                    int paddingEnd = ((ViewGroup) phoneStatusBarView8.requireViewById(R.id.statusIcons)).getPaddingEnd();
                    int dimensionPixelSize = phoneStatusBarView8.getResources().getDimensionPixelSize(17106384);
                    int width = phoneStatusBarView8.getResources().getConfiguration().windowConfiguration.getBounds().width();
                    int dimensionPixelSize2 = phoneStatusBarView8.getResources().getDimensionPixelSize(R.dimen.indicator_marquee_max_shift) + rect.right;
                    TwoPhoneModeIconController twoPhoneModeIconController2 = phoneStatusBarViewControllerExt2.twoPhoneModeIconController;
                    int measuredWidth = batteryMeterView3.getMeasuredWidth() + dimensionPixelSize + paddingEnd + (twoPhoneModeIconController2.featureEnabled() ? twoPhoneModeIconController2.getViewWidth() : 0);
                    PhoneStatusBarClockManager phoneStatusBarClockManager2 = phoneStatusBarViewControllerExt2.phoneStatusBarClockManager;
                    if (phoneStatusBarClockManager2.mClockPosition == PhoneStatusBarClockManager.POSITION.RIGHT) {
                        measuredWidth += phoneStatusBarClockManager2.getClockWidth();
                    }
                    return (width - measuredWidth) - dimensionPixelSize2;
                }
            };
            PhoneStatusBarView phoneStatusBarView8 = phoneStatusBarViewControllerExt.phoneStatusBarView;
            if (phoneStatusBarView8 != null && (viewGroup = (ViewGroup) phoneStatusBarView8.requireViewById(R.id.system_icons)) != null) {
                viewGroup.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewControllerExt$addOnLayoutChangeListenerForJumpingCutout$1$1
                    @Override // android.view.View.OnLayoutChangeListener
                    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                        StatusIconContainer statusIconContainer;
                        if (PhoneStatusBarViewControllerExt.this.indicatorCutoutUtil.getDisplayCutoutAreaToExclude() == null || (statusIconContainer = (StatusIconContainer) viewGroup.findViewById(R.id.statusIcons)) == null) {
                            return;
                        }
                        if (statusIconContainer.getWidth() != statusIconContainer.getMeasuredWidth() || statusIconContainer.getX() < 0.0f) {
                            statusIconContainer.requestLayout();
                        }
                    }
                });
            }
        }
        if (DeviceState.isTestModeIndicatorGarden() && (phoneStatusBarView = phoneStatusBarViewControllerExt.phoneStatusBarView) != null) {
            ViewGroup viewGroup4 = (ViewGroup) phoneStatusBarView.requireViewById(R.id.status_bar_left_container);
            if (viewGroup4 != null) {
                viewGroup4.setBackgroundColor(587137024);
            }
            ViewGroup viewGroup5 = (ViewGroup) phoneStatusBarView.requireViewById(R.id.system_icon_area);
            if (viewGroup5 != null) {
                viewGroup5.setBackgroundColor(570490624);
            }
            phoneStatusBarView.setBackgroundColor(570425599);
        }
        View requireViewById = ((PhoneStatusBarView) this.mView).requireViewById(R.id.system_icons);
        this.statusContainer = requireViewById;
        if (requireViewById == null) {
            requireViewById = null;
        }
        requireViewById.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$onViewAttached$1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getSource() != 8194) {
                    return false;
                }
                if (motionEvent.getAction() == 1) {
                    view.performClick();
                    SecPanelSplitHelper.Companion.getClass();
                    if (SecPanelSplitHelper.isEnabled) {
                        ((BaseShadeControllerImpl) PhoneStatusBarViewController.this.shadeController).animateExpandQs();
                    } else {
                        ((BaseShadeControllerImpl) PhoneStatusBarViewController.this.shadeController).animateExpandShade();
                    }
                }
                return true;
            }
        });
        this.clock = (QSClockIndicatorView) ((PhoneStatusBarView) this.mView).requireViewById(R.id.clock);
        BatteryMeterView batteryMeterView3 = (BatteryMeterView) ((PhoneStatusBarView) this.mView).requireViewById(R.id.battery);
        this.battery = batteryMeterView3;
        if (batteryMeterView3 == null) {
            batteryMeterView3 = null;
        }
        DarkIconDispatcher darkIconDispatcher = this.darkIconDispatcher;
        darkIconDispatcher.addDarkReceiver(batteryMeterView3);
        QSClockIndicatorView qSClockIndicatorView = this.clock;
        darkIconDispatcher.addDarkReceiver(qSClockIndicatorView != null ? qSClockIndicatorView : null);
        ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider = this.progressProvider;
        if (scopedUnfoldTransitionProgressProvider != null) {
            scopedUnfoldTransitionProgressProvider.setReadyToHandleTransition(true);
        }
        ((ConfigurationControllerImpl) this.configurationController).addCallback(this.configurationListener);
        View requireViewById2 = ((PhoneStatusBarView) this.mView).requireViewById(R.id.status_bar_start_side_except_heads_up);
        ViewGroup viewGroup6 = (ViewGroup) ((PhoneStatusBarView) this.mView).requireViewById(R.id.status_bar_end_side_content);
        if (this.moveFromCenterAnimationController == null) {
            return;
        }
        final View[] viewArr = {requireViewById2, viewGroup6};
        ((PhoneStatusBarView) this.mView).getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$onViewAttached$2
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                View view;
                StatusBarMoveFromCenterAnimationController statusBarMoveFromCenterAnimationController = PhoneStatusBarViewController.this.moveFromCenterAnimationController;
                View[] viewArr2 = viewArr;
                UnfoldMoveFromCenterAnimator unfoldMoveFromCenterAnimator = statusBarMoveFromCenterAnimationController.moveFromCenterAnimator;
                UnfoldMoveFromCenterAnimator.updateDisplayProperties$default(unfoldMoveFromCenterAnimator);
                for (View view2 : viewArr2) {
                    UnfoldMoveFromCenterAnimator.AnimatedView animatedView = new UnfoldMoveFromCenterAnimator.AnimatedView(new WeakReference(view2), 0.0f, 0.0f, 6, null);
                    unfoldMoveFromCenterAnimator.updateAnimatedView(animatedView, view2);
                    ((ArrayList) unfoldMoveFromCenterAnimator.animatedViews).add(animatedView);
                }
                statusBarMoveFromCenterAnimationController.progressProvider.listeners.add(statusBarMoveFromCenterAnimationController.transitionListener);
                view = ((ViewController) PhoneStatusBarViewController.this).mView;
                ((PhoneStatusBarView) view).getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
        ((PhoneStatusBarView) this.mView).addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$onViewAttached$3
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                if (i3 - i != i7 - i5) {
                    UnfoldMoveFromCenterAnimator unfoldMoveFromCenterAnimator = PhoneStatusBarViewController.this.moveFromCenterAnimationController.moveFromCenterAnimator;
                    UnfoldMoveFromCenterAnimator.updateDisplayProperties$default(unfoldMoveFromCenterAnimator);
                    ArrayList arrayList = (ArrayList) unfoldMoveFromCenterAnimator.animatedViews;
                    int size = arrayList.size();
                    int i9 = 0;
                    while (i9 < size) {
                        Object obj = arrayList.get(i9);
                        i9++;
                        UnfoldMoveFromCenterAnimator.AnimatedView animatedView = (UnfoldMoveFromCenterAnimator.AnimatedView) obj;
                        View view2 = (View) animatedView.view.get();
                        if (view2 != null) {
                            unfoldMoveFromCenterAnimator.updateAnimatedView(animatedView, view2);
                        }
                    }
                    unfoldMoveFromCenterAnimator.onTransitionProgress(unfoldMoveFromCenterAnimator.lastAnimationProgress);
                }
            }
        });
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
        NetspeedView netspeedView;
        PhoneStatusBarViewControllerExt phoneStatusBarViewControllerExt = (PhoneStatusBarViewControllerExt) this.samsungExtLazy.get();
        ((ConfigurationControllerImpl) phoneStatusBarViewControllerExt.configurationController).removeCallback(phoneStatusBarViewControllerExt.configurationListener);
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED && (netspeedView = phoneStatusBarViewControllerExt.netspeedView) != null) {
            phoneStatusBarViewControllerExt.darkIconDispatcher.removeDarkReceiver(netspeedView);
        }
        IndicatorMarqueeGardener indicatorMarqueeGardener = phoneStatusBarViewControllerExt.indicatorMarqueeGardener;
        indicatorMarqueeGardener.wakefulnessLifecycle.removeObserver(indicatorMarqueeGardener.wakefulnessLifecycleObserver);
        phoneStatusBarViewControllerExt.samsungStatusBarGrayIconHelper.grayIconChangedCallback = null;
        phoneStatusBarViewControllerExt.dumpManager.unregisterDumpable("PhoneStatusBarViewControllerExt");
        PhoneStatusBarClockManager phoneStatusBarClockManager = phoneStatusBarViewControllerExt.phoneStatusBarClockManager;
        ((SlimIndicatorViewMediatorImpl) phoneStatusBarClockManager.mSlimIndicatorViewMediator).unregisterSubscriber("[QuickStar]PhoneStatusBarClockManager");
        phoneStatusBarClockManager.mClockPosition = PhoneStatusBarClockManager.POSITION.NONE;
        TwoPhoneModeIconController twoPhoneModeIconController = phoneStatusBarViewControllerExt.twoPhoneModeIconController;
        if (twoPhoneModeIconController.featureEnabled()) {
            twoPhoneModeIconController.onViewDetached();
        }
        phoneStatusBarViewControllerExt.phoneStatusBarView = null;
        BatteryMeterView batteryMeterView = this.battery;
        if (batteryMeterView == null) {
            batteryMeterView = null;
        }
        DarkIconDispatcher darkIconDispatcher = this.darkIconDispatcher;
        darkIconDispatcher.removeDarkReceiver(batteryMeterView);
        QSClockIndicatorView qSClockIndicatorView = this.clock;
        darkIconDispatcher.removeDarkReceiver(qSClockIndicatorView != null ? qSClockIndicatorView : null);
        ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider = this.progressProvider;
        if (scopedUnfoldTransitionProgressProvider != null) {
            scopedUnfoldTransitionProgressProvider.setReadyToHandleTransition(false);
        }
        StatusBarMoveFromCenterAnimationController statusBarMoveFromCenterAnimationController = this.moveFromCenterAnimationController;
        if (statusBarMoveFromCenterAnimationController != null) {
            statusBarMoveFromCenterAnimationController.progressProvider.listeners.remove(statusBarMoveFromCenterAnimationController.transitionListener);
            UnfoldMoveFromCenterAnimator unfoldMoveFromCenterAnimator = statusBarMoveFromCenterAnimationController.moveFromCenterAnimator;
            unfoldMoveFromCenterAnimator.onTransitionProgress(1.0f);
            ((ArrayList) unfoldMoveFromCenterAnimator.animatedViews).clear();
        }
        ((ConfigurationControllerImpl) this.configurationController).removeCallback(this.configurationListener);
    }

    public final boolean sendTouchToView(MotionEvent motionEvent) {
        return ((PhoneStatusBarView) this.mView).dispatchTouchEvent(motionEvent);
    }

    public final void setImportantForAccessibility(int i) {
        ((PhoneStatusBarView) this.mView).setImportantForAccessibility(i);
    }

    public final boolean touchIsWithinView(float f, float f2) {
        return this.viewUtil.touchIsWithinView(this.mView, f, f2);
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final void updateGarden(IndicatorGardenModel indicatorGardenModel, IndicatorGardenInputProperties indicatorGardenInputProperties) {
        updateGarden(indicatorGardenModel, indicatorGardenInputProperties);
    }

    /* JADX WARN: Type inference failed for: r1v19, types: [com.android.systemui.statusbar.phone.PhoneStatusBarViewController$gardener$1] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.statusbar.phone.PhoneStatusBarViewController$configurationListener$1] */
    private PhoneStatusBarViewController(PhoneStatusBarView phoneStatusBarView, dagger.Lazy lazy, ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider, CentralSurfaces centralSurfaces, StatusBarWindowStateController statusBarWindowStateController, ShadeController shadeController, ShadeViewController shadeViewController, PanelExpansionInteractor panelExpansionInteractor, Provider provider, Provider provider2, ShadeLogger shadeLogger, StatusBarMoveFromCenterAnimationController statusBarMoveFromCenterAnimationController, StatusBarUserChipViewModel statusBarUserChipViewModel, ViewUtil viewUtil, ConfigurationController configurationController, StatusOverlayHoverListenerFactory statusOverlayHoverListenerFactory, DarkIconDispatcher darkIconDispatcher, StatusBarContentInsetsProviderStore statusBarContentInsetsProviderStore, dagger.Lazy lazy2) {
        super(phoneStatusBarView);
        this.samsungExtLazy = lazy;
        this.progressProvider = scopedUnfoldTransitionProgressProvider;
        this.centralSurfaces = centralSurfaces;
        this.statusBarWindowStateController = statusBarWindowStateController;
        this.shadeController = shadeController;
        this.shadeViewController = shadeViewController;
        this.shadeLogger = shadeLogger;
        this.moveFromCenterAnimationController = statusBarMoveFromCenterAnimationController;
        this.viewUtil = viewUtil;
        this.configurationController = configurationController;
        this.darkIconDispatcher = darkIconDispatcher;
        this.lazyStatusBarShadeDisplayPolicy = lazy2;
        final int i = 0;
        this.quickPanelLogger$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        return new QuickPanelLogger("PSBVC");
                    default:
                        return (CommandQueue) Dependency.sDependency.getDependencyInner(CommandQueue.class);
                }
            }
        });
        final int i2 = 1;
        this.commandQueue$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        return new QuickPanelLogger("PSBVC");
                    default:
                        return (CommandQueue) Dependency.sDependency.getDependencyInner(CommandQueue.class);
                }
            }
        });
        new View.OnTouchListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$iconsOnTouchListener$1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getSource() != 8194) {
                    return false;
                }
                if (motionEvent.getAction() == 1) {
                    PhoneStatusBarViewController.access$dispatchEventToShadeDisplayPolicy(PhoneStatusBarViewController.this, motionEvent);
                    view.performClick();
                    ((BaseShadeControllerImpl) PhoneStatusBarViewController.this.shadeController).animateExpandShade();
                }
                return true;
            }
        };
        this.configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$configurationListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
            }
        };
        ((PhoneStatusBarView) this.mView).mTouchEventHandler = new PhoneStatusBarViewTouchHandler();
        final StatusBarContentInsetsProvider statusBarContentInsetsProvider = (StatusBarContentInsetsProvider) statusBarContentInsetsProviderStore.forDisplay(getContext().getDisplayId());
        if (statusBarContentInsetsProvider != null) {
            PhoneStatusBarView phoneStatusBarView2 = (PhoneStatusBarView) this.mView;
            new Object(statusBarContentInsetsProvider) { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$1$1
            };
            phoneStatusBarView2.getClass();
            PhoneStatusBarView phoneStatusBarView3 = (PhoneStatusBarView) this.mView;
            new Object(statusBarContentInsetsProvider) { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$1$2
            };
            phoneStatusBarView3.getClass();
        }
        StatusBarUserChipViewBinder.bind((StatusBarUserSwitcherContainer) ((PhoneStatusBarView) this.mView).findViewById(R.id.user_switcher_container), statusBarUserChipViewModel, null);
        this.gardener = new IndicatorBasicGardener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$gardener$1
            {
                super(PhoneStatusBarViewController.this, "PhoneStatusBarViewController");
            }

            @Override // com.android.systemui.statusbar.phone.IndicatorBasicGardener
            public final ViewGroup.MarginLayoutParams getCameraTopMarginContainerMarginLayoutParams() {
                View view;
                view = ((ViewController) PhoneStatusBarViewController.this).mView;
                View findViewById = ((PhoneStatusBarView) view).findViewById(R.id.status_bar_contents);
                return (ViewGroup.MarginLayoutParams) (findViewById != null ? findViewById.getLayoutParams() : null);
            }

            @Override // com.android.systemui.statusbar.phone.IndicatorBasicGardener
            public final boolean needToUpdatePaddings(IndicatorGardenModel indicatorGardenModel) {
                if (super.needToUpdatePaddings(indicatorGardenModel)) {
                    return true;
                }
                IndicatorMarqueeGardener indicatorMarqueeGardener = ((PhoneStatusBarViewControllerExt) PhoneStatusBarViewController.this.samsungExtLazy.get()).indicatorMarqueeGardener;
                if (!indicatorMarqueeGardener.hasSomethingChanged) {
                    return false;
                }
                indicatorMarqueeGardener.marqueeModel.getClass();
                indicatorMarqueeGardener.hasSomethingChanged = false;
                return true;
            }

            @Override // com.android.systemui.statusbar.phone.IndicatorBasicGardener
            public final void updateSidePadding(int i3, int i4) {
                PhoneStatusBarViewController phoneStatusBarViewController = PhoneStatusBarViewController.this;
                PhoneStatusBarViewControllerExt phoneStatusBarViewControllerExt = (PhoneStatusBarViewControllerExt) phoneStatusBarViewController.samsungExtLazy.get();
                ViewGroup viewGroup = phoneStatusBarViewController.sidePaddingContainer;
                IndicatorMarqueeGardener.MarqueeModel marqueeModel = phoneStatusBarViewControllerExt.indicatorMarqueeGardener.marqueeModel;
                int i5 = i3 + marqueeModel.shiftLeft;
                int i6 = marqueeModel.shiftTop;
                int i7 = i4 + marqueeModel.shiftRight;
                int i8 = marqueeModel.shiftBottom;
                if (viewGroup != null) {
                    viewGroup.setPadding(i5, i6, i7, i8);
                }
            }
        };
        T t = this.mView;
        this.heightContainer = (ViewGroup) t;
        this.sidePaddingContainer = (ViewGroup) ((PhoneStatusBarView) t).findViewById(R.id.status_bar_contents);
        this.leftContainer = (IndicatorGardenContainer) ((PhoneStatusBarView) this.mView).findViewById(R.id.status_bar_left_container);
        this.centerContainer = (IndicatorGardenContainer) ((PhoneStatusBarView) this.mView).findViewById(R.id.status_bar_center_container);
        this.rightContainer = (IndicatorGardenContainer) ((PhoneStatusBarView) this.mView).findViewById(R.id.system_icon_area);
    }
}
