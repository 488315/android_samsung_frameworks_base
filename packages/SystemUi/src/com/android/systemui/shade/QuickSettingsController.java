package com.android.systemui.shade;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Debug;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.MathUtils;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.policy.SystemBarUtils;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardStatusView;
import com.android.keyguard.KeyguardStatusViewController;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.wm.shell.animation.FlingAnimationUtils;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.animation.ShadeInterpolation;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.keyguard.animator.KeyguardTouchAnimator;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardFaceAuthInteractor;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.log.SecPanelLogger;
import com.android.systemui.log.SecPanelLoggerImpl;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.qs.HeaderPrivacyIconsController;
import com.android.systemui.qs.NonInterceptingScrollView;
import com.android.systemui.qs.QSContainerImpl;
import com.android.systemui.qs.QSFragment;
import com.android.systemui.qs.QuickAnimation;
import com.android.systemui.qs.SecQSFragment;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQuickQSPanel;
import com.android.systemui.qs.animator.SecQSFragmentAnimatorManager;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.BarType;
import com.android.systemui.qs.bar.QSMediaPlayerBar;
import com.android.systemui.pluginlock.PluginLockDelegateApp;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.PluginLockMediatorImpl;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.InterfaceC1922QS;
import com.android.systemui.privacy.PrivacyConfig;
import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.scrim.ScrimDrawable;
import com.android.systemui.scrim.ScrimView;
import com.android.systemui.shade.QuickSettingsController;
import com.android.systemui.shade.ShadeStateEvents;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.shade.transition.ShadeTransitionController;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.PulseExpansionHandler;
import com.android.systemui.statusbar.QsFrameTranslateController;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.DcmMascotViewContainer;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaView;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.KeyguardSecAffordanceHelper;
import com.android.systemui.statusbar.phone.KeyguardStatusBarView;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LockscreenGestureLogger;
import com.android.systemui.statusbar.phone.QSScrimViewSwitch;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.ScrimState;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager;
import com.android.systemui.statusbar.policy.CastController;
import com.android.systemui.statusbar.policy.CastControllerImpl;
import com.android.systemui.statusbar.policy.CastControllerImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherController;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.LargeScreenUtils;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QuickSettingsController implements Dumpable {
    public final AccessibilityManager mAccessibilityManager;
    public final AmbientState mAmbientState;
    public float mAmount;
    public boolean mAnimateNextNotificationBounds;
    public boolean mAnimating;
    public boolean mAnimatingHiddenFromCollapsed;
    public boolean mAnimatorExpand;
    public NotificationPanelViewController$$ExternalSyntheticLambda2 mApplyClippingImmediatelyListener;
    public int mBarState;
    public Insets mCachedGestureInsets;
    public final CastController mCastController;
    public boolean mCollapsedOnDown;
    public boolean mConflictingExpansionGesture;
    public final NotificationShadeDepthController mDepthController;
    public int mDistanceForFullShadeTransition;
    public boolean mDozing;
    public boolean mEnableClipping;
    public boolean mExpandImmediate;
    public boolean mExpanded;
    public boolean mExpandedWhenExpandingStarted;
    public ValueAnimator mExpansionAnimator;
    public boolean mExpansionFromOverscroll;
    public float mExpansionHeight;
    public NotificationPanelViewController$$ExternalSyntheticLambda2 mExpansionHeightListener;
    public NotificationPanelViewController$$ExternalSyntheticLambda2 mExpansionHeightSetToMaxListener;
    public final FalsingCollector mFalsingCollector;
    public final FalsingManager mFalsingManager;
    public int mFalsingThreshold;
    public NotificationPanelViewController$$ExternalSyntheticLambda2 mFlingQsWithoutClickListener;
    public boolean mFullyExpanded;
    public float mInitialHeightOnTouch;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public boolean mIsFullWidth;
    public boolean mIsPulseExpansionResettingAnimator;
    public boolean mIsRubberBanded;
    public boolean mIsTranslationResettingAnimator;
    public final KeyguardBypassController mKeyguardBypassController;
    public final KeyguardStatusBarView mKeyguardStatusBar;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public float mLastOverscroll;
    public boolean mLastShadeFlingWasExpanding;
    public final LightBarController mLightBarController;
    public final LockscreenGestureLogger mLockscreenGestureLogger;
    public int mLockscreenNotificationPadding;
    public final LockscreenShadeTransitionController mLockscreenShadeTransitionController;
    public final DcmMascotViewContainer mMascotViewContainer;
    public int mMaxExpansionHeight;
    public final MetricsLogger mMetricsLogger;
    public int mMinExpansionHeight;
    public long mNotificationBoundsAnimationDelay;
    public long mNotificationBoundsAnimationDuration;
    public final NotificationStackScrollLayoutController mNotificationStackScrollLayoutController;
    public final SecPanelLogger mPanelLogger;
    public final NotificationPanelView mPanelView;
    public final Lazy mPanelViewControllerLazy;
    public int mPeekHeight;
    public final PluginLockMediator mPluginLockMediator;
    public final PulseExpansionHandler mPulseExpansionHandler;
    public InterfaceC1922QS mQs;
    public final FrameLayout mQsFrame;
    public final QsFrameTranslateController mQsFrameTranslateController;
    public NotificationPanelViewController$$ExternalSyntheticLambda2 mQsStateUpdateListener;
    public VelocityTracker mQsVelocityTracker;
    public float mQuickQsHeaderHeight;
    public int mQuickQsOffsetHeight;
    public final RecordingController mRecordingController;
    public final NotificationRemoteInputManager mRemoteInputManager;
    public final Resources mResources;
    public int mScreenCornerRadius;
    public final ScrimController mScrimController;
    public int mScrimCornerRadius;
    public final SecQuickSettingsController mSecQuickSettingsController;
    public float mShadeExpandedFraction;
    public final ShadeExpansionStateManager mShadeExpansionStateManager;
    public final ShadeHeaderController mShadeHeaderController;
    public final ShadeLogger mShadeLog;
    public final ShadeRepository mShadeRepository;
    public final ShadeTransitionController mShadeTransitionController;
    public int mShelfHeight;
    public ValueAnimator mSizeChangeAnimator;
    public float mSlopMultiplier;
    public boolean mSplitShadeEnabled;
    public int mSplitShadeNotificationsScrimMarginBottom;
    public boolean mStackScrollerOverscrolling;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public int mStatusBarMinHeight;
    public final StatusBarTouchableRegionManager mStatusBarTouchableRegionManager;
    public boolean mTouchAboveFalsingThreshold;
    public int mTouchSlop;
    public boolean mTracking;
    public int mTrackingPointer;
    public float mTranslationForFullShadeTransition;
    public boolean mTwoFingerExpandPossible;
    public boolean mVisible;
    public boolean mScrimEnabled = true;
    public boolean mUseLargeScreenShadeHeader = true;
    public int mDisplayRightInset = 0;
    public int mDisplayLeftInset = 0;
    public float mShadeExpandedHeight = 0.0f;
    public boolean mExpansionEnabledPolicy = true;
    public boolean mExpansionEnabledAmbient = true;
    public final Region mInterceptRegion = new Region();
    public final Rect mClippingAnimationEndBounds = new Rect();
    public final Rect mLastClipBounds = new Rect();
    public ValueAnimator mClippingAnimator = null;
    public final QuickSettingsController$$ExternalSyntheticLambda2 mQsHeightListener = new QuickSettingsController$$ExternalSyntheticLambda2(this);
    public final QuickSettingsController$$ExternalSyntheticLambda5 mQsCollapseExpandAction = new QuickSettingsController$$ExternalSyntheticLambda5(this, 0);
    public final QuickSettingsController$$ExternalSyntheticLambda2 mQsScrollListener = new QuickSettingsController$$ExternalSyntheticLambda2(this);
    public final StringBuilder mLogBuilder = new StringBuilder();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LockscreenShadeTransitionCallback {
        public /* synthetic */ LockscreenShadeTransitionCallback(QuickSettingsController quickSettingsController, int i) {
            this();
        }

        private LockscreenShadeTransitionCallback() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NsslOverscrollTopChangedListener {
        public /* synthetic */ NsslOverscrollTopChangedListener(QuickSettingsController quickSettingsController, int i) {
            this();
        }

        private NsslOverscrollTopChangedListener() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class QsFragmentListener implements FragmentHostManager.FragmentListener {
        public QsFragmentListener() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
        public final void onFragmentViewCreated(Fragment fragment) {
            SecQSFragment secQSFragment;
            NonInterceptingScrollView nonInterceptingScrollView;
            View view;
            InterfaceC1922QS interfaceC1922QS = (InterfaceC1922QS) fragment;
            QuickSettingsController quickSettingsController = QuickSettingsController.this;
            quickSettingsController.mQs = interfaceC1922QS;
            interfaceC1922QS.setPanelView(quickSettingsController.mQsHeightListener);
            quickSettingsController.mQs.setCollapseExpandAction(quickSettingsController.mQsCollapseExpandAction);
            quickSettingsController.mQs.setHeaderClickable(quickSettingsController.isExpansionEnabled());
            quickSettingsController.mQs.setOverscrolling(quickSettingsController.mStackScrollerOverscrolling);
            quickSettingsController.mQs.setInSplitShade(quickSettingsController.mSplitShadeEnabled);
            quickSettingsController.mQs.setIsNotificationPanelFullWidth(quickSettingsController.mIsFullWidth);
            quickSettingsController.mQs.getView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.shade.QuickSettingsController$QsFragmentListener$$ExternalSyntheticLambda0
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    QuickSettingsController.QsFragmentListener qsFragmentListener = QuickSettingsController.QsFragmentListener.this;
                    if (i4 - i2 != i8 - i6) {
                        QuickSettingsController.this.onHeightChanged();
                    } else {
                        qsFragmentListener.getClass();
                    }
                }
            });
            InterfaceC1922QS interfaceC1922QS2 = quickSettingsController.mQs;
            quickSettingsController.mLockscreenShadeTransitionController.f346qS = interfaceC1922QS2;
            quickSettingsController.mShadeTransitionController.f344qs = interfaceC1922QS2;
            quickSettingsController.mNotificationStackScrollLayoutController.mView.mQsHeader = (ViewGroup) interfaceC1922QS2.getHeader();
            quickSettingsController.mQs.setScrollListener(quickSettingsController.mQsScrollListener);
            quickSettingsController.updateExpansion();
            final SecQuickSettingsController secQuickSettingsController = quickSettingsController.mSecQuickSettingsController;
            Supplier supplier = secQuickSettingsController.qsSupplier;
            Object obj = supplier.get();
            QSFragment qSFragment = obj instanceof QSFragment ? (QSFragment) obj : null;
            if (qSFragment == null || (secQSFragment = qSFragment.mSecQSFragment) == null) {
                return;
            }
            NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) secQuickSettingsController.panelViewControllerLazy.get();
            QuickAnimation quickAnimation = secQSFragment.quickAnimation;
            SecQSFragmentAnimatorManager secQSFragmentAnimatorManager = quickAnimation.secQSFragmentAnimatorManager;
            if (secQSFragmentAnimatorManager != null) {
                secQSFragmentAnimatorManager.setPanelViewController(notificationPanelViewController);
            }
            SecQSFragmentAnimatorManager secQSFragmentAnimatorManager2 = quickAnimation.secQSFragmentAnimatorManager;
            if (secQSFragmentAnimatorManager2 != null) {
                secQSFragmentAnimatorManager2.setNotificationStackScrollerController(secQuickSettingsController.notificationStackScrollLayoutController);
            }
            SecQSFragmentAnimatorManager secQSFragmentAnimatorManager3 = quickAnimation.secQSFragmentAnimatorManager;
            BooleanSupplier booleanSupplier = secQuickSettingsController.expandImmediateSupplier;
            if (secQSFragmentAnimatorManager3 != null) {
                secQSFragmentAnimatorManager3.setExpandImmediateSupplier(booleanSupplier);
            }
            BarController barController = secQSFragment.quickBar.barController;
            if (barController != null) {
                SecQsMediaTouchHelper secQsMediaTouchHelper = secQuickSettingsController.mediaTouchHelper;
                secQsMediaTouchHelper.getClass();
                BarItemImpl barInCollapsed = barController.getBarInCollapsed(BarType.QS_MEDIA_PLAYER);
                secQsMediaTouchHelper.qsMediaPlayerBar = barInCollapsed instanceof QSMediaPlayerBar ? (QSMediaPlayerBar) barInCollapsed : null;
                secQsMediaTouchHelper.mediaHost.mVisibilityListeners.add(secQsMediaTouchHelper);
            } else {
                Log.e("SecQuickSettingsController", "setupMediaTouchHelper: barController is null !!");
            }
            secQSFragment.expandImmediate = booleanSupplier;
            InterfaceC1922QS interfaceC1922QS3 = (InterfaceC1922QS) supplier.get();
            if (interfaceC1922QS3 != null && (view = interfaceC1922QS3.getView()) != null) {
                secQuickSettingsController.qsScrollView = (NonInterceptingScrollView) view.findViewById(R.id.expanded_qs_scroll_view);
                QSContainerImpl qSContainerImpl = (QSContainerImpl) view.findViewById(R.id.quick_settings_container);
                secQuickSettingsController.qsContainerImpl = qSContainerImpl;
                if (qSContainerImpl != null) {
                    qSContainerImpl.mMinExpansionHeightSupplier = secQuickSettingsController.minExpansionHeightSupplier;
                }
                if (qSContainerImpl != null) {
                    qSContainerImpl.mMaxExpansionHeightSupplier = secQuickSettingsController.maxExpansionHeightSupplier;
                }
            }
            BooleanSupplier booleanSupplier2 = secQuickSettingsController.heightAnimatingSupplier;
            if (booleanSupplier2 != null && (nonInterceptingScrollView = secQuickSettingsController.qsScrollView) != null) {
                nonInterceptingScrollView.mHeightAnimatingSupplier = booleanSupplier2;
            }
            QSContainerImpl qSContainerImpl2 = secQuickSettingsController.qsContainerImpl;
            if (qSContainerImpl2 != null) {
                qSContainerImpl2.mExpandImmediateSupplier = booleanSupplier;
                qSContainerImpl2.mImmersiveScrollingSupplier = new BooleanSupplier() { // from class: com.android.systemui.shade.SecQuickSettingsController$onFragmentViewCreated$1$3$1
                    @Override // java.util.function.BooleanSupplier
                    public final boolean getAsBoolean() {
                        return SecQuickSettingsController.this.notificationStackScrollLayoutController.mView.mOwnScrollY > 0;
                    }
                };
                secQuickSettingsController.quickQSPanel = (SecQuickQSPanel) qSContainerImpl2.findViewById(R.id.quick_qs_panel);
            }
            SecQuickQSPanel secQuickQSPanel = secQuickSettingsController.quickQSPanel;
            if (secQuickQSPanel != null) {
            }
            secQuickSettingsController.naviBarGestureMode = secQuickSettingsController.navigationModeController.addListener(secQuickSettingsController.modeChangedListener);
            PanelScreenShotLogger.INSTANCE.addLogProvider("SecQuickSettingsController", secQuickSettingsController.logProvider);
        }

        @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
        public final void onFragmentViewDestroyed(Fragment fragment) {
            QuickSettingsController quickSettingsController = QuickSettingsController.this;
            SecQuickSettingsController secQuickSettingsController = quickSettingsController.mSecQuickSettingsController;
            SecQsMediaTouchHelper secQsMediaTouchHelper = secQuickSettingsController.mediaTouchHelper;
            secQsMediaTouchHelper.mediaHost.mVisibilityListeners.remove(secQsMediaTouchHelper);
            secQuickSettingsController.navigationModeController.removeListener(secQuickSettingsController.modeChangedListener);
            synchronized (PanelScreenShotLogger.INSTANCE) {
                PanelScreenShotLogger.providers.remove("SecQuickSettingsController");
            }
            if (fragment == quickSettingsController.mQs) {
                quickSettingsController.mQs = null;
            }
        }
    }

    public QuickSettingsController(DcmMascotViewContainer dcmMascotViewContainer, final Lazy lazy, NotificationPanelView notificationPanelView, QsFrameTranslateController qsFrameTranslateController, ShadeTransitionController shadeTransitionController, PulseExpansionHandler pulseExpansionHandler, NotificationRemoteInputManager notificationRemoteInputManager, ShadeExpansionStateManager shadeExpansionStateManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, LightBarController lightBarController, NotificationStackScrollLayoutController notificationStackScrollLayoutController, LockscreenShadeTransitionController lockscreenShadeTransitionController, NotificationShadeDepthController notificationShadeDepthController, ShadeHeaderController shadeHeaderController, StatusBarTouchableRegionManager statusBarTouchableRegionManager, KeyguardStateController keyguardStateController, KeyguardBypassController keyguardBypassController, KeyguardUpdateMonitor keyguardUpdateMonitor, ScrimController scrimController, MediaDataManager mediaDataManager, AmbientState ambientState, RecordingController recordingController, FalsingManager falsingManager, FalsingCollector falsingCollector, AccessibilityManager accessibilityManager, LockscreenGestureLogger lockscreenGestureLogger, MetricsLogger metricsLogger, FeatureFlags featureFlags, InteractionJankMonitor interactionJankMonitor, ShadeLogger shadeLogger, DumpManager dumpManager, KeyguardFaceAuthInteractor keyguardFaceAuthInteractor, ShadeRepository shadeRepository, CastController castController, SecMediaHost secMediaHost, QSScrimViewSwitch qSScrimViewSwitch, SecPanelExpansionStateNotifier secPanelExpansionStateNotifier, SecQpBlurController secQpBlurController, SecPanelLogger secPanelLogger, PluginLockMediator pluginLockMediator, NavigationBarController navigationBarController, NavigationModeController navigationModeController, final PrivacyDialogController privacyDialogController) {
        final int i = 1;
        final int i2 = 0;
        this.mMascotViewContainer = dcmMascotViewContainer;
        this.mPanelViewControllerLazy = lazy;
        this.mPanelView = notificationPanelView;
        this.mQsFrame = (FrameLayout) notificationPanelView.findViewById(R.id.qs_frame);
        this.mKeyguardStatusBar = (KeyguardStatusBarView) notificationPanelView.findViewById(R.id.keyguard_header);
        Resources resources = notificationPanelView.getResources();
        this.mResources = resources;
        this.mSplitShadeEnabled = LargeScreenUtils.shouldUseSplitNotificationShade(resources);
        this.mQsFrameTranslateController = qsFrameTranslateController;
        this.mShadeTransitionController = shadeTransitionController;
        this.mPulseExpansionHandler = pulseExpansionHandler;
        pulseExpansionHandler.pulseExpandAbortListener = new QuickSettingsController$$ExternalSyntheticLambda5(this, 4);
        this.mRemoteInputManager = notificationRemoteInputManager;
        this.mShadeExpansionStateManager = shadeExpansionStateManager;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mLightBarController = lightBarController;
        this.mNotificationStackScrollLayoutController = notificationStackScrollLayoutController;
        this.mLockscreenShadeTransitionController = lockscreenShadeTransitionController;
        this.mDepthController = notificationShadeDepthController;
        this.mShadeHeaderController = shadeHeaderController;
        this.mStatusBarTouchableRegionManager = statusBarTouchableRegionManager;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mScrimController = scrimController;
        this.mAmbientState = ambientState;
        this.mRecordingController = recordingController;
        this.mFalsingManager = falsingManager;
        this.mFalsingCollector = falsingCollector;
        this.mAccessibilityManager = accessibilityManager;
        this.mLockscreenGestureLogger = lockscreenGestureLogger;
        this.mMetricsLogger = metricsLogger;
        this.mShadeLog = shadeLogger;
        this.mCastController = castController;
        this.mInteractionJankMonitor = interactionJankMonitor;
        this.mShadeRepository = shadeRepository;
        LockscreenShadeTransitionCallback lockscreenShadeTransitionCallback = new LockscreenShadeTransitionCallback(this, i2);
        ArrayList arrayList = (ArrayList) lockscreenShadeTransitionController.callbacks;
        if (!arrayList.contains(lockscreenShadeTransitionCallback)) {
            arrayList.add(lockscreenShadeTransitionCallback);
        }
        dumpManager.registerDumpable(this);
        this.mPanelLogger = secPanelLogger;
        QuickSettingsController$$ExternalSyntheticLambda5 quickSettingsController$$ExternalSyntheticLambda5 = new QuickSettingsController$$ExternalSyntheticLambda5(this, 5);
        Function function = new Function(this) { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda10
            public final /* synthetic */ QuickSettingsController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                int calculateTopClippingBound;
                switch (i2) {
                    case 0:
                        calculateTopClippingBound = this.f$0.calculateBottomPosition(((Float) obj).floatValue());
                        break;
                    default:
                        calculateTopClippingBound = this.f$0.calculateTopClippingBound(((Integer) obj).intValue());
                        break;
                }
                return Integer.valueOf(calculateTopClippingBound);
            }
        };
        Function function2 = new Function(this) { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda10
            public final /* synthetic */ QuickSettingsController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                int calculateTopClippingBound;
                switch (i) {
                    case 0:
                        calculateTopClippingBound = this.f$0.calculateBottomPosition(((Float) obj).floatValue());
                        break;
                    default:
                        calculateTopClippingBound = this.f$0.calculateTopClippingBound(((Integer) obj).intValue());
                        break;
                }
                return Integer.valueOf(calculateTopClippingBound);
            }
        };
        DoubleSupplier doubleSupplier = new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda11
            public final /* synthetic */ QuickSettingsController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                switch (i2) {
                    case 0:
                        return this.f$0.computeExpansionFraction();
                    case 1:
                        return this.f$0.mShadeExpandedFraction;
                    case 2:
                        return this.f$0.mExpansionHeight;
                    case 3:
                        return this.f$0.mInitialTouchX;
                    case 4:
                        return this.f$0.mInitialTouchY;
                    case 5:
                        return this.f$0.mMaxExpansionHeight;
                    case 6:
                        return this.f$0.mMinExpansionHeight;
                    case 7:
                        return this.f$0.getCurrentVelocity();
                    default:
                        return this.f$0.getEdgePosition();
                }
            }
        };
        Context context = notificationPanelView.getContext();
        final int i3 = 7;
        DoubleSupplier doubleSupplier2 = new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda11
            public final /* synthetic */ QuickSettingsController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                switch (i3) {
                    case 0:
                        return this.f$0.computeExpansionFraction();
                    case 1:
                        return this.f$0.mShadeExpandedFraction;
                    case 2:
                        return this.f$0.mExpansionHeight;
                    case 3:
                        return this.f$0.mInitialTouchX;
                    case 4:
                        return this.f$0.mInitialTouchY;
                    case 5:
                        return this.f$0.mMaxExpansionHeight;
                    case 6:
                        return this.f$0.mMinExpansionHeight;
                    case 7:
                        return this.f$0.getCurrentVelocity();
                    default:
                        return this.f$0.getEdgePosition();
                }
            }
        };
        final int i4 = 8;
        DoubleSupplier doubleSupplier3 = new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda11
            public final /* synthetic */ QuickSettingsController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                switch (i4) {
                    case 0:
                        return this.f$0.computeExpansionFraction();
                    case 1:
                        return this.f$0.mShadeExpandedFraction;
                    case 2:
                        return this.f$0.mExpansionHeight;
                    case 3:
                        return this.f$0.mInitialTouchX;
                    case 4:
                        return this.f$0.mInitialTouchY;
                    case 5:
                        return this.f$0.mMaxExpansionHeight;
                    case 6:
                        return this.f$0.mMinExpansionHeight;
                    case 7:
                        return this.f$0.getCurrentVelocity();
                    default:
                        return this.f$0.getEdgePosition();
                }
            }
        };
        BooleanSupplier booleanSupplier = new BooleanSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda3
            public final /* synthetic */ QuickSettingsController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                switch (i2) {
                    case 0:
                        return this.f$0.mEnableClipping;
                    case 1:
                        return this.f$0.mExpandImmediate;
                    case 2:
                        return this.f$0.mShadeExpandedFraction <= 0.0f;
                    default:
                        return this.f$0.mExpanded;
                }
            }
        };
        final int i5 = 1;
        DoubleSupplier doubleSupplier4 = new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda11
            public final /* synthetic */ QuickSettingsController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                switch (i5) {
                    case 0:
                        return this.f$0.computeExpansionFraction();
                    case 1:
                        return this.f$0.mShadeExpandedFraction;
                    case 2:
                        return this.f$0.mExpansionHeight;
                    case 3:
                        return this.f$0.mInitialTouchX;
                    case 4:
                        return this.f$0.mInitialTouchY;
                    case 5:
                        return this.f$0.mMaxExpansionHeight;
                    case 6:
                        return this.f$0.mMinExpansionHeight;
                    case 7:
                        return this.f$0.getCurrentVelocity();
                    default:
                        return this.f$0.getEdgePosition();
                }
            }
        };
        BooleanSupplier booleanSupplier2 = new BooleanSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda3
            public final /* synthetic */ QuickSettingsController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                switch (i5) {
                    case 0:
                        return this.f$0.mEnableClipping;
                    case 1:
                        return this.f$0.mExpandImmediate;
                    case 2:
                        return this.f$0.mShadeExpandedFraction <= 0.0f;
                    default:
                        return this.f$0.mExpanded;
                }
            }
        };
        final int i6 = 2;
        DoubleSupplier doubleSupplier5 = new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda11
            public final /* synthetic */ QuickSettingsController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                switch (i6) {
                    case 0:
                        return this.f$0.computeExpansionFraction();
                    case 1:
                        return this.f$0.mShadeExpandedFraction;
                    case 2:
                        return this.f$0.mExpansionHeight;
                    case 3:
                        return this.f$0.mInitialTouchX;
                    case 4:
                        return this.f$0.mInitialTouchY;
                    case 5:
                        return this.f$0.mMaxExpansionHeight;
                    case 6:
                        return this.f$0.mMinExpansionHeight;
                    case 7:
                        return this.f$0.getCurrentVelocity();
                    default:
                        return this.f$0.getEdgePosition();
                }
            }
        };
        final int i7 = 0;
        DoubleConsumer doubleConsumer = new DoubleConsumer(this) { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda4
            public final /* synthetic */ QuickSettingsController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleConsumer
            public final void accept(double d) {
                switch (i7) {
                    case 0:
                        this.f$0.mInitialTouchX = (float) d;
                        break;
                    default:
                        this.f$0.mInitialTouchY = (float) d;
                        break;
                }
            }
        };
        final int i8 = 3;
        DoubleSupplier doubleSupplier6 = new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda11
            public final /* synthetic */ QuickSettingsController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                switch (i8) {
                    case 0:
                        return this.f$0.computeExpansionFraction();
                    case 1:
                        return this.f$0.mShadeExpandedFraction;
                    case 2:
                        return this.f$0.mExpansionHeight;
                    case 3:
                        return this.f$0.mInitialTouchX;
                    case 4:
                        return this.f$0.mInitialTouchY;
                    case 5:
                        return this.f$0.mMaxExpansionHeight;
                    case 6:
                        return this.f$0.mMinExpansionHeight;
                    case 7:
                        return this.f$0.getCurrentVelocity();
                    default:
                        return this.f$0.getEdgePosition();
                }
            }
        };
        final int i9 = 1;
        DoubleConsumer doubleConsumer2 = new DoubleConsumer(this) { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda4
            public final /* synthetic */ QuickSettingsController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleConsumer
            public final void accept(double d) {
                switch (i9) {
                    case 0:
                        this.f$0.mInitialTouchX = (float) d;
                        break;
                    default:
                        this.f$0.mInitialTouchY = (float) d;
                        break;
                }
            }
        };
        final int i10 = 4;
        DoubleSupplier doubleSupplier7 = new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda11
            public final /* synthetic */ QuickSettingsController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                switch (i10) {
                    case 0:
                        return this.f$0.computeExpansionFraction();
                    case 1:
                        return this.f$0.mShadeExpandedFraction;
                    case 2:
                        return this.f$0.mExpansionHeight;
                    case 3:
                        return this.f$0.mInitialTouchX;
                    case 4:
                        return this.f$0.mInitialTouchY;
                    case 5:
                        return this.f$0.mMaxExpansionHeight;
                    case 6:
                        return this.f$0.mMinExpansionHeight;
                    case 7:
                        return this.f$0.getCurrentVelocity();
                    default:
                        return this.f$0.getEdgePosition();
                }
            }
        };
        QuickSettingsController$$ExternalSyntheticLambda5 quickSettingsController$$ExternalSyntheticLambda52 = new QuickSettingsController$$ExternalSyntheticLambda5(this, 1);
        final int i11 = 2;
        BooleanSupplier booleanSupplier3 = new BooleanSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda3
            public final /* synthetic */ QuickSettingsController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                switch (i11) {
                    case 0:
                        return this.f$0.mEnableClipping;
                    case 1:
                        return this.f$0.mExpandImmediate;
                    case 2:
                        return this.f$0.mShadeExpandedFraction <= 0.0f;
                    default:
                        return this.f$0.mExpanded;
                }
            }
        };
        BooleanSupplier booleanSupplier4 = new BooleanSupplier() { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda6
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return QuickSettingsController.this.mShadeExpandedHeight >= ((float) ((NotificationPanelViewController) lazy.get()).getMaxPanelHeight());
            }
        };
        final int i12 = 5;
        DoubleSupplier doubleSupplier8 = new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda11
            public final /* synthetic */ QuickSettingsController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                switch (i12) {
                    case 0:
                        return this.f$0.computeExpansionFraction();
                    case 1:
                        return this.f$0.mShadeExpandedFraction;
                    case 2:
                        return this.f$0.mExpansionHeight;
                    case 3:
                        return this.f$0.mInitialTouchX;
                    case 4:
                        return this.f$0.mInitialTouchY;
                    case 5:
                        return this.f$0.mMaxExpansionHeight;
                    case 6:
                        return this.f$0.mMinExpansionHeight;
                    case 7:
                        return this.f$0.getCurrentVelocity();
                    default:
                        return this.f$0.getEdgePosition();
                }
            }
        };
        final int i13 = 6;
        DoubleSupplier doubleSupplier9 = new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda11
            public final /* synthetic */ QuickSettingsController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                switch (i13) {
                    case 0:
                        return this.f$0.computeExpansionFraction();
                    case 1:
                        return this.f$0.mShadeExpandedFraction;
                    case 2:
                        return this.f$0.mExpansionHeight;
                    case 3:
                        return this.f$0.mInitialTouchX;
                    case 4:
                        return this.f$0.mInitialTouchY;
                    case 5:
                        return this.f$0.mMaxExpansionHeight;
                    case 6:
                        return this.f$0.mMinExpansionHeight;
                    case 7:
                        return this.f$0.getCurrentVelocity();
                    default:
                        return this.f$0.getEdgePosition();
                }
            }
        };
        Objects.requireNonNull(privacyDialogController);
        final int i14 = 0;
        final int i15 = 3;
        final int i16 = 1;
        final int i17 = 2;
        this.mSecQuickSettingsController = new SecQuickSettingsController(ambientState, secQpBlurController, quickSettingsController$$ExternalSyntheticLambda5, function, function2, doubleSupplier, context, doubleSupplier2, doubleSupplier3, booleanSupplier, doubleSupplier4, booleanSupplier2, doubleSupplier5, shadeHeaderController, doubleConsumer, doubleSupplier6, doubleConsumer2, doubleSupplier7, quickSettingsController$$ExternalSyntheticLambda52, booleanSupplier3, booleanSupplier4, doubleSupplier8, secMediaHost, doubleSupplier9, navigationBarController, navigationModeController, notificationStackScrollLayoutController, secPanelExpansionStateNotifier, secPanelLogger, lazy, new IntConsumer() { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda7
            @Override // java.util.function.IntConsumer
            public final void accept(int i18) {
                switch (i14) {
                    case 0:
                        ((PrivacyDialogController) privacyDialogController).mDialogTranslateX = i18;
                        break;
                    default:
                        ((QuickSettingsController) privacyDialogController).mTrackingPointer = i18;
                        break;
                }
            }
        }, new BooleanSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda3
            public final /* synthetic */ QuickSettingsController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                switch (i15) {
                    case 0:
                        return this.f$0.mEnableClipping;
                    case 1:
                        return this.f$0.mExpandImmediate;
                    case 2:
                        return this.f$0.mShadeExpandedFraction <= 0.0f;
                    default:
                        return this.f$0.mExpanded;
                }
            }
        }, new Supplier() { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i14) {
                    case 0:
                        return ((QuickSettingsController) this).mQsFrame;
                    case 1:
                        return ((QuickSettingsController) this).mQs;
                    default:
                        return ((NotificationPanelViewController) ((Lazy) this).get()).mView;
                }
            }
        }, qSScrimViewSwitch, new Supplier() { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i16) {
                    case 0:
                        return ((QuickSettingsController) this).mQsFrame;
                    case 1:
                        return ((QuickSettingsController) this).mQs;
                    default:
                        return ((NotificationPanelViewController) ((Lazy) this).get()).mView;
                }
            }
        }, new QuickSettingsController$$ExternalSyntheticLambda12(this, 1), new QuickSettingsController$$ExternalSyntheticLambda12(this, 2), new IntConsumer() { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda7
            @Override // java.util.function.IntConsumer
            public final void accept(int i18) {
                switch (i16) {
                    case 0:
                        ((PrivacyDialogController) this).mDialogTranslateX = i18;
                        break;
                    default:
                        ((QuickSettingsController) this).mTrackingPointer = i18;
                        break;
                }
            }
        }, new IntSupplier() { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda9
            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                return QuickSettingsController.this.mTrackingPointer;
            }
        }, new QuickSettingsController$$ExternalSyntheticLambda5(this, 2), new QuickSettingsController$$ExternalSyntheticLambda5(this, 3), new Supplier() { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i17) {
                    case 0:
                        return ((QuickSettingsController) lazy).mQsFrame;
                    case 1:
                        return ((QuickSettingsController) lazy).mQs;
                    default:
                        return ((NotificationPanelViewController) ((Lazy) lazy).get()).mView;
                }
            }
        });
        this.mQuickQsOffsetHeight = SystemBarUtils.getQuickQsOffsetHeight(notificationPanelView.getContext());
        this.mPluginLockMediator = pluginLockMediator;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0066  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void applyClippingImmediately(boolean z, int i, int i2, int i3, int i4) {
        boolean z2;
        Lazy lazy;
        float f;
        ScrimView scrimView;
        boolean z3;
        int i5 = this.mScrimCornerRadius;
        Rect rect = this.mLastClipBounds;
        rect.set(i, i2, i3, i4);
        boolean z4 = this.mIsFullWidth;
        ScrimController scrimController = this.mScrimController;
        float f2 = 0.0f;
        if (z4) {
            if (this.mSplitShadeEnabled) {
                RecordingController recordingController = this.mRecordingController;
                synchronized (recordingController) {
                    z3 = recordingController.mIsRecording;
                }
                if (!z3 && !((CastControllerImpl) this.mCastController).getCastDevices().stream().anyMatch(new CastControllerImpl$$ExternalSyntheticLambda0())) {
                    f = this.mScreenCornerRadius;
                    float f3 = this.mScrimCornerRadius;
                    int lerp = (int) MathUtils.lerp(f, f3, Math.min(i2 / f3, 1.0f));
                    if (!this.mExpanded) {
                        f = calculateBottomCornerRadius(f);
                    }
                    scrimView = scrimController.mNotificationsScrim;
                    if (scrimView != null) {
                        Drawable drawable = scrimView.mDrawable;
                        if (drawable instanceof ScrimDrawable) {
                            ScrimDrawable scrimDrawable = (ScrimDrawable) drawable;
                            if (scrimDrawable.mBottomEdgeRadius != f) {
                                scrimDrawable.mBottomEdgeRadius = f;
                                scrimDrawable.invalidateSelf();
                            }
                        }
                    }
                    i5 = lerp;
                    z2 = z;
                }
            }
            f = 0.0f;
            float f32 = this.mScrimCornerRadius;
            int lerp2 = (int) MathUtils.lerp(f, f32, Math.min(i2 / f32, 1.0f));
            if (!this.mExpanded) {
            }
            scrimView = scrimController.mNotificationsScrim;
            if (scrimView != null) {
            }
            i5 = lerp2;
            z2 = z;
        } else {
            z2 = false;
        }
        boolean isQsFragmentCreated = isQsFragmentCreated();
        AmbientState ambientState = this.mAmbientState;
        Lazy lazy2 = this.mPanelViewControllerLazy;
        if (isQsFragmentCreated) {
            boolean z5 = this.mPulseExpansionHandler.isExpanding;
            if (z5 || (this.mClippingAnimator != null && (this.mIsTranslationResettingAnimator || this.mIsPulseExpansionResettingAnimator))) {
                if (z5 || this.mIsPulseExpansionResettingAnimator) {
                    f2 = Math.max(0.0f, (i2 - this.mQs.getHeader().getHeight()) / 2.0f);
                } else if (!this.mSplitShadeEnabled) {
                    f2 = (i2 - this.mQs.getHeader().getHeight()) * 0.175f;
                }
            }
            this.mTranslationForFullShadeTransition = f2;
            int i6 = ((NotificationPanelViewController) lazy2.get()).mNavigationBarBottomHeight;
            int i7 = ambientState.mStackTopMargin;
            QsFrameTranslateController qsFrameTranslateController = this.mQsFrameTranslateController;
            FrameLayout frameLayout = this.mQsFrame;
            qsFrameTranslateController.translateQsFrame();
            float translationY = frameLayout.getTranslationY();
            int top = this.mEnableClipping ? (int) ((i2 - translationY) - frameLayout.getTop()) : 0;
            int top2 = this.mEnableClipping ? (int) ((i4 - translationY) - frameLayout.getTop()) : 0;
            this.mVisible = z;
            this.mQs.setQsVisible(z);
            lazy = lazy2;
            this.mQs.setFancyClipping(this.mDisplayLeftInset, top, this.mDisplayRightInset, top2, i5, z && !this.mSplitShadeEnabled, this.mIsFullWidth);
        } else {
            lazy = lazy2;
        }
        float f4 = i;
        float f5 = i2;
        float f6 = i3;
        float f7 = this.mSplitShadeEnabled ? i4 : i4 + i5;
        ScrimView scrimView2 = scrimController.mNotificationsScrim;
        if (scrimView2.mDrawableBounds == null) {
            scrimView2.mDrawableBounds = new Rect();
        }
        scrimView2.mDrawableBounds.set((int) f4, (int) f5, (int) f6, (int) f7);
        scrimView2.mDrawable.setBounds(scrimView2.mDrawableBounds);
        NotificationPanelViewController$$ExternalSyntheticLambda2 notificationPanelViewController$$ExternalSyntheticLambda2 = this.mApplyClippingImmediatelyListener;
        if (notificationPanelViewController$$ExternalSyntheticLambda2 != null) {
            boolean isQsFragmentCreated2 = isQsFragmentCreated();
            boolean z6 = this.mVisible;
            NotificationPanelViewController notificationPanelViewController = notificationPanelViewController$$ExternalSyntheticLambda2.f$0;
            if (isQsFragmentCreated2) {
                ((KeyguardRepositoryImpl) notificationPanelViewController.mKeyguardInteractor.repository)._isQuickSettingsVisible.setValue(Boolean.valueOf(z6));
            }
            KeyguardStatusViewController keyguardStatusViewController = notificationPanelViewController.mKeyguardStatusViewController;
            Rect rect2 = z2 ? rect : null;
            if (rect2 != null) {
                keyguardStatusViewController.getClass();
                int i8 = rect2.left;
                int y = (int) (rect2.top - ((KeyguardStatusView) keyguardStatusViewController.mView).getY());
                int i9 = rect2.right;
                int y2 = (int) (rect2.bottom - ((KeyguardStatusView) keyguardStatusViewController.mView).getY());
                Rect rect3 = keyguardStatusViewController.mClipBounds;
                rect3.set(i8, y, i9, y2);
                ((KeyguardStatusView) keyguardStatusViewController.mView).setClipBounds(rect3);
            } else {
                ((KeyguardStatusView) keyguardStatusViewController.mView).setClipBounds(null);
            }
            if (notificationPanelViewController.mSplitShadeEnabled) {
                KeyguardStatusBarView keyguardStatusBarView = (KeyguardStatusBarView) notificationPanelViewController.mKeyguardStatusBarViewController.mView;
                if (keyguardStatusBarView.mTopClipping != 0) {
                    keyguardStatusBarView.mTopClipping = 0;
                    keyguardStatusBarView.mClipRect.set(0, 0, keyguardStatusBarView.getWidth(), keyguardStatusBarView.getHeight());
                    keyguardStatusBarView.setClipBounds(keyguardStatusBarView.mClipRect);
                }
            } else {
                KeyguardStatusBarView keyguardStatusBarView2 = (KeyguardStatusBarView) notificationPanelViewController.mKeyguardStatusBarViewController.mView;
                int top3 = i2 - keyguardStatusBarView2.getTop();
                if (top3 != keyguardStatusBarView2.mTopClipping) {
                    keyguardStatusBarView2.mTopClipping = top3;
                    keyguardStatusBarView2.mClipRect.set(0, top3, keyguardStatusBarView2.getWidth(), keyguardStatusBarView2.getHeight());
                    keyguardStatusBarView2.setClipBounds(keyguardStatusBarView2.mClipRect);
                }
            }
        }
        ScrimView scrimView3 = scrimController.mScrimBehind;
        if (scrimView3 != null && scrimController.mNotificationsScrim != null) {
            scrimView3.setCornerRadius(i5);
            scrimController.mNotificationsScrim.setCornerRadius(i5);
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        int left = i - notificationStackScrollLayoutController.mView.getLeft();
        if (!this.mIsFullWidth) {
            left -= this.mDisplayLeftInset;
        }
        int left2 = i3 - notificationStackScrollLayoutController.mView.getLeft();
        if (!this.mIsFullWidth) {
            left2 -= this.mDisplayLeftInset;
        }
        int top4 = (this.mSplitShadeEnabled && ((NotificationPanelViewController) lazy.get()).mExpandingFromHeadsUp) ? -ambientState.mStackTopMargin : i2 - notificationStackScrollLayoutController.mView.getTop();
        int top5 = i4 - notificationStackScrollLayoutController.mView.getTop();
        boolean z7 = this.mSplitShadeEnabled;
        if (!z7) {
            i5 = 0;
        }
        if (z7) {
            boolean z8 = ((NotificationPanelViewController) lazy.get()).mExpandingFromHeadsUp;
        }
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        if (notificationStackScrollLayout.mRoundedRectClippingLeft == left && notificationStackScrollLayout.mRoundedRectClippingRight == left2 && notificationStackScrollLayout.mRoundedRectClippingBottom == top5 && notificationStackScrollLayout.mRoundedRectClippingTop == top4) {
            float[] fArr = notificationStackScrollLayout.mBgCornerRadii;
            if (fArr[0] == 0 && fArr[5] == i5) {
                return;
            }
        }
        notificationStackScrollLayout.mRoundedRectClippingLeft = left;
        notificationStackScrollLayout.mRoundedRectClippingTop = top4;
        notificationStackScrollLayout.mRoundedRectClippingBottom = top5;
        notificationStackScrollLayout.mRoundedRectClippingRight = left2;
        float[] fArr2 = notificationStackScrollLayout.mBgCornerRadii;
        float f8 = 0;
        fArr2[0] = f8;
        fArr2[1] = f8;
        fArr2[2] = f8;
        fArr2[3] = f8;
        float f9 = i5;
        fArr2[4] = f9;
        fArr2[5] = f9;
        fArr2[6] = f9;
        fArr2[7] = f9;
        notificationStackScrollLayout.mRoundedClipPath.reset();
        notificationStackScrollLayout.mRoundedClipPath.addRoundRect(left, top4, left2, top5, notificationStackScrollLayout.mBgCornerRadii, Path.Direction.CW);
        if (notificationStackScrollLayout.mShouldUseRoundedRectClipping) {
            notificationStackScrollLayout.invalidate();
        }
    }

    public final void beginJankMonitoring(boolean z) {
        InteractionJankMonitor interactionJankMonitor = this.mInteractionJankMonitor;
        if (interactionJankMonitor == null) {
            return;
        }
        interactionJankMonitor.begin(InteractionJankMonitor.Configuration.Builder.withView(0, this.mPanelView).setTag(z ? "Expand" : "Collapse"));
    }

    public int calculateBottomCornerRadius(float f) {
        return (int) MathUtils.lerp(f, this.mScrimCornerRadius, Math.min(calculateBottomRadiusProgress(), 1.0f));
    }

    public final int calculateBottomPosition(float f) {
        return (int) MathUtils.lerp(this.mQs.getQsMinExpansionHeight() + ((int) getHeaderTranslation()), this.mQs.getDesiredHeight() + 0, f);
    }

    public float calculateBottomRadiusProgress() {
        return (1.0f - this.mScrimController.mNotificationsScrim.getScaleY()) * 100.0f;
    }

    public final float calculateNotificationsTopPadding(int i) {
        boolean z = this.mBarState == 1;
        if (this.mSplitShadeEnabled) {
            if (z) {
                return i;
            }
            return 0.0f;
        }
        if (this.mSizeChangeAnimator != null) {
            return Math.max(((Integer) r3.getAnimatedValue()).intValue(), i);
        }
        if (z) {
            return MathUtils.lerp(i, this.mMaxExpansionHeight, computeExpansionFraction());
        }
        return Math.max(this.mQsFrameTranslateController.getNotificationsTopPadding(this.mExpansionHeight), this.mQuickQsHeaderHeight);
    }

    public final int calculatePanelHeightExpanded(int i) {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        int height = notificationStackScrollLayoutController.getHeight();
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        float emptyBottomMargin = (height - notificationStackScrollLayout.getEmptyBottomMargin()) - notificationStackScrollLayout.mTopPadding;
        if (notificationStackScrollLayoutController.getNotGoneChildCount() == 0) {
            notificationStackScrollLayout.getClass();
            boolean z = NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW;
            if (z ? false : notificationStackScrollLayout.mEmptyShadeView.mIsVisible) {
                notificationStackScrollLayout.getClass();
                emptyBottomMargin = z ? 0 : notificationStackScrollLayout.mEmptyShadeView.getHeight();
            }
        }
        int i2 = this.mMaxExpansionHeight;
        ValueAnimator valueAnimator = this.mSizeChangeAnimator;
        if (valueAnimator != null) {
            i2 = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        }
        if (this.mBarState != 1) {
            i = 0;
        }
        float max = Math.max(i2, i) + emptyBottomMargin + notificationStackScrollLayout.mTopPaddingOverflow;
        if (max > notificationStackScrollLayoutController.getHeight()) {
            max = Math.max(notificationStackScrollLayout.getLayoutMinHeight() + i2, notificationStackScrollLayoutController.getHeight());
        }
        return (int) max;
    }

    public final int calculateTopClippingBound(int i) {
        if (this.mSplitShadeEnabled) {
            return Math.min(i, 0);
        }
        boolean z = NotiRune.NOTI_STYLE_TABLET_BG;
        Lazy lazy = this.mPanelViewControllerLazy;
        float edgePosition = (!z || this.mExpanded) ? getEdgePosition() : Math.min(getEdgePosition(), ((NotificationPanelViewController) lazy.get()).mExpandedHeight - this.mShelfHeight);
        this.mAmbientState.mNotificationScrimTop = edgePosition;
        if (this.mBarState == 1) {
            if (!this.mKeyguardBypassController.getBypassEnabled()) {
                i = (int) Math.min(i, edgePosition);
            }
        } else if (!this.mExpanded) {
            i = (int) edgePosition;
        }
        int i2 = (int) (i + ((NotificationPanelViewController) lazy.get()).mOverStretchAmount);
        float f = ((NotificationPanelViewController) lazy.get()).mMinFraction;
        if (f <= 0.0f || f >= 1.0f) {
            return i2;
        }
        return (int) (MathUtils.saturate(((this.mShadeExpandedFraction - f) / (1.0f - f)) / f) * i2);
    }

    public final void closeQs() {
        if (this.mSplitShadeEnabled) {
            this.mShadeLog.m189d("Closing QS while in split shade");
        }
        ValueAnimator valueAnimator = this.mExpansionAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        setExpansionHeight(this.mMinExpansionHeight);
        setExpandImmediate(false);
    }

    public final float computeExpansionFraction() {
        int i;
        int i2;
        if (this.mAnimatingHiddenFromCollapsed || (i = this.mMaxExpansionHeight) == (i2 = this.mMinExpansionHeight)) {
            return 0.0f;
        }
        return Math.min(1.0f, (this.mExpansionHeight - i2) / (i - i2));
    }

    public final boolean disallowTouches() {
        SecQSFragment secQSFragment;
        Boolean valueOf;
        Object obj = this.mSecQuickSettingsController.qsSupplier.get();
        QSFragment qSFragment = obj instanceof QSFragment ? (QSFragment) obj : null;
        if (qSFragment == null || (secQSFragment = qSFragment.mSecQSFragment) == null) {
            return false;
        }
        SecQSFragmentAnimatorManager secQSFragmentAnimatorManager = secQSFragment.quickAnimation.secQSFragmentAnimatorManager;
        return (secQSFragmentAnimatorManager == null || (valueOf = Boolean.valueOf(secQSFragmentAnimatorManager.isDetailVisible())) == null) ? false : valueOf.booleanValue();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("QuickSettingsController:");
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.increaseIndent();
        asIndenting.print("mIsFullWidth=");
        asIndenting.println(this.mIsFullWidth);
        asIndenting.print("mTouchSlop=");
        asIndenting.println(this.mTouchSlop);
        asIndenting.print("mSlopMultiplier=");
        asIndenting.println(this.mSlopMultiplier);
        asIndenting.print("mBarState=");
        asIndenting.println(this.mBarState);
        asIndenting.print("mStatusBarMinHeight=");
        asIndenting.println(this.mStatusBarMinHeight);
        asIndenting.print("mScrimEnabled=");
        asIndenting.println(this.mScrimEnabled);
        asIndenting.print("mScrimCornerRadius=");
        asIndenting.println(this.mScrimCornerRadius);
        asIndenting.print("mScreenCornerRadius=");
        asIndenting.println(this.mScreenCornerRadius);
        asIndenting.print("mUseLargeScreenShadeHeader=");
        asIndenting.println(this.mUseLargeScreenShadeHeader);
        asIndenting.print("mLargeScreenShadeHeaderHeight=");
        asIndenting.println(0);
        asIndenting.print("mDisplayRightInset=");
        asIndenting.println(this.mDisplayRightInset);
        asIndenting.print("mDisplayLeftInset=");
        asIndenting.println(this.mDisplayLeftInset);
        asIndenting.print("mSplitShadeEnabled=");
        asIndenting.println(this.mSplitShadeEnabled);
        asIndenting.print("mLockscreenNotificationPadding=");
        asIndenting.println(this.mLockscreenNotificationPadding);
        asIndenting.print("mSplitShadeNotificationsScrimMarginBottom=");
        asIndenting.println(this.mSplitShadeNotificationsScrimMarginBottom);
        asIndenting.print("mDozing=");
        asIndenting.println(this.mDozing);
        asIndenting.print("mEnableClipping=");
        asIndenting.println(this.mEnableClipping);
        asIndenting.print("mFalsingThreshold=");
        asIndenting.println(this.mFalsingThreshold);
        asIndenting.print("mTransitionToFullShadePosition=");
        asIndenting.println(0);
        asIndenting.print("mCollapsedOnDown=");
        asIndenting.println(this.mCollapsedOnDown);
        asIndenting.print("mShadeExpandedHeight=");
        asIndenting.println(this.mShadeExpandedHeight);
        asIndenting.print("mLastShadeFlingWasExpanding=");
        asIndenting.println(this.mLastShadeFlingWasExpanding);
        asIndenting.print("mInitialHeightOnTouch=");
        asIndenting.println(this.mInitialHeightOnTouch);
        asIndenting.print("mInitialTouchX=");
        asIndenting.println(this.mInitialTouchX);
        asIndenting.print("mInitialTouchY=");
        asIndenting.println(this.mInitialTouchY);
        asIndenting.print("mTouchAboveFalsingThreshold=");
        asIndenting.println(this.mTouchAboveFalsingThreshold);
        asIndenting.print("mTracking=");
        asIndenting.println(this.mTracking);
        asIndenting.print("mTrackingPointer=");
        asIndenting.println(this.mTrackingPointer);
        asIndenting.print("mExpanded=");
        asIndenting.println(this.mExpanded);
        asIndenting.print("mFullyExpanded=");
        asIndenting.println(this.mFullyExpanded);
        asIndenting.print("mExpandImmediate=");
        asIndenting.println(this.mExpandImmediate);
        asIndenting.print("mExpandedWhenExpandingStarted=");
        asIndenting.println(this.mExpandedWhenExpandingStarted);
        asIndenting.print("mAnimatingHiddenFromCollapsed=");
        asIndenting.println(this.mAnimatingHiddenFromCollapsed);
        asIndenting.print("mVisible=");
        asIndenting.println(this.mVisible);
        asIndenting.print("mExpansionHeight=");
        asIndenting.println(this.mExpansionHeight);
        asIndenting.print("mMinExpansionHeight=");
        asIndenting.println(this.mMinExpansionHeight);
        asIndenting.print("mMaxExpansionHeight=");
        asIndenting.println(this.mMaxExpansionHeight);
        asIndenting.print("mShadeExpandedFraction=");
        asIndenting.println(this.mShadeExpandedFraction);
        asIndenting.print("mPeekHeight=");
        asIndenting.println(this.mPeekHeight);
        asIndenting.print("mLastOverscroll=");
        asIndenting.println(this.mLastOverscroll);
        asIndenting.print("mExpansionFromOverscroll=");
        asIndenting.println(this.mExpansionFromOverscroll);
        asIndenting.print("mExpansionEnabledPolicy=");
        asIndenting.println(this.mExpansionEnabledPolicy);
        asIndenting.print("mExpansionEnabledAmbient=");
        asIndenting.println(this.mExpansionEnabledAmbient);
        asIndenting.print("mQuickQsHeaderHeight=");
        asIndenting.println(this.mQuickQsHeaderHeight);
        asIndenting.print("mTwoFingerExpandPossible=");
        asIndenting.println(this.mTwoFingerExpandPossible);
        asIndenting.print("mConflictingExpansionGesture=");
        asIndenting.println(this.mConflictingExpansionGesture);
        asIndenting.print("mAnimatorExpand=");
        asIndenting.println(this.mAnimatorExpand);
        asIndenting.print("mCachedGestureInsets=");
        asIndenting.println(this.mCachedGestureInsets);
        asIndenting.print("mTransitioningToFullShadeProgress=");
        asIndenting.println(0.0f);
        asIndenting.print("mDistanceForFullShadeTransition=");
        asIndenting.println(this.mDistanceForFullShadeTransition);
        asIndenting.print("mStackScrollerOverscrolling=");
        asIndenting.println(this.mStackScrollerOverscrolling);
        asIndenting.print("mAnimating=");
        asIndenting.println(this.mAnimating);
        asIndenting.print("mIsTranslationResettingAnimator=");
        asIndenting.println(this.mIsTranslationResettingAnimator);
        asIndenting.print("mIsPulseExpansionResettingAnimator=");
        asIndenting.println(this.mIsPulseExpansionResettingAnimator);
        asIndenting.print("mTranslationForFullShadeTransition=");
        asIndenting.println(this.mTranslationForFullShadeTransition);
        asIndenting.print("mAnimateNextNotificationBounds=");
        asIndenting.println(this.mAnimateNextNotificationBounds);
        asIndenting.print("mNotificationBoundsAnimationDelay=");
        asIndenting.println(this.mNotificationBoundsAnimationDelay);
        asIndenting.print("mNotificationBoundsAnimationDuration=");
        asIndenting.println(this.mNotificationBoundsAnimationDuration);
        asIndenting.print("mLastClippingTopBound=");
        asIndenting.println(0);
        asIndenting.print("mLastNotificationsTopPadding=");
        asIndenting.println(0);
        asIndenting.print("mLastNotificationsClippingTopBound=");
        asIndenting.println(0);
        asIndenting.print("mLastNotificationsClippingTopBoundNssl=");
        asIndenting.println(0);
        asIndenting.print("mInterceptRegion=");
        asIndenting.println(this.mInterceptRegion);
        asIndenting.print("mClippingAnimationEndBounds=");
        asIndenting.println(this.mClippingAnimationEndBounds);
        asIndenting.print("mLastClipBounds=");
        asIndenting.println(this.mLastClipBounds);
    }

    public final void flingQs(float f, int i) {
        flingQs(f, i, null, false);
    }

    public final float getCurrentVelocity() {
        VelocityTracker velocityTracker = this.mQsVelocityTracker;
        if (velocityTracker == null) {
            return 0.0f;
        }
        velocityTracker.computeCurrentVelocity(1000);
        return this.mQsVelocityTracker.getYVelocity();
    }

    public final float getEdgePosition() {
        float height = this.mShadeHeaderController.header.getHeight();
        AmbientState ambientState = this.mAmbientState;
        float f = ambientState.mExpansionFraction;
        return Math.max(height * f, ((ambientState.mStackTopMargin * f) + ambientState.mStackY) - ambientState.mScrollY);
    }

    public final boolean getExpanded() {
        return this.mExpanded;
    }

    public final boolean getFullyExpanded() {
        return this.mFullyExpanded;
    }

    public final float getHeaderTranslation() {
        if (this.mSplitShadeEnabled) {
            return 0.0f;
        }
        int i = this.mBarState;
        KeyguardBypassController keyguardBypassController = this.mKeyguardBypassController;
        if (i == 1 && !keyguardBypassController.getBypassEnabled()) {
            return -this.mQs.getQsMinExpansionHeight();
        }
        float f = this.mShadeExpandedHeight;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        float calculateAppearFraction = notificationStackScrollLayoutController.mView.calculateAppearFraction(f);
        float f2 = -this.mExpansionHeight;
        if (this.mBarState == 0) {
            f2 *= 0.175f;
        }
        if (keyguardBypassController.getBypassEnabled() && this.mBarState == 1) {
            float f3 = notificationStackScrollLayoutController.mView.mAmbientState.mPulseHeight;
            if (f3 == 100000.0f) {
                f3 = 0.0f;
            }
            calculateAppearFraction = MathUtils.smoothStep(0.0f, r0.mIntrinsicPadding, f3);
            f2 = -this.mQs.getQsMinExpansionHeight();
        }
        return Math.min(0.0f, MathUtils.lerp(f2, 0.0f, Math.min(1.0f, calculateAppearFraction)));
    }

    public int getScrimCornerRadius() {
        return this.mScrimCornerRadius;
    }

    public float getShadeExpandedHeight() {
        return this.mShadeExpandedHeight;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: IfRegionVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r13v5 float, still in use, count: 2, list:
          (r13v5 float) from 0x017c: PHI (r13v4 float) = (r13v1 float), (r13v5 float), (r13v6 float), (r13v7 float) binds: [B:74:0x0166, B:81:0x0178, B:79:0x017b, B:78:0x0172] A[DONT_GENERATE, DONT_INLINE]
          (r13v5 float) from 0x0176: CMP_L (0.0f float), (r13v5 float) A[WRAPPED] (LINE:375)
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.dex.visitors.regions.TernaryMod.makeTernaryInsn(TernaryMod.java:125)
        	at jadx.core.dex.visitors.regions.TernaryMod.processRegion(TernaryMod.java:62)
        	at jadx.core.dex.visitors.regions.TernaryMod.enterRegion(TernaryMod.java:45)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:67)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:19)
        	at jadx.core.dex.visitors.regions.TernaryMod.process(TernaryMod.java:35)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.process(IfRegionVisitor.java:34)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.visit(IfRegionVisitor.java:30)
        */
    public final boolean handleTouch(android.view.MotionEvent r18, boolean r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 739
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.QuickSettingsController.handleTouch(android.view.MotionEvent, boolean, boolean):boolean");
    }

    public boolean isConflictingExpansionGesture() {
        return this.mConflictingExpansionGesture;
    }

    public boolean isExpandImmediate() {
        return this.mExpandImmediate;
    }

    public final boolean isExpansionEnabled() {
        if (this.mExpansionEnabledPolicy && this.mExpansionEnabledAmbient) {
            return !(this.mRemoteInputManager.isRemoteInputActive() && !this.mQs.isShowingDetail());
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isOpenQsEvent(MotionEvent motionEvent) {
        boolean z;
        int pointerCount = motionEvent.getPointerCount();
        int actionMasked = motionEvent.getActionMasked();
        boolean z2 = actionMasked == 5 && pointerCount == 2;
        boolean z3 = actionMasked == 0 && (motionEvent.isButtonPressed(32) || motionEvent.isButtonPressed(64));
        boolean z4 = actionMasked == 0 && (motionEvent.isButtonPressed(2) || motionEvent.isButtonPressed(4));
        SecQuickSettingsController secQuickSettingsController = this.mSecQuickSettingsController;
        if (z2) {
            secQuickSettingsController.openedByTwoFingerDragging = true;
        }
        if (!z2 && !z3 && !z4) {
            float x = motionEvent.getX();
            SecExpandQSAtOnceController secExpandQSAtOnceController = secQuickSettingsController.expandQSAtOnceController;
            SettingsHelper settingsHelper = secExpandQSAtOnceController.mSettingsHelper;
            if ((settingsHelper == null ? false : settingsHelper.isExpandQsAtOnceEnabled()) && actionMasked == 0) {
                StringBuilder sb = new StringBuilder("isOpenqSEvent DOWN 00");
                sb.append((int) x);
                sb.append("00 mDisplayWidthOfDivider = ");
                sb.append(secExpandQSAtOnceController.mDisplayWidthOfDivider);
                sb.append(" getSidePosition() = ");
                SettingsHelper settingsHelper2 = secExpandQSAtOnceController.mSettingsHelper;
                String stringValue = settingsHelper2.mItemLists.get("swipe_directly_to_quick_setting_position").getStringValue();
                if (stringValue == null) {
                    stringValue = "right";
                }
                ExifInterface$$ExternalSyntheticOutline0.m35m(sb, stringValue, "ExpandQSAtOnceController");
                String stringValue2 = settingsHelper2.mItemLists.get("swipe_directly_to_quick_setting_position").getStringValue();
                if (stringValue2 == null) {
                    stringValue2 = "right";
                }
                if (!"right".equals(stringValue2) || x < secExpandQSAtOnceController.mDisplayWidthOfDivider) {
                    String stringValue3 = settingsHelper2.mItemLists.get("swipe_directly_to_quick_setting_position").getStringValue();
                    if ("left".equals(stringValue3 != null ? stringValue3 : "right") && x < secExpandQSAtOnceController.mDisplayWidthOfDivider) {
                        secExpandQSAtOnceController.mShouldCloseAtOnce = true;
                    }
                } else {
                    secExpandQSAtOnceController.mShouldCloseAtOnce = true;
                }
                z = true;
                if (!z) {
                    return false;
                }
            }
            z = false;
            if (!z) {
            }
        }
        return true;
    }

    public final boolean isQsFragmentCreated() {
        return this.mQs != null;
    }

    public boolean isTracking() {
        return this.mTracking;
    }

    public boolean isTrackingBlocked() {
        return this.mConflictingExpansionGesture && this.mExpanded;
    }

    public boolean isTwoFingerExpandPossible() {
        return this.mTwoFingerExpandPossible;
    }

    public final void onExpansionStarted() {
        SecQSPanelController secQSPanelController;
        InterfaceC1922QS interfaceC1922QS = this.mQs;
        if (interfaceC1922QS != null && (secQSPanelController = ((QSFragment) interfaceC1922QS).mQSPanelController) != null && !secQSPanelController.mExpandSettingsPanel) {
            ValueAnimator valueAnimator = this.mExpansionAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            ((NotificationPanelViewController) this.mPanelViewControllerLazy.get()).cancelHeightAnimator();
        }
        DejankUtils.notifyRendererOfExpensiveFrame(this.mPanelView, "onExpansionStarted");
        setExpansionHeight(this.mExpansionHeight);
        this.mNotificationStackScrollLayoutController.checkSnoozeLeavebehind();
    }

    public void onHeightChanged() {
        int desiredHeight = isQsFragmentCreated() ? this.mQs.getDesiredHeight() : 0;
        this.mMaxExpansionHeight = desiredHeight;
        if (this.mExpanded && this.mFullyExpanded) {
            this.mExpansionHeight = desiredHeight;
            NotificationPanelViewController$$ExternalSyntheticLambda2 notificationPanelViewController$$ExternalSyntheticLambda2 = this.mExpansionHeightSetToMaxListener;
            if (notificationPanelViewController$$ExternalSyntheticLambda2 != null) {
                NotificationPanelViewController notificationPanelViewController = notificationPanelViewController$$ExternalSyntheticLambda2.f$0;
                notificationPanelViewController.requestScrollerTopPaddingUpdate(false);
                notificationPanelViewController.updateExpandedHeightToMaxHeight();
            }
        }
        if (this.mAccessibilityManager.isEnabled()) {
            this.mPanelView.setAccessibilityPaneTitle(((NotificationPanelViewController) this.mPanelViewControllerLazy.get()).determineAccessibilityPaneTitle());
        }
        this.mNotificationStackScrollLayoutController.mView.mMaxTopPadding = this.mMaxExpansionHeight;
    }

    public final void setClippingBounds() {
        int right;
        int i;
        float computeExpansionFraction = computeExpansionFraction();
        int calculateBottomPosition = calculateBottomPosition(computeExpansionFraction);
        boolean z = this.mSplitShadeEnabled;
        boolean z2 = (!z && (computeExpansionFraction > 0.0f ? 1 : (computeExpansionFraction == 0.0f ? 0 : -1)) == 0 && calculateBottomPosition > 0) || ((computeExpansionFraction > 0.0f ? 1 : (computeExpansionFraction == 0.0f ? 0 : -1)) > 0);
        if (z) {
            boolean z3 = this.mBarState == 1 && ((NotificationPanelViewController) this.mPanelViewControllerLazy.get()).mKeyguardOnlyContentAlpha == 1.0f;
            if (computeExpansionFraction == 1.0f && z3) {
                Log.wtf("QuickSettingsController", "Incorrect state, scrim is visible at the same time when clock is visible");
            }
        }
        int calculateTopClippingBound = calculateTopClippingBound(calculateBottomPosition);
        boolean z4 = this.mSplitShadeEnabled;
        NotificationPanelView notificationPanelView = this.mPanelView;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        int height = z4 ? notificationStackScrollLayoutController.getHeight() + calculateTopClippingBound + this.mSplitShadeNotificationsScrimMarginBottom : notificationPanelView.getBottom();
        int left = this.mIsFullWidth ? 0 : notificationStackScrollLayoutController.mView.getLeft() + this.mDisplayLeftInset;
        if (this.mIsFullWidth) {
            right = notificationPanelView.getRight();
            i = this.mDisplayRightInset;
        } else {
            right = notificationStackScrollLayoutController.mView.getRight();
            i = this.mDisplayLeftInset;
        }
        int i2 = right + i;
        int min = Math.min(calculateTopClippingBound, height);
        boolean z5 = this.mAnimateNextNotificationBounds;
        Rect rect = this.mClippingAnimationEndBounds;
        if (z5) {
            Rect rect2 = this.mLastClipBounds;
            if (!rect2.isEmpty()) {
                rect.set(left, min, i2, height);
                final int i3 = rect2.left;
                final int i4 = rect2.top;
                final int i5 = rect2.right;
                final int i6 = rect2.bottom;
                ValueAnimator valueAnimator = this.mClippingAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.mClippingAnimator = ofFloat;
                ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                this.mClippingAnimator.setDuration(this.mNotificationBoundsAnimationDuration);
                this.mClippingAnimator.setStartDelay(this.mNotificationBoundsAnimationDelay);
                final boolean z6 = z2;
                this.mClippingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        QuickSettingsController quickSettingsController = QuickSettingsController.this;
                        int i7 = i3;
                        int i8 = i4;
                        int i9 = i5;
                        int i10 = i6;
                        boolean z7 = z6;
                        quickSettingsController.getClass();
                        float animatedFraction = valueAnimator2.getAnimatedFraction();
                        Rect rect3 = quickSettingsController.mClippingAnimationEndBounds;
                        quickSettingsController.applyClippingImmediately(z7, (int) MathUtils.lerp(i7, rect3.left, animatedFraction), (int) MathUtils.lerp(i8, rect3.top, animatedFraction), (int) MathUtils.lerp(i9, rect3.right, animatedFraction), (int) MathUtils.lerp(i10, rect3.bottom, animatedFraction));
                    }
                });
                this.mClippingAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.QuickSettingsController.2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        QuickSettingsController quickSettingsController = QuickSettingsController.this;
                        quickSettingsController.mClippingAnimator = null;
                        quickSettingsController.mIsTranslationResettingAnimator = false;
                        quickSettingsController.mIsPulseExpansionResettingAnimator = false;
                    }
                });
                this.mClippingAnimator.start();
                this.mAnimateNextNotificationBounds = false;
                this.mNotificationBoundsAnimationDelay = 0L;
            }
        }
        if (this.mClippingAnimator != null) {
            rect.set(left, min, i2, height);
        } else {
            applyClippingImmediately(z2, left, min, i2, height);
        }
        this.mAnimateNextNotificationBounds = false;
        this.mNotificationBoundsAnimationDelay = 0L;
    }

    public void setExpandImmediate(boolean z) {
        if (z != this.mExpandImmediate) {
            ShadeLogger shadeLogger = this.mShadeLog;
            shadeLogger.getClass();
            LogLevel logLevel = LogLevel.VERBOSE;
            ShadeLogger$logQsExpandImmediateChanged$2 shadeLogger$logQsExpandImmediateChanged$2 = new Function1() { // from class: com.android.systemui.shade.ShadeLogger$logQsExpandImmediateChanged$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return AbstractC0866xb1ce8deb.m86m("qsExpandImmediate=", ((LogMessage) obj).getBool1());
                }
            };
            LogBuffer logBuffer = shadeLogger.buffer;
            LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$logQsExpandImmediateChanged$2, null);
            obtain.setBool1(z);
            logBuffer.commit(obtain);
            this.mExpandImmediate = z;
            Iterator it = this.mShadeExpansionStateManager.shadeStateEventsListeners.iterator();
            while (it.hasNext()) {
                ((ShadeStateEvents.ShadeStateEventsListener) it.next()).onExpandImmediateChanged(z);
            }
        }
    }

    public void setExpanded(boolean z) {
        if (this.mExpanded != z) {
            StringBuilder sb = this.mLogBuilder;
            sb.setLength(0);
            sb.append("QsExpand changed expanded : " + z);
            sb.append("\n");
            sb.append(Debug.getCallers(5, " - "));
            ((SecPanelLoggerImpl) this.mPanelLogger).addPanelStateInfoLog(sb, false);
            this.mExpanded = z;
            updateQsState();
            ShadeExpansionStateManager shadeExpansionStateManager = this.mShadeExpansionStateManager;
            boolean z2 = shadeExpansionStateManager.qsExpanded != z;
            shadeExpansionStateManager.qsExpanded = z;
            if (z2) {
                StringBuilder sb2 = shadeExpansionStateManager.logBuilder;
                sb2.setLength(0);
                sb2.append("ShadeExpansionStateManager onQsExpansionChanged : " + z);
                ((SecPanelLoggerImpl) ((SecPanelLogger) shadeExpansionStateManager.panelLogger$delegate.getValue())).addPanelStateInfoLog(sb2, true);
            }
            int i = ShadeExpansionStateManagerKt.$r8$clinit;
            Iterator it = shadeExpansionStateManager.qsExpansionListeners.iterator();
            while (it.hasNext()) {
                ((ShadeQsExpansionListener) it.next()).onQsExpansionChanged(z);
            }
            int i2 = this.mMinExpansionHeight;
            int i3 = this.mMaxExpansionHeight;
            boolean z3 = this.mStackScrollerOverscrolling;
            boolean z4 = this.mAnimatorExpand;
            boolean z5 = this.mAnimating;
            ShadeLogger shadeLogger = this.mShadeLog;
            shadeLogger.getClass();
            LogLevel logLevel = LogLevel.VERBOSE;
            ShadeLogger$logQsExpansionChanged$2 shadeLogger$logQsExpansionChanged$2 = new Function1() { // from class: com.android.systemui.shade.ShadeLogger$logQsExpansionChanged$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    return logMessage.getStr1() + " qsExpanded=" + logMessage.getBool1() + ",qsMinExpansionHeight=" + logMessage.getInt1() + ",qsMaxExpansionHeight=" + logMessage.getInt2() + ",stackScrollerOverscrolling=" + logMessage.getBool2() + ",qsAnimatorExpand=" + logMessage.getBool3() + ",animatingQs=" + logMessage.getLong1();
                }
            };
            LogBuffer logBuffer = shadeLogger.buffer;
            LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$logQsExpansionChanged$2, null);
            obtain.setStr1("QS Expansion Changed.");
            obtain.setBool1(z);
            obtain.setInt1(i2);
            obtain.setInt2(i3);
            obtain.setBool2(z3);
            obtain.setBool3(z4);
            obtain.setLong1(Boolean.compare(z5, false));
            logBuffer.commit(obtain);
            Lazy lazy = this.mPanelViewControllerLazy;
            KeyguardBottomAreaView keyguardBottomAreaView = ((NotificationPanelViewController) lazy.get()).mKeyguardBottomArea;
            keyguardBottomAreaView.setAllChildEnabled(keyguardBottomAreaView, !z);
            String str = LsRune.VALUE_CONFIG_CARRIER_TEXT_POLICY;
            this.mKeyguardUpdateMonitor.setPanelExpandingStarted(this.mExpanded);
            KeyguardSecAffordanceHelper keyguardSecAffordanceHelper = ((NotificationPanelViewController) lazy.get()).mSecAffordanceHelper;
            KeyguardSecAffordanceView keyguardSecAffordanceView = keyguardSecAffordanceHelper.mLeftIcon;
            Intrinsics.checkNotNull(keyguardSecAffordanceView);
            keyguardSecAffordanceView.mQsExpanded = z;
            KeyguardSecAffordanceView keyguardSecAffordanceView2 = keyguardSecAffordanceHelper.mRightIcon;
            Intrinsics.checkNotNull(keyguardSecAffordanceView2);
            keyguardSecAffordanceView2.mQsExpanded = z;
            if (this.mExpanded) {
                ((NotificationRemoteInputManager) Dependency.get(NotificationRemoteInputManager.class)).closeRemoteInputs(false);
            }
            this.mAmbientState.mIsFullyExpanding = z;
        }
        updateNightMode(this.mPanelView.getVisibility());
    }

    public final void setExpansionHeight(float f) {
        boolean z = this.mSplitShadeEnabled;
        Lazy lazy = this.mPanelViewControllerLazy;
        if (z && f == 0.0f && ((NotificationPanelViewController) lazy.get()).isShadeFullyExpanded()) {
            Log.wtfStack("QuickSettingsController", "qsExpansion set to 0 while split shade is expanding or open");
        }
        int i = this.mMaxExpansionHeight;
        int i2 = this.mMinExpansionHeight;
        if (i2 > i) {
            updateMinHeight();
            i = this.mMaxExpansionHeight;
            i2 = this.mMinExpansionHeight;
        }
        float f2 = i;
        float min = Math.min(Math.max(f, this.mMinExpansionHeight), f2);
        this.mFullyExpanded = (min != f2 || i == 0 || i2 == i) ? false : true;
        boolean z2 = !this.mAnimatorExpand && this.mAnimating;
        if (this.mExpandImmediate) {
            setExpanded(true);
        } else {
            int i3 = this.mMinExpansionHeight;
            if (min > i3 && !this.mExpanded && !this.mStackScrollerOverscrolling && !this.mDozing && !z2) {
                KeyguardTouchAnimator keyguardTouchAnimator = ((NotificationPanelViewController) lazy.get()).mKeyguardTouchAnimator;
                if (keyguardTouchAnimator.isViRunning()) {
                    keyguardTouchAnimator.reset();
                }
                setExpanded(true);
            } else if (min <= i3 && this.mExpanded && i2 != i) {
                setExpanded(false);
            }
        }
        this.mExpansionHeight = min;
        updateExpansion();
        boolean z3 = this.mExpanded;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (z3 && this.mFullyExpanded) {
            keyguardUpdateMonitor.dispatchStatusBarState(true);
        } else if (this.mBarState != 2) {
            keyguardUpdateMonitor.dispatchStatusBarState(false);
        }
        NotificationPanelViewController$$ExternalSyntheticLambda2 notificationPanelViewController$$ExternalSyntheticLambda2 = this.mExpansionHeightListener;
        if (notificationPanelViewController$$ExternalSyntheticLambda2 != null) {
            notificationPanelViewController$$ExternalSyntheticLambda2.f$0.onQsSetExpansionHeightCalled(this.mFullyExpanded);
        }
    }

    public void setQs(InterfaceC1922QS interfaceC1922QS) {
        this.mQs = interfaceC1922QS;
    }

    public void setStatusBarMinHeight(int i) {
        this.mStatusBarMinHeight = i;
    }

    public final void setTracking(boolean z) {
        this.mTracking = z;
        StringBuilder sb = this.mLogBuilder;
        sb.setLength(0);
        sb.append("onQSTrackingStarted: ");
        sb.append(", mTracking: ");
        sb.append(this.mTracking);
        sb.append(" -> true :");
        sb.append("\n");
        sb.append(Debug.getCallers(3, " - "));
        ((SecPanelLoggerImpl) this.mPanelLogger).addPanelStateInfoLog(sb, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:66:0x0126, code lost:
    
        if (r10 != false) goto L80;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean shouldQuickSettingsIntercept(float f, float f2, float f3) {
        InterfaceC1922QS interfaceC1922QS;
        boolean z = this.mBarState == 1;
        if (isExpansionEnabled() && !this.mCollapsedOnDown && ((!z || !this.mKeyguardBypassController.getBypassEnabled()) && !this.mSplitShadeEnabled)) {
            SecQuickSettingsController secQuickSettingsController = this.mSecQuickSettingsController;
            if ((secQuickSettingsController.notificationStackScrollLayoutController.mView.mOwnScrollY > 0) || secQuickSettingsController.expandQSAtOnceController.mShouldCloseAtOnce || secQuickSettingsController.panelBlockExpandingHelper.isDisabledExpandingOnKeyguard()) {
                return false;
            }
            View header = (z || (interfaceC1922QS = this.mQs) == null) ? this.mKeyguardStatusBar : interfaceC1922QS.getHeader();
            FrameLayout frameLayout = this.mQsFrame;
            int top = (z || this.mQs == null) ? 0 : frameLayout.getTop();
            Region region = this.mInterceptRegion;
            region.set((int) frameLayout.getX(), header.getTop() + 0, frameLayout.getWidth() + ((int) frameLayout.getX()), header.getBottom() + top);
            this.mStatusBarTouchableRegionManager.updateRegionForNotch(region);
            boolean contains = region.contains((int) f, (int) f2);
            if (!this.mExpanded) {
                return contains;
            }
            if (!contains) {
                float f4 = 0.0f;
                if (f3 < 0.0f) {
                    NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) this.mPanelViewControllerLazy.get();
                    if (!(notificationPanelViewController.mIsGestureNavigation && f2 > ((float) (notificationPanelViewController.mView.getHeight() - notificationPanelViewController.mNavigationBarBottomHeight)))) {
                        NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScrollLayoutController.mView;
                        int childCount = notificationStackScrollLayout.getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            ExpandableView childAtIndex = notificationStackScrollLayout.getChildAtIndex(i);
                            if (childAtIndex.getVisibility() != 8) {
                                float translationY = (childAtIndex.getTranslationY() + childAtIndex.mActualHeight) - childAtIndex.mClipBottomAmount;
                                if (translationY > f4) {
                                    f4 = translationY;
                                }
                            }
                        }
                        if (f2 <= f4 + notificationStackScrollLayout.mStackTranslation || f2 <= this.mQs.getView().getY() + this.mQs.getView().getHeight()) {
                            View view = (View) secQuickSettingsController.qsFrameLayoutSupplier.get();
                            boolean z2 = f >= view.getX() && f <= view.getX() + ((float) view.getWidth());
                        }
                    }
                }
                if (!(secQuickSettingsController.barState == 1)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public final void traceQsJank(boolean z, boolean z2) {
        InteractionJankMonitor interactionJankMonitor = this.mInteractionJankMonitor;
        if (interactionJankMonitor == null) {
            return;
        }
        if (z) {
            interactionJankMonitor.begin(this.mPanelView, 5);
        } else if (z2) {
            interactionJankMonitor.cancel(5);
        } else {
            interactionJankMonitor.end(5);
        }
    }

    public final void trackMovement(MotionEvent motionEvent) {
        VelocityTracker velocityTracker = this.mQsVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.addMovement(motionEvent);
        }
    }

    public final void updateExpansion() {
        PluginLockBasicManager pluginLockBasicManager;
        if (this.mQs != null) {
            Lazy lazy = this.mPanelViewControllerLazy;
            if (((NotificationPanelViewController) lazy.get()).mSecAffordanceHelper.isShortcutPreviewSwipingInProgress) {
                return;
            }
            boolean z = this.mExpandImmediate;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
            if ((!z && !this.mExpanded) || this.mSplitShadeEnabled) {
                notificationStackScrollLayoutController.mView.mStackScrollAlgorithm.getClass();
            }
            float computeExpansionFraction = computeExpansionFraction();
            SecQuickSettingsController secQuickSettingsController = this.mSecQuickSettingsController;
            QSScrimViewSwitch qSScrimViewSwitch = secQuickSettingsController.qSScrimViewSwitch;
            if (qSScrimViewSwitch.mStatusBarState == 1) {
                qSScrimViewSwitch.onPanelExpansionChanged(computeExpansionFraction);
            }
            this.mQs.setQsExpansion(this.mSplitShadeEnabled ? 1.0f : computeExpansionFraction(), this.mShadeExpandedFraction, getHeaderTranslation(), 1.0f);
            if (QuickStepContract.ALLOW_BACK_GESTURE_IN_SHADE) {
                ((NotificationPanelViewController) lazy.get()).getClass();
            }
            int calculateBottomPosition = calculateBottomPosition(computeExpansionFraction);
            ScrimController scrimController = this.mScrimController;
            scrimController.getClass();
            if (!Float.isNaN(computeExpansionFraction)) {
                float notificationScrimAlpha = ShadeInterpolation.getNotificationScrimAlpha(computeExpansionFraction);
                boolean z2 = calculateBottomPosition > 0;
                if (scrimController.mQsExpansion != notificationScrimAlpha || scrimController.mQsBottomVisible != z2) {
                    scrimController.mQsExpansion = notificationScrimAlpha;
                    scrimController.mQsBottomVisible = z2;
                    ScrimState scrimState = scrimController.mState;
                    if ((scrimState == ScrimState.SHADE_LOCKED || scrimState == ScrimState.KEYGUARD || scrimState == ScrimState.PULSING) && scrimController.mExpansionAffectsAlpha) {
                        scrimController.applyAndDispatchState();
                    }
                }
            }
            SecPanelExpansionStateModel secPanelExpansionStateModel = secQuickSettingsController.panelExpansionStateNotifier.mModel;
            if (!(secPanelExpansionStateModel.panelSecondDepthFraction == computeExpansionFraction)) {
                secPanelExpansionStateModel.panelSecondDepthFraction = computeExpansionFraction;
                secPanelExpansionStateModel.updatePanelOpenState();
            }
            SecQpBlurController secQpBlurController = secQuickSettingsController.blurController;
            secQpBlurController.getClass();
            float f = 0.0f;
            if (computeExpansionFraction == 0.0f) {
                if (QpRune.QUICK_PANEL_BLUR_MASSIVE && secQpBlurController.mQsExpanded) {
                    secQpBlurController.notifyWallpaper(false);
                }
                secQpBlurController.mQsExpanded = false;
            } else {
                if (QpRune.QUICK_PANEL_BLUR_MASSIVE && !secQpBlurController.mQsExpanded) {
                    secQpBlurController.notifyWallpaper(true);
                }
                secQpBlurController.mQsExpanded = true;
            }
            if ((secQpBlurController.mStatusBarStateController.getState() == 1) && !secQpBlurController.mIsBouncerShowing) {
                if (secQpBlurController.mPanelExpandedFraction != computeExpansionFraction) {
                    secQpBlurController.mPanelExpandedFraction = computeExpansionFraction;
                    SeslColorSpectrumView$$ExternalSyntheticOutline0.m44m(new StringBuilder("setQsPanelExpansion mPanelExpandedFraction: "), secQpBlurController.mPanelExpandedFraction, "SecQpBlurController");
                }
                secQpBlurController.mChoreographer.postFrameCallback(secQpBlurController.mUpdateBlurCallback);
            }
            setClippingBounds();
            if (this.mSplitShadeEnabled) {
                notificationStackScrollLayoutController.setQsExpansionFraction(0.0f);
            } else {
                notificationStackScrollLayoutController.setQsExpansionFraction(computeExpansionFraction);
            }
            NotificationShadeDepthController notificationShadeDepthController = this.mDepthController;
            notificationShadeDepthController.getClass();
            if (!QpRune.QUICK_PANEL_BLUR) {
                if (Float.isNaN(computeExpansionFraction)) {
                    Log.w("DepthController", "Invalid qs expansion");
                } else {
                    if (!(notificationShadeDepthController.qsPanelExpansion == computeExpansionFraction)) {
                        notificationShadeDepthController.qsPanelExpansion = computeExpansionFraction;
                        notificationShadeDepthController.scheduleUpdate();
                    }
                }
            }
            this.mStatusBarKeyguardViewManager.setQsExpansion(computeExpansionFraction);
            ((ShadeRepositoryImpl) this.mShadeRepository)._qsExpansion.setValue(Float.valueOf(computeExpansionFraction));
            if (LsRune.KEYGUARD_DCM_LIVE_UX) {
                float f2 = 1.0f - computeExpansionFraction;
                if (f2 >= 0.0f) {
                    this.mMascotViewContainer.setAlpha(f2);
                }
            }
            float computeExpansionFraction2 = this.mBarState == 1 ? computeExpansionFraction() : this.mShadeExpandedFraction;
            ShadeHeaderController shadeHeaderController = this.mShadeHeaderController;
            boolean z3 = shadeHeaderController.qsVisible;
            if (z3) {
                if (!(shadeHeaderController.shadeExpandedFraction == computeExpansionFraction2)) {
                    shadeHeaderController.shadeExpandedFraction = computeExpansionFraction2;
                }
            }
            if (shadeHeaderController.visible) {
                if (!(shadeHeaderController.qsExpandedFraction == computeExpansionFraction)) {
                    shadeHeaderController.qsExpandedFraction = computeExpansionFraction;
                }
            }
            boolean z4 = this.mVisible;
            if (z3 != z4) {
                shadeHeaderController.qsVisible = z4;
                HeaderPrivacyIconsController headerPrivacyIconsController = shadeHeaderController.privacyIconsController;
                if (z4) {
                    headerPrivacyIconsController.listening = true;
                    PrivacyItemController privacyItemController = headerPrivacyIconsController.privacyItemController;
                    PrivacyConfig privacyConfig = privacyItemController.privacyConfig;
                    headerPrivacyIconsController.micCameraIndicatorsEnabled = privacyConfig.micCameraAvailable;
                    headerPrivacyIconsController.locationIndicatorsEnabled = privacyConfig.locationAvailable;
                    privacyItemController.addCallback(headerPrivacyIconsController.picCallback);
                    ((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).registerCallback(headerPrivacyIconsController.desktopCallback);
                } else {
                    headerPrivacyIconsController.listening = false;
                    headerPrivacyIconsController.privacyItemController.removeCallback(headerPrivacyIconsController.picCallback);
                    headerPrivacyIconsController.privacyChipLogged = false;
                    ((ArrayList) ((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).mCallbacks).remove(headerPrivacyIconsController.desktopCallback);
                }
                shadeHeaderController.updateVisibility();
            }
            boolean z5 = this.mFullyExpanded;
            LightBarController lightBarController = this.mLightBarController;
            if (lightBarController.mQsExpanded != z5) {
                lightBarController.mQsExpanded = z5;
                lightBarController.reevaluate();
            }
            int i = this.mBarState;
            if (i == 1 || i == 2) {
                if (this.mMaxExpansionHeight != 0) {
                    f = (this.mExpansionHeight - this.mMinExpansionHeight) / (r0 - r2);
                }
                PluginLockMediatorImpl pluginLockMediatorImpl = (PluginLockMediatorImpl) this.mPluginLockMediator;
                PluginLockDelegateApp pluginLockDelegateApp = pluginLockMediatorImpl.mBasicListener;
                if (pluginLockDelegateApp == null || !pluginLockMediatorImpl.mIsEnabled || (pluginLockBasicManager = pluginLockDelegateApp.mBasicManager) == null) {
                    return;
                }
                pluginLockBasicManager.setQsExpansion(f);
            }
        }
    }

    public final void updateExpansionEnabledAmbient() {
        AmbientState ambientState = this.mAmbientState;
        this.mExpansionEnabledAmbient = this.mSplitShadeEnabled || ((float) ambientState.mScrollY) <= ((float) ambientState.mTopPadding) - this.mQuickQsHeaderHeight;
        InterfaceC1922QS interfaceC1922QS = this.mQs;
        if (interfaceC1922QS != null) {
            interfaceC1922QS.setHeaderClickable(isExpansionEnabled());
        }
    }

    public final void updateMinHeight() {
        float f = this.mMinExpansionHeight;
        if (this.mBarState == 1 || this.mSplitShadeEnabled) {
            this.mMinExpansionHeight = 0;
        } else {
            int qsMinExpansionHeight = this.mQs.getQsMinExpansionHeight();
            this.mMinExpansionHeight = qsMinExpansionHeight;
            int i = this.mQuickQsOffsetHeight;
            if (qsMinExpansionHeight < i) {
                this.mMinExpansionHeight = i;
            }
        }
        int i2 = this.mMinExpansionHeight;
        int i3 = this.mMaxExpansionHeight;
        if (i2 > i3) {
            this.mMinExpansionHeight = i3;
        }
        if (this.mExpansionHeight == f) {
            this.mExpansionHeight = this.mMinExpansionHeight;
        }
        int i4 = this.mMinExpansionHeight;
        NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.mQsMinHeight = i4;
        notificationStackScrollLayout.mAmbientState.mNotificationTopRatio = notificationStackScrollLayout.mQsMinHeight / (notificationStackScrollLayout.getHeight() - notificationStackScrollLayout.getEmptyBottomMargin());
    }

    public final void updateNightMode(int i) {
        ScrimController scrimController = this.mScrimController;
        if (scrimController != null) {
            if ((this.mPanelView.getResources().getConfiguration().uiMode & 32) != 0 && i == 0 && (this.mExpanded || this.mBarState == 2)) {
                scrimController.mSecLsScrimControlHelper.setQsExpandedOnNightMode(true);
            } else {
                scrimController.mSecLsScrimControlHelper.setQsExpandedOnNightMode(false);
            }
        }
    }

    public final void updateQsState() {
        boolean z = false;
        boolean z2 = this.mExpanded && !this.mSplitShadeEnabled;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.mQsFullScreen = z2;
        notificationStackScrollLayout.updateAlgorithmLayoutMinHeight();
        boolean z3 = !notificationStackScrollLayout.mQsFullScreen && notificationStackScrollLayout.getScrollRange() > 0;
        if (z3 != notificationStackScrollLayout.mScrollable) {
            notificationStackScrollLayout.mScrollable = z3;
            notificationStackScrollLayout.setFocusable(z3);
            notificationStackScrollLayout.updateForwardAndBackwardScrollability();
        }
        notificationStackScrollLayoutController.updateShowEmptyShadeView();
        if (this.mBarState != 1 && (!z2 || this.mExpansionFromOverscroll)) {
            z = true;
        }
        notificationStackScrollLayoutController.mView.mScrollingEnabled = z;
        NotificationPanelViewController$$ExternalSyntheticLambda2 notificationPanelViewController$$ExternalSyntheticLambda2 = this.mQsStateUpdateListener;
        if (notificationPanelViewController$$ExternalSyntheticLambda2 != null) {
            boolean z4 = this.mExpanded;
            boolean z5 = this.mStackScrollerOverscrolling;
            KeyguardUserSwitcherController keyguardUserSwitcherController = notificationPanelViewController$$ExternalSyntheticLambda2.f$0.mKeyguardUserSwitcherController;
            if (keyguardUserSwitcherController != null && z4 && !z5) {
                keyguardUserSwitcherController.closeSwitcherIfOpenAndNotSimple(true);
            }
        }
        InterfaceC1922QS interfaceC1922QS = this.mQs;
        if (interfaceC1922QS == null) {
            return;
        }
        interfaceC1922QS.setExpanded(this.mExpanded);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x014a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void flingQs(float f, int i, final QuickSettingsController$$ExternalSyntheticLambda5 quickSettingsController$$ExternalSyntheticLambda5, final boolean z) {
        int i2;
        float f2;
        float f3;
        boolean z2;
        boolean z3;
        boolean z4;
        ShadeLogger shadeLogger = this.mShadeLog;
        shadeLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        Function1 function1 = new Function1() { // from class: com.android.systemui.shade.ShadeLogger$flingQs$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return "QS fling with type " + ((LogMessage) obj).getStr1() + ", originated from click: " + z;
            }
        };
        LogBuffer logBuffer = shadeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, function1, null);
        obtain.setStr1(i != 0 ? i != 1 ? i != 2 ? "UNKNOWN" : "FLING_HIDE" : "FLING_COLLAPSE" : "FLING_EXPAND");
        obtain.setBool1(z);
        logBuffer.commit(obtain);
        float f4 = 0.0f;
        if (i == 0) {
            i2 = this.mMaxExpansionHeight;
        } else {
            if (i != 1) {
                if (isQsFragmentCreated()) {
                    this.mQs.closeDetail();
                }
                f2 = 0.0f;
                f3 = this.mExpansionHeight;
                if (f2 != f3) {
                    if (quickSettingsController$$ExternalSyntheticLambda5 != null) {
                        quickSettingsController$$ExternalSyntheticLambda5.run();
                    }
                    traceQsJank(false, i != 0);
                    return;
                }
                boolean z5 = i == 0;
                if ((f <= 0.0f || z5) && (f >= 0.0f || !z5)) {
                    f4 = f;
                    z2 = false;
                } else {
                    z2 = true;
                }
                ValueAnimator ofFloat = ValueAnimator.ofFloat(f3, f2);
                if (z) {
                    ofFloat.setInterpolator(Interpolators.TOUCH_RESPONSE);
                    ofFloat.setDuration(368L);
                } else {
                    NotificationPanelViewController$$ExternalSyntheticLambda2 notificationPanelViewController$$ExternalSyntheticLambda2 = this.mFlingQsWithoutClickListener;
                    if (notificationPanelViewController$$ExternalSyntheticLambda2 != null) {
                        float f5 = this.mExpansionHeight;
                        FlingAnimationUtils flingAnimationUtils = notificationPanelViewController$$ExternalSyntheticLambda2.f$0.mFlingAnimationUtils;
                        flingAnimationUtils.getClass();
                        z3 = z2;
                        z4 = z5;
                        flingAnimationUtils.apply(ofFloat, f5, f2, f4, Math.abs(f2 - f5));
                        if (z3) {
                            ofFloat.setDuration(350L);
                        }
                        ofFloat.addUpdateListener(new QuickSettingsController$$ExternalSyntheticLambda1(this, 0));
                        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.QuickSettingsController.3
                            public boolean mIsCanceled;

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationCancel(Animator animator) {
                                this.mIsCanceled = true;
                                QuickSettingsController.this.mAmbientState.mIsExpandAnimating = false;
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                QuickSettingsController quickSettingsController = QuickSettingsController.this;
                                quickSettingsController.mAnimatingHiddenFromCollapsed = false;
                                quickSettingsController.mAnimating = false;
                                quickSettingsController.mAmbientState.mIsExpandAnimating = false;
                                ((NotificationPanelViewController) quickSettingsController.mPanelViewControllerLazy.get()).notifyExpandingFinished();
                                QuickSettingsController quickSettingsController2 = QuickSettingsController.this;
                                quickSettingsController2.mNotificationStackScrollLayoutController.mView.mCheckForLeavebehind = true;
                                quickSettingsController2.mExpansionAnimator = null;
                                Runnable runnable = quickSettingsController$$ExternalSyntheticLambda5;
                                if (runnable != null) {
                                    runnable.run();
                                }
                                QuickSettingsController.this.traceQsJank(false, this.mIsCanceled);
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationStart(Animator animator) {
                                ((NotificationPanelViewController) QuickSettingsController.this.mPanelViewControllerLazy.get()).notifyExpandingStarted();
                            }
                        });
                        boolean z6 = this.mAnimating;
                        boolean z7 = this.mAnimatorExpand;
                        float f6 = this.mExpansionHeight;
                        SecQuickSettingsController secQuickSettingsController = this.mSecQuickSettingsController;
                        StringBuilder sb = secQuickSettingsController.logBuilder;
                        sb.setLength(0);
                        sb.append("flingQs: ");
                        sb.append("vel:");
                        sb.append(f4);
                        sb.append(", isClick:");
                        sb.append(z);
                        sb.append(", target:");
                        sb.append(f2);
                        sb.append(", mExpansionHeight:");
                        sb.append(f6);
                        sb.append(", oppositeDirection:");
                        sb.append(z3);
                        sb.append(", mAnimating(");
                        sb.append(z6);
                        sb.append(" >> true)");
                        sb.append(", mAnimatorExpand(");
                        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, z7, " >> ", z4, ")");
                        SecPanelLoggerImpl secPanelLoggerImpl = (SecPanelLoggerImpl) secQuickSettingsController.panelLogger;
                        secPanelLoggerImpl.getClass();
                        String str = i == 0 ? i != 1 ? i != 2 ? "UnKnown" : "FLING_HIDE" : "FLING_COLLAPSE" : "FLING_EXPAND";
                        secPanelLoggerImpl.writer.logPanel("TOUCH", str + " - " + ((Object) sb) + "\n" + Debug.getCallers(10, " - "));
                        this.mAnimating = true;
                        this.mAmbientState.mIsExpandAnimating = z4;
                        ofFloat.start();
                        this.mExpansionAnimator = ofFloat;
                        this.mAnimatorExpand = z4;
                        this.mAnimatingHiddenFromCollapsed = computeExpansionFraction() != 0.0f && f2 == 0.0f;
                        return;
                    }
                }
                z3 = z2;
                z4 = z5;
                if (z3) {
                }
                ofFloat.addUpdateListener(new QuickSettingsController$$ExternalSyntheticLambda1(this, 0));
                ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.QuickSettingsController.3
                    public boolean mIsCanceled;

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        this.mIsCanceled = true;
                        QuickSettingsController.this.mAmbientState.mIsExpandAnimating = false;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        QuickSettingsController quickSettingsController = QuickSettingsController.this;
                        quickSettingsController.mAnimatingHiddenFromCollapsed = false;
                        quickSettingsController.mAnimating = false;
                        quickSettingsController.mAmbientState.mIsExpandAnimating = false;
                        ((NotificationPanelViewController) quickSettingsController.mPanelViewControllerLazy.get()).notifyExpandingFinished();
                        QuickSettingsController quickSettingsController2 = QuickSettingsController.this;
                        quickSettingsController2.mNotificationStackScrollLayoutController.mView.mCheckForLeavebehind = true;
                        quickSettingsController2.mExpansionAnimator = null;
                        Runnable runnable = quickSettingsController$$ExternalSyntheticLambda5;
                        if (runnable != null) {
                            runnable.run();
                        }
                        QuickSettingsController.this.traceQsJank(false, this.mIsCanceled);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        ((NotificationPanelViewController) QuickSettingsController.this.mPanelViewControllerLazy.get()).notifyExpandingStarted();
                    }
                });
                boolean z62 = this.mAnimating;
                boolean z72 = this.mAnimatorExpand;
                float f62 = this.mExpansionHeight;
                SecQuickSettingsController secQuickSettingsController2 = this.mSecQuickSettingsController;
                StringBuilder sb2 = secQuickSettingsController2.logBuilder;
                sb2.setLength(0);
                sb2.append("flingQs: ");
                sb2.append("vel:");
                sb2.append(f4);
                sb2.append(", isClick:");
                sb2.append(z);
                sb2.append(", target:");
                sb2.append(f2);
                sb2.append(", mExpansionHeight:");
                sb2.append(f62);
                sb2.append(", oppositeDirection:");
                sb2.append(z3);
                sb2.append(", mAnimating(");
                sb2.append(z62);
                sb2.append(" >> true)");
                sb2.append(", mAnimatorExpand(");
                KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb2, z72, " >> ", z4, ")");
                SecPanelLoggerImpl secPanelLoggerImpl2 = (SecPanelLoggerImpl) secQuickSettingsController2.panelLogger;
                secPanelLoggerImpl2.getClass();
                if (i == 0) {
                }
                secPanelLoggerImpl2.writer.logPanel("TOUCH", str + " - " + ((Object) sb2) + "\n" + Debug.getCallers(10, " - "));
                this.mAnimating = true;
                this.mAmbientState.mIsExpandAnimating = z4;
                ofFloat.start();
                this.mExpansionAnimator = ofFloat;
                this.mAnimatorExpand = z4;
                this.mAnimatingHiddenFromCollapsed = computeExpansionFraction() != 0.0f && f2 == 0.0f;
                return;
            }
            if (this.mSplitShadeEnabled) {
                Log.wtfStack("QuickSettingsController", "FLING_COLLAPSE called in split shade");
            }
            setExpandImmediate(false);
            i2 = this.mMinExpansionHeight;
        }
        f2 = i2;
        f3 = this.mExpansionHeight;
        if (f2 != f3) {
        }
    }
}
