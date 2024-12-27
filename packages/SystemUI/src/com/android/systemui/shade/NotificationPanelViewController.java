package com.android.systemui.shade;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import android.graphics.Rect;
import android.graphics.Region;
import android.metrics.LogMaker;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import android.os.UserManager;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.MathUtils;
import android.util.Property;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.compose.animation.scene.SceneKey;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.internal.policy.SystemBarUtils;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.LatencyTracker;
import com.android.keyguard.ActiveUnlockConfig;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitch;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardStatusView;
import com.android.keyguard.KeyguardStatusViewController;
import com.android.keyguard.KeyguardUnfoldTransition;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.SecLockIconViewController;
import com.android.keyguard.biometrics.KeyguardFingerprintGuidePopup;
import com.android.keyguard.dagger.KeyguardQsUserSwitchComponent;
import com.android.keyguard.dagger.KeyguardStatusBarViewComponent;
import com.android.keyguard.dagger.KeyguardStatusViewComponent;
import com.android.keyguard.dagger.KeyguardUserSwitcherComponent;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.keyguard.punchhole.KeyguardPunchHoleVIView;
import com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController;
import com.android.settingslib.satellite.SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0;
import com.android.systemui.CscRune;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.bixby2.controller.MWBixbyController$$ExternalSyntheticOutline0;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.common.buffer.RingBuffer;
import com.android.systemui.common.buffer.RingBuffer$iterator$1;
import com.android.systemui.communal.ui.viewmodel.CommunalTransitionViewModel;
import com.android.systemui.cover.CoverScreenManager;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.deviceentry.shared.DeviceEntryUdfpsRefactor;
import com.android.systemui.doze.DozeLog;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.dump.DumpsysTableLogger;
import com.android.systemui.facewidget.FaceWidgetNotificationController;
import com.android.systemui.facewidget.plugin.FaceWidgetContainerWrapper;
import com.android.systemui.facewidget.plugin.FaceWidgetKeyguardStatusCallbackWrapper;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.facewidget.plugin.FaceWidgetPositionAlgorithmWrapper;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.keyguard.KeyguardBottomAreaRefactor;
import com.android.systemui.keyguard.KeyguardClickController;
import com.android.systemui.keyguard.KeyguardClickControllerImpl;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.KeyguardViewConfigurator;
import com.android.systemui.keyguard.MigrateClocksToBlueprint;
import com.android.systemui.keyguard.animator.KeyguardEditModeAnimatorController;
import com.android.systemui.keyguard.animator.KeyguardTouchAnimator;
import com.android.systemui.keyguard.animator.KeyguardTouchSwipeCallback;
import com.android.systemui.keyguard.animator.KeyguardTouchViewInjector;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardBottomAreaInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$2$1$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.domain.interactor.NaturalScrollingSettingObserver;
import com.android.systemui.keyguard.shared.ComposeLockscreen;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.GoneToDreamingLockscreenHostedTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.GoneToDreamingTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardLongPressViewModel;
import com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.LockscreenToOccludedTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.OccludedToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel;
import com.android.systemui.keyguardimage.WallpaperImageInjectCreator;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.QuickPanelLogger;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.ui.controller.KeyguardMediaController;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.media.controls.ui.controller.MediaHierarchyManager;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.gestural.Utilities;
import com.android.systemui.pluginlock.PluginLockData;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.pluginlock.model.DynamicLockData;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import com.android.systemui.plugins.keyguardstatusview.PluginNotificationController;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.shared.model.WakeSleepReason;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.qs.QSFragmentLegacy;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.SecQSImpl;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.SecQuickQSPanelController;
import com.android.systemui.qs.animator.SecQSImplAnimatorManager;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.shade.NPVCDownEventState;
import com.android.systemui.shade.QuickSettingsControllerImpl;
import com.android.systemui.shade.QuickSettingsControllerImpl.QsFragmentListener;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.shade.domain.interactor.SecHideNotificationShadeInMirrorInteractor;
import com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor;
import com.android.systemui.shade.domain.interactor.ShadeAnimationInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
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
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.PulseExpansionHandler;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.iconsOnly.LockscreenNotificationIconsOnlyController;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.ViewGroupFadeHelper;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor;
import com.android.systemui.statusbar.notification.footer.shared.FooterViewRefactor;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.shared.NotificationsHeadsUpRefactor;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator;
import com.android.systemui.statusbar.notification.stack.StackStateAnimator;
import com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor;
import com.android.systemui.statusbar.phone.BounceInterpolator;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda29;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.DcmMascotViewContainer;
import com.android.systemui.statusbar.phone.DcmMascotViewContainer$broadcastReceiver$1;
import com.android.systemui.statusbar.phone.DcmMascotViewContainer$sendUnreadCountBroadcast$1;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.HeadsUpTouchHelper;
import com.android.systemui.statusbar.phone.IndicatorTouchHandler;
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
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.ScrimState;
import com.android.systemui.statusbar.phone.SecLsScrimControlHelper;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager;
import com.android.systemui.statusbar.phone.TapAgainViewController;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager;
import com.android.systemui.statusbar.phone.datausage.DataUsageLabelParent;
import com.android.systemui.statusbar.phone.datausage.DataUsageLabelView;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.BrightnessMirrorController$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.KeyguardQsUserSwitchController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherController;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherView;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.statusbar.window.StatusBarWindowStateListener;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.unfold.FoldAodAnimationController$startAnimationRunnable$1;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.LargeScreenUtils;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.QsStatusEventLog;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.Utils;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIWidgetCallback;
import com.android.wm.shell.animation.FlingAnimationUtils;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.multiwindow.MultiWindowEdgeDetector;
import com.samsung.systemui.splugins.lockstar.LockStarValues;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import com.sec.ims.presence.ServiceTuple;
import dagger.Lazy;
import dalvik.annotation.optimization.NeverCompile;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import javax.inject.Provider;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableSet;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationPanelViewController implements ShadeSurface, Dumpable, PluginLockListener.State, KeyguardTouchViewInjector, PanelScreenShotLogger.LogProvider {
    public View.OnTouchListener mAODDoubleTouchListener;
    public final ShadeAccessibilityDelegate mAccessibilityDelegate;
    public final AccessibilityManager mAccessibilityManager;
    public final ActivityStarter mActivityStarter;
    public boolean mAllowExpandForSmallExpansion;
    public final AlternateBouncerInteractor mAlternateBouncerInteractor;
    public final AmbientState mAmbientState;
    public boolean mAnimateAfterExpanding;
    public final NotificationPanelViewController$$ExternalSyntheticLambda31 mAnimateKeyguardBottomAreaInvisibleEndRunnable;
    public boolean mAnimateNextPositionUpdate;
    public boolean mAnimatingOnDown;
    public final AuthController mAuthController;
    public float mAvailableNotifSpace;
    public int mBarState;
    public Lazy mBioUnlockControllerLazy;
    public boolean mBlockingExpansionForCurrentTouch;
    public float mBottomAreaShadeAlpha;
    public final ValueAnimator mBottomAreaShadeAlphaAnimator;
    public int mBottomMarginY;
    public boolean mBouncerShowing;
    public CentralSurfaces mCentralSurfaces;
    KeyguardClockPositionAlgorithm mClockPositionAlgorithm;
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
    public float mCurrentBackProgress;
    public int mCurrentPanelState;
    public final Lazy mDataUsageLabelManagerLazy;
    public DataUsageLabelParent mDataUsageLabelParent;
    public final NotificationShadeDepthController mDepthController;
    public final DeviceEntryFaceAuthInteractor mDeviceEntryFaceAuthInteractor;
    public final int mDisplayId;
    public MotionEvent mDownEventFromOverView;
    public long mDownTime;
    public float mDownX;
    public float mDownY;
    public final DozeLog mDozeLog;
    public final DozeParameters mDozeParameters;
    public boolean mDozing;
    public boolean mDozingOnDown;
    public final NotificationPanelViewController$$ExternalSyntheticLambda8 mDreamingLockscreenHostedToLockscreenTransition;
    public final NotificationPanelViewController$$ExternalSyntheticLambda8 mDreamingToLockscreenTransition;
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
    public final NotificationPanelViewController$$ExternalSyntheticLambda31 mFlingCollapseRunnable;
    public boolean mForceFlingAnimationForTest;
    public final FragmentService mFragmentService;
    public boolean mFullScreenModeEnabled;
    public GestureRecorder mGestureRecorder;
    public boolean mGestureWaitForTouchSlop;
    public final NotificationPanelViewController$$ExternalSyntheticLambda8 mGoneToDreamingLockscreenHostedTransition;
    public final GoneToDreamingLockscreenHostedTransitionViewModel mGoneToDreamingLockscreenHostedTransitionViewModel;
    public final NotificationPanelViewController$$ExternalSyntheticLambda8 mGoneToDreamingTransition;
    public int mGoneToDreamingTransitionTranslationY;
    public final GoneToDreamingTransitionViewModel mGoneToDreamingTransitionViewModel;
    public final NotificationGutsManager mGutsManager;
    public boolean mHandlingPointerUp;
    public boolean mHasLayoutedSinceDown;
    public boolean mHasVibratedOnOpen;
    public boolean mHeadsUpAnimatingAway;
    public HeadsUpAppearanceController mHeadsUpAppearanceController;
    public final NotificationPanelViewController$$ExternalSyntheticLambda31 mHeadsUpExistenceChangedRunnable;
    public int mHeadsUpInset;
    public HeadsUpManager mHeadsUpManager;
    public boolean mHeadsUpPinnedMode;
    public int mHeadsUpStartHeight;
    public HeadsUpTouchHelper mHeadsUpTouchHelper;
    public boolean mHeadsUpVisibleOnDown;
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
    public boolean mIsExpandingOrCollapsing;
    public boolean mIsFaceWidgetOnTouchDown;
    public boolean mIsFlinging;
    public boolean mIsFullWidth;
    public boolean mIsGestureNavigation;
    public boolean mIsGoneToDreamingLockscreenHostedTransitionRunning;
    public boolean mIsKeyguardSupportLandscapePhone;
    public boolean mIsLaunchTransitionFinished;
    public boolean mIsLaunchTransitionRunning;
    public boolean mIsOcclusionTransitionRunning;
    public boolean mIsPanelCollapseOnQQS;
    public boolean mIsSpringBackAnimation;
    public boolean mIsTrackpadReverseScroll;
    public final KeyguardAffordanceHelperCallback mKeyguardAffordanceHelperCallback;
    public KeyguardBottomAreaView mKeyguardBottomArea;
    public final KeyguardBottomAreaInteractor mKeyguardBottomAreaInteractor;
    public final KeyguardBottomAreaViewController mKeyguardBottomAreaViewController;
    public final KeyguardBottomAreaViewModel mKeyguardBottomAreaViewModel;
    public final KeyguardBypassController mKeyguardBypassController;
    public final KeyguardEditModeController mKeyguardEditModeController;
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
    public final KeyguardViewConfigurator mKeyguardViewConfigurator;
    public final KeyguardWallpaperController mKeyguardWallpaperController;
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
    public final NotificationPanelViewController$$ExternalSyntheticLambda8 mLockscreenToDreamingLockscreenHostedTransition;
    public final NotificationPanelViewController$$ExternalSyntheticLambda8 mLockscreenToDreamingTransition;
    public int mLockscreenToDreamingTransitionTranslationY;
    public final LockscreenToDreamingTransitionViewModel mLockscreenToDreamingTransitionViewModel;
    public final NotificationPanelViewController$$ExternalSyntheticLambda8 mLockscreenToOccludedTransition;
    public final LockscreenToOccludedTransitionViewModel mLockscreenToOccludedTransitionViewModel;
    public final CoroutineDispatcher mMainDispatcher;
    public final DcmMascotViewContainer mMascotViewContainer;
    public int mMaxAllowedKeyguardNotifications;
    public int mMaxOverscrollAmountForPulse;
    public final NotificationPanelViewController$$ExternalSyntheticLambda31 mMaybeHideExpandedRunnable;
    public final MediaDataManager mMediaDataManager;
    public final MediaHierarchyManager mMediaHierarchyManager;
    public int mMediaNowBarExpandState;
    public boolean mMediaOutputDetailShowing;
    public final MetricsLogger mMetricsLogger;
    public float mMinExpandHeight;
    public float mMinFraction;
    public boolean mMotionAborted;
    public final MultiWindowEdgeDetector mMultiWindowEdgeDetector;
    public final NaturalScrollingSettingObserver mNaturalScrollingSettingObserver;
    public int mNavigationBarBottomHeight;
    public final NavigationBarController mNavigationBarController;
    public NewNotifReadListener mNewNotifReadListener;
    public float mNextCollapseSpeedUpFactor;
    public NotificationsQuickSettingsContainer mNotificationContainerParent;
    public final NotificationListContainer mNotificationListContainer;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final NotificationStackScrollLayoutController mNotificationStackScrollLayoutController;
    public final NotificationStackSizeCalculator mNotificationStackSizeCalculator;
    public final boolean mNotificationsDragEnabled;
    public final NotificationsQSContainerController mNotificationsQSContainerController;
    public View mNowBarContainer;
    public final NotificationPanelViewController$$ExternalSyntheticLambda8 mOccludedToLockscreenTransition;
    public final OccludedToLockscreenTransitionViewModel mOccludedToLockscreenTransitionViewModel;
    public int mOldLayoutDirection;
    public final ShadeHeadsUpChangedListener mOnHeadsUpChangedListener;
    public boolean mOnlyAffordanceInThisMotion;
    public ShadeControllerImpl.AnonymousClass2 mOpenCloseListener;
    public float mOverExpansion;
    public float mOverStretchAmount;
    public int mPanelAlpha;
    public final AnimatableProperty.AnonymousClass6 mPanelAlphaAnimator;
    public Runnable mPanelAlphaEndAction;
    public final AnimationProperties mPanelAlphaInPropertiesAnimator;
    public final AnimationProperties mPanelAlphaOutPropertiesAnimator;
    public boolean mPanelClosedOnDown;
    public float mPanelFlingOvershootAmount;
    public int mPanelInVisibleReason;
    public final SecPanelSplitHelper mPanelSplitHelper;
    public final AnonymousClass2 mPanelTransitionStateListener;
    public boolean mPanelUpdateWhenAnimatorEnds;
    public final Lazy mPluginAODManagerLazy;
    public PluginLock mPluginLock;
    public final PluginLockData mPluginLockData;
    public final PluginLockMediator mPluginLockMediator;
    public View mPluginLockStarContainer;
    public final Lazy mPluginLockStarManagerLazy;
    public int mPluginLockViewMode;
    public final NotificationPanelViewController$$ExternalSyntheticLambda31 mPostCollapseRunnable;
    public final PowerInteractor mPowerInteractor;
    public final PrimaryBouncerToGoneTransitionViewModel mPrimaryBouncerToGoneTransitionViewModel;
    public final PrivacyDialogController mPrivacyDialogController;
    public final PulseExpansionHandler mPulseExpansionHandler;
    public boolean mPulsing;
    public final KeyguardPunchHoleVIViewController.Factory mPunchHoleVIViewControllerFactory;
    public final QuickSettingsControllerImpl mQsController;
    public boolean mQsExpandedOnTouchDown;
    public final QsStatusEventLog mQsStatusEventLog;
    public final StringBuilder mQuickPanelLogBuilder;
    public final QuickPanelLogger mQuickPanelLogger;
    public final int mQuickQsOffsetHeight;
    public String mRecomputedMaxCountCallStack;
    public String mRecomputedMaxCountNotification;
    public String mRecomputedReason;
    public final Resources mResources;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public final ScrimController mScrimController;
    public KeyguardSecAffordanceHelper mSecAffordanceHelper;
    public final SecNotificationPanelViewController mSecNotificationPanelViewController;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final SettingsChangeObserver mSettingsChangeObserver;
    public final ShadeAnimationInteractor mShadeAnimationInteractor;
    public final ShadeExpansionStateManager mShadeExpansionStateManager;
    public final ShadeHeaderController mShadeHeaderController;
    public final ShadeHeadsUpTrackerImpl mShadeHeadsUpTracker;
    public final ShadeLogger mShadeLog;
    public final ShadeRepository mShadeRepository;
    public final AnonymousClass16 mShadeViewStateProvider;
    public final NotificationShelfManager mShelfManager;
    public int mShortcut;
    public boolean mShowIconsWhenExpanded;
    public float mSlopMultiplier;
    public int mSplitShadeFullTransitionDistance;
    public int mSplitShadeScrimTransitionDistance;
    public final SplitShadeStateController mSplitShadeStateController;
    public int mStackScrollerMeasuringPass;
    public int mStatusBarHeaderHeightKeyguard;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public int mStatusBarMinHeight;
    public final IStatusBarService mStatusBarService;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public final StatusBarStateListener mStatusBarStateListener;
    public final StatusBarTouchableRegionManager mStatusBarTouchableRegionManager;
    public final Lazy mSubScreenManagerLazy;
    public final SysUiState mSysUiState;
    public final SystemClock mSystemClock;
    public final AnonymousClass17 mSystemUIWidgetCallback;
    public final TapAgainViewController mTapAgainViewController;
    Set<Animator> mTestSetOfAnimatorsUsed;
    public boolean mTouchAboveFalsingThreshold;
    public boolean mTouchDisabled;
    public boolean mTouchDownOnHeadsUpPinnded;
    public int mTouchSlop;
    public boolean mTouchSlopExceeded;
    public boolean mTouchSlopExceededBeforeDown;
    public boolean mTouchStartedInEmptyArea;
    public ExpandableNotificationRow mTrackedHeadsUpNotification;
    public int mTrackingPointer;
    public ShadeControllerImpl$$ExternalSyntheticLambda4 mTrackingStartedListener;
    public float mUdfpsMaxYBurnInOffset;
    public final UnlockedScreenOffAnimationController mUnlockedScreenOffAnimationController;
    public boolean mUpdateFlingOnLayout;
    public float mUpdateFlingVelocity;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public boolean mUpwardsWhenThresholdReached;
    public boolean mUseExternalTouch;
    public final UserManager mUserManager;
    public boolean mUserSetupComplete;
    public final boolean mVibrateOnOpening;
    public final VibratorHelper mVibratorHelper;
    public final NotificationPanelView mView;
    public String mViewName;
    public final NotificationWakeUpCoordinator mWakeUpCoordinator;
    public final WallpaperImageInjectCreator mWallpaperImageCreator;
    public boolean mWillPlayDelayedDozeAmountAnimation;
    public boolean shouldScrollViewIntercept;
    public static final Rect M_DUMMY_DIRTY_RECT = new Rect(0, 0, 1, 1);
    public static final Rect EMPTY_RECT = new Rect();
    public int mLastCameraLaunchSource = 3;
    public final NotificationPanelViewController$$ExternalSyntheticLambda7 mOnEmptySpaceClickListener = new NotificationPanelViewController$$ExternalSyntheticLambda7(this);
    public final VelocityTracker mVelocityTracker = VelocityTracker.obtain();
    public final NotificationPanelViewController$$ExternalSyntheticLambda17 mFalsingTapListener = new FalsingManager.FalsingTapListener() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda17
        @Override // com.android.systemui.plugins.FalsingManager.FalsingTapListener
        public final void onAdditionalTapRequired() {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) notificationPanelViewController.mStatusBarStateController;
            if (statusBarStateControllerImpl.mState == 2) {
                notificationPanelViewController.mTapAgainViewController.show();
            } else {
                notificationPanelViewController.mKeyguardIndicationController.showTransientIndication(R.string.notification_tap_again);
            }
            if (statusBarStateControllerImpl.mIsDozing) {
                return;
            }
            notificationPanelViewController.mVibratorHelper.getClass();
            notificationPanelViewController.mView.performHapticFeedback(17);
        }
    };
    public final TouchHandler mTouchHandler = new TouchHandler();
    public float mExpandedHeight = 0.0f;
    public int mDisplayTopInset = 0;
    public int mDisplayRightInset = 0;
    public int mDisplayLeftInset = 0;
    public int mNotiCardCount = -1;
    public boolean mIsLockStarOnTouchDown = false;
    public final AnonymousClass1 mLockStarCallback = new PluginLockStarManager.LockStarCallback() { // from class: com.android.systemui.shade.NotificationPanelViewController.1
        @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
        public final void onChangedLockStarData(boolean z) {
            Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
            SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0.m("onChangedLockStarData: ", "NotificationPanelView", z);
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.mLockStarEnabled = z;
            notificationPanelViewController.updateLockStarContainer();
        }

        @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
        public final Bundle request(Bundle bundle) {
            return new Bundle();
        }
    };
    public final KeyguardClockPositionAlgorithm.Result mClockPositionResult = new KeyguardClockPositionAlgorithm.Result();
    public final ShadeFoldAnimatorImpl mShadeFoldAnimator = new ShadeFoldAnimatorImpl();
    public final ArrayList mTrackingHeadsUpListeners = new ArrayList();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.shade.NotificationPanelViewController$10, reason: invalid class name */
    public final class AnonymousClass10 {
        public AnonymousClass10() {
        }

        public final void setFullScreenMode(final boolean z, long j, Animator.AnimatorListener animatorListener) {
            final NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.getClass();
            Log.d("NotificationPanelView", "setFullScreenMode() enabled = " + z + ", duration = " + j + ", listener = " + animatorListener);
            notificationPanelViewController.mFullScreenModeEnabled = z;
            notificationPanelViewController.cancelAnimation();
            NotificationPanelView notificationPanelView = notificationPanelViewController.mView;
            notificationPanelView.setVisibility(0);
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
                notificationPanelView.animate().alpha(f).setDuration(j).setInterpolator(Interpolators.ACCELERATE).setListener(animatorListener).withEndAction(new Runnable() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda59
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                        boolean z2 = z;
                        NotificationPanelView notificationPanelView2 = notificationPanelViewController2.mView;
                        if (z2) {
                            notificationPanelView2.setVisibility(4);
                        } else {
                            notificationPanelView2.setVisibility(0);
                        }
                    }
                }).withLayer();
            }
            notificationPanelViewController.mUpdateMonitor.setFaceWidgetFullScreenMode(z);
            if (z) {
                notificationPanelViewController.mKeyguardTouchAnimator.reset(false);
            }
            if (z) {
                ((CentralSurfacesImpl) notificationPanelViewController.mCentralSurfaces).getNotificationShadeWindowViewController().mView.getWindowInsetsController().setAnimationsDisabled(z);
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.shade.NotificationPanelViewController$9, reason: invalid class name */
    public final class AnonymousClass9 {
        public AnonymousClass9() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ConfigurationListener implements ConfigurationController.ConfigurationListener {
        public /* synthetic */ ConfigurationListener(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onConfigChanged(Configuration configuration) {
            MultiWindowEdgeDetector multiWindowEdgeDetector = NotificationPanelViewController.this.mMultiWindowEdgeDetector;
            if (multiWindowEdgeDetector != null) {
                multiWindowEdgeDetector.onConfigurationChanged();
            }
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDensityOrFontScaleChanged() {
            Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.getClass();
            notificationPanelViewController.reInflateViews();
            KeyguardTouchAnimator keyguardTouchAnimator = notificationPanelViewController.mKeyguardTouchAnimator;
            if (keyguardTouchAnimator != null) {
                keyguardTouchAnimator.initDimens$5();
            }
            if (CscRune.KEYGUARD_DCM_LIVE_UX) {
                DcmMascotViewContainer dcmMascotViewContainer = notificationPanelViewController.mMascotViewContainer;
                dcmMascotViewContainer.updateRes();
                ViewGroup.LayoutParams layoutParams = dcmMascotViewContainer.getLayoutParams();
                layoutParams.height = dcmMascotViewContainer.mascotHeight;
                dcmMascotViewContainer.setLayoutParams(layoutParams);
            }
            ((KeyguardEditModeControllerImpl) notificationPanelViewController.mKeyguardEditModeController).refreshRadius();
            PluginLockMediator pluginLockMediator = notificationPanelViewController.mPluginLockMediator;
            if (pluginLockMediator != null) {
                pluginLockMediator.onDensityOrFontScaleChanged();
            }
            KeyguardBottomAreaViewController keyguardBottomAreaViewController = notificationPanelViewController.mKeyguardBottomAreaViewController;
            if (keyguardBottomAreaViewController != null) {
                keyguardBottomAreaViewController.onDensityOrFontScaleChanged();
            }
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onSmallestScreenWidthChanged() {
            Trace.beginSection("onSmallestScreenWidthChanged");
            Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
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
            Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.getClass();
            notificationPanelViewController.reInflateViews();
        }

        private ConfigurationListener() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class HeadsUpNotificationViewControllerImpl implements HeadsUpTouchHelper.HeadsUpNotificationViewController {
        public /* synthetic */ HeadsUpNotificationViewControllerImpl(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        private HeadsUpNotificationViewControllerImpl() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class KeyguardAffordanceHelperCallback implements KeyguardSecAffordanceHelper.Callback {
        public /* synthetic */ KeyguardAffordanceHelperCallback(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        private KeyguardAffordanceHelperCallback() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface NewNotifReadListener {
        void onNewNotificationRead();
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class NsslHeightChangedListener implements ExpandableView.OnHeightChangedListener {
        public /* synthetic */ NsslHeightChangedListener(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.notification.row.ExpandableView.OnHeightChangedListener
        public final void onHeightChanged(ExpandableView expandableView, boolean z) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            if (expandableView == null && notificationPanelViewController.mQsController.getExpanded$1()) {
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
            if (notificationPanelViewController.isKeyguardShowing$2()) {
                notificationPanelViewController.updateMaxDisplayedNotifications(true);
                notificationPanelViewController.mRecomputedMaxCountCallStack = "onHeightChanged";
            }
            notificationPanelViewController.updateExpandedHeightToMaxHeight();
        }

        private NsslHeightChangedListener() {
        }

        @Override // com.android.systemui.statusbar.notification.row.ExpandableView.OnHeightChangedListener
        public final void onReset(ExpandableView expandableView) {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SettingsChangeObserver extends ContentObserver {
        public SettingsChangeObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
            notificationPanelViewController.getClass();
            NotificationPanelViewController.this.reInflateViews();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ShadeAttachStateChangeListener implements View.OnAttachStateChangeListener {
        public /* synthetic */ ShadeAttachStateChangeListener(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
            SecHideNotificationShadeInMirrorInteractor secHideNotificationShadeInMirrorInteractor;
            final int i = 0;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            FragmentHostManager fragmentHostManager = notificationPanelViewController.mFragmentService.getFragmentHostManager(notificationPanelViewController.mView);
            QuickSettingsControllerImpl quickSettingsControllerImpl = NotificationPanelViewController.this.mQsController;
            quickSettingsControllerImpl.getClass();
            fragmentHostManager.addTagListener(QS.TAG, quickSettingsControllerImpl.new QsFragmentListener());
            int i2 = SceneContainerFlag.$r8$clinit;
            Flags.sceneContainer();
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
            notificationPanelViewController6.mContentResolver.registerContentObserver(Settings.Global.getUriFor(SettingsHelper.INDEX_USER_SWITCHER_ENABLED), false, notificationPanelViewController6.mSettingsChangeObserver);
            boolean z = QpRune.QUICK_DATA_USAGE_LABEL;
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
                ((Handler) Dependency.sDependency.getDependencyInner(dependencyKey)).post(new Runnable() { // from class: com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i3 = i;
                        DataUsageLabelManager dataUsageLabelManager2 = dataUsageLabelManager;
                        switch (i3) {
                            case 0:
                                dataUsageLabelManager2.onPanelConfigurationChanged(dataUsageLabelManager2.mContext.getResources().getConfiguration());
                                break;
                            default:
                                dataUsageLabelManager2.updateLabelViewColor();
                                break;
                        }
                    }
                });
                final int i3 = 1;
                ((Handler) Dependency.sDependency.getDependencyInner(dependencyKey)).postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i32 = i3;
                        DataUsageLabelManager dataUsageLabelManager2 = dataUsageLabelManager;
                        switch (i32) {
                            case 0:
                                dataUsageLabelManager2.onPanelConfigurationChanged(dataUsageLabelManager2.mContext.getResources().getConfiguration());
                                break;
                            default:
                                dataUsageLabelManager2.updateLabelViewColor();
                                break;
                        }
                    }
                }, 10000L);
                dataUsageLabelManager.mNavSettingsHelper.onAttachedToWindow();
                DataUsageLabelManager.QuickStarHelper quickStarHelper = dataUsageLabelManager.mQuickStarHelper;
                ((SlimIndicatorViewMediatorImpl) quickStarHelper.mSlimIndicatorViewMediator).registerSubscriber("DataUsageLabelManager", DataUsageLabelManager.this.mQuickStarHelper);
            }
            NotificationPanelViewController notificationPanelViewController7 = NotificationPanelViewController.this;
            if (notificationPanelViewController7.mBarState != ((StatusBarStateControllerImpl) notificationPanelViewController7.mStatusBarStateController).mState) {
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                StringBuilder sb = new StringBuilder("panel mBarState ");
                sb.append(NotificationPanelViewController.this.mBarState);
                sb.append("/ StatusBarStateController.getState() ");
                RecyclerView$$ExternalSyntheticOutline0.m(((StatusBarStateControllerImpl) NotificationPanelViewController.this.mStatusBarStateController).mState, "NotificationPanelView", sb);
                NotificationPanelViewController notificationPanelViewController8 = NotificationPanelViewController.this;
                notificationPanelViewController8.mStatusBarStateListener.onStateChanged(((StatusBarStateControllerImpl) notificationPanelViewController8.mStatusBarStateController).mState);
            }
            IndicatorTouchHandler indicatorTouchHandler = NotificationPanelViewController.this.mIndicatorTouchHandler;
            indicatorTouchHandler.ongoingCallController.addCallback((OngoingCallListener) indicatorTouchHandler.ongoingCallListener);
            SecNotificationPanelViewController secNotificationPanelViewController = NotificationPanelViewController.this.mSecNotificationPanelViewController;
            if (secNotificationPanelViewController != null && (secHideNotificationShadeInMirrorInteractor = secNotificationPanelViewController.hideNotificationShadeInMirrorInteractor) != null) {
                secHideNotificationShadeInMirrorInteractor.setup();
            }
            KeyguardEditModeController keyguardEditModeController = NotificationPanelViewController.this.mKeyguardEditModeController;
            if (keyguardEditModeController != null) {
                KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = (KeyguardEditModeControllerImpl) keyguardEditModeController;
                if (!SafeUIState.isSysUiSafeModeEnabled()) {
                    keyguardEditModeControllerImpl.wakefulnessLifecycle.addObserver(keyguardEditModeControllerImpl.wakefulnessLifecycleObserver);
                    keyguardEditModeControllerImpl.displayLifecycle.addObserver(keyguardEditModeControllerImpl.displayLifecycleObserver);
                }
                keyguardEditModeControllerImpl.keyguardUpdateMonitor.registerCallback(keyguardEditModeControllerImpl.keyguardUpdateMonitorCallback);
            }
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewDetachedFromWindow(View view) {
            SecHideNotificationShadeInMirrorInteractor secHideNotificationShadeInMirrorInteractor;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.mContentResolver.unregisterContentObserver(notificationPanelViewController.mSettingsChangeObserver);
            NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
            FragmentHostManager fragmentHostManager = notificationPanelViewController2.mFragmentService.getFragmentHostManager(notificationPanelViewController2.mView);
            QuickSettingsControllerImpl quickSettingsControllerImpl = NotificationPanelViewController.this.mQsController;
            quickSettingsControllerImpl.getClass();
            QuickSettingsControllerImpl.QsFragmentListener qsFragmentListener = quickSettingsControllerImpl.new QsFragmentListener();
            ArrayList arrayList = (ArrayList) fragmentHostManager.mListeners.get(QS.TAG);
            if (arrayList != null && arrayList.remove(qsFragmentListener) && arrayList.size() == 0) {
                fragmentHostManager.mListeners.remove(QS.TAG);
            }
            NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
            ((StatusBarStateControllerImpl) notificationPanelViewController3.mStatusBarStateController).removeCallback((StatusBarStateController.StateListener) notificationPanelViewController3.mStatusBarStateListener);
            NotificationPanelViewController notificationPanelViewController4 = NotificationPanelViewController.this;
            ((ConfigurationControllerImpl) notificationPanelViewController4.mConfigurationController).removeCallback(notificationPanelViewController4.mConfigurationListener);
            NotificationPanelViewController notificationPanelViewController5 = NotificationPanelViewController.this;
            notificationPanelViewController5.mFalsingManager.removeTapListener(notificationPanelViewController5.mFalsingTapListener);
            if (QpRune.QUICK_DATA_USAGE_LABEL) {
                DataUsageLabelManager dataUsageLabelManager = (DataUsageLabelManager) NotificationPanelViewController.this.mDataUsageLabelManagerLazy.get();
                DataUsageLabelParent dataUsageLabelParent = dataUsageLabelManager.mDataUsageLabelParent;
                if (dataUsageLabelParent != null && dataUsageLabelParent.getParentViewGroup() != null) {
                    dataUsageLabelParent.getParentViewGroup().removeAllViews();
                }
                dataUsageLabelManager.mNavSettingsHelper.onDetachedFromWindow();
                ((SlimIndicatorViewMediatorImpl) dataUsageLabelManager.mQuickStarHelper.mSlimIndicatorViewMediator).unregisterSubscriber("DataUsageLabelManager");
            }
            IndicatorTouchHandler indicatorTouchHandler = NotificationPanelViewController.this.mIndicatorTouchHandler;
            indicatorTouchHandler.ongoingCallController.removeCallback((OngoingCallListener) indicatorTouchHandler.ongoingCallListener);
            SecNotificationPanelViewController secNotificationPanelViewController = NotificationPanelViewController.this.mSecNotificationPanelViewController;
            if (secNotificationPanelViewController != null && (secHideNotificationShadeInMirrorInteractor = secNotificationPanelViewController.hideNotificationShadeInMirrorInteractor) != null) {
                secHideNotificationShadeInMirrorInteractor.tearDown();
            }
            KeyguardEditModeController keyguardEditModeController = NotificationPanelViewController.this.mKeyguardEditModeController;
            if (keyguardEditModeController != null) {
                KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = (KeyguardEditModeControllerImpl) keyguardEditModeController;
                if (!SafeUIState.isSysUiSafeModeEnabled()) {
                    keyguardEditModeControllerImpl.wakefulnessLifecycle.removeObserver(keyguardEditModeControllerImpl.wakefulnessLifecycleObserver);
                    keyguardEditModeControllerImpl.displayLifecycle.removeObserver(keyguardEditModeControllerImpl.displayLifecycleObserver);
                }
                keyguardEditModeControllerImpl.keyguardUpdateMonitor.removeCallback(keyguardEditModeControllerImpl.keyguardUpdateMonitorCallback);
            }
        }

        private ShadeAttachStateChangeListener() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ShadeFoldAnimatorImpl implements ShadeFoldAnimator {
        public ShadeFoldAnimatorImpl() {
        }

        public final ViewPropertyAnimator buildViewAnimator(final FoldAodAnimationController$startAnimationRunnable$1.AnonymousClass1 anonymousClass1, final FoldAodAnimationController$startAnimationRunnable$1.AnonymousClass2 anonymousClass2, final FoldAodAnimationController$startAnimationRunnable$1.AnonymousClass3 anonymousClass3) {
            final ViewPropertyAnimator animate = NotificationPanelViewController.this.mView.animate();
            animate.cancel();
            return animate.translationX(0.0f).alpha(1.0f).setDuration(600L).setInterpolator(Interpolators.EMPHASIZED_DECELERATE).setListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.shade.NotificationPanelViewController.ShadeFoldAnimatorImpl.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    anonymousClass3.run();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    anonymousClass2.run();
                    animate.setListener(null);
                    animate.setUpdateListener(null);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    anonymousClass1.run();
                }
            });
        }

        @Override // com.android.systemui.shade.ShadeFoldAnimator
        public final void cancelFoldToAodAnimation() {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.cancelAnimation();
            notificationPanelViewController.resetAlpha();
            notificationPanelViewController.resetTranslation();
        }

        @Override // com.android.systemui.shade.ShadeFoldAnimator
        public final ViewGroup getView() {
            return NotificationPanelViewController.this.mView;
        }

        @Override // com.android.systemui.shade.ShadeFoldAnimator
        public final void prepareFoldToAodAnimation() {
            MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
            Flags.migrateClocksToBlueprint();
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.showAodUi();
            NotificationPanelView notificationPanelView = notificationPanelViewController.mView;
            notificationPanelView.setTranslationX(-notificationPanelView.getResources().getDimensionPixelSize(R.dimen.below_clock_padding_start));
            notificationPanelView.setAlpha(0.0f);
        }

        @Override // com.android.systemui.shade.ShadeFoldAnimator
        public final void startFoldToAodAnimation(FoldAodAnimationController$startAnimationRunnable$1.AnonymousClass1 anonymousClass1, FoldAodAnimationController$startAnimationRunnable$1.AnonymousClass2 anonymousClass2, FoldAodAnimationController$startAnimationRunnable$1.AnonymousClass3 anonymousClass3) {
            MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
            Flags.migrateClocksToBlueprint();
            buildViewAnimator(anonymousClass1, anonymousClass2, anonymousClass3).setUpdateListener(new NotificationPanelViewController$$ExternalSyntheticLambda20(this, 2)).start();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ShadeHeadsUpChangedListener implements OnHeadsUpChangedListener {
        public /* synthetic */ ShadeHeadsUpChangedListener(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
        public final void onHeadsUpPinned(NotificationEntry notificationEntry) {
            NotificationsHeadsUpRefactor.assertInLegacyMode();
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            if (notificationPanelViewController.isKeyguardShowing$2()) {
                return;
            }
            NotificationStackScrollLayout notificationStackScrollLayout = notificationPanelViewController.mNotificationStackScrollLayoutController.mView;
            notificationStackScrollLayout.getClass();
            NotificationsHeadsUpRefactor.assertInLegacyMode();
            notificationStackScrollLayout.generateHeadsUpAnimation(notificationEntry.row, true);
        }

        @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
        public final void onHeadsUpPinnedModeChanged(boolean z) {
            NotificationsHeadsUpRefactor.assertInLegacyMode();
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            if (z) {
                notificationPanelViewController.mHeadsUpExistenceChangedRunnable.run();
                notificationPanelViewController.updateNotificationTranslucency();
            } else {
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                notificationPanelViewController.getClass();
                NotificationsHeadsUpRefactor.assertInLegacyMode();
                notificationPanelViewController.mHeadsUpAnimatingAway = true;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
                notificationStackScrollLayoutController.getClass();
                NotificationsHeadsUpRefactor.assertInLegacyMode();
                notificationStackScrollLayoutController.mView.setHeadsUpAnimatingAway(true);
                notificationPanelViewController.updateVisibility$6();
                notificationStackScrollLayoutController.mView.mAnimationFinishedRunnables.add(notificationPanelViewController.mHeadsUpExistenceChangedRunnable);
            }
            Rect rect2 = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
            notificationPanelViewController.updateGestureExclusionRect();
            notificationPanelViewController.mHeadsUpPinnedMode = z;
            notificationPanelViewController.updateVisibility$6();
            KeyguardStatusBarViewController keyguardStatusBarViewController = notificationPanelViewController.mKeyguardStatusBarViewController;
            keyguardStatusBarViewController.getClass();
            SceneContainerFlag.assertInLegacyMode();
            keyguardStatusBarViewController.updateForHeadsUp(true);
        }

        @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
        public final void onHeadsUpUnPinned(NotificationEntry notificationEntry) {
            ExpandableNotificationRow expandableNotificationRow;
            NotificationsHeadsUpRefactor.assertInLegacyMode();
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            if (!notificationPanelViewController.isFullyCollapsed() || (expandableNotificationRow = notificationEntry.row) == null || !expandableNotificationRow.mIsHeadsUp || notificationPanelViewController.isKeyguardShowing$2()) {
                return;
            }
            NotificationStackScrollLayout notificationStackScrollLayout = notificationPanelViewController.mNotificationStackScrollLayoutController.mView;
            notificationStackScrollLayout.getClass();
            NotificationsHeadsUpRefactor.assertInLegacyMode();
            notificationStackScrollLayout.generateHeadsUpAnimation(notificationEntry.row, false);
            ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
            if (expandableNotificationRow2 != null) {
                expandableNotificationRow2.mMustStayOnScreen = false;
            }
        }

        private ShadeHeadsUpChangedListener() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ShadeHeadsUpTrackerImpl implements ShadeHeadsUpTracker {
        public /* synthetic */ ShadeHeadsUpTrackerImpl(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        @Override // com.android.systemui.shade.ShadeHeadsUpTracker
        public final void addTrackingHeadsUpListener(Consumer consumer) {
            NotificationPanelViewController.this.mTrackingHeadsUpListeners.add(consumer);
        }

        @Override // com.android.systemui.shade.ShadeHeadsUpTracker
        public final ExpandableNotificationRow getTrackedHeadsUpNotification() {
            return NotificationPanelViewController.this.mTrackedHeadsUpNotification;
        }

        @Override // com.android.systemui.shade.ShadeHeadsUpTracker
        public final void removeTrackingHeadsUpListener(HeadsUpAppearanceController$$ExternalSyntheticLambda0 headsUpAppearanceController$$ExternalSyntheticLambda0) {
            NotificationPanelViewController.this.mTrackingHeadsUpListeners.remove(headsUpAppearanceController$$ExternalSyntheticLambda0);
        }

        @Override // com.android.systemui.shade.ShadeHeadsUpTracker
        public final void setHeadsUpAppearanceController(HeadsUpAppearanceController headsUpAppearanceController) {
            NotificationPanelViewController.this.mHeadsUpAppearanceController = headsUpAppearanceController;
        }

        private ShadeHeadsUpTrackerImpl() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ShadeLayoutChangeListener implements View.OnLayoutChangeListener {
        public /* synthetic */ ShadeLayoutChangeListener(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        @Override // android.view.View.OnLayoutChangeListener
        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            int i9 = 1;
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
            NotificationPanelViewController.this.updateMaxDisplayedNotifications(!r3.mUnlockedScreenOffAnimationController.isAnimationPlaying());
            NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
            notificationPanelViewController3.mRecomputedMaxCountCallStack = "onLayoutChange";
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController3.mNotificationStackScrollLayoutController;
            boolean z = ((float) notificationStackScrollLayoutController.mView.getWidth()) == ((float) NotificationPanelViewController.this.mView.getWidth());
            notificationPanelViewController3.mIsFullWidth = z;
            notificationPanelViewController3.mScrimController.getClass();
            notificationStackScrollLayoutController.mView.mAmbientState.mIsSmallScreen = z;
            QuickSettingsControllerImpl quickSettingsControllerImpl = notificationPanelViewController3.mQsController;
            quickSettingsControllerImpl.mIsFullWidth = z;
            QS qs = quickSettingsControllerImpl.mQs;
            if (qs != null) {
                qs.setIsNotificationPanelFullWidth(z);
            }
            MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
            Flags.migrateClocksToBlueprint();
            QuickSettingsControllerImpl quickSettingsControllerImpl2 = NotificationPanelViewController.this.mQsController;
            int i10 = quickSettingsControllerImpl2.mMaxExpansionHeight;
            if (quickSettingsControllerImpl2.isQsFragmentCreated()) {
                quickSettingsControllerImpl2.updateMinHeight();
                int desiredHeight = quickSettingsControllerImpl2.mQs.getDesiredHeight();
                quickSettingsControllerImpl2.mMaxExpansionHeight = desiredHeight;
                quickSettingsControllerImpl2.mNotificationStackScrollLayoutController.mView.mMaxTopPadding = desiredHeight;
            }
            NotificationPanelViewController.this.positionClockAndNotifications(false);
            final QuickSettingsControllerImpl quickSettingsControllerImpl3 = NotificationPanelViewController.this.mQsController;
            if (quickSettingsControllerImpl3.getExpanded$1() && quickSettingsControllerImpl3.mFullyExpanded) {
                quickSettingsControllerImpl3.mExpansionHeight = quickSettingsControllerImpl3.mMaxExpansionHeight;
                NotificationPanelViewController$$ExternalSyntheticLambda7 notificationPanelViewController$$ExternalSyntheticLambda7 = quickSettingsControllerImpl3.mExpansionHeightSetToMaxListener;
                if (notificationPanelViewController$$ExternalSyntheticLambda7 != null) {
                    notificationPanelViewController$$ExternalSyntheticLambda7.onExpansionHeightSetToMax(true);
                }
                int i11 = quickSettingsControllerImpl3.mMaxExpansionHeight;
                if (i11 != i10) {
                    ValueAnimator valueAnimator = quickSettingsControllerImpl3.mSizeChangeAnimator;
                    if (valueAnimator != null) {
                        i10 = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        quickSettingsControllerImpl3.mSizeChangeAnimator.cancel();
                    }
                    ValueAnimator ofInt = ValueAnimator.ofInt(i10, i11);
                    quickSettingsControllerImpl3.mSizeChangeAnimator = ofInt;
                    ofInt.setDuration(300L);
                    quickSettingsControllerImpl3.mSizeChangeAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                    quickSettingsControllerImpl3.mSizeChangeAnimator.addUpdateListener(new QuickSettingsControllerImpl$$ExternalSyntheticLambda32(quickSettingsControllerImpl3, i9));
                    quickSettingsControllerImpl3.mSizeChangeAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl.1
                        public AnonymousClass1() {
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            QuickSettingsControllerImpl.this.mSizeChangeAnimator = null;
                        }
                    });
                    quickSettingsControllerImpl3.mSizeChangeAnimator.start();
                }
            } else if (quickSettingsControllerImpl3.getExpanded$1() || quickSettingsControllerImpl3.mExpansionAnimator != null) {
                quickSettingsControllerImpl3.mShadeLog.v("onLayoutChange: qs expansion not set");
            } else {
                quickSettingsControllerImpl3.setExpansionHeight(quickSettingsControllerImpl3.mMinExpansionHeight + quickSettingsControllerImpl3.mLastOverscroll);
            }
            NotificationPanelViewController notificationPanelViewController4 = NotificationPanelViewController.this;
            notificationPanelViewController4.updateExpandedHeight(notificationPanelViewController4.mExpandedHeight);
            NotificationPanelViewController notificationPanelViewController5 = NotificationPanelViewController.this;
            if (notificationPanelViewController5.mBarState == 1) {
                notificationPanelViewController5.mKeyguardStatusBarViewController.updateViewState();
            }
            notificationPanelViewController5.mQsController.updateExpansion();
            QuickSettingsControllerImpl quickSettingsControllerImpl4 = NotificationPanelViewController.this.mQsController;
            if (quickSettingsControllerImpl4.mSizeChangeAnimator != null && quickSettingsControllerImpl4.isQsFragmentCreated()) {
                QS qs2 = quickSettingsControllerImpl4.mQs;
                qs2.setHeightOverride(qs2.getDesiredHeight());
            }
            NotificationPanelViewController.this.updateMaxHeadsUpTranslation();
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class StatusBarStateListener implements StatusBarStateController.StateListener {
        public /* synthetic */ StatusBarStateListener(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onDozeAmountChanged(float f, float f2) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.mInterpolatedDarkAmount = f2;
            notificationPanelViewController.mLinearDarkAmount = f;
            PluginFaceWidgetManager pluginFaceWidgetManager = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
            if (pluginFaceWidgetManager == null) {
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
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

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStateChanged(int i) {
            NotificationStackScrollLayout notificationStackScrollLayout;
            int i2;
            long j;
            long j2;
            QS qs;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            boolean goingToFullShade = ((StatusBarStateControllerImpl) notificationPanelViewController.mStatusBarStateController).goingToFullShade();
            KeyguardStateControllerImpl keyguardStateControllerImpl = notificationPanelViewController.mKeyguardStateController;
            boolean z = keyguardStateControllerImpl.mKeyguardFadingAway;
            int i3 = notificationPanelViewController.mBarState;
            boolean z2 = i == 1;
            if (notificationPanelViewController.mDozeParameters.mScreenOffAnimationController.shouldDelayKeyguardShow() && i3 == 0 && i == 1) {
                MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
                Flags.migrateClocksToBlueprint();
                KeyguardStatusViewController keyguardStatusViewController = notificationPanelViewController.mKeyguardStatusViewController;
                KeyguardClockPositionAlgorithm.Result result = notificationPanelViewController.mClockPositionResult;
                keyguardStatusViewController.updatePosition(result.clockX, result.clockYFullyDozing, result.clockScale, false, result.contentsContainerPosition);
            }
            MigrateClocksToBlueprint migrateClocksToBlueprint2 = MigrateClocksToBlueprint.INSTANCE;
            Flags.migrateClocksToBlueprint();
            notificationPanelViewController.mKeyguardStatusViewController.setKeyguardStatusViewVisibility(i, notificationPanelViewController.mBarState, z, goingToFullShade);
            KeyguardBottomAreaRefactor keyguardBottomAreaRefactor = KeyguardBottomAreaRefactor.INSTANCE;
            Flags.keyguardBottomAreaRefactor();
            notificationPanelViewController.setKeyguardBottomAreaVisibility(i, goingToFullShade);
            notificationPanelViewController.mBarState = i;
            QuickSettingsControllerImpl quickSettingsControllerImpl = notificationPanelViewController.mQsController;
            quickSettingsControllerImpl.mBarState = i;
            SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = quickSettingsControllerImpl.mSecQuickSettingsControllerImpl;
            if (secQuickSettingsControllerImpl != null) {
                ((SecQSPanelResourcePicker) secQuickSettingsControllerImpl.resourcePicker$delegate.getValue()).resourcePickHelper.getTargetPicker().getClass();
                if (i == 2 && (qs = quickSettingsControllerImpl.mQs) != null) {
                    qs.setListening(true);
                }
            }
            NotificationPanelView notificationPanelView = notificationPanelViewController.mView;
            if (i3 == 1 && (goingToFullShade || i == 2)) {
                if (keyguardStateControllerImpl.mKeyguardFadingAway) {
                    j = keyguardStateControllerImpl.mKeyguardFadingAwayDelay;
                    keyguardStateControllerImpl.getClass();
                    j2 = keyguardStateControllerImpl.mKeyguardFadingAwayDuration / 2;
                } else {
                    j = 0;
                    j2 = 360;
                }
                notificationPanelViewController.mKeyguardStatusBarViewController.animateKeyguardStatusBarOut(j, j2);
                quickSettingsControllerImpl.updateMinHeight();
                quickSettingsControllerImpl.updateNightMode(notificationPanelView.getVisibility());
                if (CscRune.KEYGUARD_DCM_LIVE_UX) {
                    notificationPanelViewController.mMascotViewContainer.setMascotViewVisible(4);
                }
            } else {
                KeyguardTouchAnimator keyguardTouchAnimator = notificationPanelViewController.mKeyguardTouchAnimator;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
                if (i3 == 2 && i == 1) {
                    notificationPanelViewController.mKeyguardStatusBarViewController.animateKeyguardStatusBarIn();
                    notificationStackScrollLayoutController.mView.resetScrollPosition();
                    quickSettingsControllerImpl.updateNightMode(notificationPanelView.getVisibility());
                    keyguardTouchAnimator.reset(false);
                    if (CscRune.KEYGUARD_DCM_LIVE_UX) {
                        notificationPanelViewController.mMascotViewContainer.setMascotViewVisible(notificationPanelViewController.mDozing ? 8 : 0);
                    }
                } else {
                    if (i3 != 0 || i != 1 || !notificationPanelViewController.mScreenOffAnimationController.isKeyguardShowDelayed()) {
                        ShadeLogger shadeLogger = notificationPanelViewController.mShadeLog;
                        if (z2) {
                            shadeLogger.v("Updating keyguard status bar state to visible");
                        } else {
                            shadeLogger.v("Updating keyguard status bar state to invisible");
                        }
                        notificationPanelViewController.mKeyguardStatusBarViewController.updateViewState(1.0f, z2 ? 0 : 4);
                    }
                    if (z2 && i3 != notificationPanelViewController.mBarState) {
                        QS qs2 = quickSettingsControllerImpl.mQs;
                        if (qs2 != null) {
                            qs2.hideImmediately();
                        }
                        if (((KeyguardEditModeControllerImpl) notificationPanelViewController.mKeyguardEditModeController).isEditMode) {
                            KeyguardEditModeAnimatorController keyguardEditModeAnimatorController = keyguardTouchAnimator.editModeAnimatorController;
                            Log.d("KeyguardEditModeAnimatorController", "dismissEditActivity " + keyguardEditModeAnimatorController.isEditMode());
                            if (keyguardEditModeAnimatorController.isEditMode()) {
                                keyguardEditModeAnimatorController.animate(false);
                            }
                        }
                    } else if (i3 == 1 && i == 0) {
                        notificationPanelViewController.cancelHeightAnimator();
                    }
                    if (i3 == 1 && i == 0 && (notificationStackScrollLayout = notificationStackScrollLayoutController.mView) != null) {
                        HashSet hashSet = notificationStackScrollLayout.mAnimationFinishedRunnables;
                        NotificationPanelViewController$$ExternalSyntheticLambda31 notificationPanelViewController$$ExternalSyntheticLambda31 = notificationPanelViewController.mHeadsUpExistenceChangedRunnable;
                        if (hashSet.contains(notificationPanelViewController$$ExternalSyntheticLambda31)) {
                            notificationPanelViewController$$ExternalSyntheticLambda31.run();
                        }
                    }
                }
            }
            KeyguardStatusBarViewController keyguardStatusBarViewController = notificationPanelViewController.mKeyguardStatusBarViewController;
            keyguardStatusBarViewController.getClass();
            SceneContainerFlag.assertInLegacyMode();
            keyguardStatusBarViewController.updateForHeadsUp(true);
            if (z2) {
                notificationPanelViewController.updateDozingVisibilities(false);
            }
            notificationPanelViewController.updateMaxDisplayedNotifications(false);
            notificationPanelViewController.mRecomputedMaxCountCallStack = "onStateChanged";
            notificationPanelViewController.maybeAnimateBottomAreaAlpha();
            quickSettingsControllerImpl.updateQsState$1$1();
            notificationPanelViewController.onBarStateChanged(notificationPanelViewController.mBarState);
            if (i3 == i && i == 1 && (i2 = notificationPanelViewController.mPluginLockViewMode) != 0) {
                notificationPanelViewController.setViewMode(i2);
            }
            if (notificationPanelViewController.mBarState == 0 && notificationPanelViewController.mPluginLockMediator.isWindowSecured()) {
                notificationPanelViewController.mPluginLockMediator.updateWindowSecureState(false);
            }
            View view = notificationPanelViewController.mPluginLockStarContainer;
            if (view != null) {
                view.setVisibility(notificationPanelViewController.mBarState == 1 ? 0 : 8);
            }
            SecNotificationPanelViewController secNotificationPanelViewController = notificationPanelViewController.mSecNotificationPanelViewController;
            if (secNotificationPanelViewController != null) {
                SecPanelSplitHelper.Companion.getClass();
                if (SecPanelSplitHelper.isEnabled) {
                    if (i == 1) {
                        SecPanelSplitHelper secPanelSplitHelper = secNotificationPanelViewController.panelSplitHelper;
                        if (secPanelSplitHelper != null) {
                            secPanelSplitHelper.slide$1(1);
                        }
                    } else if (i == 2) {
                        secNotificationPanelViewController.quickSettingsController.setExpansionHeight(r2.mMaxExpansionHeight);
                    }
                }
            }
            if (i == 0 && notificationPanelViewController.mInstantExpanding) {
                notificationPanelViewController.mInstantExpanding = false;
            }
        }

        private StatusBarStateListener() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TouchHandler implements View.OnTouchListener, Gefingerpoken {
        public long mLastTouchDownTime = -1;

        public TouchHandler() {
        }

        public final boolean handleTouch$1(MotionEvent motionEvent) {
            boolean z;
            StringBuilder sb;
            int pointerId;
            QuickPanelLogger quickPanelLogger = NotificationPanelViewController.this.mQuickPanelLogger;
            if (quickPanelLogger != null) {
                quickPanelLogger.quickPanelLoggerHelper.handleTouchLogger.log(motionEvent, quickPanelLogger.tag, "");
            }
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            SecNotificationPanelViewController secNotificationPanelViewController = notificationPanelViewController.mSecNotificationPanelViewController;
            if (secNotificationPanelViewController != null && notificationPanelViewController.isFullyCollapsed() && motionEvent.isFromSource(8194) && !secNotificationPanelViewController.isTrackingSupplier.getAsBoolean()) {
                int action = motionEvent.getAction();
                if (action == 0) {
                    NotificationPanelViewController.this.mMotionAborted = false;
                } else if (action == 1) {
                    notificationPanelViewController.expand(true);
                }
                NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                QuickPanelLogger quickPanelLogger2 = notificationPanelViewController2.mQuickPanelLogger;
                if (quickPanelLogger2 != null) {
                    if (notificationPanelViewController2.mMotionAborted) {
                        quickPanelLogger2.handleTouch(motionEvent, "On expanding, single mouse click expands the panel instead of dragging", true);
                    } else {
                        quickPanelLogger2.handleTouch(motionEvent, "!isFullyCollapsed and from mouse", true);
                    }
                }
                return true;
            }
            NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
            if (notificationPanelViewController3.mInstantExpanding) {
                notificationPanelViewController3.mShadeLog.logMotionEvent(motionEvent, "handleTouch: touch ignored due to instant expanding");
                QuickPanelLogger quickPanelLogger3 = NotificationPanelViewController.this.mQuickPanelLogger;
                if (quickPanelLogger3 != null) {
                    quickPanelLogger3.handleTouch(motionEvent, "mInstantExpanding", false);
                }
                return false;
            }
            if (notificationPanelViewController3.mTouchDisabled && motionEvent.getActionMasked() != 3) {
                NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "handleTouch: non-cancel action, touch disabled");
                QuickPanelLogger quickPanelLogger4 = NotificationPanelViewController.this.mQuickPanelLogger;
                if (quickPanelLogger4 != null) {
                    quickPanelLogger4.handleTouch(motionEvent, "mTouchDisabled && event.getActionMasked() != ACTION_CANCEL", false);
                }
                return false;
            }
            if (NotificationPanelViewController.this.mMotionAborted && motionEvent.getActionMasked() != 0) {
                NotificationPanelViewController notificationPanelViewController4 = NotificationPanelViewController.this;
                notificationPanelViewController4.mShadeLog.logMotionEventStatusBarState(motionEvent, ((StatusBarStateControllerImpl) notificationPanelViewController4.mStatusBarStateController).mState, "handleTouch: non-down action, motion was aborted");
                QuickPanelLogger quickPanelLogger5 = NotificationPanelViewController.this.mQuickPanelLogger;
                if (quickPanelLogger5 != null) {
                    quickPanelLogger5.handleTouch(motionEvent, "mMotionAborted && event.getActionMasked() != ACTION_DOWN", false);
                }
                return false;
            }
            NotificationPanelViewController notificationPanelViewController5 = NotificationPanelViewController.this;
            if (!notificationPanelViewController5.mNotificationsDragEnabled) {
                if (notificationPanelViewController5.isTracking()) {
                    NotificationPanelViewController.this.onTrackingStopped(true);
                }
                NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "handleTouch: drag not enabled");
                QuickPanelLogger quickPanelLogger6 = NotificationPanelViewController.this.mQuickPanelLogger;
                if (quickPanelLogger6 != null) {
                    quickPanelLogger6.handleTouch(motionEvent, "!mNotificationsDragEnabled", false);
                }
                return false;
            }
            int findPointerIndex = motionEvent.findPointerIndex(notificationPanelViewController5.mTrackingPointer);
            if (findPointerIndex < 0) {
                NotificationPanelViewController.this.mTrackingPointer = motionEvent.getPointerId(0);
                findPointerIndex = 0;
            }
            float x = motionEvent.getX(findPointerIndex);
            float y = motionEvent.getY(findPointerIndex);
            if (motionEvent.getActionMasked() == 0 || motionEvent.getActionMasked() == 2) {
                NotificationPanelViewController notificationPanelViewController6 = NotificationPanelViewController.this;
                if (notificationPanelViewController6.mExpectingSynthesizedDown) {
                    Log.d("NotificationPanelView", "shouldGestureWaitForTouchSlop set mExpectingSynthesizedDown to false");
                    notificationPanelViewController6.mExpectingSynthesizedDown = false;
                } else if (notificationPanelViewController6.isFullyCollapsed() || notificationPanelViewController6.mBarState != 0) {
                    z = true;
                    notificationPanelViewController6.mGestureWaitForTouchSlop = z;
                    NotificationPanelViewController.this.mIgnoreXTouchSlop = true;
                }
                z = false;
                notificationPanelViewController6.mGestureWaitForTouchSlop = z;
                NotificationPanelViewController.this.mIgnoreXTouchSlop = true;
            }
            boolean z2 = Utilities.isTrackpadScroll(motionEvent) || Utilities.isTrackpadThreeFingerSwipe(motionEvent);
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 0) {
                if (actionMasked != 1) {
                    if (actionMasked == 2) {
                        if (QuickStepContract.ALLOW_BACK_GESTURE_IN_SHADE) {
                            NotificationPanelViewController.this.getClass();
                        }
                        if (NotificationPanelViewController.this.isFullyCollapsed()) {
                            NotificationPanelViewController notificationPanelViewController7 = NotificationPanelViewController.this;
                            notificationPanelViewController7.mHasVibratedOnOpen = false;
                            float f = notificationPanelViewController7.mExpandedFraction;
                            ShadeLogger shadeLogger = notificationPanelViewController7.mShadeLog;
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
                            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                            logMessageImpl.bool1 = false;
                            logMessageImpl.double1 = f;
                            logBuffer.commit(obtain);
                        }
                        NotificationPanelViewController.m2103$$Nest$maddMovement(NotificationPanelViewController.this, motionEvent);
                        if (!NotificationPanelViewController.this.isFullyCollapsed()) {
                            NotificationPanelViewController.this.maybeVibrateOnOpening(true);
                        }
                        NotificationPanelViewController notificationPanelViewController8 = NotificationPanelViewController.this;
                        float f2 = (y - notificationPanelViewController8.mInitialExpandY) * (notificationPanelViewController8.mIsTrackpadReverseScroll ? -1 : 1);
                        if (Math.abs(f2) > NotificationPanelViewController.this.getTouchSlop$1(motionEvent) && (Math.abs(f2) > Math.abs(x - NotificationPanelViewController.this.mInitialExpandX) || NotificationPanelViewController.this.mIgnoreXTouchSlop)) {
                            NotificationPanelViewController notificationPanelViewController9 = NotificationPanelViewController.this;
                            notificationPanelViewController9.mTouchSlopExceeded = true;
                            if (notificationPanelViewController9.mGestureWaitForTouchSlop && !notificationPanelViewController9.isTracking()) {
                                NotificationPanelViewController notificationPanelViewController10 = NotificationPanelViewController.this;
                                if (!notificationPanelViewController10.mCollapsedAndHeadsUpOnDown) {
                                    if (notificationPanelViewController10.mInitialOffsetOnTouch != 0.0f) {
                                        NotificationPanelViewController.m2106$$Nest$mstartExpandMotion(notificationPanelViewController10, x, y, false, notificationPanelViewController10.mExpandedHeight);
                                        f2 = 0.0f;
                                    }
                                    NotificationPanelViewController.this.cancelHeightAnimator();
                                    NotificationPanelViewController.this.onTrackingStarted();
                                }
                            }
                        }
                        float max = Math.max(Math.max(0.0f, NotificationPanelViewController.this.mInitialOffsetOnTouch + f2), NotificationPanelViewController.this.mMinExpandHeight);
                        if ((-f2) >= NotificationPanelViewController.this.getFalsingThreshold()) {
                            NotificationPanelViewController notificationPanelViewController11 = NotificationPanelViewController.this;
                            notificationPanelViewController11.mTouchAboveFalsingThreshold = true;
                            float f3 = x - notificationPanelViewController11.mInitialExpandX;
                            float f4 = (y - notificationPanelViewController11.mInitialExpandY) * (notificationPanelViewController11.mIsTrackpadReverseScroll ? -1 : 1);
                            notificationPanelViewController11.mUpwardsWhenThresholdReached = f4 < 0.0f && Math.abs(f4) >= Math.abs(f3);
                        }
                        NotificationPanelViewController notificationPanelViewController12 = NotificationPanelViewController.this;
                        if (!notificationPanelViewController12.mGestureWaitForTouchSlop || notificationPanelViewController12.isTracking()) {
                            NotificationPanelViewController notificationPanelViewController13 = NotificationPanelViewController.this;
                            if (!notificationPanelViewController13.mBlockingExpansionForCurrentTouch) {
                                QuickSettingsControllerImpl quickSettingsControllerImpl = notificationPanelViewController13.mQsController;
                                if (!quickSettingsControllerImpl.mConflictingExpansionGesture || !quickSettingsControllerImpl.getExpanded$1()) {
                                    NotificationPanelViewController notificationPanelViewController14 = NotificationPanelViewController.this;
                                    AmbientState ambientState = notificationPanelViewController14.mAmbientState;
                                    boolean z3 = f2 <= 0.0f;
                                    if (!z3 && ambientState.mIsSwipingUp) {
                                        ambientState.mIsFlingRequiredAfterLockScreenSwipeUp = true;
                                    }
                                    ambientState.mIsSwipingUp = z3;
                                    notificationPanelViewController14.setExpandedHeightInternal(max);
                                }
                            }
                        }
                    } else if (actionMasked != 3) {
                        if (actionMasked == 5) {
                            NotificationPanelViewController notificationPanelViewController15 = NotificationPanelViewController.this;
                            notificationPanelViewController15.mShadeLog.logMotionEventStatusBarState(motionEvent, ((StatusBarStateControllerImpl) notificationPanelViewController15.mStatusBarStateController).mState, "handleTouch: pointer down action");
                            if (!z2) {
                                NotificationPanelViewController notificationPanelViewController16 = NotificationPanelViewController.this;
                                if (((StatusBarStateControllerImpl) notificationPanelViewController16.mStatusBarStateController).mState == 1) {
                                    notificationPanelViewController16.mMotionAborted = true;
                                    NotificationPanelViewController.m2104$$Nest$mendMotionEvent(notificationPanelViewController16, motionEvent, x, y, true);
                                    QuickPanelLogger quickPanelLogger7 = NotificationPanelViewController.this.mQuickPanelLogger;
                                    if (quickPanelLogger7 != null) {
                                        quickPanelLogger7.handleTouch(motionEvent, "!isTrackpadTwoOrThreeFingerSwipe && mStatusBarStateController.getState() == KEYGUARD)", false);
                                    }
                                    return false;
                                }
                            }
                        } else if (actionMasked == 6 && !z2 && NotificationPanelViewController.this.mTrackingPointer == (pointerId = motionEvent.getPointerId(motionEvent.getActionIndex()))) {
                            int i = motionEvent.getPointerId(0) != pointerId ? 0 : 1;
                            float y2 = motionEvent.getY(i);
                            float x2 = motionEvent.getX(i);
                            NotificationPanelViewController.this.mTrackingPointer = motionEvent.getPointerId(i);
                            NotificationPanelViewController notificationPanelViewController17 = NotificationPanelViewController.this;
                            notificationPanelViewController17.mHandlingPointerUp = true;
                            NotificationPanelViewController.m2106$$Nest$mstartExpandMotion(notificationPanelViewController17, x2, y2, true, notificationPanelViewController17.mExpandedHeight);
                            NotificationPanelViewController.this.mHandlingPointerUp = false;
                        }
                    }
                }
                NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "onTouch: up/cancel action");
                NotificationPanelViewController.m2103$$Nest$maddMovement(NotificationPanelViewController.this, motionEvent);
                NotificationPanelViewController.m2104$$Nest$mendMotionEvent(NotificationPanelViewController.this, motionEvent, x, y, false);
                if (NotificationPanelViewController.this.mHeightAnimator == null) {
                    if (motionEvent.getActionMasked() == 1) {
                        InteractionJankMonitor interactionJankMonitor = (InteractionJankMonitor) NotificationPanelViewController.this.mQsController.mInteractionJankMonitorLazy.get();
                        if (interactionJankMonitor != null) {
                            interactionJankMonitor.end(0);
                        }
                    } else {
                        InteractionJankMonitor interactionJankMonitor2 = (InteractionJankMonitor) NotificationPanelViewController.this.mQsController.mInteractionJankMonitorLazy.get();
                        if (interactionJankMonitor2 != null) {
                            interactionJankMonitor2.cancel(0);
                        }
                    }
                }
                NotificationPanelViewController.this.mIsTrackpadReverseScroll = false;
            } else {
                if (QuickStepContract.ALLOW_BACK_GESTURE_IN_SHADE) {
                    NotificationPanelViewController.this.getClass();
                }
                NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "onTouch: down action");
                NotificationPanelViewController notificationPanelViewController18 = NotificationPanelViewController.this;
                NotificationPanelViewController.m2106$$Nest$mstartExpandMotion(notificationPanelViewController18, x, y, false, notificationPanelViewController18.mExpandedHeight);
                NotificationPanelViewController notificationPanelViewController19 = NotificationPanelViewController.this;
                notificationPanelViewController19.mMinExpandHeight = 0.0f;
                notificationPanelViewController19.mPanelClosedOnDown = notificationPanelViewController19.isFullyCollapsed();
                NotificationPanelViewController notificationPanelViewController20 = NotificationPanelViewController.this;
                ShadeLogger shadeLogger2 = notificationPanelViewController20.mShadeLog;
                boolean z4 = notificationPanelViewController20.mPanelClosedOnDown;
                float f5 = notificationPanelViewController20.mExpandedFraction;
                shadeLogger2.getClass();
                LogLevel logLevel2 = LogLevel.VERBOSE;
                ShadeLogger$logPanelClosedOnDown$2 shadeLogger$logPanelClosedOnDown$2 = ShadeLogger$logPanelClosedOnDown$2.INSTANCE;
                LogBuffer logBuffer2 = shadeLogger2.buffer;
                LogMessage obtain2 = logBuffer2.obtain("systemui.shade", logLevel2, shadeLogger$logPanelClosedOnDown$2, null);
                LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
                logMessageImpl2.str1 = "handle down touch";
                logMessageImpl2.bool1 = z4;
                logMessageImpl2.double1 = f5;
                logBuffer2.commit(obtain2);
                NotificationPanelViewController notificationPanelViewController21 = NotificationPanelViewController.this;
                notificationPanelViewController21.mHasLayoutedSinceDown = false;
                notificationPanelViewController21.mUpdateFlingOnLayout = false;
                notificationPanelViewController21.mMotionAborted = false;
                notificationPanelViewController21.mDownTime = notificationPanelViewController21.mSystemClock.uptimeMillis();
                NotificationPanelViewController notificationPanelViewController22 = NotificationPanelViewController.this;
                notificationPanelViewController22.mTouchAboveFalsingThreshold = false;
                notificationPanelViewController22.mCollapsedAndHeadsUpOnDown = notificationPanelViewController22.isFullyCollapsed() && ((BaseHeadsUpManager) NotificationPanelViewController.this.mHeadsUpManager).mHasPinnedNotification;
                NotificationPanelViewController.m2103$$Nest$maddMovement(NotificationPanelViewController.this, motionEvent);
                NotificationPanelViewController notificationPanelViewController23 = NotificationPanelViewController.this;
                boolean z5 = (notificationPanelViewController23.mHeightAnimator == null || notificationPanelViewController23.mIsSpringBackAnimation) ? false : true;
                if (!notificationPanelViewController23.mGestureWaitForTouchSlop || z5) {
                    notificationPanelViewController23.mTouchSlopExceeded = z5 || notificationPanelViewController23.mTouchSlopExceededBeforeDown;
                    notificationPanelViewController23.cancelHeightAnimator();
                    NotificationPanelViewController.this.onTrackingStarted();
                }
                if (NotificationPanelViewController.this.isFullyCollapsed()) {
                    NotificationPanelViewController notificationPanelViewController24 = NotificationPanelViewController.this;
                    if (!((BaseHeadsUpManager) notificationPanelViewController24.mHeadsUpManager).mHasPinnedNotification && !((CentralSurfacesImpl) notificationPanelViewController24.mCentralSurfaces).mBouncerShowing) {
                        notificationPanelViewController24.updateExpansionAndVisibility();
                        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) notificationPanelViewController24.mCentralSurfaces;
                        DisplayMetrics displayMetrics = centralSurfacesImpl.mDisplayMetrics;
                        notificationPanelViewController24.mLockscreenGestureLogger.mMetricsLogger.write(new LogMaker(1328).setType(4).addTaggedData(1326, Integer.valueOf((int) ((motionEvent.getX() / displayMetrics.widthPixels) * 100.0f))).addTaggedData(1327, Integer.valueOf((int) ((motionEvent.getY() / displayMetrics.heightPixels) * 100.0f))).addTaggedData(1329, Integer.valueOf(centralSurfacesImpl.mDisplay.getRotation())));
                        new UiEventLoggerImpl().log(LockscreenGestureLogger.LockscreenUiEvent.LOCKSCREEN_UNLOCKED_NOTIFICATION_PANEL_EXPAND);
                    }
                }
            }
            NotificationPanelViewController notificationPanelViewController25 = NotificationPanelViewController.this;
            if (notificationPanelViewController25.mQuickPanelLogger != null && (sb = notificationPanelViewController25.mQuickPanelLogBuilder) != null) {
                sb.setLength(0);
                StringBuilder sb2 = NotificationPanelViewController.this.mQuickPanelLogBuilder;
                sb2.append("FINAL: !mGestureWaitForTouchSlop: ");
                sb2.append(!NotificationPanelViewController.this.mGestureWaitForTouchSlop);
                sb2.append(" || isTracking(): ");
                sb2.append(NotificationPanelViewController.this.isTracking());
                NotificationPanelViewController notificationPanelViewController26 = NotificationPanelViewController.this;
                QuickPanelLogger quickPanelLogger8 = notificationPanelViewController26.mQuickPanelLogger;
                quickPanelLogger8.quickPanelLoggerHelper.handleTouchLogger.log(motionEvent, quickPanelLogger8.tag, notificationPanelViewController26.mQuickPanelLogBuilder.toString());
            }
            NotificationPanelViewController notificationPanelViewController27 = NotificationPanelViewController.this;
            return !notificationPanelViewController27.mGestureWaitForTouchSlop || notificationPanelViewController27.isTracking();
        }

        /* JADX WARN: Removed duplicated region for block: B:144:0x031f  */
        /* JADX WARN: Removed duplicated region for block: B:323:0x05a7  */
        /* JADX WARN: Removed duplicated region for block: B:326:0x05d4  */
        /* JADX WARN: Removed duplicated region for block: B:330:0x05de  */
        /* JADX WARN: Removed duplicated region for block: B:350:0x061b  */
        @Override // com.android.systemui.Gefingerpoken
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean onInterceptTouchEvent(android.view.MotionEvent r18) {
            /*
                Method dump skipped, instructions count: 2536
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.NotificationPanelViewController.TouchHandler.onInterceptTouchEvent(android.view.MotionEvent):boolean");
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            View.OnTouchListener onTouchListener;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            return (!((StatusBarStateControllerImpl) notificationPanelViewController.mStatusBarStateController).mIsDozing || (onTouchListener = notificationPanelViewController.mAODDoubleTouchListener) == null) ? onTouchEvent(motionEvent) : onTouchListener.onTouch(view, motionEvent);
        }

        /* JADX WARN: Code restructure failed: missing block: B:162:0x02e8, code lost:
        
            if (r0.mQsController.shouldQuickSettingsIntercept(r0.mDownX, r0.mDownY, 0.0f) != false) goto L176;
         */
        /* JADX WARN: Code restructure failed: missing block: B:164:0x02fa, code lost:
        
            if (r16.this$0.mPulseExpansionHandler.onTouchEvent(r17) == false) goto L186;
         */
        /* JADX WARN: Code restructure failed: missing block: B:165:0x02fc, code lost:
        
            r16.this$0.mShadeLog.logMotionEvent(r17, "onTouch: PulseExpansionHandler handled event");
            r0 = r16.this$0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:166:0x0309, code lost:
        
            if (r0.mQuickPanelLogger == null) goto L185;
         */
        /* JADX WARN: Code restructure failed: missing block: B:167:0x030b, code lost:
        
            r0 = r0.mQuickPanelLogBuilder;
         */
        /* JADX WARN: Code restructure failed: missing block: B:168:0x030d, code lost:
        
            if (r0 == null) goto L185;
         */
        /* JADX WARN: Code restructure failed: missing block: B:169:0x030f, code lost:
        
            r0.setLength(0);
            r0 = r16.this$0.mQuickPanelLogBuilder;
            r0.append("pulseShouldGetTouch && mPulseExpansionHandler.onTouchEvent()");
            r0.append(" (!mIsExpandingOrCollapsing: ");
            r0.append(!r16.this$0.mIsExpandingOrCollapsing);
            r0.append(" && !mQsController.shouldQuickSettingsIntercept(): ");
            r3 = r16.this$0;
            r0.append(!r3.mQsController.shouldQuickSettingsIntercept(r3.mDownX, r3.mDownY, 0.0f));
            r0.append(") || mPulseExpansionHandler.isExpanding(): ");
            r0.append(r16.this$0.mPulseExpansionHandler.isExpanding);
            r0 = r16.this$0;
            r0.mQuickPanelLogger.onTouchEvent(r17, r0.mQuickPanelLogBuilder.toString(), true);
         */
        /* JADX WARN: Code restructure failed: missing block: B:170:0x0358, code lost:
        
            return true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:171:0x0359, code lost:
        
            r0 = r16.this$0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:172:0x035d, code lost:
        
            if (r0.mPulsing == false) goto L192;
         */
        /* JADX WARN: Code restructure failed: missing block: B:173:0x035f, code lost:
        
            r0.mShadeLog.logMotionEvent(r17, "onTouch: eat touch, device pulsing");
            r0 = r16.this$0.mQuickPanelLogger;
         */
        /* JADX WARN: Code restructure failed: missing block: B:174:0x036a, code lost:
        
            if (r0 == null) goto L191;
         */
        /* JADX WARN: Code restructure failed: missing block: B:175:0x036c, code lost:
        
            r0.onTouchEvent(r17, "mPulsing", true);
         */
        /* JADX WARN: Code restructure failed: missing block: B:176:0x0371, code lost:
        
            return true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:178:0x0374, code lost:
        
            if (r0.mListenForHeadsUp == false) goto L204;
         */
        /* JADX WARN: Code restructure failed: missing block: B:179:0x0376, code lost:
        
            r8 = r0.mHeadsUpTouchHelper;
         */
        /* JADX WARN: Code restructure failed: missing block: B:180:0x037a, code lost:
        
            if (r8.mTrackingHeadsUp != false) goto L204;
         */
        /* JADX WARN: Code restructure failed: missing block: B:182:0x0380, code lost:
        
            if (r0.mNotificationStackScrollLayoutController.mLongPressedView == null) goto L199;
         */
        /* JADX WARN: Code restructure failed: missing block: B:183:0x0382, code lost:
        
            r0 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:184:0x0385, code lost:
        
            if (r0 != false) goto L204;
         */
        /* JADX WARN: Code restructure failed: missing block: B:186:0x038b, code lost:
        
            if (r8.onInterceptTouchEvent(r17) == false) goto L204;
         */
        /* JADX WARN: Code restructure failed: missing block: B:187:0x038d, code lost:
        
            r16.this$0.mMetricsLogger.count("panel_open_peek", 1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:188:0x0384, code lost:
        
            r0 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:189:0x0396, code lost:
        
            r0 = r16.this$0.mHeadsUpTouchHelper.onTouchEvent(r17);
            r8 = r16.this$0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:190:0x03a3, code lost:
        
            if (r8.mIsExpandingOrCollapsing == false) goto L208;
         */
        /* JADX WARN: Code restructure failed: missing block: B:192:0x03a7, code lost:
        
            if (r8.mHintAnimationRunning == false) goto L276;
         */
        /* JADX WARN: Code restructure failed: missing block: B:193:0x04c8, code lost:
        
            r8 = r16.this$0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:194:0x04cc, code lost:
        
            if (r8.mOnlyAffordanceInThisMotion == false) goto L282;
         */
        /* JADX WARN: Code restructure failed: missing block: B:195:0x04ce, code lost:
        
            r0 = r8.mQuickPanelLogger;
         */
        /* JADX WARN: Code restructure failed: missing block: B:196:0x04d0, code lost:
        
            if (r0 == null) goto L281;
         */
        /* JADX WARN: Code restructure failed: missing block: B:197:0x04d2, code lost:
        
            r0.onTouchEvent(r17, "mOnlyAffordanceInThisMotion", true);
         */
        /* JADX WARN: Code restructure failed: missing block: B:198:0x04d7, code lost:
        
            return true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:199:0x04d8, code lost:
        
            r8 = r8.mIndicatorTouchHandler;
            r11 = ((com.android.systemui.knox.KnoxStateMonitorImpl) r8.knoxStateMonitor).mCustomSdkMonitor;
         */
        /* JADX WARN: Code restructure failed: missing block: B:200:0x04e2, code lost:
        
            if (r11 == null) goto L299;
         */
        /* JADX WARN: Code restructure failed: missing block: B:202:0x04e6, code lost:
        
            if (r11.mKnoxCustomDoubleTapState == false) goto L299;
         */
        /* JADX WARN: Code restructure failed: missing block: B:204:0x04ea, code lost:
        
            if (r8.doubleTapCount != 0) goto L290;
         */
        /* JADX WARN: Code restructure failed: missing block: B:206:0x04f0, code lost:
        
            if (r17.getActionMasked() == 0) goto L294;
         */
        /* JADX WARN: Code restructure failed: missing block: B:207:0x04fe, code lost:
        
            r11 = r8.doubleTapCount + 1;
            r8.doubleTapCount = r11;
            r15 = r8.doubleTapTimeoutRunnable;
            r10 = r8.mainHandler;
         */
        /* JADX WARN: Code restructure failed: missing block: B:208:0x0507, code lost:
        
            if (r11 != 1) goto L297;
         */
        /* JADX WARN: Code restructure failed: missing block: B:209:0x0509, code lost:
        
            r10.removeCallbacks(r15);
            r10.postDelayed(r15, 500);
            android.util.Log.d("IndicatorTouchHandler", "Post double tap timeout runnable");
         */
        /* JADX WARN: Code restructure failed: missing block: B:210:0x0517, code lost:
        
            if (r11 < 3) goto L299;
         */
        /* JADX WARN: Code restructure failed: missing block: B:211:0x0519, code lost:
        
            android.util.Log.d("IndicatorTouchHandler", "Go to sleep by knox double tap");
            r8.doubleTapCount = 0;
            r10.removeCallbacks(r15);
            r8.powerManager.goToSleep(android.os.SystemClock.uptimeMillis());
         */
        /* JADX WARN: Code restructure failed: missing block: B:213:0x04f6, code lost:
        
            if (r17.getActionMasked() == 1) goto L294;
         */
        /* JADX WARN: Code restructure failed: missing block: B:215:0x04fc, code lost:
        
            if (r17.getActionMasked() != 3) goto L299;
         */
        /* JADX WARN: Code restructure failed: missing block: B:216:0x052c, code lost:
        
            r10 = r17.getActionMasked();
            r11 = r8.keyguardStateController;
         */
        /* JADX WARN: Code restructure failed: missing block: B:217:0x0532, code lost:
        
            if (r10 == 0) goto L327;
         */
        /* JADX WARN: Code restructure failed: missing block: B:218:0x0534, code lost:
        
            if (r10 == 1) goto L315;
         */
        /* JADX WARN: Code restructure failed: missing block: B:219:0x0536, code lost:
        
            if (r10 == 3) goto L311;
         */
        /* JADX WARN: Code restructure failed: missing block: B:221:0x0539, code lost:
        
            if (r10 == 5) goto L307;
         */
        /* JADX WARN: Code restructure failed: missing block: B:222:0x053b, code lost:
        
            if (r10 == 6) goto L311;
         */
        /* JADX WARN: Code restructure failed: missing block: B:224:0x05df, code lost:
        
            if (com.android.systemui.shade.SecPanelSplitHelper.isEnabled() == false) goto L341;
         */
        /* JADX WARN: Code restructure failed: missing block: B:226:0x05e5, code lost:
        
            if (r17.getAction() == 1) goto L340;
         */
        /* JADX WARN: Code restructure failed: missing block: B:228:0x05eb, code lost:
        
            if (r17.getAction() != 3) goto L341;
         */
        /* JADX WARN: Code restructure failed: missing block: B:229:0x05ed, code lost:
        
            r16.this$0.setOnStatusBarDownEvent(null);
         */
        /* JADX WARN: Code restructure failed: missing block: B:230:0x05f3, code lost:
        
            r8 = r16.this$0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:231:0x05f7, code lost:
        
            if (r8.mBarState == 1) goto L360;
         */
        /* JADX WARN: Code restructure failed: missing block: B:233:0x05fd, code lost:
        
            if (r8.mHeadsUpTouchHelper.mTrackingHeadsUp != false) goto L360;
         */
        /* JADX WARN: Code restructure failed: missing block: B:234:0x05ff, code lost:
        
            r9 = r8.isFullyCollapsed();
            r10 = r16.this$0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:235:0x0607, code lost:
        
            if (r10.mHeightAnimator == null) goto L350;
         */
        /* JADX WARN: Code restructure failed: missing block: B:237:0x060b, code lost:
        
            if (r10.mIsSpringBackAnimation != false) goto L350;
         */
        /* JADX WARN: Code restructure failed: missing block: B:238:0x060d, code lost:
        
            r10 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:240:0x0616, code lost:
        
            if (r8.mQsController.handleTouch(r17, r9, r10) == false) goto L360;
         */
        /* JADX WARN: Code restructure failed: missing block: B:242:0x061c, code lost:
        
            if (r17.getActionMasked() == 2) goto L356;
         */
        /* JADX WARN: Code restructure failed: missing block: B:243:0x061e, code lost:
        
            r16.this$0.mShadeLog.logMotionEvent(r17, "onTouch: handleQsTouch handled event");
         */
        /* JADX WARN: Code restructure failed: missing block: B:244:0x0627, code lost:
        
            r0 = r16.this$0.mQuickPanelLogger;
         */
        /* JADX WARN: Code restructure failed: missing block: B:245:0x062b, code lost:
        
            if (r0 == null) goto L359;
         */
        /* JADX WARN: Code restructure failed: missing block: B:246:0x062d, code lost:
        
            r0.onTouchEvent(r17, "!mHeadsUpTouchHelper.isTrackingHeadsUp() && mQsController.handleTouch()", true);
         */
        /* JADX WARN: Code restructure failed: missing block: B:247:0x0632, code lost:
        
            return true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:248:0x060f, code lost:
        
            r10 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:250:0x0637, code lost:
        
            if (r17.getActionMasked() != 0) goto L365;
         */
        /* JADX WARN: Code restructure failed: missing block: B:252:0x063f, code lost:
        
            if (r16.this$0.isFullyCollapsed() == false) goto L365;
         */
        /* JADX WARN: Code restructure failed: missing block: B:253:0x0641, code lost:
        
            r16.this$0.mMetricsLogger.count("panel_open", 1);
            r6 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:255:0x064d, code lost:
        
            r0 = r16.this$0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:256:0x0651, code lost:
        
            if (r0.mLockStarEnabled == false) goto L386;
         */
        /* JADX WARN: Code restructure failed: missing block: B:258:0x0657, code lost:
        
            if (r0.isInLockStarContainer(r17) == false) goto L386;
         */
        /* JADX WARN: Code restructure failed: missing block: B:260:0x0661, code lost:
        
            if (r16.this$0.mQsController.getExpanded$1() != false) goto L386;
         */
        /* JADX WARN: Code restructure failed: missing block: B:261:0x0663, code lost:
        
            r0 = r16.this$0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:262:0x0667, code lost:
        
            if (r0.mBarState != 1) goto L386;
         */
        /* JADX WARN: Code restructure failed: missing block: B:264:0x066f, code lost:
        
            if (r0.mPluginLockStarContainer.getVisibility() != 0) goto L386;
         */
        /* JADX WARN: Code restructure failed: missing block: B:266:0x0679, code lost:
        
            if (r16.this$0.mPluginLockStarManagerLazy.get() == null) goto L386;
         */
        /* JADX WARN: Code restructure failed: missing block: B:267:0x067b, code lost:
        
            r0 = ((com.android.systemui.lockstar.PluginLockStarManager) r16.this$0.mPluginLockStarManagerLazy.get()).onInterceptTouchEvent(r17);
            r8 = r16.this$0.mQuickPanelLogger;
         */
        /* JADX WARN: Code restructure failed: missing block: B:268:0x068d, code lost:
        
            if (r8 == null) goto L384;
         */
        /* JADX WARN: Code restructure failed: missing block: B:269:0x068f, code lost:
        
            r8.onTouchEvent(r17, "LsRune.PLUGIN_LOCK_STAR", r0);
         */
        /* JADX WARN: Code restructure failed: missing block: B:271:0x0697, code lost:
        
            return r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:362:0x0695, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:363:0x0698, code lost:
        
            r8 = com.android.systemui.shade.NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
            android.util.Log.e("NotificationPanelView", "onTouchEvent() error in LockStar - " + r0.getMessage());
         */
        /* JADX WARN: Code restructure failed: missing block: B:364:0x064c, code lost:
        
            r6 = r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:366:0x0541, code lost:
        
            if (r8.isTouchOnCallChip == false) goto L310;
         */
        /* JADX WARN: Code restructure failed: missing block: B:367:0x0543, code lost:
        
            android.util.Log.d("IndicatorTouchHandler", "pointer down x=" + r17 + ".rawX ,y=" + r17 + ".rawY");
         */
        /* JADX WARN: Code restructure failed: missing block: B:368:0x0561, code lost:
        
            r8.isTouchOnCallChip = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:370:0x0567, code lost:
        
            if (r8.isTouchOnCallChip == false) goto L314;
         */
        /* JADX WARN: Code restructure failed: missing block: B:371:0x0569, code lost:
        
            android.util.Log.d("IndicatorTouchHandler", "cancel or pointer up -> block to jump to call in multi touch");
         */
        /* JADX WARN: Code restructure failed: missing block: B:372:0x056e, code lost:
        
            r8.isTouchOnCallChip = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:374:0x0573, code lost:
        
            if (r8.isTouchOnCallChip == false) goto L326;
         */
        /* JADX WARN: Code restructure failed: missing block: B:375:0x0575, code lost:
        
            r9 = r17.getX();
            r10 = r17.getY();
         */
        /* JADX WARN: Code restructure failed: missing block: B:376:0x0581, code lost:
        
            if (((com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r11).mShowing == false) goto L320;
         */
        /* JADX WARN: Code restructure failed: missing block: B:377:0x0583, code lost:
        
            r11 = r8.keyguardCallChipRect;
         */
        /* JADX WARN: Code restructure failed: missing block: B:379:0x058e, code lost:
        
            if (r11.contains((int) r9, (int) r10) == false) goto L326;
         */
        /* JADX WARN: Code restructure failed: missing block: B:380:0x0590, code lost:
        
            r9 = r8.ongoingCallController.chipView;
         */
        /* JADX WARN: Code restructure failed: missing block: B:381:0x0594, code lost:
        
            if (r9 == null) goto L326;
         */
        /* JADX WARN: Code restructure failed: missing block: B:382:0x0596, code lost:
        
            r9.performClick();
         */
        /* JADX WARN: Code restructure failed: missing block: B:383:0x0586, code lost:
        
            r11 = r8.callChipRect;
         */
        /* JADX WARN: Code restructure failed: missing block: B:384:0x0599, code lost:
        
            r8.isTouchOnCallChip = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:385:0x059c, code lost:
        
            r8.touchDownX = r17.getRawX();
            r8.touchDownY = r17.getRawY();
            r9 = r17.getX();
            r10 = r17.getY();
         */
        /* JADX WARN: Code restructure failed: missing block: B:386:0x05b5, code lost:
        
            if (((com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r11).mShowing == false) goto L330;
         */
        /* JADX WARN: Code restructure failed: missing block: B:387:0x05b7, code lost:
        
            r12 = r8.keyguardCallChipRect;
         */
        /* JADX WARN: Code restructure failed: missing block: B:389:0x05c2, code lost:
        
            if (r12.contains((int) r9, (int) r10) == false) goto L334;
         */
        /* JADX WARN: Code restructure failed: missing block: B:390:0x05c4, code lost:
        
            r8.isTouchOnCallChip = true;
            androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0.m(androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0.m("ACTION_DOWN x=", r8.touchDownX, ", y=", r8.touchDownY, ", on the callChip=true, keyguardShowing="), ((com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r11).mShowing, "IndicatorTouchHandler");
         */
        /* JADX WARN: Code restructure failed: missing block: B:391:0x05ba, code lost:
        
            r12 = r8.callChipRect;
         */
        /* JADX WARN: Code restructure failed: missing block: B:393:0x03af, code lost:
        
            if (r8.mQsController.getExpanded$1() != false) goto L276;
         */
        /* JADX WARN: Code restructure failed: missing block: B:394:0x03b1, code lost:
        
            r8 = r16.this$0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:395:0x03b5, code lost:
        
            if (r8.mBarState == 0) goto L276;
         */
        /* JADX WARN: Code restructure failed: missing block: B:397:0x03b9, code lost:
        
            if (r8.mDozing != false) goto L276;
         */
        /* JADX WARN: Code restructure failed: missing block: B:399:0x03c1, code lost:
        
            if (r8.mKeyguardBottomArea.getVisibility() != 0) goto L276;
         */
        /* JADX WARN: Code restructure failed: missing block: B:400:0x03c3, code lost:
        
            r8 = r16.this$0.mSecAffordanceHelper;
            r8.getClass();
            r11 = r17.getActionMasked();
         */
        /* JADX WARN: Code restructure failed: missing block: B:401:0x03d0, code lost:
        
            if (r8.mMotionCancelled == false) goto L220;
         */
        /* JADX WARN: Code restructure failed: missing block: B:402:0x03d2, code lost:
        
            if (r11 == 0) goto L220;
         */
        /* JADX WARN: Code restructure failed: missing block: B:403:0x03d4, code lost:
        
            r11 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:404:0x04c7, code lost:
        
            r0 = r0 | r11;
         */
        /* JADX WARN: Code restructure failed: missing block: B:406:0x03d9, code lost:
        
            if (com.android.systemui.CscRune.SECURITY_SIM_PERM_DISABLED == false) goto L225;
         */
        /* JADX WARN: Code restructure failed: missing block: B:408:0x03e9, code lost:
        
            if (((com.android.keyguard.KeyguardUpdateMonitor) com.android.systemui.Dependency.sDependency.getDependencyInner(com.android.keyguard.KeyguardUpdateMonitor.class)).isIccBlockedPermanently() == false) goto L225;
         */
        /* JADX WARN: Code restructure failed: missing block: B:409:0x03ec, code lost:
        
            r14 = r17.getY();
            r15 = r17.getX();
         */
        /* JADX WARN: Code restructure failed: missing block: B:410:0x03f4, code lost:
        
            if (r11 == 0) goto L249;
         */
        /* JADX WARN: Code restructure failed: missing block: B:411:0x03f6, code lost:
        
            if (r11 == 1) goto L242;
         */
        /* JADX WARN: Code restructure failed: missing block: B:412:0x03f8, code lost:
        
            if (r11 == 3) goto L242;
         */
        /* JADX WARN: Code restructure failed: missing block: B:413:0x03fa, code lost:
        
            if (r11 == 5) goto L234;
         */
        /* JADX WARN: Code restructure failed: missing block: B:414:0x03fc, code lost:
        
            r8 = r8.mTargetedView;
         */
        /* JADX WARN: Code restructure failed: missing block: B:415:0x03fe, code lost:
        
            if (r8 != null) goto L233;
         */
        /* JADX WARN: Code restructure failed: missing block: B:416:0x0400, code lost:
        
            r8 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:417:0x04c1, code lost:
        
            r11 = r8;
         */
        /* JADX WARN: Code restructure failed: missing block: B:418:0x0403, code lost:
        
            r8 = r8.onTouchEvent(r17);
         */
        /* JADX WARN: Code restructure failed: missing block: B:419:0x0409, code lost:
        
            r8.mMotionCancelled = true;
            r11 = r8.mTargetedView;
         */
        /* JADX WARN: Code restructure failed: missing block: B:420:0x040d, code lost:
        
            if (r11 != null) goto L237;
         */
        /* JADX WARN: Code restructure failed: missing block: B:421:0x0410, code lost:
        
            r11 = r11.onTouchEvent(r17);
         */
        /* JADX WARN: Code restructure failed: missing block: B:422:0x0416, code lost:
        
            if (r8.mTargetedView != null) goto L240;
         */
        /* JADX WARN: Code restructure failed: missing block: B:423:0x041a, code lost:
        
            r8.endMotion();
         */
        /* JADX WARN: Code restructure failed: missing block: B:424:0x041d, code lost:
        
            r8 = r11;
         */
        /* JADX WARN: Code restructure failed: missing block: B:425:0x0420, code lost:
        
            r11 = r8.mTargetedView;
         */
        /* JADX WARN: Code restructure failed: missing block: B:426:0x0422, code lost:
        
            if (r11 != null) goto L245;
         */
        /* JADX WARN: Code restructure failed: missing block: B:427:0x0425, code lost:
        
            r11 = r11.onTouchEvent(r17);
         */
        /* JADX WARN: Code restructure failed: missing block: B:428:0x042b, code lost:
        
            if (r8.mTargetedView != null) goto L248;
         */
        /* JADX WARN: Code restructure failed: missing block: B:429:0x042f, code lost:
        
            r8.endMotion();
         */
        /* JADX WARN: Code restructure failed: missing block: B:430:0x0433, code lost:
        
            r11 = r8.mLeftIcon;
            kotlin.jvm.internal.Intrinsics.checkNotNull(r11);
         */
        /* JADX WARN: Code restructure failed: missing block: B:431:0x043c, code lost:
        
            if (r11.getVisibility() != 0) goto L254;
         */
        /* JADX WARN: Code restructure failed: missing block: B:432:0x043e, code lost:
        
            r11 = r8.mLeftIcon;
            kotlin.jvm.internal.Intrinsics.checkNotNull(r11);
         */
        /* JADX WARN: Code restructure failed: missing block: B:433:0x0447, code lost:
        
            if (r8.isOnIcon(r11, r15, r14) == false) goto L254;
         */
        /* JADX WARN: Code restructure failed: missing block: B:434:0x0449, code lost:
        
            r11 = r8.mLeftIcon;
            kotlin.jvm.internal.Intrinsics.checkNotNull(r11);
         */
        /* JADX WARN: Code restructure failed: missing block: B:435:0x046c, code lost:
        
            android.util.Log.d("KeyguardSecAffordanceHelper", "onTouchEvent: After selecting target view");
         */
        /* JADX WARN: Code restructure failed: missing block: B:436:0x0473, code lost:
        
            if (r11 == null) goto L274;
         */
        /* JADX WARN: Code restructure failed: missing block: B:437:0x0475, code lost:
        
            r14 = r8.mTargetedView;
         */
        /* JADX WARN: Code restructure failed: missing block: B:438:0x0477, code lost:
        
            if (r14 == null) goto L266;
         */
        /* JADX WARN: Code restructure failed: missing block: B:439:0x0479, code lost:
        
            if (r14 == r11) goto L266;
         */
        /* JADX WARN: Code restructure failed: missing block: B:440:0x047c, code lost:
        
            r8.mMotionCancelled = false;
            r8.mTargetedView = r11;
            r14 = r8.mLeftIcon;
         */
        /* JADX WARN: Code restructure failed: missing block: B:441:0x0482, code lost:
        
            if (r11 != r14) goto L269;
         */
        /* JADX WARN: Code restructure failed: missing block: B:442:0x0484, code lost:
        
            r11 = r8.mRightIcon;
            kotlin.jvm.internal.Intrinsics.checkNotNull(r11);
            r11.mIsTargetView = false;
            r11 = r8.mLeftIcon;
            kotlin.jvm.internal.Intrinsics.checkNotNull(r11);
            r11.mIsTargetView = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:443:0x04a3, code lost:
        
            r11 = r8.mTargetedView;
            kotlin.jvm.internal.Intrinsics.checkNotNull(r11);
            r8.startPreviewAnimation(r11, true);
            ((com.android.keyguard.KeyguardUpdateMonitor) com.android.systemui.Dependency.sDependency.getDependencyInner(com.android.keyguard.KeyguardUpdateMonitor.class)).setShortcutLaunchInProgress(true);
            r8 = r8.mTargetedView;
            kotlin.jvm.internal.Intrinsics.checkNotNull(r8);
            r8 = r8.onTouchEvent(r17);
         */
        /* JADX WARN: Code restructure failed: missing block: B:445:0x0495, code lost:
        
            if (r11 != r8.mRightIcon) goto L272;
         */
        /* JADX WARN: Code restructure failed: missing block: B:446:0x0497, code lost:
        
            kotlin.jvm.internal.Intrinsics.checkNotNull(r14);
            r14.mIsTargetView = false;
            r11 = r8.mRightIcon;
            kotlin.jvm.internal.Intrinsics.checkNotNull(r11);
            r11.mIsTargetView = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:447:0x04c3, code lost:
        
            r8.mMotionCancelled = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:448:0x044f, code lost:
        
            r11 = r8.mRightIcon;
            kotlin.jvm.internal.Intrinsics.checkNotNull(r11);
         */
        /* JADX WARN: Code restructure failed: missing block: B:449:0x0458, code lost:
        
            if (r11.getVisibility() != 0) goto L259;
         */
        /* JADX WARN: Code restructure failed: missing block: B:450:0x045a, code lost:
        
            r11 = r8.mRightIcon;
            kotlin.jvm.internal.Intrinsics.checkNotNull(r11);
         */
        /* JADX WARN: Code restructure failed: missing block: B:451:0x0463, code lost:
        
            if (r8.isOnIcon(r11, r15, r14) == false) goto L259;
         */
        /* JADX WARN: Code restructure failed: missing block: B:452:0x0465, code lost:
        
            r11 = r8.mRightIcon;
            kotlin.jvm.internal.Intrinsics.checkNotNull(r11);
         */
        /* JADX WARN: Code restructure failed: missing block: B:453:0x046b, code lost:
        
            r11 = null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:455:0x02f0, code lost:
        
            if (r16.this$0.mPulseExpansionHandler.isExpanding != false) goto L178;
         */
        /* JADX WARN: Removed duplicated region for block: B:338:0x07d3  */
        @Override // com.android.systemui.Gefingerpoken
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean onTouchEvent(android.view.MotionEvent r17) {
            /*
                Method dump skipped, instructions count: 2103
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.NotificationPanelViewController.TouchHandler.onTouchEvent(android.view.MotionEvent):boolean");
        }
    }

    /* renamed from: -$$Nest$maddMovement, reason: not valid java name */
    public static void m2103$$Nest$maddMovement(NotificationPanelViewController notificationPanelViewController, MotionEvent motionEvent) {
        notificationPanelViewController.getClass();
        float rawX = motionEvent.getRawX() - motionEvent.getX();
        float rawY = motionEvent.getRawY() - motionEvent.getY();
        motionEvent.offsetLocation(rawX, rawY);
        notificationPanelViewController.mVelocityTracker.addMovement(motionEvent);
        motionEvent.offsetLocation(-rawX, -rawY);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01ec  */
    /* renamed from: -$$Nest$mendMotionEvent, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m2104$$Nest$mendMotionEvent(com.android.systemui.shade.NotificationPanelViewController r16, android.view.MotionEvent r17, float r18, float r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 525
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.NotificationPanelViewController.m2104$$Nest$mendMotionEvent(com.android.systemui.shade.NotificationPanelViewController, android.view.MotionEvent, float, float, boolean):void");
    }

    /* renamed from: -$$Nest$minitDownStates, reason: not valid java name */
    public static void m2105$$Nest$minitDownStates(NotificationPanelViewController notificationPanelViewController, MotionEvent motionEvent) {
        boolean z;
        notificationPanelViewController.getClass();
        boolean z2 = false;
        if (motionEvent.getActionMasked() != 0) {
            notificationPanelViewController.mLastEventSynthesizedDown = false;
            return;
        }
        notificationPanelViewController.mDozingOnDown = notificationPanelViewController.mDozing;
        notificationPanelViewController.mOnlyAffordanceInThisMotion = false;
        notificationPanelViewController.mSecAffordanceHelper.isShortcutPreviewSwipingInProgress = false;
        notificationPanelViewController.mDownX = motionEvent.getX();
        notificationPanelViewController.mDownY = motionEvent.getY();
        boolean isFullyCollapsed = notificationPanelViewController.isFullyCollapsed();
        notificationPanelViewController.mCollapsedOnDown = isFullyCollapsed;
        QuickSettingsControllerImpl quickSettingsControllerImpl = notificationPanelViewController.mQsController;
        quickSettingsControllerImpl.mCollapsedOnDown = isFullyCollapsed;
        if (notificationPanelViewController.mNotificationStackScrollLayoutController.mView.mOwnScrollY >= quickSettingsControllerImpl.mMinExpansionHeight - notificationPanelViewController.mQuickQsOffsetHeight) {
            notificationPanelViewController.mIsPanelCollapseOnQQS = false;
        } else {
            float f = notificationPanelViewController.mDownX;
            float f2 = notificationPanelViewController.mDownY;
            if (!isFullyCollapsed && quickSettingsControllerImpl.mBarState != 1 && !quickSettingsControllerImpl.getExpanded$1()) {
                QS qs = quickSettingsControllerImpl.mQs;
                View header = qs == null ? quickSettingsControllerImpl.mKeyguardStatusBar : qs.getHeader();
                if (f >= quickSettingsControllerImpl.mQsFrame.getX() && f <= quickSettingsControllerImpl.mQsFrame.getX() + quickSettingsControllerImpl.mQsFrame.getWidth() && f2 <= header.getBottom()) {
                    z = true;
                    notificationPanelViewController.mIsPanelCollapseOnQQS = z;
                }
            }
            z = false;
            notificationPanelViewController.mIsPanelCollapseOnQQS = z;
        }
        if (notificationPanelViewController.mCollapsedOnDown && ((BaseHeadsUpManager) notificationPanelViewController.mHeadsUpManager).mHasPinnedNotification) {
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
        boolean z4 = quickSettingsControllerImpl.mFullyExpanded;
        quickSettingsControllerImpl.mTouchAboveFalsingThreshold = z4;
        boolean z5 = notificationPanelViewController.mDozingOnDown;
        boolean z6 = notificationPanelViewController.mCollapsedOnDown;
        boolean z7 = notificationPanelViewController.mIsPanelCollapseOnQQS;
        boolean z8 = notificationPanelViewController.mListenForHeadsUp;
        boolean z9 = notificationPanelViewController.mAllowExpandForSmallExpansion;
        boolean z10 = notificationPanelViewController.mTouchSlopExceededBeforeDown;
        boolean z11 = notificationPanelViewController.mLastEventSynthesizedDown;
        NPVCDownEventState nPVCDownEventState = (NPVCDownEventState) notificationPanelViewController.mLastDownEvents.buffer.advance();
        nPVCDownEventState.timeStamp = eventTime;
        nPVCDownEventState.x = f3;
        nPVCDownEventState.y = f4;
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
    public static void m2106$$Nest$mstartExpandMotion(NotificationPanelViewController notificationPanelViewController, float f, float f2, boolean z, float f3) {
        if (!notificationPanelViewController.mHandlingPointerUp && !((StatusBarStateControllerImpl) notificationPanelViewController.mStatusBarStateController).mIsDozing) {
            notificationPanelViewController.mQsController.beginJankMonitoring(notificationPanelViewController.isFullyCollapsed());
        }
        notificationPanelViewController.mInitialOffsetOnTouch = f3;
        if (!notificationPanelViewController.isTracking() || notificationPanelViewController.isFullyCollapsed()) {
            notificationPanelViewController.mInitialExpandY = f2;
            notificationPanelViewController.mInitialExpandX = f;
        } else {
            notificationPanelViewController.mShadeLog.d("not setting mInitialExpandY in startExpandMotion");
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

    /* JADX WARN: Type inference failed for: r10v19, types: [com.android.systemui.shade.NotificationPanelViewController$17] */
    /* JADX WARN: Type inference failed for: r11v17, types: [com.android.systemui.shade.NotificationPanelViewController$2] */
    /* JADX WARN: Type inference failed for: r11v18, types: [com.android.systemui.shade.NotificationPanelViewController$16] */
    /* JADX WARN: Type inference failed for: r13v2, types: [com.android.systemui.shade.NotificationPanelViewController$1] */
    /* JADX WARN: Type inference failed for: r15v5, types: [com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda17] */
    public NotificationPanelViewController(DcmMascotViewContainer dcmMascotViewContainer, PluginLockMediator pluginLockMediator, NotificationPanelView notificationPanelView, KeyguardTouchAnimator keyguardTouchAnimator, Lazy lazy, Handler handler, LayoutInflater layoutInflater, FeatureFlags featureFlags, NotificationWakeUpCoordinator notificationWakeUpCoordinator, PulseExpansionHandler pulseExpansionHandler, DynamicPrivacyController dynamicPrivacyController, KeyguardBypassController keyguardBypassController, FalsingManager falsingManager, FalsingCollector falsingCollector, KeyguardStateController keyguardStateController, StatusBarStateController statusBarStateController, StatusBarWindowStateController statusBarWindowStateController, NotificationShadeWindowController notificationShadeWindowController, DozeLog dozeLog, DozeParameters dozeParameters, CommandQueue commandQueue, VibratorHelper vibratorHelper, LatencyTracker latencyTracker, AccessibilityManager accessibilityManager, int i, KeyguardUpdateMonitor keyguardUpdateMonitor, MetricsLogger metricsLogger, ShadeLogger shadeLogger, ConfigurationController configurationController, Provider provider, StatusBarTouchableRegionManager statusBarTouchableRegionManager, ConversationNotificationManager conversationNotificationManager, MediaHierarchyManager mediaHierarchyManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, NotificationGutsManager notificationGutsManager, NotificationsQSContainerController notificationsQSContainerController, NotificationStackScrollLayoutController notificationStackScrollLayoutController, KeyguardStatusViewComponent.Factory factory, KeyguardQsUserSwitchComponent.Factory factory2, KeyguardUserSwitcherComponent.Factory factory3, KeyguardStatusBarViewComponent.Factory factory4, LockscreenShadeTransitionController lockscreenShadeTransitionController, AuthController authController, ScrimController scrimController, UserManager userManager, MediaDataManager mediaDataManager, NotificationShadeDepthController notificationShadeDepthController, AmbientState ambientState, SecLockIconViewController secLockIconViewController, KeyguardMediaController keyguardMediaController, TapAgainViewController tapAgainViewController, NavigationModeController navigationModeController, NavigationBarController navigationBarController, QuickSettingsControllerImpl quickSettingsControllerImpl, FragmentService fragmentService, IStatusBarService iStatusBarService, ContentResolver contentResolver, ShadeHeaderController shadeHeaderController, ScreenOffAnimationController screenOffAnimationController, LockscreenGestureLogger lockscreenGestureLogger, ShadeExpansionStateManager shadeExpansionStateManager, ShadeRepository shadeRepository, Optional<SysUIUnfoldComponent> optional, SysUiState sysUiState, Provider provider2, KeyguardWallpaperController keyguardWallpaperController, WallpaperImageInjectCreator wallpaperImageInjectCreator, EmergencyButtonController.Factory factory5, KeyguardUnlockAnimationController keyguardUnlockAnimationController, KeyguardIndicationController keyguardIndicationController, NotificationListContainer notificationListContainer, NotificationStackSizeCalculator notificationStackSizeCalculator, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, SystemClock systemClock, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, KeyguardBottomAreaInteractor keyguardBottomAreaInteractor, KeyguardClockInteractor keyguardClockInteractor, AlternateBouncerInteractor alternateBouncerInteractor, DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel, OccludedToLockscreenTransitionViewModel occludedToLockscreenTransitionViewModel, LockscreenToDreamingTransitionViewModel lockscreenToDreamingTransitionViewModel, GoneToDreamingTransitionViewModel goneToDreamingTransitionViewModel, GoneToDreamingLockscreenHostedTransitionViewModel goneToDreamingLockscreenHostedTransitionViewModel, LockscreenToOccludedTransitionViewModel lockscreenToOccludedTransitionViewModel, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, CoroutineDispatcher coroutineDispatcher, KeyguardTransitionInteractor keyguardTransitionInteractor, DumpManager dumpManager, KeyguardLongPressViewModel keyguardLongPressViewModel, KeyguardInteractor keyguardInteractor, ActivityStarter activityStarter, SharedNotificationContainerInteractor sharedNotificationContainerInteractor, ActiveNotificationsInteractor activeNotificationsInteractor, HeadsUpNotificationInteractor headsUpNotificationInteractor, Lazy lazy2, Lazy lazy3, ShadeAnimationInteractor shadeAnimationInteractor, KeyguardViewConfigurator keyguardViewConfigurator, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, SplitShadeStateController splitShadeStateController, PowerInteractor powerInteractor, KeyguardClockPositionAlgorithm keyguardClockPositionAlgorithm, NaturalScrollingSettingObserver naturalScrollingSettingObserver, PrivacyDialogController privacyDialogController, KeyguardPunchHoleVIViewController.Factory factory6, NotificationShelfManager notificationShelfManager, KeyguardEditModeController keyguardEditModeController, KeyguardClickController keyguardClickController, PluginLockData pluginLockData, Lazy lazy4, IndicatorTouchHandler indicatorTouchHandler, LockscreenNotificationManager lockscreenNotificationManager, LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController, Lazy lazy5, QsStatusEventLog qsStatusEventLog, SelectedUserInteractor selectedUserInteractor) {
        int i2 = 0;
        this.mKeyguardAffordanceHelperCallback = new KeyguardAffordanceHelperCallback(this, i2);
        this.mOnHeadsUpChangedListener = new ShadeHeadsUpChangedListener(this, i2);
        this.mConfigurationListener = new ConfigurationListener(this, i2);
        this.mStatusBarStateListener = new StatusBarStateListener(this, i2);
        this.mAccessibilityDelegate = new ShadeAccessibilityDelegate(this, i2);
        this.mShadeHeadsUpTracker = new ShadeHeadsUpTrackerImpl(this, i2);
        NotificationPanelViewController$$ExternalSyntheticLambda24 notificationPanelViewController$$ExternalSyntheticLambda24 = new NotificationPanelViewController$$ExternalSyntheticLambda24();
        NotificationPanelViewController$$ExternalSyntheticLambda23 notificationPanelViewController$$ExternalSyntheticLambda23 = new NotificationPanelViewController$$ExternalSyntheticLambda23(1);
        AnimatableProperty.AnonymousClass7 anonymousClass7 = AnimatableProperty.Y;
        AnimatableProperty.AnonymousClass6 anonymousClass6 = new AnimatableProperty.AnonymousClass6(R.id.panel_alpha_animator_start_tag, R.id.panel_alpha_animator_end_tag, R.id.panel_alpha_animator_tag, new AnimatableProperty.AnonymousClass5("panelAlpha", notificationPanelViewController$$ExternalSyntheticLambda23, notificationPanelViewController$$ExternalSyntheticLambda24));
        this.mPanelAlphaAnimator = anonymousClass6;
        AnimationProperties animationProperties = new AnimationProperties();
        animationProperties.duration = 150L;
        Property property = anonymousClass6.val$property;
        Interpolator interpolator = Interpolators.ALPHA_OUT;
        animationProperties.setCustomInterpolator(property, interpolator);
        this.mPanelAlphaOutPropertiesAnimator = animationProperties;
        AnimationProperties animationProperties2 = new AnimationProperties();
        animationProperties2.duration = 200L;
        animationProperties2.mAnimationEndAction = new NotificationPanelViewController$$ExternalSyntheticLambda8(this, 8);
        animationProperties2.setCustomInterpolator(anonymousClass6.val$property, Interpolators.ALPHA_IN);
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
        this.mUseExternalTouch = false;
        this.mWillPlayDelayedDozeAmountAnimation = false;
        this.mIsOcclusionTransitionRunning = false;
        this.mForceFlingAnimationForTest = false;
        this.mFlingCollapseRunnable = new NotificationPanelViewController$$ExternalSyntheticLambda31(this, 1);
        this.mAnimateKeyguardBottomAreaInvisibleEndRunnable = new NotificationPanelViewController$$ExternalSyntheticLambda31(this, 2);
        this.mHeadsUpExistenceChangedRunnable = new NotificationPanelViewController$$ExternalSyntheticLambda31(this, 3);
        this.mMaybeHideExpandedRunnable = new NotificationPanelViewController$$ExternalSyntheticLambda31(this, 4);
        this.mDreamingToLockscreenTransition = new NotificationPanelViewController$$ExternalSyntheticLambda8(this, 9);
        this.mOccludedToLockscreenTransition = new NotificationPanelViewController$$ExternalSyntheticLambda8(this, 0);
        this.mLockscreenToDreamingTransition = new NotificationPanelViewController$$ExternalSyntheticLambda8(this, 12);
        this.mGoneToDreamingTransition = new NotificationPanelViewController$$ExternalSyntheticLambda8(this, 1);
        this.mGoneToDreamingLockscreenHostedTransition = new NotificationPanelViewController$$ExternalSyntheticLambda8(this, 2);
        this.mLockscreenToDreamingLockscreenHostedTransition = new NotificationPanelViewController$$ExternalSyntheticLambda8(this, 3);
        this.mDreamingLockscreenHostedToLockscreenTransition = new NotificationPanelViewController$$ExternalSyntheticLambda8(this, 4);
        this.mLockscreenToOccludedTransition = new NotificationPanelViewController$$ExternalSyntheticLambda8(this, 5);
        this.mIsFaceWidgetOnTouchDown = false;
        this.mFullScreenModeEnabled = false;
        this.mPanelInVisibleReason = -1;
        this.mIsKeyguardSupportLandscapePhone = false;
        this.mHeadsUpVisibleOnDown = false;
        this.shouldScrollViewIntercept = false;
        this.mPanelSplitHelper = null;
        this.mPanelTransitionStateListener = new PanelTransitionStateListener() { // from class: com.android.systemui.shade.NotificationPanelViewController.2
            public int mPanelState = 1;

            @Override // com.android.systemui.shade.PanelTransitionStateListener
            public final void onPanelTransitionStateChanged(PanelTransitionStateChangeEvent panelTransitionStateChangeEvent) {
                int i3 = this.mPanelState;
                int i4 = panelTransitionStateChangeEvent.state;
                if (i3 != i4) {
                    this.mPanelState = i4;
                    NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                    notificationPanelViewController.mView.setAccessibilityPaneTitle(notificationPanelViewController.determineAccessibilityPaneTitle());
                }
            }
        };
        this.mDownEventFromOverView = null;
        this.mShadeViewStateProvider = new ShadeViewStateProvider() { // from class: com.android.systemui.shade.NotificationPanelViewController.16
            @Override // com.android.systemui.shade.ShadeViewStateProvider
            public final KeyguardTouchAnimator getKeyguardTouchAnimator() {
                return NotificationPanelViewController.this.mKeyguardTouchAnimator;
            }

            @Override // com.android.systemui.shade.ShadeViewStateProvider
            public final float getLockscreenShadeDragProgress() {
                return NotificationPanelViewController.this.mQsController.computeExpansionFraction();
            }

            @Override // com.android.systemui.shade.ShadeViewStateProvider
            public final float getPanelViewExpandedHeight() {
                return NotificationPanelViewController.this.mExpandedHeight;
            }

            @Override // com.android.systemui.shade.ShadeViewStateProvider
            public final boolean shouldHeadsUpBeVisible() {
                HeadsUpAppearanceController headsUpAppearanceController = NotificationPanelViewController.this.mHeadsUpAppearanceController;
                return headsUpAppearanceController != null && headsUpAppearanceController.shouldBeVisible$1();
            }
        };
        this.mDataUsageLabelParent = null;
        this.mSystemUIWidgetCallback = new SystemUIWidgetCallback() { // from class: com.android.systemui.shade.NotificationPanelViewController.17
            @Override // com.android.systemui.widget.SystemUIWidgetCallback
            public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                Log.d("NotificationPanelView", "updateStyle, onNavigationColorUpdateRequired");
                CentralSurfaces centralSurfaces = NotificationPanelViewController.this.mCentralSurfaces;
                boolean isWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper("navibar");
                StatusBarWindowController statusBarWindowController = ((CentralSurfacesImpl) centralSurfaces).mStatusBarWindowController;
                int systemUiVisibility = statusBarWindowController.mStatusBarWindowView.getSystemUiVisibility();
                statusBarWindowController.mStatusBarWindowView.setSystemUiVisibility(isWhiteKeyguardWallpaper ? systemUiVisibility | 16 : systemUiVisibility & (-17));
            }
        };
        SceneContainerFlag.assertInLegacyMode();
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardStateController;
        keyguardStateControllerImpl.addCallback(new KeyguardStateController.Callback() { // from class: com.android.systemui.shade.NotificationPanelViewController.3
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
        this.mShadeRepository = shadeRepository;
        this.mShadeAnimationInteractor = shadeAnimationInteractor;
        this.mShadeLog = shadeLogger;
        this.mGutsManager = notificationGutsManager;
        this.mDreamingToLockscreenTransitionViewModel = dreamingToLockscreenTransitionViewModel;
        this.mOccludedToLockscreenTransitionViewModel = occludedToLockscreenTransitionViewModel;
        this.mLockscreenToDreamingTransitionViewModel = lockscreenToDreamingTransitionViewModel;
        this.mGoneToDreamingTransitionViewModel = goneToDreamingTransitionViewModel;
        this.mGoneToDreamingLockscreenHostedTransitionViewModel = goneToDreamingLockscreenHostedTransitionViewModel;
        this.mLockscreenToOccludedTransitionViewModel = lockscreenToOccludedTransitionViewModel;
        this.mPrimaryBouncerToGoneTransitionViewModel = primaryBouncerToGoneTransitionViewModel;
        this.mKeyguardTransitionInteractor = keyguardTransitionInteractor;
        this.mKeyguardInteractor = keyguardInteractor;
        this.mLockscreenNotificationManager = lockscreenNotificationManager;
        this.mLockscreenNotificationIconsOnlyController = lockscreenNotificationIconsOnlyController;
        this.mPowerInteractor = powerInteractor;
        this.mKeyguardViewConfigurator = keyguardViewConfigurator;
        this.mEmergencyButtonControllerFactory = factory5;
        this.mPrivacyDialogController = privacyDialogController;
        this.mClockPositionAlgorithm = keyguardClockPositionAlgorithm;
        PluginFaceWidgetManager pluginFaceWidgetManager = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
        if (pluginFaceWidgetManager != null) {
            Log.d("PluginFaceWidgetManager", "setNPVController() controller = " + this);
            pluginFaceWidgetManager.mNPVController = this;
        }
        this.mNaturalScrollingSettingObserver = naturalScrollingSettingObserver;
        this.mKeyguardTouchAnimator = keyguardTouchAnimator;
        KeyguardTouchSwipeCallback keyguardTouchSwipeCallback = new KeyguardTouchSwipeCallback() { // from class: com.android.systemui.shade.NotificationPanelViewController.4
            @Override // com.android.systemui.keyguard.animator.KeyguardTouchSwipeCallback
            public final void callUserActivity() {
                ((CentralSurfacesImpl) NotificationPanelViewController.this.mCentralSurfaces).userActivity();
            }

            @Override // com.android.systemui.keyguard.animator.KeyguardTouchSwipeCallback
            public final void onScreenOnOffAnimationEnd() {
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                Log.d("NotificationPanelView", "NotificationPanelViewController onScreenOnOffAnimationEnd");
                KeyguardBottomAreaViewController keyguardBottomAreaViewController = NotificationPanelViewController.this.mKeyguardBottomAreaViewController;
            }

            @Override // com.android.systemui.keyguard.animator.KeyguardTouchSwipeCallback
            public final void onUnlockExecuted() {
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                notificationPanelViewController.mView.post(notificationPanelViewController.mHideExpandedRunnable);
            }

            @Override // com.android.systemui.keyguard.animator.KeyguardTouchSwipeCallback
            public final void setMotionAborted() {
                NotificationPanelViewController.this.setMotionAborted();
            }
        };
        keyguardTouchAnimator.viewInjector = this;
        keyguardTouchAnimator.callback = keyguardTouchSwipeCallback;
        this.mPunchHoleVIViewControllerFactory = factory6;
        notificationPanelView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.shade.NotificationPanelViewController.5
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                notificationPanelViewController.mViewName = notificationPanelViewController.mResources.getResourceName(notificationPanelViewController.mView.getId());
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
            }
        });
        notificationPanelView.addOnLayoutChangeListener(new ShadeLayoutChangeListener(this, 0));
        TouchHandler touchHandler = getTouchHandler();
        notificationPanelView.setOnTouchListener(touchHandler);
        notificationPanelView.mTouchHandler = touchHandler;
        notificationPanelView.mOnConfigurationChangedListener = new NotificationPanelViewController$$ExternalSyntheticLambda7(this);
        Resources resources = notificationPanelView.getResources();
        this.mResources = resources;
        this.mKeyguardStateController = keyguardStateControllerImpl;
        this.mQsController = quickSettingsControllerImpl;
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
        new BounceInterpolator();
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
        this.mMediaHierarchyManager = mediaHierarchyManager;
        this.mNotificationsQSContainerController = notificationsQSContainerController;
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
        this.mStatusBarService = iStatusBarService;
        this.mSettingsChangeObserver = new SettingsChangeObserver(handler);
        this.mSplitShadeStateController = splitShadeStateController;
        ((SplitShadeStateControllerImpl) splitShadeStateController).shouldUseSplitNotificationShade();
        notificationPanelView.setWillNotDraw(true);
        this.mShadeHeaderController = shadeHeaderController;
        this.mLayoutInflater = layoutInflater;
        this.mFeatureFlags = featureFlags;
        Flags.FEATURE_FLAGS.getClass();
        this.mFalsingCollector = falsingCollector;
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
        statusBarWindowStateController.addListener(new StatusBarWindowStateListener() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda15
            @Override // com.android.systemui.statusbar.window.StatusBarWindowStateListener
            public final void onStatusBarWindowStateChanged(int i3) {
                NotificationPanelViewController.this.getClass();
            }
        });
        this.mKeyguardBypassController = keyguardBypassController;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mLockscreenShadeTransitionController = lockscreenShadeTransitionController;
        dynamicPrivacyController.mListeners.add(new DynamicPrivacyController.Listener() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda16
            @Override // com.android.systemui.statusbar.notification.DynamicPrivacyController.Listener
            public final void onDynamicPrivacyChanged() {
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                if (notificationPanelViewController.mLinearDarkAmount != 0.0f) {
                    return;
                }
                notificationPanelViewController.mAnimateNextPositionUpdate = true;
            }
        });
        quickSettingsControllerImpl.mExpansionHeightListener = new NotificationPanelViewController$$ExternalSyntheticLambda7(this);
        quickSettingsControllerImpl.mQsStateUpdateListener = new NotificationPanelViewController$$ExternalSyntheticLambda7(this);
        quickSettingsControllerImpl.mApplyClippingImmediatelyListener = new NotificationPanelViewController$$ExternalSyntheticLambda7(this);
        quickSettingsControllerImpl.mFlingQsWithoutClickListener = new NotificationPanelViewController$$ExternalSyntheticLambda7(this);
        quickSettingsControllerImpl.mExpansionHeightSetToMaxListener = new NotificationPanelViewController$$ExternalSyntheticLambda7(this);
        shadeExpansionStateManager.stateListeners.add(new ShadeStateListener() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda19
            @Override // com.android.systemui.shade.ShadeStateListener
            public final void onPanelStateChanged(int i3) {
                ShadeControllerImpl.AnonymousClass2 anonymousClass2;
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                ShadeLogger shadeLogger2 = notificationPanelViewController.mShadeLog;
                shadeLogger2.getClass();
                LogLevel logLevel = LogLevel.VERBOSE;
                ShadeLogger$logPanelStateChanged$2 shadeLogger$logPanelStateChanged$2 = new Function1() { // from class: com.android.systemui.shade.ShadeLogger$logPanelStateChanged$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("New panel State: ", ((LogMessage) obj).getStr1());
                    }
                };
                LogBuffer logBuffer = shadeLogger2.buffer;
                LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$logPanelStateChanged$2, null);
                ((LogMessageImpl) obtain).str1 = ShadeExpansionStateManagerKt.panelStateToString(i3);
                logBuffer.commit(obtain);
                QuickSettingsControllerImpl quickSettingsControllerImpl2 = notificationPanelViewController.mQsController;
                quickSettingsControllerImpl2.updateExpansionEnabledAmbient();
                if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK && i3 == 1 && !((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.sDependency.getDependencyInner(KeyguardFoldController.class))).isFoldOpened() && notificationPanelViewController.mScrimController.mState == ScrimState.AOD) {
                    notificationPanelViewController.cancelPendingCollapse(true);
                }
                NotificationPanelView notificationPanelView2 = notificationPanelViewController.mView;
                if (i3 == 2 && notificationPanelViewController.mCurrentPanelState != i3) {
                    quickSettingsControllerImpl2.setExpandImmediate(false);
                    try {
                        notificationPanelView2.sendAccessibilityEvent(32);
                    } catch (IllegalArgumentException e) {
                        Log.w("NotificationPanelView", "sendAccessibilityEvent failed. on onPanelStateChanged");
                        e.printStackTrace();
                    }
                }
                if (i3 == 1 && (anonymousClass2 = notificationPanelViewController.mOpenCloseListener) != null) {
                    ShadeControllerImpl.this.makeExpandedVisible(false);
                }
                if (i3 == 0) {
                    quickSettingsControllerImpl2.setExpandImmediate(false);
                    QuickPanelLogger quickPanelLogger = notificationPanelViewController.mQuickPanelLogger;
                    if (quickPanelLogger != null) {
                        quickPanelLogger.logPanelState("onPanelStateChanged: STATE_CLOSED");
                    }
                    notificationPanelView2.post(notificationPanelViewController.mMaybeHideExpandedRunnable);
                }
                notificationPanelViewController.mCurrentPanelState = i3;
            }
        });
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.mBottomAreaShadeAlphaAnimator = ofFloat;
        ofFloat.addUpdateListener(new NotificationPanelViewController$$ExternalSyntheticLambda20(this, 0));
        ofFloat.setDuration(160L);
        ofFloat.setInterpolator(interpolator);
        this.mConversationNotificationManager = conversationNotificationManager;
        this.mAuthController = authController;
        this.mLockIconViewController = secLockIconViewController;
        this.mScreenOffAnimationController = screenOffAnimationController;
        this.mUnlockedScreenOffAnimationController = unlockedScreenOffAnimationController;
        this.mLastDownEvents = new NPVCDownEventState.Buffer(50);
        this.mDeviceEntryFaceAuthInteractor = deviceEntryFaceAuthInteractor;
        this.mIsGestureNavigation = QuickStepContract.isGesturalMode(navigationModeController.addListener(new NavigationModeController.ModeChangedListener() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda21
            @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
            public final void onNavigationModeChanged(int i3) {
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                notificationPanelViewController.getClass();
                notificationPanelViewController.mIsGestureNavigation = QuickStepContract.isGesturalMode(i3);
            }
        }));
        notificationPanelView.setBackgroundColor(0);
        ShadeAttachStateChangeListener shadeAttachStateChangeListener = new ShadeAttachStateChangeListener(this, 0);
        notificationPanelView.addOnAttachStateChangeListener(shadeAttachStateChangeListener);
        if (notificationPanelView.isAttachedToWindow()) {
            shadeAttachStateChangeListener.onViewAttachedToWindow(notificationPanelView);
        }
        notificationPanelView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                SecQSPanel.QSTileLayout qSTileLayout;
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                notificationPanelViewController.getClass();
                Insets insetsIgnoringVisibility = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
                notificationPanelViewController.mDisplayTopInset = insetsIgnoringVisibility.top;
                int i3 = insetsIgnoringVisibility.right;
                notificationPanelViewController.mDisplayRightInset = i3;
                int i4 = insetsIgnoringVisibility.left;
                notificationPanelViewController.mDisplayLeftInset = i4;
                QuickSettingsControllerImpl quickSettingsControllerImpl2 = notificationPanelViewController.mQsController;
                quickSettingsControllerImpl2.mDisplayLeftInset = i4;
                quickSettingsControllerImpl2.mDisplayRightInset = i3;
                SecNotificationPanelViewController secNotificationPanelViewController = notificationPanelViewController.mSecNotificationPanelViewController;
                notificationPanelViewController.mNavigationBarBottomHeight = secNotificationPanelViewController != null ? notificationPanelViewController.mBarState == 0 ? windowInsets.getInsets(WindowInsets.Type.navigationBars()).bottom : windowInsets.getStableInsetBottom() : windowInsets.getStableInsetBottom();
                notificationPanelViewController.updateMaxHeadsUpTranslation();
                if (secNotificationPanelViewController != null) {
                    Context context = notificationPanelViewController.mView.getContext();
                    int i5 = notificationPanelViewController.mDisplayTopInset;
                    int i6 = notificationPanelViewController.mNavigationBarBottomHeight;
                    SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) secNotificationPanelViewController.resourcePicker$delegate.getValue();
                    SecQSPanelResourcePhonePicker targetPicker = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker();
                    targetPicker.getClass();
                    SecQSPanelResourceCommon.Companion.getClass();
                    if (SecQSPanelResourceCommon.Companion.isPortrait(context)) {
                        targetPicker.cutoutHeight = i5;
                    } else {
                        targetPicker.cutoutHeightLandscape = i5;
                    }
                    SecQSPanelResourcePhonePicker targetPicker2 = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker();
                    targetPicker2.getClass();
                    if (SecQSPanelResourceCommon.Companion.isPortrait(context)) {
                        targetPicker2.navBarHeight = i6;
                    } else {
                        targetPicker2.navBarHeightLandscape = i6;
                    }
                    SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = secNotificationPanelViewController.secQuickSettingsControllerImpl;
                    if (secQuickSettingsControllerImpl != null && (i5 != secQuickSettingsControllerImpl.lastDisplayTopInset || i6 != secQuickSettingsControllerImpl.lastNavigationBarBottomHeight)) {
                        secQuickSettingsControllerImpl.lastDisplayTopInset = i5;
                        secQuickSettingsControllerImpl.lastNavigationBarBottomHeight = i6;
                        SecQSPanelController qsPanelController = secQuickSettingsControllerImpl.getQsPanelController();
                        if (qsPanelController != null && (qSTileLayout = qsPanelController.mTileLayout) != null) {
                            qSTileLayout.updateResources();
                            qsPanelController.updatePaddingAndMargins();
                        }
                    }
                    secNotificationPanelViewController.notificationsQSContainerController.updateConstraints$1();
                    Insets insets = windowInsets.getInsets(WindowInsets.Type.systemGestures());
                    SecPanelSplitHelper secPanelSplitHelper = secNotificationPanelViewController.panelSplitHelper;
                    if (secPanelSplitHelper != null) {
                        secPanelSplitHelper.panelSlideEventHandler.gestureInsets = insets;
                    }
                }
                if (QpRune.QUICK_DATA_USAGE_LABEL) {
                    ((DataUsageLabelManager) notificationPanelViewController.mDataUsageLabelManagerLazy.get()).updateNavBarHeight(notificationPanelViewController.mNavigationBarBottomHeight);
                }
                return windowInsets;
            }
        });
        this.mPluginLockMediator = pluginLockMediator;
        pluginLockMediator.registerStateCallback(this);
        this.mKeyguardUnfoldTransition = optional.map(new NotificationPanelViewController$$ExternalSyntheticLambda23(0));
        updateUserSwitcherFlags();
        this.mKeyguardBottomAreaViewModel = keyguardBottomAreaViewModel;
        this.mKeyguardBottomAreaInteractor = keyguardBottomAreaInteractor;
        this.mActivityStarter = activityStarter;
        if (CscRune.KEYGUARD_DCM_LIVE_UX) {
            this.mMascotViewContainer = dcmMascotViewContainer;
        }
        onFinishInflate();
        keyguardUnlockAnimationController.listeners.add(new KeyguardUnlockAnimationController.KeyguardUnlockAnimationListener() { // from class: com.android.systemui.shade.NotificationPanelViewController.6
            @Override // com.android.systemui.keyguard.KeyguardUnlockAnimationController.KeyguardUnlockAnimationListener
            public final void onUnlockAnimationFinished() {
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                notificationPanelViewController.getClass();
                if (SafeUIState.isSysUiSafeModeEnabled()) {
                    return;
                }
                notificationPanelViewController.positionClockAndNotifications(true);
                ScrimController scrimController2 = notificationPanelViewController.mScrimController;
                scrimController2.mAnimatingPanelExpansionOnUnlock = false;
                scrimController2.applyAndDispatchState();
            }

            @Override // com.android.systemui.keyguard.KeyguardUnlockAnimationController.KeyguardUnlockAnimationListener
            public final void onUnlockAnimationStarted(boolean z, boolean z2) {
                PluginKeyguardStatusView pluginKeyguardStatusView;
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                notificationPanelViewController.getClass();
                PluginFaceWidgetManager pluginFaceWidgetManager2 = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
                if (pluginFaceWidgetManager2 != null && (pluginKeyguardStatusView = pluginFaceWidgetManager2.mFaceWidgetPlugin) != null) {
                    pluginKeyguardStatusView.dismissFaceWidgetDashBoard();
                }
                boolean isTracking = notificationPanelViewController.isTracking();
                NotificationShadeDepthController notificationShadeDepthController2 = notificationPanelViewController.mDepthController;
                if (notificationShadeDepthController2.blursDisabledForUnlock != isTracking) {
                    notificationShadeDepthController2.blursDisabledForUnlock = isTracking;
                }
                if (!z || z2) {
                    return;
                }
                if (notificationPanelViewController.isTracking() || notificationPanelViewController.mIsFlinging) {
                    notificationPanelViewController.onTrackingStopped(false);
                    notificationPanelViewController.instantCollapse();
                }
            }
        });
        this.mAlternateBouncerInteractor = alternateBouncerInteractor;
        dumpManager.registerDumpable(this);
        this.mQuickPanelLogger = new QuickPanelLogger("NPVC");
        this.mQuickPanelLogBuilder = new StringBuilder();
        int i3 = 1;
        this.mSecNotificationPanelViewController = new SecNotificationPanelViewController(lockscreenShadeTransitionController, notificationsQSContainerController, quickSettingsControllerImpl, shadeHeaderController, shadeRepository, new NotificationPanelViewController$$ExternalSyntheticLambda0(this, i3), new NotificationPanelViewController$$ExternalSyntheticLambda0(this, 2), new NotificationPanelViewController$$ExternalSyntheticLambda1(this, i3), new NotificationPanelViewController$$ExternalSyntheticLambda0(this, 3), new NotificationPanelViewController$$ExternalSyntheticLambda3(this, i3), new NotificationPanelViewController$$ExternalSyntheticLambda8(this, 6), new NotificationPanelViewController$$ExternalSyntheticLambda8(this, 7));
        if (LsRune.SUBSCREEN_WATCHFACE) {
            this.mSubScreenManagerLazy = lazy2;
        }
        if (LsRune.COVER_VIRTUAL_DISPLAY) {
            this.mCoverScreenManagerLazy = lazy3;
        }
        this.mPluginAODManagerLazy = lazy;
        this.mShelfManager = notificationShelfManager;
        notificationShelfManager.notificationPanelController = this;
        this.mMultiWindowEdgeDetector = new MultiWindowEdgeDetector(this.mView.getContext(), "QuickPannel");
        this.mIndicatorTouchHandler = indicatorTouchHandler;
        lockscreenNotificationIconsOnlyController.getClass();
        Log.d("LockscreenNotificationIconsOnlyController", "setNPVController() controller = " + this);
        lockscreenNotificationIconsOnlyController.mNPVController = this;
        this.mPostCollapseRunnable = new NotificationPanelViewController$$ExternalSyntheticLambda31(this, 0);
        if (QpRune.QUICK_DATA_USAGE_LABEL) {
            this.mDataUsageLabelManagerLazy = lazy5;
        }
        this.mKeyguardEditModeController = keyguardEditModeController;
        KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = (KeyguardEditModeControllerImpl) keyguardEditModeController;
        keyguardEditModeControllerImpl.bind(this.mView);
        keyguardEditModeControllerImpl.onStartActivityListener = new Function0() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda32
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
        ((ArrayList) keyguardEditModeControllerImpl.listeners).add(new KeyguardEditModeController.Listener() { // from class: com.android.systemui.shade.NotificationPanelViewController.7
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
        ((KeyguardClickControllerImpl) keyguardClickController).isClockContainerArea = new Function2() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda33
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                View clockView;
                int intValue = ((Integer) obj).intValue();
                int intValue2 = ((Integer) obj2).intValue();
                KeyguardStatusViewController keyguardStatusViewController = NotificationPanelViewController.this.mKeyguardStatusViewController;
                if (keyguardStatusViewController == null || (clockView = keyguardStatusViewController.getClockView()) == null) {
                    return Boolean.FALSE;
                }
                Rect rect = new Rect();
                clockView.getGlobalVisibleRect(rect);
                return Boolean.valueOf(rect.contains(intValue, intValue2));
            }
        };
        this.mPluginLockData = pluginLockData;
        this.mPluginLockStarManagerLazy = lazy4;
        ((PluginLockStarManager) lazy4.get()).registerCallback("NotificationPanelViewController", this.mLockStarCallback);
        updateLockStarContainer();
        ((PluginLockStarManager) lazy4.get()).mShortcutController.bottomAreaCallback = keyguardBottomAreaViewController;
        this.mQuickQsOffsetHeight = SystemBarUtils.getQuickQsOffsetHeight(this.mView.getContext());
        PanelScreenShotLogger.INSTANCE.addLogProvider("NotificationPanelView", this);
        this.mKeyguardWallpaperController = keyguardWallpaperController;
        this.mWallpaperImageCreator = wallpaperImageInjectCreator;
        this.mQsStatusEventLog = qsStatusEventLog;
        SecPanelSplitHelper secPanelSplitHelper = (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
        this.mPanelSplitHelper = secPanelSplitHelper;
        secPanelSplitHelper.addListener(this.mPanelTransitionStateListener);
        this.mSelectedUserInteractor = selectedUserInteractor;
    }

    public final void abortAnimations() {
        cancelHeightAnimator();
        NotificationPanelViewController$$ExternalSyntheticLambda31 notificationPanelViewController$$ExternalSyntheticLambda31 = this.mPostCollapseRunnable;
        NotificationPanelView notificationPanelView = this.mView;
        if (notificationPanelViewController$$ExternalSyntheticLambda31 != null) {
            notificationPanelView.removeCallbacks(notificationPanelViewController$$ExternalSyntheticLambda31);
        }
        notificationPanelView.removeCallbacks(this.mFlingCollapseRunnable);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeBackActionInteractor
    public final void animateCollapseQs(boolean z) {
        if (SecPanelSplitHelper.isEnabled()) {
            collapse(1.0f, true, false);
            return;
        }
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        ValueAnimator valueAnimator = quickSettingsControllerImpl.mExpansionAnimator;
        if (valueAnimator != null) {
            if (!quickSettingsControllerImpl.mAnimatorExpand) {
                return;
            }
            float f = quickSettingsControllerImpl.mExpansionHeight;
            valueAnimator.cancel();
            quickSettingsControllerImpl.setExpansionHeight(f);
        }
        quickSettingsControllerImpl.flingQs(0.0f, z ? 2 : 1, null, false);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void blockExpansionForCurrentTouch() {
        this.mBlockingExpansionForCurrentTouch = isTracking();
    }

    public final int calculatePanelHeightShade() {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        int height = notificationStackScrollLayoutController.mView.getHeight() - notificationStackScrollLayoutController.mView.getEmptyBottomMargin();
        return this.mBarState == 1 ? Math.max(height, this.mClockPositionAlgorithm.mKeyguardStatusHeight + ((int) notificationStackScrollLayoutController.mView.mIntrinsicContentHeight)) : height;
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeBackActionInteractor
    public final boolean canBeCollapsed() {
        CentralSurfaces centralSurfaces = this.mCentralSurfaces;
        if (centralSurfaces != null && (((CentralSurfacesImpl) centralSurfaces).mCommandQueueCallbacks.mDisabled1 & 65536) != 0) {
            return true;
        }
        if (!isFullyCollapsed() && !isTracking() && !isClosing()) {
            Flags.FEATURE_FLAGS.getClass();
            if (!SecPanelSplitHelper.isEnabled() || !this.mPanelSplitHelper.panelSlideEventHandler.tracking) {
                return true;
            }
        }
        return false;
    }

    public boolean canCollapsePanelOnTouch() {
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        if (!quickSettingsControllerImpl.getExpanded$1() && this.mBarState == 1) {
            return true;
        }
        NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
        if (notificationStackScrollLayout.mOwnScrollY >= notificationStackScrollLayout.getScrollRange$1()) {
            return true;
        }
        SecNotificationPanelViewController secNotificationPanelViewController = this.mSecNotificationPanelViewController;
        if (secNotificationPanelViewController == null) {
            return quickSettingsControllerImpl.getExpanded$1() || this.mIsPanelCollapseOnQQS;
        }
        boolean z = this.mIsPanelCollapseOnQQS;
        boolean z2 = quickSettingsControllerImpl.getExpanded$1() || this.mIsPanelCollapseOnQQS;
        SecPanelSplitHelper.Companion.getClass();
        if (!SecPanelSplitHelper.isEnabled) {
            return z2;
        }
        SecPanelSplitHelper secPanelSplitHelper = secNotificationPanelViewController.panelSplitHelper;
        if (secPanelSplitHelper == null || !secPanelSplitHelper.isQSState()) {
            return z;
        }
        return true;
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void cancelAnimation() {
        this.mView.animate().cancel();
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

    @Override // com.android.systemui.shade.ShadeViewController
    public final void cancelInputFocusTransfer() {
        Log.d("NotificationPanelView", "cancelInputFocusTransfer");
        if (this.mCommandQueue.panelsEnabled()) {
            if (!this.mExpectingSynthesizedDown) {
                Log.d("NotificationPanelView", "couldn't call onTrackingStopped() by mExpectingSynthesizedDown");
                return;
            }
            this.mExpectingSynthesizedDown = false;
            collapse(1.0f, false);
            onTrackingStopped(false);
        }
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void cancelPendingCollapse(boolean z) {
        NotificationPanelView notificationPanelView = this.mView;
        if (z) {
            notificationPanelView.removeCallbacks(this.mHideExpandedRunnable);
        }
        com.android.systemui.keyguard.Log.d("KeyguardVisible", "cancelPendingPanelCollapse " + z);
        ((KeyguardFastBioUnlockController) Dependency.sDependency.getDependencyInner(KeyguardFastBioUnlockController.class)).reset();
        if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK) {
            ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).mHelper.resetForceInvisible(false);
        }
        notificationPanelView.removeCallbacks(this.mMaybeHideExpandedRunnable);
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void closeQsIfPossible() {
        if (!isShadeFullyExpanded()) {
            isExpandingOrCollapsing();
        }
        if (((StatusBarStateControllerImpl) this.mStatusBarStateController).mIsDozing && ((!SecPanelSplitHelper.isEnabled() || !this.mPanelSplitHelper.isShadeState()) && this.mBarState == 2)) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
            notificationStackScrollLayoutController.mView.setVisibility(4);
            notificationStackScrollLayoutController.mBlockHideAmountVisibility = true;
        }
        this.mQsController.closeQs();
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeBackActionInteractor
    public final boolean closeUserSwitcherIfOpen() {
        KeyguardUserSwitcherController keyguardUserSwitcherController = this.mKeyguardUserSwitcherController;
        if (keyguardUserSwitcherController != null) {
            return keyguardUserSwitcherController.closeSwitcherIfOpenAndNotSimple(true);
        }
        return false;
    }

    public final void collapse(float f, boolean z, boolean z2) {
        if (!z || isFullyCollapsed()) {
            resetViews(false, false);
            setExpandedFraction(0.0f);
            ShadeExpansionStateManager shadeExpansionStateManager = this.mShadeExpansionStateManager;
            ShadeExpansionStateManagerKt.panelStateToString(shadeExpansionStateManager.state);
            ShadeExpansionStateManagerKt.panelStateToString(0);
            if (shadeExpansionStateManager.state != 0) {
                shadeExpansionStateManager.updateStateInternal(0);
            }
        } else {
            collapse(f, z2);
        }
        ((NotificationGutsManager) Dependency.sDependency.getDependencyInner(NotificationGutsManager.class)).mIsGoingGutOpenedFromLock = false;
    }

    public int computeMaxKeyguardNotifications() {
        int i;
        PluginLockStarManager pluginLockStarManager = (PluginLockStarManager) this.mPluginLockStarManagerLazy.get();
        if (pluginLockStarManager != null) {
            try {
                LockStarValues lockStarValues = pluginLockStarManager.getLockStarValues();
                if (lockStarValues != null) {
                    this.mRecomputedReason = "TYPE_N_CARD";
                    return lockStarValues.getDetailNotificationCardCount();
                }
            } catch (Throwable th) {
                Log.e("NotificationPanelView", "getNotificationCardCount() : error " + th.getMessage());
            }
        }
        if (this.mPluginLockMediator.isDynamicLockEnabled() && (i = this.mNotiCardCount) != -1) {
            this.mRecomputedReason = "isDynamicLockEnabled";
            return i;
        }
        if (this.mAmbientState.mFractionToShade > 0.0f) {
            this.mRecomputedReason = "mAmbientState.getFractionToShade()";
            return this.mMaxAllowedKeyguardNotifications;
        }
        this.mRecomputedReason = "computeMaxKeyguardNotifications";
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        return this.mNotificationStackSizeCalculator.computeMaxKeyguardNotifications(notificationStackScrollLayoutController.mView, this.getVerticalSpaceForLockscreenNotifications(), this.getVerticalSpaceForLockscreenShelf(), notificationStackScrollLayoutController.mView.mShelf == null ? 0 : r0.getHeight());
    }

    public final String determineAccessibilityPaneTitle() {
        SecPanelSplitHelper secPanelSplitHelper;
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        if (quickSettingsControllerImpl != null && quickSettingsControllerImpl.isCustomizing()) {
            return this.mResources.getString(R.string.accessibility_desc_quick_settings_edit);
        }
        if (!SecPanelSplitHelper.isEnabled() || (secPanelSplitHelper = this.mPanelSplitHelper) == null) {
            return (quickSettingsControllerImpl == null || quickSettingsControllerImpl.mExpansionHeight == 0.0f || !quickSettingsControllerImpl.mFullyExpanded) ? this.mBarState == 1 ? this.mUpdateMonitor.isKeyguardUnlocking() ? "" : this.mResources.getString(R.string.ksh_group_system_lock_screen) : this.mResources.getString(R.string.accessibility_desc_notification_shade) : this.mResources.getString(R.string.accessibility_desc_quick_settings);
        }
        if (QpRune.QUICK_DATA_USAGE_LABEL) {
            ((DataUsageLabelManager) this.mDataUsageLabelManagerLazy.get()).updateLabelVisibility(false);
        }
        if (secPanelSplitHelper.isQSState()) {
            return this.mResources.getString(R.string.accessibility_desc_quick_settings);
        }
        if (secPanelSplitHelper.isShadeState()) {
            return this.mResources.getString(R.string.accessibility_desc_notification_shade);
        }
        return null;
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void dozeTimeTick() {
        PluginKeyguardStatusView pluginKeyguardStatusView;
        Lazy lazy;
        Lazy lazy2;
        this.mLockIconViewController.getClass();
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        KeyguardStatusViewController keyguardStatusViewController = this.mKeyguardStatusViewController;
        keyguardStatusViewController.refreshTime();
        keyguardStatusViewController.mKeyguardSliceViewController.refresh();
        PluginFaceWidgetManager pluginFaceWidgetManager = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
        if (pluginFaceWidgetManager == null) {
            Log.e("NotificationPanelView", "dozeTimeTick() Failed to get PluginFaceWidgetManager");
        } else {
            FaceWidgetContainerWrapper faceWidgetContainerWrapper = pluginFaceWidgetManager.mFaceWidgetContainerWrapper;
            if (faceWidgetContainerWrapper != null && (pluginKeyguardStatusView = faceWidgetContainerWrapper.mPluginKeyguardStatusView) != null) {
                pluginKeyguardStatusView.dozeTimeTick();
            }
        }
        if (this.mInterpolatedDarkAmount > 0.0f) {
            positionClockAndNotifications(false);
        }
        if (LsRune.SUBSCREEN_WATCHFACE && (lazy2 = this.mSubScreenManagerLazy) != null) {
            SubScreenManager subScreenManager = (SubScreenManager) lazy2.get();
            if (subScreenManager.mSubScreenPlugin == null) {
                Log.w("SubScreenManager", "dozeTimeTick() no plugin");
            } else {
                Log.d("SubScreenManager", "dozeTimeTick() ");
                subScreenManager.mSubScreenPlugin.dozeTimeTick();
            }
        }
        if (!LsRune.COVER_VIRTUAL_DISPLAY || (lazy = this.mCoverScreenManagerLazy) == null) {
            return;
        }
        CoverScreenManager coverScreenManager = (CoverScreenManager) lazy.get();
        if (coverScreenManager.mCoverPlugin == null) {
            Log.w("CoverScreenManager", "dozeTimeTick() no plugin");
        } else {
            Log.d("CoverScreenManager", "dozeTimeTick() ");
            coverScreenManager.mCoverPlugin.dozeTimeTick();
        }
    }

    @Override // com.android.systemui.Dumpable
    @NeverCompile
    public final void dump(PrintWriter printWriter, String[] strArr) {
        Lazy lazy;
        ViewGroup parentViewGroup;
        printWriter.println("NotificationPanelView:");
        PrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.increaseIndent();
        KeyguardBottomAreaViewController keyguardBottomAreaViewController = this.mKeyguardBottomAreaViewController;
        if (keyguardBottomAreaViewController != null) {
            keyguardBottomAreaViewController.dump(printWriter, strArr);
        }
        if (QpRune.QUICK_DATA_USAGE_LABEL && (lazy = this.mDataUsageLabelManagerLazy) != null) {
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
        asIndenting.println(isLaunchingActivity$1());
        asIndenting.print("mOverExpansion=");
        asIndenting.println(this.mOverExpansion);
        asIndenting.print("mExpandedHeight=");
        asIndenting.println(this.mExpandedHeight);
        asIndenting.print("isTracking()=");
        asIndenting.println(isTracking());
        asIndenting.print("mExpanding=");
        asIndenting.println(this.mExpanding);
        asIndenting.print("mSplitShadeEnabled=");
        asIndenting.println(false);
        asIndenting.print("mKeyguardNotificationBottomPadding=");
        asIndenting.println(this.mKeyguardNotificationBottomPadding);
        asIndenting.print("mKeyguardNotificationTopPadding=");
        asIndenting.println(this.mKeyguardNotificationTopPadding);
        asIndenting.print("mMaxAllowedKeyguardNotifications=");
        asIndenting.println(this.mMaxAllowedKeyguardNotifications);
        asIndenting.print("mAnimateNextPositionUpdate=");
        asIndenting.println(this.mAnimateNextPositionUpdate);
        asIndenting.print("isPanelExpanded()=");
        asIndenting.println(isPanelExpanded());
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
        asIndenting.print("mHeadsUpVisible=");
        asIndenting.println(false);
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
        asIndenting.print("isClosing()=");
        asIndenting.println(isClosing());
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
        StringBuilder sb5 = new StringBuilder("gestureExclusionRect:");
        Region calculateTouchableRegion = this.mStatusBarTouchableRegionManager.calculateTouchableRegion();
        Rect bounds = (!isFullyCollapsed() || calculateTouchableRegion == null) ? null : calculateTouchableRegion.getBounds();
        if (bounds == null) {
            bounds = EMPTY_RECT;
        }
        sb5.append(bounds);
        asIndenting.println(sb5.toString());
        Trace.beginSection("Table<DownEvents>");
        List list = NPVCDownEventState.TABLE_HEADERS;
        NPVCDownEventState.Buffer buffer = this.mLastDownEvents;
        buffer.getClass();
        RingBuffer ringBuffer = buffer.buffer;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(ringBuffer, 10));
        RingBuffer$iterator$1 ringBuffer$iterator$1 = new RingBuffer$iterator$1(ringBuffer);
        while (ringBuffer$iterator$1.hasNext()) {
            arrayList.add((List) ((NPVCDownEventState) ringBuffer$iterator$1.next()).asStringList$delegate.getValue());
        }
        new DumpsysTableLogger("NotificationPanelView", list, arrayList).printTableData(asIndenting);
        Trace.endSection();
    }

    public final void endClosing() {
        if (isClosing()) {
            setClosing(false);
            ShadeControllerImpl.AnonymousClass2 anonymousClass2 = this.mOpenCloseListener;
            if (anonymousClass2 != null) {
                ShadeControllerImpl.this.onClosingFinished$1();
            }
            this.mClosingWithAlphaFadeOut = false;
            this.mNotificationStackScrollLayoutController.mView.mForceNoOverlappingRendering = false;
            MediaHierarchyManager mediaHierarchyManager = this.mMediaHierarchyManager;
            mediaHierarchyManager.getClass();
            MediaCarouselController.Companion companion = MediaCarouselController.Companion;
            mediaHierarchyManager.mediaCarouselController.closeGuts(true);
            setExpandSettingsPanel$1(false);
            this.mQsStatusEventLog.startTimer();
        }
    }

    public final void expand(boolean z) {
        StringBuilder sb;
        if (isFullyCollapsed() || isCollapsing()) {
            QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
            if (quickPanelLogger != null && (sb = this.mQuickPanelLogBuilder) != null) {
                sb.setLength(0);
                sb.append("expand: ");
                sb.append("animate: ");
                sb.append(z);
                sb.append(", mInstantExpanding: ");
                sb.append(this.mInstantExpanding);
                sb.append(" -> true");
                sb.append(", mAnimateAfterExpanding: ");
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, this.mAnimateAfterExpanding, " -> ", z, ", mUpdateFlingOnLayout: ");
                sb.append(this.mUpdateFlingOnLayout);
                sb.append(" -> false");
                sb.append(", isTracking(): ");
                sb.append(isTracking());
                sb.append(", mExpanding: ");
                sb.append(this.mExpanding);
                quickPanelLogger.logPanelState(sb.toString());
            }
            this.mInstantExpanding = true;
            this.mAnimateAfterExpanding = z;
            this.mUpdateFlingOnLayout = false;
            abortAnimations();
            if (isTracking()) {
                onTrackingStopped(true);
            }
            if (this.mExpanding) {
                notifyExpandingFinished();
            }
            updateExpansionAndVisibility();
            NotificationPanelView notificationPanelView = this.mView;
            notificationPanelView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.shade.NotificationPanelViewController.13
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                    if (!notificationPanelViewController.mInstantExpanding) {
                        notificationPanelViewController.mView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        return;
                    }
                    if (!((NotificationShadeWindowControllerImpl) notificationPanelViewController.mNotificationShadeWindowController).mWindowRootView.isVisibleToUser()) {
                        QuickPanelLogger quickPanelLogger2 = NotificationPanelViewController.this.mQuickPanelLogger;
                        if (quickPanelLogger2 != null) {
                            quickPanelLogger2.logPanelState("onGlobalLayout: NotificationShadeWindowView.isVisibleToUser == false");
                            return;
                        }
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
                    QuickPanelLogger quickPanelLogger3 = notificationPanelViewController4.mQuickPanelLogger;
                    if (quickPanelLogger3 != null) {
                        quickPanelLogger3.logPanelState("onGlobalLayout: mAnimateAfterExpanding: " + NotificationPanelViewController.this.mAnimateAfterExpanding);
                    }
                }
            });
            notificationPanelView.requestLayout();
        }
        setListening$3(true);
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void expandQSForOpenDetail() {
        setExpandSettingsPanel$1(true);
        expandToQs();
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void expandToNotifications() {
        if (this.mSecNotificationPanelViewController != null) {
            if (SecPanelSplitHelper.isEnabled()) {
                this.mPanelSplitHelper.stateOnDown = 0;
            }
            CentralSurfaces centralSurfaces = this.mCentralSurfaces;
            Objects.requireNonNull(centralSurfaces);
            if (isOnKeyguard$1() && !((CentralSurfacesImpl) centralSurfaces).mBouncerShowing && !this.mFullScreenModeEnabled) {
                boolean isNeedsToExpandLocksNoti = ((AmbientState) Dependency.sDependency.getDependencyInner(AmbientState.class)).isNeedsToExpandLocksNoti();
                LockscreenShadeTransitionController lockscreenShadeTransitionController = this.mLockscreenShadeTransitionController;
                if (!isNeedsToExpandLocksNoti) {
                    lockscreenShadeTransitionController.goToLockedShade(null, true);
                    return;
                } else {
                    lockscreenShadeTransitionController.onDragDownStarted$frameworks__base__packages__SystemUI__android_common__SystemUI_core(null);
                    lockscreenShadeTransitionController.touchHelper.animateToMaxDragDown(true);
                    return;
                }
            }
        }
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        if (quickSettingsControllerImpl.getExpanded$1()) {
            quickSettingsControllerImpl.flingQs(0.0f, 1, null, false);
        } else {
            expand(true);
        }
    }

    public final void expandToQs() {
        SecNotificationPanelViewController secNotificationPanelViewController = this.mSecNotificationPanelViewController;
        if (secNotificationPanelViewController != null) {
            CentralSurfaces centralSurfaces = this.mCentralSurfaces;
            Objects.requireNonNull(centralSurfaces);
            if (isOnKeyguard$1() && (((CentralSurfacesImpl) centralSurfaces).mBouncerShowing || this.mFullScreenModeEnabled)) {
                return;
            }
            SecPanelSplitHelper.Companion.getClass();
            if (SecPanelSplitHelper.isEnabled) {
                if (isOnKeyguard$1()) {
                    this.mLockscreenShadeTransitionController.goToLockedShade(null, true);
                } else if (isFullyCollapsed()) {
                    expand(true);
                }
                SecPanelSplitHelper secPanelSplitHelper = secNotificationPanelViewController.panelSplitHelper;
                if (secPanelSplitHelper != null) {
                    SecPanelSplitHelper secPanelSplitHelper2 = secPanelSplitHelper.isQSState() ? null : secPanelSplitHelper;
                    if (secPanelSplitHelper2 != null) {
                        secPanelSplitHelper2.slide$1(0);
                        return;
                    }
                    return;
                }
                return;
            }
        }
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        if (quickSettingsControllerImpl.isExpansionEnabled()) {
            quickSettingsControllerImpl.setExpandImmediate(true);
            setShowShelfOnly(true);
        }
        if (isFullyCollapsed()) {
            expand(true);
        } else {
            quickSettingsControllerImpl.traceQsJank(true, false);
            quickSettingsControllerImpl.flingQs(0.0f, 0, null, false);
        }
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void finishInputFocusTransfer(float f) {
        SecPanelSplitHelper secPanelSplitHelper;
        MotionEvent motionEvent;
        Log.d("NotificationPanelView", "finishInputFocusTransfer");
        if (this.mCommandQueue.panelsEnabled()) {
            if (!this.mExpectingSynthesizedDown) {
                Log.d("NotificationPanelView", "couldn't call onTrackingStopped() by mExpectingSynthesizedDown");
                onTrackingStopped(false);
                return;
            }
            this.mExpectingSynthesizedDown = false;
            maybeVibrateOnOpening(false);
            if (SecPanelSplitHelper.isEnabled() && (secPanelSplitHelper = this.mPanelSplitHelper) != null && (motionEvent = this.mDownEventFromOverView) != null) {
                secPanelSplitHelper.shouldQSDown(motionEvent);
            }
            fling(f > 1.0f ? f * 1000.0f : 0.0f);
            ((BaseHeadsUpManager) this.mHeadsUpManager).unpinAll();
            onTrackingStopped(false);
        }
    }

    public final void fling(float f) {
        GestureRecorder gestureRecorder = this.mGestureRecorder;
        if (gestureRecorder != null) {
            String concat = "fling ".concat(f > 0.0f ? ServiceTuple.BASIC_STATUS_OPEN : ServiceTuple.BASIC_STATUS_CLOSED);
            String str = "notifications,v=" + f;
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            synchronized (gestureRecorder.mGestures) {
                try {
                    if (gestureRecorder.mCurrentGesture == null) {
                        GestureRecorder.Gesture gesture = new GestureRecorder.Gesture(gestureRecorder);
                        gestureRecorder.mCurrentGesture = gesture;
                        gestureRecorder.mGestures.add(gesture);
                    }
                    GestureRecorder.Gesture gesture2 = gestureRecorder.mCurrentGesture;
                    gesture2.mRecords.add(new GestureRecorder.Gesture.TagRecord(gesture2, uptimeMillis, concat, str));
                    gesture2.mTags.add(concat);
                } catch (Throwable th) {
                    throw th;
                }
            }
            GestureRecorder.AnonymousClass1 anonymousClass1 = gestureRecorder.mHandler;
            anonymousClass1.removeMessages(6351);
            anonymousClass1.sendEmptyMessageDelayed(6351, 5000L);
        }
        fling(f, 1.0f, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01cd  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0167  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void flingToHeight(float r26, boolean r27, final float r28, float r29, boolean r30) {
        /*
            Method dump skipped, instructions count: 613
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.NotificationPanelViewController.flingToHeight(float, boolean, float, float, boolean):void");
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        PanelScreenShotLogger.INSTANCE.getClass();
        PanelScreenShotLogger.addHeaderLine("NotificationPanelViewController", arrayList);
        int i = this.mClockPositionResult.stackScrollerPadding;
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        PanelScreenShotLogger.addLogItem(arrayList, "calculatePanelHeightQsExpanded", Integer.valueOf(quickSettingsControllerImpl.calculatePanelHeightExpanded(i)));
        PanelScreenShotLogger.addLogItem(arrayList, "calculatePanelHeightShade", Integer.valueOf(calculatePanelHeightShade()));
        PanelScreenShotLogger.addLogItem(arrayList, "mHeadsUpInset", Integer.valueOf(this.mHeadsUpInset));
        PanelScreenShotLogger.addLogItem(arrayList, "getKeyguardNotificationStaticPadding", Integer.valueOf(getKeyguardNotificationStaticPadding()));
        PanelScreenShotLogger.addLogItem(arrayList, "mQsMaxExpansionHeight", Integer.valueOf(quickSettingsControllerImpl.mMaxExpansionHeight));
        PanelScreenShotLogger.addLogItem(arrayList, "mQsExpansionHeight", Float.valueOf(quickSettingsControllerImpl.mExpansionHeight));
        PanelScreenShotLogger.addLogItem(arrayList, "computeQsExpansionFraction", Float.valueOf(quickSettingsControllerImpl.computeExpansionFraction()));
        PanelScreenShotLogger.addLogItem(arrayList, "mTransitioningToFullShadeProgress", Float.valueOf(0.0f));
        PanelScreenShotLogger.addLogItem(arrayList, "mOverStretchAmount", Float.valueOf(this.mOverStretchAmount));
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

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final int getBarState() {
        return this.mBarState;
    }

    public final int getCutoutHeight$1() {
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
            LockscreenShadeTransitionController lockscreenShadeTransitionController = this.mLockscreenShadeTransitionController;
            if (lockscreenShadeTransitionController.getFractionToShade() > 0.0f) {
                float fractionToShade = lockscreenShadeTransitionController.getFractionToShade();
                f = NotificationUtils.interpolate(1.0f, 0.0f, ((double) fractionToShade) > 0.5d ? 1.0f : fractionToShade * 2.0f);
            } else {
                if (this.mClockPositionAlgorithm.isPanelExpanded()) {
                    QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
                    if (quickSettingsControllerImpl.getExpanded$1()) {
                        float computeExpansionFraction = quickSettingsControllerImpl.computeExpansionFraction();
                        f = NotificationUtils.interpolate(1.0f, 0.0f, ((double) computeExpansionFraction) > 0.3d ? 1.0f : computeExpansionFraction * 3.0f);
                    }
                }
                f = 1.0f;
            }
        }
        if (((KeyguardEditModeControllerImpl) this.mKeyguardEditModeController).getVIRunning()) {
            return 1.0f;
        }
        return f;
    }

    public final int getFalsingThreshold() {
        float f;
        WakefulnessModel wakefulnessModel = (WakefulnessModel) this.mPowerInteractor.detailedWakefulness.$$delegate_0.getValue();
        ShadeViewController.Companion.getClass();
        if (wakefulnessModel.isAwake()) {
            WakeSleepReason wakeSleepReason = WakeSleepReason.TAP;
            WakeSleepReason wakeSleepReason2 = wakefulnessModel.lastWakeReason;
            if (wakeSleepReason2 == wakeSleepReason || wakeSleepReason2 == WakeSleepReason.GESTURE) {
                f = 1.5f;
                return (int) (this.mQsController.mFalsingThreshold * f);
            }
        }
        f = 1.0f;
        return (int) (this.mQsController.mFalsingThreshold * f);
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final KeyguardBottomAreaViewController getKeyguardBottomAreaViewController() {
        return this.mKeyguardBottomAreaViewController;
    }

    public final int getKeyguardNotificationStaticPadding() {
        SceneContainerFlag.assertInLegacyMode();
        int i = 0;
        if (!isKeyguardShowing$2()) {
            return 0;
        }
        ComposeLockscreen composeLockscreen = ComposeLockscreen.INSTANCE;
        Flags.composeLockscreen();
        boolean bypassEnabled = this.mKeyguardBypassController.getBypassEnabled();
        KeyguardClockPositionAlgorithm.Result result = this.mClockPositionResult;
        if (bypassEnabled) {
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
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        if (!CscRune.KEYGUARD_DCM_LIVE_UX) {
            return result.stackScrollerPadding;
        }
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        if (lockscreenNotificationIconsOnlyController != null) {
            PluginNotificationController pluginNotificationController = lockscreenNotificationIconsOnlyController.mNotificationControllerWrapper.mNotificationController;
            if ((pluginNotificationController != null ? pluginNotificationController.getIconContainer() : null) != null) {
                PluginNotificationController pluginNotificationController2 = lockscreenNotificationIconsOnlyController.mNotificationControllerWrapper.mNotificationController;
                i = (pluginNotificationController2 != null ? pluginNotificationController2.getIconContainer() : null).getHeight();
            }
        }
        int i4 = result.stackScrollerPadding;
        return this.mMascotViewContainer.updatePosition(i4, i) + i4;
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final int getMaxKeyguardNotifications(int i) {
        Log.d("NotificationPanelView", "getMaxKeyguardNotifications max: " + i);
        return computeMaxKeyguardNotifications();
    }

    public final int getMaxPanelHeight() {
        int i = this.mStatusBarMinHeight;
        int i2 = this.mBarState;
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        if (i2 != 1 && this.mNotificationStackScrollLayoutController.getNotGoneChildCount() == 0) {
            i = Math.max(i, quickSettingsControllerImpl.mMinExpansionHeight);
        }
        boolean isEnabled = SecPanelSplitHelper.isEnabled();
        KeyguardClockPositionAlgorithm.Result result = this.mClockPositionResult;
        int calculatePanelHeightExpanded = (isEnabled || quickSettingsControllerImpl.isExpandImmediate() || quickSettingsControllerImpl.getExpanded$1() || (this.mIsExpandingOrCollapsing && quickSettingsControllerImpl.mExpandedWhenExpandingStarted) || this.mPulsing) ? quickSettingsControllerImpl.calculatePanelHeightExpanded(result.stackScrollerPadding) : calculatePanelHeightShade();
        if (this.mSecNotificationPanelViewController != null) {
            int calculatePanelHeightExpanded2 = quickSettingsControllerImpl.calculatePanelHeightExpanded(result.stackScrollerPadding);
            int i3 = this.mBarState;
            SecPanelSplitHelper.Companion.getClass();
            if (SecPanelSplitHelper.isEnabled && i3 == 0) {
                calculatePanelHeightExpanded = calculatePanelHeightExpanded2 / 2;
            }
        }
        int max = Math.max(i, calculatePanelHeightExpanded);
        if (max == 0) {
            Log.wtf("NotificationPanelView", "maxPanelHeight is invalid. mOverExpansion: " + this.mOverExpansion + ", calculatePanelHeightQsExpanded: " + quickSettingsControllerImpl.calculatePanelHeightExpanded(result.stackScrollerPadding) + ", calculatePanelHeightShade: " + calculatePanelHeightShade() + ", mStatusBarMinHeight = " + this.mStatusBarMinHeight + ", mQsMinExpansionHeight = " + quickSettingsControllerImpl.mMinExpansionHeight);
        }
        return max;
    }

    public int getMaxPanelTransitionDistance() {
        return getMaxPanelHeight();
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final NotificationStackScrollLayoutController getNotificationStackScrollLayoutController() {
        return this.mNotificationStackScrollLayoutController;
    }

    public final int getNotificationTopMargin(boolean z) {
        if (DeviceState.isTablet()) {
            if (z) {
                return this.mResources.getDimensionPixelSize(R.dimen.keyguard_indication_dls_default_notification_top_margin_land_tablet);
            }
            return this.mResources.getDimensionPixelSize(R.dimen.keyguard_indication_dls_default_notification_top_margin_tablet) + getCutoutHeight$1();
        }
        if (z) {
            return this.mResources.getDimensionPixelSize(R.dimen.keyguard_indication_dls_default_notification_top_margin_land);
        }
        return this.mResources.getDimensionPixelSize(R.dimen.keyguard_indication_dls_default_notification_top_margin) + getCutoutHeight$1();
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final ShadeFoldAnimator getShadeFoldAnimator() {
        return this.mShadeFoldAnimator;
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final ShadeHeadsUpTracker getShadeHeadsUpTracker$1() {
        return this.mShadeHeadsUpTracker;
    }

    public StatusBarStateController getStatusBarStateController() {
        return this.mStatusBarStateController;
    }

    public StatusBarStateController.StateListener getStatusBarStateListener() {
        return this.mStatusBarStateListener;
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final KeyguardTouchAnimator getTouchAnimator() {
        return this.mKeyguardTouchAnimator;
    }

    public TouchHandler getTouchHandler() {
        return this.mTouchHandler;
    }

    public final float getTouchSlop$1(MotionEvent motionEvent) {
        return motionEvent.getClassification() == 1 ? this.mTouchSlop * this.mSlopMultiplier : this.mTouchSlop;
    }

    public float getVerticalSpaceForLockscreenNotifications() {
        DeviceEntryUdfpsRefactor deviceEntryUdfpsRefactor = DeviceEntryUdfpsRefactor.INSTANCE;
        Flags.deviceEntryUdfpsRefactor();
        View findViewById = this.mKeyguardViewConfigurator.keyguardRootView.findViewById(R.id.device_entry_icon_view);
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        float bottom = findViewById != null ? notificationStackScrollLayoutController.mView.getBottom() - findViewById.getTop() : 0.0f;
        NotificationPanelView notificationPanelView = this.mView;
        int dimensionPixelSize = notificationPanelView.getResources().getDimensionPixelSize(R.dimen.keyguard_margin_between_noti_indication);
        if (this.mKeyguardBottomArea == null) {
            dimensionPixelSize = 10;
        }
        this.mIndicationBottomPadding = dimensionPixelSize;
        int i = 0;
        float max = Math.max(bottom, Math.max(dimensionPixelSize, 0));
        this.mKeyguardNotificationBottomPadding = max;
        float lockscreenNotifPadding = this.mClockPositionAlgorithm.getLockscreenNotifPadding(notificationStackScrollLayoutController.mView.getTop());
        this.mKeyguardNotificationTopPadding = lockscreenNotifPadding;
        if (CscRune.KEYGUARD_DCM_LIVE_UX) {
            DcmMascotViewContainer dcmMascotViewContainer = this.mMascotViewContainer;
            if (dcmMascotViewContainer.getVisibility() == 0) {
                i = dcmMascotViewContainer.mascotHeight + dcmMascotViewContainer.mascotTopMarin + dcmMascotViewContainer.mascotBottomMarin;
            }
        }
        KeyguardClockPositionAlgorithm keyguardClockPositionAlgorithm = this.mClockPositionAlgorithm;
        int bottomMarginY = keyguardClockPositionAlgorithm instanceof FaceWidgetPositionAlgorithmWrapper ? keyguardClockPositionAlgorithm.getBottomMarginY() : (int) max;
        int height = notificationPanelView.getHeight();
        notificationStackScrollLayoutController.mView.getHeight();
        this.mBottomMarginY = bottomMarginY;
        float f = (height - lockscreenNotifPadding) - bottomMarginY;
        if (CscRune.KEYGUARD_DCM_LIVE_UX) {
            f -= i;
        }
        this.mAvailableNotifSpace = f;
        return f;
    }

    public float getVerticalSpaceForLockscreenShelf() {
        return 0.0f;
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final void goToLockedShade() {
        Log.d("NotificationPanelView", "goToLockedShade mCentralSurfaces: " + this.mCentralSurfaces);
        LockscreenShadeTransitionController lockscreenShadeTransitionController = this.mLockscreenShadeTransitionController;
        if (lockscreenShadeTransitionController != null) {
            lockscreenShadeTransitionController.goToLockedShade(null, false);
        }
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final boolean handleExternalInterceptTouch(MotionEvent motionEvent) {
        try {
            this.mUseExternalTouch = true;
            return this.mTouchHandler.onInterceptTouchEvent(motionEvent);
        } finally {
            this.mUseExternalTouch = false;
        }
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final boolean handleExternalTouch(MotionEvent motionEvent) {
        try {
            this.mUseExternalTouch = true;
            return this.mTouchHandler.onTouchEvent(motionEvent);
        } finally {
            this.mUseExternalTouch = false;
        }
    }

    public final void initBottomArea() {
        KeyguardBottomAreaRefactor keyguardBottomAreaRefactor = KeyguardBottomAreaRefactor.INSTANCE;
        Flags.keyguardBottomAreaRefactor();
        KeyguardBottomAreaView keyguardBottomAreaView = this.mKeyguardBottomArea;
        NotificationPanelViewController$$ExternalSyntheticLambda7 notificationPanelViewController$$ExternalSyntheticLambda7 = new NotificationPanelViewController$$ExternalSyntheticLambda7(this);
        keyguardBottomAreaView.init(this.mKeyguardBottomAreaViewModel, this.mFalsingManager, this.mLockIconViewController, notificationPanelViewController$$ExternalSyntheticLambda7, this.mVibratorHelper, this.mActivityStarter, this.mPluginLockStarManagerLazy);
        if (this.mSecAffordanceHelper == null) {
            this.mSecAffordanceHelper = new KeyguardSecAffordanceHelper(this.mKeyguardAffordanceHelperCallback, this.mView.getContext(), (KeyguardSecBottomAreaView) this.mKeyguardBottomArea);
            this.mKeyguardBottomAreaViewController.setUserSetupComplete$1(this.mUserSetupComplete);
        }
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void initDependencies(CentralSurfaces centralSurfaces, CentralSurfacesImpl$$ExternalSyntheticLambda29 centralSurfacesImpl$$ExternalSyntheticLambda29, HeadsUpManager headsUpManager) {
        this.mHeadsUpManager = headsUpManager;
        NotificationsHeadsUpRefactor notificationsHeadsUpRefactor = NotificationsHeadsUpRefactor.INSTANCE;
        Flags.notificationsHeadsUpRefactor();
        ((BaseHeadsUpManager) this.mHeadsUpManager).addListener(this.mOnHeadsUpChangedListener);
        this.mHeadsUpTouchHelper = new HeadsUpTouchHelper(headsUpManager, this.mStatusBarService, this.mNotificationStackScrollLayoutController.mView.mHeadsUpCallback, new HeadsUpNotificationViewControllerImpl(this, 0));
        this.mCentralSurfaces = centralSurfaces;
        this.mGestureRecorder = null;
        this.mHideExpandedRunnable = centralSurfacesImpl$$ExternalSyntheticLambda29;
        updateMaxDisplayedNotifications(true);
    }

    public final void instantCollapse() {
        QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
        if (quickPanelLogger != null) {
            quickPanelLogger.logPanelState("instantCollapse");
        }
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
        return ((Boolean) ((ShadeRepositoryImpl) this.mShadeRepository).legacyIsClosing.$$delegate_0.getValue()).booleanValue();
    }

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean isCollapsing() {
        return isClosing() || isLaunchingActivity$1();
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final boolean isExpanded() {
        return this.mExpandedFraction > 0.0f || this.mInstantExpanding || isPanelVisibleBecauseOfHeadsUp() || isTracking() || this.mHeightAnimator != null || (this.mUnlockedScreenOffAnimationController.isAnimationPlaying() && !this.mIsSpringBackAnimation);
    }

    public final boolean isExpandingOrCollapsing() {
        float computeExpansionFraction = this.mQsController.computeExpansionFraction();
        return this.mIsExpandingOrCollapsing || (0.0f < computeExpansionFraction && computeExpansionFraction < 1.0f);
    }

    public boolean isFlinging() {
        return this.mIsFlinging;
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor, com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean isFullyCollapsed() {
        return this.mExpandedFraction <= 0.0f;
    }

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean isFullyExpanded() {
        return this.mExpandedHeight >= ((float) getMaxPanelTransitionDistance());
    }

    public final boolean isInContentBounds$1(float f, float f2) {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        float f3 = notificationStackScrollLayout.mSidePaddings;
        float x = notificationStackScrollLayout.getX() + f3;
        return !notificationStackScrollLayoutController.mView.isBelowLastNotification(f - x, f2) && x < f && f < (((float) notificationStackScrollLayoutController.mView.getWidth()) + x) - (f3 * 2.0f);
    }

    @Override // com.android.systemui.shade.ShadeViewController
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

    @Override // com.android.systemui.shade.ShadeViewController
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

    public final boolean isKeyguardShowing$2() {
        return this.mBarState == 1;
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final boolean isLaunchTransitionFinished() {
        return this.mIsLaunchTransitionFinished;
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final boolean isLaunchTransitionRunning() {
        return this.mIsLaunchTransitionRunning;
    }

    public final boolean isLaunchingActivity$1() {
        return ((Boolean) this.mShadeAnimationInteractor.isLaunchingActivity.$$delegate_0.getValue()).booleanValue();
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final boolean isNoUnlockNeed(String str) {
        KeyguardBottomAreaViewController keyguardBottomAreaViewController = this.mKeyguardBottomAreaViewController;
        if (keyguardBottomAreaViewController != null) {
            return keyguardBottomAreaViewController.isNoUnlockNeed(str);
        }
        return false;
    }

    public final boolean isOnKeyguard$1() {
        return this.mBarState == 1;
    }

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean isPanelExpanded() {
        return ((Boolean) ((ShadeRepositoryImpl) this.mShadeRepository).legacyExpandedOrAwaitingInputTransfer.$$delegate_0.getValue()).booleanValue();
    }

    public final boolean isPanelVisibleBecauseOfHeadsUp() {
        NotificationsHeadsUpRefactor notificationsHeadsUpRefactor = NotificationsHeadsUpRefactor.INSTANCE;
        Flags.notificationsHeadsUpRefactor();
        HeadsUpManager headsUpManager = this.mHeadsUpManager;
        return ((headsUpManager != null && ((BaseHeadsUpManager) headsUpManager).mHasPinnedNotification) || this.mHeadsUpAnimatingAway) && this.mBarState == 0;
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
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

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0034, code lost:
    
        if (r11.mUpdateMonitor.getUserHasTrust(r11.mSelectedUserInteractor.getSelectedUserId(false)) != false) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isTouchOnEmptyArea(android.view.MotionEvent r12) {
        /*
            Method dump skipped, instructions count: 340
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.NotificationPanelViewController.isTouchOnEmptyArea(android.view.MotionEvent):boolean");
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final boolean isTouchableArea(MotionEvent motionEvent) {
        KeyguardBottomAreaView keyguardBottomAreaView;
        return CscRune.LOCKUI_BOTTOM_USIM_TEXT && (keyguardBottomAreaView = this.mKeyguardBottomArea) != null && keyguardBottomAreaView.isInEmergencyButtonArea(motionEvent);
    }

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean isTracking() {
        return ((Boolean) ((ShadeRepositoryImpl) this.mShadeRepository).legacyShadeTracking.$$delegate_0.getValue()).booleanValue();
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final boolean isViewEnabled() {
        return this.mView.isEnabled();
    }

    public void loadDimens() {
        NotificationPanelView notificationPanelView = this.mView;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(notificationPanelView.getContext());
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mSlopMultiplier = viewConfiguration.getScaledAmbiguousGestureMultiplier();
        this.mHintDistance = this.mResources.getDimension(R.dimen.hint_move_distance);
        this.mPanelFlingOvershootAmount = this.mResources.getDimension(R.dimen.panel_overshoot_amount);
        FlingAnimationUtils.Builder builder = (FlingAnimationUtils.Builder) this.mFlingAnimationUtilsBuilder.get();
        builder.mMaxLengthSeconds = 0.4f;
        this.mFlingAnimationUtils = builder.build();
        this.mStatusBarMinHeight = SystemBarUtils.getStatusBarHeight(notificationPanelView.getContext());
        this.mStatusBarHeaderHeightKeyguard = Utils.getStatusBarHeaderHeightKeyguard(notificationPanelView.getContext());
        this.mClockPositionAlgorithm.loadDimens(notificationPanelView.getContext(), this.mResources);
        this.mIndicationBottomPadding = this.mResources.getDimensionPixelSize(R.dimen.keyguard_indication_bottom_padding);
        this.mHeadsUpInset = this.mResources.getDimensionPixelSize(R.dimen.heads_up_status_bar_padding) + SystemBarUtils.getStatusBarHeight(notificationPanelView.getContext());
        this.mMaxOverscrollAmountForPulse = this.mResources.getDimensionPixelSize(R.dimen.pulse_expansion_max_top_overshoot);
        this.mUdfpsMaxYBurnInOffset = this.mResources.getDimensionPixelSize(R.dimen.udfps_burn_in_offset_y);
        this.mSplitShadeScrimTransitionDistance = this.mResources.getDimensionPixelSize(R.dimen.split_shade_scrim_transition_distance);
        this.mDreamingToLockscreenTransitionTranslationY = this.mResources.getDimensionPixelSize(R.dimen.dreaming_to_lockscreen_transition_lockscreen_translation_y);
        this.mLockscreenToDreamingTransitionTranslationY = this.mResources.getDimensionPixelSize(R.dimen.lockscreen_to_dreaming_transition_lockscreen_translation_y);
        this.mGoneToDreamingTransitionTranslationY = this.mResources.getDimensionPixelSize(R.dimen.gone_to_dreaming_transition_lockscreen_translation_y);
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        NotificationPanelView notificationPanelView2 = quickSettingsControllerImpl.mPanelView;
        ViewConfiguration viewConfiguration2 = ViewConfiguration.get(notificationPanelView2.getContext());
        quickSettingsControllerImpl.mTouchSlop = viewConfiguration2.getScaledTouchSlop();
        quickSettingsControllerImpl.mSlopMultiplier = viewConfiguration2.getScaledAmbiguousGestureMultiplier();
        quickSettingsControllerImpl.mStatusBarMinHeight = SystemBarUtils.getStatusBarHeight(notificationPanelView2.getContext());
        quickSettingsControllerImpl.mScrimCornerRadius = quickSettingsControllerImpl.mResources.getDimensionPixelSize(R.dimen.notification_scrim_corner_radius);
        quickSettingsControllerImpl.mScreenCornerRadius = (int) ScreenDecorationsUtils.getWindowCornerRadius(notificationPanelView2.getContext());
        quickSettingsControllerImpl.mFalsingThreshold = quickSettingsControllerImpl.mResources.getDimensionPixelSize(R.dimen.qs_falsing_threshold);
        quickSettingsControllerImpl.mLockscreenNotificationPadding = quickSettingsControllerImpl.mResources.getDimensionPixelSize(R.dimen.notification_side_paddings);
        quickSettingsControllerImpl.mDistanceForFullShadeTransition = quickSettingsControllerImpl.mResources.getDimensionPixelSize(R.dimen.lockscreen_shade_qs_transition_distance);
        if (DeviceType.isTablet()) {
            quickSettingsControllerImpl.mResources.getDimensionPixelSize(R.dimen.sec_notification_shelf_height_tablet);
        } else {
            quickSettingsControllerImpl.mResources.getDimensionPixelSize(R.dimen.sec_notification_shelf_height);
        }
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final void makeExpandedInvisible() {
        this.mView.post(this.mHideExpandedRunnable);
    }

    public void maybeAnimateBottomAreaAlpha() {
        this.mBottomAreaShadeAlphaAnimator.cancel();
        if (this.mBarState != 2) {
            this.mBottomAreaShadeAlpha = 1.0f;
        } else {
            this.mBottomAreaShadeAlphaAnimator.setFloatValues(this.mBottomAreaShadeAlpha, 0.0f);
            this.mBottomAreaShadeAlphaAnimator.start();
        }
    }

    public final void maybeVibrateOnOpening(boolean z) {
        int i;
        if (!this.mVibrateOnOpening || (i = this.mBarState) == 1 || i == 2) {
            return;
        }
        if (z && this.mHasVibratedOnOpen) {
            return;
        }
        this.mVibratorHelper.getClass();
        this.mView.performHapticFeedback(12);
        this.mHasVibratedOnOpen = true;
        this.mShadeLog.v("Vibrating on opening, mHasVibratedOnOpen=true");
    }

    public final void notifyExpandingFinished() {
        QSImpl qSImpl;
        SecQSImpl secQSImpl;
        SecQSImplAnimatorManager secQSImplAnimatorManager;
        endClosing();
        if (this.mExpanding) {
            this.mExpanding = false;
            int i = SceneContainerFlag.$r8$clinit;
            Flags.sceneContainer();
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
            notificationStackScrollLayoutController.getClass();
            SceneContainerFlag.assertInLegacyMode();
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            notificationStackScrollLayout.mCheckForLeavebehind = false;
            notificationStackScrollLayout.mIsExpansionChanging = false;
            notificationStackScrollLayout.mAmbientState.mExpansionChanging = false;
            if (!notificationStackScrollLayout.mIsExpanded) {
                notificationStackScrollLayout.resetScrollPosition();
                notificationStackScrollLayout.mResetUserExpandedStatesRunnable.run();
                notificationStackScrollLayout.clearTemporaryViewsInGroup(notificationStackScrollLayout, "clearTemporaryViews");
                for (int i2 = 0; i2 < notificationStackScrollLayout.getChildCount(); i2++) {
                    ExpandableView expandableView = (ExpandableView) notificationStackScrollLayout.getChildAt(i2);
                    if (expandableView instanceof ExpandableNotificationRow) {
                        notificationStackScrollLayout.clearTemporaryViewsInGroup(((ExpandableNotificationRow) expandableView).mChildrenContainer, "clearTemporaryViewsInGroup(row.getChildrenContainer())");
                    }
                }
                for (int i3 = 0; i3 < notificationStackScrollLayout.getChildCount(); i3++) {
                    ExpandableView expandableView2 = (ExpandableView) notificationStackScrollLayout.getChildAt(i3);
                    if (expandableView2 instanceof ExpandableNotificationRow) {
                        ((ExpandableNotificationRow) expandableView2).setUserLocked(false);
                    }
                }
                notificationStackScrollLayout.resetAllSwipeState();
            }
            if (notificationStackScrollLayout.mLastSentExpandedHeight > 0.0f) {
                notificationStackScrollLayout.clearHeadsUpDisappearRunning();
            }
            HeadsUpManagerPhone headsUpManagerPhone = (HeadsUpManagerPhone) this.mHeadsUpManager;
            if (headsUpManagerPhone.mReleaseOnExpandFinish) {
                headsUpManagerPhone.releaseAllImmediately();
                headsUpManagerPhone.mReleaseOnExpandFinish = false;
            } else {
                Iterator it = headsUpManagerPhone.mEntriesToRemoveAfterExpand.iterator();
                while (it.hasNext()) {
                    NotificationEntry notificationEntry = (NotificationEntry) it.next();
                    if (headsUpManagerPhone.isHeadsUpEntry(notificationEntry.mKey)) {
                        headsUpManagerPhone.removeEntry(notificationEntry.mKey, "onExpandingFinished");
                    }
                }
            }
            headsUpManagerPhone.mEntriesToRemoveAfterExpand.clear();
            this.mConversationNotificationManager.onNotificationPanelExpandStateChanged(isFullyCollapsed());
            this.mIsExpandingOrCollapsing = false;
            boolean z = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
            QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
            if (!z && isPanelExpanded() && this.mBarState != 1) {
                if (SecPanelSplitHelper.isEnabled()) {
                    SecPanelSplitHelper secPanelSplitHelper = this.mPanelSplitHelper;
                    if (secPanelSplitHelper != null && secPanelSplitHelper.isShadeState()) {
                        updateEntrySetRead();
                    }
                } else if (!quickSettingsControllerImpl.getExpanded$1()) {
                    updateEntrySetRead();
                }
            }
            MediaHierarchyManager mediaHierarchyManager = this.mMediaHierarchyManager;
            if (mediaHierarchyManager.collapsingShadeFromQS) {
                mediaHierarchyManager.collapsingShadeFromQS = false;
                MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, true, 2);
            }
            mediaHierarchyManager.setQsExpanded(quickSettingsControllerImpl.getExpanded$1());
            boolean isFullyCollapsed = isFullyCollapsed();
            NotificationPanelView notificationPanelView = this.mView;
            if (isFullyCollapsed) {
                DejankUtils.postAfterTraversal(new NotificationPanelViewController$$ExternalSyntheticLambda31(this, 5));
                notificationPanelView.postOnAnimation(new NotificationPanelViewController$$ExternalSyntheticLambda31(this, 6));
            } else {
                setListening$3(true);
            }
            if (this.mBarState != 0) {
                this.mShadeLog.d("onExpandingFinished called");
                quickSettingsControllerImpl.setExpandImmediate(false);
            }
            setShowShelfOnly(false);
            quickSettingsControllerImpl.mTwoFingerExpandPossible = false;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.mTrackedHeadsUpNotification = null;
            for (int i4 = 0; i4 < notificationPanelViewController.mTrackingHeadsUpListeners.size(); i4++) {
                ((Consumer) notificationPanelViewController.mTrackingHeadsUpListeners.get(i4)).accept(null);
            }
            this.mExpandingFromHeadsUp = false;
            setPanelScrimMinFraction(0.0f);
            setKeyguardStatusBarAlpha();
            setExpandSettingsPanel$1(false);
            SecNotificationPanelViewController secNotificationPanelViewController = this.mSecNotificationPanelViewController;
            if (secNotificationPanelViewController != null) {
                boolean isKeyguardShowing$2 = isKeyguardShowing$2();
                SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = secNotificationPanelViewController.secQuickSettingsControllerImpl;
                if (secQuickSettingsControllerImpl != null) {
                    boolean booleanValue = ((Boolean) ((ShadeRepositoryImpl) secNotificationPanelViewController.shadeRepository).legacyExpandedOrAwaitingInputTransfer.$$delegate_0.getValue()).booleanValue();
                    if (!booleanValue) {
                        Object obj = secQuickSettingsControllerImpl.qsSupplier.get();
                        QSFragmentLegacy qSFragmentLegacy = obj instanceof QSFragmentLegacy ? (QSFragmentLegacy) obj : null;
                        if (qSFragmentLegacy != null && (qSImpl = qSFragmentLegacy.mQsImpl) != null && (secQSImpl = qSImpl.mSecQSImpl) != null && (secQSImplAnimatorManager = secQSImpl.secQSImplAnimatorManager) != null) {
                            secQSImplAnimatorManager.onPanelClosed();
                        }
                        setMotionAborted();
                    }
                    SecPanelSplitHelper.Companion.getClass();
                    if (!SecPanelSplitHelper.isEnabled && !isKeyguardShowing$2) {
                        boolean expanded$1 = secNotificationPanelViewController.quickSettingsController.getExpanded$1();
                        if (booleanValue) {
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor = secNotificationPanelViewController.panelSAStatusLogInteractor;
                            if (expanded$1) {
                                if (secPanelSAStatusLogInteractor != null) {
                                    StateFlowImpl stateFlowImpl = secPanelSAStatusLogInteractor.repository._openQuickPanelFrom2Depth;
                                    stateFlowImpl.updateState(null, Long.valueOf(((Number) stateFlowImpl.getValue()).longValue() + 1));
                                    Unit unit = Unit.INSTANCE;
                                }
                            } else if (secPanelSAStatusLogInteractor != null) {
                                StateFlowImpl stateFlowImpl2 = secPanelSAStatusLogInteractor.repository._openQuickPanelFrom1Depth;
                                stateFlowImpl2.updateState(null, Long.valueOf(((Number) stateFlowImpl2.getValue()).longValue() + 1));
                            }
                        }
                        Unit unit2 = Unit.INSTANCE;
                    }
                }
                this.mHeadsUpVisibleOnDown = false;
                notificationPanelView.getRootView().getViewRootImpl().setDisableSuperHdr(new SurfaceControl.Transaction(), this.mBarState == 0 ? isPanelExpanded() : false);
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
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        boolean z = quickSettingsControllerImpl.mFullyExpanded;
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = quickSettingsControllerImpl.mNotificationStackScrollLayoutController;
        notificationStackScrollLayoutController.getClass();
        SceneContainerFlag.assertInLegacyMode();
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.mIsExpansionChanging = true;
        notificationStackScrollLayout.mAmbientState.mExpansionChanging = true;
        notificationStackScrollLayoutController.checkSnoozeLeavebehind();
        quickSettingsControllerImpl.mExpandedWhenExpandingStarted = z;
        boolean z2 = z && !quickSettingsControllerImpl.mAnimating;
        MediaHierarchyManager mediaHierarchyManager = quickSettingsControllerImpl.mMediaHierarchyManager;
        if (mediaHierarchyManager.collapsingShadeFromQS != z2) {
            mediaHierarchyManager.collapsingShadeFromQS = z2;
            MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, true, 2);
        }
        if (quickSettingsControllerImpl.getExpanded$1()) {
            quickSettingsControllerImpl.onExpansionStarted$1();
        }
        QS qs = quickSettingsControllerImpl.mQs;
        if (qs != null) {
            qs.setHeaderListening(true);
        }
        if (this.mBarState == 0) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.mNotificationStackScrollLayoutController;
            if (notificationStackScrollLayoutController2.mHasDelayedForceLayout) {
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController2.mView;
                if (notificationStackScrollLayout2.getHandler() != null) {
                    Handler handler = notificationStackScrollLayout2.getHandler();
                    NotificationStackScrollLayoutController.AnonymousClass1 anonymousClass1 = notificationStackScrollLayoutController2.mForceLayoutTimeOutRunnable;
                    if (handler.hasCallbacks(anonymousClass1)) {
                        notificationStackScrollLayout2.removeCallbacks(anonymousClass1);
                        anonymousClass1.run();
                    }
                }
            }
        }
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void onAffordanceLaunchEnded() {
        this.mLaunchingAffordance = false;
        KeyguardAffordanceHelperCallback keyguardAffordanceHelperCallback = this.mKeyguardAffordanceHelperCallback;
        NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
        ((KeyguardSecAffordanceView) (notificationPanelViewController.mView.getLayoutDirection() == 1 ? notificationPanelViewController.mKeyguardBottomArea.getRightView() : notificationPanelViewController.mKeyguardBottomArea.getLeftView())).getClass();
        NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
        ((KeyguardSecAffordanceView) (notificationPanelViewController2.mView.getLayoutDirection() == 1 ? notificationPanelViewController2.mKeyguardBottomArea.getLeftView() : notificationPanelViewController2.mKeyguardBottomArea.getRightView())).getClass();
        this.mKeyguardBypassController.launchingAffordance = false;
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeBackActionInteractor
    public final void onBackPressed() {
        closeQsIfPossible();
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final void onBarStateChanged(int i) {
        com.android.systemui.keyguard.Log.d("NotificationPanelView", "onBarStateChanged() to " + i);
        this.mPluginLockMediator.onBarStateChanged(i);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void onDismissCancelled() {
        KeyguardStatusViewController keyguardStatusViewController = this.mKeyguardStatusViewController;
        int i = this.mBarState;
        keyguardStatusViewController.setKeyguardStatusViewVisibility(i, i, false, false);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void onDragDownAmountChanged(float f) {
        this.mKeyguardStatusBarViewController.mDraggedFraction = f;
    }

    public final void onEmptySpaceClick(float f, float f2) {
        SecQuickSettingsControllerImpl secQuickSettingsControllerImpl;
        boolean isEnabled = SecPanelSplitHelper.isEnabled();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        NotificationPanelView notificationPanelView = this.mView;
        if (!isEnabled) {
            if (this.mBarState == 0 && notificationStackScrollLayoutController.mView.isBelowLastNotification(f, f2)) {
                notificationPanelView.post(this.mPostCollapseRunnable);
                return;
            } else {
                onMiddleClicked();
                return;
            }
        }
        SecPanelSplitHelper secPanelSplitHelper = this.mPanelSplitHelper;
        if (secPanelSplitHelper.isShadeState() && notificationStackScrollLayoutController.mView.isBelowLastNotification(f, f2)) {
            notificationPanelView.post(this.mPostCollapseRunnable);
            return;
        }
        if (secPanelSplitHelper.isQSState() && (secQuickSettingsControllerImpl = this.mQsController.mSecQuickSettingsControllerImpl) != null) {
            SecQSPanelController qsPanelController = secQuickSettingsControllerImpl.getQsPanelController();
            SecQSPanel view = qsPanelController != null ? qsPanelController.getView() : null;
            int[] iArr = new int[2];
            for (int i = 0; i < 2; i++) {
                iArr[i] = 0;
            }
            if (view != null) {
                view.getLocationInWindow(iArr);
            }
            if (f2 > iArr[1] + (view != null ? view.getBottom() : 0)) {
                notificationPanelView.post(this.mPostCollapseRunnable);
                return;
            }
        }
        onMiddleClicked();
    }

    public void onFinishInflate() {
        FrameLayout frameLayout;
        KeyguardUserSwitcherView keyguardUserSwitcherView;
        FaceWidgetContainerWrapper faceWidgetContainerWrapper;
        int i = 5;
        int i2 = 0;
        loadDimens();
        NotificationPanelView notificationPanelView = this.mView;
        this.mKeyguardStatusBar = (KeyguardStatusBarView) notificationPanelView.findViewById(R.id.keyguard_header);
        if (!this.mKeyguardUserSwitcherEnabled || !this.mUserManager.isUserSwitcherEnabled(this.mResources.getBoolean(R.bool.qs_show_user_switcher_for_single_user))) {
            frameLayout = null;
            keyguardUserSwitcherView = null;
        } else if (this.mKeyguardQsUserSwitchEnabled) {
            frameLayout = (FrameLayout) ((ViewStub) notificationPanelView.findViewById(R.id.keyguard_qs_user_switch_stub)).inflate();
            keyguardUserSwitcherView = null;
        } else {
            keyguardUserSwitcherView = (KeyguardUserSwitcherView) ((ViewStub) notificationPanelView.findViewById(R.id.keyguard_user_switcher_stub)).inflate();
            frameLayout = null;
        }
        KeyguardStatusBarViewController keyguardStatusBarViewController = this.mKeyguardStatusBarViewComponentFactory.build((KeyguardStatusBarView) notificationPanelView.findViewById(R.id.keyguard_header), this.mShadeViewStateProvider).getKeyguardStatusBarViewController();
        this.mKeyguardStatusBarViewController = keyguardStatusBarViewController;
        keyguardStatusBarViewController.init();
        KeyguardBottomAreaRefactor keyguardBottomAreaRefactor = KeyguardBottomAreaRefactor.INSTANCE;
        Flags.keyguardBottomAreaRefactor();
        final KeyguardBottomAreaViewController keyguardBottomAreaViewController = this.mKeyguardBottomAreaViewController;
        KeyguardBottomAreaView view = keyguardBottomAreaViewController.getView();
        this.mKeyguardBottomArea = view;
        final boolean z = true ? 1 : 0;
        Consumer consumer = new Consumer() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda42
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i3 = z;
                Dumpable dumpable = keyguardBottomAreaViewController;
                switch (i3) {
                    case 0:
                        ((NotificationStackScrollLayoutController) dumpable).mView.mAmbientState.mTrackedHeadsUpRow = (ExpandableNotificationRow) obj;
                        break;
                    default:
                        ((Boolean) obj).getClass();
                        ((KeyguardBottomAreaViewController) dumpable).updateIndicationPosition();
                        break;
                }
            }
        };
        KeyguardIndicationController keyguardIndicationController = this.mKeyguardIndicationController;
        keyguardIndicationController.mUpdatePosition = consumer;
        keyguardIndicationController.setIndicationArea((ViewGroup) view.findViewById(R.id.keyguard_indication_area));
        keyguardIndicationController.setUpperTextView((KeyguardIndicationTextView) this.mKeyguardBottomArea.findViewById(R.id.keyguard_upper_fingerprint_indication));
        if (CscRune.LOCKUI_BOTTOM_USIM_TEXT) {
            this.mKeyguardBottomArea.initEmergencyButton(this.mEmergencyButtonControllerFactory);
        }
        this.mKeyguardBottomArea.pluginLockData = this.mPluginLockData;
        final NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        LockscreenNotificationManager lockscreenNotificationManager = this.mLockscreenNotificationManager;
        lockscreenNotificationManager.getClass();
        initBottomArea();
        if (LsRune.SECURITY_PUNCH_HOLE_FACE_VI) {
            ViewStub viewStub = (ViewStub) notificationPanelView.findViewById(R.id.keyguard_punch_hole_vi_view_stub);
            viewStub.setLayoutResource(R.layout.keyguard_punch_hole_vi_view);
            KeyguardPunchHoleVIView keyguardPunchHoleVIView = (KeyguardPunchHoleVIView) viewStub.inflate();
            this.mKeyguardPunchHoleVIView = keyguardPunchHoleVIView;
            if (keyguardPunchHoleVIView != null) {
                this.mPunchHoleVIViewControllerFactory.create(keyguardPunchHoleVIView).init();
                this.mKeyguardPunchHoleVIView.bringToFront();
            }
        }
        this.mNotificationContainerParent = (NotificationsQuickSettingsContainer) notificationPanelView.findViewById(R.id.notification_container_parent);
        KeyguardStatusViewController keyguardStatusViewController = this.mKeyguardStatusViewController;
        if (keyguardStatusViewController != null) {
            keyguardStatusViewController.mDumpManager.unregisterDumpable("KeyguardStatusViewController#" + keyguardStatusViewController.hashCode());
        }
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        KeyguardStatusViewController keyguardStatusViewController2 = this.mKeyguardStatusViewComponentFactory.build((KeyguardStatusView) notificationPanelView.getRootView().findViewById(R.id.keyguard_status_view), notificationPanelView.getContext().getDisplay()).getKeyguardStatusViewController();
        this.mKeyguardStatusViewController = keyguardStatusViewController2;
        keyguardStatusViewController2.init();
        this.mLockscreenShadeTransitionController.addCallback(this.mKeyguardStatusViewController.mLockscreenShadeTransitonCallback);
        KeyguardStatusViewController keyguardStatusViewController3 = this.mKeyguardStatusViewController;
        LockscreenSmartspaceController lockscreenSmartspaceController = keyguardStatusViewController3.mKeyguardClockSwitchController.mSmartspaceController;
        lockscreenSmartspaceController.mSplitShadeEnabled = false;
        Iterator it = lockscreenSmartspaceController.smartspaceViews.iterator();
        while (it.hasNext()) {
            ((BcSmartspaceDataPlugin.SmartspaceView) it.next()).setSplitShadeEnabled(false);
        }
        keyguardStatusViewController3.mSplitShadeEnabled = false;
        updateClockAppearance();
        KeyguardUserSwitcherController keyguardUserSwitcherController = this.mKeyguardUserSwitcherController;
        if (keyguardUserSwitcherController != null) {
            keyguardUserSwitcherController.closeSwitcherIfOpenAndNotSimple(false);
        }
        this.mKeyguardQsUserSwitchController = null;
        this.mKeyguardUserSwitcherController = null;
        View findViewById = notificationPanelView.findViewById(R.id.keyguard_status_view);
        if (findViewById != null) {
            notificationPanelView.removeView(findViewById);
        }
        View findViewById2 = notificationPanelView.findViewById(R.id.keyguard_clock_container);
        if (findViewById2 != null) {
            notificationPanelView.removeView(findViewById2);
        }
        PluginFaceWidgetManager pluginFaceWidgetManager = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
        if (pluginFaceWidgetManager == null) {
            Log.e("NotificationPanelView", "Failed to get PluginFaceWidgetManager");
            faceWidgetContainerWrapper = null;
        } else {
            faceWidgetContainerWrapper = pluginFaceWidgetManager.mFaceWidgetContainerWrapper;
            if (faceWidgetContainerWrapper != null) {
                AnonymousClass10 anonymousClass10 = new AnonymousClass10();
                PluginFaceWidgetManager pluginFaceWidgetManager2 = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
                if (pluginFaceWidgetManager2 != null) {
                    FaceWidgetKeyguardStatusCallbackWrapper faceWidgetKeyguardStatusCallbackWrapper = pluginFaceWidgetManager2.mKeyguardStatusCallbackWrapper;
                    if (faceWidgetKeyguardStatusCallbackWrapper instanceof FaceWidgetKeyguardStatusCallbackWrapper) {
                        faceWidgetKeyguardStatusCallbackWrapper.mStatusCallback = anonymousClass10;
                    }
                }
            }
        }
        KeyguardStatusViewController keyguardStatusViewController4 = this.mKeyguardStatusViewController;
        keyguardStatusViewController4.mKeyguardStatusBase = faceWidgetContainerWrapper;
        keyguardStatusViewController4.mIsDLSViewEnabledSupplier = new NotificationPanelViewController$$ExternalSyntheticLambda0(this, i);
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        quickSettingsControllerImpl.getClass();
        KeyguardUserSwitcherComponent.Factory factory = this.mKeyguardUserSwitcherComponentFactory;
        KeyguardQsUserSwitchComponent.Factory factory2 = this.mKeyguardQsUserSwitchComponentFactory;
        if (frameLayout != null) {
            KeyguardQsUserSwitchController keyguardQsUserSwitchController = factory2.build(frameLayout).getKeyguardQsUserSwitchController();
            this.mKeyguardQsUserSwitchController = keyguardQsUserSwitchController;
            keyguardQsUserSwitchController.init();
            this.mKeyguardStatusBarViewController.setKeyguardUserSwitcherEnabled(true);
        } else if (keyguardUserSwitcherView != null) {
            KeyguardUserSwitcherController keyguardUserSwitcherController2 = factory.build(keyguardUserSwitcherView).getKeyguardUserSwitcherController();
            this.mKeyguardUserSwitcherController = keyguardUserSwitcherController2;
            keyguardUserSwitcherController2.init();
            this.mKeyguardStatusBarViewController.setKeyguardUserSwitcherEnabled(true);
        } else {
            this.mKeyguardStatusBarViewController.setKeyguardUserSwitcherEnabled(false);
        }
        this.mKeyguardQsUserSwitchController = null;
        this.mKeyguardUserSwitcherController = null;
        if (frameLayout != null) {
            KeyguardQsUserSwitchController keyguardQsUserSwitchController2 = factory2.build(frameLayout).getKeyguardQsUserSwitchController();
            this.mKeyguardQsUserSwitchController = keyguardQsUserSwitchController2;
            keyguardQsUserSwitchController2.init();
            this.mKeyguardStatusBarViewController.setKeyguardUserSwitcherEnabled(true);
        } else if (keyguardUserSwitcherView != null) {
            KeyguardUserSwitcherController keyguardUserSwitcherController3 = factory.build(keyguardUserSwitcherView).getKeyguardUserSwitcherController();
            this.mKeyguardUserSwitcherController = keyguardUserSwitcherController3;
            keyguardUserSwitcherController3.init();
            this.mKeyguardStatusBarViewController.setKeyguardUserSwitcherEnabled(true);
        } else {
            this.mKeyguardStatusBarViewController.setKeyguardUserSwitcherEnabled(false);
        }
        NsslHeightChangedListener nsslHeightChangedListener = new NsslHeightChangedListener(this, i2);
        NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout2.mOnHeightChangedListener = nsslHeightChangedListener;
        notificationStackScrollLayout2.mOnEmptySpaceClickListener = this.mOnEmptySpaceClickListener;
        QuickSettingsControllerImpl.NsslOverscrollTopChangedListener nsslOverscrollTopChangedListener = new QuickSettingsControllerImpl.NsslOverscrollTopChangedListener(quickSettingsControllerImpl, 0 == true ? 1 : 0);
        NotificationStackScrollLayout notificationStackScrollLayout3 = quickSettingsControllerImpl.mNotificationStackScrollLayoutController.mView;
        notificationStackScrollLayout3.mOverscrollTopChangedListener = nsslOverscrollTopChangedListener;
        notificationStackScrollLayout3.mOnStackYChanged = new QuickSettingsControllerImpl$$ExternalSyntheticLambda19(quickSettingsControllerImpl, 4);
        notificationStackScrollLayout3.mScrollListener = new QuickSettingsControllerImpl$$ExternalSyntheticLambda19(quickSettingsControllerImpl, 5);
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 = ((ShadeInteractorImpl) quickSettingsControllerImpl.mShadeInteractor).isExpandToQsEnabled;
        QuickSettingsControllerImpl$$ExternalSyntheticLambda19 quickSettingsControllerImpl$$ExternalSyntheticLambda19 = new QuickSettingsControllerImpl$$ExternalSyntheticLambda19(quickSettingsControllerImpl, 2);
        JavaAdapter javaAdapter = quickSettingsControllerImpl.mJavaAdapter;
        javaAdapter.alwaysCollectFlow(flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3, quickSettingsControllerImpl$$ExternalSyntheticLambda19);
        javaAdapter.alwaysCollectFlow(((CommunalTransitionViewModel) quickSettingsControllerImpl.mCommunalTransitionViewModelLazy.get()).isUmoOnCommunal, new QuickSettingsControllerImpl$$ExternalSyntheticLambda19(quickSettingsControllerImpl, 3));
        final byte b = 0 == true ? 1 : 0;
        this.mShadeHeadsUpTracker.addTrackingHeadsUpListener(new Consumer() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda42
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i3 = b;
                Dumpable dumpable = notificationStackScrollLayoutController;
                switch (i3) {
                    case 0:
                        ((NotificationStackScrollLayoutController) dumpable).mView.mAmbientState.mTrackedHeadsUpRow = (ExpandableNotificationRow) obj;
                        break;
                    default:
                        ((Boolean) obj).getClass();
                        ((KeyguardBottomAreaViewController) dumpable).updateIndicationPosition();
                        break;
                }
            }
        });
        if (LsRune.SECURITY_FINGERPRINT_GUIDE_POPUP) {
            ViewStub viewStub2 = (ViewStub) notificationPanelView.findViewById(R.id.keyguard_fingerprint_guide_popup_stub);
            viewStub2.setLayoutResource(R.layout.keyguard_fingerprint_guide_popup);
            KeyguardFingerprintGuidePopup keyguardFingerprintGuidePopup = (KeyguardFingerprintGuidePopup) viewStub2.inflate();
            if (keyguardFingerprintGuidePopup != null) {
                keyguardFingerprintGuidePopup.bringToFront();
            }
        }
        final NotificationWakeUpCoordinator notificationWakeUpCoordinator = this.mWakeUpCoordinator;
        notificationWakeUpCoordinator.mStackScrollerController = notificationStackScrollLayoutController;
        notificationWakeUpCoordinator.pulseExpanding = notificationStackScrollLayoutController.mView.mAmbientState.isPulseExpanding();
        notificationStackScrollLayoutController.mView.mAmbientState.mOnPulseHeightChangedListener = new Runnable() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator$setStackScroller$1
            @Override // java.lang.Runnable
            public final void run() {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = NotificationWakeUpCoordinator.this.mStackScrollerController;
                if (notificationStackScrollLayoutController2 == null) {
                    notificationStackScrollLayoutController2 = null;
                }
                boolean isPulseExpanding = notificationStackScrollLayoutController2.mView.mAmbientState.isPulseExpanding();
                NotificationWakeUpCoordinator notificationWakeUpCoordinator2 = NotificationWakeUpCoordinator.this;
                boolean z2 = isPulseExpanding != notificationWakeUpCoordinator2.pulseExpanding;
                notificationWakeUpCoordinator2.pulseExpanding = isPulseExpanding;
                Iterator it2 = notificationWakeUpCoordinator2.wakeUpListeners.iterator();
                while (it2.hasNext()) {
                    ((NotificationWakeUpCoordinator.WakeUpListener) it2.next()).onPulseExpansionAmountChanged();
                }
                if (z2) {
                    Iterator it3 = NotificationWakeUpCoordinator.this.wakeUpListeners.iterator();
                    while (it3.hasNext()) {
                        NotificationWakeUpCoordinator.WakeUpListener wakeUpListener = (NotificationWakeUpCoordinator.WakeUpListener) it3.next();
                        boolean z3 = NotificationWakeUpCoordinator.this.pulseExpanding;
                        wakeUpListener.getClass();
                    }
                    NotificationWakeUpCoordinator notificationWakeUpCoordinator3 = NotificationWakeUpCoordinator.this;
                    notificationWakeUpCoordinator3.notifsKeyguardInteractor.repository.isPulseExpanding.updateState(null, Boolean.valueOf(notificationWakeUpCoordinator3.pulseExpanding));
                }
            }
        };
        this.mPulseExpansionHandler.stackScrollerController = notificationStackScrollLayoutController;
        notificationWakeUpCoordinator.wakeUpListeners.add(new NotificationWakeUpCoordinator.WakeUpListener() { // from class: com.android.systemui.shade.NotificationPanelViewController.8
            @Override // com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator.WakeUpListener
            public final void onDelayedDozeAmountAnimationRunning() {
                NotificationPanelViewController.this.setWillPlayDelayedDozeAmountAnimation(false);
            }

            @Override // com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator.WakeUpListener
            public final void onFullyHiddenChanged(boolean z2) {
                KeyguardStatusBarViewController keyguardStatusBarViewController2 = NotificationPanelViewController.this.mKeyguardStatusBarViewController;
                keyguardStatusBarViewController2.getClass();
                SceneContainerFlag.assertInLegacyMode();
                keyguardStatusBarViewController2.updateForHeadsUp(true);
            }

            @Override // com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator.WakeUpListener
            public final void onPulseExpansionAmountChanged() {
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                if (notificationPanelViewController.mKeyguardBypassController.getBypassEnabled()) {
                    notificationPanelViewController.requestScrollerTopPaddingUpdate(false);
                }
            }
        });
        notificationPanelView.mRtlChangeListener = new NotificationPanelViewController$$ExternalSyntheticLambda7(this);
        notificationPanelView.setAccessibilityDelegate(this.mAccessibilityDelegate);
        if (CscRune.KEYGUARD_DCM_LIVE_UX) {
            AnonymousClass9 anonymousClass9 = new AnonymousClass9();
            DcmMascotViewContainer dcmMascotViewContainer = this.mMascotViewContainer;
            dcmMascotViewContainer.injector = anonymousClass9;
            dcmMascotViewContainer.updateRes();
            dcmMascotViewContainer.setLayoutParams(new ViewGroup.LayoutParams(-1, dcmMascotViewContainer.mascotHeight));
            dcmMascotViewContainer.setGravity(dcmMascotViewContainer.getContext().getResources().getInteger(R.integer.notification_panel_layout_gravity));
            AnonymousClass9 anonymousClass92 = dcmMascotViewContainer.injector;
            NotificationPanelViewController.this.mNotificationContainerParent.addView(dcmMascotViewContainer, 0);
            BroadcastDispatcher.registerReceiver$default(dcmMascotViewContainer.broadcastDispatcher, dcmMascotViewContainer.broadcastReceiver, new IntentFilter("jp.co.nttdocomo.carriermail.APP_LINK_RECEIVED_MESSAGE"), null, null, 0, null, 60);
            BroadcastDispatcher broadcastDispatcher = dcmMascotViewContainer.broadcastDispatcher;
            DcmMascotViewContainer$broadcastReceiver$1 dcmMascotViewContainer$broadcastReceiver$1 = dcmMascotViewContainer.broadcastReceiver;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.nttdocomo.android.mascot.KEYGUARD_UPDATE");
            intentFilter.addAction("com.android.internal.policy.impl.CARRIERMAIL_COUNT_UPDATE");
            intentFilter.addAction(PopupUIUtil.ACTION_BOOT_COMPLETED);
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
        this.mTapAgainViewController.init();
        this.mShadeHeaderController.init();
        Edge.Companion companion = Edge.Companion;
        KeyguardState keyguardState = KeyguardState.DREAMING;
        KeyguardState keyguardState2 = KeyguardState.LOCKSCREEN;
        Edge.StateToState m = KeyguardTransitionInteractor$2$1$$ExternalSyntheticOutline0.m(companion, keyguardState, keyguardState2);
        KeyguardTransitionInteractor keyguardTransitionInteractor = this.mKeyguardTransitionInteractor;
        Flow transition = keyguardTransitionInteractor.transition((Edge) m);
        NotificationPanelViewController$$ExternalSyntheticLambda8 notificationPanelViewController$$ExternalSyntheticLambda8 = this.mDreamingToLockscreenTransition;
        CoroutineDispatcher coroutineDispatcher = this.mMainDispatcher;
        JavaAdapterKt.collectFlow(notificationPanelView, transition, notificationPanelViewController$$ExternalSyntheticLambda8, coroutineDispatcher);
        DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel = this.mDreamingToLockscreenTransitionViewModel;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 = dreamingToLockscreenTransitionViewModel.lockscreenAlpha;
        final boolean z2 = true ? 1 : 0;
        JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1, new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda50
            public final /* synthetic */ NotificationPanelViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (z2) {
                    case 0:
                        NotificationPanelViewController notificationPanelViewController = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationStackScrollLayoutController;
                        Float f = (Float) obj;
                        notificationPanelViewController.getClass();
                        MigrateClocksToBlueprint migrateClocksToBlueprint2 = MigrateClocksToBlueprint.INSTANCE;
                        Flags.migrateClocksToBlueprint();
                        notificationPanelViewController.mKeyguardStatusViewController.setTranslationY$1(f.floatValue());
                        if (!notificationStackScrollLayoutController2.mView.mStateAnimator.isRunning()) {
                            notificationStackScrollLayoutController2.mView.setTranslationY(f.floatValue());
                            break;
                        }
                        break;
                    default:
                        NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController;
                        Float f2 = (Float) obj;
                        KeyguardStatusBarViewController keyguardStatusBarViewController2 = notificationPanelViewController2.mKeyguardStatusBarViewController;
                        float floatValue = f2.floatValue();
                        keyguardStatusBarViewController2.getClass();
                        KeyguardStatusBarViewController.isMigrationEnabled();
                        keyguardStatusBarViewController2.mExplicitAlpha = floatValue;
                        keyguardStatusBarViewController2.updateViewState();
                        new NotificationPanelViewController$$ExternalSyntheticLambda54(notificationPanelViewController2, false, notificationStackScrollLayoutController3).accept(f2);
                        break;
                }
            }
        }, coroutineDispatcher);
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenTranslationY = dreamingToLockscreenTransitionViewModel.lockscreenTranslationY(this.mDreamingToLockscreenTransitionTranslationY);
        final byte b2 = 0 == true ? 1 : 0;
        JavaAdapterKt.collectFlow(notificationPanelView, lockscreenTranslationY, new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda50
            public final /* synthetic */ NotificationPanelViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (b2) {
                    case 0:
                        NotificationPanelViewController notificationPanelViewController = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationStackScrollLayoutController;
                        Float f = (Float) obj;
                        notificationPanelViewController.getClass();
                        MigrateClocksToBlueprint migrateClocksToBlueprint2 = MigrateClocksToBlueprint.INSTANCE;
                        Flags.migrateClocksToBlueprint();
                        notificationPanelViewController.mKeyguardStatusViewController.setTranslationY$1(f.floatValue());
                        if (!notificationStackScrollLayoutController2.mView.mStateAnimator.isRunning()) {
                            notificationStackScrollLayoutController2.mView.setTranslationY(f.floatValue());
                            break;
                        }
                        break;
                    default:
                        NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController;
                        Float f2 = (Float) obj;
                        KeyguardStatusBarViewController keyguardStatusBarViewController2 = notificationPanelViewController2.mKeyguardStatusBarViewController;
                        float floatValue = f2.floatValue();
                        keyguardStatusBarViewController2.getClass();
                        KeyguardStatusBarViewController.isMigrationEnabled();
                        keyguardStatusBarViewController2.mExplicitAlpha = floatValue;
                        keyguardStatusBarViewController2.updateViewState();
                        new NotificationPanelViewController$$ExternalSyntheticLambda54(notificationPanelViewController2, false, notificationStackScrollLayoutController3).accept(f2);
                        break;
                }
            }
        }, coroutineDispatcher);
        SceneKey sceneKey = Scenes.Gone;
        KeyguardState keyguardState3 = KeyguardState.DREAMING_LOCKSCREEN_HOSTED;
        new Edge.SceneToState(sceneKey, keyguardState3);
        KeyguardState keyguardState4 = KeyguardState.GONE;
        JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionInteractor.transition(new Edge.StateToState(keyguardState4, keyguardState3)), this.mGoneToDreamingLockscreenHostedTransition, coroutineDispatcher);
        JavaAdapterKt.collectFlow(notificationPanelView, this.mGoneToDreamingLockscreenHostedTransitionViewModel.lockscreenAlpha, new NotificationPanelViewController$$ExternalSyntheticLambda54(this, 0 == true ? 1 : 0, notificationStackScrollLayoutController), coroutineDispatcher);
        JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionInteractor.transition((Edge) new Edge.StateToState(keyguardState2, keyguardState3)), this.mLockscreenToDreamingLockscreenHostedTransition, coroutineDispatcher);
        JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionInteractor.transition((Edge) new Edge.StateToState(keyguardState3, keyguardState2)), this.mDreamingLockscreenHostedToLockscreenTransition, coroutineDispatcher);
        KeyguardState keyguardState5 = KeyguardState.OCCLUDED;
        JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionInteractor.transition((Edge) new Edge.StateToState(keyguardState5, keyguardState2)), this.mOccludedToLockscreenTransition, coroutineDispatcher);
        Flags.migrateClocksToBlueprint();
        OccludedToLockscreenTransitionViewModel occludedToLockscreenTransitionViewModel = this.mOccludedToLockscreenTransitionViewModel;
        JavaAdapterKt.collectFlow(notificationPanelView, occludedToLockscreenTransitionViewModel.lockscreenAlpha, new NotificationPanelViewController$$ExternalSyntheticLambda54(this, 0 == true ? 1 : 0, notificationStackScrollLayoutController), coroutineDispatcher);
        final byte b3 = 0 == true ? 1 : 0;
        JavaAdapterKt.collectFlow(notificationPanelView, occludedToLockscreenTransitionViewModel.lockscreenTranslationY, new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda50
            public final /* synthetic */ NotificationPanelViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (b3) {
                    case 0:
                        NotificationPanelViewController notificationPanelViewController = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationStackScrollLayoutController;
                        Float f = (Float) obj;
                        notificationPanelViewController.getClass();
                        MigrateClocksToBlueprint migrateClocksToBlueprint2 = MigrateClocksToBlueprint.INSTANCE;
                        Flags.migrateClocksToBlueprint();
                        notificationPanelViewController.mKeyguardStatusViewController.setTranslationY$1(f.floatValue());
                        if (!notificationStackScrollLayoutController2.mView.mStateAnimator.isRunning()) {
                            notificationStackScrollLayoutController2.mView.setTranslationY(f.floatValue());
                            break;
                        }
                        break;
                    default:
                        NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController;
                        Float f2 = (Float) obj;
                        KeyguardStatusBarViewController keyguardStatusBarViewController2 = notificationPanelViewController2.mKeyguardStatusBarViewController;
                        float floatValue = f2.floatValue();
                        keyguardStatusBarViewController2.getClass();
                        KeyguardStatusBarViewController.isMigrationEnabled();
                        keyguardStatusBarViewController2.mExplicitAlpha = floatValue;
                        keyguardStatusBarViewController2.updateViewState();
                        new NotificationPanelViewController$$ExternalSyntheticLambda54(notificationPanelViewController2, false, notificationStackScrollLayoutController3).accept(f2);
                        break;
                }
            }
        }, coroutineDispatcher);
        JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionInteractor.transition((Edge) new Edge.StateToState(keyguardState2, keyguardState)), this.mLockscreenToDreamingTransition, coroutineDispatcher);
        Flags.migrateClocksToBlueprint();
        LockscreenToDreamingTransitionViewModel lockscreenToDreamingTransitionViewModel = this.mLockscreenToDreamingTransitionViewModel;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$12 = lockscreenToDreamingTransitionViewModel.lockscreenAlpha;
        final boolean z3 = true ? 1 : 0;
        JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$12, new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda50
            public final /* synthetic */ NotificationPanelViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (z3) {
                    case 0:
                        NotificationPanelViewController notificationPanelViewController = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationStackScrollLayoutController;
                        Float f = (Float) obj;
                        notificationPanelViewController.getClass();
                        MigrateClocksToBlueprint migrateClocksToBlueprint2 = MigrateClocksToBlueprint.INSTANCE;
                        Flags.migrateClocksToBlueprint();
                        notificationPanelViewController.mKeyguardStatusViewController.setTranslationY$1(f.floatValue());
                        if (!notificationStackScrollLayoutController2.mView.mStateAnimator.isRunning()) {
                            notificationStackScrollLayoutController2.mView.setTranslationY(f.floatValue());
                            break;
                        }
                        break;
                    default:
                        NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController;
                        Float f2 = (Float) obj;
                        KeyguardStatusBarViewController keyguardStatusBarViewController2 = notificationPanelViewController2.mKeyguardStatusBarViewController;
                        float floatValue = f2.floatValue();
                        keyguardStatusBarViewController2.getClass();
                        KeyguardStatusBarViewController.isMigrationEnabled();
                        keyguardStatusBarViewController2.mExplicitAlpha = floatValue;
                        keyguardStatusBarViewController2.updateViewState();
                        new NotificationPanelViewController$$ExternalSyntheticLambda54(notificationPanelViewController2, false, notificationStackScrollLayoutController3).accept(f2);
                        break;
                }
            }
        }, coroutineDispatcher);
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenTranslationY2 = lockscreenToDreamingTransitionViewModel.lockscreenTranslationY(this.mLockscreenToDreamingTransitionTranslationY);
        final byte b4 = 0 == true ? 1 : 0;
        JavaAdapterKt.collectFlow(notificationPanelView, lockscreenTranslationY2, new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda50
            public final /* synthetic */ NotificationPanelViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (b4) {
                    case 0:
                        NotificationPanelViewController notificationPanelViewController = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationStackScrollLayoutController;
                        Float f = (Float) obj;
                        notificationPanelViewController.getClass();
                        MigrateClocksToBlueprint migrateClocksToBlueprint2 = MigrateClocksToBlueprint.INSTANCE;
                        Flags.migrateClocksToBlueprint();
                        notificationPanelViewController.mKeyguardStatusViewController.setTranslationY$1(f.floatValue());
                        if (!notificationStackScrollLayoutController2.mView.mStateAnimator.isRunning()) {
                            notificationStackScrollLayoutController2.mView.setTranslationY(f.floatValue());
                            break;
                        }
                        break;
                    default:
                        NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController;
                        Float f2 = (Float) obj;
                        KeyguardStatusBarViewController keyguardStatusBarViewController2 = notificationPanelViewController2.mKeyguardStatusBarViewController;
                        float floatValue = f2.floatValue();
                        keyguardStatusBarViewController2.getClass();
                        KeyguardStatusBarViewController.isMigrationEnabled();
                        keyguardStatusBarViewController2.mExplicitAlpha = floatValue;
                        keyguardStatusBarViewController2.updateViewState();
                        new NotificationPanelViewController$$ExternalSyntheticLambda54(notificationPanelViewController2, false, notificationStackScrollLayoutController3).accept(f2);
                        break;
                }
            }
        }, coroutineDispatcher);
        new Edge.SceneToState(sceneKey, keyguardState);
        JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionInteractor.transition(new Edge.StateToState(keyguardState4, keyguardState)), this.mGoneToDreamingTransition, coroutineDispatcher);
        Flags.migrateClocksToBlueprint();
        GoneToDreamingTransitionViewModel goneToDreamingTransitionViewModel = this.mGoneToDreamingTransitionViewModel;
        JavaAdapterKt.collectFlow(notificationPanelView, goneToDreamingTransitionViewModel.lockscreenAlpha, new NotificationPanelViewController$$ExternalSyntheticLambda54(this, 0 == true ? 1 : 0, notificationStackScrollLayoutController), coroutineDispatcher);
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenTranslationY3 = goneToDreamingTransitionViewModel.lockscreenTranslationY(this.mGoneToDreamingTransitionTranslationY);
        final byte b5 = 0 == true ? 1 : 0;
        JavaAdapterKt.collectFlow(notificationPanelView, lockscreenTranslationY3, new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda50
            public final /* synthetic */ NotificationPanelViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (b5) {
                    case 0:
                        NotificationPanelViewController notificationPanelViewController = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationStackScrollLayoutController;
                        Float f = (Float) obj;
                        notificationPanelViewController.getClass();
                        MigrateClocksToBlueprint migrateClocksToBlueprint2 = MigrateClocksToBlueprint.INSTANCE;
                        Flags.migrateClocksToBlueprint();
                        notificationPanelViewController.mKeyguardStatusViewController.setTranslationY$1(f.floatValue());
                        if (!notificationStackScrollLayoutController2.mView.mStateAnimator.isRunning()) {
                            notificationStackScrollLayoutController2.mView.setTranslationY(f.floatValue());
                            break;
                        }
                        break;
                    default:
                        NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController;
                        Float f2 = (Float) obj;
                        KeyguardStatusBarViewController keyguardStatusBarViewController2 = notificationPanelViewController2.mKeyguardStatusBarViewController;
                        float floatValue = f2.floatValue();
                        keyguardStatusBarViewController2.getClass();
                        KeyguardStatusBarViewController.isMigrationEnabled();
                        keyguardStatusBarViewController2.mExplicitAlpha = floatValue;
                        keyguardStatusBarViewController2.updateViewState();
                        new NotificationPanelViewController$$ExternalSyntheticLambda54(notificationPanelViewController2, false, notificationStackScrollLayoutController3).accept(f2);
                        break;
                }
            }
        }, coroutineDispatcher);
        JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionInteractor.transition((Edge) new Edge.StateToState(keyguardState2, keyguardState5)), this.mLockscreenToOccludedTransition, coroutineDispatcher);
        Flags.migrateClocksToBlueprint();
        LockscreenToOccludedTransitionViewModel lockscreenToOccludedTransitionViewModel = this.mLockscreenToOccludedTransitionViewModel;
        JavaAdapterKt.collectFlow(notificationPanelView, lockscreenToOccludedTransitionViewModel.lockscreenAlpha, new NotificationPanelViewController$$ExternalSyntheticLambda54(this, 0 == true ? 1 : 0, notificationStackScrollLayoutController), coroutineDispatcher);
        final byte b6 = 0 == true ? 1 : 0;
        JavaAdapterKt.collectFlow(notificationPanelView, lockscreenToOccludedTransitionViewModel.lockscreenTranslationY, new Consumer(this) { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda50
            public final /* synthetic */ NotificationPanelViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (b6) {
                    case 0:
                        NotificationPanelViewController notificationPanelViewController = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationStackScrollLayoutController;
                        Float f = (Float) obj;
                        notificationPanelViewController.getClass();
                        MigrateClocksToBlueprint migrateClocksToBlueprint2 = MigrateClocksToBlueprint.INSTANCE;
                        Flags.migrateClocksToBlueprint();
                        notificationPanelViewController.mKeyguardStatusViewController.setTranslationY$1(f.floatValue());
                        if (!notificationStackScrollLayoutController2.mView.mStateAnimator.isRunning()) {
                            notificationStackScrollLayoutController2.mView.setTranslationY(f.floatValue());
                            break;
                        }
                        break;
                    default:
                        NotificationPanelViewController notificationPanelViewController2 = this.f$0;
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController;
                        Float f2 = (Float) obj;
                        KeyguardStatusBarViewController keyguardStatusBarViewController2 = notificationPanelViewController2.mKeyguardStatusBarViewController;
                        float floatValue = f2.floatValue();
                        keyguardStatusBarViewController2.getClass();
                        KeyguardStatusBarViewController.isMigrationEnabled();
                        keyguardStatusBarViewController2.mExplicitAlpha = floatValue;
                        keyguardStatusBarViewController2.updateViewState();
                        new NotificationPanelViewController$$ExternalSyntheticLambda54(notificationPanelViewController2, false, notificationStackScrollLayoutController3).accept(f2);
                        break;
                }
            }
        }, coroutineDispatcher);
        Flags.migrateClocksToBlueprint();
        PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel = this.mPrimaryBouncerToGoneTransitionViewModel;
        JavaAdapterKt.collectFlow(notificationPanelView, primaryBouncerToGoneTransitionViewModel.lockscreenAlpha, new NotificationPanelViewController$$ExternalSyntheticLambda54(this, true ? 1 : 0, notificationStackScrollLayoutController), coroutineDispatcher);
        JavaAdapterKt.collectFlow(notificationPanelView, primaryBouncerToGoneTransitionViewModel.notificationAlpha, new NotificationPanelViewController$$ExternalSyntheticLambda8(this, 10), coroutineDispatcher);
        JavaAdapterKt.collectFlow(notificationPanelView, this.mShadeAnimationInteractor.isLaunchingActivity, new NotificationPanelViewController$$ExternalSyntheticLambda8(this, 11), coroutineDispatcher);
        NotificationsHeadsUpRefactor notificationsHeadsUpRefactor = NotificationsHeadsUpRefactor.INSTANCE;
        Flags.notificationsHeadsUpRefactor();
        this.mEditModeContainer = notificationPanelView.findViewById(R.id.keyguard_edit_mode_container);
        this.mNowBarContainer = notificationPanelView.findViewById(R.id.now_bar_rootview);
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        lockscreenNotificationIconsOnlyController.getClass();
        lockscreenNotificationManager.addCallback(lockscreenNotificationIconsOnlyController);
        this.mIsKeyguardSupportLandscapePhone = (!DeviceState.shouldEnableKeyguardScreenRotation(notificationPanelView.getContext()) || DeviceState.isTablet() || LsRune.LOCKUI_SUB_DISPLAY_LOCK) ? false : true;
    }

    public void onFlingEnd(boolean z) {
        SecPanelSplitHelper secPanelSplitHelper;
        this.mIsFlinging = false;
        setOverExpansionInternal(0.0f, false);
        SecNotificationPanelViewController secNotificationPanelViewController = this.mSecNotificationPanelViewController;
        if (secNotificationPanelViewController != null && (secPanelSplitHelper = secNotificationPanelViewController.panelSplitHelper) != null) {
            secPanelSplitHelper.isOnceOverExpanded = false;
        }
        setAnimator$1(null);
        KeyguardStateControllerImpl keyguardStateControllerImpl = this.mKeyguardStateController;
        keyguardStateControllerImpl.mFlingingToDismissKeyguard = false;
        keyguardStateControllerImpl.mFlingingToDismissKeyguardDuringSwipeGesture = false;
        keyguardStateControllerImpl.mSnappingKeyguardBackAfterSwipe = false;
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        if (z) {
            InteractionJankMonitor interactionJankMonitor = (InteractionJankMonitor) quickSettingsControllerImpl.mInteractionJankMonitorLazy.get();
            if (interactionJankMonitor != null) {
                interactionJankMonitor.cancel(0);
            }
        } else {
            InteractionJankMonitor interactionJankMonitor2 = (InteractionJankMonitor) quickSettingsControllerImpl.mInteractionJankMonitorLazy.get();
            if (interactionJankMonitor2 != null) {
                interactionJankMonitor2.end(0);
            }
            notifyExpandingFinished();
        }
        updateExpansionAndVisibility();
        this.mNotificationStackScrollLayoutController.setPanelFlinging(false);
        this.mShadeLog.d("onFlingEnd called");
        quickSettingsControllerImpl.setExpandImmediate(false);
        ((ShadeRepositoryImpl) this.mShadeRepository)._currentFling.setValue(null);
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final void onLockStarEnabled(boolean z) {
        LogUtil.d("NotificationPanelView", KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("onLockStarEnabled : ", z), new Object[0]);
    }

    public final void onMiddleClicked() {
        int i = this.mBarState;
        if (i != 1) {
            if (i == 2 && !this.mQsController.getExpanded$1()) {
                this.mStatusBarStateController.setState(1);
                return;
            }
            return;
        }
        if (this.mDozingOnDown) {
            return;
        }
        this.mShadeLog.v("onMiddleClicked on Keyguard, mDozingOnDown: false");
        DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor = this.mDeviceEntryFaceAuthInteractor;
        deviceEntryFaceAuthInteractor.onNotificationPanelClicked();
        if (deviceEntryFaceAuthInteractor.canFaceAuthRun()) {
            this.mUpdateMonitor.requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin.UNLOCK_INTENT_LEGACY, "lockScreenEmptySpaceTap");
        } else {
            this.mLockscreenGestureLogger.write(188, 0, 0);
            new UiEventLoggerImpl().log(LockscreenGestureLogger.LockscreenUiEvent.LOCKSCREEN_LOCK_SHOW_HINT);
            this.mKeyguardIndicationController.showActionToUnlock();
        }
    }

    public void onQsSetExpansionHeightCalled(boolean z) {
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
        if (QpRune.QUICK_DATA_USAGE_LABEL) {
            ((DataUsageLabelManager) this.mDataUsageLabelManagerLazy.get()).updateLabelVisibility(false);
        }
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void onScreenTurningOn() {
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        KeyguardStatusViewController keyguardStatusViewController = this.mKeyguardStatusViewController;
        keyguardStatusViewController.refreshTime();
        keyguardStatusViewController.mKeyguardSliceViewController.refresh();
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void onThemeChanged() {
        this.mConfigurationListener.onThemeChanged();
    }

    public final void onTrackingStarted() {
        endClosing();
        QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
        if (quickPanelLogger != null) {
            quickPanelLogger.logPanelState("onTrackingStarted: isTracking(): " + isTracking() + " -> true");
        }
        ((ShadeRepositoryImpl) this.mShadeRepository)._legacyShadeTracking.updateState(null, Boolean.TRUE);
        this.mTouchDownOnHeadsUpPinnded = false;
        ShadeControllerImpl$$ExternalSyntheticLambda4 shadeControllerImpl$$ExternalSyntheticLambda4 = this.mTrackingStartedListener;
        if (shadeControllerImpl$$ExternalSyntheticLambda4 != null) {
            shadeControllerImpl$$ExternalSyntheticLambda4.f$0.runPostCollapseActions();
        }
        notifyExpandingStarted();
        updateExpansionAndVisibility();
        ScrimController scrimController = this.mScrimController;
        scrimController.mDarkenWhileDragging = !((KeyguardStateControllerImpl) scrimController.mKeyguardStateController).mCanDismissLockScreen;
        if (!scrimController.mKeyguardUnlockAnimationController.playingCannedUnlockAnimation) {
            scrimController.mAnimatingPanelExpansionOnUnlock = false;
        }
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        if (quickSettingsControllerImpl.mFullyExpanded) {
            quickSettingsControllerImpl.setExpandImmediate(true);
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
        StringBuilder sb;
        QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
        if (quickPanelLogger != null && (sb = this.mQuickPanelLogBuilder) != null) {
            sb.setLength(0);
            sb.append("onTrackingStopped: ");
            sb.append("expand: ");
            sb.append(z);
            sb.append(", isTracking(): ");
            sb.append(isTracking());
            sb.append(" -> false");
            quickPanelLogger.logPanelState(sb.toString());
        }
        ((ShadeRepositoryImpl) this.mShadeRepository)._legacyShadeTracking.updateState(null, Boolean.FALSE);
        this.mTouchDownOnHeadsUpPinnded = false;
        updateExpansionAndVisibility();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        if (z) {
            notificationStackScrollLayoutController.mView.setOverScrollAmount(0.0f, true, true, true);
        }
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.mPanelTracking = false;
        notificationStackScrollLayout.mAmbientState.mPanelTracking = false;
        if (z && (((i = this.mBarState) == 1 || i == 2) && !this.mHintAnimationRunning)) {
            this.mSecAffordanceHelper.reset(true);
        }
        NotificationShadeDepthController notificationShadeDepthController = this.mDepthController;
        if (notificationShadeDepthController.blursDisabledForUnlock) {
            notificationShadeDepthController.blursDisabledForUnlock = false;
        }
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final Bundle onUiInfoRequested(boolean z) {
        new Bundle();
        Bundle onUiInfoRequested = this.mKeyguardBottomAreaViewController.onUiInfoRequested(z);
        NotificationPanelView notificationPanelView = this.mView;
        int i = Settings.System.getInt(notificationPanelView.getContext().getContentResolver(), SettingsHelper.INDEX_LOCKSCREEN_MINIMIZING_NOTIFICATION, 1);
        onUiInfoRequested.putInt("noti_type", i);
        onUiInfoRequested.putInt("noti_visibility", Settings.Secure.getInt(notificationPanelView.getContext().getContentResolver(), SettingsHelper.INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS, 1));
        onUiInfoRequested.putInt("noti_top", getNotificationTopMargin(z));
        if (i == 0) {
            onUiInfoRequested.putInt("noti_number", computeMaxKeyguardNotifications());
        } else {
            onUiInfoRequested.putInt("noti_bottom", getNotificationTopMargin(z) + (z ? this.mResources.getDimensionPixelSize(R.dimen.keyguard_indication_dls_default_notification_height_land) : this.mResources.getDimensionPixelSize(R.dimen.keyguard_indication_dls_default_notification_height)));
        }
        Log.d("NotificationPanelView", "onUiInfoRequested bottom: " + onUiInfoRequested.toString());
        return onUiInfoRequested;
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final void onUnNeedLockAppStarted(ComponentName componentName) {
        KeyguardBottomAreaViewController keyguardBottomAreaViewController = this.mKeyguardBottomAreaViewController;
        if (keyguardBottomAreaViewController != null) {
            keyguardBottomAreaViewController.launchApp(componentName);
        }
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final void onUserActivity() {
        ((CentralSurfacesImpl) this.mCentralSurfaces).userActivity();
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final void onViewModeChanged(int i) {
        LogUtil.d("NotificationPanelView", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "onViewModeChanged : "), new Object[0]);
        setViewMode(i);
        this.mNotificationStackScrollLayoutController.mView.mSpeedBumpIndexDirty = true;
    }

    public final void positionClockAndNotifications(boolean z) {
        int i;
        int i2;
        PluginNotificationController pluginNotificationController;
        boolean z2 = false;
        int i3 = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        boolean isAddOrRemoveAnimationPending = notificationStackScrollLayoutController.isAddOrRemoveAnimationPending();
        boolean isKeyguardShowing$2 = isKeyguardShowing$2();
        if (isKeyguardShowing$2 || z) {
            updateClockAppearance();
        }
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        if (isKeyguardShowing$2) {
            KeyguardClockPositionAlgorithm.Result result = this.mClockPositionResult;
            int i4 = result.stackScrollerPaddingExpanded;
            boolean z3 = CscRune.KEYGUARD_DCM_LIVE_UX;
            i = z3 ? result.stackScrollerPadding : i4;
            if (z3) {
                if (lockscreenNotificationIconsOnlyController != null) {
                    PluginNotificationController pluginNotificationController2 = lockscreenNotificationIconsOnlyController.mNotificationControllerWrapper.mNotificationController;
                    if ((pluginNotificationController2 != null ? pluginNotificationController2.getIconContainer() : null) != null) {
                        PluginNotificationController pluginNotificationController3 = lockscreenNotificationIconsOnlyController.mNotificationControllerWrapper.mNotificationController;
                        i2 = (pluginNotificationController3 != null ? pluginNotificationController3.getIconContainer() : null).getHeight();
                        i += this.mMascotViewContainer.updatePosition(i, i2);
                    }
                }
                i2 = 0;
                i += this.mMascotViewContainer.updatePosition(i, i2);
            }
        } else {
            i = this.mQsController.getHeaderHeight();
        }
        notificationStackScrollLayoutController.mView.mIntrinsicPadding = i;
        int i5 = this.mStackScrollerMeasuringPass + 1;
        this.mStackScrollerMeasuringPass = i5;
        if (i5 > 2) {
            RecyclerView$$ExternalSyntheticOutline0.m(this.mStackScrollerMeasuringPass, "NotificationPanelView", new StringBuilder("increased StackScrollerMeasuringPass : "));
        }
        requestScrollerTopPaddingUpdate(isAddOrRemoveAnimationPending);
        if (this.mStackScrollerMeasuringPass > 2) {
            RecyclerView$$ExternalSyntheticOutline0.m(this.mStackScrollerMeasuringPass, "NotificationPanelView", new StringBuilder("reset StackScrollerMeasuringPass from "));
        }
        this.mStackScrollerMeasuringPass = 0;
        this.mAnimateNextPositionUpdate = false;
        if (lockscreenNotificationIconsOnlyController != null) {
            if (this.mDozing && this.mDozeParameters.getAlwaysOn()) {
                z2 = true;
            }
            if (!z2) {
                LockscreenNotificationManager lockscreenNotificationManager = this.mLockscreenNotificationManager;
                lockscreenNotificationManager.getClass();
                if (!LockscreenNotificationManager.isNotificationIconsOnlyShowing() && (LockscreenNotificationManager.mCurrentNotificationType != 0 || lockscreenNotificationManager.mSettingNotificationType != 1)) {
                    return;
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
        boolean z2 = z && isUserSwitcherEnabled;
        boolean z3 = !z && this.mKeyguardUserSwitcherEnabled && isUserSwitcherEnabled;
        KeyguardBottomAreaRefactor keyguardBottomAreaRefactor = KeyguardBottomAreaRefactor.INSTANCE;
        Flags.keyguardBottomAreaRefactor();
        initBottomArea();
        ViewGroup viewGroup = (ViewGroup) this.mKeyguardBottomArea.findViewById(R.id.keyguard_indication_area);
        KeyguardIndicationController keyguardIndicationController = this.mKeyguardIndicationController;
        keyguardIndicationController.setIndicationArea(viewGroup);
        keyguardIndicationController.setUpperTextView((KeyguardIndicationTextView) this.mKeyguardBottomArea.findViewById(R.id.keyguard_upper_fingerprint_indication));
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) this.mStatusBarStateController;
        float f = statusBarStateControllerImpl.mDozeAmount;
        this.mStatusBarStateListener.onDozeAmountChanged(f, statusBarStateControllerImpl.mDozeInterpolator.getInterpolation(f));
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        KeyguardStatusViewController keyguardStatusViewController = this.mKeyguardStatusViewController;
        int i = this.mBarState;
        keyguardStatusViewController.setKeyguardStatusViewVisibility(i, i, false, false);
        KeyguardQsUserSwitchController keyguardQsUserSwitchController = this.mKeyguardQsUserSwitchController;
        if (keyguardQsUserSwitchController != null) {
            int i2 = this.mBarState;
            keyguardQsUserSwitchController.mKeyguardVisibilityHelper.setViewVisibility(i2, i2, false, false);
        }
        KeyguardUserSwitcherController keyguardUserSwitcherController = this.mKeyguardUserSwitcherController;
        if (keyguardUserSwitcherController != null) {
            int i3 = this.mBarState;
            keyguardUserSwitcherController.mKeyguardVisibilityHelper.setViewVisibility(i3, i3, false, false);
        }
        Flags.keyguardBottomAreaRefactor();
        setKeyguardBottomAreaVisibility(this.mBarState, false);
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void registerAODDoubleTouchListener(View.OnTouchListener onTouchListener) {
        this.mAODDoubleTouchListener = onTouchListener;
    }

    public final void requestScrollerTopPaddingUpdate(boolean z) {
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        int keyguardNotificationStaticPadding = getKeyguardNotificationStaticPadding();
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        float calculateNotificationsTopPadding = quickSettingsControllerImpl.calculateNotificationsTopPadding(keyguardNotificationStaticPadding);
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        notificationStackScrollLayoutController.getClass();
        SceneContainerFlag.assertInLegacyMode();
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        int i2 = (int) calculateNotificationsTopPadding;
        if (notificationStackScrollLayout.getLayoutMinHeight() + i2 > notificationStackScrollLayout.getHeight()) {
            notificationStackScrollLayout.mTopPaddingOverflow = r3 - notificationStackScrollLayout.getHeight();
        } else {
            notificationStackScrollLayout.mTopPaddingOverflow = 0.0f;
        }
        boolean z2 = z && !notificationStackScrollLayout.mKeyguardBypassEnabled;
        AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
        if (ambientState.mTopPadding != i2) {
            ambientState.mTopPadding = i2;
            notificationStackScrollLayout.updateAlgorithmHeightAndPadding();
            notificationStackScrollLayout.updateContentHeight();
            if (notificationStackScrollLayout.mAmbientState.isOnKeyguard() && notificationStackScrollLayout.mShouldSkipTopPaddingAnimationAfterFold) {
                notificationStackScrollLayout.mShouldSkipTopPaddingAnimationAfterFold = false;
            } else if (z2 && notificationStackScrollLayout.mAnimationsEnabled && notificationStackScrollLayout.mIsExpanded) {
                notificationStackScrollLayout.mTopPaddingNeedsAnimation = true;
                notificationStackScrollLayout.mNeedsAnimation = true;
            }
            notificationStackScrollLayout.updateStackPosition(false);
            notificationStackScrollLayout.requestChildrenUpdate();
            notificationStackScrollLayout.notifyHeightChangeListener(null, z2);
            int i3 = notificationStackScrollLayout.mOwnScrollY;
            if (i3 > 0 && i3 < notificationStackScrollLayout.getScrollAmountToScrollBoundary() && notificationStackScrollLayout.mIsChangedOrientation) {
                notificationStackScrollLayout.setOwnScrollY(notificationStackScrollLayout.getScrollAmountToScrollBoundary());
                notificationStackScrollLayout.mIsChangedOrientation = false;
            }
        }
        notificationStackScrollLayout.setExpandedHeight(notificationStackScrollLayout.mExpandedHeight);
        if (isKeyguardShowing$2() && this.mKeyguardBypassController.getBypassEnabled()) {
            quickSettingsControllerImpl.updateExpansion();
        }
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void resetAlpha() {
        this.mView.setAlpha(1.0f);
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final void resetDynamicLock() {
        Log.d("NotificationPanelView", "resetDynamicLock()");
        if (!this.mUpdateMonitor.isKeyguardVisible()) {
            this.mKeyguardBottomArea.setVisibility(8);
        }
        this.mKeyguardIndicationController.setVisible(this.mBarState == 1);
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void resetTranslation() {
        this.mView.setTranslationX(0.0f);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
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
                    if (Intrinsics.areEqual(f, view.getAlpha())) {
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

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void resetViews(boolean z) {
        resetViews(z, false);
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void setAlpha(int i, boolean z) {
        if (this.mPanelAlpha != i) {
            this.mPanelAlpha = i;
            PropertyAnimator.setProperty(this.mView, this.mPanelAlphaAnimator, i, i == 255 ? this.mPanelAlphaInPropertiesAnimator : this.mPanelAlphaOutPropertiesAnimator, z);
        }
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void setAlphaChangeAnimationEndAction(BrightnessMirrorController$$ExternalSyntheticLambda0 brightnessMirrorController$$ExternalSyntheticLambda0) {
        this.mPanelAlphaEndAction = brightnessMirrorController$$ExternalSyntheticLambda0;
    }

    public final void setAnimator$1(ValueAnimator valueAnimator) {
        Set<Animator> set = this.mTestSetOfAnimatorsUsed;
        if (set != null && valueAnimator != null) {
            set.add(valueAnimator);
        }
        this.mHeightAnimator = valueAnimator;
        if (valueAnimator == null && this.mPanelUpdateWhenAnimatorEnds) {
            this.mPanelUpdateWhenAnimatorEnds = false;
            updateExpandedHeightToMaxHeight();
        }
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void setBouncerShowing(boolean z) {
        this.mBouncerShowing = z;
        int i = FooterViewRefactor.$r8$clinit;
        Flags.notificationsFooterViewRefactor();
        this.mNotificationStackScrollLayoutController.updateShowEmptyShadeView();
        if (z && this.mMediaNowBarExpandState == 1) {
            new Handler(Looper.getMainLooper()).postDelayed(new NotificationPanelViewController$$ExternalSyntheticLambda31(this, 7), 100L);
        } else {
            updateVisibility$6();
        }
    }

    public void setClosing(boolean z) {
        ((ShadeRepositoryImpl) this.mShadeRepository)._legacyIsClosing.updateState(null, Boolean.valueOf(z));
        this.mAmbientState.mIsClosing = z;
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void setDozing(boolean z, boolean z2) {
        if (z == this.mDozing) {
            return;
        }
        NotificationPanelView notificationPanelView = this.mView;
        notificationPanelView.mDozing = z;
        this.mDozing = z;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        notificationStackScrollLayoutController.getClass();
        SceneContainerFlag.assertInLegacyMode();
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
        if (ambientState.mDozing != z) {
            ambientState.mDozing = z;
            notificationStackScrollLayout.requestChildrenUpdate();
            notificationStackScrollLayout.notifyHeightChangeListener(notificationStackScrollLayout.mShelf, false);
        }
        KeyguardBottomAreaRefactor keyguardBottomAreaRefactor = KeyguardBottomAreaRefactor.INSTANCE;
        Flags.keyguardBottomAreaRefactor();
        ((KeyguardRepositoryImpl) this.mKeyguardBottomAreaInteractor.repository)._animateBottomAreaDozingTransitions.updateState(null, Boolean.valueOf(z2));
        this.mKeyguardBottomAreaViewController.setDozing(z);
        KeyguardStatusBarViewController keyguardStatusBarViewController = this.mKeyguardStatusBarViewController;
        boolean z3 = this.mDozing;
        keyguardStatusBarViewController.getClass();
        KeyguardStatusBarViewController.isMigrationEnabled();
        keyguardStatusBarViewController.mDozing = z3;
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
            statusBarStateControllerImpl.mClockSwitchView = (KeyguardClockSwitch) notificationPanelView.findViewById(R.id.keyguard_clock_container);
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

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final void setDynamicLockData(String str) {
        if (str == null || str.isEmpty()) {
            return;
        }
        DynamicLockData fromJSon = DynamicLockData.fromJSon(str);
        if (fromJSon != null) {
            this.mNotiCardCount = fromJSon.getNotificationData().getCardData().getNotiCardNumbers().intValue();
        }
        LogUtil.d("NotificationPanelView", "setDynamicLockData card numbers: " + this.mNotiCardCount, new Object[0]);
    }

    public final void setExpandSettingsPanel$1(boolean z) {
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
        SecQSPanelController secQSPanelController = centralSurfacesImpl.mQSPanelController;
        if (z != secQSPanelController.mExpandSettingsPanel) {
            secQSPanelController.mExpandSettingsPanel = z;
        }
        SecQuickQSPanelController secQuickQSPanelController = centralSurfacesImpl.mQuickQSPanelController;
        if (z != secQuickQSPanelController.mExpandSettingsPanel) {
            secQuickQSPanelController.mExpandSettingsPanel = z;
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
        ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).batchApplyWindowLayoutParams(new Runnable() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda45
            @Override // java.lang.Runnable
            public final void run() {
                float calculatePanelHeightExpanded;
                StringBuilder sb;
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                float f2 = f;
                if (notificationPanelViewController.mExpandLatencyTracking && f2 != 0.0f) {
                    DejankUtils.postAfterTraversal(new NotificationPanelViewController$$ExternalSyntheticLambda31(notificationPanelViewController, 8));
                    notificationPanelViewController.mExpandLatencyTracking = false;
                }
                float maxPanelTransitionDistance = notificationPanelViewController.getMaxPanelTransitionDistance();
                if (notificationPanelViewController.mHeightAnimator == null) {
                    MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
                    Flags.migrateClocksToBlueprint();
                    if (notificationPanelViewController.isTracking()) {
                        notificationPanelViewController.setOverExpansionInternal(Math.max(0.0f, f2 - maxPanelTransitionDistance), true);
                    }
                }
                float min = Math.min(f2, maxPanelTransitionDistance);
                notificationPanelViewController.mExpandedHeight = min;
                if (min < 1.0f && min != 0.0f && notificationPanelViewController.isClosing()) {
                    notificationPanelViewController.mExpandedHeight = 0.0f;
                    ValueAnimator valueAnimator = notificationPanelViewController.mHeightAnimator;
                    if (valueAnimator != null) {
                        valueAnimator.end();
                    }
                }
                boolean z = notificationPanelViewController.mHeightAnimator == null;
                float f3 = notificationPanelViewController.mExpandedHeight;
                float f4 = notificationPanelViewController.mExpandedFraction;
                float min2 = Math.min(1.0f, maxPanelTransitionDistance == 0.0f ? 0.0f : f3 / maxPanelTransitionDistance);
                QuickPanelLogger quickPanelLogger = notificationPanelViewController.mQuickPanelLogger;
                if (quickPanelLogger != null && (sb = notificationPanelViewController.mQuickPanelLogBuilder) != null && ((Float.compare(f4, 0.0f) == 0 && Float.compare(notificationPanelViewController.mExpandedFraction, 0.0f) > 0) || (Float.compare(f4, 0.0f) > 0 && Float.compare(notificationPanelViewController.mExpandedFraction, 0.0f) == 0))) {
                    sb.setLength(0);
                    sb.append("setExpandedHeightInternal: ");
                    sb.append("h: ");
                    sb.append(f2);
                    sb.append(", heightAnimatorIsNull: ");
                    sb.append(z);
                    sb.append(", isTracking(): ");
                    sb.append(notificationPanelViewController.isTracking());
                    sb.append(", expandedHeight: ");
                    sb.append(f3);
                    sb.append(", maxPanelHeight: ");
                    sb.append(maxPanelTransitionDistance);
                    sb.append(", isClosing(): ");
                    sb.append(notificationPanelViewController.isClosing());
                    sb.append(", expandedFraction: ");
                    sb.append(f4);
                    sb.append(" -> ");
                    sb.append(min2);
                    quickPanelLogger.logPanelState(sb.toString());
                }
                float min3 = Math.min(1.0f, maxPanelTransitionDistance == 0.0f ? 0.0f : notificationPanelViewController.mExpandedHeight / maxPanelTransitionDistance);
                notificationPanelViewController.mExpandedFraction = min3;
                ((ShadeRepositoryImpl) notificationPanelViewController.mShadeRepository)._legacyShadeExpansion.updateState(null, Float.valueOf(min3));
                float f5 = notificationPanelViewController.mExpandedHeight;
                float f6 = notificationPanelViewController.mExpandedFraction;
                QuickSettingsControllerImpl quickSettingsControllerImpl = notificationPanelViewController.mQsController;
                quickSettingsControllerImpl.mShadeExpandedHeight = f5;
                quickSettingsControllerImpl.mShadeExpandedFraction = f6;
                notificationPanelViewController.mExpansionDragDownAmountPx = f2;
                int i = SceneContainerFlag.$r8$clinit;
                Flags.sceneContainer();
                float f7 = notificationPanelViewController.mExpandedFraction;
                notificationPanelViewController.mAmbientState.mExpansionFraction = f7;
                float f8 = notificationPanelViewController.mExpandedHeight;
                if (f8 <= 0.0f) {
                    notificationPanelViewController.mShadeLog.logExpansionChanged("onHeightUpdated: fully collapsed.", f7, notificationPanelViewController.isExpanded(), notificationPanelViewController.isTracking(), notificationPanelViewController.mExpansionDragDownAmountPx);
                } else if (notificationPanelViewController.isFullyExpanded()) {
                    notificationPanelViewController.mShadeLog.logExpansionChanged("onHeightUpdated: fully expanded.", notificationPanelViewController.mExpandedFraction, notificationPanelViewController.isExpanded(), notificationPanelViewController.isTracking(), notificationPanelViewController.mExpansionDragDownAmountPx);
                }
                if ((!quickSettingsControllerImpl.getExpanded$1() || quickSettingsControllerImpl.isExpandImmediate() || (notificationPanelViewController.mIsExpandingOrCollapsing && quickSettingsControllerImpl.mExpandedWhenExpandingStarted)) && notificationPanelViewController.mStackScrollerMeasuringPass <= 2) {
                    notificationPanelViewController.positionClockAndNotifications(false);
                }
                if (quickSettingsControllerImpl.isExpandImmediate() || (quickSettingsControllerImpl.getExpanded$1() && !quickSettingsControllerImpl.isTracking() && quickSettingsControllerImpl.mExpansionAnimator == null && !quickSettingsControllerImpl.mExpansionFromOverscroll)) {
                    if (notificationPanelViewController.isKeyguardShowing$2()) {
                        calculatePanelHeightExpanded = f8 / notificationPanelViewController.getMaxPanelHeight();
                    } else {
                        NotificationStackScrollLayout notificationStackScrollLayout = notificationPanelViewController.mNotificationStackScrollLayoutController.mView;
                        float layoutMinHeight = notificationStackScrollLayout.getLayoutMinHeight() + notificationStackScrollLayout.mIntrinsicPadding;
                        calculatePanelHeightExpanded = (f8 - layoutMinHeight) / (quickSettingsControllerImpl.calculatePanelHeightExpanded(notificationPanelViewController.mClockPositionResult.stackScrollerPadding) - layoutMinHeight);
                    }
                    quickSettingsControllerImpl.setExpansionHeight((calculatePanelHeightExpanded * (quickSettingsControllerImpl.mMaxExpansionHeight - r4)) + quickSettingsControllerImpl.mMinExpansionHeight);
                }
                if (QpRune.QUICK_DATA_USAGE_LABEL) {
                    DataUsageLabelManager dataUsageLabelManager = (DataUsageLabelManager) notificationPanelViewController.mDataUsageLabelManagerLazy.get();
                    DataUsageLabelParent dataUsageLabelParent = dataUsageLabelManager.mDataUsageLabelParent;
                    float min4 = Math.min(1.0f, f8 / dataUsageLabelParent.mMaxPanelHeightSupplier.getAsInt());
                    ViewGroup parentViewGroup = dataUsageLabelParent.getParentViewGroup();
                    if (Float.compare(min4, 1.0f) == 0 && !dataUsageLabelManager.mLabelAlphaAnimStarted) {
                        dataUsageLabelManager.mLabelAlphaAnimStarted = true;
                        dataUsageLabelManager.animateLabelAlpha(parentViewGroup, true);
                    } else if (min4 < 1.0f && dataUsageLabelManager.mLabelAlphaAnimStarted) {
                        dataUsageLabelManager.mLabelAlphaAnimStarted = false;
                        dataUsageLabelManager.animateLabelAlpha(parentViewGroup, false);
                    } else if (min4 == 0.0f) {
                        dataUsageLabelManager.mLabelAlphaAnimStarted = false;
                    }
                }
                notificationPanelViewController.updateExpandedHeight(f8);
                if (notificationPanelViewController.mBarState == 1) {
                    notificationPanelViewController.mKeyguardStatusBarViewController.updateViewState();
                }
                quickSettingsControllerImpl.updateExpansion();
                notificationPanelViewController.updateNotificationTranslucency();
                notificationPanelViewController.updatePanelExpanded();
                notificationPanelViewController.updateGestureExclusionRect();
                notificationPanelViewController.updateExpansionAndVisibility();
            }
        });
    }

    public void setForceFlingAnimationForTest(boolean z) {
        this.mForceFlingAnimationForTest = z;
    }

    public void setHeadsUpDraggingStartingHeight(int i) {
        this.mHeadsUpStartHeight = i;
        float maxPanelHeight = getMaxPanelHeight();
        setPanelScrimMinFraction(maxPanelHeight > 0.0f ? this.mHeadsUpStartHeight / maxPanelHeight : 0.0f);
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void setImportantForAccessibility(int i) {
        this.mView.setImportantForAccessibility(i);
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

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void setKeyguardStatusBarAlpha() {
        KeyguardStatusBarViewController keyguardStatusBarViewController = this.mKeyguardStatusBarViewController;
        keyguardStatusBarViewController.getClass();
        KeyguardStatusBarViewController.isMigrationEnabled();
        keyguardStatusBarViewController.mExplicitAlpha = -1.0f;
        keyguardStatusBarViewController.updateViewState();
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void setKeyguardTransitionProgress(float f) {
        float interpolation = ((PathInterpolator) Interpolators.ALPHA_IN).getInterpolation(f);
        this.mKeyguardOnlyContentAlpha = interpolation;
        this.mKeyguardOnlyTransitionTranslationY = 0;
        if (this.mBarState == 1) {
            this.mBottomAreaShadeAlpha = interpolation;
            updateKeyguardBottomAreaAlpha();
        }
        updateClock$1();
    }

    public final void setListening$3(boolean z) {
        KeyguardStatusBarViewController keyguardStatusBarViewController = this.mKeyguardStatusBarViewController;
        keyguardStatusBarViewController.getClass();
        KeyguardStatusBarViewController.isMigrationEnabled();
        if (z != keyguardStatusBarViewController.mBatteryListening) {
            keyguardStatusBarViewController.mBatteryListening = z;
            KeyguardStatusBarViewController.AnonymousClass3 anonymousClass3 = keyguardStatusBarViewController.mBatteryStateChangeCallback;
            BatteryController batteryController = keyguardStatusBarViewController.mBatteryController;
            if (z) {
                ((BatteryControllerImpl) batteryController).addCallback(anonymousClass3);
            } else {
                ((BatteryControllerImpl) batteryController).removeCallback(anonymousClass3);
            }
        }
        QS qs = this.mQsController.mQs;
        if (qs != null) {
            qs.setListening(z);
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

    @Override // com.android.systemui.shade.ShadeViewController
    public final void setOnStatusBarDownEvent(MotionEvent motionEvent) {
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("setOnStatusBarDownEvent null ? "), motionEvent == null, "NotificationPanelView");
        this.mDownEventFromOverView = motionEvent;
        this.mPanelSplitHelper.synthesizedActionDown = motionEvent;
    }

    public void setOverExpansion(float f) {
        SecPanelSplitHelper secPanelSplitHelper;
        if (!SecPanelSplitHelper.isEnabled() || f == this.mOverExpansion) {
            return;
        }
        this.mOverExpansion = f;
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        int i = ((NotificationPanelViewController) quickSettingsControllerImpl.mPanelViewControllerLazy.get()).mNavigationBarBottomHeight;
        int i2 = quickSettingsControllerImpl.mAmbientState.mStackTopMargin;
        quickSettingsControllerImpl.mQsFrameTranslateController.getClass();
        this.mNotificationStackScrollLayoutController.setOverExpansion(f);
        SecNotificationPanelViewController secNotificationPanelViewController = this.mSecNotificationPanelViewController;
        if (secNotificationPanelViewController != null) {
            float f2 = (int) f;
            int i3 = (int) f2;
            QS qs = secNotificationPanelViewController.quickSettingsController.mQs;
            if (qs != null) {
                qs.setOverScrollAmount(i3);
            }
            if (f2 <= 15.0f || (secPanelSplitHelper = secNotificationPanelViewController.panelSplitHelper) == null) {
                return;
            }
            secPanelSplitHelper.isOnceOverExpanded = true;
        }
    }

    public final void setOverExpansionInternal(float f, boolean z) {
        if (!z) {
            this.mLastGesturedOverExpansion = -1.0f;
            setOverExpansion(f);
        } else if (this.mLastGesturedOverExpansion != f) {
            this.mLastGesturedOverExpansion = f;
            float saturate = MathUtils.saturate(f / (this.mView.getHeight() / 3.0f));
            Interpolator interpolator = Interpolators.EMPHASIZED;
            float exp = (float) (1.0d - Math.exp(saturate * (-4.0f)));
            if (0.0f > exp) {
                exp = 0.0f;
            }
            setOverExpansion(exp * this.mPanelFlingOvershootAmount * 1.5f);
        }
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void setOverStretchAmount(float f) {
        float height = f / this.mView.getHeight();
        Interpolator interpolator = Interpolators.EMPHASIZED;
        float exp = (float) (1.0d - Math.exp(height * (-4.0f)));
        if (0.0f > exp) {
            exp = 0.0f;
        }
        this.mOverStretchAmount = exp * this.mMaxOverscrollAmountForPulse;
        positionClockAndNotifications(true);
    }

    public final void setPanelScrimMinFraction(float f) {
        this.mMinFraction = f;
        this.mDepthController.getClass();
        float f2 = this.mMinFraction;
        ScrimController scrimController = this.mScrimController;
        scrimController.getClass();
        if (Float.isNaN(f2)) {
            throw new IllegalArgumentException("minFraction should not be NaN");
        }
        scrimController.mPanelScrimMinFraction = f2;
        scrimController.calculateAndUpdatePanelExpansion();
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final void setPluginLock(PluginLock pluginLock) {
        Log.d("NotificationPanelView", "setPluginLock");
        this.mPluginLock = pluginLock;
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void setPulsing(boolean z) {
        this.mPulsing = z;
        DozeParameters dozeParameters = this.mDozeParameters;
        boolean z2 = !dozeParameters.getDisplayNeedsBlanking() && dozeParameters.getAlwaysOn();
        if (z2) {
            this.mAnimateNextPositionUpdate = true;
        }
        if (!this.mPulsing && !this.mDozing) {
            this.mAnimateNextPositionUpdate = false;
        }
        NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScrollLayoutController.mView;
        if (notificationStackScrollLayout.mPulsing || z) {
            notificationStackScrollLayout.mPulsing = z;
            notificationStackScrollLayout.mAmbientState.mPulsing = z;
            notificationStackScrollLayout.mSwipeHelper.mPulsing = z;
            notificationStackScrollLayout.updateNotificationAnimationStates();
            notificationStackScrollLayout.updateAlgorithmHeightAndPadding();
            notificationStackScrollLayout.updateContentHeight();
            notificationStackScrollLayout.requestChildrenUpdate();
            notificationStackScrollLayout.notifyHeightChangeListener(null, z2);
        }
        updateKeyguardStatusViewAlignment(true);
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void setQsScrimEnabled(boolean z) {
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        boolean z2 = quickSettingsControllerImpl.mScrimEnabled != z;
        quickSettingsControllerImpl.mScrimEnabled = z;
        if (z2) {
            quickSettingsControllerImpl.updateQsState$1$1();
        }
    }

    public final void setShowShelfOnly(boolean z) {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.mShouldShowShelfOnly = z;
        notificationStackScrollLayout.updateAlgorithmLayoutMinHeight();
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void setTouchAndAnimationDisabled(boolean z) {
        QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
        if (quickPanelLogger != null) {
            quickPanelLogger.logPanelState("setTouchAndAnimationDisabled: " + this.mTouchDisabled + " -> " + z);
        }
        this.mTouchDisabled = z;
        if (z) {
            this.mSecAffordanceHelper.getClass();
            cancelHeightAnimator();
            if (isTracking()) {
                onTrackingStopped(true);
            }
            notifyExpandingFinished();
        }
        boolean z2 = !z;
        NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.mAnimationsEnabled = z2;
        notificationStackScrollLayout.updateNotificationAnimationStates();
        if (z2) {
            return;
        }
        notificationStackScrollLayout.mSwipedOutViews.clear();
        notificationStackScrollLayout.mChildrenToRemoveAnimated.clear();
        notificationStackScrollLayout.clearTemporaryViewsInGroup(notificationStackScrollLayout, "setAnimationsEnabled");
    }

    public void setTouchSlopExceeded(boolean z) {
        this.mTouchSlopExceeded = z;
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void setUserSetupComplete$1(boolean z) {
        this.mUserSetupComplete = z;
        this.mKeyguardBottomAreaViewController.setUserSetupComplete$1(z);
    }

    public final void setViewMode(int i) {
        RecyclerView$$ExternalSyntheticOutline0.m(this.mPluginLockViewMode, "NotificationPanelView", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "setViewMode, newMode:", ", oldMode:"));
        this.mKeyguardBottomAreaViewController.onViewModeChanged(i);
        if (i != this.mPluginLockViewMode || i == 1) {
            this.mPluginLockViewMode = i;
            CentralSurfaces centralSurfaces = this.mCentralSurfaces;
            if (centralSurfaces != null) {
                boolean z = i == 1 && i == 1;
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("setDlsOverLay() : ", "CentralSurfaces", z);
                ((CentralSurfacesImpl) centralSurfaces).mIsDlsOverlay = z;
            }
            final DcmMascotViewContainer dcmMascotViewContainer = this.mMascotViewContainer;
            NotificationPanelView notificationPanelView = this.mView;
            AnonymousClass17 anonymousClass17 = this.mSystemUIWidgetCallback;
            if (i == 0) {
                CentralSurfaces centralSurfaces2 = this.mCentralSurfaces;
                if (((centralSurfaces2 != null && ((CentralSurfacesImpl) centralSurfaces2).mBouncerShowing) || notificationPanelView.getVisibility() == 0) && this.mBarState != 0) {
                    if (CscRune.KEYGUARD_DCM_LIVE_UX && dcmMascotViewContainer.getVisibility() != 0 && dcmMascotViewContainer.isMascotEnabled()) {
                        dcmMascotViewContainer.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.DcmMascotViewContainer$updateDelayed$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                DcmMascotViewContainer.this.setMascotViewVisible(0);
                            }
                        }, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, TimeUnit.MILLISECONDS);
                    }
                    Log.d("NotificationPanelView", "setViewMode, removeSystemUIWidgetCallback:" + anonymousClass17);
                    WallpaperUtils.removeSystemUIWidgetCallback(anonymousClass17);
                }
            } else if (i == 1) {
                notificationPanelView.setClickable(false);
                if (CscRune.KEYGUARD_DCM_LIVE_UX) {
                    dcmMascotViewContainer.setMascotViewVisible(8);
                }
                WallpaperUtils.registerSystemUIWidgetCallback(anonymousClass17, 256L);
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

    @Override // com.android.systemui.shade.ShadeSurface
    public final void setWillPlayDelayedDozeAmountAnimation(boolean z) {
        if (this.mWillPlayDelayedDozeAmountAnimation == z) {
            return;
        }
        this.mWillPlayDelayedDozeAmountAnimation = z;
        this.mWakeUpCoordinator.logDelayingClockWakeUpAnimation(z);
        this.mKeyguardMediaController.isDozeWakeUpAnimationWaiting = z;
        positionClockAndNotifications(false);
    }

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean shouldHideStatusBarIconsWhenExpanded() {
        if (isLaunchingActivity$1()) {
            return false;
        }
        HeadsUpAppearanceController headsUpAppearanceController = this.mHeadsUpAppearanceController;
        if (headsUpAppearanceController == null || !headsUpAppearanceController.shouldBeVisible$1()) {
            return !this.mShowIconsWhenExpanded;
        }
        return false;
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void showAodUi() {
        setDozing(true, false);
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) this.mStatusBarStateController;
        statusBarStateControllerImpl.getClass();
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        statusBarStateControllerImpl.recordHistoricalState(1, statusBarStateControllerImpl.mState, true);
        statusBarStateControllerImpl.updateUpcomingState(1);
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
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

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void startBouncerPreHideAnimation() {
        KeyguardQsUserSwitchController keyguardQsUserSwitchController = this.mKeyguardQsUserSwitchController;
        if (keyguardQsUserSwitchController != null) {
            int i = this.mBarState;
            keyguardQsUserSwitchController.mKeyguardVisibilityHelper.setViewVisibility(i, i, true, false);
        }
        KeyguardUserSwitcherController keyguardUserSwitcherController = this.mKeyguardUserSwitcherController;
        if (keyguardUserSwitcherController != null) {
            int i2 = this.mBarState;
            keyguardUserSwitcherController.mKeyguardVisibilityHelper.setViewVisibility(i2, i2, true, false);
        }
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void startExpandLatencyTracking() {
        if (this.mLatencyTracker.isEnabled()) {
            this.mLatencyTracker.onActionStart(0);
            this.mExpandLatencyTracking = true;
        }
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void startInputFocusTransfer() {
        SecPanelSplitHelper secPanelSplitHelper;
        MotionEvent motionEvent;
        Log.d("NotificationPanelView", "startInputFocusTransfer");
        if (this.mCommandQueue.panelsEnabled() && isFullyCollapsed()) {
            this.mExpectingSynthesizedDown = true;
            onTrackingStarted();
            updatePanelExpanded();
            if (!SecPanelSplitHelper.isEnabled() || (secPanelSplitHelper = this.mPanelSplitHelper) == null || (motionEvent = this.mDownEventFromOverView) == null) {
                return;
            }
            secPanelSplitHelper.shouldQSDown(motionEvent);
        }
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent) {
        Log.d("NotificationPanelView", "startPendingIntentDismissingKeyguard");
        ActivityStarter activityStarter = this.mActivityStarter;
        if (activityStarter != null) {
            activityStarter.startPendingIntentDismissingKeyguard(pendingIntent);
        }
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void transitionToExpandedShade(long j, boolean z) {
        if (!NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY) {
            if (SecPanelSplitHelper.isEnabled()) {
                SecPanelSplitHelper secPanelSplitHelper = this.mPanelSplitHelper;
                if (secPanelSplitHelper != null && secPanelSplitHelper.isShadeState()) {
                    updateEntrySetRead();
                }
            } else {
                updateEntrySetRead();
            }
        }
        if (!z) {
            NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScrollLayoutController.mView;
            notificationStackScrollLayout.mGoToFullShadeNeedsAnimation = true;
            notificationStackScrollLayout.mGoToFullShadeDelay = j;
            notificationStackScrollLayout.mNeedsAnimation = true;
            notificationStackScrollLayout.requestChildrenUpdate();
        }
        this.mView.requestLayout();
        this.mAnimateNextPositionUpdate = true;
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void unregisterAODDoubleTouchListener() {
        this.mAODDoubleTouchListener = null;
    }

    public final void updateClock$1() {
        KeyguardUserSwitcherController keyguardUserSwitcherController;
        if (this.mIsOcclusionTransitionRunning || this.mSecAffordanceHelper.isShortcutPreviewSwipingInProgress) {
            return;
        }
        KeyguardClockPositionAlgorithm.Result result = this.mClockPositionResult;
        float f = result.clockAlpha * this.mKeyguardOnlyContentAlpha;
        LockscreenShadeTransitionController lockscreenShadeTransitionController = this.mLockscreenShadeTransitionController;
        if (lockscreenShadeTransitionController.getFractionToShade() > 0.0f) {
            KeyguardStatusViewController keyguardStatusViewController = this.mKeyguardStatusViewController;
            float fractionToShade = lockscreenShadeTransitionController.getFractionToShade();
            keyguardStatusViewController.setAlpha(NotificationUtils.interpolate(1.0f, 0.0f, ((double) fractionToShade) > 0.5d ? 1.0f : fractionToShade * 2.0f));
        } else if (!this.mKeyguardTouchAnimator.isViRunning()) {
            float f2 = result.clockAlpha;
            if (f2 >= 0.0f) {
                this.mKeyguardStatusViewController.setAlpha(f2);
            }
        }
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        KeyguardQsUserSwitchController keyguardQsUserSwitchController = this.mKeyguardQsUserSwitchController;
        if (keyguardQsUserSwitchController != null) {
            keyguardQsUserSwitchController.setAlpha(f);
        }
        KeyguardUserSwitcherController keyguardUserSwitcherController2 = this.mKeyguardUserSwitcherController;
        if (keyguardUserSwitcherController2 != null) {
            keyguardUserSwitcherController2.setAlpha(f);
        }
        if (LsRune.LOCKUI_MULTI_USER || (keyguardUserSwitcherController = this.mKeyguardUserSwitcherController) == null) {
            return;
        }
        keyguardUserSwitcherController.setAlpha(f);
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x01ab  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01e4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateClockAppearance() {
        /*
            Method dump skipped, instructions count: 584
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.NotificationPanelViewController.updateClockAppearance():void");
    }

    public final void updateDozingVisibilities(boolean z) {
        KeyguardBottomAreaRefactor keyguardBottomAreaRefactor = KeyguardBottomAreaRefactor.INSTANCE;
        Flags.keyguardBottomAreaRefactor();
        this.mKeyguardBottomArea.setVisibility(0);
        if (!this.mDozing && z) {
            this.mKeyguardStatusBarViewController.animateKeyguardStatusBarIn();
        }
        if (CscRune.KEYGUARD_DCM_LIVE_UX) {
            this.mMascotViewContainer.setMascotViewVisible(this.mDozing ? 8 : 0);
        }
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final void updateDynamicLockData(String str) {
        this.mKeyguardBottomAreaViewController.updateBottomView();
    }

    public final void updateEntrySetRead() {
        int i = 0;
        while (true) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
            if (i >= notificationStackScrollLayoutController.mView.getChildCount()) {
                break;
            }
            ExpandableView expandableView = (ExpandableView) notificationStackScrollLayoutController.mView.getChildAt(i);
            if (expandableView instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
                if (!expandableNotificationRow.mEntry.mIsReaded) {
                    ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder(), expandableNotificationRow.mLoggingKey, " isReaded = true", "NotificationPanelView");
                    expandableNotificationRow.mEntry.mIsReaded = true;
                }
            }
            i++;
        }
        NewNotifReadListener newNotifReadListener = this.mNewNotifReadListener;
        if (newNotifReadListener != null) {
            newNotifReadListener.onNewNotificationRead();
        }
    }

    public final void updateExpandedHeight(float f) {
        int height;
        int i;
        boolean isTracking = isTracking();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        if (isTracking) {
            this.mVelocityTracker.computeCurrentVelocity(1000);
            notificationStackScrollLayoutController.mView.mAmbientState.mExpandingVelocity = this.mVelocityTracker.getYVelocity() * (this.mIsTrackpadReverseScroll ? -1 : 1);
        }
        if (this.mKeyguardBypassController.getBypassEnabled() && isKeyguardShowing$2()) {
            f = getMaxPanelHeight();
        }
        if (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE) {
            int i2 = SceneContainerFlag.$r8$clinit;
            Flags.sceneContainer();
            notificationStackScrollLayoutController.getClass();
            SceneContainerFlag.assertInLegacyMode();
            notificationStackScrollLayoutController.mView.setExpandedHeight(f);
        } else if (!this.mKeyguardTouchAnimator.isViRunning()) {
            notificationStackScrollLayoutController.getClass();
            SceneContainerFlag.assertInLegacyMode();
            notificationStackScrollLayoutController.mView.setExpandedHeight(f);
        }
        updateKeyguardBottomAreaAlpha();
        float f2 = this.mExpandedHeight;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.getClass();
        if (NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW || notificationStackScrollLayout.mEmptyShadeView.getVisibility() == 8) {
            height = notificationStackScrollLayout.mShelf.getHeight();
            i = notificationStackScrollLayout.mWaterfallTopInset;
        } else {
            height = notificationStackScrollLayout.mShelf.getHeight();
            i = notificationStackScrollLayout.mWaterfallTopInset;
        }
        boolean z = f2 < ((float) (height + i));
        if (this.mBarState == 0 && this.mExpandedFraction > 0.0f) {
            z = false;
        }
        if (z && isKeyguardShowing$2()) {
            z = false;
        }
        if (z && this.mBarState == 2) {
            z = false;
        }
        if (z != this.mShowIconsWhenExpanded) {
            this.mShowIconsWhenExpanded = z;
            this.mCommandQueue.recomputeDisableFlags(this.mDisplayId, false);
        }
    }

    public final void updateExpandedHeightToMaxHeight() {
        float maxPanelHeight = getMaxPanelHeight();
        if (isFullyCollapsed() || this.mTouchDownOnHeadsUpPinnded || maxPanelHeight == this.mExpandedHeight) {
            return;
        }
        if ((isTracking() || this.mHeadsUpTouchHelper.mTrackingHeadsUp) && !this.mBlockingExpansionForCurrentTouch) {
            QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
            if (!quickSettingsControllerImpl.mConflictingExpansionGesture || !quickSettingsControllerImpl.getExpanded$1()) {
                return;
            }
        }
        if (this.mHeightAnimator == null || this.mIsSpringBackAnimation) {
            setExpandedHeight(maxPanelHeight);
        } else {
            this.mPanelUpdateWhenAnimatorEnds = true;
        }
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void updateExpansionAndVisibility() {
        boolean z;
        boolean z2;
        StringBuilder sb;
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        float f = this.mExpandedFraction;
        boolean isExpanded = isExpanded();
        boolean isTracking = isTracking();
        ShadeExpansionStateManager shadeExpansionStateManager = this.mShadeExpansionStateManager;
        shadeExpansionStateManager.getClass();
        if (!(!Float.isNaN(f))) {
            throw new IllegalArgumentException("fraction cannot be NaN".toString());
        }
        int i2 = shadeExpansionStateManager.state;
        shadeExpansionStateManager.fraction = f;
        shadeExpansionStateManager.expanded = isExpanded;
        shadeExpansionStateManager.tracking = isTracking;
        if (isExpanded) {
            if (i2 == 0) {
                shadeExpansionStateManager.updateStateInternal(1);
            }
            z = f >= 1.0f;
            z2 = false;
        } else {
            z = false;
            z2 = true;
        }
        if (z && !isTracking) {
            shadeExpansionStateManager.updateStateInternal(2);
        } else if (z2 && !isTracking && shadeExpansionStateManager.state != 0) {
            shadeExpansionStateManager.updateStateInternal(0);
        }
        ShadeExpansionStateManagerKt.panelStateToString(i2);
        ShadeExpansionStateManagerKt.panelStateToString(shadeExpansionStateManager.state);
        if (i2 != shadeExpansionStateManager.state) {
            MWBixbyController$$ExternalSyntheticOutline0.m("onPanelExpansionChanged: ", ShadeExpansionStateManagerKt.panelStateToString(i2), " -> ", ShadeExpansionStateManagerKt.panelStateToString(shadeExpansionStateManager.state), ShadeExpansionStateManagerKt.TAG);
        }
        if (Trace.isTagEnabled(4096L)) {
            Trace.traceCounter(4096L, "panel_expansion", (int) (100 * f));
            if (shadeExpansionStateManager.state != i2) {
                Trace.asyncTraceForTrackEnd(4096L, "ShadeExpansionState", 0);
                Trace.asyncTraceForTrackBegin(4096L, "ShadeExpansionState", ShadeExpansionStateManagerKt.panelStateToString(shadeExpansionStateManager.state), 0);
            }
        }
        ShadeExpansionChangeEvent shadeExpansionChangeEvent = new ShadeExpansionChangeEvent(f, isExpanded, isTracking);
        ShadeExpansionChangeEvent shadeExpansionChangeEvent2 = ((shadeExpansionStateManager.oldFraction > f ? 1 : (shadeExpansionStateManager.oldFraction == f ? 0 : -1)) == 0) ^ true ? shadeExpansionChangeEvent : null;
        if (shadeExpansionChangeEvent2 != null) {
            shadeExpansionStateManager.oldFraction = f;
            Log.d(ShadeExpansionStateManagerKt.TAG, "onPanelExpansionChanged: " + shadeExpansionChangeEvent2);
        }
        Iterator it = shadeExpansionStateManager.expansionListeners.iterator();
        while (it.hasNext()) {
            ((ShadeExpansionListener) it.next()).onPanelExpansionChanged(shadeExpansionChangeEvent);
        }
        updateVisibility$6();
        QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
        if (quickPanelLogger == null || (sb = this.mQuickPanelLogBuilder) == null || !isExpanded()) {
            return;
        }
        if (Float.compare(this.mExpandedFraction, 0.0f) == 0 || Float.compare(this.mExpandedFraction, 1.0f) == 0) {
            sb.setLength(0);
            sb.append("updateExpansionAndVisibility: ");
            sb.append("mExpandedFraction: ");
            sb.append(this.mExpandedFraction);
            sb.append(", mInstantExpanding: ");
            sb.append(this.mInstantExpanding);
            sb.append(", isPanelVisibleBecauseOfHeadsUp: ");
            sb.append(isPanelVisibleBecauseOfHeadsUp());
            sb.append(", isTracking(): ");
            sb.append(isTracking());
            sb.append(", mIsSpringBackAnimation: ");
            sb.append(this.mIsSpringBackAnimation);
            sb.append(", isExpanded: ");
            sb.append(isExpanded());
            sb.append(", isFullyExpanded: ");
            sb.append(isFullyExpanded());
            sb.append(", isFullyCollapsed: ");
            sb.append(isFullyCollapsed());
            sb.append(", mHeightAnimator is null: ");
            sb.append(this.mHeightAnimator == null);
            quickPanelLogger.logPanelState(sb.toString());
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
        if (this.mKeyguardBottomArea == null || this.mSecAffordanceHelper.isShortcutPreviewSwipingInProgress || this.mKeyguardStateController.mKeyguardGoingAway || this.mBarState != 1) {
            return;
        }
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        if (this.mKeyguardTouchAnimator.isViRunning() || this.mIsOcclusionTransitionRunning) {
            return;
        }
        float min = Math.min(MathUtils.constrainedMap(0.0f, 1.0f, 0.95f, 1.0f, this.mExpandedFraction), 1.0f - this.mLockscreenShadeTransitionController.getFractionToShade()) * this.mBottomAreaShadeAlpha;
        KeyguardSecAffordanceHelper keyguardSecAffordanceHelper = this.mSecAffordanceHelper;
        if (!keyguardSecAffordanceHelper.mPreviewAnimationStarted) {
            KeyguardSecAffordanceView keyguardSecAffordanceView = keyguardSecAffordanceHelper.mLeftIcon;
            Intrinsics.checkNotNull(keyguardSecAffordanceView);
            if (keyguardSecAffordanceView.mShortcutLaunchAnimator == null) {
                KeyguardSecAffordanceView keyguardSecAffordanceView2 = keyguardSecAffordanceHelper.mRightIcon;
                Intrinsics.checkNotNull(keyguardSecAffordanceView2);
                if (keyguardSecAffordanceView2.mShortcutLaunchAnimator == null) {
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
        }
        float faceWidgetAlpha = getFaceWidgetAlpha();
        if (faceWidgetAlpha < 0.0f) {
            return;
        }
        View view = this.mNowBarContainer;
        if (view != null) {
            view.setAlpha(faceWidgetAlpha);
        }
        KeyguardBottomAreaRefactor keyguardBottomAreaRefactor = KeyguardBottomAreaRefactor.INSTANCE;
        Flags.keyguardBottomAreaRefactor();
        this.mKeyguardBottomAreaViewController.setAffordanceAlpha(faceWidgetAlpha);
        ((KeyguardRepositoryImpl) this.mKeyguardBottomAreaInteractor.repository)._bottomAreaAlpha.updateState(null, Float.valueOf(faceWidgetAlpha));
        this.mLockIconViewController.mView.setAlpha(faceWidgetAlpha);
        View view2 = this.mPluginLockStarContainer;
        if (view2 != null) {
            view2.setAlpha(faceWidgetAlpha);
        }
        if (!LsRune.LOCKUI_MULTI_USER || (keyguardUserSwitcherController = this.mKeyguardUserSwitcherController) == null) {
            return;
        }
        keyguardUserSwitcherController.setAlpha(faceWidgetAlpha);
    }

    public final void updateKeyguardStatusViewAlignment(boolean z) {
        final boolean z2 = true;
        this.mKeyguardUnfoldTransition.ifPresent(new Consumer() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((KeyguardUnfoldTransition) obj).statusViewCentered = z2;
            }
        });
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        this.mKeyguardStatusViewController.updateAlignment(this.mNotificationContainerParent, false, true, z);
    }

    public final void updateLockStarContainer() {
        KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(new StringBuilder("updateLockStarContainer: "), this.mLockStarEnabled, "NotificationPanelView");
        if (this.mLockStarEnabled) {
            this.mPluginLockStarContainer = (View) ((PluginLockStarManager) Dependency.sDependency.getDependencyInner(PluginLockStarManager.class)).get("lockstarContainer");
            Log.i("NotificationPanelView", "updateLockStarContainer: [" + this.mPluginLockStarContainer + "]");
            return;
        }
        View view = this.mPluginLockStarContainer;
        if (view != null) {
            view.setVisibility(8);
            this.mPluginLockStarContainer = null;
        }
    }

    public final void updateMaxDisplayedNotifications(boolean z) {
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
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
        boolean isKeyguardShowing$2 = isKeyguardShowing$2();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        if (!isKeyguardShowing$2 || this.mAmbientState.mDragDownOnKeyguard || this.mKeyguardBypassController.getBypassEnabled()) {
            notificationStackScrollLayoutController.setMaxDisplayedNotifications(-1);
            notificationStackScrollLayoutController.mView.getClass();
        } else {
            notificationStackScrollLayoutController.setMaxDisplayedNotifications(this.mMaxAllowedKeyguardNotifications);
            notificationStackScrollLayoutController.mView.getClass();
        }
    }

    public final void updateMaxHeadsUpTranslation() {
        int height = this.mView.getHeight();
        int i = this.mNavigationBarBottomHeight;
        NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScrollLayoutController.mView;
        AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
        ambientState.mMaxHeadsUpTranslation = height - i;
        notificationStackScrollLayout.mStackScrollAlgorithm.mHeadsUpAppearHeightBottom = height;
        StackStateAnimator stackStateAnimator = notificationStackScrollLayout.mStateAnimator;
        stackStateAnimator.mHeadsUpAppearHeightBottom = height;
        stackStateAnimator.mStackTopMargin = ambientState.mStackTopMargin;
        notificationStackScrollLayout.requestChildrenUpdate();
    }

    public final void updateNotificationTranslucency() {
        if (this.mSecAffordanceHelper.isShortcutPreviewSwipingInProgress || this.mKeyguardTouchAnimator.isViRunning()) {
            return;
        }
        KeyguardEditModeController keyguardEditModeController = this.mKeyguardEditModeController;
        if ((keyguardEditModeController != null && ((KeyguardEditModeControllerImpl) keyguardEditModeController).getVIRunning()) || this.mIsOcclusionTransitionRunning || this.mQsController.getExpanded$1()) {
            return;
        }
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
    }

    public final void updatePanelExpanded() {
        boolean z = !isFullyCollapsed() || this.mExpectingSynthesizedDown;
        if (isPanelExpanded() != z) {
            SecNotificationPanelViewController secNotificationPanelViewController = this.mSecNotificationPanelViewController;
            if (secNotificationPanelViewController != null) {
                secNotificationPanelViewController.shadeHeaderController.panelExpanded = z;
            }
            ((ShadeRepositoryImpl) this.mShadeRepository)._legacyExpandedOrAwaitingInputTransfer.updateState(null, Boolean.valueOf(z));
            updateSystemUiStateFlags();
            if (!z) {
                this.mQsController.closeQsCustomizer();
            }
        }
        if (isKeyguardShowing$2()) {
            return;
        }
        KeyguardTouchAnimator keyguardTouchAnimator = this.mKeyguardTouchAnimator;
        if (keyguardTouchAnimator.notiScale > 1.0f) {
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m(new StringBuilder("current noti scale : "), keyguardTouchAnimator.notiScale, "NotificationPanelView");
        }
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void updateResources$1() {
        Trace.beginSection("NSSLC#updateResources");
        ((SplitShadeStateControllerImpl) this.mSplitShadeStateController).shouldUseSplitNotificationShade();
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        Resources resources = quickSettingsControllerImpl.mResources;
        ((SplitShadeStateControllerImpl) quickSettingsControllerImpl.mSplitShadeStateController).shouldUseSplitNotificationShade();
        QS qs = quickSettingsControllerImpl.mQs;
        if (qs != null) {
            qs.setInSplitShade(false);
        }
        quickSettingsControllerImpl.mSplitShadeNotificationsScrimMarginBottom = quickSettingsControllerImpl.mResources.getDimensionPixelSize(R.dimen.split_shade_notifications_scrim_margin_bottom);
        NotificationPanelView notificationPanelView = quickSettingsControllerImpl.mPanelView;
        boolean shouldUseLargeScreenShadeHeader = LargeScreenUtils.shouldUseLargeScreenShadeHeader(notificationPanelView.getResources());
        quickSettingsControllerImpl.mUseLargeScreenShadeHeader = shouldUseLargeScreenShadeHeader;
        int dimensionPixelSize = shouldUseLargeScreenShadeHeader ? 0 : quickSettingsControllerImpl.mResources.getDimensionPixelSize(R.dimen.notification_panel_margin_top);
        boolean z = quickSettingsControllerImpl.mUseLargeScreenShadeHeader;
        ShadeHeaderController shadeHeaderController = quickSettingsControllerImpl.mShadeHeaderController;
        if (shadeHeaderController.largeScreenActive != z) {
            shadeHeaderController.largeScreenActive = z;
            shadeHeaderController.updateTransition();
        }
        quickSettingsControllerImpl.mAmbientState.mStackTopMargin = dimensionPixelSize;
        quickSettingsControllerImpl.mQuickQsHeaderHeight = 0;
        quickSettingsControllerImpl.mEnableClipping = quickSettingsControllerImpl.mResources.getBoolean(R.bool.qs_enable_clipping);
        WindowMetrics currentWindowMetrics = ((WindowManager) quickSettingsControllerImpl.mPanelView.getContext().getSystemService(WindowManager.class)).getCurrentWindowMetrics();
        quickSettingsControllerImpl.mCachedGestureInsets = currentWindowMetrics.getWindowInsets().getInsets(WindowInsets.Type.systemGestures());
        quickSettingsControllerImpl.mCachedWindowWidth = currentWindowMetrics.getBounds().width();
        quickSettingsControllerImpl.mQuickQsOffsetHeight = SystemBarUtils.getQuickQsOffsetHeight(notificationPanelView.getContext());
        this.mNotificationsQSContainerController.updateResources$1();
        updateKeyguardStatusViewAlignment(false);
        this.mKeyguardMediaController.getClass();
        this.mSplitShadeFullTransitionDistance = this.mResources.getDimensionPixelSize(R.dimen.split_shade_full_transition_distance);
        Trace.endSection();
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void updateSystemUiStateFlags() {
        boolean z = false;
        boolean z2 = isPanelExpanded() && !isCollapsing();
        SysUiState sysUiState = this.mSysUiState;
        sysUiState.setFlag(1073741824L, z2);
        boolean isFullyExpanded = isFullyExpanded();
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        sysUiState.setFlag(4L, isFullyExpanded && !quickSettingsControllerImpl.getExpanded$1());
        if (isFullyExpanded() && quickSettingsControllerImpl.getExpanded$1()) {
            z = true;
        }
        sysUiState.setFlag(2048L, z);
        sysUiState.commitUpdate(this.mDisplayId);
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void updateTouchableRegion() {
        NotificationPanelView notificationPanelView = this.mView;
        notificationPanelView.requestLayout();
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController;
        NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
        notificationShadeWindowState.forceWindowCollapsed = true;
        notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
        notificationPanelView.post(new NotificationPanelViewController$$ExternalSyntheticLambda31(this, 9));
    }

    public final void updateUserSwitcherFlags() {
        boolean z = false;
        boolean z2 = this.mResources.getBoolean(android.R.bool.config_lidControlsScreenLock) && LsRune.LOCKUI_MULTI_USER;
        this.mKeyguardUserSwitcherEnabled = z2;
        if (z2) {
            if (((FeatureFlagsClassicRelease) this.mFeatureFlags).isEnabled(com.android.systemui.flags.Flags.QS_USER_DETAIL_SHORTCUT)) {
                z = true;
            }
        }
        this.mKeyguardQsUserSwitchEnabled = z;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0096  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateVisibility$6() {
        /*
            r7 = this;
            com.android.systemui.statusbar.notification.shared.NotificationsHeadsUpRefactor r0 = com.android.systemui.statusbar.notification.shared.NotificationsHeadsUpRefactor.INSTANCE
            com.android.systemui.Flags.notificationsHeadsUpRefactor()
            boolean r0 = r7.mHeadsUpAnimatingAway
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L12
            boolean r0 = r7.mHeadsUpPinnedMode
            if (r0 == 0) goto L10
            goto L12
        L10:
            r0 = r1
            goto L13
        L12:
            r0 = r2
        L13:
            int r3 = r7.mPanelInVisibleReason
            r4 = -1
            r7.mPanelInVisibleReason = r4
            if (r0 != 0) goto L68
            r7.mPanelInVisibleReason = r4
            boolean r4 = r7.isExpanded()
            if (r4 != 0) goto L24
            r7.mPanelInVisibleReason = r1
        L24:
            boolean r5 = r7.mBouncerShowing
            if (r5 == 0) goto L2b
            r7.mPanelInVisibleReason = r2
            r4 = r1
        L2b:
            boolean r5 = com.android.systemui.LsRune.KEYGUARD_SUB_DISPLAY_LOCK
            if (r5 == 0) goto L47
            if (r4 == 0) goto L47
            com.android.systemui.Dependency r5 = com.android.systemui.Dependency.sDependency
            java.lang.Class<com.android.systemui.keyguard.KeyguardFoldController> r6 = com.android.systemui.keyguard.KeyguardFoldController.class
            java.lang.Object r5 = r5.getDependencyInner(r6)
            com.android.systemui.keyguard.KeyguardFoldController r5 = (com.android.systemui.keyguard.KeyguardFoldController) r5
            com.android.systemui.keyguard.KeyguardFoldControllerImpl r5 = (com.android.systemui.keyguard.KeyguardFoldControllerImpl) r5
            boolean r5 = r5.isUnlockOnFoldOpened()
            if (r5 == 0) goto L47
            r4 = 2
            r7.mPanelInVisibleReason = r4
            r4 = r1
        L47:
            boolean r5 = com.android.systemui.LsRune.SECURITY_SWIPE_BOUNCER
            if (r5 == 0) goto L63
            if (r4 == 0) goto L63
            com.android.systemui.Dependency r5 = com.android.systemui.Dependency.sDependency
            java.lang.Class<com.android.systemui.statusbar.policy.KeyguardStateController> r6 = com.android.systemui.statusbar.policy.KeyguardStateController.class
            java.lang.Object r5 = r5.getDependencyInner(r6)
            com.android.systemui.statusbar.policy.KeyguardStateController r5 = (com.android.systemui.statusbar.policy.KeyguardStateController) r5
            com.android.systemui.statusbar.policy.KeyguardStateControllerImpl r5 = (com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r5
            boolean r5 = r5.isShownSwipeBouncer()
            if (r5 == 0) goto L63
            r4 = 3
            r7.mPanelInVisibleReason = r4
            r4 = r1
        L63:
            if (r4 == 0) goto L66
            goto L68
        L66:
            r4 = r1
            goto L69
        L68:
            r4 = r2
        L69:
            int r5 = r7.mPanelInVisibleReason
            com.android.systemui.shade.NotificationPanelView r7 = r7.mView
            int r6 = r7.getVisibility()
            if (r6 != 0) goto L74
            goto L75
        L74:
            r2 = r1
        L75:
            if (r2 != r4) goto L7b
            if (r4 != 0) goto L93
            if (r3 == r5) goto L93
        L7b:
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r4)
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r5)
            java.lang.Object[] r0 = new java.lang.Object[]{r2, r0, r3}
            java.lang.String r2 = "KeyguardVisible"
            java.lang.String r3 = "shouldPanelBeVisible %b / headUpVisible=%b, why=%d"
            com.android.systemui.keyguard.Log.d(r2, r3, r0)
        L93:
            if (r4 == 0) goto L96
            goto L97
        L96:
            r1 = 4
        L97:
            r7.setVisibility(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.NotificationPanelViewController.updateVisibility$6():void");
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void resetViews(boolean z, boolean z2) {
        this.mIsLaunchTransitionFinished = false;
        if (!this.mLaunchingAffordance) {
            this.mSecAffordanceHelper.reset(false);
            this.mLastCameraLaunchSource = 3;
        }
        KeyguardSecAffordanceHelper keyguardSecAffordanceHelper = this.mSecAffordanceHelper;
        if (keyguardSecAffordanceHelper.isShortcutPreviewSwipingInProgress) {
            keyguardSecAffordanceHelper.reset(false);
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
        keyguardTouchAnimator.reset(z2);
        this.mHintAnimationRunning = false;
        this.mSecAffordanceHelper.isShortcutPreviewSwipingInProgress = false;
    }

    public final void collapse(float f, boolean z) {
        if (canBeCollapsed()) {
            QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
            if (quickSettingsControllerImpl.getExpanded$1()) {
                quickSettingsControllerImpl.setExpandImmediate(true);
                setShowShelfOnly(true);
            }
            if (canBeCollapsed()) {
                cancelHeightAnimator();
                notifyExpandingStarted();
                setClosing(true);
                this.mUpdateFlingOnLayout = false;
                if (z) {
                    this.mNextCollapseSpeedUpFactor = f;
                    this.mView.postDelayed(this.mFlingCollapseRunnable, 120L);
                } else {
                    fling(0.0f, f, false);
                }
            }
        }
    }

    public final void fling(float f, float f2, boolean z) {
        float maxPanelTransitionDistance = z ? getMaxPanelTransitionDistance() : 0.0f;
        if (!z) {
            setClosing(true);
        }
        flingToHeight(f, z, maxPanelTransitionDistance, f2, false);
    }
}
