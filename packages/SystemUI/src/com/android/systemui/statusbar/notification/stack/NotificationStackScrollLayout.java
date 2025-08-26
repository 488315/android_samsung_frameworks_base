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
import android.os.Bundle;
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
import android.view.KeyEvent;
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
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
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
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.ExpandHelper;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.ShadeInterpolation;
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
import com.android.systemui.noticenter.NotiCenterPlugin;
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
import com.android.systemui.statusbar.chips.notification.shared.StatusBarNotifChips;
import com.android.systemui.statusbar.domain.interactor.SecStatusBarWindowViewTouchedInteractor;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.LaunchAnimationParameters;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.PhysicsPropertyAnimator;
import com.android.systemui.statusbar.notification.collection.EntryWithDismissStats;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl;
import com.android.systemui.statusbar.notification.emptyshade.shared.ModesEmptyShadeFix;
import com.android.systemui.statusbar.notification.emptyshade.ui.view.EmptyShadeView;
import com.android.systemui.statusbar.notification.footer.ui.view.FooterView;
import com.android.systemui.statusbar.notification.headsup.AvalancheController;
import com.android.systemui.statusbar.notification.headsup.HeadsUpAnimationEvent;
import com.android.systemui.statusbar.notification.headsup.HeadsUpTouchHelper;
import com.android.systemui.statusbar.notification.headsup.NotificationsHunSharedAnimationValues;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.row.NotificationSnooze;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.notification.shared.NotificationContentAlphaOptimization;
import com.android.systemui.statusbar.notification.shared.NotificationHeadsUpCycling;
import com.android.systemui.statusbar.notification.shared.NotificationsLiveDataStoreRefactor;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator;
import com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm;
import com.android.systemui.statusbar.notification.stack.StackStateAnimator;
import com.android.systemui.statusbar.notification.stack.ViewState;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationScrollView;
import com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1$callback$1;
import com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder;
import com.android.systemui.statusbar.notification.ui.viewbinder.HeadsUpNotificationViewBinderKt$isHeadsUpAnimatingAway$1$1;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
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
    public String mLastAlphaZeroTrace;
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
    public SharedNotificationContainerBinder.AnonymousClass3 mOnHeightChangedRunnable;
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
    public float mPreviousTranslationX;
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
    public int mWaterfallTopInset;
    public boolean mWillExpand;
    public int mYDiff;
    public ZenModeController mZenModeController;

    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$10, reason: invalid class name */
    public class AnonymousClass10 implements HeadsUpTouchHelper.Callback {
        public AnonymousClass10() {
        }
    }

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

    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$7, reason: invalid class name */
    public class AnonymousClass7 {
        public AnonymousClass7() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$9, reason: invalid class name */
    public class AnonymousClass9 {
        public AnonymousClass9() {
        }
    }

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
    public NotificationStackScrollLayout(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
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
            /* JADX WARN: Removed duplicated region for block: B:171:0x034b  */
            /* JADX WARN: Removed duplicated region for block: B:292:0x05ca  */
            /* JADX WARN: Removed duplicated region for block: B:479:0x08e5  */
            /* JADX WARN: Removed duplicated region for block: B:62:0x0123  */
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final boolean onPreDraw() {
                StackScrollAlgorithm.SectionProvider sectionProvider;
                AvalancheController avalancheController;
                int i2;
                int i3;
                float f;
                int i4;
                int i5;
                int i6;
                int i7;
                ExpandableNotificationRow expandableNotificationRow;
                ExpandableViewState expandableViewState;
                int i8;
                ExpandableViewState expandableViewState2;
                StackScrollAlgorithm.StackScrollAlgorithmState stackScrollAlgorithmState;
                ExpandableViewState expandableViewState3;
                boolean z;
                boolean z2;
                boolean z3;
                boolean z4 = true;
                int i9 = SceneContainerFlag.$r8$clinit;
                NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                int i10 = notificationStackScrollLayout.mDisplayState;
                if (i10 == 4 || i10 == 3 || notificationStackScrollLayout.getVisibility() == 8) {
                    return true;
                }
                NotificationStackScrollLayout.this.updateForcedScroll();
                NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayout.this;
                notificationStackScrollLayout2.getClass();
                Trace.beginSection("NSSL#updateChildren");
                int i11 = 0;
                if (!notificationStackScrollLayout2.mChildrenToAddAnimated.isEmpty()) {
                    for (int i12 = 0; i12 < notificationStackScrollLayout2.getChildCount(); i12++) {
                        ExpandableView expandableView = (ExpandableView) notificationStackScrollLayout2.getChildAt(i12);
                        if (notificationStackScrollLayout2.mChildrenToAddAnimated.contains(expandableView)) {
                            int positionInLinearLayout = notificationStackScrollLayout2.getPositionInLinearLayout(expandableView);
                            int intrinsicHeight = (expandableView != null ? expandableView.getIntrinsicHeight() : expandableView.getHeight()) + notificationStackScrollLayout2.mPaddingBetweenElements;
                            if (positionInLinearLayout < notificationStackScrollLayout2.getOwnScrollY()) {
                                notificationStackScrollLayout2.setOwnScrollY(notificationStackScrollLayout2.getOwnScrollY() + intrinsicHeight);
                            }
                        }
                    }
                    notificationStackScrollLayout2.clampScrollPosition();
                }
                float f2 = 0.0f;
                notificationStackScrollLayout2.mAmbientState.mCurrentScrollVelocity = notificationStackScrollLayout2.mScroller.isFinished() ? 0.0f : notificationStackScrollLayout2.mScroller.getCurrVelocity();
                StackScrollAlgorithm stackScrollAlgorithm = notificationStackScrollLayout2.mStackScrollAlgorithm;
                AmbientState ambientState = notificationStackScrollLayout2.mAmbientState;
                if (notificationStackScrollLayout2.mSpeedBumpIndexDirty) {
                    notificationStackScrollLayout2.mSpeedBumpIndexDirty = false;
                    int childCount = notificationStackScrollLayout2.getChildCount();
                    for (int i13 = 0; i13 < childCount; i13++) {
                        View childAt = notificationStackScrollLayout2.getChildAt(i13);
                        if (childAt.getVisibility() != 8 && (childAt instanceof ExpandableNotificationRow)) {
                            ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) childAt;
                            int i14 = NotificationBundleUi.$r8$clinit;
                            int i15 = expandableNotificationRow2.getEntryLegacy().mBucket;
                            expandableNotificationRow2.getEntryLegacy().mRanking.isAmbient();
                            boolean z5 = notificationStackScrollLayout2.mHighPriorityBeforeSpeedBump;
                        }
                    }
                }
                int childCount2 = stackScrollAlgorithm.mHostView.getChildCount();
                for (int i16 = 0; i16 < childCount2; i16++) {
                    ((ExpandableView) stackScrollAlgorithm.mHostView.getChildAt(i16)).resetViewState$1();
                }
                int i17 = ambientState.mScrollY;
                StackScrollAlgorithm.StackScrollAlgorithmState stackScrollAlgorithmState2 = stackScrollAlgorithm.mTempAlgorithmState;
                stackScrollAlgorithmState2.getClass();
                float f3 = -i17;
                stackScrollAlgorithmState2.mCurrentYPosition = f3;
                stackScrollAlgorithmState2.mCurrentExpandedYPosition = f3;
                int childCount3 = stackScrollAlgorithm.mHostView.getChildCount();
                stackScrollAlgorithmState2.visibleChildren.clear();
                stackScrollAlgorithmState2.visibleChildren.ensureCapacity(childCount3);
                int i18 = 0;
                boolean z6 = false;
                int i19 = 0;
                while (i18 < childCount3) {
                    ExpandableView expandableView2 = (ExpandableView) stackScrollAlgorithm.mHostView.getChildAt(i18);
                    if (expandableView2.getVisibility() != 8 && expandableView2 != ambientState.mShelf) {
                        z6 = z6;
                        if (expandableView2 instanceof EmptyShadeView) {
                            z6 = z4 ? 1 : 0;
                        }
                        if (expandableView2 instanceof FooterView) {
                            FooterView footerView = (FooterView) expandableView2;
                            if (z6 || i19 == 0) {
                                int i20 = SceneContainerFlag.$r8$clinit;
                                if (!ambientState.mShadeExpanded) {
                                    footerView.mViewState.hidden = z4;
                                }
                            } else if (!(expandableView2 instanceof DndStatusView) || expandableView2.getVisibility() == 8) {
                                expandableView2.mViewState.notGoneIndex = i19;
                                stackScrollAlgorithmState2.visibleChildren.add(expandableView2);
                                i19 += z4 ? 1 : 0;
                                if (expandableView2 instanceof ExpandableNotificationRow) {
                                    ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) expandableView2;
                                    List attachedChildren = expandableNotificationRow3.getAttachedChildren();
                                    if (expandableNotificationRow3.mIsSummaryWithChildren && attachedChildren != null) {
                                        ArrayList arrayList = (ArrayList) attachedChildren;
                                        int size = arrayList.size();
                                        int i21 = i11;
                                        while (i21 < size) {
                                            Object obj = arrayList.get(i21);
                                            i21 += z4 ? 1 : 0;
                                            boolean z7 = z4 ? 1 : 0;
                                            ExpandableNotificationRow expandableNotificationRow4 = (ExpandableNotificationRow) obj;
                                            if (expandableNotificationRow4.getVisibility() != 8) {
                                                expandableNotificationRow4.mViewState.notGoneIndex = i19;
                                                i19++;
                                            }
                                            z4 = z7;
                                        }
                                    }
                                }
                            } else {
                                expandableView2.mViewState.notGoneIndex = i19;
                                stackScrollAlgorithmState2.visibleChildren.addFirst(expandableView2);
                                i19 += z4 ? 1 : 0;
                            }
                        }
                    }
                    i18++;
                    z4 = z4;
                    i11 = 0;
                    z6 = z6;
                }
                boolean z8 = z4 ? 1 : 0;
                int i22 = -1;
                float f4 = -ambientState.mScrollY;
                int i23 = SceneContainerFlag.$r8$clinit;
                boolean zIsOnKeyguard$1 = ambientState.isOnKeyguard$1();
                StackScrollAlgorithm.BypassController bypassController = ambientState.mBypassController;
                float intrinsicHeight2 = ((!zIsOnKeyguard$1 || (((KeyguardBypassController) bypassController).getBypassEnabled() && ambientState.isPulseExpanding())) ? stackScrollAlgorithm.mNotificationScrimPadding : 0.0f) + f4;
                stackScrollAlgorithmState2.firstViewInShelf = null;
                int i24 = 0;
                while (true) {
                    int size2 = stackScrollAlgorithmState2.visibleChildren.size();
                    sectionProvider = ambientState.mSectionProvider;
                    if (i24 >= size2) {
                        break;
                    }
                    ExpandableView expandableView3 = (ExpandableView) stackScrollAlgorithmState2.visibleChildren.get(i24);
                    if (stackScrollAlgorithm.childNeedsGapHeight(sectionProvider, expandableView3, i24 > 0 ? (ExpandableView) stackScrollAlgorithmState2.visibleChildren.get(i24 - 1) : null)) {
                        intrinsicHeight2 += stackScrollAlgorithm.getGapForLocation(ambientState.mFractionToShade, ambientState.isOnKeyguard$1());
                    }
                    if (ambientState.mShelf != null && intrinsicHeight2 >= (ambientState.mStackEndHeight - r8.getHeight()) - stackScrollAlgorithm.mPaddingBetweenElements && !(expandableView3 instanceof FooterView) && stackScrollAlgorithmState2.firstViewInShelf == null) {
                        stackScrollAlgorithmState2.firstViewInShelf = expandableView3;
                    }
                    stackScrollAlgorithm.getClass();
                    intrinsicHeight2 = intrinsicHeight2 + (expandableView3 != null ? expandableView3.getIntrinsicHeight() : expandableView3 == null ? stackScrollAlgorithm.mCollapsedSize : expandableView3.getHeight()) + stackScrollAlgorithm.mPaddingBetweenElements;
                    i24++;
                }
                int i25 = SceneContainerFlag.$r8$clinit;
                float f5 = (!ambientState.isOnKeyguard$1() || (((KeyguardBypassController) bypassController).getBypassEnabled() && ambientState.isPulseExpanding())) ? stackScrollAlgorithm.mNotificationScrimPadding : 0.0f;
                stackScrollAlgorithmState2.mCurrentYPosition += f5;
                stackScrollAlgorithmState2.mCurrentExpandedYPosition += f5;
                int size3 = stackScrollAlgorithmState2.visibleChildren.size();
                stackScrollAlgorithm.mGroupExpandInterpolationY = 0.0f;
                int i26 = 0;
                while (i26 < size3) {
                    ExpandableView expandableView4 = (ExpandableView) stackScrollAlgorithmState2.visibleChildren.get(i26);
                    ExpandableViewState expandableViewState4 = expandableView4.mViewState;
                    expandableViewState4.location = 0;
                    if (expandableView4 instanceof NotificationShelf) {
                        stackScrollAlgorithmState2.mCurrentYPosition -= stackScrollAlgorithm.mGroupExpandInterpolationY;
                    }
                    boolean zChildNeedsGapHeight = stackScrollAlgorithm.childNeedsGapHeight(sectionProvider, expandableView4, i26 > 0 ? (ExpandableView) stackScrollAlgorithmState2.visibleChildren.get(i26 - 1) : null);
                    if ((!ambientState.isOnKeyguard$1() || ambientState.isNeedsToExpandLocksNoti()) && zChildNeedsGapHeight) {
                        float gapForLocation = stackScrollAlgorithm.getGapForLocation(ambientState.mFractionToShade, ambientState.isOnKeyguard$1());
                        stackScrollAlgorithmState2.mCurrentYPosition = (1.0f * gapForLocation) + stackScrollAlgorithmState2.mCurrentYPosition;
                        stackScrollAlgorithmState2.mCurrentExpandedYPosition += gapForLocation;
                    }
                    float fInterpolate = NotificationUtils.interpolate(f2, stackScrollAlgorithm.mMaxGroupExpandedBottomGap, StackScrollAlgorithm.getPreviousGroupExpandFraction(expandableView4));
                    stackScrollAlgorithm.mGroupExpandInterpolationY = fInterpolate;
                    if (i26 > 0) {
                        fInterpolate = Math.max(f2, fInterpolate - NotificationUtils.interpolate(f2, stackScrollAlgorithm.mMaxGroupExpandedBottomGap, StackScrollAlgorithm.getPreviousGroupExpandFraction((ExpandableView) stackScrollAlgorithmState2.visibleChildren.get(i26 - 1))));
                    }
                    float f6 = stackScrollAlgorithmState2.mCurrentYPosition + fInterpolate;
                    stackScrollAlgorithmState2.mCurrentYPosition = f6;
                    expandableViewState4.setYTranslation(f6);
                    int i27 = SceneContainerFlag.$r8$clinit;
                    float stackY = ambientState.getStackY();
                    float f7 = expandableViewState4.height + expandableViewState4.mYTranslation + stackY;
                    boolean z9 = ambientState.mShadeExpanded;
                    boolean zMustStayOnScreen = expandableView4.mustStayOnScreen();
                    float f8 = f2;
                    if (expandableViewState4.mYTranslation >= stackScrollAlgorithm.mNotificationScrimPadding) {
                        stackScrollAlgorithmState = stackScrollAlgorithmState2;
                        expandableViewState3 = expandableViewState4;
                        z = z8;
                    } else {
                        stackScrollAlgorithmState = stackScrollAlgorithmState2;
                        expandableViewState3 = expandableViewState4;
                        z = false;
                    }
                    StackScrollAlgorithm.StackScrollAlgorithmState stackScrollAlgorithmState3 = stackScrollAlgorithmState;
                    stackScrollAlgorithm.maybeUpdateHeadsUpIsVisible(expandableViewState3, z9, zMustStayOnScreen, z, f7, ambientState.mMaxHeadsUpTranslation);
                    if (expandableView4 instanceof FooterView) {
                        if (ambientState.mShadeExpanded) {
                            FooterView.FooterViewState footerViewState = (FooterView.FooterViewState) expandableViewState3;
                            if (stackScrollAlgorithmState3.mCurrentExpandedYPosition + ((float) expandableView4.getIntrinsicHeight()) > ambientState.mStackEndHeight) {
                                z2 = true;
                                footerViewState.hideContent = z2;
                            } else {
                                if (ambientState.mClearAllInProgress) {
                                    int i28 = 0;
                                    while (true) {
                                        if (i28 >= stackScrollAlgorithmState3.visibleChildren.size()) {
                                            z3 = false;
                                            break;
                                        }
                                        View view = (View) stackScrollAlgorithmState3.visibleChildren.get(i28);
                                        if ((view instanceof ExpandableNotificationRow) && !((ExpandableNotificationRow) view).canViewBeCleared()) {
                                            z3 = true;
                                            break;
                                        }
                                        i28++;
                                    }
                                    if (!z3) {
                                    }
                                    footerViewState.hideContent = z2;
                                }
                                z2 = false;
                                footerViewState.hideContent = z2;
                            }
                        } else {
                            expandableViewState3.hidden = z8;
                        }
                    } else {
                        if (expandableView4 instanceof EmptyShadeView) {
                            float stackY2 = (ambientState.mLayoutMaxHeight - ambientState.getStackY()) - (ambientState.mShelf.getHeight() + stackScrollAlgorithm.mPaddingBetweenElements);
                            if (QpRune.QUICK_PANEL_CODE_FOR_POP_OVER && ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) {
                                float translationY = (ambientState.mShelf.getTranslationY() - ambientState.getStackY()) - stackScrollAlgorithm.mPaddingBetweenElements;
                                float f9 = stackScrollAlgorithm.mPaddingTopWhenEmptyShade;
                                stackY2 = translationY - f9;
                                stackY += f9;
                            }
                            expandableViewState3.setYTranslation((stackY2 - expandableView4.getIntrinsicHeight()) / 2.0f);
                        } else if (expandableView4 != ambientState.getTrackedHeadsUpRow()) {
                            if (ambientState.mExpansionChanging || (ambientState.mDragDownOnKeyguard && ambientState.isNeedsToExpandLocksNoti())) {
                                expandableViewState3.hidden = false;
                                ExpandableView expandableView5 = stackScrollAlgorithmState3.firstViewInShelf;
                                expandableViewState3.inShelf = expandableView5 != null && i26 >= stackScrollAlgorithmState3.visibleChildren.indexOf(expandableView5);
                            } else if (ambientState.mShelf != null) {
                                stackScrollAlgorithm.updateViewWithShelf(expandableView4, expandableViewState3, (((!ambientState.mShadeExpanded || ambientState.mDozeAmount == 1.0f || (((KeyguardBypassController) bypassController).getBypassEnabled() && ambientState.isOnKeyguard$1() && !ambientState.isPulseExpanding())) ? ambientState.getInnerHeight$1() : ambientState.mStackHeight) - ambientState.mShelf.getHeight()) - stackScrollAlgorithm.mPaddingBetweenElements, ambientState.mShelf.getHeight());
                            }
                        }
                        expandableViewState3.height = expandableView4.getIntrinsicHeight();
                        if (!expandableView4.isPinned() && !expandableView4.isHeadsUpAnimatingAway() && ambientState.mPulsingRow != expandableView4) {
                            expandableViewState3.height = (int) (expandableViewState3.height * 1.0f);
                        }
                    }
                    stackScrollAlgorithmState3.mCurrentYPosition = ((expandableView4.getIntrinsicHeight() + stackScrollAlgorithm.mPaddingBetweenElements) * 1.0f) + stackScrollAlgorithm.mGroupExpandInterpolationY + stackScrollAlgorithmState3.mCurrentYPosition;
                    stackScrollAlgorithmState3.mCurrentExpandedYPosition = expandableView4.getIntrinsicHeight() + stackScrollAlgorithm.mPaddingBetweenElements + stackScrollAlgorithmState3.mCurrentExpandedYPosition;
                    if (SecPanelSplitHelper.isEnabled() && ambientState.mScrollY == 0) {
                        stackScrollAlgorithmState3.mCurrentYPosition = ((stackScrollAlgorithm.mOverExpansionAmount / (stackScrollAlgorithm.mContext.getResources().getDimension(R.dimen.panel_overshoot_amount) * 1.5f)) * (i26 + 1) * 12) + stackScrollAlgorithmState3.mCurrentYPosition;
                    }
                    ExpandableViewState expandableViewState5 = expandableView4.mViewState;
                    float f10 = stackScrollAlgorithmState3.mCurrentYPosition;
                    expandableViewState5.location = 4;
                    if (f10 <= f8) {
                        expandableViewState5.location = 2;
                    }
                    expandableViewState3.setYTranslation(expandableViewState3.mYTranslation + stackY);
                    i26++;
                    stackScrollAlgorithmState2 = stackScrollAlgorithmState3;
                    f2 = f8;
                    z8 = true;
                }
                float f11 = f2;
                StackScrollAlgorithm.StackScrollAlgorithmState stackScrollAlgorithmState4 = stackScrollAlgorithmState2;
                float f12 = 1.0f;
                int size4 = stackScrollAlgorithmState4.visibleChildren.size();
                int i29 = 0;
                while (true) {
                    if (i29 >= size4) {
                        i29 = -1;
                        break;
                    }
                    ExpandableView expandableView6 = (ExpandableView) stackScrollAlgorithmState4.visibleChildren.get(i29);
                    if ((expandableView6 instanceof ActivatableNotificationView) && (expandableView6.isAboveShelf() || expandableView6.showingPulsing())) {
                        break;
                    }
                    i29++;
                }
                int i30 = size4 - 1;
                float fMin = f11;
                while (i30 >= 0) {
                    boolean z10 = i30 == i29;
                    ExpandableView expandableView7 = (ExpandableView) stackScrollAlgorithmState4.visibleChildren.get(i30);
                    ExpandableViewState expandableViewState6 = expandableView7.mViewState;
                    float f13 = 0;
                    int i31 = SceneContainerFlag.$r8$clinit;
                    if (expandableView7.mustStayOnScreen() && !expandableViewState6.headsUpIsVisible && !ambientState.isDozingAndNotPulsing(expandableView7) && expandableViewState6.mYTranslation < ambientState.getTopPadding() + ambientState.mStackTranslation) {
                        fMin = fMin != f11 ? fMin + f12 : fMin + Math.min(f12, ((ambientState.getTopPadding() + ambientState.mStackTranslation) - expandableViewState6.mYTranslation) / expandableViewState6.height);
                        expandableViewState6.setZTranslation((stackScrollAlgorithm.mPinnedZTranslationExtra * fMin) + f13);
                    } else if (z10) {
                        NotificationShelf notificationShelf = ambientState.mShelf;
                        int height = notificationShelf == null ? 0 : notificationShelf.getHeight();
                        float topPadding = ambientState.getTopPadding() + (ambientState.getInnerHeight$1() - height) + ambientState.mStackTranslation;
                        float intrinsicHeight3 = expandableViewState6.mYTranslation + expandableView7.getIntrinsicHeight() + stackScrollAlgorithm.mPaddingBetweenElements;
                        if (topPadding > intrinsicHeight3) {
                            expandableViewState6.setZTranslation(f13);
                        } else {
                            float f14 = (intrinsicHeight3 - topPadding) / height;
                            if (Float.isNaN(f14)) {
                                f14 = 1.0f;
                            }
                            expandableViewState6.setZTranslation((Math.min(f14, 1.0f) * stackScrollAlgorithm.mPinnedZTranslationExtra) + f13);
                        }
                    } else {
                        expandableViewState6.setZTranslation(f13);
                    }
                    expandableViewState6.setZTranslation(((1.0f - expandableView7.getHeaderVisibleAmount()) * stackScrollAlgorithm.mPinnedZTranslationExtra) + expandableViewState6.mZTranslation);
                    i30--;
                    f12 = 1.0f;
                }
                int size5 = stackScrollAlgorithmState4.visibleChildren.size();
                int i32 = SceneContainerFlag.$r8$clinit;
                float f15 = stackScrollAlgorithm.mHeadsUpInset - ambientState.mStackTopMargin;
                ExpandableNotificationRow trackedHeadsUpRow = ambientState.getTrackedHeadsUpRow();
                if (trackedHeadsUpRow != null && (expandableViewState2 = trackedHeadsUpRow.mViewState) != null) {
                    expandableViewState2.setYTranslation(MathUtils.lerp(f15, expandableViewState2.mYTranslation - ambientState.mStackTranslation, ambientState.mAppearFraction));
                }
                int i33 = -1;
                int i34 = 0;
                ExpandableNotificationRow expandableNotificationRow5 = null;
                while (true) {
                    avalancheController = ambientState.mAvalancheController;
                    if (i34 >= size5) {
                        break;
                    }
                    View view2 = (View) stackScrollAlgorithmState4.visibleChildren.get(i34);
                    if (view2 instanceof ExpandableNotificationRow) {
                        ExpandableNotificationRow expandableNotificationRow6 = (ExpandableNotificationRow) view2;
                        if (expandableNotificationRow6.mIsHeadsUp || expandableNotificationRow6.mHeadsupDisappearRunning) {
                            ExpandableViewState expandableViewState7 = expandableNotificationRow6.mViewState;
                            int i35 = SceneContainerFlag.$r8$clinit;
                            boolean zMustStayOnScreen2 = expandableNotificationRow6.mustStayOnScreen();
                            if (expandableNotificationRow5 == null && zMustStayOnScreen2 && !expandableViewState7.headsUpIsVisible) {
                                expandableViewState7.location = 1;
                                expandableNotificationRow5 = expandableNotificationRow6;
                            }
                            boolean z11 = expandableNotificationRow5 == expandableNotificationRow6;
                            float f16 = expandableViewState7.mYTranslation + expandableViewState7.height;
                            if (stackScrollAlgorithm.mIsExpanded) {
                                boolean zMustStayOnScreen3 = expandableNotificationRow6.mustStayOnScreen();
                                boolean z12 = expandableViewState7.headsUpIsVisible;
                                boolean zShowingPulsing = expandableNotificationRow6.showingPulsing();
                                boolean zIsOnKeyguard$12 = ambientState.isOnKeyguard$1();
                                int i36 = NotificationBundleUi.$r8$clinit;
                                i6 = i34;
                                expandableViewState = expandableViewState7;
                                i5 = size5;
                                expandableNotificationRow = expandableNotificationRow6;
                                stackScrollAlgorithm.shouldHunBeVisibleWhenScrolled(zMustStayOnScreen3, z12, zShowingPulsing, zIsOnKeyguard$12, expandableNotificationRow6.getEntryLegacy().isStickyAndNotDemoted());
                            } else {
                                i5 = size5;
                                i6 = i34;
                                expandableNotificationRow = expandableNotificationRow6;
                                expandableViewState = expandableViewState7;
                            }
                            if (expandableNotificationRow.mPinnedStatus.isPinned()) {
                                expandableViewState.setYTranslation(Math.max(expandableViewState.mYTranslation, f15));
                                expandableViewState.height = Math.max(expandableNotificationRow.getIntrinsicHeight(), expandableViewState.height);
                                int i37 = NotificationHeadsUpCycling.$r8$clinit;
                                if (StackScrollAlgorithm.isCyclingIn(expandableNotificationRow, ambientState)) {
                                    i7 = i22;
                                    if (i33 == i7) {
                                        i33 = expandableViewState.height;
                                    }
                                } else {
                                    i7 = i22;
                                }
                                expandableViewState.hidden = false;
                                ExpandableViewState expandableViewState8 = expandableNotificationRow5 == null ? null : expandableNotificationRow5.mViewState;
                                if (expandableViewState8 != null && !z11 && (!stackScrollAlgorithm.mIsExpanded || f16 > expandableViewState8.mYTranslation + expandableViewState8.height)) {
                                    expandableViewState.height = expandableNotificationRow.getIntrinsicHeight();
                                }
                                if (!stackScrollAlgorithm.mIsExpanded && z11 && (i8 = ambientState.mScrollY) > 0) {
                                    expandableViewState.setYTranslation(expandableViewState.mYTranslation - i8);
                                }
                            } else {
                                i7 = i22;
                            }
                            if (expandableNotificationRow.mHeadsupDisappearRunning && !stackScrollAlgorithm.mIsExpanded && z11) {
                                int i38 = NotificationHeadsUpCycling.$r8$clinit;
                                if (expandableNotificationRow.getKey().equals(avalancheController.previousHunKey)) {
                                    expandableViewState.setYTranslation(Math.max(expandableViewState.mYTranslation, f15) + (i33 >= expandableViewState.height ? i33 - r1 : 0) + stackScrollAlgorithm.mHeadsUpCyclingPadding);
                                    i33 = i7;
                                } else if (ambientState.mDozing) {
                                    expandableViewState.setYTranslation(Math.max(expandableViewState.mYTranslation, f15));
                                } else {
                                    boolean z13 = expandableViewState.mYTranslation + ((float) expandableViewState.height) >= ambientState.mMaxHeadsUpTranslation;
                                    int i39 = NotificationsHunSharedAnimationValues.$r8$clinit;
                                    if (z13) {
                                        expandableViewState.setYTranslation(stackScrollAlgorithm.mHeadsUpAppearHeightBottom + stackScrollAlgorithm.mHeadsUpAppearStartAboveScreen);
                                    } else {
                                        expandableViewState.setYTranslation((-ambientState.mStackTopMargin) - stackScrollAlgorithm.mHeadsUpAppearStartAboveScreen);
                                    }
                                }
                                expandableViewState.hidden = false;
                            }
                        } else {
                            i5 = size5;
                            i6 = i34;
                            i7 = i22;
                        }
                    }
                    i34 = i6 + 1;
                    i22 = i7;
                    size5 = i5;
                }
                stackScrollAlgorithm.updatePulsingStates(stackScrollAlgorithmState4, ambientState);
                boolean z14 = ambientState.mHideSensitive;
                int size6 = stackScrollAlgorithmState4.visibleChildren.size();
                for (int i40 = 0; i40 < size6; i40++) {
                    ExpandableViewState expandableViewState9 = ((ExpandableView) stackScrollAlgorithmState4.visibleChildren.get(i40)).mViewState;
                    expandableViewState9.dimmed = ambientState.mDimmed && !(ambientState.isPulseExpanding() && ambientState.mDozeAmount == 1.0f);
                    expandableViewState9.hideSensitive = z14;
                }
                int i41 = SceneContainerFlag.$r8$clinit;
                float stackY3 = ambientState.getStackY() - ambientState.mScrollY;
                if (ambientState.isOnKeyguard$1()) {
                    stackY3 = f11;
                }
                float fMax = ambientState.mNotificationScrimTop;
                float f17 = f11;
                boolean z15 = true;
                int i42 = 0;
                for (int size7 = stackScrollAlgorithmState4.visibleChildren.size(); i42 < size7; size7 = i4) {
                    ExpandableView expandableView8 = (ExpandableView) stackScrollAlgorithmState4.visibleChildren.get(i42);
                    ExpandableViewState expandableViewState10 = expandableView8.mViewState;
                    if (!expandableView8.mustStayOnScreen() || expandableViewState10.headsUpIsVisible) {
                        fMax = Math.max(stackY3, fMax);
                    }
                    float f18 = expandableViewState10.mYTranslation;
                    float f19 = expandableViewState10.height + f18;
                    float f20 = stackY3;
                    boolean z16 = expandableView8 instanceof ExpandableNotificationRow;
                    boolean z17 = z16 && expandableView8.isPinned();
                    if (!stackScrollAlgorithm.mClipNotificationScrollToTop || z15 || ((!z17 && (!expandableView8.isHeadsUpAnimatingAway() || z15)) || f19 <= f17 || ambientState.mShadeExpanded)) {
                        i4 = size7;
                        expandableViewState10.clipBottomAmount = 0;
                    } else {
                        int i43 = NotificationHeadsUpCycling.$r8$clinit;
                        if (z16) {
                            i4 = size7;
                            ((ExpandableNotificationRow) expandableView8).getKey().equals(avalancheController.previousHunKey);
                        } else {
                            i4 = size7;
                        }
                        expandableViewState10.clipBottomAmount = stackScrollAlgorithm.mEnableNotificationClipping ? (int) (f19 - f17) : 0;
                    }
                    boolean z18 = expandableViewState10.hidden;
                    if (expandableViewState10.inShelf || f18 >= fMax) {
                        expandableViewState10.clipTopAmount = 0;
                    } else {
                        expandableViewState10.clipTopAmount = (int) (fMax - f18);
                        if (fMax > f19) {
                            expandableViewState10.hidden = true;
                        } else {
                            expandableViewState10.hidden = z18;
                        }
                    }
                    if (z15) {
                        f17 = f19;
                    }
                    if (z17) {
                        z15 = false;
                    }
                    if (!expandableView8.isTransparent()) {
                        if (!z17) {
                            f18 = f19;
                        }
                        fMax = Math.max(fMax, f18);
                    }
                    i42++;
                    stackY3 = f20;
                }
                int size8 = stackScrollAlgorithmState4.visibleChildren.size();
                for (int i44 = 0; i44 < size8; i44++) {
                    ((ExpandableView) stackScrollAlgorithmState4.visibleChildren.get(i44)).mViewState.getClass();
                }
                NotificationShelf notificationShelf2 = ambientState.mShelf;
                if (notificationShelf2 != null) {
                    notificationShelf2.updateState(stackScrollAlgorithmState4, ambientState);
                }
                ArrayList arrayList2 = stackScrollAlgorithmState4.visibleChildren;
                int size9 = arrayList2.size();
                boolean z19 = false;
                int i45 = 0;
                while (i45 < size9) {
                    Object obj2 = arrayList2.get(i45);
                    i45++;
                    ExpandableView expandableView9 = (ExpandableView) obj2;
                    ExpandableViewState expandableViewState11 = expandableView9.mViewState;
                    if (ambientState.mShadeExpanded && expandableView9 == ambientState.getTrackedHeadsUpRow()) {
                        expandableViewState11.setAlpha(1.0f);
                    } else {
                        int i46 = SceneContainerFlag.$r8$clinit;
                        if (ambientState.isOnKeyguard$1()) {
                            if (expandableView9.isHeadsUpState()) {
                                expandableViewState11.setAlpha(1.0f - ambientState.mHideAmount);
                            } else {
                                expandableViewState11.setAlpha(1.0f - ambientState.mDozeAmount);
                                if (ambientState.mDozeAmount > f11) {
                                    z19 = true;
                                }
                            }
                            if (ambientState.isNeedsToExpandLocksNoti()) {
                                expandableViewState11.setAlpha(ShadeInterpolation.getNotifContentAlpha(ambientState.mFractionToShade));
                            }
                        } else if (ambientState.mExpansionChanging) {
                            float f21 = ambientState.mExpansionFraction;
                            if (!expandableView9.mustStayOnScreen()) {
                                stackScrollAlgorithmState4.visibleChildren.indexOf(expandableView9);
                                if (ambientState.mIsCollapsingHeadsup) {
                                    expandableViewState11.setAlpha(f11);
                                } else if (SecPanelSplitHelper.isEnabled()) {
                                    expandableViewState11.setAlpha(ShadeInterpolation.getNotifContentAlpha(f21));
                                } else {
                                    expandableViewState11.setAlpha(ShadeInterpolation.getContentAlpha(f21));
                                }
                            }
                        }
                    }
                    boolean z20 = expandableView9 instanceof EmptyShadeView;
                    if (z20 && ambientState.mExpansionFraction == 0.0f) {
                        expandableViewState11.setAlpha(0.0f);
                    }
                    if (z20 && ambientState.isOnKeyguard$1()) {
                        expandableViewState11.setAlpha(ShadeInterpolation.getContentAlpha(ambientState.mFractionToShade));
                    }
                    NotificationShelf notificationShelf3 = ambientState.mShelf;
                    if (notificationShelf3 != null) {
                        ExpandableViewState expandableViewState12 = notificationShelf3.mViewState;
                        if (expandableViewState12.hidden) {
                            f = 0.0f;
                        } else {
                            float f22 = expandableViewState12.mYTranslation;
                            float f23 = expandableViewState11.mYTranslation;
                            boolean z21 = expandableViewState11.inShelf && ambientState.isOnKeyguard$1();
                            if ((f23 >= f22 || z21) && !z19) {
                                f = 0.0f;
                                expandableViewState11.setAlpha(0.0f);
                            }
                        }
                    }
                    f11 = f;
                }
                StackScrollAlgorithm.getNotificationChildrenStates(stackScrollAlgorithmState4);
                int childCount4 = notificationStackScrollLayout2.getChildCount();
                int i47 = 0;
                while (i47 < childCount4) {
                    View childAt2 = notificationStackScrollLayout2.getChildAt(i47);
                    if (childAt2 instanceof ExpandableNotificationRow) {
                        ExpandableNotificationRow expandableNotificationRow7 = (ExpandableNotificationRow) childAt2;
                        if (expandableNotificationRow7.mIsSummaryWithChildren) {
                            NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow7.mChildrenContainer;
                            int notificationChildCount = notificationChildrenContainer.getNotificationChildCount();
                            int i48 = 0;
                            while (i48 < notificationChildCount) {
                                ExpandableNotificationRow expandableNotificationRow8 = (ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(i48);
                                if (!expandableNotificationRow8.mEntry.isOngoingActivity() || expandableNotificationRow8.mViewState.hasGradient) {
                                    i3 = 1;
                                } else {
                                    expandableNotificationRow8.applyGradientBackground(expandableNotificationRow8.getWidth() - (notificationStackScrollLayout2.mSidePaddings * 2), expandableNotificationRow8.getIntrinsicHeight(), expandableNotificationRow8.mEntry.isPromotedState());
                                    i3 = 1;
                                    expandableNotificationRow8.mViewState.hasGradient = true;
                                }
                                i48 += i3;
                            }
                        } else if (expandableNotificationRow7.mEntry.isOngoingActivity() && !expandableNotificationRow7.mViewState.hasGradient) {
                            expandableNotificationRow7.applyGradientBackground(expandableNotificationRow7.getWidth() - (notificationStackScrollLayout2.mSidePaddings * 2), expandableNotificationRow7.getIntrinsicHeight(), expandableNotificationRow7.mEntry.isPromotedState());
                            i2 = 1;
                            expandableNotificationRow7.mViewState.hasGradient = true;
                        }
                        i2 = 1;
                    } else {
                        i2 = 1;
                    }
                    i47 += i2;
                }
                if (!notificationStackScrollLayout2.mStateAnimator.mAnimatorSet.isEmpty() || notificationStackScrollLayout2.mNeedsAnimation) {
                    notificationStackScrollLayout2.startAnimationToState$1();
                } else {
                    notificationStackScrollLayout2.applyCurrentState();
                }
                Trace.endSection();
                NotificationStackScrollLayout notificationStackScrollLayout3 = NotificationStackScrollLayout.this;
                notificationStackScrollLayout3.mChildrenUpdateRequested = false;
                notificationStackScrollLayout3.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
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
                NotificationStackScrollLayout notificationStackScrollLayout = this.f$0;
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
                HashMap map = new HashMap();
                int childCount = notificationStackScrollLayout.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    ExpandableView expandableView = (ExpandableView) notificationStackScrollLayout.getChildAt(i2);
                    if (expandableView instanceof ExpandableNotificationRow) {
                        ((ExpandableNotificationRow) expandableView).collectVisibleLocations(map);
                    }
                }
                return map;
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
        this.mPreviousTranslationX = 0.0f;
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

    /* JADX WARN: Removed duplicated region for block: B:28:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x005d A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean includeChildInClearAll(ExpandableNotificationRow expandableNotificationRow, int i) {
        boolean z;
        if ((expandableNotificationRow == null || expandableNotificationRow.areGutsExposed() || !expandableNotificationRow.hasFinishedInitialization()) ? false : expandableNotificationRow.canViewBeCleared()) {
            int i2 = NotificationBundleUi.$r8$clinit;
            int i3 = expandableNotificationRow.getEntryLegacy().mBucket;
            if (i == 0) {
                z = true;
                if (!z) {
                    return true;
                }
            } else {
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
                            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unknown selection: "));
                        }
                        NotiCenterPlugin notiCenterPlugin = NotiCenterPlugin.INSTANCE;
                        String packageName = expandableNotificationRow.mEntry.mSbn.getPackageName();
                        notiCenterPlugin.getClass();
                        HashSet hashSet = NotiCenterPlugin.noclearAppList;
                        z = !(hashSet != null ? hashSet.contains(packageName) : false);
                    } else if (i3 != 20) {
                        z = false;
                    }
                } else if (i3 < 20) {
                }
                if (!z) {
                }
            }
        }
        return false;
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
            LogMessage logMessageObtain = logBuffer.obtain("NotificationStackScroll", logLevel, notificationStackScrollLogger$$ExternalSyntheticLambda3, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.str1 = str;
            logMessageImpl.int1 = i;
            logBuffer.commit(logMessageObtain);
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
                ((ChannelCoroutine) notificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1$callback$1.$$this$conflatedCallbackFlow).mo3476trySendJP2dKIU(this.collectVisibleLocationsCallable);
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
            boolean zIsOnKeyguard$1 = ambientState.isOnKeyguard$1();
            stackScrollAlgorithm.getClass();
            if (expandableView != null && StackScrollAlgorithm.getPreviousGroupExpandFraction(expandableView) > 0.0f) {
                return NotificationUtils.interpolate(0.0f, stackScrollAlgorithm.mMaxGroupExpandedBottomGap, StackScrollAlgorithm.getPreviousGroupExpandFraction(expandableView)) + (stackScrollAlgorithm.childNeedsGapHeight(notificationSectionsManager, expandableView2, expandableView) ? stackScrollAlgorithm.getGapForLocation(f, zIsOnKeyguard$1) : 0.0f);
            }
            if (stackScrollAlgorithm.childNeedsGapHeight(notificationSectionsManager, expandableView2, expandableView)) {
                return stackScrollAlgorithm.getGapForLocation(f, zIsOnKeyguard$1);
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
        int iIndexOfChild = indexOfChild(expandableView);
        boolean z = false;
        if (iIndexOfChild == -1) {
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
        if (expandableView == null || expandableView.getParent() != this || iIndexOfChild == i) {
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
                final NotificationStackScrollLayout notificationStackScrollLayout = this.f$0;
                final ArrayList arrayList5 = arrayList3;
                final int i6 = i;
                boolean z4 = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
                notificationStackScrollLayout.getClass();
                if (((Boolean) obj3).booleanValue()) {
                    notificationStackScrollLayout.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayout;
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
        int iMax = 60;
        int i7 = 0;
        while (i6 >= 0) {
            View view = (View) arrayList.get(i6);
            NotificationStackScrollLayout$$ExternalSyntheticLambda5 notificationStackScrollLayout$$ExternalSyntheticLambda5 = i6 == 0 ? consumer : 0;
            if (view instanceof SectionHeaderView) {
                ((StackScrollerDecorView) view).setContentVisible(z2, z4, notificationStackScrollLayout$$ExternalSyntheticLambda5);
            } else {
                this.mSwipeHelper.dismissChild(view, 0.0f, notificationStackScrollLayout$$ExternalSyntheticLambda5, i7, true, 200L, true);
            }
            iMax = Math.max(30, iMax - 5);
            i7 += iMax;
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
                        LogMessage logMessageObtain = logBuffer.obtain("NotificationStackScroll", logLevel, notificationStackScrollLogger$$ExternalSyntheticLambda0, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                        logMessageImpl.str1 = str2;
                        logMessageImpl.str2 = str;
                        logBuffer.commit(logMessageObtain);
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
        int iSave = canvas.save();
        if (this.mShouldUseRoundedRectClipping) {
            canvas.clipPath(this.mRoundedClipPath);
        }
        canvas.drawRenderNode(this.mBlurNode);
        canvas.restoreToCount(iSave);
        for (int i = 0; i < getChildCount(); i++) {
            ExpandableView expandableView = (ExpandableView) getChildAt(i);
            if (expandableView != null ? expandableView.isHeadsUpState() : false) {
                super.drawChild(canvas, expandableView, getDrawingTime());
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int i;
        int i2;
        View childAt;
        ExpandableNotificationRow expandableNotificationRow;
        int i3;
        int i4 = 0;
        boolean z = keyEvent.getAction() == 0;
        View childAt2 = null;
        if (z && keyEvent.getKeyCode() == 19) {
            View viewFindFocus = findFocus();
            if (viewFindFocus instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) viewFindFocus;
                int childCount = getChildCount();
                while (true) {
                    if (i4 >= childCount) {
                        expandableNotificationRow = null;
                        break;
                    }
                    View childAt3 = getChildAt(i4);
                    if ((childAt3 instanceof ExpandableNotificationRow) && childAt3.getVisibility() != 8 && childAt3 != this.mShelf) {
                        expandableNotificationRow = (ExpandableNotificationRow) childAt3;
                        break;
                    }
                    i4++;
                }
                if (expandableNotificationRow2 != null && expandableNotificationRow2.equals(expandableNotificationRow)) {
                    return super.dispatchKeyEvent(keyEvent);
                }
                int iIndexOfChild = indexOfChild(expandableNotificationRow2);
                for (int i5 = 1; i5 < getChildCount() - iIndexOfChild && (i3 = iIndexOfChild - i5) >= 0 && ((childAt2 = getChildAt(i3)) == null || childAt2.getVisibility() != 0); i5++) {
                }
                if (childAt2 != null) {
                    childAt2.requestFocus();
                    return true;
                }
            } else if (this.mShelf.hasFocus() && (childAt = getChildAt(indexOfChild(this.mShelf) - 1)) != null) {
                this.mOwnScrollY = getScrollRange();
                if (this.mAnimationsEnabled) {
                    this.mNeedsAnimation = true;
                }
                requestChildrenUpdate();
                childAt.post(new NotificationStackScrollLayout$$ExternalSyntheticLambda0(childAt));
                return true;
            }
        } else if ((z && keyEvent.getKeyCode() == 20) || (z && keyEvent.getKeyCode() == 61)) {
            View viewFindFocus2 = findFocus();
            if (viewFindFocus2 instanceof ExpandableNotificationRow) {
                int iIndexOfChild2 = indexOfChild((ExpandableNotificationRow) viewFindFocus2);
                for (int i6 = 1; i6 < getChildCount() - iIndexOfChild2 && (i2 = iIndexOfChild2 + i6) < getChildCount() && ((childAt2 = getChildAt(i2)) == null || childAt2.getVisibility() != 0); i6++) {
                }
                if (childAt2 != null && !(childAt2 instanceof NotificationShelf)) {
                    int iScrollAmountForKeyboardFocus = scrollAmountForKeyboardFocus(iIndexOfChild2);
                    if (iScrollAmountForKeyboardFocus != 0) {
                        if (this.mOwnScrollY + iScrollAmountForKeyboardFocus > getScrollRange()) {
                            this.mOwnScrollY = getScrollRange();
                        } else {
                            this.mOwnScrollY += iScrollAmountForKeyboardFocus;
                        }
                        if (this.mAnimationsEnabled) {
                            this.mNeedsAnimation = true;
                        }
                        requestChildrenUpdate();
                    }
                    childAt2.requestFocus();
                    return true;
                }
                if (childAt2 != null) {
                    this.mShelf.requestFocus();
                    return true;
                }
            } else if (viewFindFocus2 instanceof SectionHeaderView) {
                int iIndexOfChild3 = indexOfChild((SectionHeaderView) viewFindFocus2);
                for (int i7 = 1; i7 < getChildCount() - iIndexOfChild3 && (i = iIndexOfChild3 + i7) < getChildCount() && (childAt2 = getChildAt(i)) == null; i7++) {
                }
                if (childAt2 != null) {
                    int iScrollAmountForKeyboardFocus2 = scrollAmountForKeyboardFocus(iIndexOfChild3);
                    if (iScrollAmountForKeyboardFocus2 != 0) {
                        if (this.mOwnScrollY + iScrollAmountForKeyboardFocus2 > getScrollRange()) {
                            this.mOwnScrollY = getScrollRange();
                        } else {
                            this.mOwnScrollY += iScrollAmountForKeyboardFocus2;
                        }
                        if (this.mAnimationsEnabled) {
                            this.mNeedsAnimation = true;
                        }
                        requestChildrenUpdate();
                    }
                    childAt2.requestFocus();
                    return true;
                }
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int i = SceneContainerFlag.$r8$clinit;
        boolean zDispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        TouchLogger.Companion.getClass();
        TouchLogger.Companion.logDispatchTouch(motionEvent, "StackScroller", zDispatchTouchEvent);
        return zDispatchTouchEvent;
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
        boolean zDrawChild = super.drawChild(canvas, view, j);
        canvas.restore();
        return zDrawChild;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, final String[] strArr) {
        final IndentingPrintWriter indentingPrintWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        final long jElapsedRealtime = SystemClock.elapsedRealtime();
        indentingPrintWriterAsIndenting.println("Internal state:");
        DumpUtilsKt.withIncreasedIndent(indentingPrintWriterAsIndenting, new Runnable(indentingPrintWriterAsIndenting, jElapsedRealtime, strArr) { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda6
            public final /* synthetic */ IndentingPrintWriter f$1;
            public final /* synthetic */ long f$2;

            @Override // java.lang.Runnable
            public final void run() {
                NotificationStackScrollLayout notificationStackScrollLayout = this.f$0;
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
                    DumpUtilsKt.println(indentingPrintWriter, "contentHeight", Integer.valueOf(notificationStackScrollLayout.getContentHeight()));
                    DumpUtilsKt.println(indentingPrintWriter, "topPadding", Integer.valueOf(notificationStackScrollLayout.getTopPadding()));
                    DumpUtilsKt.println(indentingPrintWriter, "maxTopPadding", Integer.valueOf(notificationStackScrollLayout.mMaxTopPadding));
                    DumpUtilsKt.println(indentingPrintWriter, "qsExpandFraction", Float.valueOf(notificationStackScrollLayout.getQsExpansionFraction$1()));
                } catch (Throwable th) {
                    indentingPrintWriter.decreaseIndent();
                    throw th;
                }
            }
        });
        indentingPrintWriterAsIndenting.println();
        indentingPrintWriterAsIndenting.println("Contents:");
        DumpUtilsKt.withIncreasedIndent(indentingPrintWriterAsIndenting, new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                NotificationStackScrollLayout notificationStackScrollLayout = this.f$0;
                PrintWriter printWriter2 = indentingPrintWriterAsIndenting;
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
        if (str3 == null) {
            str3 = "NULL";
        }
        PanelScreenShotLogger.addLogItem(arrayList, "mLastVisibleTrace", str3);
        String str4 = this.mLastAlphaZeroTrace;
        PanelScreenShotLogger.addLogItem(arrayList, "mLastAlphaZeroTrace", str4 != null ? str4 : "NULL");
        PanelScreenShotLogger.addLogItem(arrayList, "appIconColor", Integer.toHexString(getContext().getColor(R.color.notification_app_icon_color)));
        arrayList.add("\n\n");
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) childAt;
                ExpandableViewState expandableViewState = expandableNotificationRow.mViewState;
                PanelScreenShotLogger panelScreenShotLogger = PanelScreenShotLogger.INSTANCE;
                String str5 = expandableNotificationRow.mLoggingKey;
                panelScreenShotLogger.getClass();
                PanelScreenShotLogger.addLogItem(arrayList, "key", str5);
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
        int positionInLinearLayout = this.mAmbientState.mStackTopMargin;
        boolean areAnyNotificationsPresentValue = this.mController.mActiveNotificationsInteractor.getAreAnyNotificationsPresentValue();
        boolean z = NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW;
        if (!z ? this.mEmptyShadeView.getVisibility() == 8 && areAnyNotificationsPresentValue : areAnyNotificationsPresentValue) {
            positionInLinearLayout = (z || this.mEmptyShadeView.getVisibility() == 8) ? 0 : this.mEmptyShadeView.getHeight();
        } else if (isHeadsUpTransition() || (this.mInHeadsUpPinnedMode && !this.mAmbientState.mDozing)) {
            if (this.mShelf.getVisibility() != 8) {
                positionInLinearLayout += this.mShelf.getHeight() + this.mPaddingBetweenElements;
            }
            positionInLinearLayout += getPositionInLinearLayout(this.mAmbientState.getTrackedHeadsUpRow()) + getTopHeadsUpPinnedHeight();
        } else if (this.mShelf.getVisibility() != 8) {
            positionInLinearLayout += this.mShelf.getHeight();
        }
        return positionInLinearLayout + (onKeyguard() ? getTopPadding() : this.mIntrinsicPadding);
    }

    public final float getAppearStartPosition() {
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (!isHeadsUpTransition()) {
            return this.mShelf.getHeight() + this.mWaterfallTopInset;
        }
        return (this.mHeadsUpInset - this.mAmbientState.mStackTopMargin) + (getFirstVisibleSection() != null ? r0.mFirstVisibleChild.getPinnedHeadsUpHeight() : 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0072  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ExpandableView getChildAtPosition(float f, float f2, boolean z, boolean z2) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ExpandableView expandableView = (ExpandableView) getChildAt(i);
            if (expandableView.getVisibility() == 0 && (!z2 || !(expandableView instanceof StackScrollerDecorView))) {
                float translationY = expandableView.getTranslationY();
                float fMax = Math.max(0, expandableView.mClipTopAmount) + translationY;
                float f3 = (expandableView.mActualHeight + translationY) - expandableView.mClipBottomAmount;
                int width = getWidth();
                if ((f3 - fMax >= this.mMinInteractionHeight || !z) && f2 >= fMax && f2 <= f3 && f >= 0 && f <= width) {
                    if (!(expandableView instanceof ExpandableNotificationRow)) {
                        return expandableView;
                    }
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
                    int i2 = NotificationBundleUi.$r8$clinit;
                    NotificationEntry entryLegacy = expandableNotificationRow.getEntryLegacy();
                    ExpandableNotificationRow expandableNotificationRow2 = this.mTopHeadsUpRow;
                    if (expandableNotificationRow2 != null) {
                        boolean z3 = ((GroupMembershipManagerImpl) this.mGroupMembershipManager).getGroupSummary(expandableNotificationRow2.getEntryLegacy()) == entryLegacy;
                        if (this.mIsExpanded || !expandableNotificationRow.mIsHeadsUp || !expandableNotificationRow.mPinnedStatus.isPinned() || this.mTopHeadsUpRow == expandableNotificationRow || z3) {
                            return expandableNotificationRow.getViewAtPosition(f2 - translationY);
                        }
                    }
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

    public final int getContentHeight() {
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        return this.mContentHeight;
    }

    public final float getCurrentOverScrollAmount(boolean z) {
        AmbientState ambientState = this.mAmbientState;
        return z ? ambientState.mOverScrollTopAmount : ambientState.mOverScrollBottomAmount;
    }

    public final String getDndStatusText(ZenModeController zenModeController) {
        ZenModeConfig zenModeConfig;
        Uri uri;
        String string = "";
        if (zenModeController != null && (zenModeConfig = ((ZenModeControllerImpl) zenModeController).mConfig) != null) {
            ZenModeConfig.ZenRule zenRule = zenModeConfig.manualRule;
            if (zenRule != null && zenRule.conditionId == null) {
                String str = zenRule.enabler;
                return str != null ? ((ViewGroup) this).mContext.getString(R.string.sec_zen_mode_footer_by_app_name, getApplicationNameFromPackage(zenModeController, str)) : ((ViewGroup) this).mContext.getString(R.string.zen_mode_settings_dnd_manual_indefinite);
            }
            if (zenRule != null && (uri = zenRule.conditionId) != null && ZenModeConfig.isValidCountdownConditionId(uri)) {
                long jTryParseCountdownConditionId = ZenModeConfig.tryParseCountdownConditionId(zenModeConfig.manualRule.conditionId);
                boolean zIsToday = ZenModeConfig.isToday(jTryParseCountdownConditionId);
                Context context = ((ViewGroup) this).mContext;
                CharSequence formattedTime = ZenModeConfig.getFormattedTime(context, jTryParseCountdownConditionId, zIsToday, context.getUserId());
                return zIsToday ? ((ViewGroup) this).mContext.getString(R.string.sec_zen_mode_footer_until_time, formattedTime) : ((ViewGroup) this).mContext.getString(R.string.sec_zen_mode_footer_until_time_tomorrow, formattedTime);
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
                            boolean zIsToday2 = ZenModeConfig.isToday(nextChangeTime);
                            Context context2 = ((ViewGroup) this).mContext;
                            CharSequence formattedTime2 = ZenModeConfig.getFormattedTime(context2, nextChangeTime, zIsToday2, context2.getUserId());
                            string = zIsToday2 ? ((ViewGroup) this).mContext.getString(R.string.dnd_on_schedule_on_today_header, formattedTime2) : ((ViewGroup) this).mContext.getString(R.string.dnd_on_schedule_on_next_day_header, formattedTime2);
                        } else {
                            string = ((ViewGroup) this).mContext.getString(R.string.sec_zen_mode_footer_by_app_and_schedule_name, getApplicationNameFromPackage(zenModeController, zenRule2.pkg.equals(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG) ? "com.samsung.android.app.routines" : zenRule2.pkg), description);
                        }
                    }
                }
                if (!z) {
                    return ((ViewGroup) this).mContext.getString(R.string.zen_mode_settings_dnd_manual_indefinite);
                }
            }
        }
        return string;
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
    /* JADX WARN: Removed duplicated region for block: B:11:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getPositionInLinearLayout(View view) {
        ExpandableNotificationRow expandableNotificationRow;
        ExpandableNotificationRow expandableNotificationRow2;
        PipelineEntry pipelineEntry;
        ExpandableView expandableView = null;
        if (view instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) view;
            int i = NotificationBundleUi.$r8$clinit;
            GroupMembershipManager groupMembershipManager = this.mGroupMembershipManager;
            NotificationEntry entryLegacy = expandableNotificationRow3.getEntryLegacy();
            GroupMembershipManagerImpl groupMembershipManagerImpl = (GroupMembershipManagerImpl) groupMembershipManager;
            groupMembershipManagerImpl.getClass();
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            if (groupMembershipManagerImpl.isGroupSummary(entryLegacy) || (pipelineEntry = entryLegacy.mAttachState.parent) == GroupEntry.ROOT_ENTRY || pipelineEntry == null) {
                expandableNotificationRow = 0;
                expandableNotificationRow2 = null;
            } else {
                view = expandableNotificationRow3.mNotificationParent;
                expandableNotificationRow2 = expandableNotificationRow3;
                expandableNotificationRow = view;
            }
        }
        int i2 = SceneContainerFlag.$r8$clinit;
        int i3 = 0;
        float f = this.mAmbientState.isOnKeyguard$1() ? 0 : this.mMinimumPaddings;
        int intrinsicHeight = (int) f;
        for (int i4 = 0; i4 < getChildCount(); i4++) {
            ExpandableView expandableView2 = (ExpandableView) getChildAt(i4);
            boolean z = expandableView2.getVisibility() != 8;
            if (z && !expandableView2.hasNoContentHeight()) {
                float f2 = intrinsicHeight;
                if (f2 != f) {
                    if (expandableView != null) {
                        intrinsicHeight = (int) (calculateGapHeight(expandableView, expandableView2) + f2);
                    }
                    intrinsicHeight += this.mPaddingBetweenElements;
                }
            }
            if (expandableView2 == view) {
                if (expandableNotificationRow == 0) {
                    return intrinsicHeight;
                }
                if (expandableNotificationRow.mIsSummaryWithChildren) {
                    NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
                    int intrinsicHeight2 = (notificationChildrenContainer.mContainingNotification.isGroupExpanded$1() ? notificationChildrenContainer.mHeaderExpandedHeight : 0) + notificationChildrenContainer.mAdditionalExpandedHeaderMargin;
                    int i5 = 0;
                    while (true) {
                        if (i5 >= ((ArrayList) notificationChildrenContainer.mAttachedChildren).size()) {
                            break;
                        }
                        ExpandableNotificationRow expandableNotificationRow4 = (ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(i5);
                        boolean z2 = expandableNotificationRow4.getVisibility() != 8;
                        if (z2) {
                            intrinsicHeight2 += notificationChildrenContainer.mDividerHeight;
                        }
                        if (expandableNotificationRow4 == expandableNotificationRow2) {
                            i3 = intrinsicHeight2;
                            break;
                        }
                        if (z2) {
                            intrinsicHeight2 = expandableNotificationRow4.getIntrinsicHeight() + intrinsicHeight2;
                        }
                        i5++;
                    }
                }
                return intrinsicHeight + i3;
            }
            if (z) {
                intrinsicHeight = expandableView2.getIntrinsicHeight() + intrinsicHeight;
                expandableView = expandableView2;
            }
        }
        return 0;
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
        int contentHeight = getContentHeight();
        if (!this.mIsExpanded && this.mInHeadsUpPinnedMode) {
            contentHeight = this.mHeadsUpInset + getTopHeadsUpPinnedHeight();
        }
        int iMax = Math.max(0, contentHeight - this.mMaxLayoutHeight);
        int imeInset = getImeInset();
        int iMin = Math.min(imeInset, Math.max(0, contentHeight - (getHeight() - imeInset))) + iMax;
        return (this.mInHeadsUpPinnedMode || iMin <= 0) ? iMin : Math.max(getScrollAmountToScrollBoundary(), iMin);
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
        boolean zIsBelowLastNotification = isBelowLastNotification(this.mInitialTouchX, this.mInitialTouchY);
        int i2 = this.mStatusBarState;
        boolean z = this.mTouchIsClick;
        NotificationStackScrollLogger notificationStackScrollLogger = this.mLogger;
        if (notificationStackScrollLogger != null) {
            String strActionToString = MotionEvent.actionToString(motionEvent.getActionMasked());
            LogLevel logLevel = LogLevel.DEBUG;
            NotificationStackScrollLogger$$ExternalSyntheticLambda0 notificationStackScrollLogger$$ExternalSyntheticLambda0 = new NotificationStackScrollLogger$$ExternalSyntheticLambda0(1);
            LogBuffer logBuffer = notificationStackScrollLogger.shadeLogBuffer;
            LogMessage logMessageObtain = logBuffer.obtain("NotificationStackScroll", logLevel, notificationStackScrollLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.int1 = i2;
            logMessageImpl.bool1 = z;
            logMessageImpl.bool2 = zIsBelowLastNotification;
            logMessageImpl.str1 = strActionToString;
            logBuffer.commit(logMessageObtain);
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
        int iIndexOfChild = 0;
        DndStatusView dndStatusView2 = (DndStatusView) LayoutInflater.from(((ViewGroup) this).mContext).inflate(R.layout.status_bar_notification_dnd_status, (ViewGroup) this, false);
        View viewFindViewById = dndStatusView2.findViewById(R.id.notification_dnd_status_text_icon_container);
        if (viewFindViewById != null) {
            viewFindViewById.setOnClickListener(new NotificationStackScrollLayout$$ExternalSyntheticLambda1(this, 0));
        }
        dndStatusView2.setVisible(dndStatusView != null && dndStatusView.mIsVisible, false);
        dndStatusView2.setDndTextAndIcon(getDndStatusText(this.mZenModeController));
        dndStatusView2.setSecondaryVisible(dndStatusView != null && dndStatusView.mIsVisible);
        View view = this.mDndStatusView;
        if (view != null) {
            iIndexOfChild = indexOfChild(view);
            removeView(this.mDndStatusView);
        }
        this.mDndStatusView = dndStatusView2;
        addView(dndStatusView2, iIndexOfChild);
    }

    public final void inflateEmptyShadeView() {
        int iIndexOfChild;
        if (NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW) {
            return;
        }
        int i = ModesEmptyShadeFix.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        EmptyShadeView emptyShadeView = this.mEmptyShadeView;
        EmptyShadeView emptyShadeView2 = (EmptyShadeView) LayoutInflater.from(((ViewGroup) this).mContext).inflate(R.layout.status_bar_no_notifications, (ViewGroup) this, false);
        View view = this.mEmptyShadeView;
        if (view != null) {
            iIndexOfChild = indexOfChild(view);
            removeView(this.mEmptyShadeView);
        } else {
            iIndexOfChild = -1;
        }
        this.mEmptyShadeView = emptyShadeView2;
        addView(emptyShadeView2, iIndexOfChild);
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
        StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("mIsSmallLandscapeLockscreenEnabled=false isSmallScreenLandscape=", " useSmallLandscapeLockscreenResources=", " skinnyNotifsInLandscape=", z, false);
        sbM.append(z2);
        sbM.append(" mSkinnyNotifsInLandscape=");
        sbM.append(this.mSkinnyNotifsInLandscape);
        this.mLastInitViewDumpString = sbM.toString();
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
        LogMessage logMessageObtain = logBuffer.obtain("NotificationStackScroll", logLevel, notificationStackScrollLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str2;
        logMessageImpl.str2 = str;
        logBuffer.commit(logMessageObtain);
    }

    public final void notifyAppearChangedListeners() {
        float fSaturate;
        float f;
        if (this.mKeyguardBypassEnabled && onKeyguard()) {
            float f2 = this.mAmbientState.mPulseHeight;
            f = 0.0f;
            if (f2 == 100000.0f) {
                f2 = 0.0f;
            }
            int i = SceneContainerFlag.$r8$clinit;
            fSaturate = MathUtils.smoothStep(0.0f, this.mIntrinsicPadding, f2);
            float f3 = this.mAmbientState.mPulseHeight;
            if (f3 != 100000.0f) {
                f = f3;
            }
        } else {
            fSaturate = MathUtils.saturate(calculateAppearFraction(this.mExpandedHeight));
            f = this.mExpandedHeight;
        }
        if (fSaturate == this.mLastSentAppear && f == this.mLastSentExpandedHeight) {
            return;
        }
        this.mLastSentAppear = fSaturate;
        this.mLastSentExpandedHeight = f;
        for (int i2 = 0; i2 < this.mExpandedHeightListeners.size(); i2++) {
            ((BiConsumer) this.mExpandedHeightListeners.get(i2)).accept(Float.valueOf(f), Float.valueOf(fSaturate));
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
        SharedNotificationContainerBinder.AnonymousClass3 anonymousClass3 = this.mOnHeightChangedRunnable;
        if (anonymousClass3 != null) {
            anonymousClass3.run();
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
                int height = this.mMaxLayoutHeight + ((int) this.mAmbientState.mStackTranslation);
                NotificationSection lastVisibleSection = getLastVisibleSection();
                if (expandableNotificationRow != (lastVisibleSection == null ? null : lastVisibleSection.mLastVisibleChild) && this.mShelf.getVisibility() != 8) {
                    height -= this.mShelf.getHeight() + this.mPaddingBetweenElements;
                }
                float f = height;
                if (translationY > f) {
                    int i = SceneContainerFlag.$r8$clinit;
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

    /* JADX WARN: Removed duplicated region for block: B:34:0x008d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onInterceptTouchEventScroll(MotionEvent motionEvent) {
        if (!this.mScrollingEnabled) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 2 && this.mIsBeingDragged) {
            return true;
        }
        int i = action & 255;
        if (i == 0) {
            int y = (int) motionEvent.getY();
            AnonymousClass9 anonymousClass9 = this.mScrollAdapter;
            anonymousClass9.getClass();
            int i2 = SceneContainerFlag.$r8$clinit;
            this.mScrolledToTopOnFirstDown = NotificationStackScrollLayout.this.getOwnScrollY() == 0;
            if (getChildAtPosition(motionEvent.getX(), y, false, false) == null) {
                setIsBeingDragged(false);
                VelocityTracker velocityTracker = this.mVelocityTracker;
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    this.mVelocityTracker = null;
                }
            } else {
                this.mLastMotionY = y;
                this.mDownX = (int) motionEvent.getX();
                this.mActivePointerId = motionEvent.getPointerId(0);
                VelocityTracker velocityTracker2 = this.mVelocityTracker;
                if (velocityTracker2 == null) {
                    this.mVelocityTracker = VelocityTracker.obtain();
                } else {
                    velocityTracker2.clear();
                }
                this.mVelocityTracker.addMovement(motionEvent);
                setIsBeingDragged(!this.mScroller.isFinished());
            }
        } else if (i == 1) {
            setIsBeingDragged(false);
            this.mActivePointerId = -1;
            VelocityTracker velocityTracker3 = this.mVelocityTracker;
            if (velocityTracker3 != null) {
                velocityTracker3.recycle();
                this.mVelocityTracker = null;
            }
            if (this.mScroller.springBack(((ViewGroup) this).mScrollX, getOwnScrollY(), 0, 0, 0, getScrollRange())) {
                animateScroll();
            }
        } else if (i == 2) {
            int i3 = this.mActivePointerId;
            if (i3 != -1) {
                int iFindPointerIndex = motionEvent.findPointerIndex(i3);
                if (iFindPointerIndex == -1) {
                    Log.e("StackScroller", "Invalid pointerId=" + i3 + " in onInterceptTouchEvent");
                } else {
                    int y2 = (int) motionEvent.getY(iFindPointerIndex);
                    int x = (int) motionEvent.getX(iFindPointerIndex);
                    int iAbs = Math.abs(y2 - this.mLastMotionY);
                    int iAbs2 = Math.abs(x - this.mDownX);
                    if (iAbs > getTouchSlop$2(motionEvent) && iAbs > iAbs2) {
                        setIsBeingDragged(true);
                        this.mLastMotionY = y2;
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
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(size, "viewWidth=", " skinnyNotifsInLandscape=");
        sbM.append(this.mSkinnyNotifsInLandscape);
        sbM.append(" orientation=");
        sbM.append(i3);
        this.mLastUpdateSidePaddingDumpString = sbM.toString();
        this.mLastUpdateSidePaddingElapsedRealtime = SystemClock.elapsedRealtime();
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        int notificationSidePadding = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getNotificationSidePadding(((ViewGroup) this).mContext, true);
        this.mSidePaddings = notificationSidePadding;
        if (this.mSuppressChildrenMeasureAndLayout) {
            Log.d("StackScroller", "SuppressChildrenMeasureAndLayout set as ture. return onMeasure");
            Trace.endSection();
            return;
        }
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size - (notificationSidePadding * 2), View.MeasureSpec.getMode(i));
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), 0);
        int childCount = getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            measureChild(getChildAt(i4), iMakeMeasureSpec, iMakeMeasureSpec2);
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
                boolean zIsExpansionEnabled = quickSettingsControllerImpl.isExpansionEnabled();
                if (!zIsExpansionEnabled && z) {
                    f = 0.0f;
                }
                quickSettingsControllerImpl.flingQs(f, (z && zIsExpansionEnabled) ? 0 : 1, new QuickSettingsControllerImpl$$ExternalSyntheticLambda10(nsslOverscrollTopChangedListener, 4), false);
            }
        }
        this.mDontReportNextOverScroll = true;
        setOverScrollAmount(0.0f, true, false, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:95:0x01d5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onScrollTouch(MotionEvent motionEvent) {
        float f;
        StringBuilder sb;
        if (this.mScrollingEnabled) {
            if (isInScrollableRegion(motionEvent) || this.mIsBeingDragged) {
                this.mForcedScroll = null;
                if (this.mVelocityTracker == null) {
                    this.mVelocityTracker = VelocityTracker.obtain();
                }
                this.mVelocityTracker.addMovement(motionEvent);
                int actionMasked = motionEvent.getActionMasked();
                if (motionEvent.findPointerIndex(this.mActivePointerId) == -1 && actionMasked != 0) {
                    Log.e("StackScroller", "Invalid pointerId=" + this.mActivePointerId + " in onTouchEvent " + MotionEvent.actionToString(motionEvent.getActionMasked()));
                    return true;
                }
                int i = SceneContainerFlag.$r8$clinit;
                if (actionMasked != 0) {
                    if (actionMasked != 1) {
                        if (actionMasked == 2) {
                            int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                            if (iFindPointerIndex == -1) {
                                Log.e("StackScroller", "Invalid pointerId=" + this.mActivePointerId + " in onTouchEvent");
                                return true;
                            }
                            int y = (int) motionEvent.getY(iFindPointerIndex);
                            int x = (int) motionEvent.getX(iFindPointerIndex);
                            int i2 = this.mLastMotionY - y;
                            int iAbs = Math.abs(x - this.mDownX);
                            int iAbs2 = Math.abs(i2);
                            float touchSlop$2 = getTouchSlop$2(motionEvent);
                            if (!this.mIsBeingDragged && iAbs2 > touchSlop$2 && iAbs2 > iAbs) {
                                setIsBeingDragged(true);
                                i2 = (int) (i2 > 0 ? i2 - touchSlop$2 : i2 + touchSlop$2);
                            }
                            if (this.mIsBeingDragged) {
                                this.mLastMotionY = y;
                                int scrollRange = getScrollRange();
                                if (this.mExpandedInThisMotion) {
                                    scrollRange = Math.min(scrollRange, this.mMaxScrollAfterExpand);
                                }
                                if (i2 < 0) {
                                    int iMin = Math.min(i2, 0);
                                    float currentOverScrollAmount = getCurrentOverScrollAmount(false);
                                    f = iMin + currentOverScrollAmount;
                                    if (currentOverScrollAmount > 0.0f) {
                                        setOverScrollAmount(f, false, false, true);
                                    }
                                    if (f >= 0.0f) {
                                        f = 0.0f;
                                    }
                                    float ownScrollY = getOwnScrollY() + f;
                                    if (ownScrollY < 0.0f) {
                                        setOverScrollAmount(getRubberBandFactor(true) * (this.mOverScrolledTopPixels - ownScrollY), true, false, true);
                                        setOwnScrollY(0);
                                        f = 0.0f;
                                    }
                                    if (f != 0.0f) {
                                        customOverScrollBy((int) f, getOwnScrollY(), scrollRange, getHeight() / 2);
                                        return true;
                                    }
                                } else {
                                    int iMax = Math.max(i2, 0);
                                    float currentOverScrollAmount2 = getCurrentOverScrollAmount(true);
                                    float f2 = currentOverScrollAmount2 - iMax;
                                    if (currentOverScrollAmount2 > 0.0f) {
                                        setOverScrollAmount(f2, true, false, true);
                                    }
                                    f = f2 < 0.0f ? -f2 : 0.0f;
                                    float ownScrollY2 = getOwnScrollY() + f;
                                    float f3 = scrollRange;
                                    if (ownScrollY2 > f3) {
                                        if (!this.mExpandedInThisMotion) {
                                            setOverScrollAmount(getRubberBandFactor(false) * ((this.mOverScrolledBottomPixels + ownScrollY2) - f3), false, false, true);
                                        }
                                        setOwnScrollY(scrollRange);
                                        f = 0.0f;
                                    }
                                    if (f != 0.0f) {
                                    }
                                }
                            }
                        } else if (actionMasked != 3) {
                            if (actionMasked == 5) {
                                int actionIndex = motionEvent.getActionIndex();
                                this.mLastMotionY = (int) motionEvent.getY(actionIndex);
                                this.mDownX = (int) motionEvent.getX(actionIndex);
                                this.mActivePointerId = motionEvent.getPointerId(actionIndex);
                                return true;
                            }
                            if (actionMasked == 6) {
                                onSecondaryPointerUp(motionEvent);
                                this.mLastMotionY = (int) motionEvent.getY(motionEvent.findPointerIndex(this.mActivePointerId));
                                this.mDownX = (int) motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId));
                                return true;
                            }
                        } else if (this.mIsBeingDragged && getChildCount() > 0) {
                            if (this.mScroller.springBack(((ViewGroup) this).mScrollX, getOwnScrollY(), 0, 0, 0, getScrollRange())) {
                                animateScroll();
                            }
                            this.mActivePointerId = -1;
                            endDrag();
                            return true;
                        }
                    } else if (this.mIsBeingDragged) {
                        VelocityTracker velocityTracker = this.mVelocityTracker;
                        velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                        int yVelocity = (int) velocityTracker.getYVelocity(this.mActivePointerId);
                        float currentOverScrollAmount3 = getCurrentOverScrollAmount(true);
                        if (this.mScrolledToTopOnFirstDown && ((!SecPanelSplitHelper.isEnabled() || !getPanelSplitHelper().isShadeState()) && !this.mExpandedInThisMotion && (yVelocity > this.mMinimumVelocity || (currentOverScrollAmount3 > this.mMinTopOverScrollToEscape && yVelocity > 0)))) {
                            onOverScrollFling(yVelocity, true);
                        } else if (getChildCount() > 0) {
                            if (Math.abs(yVelocity) > this.mMinimumVelocity) {
                                if (getCurrentOverScrollAmount(true) == 0.0f || yVelocity > 0) {
                                    this.mFlingAfterUpEvent = true;
                                    NotificationStackScrollLayout$$ExternalSyntheticLambda4 notificationStackScrollLayout$$ExternalSyntheticLambda4 = new NotificationStackScrollLayout$$ExternalSyntheticLambda4(this, 1);
                                    RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                                    this.mFinishScrollingCallback = notificationStackScrollLayout$$ExternalSyntheticLambda4;
                                    int i3 = -yVelocity;
                                    if (getChildCount() > 0) {
                                        float currentOverScrollAmount4 = getCurrentOverScrollAmount(true);
                                        float currentOverScrollAmount5 = getCurrentOverScrollAmount(false);
                                        if (i3 < 0 && currentOverScrollAmount4 > 0.0f) {
                                            setOwnScrollY(getOwnScrollY() - ((int) currentOverScrollAmount4));
                                            this.mDontReportNextOverScroll = true;
                                            setOverScrollAmount(0.0f, true, false, true);
                                            this.mMaxOverScroll = (getRubberBandFactor(true) * (Math.abs(i3) / 1000.0f) * this.mOverflingDistance) + currentOverScrollAmount4;
                                        } else if (i3 <= 0 || currentOverScrollAmount5 <= 0.0f) {
                                            this.mMaxOverScroll = 0.0f;
                                        } else {
                                            setOwnScrollY((int) (getOwnScrollY() + currentOverScrollAmount5));
                                            setOverScrollAmount(0.0f, false, false, true);
                                            this.mMaxOverScroll = ((Math.abs(i3) / 1000.0f) * RUBBER_BAND_FACTOR_NORMAL * this.mOverflingDistance) + currentOverScrollAmount5;
                                        }
                                        int iMax2 = Math.max(0, getScrollRange());
                                        if (this.mExpandedInThisMotion) {
                                            iMax2 = Math.min(iMax2, this.mMaxScrollAfterExpand);
                                        }
                                        this.mScroller.fling(((ViewGroup) this).mScrollX, getOwnScrollY(), 1, i3, 0, 0, i3 > 0 ? getScrollAmountToScrollBoundary() : 0, iMax2, 0, (!this.mExpandedInThisMotion || getOwnScrollY() < 0) ? 1073741823 : 0);
                                        if (i3 < 0 && this.mScroller.getFinalY() > 0 && this.mScroller.getFinalY() < getScrollAmountToScrollBoundary()) {
                                            this.mScroller.forceFinished(true);
                                            this.mScroller.startScroll(((ViewGroup) this).mScrollX, getOwnScrollY(), 0, -getOwnScrollY(), 1050);
                                        }
                                        animateScroll();
                                    }
                                } else {
                                    onOverScrollFling(yVelocity, false);
                                }
                            } else if (this.mScroller.springBack(((ViewGroup) this).mScrollX, getOwnScrollY(), 0, 0, 0, getScrollRange())) {
                                animateScroll();
                            } else if (this.mOwnScrollY > 0) {
                                int scrollAmountToScrollBoundary = getScrollAmountToScrollBoundary();
                                int i4 = this.mOwnScrollY;
                                if (scrollAmountToScrollBoundary > i4) {
                                    this.mScroller.startScroll(((ViewGroup) this).mScrollX, i4, 0, -i4, 1050);
                                    animateScroll();
                                }
                            }
                        }
                        this.mActivePointerId = -1;
                        endDrag();
                    }
                    return true;
                }
                if (getChildCount() != 0) {
                    if (motionEvent.getY() < (getHeight() - Math.max(this.mMaxLayoutHeight - getContentHeight(), 0)) - this.mShelf.getHeight() && this.mController.mActiveNotificationsInteractor.getAreAnyNotificationsPresentValue()) {
                        setIsBeingDragged(!this.mScroller.isFinished());
                        if (!this.mScroller.isFinished()) {
                            this.mScroller.forceFinished(true);
                        }
                        this.mLastMotionY = (int) motionEvent.getY();
                        this.mDownX = (int) motionEvent.getX();
                        this.mActivePointerId = motionEvent.getPointerId(0);
                        return true;
                    }
                }
            } else if (this.mQuickPanelLogger != null && (sb = this.mQuickPanelLogBuilder) != null) {
                sb.setLength(0);
                StringBuilder sb2 = this.mQuickPanelLogBuilder;
                sb2.append("onScrollTouch: inInsideQsHeader : ");
                sb2.append(isInsideQsHeader(motionEvent));
                sb2.append(" , mIsBeingDragged : ");
                sb2.append(this.mIsBeingDragged);
                this.mQuickPanelLogger.onTouchEvent(motionEvent, this.mQuickPanelLogBuilder.toString(), false);
                return false;
            }
        }
        return false;
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

    /* JADX WARN: Removed duplicated region for block: B:142:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x023b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:167:0x027c  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x02b4  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x036e  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x010e  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        boolean zHandleTouch;
        StringBuilder sb;
        float yVelocity;
        NotificationStackScrollLayout notificationStackScrollLayout;
        boolean z3;
        NotificationStackScrollLayoutController.TouchHandler touchHandler = this.mTouchHandler;
        if (touchHandler != null) {
            QuickPanelLogger quickPanelLogger = touchHandler.mQuickPanelLogger;
            SecStatusBarWindowViewTouchedInteractor secStatusBarWindowViewTouchedInteractor = touchHandler.mStatusBarWindowViewTouchedInteractor;
            if (secStatusBarWindowViewTouchedInteractor == null || !secStatusBarWindowViewTouchedInteractor.isTouched()) {
                boolean z4 = LsRune.SECURITY_BOUNCER_WINDOW;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                if (!z4 && motionEvent.getActionMasked() == 1 && notificationStackScrollLayoutController.mPrimaryBouncerInteractor.isBouncerShowing() && notificationStackScrollLayoutController.mStatusBarStateController.getState() == 2) {
                    if (quickPanelLogger != null) {
                        quickPanelLogger.onInterceptTouchEvent(motionEvent, "SHADE_LOCKED Bouncer Showing", false);
                        z3 = false;
                    }
                    z3 = false;
                } else {
                    if (quickPanelLogger != null) {
                        quickPanelLogger.onTouchEvent(motionEvent);
                    }
                    if (!notificationStackScrollLayoutController.mIsStartFromContentsBound) {
                        float rawX = motionEvent.getRawX();
                        motionEvent.getRawY();
                        if (notificationStackScrollLayoutController.isInContentBounds$2(rawX)) {
                            NotificationGuts notificationGuts = notificationStackScrollLayoutController.mNotificationGutsManager.mNotificationGutsExposed;
                            boolean z5 = motionEvent.getActionMasked() == 3 || motionEvent.getActionMasked() == 1;
                            notificationStackScrollLayoutController.mView.handleEmptySpaceClick(motionEvent);
                            boolean zOnTouchEvent = (notificationGuts == null || notificationStackScrollLayoutController.mLongPressedView == null) ? false : notificationStackScrollLayoutController.mSwipeHelper.onTouchEvent(motionEvent);
                            NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
                            boolean z6 = notificationStackScrollLayout2.mOnlyScrollingInThisMotion;
                            boolean z7 = notificationStackScrollLayout2.mExpandingNotification;
                            if (notificationStackScrollLayoutController.mLongPressedView == null && notificationStackScrollLayout2.mIsExpanded && !notificationStackScrollLayoutController.mSwipeHelper.mIsSwiping && !z6 && notificationGuts == null) {
                                ExpandHelper expandHelper = notificationStackScrollLayout2.mExpandHelper;
                                if (z5) {
                                    expandHelper.mOnlyMovements = false;
                                }
                                if (expandHelper.mEnabled || expandHelper.mExpanding) {
                                    expandHelper.trackVelocity(motionEvent);
                                    int actionMasked = motionEvent.getActionMasked();
                                    expandHelper.mSGD.onTouchEvent(motionEvent);
                                    int focusX = (int) expandHelper.mSGD.getFocusX();
                                    int focusY = (int) expandHelper.mSGD.getFocusY();
                                    if (expandHelper.mOnlyMovements) {
                                        expandHelper.mLastMotionY = motionEvent.getRawY();
                                        z = z5;
                                        notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                                        boolean z8 = notificationStackScrollLayout.mExpandingNotification;
                                        if (notificationStackScrollLayout.mExpandedInThisMotion && !z8 && z7 && !notificationStackScrollLayout.mDisallowScrollingInThisMotion) {
                                            int i = SceneContainerFlag.$r8$clinit;
                                            MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
                                            motionEventObtain.setAction(0);
                                            notificationStackScrollLayout.onScrollTouch(motionEventObtain);
                                            motionEventObtain.recycle();
                                        }
                                        z7 = z8;
                                    } else {
                                        if (actionMasked == 0) {
                                            z = z5;
                                            AnonymousClass9 anonymousClass9 = expandHelper.mScrollAdapter;
                                            if (anonymousClass9 != null) {
                                                boolean z9 = expandHelper.isInside(NotificationStackScrollLayout.this, (float) focusX, (float) focusY);
                                                expandHelper.mWatchingForPull = z9;
                                                expandHelper.mResizedView = expandHelper.findView$1(focusX, focusY);
                                                expandHelper.mInitialTouchX = motionEvent.getRawX();
                                                expandHelper.mInitialTouchY = motionEvent.getRawY();
                                            }
                                        } else if (actionMasked == 1) {
                                            z = z5;
                                            boolean z10 = !expandHelper.mEnabled || motionEvent.getActionMasked() == 3;
                                            VelocityTracker velocityTracker = expandHelper.mVelocityTracker;
                                            if (velocityTracker != null) {
                                                velocityTracker.computeCurrentVelocity(1000);
                                                yVelocity = expandHelper.mVelocityTracker.getYVelocity();
                                            } else {
                                                yVelocity = 0.0f;
                                            }
                                            expandHelper.finishExpanding(z10, yVelocity);
                                            expandHelper.mResizedView = null;
                                        } else if (actionMasked == 2) {
                                            if (expandHelper.mWatchingForPull) {
                                                float rawY = motionEvent.getRawY() - expandHelper.mInitialTouchY;
                                                float rawX2 = motionEvent.getRawX() - expandHelper.mInitialTouchX;
                                                int classification = motionEvent.getClassification();
                                                int i2 = expandHelper.mTouchSlop;
                                                if (rawY > (classification == 1 ? i2 * expandHelper.mSlopMultiplier : i2) && rawY > Math.abs(rawX2)) {
                                                    expandHelper.mWatchingForPull = false;
                                                    ExpandableView expandableView = expandHelper.mResizedView;
                                                    if (expandableView != null && ((expandableView.getIntrinsicHeight() != expandableView.getMaxContentHeight() || (expandableView.isSummaryWithChildren() && !expandableView.areChildrenExpanded())) && expandHelper.startExpanding(expandHelper.mResizedView, 1))) {
                                                        expandHelper.mInitialTouchY = motionEvent.getRawY();
                                                        expandHelper.mLastMotionY = motionEvent.getRawY();
                                                    }
                                                }
                                            }
                                            boolean z11 = expandHelper.mExpanding;
                                            if (!z11 || (expandHelper.mExpansionStyle & 1) == 0) {
                                                z = z5;
                                                if (z11) {
                                                    expandHelper.updateExpansion();
                                                    expandHelper.mLastMotionY = motionEvent.getRawY();
                                                }
                                            } else {
                                                float rawY2 = (motionEvent.getRawY() - expandHelper.mLastMotionY) + expandHelper.mCurrentHeight;
                                                int i3 = expandHelper.mSmallSize;
                                                float f = i3;
                                                if (rawY2 >= f) {
                                                    f = rawY2;
                                                }
                                                float f2 = expandHelper.mNaturalHeight;
                                                if (f > f2) {
                                                    f = f2;
                                                }
                                                boolean z12 = rawY2 > f2;
                                                if (rawY2 < i3) {
                                                    z12 = true;
                                                }
                                                ExpandHelper.ViewScaler viewScaler = expandHelper.mScaler;
                                                z = z5;
                                                viewScaler.mView.setActualHeight((int) f, true);
                                                ExpandHelper.this.mCurrentHeight = f;
                                                expandHelper.mLastMotionY = motionEvent.getRawY();
                                                ExpandHelper.Callback callback = expandHelper.mCallback;
                                                if (z12) {
                                                    ((AnonymousClass11) callback).expansionStateChanged(false);
                                                } else {
                                                    ((AnonymousClass11) callback).expansionStateChanged(true);
                                                }
                                            }
                                            notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                                            boolean z82 = notificationStackScrollLayout.mExpandingNotification;
                                            if (notificationStackScrollLayout.mExpandedInThisMotion) {
                                                int i4 = SceneContainerFlag.$r8$clinit;
                                                MotionEvent motionEventObtain2 = MotionEvent.obtain(motionEvent);
                                                motionEventObtain2.setAction(0);
                                                notificationStackScrollLayout.onScrollTouch(motionEventObtain2);
                                                motionEventObtain2.recycle();
                                            }
                                            z7 = z82;
                                        } else if (actionMasked != 3) {
                                            if (actionMasked == 5 || actionMasked == 6) {
                                                expandHelper.mInitialTouchY = (expandHelper.mSGD.getFocusY() - expandHelper.mLastFocusY) + expandHelper.mInitialTouchY;
                                                expandHelper.mInitialTouchSpan = (expandHelper.mSGD.getCurrentSpan() - expandHelper.mLastSpanY) + expandHelper.mInitialTouchSpan;
                                            }
                                            z = z5;
                                        }
                                        expandHelper.mLastMotionY = motionEvent.getRawY();
                                        expandHelper.maybeRecycleVelocityTracker(motionEvent);
                                        z2 = expandHelper.mResizedView != null;
                                        notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                                        boolean z822 = notificationStackScrollLayout.mExpandingNotification;
                                        if (notificationStackScrollLayout.mExpandedInThisMotion) {
                                        }
                                        z7 = z822;
                                    }
                                } else {
                                    z = z5;
                                    notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                                    boolean z8222 = notificationStackScrollLayout.mExpandingNotification;
                                    if (notificationStackScrollLayout.mExpandedInThisMotion) {
                                    }
                                    z7 = z8222;
                                }
                            } else {
                                z = z5;
                                z2 = false;
                            }
                            ((NotificationStackScrollLayoutController.AnonymousClass12) notificationStackScrollLayoutController.mNotificationCallback).getChildAtPosition(motionEvent);
                            if (notificationStackScrollLayoutController.mLongPressedView == null) {
                                NotificationStackScrollLayout notificationStackScrollLayout3 = notificationStackScrollLayoutController.mView;
                                boolean zOnTouchEvent2 = (notificationStackScrollLayout3.mIsBeingDragged || z7 || notificationStackScrollLayout3.mExpandedInThisMotion || z6 || notificationStackScrollLayout3.mDisallowDismissInThisMotion) ? false : notificationStackScrollLayoutController.mSwipeHelper.onTouchEvent(motionEvent);
                                if (touchHandler.panelSliderIntercepted) {
                                    if (motionEvent.getActionMasked() == 3 || motionEvent.getActionMasked() == 1) {
                                        touchHandler.panelSliderIntercepted = false;
                                    }
                                    zHandleTouch = notificationStackScrollLayoutController.mPanelSplitHelper.handleTouch(motionEvent);
                                } else {
                                    zHandleTouch = false;
                                }
                                if (notificationStackScrollLayoutController.mLongPressedView == null) {
                                    NotificationStackScrollLayout notificationStackScrollLayout4 = notificationStackScrollLayoutController.mView;
                                    boolean zOnScrollTouch = (!notificationStackScrollLayout4.mIsExpanded || notificationStackScrollLayoutController.mSwipeHelper.mIsSwiping || z7 || notificationStackScrollLayout4.mDisallowScrollingInThisMotion) ? false : notificationStackScrollLayout4.onScrollTouch(motionEvent);
                                    int i5 = SceneContainerFlag.$r8$clinit;
                                    if (notificationGuts != null && !NotificationSwipeHelper.isTouchInView(notificationGuts, motionEvent)) {
                                        NotificationGuts.GutsContent gutsContent = notificationGuts.mGutsContent;
                                        if ((gutsContent instanceof NotificationSnooze) && ((((NotificationSnooze) gutsContent).mExpanded && z) || (!zOnTouchEvent2 && zOnScrollTouch))) {
                                            notificationStackScrollLayoutController.checkSnoozeLeavebehind();
                                        }
                                    }
                                    if (motionEvent.getActionMasked() == 1) {
                                        if (!zOnTouchEvent2) {
                                            notificationStackScrollLayoutController.mFalsingManager.isFalseTouch(11);
                                        }
                                        notificationStackScrollLayoutController.mView.mCheckForLeavebehind = true;
                                    }
                                    NotificationStackScrollLayoutController.m3084$$Nest$mupdateEventAvailability(notificationStackScrollLayoutController, motionEvent);
                                    int actionMasked2 = motionEvent.getActionMasked();
                                    InteractionJankMonitor interactionJankMonitor = notificationStackScrollLayoutController.mJankMonitor;
                                    if (interactionJankMonitor == null) {
                                        Log.w("StackScrollerController", "traceJankOnTouchEvent, mJankMonitor is null");
                                    } else {
                                        NotificationStackScrollLayout notificationStackScrollLayout5 = notificationStackScrollLayoutController.mView;
                                        if (actionMasked2 != 0) {
                                            if (actionMasked2 != 1) {
                                                if (actionMasked2 == 3 && zOnScrollTouch) {
                                                    interactionJankMonitor.cancel(2);
                                                }
                                            } else if (zOnScrollTouch) {
                                                notificationStackScrollLayout5.getClass();
                                                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                                                if (!notificationStackScrollLayout5.mFlingAfterUpEvent) {
                                                    notificationStackScrollLayoutController.mJankMonitor.end(2);
                                                }
                                            }
                                        } else if (zOnScrollTouch) {
                                            interactionJankMonitor.begin(notificationStackScrollLayout5, 2);
                                        }
                                    }
                                    if (quickPanelLogger != null && (sb = touchHandler.mQuickPanelLogBuilder) != null) {
                                        sb.setLength(0);
                                        sb.append("horizontalSwipeWantsIt: ");
                                        sb.append(zOnTouchEvent2);
                                        sb.append(", scrollerWantsIt: ");
                                        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, zOnScrollTouch, ", expandWantsIt: ", z2, ", longPressWantsIt: ");
                                        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, zOnTouchEvent, ", panelSlideWantsIt: ", zHandleTouch, ", hunWantsIt: ");
                                        sb.append(false);
                                        quickPanelLogger.onTouchEvent(motionEvent, sb.toString(), zOnTouchEvent2 || zOnScrollTouch || z2 || zOnTouchEvent || zHandleTouch);
                                    }
                                    if (zOnTouchEvent2 || zOnScrollTouch || z2 || zOnTouchEvent) {
                                    }
                                }
                            }
                        } else if (quickPanelLogger != null) {
                            quickPanelLogger.onTouchEvent(motionEvent, "NotiRune.NOTI_POLICY_TOUCH_REGION", false);
                            z3 = false;
                        }
                        z3 = false;
                    }
                }
                int i6 = SceneContainerFlag.$r8$clinit;
                if (z3) {
                    if (this.mOrientation == 2) {
                        float f3 = this.mInitialTouchX;
                        if (f3 < this.mSidePaddings || f3 > getWidth() - this.mSidePaddings) {
                            return false;
                        }
                    }
                    return true;
                }
            } else if (quickPanelLogger != null) {
                quickPanelLogger.onTouchEvent(motionEvent, "StatusBarWindowView Touched", true);
            }
            z3 = true;
            int i62 = SceneContainerFlag.$r8$clinit;
            if (z3) {
            }
        }
        return super.onTouchEvent(motionEvent);
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

    /* JADX WARN: Removed duplicated region for block: B:15:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0207  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onViewRemovedInternal(ExpandableView expandableView, ViewGroup viewGroup) {
        String key;
        Boolean bool;
        if (this.mChangePositionInProgress) {
            return;
        }
        expandableView.mOnHeightChangedListener = null;
        boolean z = expandableView instanceof ExpandableNotificationRow;
        if (z) {
            ((ExpandableNotificationRow) expandableView).mEntry.mOnSensitivityChangedListeners.remove(this.mOnChildSensitivityChangedListener);
        }
        boolean z2 = true;
        if (z) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
            if (!expandableNotificationRow.isInsignificant() || expandableNotificationRow.isInsignificantSummary()) {
                if (z && ((ExpandableNotificationRow) expandableView).mPinnedStatus.isPinned() && getImeInset() > 0) {
                    resetScrollPosition();
                } else {
                    int i = SceneContainerFlag.$r8$clinit;
                    RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                    int positionInLinearLayout = getPositionInLinearLayout(expandableView);
                    int intrinsicHeight = expandableView.getIntrinsicHeight() + this.mPaddingBetweenElements;
                    int i2 = positionInLinearLayout + intrinsicHeight;
                    int scrollAmountToScrollBoundary = getScrollAmountToScrollBoundary();
                    this.mAnimateStackYForContentHeightChange = true;
                    if (i2 <= getOwnScrollY() - scrollAmountToScrollBoundary) {
                        setOwnScrollY(getOwnScrollY() - intrinsicHeight);
                    } else if (positionInLinearLayout < getOwnScrollY() - scrollAmountToScrollBoundary) {
                        setOwnScrollY(positionInLinearLayout + scrollAmountToScrollBoundary);
                    }
                }
            }
        }
        if (viewGroup == null) {
            if (z) {
                ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("onViewRemovedInternal remove child without animation "), ((ExpandableNotificationRow) expandableView).mLoggingKey, "StackScroller");
            }
            this.mSwipedOutViews.remove(expandableView);
            if (z) {
                ((ExpandableNotificationRow) expandableView).removeChildrenWithKeepInParent();
            }
        } else if (z) {
            ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) expandableView;
            if (expandableNotificationRow2.mSkipRemovalAnim) {
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("generateRemoveAnimation skip removalAnim", expandableNotificationRow2.getKey(), "StackScroller");
                expandableNotificationRow2.mSkipRemovalAnim = false;
            } else {
                key = "";
                if (this.mDebugRemoveAnimation) {
                    key = z ? ((ExpandableNotificationRow) expandableView).getKey() : "";
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("generateRemoveAnimation ", key, "StackScroller");
                }
                boolean z3 = false;
                for (HeadsUpAnimationEvent headsUpAnimationEvent : ((HashMap) this.mHeadsUpChangeAnimations).values()) {
                    ExpandableNotificationRow expandableNotificationRow3 = headsUpAnimationEvent.row;
                    if (expandableView == expandableNotificationRow3) {
                        this.mTmpHeadsUpChangeAnimations.add(expandableNotificationRow3);
                        z3 |= headsUpAnimationEvent.isHeadsUpAppearance;
                    }
                }
                if (z3) {
                    this.mTmpHeadsUpChangeAnimations.forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda8
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((HashMap) this.f$0.mHeadsUpChangeAnimations).remove((ExpandableNotificationRow) obj);
                        }
                    });
                    ((ExpandableNotificationRow) expandableView).setHeadsUpAnimatingAway(false);
                }
                this.mTmpHeadsUpChangeAnimations.clear();
                if (z3 && this.mAddedHeadsUpChildren.contains(expandableView)) {
                    if (this.mDebugRemoveAnimation) {
                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("generateRemoveAnimation removedBecauseOfHeadsUp ", key, "StackScroller");
                    }
                    this.mAddedHeadsUpChildren.remove(expandableView);
                } else {
                    if (this.mIsExpanded || (bool = (Boolean) expandableView.getTag(R.id.is_clicked_heads_up_tag)) == null || !bool.booleanValue()) {
                        if (this.mDebugRemoveAnimation) {
                            StringBuilder sbM = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("generateRemoveAnimation ", key, " mIsExpanded ");
                            sbM.append(this.mIsExpanded);
                            sbM.append(" mAnimationsEnabled ");
                            ActionBarContextView$$ExternalSyntheticOutline0.m(sbM, this.mAnimationsEnabled, "StackScroller");
                        }
                        if (this.mIsExpanded && this.mAnimationsEnabled) {
                            if (this.mChildrenToAddAnimated.contains(expandableView)) {
                                this.mChildrenToAddAnimated.remove(expandableView);
                                this.mFromMoreCardAdditions.remove(expandableView);
                            } else {
                                if (this.mDebugRemoveAnimation) {
                                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("generateRemoveAnimation needsAnimation = true ", key, "StackScroller");
                                }
                                this.mChildrenToRemoveAnimated.add(expandableView);
                                this.mNeedsAnimation = true;
                            }
                        }
                    } else {
                        ActionBarContextView$$ExternalSyntheticOutline0.m(ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("generateRemoveAnimation  isClickedHeadsUp ", key, " "), expandableView.mInRemovalAnimation, "StackScroller");
                        this.mClearTransientViewsWhenFinished.add(expandableView);
                        z2 = expandableView.mInRemovalAnimation;
                    }
                    if (!z2) {
                        if (!this.mSwipedOutViews.contains(expandableView) || !isFullySwipedOut(expandableView)) {
                            if (this.mSwipeCancelledView.contains(expandableView)) {
                                this.mSwipeCancelledView.remove(expandableView);
                                Log.d("StackScroller", "onViewRemovedInternal not add to transient container. Reason : swipe cancelled");
                            } else {
                                NotificationStackScrollLogger notificationStackScrollLogger = this.mLogger;
                                if (notificationStackScrollLogger != null && z) {
                                    boolean z4 = viewGroup instanceof NotificationChildrenContainer;
                                    LogBuffer logBuffer = notificationStackScrollLogger.notificationRenderBuffer;
                                    if (z4) {
                                        String str = ((ExpandableNotificationRow) expandableView).mLoggingKey;
                                        String str2 = ((NotificationChildrenContainer) viewGroup).mContainingNotification.mLoggingKey;
                                        LogMessage logMessageObtain = logBuffer.obtain("NotificationStackScroll", LogLevel.INFO, new NotificationStackScrollLogger$$ExternalSyntheticLambda0(6), null);
                                        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                                        logMessageImpl.str1 = str;
                                        logMessageImpl.str2 = str2;
                                        logBuffer.commit(logMessageObtain);
                                    } else if (viewGroup instanceof NotificationStackScrollLayout) {
                                        String str3 = ((ExpandableNotificationRow) expandableView).mLoggingKey;
                                        LogMessage logMessageObtain2 = logBuffer.obtain("NotificationStackScroll", LogLevel.INFO, new NotificationStackScrollLogger$$ExternalSyntheticLambda0(0), null);
                                        ((LogMessageImpl) logMessageObtain2).str1 = str3;
                                        logBuffer.commit(logMessageObtain2);
                                    } else {
                                        String str4 = ((ExpandableNotificationRow) expandableView).mLoggingKey;
                                        LogMessage logMessageObtain3 = logBuffer.obtain("NotificationStackScroll", LogLevel.ERROR, new NotificationStackScrollLogger$$ExternalSyntheticLambda0(7), null);
                                        LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain3;
                                        logMessageImpl2.str1 = str4;
                                        logMessageImpl2.str2 = viewGroup.toString();
                                        logBuffer.commit(logMessageObtain3);
                                    }
                                }
                                viewGroup.addTransientView(expandableView, 0);
                                expandableView.mTransientContainer = viewGroup;
                                Log.d("StackScroller", "onViewRemovedInternal enqueue next animation");
                            }
                        }
                    }
                }
            }
            z2 = false;
            if (!z2) {
            }
        }
        if (z) {
            ((ExpandableNotificationRow) expandableView).setAnimationRunning(false);
        }
        if (z) {
            ExpandableNotificationRow expandableNotificationRow4 = (ExpandableNotificationRow) expandableView;
            if (expandableNotificationRow4.mRefocusOnDismiss || expandableNotificationRow4.isAccessibilityFocused()) {
                View firstChildBelowTranlsationY = expandableNotificationRow4.mChildAfterViewWhenDismissed;
                if (firstChildBelowTranlsationY == null) {
                    ExpandableNotificationRow expandableNotificationRow5 = expandableNotificationRow4.mGroupParentWhenDismissed;
                    firstChildBelowTranlsationY = getFirstChildBelowTranlsationY(expandableNotificationRow5 != null ? expandableNotificationRow5.getTranslationY() : expandableView.getTranslationY());
                }
                if (firstChildBelowTranlsationY != null) {
                    firstChildBelowTranlsationY.requestAccessibilityFocus();
                }
            }
        }
    }

    @Override // android.view.View
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            return;
        }
        cancelLongPress();
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0022, code lost:
    
        if (r6 != 16908346) goto L23;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0056  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        int i2;
        int iMax;
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        if (isEnabled()) {
            int i3 = SceneContainerFlag.$r8$clinit;
            if (i == 4096) {
                i2 = 1;
                iMax = Math.max(0, Math.min((i2 * ((((getHeight() - ((ViewGroup) this).mPaddingBottom) - getTopPadding()) - ((ViewGroup) this).mPaddingTop) - this.mShelf.getHeight())) + getOwnScrollY(), getScrollRange()));
                if (iMax != getOwnScrollY()) {
                    this.mScroller.startScroll(((ViewGroup) this).mScrollX, getOwnScrollY(), 0, iMax - getOwnScrollY());
                    animateScroll();
                    return true;
                }
            } else if (i == 8192 || i == 16908344) {
                i2 = -1;
                iMax = Math.max(0, Math.min((i2 * ((((getHeight() - ((ViewGroup) this).mPaddingBottom) - getTopPadding()) - ((ViewGroup) this).mPaddingTop) - this.mShelf.getHeight())) + getOwnScrollY(), getScrollRange()));
                if (iMax != getOwnScrollY()) {
                }
            }
        }
        return false;
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
            LogMessage logMessageObtain = logBuffer.obtain("NotificationStackScroll", logLevel, notificationStackScrollLogger$$ExternalSyntheticLambda3, null);
            ((LogMessageImpl) logMessageObtain).str1 = str;
            logBuffer.commit(logMessageObtain);
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
        int iTargetScrollForView = targetScrollForView(expandableView, positionInLinearLayout);
        int intrinsicHeight = expandableView.getIntrinsicHeight() + positionInLinearLayout;
        if (getOwnScrollY() >= iTargetScrollForView && intrinsicHeight >= getOwnScrollY()) {
            return false;
        }
        this.mScroller.startScroll(((ViewGroup) this).mScrollX, getOwnScrollY(), 0, iTargetScrollForView - getOwnScrollY());
        this.mDontReportNextOverScroll = true;
        animateScroll();
        return true;
    }

    @Override // android.view.View
    public final void setAlpha(float f) {
        if (QsAnimatorState.isCustomizerShowing || (QsAnimatorState.isDetailShowing && !QsAnimatorState.isDetailClosing)) {
            if (QsAnimatorState.isCustomizerShowing) {
                super.setAlpha(0.0f);
                this.mLastAlphaZeroTrace = this.mDateFormat.format(new Date(System.currentTimeMillis())) + " " + Log.getStackTraceString(new Throwable());
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
        float fMin = Math.min(f, Math.min(notificationStackScrollLayoutController.mMaxAlphaForRebind, Math.min(Math.min(notificationStackScrollLayoutController.mMaxAlphaFromView, notificationStackScrollLayoutController.mBarState == 1 ? notificationStackScrollLayoutController.mMaxAlphaForKeyguard : 1.0f), Math.min(notificationStackScrollLayoutController.mMaxAlphaForUnhide, notificationStackScrollLayoutController.mMaxAlphaForGlanceableHub))));
        if (Trace.isEnabled()) {
            Trace.setCounter(TrackGroupUtils.trackGroup("shade", "NSSLResultingAlpha"), (int) (100.0f * fMin));
        }
        super.setAlpha(fMin);
        if (fMin == 0.0f) {
            this.mLastAlphaZeroTrace = this.mDateFormat.format(new Date(System.currentTimeMillis())) + " " + Log.getStackTraceString(new Throwable());
        }
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
        int topPadding;
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        boolean zShouldSkipHeightUpdate = shouldSkipHeightUpdate();
        updateStackPosition(false);
        float fLerp = 0.0f;
        if (!zShouldSkipHeightUpdate) {
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
        float fCalculateAppearFraction = 1.0f;
        if (calculateAppearFraction(f) < 1.0f) {
            fCalculateAppearFraction = calculateAppearFraction(f);
            float fInterpolate = fCalculateAppearFraction >= 0.0f ? NotificationUtils.interpolate(((this.mShelf.getHeight() + this.mWaterfallTopInset) + (-getTopPadding())) - this.mShelf.getHeight(), 0.0f, fCalculateAppearFraction) : (f - getAppearStartPosition()) + (((this.mShelf.getHeight() + this.mWaterfallTopInset) + (-getTopPadding())) - this.mShelf.getHeight());
            topPadding = (int) (f - fInterpolate);
            fLerp = (!isHeadsUpTransition() || fCalculateAppearFraction < 0.0f) ? fInterpolate : MathUtils.lerp(this.mHeadsUpInset - getTopPadding(), 0.0f, fCalculateAppearFraction);
        } else if (this.mShouldShowShelfOnly) {
            topPadding = getTopPadding() + this.mShelf.getHeight();
        } else {
            if (this.mQsFullScreen) {
                int contentHeight = (getContentHeight() - getTopPadding()) + this.mIntrinsicPadding;
                int height2 = this.mShelf.getHeight() + this.mMaxTopPadding;
                if (contentHeight <= height2) {
                    topPadding = height2;
                } else {
                    f = NotificationUtils.interpolate(contentHeight, height2, getQsExpansionFraction$1());
                }
            } else if (zShouldSkipHeightUpdate) {
                f = this.mExpandedHeight;
            }
            topPadding = (int) f;
        }
        this.mAmbientState.mAppearFraction = fCalculateAppearFraction;
        if (topPadding != this.mCurrentStackHeight && !zShouldSkipHeightUpdate) {
            this.mCurrentStackHeight = topPadding;
            updateAlgorithmHeightAndPadding();
            requestChildrenUpdate();
        }
        AmbientState ambientState = this.mAmbientState;
        if (fLerp != ambientState.mStackTranslation) {
            ambientState.mStackTranslation = fLerp;
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
        float fMax;
        this.mAmbientState.setPulseHeight(f);
        if (this.mKeyguardBypassEnabled) {
            notifyAppearChangedListeners();
            int i = SceneContainerFlag.$r8$clinit;
            fMax = Math.max(0.0f, f - this.mIntrinsicPadding);
        } else {
            fMax = Math.max(0.0f, f - this.mAmbientState.getInnerHeight$1());
        }
        requestChildrenUpdate();
        return fMax;
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
    public final void setTranslationX(float f) {
        if (this.mPreviousTranslationX != f) {
            String[] strArrSplit = Log.getStackTraceString(new Throwable()).split("\n");
            StringBuilder sb = new StringBuilder(strArrSplit[0]);
            for (int i = 1; i < Math.min(5, strArrSplit.length); i++) {
                sb.append("\n");
                sb.append(strArrSplit[i]);
            }
            Log.d("StackScroller", " mPreviousTranslationX : " + Float.toString(this.mPreviousTranslationX) + " | translationX : " + Float.toString(f));
            StringBuilder sb2 = new StringBuilder(" setTranslationX ");
            sb2.append((Object) sb);
            Log.d("StackScroller", sb2.toString());
            this.mPreviousTranslationX = f;
        }
        super.setTranslationX(f);
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

    /* JADX WARN: Removed duplicated region for block: B:114:0x0252  */
    /* JADX WARN: Removed duplicated region for block: B:351:0x0881  */
    /* JADX WARN: Removed duplicated region for block: B:371:0x08cc  */
    /* JADX WARN: Removed duplicated region for block: B:373:0x08d2  */
    /* JADX WARN: Removed duplicated region for block: B:374:0x08de  */
    /* JADX WARN: Removed duplicated region for block: B:389:0x0928  */
    /* JADX WARN: Removed duplicated region for block: B:398:0x0953  */
    /* JADX WARN: Removed duplicated region for block: B:401:0x0964  */
    /* JADX WARN: Removed duplicated region for block: B:416:0x09d9  */
    /* JADX WARN: Removed duplicated region for block: B:438:0x011c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:445:0x001b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:478:0x095b A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void startAnimationToState$1() {
        boolean z;
        int i;
        AnimationFilter animationFilter;
        int i2;
        int i3;
        boolean z2;
        boolean z3;
        boolean z4;
        long j;
        int i4;
        int i5;
        int i6;
        long jMax;
        View view;
        ExpandableView expandableView;
        int i7;
        final String key;
        boolean z5;
        boolean z6;
        char c;
        ArrayList arrayList;
        int i8;
        char c2;
        StackStateAnimator stackStateAnimator;
        final StackStateAnimator stackStateAnimator2;
        String str;
        final StackStateAnimator$$ExternalSyntheticLambda1 stackStateAnimator$$ExternalSyntheticLambda1;
        Runnable stackStateAnimator$$ExternalSyntheticLambda12;
        Runnable runnable;
        final StackStateAnimator$$ExternalSyntheticLambda1 stackStateAnimator$$ExternalSyntheticLambda13;
        StackStateAnimator stackStateAnimator3;
        Runnable stackStateAnimator$$ExternalSyntheticLambda14;
        Runnable runnable2;
        char c3;
        Runnable runnable3;
        Runnable runnable4;
        final StackStateAnimator stackStateAnimator4;
        final ExpandableView expandableView2;
        final StackStateAnimator stackStateAnimator5;
        Runnable stackStateAnimator$$ExternalSyntheticLambda15;
        Runnable runnable5;
        Integer num;
        int i9;
        boolean z7;
        NotificationStackScrollLogger notificationStackScrollLogger;
        String strValueOf;
        int i10;
        String str2 = "StackScroller";
        String str3 = null;
        if (this.mNeedsAnimation) {
            for (HeadsUpAnimationEvent headsUpAnimationEvent : ((HashMap) this.mHeadsUpChangeAnimations).values()) {
                ExpandableNotificationRow expandableNotificationRow = headsUpAnimationEvent.row;
                boolean z8 = expandableNotificationRow.mIsHeadsUp;
                boolean z9 = headsUpAnimationEvent.isHeadsUpAppearance;
                if (z9 != z8) {
                    NotificationStackScrollLogger notificationStackScrollLogger2 = this.mLogger;
                    if (notificationStackScrollLogger2 != null) {
                        String str4 = expandableNotificationRow.mLoggingKey;
                        LogLevel logLevel = LogLevel.INFO;
                        NotificationStackScrollLogger$$ExternalSyntheticLambda0 notificationStackScrollLogger$$ExternalSyntheticLambda0 = new NotificationStackScrollLogger$$ExternalSyntheticLambda0(3);
                        LogBuffer logBuffer = notificationStackScrollLogger2.buffer;
                        LogMessage logMessageObtain = logBuffer.obtain("NotificationStackScroll", logLevel, notificationStackScrollLogger$$ExternalSyntheticLambda0, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                        logMessageImpl.str1 = str4;
                        logMessageImpl.bool1 = z9;
                        logMessageImpl.bool2 = z8;
                        logBuffer.commit(logMessageObtain);
                    }
                } else {
                    boolean z10 = expandableNotificationRow.mPinnedStatus.isPinned() && !this.mIsExpanded;
                    if ((!this.mIsExpanded || (this.mKeyguardBypassEnabled && onKeyguard() && this.mInHeadsUpPinnedMode)) && !z9) {
                        i9 = expandableNotificationRow.mJustClicked ? 13 : 12;
                        int i11 = NotificationHeadsUpCycling.$r8$clinit;
                        StackScrollAlgorithm stackScrollAlgorithm = this.mStackScrollAlgorithm;
                        AmbientState ambientState = this.mAmbientState;
                        stackScrollAlgorithm.getClass();
                        if (expandableNotificationRow.getKey().equals(ambientState.mAvalancheController.previousHunKey)) {
                            i9 = 16;
                        }
                        if (expandableNotificationRow.isChildInGroup()) {
                            expandableNotificationRow.setHeadsUpAnimatingAway(false);
                            logHunAnimationSkipped(expandableNotificationRow, "row is child in group");
                        } else {
                            z7 = false;
                            AnimationEvent animationEvent = new AnimationEvent(expandableNotificationRow, i9);
                            animationEvent.headsUpFromBottom = z7;
                            int i12 = StatusBarNotifChips.$r8$clinit;
                            animationEvent.filter.animateHeight = false;
                            this.mAnimationEvents.add(animationEvent);
                            notificationStackScrollLogger = this.mLogger;
                            if (notificationStackScrollLogger != null) {
                                String str5 = expandableNotificationRow.mLoggingKey;
                                if (i9 != 0) {
                                    switch (i9) {
                                        case 11:
                                            strValueOf = "HEADS_UP_APPEAR";
                                            break;
                                        case 12:
                                            strValueOf = "HEADS_UP_DISAPPEAR";
                                            break;
                                        case 13:
                                            strValueOf = "HEADS_UP_DISAPPEAR_CLICK";
                                            break;
                                        case 14:
                                            strValueOf = "HEADS_UP_OTHER";
                                            break;
                                        default:
                                            strValueOf = String.valueOf(i9);
                                            break;
                                    }
                                } else {
                                    strValueOf = "ADD";
                                }
                                LogLevel logLevel2 = LogLevel.INFO;
                                NotificationStackScrollLogger$$ExternalSyntheticLambda0 notificationStackScrollLogger$$ExternalSyntheticLambda02 = new NotificationStackScrollLogger$$ExternalSyntheticLambda0(5);
                                LogBuffer logBuffer2 = notificationStackScrollLogger.buffer;
                                LogMessage logMessageObtain2 = logBuffer2.obtain("NotificationStackScroll", logLevel2, notificationStackScrollLogger$$ExternalSyntheticLambda02, null);
                                LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
                                logMessageImpl2.str1 = str5;
                                logMessageImpl2.str2 = strValueOf;
                                logBuffer2.commit(logMessageObtain2);
                            }
                        }
                    } else {
                        ExpandableViewState expandableViewState = expandableNotificationRow.mViewState;
                        if (expandableViewState == null) {
                            logHunAnimationSkipped(expandableNotificationRow, "row has no viewState");
                        } else {
                            StackScrollAlgorithm stackScrollAlgorithm2 = this.mStackScrollAlgorithm;
                            AmbientState ambientState2 = this.mAmbientState;
                            stackScrollAlgorithm2.getClass();
                            boolean z11 = expandableViewState.mYTranslation + ((float) expandableViewState.height) >= ambientState2.mMaxHeadsUpTranslation;
                            if (z9 && (this.mAddedHeadsUpChildren.contains(expandableNotificationRow) || z10)) {
                                if (z10 || z11) {
                                    int i13 = NotificationHeadsUpCycling.$r8$clinit;
                                    StackScrollAlgorithm stackScrollAlgorithm3 = this.mStackScrollAlgorithm;
                                    AmbientState ambientState3 = this.mAmbientState;
                                    stackScrollAlgorithm3.getClass();
                                    i10 = StackScrollAlgorithm.isCyclingIn(expandableNotificationRow, ambientState3) ? 17 : 11;
                                } else {
                                    i10 = 0;
                                }
                                int i14 = i10;
                                z7 = !z10;
                                i9 = i14;
                                AnimationEvent animationEvent2 = new AnimationEvent(expandableNotificationRow, i9);
                                animationEvent2.headsUpFromBottom = z7;
                                int i122 = StatusBarNotifChips.$r8$clinit;
                                animationEvent2.filter.animateHeight = false;
                                this.mAnimationEvents.add(animationEvent2);
                                notificationStackScrollLogger = this.mLogger;
                                if (notificationStackScrollLogger != null) {
                                }
                            } else {
                                i9 = 14;
                                z7 = false;
                                AnimationEvent animationEvent22 = new AnimationEvent(expandableNotificationRow, i9);
                                animationEvent22.headsUpFromBottom = z7;
                                int i1222 = StatusBarNotifChips.$r8$clinit;
                                animationEvent22.filter.animateHeight = false;
                                this.mAnimationEvents.add(animationEvent22);
                                notificationStackScrollLogger = this.mLogger;
                                if (notificationStackScrollLogger != null) {
                                }
                            }
                        }
                    }
                }
            }
            z = true;
            ((HashMap) this.mHeadsUpChangeAnimations).clear();
            this.mAddedHeadsUpChildren.clear();
            ArrayList arrayList2 = this.mChildrenToRemoveAnimated;
            int size = arrayList2.size();
            int i15 = 0;
            while (i15 < size) {
                Object obj = arrayList2.get(i15);
                i15++;
                ExpandableView expandableView3 = (ExpandableView) obj;
                boolean zContains = this.mSwipedOutViews.contains(expandableView3);
                float translationY = expandableView3.getTranslationY();
                boolean z12 = expandableView3 instanceof ExpandableNotificationRow;
                if (z12) {
                    zContains |= isFullySwipedOut((ExpandableNotificationRow) expandableView3);
                } else if (expandableView3 instanceof MediaContainerView) {
                    zContains = true;
                }
                if (!zContains) {
                    Rect clipBounds = expandableView3.getClipBounds();
                    zContains = clipBounds != null && clipBounds.height() == 0;
                    if (zContains) {
                        expandableView3.removeFromTransientContainer();
                    }
                }
                AnimationEvent animationEvent3 = new AnimationEvent(expandableView3, zContains ? 2 : 1);
                animationEvent3.viewAfterChangingView = getFirstChildBelowTranlsationY(translationY);
                this.mAnimationEvents.add(animationEvent3);
                this.mSwipedOutViews.remove(expandableView3);
                if (this.mDebugRemoveAnimation) {
                    Log.d("StackScroller", "created Remove Event - SwipedOut: " + zContains + " " + (z12 ? ((ExpandableNotificationRow) expandableView3).getKey() : ""));
                }
            }
            this.mChildrenToRemoveAnimated.clear();
            Iterator it = this.mChildrenToAddAnimated.iterator();
            while (it.hasNext()) {
                ExpandableView expandableView4 = (ExpandableView) it.next();
                if (this.mFromMoreCardAdditions.contains(expandableView4)) {
                    this.mAnimationEvents.add(new AnimationEvent(expandableView4, 0, 360L));
                } else {
                    this.mAnimationEvents.add(new AnimationEvent(expandableView4, 0));
                }
            }
            this.mChildrenToAddAnimated.clear();
            this.mFromMoreCardAdditions.clear();
            ArrayList arrayList3 = this.mChildrenChangingPositions;
            int size2 = arrayList3.size();
            int i16 = 0;
            while (i16 < size2) {
                Object obj2 = arrayList3.get(i16);
                i16++;
                ExpandableView expandableView5 = (ExpandableView) obj2;
                if (expandableView5 instanceof ExpandableNotificationRow) {
                    ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) expandableView5;
                    if (expandableNotificationRow2.mEntry.mIsMarkedForUserTriggeredMovement) {
                        num = 500;
                        expandableNotificationRow2.mEntry.mIsMarkedForUserTriggeredMovement = false;
                    } else {
                        num = null;
                    }
                }
                this.mAnimationEvents.add(num == null ? new AnimationEvent(expandableView5, 6) : new AnimationEvent(expandableView5, 6, num.intValue()));
            }
            this.mChildrenChangingPositions.clear();
            if (this.mTopPaddingNeedsAnimation) {
                this.mAnimationEvents.add(this.mAmbientState.mDozing ? new AnimationEvent((ExpandableView) null, 3, 550L) : new AnimationEvent(null, 3));
            }
            this.mTopPaddingNeedsAnimation = false;
            if (this.mHideSensitiveNeedsAnimation) {
                this.mAnimationEvents.add(new AnimationEvent(null, 8));
            }
            this.mHideSensitiveNeedsAnimation = false;
            if (this.mGoToFullShadeNeedsAnimation) {
                this.mAnimationEvents.add(new AnimationEvent(null, 7));
            }
            this.mGoToFullShadeNeedsAnimation = false;
            if (this.mNeedViewResizeAnimation) {
                ArrayList arrayList4 = this.mAnimationEvents;
                int size3 = arrayList4.size();
                int i17 = 0;
                while (true) {
                    if (i17 < size3) {
                        Object obj3 = arrayList4.get(i17);
                        i17++;
                        int i18 = ((AnimationEvent) obj3).animationType;
                        if (i18 == 13 || i18 == 12) {
                        }
                    } else {
                        this.mAnimationEvents.add(new AnimationEvent(null, 9));
                    }
                }
            }
            this.mNeedViewResizeAnimation = false;
            if (this.mExpandedGroupView != null) {
                this.mAnimationEvents.add(new AnimationEvent(this.mExpandedGroupView, 10));
                this.mExpandedGroupView = null;
            }
            this.mNeedsAnimation = false;
        } else {
            z = true;
        }
        if (this.mAnimationEvents.isEmpty() && this.mStateAnimator.mAnimatorSet.isEmpty()) {
            applyCurrentState();
        } else {
            setAnimationRunning(z);
            final StackStateAnimator stackStateAnimator6 = this.mStateAnimator;
            ArrayList arrayList5 = this.mAnimationEvents;
            long j2 = this.mGoToFullShadeDelay;
            stackStateAnimator6.getClass();
            int size4 = arrayList5.size();
            boolean z13 = false;
            int i19 = 0;
            while (true) {
                StackStateAnimator.AnonymousClass1 anonymousClass1 = stackStateAnimator6.mAnimationProperties;
                NotificationStackScrollLayout notificationStackScrollLayout = stackStateAnimator6.mHostLayout;
                String str6 = str2;
                if (i19 < size4) {
                    Object obj4 = arrayList5.get(i19);
                    i19++;
                    AnimationEvent animationEvent4 = (AnimationEvent) obj4;
                    final ExpandableView expandableView6 = animationEvent4.mChangingView;
                    if (!(expandableView6 instanceof ExpandableNotificationRow) || stackStateAnimator6.mLogger == null) {
                        key = str3;
                        z5 = false;
                        z6 = false;
                    } else {
                        ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) expandableView6;
                        boolean z14 = expandableNotificationRow3.mIsHeadsUp;
                        key = expandableNotificationRow3.getKey();
                        z6 = z14;
                        z5 = true;
                    }
                    int i20 = animationEvent4.animationType;
                    if (i20 == 0) {
                        ExpandableViewState expandableViewState2 = expandableView6.mViewState;
                        if (expandableViewState2 == null || expandableViewState2.gone) {
                            stackStateAnimator4 = stackStateAnimator6;
                            c = 6;
                            stackStateAnimator6 = stackStateAnimator4;
                            str2 = str6;
                        } else {
                            if (z5 && z6) {
                                StackStateLogger stackStateLogger = stackStateAnimator6.mLogger;
                                stackStateLogger.getClass();
                                LogLevel logLevel3 = LogLevel.ERROR;
                                StackStateLogger$$ExternalSyntheticLambda0 stackStateLogger$$ExternalSyntheticLambda0 = new StackStateLogger$$ExternalSyntheticLambda0(6);
                                LogBuffer logBuffer3 = stackStateLogger.buffer;
                                LogMessage logMessageObtain3 = logBuffer3.obtain("StackScroll", logLevel3, stackStateLogger$$ExternalSyntheticLambda0, null);
                                ((LogMessageImpl) logMessageObtain3).str1 = NotificationUtils.logKey(key);
                                logBuffer3.commit(logMessageObtain3);
                            }
                            expandableViewState2.applyToView(expandableView6);
                            stackStateAnimator6.mNewAddChildren.add(expandableView6);
                            stackStateAnimator = stackStateAnimator6;
                            arrayList = arrayList5;
                            i8 = size4;
                            c2 = 3;
                            c = 6;
                            stackStateAnimator2 = stackStateAnimator;
                            str = str6;
                            stackStateAnimator2.mNewEvents.add(animationEvent4);
                            stackStateAnimator6 = stackStateAnimator2;
                            str2 = str;
                            arrayList5 = arrayList;
                            size4 = i8;
                        }
                    } else {
                        boolean z15 = z5;
                        c = 6;
                        if (i20 == 1) {
                            int visibility = expandableView6.getVisibility();
                            if (z15) {
                                StackStateLogger stackStateLogger2 = stackStateAnimator6.mLogger;
                                stackStateLogger2.getClass();
                                LogLevel logLevel4 = LogLevel.INFO;
                                stackStateAnimator4 = stackStateAnimator6;
                                StackStateLogger$$ExternalSyntheticLambda0 stackStateLogger$$ExternalSyntheticLambda02 = new StackStateLogger$$ExternalSyntheticLambda0(4);
                                LogBuffer logBuffer4 = stackStateLogger2.notificationRenderBuffer;
                                LogMessage logMessageObtain4 = logBuffer4.obtain("StackScroll", logLevel4, stackStateLogger$$ExternalSyntheticLambda02, null);
                                LogMessageImpl logMessageImpl3 = (LogMessageImpl) logMessageObtain4;
                                logMessageImpl3.str1 = NotificationUtils.logKey(key);
                                logMessageImpl3.int1 = visibility;
                                logMessageImpl3.bool1 = z6;
                                logBuffer4.commit(logMessageObtain4);
                            } else {
                                stackStateAnimator4 = stackStateAnimator6;
                            }
                            if (visibility != 0) {
                                expandableView6.removeFromTransientContainer();
                                stackStateAnimator6 = stackStateAnimator4;
                                str2 = str6;
                            } else {
                                float fMax = -1.0f;
                                if (animationEvent4.viewAfterChangingView != null) {
                                    float translationY2 = expandableView6.getTranslationY();
                                    if (expandableView6 instanceof ExpandableNotificationRow) {
                                        View view2 = animationEvent4.viewAfterChangingView;
                                        if (view2 instanceof ExpandableNotificationRow) {
                                        }
                                    }
                                    float f = expandableView6.mActualHeight;
                                    fMax = Math.max(Math.min(((((ExpandableView) animationEvent4.viewAfterChangingView).mViewState.mYTranslation - ((f / 2.0f) + translationY2)) * 2.0f) / f, 1.0f), -1.0f);
                                }
                                float f2 = fMax;
                                if (z15) {
                                    final int i21 = 0;
                                    final boolean z16 = z6;
                                    stackStateAnimator$$ExternalSyntheticLambda15 = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda0
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            switch (i21) {
                                                case 0:
                                                    StackStateAnimator stackStateAnimator7 = stackStateAnimator4;
                                                    String str7 = key;
                                                    boolean z17 = z16;
                                                    ExpandableView expandableView7 = expandableView6;
                                                    stackStateAnimator7.mLogger.animationStart(str7, "ANIMATION_TYPE_REMOVE", z17);
                                                    expandableView7.mInRemovalAnimation = true;
                                                    break;
                                                case 1:
                                                    StackStateAnimator stackStateAnimator8 = stackStateAnimator4;
                                                    String str8 = key;
                                                    boolean z18 = z16;
                                                    ExpandableView expandableView8 = expandableView6;
                                                    stackStateAnimator8.mLogger.animationStart(str8, "ANIMATION_TYPE_HEADS_UP_CYCLING_OUT", z18);
                                                    expandableView8.mInRemovalAnimation = true;
                                                    break;
                                                default:
                                                    StackStateAnimator stackStateAnimator9 = stackStateAnimator4;
                                                    String str9 = key;
                                                    boolean z19 = z16;
                                                    ExpandableView expandableView9 = expandableView6;
                                                    stackStateAnimator9.mLogger.animationEnd(str9, "ANIMATION_TYPE_REMOVE", z19);
                                                    expandableView9.mInRemovalAnimation = false;
                                                    expandableView9.removeFromTransientContainer();
                                                    stackStateAnimator9.mHostLayout.onChildAnimationFinished();
                                                    break;
                                            }
                                        }
                                    };
                                    final int i22 = 2;
                                    stackStateAnimator5 = stackStateAnimator4;
                                    expandableView2 = expandableView6;
                                    runnable5 = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda0
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            switch (i22) {
                                                case 0:
                                                    StackStateAnimator stackStateAnimator7 = stackStateAnimator4;
                                                    String str7 = key;
                                                    boolean z17 = z16;
                                                    ExpandableView expandableView7 = expandableView6;
                                                    stackStateAnimator7.mLogger.animationStart(str7, "ANIMATION_TYPE_REMOVE", z17);
                                                    expandableView7.mInRemovalAnimation = true;
                                                    break;
                                                case 1:
                                                    StackStateAnimator stackStateAnimator8 = stackStateAnimator4;
                                                    String str8 = key;
                                                    boolean z18 = z16;
                                                    ExpandableView expandableView8 = expandableView6;
                                                    stackStateAnimator8.mLogger.animationStart(str8, "ANIMATION_TYPE_HEADS_UP_CYCLING_OUT", z18);
                                                    expandableView8.mInRemovalAnimation = true;
                                                    break;
                                                default:
                                                    StackStateAnimator stackStateAnimator9 = stackStateAnimator4;
                                                    String str9 = key;
                                                    boolean z19 = z16;
                                                    ExpandableView expandableView9 = expandableView6;
                                                    stackStateAnimator9.mLogger.animationEnd(str9, "ANIMATION_TYPE_REMOVE", z19);
                                                    expandableView9.mInRemovalAnimation = false;
                                                    expandableView9.removeFromTransientContainer();
                                                    stackStateAnimator9.mHostLayout.onChildAnimationFinished();
                                                    break;
                                            }
                                        }
                                    };
                                } else {
                                    expandableView2 = expandableView6;
                                    stackStateAnimator5 = stackStateAnimator4;
                                    stackStateAnimator$$ExternalSyntheticLambda15 = new StackStateAnimator$$ExternalSyntheticLambda1(expandableView2, 3);
                                    final int i23 = 0;
                                    runnable5 = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda9
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            switch (i23) {
                                                case 0:
                                                    StackStateAnimator stackStateAnimator7 = stackStateAnimator5;
                                                    ExpandableView expandableView7 = (ExpandableView) expandableView2;
                                                    stackStateAnimator7.getClass();
                                                    expandableView7.mInRemovalAnimation = false;
                                                    expandableView7.removeFromTransientContainer();
                                                    stackStateAnimator7.mHostLayout.onChildAnimationFinished();
                                                    break;
                                                case 1:
                                                    StackStateAnimator stackStateAnimator8 = stackStateAnimator5;
                                                    String str7 = (String) expandableView2;
                                                    StackStateLogger stackStateLogger3 = stackStateAnimator8.mLogger;
                                                    stackStateLogger3.getClass();
                                                    LogLevel logLevel5 = LogLevel.INFO;
                                                    StackStateLogger$$ExternalSyntheticLambda0 stackStateLogger$$ExternalSyntheticLambda03 = new StackStateLogger$$ExternalSyntheticLambda0(2);
                                                    LogBuffer logBuffer5 = stackStateLogger3.buffer;
                                                    LogMessage logMessageObtain5 = logBuffer5.obtain("StackScroll", logLevel5, stackStateLogger$$ExternalSyntheticLambda03, null);
                                                    ((LogMessageImpl) logMessageObtain5).str1 = NotificationUtils.logKey(str7);
                                                    logBuffer5.commit(logMessageObtain5);
                                                    break;
                                                default:
                                                    StackStateAnimator stackStateAnimator9 = stackStateAnimator5;
                                                    String str8 = (String) expandableView2;
                                                    StackStateLogger stackStateLogger4 = stackStateAnimator9.mLogger;
                                                    stackStateLogger4.getClass();
                                                    LogLevel logLevel6 = LogLevel.INFO;
                                                    StackStateLogger$$ExternalSyntheticLambda0 stackStateLogger$$ExternalSyntheticLambda04 = new StackStateLogger$$ExternalSyntheticLambda0(2);
                                                    LogBuffer logBuffer6 = stackStateLogger4.buffer;
                                                    LogMessage logMessageObtain6 = logBuffer6.obtain("StackScroll", logLevel6, stackStateLogger$$ExternalSyntheticLambda04, null);
                                                    ((LogMessageImpl) logMessageObtain6).str1 = NotificationUtils.logKey(str8);
                                                    logBuffer6.commit(logMessageObtain6);
                                                    break;
                                            }
                                        }
                                    };
                                }
                                expandableView2.performRemoveAnimation(464L, 0L, f2, false, false, stackStateAnimator$$ExternalSyntheticLambda15, runnable5, stackStateAnimator5.getGlobalAnimationFinishedListener(), ExpandableView.ClipSide.BOTTOM);
                                arrayList = arrayList5;
                                i8 = size4;
                                str = str6;
                                z13 = true;
                                c2 = 3;
                                stackStateAnimator2 = stackStateAnimator5;
                            }
                        } else {
                            arrayList = arrayList5;
                            final boolean z17 = z6;
                            final String str7 = key;
                            if (i20 == 2) {
                                boolean zIsFullySwipedOut = notificationStackScrollLayout.isFullySwipedOut(expandableView6);
                                if (z15) {
                                    StackStateLogger stackStateLogger3 = stackStateAnimator6.mLogger;
                                    stackStateLogger3.getClass();
                                    LogLevel logLevel5 = LogLevel.INFO;
                                    i8 = size4;
                                    StackStateLogger$$ExternalSyntheticLambda0 stackStateLogger$$ExternalSyntheticLambda03 = new StackStateLogger$$ExternalSyntheticLambda0(1);
                                    LogBuffer logBuffer5 = stackStateLogger3.notificationRenderBuffer;
                                    LogMessage logMessageObtain5 = logBuffer5.obtain("StackScroll", logLevel5, stackStateLogger$$ExternalSyntheticLambda03, null);
                                    LogMessageImpl logMessageImpl4 = (LogMessageImpl) logMessageObtain5;
                                    logMessageImpl4.str1 = NotificationUtils.logKey(str7);
                                    logMessageImpl4.bool1 = zIsFullySwipedOut;
                                    logMessageImpl4.bool2 = z17;
                                    logBuffer5.commit(logMessageObtain5);
                                } else {
                                    i8 = size4;
                                }
                                if (zIsFullySwipedOut) {
                                    expandableView6.removeFromTransientContainer();
                                }
                            } else {
                                i8 = size4;
                                ExpandableViewState expandableViewState3 = stackStateAnimator6.mTmpState;
                                if (i20 == 17) {
                                    stackStateAnimator6.mHeadsUpAppearChildren.add(expandableView6);
                                    expandableViewState3.copyFrom(expandableView6.mViewState);
                                    expandableViewState3.setYTranslation(expandableView6.mViewState.mYTranslation + (animationEvent4.headsUpFromBottom ? stackStateAnimator6.mHeadsUpAppearHeightBottom + stackStateAnimator6.mHeadsUpCyclingPadding : -stackStateAnimator6.mHeadsUpCyclingPadding));
                                    expandableViewState3.applyToView(expandableView6);
                                    if (z15) {
                                        StackStateLogger stackStateLogger4 = stackStateAnimator6.mLogger;
                                        stackStateLogger4.getClass();
                                        LogLevel logLevel6 = LogLevel.INFO;
                                        StackStateLogger$$ExternalSyntheticLambda0 stackStateLogger$$ExternalSyntheticLambda04 = new StackStateLogger$$ExternalSyntheticLambda0(3);
                                        LogBuffer logBuffer6 = stackStateLogger4.buffer;
                                        LogMessage logMessageObtain6 = logBuffer6.obtain("StackScroll", logLevel6, stackStateLogger$$ExternalSyntheticLambda04, null);
                                        ((LogMessageImpl) logMessageObtain6).str1 = NotificationUtils.logKey(str7);
                                        logBuffer6.commit(logMessageObtain6);
                                        final int i24 = 1;
                                        runnable4 = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda9
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                switch (i24) {
                                                    case 0:
                                                        StackStateAnimator stackStateAnimator7 = stackStateAnimator6;
                                                        ExpandableView expandableView7 = (ExpandableView) str7;
                                                        stackStateAnimator7.getClass();
                                                        expandableView7.mInRemovalAnimation = false;
                                                        expandableView7.removeFromTransientContainer();
                                                        stackStateAnimator7.mHostLayout.onChildAnimationFinished();
                                                        break;
                                                    case 1:
                                                        StackStateAnimator stackStateAnimator8 = stackStateAnimator6;
                                                        String str72 = (String) str7;
                                                        StackStateLogger stackStateLogger32 = stackStateAnimator8.mLogger;
                                                        stackStateLogger32.getClass();
                                                        LogLevel logLevel52 = LogLevel.INFO;
                                                        StackStateLogger$$ExternalSyntheticLambda0 stackStateLogger$$ExternalSyntheticLambda032 = new StackStateLogger$$ExternalSyntheticLambda0(2);
                                                        LogBuffer logBuffer52 = stackStateLogger32.buffer;
                                                        LogMessage logMessageObtain52 = logBuffer52.obtain("StackScroll", logLevel52, stackStateLogger$$ExternalSyntheticLambda032, null);
                                                        ((LogMessageImpl) logMessageObtain52).str1 = NotificationUtils.logKey(str72);
                                                        logBuffer52.commit(logMessageObtain52);
                                                        break;
                                                    default:
                                                        StackStateAnimator stackStateAnimator9 = stackStateAnimator6;
                                                        String str8 = (String) str7;
                                                        StackStateLogger stackStateLogger42 = stackStateAnimator9.mLogger;
                                                        stackStateLogger42.getClass();
                                                        LogLevel logLevel62 = LogLevel.INFO;
                                                        StackStateLogger$$ExternalSyntheticLambda0 stackStateLogger$$ExternalSyntheticLambda042 = new StackStateLogger$$ExternalSyntheticLambda0(2);
                                                        LogBuffer logBuffer62 = stackStateLogger42.buffer;
                                                        LogMessage logMessageObtain62 = logBuffer62.obtain("StackScroll", logLevel62, stackStateLogger$$ExternalSyntheticLambda042, null);
                                                        ((LogMessageImpl) logMessageObtain62).str1 = NotificationUtils.logKey(str8);
                                                        logBuffer62.commit(logMessageObtain62);
                                                        break;
                                                }
                                            }
                                        };
                                    } else {
                                        runnable4 = null;
                                    }
                                    expandableView6.performAddAnimation(0L, 400L, true, true, runnable4);
                                } else {
                                    if (i20 == 11) {
                                        stackStateAnimator6.mHeadsUpAppearChildren.add(expandableView6);
                                        expandableViewState3.copyFrom(expandableView6.mViewState);
                                        boolean z18 = animationEvent4.headsUpFromBottom;
                                        int i25 = NotificationsHunSharedAnimationValues.$r8$clinit;
                                        expandableViewState3.setYTranslation(z18 ? stackStateAnimator6.mHeadsUpAppearHeightBottom + stackStateAnimator6.mHeadsUpAppearStartAboveScreen : (-stackStateAnimator6.mStackTopMargin) - stackStateAnimator6.mHeadsUpAppearStartAboveScreen);
                                        expandableViewState3.applyToView(expandableView6);
                                        anonymousClass1.setCustomInterpolator(View.TRANSLATION_Y, Interpolators.FAST_OUT_SLOW_IN);
                                        if (z15) {
                                            StackStateLogger stackStateLogger5 = stackStateAnimator6.mLogger;
                                            stackStateLogger5.getClass();
                                            LogLevel logLevel7 = LogLevel.INFO;
                                            c3 = 3;
                                            StackStateLogger$$ExternalSyntheticLambda0 stackStateLogger$$ExternalSyntheticLambda05 = new StackStateLogger$$ExternalSyntheticLambda0(3);
                                            LogBuffer logBuffer7 = stackStateLogger5.buffer;
                                            LogMessage logMessageObtain7 = logBuffer7.obtain("StackScroll", logLevel7, stackStateLogger$$ExternalSyntheticLambda05, null);
                                            ((LogMessageImpl) logMessageObtain7).str1 = NotificationUtils.logKey(str7);
                                            logBuffer7.commit(logMessageObtain7);
                                            final int i26 = 2;
                                            runnable3 = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda9
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    switch (i26) {
                                                        case 0:
                                                            StackStateAnimator stackStateAnimator7 = stackStateAnimator6;
                                                            ExpandableView expandableView7 = (ExpandableView) str7;
                                                            stackStateAnimator7.getClass();
                                                            expandableView7.mInRemovalAnimation = false;
                                                            expandableView7.removeFromTransientContainer();
                                                            stackStateAnimator7.mHostLayout.onChildAnimationFinished();
                                                            break;
                                                        case 1:
                                                            StackStateAnimator stackStateAnimator8 = stackStateAnimator6;
                                                            String str72 = (String) str7;
                                                            StackStateLogger stackStateLogger32 = stackStateAnimator8.mLogger;
                                                            stackStateLogger32.getClass();
                                                            LogLevel logLevel52 = LogLevel.INFO;
                                                            StackStateLogger$$ExternalSyntheticLambda0 stackStateLogger$$ExternalSyntheticLambda032 = new StackStateLogger$$ExternalSyntheticLambda0(2);
                                                            LogBuffer logBuffer52 = stackStateLogger32.buffer;
                                                            LogMessage logMessageObtain52 = logBuffer52.obtain("StackScroll", logLevel52, stackStateLogger$$ExternalSyntheticLambda032, null);
                                                            ((LogMessageImpl) logMessageObtain52).str1 = NotificationUtils.logKey(str72);
                                                            logBuffer52.commit(logMessageObtain52);
                                                            break;
                                                        default:
                                                            StackStateAnimator stackStateAnimator9 = stackStateAnimator6;
                                                            String str8 = (String) str7;
                                                            StackStateLogger stackStateLogger42 = stackStateAnimator9.mLogger;
                                                            stackStateLogger42.getClass();
                                                            LogLevel logLevel62 = LogLevel.INFO;
                                                            StackStateLogger$$ExternalSyntheticLambda0 stackStateLogger$$ExternalSyntheticLambda042 = new StackStateLogger$$ExternalSyntheticLambda0(2);
                                                            LogBuffer logBuffer62 = stackStateLogger42.buffer;
                                                            LogMessage logMessageObtain62 = logBuffer62.obtain("StackScroll", logLevel62, stackStateLogger$$ExternalSyntheticLambda042, null);
                                                            ((LogMessageImpl) logMessageObtain62).str1 = NotificationUtils.logKey(str8);
                                                            logBuffer62.commit(logMessageObtain62);
                                                            break;
                                                    }
                                                }
                                            };
                                        } else {
                                            c3 = 3;
                                            runnable3 = null;
                                        }
                                        expandableView6.performAddAnimation(0L, 400L, true, false, runnable3);
                                        stackStateAnimator = stackStateAnimator6;
                                        c2 = c3;
                                    } else {
                                        c2 = 3;
                                        if (i20 == 16) {
                                            stackStateAnimator6.mHeadsUpDisappearChildren.add(expandableView6);
                                            expandableViewState3.copyFrom(expandableView6.mViewState);
                                            if (expandableView6.getParent() == null) {
                                                notificationStackScrollLayout.addTransientView(expandableView6, 0);
                                                expandableView6.mTransientContainer = notificationStackScrollLayout;
                                                expandableViewState3.setYTranslation(expandableViewState3.mYTranslation + 10.0f);
                                                stackStateAnimator$$ExternalSyntheticLambda13 = new StackStateAnimator$$ExternalSyntheticLambda1(expandableView6, 1);
                                            } else {
                                                stackStateAnimator$$ExternalSyntheticLambda13 = null;
                                            }
                                            boolean z19 = ((expandableView6 instanceof ExpandableNotificationRow) && ((ExpandableNotificationRow) expandableView6).mDismissed) ? false : true;
                                            if (z19) {
                                                if (z15) {
                                                    final int i27 = 1;
                                                    final StackStateAnimator stackStateAnimator7 = stackStateAnimator6;
                                                    Runnable runnable6 = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda0
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            switch (i27) {
                                                                case 0:
                                                                    StackStateAnimator stackStateAnimator72 = stackStateAnimator7;
                                                                    String str72 = str7;
                                                                    boolean z172 = z17;
                                                                    ExpandableView expandableView7 = expandableView6;
                                                                    stackStateAnimator72.mLogger.animationStart(str72, "ANIMATION_TYPE_REMOVE", z172);
                                                                    expandableView7.mInRemovalAnimation = true;
                                                                    break;
                                                                case 1:
                                                                    StackStateAnimator stackStateAnimator8 = stackStateAnimator7;
                                                                    String str8 = str7;
                                                                    boolean z182 = z17;
                                                                    ExpandableView expandableView8 = expandableView6;
                                                                    stackStateAnimator8.mLogger.animationStart(str8, "ANIMATION_TYPE_HEADS_UP_CYCLING_OUT", z182);
                                                                    expandableView8.mInRemovalAnimation = true;
                                                                    break;
                                                                default:
                                                                    StackStateAnimator stackStateAnimator9 = stackStateAnimator7;
                                                                    String str9 = str7;
                                                                    boolean z192 = z17;
                                                                    ExpandableView expandableView9 = expandableView6;
                                                                    stackStateAnimator9.mLogger.animationEnd(str9, "ANIMATION_TYPE_REMOVE", z192);
                                                                    expandableView9.mInRemovalAnimation = false;
                                                                    expandableView9.removeFromTransientContainer();
                                                                    stackStateAnimator9.mHostLayout.onChildAnimationFinished();
                                                                    break;
                                                            }
                                                        }
                                                    };
                                                    StackStateAnimator$$ExternalSyntheticLambda3 stackStateAnimator$$ExternalSyntheticLambda3 = new StackStateAnimator$$ExternalSyntheticLambda3(stackStateAnimator7, str7, z17, expandableView6, stackStateAnimator$$ExternalSyntheticLambda13);
                                                    stackStateAnimator3 = stackStateAnimator7;
                                                    stackStateAnimator$$ExternalSyntheticLambda14 = runnable6;
                                                    runnable2 = stackStateAnimator$$ExternalSyntheticLambda3;
                                                } else {
                                                    stackStateAnimator3 = stackStateAnimator6;
                                                    final int i28 = 1;
                                                    Runnable runnable7 = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda6
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            switch (i28) {
                                                                case 0:
                                                                    ExpandableView expandableView7 = expandableView6;
                                                                    StackStateAnimator$$ExternalSyntheticLambda1 stackStateAnimator$$ExternalSyntheticLambda16 = stackStateAnimator$$ExternalSyntheticLambda13;
                                                                    expandableView7.mInRemovalAnimation = false;
                                                                    if (stackStateAnimator$$ExternalSyntheticLambda16 != null) {
                                                                        stackStateAnimator$$ExternalSyntheticLambda16.run();
                                                                        break;
                                                                    }
                                                                    break;
                                                                default:
                                                                    ExpandableView expandableView8 = expandableView6;
                                                                    StackStateAnimator$$ExternalSyntheticLambda1 stackStateAnimator$$ExternalSyntheticLambda17 = stackStateAnimator$$ExternalSyntheticLambda13;
                                                                    expandableView8.mInRemovalAnimation = false;
                                                                    if (stackStateAnimator$$ExternalSyntheticLambda17 != null) {
                                                                        stackStateAnimator$$ExternalSyntheticLambda17.run();
                                                                        break;
                                                                    }
                                                                    break;
                                                            }
                                                        }
                                                    };
                                                    stackStateAnimator$$ExternalSyntheticLambda14 = new StackStateAnimator$$ExternalSyntheticLambda1(expandableView6, 0);
                                                    runnable2 = runnable7;
                                                }
                                                stackStateAnimator = stackStateAnimator3;
                                                anonymousClass1.delay += expandableView6.performRemoveAnimation(400L, 0L, 0.0f, true, true, stackStateAnimator$$ExternalSyntheticLambda14, runnable2, stackStateAnimator3.getGlobalAnimationFinishedListener(), ExpandableView.ClipSide.TOP);
                                                anonymousClass1.duration = 400L;
                                                anonymousClass1.setCustomInterpolator(View.TRANSLATION_Y, Interpolators.LINEAR);
                                                StackStateAnimator.this.mAnimationFilter.animateY = true;
                                                expandableViewState3.animateTo(expandableView6, anonymousClass1);
                                                anonymousClass1.mInterpolatorMap = null;
                                            } else {
                                                stackStateAnimator = stackStateAnimator6;
                                                if (stackStateAnimator$$ExternalSyntheticLambda13 != null) {
                                                    stackStateAnimator$$ExternalSyntheticLambda13.run();
                                                }
                                            }
                                            z13 |= z19;
                                        } else {
                                            stackStateAnimator = stackStateAnimator6;
                                            if (i20 == 12 || i20 == 13) {
                                                stackStateAnimator2 = stackStateAnimator;
                                                stackStateAnimator2.mHeadsUpDisappearChildren.add(expandableView6);
                                                expandableViewState3.copyFrom(expandableView6.mViewState);
                                                if (expandableView6.getParent() == null) {
                                                    str = str6;
                                                    Log.d(str, "HEADS_UP_DISAPPEAR addTransientView : " + expandableView6);
                                                    notificationStackScrollLayout.addTransientView(expandableView6, 0);
                                                    expandableView6.mTransientContainer = notificationStackScrollLayout;
                                                    boolean z20 = animationEvent4.headsUpFromBottom;
                                                    int i29 = NotificationsHunSharedAnimationValues.$r8$clinit;
                                                    expandableViewState3.setYTranslation(z20 ? stackStateAnimator2.mHeadsUpAppearHeightBottom + stackStateAnimator2.mHeadsUpAppearStartAboveScreen : (-stackStateAnimator2.mStackTopMargin) - stackStateAnimator2.mHeadsUpAppearStartAboveScreen);
                                                    stackStateAnimator$$ExternalSyntheticLambda1 = new StackStateAnimator$$ExternalSyntheticLambda1(expandableView6, 1);
                                                } else {
                                                    str = str6;
                                                    stackStateAnimator$$ExternalSyntheticLambda1 = null;
                                                }
                                                boolean z21 = ((expandableView6 instanceof ExpandableNotificationRow) && ((ExpandableNotificationRow) expandableView6).mDismissed) ? false : true;
                                                if (z21) {
                                                    if (z15) {
                                                        final String str8 = i20 == 12 ? "ANIMATION_TYPE_HEADS_UP_DISAPPEAR" : "ANIMATION_TYPE_HEADS_UP_DISAPPEAR_CLICK";
                                                        StackStateAnimator$$ExternalSyntheticLambda3 stackStateAnimator$$ExternalSyntheticLambda32 = new StackStateAnimator$$ExternalSyntheticLambda3(stackStateAnimator2, str7, str8, z17, expandableView6);
                                                        Runnable runnable8 = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda4
                                                            @Override // java.lang.Runnable
                                                            public final void run() {
                                                                StackStateAnimator stackStateAnimator8 = stackStateAnimator2;
                                                                String str9 = str7;
                                                                String str10 = str8;
                                                                boolean z22 = z17;
                                                                ExpandableView expandableView7 = expandableView6;
                                                                StackStateAnimator$$ExternalSyntheticLambda1 stackStateAnimator$$ExternalSyntheticLambda16 = stackStateAnimator$$ExternalSyntheticLambda1;
                                                                stackStateAnimator8.mLogger.animationEnd(str9, str10, z22);
                                                                expandableView7.mInRemovalAnimation = false;
                                                                if (stackStateAnimator$$ExternalSyntheticLambda16 != null) {
                                                                    stackStateAnimator$$ExternalSyntheticLambda16.run();
                                                                }
                                                            }
                                                        };
                                                        stackStateAnimator2 = stackStateAnimator2;
                                                        stackStateAnimator$$ExternalSyntheticLambda12 = stackStateAnimator$$ExternalSyntheticLambda32;
                                                        runnable = runnable8;
                                                    } else {
                                                        final StackStateAnimator$$ExternalSyntheticLambda1 stackStateAnimator$$ExternalSyntheticLambda16 = stackStateAnimator$$ExternalSyntheticLambda1;
                                                        final int i30 = 0;
                                                        stackStateAnimator$$ExternalSyntheticLambda12 = new StackStateAnimator$$ExternalSyntheticLambda1(expandableView6, 2);
                                                        runnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda6
                                                            @Override // java.lang.Runnable
                                                            public final void run() {
                                                                switch (i30) {
                                                                    case 0:
                                                                        ExpandableView expandableView7 = expandableView6;
                                                                        StackStateAnimator$$ExternalSyntheticLambda1 stackStateAnimator$$ExternalSyntheticLambda162 = stackStateAnimator$$ExternalSyntheticLambda16;
                                                                        expandableView7.mInRemovalAnimation = false;
                                                                        if (stackStateAnimator$$ExternalSyntheticLambda162 != null) {
                                                                            stackStateAnimator$$ExternalSyntheticLambda162.run();
                                                                            break;
                                                                        }
                                                                        break;
                                                                    default:
                                                                        ExpandableView expandableView8 = expandableView6;
                                                                        StackStateAnimator$$ExternalSyntheticLambda1 stackStateAnimator$$ExternalSyntheticLambda17 = stackStateAnimator$$ExternalSyntheticLambda16;
                                                                        expandableView8.mInRemovalAnimation = false;
                                                                        if (stackStateAnimator$$ExternalSyntheticLambda17 != null) {
                                                                            stackStateAnimator$$ExternalSyntheticLambda17.run();
                                                                            break;
                                                                        }
                                                                        break;
                                                                }
                                                            }
                                                        };
                                                    }
                                                    anonymousClass1.delay += expandableView6.performRemoveAnimation(400L, 0L, 0.0f, true, false, stackStateAnimator$$ExternalSyntheticLambda12, runnable, stackStateAnimator2.getGlobalAnimationFinishedListener(), ExpandableView.ClipSide.BOTTOM);
                                                    anonymousClass1.duration = 400L;
                                                    anonymousClass1.setCustomInterpolator(View.TRANSLATION_Y, Interpolators.FAST_OUT_SLOW_IN_REVERSE);
                                                    StackStateAnimator.this.mAnimationFilter.animateY = true;
                                                    expandableViewState3.animateTo(expandableView6, anonymousClass1);
                                                    anonymousClass1.mInterpolatorMap = null;
                                                } else {
                                                    StackStateAnimator$$ExternalSyntheticLambda1 stackStateAnimator$$ExternalSyntheticLambda17 = stackStateAnimator$$ExternalSyntheticLambda1;
                                                    if (stackStateAnimator$$ExternalSyntheticLambda17 != null) {
                                                        stackStateAnimator$$ExternalSyntheticLambda17.run();
                                                    }
                                                }
                                                z13 |= z21;
                                            }
                                        }
                                    }
                                    stackStateAnimator2 = stackStateAnimator;
                                    str = str6;
                                }
                            }
                            stackStateAnimator = stackStateAnimator6;
                            c2 = 3;
                            stackStateAnimator2 = stackStateAnimator;
                            str = str6;
                        }
                        stackStateAnimator2.mNewEvents.add(animationEvent4);
                        stackStateAnimator6 = stackStateAnimator2;
                        str2 = str;
                        arrayList5 = arrayList;
                        size4 = i8;
                    }
                    str3 = null;
                } else {
                    StackStateAnimator stackStateAnimator8 = stackStateAnimator6;
                    int childCount = notificationStackScrollLayout.getChildCount();
                    AnimationFilter animationFilter2 = stackStateAnimator8.mAnimationFilter;
                    ArrayList arrayList6 = stackStateAnimator8.mNewEvents;
                    animationFilter2.reset();
                    int size5 = arrayList6.size();
                    for (int i31 = 0; i31 < size5; i31++) {
                        AnimationEvent animationEvent5 = (AnimationEvent) arrayList6.get(i31);
                        animationFilter2.combineFilter(((AnimationEvent) arrayList6.get(i31)).filter);
                        if (animationEvent5.animationType == 7) {
                            animationFilter2.hasGoToFullShadeEvent = true;
                        }
                    }
                    stackStateAnimator8.mCurrentAdditionalDelay = j2;
                    ArrayList arrayList7 = stackStateAnimator8.mNewEvents;
                    AnimationFilter[] animationFilterArr = AnimationEvent.FILTERS;
                    int size6 = arrayList7.size();
                    int i32 = 0;
                    long jMax2 = 0;
                    while (true) {
                        if (i32 < size6) {
                            AnimationEvent animationEvent6 = (AnimationEvent) arrayList7.get(i32);
                            jMax2 = Math.max(jMax2, animationEvent6.length);
                            if (animationEvent6.animationType == 7) {
                                jMax2 = animationEvent6.length;
                            } else {
                                i32++;
                            }
                        }
                    }
                    stackStateAnimator8.mCurrentLength = jMax2;
                    ExpandableView expandableView7 = (ExpandableView) notificationStackScrollLayout.getChildAt(notificationStackScrollLayout.mShelf.mViewState.notGoneIndex);
                    if (expandableView7 != null) {
                        expandableView7.getTranslationY();
                    } else {
                        notificationStackScrollLayout.getTopPadding();
                    }
                    int i33 = 0;
                    int i34 = 0;
                    while (i33 < childCount) {
                        ExpandableView expandableView8 = (ExpandableView) notificationStackScrollLayout.getChildAt(i33);
                        ExpandableViewState expandableViewState4 = expandableView8.mViewState;
                        if (expandableViewState4 == null || expandableView8.getVisibility() == 8) {
                            i = childCount;
                            animationFilter = animationFilter2;
                            i2 = i33;
                            i3 = i34;
                            z2 = z13;
                        } else {
                            if (!stackStateAnimator8.mShadeExpanded) {
                                ViewState.AnonymousClass1 anonymousClass12 = ViewState.NO_NEW_ANIMATIONS;
                                if (!ViewState.isAnimating(expandableView8, PhysicsPropertyAnimator.TAG_ANIMATOR_TRANSLATION_Y) && !stackStateAnimator8.mHeadsUpDisappearChildren.contains(expandableView8) && !stackStateAnimator8.mHeadsUpAppearChildren.contains(expandableView8) && !isPinnedHeadsUp(expandableView8)) {
                                    expandableViewState4.applyToView(expandableView8);
                                    i = childCount;
                                    animationFilter = animationFilter2;
                                    i2 = i33;
                                    i3 = i34;
                                    z2 = z13;
                                }
                            }
                            if (anonymousClass1.wasAdded(expandableView8) && i34 < 5) {
                                i34++;
                            }
                            boolean zWasAdded = anonymousClass1.wasAdded(expandableView8);
                            anonymousClass1.duration = stackStateAnimator8.mCurrentLength;
                            boolean z22 = expandableView8 instanceof StackScrollerDecorView;
                            boolean z23 = zWasAdded || z22;
                            boolean z24 = expandableView8 instanceof ExpandableNotificationRow;
                            if (z24) {
                                ExpandableNotificationRow expandableNotificationRow4 = (ExpandableNotificationRow) expandableView8;
                                i = childCount;
                                if (expandableNotificationRow4.mEntry.isOngoingActivity() && expandableNotificationRow4.mEntry.isPromotedState()) {
                                    z3 = true;
                                }
                                if (z23 || !animationFilter2.hasGoToFullShadeEvent) {
                                    z4 = zWasAdded;
                                    z2 = z13;
                                } else {
                                    if (z22) {
                                        z4 = zWasAdded;
                                        z2 = z13;
                                        i7 = 0;
                                    } else {
                                        i7 = stackStateAnimator8.mGoToFullShadeAppearingTranslation;
                                        z4 = zWasAdded;
                                        z2 = z13;
                                        anonymousClass1.duration = ((long) (((float) Math.pow(i34, 0.699999988079071d)) * 100.0f)) + 514;
                                    }
                                    expandableView8.setTranslationY(expandableViewState4.mYTranslation + i7);
                                }
                                if (z3 && z4) {
                                    anonymousClass1.duration = 100L;
                                }
                                anonymousClass1.delay = 0L;
                                if (!z4 || (animationFilter2.hasDelays && !(expandableViewState4.mYTranslation == expandableView8.getTranslationY() && expandableViewState4.mZTranslation == expandableView8.getTranslationZ() && expandableViewState4.mAlpha == expandableView8.getAlpha() && expandableViewState4.height == expandableView8.mActualHeight && expandableViewState4.clipTopAmount == expandableView8.mClipTopAmount))) {
                                    long j3 = stackStateAnimator8.mCurrentAdditionalDelay;
                                    if (animationFilter2.hasGoToFullShadeEvent) {
                                        j = animationFilter2.customDelay;
                                        if (j != -1) {
                                            animationFilter = animationFilter2;
                                            i2 = i33;
                                            i4 = i34;
                                        } else {
                                            ArrayList arrayList8 = stackStateAnimator8.mNewEvents;
                                            int size7 = arrayList8.size();
                                            animationFilter = animationFilter2;
                                            int i35 = 0;
                                            long j4 = 0;
                                            while (i35 < size7) {
                                                Object obj5 = arrayList8.get(i35);
                                                ArrayList arrayList9 = arrayList8;
                                                int i36 = i35 + 1;
                                                AnimationEvent animationEvent7 = (AnimationEvent) obj5;
                                                int i37 = animationEvent7.animationType;
                                                long j5 = 80;
                                                if (i37 != 0) {
                                                    i5 = i33;
                                                    if (i37 == 1) {
                                                        int i38 = expandableViewState4.notGoneIndex;
                                                        view = animationEvent7.viewAfterChangingView;
                                                        if (view != null) {
                                                            int childCount2 = notificationStackScrollLayout.getChildCount() - 1;
                                                            while (true) {
                                                                if (childCount2 >= 0) {
                                                                    View childAt = notificationStackScrollLayout.getChildAt(childCount2);
                                                                    int i39 = childCount2;
                                                                    i6 = i34;
                                                                    if (childAt.getVisibility() == 8 || childAt == notificationStackScrollLayout.mShelf) {
                                                                        childCount2 = i39 - 1;
                                                                        i34 = i6;
                                                                    } else {
                                                                        expandableView = (ExpandableView) childAt;
                                                                    }
                                                                } else {
                                                                    i6 = i34;
                                                                    expandableView = null;
                                                                }
                                                            }
                                                        } else {
                                                            i6 = i34;
                                                            expandableView = (ExpandableView) view;
                                                        }
                                                        if (expandableView != null) {
                                                            if (i38 >= expandableView.mViewState.notGoneIndex) {
                                                                i38++;
                                                            }
                                                            jMax = Math.max(Math.max(0, Math.min(2, Math.abs(i38 - r1) - 1)) * j5, j4);
                                                        }
                                                    } else if (i37 != 2) {
                                                        i6 = i34;
                                                    } else {
                                                        j5 = 32;
                                                        int i382 = expandableViewState4.notGoneIndex;
                                                        view = animationEvent7.viewAfterChangingView;
                                                        if (view != null) {
                                                        }
                                                        if (expandableView != null) {
                                                        }
                                                    }
                                                    i35 = i36;
                                                    arrayList8 = arrayList9;
                                                    i33 = i5;
                                                    i34 = i6;
                                                } else {
                                                    i5 = i33;
                                                    i6 = i34;
                                                    jMax = Math.max((2 - Math.max(0, Math.min(2, Math.abs(expandableViewState4.notGoneIndex - animationEvent7.mChangingView.mViewState.notGoneIndex) - 1))) * 80, j4);
                                                }
                                                j4 = jMax;
                                                i35 = i36;
                                                arrayList8 = arrayList9;
                                                i33 = i5;
                                                i34 = i6;
                                            }
                                            i2 = i33;
                                            i4 = i34;
                                            j = j4;
                                        }
                                    } else {
                                        animationFilter = animationFilter2;
                                        i2 = i33;
                                        i4 = i34;
                                        j = 0;
                                    }
                                    anonymousClass1.delay = j3 + j;
                                } else {
                                    animationFilter = animationFilter2;
                                    i2 = i33;
                                    i4 = i34;
                                }
                                if (z4 || !z24) {
                                    expandableViewState4.animateTo(expandableView8, anonymousClass1);
                                    i3 = i4;
                                } else {
                                    ExpandableNotificationRow expandableNotificationRow5 = (ExpandableNotificationRow) expandableView8;
                                    if (expandableNotificationRow5.mEntry.isOngoingActivity() && expandableNotificationRow5.mEntry.isPromotedState()) {
                                        anonymousClass1.delay = 400L;
                                    }
                                    expandableViewState4.animateTo(expandableView8, anonymousClass1);
                                    i3 = i4;
                                }
                            } else {
                                i = childCount;
                            }
                            z3 = false;
                            if (z23) {
                                z4 = zWasAdded;
                                z2 = z13;
                                if (z3) {
                                    anonymousClass1.duration = 100L;
                                }
                                anonymousClass1.delay = 0L;
                                if (z4) {
                                    long j32 = stackStateAnimator8.mCurrentAdditionalDelay;
                                    if (animationFilter2.hasGoToFullShadeEvent) {
                                    }
                                    anonymousClass1.delay = j32 + j;
                                    if (z4) {
                                        expandableViewState4.animateTo(expandableView8, anonymousClass1);
                                        i3 = i4;
                                    }
                                }
                            }
                        }
                        i34 = i3;
                        i33 = i2 + 1;
                        childCount = i;
                        z13 = z2;
                        animationFilter2 = animationFilter;
                    }
                    boolean z25 = z13;
                    if (stackStateAnimator8.mAnimatorSet.isEmpty() && !notificationStackScrollLayout.mHeadsUpAnimatingAway && !z25) {
                        stackStateAnimator8.onAnimationFinished();
                    }
                    stackStateAnimator8.mHeadsUpAppearChildren.clear();
                    stackStateAnimator8.mHeadsUpDisappearChildren.clear();
                    stackStateAnimator8.mNewEvents.clear();
                    stackStateAnimator8.mNewAddChildren.clear();
                    anonymousClass1.mInterpolatorMap = null;
                    this.mAnimationEvents.clear();
                    updateViewShadows();
                }
            }
        }
        this.mGoToFullShadeDelay = 0L;
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
        SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 sequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1ComputeHeightPerNotificationLimit = notificationStackSizeCalculator.computeHeightPerNotificationLimit(this, height);
        if (i3 >= 0) {
            SequenceBuilderIterator it = SequencesKt__SequenceBuilderKt.iterator(sequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1ComputeHeightPerNotificationLimit.$block$inlined);
            int i4 = 0;
            while (true) {
                if (!it.hasNext()) {
                    obj = (NotificationStackSizeCalculator.StackHeight) SequencesKt___SequencesKt.last(sequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1ComputeHeightPerNotificationLimit);
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
            obj = (NotificationStackSizeCalculator.StackHeight) SequencesKt___SequencesKt.last(sequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1ComputeHeightPerNotificationLimit);
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
        int contentHeight = getContentHeight();
        ambientState.getClass();
        ambientState.mContentHeight = contentHeight;
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
        if (NotiRune.NOTI_STYLE_POP_OVER_DISMISS_CLIP_VIEW && ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet() && this.mStatusBarState != 1 && this.mIsExpanded) {
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
            int iTargetScrollForView = targetScrollForView(expandableView, positionInLinearLayout);
            int intrinsicHeight = expandableView.getIntrinsicHeight() + positionInLinearLayout;
            int iMax = Math.max(0, Math.min(iTargetScrollForView, getScrollRange()));
            if (getOwnScrollY() < iMax || intrinsicHeight < getOwnScrollY()) {
                setOwnScrollY(iMax);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateForwardAndBackwardScrollability() {
        boolean z;
        boolean z2;
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (this.mScrollable) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
            z = notificationStackScrollLayout.getOwnScrollY() < notificationStackScrollLayout.getScrollRange();
        }
        if (this.mScrollable) {
            AnonymousClass9 anonymousClass9 = this.mScrollAdapter;
            anonymousClass9.getClass();
            z2 = NotificationStackScrollLayout.this.getOwnScrollY() != 0;
        }
        boolean z3 = (z == this.mForwardScrollable && z2 == this.mBackwardScrollable) ? false : true;
        this.mForwardScrollable = z;
        this.mBackwardScrollable = z2;
        if (z3) {
            sendAccessibilityEvent(2048);
        }
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
            int iMin = Math.min(this.mLaunchAnimationParams.left - iArr[0], this.mRoundedRectClippingLeft);
            int iMax = Math.max(this.mLaunchAnimationParams.right - iArr[0], this.mRoundedRectClippingRight);
            int iMax2 = Math.max(this.mLaunchAnimationParams.bottom - iArr[1], this.mRoundedRectClippingBottom);
            Interpolator interpolator = Interpolators.FAST_OUT_SLOW_IN;
            LaunchAnimationParameters launchAnimationParameters = this.mLaunchAnimationParams;
            launchAnimationParameters.getClass();
            TransitionAnimator.Companion companion = TransitionAnimator.Companion;
            TransitionAnimator.Timings timings = ActivityTransitionAnimator.TIMINGS;
            float f = launchAnimationParameters.linearProgress;
            companion.getClass();
            int iMin2 = (int) Math.min(MathUtils.lerp(this.mRoundedRectClippingTop, this.mLaunchAnimationParams.top - iArr[1], ((PathInterpolator) interpolator).getInterpolation(TransitionAnimator.Companion.getProgress(timings, f, 0L, 100L))), this.mRoundedRectClippingTop);
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
            this.mLaunchedNotificationClipPath.addRoundRect(iMin, iMin2, iMax, iMax2, this.mLaunchedNotificationRadii, Path.Direction.CW);
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
            float fMax = Math.max(this.mMaxLayoutHeight - getContentHeight(), 0);
            float topPadding = getTopPadding();
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            float fMax2 = this.mMaxDisplayedNotifications != -1 ? this.mIntrinsicContentHeight : Math.max(0.0f, (height - fMax) - topPadding);
            this.mAmbientState.mStackEndHeight = fMax2;
            updateInterpolatedStackHeight(fMax2, f);
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
        float fAboutToShowBouncerProgress = ambientState.mExpansionFraction;
        if (z2) {
            fAboutToShowBouncerProgress = ambientState.mFractionToShade;
        }
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = ambientState.mStatusBarKeyguardViewManager;
        if (statusBarKeyguardViewManager != null && statusBarKeyguardViewManager.isPrimaryBouncerInTransit() && getQsExpansionFraction$1() > 0.0f) {
            fAboutToShowBouncerProgress = BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(fAboutToShowBouncerProgress);
        }
        if (!SecPanelSplitHelper.isEnabled() && (this.mAmbientState.mExpansionChanging || (onKeyguard() && this.mAmbientState.isNeedsToExpandLocksNoti()))) {
            f2 = navBarHeight - this.mYDiff;
        }
        float fLerp = MathUtils.lerp(f2, navBarHeight, fAboutToShowBouncerProgress);
        AmbientState ambientState2 = this.mAmbientState;
        ambientState2.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        ambientState2.mStackY = fLerp;
        QuickSettingsControllerImpl$$ExternalSyntheticLambda18 quickSettingsControllerImpl$$ExternalSyntheticLambda18 = this.mOnStackYChanged;
        if (quickSettingsControllerImpl$$ExternalSyntheticLambda18 != null) {
            quickSettingsControllerImpl$$ExternalSyntheticLambda18.accept(Boolean.valueOf(z));
        }
        updateStackEndHeightAndStackHeight(fAboutToShowBouncerProgress);
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
        float fMax = Math.max(0.0f, f);
        if (!z2) {
            float rubberBandFactor = fMax / getRubberBandFactor(z);
            if (z) {
                this.mOverScrolledTopPixels = rubberBandFactor;
            } else {
                this.mOverScrolledBottomPixels = rubberBandFactor;
            }
            AmbientState ambientState = this.mAmbientState;
            if (z) {
                ambientState.mOverScrollTopAmount = fMax;
            } else {
                ambientState.mOverScrollBottomAmount = fMax;
            }
            if (z) {
                notifyOverscrollTopListener(fMax, z4);
            }
            updateStackPosition(false);
            requestChildrenUpdate();
            return;
        }
        final StackStateAnimator stackStateAnimator2 = this.mStateAnimator;
        float currentOverScrollAmount = stackStateAnimator2.mHostLayout.getCurrentOverScrollAmount(z);
        if (fMax == currentOverScrollAmount) {
            return;
        }
        ValueAnimator valueAnimator2 = z ? stackStateAnimator2.mTopOverScrollAnimator : stackStateAnimator2.mBottomOverScrollAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.cancel();
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(currentOverScrollAmount, fMax);
        valueAnimatorOfFloat.setDuration(360L);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator.4
            public final /* synthetic */ boolean val$isRubberbanded;
            public final /* synthetic */ boolean val$onTop;

            public AnonymousClass4(final boolean z5, final boolean z42) {
                z = z5;
                z = z42;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                StackStateAnimator.this.mHostLayout.setOverScrollAmount(((Float) valueAnimator3.getAnimatedValue()).floatValue(), z, false, false, z);
            }
        });
        valueAnimatorOfFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator.5
            public final /* synthetic */ boolean val$onTop;

            public AnonymousClass5(final boolean z5) {
                z = z5;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (z) {
                    StackStateAnimator.this.mTopOverScrollAnimator = null;
                } else {
                    StackStateAnimator.this.mBottomOverScrollAnimator = null;
                }
            }
        });
        valueAnimatorOfFloat.start();
        if (z5) {
            stackStateAnimator2.mTopOverScrollAnimator = valueAnimatorOfFloat;
        } else {
            stackStateAnimator2.mBottomOverScrollAnimator = valueAnimatorOfFloat;
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
