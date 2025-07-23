package com.android.systemui.shade;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
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
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.common.buffer.RingBuffer;
import com.android.systemui.common.buffer.RingBuffer$iterator$1;
import com.android.systemui.common.domain.interactor.SysUIStateDisplaysInteractor;
import com.android.systemui.communal.ui.viewmodel.CommunalTransitionViewModel;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl;
import com.android.systemui.doze.DozeLog;
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
import com.android.systemui.qs.QSFragmentLegacy;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.SecQSImpl;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.animator.QsAnimatorState;
import com.android.systemui.qs.animator.SecQSImplAnimatorManager;
import com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor;
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
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda29;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.DcmMascotViewContainer;
import com.android.systemui.statusbar.phone.DcmMascotViewContainer$broadcastReceiver$1;
import com.android.systemui.statusbar.phone.DcmMascotViewContainer$sendUnreadCountBroadcast$1;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.statusbar.phone.IndicatorTouchHandler;
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
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public CentralSurfacesImpl$$ExternalSyntheticLambda29 mHideExpandedRunnable;
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
        public final void onAdditionalTapRequired() {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                notificationPanelView.animate().alpha(f).setDuration(j).setInterpolator(Interpolators.ACCELERATE).setListener(animatorListener).withEndAction(new Runnable() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda52
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.shade.NotificationPanelViewController$9, reason: invalid class name */
    public class AnonymousClass9 {
        public AnonymousClass9() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        float displayWidth = DeviceState.getDisplayWidth(((NotificationPanelView) tabletHorizontalPanelPositionHelper.viewSupplier.get()).getContext());
                        float width = tabletHorizontalPanelPositionHelper.notificationStackScrollLayoutController.getWidth();
                        float f = 2;
                        tabletHorizontalPanelPositionHelper.panelCenter = displayWidth / f;
                        tabletHorizontalPanelPositionHelper.controllerCenter = width / f;
                        float asInt = tabletHorizontalPanelPositionHelper.positionMinSideMarginSupplier.getAsInt() + tabletHorizontalPanelPositionHelper.controllerCenter;
                        tabletHorizontalPanelPositionHelper.leftMost = asInt;
                        float f2 = displayWidth - asInt;
                        tabletHorizontalPanelPositionHelper.rightMost = f2;
                        tabletHorizontalPanelPositionHelper.setHorizontalPanelTranslation((f2 - tabletHorizontalPanelPositionHelper.panelCenter) * tabletHorizontalPanelPositionHelper.posRatio, true);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            NotificationPanelViewController.m2929$$Nest$mstartExpandMotion(NotificationPanelViewController.this, f, f2, true, f3);
        }

        private HeadsUpNotificationViewControllerImpl() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class KeyguardAffordanceHelperCallback implements KeyguardSecAffordanceHelper.Callback {
        public /* synthetic */ KeyguardAffordanceHelperCallback(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        private KeyguardAffordanceHelperCallback() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface NewNotifReadListener {
        void onNewNotificationRead();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NsslHeightChangedListener {
        public /* synthetic */ NsslHeightChangedListener(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        private NsslHeightChangedListener() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ShadeAttachStateChangeListener implements View.OnAttachStateChangeListener {
        public /* synthetic */ ShadeAttachStateChangeListener(NotificationPanelViewController notificationPanelViewController, int i) {
            this();
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ShadeFoldAnimatorImpl implements ShadeFoldAnimator {
        public ShadeFoldAnimatorImpl() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            notificationPanelViewController3.mRecomputedMaxCountCallStack = "onLayoutChange";
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController3.mNotificationStackScrollLayoutController;
            boolean z = notificationStackScrollLayoutController.getWidth() == ((float) NotificationPanelViewController.this.mView.getWidth());
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
            int i9 = quickSettingsControllerImpl2.mMaxExpansionHeight;
            if (quickSettingsControllerImpl2.isQsFragmentCreated()) {
                quickSettingsControllerImpl2.updateMinHeight();
                int desiredHeight = quickSettingsControllerImpl2.mQs.getDesiredHeight();
                quickSettingsControllerImpl2.mMaxExpansionHeight = desiredHeight;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = quickSettingsControllerImpl2.mNotificationStackScrollLayoutController;
                notificationStackScrollLayoutController2.getClass();
                int i10 = SceneContainerFlag.$r8$clinit;
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
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
                final int i11 = quickSettingsControllerImpl3.mMaxExpansionHeight;
                if (i11 != i9) {
                    ValueAnimator valueAnimator = quickSettingsControllerImpl3.mSizeChangeAnimator;
                    if (valueAnimator != null) {
                        i9 = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        quickSettingsControllerImpl3.mSizeChangeAnimator.cancel();
                    }
                    ValueAnimator ofInt = ValueAnimator.ofInt(i9, i11);
                    quickSettingsControllerImpl3.mSizeChangeAnimator = ofInt;
                    ofInt.setDuration(300L);
                    quickSettingsControllerImpl3.mSizeChangeAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                    quickSettingsControllerImpl3.mSizeChangeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda36
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            QuickSettingsControllerImpl quickSettingsControllerImpl4 = QuickSettingsControllerImpl.this;
                            int i12 = i11;
                            NotificationPanelViewController$$ExternalSyntheticLambda0 notificationPanelViewController$$ExternalSyntheticLambda02 = quickSettingsControllerImpl4.mExpansionHeightSetToMaxListener;
                            if (notificationPanelViewController$$ExternalSyntheticLambda02 != null) {
                                notificationPanelViewController$$ExternalSyntheticLambda02.onExpansionHeightSetToMax(true);
                            }
                            ValueAnimator valueAnimator3 = quickSettingsControllerImpl4.mSizeChangeAnimator;
                            if (valueAnimator3 != null) {
                                i12 = ((Integer) valueAnimator3.getAnimatedValue()).intValue();
                            } else {
                                RecordingInputConnection$$ExternalSyntheticOutline0.m(i12, "animator is null. So force set height as ", "QuickSettingsController");
                            }
                            quickSettingsControllerImpl4.mQs.setHeightOverride(i12);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        public final void onStateChanged(int i) {
            onStateChanged(i, false);
        }

        private StatusBarStateListener() {
        }

        /* JADX WARN: Removed duplicated region for block: B:48:0x01c0  */
        /* JADX WARN: Removed duplicated region for block: B:70:0x020a  */
        /* JADX WARN: Removed duplicated region for block: B:77:0x0219  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onStateChanged(int r20, boolean r21) {
            /*
                Method dump skipped, instructions count: 608
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.NotificationPanelViewController.StatusBarStateListener.onStateChanged(int, boolean):void");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TouchHandler implements View.OnTouchListener, Gefingerpoken {
        public long mLastTouchDownTime = -1;

        public TouchHandler() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:182:0x0413  */
        /* JADX WARN: Removed duplicated region for block: B:187:0x0452  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean handleTouch$1(android.view.MotionEvent r13) {
            /*
                Method dump skipped, instructions count: 1134
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.NotificationPanelViewController.TouchHandler.handleTouch$1(android.view.MotionEvent):boolean");
        }

        /* JADX WARN: Code restructure failed: missing block: B:290:0x0586, code lost:
        
            if (r5.mUpdateMonitor.getUserHasTrust(r5.mSelectedUserInteractor.getSelectedUserId()) != false) goto L343;
         */
        /* JADX WARN: Code restructure failed: missing block: B:302:0x05f1, code lost:
        
            if (r10 < (r12.getHeight() + r14)) goto L369;
         */
        /* JADX WARN: Code restructure failed: missing block: B:519:0x05bc, code lost:
        
            if (r10 < (r14 + r12.getHeight())) goto L369;
         */
        /* JADX WARN: Removed duplicated region for block: B:152:0x0304  */
        /* JADX WARN: Removed duplicated region for block: B:164:0x0330  */
        /* JADX WARN: Removed duplicated region for block: B:171:0x0367  */
        /* JADX WARN: Removed duplicated region for block: B:319:0x06a3  */
        /* JADX WARN: Removed duplicated region for block: B:325:0x06d5  */
        /* JADX WARN: Removed duplicated region for block: B:328:0x0702  */
        /* JADX WARN: Removed duplicated region for block: B:332:0x070c  */
        /* JADX WARN: Removed duplicated region for block: B:352:0x0746  */
        /* JADX WARN: Removed duplicated region for block: B:426:0x0a50  */
        /* JADX WARN: Removed duplicated region for block: B:530:0x033c  */
        /* JADX WARN: Removed duplicated region for block: B:54:0x00b7  */
        /* JADX WARN: Removed duplicated region for block: B:58:0x00c3  */
        @Override // com.android.systemui.Gefingerpoken
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean onInterceptTouchEvent(android.view.MotionEvent r19) {
            /*
                Method dump skipped, instructions count: 2757
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.NotificationPanelViewController.TouchHandler.onInterceptTouchEvent(android.view.MotionEvent):boolean");
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

        /* JADX WARN: Code restructure failed: missing block: B:224:0x0399, code lost:
        
            if (r9.mQsController.shouldQuickSettingsIntercept(r9.mDownX, r9.mDownY, 0.0f) != false) goto L244;
         */
        /* JADX WARN: Code restructure failed: missing block: B:289:0x0663, code lost:
        
            if (r0 != 6) goto L428;
         */
        /* JADX WARN: Code restructure failed: missing block: B:531:0x04ce, code lost:
        
            if (((com.android.keyguard.KeyguardUpdateMonitor) com.android.systemui.Dependency.sDependency.getDependencyInner(com.android.keyguard.KeyguardUpdateMonitor.class)).isIccBlockedPermanently() != false) goto L327;
         */
        /* JADX WARN: Removed duplicated region for block: B:131:0x0201  */
        /* JADX WARN: Removed duplicated region for block: B:135:0x0216  */
        /* JADX WARN: Removed duplicated region for block: B:162:0x02ee  */
        /* JADX WARN: Removed duplicated region for block: B:237:0x0416  */
        /* JADX WARN: Removed duplicated region for block: B:241:0x0429  */
        /* JADX WARN: Removed duplicated region for block: B:263:0x05ef  */
        /* JADX WARN: Removed duplicated region for block: B:267:0x05f9  */
        /* JADX WARN: Removed duplicated region for block: B:444:0x09ca  */
        /* JADX WARN: Removed duplicated region for block: B:558:0x055a  */
        /* JADX WARN: Removed duplicated region for block: B:57:0x00c5  */
        /* JADX WARN: Removed duplicated region for block: B:70:0x00ee  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean onTouchEvent(android.view.MotionEvent r20) {
            /*
                Method dump skipped, instructions count: 2607
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.NotificationPanelViewController.TouchHandler.onTouchEvent(android.view.MotionEvent):boolean");
        }
    }

    /* renamed from: -$$Nest$maddMovement, reason: not valid java name */
    public static void m2926$$Nest$maddMovement(NotificationPanelViewController notificationPanelViewController, MotionEvent motionEvent) {
        notificationPanelViewController.getClass();
        float rawX = motionEvent.getRawX() - motionEvent.getX();
        float rawY = motionEvent.getRawY() - motionEvent.getY();
        motionEvent.offsetLocation(rawX, rawY);
        notificationPanelViewController.mVelocityTracker.addMovement(motionEvent);
        motionEvent.offsetLocation(-rawX, -rawY);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x023d  */
    /* renamed from: -$$Nest$mendMotionEvent, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m2927$$Nest$mendMotionEvent(com.android.systemui.shade.NotificationPanelViewController r17, android.view.MotionEvent r18, float r19, float r20, boolean r21) {
        /*
            Method dump skipped, instructions count: 581
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.NotificationPanelViewController.m2927$$Nest$mendMotionEvent(com.android.systemui.shade.NotificationPanelViewController, android.view.MotionEvent, float, float, boolean):void");
    }

    /* renamed from: -$$Nest$minitDownStates, reason: not valid java name */
    public static void m2928$$Nest$minitDownStates(NotificationPanelViewController notificationPanelViewController, MotionEvent motionEvent) {
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
        boolean isFullyCollapsed = notificationPanelViewController.isFullyCollapsed();
        notificationPanelViewController.mCollapsedOnDown = isFullyCollapsed;
        QuickSettingsControllerImpl quickSettingsControllerImpl = notificationPanelViewController.mQsController;
        quickSettingsControllerImpl.mCollapsedOnDown = isFullyCollapsed;
        if (notificationPanelViewController.mNotificationStackScrollLayoutController.mView.getOwnScrollY() >= quickSettingsControllerImpl.mMinExpansionHeight - notificationPanelViewController.mQuickQsOffsetHeight) {
            notificationPanelViewController.mIsPanelCollapseOnQQS = false;
        } else {
            float f = notificationPanelViewController.mDownX;
            float f2 = notificationPanelViewController.mDownY;
            if (!quickSettingsControllerImpl.mCollapsedOnDown && quickSettingsControllerImpl.mBarState != 1 && !quickSettingsControllerImpl.getExpanded()) {
                QS qs = quickSettingsControllerImpl.mQs;
                if (qs == null) {
                    bottom = quickSettingsControllerImpl.mKeyguardStatusBar.getBottom();
                } else {
                    int i = QSComposeFragment.$r8$clinit;
                    bottom = qs.getHeader().getBottom();
                }
                if (f >= quickSettingsControllerImpl.mQsFrame.getX() && f <= quickSettingsControllerImpl.mQsFrame.getX() + quickSettingsControllerImpl.mQsFrame.getWidth() && f2 <= bottom) {
                    z = true;
                    notificationPanelViewController.mIsPanelCollapseOnQQS = z;
                }
            }
            z = false;
            notificationPanelViewController.mIsPanelCollapseOnQQS = z;
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
    public static void m2929$$Nest$mstartExpandMotion(NotificationPanelViewController notificationPanelViewController, float f, float f2, boolean z, float f3) {
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
    /* JADX WARN: Type inference failed for: r7v10, types: [com.android.systemui.shade.NotificationPanelViewController$17] */
    /* JADX WARN: Type inference failed for: r7v11, types: [com.android.systemui.shade.NotificationPanelViewController$18] */
    /* JADX WARN: Type inference failed for: r7v9, types: [com.android.systemui.shade.NotificationPanelViewController$11] */
    public NotificationPanelViewController(DcmMascotViewContainer dcmMascotViewContainer, PluginLockMediator pluginLockMediator, NotificationPanelView notificationPanelView, KeyguardTouchAnimator keyguardTouchAnimator, Lazy lazy, NotificationWakeUpCoordinator notificationWakeUpCoordinator, PulseExpansionHandler pulseExpansionHandler, DynamicPrivacyController dynamicPrivacyController, KeyguardBypassController keyguardBypassController, FalsingManager falsingManager, FalsingCollector falsingCollector, KeyguardStateController keyguardStateController, StatusBarStateController statusBarStateController, NotificationShadeWindowController notificationShadeWindowController, DozeLog dozeLog, DozeParameters dozeParameters, CommandQueue commandQueue, VibratorHelper vibratorHelper, LatencyTracker latencyTracker, AccessibilityManager accessibilityManager, int i, KeyguardUpdateMonitor keyguardUpdateMonitor, MetricsLogger metricsLogger, ShadeLogger shadeLogger, ConfigurationController configurationController, Provider provider, ShadeTouchableRegionManager shadeTouchableRegionManager, ConversationNotificationManager conversationNotificationManager, MediaHierarchyManager mediaHierarchyManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, NotificationGutsManager notificationGutsManager, NotificationsQSContainerController notificationsQSContainerController, NotificationStackScrollLayoutController notificationStackScrollLayoutController, KeyguardStatusBarViewComponent.Factory factory, LockscreenShadeTransitionController lockscreenShadeTransitionController, ScrimController scrimController, MediaDataManager mediaDataManager, NotificationShadeDepthController notificationShadeDepthController, AmbientState ambientState, SecLockIconViewController secLockIconViewController, KeyguardMediaController keyguardMediaController, TapAgainViewController tapAgainViewController, NavigationModeController navigationModeController, NavigationBarController navigationBarController, QuickSettingsControllerImpl quickSettingsControllerImpl, FragmentService fragmentService, IStatusBarService iStatusBarService, ShadeHeaderController shadeHeaderController, ScreenOffAnimationController screenOffAnimationController, LockscreenGestureLogger lockscreenGestureLogger, ShadeExpansionStateManager shadeExpansionStateManager, ShadeRepository shadeRepository, Optional<SysUIUnfoldComponent> optional, SysUiState sysUiState, SysUIStateDisplaysInteractor sysUIStateDisplaysInteractor, Provider provider2, KeyguardWallpaperController keyguardWallpaperController, WallpaperImageInjectCreator wallpaperImageInjectCreator, EmergencyButtonController.Factory factory2, KeyguardUnlockAnimationController keyguardUnlockAnimationController, KeyguardIndicationController keyguardIndicationController, NotificationListContainer notificationListContainer, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, SystemClock systemClock, KeyguardClockInteractor keyguardClockInteractor, AlternateBouncerInteractor alternateBouncerInteractor, DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel, CoroutineDispatcher coroutineDispatcher, KeyguardTransitionInteractor keyguardTransitionInteractor, DumpManager dumpManager, KeyguardTouchHandlingViewModel keyguardTouchHandlingViewModel, WallpaperFocalAreaViewModel wallpaperFocalAreaViewModel, KeyguardInteractor keyguardInteractor, ActivityStarter activityStarter, SharedNotificationContainerInteractor sharedNotificationContainerInteractor, ActiveNotificationsInteractor activeNotificationsInteractor, ShadeAnimationInteractor shadeAnimationInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, SplitShadeStateController splitShadeStateController, PowerInteractor powerInteractor, KeyguardClockPositionAlgorithm keyguardClockPositionAlgorithm, MSDLPlayer mSDLPlayer, BrightnessMirrorShowingRepository brightnessMirrorShowingRepository, BlurConfig blurConfig, Lazy lazy2, PrivacyDialogController privacyDialogController, KeyguardPunchHoleVIViewController.Factory factory3, NotificationShelfManager notificationShelfManager, KeyguardEditModeController keyguardEditModeController, KeyguardClickController keyguardClickController, PluginLockData pluginLockData, Lazy lazy3, Lazy lazy4, LockscreenNotificationManager lockscreenNotificationManager, LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController, Lazy lazy5, QsStatusEventLog qsStatusEventLog, SelectedUserInteractor selectedUserInteractor, SecQuickSettingsAffordanceInteractor secQuickSettingsAffordanceInteractor, SecQSPanelComposeAdapter secQSPanelComposeAdapter, SecHideNotificationShadeInMirrorInteractor secHideNotificationShadeInMirrorInteractor, PanelPopOverManager panelPopOverManager, SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor) {
        KeyguardEditModeControllerImpl keyguardEditModeControllerImpl;
        ImageView imageView;
        FrameLayout frameLayout;
        int i2 = 0;
        this.mKeyguardAffordanceHelperCallback = new KeyguardAffordanceHelperCallback(this, i2);
        this.mOnHeadsUpChangedListener = new ShadeHeadsUpChangedListener(this, i2);
        this.mConfigurationListener = new ConfigurationListener(this, i2);
        this.mStatusBarStateListener = new StatusBarStateListener(this, i2);
        this.mAccessibilityDelegate = new ShadeAccessibilityDelegate(this, i2);
        this.mShadeHeadsUpTracker = new ShadeHeadsUpTrackerImpl(this, i2);
        BiConsumer biConsumer = new BiConsumer() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda22
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                float floatValue = ((Float) obj2).floatValue();
                ((KeyguardRepositoryImpl) notificationPanelViewController.mKeyguardInteractor.repository).panelAlpha.updateState(null, Float.valueOf(floatValue / 255.0f));
                int i3 = (int) floatValue;
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
        this.mFlingCollapseRunnable = new NotificationPanelViewController$$ExternalSyntheticLambda18(this, 2);
        this.mAnimateKeyguardBottomAreaInvisibleEndRunnable = new NotificationPanelViewController$$ExternalSyntheticLambda18(this, 3);
        this.mHeadsUpExistenceChangedRunnable = new NotificationPanelViewController$$ExternalSyntheticLambda18(this, 4);
        this.mMaybeHideExpandedRunnable = new NotificationPanelViewController$$ExternalSyntheticLambda18(this, 5);
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
                    notificationPanelViewController.mView.setAccessibilityPaneTitle(notificationPanelViewController.determineAccessibilityPaneTitle());
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
                List list;
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
                    View view2 = null;
                    if (DeviceState.isTablet() && (list = notificationPanelViewController.mKeyguardStatusBase.mContentsContainerList) != null && list.size() > 2) {
                        view2 = (View) list.get(2);
                    }
                    if (view2 == null || view2.getVisibility() != 0) {
                        return;
                    }
                    view2.setTranslationY(f2);
                    if (notificationPanelViewController.mBarState == 1) {
                        view2.setAlpha(1.0f - f);
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
                boolean isWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper("navibar");
                StatusBarWindowView statusBarWindowView = ((StatusBarWindowControllerImpl) ((StatusBarWindowController) centralSurfacesImpl.mStatusBarWindowControllerStore.getDefaultDisplay())).mStatusBarWindowView;
                int systemUiVisibility = statusBarWindowView.getSystemUiVisibility();
                statusBarWindowView.setSystemUiVisibility(isWhiteKeyguardWallpaper ? systemUiVisibility | 16 : systemUiVisibility & (-17));
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
        notificationPanelView.setAccessibilityPaneTitle(determineAccessibilityPaneTitle());
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
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
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
            public final void onPanelStateChanged$1(int i4) {
                ShadeControllerImpl.AnonymousClass2 anonymousClass2;
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                ShadeLogger shadeLogger2 = notificationPanelViewController.mShadeLog;
                shadeLogger2.getClass();
                LogLevel logLevel = LogLevel.VERBOSE;
                ShadeLogger$$ExternalSyntheticLambda0 shadeLogger$$ExternalSyntheticLambda0 = new ShadeLogger$$ExternalSyntheticLambda0(14);
                LogBuffer logBuffer = shadeLogger2.buffer;
                LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) obtain).str1 = ShadeExpansionStateManagerKt.panelStateToString(i4);
                logBuffer.commit(obtain);
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
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.mBottomAreaShadeAlphaAnimator = ofFloat;
        ofFloat.addUpdateListener(new NotificationPanelViewController$$ExternalSyntheticLambda7(this, 0));
        ofFloat.setDuration(160L);
        ofFloat.setInterpolator(interpolator);
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
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
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
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
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
                notificationPanelViewController.mNavigationBarBottomHeight = secNotificationPanelViewController != null ? notificationPanelViewController.mBarState == 0 ? windowInsets.getInsets(WindowInsets.Type.navigationBars()).bottom : windowInsets.getStableInsetBottom() : windowInsets.getStableInsetBottom();
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
                    panelPopOverManager2.navigationBarTop = DeviceState.getScreenHeight(panelPopOverManager2.context) - notificationPanelViewController.mNavigationBarBottomHeight;
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
        this.mSecNotificationPanelViewController = new SecNotificationPanelViewController(lockscreenShadeTransitionController, notificationsQSContainerController, quickSettingsControllerImpl, shadeHeaderController, shadeRepository, new NotificationPanelViewController$$ExternalSyntheticLambda12(this, i4), new NotificationPanelViewController$$ExternalSyntheticLambda13(this, i4), new NotificationPanelViewController$$ExternalSyntheticLambda12(this, i5), new NotificationPanelViewController$$ExternalSyntheticLambda15(this, i4), new NotificationPanelViewController$$ExternalSyntheticLambda16(this, i4), new NotificationPanelViewController$$ExternalSyntheticLambda16(this, i5), new NotificationPanelViewController$$ExternalSyntheticLambda18(this, 0), secHideNotificationShadeInMirrorInteractor);
        this.mPluginAODManagerLazy = lazy;
        this.mShelfManager = notificationShelfManager;
        notificationShelfManager.getClass();
        this.mMultiWindowEdgeDetector = new MultiWindowEdgeDetector(this.mView.getContext(), "QuickPannel");
        this.mSamsungBarExt = lazy4;
        lockscreenNotificationIconsOnlyController.getClass();
        Log.d("LockscreenNotificationIconsOnlyController", "setNPVController() controller = " + this);
        lockscreenNotificationIconsOnlyController.mNPVController = this;
        NotificationPanelViewController$$ExternalSyntheticLambda18 notificationPanelViewController$$ExternalSyntheticLambda18 = new NotificationPanelViewController$$ExternalSyntheticLambda18(this, 1);
        this.mPostCollapseRunnable = notificationPanelViewController$$ExternalSyntheticLambda18;
        if (QpRune.QUICK_DATA_USAGE_LABEL) {
            this.mDataUsageLabelManagerLazy = lazy5;
        }
        this.mKeyguardEditModeController = keyguardEditModeController;
        NotificationPanelView notificationPanelView2 = this.mView;
        KeyguardEditModeControllerImpl keyguardEditModeControllerImpl2 = (KeyguardEditModeControllerImpl) keyguardEditModeController;
        keyguardEditModeControllerImpl2.getClass();
        ImageView imageView2 = (ImageView) notificationPanelView2.findViewById(R.id.keyguard_edit_mode_blur_effect);
        if (imageView2 == null || (imageView = (ImageView) notificationPanelView2.findViewById(R.id.keyguard_edit_mode_wallpaper)) == null || (frameLayout = (FrameLayout) notificationPanelView2.findViewById(R.id.keyguard_edit_mode_container)) == null) {
            keyguardEditModeControllerImpl = keyguardEditModeControllerImpl2;
        } else {
            keyguardEditModeControllerImpl2.wallpaperCardView = (CardView) notificationPanelView2.findViewById(R.id.keyguard_edit_round_layout);
            keyguardEditModeControllerImpl2.refreshRadius();
            KeyguardEditModeControllerImpl$$ExternalSyntheticLambda2 keyguardEditModeControllerImpl$$ExternalSyntheticLambda2 = new KeyguardEditModeControllerImpl$$ExternalSyntheticLambda2(keyguardEditModeControllerImpl2, notificationPanelView2, imageView2, imageView, frameLayout);
            keyguardEditModeControllerImpl = keyguardEditModeControllerImpl2;
            keyguardEditModeControllerImpl.updateViewsFunction = keyguardEditModeControllerImpl$$ExternalSyntheticLambda2;
            keyguardEditModeControllerImpl.initPreviewValues(notificationPanelView2.getContext());
        }
        keyguardEditModeControllerImpl.onStartActivityListener = new NotificationPanelViewController$$ExternalSyntheticLambda20(this);
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
        ((KeyguardClickControllerImpl) keyguardClickController).isClickContainerArea = new Function2() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda21
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                boolean z;
                int intValue = ((Integer) obj).intValue();
                int intValue2 = ((Integer) obj2).intValue();
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
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
                    z = rect.contains(intValue, intValue2);
                } else {
                    z = false;
                }
                if (iconContainer != null) {
                    Rect rect2 = new Rect();
                    iconContainer.getGlobalVisibleRect(rect2);
                    z |= rect2.contains(intValue, intValue2);
                }
                return Boolean.valueOf(z);
            }
        };
        this.mPluginLockData = pluginLockData;
        this.mPluginLockStarManagerLazy = lazy3;
        ((PluginLockStarManager) lazy3.get()).registerCallback("NotificationPanelViewController", this.mLockStarCallback);
        updateLockStarContainer();
        ((PluginLockStarManager) lazy3.get()).mShortcutController.bottomAreaCallback = keyguardSecBottomAreaViewController;
        this.mQuickQsOffsetHeight = SystemBarUtils.getQuickQsOffsetHeight(this.mView.getContext());
        PanelScreenShotLogger.INSTANCE.addLogProvider("NotificationPanelView", this);
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
        int height = notificationStackScrollLayoutController.mView.getHeight() - Math.max(notificationStackScrollLayout.mMaxLayoutHeight - notificationStackScrollLayout.mContentHeight, 0);
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
    public final void cancelInputFocusTransfer() {
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
        PrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.increaseIndent();
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
        asIndenting.print("mAnimateNextPositionUpdate=");
        asIndenting.println(this.mAnimateNextPositionUpdate);
        asIndenting.print("isPanelExpanded()=");
        asIndenting.println(isPanelExpanded());
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
        asIndenting.println(0.0f);
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
        Region calculateTouchableRegion = this.mShadeTouchableRegionManager.calculateTouchableRegion();
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
        ringBuffer.getClass();
        RingBuffer$iterator$1 ringBuffer$iterator$1 = new RingBuffer$iterator$1(ringBuffer);
        while (ringBuffer$iterator$1.hasNext()) {
            arrayList.add((List) ((NPVCDownEventState) ringBuffer$iterator$1.next()).asStringList$delegate.getValue());
        }
        new DumpsysTableLogger("NotificationPanelView", list, arrayList).printTableData(asIndenting);
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
            notificationPanelView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.shade.NotificationPanelViewController.15
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
        setListening$1(true);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void expandToNotifications() {
        if (this.mSecNotificationPanelViewController != null) {
            if (SecPanelSplitHelper.isEnabled()) {
                this.mPanelSplitHelper.stateOnDown = 0;
            }
            CentralSurfacesImpl centralSurfacesImpl = this.mCentralSurfaces;
            Objects.requireNonNull(centralSurfacesImpl);
            if (isOnKeyguard() && !centralSurfacesImpl.mBouncerShowing && !this.mFullScreenModeEnabled) {
                boolean isNeedsToExpandLocksNoti = ((AmbientState) Dependency.sDependency.getDependencyInner(AmbientState.class)).isNeedsToExpandLocksNoti();
                LockscreenShadeTransitionController lockscreenShadeTransitionController = this.mLockscreenShadeTransitionController;
                if (!isNeedsToExpandLocksNoti) {
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

    public final void expandToQs() {
        SecNotificationPanelViewController secNotificationPanelViewController = this.mSecNotificationPanelViewController;
        if (secNotificationPanelViewController != null) {
            CentralSurfacesImpl centralSurfacesImpl = this.mCentralSurfaces;
            Objects.requireNonNull(centralSurfacesImpl);
            if (isOnKeyguard() && (centralSurfacesImpl.mBouncerShowing || this.mFullScreenModeEnabled)) {
                return;
            }
            SecPanelSplitHelper.Companion.getClass();
            if (SecPanelSplitHelper.isEnabled) {
                if (isOnKeyguard()) {
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

    public final void fling(float f) {
        fling(f, 1.0f, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:110:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01c8  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0284  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0209  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void flingToHeight(float r24, boolean r25, final float r26, float r27, boolean r28) {
        /*
            Method dump skipped, instructions count: 659
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

    public final float getFaceWidgetAlpha() {
        float f;
        if (this.mKeyguardTouchAnimator.isViRunning() || this.mCentralSurfaces.mBouncerShowing) {
            f = -1.0f;
        } else {
            LockscreenShadeTransitionController lockscreenShadeTransitionController = this.mLockscreenShadeTransitionController;
            if (lockscreenShadeTransitionController.getFractionToShade() > 0.0f) {
                float fractionToShade = lockscreenShadeTransitionController.getFractionToShade();
                f = NotificationUtils.interpolate(1.0f, 0.0f, ((double) fractionToShade) > 0.5d ? 1.0f : fractionToShade * 2.0f);
            } else {
                if (this.mClockPositionAlgorithm.isPanelExpanded()) {
                    QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
                    if (quickSettingsControllerImpl.getExpanded()) {
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

    public final int getKeyguardNotificationStaticPadding() {
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i2 = 0;
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
            int i3 = result.stackScrollerPadding;
            int lockscreenNotifPadding2 = this.mClockPositionAlgorithm.getLockscreenNotifPadding();
            if (lockscreenNotifPadding2 == 0) {
                lockscreenNotifPadding2 = result.stackScrollerPadding;
            }
            LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
            if (lockscreenNotificationIconsOnlyController != null && lockscreenNotificationIconsOnlyController.getIconContainer() != null) {
                i2 = lockscreenNotificationIconsOnlyController.getIconContainer().getHeight();
            }
            return this.mMascotViewContainer.updatePosition(lockscreenNotifPadding2, i2) + lockscreenNotifPadding2;
        }
        int i4 = this.mHeadsUpInset;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        if (!notificationStackScrollLayoutController.mView.mAmbientState.isPulseExpanding()) {
            return i4;
        }
        int i5 = result.stackScrollerPadding;
        int lockscreenNotifPadding3 = this.mClockPositionAlgorithm.getLockscreenNotifPadding();
        if (lockscreenNotifPadding3 != 0) {
            i5 = lockscreenNotifPadding3;
        }
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.getClass();
        float f = notificationStackScrollLayout.mAmbientState.mPulseHeight;
        if (f == 100000.0f) {
            f = 0.0f;
        }
        return (int) MathUtils.lerp(i4, i5, MathUtils.smoothStep(0.0f, notificationStackScrollLayout.mIntrinsicPadding, f));
    }

    @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
    public final int getMaxKeyguardNotifications(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "getMaxKeyguardNotifications max: ", "NotificationPanelView");
        return 0;
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
        int calculatePanelHeightExpanded = (isEnabled || quickSettingsControllerImpl.isExpandImmediate() || quickSettingsControllerImpl.getExpanded() || (this.mIsExpandingOrCollapsing && quickSettingsControllerImpl.mExpandedWhenExpandingStarted) || this.mPulsing) ? quickSettingsControllerImpl.calculatePanelHeightExpanded(result.stackScrollerPadding) : calculatePanelHeightShade();
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
    public final void initDependencies(CentralSurfacesImpl centralSurfacesImpl, CentralSurfacesImpl$$ExternalSyntheticLambda29 centralSurfacesImpl$$ExternalSyntheticLambda29, HeadsUpManager headsUpManager) {
        this.mHeadsUpManager = headsUpManager;
        ((HeadsUpManagerImpl) headsUpManager).addListener(this.mOnHeadsUpChangedListener);
        this.mHeadsUpTouchHelper = new HeadsUpTouchHelper(headsUpManager, this.mStatusBarService, this.mNotificationStackScrollLayoutController.mView.mHeadsUpCallback, new HeadsUpNotificationViewControllerImpl(this, 0));
        this.mCentralSurfaces = centralSurfacesImpl;
        this.mHideExpandedRunnable = centralSurfacesImpl$$ExternalSyntheticLambda29;
        this.mNowBarContainer = centralSurfacesImpl.getNotificationShadeWindowViewController().mView.findViewById(R.id.now_bar_rootview);
    }

    public final void instantCollapse() {
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
        float computeExpansionFraction = this.mQsController.computeExpansionFraction();
        if (this.mIsExpandingOrCollapsing) {
            return true;
        }
        return 0.0f < computeExpansionFraction && computeExpansionFraction < 1.0f;
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
        return !notificationStackScrollLayoutController.mView.isBelowLastNotification(f - x, f2) && x < f && f < (notificationStackScrollLayoutController.getWidth() + x) - (f3 * 2.0f);
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

    public final void notifyExpandingFinished() {
        SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor;
        QSImpl qSImpl;
        SecQSImpl secQSImpl;
        SecQSImplAnimatorManager secQSImplAnimatorManager;
        int i = 2;
        byte b = 0;
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
            boolean isFullyCollapsed = isFullyCollapsed();
            ConversationNotificationManager conversationNotificationManager = this.mConversationNotificationManager;
            conversationNotificationManager.notifPanelCollapsed = isFullyCollapsed;
            if (!isFullyCollapsed) {
                FilteringSequence mapNotNull = SequencesKt___SequencesKt.mapNotNull(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(conversationNotificationManager.states.entrySet()), new ConversationNotificationManager$$ExternalSyntheticLambda4(conversationNotificationManager, b == true ? 1 : 0));
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(mapNotNull);
                while (filteringSequence$iterator$1.hasNext()) {
                    Pair pair = (Pair) filteringSequence$iterator$1.next();
                    linkedHashMap.put(pair.component1(), pair.component2());
                }
                final Map optimizeReadOnlyMap = MapsKt__MapsKt.optimizeReadOnlyMap(linkedHashMap);
                conversationNotificationManager.states.replaceAll(new ConversationNotificationManager$sam$java_util_function_BiFunction$0(new Function2() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$$ExternalSyntheticLambda5
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ConversationNotificationManager.ConversationState conversationState = (ConversationNotificationManager.ConversationState) obj2;
                        int i5 = ConversationNotificationManager.$r8$clinit;
                        return optimizeReadOnlyMap.containsKey((String) obj) ? new ConversationNotificationManager.ConversationState(0, conversationState.f133notification) : conversationState;
                    }
                }));
                FilteringSequence$iterator$1 filteringSequence$iterator$12 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.mapNotNull(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(optimizeReadOnlyMap.values()), new ConversationNotificationManager$$ExternalSyntheticLambda1(i)));
                while (filteringSequence$iterator$12.hasNext()) {
                    ConversationNotificationManager.resetBadgeUi((ExpandableNotificationRow) filteringSequence$iterator$12.next());
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
            boolean isFullyCollapsed2 = isFullyCollapsed();
            NotificationPanelView notificationPanelView = this.mView;
            if (isFullyCollapsed2) {
                DejankUtils.postAfterTraversal(new NotificationPanelViewController$$ExternalSyntheticLambda18(this, 6));
                notificationPanelView.postOnAnimation(new NotificationPanelViewController$$ExternalSyntheticLambda18(this, 7));
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
                boolean isKeyguardShowing$1 = isKeyguardShowing$1();
                SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = secNotificationPanelViewController.secQuickSettingsControllerImpl;
                if (secQuickSettingsControllerImpl != null) {
                    boolean booleanValue = ((Boolean) ((ShadeRepositoryImpl) secNotificationPanelViewController.shadeRepository).legacyExpandedOrAwaitingInputTransfer.$$delegate_0.getValue()).booleanValue();
                    if (!booleanValue) {
                        Object obj = secQuickSettingsControllerImpl.qsSupplier.get();
                        QSFragmentLegacy qSFragmentLegacy = obj instanceof QSFragmentLegacy ? (QSFragmentLegacy) obj : null;
                        if (qSFragmentLegacy != null && (qSImpl = qSFragmentLegacy.mQsImpl) != null && (secQSImpl = qSImpl.mSecQSImpl) != null && (secQSImplAnimatorManager = secQSImpl.secQSImplAnimatorManager) != null) {
                            secQSImplAnimatorManager.onPanelClosed$1();
                        }
                        setMotionAborted();
                    }
                    SecPanelSplitHelper.Companion.getClass();
                    if (!SecPanelSplitHelper.isEnabled && !isKeyguardShowing$1 && booleanValue && secNotificationPanelViewController.quickSettingsController.getExpanded() && (secPanelSAStatusLogInteractor = secNotificationPanelViewController.panelSAStatusLogInteractor) != null) {
                        StateFlowImpl stateFlowImpl = secPanelSAStatusLogInteractor.repository._openQuickPanelFrom2Depth;
                        LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl.getValue(), 1L, stateFlowImpl, null);
                    }
                }
                this.mHeadsUpVisibleOnDown = false;
                boolean isPanelExpanded = isPanelExpanded();
                ViewRootImpl viewRootImpl = notificationPanelView.getRootView().getViewRootImpl();
                int i6 = this.mBarState;
                if (viewRootImpl != null) {
                    viewRootImpl.setDisableSuperHdr(new SurfaceControl.Transaction(), i6 == 0 ? isPanelExpanded : false);
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
        boolean isEnabled = SecPanelSplitHelper.isEnabled();
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        NotificationPanelView notificationPanelView = this.mView;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        if (!isEnabled) {
            if (this.mBarState == 0) {
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

    public void onFinishInflate() {
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
        NotificationPanelViewController$$ExternalSyntheticLambda36 notificationPanelViewController$$ExternalSyntheticLambda36 = new NotificationPanelViewController$$ExternalSyntheticLambda36(keyguardSecBottomAreaViewController, 1);
        KeyguardIndicationController keyguardIndicationController = this.mKeyguardIndicationController;
        keyguardIndicationController.mUpdatePosition = notificationPanelViewController$$ExternalSyntheticLambda36;
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
        this.mShadeHeadsUpTracker.addTrackingHeadsUpListener(new NotificationPanelViewController$$ExternalSyntheticLambda36(notificationStackScrollLayoutController, i3));
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
        Edge.StateToState m = KeyguardInteractor$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.AOD, KeyguardState.LOCKSCREEN);
        KeyguardTransitionInteractor keyguardTransitionInteractor = this.mKeyguardTransitionInteractor;
        JavaAdapterKt.collectFlow(notificationPanelView, keyguardTransitionInteractor.transition(m), new NotificationPanelViewController$$ExternalSyntheticLambda16(this, i4), coroutineDispatcher);
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

    public void onFlingEnd(boolean z) {
        SecPanelSplitHelper secPanelSplitHelper;
        this.mIsFlinging = false;
        this.mExpectingSynthesizedDown = false;
        setOverExpansionInternal(0.0f, false);
        SecNotificationPanelViewController secNotificationPanelViewController = this.mSecNotificationPanelViewController;
        if (secNotificationPanelViewController != null && (secPanelSplitHelper = secNotificationPanelViewController.panelSplitHelper) != null) {
            secPanelSplitHelper.isOnceOverExpanded = false;
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

    public void onQsSetExpansionHeightCalled(boolean z) {
        requestScrollerTopPaddingUpdate();
        this.mKeyguardStatusBarViewController.updateViewState();
        int i = this.mBarState;
        if (i == 2 || i == 1) {
            updateKeyguardSecBottomAreaAlpha();
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
        Bundle onUiInfoRequested = this.mKeyguardSecBottomAreaViewController.onUiInfoRequested(z);
        NotificationPanelView notificationPanelView = this.mView;
        int i = Settings.System.getInt(notificationPanelView.getContext().getContentResolver(), SettingsHelper.INDEX_LOCKSCREEN_MINIMIZING_NOTIFICATION, 1);
        onUiInfoRequested.putInt("noti_type", i);
        onUiInfoRequested.putInt("noti_visibility", Settings.Secure.getInt(notificationPanelView.getContext().getContentResolver(), SettingsHelper.INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS, 1));
        onUiInfoRequested.putInt("noti_top", getNotificationTopMargin(z));
        if (i != 0) {
            onUiInfoRequested.putInt("noti_bottom", getNotificationTopMargin(z) + (z ? this.mResources.getDimensionPixelSize(R.dimen.keyguard_indication_dls_default_notification_height_land) : this.mResources.getDimensionPixelSize(R.dimen.keyguard_indication_dls_default_notification_height)));
        }
        Log.d("NotificationPanelView", "onUiInfoRequested bottom: " + onUiInfoRequested.toString());
        return onUiInfoRequested;
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
        int i;
        PluginNotificationController pluginNotificationController;
        KeyguardClockPositionAlgorithm.Result result = this.mClockPositionResult;
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        boolean isKeyguardShowing$1 = isKeyguardShowing$1();
        if (isKeyguardShowing$1 || z) {
            ClockSize clockSize = (this.mActiveNotificationsInteractor.getAreAnyNotificationsPresentValue() || this.mMediaDataManager.hasActiveMediaOrRecommendation()) ? ClockSize.SMALL : ClockSize.LARGE;
            KeyguardClockInteractor keyguardClockInteractor = this.mKeyguardClockInteractor;
            keyguardClockInteractor.getClass();
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            int i2 = SceneContainerFlag.$r8$clinit;
            KeyguardClockRepositoryImpl keyguardClockRepositoryImpl = (KeyguardClockRepositoryImpl) keyguardClockInteractor.keyguardClockRepository;
            keyguardClockRepositoryImpl.getClass();
            keyguardClockRepositoryImpl._clockSize.setValue(clockSize);
            updateKeyguardStatusViewAlignment();
            this.mClockPositionAlgorithm.setup(this.mScreenOffAnimationController.shouldExpandNotifications() ? 1.0f : this.mInterpolatedDarkAmount, this.mOverStretchAmount, quickSettingsControllerImpl.getHeaderHeight(), this.mKeyguardBypassController.getBypassEnabled());
            this.mClockPositionAlgorithm.run(result);
            updateClock$1();
        }
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        if (isKeyguardShowing$1) {
            int i3 = result.stackScrollerPaddingExpanded;
            boolean z2 = CscRune.KEYGUARD_DCM_LIVE_UX;
            i = z2 ? result.stackScrollerPadding : i3;
            if (z2) {
                i += this.mMascotViewContainer.updatePosition(i, (lockscreenNotificationIconsOnlyController == null || lockscreenNotificationIconsOnlyController.getIconContainer() == null) ? 0 : lockscreenNotificationIconsOnlyController.getIconContainer().getHeight());
            }
        } else {
            i = quickSettingsControllerImpl.getHeaderHeight();
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        notificationStackScrollLayoutController.getClass();
        int i4 = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.getClass();
        notificationStackScrollLayout.mIntrinsicPadding = i;
        int i5 = this.mStackScrollerMeasuringPass + 1;
        this.mStackScrollerMeasuringPass = i5;
        if (i5 > 2) {
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
        float max;
        int i = SceneContainerFlag.$r8$clinit;
        int keyguardNotificationStaticPadding = getKeyguardNotificationStaticPadding();
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        quickSettingsControllerImpl.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        boolean z = quickSettingsControllerImpl.mBarState == 1;
        if (quickSettingsControllerImpl.mSizeChangeAnimator != null && !SecPanelSplitHelper.isEnabled()) {
            max = Math.max(((Integer) quickSettingsControllerImpl.mSizeChangeAnimator.getAnimatedValue()).intValue(), keyguardNotificationStaticPadding);
        } else if (!z || quickSettingsControllerImpl.mAmbientState.mDragDownOnKeyguard) {
            max = SecPanelSplitHelper.isEnabled() ? ((float) Math.max(r2.getNotificationsTopPadding((float) quickSettingsControllerImpl.mSecQuickSettingsControllerImpl.minExpansionHeightSupplier.getAsDouble()), quickSettingsControllerImpl.mQuickQsHeaderHeight)) + quickSettingsControllerImpl.mLastOverscroll : Math.max(quickSettingsControllerImpl.mQsFrameTranslateController.getNotificationsTopPadding(quickSettingsControllerImpl.mExpansionHeight), quickSettingsControllerImpl.mQuickQsHeaderHeight);
        } else {
            max = MathUtils.lerp(keyguardNotificationStaticPadding, quickSettingsControllerImpl.mMaxExpansionHeight, quickSettingsControllerImpl.computeExpansionFraction());
        }
        boolean isKeyguardShowing$1 = isKeyguardShowing$1();
        SharedNotificationContainerInteractor sharedNotificationContainerInteractor = this.mSharedNotificationContainerInteractor;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        if (isKeyguardShowing$1) {
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
        int i2 = (int) max;
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
        sharedNotificationContainerInteractor._topPosition.updateState(null, Float.valueOf(max));
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
            new Handler(Looper.getMainLooper()).postDelayed(new NotificationPanelViewController$$ExternalSyntheticLambda18(this, 10), 100L);
        } else {
            updateVisibility();
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
        if (valueAnimator != null && valueAnimator.isRunning()) {
            if (!z2 || statusBarStateControllerImpl.mDozeAmountTarget != f) {
                statusBarStateControllerImpl.mDarkAnimator.cancel();
            }
            updateKeyguardStatusViewAlignment();
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
        updateKeyguardStatusViewAlignment();
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
        ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).batchApplyWindowLayoutParams(new Runnable() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda41
            @Override // java.lang.Runnable
            public final void run() {
                float calculatePanelHeightExpanded;
                StringBuilder sb;
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                float f2 = f;
                if (notificationPanelViewController.mExpandLatencyTracking && f2 != 0.0f) {
                    DejankUtils.postAfterTraversal(new NotificationPanelViewController$$ExternalSyntheticLambda18(notificationPanelViewController, 8));
                    notificationPanelViewController.mExpandLatencyTracking = false;
                }
                float maxPanelTransitionDistance = notificationPanelViewController.getMaxPanelTransitionDistance();
                if (SecPanelSplitHelper.isEnabled() && notificationPanelViewController.mHeightAnimator == null && notificationPanelViewController.isTracking()) {
                    notificationPanelViewController.setOverExpansionInternal(Math.max(0.0f, f2 - maxPanelTransitionDistance), true);
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
                if (min3 > 0.0f && notificationPanelViewController.mExpectingSynthesizedDown) {
                    notificationPanelViewController.mExpectingSynthesizedDown = false;
                }
                float f5 = notificationPanelViewController.mExpandedHeight;
                QuickSettingsControllerImpl quickSettingsControllerImpl = notificationPanelViewController.mQsController;
                quickSettingsControllerImpl.mShadeExpandedHeight = f5;
                quickSettingsControllerImpl.mShadeExpandedFraction = min3;
                quickSettingsControllerImpl.mMediaHierarchyManager.getClass();
                ((ShadeRepositoryImpl) notificationPanelViewController.mShadeRepository)._legacyShadeExpansion.updateState(null, Float.valueOf(min3));
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
                        calculatePanelHeightExpanded = f7 / notificationPanelViewController.getMaxPanelHeight();
                    } else {
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
                        notificationStackScrollLayoutController.getClass();
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                        notificationStackScrollLayout.getClass();
                        float f8 = notificationStackScrollLayout.mIntrinsicPadding;
                        notificationStackScrollLayoutController.mView.getClass();
                        float layoutMinHeightInternal = r1.getLayoutMinHeightInternal() + f8;
                        calculatePanelHeightExpanded = (f7 - layoutMinHeightInternal) / (quickSettingsControllerImpl.calculatePanelHeightExpanded(notificationPanelViewController.mClockPositionResult.stackScrollerPadding) - layoutMinHeightInternal);
                    }
                    quickSettingsControllerImpl.setExpansionHeight((calculatePanelHeightExpanded * (quickSettingsControllerImpl.mMaxExpansionHeight - r4)) + quickSettingsControllerImpl.mMinExpansionHeight);
                }
                if (QpRune.QUICK_DATA_USAGE_LABEL) {
                    DataUsageLabelManager dataUsageLabelManager = (DataUsageLabelManager) notificationPanelViewController.mDataUsageLabelManagerLazy.get();
                    DataUsageLabelParent dataUsageLabelParent = dataUsageLabelManager.mDataUsageLabelParent;
                    float min4 = Math.min(1.0f, f7 / dataUsageLabelParent.mMaxPanelHeightSupplier.getAsInt());
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
            ViewPropertyAnimator alpha = this.mKeyguardSecBottomArea.animate().alpha(0.0f);
            KeyguardStateControllerImpl keyguardStateControllerImpl = this.mKeyguardStateController;
            ViewPropertyAnimator startDelay = alpha.setStartDelay(keyguardStateControllerImpl.mKeyguardFadingAwayDelay);
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
    public final void setKeyguardStatusBarAlpha() {
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
        int i;
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
        int i2 = ((NotificationPanelViewController) quickSettingsControllerImpl.mPanelViewControllerLazy.get()).mNavigationBarBottomHeight;
        int i3 = quickSettingsControllerImpl.mAmbientState.mStackTopMargin;
        quickSettingsControllerImpl.mQsFrameTranslateController.getClass();
        this.mNotificationStackScrollLayoutController.setOverExpansion(f);
        SecNotificationPanelViewController secNotificationPanelViewController = this.mSecNotificationPanelViewController;
        if (secNotificationPanelViewController != null) {
            float f2 = (int) f;
            int i4 = (int) f2;
            QS qs = secNotificationPanelViewController.quickSettingsController.mQs;
            if (qs != null) {
                qs.setOverScrollAmount(i4);
            }
            if (f2 > 15.0f && (secPanelSplitHelper = secNotificationPanelViewController.panelSplitHelper) != null) {
                secPanelSplitHelper.isOnceOverExpanded = true;
            }
        }
        if (!QpRune.QUICK_PANEL_CODE_FOR_POP_OVER || (panelPopOverManager = this.mPanelPopOverManager) == null) {
            return;
        }
        float dimension = ((int) f) / (panelPopOverManager.context.getResources().getDimension(R.dimen.panel_overshoot_amount) * 1.5f);
        boolean isTablet = ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet();
        BarOrderInteractor barOrderInteractor = panelPopOverManager.barOrderInteractor;
        panelPopOverManager.overExpansionAmount = (int) (dimension * (isTablet ? barOrderInteractor.getBarViewsByOrder() : panelPopOverManager.context.getResources().getConfiguration().orientation == 2 ? barOrderInteractor.landscapeBars : barOrderInteractor.getBarViewsByOrder()).size() * 20);
        SecPanelSplitHelper secPanelSplitHelper2 = panelPopOverManager.panelSplitHelper;
        if (secPanelSplitHelper2.enabled) {
            int i5 = secPanelSplitHelper2.currentState;
            i = i5 != 2 ? i5 : secPanelSplitHelper2.draggedFraction < 0.5f ? secPanelSplitHelper2.stateOnDown : secPanelSplitHelper2.stateToChange;
        } else {
            i = 3;
        }
        if (i == 0) {
            View view = panelPopOverManager.qsPanelView;
            panelPopOverManager.setBlurArea(view != null ? view.getWidth() : 0, panelPopOverManager.getPopOverHeight());
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
    public final void setTouchAndAnimationDisabled(boolean z) {
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
                                DcmMascotViewContainer.this.setMascotViewVisible(0);
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

    /* JADX WARN: Removed duplicated region for block: B:35:0x007d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean shouldPanelBeVisible() {
        /*
            r7 = this;
            boolean r0 = r7.mHeadsUpAnimatingAway
            r1 = 1
            r2 = 0
            if (r0 != 0) goto Ld
            boolean r0 = r7.mHeadsUpPinnedMode
            if (r0 == 0) goto Lb
            goto Ld
        Lb:
            r0 = r2
            goto Le
        Ld:
            r0 = r1
        Le:
            int r3 = r7.mPanelInVisibleReason
            r4 = -1
            r7.mPanelInVisibleReason = r4
            if (r0 != 0) goto L71
            r7.mPanelInVisibleReason = r4
            boolean r4 = r7.isExpanded()
            if (r4 != 0) goto L1f
            r7.mPanelInVisibleReason = r2
        L1f:
            boolean r5 = r7.mBouncerShowing
            if (r5 == 0) goto L26
            r7.mPanelInVisibleReason = r1
            r4 = r2
        L26:
            boolean r5 = com.android.systemui.LsRune.KEYGUARD_SUB_DISPLAY_LOCK
            if (r5 == 0) goto L42
            if (r4 == 0) goto L42
            com.android.systemui.Dependency r5 = com.android.systemui.Dependency.sDependency
            java.lang.Class<com.android.systemui.keyguard.KeyguardFoldController> r6 = com.android.systemui.keyguard.KeyguardFoldController.class
            java.lang.Object r5 = r5.getDependencyInner(r6)
            com.android.systemui.keyguard.KeyguardFoldController r5 = (com.android.systemui.keyguard.KeyguardFoldController) r5
            com.android.systemui.keyguard.KeyguardFoldControllerImpl r5 = (com.android.systemui.keyguard.KeyguardFoldControllerImpl) r5
            boolean r5 = r5.isUnlockOnFoldOpened()
            if (r5 == 0) goto L42
            r4 = 2
            r7.mPanelInVisibleReason = r4
            r4 = r2
        L42:
            boolean r5 = com.android.systemui.LsRune.SECURITY_SWIPE_BOUNCER
            if (r5 == 0) goto L5e
            if (r4 == 0) goto L5e
            com.android.systemui.Dependency r5 = com.android.systemui.Dependency.sDependency
            java.lang.Class<com.android.systemui.statusbar.policy.KeyguardStateController> r6 = com.android.systemui.statusbar.policy.KeyguardStateController.class
            java.lang.Object r5 = r5.getDependencyInner(r6)
            com.android.systemui.statusbar.policy.KeyguardStateController r5 = (com.android.systemui.statusbar.policy.KeyguardStateController) r5
            com.android.systemui.statusbar.policy.KeyguardStateControllerImpl r5 = (com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r5
            boolean r5 = r5.isShownSwipeBouncer()
            if (r5 == 0) goto L5e
            r4 = 3
            r7.mPanelInVisibleReason = r4
            r4 = r2
        L5e:
            boolean r5 = r7.isKeyguardShowing$1()
            if (r5 == 0) goto L6c
            boolean r5 = r7.mFullScreenModeEnabled
            if (r5 == 0) goto L6c
            r4 = 5
            r7.mPanelInVisibleReason = r4
            r4 = r2
        L6c:
            if (r4 == 0) goto L6f
            goto L71
        L6f:
            r4 = r2
            goto L72
        L71:
            r4 = r1
        L72:
            int r5 = r7.mPanelInVisibleReason
            com.android.systemui.shade.NotificationPanelView r7 = r7.mView
            int r7 = r7.getVisibility()
            if (r7 != 0) goto L7d
            goto L7e
        L7d:
            r1 = r2
        L7e:
            if (r1 != r4) goto L86
            if (r4 != 0) goto L85
            if (r3 == r5) goto L85
            goto L86
        L85:
            return r4
        L86:
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r4)
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r5)
            java.lang.Object[] r7 = new java.lang.Object[]{r7, r0, r1}
            java.lang.String r0 = "KeyguardVisible"
            java.lang.String r1 = "shouldPanelBeVisible %b / headUpVisible=%b, why=%d"
            com.android.systemui.keyguard.Log.d(r0, r1, r7)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.NotificationPanelViewController.shouldPanelBeVisible():boolean");
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void showAodUi() {
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
        float interpolate = NotificationUtils.interpolate(1.0f, 0.0f, ((double) fractionToShade) > 0.5d ? 1.0f : fractionToShade * 2.0f);
        KeyguardSecVisibilityHelper keyguardSecVisibilityHelper2 = faceWidgetContainerWrapper2.mKeyguardSecVisibilityHelper;
        if (keyguardSecVisibilityHelper2 == null || keyguardSecVisibilityHelper2.isVisibilityAnimating) {
            return;
        }
        View view2 = faceWidgetContainerWrapper2.mFaceWidgetContainer;
        if (view2 != null) {
            view2.setAlpha(interpolate);
        }
        KeyguardStatusViewAlphaChangeControllerWrapper keyguardStatusViewAlphaChangeControllerWrapper2 = faceWidgetContainerWrapper2.mKeyguardStatusViewAlphaChangeControllerWrapper;
        if (keyguardStatusViewAlphaChangeControllerWrapper2 != null) {
            keyguardStatusViewAlphaChangeControllerWrapper2.updateAlpha(interpolate);
        }
    }

    public final void updateDozingVisibilities(boolean z) {
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
        boolean isTracking = isTracking();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mNotificationStackScrollLayoutController;
        if (isTracking) {
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
        boolean isExpanded = isExpanded();
        boolean isTracking = isTracking();
        ShadeExpansionStateManager shadeExpansionStateManager = this.mShadeExpansionStateManager;
        shadeExpansionStateManager.getClass();
        if (Float.isNaN(f)) {
            throw new IllegalArgumentException("fraction cannot be NaN");
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
            MediaSessions$H$$ExternalSyntheticOutline0.m("onPanelExpansionChanged: ", ShadeExpansionStateManagerKt.panelStateToString(i2), " -> ", ShadeExpansionStateManagerKt.panelStateToString(shadeExpansionStateManager.state), ShadeExpansionStateManagerKt.TAG);
        }
        if (Trace.isTagEnabled(4096L)) {
            TrackTracer.Companion.getClass();
            TrackTracer.Companion.instantForGroup((int) (100 * f), "shade", "panel_expansion");
            shadeExpansionStateManager.stateLogger.log(ShadeExpansionStateManagerKt.panelStateToString(shadeExpansionStateManager.state));
        }
        ShadeExpansionChangeEvent shadeExpansionChangeEvent = new ShadeExpansionChangeEvent(f, isExpanded, isTracking);
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
        Region calculateTouchableRegion = this.mShadeTouchableRegionManager.calculateTouchableRegion();
        Rect bounds = (!isFullyCollapsed() || calculateTouchableRegion == null) ? null : calculateTouchableRegion.getBounds();
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
        float constrainedMap = MathUtils.constrainedMap(0.0f, 1.0f, 0.95f, 1.0f, this.mExpandedFraction);
        LockscreenShadeTransitionController lockscreenShadeTransitionController = this.mLockscreenShadeTransitionController;
        float min = Math.min(constrainedMap, 1.0f - lockscreenShadeTransitionController.getFractionToShade());
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
                    leftView.setImageAlpha(Math.min(1.0f, min), false);
                    leftView.setImageScale(1.0f, false);
                    KeyguardSecAffordanceHelper keyguardSecAffordanceHelper3 = this.mSecAffordanceHelper;
                    KeyguardSecAffordanceView rightView = this.mKeyguardSecBottomArea.getRightView();
                    keyguardSecAffordanceHelper3.getClass();
                    rightView.setImageAlpha(Math.min(1.0f, min), false);
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
        this.mKeyguardUnfoldTransition.ifPresent(new Consumer() { // from class: com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda42
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
        if (QpRune.QUICK_PANEL_CODE_FOR_POP_OVER && (panelPopOverManager = this.mPanelPopOverManager) != null && z && panelPopOverManager.getNeedToPopOver() && !panelPopOverManager.isPopOverAreaListenerAdded) {
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
            panelPopOverManager.customizerView = notificationPanelView4 != null ? notificationPanelView4.findViewById(R.id.main_content) : null;
            NotificationPanelView notificationPanelView5 = panelPopOverManager.mView;
            panelPopOverManager.detailView = notificationPanelView5 != null ? notificationPanelView5.findViewById(R.id.qs_detail_container) : null;
            NotificationPanelView notificationPanelView6 = panelPopOverManager.mView;
            panelPopOverManager.blurView = notificationPanelView6 != null ? notificationPanelView6.findViewById(R.id.qs_new_blur_view) : null;
            NotificationPanelView notificationPanelView7 = panelPopOverManager.mView;
            panelPopOverManager.largeShadowView = notificationPanelView7 != null ? (SecQSBlurShadowView) notificationPanelView7.findViewById(R.id.qs_large_shadow_view) : null;
            NotificationPanelView notificationPanelView8 = panelPopOverManager.mView;
            panelPopOverManager.smallShadowView = notificationPanelView8 != null ? (SecQSBlurShadowView) notificationPanelView8.findViewById(R.id.qs_small_shadow_view) : null;
            panelPopOverManager.notificationShelf = panelPopOverManager.notificationStackScrollLayoutController.mView.mShelf;
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
        boolean isEnabled = ShadeWindowGoesAround.isEnabled();
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.mQsController;
        boolean z = false;
        int i = 0;
        z = false;
        if (!isEnabled) {
            SysUiState flag = this.mSysUiState.setFlag(1073741824L, isPanelExpanded() && !isCollapsing()).setFlag(4L, isFullyExpanded() && !quickSettingsControllerImpl.getExpanded());
            if (isFullyExpanded() && quickSettingsControllerImpl.getExpanded()) {
                z = true;
            }
            ((SysUiStateImpl) flag.setFlag(2048L, z)).commitUpdate();
            return;
        }
        int intValue = ShadeWindowGoesAround.isEnabled() ? ((Integer) ((ShadeDisplaysRepositoryImpl) ((ShadeDisplaysRepository) this.mShadeDisplaysRepository.get())).pendingDisplayId.$$delegate_0.getValue()).intValue() : 0;
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
            if (sysUiState2.getDisplayId() == intValue) {
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
        notificationPanelView.post(new NotificationPanelViewController$$ExternalSyntheticLambda18(this, 11));
    }

    public final void updateVisibility() {
        int i = 4;
        this.mView.setVisibility(shouldPanelBeVisible() ? 0 : 4);
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

    public final void fling(float f, float f2, boolean z) {
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

    public final void collapse(float f, boolean z) {
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
