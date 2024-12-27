package com.android.systemui.statusbar.phone;

import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.log.QuickPanelLogger;
import com.android.systemui.shade.BaseShadeControllerImpl;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeLogger;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.shared.animation.UnfoldMoveFromCenterAnimator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import com.android.systemui.statusbar.events.ViewState;
import com.android.systemui.statusbar.phone.IndicatorMarqueeGardener;
import com.android.systemui.statusbar.phone.PhoneStatusBarClockManager;
import com.android.systemui.statusbar.phone.knox.ui.binder.KnoxStatusBarControlBinder;
import com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarControlViewModel;
import com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarViewControl;
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserSwitcherContainer;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.NetspeedViewController;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
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
import javax.inject.Provider;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.StringCompanionObject;

public final class PhoneStatusBarViewController extends ViewController implements IndicatorGarden, Dumpable {
    public final IndicatorGardenContainer centerContainer;
    public final CentralSurfaces centralSurfaces;
    public final Lazy commandQueue$delegate;
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
    public final PhoneStatusBarClockManager phoneStatusBarClockManager;
    public final PrivacyDotViewController privacyDotViewController;
    public final ScopedUnfoldTransitionProgressProvider progressProvider;
    public final Lazy quickPanelLogger$delegate;
    public final IndicatorGardenContainer rightContainer;
    public final SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper;
    public final ShadeController shadeController;
    public final ShadeLogger shadeLogger;
    public final ShadeViewController shadeViewController;
    public final ViewGroup sidePaddingContainer;
    public final StatusBarWindowStateController statusBarWindowStateController;
    public View statusContainer;
    public final StatusIconContainerController statusIconContainerController;
    public final TwoPhoneModeIconController twoPhoneModeIconController;
    public final IndicatorGardenViewTreeLogHelper viewTreeLogHelper;
    public final ViewUtil viewUtil;

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
        public final PanelExpansionInteractor panelExpansionInteractor;
        public final PhoneStatusBarClockManager phoneStatusBarClockManager;
        public final PrivacyDotViewController privacyDotViewController;
        public final Optional progressProvider;
        public final SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper;
        public final ShadeController shadeController;
        public final ShadeLogger shadeLogger;
        public final ShadeViewController shadeViewController;
        public final StatusBarWindowStateController statusBarWindowStateController;
        public final StatusIconContainerController statusIconContainerController;
        public final StatusOverlayHoverListenerFactory statusOverlayHoverListenerFactory;
        public final TwoPhoneModeIconController twoPhoneModeIconController;
        public final StatusBarUserChipViewModel userChipViewModel;
        public final IndicatorGardenViewTreeLogHelper viewTreeLogHelper;
        public final ViewUtil viewUtil;
        public final Provider windowRootView;

