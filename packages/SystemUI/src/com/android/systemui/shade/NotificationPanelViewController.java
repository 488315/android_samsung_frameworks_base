package com.android.systemui.shade;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.PendingIntent;
import android.app.SemWallpaperColors;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.Region;
import android.metrics.LogMaker;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.Trace;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.MathUtils;
import android.util.Property;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.MotionEvent;
import android.view.SurfaceControl;
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
import android.widget.ImageView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.cardview.widget.CardView;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.app.tracing.coroutines.TrackTracer;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.internal.policy.SystemBarUtils;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.LatencyTracker;
import com.android.keyguard.ActiveUnlockConfig;
import com.android.keyguard.EmergencyButton;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardContinuityLockscreenAffordanceArea;
import com.android.keyguard.KeyguardContinuityLockscreenAffordanceController;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecVisibilityHelper;
import com.android.keyguard.KeyguardUnfoldTransition;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.SecLockIconViewController;
import com.android.keyguard.biometrics.KeyguardFingerprintGuidePopup;
import com.android.keyguard.dagger.KeyguardStatusBarViewComponent;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.keyguard.punchhole.KeyguardPunchHoleVIView;
import com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.CscRune;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.android.systemui.blur.SecQSBlurShadowView;
import com.android.systemui.blur.SecQSNewBlurView;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.common.buffer.RingBuffer;
import com.android.systemui.common.buffer.RingBuffer.AnonymousClass1;
import com.android.systemui.common.domain.interactor.SysUIStateDisplaysInteractor;
import com.android.systemui.communal.ui.viewmodel.CommunalTransitionViewModel;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl;
import com.android.systemui.doze.DozeLog;
import com.android.systemui.doze.DozeLogger;
import com.android.systemui.doze.DozeLogger$$ExternalSyntheticLambda0;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.dump.DumpsysTableLogger;
import com.android.systemui.facewidget.FaceWidgetNotificationController;
import com.android.systemui.facewidget.plugin.FaceWidgetContainerWrapper;
import com.android.systemui.facewidget.plugin.FaceWidgetKeyguardStatusCallbackWrapper;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.facewidget.plugin.FaceWidgetPositionAlgorithmWrapper;
import com.android.systemui.facewidget.plugin.KeyguardStatusViewAlphaChangeControllerWrapper;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.keyguard.KeyguardClickController;
import com.android.systemui.keyguard.KeyguardClickControllerImpl;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl$$ExternalSyntheticLambda2;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.animator.KeyguardEditModeAnimatorController;
import com.android.systemui.keyguard.animator.KeyguardTouchAnimator;
import com.android.systemui.keyguard.animator.KeyguardTouchSwipeCallback;
import com.android.systemui.keyguard.data.repository.KeyguardClockRepositoryImpl;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.ClockSize;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.BlurConfig;
import com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardTouchHandlingViewModel;
import com.android.systemui.keyguardimage.WallpaperImageInjectCreator;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
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
import com.android.systemui.model.StateChange;
import com.android.systemui.model.SysUiState;
import com.android.systemui.model.SysUiStateImpl;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.gestural.Utilities;
import com.android.systemui.navigationbar.views.NavigationBarView;
import com.android.systemui.pluginlock.PluginLockData;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.pluginlock.model.DynamicLockData;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import com.android.systemui.plugins.keyguardstatusview.PluginNotificationController;
import com.android.systemui.plugins.keyguardstatusview.PluginSecKeyguardClockPositionAlgorithm;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.shared.model.WakeSleepReason;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.qs.NonInterceptingScrollView;
import com.android.systemui.qs.QSFragmentLegacy;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.SecQSImpl;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.TileChunkLayoutBarExpandHelper;
import com.android.systemui.qs.animator.QsAnimatorState;
import com.android.systemui.qs.animator.SecQSImplAnimatorManager;
import com.android.systemui.qs.bar.TileChunkLayoutBar;
import com.android.systemui.qs.flags.QSComposeFragment;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker;
import com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticOutline0;
import com.android.systemui.samsung.quicksetting.SecQSPanelComposeAdapter;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.settings.brightness.data.repository.BrightnessMirrorShowingRepository;
import com.android.systemui.settings.brightness.domain.interactor.BrightnessMirrorShowingInteractor;
import com.android.systemui.shade.NPVCDownEventState;
import com.android.systemui.shade.PanelSlideEventHandler;
import com.android.systemui.shade.QuickSettingsControllerImpl;
import com.android.systemui.shade.QuickSettingsControllerImpl.QsFragmentListener;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.shade.data.repository.FlingInfo;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepository;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.shade.domain.interactor.SecHideNotificationShadeInMirrorInteractor;
import com.android.systemui.shade.domain.interactor.SecHideNotificationShadeInMirrorInteractorImpl;
import com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor;
import com.android.systemui.shade.domain.interactor.SecQuickSettingsAffordanceInteractor;
import com.android.systemui.shade.domain.interactor.ShadeAnimationInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.DragDownHelper;
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
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.ConversationNotificationManager$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.notification.ConversationNotificationManager$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.notification.ConversationNotificationManager$sam$java_util_function_BiFunction$0;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator$setStackScroller$1;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.ViewGroupFadeHelper;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.emptyshade.ui.view.EmptyShadeView;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.headsup.HeadsUpTouchHelper;
import com.android.systemui.statusbar.notification.headsup.NotificationsHunSharedAnimationValues;
import com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm;
import com.android.systemui.statusbar.notification.stack.StackStateAnimator;
import com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda30;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.DcmMascotViewContainer;
import com.android.systemui.statusbar.phone.DcmMascotViewContainer$broadcastReceiver$1;
import com.android.systemui.statusbar.phone.DcmMascotViewContainer$sendUnreadCountBroadcast$1;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.statusbar.phone.IndicatorTouchHandler;
import com.android.systemui.statusbar.phone.IndicatorTouchHandler$doubleTapTimeoutRunnable$1;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm;
import com.android.systemui.statusbar.phone.KeyguardIndicationTextView;
import com.android.systemui.statusbar.phone.KeyguardSecAffordanceHelper;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController;
import com.android.systemui.statusbar.phone.KeyguardStatusBarView;
import com.android.systemui.statusbar.phone.KeyguardStatusBarViewController;
import com.android.systemui.statusbar.phone.LockscreenGestureLogger;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.ScrimState;
import com.android.systemui.statusbar.phone.SecLsScrimControlHelper;
import com.android.systemui.statusbar.phone.ShadeTouchableRegionManager;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.TapAgainViewController;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager;
import com.android.systemui.statusbar.phone.datausage.DataUsageLabelParent;
import com.android.systemui.statusbar.phone.datausage.DataUsageLabelView;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.BrightnessMirrorController$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowView;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.QsStatusEventLog;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.Utils;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpapers.ui.viewmodel.WallpaperFocalAreaViewModel;
import com.android.systemui.widget.SystemUIWidgetCallback;
import com.android.wm.shell.animation.FlingAnimationUtils;
import com.google.android.msdl.domain.MSDLPlayer;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.multiwindow.MultiWindowEdgeDetector;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import dagger.Lazy;
import dalvik.annotation.optimization.NeverCompile;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.inject.Provider;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableSet;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.FilteringSequence.AnonymousClass1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class NotificationPanelViewController implements ShadeSurface, Dumpable, BrightnessMirrorShowingInteractor, PluginLockListener.State, PanelScreenShotLogger.LogProvider {
    public View.OnTouchListener mAODDoubleTouchListener;
    public final ShadeAccessibilityDelegate mAccessibilityDelegate;
    public final AccessibilityManager mAccessibilityManager;
    public final ActiveNotificationsInteractor mActiveNotificationsInteractor;
    public final ActivityStarter mActivityStarter;
    public boolean mAllowExpandForSmallExpansion;
    public final AlternateBouncerInteractor mAlternateBouncerInteractor;
    public final AmbientState mAmbientState;
    public boolean mAnimateAfterExpanding;
    public final NotificationPanelViewController$$ExternalSyntheticLambda18 mAnimateKeyguardBottomAreaInvisibleEndRunnable;
    public boolean mAnimateNextPositionUpdate;
    public boolean mAnimatingOnDown;
    public int mBarState;
    public Lazy mBioUnlockControllerLazy;
    public boolean mBlockingExpansionForCurrentTouch;
    public float mBottomAreaShadeAlpha;
    public final ValueAnimator mBottomAreaShadeAlphaAnimator;
    public boolean mBouncerShowing;
    public final BrightnessMirrorShowingRepository mBrightnessMirrorShowingRepository;
    public CentralSurfacesImpl mCentralSurfaces;
    KeyguardClockPositionAlgorithm mClockPositionAlgorithm;
    public boolean mClosingWithAlphaFadeOut;
    public boolean mCollapsedAndHeadsUpOnDown;
    public boolean mCollapsedOnDown;
    public final CommandQueue mCommandQueue;
    public final ConfigurationController mConfigurationController;
    public final ConfigurationListener mConfigurationListener;
    public KeyguardContinuityLockscreenAffordanceArea mContinuityLockScreenContainer;
    public KeyguardContinuityLockscreenAffordanceController mContinuityLockScreenContainerController;
    public final ConversationNotificationManager mConversationNotificationManager;
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
    public final DreamingToLockscreenTransitionViewModel mDreamingToLockscreenTransitionViewModel;
    public View mEditModeContainer;
    public final EmergencyButtonController.Factory mEmergencyButtonControllerFactory;
    public boolean mExpandLatencyTracking;
    public float mExpandedFraction;
    public boolean mExpanding;
    public boolean mExpandingFromHeadsUp;
    public float mExpansionDragDownAmountPx;
    public boolean mExpectingSynthesizedDown;
    public final FalsingCollector mFalsingCollector;
    public final FalsingManager mFalsingManager;
    public int mFixedDuration;
    public FlingAnimationUtils mFlingAnimationUtils;
    public final Provider mFlingAnimationUtilsBuilder;
    public final FlingAnimationUtils mFlingAnimationUtilsClosing;
    public final FlingAnimationUtils mFlingAnimationUtilsDismissing;
    public Animator mFlingAnimator;
    public final NotificationPanelViewController$$ExternalSyntheticLambda18 mFlingCollapseRunnable;
    public final FragmentService mFragmentService;
    public boolean mFullScreenModeEnabled;
    public boolean mGestureWaitForTouchSlop;
    public final NotificationGutsManager mGutsManager;
    public boolean mHandlingPointerUp;
    public boolean mHasLayoutedSinceDown;
    public boolean mHasVibratedOnOpen;
    public boolean mHeadsUpAnimatingAway;
    public HeadsUpAppearanceController mHeadsUpAppearanceController;
    public final NotificationPanelViewController$$ExternalSyntheticLambda18 mHeadsUpExistenceChangedRunnable;
    public int mHeadsUpInset;
    public HeadsUpManager mHeadsUpManager;
    public boolean mHeadsUpPinnedMode;
    public int mHeadsUpStartHeight;
    public HeadsUpTouchHelper mHeadsUpTouchHelper;
    public boolean mHeadsUpVisibleOnDown;
    public ValueAnimator mHeightAnimator;
    public CentralSurfacesImpl$$ExternalSyntheticLambda30 mHideExpandedRunnable;
    public boolean mHintAnimationRunning;
    public float mHintDistance;
    public boolean mIgnoreXTouchSlop;
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
    public boolean mIsLaunchTransitionFinished;
    public boolean mIsLaunchTransitionRunning;
    public boolean mIsOcclusionTransitionRunning;
    public boolean mIsPanelCollapseOnQQS;
    public boolean mIsSpringBackAnimation;
    public final KeyguardAffordanceHelperCallback mKeyguardAffordanceHelperCallback;
    public final KeyguardBypassController mKeyguardBypassController;
    public final KeyguardClockInteractor mKeyguardClockInteractor;
    public final KeyguardEditModeController mKeyguardEditModeController;
    public final KeyguardIndicationController mKeyguardIndicationController;
    public final KeyguardInteractor mKeyguardInteractor;
    public final KeyguardMediaController mKeyguardMediaController;
    public KeyguardPunchHoleVIView mKeyguardPunchHoleVIView;
    public KeyguardSecBottomAreaView mKeyguardSecBottomArea;
    public final KeyguardSecBottomAreaViewController mKeyguardSecBottomAreaViewController;
    public final KeyguardStateControllerImpl mKeyguardStateController;
    public KeyguardStatusBarView mKeyguardStatusBar;
    public final KeyguardStatusBarViewComponent.Factory mKeyguardStatusBarViewComponentFactory;
    public KeyguardStatusBarViewController mKeyguardStatusBarViewController;
    public FaceWidgetContainerWrapper mKeyguardStatusBase;
    public final KeyguardTouchAnimator mKeyguardTouchAnimator;
    public final KeyguardTransitionInteractor mKeyguardTransitionInteractor;
    public final Optional mKeyguardUnfoldTransition;
    public final KeyguardWallpaperController mKeyguardWallpaperController;
    public final NPVCDownEventState.Buffer mLastDownEvents;
    public boolean mLastEventSynthesizedDown;
    public float mLastGesturedOverExpansion;
    public final LatencyTracker mLatencyTracker;
    public float mLinearDarkAmount;
    public boolean mListenForHeadsUp;
    public final SecLockIconViewController mLockIconViewController;
    public boolean mLockStarEnabled;
    public final LockscreenGestureLogger mLockscreenGestureLogger;
    public final LockscreenNotificationIconsOnlyController mLockscreenNotificationIconsOnlyController;
    public final LockscreenNotificationManager mLockscreenNotificationManager;
    public final LockscreenShadeTransitionController mLockscreenShadeTransitionController;
    public final AnonymousClass11 mLockscreenShadeTransitonCallback;
    public final CoroutineDispatcher mMainDispatcher;
    public final DcmMascotViewContainer mMascotViewContainer;
    public int mMaxOverscrollAmountForPulse;
    public final NotificationPanelViewController$$ExternalSyntheticLambda18 mMaybeHideExpandedRunnable;
    public final MediaDataManager mMediaDataManager;
    public final MediaHierarchyManager mMediaHierarchyManager;
    public int mMediaNowBarExpandState;
    public boolean mMediaOutputDetailShowing;
    public final MetricsLogger mMetricsLogger;
    public float mMinFraction;
    public boolean mMotionAborted;
    public final MultiWindowEdgeDetector mMultiWindowEdgeDetector;
    public int mNavigationBarBottomHeight;
    public final NavigationBarController mNavigationBarController;
    public NewNotifReadListener mNewNotifReadListener;
    public float mNextCollapseSpeedUpFactor;
    public NotificationsQuickSettingsContainer mNotificationContainerParent;
    public final NotificationListContainer mNotificationListContainer;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final NotificationStackScrollLayoutController mNotificationStackScrollLayoutController;
    public final boolean mNotificationsDragEnabled;
    public final NotificationsQSContainerController mNotificationsQSContainerController;
    public View mNowBarContainer;
    public int mOldLayoutDirection;
    public final ShadeHeadsUpChangedListener mOnHeadsUpChangedListener;
    public boolean mOnlyAffordanceInThisMotion;
    public ShadeControllerImpl.AnonymousClass2 mOpenCloseListener;
    public float mOverExpansion;
    public float mOverStretchAmount;
    public final PanelAgent mPanelAgent;
    public int mPanelAlpha;
    public final AnimatableProperty.AnonymousClass6 mPanelAlphaAnimator;
    public BrightnessMirrorController$$ExternalSyntheticLambda0 mPanelAlphaEndAction;
    public final AnimationProperties mPanelAlphaInPropertiesAnimator;
    public final AnimationProperties mPanelAlphaOutPropertiesAnimator;
    public boolean mPanelClosedOnDown;
    public boolean mPanelExpandForBiometric;
    public float mPanelFlingOvershootAmount;
    public int mPanelInVisibleReason;
    public final PanelPopOverManager mPanelPopOverManager;
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
    public final NotificationPanelViewController$$ExternalSyntheticLambda18 mPostCollapseRunnable;
    public final PowerInteractor mPowerInteractor;
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
    public final Resources mResources;
    public final Lazy mSamsungBarExt;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public final ScrimController mScrimController;
    public KeyguardSecAffordanceHelper mSecAffordanceHelper;
    public final SecNotificationPanelViewController mSecNotificationPanelViewController;
    public final SecQsUiDisplayModeInteractor mSecQsUiDisplayModeInteractor;
    public final SecQuickSettingsAffordanceInteractor mSecQuickSettingsAffordanceInteractor;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final ShadeAnimationInteractor mShadeAnimationInteractor;
    public final Lazy mShadeDisplaysRepository;
    public final ShadeExpansionStateManager mShadeExpansionStateManager;
    public final ShadeHeaderController mShadeHeaderController;
    public final ShadeHeadsUpTrackerImpl mShadeHeadsUpTracker;
    public final ShadeLogger mShadeLog;
    public final ShadeRepository mShadeRepository;
    public final ShadeTouchableRegionManager mShadeTouchableRegionManager;
    public final AnonymousClass17 mShadeViewStateProvider;
    public final SharedNotificationContainerInteractor mSharedNotificationContainerInteractor;
    public final NotificationShelfManager mShelfManager;
    public int mShortcut;
    public boolean mShowIconsWhenExpanded;
    public float mSlopMultiplier;
    public int mSplitShadeFullTransitionDistance;
    public int mSplitShadeScrimTransitionDistance;
    public final SplitShadeStateController mSplitShadeStateController;
    public ValueAnimator mStackScrollerAlphaAnimator;
    public int mStackScrollerMeasuringPass;
    public int mStatusBarHeaderHeightKeyguard;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public int mStatusBarMinHeight;
    public final IStatusBarService mStatusBarService;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public final StatusBarStateListener mStatusBarStateListener;
    public final SysUIStateDisplaysInteractor mSysUIStateDisplaysInteractor;
    public final SysUiState mSysUiState;
    public final SystemClock mSystemClock;
    public final AnonymousClass18 mSystemUIWidgetCallback;
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
    public final UnlockedScreenOffAnimationController mUnlockedScreenOffAnimationController;
    public boolean mUpdateFlingOnLayout;
    public float mUpdateFlingVelocity;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public boolean mUpwardsWhenThresholdReached;
    public boolean mUseExternalTouch;
    public boolean mUserSetupComplete;
    public final boolean mVibrateOnOpening;
    public final VibratorHelper mVibratorHelper;
    public final NotificationPanelView mView;
    public String mViewName;
    public final NotificationWakeUpCoordinator mWakeUpCoordinator;
    public final WallpaperImageInjectCreator mWallpaperImageCreator;
    public boolean shouldScrollViewIntercept;
    public static final Rect M_DUMMY_DIRTY_RECT = new Rect(0, 0, 1, 1);
    public static final Rect EMPTY_RECT = new Rect();
    public static final boolean DISABLE_LONG_PRESS_EXPAND = Build.HARDWARE.equals("cutf_cvm");
    public int mLastCameraLaunchSource = 3;
    public final NotificationPanelViewController$$ExternalSyntheticLambda0 mOnEmptySpaceClickListener = new NotificationPanelViewController$$ExternalSyntheticLambda0(this);
    public final VelocityTracker mVelocityTracker = VelocityTracker.obtain();
    public final NotificationPanelViewController$$ExternalSyntheticLambda11 mFalsingTapListener = new FalsingManager.FalsingTapListener() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda11
        @Override // com.android.systemui.plugins.FalsingManager.FalsingTapListener
        public final void onAdditionalTapRequired() throws Resources.NotFoundException {
            NotificationPanelViewController notificationPanelViewController = this.f$0;
            SysuiStatusBarStateController sysuiStatusBarStateController = notificationPanelViewController.mStatusBarStateController;
            if (sysuiStatusBarStateController.getState() == 2) {
                notificationPanelViewController.mTapAgainViewController.show();
            } else {
                notificationPanelViewController.mKeyguardIndicationController.showTransientIndication(R.string.notification_tap_again);
            }
            if (sysuiStatusBarStateController.isDozing()) {
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
            AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("onChangedLockStarData: ", "NotificationPanelView", z);
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
    public final StateFlowImpl mIsBrightnessMirrorShowing = StateFlowKt.MutableStateFlow(Boolean.FALSE);

    /* renamed from: com.android.systemui.shade.NotificationPanelViewController$10, reason: invalid class name */
    public class AnonymousClass10 {
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
            PluginFaceWidgetManager pluginFaceWidgetManager = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
            if (pluginFaceWidgetManager == null) {
                Log.e("NotificationPanelView", "Failed to get PluginFaceWidgetManager");
            } else {
                pluginFaceWidgetManager.updateNowBarVisibility(notificationPanelViewController.mBarState == 1 ? 0 : 8);
            }
            float f = z ? 0.0f : 1.0f;
            if (j <= 0) {
                Log.d("NotificationPanelView", "updateAlpha() mFullScreenModeEnabled = " + notificationPanelViewController.mFullScreenModeEnabled + ", alpha = " + f);
                if (!notificationPanelViewController.mFullScreenModeEnabled || f <= 0.0f) {
                    notificationPanelViewController.setAlpha((int) (255.0f * f), false);
                    notificationPanelView.setAlpha(f);
                }
                if (animatorListener != null) {
                    animatorListener.onAnimationStart(null);
                    animatorListener.onAnimationEnd(null);
                }
            } else {
                notificationPanelViewController.setAlpha(z ? 0 : 255, true);
                notificationPanelView.animate().alpha(f).setDuration(j).setInterpolator(Interpolators.ACCELERATE).setListener(animatorListener).withEndAction(new Runnable() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda60
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationPanelViewController notificationPanelViewController2 = notificationPanelViewController;
                        boolean z2 = z;
                        Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                        notificationPanelViewController2.getClass();
                        notificationPanelViewController2.mView.setVisibility(z2 ? 4 : 0);
                        PluginFaceWidgetManager pluginFaceWidgetManager2 = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
                        if (pluginFaceWidgetManager2 == null) {
                            Log.e("NotificationPanelView", "Failed to get PluginFaceWidgetManager");
                        } else {
                            pluginFaceWidgetManager2.updateNowBarVisibility((notificationPanelViewController2.mBarState != 1 || z2) ? 4 : 0);
                        }
                    }
                }).withLayer();
            }
            notificationPanelViewController.mUpdateMonitor.setFaceWidgetFullScreenMode(z);
            if (z) {
                notificationPanelViewController.mKeyguardTouchAnimator.reset(false);
            }
            if (z) {
                notificationPanelViewController.mCentralSurfaces.getNotificationShadeWindowViewController().mView.getWindowInsetsController().setAnimationsDisabled(z);
                NavigationBarView navigationBarView = notificationPanelViewController.mCentralSurfaces.getNavigationBarView();
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
            SecQuickSettingsAffordanceInteractor secQuickSettingsAffordanceInteractor = notificationPanelViewController.mSecQuickSettingsAffordanceInteractor;
            if (secQuickSettingsAffordanceInteractor != null) {
                secQuickSettingsAffordanceInteractor.hideEffectIfNeeded("setFullScreenMode", z);
            }
        }
    }

    /* renamed from: com.android.systemui.shade.NotificationPanelViewController$4, reason: invalid class name */
    public class AnonymousClass4 implements KeyguardTouchSwipeCallback {
        public AnonymousClass4() {
        }

        public final void callUserActivity() {
            NotificationPanelViewController.this.mCentralSurfaces.userActivity();
        }

        @Override // com.android.systemui.keyguard.animator.KeyguardTouchSwipeCallback
        public final void onUnlockExecuted() {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.mView.post(notificationPanelViewController.mHideExpandedRunnable);
        }
    }

    /* renamed from: com.android.systemui.shade.NotificationPanelViewController$9, reason: invalid class name */
    public class AnonymousClass9 {
        public AnonymousClass9() {
        }
    }

    public final class ConfigurationListener implements ConfigurationController.ConfigurationListener {
        public /* synthetic */ ConfigurationListener(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onConfigChanged(Configuration configuration) {
            SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor;
            boolean z = QpRune.QUICK_PANEL_CODE_FOR_POP_OVER;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            if (z && (secQsUiDisplayModeInteractor = notificationPanelViewController.mSecQsUiDisplayModeInteractor) != null && secQsUiDisplayModeInteractor.isTablet()) {
                SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = notificationPanelViewController.mQsController.mSecQuickSettingsControllerImpl;
                if (secQuickSettingsControllerImpl.secQsUiDisplayModeInteractor.isTablet()) {
                    SecTabletHorizontalPanelPositionHelper tabletHorizontalPanelPositionHelper = secQuickSettingsControllerImpl.getTabletHorizontalPanelPositionHelper();
                    tabletHorizontalPanelPositionHelper.getClass();
                    int i = configuration.orientation;
                    if (i != tabletHorizontalPanelPositionHelper.currentOrientation) {
                        tabletHorizontalPanelPositionHelper.currentOrientation = i;
                        tabletHorizontalPanelPositionHelper.updateResources();
                        tabletHorizontalPanelPositionHelper.setHorizontalPanelTranslation((tabletHorizontalPanelPositionHelper.rightMost - tabletHorizontalPanelPositionHelper.panelCenter) * tabletHorizontalPanelPositionHelper.posRatio, true);
                    }
                }
                secQuickSettingsControllerImpl.updateScrollViewLocationDelta();
            }
            if (ShadeWindowGoesAround.isEnabled()) {
                notificationPanelViewController.updateResources$1();
            }
            MultiWindowEdgeDetector multiWindowEdgeDetector = notificationPanelViewController.mMultiWindowEdgeDetector;
            if (multiWindowEdgeDetector != null) {
                multiWindowEdgeDetector.onConfigurationChanged();
            }
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDensityOrFontScaleChanged() {
            PluginKeyguardStatusView pluginKeyguardStatusView;
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
            KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = notificationPanelViewController.mKeyguardSecBottomAreaViewController;
            if (keyguardSecBottomAreaViewController != null) {
                keyguardSecBottomAreaViewController.onDensityOrFontScaleChanged(false);
            }
            FaceWidgetContainerWrapper faceWidgetContainerWrapper = notificationPanelViewController.mKeyguardStatusBase;
            if (faceWidgetContainerWrapper == null || (pluginKeyguardStatusView = faceWidgetContainerWrapper.mPluginKeyguardStatusView) == null) {
                return;
            }
            pluginKeyguardStatusView.onDensityOrFontScaleChanged();
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

    public final class HeadsUpNotificationViewControllerImpl implements HeadsUpTouchHelper.HeadsUpNotificationViewController {
        public /* synthetic */ HeadsUpNotificationViewControllerImpl(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.notification.headsup.HeadsUpTouchHelper.HeadsUpNotificationViewController
        public final void setHeadsUpDraggingStartingHeight(int i) {
            NotificationPanelViewController.this.setHeadsUpDraggingStartingHeight(i);
        }

        @Override // com.android.systemui.statusbar.notification.headsup.HeadsUpTouchHelper.HeadsUpNotificationViewController
        public final void setTrackedHeadsUp(ExpandableNotificationRow expandableNotificationRow) {
            if (expandableNotificationRow != null) {
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                notificationPanelViewController2.mTrackedHeadsUpNotification = expandableNotificationRow;
                for (int i = 0; i < notificationPanelViewController2.mTrackingHeadsUpListeners.size(); i++) {
                    ((Consumer) notificationPanelViewController2.mTrackingHeadsUpListeners.get(i)).accept(expandableNotificationRow);
                }
                notificationPanelViewController.mExpandingFromHeadsUp = true;
            }
        }

        @Override // com.android.systemui.statusbar.notification.headsup.HeadsUpTouchHelper.HeadsUpNotificationViewController
        public final void startExpand(float f, float f2, float f3) {
            NotificationPanelViewController.m2946$$Nest$mstartExpandMotion(NotificationPanelViewController.this, f, f2, true, f3);
        }

        private HeadsUpNotificationViewControllerImpl() {
        }
    }

    public class KeyguardAffordanceHelperCallback implements KeyguardSecAffordanceHelper.Callback {
        public /* synthetic */ KeyguardAffordanceHelperCallback(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        private KeyguardAffordanceHelperCallback() {
        }
    }

    public interface NewNotifReadListener {
        void onNewNotificationRead();
    }

    public final class NsslHeightChangedListener {
        public /* synthetic */ NsslHeightChangedListener(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        private NsslHeightChangedListener() {
        }
    }

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
            NotificationPanelViewController.this.mStatusBarKeyguardViewManager.showPrimaryBouncer("NotificationPanelViewController#performAccessibilityAction", true);
            return true;
        }

        private ShadeAccessibilityDelegate() {
        }
    }

    public final class ShadeAttachStateChangeListener implements View.OnAttachStateChangeListener {
        public /* synthetic */ ShadeAttachStateChangeListener(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) throws Resources.NotFoundException {
            final int i = 0;
            final int i2 = 1;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            FragmentHostManager fragmentHostManager = notificationPanelViewController.mFragmentService.getFragmentHostManager(notificationPanelViewController.mView);
            QuickSettingsControllerImpl quickSettingsControllerImpl = NotificationPanelViewController.this.mQsController;
            quickSettingsControllerImpl.getClass();
            fragmentHostManager.addTagListener(QS.TAG, quickSettingsControllerImpl.new QsFragmentListener());
            int i3 = SceneContainerFlag.$r8$clinit;
            NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
            notificationPanelViewController2.mStatusBarStateController.addCallback(notificationPanelViewController2.mStatusBarStateListener);
            NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
            notificationPanelViewController3.mStatusBarStateListener.onStateChanged(notificationPanelViewController3.mStatusBarStateController.getState(), true);
            NotificationPanelViewController notificationPanelViewController4 = NotificationPanelViewController.this;
            ((ConfigurationControllerImpl) notificationPanelViewController4.mConfigurationController).addCallback(notificationPanelViewController4.mConfigurationListener);
            NotificationPanelViewController.this.mConfigurationListener.onThemeChanged();
            NotificationPanelViewController notificationPanelViewController5 = NotificationPanelViewController.this;
            notificationPanelViewController5.mFalsingManager.addTapListener(notificationPanelViewController5.mFalsingTapListener);
            NotificationPanelViewController.this.mKeyguardIndicationController.init();
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
                        int i4 = i;
                        DataUsageLabelManager dataUsageLabelManager2 = dataUsageLabelManager;
                        switch (i4) {
                            case 0:
                                dataUsageLabelManager2.onPanelConfigurationChanged(dataUsageLabelManager2.mContext.getResources().getConfiguration());
                                break;
                            default:
                                boolean z2 = DataUsageLabelManager.DEBUG;
                                DataUsageLabelView dataUsageLabelView2 = dataUsageLabelManager2.mLabelView;
                                if (dataUsageLabelView2 != null) {
                                    dataUsageLabelView2.setTextColor(dataUsageLabelManager2.mContext.getColor(R.color.sec_qs_security_footer_tint_color));
                                    break;
                                }
                                break;
                        }
                    }
                });
                ((Handler) Dependency.sDependency.getDependencyInner(dependencyKey)).postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.datausage.DataUsageLabelManager$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i4 = i2;
                        DataUsageLabelManager dataUsageLabelManager2 = dataUsageLabelManager;
                        switch (i4) {
                            case 0:
                                dataUsageLabelManager2.onPanelConfigurationChanged(dataUsageLabelManager2.mContext.getResources().getConfiguration());
                                break;
                            default:
                                boolean z2 = DataUsageLabelManager.DEBUG;
                                DataUsageLabelView dataUsageLabelView2 = dataUsageLabelManager2.mLabelView;
                                if (dataUsageLabelView2 != null) {
                                    dataUsageLabelView2.setTextColor(dataUsageLabelManager2.mContext.getColor(R.color.sec_qs_security_footer_tint_color));
                                    break;
                                }
                                break;
                        }
                    }
                }, 10000L);
                dataUsageLabelManager.mNavSettingsHelper.onAttachedToWindow();
                DataUsageLabelManager.QuickStarHelper quickStarHelper = dataUsageLabelManager.mQuickStarHelper;
                ((SlimIndicatorViewMediatorImpl) quickStarHelper.mSlimIndicatorViewMediator).registerSubscriber("DataUsageLabelManager", DataUsageLabelManager.this.mQuickStarHelper);
            }
            NotificationPanelViewController notificationPanelViewController6 = NotificationPanelViewController.this;
            if (notificationPanelViewController6.mBarState != notificationPanelViewController6.mStatusBarStateController.getState()) {
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                Log.d("NotificationPanelView", "panel mBarState " + NotificationPanelViewController.this.mBarState + "/ StatusBarStateController.getState() " + NotificationPanelViewController.this.mStatusBarStateController.getState());
                NotificationPanelViewController notificationPanelViewController7 = NotificationPanelViewController.this;
                notificationPanelViewController7.mStatusBarStateListener.onStateChanged(notificationPanelViewController7.mStatusBarStateController.getState(), false);
            }
            StatusBarNotificationPanelViewControllerExt statusBarNotificationPanelViewControllerExt = (StatusBarNotificationPanelViewControllerExt) NotificationPanelViewController.this.mSamsungBarExt.get();
            statusBarNotificationPanelViewControllerExt.printLog("onViewAttached()");
            IndicatorTouchHandler indicatorTouchHandler = statusBarNotificationPanelViewControllerExt.indicatorTouchHandler;
            indicatorTouchHandler.ongoingCallController.addCallback((OngoingCallListener) indicatorTouchHandler.ongoingCallListener);
            SecNotificationPanelViewController secNotificationPanelViewController = NotificationPanelViewController.this.mSecNotificationPanelViewController;
            if (secNotificationPanelViewController != null) {
                ((SecHideNotificationShadeInMirrorInteractorImpl) secNotificationPanelViewController.hideNotificationShadeInMirrorInteractor).setup();
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
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            FragmentHostManager fragmentHostManager = notificationPanelViewController.mFragmentService.getFragmentHostManager(notificationPanelViewController.mView);
            QuickSettingsControllerImpl quickSettingsControllerImpl = NotificationPanelViewController.this.mQsController;
            quickSettingsControllerImpl.getClass();
            QuickSettingsControllerImpl.QsFragmentListener qsFragmentListener = quickSettingsControllerImpl.new QsFragmentListener();
            ArrayList arrayList = (ArrayList) fragmentHostManager.mListeners.get(QS.TAG);
            if (arrayList != null && arrayList.remove(qsFragmentListener) && arrayList.size() == 0) {
                fragmentHostManager.mListeners.remove(QS.TAG);
            }
            NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
            notificationPanelViewController2.mStatusBarStateController.removeCallback(notificationPanelViewController2.mStatusBarStateListener);
            NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
            ((ConfigurationControllerImpl) notificationPanelViewController3.mConfigurationController).removeCallback(notificationPanelViewController3.mConfigurationListener);
            NotificationPanelViewController notificationPanelViewController4 = NotificationPanelViewController.this;
            notificationPanelViewController4.mFalsingManager.removeTapListener(notificationPanelViewController4.mFalsingTapListener);
            if (QpRune.QUICK_DATA_USAGE_LABEL) {
                DataUsageLabelManager dataUsageLabelManager = (DataUsageLabelManager) NotificationPanelViewController.this.mDataUsageLabelManagerLazy.get();
                DataUsageLabelParent dataUsageLabelParent = dataUsageLabelManager.mDataUsageLabelParent;
                if (dataUsageLabelParent != null && dataUsageLabelParent.getParentViewGroup() != null) {
                    dataUsageLabelParent.getParentViewGroup().removeAllViews();
                }
                dataUsageLabelManager.mNavSettingsHelper.onDetachedFromWindow();
                ((SlimIndicatorViewMediatorImpl) dataUsageLabelManager.mQuickStarHelper.mSlimIndicatorViewMediator).unregisterSubscriber("DataUsageLabelManager");
            }
            SecNotificationPanelViewController secNotificationPanelViewController = NotificationPanelViewController.this.mSecNotificationPanelViewController;
            if (secNotificationPanelViewController != null) {
                ((SecHideNotificationShadeInMirrorInteractorImpl) secNotificationPanelViewController.hideNotificationShadeInMirrorInteractor).tearDown();
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
            StatusBarNotificationPanelViewControllerExt statusBarNotificationPanelViewControllerExt = (StatusBarNotificationPanelViewControllerExt) NotificationPanelViewController.this.mSamsungBarExt.get();
            statusBarNotificationPanelViewControllerExt.printLog("onViewDetached()");
            IndicatorTouchHandler indicatorTouchHandler = statusBarNotificationPanelViewControllerExt.indicatorTouchHandler;
            indicatorTouchHandler.ongoingCallController.removeCallback((OngoingCallListener) indicatorTouchHandler.ongoingCallListener);
        }

        private ShadeAttachStateChangeListener() {
        }
    }

    public final class ShadeFoldAnimatorImpl implements ShadeFoldAnimator {
        public ShadeFoldAnimatorImpl() {
        }
    }

    public final class ShadeHeadsUpChangedListener implements OnHeadsUpChangedListener {
        public /* synthetic */ ShadeHeadsUpChangedListener(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
        public final void onHeadsUpPinned(NotificationEntry notificationEntry) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            if (notificationPanelViewController.isKeyguardShowing$1()) {
                return;
            }
            notificationPanelViewController.mNotificationStackScrollLayoutController.generateHeadsUpAnimation(notificationEntry, true);
        }

        @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
        public final void onHeadsUpPinnedModeChanged(boolean z) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            if (z) {
                notificationPanelViewController.mHeadsUpExistenceChangedRunnable.run();
            } else {
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                notificationPanelViewController.mHeadsUpAnimatingAway = true;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
                notificationStackScrollLayoutController.getClass();
                int i = SceneContainerFlag.$r8$clinit;
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                notificationStackScrollLayoutController.mView.setHeadsUpAnimatingAway(true);
                notificationPanelViewController.updateVisibility();
                notificationStackScrollLayoutController.mView.mAnimationFinishedRunnables.add(notificationPanelViewController.mHeadsUpExistenceChangedRunnable);
            }
            Rect rect2 = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
            notificationPanelViewController.updateGestureExclusionRect();
            notificationPanelViewController.mHeadsUpPinnedMode = z;
            notificationPanelViewController.updateVisibility();
            KeyguardStatusBarViewController keyguardStatusBarViewController = notificationPanelViewController.mKeyguardStatusBarViewController;
            keyguardStatusBarViewController.getClass();
            int i2 = SceneContainerFlag.$r8$clinit;
            RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
            keyguardStatusBarViewController.updateForHeadsUp(true);
        }

        @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
        public final void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
            if (!notificationEntry.isRowDismissed() || z) {
                return;
            }
            Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("onHeadsUpStateChanged ->  runAfterAnimationFinished "), notificationEntry.mKey, "NotificationPanelView");
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.mNotificationStackScrollLayoutController.mView.mAnimationFinishedRunnables.add(notificationPanelViewController.mHeadsUpExistenceChangedRunnable);
            notificationPanelViewController.mNotificationStackScrollLayoutController.mView.runAnimationFinishedRunnables();
        }

        @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
        public final void onHeadsUpUnPinned(NotificationEntry notificationEntry) {
            ExpandableNotificationRow expandableNotificationRow;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            if (!notificationPanelViewController.isFullyCollapsed() || (expandableNotificationRow = notificationEntry.row) == null || !expandableNotificationRow.mIsHeadsUp || notificationPanelViewController.isKeyguardShowing$1()) {
                return;
            }
            notificationPanelViewController.mNotificationStackScrollLayoutController.generateHeadsUpAnimation(notificationEntry, false);
            ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
            if (expandableNotificationRow2 != null) {
                expandableNotificationRow2.mMustStayOnScreen = false;
            }
        }

        private ShadeHeadsUpChangedListener() {
        }
    }

    public class ShadeHeadsUpTrackerImpl implements ShadeHeadsUpTracker {
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

    public final class ShadeLayoutChangeListener implements View.OnLayoutChangeListener {
        public /* synthetic */ ShadeLayoutChangeListener(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        @Override // android.view.View.OnLayoutChangeListener
        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) throws Resources.NotFoundException {
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
            notificationPanelViewController3.mRecomputedMaxCountCallStack = "onLayoutChange";
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController3.mNotificationStackScrollLayoutController;
            notificationStackScrollLayoutController.getClass();
            int i9 = SceneContainerFlag.$r8$clinit;
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
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
            QuickSettingsControllerImpl quickSettingsControllerImpl2 = NotificationPanelViewController.this.mQsController;
            int iIntValue = quickSettingsControllerImpl2.mMaxExpansionHeight;
            if (quickSettingsControllerImpl2.isQsFragmentCreated()) {
                quickSettingsControllerImpl2.updateMinHeight();
                int desiredHeight = quickSettingsControllerImpl2.mQs.getDesiredHeight();
                quickSettingsControllerImpl2.mMaxExpansionHeight = desiredHeight;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = quickSettingsControllerImpl2.mNotificationStackScrollLayoutController;
                notificationStackScrollLayoutController2.getClass();
                NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController2.mView;
                notificationStackScrollLayout.getClass();
                notificationStackScrollLayout.mMaxTopPadding = desiredHeight;
            }
            NotificationPanelViewController.this.positionClockAndNotifications(false);
            final QuickSettingsControllerImpl quickSettingsControllerImpl3 = NotificationPanelViewController.this.mQsController;
            if (quickSettingsControllerImpl3.getExpanded() && quickSettingsControllerImpl3.mFullyExpanded) {
                quickSettingsControllerImpl3.mExpansionHeight = quickSettingsControllerImpl3.mMaxExpansionHeight;
                NotificationPanelViewController$$ExternalSyntheticLambda0 notificationPanelViewController$$ExternalSyntheticLambda0 = quickSettingsControllerImpl3.mExpansionHeightSetToMaxListener;
                if (notificationPanelViewController$$ExternalSyntheticLambda0 != null) {
                    notificationPanelViewController$$ExternalSyntheticLambda0.onExpansionHeightSetToMax(true);
                }
                final int i10 = quickSettingsControllerImpl3.mMaxExpansionHeight;
                if (i10 != iIntValue) {
                    ValueAnimator valueAnimator = quickSettingsControllerImpl3.mSizeChangeAnimator;
                    if (valueAnimator != null) {
                        iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        quickSettingsControllerImpl3.mSizeChangeAnimator.cancel();
                    }
                    ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(iIntValue, i10);
                    quickSettingsControllerImpl3.mSizeChangeAnimator = valueAnimatorOfInt;
                    valueAnimatorOfInt.setDuration(300L);
                    quickSettingsControllerImpl3.mSizeChangeAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                    quickSettingsControllerImpl3.mSizeChangeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda36
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            QuickSettingsControllerImpl quickSettingsControllerImpl4 = quickSettingsControllerImpl3;
                            int iIntValue2 = i10;
                            NotificationPanelViewController$$ExternalSyntheticLambda0 notificationPanelViewController$$ExternalSyntheticLambda02 = quickSettingsControllerImpl4.mExpansionHeightSetToMaxListener;
                            if (notificationPanelViewController$$ExternalSyntheticLambda02 != null) {
                                notificationPanelViewController$$ExternalSyntheticLambda02.onExpansionHeightSetToMax(true);
                            }
                            ValueAnimator valueAnimator3 = quickSettingsControllerImpl4.mSizeChangeAnimator;
                            if (valueAnimator3 != null) {
                                iIntValue2 = ((Integer) valueAnimator3.getAnimatedValue()).intValue();
                            } else {
                                RecordingInputConnection$$ExternalSyntheticOutline0.m(iIntValue2, "animator is null. So force set height as ", "QuickSettingsController");
                            }
                            quickSettingsControllerImpl4.mQs.setHeightOverride(iIntValue2);
                        }
                    });
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
            } else if (quickSettingsControllerImpl3.getExpanded() || quickSettingsControllerImpl3.mExpansionAnimator != null) {
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
            NotificationPanelViewController.this.getClass();
            NotificationPanelViewController.this.updateNsslWidth();
            DejankUtils.stopDetectingBlockingIpcs("NVP#onLayout");
        }

        private ShadeLayoutChangeListener() {
        }
    }

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
                notificationPanelViewController.mRecomputedMaxCountCallStack = "onDozeAmountChanged";
            }
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStateChanged(int i) throws Resources.NotFoundException {
            onStateChanged(i, false);
        }

        private StatusBarStateListener() {
        }

        public final void onStateChanged(int i, boolean z) throws Resources.NotFoundException {
            boolean z2;
            NotificationStackScrollLayout notificationStackScrollLayout;
            int i2;
            SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor;
            long j;
            long j2;
            QS qs;
            int i3 = 1;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) notificationPanelViewController.mStatusBarStateController;
            boolean z3 = statusBarStateControllerImpl.mState == 0 && statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide;
            int i4 = notificationPanelViewController.mBarState;
            boolean z4 = i == 1;
            notificationPanelViewController.setKeyguardSecBottomAreaVisibility(i, z3);
            KeyguardStateControllerImpl keyguardStateControllerImpl = notificationPanelViewController.mKeyguardStateController;
            notificationPanelViewController.mKeyguardStatusBase.setKeyguardStatusViewVisibility(i, notificationPanelViewController.mBarState, keyguardStateControllerImpl.mKeyguardFadingAway, z3);
            if (i == 1) {
                Log.d("NotificationPanelView", "resetClockViewAlpha");
                FaceWidgetContainerWrapper faceWidgetContainerWrapper = notificationPanelViewController.mKeyguardStatusBase;
                View view = faceWidgetContainerWrapper.mClockContainer;
                if (view == null) {
                    view = faceWidgetContainerWrapper.mFaceWidgetContainer;
                }
                if (view instanceof ViewGroup) {
                    ((ViewGroup) view).getChildAt(0).setAlpha(1.0f);
                }
                View viewProvideComplication = notificationPanelViewController.provideComplication();
                if (viewProvideComplication != null) {
                    viewProvideComplication.setAlpha(1.0f);
                }
            }
            int i5 = notificationPanelViewController.mMediaNowBarExpandState;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
            if (i5 == 1 && z4 && notificationStackScrollLayoutController != null) {
                notificationStackScrollLayoutController.mMaxAlphaForKeyguard = 0.0f;
                notificationStackScrollLayoutController.mMaxAlphaForKeyguardSource = "MediaNowBar keyguardShowing";
                notificationStackScrollLayoutController.updateAlpha$1$1();
            }
            notificationPanelViewController.mBarState = i;
            QuickSettingsControllerImpl quickSettingsControllerImpl = notificationPanelViewController.mQsController;
            quickSettingsControllerImpl.mBarState = i;
            SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = quickSettingsControllerImpl.mSecQuickSettingsControllerImpl;
            if (secQuickSettingsControllerImpl != null) {
                ((SecQSPanelResourcePicker) secQuickSettingsControllerImpl.resourcePicker$delegate.getValue()).resourcePickHelper.getTargetPicker().getClass();
                secQuickSettingsControllerImpl.barState = i;
                if (i == 2 && (qs = quickSettingsControllerImpl.mQs) != null) {
                    qs.setListening(true);
                }
            }
            PluginFaceWidgetManager pluginFaceWidgetManager = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
            if (pluginFaceWidgetManager == null) {
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                Log.e("NotificationPanelView", "Failed to get PluginFaceWidgetManager");
            } else {
                PluginKeyguardStatusView pluginKeyguardStatusView = pluginFaceWidgetManager.mFaceWidgetPlugin;
                if (pluginKeyguardStatusView != null) {
                    pluginKeyguardStatusView.updateBarState(i);
                }
            }
            NotificationPanelView notificationPanelView = notificationPanelViewController.mView;
            if (i4 == 1 && (z3 || i == 2)) {
                if (keyguardStateControllerImpl.mKeyguardFadingAway) {
                    z2 = z4;
                    j = keyguardStateControllerImpl.mKeyguardFadingAwayDelay;
                    keyguardStateControllerImpl.getClass();
                    j2 = keyguardStateControllerImpl.mKeyguardFadingAwayDuration / 2;
                } else {
                    z2 = z4;
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
                z2 = z4;
                KeyguardTouchAnimator keyguardTouchAnimator = notificationPanelViewController.mKeyguardTouchAnimator;
                if (i4 == 2 && i == 1) {
                    notificationPanelViewController.mKeyguardStatusBarViewController.animateKeyguardStatusBarIn();
                    notificationStackScrollLayoutController.mView.resetScrollPosition();
                    quickSettingsControllerImpl.updateNightMode(notificationPanelView.getVisibility());
                    keyguardTouchAnimator.reset(false);
                    if (CscRune.KEYGUARD_DCM_LIVE_UX) {
                        notificationPanelViewController.mMascotViewContainer.setMascotViewVisible(notificationPanelViewController.mDozing ? 8 : 0);
                    }
                } else {
                    if (i4 != 0 || i != 1 || !notificationPanelViewController.mScreenOffAnimationController.isKeyguardShowDelayed() || z) {
                        boolean zIsOnAod = notificationPanelViewController.isOnAod();
                        ShadeLogger shadeLogger = notificationPanelViewController.mShadeLog;
                        shadeLogger.getClass();
                        LogLevel logLevel = LogLevel.VERBOSE;
                        ShadeLogger$$ExternalSyntheticLambda0 shadeLogger$$ExternalSyntheticLambda0 = new ShadeLogger$$ExternalSyntheticLambda0(i3);
                        LogBuffer logBuffer = shadeLogger.buffer;
                        LogMessage logMessageObtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$$ExternalSyntheticLambda0, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                        logMessageImpl.bool1 = z2;
                        logMessageImpl.bool2 = zIsOnAod;
                        logMessageImpl.bool3 = z;
                        logMessageImpl.int1 = i4;
                        logMessageImpl.int2 = i;
                        logBuffer.commit(logMessageObtain);
                        notificationPanelViewController.mKeyguardStatusBarViewController.updateViewState(1.0f, z2 ? 0 : 4);
                    }
                    if (z2 && i4 != notificationPanelViewController.mBarState) {
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
                    } else if (i4 == 1 && i == 0) {
                        notificationPanelViewController.cancelHeightAnimator();
                    }
                    if (i4 == 1 && i == 0 && (notificationStackScrollLayout = notificationStackScrollLayoutController.mView) != null) {
                        HashSet hashSet = notificationStackScrollLayout.mAnimationFinishedRunnables;
                        NotificationPanelViewController$$ExternalSyntheticLambda18 notificationPanelViewController$$ExternalSyntheticLambda18 = notificationPanelViewController.mHeadsUpExistenceChangedRunnable;
                        if (hashSet.contains(notificationPanelViewController$$ExternalSyntheticLambda18)) {
                            notificationPanelViewController$$ExternalSyntheticLambda18.run();
                        }
                    }
                }
            }
            KeyguardStatusBarViewController keyguardStatusBarViewController = notificationPanelViewController.mKeyguardStatusBarViewController;
            keyguardStatusBarViewController.getClass();
            int i6 = SceneContainerFlag.$r8$clinit;
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            keyguardStatusBarViewController.updateForHeadsUp(true);
            if (z2) {
                notificationPanelViewController.updateDozingVisibilities(false);
            }
            notificationPanelViewController.mRecomputedMaxCountCallStack = "onStateChanged";
            quickSettingsControllerImpl.updateQsState$2();
            if (QpRune.QUICK_PANEL_CODE_FOR_POP_OVER && (secQsUiDisplayModeInteractor = notificationPanelViewController.mSecQsUiDisplayModeInteractor) != null && secQsUiDisplayModeInteractor.isTablet() && i == 1) {
                quickSettingsControllerImpl.mSecQuickSettingsControllerImpl.getTabletHorizontalPanelPositionHelper().resetHorizontalPanelPosition(true);
            }
            notificationPanelViewController.onBarStateChanged(notificationPanelViewController.mBarState);
            if (i4 == i && i == 1 && (i2 = notificationPanelViewController.mPluginLockViewMode) != 0) {
                notificationPanelViewController.setViewMode(i2);
            }
            if (notificationPanelViewController.mBarState == 0 && notificationPanelViewController.mPluginLockMediator.isWindowSecured()) {
                notificationPanelViewController.mPluginLockMediator.updateWindowSecureState(false);
            }
            View view2 = notificationPanelViewController.mPluginLockStarContainer;
            if (view2 != null) {
                view2.setVisibility(notificationPanelViewController.mBarState == 1 ? 0 : 8);
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
                        secNotificationPanelViewController.quickSettingsController.setExpansionHeight(r4.mMaxExpansionHeight);
                    }
                    if (((i4 == 1 && i == 0) || (i4 == 0 && i == 1)) && secNotificationPanelViewController.isTrackingSupplier.getAsBoolean()) {
                        Log.d("SecNotificationPanelViewController", "legacyShadeTracking true -> false by force in onStateChanged()");
                        secNotificationPanelViewController.onTrackingStoppedConsumer.accept(Boolean.FALSE);
                    }
                }
            }
            if (i == 0 && notificationPanelViewController.mInstantExpanding) {
                notificationPanelViewController.mInstantExpanding = false;
            }
        }
    }

    public final class TouchHandler implements View.OnTouchListener, Gefingerpoken {
        public long mLastTouchDownTime = -1;

        public TouchHandler() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:198:0x040d  */
        /* JADX WARN: Removed duplicated region for block: B:200:0x0413  */
        /* JADX WARN: Removed duplicated region for block: B:205:0x0452  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean handleTouch$1(MotionEvent motionEvent) throws Resources.NotFoundException {
            boolean z;
            NotificationPanelViewController notificationPanelViewController;
            NotificationPanelViewController notificationPanelViewController2;
            StringBuilder sb;
            int pointerId;
            QuickPanelLogger quickPanelLogger = NotificationPanelViewController.this.mQuickPanelLogger;
            if (quickPanelLogger != null) {
                quickPanelLogger.quickPanelLoggerHelper.handleTouchLogger.log(motionEvent, quickPanelLogger.tag, "");
            }
            NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
            SecNotificationPanelViewController secNotificationPanelViewController = notificationPanelViewController3.mSecNotificationPanelViewController;
            if (secNotificationPanelViewController == null || !notificationPanelViewController3.isFullyCollapsed() || !motionEvent.isFromSource(8194) || secNotificationPanelViewController.isTrackingSupplier.getAsBoolean()) {
                NotificationPanelViewController notificationPanelViewController4 = NotificationPanelViewController.this;
                if (notificationPanelViewController4.mInstantExpanding) {
                    notificationPanelViewController4.mShadeLog.logMotionEvent(motionEvent, "handleTouch: touch ignored due to instant expanding");
                    QuickPanelLogger quickPanelLogger2 = NotificationPanelViewController.this.mQuickPanelLogger;
                    if (quickPanelLogger2 != null) {
                        quickPanelLogger2.handleTouch(motionEvent, "mInstantExpanding", false);
                        return false;
                    }
                } else if (notificationPanelViewController4.mTouchDisabled && motionEvent.getActionMasked() != 3) {
                    NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "handleTouch: non-cancel action, touch disabled");
                    QuickPanelLogger quickPanelLogger3 = NotificationPanelViewController.this.mQuickPanelLogger;
                    if (quickPanelLogger3 != null) {
                        quickPanelLogger3.handleTouch(motionEvent, "mTouchDisabled && event.getActionMasked() != ACTION_CANCEL", false);
                        return false;
                    }
                } else if (!NotificationPanelViewController.this.mMotionAborted || motionEvent.getActionMasked() == 0) {
                    NotificationPanelViewController notificationPanelViewController5 = NotificationPanelViewController.this;
                    if (notificationPanelViewController5.mNotificationsDragEnabled) {
                        int iFindPointerIndex = motionEvent.findPointerIndex(notificationPanelViewController5.mTrackingPointer);
                        if (iFindPointerIndex < 0) {
                            NotificationPanelViewController.this.mTrackingPointer = motionEvent.getPointerId(0);
                            iFindPointerIndex = 0;
                        }
                        float x = motionEvent.getX(iFindPointerIndex);
                        float y = motionEvent.getY(iFindPointerIndex);
                        if (motionEvent.getActionMasked() == 0 || motionEvent.getActionMasked() == 2) {
                            NotificationPanelViewController notificationPanelViewController6 = NotificationPanelViewController.this;
                            if (notificationPanelViewController6.mExpectingSynthesizedDown) {
                                Log.d("NotificationPanelView", "shouldGestureWaitForTouchSlop set mExpectingSynthesizedDown to false");
                                notificationPanelViewController6.mExpectingSynthesizedDown = false;
                            } else {
                                if (notificationPanelViewController6.isFullyCollapsed() || notificationPanelViewController6.mBarState != 0) {
                                    z = true;
                                }
                                notificationPanelViewController6.mGestureWaitForTouchSlop = z;
                                NotificationPanelViewController.this.mIgnoreXTouchSlop = true;
                            }
                            z = false;
                            notificationPanelViewController6.mGestureWaitForTouchSlop = z;
                            NotificationPanelViewController.this.mIgnoreXTouchSlop = true;
                        }
                        boolean zIsTrackpadThreeFingerSwipe = Utilities.isTrackpadThreeFingerSwipe(motionEvent);
                        switch (motionEvent.getActionMasked()) {
                            case 0:
                                if (QuickStepContract.ALLOW_BACK_GESTURE_IN_SHADE) {
                                    NotificationPanelViewController.this.getClass();
                                }
                                NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "onTouch: down action");
                                NotificationPanelViewController notificationPanelViewController7 = NotificationPanelViewController.this;
                                NotificationPanelViewController.m2946$$Nest$mstartExpandMotion(notificationPanelViewController7, x, y, false, notificationPanelViewController7.mExpandedHeight);
                                NotificationPanelViewController notificationPanelViewController8 = NotificationPanelViewController.this;
                                notificationPanelViewController8.getClass();
                                notificationPanelViewController8.mPanelClosedOnDown = notificationPanelViewController8.isFullyCollapsed();
                                NotificationPanelViewController notificationPanelViewController9 = NotificationPanelViewController.this;
                                notificationPanelViewController9.mShadeLog.logPanelClosedOnDown("handle down touch", notificationPanelViewController9.mPanelClosedOnDown, notificationPanelViewController9.mExpandedFraction);
                                NotificationPanelViewController notificationPanelViewController10 = NotificationPanelViewController.this;
                                notificationPanelViewController10.mHasLayoutedSinceDown = false;
                                notificationPanelViewController10.mUpdateFlingOnLayout = false;
                                notificationPanelViewController10.mMotionAborted = false;
                                notificationPanelViewController10.mDownTime = notificationPanelViewController10.mSystemClock.uptimeMillis();
                                NotificationPanelViewController notificationPanelViewController11 = NotificationPanelViewController.this;
                                notificationPanelViewController11.getClass();
                                notificationPanelViewController11.mTouchAboveFalsingThreshold = false;
                                notificationPanelViewController11.mCollapsedAndHeadsUpOnDown = notificationPanelViewController11.isFullyCollapsed() && ((HeadsUpManagerImpl) NotificationPanelViewController.this.mHeadsUpManager).mHasPinnedNotification;
                                NotificationPanelViewController.m2943$$Nest$maddMovement(NotificationPanelViewController.this, motionEvent);
                                NotificationPanelViewController notificationPanelViewController12 = NotificationPanelViewController.this;
                                boolean z2 = (notificationPanelViewController12.mHeightAnimator == null || notificationPanelViewController12.mIsSpringBackAnimation) ? false : true;
                                if (!notificationPanelViewController12.mGestureWaitForTouchSlop || z2) {
                                    notificationPanelViewController12.mTouchSlopExceeded = z2 || notificationPanelViewController12.mTouchSlopExceededBeforeDown;
                                    notificationPanelViewController12.cancelHeightAnimator();
                                    NotificationPanelViewController.this.onTrackingStarted();
                                }
                                if (NotificationPanelViewController.this.isFullyCollapsed()) {
                                    NotificationPanelViewController notificationPanelViewController13 = NotificationPanelViewController.this;
                                    if (!((HeadsUpManagerImpl) notificationPanelViewController13.mHeadsUpManager).mHasPinnedNotification && !notificationPanelViewController13.mCentralSurfaces.mBouncerShowing) {
                                        notificationPanelViewController13.updateExpansionAndVisibility();
                                        CentralSurfacesImpl centralSurfacesImpl = notificationPanelViewController13.mCentralSurfaces;
                                        DisplayMetrics displayMetrics = centralSurfacesImpl.mDisplayMetrics;
                                        notificationPanelViewController13.mLockscreenGestureLogger.mMetricsLogger.write(new LogMaker(1328).setType(4).addTaggedData(1326, Integer.valueOf((int) ((motionEvent.getX() / displayMetrics.widthPixels) * 100.0f))).addTaggedData(1327, Integer.valueOf((int) ((motionEvent.getY() / displayMetrics.heightPixels) * 100.0f))).addTaggedData(1329, Integer.valueOf(centralSurfacesImpl.mDisplay.getRotation())));
                                        new UiEventLoggerImpl().log(LockscreenGestureLogger.LockscreenUiEvent.LOCKSCREEN_UNLOCKED_NOTIFICATION_PANEL_EXPAND);
                                    }
                                }
                                notificationPanelViewController = NotificationPanelViewController.this;
                                if (notificationPanelViewController.mQuickPanelLogger != null && (sb = notificationPanelViewController.mQuickPanelLogBuilder) != null) {
                                    sb.setLength(0);
                                    StringBuilder sb2 = NotificationPanelViewController.this.mQuickPanelLogBuilder;
                                    sb2.append("FINAL: !mGestureWaitForTouchSlop: ");
                                    sb2.append(!NotificationPanelViewController.this.mGestureWaitForTouchSlop);
                                    sb2.append(" || isTracking(): ");
                                    sb2.append(NotificationPanelViewController.this.isTracking());
                                    NotificationPanelViewController notificationPanelViewController14 = NotificationPanelViewController.this;
                                    QuickPanelLogger quickPanelLogger4 = notificationPanelViewController14.mQuickPanelLogger;
                                    quickPanelLogger4.quickPanelLoggerHelper.handleTouchLogger.log(motionEvent, quickPanelLogger4.tag, notificationPanelViewController14.mQuickPanelLogBuilder.toString());
                                }
                                notificationPanelViewController2 = NotificationPanelViewController.this;
                                if (notificationPanelViewController2.mGestureWaitForTouchSlop || notificationPanelViewController2.isTracking()) {
                                }
                                break;
                            case 1:
                            case 3:
                            case 4:
                                NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "onTouch: up/cancel action");
                                NotificationPanelViewController.m2943$$Nest$maddMovement(NotificationPanelViewController.this, motionEvent);
                                NotificationPanelViewController.m2944$$Nest$mendMotionEvent(NotificationPanelViewController.this, motionEvent, x, y, false);
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
                                notificationPanelViewController = NotificationPanelViewController.this;
                                if (notificationPanelViewController.mQuickPanelLogger != null) {
                                    sb.setLength(0);
                                    StringBuilder sb22 = NotificationPanelViewController.this.mQuickPanelLogBuilder;
                                    sb22.append("FINAL: !mGestureWaitForTouchSlop: ");
                                    sb22.append(!NotificationPanelViewController.this.mGestureWaitForTouchSlop);
                                    sb22.append(" || isTracking(): ");
                                    sb22.append(NotificationPanelViewController.this.isTracking());
                                    NotificationPanelViewController notificationPanelViewController142 = NotificationPanelViewController.this;
                                    QuickPanelLogger quickPanelLogger42 = notificationPanelViewController142.mQuickPanelLogger;
                                    quickPanelLogger42.quickPanelLoggerHelper.handleTouchLogger.log(motionEvent, quickPanelLogger42.tag, notificationPanelViewController142.mQuickPanelLogBuilder.toString());
                                    break;
                                }
                                notificationPanelViewController2 = NotificationPanelViewController.this;
                                if (notificationPanelViewController2.mGestureWaitForTouchSlop) {
                                    break;
                                }
                                break;
                            case 2:
                                if (QuickStepContract.ALLOW_BACK_GESTURE_IN_SHADE) {
                                    NotificationPanelViewController.this.getClass();
                                }
                                if (NotificationPanelViewController.this.isFullyCollapsed()) {
                                    NotificationPanelViewController notificationPanelViewController15 = NotificationPanelViewController.this;
                                    notificationPanelViewController15.mHasVibratedOnOpen = false;
                                    float f = notificationPanelViewController15.mExpandedFraction;
                                    ShadeLogger shadeLogger = notificationPanelViewController15.mShadeLog;
                                    shadeLogger.getClass();
                                    LogLevel logLevel = LogLevel.VERBOSE;
                                    ShadeLogger$$ExternalSyntheticLambda0 shadeLogger$$ExternalSyntheticLambda0 = new ShadeLogger$$ExternalSyntheticLambda0(6);
                                    LogBuffer logBuffer = shadeLogger.buffer;
                                    LogMessage logMessageObtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$$ExternalSyntheticLambda0, null);
                                    LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                                    logMessageImpl.bool1 = false;
                                    logMessageImpl.double1 = f;
                                    logBuffer.commit(logMessageObtain);
                                }
                                NotificationPanelViewController.m2943$$Nest$maddMovement(NotificationPanelViewController.this, motionEvent);
                                if (!NotificationPanelViewController.this.isFullyCollapsed()) {
                                    NotificationPanelViewController.this.maybeVibrateOnOpening(true);
                                }
                                float f2 = y - NotificationPanelViewController.this.mInitialExpandY;
                                if (Math.abs(f2) > NotificationPanelViewController.this.getTouchSlop$1(motionEvent) && (Math.abs(f2) > Math.abs(x - NotificationPanelViewController.this.mInitialExpandX) || NotificationPanelViewController.this.mIgnoreXTouchSlop)) {
                                    NotificationPanelViewController notificationPanelViewController16 = NotificationPanelViewController.this;
                                    notificationPanelViewController16.mTouchSlopExceeded = true;
                                    if (notificationPanelViewController16.mGestureWaitForTouchSlop && !notificationPanelViewController16.isTracking() && !NotificationPanelViewController.this.mCollapsedAndHeadsUpOnDown && (motionEvent.getSource() != 8194 || motionEvent.getToolType(0) != 1 || motionEvent.getClassification() != 3)) {
                                        NotificationPanelViewController notificationPanelViewController17 = NotificationPanelViewController.this;
                                        if (notificationPanelViewController17.mInitialOffsetOnTouch != 0.0f) {
                                            NotificationPanelViewController.m2946$$Nest$mstartExpandMotion(notificationPanelViewController17, x, y, false, notificationPanelViewController17.mExpandedHeight);
                                            f2 = 0.0f;
                                        }
                                        NotificationPanelViewController.this.cancelHeightAnimator();
                                        NotificationPanelViewController.this.onTrackingStarted();
                                    }
                                }
                                float fMax = Math.max(0.0f, NotificationPanelViewController.this.mInitialOffsetOnTouch + f2);
                                NotificationPanelViewController.this.getClass();
                                float fMax2 = Math.max(fMax, 0.0f);
                                if ((-f2) >= NotificationPanelViewController.this.getFalsingThreshold()) {
                                    NotificationPanelViewController notificationPanelViewController18 = NotificationPanelViewController.this;
                                    notificationPanelViewController18.mTouchAboveFalsingThreshold = true;
                                    float f3 = x - notificationPanelViewController18.mInitialExpandX;
                                    float f4 = y - notificationPanelViewController18.mInitialExpandY;
                                    notificationPanelViewController18.mUpwardsWhenThresholdReached = f4 < 0.0f && Math.abs(f4) >= Math.abs(f3);
                                }
                                NotificationPanelViewController notificationPanelViewController19 = NotificationPanelViewController.this;
                                if (!notificationPanelViewController19.mGestureWaitForTouchSlop || notificationPanelViewController19.isTracking()) {
                                    NotificationPanelViewController notificationPanelViewController20 = NotificationPanelViewController.this;
                                    if (!notificationPanelViewController20.mBlockingExpansionForCurrentTouch) {
                                        QuickSettingsControllerImpl quickSettingsControllerImpl = notificationPanelViewController20.mQsController;
                                        if (!quickSettingsControllerImpl.mConflictingExpansionGesture || !quickSettingsControllerImpl.getExpanded()) {
                                            NotificationPanelViewController.this.mAmbientState.setSwipingUp(f2 <= 0.0f);
                                            NotificationPanelViewController.this.setExpandedHeightInternal(fMax2);
                                        }
                                    }
                                }
                                notificationPanelViewController = NotificationPanelViewController.this;
                                if (notificationPanelViewController.mQuickPanelLogger != null) {
                                }
                                notificationPanelViewController2 = NotificationPanelViewController.this;
                                if (notificationPanelViewController2.mGestureWaitForTouchSlop) {
                                }
                                break;
                            case 5:
                                NotificationPanelViewController notificationPanelViewController21 = NotificationPanelViewController.this;
                                notificationPanelViewController21.mShadeLog.logMotionEventStatusBarState(motionEvent, notificationPanelViewController21.mStatusBarStateController.getState(), "handleTouch: pointer down action");
                                if (!zIsTrackpadThreeFingerSwipe && NotificationPanelViewController.this.mStatusBarStateController.getState() == 1) {
                                    NotificationPanelViewController notificationPanelViewController22 = NotificationPanelViewController.this;
                                    notificationPanelViewController22.mMotionAborted = true;
                                    NotificationPanelViewController.m2944$$Nest$mendMotionEvent(notificationPanelViewController22, motionEvent, x, y, true);
                                    QuickPanelLogger quickPanelLogger5 = NotificationPanelViewController.this.mQuickPanelLogger;
                                    if (quickPanelLogger5 != null) {
                                        quickPanelLogger5.handleTouch(motionEvent, "!isTrackpadTwoOrThreeFingerSwipe && mStatusBarStateController.getState() == KEYGUARD)", false);
                                        break;
                                    }
                                } else {
                                    notificationPanelViewController = NotificationPanelViewController.this;
                                    if (notificationPanelViewController.mQuickPanelLogger != null) {
                                    }
                                    notificationPanelViewController2 = NotificationPanelViewController.this;
                                    if (notificationPanelViewController2.mGestureWaitForTouchSlop) {
                                    }
                                }
                                break;
                            case 6:
                                if (!zIsTrackpadThreeFingerSwipe && NotificationPanelViewController.this.mTrackingPointer == (pointerId = motionEvent.getPointerId(motionEvent.getActionIndex()))) {
                                    int i = motionEvent.getPointerId(0) != pointerId ? 0 : 1;
                                    float y2 = motionEvent.getY(i);
                                    float x2 = motionEvent.getX(i);
                                    NotificationPanelViewController.this.mTrackingPointer = motionEvent.getPointerId(i);
                                    NotificationPanelViewController notificationPanelViewController23 = NotificationPanelViewController.this;
                                    notificationPanelViewController23.mHandlingPointerUp = true;
                                    NotificationPanelViewController.m2946$$Nest$mstartExpandMotion(notificationPanelViewController23, x2, y2, true, notificationPanelViewController23.mExpandedHeight);
                                    NotificationPanelViewController.this.mHandlingPointerUp = false;
                                }
                                notificationPanelViewController = NotificationPanelViewController.this;
                                if (notificationPanelViewController.mQuickPanelLogger != null) {
                                }
                                notificationPanelViewController2 = NotificationPanelViewController.this;
                                if (notificationPanelViewController2.mGestureWaitForTouchSlop) {
                                }
                                break;
                        }
                        return true;
                    }
                    if (notificationPanelViewController5.isTracking()) {
                        NotificationPanelViewController.this.onTrackingStopped(true);
                    }
                    NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "handleTouch: drag not enabled");
                    QuickPanelLogger quickPanelLogger6 = NotificationPanelViewController.this.mQuickPanelLogger;
                    if (quickPanelLogger6 != null) {
                        quickPanelLogger6.handleTouch(motionEvent, "!mNotificationsDragEnabled", false);
                        return false;
                    }
                } else {
                    NotificationPanelViewController notificationPanelViewController24 = NotificationPanelViewController.this;
                    notificationPanelViewController24.mShadeLog.logMotionEventStatusBarState(motionEvent, notificationPanelViewController24.mStatusBarStateController.getState(), "handleTouch: non-down action, motion was aborted");
                    QuickPanelLogger quickPanelLogger7 = NotificationPanelViewController.this.mQuickPanelLogger;
                    if (quickPanelLogger7 != null) {
                        quickPanelLogger7.handleTouch(motionEvent, "mMotionAborted && event.getActionMasked() != ACTION_DOWN", false);
                        return false;
                    }
                }
                return false;
            }
            int action = motionEvent.getAction();
            if (action == 0) {
                NotificationPanelViewController.this.mMotionAborted = false;
            } else if (action == 1) {
                notificationPanelViewController3.expand(true);
            }
            NotificationPanelViewController notificationPanelViewController25 = NotificationPanelViewController.this;
            QuickPanelLogger quickPanelLogger8 = notificationPanelViewController25.mQuickPanelLogger;
            if (quickPanelLogger8 != null) {
                if (notificationPanelViewController25.mMotionAborted) {
                    quickPanelLogger8.handleTouch(motionEvent, "On expanding, single mouse click expands the panel instead of dragging", true);
                    return true;
                }
                quickPanelLogger8.handleTouch(motionEvent, "!isFullyCollapsed and from mouse", true);
                return true;
            }
            return true;
        }

        /* JADX WARN: Code restructure failed: missing block: B:342:0x0588, code lost:
        
            if (r5.mUpdateMonitor.getUserHasTrust(r5.mSelectedUserInteractor.getSelectedUserId()) != false) goto L343;
         */
        /* JADX WARN: Code restructure failed: missing block: B:355:0x05be, code lost:
        
            if (r10 < (r14 + r12.getHeight())) goto L369;
         */
        /* JADX WARN: Code restructure failed: missing block: B:368:0x05f3, code lost:
        
            if (r10 < (r12.getHeight() + r14)) goto L369;
         */
        /* JADX WARN: Removed duplicated region for block: B:183:0x02e8  */
        /* JADX WARN: Removed duplicated region for block: B:190:0x0306  */
        /* JADX WARN: Removed duplicated region for block: B:191:0x0308  */
        /* JADX WARN: Removed duplicated region for block: B:194:0x030f  */
        /* JADX WARN: Removed duplicated region for block: B:196:0x0315  */
        /* JADX WARN: Removed duplicated region for block: B:203:0x032a  */
        /* JADX WARN: Removed duplicated region for block: B:206:0x0332  */
        /* JADX WARN: Removed duplicated region for block: B:207:0x033e  */
        /* JADX WARN: Removed duplicated region for block: B:227:0x03a9  */
        /* JADX WARN: Removed duplicated region for block: B:235:0x03d8  */
        /* JADX WARN: Removed duplicated region for block: B:243:0x0404  */
        /* JADX WARN: Removed duplicated region for block: B:406:0x06a5  */
        /* JADX WARN: Removed duplicated region for block: B:414:0x06d7  */
        /* JADX WARN: Removed duplicated region for block: B:417:0x0704  */
        /* JADX WARN: Removed duplicated region for block: B:421:0x070e  */
        /* JADX WARN: Removed duplicated region for block: B:42:0x0099  */
        /* JADX WARN: Removed duplicated region for block: B:443:0x0748  */
        /* JADX WARN: Removed duplicated region for block: B:51:0x00b4  */
        /* JADX WARN: Removed duplicated region for block: B:547:0x09a3  */
        /* JADX WARN: Removed duplicated region for block: B:572:0x0a52  */
        /* JADX WARN: Removed duplicated region for block: B:573:0x0a59  */
        /* JADX WARN: Removed duplicated region for block: B:66:0x00de  */
        /* JADX WARN: Removed duplicated region for block: B:99:0x0179  */
        @Override // com.android.systemui.Gefingerpoken
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            float f;
            NotificationPanelViewController notificationPanelViewController;
            NotificationPanelViewController notificationPanelViewController2;
            boolean z;
            NotificationPanelViewController notificationPanelViewController3;
            StringBuilder sb;
            int i;
            NotificationPanelViewController notificationPanelViewController4;
            QuickPanelLogger quickPanelLogger;
            StringBuilder sb2;
            int pointerId;
            boolean z2;
            SecPanelSplitHelper secPanelSplitHelper;
            SecQuickSettingsControllerImpl secQuickSettingsControllerImpl;
            NonInterceptingScrollView nonInterceptingScrollView;
            boolean z3;
            KeyguardSecBottomAreaView keyguardSecBottomAreaView;
            boolean z4;
            LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController;
            boolean z5;
            SecQuickTileChunkLayoutBarTouchHelper secQuickTileChunkLayoutBarTouchHelper;
            boolean z6;
            boolean z7;
            QuickPanelLogger quickPanelLogger2;
            SecNotificationPanelViewController secNotificationPanelViewController = NotificationPanelViewController.this.mSecNotificationPanelViewController;
            if (secNotificationPanelViewController == null || !secNotificationPanelViewController.isStatusBarWindowViewTouched()) {
                if (NotificationPanelViewController.this.mStatusBarStateController.getState() == 0) {
                    NotificationPanelViewController notificationPanelViewController5 = NotificationPanelViewController.this;
                    if (!notificationPanelViewController5.mUseExternalTouch) {
                        QuickPanelLogger quickPanelLogger3 = notificationPanelViewController5.mQuickPanelLogger;
                        if (quickPanelLogger3 == null) {
                            return false;
                        }
                        quickPanelLogger3.onInterceptTouchEvent(motionEvent, "!mUseExternalTouch", false);
                        return false;
                    }
                }
                NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "NPVC onInterceptTouchEvent");
                QuickPanelLogger quickPanelLogger4 = NotificationPanelViewController.this.mQuickPanelLogger;
                if (quickPanelLogger4 != null) {
                    quickPanelLogger4.onInterceptTouchEvent(motionEvent);
                }
                QS qs = NotificationPanelViewController.this.mQsController.mQs;
                if (qs != null ? qs.disallowPanelTouches() : false) {
                    NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "NPVC not intercepting touch, panel touches disallowed");
                    NotificationPanelViewController notificationPanelViewController6 = NotificationPanelViewController.this;
                    float f2 = notificationPanelViewController6.mExpandedFraction;
                    QuickPanelLogger quickPanelLogger5 = notificationPanelViewController6.mQuickPanelLogger;
                    if (f2 == 1.0f) {
                        if (quickPanelLogger5 == null) {
                            return false;
                        }
                        quickPanelLogger5.onInterceptTouchEvent(motionEvent, "mQsController.disallowTouches()", false);
                        return false;
                    }
                    if (quickPanelLogger5 != null) {
                        quickPanelLogger5.onInterceptTouchEvent(motionEvent, "mQsController.disallowTouches()", true);
                        return true;
                    }
                } else if (LsRune.AOD_FULLSCREEN) {
                    NotificationPanelViewController notificationPanelViewController7 = NotificationPanelViewController.this;
                    if (notificationPanelViewController7.mUnlockedScreenOffAnimationController.lightRevealAnimationPlaying) {
                        QuickPanelLogger quickPanelLogger6 = notificationPanelViewController7.mQuickPanelLogger;
                        if (quickPanelLogger6 != null) {
                            quickPanelLogger6.onInterceptTouchEvent(motionEvent, "unlockedScreenOff animation playing", true);
                            return true;
                        }
                    } else {
                        SecNotificationPanelViewController secNotificationPanelViewController2 = NotificationPanelViewController.this.mSecNotificationPanelViewController;
                        if (secNotificationPanelViewController2 == null) {
                            SecNotificationPanelViewController secNotificationPanelViewController3 = NotificationPanelViewController.this.mSecNotificationPanelViewController;
                            if (secNotificationPanelViewController3 != null) {
                                SecPanelSplitHelper secPanelSplitHelper2 = secNotificationPanelViewController3.panelSplitHelper;
                                if ((secPanelSplitHelper2 != null ? Boolean.valueOf(secPanelSplitHelper2.panelSlideEventHandler.panelSliderIntercepted) : null).booleanValue()) {
                                    QuickPanelLogger quickPanelLogger7 = NotificationPanelViewController.this.mQuickPanelLogger;
                                    if (quickPanelLogger7 == null) {
                                        return false;
                                    }
                                    quickPanelLogger7.onInterceptTouchEvent(motionEvent, "PanelSplit intercepted. No need to intercept from here", false);
                                    return false;
                                }
                            }
                            NotificationPanelViewController.m2945$$Nest$minitDownStates(NotificationPanelViewController.this, motionEvent);
                            NotificationPanelViewController notificationPanelViewController8 = NotificationPanelViewController.this;
                            if (notificationPanelViewController8.mCentralSurfaces.mBouncerShowing) {
                                notificationPanelViewController8.mShadeLog.v("NotificationPanelViewController MotionEvent intercepted: bouncer is showing");
                                QuickPanelLogger quickPanelLogger8 = NotificationPanelViewController.this.mQuickPanelLogger;
                                if (quickPanelLogger8 != null) {
                                    quickPanelLogger8.onInterceptTouchEvent(motionEvent, "mCentralSurfaces.isBouncerShowing()", true);
                                    return true;
                                }
                            } else if (notificationPanelViewController8.mCommandQueue.panelsEnabled()) {
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationPanelViewController.this.mNotificationStackScrollLayoutController;
                                notificationStackScrollLayoutController.getClass();
                                int i2 = SceneContainerFlag.$r8$clinit;
                                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                                if ((!(notificationStackScrollLayoutController.mLongPressedView != null) || motionEvent.getAction() == 0) && NotificationPanelViewController.this.mHeadsUpTouchHelper.onInterceptTouchEvent(motionEvent)) {
                                    NotificationPanelViewController.this.mMetricsLogger.count("panel_open", 1);
                                    NotificationPanelViewController.this.mMetricsLogger.count("panel_open_peek", 1);
                                    NotificationPanelViewController.this.mShadeLog.v("NotificationPanelViewController MotionEvent intercepted: HeadsUpTouchHelper");
                                    QuickPanelLogger quickPanelLogger9 = NotificationPanelViewController.this.mQuickPanelLogger;
                                    if (quickPanelLogger9 != null) {
                                        quickPanelLogger9.onInterceptTouchEvent(motionEvent, "mCommandQueue.panelsEnabled() && !mNotificationStackScrollLayoutController.isLongPressInProgress() && mHeadsUpTouchHelper.onInterceptTouchEvent()", true);
                                        return true;
                                    }
                                } else {
                                    NotificationPanelViewController notificationPanelViewController9 = NotificationPanelViewController.this;
                                    if (notificationPanelViewController9.mHeadsUpTouchHelper.mTouchingHeadsUpView) {
                                        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationPanelViewController9.mNotificationStackScrollLayoutController;
                                        notificationStackScrollLayoutController2.getClass();
                                        int i3 = SceneContainerFlag.$r8$clinit;
                                        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                                        if ((notificationStackScrollLayoutController2.mLongPressedView != null) && motionEvent.getAction() == 2 && (quickPanelLogger2 = NotificationPanelViewController.this.mQuickPanelLogger) != null) {
                                            quickPanelLogger2.onInterceptTouchEvent(motionEvent, "NotiRune.NOTI_AOSP_BUGFIX_NOT_REFER_DELTAY_TOUCH_FOR_DRAG_AND_DROP_HEADS_UP", false);
                                            return false;
                                        }
                                    }
                                    NotificationPanelViewController notificationPanelViewController10 = NotificationPanelViewController.this;
                                    if (notificationPanelViewController10.mSecNotificationPanelViewController == null || notificationPanelViewController10.mPanelSplitHelper.isShadeState()) {
                                        f = 0.0f;
                                        if (motionEvent.getAction() != 0) {
                                            NotificationPanelViewController notificationPanelViewController11 = NotificationPanelViewController.this;
                                            notificationPanelViewController11.shouldScrollViewIntercept = false;
                                            notificationPanelViewController11.mInitialExpandY = motionEvent.getY();
                                        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                                            NotificationPanelViewController.this.shouldScrollViewIntercept = false;
                                        }
                                        if (NotificationPanelViewController.this.isFullyExpanded() || NotificationPanelViewController.this.mPanelSplitHelper.isShadeState()) {
                                            notificationPanelViewController = NotificationPanelViewController.this;
                                            if (notificationPanelViewController.mQsController.shouldQuickSettingsIntercept(notificationPanelViewController.mDownX, notificationPanelViewController.mDownY, f) && NotificationPanelViewController.this.mPulseExpansionHandler.onInterceptTouchEvent(motionEvent)) {
                                                NotificationPanelViewController.this.mShadeLog.v("NotificationPanelViewController MotionEvent intercepted: PulseExpansionHandler");
                                                QuickPanelLogger quickPanelLogger10 = NotificationPanelViewController.this.mQuickPanelLogger;
                                                if (quickPanelLogger10 != null) {
                                                    quickPanelLogger10.onInterceptTouchEvent(motionEvent, "!mQsController.shouldQuickSettingsIntercept() && mPulseExpansionHandler.onInterceptTouchEvent()", true);
                                                    return true;
                                                }
                                            } else {
                                                if (!NotificationPanelViewController.this.isFullyCollapsed() || !NotificationPanelViewController.this.mQsController.onIntercept(motionEvent)) {
                                                    notificationPanelViewController2 = NotificationPanelViewController.this;
                                                    z = notificationPanelViewController2.mInstantExpanding;
                                                    if (!z || !notificationPanelViewController2.mNotificationsDragEnabled || notificationPanelViewController2.mTouchDisabled) {
                                                        boolean z8 = !notificationPanelViewController2.mNotificationsDragEnabled;
                                                        boolean z9 = notificationPanelViewController2.mTouchDisabled;
                                                        ShadeLogger shadeLogger = notificationPanelViewController2.mShadeLog;
                                                        shadeLogger.getClass();
                                                        LogLevel logLevel = LogLevel.VERBOSE;
                                                        ShadeLogger$$ExternalSyntheticLambda0 shadeLogger$$ExternalSyntheticLambda0 = new ShadeLogger$$ExternalSyntheticLambda0(3);
                                                        LogBuffer logBuffer = shadeLogger.buffer;
                                                        LogMessage logMessageObtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$$ExternalSyntheticLambda0, null);
                                                        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                                                        logMessageImpl.bool1 = z;
                                                        logMessageImpl.bool2 = z8;
                                                        logMessageImpl.bool3 = z9;
                                                        logBuffer.commit(logMessageObtain);
                                                        notificationPanelViewController3 = NotificationPanelViewController.this;
                                                        if (notificationPanelViewController3.mQuickPanelLogger != null && (sb = notificationPanelViewController3.mQuickPanelLogBuilder) != null) {
                                                            sb.setLength(0);
                                                            StringBuilder sb3 = NotificationPanelViewController.this.mQuickPanelLogBuilder;
                                                            sb3.append("mInstantExpanding: ");
                                                            sb3.append(NotificationPanelViewController.this.mInstantExpanding);
                                                            sb3.append(" || !mNotificationsDragEnabled: ");
                                                            sb3.append(!NotificationPanelViewController.this.mNotificationsDragEnabled);
                                                            sb3.append(" || mTouchDisabled: ");
                                                            sb3.append(NotificationPanelViewController.this.mTouchDisabled);
                                                            NotificationPanelViewController notificationPanelViewController12 = NotificationPanelViewController.this;
                                                            notificationPanelViewController12.mQuickPanelLogger.onInterceptTouchEvent(motionEvent, notificationPanelViewController12.mQuickPanelLogBuilder.toString(), false);
                                                            return false;
                                                        }
                                                    } else if (!notificationPanelViewController2.mMotionAborted || motionEvent.getActionMasked() == 0) {
                                                        if (NotificationPanelViewController.this.mCommandQueue.panelsEnabled() && !NotificationPanelViewController.this.mQsController.getExpanded() && (lockscreenNotificationIconsOnlyController = NotificationPanelViewController.this.mLockscreenNotificationIconsOnlyController) != null) {
                                                            PluginNotificationController pluginNotificationController = lockscreenNotificationIconsOnlyController.mNotificationControllerWrapper.mNotificationController;
                                                            if (pluginNotificationController != null ? pluginNotificationController.isIconsOnlyInterceptTouchEvent(motionEvent) : false) {
                                                                QuickPanelLogger quickPanelLogger11 = NotificationPanelViewController.this.mQuickPanelLogger;
                                                                if (quickPanelLogger11 != null) {
                                                                    quickPanelLogger11.onInterceptTouchEvent(motionEvent, "LsRune.LOCKUI_NOTI_ICON_TYPE", true);
                                                                    return true;
                                                                }
                                                            }
                                                        }
                                                        PluginLock pluginLock = NotificationPanelViewController.this.mPluginLock;
                                                        if (pluginLock != null && pluginLock.getTouchManager() != null && !NotificationPanelViewController.this.mQsController.getExpanded() && NotificationPanelViewController.this.mStatusBarStateController.getState() == 1) {
                                                            NotificationPanelViewController notificationPanelViewController13 = NotificationPanelViewController.this;
                                                            if (!notificationPanelViewController13.mMediaOutputDetailShowing) {
                                                                if (notificationPanelViewController13.mPluginLock.getTouchManager().isTouchOnItemViewArea(motionEvent)) {
                                                                    NotificationPanelViewController.this.onUserActivity();
                                                                    if (motionEvent.getActionMasked() == 0) {
                                                                        NotificationPanelViewController.this.mPluginLock.getTouchManager().setIntercept(true);
                                                                    }
                                                                    QuickPanelLogger quickPanelLogger12 = NotificationPanelViewController.this.mQuickPanelLogger;
                                                                    if (quickPanelLogger12 != null) {
                                                                        quickPanelLogger12.onInterceptTouchEvent(motionEvent, "LsRune.PLUGIN_LOCK", false);
                                                                        return false;
                                                                    }
                                                                } else {
                                                                    NotificationPanelViewController notificationPanelViewController14 = NotificationPanelViewController.this;
                                                                    if ((notificationPanelViewController14.mPluginLockViewMode == 0) && notificationPanelViewController14.mPluginLock.getTouchManager().isIntercepting()) {
                                                                        NotificationPanelViewController.this.mPluginLock.getTouchManager().setIntercept(false);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (NotificationPanelViewController.this.mMediaOutputDetailShowing) {
                                                            Log.i("NotificationPanelView", "mMediaOutputDetailShowing is true");
                                                        }
                                                        if (CscRune.LOCKUI_BOTTOM_USIM_TEXT && !NotificationPanelViewController.this.mQsController.getExpanded() && NotificationPanelViewController.this.mBarState == 1 && motionEvent.getActionMasked() == 0 && (keyguardSecBottomAreaView = NotificationPanelViewController.this.mKeyguardSecBottomArea) != null && keyguardSecBottomAreaView.isInEmergencyButtonArea(motionEvent)) {
                                                            QuickPanelLogger quickPanelLogger13 = NotificationPanelViewController.this.mQuickPanelLogger;
                                                            if (quickPanelLogger13 != null) {
                                                                z4 = false;
                                                                quickPanelLogger13.onInterceptTouchEvent(motionEvent, "CscRune.LOCKUI_BOTTOM_USIM_TEXT", false);
                                                            } else {
                                                                z4 = false;
                                                            }
                                                            NotificationPanelViewController.this.setMotionAborted();
                                                            return z4;
                                                        }
                                                        int actionMasked = motionEvent.getActionMasked();
                                                        NotificationPanelViewController notificationPanelViewController15 = NotificationPanelViewController.this;
                                                        notificationPanelViewController15.mQsExpandedOnTouchDown = notificationPanelViewController15.mQsController.getExpanded() || NotificationPanelViewController.this.mQsController.mFullyExpanded;
                                                        if (!NotificationPanelViewController.this.mQsController.getExpanded()) {
                                                            NotificationPanelViewController notificationPanelViewController16 = NotificationPanelViewController.this;
                                                            if (notificationPanelViewController16.mBarState == 1) {
                                                                Lazy lazy = notificationPanelViewController16.mPluginLockStarManagerLazy;
                                                                float x = motionEvent.getX();
                                                                float y = motionEvent.getY();
                                                                PluginKeyguardStatusView pluginKeyguardStatusView = notificationPanelViewController16.mKeyguardStatusBase.mPluginKeyguardStatusView;
                                                                if (!(pluginKeyguardStatusView != null ? pluginKeyguardStatusView.isInContentBounds(x, y) : false)) {
                                                                    if (!notificationPanelViewController16.mAccessibilityManager.isEnabled()) {
                                                                    }
                                                                    View viewFindViewById = notificationPanelViewController16.mView.findViewById(R.id.sec_lock_icon_view);
                                                                    if (viewFindViewById != null) {
                                                                        float x2 = viewFindViewById.getX();
                                                                        float y2 = viewFindViewById.getY();
                                                                        if (viewFindViewById.getVisibility() == 0) {
                                                                            if (x2 < x) {
                                                                                if (x < x2 + viewFindViewById.getWidth()) {
                                                                                    if (y2 < y) {
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                    if (CscRune.KEYGUARD_DCM_LIVE_UX) {
                                                                        DcmMascotViewContainer dcmMascotViewContainer = notificationPanelViewController16.mMascotViewContainer;
                                                                        if (dcmMascotViewContainer.getVisibility() == 0) {
                                                                            int x3 = (int) dcmMascotViewContainer.getX();
                                                                            int y3 = (int) dcmMascotViewContainer.getY();
                                                                            if (x3 < x) {
                                                                                if (x < dcmMascotViewContainer.getWidth() + x3) {
                                                                                    if (y3 < y) {
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                    try {
                                                                        if (notificationPanelViewController16.mLockStarEnabled && lazy.get() != null && ((PluginLockStarManager) lazy.get()).isTouchable(motionEvent)) {
                                                                            LogUtil.dm("NotificationPanelView", "isTouchOnEmptyArea on lockstar item", new Object[0]);
                                                                        }
                                                                    } catch (Throwable unused) {
                                                                        Log.e("NotificationPanelView", "isTouchOnEmptyArea() error in Lockstar");
                                                                    }
                                                                    if (notificationPanelViewController16.mLockStarEnabled) {
                                                                        LogUtil.dm("NotificationPanelView", "isTouchOnEmptyArea belowClock false", new Object[0]);
                                                                    }
                                                                    if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowNotificationOnKeyguard()) {
                                                                        NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationPanelViewController16.mNotificationStackScrollLayoutController;
                                                                        boolean z10 = true;
                                                                        for (int childCount = notificationStackScrollLayoutController3.mView.getChildCount() - 1; childCount >= 0; childCount--) {
                                                                            ExpandableView expandableView = (ExpandableView) notificationStackScrollLayoutController3.mView.getChildAt(childCount);
                                                                            if (expandableView instanceof ExpandableNotificationRow) {
                                                                                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
                                                                                if (expandableNotificationRow.getVisibility() != 8 && expandableNotificationRow.getY() < y) {
                                                                                    z10 = false;
                                                                                }
                                                                            }
                                                                        }
                                                                        i = 8;
                                                                        int notGoneChildCount = notificationStackScrollLayoutController3.getNotGoneChildCount();
                                                                        boolean zIsInContentBounds$1 = notificationPanelViewController16.isInContentBounds$1(x, y);
                                                                        z3 = notGoneChildCount <= 0 || !zIsInContentBounds$1 || z10;
                                                                        if (!z3) {
                                                                            LogUtil.d("NotificationPanelView", "isTouchOnEmptyArea return %s: notGoneChildCount() %s, isInContentBounds %s", Boolean.valueOf(z3), Integer.valueOf(notGoneChildCount), Boolean.valueOf(zIsInContentBounds$1));
                                                                        }
                                                                    } else {
                                                                        LogUtil.dm("NotificationPanelView", "isTouchOnEmptyArea returns true", new Object[0]);
                                                                        z3 = true;
                                                                        i = 8;
                                                                    }
                                                                    if (z3) {
                                                                        ExpandableView childAtRawPosition = NotificationPanelViewController.this.mNotificationStackScrollLayoutController.mView.getChildAtRawPosition(motionEvent.getX(), motionEvent.getY());
                                                                        NotificationPanelViewController notificationPanelViewController17 = NotificationPanelViewController.this;
                                                                        if (childAtRawPosition != notificationPanelViewController17.mShelfManager.shelf) {
                                                                            if (actionMasked == 0) {
                                                                                notificationPanelViewController17.mKeyguardTouchAnimator.setIntercept(true);
                                                                            }
                                                                        }
                                                                        if (motionEvent.getAction() != 2) {
                                                                            LogUtil.d("KeyguardTouchAnimator", "intercepted: action=%d mQsExpanded=%b, mQsFullyExpanded=%b", Integer.valueOf(actionMasked), Boolean.valueOf(NotificationPanelViewController.this.mQsController.getExpanded()), Boolean.valueOf(NotificationPanelViewController.this.mQsController.mFullyExpanded));
                                                                        }
                                                                        notificationPanelViewController4 = NotificationPanelViewController.this;
                                                                        if (notificationPanelViewController4.mKeyguardTouchAnimator.intercepting) {
                                                                            QuickPanelLogger quickPanelLogger14 = notificationPanelViewController4.mQuickPanelLogger;
                                                                            if (quickPanelLogger14 != null) {
                                                                                quickPanelLogger14.onInterceptTouchEvent(motionEvent, "LsRune.KEYGUARD_ALL_DIRECTIONS_SWIPE_UNLOCK", true);
                                                                                return true;
                                                                            }
                                                                        } else {
                                                                            SecNotificationPanelViewController secNotificationPanelViewController4 = notificationPanelViewController4.mSecNotificationPanelViewController;
                                                                            if (secNotificationPanelViewController4 != null) {
                                                                                SecPanelSplitHelper.Companion.getClass();
                                                                                if (SecPanelSplitHelper.isEnabled && (secPanelSplitHelper = secNotificationPanelViewController4.panelSplitHelper) != null && secPanelSplitHelper.isQSState() && (secQuickSettingsControllerImpl = secNotificationPanelViewController4.secQuickSettingsControllerImpl) != null && (nonInterceptingScrollView = secQuickSettingsControllerImpl.getNonInterceptingScrollView()) != null && nonInterceptingScrollView.getScrollRange() > 0) {
                                                                                    NonInterceptingScrollView nonInterceptingScrollView2 = secQuickSettingsControllerImpl.getNonInterceptingScrollView();
                                                                                    z2 = nonInterceptingScrollView2 != null ? nonInterceptingScrollView2.canScrollVertically(1) : false;
                                                                                    if (!z2) {
                                                                                        QuickPanelLogger quickPanelLogger15 = NotificationPanelViewController.this.mQuickPanelLogger;
                                                                                        if (quickPanelLogger15 != null) {
                                                                                            quickPanelLogger15.onInterceptTouchEvent(motionEvent, "canQsScrollUp()", false);
                                                                                            return false;
                                                                                        }
                                                                                    }
                                                                                }
                                                                                if (!z2) {
                                                                                }
                                                                            }
                                                                            NotificationPanelViewController notificationPanelViewController18 = NotificationPanelViewController.this;
                                                                            if (notificationPanelViewController18.mFullScreenModeEnabled) {
                                                                                QuickPanelLogger quickPanelLogger16 = notificationPanelViewController18.mQuickPanelLogger;
                                                                                if (quickPanelLogger16 != null) {
                                                                                    quickPanelLogger16.onInterceptTouchEvent(motionEvent, "LsRune.LOCKUI_FACE_WIDGET", false);
                                                                                    return false;
                                                                                }
                                                                            } else {
                                                                                if (notificationPanelViewController18.isInFaceWidgetContainer(motionEvent) && !NotificationPanelViewController.this.mQsController.getExpanded()) {
                                                                                    NotificationPanelViewController notificationPanelViewController19 = NotificationPanelViewController.this;
                                                                                    if (notificationPanelViewController19.mBarState == 1) {
                                                                                        View view = notificationPanelViewController19.mKeyguardStatusBase.mFaceWidgetContainer;
                                                                                        if ((view != null ? view.getVisibility() : i) == 0) {
                                                                                            View view2 = NotificationPanelViewController.this.mKeyguardStatusBase.mFaceWidgetContainer;
                                                                                            boolean zOnInterceptTouchEvent = (view2 == null || !(view2 instanceof ViewGroup)) ? false : ((ViewGroup) view2).onInterceptTouchEvent(motionEvent);
                                                                                            QuickPanelLogger quickPanelLogger17 = NotificationPanelViewController.this.mQuickPanelLogger;
                                                                                            if (quickPanelLogger17 != null) {
                                                                                                quickPanelLogger17.onInterceptTouchEvent(motionEvent, "LsRune.LOCKUI_FACE_WIDGET", zOnInterceptTouchEvent);
                                                                                            }
                                                                                            return zOnInterceptTouchEvent;
                                                                                        }
                                                                                    }
                                                                                }
                                                                                try {
                                                                                    StringBuilder sb4 = new StringBuilder("onInterceptTouchEvent: mLockStarEnabled=");
                                                                                    sb4.append(NotificationPanelViewController.this.mLockStarEnabled);
                                                                                    sb4.append(", isInLockStarContainer(event) = ");
                                                                                    sb4.append(NotificationPanelViewController.this.isInLockStarContainer(motionEvent));
                                                                                    sb4.append(", pand = ");
                                                                                    sb4.append(!NotificationPanelViewController.this.mQsController.getExpanded());
                                                                                    Log.i("NotificationPanelView", sb4.toString());
                                                                                    NotificationPanelViewController notificationPanelViewController20 = NotificationPanelViewController.this;
                                                                                    if (notificationPanelViewController20.mLockStarEnabled && notificationPanelViewController20.isInLockStarContainer(motionEvent) && !NotificationPanelViewController.this.mQsController.getExpanded()) {
                                                                                        NotificationPanelViewController notificationPanelViewController21 = NotificationPanelViewController.this;
                                                                                        if (notificationPanelViewController21.mBarState == 1 && notificationPanelViewController21.mPluginLockStarContainer.getVisibility() == 0 && NotificationPanelViewController.this.mPluginLockStarManagerLazy.get() != null) {
                                                                                            boolean zOnInterceptTouchEvent2 = ((PluginLockStarManager) NotificationPanelViewController.this.mPluginLockStarManagerLazy.get()).onInterceptTouchEvent(motionEvent);
                                                                                            QuickPanelLogger quickPanelLogger18 = NotificationPanelViewController.this.mQuickPanelLogger;
                                                                                            if (quickPanelLogger18 == null) {
                                                                                                return zOnInterceptTouchEvent2;
                                                                                            }
                                                                                            quickPanelLogger18.onInterceptTouchEvent(motionEvent, "LsRune.PLUGIN_LOCK_STAR", zOnInterceptTouchEvent2);
                                                                                            return zOnInterceptTouchEvent2;
                                                                                        }
                                                                                    }
                                                                                } catch (Throwable th) {
                                                                                    Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                                                                                    Log.e("NotificationPanelView", "onInterceptTouchEvent() error in LockStar - " + th.getMessage());
                                                                                }
                                                                                int iFindPointerIndex = motionEvent.findPointerIndex(NotificationPanelViewController.this.mTrackingPointer);
                                                                                if (iFindPointerIndex < 0) {
                                                                                    NotificationPanelViewController.this.mTrackingPointer = motionEvent.getPointerId(0);
                                                                                    iFindPointerIndex = 0;
                                                                                }
                                                                                float x4 = motionEvent.getX(iFindPointerIndex);
                                                                                float y4 = motionEvent.getY(iFindPointerIndex);
                                                                                boolean zCanCollapsePanelOnTouch = NotificationPanelViewController.this.canCollapsePanelOnTouch();
                                                                                boolean zIsTrackpadThreeFingerSwipe = Utilities.isTrackpadThreeFingerSwipe(motionEvent);
                                                                                int actionMasked2 = motionEvent.getActionMasked();
                                                                                if (actionMasked2 == 0) {
                                                                                    NotificationPanelViewController notificationPanelViewController22 = NotificationPanelViewController.this;
                                                                                    notificationPanelViewController22.mAnimatingOnDown = (notificationPanelViewController22.mHeightAnimator == null || notificationPanelViewController22.mIsSpringBackAnimation) ? false : true;
                                                                                    notificationPanelViewController22.getClass();
                                                                                    NotificationPanelViewController notificationPanelViewController23 = NotificationPanelViewController.this;
                                                                                    notificationPanelViewController23.mDownTime = notificationPanelViewController23.mSystemClock.uptimeMillis();
                                                                                    NotificationPanelViewController notificationPanelViewController24 = NotificationPanelViewController.this;
                                                                                    if (notificationPanelViewController24.mAnimatingOnDown && notificationPanelViewController24.isClosing()) {
                                                                                        NotificationPanelViewController.this.cancelHeightAnimator();
                                                                                        NotificationPanelViewController notificationPanelViewController25 = NotificationPanelViewController.this;
                                                                                        notificationPanelViewController25.mTouchSlopExceeded = true;
                                                                                        notificationPanelViewController25.mShadeLog.v("NotificationPanelViewController MotionEvent intercepted: mAnimatingOnDown: true, isClosing(): true");
                                                                                        QuickPanelLogger quickPanelLogger19 = NotificationPanelViewController.this.mQuickPanelLogger;
                                                                                        if (quickPanelLogger19 != null) {
                                                                                            quickPanelLogger19.onInterceptTouchEvent(motionEvent, "mAnimationOnDown && isClosing()", true);
                                                                                        }
                                                                                    } else {
                                                                                        if (!NotificationPanelViewController.this.isTracking() || NotificationPanelViewController.this.isFullyCollapsed()) {
                                                                                            NotificationPanelViewController notificationPanelViewController26 = NotificationPanelViewController.this;
                                                                                            notificationPanelViewController26.mInitialExpandY = y4;
                                                                                            notificationPanelViewController26.mInitialExpandX = x4;
                                                                                        } else {
                                                                                            NotificationPanelViewController.this.mShadeLog.d("not setting mInitialExpandY in onInterceptTouch");
                                                                                        }
                                                                                        NotificationPanelViewController.this.mTouchStartedInEmptyArea = !r4.isInContentBounds$1(x4, y4);
                                                                                        NotificationPanelViewController notificationPanelViewController27 = NotificationPanelViewController.this;
                                                                                        notificationPanelViewController27.mTouchSlopExceeded = notificationPanelViewController27.mTouchSlopExceededBeforeDown;
                                                                                        notificationPanelViewController27.mMotionAborted = false;
                                                                                        notificationPanelViewController27.mPanelClosedOnDown = notificationPanelViewController27.isFullyCollapsed();
                                                                                        NotificationPanelViewController notificationPanelViewController28 = NotificationPanelViewController.this;
                                                                                        notificationPanelViewController28.mShadeLog.logPanelClosedOnDown("intercept down touch", notificationPanelViewController28.mPanelClosedOnDown, notificationPanelViewController28.mExpandedFraction);
                                                                                        NotificationPanelViewController notificationPanelViewController29 = NotificationPanelViewController.this;
                                                                                        notificationPanelViewController29.mCollapsedAndHeadsUpOnDown = false;
                                                                                        notificationPanelViewController29.mHasLayoutedSinceDown = false;
                                                                                        notificationPanelViewController29.mUpdateFlingOnLayout = false;
                                                                                        notificationPanelViewController29.mTouchAboveFalsingThreshold = false;
                                                                                        notificationPanelViewController29.mHeadsUpVisibleOnDown = ((HeadsUpManagerImpl) notificationPanelViewController29.mHeadsUpManager).mHasPinnedNotification;
                                                                                        NotificationPanelViewController.m2943$$Nest$maddMovement(notificationPanelViewController29, motionEvent);
                                                                                        quickPanelLogger = NotificationPanelViewController.this.mQuickPanelLogger;
                                                                                        if (quickPanelLogger != null) {
                                                                                        }
                                                                                    }
                                                                                } else if (actionMasked2 == 1) {
                                                                                    NotificationPanelViewController.this.mVelocityTracker.clear();
                                                                                    quickPanelLogger = NotificationPanelViewController.this.mQuickPanelLogger;
                                                                                    if (quickPanelLogger != null) {
                                                                                        quickPanelLogger.onInterceptTouchEvent(motionEvent, "FINAL", false);
                                                                                        return false;
                                                                                    }
                                                                                } else {
                                                                                    if (actionMasked2 == 2) {
                                                                                        NotificationPanelViewController notificationPanelViewController30 = NotificationPanelViewController.this;
                                                                                        float f3 = y4 - notificationPanelViewController30.mInitialExpandY;
                                                                                        NotificationPanelViewController.m2943$$Nest$maddMovement(notificationPanelViewController30, motionEvent);
                                                                                        NotificationPanelViewController notificationPanelViewController31 = NotificationPanelViewController.this;
                                                                                        boolean z11 = notificationPanelViewController31.mPanelClosedOnDown && !notificationPanelViewController31.mCollapsedAndHeadsUpOnDown;
                                                                                        if (zCanCollapsePanelOnTouch || notificationPanelViewController31.mTouchStartedInEmptyArea || notificationPanelViewController31.mAnimatingOnDown || z11) {
                                                                                            float fAbs = Math.abs(f3);
                                                                                            float touchSlop$1 = NotificationPanelViewController.this.getTouchSlop$1(motionEvent);
                                                                                            float f4 = -touchSlop$1;
                                                                                            if ((f3 < f4 || ((z11 || NotificationPanelViewController.this.mAnimatingOnDown) && fAbs > touchSlop$1)) && fAbs > Math.abs(x4 - NotificationPanelViewController.this.mInitialExpandX)) {
                                                                                                NotificationPanelViewController.this.cancelHeightAnimator();
                                                                                                NotificationPanelViewController notificationPanelViewController32 = NotificationPanelViewController.this;
                                                                                                NotificationPanelViewController.m2946$$Nest$mstartExpandMotion(notificationPanelViewController32, x4, y4, true, notificationPanelViewController32.mExpandedHeight);
                                                                                                NotificationPanelViewController.this.mShadeLog.v("NotificationPanelViewController MotionEvent intercepted: startExpandMotion");
                                                                                                NotificationPanelViewController notificationPanelViewController33 = NotificationPanelViewController.this;
                                                                                                if (notificationPanelViewController33.mQuickPanelLogger != null && (sb2 = notificationPanelViewController33.mQuickPanelLogBuilder) != null) {
                                                                                                    sb2.setLength(0);
                                                                                                    StringBuilder sb5 = NotificationPanelViewController.this.mQuickPanelLogBuilder;
                                                                                                    sb5.append("(h: ");
                                                                                                    sb5.append(f3);
                                                                                                    sb5.append(" < -touchSlop: ");
                                                                                                    sb5.append(f4);
                                                                                                    sb5.append(" || ((openShadeWithoutHun: ");
                                                                                                    sb5.append(z11);
                                                                                                    sb5.append(" || mAnimatingOnDown: ");
                                                                                                    sb5.append(NotificationPanelViewController.this.mAnimatingOnDown);
                                                                                                    sb5.append(") && hAbs: ");
                                                                                                    sb5.append(fAbs);
                                                                                                    sb5.append(" > touchSlop: ");
                                                                                                    sb5.append(touchSlop$1);
                                                                                                    sb5.append(")) && hAbs: ");
                                                                                                    sb5.append(fAbs);
                                                                                                    sb5.append(" > abs(x-mInitialExpandX): ");
                                                                                                    sb5.append(Math.abs(x4 - NotificationPanelViewController.this.mInitialExpandX));
                                                                                                    sb5.append("))");
                                                                                                    NotificationPanelViewController notificationPanelViewController34 = NotificationPanelViewController.this;
                                                                                                    notificationPanelViewController34.mQuickPanelLogger.onInterceptTouchEvent(motionEvent, notificationPanelViewController34.mQuickPanelLogBuilder.toString(), true);
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    } else if (actionMasked2 != 3) {
                                                                                        if (actionMasked2 == 5) {
                                                                                            NotificationPanelViewController notificationPanelViewController35 = NotificationPanelViewController.this;
                                                                                            notificationPanelViewController35.mShadeLog.logMotionEventStatusBarState(motionEvent, notificationPanelViewController35.mStatusBarStateController.getState(), "onInterceptTouchEvent: pointer down action");
                                                                                            if (!zIsTrackpadThreeFingerSwipe && NotificationPanelViewController.this.mStatusBarStateController.getState() == 1) {
                                                                                                NotificationPanelViewController notificationPanelViewController36 = NotificationPanelViewController.this;
                                                                                                notificationPanelViewController36.mMotionAborted = true;
                                                                                                notificationPanelViewController36.mVelocityTracker.clear();
                                                                                            }
                                                                                        } else if (actionMasked2 == 6 && !zIsTrackpadThreeFingerSwipe && NotificationPanelViewController.this.mTrackingPointer == (pointerId = motionEvent.getPointerId(motionEvent.getActionIndex()))) {
                                                                                            int i4 = motionEvent.getPointerId(0) != pointerId ? 0 : 1;
                                                                                            NotificationPanelViewController.this.mTrackingPointer = motionEvent.getPointerId(i4);
                                                                                            NotificationPanelViewController.this.mInitialExpandX = motionEvent.getX(i4);
                                                                                            NotificationPanelViewController.this.mInitialExpandY = motionEvent.getY(i4);
                                                                                        }
                                                                                    }
                                                                                    quickPanelLogger = NotificationPanelViewController.this.mQuickPanelLogger;
                                                                                    if (quickPanelLogger != null) {
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                i = 8;
                                                                z3 = false;
                                                                if (z3) {
                                                                }
                                                            }
                                                            NotificationPanelViewController.this.mKeyguardTouchAnimator.setIntercept(false);
                                                            if (motionEvent.getAction() != 2) {
                                                            }
                                                            notificationPanelViewController4 = NotificationPanelViewController.this;
                                                            if (notificationPanelViewController4.mKeyguardTouchAnimator.intercepting) {
                                                            }
                                                        }
                                                        i = 8;
                                                        NotificationPanelViewController.this.mKeyguardTouchAnimator.setIntercept(false);
                                                        if (motionEvent.getAction() != 2) {
                                                        }
                                                        notificationPanelViewController4 = NotificationPanelViewController.this;
                                                        if (notificationPanelViewController4.mKeyguardTouchAnimator.intercepting) {
                                                        }
                                                    } else {
                                                        NotificationPanelViewController notificationPanelViewController37 = NotificationPanelViewController.this;
                                                        notificationPanelViewController37.mShadeLog.logMotionEventStatusBarState(motionEvent, notificationPanelViewController37.mStatusBarStateController.getState(), "NPVC MotionEvent not intercepted: non-down action, motion was aborted");
                                                        QuickPanelLogger quickPanelLogger20 = NotificationPanelViewController.this.mQuickPanelLogger;
                                                        if (quickPanelLogger20 != null) {
                                                            quickPanelLogger20.onInterceptTouchEvent(motionEvent, "mMotionAborted && event.getActionMasked() != ACTION_DOWN", false);
                                                            return false;
                                                        }
                                                    }
                                                    return false;
                                                }
                                                NotificationPanelViewController.this.getClass();
                                                NotificationPanelViewController.this.mShadeLog.v("NotificationPanelViewController MotionEvent intercepted: QsIntercept");
                                                QuickPanelLogger quickPanelLogger21 = NotificationPanelViewController.this.mQuickPanelLogger;
                                                if (quickPanelLogger21 != null) {
                                                    quickPanelLogger21.onInterceptTouchEvent(motionEvent, "!isFullyCollapsed() && mQsController.onIntercept()", true);
                                                    return true;
                                                }
                                            }
                                        } else {
                                            QuickSettingsControllerImpl quickSettingsControllerImpl = NotificationPanelViewController.this.mQsController;
                                            if (quickSettingsControllerImpl.mFullyExpanded) {
                                                if (quickSettingsControllerImpl.mSecQuickSettingsControllerImpl.checkIfScrollEnabled(motionEvent.getY() - NotificationPanelViewController.this.mInitialExpandY, r8.mTouchSlop)) {
                                                    NotificationPanelViewController notificationPanelViewController38 = NotificationPanelViewController.this;
                                                    notificationPanelViewController38.shouldScrollViewIntercept = true;
                                                    SecQuickSettingsControllerImpl secQuickSettingsControllerImpl2 = notificationPanelViewController38.mQsController.mSecQuickSettingsControllerImpl;
                                                    secQuickSettingsControllerImpl2.updateScrollViewLocationDelta();
                                                    MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
                                                    motionEventObtain.offsetLocation(secQuickSettingsControllerImpl2.deltaX, secQuickSettingsControllerImpl2.deltaY);
                                                    NonInterceptingScrollView nonInterceptingScrollView3 = secQuickSettingsControllerImpl2.getNonInterceptingScrollView();
                                                    if (nonInterceptingScrollView3 != null) {
                                                        nonInterceptingScrollView3.dispatchTouchEvent(motionEventObtain);
                                                    }
                                                    QuickPanelLogger quickPanelLogger22 = NotificationPanelViewController.this.mQuickPanelLogger;
                                                    if (quickPanelLogger22 != null) {
                                                        quickPanelLogger22.onInterceptTouchEvent(motionEvent, "shouldScrollViewIntercept true", true);
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        NotificationPanelViewController notificationPanelViewController39 = NotificationPanelViewController.this;
                                        if (notificationPanelViewController39.mQsController.mFullyExpanded) {
                                            SecQuickSettingsControllerImpl secQuickSettingsControllerImpl3 = notificationPanelViewController39.mSecNotificationPanelViewController.secQuickSettingsControllerImpl;
                                            if (secQuickSettingsControllerImpl3 == null || (secQuickTileChunkLayoutBarTouchHelper = secQuickSettingsControllerImpl3.tileChunkLayoutBarTouchHelper) == null) {
                                                f = 0.0f;
                                                z5 = false;
                                            } else {
                                                secQuickTileChunkLayoutBarTouchHelper.updateChunkLayoutBar(motionEvent);
                                                if (motionEvent.getActionMasked() == 0) {
                                                    float x5 = motionEvent.getX();
                                                    float y5 = motionEvent.getY();
                                                    TileChunkLayoutBar tileChunkLayoutBar = secQuickTileChunkLayoutBarTouchHelper.tileChunkLayoutBar;
                                                    if (tileChunkLayoutBar != null) {
                                                        TileChunkLayoutBarExpandHelper tileChunkLayoutBarExpandHelper = secQuickTileChunkLayoutBarTouchHelper.expandHelper;
                                                        View view3 = (tileChunkLayoutBarExpandHelper == null || !tileChunkLayoutBarExpandHelper.tileChunkLayoutBar.mIsExpanded) ? tileChunkLayoutBar.mBarRootView : tileChunkLayoutBar.mScrollIndicatorClickContainer;
                                                        int[] iArr = new int[2];
                                                        f = 0.0f;
                                                        notificationPanelViewController39.mView.getLocationOnScreen(iArr);
                                                        int[] iArr2 = new int[2];
                                                        view3.getLocationOnScreen(iArr2);
                                                        int i5 = iArr2[0] - iArr[0];
                                                        int[] iArr3 = new int[2];
                                                        view3.getLocationOnScreen(iArr3);
                                                        int i6 = iArr3[1] - iArr[1];
                                                        if (secQuickTileChunkLayoutBarTouchHelper.qsExpandedSupplier.getAsBoolean()) {
                                                            float f5 = i5;
                                                            if (x5 <= i5 + view3.getWidth() && f5 <= x5) {
                                                                float f6 = i6;
                                                                if (y5 <= i6 + view3.getHeight() && f6 <= y5) {
                                                                    z7 = true;
                                                                }
                                                                secQuickTileChunkLayoutBarTouchHelper.actionDownStartInChunkBar = z7;
                                                            }
                                                        }
                                                    } else {
                                                        f = 0.0f;
                                                    }
                                                    z7 = false;
                                                    secQuickTileChunkLayoutBarTouchHelper.actionDownStartInChunkBar = z7;
                                                } else {
                                                    f = 0.0f;
                                                }
                                                if (secQuickTileChunkLayoutBarTouchHelper.actionDownStartInChunkBar) {
                                                    Rect rect2 = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                                                    if (notificationPanelViewController39.isOnKeyguard()) {
                                                        z6 = false;
                                                        z5 = z6;
                                                        if (motionEvent.getActionMasked() == 3 || motionEvent.getActionMasked() == 1) {
                                                            secQuickTileChunkLayoutBarTouchHelper.actionDownStartInChunkBar = false;
                                                        }
                                                    } else {
                                                        int iPreparePointerIndex = secQuickTileChunkLayoutBarTouchHelper.preparePointerIndex(motionEvent);
                                                        float x6 = motionEvent.getX(iPreparePointerIndex);
                                                        float y6 = motionEvent.getY(iPreparePointerIndex);
                                                        secQuickTileChunkLayoutBarTouchHelper.trackMovementConsumer.accept(motionEvent);
                                                        int actionMasked3 = motionEvent.getActionMasked();
                                                        if (actionMasked3 == 0) {
                                                            TileChunkLayoutBarExpandHelper tileChunkLayoutBarExpandHelper2 = secQuickTileChunkLayoutBarTouchHelper.expandHelper;
                                                            if (tileChunkLayoutBarExpandHelper2 != null) {
                                                                tileChunkLayoutBarExpandHelper2.setTracking((float) secQuickTileChunkLayoutBarTouchHelper.currentQsVelocitySupplier.getAsDouble(), true);
                                                            }
                                                            secQuickTileChunkLayoutBarTouchHelper.initVelocityTrackerRunnable.run();
                                                        } else if (actionMasked3 == 1) {
                                                            secQuickTileChunkLayoutBarTouchHelper.isExpanding = false;
                                                            TileChunkLayoutBarExpandHelper tileChunkLayoutBarExpandHelper3 = secQuickTileChunkLayoutBarTouchHelper.expandHelper;
                                                            z6 = tileChunkLayoutBarExpandHelper3 == null && tileChunkLayoutBarExpandHelper3.setTracking((float) secQuickTileChunkLayoutBarTouchHelper.currentQsVelocitySupplier.getAsDouble(), false);
                                                            secQuickTileChunkLayoutBarTouchHelper.clearVelocityTrackerRunnable.run();
                                                            if (z6) {
                                                            }
                                                            if (motionEvent.getActionMasked() == 3) {
                                                                secQuickTileChunkLayoutBarTouchHelper.actionDownStartInChunkBar = false;
                                                            }
                                                        } else if (actionMasked3 != 2) {
                                                            if (actionMasked3 != 3) {
                                                                if (actionMasked3 == 6) {
                                                                    if (secQuickTileChunkLayoutBarTouchHelper.trackingPointerSupplier.getAsInt() == motionEvent.getPointerId(motionEvent.getActionIndex())) {
                                                                        motionEvent.getPointerId(0);
                                                                    }
                                                                }
                                                            }
                                                            secQuickTileChunkLayoutBarTouchHelper.isExpanding = false;
                                                            TileChunkLayoutBarExpandHelper tileChunkLayoutBarExpandHelper32 = secQuickTileChunkLayoutBarTouchHelper.expandHelper;
                                                            if (tileChunkLayoutBarExpandHelper32 == null) {
                                                                secQuickTileChunkLayoutBarTouchHelper.clearVelocityTrackerRunnable.run();
                                                                if (z6) {
                                                                }
                                                                if (motionEvent.getActionMasked() == 3) {
                                                                }
                                                            }
                                                        } else {
                                                            float asDouble = y6 - ((float) secQuickTileChunkLayoutBarTouchHelper.initialTouchYSupplier.getAsDouble());
                                                            secQuickTileChunkLayoutBarTouchHelper.draggedHeight = asDouble;
                                                            if (Math.abs(asDouble) > notificationPanelViewController39.getTouchSlop$1(motionEvent) && Math.abs(secQuickTileChunkLayoutBarTouchHelper.draggedHeight) > Math.abs(x6 - ((float) secQuickTileChunkLayoutBarTouchHelper.initialTouchXSupplier.getAsDouble()))) {
                                                                int iPreparePointerIndex2 = secQuickTileChunkLayoutBarTouchHelper.preparePointerIndex(motionEvent);
                                                                motionEvent.getX(iPreparePointerIndex2);
                                                                motionEvent.getY(iPreparePointerIndex2);
                                                                TileChunkLayoutBarExpandHelper tileChunkLayoutBarExpandHelper4 = secQuickTileChunkLayoutBarTouchHelper.expandHelper;
                                                                if (tileChunkLayoutBarExpandHelper4 == null || !tileChunkLayoutBarExpandHelper4.tileChunkLayoutBar.mIsExpanded ? secQuickTileChunkLayoutBarTouchHelper.draggedHeight > f : secQuickTileChunkLayoutBarTouchHelper.draggedHeight <= f) {
                                                                    secQuickTileChunkLayoutBarTouchHelper.isExpanding = true;
                                                                    z6 = true;
                                                                }
                                                                if (z6) {
                                                                }
                                                                if (motionEvent.getActionMasked() == 3) {
                                                                }
                                                            }
                                                        }
                                                        z6 = false;
                                                        if (z6) {
                                                        }
                                                        if (motionEvent.getActionMasked() == 3) {
                                                        }
                                                    }
                                                }
                                            }
                                            if (z5) {
                                                QuickPanelLogger quickPanelLogger23 = NotificationPanelViewController.this.mQuickPanelLogger;
                                                if (quickPanelLogger23 != null) {
                                                    quickPanelLogger23.onInterceptTouchEvent(motionEvent, "onInterceptTouchEventForTileChunkLayoutBar()", true);
                                                    return true;
                                                }
                                            }
                                        }
                                        if (motionEvent.getAction() != 0) {
                                        }
                                        if (NotificationPanelViewController.this.isFullyExpanded()) {
                                            notificationPanelViewController = NotificationPanelViewController.this;
                                            if (notificationPanelViewController.mQsController.shouldQuickSettingsIntercept(notificationPanelViewController.mDownX, notificationPanelViewController.mDownY, f)) {
                                                if (!NotificationPanelViewController.this.isFullyCollapsed()) {
                                                    notificationPanelViewController2 = NotificationPanelViewController.this;
                                                    z = notificationPanelViewController2.mInstantExpanding;
                                                    if (!z) {
                                                        boolean z82 = !notificationPanelViewController2.mNotificationsDragEnabled;
                                                        boolean z92 = notificationPanelViewController2.mTouchDisabled;
                                                        ShadeLogger shadeLogger2 = notificationPanelViewController2.mShadeLog;
                                                        shadeLogger2.getClass();
                                                        LogLevel logLevel2 = LogLevel.VERBOSE;
                                                        ShadeLogger$$ExternalSyntheticLambda0 shadeLogger$$ExternalSyntheticLambda02 = new ShadeLogger$$ExternalSyntheticLambda0(3);
                                                        LogBuffer logBuffer2 = shadeLogger2.buffer;
                                                        LogMessage logMessageObtain2 = logBuffer2.obtain("systemui.shade", logLevel2, shadeLogger$$ExternalSyntheticLambda02, null);
                                                        LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
                                                        logMessageImpl2.bool1 = z;
                                                        logMessageImpl2.bool2 = z82;
                                                        logMessageImpl2.bool3 = z92;
                                                        logBuffer2.commit(logMessageObtain2);
                                                        notificationPanelViewController3 = NotificationPanelViewController.this;
                                                        if (notificationPanelViewController3.mQuickPanelLogger != null) {
                                                            sb.setLength(0);
                                                            StringBuilder sb32 = NotificationPanelViewController.this.mQuickPanelLogBuilder;
                                                            sb32.append("mInstantExpanding: ");
                                                            sb32.append(NotificationPanelViewController.this.mInstantExpanding);
                                                            sb32.append(" || !mNotificationsDragEnabled: ");
                                                            sb32.append(!NotificationPanelViewController.this.mNotificationsDragEnabled);
                                                            sb32.append(" || mTouchDisabled: ");
                                                            sb32.append(NotificationPanelViewController.this.mTouchDisabled);
                                                            NotificationPanelViewController notificationPanelViewController122 = NotificationPanelViewController.this;
                                                            notificationPanelViewController122.mQuickPanelLogger.onInterceptTouchEvent(motionEvent, notificationPanelViewController122.mQuickPanelLogBuilder.toString(), false);
                                                            return false;
                                                        }
                                                    }
                                                    return false;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            SecPanelSplitHelper secPanelSplitHelper3 = secNotificationPanelViewController2.panelSplitHelper;
                            if (secPanelSplitHelper3 != null) {
                                SecPanelSplitHelper.Companion.getClass();
                                boolean z12 = SecPanelSplitHelper.isEnabled && secPanelSplitHelper3.panelSlideEventHandler.sliderAnimator != null;
                                if (z12) {
                                    QuickPanelLogger quickPanelLogger24 = NotificationPanelViewController.this.mQuickPanelLogger;
                                    if (quickPanelLogger24 != null) {
                                        quickPanelLogger24.onInterceptTouchEvent(motionEvent, "isSlideAnimating()", true);
                                        return true;
                                    }
                                } else {
                                    NotificationPanelViewController notificationPanelViewController40 = NotificationPanelViewController.this;
                                    if (notificationPanelViewController40.mSecNotificationPanelViewController.lockscreenShadeTransitionController.touchHelper.maxDragDownAnimator != null) {
                                        QuickPanelLogger quickPanelLogger25 = notificationPanelViewController40.mQuickPanelLogger;
                                        if (quickPanelLogger25 != null) {
                                            quickPanelLogger25.onInterceptTouchEvent(motionEvent, "isDragDownAnimating()", true);
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                QuickPanelLogger quickPanelLogger26 = NotificationPanelViewController.this.mQuickPanelLogger;
                if (quickPanelLogger26 != null) {
                    quickPanelLogger26.onInterceptTouchEvent(motionEvent, "StatusBarWindowView Touched", true);
                    return true;
                }
            }
            return true;
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            View.OnTouchListener onTouchListener;
            SecNotificationPanelViewController secNotificationPanelViewController = NotificationPanelViewController.this.mSecNotificationPanelViewController;
            if (secNotificationPanelViewController == null || !secNotificationPanelViewController.isStatusBarWindowViewTouched()) {
                return (!NotificationPanelViewController.this.mStatusBarStateController.isDozing() || (onTouchListener = NotificationPanelViewController.this.mAODDoubleTouchListener) == null) ? onTouchEvent(motionEvent) : onTouchListener.onTouch(view, motionEvent);
            }
            QuickPanelLogger quickPanelLogger = NotificationPanelViewController.this.mQuickPanelLogger;
            if (quickPanelLogger != null) {
                quickPanelLogger.onTouchEvent(motionEvent, "StatusBarWindowView Touched", true);
            }
            return true;
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x0027  */
        /* JADX WARN: Removed duplicated region for block: B:161:0x0262  */
        /* JADX WARN: Removed duplicated region for block: B:215:0x0344  */
        /* JADX WARN: Removed duplicated region for block: B:217:0x034c  */
        /* JADX WARN: Removed duplicated region for block: B:271:0x041a  */
        /* JADX WARN: Removed duplicated region for block: B:307:0x04e5  */
        /* JADX WARN: Removed duplicated region for block: B:377:0x05e5  */
        /* JADX WARN: Removed duplicated region for block: B:382:0x0601  */
        /* JADX WARN: Removed duplicated region for block: B:452:0x0757  */
        /* JADX WARN: Removed duplicated region for block: B:501:0x0826  */
        /* JADX WARN: Removed duplicated region for block: B:605:0x0a04  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean onTouchEvent(MotionEvent motionEvent) throws Resources.NotFoundException {
            KeyguardSecAffordanceHelper keyguardSecAffordanceHelper;
            KeyguardSecAffordanceView keyguardSecAffordanceView;
            KeyguardSecAffordanceView keyguardSecAffordanceView2;
            boolean zOnTouchEvent;
            boolean zOnTouchEvent2;
            float f;
            boolean z;
            boolean zHandleTouch$1;
            SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor;
            StringBuilder sb;
            SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor2;
            PluginLock pluginLock;
            SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor3;
            View view;
            boolean zStartExpansion;
            StringBuilder sb2;
            MultiWindowEdgeDetector multiWindowEdgeDetector;
            final SecQuickSettingsControllerImpl secQuickSettingsControllerImpl;
            SecQuickTileChunkLayoutBarTouchHelper secQuickTileChunkLayoutBarTouchHelper;
            int pointerId;
            SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor4;
            PanelPopOverManager panelPopOverManager;
            NotificationPanelView notificationPanelView;
            if (NotificationPanelViewController.this.mStatusBarStateController.getState() == 0) {
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                if (notificationPanelViewController.mUseExternalTouch) {
                    boolean z2 = QpRune.QUICK_PANEL_CODE_FOR_POP_OVER;
                    if (z2 && (panelPopOverManager = NotificationPanelViewController.this.mPanelPopOverManager) != null) {
                        int actionMasked = motionEvent.getActionMasked();
                        if (panelPopOverManager.getNeedToPopOver() && ((actionMasked == 4 || ((QsAnimatorState.isDetailShowing || QsAnimatorState.isCustomizerShowing) && actionMasked == 1)) && (notificationPanelView = panelPopOverManager.mView) != null)) {
                            notificationPanelView.post(panelPopOverManager.collapseRunnable);
                        }
                    }
                    if (!NotificationPanelViewController.this.mAlternateBouncerInteractor.isVisibleState()) {
                        QuickPanelLogger quickPanelLogger = NotificationPanelViewController.this.mQuickPanelLogger;
                        if (quickPanelLogger != null) {
                            quickPanelLogger.onTouchEvent(motionEvent);
                        }
                        SecNotificationPanelViewController secNotificationPanelViewController = NotificationPanelViewController.this.mSecNotificationPanelViewController;
                        if (secNotificationPanelViewController != null) {
                            SecPanelSplitHelper secPanelSplitHelper = secNotificationPanelViewController.panelSplitHelper;
                            if (secPanelSplitHelper != null) {
                                SecPanelSplitHelper.Companion.getClass();
                                if (SecPanelSplitHelper.isEnabled && secPanelSplitHelper.panelSlideEventHandler.sliderAnimator != null) {
                                    QuickPanelLogger quickPanelLogger2 = NotificationPanelViewController.this.mQuickPanelLogger;
                                    if (quickPanelLogger2 != null) {
                                        quickPanelLogger2.onTouchEvent(motionEvent, "isSlideAnimating()", true);
                                    }
                                    if (z2 && (secQsUiDisplayModeInteractor4 = NotificationPanelViewController.this.mSecQsUiDisplayModeInteractor) != null && secQsUiDisplayModeInteractor4.isTablet() && motionEvent.getActionMasked() == 0) {
                                        NotificationPanelViewController.this.mQsController.mSecQuickSettingsControllerImpl.onTouch(motionEvent);
                                        return true;
                                    }
                                }
                                return true;
                            }
                            NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                            if (notificationPanelViewController2.mSecNotificationPanelViewController.lockscreenShadeTransitionController.touchHelper.maxDragDownAnimator != null && !notificationPanelViewController2.mHeadsUpVisibleOnDown) {
                                QuickPanelLogger quickPanelLogger3 = notificationPanelViewController2.mQuickPanelLogger;
                                if (quickPanelLogger3 != null) {
                                    quickPanelLogger3.onTouchEvent(motionEvent, "shouldScrollViewIntercept & isDragDownAnimating() & !mHeadsUpVisibleOnDown", true);
                                    return true;
                                }
                            }
                            return true;
                        }
                        NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
                        boolean z3 = notificationPanelViewController3.shouldScrollViewIntercept;
                        QuickSettingsControllerImpl quickSettingsControllerImpl = notificationPanelViewController3.mQsController;
                        if (z3) {
                            SecQuickSettingsControllerImpl secQuickSettingsControllerImpl2 = quickSettingsControllerImpl.mSecQuickSettingsControllerImpl;
                            secQuickSettingsControllerImpl2.updateScrollViewLocationDelta();
                            MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
                            motionEventObtain.offsetLocation(secQuickSettingsControllerImpl2.deltaX, secQuickSettingsControllerImpl2.deltaY);
                            NonInterceptingScrollView nonInterceptingScrollView = secQuickSettingsControllerImpl2.getNonInterceptingScrollView();
                            if (nonInterceptingScrollView != null) {
                                nonInterceptingScrollView.dispatchTouchEvent(motionEventObtain);
                            }
                            if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                                NotificationPanelViewController.this.shouldScrollViewIntercept = false;
                            }
                            QuickPanelLogger quickPanelLogger4 = NotificationPanelViewController.this.mQuickPanelLogger;
                            if (quickPanelLogger4 != null) {
                                quickPanelLogger4.onTouchEvent(motionEvent, "shouldScrollViewIntercept", true);
                                return true;
                            }
                        } else if (LsRune.AOD_FULLSCREEN && notificationPanelViewController3.mUnlockedScreenOffAnimationController.lightRevealAnimationPlaying) {
                            QuickPanelLogger quickPanelLogger5 = notificationPanelViewController3.mQuickPanelLogger;
                            if (quickPanelLogger5 != null) {
                                quickPanelLogger5.onTouchEvent(motionEvent, "unlockedScreenOff animation playing", true);
                                return true;
                            }
                        } else {
                            QS qs = quickSettingsControllerImpl.mQs;
                            if (qs != null ? qs.disallowPanelTouches() : false) {
                                QuickPanelLogger quickPanelLogger6 = NotificationPanelViewController.this.mQuickPanelLogger;
                                if (quickPanelLogger6 != null) {
                                    quickPanelLogger6.onTouchEvent(motionEvent, "qs touch is disallowed", true);
                                    return true;
                                }
                            } else {
                                if (motionEvent.getAction() == 0) {
                                    if (motionEvent.getDownTime() == this.mLastTouchDownTime) {
                                        NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "onTouch: duplicate down event detected... ignoring");
                                        QuickPanelLogger quickPanelLogger7 = NotificationPanelViewController.this.mQuickPanelLogger;
                                        if (quickPanelLogger7 != null) {
                                            quickPanelLogger7.onTouchEvent(motionEvent, "event.getDownTime() == mLastTouchDownTime", true);
                                            return true;
                                        }
                                    } else {
                                        this.mLastTouchDownTime = motionEvent.getDownTime();
                                        NotificationPanelViewController notificationPanelViewController4 = NotificationPanelViewController.this;
                                        notificationPanelViewController4.mHeadsUpVisibleOnDown = ((HeadsUpManagerImpl) notificationPanelViewController4.mHeadsUpManager).mHasPinnedNotification;
                                    }
                                } else if (motionEvent.getAction() == 1 && ((NotificationPanelViewController.this.mStatusBarStateController.getState() == 1 || NotificationPanelViewController.this.mStatusBarStateController.getState() == 2) && !NotificationPanelViewController.this.mKeyguardTouchAnimator.isViRunning())) {
                                    NotificationPanelViewController notificationPanelViewController5 = NotificationPanelViewController.this;
                                    if (!notificationPanelViewController5.mQsController.mFullyExpanded) {
                                        notificationPanelViewController5.mKeyguardTouchAnimator.setIntercept(false);
                                    }
                                }
                                QuickSettingsControllerImpl quickSettingsControllerImpl2 = NotificationPanelViewController.this.mQsController;
                                if (quickSettingsControllerImpl2.isQsFragmentCreated() && quickSettingsControllerImpl2.mFullyExpanded) {
                                    QS qs2 = quickSettingsControllerImpl2.mQs;
                                    if (qs2 != null ? qs2.disallowPanelTouches() : false) {
                                        NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "onTouch: ignore touch, panel touches disallowed and qs fully expanded");
                                        QuickPanelLogger quickPanelLogger8 = NotificationPanelViewController.this.mQuickPanelLogger;
                                        if (quickPanelLogger8 != null) {
                                            quickPanelLogger8.onTouchEvent(motionEvent, "mQsController.isFullyExpandedAndTouchesDisallowed()", false);
                                            return false;
                                        }
                                    }
                                }
                                SecNotificationPanelViewController secNotificationPanelViewController2 = NotificationPanelViewController.this.mSecNotificationPanelViewController;
                                if (secNotificationPanelViewController2 != null && (secQuickSettingsControllerImpl = secNotificationPanelViewController2.secQuickSettingsControllerImpl) != null && (secQuickTileChunkLayoutBarTouchHelper = secQuickSettingsControllerImpl.tileChunkLayoutBarTouchHelper) != null) {
                                    Consumer consumer = new Consumer() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$onTouchEventForTileChunkLayoutBar$1
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            secQuickSettingsControllerImpl.touchAboveFalsingThresholdConsumer.accept((Boolean) obj);
                                        }
                                    };
                                    secQuickTileChunkLayoutBarTouchHelper.updateChunkLayoutBar(motionEvent);
                                    if (secQuickTileChunkLayoutBarTouchHelper.isExpanding) {
                                        int iPreparePointerIndex = secQuickTileChunkLayoutBarTouchHelper.preparePointerIndex(motionEvent);
                                        float y = motionEvent.getY(iPreparePointerIndex);
                                        motionEvent.getX(iPreparePointerIndex);
                                        float asDouble = y - ((float) secQuickTileChunkLayoutBarTouchHelper.initialTouchYSupplier.getAsDouble());
                                        secQuickTileChunkLayoutBarTouchHelper.trackMovementConsumer.accept(motionEvent);
                                        int actionMasked2 = motionEvent.getActionMasked();
                                        if (actionMasked2 == 0) {
                                            TileChunkLayoutBarExpandHelper tileChunkLayoutBarExpandHelper = secQuickTileChunkLayoutBarTouchHelper.expandHelper;
                                            if (tileChunkLayoutBarExpandHelper != null) {
                                                tileChunkLayoutBarExpandHelper.setTracking((float) secQuickTileChunkLayoutBarTouchHelper.currentQsVelocitySupplier.getAsDouble(), true);
                                            }
                                            secQuickTileChunkLayoutBarTouchHelper.initVelocityTrackerRunnable.run();
                                        } else if (actionMasked2 == 1) {
                                            secQuickTileChunkLayoutBarTouchHelper.trackingPointerConsumer.accept(-1);
                                            TileChunkLayoutBarExpandHelper tileChunkLayoutBarExpandHelper2 = secQuickTileChunkLayoutBarTouchHelper.expandHelper;
                                            if (tileChunkLayoutBarExpandHelper2 != null) {
                                                tileChunkLayoutBarExpandHelper2.setTracking((float) secQuickTileChunkLayoutBarTouchHelper.currentQsVelocitySupplier.getAsDouble(), false);
                                            }
                                            secQuickTileChunkLayoutBarTouchHelper.clearVelocityTrackerRunnable.run();
                                        } else if (actionMasked2 == 2) {
                                            TileChunkLayoutBarExpandHelper tileChunkLayoutBarExpandHelper3 = secQuickTileChunkLayoutBarTouchHelper.expandHelper;
                                            if (tileChunkLayoutBarExpandHelper3 != null) {
                                                float f2 = tileChunkLayoutBarExpandHelper3.initialBarHeight + asDouble;
                                                TileChunkLayoutBar tileChunkLayoutBar = tileChunkLayoutBarExpandHelper3.tileChunkLayoutBar;
                                                float f3 = tileChunkLayoutBar.mContainerCollapsedHeight;
                                                if (f2 < f3) {
                                                    f2 = f3;
                                                }
                                                float f4 = tileChunkLayoutBar.mContainerExpandedHeight;
                                                if (f2 > f4) {
                                                    f2 = f4;
                                                }
                                                tileChunkLayoutBar.setContainerHeight((int) f2);
                                            }
                                            if (asDouble >= r11.getFalsingThreshold()) {
                                                consumer.accept(Boolean.TRUE);
                                            }
                                        } else if (actionMasked2 != 3) {
                                            if (actionMasked2 == 6 && secQuickTileChunkLayoutBarTouchHelper.trackingPointerSupplier.getAsInt() == (pointerId = motionEvent.getPointerId(motionEvent.getActionIndex()))) {
                                                secQuickTileChunkLayoutBarTouchHelper.trackingPointerConsumer.accept(motionEvent.getPointerId(motionEvent.getPointerId(0) != pointerId ? 0 : 1));
                                            }
                                        }
                                        if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                                            secQuickTileChunkLayoutBarTouchHelper.isExpanding = false;
                                        }
                                        QuickPanelLogger quickPanelLogger9 = NotificationPanelViewController.this.mQuickPanelLogger;
                                        if (quickPanelLogger9 != null) {
                                            quickPanelLogger9.onTouchEvent(motionEvent, "onTouchEventForTileChunkLayoutBar()", true);
                                            return true;
                                        }
                                    }
                                }
                                NotificationPanelViewController notificationPanelViewController6 = NotificationPanelViewController.this;
                                if (notificationPanelViewController6.mKeyguardStateController.mShowing || notificationPanelViewController6.isPanelExpanded() || (multiWindowEdgeDetector = NotificationPanelViewController.this.mMultiWindowEdgeDetector) == null || !multiWindowEdgeDetector.interceptTouchForCornerGesture(motionEvent)) {
                                    CentralSurfacesImpl centralSurfacesImpl = NotificationPanelViewController.this.mCentralSurfaces;
                                    if (centralSurfacesImpl.mBouncerShowing && centralSurfacesImpl.mStatusBarKeyguardViewManager.primaryBouncerNeedsScrimming()) {
                                        NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "onTouch: ignore touch, bouncer scrimmed or showing over dream");
                                        QuickPanelLogger quickPanelLogger10 = NotificationPanelViewController.this.mQuickPanelLogger;
                                        if (quickPanelLogger10 != null) {
                                            quickPanelLogger10.onTouchEvent(motionEvent, "mCentralSurfaces.isBouncerShowingScrimmed()", false);
                                        }
                                    } else {
                                        if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                                            NotificationPanelViewController.this.mBlockingExpansionForCurrentTouch = false;
                                        }
                                        if (NotificationPanelViewController.this.mLastEventSynthesizedDown && motionEvent.getAction() == 1) {
                                            NotificationPanelViewController.this.expand(true);
                                        }
                                        NotificationPanelViewController.m2945$$Nest$minitDownStates(NotificationPanelViewController.this, motionEvent);
                                        NotificationPanelViewController notificationPanelViewController7 = NotificationPanelViewController.this;
                                        if (!notificationPanelViewController7.mIsExpandingOrCollapsing) {
                                            if (notificationPanelViewController7.mQsController.shouldQuickSettingsIntercept(notificationPanelViewController7.mDownX, notificationPanelViewController7.mDownY, 0.0f)) {
                                                if (NotificationPanelViewController.this.mPulseExpansionHandler.isExpanding) {
                                                    PulseExpansionHandler pulseExpansionHandler = NotificationPanelViewController.this.mPulseExpansionHandler;
                                                    pulseExpansionHandler.getClass();
                                                    boolean z4 = (motionEvent.getAction() == 3 || motionEvent.getAction() == 1) && pulseExpansionHandler.isExpanding;
                                                    ExpandableView expandableView = pulseExpansionHandler.mStartingChild;
                                                    boolean z5 = (expandableView != null && expandableView.showingPulsing()) || pulseExpansionHandler.bypassController.canBypass();
                                                    if ((!pulseExpansionHandler.canHandleMotionEvent() || !z5) && !z4) {
                                                        zStartExpansion = false;
                                                    } else if (pulseExpansionHandler.velocityTracker == null || !pulseExpansionHandler.isExpanding || motionEvent.getActionMasked() == 0) {
                                                        zStartExpansion = pulseExpansionHandler.startExpansion(motionEvent);
                                                    } else {
                                                        VelocityTracker velocityTracker = pulseExpansionHandler.velocityTracker;
                                                        velocityTracker.getClass();
                                                        velocityTracker.addMovement(motionEvent);
                                                        float y2 = motionEvent.getY() - pulseExpansionHandler.mInitialTouchY;
                                                        int actionMasked3 = motionEvent.getActionMasked();
                                                        NotificationWakeUpCoordinator notificationWakeUpCoordinator = pulseExpansionHandler.wakeUpCoordinator;
                                                        LockscreenShadeTransitionController lockscreenShadeTransitionController = pulseExpansionHandler.lockscreenShadeTransitionController;
                                                        if (actionMasked3 == 1) {
                                                            VelocityTracker velocityTracker2 = pulseExpansionHandler.velocityTracker;
                                                            velocityTracker2.getClass();
                                                            velocityTracker2.computeCurrentVelocity(1000);
                                                            StatusBarStateController statusBarStateController = pulseExpansionHandler.statusBarStateController;
                                                            if (y2 > 0.0f) {
                                                                VelocityTracker velocityTracker3 = pulseExpansionHandler.velocityTracker;
                                                                velocityTracker3.getClass();
                                                                boolean z6 = velocityTracker3.getYVelocity() > -1000.0f && statusBarStateController.getState() != 0;
                                                                FalsingManager falsingManager = pulseExpansionHandler.falsingManager;
                                                                if (falsingManager.isUnlockingDisabled() || falsingManager.isFalseTouch(2) || !z6) {
                                                                    pulseExpansionHandler.cancelExpansion();
                                                                } else {
                                                                    ExpandableView expandableView2 = pulseExpansionHandler.mStartingChild;
                                                                    if (expandableView2 != null) {
                                                                        if (expandableView2 instanceof ExpandableNotificationRow) {
                                                                            ((ExpandableNotificationRow) expandableView2).setUserLocked(false);
                                                                        }
                                                                        pulseExpansionHandler.mStartingChild = null;
                                                                    }
                                                                    if (statusBarStateController.isDozing()) {
                                                                        if (notificationWakeUpCoordinator.outputLinearDozeAmount != 0.0f) {
                                                                            notificationWakeUpCoordinator.willWakeUp = true;
                                                                        }
                                                                        PowerManager powerManager = pulseExpansionHandler.mPowerManager;
                                                                        powerManager.getClass();
                                                                        powerManager.wakeUp(android.os.SystemClock.uptimeMillis(), 4, "com.android.systemui:PULSEDRAG");
                                                                    }
                                                                    lockscreenShadeTransitionController.goToLockedShade(expandableView2, false);
                                                                    lockscreenShadeTransitionController.finishPulseAnimation(false);
                                                                    pulseExpansionHandler.leavingLockscreen = true;
                                                                    pulseExpansionHandler.setExpanding(false);
                                                                    ExpandableView expandableView3 = pulseExpansionHandler.mStartingChild;
                                                                    if (expandableView3 instanceof ExpandableNotificationRow) {
                                                                        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView3;
                                                                        expandableNotificationRow.getClass();
                                                                        expandableNotificationRow.onExpandedByGesture(true);
                                                                    }
                                                                }
                                                                VelocityTracker velocityTracker4 = pulseExpansionHandler.velocityTracker;
                                                                if (velocityTracker4 != null) {
                                                                    velocityTracker4.recycle();
                                                                }
                                                                pulseExpansionHandler.velocityTracker = null;
                                                            }
                                                        } else if (actionMasked3 == 2) {
                                                            float fMax = Math.max(y2, 0.0f);
                                                            ExpandableView expandableView4 = pulseExpansionHandler.mStartingChild;
                                                            if (expandableView4 != null) {
                                                                expandableView4.setActualHeight(Math.min((int) (expandableView4.getCollapsedHeight() + fMax), expandableView4.getMaxContentHeight()), true);
                                                            } else {
                                                                notificationWakeUpCoordinator.setNotificationsVisibleForExpansion(y2 > ((float) lockscreenShadeTransitionController.fullTransitionDistance), true, true);
                                                            }
                                                            lockscreenShadeTransitionController.setPulseHeight(fMax, false);
                                                        } else if (actionMasked3 == 3) {
                                                            pulseExpansionHandler.cancelExpansion();
                                                            VelocityTracker velocityTracker5 = pulseExpansionHandler.velocityTracker;
                                                            if (velocityTracker5 != null) {
                                                                velocityTracker5.recycle();
                                                            }
                                                            pulseExpansionHandler.velocityTracker = null;
                                                        }
                                                        zStartExpansion = pulseExpansionHandler.isExpanding;
                                                    }
                                                    if (zStartExpansion) {
                                                        NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "onTouch: PulseExpansionHandler handled event");
                                                        NotificationPanelViewController notificationPanelViewController8 = NotificationPanelViewController.this;
                                                        if (notificationPanelViewController8.mQuickPanelLogger != null && (sb2 = notificationPanelViewController8.mQuickPanelLogBuilder) != null) {
                                                            sb2.setLength(0);
                                                            StringBuilder sb3 = NotificationPanelViewController.this.mQuickPanelLogBuilder;
                                                            sb3.append("pulseShouldGetTouch && mPulseExpansionHandler.onTouchEvent()");
                                                            sb3.append(" (!mIsExpandingOrCollapsing: ");
                                                            sb3.append(!NotificationPanelViewController.this.mIsExpandingOrCollapsing);
                                                            sb3.append(" && !mQsController.shouldQuickSettingsIntercept(): ");
                                                            NotificationPanelViewController notificationPanelViewController9 = NotificationPanelViewController.this;
                                                            sb3.append(!notificationPanelViewController9.mQsController.shouldQuickSettingsIntercept(notificationPanelViewController9.mDownX, notificationPanelViewController9.mDownY, 0.0f));
                                                            sb3.append(") || mPulseExpansionHandler.isExpanding(): ");
                                                            sb3.append(NotificationPanelViewController.this.mPulseExpansionHandler.isExpanding);
                                                            NotificationPanelViewController notificationPanelViewController10 = NotificationPanelViewController.this;
                                                            notificationPanelViewController10.mQuickPanelLogger.onTouchEvent(motionEvent, notificationPanelViewController10.mQuickPanelLogBuilder.toString(), true);
                                                            return true;
                                                        }
                                                    } else {
                                                        NotificationPanelViewController notificationPanelViewController11 = NotificationPanelViewController.this;
                                                        if (notificationPanelViewController11.mPulsing) {
                                                            notificationPanelViewController11.mShadeLog.logMotionEvent(motionEvent, "onTouch: eat touch, device pulsing");
                                                            QuickPanelLogger quickPanelLogger11 = NotificationPanelViewController.this.mQuickPanelLogger;
                                                            if (quickPanelLogger11 != null) {
                                                                quickPanelLogger11.onTouchEvent(motionEvent, "mPulsing", true);
                                                                return true;
                                                            }
                                                        } else {
                                                            if (notificationPanelViewController11.mListenForHeadsUp && !notificationPanelViewController11.mHeadsUpTouchHelper.mTrackingHeadsUp) {
                                                                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController11.mNotificationStackScrollLayoutController;
                                                                notificationStackScrollLayoutController.getClass();
                                                                int i = SceneContainerFlag.$r8$clinit;
                                                                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                                                                if (!(notificationStackScrollLayoutController.mLongPressedView != null) && NotificationPanelViewController.this.mHeadsUpTouchHelper.onInterceptTouchEvent(motionEvent)) {
                                                                    NotificationPanelViewController.this.mMetricsLogger.count("panel_open_peek", 1);
                                                                }
                                                            }
                                                            boolean zOnTouchEvent3 = NotificationPanelViewController.this.mHeadsUpTouchHelper.onTouchEvent(motionEvent);
                                                            int i2 = ShadeExpandsOnStatusBarLongPress.$r8$clinit;
                                                            NotificationPanelViewController notificationPanelViewController12 = NotificationPanelViewController.this;
                                                            if ((!notificationPanelViewController12.mIsExpandingOrCollapsing || notificationPanelViewController12.mHintAnimationRunning) && !notificationPanelViewController12.mQsController.getExpanded()) {
                                                                NotificationPanelViewController notificationPanelViewController13 = NotificationPanelViewController.this;
                                                                if (notificationPanelViewController13.mBarState != 0 && !notificationPanelViewController13.mDozing && notificationPanelViewController13.mKeyguardSecBottomArea.getVisibility() == 0 && (keyguardSecAffordanceHelper = NotificationPanelViewController.this.mSecAffordanceHelper) != null) {
                                                                    int actionMasked4 = motionEvent.getActionMasked();
                                                                    if ((!keyguardSecAffordanceHelper.mMotionCancelled || actionMasked4 == 0) && !(CscRune.SECURITY_SIM_PERM_DISABLED && ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isIccBlockedPermanently())) {
                                                                        float y3 = motionEvent.getY();
                                                                        float x = motionEvent.getX();
                                                                        if (actionMasked4 != 0) {
                                                                            if (actionMasked4 == 1 || actionMasked4 == 3) {
                                                                                KeyguardSecAffordanceView keyguardSecAffordanceView3 = keyguardSecAffordanceHelper.mTargetedView;
                                                                                if (keyguardSecAffordanceView3 != null) {
                                                                                    zOnTouchEvent2 = keyguardSecAffordanceView3.onTouchEvent(motionEvent);
                                                                                    if (keyguardSecAffordanceHelper.mTargetedView != null) {
                                                                                        keyguardSecAffordanceHelper.endMotion();
                                                                                    }
                                                                                    zOnTouchEvent = zOnTouchEvent2;
                                                                                }
                                                                                zOnTouchEvent = false;
                                                                            } else {
                                                                                if (actionMasked4 != 5) {
                                                                                    KeyguardSecAffordanceView keyguardSecAffordanceView4 = keyguardSecAffordanceHelper.mTargetedView;
                                                                                    if (keyguardSecAffordanceView4 != null) {
                                                                                        zOnTouchEvent = keyguardSecAffordanceView4.onTouchEvent(motionEvent);
                                                                                    }
                                                                                } else {
                                                                                    keyguardSecAffordanceHelper.mMotionCancelled = true;
                                                                                    KeyguardSecAffordanceView keyguardSecAffordanceView5 = keyguardSecAffordanceHelper.mTargetedView;
                                                                                    if (keyguardSecAffordanceView5 != null) {
                                                                                        zOnTouchEvent2 = keyguardSecAffordanceView5.onTouchEvent(motionEvent);
                                                                                        if (keyguardSecAffordanceHelper.mTargetedView != null) {
                                                                                            keyguardSecAffordanceHelper.endMotion();
                                                                                        }
                                                                                        zOnTouchEvent = zOnTouchEvent2;
                                                                                    }
                                                                                }
                                                                                zOnTouchEvent = false;
                                                                            }
                                                                            zOnTouchEvent3 |= zOnTouchEvent;
                                                                        } else {
                                                                            KeyguardSecAffordanceView keyguardSecAffordanceView6 = keyguardSecAffordanceHelper.mLeftIcon;
                                                                            keyguardSecAffordanceView6.getClass();
                                                                            if (keyguardSecAffordanceView6.getVisibility() == 0) {
                                                                                KeyguardSecAffordanceView keyguardSecAffordanceView7 = keyguardSecAffordanceHelper.mLeftIcon;
                                                                                keyguardSecAffordanceView7.getClass();
                                                                                if (keyguardSecAffordanceHelper.isOnIcon(keyguardSecAffordanceView7, x, y3)) {
                                                                                    keyguardSecAffordanceView = keyguardSecAffordanceHelper.mLeftIcon;
                                                                                    keyguardSecAffordanceView.getClass();
                                                                                } else {
                                                                                    KeyguardSecAffordanceView keyguardSecAffordanceView8 = keyguardSecAffordanceHelper.mRightIcon;
                                                                                    keyguardSecAffordanceView8.getClass();
                                                                                    if (keyguardSecAffordanceView8.getVisibility() == 0) {
                                                                                        KeyguardSecAffordanceView keyguardSecAffordanceView9 = keyguardSecAffordanceHelper.mRightIcon;
                                                                                        keyguardSecAffordanceView9.getClass();
                                                                                        if (keyguardSecAffordanceHelper.isOnIcon(keyguardSecAffordanceView9, x, y3)) {
                                                                                            keyguardSecAffordanceView = keyguardSecAffordanceHelper.mRightIcon;
                                                                                            keyguardSecAffordanceView.getClass();
                                                                                        } else {
                                                                                            keyguardSecAffordanceView = null;
                                                                                        }
                                                                                    }
                                                                                }
                                                                                Log.d("KeyguardSecAffordanceHelper", "onTouchEvent: After selecting target view");
                                                                                if (keyguardSecAffordanceView == null || !((keyguardSecAffordanceView2 = keyguardSecAffordanceHelper.mTargetedView) == null || keyguardSecAffordanceView2 == keyguardSecAffordanceView)) {
                                                                                    keyguardSecAffordanceHelper.mMotionCancelled = true;
                                                                                    zOnTouchEvent = false;
                                                                                    zOnTouchEvent3 |= zOnTouchEvent;
                                                                                } else {
                                                                                    keyguardSecAffordanceHelper.mMotionCancelled = false;
                                                                                    if (keyguardSecAffordanceHelper.context.getResources().getConfiguration().orientation != 1 || DeviceState.isMultiFoldMain()) {
                                                                                        WindowManager.LayoutParams layoutParams = keyguardSecAffordanceHelper.layoutParams;
                                                                                        if (layoutParams == null) {
                                                                                            layoutParams = null;
                                                                                        }
                                                                                        layoutParams.semClearExtensionFlags(8);
                                                                                    } else {
                                                                                        WindowManager.LayoutParams layoutParams2 = keyguardSecAffordanceHelper.layoutParams;
                                                                                        if (layoutParams2 == null) {
                                                                                            layoutParams2 = null;
                                                                                        }
                                                                                        layoutParams2.semAddExtensionFlags(8);
                                                                                        Log.d("KeyguardSecAffordanceHelper", "updateBlurPanelOrientation Fixed Portrait");
                                                                                    }
                                                                                    WindowManager windowManager = (WindowManager) keyguardSecAffordanceHelper.context.getSystemService("window");
                                                                                    FrameLayout frameLayout = keyguardSecAffordanceHelper.mBlurPanelView;
                                                                                    WindowManager.LayoutParams layoutParams3 = keyguardSecAffordanceHelper.layoutParams;
                                                                                    if (layoutParams3 == null) {
                                                                                        layoutParams3 = null;
                                                                                    }
                                                                                    windowManager.updateViewLayout(frameLayout, layoutParams3);
                                                                                    keyguardSecAffordanceHelper.mTargetedView = keyguardSecAffordanceView;
                                                                                    KeyguardSecAffordanceView keyguardSecAffordanceView10 = keyguardSecAffordanceHelper.mLeftIcon;
                                                                                    if (keyguardSecAffordanceView == keyguardSecAffordanceView10) {
                                                                                        KeyguardSecAffordanceView keyguardSecAffordanceView11 = keyguardSecAffordanceHelper.mRightIcon;
                                                                                        keyguardSecAffordanceView11.getClass();
                                                                                        keyguardSecAffordanceView11.mIsTargetView = false;
                                                                                        KeyguardSecAffordanceView keyguardSecAffordanceView12 = keyguardSecAffordanceHelper.mLeftIcon;
                                                                                        keyguardSecAffordanceView12.getClass();
                                                                                        keyguardSecAffordanceView12.mIsTargetView = true;
                                                                                    } else if (keyguardSecAffordanceView == keyguardSecAffordanceHelper.mRightIcon) {
                                                                                        keyguardSecAffordanceView10.getClass();
                                                                                        keyguardSecAffordanceView10.mIsTargetView = false;
                                                                                        KeyguardSecAffordanceView keyguardSecAffordanceView13 = keyguardSecAffordanceHelper.mRightIcon;
                                                                                        keyguardSecAffordanceView13.getClass();
                                                                                        keyguardSecAffordanceView13.mIsTargetView = true;
                                                                                    }
                                                                                    KeyguardSecAffordanceView keyguardSecAffordanceView14 = keyguardSecAffordanceHelper.mTargetedView;
                                                                                    keyguardSecAffordanceView14.getClass();
                                                                                    keyguardSecAffordanceHelper.startPreviewAnimation(keyguardSecAffordanceView14, true);
                                                                                    ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).setShortcutLaunchInProgress(true);
                                                                                    KeyguardSecAffordanceView keyguardSecAffordanceView15 = keyguardSecAffordanceHelper.mTargetedView;
                                                                                    keyguardSecAffordanceView15.getClass();
                                                                                    zOnTouchEvent = keyguardSecAffordanceView15.onTouchEvent(motionEvent);
                                                                                    zOnTouchEvent3 |= zOnTouchEvent;
                                                                                }
                                                                            }
                                                                        }
                                                                    } else {
                                                                        zOnTouchEvent = false;
                                                                        zOnTouchEvent3 |= zOnTouchEvent;
                                                                    }
                                                                }
                                                            }
                                                            NotificationPanelViewController notificationPanelViewController14 = NotificationPanelViewController.this;
                                                            if (notificationPanelViewController14.mOnlyAffordanceInThisMotion) {
                                                                QuickPanelLogger quickPanelLogger12 = notificationPanelViewController14.mQuickPanelLogger;
                                                                if (quickPanelLogger12 != null) {
                                                                    quickPanelLogger12.onTouchEvent(motionEvent, "mOnlyAffordanceInThisMotion", true);
                                                                    return true;
                                                                }
                                                            } else {
                                                                IndicatorTouchHandler indicatorTouchHandler = ((StatusBarNotificationPanelViewControllerExt) notificationPanelViewController14.mSamsungBarExt.get()).indicatorTouchHandler;
                                                                CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) indicatorTouchHandler.knoxStateMonitor).mCustomSdkMonitor;
                                                                if (customSdkMonitor != null && customSdkMonitor.mKnoxCustomDoubleTapState && ((indicatorTouchHandler.doubleTapCount == 0 && motionEvent.getActionMasked() == 0) || motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3)) {
                                                                    int i3 = indicatorTouchHandler.doubleTapCount + 1;
                                                                    indicatorTouchHandler.doubleTapCount = i3;
                                                                    IndicatorTouchHandler$doubleTapTimeoutRunnable$1 indicatorTouchHandler$doubleTapTimeoutRunnable$1 = indicatorTouchHandler.doubleTapTimeoutRunnable;
                                                                    Handler handler = indicatorTouchHandler.mainHandler;
                                                                    if (i3 == 1) {
                                                                        handler.removeCallbacks(indicatorTouchHandler$doubleTapTimeoutRunnable$1);
                                                                        f = 0.0f;
                                                                        z = zOnTouchEvent3;
                                                                        handler.postDelayed(indicatorTouchHandler$doubleTapTimeoutRunnable$1, 500L);
                                                                        Log.d("IndicatorTouchHandler", "Post double tap timeout runnable");
                                                                    } else {
                                                                        f = 0.0f;
                                                                        z = zOnTouchEvent3;
                                                                        if (i3 >= 3) {
                                                                            Log.d("IndicatorTouchHandler", "Go to sleep by knox double tap");
                                                                            indicatorTouchHandler.doubleTapCount = 0;
                                                                            handler.removeCallbacks(indicatorTouchHandler$doubleTapTimeoutRunnable$1);
                                                                            indicatorTouchHandler.powerManager.goToSleep(android.os.SystemClock.uptimeMillis());
                                                                        }
                                                                    }
                                                                } else {
                                                                    f = 0.0f;
                                                                    z = zOnTouchEvent3;
                                                                }
                                                                int actionMasked5 = motionEvent.getActionMasked();
                                                                KeyguardStateController keyguardStateController = indicatorTouchHandler.keyguardStateController;
                                                                if (actionMasked5 == 0) {
                                                                    indicatorTouchHandler.touchDownX = motionEvent.getRawX();
                                                                    indicatorTouchHandler.touchDownY = motionEvent.getRawY();
                                                                    if ((((KeyguardStateControllerImpl) keyguardStateController).mShowing ? indicatorTouchHandler.keyguardCallChipRect : indicatorTouchHandler.callChipRect).contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                                                                        indicatorTouchHandler.isTouchOnCallChip = true;
                                                                        ActionBarContextView$$ExternalSyntheticOutline0.m(CubicBezierEasing$$ExternalSyntheticOutline0.m("ACTION_DOWN x=", indicatorTouchHandler.touchDownX, ", y=", indicatorTouchHandler.touchDownY, ", on the callChip=true, keyguardShowing="), ((KeyguardStateControllerImpl) keyguardStateController).mShowing, "IndicatorTouchHandler");
                                                                    }
                                                                } else if (actionMasked5 == 1) {
                                                                    if (indicatorTouchHandler.isTouchOnCallChip) {
                                                                        if ((((KeyguardStateControllerImpl) keyguardStateController).mShowing ? indicatorTouchHandler.keyguardCallChipRect : indicatorTouchHandler.callChipRect).contains((int) motionEvent.getX(), (int) motionEvent.getY()) && (view = indicatorTouchHandler.ongoingCallController.chipView) != null) {
                                                                            view.performClick();
                                                                        }
                                                                    }
                                                                    indicatorTouchHandler.isTouchOnCallChip = false;
                                                                } else if (actionMasked5 == 3) {
                                                                    if (indicatorTouchHandler.isTouchOnCallChip) {
                                                                        Log.d("IndicatorTouchHandler", "cancel or pointer up -> block to jump to call in multi touch");
                                                                    }
                                                                    indicatorTouchHandler.isTouchOnCallChip = false;
                                                                } else if (actionMasked5 == 5) {
                                                                    if (indicatorTouchHandler.isTouchOnCallChip) {
                                                                        Log.d("IndicatorTouchHandler", "pointer down x=" + motionEvent + ".rawX ,y=" + motionEvent + ".rawY");
                                                                    }
                                                                    indicatorTouchHandler.isTouchOnCallChip = false;
                                                                } else if (actionMasked5 == 6) {
                                                                }
                                                                if (SecPanelSplitHelper.isEnabled() && (motionEvent.getAction() == 1 || motionEvent.getAction() == 3)) {
                                                                    NotificationPanelViewController.this.setOnStatusBarDownEvent(null);
                                                                }
                                                                NotificationPanelViewController notificationPanelViewController15 = NotificationPanelViewController.this;
                                                                if (notificationPanelViewController15.mBarState == 1 || notificationPanelViewController15.mHeadsUpTouchHelper.mTrackingHeadsUp) {
                                                                    if (motionEvent.getActionMasked() == 0 && NotificationPanelViewController.this.isFullyCollapsed()) {
                                                                        NotificationPanelViewController.this.mMetricsLogger.count("panel_open", 1);
                                                                        if (z2 && (secQsUiDisplayModeInteractor3 = NotificationPanelViewController.this.mSecQsUiDisplayModeInteractor) != null && secQsUiDisplayModeInteractor3.isTablet()) {
                                                                            NotificationPanelViewController.this.mQsController.mSecQuickSettingsControllerImpl.onTouch(motionEvent);
                                                                        }
                                                                        zHandleTouch$1 = true;
                                                                    } else {
                                                                        if (z2 && (secQsUiDisplayModeInteractor = NotificationPanelViewController.this.mSecQsUiDisplayModeInteractor) != null && secQsUiDisplayModeInteractor.isTablet() && motionEvent.getActionMasked() == 0 && (NotificationPanelViewController.this.isFullyCollapsed() || NotificationPanelViewController.this.mBarState == 1)) {
                                                                            NotificationPanelViewController.this.mQsController.mSecQuickSettingsControllerImpl.onTouch(motionEvent);
                                                                        }
                                                                        zHandleTouch$1 = z;
                                                                    }
                                                                    try {
                                                                        NotificationPanelViewController notificationPanelViewController16 = NotificationPanelViewController.this;
                                                                        if (notificationPanelViewController16.mLockStarEnabled && notificationPanelViewController16.isInLockStarContainer(motionEvent) && !NotificationPanelViewController.this.mQsController.getExpanded()) {
                                                                            NotificationPanelViewController notificationPanelViewController17 = NotificationPanelViewController.this;
                                                                            if (notificationPanelViewController17.mBarState == 1 && notificationPanelViewController17.mPluginLockStarContainer.getVisibility() == 0 && NotificationPanelViewController.this.mPluginLockStarManagerLazy.get() != null) {
                                                                                boolean zOnInterceptTouchEvent = ((PluginLockStarManager) NotificationPanelViewController.this.mPluginLockStarManagerLazy.get()).onInterceptTouchEvent(motionEvent);
                                                                                QuickPanelLogger quickPanelLogger13 = NotificationPanelViewController.this.mQuickPanelLogger;
                                                                                if (quickPanelLogger13 == null) {
                                                                                    return zOnInterceptTouchEvent;
                                                                                }
                                                                                quickPanelLogger13.onTouchEvent(motionEvent, "LsRune.PLUGIN_LOCK_STAR", zOnInterceptTouchEvent);
                                                                                return zOnInterceptTouchEvent;
                                                                            }
                                                                        }
                                                                    } catch (Throwable th) {
                                                                        Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                                                                        Log.e("NotificationPanelView", "onTouchEvent() error in LockStar - " + th.getMessage());
                                                                    }
                                                                    if (motionEvent.getActionMasked() == 0 && NotificationPanelViewController.this.isFullyExpanded()) {
                                                                        NotificationPanelViewController notificationPanelViewController18 = NotificationPanelViewController.this;
                                                                        if (notificationPanelViewController18.mKeyguardStateController.mShowing) {
                                                                            notificationPanelViewController18.mStatusBarKeyguardViewManager.updateKeyguardPosition(motionEvent.getX());
                                                                        }
                                                                    }
                                                                    PluginLock pluginLock2 = NotificationPanelViewController.this.mPluginLock;
                                                                    if (pluginLock2 != null && pluginLock2.getTouchManager() != null && !NotificationPanelViewController.this.mQsController.getExpanded() && NotificationPanelViewController.this.mStatusBarStateController.getState() == 1 && NotificationPanelViewController.this.mPluginLock.getTouchManager().isIntercepting()) {
                                                                        Log.e("NotificationPanelView", "onTouch() event.getAction() : " + motionEvent.getAction());
                                                                        if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                                                                            NotificationPanelViewController notificationPanelViewController19 = NotificationPanelViewController.this;
                                                                            if (notificationPanelViewController19.mPluginLockViewMode == 0) {
                                                                                notificationPanelViewController19.mPluginLock.getTouchManager().setIntercept(false);
                                                                            }
                                                                        }
                                                                        boolean zOnTouchEvent4 = NotificationPanelViewController.this.mPluginLock.getTouchManager().onTouchEvent(motionEvent);
                                                                        QuickPanelLogger quickPanelLogger14 = NotificationPanelViewController.this.mQuickPanelLogger;
                                                                        if (quickPanelLogger14 != null) {
                                                                            quickPanelLogger14.onTouchEvent(motionEvent, "LsRune.PLUGIN_LOCK", zOnTouchEvent4);
                                                                        }
                                                                        return zOnTouchEvent4;
                                                                    }
                                                                    NotificationPanelViewController notificationPanelViewController20 = NotificationPanelViewController.this;
                                                                    if (notificationPanelViewController20.mQsExpandedOnTouchDown || notificationPanelViewController20.mLockscreenShadeTransitionController.getFractionToShade() > f) {
                                                                        NotificationPanelViewController notificationPanelViewController21 = NotificationPanelViewController.this;
                                                                        if (notificationPanelViewController21.mSecNotificationPanelViewController != null) {
                                                                            if (QpRune.QUICK_PANEL_CODE_FOR_POP_OVER && (secQsUiDisplayModeInteractor2 = notificationPanelViewController21.mSecQsUiDisplayModeInteractor) != null && secQsUiDisplayModeInteractor2.isTablet() && (motionEvent.getActionMasked() == 0 || motionEvent.getActionMasked() == 4)) {
                                                                                NotificationPanelViewController.this.mQsController.mSecQuickSettingsControllerImpl.onTouch(motionEvent);
                                                                            }
                                                                            if (NotificationPanelViewController.this.mSecNotificationPanelViewController.onTouchEvent(motionEvent, NotificationPanelViewController.this.mMotionAborted && motionEvent.getActionMasked() != 0)) {
                                                                                QuickPanelLogger quickPanelLogger15 = NotificationPanelViewController.this.mQuickPanelLogger;
                                                                                if (quickPanelLogger15 != null) {
                                                                                    quickPanelLogger15.onTouchEvent(motionEvent, "PanelSliding ", true);
                                                                                }
                                                                            }
                                                                        }
                                                                        NotificationPanelViewController notificationPanelViewController22 = NotificationPanelViewController.this;
                                                                        if (notificationPanelViewController22.mBarState == 1) {
                                                                            QuickPanelLogger quickPanelLogger16 = notificationPanelViewController22.mQuickPanelLogger;
                                                                            if (quickPanelLogger16 != null) {
                                                                                quickPanelLogger16.onTouchEvent(motionEvent, "No handleTouch() in KEYGUARD state", zHandleTouch$1);
                                                                            }
                                                                        } else {
                                                                            zHandleTouch$1 |= handleTouch$1(motionEvent);
                                                                        }
                                                                        NotificationPanelViewController notificationPanelViewController23 = NotificationPanelViewController.this;
                                                                        if (notificationPanelViewController23.mQuickPanelLogger != null && (sb = notificationPanelViewController23.mQuickPanelLogBuilder) != null) {
                                                                            sb.setLength(0);
                                                                            StringBuilder sb4 = NotificationPanelViewController.this.mQuickPanelLogBuilder;
                                                                            sb4.append("FINAL: !mDozing: ");
                                                                            sb4.append(!NotificationPanelViewController.this.mDozing);
                                                                            sb4.append(" || handled: ");
                                                                            sb4.append(zHandleTouch$1);
                                                                            NotificationPanelViewController notificationPanelViewController24 = NotificationPanelViewController.this;
                                                                            QuickPanelLogger quickPanelLogger17 = notificationPanelViewController24.mQuickPanelLogger;
                                                                            quickPanelLogger17.quickPanelLoggerHelper.onTouchEventLogger.log(motionEvent, quickPanelLogger17.tag, notificationPanelViewController24.mQuickPanelLogBuilder.toString());
                                                                        }
                                                                        return !NotificationPanelViewController.this.mDozing || zHandleTouch$1;
                                                                    }
                                                                    NotificationPanelViewController notificationPanelViewController25 = NotificationPanelViewController.this;
                                                                    boolean zOnAnimatorTouchEvent = (notificationPanelViewController25.mBarState != 1 || (pluginLock = notificationPanelViewController25.mPluginLock) == null || pluginLock.getTouchManager() == null) ? false : NotificationPanelViewController.this.mPluginLock.getTouchManager().onAnimatorTouchEvent(motionEvent);
                                                                    if (NotificationPanelViewController.this.mBarState != 1) {
                                                                        Log.w("KeyguardTouchAnimator", "NPVC touch is canceled on not KEYGUARD STATE = " + NotificationPanelViewController.this.mBarState);
                                                                        NotificationPanelViewController.this.mKeyguardTouchAnimator.setIntercept(false);
                                                                    }
                                                                    if (!zOnAnimatorTouchEvent) {
                                                                        NotificationPanelViewController notificationPanelViewController26 = NotificationPanelViewController.this;
                                                                        if (notificationPanelViewController26.mBarState == 1 && notificationPanelViewController26.mKeyguardTouchAnimator.onTouchEvent(motionEvent)) {
                                                                            KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = NotificationPanelViewController.this.mKeyguardSecBottomAreaViewController;
                                                                            AnimatorSet animatorSet = keyguardSecBottomAreaViewController.helpTextAnimSet;
                                                                            if (animatorSet != null && animatorSet.isRunning()) {
                                                                                AnimatorSet animatorSet2 = keyguardSecBottomAreaViewController.helpTextAnimSet;
                                                                                animatorSet2.getClass();
                                                                                animatorSet2.cancel();
                                                                            }
                                                                            QuickPanelLogger quickPanelLogger18 = NotificationPanelViewController.this.mQuickPanelLogger;
                                                                            if (quickPanelLogger18 != null) {
                                                                                quickPanelLogger18.onTouchEvent(motionEvent, "LsRune.KEYGUARD_ALL_DIRECTIONS_SWIPE_UNLOCK", true);
                                                                            }
                                                                        }
                                                                    }
                                                                } else {
                                                                    boolean zIsFullyCollapsed = notificationPanelViewController15.isFullyCollapsed();
                                                                    NotificationPanelViewController notificationPanelViewController27 = NotificationPanelViewController.this;
                                                                    if (notificationPanelViewController15.mQsController.handleTouch(motionEvent, zIsFullyCollapsed, (notificationPanelViewController27.mHeightAnimator == null || notificationPanelViewController27.mIsSpringBackAnimation) ? false : true)) {
                                                                        if (motionEvent.getActionMasked() != 2) {
                                                                            NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "onTouch: handleQsTouch handled event");
                                                                        }
                                                                        QuickPanelLogger quickPanelLogger19 = NotificationPanelViewController.this.mQuickPanelLogger;
                                                                        if (quickPanelLogger19 != null) {
                                                                            quickPanelLogger19.onTouchEvent(motionEvent, "!mHeadsUpTouchHelper.isTrackingHeadsUp() && mQsController.handleTouch()", true);
                                                                            return true;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (NotificationPanelViewController.this.mMultiWindowEdgeDetector.isGestureDetected()) {
                                        NotificationPanelViewController.this.setMotionAborted();
                                    }
                                    NotificationPanelViewController.this.mShadeLog.logMotionEvent(motionEvent, "MultiWindowEdgeDetector - motion aborted.");
                                    QuickPanelLogger quickPanelLogger20 = NotificationPanelViewController.this.mQuickPanelLogger;
                                    if (quickPanelLogger20 != null) {
                                        quickPanelLogger20.onTouchEvent(motionEvent, "CoreRune.MW_FREEFORM_CORNER_GESTURE", true);
                                        return true;
                                    }
                                }
                            }
                        }
                        return true;
                    }
                    QuickPanelLogger quickPanelLogger21 = NotificationPanelViewController.this.mQuickPanelLogger;
                    if (quickPanelLogger21 != null) {
                        quickPanelLogger21.onTouchEvent(motionEvent, "DeviceEntryUdfpsRefactor.isEnabled() && mAlternateBouncerInteractor.isVisibleState()", false);
                        return false;
                    }
                } else {
                    QuickPanelLogger quickPanelLogger22 = notificationPanelViewController.mQuickPanelLogger;
                    if (quickPanelLogger22 != null) {
                        quickPanelLogger22.onTouchEvent(motionEvent, "!mUseExternalTouch", false);
                        return false;
                    }
                }
            }
            return false;
        }
    }

    /* renamed from: -$$Nest$maddMovement, reason: not valid java name */
    public static void m2943$$Nest$maddMovement(NotificationPanelViewController notificationPanelViewController, MotionEvent motionEvent) {
        notificationPanelViewController.getClass();
        float rawX = motionEvent.getRawX() - motionEvent.getX();
        float rawY = motionEvent.getRawY() - motionEvent.getY();
        motionEvent.offsetLocation(rawX, rawY);
        notificationPanelViewController.mVelocityTracker.addMovement(motionEvent);
        motionEvent.offsetLocation(-rawX, -rawY);
    }

    /* JADX WARN: Removed duplicated region for block: B:73:0x0164  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01dd  */
    /* renamed from: -$$Nest$mendMotionEvent, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m2944$$Nest$mendMotionEvent(NotificationPanelViewController notificationPanelViewController, MotionEvent motionEvent, float f, float f2, boolean z) throws Resources.NotFoundException {
        boolean z2;
        float f3;
        boolean z3;
        ShadeLogger shadeLogger = notificationPanelViewController.mShadeLog;
        shadeLogger.logEndMotionEvent("endMotionEvent called", z, false);
        notificationPanelViewController.mTrackingPointer = -1;
        notificationPanelViewController.mAmbientState.setSwipingUp(false);
        boolean zIsTracking = notificationPanelViewController.isTracking();
        KeyguardStateControllerImpl keyguardStateControllerImpl = notificationPanelViewController.mKeyguardStateController;
        if ((zIsTracking && notificationPanelViewController.mTouchSlopExceeded) || Math.abs(f - notificationPanelViewController.mInitialExpandX) > notificationPanelViewController.mTouchSlop || Math.abs(f2 - notificationPanelViewController.mInitialExpandY) > notificationPanelViewController.mTouchSlop || ((notificationPanelViewController.mExpandedFraction <= 0.98f && !notificationPanelViewController.isFullyCollapsed()) || motionEvent.getActionMasked() == 3 || z)) {
            notificationPanelViewController.mVelocityTracker.computeCurrentVelocity(1000);
            float yVelocity = notificationPanelViewController.mVelocityTracker.getYVelocity();
            float fHypot = (float) Math.hypot(notificationPanelViewController.mVelocityTracker.getXVelocity(), notificationPanelViewController.mVelocityTracker.getYVelocity());
            boolean z4 = keyguardStateControllerImpl.mShowing;
            if (keyguardStateControllerImpl.mKeyguardFadingAway || (notificationPanelViewController.mInitialTouchFromKeyguard && !z4)) {
                z2 = z4;
                f3 = 0.0f;
                z3 = false;
            } else if (motionEvent.getActionMasked() == 3 || z) {
                z2 = z4;
                f3 = 0.0f;
                if (z2) {
                    shadeLogger.logEndMotionEvent("endMotionEvent: cancel while on keyguard", z, true);
                    z3 = true;
                } else {
                    if (notificationPanelViewController.mCollapsedAndHeadsUpOnDown) {
                        shadeLogger.logEndMotionEvent("endMotionEvent: cancel But should expand panel with heads up", z, true);
                        Log.d("NotificationPanelView", "endMotionEvent: cancel But should expand panel with heads up: force: " + z + ", returned without onTrackingStopped()");
                        return;
                    }
                    z3 = !notificationPanelViewController.mPanelClosedOnDown;
                    shadeLogger.logEndMotionEvent("endMotionEvent: cancel", z, z3);
                }
            } else {
                int i = 4;
                if (NotiRune.NOTI_STYLE_POP_OVER_COLLAPSE && ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet() && motionEvent.getActionMasked() == 4 && notificationPanelViewController.mBarState != 1 && notificationPanelViewController.isFullyExpanded()) {
                    z3 = true;
                    z2 = z4;
                    f3 = 0.0f;
                } else {
                    if (notificationPanelViewController.mFalsingManager.isUnlockingDisabled()) {
                        z2 = z4;
                        f3 = 0.0f;
                    } else {
                        if (f2 - notificationPanelViewController.mInitialExpandY > 0.0f) {
                            i = 0;
                        } else if (!keyguardStateControllerImpl.mCanDismissLockScreen) {
                            i = 8;
                        }
                        float f4 = notificationPanelViewController.mFlingAnimationUtils.mMinVelocityPxPerSecond;
                        boolean z5 = notificationPanelViewController.mExpandedFraction > 0.5f;
                        boolean z6 = notificationPanelViewController.mAllowExpandForSmallExpansion;
                        LogLevel logLevel = LogLevel.VERBOSE;
                        f3 = 0.0f;
                        ShadeLogger$$ExternalSyntheticLambda0 shadeLogger$$ExternalSyntheticLambda0 = new ShadeLogger$$ExternalSyntheticLambda0(8);
                        LogBuffer logBuffer = shadeLogger.buffer;
                        LogMessage logMessageObtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$$ExternalSyntheticLambda0, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                        logMessageImpl.int1 = i;
                        z2 = z4;
                        logMessageImpl.long1 = (long) yVelocity;
                        logMessageImpl.long2 = (long) fHypot;
                        logMessageImpl.double1 = f4;
                        logMessageImpl.bool1 = z5;
                        logMessageImpl.bool2 = z6;
                        logBuffer.commit(logMessageObtain);
                        z3 = Math.abs(fHypot) >= notificationPanelViewController.mFlingAnimationUtils.mMinVelocityPxPerSecond ? yVelocity > 0.0f : notificationPanelViewController.mExpandedFraction > 0.5f;
                        if (notificationPanelViewController.mQsController.mExpansionAnimator != null) {
                            z3 = true;
                        }
                    }
                    if (notificationPanelViewController.mQsController.mExpansionAnimator != null) {
                    }
                }
                if (notificationPanelViewController.mBarState != 1 && z3 && !notificationPanelViewController.isFullyExpanded() && notificationPanelViewController.mListenForHeadsUp) {
                    Log.d("NotificationPanelView", "previous heightAnimator cancel due to headsup");
                    notificationPanelViewController.cancelHeightAnimator();
                }
                shadeLogger.logEndMotionEvent("endMotionEvent: flingExpands", z, z3);
            }
            boolean z7 = notificationPanelViewController.mTouchAboveFalsingThreshold;
            WakefulnessModel wakefulnessModel = (WakefulnessModel) notificationPanelViewController.mPowerInteractor.detailedWakefulness.$$delegate_0.getValue();
            if (wakefulnessModel.isAwake()) {
                WakeSleepReason wakeSleepReason = WakeSleepReason.TAP;
                WakeSleepReason wakeSleepReason2 = wakefulnessModel.lastWakeReason;
                boolean z8 = wakeSleepReason2 == wakeSleepReason || wakeSleepReason2 == WakeSleepReason.GESTURE;
                DozeLogger dozeLogger = notificationPanelViewController.mDozeLog.mLogger;
                dozeLogger.getClass();
                LogLevel logLevel2 = LogLevel.DEBUG;
                DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda0 = new DozeLogger$$ExternalSyntheticLambda0(20);
                LogBuffer logBuffer2 = dozeLogger.buffer;
                LogMessage logMessageObtain2 = logBuffer2.obtain("DozeLog", logLevel2, dozeLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
                logMessageImpl2.bool1 = z3;
                logMessageImpl2.bool2 = z7;
                logMessageImpl2.bool4 = z8;
                logBuffer2.commit(logMessageObtain2);
                if (!z3 && z2) {
                    float displayDensity = notificationPanelViewController.getDisplayDensity();
                    notificationPanelViewController.mLockscreenGestureLogger.write(186, (int) Math.abs((f2 - notificationPanelViewController.mInitialExpandY) / displayDensity), (int) Math.abs(yVelocity / displayDensity));
                    new UiEventLoggerImpl().log(LockscreenGestureLogger.LockscreenUiEvent.LOCKSCREEN_UNLOCK);
                }
                if (notificationPanelViewController.mBarState != 1 || notificationPanelViewController.mExpandedFraction < 1.0d) {
                    notificationPanelViewController.fling(yVelocity, 1.0f, z3);
                } else {
                    shadeLogger.d("NPVC endMotionEvent - skipping fling on keyguard");
                    if (notificationPanelViewController.mUseExternalTouch) {
                        notificationPanelViewController.mNotificationStackScrollLayoutController.setOverExpansion(f3);
                    }
                }
                notificationPanelViewController.onTrackingStopped(z3);
                boolean z9 = z3 && notificationPanelViewController.mPanelClosedOnDown && !notificationPanelViewController.mHasLayoutedSinceDown;
                notificationPanelViewController.mUpdateFlingOnLayout = z9;
                if (z9) {
                    notificationPanelViewController.mUpdateFlingVelocity = yVelocity;
                }
            }
        } else if (notificationPanelViewController.mCentralSurfaces.mBouncerShowing || notificationPanelViewController.mAlternateBouncerInteractor.isVisibleState() || keyguardStateControllerImpl.mKeyguardGoingAway) {
            Log.d("NotificationPanelView", "endMotionEvent: ELSE: returned without onTrackingStopped()");
        } else {
            notificationPanelViewController.onEmptySpaceClick(f, f2);
            notificationPanelViewController.onTrackingStopped(true);
        }
        notificationPanelViewController.mVelocityTracker.clear();
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x008a  */
    /* renamed from: -$$Nest$minitDownStates, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m2945$$Nest$minitDownStates(NotificationPanelViewController notificationPanelViewController, MotionEvent motionEvent) {
        boolean z;
        int bottom;
        notificationPanelViewController.getClass();
        boolean z2 = false;
        if (motionEvent.getActionMasked() != 0) {
            notificationPanelViewController.mLastEventSynthesizedDown = false;
            return;
        }
        notificationPanelViewController.mDozingOnDown = notificationPanelViewController.mDozing;
        notificationPanelViewController.mOnlyAffordanceInThisMotion = false;
        KeyguardSecAffordanceHelper keyguardSecAffordanceHelper = notificationPanelViewController.mSecAffordanceHelper;
        if (keyguardSecAffordanceHelper != null) {
            keyguardSecAffordanceHelper.isShortcutPreviewSwipingInProgress = false;
        }
        notificationPanelViewController.mDownX = motionEvent.getX();
        notificationPanelViewController.mDownY = motionEvent.getY();
        boolean zIsFullyCollapsed = notificationPanelViewController.isFullyCollapsed();
        notificationPanelViewController.mCollapsedOnDown = zIsFullyCollapsed;
        QuickSettingsControllerImpl quickSettingsControllerImpl = notificationPanelViewController.mQsController;
        quickSettingsControllerImpl.mCollapsedOnDown = zIsFullyCollapsed;
        if (notificationPanelViewController.mNotificationStackScrollLayoutController.mView.getOwnScrollY() >= quickSettingsControllerImpl.mMinExpansionHeight - notificationPanelViewController.mQuickQsOffsetHeight) {
            notificationPanelViewController.mIsPanelCollapseOnQQS = false;
        } else {
            float f = notificationPanelViewController.mDownX;
            float f2 = notificationPanelViewController.mDownY;
            if (quickSettingsControllerImpl.mCollapsedOnDown || quickSettingsControllerImpl.mBarState == 1 || quickSettingsControllerImpl.getExpanded()) {
                z = false;
                notificationPanelViewController.mIsPanelCollapseOnQQS = z;
            } else {
                QS qs = quickSettingsControllerImpl.mQs;
                if (qs == null) {
                    bottom = quickSettingsControllerImpl.mKeyguardStatusBar.getBottom();
                } else {
                    int i = QSComposeFragment.$r8$clinit;
                    bottom = qs.getHeader().getBottom();
                }
                if (f >= quickSettingsControllerImpl.mQsFrame.getX() && f <= quickSettingsControllerImpl.mQsFrame.getX() + quickSettingsControllerImpl.mQsFrame.getWidth() && f2 <= bottom) {
                    z = true;
                }
                notificationPanelViewController.mIsPanelCollapseOnQQS = z;
            }
        }
        if (notificationPanelViewController.mCollapsedOnDown && ((HeadsUpManagerImpl) notificationPanelViewController.mHeadsUpManager).mHasPinnedNotification) {
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
    public static void m2946$$Nest$mstartExpandMotion(NotificationPanelViewController notificationPanelViewController, float f, float f2, boolean z, float f3) {
        if (!notificationPanelViewController.mHandlingPointerUp && !notificationPanelViewController.mStatusBarStateController.isDozing()) {
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

    /* JADX WARN: Type inference failed for: r11v3, types: [com.android.systemui.shade.NotificationPanelViewController$2] */
    /* JADX WARN: Type inference failed for: r15v5, types: [com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda11] */
    /* JADX WARN: Type inference failed for: r15v9, types: [com.android.systemui.shade.NotificationPanelViewController$1] */
    /* JADX WARN: Type inference failed for: r9v10, types: [com.android.systemui.shade.NotificationPanelViewController$17] */
    /* JADX WARN: Type inference failed for: r9v11, types: [com.android.systemui.shade.NotificationPanelViewController$18] */
    /* JADX WARN: Type inference failed for: r9v9, types: [com.android.systemui.shade.NotificationPanelViewController$11] */
    public NotificationPanelViewController(DcmMascotViewContainer dcmMascotViewContainer, PluginLockMediator pluginLockMediator, NotificationPanelView notificationPanelView, KeyguardTouchAnimator keyguardTouchAnimator, Lazy lazy, NotificationWakeUpCoordinator notificationWakeUpCoordinator, PulseExpansionHandler pulseExpansionHandler, DynamicPrivacyController dynamicPrivacyController, KeyguardBypassController keyguardBypassController, FalsingManager falsingManager, FalsingCollector falsingCollector, KeyguardStateController keyguardStateController, StatusBarStateController statusBarStateController, NotificationShadeWindowController notificationShadeWindowController, DozeLog dozeLog, DozeParameters dozeParameters, CommandQueue commandQueue, VibratorHelper vibratorHelper, LatencyTracker latencyTracker, AccessibilityManager accessibilityManager, int i, KeyguardUpdateMonitor keyguardUpdateMonitor, MetricsLogger metricsLogger, ShadeLogger shadeLogger, ConfigurationController configurationController, Provider provider, ShadeTouchableRegionManager shadeTouchableRegionManager, ConversationNotificationManager conversationNotificationManager, MediaHierarchyManager mediaHierarchyManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, NotificationGutsManager notificationGutsManager, NotificationsQSContainerController notificationsQSContainerController, NotificationStackScrollLayoutController notificationStackScrollLayoutController, KeyguardStatusBarViewComponent.Factory factory, LockscreenShadeTransitionController lockscreenShadeTransitionController, ScrimController scrimController, MediaDataManager mediaDataManager, NotificationShadeDepthController notificationShadeDepthController, AmbientState ambientState, SecLockIconViewController secLockIconViewController, KeyguardMediaController keyguardMediaController, TapAgainViewController tapAgainViewController, NavigationModeController navigationModeController, NavigationBarController navigationBarController, QuickSettingsControllerImpl quickSettingsControllerImpl, FragmentService fragmentService, IStatusBarService iStatusBarService, ShadeHeaderController shadeHeaderController, ScreenOffAnimationController screenOffAnimationController, LockscreenGestureLogger lockscreenGestureLogger, ShadeExpansionStateManager shadeExpansionStateManager, ShadeRepository shadeRepository, Optional<SysUIUnfoldComponent> optional, SysUiState sysUiState, SysUIStateDisplaysInteractor sysUIStateDisplaysInteractor, Provider provider2, KeyguardWallpaperController keyguardWallpaperController, WallpaperImageInjectCreator wallpaperImageInjectCreator, EmergencyButtonController.Factory factory2, KeyguardUnlockAnimationController keyguardUnlockAnimationController, KeyguardIndicationController keyguardIndicationController, NotificationListContainer notificationListContainer, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, SystemClock systemClock, KeyguardClockInteractor keyguardClockInteractor, AlternateBouncerInteractor alternateBouncerInteractor, DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel, CoroutineDispatcher coroutineDispatcher, KeyguardTransitionInteractor keyguardTransitionInteractor, DumpManager dumpManager, KeyguardTouchHandlingViewModel keyguardTouchHandlingViewModel, WallpaperFocalAreaViewModel wallpaperFocalAreaViewModel, KeyguardInteractor keyguardInteractor, ActivityStarter activityStarter, SharedNotificationContainerInteractor sharedNotificationContainerInteractor, ActiveNotificationsInteractor activeNotificationsInteractor, ShadeAnimationInteractor shadeAnimationInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, SplitShadeStateController splitShadeStateController, PowerInteractor powerInteractor, KeyguardClockPositionAlgorithm keyguardClockPositionAlgorithm, MSDLPlayer mSDLPlayer, BrightnessMirrorShowingRepository brightnessMirrorShowingRepository, BlurConfig blurConfig, Lazy lazy2, PrivacyDialogController privacyDialogController, KeyguardPunchHoleVIViewController.Factory factory3, NotificationShelfManager notificationShelfManager, KeyguardEditModeController keyguardEditModeController, KeyguardClickController keyguardClickController, PluginLockData pluginLockData, Lazy lazy3, Lazy lazy4, LockscreenNotificationManager lockscreenNotificationManager, LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController, Lazy lazy5, QsStatusEventLog qsStatusEventLog, SelectedUserInteractor selectedUserInteractor, SecQuickSettingsAffordanceInteractor secQuickSettingsAffordanceInteractor, SecQSPanelComposeAdapter secQSPanelComposeAdapter, SecHideNotificationShadeInMirrorInteractor secHideNotificationShadeInMirrorInteractor, PanelPopOverManager panelPopOverManager, SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor) throws Resources.NotFoundException {
        ImageView imageView;
        FrameLayout frameLayout;
        int i2 = 0;
        this.mKeyguardAffordanceHelperCallback = new KeyguardAffordanceHelperCallback(this, i2);
        this.mOnHeadsUpChangedListener = new ShadeHeadsUpChangedListener(this, i2);
        this.mConfigurationListener = new ConfigurationListener(this, i2);
        this.mStatusBarStateListener = new StatusBarStateListener(this, i2);
        this.mAccessibilityDelegate = new ShadeAccessibilityDelegate(this, i2);
        this.mShadeHeadsUpTracker = new ShadeHeadsUpTrackerImpl(this, i2);
        BiConsumer biConsumer = new BiConsumer() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda21
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                NotificationPanelViewController notificationPanelViewController = this.f$0;
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                float fFloatValue = ((Float) obj2).floatValue();
                ((KeyguardRepositoryImpl) notificationPanelViewController.mKeyguardInteractor.repository).panelAlpha.updateState(null, Float.valueOf(fFloatValue / 255.0f));
                int i3 = (int) fFloatValue;
                NotificationPanelView notificationPanelView2 = notificationPanelViewController.mView;
                notificationPanelView2.mCurrentPanelAlpha = i3;
                notificationPanelView2.mAlphaPaint.setARGB(i3, 255, 255, 255);
                notificationPanelView2.invalidate();
            }
        };
        NotificationPanelViewController$$ExternalSyntheticLambda10 notificationPanelViewController$$ExternalSyntheticLambda10 = new NotificationPanelViewController$$ExternalSyntheticLambda10(1);
        AnimatableProperty.AnonymousClass7 anonymousClass7 = AnimatableProperty.Y;
        AnimatableProperty.AnonymousClass6 anonymousClass6 = new AnimatableProperty.AnonymousClass6(R.id.panel_alpha_animator_start_tag, R.id.panel_alpha_animator_end_tag, R.id.panel_alpha_animator_tag, new AnimatableProperty.AnonymousClass5("panelAlpha", notificationPanelViewController$$ExternalSyntheticLambda10, biConsumer));
        this.mPanelAlphaAnimator = anonymousClass6;
        AnimationProperties animationProperties = new AnimationProperties();
        animationProperties.duration = 150L;
        Property property = anonymousClass6.val$property;
        Interpolator interpolator = Interpolators.ALPHA_OUT;
        animationProperties.setCustomInterpolator(property, interpolator);
        this.mPanelAlphaOutPropertiesAnimator = animationProperties;
        AnimationProperties animationProperties2 = new AnimationProperties();
        animationProperties2.duration = 200L;
        animationProperties2.mAnimationEndAction = new NotificationPanelViewController$$ExternalSyntheticLambda16(this, 2);
        animationProperties2.setCustomInterpolator(anonymousClass6.val$property, Interpolators.ALPHA_IN);
        this.mPanelAlphaInPropertiesAnimator = animationProperties2;
        this.mCurrentPanelState = 0;
        this.mHasVibratedOnOpen = false;
        this.mFixedDuration = -1;
        this.mLastGesturedOverExpansion = -1.0f;
        this.mExpandedFraction = 0.0f;
        this.mExpansionDragDownAmountPx = 0.0f;
        this.mNextCollapseSpeedUpFactor = 1.0f;
        this.mUseExternalTouch = false;
        this.mIsOcclusionTransitionRunning = false;
        this.mFlingCollapseRunnable = new NotificationPanelViewController$$ExternalSyntheticLambda18(this, 5);
        this.mAnimateKeyguardBottomAreaInvisibleEndRunnable = new NotificationPanelViewController$$ExternalSyntheticLambda18(this, 6);
        this.mHeadsUpExistenceChangedRunnable = new NotificationPanelViewController$$ExternalSyntheticLambda18(this, 7);
        this.mMaybeHideExpandedRunnable = new NotificationPanelViewController$$ExternalSyntheticLambda18(this, 8);
        this.mIsFaceWidgetOnTouchDown = false;
        this.mFullScreenModeEnabled = false;
        this.mPanelInVisibleReason = -1;
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
                    notificationPanelViewController.mView.getRootView().setAccessibilityPaneTitle(notificationPanelViewController.determineAccessibilityPaneTitle());
                    if (this.mPanelState == 1 && notificationPanelViewController.isPanelExpanded() && !notificationPanelViewController.isOnKeyguard()) {
                        notificationPanelViewController.updateEntrySetRead();
                    }
                }
            }
        };
        this.mPanelExpandForBiometric = false;
        this.mPanelPopOverManager = null;
        this.mSecQsUiDisplayModeInteractor = null;
        this.mLockscreenShadeTransitonCallback = new LockscreenShadeTransitionController.Callback() { // from class: com.android.systemui.shade.NotificationPanelViewController.11
            @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
            public final void setTransitionToFullShadeAmount(float f) {
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                Log.d("NotificationPanelView", "setTransitionToFullShadeAmount  " + f);
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                FaceWidgetContainerWrapper faceWidgetContainerWrapper = notificationPanelViewController.mKeyguardStatusBase;
                View view = faceWidgetContainerWrapper.mClockContainer;
                if (view == null) {
                    view = faceWidgetContainerWrapper.mFaceWidgetContainer;
                }
                if (view instanceof ViewGroup) {
                    View childAt = ((ViewGroup) view).getChildAt(0);
                    float f2 = view.getResources().getDisplayMetrics().heightPixels * 0.325f * f;
                    if (childAt != null) {
                        childAt.setTranslationY(f2);
                        if (notificationPanelViewController.mBarState == 1) {
                            childAt.setAlpha(1.0f - f);
                        }
                    }
                    View viewProvideComplication = notificationPanelViewController.provideComplication();
                    if (viewProvideComplication == null || viewProvideComplication.getVisibility() != 0) {
                        return;
                    }
                    viewProvideComplication.setTranslationY(f2);
                    if (notificationPanelViewController.mBarState == 1) {
                        viewProvideComplication.setAlpha(1.0f - f);
                    }
                }
            }
        };
        this.mDownEventFromOverView = null;
        this.mShadeViewStateProvider = new ShadeViewStateProvider() { // from class: com.android.systemui.shade.NotificationPanelViewController.17
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
                return headsUpAppearanceController != null && headsUpAppearanceController.shouldHeadsUpStatusBarBeVisible();
            }
        };
        this.mDataUsageLabelParent = null;
        this.mSystemUIWidgetCallback = new SystemUIWidgetCallback() { // from class: com.android.systemui.shade.NotificationPanelViewController.18
            @Override // com.android.systemui.widget.SystemUIWidgetCallback
            public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                Log.d("NotificationPanelView", "updateStyle, onNavigationColorUpdateRequired");
                CentralSurfacesImpl centralSurfacesImpl = NotificationPanelViewController.this.mCentralSurfaces;
                boolean zIsWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper("navibar");
                StatusBarWindowView statusBarWindowView = ((StatusBarWindowControllerImpl) ((StatusBarWindowController) centralSurfacesImpl.mStatusBarWindowControllerStore.getDefaultDisplay())).mStatusBarWindowView;
                int systemUiVisibility = statusBarWindowView.getSystemUiVisibility();
                statusBarWindowView.setSystemUiVisibility(zIsWhiteKeyguardWallpaper ? systemUiVisibility | 16 : systemUiVisibility & (-17));
            }
        };
        int i3 = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
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
        this.mKeyguardTransitionInteractor = keyguardTransitionInteractor;
        this.mSharedNotificationContainerInteractor = sharedNotificationContainerInteractor;
        this.mActiveNotificationsInteractor = activeNotificationsInteractor;
        this.mKeyguardInteractor = keyguardInteractor;
        this.mLockscreenNotificationManager = lockscreenNotificationManager;
        this.mLockscreenNotificationIconsOnlyController = lockscreenNotificationIconsOnlyController;
        this.mPowerInteractor = powerInteractor;
        this.mEmergencyButtonControllerFactory = factory2;
        this.mPrivacyDialogController = privacyDialogController;
        this.mClockPositionAlgorithm = keyguardClockPositionAlgorithm;
        PluginFaceWidgetManager pluginFaceWidgetManager = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
        if (pluginFaceWidgetManager != null) {
            Log.d("PluginFaceWidgetManager", "setNPVController() controller = " + this);
            pluginFaceWidgetManager.mNPVController = this;
        }
        this.mKeyguardTouchAnimator = keyguardTouchAnimator;
        AnonymousClass4 anonymousClass4 = new AnonymousClass4();
        keyguardTouchAnimator.viewInjector = this;
        keyguardTouchAnimator.callback = anonymousClass4;
        this.mPunchHoleVIViewControllerFactory = factory3;
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
        notificationPanelView.mOnConfigurationChangedListener = new NotificationPanelViewController$$ExternalSyntheticLambda0(this);
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
        this.mFalsingManager = falsingManager;
        this.mDozeLog = dozeLog;
        this.mNotificationsDragEnabled = resources.getBoolean(R.bool.config_enableNotificationShadeDrag);
        this.mVibratorHelper = vibratorHelper;
        this.mVibrateOnOpening = resources.getBoolean(R.bool.config_vibrateOnIconAnimation);
        this.mShadeTouchableRegionManager = shadeTouchableRegionManager;
        this.mSystemClock = systemClock;
        this.mKeyguardMediaController = keyguardMediaController;
        this.mMetricsLogger = metricsLogger;
        this.mConfigurationController = configurationController;
        this.mFlingAnimationUtilsBuilder = provider;
        this.mMediaHierarchyManager = mediaHierarchyManager;
        this.mNotificationsQSContainerController = notificationsQSContainerController;
        this.mNotificationListContainer = notificationListContainer;
        this.mNavigationBarController = navigationBarController;
        KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = (KeyguardSecBottomAreaViewController) provider2.get();
        this.mKeyguardSecBottomAreaViewController = keyguardSecBottomAreaViewController;
        keyguardSecBottomAreaViewController.init();
        notificationsQSContainerController.init();
        this.mNotificationStackScrollLayoutController = notificationStackScrollLayoutController;
        this.mKeyguardStatusBarViewComponentFactory = factory;
        this.mDepthController = notificationShadeDepthController;
        this.mFragmentService = fragmentService;
        this.mStatusBarService = iStatusBarService;
        this.mSplitShadeStateController = splitShadeStateController;
        ((SplitShadeStateControllerImpl) splitShadeStateController).shouldUseSplitNotificationShade();
        notificationPanelView.setWillNotDraw(true);
        this.mShadeHeaderController = shadeHeaderController;
        this.mFalsingCollector = falsingCollector;
        this.mWakeUpCoordinator = notificationWakeUpCoordinator;
        this.mMainDispatcher = coroutineDispatcher;
        this.mAccessibilityManager = accessibilityManager;
        notificationPanelView.getRootView().setAccessibilityPaneTitle(determineAccessibilityPaneTitle());
        setAlpha(255, false);
        this.mCommandQueue = commandQueue;
        this.mDisplayId = i;
        this.mPulseExpansionHandler = pulseExpansionHandler;
        this.mDozeParameters = dozeParameters;
        this.mScrimController = scrimController;
        this.mMediaDataManager = mediaDataManager;
        this.mTapAgainViewController = tapAgainViewController;
        this.mSysUiState = sysUiState;
        this.mSysUIStateDisplaysInteractor = sysUIStateDisplaysInteractor;
        this.mShadeDisplaysRepository = lazy2;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mLockscreenShadeTransitionController = lockscreenShadeTransitionController;
        dynamicPrivacyController.mListeners.add(new DynamicPrivacyController.Listener() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda1
            @Override // com.android.systemui.statusbar.notification.DynamicPrivacyController.Listener
            public final void onDynamicPrivacyChanged() {
                NotificationPanelViewController notificationPanelViewController = this.f$0;
                if (notificationPanelViewController.mLinearDarkAmount != 0.0f) {
                    return;
                }
                notificationPanelViewController.mAnimateNextPositionUpdate = true;
            }
        });
        quickSettingsControllerImpl.mExpansionHeightListener = new NotificationPanelViewController$$ExternalSyntheticLambda0(this);
        quickSettingsControllerImpl.mApplyClippingImmediatelyListener = new NotificationPanelViewController$$ExternalSyntheticLambda0(this);
        quickSettingsControllerImpl.mFlingQsWithoutClickListener = new NotificationPanelViewController$$ExternalSyntheticLambda0(this);
        quickSettingsControllerImpl.mExpansionHeightSetToMaxListener = new NotificationPanelViewController$$ExternalSyntheticLambda0(this);
        shadeExpansionStateManager.stateListeners.add(new ShadeStateListener() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda6
            @Override // com.android.systemui.shade.ShadeStateListener
            public final void onPanelStateChanged$2(int i4) {
                ShadeControllerImpl.AnonymousClass2 anonymousClass2;
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                NotificationPanelViewController notificationPanelViewController = this.f$0;
                ShadeLogger shadeLogger2 = notificationPanelViewController.mShadeLog;
                shadeLogger2.getClass();
                LogLevel logLevel = LogLevel.VERBOSE;
                ShadeLogger$$ExternalSyntheticLambda0 shadeLogger$$ExternalSyntheticLambda0 = new ShadeLogger$$ExternalSyntheticLambda0(14);
                LogBuffer logBuffer = shadeLogger2.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) logMessageObtain).str1 = ShadeExpansionStateManagerKt.panelStateToString(i4);
                logBuffer.commit(logMessageObtain);
                QuickSettingsControllerImpl quickSettingsControllerImpl2 = notificationPanelViewController.mQsController;
                quickSettingsControllerImpl2.updateExpansionEnabledAmbient();
                if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK && i4 == 1 && !((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.sDependency.getDependencyInner(KeyguardFoldController.class))).isFoldOpened() && notificationPanelViewController.mScrimController.mState == ScrimState.AOD) {
                    notificationPanelViewController.cancelPendingCollapse(true);
                }
                NotificationPanelView notificationPanelView2 = notificationPanelViewController.mView;
                if (i4 == 2 && notificationPanelViewController.mCurrentPanelState != i4) {
                    quickSettingsControllerImpl2.setExpandImmediate(false);
                    try {
                        notificationPanelView2.sendAccessibilityEvent(32);
                    } catch (IllegalArgumentException e) {
                        Log.w("NotificationPanelView", "sendAccessibilityEvent failed. on onPanelStateChanged");
                        e.printStackTrace();
                    }
                }
                if (i4 == 1 && (anonymousClass2 = notificationPanelViewController.mOpenCloseListener) != null) {
                    ShadeControllerImpl.this.makeExpandedVisible(false);
                }
                if (i4 == 0) {
                    if (SecPanelSplitHelper.isEnabled()) {
                        notificationPanelViewController.setOnStatusBarDownEvent(null);
                    }
                    quickSettingsControllerImpl2.setExpandImmediate(false);
                    notificationPanelViewController.mInstantExpanding = false;
                    QuickPanelLogger quickPanelLogger = notificationPanelViewController.mQuickPanelLogger;
                    if (quickPanelLogger != null) {
                        quickPanelLogger.logPanelState("onPanelStateChanged: STATE_CLOSED");
                    }
                    notificationPanelView2.post(notificationPanelViewController.mMaybeHideExpandedRunnable);
                }
                notificationPanelViewController.mCurrentPanelState = i4;
            }
        });
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.mBottomAreaShadeAlphaAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.addUpdateListener(new NotificationPanelViewController$$ExternalSyntheticLambda7(this, 0));
        valueAnimatorOfFloat.setDuration(160L);
        valueAnimatorOfFloat.setInterpolator(interpolator);
        this.mConversationNotificationManager = conversationNotificationManager;
        this.mLockIconViewController = secLockIconViewController;
        this.mScreenOffAnimationController = screenOffAnimationController;
        this.mUnlockedScreenOffAnimationController = unlockedScreenOffAnimationController;
        this.mLastDownEvents = new NPVCDownEventState.Buffer(50);
        this.mDeviceEntryFaceAuthInteractor = deviceEntryFaceAuthInteractor;
        this.mIsGestureNavigation = QuickStepContract.isGesturalMode(navigationModeController.addListener(new NavigationModeController.ModeChangedListener() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda8
            @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
            public final void onNavigationModeChanged(int i4) {
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                NotificationPanelViewController notificationPanelViewController = this.f$0;
                notificationPanelViewController.getClass();
                notificationPanelViewController.mIsGestureNavigation = QuickStepContract.isGesturalMode(i4);
            }
        }));
        notificationPanelView.setBackgroundColor(0);
        ShadeAttachStateChangeListener shadeAttachStateChangeListener = new ShadeAttachStateChangeListener(this, 0);
        notificationPanelView.addOnAttachStateChangeListener(shadeAttachStateChangeListener);
        if (notificationPanelView.isAttachedToWindow()) {
            shadeAttachStateChangeListener.onViewAttachedToWindow(notificationPanelView);
        }
        notificationPanelView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda9
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                PanelPopOverManager panelPopOverManager2;
                SecQSPanel.QSTileLayout qSTileLayout;
                NotificationPanelViewController notificationPanelViewController = this.f$0;
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD_HID_BUT_UDC_CUTOUT) {
                    IndicatorCutoutUtil.Companion.getClass();
                    windowInsets = IndicatorCutoutUtil.Companion.getHidWindowInsetsFromUDC(windowInsets);
                    if (windowInsets == null) {
                        return null;
                    }
                }
                Insets insetsIgnoringVisibility = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
                notificationPanelViewController.mDisplayTopInset = insetsIgnoringVisibility.top;
                int i4 = insetsIgnoringVisibility.right;
                notificationPanelViewController.mDisplayRightInset = i4;
                int i5 = insetsIgnoringVisibility.left;
                notificationPanelViewController.mDisplayLeftInset = i5;
                QuickSettingsControllerImpl quickSettingsControllerImpl2 = notificationPanelViewController.mQsController;
                quickSettingsControllerImpl2.mDisplayLeftInset = i5;
                quickSettingsControllerImpl2.mDisplayRightInset = i4;
                SecNotificationPanelViewController secNotificationPanelViewController = notificationPanelViewController.mSecNotificationPanelViewController;
                int stableInsetBottom = (secNotificationPanelViewController == null || notificationPanelViewController.mBarState != 0) ? windowInsets.getStableInsetBottom() : windowInsets.getInsets(WindowInsets.Type.navigationBars()).bottom;
                notificationPanelViewController.mNavigationBarBottomHeight = stableInsetBottom;
                notificationPanelViewController.updateMaxHeadsUpTranslation();
                if (secNotificationPanelViewController != null) {
                    Context context = notificationPanelViewController.mView.getContext();
                    int i6 = notificationPanelViewController.mDisplayTopInset;
                    int i7 = notificationPanelViewController.mNavigationBarBottomHeight;
                    SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) secNotificationPanelViewController.resourcePicker$delegate.getValue();
                    SecQSPanelResourceNormalPicker targetPicker = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker();
                    targetPicker.getClass();
                    SecQSPanelResourceCommon.Companion.getClass();
                    if (SecQSPanelResourceCommon.Companion.isPortrait(context)) {
                        targetPicker.cutoutHeight = i6;
                    } else {
                        targetPicker.cutoutHeightLandscape = i6;
                    }
                    SecQSPanelResourceNormalPicker targetPicker2 = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker();
                    targetPicker2.getClass();
                    if (SecQSPanelResourceCommon.Companion.isPortrait(context)) {
                        targetPicker2.navBarHeight = i7;
                    } else {
                        targetPicker2.navBarHeightLandscape = i7;
                    }
                    SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = secNotificationPanelViewController.secQuickSettingsControllerImpl;
                    if (secQuickSettingsControllerImpl != null && (i6 != secQuickSettingsControllerImpl.lastDisplayTopInset || i7 != secQuickSettingsControllerImpl.lastNavigationBarBottomHeight)) {
                        secQuickSettingsControllerImpl.lastDisplayTopInset = i6;
                        secQuickSettingsControllerImpl.lastNavigationBarBottomHeight = i7;
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
                if (QpRune.QUICK_PANEL_CODE_FOR_POP_OVER && (panelPopOverManager2 = notificationPanelViewController.mPanelPopOverManager) != null) {
                    panelPopOverManager2.navBarHeight = notificationPanelViewController.mNavigationBarBottomHeight;
                    if (!QpRune.QUICK_PANEL_CODE_FOR_POP_OVER_NOT_SET_TOUCHABLE_AREA) {
                        Insets insetsIgnoringVisibility2 = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemGestures());
                        int iWidth = panelPopOverManager2.context.getResources().getConfiguration().windowConfiguration.getBounds().width();
                        int iHeight = panelPopOverManager2.context.getResources().getConfiguration().windowConfiguration.getBounds().height();
                        int i8 = insetsIgnoringVisibility2.left;
                        int i9 = insetsIgnoringVisibility2.right;
                        int i10 = insetsIgnoringVisibility2.top;
                        int i11 = insetsIgnoringVisibility2.bottom;
                        Log.d("PanelPopOverManager", "setGestureArea gestureInsets: " + insetsIgnoringVisibility2);
                        int i12 = iHeight - i11;
                        panelPopOverManager2.leftGestureArea = new Rect(0, i10, i8, i12);
                        panelPopOverManager2.rightGestureArea = new Rect(iWidth - i9, i10, iWidth, i12);
                    }
                }
                notificationPanelViewController.updateNsslMargin();
                notificationPanelViewController.updateNsslWidth();
                if (QpRune.QUICK_DATA_USAGE_LABEL) {
                    ((DataUsageLabelManager) notificationPanelViewController.mDataUsageLabelManagerLazy.get()).updateNavBarHeight(notificationPanelViewController.mNavigationBarBottomHeight);
                }
                return windowInsets;
            }
        });
        this.mPluginLockMediator = pluginLockMediator;
        pluginLockMediator.registerStateCallback(this);
        this.mKeyguardUnfoldTransition = optional.map(new NotificationPanelViewController$$ExternalSyntheticLambda10(0));
        this.mKeyguardClockInteractor = keyguardClockInteractor;
        this.mActivityStarter = activityStarter;
        this.mBrightnessMirrorShowingRepository = brightnessMirrorShowingRepository;
        this.mIsBrightnessMirrorShowing.setValue((Boolean) brightnessMirrorShowingRepository.isShowing.$$delegate_0.getValue());
        if (CscRune.KEYGUARD_DCM_LIVE_UX) {
            this.mMascotViewContainer = dcmMascotViewContainer;
        }
        this.mSelectedUserInteractor = selectedUserInteractor;
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
            public final void onUnlockAnimationStarted(boolean z, boolean z2) throws Resources.NotFoundException {
                PluginKeyguardStatusView pluginKeyguardStatusView;
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                notificationPanelViewController.getClass();
                PluginFaceWidgetManager pluginFaceWidgetManager2 = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
                if (pluginFaceWidgetManager2 != null && (pluginKeyguardStatusView = pluginFaceWidgetManager2.mFaceWidgetPlugin) != null) {
                    pluginKeyguardStatusView.dismissFaceWidgetDashBoard();
                }
                boolean zIsTracking = notificationPanelViewController.isTracking();
                NotificationShadeDepthController notificationShadeDepthController2 = notificationPanelViewController.mDepthController;
                if (notificationShadeDepthController2.blursDisabledForUnlock != zIsTracking) {
                    notificationShadeDepthController2.blursDisabledForUnlock = zIsTracking;
                    notificationShadeDepthController2.scheduleUpdate();
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
        int i4 = 0;
        int i5 = 1;
        this.mSecNotificationPanelViewController = new SecNotificationPanelViewController(lockscreenShadeTransitionController, notificationsQSContainerController, quickSettingsControllerImpl, shadeHeaderController, shadeRepository, new NotificationPanelViewController$$ExternalSyntheticLambda12(this, i4), new NotificationPanelViewController$$ExternalSyntheticLambda13(this, i4), new NotificationPanelViewController$$ExternalSyntheticLambda12(this, i5), new NotificationPanelViewController$$ExternalSyntheticLambda15(this, i4), new NotificationPanelViewController$$ExternalSyntheticLambda16(this, i4), new NotificationPanelViewController$$ExternalSyntheticLambda16(this, i5), new NotificationPanelViewController$$ExternalSyntheticLambda18(this, i4), secHideNotificationShadeInMirrorInteractor);
        int i6 = 2;
        this.mPanelAgent = new PanelAgent(new NotificationPanelViewController$$ExternalSyntheticLambda16(this, i5), new NotificationPanelViewController$$ExternalSyntheticLambda18(this, i5), new NotificationPanelViewController$$ExternalSyntheticLambda13(this, i5), new NotificationPanelViewController$$ExternalSyntheticLambda13(this, i4), new NotificationPanelViewController$$ExternalSyntheticLambda18(this, i6), new NotificationPanelViewController$$ExternalSyntheticLambda18(this, 3), new NotificationPanelViewController$$ExternalSyntheticLambda13(this, i6), new NotificationPanelViewController$$ExternalSyntheticLambda25(this, i4), new NotificationPanelViewController$$ExternalSyntheticLambda25(this, 1));
        this.mPluginAODManagerLazy = lazy;
        this.mShelfManager = notificationShelfManager;
        notificationShelfManager.getClass();
        this.mMultiWindowEdgeDetector = new MultiWindowEdgeDetector(this.mView.getContext(), "QuickPannel");
        this.mSamsungBarExt = lazy4;
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController2 = this.mLockscreenNotificationIconsOnlyController;
        lockscreenNotificationIconsOnlyController2.getClass();
        Log.d("LockscreenNotificationIconsOnlyController", "setNPVController() controller = " + this);
        lockscreenNotificationIconsOnlyController2.mNPVController = this;
        NotificationPanelViewController$$ExternalSyntheticLambda18 notificationPanelViewController$$ExternalSyntheticLambda18 = new NotificationPanelViewController$$ExternalSyntheticLambda18(this, 4);
        this.mPostCollapseRunnable = notificationPanelViewController$$ExternalSyntheticLambda18;
        if (QpRune.QUICK_DATA_USAGE_LABEL) {
            this.mDataUsageLabelManagerLazy = lazy5;
        }
        this.mKeyguardEditModeController = keyguardEditModeController;
        NotificationPanelView notificationPanelView2 = this.mView;
        KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = (KeyguardEditModeControllerImpl) keyguardEditModeController;
        keyguardEditModeControllerImpl.getClass();
        ImageView imageView2 = (ImageView) notificationPanelView2.findViewById(R.id.keyguard_edit_mode_blur_effect);
        if (imageView2 != null && (imageView = (ImageView) notificationPanelView2.findViewById(R.id.keyguard_edit_mode_wallpaper)) != null && (frameLayout = (FrameLayout) notificationPanelView2.findViewById(R.id.keyguard_edit_mode_container)) != null) {
            keyguardEditModeControllerImpl.wallpaperCardView = (CardView) notificationPanelView2.findViewById(R.id.keyguard_edit_round_layout);
            keyguardEditModeControllerImpl.refreshRadius();
            keyguardEditModeControllerImpl.updateViewsFunction = new KeyguardEditModeControllerImpl$$ExternalSyntheticLambda2(keyguardEditModeControllerImpl, notificationPanelView2, imageView2, imageView, frameLayout);
            keyguardEditModeControllerImpl.initPreviewValues(notificationPanelView2.getContext());
        }
        keyguardEditModeControllerImpl.onStartActivityListener = new NotificationPanelViewController$$ExternalSyntheticLambda25(this, 2);
        ((ArrayList) keyguardEditModeControllerImpl.listeners).add(new KeyguardEditModeController.Listener() { // from class: com.android.systemui.shade.NotificationPanelViewController.7
            @Override // com.android.systemui.keyguard.KeyguardEditModeController.Listener
            public final void onAnimationStarted(boolean z) {
                if (z) {
                    NotificationPanelViewController.this.mCentralSurfaces.userActivity();
                }
            }

            @Override // com.android.systemui.keyguard.KeyguardEditModeController.Listener
            public final void onAnimationEnded() {
            }
        });
        ((KeyguardClickControllerImpl) keyguardClickController).isClickContainerArea = new Function2() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda29
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                boolean zContains;
                int iIntValue = ((Integer) obj).intValue();
                int iIntValue2 = ((Integer) obj2).intValue();
                NotificationPanelViewController notificationPanelViewController = this.f$0;
                FaceWidgetContainerWrapper faceWidgetContainerWrapper = notificationPanelViewController.mKeyguardStatusBase;
                if (faceWidgetContainerWrapper == null) {
                    return Boolean.FALSE;
                }
                View view = faceWidgetContainerWrapper.mClockContainer;
                if (view == null) {
                    view = faceWidgetContainerWrapper.mFaceWidgetContainer;
                }
                View iconContainer = notificationPanelViewController.mLockscreenNotificationIconsOnlyController.getIconContainer();
                if (view != null) {
                    Rect rect = new Rect();
                    view.getGlobalVisibleRect(rect);
                    zContains = rect.contains(iIntValue, iIntValue2);
                } else {
                    zContains = false;
                }
                if (iconContainer != null) {
                    Rect rect2 = new Rect();
                    iconContainer.getGlobalVisibleRect(rect2);
                    zContains |= rect2.contains(iIntValue, iIntValue2);
                }
                return Boolean.valueOf(zContains);
            }
        };
        this.mPluginLockData = pluginLockData;
        this.mPluginLockStarManagerLazy = lazy3;
        ((PluginLockStarManager) lazy3.get()).registerCallback("NotificationPanelViewController", this.mLockStarCallback);
        updateLockStarContainer();
        ((PluginLockStarManager) lazy3.get()).mShortcutController.bottomAreaCallback = keyguardSecBottomAreaViewController;
        this.mQuickQsOffsetHeight = SystemBarUtils.getQuickQsOffsetHeight(this.mView.getContext());
        PanelScreenShotLogger panelScreenShotLogger = PanelScreenShotLogger.INSTANCE;
        panelScreenShotLogger.addLogProvider("NotificationPanelView", this);
        this.mKeyguardWallpaperController = keyguardWallpaperController;
        this.mWallpaperImageCreator = wallpaperImageInjectCreator;
        this.mQsStatusEventLog = qsStatusEventLog;
        SecPanelSplitHelper secPanelSplitHelper = (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
        this.mPanelSplitHelper = secPanelSplitHelper;
        secPanelSplitHelper.addListener(this.mPanelTransitionStateListener);
        this.mSecQuickSettingsAffordanceInteractor = secQuickSettingsAffordanceInteractor;
        if (QpRune.QUICK_PANEL_CODE_FOR_POP_OVER) {
            this.mSecQsUiDisplayModeInteractor = secQsUiDisplayModeInteractor;
            this.mPanelPopOverManager = panelPopOverManager;
            panelPopOverManager.panelViewController = this;
            panelPopOverManager.mView = this.mView;
            panelPopOverManager.collapseRunnable = notificationPanelViewController$$ExternalSyntheticLambda18;
            panelPopOverManager.shadeExpansionStateManager.stateListeners.add(new PanelPopOverManager$setPanelController$1(panelPopOverManager));
            panelScreenShotLogger.addLogProvider("PanelPopOverManager", panelPopOverManager);
            lockscreenShadeTransitionController.addCallback(panelPopOverManager);
        }
    }

    public final void abortAnimations() {
        cancelHeightAnimator();
        NotificationPanelViewController$$ExternalSyntheticLambda18 notificationPanelViewController$$ExternalSyntheticLambda18 = this.mPostCollapseRunnable;
        NotificationPanelView notificationPanelView = this.mView;
        if (notificationPanelViewController$$ExternalSyntheticLambda18 != null) {
            notificationPanelView.removeCallbacks(notificationPanelViewController$$ExternalSyntheticLambda18);
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
        int i = this.mBarState;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        if (i == 1 && this.mKeyguardBypassController.getBypassEnabled()) {
            return notificationStackScrollLayoutController.mView.getHeight();
        }
        notificationStackScrollLayoutController.getClass();
        int i2 = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.getClass();
        int height = notificationStackScrollLayoutController.mView.getHeight() - Math.max(notificationStackScrollLayout.mMaxLayoutHeight - notificationStackScrollLayout.getContentHeight(), 0);
        if (this.mBarState != 1) {
            return height;
        }
        NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout2.getClass();
        return Math.max(height, (int) notificationStackScrollLayout2.mIntrinsicContentHeight);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeBackActionInteractor
    public final boolean canBeCollapsed() {
        CentralSurfacesImpl centralSurfacesImpl = this.mCentralSurfaces;
        if (centralSurfacesImpl == null || (centralSurfacesImpl.mCommandQueueCallbacks.mDisabled1 & 65536) == 0) {
            return (isFullyCollapsed() || isTracking() || isClosing()) ? false : true;
        }
        return true;
    }

    public boolean canCollapsePanelOnTouch() {
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        if (quickSettingsControllerImpl.getExpanded() || this.mBarState != 1) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
            notificationStackScrollLayoutController.getClass();
            int i = SceneContainerFlag.$r8$clinit;
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            notificationStackScrollLayout.getClass();
            NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayout.this;
            if (notificationStackScrollLayout2.getOwnScrollY() >= notificationStackScrollLayout2.getScrollRange()) {
                return true;
            }
            SecNotificationPanelViewController secNotificationPanelViewController = this.mSecNotificationPanelViewController;
            if (secNotificationPanelViewController != null) {
                boolean z = this.mIsPanelCollapseOnQQS;
                boolean z2 = quickSettingsControllerImpl.getExpanded() || this.mIsPanelCollapseOnQQS;
                SecPanelSplitHelper.Companion.getClass();
                if (!SecPanelSplitHelper.isEnabled) {
                    return z2;
                }
                SecPanelSplitHelper secPanelSplitHelper = secNotificationPanelViewController.panelSplitHelper;
                if (secPanelSplitHelper == null || !secPanelSplitHelper.isQSState()) {
                    return z;
                }
            } else if (!quickSettingsControllerImpl.getExpanded() && !this.mIsPanelCollapseOnQQS) {
                return false;
            }
        }
        return true;
    }

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
    public final void cancelInputFocusTransfer() throws Resources.NotFoundException {
        if (!this.mCommandQueue.panelsEnabled()) {
            Log.d("NotificationPanelView", "cancelInputFocusTransfer: failed by !panelsEnabled()");
            return;
        }
        SecNotificationPanelViewController secNotificationPanelViewController = this.mSecNotificationPanelViewController;
        if (secNotificationPanelViewController != null && secNotificationPanelViewController.isStatusBarWindowViewTouched()) {
            Log.d("NotificationPanelView", "cancelInputFocusTransfer: failed by StatusBarWindowView Touched");
            return;
        }
        Log.d("NotificationPanelView", "cancelInputFocusTransfer");
        if (this.mExpectingSynthesizedDown) {
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
        if (this.mStatusBarStateController.isDozing() && ((!SecPanelSplitHelper.isEnabled() || !this.mPanelSplitHelper.isShadeState()) && this.mBarState == 2)) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
            notificationStackScrollLayoutController.mView.setVisibility(4);
            notificationStackScrollLayoutController.mBlockHideAmountVisibility = true;
        }
        this.mQsController.closeQs();
    }

    public final void collapse(float f, boolean z, boolean z2) throws Resources.NotFoundException {
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

    @Override // com.android.systemui.Dumpable
    @NeverCompile
    public final void dump(PrintWriter printWriter, String[] strArr) {
        Lazy lazy;
        ViewGroup parentViewGroup;
        printWriter.println("NotificationPanelView:");
        PrintWriter printWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        printWriterAsIndenting.increaseIndent();
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
        printWriterAsIndenting.print("mDownTime=");
        printWriterAsIndenting.println(this.mDownTime);
        printWriterAsIndenting.print("mTouchSlopExceededBeforeDown=");
        printWriterAsIndenting.println(this.mTouchSlopExceededBeforeDown);
        printWriterAsIndenting.print("mIsLaunchAnimationRunning=");
        printWriterAsIndenting.println(isLaunchingActivity$1());
        printWriterAsIndenting.print("mOverExpansion=");
        printWriterAsIndenting.println(this.mOverExpansion);
        printWriterAsIndenting.print("mExpandedHeight=");
        printWriterAsIndenting.println(this.mExpandedHeight);
        printWriterAsIndenting.print("isTracking()=");
        printWriterAsIndenting.println(isTracking());
        printWriterAsIndenting.print("mExpanding=");
        printWriterAsIndenting.println(this.mExpanding);
        printWriterAsIndenting.print("mSplitShadeEnabled=");
        printWriterAsIndenting.println(false);
        printWriterAsIndenting.print("mAnimateNextPositionUpdate=");
        printWriterAsIndenting.println(this.mAnimateNextPositionUpdate);
        printWriterAsIndenting.print("isPanelExpanded()=");
        printWriterAsIndenting.println(isPanelExpanded());
        printWriterAsIndenting.print("mDozing=");
        printWriterAsIndenting.println(this.mDozing);
        printWriterAsIndenting.print("mDozingOnDown=");
        printWriterAsIndenting.println(this.mDozingOnDown);
        printWriterAsIndenting.print("mBouncerShowing=");
        printWriterAsIndenting.println(this.mBouncerShowing);
        printWriterAsIndenting.print("mBarState=");
        printWriterAsIndenting.println(this.mBarState);
        printWriterAsIndenting.print("mStatusBarMinHeight=");
        printWriterAsIndenting.println(this.mStatusBarMinHeight);
        printWriterAsIndenting.print("mStatusBarHeaderHeightKeyguard=");
        printWriterAsIndenting.println(this.mStatusBarHeaderHeightKeyguard);
        printWriterAsIndenting.print("mOverStretchAmount=");
        printWriterAsIndenting.println(this.mOverStretchAmount);
        printWriterAsIndenting.print("mDownX=");
        printWriterAsIndenting.println(this.mDownX);
        printWriterAsIndenting.print("mDownY=");
        printWriterAsIndenting.println(this.mDownY);
        printWriterAsIndenting.print("mDisplayTopInset=");
        printWriterAsIndenting.println(this.mDisplayTopInset);
        printWriterAsIndenting.print("mDisplayRightInset=");
        printWriterAsIndenting.println(this.mDisplayRightInset);
        printWriterAsIndenting.print("mDisplayLeftInset=");
        printWriterAsIndenting.println(this.mDisplayLeftInset);
        printWriterAsIndenting.print("mIsExpandingOrCollapsing=");
        printWriterAsIndenting.println(this.mIsExpandingOrCollapsing);
        printWriterAsIndenting.print("mHeadsUpStartHeight=");
        printWriterAsIndenting.println(this.mHeadsUpStartHeight);
        printWriterAsIndenting.print("mListenForHeadsUp=");
        printWriterAsIndenting.println(this.mListenForHeadsUp);
        printWriterAsIndenting.print("mNavigationBarBottomHeight=");
        printWriterAsIndenting.println(this.mNavigationBarBottomHeight);
        printWriterAsIndenting.print("mExpandingFromHeadsUp=");
        printWriterAsIndenting.println(this.mExpandingFromHeadsUp);
        printWriterAsIndenting.print("mCollapsedOnDown=");
        printWriterAsIndenting.println(this.mCollapsedOnDown);
        printWriterAsIndenting.print("mClosingWithAlphaFadeOut=");
        printWriterAsIndenting.println(this.mClosingWithAlphaFadeOut);
        printWriterAsIndenting.print("mHeadsUpAnimatingAway=");
        printWriterAsIndenting.println(this.mHeadsUpAnimatingAway);
        printWriterAsIndenting.print("mShowIconsWhenExpanded=");
        printWriterAsIndenting.println(this.mShowIconsWhenExpanded);
        printWriterAsIndenting.print("mIsFullWidth=");
        printWriterAsIndenting.println(this.mIsFullWidth);
        printWriterAsIndenting.print("mBlockingExpansionForCurrentTouch=");
        printWriterAsIndenting.println(this.mBlockingExpansionForCurrentTouch);
        printWriterAsIndenting.print("mExpectingSynthesizedDown=");
        printWriterAsIndenting.println(this.mExpectingSynthesizedDown);
        printWriterAsIndenting.print("mLastEventSynthesizedDown=");
        printWriterAsIndenting.println(this.mLastEventSynthesizedDown);
        printWriterAsIndenting.print("mInterpolatedDarkAmount=");
        printWriterAsIndenting.println(this.mInterpolatedDarkAmount);
        printWriterAsIndenting.print("mLinearDarkAmount=");
        printWriterAsIndenting.println(this.mLinearDarkAmount);
        printWriterAsIndenting.print("mPulsing=");
        printWriterAsIndenting.println(this.mPulsing);
        printWriterAsIndenting.print("mStackScrollerMeasuringPass=");
        printWriterAsIndenting.println(this.mStackScrollerMeasuringPass);
        printWriterAsIndenting.print("mPanelAlpha=");
        printWriterAsIndenting.println(this.mPanelAlpha);
        printWriterAsIndenting.print("mBottomAreaShadeAlpha=");
        printWriterAsIndenting.println(this.mBottomAreaShadeAlpha);
        printWriterAsIndenting.print("mHeadsUpInset=");
        printWriterAsIndenting.println(this.mHeadsUpInset);
        printWriterAsIndenting.print("mHeadsUpPinnedMode=");
        printWriterAsIndenting.println(this.mHeadsUpPinnedMode);
        printWriterAsIndenting.print("mAllowExpandForSmallExpansion=");
        printWriterAsIndenting.println(this.mAllowExpandForSmallExpansion);
        printWriterAsIndenting.print("mMaxOverscrollAmountForPulse=");
        printWriterAsIndenting.println(this.mMaxOverscrollAmountForPulse);
        printWriterAsIndenting.print("mIsPanelCollapseOnQQS=");
        printWriterAsIndenting.println(this.mIsPanelCollapseOnQQS);
        printWriterAsIndenting.print("mIsGestureNavigation=");
        printWriterAsIndenting.println(this.mIsGestureNavigation);
        printWriterAsIndenting.print("mOldLayoutDirection=");
        printWriterAsIndenting.println(this.mOldLayoutDirection);
        printWriterAsIndenting.print("mMinFraction=");
        printWriterAsIndenting.println(this.mMinFraction);
        printWriterAsIndenting.print("mSplitShadeFullTransitionDistance=");
        printWriterAsIndenting.println(this.mSplitShadeFullTransitionDistance);
        printWriterAsIndenting.print("mSplitShadeScrimTransitionDistance=");
        printWriterAsIndenting.println(this.mSplitShadeScrimTransitionDistance);
        printWriterAsIndenting.print("mMinExpandHeight=");
        printWriterAsIndenting.println(0.0f);
        printWriterAsIndenting.print("mPanelUpdateWhenAnimatorEnds=");
        printWriterAsIndenting.println(this.mPanelUpdateWhenAnimatorEnds);
        printWriterAsIndenting.print("mHasVibratedOnOpen=");
        printWriterAsIndenting.println(this.mHasVibratedOnOpen);
        printWriterAsIndenting.print("mFixedDuration=");
        printWriterAsIndenting.println(this.mFixedDuration);
        printWriterAsIndenting.print("mPanelFlingOvershootAmount=");
        printWriterAsIndenting.println(this.mPanelFlingOvershootAmount);
        printWriterAsIndenting.print("mLastGesturedOverExpansion=");
        printWriterAsIndenting.println(this.mLastGesturedOverExpansion);
        printWriterAsIndenting.print("mIsSpringBackAnimation=");
        printWriterAsIndenting.println(this.mIsSpringBackAnimation);
        printWriterAsIndenting.print("mHintDistance=");
        printWriterAsIndenting.println(this.mHintDistance);
        printWriterAsIndenting.print("mInitialOffsetOnTouch=");
        printWriterAsIndenting.println(this.mInitialOffsetOnTouch);
        printWriterAsIndenting.print("mCollapsedAndHeadsUpOnDown=");
        printWriterAsIndenting.println(this.mCollapsedAndHeadsUpOnDown);
        printWriterAsIndenting.print("mExpandedFraction=");
        printWriterAsIndenting.println(this.mExpandedFraction);
        printWriterAsIndenting.print("mExpansionDragDownAmountPx=");
        printWriterAsIndenting.println(this.mExpansionDragDownAmountPx);
        printWriterAsIndenting.print("mPanelClosedOnDown=");
        printWriterAsIndenting.println(this.mPanelClosedOnDown);
        printWriterAsIndenting.print("mHasLayoutedSinceDown=");
        printWriterAsIndenting.println(this.mHasLayoutedSinceDown);
        printWriterAsIndenting.print("mUpdateFlingVelocity=");
        printWriterAsIndenting.println(this.mUpdateFlingVelocity);
        printWriterAsIndenting.print("mUpdateFlingOnLayout=");
        printWriterAsIndenting.println(this.mUpdateFlingOnLayout);
        printWriterAsIndenting.print("isClosing()=");
        printWriterAsIndenting.println(isClosing());
        printWriterAsIndenting.print("mTouchSlopExceeded=");
        printWriterAsIndenting.println(this.mTouchSlopExceeded);
        printWriterAsIndenting.print("mTrackingPointer=");
        printWriterAsIndenting.println(this.mTrackingPointer);
        printWriterAsIndenting.print("mTouchSlop=");
        printWriterAsIndenting.println(this.mTouchSlop);
        printWriterAsIndenting.print("mSlopMultiplier=");
        printWriterAsIndenting.println(this.mSlopMultiplier);
        printWriterAsIndenting.print("mTouchAboveFalsingThreshold=");
        printWriterAsIndenting.println(this.mTouchAboveFalsingThreshold);
        printWriterAsIndenting.print("mTouchStartedInEmptyArea=");
        printWriterAsIndenting.println(this.mTouchStartedInEmptyArea);
        printWriterAsIndenting.print("mMotionAborted=");
        printWriterAsIndenting.println(this.mMotionAborted);
        printWriterAsIndenting.print("mUpwardsWhenThresholdReached=");
        printWriterAsIndenting.println(this.mUpwardsWhenThresholdReached);
        printWriterAsIndenting.print("mAnimatingOnDown=");
        printWriterAsIndenting.println(this.mAnimatingOnDown);
        printWriterAsIndenting.print("mHandlingPointerUp=");
        printWriterAsIndenting.println(this.mHandlingPointerUp);
        printWriterAsIndenting.print("mInstantExpanding=");
        printWriterAsIndenting.println(this.mInstantExpanding);
        printWriterAsIndenting.print("mAnimateAfterExpanding=");
        printWriterAsIndenting.println(this.mAnimateAfterExpanding);
        printWriterAsIndenting.print("mIsFlinging=");
        printWriterAsIndenting.println(this.mIsFlinging);
        printWriterAsIndenting.print("mViewName=");
        printWriterAsIndenting.println(this.mViewName);
        printWriterAsIndenting.print("mInitialExpandY=");
        printWriterAsIndenting.println(this.mInitialExpandY);
        printWriterAsIndenting.print("mInitialExpandX=");
        printWriterAsIndenting.println(this.mInitialExpandX);
        printWriterAsIndenting.print("mTouchDisabled=");
        printWriterAsIndenting.println(this.mTouchDisabled);
        printWriterAsIndenting.print("mInitialTouchFromKeyguard=");
        printWriterAsIndenting.println(this.mInitialTouchFromKeyguard);
        printWriterAsIndenting.print("mNextCollapseSpeedUpFactor=");
        printWriterAsIndenting.println(this.mNextCollapseSpeedUpFactor);
        printWriterAsIndenting.print("mGestureWaitForTouchSlop=");
        printWriterAsIndenting.println(this.mGestureWaitForTouchSlop);
        printWriterAsIndenting.print("mIgnoreXTouchSlop=");
        printWriterAsIndenting.println(this.mIgnoreXTouchSlop);
        printWriterAsIndenting.print("mExpandLatencyTracking=");
        printWriterAsIndenting.println(this.mExpandLatencyTracking);
        StringBuilder sb5 = new StringBuilder("gestureExclusionRect:");
        Region regionCalculateTouchableRegion = this.mShadeTouchableRegionManager.calculateTouchableRegion();
        Rect bounds = (!isFullyCollapsed() || regionCalculateTouchableRegion == null) ? null : regionCalculateTouchableRegion.getBounds();
        if (bounds == null) {
            bounds = EMPTY_RECT;
        }
        sb5.append(bounds);
        printWriterAsIndenting.println(sb5.toString());
        Trace.beginSection("Table<DownEvents>");
        List list = NPVCDownEventState.TABLE_HEADERS;
        NPVCDownEventState.Buffer buffer = this.mLastDownEvents;
        buffer.getClass();
        RingBuffer ringBuffer = buffer.buffer;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(ringBuffer, 10));
        ringBuffer.getClass();
        RingBuffer.AnonymousClass1 anonymousClass1 = ringBuffer.new AnonymousClass1();
        while (anonymousClass1.hasNext()) {
            arrayList.add((List) ((NPVCDownEventState) anonymousClass1.next()).asStringList$delegate.getValue());
        }
        new DumpsysTableLogger("NotificationPanelView", list, arrayList).printTableData(printWriterAsIndenting);
        Trace.endSection();
    }

    public final void endClosing() {
        SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor;
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
            mediaHierarchyManager.mediaCarouselController.getClass();
            MediaCarouselController.closeGuts(true);
            if (QpRune.QUICK_PANEL_CODE_FOR_POP_OVER && (secQsUiDisplayModeInteractor = this.mSecQsUiDisplayModeInteractor) != null && secQsUiDisplayModeInteractor.isTablet()) {
                this.mQsController.mSecQuickSettingsControllerImpl.getTabletHorizontalPanelPositionHelper().resetHorizontalPanelPosition(false);
            }
            this.mQsStatusEventLog.startTimer();
        }
    }

    public final void expand(boolean z) throws Resources.NotFoundException {
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
            notificationPanelView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.shade.NotificationPanelViewController.15
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() throws Resources.NotFoundException {
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
        setListening$1(true);
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void expandQSForOpenDetail() throws Resources.NotFoundException {
        expandToQs();
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void expandToNotifications() throws Resources.NotFoundException {
        if (this.mSecNotificationPanelViewController != null) {
            if (SecPanelSplitHelper.isEnabled()) {
                this.mPanelSplitHelper.stateOnDown = 0;
            }
            CentralSurfacesImpl centralSurfacesImpl = this.mCentralSurfaces;
            Objects.requireNonNull(centralSurfacesImpl);
            if (isOnKeyguard() && !centralSurfacesImpl.mBouncerShowing && !this.mFullScreenModeEnabled) {
                boolean zIsNeedsToExpandLocksNoti = ((AmbientState) Dependency.sDependency.getDependencyInner(AmbientState.class)).isNeedsToExpandLocksNoti();
                LockscreenShadeTransitionController lockscreenShadeTransitionController = this.mLockscreenShadeTransitionController;
                if (!zIsNeedsToExpandLocksNoti) {
                    lockscreenShadeTransitionController.goToLockedShade(null, true);
                    return;
                }
                lockscreenShadeTransitionController.onDragDownStarted$frameworks__base__packages__SystemUI__android_common__SystemUI_core(null);
                DragDownHelper dragDownHelper = lockscreenShadeTransitionController.touchHelper;
                SecPanelSplitHelper panelSplitHelper$1 = dragDownHelper.dragDownCallback.getPanelSplitHelper$1();
                if (panelSplitHelper$1 != null) {
                    panelSplitHelper$1.shouldQsDownInLockscreen = PanelSlideEventHandler.Direction.UNDECIDED;
                }
                dragDownHelper.animateToMaxDragDown(0.0f, 0.0f, true);
                return;
            }
        }
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        if (quickSettingsControllerImpl.getExpanded()) {
            quickSettingsControllerImpl.flingQs(0.0f, 1, null, false);
        } else {
            expand(true);
        }
    }

    public final void expandToQs() throws Resources.NotFoundException {
        SecNotificationPanelViewController secNotificationPanelViewController = this.mSecNotificationPanelViewController;
        if (secNotificationPanelViewController != null) {
            CentralSurfacesImpl centralSurfacesImpl = this.mCentralSurfaces;
            Objects.requireNonNull(centralSurfacesImpl);
            if (isOnKeyguard() && (centralSurfacesImpl.mBouncerShowing || this.mFullScreenModeEnabled)) {
                return;
            }
            SecPanelSplitHelper.Companion.getClass();
            if (SecPanelSplitHelper.isEnabled) {
                SecPanelSplitHelper secPanelSplitHelper = secNotificationPanelViewController.panelSplitHelper;
                if (secPanelSplitHelper != null) {
                    if (secPanelSplitHelper.isQSState()) {
                        secPanelSplitHelper = null;
                    }
                    if (secPanelSplitHelper != null) {
                        secPanelSplitHelper.slide$1(0);
                    }
                }
                if (isOnKeyguard()) {
                    this.mLockscreenShadeTransitionController.goToLockedShade(null, true);
                    return;
                } else {
                    if (isFullyCollapsed()) {
                        expand(true);
                        return;
                    }
                    return;
                }
            }
        }
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        if (quickSettingsControllerImpl.isExpansionEnabled()) {
            if (isFullyCollapsed() || SecPanelSplitHelper.isEnabled()) {
                quickSettingsControllerImpl.setExpandImmediate(true);
            }
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
    public final void finishInputFocusTransfer(float f) throws Resources.NotFoundException {
        MotionEvent motionEvent;
        if (!this.mCommandQueue.panelsEnabled()) {
            Log.d("NotificationPanelView", "finishInputFocusTransfer: failed by !panelsEnabled()");
            return;
        }
        SecNotificationPanelViewController secNotificationPanelViewController = this.mSecNotificationPanelViewController;
        if (secNotificationPanelViewController != null && secNotificationPanelViewController.isStatusBarWindowViewTouched()) {
            Log.d("NotificationPanelView", "finishInputFocusTransfer: failed by StatusBarWindowView Touched");
            return;
        }
        Log.d("NotificationPanelView", "finishInputFocusTransfer");
        if (this.mExpectingSynthesizedDown) {
            maybeVibrateOnOpening(false);
            if (SecPanelSplitHelper.isEnabled()) {
                SecPanelSplitHelper secPanelSplitHelper = this.mPanelSplitHelper;
                if (secPanelSplitHelper != null && (motionEvent = this.mDownEventFromOverView) != null) {
                    secPanelSplitHelper.shouldQSDown(motionEvent);
                }
            } else {
                StateFlowImpl stateFlowImpl = ((SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class)).repository._openQuickPanelFrom1DepthEtcInShade;
                LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl.getValue(), 1L, stateFlowImpl, null);
            }
            fling(f > 1.0f ? f * 1000.0f : 0.0f);
            ((HeadsUpManagerImpl) this.mHeadsUpManager).unpinAll();
            onTrackingStopped(false);
        }
    }

    public final void fling(float f) throws Resources.NotFoundException {
        fling(f, 1.0f, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:115:0x0284  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01c8  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0209  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void flingToHeight(float f, boolean z, final float f2, float f3, boolean z2) throws Resources.NotFoundException {
        KeyguardStateControllerImpl keyguardStateControllerImpl;
        boolean z3;
        final ValueAnimator valueAnimatorOfFloat;
        Set<Animator> set;
        float fPow;
        PanelPopOverManager panelPopOverManager;
        StringBuilder sb;
        int i = 0;
        QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
        if (quickPanelLogger != null && (sb = this.mQuickPanelLogBuilder) != null) {
            sb.setLength(0);
            sb.append("flingToHeight: ");
            sb.append("vel: ");
            sb.append(f);
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
            sb.append(this.mOverExpansion);
            sb.append(", mIsFling: ");
            sb.append(this.mIsFlinging);
            quickPanelLogger.logPanelState(sb.toString());
        }
        Animator animator = this.mFlingAnimator;
        if (animator != null && (animator.isRunning() || this.mFlingAnimator.isStarted())) {
            if (!SecPanelSplitHelper.isEnabled()) {
                return;
            } else {
                this.mFlingAnimator.cancel();
            }
        }
        if (QpRune.QUICK_PANEL_CODE_FOR_POP_OVER && (panelPopOverManager = this.mPanelPopOverManager) != null && !z) {
            panelPopOverManager.removePopOverAreaListener();
        }
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        quickSettingsControllerImpl.mLastShadeFlingWasExpanding = z;
        ShadeLogger shadeLogger = quickSettingsControllerImpl.mShadeLog;
        shadeLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        ShadeLogger$$ExternalSyntheticLambda0 shadeLogger$$ExternalSyntheticLambda0 = new ShadeLogger$$ExternalSyntheticLambda0(i);
        LogBuffer logBuffer = shadeLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).bool1 = z;
        logBuffer.commit(logMessageObtain);
        HeadsUpTouchHelper headsUpTouchHelper = this.mHeadsUpTouchHelper;
        HeadsUpManager headsUpManager = headsUpTouchHelper.mHeadsUpManager;
        if (!z && headsUpTouchHelper.mCollapseSnoozes) {
            ((HeadsUpManagerImpl) headsUpManager).snooze();
        }
        headsUpTouchHelper.mCollapseSnoozes = false;
        headsUpTouchHelper.mAmbientState.mIsCollapsingHeadsup = false;
        if (z) {
            Log.d("HeadsUpTouchHelper", "unpinAll because of notifyFling expand");
            ((HeadsUpManagerImpl) headsUpManager).unpinAll();
            headsUpTouchHelper.mTrackingPointer = -1;
            headsUpTouchHelper.mPickedChild = null;
            headsUpTouchHelper.mTouchingHeadsUpView = false;
        }
        boolean z4 = isOnKeyguard() && !z;
        KeyguardStateControllerImpl keyguardStateControllerImpl2 = this.mKeyguardStateController;
        keyguardStateControllerImpl2.mFlingingToDismissKeyguard = z4;
        keyguardStateControllerImpl2.mFlingingToDismissKeyguardDuringSwipeGesture = false;
        keyguardStateControllerImpl2.mSnappingKeyguardBackAfterSwipe = !z4;
        if (!z && !isKeyguardShowing$1()) {
            if (quickSettingsControllerImpl.mMinExpansionHeight == 0) {
                keyguardStateControllerImpl = keyguardStateControllerImpl2;
                fPow = 1.0f;
            } else {
                keyguardStateControllerImpl = keyguardStateControllerImpl2;
                fPow = (float) Math.pow(Math.max(0.0f, Math.min(this.mExpandedHeight / r0, 1.0f)), 0.75d);
            }
            if (fPow == 1.0f) {
                z3 = true;
            }
            this.mClosingWithAlphaFadeOut = z3;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
            notificationStackScrollLayoutController.mView.mForceNoOverlappingRendering = z3;
            notificationStackScrollLayoutController.setPanelFlinging(true);
            ((ShadeRepositoryImpl) this.mShadeRepository).setCurrentFling(new FlingInfo(z, f));
            if (f2 != this.mExpandedHeight && this.mOverExpansion == 0.0f) {
                onFlingEnd(false);
                return;
            }
            this.mIsFlinging = true;
            boolean z5 = !z && this.mStatusBarStateController.getState() != 1 && this.mOverExpansion == 0.0f && f >= 0.0f;
            final boolean z6 = !z5 || (this.mOverExpansion != 0.0f && z);
            final float fLerp = !z5 ? (this.mOverExpansion / this.mPanelFlingOvershootAmount) + MathUtils.lerp(0.2f, 1.0f, MathUtils.saturate(f / (this.mFlingAnimationUtils.mHighVelocityPxPerSecond * 0.5f))) : 0.0f;
            final float f4 = this.mOverExpansion;
            valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mExpandedHeight, f2);
            set = this.mTestSetOfAnimatorsUsed;
            if (set != null && valueAnimatorOfFloat != null) {
                set.add(valueAnimatorOfFloat);
            }
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda50
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    NotificationPanelViewController notificationPanelViewController = this.f$0;
                    float f5 = fLerp;
                    float f6 = f2;
                    float f7 = f4;
                    ValueAnimator valueAnimator2 = valueAnimatorOfFloat;
                    Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                    notificationPanelViewController.getClass();
                    if (f5 > 0.0f || (f6 == 0.0f && f7 != 0.0f)) {
                        notificationPanelViewController.setOverExpansionInternal(MathUtils.lerp(f7, notificationPanelViewController.mPanelFlingOvershootAmount * f5, ((PathInterpolator) Interpolators.FAST_OUT_SLOW_IN).getInterpolation(valueAnimator2.getAnimatedFraction())), false);
                    }
                    notificationPanelViewController.setExpandedHeightInternal(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
            valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.NotificationPanelViewController.12
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator2) {
                    NotificationPanelViewController.this.mFlingAnimator = null;
                }
            });
            this.mFlingAnimator = valueAnimatorOfFloat;
            NotificationPanelView notificationPanelView = this.mView;
            if (z) {
                this.mHasVibratedOnOpen = false;
                if (this.mBarState == 0 || (!keyguardStateControllerImpl.mCanDismissLockScreen && isTracking())) {
                    this.mFlingAnimationUtilsClosing.apply(valueAnimatorOfFloat, this.mExpandedHeight, f2, f, notificationPanelView.getHeight());
                } else if (f == 0.0f) {
                    valueAnimatorOfFloat.setInterpolator(Interpolators.PANEL_CLOSE_ACCELERATED);
                    valueAnimatorOfFloat.setDuration((long) (((this.mExpandedHeight / notificationPanelView.getHeight()) * 100.0f) + 200.0f));
                } else {
                    this.mFlingAnimationUtilsDismissing.apply(valueAnimatorOfFloat, this.mExpandedHeight, f2, f, notificationPanelView.getHeight());
                }
                if (f == 0.0f) {
                    valueAnimatorOfFloat.setDuration((long) (valueAnimatorOfFloat.getDuration() / f3));
                }
                int i2 = this.mFixedDuration;
                if (i2 != -1) {
                    valueAnimatorOfFloat.setDuration(i2);
                }
                valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.NotificationPanelViewController.13
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator2) {
                        View view;
                        NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                        notificationPanelViewController.getClass();
                        if (notificationPanelViewController.mNotificationContainerParent == null) {
                            return;
                        }
                        float fLerp2 = MathUtils.lerp(1.0f, 0.9f, 0.0f);
                        NotificationsQuickSettingsContainer notificationsQuickSettingsContainer = notificationPanelViewController.mNotificationContainerParent;
                        if (notificationsQuickSettingsContainer.mStackScroller != null && (view = notificationsQuickSettingsContainer.mQSContainer) != null) {
                            view.getBoundsOnScreen(notificationsQuickSettingsContainer.mUpperRect);
                            notificationsQuickSettingsContainer.mStackScroller.getBoundsOnScreen(notificationsQuickSettingsContainer.mBoundingBoxRect);
                            notificationsQuickSettingsContainer.mBoundingBoxRect.union(notificationsQuickSettingsContainer.mUpperRect);
                            float fCenterX = notificationsQuickSettingsContainer.mBoundingBoxRect.centerX();
                            float fCenterY = notificationsQuickSettingsContainer.mBoundingBoxRect.centerY();
                            notificationsQuickSettingsContainer.mQSContainer.setPivotX(fCenterX);
                            notificationsQuickSettingsContainer.mQSContainer.setPivotY(fCenterY);
                            notificationsQuickSettingsContainer.mQSContainer.setScaleX(fLerp2);
                            notificationsQuickSettingsContainer.mQSContainer.setScaleY(fLerp2);
                            notificationsQuickSettingsContainer.mStackScroller.setPivotX(fCenterX);
                            notificationsQuickSettingsContainer.mStackScroller.setPivotY(fCenterY);
                            notificationsQuickSettingsContainer.mStackScroller.setScaleX(fLerp2);
                            notificationsQuickSettingsContainer.mStackScroller.setScaleY(fLerp2);
                        }
                        ScrimController scrimController = notificationPanelViewController.mScrimController;
                        scrimController.mNotificationsScrim.setScaleX(fLerp2);
                        scrimController.mNotificationsScrim.setScaleY(fLerp2);
                    }
                });
            } else {
                maybeVibrateOnOpening(true);
                float f5 = (!z2 || f >= 0.0f) ? f : 0.0f;
                this.mFlingAnimationUtils.apply(valueAnimatorOfFloat, this.mExpandedHeight, (fLerp * this.mPanelFlingOvershootAmount) + f2, f5, notificationPanelView.getHeight());
                if (z && this.mExpandedFraction == 1.0f) {
                    valueAnimatorOfFloat.setDuration(0L);
                } else if (f5 == 0.0f) {
                    valueAnimatorOfFloat.setDuration(350L);
                }
            }
            valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.NotificationPanelViewController.14
                public boolean mCancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator2) {
                    this.mCancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator2) throws Resources.NotFoundException {
                    int i3 = 1;
                    if (!z6 || this.mCancelled) {
                        NotificationPanelViewController.this.onFlingEnd(this.mCancelled);
                        return;
                    }
                    final NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                    float f6 = notificationPanelViewController.mOverExpansion;
                    if (f6 == 0.0f) {
                        notificationPanelViewController.onFlingEnd(false);
                        return;
                    }
                    notificationPanelViewController.mIsSpringBackAnimation = true;
                    ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(f6, 0.0f);
                    valueAnimatorOfFloat2.addUpdateListener(new NotificationPanelViewController$$ExternalSyntheticLambda7(notificationPanelViewController, i3));
                    valueAnimatorOfFloat2.setDuration(250L);
                    valueAnimatorOfFloat2.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                    valueAnimatorOfFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.NotificationPanelViewController.16
                        public boolean mCancelled;

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationCancel(Animator animator3) {
                            this.mCancelled = true;
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator3) throws Resources.NotFoundException {
                            NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                            notificationPanelViewController2.mIsSpringBackAnimation = false;
                            notificationPanelViewController2.onFlingEnd(this.mCancelled);
                        }
                    });
                    notificationPanelViewController.setAnimator(valueAnimatorOfFloat2);
                    valueAnimatorOfFloat2.start();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator2) {
                    if (NotificationPanelViewController.this.mStatusBarStateController.isDozing()) {
                        return;
                    }
                    NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                    notificationPanelViewController.mQsController.beginJankMonitoring(notificationPanelViewController.isFullyCollapsed());
                }
            });
            if (!this.mScrimController.mScreenOn) {
                valueAnimatorOfFloat.setDuration(1L);
            }
            setAnimator(valueAnimatorOfFloat);
            valueAnimatorOfFloat.start();
            setMotionAborted();
        }
        keyguardStateControllerImpl = keyguardStateControllerImpl2;
        z3 = false;
        this.mClosingWithAlphaFadeOut = z3;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.mNotificationStackScrollLayoutController;
        notificationStackScrollLayoutController2.mView.mForceNoOverlappingRendering = z3;
        notificationStackScrollLayoutController2.setPanelFlinging(true);
        ((ShadeRepositoryImpl) this.mShadeRepository).setCurrentFling(new FlingInfo(z, f));
        if (f2 != this.mExpandedHeight) {
        }
        this.mIsFlinging = true;
        if (z) {
        }
        if (z5) {
        }
        if (!z5) {
        }
        final float f42 = this.mOverExpansion;
        valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mExpandedHeight, f2);
        set = this.mTestSetOfAnimatorsUsed;
        if (set != null) {
            set.add(valueAnimatorOfFloat);
        }
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda50
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                NotificationPanelViewController notificationPanelViewController = this.f$0;
                float f52 = fLerp;
                float f6 = f2;
                float f7 = f42;
                ValueAnimator valueAnimator2 = valueAnimatorOfFloat;
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                notificationPanelViewController.getClass();
                if (f52 > 0.0f || (f6 == 0.0f && f7 != 0.0f)) {
                    notificationPanelViewController.setOverExpansionInternal(MathUtils.lerp(f7, notificationPanelViewController.mPanelFlingOvershootAmount * f52, ((PathInterpolator) Interpolators.FAST_OUT_SLOW_IN).getInterpolation(valueAnimator2.getAnimatedFraction())), false);
                }
                notificationPanelViewController.setExpandedHeightInternal(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.NotificationPanelViewController.12
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                NotificationPanelViewController.this.mFlingAnimator = null;
            }
        });
        this.mFlingAnimator = valueAnimatorOfFloat;
        NotificationPanelView notificationPanelView2 = this.mView;
        if (z) {
        }
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.NotificationPanelViewController.14
            public boolean mCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator2) {
                this.mCancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) throws Resources.NotFoundException {
                int i3 = 1;
                if (!z6 || this.mCancelled) {
                    NotificationPanelViewController.this.onFlingEnd(this.mCancelled);
                    return;
                }
                final NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                float f6 = notificationPanelViewController.mOverExpansion;
                if (f6 == 0.0f) {
                    notificationPanelViewController.onFlingEnd(false);
                    return;
                }
                notificationPanelViewController.mIsSpringBackAnimation = true;
                ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(f6, 0.0f);
                valueAnimatorOfFloat2.addUpdateListener(new NotificationPanelViewController$$ExternalSyntheticLambda7(notificationPanelViewController, i3));
                valueAnimatorOfFloat2.setDuration(250L);
                valueAnimatorOfFloat2.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                valueAnimatorOfFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.NotificationPanelViewController.16
                    public boolean mCancelled;

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator3) {
                        this.mCancelled = true;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator3) throws Resources.NotFoundException {
                        NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                        notificationPanelViewController2.mIsSpringBackAnimation = false;
                        notificationPanelViewController2.onFlingEnd(this.mCancelled);
                    }
                });
                notificationPanelViewController.setAnimator(valueAnimatorOfFloat2);
                valueAnimatorOfFloat2.start();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator2) {
                if (NotificationPanelViewController.this.mStatusBarStateController.isDozing()) {
                    return;
                }
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                notificationPanelViewController.mQsController.beginJankMonitoring(notificationPanelViewController.isFullyCollapsed());
            }
        });
        if (!this.mScrimController.mScreenOn) {
        }
        setAnimator(valueAnimatorOfFloat);
        valueAnimatorOfFloat.start();
        setMotionAborted();
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
        NotificationPanelView notificationPanelView = this.mView;
        PanelScreenShotLogger.addLogItem(arrayList, "currentPanelAlpha", Float.valueOf(notificationPanelView.mCurrentPanelAlpha));
        PanelScreenShotLogger.addLogItem(arrayList, "visibility", Integer.valueOf(notificationPanelView.getVisibility()));
        PanelScreenShotLogger.addLogItem(arrayList, "getAlpha", Float.valueOf(notificationPanelView.getAlpha()));
        PanelScreenShotLogger.addLogItem(arrayList, "mQuickQsOffsetHeight", Integer.valueOf(this.mQuickQsOffsetHeight));
        PanelScreenShotLogger.addLogItem(arrayList, "mExpandedHeight", Float.valueOf(this.mExpandedHeight));
        PanelScreenShotLogger.addLogItem(arrayList, "mRecomputedMaxCountNotification", null);
        PanelScreenShotLogger.addLogItem(arrayList, "mRecomputedMaxCountCallStack", this.mRecomputedMaxCountCallStack);
        PanelScreenShotLogger.addLogItem(arrayList, "mRecomputedReason", null);
        PanelScreenShotLogger.addLogItem(arrayList, "mBottomMarginY", 0);
        PanelScreenShotLogger.addLogItem(arrayList, "mAvailableNotifSpace", Float.valueOf(0.0f));
        return arrayList;
    }

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final int getBarState() {
        return this.mBarState;
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

    public final float getDisplayDensity() {
        return ShadeWindowGoesAround.isEnabled() ? this.mView.getContext().getResources().getConfiguration().densityDpi : this.mCentralSurfaces.mDisplayMetrics.density;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0059  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final float getFaceWidgetAlpha() {
        float fInterpolate;
        if (this.mKeyguardTouchAnimator.isViRunning() || this.mCentralSurfaces.mBouncerShowing) {
            fInterpolate = -1.0f;
        } else {
            LockscreenShadeTransitionController lockscreenShadeTransitionController = this.mLockscreenShadeTransitionController;
            if (lockscreenShadeTransitionController.getFractionToShade() > 0.0f) {
                float fractionToShade = lockscreenShadeTransitionController.getFractionToShade();
                fInterpolate = NotificationUtils.interpolate(1.0f, 0.0f, ((double) fractionToShade) > 0.5d ? 1.0f : fractionToShade * 2.0f);
            } else if (this.mClockPositionAlgorithm.isPanelExpanded()) {
                QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
                if (quickSettingsControllerImpl.getExpanded()) {
                    float fComputeExpansionFraction = quickSettingsControllerImpl.computeExpansionFraction();
                    fInterpolate = NotificationUtils.interpolate(1.0f, 0.0f, ((double) fComputeExpansionFraction) > 0.3d ? 1.0f : fComputeExpansionFraction * 3.0f);
                } else {
                    fInterpolate = 1.0f;
                }
            }
        }
        if (((KeyguardEditModeControllerImpl) this.mKeyguardEditModeController).getVIRunning()) {
            return 1.0f;
        }
        return fInterpolate;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getFalsingThreshold() {
        float f;
        WakefulnessModel wakefulnessModel = (WakefulnessModel) this.mPowerInteractor.detailedWakefulness.$$delegate_0.getValue();
        ShadeViewController.Companion.getClass();
        if (wakefulnessModel.isAwake()) {
            WakeSleepReason wakeSleepReason = WakeSleepReason.TAP;
            WakeSleepReason wakeSleepReason2 = wakefulnessModel.lastWakeReason;
            f = (wakeSleepReason2 == wakeSleepReason || wakeSleepReason2 == WakeSleepReason.GESTURE) ? 1.5f : 1.0f;
        }
        return (int) (this.mQsController.mFalsingThreshold * f);
    }

    public final int getKeyguardNotificationStaticPadding() {
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int height = 0;
        if (!isKeyguardShowing$1()) {
            return 0;
        }
        boolean bypassEnabled = this.mKeyguardBypassController.getBypassEnabled();
        KeyguardClockPositionAlgorithm.Result result = this.mClockPositionResult;
        if (!bypassEnabled) {
            if (!CscRune.KEYGUARD_DCM_LIVE_UX) {
                int lockscreenNotifPadding = this.mClockPositionAlgorithm.getLockscreenNotifPadding();
                return lockscreenNotifPadding != 0 ? lockscreenNotifPadding : result.stackScrollerPadding;
            }
            int i2 = result.stackScrollerPadding;
            int lockscreenNotifPadding2 = this.mClockPositionAlgorithm.getLockscreenNotifPadding();
            if (lockscreenNotifPadding2 == 0) {
                lockscreenNotifPadding2 = result.stackScrollerPadding;
            }
            LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
            if (lockscreenNotificationIconsOnlyController != null && lockscreenNotificationIconsOnlyController.getIconContainer() != null) {
                height = lockscreenNotificationIconsOnlyController.getIconContainer().getHeight();
            }
            return this.mMascotViewContainer.updatePosition(lockscreenNotifPadding2, height) + lockscreenNotifPadding2;
        }
        int i3 = this.mHeadsUpInset;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        if (!notificationStackScrollLayoutController.mView.mAmbientState.isPulseExpanding()) {
            return i3;
        }
        int i4 = result.stackScrollerPadding;
        int lockscreenNotifPadding3 = this.mClockPositionAlgorithm.getLockscreenNotifPadding();
        if (lockscreenNotifPadding3 != 0) {
            i4 = lockscreenNotifPadding3;
        }
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.getClass();
        float f = notificationStackScrollLayout.mAmbientState.mPulseHeight;
        if (f == 100000.0f) {
            f = 0.0f;
        }
        return (int) MathUtils.lerp(i3, i4, MathUtils.smoothStep(0.0f, notificationStackScrollLayout.mIntrinsicPadding, f));
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final int getMaxKeyguardNotifications(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "getMaxKeyguardNotifications max: ", "NotificationPanelView");
        return 0;
    }

    public final int getMaxPanelHeight() {
        int iMax = this.mStatusBarMinHeight;
        int i = this.mBarState;
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        if (i != 1 && this.mNotificationStackScrollLayoutController.getNotGoneChildCount() == 0) {
            iMax = Math.max(iMax, quickSettingsControllerImpl.mMinExpansionHeight);
        }
        boolean zIsEnabled = SecPanelSplitHelper.isEnabled();
        KeyguardClockPositionAlgorithm.Result result = this.mClockPositionResult;
        int iCalculatePanelHeightExpanded = (zIsEnabled || quickSettingsControllerImpl.isExpandImmediate() || quickSettingsControllerImpl.getExpanded() || (this.mIsExpandingOrCollapsing && quickSettingsControllerImpl.mExpandedWhenExpandingStarted) || this.mPulsing) ? quickSettingsControllerImpl.calculatePanelHeightExpanded(result.stackScrollerPadding) : calculatePanelHeightShade();
        if (this.mSecNotificationPanelViewController != null) {
            int iCalculatePanelHeightExpanded2 = quickSettingsControllerImpl.calculatePanelHeightExpanded(result.stackScrollerPadding);
            int i2 = this.mBarState;
            SecPanelSplitHelper.Companion.getClass();
            if (SecPanelSplitHelper.isEnabled && i2 == 0) {
                iCalculatePanelHeightExpanded = iCalculatePanelHeightExpanded2 / 2;
            }
        }
        int iMax2 = Math.max(iMax, iCalculatePanelHeightExpanded);
        if (iMax2 == 0) {
            Log.wtf("NotificationPanelView", "maxPanelHeight is invalid. mOverExpansion: " + this.mOverExpansion + ", calculatePanelHeightQsExpanded: " + quickSettingsControllerImpl.calculatePanelHeightExpanded(result.stackScrollerPadding) + ", calculatePanelHeightShade: " + calculatePanelHeightShade() + ", mStatusBarMinHeight = " + this.mStatusBarMinHeight + ", mQsMinExpansionHeight = " + quickSettingsControllerImpl.mMinExpansionHeight);
        }
        return iMax2;
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
            return this.mResources.getDimensionPixelSize(R.dimen.keyguard_indication_dls_default_notification_top_margin_tablet) + getCutoutHeight();
        }
        if (z) {
            return this.mResources.getDimensionPixelSize(R.dimen.keyguard_indication_dls_default_notification_top_margin_land);
        }
        return this.mResources.getDimensionPixelSize(R.dimen.keyguard_indication_dls_default_notification_top_margin) + getCutoutHeight();
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

    @Override // com.android.systemui.shade.ShadeSurface
    public final void initDependencies(CentralSurfacesImpl centralSurfacesImpl, CentralSurfacesImpl$$ExternalSyntheticLambda30 centralSurfacesImpl$$ExternalSyntheticLambda30, HeadsUpManager headsUpManager) {
        this.mHeadsUpManager = headsUpManager;
        ((HeadsUpManagerImpl) headsUpManager).addListener(this.mOnHeadsUpChangedListener);
        this.mHeadsUpTouchHelper = new HeadsUpTouchHelper(headsUpManager, this.mStatusBarService, this.mNotificationStackScrollLayoutController.mView.mHeadsUpCallback, new HeadsUpNotificationViewControllerImpl(this, 0));
        this.mCentralSurfaces = centralSurfacesImpl;
        this.mHideExpandedRunnable = centralSurfacesImpl$$ExternalSyntheticLambda30;
        this.mNowBarContainer = centralSurfacesImpl.getNotificationShadeWindowViewController().mView.findViewById(R.id.now_bar_rootview);
    }

    public final void instantCollapse() throws Resources.NotFoundException {
        QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
        if (quickPanelLogger != null) {
            quickPanelLogger.logPanelState("instantCollapse");
        }
        abortAnimations();
        setExpandedFraction(0.0f);
        if (isTracking()) {
            onTrackingStopped(false);
        }
        if (SecPanelSplitHelper.isEnabled()) {
            Log.d("NotificationPanelView", "instantCollapse forced set isSliding is false");
            QsAnimatorState.isSliding = false;
        }
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
        if (this.mExpandedFraction > 0.0f || this.mInstantExpanding || isPanelVisibleBecauseOfHeadsUp() || isTracking() || this.mHeightAnimator != null) {
            return true;
        }
        return this.mUnlockedScreenOffAnimationController.lightRevealAnimationPlaying && !this.mIsSpringBackAnimation;
    }

    public final boolean isExpandingOrCollapsing() {
        float fComputeExpansionFraction = this.mQsController.computeExpansionFraction();
        if (this.mIsExpandingOrCollapsing) {
            return true;
        }
        return 0.0f < fComputeExpansionFraction && fComputeExpansionFraction < 1.0f;
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
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        float x = notificationStackScrollLayout.getX() + f3;
        return !notificationStackScrollLayoutController.mView.isBelowLastNotification(f - x, f2) && x < f && f < (((float) notificationStackScrollLayoutController.mView.getWidth()) + x) - (f3 * 2.0f);
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final boolean isInFaceWidgetContainer(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1) {
            boolean z = this.mIsFaceWidgetOnTouchDown;
            this.mIsFaceWidgetOnTouchDown = false;
            return z;
        }
        if (actionMasked == 0) {
            FaceWidgetContainerWrapper faceWidgetContainerWrapper = this.mKeyguardStatusBase;
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            PluginKeyguardStatusView pluginKeyguardStatusView = faceWidgetContainerWrapper.mPluginKeyguardStatusView;
            if (pluginKeyguardStatusView != null ? pluginKeyguardStatusView.isInContentBounds(x, y) : false) {
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

    public final boolean isKeyguardShowing$1() {
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
        KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = this.mKeyguardSecBottomAreaViewController;
        if (keyguardSecBottomAreaViewController != null) {
            return keyguardSecBottomAreaViewController.isNoUnlockNeed(str);
        }
        return false;
    }

    public final boolean isOnAod() {
        return this.mDozing && this.mDozeParameters.getAlwaysOn();
    }

    public final boolean isOnKeyguard() {
        return this.mBarState == 1;
    }

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean isPanelExpanded() {
        return ((Boolean) ((ShadeRepositoryImpl) this.mShadeRepository).legacyExpandedOrAwaitingInputTransfer.$$delegate_0.getValue()).booleanValue();
    }

    public final boolean isPanelVisibleBecauseOfHeadsUp() {
        HeadsUpManager headsUpManager = this.mHeadsUpManager;
        return ((headsUpManager != null && ((HeadsUpManagerImpl) headsUpManager).mHasPinnedNotification) || this.mHeadsUpAnimatingAway) && this.mBarState == 0;
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

    @Override // com.android.systemui.settings.brightness.domain.interactor.BrightnessMirrorShowingInteractor
    public final StateFlow isShowing() {
        return this.mIsBrightnessMirrorShowing;
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final boolean isTouchableArea(MotionEvent motionEvent) {
        KeyguardSecBottomAreaView keyguardSecBottomAreaView;
        return CscRune.LOCKUI_BOTTOM_USIM_TEXT && (keyguardSecBottomAreaView = this.mKeyguardSecBottomArea) != null && keyguardSecBottomAreaView.isInEmergencyButtonArea(motionEvent);
    }

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean isTracking() {
        return ((Boolean) ((ShadeRepositoryImpl) this.mShadeRepository).legacyShadeTracking.$$delegate_0.getValue()).booleanValue();
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final boolean isViewEnabled() {
        return this.mView.isEnabled();
    }

    public void loadDimens() throws Resources.NotFoundException {
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
        this.mHeadsUpInset = this.mResources.getDimensionPixelSize(R.dimen.heads_up_status_bar_padding) + SystemBarUtils.getStatusBarHeight(notificationPanelView.getContext());
        this.mMaxOverscrollAmountForPulse = this.mResources.getDimensionPixelSize(R.dimen.pulse_expansion_max_top_overshoot);
        this.mSplitShadeScrimTransitionDistance = this.mResources.getDimensionPixelSize(R.dimen.split_shade_scrim_transition_distance);
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
        quickSettingsControllerImpl.mNSSLTopPadding = quickSettingsControllerImpl.mResources.getDimensionPixelSize(R.dimen.pop_over_style_top_padding);
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final void makeExpandedInvisible() {
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
        this.mVibratorHelper.getClass();
        this.mView.performHapticFeedback(12);
        this.mHasVibratedOnOpen = true;
        this.mShadeLog.v("Vibrating on opening, mHasVibratedOnOpen=true");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void notifyExpandingFinished() throws Resources.NotFoundException {
        SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor;
        QSImpl qSImpl;
        SecQSImpl secQSImpl;
        SecQSImplAnimatorManager secQSImplAnimatorManager;
        int i = 2;
        Object[] objArr = 0;
        endClosing();
        if (this.mExpanding) {
            this.mExpanding = false;
            int i2 = SceneContainerFlag.$r8$clinit;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
            notificationStackScrollLayoutController.getClass();
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            notificationStackScrollLayout.mCheckForLeavebehind = false;
            notificationStackScrollLayout.mIsExpansionChanging = false;
            notificationStackScrollLayout.mAmbientState.mExpansionChanging = false;
            if (!notificationStackScrollLayout.mIsExpanded) {
                notificationStackScrollLayout.resetScrollPosition();
                notificationStackScrollLayout.mResetUserExpandedStatesRunnable.run();
                notificationStackScrollLayout.mSwipeCancelledView.clear();
                notificationStackScrollLayout.clearTemporaryViewsInGroup(notificationStackScrollLayout, "clearTemporaryViews");
                for (int i3 = 0; i3 < notificationStackScrollLayout.getChildCount(); i3++) {
                    ExpandableView expandableView = (ExpandableView) notificationStackScrollLayout.getChildAt(i3);
                    if (expandableView instanceof ExpandableNotificationRow) {
                        notificationStackScrollLayout.clearTemporaryViewsInGroup(((ExpandableNotificationRow) expandableView).mChildrenContainer, "clearTemporaryViewsInGroup(row.getChildrenContainer())");
                    }
                }
                for (int i4 = 0; i4 < notificationStackScrollLayout.getChildCount(); i4++) {
                    ExpandableView expandableView2 = (ExpandableView) notificationStackScrollLayout.getChildAt(i4);
                    if (expandableView2 instanceof ExpandableNotificationRow) {
                        ((ExpandableNotificationRow) expandableView2).setUserLocked(false);
                    }
                }
                notificationStackScrollLayout.resetAllSwipeState();
            }
            if (notificationStackScrollLayout.mLastSentExpandedHeight > 0.0f) {
                notificationStackScrollLayout.clearHeadsUpDisappearRunning();
            }
            HeadsUpManagerImpl headsUpManagerImpl = (HeadsUpManagerImpl) this.mHeadsUpManager;
            if (headsUpManagerImpl.mReleaseOnExpandFinish) {
                headsUpManagerImpl.releaseAllImmediately();
                headsUpManagerImpl.mReleaseOnExpandFinish = false;
            } else {
                Iterator it = headsUpManagerImpl.getAllEntries().toList().iterator();
                while (it.hasNext()) {
                    ((NotificationEntry) it.next()).getClass();
                }
                Iterator it2 = headsUpManagerImpl.mEntriesToRemoveAfterExpand.iterator();
                while (it2.hasNext()) {
                    NotificationEntry notificationEntry = (NotificationEntry) it2.next();
                    if (headsUpManagerImpl.isHeadsUpEntry(notificationEntry.mKey)) {
                        headsUpManagerImpl.removeEntry(notificationEntry.mKey, "onExpandingFinished");
                    }
                }
            }
            headsUpManagerImpl.mEntriesToRemoveAfterExpand.clear();
            boolean zIsFullyCollapsed = isFullyCollapsed();
            ConversationNotificationManager conversationNotificationManager = this.mConversationNotificationManager;
            conversationNotificationManager.notifPanelCollapsed = zIsFullyCollapsed;
            if (!zIsFullyCollapsed) {
                FilteringSequence filteringSequenceMapNotNull = SequencesKt___SequencesKt.mapNotNull(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(conversationNotificationManager.states.entrySet()), new ConversationNotificationManager$$ExternalSyntheticLambda4(conversationNotificationManager, objArr == true ? 1 : 0));
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                FilteringSequence.AnonymousClass1 anonymousClass1 = filteringSequenceMapNotNull.new AnonymousClass1();
                while (anonymousClass1.hasNext()) {
                    Pair pair = (Pair) anonymousClass1.next();
                    linkedHashMap.put(pair.component1(), pair.component2());
                }
                final Map mapOptimizeReadOnlyMap = MapsKt__MapsKt.optimizeReadOnlyMap(linkedHashMap);
                conversationNotificationManager.states.replaceAll(new ConversationNotificationManager$sam$java_util_function_BiFunction$0(new Function2() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$$ExternalSyntheticLambda5
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ConversationNotificationManager.ConversationState conversationState = (ConversationNotificationManager.ConversationState) obj2;
                        int i5 = ConversationNotificationManager.$r8$clinit;
                        return mapOptimizeReadOnlyMap.containsKey((String) obj) ? new ConversationNotificationManager.ConversationState(0, conversationState.f134notification) : conversationState;
                    }
                }));
                FilteringSequence.AnonymousClass1 anonymousClass12 = SequencesKt___SequencesKt.mapNotNull(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(mapOptimizeReadOnlyMap.values()), new ConversationNotificationManager$$ExternalSyntheticLambda1(i)).new AnonymousClass1();
                while (anonymousClass12.hasNext()) {
                    ConversationNotificationManager.resetBadgeUi((ExpandableNotificationRow) anonymousClass12.next());
                }
            }
            this.mIsExpandingOrCollapsing = false;
            boolean z = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
            QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
            if (!z && isPanelExpanded() && this.mBarState != 1) {
                if (SecPanelSplitHelper.isEnabled()) {
                    SecPanelSplitHelper secPanelSplitHelper = this.mPanelSplitHelper;
                    if (secPanelSplitHelper != null && secPanelSplitHelper.isShadeState()) {
                        updateEntrySetRead();
                    }
                } else if (!quickSettingsControllerImpl.getExpanded()) {
                    updateEntrySetRead();
                }
            }
            MediaHierarchyManager mediaHierarchyManager = this.mMediaHierarchyManager;
            if (mediaHierarchyManager.collapsingShadeFromQS) {
                mediaHierarchyManager.collapsingShadeFromQS = false;
                MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, true, 2);
            }
            boolean expanded = quickSettingsControllerImpl.getExpanded();
            if (mediaHierarchyManager.qsExpanded != expanded) {
                mediaHierarchyManager.qsExpanded = expanded;
                mediaHierarchyManager.mediaCarouselController.mediaCarouselScrollHandler.getClass();
            }
            mediaHierarchyManager.updateUserVisibility();
            boolean zIsFullyCollapsed2 = isFullyCollapsed();
            NotificationPanelView notificationPanelView = this.mView;
            if (zIsFullyCollapsed2) {
                DejankUtils.postAfterTraversal(new NotificationPanelViewController$$ExternalSyntheticLambda18(this, 9));
                notificationPanelView.postOnAnimation(new NotificationPanelViewController$$ExternalSyntheticLambda18(this, 10));
            } else {
                setListening$1(true);
            }
            if (this.mBarState != 0) {
                this.mShadeLog.d("onExpandingFinished called");
                quickSettingsControllerImpl.setExpandImmediate(false);
            }
            setShowShelfOnly(false);
            quickSettingsControllerImpl.mTwoFingerExpandPossible = false;
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.mTrackedHeadsUpNotification = null;
            for (int i5 = 0; i5 < notificationPanelViewController.mTrackingHeadsUpListeners.size(); i5++) {
                ((Consumer) notificationPanelViewController.mTrackingHeadsUpListeners.get(i5)).accept(null);
            }
            this.mExpandingFromHeadsUp = false;
            setPanelScrimMinFraction(0.0f);
            setKeyguardStatusBarAlpha();
            SecNotificationPanelViewController secNotificationPanelViewController = this.mSecNotificationPanelViewController;
            if (secNotificationPanelViewController != null) {
                boolean zIsKeyguardShowing$1 = isKeyguardShowing$1();
                SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = secNotificationPanelViewController.secQuickSettingsControllerImpl;
                if (secQuickSettingsControllerImpl != null) {
                    boolean zBooleanValue = ((Boolean) ((ShadeRepositoryImpl) secNotificationPanelViewController.shadeRepository).legacyExpandedOrAwaitingInputTransfer.$$delegate_0.getValue()).booleanValue();
                    if (!zBooleanValue) {
                        Object obj = secQuickSettingsControllerImpl.qsSupplier.get();
                        QSFragmentLegacy qSFragmentLegacy = obj instanceof QSFragmentLegacy ? (QSFragmentLegacy) obj : null;
                        if (qSFragmentLegacy != null && (qSImpl = qSFragmentLegacy.mQsImpl) != null && (secQSImpl = qSImpl.mSecQSImpl) != null && (secQSImplAnimatorManager = secQSImpl.secQSImplAnimatorManager) != null) {
                            secQSImplAnimatorManager.onPanelClosed$1();
                        }
                        setMotionAborted();
                    }
                    SecPanelSplitHelper.Companion.getClass();
                    if (!SecPanelSplitHelper.isEnabled && !zIsKeyguardShowing$1 && zBooleanValue && secNotificationPanelViewController.quickSettingsController.getExpanded() && (secPanelSAStatusLogInteractor = secNotificationPanelViewController.panelSAStatusLogInteractor) != null) {
                        StateFlowImpl stateFlowImpl = secPanelSAStatusLogInteractor.repository._openQuickPanelFrom2Depth;
                        LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl.getValue(), 1L, stateFlowImpl, null);
                    }
                }
                this.mHeadsUpVisibleOnDown = false;
                boolean zIsPanelExpanded = isPanelExpanded();
                ViewRootImpl viewRootImpl = notificationPanelView.getRootView().getViewRootImpl();
                int i6 = this.mBarState;
                if (viewRootImpl != null) {
                    viewRootImpl.setDisableSuperHdr(new SurfaceControl.Transaction(), i6 == 0 ? zIsPanelExpanded : false);
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
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        boolean z = quickSettingsControllerImpl.mFullyExpanded;
        int i = SceneContainerFlag.$r8$clinit;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = quickSettingsControllerImpl.mNotificationStackScrollLayoutController;
        notificationStackScrollLayoutController.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
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
        if (quickSettingsControllerImpl.getExpanded()) {
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
        KeyguardAffordanceHelperCallback keyguardAffordanceHelperCallback = this.mKeyguardAffordanceHelperCallback;
        NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
        (notificationPanelViewController.mView.getLayoutDirection() == 1 ? notificationPanelViewController.mKeyguardSecBottomArea.getRightView() : notificationPanelViewController.mKeyguardSecBottomArea.getLeftView()).getClass();
        NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
        (notificationPanelViewController2.mView.getLayoutDirection() == 1 ? notificationPanelViewController2.mKeyguardSecBottomArea.getLeftView() : notificationPanelViewController2.mKeyguardSecBottomArea.getRightView()).getClass();
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
        FaceWidgetContainerWrapper faceWidgetContainerWrapper = this.mKeyguardStatusBase;
        int i = this.mBarState;
        faceWidgetContainerWrapper.setKeyguardStatusViewVisibility(i, i, false, false);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void onDragDownAmountChanged(float f) {
        if (this.mBarState == 1) {
            this.mBottomAreaShadeAlpha = ((PathInterpolator) Interpolators.ALPHA_IN).getInterpolation(1.0f - f);
            updateKeyguardSecBottomAreaAlpha();
        }
        this.mKeyguardStatusBarViewController.mDraggedFraction = f;
        updateClock$1();
    }

    public final void onEmptySpaceClick(float f, float f2) {
        NotificationPanelViewController$$ExternalSyntheticLambda18 notificationPanelViewController$$ExternalSyntheticLambda18 = this.mPostCollapseRunnable;
        boolean zIsEnabled = SecPanelSplitHelper.isEnabled();
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        NotificationPanelView notificationPanelView = this.mView;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        if (!zIsEnabled) {
            if (this.mBarState != 1) {
                notificationStackScrollLayoutController.getClass();
                int i = SceneContainerFlag.$r8$clinit;
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                if (notificationStackScrollLayoutController.mView.isBelowLastNotification(f, f2) || notificationStackScrollLayoutController.isLeftOrRightOutOfNSSL(f) || quickSettingsControllerImpl.isOutOfQsContents(f, f2)) {
                    notificationPanelView.post(notificationPanelViewController$$ExternalSyntheticLambda18);
                    return;
                }
            }
            onMiddleClicked();
            return;
        }
        SecPanelSplitHelper secPanelSplitHelper = this.mPanelSplitHelper;
        if (secPanelSplitHelper.isShadeState()) {
            notificationStackScrollLayoutController.getClass();
            int i2 = SceneContainerFlag.$r8$clinit;
            RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
            if (notificationStackScrollLayoutController.mView.isBelowLastNotification(f, f2) || notificationStackScrollLayoutController.isLeftOrRightOutOfNSSL(f)) {
                notificationPanelView.post(notificationPanelViewController$$ExternalSyntheticLambda18);
                return;
            }
        }
        if (secPanelSplitHelper.isQSState() && quickSettingsControllerImpl.isOutOfQsContents(f, f2)) {
            notificationPanelView.post(notificationPanelViewController$$ExternalSyntheticLambda18);
        } else {
            onMiddleClicked();
        }
    }

    public void onFinishInflate() throws Resources.NotFoundException {
        FaceWidgetContainerWrapper faceWidgetContainerWrapper;
        FaceWidgetKeyguardStatusCallbackWrapper faceWidgetKeyguardStatusCallbackWrapper;
        EmergencyButton emergencyButton;
        int i = 5;
        int i2 = 4;
        int i3 = 0;
        int i4 = 3;
        loadDimens();
        NotificationPanelView notificationPanelView = this.mView;
        this.mKeyguardStatusBar = (KeyguardStatusBarView) notificationPanelView.findViewById(R.id.keyguard_header);
        KeyguardStatusBarViewController keyguardStatusBarViewController = ((DaggerReferenceGlobalRootComponent.KeyguardStatusBarViewComponentImpl) this.mKeyguardStatusBarViewComponentFactory.build((KeyguardStatusBarView) notificationPanelView.findViewById(R.id.keyguard_header), this.mShadeViewStateProvider)).getKeyguardStatusBarViewController();
        this.mKeyguardStatusBarViewController = keyguardStatusBarViewController;
        keyguardStatusBarViewController.init();
        KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = this.mKeyguardSecBottomAreaViewController;
        KeyguardSecBottomAreaView view = keyguardSecBottomAreaViewController.getView();
        this.mKeyguardSecBottomArea = view;
        NotificationPanelViewController$$ExternalSyntheticLambda43 notificationPanelViewController$$ExternalSyntheticLambda43 = new NotificationPanelViewController$$ExternalSyntheticLambda43(keyguardSecBottomAreaViewController, 1);
        KeyguardIndicationController keyguardIndicationController = this.mKeyguardIndicationController;
        keyguardIndicationController.mUpdatePosition = notificationPanelViewController$$ExternalSyntheticLambda43;
        keyguardIndicationController.setIndicationArea((ViewGroup) view.findViewById(R.id.keyguard_indication_area));
        keyguardIndicationController.setUpperTextView((KeyguardIndicationTextView) this.mKeyguardSecBottomArea.findViewById(R.id.keyguard_upper_fingerprint_indication));
        if (CscRune.LOCKUI_BOTTOM_USIM_TEXT && (emergencyButton = this.mKeyguardSecBottomArea.emergencyButton) != null) {
            EmergencyButtonController.Factory factory = this.mEmergencyButtonControllerFactory;
            factory.getClass();
            factory.create(emergencyButton).init();
        }
        this.mKeyguardSecBottomArea.pluginLockData = this.mPluginLockData;
        if (this.mSecAffordanceHelper == null) {
            this.mSecAffordanceHelper = new KeyguardSecAffordanceHelper(this.mKeyguardAffordanceHelperCallback, notificationPanelView.getContext(), this.mKeyguardSecBottomArea);
            keyguardSecBottomAreaViewController.setUserSetupComplete(this.mUserSetupComplete);
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
                if (pluginFaceWidgetManager2 != null && (faceWidgetKeyguardStatusCallbackWrapper = pluginFaceWidgetManager2.mKeyguardStatusCallbackWrapper) != null) {
                    faceWidgetKeyguardStatusCallbackWrapper.mStatusCallback = anonymousClass10;
                }
            }
        }
        this.mKeyguardStatusBase = faceWidgetContainerWrapper;
        this.mLockscreenShadeTransitionController.addCallback(this.mLockscreenShadeTransitonCallback);
        this.mKeyguardStatusBase.mIsDLSViewEnabledSupplier = new NotificationPanelViewController$$ExternalSyntheticLambda12(this, i4);
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        LockscreenNotificationManager lockscreenNotificationManager = this.mLockscreenNotificationManager;
        lockscreenNotificationManager.getClass();
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
        if (LsRune.SECURITY_CONTINUITY_LOCKSCREEN_VI) {
            KeyguardContinuityLockscreenAffordanceArea keyguardContinuityLockscreenAffordanceArea = (KeyguardContinuityLockscreenAffordanceArea) notificationPanelView.findViewById(R.id.keyguard_indication_continuity_vi_view);
            this.mContinuityLockScreenContainer = keyguardContinuityLockscreenAffordanceArea;
            keyguardContinuityLockscreenAffordanceArea.bringToFront();
            KeyguardContinuityLockscreenAffordanceController keyguardContinuityLockscreenAffordanceController = new KeyguardContinuityLockscreenAffordanceController(this.mContinuityLockScreenContainer, this.mUpdateMonitor, this.mStatusBarStateController, this.mKeyguardStateController, this.mSelectedUserInteractor);
            this.mContinuityLockScreenContainerController = keyguardContinuityLockscreenAffordanceController;
            keyguardContinuityLockscreenAffordanceController.init();
        }
        this.mNotificationContainerParent = (NotificationsQuickSettingsContainer) notificationPanelView.findViewById(R.id.notification_container_parent);
        NsslHeightChangedListener nsslHeightChangedListener = new NsslHeightChangedListener(this, i3);
        NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout2.mOnHeightChangedListener = nsslHeightChangedListener;
        int i5 = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        notificationStackScrollLayout2.getClass();
        notificationStackScrollLayout2.mOnEmptySpaceClickListener = this.mOnEmptySpaceClickListener;
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        quickSettingsControllerImpl.getClass();
        QuickSettingsControllerImpl.NsslOverscrollTopChangedListener nsslOverscrollTopChangedListener = new QuickSettingsControllerImpl.NsslOverscrollTopChangedListener(quickSettingsControllerImpl, i3);
        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = quickSettingsControllerImpl.mNotificationStackScrollLayoutController;
        notificationStackScrollLayoutController2.getClass();
        NotificationStackScrollLayout notificationStackScrollLayout3 = notificationStackScrollLayoutController2.mView;
        notificationStackScrollLayout3.getClass();
        notificationStackScrollLayout3.mOverscrollTopChangedListener = nsslOverscrollTopChangedListener;
        notificationStackScrollLayout3.mOnStackYChanged = new QuickSettingsControllerImpl$$ExternalSyntheticLambda18(quickSettingsControllerImpl, 4);
        notificationStackScrollLayout3.mScrollListener = new QuickSettingsControllerImpl$$ExternalSyntheticLambda18(quickSettingsControllerImpl, 5);
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 = ((ShadeInteractorImpl) quickSettingsControllerImpl.mShadeInteractor).isExpandToQsEnabled;
        QuickSettingsControllerImpl$$ExternalSyntheticLambda18 quickSettingsControllerImpl$$ExternalSyntheticLambda18 = new QuickSettingsControllerImpl$$ExternalSyntheticLambda18(quickSettingsControllerImpl, 2);
        JavaAdapter javaAdapter = quickSettingsControllerImpl.mJavaAdapter;
        javaAdapter.alwaysCollectFlow(flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3, quickSettingsControllerImpl$$ExternalSyntheticLambda18);
        javaAdapter.alwaysCollectFlow(((CommunalTransitionViewModel) quickSettingsControllerImpl.mCommunalTransitionViewModelLazy.get()).isUmoOnCommunal, new QuickSettingsControllerImpl$$ExternalSyntheticLambda18(quickSettingsControllerImpl, 3));
        this.mShadeHeadsUpTracker.addTrackingHeadsUpListener(new NotificationPanelViewController$$ExternalSyntheticLambda43(notificationStackScrollLayoutController, i3));
        if (LsRune.SECURITY_FINGERPRINT_GUIDE_POPUP) {
            ViewStub viewStub2 = (ViewStub) notificationPanelView.findViewById(R.id.keyguard_fingerprint_guide_popup_stub);
            viewStub2.setLayoutResource(R.layout.keyguard_fingerprint_guide_popup);
            KeyguardFingerprintGuidePopup keyguardFingerprintGuidePopup = (KeyguardFingerprintGuidePopup) viewStub2.inflate();
            if (keyguardFingerprintGuidePopup != null) {
                keyguardFingerprintGuidePopup.bringToFront();
            }
        }
        NotificationWakeUpCoordinator notificationWakeUpCoordinator = this.mWakeUpCoordinator;
        notificationWakeUpCoordinator.stackScrollerController = notificationStackScrollLayoutController;
        notificationWakeUpCoordinator.pulseExpanding = notificationStackScrollLayoutController.mView.mAmbientState.isPulseExpanding();
        notificationStackScrollLayoutController.mView.mAmbientState.mOnPulseHeightChangedListener = new NotificationWakeUpCoordinator$setStackScroller$1(notificationWakeUpCoordinator);
        notificationWakeUpCoordinator.wakeUpListeners.add(new NotificationWakeUpCoordinator.WakeUpListener() { // from class: com.android.systemui.shade.NotificationPanelViewController.8
            @Override // com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator.WakeUpListener
            public final void onFullyHiddenChanged(boolean z) {
                KeyguardStatusBarViewController keyguardStatusBarViewController2 = NotificationPanelViewController.this.mKeyguardStatusBarViewController;
                keyguardStatusBarViewController2.getClass();
                int i6 = SceneContainerFlag.$r8$clinit;
                RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                keyguardStatusBarViewController2.updateForHeadsUp(true);
            }
        });
        notificationPanelView.mRtlChangeListener = new NotificationPanelViewController$$ExternalSyntheticLambda0(this);
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
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 = this.mDreamingToLockscreenTransitionViewModel.lockscreenAlpha;
        NotificationPanelViewController$$ExternalSyntheticLambda16 notificationPanelViewController$$ExternalSyntheticLambda16 = new NotificationPanelViewController$$ExternalSyntheticLambda16(this, 6);
        CoroutineDispatcher coroutineDispatcher = this.mMainDispatcher;
        JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1, notificationPanelViewController$$ExternalSyntheticLambda16, coroutineDispatcher);
        Edge.StateToState stateToStateM = KeyguardInteractor$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.AOD, KeyguardState.LOCKSCREEN);
        KeyguardTransitionInteractor keyguardTransitionInteractor = this.mKeyguardTransitionInteractor;
        JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionInteractor.transition(stateToStateM), new NotificationPanelViewController$$ExternalSyntheticLambda16(this, i4), coroutineDispatcher);
        JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionInteractor.currentKeyguardState, new NotificationPanelViewController$$ExternalSyntheticLambda16(this, i2), coroutineDispatcher);
        JavaAdapterKt.collectFlow(notificationPanelView, this.mShadeAnimationInteractor.isLaunchingActivity, new NotificationPanelViewController$$ExternalSyntheticLambda16(this, i), coroutineDispatcher);
        int i6 = QSComposeFragment.$r8$clinit;
        this.mEditModeContainer = notificationPanelView.findViewById(R.id.keyguard_edit_mode_container);
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        lockscreenNotificationIconsOnlyController.getClass();
        lockscreenNotificationManager.addCallback(lockscreenNotificationIconsOnlyController);
        if (DeviceState.shouldEnableKeyguardScreenRotation(notificationPanelView.getContext())) {
            DeviceState.isTablet();
        }
    }

    public void onFlingEnd(boolean z) throws Resources.NotFoundException {
        this.mIsFlinging = false;
        this.mExpectingSynthesizedDown = false;
        setOverExpansionInternal(0.0f, false);
        SecNotificationPanelViewController secNotificationPanelViewController = this.mSecNotificationPanelViewController;
        if (secNotificationPanelViewController != null) {
            SecPanelSplitHelper secPanelSplitHelper = secNotificationPanelViewController.panelSplitHelper;
            if (secPanelSplitHelper != null) {
                secPanelSplitHelper.isOnceOverExpanded = false;
            }
            if (z) {
                boolean z2 = !isCollapsing() && isPanelExpanded();
                ValueAnimator valueAnimator = this.mHeightAnimator;
                if (valueAnimator != null) {
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    if (z2 && animatedFraction != 1.0f) {
                        valueAnimator.end();
                        Log.d("QuickPanelLog", "height animator fraction is " + animatedFraction + " while expanded, so make it end force");
                    }
                }
            }
        }
        setAnimator(null);
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
        ((ShadeRepositoryImpl) this.mShadeRepository).setCurrentFling(null);
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final void onLockStarEnabled(boolean z) {
        LogUtil.d("NotificationPanelView", KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("onLockStarEnabled : ", z), new Object[0]);
    }

    public final void onMiddleClicked() {
        int i = this.mBarState;
        if (i != 1) {
            if (i == 2 && !this.mQsController.getExpanded()) {
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
            return;
        }
        this.mLockscreenGestureLogger.write(188, 0, 0);
        new UiEventLoggerImpl().log(LockscreenGestureLogger.LockscreenUiEvent.LOCKSCREEN_LOCK_SHOW_HINT);
        this.mKeyguardIndicationController.showActionToUnlock();
        this.mKeyguardClockInteractor.getClass();
    }

    public void onQsSetExpansionHeightCalled(boolean z) throws Resources.NotFoundException {
        requestScrollerTopPaddingUpdate();
        this.mKeyguardStatusBarViewController.updateViewState();
        int i = this.mBarState;
        if (i == 2 || i == 1) {
            updateKeyguardSecBottomAreaAlpha();
            positionClockAndNotifications(false);
        }
        if (this.mAccessibilityManager.isEnabled()) {
            this.mView.getRootView().setAccessibilityPaneTitle(determineAccessibilityPaneTitle());
        }
        if (!this.mFalsingManager.isUnlockingDisabled() && z) {
            this.mFalsingCollector.getClass();
        }
        if (QpRune.QUICK_DATA_USAGE_LABEL) {
            ((DataUsageLabelManager) this.mDataUsageLabelManagerLazy.get()).updateLabelVisibility(false);
        }
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void onStatusBarLongPress() {
        Log.i("NotificationPanelView", "Status Bar was long pressed.");
        if (DISABLE_LONG_PRESS_EXPAND) {
            Log.i("NotificationPanelView", "Ignoring status Bar long press on virtualized test device.");
        } else {
            int i = ShadeExpandsOnStatusBarLongPress.$r8$clinit;
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            throw new IllegalStateException("New code path not supported when com.android.systemui.shade_expands_on_status_bar_long_press is disabled.");
        }
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void onThemeChanged() {
        this.mConfigurationListener.onThemeChanged();
    }

    public final void onTrackingStarted() {
        KeyguardSecAffordanceHelper keyguardSecAffordanceHelper;
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
        if ((i == 1 || i == 2) && (keyguardSecAffordanceHelper = this.mSecAffordanceHelper) != null) {
            KeyguardSecAffordanceHelper.updateIcon(keyguardSecAffordanceHelper.mRightIcon, 0.0f, true, false);
            KeyguardSecAffordanceHelper.updateIcon(keyguardSecAffordanceHelper.mLeftIcon, 0.0f, true, false);
        }
        NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.mPanelTracking = true;
        notificationStackScrollLayout.mAmbientState.mPanelTracking = true;
        notificationStackScrollLayout.mSwipeHelper.resetExposedMenuView$1(true, true);
        cancelPendingCollapse(false);
    }

    public final void onTrackingStopped(boolean z) {
        int i;
        KeyguardSecAffordanceHelper keyguardSecAffordanceHelper;
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
        if (z && (((i = this.mBarState) == 1 || i == 2) && !this.mHintAnimationRunning && (keyguardSecAffordanceHelper = this.mSecAffordanceHelper) != null)) {
            keyguardSecAffordanceHelper.reset(true);
        }
        NotificationShadeDepthController notificationShadeDepthController = this.mDepthController;
        if (notificationShadeDepthController.blursDisabledForUnlock) {
            notificationShadeDepthController.blursDisabledForUnlock = false;
            notificationShadeDepthController.scheduleUpdate();
        }
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final Bundle onUiInfoRequested(boolean z) {
        new Bundle();
        Bundle bundleOnUiInfoRequested = this.mKeyguardSecBottomAreaViewController.onUiInfoRequested(z);
        NotificationPanelView notificationPanelView = this.mView;
        int i = Settings.System.getInt(notificationPanelView.getContext().getContentResolver(), SettingsHelper.INDEX_LOCKSCREEN_MINIMIZING_NOTIFICATION, 1);
        bundleOnUiInfoRequested.putInt("noti_type", i);
        bundleOnUiInfoRequested.putInt("noti_visibility", Settings.Secure.getInt(notificationPanelView.getContext().getContentResolver(), SettingsHelper.INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS, 1));
        bundleOnUiInfoRequested.putInt("noti_top", getNotificationTopMargin(z));
        if (i != 0) {
            bundleOnUiInfoRequested.putInt("noti_bottom", getNotificationTopMargin(z) + (z ? this.mResources.getDimensionPixelSize(R.dimen.keyguard_indication_dls_default_notification_height_land) : this.mResources.getDimensionPixelSize(R.dimen.keyguard_indication_dls_default_notification_height)));
        }
        Log.d("NotificationPanelView", "onUiInfoRequested bottom: " + bundleOnUiInfoRequested.toString());
        return bundleOnUiInfoRequested;
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final void onUnNeedLockAppStarted(ComponentName componentName) {
        KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = this.mKeyguardSecBottomAreaViewController;
        if (keyguardSecBottomAreaViewController != null) {
            keyguardSecBottomAreaViewController.launchApp(componentName);
        }
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final void onUserActivity() {
        this.mCentralSurfaces.userActivity();
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final void onViewModeChanged(int i) {
        LogUtil.d("NotificationPanelView", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "onViewModeChanged : "), new Object[0]);
        setViewMode(i);
        this.mNotificationStackScrollLayoutController.mView.mSpeedBumpIndexDirty = true;
    }

    public final void positionClockAndNotifications(boolean z) {
        int iUpdatePosition;
        PluginNotificationController pluginNotificationController;
        KeyguardClockPositionAlgorithm.Result result = this.mClockPositionResult;
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        boolean zIsKeyguardShowing$1 = isKeyguardShowing$1();
        if (zIsKeyguardShowing$1 || z) {
            ClockSize clockSize = (this.mActiveNotificationsInteractor.getAreAnyNotificationsPresentValue() || this.mMediaDataManager.hasActiveMediaOrRecommendation()) ? ClockSize.SMALL : ClockSize.LARGE;
            KeyguardClockInteractor keyguardClockInteractor = this.mKeyguardClockInteractor;
            keyguardClockInteractor.getClass();
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            int i = SceneContainerFlag.$r8$clinit;
            KeyguardClockRepositoryImpl keyguardClockRepositoryImpl = (KeyguardClockRepositoryImpl) keyguardClockInteractor.keyguardClockRepository;
            keyguardClockRepositoryImpl.getClass();
            keyguardClockRepositoryImpl._clockSize.setValue(clockSize);
            updateKeyguardStatusViewAlignment();
            this.mClockPositionAlgorithm.setup(this.mScreenOffAnimationController.shouldExpandNotifications() ? 1.0f : this.mInterpolatedDarkAmount, this.mOverStretchAmount, quickSettingsControllerImpl.getHeaderHeight(), this.mKeyguardBypassController.getBypassEnabled());
            this.mClockPositionAlgorithm.run(result);
            updateClock$1();
        }
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        if (zIsKeyguardShowing$1) {
            int i2 = result.stackScrollerPaddingExpanded;
            boolean z2 = CscRune.KEYGUARD_DCM_LIVE_UX;
            iUpdatePosition = z2 ? result.stackScrollerPadding : i2;
            if (z2) {
                iUpdatePosition += this.mMascotViewContainer.updatePosition(iUpdatePosition, (lockscreenNotificationIconsOnlyController == null || lockscreenNotificationIconsOnlyController.getIconContainer() == null) ? 0 : lockscreenNotificationIconsOnlyController.getIconContainer().getHeight());
            }
        } else {
            iUpdatePosition = quickSettingsControllerImpl.getHeaderHeight();
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        notificationStackScrollLayoutController.getClass();
        int i3 = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.getClass();
        notificationStackScrollLayout.mIntrinsicPadding = iUpdatePosition;
        int i4 = this.mStackScrollerMeasuringPass + 1;
        this.mStackScrollerMeasuringPass = i4;
        if (i4 > 2) {
            RecyclerView$$ExternalSyntheticOutline0.m(this.mStackScrollerMeasuringPass, "NotificationPanelView", new StringBuilder("increased StackScrollerMeasuringPass : "));
        }
        requestScrollerTopPaddingUpdate();
        if (this.mStackScrollerMeasuringPass > 2) {
            RecyclerView$$ExternalSyntheticOutline0.m(this.mStackScrollerMeasuringPass, "NotificationPanelView", new StringBuilder("reset StackScrollerMeasuringPass from "));
        }
        this.mStackScrollerMeasuringPass = 0;
        this.mAnimateNextPositionUpdate = false;
        if (lockscreenNotificationIconsOnlyController != null) {
            if (!isOnAod()) {
                LockscreenNotificationManager lockscreenNotificationManager = this.mLockscreenNotificationManager;
                lockscreenNotificationManager.getClass();
                int i5 = LockscreenNotificationManager.mCurrentNotificationType;
                if (i5 != 1 && i5 != 3 && i5 != 2 && i5 != 4 && (i5 != 0 || lockscreenNotificationManager.mSettingNotificationType != 1)) {
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

    public final View provideComplication() {
        List list;
        if (!DeviceState.isTablet() || (list = this.mKeyguardStatusBase.mContentsContainerList) == null || list.size() <= 2) {
            return null;
        }
        return (View) list.get(2);
    }

    public void reInflateViews() {
        updateResources$1();
        SysuiStatusBarStateController sysuiStatusBarStateController = this.mStatusBarStateController;
        float dozeAmount = sysuiStatusBarStateController.getDozeAmount();
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) sysuiStatusBarStateController;
        this.mStatusBarStateListener.onDozeAmountChanged(dozeAmount, statusBarStateControllerImpl.mDozeInterpolator.getInterpolation(statusBarStateControllerImpl.mDozeAmount));
        setKeyguardSecBottomAreaVisibility(this.mBarState, false);
        ViewGroup viewGroup = (ViewGroup) this.mKeyguardSecBottomArea.findViewById(R.id.keyguard_indication_area);
        KeyguardIndicationController keyguardIndicationController = this.mKeyguardIndicationController;
        keyguardIndicationController.setIndicationArea(viewGroup);
        keyguardIndicationController.setUpperTextView((KeyguardIndicationTextView) this.mKeyguardSecBottomArea.findViewById(R.id.keyguard_upper_fingerprint_indication));
        FaceWidgetContainerWrapper faceWidgetContainerWrapper = this.mKeyguardStatusBase;
        int i = this.mBarState;
        faceWidgetContainerWrapper.setKeyguardStatusViewVisibility(i, i, false, false);
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void registerAODDoubleTouchListener(View.OnTouchListener onTouchListener) {
        this.mAODDoubleTouchListener = onTouchListener;
    }

    public final void requestScrollerTopPaddingUpdate() {
        float fMax;
        int i = SceneContainerFlag.$r8$clinit;
        int keyguardNotificationStaticPadding = getKeyguardNotificationStaticPadding();
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        quickSettingsControllerImpl.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        boolean z = quickSettingsControllerImpl.mBarState == 1;
        if (quickSettingsControllerImpl.mSizeChangeAnimator != null && !SecPanelSplitHelper.isEnabled()) {
            fMax = Math.max(((Integer) quickSettingsControllerImpl.mSizeChangeAnimator.getAnimatedValue()).intValue(), keyguardNotificationStaticPadding);
        } else if (!z || quickSettingsControllerImpl.mAmbientState.mDragDownOnKeyguard) {
            fMax = SecPanelSplitHelper.isEnabled() ? ((float) Math.max(r2.getNotificationsTopPadding((float) quickSettingsControllerImpl.mSecQuickSettingsControllerImpl.minExpansionHeightSupplier.getAsDouble()), quickSettingsControllerImpl.mQuickQsHeaderHeight)) + quickSettingsControllerImpl.mLastOverscroll : Math.max(quickSettingsControllerImpl.mQsFrameTranslateController.getNotificationsTopPadding(quickSettingsControllerImpl.mExpansionHeight), quickSettingsControllerImpl.mQuickQsHeaderHeight);
        } else {
            fMax = MathUtils.lerp(keyguardNotificationStaticPadding, quickSettingsControllerImpl.mMaxExpansionHeight, quickSettingsControllerImpl.computeExpansionFraction());
        }
        boolean zIsKeyguardShowing$1 = isKeyguardShowing$1();
        SharedNotificationContainerInteractor sharedNotificationContainerInteractor = this.mSharedNotificationContainerInteractor;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        if (zIsKeyguardShowing$1) {
            int dimensionPixelSize = this.mView.getResources().getDimensionPixelSize(R.dimen.keyguard_margin_between_noti_indication);
            KeyguardClockPositionAlgorithm keyguardClockPositionAlgorithm = this.mClockPositionAlgorithm;
            if (keyguardClockPositionAlgorithm instanceof FaceWidgetPositionAlgorithmWrapper) {
                PluginSecKeyguardClockPositionAlgorithm pluginSecKeyguardClockPositionAlgorithm = ((FaceWidgetPositionAlgorithmWrapper) keyguardClockPositionAlgorithm).mPositionAlgorithm;
                dimensionPixelSize += pluginSecKeyguardClockPositionAlgorithm != null ? pluginSecKeyguardClockPositionAlgorithm.getBottomMarginY() : 0;
            }
            if (CscRune.KEYGUARD_DCM_LIVE_UX) {
                DcmMascotViewContainer dcmMascotViewContainer = this.mMascotViewContainer;
                dimensionPixelSize += dcmMascotViewContainer.getVisibility() == 0 ? dcmMascotViewContainer.mascotHeight + dcmMascotViewContainer.mascotTopMarin + dcmMascotViewContainer.mascotBottomMarin : 0;
            }
            if (CscRune.LOCKUI_BOTTOM_USIM_TEXT) {
                dimensionPixelSize += this.mKeyguardSecBottomAreaViewController.getUsimTextAreaHeight();
            }
            float f = dimensionPixelSize;
            notificationStackScrollLayoutController.mView.mKeyguardBottomPadding = f;
            sharedNotificationContainerInteractor._bottomPosition.updateState(null, Float.valueOf(f));
        } else {
            notificationStackScrollLayoutController.mView.mKeyguardBottomPadding = -1.0f;
            sharedNotificationContainerInteractor._bottomPosition.updateState(null, Float.valueOf(-1.0f));
        }
        notificationStackScrollLayoutController.getClass();
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.getClass();
        int i2 = (int) fMax;
        if (notificationStackScrollLayout.getLayoutMinHeightInternal() + i2 > notificationStackScrollLayout.getHeight()) {
            notificationStackScrollLayout.mTopPaddingOverflow = r7 - notificationStackScrollLayout.getHeight();
        } else {
            notificationStackScrollLayout.mTopPaddingOverflow = 0.0f;
        }
        if (notificationStackScrollLayout.mAmbientState.getTopPadding() != i2) {
            AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
            ambientState.getClass();
            ambientState.mTopPadding = i2;
            notificationStackScrollLayout.updateAlgorithmHeightAndPadding();
            notificationStackScrollLayout.updateContentHeight();
            if (notificationStackScrollLayout.mAmbientState.isOnKeyguard$1() && notificationStackScrollLayout.mShouldSkipTopPaddingAnimationAfterFold) {
                notificationStackScrollLayout.mShouldSkipTopPaddingAnimationAfterFold = false;
            }
            notificationStackScrollLayout.updateStackPosition(false);
            notificationStackScrollLayout.requestChildrenUpdate();
            notificationStackScrollLayout.notifyHeightChangeListener(null, false);
            int i3 = notificationStackScrollLayout.mOwnScrollY;
            if (i3 > 0 && i3 < notificationStackScrollLayout.getScrollAmountToScrollBoundary() && notificationStackScrollLayout.mIsChangedOrientation) {
                notificationStackScrollLayout.setOwnScrollY(notificationStackScrollLayout.getScrollAmountToScrollBoundary());
                notificationStackScrollLayout.mIsChangedOrientation = false;
            }
        }
        notificationStackScrollLayout.setExpandedHeight(notificationStackScrollLayout.mExpandedHeight);
        sharedNotificationContainerInteractor._topPosition.updateState(null, Float.valueOf(fMax));
        if (isKeyguardShowing$1() && this.mKeyguardBypassController.getBypassEnabled()) {
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
            this.mKeyguardSecBottomArea.setVisibility(8);
            PluginFaceWidgetManager pluginFaceWidgetManager = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
            if (pluginFaceWidgetManager == null) {
                Log.e("NotificationPanelView", "Failed to get PluginFaceWidgetManager");
            } else {
                pluginFaceWidgetManager.updateNowBarVisibility(8);
            }
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

    public final void setAnimator(ValueAnimator valueAnimator) {
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
        if (z && this.mMediaNowBarExpandState == 1) {
            new Handler(Looper.getMainLooper()).postDelayed(new NotificationPanelViewController$$ExternalSyntheticLambda18(this, 13), 100L);
        } else {
            updateVisibility();
        }
    }

    public void setClosing(boolean z) {
        ((ShadeRepositoryImpl) this.mShadeRepository)._legacyIsClosing.updateState(null, Boolean.valueOf(z));
        this.mAmbientState.mIsClosing = z;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00c8  */
    @Override // com.android.systemui.shade.ShadeSurface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setDozing(boolean z, boolean z2) throws Resources.NotFoundException {
        View view;
        if (z == this.mDozing) {
            return;
        }
        NotificationPanelView notificationPanelView = this.mView;
        notificationPanelView.mDozing = z;
        this.mDozing = z;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        notificationStackScrollLayoutController.getClass();
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
        if (ambientState.mDozing != z) {
            ambientState.mDozing = z;
            notificationStackScrollLayout.requestChildrenUpdate();
            notificationStackScrollLayout.notifyHeightChangeListener(notificationStackScrollLayout.mShelf, false);
        }
        ((KeyguardRepositoryImpl) this.mKeyguardInteractor.repository)._animateBottomAreaDozingTransitions.updateState(null, Boolean.valueOf(z2));
        this.mKeyguardSecBottomAreaViewController.setDozing(z);
        KeyguardStatusBarViewController keyguardStatusBarViewController = this.mKeyguardStatusBarViewController;
        boolean z3 = this.mDozing;
        keyguardStatusBarViewController.getClass();
        keyguardStatusBarViewController.mDozing = z3;
        keyguardStatusBarViewController.updateViewState();
        this.mQsController.mDozing = this.mDozing;
        if (z) {
            this.mBottomAreaShadeAlphaAnimator.cancel();
        }
        int i2 = this.mBarState;
        if (i2 == 1 || i2 == 2) {
            updateDozingVisibilities(z2);
        }
        float f = z ? 1.0f : 0.0f;
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) this.mStatusBarStateController;
        statusBarStateControllerImpl.getClass();
        ValueAnimator valueAnimator = statusBarStateControllerImpl.mDarkAnimator;
        if (valueAnimator == null || !valueAnimator.isRunning()) {
            view = statusBarStateControllerImpl.mView;
            if ((view != null || !view.isAttachedToWindow()) && notificationPanelView.isAttachedToWindow()) {
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
        } else if (!z2 || statusBarStateControllerImpl.mDozeAmountTarget != f) {
            statusBarStateControllerImpl.mDarkAnimator.cancel();
            view = statusBarStateControllerImpl.mView;
            if (view != null) {
                statusBarStateControllerImpl.mView = notificationPanelView;
                statusBarStateControllerImpl.mDozeAmountTarget = f;
                if (z2) {
                }
            } else {
                statusBarStateControllerImpl.mView = notificationPanelView;
                statusBarStateControllerImpl.mDozeAmountTarget = f;
                if (z2) {
                }
            }
        }
        updateKeyguardStatusViewAlignment();
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final void setDynamicLockData(String str) {
        if (str == null || str.isEmpty()) {
            return;
        }
        DynamicLockData dynamicLockDataFromJSon = DynamicLockData.fromJSon(str);
        if (dynamicLockDataFromJSon != null) {
            this.mNotiCardCount = dynamicLockDataFromJSon.getNotificationData().getCardData().getNotiCardNumbers().intValue();
        }
        LogUtil.d("NotificationPanelView", "setDynamicLockData card numbers: " + this.mNotiCardCount, new Object[0]);
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
        ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).batchApplyWindowLayoutParams(new Runnable() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda48
            @Override // java.lang.Runnable
            public final void run() throws Resources.NotFoundException {
                float fCalculatePanelHeightExpanded;
                StringBuilder sb;
                NotificationPanelViewController notificationPanelViewController = this.f$0;
                float f2 = f;
                if (notificationPanelViewController.mExpandLatencyTracking && f2 != 0.0f) {
                    DejankUtils.postAfterTraversal(new NotificationPanelViewController$$ExternalSyntheticLambda18(notificationPanelViewController, 12));
                    notificationPanelViewController.mExpandLatencyTracking = false;
                }
                float maxPanelTransitionDistance = notificationPanelViewController.getMaxPanelTransitionDistance();
                if (SecPanelSplitHelper.isEnabled() && notificationPanelViewController.mHeightAnimator == null && notificationPanelViewController.isTracking()) {
                    notificationPanelViewController.setOverExpansionInternal(Math.max(0.0f, f2 - maxPanelTransitionDistance), true);
                }
                float fMin = Math.min(f2, maxPanelTransitionDistance);
                notificationPanelViewController.mExpandedHeight = fMin;
                if (fMin < 1.0f && fMin != 0.0f && notificationPanelViewController.isClosing()) {
                    notificationPanelViewController.mExpandedHeight = 0.0f;
                    ValueAnimator valueAnimator = notificationPanelViewController.mHeightAnimator;
                    if (valueAnimator != null) {
                        valueAnimator.end();
                    }
                }
                boolean z = notificationPanelViewController.mHeightAnimator == null;
                float f3 = notificationPanelViewController.mExpandedHeight;
                float f4 = notificationPanelViewController.mExpandedFraction;
                float fMin2 = Math.min(1.0f, maxPanelTransitionDistance == 0.0f ? 0.0f : f3 / maxPanelTransitionDistance);
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
                    sb.append(fMin2);
                    quickPanelLogger.logPanelState(sb.toString());
                }
                float fMin3 = Math.min(1.0f, maxPanelTransitionDistance == 0.0f ? 0.0f : notificationPanelViewController.mExpandedHeight / maxPanelTransitionDistance);
                notificationPanelViewController.mExpandedFraction = fMin3;
                if (fMin3 > 0.0f && notificationPanelViewController.mExpectingSynthesizedDown) {
                    notificationPanelViewController.mExpectingSynthesizedDown = false;
                }
                float f5 = notificationPanelViewController.mExpandedHeight;
                QuickSettingsControllerImpl quickSettingsControllerImpl = notificationPanelViewController.mQsController;
                quickSettingsControllerImpl.mShadeExpandedHeight = f5;
                quickSettingsControllerImpl.mShadeExpandedFraction = fMin3;
                quickSettingsControllerImpl.mMediaHierarchyManager.getClass();
                ((ShadeRepositoryImpl) notificationPanelViewController.mShadeRepository)._legacyShadeExpansion.updateState(null, Float.valueOf(fMin3));
                notificationPanelViewController.mExpansionDragDownAmountPx = f2;
                int i = SceneContainerFlag.$r8$clinit;
                float f6 = notificationPanelViewController.mExpandedFraction;
                notificationPanelViewController.mAmbientState.mExpansionFraction = f6;
                float f7 = notificationPanelViewController.mExpandedHeight;
                if (f7 <= 0.0f) {
                    notificationPanelViewController.mShadeLog.logExpansionChanged("onHeightUpdated: fully collapsed.", f6, notificationPanelViewController.isExpanded(), notificationPanelViewController.isTracking(), notificationPanelViewController.mExpansionDragDownAmountPx);
                } else if (notificationPanelViewController.isFullyExpanded()) {
                    notificationPanelViewController.mShadeLog.logExpansionChanged("onHeightUpdated: fully expanded.", notificationPanelViewController.mExpandedFraction, notificationPanelViewController.isExpanded(), notificationPanelViewController.isTracking(), notificationPanelViewController.mExpansionDragDownAmountPx);
                }
                if ((!quickSettingsControllerImpl.getExpanded() || quickSettingsControllerImpl.isExpandImmediate() || (notificationPanelViewController.mIsExpandingOrCollapsing && quickSettingsControllerImpl.mExpandedWhenExpandingStarted)) && notificationPanelViewController.mStackScrollerMeasuringPass <= 2) {
                    notificationPanelViewController.positionClockAndNotifications(false);
                }
                if (quickSettingsControllerImpl.isExpandImmediate() || (quickSettingsControllerImpl.getExpanded() && !quickSettingsControllerImpl.isTracking() && quickSettingsControllerImpl.mExpansionAnimator == null && !quickSettingsControllerImpl.mExpansionFromOverscroll)) {
                    if (notificationPanelViewController.isKeyguardShowing$1()) {
                        fCalculatePanelHeightExpanded = f7 / notificationPanelViewController.getMaxPanelHeight();
                    } else {
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
                        notificationStackScrollLayoutController.getClass();
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                        notificationStackScrollLayout.getClass();
                        float f8 = notificationStackScrollLayout.mIntrinsicPadding;
                        notificationStackScrollLayoutController.mView.getClass();
                        float layoutMinHeightInternal = r1.getLayoutMinHeightInternal() + f8;
                        fCalculatePanelHeightExpanded = (f7 - layoutMinHeightInternal) / (quickSettingsControllerImpl.calculatePanelHeightExpanded(notificationPanelViewController.mClockPositionResult.stackScrollerPadding) - layoutMinHeightInternal);
                    }
                    quickSettingsControllerImpl.setExpansionHeight((fCalculatePanelHeightExpanded * (quickSettingsControllerImpl.mMaxExpansionHeight - r4)) + quickSettingsControllerImpl.mMinExpansionHeight);
                }
                if (QpRune.QUICK_DATA_USAGE_LABEL) {
                    DataUsageLabelManager dataUsageLabelManager = (DataUsageLabelManager) notificationPanelViewController.mDataUsageLabelManagerLazy.get();
                    DataUsageLabelParent dataUsageLabelParent = dataUsageLabelManager.mDataUsageLabelParent;
                    float fMin4 = Math.min(1.0f, f7 / dataUsageLabelParent.mMaxPanelHeightSupplier.getAsInt());
                    ViewGroup parentViewGroup = dataUsageLabelParent.getParentViewGroup();
                    if (Float.compare(fMin4, 1.0f) == 0 && !dataUsageLabelManager.mLabelAlphaAnimStarted) {
                        dataUsageLabelManager.mLabelAlphaAnimStarted = true;
                        dataUsageLabelManager.animateLabelAlpha(parentViewGroup, true);
                    } else if (fMin4 < 1.0f && dataUsageLabelManager.mLabelAlphaAnimStarted) {
                        dataUsageLabelManager.mLabelAlphaAnimStarted = false;
                        dataUsageLabelManager.animateLabelAlpha(parentViewGroup, false);
                    } else if (fMin4 == 0.0f) {
                        dataUsageLabelManager.mLabelAlphaAnimStarted = false;
                    }
                }
                notificationPanelViewController.updateExpandedHeight(f7);
                if (notificationPanelViewController.mBarState == 1) {
                    notificationPanelViewController.mKeyguardStatusBarViewController.updateViewState();
                }
                quickSettingsControllerImpl.updateExpansion();
                notificationPanelViewController.updatePanelExpanded();
                notificationPanelViewController.updateGestureExclusionRect();
                notificationPanelViewController.updateExpansionAndVisibility();
            }
        });
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

    @Override // com.android.systemui.shade.ShadeViewController
    public final void setIsOcclusionTransitionRunning(boolean z) {
        this.mIsOcclusionTransitionRunning = z;
    }

    public final void setKeyguardSecBottomAreaVisibility(int i, boolean z) {
        this.mKeyguardSecBottomArea.animate().cancel();
        if (z) {
            ViewPropertyAnimator viewPropertyAnimatorAlpha = this.mKeyguardSecBottomArea.animate().alpha(0.0f);
            KeyguardStateControllerImpl keyguardStateControllerImpl = this.mKeyguardStateController;
            ViewPropertyAnimator startDelay = viewPropertyAnimatorAlpha.setStartDelay(keyguardStateControllerImpl.mKeyguardFadingAwayDelay);
            keyguardStateControllerImpl.getClass();
            startDelay.setDuration(keyguardStateControllerImpl.mKeyguardFadingAwayDuration / 2).setInterpolator(Interpolators.ALPHA_OUT).withEndAction(this.mAnimateKeyguardBottomAreaInvisibleEndRunnable).start();
            return;
        }
        if (i != 1) {
            this.mKeyguardSecBottomArea.animate().cancel();
            this.mKeyguardSecBottomArea.setVisibility(8);
            PluginFaceWidgetManager pluginFaceWidgetManager = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
            if (pluginFaceWidgetManager == null) {
                Log.e("NotificationPanelView", "Failed to get PluginFaceWidgetManager");
                return;
            } else {
                pluginFaceWidgetManager.updateNowBarVisibility(8);
                return;
            }
        }
        this.mKeyguardSecBottomArea.animate().cancel();
        this.mKeyguardSecBottomArea.setVisibility(0);
        PluginFaceWidgetManager pluginFaceWidgetManager2 = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
        if (pluginFaceWidgetManager2 == null) {
            Log.e("NotificationPanelView", "Failed to get PluginFaceWidgetManager");
        } else {
            pluginFaceWidgetManager2.updateNowBarVisibility(this.mBarState == 1 ? 0 : 8);
        }
        if (this.mIsOcclusionTransitionRunning) {
            return;
        }
        this.mKeyguardSecBottomArea.setAlpha(1.0f);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void setKeyguardStatusBarAlpha() throws Resources.NotFoundException {
        this.mKeyguardStatusBarViewController.setAlpha(-1.0f);
    }

    public final void setListening$1(boolean z) {
        KeyguardStatusBarViewController keyguardStatusBarViewController = this.mKeyguardStatusBarViewController;
        keyguardStatusBarViewController.getClass();
        int i = SceneContainerFlag.$r8$clinit;
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

    @Override // com.android.systemui.settings.brightness.domain.interactor.BrightnessMirrorShowingInteractor
    public final void setMirrorShowing(boolean z) {
        this.mBrightnessMirrorShowingRepository._isShowing.updateState(null, Boolean.valueOf(z));
    }

    public final void setMotionAborted() {
        if (this.mMotionAborted) {
            return;
        }
        Log.d("NotificationPanelView", "setMotionAborted");
        QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
        if (quickPanelLogger != null) {
            quickPanelLogger.logPanelState("setMotionAborted");
        }
        this.mMotionAborted = true;
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void setOnStatusBarDownEvent(MotionEvent motionEvent) {
        SecNotificationPanelViewController secNotificationPanelViewController = this.mSecNotificationPanelViewController;
        if (secNotificationPanelViewController != null && secNotificationPanelViewController.isStatusBarWindowViewTouched()) {
            Log.d("NotificationPanelView", "setOnStatusBarDownEvent: failed by StatusBarWindowView Touched");
            return;
        }
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("setOnStatusBarDownEvent null ? "), motionEvent == null, "NotificationPanelView");
        this.mDownEventFromOverView = motionEvent;
        this.mPanelSplitHelper.synthesizedActionDown = motionEvent;
    }

    public void setOverExpansion(float f) {
        PanelPopOverManager panelPopOverManager;
        SecPanelSplitHelper secPanelSplitHelper;
        if (!SecPanelSplitHelper.isEnabled()) {
            this.mOverExpansion = 0.0f;
            return;
        }
        if (f == this.mOverExpansion) {
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
            if (f2 > 15.0f && (secPanelSplitHelper = secNotificationPanelViewController.panelSplitHelper) != null) {
                secPanelSplitHelper.isOnceOverExpanded = true;
            }
        }
        if (!QpRune.QUICK_PANEL_CODE_FOR_POP_OVER || (panelPopOverManager = this.mPanelPopOverManager) == null) {
            return;
        }
        panelPopOverManager.setOverScrollAmount((int) f);
    }

    public final void setOverExpansionInternal(float f, boolean z) {
        if (!z) {
            this.mLastGesturedOverExpansion = -1.0f;
            setOverExpansion(f);
        } else if (this.mLastGesturedOverExpansion != f) {
            this.mLastGesturedOverExpansion = f;
            float fSaturate = MathUtils.saturate(f / (this.mView.getHeight() / 3.0f));
            Interpolator interpolator = Interpolators.EMPHASIZED;
            float fExp = (float) (1.0d - Math.exp(fSaturate * (-4.0f)));
            if (0.0f > fExp) {
                fExp = 0.0f;
            }
            setOverExpansion(fExp * this.mPanelFlingOvershootAmount * 1.5f);
        }
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void setOverStretchAmount(float f) {
        float height = f / this.mView.getHeight();
        Interpolator interpolator = Interpolators.EMPHASIZED;
        float fExp = (float) (1.0d - Math.exp(height * (-4.0f)));
        if (0.0f > fExp) {
            fExp = 0.0f;
        }
        this.mOverStretchAmount = fExp * this.mMaxOverscrollAmountForPulse;
        positionClockAndNotifications(true);
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
        updateKeyguardStatusViewAlignment();
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void setQsScrimEnabled(boolean z) {
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        boolean z2 = quickSettingsControllerImpl.mScrimEnabled != z;
        quickSettingsControllerImpl.mScrimEnabled = z;
        if (z2) {
            quickSettingsControllerImpl.updateQsState$2();
        }
    }

    public final void setShowShelfOnly(boolean z) {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.mShouldShowShelfOnly = z;
        notificationStackScrollLayout.updateAlgorithmLayoutMinHeight();
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void setTouchAndAnimationDisabled(boolean z) throws Resources.NotFoundException {
        QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
        if (quickPanelLogger != null) {
            quickPanelLogger.logPanelState("setTouchAndAnimationDisabled: " + this.mTouchDisabled + " -> " + z);
        }
        this.mTouchDisabled = z;
        if (z) {
            KeyguardSecAffordanceHelper keyguardSecAffordanceHelper = this.mSecAffordanceHelper;
            if (keyguardSecAffordanceHelper != null) {
                keyguardSecAffordanceHelper.getClass();
            }
            cancelHeightAnimator();
            if (isTracking()) {
                onTrackingStopped(true);
            }
            notifyExpandingFinished();
        }
        NotificationStackScrollLayout notificationStackScrollLayout = this.mNotificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.mAnimationsEnabled = !z;
        notificationStackScrollLayout.updateNotificationAnimationStates();
        if (z) {
            notificationStackScrollLayout.mSwipedOutViews.clear();
            notificationStackScrollLayout.mChildrenToRemoveAnimated.clear();
            notificationStackScrollLayout.clearTemporaryViewsInGroup(notificationStackScrollLayout, "setAnimationsEnabled");
        }
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void setUserSetupComplete(boolean z) {
        this.mUserSetupComplete = z;
        this.mKeyguardSecBottomAreaViewController.setUserSetupComplete(z);
    }

    public final void setViewMode(int i) {
        RecyclerView$$ExternalSyntheticOutline0.m(this.mPluginLockViewMode, "NotificationPanelView", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "setViewMode, newMode:", ", oldMode:"));
        this.mKeyguardSecBottomAreaViewController.onViewModeChanged(i);
        if (i != this.mPluginLockViewMode || i == 1) {
            this.mPluginLockViewMode = i;
            CentralSurfacesImpl centralSurfacesImpl = this.mCentralSurfaces;
            if (centralSurfacesImpl != null) {
                boolean z = i == 1 && i == 1;
                EmergencyButtonController$$ExternalSyntheticOutline0.m("setDlsOverLay() : ", "CentralSurfaces", z);
                centralSurfacesImpl.mIsDlsOverlay = z;
            }
            final DcmMascotViewContainer dcmMascotViewContainer = this.mMascotViewContainer;
            AnonymousClass18 anonymousClass18 = this.mSystemUIWidgetCallback;
            NotificationPanelView notificationPanelView = this.mView;
            if (i == 0) {
                CentralSurfacesImpl centralSurfacesImpl2 = this.mCentralSurfaces;
                if (((centralSurfacesImpl2 != null && centralSurfacesImpl2.mBouncerShowing) || notificationPanelView.getVisibility() == 0) && this.mBarState != 0) {
                    if (CscRune.KEYGUARD_DCM_LIVE_UX && dcmMascotViewContainer.getVisibility() != 0 && dcmMascotViewContainer.isMascotEnabled()) {
                        dcmMascotViewContainer.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.DcmMascotViewContainer$updateDelayed$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                dcmMascotViewContainer.setMascotViewVisible(0);
                            }
                        }, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, TimeUnit.MILLISECONDS);
                    }
                    Log.d("NotificationPanelView", "setViewMode, removeSystemUIWidgetCallback:" + anonymousClass18);
                    WallpaperUtils.removeSystemUIWidgetCallback(anonymousClass18);
                }
            } else if (i == 1) {
                notificationPanelView.setClickable(false);
                if (CscRune.KEYGUARD_DCM_LIVE_UX) {
                    dcmMascotViewContainer.setMascotViewVisible(8);
                }
                WallpaperUtils.registerSystemUIWidgetCallback(anonymousClass18, 256L);
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
            CentralSurfacesImpl centralSurfacesImpl3 = this.mCentralSurfaces;
            if (centralSurfacesImpl3 != null) {
                centralSurfacesImpl3.mStatusBarKeyguardViewManager.updateDlsNaviBarVisibility();
            }
            this.mUpdateMonitor.dispatchDlsViewMode(i);
        }
    }

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean shouldHideStatusBarIconsWhenExpanded() {
        if (isLaunchingActivity$1()) {
            return false;
        }
        HeadsUpAppearanceController headsUpAppearanceController = this.mHeadsUpAppearanceController;
        if (headsUpAppearanceController == null || !headsUpAppearanceController.shouldHeadsUpStatusBarBeVisible()) {
            return !this.mShowIconsWhenExpanded;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0071  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean shouldPanelBeVisible() {
        boolean z;
        boolean z2 = this.mHeadsUpAnimatingAway || this.mHeadsUpPinnedMode;
        int i = this.mPanelInVisibleReason;
        this.mPanelInVisibleReason = -1;
        if (!z2) {
            this.mPanelInVisibleReason = -1;
            boolean zIsExpanded = isExpanded();
            if (!zIsExpanded) {
                this.mPanelInVisibleReason = 0;
            }
            if (this.mBouncerShowing) {
                this.mPanelInVisibleReason = 1;
                zIsExpanded = false;
            }
            if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK && zIsExpanded && ((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.sDependency.getDependencyInner(KeyguardFoldController.class))).isUnlockOnFoldOpened()) {
                this.mPanelInVisibleReason = 2;
                zIsExpanded = false;
            }
            if (LsRune.SECURITY_SWIPE_BOUNCER && zIsExpanded && ((KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.sDependency.getDependencyInner(KeyguardStateController.class))).isShownSwipeBouncer()) {
                this.mPanelInVisibleReason = 3;
                zIsExpanded = false;
            }
            if (isKeyguardShowing$1() && this.mFullScreenModeEnabled) {
                this.mPanelInVisibleReason = 5;
                zIsExpanded = false;
            }
            z = zIsExpanded;
        }
        int i2 = this.mPanelInVisibleReason;
        if ((this.mView.getVisibility() == 0) == z && (z || i == i2)) {
            return z;
        }
        com.android.systemui.keyguard.Log.d("KeyguardVisible", "shouldPanelBeVisible %b / headUpVisible=%b, why=%d", Boolean.valueOf(z), Boolean.valueOf(z2), Integer.valueOf(i2));
        return z;
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void showAodUi() throws Resources.NotFoundException {
        setDozing(true, false);
        SysuiStatusBarStateController sysuiStatusBarStateController = this.mStatusBarStateController;
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) sysuiStatusBarStateController;
        statusBarStateControllerImpl.getClass();
        int i = SceneContainerFlag.$r8$clinit;
        statusBarStateControllerImpl.recordHistoricalState(1, statusBarStateControllerImpl.mState, true);
        statusBarStateControllerImpl.updateUpcomingState(1);
        sysuiStatusBarStateController.setState(1);
        this.mStatusBarStateListener.onDozeAmountChanged(1.0f, 1.0f);
        if (LsRune.AOD_FULLSCREEN) {
            Lazy lazy = this.mPluginAODManagerLazy;
            if (((PluginAODManager) lazy.get()).mDozeParameters.mControlScreenOffAnimation) {
                Log.i("NotificationPanelView", "showAodUi: setIsDozing set true");
                ((PluginAODManager) lazy.get()).setIsDozing(true, false);
            }
        }
        setExpandedFraction(1.0f);
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
        if (!this.mCommandQueue.panelsEnabled()) {
            Log.d("NotificationPanelView", "startInputFocusTransfer: failed by !panelsEnabled()");
            return;
        }
        SecNotificationPanelViewController secNotificationPanelViewController = this.mSecNotificationPanelViewController;
        if (secNotificationPanelViewController != null && secNotificationPanelViewController.isStatusBarWindowViewTouched()) {
            Log.d("NotificationPanelView", "startInputFocusTransfer: failed by StatusBarWindowView Touched");
            return;
        }
        if (!isFullyCollapsed()) {
            Log.d("NotificationPanelView", "startInputFocusTransfer: failed by !isFullyCollapsed()");
            return;
        }
        Log.d("NotificationPanelView", "startInputFocusTransfer");
        this.mExpectingSynthesizedDown = true;
        onTrackingStarted();
        updatePanelExpanded();
        if (!SecPanelSplitHelper.isEnabled() || (secPanelSplitHelper = this.mPanelSplitHelper) == null || (motionEvent = this.mDownEventFromOverView) == null) {
            return;
        }
        secPanelSplitHelper.shouldQSDown(motionEvent);
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
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
            notificationStackScrollLayoutController.getClass();
            int i = SceneContainerFlag.$r8$clinit;
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
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
        FaceWidgetContainerWrapper faceWidgetContainerWrapper;
        KeyguardSecVisibilityHelper keyguardSecVisibilityHelper;
        if (this.mIsOcclusionTransitionRunning || this.mSecAffordanceHelper.isShortcutPreviewSwipingInProgress) {
            return;
        }
        float faceWidgetAlpha = getFaceWidgetAlpha();
        LockscreenShadeTransitionController lockscreenShadeTransitionController = this.mLockscreenShadeTransitionController;
        if (lockscreenShadeTransitionController.getFractionToShade() <= 0.0f) {
            if (this.mKeyguardTouchAnimator.isViRunning() || faceWidgetAlpha < 0.0f || (keyguardSecVisibilityHelper = (faceWidgetContainerWrapper = this.mKeyguardStatusBase).mKeyguardSecVisibilityHelper) == null || keyguardSecVisibilityHelper.isVisibilityAnimating) {
                return;
            }
            View view = faceWidgetContainerWrapper.mFaceWidgetContainer;
            if (view != null) {
                view.setAlpha(faceWidgetAlpha);
            }
            KeyguardStatusViewAlphaChangeControllerWrapper keyguardStatusViewAlphaChangeControllerWrapper = faceWidgetContainerWrapper.mKeyguardStatusViewAlphaChangeControllerWrapper;
            if (keyguardStatusViewAlphaChangeControllerWrapper != null) {
                keyguardStatusViewAlphaChangeControllerWrapper.updateAlpha(faceWidgetAlpha);
                return;
            }
            return;
        }
        FaceWidgetContainerWrapper faceWidgetContainerWrapper2 = this.mKeyguardStatusBase;
        float fractionToShade = lockscreenShadeTransitionController.getFractionToShade();
        float fInterpolate = NotificationUtils.interpolate(1.0f, 0.0f, ((double) fractionToShade) > 0.5d ? 1.0f : fractionToShade * 2.0f);
        KeyguardSecVisibilityHelper keyguardSecVisibilityHelper2 = faceWidgetContainerWrapper2.mKeyguardSecVisibilityHelper;
        if (keyguardSecVisibilityHelper2 == null || keyguardSecVisibilityHelper2.isVisibilityAnimating) {
            return;
        }
        View view2 = faceWidgetContainerWrapper2.mFaceWidgetContainer;
        if (view2 != null) {
            view2.setAlpha(fInterpolate);
        }
        KeyguardStatusViewAlphaChangeControllerWrapper keyguardStatusViewAlphaChangeControllerWrapper2 = faceWidgetContainerWrapper2.mKeyguardStatusViewAlphaChangeControllerWrapper;
        if (keyguardStatusViewAlphaChangeControllerWrapper2 != null) {
            keyguardStatusViewAlphaChangeControllerWrapper2.updateAlpha(fInterpolate);
        }
    }

    public final void updateDozingVisibilities(boolean z) throws Resources.NotFoundException {
        ((KeyguardRepositoryImpl) this.mKeyguardInteractor.repository)._animateBottomAreaDozingTransitions.updateState(null, Boolean.valueOf(z));
        this.mKeyguardSecBottomArea.setVisibility(0);
        PluginFaceWidgetManager pluginFaceWidgetManager = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
        if (pluginFaceWidgetManager == null) {
            Log.e("NotificationPanelView", "Failed to get PluginFaceWidgetManager");
        } else {
            pluginFaceWidgetManager.updateNowBarVisibility(this.mBarState == 1 ? 0 : 8);
        }
        if (!this.mDozing && z) {
            this.mKeyguardStatusBarViewController.animateKeyguardStatusBarIn();
        }
        if (CscRune.KEYGUARD_DCM_LIVE_UX) {
            this.mMascotViewContainer.setMascotViewVisible(this.mDozing ? 8 : 0);
        }
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final void updateDynamicLockData(String str) {
        this.mKeyguardSecBottomAreaViewController.updateBottomView();
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
                if (expandableNotificationRow.mEntry.getAttachedNotifChildren() != null) {
                    ArrayList arrayList = (ArrayList) expandableNotificationRow.mEntry.getAttachedNotifChildren();
                    int size = arrayList.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj = arrayList.get(i2);
                        i2++;
                        NotificationEntry notificationEntry = (NotificationEntry) obj;
                        if (!notificationEntry.mIsReaded) {
                            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder(), expandableNotificationRow.mLoggingKey, " isReaded = true", "NotificationPanelView");
                            notificationEntry.mIsReaded = true;
                        }
                    }
                } else if (!expandableNotificationRow.mEntry.mIsReaded) {
                    ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder(), expandableNotificationRow.mLoggingKey, " isReaded = true", "NotificationPanelView");
                    expandableNotificationRow.mEntry.mIsReaded = true;
                }
                expandableNotificationRow.mEntry.mWillBeHUN = false;
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
        EmptyShadeView emptyShadeView;
        boolean zIsTracking = isTracking();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        if (zIsTracking) {
            this.mVelocityTracker.computeCurrentVelocity(1000);
            float yVelocity = this.mVelocityTracker.getYVelocity();
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            notificationStackScrollLayout.getClass();
            int i2 = SceneContainerFlag.$r8$clinit;
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            notificationStackScrollLayout.mAmbientState.mExpandingVelocity = yVelocity;
        }
        if (this.mKeyguardBypassController.getBypassEnabled() && isKeyguardShowing$1()) {
            f = getMaxPanelHeight();
        }
        if (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE) {
            int i3 = SceneContainerFlag.$r8$clinit;
            notificationStackScrollLayoutController.getClass();
            RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
            notificationStackScrollLayoutController.mView.setExpandedHeight(f);
        } else if (!this.mKeyguardTouchAnimator.isViRunning()) {
            notificationStackScrollLayoutController.getClass();
            int i4 = SceneContainerFlag.$r8$clinit;
            RefactorFlagUtils refactorFlagUtils3 = RefactorFlagUtils.INSTANCE;
            notificationStackScrollLayoutController.mView.setExpandedHeight(f);
        }
        updateKeyguardSecBottomAreaAlpha();
        float f2 = this.mExpandedHeight;
        notificationStackScrollLayoutController.getClass();
        int i5 = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils4 = RefactorFlagUtils.INSTANCE;
        NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout2.getClass();
        if (NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW || (emptyShadeView = notificationStackScrollLayout2.mEmptyShadeView) == null || emptyShadeView.getVisibility() == 8) {
            height = notificationStackScrollLayout2.mShelf.getHeight();
            i = notificationStackScrollLayout2.mWaterfallTopInset;
        } else {
            height = notificationStackScrollLayout2.mShelf.getHeight();
            i = notificationStackScrollLayout2.mWaterfallTopInset;
        }
        boolean z = f2 < ((float) (height + i));
        if (this.mBarState == 0 && this.mExpandedFraction > 0.0f) {
            z = false;
        }
        if (z && isKeyguardShowing$1()) {
            z = false;
        }
        boolean z2 = (z && this.mBarState == 2) ? false : z;
        if (z2 != this.mShowIconsWhenExpanded) {
            this.mShowIconsWhenExpanded = z2;
            this.mCommandQueue.recomputeDisableFlags(this.mDisplayId, true);
        }
    }

    public final void updateExpandedHeightToMaxHeight() {
        float maxPanelHeight = getMaxPanelHeight();
        if (isFullyCollapsed() || this.mTouchDownOnHeadsUpPinnded || maxPanelHeight == this.mExpandedHeight) {
            return;
        }
        if ((isTracking() || this.mHeadsUpTouchHelper.mTrackingHeadsUp) && !this.mBlockingExpansionForCurrentTouch) {
            QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
            if (!quickSettingsControllerImpl.mConflictingExpansionGesture || !quickSettingsControllerImpl.getExpanded()) {
                return;
            }
        }
        if (this.mHeightAnimator != null && !this.mIsSpringBackAnimation) {
            this.mPanelUpdateWhenAnimatorEnds = true;
        } else if (isClosing()) {
            Log.d("NotificationPanelView", "updateExpandedHeightToMaxHeight isClosing return");
        } else {
            setExpandedHeight(maxPanelHeight);
        }
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void updateExpansionAndVisibility() {
        boolean z;
        boolean z2;
        StringBuilder sb;
        int i = SceneContainerFlag.$r8$clinit;
        float f = this.mExpandedFraction;
        boolean zIsExpanded = isExpanded();
        boolean zIsTracking = isTracking();
        ShadeExpansionStateManager shadeExpansionStateManager = this.mShadeExpansionStateManager;
        shadeExpansionStateManager.getClass();
        if (Float.isNaN(f)) {
            throw new IllegalArgumentException("fraction cannot be NaN");
        }
        int i2 = shadeExpansionStateManager.state;
        shadeExpansionStateManager.fraction = f;
        shadeExpansionStateManager.expanded = zIsExpanded;
        shadeExpansionStateManager.tracking = zIsTracking;
        if (zIsExpanded) {
            if (i2 == 0) {
                shadeExpansionStateManager.updateStateInternal(1);
            }
            z = f >= 1.0f;
            z2 = false;
        } else {
            z = false;
            z2 = true;
        }
        if (z && !zIsTracking) {
            shadeExpansionStateManager.updateStateInternal(2);
        } else if (z2 && !zIsTracking && shadeExpansionStateManager.state != 0) {
            shadeExpansionStateManager.updateStateInternal(0);
        }
        ShadeExpansionStateManagerKt.panelStateToString(i2);
        ShadeExpansionStateManagerKt.panelStateToString(shadeExpansionStateManager.state);
        if (i2 != shadeExpansionStateManager.state) {
            MediaSessions$H$$ExternalSyntheticOutline0.m("onPanelExpansionChanged: ", ShadeExpansionStateManagerKt.panelStateToString(i2), " -> ", ShadeExpansionStateManagerKt.panelStateToString(shadeExpansionStateManager.state), ShadeExpansionStateManagerKt.TAG);
        }
        if (Trace.isTagEnabled(4096L)) {
            TrackTracer.Companion.getClass();
            TrackTracer.Companion.instantForGroup((int) (100 * f), "shade", "panel_expansion");
            shadeExpansionStateManager.stateLogger.log(ShadeExpansionStateManagerKt.panelStateToString(shadeExpansionStateManager.state));
        }
        ShadeExpansionChangeEvent shadeExpansionChangeEvent = new ShadeExpansionChangeEvent(f, zIsExpanded, zIsTracking);
        ShadeExpansionChangeEvent shadeExpansionChangeEvent2 = shadeExpansionStateManager.oldFraction == f ? null : shadeExpansionChangeEvent;
        if (shadeExpansionChangeEvent2 != null) {
            shadeExpansionStateManager.oldFraction = f;
            Log.d(ShadeExpansionStateManagerKt.TAG, "onPanelExpansionChanged: " + shadeExpansionChangeEvent2);
        }
        Iterator it = shadeExpansionStateManager.expansionListeners.iterator();
        while (it.hasNext()) {
            ((ShadeExpansionListener) it.next()).onPanelExpansionChanged(shadeExpansionChangeEvent);
        }
        updateVisibility();
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
        Region regionCalculateTouchableRegion = this.mShadeTouchableRegionManager.calculateTouchableRegion();
        Rect bounds = (!isFullyCollapsed() || regionCalculateTouchableRegion == null) ? null : regionCalculateTouchableRegion.getBounds();
        if (bounds == null) {
            bounds = EMPTY_RECT;
        }
        this.mView.setSystemGestureExclusionRects(bounds.isEmpty() ? Collections.EMPTY_LIST : Collections.singletonList(bounds));
    }

    public final void updateKeyguardSecBottomAreaAlpha() {
        if (this.mKeyguardSecBottomArea == null || this.mSecAffordanceHelper.isShortcutPreviewSwipingInProgress || this.mKeyguardStateController.mKeyguardGoingAway) {
            return;
        }
        if (this.mBarState != 1 || this.mKeyguardTouchAnimator.isViRunning() || this.mIsOcclusionTransitionRunning) {
            return;
        }
        float fConstrainedMap = MathUtils.constrainedMap(0.0f, 1.0f, 0.95f, 1.0f, this.mExpandedFraction);
        LockscreenShadeTransitionController lockscreenShadeTransitionController = this.mLockscreenShadeTransitionController;
        float fMin = Math.min(fConstrainedMap, 1.0f - lockscreenShadeTransitionController.getFractionToShade());
        KeyguardSecAffordanceHelper keyguardSecAffordanceHelper = this.mSecAffordanceHelper;
        if (!keyguardSecAffordanceHelper.mPreviewAnimationStarted) {
            KeyguardSecAffordanceView keyguardSecAffordanceView = keyguardSecAffordanceHelper.mLeftIcon;
            keyguardSecAffordanceView.getClass();
            if (keyguardSecAffordanceView.mShortcutLaunchAnimator == null) {
                KeyguardSecAffordanceView keyguardSecAffordanceView2 = keyguardSecAffordanceHelper.mRightIcon;
                keyguardSecAffordanceView2.getClass();
                if (keyguardSecAffordanceView2.mShortcutLaunchAnimator == null) {
                    KeyguardSecAffordanceHelper keyguardSecAffordanceHelper2 = this.mSecAffordanceHelper;
                    KeyguardSecAffordanceView leftView = this.mKeyguardSecBottomArea.getLeftView();
                    keyguardSecAffordanceHelper2.getClass();
                    leftView.setImageAlpha(Math.min(1.0f, fMin), false);
                    leftView.setImageScale(1.0f, false);
                    KeyguardSecAffordanceHelper keyguardSecAffordanceHelper3 = this.mSecAffordanceHelper;
                    KeyguardSecAffordanceView rightView = this.mKeyguardSecBottomArea.getRightView();
                    keyguardSecAffordanceHelper3.getClass();
                    rightView.setImageAlpha(Math.min(1.0f, fMin), false);
                    rightView.setImageScale(1.0f, false);
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
        this.mKeyguardSecBottomAreaViewController.setAffordanceAlpha(faceWidgetAlpha);
        SecLockIconViewController secLockIconViewController = this.mLockIconViewController;
        if (!((KeyguardEditModeControllerImpl) secLockIconViewController.mKeyguardEditModeController).getVIRunning()) {
            secLockIconViewController.mView.setAlpha(faceWidgetAlpha);
        }
        if (LsRune.SECURITY_CONTINUITY_LOCKSCREEN_VI) {
            this.mContinuityLockScreenContainer.setAlpha(faceWidgetAlpha);
        }
        boolean z = lockscreenShadeTransitionController.getFractionToShade() > 0.5f;
        if (this.mPanelExpandForBiometric != z) {
            this.mPanelExpandForBiometric = z;
            this.mUpdateMonitor.setPanelExpandingStarted(z);
        }
        View view2 = this.mPluginLockStarContainer;
        if (view2 != null) {
            view2.setAlpha(faceWidgetAlpha);
        }
    }

    public final void updateKeyguardStatusViewAlignment() {
        final boolean z = true;
        this.mKeyguardUnfoldTransition.ifPresent(new Consumer() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda49
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                ((KeyguardUnfoldTransition) obj).statusViewCentered = z;
            }
        });
    }

    public final void updateLockStarContainer() {
        KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(new StringBuilder("updateLockStarContainer: "), this.mLockStarEnabled, "NotificationPanelView");
        if (!this.mLockStarEnabled) {
            View view = this.mPluginLockStarContainer;
            if (view != null) {
                view.setVisibility(8);
                this.mPluginLockStarContainer = null;
                return;
            }
            return;
        }
        this.mPluginLockStarContainer = (View) ((PluginLockStarManager) Dependency.sDependency.getDependencyInner(PluginLockStarManager.class)).get("lockstarContainer");
        Log.i("NotificationPanelView", "updateLockStarContainer: [" + this.mPluginLockStarContainer + "]");
        if (this.mPluginLockStarContainer != null) {
            TooltipPopup$$ExternalSyntheticOutline0.m(this.mBarState, "NotificationPanelView", new StringBuilder("updateLockStarContainer: mBarState="));
            this.mPluginLockStarContainer.setVisibility(isOnKeyguard() ? 0 : 8);
        }
    }

    public final void updateMaxHeadsUpTranslation() {
        int height = this.mView.getHeight();
        int i = this.mNavigationBarBottomHeight;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        notificationStackScrollLayoutController.getClass();
        int i2 = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.getClass();
        notificationStackScrollLayout.mAmbientState.mMaxHeadsUpTranslation = height - i;
        int i3 = NotificationsHunSharedAnimationValues.$r8$clinit;
        StackScrollAlgorithm stackScrollAlgorithm = notificationStackScrollLayout.mStackScrollAlgorithm;
        stackScrollAlgorithm.getClass();
        stackScrollAlgorithm.mHeadsUpAppearHeightBottom = height;
        StackStateAnimator stackStateAnimator = notificationStackScrollLayout.mStateAnimator;
        stackStateAnimator.getClass();
        stackStateAnimator.mHeadsUpAppearHeightBottom = height;
        StackStateAnimator stackStateAnimator2 = notificationStackScrollLayout.mStateAnimator;
        int i4 = notificationStackScrollLayout.mAmbientState.mStackTopMargin;
        stackStateAnimator2.getClass();
        stackStateAnimator2.mStackTopMargin = i4;
        notificationStackScrollLayout.requestChildrenUpdate();
    }

    public final void updateNsslMargin() {
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        NotificationPanelView notificationPanelView = this.mView;
        int navBarHeight = secQSPanelResourcePicker.getNavBarHeight(notificationPanelView.getContext());
        SecQSPanelResourcePicker secQSPanelResourcePicker2 = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        this.mSharedNotificationContainerInteractor._naviBarHeight.updateState(null, Integer.valueOf(secQSPanelResourcePicker2.resourcePickHelper.getTargetPicker().getNotificationBottomPadding(notificationPanelView.getContext()) + navBarHeight));
    }

    public final void updateNsslWidth() {
        this.mSharedNotificationContainerInteractor._nsslWidth.updateState(null, Integer.valueOf(((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getPanelWidth(this.mView.getContext())));
    }

    public final void updatePanelExpanded() {
        PanelPopOverManager panelPopOverManager;
        ViewTreeObserver viewTreeObserver;
        ViewTreeObserver viewTreeObserver2;
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
        if (QpRune.QUICK_PANEL_CODE_FOR_POP_OVER && (panelPopOverManager = this.mPanelPopOverManager) != null && z && panelPopOverManager.getNeedToPopOver() && !panelPopOverManager.isPopOverAreaListenerAdded && panelPopOverManager.currentPanelState != 0) {
            Log.d("PanelPopOverManager", "addPopoverAreaListener");
            NotificationPanelView notificationPanelView = panelPopOverManager.mView;
            PanelPopOverManager$popOverInsetsListener$1 panelPopOverManager$popOverInsetsListener$1 = panelPopOverManager.popOverInsetsListener;
            if (notificationPanelView != null && (viewTreeObserver2 = notificationPanelView.getViewTreeObserver()) != null) {
                viewTreeObserver2.removeOnComputeInternalInsetsListener(panelPopOverManager$popOverInsetsListener$1);
            }
            NotificationPanelView notificationPanelView2 = panelPopOverManager.mView;
            if (notificationPanelView2 != null && (viewTreeObserver = notificationPanelView2.getViewTreeObserver()) != null) {
                viewTreeObserver.addOnComputeInternalInsetsListener(panelPopOverManager$popOverInsetsListener$1);
            }
            panelPopOverManager.isPopOverAreaListenerAdded = true;
            NotificationPanelView notificationPanelView3 = panelPopOverManager.mView;
            panelPopOverManager.qsPanelView = notificationPanelView3 != null ? notificationPanelView3.findViewById(R.id.quick_settings_panel) : null;
            NotificationPanelView notificationPanelView4 = panelPopOverManager.mView;
            panelPopOverManager.qqsPanelView = notificationPanelView4 != null ? notificationPanelView4.findViewById(R.id.qs_container) : null;
            NotificationPanelView notificationPanelView5 = panelPopOverManager.mView;
            panelPopOverManager.customizerView = notificationPanelView5 != null ? notificationPanelView5.findViewById(R.id.main_content) : null;
            NotificationPanelView notificationPanelView6 = panelPopOverManager.mView;
            panelPopOverManager.detailView = notificationPanelView6 != null ? notificationPanelView6.findViewById(R.id.qs_detail_container) : null;
            NotificationPanelView notificationPanelView7 = panelPopOverManager.mView;
            panelPopOverManager.blurView = notificationPanelView7 != null ? notificationPanelView7.findViewById(R.id.qs_new_blur_view) : null;
            NotificationPanelView notificationPanelView8 = panelPopOverManager.mView;
            panelPopOverManager.blur = notificationPanelView8 != null ? (SecQSNewBlurView) notificationPanelView8.findViewById(R.id.qs_new_blur) : null;
            NotificationPanelView notificationPanelView9 = panelPopOverManager.mView;
            panelPopOverManager.largeShadowView = notificationPanelView9 != null ? (SecQSBlurShadowView) notificationPanelView9.findViewById(R.id.qs_large_shadow_view) : null;
            NotificationPanelView notificationPanelView10 = panelPopOverManager.mView;
            panelPopOverManager.smallShadowView = notificationPanelView10 != null ? (SecQSBlurShadowView) notificationPanelView10.findViewById(R.id.qs_small_shadow_view) : null;
            NotificationStackScrollLayout notificationStackScrollLayout = panelPopOverManager.notificationStackScrollLayoutController.mView;
            panelPopOverManager.notificationShelf = notificationStackScrollLayout.mShelf;
            panelPopOverManager.nssl = notificationStackScrollLayout;
            panelPopOverManager.largeShadowViewDistanceFromBlur = panelPopOverManager.context.getResources().getDimensionPixelSize(R.dimen.qs_pop_over_large_shadow_distance_from_blur);
            panelPopOverManager.smallShadowViewDistanceFromBlur = panelPopOverManager.context.getResources().getDimensionPixelSize(R.dimen.qs_pop_over_small_shadow_distance_from_blur);
            View view = panelPopOverManager.blurView;
            if (view != null) {
                view.setVisibility(0);
            }
            SecQSBlurShadowView secQSBlurShadowView = panelPopOverManager.largeShadowView;
            if (secQSBlurShadowView != null) {
                secQSBlurShadowView.setVisibility(0);
            }
            SecQSBlurShadowView secQSBlurShadowView2 = panelPopOverManager.smallShadowView;
            if (secQSBlurShadowView2 != null) {
                secQSBlurShadowView2.setVisibility(0);
            }
        }
        if (isKeyguardShowing$1()) {
            return;
        }
        KeyguardTouchAnimator keyguardTouchAnimator = this.mKeyguardTouchAnimator;
        if (keyguardTouchAnimator.notiScale > 1.0f) {
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m(keyguardTouchAnimator.notiScale, "NotificationPanelView", new StringBuilder("current noti scale : "));
        }
    }

    @Override // com.android.systemui.shade.ShadeSurface
    public final void updateResources$1() {
        try {
            Trace.beginSection("NSSLC#updateResources");
            ((SplitShadeStateControllerImpl) this.mSplitShadeStateController).shouldUseSplitNotificationShade();
            this.mQsController.updateResources$1();
            this.mNotificationsQSContainerController.updateResources$1();
            updateKeyguardStatusViewAlignment();
            this.mKeyguardMediaController.getClass();
            this.mSplitShadeFullTransitionDistance = this.mResources.getDimensionPixelSize(R.dimen.split_shade_full_transition_distance);
            updateNsslMargin();
        } finally {
            Trace.endSection();
        }
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void updateSystemUiStateFlags() {
        boolean zIsEnabled = ShadeWindowGoesAround.isEnabled();
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        boolean z = false;
        int i = 0;
        z = false;
        if (!zIsEnabled) {
            SysUiState flag = this.mSysUiState.setFlag(1073741824L, isPanelExpanded() && !isCollapsing()).setFlag(4L, isFullyExpanded() && !quickSettingsControllerImpl.getExpanded());
            if (isFullyExpanded() && quickSettingsControllerImpl.getExpanded()) {
                z = true;
            }
            ((SysUiStateImpl) flag.setFlag(2048L, z)).commitUpdate();
            return;
        }
        int iIntValue = ShadeWindowGoesAround.isEnabled() ? ((Integer) ((ShadeDisplaysRepositoryImpl) ((ShadeDisplaysRepository) this.mShadeDisplaysRepository.get())).pendingDisplayId.$$delegate_0.getValue()).intValue() : 0;
        StateChange stateChange = new StateChange();
        stateChange.setFlag(1073741824L, isPanelExpanded() && !isCollapsing());
        stateChange.setFlag(4L, isFullyExpanded() && !quickSettingsControllerImpl.getExpanded());
        stateChange.setFlag(2048L, isFullyExpanded() && quickSettingsControllerImpl.getExpanded());
        SysUIStateDisplaysInteractor sysUIStateDisplaysInteractor = this.mSysUIStateDisplaysInteractor;
        Iterable iterable = (Iterable) ((DisplayRepositoryImpl) sysUIStateDisplaysInteractor.displayRepository).displayRepositoryFromLib.getDisplays().getValue();
        ArrayList arrayList = new ArrayList();
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            SysUiState sysUiState = (SysUiState) sysUIStateDisplaysInteractor.sysUIStateRepository.get(((Display) it.next()).getDisplayId());
            if (sysUiState != null) {
                arrayList.add(sysUiState);
            }
        }
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            SysUiState sysUiState2 = (SysUiState) obj;
            long j = 0;
            if (sysUiState2.getDisplayId() == iIntValue) {
                long j2 = stateChange.flagsToSet | stateChange.flagsToClear;
                while (j2 != j) {
                    long j3 = (-j2) & j2;
                    long j4 = j;
                    sysUiState2.setFlag(j3, (stateChange.flagsToSet & j3) != j4);
                    j2 -= j3;
                    j = j4;
                }
            } else {
                long j5 = stateChange.flagsToSet | stateChange.flagsToClear;
                while (j5 != 0) {
                    long j6 = (-j5) & j5;
                    sysUiState2.setFlag(j6, false);
                    j5 -= j6;
                }
            }
        }
        int size2 = arrayList.size();
        while (i < size2) {
            Object obj2 = arrayList.get(i);
            i++;
            ((SysUiState) obj2).commitUpdate();
        }
    }

    @Override // com.android.systemui.shade.ShadeViewController
    public final void updateTouchableRegion() {
        NotificationPanelView notificationPanelView = this.mView;
        notificationPanelView.requestLayout();
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController;
        NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
        notificationShadeWindowState.forceWindowCollapsed = true;
        notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
        notificationPanelView.post(new NotificationPanelViewController$$ExternalSyntheticLambda18(this, 14));
    }

    public final void updateVisibility() {
        int i = 4;
        this.mView.setVisibility(shouldPanelBeVisible() ? 0 : 4);
        this.mNotificationStackScrollLayoutController.updateVisibility(shouldPanelBeVisible());
        PluginFaceWidgetManager pluginFaceWidgetManager = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
        if (pluginFaceWidgetManager == null) {
            Log.e("NotificationPanelView", "Failed to get PluginFaceWidgetManager");
            return;
        }
        if (this.mBarState == 1 && shouldPanelBeVisible()) {
            i = 0;
        }
        pluginFaceWidgetManager.updateNowBarVisibility(i);
    }

    public final void fling(float f, float f2, boolean z) throws Resources.NotFoundException {
        float maxPanelTransitionDistance = z ? getMaxPanelTransitionDistance() : 0.0f;
        if (!z) {
            setClosing(true);
        }
        flingToHeight(f, z, maxPanelTransitionDistance, f2, false);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void resetViews(boolean z, boolean z2) {
        this.mIsLaunchTransitionFinished = false;
        KeyguardSecAffordanceHelper keyguardSecAffordanceHelper = this.mSecAffordanceHelper;
        if (keyguardSecAffordanceHelper != null) {
            keyguardSecAffordanceHelper.reset(false);
            this.mLastCameraLaunchSource = 3;
        }
        KeyguardSecAffordanceHelper keyguardSecAffordanceHelper2 = this.mSecAffordanceHelper;
        if (keyguardSecAffordanceHelper2 != null && keyguardSecAffordanceHelper2.isShortcutPreviewSwipingInProgress) {
            keyguardSecAffordanceHelper2.reset(false);
        }
        this.mGutsManager.closeAndSaveGuts(true, true, true, true);
        if (this.mBarState != 1) {
            if (!z || isFullyCollapsed()) {
                closeQsIfPossible();
            } else {
                animateCollapseQs(true);
            }
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        notificationStackScrollLayoutController.mView.setOverScrollAmount(0.0f, true, z, !z);
        notificationStackScrollLayoutController.mView.resetScrollPosition();
        KeyguardTouchAnimator keyguardTouchAnimator = this.mKeyguardTouchAnimator;
        keyguardTouchAnimator.setIntercept(false);
        keyguardTouchAnimator.reset(z2);
        KeyguardSecAffordanceHelper keyguardSecAffordanceHelper3 = this.mSecAffordanceHelper;
        if (keyguardSecAffordanceHelper3 != null) {
            this.mHintAnimationRunning = false;
            keyguardSecAffordanceHelper3.isShortcutPreviewSwipingInProgress = false;
        }
    }

    public final void collapse(float f, boolean z) throws Resources.NotFoundException {
        if (canBeCollapsed()) {
            QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
            if (quickSettingsControllerImpl.getExpanded()) {
                quickSettingsControllerImpl.setExpandImmediate(true);
                setShowShelfOnly(true);
            }
            if (canBeCollapsed()) {
                cancelHeightAnimator();
                notifyExpandingStarted();
                if (SecPanelSplitHelper.isEnabled()) {
                    Log.d("NotificationPanelView", "collapse forced set isSliding is false");
                    QsAnimatorState.isSliding = false;
                }
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
}
