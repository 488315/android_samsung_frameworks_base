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
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.MathUtils;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.policy.SystemBarUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.animation.ShadeInterpolation;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$1;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.QuickPanelLogger;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.ui.controller.MediaHierarchyManager;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.views.NavigationBarView;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.privacy.PrivacyConfig;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.qs.HeaderPrivacyIconsController;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.flags.QSComposeFragment;
import com.android.systemui.samsung.quicksetting.SecQSPanelComposeAdapter;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.scrim.ScrimDrawable;
import com.android.systemui.scrim.ScrimView;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.PulseExpansionHandler;
import com.android.systemui.statusbar.QsFrameTranslateController;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.emptyshade.ui.view.EmptyShadeView;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.KeyguardStatusBarView;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LightBarControllerImpl;
import com.android.systemui.statusbar.phone.LockscreenGestureLogger;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.ScrimState;
import com.android.systemui.statusbar.phone.ShadeTouchableRegionManager;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.CastController;
import com.android.systemui.statusbar.policy.CastControllerImpl;
import com.android.systemui.statusbar.policy.CastControllerImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.LargeScreenUtils;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.utils.windowmanager.WindowManagerProvider;
import com.android.systemui.utils.windowmanager.WindowManagerProviderImpl;
import com.android.systemui.utils.windowmanager.WindowManagerUtils;
import dagger.Lazy;
import dalvik.annotation.optimization.NeverCompile;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import javax.inject.Provider;
import kotlin.jvm.internal.TypeIntrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class QuickSettingsControllerImpl implements QuickSettingsController, Dumpable, PanelScreenShotLogger.LogProvider {
    public final AccessibilityManager mAccessibilityManager;
    public final AmbientState mAmbientState;
    public float mAmount;
    public boolean mAnimateNextNotificationBounds;
    public boolean mAnimating;
    public boolean mAnimatingHiddenFromCollapsed;
    public boolean mAnimatorExpand;
    public NotificationPanelViewController$$ExternalSyntheticLambda0 mApplyClippingImmediatelyListener;
    public int mBarState;
    public Insets mCachedGestureInsets;
    public int mCachedWindowWidth;
    public final CastController mCastController;
    public boolean mCollapsedOnDown;
    public final Lazy mCommunalTransitionViewModelLazy;
    public boolean mConflictingExpansionGesture;
    public final NotificationShadeDepthController mDepthController;
    public int mDistanceForFullShadeTransition;
    public boolean mDozing;
    public boolean mEnableClipping;
    public boolean mExpandedWhenExpandingStarted;
    public ValueAnimator mExpansionAnimator;
    public boolean mExpansionFromOverscroll;
    public float mExpansionHeight;
    public NotificationPanelViewController$$ExternalSyntheticLambda0 mExpansionHeightListener;
    public NotificationPanelViewController$$ExternalSyntheticLambda0 mExpansionHeightSetToMaxListener;
    public final FalsingManager mFalsingManager;
    public int mFalsingThreshold;
    public NotificationPanelViewController$$ExternalSyntheticLambda0 mFlingQsWithoutClickListener;
    public boolean mFullyExpanded;
    public float mInitialHeightOnTouch;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public final Lazy mInteractionJankMonitorLazy;
    public boolean mIsFullWidth;
    public boolean mIsPulseExpansionResettingAnimator;
    public boolean mIsRubberBanded;
    public final JavaAdapter mJavaAdapter;
    public final KeyguardBypassController mKeyguardBypassController;
    public final KeyguardStatusBarView mKeyguardStatusBar;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public float mLastOverscroll;
    public boolean mLastShadeFlingWasExpanding;
    public final LightBarController mLightBarController;
    public final LockscreenGestureLogger mLockscreenGestureLogger;
    public int mLockscreenNotificationPadding;
    public final LockscreenShadeTransitionController mLockscreenShadeTransitionController;
    public int mMaxExpansionHeight;
    public final MediaHierarchyManager mMediaHierarchyManager;
    public final MetricsLogger mMetricsLogger;
    public int mMinExpansionHeight;
    public long mNotificationBoundsAnimationDuration;
    public final NotificationStackScrollLayoutController mNotificationStackScrollLayoutController;
    public boolean mPanelSplitEnabled;
    public final NotificationPanelView mPanelView;
    public final Lazy mPanelViewControllerLazy;
    public final PluginLockMediator mPluginLockMediator;
    public final PulseExpansionHandler mPulseExpansionHandler;
    public QS mQs;
    public final FrameLayout mQsFrame;
    public final QsFrameTranslateController mQsFrameTranslateController;
    public VelocityTracker mQsVelocityTracker;
    public final StringBuilder mQuickPanelLogBuilder;
    public final QuickPanelLogger mQuickPanelLogger;
    public float mQuickQsHeaderHeight;
    public int mQuickQsOffsetHeight;
    public final RecordingController mRecordingController;
    public final NotificationRemoteInputManager mRemoteInputManager;
    public final Resources mResources;
    public int mScreenCornerRadius;
    public final ScrimController mScrimController;
    public int mScrimCornerRadius;
    public final SecQuickSettingsControllerImpl mSecQuickSettingsControllerImpl;
    public float mShadeExpandedFraction;
    public final ShadeHeaderController mShadeHeaderController;
    public final ShadeInteractor mShadeInteractor;
    public final ShadeLogger mShadeLog;
    public final ShadeRepository mShadeRepository;
    public final ShadeTouchableRegionManager mShadeTouchableRegionManager;
    public ValueAnimator mSizeChangeAnimator;
    public float mSlopMultiplier;
    public int mSplitShadeNotificationsScrimMarginBottom;
    public final SplitShadeStateController mSplitShadeStateController;
    public boolean mStackScrollerOverscrolling;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public int mStatusBarMinHeight;
    public boolean mTouchAboveFalsingThreshold;
    public int mTouchSlop;
    public int mTrackingPointer;
    public float mTranslationForFullShadeTransition;
    public boolean mTwoFingerExpandPossible;
    public boolean mUseLargeScreenShadeHeader;
    public boolean mVisible;
    public final WindowManagerProvider mWindowManagerProvider;
    public boolean mScrimEnabled = true;
    public int mDisplayRightInset = 0;
    public int mDisplayLeftInset = 0;
    public float mShadeExpandedHeight = 0.0f;
    public boolean mExpansionEnabledPolicy = true;
    public boolean mExpansionEnabledAmbient = true;
    public final Region mInterceptRegion = new Region();
    public final Rect mClippingAnimationEndBounds = new Rect();
    public final Rect mLastClipBounds = new Rect();
    public ValueAnimator mClippingAnimator = null;
    public final QuickSettingsControllerImpl$$ExternalSyntheticLambda0 mQsHeightListener = new QS.HeightListener() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda0
        @Override // com.android.systemui.plugins.qs.QS.HeightListener
        public final void onQsHeightChanged() {
            QuickSettingsControllerImpl.this.onHeightChanged();
        }
    };
    public final QuickSettingsControllerImpl$$ExternalSyntheticLambda10 mQsCollapseExpandAction = new QuickSettingsControllerImpl$$ExternalSyntheticLambda10(this, 1);
    public final QuickSettingsControllerImpl$$ExternalSyntheticLambda22 mQsScrollListener = new QS.ScrollListener() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda22
        @Override // com.android.systemui.plugins.qs.QS.ScrollListener
        public final void onQsPanelScrollChanged(int i) {
            QuickSettingsControllerImpl quickSettingsControllerImpl = QuickSettingsControllerImpl.this;
            ShadeHeaderController shadeHeaderController = quickSettingsControllerImpl.mShadeHeaderController;
            if (shadeHeaderController.qsScrollY != i) {
                shadeHeaderController.qsScrollY = i;
                if (!shadeHeaderController.largeScreenActive) {
                    shadeHeaderController.header.setScrollY(i);
                }
            }
            if (i <= 0 || quickSettingsControllerImpl.mFullyExpanded || !SecPanelSplitHelper.isEnabled()) {
                return;
            }
            ((NotificationPanelViewController) quickSettingsControllerImpl.mPanelViewControllerLazy.get()).expandToQs();
        }
    };
    public boolean mPanelExpandedForFingerPrint = false;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LockscreenShadeTransitionCallback implements LockscreenShadeTransitionController.Callback {
        public /* synthetic */ LockscreenShadeTransitionCallback(QuickSettingsControllerImpl quickSettingsControllerImpl, int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
        public final void onPulseExpansionFinished() {
            QuickSettingsControllerImpl quickSettingsControllerImpl = QuickSettingsControllerImpl.this;
            quickSettingsControllerImpl.mAnimateNextNotificationBounds = true;
            quickSettingsControllerImpl.mNotificationBoundsAnimationDuration = 448L;
            quickSettingsControllerImpl.mIsPulseExpansionResettingAnimator = true;
        }

        @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
        public final void setTransitionToFullShadeAmount(float f, boolean z, long j) {
            String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
            QuickSettingsControllerImpl quickSettingsControllerImpl = QuickSettingsControllerImpl.this;
            boolean z2 = f > ((float) quickSettingsControllerImpl.mMinExpansionHeight) && !quickSettingsControllerImpl.getExpanded();
            if (quickSettingsControllerImpl.mPanelExpandedForFingerPrint == z2) {
                return;
            }
            quickSettingsControllerImpl.mPanelExpandedForFingerPrint = z2;
            quickSettingsControllerImpl.mKeyguardUpdateMonitor.setPanelExpandingStarted(z2);
        }

        private LockscreenShadeTransitionCallback() {
        }

        @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
        public final void setTransitionToFullShadeAmount(float f) {
            QuickSettingsControllerImpl quickSettingsControllerImpl = QuickSettingsControllerImpl.this;
            if (quickSettingsControllerImpl.mBarState == 1) {
                quickSettingsControllerImpl.mPluginLockMediator.setQsExpansion(f);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NsslOverscrollTopChangedListener {
        public /* synthetic */ NsslOverscrollTopChangedListener(QuickSettingsControllerImpl quickSettingsControllerImpl, int i) {
            this();
        }

        private NsslOverscrollTopChangedListener() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class QsFragmentListener implements FragmentHostManager.FragmentListener {
        public QsFragmentListener() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:71:0x01b8  */
        @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onFragmentViewCreated(android.app.Fragment r7) {
            /*
                Method dump skipped, instructions count: 483
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.QuickSettingsControllerImpl.QsFragmentListener.onFragmentViewCreated(android.app.Fragment):void");
        }

        @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
        public final void onFragmentViewDestroyed(Fragment fragment) {
            SecPanelSplitHelper secPanelSplitHelper;
            QuickSettingsControllerImpl quickSettingsControllerImpl = QuickSettingsControllerImpl.this;
            SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = quickSettingsControllerImpl.mSecQuickSettingsControllerImpl;
            if (secQuickSettingsControllerImpl != null) {
                synchronized (PanelScreenShotLogger.INSTANCE) {
                    PanelScreenShotLogger.providers.remove("SecQuickSettingsControllerImpl");
                }
                ((NavigationModeController) secQuickSettingsControllerImpl.navigationModeController$delegate.getValue()).removeListener(secQuickSettingsControllerImpl.modeChangedListener);
                SecPanelExpansionStateInteractor secPanelExpansionStateInteractor = secQuickSettingsControllerImpl.panelExpansionStateInteractor;
                if (secPanelExpansionStateInteractor != null) {
                    secPanelExpansionStateInteractor.shadeExpansionStateManager.removeExpansionListener(secPanelExpansionStateInteractor.shadeExpansionListener);
                    secPanelExpansionStateInteractor.statusBarStateController.removeCallback(secPanelExpansionStateInteractor.stateListener);
                    secPanelExpansionStateInteractor.wakefulnessLifecycle.removeObserver(secPanelExpansionStateInteractor.observer);
                }
                SecQSExpansionStateInteractor secQSExpansionStateInteractor = (SecQSExpansionStateInteractor) secQuickSettingsControllerImpl.qsExpansionStateInteractor$delegate.getValue();
                if (secQSExpansionStateInteractor != null && (secPanelSplitHelper = (SecPanelSplitHelper) secQSExpansionStateInteractor.splitHelper$delegate.getValue()) != null) {
                    secPanelSplitHelper.removeListener(secQSExpansionStateInteractor.panelTransitionStateListener);
                }
                RepeatWhenAttachedKt$repeatWhenAttached$1 repeatWhenAttachedKt$repeatWhenAttached$1 = secQuickSettingsControllerImpl.getTabletHorizontalPanelPositionHelper().handle;
                if (repeatWhenAttachedKt$repeatWhenAttached$1 != null) {
                    repeatWhenAttachedKt$repeatWhenAttached$1.dispose();
                }
            }
            if (fragment == quickSettingsControllerImpl.mQs) {
                int i = QSComposeFragment.$r8$clinit;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = quickSettingsControllerImpl.mNotificationStackScrollLayoutController;
                notificationStackScrollLayoutController.getClass();
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                notificationStackScrollLayout.getClass();
                notificationStackScrollLayout.mQsHeader = null;
                quickSettingsControllerImpl.mQs = null;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r10v5, types: [com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r10v7, types: [com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda22] */
    public QuickSettingsControllerImpl(final Lazy lazy, NotificationPanelView notificationPanelView, QsFrameTranslateController qsFrameTranslateController, PulseExpansionHandler pulseExpansionHandler, NotificationRemoteInputManager notificationRemoteInputManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, LightBarController lightBarController, NotificationStackScrollLayoutController notificationStackScrollLayoutController, LockscreenShadeTransitionController lockscreenShadeTransitionController, NotificationShadeDepthController notificationShadeDepthController, ShadeHeaderController shadeHeaderController, ShadeTouchableRegionManager shadeTouchableRegionManager, Provider provider, KeyguardStateController keyguardStateController, KeyguardBypassController keyguardBypassController, ScrimController scrimController, MediaDataManager mediaDataManager, MediaHierarchyManager mediaHierarchyManager, AmbientState ambientState, RecordingController recordingController, FalsingManager falsingManager, AccessibilityManager accessibilityManager, LockscreenGestureLogger lockscreenGestureLogger, MetricsLogger metricsLogger, Lazy lazy2, ShadeLogger shadeLogger, DumpManager dumpManager, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, ShadeRepository shadeRepository, ShadeInteractor shadeInteractor, ActiveNotificationsInteractor activeNotificationsInteractor, JavaAdapter javaAdapter, CastController castController, SplitShadeStateController splitShadeStateController, Lazy lazy3, Lazy lazy4, WindowManagerProvider windowManagerProvider, PluginLockMediator pluginLockMediator, KeyguardUpdateMonitor keyguardUpdateMonitor, SecQSPanelComposeAdapter secQSPanelComposeAdapter, SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor) {
        final int i = 2;
        final int i2 = 1;
        final int i3 = 0;
        int i4 = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        this.mPanelViewControllerLazy = lazy;
        this.mPanelView = notificationPanelView;
        this.mQsFrame = (FrameLayout) notificationPanelView.findViewById(R.id.qs_frame);
        this.mKeyguardStatusBar = (KeyguardStatusBarView) notificationPanelView.findViewById(R.id.keyguard_header);
        this.mResources = notificationPanelView.getResources();
        this.mSplitShadeStateController = splitShadeStateController;
        ((SplitShadeStateControllerImpl) splitShadeStateController).shouldUseSplitNotificationShade();
        this.mQsFrameTranslateController = qsFrameTranslateController;
        this.mPulseExpansionHandler = pulseExpansionHandler;
        pulseExpansionHandler.pulseExpandAbortListener = new QuickSettingsControllerImpl$$ExternalSyntheticLambda10(this, 3);
        this.mRemoteInputManager = notificationRemoteInputManager;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mLightBarController = lightBarController;
        this.mNotificationStackScrollLayoutController = notificationStackScrollLayoutController;
        this.mLockscreenShadeTransitionController = lockscreenShadeTransitionController;
        this.mDepthController = notificationShadeDepthController;
        this.mShadeHeaderController = shadeHeaderController;
        this.mShadeTouchableRegionManager = shadeTouchableRegionManager;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mScrimController = scrimController;
        this.mMediaHierarchyManager = mediaHierarchyManager;
        this.mAmbientState = ambientState;
        this.mRecordingController = recordingController;
        this.mFalsingManager = falsingManager;
        this.mAccessibilityManager = accessibilityManager;
        this.mLockscreenGestureLogger = lockscreenGestureLogger;
        this.mMetricsLogger = metricsLogger;
        this.mShadeLog = shadeLogger;
        this.mCastController = castController;
        this.mInteractionJankMonitorLazy = lazy2;
        this.mShadeRepository = shadeRepository;
        this.mShadeInteractor = shadeInteractor;
        this.mCommunalTransitionViewModelLazy = lazy3;
        this.mJavaAdapter = javaAdapter;
        lockscreenShadeTransitionController.addCallback(new LockscreenShadeTransitionCallback(this, i3));
        dumpManager.registerDumpable(this);
        this.mWindowManagerProvider = windowManagerProvider;
        this.mQuickPanelLogger = new QuickPanelLogger("QSCI");
        this.mQuickPanelLogBuilder = new StringBuilder();
        final int i5 = 4;
        final int i6 = 5;
        final int i7 = 6;
        final int i8 = 7;
        final int i9 = 8;
        final int i10 = 0;
        final int i11 = 1;
        final int i12 = 2;
        final int i13 = 1;
        final int i14 = 0;
        final int i15 = 2;
        final int i16 = 1;
        final int i17 = 3;
        this.mSecQuickSettingsControllerImpl = new SecQuickSettingsControllerImpl(ambientState, new Function(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda24
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                int i18 = i3;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i18) {
                    case 0:
                        return Integer.valueOf(quickSettingsControllerImpl.calculateBottomPosition(((Float) obj).floatValue()));
                    default:
                        return Integer.valueOf(quickSettingsControllerImpl.calculateTopClippingBound(((Integer) obj).intValue()));
                }
            }
        }, new Function(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda24
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                int i18 = i2;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i18) {
                    case 0:
                        return Integer.valueOf(quickSettingsControllerImpl.calculateBottomPosition(((Float) obj).floatValue()));
                    default:
                        return Integer.valueOf(quickSettingsControllerImpl.calculateTopClippingBound(((Integer) obj).intValue()));
                }
            }
        }, new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda1
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                int i18 = i5;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i18) {
                    case 0:
                        return quickSettingsControllerImpl.mExpansionHeight;
                    case 1:
                        return quickSettingsControllerImpl.getCurrentVelocity();
                    case 2:
                        return quickSettingsControllerImpl.mInitialTouchX;
                    case 3:
                        return quickSettingsControllerImpl.mInitialTouchY;
                    case 4:
                        return quickSettingsControllerImpl.computeExpansionFraction();
                    case 5:
                        return quickSettingsControllerImpl.getEdgePosition();
                    case 6:
                        return quickSettingsControllerImpl.mShadeExpandedFraction;
                    case 7:
                        return quickSettingsControllerImpl.mMaxExpansionHeight;
                    default:
                        return quickSettingsControllerImpl.mMinExpansionHeight;
                }
            }
        }, notificationPanelView.getContext(), new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda1
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                int i18 = i6;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i18) {
                    case 0:
                        return quickSettingsControllerImpl.mExpansionHeight;
                    case 1:
                        return quickSettingsControllerImpl.getCurrentVelocity();
                    case 2:
                        return quickSettingsControllerImpl.mInitialTouchX;
                    case 3:
                        return quickSettingsControllerImpl.mInitialTouchY;
                    case 4:
                        return quickSettingsControllerImpl.computeExpansionFraction();
                    case 5:
                        return quickSettingsControllerImpl.getEdgePosition();
                    case 6:
                        return quickSettingsControllerImpl.mShadeExpandedFraction;
                    case 7:
                        return quickSettingsControllerImpl.mMaxExpansionHeight;
                    default:
                        return quickSettingsControllerImpl.mMinExpansionHeight;
                }
            }
        }, new BooleanSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda2
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                int i18 = i2;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i18) {
                    case 0:
                        return quickSettingsControllerImpl.mShadeExpandedFraction <= 0.0f;
                    case 1:
                        return quickSettingsControllerImpl.mEnableClipping;
                    default:
                        return quickSettingsControllerImpl.getExpanded();
                }
            }
        }, new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda1
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                int i18 = i7;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i18) {
                    case 0:
                        return quickSettingsControllerImpl.mExpansionHeight;
                    case 1:
                        return quickSettingsControllerImpl.getCurrentVelocity();
                    case 2:
                        return quickSettingsControllerImpl.mInitialTouchX;
                    case 3:
                        return quickSettingsControllerImpl.mInitialTouchY;
                    case 4:
                        return quickSettingsControllerImpl.computeExpansionFraction();
                    case 5:
                        return quickSettingsControllerImpl.getEdgePosition();
                    case 6:
                        return quickSettingsControllerImpl.mShadeExpandedFraction;
                    case 7:
                        return quickSettingsControllerImpl.mMaxExpansionHeight;
                    default:
                        return quickSettingsControllerImpl.mMinExpansionHeight;
                }
            }
        }, new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda1
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                int i18 = i3;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i18) {
                    case 0:
                        return quickSettingsControllerImpl.mExpansionHeight;
                    case 1:
                        return quickSettingsControllerImpl.getCurrentVelocity();
                    case 2:
                        return quickSettingsControllerImpl.mInitialTouchX;
                    case 3:
                        return quickSettingsControllerImpl.mInitialTouchY;
                    case 4:
                        return quickSettingsControllerImpl.computeExpansionFraction();
                    case 5:
                        return quickSettingsControllerImpl.getEdgePosition();
                    case 6:
                        return quickSettingsControllerImpl.mShadeExpandedFraction;
                    case 7:
                        return quickSettingsControllerImpl.mMaxExpansionHeight;
                    default:
                        return quickSettingsControllerImpl.mMinExpansionHeight;
                }
            }
        }, shadeHeaderController, new BooleanSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda2
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                int i18 = i3;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i18) {
                    case 0:
                        return quickSettingsControllerImpl.mShadeExpandedFraction <= 0.0f;
                    case 1:
                        return quickSettingsControllerImpl.mEnableClipping;
                    default:
                        return quickSettingsControllerImpl.getExpanded();
                }
            }
        }, new BooleanSupplier() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda3
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return QuickSettingsControllerImpl.this.mShadeExpandedHeight >= ((float) ((NotificationPanelViewController) lazy.get()).getMaxPanelHeight());
            }
        }, new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda1
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                int i18 = i8;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i18) {
                    case 0:
                        return quickSettingsControllerImpl.mExpansionHeight;
                    case 1:
                        return quickSettingsControllerImpl.getCurrentVelocity();
                    case 2:
                        return quickSettingsControllerImpl.mInitialTouchX;
                    case 3:
                        return quickSettingsControllerImpl.mInitialTouchY;
                    case 4:
                        return quickSettingsControllerImpl.computeExpansionFraction();
                    case 5:
                        return quickSettingsControllerImpl.getEdgePosition();
                    case 6:
                        return quickSettingsControllerImpl.mShadeExpandedFraction;
                    case 7:
                        return quickSettingsControllerImpl.mMaxExpansionHeight;
                    default:
                        return quickSettingsControllerImpl.mMinExpansionHeight;
                }
            }
        }, new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda1
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                int i18 = i9;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i18) {
                    case 0:
                        return quickSettingsControllerImpl.mExpansionHeight;
                    case 1:
                        return quickSettingsControllerImpl.getCurrentVelocity();
                    case 2:
                        return quickSettingsControllerImpl.mInitialTouchX;
                    case 3:
                        return quickSettingsControllerImpl.mInitialTouchY;
                    case 4:
                        return quickSettingsControllerImpl.computeExpansionFraction();
                    case 5:
                        return quickSettingsControllerImpl.getEdgePosition();
                    case 6:
                        return quickSettingsControllerImpl.mShadeExpandedFraction;
                    case 7:
                        return quickSettingsControllerImpl.mMaxExpansionHeight;
                    default:
                        return quickSettingsControllerImpl.mMinExpansionHeight;
                }
            }
        }, notificationStackScrollLayoutController, lazy, new BooleanSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda2
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                int i18 = i;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i18) {
                    case 0:
                        return quickSettingsControllerImpl.mShadeExpandedFraction <= 0.0f;
                    case 1:
                        return quickSettingsControllerImpl.mEnableClipping;
                    default:
                        return quickSettingsControllerImpl.getExpanded();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                int i18 = i10;
                Object obj = this;
                switch (i18) {
                    case 0:
                        return ((QuickSettingsControllerImpl) obj).mQsFrame;
                    case 1:
                        return ((QuickSettingsControllerImpl) obj).mQs;
                    default:
                        return ((NotificationPanelViewController) ((Lazy) obj).get()).mView;
                }
            }
        }, new Supplier() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                int i18 = i11;
                Object obj = this;
                switch (i18) {
                    case 0:
                        return ((QuickSettingsControllerImpl) obj).mQsFrame;
                    case 1:
                        return ((QuickSettingsControllerImpl) obj).mQs;
                    default:
                        return ((NotificationPanelViewController) ((Lazy) obj).get()).mView;
                }
            }
        }, new Supplier() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                int i18 = i12;
                Object obj = lazy;
                switch (i18) {
                    case 0:
                        return ((QuickSettingsControllerImpl) obj).mQsFrame;
                    case 1:
                        return ((QuickSettingsControllerImpl) obj).mQs;
                    default:
                        return ((NotificationPanelViewController) ((Lazy) obj).get()).mView;
                }
            }
        }, new QuickSettingsControllerImpl$$ExternalSyntheticLambda10(this, 0), new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda1
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                int i18 = i13;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i18) {
                    case 0:
                        return quickSettingsControllerImpl.mExpansionHeight;
                    case 1:
                        return quickSettingsControllerImpl.getCurrentVelocity();
                    case 2:
                        return quickSettingsControllerImpl.mInitialTouchX;
                    case 3:
                        return quickSettingsControllerImpl.mInitialTouchY;
                    case 4:
                        return quickSettingsControllerImpl.computeExpansionFraction();
                    case 5:
                        return quickSettingsControllerImpl.getEdgePosition();
                    case 6:
                        return quickSettingsControllerImpl.mShadeExpandedFraction;
                    case 7:
                        return quickSettingsControllerImpl.mMaxExpansionHeight;
                    default:
                        return quickSettingsControllerImpl.mMinExpansionHeight;
                }
            }
        }, new DoubleConsumer(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda13
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleConsumer
            public final void accept(double d) {
                int i18 = i14;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i18) {
                    case 0:
                        quickSettingsControllerImpl.mInitialTouchX = (float) d;
                        break;
                    default:
                        quickSettingsControllerImpl.mInitialTouchY = (float) d;
                        break;
                }
            }
        }, new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda1
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                int i18 = i15;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i18) {
                    case 0:
                        return quickSettingsControllerImpl.mExpansionHeight;
                    case 1:
                        return quickSettingsControllerImpl.getCurrentVelocity();
                    case 2:
                        return quickSettingsControllerImpl.mInitialTouchX;
                    case 3:
                        return quickSettingsControllerImpl.mInitialTouchY;
                    case 4:
                        return quickSettingsControllerImpl.computeExpansionFraction();
                    case 5:
                        return quickSettingsControllerImpl.getEdgePosition();
                    case 6:
                        return quickSettingsControllerImpl.mShadeExpandedFraction;
                    case 7:
                        return quickSettingsControllerImpl.mMaxExpansionHeight;
                    default:
                        return quickSettingsControllerImpl.mMinExpansionHeight;
                }
            }
        }, new DoubleConsumer(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda13
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleConsumer
            public final void accept(double d) {
                int i18 = i16;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i18) {
                    case 0:
                        quickSettingsControllerImpl.mInitialTouchX = (float) d;
                        break;
                    default:
                        quickSettingsControllerImpl.mInitialTouchY = (float) d;
                        break;
                }
            }
        }, new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda1
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                int i18 = i17;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i18) {
                    case 0:
                        return quickSettingsControllerImpl.mExpansionHeight;
                    case 1:
                        return quickSettingsControllerImpl.getCurrentVelocity();
                    case 2:
                        return quickSettingsControllerImpl.mInitialTouchX;
                    case 3:
                        return quickSettingsControllerImpl.mInitialTouchY;
                    case 4:
                        return quickSettingsControllerImpl.computeExpansionFraction();
                    case 5:
                        return quickSettingsControllerImpl.getEdgePosition();
                    case 6:
                        return quickSettingsControllerImpl.mShadeExpandedFraction;
                    case 7:
                        return quickSettingsControllerImpl.mMaxExpansionHeight;
                    default:
                        return quickSettingsControllerImpl.mMinExpansionHeight;
                }
            }
        }, new QuickSettingsControllerImpl$$ExternalSyntheticLambda10(this, 2), new QuickSettingsControllerImpl$$ExternalSyntheticLambda18(this, 0), new IntConsumer() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda19
            @Override // java.util.function.IntConsumer
            public final void accept(int i18) {
                QuickSettingsControllerImpl.this.mTrackingPointer = i18;
            }
        }, new IntSupplier() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda20
            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                return QuickSettingsControllerImpl.this.mTrackingPointer;
            }
        }, new QuickSettingsControllerImpl$$ExternalSyntheticLambda18(this, 1), secQSPanelComposeAdapter, secQsUiDisplayModeInteractor);
        this.mQuickQsOffsetHeight = SystemBarUtils.getQuickQsOffsetHeight(notificationPanelView.getContext());
        this.mPluginLockMediator = pluginLockMediator;
        PanelScreenShotLogger.INSTANCE.addLogProvider("QuickSettingsController", this);
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
    }

    public final void applyClippingImmediately(boolean z, int i, int i2, int i3, int i4) {
        int i5 = this.mScrimCornerRadius;
        this.mLastClipBounds.set(i, i2, i3, i4);
        boolean z2 = this.mIsFullWidth;
        ScrimController scrimController = this.mScrimController;
        if (z2) {
            float f = (this.mRecordingController.isRecording() || ((CastControllerImpl) this.mCastController).getCastDevices().stream().anyMatch(new CastControllerImpl$$ExternalSyntheticLambda0())) ? 0.0f : this.mScreenCornerRadius;
            float f2 = this.mScrimCornerRadius;
            i5 = (int) MathUtils.lerp(f, f2, Math.min(i2 / f2, 1.0f));
            float calculateBottomCornerRadius = !getExpanded() ? calculateBottomCornerRadius(0.0f) : 0.0f;
            ScrimView scrimView = scrimController.mNotificationsScrim;
            if (scrimView != null) {
                Drawable drawable = scrimView.mDrawable;
                if (drawable instanceof ScrimDrawable) {
                    ScrimDrawable scrimDrawable = (ScrimDrawable) drawable;
                    if (scrimDrawable.mBottomEdgeRadius != calculateBottomCornerRadius) {
                        scrimDrawable.mBottomEdgeRadius = calculateBottomCornerRadius;
                        scrimDrawable.invalidateSelf();
                    }
                }
            }
        }
        int i6 = i5;
        if (isQsFragmentCreated()) {
            boolean z3 = this.mPulseExpansionHandler.isExpanding;
            this.mTranslationForFullShadeTransition = (z3 || (this.mClippingAnimator != null && this.mIsPulseExpansionResettingAnimator)) ? (z3 || this.mIsPulseExpansionResettingAnimator) ? Math.max(0.0f, (i2 - getHeaderHeight()) / 2.0f) : (i2 - getHeaderHeight()) * 0.175f : 0.0f;
            int i7 = ((NotificationPanelViewController) this.mPanelViewControllerLazy.get()).mNavigationBarBottomHeight;
            int i8 = this.mAmbientState.mStackTopMargin;
            this.mQsFrameTranslateController.getClass();
            float translationY = this.mQsFrame.getTranslationY();
            int top = (int) ((i2 - translationY) - this.mQsFrame.getTop());
            int top2 = (int) ((i4 - translationY) - this.mQsFrame.getTop());
            this.mVisible = z;
            this.mQs.setQsVisible(z);
            if (this.mEnableClipping) {
                this.mQs.setFancyClipping(this.mDisplayLeftInset, top, this.mDisplayRightInset, top2, i6, z, this.mIsFullWidth);
            }
        }
        float f3 = i;
        float f4 = i2;
        float f5 = i3;
        float f6 = i4 + i6;
        ScrimView scrimView2 = scrimController.mNotificationsScrim;
        if (scrimView2.mDrawableBounds == null) {
            scrimView2.mDrawableBounds = new Rect();
        }
        int i9 = (int) f4;
        scrimView2.mDrawableBounds.set((int) f3, i9, (int) f5, (int) f6);
        scrimView2.mDrawable.setBounds(scrimView2.mDrawableBounds);
        if (scrimController.mNotificationsAlpha > 0.0f) {
            ((KeyguardRepositoryImpl) scrimController.mKeyguardInteractor.repository).topClippingBounds.setValue(Integer.valueOf(i9));
        } else {
            ((KeyguardRepositoryImpl) scrimController.mKeyguardInteractor.repository).topClippingBounds.setValue(null);
        }
        NotificationPanelViewController$$ExternalSyntheticLambda0 notificationPanelViewController$$ExternalSyntheticLambda0 = this.mApplyClippingImmediatelyListener;
        if (notificationPanelViewController$$ExternalSyntheticLambda0 != null) {
            boolean isQsFragmentCreated = isQsFragmentCreated();
            boolean z4 = this.mVisible;
            NotificationPanelViewController notificationPanelViewController = notificationPanelViewController$$ExternalSyntheticLambda0.f$0;
            if (isQsFragmentCreated) {
                ((KeyguardRepositoryImpl) notificationPanelViewController.mKeyguardInteractor.repository)._isQuickSettingsVisible.updateState(null, Boolean.valueOf(z4));
            }
            notificationPanelViewController.mKeyguardStatusBarViewController.updateTopClipping(i2);
        }
        ScrimView scrimView3 = scrimController.mScrimBehind;
        if (scrimView3 != null && scrimController.mNotificationsScrim != null) {
            scrimView3.setCornerRadius(i6);
            scrimController.mNotificationsScrim.setCornerRadius(i6);
        }
        int i10 = SceneContainerFlag.$r8$clinit;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        notificationStackScrollLayoutController.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int left = i - notificationStackScrollLayoutController.mView.getLeft();
        if (!this.mIsFullWidth) {
            left -= this.mDisplayLeftInset;
        }
        int left2 = i3 - notificationStackScrollLayoutController.mView.getLeft();
        if (!this.mIsFullWidth) {
            left2 -= this.mDisplayLeftInset;
        }
        int top3 = i2 - notificationStackScrollLayoutController.mView.getTop();
        int top4 = i4 - notificationStackScrollLayoutController.mView.getTop();
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.getClass();
        if (notificationStackScrollLayout.mRoundedRectClippingLeft == left && notificationStackScrollLayout.mRoundedRectClippingRight == left2 && notificationStackScrollLayout.mRoundedRectClippingBottom == top4 && notificationStackScrollLayout.mRoundedRectClippingTop == top3) {
            float[] fArr = notificationStackScrollLayout.mRoundedClipCornerRadii;
            if (fArr[0] == 0 && fArr[5] == 0) {
                return;
            }
        }
        notificationStackScrollLayout.mRoundedRectClippingLeft = left;
        notificationStackScrollLayout.mRoundedRectClippingTop = top3;
        notificationStackScrollLayout.mRoundedRectClippingBottom = top4;
        notificationStackScrollLayout.mRoundedRectClippingRight = left2;
        float[] fArr2 = notificationStackScrollLayout.mRoundedClipCornerRadii;
        float f7 = 0;
        fArr2[0] = f7;
        fArr2[1] = f7;
        fArr2[2] = f7;
        fArr2[3] = f7;
        float f8 = 0;
        fArr2[4] = f8;
        fArr2[5] = f8;
        fArr2[6] = f8;
        fArr2[7] = f8;
        notificationStackScrollLayout.getClass();
        int i11 = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
        notificationStackScrollLayout.mRoundedClipPath.reset();
        notificationStackScrollLayout.mRoundedClipPath.addRoundRect(notificationStackScrollLayout.mRoundedRectClippingLeft, notificationStackScrollLayout.mRoundedRectClippingTop, notificationStackScrollLayout.mRoundedRectClippingRight, notificationStackScrollLayout.mRoundedRectClippingBottom, notificationStackScrollLayout.mRoundedClipCornerRadii, Path.Direction.CW);
        if (notificationStackScrollLayout.mShouldUseRoundedRectClipping) {
            notificationStackScrollLayout.invalidate();
        }
    }

    public final void beginJankMonitoring(boolean z) {
        InteractionJankMonitor interactionJankMonitor = (InteractionJankMonitor) this.mInteractionJankMonitorLazy.get();
        if (interactionJankMonitor == null) {
            return;
        }
        interactionJankMonitor.begin(InteractionJankMonitor.Configuration.Builder.withView(0, this.mPanelView).setTag(z ? "Expand" : "Collapse"));
    }

    public int calculateBottomCornerRadius(float f) {
        return (int) MathUtils.lerp(f, this.mScrimCornerRadius, Math.min(calculateBottomRadiusProgress(), 1.0f));
    }

    public final int calculateBottomPosition(float f) {
        return (int) MathUtils.lerp(this.mQs.getQsMinExpansionHeight() + ((int) getHeaderTranslation()), this.mQs.getDesiredHeight(), f);
    }

    public float calculateBottomRadiusProgress() {
        return (1.0f - this.mScrimController.mNotificationsScrim.getScaleY()) * 100.0f;
    }

    public final int calculatePanelHeightExpanded(int i) {
        EmptyShadeView emptyShadeView;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        int height = notificationStackScrollLayoutController.mView.getHeight();
        int i2 = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.getClass();
        float max = (height - Math.max(notificationStackScrollLayout.mMaxLayoutHeight - notificationStackScrollLayout.mContentHeight, 0)) - notificationStackScrollLayoutController.mView.getTopPadding();
        if (notificationStackScrollLayoutController.getNotGoneChildCount() == 0) {
            NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
            notificationStackScrollLayout2.getClass();
            boolean z = NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW;
            if ((z || (emptyShadeView = notificationStackScrollLayout2.mEmptyShadeView) == null) ? false : emptyShadeView.mIsVisible) {
                NotificationStackScrollLayout notificationStackScrollLayout3 = notificationStackScrollLayoutController.mView;
                notificationStackScrollLayout3.getClass();
                max = z ? 0 : notificationStackScrollLayout3.mEmptyShadeView.getHeight();
            }
        }
        int i3 = this.mMaxExpansionHeight;
        ValueAnimator valueAnimator = this.mSizeChangeAnimator;
        if (valueAnimator != null) {
            i3 = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        }
        if (this.mBarState != 1) {
            i = 0;
        }
        float max2 = Math.max(i3, i) + max;
        NotificationStackScrollLayout notificationStackScrollLayout4 = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout4.getClass();
        float f = max2 + notificationStackScrollLayout4.mTopPaddingOverflow;
        if (f > notificationStackScrollLayoutController.mView.getHeight()) {
            notificationStackScrollLayoutController.mView.getClass();
            f = Math.max(r6.getLayoutMinHeightInternal() + i3, notificationStackScrollLayoutController.mView.getHeight());
        }
        return (int) f;
    }

    public final int calculateTopClippingBound(int i) {
        float edgePosition = getEdgePosition();
        this.mAmbientState.mNotificationScrimTop = edgePosition;
        if (this.mBarState == 1) {
            if (!this.mKeyguardBypassController.getBypassEnabled()) {
                i = (int) Math.min(i, edgePosition);
            }
        } else if (!getExpanded()) {
            i = (int) edgePosition;
        }
        Lazy lazy = this.mPanelViewControllerLazy;
        int i2 = (int) (i + ((NotificationPanelViewController) lazy.get()).mOverStretchAmount);
        float f = ((NotificationPanelViewController) lazy.get()).mMinFraction;
        if (f <= 0.0f || f >= 1.0f) {
            return i2;
        }
        return (int) (MathUtils.saturate(((this.mShadeExpandedFraction - f) / (1.0f - f)) / f) * i2);
    }

    @Override // com.android.systemui.shade.QuickSettingsController
    public final void closeQs() {
        ValueAnimator valueAnimator = this.mExpansionAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        setExpansionHeight(this.mMinExpansionHeight);
        setExpandImmediate(false);
    }

    @Override // com.android.systemui.shade.QuickSettingsController
    public final void closeQsCustomizer() {
        QS qs = this.mQs;
        if (qs != null) {
            qs.closeCustomizer();
        }
    }

    public final float computeExpansionFraction() {
        int i;
        int i2;
        if (this.mAnimatingHiddenFromCollapsed || (i = this.mMaxExpansionHeight) == (i2 = this.mMinExpansionHeight)) {
            return 0.0f;
        }
        return Math.min(1.0f, (this.mExpansionHeight - i2) / (i - i2));
    }

    @Override // com.android.systemui.Dumpable
    @NeverCompile
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
        asIndenting.println(false);
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
        asIndenting.println(isTracking());
        asIndenting.print("mTrackingPointer=");
        asIndenting.println(this.mTrackingPointer);
        asIndenting.print("mExpanded=");
        asIndenting.println(getExpanded());
        asIndenting.print("mFullyExpanded=");
        asIndenting.println(this.mFullyExpanded);
        asIndenting.print("isExpandImmediate()=");
        asIndenting.println(isExpandImmediate());
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
        asIndenting.print("mCachedWindowWidth=");
        asIndenting.println(this.mCachedWindowWidth);
        asIndenting.print("mTransitioningToFullShadeProgress=");
        asIndenting.println(0.0f);
        asIndenting.print("mDistanceForFullShadeTransition=");
        asIndenting.println(this.mDistanceForFullShadeTransition);
        asIndenting.print("mStackScrollerOverscrolling=");
        asIndenting.println(this.mStackScrollerOverscrolling);
        asIndenting.print("mAnimating=");
        asIndenting.println(this.mAnimating);
        asIndenting.print("mIsTranslationResettingAnimator=");
        asIndenting.println(false);
        asIndenting.print("mIsPulseExpansionResettingAnimator=");
        asIndenting.println(this.mIsPulseExpansionResettingAnimator);
        asIndenting.print("mTranslationForFullShadeTransition=");
        asIndenting.println(this.mTranslationForFullShadeTransition);
        asIndenting.print("mAnimateNextNotificationBounds=");
        asIndenting.println(this.mAnimateNextNotificationBounds);
        asIndenting.print("mNotificationBoundsAnimationDelay=");
        asIndenting.println(0L);
        asIndenting.print("mNotificationBoundsAnimationDuration=");
        asIndenting.println(this.mNotificationBoundsAnimationDuration);
        asIndenting.print("mInterceptRegion=");
        asIndenting.println(this.mInterceptRegion);
        asIndenting.print("mClippingAnimationEndBounds=");
        asIndenting.println(this.mClippingAnimationEndBounds);
        asIndenting.print("mLastClipBounds=");
        asIndenting.println(this.mLastClipBounds);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0174  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void flingQs(float r18, int r19, final com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda10 r20, final boolean r21) {
        /*
            Method dump skipped, instructions count: 399
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.QuickSettingsControllerImpl.flingQs(float, int, com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda10, boolean):void");
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        PanelScreenShotLogger.INSTANCE.getClass();
        PanelScreenShotLogger.addHeaderLine("QuickSettingsController", arrayList);
        PanelScreenShotLogger.addLogItem(arrayList, "getMaxExpansionHeight", Integer.valueOf(this.mMaxExpansionHeight));
        PanelScreenShotLogger.addLogItem(arrayList, "computeExpansionFraction()", Float.valueOf(computeExpansionFraction()));
        PanelScreenShotLogger.addLogItem(arrayList, "mLastOverscroll", Float.valueOf(this.mLastOverscroll));
        PanelScreenShotLogger.addLogItem(arrayList, "mSecQuickSettingsControllerImpl.calculateNotificationsTopPadding", Float.valueOf((float) Math.max(this.mQsFrameTranslateController.getNotificationsTopPadding((float) this.mSecQuickSettingsControllerImpl.minExpansionHeightSupplier.getAsDouble()), this.mQuickQsHeaderHeight)));
        return arrayList;
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
        float measuredHeight = this.mShadeHeaderController.header.getMeasuredHeight();
        AmbientState ambientState = this.mAmbientState;
        return Math.max(measuredHeight * ambientState.mExpansionFraction, ((ambientState.mStackTopMargin * ambientState.mExpansionFraction) + ambientState.getStackY()) - ambientState.mScrollY);
    }

    @Override // com.android.systemui.shade.QuickSettingsController
    public final boolean getExpanded() {
        return ((Boolean) ((ShadeRepositoryImpl) this.mShadeRepository).legacyIsQsExpanded.$$delegate_0.getValue()).booleanValue();
    }

    public final int getHeaderHeight() {
        if (!isQsFragmentCreated()) {
            return 0;
        }
        int i = QSComposeFragment.$r8$clinit;
        return this.mQs.getHeader().getHeight();
    }

    public final float getHeaderTranslation() {
        int i = this.mBarState;
        KeyguardBypassController keyguardBypassController = this.mKeyguardBypassController;
        if (i == 1 && !keyguardBypassController.getBypassEnabled()) {
            return -this.mQs.getQsMinExpansionHeight();
        }
        float f = this.mShadeExpandedHeight;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        notificationStackScrollLayoutController.getClass();
        int i2 = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        float calculateAppearFraction = notificationStackScrollLayoutController.mView.calculateAppearFraction(f);
        float f2 = -this.mExpansionHeight;
        if (this.mBarState == 0) {
            f2 *= 0.175f;
        }
        if (keyguardBypassController.getBypassEnabled() && this.mBarState == 1) {
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            notificationStackScrollLayout.getClass();
            float f3 = notificationStackScrollLayout.mAmbientState.mPulseHeight;
            if (f3 == 100000.0f) {
                f3 = 0.0f;
            }
            calculateAppearFraction = MathUtils.smoothStep(0.0f, notificationStackScrollLayout.mIntrinsicPadding, f3);
            f2 = -this.mQs.getQsMinExpansionHeight();
        }
        return Math.min(0.0f, MathUtils.lerp(f2, 0.0f, Math.min(1.0f, calculateAppearFraction)));
    }

    public int getScrimCornerRadius() {
        return this.mScrimCornerRadius;
    }

    @Override // com.android.systemui.shade.QuickSettingsController
    public final SecQuickSettingsControllerImpl getSecQuickSettingsControllerImpl$1() {
        return this.mSecQuickSettingsControllerImpl;
    }

    public float getShadeExpandedHeight() {
        return this.mShadeExpandedHeight;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: IfRegionVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r12v12 float, still in use, count: 2, list:
          (r12v12 float) from 0x0197: PHI (r12v11 float) = (r12v2 float), (r12v2 float), (r12v12 float), (r12v13 float), (r12v14 float) binds: [B:126:0x017a, B:128:0x0180, B:135:0x0195, B:133:0x018f, B:132:0x018c] A[DONT_GENERATE, DONT_INLINE]
          (r12v12 float) from 0x0193: CMP_L (0.0f float), (r12v12 float) A[WRAPPED] (LINE:404)
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.dex.visitors.regions.TernaryMod.makeTernaryInsn(TernaryMod.java:125)
        	at jadx.core.dex.visitors.regions.TernaryMod.processRegion(TernaryMod.java:62)
        	at jadx.core.dex.visitors.regions.TernaryMod.enterRegion(TernaryMod.java:45)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:67)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:19)
        	at jadx.core.dex.visitors.regions.TernaryMod.process(TernaryMod.java:35)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.process(IfRegionVisitor.java:34)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.visit(IfRegionVisitor.java:30)
        */
    public final boolean handleTouch(android.view.MotionEvent r20, boolean r21, boolean r22) {
        /*
            Method dump skipped, instructions count: 902
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.QuickSettingsControllerImpl.handleTouch(android.view.MotionEvent, boolean, boolean):boolean");
    }

    public boolean isConflictingExpansionGesture() {
        return this.mConflictingExpansionGesture;
    }

    @Override // com.android.systemui.shade.QuickSettingsController
    public final boolean isCustomizing() {
        return isQsFragmentCreated() && this.mQs.isCustomizing();
    }

    public boolean isExpandImmediate() {
        return ((Boolean) ((ShadeRepositoryImpl) this.mShadeRepository).legacyExpandImmediate.$$delegate_0.getValue()).booleanValue();
    }

    public final boolean isExpansionEnabled() {
        NotificationRemoteInputManager notificationRemoteInputManager = this.mRemoteInputManager;
        SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = this.mSecQuickSettingsControllerImpl;
        if (secQuickSettingsControllerImpl == null) {
            return this.mExpansionEnabledPolicy && this.mExpansionEnabledAmbient && !notificationRemoteInputManager.isRemoteInputActive();
        }
        if (this.mExpansionEnabledPolicy && this.mExpansionEnabledAmbient && !notificationRemoteInputManager.isRemoteInputActive()) {
            DesktopManager desktopManager = (DesktopManager) secQuickSettingsControllerImpl.desktopManager$delegate.getValue();
            return !(desktopManager != null ? desktopManager.isStandalone() : false);
        }
        secQuickSettingsControllerImpl.getClass();
        return false;
    }

    public boolean isOpenQsEvent(MotionEvent motionEvent) {
        SecPanelSplitHelper secPanelSplitHelper;
        int pointerCount = motionEvent.getPointerCount();
        int actionMasked = motionEvent.getActionMasked();
        boolean z = actionMasked == 5 && pointerCount == 2;
        boolean z2 = actionMasked == 0 && (motionEvent.isButtonPressed(32) || motionEvent.isButtonPressed(64));
        boolean z3 = actionMasked == 0 && (motionEvent.isButtonPressed(2) || motionEvent.isButtonPressed(4));
        if (!z && !z2 && !z3) {
            SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = this.mSecQuickSettingsControllerImpl;
            if (secQuickSettingsControllerImpl != null && (secPanelSplitHelper = (SecPanelSplitHelper) secQuickSettingsControllerImpl.panelSplitHelper$delegate.getValue()) != null) {
                SecPanelSplitHelper.Companion.getClass();
                if (!SecPanelSplitHelper.isEnabled || !secPanelSplitHelper.shouldQSDown(motionEvent)) {
                }
            }
            return false;
        }
        return true;
    }

    public final boolean isOutOfQsContents(float f, float f2) {
        SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = this.mSecQuickSettingsControllerImpl;
        if (secQuickSettingsControllerImpl != null) {
            SecQSPanelController qsPanelController = secQuickSettingsControllerImpl.getQsPanelController();
            SecQSPanel view = qsPanelController != null ? qsPanelController.getView() : null;
            int[] iArr = new int[2];
            for (int i = 0; i < 2; i++) {
                iArr[i] = 0;
            }
            if (view != null) {
                view.getLocationInWindow(iArr);
            }
            if (f2 > iArr[1] + (view != null ? view.getBottom() : 0) || !secQuickSettingsControllerImpl.isInTouchQsArea(f)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isQsFragmentCreated() {
        return this.mQs != null;
    }

    public final boolean isSplitShadeAndTouchXOutsideQs(float f) {
        return f > this.mQsFrame.getX() + ((float) this.mQsFrame.getWidth());
    }

    public boolean isTracking() {
        return ((Boolean) ((ShadeRepositoryImpl) this.mShadeRepository).legacyQsTracking.$$delegate_0.getValue()).booleanValue();
    }

    public boolean isTwoFingerExpandPossible() {
        return this.mTwoFingerExpandPossible;
    }

    public final void onExpansionStarted$1() {
        ValueAnimator valueAnimator = this.mExpansionAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ((NotificationPanelViewController) this.mPanelViewControllerLazy.get()).cancelHeightAnimator();
        DejankUtils.notifyRendererOfExpensiveFrame(this.mPanelView, "onExpansionStarted");
        setExpansionHeight(this.mExpansionHeight);
        int i = SceneContainerFlag.$r8$clinit;
        this.mNotificationStackScrollLayoutController.checkSnoozeLeavebehind();
    }

    public void onHeightChanged() {
        this.mMaxExpansionHeight = isQsFragmentCreated() ? this.mQs.getDesiredHeight() : 0;
        if (getExpanded() && this.mFullyExpanded) {
            this.mExpansionHeight = this.mMaxExpansionHeight;
            NotificationPanelViewController$$ExternalSyntheticLambda0 notificationPanelViewController$$ExternalSyntheticLambda0 = this.mExpansionHeightSetToMaxListener;
            if (notificationPanelViewController$$ExternalSyntheticLambda0 != null) {
                notificationPanelViewController$$ExternalSyntheticLambda0.onExpansionHeightSetToMax(true);
            }
        }
        if (this.mAccessibilityManager.isEnabled()) {
            this.mPanelView.setAccessibilityPaneTitle(((NotificationPanelViewController) this.mPanelViewControllerLazy.get()).determineAccessibilityPaneTitle());
        }
        int i = this.mMaxExpansionHeight;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        notificationStackScrollLayoutController.getClass();
        int i2 = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.getClass();
        notificationStackScrollLayout.mMaxTopPadding = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:88:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0217  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onIntercept(android.view.MotionEvent r17) {
        /*
            Method dump skipped, instructions count: 634
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.QuickSettingsControllerImpl.onIntercept(android.view.MotionEvent):boolean");
    }

    public final void setClippingBounds() {
        int left;
        int right;
        int i;
        QuickSettingsControllerImpl quickSettingsControllerImpl;
        float computeExpansionFraction = computeExpansionFraction();
        int calculateBottomPosition = calculateBottomPosition(computeExpansionFraction);
        boolean z = ((computeExpansionFraction > 0.0f ? 1 : (computeExpansionFraction == 0.0f ? 0 : -1)) == 0 && calculateBottomPosition > 0) || ((computeExpansionFraction > 0.0f ? 1 : (computeExpansionFraction == 0.0f ? 0 : -1)) > 0);
        int calculateTopClippingBound = calculateTopClippingBound(calculateBottomPosition);
        NotificationPanelView notificationPanelView = this.mPanelView;
        int bottom = notificationPanelView.getBottom();
        boolean z2 = this.mIsFullWidth;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        if (z2) {
            left = 0;
        } else {
            notificationStackScrollLayoutController.getClass();
            int i2 = SceneContainerFlag.$r8$clinit;
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            left = notificationStackScrollLayoutController.mView.getLeft() + this.mDisplayLeftInset;
        }
        if (this.mIsFullWidth) {
            right = notificationPanelView.getRight();
            i = this.mDisplayRightInset;
        } else {
            notificationStackScrollLayoutController.getClass();
            int i3 = SceneContainerFlag.$r8$clinit;
            RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
            right = notificationStackScrollLayoutController.mView.getRight();
            i = this.mDisplayLeftInset;
        }
        int i4 = right + i;
        int min = Math.min(calculateTopClippingBound, bottom);
        if (!this.mAnimateNextNotificationBounds || this.mLastClipBounds.isEmpty()) {
            quickSettingsControllerImpl = this;
            if (quickSettingsControllerImpl.mClippingAnimator != null) {
                quickSettingsControllerImpl.mClippingAnimationEndBounds.set(left, min, i4, bottom);
            } else {
                quickSettingsControllerImpl.applyClippingImmediately(z, left, min, i4, bottom);
            }
        } else {
            this.mClippingAnimationEndBounds.set(left, min, i4, bottom);
            Rect rect = this.mLastClipBounds;
            final int i5 = rect.left;
            final int i6 = rect.top;
            final int i7 = rect.right;
            final int i8 = rect.bottom;
            ValueAnimator valueAnimator = this.mClippingAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mClippingAnimator = ofFloat;
            ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            this.mClippingAnimator.setDuration(this.mNotificationBoundsAnimationDuration);
            this.mClippingAnimator.setStartDelay(0L);
            final boolean z3 = z;
            quickSettingsControllerImpl = this;
            this.mClippingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda30
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    QuickSettingsControllerImpl quickSettingsControllerImpl2 = QuickSettingsControllerImpl.this;
                    int i9 = i5;
                    int i10 = i6;
                    int i11 = i7;
                    int i12 = i8;
                    boolean z4 = z3;
                    quickSettingsControllerImpl2.getClass();
                    float animatedFraction = valueAnimator2.getAnimatedFraction();
                    quickSettingsControllerImpl2.applyClippingImmediately(z4, (int) MathUtils.lerp(i9, quickSettingsControllerImpl2.mClippingAnimationEndBounds.left, animatedFraction), (int) MathUtils.lerp(i10, quickSettingsControllerImpl2.mClippingAnimationEndBounds.top, animatedFraction), (int) MathUtils.lerp(i11, quickSettingsControllerImpl2.mClippingAnimationEndBounds.right, animatedFraction), (int) MathUtils.lerp(i12, quickSettingsControllerImpl2.mClippingAnimationEndBounds.bottom, animatedFraction));
                }
            });
            quickSettingsControllerImpl.mClippingAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    QuickSettingsControllerImpl quickSettingsControllerImpl2 = QuickSettingsControllerImpl.this;
                    quickSettingsControllerImpl2.mClippingAnimator = null;
                    quickSettingsControllerImpl2.mIsPulseExpansionResettingAnimator = false;
                }
            });
            quickSettingsControllerImpl.mClippingAnimator.start();
        }
        quickSettingsControllerImpl.mAnimateNextNotificationBounds = false;
    }

    public final void setExpandImmediate(boolean z) {
        if (z != isExpandImmediate()) {
            ShadeLogger shadeLogger = this.mShadeLog;
            shadeLogger.getClass();
            LogLevel logLevel = LogLevel.VERBOSE;
            ShadeLogger$$ExternalSyntheticLambda0 shadeLogger$$ExternalSyntheticLambda0 = new ShadeLogger$$ExternalSyntheticLambda0(13);
            LogBuffer logBuffer = shadeLogger.buffer;
            LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) obtain).bool1 = z;
            logBuffer.commit(obtain);
            QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
            if (quickPanelLogger != null) {
                quickPanelLogger.logPanelState("setExpandImmediate: " + z);
            }
            ((ShadeRepositoryImpl) this.mShadeRepository)._legacyExpandImmediate.updateState(null, Boolean.valueOf(z));
        }
    }

    public void setExpanded(boolean z) {
        StringBuilder sb;
        if (getExpanded() != z) {
            ((ShadeRepositoryImpl) this.mShadeRepository)._legacyIsQsExpanded.updateState(null, Boolean.valueOf(z));
            updateQsState$2();
            NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) this.mPanelViewControllerLazy.get();
            notificationPanelViewController.updateExpandedHeightToMaxHeight();
            notificationPanelViewController.updateSystemUiStateFlags();
            NavigationBarView navigationBarView = ((NavigationBarControllerImpl) notificationPanelViewController.mNavigationBarController).getNavigationBarView(notificationPanelViewController.mDisplayId);
            if (navigationBarView != null) {
                navigationBarView.updateSlippery();
            }
            int i = this.mMinExpansionHeight;
            int i2 = this.mMaxExpansionHeight;
            boolean z2 = this.mStackScrollerOverscrolling;
            boolean z3 = this.mAnimatorExpand;
            boolean z4 = this.mAnimating;
            ShadeLogger shadeLogger = this.mShadeLog;
            shadeLogger.getClass();
            LogLevel logLevel = LogLevel.VERBOSE;
            ShadeLogger$$ExternalSyntheticLambda0 shadeLogger$$ExternalSyntheticLambda0 = new ShadeLogger$$ExternalSyntheticLambda0(12);
            LogBuffer logBuffer = shadeLogger.buffer;
            LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = "QS Expansion Changed.";
            logMessageImpl.bool1 = z;
            logMessageImpl.int1 = i;
            logMessageImpl.int2 = i2;
            logMessageImpl.bool2 = z2;
            logMessageImpl.bool3 = z3;
            logMessageImpl.long1 = Boolean.compare(z4, false);
            logBuffer.commit(obtain);
            QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
            if (quickPanelLogger != null && (sb = this.mQuickPanelLogBuilder) != null) {
                sb.setLength(0);
                sb.append("setExpanded: expanded: ");
                sb.append(z);
                sb.append(", getMinExpansionHeight(): ");
                sb.append(this.mMinExpansionHeight);
                sb.append(", getMaxExpansionHeight(): ");
                sb.append(this.mMaxExpansionHeight);
                sb.append(", mStackScrollerOverscrolling: ");
                sb.append(this.mStackScrollerOverscrolling);
                sb.append(", mAnimatorExpand: ");
                sb.append(this.mAnimatorExpand);
                sb.append(", mAnimating: ");
                sb.append(this.mAnimating);
                quickPanelLogger.logPanelState(sb.toString());
            }
            String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
            this.mKeyguardUpdateMonitor.setPanelExpandingStarted(getExpanded());
            if (z) {
                this.mRemoteInputManager.closeRemoteInputs(false);
            }
        }
        updateNightMode(this.mPanelView.getVisibility());
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0041, code lost:
    
        if (r12 != 1) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0056, code lost:
    
        if (r11.mBarState == 1) goto L18;
     */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0088  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setExpansionHeight(float r12) {
        /*
            Method dump skipped, instructions count: 311
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.QuickSettingsControllerImpl.setExpansionHeight(float):void");
    }

    public void setQs(QS qs) {
        this.mQs = qs;
    }

    public void setStatusBarMinHeight(int i) {
        this.mStatusBarMinHeight = i;
    }

    public final void setTracking(boolean z) {
        StringBuilder sb;
        ShadeRepository shadeRepository = this.mShadeRepository;
        QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
        if (quickPanelLogger != null && (sb = this.mQuickPanelLogBuilder) != null) {
            sb.setLength(0);
            sb.append("setTracking: ");
            sb.append(((ShadeRepositoryImpl) shadeRepository).legacyQsTracking.$$delegate_0.getValue());
            sb.append(" -> ");
            sb.append(z);
            quickPanelLogger.logPanelState(sb.toString());
        }
        ((ShadeRepositoryImpl) shadeRepository)._legacyQsTracking.updateState(null, Boolean.valueOf(z));
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x002a, code lost:
    
        if (r0 == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0040, code lost:
    
        if ((r4 != null ? r4.isKeyguardPanelDisabled() : false) != false) goto L72;
     */
    @Override // com.android.systemui.shade.QuickSettingsController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean shouldQuickSettingsIntercept(float r13, float r14, float r15) {
        /*
            Method dump skipped, instructions count: 317
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.QuickSettingsControllerImpl.shouldQuickSettingsIntercept(float, float, float):boolean");
    }

    public final void traceQsJank(boolean z, boolean z2) {
        InteractionJankMonitor interactionJankMonitor = (InteractionJankMonitor) this.mInteractionJankMonitorLazy.get();
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
        if (this.mQs == null) {
            return;
        }
        boolean isExpandImmediate = isExpandImmediate();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        if (!isExpandImmediate && !getExpanded()) {
            notificationStackScrollLayoutController.getClass();
            int i = SceneContainerFlag.$r8$clinit;
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            notificationStackScrollLayout.getClass();
            notificationStackScrollLayout.mStackScrollAlgorithm.getClass();
        }
        float computeExpansionFraction = computeExpansionFraction();
        this.mQs.setQsExpansion(computeExpansionFraction(), this.mShadeExpandedFraction, getHeaderTranslation(), 1.0f);
        if (QuickStepContract.ALLOW_BACK_GESTURE_IN_SHADE) {
            ((NotificationPanelViewController) this.mPanelViewControllerLazy.get()).getClass();
        }
        MediaHierarchyManager mediaHierarchyManager = this.mMediaHierarchyManager;
        boolean z = false;
        if (mediaHierarchyManager.qsExpansion != computeExpansionFraction) {
            mediaHierarchyManager.qsExpansion = computeExpansionFraction;
            MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, false, 3);
            if (mediaHierarchyManager.getQSTransformationProgress() >= 0.0f) {
                mediaHierarchyManager.updateTargetState();
                mediaHierarchyManager.applyTargetStateIfNotAnimating();
            }
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
        setClippingBounds();
        SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = this.mSecQuickSettingsControllerImpl;
        if (secQuickSettingsControllerImpl == null || !SecPanelSplitHelper.isEnabled()) {
            int i2 = SceneContainerFlag.$r8$clinit;
            notificationStackScrollLayoutController.setQsExpansionFraction(computeExpansionFraction);
        } else {
            secQuickSettingsControllerImpl.notificationStackScrollLayoutController.setQsExpansionFraction(0.0f);
        }
        NotificationShadeDepthController notificationShadeDepthController = this.mDepthController;
        notificationShadeDepthController.getClass();
        if (Float.isNaN(computeExpansionFraction)) {
            Log.w("DepthController", "Invalid qs expansion");
        } else if (notificationShadeDepthController.qsPanelExpansion != computeExpansionFraction) {
            notificationShadeDepthController.qsPanelExpansion = computeExpansionFraction;
            notificationShadeDepthController.scheduleUpdate();
        }
        this.mStatusBarKeyguardViewManager.setQsExpansion(computeExpansionFraction);
        ShadeRepositoryImpl shadeRepositoryImpl = (ShadeRepositoryImpl) this.mShadeRepository;
        shadeRepositoryImpl._qsExpansion.updateState(null, Float.valueOf(computeExpansionFraction));
        float computeExpansionFraction2 = this.mBarState == 1 ? computeExpansionFraction() : this.mShadeExpandedFraction;
        ShadeHeaderController shadeHeaderController = this.mShadeHeaderController;
        if (shadeHeaderController.qsVisible && shadeHeaderController.shadeExpandedFraction != computeExpansionFraction2) {
            shadeHeaderController.shadeExpandedFraction = computeExpansionFraction2;
            shadeHeaderController.updateIgnoredSlots();
        }
        if (shadeHeaderController.visible && shadeHeaderController.qsExpandedFraction != computeExpansionFraction) {
            shadeHeaderController.qsExpandedFraction = computeExpansionFraction;
            shadeHeaderController.iconContainer.mQsExpansionTransitioning = computeExpansionFraction > 0.0f && computeExpansionFraction < 1.0f;
            shadeHeaderController.updateIgnoredSlots();
        }
        boolean z3 = this.mVisible;
        if (shadeHeaderController.qsVisible != z3) {
            shadeHeaderController.qsVisible = z3;
            HeaderPrivacyIconsController headerPrivacyIconsController = shadeHeaderController.privacyIconsController;
            if (z3) {
                headerPrivacyIconsController.listening = true;
                PrivacyItemController privacyItemController = headerPrivacyIconsController.privacyItemController;
                PrivacyConfig privacyConfig = privacyItemController.privacyConfig;
                headerPrivacyIconsController.micCameraIndicatorsEnabled = privacyConfig.micCameraAvailable;
                headerPrivacyIconsController.locationIndicatorsEnabled = privacyConfig.locationAvailable;
                privacyItemController.addCallback(headerPrivacyIconsController.picCallback);
                headerPrivacyIconsController.taskbarIndicatorController.setDesktopStatusBarIconCallback(headerPrivacyIconsController.desktopCallback);
            } else {
                headerPrivacyIconsController.listening = false;
                headerPrivacyIconsController.privacyItemController.removeCallback(headerPrivacyIconsController.picCallback);
                headerPrivacyIconsController.privacyChipLogged = false;
                List list = headerPrivacyIconsController.taskbarIndicatorController.mDesktopStatusBarIconCallback;
                if (list != null) {
                    TypeIntrinsics.asMutableCollection(list).remove(headerPrivacyIconsController.desktopCallback);
                }
            }
            shadeHeaderController.updateVisibility$7();
        }
        boolean z4 = this.mFullyExpanded;
        LightBarControllerImpl lightBarControllerImpl = (LightBarControllerImpl) this.mLightBarController;
        if (lightBarControllerImpl.mQsExpanded != z4) {
            lightBarControllerImpl.mQsExpanded = z4;
            lightBarControllerImpl.reevaluate();
        }
        boolean z5 = this.mFullyExpanded;
        shadeRepositoryImpl._legacyQsFullscreen.updateState(null, Boolean.valueOf(z5));
        if (this.mPanelSplitEnabled) {
            z5 = false;
        }
        NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout2.getClass();
        int i3 = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
        if (z5 != notificationStackScrollLayout2.mQsFullScreen) {
            notificationStackScrollLayout2.mQsFullScreen = z5;
            notificationStackScrollLayout2.updateAlgorithmLayoutMinHeight();
            notificationStackScrollLayout2.updateScrollability();
        }
        if (this.mBarState != 1 && (!z5 || this.mExpansionFromOverscroll)) {
            z = true;
        }
        notificationStackScrollLayoutController.mView.mScrollingEnabled = z;
    }

    public final void updateExpansionEnabledAmbient() {
        AmbientState ambientState = this.mAmbientState;
        this.mExpansionEnabledAmbient = ((float) ambientState.mScrollY) <= ((float) ambientState.getTopPadding()) - this.mQuickQsHeaderHeight;
        QS qs = this.mQs;
        if (qs != null) {
            qs.setHeaderClickable(isExpansionEnabled());
        }
    }

    public final void updateMinHeight() {
        float f = this.mMinExpansionHeight;
        if (this.mBarState != 1 || this.mAmbientState.isNeedsToExpandLocksNoti()) {
            int qsMinExpansionHeight = this.mQs.getQsMinExpansionHeight();
            this.mMinExpansionHeight = qsMinExpansionHeight;
            int i = this.mQuickQsOffsetHeight;
            if (qsMinExpansionHeight < i) {
                this.mMinExpansionHeight = i;
            }
        } else {
            this.mMinExpansionHeight = 0;
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
        notificationStackScrollLayout.getHeight();
        int i5 = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        Math.max(notificationStackScrollLayout.mMaxLayoutHeight - notificationStackScrollLayout.mContentHeight, 0);
        notificationStackScrollLayout.mAmbientState.getClass();
    }

    public final void updateNightMode(int i) {
        ScrimController scrimController = this.mScrimController;
        if (scrimController != null) {
            if ((this.mPanelView.getResources().getConfiguration().uiMode & 32) != 0 && i == 0 && (getExpanded() || this.mBarState == 2)) {
                scrimController.mSecLsScrimControlHelper.setQsExpandedOnNightMode(true);
            } else {
                scrimController.mSecLsScrimControlHelper.setQsExpandedOnNightMode(false);
            }
        }
    }

    public final void updateQsState$2() {
        QS qs = this.mQs;
        if (qs == null) {
            return;
        }
        qs.setExpanded(getExpanded());
    }

    public final void updateResources$1() {
        ((SplitShadeStateControllerImpl) this.mSplitShadeStateController).shouldUseSplitNotificationShade();
        QS qs = this.mQs;
        if (qs != null) {
            qs.setInSplitShade(false);
        }
        this.mSplitShadeNotificationsScrimMarginBottom = this.mResources.getDimensionPixelSize(R.dimen.split_shade_notifications_scrim_margin_bottom);
        NotificationPanelView notificationPanelView = this.mPanelView;
        boolean shouldUseLargeScreenShadeHeader = LargeScreenUtils.shouldUseLargeScreenShadeHeader(notificationPanelView.getResources());
        this.mUseLargeScreenShadeHeader = shouldUseLargeScreenShadeHeader;
        int dimensionPixelSize = shouldUseLargeScreenShadeHeader ? 0 : this.mResources.getDimensionPixelSize(R.dimen.notification_panel_margin_top);
        boolean z = this.mUseLargeScreenShadeHeader;
        ShadeHeaderController shadeHeaderController = this.mShadeHeaderController;
        if (shadeHeaderController.largeScreenActive != z) {
            shadeHeaderController.largeScreenActive = z;
            shadeHeaderController.updateTransition$1();
        }
        this.mAmbientState.mStackTopMargin = dimensionPixelSize;
        this.mQuickQsHeaderHeight = 0;
        this.mEnableClipping = this.mResources.getBoolean(R.bool.qs_enable_clipping);
        Context context = this.mPanelView.getContext();
        ((WindowManagerProviderImpl) this.mWindowManagerProvider).getClass();
        WindowMetrics currentWindowMetrics = WindowManagerUtils.getWindowManager(context).getCurrentWindowMetrics();
        this.mCachedGestureInsets = currentWindowMetrics.getWindowInsets().getInsets(WindowInsets.Type.systemGestures());
        this.mCachedWindowWidth = currentWindowMetrics.getBounds().width();
        this.mQuickQsOffsetHeight = SystemBarUtils.getQuickQsOffsetHeight(notificationPanelView.getContext());
    }
}
