package com.android.systemui.statusbar.notification.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.SemWallpaperColors;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.AttributeSet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.MathUtils;
import android.util.Pair;
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
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.policy.SystemBarUtils;
import com.android.keyguard.BouncerPanelExpansionCalculator;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.ExpandHelper;
import com.android.systemui.Flags;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.QuickPanelLogger;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.notification.FullExpansionPanelNotiAlphaController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.qs.animator.QsAnimatorState;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda7;
import com.android.systemui.shade.QuickSettingsControllerImpl;
import com.android.systemui.shade.QuickSettingsControllerImpl$$ExternalSyntheticLambda11;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shade.TouchLogger;
import com.android.systemui.statusbar.DndStatusView;
import com.android.systemui.statusbar.EmptyShadeView;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.LaunchAnimationParameters;
import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl;
import com.android.systemui.statusbar.notification.footer.shared.FooterViewRefactor;
import com.android.systemui.statusbar.notification.footer.ui.view.FooterView;
import com.android.systemui.statusbar.notification.logging.NotificationLogger$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;
import com.android.systemui.statusbar.notification.shared.NotificationsHeadsUpRefactor;
import com.android.systemui.statusbar.notification.shared.NotificationsLiveDataStoreRefactor;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationScrollView;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.HeadsUpTouchHelper;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.widget.SystemUIWidgetCallback;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceBuilderIterator;
import kotlin.sequences.SequencesKt__SequenceBuilderKt;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import kotlin.sequences.SequencesKt___SequencesKt;
import noticolorpicker.NotificationColorPicker;

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
    public final float[] mBgCornerRadii;
    public int mBottomPadding;
    public boolean mChangePositionInProgress;
    public boolean mCheckForLeavebehind;
    public boolean mChildTransferInProgress;
    public final ArrayList mChildrenChangingPositions;
    public final HashSet mChildrenToAddAnimated;
    public final ArrayList mChildrenToRemoveAnimated;
    public boolean mChildrenUpdateRequested;
    public final AnonymousClass1 mChildrenUpdater;
    public NotificationStackScrollLayoutController$$ExternalSyntheticLambda6 mClearAllAnimationListener;
    public Runnable mClearAllFinishedWhilePanelExpandedRunnable;
    public boolean mClearAllInProgress;
    public NotificationStackScrollLayoutController$$ExternalSyntheticLambda6 mClearAllListener;
    public final HashSet mClearTransientViewsWhenFinished;
    public final Rect mClipRect;
    public Runnable mCollapseShadeDelayedWhenNoViewsToAnimateAwayRunnable;
    public int mContentHeight;
    public boolean mContinuousShadowUpdate;
    public NotificationStackScrollLayoutController mController;
    public int mCornerRadius;
    public int mCurrentStackHeight;
    public boolean mDisallowDismissInThisMotion;
    public boolean mDisallowScrollingInThisMotion;
    public boolean mDismissUsingRowTranslationX;
    public final AnonymousClass5 mDisplayListener;
    public final DisplayManager mDisplayManager;
    public int mDisplayState;
    public DndStatusView mDndStatusView;
    public boolean mDontClampNextScroll;
    public boolean mDontReportNextOverScroll;
    public int mDownX;
    public EmptyShadeView mEmptyShadeView;
    public boolean mEverythingNeedsAnimation;
    public final ExpandHelper mExpandHelper;
    public final AnonymousClass15 mExpandHelperCallback;
    public ExpandableNotificationRow mExpandedGroupView;
    public float mExpandedHeight;
    public final ArrayList mExpandedHeightListeners;
    public boolean mExpandedInThisMotion;
    public boolean mExpandingNotification;
    public ExpandableNotificationRow mExpandingNotificationRow;
    public float mExtraTopInsetForFullShadeTransition;
    public Runnable mFinishScrollingCallback;
    public boolean mFlingAfterUpEvent;
    public NotificationStackScrollLayoutController$$ExternalSyntheticLambda6 mFooterClearAllListener;
    public FooterView mFooterView;
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
    public boolean mHasFilteredOutSeenNotifications;
    boolean mHeadsUpAnimatingAway;
    public final Consumer mHeadsUpAnimatingAwayListener;
    public HeadsUpAppearanceController mHeadsUpAppearanceController;
    public final AnonymousClass13 mHeadsUpCallback;
    public final HashSet mHeadsUpChangeAnimations;
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
    public boolean mIsCurrentUserSetup;
    public boolean mIsExpanded;
    public boolean mIsExpansionChanging;
    public boolean mIsInsetAnimationRunning;
    public boolean mIsRemoteInputActive;
    public boolean mIsVisibleFromGone;
    public boolean mKeyguardBypassEnabled;
    public final KeyguardFoldController mKeyguardFoldController;
    public String mLastGoneCallTrace;
    public String mLastInitViewDumpString;
    public long mLastInitViewElapsedRealtime;
    public int mLastMotionY;
    public float mLastSentAppear;
    public float mLastSentExpandedHeight;
    public String mLastUpdateSidePaddingDumpString;
    public long mLastUpdateSidePaddingElapsedRealtime;
    public LaunchAnimationParameters mLaunchAnimationParams;
    public final Path mLaunchedNotificationClipPath;
    public final float[] mLaunchedNotificationRadii;
    public boolean mLaunchingNotification;
    public boolean mLaunchingNotificationNeedsToBeClipped;
    public float mLinearHideAmount;
    public NotificationLogger$$ExternalSyntheticLambda2 mListener;
    public NotificationStackScrollLogger mLogger;
    public View.OnClickListener mManageButtonClickListener;
    public int mMaxDisplayedNotifications;
    public int mMaxLayoutHeight;
    public float mMaxOverScroll;
    public int mMaxScrollAfterExpand;
    public int mMaxTopPadding;
    public int mMaximumVelocity;
    public int mMinInteractionHeight;
    public float mMinTopOverScrollToEscape;
    public int mMinimumPaddings;
    public int mMinimumVelocity;
    public boolean mNeedViewResizeAnimation;
    public boolean mNeedsAnimation;
    public NotificationStackSizeCalculator mNotificationStackSizeCalculator;
    public final AnonymousClass7 mOnChildHeightChangedListener;
    public final AnonymousClass8 mOnChildSensitivityChangedListener;
    public NotificationPanelViewController$$ExternalSyntheticLambda7 mOnEmptySpaceClickListener;
    public ExpandableView.OnHeightChangedListener mOnHeightChangedListener;
    public final Runnable mOnHeightChangedRunnable;
    public Consumer mOnStackYChanged;
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
    public Runnable mResetUserExpandedStatesRunnable;
    public final Path mRoundedClipPath;
    public int mRoundedRectClippingBottom;
    public int mRoundedRectClippingLeft;
    public int mRoundedRectClippingRight;
    public int mRoundedRectClippingTop;
    public int mRoundedRectClippingYTranslation;
    public final AnonymousClass2 mRunningAnimationUpdater;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public final AnonymousClass9 mScrollAdapter;
    public Consumer mScrollChangedConsumer;
    public Consumer mScrollListener;
    public final ScrollViewFields mScrollViewFields;
    public boolean mScrollable;
    public boolean mScrolledToTopOnFirstDown;
    public OverScroller mScroller;
    public boolean mScrollingEnabled;
    public final NotificationSection[] mSections;
    public final NotificationSectionsManager mSectionsManager;
    public boolean mShadeNeedsToClose;
    public final NotificationStackScrollLayout$$ExternalSyntheticLambda2 mShadowUpdater;
    public NotificationShelf mShelf;
    public boolean mShelfAlphaInAnimating;
    public boolean mShelfAlphaOutAnimating;
    public NotificationShelfManager mShelfManager;
    public boolean mShouldShowShelfOnly;
    public boolean mShouldSkipTopPaddingAnimationAfterFold;
    public boolean mShouldUseRoundedRectClipping;
    public int mSidePaddings;
    public boolean mSkinnyNotifsInLandscape;
    public float mSlopMultiplier;
    public int mSpeedBumpIndex;
    public boolean mSpeedBumpIndexDirty;
    public SplitShadeStateController mSplitShadeStateController;
    public final ListenerSet mStackHeightChangedListeners;
    public final StackScrollAlgorithm mStackScrollAlgorithm;
    public final StackStateAnimator mStateAnimator;
    int mStatusBarHeight;
    public int mStatusBarState;
    public boolean mSuppressChildrenMeasureAndLayout;
    public NotificationSwipeHelper mSwipeHelper;
    public final ArrayList mSwipedOutViews;
    public final AnonymousClass16 mSystemUIWidgetCallback;
    public final int[] mTempInt2;
    public final ArrayList mTmpList;
    public final Rect mTmpRect;
    public final ArrayList mTmpSortedChildren;
    public ExpandableNotificationRow mTopHeadsUpRow;
    public boolean mTopPaddingNeedsAnimation;
    public float mTopPaddingOverflow;
    public NotificationStackScrollLayoutController.TouchHandler mTouchHandler;
    public boolean mTouchIsClick;
    public int mTouchSlop;
    public int mUpcomingStatusBarState;
    public VelocityTracker mVelocityTracker;
    public final NotificationStackScrollLayout$$ExternalSyntheticLambda3 mViewPositionComparator;
    public int mWaterfallTopInset;
    public boolean mWillExpand;
    public int mYDiff;

    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$13, reason: invalid class name */
    public final class AnonymousClass13 implements HeadsUpTouchHelper.Callback {
        public AnonymousClass13() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$15, reason: invalid class name */
    public final class AnonymousClass15 implements ExpandHelper.Callback {
        public AnonymousClass15() {
        }

        public final boolean canChildBeExpanded(View view) {
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                if (expandableNotificationRow.isExpandable$1() && !expandableNotificationRow.areGutsExposed() && (NotificationStackScrollLayout.this.mIsExpanded || !expandableNotificationRow.mIsPinned)) {
                    return true;
                }
            }
            return false;
        }

        public final void expansionStateChanged(boolean z) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
            notificationStackScrollLayout.mExpandingNotification = z;
            if (notificationStackScrollLayout.mExpandedInThisMotion) {
                return;
            }
            notificationStackScrollLayout.mMaxScrollAfterExpand = notificationStackScrollLayout.mOwnScrollY;
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

    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$9, reason: invalid class name */
    public final class AnonymousClass9 {
        public AnonymousClass9() {
        }
    }

    public final class AnimationEvent {
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
        Flags.sceneContainer();
        this.mAnimationsEnabled = false;
        this.mSpeedBumpIndex = -1;
        this.mSpeedBumpIndexDirty = true;
        this.mStackHeightChangedListeners = new ListenerSet();
        this.mHeadsUpHeightChangedListeners = new ListenerSet();
        this.mIsExpanded = true;
        this.mChildrenUpdater = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.1
            /* JADX WARN: Code restructure failed: missing block: B:251:0x046d, code lost:
            
                if (r11.mIsHeadsUpEntry != false) goto L238;
             */
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final boolean onPreDraw() {
                /*
                    Method dump skipped, instructions count: 2177
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.AnonymousClass1.onPreDraw():boolean");
            }
        };
        this.mTempInt2 = new int[2];
        this.mAnimationFinishedRunnables = new HashSet();
        this.mClearTransientViewsWhenFinished = new HashSet();
        this.mHeadsUpChangeAnimations = new HashSet();
        this.mTmpList = new ArrayList();
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
        new Callable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.4
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
        this.mClipRect = new Rect();
        this.mHeadsUpGoingAwayAnimationsAllowed = true;
        this.mReflingAndAnimateScroll = new NotificationStackScrollLayout$$ExternalSyntheticLambda4(this, 0);
        this.mBackgroundAnimationRect = new Rect();
        this.mExpandedHeightListeners = new ArrayList();
        this.mTmpRect = new Rect();
        this.mHideXInterpolator = Interpolators.FAST_OUT_SLOW_IN;
        this.mRoundedClipPath = new Path();
        this.mLaunchedNotificationClipPath = new Path();
        this.mShouldUseRoundedRectClipping = false;
        this.mBgCornerRadii = new float[8];
        this.mAnimateStackYForContentHeightChange = false;
        this.mLaunchedNotificationRadii = new float[8];
        this.mDismissUsingRowTranslationX = true;
        this.mShouldSkipTopPaddingAnimationAfterFold = false;
        this.mSplitShadeStateController = null;
        this.mOnChildHeightChangedListener = new ExpandableView.OnHeightChangedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.7
            @Override // com.android.systemui.statusbar.notification.row.ExpandableView.OnHeightChangedListener
            public final void onHeightChanged(ExpandableView expandableView, boolean z) {
                NotificationStackScrollLayout.this.onChildHeightChanged(expandableView, z);
            }

            @Override // com.android.systemui.statusbar.notification.row.ExpandableView.OnHeightChangedListener
            public final void onReset(ExpandableView expandableView) {
                NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                boolean z = (notificationStackScrollLayout.mAnimationsEnabled || notificationStackScrollLayout.mPulsing) && (notificationStackScrollLayout.mIsExpanded || NotificationStackScrollLayout.isPinnedHeadsUp(expandableView));
                boolean z2 = expandableView instanceof ExpandableNotificationRow;
                if (z2) {
                    ((ExpandableNotificationRow) expandableView).setAnimationRunning(z);
                }
                if (z2) {
                    ((ExpandableNotificationRow) expandableView).setChronometerRunning(notificationStackScrollLayout.mIsExpanded);
                }
                if (notificationStackScrollLayout.mTopHeadsUpRow == expandableView) {
                    Iterator it = notificationStackScrollLayout.mHeadsUpHeightChangedListeners.iterator();
                    while (it.hasNext()) {
                        ((Runnable) it.next()).run();
                    }
                }
            }
        };
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
        new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.12
            @Override // java.lang.Runnable
            public final void run() {
                NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                boolean z = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
                int scrollRange$1 = notificationStackScrollLayout.getScrollRange$1();
                NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayout.this;
                OverScroller overScroller = notificationStackScrollLayout2.mScroller;
                int i2 = ((ViewGroup) notificationStackScrollLayout2).mScrollX;
                int i3 = NotificationStackScrollLayout.this.mOwnScrollY;
                overScroller.startScroll(i2, i3, 0, scrollRange$1 - i3);
                NotificationStackScrollLayout notificationStackScrollLayout3 = NotificationStackScrollLayout.this;
                notificationStackScrollLayout3.mDontReportNextOverScroll = true;
                notificationStackScrollLayout3.mDontClampNextScroll = true;
                notificationStackScrollLayout3.animateScroll();
            }
        };
        this.mHeadsUpCallback = new AnonymousClass13();
        this.mExpandHelperCallback = new AnonymousClass15();
        this.mSystemUIWidgetCallback = new SystemUIWidgetCallback() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.16
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
        Resources resources = getResources();
        FeatureFlags featureFlags = (FeatureFlags) Dependency.sDependency.getDependencyInner(FeatureFlags.class);
        com.android.systemui.flags.Flags flags = com.android.systemui.flags.Flags.INSTANCE;
        featureFlags.getClass();
        NotificationSectionsManager notificationSectionsManager = (NotificationSectionsManager) Dependency.sDependency.getDependencyInner(NotificationSectionsManager.class);
        this.mSectionsManager = notificationSectionsManager;
        this.mScreenOffAnimationController = (ScreenOffAnimationController) Dependency.sDependency.getDependencyInner(ScreenOffAnimationController.class);
        if (!(!notificationSectionsManager.initialized)) {
            throw new IllegalStateException("NotificationSectionsManager already initialized".toString());
        }
        notificationSectionsManager.initialized = true;
        notificationSectionsManager.parent = this;
        notificationSectionsManager.reinflateViews();
        ((ConfigurationControllerImpl) notificationSectionsManager.configurationController).addCallback(notificationSectionsManager.configurationListener);
        NotificationSectionsFeatureManager notificationSectionsFeatureManager = notificationSectionsManager.sectionsFeatureManager;
        notificationSectionsFeatureManager.getClass();
        Flags.FEATURE_FLAGS.getClass();
        Flags.notificationMinimalismPrototype();
        notificationSectionsFeatureManager.isFilteringEnabled();
        notificationSectionsFeatureManager.isFilteringEnabled();
        int[] iArr = notificationSectionsFeatureManager.isFilteringEnabled() ? new int[]{4, 2, 5, 6, 7, 8, 9, 11, 12, 13} : new int[]{4, 2, 5, 6, 12, 13};
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int i2 : iArr) {
            arrayList.add(new NotificationSection(i2));
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
        this.mStackScrollAlgorithm = new StackScrollAlgorithm(context, this);
        this.mStateAnimator = new StackStateAnimator(context, this);
        setOutlineProvider(this.mOutlineProvider);
        Flags.sceneContainer();
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
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0052, code lost:
    
        if (r4.mEntry.mBucket == 13) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0055, code lost:
    
        r4 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x005b, code lost:
    
        if (r4.mEntry.mBucket < 13) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean includeChildInClearAll(com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r4, int r5) {
        /*
            boolean r0 = r4 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            r1 = 0
            if (r0 == 0) goto L19
            boolean r0 = r4.areGutsExposed()
            if (r0 != 0) goto L19
            com.android.systemui.statusbar.notification.collection.NotificationEntry r0 = r4.mEntry
            boolean r0 = r0.hasFinishedInitialization()
            if (r0 != 0) goto L14
            goto L19
        L14:
            boolean r0 = r4.canViewBeCleared()
            goto L1a
        L19:
            r0 = r1
        L1a:
            if (r0 == 0) goto L61
            r0 = 1
            if (r5 == 0) goto L5d
            r2 = 13
            if (r5 == r0) goto L57
            r3 = 2
            if (r5 == r3) goto L4e
            r2 = 3
            if (r5 != r2) goto L42
            com.android.systemui.noticenter.NotiCenterPlugin r5 = com.android.systemui.noticenter.NotiCenterPlugin.INSTANCE
            com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r4.mEntry
            android.service.notification.StatusBarNotification r4 = r4.mSbn
            java.lang.String r4 = r4.getPackageName()
            r5.getClass()
            java.util.HashSet r5 = com.android.systemui.noticenter.NotiCenterPlugin.noclearAppList
            if (r5 == 0) goto L3f
            boolean r4 = r5.contains(r4)
            goto L40
        L3f:
            r4 = r1
        L40:
            r4 = r4 ^ r0
            goto L5e
        L42:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "Unknown selection: "
            java.lang.String r5 = android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(r5, r0)
            r4.<init>(r5)
            throw r4
        L4e:
            com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r4.mEntry
            int r4 = r4.mBucket
            if (r4 != r2) goto L55
            goto L5d
        L55:
            r4 = r1
            goto L5e
        L57:
            com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r4.mEntry
            int r4 = r4.mBucket
            if (r4 >= r2) goto L55
        L5d:
            r4 = r0
        L5e:
            if (r4 == 0) goto L61
            r1 = r0
        L61:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.includeChildInClearAll(com.android.systemui.statusbar.notification.row.ExpandableNotificationRow, int):boolean");
    }

    public static boolean isPinnedHeadsUp(View view) {
        if (!(view instanceof ExpandableNotificationRow)) {
            return false;
        }
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
        return expandableNotificationRow.mIsHeadsUp && expandableNotificationRow.mIsPinned;
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

    public final void addTransientView(View view, int i) {
        NotificationStackScrollLogger notificationStackScrollLogger = this.mLogger;
        if (notificationStackScrollLogger != null && (view instanceof ExpandableNotificationRow)) {
            final NotificationEntry notificationEntry = ((ExpandableNotificationRow) view).mEntry;
            notificationStackScrollLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$addTransientRow$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    String str1 = logMessage.getStr1();
                    int int1 = logMessage.getInt1();
                    ExpandableNotificationRow expandableNotificationRow = NotificationEntry.this.row;
                    StringBuilder m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(int1, "addTransientRow to NSSL: childKey: ", str1, " -- index: ", " -- view : ");
                    m.append(expandableNotificationRow);
                    return m.toString();
                }
            };
            LogBuffer logBuffer = notificationStackScrollLogger.notificationRenderBuffer;
            LogMessage obtain = logBuffer.obtain("NotificationStackScroll", logLevel, function1, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logMessageImpl.int1 = i;
            logBuffer.commit(obtain);
        }
        super.addTransientView(view, i);
    }

    public final void animateScroll() {
        if (!this.mScroller.computeScrollOffset()) {
            this.mDontClampNextScroll = false;
            Runnable runnable = this.mFinishScrollingCallback;
            if (runnable != null) {
                runnable.run();
                return;
            }
            return;
        }
        int i = this.mOwnScrollY;
        int currY = this.mScroller.getCurrY();
        if (i != currY) {
            int scrollRange$1 = getScrollRange$1();
            if ((currY < 0 && i >= 0) || (currY > scrollRange$1 && i <= scrollRange$1)) {
                float currVelocity = this.mScroller.getCurrVelocity();
                if (currVelocity >= this.mMinimumVelocity) {
                    this.mMaxOverScroll = (Math.abs(currVelocity) / 1000.0f) * this.mOverflingDistance;
                }
            }
            if (this.mDontClampNextScroll) {
                scrollRange$1 = Math.max(scrollRange$1, i);
            }
            customOverScrollBy(currY - i, i, scrollRange$1, (int) this.mMaxOverScroll);
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
            Flags.notificationsLiveDataStoreRefactor();
            NotificationLogger$$ExternalSyntheticLambda2 notificationLogger$$ExternalSyntheticLambda2 = this.mListener;
            if (notificationLogger$$ExternalSyntheticLambda2 != null) {
                notificationLogger$$ExternalSyntheticLambda2.f$0.onChildLocationsChanged();
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
        int i = FooterViewRefactor.$r8$clinit;
        Flags.notificationsFooterViewRefactor();
        float appearEndPositionLegacy = getAppearEndPositionLegacy();
        float appearStartPosition = getAppearStartPosition();
        return MathUtils.constrain((f - appearStartPosition) / (appearEndPositionLegacy - appearStartPosition), -1.0f, 1.0f);
    }

    public final float calculateGapHeight(ExpandableView expandableView, ExpandableView expandableView2, int i) {
        if (onKeyguard() && !this.mAmbientState.isNeedsToExpandLocksNoti()) {
            return 0.0f;
        }
        StackScrollAlgorithm stackScrollAlgorithm = this.mStackScrollAlgorithm;
        NotificationSectionsManager notificationSectionsManager = this.mSectionsManager;
        AmbientState ambientState = this.mAmbientState;
        float f = ambientState.mFractionToShade;
        boolean isOnKeyguard = ambientState.isOnKeyguard();
        stackScrollAlgorithm.getClass();
        if ((expandableView instanceof ExpandableView) && StackScrollAlgorithm.getPreviousGroupExpandFraction(expandableView) > 0.0f) {
            return NotificationUtils.interpolate(0.0f, stackScrollAlgorithm.mMaxGroupExpandedBottomGap, StackScrollAlgorithm.getPreviousGroupExpandFraction(expandableView));
        }
        if (stackScrollAlgorithm.childNeedsGapHeight(notificationSectionsManager, expandableView2, expandableView)) {
            return stackScrollAlgorithm.getGapForLocation(f, isOnKeyguard);
        }
        return 0.0f;
    }

    public final void cancelExpandHelper() {
        ExpandHelper expandHelper = this.mExpandHelper;
        expandHelper.finishExpanding(0.0f, true, true);
        expandHelper.mResizedView = null;
        expandHelper.mSGD = new ScaleGestureDetector(expandHelper.mContext, expandHelper.mScaleGestureListener);
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
        int scrollRange$1 = getScrollRange$1();
        if (scrollRange$1 >= this.mOwnScrollY || this.mAmbientState.mClearAllInProgress) {
            return;
        }
        setOwnScrollY(scrollRange$1, scrollRange$1 < getScrollAmountToScrollBoundary() && this.mAnimateStackYForContentHeightChange);
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
                    Iterator it = expandableNotificationRow.getAttachedChildren().iterator();
                    while (it.hasNext()) {
                        ((ExpandableNotificationRow) it.next()).setHeadsUpAnimatingAway(false);
                    }
                }
            }
        }
    }

    public final void clearNotifications(int i, boolean z) {
        FooterViewRefactor.assertInLegacyMode();
        this.mController.hasNotifications(2, false);
        clearNotifications$1(i, z);
    }

    public final void clearNotifications$1(final int i, boolean z) {
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
                    for (ExpandableNotificationRow expandableNotificationRow2 : expandableNotificationRow.getAttachedChildren()) {
                        if (isVisible(expandableNotificationRow2) && includeChildInClearAll(expandableNotificationRow2, i)) {
                            arrayList.add(expandableNotificationRow2);
                        }
                    }
                }
            }
        }
        int childCount2 = getChildCount();
        final ArrayList arrayList2 = new ArrayList(childCount2);
        for (int i3 = 0; i3 < childCount2; i3++) {
            View childAt2 = getChildAt(i3);
            if (childAt2 instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) childAt2;
                if (includeChildInClearAll(expandableNotificationRow3, i)) {
                    arrayList2.add(expandableNotificationRow3);
                }
                List<ExpandableNotificationRow> attachedChildren2 = expandableNotificationRow3.getAttachedChildren();
                if (isVisible(expandableNotificationRow3) && attachedChildren2 != null) {
                    for (ExpandableNotificationRow expandableNotificationRow4 : attachedChildren2) {
                        if (includeChildInClearAll(expandableNotificationRow3, i)) {
                            arrayList2.add(expandableNotificationRow4);
                        }
                    }
                }
            }
        }
        NotificationStackScrollLayoutController$$ExternalSyntheticLambda6 notificationStackScrollLayoutController$$ExternalSyntheticLambda6 = this.mClearAllListener;
        if (notificationStackScrollLayoutController$$ExternalSyntheticLambda6 != null) {
            notificationStackScrollLayoutController$$ExternalSyntheticLambda6.f$0.mUiEventLogger.log(i == 0 ? NotificationStackScrollLayoutController.NotificationPanelEvent.DISMISS_ALL_NOTIFICATIONS_PANEL : i == 2 ? NotificationStackScrollLayoutController.NotificationPanelEvent.DISMISS_SILENT_NOTIFICATIONS_PANEL : NotificationStackScrollLayoutController.NotificationPanelEvent.INVALID);
        }
        Consumer consumer = new Consumer() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                final NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                final ArrayList arrayList3 = arrayList2;
                final int i4 = i;
                boolean z4 = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
                notificationStackScrollLayout.getClass();
                if (((Boolean) obj).booleanValue()) {
                    notificationStackScrollLayout.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda15
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayout.this;
                            ArrayList arrayList4 = arrayList3;
                            int i5 = i4;
                            boolean z5 = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
                            notificationStackScrollLayout2.onClearAllAnimationsEnd(i5, arrayList4);
                        }
                    });
                } else {
                    notificationStackScrollLayout.onClearAllAnimationsEnd(i4, arrayList3);
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
        int size = arrayList.size();
        int i4 = size - 1;
        int i5 = 60;
        int i6 = 0;
        while (i4 >= 0) {
            View view = (View) arrayList.get(i4);
            NotificationStackScrollLayout$$ExternalSyntheticLambda10 notificationStackScrollLayout$$ExternalSyntheticLambda10 = i4 == 0 ? consumer : 0;
            if (view instanceof SectionHeaderView) {
                ((StackScrollerDecorView) view).setContentVisible(notificationStackScrollLayout$$ExternalSyntheticLambda10, z2, z4);
            } else {
                this.mSwipeHelper.dismissChild(view, 0.0f, notificationStackScrollLayout$$ExternalSyntheticLambda10, i6, true, 200L, true);
            }
            i5 = Math.max(30, i5 - 5);
            i6 += i5;
            i4--;
            z2 = false;
            z4 = true;
        }
        SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_OPENED, SystemUIAnalytics.EID_QPNE_CLEAR_NOTIFICATION, "type", i == 0 ? SystemUIAnalytics.QPNE_VID_COVER_ALL : SystemUIAnalytics.QPNE_VID_SILENT, SystemUIAnalytics.QPNE_KEY_COUNT, Integer.toString(size));
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
                        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
                        LogLevel logLevel = LogLevel.INFO;
                        NotificationStackScrollLogger$transientNotificationRowTraversalCleaned$2 notificationStackScrollLogger$transientNotificationRowTraversalCleaned$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$transientNotificationRowTraversalCleaned$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                LogMessage logMessage = (LogMessage) obj;
                                return FontProvider$$ExternalSyntheticOutline0.m("transientNotificationRowTraversalCleaned: key: ", logMessage.getStr1(), " reason: ", logMessage.getStr2());
                            }
                        };
                        LogBuffer logBuffer = notificationStackScrollLogger.notificationRenderBuffer;
                        LogMessage obtain = logBuffer.obtain("NotificationStackScroll", logLevel, notificationStackScrollLogger$transientNotificationRowTraversalCleaned$2, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
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
            if (this.mOwnScrollY < 0) {
                notifyOverscrollTopListener(-r4, isRubberbanded(true));
                return;
            } else {
                notifyOverscrollTopListener(currentOverScrollAmount, isRubberbanded(true));
                return;
            }
        }
        int scrollRange$1 = getScrollRange$1();
        int i8 = this.mOwnScrollY;
        boolean z3 = i8 <= 0;
        boolean z4 = i8 >= scrollRange$1;
        if (z3 || z4) {
            if (z3) {
                f = -i8;
                setOwnScrollY(0);
                this.mDontReportNextOverScroll = true;
                z2 = true;
            } else {
                setOwnScrollY(scrollRange$1);
                f = i8 - scrollRange$1;
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
        if (this.mShouldUseRoundedRectClipping && !this.mLaunchingNotification) {
            canvas.clipPath(this.mRoundedClipPath);
        }
        super.dispatchDraw(canvas);
    }

    /* JADX WARN: Code restructure failed: missing block: B:111:0x0142, code lost:
    
        r9 = scrollAmountForKeyboardFocus(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0146, code lost:
    
        if (r9 == 0) goto L113;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x014f, code lost:
    
        if ((r8.mOwnScrollY + r9) <= getScrollRange$1()) goto L108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x0151, code lost:
    
        r8.mOwnScrollY = getScrollRange$1();
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
    
        if ((r8.mOwnScrollY + r9) <= getScrollRange$1()) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x00fa, code lost:
    
        r8.mOwnScrollY = getScrollRange$1();
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
        Flags.sceneContainer();
        boolean dispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        TouchLogger.Companion.getClass();
        TouchLogger.Companion.logDispatchTouch(motionEvent, "StackScroller", dispatchTouchEvent);
        return dispatchTouchEvent;
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j) {
        if (!this.mShouldUseRoundedRectClipping || !this.mLaunchingNotification) {
            return super.drawChild(canvas, view, j);
        }
        canvas.save();
        ExpandableView expandableView = (ExpandableView) view;
        Path path = (expandableView.isExpandAnimationRunning() || expandableView.hasExpandingChild()) ? null : this.mRoundedClipPath;
        if (path != null) {
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
                DumpUtilsKt.println(indentingPrintWriter, "qsClipping", Boolean.valueOf(notificationStackScrollLayout.mShouldUseRoundedRectClipping));
                DumpUtilsKt.println(indentingPrintWriter, "qsClipDismiss", Boolean.valueOf(notificationStackScrollLayout.mDismissUsingRowTranslationX));
                DumpUtilsKt.println(indentingPrintWriter, "visibility", DumpUtilsKt.visibilityString(notificationStackScrollLayout.getVisibility()));
                DumpUtilsKt.println(indentingPrintWriter, "alpha", Float.valueOf(notificationStackScrollLayout.getAlpha()));
                DumpUtilsKt.println(indentingPrintWriter, "suppressChildrenMeasureLayout", Boolean.valueOf(notificationStackScrollLayout.mSuppressChildrenMeasureAndLayout));
                DumpUtilsKt.println(indentingPrintWriter, "scrollY", Integer.valueOf(notificationStackScrollLayout.mAmbientState.mScrollY));
                DumpUtilsKt.println(indentingPrintWriter, "maxTopPadding", Integer.valueOf(notificationStackScrollLayout.mMaxTopPadding));
                DumpUtilsKt.println(indentingPrintWriter, "showShelfOnly", Boolean.valueOf(notificationStackScrollLayout.mShouldShowShelfOnly));
                DumpUtilsKt.println(indentingPrintWriter, "qsExpandFraction", Float.valueOf(notificationStackScrollLayout.mQsExpansionFraction));
                DumpUtilsKt.println(indentingPrintWriter, "isCurrentUserSetup", Boolean.valueOf(notificationStackScrollLayout.mIsCurrentUserSetup));
                DumpUtilsKt.println(indentingPrintWriter, "hideAmount", Float.valueOf(notificationStackScrollLayout.mAmbientState.mHideAmount));
                DumpUtilsKt.println(indentingPrintWriter, "ambientStateSwipingUp", Boolean.valueOf(notificationStackScrollLayout.mAmbientState.mIsSwipingUp));
                DumpUtilsKt.println(indentingPrintWriter, "maxDisplayedNotifications", Integer.valueOf(notificationStackScrollLayout.mMaxDisplayedNotifications));
                DumpUtilsKt.println(indentingPrintWriter, "intrinsicContentHeight", Float.valueOf(notificationStackScrollLayout.mIntrinsicContentHeight));
                DumpUtilsKt.println(indentingPrintWriter, "contentHeight", Integer.valueOf(notificationStackScrollLayout.mContentHeight));
                DumpUtilsKt.println(indentingPrintWriter, "intrinsicPadding", Integer.valueOf(notificationStackScrollLayout.mIntrinsicPadding));
                DumpUtilsKt.println(indentingPrintWriter, "topPadding", Integer.valueOf(notificationStackScrollLayout.mAmbientState.mTopPadding));
                DumpUtilsKt.println(indentingPrintWriter, "bottomPadding", Integer.valueOf(notificationStackScrollLayout.mBottomPadding));
                indentingPrintWriter.append("roundedRectClipping{l=").print(notificationStackScrollLayout.mRoundedRectClippingLeft);
                indentingPrintWriter.append(" t=").print(notificationStackScrollLayout.mRoundedRectClippingTop);
                indentingPrintWriter.append(" r=").print(notificationStackScrollLayout.mRoundedRectClippingRight);
                indentingPrintWriter.append(" b=").print(notificationStackScrollLayout.mRoundedRectClippingBottom);
                indentingPrintWriter.append(" +y=").print(notificationStackScrollLayout.mRoundedRectClippingYTranslation);
                indentingPrintWriter.append("} topRadius=").print(notificationStackScrollLayout.mBgCornerRadii[0]);
                indentingPrintWriter.append(" bottomRadius=").println(notificationStackScrollLayout.mBgCornerRadii[4]);
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
                Boolean bool = Boolean.FALSE;
                DumpUtilsKt.println(indentingPrintWriter, "shouldUseSplitNotificationShade", bool);
                DumpUtilsKt.println(indentingPrintWriter, "lastUpdateSidePadding", notificationStackScrollLayout.mLastUpdateSidePaddingDumpString);
                DumpUtilsKt.println(indentingPrintWriter, "lastUpdateSidePaddingElapsedRealtime", Long.valueOf(notificationStackScrollLayout.mLastUpdateSidePaddingElapsedRealtime));
                DumpUtilsKt.println(indentingPrintWriter, "lastUpdateSidePaddingMillisAgo", Long.valueOf(j - notificationStackScrollLayout.mLastUpdateSidePaddingElapsedRealtime));
                DumpUtilsKt.println(indentingPrintWriter, "isSmallLandscapeLockscreenEnabled", bool);
                indentingPrintWriter.println("NotificationStackSizeCalculator saveSpaceOnLockscreen=" + notificationStackScrollLayout.mNotificationStackSizeCalculator.saveSpaceOnLockscreen);
                ScrollViewFields scrollViewFields = notificationStackScrollLayout.mScrollViewFields;
                scrollViewFields.getClass();
                indentingPrintWriter.append("StackViewStates").println(":");
                indentingPrintWriter.increaseIndent();
                try {
                    DumpUtilsKt.println(indentingPrintWriter, "scrimClippingShape", null);
                    DumpUtilsKt.println(indentingPrintWriter, "stackTop", Float.valueOf(scrollViewFields.stackTop));
                    DumpUtilsKt.println(indentingPrintWriter, "stackBottom", Float.valueOf(scrollViewFields.stackBottom));
                    DumpUtilsKt.println(indentingPrintWriter, "headsUpTop", Float.valueOf(scrollViewFields.headsUpTop));
                    DumpUtilsKt.println(indentingPrintWriter, "isScrolledToTop", Boolean.valueOf(scrollViewFields.isScrolledToTop));
                } finally {
                    indentingPrintWriter.decreaseIndent();
                }
            }
        });
        asIndenting.println();
        asIndenting.println("Contents:");
        DumpUtilsKt.withIncreasedIndent(asIndenting, new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                int i = 0;
                NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                PrintWriter printWriter2 = asIndenting;
                String[] strArr2 = strArr;
                boolean z = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
                int childCount = notificationStackScrollLayout.getChildCount();
                printWriter2.println("Number of children: " + childCount);
                printWriter2.println();
                for (int i2 = 0; i2 < childCount; i2++) {
                    ExpandableView expandableView = (ExpandableView) notificationStackScrollLayout.getChildAt(i2);
                    expandableView.dump(printWriter2, strArr2);
                    int i3 = FooterViewRefactor.$r8$clinit;
                    Flags.notificationsFooterViewRefactor();
                    if (expandableView instanceof FooterView) {
                        DumpUtilsKt.withIncreasedIndent((IndentingPrintWriter) printWriter2, (Runnable) new NotificationStackScrollLayout$$ExternalSyntheticLambda8(notificationStackScrollLayout, printWriter2, i));
                    }
                    printWriter2.println();
                }
                int transientViewCount = notificationStackScrollLayout.getTransientViewCount();
                printWriter2.println("Transient Views: " + transientViewCount);
                while (i < transientViewCount) {
                    ((ExpandableView) notificationStackScrollLayout.getTransientView(i)).dump(printWriter2, strArr2);
                    i++;
                }
                NotificationSwipeHelper notificationSwipeHelper = notificationStackScrollLayout.mSwipeHelper;
                ExpandableView expandableView2 = notificationSwipeHelper.mIsSwiping ? notificationSwipeHelper.mTouchedView : null;
                printWriter2.println("Swiped view: " + expandableView2);
                if (expandableView2 instanceof ExpandableView) {
                    expandableView2.dump(printWriter2, strArr2);
                }
            }
        });
    }

    public final void endDrag$1() {
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
                    this.mClearAllFinishedWhilePanelExpandedRunnable.run();
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
        FooterViewRefactor.isUnexpectedlyInLegacyMode();
        PanelScreenShotLogger.addLogItem(arrayList, "getAppearEndPosition", Float.valueOf(getAppearEndPositionLegacy()));
        PanelScreenShotLogger.addLogItem(arrayList, "mExtraTopInsetForFullShadeTransition", Float.valueOf(this.mExtraTopInsetForFullShadeTransition));
        PanelScreenShotLogger.addLogItem(arrayList, "mIntrinsicPadding", Integer.valueOf(this.mIntrinsicPadding));
        PanelScreenShotLogger.addLogItem(arrayList, "mShouldShowShelfOnly", Boolean.valueOf(this.mShouldShowShelfOnly));
        PanelScreenShotLogger.addLogItem(arrayList, "getVisibility", Integer.valueOf(getVisibility()));
        String str = this.mLastGoneCallTrace;
        if (str == null) {
            str = "NULL";
        }
        PanelScreenShotLogger.addLogItem(arrayList, "mLastGoneCallTrace", str);
        PanelScreenShotLogger.addLogItem(arrayList, "appIconColor", Integer.toHexString(getContext().getColor(R.color.notification_app_icon_color)));
        arrayList.add("\n\n");
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) childAt;
                ExpandableViewState expandableViewState = expandableNotificationRow.mViewState;
                PanelScreenShotLogger panelScreenShotLogger = PanelScreenShotLogger.INSTANCE;
                String str2 = expandableNotificationRow.mLoggingKey;
                panelScreenShotLogger.getClass();
                PanelScreenShotLogger.addLogItem(arrayList, "key", str2);
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

    public final void generateHeadsUpAnimation(ExpandableNotificationRow expandableNotificationRow, boolean z) {
        if (this.mAnimationsEnabled) {
            if (z || this.mHeadsUpGoingAwayAnimationsAllowed) {
                if (!z && this.mHeadsUpChangeAnimations.remove(new Pair(expandableNotificationRow, Boolean.TRUE))) {
                    logHunAnimationSkipped(expandableNotificationRow, "previous hun appear animation cancelled");
                    return;
                }
                this.mHeadsUpChangeAnimations.add(new Pair(expandableNotificationRow, Boolean.valueOf(z)));
                this.mNeedsAnimation = true;
                if (!this.mIsExpanded && !this.mWillExpand && !z) {
                    expandableNotificationRow.setHeadsUpAnimatingAway(true);
                    NotificationsHeadsUpRefactor notificationsHeadsUpRefactor = NotificationsHeadsUpRefactor.INSTANCE;
                    Flags.notificationsHeadsUpRefactor();
                }
                requestChildrenUpdate();
            }
        }
    }

    public final float getAppearEndPositionLegacy() {
        FooterViewRefactor.assertInLegacyMode();
        int i = this.mAmbientState.mStackTopMargin;
        int visibleNotificationCount = this.mController.getVisibleNotificationCount();
        boolean z = NotiRune.NOTI_STYLE_EMPTY_SHADE;
        if (((z || this.mEmptyShadeView != null) && this.mEmptyShadeView.getVisibility() != 8) || visibleNotificationCount <= 0) {
            if (z) {
                i = this.mEmptyShadeView.getHeight();
            }
        } else if (isHeadsUpTransition() || (this.mInHeadsUpPinnedMode && !this.mAmbientState.mDozing)) {
            if (this.mShelf.getVisibility() != 8 && visibleNotificationCount > 1) {
                i += this.mShelf.getHeight() + this.mPaddingBetweenElements;
            }
            i += getPositionInLinearLayout(this.mAmbientState.getTrackedHeadsUpRow()) + getTopHeadsUpPinnedHeight();
        } else if (this.mShelf.getVisibility() != 8) {
            i += this.mShelf.getHeight();
        }
        return i + (onKeyguard() ? this.mAmbientState.mTopPadding : this.mIntrinsicPadding);
    }

    public final float getAppearStartPosition() {
        if (!isHeadsUpTransition()) {
            return this.mShelf.getHeight() + this.mWaterfallTopInset;
        }
        return (this.mHeadsUpInset - this.mAmbientState.mStackTopMargin) + (getFirstVisibleSection() != null ? r0.mFirstVisibleChild.getPinnedHeadsUpHeight() : 0);
    }

    public final ExpandableView getChildAtPosition(float f, float f2, boolean z, boolean z2) {
        ExpandableNotificationRow expandableNotificationRow;
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
                    ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) expandableView;
                    NotificationEntry notificationEntry = expandableNotificationRow2.mEntry;
                    if (!this.mIsExpanded && expandableNotificationRow2.mIsHeadsUp && expandableNotificationRow2.mIsPinned && (expandableNotificationRow = this.mTopHeadsUpRow) != expandableNotificationRow2) {
                        if (((GroupMembershipManagerImpl) this.mGroupMembershipManager).getGroupSummary(expandableNotificationRow.mEntry) != notificationEntry) {
                        }
                    }
                    return expandableNotificationRow2.getViewAtPosition(f2 - translationY);
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

    public final int getEmptyBottomMargin() {
        return Math.max(this.mMaxLayoutHeight - this.mContentHeight, 0);
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

    public final ExpandableView getFirstChildNotGone() {
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

    public final int getLayoutMinHeight() {
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

    public final SecPanelSplitHelper getPanelSplitHelper() {
        if (this.mPanelSplitHelper == null) {
            this.mPanelSplitHelper = (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
        }
        return this.mPanelSplitHelper;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getPositionInLinearLayout(android.view.View r15) {
        /*
            Method dump skipped, instructions count: 201
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.getPositionInLinearLayout(android.view.View):int");
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
        if (!this.mScrolledToTopOnFirstDown || (SecPanelSplitHelper.isEnabled() && getPanelSplitHelper().isShadeState())) {
            return RUBBER_BAND_FACTOR_NORMAL;
        }
        return 1.0f;
    }

    public final int getScrollAmountToScrollBoundary() {
        return this.mAmbientState.mTopPadding - ((ShadeHeaderController) Dependency.sDependency.getDependencyInner(ShadeHeaderController.class)).header.getHeight();
    }

    public final int getScrollRange$1() {
        int i = this.mContentHeight;
        if (!this.mIsExpanded && this.mInHeadsUpPinnedMode) {
            i = this.mHeadsUpInset + getTopHeadsUpPinnedHeight();
        }
        int max = Math.max(0, i - this.mMaxLayoutHeight);
        int max2 = Math.max(0, this.mImeInset - ((getRootView().getHeight() - getHeight()) - getLocationOnScreen()[1]));
        int min = Math.min(max2, Math.max(0, i - (getHeight() - max2))) + max;
        return min > 0 ? Math.max(getScrollAmountToScrollBoundary(), min) : min;
    }

    public final int getSpeedBumpIndex$1() {
        if (this.mSpeedBumpIndexDirty) {
            this.mSpeedBumpIndexDirty = false;
            int childCount = getChildCount();
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (childAt.getVisibility() != 8 && (childAt instanceof ExpandableNotificationRow)) {
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) childAt;
                    i2++;
                    boolean z = true;
                    if (!this.mHighPriorityBeforeSpeedBump) {
                        z = true ^ expandableNotificationRow.mEntry.mRanking.isAmbient();
                    } else if (expandableNotificationRow.mEntry.mBucket >= 13) {
                        z = false;
                    }
                    if (z) {
                        i = i2;
                    }
                }
            }
            this.mSpeedBumpIndex = i;
        }
        return this.mSpeedBumpIndex;
    }

    public final int getTopHeadsUpPinnedHeight() {
        ExpandableNotificationRow expandableNotificationRow = this.mTopHeadsUpRow;
        if (expandableNotificationRow == null) {
            return 0;
        }
        if (expandableNotificationRow.isChildInGroup()) {
            NotificationEntry groupSummary = ((GroupMembershipManagerImpl) this.mGroupMembershipManager).getGroupSummary(expandableNotificationRow.mEntry);
            if (groupSummary != null) {
                expandableNotificationRow = groupSummary.row;
            }
        }
        return expandableNotificationRow.getPinnedHeadsUpHeight(true);
    }

    public final float getTouchSlop$2(MotionEvent motionEvent) {
        return motionEvent.getClassification() == 1 ? this.mTouchSlop * this.mSlopMultiplier : this.mTouchSlop;
    }

    public final void handleEmptySpaceClick(MotionEvent motionEvent) {
        boolean isBelowLastNotification = isBelowLastNotification(this.mInitialTouchX, this.mInitialTouchY);
        int i = this.mStatusBarState;
        boolean z = this.mTouchIsClick;
        NotificationStackScrollLogger notificationStackScrollLogger = this.mLogger;
        if (notificationStackScrollLogger != null) {
            String actionToString = MotionEvent.actionToString(motionEvent.getActionMasked());
            LogLevel logLevel = LogLevel.DEBUG;
            NotificationStackScrollLogger$logEmptySpaceClick$2 notificationStackScrollLogger$logEmptySpaceClick$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$logEmptySpaceClick$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    int int1 = logMessage.getInt1();
                    boolean bool1 = logMessage.getBool1();
                    boolean bool2 = logMessage.getBool2();
                    String str1 = logMessage.getStr1();
                    StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("handleEmptySpaceClick: statusBarState: ", int1, " isTouchAClick: ", bool1, " isTouchBelowNotification: ");
                    m.append(bool2);
                    m.append(" motionEvent: ");
                    m.append(str1);
                    return m.toString();
                }
            };
            LogBuffer logBuffer = notificationStackScrollLogger.shadeLogBuffer;
            LogMessage obtain = logBuffer.obtain("NotificationStackScroll", logLevel, notificationStackScrollLogger$logEmptySpaceClick$2, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.int1 = i;
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
                notificationStackScrollLogger2.shadeLogBuffer.log("NotificationStackScroll", LogLevel.DEBUG, "handleEmptySpaceClick: MotionEvent ignored", null);
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
        if (this.mStateAnimator.isRunning()) {
            Log.d("StackScroller", "onEmptySpaceClicked is ignored by notification Animating..");
            return;
        }
        if (this.mStatusBarState != 1 && this.mTouchIsClick && isBelowLastNotification(this.mInitialTouchX, this.mInitialTouchY)) {
            NotificationStackScrollLogger notificationStackScrollLogger3 = this.mLogger;
            if (notificationStackScrollLogger3 != null) {
                notificationStackScrollLogger3.shadeLogBuffer.log("NotificationStackScroll", LogLevel.DEBUG, "handleEmptySpaceClick: touch event propagated further", null);
            }
            NotificationPanelViewController$$ExternalSyntheticLambda7 notificationPanelViewController$$ExternalSyntheticLambda7 = this.mOnEmptySpaceClickListener;
            notificationPanelViewController$$ExternalSyntheticLambda7.f$0.onEmptySpaceClick(this.mInitialTouchX, this.mInitialTouchY);
        }
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return !this.mForceNoOverlappingRendering && super.hasOverlappingRendering();
    }

    public final void inflateDndView() {
        int i;
        DndStatusView dndStatusView = this.mDndStatusView;
        DndStatusView dndStatusView2 = (DndStatusView) LayoutInflater.from(((ViewGroup) this).mContext).inflate(R.layout.status_bar_notification_dnd_status, (ViewGroup) this, false);
        View findViewById = dndStatusView2.findViewById(R.id.notification_dnd_status_text_icon_container);
        if (findViewById != null) {
            findViewById.setOnClickListener(new NotificationStackScrollLayout$$ExternalSyntheticLambda1(this, 0));
        }
        View view = this.mDndStatusView;
        if (view != null) {
            i = indexOfChild(view);
            removeView(this.mDndStatusView);
        } else {
            i = 0;
        }
        this.mDndStatusView = dndStatusView2;
        addView(dndStatusView2, i);
        dndStatusView2.setVisible(dndStatusView != null && dndStatusView.mIsVisible, false);
    }

    public final void inflateEmptyShadeView() {
        int i;
        if (NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW) {
            return;
        }
        EmptyShadeView emptyShadeView = this.mEmptyShadeView;
        EmptyShadeView emptyShadeView2 = (EmptyShadeView) LayoutInflater.from(((ViewGroup) this).mContext).inflate(R.layout.status_bar_no_notifications, (ViewGroup) this, false);
        if (!NotiRune.NOTI_STYLE_EMPTY_SHADE) {
            emptyShadeView2.setOnClickListener(new NotificationStackScrollLayout$$ExternalSyntheticLambda1(this, 2));
        }
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
        updateEmptyShadeViewResources(emptyShadeView == null ? R.string.empty_shade_text : emptyShadeView.mText, emptyShadeView == null ? 0 : emptyShadeView.mFooterText, emptyShadeView != null ? emptyShadeView.mFooterIcon : 0);
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
        boolean z = resources.getBoolean(R.bool.is_small_screen_landscape);
        boolean z2 = resources.getBoolean(R.bool.config_skinnyNotifsInLandscape);
        this.mSkinnyNotifsInLandscape = z2;
        StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("mIsSmallLandscapeLockscreenEnabled=false isSmallScreenLandscape=", " useSmallLandscapeLockscreenResources=", " skinnyNotifsInLandscape=", z, false);
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
        NotificationShelf notificationShelf;
        int childCount = getChildCount();
        if (this.mStatusBarState != 1 && (this.mQsMinHeight > f2 || (this.mShelf.getVisibility() == 0 && (notificationShelf = this.mShelf) != null && notificationShelf.getX() < f && f < notificationShelf.getX() + notificationShelf.getWidth() && notificationShelf.getY() < f2 && f2 < notificationShelf.getY() + notificationShelf.getHeight()))) {
            return false;
        }
        for (int i = childCount - 1; i >= 0; i--) {
            ExpandableView expandableView = (ExpandableView) getChildAt(i);
            if (expandableView.getVisibility() != 8) {
                if (expandableView == this.mEmptyShadeView) {
                    return true;
                }
                if (expandableView == this.mShelf) {
                    continue;
                } else {
                    float y = expandableView.getY();
                    if (y > f2) {
                        if (onKeyguard() && (expandableView instanceof ExpandableNotificationRow)) {
                            StringBuilder sb = new StringBuilder("isBelowLastNotification false : child top , ");
                            sb.append(y);
                            sb.append(" of ");
                            ExifInterface$$ExternalSyntheticOutline0.m(sb, ((ExpandableNotificationRow) expandableView).mLoggingKey, "StackScroller");
                        }
                        return false;
                    }
                    boolean z = f2 > (((float) expandableView.mActualHeight) + y) - ((float) expandableView.mClipBottomAmount);
                    FooterView footerView = this.mFooterView;
                    if (expandableView != footerView) {
                        if (expandableView == this.mEmptyShadeView) {
                            return true;
                        }
                        if (!z) {
                            if (onKeyguard() && (expandableView instanceof ExpandableNotificationRow)) {
                                ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("isBelowLastNotification false : !belowChild of "), ((ExpandableNotificationRow) expandableView).mLoggingKey, "StackScroller");
                            }
                            return false;
                        }
                    } else if (z) {
                        continue;
                    } else {
                        float x = f - footerView.getX();
                        float f3 = f2 - y;
                        if (x >= footerView.mContent.getX() && x <= footerView.mContent.getX() + footerView.mContent.getWidth() && f3 >= footerView.mContent.getY() && f3 <= footerView.mContent.getY() + footerView.mContent.getHeight()) {
                            return false;
                        }
                    }
                }
            }
        }
        if (onKeyguard()) {
            StringBuilder sb2 = new StringBuilder("isBelowLastNotification return :  of touchY : ");
            sb2.append(f2);
            sb2.append(" , topPadding : ");
            sb2.append(this.mAmbientState.mTopPadding);
            sb2.append(" , getStackTranslation : ");
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m(sb2, this.mAmbientState.mStackTranslation, "StackScroller");
        }
        AmbientState ambientState = this.mAmbientState;
        return f2 > ((float) ambientState.mTopPadding) + ambientState.mStackTranslation;
    }

    public final boolean isFullySwipedOut(ExpandableView expandableView) {
        float f;
        float abs = Math.abs(expandableView.getTranslation());
        if (this.mDismissUsingRowTranslationX) {
            float measuredWidth = expandableView.getMeasuredWidth();
            float measuredWidth2 = getMeasuredWidth();
            f = measuredWidth2 - ((measuredWidth2 - measuredWidth) / 2.0f);
        } else {
            f = expandableView.getMeasuredWidth();
        }
        return abs >= Math.abs(f);
    }

    public final boolean isHeadsUpTransition() {
        return this.mAmbientState.getTrackedHeadsUpRow() != null;
    }

    public final boolean isInsideQsHeader(MotionEvent motionEvent) {
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        if (this.mAmbientState.mNotificationScrimTop > motionEvent.getY()) {
            return true;
        }
        this.mQsHeader.getBoundsOnScreen(this.mQsHeaderBound);
        this.mQsHeaderBound.offsetTo(Math.round((motionEvent.getRawX() - motionEvent.getX()) + this.mQsHeader.getLeft()), Math.round(motionEvent.getRawY() - motionEvent.getY()));
        return this.mQsHeaderBound.contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
    }

    public final boolean isRubberbanded(boolean z) {
        return !z || this.mExpandedInThisMotion || this.mIsExpansionChanging || this.mPanelTracking || !this.mScrolledToTopOnFirstDown || (SecPanelSplitHelper.isEnabled() && getPanelSplitHelper().isShadeState());
    }

    public boolean isVisible(View view) {
        return view.getVisibility() == 0 && (!view.getClipBounds(this.mTmpRect) || this.mTmpRect.height() > 0);
    }

    public final void logHunAnimationSkipped(ExpandableNotificationRow expandableNotificationRow, String str) {
        NotificationStackScrollLogger notificationStackScrollLogger = this.mLogger;
        if (notificationStackScrollLogger == null) {
            return;
        }
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        LogLevel logLevel = LogLevel.INFO;
        NotificationStackScrollLogger$hunAnimationSkipped$2 notificationStackScrollLogger$hunAnimationSkipped$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$hunAnimationSkipped$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("heads up animation skipped: key: ", logMessage.getStr1(), " reason: ", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = notificationStackScrollLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotificationStackScroll", logLevel, notificationStackScrollLogger$hunAnimationSkipped$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
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
        for (int i = 0; i < this.mExpandedHeightListeners.size(); i++) {
            ((BiConsumer) this.mExpandedHeightListeners.get(i)).accept(Float.valueOf(f), Float.valueOf(saturate));
        }
    }

    public final void notifyHeightChangeListener(ExpandableView expandableView, boolean z) {
        ExpandableView.OnHeightChangedListener onHeightChangedListener = this.mOnHeightChangedListener;
        if (onHeightChangedListener != null) {
            onHeightChangedListener.onHeightChanged(expandableView, z);
        }
        Runnable runnable = this.mOnHeightChangedRunnable;
        if (runnable != null) {
            runnable.run();
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
            quickSettingsControllerImpl.updateQsState$1$1();
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
        if (!this.mIsInsetAnimationRunning) {
            updateImeInset(windowInsets);
        }
        return windowInsets;
    }

    public final void onChildHeightChanged(ExpandableView expandableView, boolean z) {
        boolean z2 = this.mAnimateStackYForContentHeightChange;
        if (z) {
            this.mAnimateStackYForContentHeightChange = true;
        }
        updateContentHeight();
        if (this.mOwnScrollY > 0 && (expandableView instanceof ExpandableNotificationRow)) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
            if (!onKeyguard() && expandableNotificationRow.mUserLocked && expandableNotificationRow != getFirstChildNotGone() && !expandableNotificationRow.mIsSummaryWithChildren) {
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
                    Flags.sceneContainer();
                    setOwnScrollY((int) ((this.mOwnScrollY + translationY) - f));
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
        if (z && this.mAnimationsEnabled && (this.mIsExpanded || (expandableNotificationRow2 != null && expandableNotificationRow2.mIsPinned))) {
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
        NotificationStackScrollLayoutController$$ExternalSyntheticLambda6 notificationStackScrollLayoutController$$ExternalSyntheticLambda6 = this.mClearAllAnimationListener;
        if (notificationStackScrollLayoutController$$ExternalSyntheticLambda6 != null) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationStackScrollLayoutController$$ExternalSyntheticLambda6.f$0;
            NotifCollection notifCollection = notificationStackScrollLayoutController.mNotifCollection;
            if (i == 0) {
                notifCollection.dismissAllNotifications(((NotificationLockscreenUserManagerImpl) notificationStackScrollLayoutController.mLockscreenUserManager).mCurrentUserId, false);
                return;
            }
            ArrayList arrayList = new ArrayList();
            Iterator it = ((ArrayList) list).iterator();
            while (it.hasNext()) {
                NotificationEntry notificationEntry = ((ExpandableNotificationRow) it.next()).mEntry;
                arrayList.add(new Pair(notificationEntry, new DismissedByUserStats(3, 1, ((NotificationVisibilityProviderImpl) notificationStackScrollLayoutController.mVisibilityProvider).obtain(notificationEntry))));
            }
            notifCollection.dismissNotifications(arrayList);
        }
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Resources resources = getResources();
        updateSplitNotificationShade();
        this.mStatusBarHeight = SystemBarUtils.getStatusBarHeight(((ViewGroup) this).mContext);
        this.mSwipeHelper.mDensityScale = resources.getDisplayMetrics().density;
        this.mSwipeHelper.mPagingTouchSlop = ViewConfiguration.get(getContext()).getScaledPagingTouchSlop();
        initView(getContext(), this.mSwipeHelper, this.mNotificationStackSizeCalculator);
        int i = this.mOrientation;
        int i2 = configuration.orientation;
        if (i != i2) {
            this.mIsChangedOrientation = true;
            this.mOrientation = i2;
            this.mAmbientState.mOrientation = i2;
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        inflateEmptyShadeView();
        inflateDndView();
        int i = FooterViewRefactor.$r8$clinit;
        Flags.notificationsFooterViewRefactor();
        inflateFooterView();
    }

    @Override // android.view.View
    public final boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if (!this.mScrollingEnabled || !this.mIsExpanded || this.mSwipeHelper.mIsSwiping || this.mExpandingNotification || this.mDisallowScrollingInThisMotion) {
            return false;
        }
        if ((motionEvent.getSource() & 2) != 0 && motionEvent.getAction() == 8 && !this.mIsBeingDragged) {
            float axisValue = motionEvent.getAxisValue(9);
            if (axisValue != 0.0f) {
                int verticalScrollFactor = (int) (axisValue * getVerticalScrollFactor());
                int scrollRange$1 = getScrollRange$1();
                int i = this.mOwnScrollY;
                int i2 = i - verticalScrollFactor;
                int i3 = i2 >= 0 ? i2 > scrollRange$1 ? scrollRange$1 : i2 : 0;
                if (i3 != i) {
                    setOwnScrollY(i3);
                    return true;
                }
            }
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    public final void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setScrollable(this.mScrollable);
        accessibilityEvent.setMaxScrollX(((ViewGroup) this).mScrollX);
        accessibilityEvent.setScrollY(this.mOwnScrollY);
        accessibilityEvent.setMaxScrollY(getScrollRange$1());
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
                        onSecondaryPointerUp$1(motionEvent);
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
            if (this.mScroller.springBack(((ViewGroup) this).mScrollX, this.mOwnScrollY, 0, 0, 0, getScrollRange$1())) {
                animateScroll();
            }
        } else {
            int y2 = (int) motionEvent.getY();
            AnonymousClass9 anonymousClass9 = this.mScrollAdapter;
            anonymousClass9.getClass();
            int i3 = SceneContainerFlag.$r8$clinit;
            Flags.sceneContainer();
            this.mScrolledToTopOnFirstDown = NotificationStackScrollLayout.this.mOwnScrollY == 0;
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
        Flags.sceneContainer();
        this.mMaxLayoutHeight = getHeight();
        updateAlgorithmHeightAndPadding();
        updateContentHeight();
        clampScrollPosition();
        requestChildrenUpdate();
        updateFirstAndLastBackgroundViews();
        updateAlgorithmLayoutMinHeight();
        updateOwnTranslationZ();
        StackScrollAlgorithm stackScrollAlgorithm = this.mStackScrollAlgorithm;
        ViewGroup viewGroup = this.mQsHeader;
        if (viewGroup != null) {
            viewGroup.getHeight();
        }
        stackScrollAlgorithm.getClass();
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
            Log.d("StackScroller", "visible from gone, fisrt measure!");
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
        Flags.sceneContainer();
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
                quickSettingsControllerImpl.flingQs(f, (z && isExpansionEnabled) ? 0 : 1, new QuickSettingsControllerImpl$$ExternalSyntheticLambda11(nsslOverscrollTopChangedListener, 4), false);
            }
        }
        this.mDontReportNextOverScroll = true;
        setOverScrollAmount(0.0f, true, false, true);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onScrollTouch(android.view.MotionEvent r23) {
        /*
            Method dump skipped, instructions count: 938
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.onScrollTouch(android.view.MotionEvent):boolean");
    }

    public final void onSecondaryPointerUp$1(MotionEvent motionEvent) {
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

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        NotificationStackScrollLayoutController.TouchHandler touchHandler = this.mTouchHandler;
        if (touchHandler != null) {
            boolean onTouchEvent = touchHandler.onTouchEvent(motionEvent);
            int i = SceneContainerFlag.$r8$clinit;
            Flags.sceneContainer();
            if (onTouchEvent) {
                if (this.mOrientation != 2) {
                    return true;
                }
                float f = this.mInitialTouchX;
                return f >= ((float) this.mSidePaddings) && f <= ((float) (getWidth() - this.mSidePaddings));
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
        }
        boolean z2 = true;
        if (this.mIsExpanded && this.mAnimationsEnabled && !this.mChangePositionInProgress && !this.mAmbientState.isFullyHidden()) {
            this.mChildrenToAddAnimated.add(expandableView);
            this.mNeedsAnimation = true;
        }
        if ((z ? ((ExpandableNotificationRow) expandableView).mIsHeadsUp : false) && this.mAnimationsEnabled && !this.mChangePositionInProgress && !this.mAmbientState.isFullyHidden()) {
            this.mAddedHeadsUpChildren.add(expandableView);
            this.mChildrenToAddAnimated.remove(expandableView);
        }
        if ((!this.mAnimationsEnabled && !this.mPulsing) || (!this.mIsExpanded && !isPinnedHeadsUp(expandableView))) {
            z2 = false;
        }
        if (z) {
            ((ExpandableNotificationRow) expandableView).setAnimationRunning(z2);
        }
        if (z) {
            ((ExpandableNotificationRow) expandableView).setChronometerRunning(this.mIsExpanded);
        }
        if (z) {
            ((ExpandableNotificationRow) expandableView).setDismissUsingRowTranslationX(this.mDismissUsingRowTranslationX);
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

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onViewRemovedInternal(com.android.systemui.statusbar.notification.row.ExpandableView r10, android.view.ViewGroup r11) {
        /*
            Method dump skipped, instructions count: 426
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

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0020, code lost:
    
        if (r5 != 16908346) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean performAccessibilityActionInternal(int r5, android.os.Bundle r6) {
        /*
            r4 = this;
            boolean r6 = super.performAccessibilityActionInternal(r5, r6)
            r0 = 1
            if (r6 == 0) goto L8
            return r0
        L8:
            boolean r6 = r4.isEnabled()
            r1 = 0
            if (r6 != 0) goto L10
            return r1
        L10:
            r6 = 4096(0x1000, float:5.74E-42)
            if (r5 == r6) goto L25
            r6 = 8192(0x2000, float:1.14794E-41)
            if (r5 == r6) goto L23
            r6 = 16908344(0x1020038, float:2.3877386E-38)
            if (r5 == r6) goto L23
            r6 = 16908346(0x102003a, float:2.3877392E-38)
            if (r5 == r6) goto L25
            goto L5c
        L23:
            r5 = -1
            goto L26
        L25:
            r5 = r0
        L26:
            int r6 = r4.getHeight()
            int r2 = r4.mPaddingBottom
            int r6 = r6 - r2
            com.android.systemui.statusbar.notification.stack.AmbientState r2 = r4.mAmbientState
            int r2 = r2.mTopPadding
            int r6 = r6 - r2
            int r2 = r4.mPaddingTop
            int r6 = r6 - r2
            com.android.systemui.statusbar.NotificationShelf r2 = r4.mShelf
            int r2 = r2.getHeight()
            int r6 = r6 - r2
            int r2 = r4.mOwnScrollY
            int r5 = r5 * r6
            int r5 = r5 + r2
            int r6 = r4.getScrollRange$1()
            int r5 = java.lang.Math.min(r5, r6)
            int r5 = java.lang.Math.max(r1, r5)
            int r6 = r4.mOwnScrollY
            if (r5 == r6) goto L5c
            android.widget.OverScroller r2 = r4.mScroller
            int r3 = r4.mScrollX
            int r5 = r5 - r6
            r2.startScroll(r3, r6, r1, r5)
            r4.animateScroll()
            return r0
        L5c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.performAccessibilityActionInternal(int, android.os.Bundle):boolean");
    }

    public final void removeTransientView(View view) {
        NotificationStackScrollLogger notificationStackScrollLogger = this.mLogger;
        if (notificationStackScrollLogger != null && (view instanceof ExpandableNotificationRow)) {
            final NotificationEntry notificationEntry = ((ExpandableNotificationRow) view).mEntry;
            notificationStackScrollLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$removeTransientRow$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return "removeTransientRow from NSSL: childKey: " + ((LogMessage) obj).getStr1() + " -- view : " + NotificationEntry.this.row;
                }
            };
            LogBuffer logBuffer = notificationStackScrollLogger.notificationRenderBuffer;
            LogMessage obtain = logBuffer.obtain("NotificationStackScroll", logLevel, function1, null);
            ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
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
        List<ExpandableNotificationRow> attachedChildren;
        Trace.beginSection("NSSL.resetAllSwipeState()");
        this.mSwipeHelper.resetSwipeStates(true);
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            NotificationSwipeHelper notificationSwipeHelper = this.mSwipeHelper;
            notificationSwipeHelper.getClass();
            if (childAt.getTranslationX() != 0.0f) {
                notificationSwipeHelper.setTranslation(0.0f, childAt);
                notificationSwipeHelper.updateSwipeProgressFromOffset(childAt, 0.0f, true);
            }
            if ((childAt instanceof ExpandableNotificationRow) && (attachedChildren = ((ExpandableNotificationRow) childAt).getAttachedChildren()) != null) {
                for (ExpandableNotificationRow expandableNotificationRow : attachedChildren) {
                    NotificationSwipeHelper notificationSwipeHelper2 = this.mSwipeHelper;
                    notificationSwipeHelper2.getClass();
                    if (expandableNotificationRow.getTranslationX() != 0.0f) {
                        notificationSwipeHelper2.setTranslation(0.0f, expandableNotificationRow);
                        notificationSwipeHelper2.updateSwipeProgressFromOffset(expandableNotificationRow, 0.0f, true);
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
        ExpandableView expandableView = (ExpandableView) view;
        int positionInLinearLayout = getPositionInLinearLayout(view);
        int targetScrollForView = targetScrollForView(expandableView, positionInLinearLayout);
        int intrinsicHeight = expandableView.getIntrinsicHeight() + positionInLinearLayout;
        int i = this.mOwnScrollY;
        if (i >= targetScrollForView && intrinsicHeight >= i) {
            return false;
        }
        this.mScroller.startScroll(((ViewGroup) this).mScrollX, i, 0, targetScrollForView - i);
        this.mDontReportNextOverScroll = true;
        animateScroll();
        return true;
    }

    @Override // android.view.View
    public final void setAlpha(float f) {
        if (QsAnimatorState.isCustomizerShowing || this.mController.mMusicItemExpanded) {
            return;
        }
        if (!QsAnimatorState.isDetailShowing || QsAnimatorState.isDetailClosing) {
            if (SecPanelSplitHelper.isEnabled()) {
                if (getPanelSplitHelper().isQSState()) {
                    f = 0.0f;
                }
                if (getPanelSplitHelper().isShadeState() && this.mStatusBarState != 1) {
                    f = 1.0f;
                }
            }
            super.setAlpha(f);
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
        int i;
        boolean shouldSkipHeightUpdate = shouldSkipHeightUpdate();
        int i2 = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
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
        boolean z = calculateAppearFraction(f) < 1.0f;
        AmbientState ambientState = this.mAmbientState;
        ambientState.mAppearing = z;
        if (z) {
            f3 = calculateAppearFraction(f);
            float interpolate = f3 >= 0.0f ? NotificationUtils.interpolate(((this.mShelf.getHeight() + this.mWaterfallTopInset) + (-this.mAmbientState.mTopPadding)) - this.mShelf.getHeight(), 0.0f, f3) : (f - getAppearStartPosition()) + (((this.mShelf.getHeight() + this.mWaterfallTopInset) + (-this.mAmbientState.mTopPadding)) - this.mShelf.getHeight());
            int i3 = (int) (f - interpolate);
            if (isHeadsUpTransition() && f3 >= 0.0f) {
                interpolate = MathUtils.lerp(this.mHeadsUpInset - this.mAmbientState.mTopPadding, 0.0f, f3);
            }
            if (NotiRune.NOTI_STATIC_SHELF_ALPHA_VI && !this.mShelfAlphaOutAnimating) {
                if (LsRune.AOD_FULLSCREEN) {
                    SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = (SecUnlockedScreenOffAnimationHelper) Dependency.sDependency.getDependencyInner(SecUnlockedScreenOffAnimationHelper.class);
                    if (secUnlockedScreenOffAnimationHelper.lastShouldPlay || secUnlockedScreenOffAnimationHelper.skipAnimationInOthers) {
                        this.mShelf.mShelfIcons.setAlpha(0.0f);
                    }
                }
                if (this.mAmbientState.mDozeAmount == 0.0f && !onKeyguard()) {
                    this.mShelf.mShelfIcons.animate().alpha(0.0f).setDuration(100L).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.11
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            NotificationStackScrollLayout.this.mShelfAlphaOutAnimating = false;
                            super.onAnimationEnd(animator);
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                            NotificationStackScrollLayout.this.mShelfAlphaOutAnimating = true;
                            super.onAnimationStart(animator);
                        }
                    }).setInterpolator(Interpolators.ALPHA_IN).start();
                }
            }
            i = i3;
            f2 = interpolate;
        } else {
            if (this.mShouldShowShelfOnly) {
                i = this.mShelf.getHeight() + ambientState.mTopPadding;
            } else if (this.mQsFullScreen) {
                int i4 = (this.mContentHeight - ambientState.mTopPadding) + this.mIntrinsicPadding;
                int height2 = this.mShelf.getHeight() + this.mMaxTopPadding;
                i = i4 <= height2 ? height2 : (int) NotificationUtils.interpolate(i4, height2, this.mQsExpansionFraction);
            } else {
                if (shouldSkipHeightUpdate) {
                    f = this.mExpandedHeight;
                }
                i = (int) f;
            }
            if (NotiRune.NOTI_STATIC_SHELF_ALPHA_VI && !this.mShelfAlphaInAnimating) {
                if (LsRune.AOD_FULLSCREEN) {
                    SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper2 = (SecUnlockedScreenOffAnimationHelper) Dependency.sDependency.getDependencyInner(SecUnlockedScreenOffAnimationHelper.class);
                    if (secUnlockedScreenOffAnimationHelper2.lastShouldPlay || secUnlockedScreenOffAnimationHelper2.skipAnimationInOthers) {
                        this.mShelf.mShelfIcons.setAlpha(1.0f);
                    }
                }
                if (this.mAmbientState.mDozeAmount == 0.0f) {
                    this.mShelf.mShelfIcons.animate().alpha(1.0f).setDuration(100L).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.10
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            NotificationStackScrollLayout.this.mShelfAlphaInAnimating = false;
                            super.onAnimationEnd(animator);
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                            NotificationStackScrollLayout.this.mShelfAlphaInAnimating = true;
                            super.onAnimationStart(animator);
                        }
                    }).setInterpolator(Interpolators.ALPHA_IN).start();
                }
            }
        }
        this.mAmbientState.mAppearFraction = f3;
        if (i != this.mCurrentStackHeight && !shouldSkipHeightUpdate) {
            this.mCurrentStackHeight = i;
            updateAlgorithmHeightAndPadding();
            requestChildrenUpdate();
        }
        AmbientState ambientState2 = this.mAmbientState;
        if (f2 != ambientState2.mStackTranslation) {
            ambientState2.mStackTranslation = f2;
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

    public final void setFooterView(final FooterView footerView) {
        int i;
        FooterView footerView2 = this.mFooterView;
        if (footerView2 != null) {
            i = indexOfChild(footerView2);
            removeView(this.mFooterView);
        } else {
            i = -1;
        }
        this.mFooterView = footerView;
        addView(footerView, i);
        int i2 = FooterViewRefactor.$r8$clinit;
        Flags.notificationsFooterViewRefactor();
        View.OnClickListener onClickListener = this.mManageButtonClickListener;
        if (onClickListener != null) {
            this.mFooterView.mManageOrHistoryButton.setOnClickListener(onClickListener);
        }
        FooterView footerView3 = this.mFooterView;
        View.OnClickListener onClickListener2 = new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                FooterView footerView4 = footerView;
                NotificationStackScrollLayoutController$$ExternalSyntheticLambda6 notificationStackScrollLayoutController$$ExternalSyntheticLambda6 = notificationStackScrollLayout.mFooterClearAllListener;
                if (notificationStackScrollLayoutController$$ExternalSyntheticLambda6 != null) {
                    notificationStackScrollLayoutController$$ExternalSyntheticLambda6.f$0.mMetricsLogger.action(148);
                }
                notificationStackScrollLayout.clearNotifications(0, true);
                footerView4.setSecondaryVisible(false, true, null);
            }
        };
        footerView3.getClass();
        Flags.notificationsFooterViewRefactor();
        footerView3.mClearAllButton.setOnClickListener(onClickListener2);
    }

    public final void setHeadsUpAnimatingAway(boolean z) {
        if (this.mHeadsUpAnimatingAway != z) {
            this.mHeadsUpAnimatingAway = z;
            Consumer consumer = this.mHeadsUpAnimatingAwayListener;
            if (consumer != null) {
                consumer.accept(Boolean.valueOf(z));
            }
        }
        updateClipping$1();
    }

    public void setIsBeingDragged(boolean z) {
        this.mIsBeingDragged = z;
        if (z) {
            requestDisallowInterceptTouchEvent(true);
            cancelLongPress();
            this.mSwipeHelper.resetExposedMenuView(true, true);
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
                NotificationsHeadsUpRefactor notificationsHeadsUpRefactor = NotificationsHeadsUpRefactor.INSTANCE;
                Flags.notificationsHeadsUpRefactor();
            } else {
                GroupExpansionManagerImpl groupExpansionManagerImpl = (GroupExpansionManagerImpl) this.mGroupExpansionManager;
                groupExpansionManagerImpl.getClass();
                Iterator it = new ArrayList(groupExpansionManagerImpl.mExpandedGroups).iterator();
                while (it.hasNext()) {
                    groupExpansionManagerImpl.setGroupExpanded((NotificationEntry) it.next(), false);
                }
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
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                if (childAt instanceof ExpandableNotificationRow) {
                    ((ExpandableNotificationRow) childAt).setChronometerRunning(this.mIsExpanded);
                }
            }
            requestChildrenUpdate();
            updateUseRoundedRectClipping();
            updateDismissBehavior();
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
        if (getVisibility() != 8 && i == 8) {
            this.mLastGoneCallTrace = Log.getStackTraceString(new Throwable());
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

    public final boolean shouldShowFooterView(boolean z) {
        FooterViewRefactor.assertInLegacyMode();
        return ((!z && this.mController.getVisibleNotificationCount() <= 0) || !this.mIsCurrentUserSetup || onKeyguard() || this.mUpcomingStatusBarState == 1 || (this.mQsExpansionFraction == 1.0f && this.mQsFullScreen) || this.mScreenOffAnimationController.shouldHideNotificationsFooter() || this.mIsRemoteInputActive) ? false : true;
    }

    public final boolean shouldSkipHeightUpdate() {
        if (this.mAmbientState.isOnKeyguard()) {
            AmbientState ambientState = this.mAmbientState;
            if (ambientState.mIsSwipingUp || (ambientState.mIsFlinging && ambientState.mIsFlingRequiredAfterLockScreenSwipeUp)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:284:0x0599, code lost:
    
        if (r1 == 13) goto L252;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startAnimationToState$1() {
        /*
            Method dump skipped, instructions count: 2490
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.startAnimationToState$1():void");
    }

    public final int targetScrollForView(ExpandableView expandableView, int i) {
        int max = ((Math.max(0, this.mImeInset - ((getRootView().getHeight() - getHeight()) - getLocationOnScreen()[1])) + (expandableView.getIntrinsicHeight() + i)) - getHeight()) + ((this.mIsExpanded || !isPinnedHeadsUp(expandableView)) ? this.mAmbientState.mTopPadding : this.mHeadsUpInset) + this.mMinimumPaddings;
        return ((this.mIsExpanded || !isPinnedHeadsUp(expandableView)) && max > 0 && max < getScrollAmountToScrollBoundary()) ? getScrollAmountToScrollBoundary() : max;
    }

    public final void updateAlgorithmHeightAndPadding() {
        this.mAmbientState.mLayoutHeight = Math.min(this.mMaxLayoutHeight, this.mCurrentStackHeight);
        this.mAmbientState.mLayoutMaxHeight = this.mMaxLayoutHeight;
        updateAlgorithmLayoutMinHeight();
    }

    public final void updateAlgorithmLayoutMinHeight() {
        this.mAmbientState.mLayoutMinHeight = (this.mQsFullScreen || isHeadsUpTransition()) ? getLayoutMinHeight() : 0;
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
        Object invoke;
        float f = this.mAmbientState.isOnKeyguard() ? 0.0f : this.mMinimumPaddings;
        NotificationShelf notificationShelf = this.mShelf;
        int height = notificationShelf != null ? notificationShelf.getHeight() : 0;
        FooterView footerView = this.mFooterView;
        int height2 = footerView != null ? footerView.getHeight() : 0;
        int i = (int) f;
        NotificationStackSizeCalculator notificationStackSizeCalculator = this.mNotificationStackSizeCalculator;
        int i2 = this.mMaxDisplayedNotifications;
        notificationStackSizeCalculator.getClass();
        final SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 sequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 = new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new NotificationStackSizeCalculator$computeHeightPerNotificationLimit$1(notificationStackSizeCalculator, this, height, null));
        Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$computeHeight$3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ((Number) obj).intValue();
                return (NotificationStackSizeCalculator.StackHeight) SequencesKt___SequencesKt.last(Sequence.this);
            }
        };
        if (i2 >= 0) {
            SequenceBuilderIterator it = SequencesKt__SequenceBuilderKt.iterator(sequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1.$block$inlined);
            int i3 = 0;
            while (true) {
                if (!it.hasNext()) {
                    invoke = function1.invoke(Integer.valueOf(i2));
                    break;
                }
                Object next = it.next();
                int i4 = i3 + 1;
                if (i2 == i3) {
                    invoke = next;
                    break;
                }
                i3 = i4;
            }
        } else {
            invoke = function1.invoke(Integer.valueOf(i2));
        }
        NotificationStackSizeCalculator.StackHeight stackHeight = (NotificationStackSizeCalculator.StackHeight) invoke;
        float f2 = stackHeight.notifsHeight;
        boolean z = notificationStackSizeCalculator.saveSpaceOnLockscreen;
        float f3 = stackHeight.shelfHeightWithSpaceBefore;
        float f4 = i + ((int) (z ? stackHeight.notifsHeightSavingSpace + f3 : f2 + f3));
        this.mIntrinsicContentHeight = f4;
        float f5 = this.mBottomPadding;
        this.mContentHeight = (int) (f4 + Math.max(this.mIntrinsicPadding, this.mAmbientState.mTopPadding) + f5);
        this.mScrollViewFields.intrinsicStackHeight = (int) (this.mIntrinsicPadding + this.mIntrinsicContentHeight + height2 + f5);
        boolean z2 = !this.mQsFullScreen && getScrollRange$1() > 0;
        if (z2 != this.mScrollable) {
            this.mScrollable = z2;
            setFocusable(z2);
            updateForwardAndBackwardScrollability();
        }
        clampScrollPosition();
        updateStackPosition(false);
        this.mAmbientState.mContentHeight = this.mContentHeight;
        Iterator it2 = this.mStackHeightChangedListeners.iterator();
        while (it2.hasNext()) {
            ((Runnable) it2.next()).run();
        }
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
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(((ViewGroup) this).mContext, android.R.^attr-private.materialColorOnTertiary, 0);
        int colorAttrDefaultColor2 = Utils.getColorAttrDefaultColor(((ViewGroup) this).mContext, android.R.^attr-private.materialColorOnTertiaryFixed, 0);
        ColorUpdateLogger.Companion.getClass();
        NotificationSectionsManager notificationSectionsManager = this.mSectionsManager;
        SectionHeaderView sectionHeaderView = ((SectionHeaderNodeControllerImpl) notificationSectionsManager.peopleHeaderController)._view;
        if (sectionHeaderView != null) {
            sectionHeaderView.mLabelView.setTextColor(colorAttrDefaultColor);
            sectionHeaderView.mClearAllButton.setImageTintList(ColorStateList.valueOf(colorAttrDefaultColor2));
        }
        SectionHeaderView sectionHeaderView2 = ((SectionHeaderNodeControllerImpl) notificationSectionsManager.silentHeaderController)._view;
        if (sectionHeaderView2 != null) {
            sectionHeaderView2.mLabelView.setTextColor(colorAttrDefaultColor);
            sectionHeaderView2.mClearAllButton.setImageTintList(ColorStateList.valueOf(colorAttrDefaultColor2));
        }
        SectionHeaderView sectionHeaderView3 = ((SectionHeaderNodeControllerImpl) notificationSectionsManager.alertingHeaderController)._view;
        if (sectionHeaderView3 != null) {
            sectionHeaderView3.mLabelView.setTextColor(colorAttrDefaultColor);
            sectionHeaderView3.mClearAllButton.setImageTintList(ColorStateList.valueOf(colorAttrDefaultColor2));
        }
        if (NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW || (emptyShadeView = this.mEmptyShadeView) == null) {
            return;
        }
        if (NotiRune.NOTI_STYLE_EMPTY_SHADE) {
            emptyShadeView.mEmptyText.setTextColor(emptyShadeView.getResources().getColor(R.color.sec_no_notification_text_color));
        } else {
            emptyShadeView.mEmptyText.setTextColor(colorAttrDefaultColor2);
        }
        emptyShadeView.mEmptyFooterText.setTextColor(colorAttrDefaultColor);
        emptyShadeView.mEmptyFooterText.setCompoundDrawableTintList(ColorStateList.valueOf(colorAttrDefaultColor));
    }

    public final void updateDismissBehavior() {
        if (!this.mDismissUsingRowTranslationX) {
            this.mDismissUsingRowTranslationX = true;
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                if (childAt instanceof ExpandableNotificationRow) {
                    ((ExpandableNotificationRow) childAt).setDismissUsingRowTranslationX(true);
                }
            }
        }
    }

    public final void updateDndView(int i) {
        boolean z = i == 1 && (!onKeyguard() || this.mAmbientState.isNeedsToExpandLocksNoti());
        DndStatusView dndStatusView = this.mDndStatusView;
        if (dndStatusView != null) {
            dndStatusView.setVisible(z, false);
            DndStatusView dndStatusView2 = this.mDndStatusView;
            int i2 = z ? 0 : 8;
            dndStatusView2.mDndStatusVisibility = i2;
            dndStatusView2.setSecondaryVisible(i2 == 0, false, null);
            requestChildrenUpdate();
        }
    }

    public final void updateEmptyShadeView(boolean z, boolean z2, boolean z3) {
        if (NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW) {
            return;
        }
        this.mEmptyShadeView.setVisible(z, this.mIsExpanded && this.mAnimationsEnabled);
        if (z2) {
            updateEmptyShadeViewResources(R.string.dnd_suppressing_shade_text, 0, 0);
        } else if (z3) {
            updateEmptyShadeViewResources(R.string.no_unseen_notif_text, R.string.unlock_to_see_notif_text, R.drawable.ic_friction_lock_closed);
        } else {
            updateEmptyShadeViewResources(R.string.empty_shade_text, 0, 0);
        }
    }

    public final void updateEmptyShadeViewResources(int i, int i2, int i3) {
        Drawable drawable;
        EmptyShadeView emptyShadeView = this.mEmptyShadeView;
        if (emptyShadeView.mText != i) {
            emptyShadeView.mText = i;
            emptyShadeView.mEmptyText.setText(i);
        }
        EmptyShadeView emptyShadeView2 = this.mEmptyShadeView;
        if (emptyShadeView2.mFooterText != i2) {
            emptyShadeView2.mFooterText = i2;
            if (i2 != 0) {
                emptyShadeView2.mEmptyFooterText.setText(i2);
            } else {
                emptyShadeView2.mEmptyFooterText.setText((CharSequence) null);
            }
        }
        EmptyShadeView emptyShadeView3 = this.mEmptyShadeView;
        if (emptyShadeView3.mFooterIcon != i3) {
            emptyShadeView3.mFooterIcon = i3;
            if (i3 == 0) {
                drawable = null;
            } else {
                drawable = emptyShadeView3.getResources().getDrawable(i3);
                int i4 = emptyShadeView3.mSize;
                drawable.setBounds(0, 0, i4, i4);
            }
            emptyShadeView3.mEmptyFooterText.setCompoundDrawablesRelative(drawable, null, null, null);
        }
        if (i3 == 0 && i2 == 0) {
            EmptyShadeView emptyShadeView4 = this.mEmptyShadeView;
            emptyShadeView4.mFooterVisibility = 8;
            emptyShadeView4.setSecondaryVisible(false, false, null);
        } else {
            EmptyShadeView emptyShadeView5 = this.mEmptyShadeView;
            emptyShadeView5.mFooterVisibility = 0;
            emptyShadeView5.setSecondaryVisible(true, false, null);
        }
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

    public void updateFooter() {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController;
        FooterViewRefactor.assertInLegacyMode();
        if (this.mFooterView == null || (notificationStackScrollLayoutController = this.mController) == null) {
            return;
        }
        notificationStackScrollLayoutController.isHistoryEnabled();
        FooterViewRefactor.assertInLegacyMode();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.mController;
        boolean z = false;
        if (notificationStackScrollLayoutController2 != null) {
            FooterViewRefactor.assertInLegacyMode();
            z = notificationStackScrollLayoutController2.hasNotifications(0, true);
        }
        shouldShowFooterView(z);
        FooterViewRefactor.assertInLegacyMode();
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
            int targetScrollForView = targetScrollForView(expandableView, positionInLinearLayout);
            int intrinsicHeight = expandableView.getIntrinsicHeight() + positionInLinearLayout;
            int max = Math.max(0, Math.min(targetScrollForView, getScrollRange$1()));
            int i = this.mOwnScrollY;
            if (i < max || intrinsicHeight < i) {
                setOwnScrollY(max);
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateForwardAndBackwardScrollability() {
        /*
            r5 = this;
            boolean r0 = r5.mScrollable
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L15
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$9 r0 = r5.mScrollAdapter
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.this
            int r3 = r0.mOwnScrollY
            int r0 = r0.getScrollRange$1()
            if (r3 < r0) goto L13
            goto L15
        L13:
            r0 = r2
            goto L16
        L15:
            r0 = r1
        L16:
            boolean r3 = r5.mScrollable
            if (r3 == 0) goto L2d
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$9 r3 = r5.mScrollAdapter
            r3.getClass()
            int r4 = com.android.systemui.scene.shared.flag.SceneContainerFlag.$r8$clinit
            com.android.systemui.Flags.sceneContainer()
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r3 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.this
            int r3 = r3.mOwnScrollY
            if (r3 != 0) goto L2b
            goto L2d
        L2b:
            r3 = r2
            goto L2e
        L2d:
            r3 = r1
        L2e:
            boolean r4 = r5.mForwardScrollable
            if (r0 != r4) goto L36
            boolean r4 = r5.mBackwardScrollable
            if (r3 == r4) goto L37
        L36:
            r1 = r2
        L37:
            r5.mForwardScrollable = r0
            r5.mBackwardScrollable = r3
            if (r1 == 0) goto L42
            r0 = 2048(0x800, float:2.87E-42)
            r5.sendAccessibilityEvent(r0)
        L42:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.updateForwardAndBackwardScrollability():void");
    }

    public final void updateImeInset(WindowInsets windowInsets) {
        ExpandableViewState expandableViewState;
        int i = windowInsets.getInsets(WindowInsets.Type.ime()).bottom;
        this.mImeInset = i;
        FooterView footerView = this.mFooterView;
        if (footerView != null && (expandableViewState = footerView.mViewState) != null) {
            FooterView.FooterViewState footerViewState = (FooterView.FooterViewState) expandableViewState;
            footerViewState.resetY = (i > 0) | footerViewState.resetY;
        }
        if (this.mForcedScroll != null) {
            updateForcedScroll();
        }
        int scrollRange$1 = getScrollRange$1();
        if (this.mOwnScrollY > scrollRange$1) {
            setOwnScrollY(scrollRange$1);
        }
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
        NotificationShelf notificationShelf = this.mShelf;
        notificationShelf.mAnimationsEnabled = z;
        if (!z) {
            notificationShelf.mShelfIcons.setAnimationsEnabled(false);
        }
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
        ExpandableView firstChildNotGone;
        setTranslationZ((this.mKeyguardBypassEnabled && this.mAmbientState.isHiddenAtAll() && (firstChildNotGone = getFirstChildNotGone()) != null && firstChildNotGone.showingPulsing()) ? firstChildNotGone.getTranslationZ() : 0.0f);
    }

    public final void updateRoundedClipPath() {
        SceneContainerFlag.assertInLegacyMode();
        this.mRoundedClipPath.reset();
        Path path = this.mRoundedClipPath;
        float f = this.mRoundedRectClippingLeft;
        int i = this.mRoundedRectClippingTop;
        int i2 = this.mRoundedRectClippingYTranslation;
        path.addRoundRect(f, i + i2, this.mRoundedRectClippingRight, this.mRoundedRectClippingBottom + i2, this.mBgCornerRadii, Path.Direction.CW);
        if (this.mShouldUseRoundedRectClipping) {
            invalidate();
        }
    }

    public final void updateSectionColor() {
        int color = ((ViewGroup) this).mContext.getColor(R.color.notification_section_header_text_color);
        NotificationSectionsManager notificationSectionsManager = this.mSectionsManager;
        SectionHeaderView sectionHeaderView = ((SectionHeaderNodeControllerImpl) notificationSectionsManager.peopleHeaderController)._view;
        if (sectionHeaderView != null) {
            sectionHeaderView.mLabelView.setTextColor(color);
            sectionHeaderView.mClearAllButton.setImageTintList(ColorStateList.valueOf(color));
        }
        SectionHeaderView sectionHeaderView2 = ((SectionHeaderNodeControllerImpl) notificationSectionsManager.silentHeaderController)._view;
        if (sectionHeaderView2 != null) {
            sectionHeaderView2.mLabelView.setTextColor(color);
            sectionHeaderView2.mClearAllButton.setImageTintList(ColorStateList.valueOf(color));
        }
        SectionHeaderView sectionHeaderView3 = ((SectionHeaderNodeControllerImpl) notificationSectionsManager.alertingHeaderController)._view;
        if (sectionHeaderView3 != null) {
            sectionHeaderView3.mLabelView.setTextColor(color);
            sectionHeaderView3.mClearAllButton.setImageTintList(ColorStateList.valueOf(color));
        }
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
        if (this.mQsExpansionFraction > 0.0f || shouldSkipHeightUpdate()) {
            updateStackHeight(this.mAmbientState.mStackEndHeight, f);
        } else {
            float max = this.mMaxDisplayedNotifications != -1 ? this.mIntrinsicContentHeight : Math.max(0.0f, (getHeight() - getEmptyBottomMargin()) - this.mAmbientState.mTopPadding);
            this.mAmbientState.mStackEndHeight = max;
            updateStackHeight(max, f);
        }
        if (f2 != this.mAmbientState.mStackHeight) {
            requestChildrenUpdate();
        }
    }

    public void updateStackHeight(float f, float f2) {
        Flags.FEATURE_FLAGS.getClass();
        this.mAmbientState.mStackHeight = MathUtils.lerp(0.5f * f, f, f2);
    }

    public final void updateStackPosition(boolean z) {
        boolean z2 = onKeyguard() && this.mAmbientState.isNeedsToExpandLocksNoti();
        AmbientState ambientState = this.mAmbientState;
        float f = ambientState.mQsExpansionFraction;
        float f2 = 0.0f;
        float navBarHeight = ((f < 0.8f ? 0.0f : (f - 0.8f) / 0.2f) * ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getNavBarHeight(((ViewGroup) this).mContext)) + ((((ambientState.mTopPadding + this.mExtraTopInsetForFullShadeTransition) + ambientState.mOverExpansion) + 0.0f) - getCurrentOverScrollAmount(false));
        AmbientState ambientState2 = this.mAmbientState;
        float f3 = ambientState2.mExpansionFraction;
        if (z2) {
            f3 = ambientState2.mFractionToShade;
        }
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = ambientState2.mStatusBarKeyguardViewManager;
        if (statusBarKeyguardViewManager != null && statusBarKeyguardViewManager.isPrimaryBouncerInTransit() && this.mQsExpansionFraction > 0.0f) {
            f3 = BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(f3);
        }
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        if (!SecPanelSplitHelper.isEnabled() && (this.mAmbientState.mExpansionChanging || (onKeyguard() && this.mAmbientState.isNeedsToExpandLocksNoti()))) {
            f2 = navBarHeight - this.mYDiff;
        }
        this.mAmbientState.mStackY = MathUtils.lerp(f2, navBarHeight, f3);
        Consumer consumer = this.mOnStackYChanged;
        if (consumer != null) {
            consumer.accept(Boolean.valueOf(z));
        }
        updateStackEndHeightAndStackHeight(f3);
    }

    public final void updateUseRoundedRectClipping() {
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        boolean z = this.mIsExpanded && ((this.mQsExpansionFraction > 0.5f ? 1 : (this.mQsExpansionFraction == 0.5f ? 0 : -1)) < 0);
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

    public final void updateVisibility$1() {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mController;
        boolean z = (this.mAmbientState.isFullyHidden() && onKeyguard()) ? false : true;
        notificationStackScrollLayoutController.getClass();
        if (z && notificationStackScrollLayoutController.mBlockHideAmountVisibility) {
            notificationStackScrollLayoutController.mBlockHideAmountVisibility = false;
            z = false;
        }
        int i = z ? 0 : 4;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.setVisibility(i);
        int i2 = FooterViewRefactor.$r8$clinit;
        Flags.notificationsFooterViewRefactor();
        if (notificationStackScrollLayout.getVisibility() == 0) {
            notificationStackScrollLayoutController.updateShowEmptyShadeView();
            notificationStackScrollLayoutController.updateImportantForAccessibility();
        }
    }

    public final void setOverScrollAmount(float f, final boolean z, boolean z2, boolean z3, final boolean z4) {
        if (z3) {
            StackStateAnimator stackStateAnimator = this.mStateAnimator;
            ValueAnimator valueAnimator = z ? stackStateAnimator.mTopOverScrollAnimator : stackStateAnimator.mBottomOverScrollAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
        }
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
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator.3
            public final /* synthetic */ boolean val$isRubberbanded;
            public final /* synthetic */ boolean val$onTop;

            public AnonymousClass3(final boolean z5, final boolean z42) {
                r2 = z5;
                r3 = z42;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                StackStateAnimator.this.mHostLayout.setOverScrollAmount(((Float) valueAnimator3.getAnimatedValue()).floatValue(), r2, false, false, r3);
            }
        });
        ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator.4
            public final /* synthetic */ boolean val$onTop;

            public AnonymousClass4(final boolean z5) {
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
        int i2;
        int i3 = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        AmbientState ambientState = this.mAmbientState;
        if (ambientState.mIsClosing || ambientState.mClearAllInProgress || i == (i2 = this.mOwnScrollY)) {
            return;
        }
        int i4 = ((ViewGroup) this).mScrollX;
        onScrollChanged(i4, i, i4, i2);
        this.mOwnScrollY = i;
        AmbientState ambientState2 = this.mAmbientState;
        ambientState2.getClass();
        ambientState2.mScrollY = Math.max(i, 0);
        Consumer consumer = this.mScrollListener;
        if (consumer != null) {
            consumer.accept(Integer.valueOf(this.mOwnScrollY));
        }
        Consumer consumer2 = this.mScrollChangedConsumer;
        if (consumer2 != null) {
            consumer2.accept(Integer.valueOf(this.mOwnScrollY));
        }
        updateForwardAndBackwardScrollability();
        requestChildrenUpdate();
        updateStackPosition(z);
    }

    public void inflateFooterView() {
    }
}