        public Factory(Optional<SysUIUnfoldComponent> optional, Optional<ScopedUnfoldTransitionProgressProvider> optional2, FeatureFlags featureFlags, StatusBarUserChipViewModel statusBarUserChipViewModel, CentralSurfaces centralSurfaces, StatusBarWindowStateController statusBarWindowStateController, ShadeController shadeController, ShadeViewController shadeViewController, PanelExpansionInteractor panelExpansionInteractor, Provider provider, ShadeLogger shadeLogger, ViewUtil viewUtil, ConfigurationController configurationController, StatusOverlayHoverListenerFactory statusOverlayHoverListenerFactory, IndicatorGardenPresenter indicatorGardenPresenter, PrivacyDotViewController privacyDotViewController, DumpManager dumpManager, IndicatorGardenViewTreeLogHelper indicatorGardenViewTreeLogHelper, IndicatorMarqueeGardener indicatorMarqueeGardener, StatusIconContainerController statusIconContainerController, IndicatorCutoutUtil indicatorCutoutUtil, TwoPhoneModeIconController twoPhoneModeIconController, PhoneStatusBarClockManager phoneStatusBarClockManager, KnoxStatusBarControlViewModel knoxStatusBarControlViewModel, NetspeedViewController netspeedViewController, SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper) {
            this.progressProvider = optional2;
            this.featureFlags = featureFlags;
            this.userChipViewModel = statusBarUserChipViewModel;
            this.centralSurfaces = centralSurfaces;
            this.statusBarWindowStateController = statusBarWindowStateController;
            this.shadeController = shadeController;
            this.shadeViewController = shadeViewController;
            this.panelExpansionInteractor = panelExpansionInteractor;
            this.windowRootView = provider;
            this.shadeLogger = shadeLogger;
            this.viewUtil = viewUtil;
            this.configurationController = configurationController;
            this.statusOverlayHoverListenerFactory = statusOverlayHoverListenerFactory;
            this.indicatorGardenPresenter = indicatorGardenPresenter;
            this.privacyDotViewController = privacyDotViewController;
            this.dumpManager = dumpManager;
            this.viewTreeLogHelper = indicatorGardenViewTreeLogHelper;
            this.indicatorMarqueeGardener = indicatorMarqueeGardener;
            this.statusIconContainerController = statusIconContainerController;
            this.indicatorCutoutUtil = indicatorCutoutUtil;
            this.twoPhoneModeIconController = twoPhoneModeIconController;
            this.phoneStatusBarClockManager = phoneStatusBarClockManager;
            this.knoxStateBarViewModel = knoxStatusBarControlViewModel;
            this.netspeedViewController = netspeedViewController;
            this.samsungStatusBarGrayIconHelper = samsungStatusBarGrayIconHelper;
        }
    }

    public final class PhoneStatusBarViewTouchHandler implements Gefingerpoken {
        public PhoneStatusBarViewTouchHandler() {
        }

        @Override // com.android.systemui.Gefingerpoken
        public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            PhoneStatusBarViewController phoneStatusBarViewController = PhoneStatusBarViewController.this;
            QuickPanelLogger quickPanelLogger = phoneStatusBarViewController.getQuickPanelLogger();
            if (quickPanelLogger != null) {
                quickPanelLogger.quickPanelLoggerHelper.onInterceptTouchEventLogger.log(motionEvent, quickPanelLogger.tag, "");
            }
            phoneStatusBarViewController.onTouch(motionEvent);
            return false;
        }

        @Override // com.android.systemui.Gefingerpoken
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            PhoneStatusBarViewController phoneStatusBarViewController = PhoneStatusBarViewController.this;
            QuickPanelLogger quickPanelLogger = phoneStatusBarViewController.getQuickPanelLogger();
            if (quickPanelLogger != null) {
                quickPanelLogger.quickPanelLoggerHelper.onTouchEventLogger.log(motionEvent, quickPanelLogger.tag, "");
            }
            phoneStatusBarViewController.onTouch(motionEvent);
            if (!((CentralSurfacesImpl) phoneStatusBarViewController.centralSurfaces).mCommandQueue.panelsEnabled()) {
                if (motionEvent.getAction() == 0) {
                    int i = StringCompanionObject.$r8$clinit;
                    String.format(HeightInLinesModifierKt$$ExternalSyntheticOutline0.m((int) motionEvent.getX(), (int) motionEvent.getY(), "onTouchForwardedFromStatusBar: panel disabled, ignoring touch at (", ",", ")"), Arrays.copyOf(new Object[0], 0));
                }
                QuickPanelLogger quickPanelLogger2 = phoneStatusBarViewController.getQuickPanelLogger();
                if (quickPanelLogger2 != null) {
                    quickPanelLogger2.onTouchEvent(motionEvent, "!centralSurfaces.commandQueuePanelsEnabled", false);
                }
                return false;
            }
            Flags.sceneContainer();
            int action = motionEvent.getAction();
            ShadeViewController shadeViewController = phoneStatusBarViewController.shadeViewController;
            if (action != 0 || shadeViewController.isViewEnabled()) {
                return shadeViewController.handleExternalTouch(motionEvent);
            }
            phoneStatusBarViewController.shadeLogger.logMotionEvent(motionEvent, "onTouchForwardedFromStatusBar: panel view disabled");
            QuickPanelLogger quickPanelLogger3 = phoneStatusBarViewController.getQuickPanelLogger();
            if (quickPanelLogger3 != null) {
                quickPanelLogger3.onTouchEvent(motionEvent, "!shadeViewController.isViewEnabled", true);
            }
            return true;
        }
    }

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

    public /* synthetic */ PhoneStatusBarViewController(PhoneStatusBarView phoneStatusBarView, ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider, CentralSurfaces centralSurfaces, StatusBarWindowStateController statusBarWindowStateController, ShadeController shadeController, ShadeViewController shadeViewController, PanelExpansionInteractor panelExpansionInteractor, Provider provider, ShadeLogger shadeLogger, StatusBarMoveFromCenterAnimationController statusBarMoveFromCenterAnimationController, StatusBarUserChipViewModel statusBarUserChipViewModel, ViewUtil viewUtil, ConfigurationController configurationController, StatusOverlayHoverListenerFactory statusOverlayHoverListenerFactory, IndicatorGardenPresenter indicatorGardenPresenter, PrivacyDotViewController privacyDotViewController, DumpManager dumpManager, IndicatorGardenViewTreeLogHelper indicatorGardenViewTreeLogHelper, IndicatorMarqueeGardener indicatorMarqueeGardener, StatusIconContainerController statusIconContainerController, IndicatorCutoutUtil indicatorCutoutUtil, TwoPhoneModeIconController twoPhoneModeIconController, PhoneStatusBarClockManager phoneStatusBarClockManager, KnoxStatusBarControlViewModel knoxStatusBarControlViewModel, NetspeedViewController netspeedViewController, SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper, DefaultConstructorMarker defaultConstructorMarker) {
        this(phoneStatusBarView, scopedUnfoldTransitionProgressProvider, centralSurfaces, statusBarWindowStateController, shadeController, shadeViewController, panelExpansionInteractor, provider, shadeLogger, statusBarMoveFromCenterAnimationController, statusBarUserChipViewModel, viewUtil, configurationController, statusOverlayHoverListenerFactory, indicatorGardenPresenter, privacyDotViewController, dumpManager, indicatorGardenViewTreeLogHelper, indicatorMarqueeGardener, statusIconContainerController, indicatorCutoutUtil, twoPhoneModeIconController, phoneStatusBarClockManager, knoxStatusBarControlViewModel, netspeedViewController, samsungStatusBarGrayIconHelper);
    }

    public static final int access$getCutoutRightSideAvailableWidth(PhoneStatusBarViewController phoneStatusBarViewController, Rect rect) {
        BatteryMeterView batteryMeterView = (BatteryMeterView) ((PhoneStatusBarView) phoneStatusBarViewController.mView).requireViewById(R.id.battery);
        int paddingEnd = ((ViewGroup) ((PhoneStatusBarView) phoneStatusBarViewController.mView).requireViewById(R.id.statusIcons)).getPaddingEnd();
        int dimensionPixelSize = phoneStatusBarViewController.getResources().getDimensionPixelSize(17106292);
        int width = phoneStatusBarViewController.getResources().getConfiguration().windowConfiguration.getBounds().width();
        int dimensionPixelSize2 = phoneStatusBarViewController.getResources().getDimensionPixelSize(R.dimen.indicator_marquee_max_shift) + rect.right;
        TwoPhoneModeIconController twoPhoneModeIconController = phoneStatusBarViewController.twoPhoneModeIconController;
        int measuredWidth = batteryMeterView.getMeasuredWidth() + dimensionPixelSize + paddingEnd + ((!twoPhoneModeIconController.featureEnabled() || twoPhoneModeIconController.getViewWidth() <= 0) ? 0 : twoPhoneModeIconController.getViewWidth());
        PhoneStatusBarClockManager phoneStatusBarClockManager = phoneStatusBarViewController.phoneStatusBarClockManager;
        if (phoneStatusBarClockManager.mClockPosition == PhoneStatusBarClockManager.POSITION.RIGHT) {
            measuredWidth += phoneStatusBarClockManager.getClockWidth();
        }
        return (width - measuredWidth) - dimensionPixelSize2;
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
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("  height:", indicatorGardenModel.totalHeight, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  hasCameraTopMargin:", indicatorGardenModel.hasCameraTopMargin, printWriter);
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("  cameraTopMargin:", indicatorGardenModel.cameraTopMargin, printWriter);
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("  statusBarContentsHeight=", indicatorGardenModel.totalHeight - indicatorGardenModel.cameraTopMargin, printWriter);
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("  leftPadding:", indicatorGardenModel.paddingLeft, printWriter);
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("  rightPadding:", indicatorGardenModel.paddingRight, printWriter);
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("  leftContainer:", indicatorGardenModel.maxWidthLeftContainer, printWriter);
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("  centerContainer:", indicatorGardenModel.maxWidthCenterContainer, printWriter);
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
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("    statusBarHidden=", knoxStatusBarControlViewModel.statusBarHidden.$$delegate_0.getValue(), printWriter);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("    statusBarIconsEnabled=", knoxStatusBarControlViewModel.statusBarIconsEnabled.$$delegate_0.getValue(), printWriter);
        printWriter.println("    knoxStatusBarCustomText=" + knoxStatusBarControlViewModel.knoxStatusBarCustomText.$$delegate_0.getValue());
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

    /* JADX WARN: Removed duplicated region for block: B:12:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x003c  */
    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getEssentialRightWidth() {
        /*
            r6 = this;
            T extends android.view.View r0 = r6.mView
            com.android.systemui.statusbar.phone.PhoneStatusBarView r0 = (com.android.systemui.statusbar.phone.PhoneStatusBarView) r0
            r1 = 2131362145(0x7f0a0161, float:1.8344062E38)
            android.view.View r0 = r0.requireViewById(r1)
            com.android.systemui.battery.BatteryMeterView r0 = (com.android.systemui.battery.BatteryMeterView) r0
            boolean r1 = com.android.systemui.BasicRune.STATUS_REAL_TIME_NETWORK_SPEED
            r2 = 0
            if (r1 == 0) goto L2e
            T extends android.view.View r1 = r6.mView
            com.android.systemui.statusbar.phone.PhoneStatusBarView r1 = (com.android.systemui.statusbar.phone.PhoneStatusBarView) r1
            if (r1 == 0) goto L20
            r3 = 2131363771(0x7f0a07bb, float:1.834736E38)
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
            com.android.systemui.statusbar.phone.PhoneStatusBarClockManager r3 = r6.phoneStatusBarClockManager
            com.android.systemui.statusbar.phone.PhoneStatusBarClockManager$POSITION r4 = r3.mClockPosition
            com.android.systemui.statusbar.phone.PhoneStatusBarClockManager$POSITION r5 = com.android.systemui.statusbar.phone.PhoneStatusBarClockManager.POSITION.RIGHT
            if (r4 != r5) goto L3c
            int r3 = r3.getClockWidth()
            goto L3d
        L3c:
            r3 = r2
        L3d:
            com.android.systemui.statusbar.phone.TwoPhoneModeIconController r6 = r6.twoPhoneModeIconController
            boolean r4 = r6.featureEnabled()
            if (r4 == 0) goto L4f
            int r4 = r6.getViewWidth()
            if (r4 <= 0) goto L4f
            int r2 = r6.getViewWidth()
        L4f:
            int r6 = r0.getMeasuredWidth()
            int r6 = r6 + r1
            int r6 = r6 + r3
            int r6 = r6 + r2
            return r6
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
        NetspeedViewController netspeedViewController;
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED && (netspeedViewController = this.netspeedViewController) != null) {
            netspeedViewController.init();
        }
        this.statusIconContainerController.init();
    }

    public final void onTouch(MotionEvent motionEvent) {
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
        this.indicatorGardenPresenter.updateGardenWithNewModel(this);
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
                IndicatorGardenPresenter indicatorGardenPresenter = phoneStatusBarViewController.indicatorGardenPresenter;
                indicatorGardenPresenter.getClass();
                indicatorGardenPresenter.mainHandler.post(new IndicatorGardenPresenter$onGardenOnLayout$1(indicatorGardenPresenter, phoneStatusBarViewController));
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
            phoneStatusBarClockManager.mMiddleContainer = (ViewGroup) phoneStatusBarClockManager.mGrandParentView.findViewById(R.id.middle_clock_container);
            phoneStatusBarClockManager.mRightContainer = (ViewGroup) phoneStatusBarClockManager.mGrandParentView.findViewById(R.id.right_clock_container);
            ((SlimIndicatorViewMediatorImpl) phoneStatusBarClockManager.mSlimIndicatorViewMediator).registerSubscriber("[QuickStar]PhoneStatusBarClockManager", phoneStatusBarClockManager);
            phoneStatusBarClockManager.updateResources();
        } else {
            Log.e("[QuickStar]PhoneStatusBarClockManager", "onAttachedToWindow(), mGrandParentView is null");
        }
        this.dumpManager.registerNormalDumpable("PhoneStatusBarViewController", this);
        PhoneStatusBarViewController$onViewAttached$3 phoneStatusBarViewController$onViewAttached$3 = new PhoneStatusBarViewController$onViewAttached$3(this);
        this.samsungStatusBarGrayIconHelper.grayIconChangedCallback = phoneStatusBarViewController$onViewAttached$3;
        BatteryMeterView batteryMeterView = (BatteryMeterView) ((PhoneStatusBarView) phoneStatusBarViewController$onViewAttached$3.this$0.mView).requireViewById(R.id.battery);
        batteryMeterView.mIsGrayColor = false;
        batteryMeterView.mSamsungDrawable.shouldShowGrayIcon = false;
        View requireViewById = ((PhoneStatusBarView) this.mView).requireViewById(R.id.system_icons);
        this.statusContainer = requireViewById;
        requireViewById.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$onViewAttached$4
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
        ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider = this.progressProvider;
        if (scopedUnfoldTransitionProgressProvider != null) {
            scopedUnfoldTransitionProgressProvider.setReadyToHandleTransition(true);
        }
        ((ConfigurationControllerImpl) this.configurationController).addCallback(this.configurationListener);
        View requireViewById2 = ((PhoneStatusBarView) this.mView).requireViewById(R.id.status_bar_start_side_except_heads_up);
        ViewGroup viewGroup2 = (ViewGroup) ((PhoneStatusBarView) this.mView).requireViewById(R.id.status_bar_end_side_content);
        TwoPhoneModeIconController twoPhoneModeIconController = this.twoPhoneModeIconController;
        if (twoPhoneModeIconController.featureEnabled()) {
            twoPhoneModeIconController.onViewAttached(viewGroup2);
        }
        if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT) {
            this.statusIconContainerController.view.mSidelingCutoutContainerInfo = new SidelingCutoutContainerInfo() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$onViewAttached$5
                @Override // com.android.systemui.statusbar.phone.SidelingCutoutContainerInfo
                public final int getRightSideAvailableWidth(Rect rect) {
                    return PhoneStatusBarViewController.access$getCutoutRightSideAvailableWidth(PhoneStatusBarViewController.this, rect);
                }
            };
            final ViewGroup viewGroup3 = (ViewGroup) ((PhoneStatusBarView) this.mView).requireViewById(R.id.system_icons);
            viewGroup3.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$onViewAttached$6
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
        if (this.moveFromCenterAnimationController == null) {
            return;
        }
        final View[] viewArr = {requireViewById2, viewGroup2};
        ((PhoneStatusBarView) this.mView).getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$onViewAttached$7
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
                statusBarMoveFromCenterAnimationController.progressProvider.listeners.add(statusBarMoveFromCenterAnimationController.transitionListener);
                ((PhoneStatusBarView) ((ViewController) PhoneStatusBarViewController.this).mView).getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
        ((PhoneStatusBarView) this.mView).addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$onViewAttached$8
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
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        View view = this.statusContainer;
        if (view == null) {
            view = null;
        }
        view.setOnHoverListener(null);
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
        this.dumpManager.unregisterDumpable("PhoneStatusBarViewController");
        TwoPhoneModeIconController twoPhoneModeIconController = this.twoPhoneModeIconController;
        if (twoPhoneModeIconController.featureEnabled()) {
            twoPhoneModeIconController.onViewDetached();
        }
        PhoneStatusBarClockManager phoneStatusBarClockManager = this.phoneStatusBarClockManager;
        ((SlimIndicatorViewMediatorImpl) phoneStatusBarClockManager.mSlimIndicatorViewMediator).unregisterSubscriber("[QuickStar]PhoneStatusBarClockManager");
        phoneStatusBarClockManager.mClockPosition = PhoneStatusBarClockManager.POSITION.NONE;
        IndicatorMarqueeGardener indicatorMarqueeGardener = this.indicatorMarqueeGardener;
        indicatorMarqueeGardener.wakefulnessLifecycle.removeObserver(indicatorMarqueeGardener.wakefulnessLifecycleObserver);
        this.samsungStatusBarGrayIconHelper.grayIconChangedCallback = null;
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

    public final void updatePaddingsForPrivacyDot(WindowInsets windowInsets) {
        PrivacyDotViewController privacyDotViewController = this.privacyDotViewController;
        int calculateLeftPadding = this.indicatorGardenPresenter.gardenAlgorithm.calculateLeftPadding();
        int calculateRightPadding = this.indicatorGardenPresenter.gardenAlgorithm.calculateRightPadding();
        synchronized (privacyDotViewController.lock) {
            privacyDotViewController.setNextViewState(ViewState.copy$default(privacyDotViewController.nextViewState, false, false, false, false, null, null, null, null, false, 0, privacyDotViewController.contentInsetsProvider.getStatusBarPaddingTop(), 0, null, null, calculateLeftPadding, calculateRightPadding, windowInsets.getInsets(WindowInsets.Type.systemBars()).left, windowInsets.getInsets(WindowInsets.Type.systemBars()).right, 277503));
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX WARN: Type inference failed for: r1v25, types: [com.android.systemui.statusbar.phone.PhoneStatusBarViewController$configurationListener$1] */
    /* JADX WARN: Type inference failed for: r1v32, types: [com.android.systemui.statusbar.phone.PhoneStatusBarViewController$gardener$1] */
    private PhoneStatusBarViewController(PhoneStatusBarView phoneStatusBarView, ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider, CentralSurfaces centralSurfaces, StatusBarWindowStateController statusBarWindowStateController, ShadeController shadeController, ShadeViewController shadeViewController, PanelExpansionInteractor panelExpansionInteractor, Provider provider, ShadeLogger shadeLogger, StatusBarMoveFromCenterAnimationController statusBarMoveFromCenterAnimationController, StatusBarUserChipViewModel statusBarUserChipViewModel, ViewUtil viewUtil, ConfigurationController configurationController, StatusOverlayHoverListenerFactory statusOverlayHoverListenerFactory, IndicatorGardenPresenter indicatorGardenPresenter, PrivacyDotViewController privacyDotViewController, DumpManager dumpManager, IndicatorGardenViewTreeLogHelper indicatorGardenViewTreeLogHelper, IndicatorMarqueeGardener indicatorMarqueeGardener, StatusIconContainerController statusIconContainerController, IndicatorCutoutUtil indicatorCutoutUtil, TwoPhoneModeIconController twoPhoneModeIconController, PhoneStatusBarClockManager phoneStatusBarClockManager, KnoxStatusBarControlViewModel knoxStatusBarControlViewModel, NetspeedViewController netspeedViewController, SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper) {
        super(phoneStatusBarView);
        this.progressProvider = scopedUnfoldTransitionProgressProvider;
        this.centralSurfaces = centralSurfaces;
        this.statusBarWindowStateController = statusBarWindowStateController;
        this.shadeController = shadeController;
        this.shadeViewController = shadeViewController;
        this.shadeLogger = shadeLogger;
        this.moveFromCenterAnimationController = statusBarMoveFromCenterAnimationController;
        this.viewUtil = viewUtil;
        this.configurationController = configurationController;
        this.indicatorGardenPresenter = indicatorGardenPresenter;
        this.privacyDotViewController = privacyDotViewController;
        this.dumpManager = dumpManager;
        this.viewTreeLogHelper = indicatorGardenViewTreeLogHelper;
        this.indicatorMarqueeGardener = indicatorMarqueeGardener;
        this.statusIconContainerController = statusIconContainerController;
        this.indicatorCutoutUtil = indicatorCutoutUtil;
        this.twoPhoneModeIconController = twoPhoneModeIconController;
        this.phoneStatusBarClockManager = phoneStatusBarClockManager;
        this.knoxStateBarControlViewModel = knoxStatusBarControlViewModel;
        this.netspeedViewController = netspeedViewController;
        this.samsungStatusBarGrayIconHelper = samsungStatusBarGrayIconHelper;
        this.quickPanelLogger$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$quickPanelLogger$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new QuickPanelLogger("PSBVC");
            }
        });
        this.commandQueue$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$commandQueue$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (CommandQueue) Dependency.sDependency.getDependencyInner(CommandQueue.class);
            }
        });
        this.configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$configurationListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                PhoneStatusBarViewController phoneStatusBarViewController = PhoneStatusBarViewController.this;
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
        ((PhoneStatusBarView) this.mView).mTouchEventHandler = new PhoneStatusBarViewTouchHandler();
        StatusBarUserChipViewBinder.bind((StatusBarUserSwitcherContainer) ((PhoneStatusBarView) this.mView).findViewById(R.id.user_switcher_container), statusBarUserChipViewModel, null);
        this.gardener = new IndicatorBasicGardener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewController$gardener$1
            {
                super(PhoneStatusBarViewController.this, "PhoneStatusBarViewController");
            }

            @Override // com.android.systemui.statusbar.phone.IndicatorBasicGardener
            public final ViewGroup.MarginLayoutParams getCameraTopMarginContainerMarginLayoutParams() {
                View findViewById = ((PhoneStatusBarView) ((ViewController) PhoneStatusBarViewController.this).mView).findViewById(R.id.status_bar_contents);
                return (ViewGroup.MarginLayoutParams) (findViewById != null ? findViewById.getLayoutParams() : null);
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
        T t = this.mView;
        this.heightContainer = (ViewGroup) t;
        this.sidePaddingContainer = (ViewGroup) ((PhoneStatusBarView) t).findViewById(R.id.status_bar_contents);
        this.leftContainer = (IndicatorGardenContainer) ((PhoneStatusBarView) this.mView).findViewById(R.id.status_bar_left_container);
        this.centerContainer = (IndicatorGardenContainer) ((PhoneStatusBarView) this.mView).findViewById(R.id.status_bar_center_container);
        this.rightContainer = (IndicatorGardenContainer) ((PhoneStatusBarView) this.mView).findViewById(R.id.system_icon_area);
    }
}
