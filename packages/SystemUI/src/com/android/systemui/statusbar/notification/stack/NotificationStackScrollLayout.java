package com.android.systemui.statusbar.notification.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.SemWallpaperColors;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RenderEffect;
import android.graphics.RenderNode;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.service.notification.StatusBarNotification;
import android.service.notification.ZenModeConfig;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.MathUtils;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.OverScroller;
import android.widget.ScrollView;
import androidx.collection.ArraySet;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.app.tracing.TrackGroupUtils;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.policy.SystemBarUtils;
import com.android.keyguard.BouncerPanelExpansionCalculator;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.ExpandHelper;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.QuickPanelLogger;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.notification.FullExpansionPanelNotiAlphaController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.qs.animator.QsAnimatorState;
import com.android.systemui.qs.animator.SecQSImplAnimatorManager;
import com.android.systemui.qs.flags.QSComposeFragment;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda0;
import com.android.systemui.shade.QuickSettingsControllerImpl;
import com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda10;
import com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda18;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shade.TouchLogger;
import com.android.systemui.statusbar.DndStatusView;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.LaunchAnimationParameters;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.EntryWithDismissStats;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl;
import com.android.systemui.statusbar.notification.emptyshade.shared.ModesEmptyShadeFix;
import com.android.systemui.statusbar.notification.emptyshade.ui.view.EmptyShadeView;
import com.android.systemui.statusbar.notification.headsup.HeadsUpTouchHelper;
import com.android.systemui.statusbar.notification.headsup.NotificationsHunSharedAnimationValues;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.notification.shared.NotificationContentAlphaOptimization;
import com.android.systemui.statusbar.notification.shared.NotificationsLiveDataStoreRefactor;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationScrollView;
import com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1$callback$1;
import com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$bind$3;
import com.android.systemui.statusbar.notification.ui.viewbinder.HeadsUpNotificationViewBinderKt$isHeadsUpAnimatingAway$1$1;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.widget.SystemUIWidgetCallback;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.sequences.SequenceBuilderIterator;
import kotlin.sequences.SequencesKt__SequenceBuilderKt;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlinx.coroutines.channels.ChannelCoroutine;
import noticolorpicker.NotificationColorPicker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NotificationStackScrollLayout extends ViewGroup implements Dumpable, NotificationScrollView, PanelScreenShotLogger.LogProvider {
    public static final boolean DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY = SystemProperties.getBoolean("debug.noti.disable_new_notif_only", false);
    static final float RUBBER_BAND_FACTOR_NORMAL = 0.1f;
    public final AnonymousClass4 collectVisibleLocationsCallable;
    public int mActivePointerId;
    public ActivityStarter mActivityStarter;
    public final ArrayList mAddedHeadsUpChildren;
    public final AmbientState mAmbientState;
    public boolean mAnimateStackYForContentHeightChange;
    public final ArrayList mAnimationEvents;
    public final HashSet mAnimationFinishedRunnables;
    public boolean mAnimationRunning;
    public boolean mAnimationsEnabled;
    public final Rect mBackgroundAnimationRect;
    public float mBackgroundXFactor;
    public boolean mBackwardScrollable;
    public final RenderEffect mBlurEffect;
    public final RenderNode mBlurNode;
    public int mBottomPadding;
    public boolean mChangePositionInProgress;
    public boolean mCheckForLeavebehind;
    public boolean mChildTransferInProgress;
    public final ArrayList mChildrenChangingPositions;
    public final HashSet mChildrenToAddAnimated;
    public final ArrayList mChildrenToRemoveAnimated;
    public boolean mChildrenUpdateRequested;
    public final AnonymousClass1 mChildrenUpdater;
    public NotificationStackScrollLayoutController$$ExternalSyntheticLambda4 mClearAllAnimationListener;
    public NotificationStackScrollLayoutController$$ExternalSyntheticLambda6 mClearAllFinishedWhilePanelExpandedRunnable;
    public boolean mClearAllInProgress;
    public NotificationStackScrollLayoutController$$ExternalSyntheticLambda4 mClearAllListener;
    public final HashSet mClearTransientViewsWhenFinished;
    public final Rect mClipRect;
    public NotificationStackScrollLayoutController$$ExternalSyntheticLambda6 mCollapseShadeDelayedWhenNoViewsToAnimateAwayRunnable;
    public int mContentHeight;
    public boolean mContinuousShadowUpdate;
    public NotificationStackScrollLayoutController mController;
    public int mCornerRadius;
    public int mCurrentStackHeight;
    public final SimpleDateFormat mDateFormat;
    public final boolean mDebugRemoveAnimation;
    public int mDeviceWidth;
    public boolean mDisallowDismissInThisMotion;
    public boolean mDisallowScrollingInThisMotion;
    public boolean mDismissUsingRowTranslationX;
    public final AnonymousClass5 mDisplayListener;
    public final DisplayManager mDisplayManager;
    public int mDisplayState;
    public int mDndStatus;
    public DndStatusView mDndStatusView;
    public boolean mDontReportNextOverScroll;
    public int mDownX;
    public EmptyShadeView mEmptyShadeView;
    public boolean mEverythingNeedsAnimation;
    public final ExpandHelper mExpandHelper;
    public final AnonymousClass11 mExpandHelperCallback;
    public ExpandableNotificationRow mExpandedGroupView;
    public float mExpandedHeight;
    public final ArrayList mExpandedHeightListeners;
    public boolean mExpandedInThisMotion;
    public boolean mExpandingNotification;
    public ExpandableNotificationRow mExpandingNotificationRow;
    public float mExtraTopInsetForFullShadeTransition;
    public Runnable mFinishScrollingCallback;
    public boolean mFlingAfterUpEvent;
    public boolean mForceLayoutFirstMeasure;
    public boolean mForceNoOverlappingRendering;
    public View mForcedScroll;
    public boolean mForwardScrollable;
    public final HashSet mFromMoreCardAdditions;
    public final FullExpansionPanelNotiAlphaController mFullExpansionPanelNotiAlphaController;
    public long mGoToFullShadeDelay;
    public boolean mGoToFullShadeNeedsAnimation;
    public final GroupExpansionManager mGroupExpansionManager;
    public final GroupMembershipManager mGroupMembershipManager;
    boolean mHeadsUpAnimatingAway;
    public final HeadsUpNotificationViewBinderKt$isHeadsUpAnimatingAway$1$1 mHeadsUpAnimatingAwayListener;
    public HeadsUpAppearanceController mHeadsUpAppearanceController;
    public final AnonymousClass10 mHeadsUpCallback;
    public final Map mHeadsUpChangeAnimations;
    public boolean mHeadsUpGoingAwayAnimationsAllowed;
    public final ListenerSet mHeadsUpHeightChangedListeners;
    public int mHeadsUpInset;
    public boolean mHideSensitiveNeedsAnimation;
    public Interpolator mHideXInterpolator;
    public boolean mHighPriorityBeforeSpeedBump;
    int mImeInset;
    public boolean mInHeadsUpPinnedMode;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public final AnonymousClass6 mInsetsCallback;
    public float mInterpolatedHideAmount;
    public float mIntrinsicContentHeight;
    public int mIntrinsicPadding;
    public boolean mIsBeingDragged;
    public boolean mIsChangedOrientation;
    public boolean mIsClipped;
    public boolean mIsExpanded;
    public boolean mIsExpansionChanging;
    public boolean mIsInsetAnimationRunning;
    public boolean mIsVisibleFromGone;
    public float mKeyguardBottomPadding;
    public boolean mKeyguardBypassEnabled;
    public final KeyguardFoldController mKeyguardFoldController;
    public String mLastGoneCallTrace;
    public String mLastInitViewDumpString;
    public long mLastInitViewElapsedRealtime;
    public String mLastInvisibleTrace;
    public int mLastMotionY;
    public float mLastSentAppear;
    public float mLastSentExpandedHeight;
    public String mLastUpdateSidePaddingDumpString;
    public long mLastUpdateSidePaddingElapsedRealtime;
    public String mLastVisibleTrace;
    public LaunchAnimationParameters mLaunchAnimationParams;
    public final Path mLaunchedNotificationClipPath;
    public final float[] mLaunchedNotificationRadii;
    public boolean mLaunchingNotification;
    public boolean mLaunchingNotificationNeedsToBeClipped;
    public float mLinearHideAmount;
    public NotificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1$callback$1 mLocationsChangedListener;
    public NotificationStackScrollLogger mLogger;
    public int mMaxDisplayedNotifications;
    public int mMaxLayoutHeight;
    public float mMaxOverScroll;
    public int mMaxScrollAfterExpand;
    public int mMaxTopPadding;
    public int mMaximumVelocity;
    public SecMediaHost mMediaHost;
    public int mMinInteractionHeight;
    public float mMinTopOverScrollToEscape;
    public int mMinimumPaddings;
    public int mMinimumVelocity;
    public boolean mNeedToUpdateProgress;
    public boolean mNeedViewResizeAnimation;
    public boolean mNeedsAnimation;
    public final Path mNegativeRoundedClipPath;
    public NotificationStackSizeCalculator mNotificationStackSizeCalculator;
    public final AnonymousClass7 mOnChildHeightChangedListener;
    public final AnonymousClass8 mOnChildSensitivityChangedListener;
    public NotificationPanelViewController$$ExternalSyntheticLambda0 mOnEmptySpaceClickListener;
    public NotificationPanelViewController.NsslHeightChangedListener mOnHeightChangedListener;
    public SharedNotificationContainerBinder$bind$3 mOnHeightChangedRunnable;
    public QuickSettingsControllerImpl$$ExternalSyntheticLambda18 mOnStackYChanged;
    public boolean mOnlyScrollingInThisMotion;
    public int mOrientation;
    public final AnonymousClass3 mOutlineProvider;
    public float mOverScrolledBottomPixels;
    public float mOverScrolledTopPixels;
    public int mOverflingDistance;
    public QuickSettingsControllerImpl.NsslOverscrollTopChangedListener mOverscrollTopChangedListener;
    public int mOwnScrollY;
    public int mPaddingBetweenElements;
    public SecPanelSplitHelper mPanelSplitHelper;
    public boolean mPanelTracking;
    public float mPreviousTouchX;
    public float mPreviousTouchY;
    public boolean mPulsing;
    public float mQsExpansionFraction;
    public boolean mQsFullScreen;
    public ViewGroup mQsHeader;
    public final Rect mQsHeaderBound;
    public int mQsMinHeight;
    public int mQsTilePadding;
    public final StringBuilder mQuickPanelLogBuilder;
    public final QuickPanelLogger mQuickPanelLogger;
    public final NotificationStackScrollLayout$$ExternalSyntheticLambda4 mReflingAndAnimateScroll;
    public Rect mRequestedClipBounds;
    public NotificationStackScrollLayoutController$$ExternalSyntheticLambda6 mResetUserExpandedStatesRunnable;
    public final float[] mRoundedClipCornerRadii;
    public final Path mRoundedClipPath;
    public int mRoundedRectClippingBottom;
    public int mRoundedRectClippingLeft;
    public int mRoundedRectClippingRight;
    public int mRoundedRectClippingTop;
    public final AnonymousClass2 mRunningAnimationUpdater;
    public final AnonymousClass9 mScrollAdapter;
    public SecQSImplAnimatorManager.AnonymousClass1 mScrollChangedConsumer;
    public QuickSettingsControllerImpl$$ExternalSyntheticLambda18 mScrollListener;
    public final ScrollViewFields mScrollViewFields;
    public boolean mScrollable;
    public boolean mScrolledToTopOnFirstDown;
    public OverScroller mScroller;
    public boolean mScrollingEnabled;
    public final NotificationSection[] mSections;
    public final NotificationSectionsManager mSectionsManager;
    public int mSemDisplayDeviceType;
    public boolean mShadeNeedsToClose;
    public final NotificationStackScrollLayout$$ExternalSyntheticLambda2 mShadowUpdater;
    public NotificationShelf mShelf;
    public NotificationShelfManager mShelfManager;
    public boolean mShouldShowShelfOnly;
    public boolean mShouldSkipTopPaddingAnimationAfterFold;
    public boolean mShouldUseRoundedRectClipping;
    public int mSidePaddings;
    public boolean mSkinnyNotifsInLandscape;
    public float mSlopMultiplier;
    public boolean mSpeedBumpIndexDirty;
    public SplitShadeStateController mSplitShadeStateController;
    public final StackScrollAlgorithm mStackScrollAlgorithm;
    public final StackStateAnimator mStateAnimator;
    int mStatusBarHeight;
    public int mStatusBarState;
    public boolean mSuppressChildrenMeasureAndLayout;
    public final HashSet mSwipeCancelledView;
    public NotificationSwipeHelper mSwipeHelper;
    public final ArrayList mSwipedOutViews;
    public final AnonymousClass12 mSystemUIWidgetCallback;
    public final int[] mTempInt2;
    public final ArrayList mTmpHeadsUpChangeAnimations;
    public final Rect mTmpRect;
    public final ArrayList mTmpSortedChildren;
    public ExpandableNotificationRow mTopHeadsUpRow;
    public boolean mTopPaddingNeedsAnimation;
    public float mTopPaddingOverflow;
    public NotificationStackScrollLayoutController.TouchHandler mTouchHandler;
    public boolean mTouchIsClick;
    public int mTouchSlop;
    public VelocityTracker mVelocityTracker;
    public final NotificationStackScrollLayout$$ExternalSyntheticLambda3 mViewPositionComparator;
    public boolean mVislbeNSSLWhileMediaExpanded;
    public int mWaterfallTopInset;
    public boolean mWillExpand;
    public int mYDiff;
    public ZenModeController mZenModeController;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$10, reason: invalid class name */
    public class AnonymousClass10 implements HeadsUpTouchHelper.Callback {
        public AnonymousClass10() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$11, reason: invalid class name */
    public class AnonymousClass11 implements ExpandHelper.Callback {
        public AnonymousClass11() {
        }

        public final boolean canChildBeExpanded(ExpandableView expandableView) {
            if (!(expandableView instanceof ExpandableNotificationRow)) {
                return false;
            }
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
            if (!expandableNotificationRow.isExpandable() || expandableNotificationRow.areGutsExposed()) {
                return false;
            }
            return NotificationStackScrollLayout.this.mIsExpanded || !expandableNotificationRow.mPinnedStatus.isPinned();
        }

        public final void expansionStateChanged(boolean z) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
            notificationStackScrollLayout.mExpandingNotification = z;
            if (notificationStackScrollLayout.mExpandedInThisMotion) {
                return;
            }
            notificationStackScrollLayout.mMaxScrollAfterExpand = notificationStackScrollLayout.getOwnScrollY();
            notificationStackScrollLayout.mExpandedInThisMotion = true;
        }

        public final void setUserExpandedChild(View view, boolean z) {
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                if (z) {
                    NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                    if (notificationStackScrollLayout.onKeyguard()) {
                        expandableNotificationRow.setUserLocked(false);
                        notificationStackScrollLayout.updateContentHeight();
                        notificationStackScrollLayout.notifyHeightChangeListener(expandableNotificationRow, false);
                        return;
                    }
                }
                expandableNotificationRow.setUserExpanded(z, true);
                expandableNotificationRow.onExpandedByGesture(z);
                if (z) {
                    SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_NOTI_EXPANSION, "type", expandableNotificationRow.mIsSummaryWithChildren ? SystemUIAnalytics.QPNE_VID_GROUPED : SystemUIAnalytics.QPNE_VID_SINGLE, SystemUIAnalytics.QPNE_KEY_APP, expandableNotificationRow.mEntry.mSbn.getPackageName());
                }
            }
        }

        public final void setUserLockedChild(View view, boolean z) {
            if (view instanceof ExpandableNotificationRow) {
                ((ExpandableNotificationRow) view).setUserLocked(z);
            }
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
            notificationStackScrollLayout.cancelLongPress();
            notificationStackScrollLayout.requestDisallowInterceptTouchEvent(true);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$7, reason: invalid class name */
    public class AnonymousClass7 {
        public AnonymousClass7() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$9, reason: invalid class name */
    public class AnonymousClass9 {
        public AnonymousClass9() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AnimationEvent {
        public static final AnimationFilter[] FILTERS;
        public static final int[] LENGTHS;
        public final int animationType;
        public final AnimationFilter filter;
        public boolean headsUpFromBottom;
        public final long length;
        public final ExpandableView mChangingView;
        public View viewAfterChangingView;

        static {
            AnimationFilter animationFilter = new AnimationFilter();
            animationFilter.animateAlpha = true;
            animationFilter.animateHeight = true;
            animationFilter.animateTopInset = true;
            animationFilter.animateY = true;
            animationFilter.animateZ = true;
            animationFilter.hasDelays = true;
            AnimationFilter animationFilter2 = new AnimationFilter();
            animationFilter2.animateAlpha = true;
            animationFilter2.animateHeight = true;
            animationFilter2.animateTopInset = true;
            animationFilter2.animateY = true;
            animationFilter2.animateZ = true;
            animationFilter2.hasDelays = true;
            AnimationFilter animationFilter3 = new AnimationFilter();
            animationFilter3.animateHeight = true;
            animationFilter3.animateTopInset = true;
            animationFilter3.animateY = true;
            animationFilter3.animateZ = true;
            animationFilter3.hasDelays = true;
            AnimationFilter animationFilter4 = new AnimationFilter();
            animationFilter4.animateHeight = true;
            animationFilter4.animateTopInset = true;
            animationFilter4.animateY = true;
            animationFilter4.animateZ = true;
            AnimationFilter animationFilter5 = new AnimationFilter();
            animationFilter5.animateZ = true;
            AnimationFilter animationFilter6 = new AnimationFilter();
            AnimationFilter animationFilter7 = new AnimationFilter();
            animationFilter7.animateAlpha = true;
            animationFilter7.animateHeight = true;
            animationFilter7.animateTopInset = true;
            animationFilter7.animateY = true;
            animationFilter7.animateZ = true;
            AnimationFilter animationFilter8 = new AnimationFilter();
            animationFilter8.animateHeight = true;
            animationFilter8.animateTopInset = true;
            animationFilter8.animateY = true;
            animationFilter8.animateZ = true;
            animationFilter8.animateAlpha = true;
            animationFilter8.hasDelays = true;
            AnimationFilter animationFilter9 = new AnimationFilter();
            animationFilter9.animateHideSensitive = true;
            AnimationFilter animationFilter10 = new AnimationFilter();
            animationFilter10.animateHeight = true;
            animationFilter10.animateTopInset = true;
            animationFilter10.animateY = true;
            animationFilter10.animateZ = true;
            AnimationFilter animationFilter11 = new AnimationFilter();
            ArraySet arraySet = animationFilter11.mAnimatedProperties;
            animationFilter11.animateAlpha = true;
            animationFilter11.animateHeight = true;
            animationFilter11.animateTopInset = true;
            animationFilter11.animateY = true;
            arraySet.add(View.SCALE_X);
            arraySet.add(View.SCALE_Y);
            animationFilter11.animateZ = true;
            AnimationFilter animationFilter12 = new AnimationFilter();
            animationFilter12.animateHeight = true;
            animationFilter12.animateTopInset = true;
            animationFilter12.animateY = true;
            animationFilter12.animateZ = true;
            AnimationFilter animationFilter13 = new AnimationFilter();
            animationFilter13.animateHeight = true;
            animationFilter13.animateTopInset = true;
            animationFilter13.animateY = true;
            animationFilter13.animateZ = true;
            animationFilter13.hasDelays = true;
            AnimationFilter animationFilter14 = new AnimationFilter();
            animationFilter14.animateHeight = true;
            animationFilter14.animateTopInset = true;
            animationFilter14.animateY = true;
            animationFilter14.animateZ = true;
            animationFilter14.hasDelays = true;
            AnimationFilter animationFilter15 = new AnimationFilter();
            animationFilter15.animateHeight = true;
            animationFilter15.animateTopInset = true;
            animationFilter15.animateY = true;
            animationFilter15.animateZ = true;
            AnimationFilter animationFilter16 = new AnimationFilter();
            animationFilter16.animateAlpha = true;
            animationFilter16.animateHideSensitive = true;
            animationFilter16.animateHeight = true;
            animationFilter16.animateTopInset = true;
            animationFilter16.animateY = true;
            animationFilter16.animateZ = true;
            AnimationFilter animationFilter17 = new AnimationFilter();
            animationFilter17.animateHeight = true;
            animationFilter17.animateTopInset = true;
            animationFilter17.animateY = true;
            animationFilter17.animateZ = true;
            animationFilter17.hasDelays = true;
            AnimationFilter animationFilter18 = new AnimationFilter();
            animationFilter18.animateHeight = true;
            animationFilter18.animateTopInset = true;
            animationFilter18.animateY = true;
            animationFilter18.animateZ = true;
            animationFilter18.hasDelays = true;
            FILTERS = new AnimationFilter[]{animationFilter, animationFilter2, animationFilter3, animationFilter4, animationFilter5, animationFilter6, animationFilter7, animationFilter8, animationFilter9, animationFilter10, animationFilter11, animationFilter12, animationFilter13, animationFilter14, animationFilter15, animationFilter16, animationFilter17, animationFilter18};
            LENGTHS = new int[]{464, 464, 360, 360, 220, 220, 360, 448, 360, 360, 360, 400, 400, 400, 360, 360, 400, 400};
        }

        public AnimationEvent(ExpandableView expandableView, int i) {
            this(expandableView, i, LENGTHS[i]);
        }

        public AnimationEvent(ExpandableView expandableView, int i, AnimationFilter animationFilter) {
            this(expandableView, i, LENGTHS[i], animationFilter);
        }

        public AnimationEvent(ExpandableView expandableView, int i, long j) {
            this(expandableView, i, j, FILTERS[i]);
        }

        public AnimationEvent(ExpandableView expandableView, int i, long j, AnimationFilter animationFilter) {
            AnimationUtils.currentAnimationTimeMillis();
            this.mChangingView = expandableView;
            this.animationType = i;
            this.length = j;
            this.filter = animationFilter;
        }
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$8] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$12] */
    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$1] */
    /* JADX WARN: Type inference failed for: r5v10, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$3] */
    /* JADX WARN: Type inference failed for: r5v11, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$4] */
    /* JADX WARN: Type inference failed for: r5v13, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$5] */
    /* JADX WARN: Type inference failed for: r5v14, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$6] */
    /* JADX WARN: Type inference failed for: r5v5, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$2] */
    /* JADX WARN: Type inference failed for: r5v8, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda2] */
    public NotificationStackScrollLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, 0);
        this.mShadeNeedsToClose = false;
        this.mCurrentStackHeight = Integer.MAX_VALUE;
        this.mQuickPanelLogger = new QuickPanelLogger("NSSL");
        this.mQuickPanelLogBuilder = new StringBuilder();
        this.mActivePointerId = -1;
        this.mImeInset = 0;
        this.mScrollViewFields = new ScrollViewFields();
        this.mChildrenToAddAnimated = new HashSet();
        this.mAddedHeadsUpChildren = new ArrayList();
        this.mChildrenToRemoveAnimated = new ArrayList();
        this.mChildrenChangingPositions = new ArrayList();
        this.mFromMoreCardAdditions = new HashSet();
        this.mAnimationEvents = new ArrayList();
        this.mSwipedOutViews = new ArrayList();
        int i = SceneContainerFlag.$r8$clinit;
        this.mAnimationsEnabled = false;
        this.mSpeedBumpIndexDirty = true;
        new ListenerSet();
        this.mHeadsUpHeightChangedListeners = new ListenerSet();
        this.mIsExpanded = true;
        this.mChildrenUpdater = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.1
            /* JADX WARN: Code restructure failed: missing block: B:181:0x0346, code lost:
            
                if (r12 == false) goto L171;
             */
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final boolean onPreDraw() {
                /*
                    Method dump skipped, instructions count: 2454
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.AnonymousClass1.onPreDraw():boolean");
            }
        };
        this.mTempInt2 = new int[2];
        this.mAnimationFinishedRunnables = new HashSet();
        this.mClearTransientViewsWhenFinished = new HashSet();
        this.mHeadsUpChangeAnimations = new HashMap();
        this.mTmpHeadsUpChangeAnimations = new ArrayList();
        this.mRunningAnimationUpdater = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.2
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                NotificationStackScrollLayout.this.mShelf.updateAppearance();
                return true;
            }
        };
        this.mTmpSortedChildren = new ArrayList();
        this.mQsHeaderBound = new Rect();
        this.mShadowUpdater = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda2
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                boolean z = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
                notificationStackScrollLayout.updateViewShadows();
                return true;
            }
        };
        this.mViewPositionComparator = new NotificationStackScrollLayout$$ExternalSyntheticLambda3();
        this.mOutlineProvider = new ViewOutlineProvider() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.3
            @Override // android.view.ViewOutlineProvider
            public final void getOutline(View view, Outline outline) {
                if (!NotificationStackScrollLayout.this.mAmbientState.isHiddenAtAll()) {
                    ViewOutlineProvider.BACKGROUND.getOutline(view, outline);
                    return;
                }
                NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                float interpolation = notificationStackScrollLayout.mHideXInterpolator.getInterpolation((1.0f - notificationStackScrollLayout.mLinearHideAmount) * notificationStackScrollLayout.mBackgroundXFactor);
                NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayout.this;
                Rect rect = notificationStackScrollLayout2.mBackgroundAnimationRect;
                int i2 = notificationStackScrollLayout2.mCornerRadius;
                outline.setRoundRect(rect, MathUtils.lerp(i2 / 2.0f, i2, interpolation));
                outline.setAlpha(1.0f - NotificationStackScrollLayout.this.mAmbientState.mHideAmount);
            }
        };
        this.collectVisibleLocationsCallable = new Callable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.4
            @Override // java.util.concurrent.Callable
            public final Object call() {
                boolean z = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
                NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                notificationStackScrollLayout.getClass();
                HashMap hashMap = new HashMap();
                int childCount = notificationStackScrollLayout.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    ExpandableView expandableView = (ExpandableView) notificationStackScrollLayout.getChildAt(i2);
                    if (expandableView instanceof ExpandableNotificationRow) {
                        ((ExpandableNotificationRow) expandableView).collectVisibleLocations(hashMap);
                    }
                }
                return hashMap;
            }
        };
        this.mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        this.mDisplayListener = new DisplayManager.DisplayListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.5
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayChanged(int i2) {
                if (i2 != 0) {
                    NotificationStackScrollLayout.this.mDisplayState = 0;
                    return;
                }
                Display display = NotificationStackScrollLayout.this.mDisplayManager.getDisplay(i2);
                NotificationStackScrollLayout.this.mDisplayState = display.getState();
                RecyclerView$$ExternalSyntheticOutline0.m(NotificationStackScrollLayout.this.mDisplayState, "StackScroller", new StringBuilder("onDisplayChanged for predraw skip to "));
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayAdded(int i2) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayRemoved(int i2) {
            }
        };
        this.mInsetsCallback = new WindowInsetsAnimation.Callback(1) { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.6
            @Override // android.view.WindowInsetsAnimation.Callback
            public final void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
                NotificationStackScrollLayout.this.mIsInsetAnimationRunning = false;
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public final void onPrepare(WindowInsetsAnimation windowInsetsAnimation) {
                NotificationStackScrollLayout.this.mIsInsetAnimationRunning = true;
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public final WindowInsets onProgress(WindowInsets windowInsets, List list) {
                NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                boolean z = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
                notificationStackScrollLayout.updateImeInset(windowInsets);
                return windowInsets;
            }
        };
        this.mInterpolatedHideAmount = 0.0f;
        this.mLinearHideAmount = 0.0f;
        this.mBackgroundXFactor = 1.0f;
        this.mMaxDisplayedNotifications = -1;
        this.mKeyguardBottomPadding = -1.0f;
        this.mClipRect = new Rect();
        this.mHeadsUpGoingAwayAnimationsAllowed = true;
        this.mReflingAndAnimateScroll = new NotificationStackScrollLayout$$ExternalSyntheticLambda4(this, 0);
        this.mBackgroundAnimationRect = new Rect();
        this.mExpandedHeightListeners = new ArrayList();
        this.mTmpRect = new Rect();
        this.mHideXInterpolator = Interpolators.FAST_OUT_SLOW_IN;
        this.mRoundedClipPath = new Path();
        this.mNegativeRoundedClipPath = new Path();
        this.mBlurNode = new RenderNode("BlurNode");
        this.mBlurEffect = null;
        this.mLaunchedNotificationClipPath = new Path();
        this.mShouldUseRoundedRectClipping = false;
        this.mRoundedClipCornerRadii = new float[8];
        this.mAnimateStackYForContentHeightChange = false;
        this.mLaunchedNotificationRadii = new float[8];
        this.mDismissUsingRowTranslationX = true;
        this.mShouldSkipTopPaddingAnimationAfterFold = false;
        this.mSplitShadeStateController = null;
        this.mSwipeCancelledView = new HashSet();
        this.mOnChildHeightChangedListener = new AnonymousClass7();
        this.mOnChildSensitivityChangedListener = new NotificationEntry.OnSensitivityChangedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.8
            @Override // com.android.systemui.statusbar.notification.collection.NotificationEntry.OnSensitivityChangedListener
            public final void onSensitivityChanged(NotificationEntry notificationEntry) {
                NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                if (notificationStackScrollLayout.mAnimationsEnabled) {
                    notificationStackScrollLayout.mHideSensitiveNeedsAnimation = true;
                    notificationStackScrollLayout.requestChildrenUpdate();
                }
            }
        };
        this.mScrollAdapter = new AnonymousClass9();
        this.mSuppressChildrenMeasureAndLayout = false;
        this.mHeadsUpCallback = new AnonymousClass10();
        this.mExpandHelperCallback = new AnonymousClass11();
        this.mSystemUIWidgetCallback = new SystemUIWidgetCallback() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.12
            @Override // com.android.systemui.widget.SystemUIWidgetCallback
            public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
                if ((j & 512) != 0) {
                    NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                    int childCount = notificationStackScrollLayout.getChildCount();
                    for (int i2 = 0; i2 < childCount; i2++) {
                        View childAt = notificationStackScrollLayout.getChildAt(i2);
                        if (childAt instanceof ExpandableNotificationRow) {
                            NotificationStackScrollLayout.updateNotification((ExpandableNotificationRow) childAt);
                        }
                    }
                }
            }
        };
        this.mVislbeNSSLWhileMediaExpanded = false;
        Resources resources = getResources();
        FeatureFlags featureFlags = (FeatureFlags) Dependency.sDependency.getDependencyInner(FeatureFlags.class);
        Flags flags = Flags.INSTANCE;
        featureFlags.getClass();
        this.mDebugRemoveAnimation = true;
        NotificationSectionsManager notificationSectionsManager = (NotificationSectionsManager) Dependency.sDependency.getDependencyInner(NotificationSectionsManager.class);
        this.mSectionsManager = notificationSectionsManager;
        if (notificationSectionsManager.initialized) {
            throw new IllegalStateException("NotificationSectionsManager already initialized");
        }
        notificationSectionsManager.initialized = true;
        notificationSectionsManager.parent = this;
        notificationSectionsManager.reinflateViews();
        ((ConfigurationControllerImpl) notificationSectionsManager.configurationController).addCallback(notificationSectionsManager.configurationListener);
        PriorityBucket.Companion.getClass();
        int[] iArr = {0, 1, 13, 2, 3, 5, 6, 7, 14, 8, 9, 10, 11, 15, 16, 17, 18, 19, 20};
        ArrayList arrayList = new ArrayList(19);
        for (int i2 = 0; i2 < 19; i2++) {
            arrayList.add(new NotificationSection(iArr[i2]));
        }
        this.mSections = (NotificationSection[]) arrayList.toArray(new NotificationSection[0]);
        AmbientState ambientState = (AmbientState) Dependency.sDependency.getDependencyInner(AmbientState.class);
        this.mAmbientState = ambientState;
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.notification_min_height);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.notification_max_height);
        resources.getDimensionPixelSize(R.dimen.nssl_split_shade_min_content_height);
        ExpandHelper expandHelper = new ExpandHelper(getContext(), this.mExpandHelperCallback, dimensionPixelSize, dimensionPixelSize2);
        this.mExpandHelper = expandHelper;
        expandHelper.mEventSource = this;
        expandHelper.mScrollAdapter = this.mScrollAdapter;
        int i3 = NotificationsHunSharedAnimationValues.$r8$clinit;
        this.mStackScrollAlgorithm = new StackScrollAlgorithm(context, this, null);
        this.mStateAnimator = new StackStateAnimator(context, this, null);
        setOutlineProvider(this.mOutlineProvider);
        int i4 = SceneContainerFlag.$r8$clinit;
        setWillNotDraw(true);
        this.mGroupMembershipManager = (GroupMembershipManager) Dependency.sDependency.getDependencyInner(GroupMembershipManager.class);
        this.mGroupExpansionManager = (GroupExpansionManager) Dependency.sDependency.getDependencyInner(GroupExpansionManager.class);
        setImportantForAccessibility(1);
        setWindowInsetsAnimationCallback(this.mInsetsCallback);
        FullExpansionPanelNotiAlphaController fullExpansionPanelNotiAlphaController = (FullExpansionPanelNotiAlphaController) Dependency.sDependency.getDependencyInner(FullExpansionPanelNotiAlphaController.class);
        this.mFullExpansionPanelNotiAlphaController = fullExpansionPanelNotiAlphaController;
        fullExpansionPanelNotiAlphaController.mStackScrollLayout = this;
        TouchAnimator.Builder builder = new TouchAnimator.Builder();
        builder.addFloat(fullExpansionPanelNotiAlphaController.mStackScrollLayout, "alpha", 1.0f, 0.0f);
        builder.mStartDelay = 0.0f;
        builder.mEndDelay = 0.5f;
        builder.mInterpolator = fullExpansionPanelNotiAlphaController.mSineInOut33;
        fullExpansionPanelNotiAlphaController.mStackScrollerAlphaAnimator = builder.build();
        TouchAnimator.Builder builder2 = new TouchAnimator.Builder();
        builder2.addFloat(fullExpansionPanelNotiAlphaController.mStackScrollLayout, "alpha", 1.0f, 0.0f);
        builder2.mEndDelay = 0.8f;
        builder2.build();
        Handler handler = (Handler) Dependency.sDependency.getDependencyInner(Dependency.BG_HANDLER);
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        this.mDisplayManager = displayManager;
        displayManager.registerDisplayListener(this.mDisplayListener, handler);
        this.mKeyguardFoldController = (KeyguardFoldController) Dependency.sDependency.getDependencyInner(KeyguardFoldController.class);
        PanelScreenShotLogger panelScreenShotLogger = PanelScreenShotLogger.INSTANCE;
        panelScreenShotLogger.addLogProvider("AmbientState", ambientState);
        panelScreenShotLogger.addLogProvider("StackScroller", this);
        ((WallpaperEventNotifier) Dependency.sDependency.getDependencyInner(WallpaperEventNotifier.class)).registerCallback(false, this.mSystemUIWidgetCallback, 512L);
        this.mDeviceWidth = DeviceState.getDisplayWidth(getContext());
    }

    public static String getApplicationNameFromPackage(ZenModeController zenModeController, String str) {
        PackageManager packageManager = ((ZenModeControllerImpl) zenModeController).mContext.getPackageManager();
        try {
            return packageManager.getApplicationLabel(packageManager.getApplicationInfo(str, 0)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0052, code lost:
    
        if (r1 == 20) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0056, code lost:
    
        r5 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0058, code lost:
    
        if (r1 < 20) goto L28;
     */
    /* JADX WARN: Removed duplicated region for block: B:22:0x005d A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean includeChildInClearAll(com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r5, int r6) {
        /*
            r0 = 0
            if (r5 == 0) goto L15
            boolean r1 = r5.areGutsExposed()
            if (r1 != 0) goto L15
            boolean r1 = r5.hasFinishedInitialization()
            if (r1 != 0) goto L10
            goto L15
        L10:
            boolean r1 = r5.canViewBeCleared()
            goto L16
        L15:
            r1 = r0
        L16:
            if (r1 == 0) goto L5e
            int r1 = com.android.systemui.statusbar.notification.shared.NotificationBundleUi.$r8$clinit
            com.android.systemui.statusbar.notification.collection.NotificationEntry r1 = r5.getEntryLegacy()
            int r1 = r1.mBucket
            r2 = 1
            if (r6 == 0) goto L54
            r3 = 20
            if (r6 == r2) goto L58
            r4 = 2
            if (r6 == r4) goto L52
            r1 = 3
            if (r6 != r1) goto L46
            com.android.systemui.noticenter.NotiCenterPlugin r6 = com.android.systemui.noticenter.NotiCenterPlugin.INSTANCE
            com.android.systemui.statusbar.notification.collection.NotificationEntry r5 = r5.mEntry
            android.service.notification.StatusBarNotification r5 = r5.mSbn
            java.lang.String r5 = r5.getPackageName()
            r6.getClass()
            java.util.HashSet r6 = com.android.systemui.noticenter.NotiCenterPlugin.noclearAppList
            if (r6 == 0) goto L43
            boolean r5 = r6.contains(r5)
            goto L44
        L43:
            r5 = r0
        L44:
            r5 = r5 ^ r2
            goto L5b
        L46:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "Unknown selection: "
            java.lang.String r6 = android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(r6, r0)
            r5.<init>(r6)
            throw r5
        L52:
            if (r1 != r3) goto L56
        L54:
            r5 = r2
            goto L5b
        L56:
            r5 = r0
            goto L5b
        L58:
            if (r1 >= r3) goto L56
            goto L54
        L5b:
            if (r5 == 0) goto L5e
            return r2
        L5e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.includeChildInClearAll(com.android.systemui.statusbar.notification.row.ExpandableNotificationRow, int):boolean");
    }

    public static boolean isPinnedHeadsUp(View view) {
        if (!(view instanceof ExpandableNotificationRow)) {
            return false;
        }
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
        return expandableNotificationRow.mIsHeadsUp && expandableNotificationRow.mPinnedStatus.isPinned();
    }

    public static boolean isTouchInsideView(View view, float f, float f2) {
        return view != null && view.getX() < f && f < view.getX() + ((float) view.getWidth()) && view.getY() < f2 && f2 < view.getY() + ((float) view.getHeight());
    }

    public static void updateNotification(ExpandableNotificationRow expandableNotificationRow) {
        ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).updateAllTextViewColors(expandableNotificationRow, expandableNotificationRow.mDimmed);
        if (expandableNotificationRow.mIsSummaryWithChildren) {
            NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
            int notificationChildCount = notificationChildrenContainer.getNotificationChildCount();
            for (int i = 0; i < notificationChildCount; i++) {
                updateNotification((ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(i));
            }
        }
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [T, java.lang.String] */
    public final void addTransientView(View view, int i) {
        NotificationStackScrollLogger notificationStackScrollLogger = this.mLogger;
        if (notificationStackScrollLogger != null && (view instanceof ExpandableNotificationRow)) {
            String str = ((ExpandableNotificationRow) view).mLoggingKey;
            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ref$ObjectRef.element = Log.getStackTraceString(new Throwable());
            LogLevel logLevel = LogLevel.INFO;
            NotificationStackScrollLogger$$ExternalSyntheticLambda3 notificationStackScrollLogger$$ExternalSyntheticLambda3 = new NotificationStackScrollLogger$$ExternalSyntheticLambda3(ref$ObjectRef, 1);
            LogBuffer logBuffer = notificationStackScrollLogger.notificationRenderBuffer;
            LogMessage obtain = logBuffer.obtain("NotificationStackScroll", logLevel, notificationStackScrollLogger$$ExternalSyntheticLambda3, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = str;
            logMessageImpl.int1 = i;
            logBuffer.commit(obtain);
        }
        super.addTransientView(view, i);
    }

    public final void animateScroll() {
        if (!this.mScroller.computeScrollOffset()) {
            Runnable runnable = this.mFinishScrollingCallback;
            if (runnable != null) {
                runnable.run();
                return;
            }
            return;
        }
        int ownScrollY = getOwnScrollY();
        int currY = this.mScroller.getCurrY();
        if (ownScrollY != currY) {
            int scrollRange = getScrollRange();
            if ((currY < 0 && ownScrollY >= 0) || (currY > scrollRange && ownScrollY <= scrollRange)) {
                float currVelocity = this.mScroller.getCurrVelocity();
                if (currVelocity >= this.mMinimumVelocity) {
                    this.mMaxOverScroll = (Math.abs(currVelocity) / 1000.0f) * this.mOverflingDistance;
                }
            }
            customOverScrollBy(currY - ownScrollY, ownScrollY, scrollRange, (int) this.mMaxOverScroll);
        }
        postOnAnimation(this.mReflingAndAnimateScroll);
    }

    public final void applyCurrentState() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ExpandableView expandableView = (ExpandableView) getChildAt(i);
            ExpandableViewState expandableViewState = expandableView.mViewState;
            if (!expandableViewState.gone) {
                expandableViewState.applyToView(expandableView);
            }
        }
        if (!SecPanelSplitHelper.isEnabled() ? !this.mQsFullScreen : getPanelSplitHelper().isShadeState()) {
            int i2 = NotificationsLiveDataStoreRefactor.$r8$clinit;
            NotificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1$callback$1 notificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1$callback$1 = this.mLocationsChangedListener;
            if (notificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1$callback$1 != null) {
                ((ChannelCoroutine) notificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1$callback$1.$$this$conflatedCallbackFlow).mo3456trySendJP2dKIU(this.collectVisibleLocationsCallable);
            }
        }
        runAnimationFinishedRunnables();
        setAnimationRunning(false);
        updateViewShadows();
    }

    public final float calculateAppearFraction(float f) {
        if (!isHeadsUpTransition()) {
            return this.mAmbientState.mExpansionFraction;
        }
        int i = SceneContainerFlag.$r8$clinit;
        float appearEndPosition = getAppearEndPosition();
        float appearStartPosition = getAppearStartPosition();
        return MathUtils.constrain((f - appearStartPosition) / (appearEndPosition - appearStartPosition), -1.0f, 1.0f);
    }

    public final float calculateGapHeight(ExpandableView expandableView, ExpandableView expandableView2) {
        if (!onKeyguard() || this.mAmbientState.isNeedsToExpandLocksNoti()) {
            StackScrollAlgorithm stackScrollAlgorithm = this.mStackScrollAlgorithm;
            NotificationSectionsManager notificationSectionsManager = this.mSectionsManager;
            AmbientState ambientState = this.mAmbientState;
            float f = ambientState.mFractionToShade;
            boolean isOnKeyguard$1 = ambientState.isOnKeyguard$1();
            stackScrollAlgorithm.getClass();
            if (expandableView != null && StackScrollAlgorithm.getPreviousGroupExpandFraction(expandableView) > 0.0f) {
                return NotificationUtils.interpolate(0.0f, stackScrollAlgorithm.mMaxGroupExpandedBottomGap, StackScrollAlgorithm.getPreviousGroupExpandFraction(expandableView)) + (stackScrollAlgorithm.childNeedsGapHeight(notificationSectionsManager, expandableView2, expandableView) ? stackScrollAlgorithm.getGapForLocation(f, isOnKeyguard$1) : 0.0f);
            }
            if (stackScrollAlgorithm.childNeedsGapHeight(notificationSectionsManager, expandableView2, expandableView)) {
                return stackScrollAlgorithm.getGapForLocation(f, isOnKeyguard$1);
            }
        }
        return 0.0f;
    }

    @Override // android.view.View
    public final void cancelLongPress() {
        this.mSwipeHelper.cancelLongPress();
    }

    public final void changeViewPosition(ExpandableView expandableView, int i) {
        NotificationShelf notificationShelf;
        Assert.isMainThread();
        if (this.mChangePositionInProgress) {
            throw new IllegalStateException("Reentrant call to changeViewPosition");
        }
        int indexOfChild = indexOfChild(expandableView);
        boolean z = false;
        if (indexOfChild == -1) {
            if ((expandableView instanceof ExpandableNotificationRow) && expandableView.mTransientContainer != null) {
                z = true;
            }
            StringBuilder sb = new StringBuilder("Attempting to re-position ");
            sb.append(z ? "transient" : "");
            sb.append(" view {");
            sb.append(expandableView);
            sb.append("}");
            Log.e("StackScroller", sb.toString());
            if (!(expandableView instanceof NotificationShelf) || (notificationShelf = this.mShelf) == null) {
                return;
            }
            addView(notificationShelf);
            return;
        }
        if (expandableView == null || expandableView.getParent() != this || indexOfChild == i) {
            return;
        }
        this.mChangePositionInProgress = true;
        expandableView.mChangingPosition = true;
        removeView(expandableView);
        addView(expandableView, i);
        expandableView.mChangingPosition = false;
        this.mChangePositionInProgress = false;
        if (this.mIsExpanded && this.mAnimationsEnabled && expandableView.getVisibility() != 8) {
            this.mChildrenChangingPositions.add(expandableView);
            this.mNeedsAnimation = true;
        }
    }

    public final void clampScrollPosition() {
        int i = SceneContainerFlag.$r8$clinit;
        int scrollRange = getScrollRange();
        if (scrollRange >= getOwnScrollY() || this.mAmbientState.mClearAllInProgress) {
            return;
        }
        setOwnScrollY(scrollRange, scrollRange < getScrollAmountToScrollBoundary() && this.mAnimateStackYForContentHeightChange);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void clearChildFocus(View view) {
        super.clearChildFocus(view);
        if (this.mForcedScroll == view) {
            this.mForcedScroll = null;
        }
    }

    public final void clearHeadsUpDisappearRunning() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) childAt;
                expandableNotificationRow.setHeadsUpAnimatingAway(false);
                if (expandableNotificationRow.mIsSummaryWithChildren) {
                    ArrayList arrayList = (ArrayList) expandableNotificationRow.getAttachedChildren();
                    int size = arrayList.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj = arrayList.get(i2);
                        i2++;
                        ((ExpandableNotificationRow) obj).setHeadsUpAnimatingAway(false);
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void clearNotifications(final int i, boolean z) {
        int childCount = getChildCount();
        ArrayList arrayList = new ArrayList(childCount);
        boolean z2 = false;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            boolean z3 = childAt instanceof SectionHeaderView;
            if (childAt instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) childAt;
                if (isVisible(expandableNotificationRow) && includeChildInClearAll(expandableNotificationRow, i)) {
                    arrayList.add(expandableNotificationRow);
                }
                List attachedChildren = expandableNotificationRow.getAttachedChildren();
                if (isVisible(expandableNotificationRow) && attachedChildren != null && expandableNotificationRow.mChildrenExpanded) {
                    ArrayList arrayList2 = (ArrayList) expandableNotificationRow.getAttachedChildren();
                    int size = arrayList2.size();
                    int i3 = 0;
                    while (i3 < size) {
                        Object obj = arrayList2.get(i3);
                        i3++;
                        ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) obj;
                        if (isVisible(expandableNotificationRow2) && includeChildInClearAll(expandableNotificationRow2, i)) {
                            arrayList.add(expandableNotificationRow2);
                        }
                    }
                }
            }
        }
        int childCount2 = getChildCount();
        final ArrayList arrayList3 = new ArrayList(childCount2);
        for (int i4 = 0; i4 < childCount2; i4++) {
            View childAt2 = getChildAt(i4);
            if (childAt2 instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) childAt2;
                if (includeChildInClearAll(expandableNotificationRow3, i)) {
                    arrayList3.add(expandableNotificationRow3);
                }
                List attachedChildren2 = expandableNotificationRow3.getAttachedChildren();
                if (isVisible(expandableNotificationRow3) && attachedChildren2 != null) {
                    ArrayList arrayList4 = (ArrayList) attachedChildren2;
                    int size2 = arrayList4.size();
                    int i5 = 0;
                    while (i5 < size2) {
                        Object obj2 = arrayList4.get(i5);
                        i5++;
                        ExpandableNotificationRow expandableNotificationRow4 = (ExpandableNotificationRow) obj2;
                        if (includeChildInClearAll(expandableNotificationRow3, i)) {
                            arrayList3.add(expandableNotificationRow4);
                        }
                    }
                }
            }
        }
        NotificationStackScrollLayoutController$$ExternalSyntheticLambda4 notificationStackScrollLayoutController$$ExternalSyntheticLambda4 = this.mClearAllListener;
        if (notificationStackScrollLayoutController$$ExternalSyntheticLambda4 != null) {
            ((NotificationStackScrollLayoutController) notificationStackScrollLayoutController$$ExternalSyntheticLambda4.f$0).mUiEventLogger.log(i == 0 ? NotificationStackScrollLayoutController.NotificationPanelEvent.DISMISS_ALL_NOTIFICATIONS_PANEL : i == 2 ? NotificationStackScrollLayoutController.NotificationPanelEvent.DISMISS_SILENT_NOTIFICATIONS_PANEL : NotificationStackScrollLayoutController.NotificationPanelEvent.INVALID);
        }
        Consumer consumer = new Consumer() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj3) {
                final NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                final ArrayList arrayList5 = arrayList3;
                final int i6 = i;
                boolean z4 = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
                notificationStackScrollLayout.getClass();
                if (((Boolean) obj3).booleanValue()) {
                    notificationStackScrollLayout.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayout.this;
                            ArrayList arrayList6 = arrayList5;
                            int i7 = i6;
                            boolean z5 = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
                            notificationStackScrollLayout2.onClearAllAnimationsEnd(i7, arrayList6);
                        }
                    });
                } else {
                    notificationStackScrollLayout.onClearAllAnimationsEnd(i6, arrayList5);
                }
            }
        };
        if (arrayList.isEmpty()) {
            consumer.accept(Boolean.TRUE);
            if (this.mIsExpanded && z) {
                this.mCollapseShadeDelayedWhenNoViewsToAnimateAwayRunnable.run();
                return;
            }
            return;
        }
        boolean z4 = true;
        setClearAllInProgress(true);
        this.mShadeNeedsToClose = z;
        InteractionJankMonitor.getInstance().begin(this, 62);
        int size3 = arrayList.size();
        int i6 = size3 - 1;
        int i7 = 60;
        int i8 = 0;
        while (i6 >= 0) {
            View view = (View) arrayList.get(i6);
            NotificationStackScrollLayout$$ExternalSyntheticLambda5 notificationStackScrollLayout$$ExternalSyntheticLambda5 = i6 == 0 ? consumer : 0;
            if (view instanceof SectionHeaderView) {
                ((StackScrollerDecorView) view).setContentVisible(z2, z4, notificationStackScrollLayout$$ExternalSyntheticLambda5);
            } else {
                this.mSwipeHelper.dismissChild(view, 0.0f, notificationStackScrollLayout$$ExternalSyntheticLambda5, i8, true, 200L, true);
            }
            i7 = Math.max(30, i7 - 5);
            i8 += i7;
            i6--;
            z2 = false;
            z4 = true;
        }
        SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_OPENED, SystemUIAnalytics.EID_QPNE_CLEAR_NOTIFICATION, "type", i == 0 ? SystemUIAnalytics.QPNE_VID_COVER_ALL : SystemUIAnalytics.QPNE_VID_SILENT, SystemUIAnalytics.QPNE_KEY_COUNT, Integer.toString(size3));
    }

    public final void clearTemporaryViewsInGroup(ViewGroup viewGroup, String str) {
        while (viewGroup != null && viewGroup.getTransientViewCount() != 0) {
            View transientView = viewGroup.getTransientView(0);
            viewGroup.removeTransientView(transientView);
            if (transientView instanceof ExpandableView) {
                ((ExpandableView) transientView).mTransientContainer = null;
                if (transientView instanceof ExpandableNotificationRow) {
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) transientView;
                    NotificationStackScrollLogger notificationStackScrollLogger = this.mLogger;
                    if (notificationStackScrollLogger != null) {
                        String str2 = expandableNotificationRow.mLoggingKey;
                        LogLevel logLevel = LogLevel.INFO;
                        NotificationStackScrollLogger$$ExternalSyntheticLambda0 notificationStackScrollLogger$$ExternalSyntheticLambda0 = new NotificationStackScrollLogger$$ExternalSyntheticLambda0(8);
                        LogBuffer logBuffer = notificationStackScrollLogger.notificationRenderBuffer;
                        LogMessage obtain = logBuffer.obtain("NotificationStackScroll", logLevel, notificationStackScrollLogger$$ExternalSyntheticLambda0, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                        logMessageImpl.str1 = str2;
                        logMessageImpl.str2 = str;
                        logBuffer.commit(obtain);
                    }
                }
            }
        }
    }

    public final void customOverScrollBy(int i, int i2, int i3, int i4) {
        boolean z;
        float f;
        boolean z2;
        int i5 = i2 + i;
        int i6 = -i4;
        int i7 = i4 + i3;
        if (i5 > i7) {
            z = true;
            i5 = i7;
        } else if (i5 < i6) {
            i5 = i6;
            z = true;
        } else {
            z = false;
        }
        if (this.mScroller.isFinished()) {
            setOwnScrollY(i5);
            return;
        }
        setOwnScrollY(i5);
        if (!z) {
            float currentOverScrollAmount = getCurrentOverScrollAmount(true);
            if (getOwnScrollY() < 0) {
                notifyOverscrollTopListener(-getOwnScrollY(), isRubberbanded(true));
                return;
            } else {
                notifyOverscrollTopListener(currentOverScrollAmount, isRubberbanded(true));
                return;
            }
        }
        int scrollRange = getScrollRange();
        boolean z3 = getOwnScrollY() <= 0;
        boolean z4 = getOwnScrollY() >= scrollRange;
        if (z3 || z4) {
            if (z3) {
                f = -getOwnScrollY();
                setOwnScrollY(0);
                this.mDontReportNextOverScroll = true;
                z2 = true;
            } else {
                float ownScrollY = getOwnScrollY() - scrollRange;
                setOwnScrollY(scrollRange);
                f = ownScrollY;
                z2 = false;
            }
            setOverScrollAmount(f, z2, false, true);
            setOverScrollAmount(0.0f, z2, true, true);
            this.mScroller.forceFinished(true);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        if (SecPanelSplitHelper.isEnabled()) {
            super.dispatchDraw(canvas);
            return;
        }
        if (this.mBlurEffect == null) {
            if (!this.mLaunchingNotification && this.mShouldUseRoundedRectClipping) {
                canvas.clipPath(this.mRoundedClipPath);
            }
            super.dispatchDraw(canvas);
            return;
        }
        this.mBlurNode.setPosition(0, 0, canvas.getWidth(), canvas.getHeight());
        this.mBlurNode.setRenderEffect(this.mBlurEffect);
        super.dispatchDraw(this.mBlurNode.beginRecording());
        this.mBlurNode.endRecording();
        int save = canvas.save();
        if (this.mShouldUseRoundedRectClipping) {
            canvas.clipPath(this.mRoundedClipPath);
        }
        canvas.drawRenderNode(this.mBlurNode);
        canvas.restoreToCount(save);
        for (int i = 0; i < getChildCount(); i++) {
            ExpandableView expandableView = (ExpandableView) getChildAt(i);
            if (expandableView != null ? expandableView.isHeadsUpState() : false) {
                super.drawChild(canvas, expandableView, getDrawingTime());
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:111:0x0142, code lost:
    
        r9 = scrollAmountForKeyboardFocus(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0146, code lost:
    
        if (r9 == 0) goto L113;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x014f, code lost:
    
        if ((r8.mOwnScrollY + r9) <= getScrollRange()) goto L108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x0151, code lost:
    
        r8.mOwnScrollY = getScrollRange();
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x015f, code lost:
    
        if (r8.mAnimationsEnabled == false) goto L112;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x0161, code lost:
    
        r8.mNeedsAnimation = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x0163, code lost:
    
        requestChildrenUpdate();
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x0158, code lost:
    
        r8.mOwnScrollY += r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x0166, code lost:
    
        r3.requestFocus();
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0169, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0071, code lost:
    
        r3.requestFocus();
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0074, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x00e9, code lost:
    
        if ((r3 instanceof com.android.systemui.statusbar.NotificationShelf) != false) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x00eb, code lost:
    
        r9 = scrollAmountForKeyboardFocus(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x00ef, code lost:
    
        if (r9 == 0) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x00f8, code lost:
    
        if ((r8.mOwnScrollY + r9) <= getScrollRange()) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x00fa, code lost:
    
        r8.mOwnScrollY = getScrollRange();
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0108, code lost:
    
        if (r8.mAnimationsEnabled == false) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x010a, code lost:
    
        r8.mNeedsAnimation = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x010c, code lost:
    
        requestChildrenUpdate();
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0101, code lost:
    
        r8.mOwnScrollY += r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x010f, code lost:
    
        r3.requestFocus();
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0112, code lost:
    
        return true;
     */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean dispatchKeyEvent(android.view.KeyEvent r9) {
        /*
            Method dump skipped, instructions count: 367
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.dispatchKeyEvent(android.view.KeyEvent):boolean");
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int i = SceneContainerFlag.$r8$clinit;
        boolean dispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        TouchLogger.Companion.getClass();
        TouchLogger.Companion.logDispatchTouch(motionEvent, "StackScroller", dispatchTouchEvent);
        return dispatchTouchEvent;
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j) {
        boolean z = this.mShouldUseRoundedRectClipping;
        if (this.mBlurEffect != null) {
            if (view instanceof ExpandableView ? ((ExpandableView) view).isHeadsUpState() : false) {
                return false;
            }
            return super.drawChild(canvas, view, j);
        }
        if (!this.mLaunchingNotification || !z) {
            return super.drawChild(canvas, view, j);
        }
        canvas.save();
        ExpandableView expandableView = (ExpandableView) view;
        Path path = (expandableView.isExpandAnimationRunning() || expandableView.hasExpandingChild()) ? null : this.mRoundedClipPath;
        if (this.mShouldUseRoundedRectClipping && path != null) {
            canvas.clipPath(path);
        }
        boolean drawChild = super.drawChild(canvas, view, j);
        canvas.restore();
        return drawChild;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, final String[] strArr) {
        final IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        final long elapsedRealtime = SystemClock.elapsedRealtime();
        asIndenting.println("Internal state:");
        DumpUtilsKt.withIncreasedIndent(asIndenting, new Runnable(asIndenting, elapsedRealtime, strArr) { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda6
            public final /* synthetic */ IndentingPrintWriter f$1;
            public final /* synthetic */ long f$2;

            @Override // java.lang.Runnable
            public final void run() {
                NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                IndentingPrintWriter indentingPrintWriter = this.f$1;
                long j = this.f$2;
                DumpUtilsKt.println(indentingPrintWriter, "pulsing", Boolean.valueOf(notificationStackScrollLayout.mPulsing));
                DumpUtilsKt.println(indentingPrintWriter, "expanded", Boolean.valueOf(notificationStackScrollLayout.mIsExpanded));
                DumpUtilsKt.println(indentingPrintWriter, "headsUpPinned", Boolean.valueOf(notificationStackScrollLayout.mInHeadsUpPinnedMode));
                DumpUtilsKt.println(indentingPrintWriter, "roundedRectClipping", Boolean.valueOf(notificationStackScrollLayout.mShouldUseRoundedRectClipping));
                Boolean bool = Boolean.FALSE;
                DumpUtilsKt.println(indentingPrintWriter, "negativeRoundedRectClipping", bool);
                DumpUtilsKt.println(indentingPrintWriter, "qsClipDismiss", Boolean.valueOf(notificationStackScrollLayout.mDismissUsingRowTranslationX));
                DumpUtilsKt.println(indentingPrintWriter, "visibility", DumpUtilsKt.visibilityString(notificationStackScrollLayout.getVisibility()));
                DumpUtilsKt.println(indentingPrintWriter, "alpha", Float.valueOf(notificationStackScrollLayout.getAlpha()));
                DumpUtilsKt.println(indentingPrintWriter, "suppressChildrenMeasureLayout", Boolean.valueOf(notificationStackScrollLayout.mSuppressChildrenMeasureAndLayout));
                DumpUtilsKt.println(indentingPrintWriter, "scrollY", Integer.valueOf(notificationStackScrollLayout.mAmbientState.mScrollY));
                DumpUtilsKt.println(indentingPrintWriter, "showShelfOnly", Boolean.valueOf(notificationStackScrollLayout.mShouldShowShelfOnly));
                DumpUtilsKt.println(indentingPrintWriter, "hideAmount", Float.valueOf(notificationStackScrollLayout.mAmbientState.mHideAmount));
                DumpUtilsKt.println(indentingPrintWriter, "ambientStateSwipingUp", Boolean.valueOf(notificationStackScrollLayout.mAmbientState.mIsSwipingUp));
                DumpUtilsKt.println(indentingPrintWriter, "maxDisplayedNotifications", Integer.valueOf(notificationStackScrollLayout.mMaxDisplayedNotifications));
                DumpUtilsKt.println(indentingPrintWriter, "intrinsicPadding", Integer.valueOf(notificationStackScrollLayout.mIntrinsicPadding));
                DumpUtilsKt.println(indentingPrintWriter, "bottomPadding", Integer.valueOf(notificationStackScrollLayout.mBottomPadding));
                indentingPrintWriter.append("roundedRectClipping{l=").print(notificationStackScrollLayout.mRoundedRectClippingLeft);
                indentingPrintWriter.append(" t=").print(notificationStackScrollLayout.mRoundedRectClippingTop);
                indentingPrintWriter.append(" r=").print(notificationStackScrollLayout.mRoundedRectClippingRight);
                indentingPrintWriter.append(" b=").print(notificationStackScrollLayout.mRoundedRectClippingBottom);
                indentingPrintWriter.append(" +y=").print(0);
                indentingPrintWriter.append("} topRadius=").print(notificationStackScrollLayout.mRoundedClipCornerRadii[0]);
                indentingPrintWriter.append(" bottomRadius=").println(notificationStackScrollLayout.mRoundedClipCornerRadii[4]);
                DumpUtilsKt.println(indentingPrintWriter, "requestedClipBounds", notificationStackScrollLayout.mRequestedClipBounds);
                DumpUtilsKt.println(indentingPrintWriter, "isClipped", Boolean.valueOf(notificationStackScrollLayout.mIsClipped));
                DumpUtilsKt.println(indentingPrintWriter, "translationX", Float.valueOf(notificationStackScrollLayout.getTranslationX()));
                DumpUtilsKt.println(indentingPrintWriter, "translationY", Float.valueOf(notificationStackScrollLayout.getTranslationY()));
                DumpUtilsKt.println(indentingPrintWriter, "translationZ", Float.valueOf(notificationStackScrollLayout.getTranslationZ()));
                DumpUtilsKt.println(indentingPrintWriter, "skinnyNotifsInLandscape", Boolean.valueOf(notificationStackScrollLayout.mSkinnyNotifsInLandscape));
                DumpUtilsKt.println(indentingPrintWriter, "minimumPaddings", Integer.valueOf(notificationStackScrollLayout.mMinimumPaddings));
                DumpUtilsKt.println(indentingPrintWriter, "qsTilePadding", Integer.valueOf(notificationStackScrollLayout.mQsTilePadding));
                DumpUtilsKt.println(indentingPrintWriter, "sidePaddings", Integer.valueOf(notificationStackScrollLayout.mSidePaddings));
                DumpUtilsKt.println(indentingPrintWriter, "elapsedRealtime", Long.valueOf(j));
                DumpUtilsKt.println(indentingPrintWriter, "lastInitView", notificationStackScrollLayout.mLastInitViewDumpString);
                DumpUtilsKt.println(indentingPrintWriter, "lastInitViewElapsedRealtime", Long.valueOf(notificationStackScrollLayout.mLastInitViewElapsedRealtime));
                DumpUtilsKt.println(indentingPrintWriter, "lastInitViewMillisAgo", Long.valueOf(j - notificationStackScrollLayout.mLastInitViewElapsedRealtime));
                DumpUtilsKt.println(indentingPrintWriter, "shouldUseSplitNotificationShade", bool);
                DumpUtilsKt.println(indentingPrintWriter, "lastUpdateSidePadding", notificationStackScrollLayout.mLastUpdateSidePaddingDumpString);
                DumpUtilsKt.println(indentingPrintWriter, "lastUpdateSidePaddingElapsedRealtime", Long.valueOf(notificationStackScrollLayout.mLastUpdateSidePaddingElapsedRealtime));
                DumpUtilsKt.println(indentingPrintWriter, "lastUpdateSidePaddingMillisAgo", Long.valueOf(j - notificationStackScrollLayout.mLastUpdateSidePaddingElapsedRealtime));
                DumpUtilsKt.println(indentingPrintWriter, "isSmallLandscapeLockscreenEnabled", bool);
                indentingPrintWriter.println("NotificationStackSizeCalculator saveSpaceOnLockscreen=" + notificationStackScrollLayout.mNotificationStackSizeCalculator.saveSpaceOnLockscreen);
                indentingPrintWriter.println("NotificationStackSizeCalculator limitLockScreenToOneImportant=false");
                ScrollViewFields scrollViewFields = notificationStackScrollLayout.mScrollViewFields;
                scrollViewFields.getClass();
                indentingPrintWriter.append("StackViewStates").println(":");
                indentingPrintWriter.increaseIndent();
                try {
                    DumpUtilsKt.println(indentingPrintWriter, "scrimClippingShape", null);
                    DumpUtilsKt.println(indentingPrintWriter, "negativeClippingShape", null);
                    DumpUtilsKt.println(indentingPrintWriter, "scrollState", scrollViewFields.scrollState);
                    indentingPrintWriter.decreaseIndent();
                    int i = SceneContainerFlag.$r8$clinit;
                    RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                    DumpUtilsKt.println(indentingPrintWriter, "intrinsicContentHeight", Float.valueOf(notificationStackScrollLayout.mIntrinsicContentHeight));
                    DumpUtilsKt.println(indentingPrintWriter, "contentHeight", Integer.valueOf(notificationStackScrollLayout.mContentHeight));
                    DumpUtilsKt.println(indentingPrintWriter, "topPadding", Integer.valueOf(notificationStackScrollLayout.getTopPadding()));
                    DumpUtilsKt.println(indentingPrintWriter, "maxTopPadding", Integer.valueOf(notificationStackScrollLayout.mMaxTopPadding));
                    DumpUtilsKt.println(indentingPrintWriter, "qsExpandFraction", Float.valueOf(notificationStackScrollLayout.getQsExpansionFraction$1()));
                } catch (Throwable th) {
                    indentingPrintWriter.decreaseIndent();
                    throw th;
                }
            }
        });
        asIndenting.println();
        asIndenting.println("Contents:");
        DumpUtilsKt.withIncreasedIndent(asIndenting, new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                PrintWriter printWriter2 = asIndenting;
                String[] strArr2 = strArr;
                boolean z = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
                int childCount = notificationStackScrollLayout.getChildCount();
                printWriter2.println("Number of children: " + childCount);
                printWriter2.println();
                for (int i = 0; i < childCount; i++) {
                    ((ExpandableView) notificationStackScrollLayout.getChildAt(i)).dump(printWriter2, strArr2);
                    printWriter2.println();
                }
                int transientViewCount = notificationStackScrollLayout.getTransientViewCount();
                printWriter2.println("Transient Views: " + transientViewCount);
                for (int i2 = 0; i2 < transientViewCount; i2++) {
                    ((ExpandableView) notificationStackScrollLayout.getTransientView(i2)).dump(printWriter2, strArr2);
                }
                NotificationSwipeHelper notificationSwipeHelper = notificationStackScrollLayout.mSwipeHelper;
                ExpandableView expandableView = notificationSwipeHelper.mIsSwiping ? notificationSwipeHelper.mTouchedView : null;
                printWriter2.println("Swiped view: " + expandableView);
                if (expandableView != null) {
                    expandableView.dump(printWriter2, strArr2);
                }
            }
        });
    }

    public final void endDrag() {
        setIsBeingDragged(false);
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        if (getCurrentOverScrollAmount(true) > 0.0f) {
            setOverScrollAmount(0.0f, true, true, true);
        }
        if (getCurrentOverScrollAmount(false) > 0.0f) {
            setOverScrollAmount(0.0f, false, true, true);
        }
    }

    public final void finalizeClearAllAnimation() {
        if (this.mAmbientState.mClearAllInProgress) {
            setClearAllInProgress(false);
            if (this.mShadeNeedsToClose) {
                this.mShadeNeedsToClose = false;
                if (this.mIsExpanded) {
                    for (int i = 0; i < getChildCount(); i++) {
                        View childAt = getChildAt(i);
                        if (childAt instanceof ExpandableNotificationRow) {
                            this.mClearAllFinishedWhilePanelExpandedRunnable.run();
                            return;
                        }
                    }
                    this.mCollapseShadeDelayedWhenNoViewsToAnimateAwayRunnable.run();
                }
            }
        }
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        StatusBarNotification statusBarNotification;
        ArrayList arrayList = new ArrayList();
        PanelScreenShotLogger.INSTANCE.getClass();
        PanelScreenShotLogger.addHeaderLine("NotificationStackScrollLayout", arrayList);
        PanelScreenShotLogger.addLogItem(arrayList, "alpha", Float.valueOf(getAlpha()));
        PanelScreenShotLogger.addLogItem(arrayList, "mOwnScrollY", Integer.valueOf(this.mOwnScrollY));
        PanelScreenShotLogger.addLogItem(arrayList, "getHeight", Integer.valueOf(getHeight()));
        PanelScreenShotLogger.addLogItem(arrayList, "mTopPaddingOverflow", Float.valueOf(this.mTopPaddingOverflow));
        PanelScreenShotLogger.addLogItem(arrayList, "mCurrentStackHeight", Integer.valueOf(this.mCurrentStackHeight));
        PanelScreenShotLogger.addLogItem(arrayList, "mExpandedHeight", Float.valueOf(this.mExpandedHeight));
        PanelScreenShotLogger.addLogItem(arrayList, "getAppearStartPosition", Float.valueOf(getAppearStartPosition()));
        PanelScreenShotLogger.addLogItem(arrayList, "getAppearEndPosition", Float.valueOf(getAppearEndPosition()));
        PanelScreenShotLogger.addLogItem(arrayList, "mExtraTopInsetForFullShadeTransition", Float.valueOf(this.mExtraTopInsetForFullShadeTransition));
        PanelScreenShotLogger.addLogItem(arrayList, "mIntrinsicPadding", Integer.valueOf(this.mIntrinsicPadding));
        PanelScreenShotLogger.addLogItem(arrayList, "mShouldShowShelfOnly", Boolean.valueOf(this.mShouldShowShelfOnly));
        PanelScreenShotLogger.addLogItem(arrayList, "getVisibility", Integer.valueOf(getVisibility()));
        String str = this.mLastGoneCallTrace;
        if (str == null) {
            str = "NULL";
        }
        PanelScreenShotLogger.addLogItem(arrayList, "mLastGoneCallTrace", str);
        String str2 = this.mLastInvisibleTrace;
        if (str2 == null) {
            str2 = "NULL";
        }
        PanelScreenShotLogger.addLogItem(arrayList, "mLastInvisibleTrace", str2);
        String str3 = this.mLastVisibleTrace;
        PanelScreenShotLogger.addLogItem(arrayList, "mLastVisibleTrace", str3 != null ? str3 : "NULL");
        PanelScreenShotLogger.addLogItem(arrayList, "appIconColor", Integer.toHexString(getContext().getColor(R.color.notification_app_icon_color)));
        arrayList.add("\n\n");
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) childAt;
                ExpandableViewState expandableViewState = expandableNotificationRow.mViewState;
                PanelScreenShotLogger panelScreenShotLogger = PanelScreenShotLogger.INSTANCE;
                String str4 = expandableNotificationRow.mLoggingKey;
                panelScreenShotLogger.getClass();
                PanelScreenShotLogger.addLogItem(arrayList, "key", str4);
                PanelScreenShotLogger.addLogItem(arrayList, "x", Float.valueOf(expandableNotificationRow.getX()));
                PanelScreenShotLogger.addLogItem(arrayList, "y", Float.valueOf(expandableNotificationRow.getY()));
                PanelScreenShotLogger.addLogItem(arrayList, "alpha", Float.valueOf(expandableNotificationRow.getAlpha()));
                PanelScreenShotLogger.addLogItem(arrayList, "visibility", Integer.valueOf(expandableNotificationRow.getVisibility()));
                PanelScreenShotLogger.addLogItem(arrayList, "intrinsicHeight", Integer.valueOf(expandableNotificationRow.getIntrinsicHeight()));
                PanelScreenShotLogger.addLogItem(arrayList, "clipTop", Integer.valueOf(expandableNotificationRow.mClipTopAmount));
                PanelScreenShotLogger.addLogItem(arrayList, "clipBottom", Integer.valueOf(expandableNotificationRow.mClipBottomAmount));
                PanelScreenShotLogger.addLogItem(arrayList, "removed", Boolean.FALSE);
                PanelScreenShotLogger.addLogItem(arrayList, "keepInParentForDismissAnimation", Boolean.valueOf(expandableNotificationRow.mKeepInParentForDismissAnimation));
                PanelScreenShotLogger.addLogItem(arrayList, "dismissed", Boolean.valueOf(expandableNotificationRow.mDismissed));
                NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
                if (notificationEntry != null && (statusBarNotification = notificationEntry.mSbn) != null && statusBarNotification.getNotification() != null) {
                    PanelScreenShotLogger.addLogItem(arrayList, "when", Long.valueOf(expandableNotificationRow.mEntry.mSbn.getNotification().when));
                    PanelScreenShotLogger.addLogItem(arrayList, "postTime", Long.valueOf(expandableNotificationRow.mEntry.mSbn.getPostTime()));
                }
                if (expandableViewState != null) {
                    PanelScreenShotLogger.addLogItem(arrayList, "location", Integer.valueOf(expandableViewState.location));
                    PanelScreenShotLogger.addLogItem(arrayList, "inShelf", Boolean.valueOf(expandableViewState.inShelf));
                    PanelScreenShotLogger.addLogItem(arrayList, "hideSensitive", Boolean.valueOf(expandableViewState.hideSensitive));
                    PanelScreenShotLogger.addLogItem(arrayList, "gone", Boolean.valueOf(expandableViewState.gone));
                }
                arrayList.addAll(expandableNotificationRow.gatherState());
                arrayList.add("\n");
            }
            if (childAt instanceof NotificationShelf) {
                NotificationShelf notificationShelf = (NotificationShelf) childAt;
                PanelScreenShotLogger.INSTANCE.getClass();
                PanelScreenShotLogger.addLogItem(arrayList, "SHELF", "NOTIFICATION_SHELF");
                PanelScreenShotLogger.addLogItem(arrayList, "x", Float.valueOf(notificationShelf.getX()));
                PanelScreenShotLogger.addLogItem(arrayList, "y", Float.valueOf(notificationShelf.getY()));
                PanelScreenShotLogger.addLogItem(arrayList, "alpha", Float.valueOf(notificationShelf.getAlpha()));
                PanelScreenShotLogger.addLogItem(arrayList, "visibility", Integer.valueOf(notificationShelf.getVisibility()));
                PanelScreenShotLogger.addLogItem(arrayList, "intrinsicHeight", Integer.valueOf(notificationShelf.getHeight()));
            }
        }
        return arrayList;
    }

    public final float getAppearEndPosition() {
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i2 = this.mAmbientState.mStackTopMargin;
        boolean areAnyNotificationsPresentValue = this.mController.mActiveNotificationsInteractor.getAreAnyNotificationsPresentValue();
        boolean z = NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW;
        if (!z ? this.mEmptyShadeView.getVisibility() == 8 && areAnyNotificationsPresentValue : areAnyNotificationsPresentValue) {
            i2 = (z || this.mEmptyShadeView.getVisibility() == 8) ? 0 : this.mEmptyShadeView.getHeight();
        } else if (isHeadsUpTransition() || (this.mInHeadsUpPinnedMode && !this.mAmbientState.mDozing)) {
            if (this.mShelf.getVisibility() != 8) {
                i2 += this.mShelf.getHeight() + this.mPaddingBetweenElements;
            }
            i2 += getPositionInLinearLayout(this.mAmbientState.getTrackedHeadsUpRow()) + getTopHeadsUpPinnedHeight();
        } else if (this.mShelf.getVisibility() != 8) {
            i2 += this.mShelf.getHeight();
        }
        return i2 + (onKeyguard() ? getTopPadding() : this.mIntrinsicPadding);
    }

    public final float getAppearStartPosition() {
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (!isHeadsUpTransition()) {
            return this.mShelf.getHeight() + this.mWaterfallTopInset;
        }
        return (this.mHeadsUpInset - this.mAmbientState.mStackTopMargin) + (getFirstVisibleSection() != null ? r0.mFirstVisibleChild.getPinnedHeadsUpHeight() : 0);
    }

    public final ExpandableView getChildAtPosition(float f, float f2, boolean z, boolean z2) {
        boolean z3;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ExpandableView expandableView = (ExpandableView) getChildAt(i);
            if (expandableView.getVisibility() == 0 && (!z2 || !(expandableView instanceof StackScrollerDecorView))) {
                float translationY = expandableView.getTranslationY();
                float max = Math.max(0, expandableView.mClipTopAmount) + translationY;
                float f3 = (expandableView.mActualHeight + translationY) - expandableView.mClipBottomAmount;
                int width = getWidth();
                if ((f3 - max >= this.mMinInteractionHeight || !z) && f2 >= max && f2 <= f3 && f >= 0 && f <= width) {
                    if (!(expandableView instanceof ExpandableNotificationRow)) {
                        return expandableView;
                    }
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
                    int i2 = NotificationBundleUi.$r8$clinit;
                    NotificationEntry entryLegacy = expandableNotificationRow.getEntryLegacy();
                    ExpandableNotificationRow expandableNotificationRow2 = this.mTopHeadsUpRow;
                    if (expandableNotificationRow2 != null) {
                        if (((GroupMembershipManagerImpl) this.mGroupMembershipManager).getGroupSummary(expandableNotificationRow2.getEntryLegacy()) == entryLegacy) {
                            z3 = true;
                            if (!this.mIsExpanded || !expandableNotificationRow.mIsHeadsUp || !expandableNotificationRow.mPinnedStatus.isPinned() || this.mTopHeadsUpRow == expandableNotificationRow || z3) {
                                return expandableNotificationRow.getViewAtPosition(f2 - translationY);
                            }
                        }
                    }
                    z3 = false;
                    if (!this.mIsExpanded) {
                    }
                    return expandableNotificationRow.getViewAtPosition(f2 - translationY);
                }
            }
        }
        return null;
    }

    public final ExpandableView getChildAtRawPosition(float f, float f2) {
        getLocationOnScreen(this.mTempInt2);
        int[] iArr = this.mTempInt2;
        return getChildAtPosition(f - iArr[0], f2 - iArr[1], true, true);
    }

    public final List getChildrenWithBackground() {
        ArrayList arrayList = new ArrayList();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ExpandableView expandableView = (ExpandableView) getChildAt(i);
            if (expandableView.getVisibility() != 8 && !(expandableView instanceof StackScrollerDecorView) && expandableView != this.mShelf) {
                arrayList.add(expandableView);
            }
        }
        return arrayList;
    }

    public final float getCurrentOverScrollAmount(boolean z) {
        AmbientState ambientState = this.mAmbientState;
        return z ? ambientState.mOverScrollTopAmount : ambientState.mOverScrollBottomAmount;
    }

    public final String getDndStatusText(ZenModeController zenModeController) {
        ZenModeConfig zenModeConfig;
        Uri uri;
        String str = "";
        if (zenModeController != null && (zenModeConfig = ((ZenModeControllerImpl) zenModeController).mConfig) != null) {
            ZenModeConfig.ZenRule zenRule = zenModeConfig.manualRule;
            if (zenRule != null && zenRule.conditionId == null) {
                String str2 = zenRule.enabler;
                return str2 != null ? ((ViewGroup) this).mContext.getString(R.string.sec_zen_mode_footer_by_app_name, getApplicationNameFromPackage(zenModeController, str2)) : ((ViewGroup) this).mContext.getString(R.string.zen_mode_settings_dnd_manual_indefinite);
            }
            if (zenRule != null && (uri = zenRule.conditionId) != null && ZenModeConfig.isValidCountdownConditionId(uri)) {
                long tryParseCountdownConditionId = ZenModeConfig.tryParseCountdownConditionId(zenModeConfig.manualRule.conditionId);
                boolean isToday = ZenModeConfig.isToday(tryParseCountdownConditionId);
                Context context = ((ViewGroup) this).mContext;
                CharSequence formattedTime = ZenModeConfig.getFormattedTime(context, tryParseCountdownConditionId, isToday, context.getUserId());
                return isToday ? ((ViewGroup) this).mContext.getString(R.string.sec_zen_mode_footer_until_time, formattedTime) : ((ViewGroup) this).mContext.getString(R.string.sec_zen_mode_footer_until_time_tomorrow, formattedTime);
            }
            ArrayMap arrayMap = zenModeConfig.automaticRules;
            if (arrayMap != null && !arrayMap.isEmpty()) {
                boolean z = true;
                String description = ZenModeConfig.getDescription(((ViewGroup) this).mContext, true, zenModeConfig, false);
                Iterator it = zenModeConfig.automaticRules.values().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    ZenModeConfig.ZenRule zenRule2 = (ZenModeConfig.ZenRule) it.next();
                    if (zenRule2.isAutomaticActive() && description != null && description.equals(zenRule2.name)) {
                        if (ZenModeConfig.isValidScheduleConditionId(zenRule2.conditionId)) {
                            long nextChangeTime = ZenModeConfig.toScheduleCalendar(zenRule2.conditionId).getNextChangeTime(System.currentTimeMillis());
                            boolean isToday2 = ZenModeConfig.isToday(nextChangeTime);
                            Context context2 = ((ViewGroup) this).mContext;
                            CharSequence formattedTime2 = ZenModeConfig.getFormattedTime(context2, nextChangeTime, isToday2, context2.getUserId());
                            str = isToday2 ? ((ViewGroup) this).mContext.getString(R.string.dnd_on_schedule_on_today_header, formattedTime2) : ((ViewGroup) this).mContext.getString(R.string.dnd_on_schedule_on_next_day_header, formattedTime2);
                        } else {
                            str = ((ViewGroup) this).mContext.getString(R.string.sec_zen_mode_footer_by_app_and_schedule_name, getApplicationNameFromPackage(zenModeController, zenRule2.pkg.equals(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG) ? "com.samsung.android.app.routines" : zenRule2.pkg), description);
                        }
                    }
                }
                if (!z) {
                    return ((ViewGroup) this).mContext.getString(R.string.zen_mode_settings_dnd_manual_indefinite);
                }
            }
        }
        return str;
    }

    public final View getFirstChildBelowTranlsationY(float f) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8 && childAt.getTranslationY() >= f) {
                return childAt;
            }
        }
        return null;
    }

    public final ExpandableView getFirstChildNotGoneInternal() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8 && childAt != this.mShelf) {
                return (ExpandableView) childAt;
            }
        }
        return null;
    }

    public final NotificationSection getFirstVisibleSection() {
        for (NotificationSection notificationSection : this.mSections) {
            if (notificationSection.mFirstVisibleChild != null) {
                return notificationSection;
            }
        }
        return null;
    }

    public final int getImeInset() {
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        return Math.max(0, this.mImeInset - ((getRootView().getHeight() - getHeight()) - getLocationOnScreen()[1]));
    }

    public boolean getIsBeingDragged() {
        return this.mIsBeingDragged;
    }

    public final NotificationSection getLastVisibleSection() {
        for (int length = this.mSections.length - 1; length >= 0; length--) {
            NotificationSection notificationSection = this.mSections[length];
            if (notificationSection.mLastVisibleChild != null) {
                return notificationSection;
            }
        }
        return null;
    }

    public final int getLayoutMinHeightInternal() {
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (!isHeadsUpTransition()) {
            if (this.mShelf.getVisibility() == 8) {
                return 0;
            }
            return this.mShelf.getHeight();
        }
        ExpandableNotificationRow trackedHeadsUpRow = this.mAmbientState.getTrackedHeadsUpRow();
        if (!trackedHeadsUpRow.isAboveShelf()) {
            return getTopHeadsUpPinnedHeight();
        }
        return getTopHeadsUpPinnedHeight() + ((int) MathUtils.lerp(0, getPositionInLinearLayout(trackedHeadsUpRow), this.mAmbientState.mAppearFraction));
    }

    public int getOwnScrollY() {
        int i = SceneContainerFlag.$r8$clinit;
        return this.mOwnScrollY;
    }

    public final SecPanelSplitHelper getPanelSplitHelper() {
        if (this.mPanelSplitHelper == null) {
            this.mPanelSplitHelper = (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
        }
        return this.mPanelSplitHelper;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:11:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x003d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getPositionInLinearLayout(android.view.View r14) {
        /*
            Method dump skipped, instructions count: 202
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.getPositionInLinearLayout(android.view.View):int");
    }

    public final float getQsExpansionFraction$1() {
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        return this.mQsExpansionFraction;
    }

    public final float getRubberBandFactor(boolean z) {
        if (!z) {
            return RUBBER_BAND_FACTOR_NORMAL;
        }
        if (this.mExpandedInThisMotion) {
            return 0.15f;
        }
        if (this.mIsExpansionChanging || this.mPanelTracking) {
            return 0.21f;
        }
        if (!this.mScrolledToTopOnFirstDown) {
            return RUBBER_BAND_FACTOR_NORMAL;
        }
        if (SecPanelSplitHelper.isEnabled() && getPanelSplitHelper().isShadeState()) {
            return RUBBER_BAND_FACTOR_NORMAL;
        }
        return 1.0f;
    }

    public final int getScrollAmountToScrollBoundary() {
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        return getTopPadding() - ((ShadeHeaderController) Dependency.sDependency.getDependencyInner(ShadeHeaderController.class)).header.getHeight();
    }

    public final int getScrollRange() {
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i2 = this.mContentHeight;
        if (!this.mIsExpanded && this.mInHeadsUpPinnedMode) {
            i2 = this.mHeadsUpInset + getTopHeadsUpPinnedHeight();
        }
        int max = Math.max(0, i2 - this.mMaxLayoutHeight);
        int imeInset = getImeInset();
        int min = Math.min(imeInset, Math.max(0, i2 - (getHeight() - imeInset))) + max;
        return (this.mInHeadsUpPinnedMode || min <= 0) ? min : Math.max(getScrollAmountToScrollBoundary(), min);
    }

    public final int getTopHeadsUpPinnedHeight() {
        ExpandableNotificationRow expandableNotificationRow = this.mTopHeadsUpRow;
        if (expandableNotificationRow == null) {
            return 0;
        }
        int i = NotificationBundleUi.$r8$clinit;
        if (expandableNotificationRow.isChildInGroup()) {
            NotificationEntry groupSummary = ((GroupMembershipManagerImpl) this.mGroupMembershipManager).getGroupSummary(expandableNotificationRow.getEntryLegacy());
            if (groupSummary != null) {
                expandableNotificationRow = groupSummary.row;
            }
        }
        return expandableNotificationRow.getPinnedHeadsUpHeight(true);
    }

    public final int getTopPadding() {
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        return this.mAmbientState.getTopPadding();
    }

    public final float getTotalTranslationLength(View view) {
        if (!this.mDismissUsingRowTranslationX) {
            return view.getMeasuredWidth();
        }
        float measuredWidth = view.getMeasuredWidth();
        int measuredWidth2 = getMeasuredWidth();
        int i = this.mDeviceWidth;
        if (i != measuredWidth2) {
            return measuredWidth2 + ((i - measuredWidth) / 2.0f);
        }
        float f = measuredWidth2;
        return f - ((f - measuredWidth) / 2.0f);
    }

    public final float getTouchSlop$2(MotionEvent motionEvent) {
        return motionEvent.getClassification() == 1 ? this.mTouchSlop * this.mSlopMultiplier : this.mTouchSlop;
    }

    public final void handleEmptySpaceClick(MotionEvent motionEvent) {
        int i = SceneContainerFlag.$r8$clinit;
        boolean isBelowLastNotification = isBelowLastNotification(this.mInitialTouchX, this.mInitialTouchY);
        int i2 = this.mStatusBarState;
        boolean z = this.mTouchIsClick;
        NotificationStackScrollLogger notificationStackScrollLogger = this.mLogger;
        if (notificationStackScrollLogger != null) {
            String actionToString = MotionEvent.actionToString(motionEvent.getActionMasked());
            LogLevel logLevel = LogLevel.DEBUG;
            NotificationStackScrollLogger$$ExternalSyntheticLambda0 notificationStackScrollLogger$$ExternalSyntheticLambda0 = new NotificationStackScrollLogger$$ExternalSyntheticLambda0(1);
            LogBuffer logBuffer = notificationStackScrollLogger.shadeLogBuffer;
            LogMessage obtain = logBuffer.obtain("NotificationStackScroll", logLevel, notificationStackScrollLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.int1 = i2;
            logMessageImpl.bool1 = z;
            logMessageImpl.bool2 = isBelowLastNotification;
            logMessageImpl.str1 = actionToString;
            logBuffer.commit(obtain);
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 1) {
            if (actionMasked != 2) {
                NotificationStackScrollLogger notificationStackScrollLogger2 = this.mLogger;
                if (notificationStackScrollLogger2 == null) {
                    return;
                }
                LogBuffer.log$default(notificationStackScrollLogger2.shadeLogBuffer, "NotificationStackScroll", LogLevel.DEBUG, "handleEmptySpaceClick: MotionEvent ignored");
                return;
            }
            float touchSlop$2 = getTouchSlop$2(motionEvent);
            if (this.mTouchIsClick) {
                if (Math.abs(motionEvent.getY() - this.mInitialTouchY) > touchSlop$2 || Math.abs(motionEvent.getX() - this.mInitialTouchX) > touchSlop$2) {
                    this.mTouchIsClick = false;
                    return;
                }
                return;
            }
            return;
        }
        if (!this.mStateAnimator.mAnimatorSet.isEmpty()) {
            Log.d("StackScroller", "onEmptySpaceClicked is ignored by notification Animating..");
            return;
        }
        if (this.mStatusBarState != 1 && this.mTouchIsClick && isBelowLastNotification(this.mInitialTouchX, this.mInitialTouchY)) {
            NotificationStackScrollLogger notificationStackScrollLogger3 = this.mLogger;
            if (notificationStackScrollLogger3 != null) {
                LogBuffer.log$default(notificationStackScrollLogger3.shadeLogBuffer, "NotificationStackScroll", LogLevel.DEBUG, "handleEmptySpaceClick: touch event propagated further");
            }
            NotificationPanelViewController$$ExternalSyntheticLambda0 notificationPanelViewController$$ExternalSyntheticLambda0 = this.mOnEmptySpaceClickListener;
            float f = this.mInitialTouchX;
            float f2 = this.mInitialTouchY;
            notificationPanelViewController$$ExternalSyntheticLambda0.getClass();
            Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
            notificationPanelViewController$$ExternalSyntheticLambda0.f$0.onEmptySpaceClick(f, f2);
        }
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return !this.mForceNoOverlappingRendering && super.hasOverlappingRendering();
    }

    public final void inflateDndView() {
        DndStatusView dndStatusView = this.mDndStatusView;
        int i = 0;
        DndStatusView dndStatusView2 = (DndStatusView) LayoutInflater.from(((ViewGroup) this).mContext).inflate(R.layout.status_bar_notification_dnd_status, (ViewGroup) this, false);
        View findViewById = dndStatusView2.findViewById(R.id.notification_dnd_status_text_icon_container);
        if (findViewById != null) {
            findViewById.setOnClickListener(new NotificationStackScrollLayout$$ExternalSyntheticLambda1(this, 0));
        }
        dndStatusView2.setVisible(dndStatusView != null && dndStatusView.mIsVisible, false);
        dndStatusView2.setDndTextAndIcon(getDndStatusText(this.mZenModeController));
        dndStatusView2.setSecondaryVisible(dndStatusView != null && dndStatusView.mIsVisible);
        View view = this.mDndStatusView;
        if (view != null) {
            i = indexOfChild(view);
            removeView(this.mDndStatusView);
        }
        this.mDndStatusView = dndStatusView2;
        addView(dndStatusView2, i);
    }

    public final void inflateEmptyShadeView() {
        int i;
        if (NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW) {
            return;
        }
        int i2 = ModesEmptyShadeFix.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        EmptyShadeView emptyShadeView = this.mEmptyShadeView;
        EmptyShadeView emptyShadeView2 = (EmptyShadeView) LayoutInflater.from(((ViewGroup) this).mContext).inflate(R.layout.status_bar_no_notifications, (ViewGroup) this, false);
        View view = this.mEmptyShadeView;
        if (view != null) {
            i = indexOfChild(view);
            removeView(this.mEmptyShadeView);
        } else {
            i = -1;
        }
        this.mEmptyShadeView = emptyShadeView2;
        addView(emptyShadeView2, i);
        emptyShadeView2.setVisible(emptyShadeView != null && emptyShadeView.mIsVisible, false);
        updateEmptyShadeViewResources(emptyShadeView == null ? R.string.empty_shade_text : emptyShadeView.mTextId, emptyShadeView == null ? 0 : emptyShadeView.mFooterText, emptyShadeView != null ? emptyShadeView.mFooterIcon : 0);
    }

    public final void initView(Context context, NotificationSwipeHelper notificationSwipeHelper, NotificationStackSizeCalculator notificationStackSizeCalculator) {
        this.mScroller = new OverScroller(getContext());
        this.mSwipeHelper = notificationSwipeHelper;
        this.mNotificationStackSizeCalculator = notificationStackSizeCalculator;
        setDescendantFocusability(262144);
        setClipChildren(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mSlopMultiplier = viewConfiguration.getScaledAmbiguousGestureMultiplier();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mOverflingDistance = viewConfiguration.getScaledOverflingDistance();
        Resources resources = context.getResources();
        NotificationSwipeHelper notificationSwipeHelper2 = this.mSwipeHelper;
        notificationSwipeHelper2.getClass();
        float f = resources.getDisplayMetrics().density;
        notificationSwipeHelper2.mDensityScale = f;
        NotificationStackScrollLayoutController.this.mMagneticNotificationRowManager.onDensityChange(f);
        notificationSwipeHelper2.mFalsingThreshold = resources.getDimensionPixelSize(R.dimen.swipe_helper_falsing_threshold);
        boolean z = resources.getBoolean(R.bool.is_small_screen_landscape);
        boolean z2 = resources.getBoolean(R.bool.config_skinnyNotifsInLandscape);
        this.mSkinnyNotifsInLandscape = z2;
        StringBuilder m = EmergencyButtonController$$ExternalSyntheticOutline0.m("mIsSmallLandscapeLockscreenEnabled=false isSmallScreenLandscape=", " useSmallLandscapeLockscreenResources=", " skinnyNotifsInLandscape=", z, false);
        m.append(z2);
        m.append(" mSkinnyNotifsInLandscape=");
        m.append(this.mSkinnyNotifsInLandscape);
        this.mLastInitViewDumpString = m.toString();
        this.mLastInitViewElapsedRealtime = SystemClock.elapsedRealtime();
        resources.getDimensionPixelSize(R.dimen.notification_section_divider_height);
        this.mStackScrollAlgorithm.initView(context);
        this.mStateAnimator.initView(context);
        AmbientState ambientState = this.mAmbientState;
        ambientState.getClass();
        ambientState.mZDistanceBetweenElements = Math.max(1, context.getResources().getDimensionPixelSize(R.dimen.z_distance_between_notifications));
        this.mPaddingBetweenElements = Math.max(1, resources.getDimensionPixelSize(R.dimen.notification_divider_height));
        this.mMinTopOverScrollToEscape = resources.getDimensionPixelSize(R.dimen.min_top_overscroll_to_qs);
        this.mStatusBarHeight = SystemBarUtils.getStatusBarHeight(((ViewGroup) this).mContext);
        this.mBottomPadding = resources.getDimensionPixelSize(R.dimen.notification_panel_padding_bottom);
        this.mMinimumPaddings = resources.getDimensionPixelSize(R.dimen.notification_scrim_top_padding);
        this.mQsTilePadding = resources.getDimensionPixelOffset(R.dimen.qs_tile_margin_horizontal);
        this.mSidePaddings = this.mMinimumPaddings;
        this.mMinInteractionHeight = resources.getDimensionPixelSize(R.dimen.notification_min_interaction_height);
        this.mCornerRadius = resources.getDimensionPixelSize(R.dimen.notification_corner_radius);
        this.mHeadsUpInset = resources.getDimensionPixelSize(R.dimen.heads_up_status_bar_padding) + this.mStatusBarHeight;
        SystemBarUtils.getQuickQsOffsetHeight(((ViewGroup) this).mContext);
        this.mYDiff = (int) (resources.getDimensionPixelSize(R.dimen.quick_qs_common_bottom_margin) * 1.3f);
    }

    public final boolean isBelowLastNotification(float f, float f2) {
        boolean z;
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (this.mPreviousTouchX == f && this.mPreviousTouchY == f2) {
            z = false;
        } else {
            this.mPreviousTouchX = f;
            this.mPreviousTouchY = f2;
            z = true;
        }
        int childCount = getChildCount();
        if (this.mStatusBarState == 1 || (this.mQsMinHeight <= f2 && !(this.mShelf.getVisibility() == 0 && isTouchInsideView(this.mShelf, f, f2)))) {
            int i2 = childCount - 1;
            while (true) {
                if (i2 < 0) {
                    if (onKeyguard() && z) {
                        StringBuilder sb = new StringBuilder("isBelowLastNotification ");
                        sb.append(f2 > ((float) getTopPadding()) + this.mAmbientState.mStackTranslation);
                        sb.append(" :  of touchY : ");
                        sb.append(f2);
                        sb.append(" , topPadding : ");
                        sb.append(getTopPadding());
                        sb.append(" , getStackTranslation : ");
                        SeslColorSpectrumView$$ExternalSyntheticOutline0.m(this.mAmbientState.mStackTranslation, "StackScroller", sb);
                    }
                    if (f2 > getTopPadding() + this.mAmbientState.mStackTranslation) {
                        break;
                    }
                } else {
                    ExpandableView expandableView = (ExpandableView) getChildAt(i2);
                    if (expandableView.getVisibility() != 8) {
                        if (expandableView == this.mEmptyShadeView) {
                            if (z) {
                                Log.d("StackScroller", "isBelowLastNotification true : EmptyShadeView is Visible");
                                return true;
                            }
                        } else if (this.mStatusBarState == 1 || expandableView != this.mShelf) {
                            float y = expandableView.getY();
                            if (y <= f2) {
                                boolean z2 = f2 > (((float) expandableView.mActualHeight) + y) - ((float) expandableView.mClipBottomAmount);
                                if (expandableView == this.mEmptyShadeView) {
                                    break;
                                }
                                if (!z2) {
                                    if (onKeyguard() && z) {
                                        if (expandableView instanceof ExpandableNotificationRow) {
                                            StringBuilder sb2 = new StringBuilder("isBelowLastNotification false : !belowChild , ");
                                            sb2.append((y + expandableView.mActualHeight) - expandableView.mClipBottomAmount);
                                            sb2.append(" of ");
                                            ExifInterface$$ExternalSyntheticOutline0.m(sb2, ((ExpandableNotificationRow) expandableView).mLoggingKey, "StackScroller");
                                            return false;
                                        }
                                        Log.d("StackScroller", "isBelowLastNotification false : !belowChild , " + ((y + expandableView.mActualHeight) - expandableView.mClipBottomAmount) + " of " + expandableView.getClass() + " Visibility: " + expandableView.getVisibility());
                                        return false;
                                    }
                                }
                            } else if (onKeyguard() && z) {
                                if (expandableView instanceof ExpandableNotificationRow) {
                                    StringBuilder sb3 = new StringBuilder("isBelowLastNotification false : child top , ");
                                    sb3.append(y);
                                    sb3.append(" of ");
                                    ExifInterface$$ExternalSyntheticOutline0.m(sb3, ((ExpandableNotificationRow) expandableView).mLoggingKey, "StackScroller");
                                    return false;
                                }
                                Log.d("StackScroller", "isBelowLastNotification false : child top , " + y + " of " + expandableView.getClass() + " Visibility: " + expandableView.getVisibility());
                                return false;
                            }
                        }
                    }
                    i2--;
                }
            }
            return true;
        }
        if (z) {
            if (this.mQsMinHeight > f2) {
                Log.d("StackScroller", "isBelowLastNotification false : mQsMinHeight > touchY");
                return false;
            }
            if (this.mShelf.getVisibility() == 0 && isTouchInsideView(this.mShelf, f, f2)) {
                Log.d("StackScroller", "isBelowLastNotification false : Shelf is visible, touch inside of shelf");
                return false;
            }
        }
        return false;
    }

    public final boolean isFullySwipedOut(ExpandableView expandableView) {
        return Math.abs(expandableView.getTranslation()) >= Math.abs(getTotalTranslationLength(expandableView));
    }

    public final boolean isHeadsUpTransition() {
        return this.mAmbientState.getTrackedHeadsUpRow() != null;
    }

    public boolean isInScrollableRegion(MotionEvent motionEvent) {
        int i = SceneContainerFlag.$r8$clinit;
        return !isInsideQsHeader(motionEvent);
    }

    public final boolean isInsideQsHeader(MotionEvent motionEvent) {
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i2 = QSComposeFragment.$r8$clinit;
        if (this.mQsHeader == null) {
            return false;
        }
        if (this.mAmbientState.mNotificationScrimTop > motionEvent.getY()) {
            return true;
        }
        this.mQsHeader.getBoundsOnScreen(this.mQsHeaderBound);
        this.mQsHeaderBound.offsetTo(Math.round((motionEvent.getRawX() - motionEvent.getX()) + this.mQsHeader.getLeft()), Math.round(motionEvent.getRawY() - motionEvent.getY()));
        return this.mQsHeaderBound.contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
    }

    public final boolean isRubberbanded(boolean z) {
        if (!z || this.mExpandedInThisMotion || this.mIsExpansionChanging || this.mPanelTracking || !this.mScrolledToTopOnFirstDown) {
            return true;
        }
        return SecPanelSplitHelper.isEnabled() && getPanelSplitHelper().isShadeState();
    }

    public boolean isVisible(View view) {
        boolean clipBounds = view.getClipBounds(this.mTmpRect);
        if (view.getVisibility() == 0) {
            return !clipBounds || this.mTmpRect.height() > 0;
        }
        return false;
    }

    public final void logHunAnimationSkipped(ExpandableNotificationRow expandableNotificationRow, String str) {
        NotificationStackScrollLogger notificationStackScrollLogger = this.mLogger;
        if (notificationStackScrollLogger == null) {
            return;
        }
        String str2 = expandableNotificationRow.mLoggingKey;
        LogLevel logLevel = LogLevel.INFO;
        NotificationStackScrollLogger$$ExternalSyntheticLambda0 notificationStackScrollLogger$$ExternalSyntheticLambda0 = new NotificationStackScrollLogger$$ExternalSyntheticLambda0(4);
        LogBuffer logBuffer = notificationStackScrollLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotificationStackScroll", logLevel, notificationStackScrollLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str2;
        logMessageImpl.str2 = str;
        logBuffer.commit(obtain);
    }

    public final void notifyAppearChangedListeners() {
        float saturate;
        float f;
        if (this.mKeyguardBypassEnabled && onKeyguard()) {
            float f2 = this.mAmbientState.mPulseHeight;
            f = 0.0f;
            if (f2 == 100000.0f) {
                f2 = 0.0f;
            }
            int i = SceneContainerFlag.$r8$clinit;
            saturate = MathUtils.smoothStep(0.0f, this.mIntrinsicPadding, f2);
            float f3 = this.mAmbientState.mPulseHeight;
            if (f3 != 100000.0f) {
                f = f3;
            }
        } else {
            saturate = MathUtils.saturate(calculateAppearFraction(this.mExpandedHeight));
            f = this.mExpandedHeight;
        }
        if (saturate == this.mLastSentAppear && f == this.mLastSentExpandedHeight) {
            return;
        }
        this.mLastSentAppear = saturate;
        this.mLastSentExpandedHeight = f;
        for (int i2 = 0; i2 < this.mExpandedHeightListeners.size(); i2++) {
            ((BiConsumer) this.mExpandedHeightListeners.get(i2)).accept(Float.valueOf(f), Float.valueOf(saturate));
        }
    }

    public final void notifyHeightChangeListener(ExpandableView expandableView, boolean z) {
        NotificationPanelViewController.NsslHeightChangedListener nsslHeightChangedListener = this.mOnHeightChangedListener;
        if (nsslHeightChangedListener != null) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            if (expandableView != null || !notificationPanelViewController.mQsController.getExpanded()) {
                if (z && notificationPanelViewController.mInterpolatedDarkAmount == 0.0f) {
                    notificationPanelViewController.mAnimateNextPositionUpdate = true;
                }
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
                notificationStackScrollLayoutController.getClass();
                int i = SceneContainerFlag.$r8$clinit;
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                notificationStackScrollLayout.getClass();
                ExpandableView firstChildNotGoneInternal = notificationStackScrollLayout.getFirstChildNotGoneInternal();
                ExpandableNotificationRow expandableNotificationRow = firstChildNotGoneInternal instanceof ExpandableNotificationRow ? (ExpandableNotificationRow) firstChildNotGoneInternal : null;
                if (expandableNotificationRow != null && (expandableView == expandableNotificationRow || expandableNotificationRow.mNotificationParent == expandableNotificationRow)) {
                    notificationPanelViewController.requestScrollerTopPaddingUpdate();
                }
                notificationPanelViewController.updateExpandedHeightToMaxHeight();
            }
        }
        SharedNotificationContainerBinder$bind$3 sharedNotificationContainerBinder$bind$3 = this.mOnHeightChangedRunnable;
        if (sharedNotificationContainerBinder$bind$3 != null) {
            sharedNotificationContainerBinder$bind$3.run();
        }
    }

    public final void notifyOverscrollTopListener(float f, boolean z) {
        this.mExpandHelper.mOnlyMovements = f > 1.0f;
        if (this.mDontReportNextOverScroll) {
            this.mDontReportNextOverScroll = false;
            return;
        }
        ((FullExpansionPanelNotiAlphaController) Dependency.sDependency.getDependencyInner(FullExpansionPanelNotiAlphaController.class)).mStackScrollerOverscrolling = (f >= 1.0f ? f : 0.0f) != 0.0f && z;
        QuickSettingsControllerImpl.NsslOverscrollTopChangedListener nsslOverscrollTopChangedListener = this.mOverscrollTopChangedListener;
        if (nsslOverscrollTopChangedListener != null) {
            QuickSettingsControllerImpl quickSettingsControllerImpl = QuickSettingsControllerImpl.this;
            quickSettingsControllerImpl.getClass();
            if (quickSettingsControllerImpl.mAmount == f && quickSettingsControllerImpl.mIsRubberBanded == z) {
                return;
            }
            quickSettingsControllerImpl.mAmount = f;
            quickSettingsControllerImpl.mIsRubberBanded = z;
            ValueAnimator valueAnimator = quickSettingsControllerImpl.mExpansionAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            if (!quickSettingsControllerImpl.isExpansionEnabled()) {
                f = 0.0f;
            }
            if (f < 1.0f) {
                f = 0.0f;
            }
            boolean z2 = f != 0.0f && z;
            quickSettingsControllerImpl.mStackScrollerOverscrolling = z2;
            QS qs = quickSettingsControllerImpl.mQs;
            if (qs != null) {
                qs.setOverscrolling(z2);
            }
            quickSettingsControllerImpl.mExpansionFromOverscroll = f != 0.0f;
            quickSettingsControllerImpl.mLastOverscroll = f;
            quickSettingsControllerImpl.updateQsState$2();
            quickSettingsControllerImpl.setExpansionHeight(quickSettingsControllerImpl.mMinExpansionHeight + f);
        }
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        this.mWaterfallTopInset = 0;
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if (displayCutout != null) {
            this.mWaterfallTopInset = displayCutout.getWaterfallInsets().top;
        }
        int i = SceneContainerFlag.$r8$clinit;
        if (!this.mIsInsetAnimationRunning) {
            updateImeInset(windowInsets);
        }
        return windowInsets;
    }

    public final void onChildAnimationFinished() {
        setAnimationRunning(false);
        int i = SceneContainerFlag.$r8$clinit;
        requestChildrenUpdate();
        runAnimationFinishedRunnables();
        Iterator it = this.mClearTransientViewsWhenFinished.iterator();
        while (it.hasNext()) {
            ((ExpandableView) it.next()).removeFromTransientContainer();
        }
        this.mClearTransientViewsWhenFinished.clear();
        clearHeadsUpDisappearRunning();
        Log.d("StackScroller", "onChildAnimationFinished clearTemporaryViews");
        finalizeClearAllAnimation();
    }

    public final void onChildHeightChanged(ExpandableView expandableView, boolean z) {
        boolean z2 = this.mAnimateStackYForContentHeightChange;
        if (z) {
            this.mAnimateStackYForContentHeightChange = true;
        }
        updateContentHeight();
        if (this.mOwnScrollY > 0 && (expandableView instanceof ExpandableNotificationRow)) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
            if (!onKeyguard() && expandableNotificationRow.mUserLocked && expandableNotificationRow != getFirstChildNotGoneInternal() && !expandableNotificationRow.mIsSummaryWithChildren) {
                float translationY = expandableNotificationRow.getTranslationY() + expandableNotificationRow.mActualHeight;
                if (expandableNotificationRow.isChildInGroup()) {
                    translationY += expandableNotificationRow.mNotificationParent.getTranslationY();
                }
                int i = this.mMaxLayoutHeight + ((int) this.mAmbientState.mStackTranslation);
                NotificationSection lastVisibleSection = getLastVisibleSection();
                if (expandableNotificationRow != (lastVisibleSection == null ? null : lastVisibleSection.mLastVisibleChild) && this.mShelf.getVisibility() != 8) {
                    i -= this.mShelf.getHeight() + this.mPaddingBetweenElements;
                }
                float f = i;
                if (translationY > f) {
                    int i2 = SceneContainerFlag.$r8$clinit;
                    setOwnScrollY((int) ((getOwnScrollY() + translationY) - f));
                    this.mDisallowScrollingInThisMotion = true;
                }
            }
        }
        clampScrollPosition();
        notifyHeightChangeListener(expandableView, z);
        ExpandableNotificationRow expandableNotificationRow2 = expandableView instanceof ExpandableNotificationRow ? (ExpandableNotificationRow) expandableView : null;
        NotificationSection firstVisibleSection = getFirstVisibleSection();
        ExpandableView expandableView2 = firstVisibleSection != null ? firstVisibleSection.mFirstVisibleChild : null;
        if (expandableNotificationRow2 != null && (expandableNotificationRow2 == expandableView2 || expandableNotificationRow2.mNotificationParent == expandableView2)) {
            updateAlgorithmLayoutMinHeight();
        }
        if (z && this.mAnimationsEnabled && (this.mIsExpanded || (expandableNotificationRow2 != null && expandableNotificationRow2.mPinnedStatus.isPinned()))) {
            this.mNeedViewResizeAnimation = true;
            this.mNeedsAnimation = true;
        }
        requestChildrenUpdate();
        if (this.mTopHeadsUpRow == expandableView) {
            Iterator it = this.mHeadsUpHeightChangedListeners.iterator();
            while (it.hasNext()) {
                ((Runnable) it.next()).run();
            }
        }
        this.mAnimateStackYForContentHeightChange = z2;
    }

    public final void onClearAllAnimationsEnd(int i, List list) {
        InteractionJankMonitor.getInstance().end(62);
        NotificationStackScrollLayoutController$$ExternalSyntheticLambda4 notificationStackScrollLayoutController$$ExternalSyntheticLambda4 = this.mClearAllAnimationListener;
        if (notificationStackScrollLayoutController$$ExternalSyntheticLambda4 != null) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = (NotificationStackScrollLayoutController) notificationStackScrollLayoutController$$ExternalSyntheticLambda4.f$0;
            int i2 = 0;
            NotifCollection notifCollection = notificationStackScrollLayoutController.mNotifCollection;
            if (i == 0) {
                notifCollection.dismissAllNotifications(((NotificationLockscreenUserManagerImpl) notificationStackScrollLayoutController.mLockscreenUserManager).mCurrentUserId, false);
                return;
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = (ArrayList) list;
            int size = arrayList2.size();
            while (i2 < size) {
                Object obj = arrayList2.get(i2);
                i2++;
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) obj;
                if (expandableNotificationRow != null) {
                    int i3 = NotificationBundleUi.$r8$clinit;
                    NotificationEntry entryLegacy = expandableNotificationRow.getEntryLegacy();
                    arrayList.add(new EntryWithDismissStats(entryLegacy, new DismissedByUserStats(3, 1, ((NotificationVisibilityProviderImpl) notificationStackScrollLayoutController.mVisibilityProvider).obtain(entryLegacy)), entryLegacy.mKey, entryLegacy.hashCode()));
                }
            }
            notifCollection.dismissNotifications(arrayList);
        }
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        getResources();
        updateSplitNotificationShade();
        this.mStatusBarHeight = SystemBarUtils.getStatusBarHeight(((ViewGroup) this).mContext);
        this.mSwipeHelper.mPagingTouchSlop = ViewConfiguration.get(getContext()).getScaledPagingTouchSlop();
        initView(getContext(), this.mSwipeHelper, this.mNotificationStackSizeCalculator);
        int i = this.mOrientation;
        int i2 = configuration.orientation;
        if (i != i2) {
            this.mIsChangedOrientation = true;
            this.mOrientation = i2;
            this.mAmbientState.mOrientation = i2;
        }
        int i3 = this.mSemDisplayDeviceType;
        int i4 = configuration.semDisplayDeviceType;
        if (i3 != i4) {
            this.mSemDisplayDeviceType = i4;
            this.mAmbientState.getClass();
        }
        updateDndView(this.mDndStatus, getDndStatusText(this.mZenModeController));
        this.mDeviceWidth = DeviceState.getDisplayWidth(getContext());
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int i = SceneContainerFlag.$r8$clinit;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        int i = ModesEmptyShadeFix.$r8$clinit;
        inflateEmptyShadeView();
        inflateDndView();
    }

    @Override // android.view.View
    public final boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if (!this.mScrollingEnabled || !this.mIsExpanded || this.mSwipeHelper.mIsSwiping || this.mExpandingNotification || this.mDisallowScrollingInThisMotion) {
            return false;
        }
        if ((motionEvent.getSource() & 2) != 0 && motionEvent.getAction() == 8) {
            int i = SceneContainerFlag.$r8$clinit;
            if (!this.mIsBeingDragged) {
                float axisValue = motionEvent.getAxisValue(9);
                if (axisValue != 0.0f) {
                    int verticalScrollFactor = (int) (axisValue * getVerticalScrollFactor());
                    int scrollRange = getScrollRange();
                    int ownScrollY = getOwnScrollY();
                    int i2 = ownScrollY - verticalScrollFactor;
                    int i3 = i2 >= 0 ? i2 > scrollRange ? scrollRange : i2 : 0;
                    if (i3 != ownScrollY) {
                        setOwnScrollY(i3);
                        return true;
                    }
                }
            }
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    public final void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setScrollable(this.mScrollable);
        accessibilityEvent.setMaxScrollX(((ViewGroup) this).mScrollX);
        int i = SceneContainerFlag.$r8$clinit;
        accessibilityEvent.setScrollY(getOwnScrollY());
        accessibilityEvent.setMaxScrollY(getScrollRange());
    }

    public final void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (this.mScrollable) {
            accessibilityNodeInfo.setScrollable(true);
            if (this.mBackwardScrollable) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP);
            }
            if (this.mForwardScrollable) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN);
            }
        }
        accessibilityNodeInfo.setClassName(ScrollView.class.getName());
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        NotificationStackScrollLayoutController.TouchHandler touchHandler = this.mTouchHandler;
        if (touchHandler == null || !touchHandler.onInterceptTouchEvent(motionEvent)) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return true;
    }

    public final boolean onInterceptTouchEventScroll(MotionEvent motionEvent) {
        if (!this.mScrollingEnabled) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 2 && this.mIsBeingDragged) {
            return true;
        }
        int i = action & 255;
        if (i != 0) {
            if (i != 1) {
                if (i == 2) {
                    int i2 = this.mActivePointerId;
                    if (i2 != -1) {
                        int findPointerIndex = motionEvent.findPointerIndex(i2);
                        if (findPointerIndex == -1) {
                            Log.e("StackScroller", "Invalid pointerId=" + i2 + " in onInterceptTouchEvent");
                        } else {
                            int y = (int) motionEvent.getY(findPointerIndex);
                            int x = (int) motionEvent.getX(findPointerIndex);
                            int abs = Math.abs(y - this.mLastMotionY);
                            int abs2 = Math.abs(x - this.mDownX);
                            if (abs > getTouchSlop$2(motionEvent) && abs > abs2) {
                                setIsBeingDragged(true);
                                this.mLastMotionY = y;
                                this.mDownX = x;
                                if (this.mVelocityTracker == null) {
                                    this.mVelocityTracker = VelocityTracker.obtain();
                                }
                                this.mVelocityTracker.addMovement(motionEvent);
                            }
                        }
                    }
                } else if (i != 3) {
                    if (i == 6) {
                        onSecondaryPointerUp(motionEvent);
                    }
                }
            }
            setIsBeingDragged(false);
            this.mActivePointerId = -1;
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.mVelocityTracker = null;
            }
            if (this.mScroller.springBack(((ViewGroup) this).mScrollX, getOwnScrollY(), 0, 0, 0, getScrollRange())) {
                animateScroll();
            }
        } else {
            int y2 = (int) motionEvent.getY();
            AnonymousClass9 anonymousClass9 = this.mScrollAdapter;
            anonymousClass9.getClass();
            int i3 = SceneContainerFlag.$r8$clinit;
            this.mScrolledToTopOnFirstDown = NotificationStackScrollLayout.this.getOwnScrollY() == 0;
            if (getChildAtPosition(motionEvent.getX(), y2, false, false) == null) {
                setIsBeingDragged(false);
                VelocityTracker velocityTracker2 = this.mVelocityTracker;
                if (velocityTracker2 != null) {
                    velocityTracker2.recycle();
                    this.mVelocityTracker = null;
                }
            } else {
                this.mLastMotionY = y2;
                this.mDownX = (int) motionEvent.getX();
                this.mActivePointerId = motionEvent.getPointerId(0);
                VelocityTracker velocityTracker3 = this.mVelocityTracker;
                if (velocityTracker3 == null) {
                    this.mVelocityTracker = VelocityTracker.obtain();
                } else {
                    velocityTracker3.clear();
                }
                this.mVelocityTracker.addMovement(motionEvent);
                setIsBeingDragged(!this.mScroller.isFinished());
            }
        }
        return this.mIsBeingDragged;
    }

    public final boolean onKeyguard() {
        return this.mStatusBarState == 1;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (!this.mSuppressChildrenMeasureAndLayout) {
            float width = getWidth() / 2.0f;
            for (int i5 = 0; i5 < getChildCount(); i5++) {
                float measuredWidth = r8.getMeasuredWidth() / 2.0f;
                getChildAt(i5).layout((int) (width - measuredWidth), 0, (int) (measuredWidth + width), r8.getMeasuredHeight());
            }
        }
        int i6 = SceneContainerFlag.$r8$clinit;
        this.mMaxLayoutHeight = getHeight();
        updateAlgorithmHeightAndPadding();
        updateContentHeight();
        clampScrollPosition();
        requestChildrenUpdate();
        updateFirstAndLastBackgroundViews();
        updateAlgorithmLayoutMinHeight();
        updateOwnTranslationZ();
        int i7 = QSComposeFragment.$r8$clinit;
        ViewGroup viewGroup = this.mQsHeader;
        if (viewGroup != null) {
            viewGroup.getHeight();
        }
        this.mStackScrollAlgorithm.getClass();
        this.mAnimateStackYForContentHeightChange = false;
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        Trace.beginSection("NotificationStackScrollLayout#onMeasure");
        super.onMeasure(i, i2);
        if (((KeyguardFoldControllerImpl) this.mKeyguardFoldController).isUnlockOnFoldOpened() || ((KeyguardFoldControllerImpl) this.mKeyguardFoldController).isBouncerOnFoldOpened() || this.mController.mHasDelayedForceLayout) {
            this.mForceLayoutFirstMeasure = true;
            return;
        }
        if (this.mForceLayoutFirstMeasure) {
            Log.d("StackScroller", "stackScroller forcelayout measure!");
            this.mForceLayoutFirstMeasure = false;
        }
        if (this.mIsVisibleFromGone) {
            this.mIsVisibleFromGone = false;
            Log.d("StackScroller", "visible from gone, first measure!");
        }
        int size = View.MeasureSpec.getSize(i);
        int i3 = getResources().getConfiguration().orientation;
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(size, "viewWidth=", " skinnyNotifsInLandscape=");
        m.append(this.mSkinnyNotifsInLandscape);
        m.append(" orientation=");
        m.append(i3);
        this.mLastUpdateSidePaddingDumpString = m.toString();
        this.mLastUpdateSidePaddingElapsedRealtime = SystemClock.elapsedRealtime();
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        int notificationSidePadding = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getNotificationSidePadding(((ViewGroup) this).mContext, true);
        this.mSidePaddings = notificationSidePadding;
        if (this.mSuppressChildrenMeasureAndLayout) {
            Log.d("StackScroller", "SuppressChildrenMeasureAndLayout set as ture. return onMeasure");
            Trace.endSection();
            return;
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size - (notificationSidePadding * 2), View.MeasureSpec.getMode(i));
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), 0);
        int childCount = getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            measureChild(getChildAt(i4), makeMeasureSpec, makeMeasureSpec2);
        }
        int i5 = SceneContainerFlag.$r8$clinit;
        Trace.endSection();
    }

    public final void onOverScrollFling(int i, boolean z) {
        QuickSettingsControllerImpl.NsslOverscrollTopChangedListener nsslOverscrollTopChangedListener = this.mOverscrollTopChangedListener;
        if (nsslOverscrollTopChangedListener != null) {
            float f = i;
            QuickSettingsControllerImpl quickSettingsControllerImpl = QuickSettingsControllerImpl.this;
            if (!quickSettingsControllerImpl.isSplitShadeAndTouchXOutsideQs(quickSettingsControllerImpl.mInitialTouchX)) {
                quickSettingsControllerImpl.mLastOverscroll = 0.0f;
                quickSettingsControllerImpl.mExpansionFromOverscroll = false;
                if (z) {
                    quickSettingsControllerImpl.mStackScrollerOverscrolling = false;
                    QS qs = quickSettingsControllerImpl.mQs;
                    if (qs != null) {
                        qs.setOverscrolling(false);
                    }
                }
                quickSettingsControllerImpl.setExpansionHeight(quickSettingsControllerImpl.mExpansionHeight);
                boolean isExpansionEnabled = quickSettingsControllerImpl.isExpansionEnabled();
                if (!isExpansionEnabled && z) {
                    f = 0.0f;
                }
                quickSettingsControllerImpl.flingQs(f, (z && isExpansionEnabled) ? 0 : 1, new QuickSettingsControllerImpl$$ExternalSyntheticLambda10(nsslOverscrollTopChangedListener, 4), false);
            }
        }
        this.mDontReportNextOverScroll = true;
        setOverScrollAmount(0.0f, true, false, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:80:0x01d5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onScrollTouch(android.view.MotionEvent r23) {
        /*
            Method dump skipped, instructions count: 955
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.onScrollTouch(android.view.MotionEvent):boolean");
    }

    public final void onSecondaryPointerUp(MotionEvent motionEvent) {
        int action = (motionEvent.getAction() & 65280) >> 8;
        if (motionEvent.getPointerId(action) == this.mActivePointerId) {
            int i = action == 0 ? 1 : 0;
            this.mLastMotionY = (int) motionEvent.getY(i);
            this.mActivePointerId = motionEvent.getPointerId(i);
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:129:0x0344, code lost:
    
        if (r11 == false) goto L228;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x020d, code lost:
    
        if (r12.mResizedView != null) goto L109;
     */
    /* JADX WARN: Removed duplicated region for block: B:108:0x02b7  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x02cf  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0303  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0340  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x034b  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x02d8  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0275  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x025e  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x027a  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0296  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onTouchEvent(android.view.MotionEvent r19) {
        /*
            Method dump skipped, instructions count: 878
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.ViewGroup
    public final void onViewAdded(View view) {
        super.onViewAdded(view);
        if (view instanceof ExpandableView) {
            onViewAddedInternal((ExpandableView) view);
        }
    }

    public final void onViewAddedInternal(ExpandableView expandableView) {
        expandableView.setHideSensitiveForIntrinsicHeight(this.mAmbientState.mHideSensitive);
        expandableView.mOnHeightChangedListener = this.mOnChildHeightChangedListener;
        boolean z = expandableView instanceof ExpandableNotificationRow;
        if (z) {
            ((ExpandableNotificationRow) expandableView).mEntry.mOnSensitivityChangedListeners.addIfAbsent(this.mOnChildSensitivityChangedListener);
            int i = SceneContainerFlag.$r8$clinit;
        }
        if (this.mIsExpanded && this.mAnimationsEnabled && !this.mChangePositionInProgress && !this.mAmbientState.isFullyHidden()) {
            this.mChildrenToAddAnimated.add(expandableView);
            this.mNeedsAnimation = true;
        }
        boolean z2 = false;
        if ((z ? ((ExpandableNotificationRow) expandableView).mIsHeadsUp : false) && this.mAnimationsEnabled && !this.mChangePositionInProgress && !this.mAmbientState.isFullyHidden()) {
            this.mAddedHeadsUpChildren.add(expandableView);
            this.mChildrenToAddAnimated.remove(expandableView);
        }
        if ((this.mAnimationsEnabled || this.mPulsing) && (this.mIsExpanded || isPinnedHeadsUp(expandableView))) {
            z2 = true;
        }
        if (z) {
            ((ExpandableNotificationRow) expandableView).setAnimationRunning(z2);
        }
        if (z) {
            ((ExpandableNotificationRow) expandableView).setChronometerRunning(this.mIsExpanded);
        }
        if (z) {
            ((ExpandableNotificationRow) expandableView).setDismissUsingRowTranslationX(this.mDismissUsingRowTranslationX, true);
        }
    }

    @Override // android.view.ViewGroup
    public final void onViewRemoved(View view) {
        super.onViewRemoved(view);
        ExpandableView expandableView = (ExpandableView) view;
        if (!this.mChildTransferInProgress) {
            onViewRemovedInternal(expandableView, this);
        }
        this.mShelf.getClass();
        expandableView.requestRoundnessReset(NotificationShelf.SHELF_SCROLL);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x002a, code lost:
    
        if (r4.isInsignificantSummary() == false) goto L27;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0227  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x022f  */
    /* JADX WARN: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onViewRemovedInternal(com.android.systemui.statusbar.notification.row.ExpandableView r13, android.view.ViewGroup r14) {
        /*
            Method dump skipped, instructions count: 599
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.onViewRemovedInternal(com.android.systemui.statusbar.notification.row.ExpandableView, android.view.ViewGroup):void");
    }

    @Override // android.view.View
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            return;
        }
        cancelLongPress();
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0022, code lost:
    
        if (r6 != 16908346) goto L23;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0056  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean performAccessibilityActionInternal(int r6, android.os.Bundle r7) {
        /*
            r5 = this;
            boolean r7 = super.performAccessibilityActionInternal(r6, r7)
            r0 = 1
            if (r7 == 0) goto L8
            return r0
        L8:
            boolean r7 = r5.isEnabled()
            r1 = 0
            if (r7 != 0) goto L10
            goto L6a
        L10:
            int r7 = com.android.systemui.scene.shared.flag.SceneContainerFlag.$r8$clinit
            r7 = 4096(0x1000, float:5.74E-42)
            if (r6 == r7) goto L27
            r7 = 8192(0x2000, float:1.148E-41)
            if (r6 == r7) goto L25
            r7 = 16908344(0x1020038, float:2.3877386E-38)
            if (r6 == r7) goto L25
            r7 = 16908346(0x102003a, float:2.3877392E-38)
            if (r6 == r7) goto L27
            goto L6a
        L25:
            r6 = -1
            goto L28
        L27:
            r6 = r0
        L28:
            int r7 = r5.getHeight()
            int r2 = r5.mPaddingBottom
            int r7 = r7 - r2
            int r2 = r5.getTopPadding()
            int r7 = r7 - r2
            int r2 = r5.mPaddingTop
            int r7 = r7 - r2
            com.android.systemui.statusbar.NotificationShelf r2 = r5.mShelf
            int r2 = r2.getHeight()
            int r7 = r7 - r2
            int r2 = r5.getOwnScrollY()
            int r6 = r6 * r7
            int r6 = r6 + r2
            int r7 = r5.getScrollRange()
            int r6 = java.lang.Math.min(r6, r7)
            int r6 = java.lang.Math.max(r1, r6)
            int r7 = r5.getOwnScrollY()
            if (r6 == r7) goto L6a
            android.widget.OverScroller r7 = r5.mScroller
            int r2 = r5.mScrollX
            int r3 = r5.getOwnScrollY()
            int r4 = r5.getOwnScrollY()
            int r6 = r6 - r4
            r7.startScroll(r2, r3, r1, r6)
            r5.animateScroll()
            return r0
        L6a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.performAccessibilityActionInternal(int, android.os.Bundle):boolean");
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [T, java.lang.String] */
    public final void removeTransientView(View view) {
        NotificationStackScrollLogger notificationStackScrollLogger = this.mLogger;
        if (notificationStackScrollLogger != null && (view instanceof ExpandableNotificationRow)) {
            String str = ((ExpandableNotificationRow) view).mLoggingKey;
            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ref$ObjectRef.element = Log.getStackTraceString(new Throwable());
            LogLevel logLevel = LogLevel.INFO;
            NotificationStackScrollLogger$$ExternalSyntheticLambda3 notificationStackScrollLogger$$ExternalSyntheticLambda3 = new NotificationStackScrollLogger$$ExternalSyntheticLambda3(ref$ObjectRef, 0);
            LogBuffer logBuffer = notificationStackScrollLogger.notificationRenderBuffer;
            LogMessage obtain = logBuffer.obtain("NotificationStackScroll", logLevel, notificationStackScrollLogger$$ExternalSyntheticLambda3, null);
            ((LogMessageImpl) obtain).str1 = str;
            logBuffer.commit(obtain);
        }
        super.removeTransientView(view);
    }

    public final void requestChildrenUpdate() {
        if (this.mChildrenUpdateRequested) {
            return;
        }
        getViewTreeObserver().addOnPreDrawListener(this.mChildrenUpdater);
        this.mChildrenUpdateRequested = true;
        invalidate();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestDisallowInterceptTouchEvent(boolean z) {
        super.requestDisallowInterceptTouchEvent(z);
        if (z) {
            cancelLongPress();
        }
    }

    @Override // android.view.View, android.view.ViewParent
    public final void requestLayout() {
        Trace.instant(4096L, "NotificationStackScrollLayout#requestLayout");
        super.requestLayout();
    }

    public final void resetAllSwipeState() {
        List attachedChildren;
        Trace.beginSection("NSSL.resetAllSwipeState()");
        NotificationSwipeHelper notificationSwipeHelper = this.mSwipeHelper;
        notificationSwipeHelper.resetSwipeStates(true);
        NotificationStackScrollLayoutController.this.mMagneticNotificationRowManager.reset();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            NotificationSwipeHelper notificationSwipeHelper2 = this.mSwipeHelper;
            notificationSwipeHelper2.getClass();
            if (childAt.getTranslationX() == 0.0f) {
                int i2 = NotificationContentAlphaOptimization.$r8$clinit;
            } else {
                notificationSwipeHelper2.setTranslation(0.0f, childAt);
                notificationSwipeHelper2.updateSwipeProgressFromOffset(childAt, 0.0f, true);
            }
            if ((childAt instanceof ExpandableNotificationRow) && (attachedChildren = ((ExpandableNotificationRow) childAt).getAttachedChildren()) != null) {
                ArrayList arrayList = (ArrayList) attachedChildren;
                int size = arrayList.size();
                int i3 = 0;
                while (i3 < size) {
                    Object obj = arrayList.get(i3);
                    i3++;
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) obj;
                    NotificationSwipeHelper notificationSwipeHelper3 = this.mSwipeHelper;
                    notificationSwipeHelper3.getClass();
                    if (expandableNotificationRow.getTranslationX() == 0.0f) {
                        int i4 = NotificationContentAlphaOptimization.$r8$clinit;
                    } else {
                        notificationSwipeHelper3.setTranslation(0.0f, expandableNotificationRow);
                        notificationSwipeHelper3.updateSwipeProgressFromOffset(expandableNotificationRow, 0.0f, true);
                    }
                }
            }
        }
        updateContinuousShadowDrawing();
        Trace.endSection();
    }

    public final void resetScrollPosition() {
        this.mScroller.abortAnimation();
        setOwnScrollY(0);
        if (getCurrentOverScrollAmount(true) > 0.0f) {
            setOverScrollAmount(0.0f, true, false, true);
        }
        if (getCurrentOverScrollAmount(false) > 0.0f) {
            setOverScrollAmount(0.0f, false, false, true);
        }
    }

    public final void runAnimationFinishedRunnables() {
        Iterator it = this.mAnimationFinishedRunnables.iterator();
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
        }
        this.mAnimationFinishedRunnables.clear();
    }

    public final int scrollAmountForKeyboardFocus(int i) {
        View childAt = getChildAt(i + 1);
        if (!(childAt instanceof ExpandableNotificationRow) || childAt.getY() + childAt.getHeight() <= this.mShelf.getY()) {
            return 0;
        }
        return ((ExpandableNotificationRow) childAt).mActualHeight + this.mPaddingBetweenElements;
    }

    public final boolean scrollTo(View view) {
        int i = SceneContainerFlag.$r8$clinit;
        ExpandableView expandableView = (ExpandableView) view;
        int positionInLinearLayout = getPositionInLinearLayout(view);
        int targetScrollForView = targetScrollForView(expandableView, positionInLinearLayout);
        int intrinsicHeight = expandableView.getIntrinsicHeight() + positionInLinearLayout;
        if (getOwnScrollY() >= targetScrollForView && intrinsicHeight >= getOwnScrollY()) {
            return false;
        }
        this.mScroller.startScroll(((ViewGroup) this).mScrollX, getOwnScrollY(), 0, targetScrollForView - getOwnScrollY());
        this.mDontReportNextOverScroll = true;
        animateScroll();
        return true;
    }

    @Override // android.view.View
    public final void setAlpha(float f) {
        if (QsAnimatorState.isCustomizerShowing || ((this.mController.mMusicItemExpanded && !this.mVislbeNSSLWhileMediaExpanded) || (QsAnimatorState.isDetailShowing && !QsAnimatorState.isDetailClosing))) {
            if (QsAnimatorState.isCustomizerShowing) {
                super.setAlpha(0.0f);
                return;
            }
            return;
        }
        if (SecPanelSplitHelper.isEnabled()) {
            if (getPanelSplitHelper().isQSState()) {
                f = 0.0f;
            }
            if (getPanelSplitHelper().isShadeState() && this.mStatusBarState != 1) {
                f = 1.0f;
            }
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mController;
        float min = Math.min(f, Math.min(notificationStackScrollLayoutController.mMaxAlphaForRebind, Math.min(Math.min(notificationStackScrollLayoutController.mMaxAlphaFromView, notificationStackScrollLayoutController.mBarState == 1 ? notificationStackScrollLayoutController.mMaxAlphaForKeyguard : 1.0f), Math.min(notificationStackScrollLayoutController.mMaxAlphaForUnhide, notificationStackScrollLayoutController.mMaxAlphaForGlanceableHub))));
        if (Trace.isEnabled()) {
            Trace.setCounter(TrackGroupUtils.trackGroup("shade", "NSSLResultingAlpha"), (int) (100.0f * min));
        }
        super.setAlpha(min);
    }

    public final void setAnimationRunning(boolean z) {
        if (z != this.mAnimationRunning) {
            if (z) {
                getViewTreeObserver().addOnPreDrawListener(this.mRunningAnimationUpdater);
            } else {
                getViewTreeObserver().removeOnPreDrawListener(this.mRunningAnimationUpdater);
            }
            this.mAnimationRunning = z;
            updateContinuousShadowDrawing();
        }
    }

    public void setClearAllInProgress(boolean z) {
        this.mClearAllInProgress = z;
        this.mAmbientState.mClearAllInProgress = z;
        this.mController.mNotificationRoundnessManager.mIsClearAllInProgress = z;
    }

    public final void setExpandedHeight(float f) {
        int i;
        int i2 = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        boolean shouldSkipHeightUpdate = shouldSkipHeightUpdate();
        updateStackPosition(false);
        float f2 = 0.0f;
        if (!shouldSkipHeightUpdate) {
            this.mExpandedHeight = f;
            setIsExpanded(f > 0.0f);
            float height = this.mShelf.getHeight() + this.mWaterfallTopInset;
            if (f < height) {
                Rect rect = this.mClipRect;
                rect.left = 0;
                rect.right = getWidth();
                Rect rect2 = this.mClipRect;
                rect2.top = 0;
                rect2.bottom = (int) f;
                this.mRequestedClipBounds = rect2;
                updateClipping$1();
                f = height;
            } else {
                this.mRequestedClipBounds = null;
                updateClipping$1();
            }
        }
        float f3 = 1.0f;
        if (calculateAppearFraction(f) < 1.0f) {
            f3 = calculateAppearFraction(f);
            float interpolate = f3 >= 0.0f ? NotificationUtils.interpolate(((this.mShelf.getHeight() + this.mWaterfallTopInset) + (-getTopPadding())) - this.mShelf.getHeight(), 0.0f, f3) : (f - getAppearStartPosition()) + (((this.mShelf.getHeight() + this.mWaterfallTopInset) + (-getTopPadding())) - this.mShelf.getHeight());
            i = (int) (f - interpolate);
            f2 = (!isHeadsUpTransition() || f3 < 0.0f) ? interpolate : MathUtils.lerp(this.mHeadsUpInset - getTopPadding(), 0.0f, f3);
        } else if (this.mShouldShowShelfOnly) {
            i = getTopPadding() + this.mShelf.getHeight();
        } else {
            if (this.mQsFullScreen) {
                int topPadding = (this.mContentHeight - getTopPadding()) + this.mIntrinsicPadding;
                int height2 = this.mShelf.getHeight() + this.mMaxTopPadding;
                if (topPadding <= height2) {
                    i = height2;
                } else {
                    f = NotificationUtils.interpolate(topPadding, height2, getQsExpansionFraction$1());
                }
            } else if (shouldSkipHeightUpdate) {
                f = this.mExpandedHeight;
            }
            i = (int) f;
        }
        this.mAmbientState.mAppearFraction = f3;
        if (i != this.mCurrentStackHeight && !shouldSkipHeightUpdate) {
            this.mCurrentStackHeight = i;
            updateAlgorithmHeightAndPadding();
            requestChildrenUpdate();
        }
        AmbientState ambientState = this.mAmbientState;
        if (f2 != ambientState.mStackTranslation) {
            ambientState.mStackTranslation = f2;
            requestChildrenUpdate();
        }
        notifyAppearChangedListeners();
    }

    public void setExpandedInThisMotion(boolean z) {
        this.mExpandedInThisMotion = z;
    }

    public void setExpandingNotification(boolean z) {
        this.mExpandingNotification = z;
    }

    public final void setHeadsUpAnimatingAway(boolean z) {
        if (this.mHeadsUpAnimatingAway != z) {
            this.mHeadsUpAnimatingAway = z;
            HeadsUpNotificationViewBinderKt$isHeadsUpAnimatingAway$1$1 headsUpNotificationViewBinderKt$isHeadsUpAnimatingAway$1$1 = this.mHeadsUpAnimatingAwayListener;
            if (headsUpNotificationViewBinderKt$isHeadsUpAnimatingAway$1$1 != null) {
                headsUpNotificationViewBinderKt$isHeadsUpAnimatingAway$1$1.accept(Boolean.valueOf(z));
            }
        }
        updateClipping$1();
    }

    public void setIsBeingDragged(boolean z) {
        this.mIsBeingDragged = z;
        if (z) {
            requestDisallowInterceptTouchEvent(true);
            cancelLongPress();
            this.mSwipeHelper.resetExposedMenuView$1(true, true);
        }
    }

    public void setIsExpanded(boolean z) {
        ExpandableView expandableView;
        boolean z2 = z != this.mIsExpanded;
        this.mIsExpanded = z;
        this.mStackScrollAlgorithm.mIsExpanded = z;
        this.mAmbientState.mShadeExpanded = z;
        this.mStateAnimator.mShadeExpanded = z;
        NotificationSwipeHelper notificationSwipeHelper = this.mSwipeHelper;
        notificationSwipeHelper.mIsExpanded = z;
        if (!this.mInHeadsUpPinnedMode && !z && (expandableView = notificationSwipeHelper.mTouchedView) != null) {
            notificationSwipeHelper.snapChild(expandableView, 0.0f, 0.0f);
        }
        if (z2) {
            this.mWillExpand = false;
            if (this.mIsExpanded) {
                int i = SceneContainerFlag.$r8$clinit;
                int i2 = NotificationContentAlphaOptimization.$r8$clinit;
            } else {
                ((GroupExpansionManagerImpl) this.mGroupExpansionManager).collapseGroups();
                ExpandHelper expandHelper = this.mExpandHelper;
                expandHelper.finishExpanding(0.0f, true, false);
                expandHelper.mResizedView = null;
                expandHelper.mSGD = new ScaleGestureDetector(expandHelper.mContext, expandHelper.mScaleGestureListener);
                if (!this.mIsExpansionChanging) {
                    resetAllSwipeState();
                }
                finalizeClearAllAnimation();
            }
            updateNotificationAnimationStates();
            int childCount = getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (childAt instanceof ExpandableNotificationRow) {
                    ((ExpandableNotificationRow) childAt).setChronometerRunning(this.mIsExpanded);
                }
            }
            requestChildrenUpdate();
            updateUseRoundedRectClipping();
            updateDismissBehavior();
        }
    }

    public final void setMaxDisplayedNotifications(int i) {
        if (this.mMaxDisplayedNotifications != i) {
            KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(new StringBuilder("setMaxDisplayedNotifications prev "), this.mMaxDisplayedNotifications, " new ", i, "StackScroller");
            this.mMaxDisplayedNotifications = i;
            int i2 = SceneContainerFlag.$r8$clinit;
            updateContentHeight();
            notifyHeightChangeListener(this.mShelf, false);
        }
    }

    public final void setOverScrollAmount(float f, boolean z, boolean z2, boolean z3) {
        setOverScrollAmount(f, z, z2, z3, isRubberbanded(z));
    }

    public void setOwnScrollY(int i) {
        setOwnScrollY(i, false);
    }

    public final float setPulseHeight(float f) {
        float max;
        this.mAmbientState.setPulseHeight(f);
        if (this.mKeyguardBypassEnabled) {
            notifyAppearChangedListeners();
            int i = SceneContainerFlag.$r8$clinit;
            max = Math.max(0.0f, f - this.mIntrinsicPadding);
        } else {
            max = Math.max(0.0f, f - this.mAmbientState.getInnerHeight$1());
        }
        requestChildrenUpdate();
        return max;
    }

    public void setStatusBarState(int i) {
        if (this.mStatusBarState == 2 && i == 1) {
            setIsBeingDragged(false);
        }
        this.mStatusBarState = i;
        AmbientState ambientState = this.mAmbientState;
        if (ambientState.mStatusBarState != 1) {
            ambientState.mIsFlingRequiredAfterLockScreenSwipeUp = false;
        }
        ambientState.mStatusBarState = i;
        NotificationShelf notificationShelf = this.mShelf;
        if (notificationShelf != null) {
            notificationShelf.mPaddingBetweenElements = notificationShelf.getResources().getDimensionPixelSize(R.dimen.notification_divider_height);
        }
        this.mSpeedBumpIndexDirty = true;
        updateDismissBehavior();
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        if (getVisibility() != 0 && i == 0 && this.mAmbientState.mDozing) {
            this.mLastVisibleTrace = " skip setVisibility on doze " + this.mDateFormat.format(new Date(System.currentTimeMillis())) + " " + Log.getStackTraceString(new Throwable());
            return;
        }
        if (getVisibility() != 4 && i == 4) {
            this.mLastInvisibleTrace = this.mDateFormat.format(new Date(System.currentTimeMillis())) + " " + Log.getStackTraceString(new Throwable());
        }
        if (getVisibility() != 8 && i == 8) {
            this.mLastGoneCallTrace = this.mDateFormat.format(new Date(System.currentTimeMillis())) + " " + Log.getStackTraceString(new Throwable());
        }
        if (getVisibility() == 8 && i == 0) {
            this.mIsVisibleFromGone = true;
        }
        super.setVisibility(i);
    }

    @Override // android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return true;
    }

    public final boolean shouldSkipHeightUpdate() {
        int i = SceneContainerFlag.$r8$clinit;
        if (!this.mAmbientState.isOnKeyguard$1()) {
            return false;
        }
        AmbientState ambientState = this.mAmbientState;
        if (ambientState.mIsSwipingUp) {
            return true;
        }
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        return ambientState.mIsFlinging && ambientState.mIsFlingRequiredAfterLockScreenSwipeUp;
    }

    /* JADX WARN: Code restructure failed: missing block: B:293:0x066c, code lost:
    
        if (r1 != 13) goto L253;
     */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0255  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x025b  */
    /* JADX WARN: Removed duplicated region for block: B:414:0x08e4  */
    /* JADX WARN: Removed duplicated region for block: B:416:0x08f0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startAnimationToState$1() {
        /*
            Method dump skipped, instructions count: 2642
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.startAnimationToState$1():void");
    }

    public final int targetScrollForView(ExpandableView expandableView, int i) {
        int i2 = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int imeInset = ((getImeInset() + (expandableView.getIntrinsicHeight() + i)) - getHeight()) + ((this.mIsExpanded || !isPinnedHeadsUp(expandableView)) ? getTopPadding() : this.mHeadsUpInset) + this.mMinimumPaddings;
        return ((this.mIsExpanded || !isPinnedHeadsUp(expandableView)) && imeInset > 0 && imeInset < getScrollAmountToScrollBoundary()) ? getScrollAmountToScrollBoundary() : imeInset;
    }

    public final void updateAlgorithmHeightAndPadding() {
        this.mAmbientState.mLayoutHeight = Math.min(this.mMaxLayoutHeight, this.mCurrentStackHeight);
        this.mAmbientState.mLayoutMaxHeight = this.mMaxLayoutHeight;
        updateAlgorithmLayoutMinHeight();
    }

    public final void updateAlgorithmLayoutMinHeight() {
        int i = SceneContainerFlag.$r8$clinit;
        AmbientState ambientState = this.mAmbientState;
        int layoutMinHeightInternal = (this.mQsFullScreen || isHeadsUpTransition()) ? getLayoutMinHeightInternal() : 0;
        ambientState.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        ambientState.mLayoutMinHeight = layoutMinHeightInternal;
    }

    public final void updateBgColor() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof ActivatableNotificationView) {
                ((ActivatableNotificationView) childAt).updateBackgroundColors();
            }
        }
    }

    public final void updateClipping$1() {
        boolean z = (this.mRequestedClipBounds == null || this.mInHeadsUpPinnedMode || this.mHeadsUpAnimatingAway) ? false : true;
        if (this.mIsClipped != z) {
            this.mIsClipped = z;
        }
        if (this.mAmbientState.isHiddenAtAll()) {
            invalidateOutline();
            if (this.mAmbientState.isFullyHidden()) {
                setClipBounds(null);
            }
        } else if (z) {
            setClipBounds(this.mRequestedClipBounds);
        } else {
            setClipBounds(null);
        }
        setClipToOutline(false);
    }

    public final void updateContentHeight() {
        Object obj;
        int i = SceneContainerFlag.$r8$clinit;
        float f = this.mAmbientState.isOnKeyguard$1() ? 0 : this.mMinimumPaddings;
        NotificationShelf notificationShelf = this.mShelf;
        int height = notificationShelf != null ? notificationShelf.getHeight() : 0;
        int i2 = (int) f;
        NotificationStackSizeCalculator notificationStackSizeCalculator = this.mNotificationStackSizeCalculator;
        int i3 = this.mMaxDisplayedNotifications;
        SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 computeHeightPerNotificationLimit = notificationStackSizeCalculator.computeHeightPerNotificationLimit(this, height);
        if (i3 >= 0) {
            SequenceBuilderIterator it = SequencesKt__SequenceBuilderKt.iterator(computeHeightPerNotificationLimit.$block$inlined);
            int i4 = 0;
            while (true) {
                if (!it.hasNext()) {
                    obj = (NotificationStackSizeCalculator.StackHeight) SequencesKt___SequencesKt.last(computeHeightPerNotificationLimit);
                    break;
                }
                Object next = it.next();
                int i5 = i4 + 1;
                if (i3 == i4) {
                    obj = next;
                    break;
                }
                i4 = i5;
            }
        } else {
            obj = (NotificationStackSizeCalculator.StackHeight) SequencesKt___SequencesKt.last(computeHeightPerNotificationLimit);
        }
        NotificationStackSizeCalculator.StackHeight stackHeight = (NotificationStackSizeCalculator.StackHeight) obj;
        float f2 = stackHeight.notifsHeight;
        boolean z = notificationStackSizeCalculator.saveSpaceOnLockscreen;
        float f3 = stackHeight.shelfHeightWithSpaceBefore;
        float f4 = i2 + ((int) (z ? stackHeight.notifsHeightSavingSpace + f3 : f2 + f3));
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        this.mIntrinsicContentHeight = f4;
        this.mContentHeight = (int) (f4 + Math.max(this.mIntrinsicPadding, getTopPadding()) + this.mBottomPadding);
        updateScrollability();
        clampScrollPosition();
        updateStackPosition(false);
        AmbientState ambientState = this.mAmbientState;
        int i6 = this.mContentHeight;
        ambientState.getClass();
        ambientState.mContentHeight = i6;
    }

    public final void updateContinuousShadowDrawing() {
        boolean z = this.mAnimationRunning || this.mSwipeHelper.mIsSwiping;
        if (z != this.mContinuousShadowUpdate) {
            if (z) {
                getViewTreeObserver().addOnPreDrawListener(this.mShadowUpdater);
            } else {
                getViewTreeObserver().removeOnPreDrawListener(this.mShadowUpdater);
            }
            this.mContinuousShadowUpdate = z;
        }
    }

    public final void updateDecorViews() {
        EmptyShadeView emptyShadeView;
        int color = ((ViewGroup) this).mContext.getColor(android.R.color.search_url_text_material_light);
        int color2 = ((ViewGroup) this).mContext.getColor(android.R.color.search_url_text_normal);
        ColorUpdateLogger.Companion.getClass();
        this.mSectionsManager.setHeaderForegroundColors(color, color2);
        if (NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW || (emptyShadeView = this.mEmptyShadeView) == null) {
            return;
        }
        emptyShadeView.mEmptyText.setTextColor(emptyShadeView.getResources().getColor(R.color.sec_no_notification_text_color));
        emptyShadeView.mEmptyFooterText.setTextColor(color);
        emptyShadeView.mEmptyFooterText.setCompoundDrawableTintList(ColorStateList.valueOf(color));
    }

    public final void updateDismissBehavior() {
        boolean z = true;
        if (NotiRune.NOTI_STYLE_POP_OVER_DISMISS_CLIP_VIEW && this.mStatusBarState != 1 && this.mIsExpanded) {
            z = false;
        }
        if (this.mDismissUsingRowTranslationX != z) {
            this.mDismissUsingRowTranslationX = z;
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                if (childAt instanceof ExpandableNotificationRow) {
                    ((ExpandableNotificationRow) childAt).setDismissUsingRowTranslationX(z, false);
                }
            }
        }
    }

    public final void updateDndView(int i, String str) {
        boolean z = i == 1 && !onKeyguard();
        DndStatusView dndStatusView = this.mDndStatusView;
        if (dndStatusView != null) {
            dndStatusView.setVisible(z, false);
            DndStatusView dndStatusView2 = this.mDndStatusView;
            char c = z ? (char) 0 : '\b';
            dndStatusView2.getClass();
            dndStatusView2.setSecondaryVisible(c == 0);
            if (z && str != null && !str.isEmpty()) {
                this.mDndStatusView.setDndTextAndIcon(str);
            }
            requestChildrenUpdate();
        }
    }

    public final void updateEmptyShadeViewResources(int i, int i2, int i3) {
        int i4 = ModesEmptyShadeFix.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        EmptyShadeView emptyShadeView = this.mEmptyShadeView;
        emptyShadeView.getClass();
        if (emptyShadeView.mTextId != i) {
            EmptyShadeView emptyShadeView2 = this.mEmptyShadeView;
            emptyShadeView2.getClass();
            emptyShadeView2.mTextId = i;
            emptyShadeView2.mEmptyText.setText(i);
        }
        EmptyShadeView emptyShadeView3 = this.mEmptyShadeView;
        emptyShadeView3.getClass();
        if (emptyShadeView3.mFooterText != i2) {
            EmptyShadeView emptyShadeView4 = this.mEmptyShadeView;
            emptyShadeView4.getClass();
            emptyShadeView4.mFooterText = i2;
            if (i2 != 0) {
                emptyShadeView4.mEmptyFooterText.setText(i2);
            } else {
                emptyShadeView4.mEmptyFooterText.setText((CharSequence) null);
            }
        }
        EmptyShadeView emptyShadeView5 = this.mEmptyShadeView;
        emptyShadeView5.getClass();
        if (emptyShadeView5.mFooterIcon != i3) {
            this.mEmptyShadeView.setFooterIcon(i3);
        }
        if (i3 == 0 && i2 == 0) {
            EmptyShadeView emptyShadeView6 = this.mEmptyShadeView;
            emptyShadeView6.getClass();
            emptyShadeView6.mFooterVisibility = 8;
            emptyShadeView6.setSecondaryVisible(false);
            return;
        }
        EmptyShadeView emptyShadeView7 = this.mEmptyShadeView;
        emptyShadeView7.getClass();
        emptyShadeView7.mFooterVisibility = 0;
        emptyShadeView7.setSecondaryVisible(true);
    }

    public final void updateFirstAndLastBackgroundViews() {
        ExpandableView expandableView;
        int childCount = getChildCount() - 1;
        while (true) {
            if (childCount < 0) {
                expandableView = null;
                break;
            }
            expandableView = (ExpandableView) getChildAt(childCount);
            if (expandableView.getVisibility() != 8 && !(expandableView instanceof StackScrollerDecorView) && expandableView != this.mShelf) {
                break;
            } else {
                childCount--;
            }
        }
        this.mSectionsManager.updateFirstAndLastViewsForAllSections(this.mSections, getChildrenWithBackground());
        this.mAmbientState.mLastVisibleBackgroundChild = expandableView;
        invalidate();
    }

    public final void updateForcedScroll() {
        View view = this.mForcedScroll;
        if (view != null && (!view.hasFocus() || !this.mForcedScroll.isAttachedToWindow())) {
            this.mForcedScroll = null;
        }
        View view2 = this.mForcedScroll;
        if (view2 != null) {
            ExpandableView expandableView = (ExpandableView) view2;
            int positionInLinearLayout = getPositionInLinearLayout(expandableView);
            if (!SecPanelSplitHelper.isEnabled() && this.mForcedScroll != null && !this.mIsExpanded && isPinnedHeadsUp(expandableView) && this.mOrientation == 2) {
                positionInLinearLayout = this.mAmbientState.isOnKeyguard$1() ? 0 : this.mMinimumPaddings + this.mPaddingBetweenElements;
            }
            int targetScrollForView = targetScrollForView(expandableView, positionInLinearLayout);
            int intrinsicHeight = expandableView.getIntrinsicHeight() + positionInLinearLayout;
            int max = Math.max(0, Math.min(targetScrollForView, getScrollRange()));
            if (getOwnScrollY() < max || intrinsicHeight < getOwnScrollY()) {
                setOwnScrollY(max);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateForwardAndBackwardScrollability() {
        /*
            r5 = this;
            int r0 = com.android.systemui.scene.shared.flag.SceneContainerFlag.$r8$clinit
            com.android.systemui.flags.RefactorFlagUtils r0 = com.android.systemui.flags.RefactorFlagUtils.INSTANCE
            boolean r0 = r5.mScrollable
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L1b
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$9 r0 = r5.mScrollAdapter
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.this
            int r3 = r0.getOwnScrollY()
            int r0 = r0.getScrollRange()
            if (r3 < r0) goto L19
            goto L1b
        L19:
            r0 = r2
            goto L1c
        L1b:
            r0 = r1
        L1c:
            boolean r3 = r5.mScrollable
            if (r3 == 0) goto L30
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$9 r3 = r5.mScrollAdapter
            r3.getClass()
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r3 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.this
            int r3 = r3.getOwnScrollY()
            if (r3 != 0) goto L2e
            goto L30
        L2e:
            r3 = r2
            goto L31
        L30:
            r3 = r1
        L31:
            boolean r4 = r5.mForwardScrollable
            if (r0 != r4) goto L39
            boolean r4 = r5.mBackwardScrollable
            if (r3 == r4) goto L3a
        L39:
            r1 = r2
        L3a:
            r5.mForwardScrollable = r0
            r5.mBackwardScrollable = r3
            if (r1 == 0) goto L45
            r0 = 2048(0x800, float:2.87E-42)
            r5.sendAccessibilityEvent(r0)
        L45:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.updateForwardAndBackwardScrollability():void");
    }

    public final void updateImeInset(WindowInsets windowInsets) {
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        this.mImeInset = windowInsets.getInsets(WindowInsets.Type.ime()).bottom;
        if (this.mForcedScroll != null) {
            updateForcedScroll();
        }
        int scrollRange = getScrollRange();
        if (getOwnScrollY() > scrollRange) {
            setOwnScrollY(scrollRange);
        }
    }

    public void updateInterpolatedStackHeight(float f, float f2) {
        this.mAmbientState.mStackHeight = MathUtils.lerp(0.5f * f, f, f2);
    }

    public void updateIntrinsicStackHeight() {
        SceneContainerFlag.isUnexpectedlyInLegacyMode();
    }

    public final void updateLaunchedNotificationClipPath() {
        if (this.mLaunchingNotificationNeedsToBeClipped && this.mLaunchingNotification && this.mExpandingNotificationRow != null) {
            int[] iArr = new int[2];
            getLocationOnScreen(iArr);
            int min = Math.min(this.mLaunchAnimationParams.left - iArr[0], this.mRoundedRectClippingLeft);
            int max = Math.max(this.mLaunchAnimationParams.right - iArr[0], this.mRoundedRectClippingRight);
            int max2 = Math.max(this.mLaunchAnimationParams.bottom - iArr[1], this.mRoundedRectClippingBottom);
            Interpolator interpolator = Interpolators.FAST_OUT_SLOW_IN;
            LaunchAnimationParameters launchAnimationParameters = this.mLaunchAnimationParams;
            launchAnimationParameters.getClass();
            TransitionAnimator.Companion companion = TransitionAnimator.Companion;
            TransitionAnimator.Timings timings = ActivityTransitionAnimator.TIMINGS;
            float f = launchAnimationParameters.linearProgress;
            companion.getClass();
            int min2 = (int) Math.min(MathUtils.lerp(this.mRoundedRectClippingTop, this.mLaunchAnimationParams.top - iArr[1], ((PathInterpolator) interpolator).getInterpolation(TransitionAnimator.Companion.getProgress(timings, f, 0L, 100L))), this.mRoundedRectClippingTop);
            LaunchAnimationParameters launchAnimationParameters2 = this.mLaunchAnimationParams;
            float f2 = launchAnimationParameters2.topCornerRadius;
            float f3 = launchAnimationParameters2.bottomCornerRadius;
            float[] fArr = this.mLaunchedNotificationRadii;
            fArr[0] = f2;
            fArr[1] = f2;
            fArr[2] = f2;
            fArr[3] = f2;
            fArr[4] = f3;
            fArr[5] = f3;
            fArr[6] = f3;
            fArr[7] = f3;
            this.mLaunchedNotificationClipPath.reset();
            this.mLaunchedNotificationClipPath.addRoundRect(min, min2, max, max2, this.mLaunchedNotificationRadii, Path.Direction.CW);
            ExpandableNotificationRow expandableNotificationRow = this.mExpandingNotificationRow;
            ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow.mNotificationParent;
            if (expandableNotificationRow2 != null) {
                expandableNotificationRow = expandableNotificationRow2;
            }
            this.mLaunchedNotificationClipPath.offset((-expandableNotificationRow.getLeft()) - expandableNotificationRow.getTranslationX(), (-expandableNotificationRow.getTop()) - expandableNotificationRow.getTranslationY());
            expandableNotificationRow.mExpandingClipPath = this.mLaunchedNotificationClipPath;
            expandableNotificationRow.invalidate();
            if (this.mShouldUseRoundedRectClipping) {
                invalidate();
            }
        }
    }

    public final void updateNotificationAnimationStates() {
        boolean z = this.mAnimationsEnabled || this.mPulsing;
        this.mShelf.setAnimationsEnabled(z);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            z &= this.mIsExpanded || isPinnedHeadsUp(childAt);
            if (childAt instanceof ExpandableNotificationRow) {
                ((ExpandableNotificationRow) childAt).setAnimationRunning(z);
            }
        }
    }

    public final void updateOwnTranslationZ() {
        ExpandableView firstChildNotGoneInternal;
        setTranslationZ((this.mKeyguardBypassEnabled && this.mAmbientState.isHiddenAtAll() && (firstChildNotGoneInternal = getFirstChildNotGoneInternal()) != null && firstChildNotGoneInternal.showingPulsing()) ? firstChildNotGoneInternal.getTranslationZ() : 0.0f);
    }

    public final void updateScrollability() {
        int i = SceneContainerFlag.$r8$clinit;
        boolean z = !this.mQsFullScreen && getScrollRange() > 0;
        if (z != this.mScrollable) {
            this.mScrollable = z;
            setFocusable(z);
            updateForwardAndBackwardScrollability();
        }
    }

    public final void updateSectionColor() {
        int color = ((ViewGroup) this).mContext.getColor(R.color.notification_section_header_text_color);
        this.mSectionsManager.setHeaderForegroundColors(color, color);
    }

    public final void updateSensitiveness(boolean z, boolean z2) {
        if (z2 != this.mAmbientState.mHideSensitive) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                ((ExpandableView) getChildAt(i)).setHideSensitiveForIntrinsicHeight(z2);
            }
            this.mAmbientState.mHideSensitive = z2;
            if (z && this.mAnimationsEnabled) {
                this.mHideSensitiveNeedsAnimation = true;
                this.mNeedsAnimation = true;
            }
            updateContentHeight();
            requestChildrenUpdate();
        }
    }

    public void updateSplitNotificationShade() {
        SplitShadeStateController splitShadeStateController = this.mSplitShadeStateController;
        getResources();
        ((SplitShadeStateControllerImpl) splitShadeStateController).shouldUseSplitNotificationShade();
    }

    public void updateStackEndHeightAndStackHeight(float f) {
        float f2 = this.mAmbientState.mStackHeight;
        int i = SceneContainerFlag.$r8$clinit;
        if (getQsExpansionFraction$1() > 0.0f || shouldSkipHeightUpdate()) {
            updateInterpolatedStackHeight(this.mAmbientState.mStackEndHeight, f);
        } else {
            float height = getHeight();
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            float max = this.mMaxDisplayedNotifications != -1 ? this.mIntrinsicContentHeight : Math.max(0.0f, (height - Math.max(this.mMaxLayoutHeight - this.mContentHeight, 0)) - getTopPadding());
            this.mAmbientState.mStackEndHeight = max;
            updateInterpolatedStackHeight(max, f);
        }
        if (f2 != this.mAmbientState.mStackHeight) {
            requestChildrenUpdate();
        }
    }

    public final void updateStackPosition(boolean z) {
        int i = SceneContainerFlag.$r8$clinit;
        boolean z2 = onKeyguard() && this.mAmbientState.isNeedsToExpandLocksNoti();
        float f = this.mQsExpansionFraction;
        float f2 = 0.0f;
        float navBarHeight = ((f < 0.8f ? 0.0f : (f - 0.8f) / 0.2f) * ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getNavBarHeight(((ViewGroup) this).mContext)) + ((((getTopPadding() + this.mExtraTopInsetForFullShadeTransition) + this.mAmbientState.mOverExpansion) + 0.0f) - getCurrentOverScrollAmount(false));
        AmbientState ambientState = this.mAmbientState;
        float f3 = ambientState.mExpansionFraction;
        if (z2) {
            f3 = ambientState.mFractionToShade;
        }
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = ambientState.mStatusBarKeyguardViewManager;
        if (statusBarKeyguardViewManager != null && statusBarKeyguardViewManager.isPrimaryBouncerInTransit() && getQsExpansionFraction$1() > 0.0f) {
            f3 = BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(f3);
        }
        if (!SecPanelSplitHelper.isEnabled() && (this.mAmbientState.mExpansionChanging || (onKeyguard() && this.mAmbientState.isNeedsToExpandLocksNoti()))) {
            f2 = navBarHeight - this.mYDiff;
        }
        float lerp = MathUtils.lerp(f2, navBarHeight, f3);
        AmbientState ambientState2 = this.mAmbientState;
        ambientState2.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        ambientState2.mStackY = lerp;
        QuickSettingsControllerImpl$$ExternalSyntheticLambda18 quickSettingsControllerImpl$$ExternalSyntheticLambda18 = this.mOnStackYChanged;
        if (quickSettingsControllerImpl$$ExternalSyntheticLambda18 != null) {
            quickSettingsControllerImpl$$ExternalSyntheticLambda18.accept(Boolean.valueOf(z));
        }
        updateStackEndHeightAndStackHeight(f3);
    }

    public final void updateUseRoundedRectClipping() {
        int i = SceneContainerFlag.$r8$clinit;
        boolean z = this.mIsExpanded && ((getQsExpansionFraction$1() > 0.5f ? 1 : (getQsExpansionFraction$1() == 0.5f ? 0 : -1)) < 0);
        if (z != this.mShouldUseRoundedRectClipping) {
            this.mShouldUseRoundedRectClipping = z;
            invalidate();
        }
    }

    public final void updateViewShadows() {
        for (int i = 0; i < getChildCount(); i++) {
            ExpandableView expandableView = (ExpandableView) getChildAt(i);
            if (expandableView.getVisibility() != 8) {
                this.mTmpSortedChildren.add(expandableView);
            }
        }
        Collections.sort(this.mTmpSortedChildren, this.mViewPositionComparator);
        ExpandableView expandableView2 = null;
        int i2 = 0;
        while (i2 < this.mTmpSortedChildren.size()) {
            ExpandableView expandableView3 = (ExpandableView) this.mTmpSortedChildren.get(i2);
            float translationZ = expandableView3.getTranslationZ();
            float translationZ2 = (expandableView2 == null ? translationZ : expandableView2.getTranslationZ()) - translationZ;
            if (translationZ2 <= 0.0f || translationZ2 >= RUBBER_BAND_FACTOR_NORMAL) {
                expandableView3.setFakeShadowIntensity(0, 0.0f, 0.0f, 0);
            } else {
                expandableView3.setFakeShadowIntensity((int) ((expandableView2.getTranslationY() + expandableView2.mActualHeight) - expandableView3.getTranslationY()), translationZ2 / RUBBER_BAND_FACTOR_NORMAL, expandableView2.getOutlineAlpha(), (int) (expandableView2.getTranslation() + expandableView2.getOutlineTranslation()));
            }
            i2++;
            expandableView2 = expandableView3;
        }
        this.mTmpSortedChildren.clear();
    }

    public final void setOverScrollAmount(float f, final boolean z, boolean z2, boolean z3, final boolean z4) {
        if (z3) {
            StackStateAnimator stackStateAnimator = this.mStateAnimator;
            ValueAnimator valueAnimator = z ? stackStateAnimator.mTopOverScrollAnimator : stackStateAnimator.mBottomOverScrollAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
        }
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        float max = Math.max(0.0f, f);
        if (!z2) {
            float rubberBandFactor = max / getRubberBandFactor(z);
            if (z) {
                this.mOverScrolledTopPixels = rubberBandFactor;
            } else {
                this.mOverScrolledBottomPixels = rubberBandFactor;
            }
            AmbientState ambientState = this.mAmbientState;
            if (z) {
                ambientState.mOverScrollTopAmount = max;
            } else {
                ambientState.mOverScrollBottomAmount = max;
            }
            if (z) {
                notifyOverscrollTopListener(max, z4);
            }
            updateStackPosition(false);
            requestChildrenUpdate();
            return;
        }
        final StackStateAnimator stackStateAnimator2 = this.mStateAnimator;
        float currentOverScrollAmount = stackStateAnimator2.mHostLayout.getCurrentOverScrollAmount(z);
        if (max == currentOverScrollAmount) {
            return;
        }
        ValueAnimator valueAnimator2 = z ? stackStateAnimator2.mTopOverScrollAnimator : stackStateAnimator2.mBottomOverScrollAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(currentOverScrollAmount, max);
        ofFloat.setDuration(360L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator.4
            public final /* synthetic */ boolean val$isRubberbanded;
            public final /* synthetic */ boolean val$onTop;

            public AnonymousClass4(final boolean z5, final boolean z42) {
                r2 = z5;
                r3 = z42;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                StackStateAnimator.this.mHostLayout.setOverScrollAmount(((Float) valueAnimator3.getAnimatedValue()).floatValue(), r2, false, false, r3);
            }
        });
        ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator.5
            public final /* synthetic */ boolean val$onTop;

            public AnonymousClass5(final boolean z5) {
                r2 = z5;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (r2) {
                    StackStateAnimator.this.mTopOverScrollAnimator = null;
                } else {
                    StackStateAnimator.this.mBottomOverScrollAnimator = null;
                }
            }
        });
        ofFloat.start();
        if (z5) {
            stackStateAnimator2.mTopOverScrollAnimator = ofFloat;
        } else {
            stackStateAnimator2.mBottomOverScrollAnimator = ofFloat;
        }
    }

    public final void setOwnScrollY(int i, boolean z) {
        int i2 = SceneContainerFlag.$r8$clinit;
        if (this.mAmbientState.mIsClosing || i == getOwnScrollY()) {
            return;
        }
        int i3 = ((ViewGroup) this).mScrollX;
        onScrollChanged(i3, i, i3, getOwnScrollY());
        this.mOwnScrollY = i;
        AmbientState ambientState = this.mAmbientState;
        int ownScrollY = getOwnScrollY();
        ambientState.getClass();
        ambientState.mScrollY = Math.max(ownScrollY, 0);
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        QuickSettingsControllerImpl$$ExternalSyntheticLambda18 quickSettingsControllerImpl$$ExternalSyntheticLambda18 = this.mScrollListener;
        if (quickSettingsControllerImpl$$ExternalSyntheticLambda18 != null) {
            quickSettingsControllerImpl$$ExternalSyntheticLambda18.accept(Integer.valueOf(getOwnScrollY()));
        }
        SecQSImplAnimatorManager.AnonymousClass1 anonymousClass1 = this.mScrollChangedConsumer;
        if (anonymousClass1 != null) {
            anonymousClass1.accept(Integer.valueOf(this.mOwnScrollY));
        }
        updateForwardAndBackwardScrollability();
        requestChildrenUpdate();
        updateStackPosition(z);
    }
}
