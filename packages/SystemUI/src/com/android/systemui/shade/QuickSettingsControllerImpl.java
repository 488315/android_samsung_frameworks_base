package com.android.systemui.shade;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.util.IndentingPrintWriter;
import android.util.MathUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.policy.SystemBarUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.animation.ShadeInterpolation;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.QuickPanelLogger;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.ui.controller.MediaHierarchyManager;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.privacy.PrivacyConfig;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.qs.HeaderPrivacyIconsController;
import com.android.systemui.qs.NonInterceptingScrollView;
import com.android.systemui.qs.QSFragmentLegacy;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.SecQSImpl;
import com.android.systemui.qs.TileChunkLayoutBarExpandHelper;
import com.android.systemui.qs.animator.SecQSImplAnimatorManager;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.BarType;
import com.android.systemui.qs.bar.TileChunkLayoutBar;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.scrim.ScrimDrawable;
import com.android.systemui.scrim.ScrimView;
import com.android.systemui.shade.QuickSettingsControllerImpl;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.PulseExpansionHandler;
import com.android.systemui.statusbar.QsFrameTranslateController;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.footer.shared.FooterViewRefactor;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaView;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.KeyguardSecAffordanceHelper;
import com.android.systemui.statusbar.phone.KeyguardStatusBarView;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LockscreenGestureLogger;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.ScrimState;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.statusbar.policy.CastController;
import com.android.systemui.statusbar.policy.CastControllerImpl;
import com.android.systemui.statusbar.policy.CastControllerImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherController;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.kotlin.JavaAdapter;
import dagger.Lazy;
import dalvik.annotation.optimization.NeverCompile;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import kotlin.collections.EmptyIterator;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QuickSettingsControllerImpl implements QuickSettingsController, Dumpable, PanelScreenShotLogger.LogProvider {
    public final AccessibilityManager mAccessibilityManager;
    public final AmbientState mAmbientState;
    public float mAmount;
    public boolean mAnimateNextNotificationBounds;
    public boolean mAnimating;
    public boolean mAnimatingHiddenFromCollapsed;
    public boolean mAnimatorExpand;
    public NotificationPanelViewController$$ExternalSyntheticLambda7 mApplyClippingImmediatelyListener;
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
    public NotificationPanelViewController$$ExternalSyntheticLambda7 mExpansionHeightListener;
    public NotificationPanelViewController$$ExternalSyntheticLambda7 mExpansionHeightSetToMaxListener;
    public final FalsingManager mFalsingManager;
    public int mFalsingThreshold;
    public NotificationPanelViewController$$ExternalSyntheticLambda7 mFlingQsWithoutClickListener;
    public boolean mFullyExpanded;
    public float mInitialHeightOnTouch;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public final Lazy mInteractionJankMonitorLazy;
    public boolean mIsFullWidth;
    public boolean mIsPulseExpansionResettingAnimator;
    public boolean mIsRubberBanded;
    public boolean mIsTranslationResettingAnimator;
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
    public long mNotificationBoundsAnimationDelay;
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
    public NotificationPanelViewController$$ExternalSyntheticLambda7 mQsStateUpdateListener;
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
    public ValueAnimator mSizeChangeAnimator;
    public float mSlopMultiplier;
    public int mSplitShadeNotificationsScrimMarginBottom;
    public final SplitShadeStateController mSplitShadeStateController;
    public boolean mStackScrollerOverscrolling;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public int mStatusBarMinHeight;
    public final StatusBarTouchableRegionManager mStatusBarTouchableRegionManager;
    public boolean mTouchAboveFalsingThreshold;
    public int mTouchSlop;
    public int mTrackingPointer;
    public float mTranslationForFullShadeTransition;
    public boolean mTwoFingerExpandPossible;
    public boolean mUseLargeScreenShadeHeader;
    public boolean mVisible;
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
    public final QuickSettingsControllerImpl$$ExternalSyntheticLambda2 mQsHeightListener = new QuickSettingsControllerImpl$$ExternalSyntheticLambda2(this);
    public final QuickSettingsControllerImpl$$ExternalSyntheticLambda11 mQsCollapseExpandAction = new QuickSettingsControllerImpl$$ExternalSyntheticLambda11(this, 1);
    public final QuickSettingsControllerImpl$$ExternalSyntheticLambda2 mQsScrollListener = new QuickSettingsControllerImpl$$ExternalSyntheticLambda2(this);
    public boolean mPanelExpandedForFingerPrint = false;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class LockscreenShadeTransitionCallback implements LockscreenShadeTransitionController.Callback {
        public /* synthetic */ LockscreenShadeTransitionCallback(QuickSettingsControllerImpl quickSettingsControllerImpl, int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
        public final void onPulseExpansionFinished() {
            QuickSettingsControllerImpl quickSettingsControllerImpl = QuickSettingsControllerImpl.this;
            quickSettingsControllerImpl.mAnimateNextNotificationBounds = true;
            quickSettingsControllerImpl.mNotificationBoundsAnimationDuration = 448L;
            quickSettingsControllerImpl.mNotificationBoundsAnimationDelay = 0L;
            quickSettingsControllerImpl.mIsPulseExpansionResettingAnimator = true;
        }

        @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
        public final void setTransitionToFullShadeAmount(float f, boolean z, long j) {
            String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
            QuickSettingsControllerImpl quickSettingsControllerImpl = QuickSettingsControllerImpl.this;
            boolean z2 = f > ((float) quickSettingsControllerImpl.mMinExpansionHeight) && !quickSettingsControllerImpl.getExpanded$1();
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class NsslOverscrollTopChangedListener {
        public /* synthetic */ NsslOverscrollTopChangedListener(QuickSettingsControllerImpl quickSettingsControllerImpl, int i) {
            this();
        }

        private NsslOverscrollTopChangedListener() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class QsFragmentListener implements FragmentHostManager.FragmentListener {
        public QsFragmentListener() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
        public final void onFragmentViewCreated(Fragment fragment) {
            View view;
            SecPanelSplitHelper secPanelSplitHelper;
            View view2;
            QSImpl qSImpl;
            SecQSImpl secQSImpl;
            BarController barController;
            View view3;
            View view4;
            QSImpl qSImpl2;
            SecQSImpl secQSImpl2;
            SecQSImplAnimatorManager secQSImplAnimatorManager;
            QS qs = (QS) fragment;
            QuickSettingsControllerImpl quickSettingsControllerImpl = QuickSettingsControllerImpl.this;
            quickSettingsControllerImpl.mQs = qs;
            qs.setPanelView(quickSettingsControllerImpl.mQsHeightListener);
            quickSettingsControllerImpl.mQs.setCollapseExpandAction(quickSettingsControllerImpl.mQsCollapseExpandAction);
            quickSettingsControllerImpl.mQs.setHeaderClickable(quickSettingsControllerImpl.isExpansionEnabled());
            quickSettingsControllerImpl.mQs.setOverscrolling(quickSettingsControllerImpl.mStackScrollerOverscrolling);
            quickSettingsControllerImpl.mQs.setInSplitShade(false);
            quickSettingsControllerImpl.mQs.setIsNotificationPanelFullWidth(quickSettingsControllerImpl.mIsFullWidth);
            quickSettingsControllerImpl.mQs.getView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$QsFragmentListener$$ExternalSyntheticLambda0
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view5, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    QuickSettingsControllerImpl.QsFragmentListener qsFragmentListener = QuickSettingsControllerImpl.QsFragmentListener.this;
                    if (i4 - i2 != i8 - i6) {
                        QuickSettingsControllerImpl.this.onHeightChanged();
                    } else {
                        qsFragmentListener.getClass();
                    }
                }
            });
            QS qs2 = quickSettingsControllerImpl.mQs;
            quickSettingsControllerImpl.mLockscreenShadeTransitionController.qS = qs2;
            quickSettingsControllerImpl.mNotificationStackScrollLayoutController.mView.mQsHeader = (ViewGroup) qs2.getHeader();
            quickSettingsControllerImpl.mQs.setScrollListener(quickSettingsControllerImpl.mQsScrollListener);
            quickSettingsControllerImpl.updateExpansion();
            SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = quickSettingsControllerImpl.mSecQuickSettingsControllerImpl;
            if (secQuickSettingsControllerImpl != null) {
                Object obj = secQuickSettingsControllerImpl.qsSupplier.get();
                View view5 = null;
                QSFragmentLegacy qSFragmentLegacy = obj instanceof QSFragmentLegacy ? (QSFragmentLegacy) obj : null;
                if (qSFragmentLegacy != null && (qSImpl2 = qSFragmentLegacy.mQsImpl) != null && (secQSImpl2 = qSImpl2.mSecQSImpl) != null && (secQSImplAnimatorManager = secQSImpl2.secQSImplAnimatorManager) != null) {
                    secQSImplAnimatorManager.setNotificationStackScrollerController(secQuickSettingsControllerImpl.notificationStackScrollLayoutController);
                    secQSImplAnimatorManager.setPanelViewController((NotificationPanelViewController) secQuickSettingsControllerImpl.panelViewControllerLazy.get());
                }
                final SecQuickTileChunkLayoutBarTouchHelper secQuickTileChunkLayoutBarTouchHelper = secQuickSettingsControllerImpl.tileChunkLayoutBarTouchHelper;
                if (secQuickTileChunkLayoutBarTouchHelper != null) {
                    Object obj2 = secQuickTileChunkLayoutBarTouchHelper.qsSupplier.get();
                    QSFragmentLegacy qSFragmentLegacy2 = obj2 instanceof QSFragmentLegacy ? (QSFragmentLegacy) obj2 : null;
                    if (qSFragmentLegacy2 != null && (qSImpl = qSFragmentLegacy2.mQsImpl) != null && (secQSImpl = qSImpl.mSecQSImpl) != null && (barController = secQSImpl.barController) != null) {
                        BarItemImpl barInExpanded = barController.getBarInExpanded(BarType.TILE_CHUNK_LAYOUT);
                        TileChunkLayoutBar tileChunkLayoutBar = barInExpanded instanceof TileChunkLayoutBar ? (TileChunkLayoutBar) barInExpanded : null;
                        if (tileChunkLayoutBar != null) {
                            secQuickTileChunkLayoutBarTouchHelper.tileChunkLayoutBar = tileChunkLayoutBar;
                            secQuickTileChunkLayoutBarTouchHelper.expandHelper = new TileChunkLayoutBarExpandHelper(tileChunkLayoutBar);
                            TileChunkLayoutBar tileChunkLayoutBar2 = secQuickTileChunkLayoutBarTouchHelper.tileChunkLayoutBar;
                            if (tileChunkLayoutBar2 != null && (view4 = tileChunkLayoutBar2.mScrollIndicatorClickContainer) != null) {
                                view4.setAccessibilityDelegate(secQuickTileChunkLayoutBarTouchHelper.scrollIndicatorAccessibilityDelegate);
                                view4.setOnKeyListener(new View.OnKeyListener() { // from class: com.android.systemui.shade.SecQuickTileChunkLayoutBarTouchHelper$initForAccessibility$1$1
                                    @Override // android.view.View.OnKeyListener
                                    public final boolean onKey(View view6, int i, KeyEvent keyEvent) {
                                        if (i != 66 || keyEvent.getAction() != 1) {
                                            return false;
                                        }
                                        TileChunkLayoutBarExpandHelper tileChunkLayoutBarExpandHelper = SecQuickTileChunkLayoutBarTouchHelper.this.expandHelper;
                                        if (tileChunkLayoutBarExpandHelper != null) {
                                            tileChunkLayoutBarExpandHelper.forceToggleBar();
                                        }
                                        return true;
                                    }
                                });
                            }
                            TileChunkLayoutBar tileChunkLayoutBar3 = secQuickTileChunkLayoutBarTouchHelper.tileChunkLayoutBar;
                            if (tileChunkLayoutBar3 != null && (view3 = tileChunkLayoutBar3.mScrollIndicatorClickContainer) != null) {
                                view3.setClickable(true);
                                view3.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.shade.SecQuickTileChunkLayoutBarTouchHelper$initForClickListener$1$1
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view6) {
                                        TileChunkLayoutBarExpandHelper tileChunkLayoutBarExpandHelper = SecQuickTileChunkLayoutBarTouchHelper.this.expandHelper;
                                        if (tileChunkLayoutBarExpandHelper != null) {
                                            tileChunkLayoutBarExpandHelper.forceToggleBar();
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
                QS qs3 = (QS) secQuickSettingsControllerImpl.qsSupplier.get();
                if (qs3 != null && (view2 = qs3.getView()) != null) {
                    NonInterceptingScrollView nonInterceptingScrollView = (NonInterceptingScrollView) view2.findViewById(R.id.expanded_qs_scroll_view);
                    if (nonInterceptingScrollView == null) {
                        nonInterceptingScrollView = null;
                    }
                    secQuickSettingsControllerImpl.qsScrollView = nonInterceptingScrollView;
                }
                PanelScreenShotLogger.INSTANCE.addLogProvider("SecQuickSettingsControllerImpl", secQuickSettingsControllerImpl.logProvider);
                secQuickSettingsControllerImpl.naviBarGestureMode = ((NavigationModeController) secQuickSettingsControllerImpl.navigationModeController$delegate.getValue()).addListener(secQuickSettingsControllerImpl.modeChangedListener);
                SecPanelExpansionStateInteractor secPanelExpansionStateInteractor = (SecPanelExpansionStateInteractor) secQuickSettingsControllerImpl.panelExpansionStateInteractor$delegate.getValue();
                if (secPanelExpansionStateInteractor != null) {
                    secPanelExpansionStateInteractor.shadeExpansionStateManager.addExpansionListener(secPanelExpansionStateInteractor.shadeExpansionListener);
                    secPanelExpansionStateInteractor.statusBarStateController.addCallback(secPanelExpansionStateInteractor.stateListener);
                    secPanelExpansionStateInteractor.wakefulnessLifecycle.addObserver(secPanelExpansionStateInteractor.observer);
                }
                SecQSExpansionStateInteractor secQSExpansionStateInteractor = (SecQSExpansionStateInteractor) secQuickSettingsControllerImpl.qsExpansionStateInteractor$delegate.getValue();
                if (secQSExpansionStateInteractor != null && (secPanelSplitHelper = (SecPanelSplitHelper) secQSExpansionStateInteractor.splitHelper$delegate.getValue()) != null) {
                    secPanelSplitHelper.addListener(secQSExpansionStateInteractor.panelTransitionStateListener);
                }
                SecPanelSplitHelper secPanelSplitHelper2 = (SecPanelSplitHelper) secQuickSettingsControllerImpl.panelSplitHelper$delegate.getValue();
                if (secPanelSplitHelper2 != null) {
                    QS qs4 = (QS) secQuickSettingsControllerImpl.qsSupplier.get();
                    secPanelSplitHelper2.qs = qs4;
                    if (qs4 != null && (view = qs4.getView()) != null) {
                        view5 = view.findViewById(R.id.expanded_qs_scroll_view);
                    }
                    secPanelSplitHelper2.qsScrollView = view5;
                    if (view5 != null) {
                        secPanelSplitHelper2.updatePanelVisibility();
                    }
                }
                secQuickSettingsControllerImpl.updateScrollViewLocationDelta();
            }
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
                SecPanelExpansionStateInteractor secPanelExpansionStateInteractor = (SecPanelExpansionStateInteractor) secQuickSettingsControllerImpl.panelExpansionStateInteractor$delegate.getValue();
                if (secPanelExpansionStateInteractor != null) {
                    secPanelExpansionStateInteractor.shadeExpansionStateManager.removeExpansionListener(secPanelExpansionStateInteractor.shadeExpansionListener);
                    secPanelExpansionStateInteractor.statusBarStateController.removeCallback(secPanelExpansionStateInteractor.stateListener);
                    secPanelExpansionStateInteractor.wakefulnessLifecycle.removeObserver(secPanelExpansionStateInteractor.observer);
                }
                SecQSExpansionStateInteractor secQSExpansionStateInteractor = (SecQSExpansionStateInteractor) secQuickSettingsControllerImpl.qsExpansionStateInteractor$delegate.getValue();
                if (secQSExpansionStateInteractor != null && (secPanelSplitHelper = (SecPanelSplitHelper) secQSExpansionStateInteractor.splitHelper$delegate.getValue()) != null) {
                    secPanelSplitHelper.removeListener(secQSExpansionStateInteractor.panelTransitionStateListener);
                }
            }
            if (fragment == quickSettingsControllerImpl.mQs) {
                quickSettingsControllerImpl.mQs = null;
            }
        }
    }

    public QuickSettingsControllerImpl(final Lazy lazy, NotificationPanelView notificationPanelView, QsFrameTranslateController qsFrameTranslateController, PulseExpansionHandler pulseExpansionHandler, NotificationRemoteInputManager notificationRemoteInputManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, LightBarController lightBarController, NotificationStackScrollLayoutController notificationStackScrollLayoutController, LockscreenShadeTransitionController lockscreenShadeTransitionController, NotificationShadeDepthController notificationShadeDepthController, ShadeHeaderController shadeHeaderController, StatusBarTouchableRegionManager statusBarTouchableRegionManager, KeyguardStateController keyguardStateController, KeyguardBypassController keyguardBypassController, ScrimController scrimController, MediaDataManager mediaDataManager, MediaHierarchyManager mediaHierarchyManager, AmbientState ambientState, RecordingController recordingController, FalsingManager falsingManager, AccessibilityManager accessibilityManager, LockscreenGestureLogger lockscreenGestureLogger, MetricsLogger metricsLogger, Lazy lazy2, ShadeLogger shadeLogger, DumpManager dumpManager, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, ShadeRepository shadeRepository, ShadeInteractor shadeInteractor, ActiveNotificationsInteractor activeNotificationsInteractor, JavaAdapter javaAdapter, CastController castController, SplitShadeStateController splitShadeStateController, Lazy lazy3, Lazy lazy4, PluginLockMediator pluginLockMediator, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        SceneContainerFlag.assertInLegacyMode();
        this.mPanelViewControllerLazy = lazy;
        this.mPanelView = notificationPanelView;
        this.mQsFrame = (FrameLayout) notificationPanelView.findViewById(R.id.qs_frame);
        this.mKeyguardStatusBar = (KeyguardStatusBarView) notificationPanelView.findViewById(R.id.keyguard_header);
        this.mResources = notificationPanelView.getResources();
        this.mSplitShadeStateController = splitShadeStateController;
        ((SplitShadeStateControllerImpl) splitShadeStateController).shouldUseSplitNotificationShade();
        this.mQsFrameTranslateController = qsFrameTranslateController;
        this.mPulseExpansionHandler = pulseExpansionHandler;
        pulseExpansionHandler.pulseExpandAbortListener = new QuickSettingsControllerImpl$$ExternalSyntheticLambda11(this, 3);
        this.mRemoteInputManager = notificationRemoteInputManager;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mLightBarController = lightBarController;
        this.mNotificationStackScrollLayoutController = notificationStackScrollLayoutController;
        this.mLockscreenShadeTransitionController = lockscreenShadeTransitionController;
        this.mDepthController = notificationShadeDepthController;
        this.mShadeHeaderController = shadeHeaderController;
        this.mStatusBarTouchableRegionManager = statusBarTouchableRegionManager;
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
        lockscreenShadeTransitionController.addCallback(new LockscreenShadeTransitionCallback(this, 0));
        dumpManager.registerDumpable(this);
        this.mQuickPanelLogger = new QuickPanelLogger("QSCI");
        this.mQuickPanelLogBuilder = new StringBuilder();
        final int i = 0;
        Function function = new Function(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda25
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                int i2 = i;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i2) {
                    case 0:
                        return Integer.valueOf(quickSettingsControllerImpl.calculateBottomPosition(((Float) obj).floatValue()));
                    default:
                        return Integer.valueOf(quickSettingsControllerImpl.calculateTopClippingBound(((Integer) obj).intValue()));
                }
            }
        };
        final int i2 = 1;
        Function function2 = new Function(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda25
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                int i22 = i2;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i22) {
                    case 0:
                        return Integer.valueOf(quickSettingsControllerImpl.calculateBottomPosition(((Float) obj).floatValue()));
                    default:
                        return Integer.valueOf(quickSettingsControllerImpl.calculateTopClippingBound(((Integer) obj).intValue()));
                }
            }
        };
        final int i3 = 4;
        DoubleSupplier doubleSupplier = new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda3
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                int i4 = i3;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i4) {
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
        };
        Context context = notificationPanelView.getContext();
        final int i4 = 5;
        DoubleSupplier doubleSupplier2 = new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda3
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                int i42 = i4;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i42) {
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
        };
        QuickSettingsControllerImpl$$ExternalSyntheticLambda0 quickSettingsControllerImpl$$ExternalSyntheticLambda0 = new QuickSettingsControllerImpl$$ExternalSyntheticLambda0(this, 2);
        final int i5 = 6;
        DoubleSupplier doubleSupplier3 = new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda3
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                int i42 = i5;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i42) {
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
        };
        final int i6 = 0;
        DoubleSupplier doubleSupplier4 = new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda3
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                int i42 = i6;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i42) {
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
        };
        QuickSettingsControllerImpl$$ExternalSyntheticLambda0 quickSettingsControllerImpl$$ExternalSyntheticLambda02 = new QuickSettingsControllerImpl$$ExternalSyntheticLambda0(this, 3);
        BooleanSupplier booleanSupplier = new BooleanSupplier() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda5
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return QuickSettingsControllerImpl.this.mShadeExpandedHeight >= ((float) ((NotificationPanelViewController) lazy.get()).getMaxPanelHeight());
            }
        };
        final int i7 = 7;
        DoubleSupplier doubleSupplier5 = new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda3
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                int i42 = i7;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i42) {
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
        };
        final int i8 = 8;
        DoubleSupplier doubleSupplier6 = new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda3
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                int i42 = i8;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i42) {
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
        };
        QuickSettingsControllerImpl$$ExternalSyntheticLambda0 quickSettingsControllerImpl$$ExternalSyntheticLambda03 = new QuickSettingsControllerImpl$$ExternalSyntheticLambda0(this, 4);
        final int i9 = 0;
        Supplier supplier = new Supplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda9
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i10 = i9;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i10) {
                    case 0:
                        return quickSettingsControllerImpl.mQsFrame;
                    default:
                        return quickSettingsControllerImpl.mQs;
                }
            }
        };
        final int i10 = 1;
        Supplier supplier2 = new Supplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda9
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i102 = i10;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i102) {
                    case 0:
                        return quickSettingsControllerImpl.mQsFrame;
                    default:
                        return quickSettingsControllerImpl.mQs;
                }
            }
        };
        QuickSettingsControllerImpl$$ExternalSyntheticLambda11 quickSettingsControllerImpl$$ExternalSyntheticLambda11 = new QuickSettingsControllerImpl$$ExternalSyntheticLambda11(this, 0);
        final int i11 = 1;
        DoubleSupplier doubleSupplier7 = new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda3
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                int i42 = i11;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i42) {
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
        };
        final int i12 = 0;
        DoubleConsumer doubleConsumer = new DoubleConsumer(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda14
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleConsumer
            public final void accept(double d) {
                int i13 = i12;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i13) {
                    case 0:
                        quickSettingsControllerImpl.mInitialTouchX = (float) d;
                        break;
                    default:
                        quickSettingsControllerImpl.mInitialTouchY = (float) d;
                        break;
                }
            }
        };
        final int i13 = 2;
        DoubleSupplier doubleSupplier8 = new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda3
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                int i42 = i13;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i42) {
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
        };
        final int i14 = 1;
        DoubleConsumer doubleConsumer2 = new DoubleConsumer(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda14
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleConsumer
            public final void accept(double d) {
                int i132 = i14;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i132) {
                    case 0:
                        quickSettingsControllerImpl.mInitialTouchX = (float) d;
                        break;
                    default:
                        quickSettingsControllerImpl.mInitialTouchY = (float) d;
                        break;
                }
            }
        };
        final int i15 = 3;
        this.mSecQuickSettingsControllerImpl = new SecQuickSettingsControllerImpl(ambientState, function, function2, doubleSupplier, context, doubleSupplier2, quickSettingsControllerImpl$$ExternalSyntheticLambda0, doubleSupplier3, doubleSupplier4, shadeHeaderController, quickSettingsControllerImpl$$ExternalSyntheticLambda02, booleanSupplier, doubleSupplier5, doubleSupplier6, notificationStackScrollLayoutController, lazy, quickSettingsControllerImpl$$ExternalSyntheticLambda03, supplier, supplier2, quickSettingsControllerImpl$$ExternalSyntheticLambda11, doubleSupplier7, doubleConsumer, doubleSupplier8, doubleConsumer2, new DoubleSupplier(this) { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda3
            public final /* synthetic */ QuickSettingsControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                int i42 = i15;
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                switch (i42) {
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
        }, new QuickSettingsControllerImpl$$ExternalSyntheticLambda11(this, 2), new QuickSettingsControllerImpl$$ExternalSyntheticLambda19(this, 0), new IntConsumer() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda20
            @Override // java.util.function.IntConsumer
            public final void accept(int i16) {
                QuickSettingsControllerImpl.this.mTrackingPointer = i16;
            }
        }, new IntSupplier() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda21
            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                return QuickSettingsControllerImpl.this.mTrackingPointer;
            }
        }, new QuickSettingsControllerImpl$$ExternalSyntheticLambda19(this, 1));
        this.mQuickQsOffsetHeight = SystemBarUtils.getQuickQsOffsetHeight(notificationPanelView.getContext());
        this.mPluginLockMediator = pluginLockMediator;
        PanelScreenShotLogger.INSTANCE.addLogProvider("QuickSettingsController", this);
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
    }

    public final void applyClippingImmediately(int i, int i2, int i3, int i4, boolean z) {
        int i5;
        boolean z2;
        int i6;
        int i7 = this.mScrimCornerRadius;
        this.mLastClipBounds.set(i, i2, i3, i4);
        boolean z3 = this.mIsFullWidth;
        ScrimController scrimController = this.mScrimController;
        if (z3) {
            float f = (this.mRecordingController.isRecording() || ((CastControllerImpl) this.mCastController).getCastDevices().stream().anyMatch(new CastControllerImpl$$ExternalSyntheticLambda0())) ? 0.0f : this.mScreenCornerRadius;
            float f2 = this.mScrimCornerRadius;
            int lerp = (int) MathUtils.lerp(f, f2, Math.min(i2 / f2, 1.0f));
            float calculateBottomCornerRadius = !getExpanded$1() ? calculateBottomCornerRadius(0.0f) : 0.0f;
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
            i5 = lerp;
            z2 = z;
        } else {
            i5 = i7;
            z2 = false;
        }
        if (isQsFragmentCreated()) {
            boolean z4 = this.mPulseExpansionHandler.isExpanding;
            this.mTranslationForFullShadeTransition = (z4 || (this.mClippingAnimator != null && (this.mIsTranslationResettingAnimator || this.mIsPulseExpansionResettingAnimator))) ? (z4 || this.mIsPulseExpansionResettingAnimator) ? Math.max(0.0f, (i2 - getHeaderHeight()) / 2.0f) : (i2 - getHeaderHeight()) * 0.175f : 0.0f;
            int i8 = ((NotificationPanelViewController) this.mPanelViewControllerLazy.get()).mNavigationBarBottomHeight;
            int i9 = this.mAmbientState.mStackTopMargin;
            this.mQsFrameTranslateController.getClass();
            float translationY = this.mQsFrame.getTranslationY();
            int top = this.mEnableClipping ? (int) ((i2 - translationY) - this.mQsFrame.getTop()) : 0;
            int top2 = this.mEnableClipping ? (int) ((i4 - translationY) - this.mQsFrame.getTop()) : 0;
            this.mVisible = z;
            this.mQs.setQsVisible(z);
            i6 = i5;
            this.mQs.setFancyClipping(this.mDisplayLeftInset, top, this.mDisplayRightInset, top2, i5, z, this.mIsFullWidth);
        } else {
            i6 = i5;
        }
        float f3 = i;
        float f4 = i2;
        float f5 = i3;
        float f6 = i4 + i6;
        ScrimView scrimView2 = scrimController.mNotificationsScrim;
        if (scrimView2.mDrawableBounds == null) {
            scrimView2.mDrawableBounds = new Rect();
        }
        int i10 = (int) f4;
        scrimView2.mDrawableBounds.set((int) f3, i10, (int) f5, (int) f6);
        scrimView2.mDrawable.setBounds(scrimView2.mDrawableBounds);
        if (scrimController.mNotificationsAlpha > 0.0f) {
            ((KeyguardRepositoryImpl) scrimController.mKeyguardInteractor.repository).topClippingBounds.setValue(Integer.valueOf(i10));
        } else {
            ((KeyguardRepositoryImpl) scrimController.mKeyguardInteractor.repository).topClippingBounds.setValue(null);
        }
        NotificationPanelViewController$$ExternalSyntheticLambda7 notificationPanelViewController$$ExternalSyntheticLambda7 = this.mApplyClippingImmediatelyListener;
        if (notificationPanelViewController$$ExternalSyntheticLambda7 != null) {
            Rect rect = this.mLastClipBounds;
            boolean isQsFragmentCreated = isQsFragmentCreated();
            boolean z5 = this.mVisible;
            NotificationPanelViewController notificationPanelViewController = notificationPanelViewController$$ExternalSyntheticLambda7.f$0;
            if (isQsFragmentCreated) {
                ((KeyguardRepositoryImpl) notificationPanelViewController.mKeyguardInteractor.repository)._isQuickSettingsVisible.updateState(null, Boolean.valueOf(z5));
            }
            notificationPanelViewController.mKeyguardStatusViewController.setClipBounds(z2 ? rect : null);
            notificationPanelViewController.mKeyguardStatusBarViewController.updateTopClipping(i2);
        }
        ScrimView scrimView3 = scrimController.mScrimBehind;
        if (scrimView3 != null && scrimController.mNotificationsScrim != null) {
            scrimView3.setCornerRadius(i6);
            scrimController.mNotificationsScrim.setCornerRadius(i6);
        }
        int i11 = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
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
        SceneContainerFlag.assertInLegacyMode();
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.getClass();
        SceneContainerFlag.assertInLegacyMode();
        if (notificationStackScrollLayout.mRoundedRectClippingLeft == left && notificationStackScrollLayout.mRoundedRectClippingRight == left2 && notificationStackScrollLayout.mRoundedRectClippingBottom == top4 && notificationStackScrollLayout.mRoundedRectClippingTop == top3) {
            float[] fArr = notificationStackScrollLayout.mBgCornerRadii;
            float f7 = 0;
            if (fArr[0] == f7 && fArr[5] == f7) {
                return;
            }
        }
        notificationStackScrollLayout.mRoundedRectClippingLeft = left;
        notificationStackScrollLayout.mRoundedRectClippingTop = top3;
        notificationStackScrollLayout.mRoundedRectClippingBottom = top4;
        notificationStackScrollLayout.mRoundedRectClippingRight = left2;
        float[] fArr2 = notificationStackScrollLayout.mBgCornerRadii;
        float f8 = 0;
        fArr2[0] = f8;
        fArr2[1] = f8;
        fArr2[2] = f8;
        fArr2[3] = f8;
        fArr2[4] = f8;
        fArr2[5] = f8;
        fArr2[6] = f8;
        fArr2[7] = f8;
        notificationStackScrollLayout.updateRoundedClipPath();
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

    public final float calculateNotificationsTopPadding(int i) {
        SceneContainerFlag.assertInLegacyMode();
        boolean z = this.mBarState == 1;
        if (this.mSizeChangeAnimator != null && !SecPanelSplitHelper.isEnabled()) {
            return Math.max(((Integer) this.mSizeChangeAnimator.getAnimatedValue()).intValue(), i);
        }
        if (z && !this.mAmbientState.mDragDownOnKeyguard) {
            return MathUtils.lerp(i, this.mMaxExpansionHeight, computeExpansionFraction());
        }
        SecPanelSplitHelper.Companion.getClass();
        return SecPanelSplitHelper.isEnabled ? ((float) Math.max(r0.getNotificationsTopPadding((float) this.mSecQuickSettingsControllerImpl.minExpansionHeightSupplier.getAsDouble()), this.mQuickQsHeaderHeight)) + this.mLastOverscroll : Math.max(this.mQsFrameTranslateController.getNotificationsTopPadding(this.mExpansionHeight), this.mQuickQsHeaderHeight);
    }

    public final int calculatePanelHeightExpanded(int i) {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        float height = (notificationStackScrollLayoutController.mView.getHeight() - notificationStackScrollLayoutController.mView.getEmptyBottomMargin()) - notificationStackScrollLayoutController.mView.mAmbientState.mTopPadding;
        if (notificationStackScrollLayoutController.getNotGoneChildCount() == 0) {
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            notificationStackScrollLayout.getClass();
            boolean z = NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW;
            if (z ? false : notificationStackScrollLayout.mEmptyShadeView.mIsVisible) {
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
                notificationStackScrollLayout2.getClass();
                height = z ? 0 : notificationStackScrollLayout2.mEmptyShadeView.getHeight();
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
        float max = Math.max(i2, i) + height + notificationStackScrollLayoutController.mView.mTopPaddingOverflow;
        if (max > r6.getHeight()) {
            max = Math.max(notificationStackScrollLayoutController.mView.getLayoutMinHeight() + i2, notificationStackScrollLayoutController.mView.getHeight());
        }
        return (int) max;
    }

    public final int calculateTopClippingBound(int i) {
        float edgePosition = getEdgePosition();
        this.mAmbientState.mNotificationScrimTop = edgePosition;
        if (this.mBarState == 1) {
            if (!this.mKeyguardBypassController.getBypassEnabled()) {
                i = (int) Math.min(i, edgePosition);
            }
        } else if (!getExpanded$1()) {
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
        asIndenting.println(getExpanded$1());
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
        asIndenting.print("mInterceptRegion=");
        asIndenting.println(this.mInterceptRegion);
        asIndenting.print("mClippingAnimationEndBounds=");
        asIndenting.println(this.mClippingAnimationEndBounds);
        asIndenting.print("mLastClipBounds=");
        asIndenting.println(this.mLastClipBounds);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00df  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void flingQs(float r19, int r20, final com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda11 r21, final boolean r22) {
        /*
            Method dump skipped, instructions count: 406
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.QuickSettingsControllerImpl.flingQs(float, int, com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda11, boolean):void");
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
        float height = this.mShadeHeaderController.header.getHeight();
        AmbientState ambientState = this.mAmbientState;
        float f = ambientState.mExpansionFraction;
        return Math.max(height * f, ((ambientState.mStackTopMargin * f) + ambientState.mStackY) - ambientState.mScrollY);
    }

    @Override // com.android.systemui.shade.QuickSettingsController
    public final boolean getExpanded$1() {
        return ((Boolean) ((ShadeRepositoryImpl) this.mShadeRepository).legacyIsQsExpanded.$$delegate_0.getValue()).booleanValue();
    }

    public final int getHeaderHeight() {
        if (isQsFragmentCreated()) {
            return this.mQs.getHeader().getHeight();
        }
        return 0;
    }

    public final float getHeaderTranslation() {
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

    @Override // com.android.systemui.shade.QuickSettingsController
    public final SecQuickSettingsControllerImpl getSecQuickSettingsControllerImpl$1() {
        return this.mSecQuickSettingsControllerImpl;
    }

    public float getShadeExpandedHeight() {
        return this.mShadeExpandedHeight;
    }

    public final boolean handleTouch(MotionEvent motionEvent, boolean z, boolean z2) {
        int i;
        int i2;
        int pointerId;
        StringBuilder sb;
        QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
        if (quickPanelLogger != null && (sb = this.mQuickPanelLogBuilder) != null) {
            sb.setLength(0);
            sb.append("isFullyCollapsed: ");
            sb.append(z);
            sb.append(", isShadeOrQsHeightAnimationRunning: ");
            sb.append(z2);
            quickPanelLogger.quickPanelLoggerHelper.handleTouchLogger.log(motionEvent, quickPanelLogger.tag, sb.toString());
        }
        SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = this.mSecQuickSettingsControllerImpl;
        if (secQuickSettingsControllerImpl == null && isSplitShadeAndTouchXOutsideQs(motionEvent.getX())) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        boolean z3 = this.mShadeExpandedFraction == 1.0f && this.mBarState != 1 && (getExpanded$1() ^ true) && isExpansionEnabled();
        ShadeLogger shadeLogger = this.mShadeLog;
        if (actionMasked == 0 && z3) {
            shadeLogger.logMotionEvent(motionEvent, "handleQsTouch: down action, QS tracking enabled");
            setTracking(true);
            traceQsJank(true, false);
            this.mConflictingExpansionGesture = true;
            onExpansionStarted$1();
            this.mInitialHeightOnTouch = this.mExpansionHeight;
            this.mInitialTouchY = motionEvent.getY();
            this.mInitialTouchX = motionEvent.getX();
        }
        Lazy lazy = this.mPanelViewControllerLazy;
        if (!z && !z2 && motionEvent.getActionMasked() == 0) {
            if (QuickStepContract.ALLOW_BACK_GESTURE_IN_SHADE) {
                ((NotificationPanelViewController) lazy.get()).getClass();
            }
            if (shouldQuickSettingsIntercept(motionEvent.getX(), motionEvent.getY(), -1.0f)) {
                shadeLogger.logMotionEvent(motionEvent, "handleQsDown: down action, QS tracking enabled");
                setTracking(true);
                onExpansionStarted$1();
                this.mInitialHeightOnTouch = this.mExpansionHeight;
                this.mInitialTouchY = motionEvent.getY();
                this.mInitialTouchX = motionEvent.getX();
                ((NotificationPanelViewController) lazy.get()).notifyExpandingFinished();
            }
        }
        if (!this.mLastShadeFlingWasExpanding && computeExpansionFraction() <= 0.01d && this.mShadeExpandedFraction < 1.0d) {
            setTracking(false);
        }
        if (isExpandImmediate() || !isTracking()) {
            i = 1;
            i2 = 3;
        } else {
            if (quickPanelLogger != null) {
                quickPanelLogger.onTouchEvent(motionEvent);
            }
            int findPointerIndex = motionEvent.findPointerIndex(this.mTrackingPointer);
            if (findPointerIndex < 0) {
                this.mTrackingPointer = motionEvent.getPointerId(0);
                findPointerIndex = 0;
            }
            float y = motionEvent.getY(findPointerIndex);
            float x = motionEvent.getX(findPointerIndex);
            float f = y - this.mInitialTouchY;
            int actionMasked2 = motionEvent.getActionMasked();
            if (actionMasked2 != 0) {
                if (actionMasked2 != 1) {
                    if (actionMasked2 == 2) {
                        if (secQuickSettingsControllerImpl != null) {
                            int i3 = this.mTouchSlop;
                            if (secQuickSettingsControllerImpl.isBackGestureAllowed) {
                                int i4 = i3 * 5;
                                f = f > 0.0f ? RangesKt___RangesKt.coerceAtLeast(0.0f, f - i4) : RangesKt___RangesKt.coerceAtMost(0.0f, f + i4);
                            }
                        }
                        setExpansionHeight(this.mInitialHeightOnTouch + f);
                        if (f >= ((NotificationPanelViewController) lazy.get()).getFalsingThreshold()) {
                            this.mTouchAboveFalsingThreshold = true;
                        }
                        trackMovement$1(motionEvent);
                    } else if (actionMasked2 != 3) {
                        if (actionMasked2 == 6 && this.mTrackingPointer == (pointerId = motionEvent.getPointerId(motionEvent.getActionIndex()))) {
                            int i5 = motionEvent.getPointerId(0) != pointerId ? 0 : 1;
                            float y2 = motionEvent.getY(i5);
                            float x2 = motionEvent.getX(i5);
                            this.mTrackingPointer = motionEvent.getPointerId(i5);
                            this.mInitialHeightOnTouch = this.mExpansionHeight;
                            this.mInitialTouchY = y2;
                            this.mInitialTouchX = x2;
                        }
                    }
                }
                shadeLogger.logMotionEvent(motionEvent, "onQsTouch: up/cancel action, QS tracking disabled");
                setTracking(false);
                this.mTrackingPointer = -1;
                trackMovement$1(motionEvent);
                if (computeExpansionFraction() != 0.0f || y >= this.mInitialTouchY) {
                    boolean z4 = motionEvent.getActionMasked() == 3;
                    float currentVelocity = getCurrentVelocity();
                    NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) lazy.get();
                    notificationPanelViewController.getClass();
                    boolean z5 = Math.abs(currentVelocity) >= notificationPanelViewController.mFlingAnimationUtils.mMinVelocityPxPerSecond ? currentVelocity > 0.0f : notificationPanelViewController.mQsController.computeExpansionFraction() > 0.5f;
                    FalsingManager falsingManager = this.mFalsingManager;
                    if (z5) {
                        if (falsingManager.isUnlockingDisabled()) {
                            z5 = false;
                        } else {
                            float currentVelocity2 = getCurrentVelocity();
                            int i6 = this.mBarState == 1 ? 193 : 194;
                            float f2 = ((CentralSurfacesImpl) ((NotificationPanelViewController) lazy.get()).mCentralSurfaces).mDisplayMetrics.density;
                            this.mLockscreenGestureLogger.write(i6, (int) ((y - this.mInitialTouchY) / f2), (int) (currentVelocity2 / f2));
                        }
                    } else if (currentVelocity < 0.0f) {
                        falsingManager.isFalseTouch(12);
                    }
                    flingQs(currentVelocity, (!z5 || z4) ? 1 : 0, null, false);
                } else {
                    traceQsJank(false, motionEvent.getActionMasked() == 3);
                }
                VelocityTracker velocityTracker = this.mQsVelocityTracker;
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    this.mQsVelocityTracker = null;
                }
                if (secQuickSettingsControllerImpl != null) {
                    secQuickSettingsControllerImpl.isBackGestureAllowed = false;
                }
            } else {
                shadeLogger.logMotionEvent(motionEvent, "onQsTouch: down action, QS tracking enabled");
                setTracking(true);
                traceQsJank(true, false);
                this.mInitialTouchY = y;
                this.mInitialTouchX = x;
                onExpansionStarted$1();
                this.mInitialHeightOnTouch = this.mExpansionHeight;
                VelocityTracker velocityTracker2 = this.mQsVelocityTracker;
                if (velocityTracker2 != null) {
                    velocityTracker2.recycle();
                }
                this.mQsVelocityTracker = VelocityTracker.obtain();
                trackMovement$1(motionEvent);
            }
            if (!this.mConflictingExpansionGesture) {
                if (quickPanelLogger == null) {
                    return true;
                }
                quickPanelLogger.handleTouch(motionEvent, "!mConflictingExpansionGesture && !mSplitShadeEnabled", true);
                return true;
            }
            i = 1;
            i2 = 3;
        }
        if (actionMasked == i2 || actionMasked == i) {
            this.mConflictingExpansionGesture = false;
            if (isTracking()) {
                setTracking(false);
            }
            if (this.mTwoFingerExpandPossible) {
                this.mTwoFingerExpandPossible = false;
            }
        }
        if (actionMasked == 0 && z && isExpansionEnabled()) {
            this.mTwoFingerExpandPossible = true;
        }
        if (this.mTwoFingerExpandPossible && isOpenQsEvent(motionEvent) && motionEvent.getY(motionEvent.getActionIndex()) < this.mStatusBarMinHeight && this.mBarState != 1) {
            this.mMetricsLogger.count("panel_open_qs", 1);
            setExpandImmediate(true);
            NotificationPanelViewController$$ExternalSyntheticLambda7 notificationPanelViewController$$ExternalSyntheticLambda7 = this.mExpansionHeightSetToMaxListener;
            if (notificationPanelViewController$$ExternalSyntheticLambda7 != null) {
                notificationPanelViewController$$ExternalSyntheticLambda7.onExpansionHeightSetToMax(false);
            }
            QS qs = this.mQs;
            if (qs != null) {
                qs.setListening(true);
            }
        }
        return false;
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
            if (secQuickSettingsControllerImpl == null || (secPanelSplitHelper = (SecPanelSplitHelper) secQuickSettingsControllerImpl.panelSplitHelper$delegate.getValue()) == null) {
                return false;
            }
            SecPanelSplitHelper.Companion.getClass();
            if (!SecPanelSplitHelper.isEnabled || !secPanelSplitHelper.shouldQSDown(motionEvent)) {
                return false;
            }
        }
        return true;
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
        QS qs = this.mQs;
        if (qs != null) {
            QSImpl qSImpl = ((QSFragmentLegacy) qs).mQsImpl;
            if ((qSImpl != null ? qSImpl.mQSPanelController : null) != null) {
                QSImpl qSImpl2 = ((QSFragmentLegacy) qs).mQsImpl;
                if (!(qSImpl2 != null ? qSImpl2.mQSPanelController : null).mExpandSettingsPanel) {
                    ValueAnimator valueAnimator = this.mExpansionAnimator;
                    if (valueAnimator != null) {
                        valueAnimator.cancel();
                    }
                    ((NotificationPanelViewController) this.mPanelViewControllerLazy.get()).cancelHeightAnimator();
                }
            }
        }
        DejankUtils.notifyRendererOfExpensiveFrame(this.mPanelView, "onExpansionStarted");
        setExpansionHeight(this.mExpansionHeight);
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        this.mNotificationStackScrollLayoutController.checkSnoozeLeavebehind();
    }

    public void onHeightChanged() {
        this.mMaxExpansionHeight = isQsFragmentCreated() ? this.mQs.getDesiredHeight() : 0;
        if (getExpanded$1() && this.mFullyExpanded) {
            this.mExpansionHeight = this.mMaxExpansionHeight;
            NotificationPanelViewController$$ExternalSyntheticLambda7 notificationPanelViewController$$ExternalSyntheticLambda7 = this.mExpansionHeightSetToMaxListener;
            if (notificationPanelViewController$$ExternalSyntheticLambda7 != null) {
                notificationPanelViewController$$ExternalSyntheticLambda7.onExpansionHeightSetToMax(true);
            }
        }
        if (this.mAccessibilityManager.isEnabled()) {
            this.mPanelView.setAccessibilityPaneTitle(((NotificationPanelViewController) this.mPanelViewControllerLazy.get()).determineAccessibilityPaneTitle());
        }
        this.mNotificationStackScrollLayoutController.mView.mMaxTopPadding = this.mMaxExpansionHeight;
    }

    /* JADX WARN: Removed duplicated region for block: B:84:0x020c  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0213  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onIntercept(android.view.MotionEvent r17) {
        /*
            Method dump skipped, instructions count: 669
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.QuickSettingsControllerImpl.onIntercept(android.view.MotionEvent):boolean");
    }

    public final void setClippingBounds() {
        int right;
        int i;
        float computeExpansionFraction = computeExpansionFraction();
        int calculateBottomPosition = calculateBottomPosition(computeExpansionFraction);
        boolean z = true;
        boolean z2 = computeExpansionFraction == 0.0f && calculateBottomPosition > 0;
        boolean z3 = computeExpansionFraction > 0.0f;
        if (!z2 && !z3) {
            z = false;
        }
        int calculateTopClippingBound = calculateTopClippingBound(calculateBottomPosition);
        NotificationPanelView notificationPanelView = this.mPanelView;
        int bottom = notificationPanelView.getBottom();
        boolean z4 = this.mIsFullWidth;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        int left = z4 ? 0 : notificationStackScrollLayoutController.mView.getLeft() + this.mDisplayLeftInset;
        if (this.mIsFullWidth) {
            right = notificationPanelView.getRight();
            i = this.mDisplayRightInset;
        } else {
            right = notificationStackScrollLayoutController.mView.getRight();
            i = this.mDisplayLeftInset;
        }
        int i2 = right + i;
        int min = Math.min(calculateTopClippingBound, bottom);
        if (this.mAnimateNextNotificationBounds && !this.mLastClipBounds.isEmpty()) {
            this.mClippingAnimationEndBounds.set(left, min, i2, bottom);
            Rect rect = this.mLastClipBounds;
            final int i3 = rect.left;
            final int i4 = rect.top;
            final int i5 = rect.right;
            final int i6 = rect.bottom;
            ValueAnimator valueAnimator = this.mClippingAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mClippingAnimator = ofFloat;
            ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            this.mClippingAnimator.setDuration(this.mNotificationBoundsAnimationDuration);
            this.mClippingAnimator.setStartDelay(this.mNotificationBoundsAnimationDelay);
            final boolean z5 = z;
            this.mClippingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda31
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    QuickSettingsControllerImpl quickSettingsControllerImpl = QuickSettingsControllerImpl.this;
                    int i7 = i3;
                    int i8 = i4;
                    int i9 = i5;
                    int i10 = i6;
                    boolean z6 = z5;
                    quickSettingsControllerImpl.getClass();
                    float animatedFraction = valueAnimator2.getAnimatedFraction();
                    quickSettingsControllerImpl.applyClippingImmediately((int) MathUtils.lerp(i7, quickSettingsControllerImpl.mClippingAnimationEndBounds.left, animatedFraction), (int) MathUtils.lerp(i8, quickSettingsControllerImpl.mClippingAnimationEndBounds.top, animatedFraction), (int) MathUtils.lerp(i9, quickSettingsControllerImpl.mClippingAnimationEndBounds.right, animatedFraction), (int) MathUtils.lerp(i10, quickSettingsControllerImpl.mClippingAnimationEndBounds.bottom, animatedFraction), z6);
                }
            });
            this.mClippingAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    QuickSettingsControllerImpl quickSettingsControllerImpl = QuickSettingsControllerImpl.this;
                    quickSettingsControllerImpl.mClippingAnimator = null;
                    quickSettingsControllerImpl.mIsTranslationResettingAnimator = false;
                    quickSettingsControllerImpl.mIsPulseExpansionResettingAnimator = false;
                }
            });
            this.mClippingAnimator.start();
        } else if (this.mClippingAnimator != null) {
            this.mClippingAnimationEndBounds.set(left, min, i2, bottom);
        } else {
            applyClippingImmediately(left, min, i2, bottom, z);
        }
        this.mAnimateNextNotificationBounds = false;
        this.mNotificationBoundsAnimationDelay = 0L;
    }

    public final void setExpandImmediate(boolean z) {
        if (z != isExpandImmediate()) {
            ShadeLogger shadeLogger = this.mShadeLog;
            shadeLogger.getClass();
            LogLevel logLevel = LogLevel.VERBOSE;
            ShadeLogger$logQsExpandImmediateChanged$2 shadeLogger$logQsExpandImmediateChanged$2 = new Function1() { // from class: com.android.systemui.shade.ShadeLogger$logQsExpandImmediateChanged$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("qsExpandImmediate=", ((LogMessage) obj).getBool1());
                }
            };
            LogBuffer logBuffer = shadeLogger.buffer;
            LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$logQsExpandImmediateChanged$2, null);
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
        if (getExpanded$1() != z) {
            ((ShadeRepositoryImpl) this.mShadeRepository)._legacyIsQsExpanded.updateState(null, Boolean.valueOf(z));
            updateQsState$1$1();
            Lazy lazy = this.mPanelViewControllerLazy;
            NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) lazy.get();
            notificationPanelViewController.updateExpandedHeightToMaxHeight();
            notificationPanelViewController.mKeyguardStatusViewController.setStatusAccessibilityImportance(z ? 4 : 0);
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
            ShadeLogger$logQsExpansionChanged$2 shadeLogger$logQsExpansionChanged$2 = new Function1() { // from class: com.android.systemui.shade.ShadeLogger$logQsExpansionChanged$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    return logMessage.getStr1() + " qsExpanded=" + logMessage.getBool1() + ",qsMinExpansionHeight=" + logMessage.getInt1() + ",qsMaxExpansionHeight=" + logMessage.getInt2() + ",stackScrollerOverscrolling=" + logMessage.getBool2() + ",qsAnimatorExpand=" + logMessage.getBool3() + ",animatingQs=" + logMessage.getLong1();
                }
            };
            LogBuffer logBuffer = shadeLogger.buffer;
            LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$logQsExpansionChanged$2, null);
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
            this.mKeyguardUpdateMonitor.setPanelExpandingStarted(getExpanded$1());
            if (z) {
                this.mRemoteInputManager.closeRemoteInputs(false);
            }
            KeyguardBottomAreaView view = ((NotificationPanelViewController) lazy.get()).mKeyguardBottomAreaViewController.getView();
            view.setAllChildEnabled(view, !z);
            KeyguardSecAffordanceHelper keyguardSecAffordanceHelper = ((NotificationPanelViewController) lazy.get()).mSecAffordanceHelper;
            KeyguardSecAffordanceView keyguardSecAffordanceView = keyguardSecAffordanceHelper.mLeftIcon;
            Intrinsics.checkNotNull(keyguardSecAffordanceView);
            keyguardSecAffordanceView.mQsExpanded = z;
            KeyguardSecAffordanceView keyguardSecAffordanceView2 = keyguardSecAffordanceHelper.mRightIcon;
            Intrinsics.checkNotNull(keyguardSecAffordanceView2);
            keyguardSecAffordanceView2.mQsExpanded = z;
        }
        updateNightMode(this.mPanelView.getVisibility());
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x004d, code lost:
    
        if (r14 != 1) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setExpansionHeight(float r14) {
        /*
            Method dump skipped, instructions count: 331
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
    
        if (r0 == false) goto L75;
     */
    /* JADX WARN: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean shouldQuickSettingsIntercept(float r12, float r13, float r14) {
        /*
            Method dump skipped, instructions count: 313
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

    public final void trackMovement$1(MotionEvent motionEvent) {
        VelocityTracker velocityTracker = this.mQsVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.addMovement(motionEvent);
        }
    }

    public final void updateExpansion() {
        SecPanelExpansionStateInteractor secPanelExpansionStateInteractor;
        if (this.mQs != null) {
            Lazy lazy = this.mPanelViewControllerLazy;
            if (((NotificationPanelViewController) lazy.get()).mSecAffordanceHelper.isShortcutPreviewSwipingInProgress) {
                return;
            }
            boolean isExpandImmediate = isExpandImmediate();
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
            if (!isExpandImmediate && !getExpanded$1()) {
                notificationStackScrollLayoutController.mView.mStackScrollAlgorithm.getClass();
            }
            float computeExpansionFraction = computeExpansionFraction();
            this.mQs.setQsExpansion(computeExpansionFraction(), this.mShadeExpandedFraction, getHeaderTranslation(), 1.0f);
            if (QuickStepContract.ALLOW_BACK_GESTURE_IN_SHADE) {
                ((NotificationPanelViewController) lazy.get()).getClass();
            }
            MediaHierarchyManager mediaHierarchyManager = this.mMediaHierarchyManager;
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
                boolean z = calculateBottomPosition > 0;
                if (scrimController.mQsExpansion != notificationScrimAlpha || scrimController.mQsBottomVisible != z) {
                    scrimController.mQsExpansion = notificationScrimAlpha;
                    scrimController.mQsBottomVisible = z;
                    ScrimState scrimState = scrimController.mState;
                    if ((scrimState == ScrimState.SHADE_LOCKED || scrimState == ScrimState.KEYGUARD || scrimState == ScrimState.PULSING) && scrimController.mExpansionAffectsAlpha) {
                        scrimController.applyAndDispatchState();
                    }
                }
            }
            SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = this.mSecQuickSettingsControllerImpl;
            if (secQuickSettingsControllerImpl != null && (secPanelExpansionStateInteractor = (SecPanelExpansionStateInteractor) secQuickSettingsControllerImpl.panelExpansionStateInteractor$delegate.getValue()) != null) {
                secPanelExpansionStateInteractor.getRepository()._qsFraction.updateState(null, Float.valueOf(computeExpansionFraction));
            }
            setClippingBounds();
            if (secQuickSettingsControllerImpl == null || !SecPanelSplitHelper.isEnabled()) {
                notificationStackScrollLayoutController.setQsExpansionFraction(computeExpansionFraction);
            } else {
                secQuickSettingsControllerImpl.notificationStackScrollLayoutController.setQsExpansionFraction(0.0f);
            }
            this.mDepthController.getClass();
            this.mStatusBarKeyguardViewManager.setQsExpansion(computeExpansionFraction);
            ((ShadeRepositoryImpl) this.mShadeRepository)._qsExpansion.updateState(null, Float.valueOf(computeExpansionFraction));
            float computeExpansionFraction2 = this.mBarState == 1 ? computeExpansionFraction() : this.mShadeExpandedFraction;
            ShadeHeaderController shadeHeaderController = this.mShadeHeaderController;
            if (shadeHeaderController.qsVisible && shadeHeaderController.shadeExpandedFraction != computeExpansionFraction2) {
                shadeHeaderController.shadeExpandedFraction = computeExpansionFraction2;
            }
            if (shadeHeaderController.visible && shadeHeaderController.qsExpandedFraction != computeExpansionFraction) {
                shadeHeaderController.qsExpandedFraction = computeExpansionFraction;
                boolean z2 = computeExpansionFraction > 0.0f && computeExpansionFraction < 1.0f;
                StatusIconContainer statusIconContainer = shadeHeaderController.iconContainer;
                statusIconContainer.mQsExpansionTransitioning = z2;
                if (computeExpansionFraction < 0.5d) {
                    EmptyList emptyList = shadeHeaderController.carrierIconSlots;
                    EmptyList emptyList2 = emptyList != null ? emptyList : null;
                    statusIconContainer.getClass();
                    Iterator it = emptyList2.iterator();
                    boolean z3 = false;
                    while (it.hasNext()) {
                        z3 |= statusIconContainer.mIgnoredSlots.remove((String) it.next());
                    }
                    if (z3) {
                        statusIconContainer.requestLayout();
                    }
                } else {
                    EmptyList emptyList3 = shadeHeaderController.carrierIconSlots;
                    if (emptyList3 == null) {
                        emptyList3 = null;
                    }
                    EmptyIterator emptyIterator = (EmptyIterator) emptyList3.iterator();
                    if (emptyIterator.hasNext()) {
                        emptyIterator.next();
                        throw null;
                    }
                }
            }
            boolean z4 = this.mVisible;
            if (shadeHeaderController.qsVisible != z4) {
                shadeHeaderController.qsVisible = z4;
                HeaderPrivacyIconsController headerPrivacyIconsController = shadeHeaderController.privacyIconsController;
                if (z4) {
                    headerPrivacyIconsController.listening = true;
                    PrivacyItemController privacyItemController = headerPrivacyIconsController.privacyItemController;
                    PrivacyConfig privacyConfig = privacyItemController.privacyConfig;
                    headerPrivacyIconsController.micCameraIndicatorsEnabled = privacyConfig.micCameraAvailable;
                    headerPrivacyIconsController.locationIndicatorsEnabled = privacyConfig.locationAvailable;
                    privacyItemController.addCallback(headerPrivacyIconsController.picCallback);
                    ((DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class)).registerCallback(headerPrivacyIconsController.desktopCallback);
                } else {
                    headerPrivacyIconsController.listening = false;
                    headerPrivacyIconsController.privacyItemController.removeCallback(headerPrivacyIconsController.picCallback);
                    headerPrivacyIconsController.privacyChipLogged = false;
                    ((DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class)).unregisterCallback(headerPrivacyIconsController.desktopCallback);
                }
                shadeHeaderController.updateVisibility$5();
            }
            boolean z5 = this.mFullyExpanded;
            LightBarController lightBarController = this.mLightBarController;
            if (lightBarController.mQsExpanded != z5) {
                lightBarController.mQsExpanded = z5;
                lightBarController.reevaluate();
            }
            int i = FooterViewRefactor.$r8$clinit;
            Flags.notificationsFooterViewRefactor();
        }
    }

    public final void updateExpansionEnabledAmbient() {
        AmbientState ambientState = this.mAmbientState;
        this.mExpansionEnabledAmbient = ((float) ambientState.mScrollY) <= ((float) ambientState.mTopPadding) - this.mQuickQsHeaderHeight;
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
        notificationStackScrollLayout.getEmptyBottomMargin();
        notificationStackScrollLayout.mAmbientState.getClass();
    }

    public final void updateNightMode(int i) {
        ScrimController scrimController = this.mScrimController;
        if (scrimController != null) {
            if ((this.mPanelView.getResources().getConfiguration().uiMode & 32) != 0 && i == 0 && (getExpanded$1() || this.mBarState == 2)) {
                scrimController.mSecLsScrimControlHelper.setQsExpandedOnNightMode(true);
            } else {
                scrimController.mSecLsScrimControlHelper.setQsExpandedOnNightMode(false);
            }
        }
    }

    public final void updateQsState$1$1() {
        int i = FooterViewRefactor.$r8$clinit;
        Flags.notificationsFooterViewRefactor();
        boolean expanded$1 = getExpanded$1();
        ((ShadeRepositoryImpl) this.mShadeRepository)._legacyQsFullscreen.updateState(null, Boolean.valueOf(expanded$1));
        boolean z = false;
        if (this.mPanelSplitEnabled) {
            expanded$1 = false;
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.getClass();
        Flags.notificationsFooterViewRefactor();
        notificationStackScrollLayout.mQsFullScreen = expanded$1;
        notificationStackScrollLayout.updateAlgorithmLayoutMinHeight();
        boolean z2 = !notificationStackScrollLayout.mQsFullScreen && notificationStackScrollLayout.getScrollRange$1() > 0;
        if (z2 != notificationStackScrollLayout.mScrollable) {
            notificationStackScrollLayout.mScrollable = z2;
            notificationStackScrollLayout.setFocusable(z2);
            notificationStackScrollLayout.updateForwardAndBackwardScrollability();
        }
        Flags.notificationsFooterViewRefactor();
        notificationStackScrollLayoutController.updateShowEmptyShadeView();
        int i2 = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        if (this.mBarState != 1 && (!expanded$1 || this.mExpansionFromOverscroll)) {
            z = true;
        }
        SceneContainerFlag.assertInLegacyMode();
        notificationStackScrollLayoutController.mView.mScrollingEnabled = z;
        NotificationPanelViewController$$ExternalSyntheticLambda7 notificationPanelViewController$$ExternalSyntheticLambda7 = this.mQsStateUpdateListener;
        if (notificationPanelViewController$$ExternalSyntheticLambda7 != null) {
            boolean expanded$12 = getExpanded$1();
            boolean z3 = this.mStackScrollerOverscrolling;
            KeyguardUserSwitcherController keyguardUserSwitcherController = notificationPanelViewController$$ExternalSyntheticLambda7.f$0.mKeyguardUserSwitcherController;
            if (keyguardUserSwitcherController != null && expanded$12 && !z3) {
                keyguardUserSwitcherController.closeSwitcherIfOpenAndNotSimple(true);
            }
        }
        QS qs = this.mQs;
        if (qs == null) {
            return;
        }
        qs.setExpanded(getExpanded$1());
    }
}
