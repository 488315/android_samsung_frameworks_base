package com.android.systemui.shade;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.PendingIntent;
import android.app.SemWallpaperColors;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.metrics.LogMaker;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.Process;
import android.os.Trace;
import android.os.UserManager;
import android.os.VibrationEffect;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.MathUtils;
import android.util.Property;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewRootImpl;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.app.animation.InterpolatorsAndroidX;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.internal.policy.SystemBarUtils;
import com.android.internal.util.LatencyTracker;
import com.android.keyguard.ActiveUnlockConfig;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.FaceAuthUiEvent;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitch;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardStatusView;
import com.android.keyguard.KeyguardStatusViewController;
import com.android.keyguard.KeyguardUnfoldTransition;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.LockIconView;
import com.android.keyguard.SecLockIconViewController;
import com.android.keyguard.biometrics.KeyguardFingerprintGuidePopup;
import com.android.keyguard.dagger.KeyguardQsUserSwitchComponent;
import com.android.keyguard.dagger.KeyguardStatusBarViewComponent;
import com.android.keyguard.dagger.KeyguardStatusViewComponent;
import com.android.keyguard.dagger.KeyguardUserSwitcherComponent;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.keyguard.punchhole.KeyguardPunchHoleVIView;
import com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController;
import com.android.keyguard.punchhole.VIDirector$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.animation.FlingAnimationUtils;
import com.android.systemui.AbstractC0950x8906c950;
import com.android.systemui.BasicRune;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.common.shared.model.Position;
import com.android.systemui.doze.AODOverlayContainer;
import com.android.systemui.doze.DozeLog;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.dump.DumpsysTableLogger;
import com.android.systemui.facewidget.FaceWidgetNotificationController;
import com.android.systemui.facewidget.KeyguardStatusCallback;
import com.android.systemui.facewidget.plugin.FaceWidgetContainerWrapper;
import com.android.systemui.facewidget.plugin.FaceWidgetKeyguardStatusCallbackWrapper;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.facewidget.plugin.FaceWidgetPositionAlgorithmWrapper;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.animator.KeyguardEditModeAnimatorController;
import com.android.systemui.keyguard.animator.KeyguardTouchAnimator;
import com.android.systemui.keyguard.animator.KeyguardTouchSwipeCallback;
import com.android.systemui.keyguard.animator.KeyguardTouchViewInjector;
import com.android.systemui.keyguard.data.repository.C1633x38e7c866;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardBottomAreaInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardFaceAuthInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.SystemUIKeyguardFaceAuthInteractor;
import com.android.systemui.keyguard.p009ui.viewmodel.DreamingToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.p009ui.viewmodel.GoneToDreamingTransitionViewModel;
import com.android.systemui.keyguard.p009ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.keyguard.p009ui.viewmodel.KeyguardLongPressViewModel;
import com.android.systemui.keyguard.p009ui.viewmodel.LockscreenToDreamingTransitionViewModel;
import com.android.systemui.keyguard.p009ui.viewmodel.LockscreenToOccludedTransitionViewModel;
import com.android.systemui.keyguard.p009ui.viewmodel.OccludedToLockscreenTransitionViewModel;
import com.android.systemui.keyguardimage.WallpaperImageInjectCreator;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.log.SecPanelLogger;
import com.android.systemui.log.SecPanelLoggerImpl;
import com.android.systemui.log.SecTouchLogHelper;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.media.MediaPlayerBarExpandHelper;
import com.android.systemui.media.MediaType;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.media.controls.p010ui.KeyguardMediaController;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.p016qs.NonInterceptingScrollView;
import com.android.systemui.p016qs.QSFragment;
import com.android.systemui.p016qs.SecQSPanelController;
import com.android.systemui.p016qs.SecQSPanelResourcePicker;
import com.android.systemui.p016qs.animator.SecQSFragmentAnimatorManager;
import com.android.systemui.pluginlock.PluginLockData;
import com.android.systemui.pluginlock.PluginLockDataImpl;
import com.android.systemui.pluginlock.PluginLockDelegateApp;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.PluginLockMediatorImpl;
import com.android.systemui.pluginlock.PluginLockMediatorImpl$$ExternalSyntheticLambda2;
import com.android.systemui.pluginlock.listener.PluginLockListener$State;
import com.android.systemui.pluginlock.model.DynamicLockData;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.ClockController;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import com.android.systemui.plugins.keyguardstatusview.PluginNotificationController;
import com.android.systemui.plugins.p013qs.InterfaceC1922QS;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.shade.NPVCDownEventState;
import com.android.systemui.shade.QuickSettingsController;
import com.android.systemui.shade.QuickSettingsController.QsFragmentListener;
import com.android.systemui.shade.SecExpandQSAtOnceController;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.shade.ShadeStateEvents;
import com.android.systemui.shade.panelpolicy.NotificationPanelViewControllerAgent;
import com.android.systemui.shade.transition.ShadeTransitionController;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.GestureRecorder;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.LockscreenNotificationManager;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.NotificationShelfController;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.PulseExpansionHandler;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.iconsOnly.AnimationCreator;
import com.android.systemui.statusbar.iconsOnly.LockscreenNotificationIconsOnlyController;
import com.android.systemui.statusbar.iconsOnly.NotificationIconTransitionController;
import com.android.systemui.statusbar.iconsOnly.NotificationIconTransitionController$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.ViewGroupFadeHelper;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator;
import com.android.systemui.statusbar.notification.stack.SecNsslOpaqueBgHelper;
import com.android.systemui.statusbar.phone.BounceInterpolator;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.DcmMascotViewContainer;
import com.android.systemui.statusbar.phone.DcmMascotViewContainer$broadcastReceiver$1;
import com.android.systemui.statusbar.phone.DcmMascotViewContainer$sendUnreadCountBroadcast$1;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.HeadsUpTouchHelper;
import com.android.systemui.statusbar.phone.IndicatorTouchHandler;
import com.android.systemui.statusbar.phone.IndicatorTouchHandler$doubleTapTimeoutRunnable$1;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaView;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaViewController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm;
import com.android.systemui.statusbar.phone.KeyguardIndicationTextView;
import com.android.systemui.statusbar.phone.KeyguardSecAffordanceHelper;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView;
import com.android.systemui.statusbar.phone.KeyguardStatusBarView;
import com.android.systemui.statusbar.phone.KeyguardStatusBarViewController;
import com.android.systemui.statusbar.phone.LockscreenGestureLogger;
import com.android.systemui.statusbar.phone.ScreenOffAnimation;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.ScrimState;
import com.android.systemui.statusbar.phone.SecLsScrimControlHelper;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager;
import com.android.systemui.statusbar.phone.TapAgainView;
import com.android.systemui.statusbar.phone.TapAgainViewController;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager;
import com.android.systemui.statusbar.phone.datausage.DataUsageLabelParent;
import com.android.systemui.statusbar.phone.datausage.DataUsageLabelView;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardQsUserSwitchController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherController;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherListView;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherScrim;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherView;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.statusbar.window.StatusBarWindowStateListener;
import com.android.systemui.statusbar.window.StatusBarWindowView;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.LargeScreenUtils;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.QsStatusEventLog;
import com.android.systemui.util.QsStatusEventLog$$ExternalSyntheticLambda0;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIWidgetCallback;
import com.android.systemui.widget.SystemUIWidgetUtil;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.multiwindow.MultiWindowEdgeDetector;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager;
import com.sec.ims.gls.GlsIntent;
import com.sec.ims.presence.ServiceTuple;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.IntConsumer;
import javax.inject.Provider;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableSet;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationPanelViewController implements ShadeSurface, Dumpable, PluginLockListener$State, KeyguardTouchViewInjector, PanelScreenShotLogger.LogProvider {
    public boolean dataUsageVisible;
    public View.OnTouchListener mAODDoubleTouchListener;
    public AODOverlayContainer mAODOverlayContainer;
    public final ShadeAccessibilityDelegate mAccessibilityDelegate;
    public final AccessibilityManager mAccessibilityManager;
    public final ActivityStarter mActivityStarter;
    public boolean mAllowExpandForSmallExpansion;
    public final AlternateBouncerInteractor mAlternateBouncerInteractor;
    public final AmbientState mAmbientState;
    public boolean mAnimateAfterExpanding;
    public final NotificationPanelViewController$$ExternalSyntheticLambda0 mAnimateKeyguardBottomAreaInvisibleEndRunnable;
    public boolean mAnimateNextPositionUpdate;
    public boolean mAnimatingOnDown;
    public final AuthController mAuthController;
    public float mAvailableNotifSpace;
    public int mBarState;
    public boolean mBlockingExpansionForCurrentTouch;
    public float mBottomAreaShadeAlpha;
    public final ValueAnimator mBottomAreaShadeAlphaAnimator;
    public int mBottomMarginY;
    public final BounceInterpolator mBounceInterpolator;
    public boolean mBouncerShowing;
    public CentralSurfaces mCentralSurfaces;
    public final KeyguardClockPositionAlgorithm mClockPositionAlgorithm;
    public boolean mClosing;
    public boolean mClosingWithAlphaFadeOut;
    public boolean mCollapsedAndHeadsUpOnDown;
    public boolean mCollapsedOnDown;
    public final CommandQueue mCommandQueue;
    public final ConfigurationController mConfigurationController;
    public final ConfigurationListener mConfigurationListener;
    public final ContentResolver mContentResolver;
    public final ConversationNotificationManager mConversationNotificationManager;
    public int mCountOfUpdateDisplayedNotifications;
    public long mCountOfUpdateDisplayedNotificationsCurrentMill;
    public final Lazy mCoverScreenManagerLazy;
    public int mCurrentPanelState;
    public final Lazy mDataUsageLabelManagerLazy;
    public DataUsageLabelParent mDataUsageLabelParent;
    public final NotificationShadeDepthController mDepthController;
    public boolean mDetailViewCollapseAnimating;
    public final int mDisplayId;
    public long mDownTime;
    public float mDownX;
    public float mDownY;
    public final DozeLog mDozeLog;
    public final DozeParameters mDozeParameters;
    public boolean mDozing;
    public boolean mDozingOnDown;
    public final NotificationPanelViewController$$ExternalSyntheticLambda1 mDreamingToLockscreenTransition;
    public int mDreamingToLockscreenTransitionTranslationY;
    public final DreamingToLockscreenTransitionViewModel mDreamingToLockscreenTransitionViewModel;
    public View mEditModeContainer;
    public final EmergencyButtonController.Factory mEmergencyButtonControllerFactory;
    public Runnable mExpandAfterLayoutRunnable;
    public boolean mExpandLatencyTracking;
    public float mExpandedFraction;
    public boolean mExpanding;
    public boolean mExpandingFromHeadsUp;
    public float mExpansionDragDownAmountPx;
    public boolean mExpectingSynthesizedDown;
    public final FalsingCollector mFalsingCollector;
    public final FalsingManager mFalsingManager;
    public final FeatureFlags mFeatureFlags;
    public int mFixedDuration;
    public FlingAnimationUtils mFlingAnimationUtils;
    public final Provider mFlingAnimationUtilsBuilder;
    public final FlingAnimationUtils mFlingAnimationUtilsClosing;
    public final FlingAnimationUtils mFlingAnimationUtilsDismissing;
    public final NotificationPanelViewController$$ExternalSyntheticLambda0 mFlingCollapseRunnable;
    public final FragmentService mFragmentService;
    public GestureRecorder mGestureRecorder;
    public boolean mGestureWaitForTouchSlop;
    public final NotificationPanelViewController$$ExternalSyntheticLambda1 mGoneToDreamingTransition;
    public int mGoneToDreamingTransitionTranslationY;
    public final GoneToDreamingTransitionViewModel mGoneToDreamingTransitionViewModel;
    public final NotificationGutsManager mGutsManager;
    public boolean mHandlingPointerUp;
    public boolean mHasLayoutedSinceDown;
    public boolean mHasVibratedOnOpen;
    public boolean mHeadsUpAnimatingAway;
    public HeadsUpAppearanceController mHeadsUpAppearanceController;
    public final NotificationPanelViewController$$ExternalSyntheticLambda0 mHeadsUpExistenceChangedRunnable;
    public int mHeadsUpInset;
    public HeadsUpManagerPhone mHeadsUpManager;
    public boolean mHeadsUpPinnedMode;
    public int mHeadsUpStartHeight;
    public HeadsUpTouchHelper mHeadsUpTouchHelper;
    public ValueAnimator mHeightAnimator;
    public Runnable mHideExpandedRunnable;
    public boolean mHintAnimationRunning;
    public float mHintDistance;
    public boolean mIgnoreXTouchSlop;
    public int mIndicationBottomPadding;
    public final IndicatorTouchHandler mIndicatorTouchHandler;
    public float mInitialExpandX;
    public float mInitialExpandY;
    public float mInitialOffsetOnTouch;
    public boolean mInitialTouchFromKeyguard;
    public boolean mInstantExpanding;
    public float mInterpolatedDarkAmount;
    public boolean mIsAnyMultiShadeExpanded;
    public boolean mIsExpandingOrCollapsing;
    public boolean mIsFlinging;
    public boolean mIsFullWidth;
    public boolean mIsGestureNavigation;
    public boolean mIsKeyguardSupportLandscapePhone;
    public boolean mIsLaunchAnimationRunning;
    public boolean mIsLaunchTransitionFinished;
    public boolean mIsLaunchTransitionRunning;
    public boolean mIsLockStarOnTouchDown;
    public boolean mIsOcclusionTransitionRunning;
    public boolean mIsPanelCollapseOnQQS;
    public boolean mIsSpringBackAnimation;
    public final KeyguardAffordanceHelperCallback mKeyguardAffordanceHelperCallback;
    public KeyguardBottomAreaView mKeyguardBottomArea;
    public final KeyguardBottomAreaInteractor mKeyguardBottomAreaInteractor;
    public final KeyguardBottomAreaViewController mKeyguardBottomAreaViewController;
    public final KeyguardBottomAreaViewModel mKeyguardBottomAreaViewModel;
    public final KeyguardBypassController mKeyguardBypassController;
    public final KeyguardEditModeController mKeyguardEditModeController;
    public final KeyguardFaceAuthInteractor mKeyguardFaceAuthInteractor;
    public final KeyguardIndicationController mKeyguardIndicationController;
    public final KeyguardInteractor mKeyguardInteractor;
    public final KeyguardMediaController mKeyguardMediaController;
    public float mKeyguardNotificationBottomPadding;
    public float mKeyguardNotificationTopPadding;
    public float mKeyguardOnlyContentAlpha;
    public int mKeyguardOnlyTransitionTranslationY;
    public KeyguardPunchHoleVIView mKeyguardPunchHoleVIView;
    public final KeyguardQsUserSwitchComponent.Factory mKeyguardQsUserSwitchComponentFactory;
    public KeyguardQsUserSwitchController mKeyguardQsUserSwitchController;
    public boolean mKeyguardQsUserSwitchEnabled;
    public final KeyguardStateControllerImpl mKeyguardStateController;
    public KeyguardStatusBarView mKeyguardStatusBar;
    public final KeyguardStatusBarViewComponent.Factory mKeyguardStatusBarViewComponentFactory;
    public KeyguardStatusBarViewController mKeyguardStatusBarViewController;
    public final KeyguardStatusViewComponent.Factory mKeyguardStatusViewComponentFactory;
    public KeyguardStatusViewController mKeyguardStatusViewController;
    public final KeyguardTouchAnimator mKeyguardTouchAnimator;
    public final KeyguardTransitionInteractor mKeyguardTransitionInteractor;
    public final Optional mKeyguardUnfoldTransition;
    public final KeyguardUserSwitcherComponent.Factory mKeyguardUserSwitcherComponentFactory;
    public KeyguardUserSwitcherController mKeyguardUserSwitcherController;
    public boolean mKeyguardUserSwitcherEnabled;
    public final KeyguardWallpaperController mKeyguardWallpaperController;
    public int mLastCameraLaunchSource;
    public final NPVCDownEventState.Buffer mLastDownEvents;
    public boolean mLastEventSynthesizedDown;
    public float mLastGesturedOverExpansion;
    public final LatencyTracker mLatencyTracker;
    public Runnable mLaunchAnimationEndRunnable;
    public boolean mLaunchingAffordance;
    public final LayoutInflater mLayoutInflater;
    public float mLinearDarkAmount;
    public boolean mListenForHeadsUp;
    public final SecLockIconViewController mLockIconViewController;
    public boolean mLockStarEnabled;
    public final LockscreenGestureLogger mLockscreenGestureLogger;
    public final LockscreenNotificationIconsOnlyController mLockscreenNotificationIconsOnlyController;
    public final LockscreenNotificationManager mLockscreenNotificationManager;
    public final LockscreenShadeTransitionController mLockscreenShadeTransitionController;
    public final NotificationPanelViewController$$ExternalSyntheticLambda1 mLockscreenToDreamingTransition;
    public int mLockscreenToDreamingTransitionTranslationY;
    public final LockscreenToDreamingTransitionViewModel mLockscreenToDreamingTransitionViewModel;
    public final NotificationPanelViewController$$ExternalSyntheticLambda1 mLockscreenToOccludedTransition;
    public int mLockscreenToOccludedTransitionTranslationY;
    public final LockscreenToOccludedTransitionViewModel mLockscreenToOccludedTransitionViewModel;
    public final StringBuilder mLogBuilder;
    public final CoroutineDispatcher mMainDispatcher;
    public final DcmMascotViewContainer mMascotViewContainer;
    public int mMaxAllowedKeyguardNotifications;
    public int mMaxOverscrollAmountForPulse;
    public final NotificationPanelViewController$$ExternalSyntheticLambda0 mMaybeHideExpandedRunnable;
    public final MediaDataManager mMediaDataManager;
    public final MetricsLogger mMetricsLogger;
    public float mMinExpandHeight;
    public float mMinFraction;
    public boolean mMotionAborted;
    public final NotificationPanelViewController$$ExternalSyntheticLambda1 mMultiShadeExpansionConsumer;
    public final MultiWindowEdgeDetector mMultiWindowEdgeDetector;
    public boolean mNavBarKeyboardButtonShowing;
    public int mNavigationBarBottomHeight;
    public final NavigationBarController mNavigationBarController;
    public float mNextCollapseSpeedUpFactor;
    public NotificationsQuickSettingsContainer mNotificationContainerParent;
    public final NotificationListContainer mNotificationListContainer;
    public final Optional mNotificationPanelUnfoldAnimationController;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public NotificationShelfController mNotificationShelfController;
    public final NotificationStackScrollLayoutController mNotificationStackScrollLayoutController;
    public final NotificationStackSizeCalculator mNotificationStackSizeCalculator;
    public final boolean mNotificationsDragEnabled;
    public final NotificationsQSContainerController mNotificationsQSContainerController;
    public final NotificationPanelViewController$$ExternalSyntheticLambda1 mOccludedToLockscreenTransition;
    public int mOccludedToLockscreenTransitionTranslationY;
    public final OccludedToLockscreenTransitionViewModel mOccludedToLockscreenTransitionViewModel;
    public int mOldLayoutDirection;
    public final ShadeHeadsUpChangedListener mOnHeadsUpChangedListener;
    public boolean mOnlyAffordanceInThisMotion;
    public ShadeControllerImpl.C24622 mOpenCloseListener;
    public float mOverStretchAmount;
    public int mPanelAlpha;
    public final AnimatableProperty.C27056 mPanelAlphaAnimator;
    public Runnable mPanelAlphaEndAction;
    public final AnimationProperties mPanelAlphaInPropertiesAnimator;
    public final AnimationProperties mPanelAlphaOutPropertiesAnimator;
    public boolean mPanelClosedOnDown;
    public boolean mPanelExpanded;
    public float mPanelFlingOvershootAmount;
    public final SecPanelLogger mPanelLogger;
    public boolean mPanelUpdateWhenAnimatorEnds;
    public final Lazy mPluginAODManagerLazy;
    public PluginLock mPluginLock;
    public final PluginLockData mPluginLockData;
    public final PluginLockMediator mPluginLockMediator;
    public View mPluginLockStarContainer;
    public final Lazy mPluginLockStarManagerLazy;
    public int mPluginLockViewMode;
    public final NotificationPanelViewController$$ExternalSyntheticLambda0 mPostCollapseRunnable;
    public final PowerManager mPowerManager;
    public final PrivacyDialogController mPrivacyDialogController;
    public final PulseExpansionHandler mPulseExpansionHandler;
    public boolean mPulsing;
    public final KeyguardPunchHoleVIViewController.Factory mPunchHoleVIViewControllerFactory;
    public final QuickSettingsController mQsController;
    public boolean mQsExpandedOnTouchDown;
    public boolean mQsExpandedViewCollapseAnimating;
    public final QsStatusEventLog mQsStatusEventLog;
    public final int mQuickQsOffsetHeight;
    public String mRecomputedMaxCountCallStack;
    public String mRecomputedMaxCountNotification;
    public String mRecomputedReason;
    public final Resources mResources;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public final ScrimController mScrimController;
    public KeyguardSecAffordanceHelper mSecAffordanceHelper;
    public final SettingsChangeObserver mSettingsChangeObserver;
    public final ShadeExpansionStateManager mShadeExpansionStateManager;
    public final ShadeFoldAnimatorImpl mShadeFoldAnimator;
    public final ShadeHeaderController mShadeHeaderController;
    public final ShadeHeadsUpTrackerImpl mShadeHeadsUpTracker;
    public final ShadeLogger mShadeLog;
    public final ShadeNotificationPresenterImpl mShadeNotificationPresenter;
    public final C243316 mShadeViewStateProvider;
    public int mShortcut;
    public boolean mShowIconsWhenExpanded;
    public float mSlopMultiplier;
    public boolean mSplitShadeEnabled;
    public int mSplitShadeFullTransitionDistance;
    public int mSplitShadeScrimTransitionDistance;
    public int mStackScrollerMeasuringPass;
    public int mStatusBarHeaderHeightKeyguard;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public int mStatusBarMinHeight;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public final StatusBarStateListener mStatusBarStateListener;
    public final StatusBarTouchableRegionManager mStatusBarTouchableRegionManager;
    public final Lazy mSubScreenManagerLazy;
    public final SysUiState mSysUiState;
    public final SystemClock mSystemClock;
    public final C243417 mSystemUIWidgetCallback;
    public final TapAgainViewController mTapAgainViewController;
    public boolean mTouchAboveFalsingThreshold;
    public boolean mTouchDisabled;
    public boolean mTouchDownOnHeadsUpPinnded;
    public boolean mTouchIsClick;
    public int mTouchSlop;
    public boolean mTouchSlopExceeded;
    public boolean mTouchSlopExceededBeforeDown;
    public boolean mTouchStartedInEmptyArea;
    public ExpandableNotificationRow mTrackedHeadsUpNotification;
    public boolean mTracking;
    public int mTrackingPointer;
    public ShadeControllerImpl$$ExternalSyntheticLambda0 mTrackingStartedListener;
    public float mUdfpsMaxYBurnInOffset;
    public final UnlockedScreenOffAnimationController mUnlockedScreenOffAnimationController;
    public boolean mUpdateFlingOnLayout;
    public float mUpdateFlingVelocity;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public boolean mUpwardsWhenThresholdReached;
    public final UserManager mUserManager;
    public boolean mUserSetupComplete;
    public final boolean mVibrateOnOpening;
    public final VibratorHelper mVibratorHelper;
    public final NotificationPanelView mView;
    public String mViewName;
    public final NotificationWakeUpCoordinator mWakeUpCoordinator;
    public final WallpaperImageInjectCreator mWallpaperImageCreator;
    public boolean mWillPlayDelayedDozeAmountAnimation;
    public static final VibrationEffect ADDITIONAL_TAP_REQUIRED_VIBRATION_EFFECT = VibrationEffect.get(1, false);
    public static final long ANIMATION_DELAY_ICON_FADE_IN = ((ActivityLaunchAnimator.TIMINGS.totalDuration - 320) - 50) - 48;
    public static final Rect M_DUMMY_DIRTY_RECT = new Rect(0, 0, 1, 1);
    public static final Rect EMPTY_RECT = new Rect();
    public final NotificationPanelViewController$$ExternalSyntheticLambda2 mOnEmptySpaceClickListener = new NotificationPanelViewController$$ExternalSyntheticLambda2(this);
    public final VelocityTracker mVelocityTracker = VelocityTracker.obtain();
    public final NotificationPanelViewController$$ExternalSyntheticLambda5 mFalsingTapListener = new FalsingManager.FalsingTapListener() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda5
        @Override // com.android.systemui.plugins.FalsingManager.FalsingTapListener
        public final void onAdditionalTapRequired() {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) notificationPanelViewController.mStatusBarStateController;
            if (statusBarStateControllerImpl.mState == 2) {
                final TapAgainViewController tapAgainViewController = notificationPanelViewController.mTapAgainViewController;
                ExecutorImpl.ExecutionToken executionToken = tapAgainViewController.mHideCanceler;
                if (executionToken != null) {
                    executionToken.run();
                }
                ((TapAgainView) tapAgainViewController.mView).animateIn();
                Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.TapAgainViewController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        TapAgainViewController tapAgainViewController2 = TapAgainViewController.this;
                        tapAgainViewController2.mHideCanceler = null;
                        ((TapAgainView) tapAgainViewController2.mView).animateOut();
                    }
                };
                tapAgainViewController.mHideCanceler = tapAgainViewController.mDelayableExecutor.executeDelayed(tapAgainViewController.mDoubleTapTimeMs, runnable);
            } else {
                notificationPanelViewController.mKeyguardIndicationController.showTransientIndication(R.string.notification_tap_again);
            }
            if (statusBarStateControllerImpl.mIsDozing) {
                return;
            }
            notificationPanelViewController.mVibratorHelper.vibrate(Process.myUid(), notificationPanelViewController.mView.getContext().getPackageName(), NotificationPanelViewController.ADDITIONAL_TAP_REQUIRED_VIBRATION_EFFECT, "falsing-additional-tap-required", VibratorHelper.TOUCH_VIBRATION_ATTRIBUTES);
        }
    };
    public final TouchHandler mTouchHandler = new TouchHandler();
    public float mExpandedHeight = 0.0f;
    public float mCurrentBackProgress = 0.0f;
    public int mDisplayTopInset = 0;
    public int mDisplayRightInset = 0;
    public int mDisplayLeftInset = 0;
    public int mNotiCardCount = -1;
    public boolean mIsFaceWidgetOnTouchDown = false;
    public boolean mFullScreenModeEnabled = false;
    public int mPanelInVisibleReason = -1;
    public final KeyguardClockPositionAlgorithm.Result mClockPositionResult = new KeyguardClockPositionAlgorithm.Result();
    public boolean mHideIconsDuringLaunchAnimation = true;
    public final ArrayList mTrackingHeadsUpListeners = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.shade.NotificationPanelViewController$16 */
    public final class C243316 implements ShadeViewStateProvider {
        public C243316() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.shade.NotificationPanelViewController$8 */
    public final class C24418 {
        public C24418() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.shade.NotificationPanelViewController$9 */
    public final class C24429 implements KeyguardStatusCallback {
        public C24429() {
        }

        public final void setFullScreenMode(boolean z, long j, Animator.AnimatorListener animatorListener) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.getClass();
            Log.d("NotificationPanelView", "setFullScreenMode() enabled = " + z + ", duration = " + j + ", listener = " + animatorListener);
            notificationPanelViewController.mFullScreenModeEnabled = z;
            NotificationPanelView notificationPanelView = notificationPanelViewController.mView;
            notificationPanelView.animate().cancel();
            float f = z ? 0.0f : 1.0f;
            if (j <= 0) {
                Log.d("NotificationPanelView", "updateAlpha() mFullScreenModeEnabled = " + notificationPanelViewController.mFullScreenModeEnabled + ", alpha = " + f);
                if (!notificationPanelViewController.mFullScreenModeEnabled || f <= 0.0f) {
                    notificationPanelView.setAlpha(f);
                }
                if (animatorListener != null) {
                    animatorListener.onAnimationStart(null);
                    animatorListener.onAnimationEnd(null);
                }
            } else {
                notificationPanelView.animate().alpha(f).setDuration(j).setInterpolator(Interpolators.ACCELERATE).setListener(animatorListener).withEndAction(null).withLayer();
            }
            notificationPanelViewController.mUpdateMonitor.setFaceWidgetFullScreenMode(z);
            if (z) {
                notificationPanelViewController.mKeyguardTouchAnimator.reset();
            }
            if (z) {
                ((CentralSurfacesImpl) notificationPanelViewController.mCentralSurfaces).mNotificationShadeWindowView.getWindowInsetsController().setAnimationsDisabled(z);
                NavigationBarView navigationBarView = ((CentralSurfacesImpl) notificationPanelViewController.mCentralSurfaces).getNavigationBarView();
                if (navigationBarView != null) {
                    View view = navigationBarView.mVertical;
                    if (view != null) {
                        view.animate().alpha(1.0f).start();
                    }
                    View view2 = navigationBarView.mHorizontal;
                    if (view2 != null) {
                        view2.animate().alpha(1.0f).start();
                    }
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ConfigurationListener implements ConfigurationController.ConfigurationListener {
        public /* synthetic */ ConfigurationListener(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onConfigChanged(Configuration configuration) {
            boolean z = QpRune.QUICK_TABLET_HORIZONTAL_PANEL_POSITION;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            if (z) {
                SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper = notificationPanelViewController.mQsController.mSecQuickSettingsController.tabletHorizontalPanelPositionHelper;
                int i = secTabletHorizontalPanelPositionHelper.lastOrientation;
                int i2 = configuration.orientation;
                if (i != i2) {
                    secTabletHorizontalPanelPositionHelper.lastOrientation = i2;
                    secTabletHorizontalPanelPositionHelper.resetHorizontalPanelPosition(false);
                }
            }
            MultiWindowEdgeDetector multiWindowEdgeDetector = notificationPanelViewController.mMultiWindowEdgeDetector;
            if (multiWindowEdgeDetector != null) {
                multiWindowEdgeDetector.onConfigurationChanged();
            }
            notificationPanelViewController.mKeyguardIndicationController.onConfigChanged();
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDensityOrFontScaleChanged() {
            PluginLockDelegateApp pluginLockDelegateApp;
            PluginLockBasicManager pluginLockBasicManager;
            VibrationEffect vibrationEffect = NotificationPanelViewController.ADDITIONAL_TAP_REQUIRED_VIBRATION_EFFECT;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.getClass();
            notificationPanelViewController.reInflateViews();
            notificationPanelViewController.mKeyguardTouchAnimator.initDimens();
            if (LsRune.KEYGUARD_DCM_LIVE_UX) {
                DcmMascotViewContainer dcmMascotViewContainer = notificationPanelViewController.mMascotViewContainer;
                dcmMascotViewContainer.updateRes();
                ViewGroup.LayoutParams layoutParams = dcmMascotViewContainer.getLayoutParams();
                layoutParams.height = dcmMascotViewContainer.mascotHeight;
                dcmMascotViewContainer.setLayoutParams(layoutParams);
            }
            PluginLockMediator pluginLockMediator = notificationPanelViewController.mPluginLockMediator;
            if (pluginLockMediator != null && (pluginLockDelegateApp = ((PluginLockMediatorImpl) pluginLockMediator).mBasicListener) != null && (pluginLockBasicManager = pluginLockDelegateApp.mBasicManager) != null) {
                pluginLockBasicManager.onDensityOrFontScaleChanged();
            }
            ((KeyguardEditModeControllerImpl) notificationPanelViewController.mKeyguardEditModeController).refreshRadius();
            if (notificationPanelViewController.mKeyguardBottomArea != null) {
                notificationPanelViewController.mKeyguardBottomAreaViewController.onDensityOrFontScaleChanged();
                notificationPanelViewController.mSecAffordanceHelper.initDimens();
            }
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onSmallestScreenWidthChanged() {
            Trace.beginSection("onSmallestScreenWidthChanged");
            VibrationEffect vibrationEffect = NotificationPanelViewController.ADDITIONAL_TAP_REQUIRED_VIBRATION_EFFECT;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.getClass();
            boolean z = notificationPanelViewController.mKeyguardUserSwitcherEnabled;
            boolean z2 = notificationPanelViewController.mKeyguardQsUserSwitchEnabled;
            notificationPanelViewController.updateUserSwitcherFlags();
            if (z != notificationPanelViewController.mKeyguardUserSwitcherEnabled || z2 != notificationPanelViewController.mKeyguardQsUserSwitchEnabled) {
                notificationPanelViewController.reInflateViews();
            }
            Trace.endSection();
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onThemeChanged() {
            VibrationEffect vibrationEffect = NotificationPanelViewController.ADDITIONAL_TAP_REQUIRED_VIBRATION_EFFECT;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.getClass();
            notificationPanelViewController.reInflateViews();
        }

        private ConfigurationListener() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class HeadsUpNotificationViewControllerImpl implements HeadsUpTouchHelper.HeadsUpNotificationViewController {
        public /* synthetic */ HeadsUpNotificationViewControllerImpl(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        private HeadsUpNotificationViewControllerImpl() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class KeyguardAffordanceHelperCallback implements KeyguardSecAffordanceHelper.Callback {
        public /* synthetic */ KeyguardAffordanceHelperCallback(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        private KeyguardAffordanceHelperCallback() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NsslHeightChangedListener implements ExpandableView.OnHeightChangedListener {
        public /* synthetic */ NsslHeightChangedListener(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.notification.row.ExpandableView.OnHeightChangedListener
        public final void onHeightChanged(ExpandableView expandableView, boolean z) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            if (expandableView == null && notificationPanelViewController.mQsController.mExpanded) {
                return;
            }
            if (z && notificationPanelViewController.mInterpolatedDarkAmount == 0.0f) {
                notificationPanelViewController.mAnimateNextPositionUpdate = true;
            }
            ExpandableView firstChildNotGone = notificationPanelViewController.mNotificationStackScrollLayoutController.mView.getFirstChildNotGone();
            ExpandableNotificationRow expandableNotificationRow = firstChildNotGone instanceof ExpandableNotificationRow ? (ExpandableNotificationRow) firstChildNotGone : null;
            if (expandableNotificationRow != null && (expandableView == expandableNotificationRow || expandableNotificationRow.mNotificationParent == expandableNotificationRow)) {
                notificationPanelViewController.requestScrollerTopPaddingUpdate(false);
            }
            if (notificationPanelViewController.isKeyguardShowing()) {
                notificationPanelViewController.updateMaxDisplayedNotifications(true);
                notificationPanelViewController.mRecomputedMaxCountCallStack = "onHeightChanged";
            }
            notificationPanelViewController.updateExpandedHeightToMaxHeight();
        }

        private NsslHeightChangedListener() {
        }

        @Override // com.android.systemui.statusbar.notification.row.ExpandableView.OnHeightChangedListener
        public final void onReset(ExpandableNotificationRow expandableNotificationRow) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SettingsChangeObserver extends ContentObserver {
        public SettingsChangeObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            VibrationEffect vibrationEffect = NotificationPanelViewController.ADDITIONAL_TAP_REQUIRED_VIBRATION_EFFECT;
            notificationPanelViewController.getClass();
            NotificationPanelViewController.this.reInflateViews();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ShadeAccessibilityDelegate extends View.AccessibilityDelegate {
        public /* synthetic */ ShadeAccessibilityDelegate(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        @Override // android.view.View.AccessibilityDelegate
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP);
        }

        @Override // android.view.View.AccessibilityDelegate
        public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (i != AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD.getId() && i != AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP.getId()) {
                return super.performAccessibilityAction(view, i, bundle);
            }
            NotificationPanelViewController.this.mStatusBarKeyguardViewManager.showPrimaryBouncer(true);
            return true;
        }

        private ShadeAccessibilityDelegate() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ShadeAttachStateChangeListener implements View.OnAttachStateChangeListener {
        public /* synthetic */ ShadeAttachStateChangeListener(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            FragmentHostManager fragmentHostManager = notificationPanelViewController.mFragmentService.getFragmentHostManager(notificationPanelViewController.mView);
            QuickSettingsController quickSettingsController = NotificationPanelViewController.this.mQsController;
            quickSettingsController.getClass();
            fragmentHostManager.addTagListener(InterfaceC1922QS.TAG, quickSettingsController.new QsFragmentListener());
            NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
            ((StatusBarStateControllerImpl) notificationPanelViewController2.mStatusBarStateController).addCallback((StatusBarStateController.StateListener) notificationPanelViewController2.mStatusBarStateListener);
            NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
            notificationPanelViewController3.mStatusBarStateListener.onStateChanged(((StatusBarStateControllerImpl) notificationPanelViewController3.mStatusBarStateController).mState);
            NotificationPanelViewController notificationPanelViewController4 = NotificationPanelViewController.this;
            ((ConfigurationControllerImpl) notificationPanelViewController4.mConfigurationController).addCallback(notificationPanelViewController4.mConfigurationListener);
            NotificationPanelViewController.this.mConfigurationListener.onThemeChanged();
            NotificationPanelViewController notificationPanelViewController5 = NotificationPanelViewController.this;
            notificationPanelViewController5.mFalsingManager.addTapListener(notificationPanelViewController5.mFalsingTapListener);
            NotificationPanelViewController.this.mKeyguardIndicationController.init();
            NotificationPanelViewController notificationPanelViewController6 = NotificationPanelViewController.this;
            notificationPanelViewController6.getClass();
            final int i = 0;
            notificationPanelViewController6.mContentResolver.registerContentObserver(Settings.Global.getUriFor("user_switcher_enabled"), false, notificationPanelViewController6.mSettingsChangeObserver);
            boolean z = QpRune.PANEL_DATA_USAGE_LABEL;
            if (z) {
                final DataUsageLabelManager dataUsageLabelManager = (DataUsageLabelManager) NotificationPanelViewController.this.mDataUsageLabelManagerLazy.get();
                DataUsageLabelParent dataUsageLabelParent = dataUsageLabelManager.mDataUsageLabelParent;
                if (dataUsageLabelParent == null || dataUsageLabelParent.getParentViewGroup() == null) {
                    Log.e("DataUsageLabelManager", "attachDataUsageLabelView() - but panel parent view is null" + dataUsageLabelParent);
                } else if (z) {
                    if (DataUsageLabelManager.DEBUG) {
                        Log.d("DataUsageLabelManager", "attachDataUsageLabelView(COMMON for DATAUSAGE)");
                    }
                    DataUsageLabelView dataUsageLabelView = new DataUsageLabelView(dataUsageLabelManager.mContext);
                    dataUsageLabelView.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
                    dataUsageLabelParent.getParentViewGroup().addView(dataUsageLabelView);
                    dataUsageLabelManager.mLabelView = dataUsageLabelView;
                }
                Dependency.DependencyKey dependencyKey = Dependency.MAIN_HANDLER;
                ((Handler) Dependency.get(dependencyKey)).post(new Runnable() { // from class: com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i) {
                            case 0:
                                DataUsageLabelManager dataUsageLabelManager2 = dataUsageLabelManager;
                                dataUsageLabelManager2.onPanelConfigurationChanged(dataUsageLabelManager2.mContext.getResources().getConfiguration());
                                break;
                            default:
                                dataUsageLabelManager.updateLabelViewColor();
                                break;
                        }
                    }
                });
                final int i2 = 1;
                ((Handler) Dependency.get(dependencyKey)).postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i2) {
                            case 0:
                                DataUsageLabelManager dataUsageLabelManager2 = dataUsageLabelManager;
                                dataUsageLabelManager2.onPanelConfigurationChanged(dataUsageLabelManager2.mContext.getResources().getConfiguration());
                                break;
                            default:
                                dataUsageLabelManager.updateLabelViewColor();
                                break;
                        }
                    }
                }, 10000L);
                DataUsageLabelManager.NavSettingsHelper navSettingsHelper = dataUsageLabelManager.mNavSettingsHelper;
                SettingsHelper settingsHelper = navSettingsHelper.mSettingsHelper;
                if (settingsHelper != null) {
                    settingsHelper.registerCallback(navSettingsHelper, navSettingsHelper.SETTINGS_VALUE_LIST);
                }
                DataUsageLabelManager.QuickStarHelper quickStarHelper = dataUsageLabelManager.mQuickStarHelper;
                ((SlimIndicatorViewMediatorImpl) quickStarHelper.mSlimIndicatorViewMediator).registerSubscriber("DataUsageLabelManager", DataUsageLabelManager.this.mQuickStarHelper);
            }
            NotificationPanelViewController notificationPanelViewController7 = NotificationPanelViewController.this;
            if (notificationPanelViewController7.mBarState != ((StatusBarStateControllerImpl) notificationPanelViewController7.mStatusBarStateController).mState) {
                VibrationEffect vibrationEffect = NotificationPanelViewController.ADDITIONAL_TAP_REQUIRED_VIBRATION_EFFECT;
                StringBuilder sb = new StringBuilder("panel mBarState ");
                sb.append(NotificationPanelViewController.this.mBarState);
                sb.append("/ StatusBarStateController.getState() ");
                RecyclerView$$ExternalSyntheticOutline0.m46m(sb, ((StatusBarStateControllerImpl) NotificationPanelViewController.this.mStatusBarStateController).mState, "NotificationPanelView");
                NotificationPanelViewController notificationPanelViewController8 = NotificationPanelViewController.this;
                notificationPanelViewController8.mStatusBarStateListener.onStateChanged(((StatusBarStateControllerImpl) notificationPanelViewController8.mStatusBarStateController).mState);
            }
            SecExpandQSAtOnceController secExpandQSAtOnceController = NotificationPanelViewController.this.mQsController.mSecQuickSettingsController.expandQSAtOnceController;
            SecExpandQSAtOnceController.SettingsListener settingsListener = secExpandQSAtOnceController.mSettingsListener;
            SecExpandQSAtOnceController secExpandQSAtOnceController2 = SecExpandQSAtOnceController.this;
            secExpandQSAtOnceController2.mSettingsHelper.registerCallback(secExpandQSAtOnceController2.mSettingsListener, settingsListener.mSettingsValueList);
            secExpandQSAtOnceController.updateResources();
            IndicatorTouchHandler indicatorTouchHandler = NotificationPanelViewController.this.mIndicatorTouchHandler;
            indicatorTouchHandler.ongoingCallController.addCallback((OngoingCallListener) indicatorTouchHandler.ongoingCallListener);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewDetachedFromWindow(View view) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.mContentResolver.unregisterContentObserver(notificationPanelViewController.mSettingsChangeObserver);
            NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
            FragmentHostManager fragmentHostManager = notificationPanelViewController2.mFragmentService.getFragmentHostManager(notificationPanelViewController2.mView);
            QuickSettingsController quickSettingsController = NotificationPanelViewController.this.mQsController;
            quickSettingsController.getClass();
            QuickSettingsController.QsFragmentListener qsFragmentListener = quickSettingsController.new QsFragmentListener();
            HashMap hashMap = fragmentHostManager.mListeners;
            ArrayList arrayList = (ArrayList) hashMap.get(InterfaceC1922QS.TAG);
            if (arrayList != null && arrayList.remove(qsFragmentListener) && arrayList.size() == 0) {
                hashMap.remove(InterfaceC1922QS.TAG);
            }
            NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
            ((StatusBarStateControllerImpl) notificationPanelViewController3.mStatusBarStateController).removeCallback((StatusBarStateController.StateListener) notificationPanelViewController3.mStatusBarStateListener);
            NotificationPanelViewController notificationPanelViewController4 = NotificationPanelViewController.this;
            ((ConfigurationControllerImpl) notificationPanelViewController4.mConfigurationController).removeCallback(notificationPanelViewController4.mConfigurationListener);
            NotificationPanelViewController notificationPanelViewController5 = NotificationPanelViewController.this;
            notificationPanelViewController5.mFalsingManager.removeTapListener(notificationPanelViewController5.mFalsingTapListener);
            if (QpRune.PANEL_DATA_USAGE_LABEL) {
                DataUsageLabelManager dataUsageLabelManager = (DataUsageLabelManager) NotificationPanelViewController.this.mDataUsageLabelManagerLazy.get();
                DataUsageLabelParent dataUsageLabelParent = dataUsageLabelManager.mDataUsageLabelParent;
                if (dataUsageLabelParent != null && dataUsageLabelParent.getParentViewGroup() != null) {
                    dataUsageLabelParent.getParentViewGroup().removeAllViews();
                }
                DataUsageLabelManager.NavSettingsHelper navSettingsHelper = dataUsageLabelManager.mNavSettingsHelper;
                SettingsHelper settingsHelper = navSettingsHelper.mSettingsHelper;
                if (settingsHelper != null) {
                    settingsHelper.unregisterCallback(navSettingsHelper);
                }
                ((SlimIndicatorViewMediatorImpl) dataUsageLabelManager.mQuickStarHelper.mSlimIndicatorViewMediator).unregisterSubscriber("DataUsageLabelManager");
            }
            SecExpandQSAtOnceController secExpandQSAtOnceController = SecExpandQSAtOnceController.this;
            secExpandQSAtOnceController.mSettingsHelper.unregisterCallback(secExpandQSAtOnceController.mSettingsListener);
            IndicatorTouchHandler indicatorTouchHandler = NotificationPanelViewController.this.mIndicatorTouchHandler;
            indicatorTouchHandler.ongoingCallController.removeCallback((OngoingCallListener) indicatorTouchHandler.ongoingCallListener);
        }

        private ShadeAttachStateChangeListener() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ShadeFoldAnimatorImpl {
        public /* synthetic */ ShadeFoldAnimatorImpl(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        public final void prepareFoldToAodAnimation() {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.showAodUi();
            notificationPanelViewController.mView.setTranslationX(-notificationPanelViewController.mView.getResources().getDimensionPixelSize(R.dimen.below_clock_padding_start));
            notificationPanelViewController.mView.setAlpha(0.0f);
        }

        private ShadeFoldAnimatorImpl() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ShadeHeadsUpChangedListener implements OnHeadsUpChangedListener {
        public /* synthetic */ ShadeHeadsUpChangedListener(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
        public final void onHeadsUpPinned(NotificationEntry notificationEntry) {
            VibrationEffect vibrationEffect = NotificationPanelViewController.ADDITIONAL_TAP_REQUIRED_VIBRATION_EFFECT;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            if (notificationPanelViewController.isOnKeyguard()) {
                return;
            }
            notificationPanelViewController.mNotificationStackScrollLayoutController.mView.generateHeadsUpAnimation(notificationEntry.row, true);
        }

        @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
        public final void onHeadsUpPinnedModeChanged(boolean z) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            if (z) {
                notificationPanelViewController.mHeadsUpExistenceChangedRunnable.run();
                notificationPanelViewController.updateNotificationTranslucency();
            } else {
                VibrationEffect vibrationEffect = NotificationPanelViewController.ADDITIONAL_TAP_REQUIRED_VIBRATION_EFFECT;
                notificationPanelViewController.mHeadsUpAnimatingAway = true;
                NotificationStackScrollLayout notificationStackScrollLayout = notificationPanelViewController.mNotificationStackScrollLayoutController.mView;
                notificationStackScrollLayout.mHeadsUpAnimatingAway = true;
                notificationStackScrollLayout.updateClipping();
                notificationPanelViewController.updateVisibility();
                notificationPanelViewController.mNotificationStackScrollLayoutController.mView.mAnimationFinishedRunnables.add(notificationPanelViewController.mHeadsUpExistenceChangedRunnable);
            }
            VibrationEffect vibrationEffect2 = NotificationPanelViewController.ADDITIONAL_TAP_REQUIRED_VIBRATION_EFFECT;
            notificationPanelViewController.updateGestureExclusionRect();
            notificationPanelViewController.mHeadsUpPinnedMode = z;
            notificationPanelViewController.updateVisibility();
            notificationPanelViewController.mKeyguardStatusBarViewController.updateForHeadsUp();
        }

        @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
        public final void onHeadsUpUnPinned(NotificationEntry notificationEntry) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            if (notificationPanelViewController.isFullyCollapsed()) {
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                if (!(expandableNotificationRow != null && expandableNotificationRow.mIsHeadsUp) || notificationPanelViewController.isOnKeyguard()) {
                    return;
                }
                notificationPanelViewController.mNotificationStackScrollLayoutController.mView.generateHeadsUpAnimation(notificationEntry.row, false);
                ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
                if (expandableNotificationRow2 != null) {
                    expandableNotificationRow2.mMustStayOnScreen = false;
                }
            }
        }

        private ShadeHeadsUpChangedListener() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ShadeHeadsUpTrackerImpl {
        public /* synthetic */ ShadeHeadsUpTrackerImpl(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        private ShadeHeadsUpTrackerImpl() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ShadeLayoutChangeListener implements View.OnLayoutChangeListener {
        public /* synthetic */ ShadeLayoutChangeListener(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        @Override // android.view.View.OnLayoutChangeListener
        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            DejankUtils.startDetectingBlockingIpcs("NVP#onLayout");
            NotificationPanelViewController.this.updateExpandedHeightToMaxHeight();
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.mHasLayoutedSinceDown = true;
            if (notificationPanelViewController.mUpdateFlingOnLayout) {
                notificationPanelViewController.abortAnimations();
                NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                notificationPanelViewController2.fling(notificationPanelViewController2.mUpdateFlingVelocity);
                NotificationPanelViewController.this.mUpdateFlingOnLayout = false;
            }
            NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
            notificationPanelViewController3.updateMaxDisplayedNotifications(!((notificationPanelViewController3.mHintAnimationRunning || notificationPanelViewController3.mUnlockedScreenOffAnimationController.isAnimationPlaying()) && ((NotificationIconTransitionController) Dependency.get(NotificationIconTransitionController.class)).mChildAnimatable));
            NotificationPanelViewController notificationPanelViewController4 = NotificationPanelViewController.this;
            notificationPanelViewController4.mRecomputedMaxCountCallStack = "onLayoutChange";
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController4.mNotificationStackScrollLayoutController;
            boolean z = ((float) notificationStackScrollLayoutController.mView.getWidth()) == ((float) NotificationPanelViewController.this.mView.getWidth());
            notificationPanelViewController4.mIsFullWidth = z;
            notificationPanelViewController4.mScrimController.getClass();
            notificationStackScrollLayoutController.mView.mAmbientState.mIsSmallScreen = z;
            QuickSettingsController quickSettingsController = notificationPanelViewController4.mQsController;
            quickSettingsController.mIsFullWidth = z;
            InterfaceC1922QS interfaceC1922QS = quickSettingsController.mQs;
            if (interfaceC1922QS != null) {
                interfaceC1922QS.setIsNotificationPanelFullWidth(z);
            }
            QuickSettingsController quickSettingsController2 = NotificationPanelViewController.this.mQsController;
            int i9 = quickSettingsController2.mMaxExpansionHeight;
            if (quickSettingsController2.isQsFragmentCreated()) {
                quickSettingsController2.updateMinHeight();
                int desiredHeight = quickSettingsController2.mQs.getDesiredHeight();
                quickSettingsController2.mMaxExpansionHeight = desiredHeight;
                quickSettingsController2.mNotificationStackScrollLayoutController.mView.mMaxTopPadding = desiredHeight;
            }
            NotificationPanelViewController.this.positionClockAndNotifications(false);
            final QuickSettingsController quickSettingsController3 = NotificationPanelViewController.this.mQsController;
            boolean z2 = quickSettingsController3.mExpanded;
            if (z2 && quickSettingsController3.mFullyExpanded) {
                quickSettingsController3.mExpansionHeight = quickSettingsController3.mMaxExpansionHeight;
                NotificationPanelViewController$$ExternalSyntheticLambda2 notificationPanelViewController$$ExternalSyntheticLambda2 = quickSettingsController3.mExpansionHeightSetToMaxListener;
                if (notificationPanelViewController$$ExternalSyntheticLambda2 != null) {
                    NotificationPanelViewController notificationPanelViewController5 = notificationPanelViewController$$ExternalSyntheticLambda2.f$0;
                    notificationPanelViewController5.requestScrollerTopPaddingUpdate(false);
                    notificationPanelViewController5.updateExpandedHeightToMaxHeight();
                }
                int i10 = quickSettingsController3.mMaxExpansionHeight;
                if (i10 != i9) {
                    ValueAnimator valueAnimator = quickSettingsController3.mSizeChangeAnimator;
                    if (valueAnimator != null) {
                        i9 = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        quickSettingsController3.mSizeChangeAnimator.cancel();
                    }
                    ValueAnimator ofInt = ValueAnimator.ofInt(i9, i10);
                    quickSettingsController3.mSizeChangeAnimator = ofInt;
                    ofInt.setDuration(300L);
                    quickSettingsController3.mSizeChangeAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                    quickSettingsController3.mSizeChangeAnimator.addUpdateListener(new QuickSettingsController$$ExternalSyntheticLambda1(quickSettingsController3, r3));
                    quickSettingsController3.mSizeChangeAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.QuickSettingsController.1
                        public C24511() {
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            QuickSettingsController.this.mSizeChangeAnimator = null;
                        }
                    });
                    quickSettingsController3.mSizeChangeAnimator.start();
                }
            } else {
                if (!z2) {
                    if (!(quickSettingsController3.mExpansionAnimator != null)) {
                        quickSettingsController3.setExpansionHeight(quickSettingsController3.mMinExpansionHeight + quickSettingsController3.mLastOverscroll);
                    }
                }
                quickSettingsController3.mShadeLog.m190v("onLayoutChange: qs expansion not set");
            }
            NotificationPanelViewController notificationPanelViewController6 = NotificationPanelViewController.this;
            notificationPanelViewController6.updateExpandedHeight(notificationPanelViewController6.mExpandedHeight);
            NotificationPanelViewController notificationPanelViewController7 = NotificationPanelViewController.this;
            if (notificationPanelViewController7.mBarState == 1) {
                notificationPanelViewController7.mKeyguardStatusBarViewController.updateViewState();
            }
            notificationPanelViewController7.mQsController.updateExpansion();
            QuickSettingsController quickSettingsController4 = NotificationPanelViewController.this.mQsController;
            if ((quickSettingsController4.mSizeChangeAnimator == null ? 0 : 1) != 0 && quickSettingsController4.isQsFragmentCreated()) {
                InterfaceC1922QS interfaceC1922QS2 = quickSettingsController4.mQs;
                interfaceC1922QS2.setHeightOverride(interfaceC1922QS2.getDesiredHeight());
            }
            NotificationPanelViewController notificationPanelViewController8 = NotificationPanelViewController.this;
            int height = notificationPanelViewController8.mView.getHeight();
            int i11 = notificationPanelViewController8.mNavigationBarBottomHeight;
            NotificationStackScrollLayout notificationStackScrollLayout = notificationPanelViewController8.mNotificationStackScrollLayoutController.mView;
            notificationStackScrollLayout.mAmbientState.mMaxHeadsUpTranslation = height - i11;
            notificationStackScrollLayout.mStateAnimator.mHeadsUpAppearHeightBottom = height;
            notificationStackScrollLayout.requestChildrenUpdate();
            NotificationPanelViewController.this.updateGestureExclusionRect();
            Runnable runnable = NotificationPanelViewController.this.mExpandAfterLayoutRunnable;
            if (runnable != null) {
                runnable.run();
                NotificationPanelViewController.this.mExpandAfterLayoutRunnable = null;
            }
            DejankUtils.stopDetectingBlockingIpcs("NVP#onLayout");
        }

        private ShadeLayoutChangeListener() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ShadeNotificationPresenterImpl {
        public /* synthetic */ ShadeNotificationPresenterImpl(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        private ShadeNotificationPresenterImpl() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StatusBarStateListener implements StatusBarStateController.StateListener {
        public /* synthetic */ StatusBarStateListener(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onDozeAmountChanged(float f, float f2) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.mInterpolatedDarkAmount = f2;
            notificationPanelViewController.mLinearDarkAmount = f;
            PluginFaceWidgetManager pluginFaceWidgetManager = (PluginFaceWidgetManager) Dependency.get(PluginFaceWidgetManager.class);
            if (pluginFaceWidgetManager == null) {
                VibrationEffect vibrationEffect = NotificationPanelViewController.ADDITIONAL_TAP_REQUIRED_VIBRATION_EFFECT;
                Log.e("NotificationPanelView", "Failed to get PluginFaceWidgetManager");
            } else {
                FaceWidgetContainerWrapper faceWidgetContainerWrapper = pluginFaceWidgetManager.mFaceWidgetContainerWrapper;
                if (faceWidgetContainerWrapper != null && (!LsRune.AOD_FULLSCREEN || !notificationPanelViewController.mScreenOffAnimationController.shouldHideLightRevealScrimOnWakeUp())) {
                    float f3 = notificationPanelViewController.mInterpolatedDarkAmount;
                    PluginKeyguardStatusView pluginKeyguardStatusView = faceWidgetContainerWrapper.mPluginKeyguardStatusView;
                    if (pluginKeyguardStatusView != null) {
                        pluginKeyguardStatusView.setDarkAmount(f3);
                    }
                }
            }
            notificationPanelViewController.positionClockAndNotifications(false);
            if (f == 0.0f) {
                notificationPanelViewController.updateMaxDisplayedNotifications(true);
                notificationPanelViewController.mRecomputedMaxCountCallStack = "onDozeAmountChanged";
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:50:0x0237  */
        /* JADX WARN: Removed duplicated region for block: B:55:0x0245  */
        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onStateChanged(int i) {
            int i2;
            NotificationStackScrollLayout notificationStackScrollLayout;
            boolean z;
            int i3;
            View view;
            int i4;
            DcmMascotViewContainer dcmMascotViewContainer;
            long j;
            long j2;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            boolean goingToFullShade = ((StatusBarStateControllerImpl) notificationPanelViewController.mStatusBarStateController).goingToFullShade();
            KeyguardStateControllerImpl keyguardStateControllerImpl = notificationPanelViewController.mKeyguardStateController;
            boolean z2 = keyguardStateControllerImpl.mKeyguardFadingAway;
            int i5 = notificationPanelViewController.mBarState;
            boolean z3 = i == 1;
            NotificationPanelView notificationPanelView = notificationPanelViewController.mView;
            notificationPanelView.mStatusBarState = i;
            if (notificationPanelViewController.mDozeParameters.mScreenOffAnimationController.shouldDelayKeyguardShow() && i5 == 0 && i == 1) {
                KeyguardStatusViewController keyguardStatusViewController = notificationPanelViewController.mKeyguardStatusViewController;
                KeyguardClockPositionAlgorithm.Result result = notificationPanelViewController.mClockPositionResult;
                keyguardStatusViewController.updatePosition(result.clockX, result.clockYFullyDozing, false, null);
            }
            notificationPanelViewController.mKeyguardStatusViewController.setKeyguardStatusViewVisibility(i, notificationPanelViewController.mBarState, z2, goingToFullShade);
            notificationPanelViewController.setKeyguardBottomAreaVisibility(i, goingToFullShade);
            notificationPanelViewController.mBarState = i;
            QuickSettingsController quickSettingsController = notificationPanelViewController.mQsController;
            quickSettingsController.mBarState = i;
            SecQuickSettingsController secQuickSettingsController = quickSettingsController.mSecQuickSettingsController;
            secQuickSettingsController.barState = i;
            secQuickSettingsController.expandQSAtOnceController.mShouldCloseAtOnce = false;
            boolean z4 = i == 1 && (i5 == 0 || i5 == 2);
            if (notificationPanelViewController.mSplitShadeEnabled && z4) {
                quickSettingsController.closeQs();
            }
            DcmMascotViewContainer dcmMascotViewContainer2 = notificationPanelViewController.mMascotViewContainer;
            if (i5 == 1 && (goingToFullShade || i == 2)) {
                if (keyguardStateControllerImpl.mKeyguardFadingAway) {
                    dcmMascotViewContainer = dcmMascotViewContainer2;
                    j = keyguardStateControllerImpl.mKeyguardFadingAwayDelay;
                    keyguardStateControllerImpl.getClass();
                    j2 = keyguardStateControllerImpl.mKeyguardFadingAwayDuration / 2;
                } else {
                    dcmMascotViewContainer = dcmMascotViewContainer2;
                    j = 0;
                    j2 = 360;
                }
                final KeyguardStatusBarViewController keyguardStatusBarViewController = notificationPanelViewController.mKeyguardStatusBarViewController;
                keyguardStatusBarViewController.getClass();
                keyguardStatusBarViewController.mLogger.buffer.log("KeyguardStatusBarViewController", LogLevel.DEBUG, "animating status bar out", (Throwable) null);
                androidx.core.animation.ValueAnimator ofFloat = androidx.core.animation.ValueAnimator.ofFloat(((KeyguardStatusBarView) keyguardStatusBarViewController.mView).getAlpha(), 0.0f);
                ofFloat.addUpdateListener(keyguardStatusBarViewController.mAnimatorUpdateListener);
                ofFloat.setStartDelay(j);
                ofFloat.setDuration(j2);
                ofFloat.setInterpolator(InterpolatorsAndroidX.LINEAR_OUT_SLOW_IN);
                ofFloat.addListener(new androidx.core.animation.AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.10
                    public C305610() {
                    }

                    @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
                    public final void onAnimationEnd$1(androidx.core.animation.Animator animator) {
                        AnimationProperties animationProperties = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
                        KeyguardStatusBarViewController keyguardStatusBarViewController2 = KeyguardStatusBarViewController.this;
                        ((KeyguardStatusBarView) keyguardStatusBarViewController2.mView).setVisibility(4);
                        ((KeyguardStatusBarView) keyguardStatusBarViewController2.mView).setAlpha(1.0f);
                        keyguardStatusBarViewController2.mKeyguardStatusBarAnimateAlpha = 1.0f;
                    }
                });
                ofFloat.start(false);
                quickSettingsController.updateMinHeight();
                quickSettingsController.updateNightMode(notificationPanelView.getVisibility());
                if (LsRune.KEYGUARD_DCM_LIVE_UX) {
                    dcmMascotViewContainer.setMascotViewVisible(4);
                }
            } else {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
                if (i5 == 2 && i == 1) {
                    notificationPanelViewController.mKeyguardStatusBarViewController.animateKeyguardStatusBarIn();
                    notificationStackScrollLayoutController.mView.resetScrollPosition();
                    quickSettingsController.updateNightMode(notificationPanelView.getVisibility());
                    if (LsRune.KEYGUARD_DCM_LIVE_UX) {
                        dcmMascotViewContainer2.setMascotViewVisible(notificationPanelViewController.mDozing ? 8 : 0);
                    }
                } else {
                    if (!(i5 == 0 && i == 1 && notificationPanelViewController.mScreenOffAnimationController.isKeyguardShowDelayed())) {
                        ShadeLogger shadeLogger = notificationPanelViewController.mShadeLog;
                        if (z3) {
                            shadeLogger.m190v("Updating keyguard status bar state to visible");
                        } else {
                            shadeLogger.m190v("Updating keyguard status bar state to invisible");
                        }
                        KeyguardStatusBarViewController keyguardStatusBarViewController2 = notificationPanelViewController.mKeyguardStatusBarViewController;
                        int i6 = keyguardStatusBarViewController2.mDisableStateTracker.isDisabled ? 4 : z3 ? 0 : 4;
                        ((KeyguardStatusBarView) keyguardStatusBarViewController2.mView).setAlpha(1.0f);
                        ((KeyguardStatusBarView) keyguardStatusBarViewController2.mView).setVisibility(i6);
                    }
                    if (!z3 || i5 == notificationPanelViewController.mBarState) {
                        i2 = 1;
                        if (i5 == 1 && i == 0) {
                            notificationPanelViewController.cancelHeightAnimator();
                        }
                    } else {
                        InterfaceC1922QS interfaceC1922QS = quickSettingsController.mQs;
                        if (interfaceC1922QS != null) {
                            interfaceC1922QS.hideImmediately();
                        }
                        if (((KeyguardEditModeControllerImpl) notificationPanelViewController.mKeyguardEditModeController).isEditMode) {
                            KeyguardEditModeAnimatorController keyguardEditModeAnimatorController = notificationPanelViewController.mKeyguardTouchAnimator.editModeAnimatorController;
                            Log.d("KeyguardEditModeAnimatorController", "dismissEditActivity " + keyguardEditModeAnimatorController.isEditMode());
                            if (keyguardEditModeAnimatorController.isEditMode()) {
                                keyguardEditModeAnimatorController.animate(false);
                            }
                            ((KeyguardWallpaperController) keyguardEditModeAnimatorController.keyguardWallpaper).setThumbnailVisibility(4);
                        }
                        i2 = 1;
                    }
                    if (i5 == i2 && i == 0 && (notificationStackScrollLayout = notificationStackScrollLayoutController.mView) != null) {
                        HashSet hashSet = notificationStackScrollLayout.mAnimationFinishedRunnables;
                        NotificationPanelViewController$$ExternalSyntheticLambda0 notificationPanelViewController$$ExternalSyntheticLambda0 = notificationPanelViewController.mHeadsUpExistenceChangedRunnable;
                        if (hashSet.contains(notificationPanelViewController$$ExternalSyntheticLambda0)) {
                            notificationPanelViewController$$ExternalSyntheticLambda0.run();
                        }
                    }
                }
            }
            notificationPanelViewController.mKeyguardStatusBarViewController.updateForHeadsUp();
            if (z3) {
                notificationPanelViewController.updateDozingVisibilities(false);
            }
            notificationPanelViewController.updateMaxDisplayedNotifications(false);
            notificationPanelViewController.mRecomputedMaxCountCallStack = "onStateChanged";
            ValueAnimator valueAnimator = notificationPanelViewController.mBottomAreaShadeAlphaAnimator;
            valueAnimator.cancel();
            if (notificationPanelViewController.mBarState == 2) {
                z = true;
                valueAnimator.setFloatValues(notificationPanelViewController.mBottomAreaShadeAlpha, 0.0f);
                valueAnimator.start();
            } else {
                z = true;
                notificationPanelViewController.mBottomAreaShadeAlpha = 1.0f;
            }
            quickSettingsController.updateQsState();
            if (QpRune.QUICK_TABLET_HORIZONTAL_PANEL_POSITION) {
                quickSettingsController.mSecQuickSettingsController.tabletHorizontalPanelPositionHelper.resetHorizontalPanelPosition(z);
            }
            int i7 = notificationPanelViewController.mBarState;
            com.android.systemui.keyguard.Log.m138d("NotificationPanelView", "onBarStateChanged() to " + i7);
            PluginLockMediator pluginLockMediator = notificationPanelViewController.mPluginLockMediator;
            PluginLockMediatorImpl pluginLockMediatorImpl = (PluginLockMediatorImpl) pluginLockMediator;
            pluginLockMediatorImpl.mBarState = i7;
            if (pluginLockMediatorImpl.mBasicListener != null) {
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    i3 = 1;
                    pluginLockMediatorImpl.mHandler.post(new PluginLockMediatorImpl$$ExternalSyntheticLambda2(pluginLockMediatorImpl, i7, i3));
                    if (i5 == i && i == i3 && (i4 = notificationPanelViewController.mPluginLockViewMode) != 0) {
                        notificationPanelViewController.setViewMode(i4);
                    }
                    if (notificationPanelViewController.mBarState == 0) {
                        PluginLockMediatorImpl pluginLockMediatorImpl2 = (PluginLockMediatorImpl) pluginLockMediator;
                        if (pluginLockMediatorImpl2.mIsSecureWindow) {
                            pluginLockMediatorImpl2.updateWindowSecureState(false);
                        }
                    }
                    view = notificationPanelViewController.mPluginLockStarContainer;
                    if (view != null) {
                        view.setVisibility(notificationPanelViewController.mBarState == 1 ? 0 : 8);
                    }
                    if (i == 0 || !notificationPanelViewController.mInstantExpanding) {
                    }
                    notificationPanelViewController.mInstantExpanding = false;
                    return;
                }
                pluginLockMediatorImpl.mBasicListener.onBarStateChanged(i7);
            }
            i3 = 1;
            if (i5 == i) {
                notificationPanelViewController.setViewMode(i4);
            }
            if (notificationPanelViewController.mBarState == 0) {
            }
            view = notificationPanelViewController.mPluginLockStarContainer;
            if (view != null) {
            }
            if (i == 0) {
            }
        }

        private StatusBarStateListener() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TouchHandler implements View.OnTouchListener, Gefingerpoken {
        public long mLastTouchDownTime = -1;
        public final SecTouchLogHelper mTouchLogHelper = new SecTouchLogHelper();

        public TouchHandler() {
        }

        public final boolean handleTouch(MotionEvent motionEvent) {
            boolean z;
            SecTouchLogHelper secTouchLogHelper = this.mTouchLogHelper;
            secTouchLogHelper.getClass();
            int action = motionEvent.getAction();
            String actionToString = MotionEvent.actionToString(action);
            if (action != 2) {
                secTouchLogHelper.printLogInternal("NPVC", "handleTouch", actionToString, "");
                secTouchLogHelper.shouldPrintOnInterceptTouchEventMove = true;
            } else if (secTouchLogHelper.shouldPrintOnInterceptTouchEventMove) {
                secTouchLogHelper.printLogInternal("NPVC", "handleTouch", actionToString, "");
                secTouchLogHelper.shouldPrintOnInterceptTouchEventMove = false;
            }
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            if (notificationPanelViewController.mInstantExpanding) {
                notificationPanelViewController.mShadeLog.logMotionEvent(motionEvent, "handleTouch: touch ignored due to instant expanding");
                SecPanelLoggerImpl secPanelLoggerImpl = (SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger;
                secPanelLoggerImpl.getClass();
                secPanelLoggerImpl.addPanelLog(motionEvent, "[NPVC]|[handleTouch]", new StringBuilder("mInstantExpanding"), false);
                return false;
            }
            if (notificationPanelViewController.mTouchDisabled && motionEvent.getActionMasked() != 3) {
                NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "handleTouch: non-cancel action, touch disabled");
                SecPanelLoggerImpl secPanelLoggerImpl2 = (SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger;
                secPanelLoggerImpl2.getClass();
                secPanelLoggerImpl2.addPanelLog(motionEvent, "[NPVC]|[handleTouch]", new StringBuilder("mTouchDisabled && action != CANCEL"), false);
                return false;
            }
            if (NotificationPanelViewController.this.mMotionAborted && motionEvent.getActionMasked() != 0) {
                if (NotificationPanelViewController.this.isFullyCollapsed() && motionEvent.isFromSource(8194)) {
                    NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                    if (!notificationPanelViewController2.mTracking) {
                        if (notificationPanelViewController2.mMotionAborted && motionEvent.getAction() == 0) {
                            NotificationPanelViewController.this.mMotionAborted = false;
                        }
                        if (motionEvent.getAction() == 1) {
                            NotificationPanelViewController.this.expand(true);
                        }
                        SecPanelLoggerImpl secPanelLoggerImpl3 = (SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger;
                        secPanelLoggerImpl3.getClass();
                        secPanelLoggerImpl3.addPanelLog(motionEvent, "[NPVC]|[handleTouch]", new StringBuilder("On expanding, single mouse click expands the panel instead of dragging"), true);
                        return true;
                    }
                }
                NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
                notificationPanelViewController3.mShadeLog.logMotionEventStatusBarState(motionEvent, ((StatusBarStateControllerImpl) notificationPanelViewController3.mStatusBarStateController).mState, "handleTouch: non-down action, motion was aborted");
                SecPanelLoggerImpl secPanelLoggerImpl4 = (SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger;
                secPanelLoggerImpl4.getClass();
                secPanelLoggerImpl4.addPanelLog(motionEvent, "[NPVC]|[handleTouch]", new StringBuilder("mMotionAborted && action != DOWN"), false);
                return false;
            }
            NotificationPanelViewController notificationPanelViewController4 = NotificationPanelViewController.this;
            if (!notificationPanelViewController4.mNotificationsDragEnabled) {
                if (notificationPanelViewController4.mTracking) {
                    notificationPanelViewController4.onTrackingStopped(true);
                }
                NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "handleTouch: drag not enabled");
                NotificationPanelViewController.this.mLogBuilder.setLength(0);
                StringBuilder sb = NotificationPanelViewController.this.mLogBuilder;
                sb.append("!mNotificationsDragEnabled");
                sb.append(", mTracking: ");
                sb.append(NotificationPanelViewController.this.mTracking);
                NotificationPanelViewController notificationPanelViewController5 = NotificationPanelViewController.this;
                ((SecPanelLoggerImpl) notificationPanelViewController5.mPanelLogger).addPanelLog(motionEvent, "[NPVC]|[handleTouch]", notificationPanelViewController5.mLogBuilder, false);
                return false;
            }
            if (notificationPanelViewController4.isFullyCollapsed() && motionEvent.isFromSource(8194) && !NotificationPanelViewController.this.mTracking) {
                if (motionEvent.getAction() == 1) {
                    NotificationPanelViewController.this.expand(true);
                }
                NotificationPanelViewController.this.mLogBuilder.setLength(0);
                NotificationPanelViewController.this.mLogBuilder.append("!isFullyCollapsed and from mouse");
                NotificationPanelViewController notificationPanelViewController6 = NotificationPanelViewController.this;
                ((SecPanelLoggerImpl) notificationPanelViewController6.mPanelLogger).addPanelLog(motionEvent, "[NPVC]|[handleTouch]", notificationPanelViewController6.mLogBuilder, true);
                return true;
            }
            int findPointerIndex = motionEvent.findPointerIndex(NotificationPanelViewController.this.mTrackingPointer);
            if (findPointerIndex < 0) {
                NotificationPanelViewController.this.mTrackingPointer = motionEvent.getPointerId(0);
                findPointerIndex = 0;
            }
            float x = motionEvent.getX(findPointerIndex);
            float y = motionEvent.getY(findPointerIndex);
            if (motionEvent.getActionMasked() == 0 || motionEvent.getActionMasked() == 2) {
                NotificationPanelViewController notificationPanelViewController7 = NotificationPanelViewController.this;
                if (notificationPanelViewController7.mExpectingSynthesizedDown) {
                    notificationPanelViewController7.mExpectingSynthesizedDown = false;
                } else if (notificationPanelViewController7.isFullyCollapsed() || notificationPanelViewController7.mBarState != 0) {
                    z = true;
                    notificationPanelViewController7.mGestureWaitForTouchSlop = z;
                    NotificationPanelViewController.this.mIgnoreXTouchSlop = true;
                }
                z = false;
                notificationPanelViewController7.mGestureWaitForTouchSlop = z;
                NotificationPanelViewController.this.mIgnoreXTouchSlop = true;
            }
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 0) {
                if (actionMasked != 1) {
                    if (actionMasked == 2) {
                        if (QuickStepContract.ALLOW_BACK_GESTURE_IN_SHADE) {
                            NotificationPanelViewController.this.getClass();
                        }
                        if (NotificationPanelViewController.this.isFullyCollapsed()) {
                            NotificationPanelViewController notificationPanelViewController8 = NotificationPanelViewController.this;
                            notificationPanelViewController8.mHasVibratedOnOpen = false;
                            float f = notificationPanelViewController8.mExpandedFraction;
                            ShadeLogger shadeLogger = notificationPanelViewController8.mShadeLog;
                            shadeLogger.getClass();
                            LogLevel logLevel = LogLevel.VERBOSE;
                            ShadeLogger$logHasVibrated$2 shadeLogger$logHasVibrated$2 = new Function1() { // from class: com.android.systemui.shade.ShadeLogger$logHasVibrated$2
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    LogMessage logMessage = (LogMessage) obj;
                                    return "hasVibratedOnOpen=" + logMessage.getBool1() + ", expansionFraction=" + logMessage.getDouble1();
                                }
                            };
                            LogBuffer logBuffer = shadeLogger.buffer;
                            LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$logHasVibrated$2, null);
                            obtain.setBool1(false);
                            obtain.setDouble1(f);
                            logBuffer.commit(obtain);
                        }
                        NotificationPanelViewController.m1690$$Nest$maddMovement(NotificationPanelViewController.this, motionEvent);
                        if (!NotificationPanelViewController.this.isFullyCollapsed()) {
                            NotificationPanelViewController.this.maybeVibrateOnOpening(true);
                        }
                        float f2 = y - NotificationPanelViewController.this.mInitialExpandY;
                        if (Math.abs(f2) > NotificationPanelViewController.this.getTouchSlop(motionEvent) && (Math.abs(f2) > Math.abs(x - NotificationPanelViewController.this.mInitialExpandX) || NotificationPanelViewController.this.mIgnoreXTouchSlop)) {
                            NotificationPanelViewController notificationPanelViewController9 = NotificationPanelViewController.this;
                            notificationPanelViewController9.mTouchSlopExceeded = true;
                            if (notificationPanelViewController9.mGestureWaitForTouchSlop && !notificationPanelViewController9.mTracking && !notificationPanelViewController9.mCollapsedAndHeadsUpOnDown) {
                                if (notificationPanelViewController9.mInitialOffsetOnTouch != 0.0f) {
                                    NotificationPanelViewController.m1695$$Nest$mstartExpandMotion(notificationPanelViewController9, x, y, false, notificationPanelViewController9.mExpandedHeight);
                                    f2 = 0.0f;
                                }
                                NotificationPanelViewController.this.cancelHeightAnimator();
                                NotificationPanelViewController.this.onTrackingStarted();
                            }
                        }
                        float max = Math.max(Math.max(0.0f, NotificationPanelViewController.this.mInitialOffsetOnTouch + f2), NotificationPanelViewController.this.mMinExpandHeight);
                        if ((-f2) >= NotificationPanelViewController.this.getFalsingThreshold()) {
                            NotificationPanelViewController notificationPanelViewController10 = NotificationPanelViewController.this;
                            notificationPanelViewController10.mTouchAboveFalsingThreshold = true;
                            float f3 = x - notificationPanelViewController10.mInitialExpandX;
                            float f4 = y - notificationPanelViewController10.mInitialExpandY;
                            notificationPanelViewController10.mUpwardsWhenThresholdReached = f4 < 0.0f && Math.abs(f4) >= Math.abs(f3);
                        }
                        NotificationPanelViewController notificationPanelViewController11 = NotificationPanelViewController.this;
                        if ((notificationPanelViewController11.mGestureWaitForTouchSlop && !notificationPanelViewController11.mTracking) || notificationPanelViewController11.mBlockingExpansionForCurrentTouch || notificationPanelViewController11.mQsController.isTrackingBlocked()) {
                            NotificationPanelViewController.this.mLogBuilder.setLength(0);
                            StringBuilder sb2 = NotificationPanelViewController.this.mLogBuilder;
                            sb2.append("Panel is Not Being handled. :: ");
                            sb2.append("mGestureWaitForTouchSlop : ");
                            sb2.append(NotificationPanelViewController.this.mGestureWaitForTouchSlop);
                            sb2.append(", mTracking : ");
                            sb2.append(NotificationPanelViewController.this.mTracking);
                            sb2.append(", mBlockingExpansionForCurrentTouch:");
                            sb2.append(NotificationPanelViewController.this.mBlockingExpansionForCurrentTouch);
                            sb2.append(", mQsController.isTrackingBlocked():");
                            sb2.append(NotificationPanelViewController.this.mQsController.isTrackingBlocked());
                            Log.d("NotificationPanelView", NotificationPanelViewController.this.mLogBuilder.toString());
                        } else {
                            NotificationPanelViewController notificationPanelViewController12 = NotificationPanelViewController.this;
                            AmbientState ambientState = notificationPanelViewController12.mAmbientState;
                            boolean z2 = f2 <= 0.0f;
                            if (!z2 && ambientState.mIsSwipingUp) {
                                ambientState.mIsFlingRequiredAfterLockScreenSwipeUp = true;
                            }
                            ambientState.mIsSwipingUp = z2;
                            notificationPanelViewController12.setExpandedHeightInternal(max);
                        }
                    } else if (actionMasked != 3) {
                        if (actionMasked == 5) {
                            NotificationPanelViewController notificationPanelViewController13 = NotificationPanelViewController.this;
                            notificationPanelViewController13.mShadeLog.logMotionEventStatusBarState(motionEvent, ((StatusBarStateControllerImpl) notificationPanelViewController13.mStatusBarStateController).mState, "handleTouch: pointer down action");
                            NotificationPanelViewController notificationPanelViewController14 = NotificationPanelViewController.this;
                            VibrationEffect vibrationEffect = NotificationPanelViewController.ADDITIONAL_TAP_REQUIRED_VIBRATION_EFFECT;
                            notificationPanelViewController14.getClass();
                            NotificationPanelViewController notificationPanelViewController15 = NotificationPanelViewController.this;
                            if (((StatusBarStateControllerImpl) notificationPanelViewController15.mStatusBarStateController).mState == 1) {
                                notificationPanelViewController15.mMotionAborted = true;
                                NotificationPanelViewController.m1691$$Nest$mendMotionEvent(notificationPanelViewController15, motionEvent, x, y, true);
                                SecPanelLoggerImpl secPanelLoggerImpl5 = (SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger;
                                secPanelLoggerImpl5.getClass();
                                secPanelLoggerImpl5.addPanelLog(motionEvent, "[NPVC]|[handleTouch]", new StringBuilder("!isTrackpadMotionEvent && SB state == KEYGUARD"), false);
                                return false;
                            }
                        } else if (actionMasked == 6) {
                            NotificationPanelViewController notificationPanelViewController16 = NotificationPanelViewController.this;
                            VibrationEffect vibrationEffect2 = NotificationPanelViewController.ADDITIONAL_TAP_REQUIRED_VIBRATION_EFFECT;
                            notificationPanelViewController16.getClass();
                            int pointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
                            if (NotificationPanelViewController.this.mTrackingPointer == pointerId) {
                                int i = motionEvent.getPointerId(0) != pointerId ? 0 : 1;
                                float y2 = motionEvent.getY(i);
                                float x2 = motionEvent.getX(i);
                                NotificationPanelViewController.this.mTrackingPointer = motionEvent.getPointerId(i);
                                NotificationPanelViewController notificationPanelViewController17 = NotificationPanelViewController.this;
                                notificationPanelViewController17.mHandlingPointerUp = true;
                                NotificationPanelViewController.m1695$$Nest$mstartExpandMotion(notificationPanelViewController17, x2, y2, true, notificationPanelViewController17.mExpandedHeight);
                                NotificationPanelViewController.this.mHandlingPointerUp = false;
                            }
                        }
                    }
                }
                NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "onTouch: up/cancel action");
                NotificationPanelViewController.m1690$$Nest$maddMovement(NotificationPanelViewController.this, motionEvent);
                NotificationPanelViewController.m1691$$Nest$mendMotionEvent(NotificationPanelViewController.this, motionEvent, x, y, false);
                if (NotificationPanelViewController.this.mHeightAnimator == null) {
                    if (motionEvent.getActionMasked() == 1) {
                        if (NotificationPanelViewController.this.mQsController.mInteractionJankMonitor != null) {
                            InteractionJankMonitor.getInstance().end(0);
                        }
                    } else if (NotificationPanelViewController.this.mQsController.mInteractionJankMonitor != null) {
                        InteractionJankMonitor.getInstance().cancel(0);
                    }
                }
            } else {
                if (QuickStepContract.ALLOW_BACK_GESTURE_IN_SHADE) {
                    NotificationPanelViewController.this.getClass();
                }
                NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "onTouch: down action");
                NotificationPanelViewController notificationPanelViewController18 = NotificationPanelViewController.this;
                NotificationPanelViewController.m1695$$Nest$mstartExpandMotion(notificationPanelViewController18, x, y, false, notificationPanelViewController18.mExpandedHeight);
                NotificationPanelViewController notificationPanelViewController19 = NotificationPanelViewController.this;
                notificationPanelViewController19.mMinExpandHeight = 0.0f;
                notificationPanelViewController19.mPanelClosedOnDown = notificationPanelViewController19.isFullyCollapsed();
                NotificationPanelViewController notificationPanelViewController20 = NotificationPanelViewController.this;
                ShadeLogger shadeLogger2 = notificationPanelViewController20.mShadeLog;
                boolean z3 = notificationPanelViewController20.mPanelClosedOnDown;
                float f5 = notificationPanelViewController20.mExpandedFraction;
                shadeLogger2.getClass();
                LogLevel logLevel2 = LogLevel.VERBOSE;
                ShadeLogger$logPanelClosedOnDown$2 shadeLogger$logPanelClosedOnDown$2 = ShadeLogger$logPanelClosedOnDown$2.INSTANCE;
                LogBuffer logBuffer2 = shadeLogger2.buffer;
                LogMessage obtain2 = logBuffer2.obtain("systemui.shade", logLevel2, shadeLogger$logPanelClosedOnDown$2, null);
                obtain2.setStr1("handle down touch");
                obtain2.setBool1(z3);
                obtain2.setDouble1(f5);
                logBuffer2.commit(obtain2);
                NotificationPanelViewController notificationPanelViewController21 = NotificationPanelViewController.this;
                notificationPanelViewController21.mHasLayoutedSinceDown = false;
                notificationPanelViewController21.mUpdateFlingOnLayout = false;
                notificationPanelViewController21.mMotionAborted = false;
                ((SystemClockImpl) notificationPanelViewController21.mSystemClock).getClass();
                notificationPanelViewController21.mDownTime = android.os.SystemClock.uptimeMillis();
                NotificationPanelViewController notificationPanelViewController22 = NotificationPanelViewController.this;
                notificationPanelViewController22.mTouchAboveFalsingThreshold = false;
                notificationPanelViewController22.mCollapsedAndHeadsUpOnDown = notificationPanelViewController22.isFullyCollapsed() && NotificationPanelViewController.this.mHeadsUpManager.mHasPinnedNotification;
                NotificationPanelViewController.m1690$$Nest$maddMovement(NotificationPanelViewController.this, motionEvent);
                NotificationPanelViewController notificationPanelViewController23 = NotificationPanelViewController.this;
                boolean z4 = (notificationPanelViewController23.mHeightAnimator == null || notificationPanelViewController23.mHintAnimationRunning || notificationPanelViewController23.mIsSpringBackAnimation) ? false : true;
                if (!notificationPanelViewController23.mGestureWaitForTouchSlop || z4) {
                    notificationPanelViewController23.mTouchSlopExceeded = z4 || notificationPanelViewController23.mTouchSlopExceededBeforeDown;
                    notificationPanelViewController23.cancelHeightAnimator();
                    NotificationPanelViewController.this.onTrackingStarted();
                }
                NotificationPanelViewController.this.mLogBuilder.setLength(0);
                StringBuilder sb3 = NotificationPanelViewController.this.mLogBuilder;
                sb3.append("ACTION_DOWN: ");
                sb3.append("isFullyCollapsed:");
                sb3.append(NotificationPanelViewController.this.isFullyCollapsed());
                sb3.append(", hasPinnedHeadsUp:");
                sb3.append(NotificationPanelViewController.this.mHeadsUpManager.mHasPinnedNotification);
                sb3.append(", isBouncerShowing:");
                sb3.append(((CentralSurfacesImpl) NotificationPanelViewController.this.mCentralSurfaces).mBouncerShowing);
                sb3.append(", mGestureWaitForTouchSlop:");
                sb3.append(NotificationPanelViewController.this.mGestureWaitForTouchSlop);
                sb3.append(", mIgnoreXTouchSlop:");
                sb3.append(NotificationPanelViewController.this.mIgnoreXTouchSlop);
                Log.d("NotificationPanelView", NotificationPanelViewController.this.mLogBuilder.toString());
                if (NotificationPanelViewController.this.isFullyCollapsed()) {
                    NotificationPanelViewController notificationPanelViewController24 = NotificationPanelViewController.this;
                    if (!notificationPanelViewController24.mHeadsUpManager.mHasPinnedNotification && !((CentralSurfacesImpl) notificationPanelViewController24.mCentralSurfaces).mBouncerShowing) {
                        notificationPanelViewController24.updateExpansionAndVisibility();
                        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) notificationPanelViewController24.mCentralSurfaces;
                        DisplayMetrics displayMetrics = centralSurfacesImpl.mDisplayMetrics;
                        float f6 = displayMetrics.widthPixels;
                        float f7 = displayMetrics.heightPixels;
                        int rotation = centralSurfacesImpl.mDisplay.getRotation();
                        int x3 = (int) ((motionEvent.getX() / f6) * 100.0f);
                        int y3 = (int) ((motionEvent.getY() / f7) * 100.0f);
                        LockscreenGestureLogger lockscreenGestureLogger = notificationPanelViewController24.mLockscreenGestureLogger;
                        lockscreenGestureLogger.getClass();
                        lockscreenGestureLogger.mMetricsLogger.write(new LogMaker(1328).setType(4).addTaggedData(1326, Integer.valueOf(x3)).addTaggedData(1327, Integer.valueOf(y3)).addTaggedData(1329, Integer.valueOf(rotation)));
                        new UiEventLoggerImpl().log(LockscreenGestureLogger.LockscreenUiEvent.LOCKSCREEN_UNLOCKED_NOTIFICATION_PANEL_EXPAND);
                    }
                }
            }
            if (motionEvent.getActionMasked() == 0) {
                NotificationPanelViewController.this.mLogBuilder.setLength(0);
                StringBuilder sb4 = NotificationPanelViewController.this.mLogBuilder;
                sb4.append("FINAL: onTouch(ACTION_DOWN): ");
                sb4.append("mGestureWaitForTouchSlop:");
                sb4.append(NotificationPanelViewController.this.mGestureWaitForTouchSlop);
                sb4.append(", mTracking:");
                sb4.append(NotificationPanelViewController.this.mTracking);
                sb4.append(", isFullyCollapsed:");
                sb4.append(NotificationPanelViewController.this.isFullyCollapsed());
                sb4.append(", isFullyExpanded:");
                sb4.append(NotificationPanelViewController.this.isFullyExpanded());
                NotificationPanelViewController notificationPanelViewController25 = NotificationPanelViewController.this;
                ((SecPanelLoggerImpl) notificationPanelViewController25.mPanelLogger).addPanelLog(motionEvent, "[NPVC]|[handleTouch]", notificationPanelViewController25.mLogBuilder, !notificationPanelViewController25.mGestureWaitForTouchSlop || notificationPanelViewController25.mTracking);
            }
            NotificationPanelViewController notificationPanelViewController26 = NotificationPanelViewController.this;
            return !notificationPanelViewController26.mGestureWaitForTouchSlop || notificationPanelViewController26.mTracking;
        }

        /* JADX WARN: Removed duplicated region for block: B:107:0x02a0  */
        /* JADX WARN: Removed duplicated region for block: B:155:0x0538  */
        /* JADX WARN: Removed duplicated region for block: B:176:0x03b2  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x00a9  */
        @Override // com.android.systemui.Gefingerpoken
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            View view;
            LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController;
            boolean z;
            EdgeBackGestureHandler edgeBackGestureHandler;
            boolean isBackGestureAllowed;
            boolean z2;
            int pointerId;
            boolean z3;
            int pointerId2;
            boolean z4;
            int i;
            NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "NPVC onInterceptTouchEvent");
            this.mTouchLogHelper.printOnInterceptTouchEventLog(motionEvent, "NPVC", "");
            boolean z5 = false;
            if (NotificationPanelViewController.this.mQsController.disallowTouches()) {
                NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "NPVC not intercepting touch, panel touches disallowed");
                ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcInterceptTouchLog(motionEvent, "disallowPanelTouches", false);
                return false;
            }
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            if (notificationPanelViewController.mDetailViewCollapseAnimating || notificationPanelViewController.mQsExpandedViewCollapseAnimating) {
                ((SecPanelLoggerImpl) notificationPanelViewController.mPanelLogger).addNpvcInterceptTouchLog(motionEvent, "mDetailViewCollapseAnimating : " + NotificationPanelViewController.this.mDetailViewCollapseAnimating + " mQsExpandedViewCollapseAnimating : " + NotificationPanelViewController.this.mQsExpandedViewCollapseAnimating, true);
                return true;
            }
            if (LsRune.AOD_FULLSCREEN && notificationPanelViewController.mUnlockedScreenOffAnimationController.isAnimationPlaying()) {
                ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcInterceptTouchLog(motionEvent, "unlockedScreenOff animation playing", true);
                return true;
            }
            if (LsRune.LOCKUI_MULTI_USER) {
                NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                if (notificationPanelViewController2.mKeyguardUserSwitcherController != null && ((StatusBarStateControllerImpl) notificationPanelViewController2.mStatusBarStateController).mState == 1) {
                    int x = (int) motionEvent.getX();
                    int y = (int) motionEvent.getY();
                    KeyguardUserSwitcherController keyguardUserSwitcherController = NotificationPanelViewController.this.mKeyguardUserSwitcherController;
                    KeyguardUserSwitcherListView keyguardUserSwitcherListView = keyguardUserSwitcherController.mListView;
                    if (keyguardUserSwitcherListView != null && keyguardUserSwitcherListView.getVisibility() == 0) {
                        int[] iArr = new int[2];
                        keyguardUserSwitcherController.mListView.getLocationInWindow(iArr);
                        int i2 = iArr[0];
                        if (x >= i2 && x <= keyguardUserSwitcherController.mListView.getWidth() + i2 && y >= (i = iArr[1]) && y <= keyguardUserSwitcherController.mListView.getHeight() + i) {
                            z4 = true;
                            if (z4) {
                                ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcInterceptTouchLog(motionEvent, "LsRune.LOCKUI_MULTI_USER", false);
                                return false;
                            }
                        }
                    }
                    z4 = false;
                    if (z4) {
                    }
                }
            }
            NotificationPanelViewController.m1693$$Nest$minitDownStates(NotificationPanelViewController.this, motionEvent);
            NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
            if (((CentralSurfacesImpl) notificationPanelViewController3.mCentralSurfaces).mBouncerShowing) {
                notificationPanelViewController3.mShadeLog.m190v("NotificationPanelViewController MotionEvent intercepted: bouncer is showing");
                ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcInterceptTouchLog(motionEvent, "isBouncerShowing", true);
                return true;
            }
            if (notificationPanelViewController3.mCommandQueue.panelsEnabled()) {
                if ((!(NotificationPanelViewController.this.mNotificationStackScrollLayoutController.mLongPressedView != null) || motionEvent.getAction() == 0) && NotificationPanelViewController.this.mHeadsUpTouchHelper.onInterceptTouchEvent(motionEvent)) {
                    NotificationPanelViewController.this.mMetricsLogger.count("panel_open", 1);
                    NotificationPanelViewController.this.mMetricsLogger.count("panel_open_peek", 1);
                    NotificationPanelViewController.this.mShadeLog.m190v("NotificationPanelViewController MotionEvent intercepted: HeadsUpTouchHelper");
                    ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcInterceptTouchLog(motionEvent, "no long press and HUN consume", true);
                    return true;
                }
            }
            NotificationPanelViewController notificationPanelViewController4 = NotificationPanelViewController.this;
            if (notificationPanelViewController4.mHeadsUpTouchHelper.mTouchingHeadsUpView) {
                if ((notificationPanelViewController4.mNotificationStackScrollLayoutController.mLongPressedView != null) && motionEvent.getAction() == 2) {
                    return false;
                }
            }
            NotificationPanelViewController notificationPanelViewController5 = NotificationPanelViewController.this;
            final SecQuickSettingsController secQuickSettingsController = notificationPanelViewController5.mQsController.mSecQuickSettingsController;
            secQuickSettingsController.getClass();
            Runnable runnable = new Runnable() { // from class: com.android.systemui.shade.SecQuickSettingsController$interceptMediaTouchEvent$1
                @Override // java.lang.Runnable
                public final void run() {
                    SecQuickSettingsController.this.trackingRunnable.run();
                    ((NotificationPanelViewController) SecQuickSettingsController.this.panelViewControllerLazy.get()).notifyExpandingFinished();
                    SecQuickSettingsController.this.updateInitialHeightOnTouchRunnable.run();
                }
            };
            SecQsMediaTouchHelper secQsMediaTouchHelper = secQuickSettingsController.mediaTouchHelper;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = secQsMediaTouchHelper.notificationStackScrollLayoutController;
            if (!(notificationStackScrollLayoutController.mView.mOwnScrollY > 0)) {
                secQsMediaTouchHelper.updateMediaPlayerBar(motionEvent, null);
                if (motionEvent.getActionMasked() == 0) {
                    secQsMediaTouchHelper.actionDownStartInMediaPlayer = secQsMediaTouchHelper.isInMediaPlayer(motionEvent.getX(), motionEvent.getY());
                }
                if (secQsMediaTouchHelper.actionDownStartInMediaPlayer) {
                    MediaType mediaType = MediaType.QS;
                    SecMediaHost secMediaHost = secQsMediaTouchHelper.mediaHost;
                    if (secMediaHost.getMediaPlayerSize(mediaType) != 0 && !notificationPanelViewController5.isOnKeyguard()) {
                        int preparePointerIndex = secQsMediaTouchHelper.preparePointerIndex(motionEvent);
                        float x2 = motionEvent.getX(preparePointerIndex);
                        float y2 = motionEvent.getY(preparePointerIndex);
                        secQsMediaTouchHelper.trackMovementConsumer.accept(motionEvent);
                        int actionMasked = motionEvent.getActionMasked();
                        DoubleSupplier doubleSupplier = secQsMediaTouchHelper.currentQsVelocitySupplier;
                        if (actionMasked != 0) {
                            if (actionMasked != 1) {
                                if (actionMasked == 2) {
                                    float asDouble = y2 - ((float) secQsMediaTouchHelper.initialTouchYSupplier.getAsDouble());
                                    secQsMediaTouchHelper.mediaDraggedHeight = asDouble;
                                    if (Math.abs(asDouble) > Float.valueOf(notificationPanelViewController5.getTouchSlop(motionEvent)).floatValue() && Math.abs(secQsMediaTouchHelper.mediaDraggedHeight) > Math.abs(x2 - ((float) secQsMediaTouchHelper.initialTouchXSupplier.getAsDouble()))) {
                                        NotificationPanelView notificationPanelView = notificationPanelViewController5.mView;
                                        notificationPanelView.getParent().requestDisallowInterceptTouchEvent(true);
                                        secQsMediaTouchHelper.updateInitialTouchPosition(x2, y2);
                                        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                                        notificationStackScrollLayout.cancelLongPress();
                                        int preparePointerIndex2 = secQsMediaTouchHelper.preparePointerIndex(motionEvent);
                                        float x3 = motionEvent.getX(preparePointerIndex2);
                                        float y3 = motionEvent.getY(preparePointerIndex2);
                                        if (!(secMediaHost.mPlayerBarExpandHelper.playerBarState == 1)) {
                                            if (secQsMediaTouchHelper.mediaDraggedHeight > 0.0f) {
                                                secQsMediaTouchHelper.mediaPlayerExpanding = true;
                                            } else {
                                                if (Math.min(notificationStackScrollLayout.mMaxLayoutHeight, notificationStackScrollLayout.mCurrentStackHeight) < notificationStackScrollLayout.mContentHeight) {
                                                    secQsMediaTouchHelper.mediaPlayerScrolling = true;
                                                    notificationStackScrollLayout.mShouldMediaPlayerDraggingStarted = true;
                                                }
                                            }
                                            z3 = true;
                                        } else if (secQsMediaTouchHelper.mediaDraggedHeight > 0.0f) {
                                            notificationPanelView.getParent().requestDisallowInterceptTouchEvent(true);
                                            runnable.run();
                                            secQsMediaTouchHelper.updateInitialTouchPosition(x3, y3);
                                            notificationStackScrollLayout.cancelLongPress();
                                        } else {
                                            secQsMediaTouchHelper.mediaPlayerExpanding = true;
                                            z3 = true;
                                        }
                                        if (z3) {
                                            SecQsMediaTouchHelper.log(motionEvent, new SecQsMediaTouchHelper$onInterceptTouchEvent$1((SecPanelLogger) secQsMediaTouchHelper.panelLogger$delegate.getValue()), "");
                                            z5 = true;
                                            if (motionEvent.getActionMasked() != 3 || motionEvent.getActionMasked() == 1) {
                                                secQsMediaTouchHelper.actionDownStartInMediaPlayer = false;
                                            }
                                        }
                                    }
                                } else if (actionMasked != 3) {
                                    if (actionMasked == 6 && secQsMediaTouchHelper.trackingPointerSupplier.getAsInt() == (pointerId2 = motionEvent.getPointerId(motionEvent.getActionIndex()))) {
                                        int i3 = motionEvent.getPointerId(0) != pointerId2 ? 0 : 1;
                                        secQsMediaTouchHelper.updateInitialTouchPosition(motionEvent.getX(i3), motionEvent.getY(i3));
                                    }
                                }
                            }
                            secQsMediaTouchHelper.mediaPlayerExpanding = false;
                            secQsMediaTouchHelper.mediaPlayerScrolling = false;
                            z3 = false | secMediaHost.mPlayerBarExpandHelper.setTracking((float) doubleSupplier.getAsDouble(), false);
                            secQsMediaTouchHelper.clearVelocityTrackerRunnable.run();
                            if (z3) {
                            }
                        } else {
                            secQsMediaTouchHelper.updateInitialTouchPosition(x2, y2);
                            secMediaHost.mPlayerBarExpandHelper.setTracking((float) doubleSupplier.getAsDouble(), true);
                            secQsMediaTouchHelper.initVelocityTrackerRunnable.run();
                        }
                    }
                    z3 = false;
                    if (z3) {
                    }
                }
                z5 = false;
                if (motionEvent.getActionMasked() != 3) {
                }
                secQsMediaTouchHelper.actionDownStartInMediaPlayer = false;
            }
            if (z5) {
                return true;
            }
            NotificationPanelViewController notificationPanelViewController6 = NotificationPanelViewController.this;
            if (!notificationPanelViewController6.mQsController.shouldQuickSettingsIntercept(notificationPanelViewController6.mDownX, notificationPanelViewController6.mDownY, 0.0f) && NotificationPanelViewController.this.mPulseExpansionHandler.onInterceptTouchEvent(motionEvent)) {
                NotificationPanelViewController.this.mShadeLog.m190v("NotificationPanelViewController MotionEvent intercepted: PulseExpansionHandler");
                ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcInterceptTouchLog(motionEvent, "!shouldQuickSettingsIntercept and pulseExpansion consume", true);
                return true;
            }
            if (!NotificationPanelViewController.this.isFullyCollapsed()) {
                QuickSettingsController quickSettingsController = NotificationPanelViewController.this.mQsController;
                int findPointerIndex = motionEvent.findPointerIndex(quickSettingsController.mTrackingPointer);
                if (findPointerIndex < 0) {
                    quickSettingsController.mTrackingPointer = motionEvent.getPointerId(0);
                    findPointerIndex = 0;
                }
                float x4 = motionEvent.getX(findPointerIndex);
                float y4 = motionEvent.getY(findPointerIndex);
                int actionMasked2 = motionEvent.getActionMasked();
                NotificationPanelView notificationPanelView2 = quickSettingsController.mPanelView;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = quickSettingsController.mNotificationStackScrollLayoutController;
                Lazy lazy = quickSettingsController.mPanelViewControllerLazy;
                SecQuickSettingsController secQuickSettingsController2 = quickSettingsController.mSecQuickSettingsController;
                ShadeLogger shadeLogger = quickSettingsController.mShadeLog;
                if (actionMasked2 != 0) {
                    if (actionMasked2 != 1) {
                        if (actionMasked2 == 2) {
                            float f = y4 - quickSettingsController.mInitialTouchY;
                            quickSettingsController.trackMovement(motionEvent);
                            if (quickSettingsController.mTracking) {
                                quickSettingsController.setExpansionHeight(f + quickSettingsController.mInitialHeightOnTouch);
                                quickSettingsController.trackMovement(motionEvent);
                            } else {
                                float f2 = motionEvent.getClassification() == 1 ? quickSettingsController.mTouchSlop * quickSettingsController.mSlopMultiplier : quickSettingsController.mTouchSlop;
                                secQuickSettingsController2.getClass();
                                if (Math.abs(f) > f2) {
                                    boolean z6 = f < 0.0f;
                                    boolean z7 = f > 0.0f;
                                    if ((z6 && secQuickSettingsController2.canScrollUp) || (z7 && secQuickSettingsController2.canScrollDown)) {
                                        z2 = true;
                                        if (!z2) {
                                            if ((f > f2 || (f < (-f2) && quickSettingsController.mExpanded)) && Math.abs(f) > Math.abs(x4 - quickSettingsController.mInitialTouchX) && quickSettingsController.shouldQuickSettingsIntercept(quickSettingsController.mInitialTouchX, quickSettingsController.mInitialTouchY, f)) {
                                                notificationPanelView2.getParent().requestDisallowInterceptTouchEvent(true);
                                                shadeLogger.getClass();
                                                LogLevel logLevel = LogLevel.VERBOSE;
                                                ShadeLogger$onQsInterceptMoveQsTrackingEnabled$2 shadeLogger$onQsInterceptMoveQsTrackingEnabled$2 = new Function1() { // from class: com.android.systemui.shade.ShadeLogger$onQsInterceptMoveQsTrackingEnabled$2
                                                    @Override // kotlin.jvm.functions.Function1
                                                    public final Object invoke(Object obj) {
                                                        return "onQsIntercept: move action, QS tracking enabled. h = " + ((LogMessage) obj).getDouble1();
                                                    }
                                                };
                                                LogBuffer logBuffer = shadeLogger.buffer;
                                                LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$onQsInterceptMoveQsTrackingEnabled$2, null);
                                                obtain.setDouble1(f);
                                                logBuffer.commit(obtain);
                                                quickSettingsController.setTracking(true);
                                                quickSettingsController.traceQsJank(true, false);
                                                quickSettingsController.onExpansionStarted();
                                                ((NotificationPanelViewController) lazy.get()).notifyExpandingFinished();
                                                quickSettingsController.mInitialHeightOnTouch = quickSettingsController.mExpansionHeight;
                                                quickSettingsController.mInitialTouchY = y4;
                                                quickSettingsController.mInitialTouchX = x4;
                                                notificationStackScrollLayoutController2.mView.cancelLongPress();
                                            } else {
                                                float f3 = quickSettingsController.mInitialTouchY;
                                                boolean z8 = quickSettingsController.mExpanded;
                                                boolean isKeyguardShowing = ((NotificationPanelViewController) lazy.get()).isKeyguardShowing();
                                                boolean isExpansionEnabled = quickSettingsController.isExpansionEnabled();
                                                long downTime = motionEvent.getDownTime();
                                                shadeLogger.getClass();
                                                LogLevel logLevel2 = LogLevel.VERBOSE;
                                                ShadeLogger$logQsTrackingNotStarted$2 shadeLogger$logQsTrackingNotStarted$2 = new Function1() { // from class: com.android.systemui.shade.ShadeLogger$logQsTrackingNotStarted$2
                                                    @Override // kotlin.jvm.functions.Function1
                                                    public final Object invoke(Object obj) {
                                                        LogMessage logMessage = (LogMessage) obj;
                                                        String str1 = logMessage.getStr1();
                                                        int int1 = logMessage.getInt1();
                                                        int int2 = logMessage.getInt2();
                                                        long long1 = logMessage.getLong1();
                                                        double double1 = logMessage.getDouble1();
                                                        boolean bool1 = logMessage.getBool1();
                                                        boolean bool2 = logMessage.getBool2();
                                                        boolean bool3 = logMessage.getBool3();
                                                        StringBuilder m92m = AbstractC0950x8906c950.m92m("QsTrackingNotStarted: downTime=", str1, ",initTouchY=", int1, ",y=");
                                                        m92m.append(int2);
                                                        m92m.append(",h=");
                                                        m92m.append(long1);
                                                        m92m.append(",slop=");
                                                        m92m.append(double1);
                                                        m92m.append(",qsExpanded=");
                                                        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m92m, bool1, ",keyguardShowing=", bool2, ",qsExpansion=");
                                                        m92m.append(bool3);
                                                        return m92m.toString();
                                                    }
                                                };
                                                LogBuffer logBuffer2 = shadeLogger.buffer;
                                                LogMessage obtain2 = logBuffer2.obtain("systemui.shade", logLevel2, shadeLogger$logQsTrackingNotStarted$2, null);
                                                obtain2.setInt1((int) f3);
                                                obtain2.setInt2((int) y4);
                                                obtain2.setLong1((long) f);
                                                obtain2.setDouble1(f2);
                                                obtain2.setBool1(z8);
                                                obtain2.setBool2(isKeyguardShowing);
                                                obtain2.setBool3(isExpansionEnabled);
                                                obtain2.setStr1(String.valueOf(downTime));
                                                logBuffer2.commit(obtain2);
                                            }
                                        }
                                    }
                                }
                                z2 = false;
                                if (!z2) {
                                }
                            }
                            if (r4 != 0) {
                                NotificationPanelViewController.this.getClass();
                                NotificationPanelViewController.this.mShadeLog.m190v("NotificationPanelViewController MotionEvent intercepted: QsIntercept");
                                ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcInterceptTouchLog(motionEvent, "!isFullyCollapsed and onIntercept", true);
                                return true;
                            }
                        } else if (actionMasked2 != 3) {
                            if (actionMasked2 == 6 && quickSettingsController.mTrackingPointer == (pointerId = motionEvent.getPointerId(motionEvent.getActionIndex()))) {
                                r4 = motionEvent.getPointerId(0) != pointerId ? 0 : 1;
                                quickSettingsController.mTrackingPointer = motionEvent.getPointerId(r4);
                                quickSettingsController.mInitialTouchX = motionEvent.getX(r4);
                                quickSettingsController.mInitialTouchY = motionEvent.getY(r4);
                            }
                        }
                        r4 = 0;
                        if (r4 != 0) {
                        }
                    }
                    quickSettingsController.trackMovement(motionEvent);
                    shadeLogger.logMotionEvent(motionEvent, "onQsIntercept: up action, QS tracking disabled");
                    quickSettingsController.setTracking(false);
                    secQuickSettingsController2.updateScrollableDirection(true);
                    r4 = 0;
                    if (r4 != 0) {
                    }
                } else {
                    quickSettingsController.mInitialTouchY = y4;
                    quickSettingsController.mInitialTouchX = x4;
                    secQuickSettingsController2.updateScrollableDirection(false);
                    VelocityTracker velocityTracker = quickSettingsController.mQsVelocityTracker;
                    if (velocityTracker != null) {
                        velocityTracker.recycle();
                    }
                    quickSettingsController.mQsVelocityTracker = VelocityTracker.obtain();
                    quickSettingsController.trackMovement(motionEvent);
                    float computeExpansionFraction = quickSettingsController.computeExpansionFraction();
                    boolean z9 = quickSettingsController.mExpanded;
                    if (secQuickSettingsController2.naviBarGestureMode == 2 && motionEvent.getAction() == 0) {
                        if ((secQuickSettingsController2.panelExpansionStateNotifier.mModel.panelOpenState == 2) && !z9) {
                            NavigationBarController navigationBarController = secQuickSettingsController2.navigationBarController;
                            navigationBarController.mDisplayTracker.getClass();
                            NavigationBarView navigationBarView = navigationBarController.getNavigationBarView(0);
                            if (BasicRune.NAVBAR_SUPPORT_TASKBAR && navigationBarController.mNavBarStateManager.isTaskBarEnabled(false)) {
                                EdgeBackGestureHandler edgeBackGestureHandler2 = navigationBarController.mTaskbarDelegate.mEdgeBackGestureHandler;
                                if (edgeBackGestureHandler2 != null) {
                                    isBackGestureAllowed = edgeBackGestureHandler2.isBackGestureAllowed(motionEvent);
                                    secQuickSettingsController2.isBackGestureAllowed = isBackGestureAllowed;
                                }
                                isBackGestureAllowed = false;
                                secQuickSettingsController2.isBackGestureAllowed = isBackGestureAllowed;
                            } else {
                                if (navigationBarView != null && (edgeBackGestureHandler = navigationBarView.mEdgeBackGestureHandler) != null) {
                                    isBackGestureAllowed = edgeBackGestureHandler.isBackGestureAllowed(motionEvent);
                                    secQuickSettingsController2.isBackGestureAllowed = isBackGestureAllowed;
                                }
                                isBackGestureAllowed = false;
                                secQuickSettingsController2.isBackGestureAllowed = isBackGestureAllowed;
                            }
                        }
                    }
                    if (!quickSettingsController.mSplitShadeEnabled) {
                        double d = computeExpansionFraction;
                        if (d > 0.0d && d < 1.0d) {
                            shadeLogger.logMotionEvent(motionEvent, "onQsIntercept: down action, QS partially expanded/collapsed");
                            r4 = 1;
                            if (r4 != 0) {
                            }
                        }
                    }
                    if (((NotificationPanelViewController) lazy.get()).isKeyguardShowing() && quickSettingsController.shouldQuickSettingsIntercept(quickSettingsController.mInitialTouchX, quickSettingsController.mInitialTouchY, 0.0f)) {
                        z = true;
                        notificationPanelView2.getParent().requestDisallowInterceptTouchEvent(true);
                    } else {
                        z = true;
                    }
                    if (quickSettingsController.mExpansionAnimator != null) {
                        quickSettingsController.mInitialHeightOnTouch = quickSettingsController.mExpansionHeight;
                        shadeLogger.logMotionEvent(motionEvent, "onQsIntercept: down action, QS tracking enabled");
                        quickSettingsController.setTracking(z);
                        quickSettingsController.traceQsJank(z, false);
                        notificationStackScrollLayoutController2.mView.cancelLongPress();
                    }
                    r4 = 0;
                    if (r4 != 0) {
                    }
                }
            }
            NotificationPanelViewController notificationPanelViewController7 = NotificationPanelViewController.this;
            boolean z10 = notificationPanelViewController7.mInstantExpanding;
            boolean z11 = notificationPanelViewController7.mNotificationsDragEnabled;
            if (z10 || !z11 || notificationPanelViewController7.mTouchDisabled) {
                boolean z12 = notificationPanelViewController7.mTouchDisabled;
                ShadeLogger shadeLogger2 = notificationPanelViewController7.mShadeLog;
                shadeLogger2.getClass();
                LogLevel logLevel3 = LogLevel.VERBOSE;
                ShadeLogger$logNotInterceptingTouchInstantExpanding$2 shadeLogger$logNotInterceptingTouchInstantExpanding$2 = new Function1() { // from class: com.android.systemui.shade.ShadeLogger$logNotInterceptingTouchInstantExpanding$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        LogMessage logMessage = (LogMessage) obj;
                        boolean bool1 = logMessage.getBool1();
                        boolean bool2 = logMessage.getBool2();
                        boolean bool3 = logMessage.getBool3();
                        StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("NPVC not intercepting touch, instantExpanding: ", bool1, ", !notificationsDragEnabled: ", bool2, ", touchDisabled: ");
                        m69m.append(bool3);
                        return m69m.toString();
                    }
                };
                LogBuffer logBuffer3 = shadeLogger2.buffer;
                LogMessage obtain3 = logBuffer3.obtain("systemui.shade", logLevel3, shadeLogger$logNotInterceptingTouchInstantExpanding$2, null);
                obtain3.setBool1(z10);
                obtain3.setBool2(!z11);
                obtain3.setBool3(z12);
                logBuffer3.commit(obtain3);
                NotificationPanelViewController.this.mLogBuilder.setLength(0);
                StringBuilder sb = NotificationPanelViewController.this.mLogBuilder;
                sb.append("[mInstantExpanding: ");
                sb.append(NotificationPanelViewController.this.mInstantExpanding);
                sb.append(" || !mNotificationsDragEnabled: ");
                sb.append(NotificationPanelViewController.this.mNotificationsDragEnabled);
                sb.append(" || mTouchDisabled: ");
                sb.append(NotificationPanelViewController.this.mTouchDisabled);
                sb.append("]");
                NotificationPanelViewController notificationPanelViewController8 = NotificationPanelViewController.this;
                ((SecPanelLoggerImpl) notificationPanelViewController8.mPanelLogger).addPanelLog(motionEvent, "[NPVC]|[InterceptTouch]", notificationPanelViewController8.mLogBuilder, false);
                return false;
            }
            if (notificationPanelViewController7.mMotionAborted && motionEvent.getActionMasked() != 0) {
                NotificationPanelViewController notificationPanelViewController9 = NotificationPanelViewController.this;
                notificationPanelViewController9.mShadeLog.logMotionEventStatusBarState(motionEvent, ((StatusBarStateControllerImpl) notificationPanelViewController9.mStatusBarStateController).mState, "NPVC MotionEvent not intercepted: non-down action, motion was aborted");
                ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcInterceptTouchLog(motionEvent, "mMotionAborted && action != DOWN", false);
                return false;
            }
            if (NotificationPanelViewController.this.mCommandQueue.panelsEnabled()) {
                NotificationPanelViewController notificationPanelViewController10 = NotificationPanelViewController.this;
                if (!notificationPanelViewController10.mQsController.mExpanded && (lockscreenNotificationIconsOnlyController = notificationPanelViewController10.mLockscreenNotificationIconsOnlyController) != null && lockscreenNotificationIconsOnlyController.isIconsOnlyInterceptTouchEvent(motionEvent)) {
                    ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcInterceptTouchLog(motionEvent, "LsRune.LOCKUI_NOTI_ICON_TYPE", true);
                    return true;
                }
            }
            NotificationPanelViewController notificationPanelViewController11 = NotificationPanelViewController.this;
            if (notificationPanelViewController11.mFullScreenModeEnabled) {
                ((SecPanelLoggerImpl) notificationPanelViewController11.mPanelLogger).addNpvcInterceptTouchLog(motionEvent, "LsRune.LOCKUI_FACE_WIDGET", false);
                return false;
            }
            if (notificationPanelViewController11.isInFaceWidgetContainer(motionEvent)) {
                NotificationPanelViewController notificationPanelViewController12 = NotificationPanelViewController.this;
                if (!notificationPanelViewController12.mQsController.mExpanded && notificationPanelViewController12.mBarState == 1) {
                    FaceWidgetContainerWrapper faceWidgetContainerWrapper = notificationPanelViewController12.mKeyguardStatusViewController.mKeyguardStatusBase;
                    int i4 = 8;
                    if (faceWidgetContainerWrapper != null && (view = faceWidgetContainerWrapper.mFaceWidgetContainer) != null) {
                        i4 = view.getVisibility();
                    }
                    if (i4 == 0) {
                        boolean onInterceptTouchEvent = NotificationPanelViewController.this.mKeyguardStatusViewController.onInterceptTouchEvent(motionEvent);
                        ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcInterceptTouchLog(motionEvent, "LsRune.LOCKUI_FACE_WIDGET", onInterceptTouchEvent);
                        return onInterceptTouchEvent;
                    }
                }
            }
            try {
                NotificationPanelViewController notificationPanelViewController13 = NotificationPanelViewController.this;
                if (notificationPanelViewController13.mLockStarEnabled && notificationPanelViewController13.isInLockStarContainer(motionEvent) && !NotificationPanelViewController.this.mQsController.getExpanded() && NotificationPanelViewController.this.mBarState == 1 && NotificationPanelViewController.this.mPluginLockStarContainer.getVisibility() == 0 && NotificationPanelViewController.this.mPluginLockStarManagerLazy.get() != null) {
                    boolean onInterceptTouchEvent2 = ((PluginLockStarManager) NotificationPanelViewController.this.mPluginLockStarManagerLazy.get()).onInterceptTouchEvent(motionEvent);
                    ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcInterceptTouchLog(motionEvent, "LsRune.PLUGIN_LOCK_STAR", onInterceptTouchEvent2);
                    return onInterceptTouchEvent2;
                }
            } catch (Throwable th) {
                VibrationEffect vibrationEffect = NotificationPanelViewController.ADDITIONAL_TAP_REQUIRED_VIBRATION_EFFECT;
                Log.e("NotificationPanelView", "onInterceptTouchEvent() error in LockStar - " + th.getMessage());
            }
            if (LsRune.LOCKUI_BOTTOM_USIM_TEXT && !NotificationPanelViewController.this.mQsController.getExpanded() && NotificationPanelViewController.this.mBarState == 1 && motionEvent.getActionMasked() == 0 && NotificationPanelViewController.this.mKeyguardBottomArea != null && NotificationPanelViewController.this.mKeyguardBottomArea.isInEmergencyButtonArea(motionEvent)) {
                ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcInterceptTouchLog(motionEvent, "LsRune.LOCKUI_BOTTOM_USIM_TEXT", false);
                NotificationPanelViewController.this.setMotionAborted();
                return false;
            }
            if (!NotificationPanelViewController.this.mQsController.getExpanded() && NotificationPanelViewController.this.mBarState == 1 && motionEvent.getActionMasked() == 0 && NotificationPanelViewController.this.mKeyguardIndicationController != null && NotificationPanelViewController.this.mKeyguardIndicationController.isInLifeStyleArea(motionEvent)) {
                ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcInterceptTouchLog(motionEvent, "LsRune.LOCKUI_LIFE_STYLE", false);
                NotificationPanelViewController.this.setMotionAborted();
                return false;
            }
            if (NotificationPanelViewController.this.mPluginLock != null && NotificationPanelViewController.this.mPluginLock.getTouchManager() != null && !NotificationPanelViewController.this.mQsController.getExpanded() && ((StatusBarStateControllerImpl) NotificationPanelViewController.this.mStatusBarStateController).getState() == 1) {
                if (NotificationPanelViewController.this.mPluginLock.getTouchManager().isTouchOnItemViewArea(motionEvent)) {
                    if (motionEvent.getActionMasked() == 0) {
                        NotificationPanelViewController.this.mPluginLock.getTouchManager().setIntercept(true);
                    }
                    ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcInterceptTouchLog(motionEvent, "LsRune.PLUGIN_LOCK", false);
                    return false;
                }
                NotificationPanelViewController notificationPanelViewController14 = NotificationPanelViewController.this;
                if ((notificationPanelViewController14.mPluginLockViewMode == 0) && notificationPanelViewController14.mPluginLock.getTouchManager().isIntercepting()) {
                    NotificationPanelViewController.this.mPluginLock.getTouchManager().setIntercept(false);
                }
            }
            int actionMasked3 = motionEvent.getActionMasked();
            NotificationPanelViewController notificationPanelViewController15 = NotificationPanelViewController.this;
            notificationPanelViewController15.mQsExpandedOnTouchDown = notificationPanelViewController15.mQsController.getExpanded() || NotificationPanelViewController.this.mQsController.getFullyExpanded();
            if (NotificationPanelViewController.this.mQsController.getExpanded() || NotificationPanelViewController.this.mBarState != 1 || !NotificationPanelViewController.this.isTouchOnEmptyArea(motionEvent)) {
                NotificationPanelViewController.this.mKeyguardTouchAnimator.setIntercept(false);
            } else if (actionMasked3 == 0) {
                NotificationPanelViewController.this.mKeyguardTouchAnimator.setIntercept(true);
            }
            if (motionEvent.getAction() != 2) {
                LogUtil.m223d("KeyguardTouchAnimator", "intercepted: action=%d mQsExpanded=%b, mQsFullyExpanded=%b", Integer.valueOf(actionMasked3), Boolean.valueOf(NotificationPanelViewController.this.mQsController.getExpanded()), Boolean.valueOf(NotificationPanelViewController.this.mQsController.getFullyExpanded()));
            }
            if (NotificationPanelViewController.this.mKeyguardTouchAnimator.onInterceptTouchEvent(motionEvent)) {
                ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcInterceptTouchLog(motionEvent, "LsRune.KEYGUARD_ALL_DIRECTIONS_SWIPE_UNLOCK", true);
                return true;
            }
            int findPointerIndex2 = motionEvent.findPointerIndex(NotificationPanelViewController.this.mTrackingPointer);
            if (findPointerIndex2 < 0) {
                NotificationPanelViewController.this.mTrackingPointer = motionEvent.getPointerId(0);
                findPointerIndex2 = 0;
            }
            float x5 = motionEvent.getX(findPointerIndex2);
            float y5 = motionEvent.getY(findPointerIndex2);
            boolean canCollapsePanelOnTouch = NotificationPanelViewController.this.canCollapsePanelOnTouch();
            int actionMasked4 = motionEvent.getActionMasked();
            if (actionMasked4 == 0) {
                ((CentralSurfacesImpl) NotificationPanelViewController.this.mCentralSurfaces).userActivity();
                NotificationPanelViewController notificationPanelViewController16 = NotificationPanelViewController.this;
                notificationPanelViewController16.mAnimatingOnDown = (notificationPanelViewController16.mHeightAnimator == null || NotificationPanelViewController.this.mIsSpringBackAnimation) ? false : true;
                NotificationPanelViewController.this.mMinExpandHeight = 0.0f;
                NotificationPanelViewController notificationPanelViewController17 = NotificationPanelViewController.this;
                notificationPanelViewController17.mDownTime = ((SystemClockImpl) notificationPanelViewController17.mSystemClock).uptimeMillis();
                if (NotificationPanelViewController.this.mAnimatingOnDown && NotificationPanelViewController.this.mClosing && !NotificationPanelViewController.this.mHintAnimationRunning) {
                    NotificationPanelViewController.this.cancelHeightAnimator();
                    NotificationPanelViewController.this.mTouchSlopExceeded = true;
                    NotificationPanelViewController.this.mShadeLog.m190v("NotificationPanelViewController MotionEvent intercepted: mAnimatingOnDown: true, mClosing: true, mHintAnimationRunning: false");
                    ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcInterceptTouchLog(motionEvent, "ACTION_DOWN: mAnimatingOnDown && mClosing && !mHintAnimationRunning", true);
                    return true;
                }
                if (!NotificationPanelViewController.this.mTracking || NotificationPanelViewController.this.isFullyCollapsed()) {
                    NotificationPanelViewController.this.mInitialExpandY = y5;
                    NotificationPanelViewController.this.mInitialExpandX = x5;
                } else {
                    NotificationPanelViewController.this.mShadeLog.m189d("not setting mInitialExpandY in onInterceptTouch");
                }
                NotificationPanelViewController.this.mTouchStartedInEmptyArea = !r3.isInContentBounds(x5, y5);
                NotificationPanelViewController notificationPanelViewController18 = NotificationPanelViewController.this;
                notificationPanelViewController18.mTouchSlopExceeded = notificationPanelViewController18.mTouchSlopExceededBeforeDown;
                NotificationPanelViewController.this.mMotionAborted = false;
                NotificationPanelViewController notificationPanelViewController19 = NotificationPanelViewController.this;
                notificationPanelViewController19.mPanelClosedOnDown = notificationPanelViewController19.isFullyCollapsed();
                ShadeLogger shadeLogger3 = NotificationPanelViewController.this.mShadeLog;
                boolean z13 = NotificationPanelViewController.this.mPanelClosedOnDown;
                float f4 = NotificationPanelViewController.this.mExpandedFraction;
                shadeLogger3.getClass();
                LogLevel logLevel4 = LogLevel.VERBOSE;
                ShadeLogger$logPanelClosedOnDown$2 shadeLogger$logPanelClosedOnDown$2 = ShadeLogger$logPanelClosedOnDown$2.INSTANCE;
                LogBuffer logBuffer4 = shadeLogger3.buffer;
                LogMessage obtain4 = logBuffer4.obtain("systemui.shade", logLevel4, shadeLogger$logPanelClosedOnDown$2, null);
                obtain4.setStr1("intercept down touch");
                obtain4.setBool1(z13);
                obtain4.setDouble1(f4);
                logBuffer4.commit(obtain4);
                NotificationPanelViewController.this.mCollapsedAndHeadsUpOnDown = false;
                NotificationPanelViewController.this.mHasLayoutedSinceDown = false;
                NotificationPanelViewController.this.mUpdateFlingOnLayout = false;
                NotificationPanelViewController.this.mTouchAboveFalsingThreshold = false;
                NotificationPanelViewController.m1690$$Nest$maddMovement(NotificationPanelViewController.this, motionEvent);
                return false;
            }
            if (actionMasked4 != 1) {
                if (actionMasked4 == 2) {
                    float f5 = y5 - NotificationPanelViewController.this.mInitialExpandY;
                    NotificationPanelViewController.m1690$$Nest$maddMovement(NotificationPanelViewController.this, motionEvent);
                    boolean z14 = NotificationPanelViewController.this.mPanelClosedOnDown && !NotificationPanelViewController.this.mCollapsedAndHeadsUpOnDown;
                    if (!canCollapsePanelOnTouch && !NotificationPanelViewController.this.mTouchStartedInEmptyArea && !NotificationPanelViewController.this.mAnimatingOnDown && !z14) {
                        return false;
                    }
                    float abs = Math.abs(f5);
                    float touchSlop = NotificationPanelViewController.this.getTouchSlop(motionEvent);
                    float f6 = -touchSlop;
                    if ((f5 >= f6 && ((!z14 && !NotificationPanelViewController.this.mAnimatingOnDown) || abs <= touchSlop)) || abs <= Math.abs(x5 - NotificationPanelViewController.this.mInitialExpandX) || NotificationPanelViewController.this.mBarState == 1) {
                        return false;
                    }
                    NotificationPanelViewController.this.cancelHeightAnimator();
                    NotificationPanelViewController notificationPanelViewController20 = NotificationPanelViewController.this;
                    NotificationPanelViewController.m1695$$Nest$mstartExpandMotion(notificationPanelViewController20, x5, y5, true, notificationPanelViewController20.mExpandedHeight);
                    NotificationPanelViewController.this.mShadeLog.m190v("NotificationPanelViewController MotionEvent intercepted: startExpandMotion");
                    NotificationPanelViewController.this.mLogBuilder.setLength(0);
                    StringBuilder sb2 = NotificationPanelViewController.this.mLogBuilder;
                    sb2.append("ACTION_MOVE: ");
                    sb2.append("mInitialTouchY: ");
                    sb2.append(NotificationPanelViewController.this.mInitialExpandY);
                    sb2.append(" [h: ");
                    sb2.append(f5);
                    sb2.append(" < -touchSlop: ");
                    sb2.append(f6);
                    sb2.append("]: ");
                    KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb2, f5 < f6, "[openShadeWithoutHun: ", z14, " || mAnimatingOnDown: ");
                    sb2.append(NotificationPanelViewController.this.mAnimatingOnDown);
                    sb2.append("]: ");
                    sb2.append(z14 || NotificationPanelViewController.this.mAnimatingOnDown);
                    sb2.append("hAbs: ");
                    sb2.append(abs);
                    sb2.append("[x: ");
                    sb2.append(x5);
                    sb2.append(" - mInitialExpandX: ");
                    sb2.append(NotificationPanelViewController.this.mInitialExpandX);
                    sb2.append("]: ");
                    sb2.append(x5 - NotificationPanelViewController.this.mInitialExpandX);
                    ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addPanelLog(motionEvent, "[NPVC]|[InterceptTouch]", NotificationPanelViewController.this.mLogBuilder, true);
                    return true;
                }
                if (actionMasked4 != 3) {
                    if (actionMasked4 == 5) {
                        NotificationPanelViewController.this.mShadeLog.logMotionEventStatusBarState(motionEvent, ((StatusBarStateControllerImpl) NotificationPanelViewController.this.mStatusBarStateController).getState(), "onInterceptTouchEvent: pointer down action");
                        NotificationPanelViewController.this.getClass();
                        if (((StatusBarStateControllerImpl) NotificationPanelViewController.this.mStatusBarStateController).getState() != 1) {
                            return false;
                        }
                        NotificationPanelViewController.this.mMotionAborted = true;
                        NotificationPanelViewController.this.mVelocityTracker.clear();
                        return false;
                    }
                    if (actionMasked4 != 6) {
                        return false;
                    }
                    NotificationPanelViewController.this.getClass();
                    int pointerId3 = motionEvent.getPointerId(motionEvent.getActionIndex());
                    if (NotificationPanelViewController.this.mTrackingPointer != pointerId3) {
                        return false;
                    }
                    int i5 = motionEvent.getPointerId(0) != pointerId3 ? 0 : 1;
                    NotificationPanelViewController.this.mTrackingPointer = motionEvent.getPointerId(i5);
                    NotificationPanelViewController.this.mInitialExpandX = motionEvent.getX(i5);
                    NotificationPanelViewController.this.mInitialExpandY = motionEvent.getY(i5);
                    return false;
                }
            }
            NotificationPanelViewController.this.mVelocityTracker.clear();
            return false;
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            View.OnTouchListener onTouchListener;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            return (!((StatusBarStateControllerImpl) notificationPanelViewController.mStatusBarStateController).mIsDozing || (onTouchListener = notificationPanelViewController.mAODDoubleTouchListener) == null) ? onTouchEvent(motionEvent) : onTouchListener.onTouch(view, motionEvent);
        }

        /* JADX WARN: Code restructure failed: missing block: B:109:0x02e3, code lost:
        
            if (r1.mQsController.shouldQuickSettingsIntercept(r1.mDownX, r1.mDownY, 0.0f) != false) goto L157;
         */
        /* JADX WARN: Code restructure failed: missing block: B:171:0x0548, code lost:
        
            if (r5 != 6) goto L328;
         */
        /* JADX WARN: Removed duplicated region for block: B:118:0x0353  */
        /* JADX WARN: Removed duplicated region for block: B:120:0x0366  */
        /* JADX WARN: Removed duplicated region for block: B:364:0x0471  */
        /* JADX WARN: Removed duplicated region for block: B:77:0x0249  */
        /* JADX WARN: Removed duplicated region for block: B:79:0x0255  */
        @Override // com.android.systemui.Gefingerpoken
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            boolean z;
            boolean z2;
            NotificationPanelViewController notificationPanelViewController;
            KeyguardSecAffordanceView keyguardSecAffordanceView;
            KeyguardSecAffordanceView keyguardSecAffordanceView2;
            boolean onTouchEvent;
            boolean z3;
            PluginLock pluginLock;
            View view;
            View view2;
            View view3;
            int pointerId;
            LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController;
            this.mTouchLogHelper.printOnTouchEventLog(motionEvent, "NPVC", "");
            if (motionEvent.getAction() == 0) {
                if (motionEvent.getDownTime() == this.mLastTouchDownTime) {
                    NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "onTouch: duplicate down event detected... ignoring");
                    ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcOnTouchLog(motionEvent, "same touch down time", true);
                    return true;
                }
                this.mLastTouchDownTime = motionEvent.getDownTime();
            } else if (motionEvent.getAction() == 1) {
                NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                int i = ((StatusBarStateControllerImpl) notificationPanelViewController2.mStatusBarStateController).mState;
                if ((i == 1 || i == 2) && !notificationPanelViewController2.mKeyguardTouchAnimator.isViRunning()) {
                    NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
                    if (!notificationPanelViewController3.mQsController.mFullyExpanded) {
                        notificationPanelViewController3.mKeyguardTouchAnimator.setIntercept(false);
                    }
                }
            }
            QuickSettingsController quickSettingsController = NotificationPanelViewController.this.mQsController;
            if (quickSettingsController.isQsFragmentCreated() && quickSettingsController.mFullyExpanded && quickSettingsController.disallowTouches()) {
                NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "onTouch: ignore touch, panel touches disallowed and qs fully expanded");
                ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcOnTouchLog(motionEvent, "isFullyExpandedAndTouchesDisallowed", false);
                return false;
            }
            NotificationPanelViewController notificationPanelViewController4 = NotificationPanelViewController.this;
            if (notificationPanelViewController4.mDetailViewCollapseAnimating || notificationPanelViewController4.mQsExpandedViewCollapseAnimating) {
                ((SecPanelLoggerImpl) notificationPanelViewController4.mPanelLogger).addNpvcOnTouchLog(motionEvent, "mDetailViewCollapseAnimating : " + NotificationPanelViewController.this.mDetailViewCollapseAnimating + " mQsExpandedViewCollapseAnimating : " + NotificationPanelViewController.this.mQsExpandedViewCollapseAnimating, true);
                return true;
            }
            if (LsRune.AOD_FULLSCREEN && notificationPanelViewController4.mUnlockedScreenOffAnimationController.isAnimationPlaying()) {
                ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcOnTouchLog(motionEvent, "unlockedScreenOff animation playing", true);
                return true;
            }
            if (NotificationPanelViewController.this.mCommandQueue.panelsEnabled()) {
                NotificationPanelViewController notificationPanelViewController5 = NotificationPanelViewController.this;
                if ((notificationPanelViewController5.mPluginLockViewMode == 0) && !notificationPanelViewController5.mQsController.mExpanded && (lockscreenNotificationIconsOnlyController = notificationPanelViewController5.mLockscreenNotificationIconsOnlyController) != null) {
                    PluginNotificationController pluginNotificationController = lockscreenNotificationIconsOnlyController.mNotificationControllerWrapper.mNotificationController;
                    if (pluginNotificationController != null ? pluginNotificationController.isIconsOnlyTouchEvent(motionEvent) : false) {
                        ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcOnTouchLog(motionEvent, "iconsOnlyTouchEvent is true", true);
                        return true;
                    }
                }
            }
            NotificationPanelViewController notificationPanelViewController6 = NotificationPanelViewController.this;
            if (notificationPanelViewController6.mFullScreenModeEnabled) {
                ((SecPanelLoggerImpl) notificationPanelViewController6.mPanelLogger).addNpvcOnTouchLog(motionEvent, "LsRune.LOCKUI_FACE_WIDGET", false);
                return false;
            }
            final SecQuickSettingsController secQuickSettingsController = notificationPanelViewController6.mQsController.mSecQuickSettingsController;
            secQuickSettingsController.getClass();
            Consumer consumer = new Consumer() { // from class: com.android.systemui.shade.SecQuickSettingsController$mediaTouchEvent$1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    SecQuickSettingsController.this.touchAboveFalsingThresholdConsumer.accept((Boolean) obj);
                }
            };
            SecQsMediaTouchHelper secQsMediaTouchHelper = secQuickSettingsController.mediaTouchHelper;
            secQsMediaTouchHelper.getClass();
            MediaType mediaType = MediaType.QS;
            SecMediaHost secMediaHost = secQsMediaTouchHelper.mediaHost;
            if (secMediaHost.getMediaPlayerSize(mediaType) != 0) {
                secQsMediaTouchHelper.updateMediaPlayerBar(motionEvent, new SecQsMediaTouchHelper$onTouch$1(secQsMediaTouchHelper, motionEvent));
                boolean z4 = secQsMediaTouchHelper.mediaPlayerExpanding;
                kotlin.Lazy lazy = secQsMediaTouchHelper.panelLogger$delegate;
                if (z4) {
                    SecQsMediaTouchHelper.log(motionEvent, new SecQsMediaTouchHelper$onTouch$2((SecPanelLogger) lazy.getValue()), "mediaPlayerExpanding is true");
                    int preparePointerIndex = secQsMediaTouchHelper.preparePointerIndex(motionEvent);
                    float y = motionEvent.getY(preparePointerIndex);
                    float x = motionEvent.getX(preparePointerIndex);
                    float asDouble = y - ((float) secQsMediaTouchHelper.initialTouchYSupplier.getAsDouble());
                    secQsMediaTouchHelper.trackMovementConsumer.accept(motionEvent);
                    int actionMasked = motionEvent.getActionMasked();
                    DoubleSupplier doubleSupplier = secQsMediaTouchHelper.currentQsVelocitySupplier;
                    if (actionMasked != 0) {
                        IntConsumer intConsumer = secQsMediaTouchHelper.trackingPointerConsumer;
                        if (actionMasked != 1) {
                            if (actionMasked == 2) {
                                MediaPlayerBarExpandHelper mediaPlayerBarExpandHelper = secMediaHost.mPlayerBarExpandHelper;
                                mediaPlayerBarExpandHelper.getClass();
                                if (!(!QpRune.QUICK_TABLET && mediaPlayerBarExpandHelper.context.getResources().getConfiguration().orientation == 2)) {
                                    float f = mediaPlayerBarExpandHelper.initialBarHeight + asDouble;
                                    float collapsedHeight = mediaPlayerBarExpandHelper.getCollapsedHeight();
                                    if (f < collapsedHeight) {
                                        f = collapsedHeight;
                                    }
                                    float expandedHeight = mediaPlayerBarExpandHelper.getExpandedHeight();
                                    if (f > expandedHeight) {
                                        f = expandedHeight;
                                    }
                                    mediaPlayerBarExpandHelper.setPlayerBarExpansion(f, true);
                                    mediaPlayerBarExpandHelper.isGestureExpand = true;
                                }
                                if (asDouble >= notificationPanelViewController6.getFalsingThreshold()) {
                                    consumer.accept(Boolean.TRUE);
                                }
                            } else if (actionMasked != 3) {
                                if (actionMasked == 6 && secQsMediaTouchHelper.trackingPointerSupplier.getAsInt() == (pointerId = motionEvent.getPointerId(motionEvent.getActionIndex()))) {
                                    int i2 = motionEvent.getPointerId(0) != pointerId ? 0 : 1;
                                    intConsumer.accept(motionEvent.getPointerId(i2));
                                    secQsMediaTouchHelper.updateInitialTouchPosition(motionEvent.getX(i2), motionEvent.getY(i2));
                                }
                            }
                        }
                        intConsumer.accept(-1);
                        secMediaHost.mPlayerBarExpandHelper.setTracking((float) doubleSupplier.getAsDouble(), false);
                        secQsMediaTouchHelper.clearVelocityTrackerRunnable.run();
                    } else {
                        secQsMediaTouchHelper.updateInitialTouchPosition(x, y);
                        secMediaHost.mPlayerBarExpandHelper.setTracking((float) doubleSupplier.getAsDouble(), true);
                        secQsMediaTouchHelper.initVelocityTrackerRunnable.run();
                    }
                    if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                        secQsMediaTouchHelper.mediaPlayerExpanding = false;
                    }
                } else if (secQsMediaTouchHelper.mediaPlayerScrolling) {
                    SecQsMediaTouchHelper.log(motionEvent, new SecQsMediaTouchHelper$onTouch$3((SecPanelLogger) lazy.getValue()), "mediaPlayerScrolling is true");
                    secQsMediaTouchHelper.notificationStackScrollLayoutController.mView.onMediaPlayerScroll(motionEvent);
                    if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                        secQsMediaTouchHelper.mediaPlayerScrolling = false;
                    }
                }
                z = true;
                if (!z) {
                    ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcOnTouchLog(motionEvent, "mediaTouchEvent", true);
                    return true;
                }
                MultiWindowEdgeDetector multiWindowEdgeDetector = NotificationPanelViewController.this.mMultiWindowEdgeDetector;
                if (multiWindowEdgeDetector != null && multiWindowEdgeDetector.interceptTouchForCornerGesture(motionEvent)) {
                    if (NotificationPanelViewController.this.mMultiWindowEdgeDetector.isGestureDetected()) {
                        NotificationPanelViewController.this.setMotionAborted();
                    }
                    NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "MultiWindowEdgeDetector - motion aborted.");
                    ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcOnTouchLog(motionEvent, "interceptTouchForCornerGesture", true);
                    return true;
                }
                CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) NotificationPanelViewController.this.mCentralSurfaces;
                if ((centralSurfacesImpl.mBouncerShowing && centralSurfacesImpl.mStatusBarKeyguardViewManager.primaryBouncerNeedsScrimming()) || ((CentralSurfacesImpl) NotificationPanelViewController.this.mCentralSurfaces).mBouncerShowingOverDream) {
                    NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "onTouch: ignore touch, bouncer scrimmed or showing over dream");
                    CentralSurfacesImpl centralSurfacesImpl2 = (CentralSurfacesImpl) NotificationPanelViewController.this.mCentralSurfaces;
                    boolean z5 = centralSurfacesImpl2.mBouncerShowing && centralSurfacesImpl2.mStatusBarKeyguardViewManager.primaryBouncerNeedsScrimming();
                    NotificationPanelViewController notificationPanelViewController7 = NotificationPanelViewController.this;
                    boolean z6 = ((CentralSurfacesImpl) notificationPanelViewController7.mCentralSurfaces).mBouncerShowingOverDream;
                    notificationPanelViewController7.mLogBuilder.setLength(0);
                    StringBuilder sb = NotificationPanelViewController.this.mLogBuilder;
                    sb.append("[isBouncerShowingScrimmed: ");
                    sb.append(z5);
                    sb.append(" || isBouncerShowingOverDream: ");
                    sb.append(z6);
                    NotificationPanelViewController notificationPanelViewController8 = NotificationPanelViewController.this;
                    ((SecPanelLoggerImpl) notificationPanelViewController8.mPanelLogger).addPanelLog(motionEvent, "[NPVC]|[onTouch]", notificationPanelViewController8.mLogBuilder, false);
                    return false;
                }
                if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                    NotificationPanelViewController.this.mBlockingExpansionForCurrentTouch = false;
                }
                if (NotificationPanelViewController.this.mLastEventSynthesizedDown && motionEvent.getAction() == 1) {
                    NotificationPanelViewController.this.expand(true);
                }
                NotificationPanelViewController.m1693$$Nest$minitDownStates(NotificationPanelViewController.this, motionEvent);
                NotificationPanelViewController notificationPanelViewController9 = NotificationPanelViewController.this;
                if (!notificationPanelViewController9.mIsExpandingOrCollapsing) {
                }
                if (!NotificationPanelViewController.this.mPulseExpansionHandler.isExpanding) {
                    z2 = false;
                    if (!z2 && NotificationPanelViewController.this.mPulseExpansionHandler.onTouchEvent(motionEvent)) {
                        NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "onTouch: PulseExpansionHandler handled event");
                        NotificationPanelViewController.this.mLogBuilder.setLength(0);
                        StringBuilder sb2 = NotificationPanelViewController.this.mLogBuilder;
                        sb2.append("!mIsExpandingOrCollapsing: ");
                        sb2.append(!NotificationPanelViewController.this.mIsExpandingOrCollapsing);
                        sb2.append("!shouldQuickSettingsIntercept: ");
                        NotificationPanelViewController notificationPanelViewController10 = NotificationPanelViewController.this;
                        sb2.append(!notificationPanelViewController10.mQsController.shouldQuickSettingsIntercept(notificationPanelViewController10.mDownX, notificationPanelViewController10.mDownY, 0.0f));
                        sb2.append("isExpanding: ");
                        sb2.append(NotificationPanelViewController.this.mPulseExpansionHandler.isExpanding);
                        NotificationPanelViewController notificationPanelViewController11 = NotificationPanelViewController.this;
                        ((SecPanelLoggerImpl) notificationPanelViewController11.mPanelLogger).addPanelLog(motionEvent, "[NPVC]|[onTouch]", notificationPanelViewController11.mLogBuilder, true);
                        return true;
                    }
                    notificationPanelViewController = NotificationPanelViewController.this;
                    if (!notificationPanelViewController.mPulsing) {
                        notificationPanelViewController.mShadeLog.logMotionEvent(motionEvent, "onTouch: eat touch, device pulsing");
                        ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcOnTouchLog(motionEvent, "mPulsing", true);
                        return true;
                    }
                    if (notificationPanelViewController.mListenForHeadsUp) {
                        HeadsUpTouchHelper headsUpTouchHelper = notificationPanelViewController.mHeadsUpTouchHelper;
                        if (!headsUpTouchHelper.mTrackingHeadsUp) {
                            if (!(notificationPanelViewController.mNotificationStackScrollLayoutController.mLongPressedView != null) && headsUpTouchHelper.onInterceptTouchEvent(motionEvent)) {
                                NotificationPanelViewController.this.mMetricsLogger.count("panel_open_peek", 1);
                            }
                        }
                    }
                    boolean onTouchEvent2 = NotificationPanelViewController.this.mHeadsUpTouchHelper.onTouchEvent(motionEvent);
                    NotificationPanelViewController notificationPanelViewController12 = NotificationPanelViewController.this;
                    if ((!notificationPanelViewController12.mIsExpandingOrCollapsing || notificationPanelViewController12.mHintAnimationRunning) && !notificationPanelViewController12.mQsController.mExpanded && notificationPanelViewController12.mBarState != 0 && !notificationPanelViewController12.mDozing && notificationPanelViewController12.mKeyguardBottomArea.getVisibility() == 0) {
                        KeyguardSecAffordanceHelper keyguardSecAffordanceHelper = NotificationPanelViewController.this.mSecAffordanceHelper;
                        keyguardSecAffordanceHelper.getClass();
                        int actionMasked2 = motionEvent.getActionMasked();
                        if ((!keyguardSecAffordanceHelper.mMotionCancelled || actionMasked2 == 0) && (!LsRune.SECURITY_SIM_PERM_DISABLED || !((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).isIccBlockedPermanently())) {
                            float y2 = motionEvent.getY();
                            float x2 = motionEvent.getX();
                            if (actionMasked2 == 0) {
                                KeyguardSecAffordanceView keyguardSecAffordanceView3 = keyguardSecAffordanceHelper.mLeftIcon;
                                Intrinsics.checkNotNull(keyguardSecAffordanceView3);
                                if (keyguardSecAffordanceView3.getVisibility() == 0) {
                                    KeyguardSecAffordanceView keyguardSecAffordanceView4 = keyguardSecAffordanceHelper.mLeftIcon;
                                    Intrinsics.checkNotNull(keyguardSecAffordanceView4);
                                    if (keyguardSecAffordanceHelper.isOnIcon(keyguardSecAffordanceView4, x2, y2)) {
                                        keyguardSecAffordanceView = keyguardSecAffordanceHelper.mLeftIcon;
                                        Intrinsics.checkNotNull(keyguardSecAffordanceView);
                                        Log.d("KeyguardSecAffordanceHelper", "onTouchEvent: After selecting target view");
                                        if (keyguardSecAffordanceView == null && ((keyguardSecAffordanceView2 = keyguardSecAffordanceHelper.mTargetedView) == null || keyguardSecAffordanceView2 == keyguardSecAffordanceView)) {
                                            keyguardSecAffordanceHelper.mMotionCancelled = false;
                                            keyguardSecAffordanceHelper.mTargetedView = keyguardSecAffordanceView;
                                            KeyguardSecAffordanceView keyguardSecAffordanceView5 = keyguardSecAffordanceHelper.mLeftIcon;
                                            if (keyguardSecAffordanceView == keyguardSecAffordanceView5) {
                                                KeyguardSecAffordanceView keyguardSecAffordanceView6 = keyguardSecAffordanceHelper.mRightIcon;
                                                Intrinsics.checkNotNull(keyguardSecAffordanceView6);
                                                keyguardSecAffordanceView6.mIsTargetView = false;
                                                KeyguardSecAffordanceView keyguardSecAffordanceView7 = keyguardSecAffordanceHelper.mLeftIcon;
                                                Intrinsics.checkNotNull(keyguardSecAffordanceView7);
                                                keyguardSecAffordanceView7.mIsTargetView = true;
                                            } else if (keyguardSecAffordanceView == keyguardSecAffordanceHelper.mRightIcon) {
                                                Intrinsics.checkNotNull(keyguardSecAffordanceView5);
                                                keyguardSecAffordanceView5.mIsTargetView = false;
                                                KeyguardSecAffordanceView keyguardSecAffordanceView8 = keyguardSecAffordanceHelper.mRightIcon;
                                                Intrinsics.checkNotNull(keyguardSecAffordanceView8);
                                                keyguardSecAffordanceView8.mIsTargetView = true;
                                            }
                                            KeyguardSecAffordanceView keyguardSecAffordanceView9 = keyguardSecAffordanceHelper.mTargetedView;
                                            Intrinsics.checkNotNull(keyguardSecAffordanceView9);
                                            keyguardSecAffordanceHelper.startPreviewAnimation(keyguardSecAffordanceView9, true);
                                            ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).setShortcutLaunchInProgress(true);
                                            KeyguardSecAffordanceView keyguardSecAffordanceView10 = keyguardSecAffordanceHelper.mTargetedView;
                                            Intrinsics.checkNotNull(keyguardSecAffordanceView10);
                                            onTouchEvent = keyguardSecAffordanceView10.onTouchEvent(motionEvent);
                                            z3 = onTouchEvent;
                                        } else {
                                            keyguardSecAffordanceHelper.mMotionCancelled = true;
                                        }
                                    }
                                }
                                KeyguardSecAffordanceView keyguardSecAffordanceView11 = keyguardSecAffordanceHelper.mRightIcon;
                                Intrinsics.checkNotNull(keyguardSecAffordanceView11);
                                if (keyguardSecAffordanceView11.getVisibility() == 0) {
                                    KeyguardSecAffordanceView keyguardSecAffordanceView12 = keyguardSecAffordanceHelper.mRightIcon;
                                    Intrinsics.checkNotNull(keyguardSecAffordanceView12);
                                    if (keyguardSecAffordanceHelper.isOnIcon(keyguardSecAffordanceView12, x2, y2)) {
                                        keyguardSecAffordanceView = keyguardSecAffordanceHelper.mRightIcon;
                                        Intrinsics.checkNotNull(keyguardSecAffordanceView);
                                        Log.d("KeyguardSecAffordanceHelper", "onTouchEvent: After selecting target view");
                                        if (keyguardSecAffordanceView == null) {
                                        }
                                        keyguardSecAffordanceHelper.mMotionCancelled = true;
                                    }
                                }
                                keyguardSecAffordanceView = null;
                                Log.d("KeyguardSecAffordanceHelper", "onTouchEvent: After selecting target view");
                                if (keyguardSecAffordanceView == null) {
                                }
                                keyguardSecAffordanceHelper.mMotionCancelled = true;
                            } else if (actionMasked2 == 1 || actionMasked2 == 3) {
                                KeyguardSecAffordanceView keyguardSecAffordanceView13 = keyguardSecAffordanceHelper.mTargetedView;
                                if (keyguardSecAffordanceView13 != null) {
                                    z3 = keyguardSecAffordanceView13.onTouchEvent(motionEvent);
                                    if (keyguardSecAffordanceHelper.mTargetedView != null) {
                                        keyguardSecAffordanceHelper.endMotion();
                                        onTouchEvent = z3;
                                        z3 = onTouchEvent;
                                    }
                                }
                            } else if (actionMasked2 != 5) {
                                KeyguardSecAffordanceView keyguardSecAffordanceView14 = keyguardSecAffordanceHelper.mTargetedView;
                                onTouchEvent = keyguardSecAffordanceView14 == null ? false : keyguardSecAffordanceView14.onTouchEvent(motionEvent);
                                z3 = onTouchEvent;
                            } else {
                                keyguardSecAffordanceHelper.mMotionCancelled = true;
                                KeyguardSecAffordanceView keyguardSecAffordanceView15 = keyguardSecAffordanceHelper.mTargetedView;
                                if (keyguardSecAffordanceView15 != null) {
                                    z3 = keyguardSecAffordanceView15.onTouchEvent(motionEvent);
                                    if (keyguardSecAffordanceHelper.mTargetedView != null) {
                                        keyguardSecAffordanceHelper.endMotion();
                                        onTouchEvent = z3;
                                        z3 = onTouchEvent;
                                    }
                                }
                            }
                            onTouchEvent2 |= z3;
                        }
                        z3 = false;
                        onTouchEvent2 |= z3;
                    }
                    NotificationPanelViewController notificationPanelViewController13 = NotificationPanelViewController.this;
                    if (notificationPanelViewController13.mOnlyAffordanceInThisMotion) {
                        ((SecPanelLoggerImpl) notificationPanelViewController13.mPanelLogger).addNpvcOnTouchLog(motionEvent, "mOnlyAffordanceInThisMotion", true);
                        return true;
                    }
                    IndicatorTouchHandler indicatorTouchHandler = notificationPanelViewController13.mIndicatorTouchHandler;
                    CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) indicatorTouchHandler.knoxStateMonitor).mCustomSdkMonitor;
                    if (customSdkMonitor != null && customSdkMonitor.mKnoxCustomDoubleTapState) {
                        if ((indicatorTouchHandler.doubleTapCount == 0 && motionEvent.getActionMasked() == 0) || motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3) {
                            int i3 = indicatorTouchHandler.doubleTapCount + 1;
                            indicatorTouchHandler.doubleTapCount = i3;
                            IndicatorTouchHandler$doubleTapTimeoutRunnable$1 indicatorTouchHandler$doubleTapTimeoutRunnable$1 = indicatorTouchHandler.doubleTapTimeoutRunnable;
                            Handler handler = indicatorTouchHandler.mainHandler;
                            if (i3 == 1) {
                                handler.removeCallbacks(indicatorTouchHandler$doubleTapTimeoutRunnable$1);
                                handler.postDelayed(indicatorTouchHandler$doubleTapTimeoutRunnable$1, 500L);
                                Log.d("IndicatorTouchHandler", "Post double tap timeout runnable");
                            } else if (i3 >= 3) {
                                Log.d("IndicatorTouchHandler", "Go to sleep by knox double tap");
                                indicatorTouchHandler.doubleTapCount = 0;
                                handler.removeCallbacks(indicatorTouchHandler$doubleTapTimeoutRunnable$1);
                                indicatorTouchHandler.powerManager.goToSleep(android.os.SystemClock.uptimeMillis());
                            }
                        }
                    }
                    int actionMasked3 = motionEvent.getActionMasked();
                    Rect rect = indicatorTouchHandler.keyguardCallChipRect;
                    Rect rect2 = indicatorTouchHandler.callChipRect;
                    KeyguardStateController keyguardStateController = indicatorTouchHandler.keyguardStateController;
                    if (actionMasked3 == 0) {
                        indicatorTouchHandler.touchDownX = motionEvent.getRawX();
                        indicatorTouchHandler.touchDownY = motionEvent.getRawY();
                        float x3 = motionEvent.getX();
                        float y3 = motionEvent.getY();
                        if (!((KeyguardStateControllerImpl) keyguardStateController).mShowing) {
                            rect = rect2;
                        }
                        if (rect.contains((int) x3, (int) y3)) {
                            indicatorTouchHandler.isTouchOnCallChip = true;
                            ActionBarContextView$$ExternalSyntheticOutline0.m9m(VIDirector$$ExternalSyntheticOutline0.m88m("ACTION_DOWN x=", indicatorTouchHandler.touchDownX, ", y=", indicatorTouchHandler.touchDownY, ", on the callChip=true, keyguardShowing="), ((KeyguardStateControllerImpl) keyguardStateController).mShowing, "IndicatorTouchHandler");
                        }
                    } else if (actionMasked3 != 1) {
                        if (actionMasked3 != 3) {
                            if (actionMasked3 == 5) {
                                if (indicatorTouchHandler.isTouchOnCallChip) {
                                    Log.d("IndicatorTouchHandler", "pointer down x=" + motionEvent + ".rawX ,y=" + motionEvent + ".rawY");
                                }
                                indicatorTouchHandler.isTouchOnCallChip = false;
                            }
                        }
                        if (indicatorTouchHandler.isTouchOnCallChip) {
                            Log.d("IndicatorTouchHandler", "cancel or pointer up -> block to jump to call in multi touch");
                        }
                        indicatorTouchHandler.isTouchOnCallChip = false;
                    } else {
                        if (indicatorTouchHandler.isTouchOnCallChip) {
                            float x4 = motionEvent.getX();
                            float y4 = motionEvent.getY();
                            if (!((KeyguardStateControllerImpl) keyguardStateController).mShowing) {
                                rect = rect2;
                            }
                            if (rect.contains((int) x4, (int) y4) && (view3 = indicatorTouchHandler.ongoingCallController.chipView) != null) {
                                view3.performClick();
                            }
                        }
                        indicatorTouchHandler.isTouchOnCallChip = false;
                    }
                    NotificationPanelViewController notificationPanelViewController14 = NotificationPanelViewController.this;
                    if (!notificationPanelViewController14.mHeadsUpTouchHelper.mTrackingHeadsUp) {
                        boolean isFullyCollapsed = notificationPanelViewController14.isFullyCollapsed();
                        NotificationPanelViewController notificationPanelViewController15 = NotificationPanelViewController.this;
                        if (notificationPanelViewController14.mQsController.handleTouch(motionEvent, isFullyCollapsed, (notificationPanelViewController15.mHeightAnimator == null || notificationPanelViewController15.mHintAnimationRunning || notificationPanelViewController15.mIsSpringBackAnimation) ? false : true)) {
                            if (motionEvent.getActionMasked() != 2) {
                                NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "onTouch: handleQsTouch handled event");
                            }
                            ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcOnTouchLog(motionEvent, "isTrackingHeadsUp && handleTouch", true);
                            return true;
                        }
                    }
                    if (motionEvent.getActionMasked() == 0 && NotificationPanelViewController.this.isFullyCollapsed()) {
                        NotificationPanelViewController.this.mMetricsLogger.count("panel_open", 1);
                        if (QpRune.QUICK_TABLET_HORIZONTAL_PANEL_POSITION) {
                            SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper = NotificationPanelViewController.this.mQsController.mSecQuickSettingsController.tabletHorizontalPanelPositionHelper;
                            secTabletHorizontalPanelPositionHelper.getClass();
                            secTabletHorizontalPanelPositionHelper.updateTabletHorizontalPanelPosition(motionEvent.getX());
                        }
                        onTouchEvent2 = true;
                    }
                    if (NotificationPanelViewController.this.isInFaceWidgetContainer(motionEvent)) {
                        NotificationPanelViewController notificationPanelViewController16 = NotificationPanelViewController.this;
                        if (!notificationPanelViewController16.mQsController.mExpanded && notificationPanelViewController16.mBarState == 1) {
                            FaceWidgetContainerWrapper faceWidgetContainerWrapper = notificationPanelViewController16.mKeyguardStatusViewController.mKeyguardStatusBase;
                            int i4 = 8;
                            if (faceWidgetContainerWrapper != null && (view2 = faceWidgetContainerWrapper.mFaceWidgetContainer) != null) {
                                i4 = view2.getVisibility();
                            }
                            if (i4 == 0) {
                                FaceWidgetContainerWrapper faceWidgetContainerWrapper2 = NotificationPanelViewController.this.mKeyguardStatusViewController.mKeyguardStatusBase;
                                boolean onTouchEvent3 = (faceWidgetContainerWrapper2 == null || (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) == null) ? false : view.onTouchEvent(motionEvent);
                                ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcOnTouchLog(motionEvent, "LsRune.LOCKUI_FACE_WIDGET", onTouchEvent3);
                                return onTouchEvent3;
                            }
                        }
                    }
                    try {
                        NotificationPanelViewController notificationPanelViewController17 = NotificationPanelViewController.this;
                        if (notificationPanelViewController17.mLockStarEnabled && notificationPanelViewController17.isInLockStarContainer(motionEvent)) {
                            NotificationPanelViewController notificationPanelViewController18 = NotificationPanelViewController.this;
                            if (!notificationPanelViewController18.mQsController.mExpanded && notificationPanelViewController18.mBarState == 1 && notificationPanelViewController18.mPluginLockStarContainer.getVisibility() == 0 && NotificationPanelViewController.this.mPluginLockStarManagerLazy.get() != null) {
                                boolean onInterceptTouchEvent = ((PluginLockStarManager) NotificationPanelViewController.this.mPluginLockStarManagerLazy.get()).onInterceptTouchEvent(motionEvent);
                                ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcOnTouchLog(motionEvent, "LsRune.PLUGIN_LOCK_STAR", onInterceptTouchEvent);
                                return onInterceptTouchEvent;
                            }
                        }
                    } catch (Throwable th) {
                        VibrationEffect vibrationEffect = NotificationPanelViewController.ADDITIONAL_TAP_REQUIRED_VIBRATION_EFFECT;
                        Log.e("NotificationPanelView", "onTouchEvent() error in LockStar - " + th.getMessage());
                    }
                    if (motionEvent.getActionMasked() == 0 && NotificationPanelViewController.this.isFullyExpanded()) {
                        NotificationPanelViewController notificationPanelViewController19 = NotificationPanelViewController.this;
                        if (notificationPanelViewController19.mKeyguardStateController.mShowing) {
                            notificationPanelViewController19.mStatusBarKeyguardViewManager.updateKeyguardPosition(motionEvent.getX());
                        }
                    }
                    PluginLock pluginLock2 = NotificationPanelViewController.this.mPluginLock;
                    if (pluginLock2 != null && pluginLock2.getTouchManager() != null) {
                        NotificationPanelViewController notificationPanelViewController20 = NotificationPanelViewController.this;
                        if (!notificationPanelViewController20.mQsController.mExpanded && ((StatusBarStateControllerImpl) notificationPanelViewController20.mStatusBarStateController).mState == 1 && notificationPanelViewController20.mPluginLock.getTouchManager().isIntercepting()) {
                            Log.e("NotificationPanelView", "onTouch() event.getAction() : " + motionEvent.getAction());
                            if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                                NotificationPanelViewController notificationPanelViewController21 = NotificationPanelViewController.this;
                                if (notificationPanelViewController21.mPluginLockViewMode == 0) {
                                    notificationPanelViewController21.mPluginLock.getTouchManager().setIntercept(false);
                                }
                            }
                            boolean onTouchEvent4 = NotificationPanelViewController.this.mPluginLock.getTouchManager().onTouchEvent(motionEvent);
                            ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcOnTouchLog(motionEvent, "LsRune.PLUGIN_LOCK", onTouchEvent4);
                            return onTouchEvent4;
                        }
                    }
                    NotificationPanelViewController.m1692$$Nest$mhandleKeyguardEmptySpaceClick(NotificationPanelViewController.this, motionEvent);
                    NotificationPanelViewController notificationPanelViewController22 = NotificationPanelViewController.this;
                    if (!notificationPanelViewController22.mQsExpandedOnTouchDown && notificationPanelViewController22.mQsController.computeExpansionFraction() <= 0.0f) {
                        NotificationPanelViewController notificationPanelViewController23 = NotificationPanelViewController.this;
                        if (!((notificationPanelViewController23.mBarState != 1 || (pluginLock = notificationPanelViewController23.mPluginLock) == null || pluginLock.getTouchManager() == null) ? false : NotificationPanelViewController.this.mPluginLock.getTouchManager().onAnimatorTouchEvent(motionEvent)) && NotificationPanelViewController.this.mKeyguardTouchAnimator.onTouchEvent(motionEvent)) {
                            NotificationPanelViewController.this.mKeyguardBottomAreaViewController.cancelIndicationAreaAnim();
                            ((SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger).addNpvcOnTouchLog(motionEvent, "LsRune.KEYGUARD_ALL_DIRECTIONS_SWIPE_UNLOCK", true);
                            return true;
                        }
                    }
                    NotificationPanelViewController notificationPanelViewController24 = NotificationPanelViewController.this;
                    if (notificationPanelViewController24.mBarState == 1) {
                        ((SecPanelLoggerImpl) notificationPanelViewController24.mPanelLogger).addNpvcOnTouchLog(motionEvent, "No handleTouch() in KEYGUARD state", onTouchEvent2);
                    } else {
                        onTouchEvent2 |= handleTouch(motionEvent);
                    }
                    NotificationPanelViewController.this.mLogBuilder.setLength(0);
                    StringBuilder sb3 = NotificationPanelViewController.this.mLogBuilder;
                    sb3.append("FINAL (!mDozing: ");
                    KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb3, !NotificationPanelViewController.this.mDozing, " || handled: ", onTouchEvent2, ")");
                    this.mTouchLogHelper.printOnTouchEventLog(motionEvent, "NPVC", NotificationPanelViewController.this.mLogBuilder.toString());
                    return !NotificationPanelViewController.this.mDozing || onTouchEvent2;
                }
                z2 = true;
                if (!z2) {
                }
                notificationPanelViewController = NotificationPanelViewController.this;
                if (!notificationPanelViewController.mPulsing) {
                }
            }
            z = false;
            if (!z) {
            }
        }
    }

    /* renamed from: -$$Nest$maddMovement, reason: not valid java name */
    public static void m1690$$Nest$maddMovement(NotificationPanelViewController notificationPanelViewController, MotionEvent motionEvent) {
        notificationPanelViewController.getClass();
        float rawX = motionEvent.getRawX() - motionEvent.getX();
        float rawY = motionEvent.getRawY() - motionEvent.getY();
        motionEvent.offsetLocation(rawX, rawY);
        notificationPanelViewController.mVelocityTracker.addMovement(motionEvent);
        motionEvent.offsetLocation(-rawX, -rawY);
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0124  */
    /* renamed from: -$$Nest$mendMotionEvent, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m1691$$Nest$mendMotionEvent(NotificationPanelViewController notificationPanelViewController, MotionEvent motionEvent, float f, float f2, boolean z) {
        VelocityTracker velocityTracker;
        boolean z2;
        boolean z3;
        ShadeLogger shadeLogger = notificationPanelViewController.mShadeLog;
        shadeLogger.logEndMotionEvent("endMotionEvent called", z, false);
        notificationPanelViewController.mTrackingPointer = -1;
        AmbientState ambientState = notificationPanelViewController.mAmbientState;
        if (ambientState.mIsSwipingUp) {
            ambientState.mIsFlingRequiredAfterLockScreenSwipeUp = true;
        }
        ambientState.mIsSwipingUp = false;
        boolean z4 = notificationPanelViewController.mTracking;
        KeyguardStateControllerImpl keyguardStateControllerImpl = notificationPanelViewController.mKeyguardStateController;
        VelocityTracker velocityTracker2 = notificationPanelViewController.mVelocityTracker;
        if (!(z4 && notificationPanelViewController.mTouchSlopExceeded) && Math.abs(f - notificationPanelViewController.mInitialExpandX) <= notificationPanelViewController.mTouchSlop && Math.abs(f2 - notificationPanelViewController.mInitialExpandY) <= notificationPanelViewController.mTouchSlop && !((!notificationPanelViewController.isFullyExpanded() && !notificationPanelViewController.isFullyCollapsed()) || motionEvent.getActionMasked() == 3 || z)) {
            if (!((CentralSurfacesImpl) notificationPanelViewController.mCentralSurfaces).mBouncerShowing && !notificationPanelViewController.mAlternateBouncerInteractor.isVisibleState() && !keyguardStateControllerImpl.mKeyguardGoingAway) {
                notificationPanelViewController.onEmptySpaceClick();
                notificationPanelViewController.onTrackingStopped(true);
            }
            velocityTracker = velocityTracker2;
        } else {
            velocityTracker2.computeCurrentVelocity(1000);
            float yVelocity = velocityTracker2.getYVelocity();
            float hypot = (float) Math.hypot(velocityTracker2.getXVelocity(), velocityTracker2.getYVelocity());
            boolean z5 = keyguardStateControllerImpl.mShowing;
            boolean z6 = keyguardStateControllerImpl.mKeyguardFadingAway;
            QuickSettingsController quickSettingsController = notificationPanelViewController.mQsController;
            if (z6 || (notificationPanelViewController.mInitialTouchFromKeyguard && !z5)) {
                velocityTracker = velocityTracker2;
            } else {
                if (motionEvent.getActionMasked() == 3 || z) {
                    velocityTracker = velocityTracker2;
                    if (z5) {
                        shadeLogger.logEndMotionEvent("endMotionEvent: cancel while on keyguard", z, true);
                        z2 = true;
                    } else if (!((CentralSurfacesImpl) notificationPanelViewController.mCentralSurfaces).mBouncerShowingOverDream) {
                        z2 = !notificationPanelViewController.mPanelClosedOnDown;
                        shadeLogger.logEndMotionEvent("endMotionEvent: cancel", z, z2);
                    }
                } else {
                    if (notificationPanelViewController.mFalsingManager.isUnlockingDisabled()) {
                        velocityTracker = velocityTracker2;
                    } else {
                        int i = f2 - notificationPanelViewController.mInitialExpandY > 0.0f ? 0 : keyguardStateControllerImpl.mCanDismissLockScreen ? 4 : 8;
                        float f3 = notificationPanelViewController.mFlingAnimationUtils.mMinVelocityPxPerSecond;
                        boolean z7 = notificationPanelViewController.mExpandedFraction > 0.5f;
                        boolean z8 = notificationPanelViewController.mAllowExpandForSmallExpansion;
                        LogLevel logLevel = LogLevel.VERBOSE;
                        ShadeLogger$logFlingExpands$2 shadeLogger$logFlingExpands$2 = new Function1() { // from class: com.android.systemui.shade.ShadeLogger$logFlingExpands$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                LogMessage logMessage = (LogMessage) obj;
                                long long1 = logMessage.getLong1();
                                long long2 = logMessage.getLong2();
                                int int1 = logMessage.getInt1();
                                double double1 = logMessage.getDouble1();
                                boolean bool1 = logMessage.getBool1();
                                boolean bool2 = logMessage.getBool2();
                                StringBuilder m17m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("NPVC flingExpands called with vel: ", long1, ", vectorVel: ");
                                m17m.append(long2);
                                m17m.append(", interactionType: ");
                                m17m.append(int1);
                                m17m.append(", minVelocityPxPerSecond: ");
                                m17m.append(double1);
                                m17m.append(" expansionOverHalf: ");
                                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m74m(m17m, bool1, ", allowExpandForSmallExpansion: ", bool2);
                            }
                        };
                        LogBuffer logBuffer = shadeLogger.buffer;
                        velocityTracker = velocityTracker2;
                        LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$logFlingExpands$2, null);
                        obtain.setInt1(i);
                        obtain.setLong1((long) yVelocity);
                        obtain.setLong2((long) hypot);
                        obtain.setDouble1(f3);
                        obtain.setBool1(z7);
                        obtain.setBool2(z8);
                        logBuffer.commit(obtain);
                        if (Math.abs(hypot) >= notificationPanelViewController.mFlingAnimationUtils.mMinVelocityPxPerSecond ? yVelocity <= 0.0f : notificationPanelViewController.mExpandedFraction <= 0.5f) {
                            z2 = false;
                            if (quickSettingsController.mExpansionAnimator == null) {
                                z2 = true;
                            }
                            if (notificationPanelViewController.mBarState != 1 && z2 && !notificationPanelViewController.isFullyExpanded() && notificationPanelViewController.mListenForHeadsUp) {
                                Log.d("NotificationPanelView", "previous heightAnimator cancel due to headsup");
                                notificationPanelViewController.cancelHeightAnimator();
                            }
                            shadeLogger.logEndMotionEvent("endMotionEvent: flingExpands", z, z2);
                        }
                    }
                    z2 = true;
                    if (quickSettingsController.mExpansionAnimator == null) {
                    }
                    if (notificationPanelViewController.mBarState != 1) {
                        Log.d("NotificationPanelView", "previous heightAnimator cancel due to headsup");
                        notificationPanelViewController.cancelHeightAnimator();
                    }
                    shadeLogger.logEndMotionEvent("endMotionEvent: flingExpands", z, z2);
                }
                notificationPanelViewController.mDozeLog.traceFling(z2, notificationPanelViewController.mTouchAboveFalsingThreshold, ((CentralSurfacesImpl) notificationPanelViewController.mCentralSurfaces).mWakeUpComingFromTouch);
                if (!z2 && z5) {
                    float f4 = ((CentralSurfacesImpl) notificationPanelViewController.mCentralSurfaces).mDisplayMetrics.density;
                    notificationPanelViewController.mLockscreenGestureLogger.write(186, (int) Math.abs((f2 - notificationPanelViewController.mInitialExpandY) / f4), (int) Math.abs(yVelocity / f4));
                    new UiEventLoggerImpl().log(LockscreenGestureLogger.LockscreenUiEvent.LOCKSCREEN_UNLOCK);
                }
                int i2 = (yVelocity > 0.0f ? 1 : (yVelocity == 0.0f ? 0 : -1));
                if (notificationPanelViewController.mBarState == 1 || notificationPanelViewController.mExpandedFraction < 1.0d) {
                    notificationPanelViewController.fling(yVelocity, z2, 1.0f);
                } else {
                    shadeLogger.m189d("NPVC endMotionEvent - skipping fling on keyguard");
                }
                notificationPanelViewController.onTrackingStopped(z2);
                z3 = (z2 || !notificationPanelViewController.mPanelClosedOnDown || notificationPanelViewController.mHasLayoutedSinceDown || quickSettingsController.isExpandImmediate()) ? false : true;
                notificationPanelViewController.mUpdateFlingOnLayout = z3;
                if (z3) {
                    notificationPanelViewController.mUpdateFlingVelocity = yVelocity;
                }
            }
            z2 = false;
            notificationPanelViewController.mDozeLog.traceFling(z2, notificationPanelViewController.mTouchAboveFalsingThreshold, ((CentralSurfacesImpl) notificationPanelViewController.mCentralSurfaces).mWakeUpComingFromTouch);
            if (!z2) {
                float f42 = ((CentralSurfacesImpl) notificationPanelViewController.mCentralSurfaces).mDisplayMetrics.density;
                notificationPanelViewController.mLockscreenGestureLogger.write(186, (int) Math.abs((f2 - notificationPanelViewController.mInitialExpandY) / f42), (int) Math.abs(yVelocity / f42));
                new UiEventLoggerImpl().log(LockscreenGestureLogger.LockscreenUiEvent.LOCKSCREEN_UNLOCK);
            }
            int i22 = (yVelocity > 0.0f ? 1 : (yVelocity == 0.0f ? 0 : -1));
            if (notificationPanelViewController.mBarState == 1) {
            }
            notificationPanelViewController.fling(yVelocity, z2, 1.0f);
            notificationPanelViewController.onTrackingStopped(z2);
            if (z2) {
            }
            notificationPanelViewController.mUpdateFlingOnLayout = z3;
            if (z3) {
            }
        }
        velocityTracker.clear();
    }

    /* renamed from: -$$Nest$mhandleKeyguardEmptySpaceClick, reason: not valid java name */
    public static void m1692$$Nest$mhandleKeyguardEmptySpaceClick(NotificationPanelViewController notificationPanelViewController, MotionEvent motionEvent) {
        AnimatorSet animatorSet;
        ValueAnimator valueAnimator;
        notificationPanelViewController.getClass();
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 1) {
            if (actionMasked != 2) {
                return;
            }
            float touchSlop = notificationPanelViewController.getTouchSlop(motionEvent);
            if (notificationPanelViewController.mTouchIsClick) {
                if (Math.abs(motionEvent.getY() - notificationPanelViewController.mDownY) > touchSlop || Math.abs(motionEvent.getX() - notificationPanelViewController.mDownX) > touchSlop) {
                    notificationPanelViewController.mTouchIsClick = false;
                    return;
                }
                return;
            }
            return;
        }
        if (((Boolean) ((KeyguardEditModeControllerImpl) notificationPanelViewController.mKeyguardEditModeController).isTouchDownAnimationRunning.invoke()).booleanValue()) {
            Log.d("NotificationPanelView", "handleKeyguardEmptySpaceClick skip : touch down vi running");
            return;
        }
        if (notificationPanelViewController.isOnKeyguard() && notificationPanelViewController.mTouchIsClick && notificationPanelViewController.mNotificationStackScrollLayoutController.mView.isBelowLastNotification(notificationPanelViewController.mDownY) && !notificationPanelViewController.mQsController.mExpanded) {
            final NotificationIconTransitionController notificationIconTransitionController = (NotificationIconTransitionController) Dependency.get(NotificationIconTransitionController.class);
            View view = notificationIconTransitionController.mIconContainer;
            if (view != null) {
                NotificationStackScrollLayout notificationStackScrollLayout = notificationIconTransitionController.mNotificationStackScrollLayout;
                if (notificationStackScrollLayout != null) {
                    notificationStackScrollLayout.setPivotX((((view.getX() - notificationIconTransitionController.mNotificationStackScrollLayout.getX()) * 2.0f) + notificationIconTransitionController.mIconContainer.getWidth()) / 2.0f);
                    notificationIconTransitionController.mNotificationStackScrollLayout.setPivotY(notificationIconTransitionController.mIconContainer.getY() + notificationIconTransitionController.mIconContainer.getHeight());
                }
                notificationIconTransitionController.mIconContainer.setPivotX(r11.getWidth() / 2.0f);
                notificationIconTransitionController.mIconContainer.setPivotY(r11.getHeight() / 2.0f);
            }
            Optional.ofNullable(null).ifPresent(new NotificationIconTransitionController$$ExternalSyntheticLambda0(4));
            Optional.ofNullable(null).ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.iconsOnly.NotificationIconTransitionController$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    NotificationIconTransitionController notificationIconTransitionController2 = NotificationIconTransitionController.this;
                    AnimatorSet animatorSet2 = (AnimatorSet) obj;
                    notificationIconTransitionController2.getClass();
                    if (animatorSet2.isRunning()) {
                        notificationIconTransitionController2.mIsNeedDelay = false;
                        animatorSet2.cancel();
                    }
                }
            });
            if ((((SettingsHelper) Dependency.get(SettingsHelper.class)).isNotificationIconsOnlyOn() && notificationIconTransitionController.mLockscreenNotificationManager.mIsDetail && ((animatorSet = notificationIconTransitionController.mAppearingIconAnimSet) == null || !animatorSet.isRunning()) && ((valueAnimator = notificationIconTransitionController.mDisappearingDetailScaleAnim) == null || !valueAnimator.isRunning())) ? false : true) {
                return;
            }
            notificationIconTransitionController.misTransformAnimating = true;
            final AnimationCreator animationCreator = notificationIconTransitionController.mAnimationCreator;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(animationCreator.mController.mDetailedCardScale, 0.5f);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.iconsOnly.AnimationCreator$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    AnimationCreator animationCreator2 = AnimationCreator.this;
                    animationCreator2.getClass();
                    float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                    NotificationIconTransitionController notificationIconTransitionController2 = animationCreator2.mController;
                    notificationIconTransitionController2.mDetailedCardScale = floatValue;
                    NotificationStackScrollLayout notificationStackScrollLayout2 = notificationIconTransitionController2.mNotificationStackScrollLayout;
                    if (notificationStackScrollLayout2 != null) {
                        notificationStackScrollLayout2.setScaleX(floatValue);
                        notificationIconTransitionController2.mNotificationStackScrollLayout.setScaleY(notificationIconTransitionController2.mDetailedCardScale);
                    }
                }
            });
            ofFloat.setDuration(300L);
            ofFloat.setInterpolator(new PathInterpolator(0.7f, 0.0f, 0.83f, 0.83f));
            notificationIconTransitionController.mDisappearingDetailScaleAnim = ofFloat;
            ofFloat.start();
            NotificationStackScrollLayout notificationStackScrollLayout2 = notificationIconTransitionController.mNotificationStackScrollLayout;
            if (notificationStackScrollLayout2 != null) {
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(notificationStackScrollLayout2, "alpha", 0.0f);
                ofFloat2.setDuration(300L).setInterpolator(Interpolators.LINEAR);
                ofFloat2.start();
            }
            notificationIconTransitionController.mIsNeedDelay = true;
            final View view2 = notificationIconTransitionController.mIconContainer;
            if (view2 != null) {
                view2.setScaleX(1.2f);
                view2.setScaleY(1.2f);
                ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 1.0f);
                ofFloat3.addListener(new AnimatorListenerAdapter(animationCreator, view2) { // from class: com.android.systemui.statusbar.iconsOnly.AnimationCreator.2
                    public final /* synthetic */ View val$animView;

                    public C26582(final AnimationCreator animationCreator2, final View view22) {
                        this.val$animView = view22;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        this.val$animView.setVisibility(0);
                    }
                });
                ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(view22, "scaleX", view22.getScaleX(), 1.0f);
                ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(view22, "scaleY", view22.getScaleY(), 1.0f);
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(ofFloat3, ofFloat4, ofFloat5);
                animatorSet2.setDuration(200L).setInterpolator(Interpolators.LINEAR);
                notificationIconTransitionController.mAppearingIconAnimSet = animatorSet2;
                animatorSet2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.iconsOnly.NotificationIconTransitionController.3
                    public boolean isCanceled = false;

                    public C26613() {
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        this.isCanceled = true;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        NotificationIconTransitionController notificationIconTransitionController2 = NotificationIconTransitionController.this;
                        notificationIconTransitionController2.misTransformAnimating = false;
                        notificationIconTransitionController2.mNeedAnimForRemoval = false;
                        LockscreenNotificationManager lockscreenNotificationManager = notificationIconTransitionController2.mLockscreenNotificationManager;
                        if (lockscreenNotificationManager.mIsDetail) {
                            lockscreenNotificationManager.mIsDetail = false;
                            lockscreenNotificationManager.updateNotificationType();
                        }
                        NotificationIconTransitionController.this.mAppearingIconAnimSet.removeListener(this);
                        if (this.isCanceled) {
                            return;
                        }
                        NotificationIconTransitionController.this.mNotificationStackScrollLayout.updateVisibility();
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                    }
                });
                notificationIconTransitionController.mAppearingIconAnimSet.setStartDelay(notificationIconTransitionController.mIsNeedDelay ? 200L : 0L);
                notificationIconTransitionController.mAppearingIconAnimSet.start();
            }
        }
    }

    /* renamed from: -$$Nest$minitDownStates, reason: not valid java name */
    public static void m1693$$Nest$minitDownStates(NotificationPanelViewController notificationPanelViewController, MotionEvent motionEvent) {
        boolean z;
        notificationPanelViewController.getClass();
        boolean z2 = false;
        if (motionEvent.getActionMasked() != 0) {
            notificationPanelViewController.mLastEventSynthesizedDown = false;
            return;
        }
        notificationPanelViewController.mTouchIsClick = true;
        notificationPanelViewController.mDozingOnDown = notificationPanelViewController.mDozing;
        notificationPanelViewController.mOnlyAffordanceInThisMotion = false;
        notificationPanelViewController.mSecAffordanceHelper.isShortcutPreviewSwipingInProgress = false;
        notificationPanelViewController.mDownX = motionEvent.getX();
        notificationPanelViewController.mDownY = motionEvent.getY();
        boolean isFullyCollapsed = notificationPanelViewController.isFullyCollapsed();
        notificationPanelViewController.mCollapsedOnDown = isFullyCollapsed;
        QuickSettingsController quickSettingsController = notificationPanelViewController.mQsController;
        quickSettingsController.mCollapsedOnDown = isFullyCollapsed;
        if (notificationPanelViewController.mNotificationStackScrollLayoutController.mView.mOwnScrollY >= quickSettingsController.mMinExpansionHeight - notificationPanelViewController.mQuickQsOffsetHeight) {
            notificationPanelViewController.mIsPanelCollapseOnQQS = false;
        } else {
            float f = notificationPanelViewController.mDownX;
            float f2 = notificationPanelViewController.mDownY;
            if (!isFullyCollapsed && quickSettingsController.mBarState != 1 && !quickSettingsController.mExpanded) {
                InterfaceC1922QS interfaceC1922QS = quickSettingsController.mQs;
                View header = interfaceC1922QS == null ? quickSettingsController.mKeyguardStatusBar : interfaceC1922QS.getHeader();
                FrameLayout frameLayout = quickSettingsController.mQsFrame;
                if (f >= frameLayout.getX() && f <= frameLayout.getX() + frameLayout.getWidth() && f2 <= header.getBottom()) {
                    z = true;
                    notificationPanelViewController.mIsPanelCollapseOnQQS = z;
                }
            }
            z = false;
            notificationPanelViewController.mIsPanelCollapseOnQQS = z;
        }
        if (notificationPanelViewController.mCollapsedOnDown && notificationPanelViewController.mHeadsUpManager.mHasPinnedNotification) {
            z2 = true;
        }
        notificationPanelViewController.mListenForHeadsUp = z2;
        boolean z3 = notificationPanelViewController.mExpectingSynthesizedDown;
        notificationPanelViewController.mAllowExpandForSmallExpansion = z3;
        notificationPanelViewController.mTouchSlopExceededBeforeDown = z3;
        notificationPanelViewController.mLastEventSynthesizedDown = z3;
        long eventTime = motionEvent.getEventTime();
        float f3 = notificationPanelViewController.mDownX;
        float f4 = notificationPanelViewController.mDownY;
        boolean z4 = quickSettingsController.mFullyExpanded;
        quickSettingsController.mTouchAboveFalsingThreshold = z4;
        boolean z5 = notificationPanelViewController.mDozingOnDown;
        boolean z6 = notificationPanelViewController.mCollapsedOnDown;
        boolean z7 = notificationPanelViewController.mIsPanelCollapseOnQQS;
        boolean z8 = notificationPanelViewController.mListenForHeadsUp;
        boolean z9 = notificationPanelViewController.mAllowExpandForSmallExpansion;
        boolean z10 = notificationPanelViewController.mTouchSlopExceededBeforeDown;
        boolean z11 = notificationPanelViewController.mLastEventSynthesizedDown;
        NPVCDownEventState nPVCDownEventState = (NPVCDownEventState) notificationPanelViewController.mLastDownEvents.buffer.advance();
        nPVCDownEventState.timeStamp = eventTime;
        nPVCDownEventState.f341x = f3;
        nPVCDownEventState.f342y = f4;
        nPVCDownEventState.qsTouchAboveFalsingThreshold = z4;
        nPVCDownEventState.dozing = z5;
        nPVCDownEventState.collapsed = z6;
        nPVCDownEventState.canCollapseOnQQS = z7;
        nPVCDownEventState.listenForHeadsUp = z8;
        nPVCDownEventState.allowExpandForSmallExpansion = z9;
        nPVCDownEventState.touchSlopExceededBeforeDown = z10;
        nPVCDownEventState.lastEventSynthesized = z11;
    }

    /* renamed from: -$$Nest$mstartExpandMotion, reason: not valid java name */
    public static void m1695$$Nest$mstartExpandMotion(NotificationPanelViewController notificationPanelViewController, float f, float f2, boolean z, float f3) {
        if (!notificationPanelViewController.mHandlingPointerUp && !((StatusBarStateControllerImpl) notificationPanelViewController.mStatusBarStateController).mIsDozing) {
            notificationPanelViewController.mQsController.beginJankMonitoring(notificationPanelViewController.isFullyCollapsed());
        }
        notificationPanelViewController.mInitialOffsetOnTouch = f3;
        if (!notificationPanelViewController.mTracking || notificationPanelViewController.isFullyCollapsed()) {
            notificationPanelViewController.mInitialExpandY = f2;
            notificationPanelViewController.mInitialExpandX = f;
        } else {
            notificationPanelViewController.mShadeLog.m189d("not setting mInitialExpandY in startExpandMotion");
        }
        notificationPanelViewController.mInitialTouchFromKeyguard = notificationPanelViewController.mKeyguardStateController.mShowing;
        if (z) {
            notificationPanelViewController.mTouchSlopExceeded = true;
            notificationPanelViewController.setExpandedHeight(notificationPanelViewController.mInitialOffsetOnTouch);
            notificationPanelViewController.onTrackingStarted();
        } else if (notificationPanelViewController.mHeadsUpPinnedMode) {
            notificationPanelViewController.mTouchDownOnHeadsUpPinnded = true;
        }
    }

    /* JADX WARN: Type inference failed for: r10v27, types: [com.android.systemui.shade.NotificationPanelViewController$17] */
    /* JADX WARN: Type inference failed for: r10v6, types: [com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda5] */
    public NotificationPanelViewController(DcmMascotViewContainer dcmMascotViewContainer, PluginLockMediator pluginLockMediator, PluginLockData pluginLockData, KeyguardClockPositionAlgorithm keyguardClockPositionAlgorithm, Lazy lazy, NotificationPanelView notificationPanelView, Handler handler, LayoutInflater layoutInflater, FeatureFlags featureFlags, NotificationWakeUpCoordinator notificationWakeUpCoordinator, PulseExpansionHandler pulseExpansionHandler, DynamicPrivacyController dynamicPrivacyController, KeyguardBypassController keyguardBypassController, FalsingManager falsingManager, FalsingCollector falsingCollector, KeyguardStateController keyguardStateController, StatusBarStateController statusBarStateController, StatusBarWindowStateController statusBarWindowStateController, NotificationShadeWindowController notificationShadeWindowController, DozeLog dozeLog, DozeParameters dozeParameters, CommandQueue commandQueue, VibratorHelper vibratorHelper, LatencyTracker latencyTracker, PowerManager powerManager, AccessibilityManager accessibilityManager, int i, KeyguardUpdateMonitor keyguardUpdateMonitor, MetricsLogger metricsLogger, ShadeLogger shadeLogger, ConfigurationController configurationController, Provider provider, StatusBarTouchableRegionManager statusBarTouchableRegionManager, ConversationNotificationManager conversationNotificationManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, NotificationGutsManager notificationGutsManager, NotificationsQSContainerController notificationsQSContainerController, NotificationStackScrollLayoutController notificationStackScrollLayoutController, KeyguardStatusViewComponent.Factory factory, KeyguardQsUserSwitchComponent.Factory factory2, KeyguardUserSwitcherComponent.Factory factory3, KeyguardStatusBarViewComponent.Factory factory4, LockscreenShadeTransitionController lockscreenShadeTransitionController, AuthController authController, ScrimController scrimController, UserManager userManager, MediaDataManager mediaDataManager, NotificationShadeDepthController notificationShadeDepthController, AmbientState ambientState, SecLockIconViewController secLockIconViewController, KeyguardMediaController keyguardMediaController, TapAgainViewController tapAgainViewController, NavigationModeController navigationModeController, NavigationBarController navigationBarController, QuickSettingsController quickSettingsController, FragmentService fragmentService, ContentResolver contentResolver, ShadeHeaderController shadeHeaderController, ScreenOffAnimationController screenOffAnimationController, LockscreenGestureLogger lockscreenGestureLogger, ShadeExpansionStateManager shadeExpansionStateManager, Optional<SysUIUnfoldComponent> optional, SysUiState sysUiState, Provider provider2, KeyguardUnlockAnimationController keyguardUnlockAnimationController, KeyguardIndicationController keyguardIndicationController, NotificationListContainer notificationListContainer, NotificationStackSizeCalculator notificationStackSizeCalculator, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, ShadeTransitionController shadeTransitionController, InteractionJankMonitor interactionJankMonitor, SystemClock systemClock, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, KeyguardBottomAreaInteractor keyguardBottomAreaInteractor, Lazy lazy2, AlternateBouncerInteractor alternateBouncerInteractor, DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel, OccludedToLockscreenTransitionViewModel occludedToLockscreenTransitionViewModel, LockscreenToDreamingTransitionViewModel lockscreenToDreamingTransitionViewModel, GoneToDreamingTransitionViewModel goneToDreamingTransitionViewModel, LockscreenToOccludedTransitionViewModel lockscreenToOccludedTransitionViewModel, CoroutineDispatcher coroutineDispatcher, KeyguardTransitionInteractor keyguardTransitionInteractor, Provider provider3, DumpManager dumpManager, KeyguardLongPressViewModel keyguardLongPressViewModel, KeyguardInteractor keyguardInteractor, ActivityStarter activityStarter, KeyguardFaceAuthInteractor keyguardFaceAuthInteractor, NotificationShelfManager notificationShelfManager, PrivacyDialogController privacyDialogController, KeyguardPunchHoleVIViewController.Factory factory5, KeyguardTouchAnimator keyguardTouchAnimator, KeyguardEditModeController keyguardEditModeController, Lazy lazy3, IndicatorTouchHandler indicatorTouchHandler, QsStatusEventLog qsStatusEventLog, LockscreenNotificationManager lockscreenNotificationManager, LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController, KeyguardWallpaperController keyguardWallpaperController, WallpaperImageInjectCreator wallpaperImageInjectCreator, EmergencyButtonController.Factory factory6, SecPanelLogger secPanelLogger, SecPanelPolicy secPanelPolicy, Lazy lazy4, Lazy lazy5, Lazy lazy6) {
        final int i2 = 0;
        this.mKeyguardAffordanceHelperCallback = new KeyguardAffordanceHelperCallback(this, i2);
        this.mOnHeadsUpChangedListener = new ShadeHeadsUpChangedListener(this, i2);
        this.mConfigurationListener = new ConfigurationListener(this, i2);
        this.mStatusBarStateListener = new StatusBarStateListener(this, i2);
        this.mAccessibilityDelegate = new ShadeAccessibilityDelegate(this, i2);
        this.mClockPositionAlgorithm = new KeyguardClockPositionAlgorithm();
        this.mShadeHeadsUpTracker = new ShadeHeadsUpTrackerImpl(this, i2);
        this.mShadeFoldAnimator = new ShadeFoldAnimatorImpl(this, i2);
        this.mShadeNotificationPresenter = new ShadeNotificationPresenterImpl(this, i2);
        NotificationPanelViewController$$ExternalSyntheticLambda9 notificationPanelViewController$$ExternalSyntheticLambda9 = new NotificationPanelViewController$$ExternalSyntheticLambda9();
        Function function = new Function() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i2) {
                    case 0:
                        return Float.valueOf(((NotificationPanelView) obj).mCurrentPanelAlpha);
                    case 1:
                        return ((SysUIUnfoldComponent) obj).getKeyguardUnfoldTransition();
                    default:
                        return ((SysUIUnfoldComponent) obj).getNotificationPanelUnfoldAnimationController();
                }
            }
        };
        AnimatableProperty.C27067 c27067 = AnimatableProperty.f351Y;
        AnimatableProperty.C27056 c27056 = new AnimatableProperty.C27056(R.id.panel_alpha_animator_start_tag, R.id.panel_alpha_animator_end_tag, R.id.panel_alpha_animator_tag, new AnimatableProperty.C27045("panelAlpha", function, notificationPanelViewController$$ExternalSyntheticLambda9));
        this.mPanelAlphaAnimator = c27056;
        AnimationProperties animationProperties = new AnimationProperties();
        animationProperties.duration = 150L;
        Interpolator interpolator = Interpolators.ALPHA_OUT;
        Property property = c27056.val$property;
        animationProperties.setCustomInterpolator(property, interpolator);
        this.mPanelAlphaOutPropertiesAnimator = animationProperties;
        AnimationProperties animationProperties2 = new AnimationProperties();
        animationProperties2.duration = 200L;
        int i3 = 8;
        animationProperties2.mAnimationEndAction = new NotificationPanelViewController$$ExternalSyntheticLambda1(this, i3);
        animationProperties2.setCustomInterpolator(property, Interpolators.ALPHA_IN);
        this.mPanelAlphaInPropertiesAnimator = animationProperties2;
        this.mCurrentPanelState = 0;
        this.mKeyguardOnlyContentAlpha = 1.0f;
        this.mKeyguardOnlyTransitionTranslationY = 0;
        this.mHasVibratedOnOpen = false;
        this.mFixedDuration = -1;
        this.mLastGesturedOverExpansion = -1.0f;
        this.mExpandedFraction = 0.0f;
        this.mExpansionDragDownAmountPx = 0.0f;
        this.mNextCollapseSpeedUpFactor = 1.0f;
        this.mWillPlayDelayedDozeAmountAnimation = false;
        this.mIsOcclusionTransitionRunning = false;
        int i4 = 6;
        this.mFlingCollapseRunnable = new NotificationPanelViewController$$ExternalSyntheticLambda0(this, i4);
        this.mAnimateKeyguardBottomAreaInvisibleEndRunnable = new NotificationPanelViewController$$ExternalSyntheticLambda0(this, 7);
        this.mHeadsUpExistenceChangedRunnable = new NotificationPanelViewController$$ExternalSyntheticLambda0(this, i3);
        int i5 = 9;
        this.mMaybeHideExpandedRunnable = new NotificationPanelViewController$$ExternalSyntheticLambda0(this, i5);
        this.mMultiShadeExpansionConsumer = new NotificationPanelViewController$$ExternalSyntheticLambda1(this, i5);
        this.mDreamingToLockscreenTransition = new NotificationPanelViewController$$ExternalSyntheticLambda1(this, 2);
        int i6 = 3;
        this.mOccludedToLockscreenTransition = new NotificationPanelViewController$$ExternalSyntheticLambda1(this, i6);
        this.mLockscreenToDreamingTransition = new NotificationPanelViewController$$ExternalSyntheticLambda1(this, 4);
        this.mGoneToDreamingTransition = new NotificationPanelViewController$$ExternalSyntheticLambda1(this, 5);
        this.mLockscreenToOccludedTransition = new NotificationPanelViewController$$ExternalSyntheticLambda1(this, i4);
        this.mLastCameraLaunchSource = 3;
        this.mIsKeyguardSupportLandscapePhone = false;
        this.mLogBuilder = new StringBuilder();
        this.dataUsageVisible = false;
        this.mIsLockStarOnTouchDown = false;
        this.mShadeViewStateProvider = new C243316();
        this.mDataUsageLabelParent = null;
        this.mSystemUIWidgetCallback = new SystemUIWidgetCallback() { // from class: com.android.systemui.shade.NotificationPanelViewController.17
            @Override // com.android.systemui.widget.SystemUIWidgetCallback
            public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
                VibrationEffect vibrationEffect = NotificationPanelViewController.ADDITIONAL_TAP_REQUIRED_VIBRATION_EFFECT;
                Log.d("NotificationPanelView", "updateStyle, onNavigationColorUpdateRequired");
                CentralSurfaces centralSurfaces = NotificationPanelViewController.this.mCentralSurfaces;
                boolean isWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper("navibar");
                StatusBarWindowView statusBarWindowView = ((CentralSurfacesImpl) centralSurfaces).mStatusBarWindowController.mStatusBarWindowView;
                int systemUiVisibility = statusBarWindowView.getSystemUiVisibility();
                statusBarWindowView.setSystemUiVisibility(isWhiteKeyguardWallpaper ? systemUiVisibility | 16 : systemUiVisibility & (-17));
            }
        };
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardStateController;
        keyguardStateControllerImpl.addCallback(new KeyguardStateController.Callback() { // from class: com.android.systemui.shade.NotificationPanelViewController.1
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardFadingAwayChanged() {
                NotificationPanelViewController.this.updateExpandedHeightToMaxHeight();
            }
        });
        this.mAmbientState = ambientState;
        this.mView = notificationPanelView;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mLockscreenGestureLogger = lockscreenGestureLogger;
        this.mShadeExpansionStateManager = shadeExpansionStateManager;
        this.mShadeLog = shadeLogger;
        this.mGutsManager = notificationGutsManager;
        this.mDreamingToLockscreenTransitionViewModel = dreamingToLockscreenTransitionViewModel;
        this.mOccludedToLockscreenTransitionViewModel = occludedToLockscreenTransitionViewModel;
        this.mLockscreenToDreamingTransitionViewModel = lockscreenToDreamingTransitionViewModel;
        this.mGoneToDreamingTransitionViewModel = goneToDreamingTransitionViewModel;
        this.mLockscreenToOccludedTransitionViewModel = lockscreenToOccludedTransitionViewModel;
        this.mKeyguardTransitionInteractor = keyguardTransitionInteractor;
        this.mKeyguardInteractor = keyguardInteractor;
        this.mLockscreenNotificationManager = lockscreenNotificationManager;
        this.mLockscreenNotificationIconsOnlyController = lockscreenNotificationIconsOnlyController;
        this.mEmergencyButtonControllerFactory = factory6;
        this.mKeyguardTouchAnimator = keyguardTouchAnimator;
        KeyguardTouchSwipeCallback keyguardTouchSwipeCallback = new KeyguardTouchSwipeCallback() { // from class: com.android.systemui.shade.NotificationPanelViewController.2
            @Override // com.android.systemui.keyguard.animator.KeyguardTouchSwipeCallback
            public final void callUserActivity() {
                ((CentralSurfacesImpl) NotificationPanelViewController.this.mCentralSurfaces).userActivity();
            }

            @Override // com.android.systemui.keyguard.animator.KeyguardTouchSwipeCallback
            public final void onUnlockExecuted() {
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                notificationPanelViewController.mLogBuilder.setLength(0);
                StringBuilder sb = notificationPanelViewController.mLogBuilder;
                sb.append("makeExpandedInvisible returned : NPV");
                sb.append("\n");
                sb.append(Debug.getCallers(5, " - "));
                ((SecPanelLoggerImpl) notificationPanelViewController.mPanelLogger).addPanelStateInfoLog(notificationPanelViewController.mLogBuilder, true);
                notificationPanelViewController.mView.post(notificationPanelViewController.mHideExpandedRunnable);
            }

            @Override // com.android.systemui.keyguard.animator.KeyguardTouchSwipeCallback
            public final void setMotionAborted() {
                NotificationPanelViewController.this.setMotionAborted();
            }

            @Override // com.android.systemui.keyguard.animator.KeyguardTouchSwipeCallback
            public final void onAffordanceTap() {
            }
        };
        keyguardTouchAnimator.viewInjector = this;
        keyguardTouchAnimator.callback = keyguardTouchSwipeCallback;
        notificationPanelView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.shade.NotificationPanelViewController.3
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                notificationPanelViewController.mViewName = notificationPanelViewController.mResources.getResourceName(notificationPanelViewController.mView.getId());
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
            }
        });
        notificationPanelView.addOnLayoutChangeListener(new ShadeLayoutChangeListener(this, i2));
        TouchHandler touchHandler = getTouchHandler();
        notificationPanelView.setOnTouchListener(touchHandler);
        notificationPanelView.mTouchHandler = touchHandler;
        notificationPanelView.mOnConfigurationChangedListener = new NotificationPanelViewController$$ExternalSyntheticLambda2(this);
        Resources resources = notificationPanelView.getResources();
        this.mResources = resources;
        this.mKeyguardStateController = keyguardStateControllerImpl;
        this.mQsController = quickSettingsController;
        SecQuickSettingsController secQuickSettingsController = quickSettingsController.mSecQuickSettingsController;
        NotificationPanelViewController$$ExternalSyntheticLambda17 notificationPanelViewController$$ExternalSyntheticLambda17 = new NotificationPanelViewController$$ExternalSyntheticLambda17(this, 1);
        secQuickSettingsController.heightAnimatingSupplier = notificationPanelViewController$$ExternalSyntheticLambda17;
        NonInterceptingScrollView nonInterceptingScrollView = secQuickSettingsController.qsScrollView;
        if (nonInterceptingScrollView != null) {
            nonInterceptingScrollView.mHeightAnimatingSupplier = notificationPanelViewController$$ExternalSyntheticLambda17;
        }
        this.mKeyguardIndicationController = keyguardIndicationController;
        this.mStatusBarStateController = (SysuiStatusBarStateController) statusBarStateController;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        FlingAnimationUtils.Builder builder = (FlingAnimationUtils.Builder) provider.get();
        builder.reset();
        builder.mMaxLengthSeconds = 0.6f;
        builder.mSpeedUpFactor = 0.6f;
        this.mFlingAnimationUtils = builder.build();
        builder.reset();
        builder.mMaxLengthSeconds = 0.6f;
        builder.mSpeedUpFactor = 0.6f;
        this.mFlingAnimationUtilsClosing = builder.build();
        builder.reset();
        builder.mMaxLengthSeconds = 0.5f;
        builder.mSpeedUpFactor = 0.6f;
        builder.mX2 = 0.6f;
        builder.mY2 = 0.84f;
        this.mFlingAnimationUtilsDismissing = builder.build();
        this.mLatencyTracker = latencyTracker;
        this.mBounceInterpolator = new BounceInterpolator();
        this.mFalsingManager = falsingManager;
        this.mDozeLog = dozeLog;
        this.mNotificationsDragEnabled = resources.getBoolean(R.bool.config_enableNotificationShadeDrag);
        this.mVibratorHelper = vibratorHelper;
        this.mVibrateOnOpening = resources.getBoolean(R.bool.config_vibrateOnIconAnimation);
        this.mStatusBarTouchableRegionManager = statusBarTouchableRegionManager;
        this.mSystemClock = systemClock;
        this.mKeyguardMediaController = keyguardMediaController;
        this.mMetricsLogger = metricsLogger;
        this.mConfigurationController = configurationController;
        this.mFlingAnimationUtilsBuilder = provider;
        this.mNotificationsQSContainerController = notificationsQSContainerController;
        this.mNotificationListContainer = notificationListContainer;
        this.mNotificationStackSizeCalculator = notificationStackSizeCalculator;
        this.mNavigationBarController = navigationBarController;
        KeyguardBottomAreaViewController keyguardBottomAreaViewController = (KeyguardBottomAreaViewController) provider2.get();
        this.mKeyguardBottomAreaViewController = keyguardBottomAreaViewController;
        keyguardBottomAreaViewController.init();
        notificationsQSContainerController.init();
        this.mNotificationStackScrollLayoutController = notificationStackScrollLayoutController;
        this.mKeyguardStatusViewComponentFactory = factory;
        this.mKeyguardStatusBarViewComponentFactory = factory4;
        this.mDepthController = notificationShadeDepthController;
        this.mContentResolver = contentResolver;
        this.mKeyguardQsUserSwitchComponentFactory = factory2;
        this.mKeyguardUserSwitcherComponentFactory = factory3;
        this.mFragmentService = fragmentService;
        this.mSettingsChangeObserver = new SettingsChangeObserver(handler);
        this.mSplitShadeEnabled = LargeScreenUtils.shouldUseSplitNotificationShade(resources);
        notificationPanelView.setWillNotDraw(true);
        this.mShadeHeaderController = shadeHeaderController;
        this.mLayoutInflater = layoutInflater;
        this.mFeatureFlags = featureFlags;
        Flags flags = Flags.INSTANCE;
        featureFlags.getClass();
        this.mFalsingCollector = falsingCollector;
        this.mPowerManager = powerManager;
        this.mWakeUpCoordinator = notificationWakeUpCoordinator;
        this.mMainDispatcher = coroutineDispatcher;
        this.mAccessibilityManager = accessibilityManager;
        notificationPanelView.setAccessibilityPaneTitle(determineAccessibilityPaneTitle());
        setAlpha(255, false);
        this.mCommandQueue = commandQueue;
        this.mDisplayId = i;
        this.mPulseExpansionHandler = pulseExpansionHandler;
        this.mDozeParameters = dozeParameters;
        this.mScrimController = scrimController;
        this.mUserManager = userManager;
        this.mMediaDataManager = mediaDataManager;
        this.mTapAgainViewController = tapAgainViewController;
        this.mSysUiState = sysUiState;
        this.mPrivacyDialogController = privacyDialogController;
        ((HashSet) statusBarWindowStateController.listeners).add(new StatusBarWindowStateListener() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda3
            @Override // com.android.systemui.statusbar.window.StatusBarWindowStateListener
            public final void onStatusBarWindowStateChanged(int i7) {
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                if (notificationPanelViewController.isCollapsing() || i7 == 0 || ((StatusBarStateControllerImpl) notificationPanelViewController.mStatusBarStateController).mState != 0) {
                    return;
                }
                notificationPanelViewController.collapse(1.0f, false, false);
            }
        });
        this.mKeyguardBypassController = keyguardBypassController;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mLockscreenShadeTransitionController = lockscreenShadeTransitionController;
        lockscreenShadeTransitionController.shadeViewController = this;
        shadeTransitionController.shadeViewController = this;
        dynamicPrivacyController.mListeners.add(new DynamicPrivacyController.Listener() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda4
            @Override // com.android.systemui.statusbar.notification.DynamicPrivacyController.Listener
            public final void onDynamicPrivacyChanged() {
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                if (notificationPanelViewController.mLinearDarkAmount != 0.0f) {
                    return;
                }
                notificationPanelViewController.mAnimateNextPositionUpdate = true;
            }
        });
        quickSettingsController.mExpansionHeightListener = new NotificationPanelViewController$$ExternalSyntheticLambda2(this);
        quickSettingsController.mQsStateUpdateListener = new NotificationPanelViewController$$ExternalSyntheticLambda2(this);
        quickSettingsController.mApplyClippingImmediatelyListener = new NotificationPanelViewController$$ExternalSyntheticLambda2(this);
        quickSettingsController.mFlingQsWithoutClickListener = new NotificationPanelViewController$$ExternalSyntheticLambda2(this);
        quickSettingsController.mExpansionHeightSetToMaxListener = new NotificationPanelViewController$$ExternalSyntheticLambda2(this);
        shadeExpansionStateManager.stateListeners.add(new ShadeStateListener() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda6
            @Override // com.android.systemui.shade.ShadeStateListener
            public final void onPanelStateChanged(int i7) {
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                QuickSettingsController quickSettingsController2 = notificationPanelViewController.mQsController;
                quickSettingsController2.updateExpansionEnabledAmbient();
                if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK && i7 == 1 && !((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.get(KeyguardFoldController.class))).isFoldOpened() && notificationPanelViewController.mScrimController.mState == ScrimState.AOD) {
                    notificationPanelViewController.cancelPendingCollapse(true);
                }
                NotificationPanelView notificationPanelView2 = notificationPanelViewController.mView;
                if (i7 == 2 && notificationPanelViewController.mCurrentPanelState != i7) {
                    quickSettingsController2.setExpandImmediate(false);
                    notificationPanelView2.sendAccessibilityEvent(32);
                }
                if (i7 == 1) {
                    if (notificationPanelViewController.mSplitShadeEnabled && !notificationPanelViewController.isOnKeyguard()) {
                        quickSettingsController2.setExpandImmediate(true);
                    }
                    ShadeControllerImpl.this.makeExpandedVisible(false);
                }
                if (i7 == 0) {
                    quickSettingsController2.setExpandImmediate(false);
                    if (!notificationPanelViewController.mIsAnyMultiShadeExpanded) {
                        StringBuilder sb = notificationPanelViewController.mLogBuilder;
                        sb.setLength(0);
                        sb.append("makeExpandedInvisible returned : NPV");
                        sb.append("\n");
                        sb.append(Debug.getCallers(5, " - "));
                        ((SecPanelLoggerImpl) notificationPanelViewController.mPanelLogger).addPanelStateInfoLog(sb, true);
                        notificationPanelView2.post(notificationPanelViewController.mMaybeHideExpandedRunnable);
                    }
                }
                notificationPanelViewController.mCurrentPanelState = i7;
            }
        });
        if (NotiRune.NOTI_STYLE_TABLET_BG) {
            shadeExpansionStateManager.shadeStateEventsListeners.addIfAbsent(new ShadeStateEvents.ShadeStateEventsListener() { // from class: com.android.systemui.shade.NotificationPanelViewController.4
                @Override // com.android.systemui.shade.ShadeStateEvents.ShadeStateEventsListener
                public final void onExpandImmediateChanged(boolean z) {
                    SecNsslOpaqueBgHelper secNsslOpaqueBgHelper;
                    NotificationStackScrollLayout notificationStackScrollLayout = NotificationPanelViewController.this.mNotificationStackScrollLayoutController.mView;
                    notificationStackScrollLayout.mQsExpandedImmediate = z;
                    if (!NotiRune.NOTI_STYLE_TABLET_BG || (secNsslOpaqueBgHelper = notificationStackScrollLayout.mOpaqueBgHelper) == null) {
                        return;
                    }
                    secNsslOpaqueBgHelper.mQsExpandedImmediate = z;
                }
            });
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.mBottomAreaShadeAlphaAnimator = ofFloat;
        ofFloat.addUpdateListener(new NotificationPanelViewController$$ExternalSyntheticLambda21(this, 1));
        ofFloat.setDuration(160L);
        ofFloat.setInterpolator(interpolator);
        this.mConversationNotificationManager = conversationNotificationManager;
        this.mAuthController = authController;
        this.mLockIconViewController = secLockIconViewController;
        this.mScreenOffAnimationController = screenOffAnimationController;
        this.mUnlockedScreenOffAnimationController = unlockedScreenOffAnimationController;
        this.mLastDownEvents = new NPVCDownEventState.Buffer(50);
        this.mKeyguardFaceAuthInteractor = keyguardFaceAuthInteractor;
        this.mIsGestureNavigation = QuickStepContract.isGesturalMode(navigationModeController.addListener(new NavigationModeController.ModeChangedListener() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda7
            @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
            public final void onNavigationModeChanged(int i7) {
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                notificationPanelViewController.getClass();
                notificationPanelViewController.mIsGestureNavigation = QuickStepContract.isGesturalMode(i7);
            }
        }));
        notificationPanelView.setBackgroundColor(0);
        ShadeAttachStateChangeListener shadeAttachStateChangeListener = new ShadeAttachStateChangeListener(this, i2);
        notificationPanelView.addOnAttachStateChangeListener(shadeAttachStateChangeListener);
        if (notificationPanelView.isAttachedToWindow()) {
            shadeAttachStateChangeListener.onViewAttachedToWindow(notificationPanelView);
        }
        notificationPanelView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda8
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                SecQSPanelController secQSPanelController;
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                notificationPanelViewController.getClass();
                Insets insetsIgnoringVisibility = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
                notificationPanelViewController.mDisplayTopInset = insetsIgnoringVisibility.top;
                int i7 = insetsIgnoringVisibility.right;
                notificationPanelViewController.mDisplayRightInset = i7;
                int i8 = insetsIgnoringVisibility.left;
                notificationPanelViewController.mDisplayLeftInset = i8;
                QuickSettingsController quickSettingsController2 = notificationPanelViewController.mQsController;
                quickSettingsController2.mDisplayLeftInset = i8;
                quickSettingsController2.mDisplayRightInset = i7;
                boolean z = notificationPanelViewController.mNavBarKeyboardButtonShowing;
                NotificationPanelView notificationPanelView2 = notificationPanelViewController.mView;
                notificationPanelViewController.mNavigationBarBottomHeight = z ? notificationPanelView2.getContext().getResources().getDimensionPixelSize(android.R.dimen.notification_content_margin_end) : notificationPanelViewController.mBarState == 0 ? windowInsets.getInsets(WindowInsets.Type.navigationBars()).bottom : windowInsets.getStableInsetBottom();
                int height = notificationPanelView2.getHeight();
                int i9 = notificationPanelViewController.mNavigationBarBottomHeight;
                NotificationStackScrollLayout notificationStackScrollLayout = notificationPanelViewController.mNotificationStackScrollLayoutController.mView;
                notificationStackScrollLayout.mAmbientState.mMaxHeadsUpTranslation = height - i9;
                notificationStackScrollLayout.mStateAnimator.mHeadsUpAppearHeightBottom = height;
                notificationStackScrollLayout.requestChildrenUpdate();
                SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
                int i10 = notificationPanelViewController.mDisplayTopInset;
                Context context = notificationPanelView2.getContext();
                secQSPanelResourcePicker.getClass();
                if (SecQSPanelResourcePicker.isPortrait(context)) {
                    secQSPanelResourcePicker.mCutoutHeight = i10;
                } else {
                    secQSPanelResourcePicker.mCutoutHeightLandscape = i10;
                }
                SecQSPanelResourcePicker secQSPanelResourcePicker2 = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
                int i11 = notificationPanelViewController.mNavigationBarBottomHeight;
                Context context2 = notificationPanelView2.getContext();
                secQSPanelResourcePicker2.getClass();
                if (SecQSPanelResourcePicker.isPortrait(context2)) {
                    secQSPanelResourcePicker2.mNavBarHeight = i11;
                } else {
                    secQSPanelResourcePicker2.mNavBarHeightLandscape = i11;
                }
                int i12 = notificationPanelViewController.mDisplayTopInset;
                int i13 = notificationPanelViewController.mNavigationBarBottomHeight;
                SecQuickSettingsController secQuickSettingsController2 = quickSettingsController2.mSecQuickSettingsController;
                Object obj = secQuickSettingsController2.qsSupplier.get();
                QSFragment qSFragment = obj instanceof QSFragment ? (QSFragment) obj : null;
                if (qSFragment != null && (secQSPanelController = qSFragment.mQSPanelController) != null && (i12 != secQuickSettingsController2.lastDisplayTopInset || i13 != secQuickSettingsController2.lastNavigationBarBottomHeight)) {
                    secQuickSettingsController2.lastDisplayTopInset = i12;
                    secQuickSettingsController2.lastNavigationBarBottomHeight = i13;
                    secQSPanelController.updateResources();
                }
                notificationPanelViewController.mNotificationsQSContainerController.updateConstraints();
                if (QpRune.PANEL_DATA_USAGE_LABEL) {
                    ((DataUsageLabelManager) notificationPanelViewController.mDataUsageLabelManagerLazy.get()).updateNavBarHeight(notificationPanelViewController.mNavigationBarBottomHeight);
                }
                return windowInsets;
            }
        });
        this.mPluginLockMediator = pluginLockMediator;
        ((PluginLockMediatorImpl) pluginLockMediator).registerStateCallback(this);
        this.mPluginLockData = pluginLockData;
        final int i7 = 1;
        this.mKeyguardUnfoldTransition = optional.map(new Function() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i7) {
                    case 0:
                        return Float.valueOf(((NotificationPanelView) obj).mCurrentPanelAlpha);
                    case 1:
                        return ((SysUIUnfoldComponent) obj).getKeyguardUnfoldTransition();
                    default:
                        return ((SysUIUnfoldComponent) obj).getNotificationPanelUnfoldAnimationController();
                }
            }
        });
        final int i8 = 2;
        this.mNotificationPanelUnfoldAnimationController = optional.map(new Function() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i8) {
                    case 0:
                        return Float.valueOf(((NotificationPanelView) obj).mCurrentPanelAlpha);
                    case 1:
                        return ((SysUIUnfoldComponent) obj).getKeyguardUnfoldTransition();
                    default:
                        return ((SysUIUnfoldComponent) obj).getNotificationPanelUnfoldAnimationController();
                }
            }
        });
        updateUserSwitcherFlags();
        this.mKeyguardBottomAreaViewModel = keyguardBottomAreaViewModel;
        this.mKeyguardBottomAreaInteractor = keyguardBottomAreaInteractor;
        this.mActivityStarter = activityStarter;
        this.mClockPositionAlgorithm = keyguardClockPositionAlgorithm;
        PluginFaceWidgetManager pluginFaceWidgetManager = (PluginFaceWidgetManager) Dependency.get(PluginFaceWidgetManager.class);
        if (pluginFaceWidgetManager != null) {
            Log.d("PluginFaceWidgetManager", "setNPVController() controller = " + this);
            pluginFaceWidgetManager.mNPVController = this;
        }
        this.mPunchHoleVIViewControllerFactory = factory5;
        if (QpRune.PANEL_DATA_USAGE_LABEL) {
            this.mDataUsageLabelManagerLazy = lazy3;
        }
        this.mKeyguardEditModeController = keyguardEditModeController;
        KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = (KeyguardEditModeControllerImpl) keyguardEditModeController;
        keyguardEditModeControllerImpl.bind(notificationPanelView);
        keyguardEditModeControllerImpl.onStartActivityListener = new Function0() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                notificationPanelViewController.getClass();
                KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_EDIT_MODE);
                notificationPanelViewController.mIsLaunchTransitionFinished = true;
                notificationPanelViewController.mStatusBarKeyguardViewManager.setLaunchEditMode();
                return null;
            }
        };
        ((ArrayList) keyguardEditModeControllerImpl.listeners).add(new KeyguardEditModeController.Listener() { // from class: com.android.systemui.shade.NotificationPanelViewController.5
            @Override // com.android.systemui.keyguard.KeyguardEditModeController.Listener
            public final void onAnimationStarted(boolean z) {
                if (z) {
                    ((CentralSurfacesImpl) NotificationPanelViewController.this.mCentralSurfaces).userActivity();
                }
            }

            @Override // com.android.systemui.keyguard.KeyguardEditModeController.Listener
            public final void onAnimationEnded() {
            }
        });
        if (LsRune.KEYGUARD_DCM_LIVE_UX) {
            this.mMascotViewContainer = dcmMascotViewContainer;
        }
        onFinishInflate();
        keyguardUnlockAnimationController.listeners.add(new KeyguardUnlockAnimationController.KeyguardUnlockAnimationListener() { // from class: com.android.systemui.shade.NotificationPanelViewController.6
            @Override // com.android.systemui.keyguard.KeyguardUnlockAnimationController.KeyguardUnlockAnimationListener
            public final void onUnlockAnimationFinished() {
                VibrationEffect vibrationEffect = NotificationPanelViewController.ADDITIONAL_TAP_REQUIRED_VIBRATION_EFFECT;
                NotificationPanelViewController.this.positionClockAndNotifications(true);
            }

            @Override // com.android.systemui.keyguard.KeyguardUnlockAnimationController.KeyguardUnlockAnimationListener
            public final void onUnlockAnimationStarted(boolean z, boolean z2) {
                PluginKeyguardStatusView pluginKeyguardStatusView;
                VibrationEffect vibrationEffect = NotificationPanelViewController.ADDITIONAL_TAP_REQUIRED_VIBRATION_EFFECT;
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                notificationPanelViewController.getClass();
                PluginFaceWidgetManager pluginFaceWidgetManager2 = (PluginFaceWidgetManager) Dependency.get(PluginFaceWidgetManager.class);
                if (pluginFaceWidgetManager2 != null && (pluginKeyguardStatusView = pluginFaceWidgetManager2.mFaceWidgetPlugin) != null) {
                    pluginKeyguardStatusView.dismissFaceWidgetDashBoard();
                }
                boolean z3 = notificationPanelViewController.mTracking;
                NotificationShadeDepthController notificationShadeDepthController2 = notificationPanelViewController.mDepthController;
                if (notificationShadeDepthController2.blursDisabledForUnlock != z3) {
                    notificationShadeDepthController2.blursDisabledForUnlock = z3;
                    notificationShadeDepthController2.scheduleUpdate();
                }
                if (!z || z2) {
                    return;
                }
                if (notificationPanelViewController.mTracking || notificationPanelViewController.mIsFlinging) {
                    notificationPanelViewController.onTrackingStopped(false);
                    notificationPanelViewController.instantCollapse();
                }
            }
        });
        this.mAlternateBouncerInteractor = alternateBouncerInteractor;
        dumpManager.registerDumpable(this);
        notificationShelfManager.notificationPanelController = this;
        this.mQsStatusEventLog = qsStatusEventLog;
        this.mMultiWindowEdgeDetector = new MultiWindowEdgeDetector(notificationPanelView.getContext(), "QuickPannel");
        this.mIndicatorTouchHandler = indicatorTouchHandler;
        this.mPanelLogger = secPanelLogger;
        int i9 = 2;
        int i10 = 4;
        new NotificationPanelViewControllerAgent(new NotificationPanelViewController$$ExternalSyntheticLambda1(this, 7), new NotificationPanelViewController$$ExternalSyntheticLambda0(this, i9), new NotificationPanelViewController$$ExternalSyntheticLambda17(this, i9), new NotificationPanelViewController$$ExternalSyntheticLambda17(this, i6), new NotificationPanelViewController$$ExternalSyntheticLambda0(this, i6), new NotificationPanelViewController$$ExternalSyntheticLambda0(this, i10), new NotificationPanelViewController$$ExternalSyntheticLambda17(this, i10));
        lockscreenNotificationIconsOnlyController.getClass();
        Log.d("LockscreenNotificationIconsOnlyController", "setNPVController() controller = " + this);
        lockscreenNotificationIconsOnlyController.mNPVController = this;
        this.mKeyguardWallpaperController = keyguardWallpaperController;
        this.mWallpaperImageCreator = wallpaperImageInjectCreator;
        this.mPostCollapseRunnable = new NotificationPanelViewController$$ExternalSyntheticLambda0(this, 5);
        this.mQuickQsOffsetHeight = SystemBarUtils.getQuickQsOffsetHeight(notificationPanelView.getContext());
        PanelScreenShotLogger.INSTANCE.addLogProvider("NotificationPanelView", this);
        if (LsRune.SUBSCREEN_WATCHFACE) {
            this.mSubScreenManagerLazy = lazy4;
        }
        if (LsRune.COVER_VIRTUAL_DISPLAY) {
            this.mCoverScreenManagerLazy = lazy5;
        }
        this.mPluginAODManagerLazy = lazy;
        this.mPluginLockStarManagerLazy = lazy6;
    }

    public final void abortAnimations() {
        cancelHeightAnimator();
        NotificationPanelView notificationPanelView = this.mView;
        NotificationPanelViewController$$ExternalSyntheticLambda0 notificationPanelViewController$$ExternalSyntheticLambda0 = this.mPostCollapseRunnable;
        if (notificationPanelViewController$$ExternalSyntheticLambda0 != null) {
            notificationPanelView.removeCallbacks(notificationPanelViewController$$ExternalSyntheticLambda0);
        }
        notificationPanelView.removeCallbacks(this.mFlingCollapseRunnable);
    }

    public final void animateCollapseQs(boolean z) {
        if (this.mSplitShadeEnabled) {
            collapse(1.0f, true, false);
            return;
        }
        QuickSettingsController quickSettingsController = this.mQsController;
        ValueAnimator valueAnimator = quickSettingsController.mExpansionAnimator;
        if (valueAnimator != null) {
            if (!quickSettingsController.mAnimatorExpand) {
                return;
            }
            float f = quickSettingsController.mExpansionHeight;
            valueAnimator.cancel();
            quickSettingsController.setExpansionHeight(f);
        }
        quickSettingsController.flingQs(0.0f, z ? 2 : 1);
    }

    public final void applyBackScaling(float f) {
        View view;
        if (this.mNotificationContainerParent == null) {
            return;
        }
        float lerp = MathUtils.lerp(1.0f, 0.9f, f);
        NotificationsQuickSettingsContainer notificationsQuickSettingsContainer = this.mNotificationContainerParent;
        boolean z = this.mSplitShadeEnabled;
        if (notificationsQuickSettingsContainer.mStackScroller != null && (view = notificationsQuickSettingsContainer.mQSContainer) != null) {
            view.getBoundsOnScreen(notificationsQuickSettingsContainer.mUpperRect);
            notificationsQuickSettingsContainer.mStackScroller.getBoundsOnScreen(notificationsQuickSettingsContainer.mBoundingBoxRect);
            notificationsQuickSettingsContainer.mBoundingBoxRect.union(notificationsQuickSettingsContainer.mUpperRect);
            float centerX = notificationsQuickSettingsContainer.mBoundingBoxRect.centerX();
            float centerY = notificationsQuickSettingsContainer.mBoundingBoxRect.centerY();
            notificationsQuickSettingsContainer.mQSContainer.setPivotX(centerX);
            notificationsQuickSettingsContainer.mQSContainer.setPivotY(centerY);
            notificationsQuickSettingsContainer.mQSContainer.setScaleX(lerp);
            notificationsQuickSettingsContainer.mQSContainer.setScaleY(lerp);
            View view2 = notificationsQuickSettingsContainer.mStackScroller;
            if (z) {
                centerX = 0.0f;
            }
            view2.setPivotX(centerX);
            notificationsQuickSettingsContainer.mStackScroller.setPivotY(centerY);
            notificationsQuickSettingsContainer.mStackScroller.setScaleX(lerp);
            notificationsQuickSettingsContainer.mStackScroller.setScaleY(lerp);
        }
        ScrimController scrimController = this.mScrimController;
        scrimController.mNotificationsScrim.setScaleX(lerp);
        scrimController.mNotificationsScrim.setScaleY(lerp);
    }

    public final int calculatePanelHeightShade() {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        int height = notificationStackScrollLayoutController.getHeight() - notificationStackScrollLayoutController.mView.getEmptyBottomMargin();
        return this.mBarState == 1 ? Math.max(height, this.mClockPositionAlgorithm.mKeyguardStatusHeight + ((int) notificationStackScrollLayoutController.mView.mIntrinsicContentHeight)) : height;
    }

    public final boolean canBeCollapsed() {
        CentralSurfaces centralSurfaces = this.mCentralSurfaces;
        if (centralSurfaces == null || (((CentralSurfacesImpl) centralSurfaces).mDisabled1 & 65536) == 0) {
            return (isFullyCollapsed() || this.mTracking || this.mClosing) ? false : true;
        }
        return true;
    }

    public boolean canCollapsePanelOnTouch() {
        QuickSettingsController quickSettingsController = this.mQsController;
        if (!quickSettingsController.mExpanded && this.mBarState == 1) {
            return true;
        }
        NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
        if (notificationStackScrollLayout.mOwnScrollY >= notificationStackScrollLayout.getScrollRange()) {
            return true;
        }
        return !this.mSplitShadeEnabled && (quickSettingsController.mExpanded || this.mIsPanelCollapseOnQQS);
    }

    public void cancelHeightAnimator() {
        ValueAnimator valueAnimator = this.mHeightAnimator;
        if (valueAnimator != null) {
            if (valueAnimator.isRunning()) {
                this.mPanelUpdateWhenAnimatorEnds = false;
            }
            this.mHeightAnimator.cancel();
        }
        endClosing();
    }

    public final void cancelPendingCollapse(boolean z) {
        NotificationPanelView notificationPanelView = this.mView;
        if (z) {
            notificationPanelView.removeCallbacks(this.mHideExpandedRunnable);
        }
        com.android.systemui.keyguard.Log.m138d("KeyguardVisible", "cancelPendingPanelCollapse " + z);
        ((KeyguardFastBioUnlockController) Dependency.get(KeyguardFastBioUnlockController.class)).reset();
        if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK) {
            ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).mHelper.resetForceInvisible(false);
        }
        notificationPanelView.removeCallbacks(this.mMaybeHideExpandedRunnable);
    }

    public final void closeQsIfPossible() {
        boolean z = isShadeFullyExpanded() || isExpandingOrCollapsing();
        if (this.mSplitShadeEnabled && z) {
            return;
        }
        this.mQsController.closeQs();
    }

    public final void collapse(float f, boolean z, boolean z2) {
        boolean z3;
        if (!z || isFullyCollapsed()) {
            resetViews(false);
            setExpandedFraction(0.0f);
            z3 = false;
        } else {
            collapse(f, z2);
            z3 = true;
        }
        if (!z3) {
            ShadeExpansionStateManager shadeExpansionStateManager = this.mShadeExpansionStateManager;
            ShadeExpansionStateManagerKt.panelStateToString(shadeExpansionStateManager.state);
            ShadeExpansionStateManagerKt.panelStateToString(0);
            if (shadeExpansionStateManager.state != 0) {
                shadeExpansionStateManager.updateStateInternal(0);
            }
        }
        ((NotificationGutsManager) Dependency.get(NotificationGutsManager.class)).getClass();
    }

    public int computeMaxKeyguardNotifications() {
        int i;
        PluginLockDataImpl pluginLockDataImpl = (PluginLockDataImpl) this.mPluginLockData;
        if (pluginLockDataImpl.isAvailable()) {
            this.mRecomputedReason = "TYPE_N_CARD";
            if (pluginLockDataImpl.mData == null) {
                return 3;
            }
            return pluginLockDataImpl.isLandscape() ? pluginLockDataImpl.mData.getNotificationData().getCardData().getNotiCardNumbersLand().intValue() : pluginLockDataImpl.mData.getNotificationData().getCardData().getNotiCardNumbers().intValue();
        }
        if (((PluginLockMediatorImpl) this.mPluginLockMediator).isDynamicLockEnabled() && (i = this.mNotiCardCount) != -1) {
            this.mRecomputedReason = "isDynamicLockEnabled";
            return i;
        }
        if (this.mAmbientState.mFractionToShade > 0.0f) {
            this.mRecomputedReason = "mAmbientState.getFractionToShade()";
            return this.mMaxAllowedKeyguardNotifications;
        }
        this.mRecomputedReason = "computeMaxKeyguardNotifications";
        NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScrollLayoutController.mView;
        float verticalSpaceForLockscreenNotifications = getVerticalSpaceForLockscreenNotifications();
        float verticalSpaceForLockscreenShelf = getVerticalSpaceForLockscreenShelf();
        Flags flags = Flags.INSTANCE;
        this.mFeatureFlags.getClass();
        return this.mNotificationStackSizeCalculator.computeMaxKeyguardNotifications(notificationStackScrollLayout, verticalSpaceForLockscreenNotifications, verticalSpaceForLockscreenShelf, this.mNotificationShelfController.getIntrinsicHeight());
    }

    public final ValueAnimator createHeightAnimator(final float f, final float f2) {
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mExpandedHeight, f);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(f2, f, ofFloat, this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda13
            public final /* synthetic */ NotificationPanelViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                NotificationPanelViewController notificationPanelViewController = this.f$0;
                notificationPanelViewController.getClass();
                notificationPanelViewController.setExpandedHeightInternal(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        return ofFloat;
    }

    public final String determineAccessibilityPaneTitle() {
        Resources resources = this.mResources;
        QuickSettingsController quickSettingsController = this.mQsController;
        if (quickSettingsController != null) {
            if (quickSettingsController.isQsFragmentCreated() && quickSettingsController.mQs.isCustomizing()) {
                return resources.getString(R.string.accessibility_desc_quick_settings_edit);
            }
        }
        return (quickSettingsController == null || quickSettingsController.mExpansionHeight == 0.0f || !quickSettingsController.mFullyExpanded) ? this.mBarState == 1 ? this.mUpdateMonitor.isKeyguardUnlocking() ? "" : resources.getString(R.string.ksh_group_system_lock_screen) : resources.getString(R.string.accessibility_desc_notification_shade) : this.mSplitShadeEnabled ? resources.getString(R.string.accessibility_desc_qs_notification_shade) : resources.getString(R.string.accessibility_desc_quick_settings);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        Lazy lazy;
        ViewGroup parentViewGroup;
        printWriter.println("NotificationPanelView:");
        PrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.increaseIndent();
        if (this.mKeyguardBottomArea != null) {
            this.mKeyguardBottomAreaViewController.dump(printWriter, strArr);
        }
        if (QpRune.PANEL_DATA_USAGE_LABEL && (lazy = this.mDataUsageLabelManagerLazy) != null) {
            DataUsageLabelManager dataUsageLabelManager = (DataUsageLabelManager) lazy.get();
            StringBuilder sb = new StringBuilder("DataUsageLabelManager");
            sb.append(" InsetNavigationBarBottomHeight:" + dataUsageLabelManager.mInsetNavigationBarBottomHeight);
            DataUsageLabelParent dataUsageLabelParent = dataUsageLabelManager.mDataUsageLabelParent;
            if (dataUsageLabelParent != null && (parentViewGroup = dataUsageLabelParent.getParentViewGroup()) != null) {
                sb.append(", parentAlpha:" + parentViewGroup.getAlpha());
                sb.append(", parentVisibility:" + parentViewGroup.getVisibility() + " (V0-I4-G8)");
                StringBuilder sb2 = new StringBuilder(", parentHeight:");
                sb2.append(parentViewGroup.getHeight());
                sb.append(sb2.toString());
                sb.append(", parentPaddingBottom:" + parentViewGroup.getPaddingBottom());
                sb.append(", parentPaddingBottom:" + parentViewGroup.getPaddingBottom());
            }
            sb.append(dataUsageLabelManager.mNavSettingsHelper.getDumpText());
            if (dataUsageLabelManager.mLabelView != null) {
                StringBuilder sb3 = new StringBuilder(", childTextView:");
                DataUsageLabelView dataUsageLabelView = dataUsageLabelManager.mLabelView;
                StringBuilder sb4 = new StringBuilder("DataUsageLabelCommonView");
                sb4.append(" : " + dataUsageLabelView.getText().toString());
                sb4.append(" : " + Integer.toHexString(dataUsageLabelView.getCurrentTextColor()));
                sb3.append(sb4.toString());
                sb.append(sb3.toString());
            }
            printWriter.println(sb.toString());
        }
        asIndenting.print("mDownTime=");
        asIndenting.println(this.mDownTime);
        asIndenting.print("mTouchSlopExceededBeforeDown=");
        asIndenting.println(this.mTouchSlopExceededBeforeDown);
        asIndenting.print("mIsLaunchAnimationRunning=");
        asIndenting.println(this.mIsLaunchAnimationRunning);
        asIndenting.print("mOverExpansion=");
        asIndenting.println(0.0f);
        asIndenting.print("mExpandedHeight=");
        asIndenting.println(this.mExpandedHeight);
        asIndenting.print("mTracking=");
        asIndenting.println(this.mTracking);
        asIndenting.print("mHintAnimationRunning=");
        asIndenting.println(this.mHintAnimationRunning);
        asIndenting.print("mExpanding=");
        asIndenting.println(this.mExpanding);
        asIndenting.print("mSplitShadeEnabled=");
        asIndenting.println(this.mSplitShadeEnabled);
        asIndenting.print("mKeyguardNotificationBottomPadding=");
        asIndenting.println(this.mKeyguardNotificationBottomPadding);
        asIndenting.print("mKeyguardNotificationTopPadding=");
        asIndenting.println(this.mKeyguardNotificationTopPadding);
        asIndenting.print("mMaxAllowedKeyguardNotifications=");
        asIndenting.println(this.mMaxAllowedKeyguardNotifications);
        asIndenting.print("mAnimateNextPositionUpdate=");
        asIndenting.println(this.mAnimateNextPositionUpdate);
        asIndenting.print("mPanelExpanded=");
        asIndenting.println(this.mPanelExpanded);
        asIndenting.print("mKeyguardQsUserSwitchEnabled=");
        asIndenting.println(this.mKeyguardQsUserSwitchEnabled);
        asIndenting.print("mKeyguardUserSwitcherEnabled=");
        asIndenting.println(this.mKeyguardUserSwitcherEnabled);
        asIndenting.print("mDozing=");
        asIndenting.println(this.mDozing);
        asIndenting.print("mDozingOnDown=");
        asIndenting.println(this.mDozingOnDown);
        asIndenting.print("mBouncerShowing=");
        asIndenting.println(this.mBouncerShowing);
        asIndenting.print("mBarState=");
        asIndenting.println(this.mBarState);
        asIndenting.print("mStatusBarMinHeight=");
        asIndenting.println(this.mStatusBarMinHeight);
        asIndenting.print("mStatusBarHeaderHeightKeyguard=");
        asIndenting.println(this.mStatusBarHeaderHeightKeyguard);
        asIndenting.print("mOverStretchAmount=");
        asIndenting.println(this.mOverStretchAmount);
        asIndenting.print("mDownX=");
        asIndenting.println(this.mDownX);
        asIndenting.print("mDownY=");
        asIndenting.println(this.mDownY);
        asIndenting.print("mDisplayTopInset=");
        asIndenting.println(this.mDisplayTopInset);
        asIndenting.print("mDisplayRightInset=");
        asIndenting.println(this.mDisplayRightInset);
        asIndenting.print("mDisplayLeftInset=");
        asIndenting.println(this.mDisplayLeftInset);
        asIndenting.print("mIsExpandingOrCollapsing=");
        asIndenting.println(this.mIsExpandingOrCollapsing);
        asIndenting.print("mHeadsUpStartHeight=");
        asIndenting.println(this.mHeadsUpStartHeight);
        asIndenting.print("mListenForHeadsUp=");
        asIndenting.println(this.mListenForHeadsUp);
        asIndenting.print("mNavigationBarBottomHeight=");
        asIndenting.println(this.mNavigationBarBottomHeight);
        asIndenting.print("mExpandingFromHeadsUp=");
        asIndenting.println(this.mExpandingFromHeadsUp);
        asIndenting.print("mCollapsedOnDown=");
        asIndenting.println(this.mCollapsedOnDown);
        asIndenting.print("mClosingWithAlphaFadeOut=");
        asIndenting.println(this.mClosingWithAlphaFadeOut);
        asIndenting.print("mHeadsUpAnimatingAway=");
        asIndenting.println(this.mHeadsUpAnimatingAway);
        asIndenting.print("mShowIconsWhenExpanded=");
        asIndenting.println(this.mShowIconsWhenExpanded);
        asIndenting.print("mIndicationBottomPadding=");
        asIndenting.println(this.mIndicationBottomPadding);
        asIndenting.print("mAmbientIndicationBottomPadding=");
        asIndenting.println(0);
        asIndenting.print("mIsFullWidth=");
        asIndenting.println(this.mIsFullWidth);
        asIndenting.print("mBlockingExpansionForCurrentTouch=");
        asIndenting.println(this.mBlockingExpansionForCurrentTouch);
        asIndenting.print("mExpectingSynthesizedDown=");
        asIndenting.println(this.mExpectingSynthesizedDown);
        asIndenting.print("mLastEventSynthesizedDown=");
        asIndenting.println(this.mLastEventSynthesizedDown);
        asIndenting.print("mInterpolatedDarkAmount=");
        asIndenting.println(this.mInterpolatedDarkAmount);
        asIndenting.print("mLinearDarkAmount=");
        asIndenting.println(this.mLinearDarkAmount);
        asIndenting.print("mPulsing=");
        asIndenting.println(this.mPulsing);
        asIndenting.print("mHideIconsDuringLaunchAnimation=");
        asIndenting.println(this.mHideIconsDuringLaunchAnimation);
        asIndenting.print("mStackScrollerMeasuringPass=");
        asIndenting.println(this.mStackScrollerMeasuringPass);
        asIndenting.print("mPanelAlpha=");
        asIndenting.println(this.mPanelAlpha);
        asIndenting.print("mBottomAreaShadeAlpha=");
        asIndenting.println(this.mBottomAreaShadeAlpha);
        asIndenting.print("mHeadsUpInset=");
        asIndenting.println(this.mHeadsUpInset);
        asIndenting.print("mHeadsUpPinnedMode=");
        asIndenting.println(this.mHeadsUpPinnedMode);
        asIndenting.print("mAllowExpandForSmallExpansion=");
        asIndenting.println(this.mAllowExpandForSmallExpansion);
        asIndenting.print("mMaxOverscrollAmountForPulse=");
        asIndenting.println(this.mMaxOverscrollAmountForPulse);
        asIndenting.print("mIsPanelCollapseOnQQS=");
        asIndenting.println(this.mIsPanelCollapseOnQQS);
        asIndenting.print("mKeyguardOnlyContentAlpha=");
        asIndenting.println(this.mKeyguardOnlyContentAlpha);
        asIndenting.print("mKeyguardOnlyTransitionTranslationY=");
        asIndenting.println(this.mKeyguardOnlyTransitionTranslationY);
        asIndenting.print("mUdfpsMaxYBurnInOffset=");
        asIndenting.println(this.mUdfpsMaxYBurnInOffset);
        asIndenting.print("mIsGestureNavigation=");
        asIndenting.println(this.mIsGestureNavigation);
        asIndenting.print("mOldLayoutDirection=");
        asIndenting.println(this.mOldLayoutDirection);
        asIndenting.print("mMinFraction=");
        asIndenting.println(this.mMinFraction);
        asIndenting.print("mSplitShadeFullTransitionDistance=");
        asIndenting.println(this.mSplitShadeFullTransitionDistance);
        asIndenting.print("mSplitShadeScrimTransitionDistance=");
        asIndenting.println(this.mSplitShadeScrimTransitionDistance);
        asIndenting.print("mMinExpandHeight=");
        asIndenting.println(this.mMinExpandHeight);
        asIndenting.print("mPanelUpdateWhenAnimatorEnds=");
        asIndenting.println(this.mPanelUpdateWhenAnimatorEnds);
        asIndenting.print("mHasVibratedOnOpen=");
        asIndenting.println(this.mHasVibratedOnOpen);
        asIndenting.print("mFixedDuration=");
        asIndenting.println(this.mFixedDuration);
        asIndenting.print("mPanelFlingOvershootAmount=");
        asIndenting.println(this.mPanelFlingOvershootAmount);
        asIndenting.print("mLastGesturedOverExpansion=");
        asIndenting.println(this.mLastGesturedOverExpansion);
        asIndenting.print("mIsSpringBackAnimation=");
        asIndenting.println(this.mIsSpringBackAnimation);
        asIndenting.print("mSplitShadeEnabled=");
        asIndenting.println(this.mSplitShadeEnabled);
        asIndenting.print("mHintDistance=");
        asIndenting.println(this.mHintDistance);
        asIndenting.print("mInitialOffsetOnTouch=");
        asIndenting.println(this.mInitialOffsetOnTouch);
        asIndenting.print("mCollapsedAndHeadsUpOnDown=");
        asIndenting.println(this.mCollapsedAndHeadsUpOnDown);
        asIndenting.print("mExpandedFraction=");
        asIndenting.println(this.mExpandedFraction);
        asIndenting.print("mExpansionDragDownAmountPx=");
        asIndenting.println(this.mExpansionDragDownAmountPx);
        asIndenting.print("mPanelClosedOnDown=");
        asIndenting.println(this.mPanelClosedOnDown);
        asIndenting.print("mHasLayoutedSinceDown=");
        asIndenting.println(this.mHasLayoutedSinceDown);
        asIndenting.print("mUpdateFlingVelocity=");
        asIndenting.println(this.mUpdateFlingVelocity);
        asIndenting.print("mUpdateFlingOnLayout=");
        asIndenting.println(this.mUpdateFlingOnLayout);
        asIndenting.print("mClosing=");
        asIndenting.println(this.mClosing);
        asIndenting.print("mTouchSlopExceeded=");
        asIndenting.println(this.mTouchSlopExceeded);
        asIndenting.print("mTrackingPointer=");
        asIndenting.println(this.mTrackingPointer);
        asIndenting.print("mTouchSlop=");
        asIndenting.println(this.mTouchSlop);
        asIndenting.print("mSlopMultiplier=");
        asIndenting.println(this.mSlopMultiplier);
        asIndenting.print("mTouchAboveFalsingThreshold=");
        asIndenting.println(this.mTouchAboveFalsingThreshold);
        asIndenting.print("mTouchStartedInEmptyArea=");
        asIndenting.println(this.mTouchStartedInEmptyArea);
        asIndenting.print("mMotionAborted=");
        asIndenting.println(this.mMotionAborted);
        asIndenting.print("mUpwardsWhenThresholdReached=");
        asIndenting.println(this.mUpwardsWhenThresholdReached);
        asIndenting.print("mAnimatingOnDown=");
        asIndenting.println(this.mAnimatingOnDown);
        asIndenting.print("mHandlingPointerUp=");
        asIndenting.println(this.mHandlingPointerUp);
        asIndenting.print("mInstantExpanding=");
        asIndenting.println(this.mInstantExpanding);
        asIndenting.print("mAnimateAfterExpanding=");
        asIndenting.println(this.mAnimateAfterExpanding);
        asIndenting.print("mIsFlinging=");
        asIndenting.println(this.mIsFlinging);
        asIndenting.print("mViewName=");
        asIndenting.println(this.mViewName);
        asIndenting.print("mInitialExpandY=");
        asIndenting.println(this.mInitialExpandY);
        asIndenting.print("mInitialExpandX=");
        asIndenting.println(this.mInitialExpandX);
        asIndenting.print("mTouchDisabled=");
        asIndenting.println(this.mTouchDisabled);
        asIndenting.print("mInitialTouchFromKeyguard=");
        asIndenting.println(this.mInitialTouchFromKeyguard);
        asIndenting.print("mNextCollapseSpeedUpFactor=");
        asIndenting.println(this.mNextCollapseSpeedUpFactor);
        asIndenting.print("mGestureWaitForTouchSlop=");
        asIndenting.println(this.mGestureWaitForTouchSlop);
        asIndenting.print("mIgnoreXTouchSlop=");
        asIndenting.println(this.mIgnoreXTouchSlop);
        asIndenting.print("mExpandLatencyTracking=");
        asIndenting.println(this.mExpandLatencyTracking);
        asIndenting.print("mExpandLatencyTracking=");
        asIndenting.println(this.mExpandLatencyTracking);
        StringBuilder sb5 = new StringBuilder("gestureExclusionRect:");
        Region calculateTouchableRegion = this.mStatusBarTouchableRegionManager.calculateTouchableRegion();
        Rect bounds = (!isFullyCollapsed() || calculateTouchableRegion == null) ? null : calculateTouchableRegion.getBounds();
        if (bounds == null) {
            bounds = EMPTY_RECT;
        }
        sb5.append(bounds);
        asIndenting.println(sb5.toString());
        new DumpsysTableLogger("NotificationPanelView", NPVCDownEventState.TABLE_HEADERS, SequencesKt___SequencesKt.toList(new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(this.mLastDownEvents.buffer), new Function1() { // from class: com.android.systemui.shade.NPVCDownEventState$Buffer$toList$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return (List) ((NPVCDownEventState) obj).asStringList$delegate.getValue();
            }
        }))).printTableData(asIndenting);
    }

    public final void endClosing() {
        SecQSFragmentAnimatorManager secQSFragmentAnimatorManager;
        if (this.mClosing) {
            setClosing(false);
            ShadeControllerImpl.this.onClosingFinished();
            this.mClosingWithAlphaFadeOut = false;
            this.mNotificationStackScrollLayoutController.mView.mForceNoOverlappingRendering = false;
            if (QpRune.QUICK_TABLET_HORIZONTAL_PANEL_POSITION) {
                this.mQsController.mSecQuickSettingsController.tabletHorizontalPanelPositionHelper.resetHorizontalPanelPosition(false);
            }
            if (!this.mPanelExpanded && (secQSFragmentAnimatorManager = ((CentralSurfacesImpl) this.mCentralSurfaces).mQSPanelController.mSecAnimatorManager) != null) {
                secQSFragmentAnimatorManager.onPanelClosed();
            }
            setExpandSettingsPanel(false);
            QsStatusEventLog qsStatusEventLog = this.mQsStatusEventLog;
            qsStatusEventLog.getClass();
            Thread thread = new Thread(new QsStatusEventLog$$ExternalSyntheticLambda0(qsStatusEventLog));
            thread.setName("WeeklySALogging");
            thread.start();
        }
    }

    public final void expand(boolean z) {
        if (isFullyCollapsed() || isCollapsing()) {
            StringBuilder sb = this.mLogBuilder;
            sb.setLength(0);
            sb.append("expand: ");
            sb.append("animate: ");
            sb.append(z);
            sb.append(", mInstantExpanding: ");
            sb.append(this.mInstantExpanding);
            sb.append(" -> true");
            sb.append(", mAnimateAfterExpanding: ");
            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, this.mAnimateAfterExpanding, " -> ", z, ", mUpdateFlingOnLayout: ");
            sb.append(this.mUpdateFlingOnLayout);
            sb.append(" -> false");
            sb.append(", mTracking: ");
            sb.append(this.mTracking);
            sb.append(", mExpanding: ");
            sb.append(this.mExpanding);
            ((SecPanelLoggerImpl) this.mPanelLogger).addPanelStateInfoLog(sb, false);
            this.mInstantExpanding = true;
            this.mAnimateAfterExpanding = z;
            this.mUpdateFlingOnLayout = false;
            abortAnimations();
            if (this.mTracking) {
                onTrackingStopped(true);
            }
            if (this.mExpanding) {
                notifyExpandingFinished();
            }
            updateExpansionAndVisibility();
            NotificationPanelView notificationPanelView = this.mView;
            notificationPanelView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.shade.NotificationPanelViewController.12
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                    if (!notificationPanelViewController.mInstantExpanding) {
                        notificationPanelViewController.mView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        return;
                    }
                    if (!((CentralSurfacesImpl) notificationPanelViewController.mCentralSurfaces).mNotificationShadeWindowView.isVisibleToUser()) {
                        SecPanelLoggerImpl secPanelLoggerImpl = (SecPanelLoggerImpl) NotificationPanelViewController.this.mPanelLogger;
                        secPanelLoggerImpl.getClass();
                        secPanelLoggerImpl.addPanelStateInfoLog(new StringBuilder("onGlobalLayout: NotificationShadeWindowView isVisibleToUser is false"), false);
                        return;
                    }
                    NotificationPanelViewController.this.mView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                    if (notificationPanelViewController2.mAnimateAfterExpanding) {
                        notificationPanelViewController2.notifyExpandingStarted();
                        NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
                        notificationPanelViewController3.mQsController.beginJankMonitoring(notificationPanelViewController3.isFullyCollapsed());
                        NotificationPanelViewController.this.fling(0.0f);
                    } else {
                        notificationPanelViewController2.setExpandedFraction(1.0f);
                    }
                    NotificationPanelViewController notificationPanelViewController4 = NotificationPanelViewController.this;
                    notificationPanelViewController4.mInstantExpanding = false;
                    notificationPanelViewController4.mLogBuilder.setLength(0);
                    StringBuilder sb2 = NotificationPanelViewController.this.mLogBuilder;
                    sb2.append("onGlobalLayout: ");
                    sb2.append("mAnimateAfterExpanding: ");
                    sb2.append(NotificationPanelViewController.this.mAnimateAfterExpanding);
                    NotificationPanelViewController notificationPanelViewController5 = NotificationPanelViewController.this;
                    ((SecPanelLoggerImpl) notificationPanelViewController5.mPanelLogger).addPanelStateInfoLog(notificationPanelViewController5.mLogBuilder, false);
                }
            });
            notificationPanelView.requestLayout();
        }
        setListening(true);
    }

    public final void expandToNotifications() {
        if (isOnKeyguard()) {
            if (((CentralSurfacesImpl) this.mCentralSurfaces).mBouncerShowing || this.mFullScreenModeEnabled) {
                return;
            }
            this.mLockscreenShadeTransitionController.goToLockedShade(null, true);
            return;
        }
        if (this.mSplitShadeEnabled && (isShadeFullyExpanded() || isExpandingOrCollapsing())) {
            return;
        }
        QuickSettingsController quickSettingsController = this.mQsController;
        if (quickSettingsController.mExpanded) {
            quickSettingsController.flingQs(0.0f, 1);
        } else {
            expand(true);
        }
    }

    public final void expandToQs() {
        QuickSettingsController quickSettingsController = this.mQsController;
        if (quickSettingsController.isExpansionEnabled()) {
            if (!this.mPanelExpanded) {
                quickSettingsController.setExpandImmediate(true);
            }
            setShowShelfOnly(true);
        }
        if (this.mSplitShadeEnabled && isOnKeyguard()) {
            this.mLockscreenShadeTransitionController.goToLockedShade(null, true);
        } else if (isFullyCollapsed()) {
            expand(true);
        } else {
            quickSettingsController.traceQsJank(true, false);
            quickSettingsController.flingQs(0.0f, 0);
        }
    }

    public final void fling(float f) {
        GestureRecorder gestureRecorder = this.mGestureRecorder;
        if (gestureRecorder != null) {
            String concat = "fling ".concat(f > 0.0f ? ServiceTuple.BASIC_STATUS_OPEN : ServiceTuple.BASIC_STATUS_CLOSED);
            String str = "notifications,v=" + f;
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            synchronized (gestureRecorder.mGestures) {
                if (gestureRecorder.mCurrentGesture == null) {
                    GestureRecorder.Gesture gesture = new GestureRecorder.Gesture(gestureRecorder);
                    gestureRecorder.mCurrentGesture = gesture;
                    gestureRecorder.mGestures.add(gesture);
                }
                GestureRecorder.Gesture gesture2 = gestureRecorder.mCurrentGesture;
                gesture2.mRecords.add(new GestureRecorder.Gesture.TagRecord(gesture2, uptimeMillis, concat, str));
                gesture2.mTags.add(concat);
            }
            GestureRecorder.HandlerC25071 handlerC25071 = gestureRecorder.mHandler;
            handlerC25071.removeMessages(6351);
            handlerC25071.sendEmptyMessageDelayed(6351, 5000L);
        }
        fling(f, true, 1.0f);
    }

    public void flingToHeight(float f, boolean z, float f2, float f3, boolean z2) {
        ValueAnimator valueAnimator;
        float f4 = f;
        StringBuilder sb = this.mLogBuilder;
        boolean z3 = false;
        sb.setLength(0);
        sb.append("flingToHeight: ");
        sb.append("vel: ");
        sb.append(f4);
        sb.append(", expand: ");
        sb.append(z);
        sb.append(", target: ");
        sb.append(f2);
        sb.append(", collapseSpeedUpFactor: ");
        sb.append(f3);
        sb.append(", expandBecauseOfFalsing: ");
        sb.append(z2);
        sb.append(", mExpandedHeight: ");
        sb.append(this.mExpandedHeight);
        sb.append(", mOverExpansion: ");
        sb.append(0.0f);
        sb.append(", mIsFling: ");
        sb.append(this.mIsFlinging);
        sb.append(LogUtil.getCaller());
        Log.d("NotificationPanelView", sb.toString());
        QuickSettingsController quickSettingsController = this.mQsController;
        quickSettingsController.mLastShadeFlingWasExpanding = z;
        ShadeLogger shadeLogger = quickSettingsController.mShadeLog;
        shadeLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        ShadeLogger$logLastFlingWasExpanding$2 shadeLogger$logLastFlingWasExpanding$2 = new Function1() { // from class: com.android.systemui.shade.ShadeLogger$logLastFlingWasExpanding$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AbstractC0866xb1ce8deb.m86m("NPVC mLastFlingWasExpanding set to: ", ((LogMessage) obj).getBool1());
            }
        };
        LogBuffer logBuffer = shadeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$logLastFlingWasExpanding$2, null);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
        HeadsUpTouchHelper headsUpTouchHelper = this.mHeadsUpTouchHelper;
        boolean z4 = !z;
        HeadsUpManagerPhone headsUpManagerPhone = headsUpTouchHelper.mHeadsUpManager;
        if (z4 && headsUpTouchHelper.mCollapseSnoozes) {
            headsUpManagerPhone.snooze();
        }
        headsUpTouchHelper.mCollapseSnoozes = false;
        if (!z4) {
            Log.d("HeadsUpTouchHelper", "unpinAll because of notifyFling expand");
            headsUpManagerPhone.unpinAll();
        }
        boolean z5 = isOnKeyguard() && !z;
        KeyguardStateControllerImpl keyguardStateControllerImpl = this.mKeyguardStateController;
        keyguardStateControllerImpl.mFlingingToDismissKeyguard = z5;
        keyguardStateControllerImpl.mFlingingToDismissKeyguardDuringSwipeGesture = z5 && keyguardStateControllerImpl.mDismissingFromTouch;
        keyguardStateControllerImpl.mSnappingKeyguardBackAfterSwipe = !z5;
        boolean z6 = (z || isOnKeyguard() || getFadeoutAlpha() != 1.0f) ? false : true;
        this.mClosingWithAlphaFadeOut = z6;
        NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.mForceNoOverlappingRendering = z6;
        AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
        ambientState.isOnKeyguard();
        ambientState.mIsFlinging = true;
        if (f2 == this.mExpandedHeight) {
            onFlingEnd(false);
            return;
        }
        this.mIsFlinging = true;
        final boolean z7 = z && ((StatusBarStateControllerImpl) this.mStatusBarStateController).mState != 1 && f4 >= 0.0f;
        float lerp = z7 ? (0.0f / this.mPanelFlingOvershootAmount) + MathUtils.lerp(0.2f, 1.0f, MathUtils.saturate(f4 / (this.mFlingAnimationUtils.mHighVelocityPxPerSecond * 0.5f))) : 0.0f;
        ValueAnimator createHeightAnimator = createHeightAnimator(f2, lerp);
        NotificationPanelView notificationPanelView = this.mView;
        if (z) {
            maybeVibrateOnOpening(true);
            if (z2 && f4 < 0.0f) {
                f4 = 0.0f;
            }
            this.mFlingAnimationUtils.apply(createHeightAnimator, this.mExpandedHeight, (lerp * this.mPanelFlingOvershootAmount) + f2, f4, notificationPanelView.getHeight());
            if (f4 == 0.0f) {
                createHeightAnimator.setDuration(350L);
            }
            valueAnimator = createHeightAnimator;
        } else {
            this.mHasVibratedOnOpen = false;
            if (this.mBarState != 0 && (keyguardStateControllerImpl.mCanDismissLockScreen || !this.mTracking)) {
                z3 = true;
            }
            if (!z3) {
                valueAnimator = createHeightAnimator;
                this.mFlingAnimationUtilsClosing.apply(valueAnimator, this.mExpandedHeight, f2, f, notificationPanelView.getHeight());
            } else if (f4 == 0.0f) {
                createHeightAnimator.setInterpolator(Interpolators.PANEL_CLOSE_ACCELERATED);
                createHeightAnimator.setDuration((long) (((this.mExpandedHeight / notificationPanelView.getHeight()) * 100.0f) + 200.0f));
                valueAnimator = createHeightAnimator;
            } else {
                valueAnimator = createHeightAnimator;
                this.mFlingAnimationUtilsDismissing.apply(createHeightAnimator, this.mExpandedHeight, f2, f, notificationPanelView.getHeight());
            }
            if (f4 == 0.0f) {
                valueAnimator.setDuration((long) (valueAnimator.getDuration() / f3));
            }
            int i = this.mFixedDuration;
            if (i != -1) {
                valueAnimator.setDuration(i);
            }
            valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.NotificationPanelViewController.10
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                    notificationPanelViewController.mCurrentBackProgress = 0.0f;
                    notificationPanelViewController.applyBackScaling(0.0f);
                }
            });
        }
        valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.NotificationPanelViewController.11
            public boolean mCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                this.mCancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (!z7 || this.mCancelled) {
                    NotificationPanelViewController.this.onFlingEnd(this.mCancelled);
                    return;
                }
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                VibrationEffect vibrationEffect = NotificationPanelViewController.ADDITIONAL_TAP_REQUIRED_VIBRATION_EFFECT;
                notificationPanelViewController.onFlingEnd(false);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                if (((StatusBarStateControllerImpl) notificationPanelViewController.mStatusBarStateController).mIsDozing) {
                    return;
                }
                notificationPanelViewController.mQsController.beginJankMonitoring(notificationPanelViewController.isFullyCollapsed());
            }
        });
        if (this.mHeightAnimator != null) {
            cancelHeightAnimator();
        }
        this.mDetailViewCollapseAnimating = quickSettingsController.disallowTouches();
        this.mQsExpandedViewCollapseAnimating = quickSettingsController.mExpanded;
        setAnimator(valueAnimator);
        valueAnimator.start();
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        PanelScreenShotLogger.INSTANCE.getClass();
        PanelScreenShotLogger.addHeaderLine("NotificationPanelViewController", arrayList);
        int i = this.mClockPositionResult.stackScrollerPadding;
        QuickSettingsController quickSettingsController = this.mQsController;
        PanelScreenShotLogger.addLogItem(arrayList, "calculatePanelHeightQsExpanded", Integer.valueOf(quickSettingsController.calculatePanelHeightExpanded(i)));
        PanelScreenShotLogger.addLogItem(arrayList, "calculatePanelHeightShade", Integer.valueOf(calculatePanelHeightShade()));
        PanelScreenShotLogger.addLogItem(arrayList, "mHeadsUpInset", Integer.valueOf(this.mHeadsUpInset));
        PanelScreenShotLogger.addLogItem(arrayList, "getKeyguardNotificationStaticPadding", Integer.valueOf(getKeyguardNotificationStaticPadding()));
        PanelScreenShotLogger.addLogItem(arrayList, "mQsMaxExpansionHeight", Integer.valueOf(quickSettingsController.mMaxExpansionHeight));
        PanelScreenShotLogger.addLogItem(arrayList, "mQsExpansionHeight", Float.valueOf(quickSettingsController.mExpansionHeight));
        PanelScreenShotLogger.addLogItem(arrayList, "computeQsExpansionFraction", Float.valueOf(quickSettingsController.computeExpansionFraction()));
        PanelScreenShotLogger.addLogItem(arrayList, "mTransitioningToFullShadeProgress", Float.valueOf(0.0f));
        PanelScreenShotLogger.addLogItem(arrayList, "mOverStretchAmount", Float.valueOf(this.mOverStretchAmount));
        PanelScreenShotLogger.addLogItem(arrayList, "mSecAffordanceHelper.isShortcutPreviewSwipingInProgress()", Boolean.valueOf(this.mSecAffordanceHelper.isShortcutPreviewSwipingInProgress));
        PanelScreenShotLogger.addLogItem(arrayList, "mMaxAllowedKeyguardNotifications", Integer.valueOf(this.mMaxAllowedKeyguardNotifications));
        NotificationPanelView notificationPanelView = this.mView;
        PanelScreenShotLogger.addLogItem(arrayList, "currentPanelAlpha", Float.valueOf(notificationPanelView.mCurrentPanelAlpha));
        PanelScreenShotLogger.addLogItem(arrayList, "visibility", Integer.valueOf(notificationPanelView.getVisibility()));
        PanelScreenShotLogger.addLogItem(arrayList, "getAlpha", Float.valueOf(notificationPanelView.getAlpha()));
        PanelScreenShotLogger.addLogItem(arrayList, "mQuickQsOffsetHeight", Integer.valueOf(this.mQuickQsOffsetHeight));
        PanelScreenShotLogger.addLogItem(arrayList, "mExpandedHeight", Float.valueOf(this.mExpandedHeight));
        PanelScreenShotLogger.addLogItem(arrayList, "mRecomputedMaxCountNotification", this.mRecomputedMaxCountNotification);
        PanelScreenShotLogger.addLogItem(arrayList, "mRecomputedMaxCountCallStack", this.mRecomputedMaxCountCallStack);
        PanelScreenShotLogger.addLogItem(arrayList, "mRecomputedReason", this.mRecomputedReason);
        PanelScreenShotLogger.addLogItem(arrayList, "mBottomMarginY", Integer.valueOf(this.mBottomMarginY));
        PanelScreenShotLogger.addLogItem(arrayList, "mAvailableNotifSpace", Float.valueOf(this.mAvailableNotifSpace));
        return arrayList;
    }

    public final int getCutoutHeight() {
        DisplayCutout displayCutout;
        NotificationPanelView notificationPanelView = this.mView;
        if (notificationPanelView.getRootWindowInsets() == null || (displayCutout = notificationPanelView.getRootWindowInsets().getDisplayCutout()) == null) {
            return 0;
        }
        Iterator<Rect> it = displayCutout.getBoundingRects().iterator();
        if (!it.hasNext()) {
            return 0;
        }
        Rect next = it.next();
        return Math.min(next.height(), next.width());
    }

    public final float getFaceWidgetAlpha() {
        float f;
        if (this.mKeyguardTouchAnimator.isViRunning() || ((CentralSurfacesImpl) this.mCentralSurfaces).mBouncerShowing) {
            f = -1.0f;
        } else {
            if (this.mClockPositionAlgorithm.isPanelExpanded()) {
                QuickSettingsController quickSettingsController = this.mQsController;
                if (quickSettingsController.mExpanded) {
                    float computeExpansionFraction = quickSettingsController.computeExpansionFraction();
                    f = NotificationUtils.interpolate(1.0f, 0.0f, ((double) computeExpansionFraction) > 0.3d ? 1.0f : computeExpansionFraction * 3.0f);
                }
            }
            f = 1.0f;
        }
        if (((KeyguardEditModeControllerImpl) this.mKeyguardEditModeController).getVIRunning()) {
            return 1.0f;
        }
        return f;
    }

    public final float getFadeoutAlpha() {
        if (this.mQsController.mMinExpansionHeight == 0) {
            return 1.0f;
        }
        return (float) Math.pow(Math.max(0.0f, Math.min(this.mExpandedHeight / r0, 1.0f)), 0.75d);
    }

    public final int getFalsingThreshold() {
        return (int) (this.mQsController.mFalsingThreshold * (((CentralSurfacesImpl) this.mCentralSurfaces).mWakeUpComingFromTouch ? 1.5f : 1.0f));
    }

    public final int getKeyguardNotificationStaticPadding() {
        if (!isKeyguardShowing()) {
            return 0;
        }
        boolean bypassEnabled = this.mKeyguardBypassController.getBypassEnabled();
        KeyguardClockPositionAlgorithm.Result result = this.mClockPositionResult;
        if (!bypassEnabled) {
            if (!LsRune.KEYGUARD_DCM_LIVE_UX) {
                return result.stackScrollerPadding;
            }
            LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
            int notificationIconsOnlyContainerHeight = lockscreenNotificationIconsOnlyController != null ? lockscreenNotificationIconsOnlyController.getNotificationIconsOnlyContainerHeight() : 0;
            int i = result.stackScrollerPadding;
            return this.mMascotViewContainer.updatePosition(i, notificationIconsOnlyContainerHeight) + i;
        }
        int i2 = this.mHeadsUpInset;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        if (!notificationStackScrollLayoutController.mView.mAmbientState.isPulseExpanding()) {
            return i2;
        }
        int i3 = result.stackScrollerPadding;
        float f = notificationStackScrollLayoutController.mView.mAmbientState.mPulseHeight;
        if (f == 100000.0f) {
            f = 0.0f;
        }
        return (int) MathUtils.lerp(i2, i3, MathUtils.smoothStep(0.0f, r5.mIntrinsicPadding, f));
    }

    public final int getMaxPanelHeight() {
        int i = this.mStatusBarMinHeight;
        int i2 = this.mBarState;
        QuickSettingsController quickSettingsController = this.mQsController;
        if (i2 != 1 && this.mNotificationStackScrollLayoutController.getNotGoneChildCount() == 0) {
            i = Math.max(i, quickSettingsController.mMinExpansionHeight);
        }
        boolean isExpandImmediate = quickSettingsController.isExpandImmediate();
        KeyguardClockPositionAlgorithm.Result result = this.mClockPositionResult;
        int max = Math.max(i, (isExpandImmediate || quickSettingsController.mExpanded || (this.mIsExpandingOrCollapsing && quickSettingsController.mExpandedWhenExpandingStarted) || this.mPulsing || this.mSplitShadeEnabled) ? quickSettingsController.calculatePanelHeightExpanded(result.stackScrollerPadding) : calculatePanelHeightShade());
        if (max == 0) {
            Log.wtf("NotificationPanelView", "maxPanelHeight is invalid. mOverExpansion: 0.0, calculatePanelHeightQsExpanded: " + quickSettingsController.calculatePanelHeightExpanded(result.stackScrollerPadding) + ", calculatePanelHeightShade: " + calculatePanelHeightShade() + ", mStatusBarMinHeight = " + this.mStatusBarMinHeight + ", mQsMinExpansionHeight = " + quickSettingsController.mMinExpansionHeight);
        }
        return max;
    }

    public int getMaxPanelTransitionDistance() {
        if (!this.mSplitShadeEnabled || this.mBarState != 0) {
            return getMaxPanelHeight();
        }
        HeadsUpManagerPhone headsUpManagerPhone = this.mHeadsUpManager;
        if ((headsUpManagerPhone != null && headsUpManagerPhone.mTrackingHeadsUp) || this.mExpandingFromHeadsUp) {
            return (int) Math.min(getMaxPanelHeight(), Math.max(this.mSplitShadeFullTransitionDistance, this.mHeadsUpStartHeight * 2.5d));
        }
        return this.mSplitShadeFullTransitionDistance;
    }

    public final int getNotificationTopMargin(boolean z) {
        Point point = DeviceState.sDisplaySize;
        boolean isTablet = DeviceType.isTablet();
        Resources resources = this.mResources;
        return isTablet ? z ? resources.getDimensionPixelSize(R.dimen.f700x96586a2b) : getCutoutHeight() + resources.getDimensionPixelSize(R.dimen.keyguard_indication_dls_default_notification_top_margin_tablet) : z ? resources.getDimensionPixelSize(R.dimen.keyguard_indication_dls_default_notification_top_margin_land) : getCutoutHeight() + resources.getDimensionPixelSize(R.dimen.keyguard_indication_dls_default_notification_top_margin);
    }

    public StatusBarStateController getStatusBarStateController() {
        return this.mStatusBarStateController;
    }

    public StatusBarStateController.StateListener getStatusBarStateListener() {
        return this.mStatusBarStateListener;
    }

    public TouchHandler getTouchHandler() {
        return this.mTouchHandler;
    }

    public final float getTouchSlop(MotionEvent motionEvent) {
        return motionEvent.getClassification() == 1 ? this.mTouchSlop * this.mSlopMultiplier : this.mTouchSlop;
    }

    public float getVerticalSpaceForLockscreenNotifications() {
        SecLockIconViewController secLockIconViewController = this.mLockIconViewController;
        float f = r1.mLockIconCenter.y - ((LockIconView) secLockIconViewController.mView).mRadius;
        float f2 = 0.0f;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        if (f != 0.0f) {
            f2 = notificationStackScrollLayoutController.mView.getBottom() - (r0.mLockIconCenter.y - ((LockIconView) secLockIconViewController.mView).mRadius);
        }
        NotificationPanelView notificationPanelView = this.mView;
        int dimensionPixelSize = notificationPanelView.getResources().getDimensionPixelSize(R.dimen.keyguard_margin_between_noti_indication);
        if (this.mKeyguardBottomArea == null) {
            dimensionPixelSize = 10;
        }
        int i = 0;
        this.mIndicationBottomPadding = dimensionPixelSize - 0;
        float max = Math.max(f2, Math.max(r2, 0));
        this.mKeyguardNotificationBottomPadding = max;
        float top = notificationStackScrollLayoutController.mView.getTop();
        KeyguardClockPositionAlgorithm keyguardClockPositionAlgorithm = this.mClockPositionAlgorithm;
        float lockscreenNotifPadding = keyguardClockPositionAlgorithm.getLockscreenNotifPadding(top);
        this.mKeyguardNotificationTopPadding = lockscreenNotifPadding;
        boolean z = LsRune.KEYGUARD_DCM_LIVE_UX;
        if (z) {
            DcmMascotViewContainer dcmMascotViewContainer = this.mMascotViewContainer;
            if (dcmMascotViewContainer.getVisibility() == 0) {
                i = dcmMascotViewContainer.mascotHeight + dcmMascotViewContainer.mascotTopMarin + dcmMascotViewContainer.mascotBottomMarin;
            }
        }
        int bottomMarginY = keyguardClockPositionAlgorithm instanceof FaceWidgetPositionAlgorithmWrapper ? keyguardClockPositionAlgorithm.getBottomMarginY() : (int) max;
        int height = notificationPanelView.getHeight();
        notificationStackScrollLayoutController.getHeight();
        this.mBottomMarginY = bottomMarginY;
        float f3 = (height - lockscreenNotifPadding) - bottomMarginY;
        if (z) {
            f3 -= i;
        }
        this.mAvailableNotifSpace = f3;
        return f3;
    }

    public float getVerticalSpaceForLockscreenShelf() {
        float f;
        SecLockIconViewController secLockIconViewController = this.mLockIconViewController;
        if (r1.mLockIconCenter.y - ((LockIconView) secLockIconViewController.mView).mRadius != 0.0f) {
            f = this.mNotificationStackScrollLayoutController.mView.getBottom() - (r0.mLockIconCenter.y - ((LockIconView) secLockIconViewController.mView).mRadius);
        } else {
            f = 0.0f;
        }
        float max = f - Math.max(this.mIndicationBottomPadding, 0);
        if (max <= 0.0f) {
            return 0.0f;
        }
        Flags flags = Flags.INSTANCE;
        this.mFeatureFlags.getClass();
        return Math.min(this.mNotificationShelfController.getIntrinsicHeight(), max);
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
    public final void goToLockedShade() {
        Log.d("NotificationPanelView", "goToLockedShade mCentralSurfaces: " + this.mCentralSurfaces);
        LockscreenShadeTransitionController lockscreenShadeTransitionController = this.mLockscreenShadeTransitionController;
        if (lockscreenShadeTransitionController != null) {
            lockscreenShadeTransitionController.goToLockedShade(null, false);
        }
    }

    public final boolean hasVisibleNotifications() {
        return this.mNotificationStackScrollLayoutController.mNotifStats.numActiveNotifs != 0 || this.mMediaDataManager.hasActiveMediaOrRecommendation();
    }

    public final void initBottomArea() {
        if (this.mSecAffordanceHelper == null) {
            this.mSecAffordanceHelper = new KeyguardSecAffordanceHelper(this.mKeyguardAffordanceHelperCallback, this.mView.getContext(), (KeyguardSecBottomAreaView) this.mKeyguardBottomArea);
        }
        this.mKeyguardBottomArea.init(this.mKeyguardBottomAreaViewModel, this.mFalsingManager, this.mLockIconViewController, new NotificationPanelViewController$$ExternalSyntheticLambda2(this), this.mVibratorHelper, this.mActivityStarter);
        this.mKeyguardBottomAreaViewController.setUserSetupComplete(this.mUserSetupComplete);
    }

    public final void instantCollapse() {
        StringBuilder sb = this.mLogBuilder;
        sb.setLength(0);
        sb.append("instantCollapse : ");
        sb.append("\n");
        sb.append(Debug.getCallers(10, " - "));
        ((SecPanelLoggerImpl) this.mPanelLogger).addPanelStateInfoLog(sb, true);
        abortAnimations();
        setExpandedFraction(0.0f);
        setMotionAborted();
        if (this.mExpanding) {
            notifyExpandingFinished();
        }
        if (this.mInstantExpanding) {
            this.mInstantExpanding = false;
            updateExpansionAndVisibility();
        }
    }

    public boolean isClosing() {
        return this.mClosing;
    }

    public final boolean isCollapsing() {
        return this.mClosing || this.mIsLaunchAnimationRunning;
    }

    public final boolean isExpanded() {
        if (this.mExpandedFraction > 0.0f || this.mInstantExpanding) {
            return true;
        }
        if (((this.mHeadsUpManager.mHasPinnedNotification || this.mHeadsUpAnimatingAway) && this.mBarState == 0) || this.mTracking || this.mHeightAnimator != null) {
            return true;
        }
        return this.mUnlockedScreenOffAnimationController.isAnimationPlaying() && !this.mIsSpringBackAnimation;
    }

    public final boolean isExpandingOrCollapsing() {
        float computeExpansionFraction = this.mQsController.computeExpansionFraction();
        return this.mIsExpandingOrCollapsing || (0.0f < computeExpansionFraction && computeExpansionFraction < 1.0f);
    }

    public boolean isFlinging() {
        return this.mIsFlinging;
    }

    public final boolean isFullyCollapsed() {
        return this.mExpandedFraction <= 0.0f;
    }

    public final boolean isFullyExpanded() {
        return this.mExpandedHeight >= ((float) getMaxPanelTransitionDistance());
    }

    public boolean isHintAnimationRunning() {
        return this.mHintAnimationRunning;
    }

    public final boolean isInContentBounds(float f, float f2) {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        float f3 = notificationStackScrollLayout.mSidePaddings;
        float x = notificationStackScrollLayout.getX() + f3;
        NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
        return !notificationStackScrollLayout2.isBelowLastNotification(f2) && x < f && f < (((float) notificationStackScrollLayout2.getWidth()) + x) - (f3 * 2.0f);
    }

    public final boolean isInFaceWidgetContainer(MotionEvent motionEvent) {
        PluginKeyguardStatusView pluginKeyguardStatusView;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1) {
            boolean z = this.mIsFaceWidgetOnTouchDown;
            this.mIsFaceWidgetOnTouchDown = false;
            return z;
        }
        if (actionMasked == 0) {
            KeyguardStatusViewController keyguardStatusViewController = this.mKeyguardStatusViewController;
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            FaceWidgetContainerWrapper faceWidgetContainerWrapper = keyguardStatusViewController.mKeyguardStatusBase;
            if ((faceWidgetContainerWrapper == null || (pluginKeyguardStatusView = faceWidgetContainerWrapper.mPluginKeyguardStatusView) == null) ? false : pluginKeyguardStatusView.isInContentBounds(x, y)) {
                this.mIsFaceWidgetOnTouchDown = true;
            } else {
                this.mIsFaceWidgetOnTouchDown = false;
            }
        }
        return this.mIsFaceWidgetOnTouchDown;
    }

    public final boolean isInLockStarContainer(MotionEvent motionEvent) {
        Lazy lazy = this.mPluginLockStarManagerLazy;
        if (lazy.get() == null) {
            return false;
        }
        try {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 1) {
                boolean z = this.mIsLockStarOnTouchDown;
                this.mIsLockStarOnTouchDown = false;
                return z;
            }
            if (actionMasked == 0) {
                if (((PluginLockStarManager) lazy.get()).isTouchable(motionEvent)) {
                    this.mIsLockStarOnTouchDown = true;
                } else {
                    this.mIsLockStarOnTouchDown = false;
                }
            }
            return this.mIsLockStarOnTouchDown;
        } catch (Throwable th) {
            Log.e("NotificationPanelView", "isInLockStarContainer() error " + th.getMessage());
            return false;
        }
    }

    public final boolean isKeyguardShowing() {
        return this.mBarState == 1;
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
    public final boolean isNoUnlockNeed(String str) {
        KeyguardBottomAreaViewController keyguardBottomAreaViewController = this.mKeyguardBottomAreaViewController;
        if (keyguardBottomAreaViewController != null) {
            return keyguardBottomAreaViewController.isNoUnlockNeed(str);
        }
        return false;
    }

    public final boolean isOnAod() {
        return this.mDozing && this.mDozeParameters.getAlwaysOn();
    }

    public final boolean isOnKeyguard() {
        return this.mBarState == 1;
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
    public final boolean isSecure() {
        StringBuilder sb = new StringBuilder("isSecure mUpdateMonitor: ");
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        sb.append(keyguardUpdateMonitor);
        Log.d("NotificationPanelView", sb.toString());
        if (keyguardUpdateMonitor == null) {
            return false;
        }
        return keyguardUpdateMonitor.isSecure();
    }

    public final boolean isShadeFullyExpanded() {
        int i = this.mBarState;
        return i == 0 ? isFullyExpanded() : i == 2 || this.mQsController.computeExpansionFraction() == 1.0f;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x00ab A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0071 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isTouchOnEmptyArea(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        PluginKeyguardStatusView pluginKeyguardStatusView;
        Lazy lazy = this.mPluginLockStarManagerLazy;
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        FaceWidgetContainerWrapper faceWidgetContainerWrapper = this.mKeyguardStatusViewController.mKeyguardStatusBase;
        if ((faceWidgetContainerWrapper == null || (pluginKeyguardStatusView = faceWidgetContainerWrapper.mPluginKeyguardStatusView) == null) ? false : pluginKeyguardStatusView.isInContentBounds(x, y)) {
            return false;
        }
        if (this.mAccessibilityManager.isEnabled() || this.mUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser())) {
            View findViewById = this.mView.findViewById(R.id.sec_lock_icon_view);
            if (findViewById != null) {
                float x2 = findViewById.getX();
                float y2 = findViewById.getY();
                if (findViewById.getVisibility() == 0 && x2 < x && x < x2 + findViewById.getWidth() && y2 < y && y < y2 + findViewById.getHeight()) {
                    z = true;
                    if (z) {
                        return false;
                    }
                }
            }
            z = false;
            if (z) {
            }
        }
        if (LsRune.KEYGUARD_DCM_LIVE_UX) {
            DcmMascotViewContainer dcmMascotViewContainer = this.mMascotViewContainer;
            if (dcmMascotViewContainer.getVisibility() == 0) {
                int x3 = (int) dcmMascotViewContainer.getX();
                int y3 = (int) dcmMascotViewContainer.getY();
                if (x3 < x && x < dcmMascotViewContainer.getWidth() + x3 && y3 < y && y < dcmMascotViewContainer.getHeight() + y3) {
                    z2 = true;
                    if (z2) {
                        return false;
                    }
                }
            }
            z2 = false;
            if (z2) {
            }
        }
        try {
            if (this.mLockStarEnabled && lazy.get() != null && ((PluginLockStarManager) lazy.get()).isTouchable(motionEvent)) {
                LogUtil.m224dm("isTouchOnEmptyArea on lockstar item", new Object[0]);
                return false;
            }
        } catch (Throwable unused) {
            Log.e("NotificationPanelView", "isTouchOnEmptyArea() error in Lockstar");
        }
        boolean z3 = y < ((float) this.mClockPositionResult.clockY);
        if (this.mLockStarEnabled) {
            LogUtil.m224dm("isTouchOnEmptyArea belowClock false", new Object[0]);
            z3 = false;
        }
        if (!((SettingsHelper) Dependency.get(SettingsHelper.class)).isShowNotificationOnKeyguard()) {
            LogUtil.m224dm("isTouchOnEmptyArea returns true", new Object[0]);
            return true;
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        boolean z4 = true;
        for (int childCount = notificationStackScrollLayoutController.mView.getChildCount() - 1; childCount >= 0; childCount--) {
            ExpandableView expandableView = (ExpandableView) notificationStackScrollLayoutController.mView.getChildAt(childCount);
            if (expandableView.getVisibility() != 8 && expandableView.getY() < y) {
                z4 = false;
            }
        }
        boolean z5 = z4 && !z3;
        int notGoneChildCount = notificationStackScrollLayoutController.getNotGoneChildCount();
        boolean isInContentBounds = isInContentBounds(x, y);
        boolean z6 = notGoneChildCount <= 0 || !isInContentBounds || z3 || z5;
        LogUtil.m223d("NotificationPanelView", "isTouchOnEmptyArea return %s: notGoneChildCount() %s, isInContentBounds %s", Boolean.valueOf(z6), Integer.valueOf(notGoneChildCount), Boolean.valueOf(isInContentBounds));
        return z6;
    }

    public void loadDimens() {
        NotificationPanelView notificationPanelView = this.mView;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(notificationPanelView.getContext());
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mSlopMultiplier = viewConfiguration.getScaledAmbiguousGestureMultiplier();
        Resources resources = this.mResources;
        this.mHintDistance = resources.getDimension(R.dimen.hint_move_distance);
        this.mPanelFlingOvershootAmount = resources.getDimension(R.dimen.panel_overshoot_amount);
        FlingAnimationUtils.Builder builder = (FlingAnimationUtils.Builder) this.mFlingAnimationUtilsBuilder.get();
        builder.mMaxLengthSeconds = 0.4f;
        this.mFlingAnimationUtils = builder.build();
        this.mStatusBarMinHeight = SystemBarUtils.getStatusBarHeight(notificationPanelView.getContext());
        Context context = notificationPanelView.getContext();
        int statusBarHeight = SystemBarUtils.getStatusBarHeight(context);
        DisplayCutout cutout = context.getDisplay().getCutout();
        this.mStatusBarHeaderHeightKeyguard = Math.max(statusBarHeight, context.getResources().getDimensionPixelSize(R.dimen.status_bar_header_height_keyguard) + (cutout == null ? 0 : cutout.getWaterfallInsets().top));
        this.mClockPositionAlgorithm.loadDimens(resources);
        this.mIndicationBottomPadding = resources.getDimensionPixelSize(R.dimen.keyguard_indication_bottom_padding);
        this.mHeadsUpInset = resources.getDimensionPixelSize(R.dimen.heads_up_status_bar_padding) + SystemBarUtils.getStatusBarHeight(notificationPanelView.getContext());
        this.mMaxOverscrollAmountForPulse = resources.getDimensionPixelSize(R.dimen.pulse_expansion_max_top_overshoot);
        this.mUdfpsMaxYBurnInOffset = resources.getDimensionPixelSize(R.dimen.udfps_burn_in_offset_y);
        this.mSplitShadeScrimTransitionDistance = resources.getDimensionPixelSize(R.dimen.split_shade_scrim_transition_distance);
        this.mDreamingToLockscreenTransitionTranslationY = resources.getDimensionPixelSize(R.dimen.dreaming_to_lockscreen_transition_lockscreen_translation_y);
        this.mOccludedToLockscreenTransitionTranslationY = resources.getDimensionPixelSize(R.dimen.occluded_to_lockscreen_transition_lockscreen_translation_y);
        this.mLockscreenToDreamingTransitionTranslationY = resources.getDimensionPixelSize(R.dimen.lockscreen_to_dreaming_transition_lockscreen_translation_y);
        this.mGoneToDreamingTransitionTranslationY = resources.getDimensionPixelSize(R.dimen.gone_to_dreaming_transition_lockscreen_translation_y);
        this.mLockscreenToOccludedTransitionTranslationY = resources.getDimensionPixelSize(R.dimen.lockscreen_to_occluded_transition_lockscreen_translation_y);
        QuickSettingsController quickSettingsController = this.mQsController;
        NotificationPanelView notificationPanelView2 = quickSettingsController.mPanelView;
        ViewConfiguration viewConfiguration2 = ViewConfiguration.get(notificationPanelView2.getContext());
        quickSettingsController.mTouchSlop = viewConfiguration2.getScaledTouchSlop();
        quickSettingsController.mSlopMultiplier = viewConfiguration2.getScaledAmbiguousGestureMultiplier();
        Resources resources2 = quickSettingsController.mResources;
        quickSettingsController.mPeekHeight = resources2.getDimensionPixelSize(R.dimen.qs_peek_height);
        quickSettingsController.mStatusBarMinHeight = SystemBarUtils.getStatusBarHeight(notificationPanelView2.getContext());
        quickSettingsController.mScrimCornerRadius = resources2.getDimensionPixelSize(R.dimen.notification_scrim_corner_radius);
        quickSettingsController.mScreenCornerRadius = (int) ScreenDecorationsUtils.getWindowCornerRadius(notificationPanelView2.getContext());
        quickSettingsController.mFalsingThreshold = resources2.getDimensionPixelSize(R.dimen.qs_falsing_threshold);
        quickSettingsController.mLockscreenNotificationPadding = resources2.getDimensionPixelSize(R.dimen.notification_side_paddings);
        quickSettingsController.mDistanceForFullShadeTransition = resources2.getDimensionPixelSize(R.dimen.lockscreen_shade_qs_transition_distance);
        quickSettingsController.mShelfHeight = DeviceType.isTablet() ? resources2.getDimensionPixelSize(R.dimen.sec_notification_shelf_height_tablet) : resources2.getDimensionPixelSize(R.dimen.sec_notification_shelf_height);
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
    public final void makeExpandedInvisible() {
        StringBuilder sb = this.mLogBuilder;
        sb.setLength(0);
        sb.append("makeExpandedInvisible returned : NPV");
        sb.append("\n");
        sb.append(Debug.getCallers(5, " - "));
        ((SecPanelLoggerImpl) this.mPanelLogger).addPanelStateInfoLog(sb, true);
        this.mView.post(this.mHideExpandedRunnable);
    }

    public final void maybeVibrateOnOpening(boolean z) {
        int i;
        if (!this.mVibrateOnOpening || (i = this.mBarState) == 1 || i == 2) {
            return;
        }
        if (z && this.mHasVibratedOnOpen) {
            return;
        }
        this.mVibratorHelper.vibrate(2);
        this.mHasVibratedOnOpen = true;
        this.mShadeLog.m190v("Vibrating on opening, mHasVibratedOnOpen=true");
    }

    public final void notifyExpandingFinished() {
        endClosing();
        if (this.mExpanding) {
            int i = 0;
            this.mExpanding = false;
            NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScrollLayoutController.mView;
            notificationStackScrollLayout.mCheckForLeavebehind = false;
            notificationStackScrollLayout.mIsExpansionChanging = false;
            notificationStackScrollLayout.mAmbientState.mExpansionChanging = false;
            if (!notificationStackScrollLayout.mIsExpanded) {
                notificationStackScrollLayout.resetScrollPosition();
                ((CentralSurfacesImpl) notificationStackScrollLayout.mCentralSurfaces).mNotificationsController.resetUserExpandedStates();
                NotificationStackScrollLayout.clearTemporaryViewsInGroup(notificationStackScrollLayout);
                for (int i2 = 0; i2 < notificationStackScrollLayout.getChildCount(); i2++) {
                    ExpandableView childAtIndex = notificationStackScrollLayout.getChildAtIndex(i2);
                    if (childAtIndex instanceof ExpandableNotificationRow) {
                        NotificationStackScrollLayout.clearTemporaryViewsInGroup(((ExpandableNotificationRow) childAtIndex).mChildrenContainer);
                    }
                }
                for (int i3 = 0; i3 < notificationStackScrollLayout.getChildCount(); i3++) {
                    ExpandableView childAtIndex2 = notificationStackScrollLayout.getChildAtIndex(i3);
                    if (childAtIndex2 instanceof ExpandableNotificationRow) {
                        ((ExpandableNotificationRow) childAtIndex2).setUserLocked(false);
                    }
                }
                notificationStackScrollLayout.resetAllSwipeState();
            }
            if (notificationStackScrollLayout.mLastSentExpandedHeight > 0.0f) {
                notificationStackScrollLayout.clearHeadsUpDisappearRunning();
            }
            HeadsUpManagerPhone headsUpManagerPhone = this.mHeadsUpManager;
            boolean z = headsUpManagerPhone.mReleaseOnExpandFinish;
            HashSet hashSet = headsUpManagerPhone.mEntriesToRemoveAfterExpand;
            if (z) {
                headsUpManagerPhone.releaseAllImmediately();
                headsUpManagerPhone.mReleaseOnExpandFinish = false;
            } else {
                Iterator it = hashSet.iterator();
                while (it.hasNext()) {
                    NotificationEntry notificationEntry = (NotificationEntry) it.next();
                    if (headsUpManagerPhone.isAlerting(notificationEntry.mKey)) {
                        headsUpManagerPhone.removeAlertEntry(notificationEntry.mKey);
                    }
                }
            }
            hashSet.clear();
            this.mConversationNotificationManager.onNotificationPanelExpandStateChanged(isFullyCollapsed());
            this.mIsExpandingOrCollapsing = false;
            int i4 = 1;
            if (isFullyCollapsed()) {
                DejankUtils.postAfterTraversal(new NotificationPanelViewController$$ExternalSyntheticLambda0(this, i));
                this.mView.postOnAnimation(new NotificationPanelViewController$$ExternalSyntheticLambda0(this, i4));
            } else {
                setListening(true);
            }
            int i5 = this.mBarState;
            QuickSettingsController quickSettingsController = this.mQsController;
            if (i5 != 0) {
                quickSettingsController.setExpandImmediate(false);
            }
            setShowShelfOnly(false);
            quickSettingsController.mTwoFingerExpandPossible = false;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.mTrackedHeadsUpNotification = null;
            int i6 = 0;
            while (true) {
                ArrayList arrayList = notificationPanelViewController.mTrackingHeadsUpListeners;
                if (i6 >= arrayList.size()) {
                    break;
                }
                ((Consumer) arrayList.get(i6)).accept(null);
                i6++;
            }
            this.mExpandingFromHeadsUp = false;
            setPanelScrimMinFraction(0.0f);
            KeyguardStatusBarViewController keyguardStatusBarViewController = this.mKeyguardStatusBarViewController;
            keyguardStatusBarViewController.mExplicitAlpha = -1.0f;
            keyguardStatusBarViewController.updateViewState();
            setExpandSettingsPanel(false);
            boolean z2 = this.mPanelExpanded;
            SecQuickSettingsController secQuickSettingsController = quickSettingsController.mSecQuickSettingsController;
            if (z2) {
                SecExpandQSAtOnceController secExpandQSAtOnceController = secQuickSettingsController.expandQSAtOnceController;
                if (!secExpandQSAtOnceController.mQsExpandSupplier.getAsBoolean()) {
                    secExpandQSAtOnceController.mShouldCloseAtOnce = false;
                }
            } else {
                secQuickSettingsController.openedByTwoFingerDragging = false;
                secQuickSettingsController.expandQSAtOnceController.mShouldCloseAtOnce = false;
            }
            if (isKeyguardShowing()) {
                if (quickSettingsController.mExpanded) {
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "1006");
                }
            } else if (this.mPanelExpanded) {
                if (quickSettingsController.mExpanded) {
                    SystemUIAnalytics.sendEventCDLog("QPP101", "QPNE0001", GlsIntent.Extras.EXTRA_LOCATION, secQuickSettingsController.expandQSAtOnceController.mShouldCloseAtOnce ? "instant access" : secQuickSettingsController.openedByTwoFingerDragging ? "2 fingers" : "from 1st depth");
                } else {
                    SystemUIAnalytics.sendEventLog("QPN001", "QPNE0001");
                }
            }
        }
    }

    public void notifyExpandingStarted() {
        if (this.mExpanding) {
            return;
        }
        DejankUtils.notifyRendererOfExpensiveFrame(this.mView, "notifyExpandingStarted");
        this.mExpanding = true;
        this.mIsExpandingOrCollapsing = true;
        QuickSettingsController quickSettingsController = this.mQsController;
        boolean z = quickSettingsController.mFullyExpanded;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = quickSettingsController.mNotificationStackScrollLayoutController;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.mIsExpansionChanging = true;
        notificationStackScrollLayout.mAmbientState.mExpansionChanging = true;
        notificationStackScrollLayoutController.checkSnoozeLeavebehind();
        quickSettingsController.mExpandedWhenExpandingStarted = z;
        if (quickSettingsController.mExpanded) {
            quickSettingsController.onExpansionStarted();
        }
        InterfaceC1922QS interfaceC1922QS = quickSettingsController.mQs;
        if (interfaceC1922QS != null) {
            interfaceC1922QS.setHeaderListening(true);
        }
        if (this.mBarState == 0) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.mNotificationStackScrollLayoutController;
            if (notificationStackScrollLayoutController2.mHasDelayedForceLayout) {
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController2.mView;
                if (notificationStackScrollLayout2.getHandler() != null) {
                    Handler handler = notificationStackScrollLayout2.getHandler();
                    NotificationStackScrollLayoutController.RunnableC29521 runnableC29521 = notificationStackScrollLayoutController2.mForceLayoutTimeOutRunnable;
                    if (handler.hasCallbacks(runnableC29521)) {
                        notificationStackScrollLayout2.removeCallbacks(runnableC29521);
                        runnableC29521.run();
                    }
                }
            }
        }
    }

    public final void onEmptySpaceClick() {
        if (this.mHintAnimationRunning) {
            return;
        }
        int i = this.mBarState;
        if (i == 0) {
            this.mView.post(this.mPostCollapseRunnable);
            return;
        }
        if (i != 1) {
            if (i == 2 && !this.mQsController.mExpanded) {
                this.mStatusBarStateController.setState$1(1);
                return;
            }
            return;
        }
        if (this.mDozingOnDown) {
            return;
        }
        this.mShadeLog.m190v("onMiddleClicked on Keyguard, mDozingOnDown: false");
        SystemUIKeyguardFaceAuthInteractor systemUIKeyguardFaceAuthInteractor = (SystemUIKeyguardFaceAuthInteractor) this.mKeyguardFaceAuthInteractor;
        systemUIKeyguardFaceAuthInteractor.getClass();
        systemUIKeyguardFaceAuthInteractor.runFaceAuth(FaceAuthUiEvent.FACE_AUTH_TRIGGERED_NOTIFICATION_PANEL_CLICKED);
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        if (keyguardUpdateMonitor.requestFaceAuth("Face auth due to notification panel click.")) {
            keyguardUpdateMonitor.requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin.UNLOCK_INTENT, "lockScreenEmptySpaceTap");
            return;
        }
        this.mLockscreenGestureLogger.write(188, 0, 0);
        new UiEventLoggerImpl().log(LockscreenGestureLogger.LockscreenUiEvent.LOCKSCREEN_LOCK_SHOW_HINT);
        startUnlockHintAnimation();
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x01ee  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x028b  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0379  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00e1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onFinishInflate() {
        FrameLayout frameLayout;
        KeyguardUserSwitcherView keyguardUserSwitcherView;
        KeyguardUserSwitcherController keyguardUserSwitcherController;
        View findViewById;
        View findViewById2;
        PluginFaceWidgetManager pluginFaceWidgetManager;
        FaceWidgetContainerWrapper faceWidgetContainerWrapper;
        loadDimens();
        NotificationPanelView notificationPanelView = this.mView;
        this.mKeyguardStatusBar = (KeyguardStatusBarView) notificationPanelView.findViewById(R.id.keyguard_header);
        if (this.mKeyguardUserSwitcherEnabled) {
            if (this.mUserManager.isUserSwitcherEnabled(this.mResources.getBoolean(R.bool.qs_show_user_switcher_for_single_user))) {
                if (!this.mKeyguardQsUserSwitchEnabled) {
                    keyguardUserSwitcherView = (KeyguardUserSwitcherView) ((ViewStub) notificationPanelView.findViewById(R.id.keyguard_user_switcher_stub)).inflate();
                    frameLayout = null;
                    KeyguardStatusBarViewController keyguardStatusBarViewController = this.mKeyguardStatusBarViewComponentFactory.build(this.mKeyguardStatusBar, this.mShadeViewStateProvider).getKeyguardStatusBarViewController();
                    this.mKeyguardStatusBarViewController = keyguardStatusBarViewController;
                    keyguardStatusBarViewController.init();
                    KeyguardBottomAreaView keyguardBottomAreaView = (KeyguardBottomAreaView) this.mKeyguardBottomAreaViewController.mView;
                    this.mKeyguardBottomArea = keyguardBottomAreaView;
                    if (LsRune.LOCKUI_BOTTOM_USIM_TEXT) {
                    }
                    this.mKeyguardBottomArea.pluginLockData = this.mPluginLockData;
                    final NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
                    NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                    LockscreenNotificationManager lockscreenNotificationManager = this.mLockscreenNotificationManager;
                    lockscreenNotificationManager.mStackScrollLayout = notificationStackScrollLayout;
                    initBottomArea();
                    this.mNotificationContainerParent = (NotificationsQuickSettingsContainer) notificationPanelView.findViewById(R.id.notification_container_parent);
                    KeyguardStatusViewController keyguardStatusViewController = this.mKeyguardStatusViewComponentFactory.build((KeyguardStatusView) notificationPanelView.findViewById(R.id.keyguard_status_view)).getKeyguardStatusViewController();
                    this.mKeyguardStatusViewController = keyguardStatusViewController;
                    keyguardStatusViewController.init();
                    this.mKeyguardStatusViewController.setSplitShadeEnabled(this.mSplitShadeEnabled);
                    updateClockAppearance();
                    keyguardUserSwitcherController = this.mKeyguardUserSwitcherController;
                    int i = 0;
                    if (keyguardUserSwitcherController != null) {
                    }
                    findViewById = notificationPanelView.findViewById(R.id.keyguard_status_view);
                    if (findViewById != null) {
                    }
                    findViewById2 = notificationPanelView.findViewById(R.id.keyguard_clock_container);
                    if (findViewById2 != null) {
                    }
                    pluginFaceWidgetManager = (PluginFaceWidgetManager) Dependency.get(PluginFaceWidgetManager.class);
                    if (pluginFaceWidgetManager == null) {
                    }
                    KeyguardStatusViewController keyguardStatusViewController2 = this.mKeyguardStatusViewController;
                    keyguardStatusViewController2.mKeyguardStatusBase = faceWidgetContainerWrapper;
                    lockscreenNotificationManager.mKeyguardStatusBase = faceWidgetContainerWrapper;
                    keyguardStatusViewController2.mIsDLSViewEnabledSupplier = new NotificationPanelViewController$$ExternalSyntheticLambda15(this, 2);
                    updateUserSwitcherViewControllers(frameLayout, keyguardUserSwitcherView);
                    NsslHeightChangedListener nsslHeightChangedListener = new NsslHeightChangedListener(this, i);
                    NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
                    notificationStackScrollLayout2.mOnHeightChangedListener = nsslHeightChangedListener;
                    notificationStackScrollLayout2.mOnEmptySpaceClickListener = this.mOnEmptySpaceClickListener;
                    QuickSettingsController quickSettingsController = this.mQsController;
                    quickSettingsController.getClass();
                    QuickSettingsController.NsslOverscrollTopChangedListener nsslOverscrollTopChangedListener = new QuickSettingsController.NsslOverscrollTopChangedListener(quickSettingsController, i);
                    NotificationStackScrollLayout notificationStackScrollLayout3 = quickSettingsController.mNotificationStackScrollLayoutController.mView;
                    notificationStackScrollLayout3.mOverscrollTopChangedListener = nsslOverscrollTopChangedListener;
                    notificationStackScrollLayout3.mOnStackYChanged = new QuickSettingsController$$ExternalSyntheticLambda12(quickSettingsController, 0);
                    notificationStackScrollLayout3.mScrollListener = new QuickSettingsController$$ExternalSyntheticLambda12(quickSettingsController, 3);
                    this.mShadeExpansionStateManager.addQsExpansionListener(new ShadeQsExpansionListener() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda16
                        @Override // com.android.systemui.shade.ShadeQsExpansionListener
                        public final void onQsExpansionChanged(boolean z) {
                            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                            notificationPanelViewController.updateExpandedHeightToMaxHeight();
                            ((KeyguardStatusView) notificationPanelViewController.mKeyguardStatusViewController.mView).setImportantForAccessibility(z ? 4 : 0);
                            notificationPanelViewController.updateSystemUiStateFlags();
                            NavigationBarView navigationBarView = notificationPanelViewController.mNavigationBarController.getNavigationBarView(notificationPanelViewController.mDisplayId);
                            if (navigationBarView != null) {
                                navigationBarView.updateSlippery();
                            }
                        }
                    });
                    NotificationPanelViewController.this.mTrackingHeadsUpListeners.add(new NotificationPanelViewController$$ExternalSyntheticLambda1(notificationStackScrollLayoutController, 12));
                    if (LsRune.SECURITY_PUNCH_HOLE_FACE_VI) {
                    }
                    if (LsRune.SECURITY_FINGERPRINT_GUIDE_POPUP) {
                    }
                    final NotificationWakeUpCoordinator notificationWakeUpCoordinator = this.mWakeUpCoordinator;
                    notificationWakeUpCoordinator.mStackScrollerController = notificationStackScrollLayoutController;
                    notificationWakeUpCoordinator.pulseExpanding = notificationStackScrollLayout2.mAmbientState.isPulseExpanding();
                    notificationStackScrollLayout2.mAmbientState.mOnPulseHeightChangedListener = new Runnable() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator$setStackScroller$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = NotificationWakeUpCoordinator.this.mStackScrollerController;
                            if (notificationStackScrollLayoutController2 == null) {
                                notificationStackScrollLayoutController2 = null;
                            }
                            boolean isPulseExpanding = notificationStackScrollLayoutController2.mView.mAmbientState.isPulseExpanding();
                            NotificationWakeUpCoordinator notificationWakeUpCoordinator2 = NotificationWakeUpCoordinator.this;
                            boolean z = isPulseExpanding != notificationWakeUpCoordinator2.pulseExpanding;
                            notificationWakeUpCoordinator2.pulseExpanding = isPulseExpanding;
                            Iterator it = notificationWakeUpCoordinator2.wakeUpListeners.iterator();
                            while (it.hasNext()) {
                                ((NotificationWakeUpCoordinator.WakeUpListener) it.next()).onPulseExpansionChanged(z);
                            }
                        }
                    };
                    this.mPulseExpansionHandler.stackScrollerController = notificationStackScrollLayoutController;
                    notificationWakeUpCoordinator.wakeUpListeners.add(new NotificationWakeUpCoordinator.WakeUpListener() { // from class: com.android.systemui.shade.NotificationPanelViewController.7
                        @Override // com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator.WakeUpListener
                        public final void onDelayedDozeAmountAnimationRunning() {
                            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                            if (notificationPanelViewController.mWillPlayDelayedDozeAmountAnimation) {
                                notificationPanelViewController.mWillPlayDelayedDozeAmountAnimation = false;
                                notificationPanelViewController.mWakeUpCoordinator.logDelayingClockWakeUpAnimation(false);
                                notificationPanelViewController.mKeyguardMediaController.getClass();
                                notificationPanelViewController.positionClockAndNotifications(false);
                            }
                        }

                        @Override // com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator.WakeUpListener
                        public final void onFullyHiddenChanged(boolean z) {
                            NotificationPanelViewController.this.mKeyguardStatusBarViewController.updateForHeadsUp();
                        }

                        @Override // com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator.WakeUpListener
                        public final void onPulseExpansionChanged(boolean z) {
                            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                            if (notificationPanelViewController.mKeyguardBypassController.getBypassEnabled()) {
                                notificationPanelViewController.requestScrollerTopPaddingUpdate(false);
                            }
                        }
                    });
                    notificationPanelView.mRtlChangeListener = new NotificationPanelViewController$$ExternalSyntheticLambda2(this);
                    notificationPanelView.setAccessibilityDelegate(this.mAccessibilityDelegate);
                    final int i2 = 1;
                    if (LsRune.KEYGUARD_DCM_LIVE_UX) {
                    }
                    if (this.mSplitShadeEnabled) {
                    }
                    this.mTapAgainViewController.init();
                    this.mShadeHeaderController.init();
                    this.mKeyguardUnfoldTransition.ifPresent(new NotificationPanelViewController$$ExternalSyntheticLambda1(this, 10));
                    this.mNotificationPanelUnfoldAnimationController.ifPresent(new NotificationPanelViewController$$ExternalSyntheticLambda1(this, 11));
                    KeyguardTransitionInteractor keyguardTransitionInteractor = this.mKeyguardTransitionInteractor;
                    C1633x38e7c866 c1633x38e7c866 = keyguardTransitionInteractor.dreamingToLockscreenTransition;
                    NotificationPanelViewController$$ExternalSyntheticLambda1 notificationPanelViewController$$ExternalSyntheticLambda1 = this.mDreamingToLockscreenTransition;
                    CoroutineDispatcher coroutineDispatcher = this.mMainDispatcher;
                    JavaAdapterKt.collectFlow(notificationPanelView, c1633x38e7c866, notificationPanelViewController$$ExternalSyntheticLambda1, coroutineDispatcher);
                    DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel = this.mDreamingToLockscreenTransitionViewModel;
                    JavaAdapterKt.collectFlow(notificationPanelView, dreamingToLockscreenTransitionViewModel.lockscreenAlpha, new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
                        public final /* synthetic */ NotificationPanelViewController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                            View view;
                            switch (i2) {
                                case 0:
                                    NotificationPanelViewController notificationPanelViewController = this.f$0;
                                    NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationStackScrollLayoutController;
                                    Float f = (Float) obj;
                                    KeyguardStatusViewController keyguardStatusViewController3 = notificationPanelViewController.mKeyguardStatusViewController;
                                    float floatValue = f.floatValue();
                                    KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController3.mView;
                                    keyguardStatusView.getClass();
                                    Set emptySet = Collections.emptySet();
                                    for (int i3 = 0; i3 < keyguardStatusView.mStatusViewContainer.getChildCount(); i3++) {
                                        View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i3);
                                        if (!emptySet.contains(childAt)) {
                                            childAt.setTranslationY(floatValue);
                                        }
                                    }
                                    if (!(!notificationStackScrollLayoutController2.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                                        notificationStackScrollLayoutController2.mView.setTranslationY(f.floatValue());
                                        break;
                                    }
                                    break;
                                default:
                                    NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                                    NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController;
                                    Float f2 = (Float) obj;
                                    KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                                    float floatValue2 = f2.floatValue();
                                    if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                                        view.setAlpha(floatValue2);
                                    }
                                    float floatValue3 = f2.floatValue();
                                    NotificationStackScrollLayout notificationStackScrollLayout4 = notificationStackScrollLayoutController3.mView;
                                    if (notificationStackScrollLayout4 != null) {
                                        notificationStackScrollLayout4.setAlpha(floatValue3);
                                    }
                                    ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                                    float floatValue4 = f2.floatValue();
                                    SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                                    if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                                        ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                                    }
                                    KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                                    if (keyguardQsUserSwitchController != null) {
                                        float floatValue5 = f2.floatValue();
                                        if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                            ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                                        }
                                    }
                                    KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                                    if (keyguardUserSwitcherController2 != null) {
                                        float floatValue6 = f2.floatValue();
                                        if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                            ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                            break;
                                        }
                                    }
                                    break;
                            }
                        }
                    }, coroutineDispatcher);
                    final int i3 = 0;
                    JavaAdapterKt.collectFlow(notificationPanelView, dreamingToLockscreenTransitionViewModel.lockscreenTranslationY(this.mDreamingToLockscreenTransitionTranslationY), new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
                        public final /* synthetic */ NotificationPanelViewController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                            View view;
                            switch (i3) {
                                case 0:
                                    NotificationPanelViewController notificationPanelViewController = this.f$0;
                                    NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationStackScrollLayoutController;
                                    Float f = (Float) obj;
                                    KeyguardStatusViewController keyguardStatusViewController3 = notificationPanelViewController.mKeyguardStatusViewController;
                                    float floatValue = f.floatValue();
                                    KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController3.mView;
                                    keyguardStatusView.getClass();
                                    Set emptySet = Collections.emptySet();
                                    for (int i32 = 0; i32 < keyguardStatusView.mStatusViewContainer.getChildCount(); i32++) {
                                        View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i32);
                                        if (!emptySet.contains(childAt)) {
                                            childAt.setTranslationY(floatValue);
                                        }
                                    }
                                    if (!(!notificationStackScrollLayoutController2.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                                        notificationStackScrollLayoutController2.mView.setTranslationY(f.floatValue());
                                        break;
                                    }
                                    break;
                                default:
                                    NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                                    NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController;
                                    Float f2 = (Float) obj;
                                    KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                                    float floatValue2 = f2.floatValue();
                                    if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                                        view.setAlpha(floatValue2);
                                    }
                                    float floatValue3 = f2.floatValue();
                                    NotificationStackScrollLayout notificationStackScrollLayout4 = notificationStackScrollLayoutController3.mView;
                                    if (notificationStackScrollLayout4 != null) {
                                        notificationStackScrollLayout4.setAlpha(floatValue3);
                                    }
                                    ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                                    float floatValue4 = f2.floatValue();
                                    SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                                    if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                                        ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                                    }
                                    KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                                    if (keyguardQsUserSwitchController != null) {
                                        float floatValue5 = f2.floatValue();
                                        if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                            ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                                        }
                                    }
                                    KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                                    if (keyguardUserSwitcherController2 != null) {
                                        float floatValue6 = f2.floatValue();
                                        if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                            ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                            break;
                                        }
                                    }
                                    break;
                            }
                        }
                    }, coroutineDispatcher);
                    JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionInteractor.occludedToLockscreenTransition, this.mOccludedToLockscreenTransition, coroutineDispatcher);
                    OccludedToLockscreenTransitionViewModel occludedToLockscreenTransitionViewModel = this.mOccludedToLockscreenTransitionViewModel;
                    JavaAdapterKt.collectFlow(notificationPanelView, occludedToLockscreenTransitionViewModel.lockscreenAlpha, new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
                        public final /* synthetic */ NotificationPanelViewController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                            View view;
                            switch (i2) {
                                case 0:
                                    NotificationPanelViewController notificationPanelViewController = this.f$0;
                                    NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationStackScrollLayoutController;
                                    Float f = (Float) obj;
                                    KeyguardStatusViewController keyguardStatusViewController3 = notificationPanelViewController.mKeyguardStatusViewController;
                                    float floatValue = f.floatValue();
                                    KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController3.mView;
                                    keyguardStatusView.getClass();
                                    Set emptySet = Collections.emptySet();
                                    for (int i32 = 0; i32 < keyguardStatusView.mStatusViewContainer.getChildCount(); i32++) {
                                        View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i32);
                                        if (!emptySet.contains(childAt)) {
                                            childAt.setTranslationY(floatValue);
                                        }
                                    }
                                    if (!(!notificationStackScrollLayoutController2.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                                        notificationStackScrollLayoutController2.mView.setTranslationY(f.floatValue());
                                        break;
                                    }
                                    break;
                                default:
                                    NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                                    NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController;
                                    Float f2 = (Float) obj;
                                    KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                                    float floatValue2 = f2.floatValue();
                                    if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                                        view.setAlpha(floatValue2);
                                    }
                                    float floatValue3 = f2.floatValue();
                                    NotificationStackScrollLayout notificationStackScrollLayout4 = notificationStackScrollLayoutController3.mView;
                                    if (notificationStackScrollLayout4 != null) {
                                        notificationStackScrollLayout4.setAlpha(floatValue3);
                                    }
                                    ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                                    float floatValue4 = f2.floatValue();
                                    SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                                    if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                                        ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                                    }
                                    KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                                    if (keyguardQsUserSwitchController != null) {
                                        float floatValue5 = f2.floatValue();
                                        if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                            ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                                        }
                                    }
                                    KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                                    if (keyguardUserSwitcherController2 != null) {
                                        float floatValue6 = f2.floatValue();
                                        if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                            ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                            break;
                                        }
                                    }
                                    break;
                            }
                        }
                    }, coroutineDispatcher);
                    final int i4 = 0;
                    JavaAdapterKt.collectFlow(notificationPanelView, occludedToLockscreenTransitionViewModel.lockscreenTranslationY(this.mOccludedToLockscreenTransitionTranslationY), new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
                        public final /* synthetic */ NotificationPanelViewController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                            View view;
                            switch (i4) {
                                case 0:
                                    NotificationPanelViewController notificationPanelViewController = this.f$0;
                                    NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationStackScrollLayoutController;
                                    Float f = (Float) obj;
                                    KeyguardStatusViewController keyguardStatusViewController3 = notificationPanelViewController.mKeyguardStatusViewController;
                                    float floatValue = f.floatValue();
                                    KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController3.mView;
                                    keyguardStatusView.getClass();
                                    Set emptySet = Collections.emptySet();
                                    for (int i32 = 0; i32 < keyguardStatusView.mStatusViewContainer.getChildCount(); i32++) {
                                        View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i32);
                                        if (!emptySet.contains(childAt)) {
                                            childAt.setTranslationY(floatValue);
                                        }
                                    }
                                    if (!(!notificationStackScrollLayoutController2.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                                        notificationStackScrollLayoutController2.mView.setTranslationY(f.floatValue());
                                        break;
                                    }
                                    break;
                                default:
                                    NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                                    NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController;
                                    Float f2 = (Float) obj;
                                    KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                                    float floatValue2 = f2.floatValue();
                                    if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                                        view.setAlpha(floatValue2);
                                    }
                                    float floatValue3 = f2.floatValue();
                                    NotificationStackScrollLayout notificationStackScrollLayout4 = notificationStackScrollLayoutController3.mView;
                                    if (notificationStackScrollLayout4 != null) {
                                        notificationStackScrollLayout4.setAlpha(floatValue3);
                                    }
                                    ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                                    float floatValue4 = f2.floatValue();
                                    SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                                    if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                                        ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                                    }
                                    KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                                    if (keyguardQsUserSwitchController != null) {
                                        float floatValue5 = f2.floatValue();
                                        if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                            ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                                        }
                                    }
                                    KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                                    if (keyguardUserSwitcherController2 != null) {
                                        float floatValue6 = f2.floatValue();
                                        if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                            ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                            break;
                                        }
                                    }
                                    break;
                            }
                        }
                    }, coroutineDispatcher);
                    JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionInteractor.lockscreenToDreamingTransition, this.mLockscreenToDreamingTransition, coroutineDispatcher);
                    LockscreenToDreamingTransitionViewModel lockscreenToDreamingTransitionViewModel = this.mLockscreenToDreamingTransitionViewModel;
                    JavaAdapterKt.collectFlow(notificationPanelView, lockscreenToDreamingTransitionViewModel.lockscreenAlpha, new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
                        public final /* synthetic */ NotificationPanelViewController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                            View view;
                            switch (i2) {
                                case 0:
                                    NotificationPanelViewController notificationPanelViewController = this.f$0;
                                    NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationStackScrollLayoutController;
                                    Float f = (Float) obj;
                                    KeyguardStatusViewController keyguardStatusViewController3 = notificationPanelViewController.mKeyguardStatusViewController;
                                    float floatValue = f.floatValue();
                                    KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController3.mView;
                                    keyguardStatusView.getClass();
                                    Set emptySet = Collections.emptySet();
                                    for (int i32 = 0; i32 < keyguardStatusView.mStatusViewContainer.getChildCount(); i32++) {
                                        View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i32);
                                        if (!emptySet.contains(childAt)) {
                                            childAt.setTranslationY(floatValue);
                                        }
                                    }
                                    if (!(!notificationStackScrollLayoutController2.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                                        notificationStackScrollLayoutController2.mView.setTranslationY(f.floatValue());
                                        break;
                                    }
                                    break;
                                default:
                                    NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                                    NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController;
                                    Float f2 = (Float) obj;
                                    KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                                    float floatValue2 = f2.floatValue();
                                    if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                                        view.setAlpha(floatValue2);
                                    }
                                    float floatValue3 = f2.floatValue();
                                    NotificationStackScrollLayout notificationStackScrollLayout4 = notificationStackScrollLayoutController3.mView;
                                    if (notificationStackScrollLayout4 != null) {
                                        notificationStackScrollLayout4.setAlpha(floatValue3);
                                    }
                                    ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                                    float floatValue4 = f2.floatValue();
                                    SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                                    if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                                        ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                                    }
                                    KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                                    if (keyguardQsUserSwitchController != null) {
                                        float floatValue5 = f2.floatValue();
                                        if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                            ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                                        }
                                    }
                                    KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                                    if (keyguardUserSwitcherController2 != null) {
                                        float floatValue6 = f2.floatValue();
                                        if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                            ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                            break;
                                        }
                                    }
                                    break;
                            }
                        }
                    }, coroutineDispatcher);
                    final int i5 = 0;
                    JavaAdapterKt.collectFlow(notificationPanelView, lockscreenToDreamingTransitionViewModel.lockscreenTranslationY(this.mLockscreenToDreamingTransitionTranslationY), new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
                        public final /* synthetic */ NotificationPanelViewController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                            View view;
                            switch (i5) {
                                case 0:
                                    NotificationPanelViewController notificationPanelViewController = this.f$0;
                                    NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationStackScrollLayoutController;
                                    Float f = (Float) obj;
                                    KeyguardStatusViewController keyguardStatusViewController3 = notificationPanelViewController.mKeyguardStatusViewController;
                                    float floatValue = f.floatValue();
                                    KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController3.mView;
                                    keyguardStatusView.getClass();
                                    Set emptySet = Collections.emptySet();
                                    for (int i32 = 0; i32 < keyguardStatusView.mStatusViewContainer.getChildCount(); i32++) {
                                        View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i32);
                                        if (!emptySet.contains(childAt)) {
                                            childAt.setTranslationY(floatValue);
                                        }
                                    }
                                    if (!(!notificationStackScrollLayoutController2.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                                        notificationStackScrollLayoutController2.mView.setTranslationY(f.floatValue());
                                        break;
                                    }
                                    break;
                                default:
                                    NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                                    NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController;
                                    Float f2 = (Float) obj;
                                    KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                                    float floatValue2 = f2.floatValue();
                                    if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                                        view.setAlpha(floatValue2);
                                    }
                                    float floatValue3 = f2.floatValue();
                                    NotificationStackScrollLayout notificationStackScrollLayout4 = notificationStackScrollLayoutController3.mView;
                                    if (notificationStackScrollLayout4 != null) {
                                        notificationStackScrollLayout4.setAlpha(floatValue3);
                                    }
                                    ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                                    float floatValue4 = f2.floatValue();
                                    SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                                    if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                                        ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                                    }
                                    KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                                    if (keyguardQsUserSwitchController != null) {
                                        float floatValue5 = f2.floatValue();
                                        if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                            ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                                        }
                                    }
                                    KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                                    if (keyguardUserSwitcherController2 != null) {
                                        float floatValue6 = f2.floatValue();
                                        if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                            ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                            break;
                                        }
                                    }
                                    break;
                            }
                        }
                    }, coroutineDispatcher);
                    JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionInteractor.goneToDreamingTransition, this.mGoneToDreamingTransition, coroutineDispatcher);
                    GoneToDreamingTransitionViewModel goneToDreamingTransitionViewModel = this.mGoneToDreamingTransitionViewModel;
                    JavaAdapterKt.collectFlow(notificationPanelView, goneToDreamingTransitionViewModel.lockscreenAlpha, new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
                        public final /* synthetic */ NotificationPanelViewController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                            View view;
                            switch (i2) {
                                case 0:
                                    NotificationPanelViewController notificationPanelViewController = this.f$0;
                                    NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationStackScrollLayoutController;
                                    Float f = (Float) obj;
                                    KeyguardStatusViewController keyguardStatusViewController3 = notificationPanelViewController.mKeyguardStatusViewController;
                                    float floatValue = f.floatValue();
                                    KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController3.mView;
                                    keyguardStatusView.getClass();
                                    Set emptySet = Collections.emptySet();
                                    for (int i32 = 0; i32 < keyguardStatusView.mStatusViewContainer.getChildCount(); i32++) {
                                        View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i32);
                                        if (!emptySet.contains(childAt)) {
                                            childAt.setTranslationY(floatValue);
                                        }
                                    }
                                    if (!(!notificationStackScrollLayoutController2.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                                        notificationStackScrollLayoutController2.mView.setTranslationY(f.floatValue());
                                        break;
                                    }
                                    break;
                                default:
                                    NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                                    NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController;
                                    Float f2 = (Float) obj;
                                    KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                                    float floatValue2 = f2.floatValue();
                                    if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                                        view.setAlpha(floatValue2);
                                    }
                                    float floatValue3 = f2.floatValue();
                                    NotificationStackScrollLayout notificationStackScrollLayout4 = notificationStackScrollLayoutController3.mView;
                                    if (notificationStackScrollLayout4 != null) {
                                        notificationStackScrollLayout4.setAlpha(floatValue3);
                                    }
                                    ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                                    float floatValue4 = f2.floatValue();
                                    SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                                    if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                                        ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                                    }
                                    KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                                    if (keyguardQsUserSwitchController != null) {
                                        float floatValue5 = f2.floatValue();
                                        if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                            ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                                        }
                                    }
                                    KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                                    if (keyguardUserSwitcherController2 != null) {
                                        float floatValue6 = f2.floatValue();
                                        if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                            ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                            break;
                                        }
                                    }
                                    break;
                            }
                        }
                    }, coroutineDispatcher);
                    final int i6 = 0;
                    JavaAdapterKt.collectFlow(notificationPanelView, goneToDreamingTransitionViewModel.lockscreenTranslationY(this.mGoneToDreamingTransitionTranslationY), new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
                        public final /* synthetic */ NotificationPanelViewController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                            View view;
                            switch (i6) {
                                case 0:
                                    NotificationPanelViewController notificationPanelViewController = this.f$0;
                                    NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationStackScrollLayoutController;
                                    Float f = (Float) obj;
                                    KeyguardStatusViewController keyguardStatusViewController3 = notificationPanelViewController.mKeyguardStatusViewController;
                                    float floatValue = f.floatValue();
                                    KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController3.mView;
                                    keyguardStatusView.getClass();
                                    Set emptySet = Collections.emptySet();
                                    for (int i32 = 0; i32 < keyguardStatusView.mStatusViewContainer.getChildCount(); i32++) {
                                        View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i32);
                                        if (!emptySet.contains(childAt)) {
                                            childAt.setTranslationY(floatValue);
                                        }
                                    }
                                    if (!(!notificationStackScrollLayoutController2.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                                        notificationStackScrollLayoutController2.mView.setTranslationY(f.floatValue());
                                        break;
                                    }
                                    break;
                                default:
                                    NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                                    NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController;
                                    Float f2 = (Float) obj;
                                    KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                                    float floatValue2 = f2.floatValue();
                                    if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                                        view.setAlpha(floatValue2);
                                    }
                                    float floatValue3 = f2.floatValue();
                                    NotificationStackScrollLayout notificationStackScrollLayout4 = notificationStackScrollLayoutController3.mView;
                                    if (notificationStackScrollLayout4 != null) {
                                        notificationStackScrollLayout4.setAlpha(floatValue3);
                                    }
                                    ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                                    float floatValue4 = f2.floatValue();
                                    SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                                    if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                                        ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                                    }
                                    KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                                    if (keyguardQsUserSwitchController != null) {
                                        float floatValue5 = f2.floatValue();
                                        if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                            ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                                        }
                                    }
                                    KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                                    if (keyguardUserSwitcherController2 != null) {
                                        float floatValue6 = f2.floatValue();
                                        if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                            ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                            break;
                                        }
                                    }
                                    break;
                            }
                        }
                    }, coroutineDispatcher);
                    JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionInteractor.lockscreenToOccludedTransition, this.mLockscreenToOccludedTransition, coroutineDispatcher);
                    LockscreenToOccludedTransitionViewModel lockscreenToOccludedTransitionViewModel = this.mLockscreenToOccludedTransitionViewModel;
                    JavaAdapterKt.collectFlow(notificationPanelView, lockscreenToOccludedTransitionViewModel.lockscreenAlpha, new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
                        public final /* synthetic */ NotificationPanelViewController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                            View view;
                            switch (i2) {
                                case 0:
                                    NotificationPanelViewController notificationPanelViewController = this.f$0;
                                    NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationStackScrollLayoutController;
                                    Float f = (Float) obj;
                                    KeyguardStatusViewController keyguardStatusViewController3 = notificationPanelViewController.mKeyguardStatusViewController;
                                    float floatValue = f.floatValue();
                                    KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController3.mView;
                                    keyguardStatusView.getClass();
                                    Set emptySet = Collections.emptySet();
                                    for (int i32 = 0; i32 < keyguardStatusView.mStatusViewContainer.getChildCount(); i32++) {
                                        View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i32);
                                        if (!emptySet.contains(childAt)) {
                                            childAt.setTranslationY(floatValue);
                                        }
                                    }
                                    if (!(!notificationStackScrollLayoutController2.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                                        notificationStackScrollLayoutController2.mView.setTranslationY(f.floatValue());
                                        break;
                                    }
                                    break;
                                default:
                                    NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                                    NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController;
                                    Float f2 = (Float) obj;
                                    KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                                    float floatValue2 = f2.floatValue();
                                    if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                                        view.setAlpha(floatValue2);
                                    }
                                    float floatValue3 = f2.floatValue();
                                    NotificationStackScrollLayout notificationStackScrollLayout4 = notificationStackScrollLayoutController3.mView;
                                    if (notificationStackScrollLayout4 != null) {
                                        notificationStackScrollLayout4.setAlpha(floatValue3);
                                    }
                                    ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                                    float floatValue4 = f2.floatValue();
                                    SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                                    if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                                        ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                                    }
                                    KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                                    if (keyguardQsUserSwitchController != null) {
                                        float floatValue5 = f2.floatValue();
                                        if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                            ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                                        }
                                    }
                                    KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                                    if (keyguardUserSwitcherController2 != null) {
                                        float floatValue6 = f2.floatValue();
                                        if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                            ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                            break;
                                        }
                                    }
                                    break;
                            }
                        }
                    }, coroutineDispatcher);
                    final int i7 = 0;
                    JavaAdapterKt.collectFlow(notificationPanelView, lockscreenToOccludedTransitionViewModel.lockscreenTranslationY(this.mLockscreenToOccludedTransitionTranslationY), new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
                        public final /* synthetic */ NotificationPanelViewController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                            View view;
                            switch (i7) {
                                case 0:
                                    NotificationPanelViewController notificationPanelViewController = this.f$0;
                                    NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationStackScrollLayoutController;
                                    Float f = (Float) obj;
                                    KeyguardStatusViewController keyguardStatusViewController3 = notificationPanelViewController.mKeyguardStatusViewController;
                                    float floatValue = f.floatValue();
                                    KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController3.mView;
                                    keyguardStatusView.getClass();
                                    Set emptySet = Collections.emptySet();
                                    for (int i32 = 0; i32 < keyguardStatusView.mStatusViewContainer.getChildCount(); i32++) {
                                        View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i32);
                                        if (!emptySet.contains(childAt)) {
                                            childAt.setTranslationY(floatValue);
                                        }
                                    }
                                    if (!(!notificationStackScrollLayoutController2.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                                        notificationStackScrollLayoutController2.mView.setTranslationY(f.floatValue());
                                        break;
                                    }
                                    break;
                                default:
                                    NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                                    NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController;
                                    Float f2 = (Float) obj;
                                    KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                                    float floatValue2 = f2.floatValue();
                                    if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                                        view.setAlpha(floatValue2);
                                    }
                                    float floatValue3 = f2.floatValue();
                                    NotificationStackScrollLayout notificationStackScrollLayout4 = notificationStackScrollLayoutController3.mView;
                                    if (notificationStackScrollLayout4 != null) {
                                        notificationStackScrollLayout4.setAlpha(floatValue3);
                                    }
                                    ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                                    float floatValue4 = f2.floatValue();
                                    SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                                    if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                                        ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                                    }
                                    KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                                    if (keyguardQsUserSwitchController != null) {
                                        float floatValue5 = f2.floatValue();
                                        if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                            ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                                        }
                                    }
                                    KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                                    if (keyguardUserSwitcherController2 != null) {
                                        float floatValue6 = f2.floatValue();
                                        if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                            ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                            break;
                                        }
                                    }
                                    break;
                            }
                        }
                    }, coroutineDispatcher);
                    this.mEditModeContainer = notificationPanelView.findViewById(R.id.keyguard_edit_mode_container);
                    LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
                    lockscreenNotificationIconsOnlyController.getClass();
                    lockscreenNotificationManager.addCallback(lockscreenNotificationIconsOnlyController);
                    this.mIsKeyguardSupportLandscapePhone = (DeviceState.shouldEnableKeyguardScreenRotation(notificationPanelView.getContext()) || DeviceType.isTablet() || LsRune.LOCKUI_SUB_DISPLAY_LOCK) ? false : true;
                }
                frameLayout = (FrameLayout) ((ViewStub) notificationPanelView.findViewById(R.id.keyguard_qs_user_switch_stub)).inflate();
                keyguardUserSwitcherView = null;
                KeyguardStatusBarViewController keyguardStatusBarViewController2 = this.mKeyguardStatusBarViewComponentFactory.build(this.mKeyguardStatusBar, this.mShadeViewStateProvider).getKeyguardStatusBarViewController();
                this.mKeyguardStatusBarViewController = keyguardStatusBarViewController2;
                keyguardStatusBarViewController2.init();
                KeyguardBottomAreaView keyguardBottomAreaView2 = (KeyguardBottomAreaView) this.mKeyguardBottomAreaViewController.mView;
                this.mKeyguardBottomArea = keyguardBottomAreaView2;
                if (LsRune.LOCKUI_BOTTOM_USIM_TEXT) {
                    keyguardBottomAreaView2.initEmergencyButton(this.mEmergencyButtonControllerFactory);
                }
                this.mKeyguardBottomArea.pluginLockData = this.mPluginLockData;
                final NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.mNotificationStackScrollLayoutController;
                NotificationStackScrollLayout notificationStackScrollLayout4 = notificationStackScrollLayoutController2.mView;
                LockscreenNotificationManager lockscreenNotificationManager2 = this.mLockscreenNotificationManager;
                lockscreenNotificationManager2.mStackScrollLayout = notificationStackScrollLayout4;
                initBottomArea();
                this.mNotificationContainerParent = (NotificationsQuickSettingsContainer) notificationPanelView.findViewById(R.id.notification_container_parent);
                KeyguardStatusViewController keyguardStatusViewController3 = this.mKeyguardStatusViewComponentFactory.build((KeyguardStatusView) notificationPanelView.findViewById(R.id.keyguard_status_view)).getKeyguardStatusViewController();
                this.mKeyguardStatusViewController = keyguardStatusViewController3;
                keyguardStatusViewController3.init();
                this.mKeyguardStatusViewController.setSplitShadeEnabled(this.mSplitShadeEnabled);
                updateClockAppearance();
                keyguardUserSwitcherController = this.mKeyguardUserSwitcherController;
                int i8 = 0;
                if (keyguardUserSwitcherController != null) {
                    keyguardUserSwitcherController.closeSwitcherIfOpenAndNotSimple(false);
                }
                findViewById = notificationPanelView.findViewById(R.id.keyguard_status_view);
                if (findViewById != null) {
                    notificationPanelView.removeView(findViewById);
                }
                findViewById2 = notificationPanelView.findViewById(R.id.keyguard_clock_container);
                if (findViewById2 != null) {
                    notificationPanelView.removeView(findViewById2);
                }
                pluginFaceWidgetManager = (PluginFaceWidgetManager) Dependency.get(PluginFaceWidgetManager.class);
                if (pluginFaceWidgetManager == null) {
                    Log.e("NotificationPanelView", "Failed to get PluginFaceWidgetManager");
                    faceWidgetContainerWrapper = null;
                } else {
                    faceWidgetContainerWrapper = pluginFaceWidgetManager.mFaceWidgetContainerWrapper;
                    if (faceWidgetContainerWrapper != null) {
                        C24429 c24429 = new C24429();
                        PluginFaceWidgetManager pluginFaceWidgetManager2 = (PluginFaceWidgetManager) Dependency.get(PluginFaceWidgetManager.class);
                        if (pluginFaceWidgetManager2 != null) {
                            FaceWidgetKeyguardStatusCallbackWrapper faceWidgetKeyguardStatusCallbackWrapper = pluginFaceWidgetManager2.mKeyguardStatusCallbackWrapper;
                            if (faceWidgetKeyguardStatusCallbackWrapper instanceof FaceWidgetKeyguardStatusCallbackWrapper) {
                                faceWidgetKeyguardStatusCallbackWrapper.mStatusCallback = c24429;
                            }
                        }
                    }
                }
                KeyguardStatusViewController keyguardStatusViewController22 = this.mKeyguardStatusViewController;
                keyguardStatusViewController22.mKeyguardStatusBase = faceWidgetContainerWrapper;
                lockscreenNotificationManager2.mKeyguardStatusBase = faceWidgetContainerWrapper;
                keyguardStatusViewController22.mIsDLSViewEnabledSupplier = new NotificationPanelViewController$$ExternalSyntheticLambda15(this, 2);
                updateUserSwitcherViewControllers(frameLayout, keyguardUserSwitcherView);
                NsslHeightChangedListener nsslHeightChangedListener2 = new NsslHeightChangedListener(this, i8);
                NotificationStackScrollLayout notificationStackScrollLayout22 = notificationStackScrollLayoutController2.mView;
                notificationStackScrollLayout22.mOnHeightChangedListener = nsslHeightChangedListener2;
                notificationStackScrollLayout22.mOnEmptySpaceClickListener = this.mOnEmptySpaceClickListener;
                QuickSettingsController quickSettingsController2 = this.mQsController;
                quickSettingsController2.getClass();
                QuickSettingsController.NsslOverscrollTopChangedListener nsslOverscrollTopChangedListener2 = new QuickSettingsController.NsslOverscrollTopChangedListener(quickSettingsController2, i8);
                NotificationStackScrollLayout notificationStackScrollLayout32 = quickSettingsController2.mNotificationStackScrollLayoutController.mView;
                notificationStackScrollLayout32.mOverscrollTopChangedListener = nsslOverscrollTopChangedListener2;
                notificationStackScrollLayout32.mOnStackYChanged = new QuickSettingsController$$ExternalSyntheticLambda12(quickSettingsController2, 0);
                notificationStackScrollLayout32.mScrollListener = new QuickSettingsController$$ExternalSyntheticLambda12(quickSettingsController2, 3);
                this.mShadeExpansionStateManager.addQsExpansionListener(new ShadeQsExpansionListener() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda16
                    @Override // com.android.systemui.shade.ShadeQsExpansionListener
                    public final void onQsExpansionChanged(boolean z) {
                        NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                        notificationPanelViewController.updateExpandedHeightToMaxHeight();
                        ((KeyguardStatusView) notificationPanelViewController.mKeyguardStatusViewController.mView).setImportantForAccessibility(z ? 4 : 0);
                        notificationPanelViewController.updateSystemUiStateFlags();
                        NavigationBarView navigationBarView = notificationPanelViewController.mNavigationBarController.getNavigationBarView(notificationPanelViewController.mDisplayId);
                        if (navigationBarView != null) {
                            navigationBarView.updateSlippery();
                        }
                    }
                });
                NotificationPanelViewController.this.mTrackingHeadsUpListeners.add(new NotificationPanelViewController$$ExternalSyntheticLambda1(notificationStackScrollLayoutController2, 12));
                if (LsRune.SECURITY_PUNCH_HOLE_FACE_VI) {
                    ViewStub viewStub = (ViewStub) notificationPanelView.findViewById(R.id.keyguard_punch_hole_vi_view_stub);
                    viewStub.setLayoutResource(R.layout.keyguard_punch_hole_vi_view);
                    KeyguardPunchHoleVIView keyguardPunchHoleVIView = (KeyguardPunchHoleVIView) viewStub.inflate();
                    this.mKeyguardPunchHoleVIView = keyguardPunchHoleVIView;
                    if (keyguardPunchHoleVIView != null) {
                        KeyguardPunchHoleVIViewController.Factory factory = this.mPunchHoleVIViewControllerFactory;
                        new KeyguardPunchHoleVIViewController(keyguardPunchHoleVIView, factory.mHandler, factory.mKeyguardUpdateMonitor, factory.mWakefulnessLifecycle, factory.mSettingsHelper, factory.mDisplayLifecycle, factory.mRotationWatcher, factory.mConfigurationController, factory.mKeyguardEditModeController, factory.mPluginLockStarManager).init();
                        this.mKeyguardPunchHoleVIView.bringToFront();
                    }
                }
                if (LsRune.SECURITY_FINGERPRINT_GUIDE_POPUP) {
                    ViewStub viewStub2 = (ViewStub) notificationPanelView.findViewById(R.id.keyguard_fingerprint_guide_popup_stub);
                    viewStub2.setLayoutResource(R.layout.keyguard_fingerprint_guide_popup);
                    KeyguardFingerprintGuidePopup keyguardFingerprintGuidePopup = (KeyguardFingerprintGuidePopup) viewStub2.inflate();
                    if (keyguardFingerprintGuidePopup != null) {
                        keyguardFingerprintGuidePopup.bringToFront();
                    }
                }
                final NotificationWakeUpCoordinator notificationWakeUpCoordinator2 = this.mWakeUpCoordinator;
                notificationWakeUpCoordinator2.mStackScrollerController = notificationStackScrollLayoutController2;
                notificationWakeUpCoordinator2.pulseExpanding = notificationStackScrollLayout22.mAmbientState.isPulseExpanding();
                notificationStackScrollLayout22.mAmbientState.mOnPulseHeightChangedListener = new Runnable() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator$setStackScroller$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController22 = NotificationWakeUpCoordinator.this.mStackScrollerController;
                        if (notificationStackScrollLayoutController22 == null) {
                            notificationStackScrollLayoutController22 = null;
                        }
                        boolean isPulseExpanding = notificationStackScrollLayoutController22.mView.mAmbientState.isPulseExpanding();
                        NotificationWakeUpCoordinator notificationWakeUpCoordinator22 = NotificationWakeUpCoordinator.this;
                        boolean z = isPulseExpanding != notificationWakeUpCoordinator22.pulseExpanding;
                        notificationWakeUpCoordinator22.pulseExpanding = isPulseExpanding;
                        Iterator it = notificationWakeUpCoordinator22.wakeUpListeners.iterator();
                        while (it.hasNext()) {
                            ((NotificationWakeUpCoordinator.WakeUpListener) it.next()).onPulseExpansionChanged(z);
                        }
                    }
                };
                this.mPulseExpansionHandler.stackScrollerController = notificationStackScrollLayoutController2;
                notificationWakeUpCoordinator2.wakeUpListeners.add(new NotificationWakeUpCoordinator.WakeUpListener() { // from class: com.android.systemui.shade.NotificationPanelViewController.7
                    @Override // com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator.WakeUpListener
                    public final void onDelayedDozeAmountAnimationRunning() {
                        NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                        if (notificationPanelViewController.mWillPlayDelayedDozeAmountAnimation) {
                            notificationPanelViewController.mWillPlayDelayedDozeAmountAnimation = false;
                            notificationPanelViewController.mWakeUpCoordinator.logDelayingClockWakeUpAnimation(false);
                            notificationPanelViewController.mKeyguardMediaController.getClass();
                            notificationPanelViewController.positionClockAndNotifications(false);
                        }
                    }

                    @Override // com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator.WakeUpListener
                    public final void onFullyHiddenChanged(boolean z) {
                        NotificationPanelViewController.this.mKeyguardStatusBarViewController.updateForHeadsUp();
                    }

                    @Override // com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator.WakeUpListener
                    public final void onPulseExpansionChanged(boolean z) {
                        NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                        if (notificationPanelViewController.mKeyguardBypassController.getBypassEnabled()) {
                            notificationPanelViewController.requestScrollerTopPaddingUpdate(false);
                        }
                    }
                });
                notificationPanelView.mRtlChangeListener = new NotificationPanelViewController$$ExternalSyntheticLambda2(this);
                notificationPanelView.setAccessibilityDelegate(this.mAccessibilityDelegate);
                final int i22 = 1;
                if (LsRune.KEYGUARD_DCM_LIVE_UX) {
                    C24418 c24418 = new C24418();
                    DcmMascotViewContainer dcmMascotViewContainer = this.mMascotViewContainer;
                    dcmMascotViewContainer.injector = c24418;
                    dcmMascotViewContainer.updateRes();
                    dcmMascotViewContainer.setLayoutParams(new ViewGroup.LayoutParams(-1, dcmMascotViewContainer.mascotHeight));
                    dcmMascotViewContainer.setGravity(dcmMascotViewContainer.getContext().getResources().getInteger(R.integer.notification_panel_layout_gravity));
                    C24418 c244182 = dcmMascotViewContainer.injector;
                    if (c244182 == null) {
                        c244182 = null;
                    }
                    NotificationPanelViewController.this.mNotificationContainerParent.addView(dcmMascotViewContainer, 0);
                    BroadcastDispatcher.registerReceiver$default(dcmMascotViewContainer.broadcastDispatcher, dcmMascotViewContainer.broadcastReceiver, new IntentFilter("jp.co.nttdocomo.carriermail.APP_LINK_RECEIVED_MESSAGE"), null, null, 0, null, 60);
                    BroadcastDispatcher broadcastDispatcher = dcmMascotViewContainer.broadcastDispatcher;
                    DcmMascotViewContainer$broadcastReceiver$1 dcmMascotViewContainer$broadcastReceiver$1 = dcmMascotViewContainer.broadcastReceiver;
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("com.nttdocomo.android.mascot.KEYGUARD_UPDATE");
                    intentFilter.addAction("com.android.internal.policy.impl.CARRIERMAIL_COUNT_UPDATE");
                    intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
                    intentFilter.addAction("com.nttdocomo.android.mascot.widget.LockScreenMascotWidget.ACTION_SCREEN_UNLOCK");
                    Unit unit = Unit.INSTANCE;
                    BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, dcmMascotViewContainer$broadcastReceiver$1, intentFilter, null, null, 0, "com.nttdocomo.android.screenlockservice.DCM_SCREEN", 28);
                    if (dcmMascotViewContainer.isBootCompleted) {
                        dcmMascotViewContainer.setMascotRemoteViews(dcmMascotViewContainer.remoteViews);
                    } else {
                        dcmMascotViewContainer.isWaitingForBootComplete = true;
                    }
                    dcmMascotViewContainer.bgExecutor.execute(new DcmMascotViewContainer$sendUnreadCountBroadcast$1(dcmMascotViewContainer));
                    dcmMascotViewContainer.updateMonitor.registerCallback(dcmMascotViewContainer.updateMonitorCallback);
                }
                if (this.mSplitShadeEnabled) {
                    updateResources();
                }
                this.mTapAgainViewController.init();
                this.mShadeHeaderController.init();
                this.mKeyguardUnfoldTransition.ifPresent(new NotificationPanelViewController$$ExternalSyntheticLambda1(this, 10));
                this.mNotificationPanelUnfoldAnimationController.ifPresent(new NotificationPanelViewController$$ExternalSyntheticLambda1(this, 11));
                KeyguardTransitionInteractor keyguardTransitionInteractor2 = this.mKeyguardTransitionInteractor;
                C1633x38e7c866 c1633x38e7c8662 = keyguardTransitionInteractor2.dreamingToLockscreenTransition;
                NotificationPanelViewController$$ExternalSyntheticLambda1 notificationPanelViewController$$ExternalSyntheticLambda12 = this.mDreamingToLockscreenTransition;
                CoroutineDispatcher coroutineDispatcher2 = this.mMainDispatcher;
                JavaAdapterKt.collectFlow(notificationPanelView, c1633x38e7c8662, notificationPanelViewController$$ExternalSyntheticLambda12, coroutineDispatcher2);
                DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel2 = this.mDreamingToLockscreenTransitionViewModel;
                JavaAdapterKt.collectFlow(notificationPanelView, dreamingToLockscreenTransitionViewModel2.lockscreenAlpha, new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
                    public final /* synthetic */ NotificationPanelViewController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                        View view;
                        switch (i22) {
                            case 0:
                                NotificationPanelViewController notificationPanelViewController = this.f$0;
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController22 = notificationStackScrollLayoutController2;
                                Float f = (Float) obj;
                                KeyguardStatusViewController keyguardStatusViewController32 = notificationPanelViewController.mKeyguardStatusViewController;
                                float floatValue = f.floatValue();
                                KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController32.mView;
                                keyguardStatusView.getClass();
                                Set emptySet = Collections.emptySet();
                                for (int i32 = 0; i32 < keyguardStatusView.mStatusViewContainer.getChildCount(); i32++) {
                                    View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i32);
                                    if (!emptySet.contains(childAt)) {
                                        childAt.setTranslationY(floatValue);
                                    }
                                }
                                if (!(!notificationStackScrollLayoutController22.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                                    notificationStackScrollLayoutController22.mView.setTranslationY(f.floatValue());
                                    break;
                                }
                                break;
                            default:
                                NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController2;
                                Float f2 = (Float) obj;
                                KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                                float floatValue2 = f2.floatValue();
                                if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                                    view.setAlpha(floatValue2);
                                }
                                float floatValue3 = f2.floatValue();
                                NotificationStackScrollLayout notificationStackScrollLayout42 = notificationStackScrollLayoutController3.mView;
                                if (notificationStackScrollLayout42 != null) {
                                    notificationStackScrollLayout42.setAlpha(floatValue3);
                                }
                                ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                                float floatValue4 = f2.floatValue();
                                SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                                if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                                    ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                                }
                                KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                                if (keyguardQsUserSwitchController != null) {
                                    float floatValue5 = f2.floatValue();
                                    if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                        ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                                    }
                                }
                                KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                                if (keyguardUserSwitcherController2 != null) {
                                    float floatValue6 = f2.floatValue();
                                    if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                        ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                }, coroutineDispatcher2);
                final int i32 = 0;
                JavaAdapterKt.collectFlow(notificationPanelView, dreamingToLockscreenTransitionViewModel2.lockscreenTranslationY(this.mDreamingToLockscreenTransitionTranslationY), new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
                    public final /* synthetic */ NotificationPanelViewController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                        View view;
                        switch (i32) {
                            case 0:
                                NotificationPanelViewController notificationPanelViewController = this.f$0;
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController22 = notificationStackScrollLayoutController2;
                                Float f = (Float) obj;
                                KeyguardStatusViewController keyguardStatusViewController32 = notificationPanelViewController.mKeyguardStatusViewController;
                                float floatValue = f.floatValue();
                                KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController32.mView;
                                keyguardStatusView.getClass();
                                Set emptySet = Collections.emptySet();
                                for (int i322 = 0; i322 < keyguardStatusView.mStatusViewContainer.getChildCount(); i322++) {
                                    View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i322);
                                    if (!emptySet.contains(childAt)) {
                                        childAt.setTranslationY(floatValue);
                                    }
                                }
                                if (!(!notificationStackScrollLayoutController22.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                                    notificationStackScrollLayoutController22.mView.setTranslationY(f.floatValue());
                                    break;
                                }
                                break;
                            default:
                                NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController2;
                                Float f2 = (Float) obj;
                                KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                                float floatValue2 = f2.floatValue();
                                if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                                    view.setAlpha(floatValue2);
                                }
                                float floatValue3 = f2.floatValue();
                                NotificationStackScrollLayout notificationStackScrollLayout42 = notificationStackScrollLayoutController3.mView;
                                if (notificationStackScrollLayout42 != null) {
                                    notificationStackScrollLayout42.setAlpha(floatValue3);
                                }
                                ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                                float floatValue4 = f2.floatValue();
                                SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                                if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                                    ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                                }
                                KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                                if (keyguardQsUserSwitchController != null) {
                                    float floatValue5 = f2.floatValue();
                                    if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                        ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                                    }
                                }
                                KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                                if (keyguardUserSwitcherController2 != null) {
                                    float floatValue6 = f2.floatValue();
                                    if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                        ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                }, coroutineDispatcher2);
                JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionInteractor2.occludedToLockscreenTransition, this.mOccludedToLockscreenTransition, coroutineDispatcher2);
                OccludedToLockscreenTransitionViewModel occludedToLockscreenTransitionViewModel2 = this.mOccludedToLockscreenTransitionViewModel;
                JavaAdapterKt.collectFlow(notificationPanelView, occludedToLockscreenTransitionViewModel2.lockscreenAlpha, new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
                    public final /* synthetic */ NotificationPanelViewController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                        View view;
                        switch (i22) {
                            case 0:
                                NotificationPanelViewController notificationPanelViewController = this.f$0;
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController22 = notificationStackScrollLayoutController2;
                                Float f = (Float) obj;
                                KeyguardStatusViewController keyguardStatusViewController32 = notificationPanelViewController.mKeyguardStatusViewController;
                                float floatValue = f.floatValue();
                                KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController32.mView;
                                keyguardStatusView.getClass();
                                Set emptySet = Collections.emptySet();
                                for (int i322 = 0; i322 < keyguardStatusView.mStatusViewContainer.getChildCount(); i322++) {
                                    View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i322);
                                    if (!emptySet.contains(childAt)) {
                                        childAt.setTranslationY(floatValue);
                                    }
                                }
                                if (!(!notificationStackScrollLayoutController22.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                                    notificationStackScrollLayoutController22.mView.setTranslationY(f.floatValue());
                                    break;
                                }
                                break;
                            default:
                                NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController2;
                                Float f2 = (Float) obj;
                                KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                                float floatValue2 = f2.floatValue();
                                if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                                    view.setAlpha(floatValue2);
                                }
                                float floatValue3 = f2.floatValue();
                                NotificationStackScrollLayout notificationStackScrollLayout42 = notificationStackScrollLayoutController3.mView;
                                if (notificationStackScrollLayout42 != null) {
                                    notificationStackScrollLayout42.setAlpha(floatValue3);
                                }
                                ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                                float floatValue4 = f2.floatValue();
                                SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                                if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                                    ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                                }
                                KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                                if (keyguardQsUserSwitchController != null) {
                                    float floatValue5 = f2.floatValue();
                                    if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                        ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                                    }
                                }
                                KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                                if (keyguardUserSwitcherController2 != null) {
                                    float floatValue6 = f2.floatValue();
                                    if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                        ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                }, coroutineDispatcher2);
                final int i42 = 0;
                JavaAdapterKt.collectFlow(notificationPanelView, occludedToLockscreenTransitionViewModel2.lockscreenTranslationY(this.mOccludedToLockscreenTransitionTranslationY), new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
                    public final /* synthetic */ NotificationPanelViewController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                        View view;
                        switch (i42) {
                            case 0:
                                NotificationPanelViewController notificationPanelViewController = this.f$0;
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController22 = notificationStackScrollLayoutController2;
                                Float f = (Float) obj;
                                KeyguardStatusViewController keyguardStatusViewController32 = notificationPanelViewController.mKeyguardStatusViewController;
                                float floatValue = f.floatValue();
                                KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController32.mView;
                                keyguardStatusView.getClass();
                                Set emptySet = Collections.emptySet();
                                for (int i322 = 0; i322 < keyguardStatusView.mStatusViewContainer.getChildCount(); i322++) {
                                    View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i322);
                                    if (!emptySet.contains(childAt)) {
                                        childAt.setTranslationY(floatValue);
                                    }
                                }
                                if (!(!notificationStackScrollLayoutController22.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                                    notificationStackScrollLayoutController22.mView.setTranslationY(f.floatValue());
                                    break;
                                }
                                break;
                            default:
                                NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController2;
                                Float f2 = (Float) obj;
                                KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                                float floatValue2 = f2.floatValue();
                                if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                                    view.setAlpha(floatValue2);
                                }
                                float floatValue3 = f2.floatValue();
                                NotificationStackScrollLayout notificationStackScrollLayout42 = notificationStackScrollLayoutController3.mView;
                                if (notificationStackScrollLayout42 != null) {
                                    notificationStackScrollLayout42.setAlpha(floatValue3);
                                }
                                ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                                float floatValue4 = f2.floatValue();
                                SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                                if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                                    ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                                }
                                KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                                if (keyguardQsUserSwitchController != null) {
                                    float floatValue5 = f2.floatValue();
                                    if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                        ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                                    }
                                }
                                KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                                if (keyguardUserSwitcherController2 != null) {
                                    float floatValue6 = f2.floatValue();
                                    if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                        ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                }, coroutineDispatcher2);
                JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionInteractor2.lockscreenToDreamingTransition, this.mLockscreenToDreamingTransition, coroutineDispatcher2);
                LockscreenToDreamingTransitionViewModel lockscreenToDreamingTransitionViewModel2 = this.mLockscreenToDreamingTransitionViewModel;
                JavaAdapterKt.collectFlow(notificationPanelView, lockscreenToDreamingTransitionViewModel2.lockscreenAlpha, new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
                    public final /* synthetic */ NotificationPanelViewController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                        View view;
                        switch (i22) {
                            case 0:
                                NotificationPanelViewController notificationPanelViewController = this.f$0;
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController22 = notificationStackScrollLayoutController2;
                                Float f = (Float) obj;
                                KeyguardStatusViewController keyguardStatusViewController32 = notificationPanelViewController.mKeyguardStatusViewController;
                                float floatValue = f.floatValue();
                                KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController32.mView;
                                keyguardStatusView.getClass();
                                Set emptySet = Collections.emptySet();
                                for (int i322 = 0; i322 < keyguardStatusView.mStatusViewContainer.getChildCount(); i322++) {
                                    View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i322);
                                    if (!emptySet.contains(childAt)) {
                                        childAt.setTranslationY(floatValue);
                                    }
                                }
                                if (!(!notificationStackScrollLayoutController22.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                                    notificationStackScrollLayoutController22.mView.setTranslationY(f.floatValue());
                                    break;
                                }
                                break;
                            default:
                                NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController2;
                                Float f2 = (Float) obj;
                                KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                                float floatValue2 = f2.floatValue();
                                if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                                    view.setAlpha(floatValue2);
                                }
                                float floatValue3 = f2.floatValue();
                                NotificationStackScrollLayout notificationStackScrollLayout42 = notificationStackScrollLayoutController3.mView;
                                if (notificationStackScrollLayout42 != null) {
                                    notificationStackScrollLayout42.setAlpha(floatValue3);
                                }
                                ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                                float floatValue4 = f2.floatValue();
                                SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                                if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                                    ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                                }
                                KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                                if (keyguardQsUserSwitchController != null) {
                                    float floatValue5 = f2.floatValue();
                                    if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                        ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                                    }
                                }
                                KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                                if (keyguardUserSwitcherController2 != null) {
                                    float floatValue6 = f2.floatValue();
                                    if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                        ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                }, coroutineDispatcher2);
                final int i52 = 0;
                JavaAdapterKt.collectFlow(notificationPanelView, lockscreenToDreamingTransitionViewModel2.lockscreenTranslationY(this.mLockscreenToDreamingTransitionTranslationY), new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
                    public final /* synthetic */ NotificationPanelViewController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                        View view;
                        switch (i52) {
                            case 0:
                                NotificationPanelViewController notificationPanelViewController = this.f$0;
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController22 = notificationStackScrollLayoutController2;
                                Float f = (Float) obj;
                                KeyguardStatusViewController keyguardStatusViewController32 = notificationPanelViewController.mKeyguardStatusViewController;
                                float floatValue = f.floatValue();
                                KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController32.mView;
                                keyguardStatusView.getClass();
                                Set emptySet = Collections.emptySet();
                                for (int i322 = 0; i322 < keyguardStatusView.mStatusViewContainer.getChildCount(); i322++) {
                                    View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i322);
                                    if (!emptySet.contains(childAt)) {
                                        childAt.setTranslationY(floatValue);
                                    }
                                }
                                if (!(!notificationStackScrollLayoutController22.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                                    notificationStackScrollLayoutController22.mView.setTranslationY(f.floatValue());
                                    break;
                                }
                                break;
                            default:
                                NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController2;
                                Float f2 = (Float) obj;
                                KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                                float floatValue2 = f2.floatValue();
                                if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                                    view.setAlpha(floatValue2);
                                }
                                float floatValue3 = f2.floatValue();
                                NotificationStackScrollLayout notificationStackScrollLayout42 = notificationStackScrollLayoutController3.mView;
                                if (notificationStackScrollLayout42 != null) {
                                    notificationStackScrollLayout42.setAlpha(floatValue3);
                                }
                                ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                                float floatValue4 = f2.floatValue();
                                SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                                if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                                    ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                                }
                                KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                                if (keyguardQsUserSwitchController != null) {
                                    float floatValue5 = f2.floatValue();
                                    if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                        ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                                    }
                                }
                                KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                                if (keyguardUserSwitcherController2 != null) {
                                    float floatValue6 = f2.floatValue();
                                    if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                        ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                }, coroutineDispatcher2);
                JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionInteractor2.goneToDreamingTransition, this.mGoneToDreamingTransition, coroutineDispatcher2);
                GoneToDreamingTransitionViewModel goneToDreamingTransitionViewModel2 = this.mGoneToDreamingTransitionViewModel;
                JavaAdapterKt.collectFlow(notificationPanelView, goneToDreamingTransitionViewModel2.lockscreenAlpha, new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
                    public final /* synthetic */ NotificationPanelViewController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                        View view;
                        switch (i22) {
                            case 0:
                                NotificationPanelViewController notificationPanelViewController = this.f$0;
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController22 = notificationStackScrollLayoutController2;
                                Float f = (Float) obj;
                                KeyguardStatusViewController keyguardStatusViewController32 = notificationPanelViewController.mKeyguardStatusViewController;
                                float floatValue = f.floatValue();
                                KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController32.mView;
                                keyguardStatusView.getClass();
                                Set emptySet = Collections.emptySet();
                                for (int i322 = 0; i322 < keyguardStatusView.mStatusViewContainer.getChildCount(); i322++) {
                                    View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i322);
                                    if (!emptySet.contains(childAt)) {
                                        childAt.setTranslationY(floatValue);
                                    }
                                }
                                if (!(!notificationStackScrollLayoutController22.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                                    notificationStackScrollLayoutController22.mView.setTranslationY(f.floatValue());
                                    break;
                                }
                                break;
                            default:
                                NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController2;
                                Float f2 = (Float) obj;
                                KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                                float floatValue2 = f2.floatValue();
                                if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                                    view.setAlpha(floatValue2);
                                }
                                float floatValue3 = f2.floatValue();
                                NotificationStackScrollLayout notificationStackScrollLayout42 = notificationStackScrollLayoutController3.mView;
                                if (notificationStackScrollLayout42 != null) {
                                    notificationStackScrollLayout42.setAlpha(floatValue3);
                                }
                                ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                                float floatValue4 = f2.floatValue();
                                SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                                if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                                    ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                                }
                                KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                                if (keyguardQsUserSwitchController != null) {
                                    float floatValue5 = f2.floatValue();
                                    if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                        ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                                    }
                                }
                                KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                                if (keyguardUserSwitcherController2 != null) {
                                    float floatValue6 = f2.floatValue();
                                    if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                        ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                }, coroutineDispatcher2);
                final int i62 = 0;
                JavaAdapterKt.collectFlow(notificationPanelView, goneToDreamingTransitionViewModel2.lockscreenTranslationY(this.mGoneToDreamingTransitionTranslationY), new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
                    public final /* synthetic */ NotificationPanelViewController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                        View view;
                        switch (i62) {
                            case 0:
                                NotificationPanelViewController notificationPanelViewController = this.f$0;
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController22 = notificationStackScrollLayoutController2;
                                Float f = (Float) obj;
                                KeyguardStatusViewController keyguardStatusViewController32 = notificationPanelViewController.mKeyguardStatusViewController;
                                float floatValue = f.floatValue();
                                KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController32.mView;
                                keyguardStatusView.getClass();
                                Set emptySet = Collections.emptySet();
                                for (int i322 = 0; i322 < keyguardStatusView.mStatusViewContainer.getChildCount(); i322++) {
                                    View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i322);
                                    if (!emptySet.contains(childAt)) {
                                        childAt.setTranslationY(floatValue);
                                    }
                                }
                                if (!(!notificationStackScrollLayoutController22.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                                    notificationStackScrollLayoutController22.mView.setTranslationY(f.floatValue());
                                    break;
                                }
                                break;
                            default:
                                NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController2;
                                Float f2 = (Float) obj;
                                KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                                float floatValue2 = f2.floatValue();
                                if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                                    view.setAlpha(floatValue2);
                                }
                                float floatValue3 = f2.floatValue();
                                NotificationStackScrollLayout notificationStackScrollLayout42 = notificationStackScrollLayoutController3.mView;
                                if (notificationStackScrollLayout42 != null) {
                                    notificationStackScrollLayout42.setAlpha(floatValue3);
                                }
                                ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                                float floatValue4 = f2.floatValue();
                                SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                                if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                                    ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                                }
                                KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                                if (keyguardQsUserSwitchController != null) {
                                    float floatValue5 = f2.floatValue();
                                    if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                        ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                                    }
                                }
                                KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                                if (keyguardUserSwitcherController2 != null) {
                                    float floatValue6 = f2.floatValue();
                                    if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                        ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                }, coroutineDispatcher2);
                JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionInteractor2.lockscreenToOccludedTransition, this.mLockscreenToOccludedTransition, coroutineDispatcher2);
                LockscreenToOccludedTransitionViewModel lockscreenToOccludedTransitionViewModel2 = this.mLockscreenToOccludedTransitionViewModel;
                JavaAdapterKt.collectFlow(notificationPanelView, lockscreenToOccludedTransitionViewModel2.lockscreenAlpha, new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
                    public final /* synthetic */ NotificationPanelViewController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                        View view;
                        switch (i22) {
                            case 0:
                                NotificationPanelViewController notificationPanelViewController = this.f$0;
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController22 = notificationStackScrollLayoutController2;
                                Float f = (Float) obj;
                                KeyguardStatusViewController keyguardStatusViewController32 = notificationPanelViewController.mKeyguardStatusViewController;
                                float floatValue = f.floatValue();
                                KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController32.mView;
                                keyguardStatusView.getClass();
                                Set emptySet = Collections.emptySet();
                                for (int i322 = 0; i322 < keyguardStatusView.mStatusViewContainer.getChildCount(); i322++) {
                                    View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i322);
                                    if (!emptySet.contains(childAt)) {
                                        childAt.setTranslationY(floatValue);
                                    }
                                }
                                if (!(!notificationStackScrollLayoutController22.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                                    notificationStackScrollLayoutController22.mView.setTranslationY(f.floatValue());
                                    break;
                                }
                                break;
                            default:
                                NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController2;
                                Float f2 = (Float) obj;
                                KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                                float floatValue2 = f2.floatValue();
                                if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                                    view.setAlpha(floatValue2);
                                }
                                float floatValue3 = f2.floatValue();
                                NotificationStackScrollLayout notificationStackScrollLayout42 = notificationStackScrollLayoutController3.mView;
                                if (notificationStackScrollLayout42 != null) {
                                    notificationStackScrollLayout42.setAlpha(floatValue3);
                                }
                                ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                                float floatValue4 = f2.floatValue();
                                SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                                if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                                    ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                                }
                                KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                                if (keyguardQsUserSwitchController != null) {
                                    float floatValue5 = f2.floatValue();
                                    if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                        ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                                    }
                                }
                                KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                                if (keyguardUserSwitcherController2 != null) {
                                    float floatValue6 = f2.floatValue();
                                    if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                        ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                }, coroutineDispatcher2);
                final int i72 = 0;
                JavaAdapterKt.collectFlow(notificationPanelView, lockscreenToOccludedTransitionViewModel2.lockscreenTranslationY(this.mLockscreenToOccludedTransitionTranslationY), new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
                    public final /* synthetic */ NotificationPanelViewController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                        View view;
                        switch (i72) {
                            case 0:
                                NotificationPanelViewController notificationPanelViewController = this.f$0;
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController22 = notificationStackScrollLayoutController2;
                                Float f = (Float) obj;
                                KeyguardStatusViewController keyguardStatusViewController32 = notificationPanelViewController.mKeyguardStatusViewController;
                                float floatValue = f.floatValue();
                                KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController32.mView;
                                keyguardStatusView.getClass();
                                Set emptySet = Collections.emptySet();
                                for (int i322 = 0; i322 < keyguardStatusView.mStatusViewContainer.getChildCount(); i322++) {
                                    View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i322);
                                    if (!emptySet.contains(childAt)) {
                                        childAt.setTranslationY(floatValue);
                                    }
                                }
                                if (!(!notificationStackScrollLayoutController22.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                                    notificationStackScrollLayoutController22.mView.setTranslationY(f.floatValue());
                                    break;
                                }
                                break;
                            default:
                                NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController2;
                                Float f2 = (Float) obj;
                                KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                                float floatValue2 = f2.floatValue();
                                if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                                    view.setAlpha(floatValue2);
                                }
                                float floatValue3 = f2.floatValue();
                                NotificationStackScrollLayout notificationStackScrollLayout42 = notificationStackScrollLayoutController3.mView;
                                if (notificationStackScrollLayout42 != null) {
                                    notificationStackScrollLayout42.setAlpha(floatValue3);
                                }
                                ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                                float floatValue4 = f2.floatValue();
                                SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                                if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                                    ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                                }
                                KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                                if (keyguardQsUserSwitchController != null) {
                                    float floatValue5 = f2.floatValue();
                                    if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                        ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                                    }
                                }
                                KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                                if (keyguardUserSwitcherController2 != null) {
                                    float floatValue6 = f2.floatValue();
                                    if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                        ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                }, coroutineDispatcher2);
                this.mEditModeContainer = notificationPanelView.findViewById(R.id.keyguard_edit_mode_container);
                LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController2 = this.mLockscreenNotificationIconsOnlyController;
                lockscreenNotificationIconsOnlyController2.getClass();
                lockscreenNotificationManager2.addCallback(lockscreenNotificationIconsOnlyController2);
                this.mIsKeyguardSupportLandscapePhone = (DeviceState.shouldEnableKeyguardScreenRotation(notificationPanelView.getContext()) || DeviceType.isTablet() || LsRune.LOCKUI_SUB_DISPLAY_LOCK) ? false : true;
            }
        }
        frameLayout = null;
        keyguardUserSwitcherView = null;
        KeyguardStatusBarViewController keyguardStatusBarViewController22 = this.mKeyguardStatusBarViewComponentFactory.build(this.mKeyguardStatusBar, this.mShadeViewStateProvider).getKeyguardStatusBarViewController();
        this.mKeyguardStatusBarViewController = keyguardStatusBarViewController22;
        keyguardStatusBarViewController22.init();
        KeyguardBottomAreaView keyguardBottomAreaView22 = (KeyguardBottomAreaView) this.mKeyguardBottomAreaViewController.mView;
        this.mKeyguardBottomArea = keyguardBottomAreaView22;
        if (LsRune.LOCKUI_BOTTOM_USIM_TEXT) {
        }
        this.mKeyguardBottomArea.pluginLockData = this.mPluginLockData;
        final NotificationStackScrollLayoutController notificationStackScrollLayoutController22 = this.mNotificationStackScrollLayoutController;
        NotificationStackScrollLayout notificationStackScrollLayout42 = notificationStackScrollLayoutController22.mView;
        LockscreenNotificationManager lockscreenNotificationManager22 = this.mLockscreenNotificationManager;
        lockscreenNotificationManager22.mStackScrollLayout = notificationStackScrollLayout42;
        initBottomArea();
        this.mNotificationContainerParent = (NotificationsQuickSettingsContainer) notificationPanelView.findViewById(R.id.notification_container_parent);
        KeyguardStatusViewController keyguardStatusViewController32 = this.mKeyguardStatusViewComponentFactory.build((KeyguardStatusView) notificationPanelView.findViewById(R.id.keyguard_status_view)).getKeyguardStatusViewController();
        this.mKeyguardStatusViewController = keyguardStatusViewController32;
        keyguardStatusViewController32.init();
        this.mKeyguardStatusViewController.setSplitShadeEnabled(this.mSplitShadeEnabled);
        updateClockAppearance();
        keyguardUserSwitcherController = this.mKeyguardUserSwitcherController;
        int i82 = 0;
        if (keyguardUserSwitcherController != null) {
        }
        findViewById = notificationPanelView.findViewById(R.id.keyguard_status_view);
        if (findViewById != null) {
        }
        findViewById2 = notificationPanelView.findViewById(R.id.keyguard_clock_container);
        if (findViewById2 != null) {
        }
        pluginFaceWidgetManager = (PluginFaceWidgetManager) Dependency.get(PluginFaceWidgetManager.class);
        if (pluginFaceWidgetManager == null) {
        }
        KeyguardStatusViewController keyguardStatusViewController222 = this.mKeyguardStatusViewController;
        keyguardStatusViewController222.mKeyguardStatusBase = faceWidgetContainerWrapper;
        lockscreenNotificationManager22.mKeyguardStatusBase = faceWidgetContainerWrapper;
        keyguardStatusViewController222.mIsDLSViewEnabledSupplier = new NotificationPanelViewController$$ExternalSyntheticLambda15(this, 2);
        updateUserSwitcherViewControllers(frameLayout, keyguardUserSwitcherView);
        NsslHeightChangedListener nsslHeightChangedListener22 = new NsslHeightChangedListener(this, i82);
        NotificationStackScrollLayout notificationStackScrollLayout222 = notificationStackScrollLayoutController22.mView;
        notificationStackScrollLayout222.mOnHeightChangedListener = nsslHeightChangedListener22;
        notificationStackScrollLayout222.mOnEmptySpaceClickListener = this.mOnEmptySpaceClickListener;
        QuickSettingsController quickSettingsController22 = this.mQsController;
        quickSettingsController22.getClass();
        QuickSettingsController.NsslOverscrollTopChangedListener nsslOverscrollTopChangedListener22 = new QuickSettingsController.NsslOverscrollTopChangedListener(quickSettingsController22, i82);
        NotificationStackScrollLayout notificationStackScrollLayout322 = quickSettingsController22.mNotificationStackScrollLayoutController.mView;
        notificationStackScrollLayout322.mOverscrollTopChangedListener = nsslOverscrollTopChangedListener22;
        notificationStackScrollLayout322.mOnStackYChanged = new QuickSettingsController$$ExternalSyntheticLambda12(quickSettingsController22, 0);
        notificationStackScrollLayout322.mScrollListener = new QuickSettingsController$$ExternalSyntheticLambda12(quickSettingsController22, 3);
        this.mShadeExpansionStateManager.addQsExpansionListener(new ShadeQsExpansionListener() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda16
            @Override // com.android.systemui.shade.ShadeQsExpansionListener
            public final void onQsExpansionChanged(boolean z) {
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                notificationPanelViewController.updateExpandedHeightToMaxHeight();
                ((KeyguardStatusView) notificationPanelViewController.mKeyguardStatusViewController.mView).setImportantForAccessibility(z ? 4 : 0);
                notificationPanelViewController.updateSystemUiStateFlags();
                NavigationBarView navigationBarView = notificationPanelViewController.mNavigationBarController.getNavigationBarView(notificationPanelViewController.mDisplayId);
                if (navigationBarView != null) {
                    navigationBarView.updateSlippery();
                }
            }
        });
        NotificationPanelViewController.this.mTrackingHeadsUpListeners.add(new NotificationPanelViewController$$ExternalSyntheticLambda1(notificationStackScrollLayoutController22, 12));
        if (LsRune.SECURITY_PUNCH_HOLE_FACE_VI) {
        }
        if (LsRune.SECURITY_FINGERPRINT_GUIDE_POPUP) {
        }
        final NotificationWakeUpCoordinator notificationWakeUpCoordinator22 = this.mWakeUpCoordinator;
        notificationWakeUpCoordinator22.mStackScrollerController = notificationStackScrollLayoutController22;
        notificationWakeUpCoordinator22.pulseExpanding = notificationStackScrollLayout222.mAmbientState.isPulseExpanding();
        notificationStackScrollLayout222.mAmbientState.mOnPulseHeightChangedListener = new Runnable() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator$setStackScroller$1
            @Override // java.lang.Runnable
            public final void run() {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController222 = NotificationWakeUpCoordinator.this.mStackScrollerController;
                if (notificationStackScrollLayoutController222 == null) {
                    notificationStackScrollLayoutController222 = null;
                }
                boolean isPulseExpanding = notificationStackScrollLayoutController222.mView.mAmbientState.isPulseExpanding();
                NotificationWakeUpCoordinator notificationWakeUpCoordinator222 = NotificationWakeUpCoordinator.this;
                boolean z = isPulseExpanding != notificationWakeUpCoordinator222.pulseExpanding;
                notificationWakeUpCoordinator222.pulseExpanding = isPulseExpanding;
                Iterator it = notificationWakeUpCoordinator222.wakeUpListeners.iterator();
                while (it.hasNext()) {
                    ((NotificationWakeUpCoordinator.WakeUpListener) it.next()).onPulseExpansionChanged(z);
                }
            }
        };
        this.mPulseExpansionHandler.stackScrollerController = notificationStackScrollLayoutController22;
        notificationWakeUpCoordinator22.wakeUpListeners.add(new NotificationWakeUpCoordinator.WakeUpListener() { // from class: com.android.systemui.shade.NotificationPanelViewController.7
            @Override // com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator.WakeUpListener
            public final void onDelayedDozeAmountAnimationRunning() {
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                if (notificationPanelViewController.mWillPlayDelayedDozeAmountAnimation) {
                    notificationPanelViewController.mWillPlayDelayedDozeAmountAnimation = false;
                    notificationPanelViewController.mWakeUpCoordinator.logDelayingClockWakeUpAnimation(false);
                    notificationPanelViewController.mKeyguardMediaController.getClass();
                    notificationPanelViewController.positionClockAndNotifications(false);
                }
            }

            @Override // com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator.WakeUpListener
            public final void onFullyHiddenChanged(boolean z) {
                NotificationPanelViewController.this.mKeyguardStatusBarViewController.updateForHeadsUp();
            }

            @Override // com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator.WakeUpListener
            public final void onPulseExpansionChanged(boolean z) {
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                if (notificationPanelViewController.mKeyguardBypassController.getBypassEnabled()) {
                    notificationPanelViewController.requestScrollerTopPaddingUpdate(false);
                }
            }
        });
        notificationPanelView.mRtlChangeListener = new NotificationPanelViewController$$ExternalSyntheticLambda2(this);
        notificationPanelView.setAccessibilityDelegate(this.mAccessibilityDelegate);
        final int i222 = 1;
        if (LsRune.KEYGUARD_DCM_LIVE_UX) {
        }
        if (this.mSplitShadeEnabled) {
        }
        this.mTapAgainViewController.init();
        this.mShadeHeaderController.init();
        this.mKeyguardUnfoldTransition.ifPresent(new NotificationPanelViewController$$ExternalSyntheticLambda1(this, 10));
        this.mNotificationPanelUnfoldAnimationController.ifPresent(new NotificationPanelViewController$$ExternalSyntheticLambda1(this, 11));
        KeyguardTransitionInteractor keyguardTransitionInteractor22 = this.mKeyguardTransitionInteractor;
        C1633x38e7c866 c1633x38e7c86622 = keyguardTransitionInteractor22.dreamingToLockscreenTransition;
        NotificationPanelViewController$$ExternalSyntheticLambda1 notificationPanelViewController$$ExternalSyntheticLambda122 = this.mDreamingToLockscreenTransition;
        CoroutineDispatcher coroutineDispatcher22 = this.mMainDispatcher;
        JavaAdapterKt.collectFlow(notificationPanelView, c1633x38e7c86622, notificationPanelViewController$$ExternalSyntheticLambda122, coroutineDispatcher22);
        DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel22 = this.mDreamingToLockscreenTransitionViewModel;
        JavaAdapterKt.collectFlow(notificationPanelView, dreamingToLockscreenTransitionViewModel22.lockscreenAlpha, new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
            public final /* synthetic */ NotificationPanelViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                View view;
                switch (i222) {
                    case 0:
                        NotificationPanelViewController notificationPanelViewController = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController222 = notificationStackScrollLayoutController22;
                        Float f = (Float) obj;
                        KeyguardStatusViewController keyguardStatusViewController322 = notificationPanelViewController.mKeyguardStatusViewController;
                        float floatValue = f.floatValue();
                        KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController322.mView;
                        keyguardStatusView.getClass();
                        Set emptySet = Collections.emptySet();
                        for (int i322 = 0; i322 < keyguardStatusView.mStatusViewContainer.getChildCount(); i322++) {
                            View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i322);
                            if (!emptySet.contains(childAt)) {
                                childAt.setTranslationY(floatValue);
                            }
                        }
                        if (!(!notificationStackScrollLayoutController222.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                            notificationStackScrollLayoutController222.mView.setTranslationY(f.floatValue());
                            break;
                        }
                        break;
                    default:
                        NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController22;
                        Float f2 = (Float) obj;
                        KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                        float floatValue2 = f2.floatValue();
                        if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                            view.setAlpha(floatValue2);
                        }
                        float floatValue3 = f2.floatValue();
                        NotificationStackScrollLayout notificationStackScrollLayout422 = notificationStackScrollLayoutController3.mView;
                        if (notificationStackScrollLayout422 != null) {
                            notificationStackScrollLayout422.setAlpha(floatValue3);
                        }
                        ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                        float floatValue4 = f2.floatValue();
                        SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                        if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                            ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                        }
                        KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                        if (keyguardQsUserSwitchController != null) {
                            float floatValue5 = f2.floatValue();
                            if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                            }
                        }
                        KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                        if (keyguardUserSwitcherController2 != null) {
                            float floatValue6 = f2.floatValue();
                            if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                break;
                            }
                        }
                        break;
                }
            }
        }, coroutineDispatcher22);
        final int i322 = 0;
        JavaAdapterKt.collectFlow(notificationPanelView, dreamingToLockscreenTransitionViewModel22.lockscreenTranslationY(this.mDreamingToLockscreenTransitionTranslationY), new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
            public final /* synthetic */ NotificationPanelViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                View view;
                switch (i322) {
                    case 0:
                        NotificationPanelViewController notificationPanelViewController = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController222 = notificationStackScrollLayoutController22;
                        Float f = (Float) obj;
                        KeyguardStatusViewController keyguardStatusViewController322 = notificationPanelViewController.mKeyguardStatusViewController;
                        float floatValue = f.floatValue();
                        KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController322.mView;
                        keyguardStatusView.getClass();
                        Set emptySet = Collections.emptySet();
                        for (int i3222 = 0; i3222 < keyguardStatusView.mStatusViewContainer.getChildCount(); i3222++) {
                            View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i3222);
                            if (!emptySet.contains(childAt)) {
                                childAt.setTranslationY(floatValue);
                            }
                        }
                        if (!(!notificationStackScrollLayoutController222.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                            notificationStackScrollLayoutController222.mView.setTranslationY(f.floatValue());
                            break;
                        }
                        break;
                    default:
                        NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController22;
                        Float f2 = (Float) obj;
                        KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                        float floatValue2 = f2.floatValue();
                        if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                            view.setAlpha(floatValue2);
                        }
                        float floatValue3 = f2.floatValue();
                        NotificationStackScrollLayout notificationStackScrollLayout422 = notificationStackScrollLayoutController3.mView;
                        if (notificationStackScrollLayout422 != null) {
                            notificationStackScrollLayout422.setAlpha(floatValue3);
                        }
                        ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                        float floatValue4 = f2.floatValue();
                        SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                        if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                            ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                        }
                        KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                        if (keyguardQsUserSwitchController != null) {
                            float floatValue5 = f2.floatValue();
                            if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                            }
                        }
                        KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                        if (keyguardUserSwitcherController2 != null) {
                            float floatValue6 = f2.floatValue();
                            if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                break;
                            }
                        }
                        break;
                }
            }
        }, coroutineDispatcher22);
        JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionInteractor22.occludedToLockscreenTransition, this.mOccludedToLockscreenTransition, coroutineDispatcher22);
        OccludedToLockscreenTransitionViewModel occludedToLockscreenTransitionViewModel22 = this.mOccludedToLockscreenTransitionViewModel;
        JavaAdapterKt.collectFlow(notificationPanelView, occludedToLockscreenTransitionViewModel22.lockscreenAlpha, new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
            public final /* synthetic */ NotificationPanelViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                View view;
                switch (i222) {
                    case 0:
                        NotificationPanelViewController notificationPanelViewController = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController222 = notificationStackScrollLayoutController22;
                        Float f = (Float) obj;
                        KeyguardStatusViewController keyguardStatusViewController322 = notificationPanelViewController.mKeyguardStatusViewController;
                        float floatValue = f.floatValue();
                        KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController322.mView;
                        keyguardStatusView.getClass();
                        Set emptySet = Collections.emptySet();
                        for (int i3222 = 0; i3222 < keyguardStatusView.mStatusViewContainer.getChildCount(); i3222++) {
                            View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i3222);
                            if (!emptySet.contains(childAt)) {
                                childAt.setTranslationY(floatValue);
                            }
                        }
                        if (!(!notificationStackScrollLayoutController222.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                            notificationStackScrollLayoutController222.mView.setTranslationY(f.floatValue());
                            break;
                        }
                        break;
                    default:
                        NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController22;
                        Float f2 = (Float) obj;
                        KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                        float floatValue2 = f2.floatValue();
                        if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                            view.setAlpha(floatValue2);
                        }
                        float floatValue3 = f2.floatValue();
                        NotificationStackScrollLayout notificationStackScrollLayout422 = notificationStackScrollLayoutController3.mView;
                        if (notificationStackScrollLayout422 != null) {
                            notificationStackScrollLayout422.setAlpha(floatValue3);
                        }
                        ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                        float floatValue4 = f2.floatValue();
                        SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                        if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                            ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                        }
                        KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                        if (keyguardQsUserSwitchController != null) {
                            float floatValue5 = f2.floatValue();
                            if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                            }
                        }
                        KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                        if (keyguardUserSwitcherController2 != null) {
                            float floatValue6 = f2.floatValue();
                            if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                break;
                            }
                        }
                        break;
                }
            }
        }, coroutineDispatcher22);
        final int i422 = 0;
        JavaAdapterKt.collectFlow(notificationPanelView, occludedToLockscreenTransitionViewModel22.lockscreenTranslationY(this.mOccludedToLockscreenTransitionTranslationY), new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
            public final /* synthetic */ NotificationPanelViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                View view;
                switch (i422) {
                    case 0:
                        NotificationPanelViewController notificationPanelViewController = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController222 = notificationStackScrollLayoutController22;
                        Float f = (Float) obj;
                        KeyguardStatusViewController keyguardStatusViewController322 = notificationPanelViewController.mKeyguardStatusViewController;
                        float floatValue = f.floatValue();
                        KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController322.mView;
                        keyguardStatusView.getClass();
                        Set emptySet = Collections.emptySet();
                        for (int i3222 = 0; i3222 < keyguardStatusView.mStatusViewContainer.getChildCount(); i3222++) {
                            View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i3222);
                            if (!emptySet.contains(childAt)) {
                                childAt.setTranslationY(floatValue);
                            }
                        }
                        if (!(!notificationStackScrollLayoutController222.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                            notificationStackScrollLayoutController222.mView.setTranslationY(f.floatValue());
                            break;
                        }
                        break;
                    default:
                        NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController22;
                        Float f2 = (Float) obj;
                        KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                        float floatValue2 = f2.floatValue();
                        if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                            view.setAlpha(floatValue2);
                        }
                        float floatValue3 = f2.floatValue();
                        NotificationStackScrollLayout notificationStackScrollLayout422 = notificationStackScrollLayoutController3.mView;
                        if (notificationStackScrollLayout422 != null) {
                            notificationStackScrollLayout422.setAlpha(floatValue3);
                        }
                        ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                        float floatValue4 = f2.floatValue();
                        SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                        if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                            ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                        }
                        KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                        if (keyguardQsUserSwitchController != null) {
                            float floatValue5 = f2.floatValue();
                            if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                            }
                        }
                        KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                        if (keyguardUserSwitcherController2 != null) {
                            float floatValue6 = f2.floatValue();
                            if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                break;
                            }
                        }
                        break;
                }
            }
        }, coroutineDispatcher22);
        JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionInteractor22.lockscreenToDreamingTransition, this.mLockscreenToDreamingTransition, coroutineDispatcher22);
        LockscreenToDreamingTransitionViewModel lockscreenToDreamingTransitionViewModel22 = this.mLockscreenToDreamingTransitionViewModel;
        JavaAdapterKt.collectFlow(notificationPanelView, lockscreenToDreamingTransitionViewModel22.lockscreenAlpha, new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
            public final /* synthetic */ NotificationPanelViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                View view;
                switch (i222) {
                    case 0:
                        NotificationPanelViewController notificationPanelViewController = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController222 = notificationStackScrollLayoutController22;
                        Float f = (Float) obj;
                        KeyguardStatusViewController keyguardStatusViewController322 = notificationPanelViewController.mKeyguardStatusViewController;
                        float floatValue = f.floatValue();
                        KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController322.mView;
                        keyguardStatusView.getClass();
                        Set emptySet = Collections.emptySet();
                        for (int i3222 = 0; i3222 < keyguardStatusView.mStatusViewContainer.getChildCount(); i3222++) {
                            View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i3222);
                            if (!emptySet.contains(childAt)) {
                                childAt.setTranslationY(floatValue);
                            }
                        }
                        if (!(!notificationStackScrollLayoutController222.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                            notificationStackScrollLayoutController222.mView.setTranslationY(f.floatValue());
                            break;
                        }
                        break;
                    default:
                        NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController22;
                        Float f2 = (Float) obj;
                        KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                        float floatValue2 = f2.floatValue();
                        if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                            view.setAlpha(floatValue2);
                        }
                        float floatValue3 = f2.floatValue();
                        NotificationStackScrollLayout notificationStackScrollLayout422 = notificationStackScrollLayoutController3.mView;
                        if (notificationStackScrollLayout422 != null) {
                            notificationStackScrollLayout422.setAlpha(floatValue3);
                        }
                        ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                        float floatValue4 = f2.floatValue();
                        SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                        if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                            ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                        }
                        KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                        if (keyguardQsUserSwitchController != null) {
                            float floatValue5 = f2.floatValue();
                            if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                            }
                        }
                        KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                        if (keyguardUserSwitcherController2 != null) {
                            float floatValue6 = f2.floatValue();
                            if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                break;
                            }
                        }
                        break;
                }
            }
        }, coroutineDispatcher22);
        final int i522 = 0;
        JavaAdapterKt.collectFlow(notificationPanelView, lockscreenToDreamingTransitionViewModel22.lockscreenTranslationY(this.mLockscreenToDreamingTransitionTranslationY), new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
            public final /* synthetic */ NotificationPanelViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                View view;
                switch (i522) {
                    case 0:
                        NotificationPanelViewController notificationPanelViewController = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController222 = notificationStackScrollLayoutController22;
                        Float f = (Float) obj;
                        KeyguardStatusViewController keyguardStatusViewController322 = notificationPanelViewController.mKeyguardStatusViewController;
                        float floatValue = f.floatValue();
                        KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController322.mView;
                        keyguardStatusView.getClass();
                        Set emptySet = Collections.emptySet();
                        for (int i3222 = 0; i3222 < keyguardStatusView.mStatusViewContainer.getChildCount(); i3222++) {
                            View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i3222);
                            if (!emptySet.contains(childAt)) {
                                childAt.setTranslationY(floatValue);
                            }
                        }
                        if (!(!notificationStackScrollLayoutController222.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                            notificationStackScrollLayoutController222.mView.setTranslationY(f.floatValue());
                            break;
                        }
                        break;
                    default:
                        NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController22;
                        Float f2 = (Float) obj;
                        KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                        float floatValue2 = f2.floatValue();
                        if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                            view.setAlpha(floatValue2);
                        }
                        float floatValue3 = f2.floatValue();
                        NotificationStackScrollLayout notificationStackScrollLayout422 = notificationStackScrollLayoutController3.mView;
                        if (notificationStackScrollLayout422 != null) {
                            notificationStackScrollLayout422.setAlpha(floatValue3);
                        }
                        ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                        float floatValue4 = f2.floatValue();
                        SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                        if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                            ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                        }
                        KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                        if (keyguardQsUserSwitchController != null) {
                            float floatValue5 = f2.floatValue();
                            if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                            }
                        }
                        KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                        if (keyguardUserSwitcherController2 != null) {
                            float floatValue6 = f2.floatValue();
                            if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                break;
                            }
                        }
                        break;
                }
            }
        }, coroutineDispatcher22);
        JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionInteractor22.goneToDreamingTransition, this.mGoneToDreamingTransition, coroutineDispatcher22);
        GoneToDreamingTransitionViewModel goneToDreamingTransitionViewModel22 = this.mGoneToDreamingTransitionViewModel;
        JavaAdapterKt.collectFlow(notificationPanelView, goneToDreamingTransitionViewModel22.lockscreenAlpha, new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
            public final /* synthetic */ NotificationPanelViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                View view;
                switch (i222) {
                    case 0:
                        NotificationPanelViewController notificationPanelViewController = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController222 = notificationStackScrollLayoutController22;
                        Float f = (Float) obj;
                        KeyguardStatusViewController keyguardStatusViewController322 = notificationPanelViewController.mKeyguardStatusViewController;
                        float floatValue = f.floatValue();
                        KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController322.mView;
                        keyguardStatusView.getClass();
                        Set emptySet = Collections.emptySet();
                        for (int i3222 = 0; i3222 < keyguardStatusView.mStatusViewContainer.getChildCount(); i3222++) {
                            View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i3222);
                            if (!emptySet.contains(childAt)) {
                                childAt.setTranslationY(floatValue);
                            }
                        }
                        if (!(!notificationStackScrollLayoutController222.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                            notificationStackScrollLayoutController222.mView.setTranslationY(f.floatValue());
                            break;
                        }
                        break;
                    default:
                        NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController22;
                        Float f2 = (Float) obj;
                        KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                        float floatValue2 = f2.floatValue();
                        if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                            view.setAlpha(floatValue2);
                        }
                        float floatValue3 = f2.floatValue();
                        NotificationStackScrollLayout notificationStackScrollLayout422 = notificationStackScrollLayoutController3.mView;
                        if (notificationStackScrollLayout422 != null) {
                            notificationStackScrollLayout422.setAlpha(floatValue3);
                        }
                        ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                        float floatValue4 = f2.floatValue();
                        SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                        if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                            ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                        }
                        KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                        if (keyguardQsUserSwitchController != null) {
                            float floatValue5 = f2.floatValue();
                            if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                            }
                        }
                        KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                        if (keyguardUserSwitcherController2 != null) {
                            float floatValue6 = f2.floatValue();
                            if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                break;
                            }
                        }
                        break;
                }
            }
        }, coroutineDispatcher22);
        final int i622 = 0;
        JavaAdapterKt.collectFlow(notificationPanelView, goneToDreamingTransitionViewModel22.lockscreenTranslationY(this.mGoneToDreamingTransitionTranslationY), new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
            public final /* synthetic */ NotificationPanelViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                View view;
                switch (i622) {
                    case 0:
                        NotificationPanelViewController notificationPanelViewController = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController222 = notificationStackScrollLayoutController22;
                        Float f = (Float) obj;
                        KeyguardStatusViewController keyguardStatusViewController322 = notificationPanelViewController.mKeyguardStatusViewController;
                        float floatValue = f.floatValue();
                        KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController322.mView;
                        keyguardStatusView.getClass();
                        Set emptySet = Collections.emptySet();
                        for (int i3222 = 0; i3222 < keyguardStatusView.mStatusViewContainer.getChildCount(); i3222++) {
                            View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i3222);
                            if (!emptySet.contains(childAt)) {
                                childAt.setTranslationY(floatValue);
                            }
                        }
                        if (!(!notificationStackScrollLayoutController222.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                            notificationStackScrollLayoutController222.mView.setTranslationY(f.floatValue());
                            break;
                        }
                        break;
                    default:
                        NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController22;
                        Float f2 = (Float) obj;
                        KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                        float floatValue2 = f2.floatValue();
                        if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                            view.setAlpha(floatValue2);
                        }
                        float floatValue3 = f2.floatValue();
                        NotificationStackScrollLayout notificationStackScrollLayout422 = notificationStackScrollLayoutController3.mView;
                        if (notificationStackScrollLayout422 != null) {
                            notificationStackScrollLayout422.setAlpha(floatValue3);
                        }
                        ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                        float floatValue4 = f2.floatValue();
                        SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                        if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                            ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                        }
                        KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                        if (keyguardQsUserSwitchController != null) {
                            float floatValue5 = f2.floatValue();
                            if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                            }
                        }
                        KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                        if (keyguardUserSwitcherController2 != null) {
                            float floatValue6 = f2.floatValue();
                            if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                break;
                            }
                        }
                        break;
                }
            }
        }, coroutineDispatcher22);
        JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionInteractor22.lockscreenToOccludedTransition, this.mLockscreenToOccludedTransition, coroutineDispatcher22);
        LockscreenToOccludedTransitionViewModel lockscreenToOccludedTransitionViewModel22 = this.mLockscreenToOccludedTransitionViewModel;
        JavaAdapterKt.collectFlow(notificationPanelView, lockscreenToOccludedTransitionViewModel22.lockscreenAlpha, new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
            public final /* synthetic */ NotificationPanelViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                View view;
                switch (i222) {
                    case 0:
                        NotificationPanelViewController notificationPanelViewController = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController222 = notificationStackScrollLayoutController22;
                        Float f = (Float) obj;
                        KeyguardStatusViewController keyguardStatusViewController322 = notificationPanelViewController.mKeyguardStatusViewController;
                        float floatValue = f.floatValue();
                        KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController322.mView;
                        keyguardStatusView.getClass();
                        Set emptySet = Collections.emptySet();
                        for (int i3222 = 0; i3222 < keyguardStatusView.mStatusViewContainer.getChildCount(); i3222++) {
                            View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i3222);
                            if (!emptySet.contains(childAt)) {
                                childAt.setTranslationY(floatValue);
                            }
                        }
                        if (!(!notificationStackScrollLayoutController222.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                            notificationStackScrollLayoutController222.mView.setTranslationY(f.floatValue());
                            break;
                        }
                        break;
                    default:
                        NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController22;
                        Float f2 = (Float) obj;
                        KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                        float floatValue2 = f2.floatValue();
                        if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                            view.setAlpha(floatValue2);
                        }
                        float floatValue3 = f2.floatValue();
                        NotificationStackScrollLayout notificationStackScrollLayout422 = notificationStackScrollLayoutController3.mView;
                        if (notificationStackScrollLayout422 != null) {
                            notificationStackScrollLayout422.setAlpha(floatValue3);
                        }
                        ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                        float floatValue4 = f2.floatValue();
                        SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                        if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                            ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                        }
                        KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                        if (keyguardQsUserSwitchController != null) {
                            float floatValue5 = f2.floatValue();
                            if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                            }
                        }
                        KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                        if (keyguardUserSwitcherController2 != null) {
                            float floatValue6 = f2.floatValue();
                            if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                break;
                            }
                        }
                        break;
                }
            }
        }, coroutineDispatcher22);
        final int i722 = 0;
        JavaAdapterKt.collectFlow(notificationPanelView, lockscreenToOccludedTransitionViewModel22.lockscreenTranslationY(this.mLockscreenToOccludedTransitionTranslationY), new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
            public final /* synthetic */ NotificationPanelViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FaceWidgetContainerWrapper faceWidgetContainerWrapper2;
                View view;
                switch (i722) {
                    case 0:
                        NotificationPanelViewController notificationPanelViewController = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController222 = notificationStackScrollLayoutController22;
                        Float f = (Float) obj;
                        KeyguardStatusViewController keyguardStatusViewController322 = notificationPanelViewController.mKeyguardStatusViewController;
                        float floatValue = f.floatValue();
                        KeyguardStatusView keyguardStatusView = (KeyguardStatusView) keyguardStatusViewController322.mView;
                        keyguardStatusView.getClass();
                        Set emptySet = Collections.emptySet();
                        for (int i3222 = 0; i3222 < keyguardStatusView.mStatusViewContainer.getChildCount(); i3222++) {
                            View childAt = keyguardStatusView.mStatusViewContainer.getChildAt(i3222);
                            if (!emptySet.contains(childAt)) {
                                childAt.setTranslationY(floatValue);
                            }
                        }
                        if (!(!notificationStackScrollLayoutController222.mView.mStateAnimator.mAnimatorSet.isEmpty())) {
                            notificationStackScrollLayoutController222.mView.setTranslationY(f.floatValue());
                            break;
                        }
                        break;
                    default:
                        NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController22;
                        Float f2 = (Float) obj;
                        KeyguardStatusViewController keyguardStatusViewController4 = notificationPanelViewController2.mKeyguardStatusViewController;
                        float floatValue2 = f2.floatValue();
                        if (!keyguardStatusViewController4.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper2 = keyguardStatusViewController4.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper2.mFaceWidgetContainer) != null) {
                            view.setAlpha(floatValue2);
                        }
                        float floatValue3 = f2.floatValue();
                        NotificationStackScrollLayout notificationStackScrollLayout422 = notificationStackScrollLayoutController3.mView;
                        if (notificationStackScrollLayout422 != null) {
                            notificationStackScrollLayout422.setAlpha(floatValue3);
                        }
                        ((KeyguardRepositoryImpl) notificationPanelViewController2.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(f2.floatValue()));
                        float floatValue4 = f2.floatValue();
                        SecLockIconViewController secLockIconViewController = notificationPanelViewController2.mLockIconViewController;
                        if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
                            ((LockIconView) secLockIconViewController.mView).setAlpha(floatValue4);
                        }
                        KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController2.mKeyguardQsUserSwitchController;
                        if (keyguardQsUserSwitchController != null) {
                            float floatValue5 = f2.floatValue();
                            if (!keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(floatValue5);
                            }
                        }
                        KeyguardUserSwitcherController keyguardUserSwitcherController2 = notificationPanelViewController2.mKeyguardUserSwitcherController;
                        if (keyguardUserSwitcherController2 != null) {
                            float floatValue6 = f2.floatValue();
                            if (!keyguardUserSwitcherController2.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                                ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).setAlpha(floatValue6);
                                break;
                            }
                        }
                        break;
                }
            }
        }, coroutineDispatcher22);
        this.mEditModeContainer = notificationPanelView.findViewById(R.id.keyguard_edit_mode_container);
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController22 = this.mLockscreenNotificationIconsOnlyController;
        lockscreenNotificationIconsOnlyController22.getClass();
        lockscreenNotificationManager22.addCallback(lockscreenNotificationIconsOnlyController22);
        this.mIsKeyguardSupportLandscapePhone = (DeviceState.shouldEnableKeyguardScreenRotation(notificationPanelView.getContext()) || DeviceType.isTablet() || LsRune.LOCKUI_SUB_DISPLAY_LOCK) ? false : true;
    }

    public void onFlingEnd(boolean z) {
        this.mIsFlinging = false;
        setOverExpansionInternal(0.0f, false);
        setAnimator(null);
        KeyguardStateControllerImpl keyguardStateControllerImpl = this.mKeyguardStateController;
        keyguardStateControllerImpl.mFlingingToDismissKeyguard = false;
        keyguardStateControllerImpl.mFlingingToDismissKeyguardDuringSwipeGesture = false;
        keyguardStateControllerImpl.mSnappingKeyguardBackAfterSwipe = false;
        QuickSettingsController quickSettingsController = this.mQsController;
        if (!z) {
            if (quickSettingsController.mInteractionJankMonitor != null) {
                InteractionJankMonitor.getInstance().end(0);
            }
            notifyExpandingFinished();
        } else if (quickSettingsController.mInteractionJankMonitor != null) {
            InteractionJankMonitor.getInstance().cancel(0);
        }
        updateExpansionAndVisibility();
        NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScrollLayoutController.mView;
        AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
        if (ambientState.isOnKeyguard() && ambientState.mIsFlinging) {
            ambientState.mIsFlingRequiredAfterLockScreenSwipeUp = false;
        }
        ambientState.mIsFlinging = false;
        notificationStackScrollLayout.updateStackPosition(false);
        quickSettingsController.setExpandImmediate(false);
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
    public final void onLockStarEnabled(boolean z) {
        LogUtil.m223d("NotificationPanelView", AbstractC0866xb1ce8deb.m86m("onLockStarEnabled : ", z), new Object[0]);
        this.mLockStarEnabled = z;
        if (z) {
            this.mPluginLockStarContainer = (View) ((PluginLockStarManager) Dependency.get(PluginLockStarManager.class)).get("lockstarContainer");
            return;
        }
        View view = this.mPluginLockStarContainer;
        if (view != null) {
            view.setVisibility(8);
            this.mPluginLockStarContainer = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0071  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onQsSetExpansionHeightCalled(boolean z) {
        boolean z2;
        boolean z3 = false;
        requestScrollerTopPaddingUpdate(false);
        this.mKeyguardStatusBarViewController.updateViewState();
        int i = this.mBarState;
        if (i == 2 || i == 1) {
            updateKeyguardBottomAreaAlpha();
            positionClockAndNotifications(false);
        }
        if (this.mAccessibilityManager.isEnabled()) {
            this.mView.setAccessibilityPaneTitle(determineAccessibilityPaneTitle());
        }
        if (!this.mFalsingManager.isUnlockingDisabled() && z) {
            this.mFalsingCollector.getClass();
        }
        boolean z4 = QpRune.PANEL_DATA_USAGE_LABEL;
        if (z4) {
            Lazy lazy = this.mDataUsageLabelManagerLazy;
            ((DataUsageLabelManager) lazy.get()).updateLabelVisibility(false);
            DataUsageLabelManager dataUsageLabelManager = (DataUsageLabelManager) lazy.get();
            dataUsageLabelManager.getClass();
            if (z4 && dataUsageLabelManager.mLastOrientation != 2) {
                SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = (SlimIndicatorViewMediatorImpl) dataUsageLabelManager.mQuickStarHelper.mSlimIndicatorViewMediator;
                if (slimIndicatorViewMediatorImpl.mPluginMediator.mIsSPluginConnected) {
                    if (slimIndicatorViewMediatorImpl.mCarrierCrew.mIsPanelCarrierDisabled == 1) {
                        z2 = true;
                        if (!z2) {
                            z3 = true;
                        }
                    }
                }
                z2 = false;
                if (!z2) {
                }
            }
            if (dataUsageLabelManager.mPreviousVisibleWithoutAnimation != z3) {
                dataUsageLabelManager.mPreviousVisibleWithoutAnimation = z3;
                ((SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class)).mDataUsageLabelVisible = z3;
            }
            if (this.dataUsageVisible != z3) {
                this.dataUsageVisible = z3;
                this.mNotificationsQSContainerController.updateConstraints();
            }
        }
    }

    public final void onTrackingStarted() {
        boolean z = this.mKeyguardStateController.mCanDismissLockScreen;
        this.mFalsingCollector.getClass();
        endClosing();
        StringBuilder sb = this.mLogBuilder;
        sb.setLength(0);
        sb.append("onTrackingStarted: ");
        sb.append(", mTracking: ");
        sb.append(this.mTracking);
        sb.append(" -> true :");
        sb.append("\n");
        sb.append(Debug.getCallers(5, " - "));
        ((SecPanelLoggerImpl) this.mPanelLogger).addPanelStateInfoLog(sb, false);
        this.mTracking = true;
        this.mTouchDownOnHeadsUpPinnded = false;
        this.mTrackingStartedListener.f$0.runPostCollapseRunnables();
        notifyExpandingStarted();
        updateExpansionAndVisibility();
        ScrimController scrimController = this.mScrimController;
        scrimController.mDarkenWhileDragging = !((KeyguardStateControllerImpl) scrimController.mKeyguardStateController).mCanDismissLockScreen;
        if (!scrimController.mKeyguardUnlockAnimationController.playingCannedUnlockAnimation) {
            scrimController.mAnimatingPanelExpansionOnUnlock = false;
        }
        QuickSettingsController quickSettingsController = this.mQsController;
        if (quickSettingsController.mFullyExpanded) {
            quickSettingsController.setExpandImmediate(true);
            setShowShelfOnly(true);
        }
        int i = this.mBarState;
        if (i == 1 || i == 2) {
            KeyguardSecAffordanceHelper keyguardSecAffordanceHelper = this.mSecAffordanceHelper;
            KeyguardSecAffordanceHelper.updateIcon(keyguardSecAffordanceHelper.mRightIcon, 0.0f, true, false);
            KeyguardSecAffordanceHelper.updateIcon(keyguardSecAffordanceHelper.mLeftIcon, 0.0f, true, false);
        }
        NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.mPanelTracking = true;
        notificationStackScrollLayout.mAmbientState.mPanelTracking = true;
        notificationStackScrollLayout.mSwipeHelper.resetExposedMenuView(true, true);
        cancelPendingCollapse(false);
    }

    public final void onTrackingStopped(boolean z) {
        int i;
        StringBuilder sb = this.mLogBuilder;
        sb.setLength(0);
        sb.append("onTrackingStopped: ");
        sb.append("expand: ");
        sb.append(z);
        sb.append(", mTracking: ");
        sb.append(this.mTracking);
        sb.append(" -> false :");
        sb.append("\n");
        sb.append(Debug.getCallers(5, " - "));
        ((SecPanelLoggerImpl) this.mPanelLogger).addPanelStateInfoLog(sb, false);
        this.mTouchDownOnHeadsUpPinnded = false;
        this.mFalsingCollector.getClass();
        this.mTracking = false;
        updateExpansionAndVisibility();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        if (z) {
            notificationStackScrollLayoutController.mView.setOverScrollAmount(0.0f, true, true, true);
        }
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.mPanelTracking = false;
        notificationStackScrollLayout.mAmbientState.mPanelTracking = false;
        if (z && (((i = this.mBarState) == 1 || i == 2) && !this.mHintAnimationRunning)) {
            this.mSecAffordanceHelper.reset();
        }
        NotificationShadeDepthController notificationShadeDepthController = this.mDepthController;
        if (notificationShadeDepthController.blursDisabledForUnlock) {
            notificationShadeDepthController.blursDisabledForUnlock = false;
            notificationShadeDepthController.scheduleUpdate();
        }
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
    public final Bundle onUiInfoRequested(boolean z) {
        Bundle onUiInfoRequested = this.mKeyguardBottomAreaViewController.onUiInfoRequested(z);
        NotificationPanelView notificationPanelView = this.mView;
        int i = Settings.System.getInt(notificationPanelView.getContext().getContentResolver(), "lockscreen_minimizing_notification", 1);
        onUiInfoRequested.putInt("noti_type", i);
        onUiInfoRequested.putInt("noti_visibility", Settings.Secure.getInt(notificationPanelView.getContext().getContentResolver(), "lock_screen_show_notifications", 1));
        onUiInfoRequested.putInt("noti_top", getNotificationTopMargin(z));
        if (i == 0) {
            onUiInfoRequested.putInt("noti_number", computeMaxKeyguardNotifications());
        } else {
            int notificationTopMargin = getNotificationTopMargin(z);
            Resources resources = this.mResources;
            onUiInfoRequested.putInt("noti_bottom", notificationTopMargin + (z ? resources.getDimensionPixelSize(R.dimen.keyguard_indication_dls_default_notification_height_land) : resources.getDimensionPixelSize(R.dimen.keyguard_indication_dls_default_notification_height)));
        }
        Log.d("NotificationPanelView", "onUiInfoRequested bottom: " + onUiInfoRequested.toString());
        return onUiInfoRequested;
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
    public final void onUnNeedLockAppStarted(ComponentName componentName) {
        KeyguardBottomAreaViewController keyguardBottomAreaViewController = this.mKeyguardBottomAreaViewController;
        if (keyguardBottomAreaViewController != null) {
            keyguardBottomAreaViewController.launchApp(componentName);
        }
    }

    public void onUnlockHintFinished() {
        this.mKeyguardIndicationController.hideTransientIndicationDelayed(1200L);
        this.mScrimController.mExpansionAffectsAlpha = true;
        NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.mAmbientState.mUnlockHintRunning = false;
        notificationStackScrollLayout.updateStackPosition(false);
    }

    public void onUnlockHintStarted() {
        this.mFalsingCollector.getClass();
        this.mKeyguardIndicationController.showActionToUnlock();
        this.mScrimController.mExpansionAffectsAlpha = false;
        this.mNotificationStackScrollLayoutController.mView.mAmbientState.mUnlockHintRunning = true;
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
    public final void onUserActivity() {
        ((CentralSurfacesImpl) this.mCentralSurfaces).userActivity();
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
    public final void onViewModeChanged(int i) {
        LogUtil.m223d("NotificationPanelView", AbstractC0000x2c234b15.m0m("onViewModeChanged : ", i), new Object[0]);
        setViewMode(i);
        this.mNotificationStackScrollLayoutController.mView.mSpeedBumpIndexDirty = true;
    }

    public final void positionClockAndNotifications(boolean z) {
        int i;
        PluginNotificationController pluginNotificationController;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        boolean isAddOrRemoveAnimationPending = notificationStackScrollLayoutController.isAddOrRemoveAnimationPending();
        boolean isOnKeyguard = isOnKeyguard();
        if (isOnKeyguard || z) {
            updateClockAppearance();
        }
        boolean z2 = false;
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        if (isOnKeyguard) {
            boolean z3 = LsRune.KEYGUARD_DCM_LIVE_UX;
            KeyguardClockPositionAlgorithm.Result result = this.mClockPositionResult;
            i = z3 ? result.stackScrollerPadding : result.stackScrollerPaddingExpanded;
            if (z3) {
                i += this.mMascotViewContainer.updatePosition(i, lockscreenNotificationIconsOnlyController != null ? lockscreenNotificationIconsOnlyController.getNotificationIconsOnlyContainerHeight() : 0);
            }
        } else if (this.mSplitShadeEnabled) {
            i = 0;
        } else {
            QuickSettingsController quickSettingsController = this.mQsController;
            InterfaceC1922QS interfaceC1922QS = quickSettingsController.mQs;
            i = (interfaceC1922QS != null ? interfaceC1922QS.getHeader().getHeight() : 0) + quickSettingsController.mPeekHeight;
        }
        notificationStackScrollLayoutController.mView.mIntrinsicPadding = i;
        int i2 = this.mStackScrollerMeasuringPass + 1;
        this.mStackScrollerMeasuringPass = i2;
        if (i2 > 2) {
            RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("increased StackScrollerMeasuringPass : "), this.mStackScrollerMeasuringPass, "NotificationPanelView");
        }
        requestScrollerTopPaddingUpdate(isAddOrRemoveAnimationPending);
        if (this.mStackScrollerMeasuringPass > 2) {
            RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("reset StackScrollerMeasuringPass from "), this.mStackScrollerMeasuringPass, "NotificationPanelView");
        }
        this.mStackScrollerMeasuringPass = 0;
        this.mAnimateNextPositionUpdate = false;
        if (lockscreenNotificationIconsOnlyController != null) {
            if (!isOnAod()) {
                LockscreenNotificationManager lockscreenNotificationManager = this.mLockscreenNotificationManager;
                int i3 = lockscreenNotificationManager.mCurrentNotificationType;
                if (!(i3 == 1 || i3 == 2)) {
                    if (i3 == 0 && lockscreenNotificationManager.mSettingNotificationType == 1) {
                        z2 = true;
                    }
                    if (!z2) {
                        return;
                    }
                }
            }
            FaceWidgetNotificationController faceWidgetNotificationController = lockscreenNotificationIconsOnlyController.mFaceWidgetNotificationController;
            if (faceWidgetNotificationController == null || (pluginNotificationController = ((FaceWidgetNotificationControllerWrapper) faceWidgetNotificationController).mNotificationController) == null) {
                return;
            }
            pluginNotificationController.updateNotificationIconsOnlyPosition();
        }
    }

    public final View reInflateStub(int i, int i2, int i3, boolean z) {
        View view;
        NotificationPanelView notificationPanelView = this.mView;
        View findViewById = notificationPanelView.findViewById(i);
        if (findViewById == null) {
            return z ? ((ViewStub) notificationPanelView.findViewById(i2)).inflate() : findViewById;
        }
        int indexOfChild = notificationPanelView.indexOfChild(findViewById);
        notificationPanelView.removeView(findViewById);
        if (z) {
            view = this.mLayoutInflater.inflate(i3, (ViewGroup) notificationPanelView, false);
            notificationPanelView.addView(view, indexOfChild);
        } else {
            ViewStub viewStub = new ViewStub(notificationPanelView.getContext(), i3);
            viewStub.setId(i2);
            notificationPanelView.addView(viewStub, indexOfChild);
            view = null;
        }
        return view;
    }

    public void reInflateViews() {
        updateUserSwitcherFlags();
        boolean isUserSwitcherEnabled = this.mUserManager.isUserSwitcherEnabled(this.mResources.getBoolean(R.bool.qs_show_user_switcher_for_single_user));
        boolean z = this.mKeyguardQsUserSwitchEnabled;
        int i = 1;
        int i2 = 0;
        boolean z2 = z && isUserSwitcherEnabled;
        boolean z3 = !z && this.mKeyguardUserSwitcherEnabled && isUserSwitcherEnabled;
        FrameLayout frameLayout = (FrameLayout) reInflateStub(R.id.keyguard_qs_user_switch_view, R.id.keyguard_qs_user_switch_stub, R.layout.keyguard_qs_user_switch, z2);
        KeyguardUserSwitcherView keyguardUserSwitcherView = (KeyguardUserSwitcherView) reInflateStub(R.id.keyguard_user_switcher_view, R.id.keyguard_user_switcher_stub, R.layout.keyguard_user_switcher, z3);
        if (LsRune.LOCKUI_MULTI_USER) {
            updateUserSwitcherViewControllers(frameLayout, keyguardUserSwitcherView);
        }
        initBottomArea();
        ViewGroup viewGroup = (ViewGroup) this.mKeyguardBottomArea.findViewById(R.id.keyguard_indication_area);
        KeyguardIndicationController keyguardIndicationController = this.mKeyguardIndicationController;
        keyguardIndicationController.setIndicationArea(viewGroup);
        keyguardIndicationController.setUpperTextView((KeyguardIndicationTextView) this.mKeyguardBottomArea.findViewById(R.id.keyguard_upper_fingerprint_indication));
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) this.mStatusBarStateController;
        float f = statusBarStateControllerImpl.mDozeAmount;
        this.mStatusBarStateListener.onDozeAmountChanged(f, statusBarStateControllerImpl.mDozeInterpolator.getInterpolation(f));
        KeyguardStatusViewController keyguardStatusViewController = this.mKeyguardStatusViewController;
        int i3 = this.mBarState;
        keyguardStatusViewController.setKeyguardStatusViewVisibility(i3, i3, false, false);
        KeyguardQsUserSwitchController keyguardQsUserSwitchController = this.mKeyguardQsUserSwitchController;
        if (keyguardQsUserSwitchController != null) {
            int i4 = this.mBarState;
            keyguardQsUserSwitchController.mKeyguardVisibilityHelper.setViewVisibility(i4, i4, false, false);
        }
        KeyguardUserSwitcherController keyguardUserSwitcherController = this.mKeyguardUserSwitcherController;
        if (keyguardUserSwitcherController != null) {
            int i5 = this.mBarState;
            keyguardUserSwitcherController.mKeyguardVisibilityHelper.setViewVisibility(i5, i5, false, false);
        }
        setKeyguardBottomAreaVisibility(this.mBarState, false);
        this.mKeyguardUnfoldTransition.ifPresent(new NotificationPanelViewController$$ExternalSyntheticLambda1(this, i2));
        this.mNotificationPanelUnfoldAnimationController.ifPresent(new NotificationPanelViewController$$ExternalSyntheticLambda1(this, i));
    }

    public final void requestScrollerTopPaddingUpdate(boolean z) {
        int keyguardNotificationStaticPadding = getKeyguardNotificationStaticPadding();
        QuickSettingsController quickSettingsController = this.mQsController;
        int calculateNotificationsTopPadding = (int) quickSettingsController.calculateNotificationsTopPadding(keyguardNotificationStaticPadding);
        NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScrollLayoutController.mView;
        if (notificationStackScrollLayout.getLayoutMinHeight() + calculateNotificationsTopPadding > notificationStackScrollLayout.getHeight()) {
            notificationStackScrollLayout.mTopPaddingOverflow = r3 - notificationStackScrollLayout.getHeight();
        } else {
            notificationStackScrollLayout.mTopPaddingOverflow = 0.0f;
        }
        boolean z2 = z && !notificationStackScrollLayout.mKeyguardBypassEnabled;
        if (notificationStackScrollLayout.mTopPadding != calculateNotificationsTopPadding) {
            boolean z3 = z2 || notificationStackScrollLayout.mAnimateNextTopPaddingChange;
            notificationStackScrollLayout.mTopPadding = calculateNotificationsTopPadding;
            if (NotiRune.NOTI_STYLE_EMPTY_SHADE && notificationStackScrollLayout.mEmptyShadeView.mIsVisible) {
                notificationStackScrollLayout.updateEmptyShadeViewHeight();
                Log.d("StackScroller", "updateEmptyShadeViewHeight");
            }
            notificationStackScrollLayout.updateAlgorithmHeightAndPadding();
            notificationStackScrollLayout.updateContentHeight();
            if (z3 && notificationStackScrollLayout.mAnimationsEnabled && notificationStackScrollLayout.mIsExpanded) {
                notificationStackScrollLayout.mTopPaddingNeedsAnimation = true;
                notificationStackScrollLayout.mNeedsAnimation = true;
            }
            notificationStackScrollLayout.updateStackPosition(false);
            notificationStackScrollLayout.requestChildrenUpdate();
            notificationStackScrollLayout.notifyHeightChangeListener(null, z3);
            notificationStackScrollLayout.mAnimateNextTopPaddingChange = false;
            int i = notificationStackScrollLayout.mOwnScrollY;
            if (i > 0 && i < notificationStackScrollLayout.getScrollAmountToScrollBoundary() && notificationStackScrollLayout.mIsChangedOrientation) {
                notificationStackScrollLayout.setOwnScrollY(notificationStackScrollLayout.getScrollAmountToScrollBoundary());
                notificationStackScrollLayout.mIsChangedOrientation = false;
            }
        }
        notificationStackScrollLayout.setExpandedHeight(notificationStackScrollLayout.mExpandedHeight);
        if (isKeyguardShowing() && this.mKeyguardBypassController.getBypassEnabled()) {
            quickSettingsController.updateExpansion();
        }
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
    public final void resetDynamicLock() {
        Log.d("NotificationPanelView", "resetDynamicLock()");
        if (!this.mUpdateMonitor.isKeyguardVisible()) {
            this.mKeyguardBottomArea.setVisibility(8);
        }
        this.mKeyguardIndicationController.setVisible(this.mBarState == 1);
    }

    public final void resetViewGroupFade() {
        ViewGroupFadeHelper.Companion.getClass();
        NotificationPanelView notificationPanelView = this.mView;
        Object tag = notificationPanelView.getTag(R.id.view_group_fade_helper_modified_views);
        if ((tag instanceof KMappedMarker) && !(tag instanceof KMutableSet)) {
            TypeIntrinsics.throwCce(tag, "kotlin.collections.MutableSet");
            throw null;
        }
        try {
            Set<View> set = (Set) tag;
            Animator animator = (Animator) notificationPanelView.getTag(R.id.view_group_fade_helper_animator);
            if (set == null || animator == null) {
                return;
            }
            animator.cancel();
            Float f = (Float) notificationPanelView.getTag(R.id.view_group_fade_helper_previous_value_tag);
            for (View view : set) {
                Float f2 = (Float) view.getTag(R.id.view_group_fade_helper_restore_tag);
                if (f2 != null) {
                    if (f != null && f.floatValue() == view.getAlpha()) {
                        view.setAlpha(f2.floatValue());
                    }
                    if (Intrinsics.areEqual((Boolean) view.getTag(R.id.view_group_fade_helper_hardware_layer), Boolean.TRUE)) {
                        view.setLayerType(0, null);
                        view.setTag(R.id.view_group_fade_helper_hardware_layer, null);
                    }
                    view.setTag(R.id.view_group_fade_helper_restore_tag, null);
                }
            }
            notificationPanelView.setTag(R.id.view_group_fade_helper_modified_views, null);
            notificationPanelView.setTag(R.id.view_group_fade_helper_previous_value_tag, null);
            notificationPanelView.setTag(R.id.view_group_fade_helper_animator, null);
        } catch (ClassCastException e) {
            Intrinsics.sanitizeStackTrace(TypeIntrinsics.class.getName(), e);
            throw e;
        }
    }

    public final void resetViews(boolean z) {
        this.mIsLaunchTransitionFinished = false;
        if (!this.mLaunchingAffordance) {
            this.mSecAffordanceHelper.reset();
            this.mLastCameraLaunchSource = 3;
        }
        KeyguardSecAffordanceHelper keyguardSecAffordanceHelper = this.mSecAffordanceHelper;
        if (keyguardSecAffordanceHelper.isShortcutPreviewSwipingInProgress) {
            keyguardSecAffordanceHelper.reset();
        }
        this.mGutsManager.closeAndSaveGuts(true, true, true, true);
        if (!z || isFullyCollapsed()) {
            closeQsIfPossible();
        } else {
            animateCollapseQs(true);
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        notificationStackScrollLayoutController.mView.setOverScrollAmount(0.0f, true, z, !z);
        notificationStackScrollLayoutController.mView.resetScrollPosition();
        KeyguardTouchAnimator keyguardTouchAnimator = this.mKeyguardTouchAnimator;
        keyguardTouchAnimator.setIntercept(false);
        keyguardTouchAnimator.reset();
        this.mHintAnimationRunning = false;
        this.mSecAffordanceHelper.isShortcutPreviewSwipingInProgress = false;
    }

    public final void setAlpha(int i, boolean z) {
        if (this.mPanelAlpha != i) {
            this.mPanelAlpha = i;
            PropertyAnimator.setProperty(this.mView, this.mPanelAlphaAnimator, i, i == 255 ? this.mPanelAlphaInPropertiesAnimator : this.mPanelAlphaOutPropertiesAnimator, z);
        }
    }

    public final void setAnimator(ValueAnimator valueAnimator) {
        this.mHeightAnimator = valueAnimator;
        if (valueAnimator == null && (this.mDetailViewCollapseAnimating || this.mQsExpandedViewCollapseAnimating)) {
            this.mDetailViewCollapseAnimating = false;
            this.mQsExpandedViewCollapseAnimating = false;
            setMotionAborted();
        }
        if (valueAnimator == null && this.mPanelUpdateWhenAnimatorEnds) {
            this.mPanelUpdateWhenAnimatorEnds = false;
            updateExpandedHeightToMaxHeight();
        }
    }

    public void setClosing(boolean z) {
        if (this.mClosing != z) {
            this.mClosing = z;
            Iterator it = this.mShadeExpansionStateManager.shadeStateEventsListeners.iterator();
            while (it.hasNext()) {
                ((ShadeStateEvents.ShadeStateEventsListener) it.next()).onPanelCollapsingChanged(z);
            }
        }
        this.mAmbientState.mIsClosing = z;
    }

    public final void setDozing(boolean z, boolean z2) {
        if (z == this.mDozing) {
            return;
        }
        NotificationPanelView notificationPanelView = this.mView;
        notificationPanelView.mDozing = z;
        this.mDozing = z;
        NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScrollLayoutController.mView;
        AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
        if (ambientState.mDozing != z) {
            ambientState.mDozing = z;
            notificationStackScrollLayout.requestChildrenUpdate();
            notificationStackScrollLayout.notifyHeightChangeListener(notificationStackScrollLayout.mShelf, false);
        }
        ((KeyguardRepositoryImpl) this.mKeyguardBottomAreaInteractor.repository)._animateBottomAreaDozingTransitions.setValue(Boolean.valueOf(z2));
        this.mKeyguardStatusBarViewController.mDozing = this.mDozing;
        this.mKeyguardBottomAreaViewController.setDozing(z);
        this.mQsController.mDozing = this.mDozing;
        if (z) {
            this.mBottomAreaShadeAlphaAnimator.cancel();
        }
        int i = this.mBarState;
        if (i == 1 || i == 2) {
            updateDozingVisibilities(z2);
        }
        float f = z ? 1.0f : 0.0f;
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) this.mStatusBarStateController;
        ValueAnimator valueAnimator = statusBarStateControllerImpl.mDarkAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            if (!z2 || statusBarStateControllerImpl.mDozeAmountTarget != f) {
                statusBarStateControllerImpl.mDarkAnimator.cancel();
            }
            updateKeyguardStatusViewAlignment(z2);
        }
        View view = statusBarStateControllerImpl.mView;
        if ((view == null || !view.isAttachedToWindow()) && notificationPanelView.isAttachedToWindow()) {
            statusBarStateControllerImpl.mView = notificationPanelView;
        }
        statusBarStateControllerImpl.mDozeAmountTarget = f;
        if (z2) {
            float f2 = statusBarStateControllerImpl.mDozeAmount;
            if (f2 == 0.0f || f2 == 1.0f) {
                statusBarStateControllerImpl.mDozeInterpolator = statusBarStateControllerImpl.mIsDozing ? Interpolators.FAST_OUT_SLOW_IN : Interpolators.TOUCH_RESPONSE_REVERSE;
            }
            if (f2 == 1.0f && !statusBarStateControllerImpl.mIsDozing) {
                statusBarStateControllerImpl.setDozeAmountInternal(0.99f);
            }
            statusBarStateControllerImpl.mDarkAnimator = statusBarStateControllerImpl.createDarkAnimator();
        } else {
            statusBarStateControllerImpl.setDozeAmountInternal(f);
        }
        updateKeyguardStatusViewAlignment(z2);
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
    public final void setDynamicLockData(String str) {
        if (str == null || str.isEmpty()) {
            return;
        }
        DynamicLockData fromJSon = DynamicLockData.fromJSon(str);
        if (fromJSon != null) {
            this.mNotiCardCount = fromJSon.getNotificationData().getCardData().getNotiCardNumbers().intValue();
        }
        LogUtil.m223d("NotificationPanelView", "setDynamicLockData card numbers: " + this.mNotiCardCount, new Object[0]);
    }

    public final void setExpandSettingsPanel(boolean z) {
        CentralSurfaces centralSurfaces = this.mCentralSurfaces;
        if (z != ((CentralSurfacesImpl) centralSurfaces).mQSPanelController.mExpandSettingsPanel) {
            ((CentralSurfacesImpl) centralSurfaces).mQSPanelController.mExpandSettingsPanel = z;
        }
        if (z != ((CentralSurfacesImpl) centralSurfaces).mQuickQSPanelController.mExpandSettingsPanel) {
            ((CentralSurfacesImpl) centralSurfaces).mQuickQSPanelController.mExpandSettingsPanel = z;
        }
    }

    public void setExpandedFraction(float f) {
        setExpandedHeight(getMaxPanelTransitionDistance() * f);
    }

    public void setExpandedHeight(float f) {
        setExpandedHeightInternal(f);
    }

    public final void setExpandedHeightInternal(final float f) {
        if (Float.isNaN(f)) {
            Log.wtf("NotificationPanelView", "ExpandedHeight set to NaN");
        }
        ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).batchApplyWindowLayoutParams(new Runnable() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda14
            /* JADX WARN: Code restructure failed: missing block: B:53:0x0159, code lost:
            
                if (r10.mExpansionFromOverscroll == false) goto L71;
             */
            /* JADX WARN: Removed duplicated region for block: B:66:0x017a  */
            /* JADX WARN: Removed duplicated region for block: B:68:0x017c  */
            /* JADX WARN: Removed duplicated region for block: B:74:0x01b5  */
            /* JADX WARN: Removed duplicated region for block: B:89:0x01fb  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void run() {
                boolean z;
                boolean z2;
                float calculatePanelHeightExpanded;
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                float f2 = f;
                if (notificationPanelViewController.mExpandLatencyTracking && f2 != 0.0f) {
                    DejankUtils.postAfterTraversal(new NotificationPanelViewController$$ExternalSyntheticLambda0(notificationPanelViewController, 12));
                    notificationPanelViewController.mExpandLatencyTracking = false;
                }
                float maxPanelTransitionDistance = notificationPanelViewController.getMaxPanelTransitionDistance();
                if (notificationPanelViewController.mHeightAnimator == null && notificationPanelViewController.mTracking) {
                    notificationPanelViewController.setOverExpansionInternal(Math.max(0.0f, f2 - maxPanelTransitionDistance), true);
                }
                float f3 = notificationPanelViewController.mExpandedFraction;
                float min = Math.min(f2, maxPanelTransitionDistance);
                notificationPanelViewController.mExpandedHeight = min;
                if (min < 1.0f && min != 0.0f && notificationPanelViewController.mClosing) {
                    notificationPanelViewController.mExpandedHeight = 0.0f;
                    ValueAnimator valueAnimator = notificationPanelViewController.mHeightAnimator;
                    if (valueAnimator != null) {
                        valueAnimator.end();
                    }
                }
                float min2 = Math.min(1.0f, maxPanelTransitionDistance == 0.0f ? 0.0f : notificationPanelViewController.mExpandedHeight / maxPanelTransitionDistance);
                notificationPanelViewController.mExpandedFraction = min2;
                float f4 = notificationPanelViewController.mExpandedHeight;
                QuickSettingsController quickSettingsController = notificationPanelViewController.mQsController;
                quickSettingsController.mShadeExpandedHeight = f4;
                quickSettingsController.mShadeExpandedFraction = min2;
                notificationPanelViewController.mExpansionDragDownAmountPx = f2;
                notificationPanelViewController.mAmbientState.mExpansionFraction = min2;
                if ((Float.compare(f3, 0.0f) == 0 && Float.compare(notificationPanelViewController.mExpandedFraction, 0.0f) > 0) || (Float.compare(f3, 0.0f) > 0 && Float.compare(notificationPanelViewController.mExpandedFraction, 0.0f) == 0)) {
                    StringBuilder sb = notificationPanelViewController.mLogBuilder;
                    sb.setLength(0);
                    sb.append("setExpandedHeightInternal");
                    sb.append("(h:");
                    sb.append(f2);
                    sb.append(")");
                    sb.append(", mHeightAnimator is null?");
                    sb.append(notificationPanelViewController.mHeightAnimator == null);
                    sb.append(", mTracking:");
                    sb.append(notificationPanelViewController.mTracking);
                    sb.append(", mExpandedHeight:");
                    sb.append(notificationPanelViewController.mExpandedHeight);
                    sb.append(", maxPanelHeight:");
                    sb.append(maxPanelTransitionDistance);
                    sb.append(", mClosing:");
                    sb.append(notificationPanelViewController.mClosing);
                    sb.append(", mExpandedFraction(");
                    sb.append(f3);
                    sb.append(" >> ");
                    sb.append(notificationPanelViewController.mExpandedFraction);
                    sb.append(")");
                    ((SecPanelLoggerImpl) notificationPanelViewController.mPanelLogger).addPanelStateInfoLog(sb, false);
                }
                float f5 = notificationPanelViewController.mExpandedHeight;
                if (f5 <= 0.0f) {
                    notificationPanelViewController.mShadeLog.logExpansionChanged("onHeightUpdated: fully collapsed.", notificationPanelViewController.mExpandedFraction, notificationPanelViewController.isExpanded(), notificationPanelViewController.mTracking, notificationPanelViewController.mExpansionDragDownAmountPx);
                } else if (notificationPanelViewController.isFullyExpanded()) {
                    notificationPanelViewController.mShadeLog.logExpansionChanged("onHeightUpdated: fully expanded.", notificationPanelViewController.mExpandedFraction, notificationPanelViewController.isExpanded(), notificationPanelViewController.mTracking, notificationPanelViewController.mExpansionDragDownAmountPx);
                }
                if ((!quickSettingsController.mExpanded || quickSettingsController.isExpandImmediate() || (notificationPanelViewController.mIsExpandingOrCollapsing && quickSettingsController.mExpandedWhenExpandingStarted)) && notificationPanelViewController.mStackScrollerMeasuringPass <= 2) {
                    notificationPanelViewController.positionClockAndNotifications(false);
                }
                if (!quickSettingsController.mExpandImmediate) {
                    if (quickSettingsController.mExpanded && !quickSettingsController.mTracking) {
                        if (!(quickSettingsController.mExpansionAnimator != null)) {
                        }
                    }
                    z = false;
                    z2 = notificationPanelViewController.mSplitShadeEnabled;
                    boolean z3 = z2 || !notificationPanelViewController.mHeadsUpManager.mTrackingHeadsUp || f5 > ((float) notificationPanelViewController.mHeadsUpStartHeight);
                    if (z && z3) {
                        if (!z2) {
                            calculatePanelHeightExpanded = 1.0f;
                        } else if (notificationPanelViewController.isKeyguardShowing()) {
                            calculatePanelHeightExpanded = f5 / notificationPanelViewController.getMaxPanelHeight();
                        } else {
                            NotificationStackScrollLayout notificationStackScrollLayout = notificationPanelViewController.mNotificationStackScrollLayoutController.mView;
                            float layoutMinHeight = notificationStackScrollLayout.getLayoutMinHeight() + notificationStackScrollLayout.mIntrinsicPadding;
                            calculatePanelHeightExpanded = (f5 - layoutMinHeight) / (quickSettingsController.calculatePanelHeightExpanded(notificationPanelViewController.mClockPositionResult.stackScrollerPadding) - layoutMinHeight);
                        }
                        quickSettingsController.setExpansionHeight((calculatePanelHeightExpanded * (quickSettingsController.mMaxExpansionHeight - r5)) + quickSettingsController.mMinExpansionHeight);
                    }
                    if (QpRune.PANEL_DATA_USAGE_LABEL) {
                        DataUsageLabelManager dataUsageLabelManager = (DataUsageLabelManager) notificationPanelViewController.mDataUsageLabelManagerLazy.get();
                        DataUsageLabelParent dataUsageLabelParent = dataUsageLabelManager.mDataUsageLabelParent;
                        float min3 = Math.min(1.0f, f5 / dataUsageLabelParent.mMaxPanelHeightSupplier.getAsInt());
                        ViewGroup parentViewGroup = dataUsageLabelParent.getParentViewGroup();
                        if (Float.compare(min3, 1.0f) == 0 && !dataUsageLabelManager.mLabelAlphaAnimStarted) {
                            dataUsageLabelManager.mLabelAlphaAnimStarted = true;
                            dataUsageLabelManager.animateLabelAlpha(parentViewGroup, true);
                        } else if (min3 < 1.0f && dataUsageLabelManager.mLabelAlphaAnimStarted) {
                            dataUsageLabelManager.mLabelAlphaAnimStarted = false;
                            dataUsageLabelManager.animateLabelAlpha(parentViewGroup, false);
                        } else if (min3 == 0.0f) {
                            dataUsageLabelManager.mLabelAlphaAnimStarted = false;
                        }
                    }
                    notificationPanelViewController.updateExpandedHeight(f5);
                    if (notificationPanelViewController.mBarState == 1) {
                        notificationPanelViewController.mKeyguardStatusBarViewController.updateViewState();
                    }
                    quickSettingsController.updateExpansion();
                    notificationPanelViewController.updateNotificationTranslucency();
                    notificationPanelViewController.updatePanelExpanded();
                    notificationPanelViewController.updateGestureExclusionRect();
                    notificationPanelViewController.updateExpansionAndVisibility();
                }
                z = true;
                z2 = notificationPanelViewController.mSplitShadeEnabled;
                if (z2) {
                }
                if (z) {
                    if (!z2) {
                    }
                    quickSettingsController.setExpansionHeight((calculatePanelHeightExpanded * (quickSettingsController.mMaxExpansionHeight - r5)) + quickSettingsController.mMinExpansionHeight);
                }
                if (QpRune.PANEL_DATA_USAGE_LABEL) {
                }
                notificationPanelViewController.updateExpandedHeight(f5);
                if (notificationPanelViewController.mBarState == 1) {
                }
                quickSettingsController.updateExpansion();
                notificationPanelViewController.updateNotificationTranslucency();
                notificationPanelViewController.updatePanelExpanded();
                notificationPanelViewController.updateGestureExclusionRect();
                notificationPanelViewController.updateExpansionAndVisibility();
            }
        });
    }

    public void setHeadsUpDraggingStartingHeight(int i) {
        float maxPanelHeight;
        float f;
        int i2;
        this.mHeadsUpStartHeight = i;
        if (!this.mSplitShadeEnabled) {
            maxPanelHeight = getMaxPanelHeight();
            f = 0.0f;
            if (maxPanelHeight > 0.0f) {
                i2 = this.mHeadsUpStartHeight;
            }
            setPanelScrimMinFraction(f);
        }
        maxPanelHeight = ((((double) i) * 2.5d) > ((double) (((FeatureFlagsRelease) this.mFeatureFlags).isEnabled(Flags.LARGE_SHADE_GRANULAR_ALPHA_INTERPOLATION) ? this.mSplitShadeFullTransitionDistance : this.mSplitShadeScrimTransitionDistance)) ? 1 : ((((double) i) * 2.5d) == ((double) (((FeatureFlagsRelease) this.mFeatureFlags).isEnabled(Flags.LARGE_SHADE_GRANULAR_ALPHA_INTERPOLATION) ? this.mSplitShadeFullTransitionDistance : this.mSplitShadeScrimTransitionDistance)) ? 0 : -1)) > 0 ? getMaxPanelTransitionDistance() : this.mSplitShadeFullTransitionDistance;
        i2 = this.mHeadsUpStartHeight;
        f = i2 / maxPanelHeight;
        setPanelScrimMinFraction(f);
    }

    public final void setIsLaunchAnimationRunning(boolean z) {
        boolean z2 = this.mIsLaunchAnimationRunning;
        this.mIsLaunchAnimationRunning = z;
        if (z2 != z) {
            Iterator it = this.mShadeExpansionStateManager.shadeStateEventsListeners.iterator();
            while (it.hasNext()) {
                ((ShadeStateEvents.ShadeStateEventsListener) it.next()).onLaunchingActivityChanged(z);
            }
        }
    }

    public final void setKeyguardBottomAreaVisibility(int i, boolean z) {
        this.mKeyguardBottomArea.animate().cancel();
        if (z) {
            ViewPropertyAnimator alpha = this.mKeyguardBottomArea.animate().alpha(0.0f);
            KeyguardStateControllerImpl keyguardStateControllerImpl = this.mKeyguardStateController;
            ViewPropertyAnimator startDelay = alpha.setStartDelay(keyguardStateControllerImpl.mKeyguardFadingAwayDelay);
            keyguardStateControllerImpl.getClass();
            startDelay.setDuration(keyguardStateControllerImpl.mKeyguardFadingAwayDuration / 2).setInterpolator(Interpolators.ALPHA_OUT).withEndAction(this.mAnimateKeyguardBottomAreaInvisibleEndRunnable).start();
            return;
        }
        if (i != 1) {
            this.mKeyguardBottomArea.animate().cancel();
            this.mKeyguardBottomArea.setVisibility(8);
            return;
        }
        this.mKeyguardBottomArea.animate().cancel();
        this.mKeyguardBottomArea.setVisibility(0);
        if (this.mIsOcclusionTransitionRunning) {
            return;
        }
        this.mKeyguardBottomArea.setAlpha(1.0f);
    }

    public final void setListening(boolean z) {
        KeyguardStatusBarViewController keyguardStatusBarViewController = this.mKeyguardStatusBarViewController;
        if (z != keyguardStatusBarViewController.mBatteryListening) {
            keyguardStatusBarViewController.mBatteryListening = z;
            KeyguardStatusBarViewController.C30603 c30603 = keyguardStatusBarViewController.mBatteryStateChangeCallback;
            BatteryController batteryController = keyguardStatusBarViewController.mBatteryController;
            if (z) {
                ((BatteryControllerImpl) batteryController).addCallback(c30603);
            } else {
                ((BatteryControllerImpl) batteryController).removeCallback(c30603);
            }
        }
        InterfaceC1922QS interfaceC1922QS = this.mQsController.mQs;
        if (interfaceC1922QS != null) {
            interfaceC1922QS.setListening(z);
        }
    }

    public void setMaxDisplayedNotifications(int i) {
        this.mMaxAllowedKeyguardNotifications = i;
    }

    public final void setMotionAborted() {
        if (this.mMotionAborted) {
            return;
        }
        Log.d("NotificationPanelView", "setMotionAborted");
        this.mMotionAborted = true;
    }

    public final void setOverExpansionInternal(float f, boolean z) {
        if (!z) {
            this.mLastGesturedOverExpansion = -1.0f;
            setOverExpansion(f);
        } else if (this.mLastGesturedOverExpansion != f) {
            this.mLastGesturedOverExpansion = f;
            float exp = (float) (1.0d - Math.exp(MathUtils.saturate(f / (this.mView.getHeight() / 3.0f)) * (-4.0f)));
            if (0.0f > exp) {
                exp = 0.0f;
            }
            setOverExpansion(exp * this.mPanelFlingOvershootAmount * 2.0f);
        }
    }

    public final void setPanelScrimMinFraction(float f) {
        this.mMinFraction = f;
        this.mDepthController.panelPullDownMinFraction = f;
        ScrimController scrimController = this.mScrimController;
        scrimController.getClass();
        if (Float.isNaN(f)) {
            throw new IllegalArgumentException("minFraction should not be NaN");
        }
        scrimController.mPanelScrimMinFraction = f;
        scrimController.calculateAndUpdatePanelExpansion();
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
    public final void setPluginLock(PluginLock pluginLock) {
        Log.d("NotificationPanelView", "setPluginLock");
        this.mPluginLock = pluginLock;
    }

    public final void setShowShelfOnly(boolean z) {
        boolean z2 = z && !this.mSplitShadeEnabled;
        NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.mShouldShowShelfOnly = z2;
        notificationStackScrollLayout.updateAlgorithmLayoutMinHeight();
    }

    public void setTouchSlopExceeded(boolean z) {
        this.mTouchSlopExceeded = z;
    }

    public final void setViewMode(int i) {
        RecyclerView$$ExternalSyntheticOutline0.m46m(AbstractC0000x2c234b15.m1m("setViewMode, newMode:", i, ", oldMode:"), this.mPluginLockViewMode, "NotificationPanelView");
        this.mKeyguardBottomAreaViewController.onViewModeChanged(i);
        if (i != this.mPluginLockViewMode || i == 1) {
            this.mPluginLockViewMode = i;
            CentralSurfaces centralSurfaces = this.mCentralSurfaces;
            if (centralSurfaces != null) {
                boolean z = (i == 1) && i == 1;
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("setDlsOverLay() : ", z, "CentralSurfaces");
                ((CentralSurfacesImpl) centralSurfaces).mIsDlsOverlay = z;
            }
            final DcmMascotViewContainer dcmMascotViewContainer = this.mMascotViewContainer;
            NotificationPanelView notificationPanelView = this.mView;
            C243417 c243417 = this.mSystemUIWidgetCallback;
            if (i == 0) {
                CentralSurfaces centralSurfaces2 = this.mCentralSurfaces;
                if (((centralSurfaces2 != null && ((CentralSurfacesImpl) centralSurfaces2).mBouncerShowing) || notificationPanelView.getVisibility() == 0) && this.mBarState != 0) {
                    if (LsRune.KEYGUARD_DCM_LIVE_UX && dcmMascotViewContainer.getVisibility() != 0 && dcmMascotViewContainer.isMascotEnabled()) {
                        ((ExecutorImpl) dcmMascotViewContainer.mainExecutor).executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.DcmMascotViewContainer$updateDelayed$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                DcmMascotViewContainer.this.setMascotViewVisible(0);
                            }
                        }, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, TimeUnit.MILLISECONDS);
                    }
                    Log.d("NotificationPanelView", "setViewMode, removeSystemUIWidgetCallback:" + c243417);
                    WallpaperUtils.removeSystemUIWidgetCallback(c243417);
                }
            } else if (i == 1) {
                notificationPanelView.setClickable(false);
                if (LsRune.KEYGUARD_DCM_LIVE_UX) {
                    dcmMascotViewContainer.setMascotViewVisible(8);
                }
                WallpaperUtils.registerSystemUIWidgetCallback(c243417, SystemUIWidgetUtil.convertFlag("navibar"));
            }
            View view = this.mPluginLockStarContainer;
            if (view != null) {
                view.setVisibility(i == 0 ? 0 : 4);
            }
            if (!LsRune.WALLPAPER_BLUR) {
                SecLsScrimControlHelper secLsScrimControlHelper = this.mScrimController.mSecLsScrimControlHelper;
                boolean z2 = i == 1;
                if (secLsScrimControlHelper.mIsDLSOverlayView != z2) {
                    Log.d("ScrimController", "setDLSOverlayView(" + secLsScrimControlHelper.mIsDLSOverlayView + " -> " + z2 + ")");
                    secLsScrimControlHelper.mIsDLSOverlayView = z2;
                    secLsScrimControlHelper.mProvider.mUpdateScrimsRunnable.run();
                    secLsScrimControlHelper.setScrimAlphaForKeyguard(true);
                }
            }
            CentralSurfaces centralSurfaces3 = this.mCentralSurfaces;
            if (centralSurfaces3 != null) {
                ((CentralSurfacesImpl) centralSurfaces3).mStatusBarKeyguardViewManager.updateDlsNaviBarVisibility();
            }
            this.mUpdateMonitor.dispatchDlsViewMode(i);
        }
    }

    public final void showAodUi() {
        setDozing(true, false);
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) this.mStatusBarStateController;
        statusBarStateControllerImpl.recordHistoricalState(1, statusBarStateControllerImpl.mState, true);
        statusBarStateControllerImpl.updateUpcomingState(1);
        StatusBarStateListener statusBarStateListener = this.mStatusBarStateListener;
        statusBarStateListener.onStateChanged(1);
        statusBarStateListener.onDozeAmountChanged(1.0f, 1.0f);
        if (LsRune.AOD_FULLSCREEN) {
            Lazy lazy = this.mPluginAODManagerLazy;
            if (((PluginAODManager) lazy.get()).mDozeParameters.mControlScreenOffAnimation) {
                Log.i("NotificationPanelView", "showAodUi: setIsDozing set true");
                ((PluginAODManager) lazy.get()).setIsDozing(true, false);
            }
        }
        setExpandedFraction(1.0f);
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
    public final void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent) {
        Log.d("NotificationPanelView", "startPendingIntentDismissingKeyguard");
        ActivityStarter activityStarter = this.mActivityStarter;
        if (activityStarter != null) {
            activityStarter.startPendingIntentDismissingKeyguard(pendingIntent);
        }
    }

    public void startUnlockHintAnimation() {
        if (this.mPowerManager.isPowerSaveMode() || this.mAmbientState.mDozeAmount > 0.0f) {
            onUnlockHintStarted();
            onUnlockHintFinished();
            return;
        }
        if (this.mHeightAnimator != null || this.mTracking) {
            return;
        }
        notifyExpandingStarted();
        final NotificationPanelViewController$$ExternalSyntheticLambda0 notificationPanelViewController$$ExternalSyntheticLambda0 = new NotificationPanelViewController$$ExternalSyntheticLambda0(this, 10);
        ValueAnimator createHeightAnimator = createHeightAnimator(Math.max(0.0f, getMaxPanelHeight() - this.mHintDistance), 0.0f);
        createHeightAnimator.setDuration(250L);
        createHeightAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        createHeightAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.NotificationPanelViewController.14
            public boolean mCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                this.mCancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (this.mCancelled) {
                    NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                    VibrationEffect vibrationEffect = NotificationPanelViewController.ADDITIONAL_TAP_REQUIRED_VIBRATION_EFFECT;
                    notificationPanelViewController.setAnimator(null);
                    notificationPanelViewController$$ExternalSyntheticLambda0.run();
                    return;
                }
                final NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                final Runnable runnable = notificationPanelViewController$$ExternalSyntheticLambda0;
                VibrationEffect vibrationEffect2 = NotificationPanelViewController.ADDITIONAL_TAP_REQUIRED_VIBRATION_EFFECT;
                ValueAnimator createHeightAnimator2 = notificationPanelViewController2.createHeightAnimator(notificationPanelViewController2.getMaxPanelHeight(), 0.0f);
                createHeightAnimator2.setDuration(450L);
                createHeightAnimator2.setInterpolator(notificationPanelViewController2.mBounceInterpolator);
                createHeightAnimator2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.NotificationPanelViewController.15
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator2) {
                        NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
                        VibrationEffect vibrationEffect3 = NotificationPanelViewController.ADDITIONAL_TAP_REQUIRED_VIBRATION_EFFECT;
                        notificationPanelViewController3.setAnimator(null);
                        runnable.run();
                        NotificationPanelViewController.this.updateExpansionAndVisibility();
                    }
                });
                createHeightAnimator2.start();
                notificationPanelViewController2.setAnimator(createHeightAnimator2);
            }
        });
        createHeightAnimator.start();
        setAnimator(createHeightAnimator);
        for (final ViewPropertyAnimator viewPropertyAnimator : this.mKeyguardBottomArea.getBinding().getIndicationAreaAnimators()) {
            viewPropertyAnimator.translationY(-this.mHintDistance).setDuration(250L).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).withEndAction(new Runnable() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                    ViewPropertyAnimator viewPropertyAnimator2 = viewPropertyAnimator;
                    notificationPanelViewController.getClass();
                    viewPropertyAnimator2.translationY(0.0f).setDuration(450L).setInterpolator(notificationPanelViewController.mBounceInterpolator).start();
                }
            }).start();
        }
        onUnlockHintStarted();
        this.mHintAnimationRunning = true;
    }

    public final void transitionToExpandedShade(long j) {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.mGoToFullShadeNeedsAnimation = true;
        notificationStackScrollLayout.mGoToFullShadeDelay = j;
        notificationStackScrollLayout.mNeedsAnimation = true;
        notificationStackScrollLayout.requestChildrenUpdate();
        this.mView.requestLayout();
        this.mAnimateNextPositionUpdate = true;
    }

    public final void updateClock() {
        KeyguardUserSwitcherController keyguardUserSwitcherController;
        FaceWidgetContainerWrapper faceWidgetContainerWrapper;
        View view;
        if (this.mIsOcclusionTransitionRunning || this.mSecAffordanceHelper.isShortcutPreviewSwipingInProgress) {
            return;
        }
        KeyguardClockPositionAlgorithm.Result result = this.mClockPositionResult;
        float f = result.clockAlpha * this.mKeyguardOnlyContentAlpha;
        if (!this.mKeyguardTouchAnimator.isViRunning()) {
            float f2 = result.clockAlpha;
            if (f2 >= 0.0f) {
                KeyguardStatusViewController keyguardStatusViewController = this.mKeyguardStatusViewController;
                if (!keyguardStatusViewController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating && (faceWidgetContainerWrapper = keyguardStatusViewController.mKeyguardStatusBase) != null && (view = faceWidgetContainerWrapper.mFaceWidgetContainer) != null) {
                    view.setAlpha(f2);
                }
            }
        }
        KeyguardQsUserSwitchController keyguardQsUserSwitchController = this.mKeyguardQsUserSwitchController;
        if (keyguardQsUserSwitchController != null && !keyguardQsUserSwitchController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
            ((FrameLayout) keyguardQsUserSwitchController.mView).setAlpha(f);
        }
        if (LsRune.LOCKUI_MULTI_USER || (keyguardUserSwitcherController = this.mKeyguardUserSwitcherController) == null || keyguardUserSwitcherController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
            return;
        }
        ((KeyguardUserSwitcherView) keyguardUserSwitcherController.mView).setAlpha(f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0200  */
    /* JADX WARN: Type inference failed for: r3v6, types: [com.android.keyguard.KeyguardClockSwitchController] */
    /* JADX WARN: Type inference failed for: r6v2, types: [int] */
    /* JADX WARN: Type inference failed for: r6v42 */
    /* JADX WARN: Type inference failed for: r6v49 */
    /* JADX WARN: Type inference failed for: r6v58 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateClockAppearance() {
        boolean z;
        ?? r6;
        int i;
        int i2;
        int height;
        boolean isPositionSynchronized;
        PluginKeyguardStatusView pluginKeyguardStatusView;
        Rect rect;
        this.mKeyguardBypassController.getBypassEnabled();
        ScreenOffAnimationController screenOffAnimationController = this.mScreenOffAnimationController;
        List list = screenOffAnimationController.animations;
        int i3 = 0;
        if (!(list instanceof Collection) || !((ArrayList) list).isEmpty()) {
            Iterator it = ((ArrayList) list).iterator();
            while (it.hasNext()) {
                if (!((ScreenOffAnimation) it.next()).shouldAnimateClockChange()) {
                    z = false;
                    break;
                }
            }
        }
        z = true;
        KeyguardStatusViewController keyguardStatusViewController = this.mKeyguardStatusViewController;
        if (this.mSplitShadeEnabled) {
            if (!(this.mMediaDataManager.hasActiveMediaOrRecommendation() && !isOnAod())) {
                ClockController clockController = this.mKeyguardStatusViewController.mKeyguardClockSwitchController.mClockEventController.clock;
                if (!(clockController != null && clockController.getLargeClock().getConfig().getHasCustomWeatherDataDisplay()) || !hasVisibleNotifications() || !isOnAod()) {
                    r6 = 0;
                }
            }
            r6 = 1;
        } else {
            r6 = hasVisibleNotifications();
        }
        keyguardStatusViewController.mKeyguardClockSwitchController.displayClock(r6, z);
        updateKeyguardStatusViewAlignment(true);
        KeyguardQsUserSwitchController keyguardQsUserSwitchController = this.mKeyguardQsUserSwitchController;
        if (keyguardQsUserSwitchController != null) {
            keyguardQsUserSwitchController.getUserIconHeight();
        }
        KeyguardUserSwitcherController keyguardUserSwitcherController = this.mKeyguardUserSwitcherController;
        if (keyguardUserSwitcherController != null) {
            keyguardUserSwitcherController.mListView.getHeight();
        }
        screenOffAnimationController.shouldExpandNotifications();
        screenOffAnimationController.shouldExpandNotifications();
        if (this.mUpdateMonitor.isUdfpsEnrolled()) {
            AuthController authController = this.mAuthController;
            if (authController.getUdfpsLocation() != null) {
                int i4 = authController.getUdfpsLocation().y;
                if (authController.mUdfpsController != null && (rect = authController.mUdfpsBounds) != null) {
                    rect.height();
                }
            }
        }
        NotificationPanelView notificationPanelView = this.mView;
        int height2 = notificationPanelView.getHeight();
        SettingsHelper settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        boolean isShowNotificationOnKeyguard = settingsHelper.isShowNotificationOnKeyguard();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        if (isShowNotificationOnKeyguard) {
            if (!settingsHelper.isNotificationIconsOnlyOn()) {
                if (!(this.mIsKeyguardSupportLandscapePhone && notificationPanelView.getResources().getConfiguration().orientation == 2 && this.mBarState == 1)) {
                    i = settingsHelper.mItemLists.get("lockscreen_minimizing_notification").getIntValue() == 2 ? 2 : 0;
                    i2 = notificationStackScrollLayoutController.getNotGoneChildCount() > 0 ? (int) notificationStackScrollLayoutController.mView.mIntrinsicContentHeight : 0;
                }
            }
            LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
            if (lockscreenNotificationIconsOnlyController != null) {
                i2 = lockscreenNotificationIconsOnlyController.getNotificationIconsOnlyContainerHeight();
                i = 1;
            } else {
                i = 1;
                i2 = 0;
            }
        } else {
            i = 0;
            i2 = 0;
        }
        KeyguardClockPositionAlgorithm keyguardClockPositionAlgorithm = this.mClockPositionAlgorithm;
        int i5 = this.mStatusBarMinHeight;
        QuickSettingsController quickSettingsController = this.mQsController;
        float computeExpansionFraction = quickSettingsController.computeExpansionFraction();
        KeyguardStatusViewController keyguardStatusViewController2 = this.mKeyguardStatusViewController;
        FaceWidgetContainerWrapper faceWidgetContainerWrapper = keyguardStatusViewController2.mKeyguardStatusBase;
        if (faceWidgetContainerWrapper != null) {
            List<View> list2 = faceWidgetContainerWrapper.mContentsContainerList;
            if (list2 == null) {
                View view = faceWidgetContainerWrapper.mFaceWidgetContainer;
                height = view != null ? view.getHeight() : 0;
            } else {
                int i6 = Integer.MAX_VALUE;
                int i7 = 0;
                for (View view2 : list2) {
                    i6 = Math.min(i6, (int) view2.getY());
                    i7 = Math.max(i7, (int) view2.getY());
                }
                height = i7 - i6;
            }
        } else {
            height = ((KeyguardStatusView) keyguardStatusViewController2.mView).getHeight();
        }
        int calculateNotificationsTopPadding = (int) quickSettingsController.calculateNotificationsTopPadding(getKeyguardNotificationStaticPadding());
        float f = this.mInterpolatedDarkAmount;
        FaceWidgetContainerWrapper faceWidgetContainerWrapper2 = this.mKeyguardStatusViewController.mKeyguardStatusBase;
        keyguardClockPositionAlgorithm.setup(i5, computeExpansionFraction, height, calculateNotificationsTopPadding, i2, f, height2, i, (faceWidgetContainerWrapper2 == null || (pluginKeyguardStatusView = faceWidgetContainerWrapper2.mPluginKeyguardStatusView) == null) ? 0 : pluginKeyguardStatusView.getCurrentClockType(), new NotificationPanelViewController$$ExternalSyntheticLambda15(this, i3));
        KeyguardClockPositionAlgorithm keyguardClockPositionAlgorithm2 = this.mClockPositionAlgorithm;
        KeyguardClockPositionAlgorithm.Result result = this.mClockPositionResult;
        keyguardClockPositionAlgorithm2.run(result);
        KeyguardStatusViewController keyguardStatusViewController3 = this.mKeyguardStatusViewController;
        int i8 = keyguardClockPositionAlgorithm2.mIsSplitShade ? keyguardClockPositionAlgorithm2.mSplitShadeTargetTopMargin : keyguardClockPositionAlgorithm2.mMinTopMargin;
        KeyguardClockSwitch keyguardClockSwitch = (KeyguardClockSwitch) keyguardStatusViewController3.mKeyguardClockSwitchController.mView;
        if (keyguardClockSwitch.screenOffsetYPadding != i8) {
            keyguardClockSwitch.screenOffsetYPadding = i8;
            keyguardClockSwitch.updateClockTargetRegions();
        }
        ((KeyguardRepositoryImpl) this.mKeyguardBottomAreaInteractor.repository)._clockPosition.setValue(new Position(result.clockX, result.clockY));
        PluginLockDataImpl pluginLockDataImpl = (PluginLockDataImpl) this.mPluginLockData;
        if (pluginLockDataImpl.isAvailable()) {
            PluginLockStar pluginLockStar = ((PluginLockStarManager) this.mPluginLockStarManagerLazy.get()).mPluginLockStar;
            if (pluginLockStar != null) {
                try {
                    isPositionSynchronized = pluginLockStar.isPositionSynchronized(PluginLockStar.NOTIFICATION_TYPE);
                } catch (Throwable unused) {
                }
                if (!isPositionSynchronized) {
                    result.stackScrollerPadding = pluginLockDataImpl.getTop(3);
                    pluginLockDataImpl.getTop(4);
                    notificationStackScrollLayoutController.mView.setTranslationX(pluginLockDataImpl.getPaddingStart(4));
                }
            }
            isPositionSynchronized = false;
            if (!isPositionSynchronized) {
            }
        }
        if (LsRune.LOCKUI_MULTI_USER) {
            result.userSwitchY = SystemBarUtils.getStatusBarHeight(notificationPanelView.getContext());
        }
        boolean z2 = (notificationStackScrollLayoutController.isAddOrRemoveAnimationPending() || this.mAnimateNextPositionUpdate) && z;
        this.mKeyguardStatusViewController.updatePosition(result.clockX, result.clockY, z2, result.contentsContainerPosition);
        KeyguardQsUserSwitchController keyguardQsUserSwitchController2 = this.mKeyguardQsUserSwitchController;
        if (keyguardQsUserSwitchController2 != null) {
            int i9 = result.clockX;
            int i10 = result.userSwitchY;
            AnimationProperties animationProperties = KeyguardQsUserSwitchController.ANIMATION_PROPERTIES;
            PropertyAnimator.setProperty((FrameLayout) keyguardQsUserSwitchController2.mView, AnimatableProperty.f351Y, i10, animationProperties, z2);
            PropertyAnimator.setProperty((FrameLayout) keyguardQsUserSwitchController2.mView, AnimatableProperty.TRANSLATION_X, -Math.abs(i9), animationProperties, z2);
        }
        KeyguardUserSwitcherController keyguardUserSwitcherController2 = this.mKeyguardUserSwitcherController;
        if (keyguardUserSwitcherController2 != null) {
            int i11 = result.clockX;
            int i12 = result.userSwitchY;
            AnimationProperties animationProperties2 = KeyguardUserSwitcherController.ANIMATION_PROPERTIES;
            PropertyAnimator.setProperty(keyguardUserSwitcherController2.mListView, AnimatableProperty.f351Y, i12, animationProperties2, z2);
            PropertyAnimator.setProperty(keyguardUserSwitcherController2.mListView, AnimatableProperty.TRANSLATION_X, -Math.abs(i11), animationProperties2, z2);
            Rect rect2 = new Rect();
            keyguardUserSwitcherController2.mListView.getDrawingRect(rect2);
            ((KeyguardUserSwitcherView) keyguardUserSwitcherController2.mView).offsetDescendantRectToMyCoords(keyguardUserSwitcherController2.mListView, rect2);
            int translationX = (int) (keyguardUserSwitcherController2.mListView.getTranslationX() + rect2.left + (rect2.width() / 2));
            int translationY = (int) (keyguardUserSwitcherController2.mListView.getTranslationY() + rect2.top + (rect2.height() / 2));
            KeyguardUserSwitcherScrim keyguardUserSwitcherScrim = keyguardUserSwitcherController2.mBackground;
            keyguardUserSwitcherScrim.mCircleX = translationX;
            keyguardUserSwitcherScrim.mCircleY = translationY;
            keyguardUserSwitcherScrim.updatePaint();
        }
        updateNotificationTranslucency();
        updateClock();
    }

    public final void updateDozingVisibilities(boolean z) {
        this.mKeyguardBottomArea.setVisibility(0);
        if (!this.mDozing && z) {
            this.mKeyguardStatusBarViewController.animateKeyguardStatusBarIn();
        }
        if (LsRune.KEYGUARD_DCM_LIVE_UX) {
            this.mMascotViewContainer.setMascotViewVisible(this.mDozing ? 8 : 0);
        }
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
    public final void updateDynamicLockData(String str) {
        this.mKeyguardBottomAreaViewController.updateBottomView();
    }

    public final void updateExpandedHeight(float f) {
        boolean z = this.mTracking;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        if (z) {
            VelocityTracker velocityTracker = this.mVelocityTracker;
            velocityTracker.computeCurrentVelocity(1000);
            notificationStackScrollLayoutController.mView.mAmbientState.mExpandingVelocity = velocityTracker.getYVelocity();
        }
        if (this.mKeyguardBypassController.getBypassEnabled() && isOnKeyguard()) {
            f = getMaxPanelHeight();
        }
        if (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE) {
            notificationStackScrollLayoutController.mView.setExpandedHeight(f);
        } else if (!this.mKeyguardTouchAnimator.isViRunning()) {
            notificationStackScrollLayoutController.mView.setExpandedHeight(f);
        }
        updateKeyguardBottomAreaAlpha();
        float f2 = this.mExpandedHeight;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.getClass();
        boolean z2 = f2 < ((float) ((NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW || notificationStackScrollLayout.mEmptyShadeView.getVisibility() == 8) ? notificationStackScrollLayout.getMinExpansionHeight() : notificationStackScrollLayout.getMinExpansionHeight()));
        if (z2 && (isOnKeyguard() || this.mBarState == 2)) {
            z2 = false;
        }
        if (z2 != this.mShowIconsWhenExpanded) {
            this.mShowIconsWhenExpanded = z2;
            this.mCommandQueue.recomputeDisableFlags(this.mDisplayId, false);
        }
    }

    public final void updateExpandedHeightToMaxHeight() {
        float maxPanelHeight = getMaxPanelHeight();
        if (isFullyCollapsed() || this.mTouchDownOnHeadsUpPinnded || maxPanelHeight == this.mExpandedHeight) {
            return;
        }
        if ((!this.mTracking && !this.mHeadsUpTouchHelper.mTrackingHeadsUp) || this.mBlockingExpansionForCurrentTouch || this.mQsController.isTrackingBlocked()) {
            if (this.mHeightAnimator == null || this.mIsSpringBackAnimation) {
                setExpandedHeight(maxPanelHeight);
            } else {
                this.mPanelUpdateWhenAnimatorEnds = true;
            }
        }
    }

    public final void updateExpansionAndVisibility() {
        boolean z;
        boolean z2;
        float f = this.mExpandedFraction;
        boolean isExpanded = isExpanded();
        boolean z3 = this.mTracking;
        float f2 = this.mExpansionDragDownAmountPx;
        ShadeExpansionStateManager shadeExpansionStateManager = this.mShadeExpansionStateManager;
        shadeExpansionStateManager.getClass();
        boolean z4 = true;
        if (!(!Float.isNaN(f))) {
            throw new IllegalArgumentException("fraction cannot be NaN".toString());
        }
        int i = shadeExpansionStateManager.state;
        boolean z5 = !(shadeExpansionStateManager.fraction == f);
        shadeExpansionStateManager.fraction = f;
        shadeExpansionStateManager.expanded = isExpanded;
        shadeExpansionStateManager.tracking = z3;
        shadeExpansionStateManager.dragDownPxAmount = f2;
        if (isExpanded) {
            if (i == 0) {
                shadeExpansionStateManager.updateStateInternal(1);
            }
            z2 = f >= 1.0f;
            z = false;
        } else {
            z = true;
            z2 = false;
        }
        if (z2 && !z3) {
            shadeExpansionStateManager.updateStateInternal(2);
        } else if (z && !z3 && shadeExpansionStateManager.state != 0) {
            shadeExpansionStateManager.updateStateInternal(0);
        }
        ShadeExpansionStateManagerKt.panelStateToString(i);
        ShadeExpansionStateManagerKt.panelStateToString(shadeExpansionStateManager.state);
        if (Trace.isTagEnabled(4096L)) {
            Trace.traceCounter(4096L, "panel_expansion", (int) (100 * f));
            if (shadeExpansionStateManager.state != i) {
                Trace.asyncTraceForTrackEnd(4096L, "ShadeExpansionState", 0);
                Trace.asyncTraceForTrackBegin(4096L, "ShadeExpansionState", ShadeExpansionStateManagerKt.panelStateToString(shadeExpansionStateManager.state), 0);
            }
        }
        ShadeExpansionChangeEvent shadeExpansionChangeEvent = new ShadeExpansionChangeEvent(f, isExpanded, z3, f2);
        if (z5) {
            StringBuilder sb = shadeExpansionStateManager.logBuilder;
            sb.setLength(0);
            sb.append("ShadeExpansionStateManager onPanelExpansionChanged : " + shadeExpansionChangeEvent);
            ((SecPanelLoggerImpl) ((SecPanelLogger) shadeExpansionStateManager.panelLogger$delegate.getValue())).addPanelStateInfoLog(sb, true);
        }
        Iterator it = shadeExpansionStateManager.expansionListeners.iterator();
        while (it.hasNext()) {
            ((ShadeExpansionListener) it.next()).onPanelExpansionChanged(shadeExpansionChangeEvent);
        }
        updateVisibility();
        SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper = this.mQsController.mSecQuickSettingsController.tabletHorizontalPanelPositionHelper;
        secTabletHorizontalPanelPositionHelper.getClass();
        if (QpRune.QUICK_TABLET_HORIZONTAL_PANEL_POSITION) {
            RunnableC2460x13d85fb4 runnableC2460x13d85fb4 = secTabletHorizontalPanelPositionHelper.updateHorizontalPositionRunnable;
            if (runnableC2460x13d85fb4 != null) {
                runnableC2460x13d85fb4.run();
            }
            secTabletHorizontalPanelPositionHelper.updateHorizontalPositionRunnable = null;
        }
        if (isExpanded()) {
            if (Float.compare(this.mExpandedFraction, 0.0f) == 0 || Float.compare(this.mExpandedFraction, 1.0f) == 0) {
                StringBuilder sb2 = this.mLogBuilder;
                sb2.setLength(0);
                sb2.append("updateExpansionAndVisibility: ");
                sb2.append("mExpandedFraction: ");
                sb2.append(this.mExpandedFraction);
                sb2.append(", mInstantExpanding: ");
                sb2.append(this.mInstantExpanding);
                sb2.append(", isPanelVisibleBecauseOfHeadsUp: ");
                sb2.append((this.mHeadsUpManager.mHasPinnedNotification || this.mHeadsUpAnimatingAway) && this.mBarState == 0);
                sb2.append(", mTracking: ");
                sb2.append(this.mTracking);
                sb2.append(", mIsSpringBackAnimation: ");
                sb2.append(this.mIsSpringBackAnimation);
                sb2.append(", isExpanded: ");
                sb2.append(isExpanded());
                sb2.append(", isFullyExpanded: ");
                sb2.append(isFullyExpanded());
                sb2.append(", isFullyCollapsed: ");
                sb2.append(isFullyCollapsed());
                sb2.append(", mHeightAnimator is null?: ");
                sb2.append(this.mHeightAnimator == null);
                if (this.mExpandedFraction <= 0.0f && this.mExpandedHeight <= 0.0f) {
                    z4 = false;
                }
                ((SecPanelLoggerImpl) this.mPanelLogger).addPanelStateInfoLog(sb2, z4);
            }
        }
    }

    public final void updateGestureExclusionRect() {
        Region calculateTouchableRegion = this.mStatusBarTouchableRegionManager.calculateTouchableRegion();
        Rect bounds = (!isFullyCollapsed() || calculateTouchableRegion == null) ? null : calculateTouchableRegion.getBounds();
        if (bounds == null) {
            bounds = EMPTY_RECT;
        }
        this.mView.setSystemGestureExclusionRects(bounds.isEmpty() ? Collections.emptyList() : Collections.singletonList(bounds));
    }

    public final void updateKeyguardBottomAreaAlpha() {
        KeyguardUserSwitcherController keyguardUserSwitcherController;
        if (this.mKeyguardBottomArea == null || this.mSecAffordanceHelper.isShortcutPreviewSwipingInProgress || this.mKeyguardStateController.mKeyguardGoingAway) {
            return;
        }
        boolean z = true;
        if (this.mBarState != 1 || this.mKeyguardTouchAnimator.isViRunning() || this.mIsOcclusionTransitionRunning) {
            return;
        }
        float min = Math.min(MathUtils.map(this.mHintAnimationRunning ? 0.0f : 0.95f, 1.0f, 0.0f, 1.0f, this.mExpandedFraction), 1.0f - this.mQsController.computeExpansionFraction()) * this.mBottomAreaShadeAlpha;
        KeyguardSecAffordanceHelper keyguardSecAffordanceHelper = this.mSecAffordanceHelper;
        if (!keyguardSecAffordanceHelper.mPreviewAnimationStarted) {
            KeyguardSecAffordanceView keyguardSecAffordanceView = keyguardSecAffordanceHelper.mLeftIcon;
            Intrinsics.checkNotNull(keyguardSecAffordanceView);
            if (!(keyguardSecAffordanceView.mShortcutLaunchAnimator != null)) {
                KeyguardSecAffordanceView keyguardSecAffordanceView2 = keyguardSecAffordanceHelper.mRightIcon;
                Intrinsics.checkNotNull(keyguardSecAffordanceView2);
                if (!(keyguardSecAffordanceView2.mShortcutLaunchAnimator != null)) {
                    z = false;
                }
            }
            if (!z) {
                KeyguardSecAffordanceHelper keyguardSecAffordanceHelper2 = this.mSecAffordanceHelper;
                KeyguardSecAffordanceView keyguardSecAffordanceView3 = (KeyguardSecAffordanceView) this.mKeyguardBottomArea.getLeftView();
                keyguardSecAffordanceHelper2.getClass();
                keyguardSecAffordanceView3.setImageAlpha(Math.min(1.0f, min), false);
                keyguardSecAffordanceView3.setImageScale(1.0f, false);
                KeyguardSecAffordanceHelper keyguardSecAffordanceHelper3 = this.mSecAffordanceHelper;
                KeyguardSecAffordanceView keyguardSecAffordanceView4 = (KeyguardSecAffordanceView) this.mKeyguardBottomArea.getRightView();
                keyguardSecAffordanceHelper3.getClass();
                keyguardSecAffordanceView4.setImageAlpha(Math.min(1.0f, min), false);
                keyguardSecAffordanceView4.setImageScale(1.0f, false);
            }
        }
        float faceWidgetAlpha = getFaceWidgetAlpha();
        if (faceWidgetAlpha < 0.0f) {
            return;
        }
        this.mKeyguardBottomAreaViewController.setAffordanceAlpha(faceWidgetAlpha);
        ((KeyguardRepositoryImpl) this.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.setValue(Float.valueOf(faceWidgetAlpha));
        this.mKeyguardBottomArea.setAlpha(faceWidgetAlpha);
        SecLockIconViewController secLockIconViewController = this.mLockIconViewController;
        if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
            ((LockIconView) secLockIconViewController.mView).setAlpha(faceWidgetAlpha);
        }
        if (LsRune.LOCKUI_MULTI_USER && (keyguardUserSwitcherController = this.mKeyguardUserSwitcherController) != null && !keyguardUserSwitcherController.mKeyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
            ((KeyguardUserSwitcherView) keyguardUserSwitcherController.mView).setAlpha(faceWidgetAlpha);
        }
        View view = this.mPluginLockStarContainer;
        if (view != null) {
            view.setAlpha(this.mDozing ? 0.0f : faceWidgetAlpha);
        }
    }

    public final void updateKeyguardStatusViewAlignment(boolean z) {
        final boolean z2;
        if (this.mSplitShadeEnabled && hasVisibleNotifications()) {
            if (NotificationStackScrollLayoutController.this.mView.mPulsing) {
                z2 = false;
            } else if (!this.mWillPlayDelayedDozeAmountAnimation) {
                z2 = isOnAod();
            }
            this.mKeyguardStatusViewController.updateAlignment(this.mNotificationContainerParent, this.mSplitShadeEnabled, z2, z);
            this.mKeyguardUnfoldTransition.ifPresent(new Consumer() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda12
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((KeyguardUnfoldTransition) obj).statusViewCentered = z2;
                }
            });
        }
        z2 = true;
        this.mKeyguardStatusViewController.updateAlignment(this.mNotificationContainerParent, this.mSplitShadeEnabled, z2, z);
        this.mKeyguardUnfoldTransition.ifPresent(new Consumer() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((KeyguardUnfoldTransition) obj).statusViewCentered = z2;
            }
        });
    }

    public final void updateMaxDisplayedNotifications(boolean z) {
        if (z) {
            int i = this.mCountOfUpdateDisplayedNotifications + 1;
            this.mCountOfUpdateDisplayedNotifications = i;
            if (i == 1) {
                this.mCountOfUpdateDisplayedNotificationsCurrentMill = System.currentTimeMillis();
            } else if (i >= 40) {
                if (System.currentTimeMillis() - this.mCountOfUpdateDisplayedNotificationsCurrentMill < 1000) {
                    Log.d("NotificationPanelView", "too much call updateMaxDisplayedNotifications >>> " + Log.getStackTraceString(new Throwable()));
                }
                this.mCountOfUpdateDisplayedNotifications = 0;
            }
            setMaxDisplayedNotifications(Math.max(computeMaxKeyguardNotifications(), 0));
            this.mRecomputedMaxCountNotification = "Recompute";
        } else {
            this.mRecomputedMaxCountNotification = "Skip";
        }
        boolean isKeyguardShowing = isKeyguardShowing();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        if (!isKeyguardShowing || this.mKeyguardBypassController.getBypassEnabled()) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            if (notificationStackScrollLayout.mMaxDisplayedNotifications != -1) {
                notificationStackScrollLayout.mMaxDisplayedNotifications = -1;
                notificationStackScrollLayout.updateContentHeight();
                notificationStackScrollLayout.notifyHeightChangeListener(notificationStackScrollLayout.mShelf, false);
            }
            notificationStackScrollLayoutController.mView.getClass();
            return;
        }
        int i2 = this.mMaxAllowedKeyguardNotifications;
        NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayoutController.this.mView;
        if (notificationStackScrollLayout2.mMaxDisplayedNotifications != i2) {
            notificationStackScrollLayout2.mMaxDisplayedNotifications = i2;
            notificationStackScrollLayout2.updateContentHeight();
            notificationStackScrollLayout2.notifyHeightChangeListener(notificationStackScrollLayout2.mShelf, false);
        }
        notificationStackScrollLayoutController.mView.getClass();
    }

    public final void updateNotificationTranslucency() {
        if (this.mSecAffordanceHelper.isShortcutPreviewSwipingInProgress || this.mKeyguardTouchAnimator.isViRunning() || ((NotificationIconTransitionController) Dependency.get(NotificationIconTransitionController.class)).misTransformAnimating || ((KeyguardEditModeControllerImpl) this.mKeyguardEditModeController).getVIRunning() || this.mIsOcclusionTransitionRunning || this.mQsController.mExpanded) {
            return;
        }
        float fadeoutAlpha = (!this.mClosingWithAlphaFadeOut || this.mExpandingFromHeadsUp || this.mHeadsUpManager.mHasPinnedNotification) ? 1.0f : getFadeoutAlpha();
        if (this.mBarState == 1 && !this.mHintAnimationRunning && !this.mKeyguardBypassController.getBypassEnabled()) {
            fadeoutAlpha *= this.mClockPositionResult.clockAlpha;
        }
        NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScrollLayoutController.mView;
        if (notificationStackScrollLayout != null) {
            notificationStackScrollLayout.setAlpha(fadeoutAlpha);
        }
    }

    public final void updatePanelExpanded() {
        boolean z = !isFullyCollapsed() || this.mExpectingSynthesizedDown;
        if (this.mPanelExpanded != z) {
            this.mPanelExpanded = z;
            this.mShadeHeaderController.panelExpanded = z;
            ShadeExpansionStateManager shadeExpansionStateManager = this.mShadeExpansionStateManager;
            shadeExpansionStateManager.expanded = z;
            int i = ShadeExpansionStateManagerKt.$r8$clinit;
            Iterator it = shadeExpansionStateManager.fullExpansionListeners.iterator();
            while (it.hasNext()) {
                ((ShadeFullExpansionListener) it.next()).onShadeExpansionFullyChanged(z);
            }
            if (!z) {
                this.mQsController.mQs.closeCustomizer();
            }
            ViewRootImpl viewRootImpl = this.mView.getRootView().getViewRootImpl();
            if (this.mBarState == 0) {
                viewRootImpl.setDisableSuperHdr(z);
            } else {
                viewRootImpl.setDisableSuperHdr(false);
            }
        }
        if (isOnKeyguard()) {
            return;
        }
        KeyguardTouchAnimator keyguardTouchAnimator = this.mKeyguardTouchAnimator;
        if (keyguardTouchAnimator.notiScale > 1.0f) {
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m44m(new StringBuilder("current noti scale : "), keyguardTouchAnimator.notiScale, "NotificationPanelView");
        }
    }

    public final void updateResources() {
        Resources resources = this.mResources;
        boolean shouldUseSplitNotificationShade = LargeScreenUtils.shouldUseSplitNotificationShade(resources);
        boolean z = this.mSplitShadeEnabled != shouldUseSplitNotificationShade;
        this.mSplitShadeEnabled = shouldUseSplitNotificationShade;
        QuickSettingsController quickSettingsController = this.mQsController;
        Resources resources2 = quickSettingsController.mResources;
        boolean shouldUseSplitNotificationShade2 = LargeScreenUtils.shouldUseSplitNotificationShade(resources2);
        quickSettingsController.mSplitShadeEnabled = shouldUseSplitNotificationShade2;
        InterfaceC1922QS interfaceC1922QS = quickSettingsController.mQs;
        if (interfaceC1922QS != null) {
            interfaceC1922QS.setInSplitShade(shouldUseSplitNotificationShade2);
        }
        quickSettingsController.mSplitShadeNotificationsScrimMarginBottom = resources2.getDimensionPixelSize(R.dimen.split_shade_notifications_scrim_margin_bottom);
        quickSettingsController.mUseLargeScreenShadeHeader = true;
        ShadeHeaderController shadeHeaderController = quickSettingsController.mShadeHeaderController;
        if (!shadeHeaderController.largeScreenActive) {
            shadeHeaderController.largeScreenActive = true;
            shadeHeaderController.updateTransition();
        }
        quickSettingsController.mAmbientState.mStackTopMargin = 0;
        float f = 0;
        quickSettingsController.mQuickQsHeaderHeight = f;
        quickSettingsController.mEnableClipping = resources2.getBoolean(R.bool.qs_enable_clipping);
        NotificationPanelView notificationPanelView = quickSettingsController.mPanelView;
        quickSettingsController.mCachedGestureInsets = ((WindowManager) notificationPanelView.getContext().getSystemService(WindowManager.class)).getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.systemGestures());
        quickSettingsController.mSecQuickSettingsController.expandQSAtOnceController.updateResources();
        quickSettingsController.mQuickQsOffsetHeight = SystemBarUtils.getQuickQsOffsetHeight(notificationPanelView.getContext());
        this.mNotificationsQSContainerController.updateResources();
        updateKeyguardStatusViewAlignment(false);
        this.mKeyguardMediaController.getClass();
        if (z) {
            boolean z2 = this.mSplitShadeEnabled;
            ShadeLogger shadeLogger = this.mShadeLog;
            shadeLogger.getClass();
            LogLevel logLevel = LogLevel.VERBOSE;
            ShadeLogger$logSplitShadeChanged$2 shadeLogger$logSplitShadeChanged$2 = new Function1() { // from class: com.android.systemui.shade.ShadeLogger$logSplitShadeChanged$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return "Split shade state changed: split shade ".concat(((LogMessage) obj).getBool1() ? "enabled" : "disabled");
                }
            };
            LogBuffer logBuffer = shadeLogger.buffer;
            LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$logSplitShadeChanged$2, null);
            obtain.setBool1(z2);
            logBuffer.commit(obtain);
            this.mKeyguardStatusViewController.setSplitShadeEnabled(this.mSplitShadeEnabled);
            InterfaceC1922QS interfaceC1922QS2 = quickSettingsController.mQs;
            if (interfaceC1922QS2 != null) {
                interfaceC1922QS2.setOverScrollAmount(0);
            }
            this.mScrimController.mNotificationsScrim.setTranslationY(f);
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            notificationStackScrollLayout.mAmbientState.mOverExpansion = 0.0f;
            notificationStackScrollLayout.updateStackPosition(false);
            notificationStackScrollLayout.requestChildrenUpdate();
            notificationStackScrollLayoutController.setOverScrollAmount(0);
            if (!isOnKeyguard() && this.mPanelExpanded) {
                quickSettingsController.setExpanded(this.mSplitShadeEnabled);
            }
            if (isOnKeyguard() && quickSettingsController.mExpanded && this.mSplitShadeEnabled) {
                ((StatusBarStateControllerImpl) this.mStatusBarStateController).setState(2, false);
            }
            updateClockAppearance();
            quickSettingsController.updateQsState();
            notificationStackScrollLayoutController.updateFooter();
        }
        this.mSplitShadeFullTransitionDistance = resources.getDimensionPixelSize(R.dimen.split_shade_full_transition_distance);
    }

    public final void updateSystemUiStateFlags() {
        CentralSurfaces centralSurfaces = this.mCentralSurfaces;
        boolean z = centralSurfaces != null && ((CentralSurfacesImpl) centralSurfaces).mPanelExpanded;
        SysUiState sysUiState = this.mSysUiState;
        sysUiState.setFlag(1073741824L, z);
        boolean isFullyExpanded = isFullyExpanded();
        QuickSettingsController quickSettingsController = this.mQsController;
        sysUiState.setFlag(4L, isFullyExpanded && !quickSettingsController.mExpanded);
        sysUiState.setFlag(2048L, isFullyExpanded() && quickSettingsController.mExpanded);
        sysUiState.commitUpdate(this.mDisplayId);
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0022, code lost:
    
        if (((com.android.systemui.flags.FeatureFlagsRelease) r4.mFeatureFlags).isEnabled(com.android.systemui.flags.Flags.QS_USER_DETAIL_SHORTCUT) != false) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateUserSwitcherFlags() {
        boolean z = true;
        boolean z2 = this.mResources.getBoolean(android.R.bool.config_guestUserAutoCreated) && LsRune.LOCKUI_MULTI_USER;
        this.mKeyguardUserSwitcherEnabled = z2;
        if (z2) {
        }
        z = false;
        this.mKeyguardQsUserSwitchEnabled = z;
    }

    public final void updateUserSwitcherViewControllers(FrameLayout frameLayout, KeyguardUserSwitcherView keyguardUserSwitcherView) {
        this.mKeyguardQsUserSwitchController = null;
        this.mKeyguardUserSwitcherController = null;
        if (frameLayout != null) {
            KeyguardQsUserSwitchController keyguardQsUserSwitchController = this.mKeyguardQsUserSwitchComponentFactory.build(frameLayout).getKeyguardQsUserSwitchController();
            this.mKeyguardQsUserSwitchController = keyguardQsUserSwitchController;
            keyguardQsUserSwitchController.init();
            ((KeyguardStatusBarView) this.mKeyguardStatusBarViewController.mView).mKeyguardUserSwitcherEnabled = true;
            return;
        }
        if (keyguardUserSwitcherView == null) {
            ((KeyguardStatusBarView) this.mKeyguardStatusBarViewController.mView).mKeyguardUserSwitcherEnabled = false;
            return;
        }
        KeyguardUserSwitcherController keyguardUserSwitcherController = this.mKeyguardUserSwitcherComponentFactory.build(keyguardUserSwitcherView).getKeyguardUserSwitcherController();
        this.mKeyguardUserSwitcherController = keyguardUserSwitcherController;
        keyguardUserSwitcherController.init();
        ((KeyguardStatusBarView) this.mKeyguardStatusBarViewController.mView).mKeyguardUserSwitcherEnabled = true;
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x009a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateVisibility() {
        boolean z;
        int i;
        NotificationPanelView notificationPanelView;
        boolean z2 = this.mHeadsUpAnimatingAway || this.mHeadsUpPinnedMode;
        int i2 = this.mPanelInVisibleReason;
        this.mPanelInVisibleReason = -1;
        if (!z2) {
            this.mPanelInVisibleReason = -1;
            boolean isExpanded = isExpanded();
            if (!isExpanded) {
                this.mPanelInVisibleReason = 0;
            }
            if (isExpanded && this.mBouncerShowing) {
                this.mPanelInVisibleReason = 1;
                isExpanded = false;
            }
            if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK && isExpanded && ((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.get(KeyguardFoldController.class))).isUnlockOnFoldOpened()) {
                this.mPanelInVisibleReason = 2;
                isExpanded = false;
            }
            if (LsRune.SECURITY_SWIPE_BOUNCER && isExpanded) {
                KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.get(KeyguardStateController.class));
                if (keyguardStateControllerImpl.mIsSwipeBouncer && keyguardStateControllerImpl.mCanDismissLockScreen && keyguardStateControllerImpl.mShowing) {
                    this.mPanelInVisibleReason = 3;
                    isExpanded = false;
                }
            }
            if (!isExpanded) {
                z = false;
                i = this.mPanelInVisibleReason;
                notificationPanelView = this.mView;
                if ((notificationPanelView.getVisibility() == 0) == z || (!z && i2 != i)) {
                    com.android.systemui.keyguard.Log.m139d("KeyguardVisible", "shouldPanelBeVisible %b / headUpVisible=%b, why=%d", Boolean.valueOf(z), Boolean.valueOf(z2), Integer.valueOf(i));
                }
                notificationPanelView.setVisibility(z ? 0 : 4);
            }
        }
        z = true;
        i = this.mPanelInVisibleReason;
        notificationPanelView = this.mView;
        if ((notificationPanelView.getVisibility() == 0) == z) {
        }
        com.android.systemui.keyguard.Log.m139d("KeyguardVisible", "shouldPanelBeVisible %b / headUpVisible=%b, why=%d", Boolean.valueOf(z), Boolean.valueOf(z2), Integer.valueOf(i));
        notificationPanelView.setVisibility(z ? 0 : 4);
    }

    public final void collapse(float f, boolean z) {
        if (canBeCollapsed()) {
            QuickSettingsController quickSettingsController = this.mQsController;
            if (quickSettingsController.mExpanded) {
                quickSettingsController.setExpandImmediate(true);
                if (QpRune.QUICK_TABLET) {
                    setShowShelfOnly(true);
                }
            }
            if (canBeCollapsed()) {
                cancelHeightAnimator();
                notifyExpandingStarted();
                setClosing(true);
                if (z) {
                    this.mNextCollapseSpeedUpFactor = f;
                    this.mView.postDelayed(this.mFlingCollapseRunnable, 120L);
                } else {
                    fling(0.0f, false, f);
                }
            }
        }
    }

    public final void fling(float f, boolean z, float f2) {
        float maxPanelTransitionDistance = z ? getMaxPanelTransitionDistance() : 0.0f;
        if (!z) {
            setClosing(true);
        }
        flingToHeight(f, z, maxPanelTransitionDistance, f2, false);
    }

    public void setOverExpansion(float f) {
    }
}
