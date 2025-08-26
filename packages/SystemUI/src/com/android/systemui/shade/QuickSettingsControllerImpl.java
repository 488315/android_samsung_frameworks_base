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
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.MathUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.policy.SystemBarUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.BasicRune;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
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
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.QuickPanelLogger;
import com.android.systemui.log.QuickPanelLoggerHelper;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.ui.controller.MediaHierarchyManager;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.views.NavigationBarView;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.privacy.PrivacyConfig;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.qs.HeaderPrivacyIconsController;
import com.android.systemui.qs.NonInterceptingScrollView;
import com.android.systemui.qs.QSContainerImpl;
import com.android.systemui.qs.QSFragmentLegacy;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.SecQSImpl;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.TileChunkLayoutBarExpandHelper;
import com.android.systemui.qs.animator.SecQSImplAnimatorManager;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.BarType;
import com.android.systemui.qs.bar.TileChunkLayoutBar;
import com.android.systemui.qs.flags.QSComposeFragment;
import com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticOutline0;
import com.android.systemui.samsung.quicksetting.SecQSPanelComposeAdapter;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.scrim.ScrimDrawable;
import com.android.systemui.scrim.ScrimView;
import com.android.systemui.shade.QuickSettingsControllerImpl;
import com.android.systemui.shade.data.repository.SecPanelSAStatusLogRepository;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$stateListener$1;
import com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor;
import com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.PulseExpansionHandler;
import com.android.systemui.statusbar.QsFrameTranslateController;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.emptyshade.ui.view.EmptyShadeView;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableView;
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
import com.android.wm.shell.animation.FlingAnimationUtils;
import dagger.Lazy;
import dalvik.annotation.optimization.NeverCompile;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
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
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.flow.StateFlowImpl;

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
            this.f$0.onHeightChanged();
        }
    };
    public final QuickSettingsControllerImpl$$ExternalSyntheticLambda10 mQsCollapseExpandAction = new QuickSettingsControllerImpl$$ExternalSyntheticLambda10(this, 1);
    public final QuickSettingsControllerImpl$$ExternalSyntheticLambda22 mQsScrollListener = new QS.ScrollListener() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda22
        @Override // com.android.systemui.plugins.qs.QS.ScrollListener
        public final void onQsPanelScrollChanged(int i) throws Resources.NotFoundException {
            QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
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
    public int mNSSLTopPadding = 0;
    public boolean mPanelExpandedForFingerPrint = false;

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

    public final class NsslOverscrollTopChangedListener {
        public /* synthetic */ NsslOverscrollTopChangedListener(QuickSettingsControllerImpl quickSettingsControllerImpl, int i) {
            this();
        }

        private NsslOverscrollTopChangedListener() {
        }
    }

    public final class QsFragmentListener implements FragmentHostManager.FragmentListener {
        public QsFragmentListener() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
        public final void onFragmentViewCreated(Fragment fragment) {
            RepeatWhenAttachedKt.C09181 c09181RepeatWhenAttached;
            View view;
            View view2;
            SecPanelSplitHelper secPanelSplitHelper;
            View view3;
            QSImpl qSImpl;
            SecQSImpl secQSImpl;
            BarController barController;
            View view4;
            View view5;
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
                public final void onLayoutChange(View view6, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    QuickSettingsControllerImpl.QsFragmentListener qsFragmentListener = this.f$0;
                    if (i4 - i2 != i8 - i6) {
                        QuickSettingsControllerImpl.this.onHeightChanged();
                    } else {
                        qsFragmentListener.getClass();
                    }
                }
            });
            QS qs2 = quickSettingsControllerImpl.mQs;
            quickSettingsControllerImpl.mLockscreenShadeTransitionController.qS = qs2;
            int i = QSComposeFragment.$r8$clinit;
            ViewGroup viewGroup = (ViewGroup) qs2.getHeader();
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = quickSettingsControllerImpl.mNotificationStackScrollLayoutController;
            notificationStackScrollLayoutController.getClass();
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            notificationStackScrollLayout.getClass();
            notificationStackScrollLayout.mQsHeader = viewGroup;
            quickSettingsControllerImpl.mQs.setScrollListener(quickSettingsControllerImpl.mQsScrollListener);
            quickSettingsControllerImpl.updateExpansion();
            SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = quickSettingsControllerImpl.mSecQuickSettingsControllerImpl;
            if (secQuickSettingsControllerImpl != null) {
                Object obj = secQuickSettingsControllerImpl.qsSupplier.get();
                View viewFindViewById = null;
                QSFragmentLegacy qSFragmentLegacy = obj instanceof QSFragmentLegacy ? (QSFragmentLegacy) obj : null;
                if (qSFragmentLegacy != null && (qSImpl2 = qSFragmentLegacy.mQsImpl) != null && (secQSImpl2 = qSImpl2.mSecQSImpl) != null && (secQSImplAnimatorManager = secQSImpl2.secQSImplAnimatorManager) != null) {
                    secQSImplAnimatorManager.setNotificationStackScrollerController(secQuickSettingsControllerImpl.notificationStackScrollLayoutController);
                }
                Object obj2 = secQuickSettingsControllerImpl.qsSupplier.get();
                if (obj2 instanceof QSFragmentLegacy) {
                }
                final SecQuickTileChunkLayoutBarTouchHelper secQuickTileChunkLayoutBarTouchHelper = secQuickSettingsControllerImpl.tileChunkLayoutBarTouchHelper;
                if (secQuickTileChunkLayoutBarTouchHelper != null) {
                    Object obj3 = secQuickTileChunkLayoutBarTouchHelper.qsSupplier.get();
                    QSFragmentLegacy qSFragmentLegacy2 = obj3 instanceof QSFragmentLegacy ? (QSFragmentLegacy) obj3 : null;
                    if (qSFragmentLegacy2 != null && (qSImpl = qSFragmentLegacy2.mQsImpl) != null && (secQSImpl = qSImpl.mSecQSImpl) != null && (barController = secQSImpl.barController) != null) {
                        BarItemImpl barInExpanded = barController.getBarInExpanded(BarType.TILE_CHUNK_LAYOUT);
                        TileChunkLayoutBar tileChunkLayoutBar = barInExpanded instanceof TileChunkLayoutBar ? (TileChunkLayoutBar) barInExpanded : null;
                        if (tileChunkLayoutBar != null) {
                            secQuickTileChunkLayoutBarTouchHelper.tileChunkLayoutBar = tileChunkLayoutBar;
                            secQuickTileChunkLayoutBarTouchHelper.expandHelper = new TileChunkLayoutBarExpandHelper(tileChunkLayoutBar);
                            TileChunkLayoutBar tileChunkLayoutBar2 = secQuickTileChunkLayoutBarTouchHelper.tileChunkLayoutBar;
                            if (tileChunkLayoutBar2 != null && (view5 = tileChunkLayoutBar2.mScrollIndicatorClickContainer) != null) {
                                view5.setAccessibilityDelegate(secQuickTileChunkLayoutBarTouchHelper.scrollIndicatorAccessibilityDelegate);
                                view5.setOnKeyListener(new View.OnKeyListener() { // from class: com.android.systemui.shade.SecQuickTileChunkLayoutBarTouchHelper$initForAccessibility$1$1
                                    @Override // android.view.View.OnKeyListener
                                    public final boolean onKey(View view6, int i2, KeyEvent keyEvent) {
                                        if (i2 != 66 || keyEvent.getAction() != 1) {
                                            return false;
                                        }
                                        TileChunkLayoutBarExpandHelper tileChunkLayoutBarExpandHelper = secQuickTileChunkLayoutBarTouchHelper.expandHelper;
                                        if (tileChunkLayoutBarExpandHelper != null) {
                                            tileChunkLayoutBarExpandHelper.forceToggleBar();
                                        }
                                        return true;
                                    }
                                });
                            }
                            TileChunkLayoutBar tileChunkLayoutBar3 = secQuickTileChunkLayoutBarTouchHelper.tileChunkLayoutBar;
                            if (tileChunkLayoutBar3 != null && (view4 = tileChunkLayoutBar3.mScrollIndicatorClickContainer) != null) {
                                view4.setClickable(true);
                                view4.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.shade.SecQuickTileChunkLayoutBarTouchHelper$initForClickListener$1$1
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view6) {
                                        TileChunkLayoutBarExpandHelper tileChunkLayoutBarExpandHelper = secQuickTileChunkLayoutBarTouchHelper.expandHelper;
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
                if (qs3 != null && (view3 = qs3.getView()) != null) {
                    NonInterceptingScrollView nonInterceptingScrollView = (NonInterceptingScrollView) view3.findViewById(R.id.expanded_qs_scroll_view);
                    if (nonInterceptingScrollView == null) {
                        nonInterceptingScrollView = null;
                    }
                    secQuickSettingsControllerImpl.qsScrollView = nonInterceptingScrollView;
                    QSContainerImpl qSContainerImpl = (QSContainerImpl) view3.findViewById(R.id.quick_settings_container);
                    if (qSContainerImpl == null) {
                        qSContainerImpl = null;
                    }
                    secQuickSettingsControllerImpl.qsContainerImpl = qSContainerImpl;
                }
                PanelScreenShotLogger.INSTANCE.addLogProvider("SecQuickSettingsControllerImpl", secQuickSettingsControllerImpl.logProvider);
                secQuickSettingsControllerImpl.naviBarGestureMode = ((NavigationModeController) secQuickSettingsControllerImpl.navigationModeController$delegate.getValue()).addListener(secQuickSettingsControllerImpl.modeChangedListener);
                SecPanelExpansionStateInteractor secPanelExpansionStateInteractor = secQuickSettingsControllerImpl.panelExpansionStateInteractor;
                if (secPanelExpansionStateInteractor != null) {
                    StatusBarStateController statusBarStateController = secPanelExpansionStateInteractor.statusBarStateController;
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("init in SecPanelExpansionStateInteractor state ", StatusBarState.toString(statusBarStateController.getState()), "SecPanelExpansionStateInteractor");
                    secPanelExpansionStateInteractor.shadeExpansionStateManager.addExpansionListener(secPanelExpansionStateInteractor.shadeExpansionListener);
                    SecPanelExpansionStateInteractor$stateListener$1 secPanelExpansionStateInteractor$stateListener$1 = secPanelExpansionStateInteractor.stateListener;
                    statusBarStateController.addCallback(secPanelExpansionStateInteractor$stateListener$1);
                    secPanelExpansionStateInteractor$stateListener$1.onStateChanged(statusBarStateController.getState());
                    secPanelExpansionStateInteractor.wakefulnessLifecycle.addObserver(secPanelExpansionStateInteractor.observer);
                }
                SecQSExpansionStateInteractor secQSExpansionStateInteractor = (SecQSExpansionStateInteractor) secQuickSettingsControllerImpl.qsExpansionStateInteractor$delegate.getValue();
                if (secQSExpansionStateInteractor != null && (secPanelSplitHelper = (SecPanelSplitHelper) secQSExpansionStateInteractor.splitHelper$delegate.getValue()) != null) {
                    secPanelSplitHelper.addListener(secQSExpansionStateInteractor.panelTransitionStateListener);
                }
                SecTabletHorizontalPanelPositionHelper tabletHorizontalPanelPositionHelper = secQuickSettingsControllerImpl.getTabletHorizontalPanelPositionHelper();
                QS qs4 = (QS) secQuickSettingsControllerImpl.qsSupplier.get();
                ((HeadsUpManagerImpl) ((HeadsUpManager) tabletHorizontalPanelPositionHelper.headsUpManager$delegate.getValue())).addListener(tabletHorizontalPanelPositionHelper.onHeadsUpChangedListener);
                if (qs4 == null || (view2 = qs4.getView()) == null) {
                    c09181RepeatWhenAttached = null;
                } else {
                    SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1 secTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1 = new SecTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1(tabletHorizontalPanelPositionHelper, null);
                    CoroutineContext coroutineContext = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
                    c09181RepeatWhenAttached = RepeatWhenAttachedKt.repeatWhenAttached(view2, EmptyCoroutineContext.INSTANCE, secTabletHorizontalPanelPositionHelper$onFragmentViewCreated$1);
                }
                tabletHorizontalPanelPositionHelper.handle = c09181RepeatWhenAttached;
                SecPanelSplitHelper secPanelSplitHelper2 = (SecPanelSplitHelper) secQuickSettingsControllerImpl.panelSplitHelper$delegate.getValue();
                if (secPanelSplitHelper2 != null) {
                    QS qs5 = (QS) secQuickSettingsControllerImpl.qsSupplier.get();
                    secPanelSplitHelper2.qs = qs5;
                    if (qs5 != null && (view = qs5.getView()) != null) {
                        viewFindViewById = view.findViewById(R.id.expanded_qs_scroll_view);
                    }
                    secPanelSplitHelper2.qsScrollView = viewFindViewById;
                    if (viewFindViewById != null && !secPanelSplitHelper2.getStatusBarStateController$2().isDozing()) {
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
                SecTabletHorizontalPanelPositionHelper tabletHorizontalPanelPositionHelper = secQuickSettingsControllerImpl.getTabletHorizontalPanelPositionHelper();
                ((HeadsUpManagerImpl) ((HeadsUpManager) tabletHorizontalPanelPositionHelper.headsUpManager$delegate.getValue())).removeListener(tabletHorizontalPanelPositionHelper.onHeadsUpChangedListener);
                RepeatWhenAttachedKt.C09181 c09181 = tabletHorizontalPanelPositionHelper.handle;
                if (c09181 != null) {
                    c09181.dispose();
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
                return this.f$0.mShadeExpandedHeight >= ((float) ((NotificationPanelViewController) lazy.get()).getMaxPanelHeight());
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
                this.f$0.mTrackingPointer = i18;
            }
        }, new IntSupplier() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda20
            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                return this.f$0.mTrackingPointer;
            }
        }, new QuickSettingsControllerImpl$$ExternalSyntheticLambda18(this, 1), secQSPanelComposeAdapter, secQsUiDisplayModeInteractor);
        this.mQuickQsOffsetHeight = SystemBarUtils.getQuickQsOffsetHeight(notificationPanelView.getContext());
        this.mPluginLockMediator = pluginLockMediator;
        PanelScreenShotLogger.INSTANCE.addLogProvider("QuickSettingsController", this);
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
    }

    public final void applyClippingImmediately(boolean z, int i, int i2, int i3, int i4) {
        int iLerp = this.mScrimCornerRadius;
        this.mLastClipBounds.set(i, i2, i3, i4);
        boolean z2 = this.mIsFullWidth;
        ScrimController scrimController = this.mScrimController;
        if (z2) {
            float f = (this.mRecordingController.isRecording() || ((CastControllerImpl) this.mCastController).getCastDevices().stream().anyMatch(new CastControllerImpl$$ExternalSyntheticLambda0())) ? 0.0f : this.mScreenCornerRadius;
            float f2 = this.mScrimCornerRadius;
            iLerp = (int) MathUtils.lerp(f, f2, Math.min(i2 / f2, 1.0f));
            float fCalculateBottomCornerRadius = !getExpanded() ? calculateBottomCornerRadius(0.0f) : 0.0f;
            ScrimView scrimView = scrimController.mNotificationsScrim;
            if (scrimView != null) {
                Drawable drawable = scrimView.mDrawable;
                if (drawable instanceof ScrimDrawable) {
                    ScrimDrawable scrimDrawable = (ScrimDrawable) drawable;
                    if (scrimDrawable.mBottomEdgeRadius != fCalculateBottomCornerRadius) {
                        scrimDrawable.mBottomEdgeRadius = fCalculateBottomCornerRadius;
                        scrimDrawable.invalidateSelf();
                    }
                }
            }
        }
        int i5 = iLerp;
        if (isQsFragmentCreated()) {
            boolean z3 = this.mPulseExpansionHandler.isExpanding;
            this.mTranslationForFullShadeTransition = (z3 || (this.mClippingAnimator != null && this.mIsPulseExpansionResettingAnimator)) ? (z3 || this.mIsPulseExpansionResettingAnimator) ? Math.max(0.0f, (i2 - getHeaderHeight()) / 2.0f) : (i2 - getHeaderHeight()) * 0.175f : 0.0f;
            int i6 = ((NotificationPanelViewController) this.mPanelViewControllerLazy.get()).mNavigationBarBottomHeight;
            int i7 = this.mAmbientState.mStackTopMargin;
            this.mQsFrameTranslateController.getClass();
            float translationY = this.mQsFrame.getTranslationY();
            int top = (int) ((i2 - translationY) - this.mQsFrame.getTop());
            int top2 = (int) ((i4 - translationY) - this.mQsFrame.getTop());
            this.mVisible = z;
            this.mQs.setQsVisible(z);
            if (this.mEnableClipping) {
                this.mQs.setFancyClipping(this.mDisplayLeftInset, top, this.mDisplayRightInset, top2, i5, z, this.mIsFullWidth);
            }
        }
        float f3 = i;
        float f4 = i2;
        float f5 = i3;
        float f6 = i4 + i5;
        ScrimView scrimView2 = scrimController.mNotificationsScrim;
        if (scrimView2.mDrawableBounds == null) {
            scrimView2.mDrawableBounds = new Rect();
        }
        int i8 = (int) f4;
        scrimView2.mDrawableBounds.set((int) f3, i8, (int) f5, (int) f6);
        scrimView2.mDrawable.setBounds(scrimView2.mDrawableBounds);
        if (scrimController.mNotificationsAlpha > 0.0f) {
            ((KeyguardRepositoryImpl) scrimController.mKeyguardInteractor.repository).topClippingBounds.setValue(Integer.valueOf(i8));
        } else {
            ((KeyguardRepositoryImpl) scrimController.mKeyguardInteractor.repository).topClippingBounds.setValue(null);
        }
        NotificationPanelViewController$$ExternalSyntheticLambda0 notificationPanelViewController$$ExternalSyntheticLambda0 = this.mApplyClippingImmediatelyListener;
        if (notificationPanelViewController$$ExternalSyntheticLambda0 != null) {
            boolean zIsQsFragmentCreated = isQsFragmentCreated();
            boolean z4 = this.mVisible;
            NotificationPanelViewController notificationPanelViewController = notificationPanelViewController$$ExternalSyntheticLambda0.f$0;
            if (zIsQsFragmentCreated) {
                ((KeyguardRepositoryImpl) notificationPanelViewController.mKeyguardInteractor.repository)._isQuickSettingsVisible.updateState(null, Boolean.valueOf(z4));
            }
            notificationPanelViewController.mKeyguardStatusBarViewController.updateTopClipping(i2);
        }
        ScrimView scrimView3 = scrimController.mScrimBehind;
        if (scrimView3 != null && scrimController.mNotificationsScrim != null) {
            scrimView3.setCornerRadius(i5);
            scrimController.mNotificationsScrim.setCornerRadius(i5);
        }
        int i9 = SceneContainerFlag.$r8$clinit;
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
        int i10 = SceneContainerFlag.$r8$clinit;
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
        float fMax = (height - Math.max(notificationStackScrollLayout.mMaxLayoutHeight - notificationStackScrollLayout.getContentHeight(), 0)) - notificationStackScrollLayoutController.mView.getTopPadding();
        if (notificationStackScrollLayoutController.getNotGoneChildCount() == 0) {
            NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
            notificationStackScrollLayout2.getClass();
            boolean z = NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW;
            if ((z || (emptyShadeView = notificationStackScrollLayout2.mEmptyShadeView) == null) ? false : emptyShadeView.mIsVisible) {
                NotificationStackScrollLayout notificationStackScrollLayout3 = notificationStackScrollLayoutController.mView;
                notificationStackScrollLayout3.getClass();
                fMax = z ? 0 : notificationStackScrollLayout3.mEmptyShadeView.getHeight();
            }
        }
        int iIntValue = this.mMaxExpansionHeight;
        ValueAnimator valueAnimator = this.mSizeChangeAnimator;
        if (valueAnimator != null) {
            iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        }
        if (this.mBarState != 1) {
            i = 0;
        }
        float fMax2 = Math.max(iIntValue, i) + fMax;
        NotificationStackScrollLayout notificationStackScrollLayout4 = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout4.getClass();
        float fMax3 = fMax2 + notificationStackScrollLayout4.mTopPaddingOverflow;
        if (fMax3 > notificationStackScrollLayoutController.mView.getHeight()) {
            notificationStackScrollLayoutController.mView.getClass();
            fMax3 = Math.max(r6.getLayoutMinHeightInternal() + iIntValue, notificationStackScrollLayoutController.mView.getHeight());
        }
        return (int) fMax3;
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
        IndentingPrintWriter indentingPrintWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        indentingPrintWriterAsIndenting.increaseIndent();
        indentingPrintWriterAsIndenting.print("mIsFullWidth=");
        indentingPrintWriterAsIndenting.println(this.mIsFullWidth);
        indentingPrintWriterAsIndenting.print("mTouchSlop=");
        indentingPrintWriterAsIndenting.println(this.mTouchSlop);
        indentingPrintWriterAsIndenting.print("mSlopMultiplier=");
        indentingPrintWriterAsIndenting.println(this.mSlopMultiplier);
        indentingPrintWriterAsIndenting.print("mBarState=");
        indentingPrintWriterAsIndenting.println(this.mBarState);
        indentingPrintWriterAsIndenting.print("mStatusBarMinHeight=");
        indentingPrintWriterAsIndenting.println(this.mStatusBarMinHeight);
        indentingPrintWriterAsIndenting.print("mScrimEnabled=");
        indentingPrintWriterAsIndenting.println(this.mScrimEnabled);
        indentingPrintWriterAsIndenting.print("mScrimCornerRadius=");
        indentingPrintWriterAsIndenting.println(this.mScrimCornerRadius);
        indentingPrintWriterAsIndenting.print("mScreenCornerRadius=");
        indentingPrintWriterAsIndenting.println(this.mScreenCornerRadius);
        indentingPrintWriterAsIndenting.print("mUseLargeScreenShadeHeader=");
        indentingPrintWriterAsIndenting.println(this.mUseLargeScreenShadeHeader);
        indentingPrintWriterAsIndenting.print("mLargeScreenShadeHeaderHeight=");
        indentingPrintWriterAsIndenting.println(0);
        indentingPrintWriterAsIndenting.print("mDisplayRightInset=");
        indentingPrintWriterAsIndenting.println(this.mDisplayRightInset);
        indentingPrintWriterAsIndenting.print("mDisplayLeftInset=");
        indentingPrintWriterAsIndenting.println(this.mDisplayLeftInset);
        indentingPrintWriterAsIndenting.print("mSplitShadeEnabled=");
        indentingPrintWriterAsIndenting.println(false);
        indentingPrintWriterAsIndenting.print("mLockscreenNotificationPadding=");
        indentingPrintWriterAsIndenting.println(this.mLockscreenNotificationPadding);
        indentingPrintWriterAsIndenting.print("mSplitShadeNotificationsScrimMarginBottom=");
        indentingPrintWriterAsIndenting.println(this.mSplitShadeNotificationsScrimMarginBottom);
        indentingPrintWriterAsIndenting.print("mDozing=");
        indentingPrintWriterAsIndenting.println(this.mDozing);
        indentingPrintWriterAsIndenting.print("mEnableClipping=");
        indentingPrintWriterAsIndenting.println(this.mEnableClipping);
        indentingPrintWriterAsIndenting.print("mFalsingThreshold=");
        indentingPrintWriterAsIndenting.println(this.mFalsingThreshold);
        indentingPrintWriterAsIndenting.print("mTransitionToFullShadePosition=");
        indentingPrintWriterAsIndenting.println(0);
        indentingPrintWriterAsIndenting.print("mCollapsedOnDown=");
        indentingPrintWriterAsIndenting.println(this.mCollapsedOnDown);
        indentingPrintWriterAsIndenting.print("mShadeExpandedHeight=");
        indentingPrintWriterAsIndenting.println(this.mShadeExpandedHeight);
        indentingPrintWriterAsIndenting.print("mLastShadeFlingWasExpanding=");
        indentingPrintWriterAsIndenting.println(this.mLastShadeFlingWasExpanding);
        indentingPrintWriterAsIndenting.print("mInitialHeightOnTouch=");
        indentingPrintWriterAsIndenting.println(this.mInitialHeightOnTouch);
        indentingPrintWriterAsIndenting.print("mInitialTouchX=");
        indentingPrintWriterAsIndenting.println(this.mInitialTouchX);
        indentingPrintWriterAsIndenting.print("mInitialTouchY=");
        indentingPrintWriterAsIndenting.println(this.mInitialTouchY);
        indentingPrintWriterAsIndenting.print("mTouchAboveFalsingThreshold=");
        indentingPrintWriterAsIndenting.println(this.mTouchAboveFalsingThreshold);
        indentingPrintWriterAsIndenting.print("mTracking=");
        indentingPrintWriterAsIndenting.println(isTracking());
        indentingPrintWriterAsIndenting.print("mTrackingPointer=");
        indentingPrintWriterAsIndenting.println(this.mTrackingPointer);
        indentingPrintWriterAsIndenting.print("mExpanded=");
        indentingPrintWriterAsIndenting.println(getExpanded());
        indentingPrintWriterAsIndenting.print("mFullyExpanded=");
        indentingPrintWriterAsIndenting.println(this.mFullyExpanded);
        indentingPrintWriterAsIndenting.print("isExpandImmediate()=");
        indentingPrintWriterAsIndenting.println(isExpandImmediate());
        indentingPrintWriterAsIndenting.print("mExpandedWhenExpandingStarted=");
        indentingPrintWriterAsIndenting.println(this.mExpandedWhenExpandingStarted);
        indentingPrintWriterAsIndenting.print("mAnimatingHiddenFromCollapsed=");
        indentingPrintWriterAsIndenting.println(this.mAnimatingHiddenFromCollapsed);
        indentingPrintWriterAsIndenting.print("mVisible=");
        indentingPrintWriterAsIndenting.println(this.mVisible);
        indentingPrintWriterAsIndenting.print("mExpansionHeight=");
        indentingPrintWriterAsIndenting.println(this.mExpansionHeight);
        indentingPrintWriterAsIndenting.print("mMinExpansionHeight=");
        indentingPrintWriterAsIndenting.println(this.mMinExpansionHeight);
        indentingPrintWriterAsIndenting.print("mMaxExpansionHeight=");
        indentingPrintWriterAsIndenting.println(this.mMaxExpansionHeight);
        indentingPrintWriterAsIndenting.print("mShadeExpandedFraction=");
        indentingPrintWriterAsIndenting.println(this.mShadeExpandedFraction);
        indentingPrintWriterAsIndenting.print("mLastOverscroll=");
        indentingPrintWriterAsIndenting.println(this.mLastOverscroll);
        indentingPrintWriterAsIndenting.print("mExpansionFromOverscroll=");
        indentingPrintWriterAsIndenting.println(this.mExpansionFromOverscroll);
        indentingPrintWriterAsIndenting.print("mExpansionEnabledPolicy=");
        indentingPrintWriterAsIndenting.println(this.mExpansionEnabledPolicy);
        indentingPrintWriterAsIndenting.print("mExpansionEnabledAmbient=");
        indentingPrintWriterAsIndenting.println(this.mExpansionEnabledAmbient);
        indentingPrintWriterAsIndenting.print("mQuickQsHeaderHeight=");
        indentingPrintWriterAsIndenting.println(this.mQuickQsHeaderHeight);
        indentingPrintWriterAsIndenting.print("mTwoFingerExpandPossible=");
        indentingPrintWriterAsIndenting.println(this.mTwoFingerExpandPossible);
        indentingPrintWriterAsIndenting.print("mConflictingExpansionGesture=");
        indentingPrintWriterAsIndenting.println(this.mConflictingExpansionGesture);
        indentingPrintWriterAsIndenting.print("mAnimatorExpand=");
        indentingPrintWriterAsIndenting.println(this.mAnimatorExpand);
        indentingPrintWriterAsIndenting.print("mCachedGestureInsets=");
        indentingPrintWriterAsIndenting.println(this.mCachedGestureInsets);
        indentingPrintWriterAsIndenting.print("mCachedWindowWidth=");
        indentingPrintWriterAsIndenting.println(this.mCachedWindowWidth);
        indentingPrintWriterAsIndenting.print("mTransitioningToFullShadeProgress=");
        indentingPrintWriterAsIndenting.println(0.0f);
        indentingPrintWriterAsIndenting.print("mDistanceForFullShadeTransition=");
        indentingPrintWriterAsIndenting.println(this.mDistanceForFullShadeTransition);
        indentingPrintWriterAsIndenting.print("mStackScrollerOverscrolling=");
        indentingPrintWriterAsIndenting.println(this.mStackScrollerOverscrolling);
        indentingPrintWriterAsIndenting.print("mAnimating=");
        indentingPrintWriterAsIndenting.println(this.mAnimating);
        indentingPrintWriterAsIndenting.print("mIsTranslationResettingAnimator=");
        indentingPrintWriterAsIndenting.println(false);
        indentingPrintWriterAsIndenting.print("mIsPulseExpansionResettingAnimator=");
        indentingPrintWriterAsIndenting.println(this.mIsPulseExpansionResettingAnimator);
        indentingPrintWriterAsIndenting.print("mTranslationForFullShadeTransition=");
        indentingPrintWriterAsIndenting.println(this.mTranslationForFullShadeTransition);
        indentingPrintWriterAsIndenting.print("mAnimateNextNotificationBounds=");
        indentingPrintWriterAsIndenting.println(this.mAnimateNextNotificationBounds);
        indentingPrintWriterAsIndenting.print("mNotificationBoundsAnimationDelay=");
        indentingPrintWriterAsIndenting.println(0L);
        indentingPrintWriterAsIndenting.print("mNotificationBoundsAnimationDuration=");
        indentingPrintWriterAsIndenting.println(this.mNotificationBoundsAnimationDuration);
        indentingPrintWriterAsIndenting.print("mInterceptRegion=");
        indentingPrintWriterAsIndenting.println(this.mInterceptRegion);
        indentingPrintWriterAsIndenting.print("mClippingAnimationEndBounds=");
        indentingPrintWriterAsIndenting.println(this.mClippingAnimationEndBounds);
        indentingPrintWriterAsIndenting.print("mLastClipBounds=");
        indentingPrintWriterAsIndenting.println(this.mLastClipBounds);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0174  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void flingQs(float f, int i, final QuickSettingsControllerImpl$$ExternalSyntheticLambda10 quickSettingsControllerImpl$$ExternalSyntheticLambda10, final boolean z) {
        int i2;
        float f2;
        float f3;
        float f4;
        boolean z2;
        float f5;
        boolean z3;
        boolean z4;
        ValueAnimator valueAnimator;
        SecQuickSettingsControllerImpl secQuickSettingsControllerImpl;
        boolean z5;
        boolean z6 = false;
        ShadeLogger shadeLogger = this.mShadeLog;
        shadeLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        Function1 function1 = new Function1() { // from class: com.android.systemui.shade.ShadeLogger$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                StringBuilder sbM = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("QS fling with type ", ((LogMessage) obj).getStr1(), ", originated from click: ");
                sbM.append(z);
                return sbM.toString();
            }
        };
        LogBuffer logBuffer = shadeLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("systemui.shade", logLevel, function1, null);
        String str = "UNKNOWN";
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = i != 0 ? i != 1 ? i != 2 ? "UNKNOWN" : "FLING_HIDE" : "FLING_COLLAPSE" : "FLING_EXPAND";
        logMessageImpl.bool1 = z;
        logBuffer.commit(logMessageObtain);
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
                    if (quickSettingsControllerImpl$$ExternalSyntheticLambda10 != null) {
                        quickSettingsControllerImpl$$ExternalSyntheticLambda10.run();
                    }
                    traceQsJank(false, i != 0);
                    return;
                }
                boolean z7 = i == 0;
                if ((f <= 0.0f || z7) && (f >= 0.0f || !z7)) {
                    f4 = f;
                    z2 = false;
                } else {
                    z2 = true;
                    f4 = 0.0f;
                }
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f3, f2);
                if (!z) {
                    f5 = 0.0f;
                    NotificationPanelViewController$$ExternalSyntheticLambda0 notificationPanelViewController$$ExternalSyntheticLambda0 = this.mFlingQsWithoutClickListener;
                    if (notificationPanelViewController$$ExternalSyntheticLambda0 != null) {
                        float f6 = this.mExpansionHeight;
                        FlingAnimationUtils flingAnimationUtils = notificationPanelViewController$$ExternalSyntheticLambda0.f$0.mFlingAnimationUtils;
                        flingAnimationUtils.getClass();
                        z3 = z7;
                        z4 = z2;
                        valueAnimator = valueAnimatorOfFloat;
                        flingAnimationUtils.apply(valueAnimator, f6, f2, f4, Math.abs(f2 - f6));
                    }
                    boolean z8 = z3;
                    if (z4) {
                        valueAnimator.setDuration(350L);
                    }
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda31
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                            quickSettingsControllerImpl.getClass();
                            quickSettingsControllerImpl.setExpansionHeight(((Float) valueAnimator2.getAnimatedValue()).floatValue());
                        }
                    });
                    valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl.3
                        public boolean mIsCanceled;

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationCancel(Animator animator) {
                            this.mIsCanceled = true;
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) throws Resources.NotFoundException {
                            QuickSettingsControllerImpl quickSettingsControllerImpl = QuickSettingsControllerImpl.this;
                            quickSettingsControllerImpl.mAnimatingHiddenFromCollapsed = false;
                            quickSettingsControllerImpl.mAnimating = false;
                            ((NotificationPanelViewController) quickSettingsControllerImpl.mPanelViewControllerLazy.get()).notifyExpandingFinished();
                            QuickSettingsControllerImpl quickSettingsControllerImpl2 = QuickSettingsControllerImpl.this;
                            quickSettingsControllerImpl2.mNotificationStackScrollLayoutController.mView.mCheckForLeavebehind = true;
                            quickSettingsControllerImpl2.mExpansionAnimator = null;
                            Runnable runnable = quickSettingsControllerImpl$$ExternalSyntheticLambda10;
                            if (runnable != null) {
                                runnable.run();
                            }
                            QuickSettingsControllerImpl.this.traceQsJank(false, this.mIsCanceled);
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                            ((NotificationPanelViewController) QuickSettingsControllerImpl.this.mPanelViewControllerLazy.get()).notifyExpandingStarted();
                        }
                    });
                    secQuickSettingsControllerImpl = this.mSecQuickSettingsControllerImpl;
                    if (secQuickSettingsControllerImpl == null) {
                        boolean z9 = this.mAnimating;
                        boolean z10 = this.mAnimatorExpand;
                        float f7 = this.mExpansionHeight;
                        StringBuilder sb = secQuickSettingsControllerImpl.logBuilder;
                        sb.setLength(0);
                        sb.append("flingQs: ");
                        sb.append("vel: ");
                        sb.append(f4);
                        sb.append(", isClick: ");
                        sb.append(z);
                        sb.append(", target: ");
                        sb.append(f2);
                        sb.append(", expansionHeight: ");
                        sb.append(f7);
                        sb.append(", oppositeDirection: ");
                        sb.append(z4);
                        sb.append(", animating( ");
                        sb.append(z9);
                        sb.append(" >> true )");
                        sb.append(", animatorExpand( ");
                        sb.append(z10);
                        sb.append(" >> ");
                        z5 = z8;
                        sb.append(z5);
                        sb.append(" )");
                        String string = sb.toString();
                        QuickPanelLoggerHelper.FlingLogger flingLogger = secQuickSettingsControllerImpl.quickPanelLogger.quickPanelLoggerHelper.flingLogger;
                        StringBuilder sb2 = flingLogger.logBuilder;
                        sb2.setLength(0);
                        if (i == 0) {
                            str = "EXPAND";
                        } else if (i == 1) {
                            str = "COLLAPSE";
                        } else if (i == 2) {
                            str = "HIDE";
                        }
                        MoveResult$$ExternalSyntheticOutline0.m(sb2, str, " | ", string, "\n");
                        sb2.append(Debug.getCallers(10, " - "));
                        flingLogger.externalLogger.log("[FLING]", sb2.toString());
                    } else {
                        z5 = z8;
                    }
                    this.mAnimating = true;
                    valueAnimator.start();
                    this.mExpansionAnimator = valueAnimator;
                    this.mAnimatorExpand = z5;
                    if (computeExpansionFraction() == f5 && f2 == f5) {
                        z6 = true;
                    }
                    this.mAnimatingHiddenFromCollapsed = z6;
                    return;
                }
                valueAnimatorOfFloat.setInterpolator(Interpolators.TOUCH_RESPONSE);
                f5 = 0.0f;
                valueAnimatorOfFloat.setDuration(368L);
                z3 = z7;
                z4 = z2;
                valueAnimator = valueAnimatorOfFloat;
                boolean z82 = z3;
                if (z4) {
                }
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda31
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
                        quickSettingsControllerImpl.getClass();
                        quickSettingsControllerImpl.setExpansionHeight(((Float) valueAnimator2.getAnimatedValue()).floatValue());
                    }
                });
                valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl.3
                    public boolean mIsCanceled;

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        this.mIsCanceled = true;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) throws Resources.NotFoundException {
                        QuickSettingsControllerImpl quickSettingsControllerImpl = QuickSettingsControllerImpl.this;
                        quickSettingsControllerImpl.mAnimatingHiddenFromCollapsed = false;
                        quickSettingsControllerImpl.mAnimating = false;
                        ((NotificationPanelViewController) quickSettingsControllerImpl.mPanelViewControllerLazy.get()).notifyExpandingFinished();
                        QuickSettingsControllerImpl quickSettingsControllerImpl2 = QuickSettingsControllerImpl.this;
                        quickSettingsControllerImpl2.mNotificationStackScrollLayoutController.mView.mCheckForLeavebehind = true;
                        quickSettingsControllerImpl2.mExpansionAnimator = null;
                        Runnable runnable = quickSettingsControllerImpl$$ExternalSyntheticLambda10;
                        if (runnable != null) {
                            runnable.run();
                        }
                        QuickSettingsControllerImpl.this.traceQsJank(false, this.mIsCanceled);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        ((NotificationPanelViewController) QuickSettingsControllerImpl.this.mPanelViewControllerLazy.get()).notifyExpandingStarted();
                    }
                });
                secQuickSettingsControllerImpl = this.mSecQuickSettingsControllerImpl;
                if (secQuickSettingsControllerImpl == null) {
                }
                this.mAnimating = true;
                valueAnimator.start();
                this.mExpansionAnimator = valueAnimator;
                this.mAnimatorExpand = z5;
                if (computeExpansionFraction() == f5) {
                    z6 = true;
                }
                this.mAnimatingHiddenFromCollapsed = z6;
                return;
            }
            setExpandImmediate(false);
            i2 = this.mMinExpansionHeight;
        }
        f2 = i2;
        f3 = this.mExpansionHeight;
        if (f2 != f3) {
        }
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
        int measuredHeight = this.mShadeHeaderController.header.getMeasuredHeight();
        int i = (NotiRune.NOTI_STYLE_POP_OVER_TOP_PADDING && ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) ? this.mNSSLTopPadding : 0;
        AmbientState ambientState = this.mAmbientState;
        return Math.max((measuredHeight + i) * ambientState.mExpansionFraction, ((ambientState.mStackTopMargin * ambientState.mExpansionFraction) + ambientState.getStackY()) - ambientState.mScrollY);
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
        float fCalculateAppearFraction = notificationStackScrollLayoutController.mView.calculateAppearFraction(f);
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
            fCalculateAppearFraction = MathUtils.smoothStep(0.0f, notificationStackScrollLayout.mIntrinsicPadding, f3);
            f2 = -this.mQs.getQsMinExpansionHeight();
        }
        return Math.min(0.0f, MathUtils.lerp(f2, 0.0f, Math.min(1.0f, fCalculateAppearFraction)));
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

    /* JADX WARN: Removed duplicated region for block: B:81:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01b3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean handleTouch(MotionEvent motionEvent, boolean z, boolean z2) throws Resources.NotFoundException {
        int i;
        int i2;
        boolean z3;
        VelocityTracker velocityTracker;
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
        boolean z4 = motionEvent.getY(motionEvent.getActionIndex()) < ((float) this.mStatusBarMinHeight);
        int i3 = ShadeExpandsOnStatusBarLongPress.$r8$clinit;
        int actionMasked = motionEvent.getActionMasked();
        boolean z5 = this.mShadeExpandedFraction == 1.0f && this.mBarState != 1 && !getExpanded() && isExpansionEnabled();
        ShadeLogger shadeLogger = this.mShadeLog;
        if (actionMasked == 0 && z5) {
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
            i = 5;
            i2 = 3;
        } else {
            if (quickPanelLogger != null) {
                quickPanelLogger.onTouchEvent(motionEvent);
            }
            int iFindPointerIndex = motionEvent.findPointerIndex(this.mTrackingPointer);
            if (iFindPointerIndex < 0) {
                this.mTrackingPointer = motionEvent.getPointerId(0);
                iFindPointerIndex = 0;
            }
            i = 5;
            float y = motionEvent.getY(iFindPointerIndex);
            float x = motionEvent.getX(iFindPointerIndex);
            float f = y - this.mInitialTouchY;
            int actionMasked2 = motionEvent.getActionMasked();
            if (actionMasked2 == 0) {
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
                trackMovement(motionEvent);
            } else if (actionMasked2 == 1) {
                shadeLogger.logMotionEvent(motionEvent, "onQsTouch: up/cancel action, QS tracking disabled");
                setTracking(false);
                this.mTrackingPointer = -1;
                trackMovement(motionEvent);
                if (computeExpansionFraction() != 0.0f || y >= this.mInitialTouchY) {
                    boolean z6 = motionEvent.getActionMasked() == 3;
                    float currentVelocity = getCurrentVelocity();
                    NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) lazy.get();
                    notificationPanelViewController.getClass();
                    boolean z7 = Math.abs(currentVelocity) >= notificationPanelViewController.mFlingAnimationUtils.mMinVelocityPxPerSecond ? currentVelocity > 0.0f : notificationPanelViewController.mQsController.computeExpansionFraction() > 0.5f;
                    FalsingManager falsingManager = this.mFalsingManager;
                    if (z7) {
                        if (falsingManager.isUnlockingDisabled()) {
                            z7 = false;
                        } else {
                            float currentVelocity2 = getCurrentVelocity();
                            int i4 = this.mBarState == 1 ? 193 : 194;
                            float displayDensity = ((NotificationPanelViewController) lazy.get()).getDisplayDensity();
                            this.mLockscreenGestureLogger.write(i4, (int) ((y - this.mInitialTouchY) / displayDensity), (int) (currentVelocity2 / displayDensity));
                        }
                    } else if (currentVelocity < 0.0f) {
                        falsingManager.isFalseTouch(12);
                    }
                    int i5 = (!z7 || z6) ? 1 : 0;
                    z3 = false;
                    velocityTracker = null;
                    flingQs(currentVelocity, i5, null, false);
                } else {
                    z3 = false;
                    traceQsJank(false, motionEvent.getActionMasked() == 3);
                    velocityTracker = null;
                }
                VelocityTracker velocityTracker3 = this.mQsVelocityTracker;
                if (velocityTracker3 != null) {
                    velocityTracker3.recycle();
                    this.mQsVelocityTracker = velocityTracker;
                }
                if (secQuickSettingsControllerImpl != null) {
                    secQuickSettingsControllerImpl.isBackGestureAllowed = z3;
                }
            } else if (actionMasked2 == 2) {
                if (secQuickSettingsControllerImpl != null) {
                    int i6 = this.mTouchSlop;
                    if (secQuickSettingsControllerImpl.isBackGestureAllowed) {
                        int i7 = i6 * 5;
                        if (f > 0.0f) {
                            f -= i7;
                            if (0.0f >= f) {
                                f = 0.0f;
                            }
                        } else {
                            f += i7;
                            if (0.0f <= f) {
                            }
                        }
                    }
                }
                setExpansionHeight(this.mInitialHeightOnTouch + f);
                if (f >= ((NotificationPanelViewController) lazy.get()).getFalsingThreshold()) {
                    this.mTouchAboveFalsingThreshold = true;
                }
                trackMovement(motionEvent);
            } else if (actionMasked2 != 3) {
                if (actionMasked2 == 6 && this.mTrackingPointer == (pointerId = motionEvent.getPointerId(motionEvent.getActionIndex()))) {
                    int i8 = motionEvent.getPointerId(0) != pointerId ? 0 : 1;
                    float y2 = motionEvent.getY(i8);
                    float x2 = motionEvent.getX(i8);
                    this.mTrackingPointer = motionEvent.getPointerId(i8);
                    this.mInitialHeightOnTouch = this.mExpansionHeight;
                    this.mInitialTouchY = y2;
                    this.mInitialTouchX = x2;
                }
            }
            if (!this.mConflictingExpansionGesture) {
                if (quickPanelLogger != null) {
                    quickPanelLogger.handleTouch(motionEvent, "!mConflictingExpansionGesture && !mSplitShadeEnabled", true);
                }
                return true;
            }
            i2 = 3;
        }
        if (actionMasked == i2 || actionMasked == 1) {
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
        if (this.mTwoFingerExpandPossible && isOpenQsEvent(motionEvent) && z4 && this.mBarState != 1) {
            this.mMetricsLogger.count("panel_open_qs", 1);
            setExpandImmediate(true);
            NotificationPanelViewController$$ExternalSyntheticLambda0 notificationPanelViewController$$ExternalSyntheticLambda0 = this.mExpansionHeightSetToMaxListener;
            if (notificationPanelViewController$$ExternalSyntheticLambda0 != null) {
                notificationPanelViewController$$ExternalSyntheticLambda0.onExpansionHeightSetToMax(false);
            }
            QS qs = this.mQs;
            if (qs != null) {
                qs.setListening(true);
            }
            if (!SecPanelSplitHelper.isEnabled()) {
                if (!(motionEvent.getActionMasked() == i && motionEvent.getPointerCount() == 2)) {
                    StateFlowImpl stateFlowImpl = ((SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class)).repository._openQuickPanelFrom1DepthStatusBarInShade;
                    LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl.getValue(), 1L, stateFlowImpl, null);
                    return false;
                }
                SecPanelSAStatusLogRepository secPanelSAStatusLogRepository = ((SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class)).repository;
                StateFlowImpl stateFlowImpl2 = secPanelSAStatusLogRepository._openQuickPanelFrom1DepthStatusBarInShade;
                LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl2.getValue(), -1L, stateFlowImpl2, null);
                StateFlowImpl stateFlowImpl3 = secPanelSAStatusLogRepository._openQuickPanelFrom2Depth2Finger;
                LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl3.getValue(), 1L, stateFlowImpl3, null);
                return false;
            }
        } else if (!SecPanelSplitHelper.isEnabled() && motionEvent.getActionMasked() == 0 && motionEvent.getY(motionEvent.getActionIndex()) < this.mStatusBarMinHeight && this.mBarState != 1) {
            StateFlowImpl stateFlowImpl4 = ((SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class)).repository._openQuickPanelFrom1DepthStatusBarInShade;
            LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl4.getValue(), 1L, stateFlowImpl4, null);
            return false;
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

    /* JADX WARN: Removed duplicated region for block: B:59:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0215  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0217  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onIntercept(MotionEvent motionEvent) throws Resources.NotFoundException {
        int i;
        int i2;
        EdgeBackGestureHandler edgeBackGestureHandler;
        boolean zIsBackGestureAllowed;
        int pointerId;
        QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
        if (quickPanelLogger != null) {
            quickPanelLogger.onInterceptTouchEvent(motionEvent);
        }
        int iFindPointerIndex = motionEvent.findPointerIndex(this.mTrackingPointer);
        if (iFindPointerIndex < 0) {
            this.mTrackingPointer = motionEvent.getPointerId(0);
            iFindPointerIndex = 0;
        }
        float x = motionEvent.getX(iFindPointerIndex);
        float y = motionEvent.getY(iFindPointerIndex);
        int actionMasked = motionEvent.getActionMasked();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = this.mSecQuickSettingsControllerImpl;
        ShadeLogger shadeLogger = this.mShadeLog;
        StringBuilder sb = this.mQuickPanelLogBuilder;
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                trackMovement(motionEvent);
                shadeLogger.logMotionEvent(motionEvent, "onQsIntercept: up action, QS tracking disabled");
                setTracking(false);
                if (secQuickSettingsControllerImpl != null) {
                    secQuickSettingsControllerImpl.updateScrollableDirection(true);
                    return false;
                }
            } else if (actionMasked == 2) {
                float f = y - this.mInitialTouchY;
                trackMovement(motionEvent);
                if (isTracking()) {
                    setExpansionHeight(f + this.mInitialHeightOnTouch);
                    trackMovement(motionEvent);
                    if (quickPanelLogger == null) {
                        return true;
                    }
                    quickPanelLogger.onInterceptTouchEvent(motionEvent, "isTracking()", true);
                    return true;
                }
                float f2 = motionEvent.getClassification() == 1 ? this.mTouchSlop * this.mSlopMultiplier : this.mTouchSlop;
                if (secQuickSettingsControllerImpl == null || !secQuickSettingsControllerImpl.checkIfScrollEnabled(f, f2)) {
                    Lazy lazy = this.mPanelViewControllerLazy;
                    if ((f <= f2 && (f >= (-f2) || !getExpanded())) || Math.abs(f) <= Math.abs(x - this.mInitialTouchX) || !shouldQuickSettingsIntercept(this.mInitialTouchX, this.mInitialTouchY, f)) {
                        float f3 = this.mInitialTouchY;
                        boolean expanded = getExpanded();
                        boolean zIsKeyguardShowing$1 = ((NotificationPanelViewController) lazy.get()).isKeyguardShowing$1();
                        boolean zIsExpansionEnabled = isExpansionEnabled();
                        long downTime = motionEvent.getDownTime();
                        shadeLogger.getClass();
                        LogLevel logLevel = LogLevel.VERBOSE;
                        ShadeLogger$$ExternalSyntheticLambda0 shadeLogger$$ExternalSyntheticLambda0 = new ShadeLogger$$ExternalSyntheticLambda0(16);
                        LogBuffer logBuffer = shadeLogger.buffer;
                        LogMessage logMessageObtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$$ExternalSyntheticLambda0, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                        logMessageImpl.int1 = (int) f3;
                        logMessageImpl.int2 = (int) y;
                        logMessageImpl.long1 = (long) f;
                        logMessageImpl.double1 = f2;
                        logMessageImpl.bool1 = expanded;
                        logMessageImpl.bool2 = zIsKeyguardShowing$1;
                        logMessageImpl.bool3 = zIsExpansionEnabled;
                        logMessageImpl.str1 = String.valueOf(downTime);
                        logBuffer.commit(logMessageObtain);
                        return false;
                    }
                    shadeLogger.getClass();
                    LogLevel logLevel2 = LogLevel.VERBOSE;
                    ShadeLogger$$ExternalSyntheticLambda0 shadeLogger$$ExternalSyntheticLambda02 = new ShadeLogger$$ExternalSyntheticLambda0(4);
                    LogBuffer logBuffer2 = shadeLogger.buffer;
                    LogMessage logMessageObtain2 = logBuffer2.obtain("systemui.shade", logLevel2, shadeLogger$$ExternalSyntheticLambda02, null);
                    ((LogMessageImpl) logMessageObtain2).double1 = f;
                    logBuffer2.commit(logMessageObtain2);
                    setTracking(true);
                    traceQsJank(true, false);
                    onExpansionStarted$1();
                    ((NotificationPanelViewController) lazy.get()).notifyExpandingFinished();
                    this.mInitialHeightOnTouch = this.mExpansionHeight;
                    this.mInitialTouchY = y;
                    this.mInitialTouchX = x;
                    notificationStackScrollLayoutController.mView.cancelLongPress();
                    if (quickPanelLogger != null && sb != null) {
                        sb.setLength(0);
                        sb.append("h: ");
                        sb.append(f);
                        sb.append(" > touchSlop: ");
                        quickPanelLogger.onInterceptTouchEvent(motionEvent, DpCornerSize$$ExternalSyntheticOutline0.m(f2, " || (h < -touchSlop && getExpanded())", sb), true);
                        return true;
                    }
                } else if (quickPanelLogger != null) {
                    quickPanelLogger.onInterceptTouchEvent(motionEvent, "checkIfScrollEnabled()", false);
                    return false;
                }
            } else if (actionMasked != 3) {
                if (actionMasked == 6 && this.mTrackingPointer == (pointerId = motionEvent.getPointerId(motionEvent.getActionIndex()))) {
                    int i3 = motionEvent.getPointerId(0) != pointerId ? 0 : 1;
                    this.mTrackingPointer = motionEvent.getPointerId(i3);
                    this.mInitialTouchX = motionEvent.getX(i3);
                    this.mInitialTouchY = motionEvent.getY(i3);
                    return false;
                }
            }
            return false;
        }
        this.mInitialTouchY = y;
        this.mInitialTouchX = x;
        if (secQuickSettingsControllerImpl != null) {
            secQuickSettingsControllerImpl.updateScrollableDirection(false);
        }
        VelocityTracker velocityTracker = this.mQsVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
        }
        this.mQsVelocityTracker = VelocityTracker.obtain();
        trackMovement(motionEvent);
        float fComputeExpansionFraction = computeExpansionFraction();
        if (secQuickSettingsControllerImpl != null && secQuickSettingsControllerImpl.naviBarGestureMode == 2 && motionEvent.getAction() == 0 && ((Boolean) ((ShadeRepositoryImpl) ((ShadeRepository) secQuickSettingsControllerImpl.shadeRepository$delegate.getValue())).legacyExpandedOrAwaitingInputTransfer.$$delegate_0.getValue()).booleanValue() && !secQuickSettingsControllerImpl.qsExpandedSupplier.getAsBoolean()) {
            NavigationBarControllerImpl navigationBarControllerImpl = (NavigationBarControllerImpl) ((NavigationBarController) secQuickSettingsControllerImpl.navigationBarController$delegate.getValue());
            navigationBarControllerImpl.getClass();
            if (BasicRune.NAVBAR_TASKBAR) {
                i = 0;
                if (((NavBarStateManagerImpl) navigationBarControllerImpl.mNavBarStateManager).isTaskBarEnabled(false)) {
                    i2 = 1;
                }
                navigationBarControllerImpl.mDisplayTracker.getClass();
                NavigationBarView navigationBarView = navigationBarControllerImpl.getNavigationBarView(i);
                if (i2 == 0) {
                    EdgeBackGestureHandler edgeBackGestureHandler2 = navigationBarControllerImpl.mTaskbarDelegate.mEdgeBackGestureHandler;
                    zIsBackGestureAllowed = edgeBackGestureHandler2 != null && edgeBackGestureHandler2.isBackGestureAllowed(motionEvent);
                    secQuickSettingsControllerImpl.isBackGestureAllowed = zIsBackGestureAllowed;
                } else {
                    if (navigationBarView != null && (edgeBackGestureHandler = navigationBarView.mEdgeBackGestureHandler) != null) {
                        zIsBackGestureAllowed = edgeBackGestureHandler.isBackGestureAllowed(motionEvent);
                    }
                    secQuickSettingsControllerImpl.isBackGestureAllowed = zIsBackGestureAllowed;
                }
            } else {
                i = 0;
            }
            i2 = i;
            navigationBarControllerImpl.mDisplayTracker.getClass();
            NavigationBarView navigationBarView2 = navigationBarControllerImpl.getNavigationBarView(i);
            if (i2 == 0) {
            }
        }
        double d = fComputeExpansionFraction;
        if (d <= 0.0d || d >= 1.0d || notificationStackScrollLayoutController.mView.mStateAnimator.mTopOverScrollAnimator != null) {
            if (this.mExpansionAnimator == null) {
                return false;
            }
            this.mInitialHeightOnTouch = this.mExpansionHeight;
            shadeLogger.logMotionEvent(motionEvent, "onQsIntercept: down action, QS tracking enabled");
            setTracking(true);
            traceQsJank(true, false);
            notificationStackScrollLayoutController.mView.cancelLongPress();
            return false;
        }
        shadeLogger.logMotionEvent(motionEvent, "onQsIntercept: down action, QS partially expanded/collapsed");
        if (quickPanelLogger != null && sb != null) {
            sb.setLength(0);
            sb.append("!mSplitShadeEnabled && (0.0 < qsExpansionFraction: ");
            sb.append(fComputeExpansionFraction);
            sb.append(" < 1.0");
            quickPanelLogger.onInterceptTouchEvent(motionEvent, sb.toString(), true);
            return true;
        }
        return true;
    }

    public final void setClippingBounds() {
        int left;
        int right;
        int i;
        QuickSettingsControllerImpl quickSettingsControllerImpl;
        float fComputeExpansionFraction = computeExpansionFraction();
        int iCalculateBottomPosition = calculateBottomPosition(fComputeExpansionFraction);
        boolean z = ((fComputeExpansionFraction > 0.0f ? 1 : (fComputeExpansionFraction == 0.0f ? 0 : -1)) == 0 && iCalculateBottomPosition > 0) || ((fComputeExpansionFraction > 0.0f ? 1 : (fComputeExpansionFraction == 0.0f ? 0 : -1)) > 0);
        int iCalculateTopClippingBound = calculateTopClippingBound(iCalculateBottomPosition);
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
        int iMin = Math.min(iCalculateTopClippingBound, bottom);
        if (!this.mAnimateNextNotificationBounds || this.mLastClipBounds.isEmpty()) {
            quickSettingsControllerImpl = this;
            if (quickSettingsControllerImpl.mClippingAnimator != null) {
                quickSettingsControllerImpl.mClippingAnimationEndBounds.set(left, iMin, i4, bottom);
            } else {
                quickSettingsControllerImpl.applyClippingImmediately(z, left, iMin, i4, bottom);
            }
        } else {
            this.mClippingAnimationEndBounds.set(left, iMin, i4, bottom);
            Rect rect = this.mLastClipBounds;
            final int i5 = rect.left;
            final int i6 = rect.top;
            final int i7 = rect.right;
            final int i8 = rect.bottom;
            ValueAnimator valueAnimator = this.mClippingAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mClippingAnimator = valueAnimatorOfFloat;
            valueAnimatorOfFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            this.mClippingAnimator.setDuration(this.mNotificationBoundsAnimationDuration);
            this.mClippingAnimator.setStartDelay(0L);
            final boolean z3 = z;
            quickSettingsControllerImpl = this;
            this.mClippingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda30
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    QuickSettingsControllerImpl quickSettingsControllerImpl2 = this.f$0;
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
            LogMessage logMessageObtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).bool1 = z;
            logBuffer.commit(logMessageObtain);
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
            LogMessage logMessageObtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.str1 = "QS Expansion Changed.";
            logMessageImpl.bool1 = z;
            logMessageImpl.int1 = i;
            logMessageImpl.int2 = i2;
            logMessageImpl.bool2 = z2;
            logMessageImpl.bool3 = z3;
            logMessageImpl.long1 = Boolean.compare(z4, false);
            logBuffer.commit(logMessageObtain);
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

    /* JADX WARN: Removed duplicated region for block: B:18:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00ac  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setExpansionHeight(float f) {
        Boolean bool;
        SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = this.mSecQuickSettingsControllerImpl;
        if (secQuickSettingsControllerImpl != null) {
            double asDouble = secQuickSettingsControllerImpl.maxExpansionHeightSupplier.getAsDouble();
            double asDouble2 = secQuickSettingsControllerImpl.minExpansionHeightSupplier.getAsDouble();
            if (asDouble2 > asDouble) {
                updateMinHeight();
                asDouble = secQuickSettingsControllerImpl.maxExpansionHeightSupplier.getAsDouble();
                asDouble2 = secQuickSettingsControllerImpl.minExpansionHeightSupplier.getAsDouble();
            }
            double dMin = Math.min(Math.max(f, secQuickSettingsControllerImpl.minExpansionHeightSupplier.getAsDouble()), asDouble);
            SecPanelSplitHelper.Companion.getClass();
            if (SecPanelSplitHelper.isEnabled) {
                int i = this.mBarState;
                if (i != 0) {
                    if (i == 1) {
                        dMin = asDouble2;
                    }
                    this.mFullyExpanded = (dMin != asDouble || asDouble == 0.0d || asDouble2 == asDouble) ? false : true;
                    boolean z = this.mAnimatorExpand && this.mAnimating;
                    boolean asBoolean = secQuickSettingsControllerImpl.qsExpandedSupplier.getAsBoolean();
                    bool = !isExpandImmediate() ? Boolean.TRUE : (dMin <= asDouble2 || asBoolean || this.mStackScrollerOverscrolling || this.mDozing || z) ? (dMin > asDouble2 || !asBoolean || asDouble2 == asDouble) ? null : Boolean.FALSE : Boolean.TRUE;
                    if (bool != null) {
                        setExpanded(bool.booleanValue());
                    }
                    this.mExpansionHeight = (float) dMin;
                } else {
                    if (((NotificationPanelViewController) this.mPanelViewControllerLazy.get()).isPanelExpanded()) {
                        dMin = asDouble;
                    }
                    if (dMin != asDouble) {
                        this.mFullyExpanded = (dMin != asDouble || asDouble == 0.0d || asDouble2 == asDouble) ? false : true;
                        if (this.mAnimatorExpand) {
                            boolean asBoolean2 = secQuickSettingsControllerImpl.qsExpandedSupplier.getAsBoolean();
                            if (!isExpandImmediate()) {
                            }
                            if (bool != null) {
                            }
                            this.mExpansionHeight = (float) dMin;
                        }
                    }
                }
            } else {
                if (this.mBarState == 1) {
                }
            }
        } else {
            if (this.mExpansionHeight == f) {
                return;
            }
            int i2 = this.mMaxExpansionHeight;
            float f2 = i2;
            float fMin = Math.min(Math.max(f, this.mMinExpansionHeight), f2);
            this.mFullyExpanded = fMin == f2 && i2 != 0;
            boolean z2 = !this.mAnimatorExpand && this.mAnimating;
            if (fMin > this.mMinExpansionHeight && !getExpanded() && !this.mStackScrollerOverscrolling && !this.mDozing && !z2) {
                setExpanded(true);
            } else if (fMin <= this.mMinExpansionHeight && getExpanded()) {
                setExpanded(false);
            }
            this.mExpansionHeight = fMin;
        }
        updateExpansion();
        boolean expanded = getExpanded();
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (expanded && this.mFullyExpanded) {
            keyguardUpdateMonitor.dispatchStatusBarState(true);
        } else if (this.mBarState != 2) {
            keyguardUpdateMonitor.dispatchStatusBarState(false);
        }
        NotificationPanelViewController$$ExternalSyntheticLambda0 notificationPanelViewController$$ExternalSyntheticLambda0 = this.mExpansionHeightListener;
        if (notificationPanelViewController$$ExternalSyntheticLambda0 != null) {
            notificationPanelViewController$$ExternalSyntheticLambda0.f$0.onQsSetExpansionHeightCalled(this.mFullyExpanded);
        }
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

    /* JADX WARN: Removed duplicated region for block: B:19:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0044  */
    @Override // com.android.systemui.shade.QuickSettingsController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean shouldQuickSettingsIntercept(float f, float f2, float f3) {
        int bottom;
        int top;
        boolean z;
        QS qs;
        boolean z2 = this.mBarState == 1;
        if (isExpansionEnabled() && !this.mCollapsedOnDown && (!z2 || !this.mKeyguardBypassController.getBypassEnabled())) {
            SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = this.mSecQuickSettingsControllerImpl;
            if (secQuickSettingsControllerImpl != null) {
                SecPanelSplitHelper.Companion.getClass();
                if (!SecPanelSplitHelper.isEnabled || z2) {
                    if (secQuickSettingsControllerImpl == null) {
                        if (z2 || (qs = this.mQs) == null) {
                            KeyguardStatusBarView keyguardStatusBarView = this.mKeyguardStatusBar;
                            int top2 = keyguardStatusBarView.getTop();
                            bottom = keyguardStatusBarView.getBottom();
                            top = top2;
                        } else {
                            int i = QSComposeFragment.$r8$clinit;
                            top = qs.getHeader().getTop();
                            bottom = this.mQs.getHeader().getBottom();
                        }
                        this.mInterceptRegion.set((int) this.mQsFrame.getX(), top, this.mQsFrame.getWidth() + ((int) this.mQsFrame.getX()), bottom + ((z2 || this.mQs == null) ? 0 : this.mQsFrame.getTop()));
                        this.mShadeTouchableRegionManager.updateRegionForNotch(this.mInterceptRegion);
                        boolean zContains = this.mInterceptRegion.contains((int) f, (int) f2);
                        if (secQuickSettingsControllerImpl == null || !z2) {
                            if (!getExpanded()) {
                                return zContains;
                            }
                            if (!zContains) {
                                float f4 = 0.0f;
                                if (f3 < 0.0f) {
                                    if (!((NotificationPanelViewController) this.mPanelViewControllerLazy.get()).mIsGestureNavigation || f2 <= r15.mView.getHeight() - r15.mNavigationBarBottomHeight) {
                                        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
                                        notificationStackScrollLayoutController.getClass();
                                        int i2 = SceneContainerFlag.$r8$clinit;
                                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                                        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                                        notificationStackScrollLayout.getClass();
                                        int childCount = notificationStackScrollLayout.getChildCount();
                                        for (int i3 = 0; i3 < childCount; i3++) {
                                            ExpandableView expandableView = (ExpandableView) notificationStackScrollLayout.getChildAt(i3);
                                            if (expandableView.getVisibility() != 8) {
                                                float translationY = (expandableView.getTranslationY() + expandableView.mActualHeight) - expandableView.mClipBottomAmount;
                                                if (translationY > f4) {
                                                    f4 = translationY;
                                                }
                                            }
                                        }
                                        z = (f2 <= f4 + notificationStackScrollLayout.mAmbientState.mStackTranslation || f2 <= this.mQs.getView().getY() + this.mQs.getView().getHeight()) && secQuickSettingsControllerImpl != null && secQuickSettingsControllerImpl.isInTouchQsArea(f);
                                        if (!z) {
                                        }
                                    }
                                    if (!z) {
                                    }
                                }
                            }
                            return true;
                        }
                    } else {
                        SecPanelTouchBlockHelper secPanelTouchBlockHelper = (SecPanelTouchBlockHelper) secQuickSettingsControllerImpl.panelTouchBlockHelper$delegate.getValue();
                        if (!(secPanelTouchBlockHelper != null ? secPanelTouchBlockHelper.isKeyguardPanelDisabled() : false)) {
                        }
                    }
                }
            }
        }
        return false;
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
        boolean zIsExpandImmediate = isExpandImmediate();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        if (!zIsExpandImmediate && !getExpanded()) {
            notificationStackScrollLayoutController.getClass();
            int i = SceneContainerFlag.$r8$clinit;
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            notificationStackScrollLayout.getClass();
            notificationStackScrollLayout.mStackScrollAlgorithm.getClass();
        }
        float fComputeExpansionFraction = computeExpansionFraction();
        this.mQs.setQsExpansion(computeExpansionFraction(), this.mShadeExpandedFraction, getHeaderTranslation(), 1.0f);
        if (QuickStepContract.ALLOW_BACK_GESTURE_IN_SHADE) {
            ((NotificationPanelViewController) this.mPanelViewControllerLazy.get()).getClass();
        }
        MediaHierarchyManager mediaHierarchyManager = this.mMediaHierarchyManager;
        boolean z = false;
        if (mediaHierarchyManager.qsExpansion != fComputeExpansionFraction) {
            mediaHierarchyManager.qsExpansion = fComputeExpansionFraction;
            MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, false, 3);
            if (mediaHierarchyManager.getQSTransformationProgress() >= 0.0f) {
                mediaHierarchyManager.updateTargetState();
                mediaHierarchyManager.applyTargetStateIfNotAnimating();
            }
        }
        int iCalculateBottomPosition = calculateBottomPosition(fComputeExpansionFraction);
        ScrimController scrimController = this.mScrimController;
        scrimController.getClass();
        if (!Float.isNaN(fComputeExpansionFraction)) {
            float notificationScrimAlpha = ShadeInterpolation.getNotificationScrimAlpha(fComputeExpansionFraction);
            boolean z2 = iCalculateBottomPosition > 0;
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
            if (isExpandImmediate()) {
                notificationStackScrollLayoutController.setQsExpansionFraction(1.0f);
            } else {
                notificationStackScrollLayoutController.setQsExpansionFraction(fComputeExpansionFraction);
            }
        } else {
            secQuickSettingsControllerImpl.notificationStackScrollLayoutController.setQsExpansionFraction(0.0f);
        }
        NotificationShadeDepthController notificationShadeDepthController = this.mDepthController;
        notificationShadeDepthController.getClass();
        if (Float.isNaN(fComputeExpansionFraction)) {
            Log.w("DepthController", "Invalid qs expansion");
        } else if (notificationShadeDepthController.qsPanelExpansion != fComputeExpansionFraction) {
            notificationShadeDepthController.qsPanelExpansion = fComputeExpansionFraction;
            notificationShadeDepthController.scheduleUpdate();
        }
        this.mStatusBarKeyguardViewManager.setQsExpansion(fComputeExpansionFraction);
        ShadeRepositoryImpl shadeRepositoryImpl = (ShadeRepositoryImpl) this.mShadeRepository;
        shadeRepositoryImpl._qsExpansion.updateState(null, Float.valueOf(fComputeExpansionFraction));
        float fComputeExpansionFraction2 = this.mBarState == 1 ? computeExpansionFraction() : this.mShadeExpandedFraction;
        ShadeHeaderController shadeHeaderController = this.mShadeHeaderController;
        if (shadeHeaderController.qsVisible && shadeHeaderController.shadeExpandedFraction != fComputeExpansionFraction2) {
            shadeHeaderController.shadeExpandedFraction = fComputeExpansionFraction2;
            shadeHeaderController.updateIgnoredSlots();
        }
        if (shadeHeaderController.visible && shadeHeaderController.qsExpandedFraction != fComputeExpansionFraction) {
            shadeHeaderController.qsExpandedFraction = fComputeExpansionFraction;
            shadeHeaderController.iconContainer.mQsExpansionTransitioning = fComputeExpansionFraction > 0.0f && fComputeExpansionFraction < 1.0f;
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
        Math.max(notificationStackScrollLayout.mMaxLayoutHeight - notificationStackScrollLayout.getContentHeight(), 0);
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
        boolean zShouldUseLargeScreenShadeHeader = LargeScreenUtils.shouldUseLargeScreenShadeHeader(notificationPanelView.getResources());
        this.mUseLargeScreenShadeHeader = zShouldUseLargeScreenShadeHeader;
        int dimensionPixelSize = zShouldUseLargeScreenShadeHeader ? 0 : this.mResources.getDimensionPixelSize(R.dimen.notification_panel_margin_top);
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
