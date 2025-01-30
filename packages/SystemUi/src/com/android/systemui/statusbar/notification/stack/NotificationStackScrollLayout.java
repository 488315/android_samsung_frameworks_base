package com.android.systemui.statusbar.notification.stack;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeAnimator;
import android.animation.ValueAnimator;
import android.app.SemWallpaperColors;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Trace;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.AttributeSet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.MathUtils;
import android.util.Pair;
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
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat$$ExternalSyntheticLambda0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.policy.SystemBarUtils;
import com.android.keyguard.BouncerPanelExpansionCalculator;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.ExpandHelper;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.QpRune;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.LaunchAnimator;
import com.android.systemui.animation.ShadeInterpolation;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.noticenter.NotiCenterPlugin;
import com.android.systemui.notification.FullExpansionPanelNotiAlphaController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.qs.InterfaceC1922QS;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda2;
import com.android.systemui.shade.QuickSettingsController;
import com.android.systemui.shade.QuickSettingsController$$ExternalSyntheticLambda5;
import com.android.systemui.shade.SecPanelExpansionStateNotifier;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.statusbar.EmptyShadeView;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.NotificationShelfController;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.iconsOnly.NotificationIconTransitionController;
import com.android.systemui.statusbar.notification.LaunchAnimationParameters;
import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl;
import com.android.systemui.statusbar.notification.icon.IconPack;
import com.android.systemui.statusbar.notification.logging.NotificationLogger$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.FooterView;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator;
import com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm;
import com.android.systemui.statusbar.notification.stack.StackStateAnimator;
import com.android.systemui.statusbar.notification.stack.StackStateAnimator.C29762;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.HeadsUpTouchHelper;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.phone.SecShelfNotificationIconContainer;
import com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.LargeScreenUtils;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.widget.SystemUIWidgetCallback;
import com.sec.ims.gls.GlsIntent;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceBuilderIterator;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import noticolorpicker.NotificationColorPicker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class NotificationStackScrollLayout extends ViewGroup implements Dumpable, PanelScreenShotLogger.LogProvider {
    public static final /* synthetic */ int $r8$clinit = 0;
    static final float RUBBER_BAND_FACTOR_NORMAL = 0.1f;
    public int mActivePointerId;
    public ActivityStarter mActivityStarter;
    public final ArrayList mAddedHeadsUpChildren;
    public final AmbientState mAmbientState;
    public boolean mAnimateBottomOnLayout;
    public boolean mAnimateNextBackgroundBottom;
    public boolean mAnimateNextBackgroundTop;
    public boolean mAnimateNextSectionBoundsChange;
    public boolean mAnimateNextTopPaddingChange;
    public boolean mAnimateStackYForContentHeightChange;
    public boolean mAnimatedInsets;
    public final ArrayList mAnimationEvents;
    public final HashSet mAnimationFinishedRunnables;
    public boolean mAnimationRunning;
    public boolean mAnimationsEnabled;
    public final Rect mBackgroundAnimationRect;
    public final Paint mBackgroundPaint;
    public final NotificationStackScrollLayout$$ExternalSyntheticLambda3 mBackgroundUpdater;
    public float mBackgroundXFactor;
    public boolean mBackwardScrollable;
    public int mBgColor;
    public final float[] mBgCornerRadii;
    int mBottomInset;
    public int mBottomPadding;
    public int mCachedBackgroundColor;
    public CentralSurfaces mCentralSurfaces;
    public boolean mChangePositionInProgress;
    public boolean mCheckForLeavebehind;
    public boolean mChildTransferInProgress;
    public final ArrayList mChildrenChangingPositions;
    public final HashSet mChildrenToAddAnimated;
    public final ArrayList mChildrenToRemoveAnimated;
    public int mChildrenUpdateCount;
    public boolean mChildrenUpdateRequested;
    public long mChildrenUpdateStartTime;
    public final ViewTreeObserverOnPreDrawListenerC29221 mChildrenUpdater;
    public C2944xbae1b0c2 mClearAllAnimationListener;
    public final boolean mClearAllEnabled;
    public boolean mClearAllInProgress;
    public C2944xbae1b0c2 mClearAllListener;
    public final HashSet mClearTransientViewsWhenFinished;
    public final Rect mClipRect;
    public int mContentHeight;
    public boolean mContinuousBackgroundUpdate;
    public boolean mContinuousShadowUpdate;
    public NotificationStackScrollLayoutController mController;
    public int mCornerRadius;
    public int mCurrentStackHeight;
    public float mDimAmount;
    public ValueAnimator mDimAnimator;
    public final C29333 mDimEndListener;
    public final C29344 mDimUpdateListener;
    public boolean mDimmedNeedsAnimation;
    public boolean mDisallowDismissInThisMotion;
    public boolean mDisallowScrollingInThisMotion;
    public boolean mDismissUsingRowTranslationX;
    public final C29366 mDisplayListener;
    public final DisplayManager mDisplayManager;
    public int mDisplayState;
    public boolean mDontClampNextScroll;
    public boolean mDontReportNextOverScroll;
    public int mDownX;
    public EmptyShadeView mEmptyShadeView;
    public boolean mEverythingNeedsAnimation;
    public final ExpandHelper mExpandHelper;
    public final C293017 mExpandHelperCallback;
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
    public boolean mHasFilteredOutSeenNotifications;
    public boolean mHeadsUpAnimatingAway;
    public HeadsUpAppearanceController mHeadsUpAppearanceController;
    public final C292815 mHeadsUpCallback;
    public final HashSet mHeadsUpChangeAnimations;
    public boolean mHeadsUpGoingAwayAnimationsAllowed;
    public int mHeadsUpInset;
    public boolean mHideSensitiveNeedsAnimation;
    public Interpolator mHideXInterpolator;
    public boolean mHighPriorityBeforeSpeedBump;
    public boolean mInHeadsUpPinnedMode;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public final C29377 mInsetsCallback;
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
    public boolean mJustBackFromOcclude;
    public boolean mKeyguardBypassEnabled;
    public final KeyguardFoldController mKeyguardFoldController;
    public final KeyguardStateController mKeyguardStateController;
    public String mLastGoneCallTrace;
    public int mLastMotionY;
    public float mLastSentAppear;
    public float mLastSentExpandedHeight;
    public LaunchAnimationParameters mLaunchAnimationParams;
    public final Path mLaunchedNotificationClipPath;
    public final float[] mLaunchedNotificationRadii;
    public boolean mLaunchingNotification;
    public boolean mLaunchingNotificationNeedsToBeClipped;
    public float mLinearHideAmount;
    public NotificationLogger$$ExternalSyntheticLambda2 mListener;
    public NotificationStackScrollLogger mLogger;
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
    public long mNumHeadsUp;
    public final C29388 mOnChildHeightChangedListener;
    public final C29399 mOnChildSensitivityChangedListener;
    public NotificationPanelViewController$$ExternalSyntheticLambda2 mOnEmptySpaceClickListener;
    public ExpandableView.OnHeightChangedListener mOnHeightChangedListener;
    public int mOnMeasureCount;
    public long mOnMeasureStartTime;
    public ViewCompat$$ExternalSyntheticLambda0 mOnNotificationRemovedListener;
    public Consumer mOnStackYChanged;
    public boolean mOnlyScrollingInThisMotion;
    public final SecNsslOpaqueBgHelper mOpaqueBgHelper;
    public int mOrientation;
    public final C29355 mOutlineProvider;
    public float mOverScrolledBottomPixels;
    public float mOverScrolledTopPixels;
    public int mOverflingDistance;
    public QuickSettingsController.NsslOverscrollTopChangedListener mOverscrollTopChangedListener;
    public int mOwnScrollY;
    public int mPaddingBetweenElements;
    public final SecPanelExpansionStateNotifier mPanelExpansionStateNotifier;
    public boolean mPanelTracking;
    public boolean mPulsing;
    public boolean mQsExpandedImmediate;
    public float mQsExpansionFraction;
    public boolean mQsFullScreen;
    public ViewGroup mQsHeader;
    public final Rect mQsHeaderBound;
    public int mQsMinHeight;
    public final RunnableC292714 mReclamp;
    public final NotificationStackScrollLayout$$ExternalSyntheticLambda2 mReflingAndAnimateScroll;
    public Rect mRequestedClipBounds;
    public final Path mRoundedClipPath;
    public int mRoundedRectClippingBottom;
    public int mRoundedRectClippingLeft;
    public int mRoundedRectClippingRight;
    public int mRoundedRectClippingTop;
    public final ViewTreeObserverOnPreDrawListenerC29322 mRunningAnimationUpdater;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public final C292310 mScrollAdapter;
    public Consumer mScrollListener;
    public boolean mScrollable;
    public boolean mScrolledToTopOnFirstDown;
    public OverScroller mScroller;
    public boolean mScrollingEnabled;
    public final NotificationSection[] mSections;
    public final NotificationSectionsManager mSectionsManager;
    public ShadeController mShadeController;
    public boolean mShadeNeedsToClose;
    public final NotificationStackScrollLayout$$ExternalSyntheticLambda3 mShadowUpdater;
    public NotificationShelf mShelf;
    public boolean mShelfAlphaInAnimating;
    public boolean mShelfAlphaOutAnimating;
    public NotificationShelfManager mShelfManager;
    public final boolean mShouldDrawNotificationBackground;
    public boolean mShouldMediaPlayerDraggingStarted;
    public boolean mShouldShowShelfOnly;
    public boolean mShouldUseRoundedRectClipping;
    public boolean mShouldUseSplitNotificationShade;
    public int mSidePaddings;
    public final boolean mSimplifiedAppearFraction;
    public float mSlopMultiplier;
    public int mSpeedBumpIndex;
    public boolean mSpeedBumpIndexDirty;
    public final int mSplitShadeMinContentHeight;
    public final StackScrollAlgorithm mStackScrollAlgorithm;
    public float mStackTranslation;
    public final StackStateAnimator mStateAnimator;
    int mStatusBarHeight;
    public int mStatusBarState;
    public NotificationSwipeHelper mSwipeHelper;
    public final ArrayList mSwipedOutViews;
    public final C293118 mSystemUIWidgetCallback;
    public final int[] mTempInt2;
    public final ArrayList mTmpList;
    public final Rect mTmpRect;
    public final ArrayList mTmpSortedChildren;
    public NotificationEntry mTopHeadsUpEntry;
    public int mTopPadding;
    public boolean mTopPaddingNeedsAnimation;
    public float mTopPaddingOverflow;
    public NotificationStackScrollLayoutController.TouchHandler mTouchHandler;
    public boolean mTouchIsClick;
    public int mTouchSlop;
    public int mUpcomingStatusBarState;
    public VelocityTracker mVelocityTracker;
    public final NotificationStackScrollLayout$$ExternalSyntheticLambda4 mViewPositionComparator;
    public int mWaterfallTopInset;
    public boolean mWillExpand;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$10 */
    public final class C292310 {
        public C292310() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$15 */
    public final class C292815 implements HeadsUpTouchHelper.Callback {
        public C292815() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$17 */
    public final class C293017 implements ExpandHelper.Callback {
        public C293017() {
        }

        public final boolean canChildBeExpanded(View view) {
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                if (expandableNotificationRow.isExpandable() && !expandableNotificationRow.areGutsExposed() && (NotificationStackScrollLayout.this.mIsExpanded || !expandableNotificationRow.mIsPinned)) {
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
                    SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.sCurrentScreenID, "QPNE0008", "type", expandableNotificationRow.mIsSummaryWithChildren ? "grouped" : "single", "app", expandableNotificationRow.mEntry.mSbn.getPackageName());
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            animationFilter4.animateDimmed = true;
            animationFilter4.animateZ = true;
            AnimationFilter animationFilter5 = new AnimationFilter();
            animationFilter5.animateZ = true;
            AnimationFilter animationFilter6 = new AnimationFilter();
            animationFilter6.animateDimmed = true;
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
            animationFilter8.animateDimmed = true;
            animationFilter8.animateZ = true;
            animationFilter8.hasDelays = true;
            AnimationFilter animationFilter9 = new AnimationFilter();
            animationFilter9.animateHideSensitive = true;
            AnimationFilter animationFilter10 = new AnimationFilter();
            animationFilter10.animateHeight = true;
            animationFilter10.animateTopInset = true;
            animationFilter10.animateY = true;
            animationFilter10.animateZ = true;
            AnimationFilter animationFilter11 = new AnimationFilter();
            animationFilter11.animateAlpha = true;
            animationFilter11.animateHeight = true;
            animationFilter11.animateTopInset = true;
            animationFilter11.animateY = true;
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
            animationFilter16.animateDimmed = true;
            animationFilter16.animateHideSensitive = true;
            animationFilter16.animateHeight = true;
            animationFilter16.animateTopInset = true;
            animationFilter16.animateY = true;
            animationFilter16.animateZ = true;
            AnimationFilter animationFilter17 = new AnimationFilter();
            animationFilter17.animateHeight = true;
            animationFilter17.animateTopInset = true;
            animationFilter17.animateY = true;
            animationFilter17.animateDimmed = true;
            animationFilter17.animateZ = true;
            animationFilter17.hasDelays = true;
            FILTERS = new AnimationFilter[]{animationFilter, animationFilter2, animationFilter3, animationFilter4, animationFilter5, animationFilter6, animationFilter7, animationFilter8, animationFilter9, animationFilter10, animationFilter11, animationFilter12, animationFilter13, animationFilter14, animationFilter15, animationFilter16, animationFilter17};
            LENGTHS = new int[]{464, 464, 360, 360, 220, 220, 360, 448, 360, 360, 360, 400, 400, 400, 360, 360, 448};
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

    static {
        new PathInterpolator(0.0f, 0.8f, 0.2f, 1.0f);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$1] */
    /* JADX WARN: Type inference failed for: r3v10, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r3v11, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r3v13, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$5] */
    /* JADX WARN: Type inference failed for: r3v14, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$6] */
    /* JADX WARN: Type inference failed for: r3v15, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$7] */
    /* JADX WARN: Type inference failed for: r3v5, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$2] */
    /* JADX WARN: Type inference failed for: r3v7, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$3] */
    /* JADX WARN: Type inference failed for: r3v8, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$4] */
    /* JADX WARN: Type inference failed for: r4v3, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$8] */
    /* JADX WARN: Type inference failed for: r4v4, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$9] */
    /* JADX WARN: Type inference failed for: r4v6, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$14] */
    /* JADX WARN: Type inference failed for: r4v9, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$18] */
    public NotificationStackScrollLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, 0);
        final int i = 0;
        this.mShadeNeedsToClose = false;
        this.mCurrentStackHeight = Integer.MAX_VALUE;
        this.mBackgroundPaint = new Paint();
        this.mActivePointerId = -1;
        this.mBottomInset = 0;
        this.mChildrenToAddAnimated = new HashSet();
        this.mAddedHeadsUpChildren = new ArrayList();
        this.mChildrenToRemoveAnimated = new ArrayList();
        this.mChildrenChangingPositions = new ArrayList();
        this.mFromMoreCardAdditions = new HashSet();
        this.mAnimationEvents = new ArrayList();
        this.mSwipedOutViews = new ArrayList();
        this.mStateAnimator = new StackStateAnimator(this);
        this.mSpeedBumpIndex = -1;
        final int i2 = 1;
        this.mSpeedBumpIndexDirty = true;
        this.mIsExpanded = true;
        this.mChildrenUpdateCount = 1;
        this.mOnMeasureCount = 1;
        this.mChildrenUpdater = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.1
            /* JADX WARN: Code restructure failed: missing block: B:148:0x0280, code lost:
            
                if (r6 == false) goto L137;
             */
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final boolean onPreDraw() {
                StackScrollAlgorithm.SectionProvider sectionProvider;
                float f;
                float f2;
                int i3;
                float f3;
                float f4;
                int i4;
                ExpandableViewState expandableViewState;
                float f5;
                boolean z;
                boolean z2;
                NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                int i5 = notificationStackScrollLayout.mDisplayState;
                if (i5 == 4 || i5 == 3 || notificationStackScrollLayout.getVisibility() == 8) {
                    return true;
                }
                NotificationStackScrollLayout.this.updateForcedScroll();
                NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayout.this;
                if (!notificationStackScrollLayout2.mChildrenToAddAnimated.isEmpty()) {
                    for (int i6 = 0; i6 < notificationStackScrollLayout2.getChildCount(); i6++) {
                        ExpandableView childAtIndex = notificationStackScrollLayout2.getChildAtIndex(i6);
                        if (notificationStackScrollLayout2.mChildrenToAddAnimated.contains(childAtIndex)) {
                            int positionInLinearLayout = notificationStackScrollLayout2.getPositionInLinearLayout(childAtIndex);
                            int intrinsicHeight = (childAtIndex instanceof ExpandableView ? childAtIndex.getIntrinsicHeight() : childAtIndex.getHeight()) + notificationStackScrollLayout2.mPaddingBetweenElements;
                            int i7 = notificationStackScrollLayout2.mOwnScrollY;
                            if (positionInLinearLayout < i7) {
                                notificationStackScrollLayout2.setOwnScrollY(i7 + intrinsicHeight);
                            }
                        }
                    }
                    notificationStackScrollLayout2.clampScrollPosition();
                }
                notificationStackScrollLayout2.mAmbientState.mCurrentScrollVelocity = notificationStackScrollLayout2.mScroller.isFinished() ? 0.0f : notificationStackScrollLayout2.mScroller.getCurrVelocity();
                StackScrollAlgorithm stackScrollAlgorithm = notificationStackScrollLayout2.mStackScrollAlgorithm;
                AmbientState ambientState = notificationStackScrollLayout2.mAmbientState;
                int speedBumpIndex = notificationStackScrollLayout2.getSpeedBumpIndex();
                ViewGroup viewGroup = stackScrollAlgorithm.mHostView;
                int childCount = viewGroup.getChildCount();
                for (int i8 = 0; i8 < childCount; i8++) {
                    ((ExpandableView) viewGroup.getChildAt(i8)).resetViewState();
                }
                int i9 = ambientState.mScrollY;
                StackScrollAlgorithm.StackScrollAlgorithmState stackScrollAlgorithmState = stackScrollAlgorithm.mTempAlgorithmState;
                stackScrollAlgorithmState.getClass();
                float f6 = -i9;
                stackScrollAlgorithmState.mCurrentYPosition = f6;
                stackScrollAlgorithmState.mCurrentExpandedYPosition = f6;
                int childCount2 = viewGroup.getChildCount();
                ArrayList arrayList = stackScrollAlgorithmState.visibleChildren;
                arrayList.clear();
                arrayList.ensureCapacity(childCount2);
                int i10 = 0;
                for (int i11 = 0; i11 < childCount2; i11++) {
                    ExpandableView expandableView = (ExpandableView) viewGroup.getChildAt(i11);
                    if (expandableView.getVisibility() != 8 && expandableView != ambientState.mShelf) {
                        expandableView.mViewState.notGoneIndex = i10;
                        arrayList.add(expandableView);
                        i10++;
                        NotificationIconTransitionController notificationIconTransitionController = (NotificationIconTransitionController) Dependency.get(NotificationIconTransitionController.class);
                        notificationIconTransitionController.getClass();
                        expandableView.mViewState.isAnimatable = notificationIconTransitionController.mChildAnimatable;
                        if (expandableView instanceof ExpandableNotificationRow) {
                            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
                            List<ExpandableNotificationRow> attachedChildren = expandableNotificationRow.getAttachedChildren();
                            if (expandableNotificationRow.mIsSummaryWithChildren && attachedChildren != null) {
                                for (ExpandableNotificationRow expandableNotificationRow2 : attachedChildren) {
                                    if (expandableNotificationRow2.getVisibility() != 8) {
                                        expandableNotificationRow2.mViewState.notGoneIndex = i10;
                                        i10++;
                                    }
                                }
                            }
                        }
                    }
                }
                ((NotificationIconTransitionController) Dependency.get(NotificationIconTransitionController.class)).mChildAnimatable = true;
                float f7 = -ambientState.mScrollY;
                boolean isOnKeyguard = ambientState.isOnKeyguard();
                StackScrollAlgorithm.BypassController bypassController = ambientState.mBypassController;
                if (!isOnKeyguard || (((KeyguardBypassController) bypassController).getBypassEnabled() && ambientState.isPulseExpanding())) {
                    f7 += 0.0f;
                }
                stackScrollAlgorithmState.firstViewInShelf = null;
                int i12 = 0;
                while (true) {
                    int size = arrayList.size();
                    sectionProvider = ambientState.mSectionProvider;
                    if (i12 >= size) {
                        break;
                    }
                    ExpandableView expandableView2 = (ExpandableView) arrayList.get(i12);
                    if (StackScrollAlgorithm.childNeedsGapHeight(sectionProvider, i12, expandableView2, i12 > 0 ? (ExpandableView) arrayList.get(i12 - 1) : null)) {
                        f7 += stackScrollAlgorithm.getGapForLocation(ambientState.mFractionToShade, ambientState.isOnKeyguard());
                    }
                    if (ambientState.mShelf != null && f7 >= (ambientState.mStackEndHeight - r8.getHeight()) - stackScrollAlgorithm.mPaddingBetweenElements && !(expandableView2 instanceof FooterView) && stackScrollAlgorithmState.firstViewInShelf == null) {
                        stackScrollAlgorithmState.firstViewInShelf = expandableView2;
                    }
                    f7 = f7 + expandableView2.getIntrinsicHeight() + stackScrollAlgorithm.mPaddingBetweenElements;
                    i12++;
                }
                if (!ambientState.isOnKeyguard() || (((KeyguardBypassController) bypassController).getBypassEnabled() && ambientState.isPulseExpanding())) {
                    stackScrollAlgorithmState.mCurrentYPosition += 0.0f;
                    stackScrollAlgorithmState.mCurrentExpandedYPosition += 0.0f;
                    f = 0.0f;
                } else {
                    f = 0.0f;
                }
                int size2 = arrayList.size();
                stackScrollAlgorithm.mGroupExpandInterpolationY = f;
                int i13 = 0;
                while (i13 < size2) {
                    ExpandableView expandableView3 = (ExpandableView) arrayList.get(i13);
                    ExpandableViewState expandableViewState2 = expandableView3.mViewState;
                    expandableViewState2.location = 0;
                    if (expandableView3 instanceof NotificationShelf) {
                        stackScrollAlgorithmState.mCurrentYPosition -= stackScrollAlgorithm.mGroupExpandInterpolationY;
                    }
                    boolean childNeedsGapHeight = StackScrollAlgorithm.childNeedsGapHeight(sectionProvider, i13, expandableView3, i13 > 0 ? (ExpandableView) arrayList.get(i13 - 1) : null);
                    if (!ambientState.isOnKeyguard() && childNeedsGapHeight) {
                        float gapForLocation = stackScrollAlgorithm.getGapForLocation(ambientState.mFractionToShade, ambientState.isOnKeyguard());
                        stackScrollAlgorithmState.mCurrentYPosition = (1.0f * gapForLocation) + stackScrollAlgorithmState.mCurrentYPosition;
                        stackScrollAlgorithmState.mCurrentExpandedYPosition += gapForLocation;
                    }
                    expandableViewState2.setYTranslation(stackScrollAlgorithmState.mCurrentYPosition);
                    int i14 = size2;
                    StackScrollAlgorithm.SectionProvider sectionProvider2 = sectionProvider;
                    StackScrollAlgorithm.BypassController bypassController2 = bypassController;
                    stackScrollAlgorithm.maybeUpdateHeadsUpIsVisible(expandableViewState2, ambientState.mShadeExpanded, expandableView3.mustStayOnScreen(), expandableViewState2.mYTranslation >= 0.0f, expandableViewState2.mYTranslation + expandableViewState2.height + ambientState.mStackY, ambientState.mMaxHeadsUpTranslation);
                    if (expandableView3 instanceof FooterView) {
                        boolean z3 = !ambientState.mShadeExpanded;
                        boolean z4 = stackScrollAlgorithmState.firstViewInShelf != null;
                        if (z3) {
                            expandableViewState2.hidden = true;
                        } else {
                            boolean z5 = stackScrollAlgorithmState.mCurrentExpandedYPosition + ((float) expandableView3.getIntrinsicHeight()) > ambientState.mStackEndHeight;
                            FooterView.FooterViewState footerViewState = (FooterView.FooterViewState) expandableViewState2;
                            if (!z4 && !z5) {
                                if (ambientState.mClearAllInProgress) {
                                    int i15 = 0;
                                    while (true) {
                                        if (i15 >= arrayList.size()) {
                                            z2 = false;
                                            break;
                                        }
                                        View view = (View) arrayList.get(i15);
                                        if ((view instanceof ExpandableNotificationRow) && !((ExpandableNotificationRow) view).canViewBeDismissed$1()) {
                                            z2 = true;
                                            break;
                                        }
                                        i15++;
                                    }
                                }
                                z = false;
                                footerViewState.hideContent = z;
                            }
                            z = true;
                            footerViewState.hideContent = z;
                        }
                    } else {
                        if ((expandableView3 instanceof EmptyShadeView) && !NotiRune.NOTI_STYLE_EMPTY_SHADE) {
                            expandableViewState2.setYTranslation((((ambientState.mLayoutMaxHeight + stackScrollAlgorithm.mMarginBottom) - ambientState.mStackY) - expandableView3.getIntrinsicHeight()) / 2.0f);
                        } else if (expandableView3 != ambientState.getTrackedHeadsUpRow()) {
                            if (ambientState.mExpansionChanging) {
                                expandableViewState2.hidden = false;
                                ExpandableView expandableView4 = stackScrollAlgorithmState.firstViewInShelf;
                                expandableViewState2.inShelf = expandableView4 != null && i13 >= arrayList.indexOf(expandableView4);
                            } else if (ambientState.mShelf != null) {
                                stackScrollAlgorithm.updateViewWithShelf(expandableView3, expandableViewState2, (((!ambientState.mShadeExpanded || ambientState.mDozeAmount == 1.0f || (((KeyguardBypassController) bypassController2).getBypassEnabled() && ambientState.isOnKeyguard() && !ambientState.isPulseExpanding())) ? ambientState.getInnerHeight$1() : ambientState.mStackHeight) - ambientState.mShelf.getHeight()) - 0.0f);
                            }
                        }
                        expandableViewState2.height = expandableView3.getIntrinsicHeight();
                        if (!expandableView3.isPinned() && !expandableView3.isHeadsUpAnimatingAway()) {
                            if (!(ambientState.mPulsingRow == expandableView3)) {
                                expandableViewState2.height = (int) (expandableViewState2.height * 1.0f);
                            }
                        }
                    }
                    float interpolate = NotificationUtils.interpolate(0.0f, stackScrollAlgorithm.mMaxGroupExpandedBottomGap, StackScrollAlgorithm.getPreviousGroupExpandFraction(expandableView3));
                    stackScrollAlgorithm.mGroupExpandInterpolationY = interpolate;
                    stackScrollAlgorithmState.mCurrentYPosition = ((expandableView3.getIntrinsicHeight() + stackScrollAlgorithm.mPaddingBetweenElements) * 1.0f) + interpolate + stackScrollAlgorithmState.mCurrentYPosition;
                    stackScrollAlgorithmState.mCurrentExpandedYPosition = expandableView3.getIntrinsicHeight() + stackScrollAlgorithm.mPaddingBetweenElements + stackScrollAlgorithmState.mCurrentExpandedYPosition;
                    ExpandableViewState expandableViewState3 = expandableView3.mViewState;
                    float f8 = stackScrollAlgorithmState.mCurrentYPosition;
                    expandableViewState3.location = 4;
                    if (f8 <= 0.0f) {
                        expandableViewState3.location = 2;
                    }
                    expandableViewState2.setYTranslation(expandableViewState2.mYTranslation + ambientState.mStackY);
                    i13++;
                    bypassController = bypassController2;
                    sectionProvider = sectionProvider2;
                    size2 = i14;
                }
                int size3 = arrayList.size();
                int i16 = 0;
                while (true) {
                    if (i16 >= size3) {
                        i16 = -1;
                        break;
                    }
                    ExpandableView expandableView5 = (ExpandableView) arrayList.get(i16);
                    if ((expandableView5 instanceof ActivatableNotificationView) && (expandableView5.isAboveShelf() || expandableView5.showingPulsing())) {
                        break;
                    }
                    i16++;
                }
                int i17 = size3 - 1;
                float f9 = 0.0f;
                while (i17 >= 0) {
                    boolean z6 = i17 == i16;
                    ExpandableView expandableView6 = (ExpandableView) arrayList.get(i17);
                    ExpandableViewState expandableViewState4 = expandableView6.mViewState;
                    float f10 = 0;
                    if (expandableView6.mustStayOnScreen() && !expandableViewState4.headsUpIsVisible && !ambientState.isDozingAndNotPulsing(expandableView6)) {
                        float f11 = expandableViewState4.mYTranslation;
                        float f12 = ambientState.mTopPadding + ambientState.mStackTranslation;
                        if (f11 < f12) {
                            f9 = f9 != 0.0f ? f9 + 1.0f : f9 + Math.min(1.0f, (f12 - f11) / expandableViewState4.height);
                            expandableViewState4.setZTranslation((stackScrollAlgorithm.mPinnedZTranslationExtra * f9) + f10);
                            expandableViewState4.setZTranslation(((1.0f - expandableView6.getHeaderVisibleAmount()) * stackScrollAlgorithm.mPinnedZTranslationExtra) + expandableViewState4.mZTranslation);
                            i17--;
                        }
                    }
                    if (z6) {
                        NotificationShelf notificationShelf = ambientState.mShelf;
                        int height = notificationShelf == null ? 0 : notificationShelf.getHeight();
                        float innerHeight$1 = (ambientState.getInnerHeight$1() - height) + ambientState.mTopPadding + ambientState.mStackTranslation;
                        float intrinsicHeight2 = expandableViewState4.mYTranslation + expandableView6.getIntrinsicHeight() + stackScrollAlgorithm.mPaddingBetweenElements;
                        if (innerHeight$1 > intrinsicHeight2) {
                            expandableViewState4.setZTranslation(f10);
                        } else {
                            float f13 = (intrinsicHeight2 - innerHeight$1) / height;
                            if (Float.isNaN(f13)) {
                                f5 = 1.0f;
                                f13 = 1.0f;
                            } else {
                                f5 = 1.0f;
                            }
                            expandableViewState4.setZTranslation((Math.min(f13, f5) * stackScrollAlgorithm.mPinnedZTranslationExtra) + f10);
                        }
                    } else {
                        expandableViewState4.setZTranslation(f10);
                    }
                    expandableViewState4.setZTranslation(((1.0f - expandableView6.getHeaderVisibleAmount()) * stackScrollAlgorithm.mPinnedZTranslationExtra) + expandableViewState4.mZTranslation);
                    i17--;
                }
                int size4 = arrayList.size();
                float f14 = stackScrollAlgorithm.mHeadsUpInset - ambientState.mStackTopMargin;
                ExpandableNotificationRow trackedHeadsUpRow = ambientState.getTrackedHeadsUpRow();
                if (trackedHeadsUpRow != null && (expandableViewState = trackedHeadsUpRow.mViewState) != null) {
                    expandableViewState.setYTranslation(MathUtils.lerp(f14, expandableViewState.mYTranslation - ambientState.mStackTranslation, ambientState.mAppearFraction));
                }
                ExpandableNotificationRow expandableNotificationRow3 = null;
                for (int i18 = 0; i18 < size4; i18++) {
                    View view2 = (View) arrayList.get(i18);
                    if (view2 instanceof ExpandableNotificationRow) {
                        ExpandableNotificationRow expandableNotificationRow4 = (ExpandableNotificationRow) view2;
                        if (expandableNotificationRow4.mIsHeadsUp || expandableNotificationRow4.mHeadsupDisappearRunning) {
                            ExpandableViewState expandableViewState5 = expandableNotificationRow4.mViewState;
                            if (expandableNotificationRow3 == null && expandableNotificationRow4.mustStayOnScreen() && !expandableViewState5.headsUpIsVisible) {
                                expandableViewState5.location = 1;
                                expandableNotificationRow3 = expandableNotificationRow4;
                            }
                            boolean z7 = expandableNotificationRow3 == expandableNotificationRow4;
                            float f15 = expandableViewState5.mYTranslation + expandableViewState5.height;
                            if (stackScrollAlgorithm.mIsExpanded && expandableNotificationRow4.mustStayOnScreen() && !expandableViewState5.headsUpIsVisible) {
                                expandableNotificationRow4.showingPulsing();
                            }
                            if (expandableNotificationRow4.mIsPinned) {
                                expandableViewState5.setYTranslation(Math.max(expandableViewState5.mYTranslation, f14));
                                expandableViewState5.height = Math.max(expandableNotificationRow4.getIntrinsicHeight(), expandableViewState5.height);
                                expandableViewState5.hidden = false;
                                ExpandableViewState expandableViewState6 = expandableNotificationRow3 == null ? null : expandableNotificationRow3.mViewState;
                                if (expandableViewState6 != null && !z7 && (!stackScrollAlgorithm.mIsExpanded || f15 > expandableViewState6.mYTranslation + expandableViewState6.height)) {
                                    expandableViewState5.height = expandableNotificationRow4.getIntrinsicHeight();
                                }
                                if (!stackScrollAlgorithm.mIsExpanded && z7 && (i4 = ambientState.mScrollY) > 0) {
                                    expandableViewState5.setYTranslation(expandableViewState5.mYTranslation - i4);
                                }
                            }
                            if (expandableNotificationRow4.mHeadsupDisappearRunning && !stackScrollAlgorithm.mIsExpanded && z7) {
                                expandableViewState5.setYTranslation(Math.max(expandableViewState5.mYTranslation, f14));
                                expandableViewState5.hidden = false;
                            }
                        }
                    }
                }
                stackScrollAlgorithm.updatePulsingStates(stackScrollAlgorithmState, ambientState);
                boolean z8 = ambientState.mDimmed && !(ambientState.isPulseExpanding() && ambientState.mDozeAmount == 1.0f);
                boolean z9 = ambientState.mHideSensitive;
                int size5 = arrayList.size();
                for (int i19 = 0; i19 < size5; i19++) {
                    ExpandableViewState expandableViewState7 = ((ExpandableView) arrayList.get(i19)).mViewState;
                    expandableViewState7.dimmed = z8;
                    expandableViewState7.hideSensitive = z9;
                }
                float f16 = ambientState.isOnKeyguard() ? 0.0f : ambientState.mStackY - ambientState.mScrollY;
                float f17 = ambientState.mNotificationScrimTop;
                int size6 = arrayList.size();
                int i20 = 0;
                boolean z10 = true;
                float f18 = 0.0f;
                while (i20 < size6) {
                    ExpandableView expandableView7 = (ExpandableView) arrayList.get(i20);
                    ExpandableViewState expandableViewState8 = expandableView7.mViewState;
                    if (!expandableView7.mustStayOnScreen() || expandableViewState8.headsUpIsVisible) {
                        f17 = Math.max(f16, f17);
                    }
                    float f19 = expandableViewState8.mYTranslation;
                    float f20 = expandableViewState8.height + f19;
                    float f21 = f16;
                    boolean z11 = (expandableView7 instanceof ExpandableNotificationRow) && expandableView7.isPinned();
                    int i21 = size6;
                    if (!stackScrollAlgorithm.mClipNotificationScrollToTop || z10 || ((!z11 && (!expandableView7.isHeadsUpAnimatingAway() || z10)) || f20 <= f18 || ambientState.mShadeExpanded)) {
                        f4 = f18;
                        expandableViewState8.clipBottomAmount = 0;
                    } else {
                        f4 = f18;
                        expandableViewState8.clipBottomAmount = stackScrollAlgorithm.mEnableNotificationClipping ? (int) (f20 - f18) : 0;
                    }
                    if (expandableViewState8.inShelf || f19 >= f17) {
                        expandableViewState8.clipTopAmount = 0;
                    } else {
                        expandableViewState8.clipTopAmount = (int) (f17 - f19);
                    }
                    f18 = z10 ? f20 : f4;
                    if (z11) {
                        z10 = false;
                    }
                    if (!expandableView7.isTransparent()) {
                        if (!z11) {
                            f19 = f20;
                        }
                        f17 = Math.max(f17, f19);
                    }
                    i20++;
                    f16 = f21;
                    size6 = i21;
                }
                int size7 = arrayList.size();
                int i22 = 0;
                while (i22 < size7) {
                    ((ExpandableView) arrayList.get(i22)).mViewState.belowSpeedBump = i22 >= speedBumpIndex;
                    i22++;
                }
                NotificationShelf notificationShelf2 = ambientState.mShelf;
                if (notificationShelf2 != null) {
                    ExpandableView expandableView8 = ambientState.mLastVisibleBackgroundChild;
                    NotificationShelf.ShelfState shelfState = (NotificationShelf.ShelfState) notificationShelf2.mViewState;
                    if (expandableView8 == null) {
                        shelfState.setYTranslation(ambientState.mTopPadding + ambientState.mStackTranslation);
                    }
                    if (!notificationShelf2.mShowNotificationShelf || expandableView8 == null) {
                        shelfState.hidden = true;
                        shelfState.location = 64;
                        shelfState.hasItemsInStableShelf = false;
                    } else {
                        ExpandableViewState expandableViewState9 = expandableView8.mViewState;
                        if (expandableViewState9 != null) {
                            float f22 = expandableViewState9.mYTranslation + expandableViewState9.height;
                            if (notificationShelf2.mShelfManager.statusBarState != 1) {
                                i3 = 0;
                                f3 = shelfState.height + 0;
                            } else {
                                i3 = 0;
                                f3 = 0.0f;
                            }
                            float f23 = f22 + f3;
                            shelfState.copyFrom(expandableViewState9);
                            shelfState.height = notificationShelf2.getHeight();
                            shelfState.setZTranslation(i3);
                            shelfState.clipTopAmount = i3;
                            if (!ambientState.mExpansionChanging || ambientState.isOnKeyguard()) {
                                shelfState.setAlpha(1.0f - ambientState.mHideAmount);
                                SecShelfNotificationIconContainer secShelfNotificationIconContainer = notificationShelf2.mShelfIcons;
                                float f24 = 1.0f - ambientState.mHideAmount;
                                TextView textView = secShelfNotificationIconContainer.mMoreView;
                                if (textView != null && textView.getVisibility() == 0) {
                                    secShelfNotificationIconContainer.mMoreView.setAlpha(f24);
                                }
                            } else {
                                float f25 = ambientState.mExpansionFraction;
                                StatusBarKeyguardViewManager statusBarKeyguardViewManager = ambientState.mStatusBarKeyguardViewManager;
                                if (statusBarKeyguardViewManager != null && statusBarKeyguardViewManager.isPrimaryBouncerInTransit()) {
                                    shelfState.setAlpha(BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(f25));
                                } else {
                                    if (!ambientState.mIsSmallScreen) {
                                        if (((FeatureFlagsRelease) ambientState.mFeatureFlags).isEnabled(Flags.LARGE_SHADE_GRANULAR_ALPHA_INTERPOLATION)) {
                                            shelfState.setAlpha(ambientState.mLargeScreenShadeInterpolator.getNotificationContentAlpha(f25));
                                        }
                                    }
                                    shelfState.setAlpha(ShadeInterpolation.getContentAlpha(f25));
                                }
                            }
                            if (notificationShelf2.mShelfRefactorFlagEnabled) {
                                throw null;
                            }
                            shelfState.belowSpeedBump = notificationShelf2.mHostLayoutController.mView.getSpeedBumpIndex() == 0;
                            shelfState.hideSensitive = false;
                            shelfState.setXTranslation(notificationShelf2.getTranslationX());
                            shelfState.hasItemsInStableShelf = expandableViewState9.inShelf;
                            shelfState.firstViewInShelf = stackScrollAlgorithmState.firstViewInShelf;
                            int i23 = notificationShelf2.mNotGoneIndex;
                            if (i23 != -1) {
                                shelfState.notGoneIndex = Math.min(shelfState.notGoneIndex, i23);
                            }
                            if (!shelfState.hasItemsInStableShelf && ambientState.isOnKeyguard()) {
                                if ((notificationShelf2.getTranslationY() - expandableViewState9.mYTranslation) / Math.min(Math.min(notificationShelf2.getHeight() * 1.5f, expandableView8.mActualHeight + 0), expandableView8.getMinHeight(false) - notificationShelf2.getHeight()) < 0.5f) {
                                    shelfState.hasItemsInStableShelf = true;
                                }
                            }
                            AmbientState ambientState2 = notificationShelf2.mAmbientState;
                            shelfState.hidden = !ambientState2.mShadeExpanded || (ambientState2.mIsSwipingUp && ambientState2.mIsFullyExpanding) || ((ambientState2.mIsClosing && ambientState2.mExpansionChanging) || (ambientState2.mExpansionChanging && ambientState2.mIsExpandAnimating));
                            int indexOf = arrayList.indexOf(stackScrollAlgorithmState.firstViewInShelf);
                            if (notificationShelf2.mAmbientState.mExpansionChanging && stackScrollAlgorithmState.firstViewInShelf != null && indexOf > 0 && ((ExpandableView) arrayList.get(indexOf - 1)).mViewState.hidden) {
                                shelfState.hidden = true;
                            }
                            float f26 = ambientState.mStackY + ambientState.mStackHeight;
                            float innerHeight$12 = ambientState.getInnerHeight$1() + ambientState.mTopPadding + ambientState.mStackTranslation;
                            if (notificationShelf2.getResources().getConfiguration().orientation == 2 && ambientState.mNotificationScrimTop + shelfState.height > f26) {
                                shelfState.hidden = true;
                            }
                            shelfState.setYTranslation(Math.min(f23, innerHeight$12) - shelfState.height);
                        }
                    }
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ExpandableView expandableView9 = (ExpandableView) it.next();
                    ExpandableViewState expandableViewState10 = expandableView9.mViewState;
                    if (ambientState.mShadeExpanded && expandableView9 == ambientState.getTrackedHeadsUpRow()) {
                        expandableViewState10.setAlpha(1.0f);
                    } else if (ambientState.isOnKeyguard()) {
                        expandableViewState10.setAlpha(1.0f - ambientState.mHideAmount);
                    } else if (ambientState.mExpansionChanging) {
                        float f27 = ambientState.mExpansionFraction;
                        if (!NotiRune.NOTI_STYLE_TABLET_BG && !expandableView9.mustStayOnScreen()) {
                            int indexOf2 = arrayList.indexOf(expandableView9);
                            int size8 = arrayList.size();
                            float f28 = ambientState.mNotificationTopRatio;
                            ShadeInterpolation shadeInterpolation = ShadeInterpolation.INSTANCE;
                            if (9 <= indexOf2) {
                                indexOf2 = 9;
                            }
                            if (20 <= size8) {
                                size8 = 20;
                            }
                            float f29 = (indexOf2 * ((0.95f - f28) / size8)) + f28;
                            float f30 = 0.95f > f29 ? f29 : 0.95f;
                            float f31 = ((size8 - indexOf2) - 1) * 0.005f;
                            if (0.045f <= f31) {
                                f31 = 0.045f;
                            }
                            float f32 = 1;
                            float f33 = (f32 - f30) - f31;
                            if (f27 < f30) {
                                f2 = 0.0f;
                            } else if (f27 > f32 - f31) {
                                f2 = 1.0f;
                            } else {
                                f2 = (!((f27 > 1.0f ? 1 : (f27 == 1.0f ? 0 : -1)) == 0) || f31 >= 0.0f) ? (f27 - f30) / f33 : 1.0f;
                            }
                            expandableViewState10.setAlpha(f2);
                        }
                    }
                    if ((expandableView9 instanceof EmptyShadeView) && ambientState.isOnKeyguard()) {
                        expandableViewState10.setAlpha(ShadeInterpolation.getContentAlpha(ambientState.mFractionToShade));
                    }
                    NotificationShelf notificationShelf3 = ambientState.mShelf;
                    if (notificationShelf3 != null) {
                        ExpandableViewState expandableViewState11 = notificationShelf3.mViewState;
                        if (!expandableViewState11.hidden) {
                            float f34 = expandableViewState11.mYTranslation;
                            float f35 = expandableViewState10.mYTranslation;
                            boolean z12 = expandableViewState10.inShelf && ambientState.isOnKeyguard();
                            if (f35 >= f34 || z12) {
                                expandableViewState10.setAlpha(0.0f);
                            }
                        }
                    }
                }
                StackScrollAlgorithm.getNotificationChildrenStates(stackScrollAlgorithmState);
                if (notificationStackScrollLayout2.mAnimateNextTopPaddingChange) {
                    StringBuilder sb = new StringBuilder("mAnimateNextTopPaddingChange : updateChildren ");
                    sb.append(!notificationStackScrollLayout2.mStateAnimator.mAnimatorSet.isEmpty());
                    sb.append(" : ");
                    ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, notificationStackScrollLayout2.mNeedsAnimation, "StackScroller");
                }
                if ((!notificationStackScrollLayout2.mStateAnimator.mAnimatorSet.isEmpty()) || notificationStackScrollLayout2.mNeedsAnimation) {
                    notificationStackScrollLayout2.startAnimationToState$1();
                } else {
                    notificationStackScrollLayout2.applyCurrentState();
                }
                ((NotificationIconTransitionController) Dependency.get(NotificationIconTransitionController.class)).mNeedAnimForRemoval = true;
                notificationStackScrollLayout2.mController.mProgressingShadeLockedFromNotiIcon = false;
                NotificationStackScrollLayout notificationStackScrollLayout3 = NotificationStackScrollLayout.this;
                notificationStackScrollLayout3.mChildrenUpdateRequested = false;
                notificationStackScrollLayout3.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
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
                NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                notificationStackScrollLayout.mShelf.updateAppearance();
                notificationStackScrollLayout.updateClippingToTopRoundedCorner();
                if (notificationStackScrollLayout.mNeedsAnimation || notificationStackScrollLayout.mChildrenUpdateRequested) {
                    return true;
                }
                notificationStackScrollLayout.updateBackground();
                return true;
            }
        };
        this.mTmpSortedChildren = new ArrayList();
        this.mDimEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                NotificationStackScrollLayout.this.mDimAnimator = null;
            }
        };
        this.mDimUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                int i3 = NotificationStackScrollLayout.$r8$clinit;
                notificationStackScrollLayout.mDimAmount = floatValue;
                notificationStackScrollLayout.updateBackgroundDimming();
            }
        };
        this.mQsHeaderBound = new Rect();
        this.mShadowUpdater = new ViewTreeObserver.OnPreDrawListener(this) { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda3
            public final /* synthetic */ NotificationStackScrollLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                switch (i) {
                    case 0:
                        NotificationStackScrollLayout notificationStackScrollLayout = this.f$0;
                        int i3 = NotificationStackScrollLayout.$r8$clinit;
                        notificationStackScrollLayout.updateViewShadows();
                        break;
                    default:
                        NotificationStackScrollLayout notificationStackScrollLayout2 = this.f$0;
                        int i4 = NotificationStackScrollLayout.$r8$clinit;
                        notificationStackScrollLayout2.updateBackground();
                        break;
                }
                return true;
            }
        };
        this.mBackgroundUpdater = new ViewTreeObserver.OnPreDrawListener(this) { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda3
            public final /* synthetic */ NotificationStackScrollLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                switch (i2) {
                    case 0:
                        NotificationStackScrollLayout notificationStackScrollLayout = this.f$0;
                        int i3 = NotificationStackScrollLayout.$r8$clinit;
                        notificationStackScrollLayout.updateViewShadows();
                        break;
                    default:
                        NotificationStackScrollLayout notificationStackScrollLayout2 = this.f$0;
                        int i4 = NotificationStackScrollLayout.$r8$clinit;
                        notificationStackScrollLayout2.updateBackground();
                        break;
                }
                return true;
            }
        };
        this.mViewPositionComparator = new NotificationStackScrollLayout$$ExternalSyntheticLambda4();
        this.mOutlineProvider = new ViewOutlineProvider() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.5
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
                int i3 = notificationStackScrollLayout2.mCornerRadius;
                outline.setRoundRect(rect, MathUtils.lerp(i3 / 2.0f, i3, interpolation));
                outline.setAlpha(1.0f - NotificationStackScrollLayout.this.mAmbientState.mHideAmount);
            }
        };
        this.mDisplayListener = new DisplayManager.DisplayListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.6
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayChanged(int i3) {
                if (i3 != 0) {
                    NotificationStackScrollLayout.this.mDisplayState = 0;
                    return;
                }
                Display display = NotificationStackScrollLayout.this.mDisplayManager.getDisplay(i3);
                NotificationStackScrollLayout.this.mDisplayState = display.getState();
                RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("onDisplayChanged for predraw skip to "), NotificationStackScrollLayout.this.mDisplayState, "StackScroller");
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayAdded(int i3) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayRemoved(int i3) {
            }
        };
        this.mInsetsCallback = new WindowInsetsAnimation.Callback(i2) { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.7
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
                int i3 = NotificationStackScrollLayout.$r8$clinit;
                notificationStackScrollLayout.updateBottomInset(windowInsets);
                return windowInsets;
            }
        };
        this.mInterpolatedHideAmount = 0.0f;
        this.mLinearHideAmount = 0.0f;
        this.mBackgroundXFactor = 1.0f;
        this.mMaxDisplayedNotifications = -1;
        this.mClipRect = new Rect();
        this.mHeadsUpGoingAwayAnimationsAllowed = true;
        this.mReflingAndAnimateScroll = new NotificationStackScrollLayout$$ExternalSyntheticLambda2(this, i2);
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
        this.mShouldMediaPlayerDraggingStarted = false;
        this.mOnChildHeightChangedListener = new ExpandableView.OnHeightChangedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.8
            @Override // com.android.systemui.statusbar.notification.row.ExpandableView.OnHeightChangedListener
            public final void onHeightChanged(ExpandableView expandableView, boolean z) {
                NotificationStackScrollLayout.this.onChildHeightChanged(expandableView, z);
            }

            @Override // com.android.systemui.statusbar.notification.row.ExpandableView.OnHeightChangedListener
            public final void onReset(ExpandableNotificationRow expandableNotificationRow) {
                NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                expandableNotificationRow.setAnimationRunning((notificationStackScrollLayout.mAnimationsEnabled || notificationStackScrollLayout.mPulsing) && (notificationStackScrollLayout.mIsExpanded || NotificationStackScrollLayout.isPinnedHeadsUp(expandableNotificationRow)));
                expandableNotificationRow.setChronometerRunning(notificationStackScrollLayout.mIsExpanded);
            }
        };
        this.mOnChildSensitivityChangedListener = new NotificationEntry.OnSensitivityChangedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.9
            @Override // com.android.systemui.statusbar.notification.collection.NotificationEntry.OnSensitivityChangedListener
            public final void onSensitivityChanged(NotificationEntry notificationEntry) {
                NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                if (notificationStackScrollLayout.mAnimationsEnabled) {
                    notificationStackScrollLayout.mHideSensitiveNeedsAnimation = true;
                    notificationStackScrollLayout.requestChildrenUpdate();
                }
            }
        };
        this.mScrollAdapter = new C292310();
        this.mReclamp = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.14
            @Override // java.lang.Runnable
            public final void run() {
                NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                int i3 = NotificationStackScrollLayout.$r8$clinit;
                int scrollRange = notificationStackScrollLayout.getScrollRange();
                NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayout.this;
                OverScroller overScroller = notificationStackScrollLayout2.mScroller;
                int i4 = ((ViewGroup) notificationStackScrollLayout2).mScrollX;
                int i5 = NotificationStackScrollLayout.this.mOwnScrollY;
                overScroller.startScroll(i4, i5, 0, scrollRange - i5);
                NotificationStackScrollLayout notificationStackScrollLayout3 = NotificationStackScrollLayout.this;
                notificationStackScrollLayout3.mDontReportNextOverScroll = true;
                notificationStackScrollLayout3.mDontClampNextScroll = true;
                notificationStackScrollLayout3.animateScroll();
            }
        };
        this.mHeadsUpCallback = new C292815();
        this.mExpandHelperCallback = new C293017();
        this.mSystemUIWidgetCallback = new SystemUIWidgetCallback() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.18
            @Override // com.android.systemui.widget.SystemUIWidgetCallback
            public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
                if ((j & 512) != 0) {
                    NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                    int childCount = notificationStackScrollLayout.getChildCount();
                    for (int i3 = 0; i3 < childCount; i3++) {
                        View childAt = notificationStackScrollLayout.getChildAt(i3);
                        if (childAt instanceof ExpandableNotificationRow) {
                            NotificationStackScrollLayout.updateNotification((ExpandableNotificationRow) childAt);
                        }
                    }
                }
            }
        };
        Resources resources = getResources();
        FeatureFlags featureFlags = (FeatureFlags) Dependency.get(FeatureFlags.class);
        Flags flags = Flags.INSTANCE;
        featureFlags.getClass();
        FeatureFlagsRelease featureFlagsRelease = (FeatureFlagsRelease) featureFlags;
        this.mSimplifiedAppearFraction = featureFlagsRelease.isEnabled(Flags.SIMPLIFIED_APPEAR_FRACTION);
        setAnimatedInsetsEnabled(featureFlagsRelease.isEnabled(Flags.ANIMATED_NOTIFICATION_SHADE_INSETS));
        NotificationSectionsManager notificationSectionsManager = (NotificationSectionsManager) Dependency.get(NotificationSectionsManager.class);
        this.mSectionsManager = notificationSectionsManager;
        this.mScreenOffAnimationController = (ScreenOffAnimationController) Dependency.get(ScreenOffAnimationController.class);
        updateSplitNotificationShade();
        if (!(!notificationSectionsManager.initialized)) {
            throw new IllegalStateException("NotificationSectionsManager already initialized".toString());
        }
        notificationSectionsManager.initialized = true;
        notificationSectionsManager.parent = this;
        notificationSectionsManager.reinflateViews();
        ((ConfigurationControllerImpl) notificationSectionsManager.configurationController).addCallback(notificationSectionsManager.configurationListener);
        NotificationSectionsFeatureManager notificationSectionsFeatureManager = notificationSectionsManager.sectionsFeatureManager;
        notificationSectionsFeatureManager.isFilteringEnabled();
        notificationSectionsFeatureManager.isFilteringEnabled();
        int[] iArr = notificationSectionsFeatureManager.isFilteringEnabled() ? new int[]{2, 3, 4, 5, 6, 7, 8, 9} : new int[]{2, 3, 8, 9};
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int i3 : iArr) {
            NotificationStackScrollLayout notificationStackScrollLayout = notificationSectionsManager.parent;
            if (notificationStackScrollLayout == null) {
                notificationStackScrollLayout = null;
            }
            arrayList.add(new NotificationSection(notificationStackScrollLayout, i3));
        }
        this.mSections = (NotificationSection[]) arrayList.toArray(new NotificationSection[0]);
        AmbientState ambientState = (AmbientState) Dependency.get(AmbientState.class);
        this.mAmbientState = ambientState;
        this.mBgColor = Utils.getColorAttr(R.attr.colorBackgroundFloating, ((ViewGroup) this).mContext).getDefaultColor();
        int dimensionPixelSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_min_height);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_max_height);
        this.mSplitShadeMinContentHeight = resources.getDimensionPixelSize(com.android.systemui.R.dimen.nssl_split_shade_min_content_height);
        ExpandHelper expandHelper = new ExpandHelper(getContext(), this.mExpandHelperCallback, dimensionPixelSize, dimensionPixelSize2);
        this.mExpandHelper = expandHelper;
        expandHelper.mEventSource = this;
        expandHelper.mScrollAdapter = this.mScrollAdapter;
        this.mStackScrollAlgorithm = new StackScrollAlgorithm(context, this);
        boolean z = NotiRune.NOTI_STYLE_TABLET_BG;
        if (z) {
            this.mOpaqueBgHelper = new SecNsslOpaqueBgHelper(context);
        }
        if (z) {
            this.mOpaqueBgHelper.getClass();
        } else {
            z = resources.getBoolean(com.android.systemui.R.bool.config_drawNotificationBackground);
        }
        this.mShouldDrawNotificationBackground = z;
        setOutlineProvider(this.mOutlineProvider);
        setWillNotDraw(!(z));
        this.mBackgroundPaint.setAntiAlias(true);
        this.mClearAllEnabled = resources.getBoolean(com.android.systemui.R.bool.config_enableNotificationsClearAll);
        this.mGroupMembershipManager = (GroupMembershipManager) Dependency.get(GroupMembershipManager.class);
        this.mGroupExpansionManager = (GroupExpansionManager) Dependency.get(GroupExpansionManager.class);
        setImportantForAccessibility(1);
        if (this.mAnimatedInsets) {
            setWindowInsetsAnimationCallback(this.mInsetsCallback);
        }
        FullExpansionPanelNotiAlphaController fullExpansionPanelNotiAlphaController = (FullExpansionPanelNotiAlphaController) Dependency.get(FullExpansionPanelNotiAlphaController.class);
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
        ((WallpaperEventNotifier) Dependency.get(WallpaperEventNotifier.class)).registerCallback(false, this.mSystemUIWidgetCallback, 512L);
        PanelScreenShotLogger panelScreenShotLogger = PanelScreenShotLogger.INSTANCE;
        panelScreenShotLogger.addLogProvider("AmbientState", ambientState);
        panelScreenShotLogger.addLogProvider("StackScroller", this);
        this.mKeyguardFoldController = (KeyguardFoldController) Dependency.get(KeyguardFoldController.class);
        KeyguardStateController keyguardStateController = (KeyguardStateController) Dependency.get(KeyguardStateController.class);
        this.mKeyguardStateController = keyguardStateController;
        ((KeyguardStateControllerImpl) keyguardStateController).addCallback(new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.11
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardShowingChanged() {
                NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayout.this;
                KeyguardStateController keyguardStateController2 = notificationStackScrollLayout2.mKeyguardStateController;
                if (((KeyguardStateControllerImpl) keyguardStateController2).mShowing && ((KeyguardStateControllerImpl) keyguardStateController2).mOccluded) {
                    notificationStackScrollLayout2.mJustBackFromOcclude = true;
                    notificationStackScrollLayout2.forceLayout();
                }
            }
        });
        Handler handler = (Handler) Dependency.get(Dependency.BG_HANDLER);
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        this.mDisplayManager = displayManager;
        displayManager.registerDisplayListener(this.mDisplayListener, handler);
        this.mPanelExpansionStateNotifier = (SecPanelExpansionStateNotifier) Dependency.get(SecPanelExpansionStateNotifier.class);
    }

    public static void clearTemporaryViewsInGroup(ViewGroup viewGroup) {
        while (viewGroup != null && viewGroup.getTransientViewCount() != 0) {
            View transientView = viewGroup.getTransientView(0);
            viewGroup.removeTransientView(transientView);
            Log.d("StackScroller", "clearTemporaryViewsInGroup : " + transientView);
            if (transientView instanceof ExpandableView) {
                ((ExpandableView) transientView).mTransientContainer = null;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0061, code lost:
    
        if (r4.mEntry.mBucket == 9) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x006b, code lost:
    
        r4 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0068, code lost:
    
        if (r4.mEntry.mBucket < 9) goto L39;
     */
    /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean includeChildInClearAll(ExpandableNotificationRow expandableNotificationRow, int i) {
        boolean z;
        if ((expandableNotificationRow instanceof ExpandableNotificationRow) && !expandableNotificationRow.areGutsExposed() && expandableNotificationRow.mEntry.hasFinishedInitialization() && expandableNotificationRow.mEntry.isClearable() && !(expandableNotificationRow.shouldShowPublic() && expandableNotificationRow.mSensitiveHiddenInGeneral)) {
            if (i != 0) {
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
                            throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Unknown selection: ", i));
                        }
                        NotiCenterPlugin notiCenterPlugin = NotiCenterPlugin.INSTANCE;
                        String packageName = expandableNotificationRow.mEntry.mSbn.getPackageName();
                        notiCenterPlugin.getClass();
                        HashSet hashSet = NotiCenterPlugin.noclearAppList;
                        z = !(hashSet != null ? hashSet.contains(packageName) : false);
                    }
                }
                if (z) {
                    return true;
                }
            }
            z = true;
            if (z) {
            }
        }
        return false;
    }

    public static boolean isPinnedHeadsUp(View view) {
        if (!(view instanceof ExpandableNotificationRow)) {
            return false;
        }
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
        return expandableNotificationRow.mIsHeadsUp && expandableNotificationRow.mIsPinned;
    }

    public static void updateNotification(ExpandableNotificationRow expandableNotificationRow) {
        ((NotificationColorPicker) Dependency.get(NotificationColorPicker.class)).updateAllTextViewColors(expandableNotificationRow, expandableNotificationRow.mDimmed);
        if (expandableNotificationRow.mIsSummaryWithChildren) {
            NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
            int notificationChildCount = notificationChildrenContainer.getNotificationChildCount();
            for (int i = 0; i < notificationChildCount; i++) {
                updateNotification((ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(i));
            }
        }
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
            int scrollRange = getScrollRange();
            if ((currY < 0 && i >= 0) || (currY > scrollRange && i <= scrollRange)) {
                float currVelocity = this.mScroller.getCurrVelocity();
                if (currVelocity >= this.mMinimumVelocity) {
                    this.mMaxOverScroll = (Math.abs(currVelocity) / 1000.0f) * this.mOverflingDistance;
                }
            }
            if (this.mDontClampNextScroll) {
                scrollRange = Math.max(scrollRange, i);
            }
            customOverScrollBy(currY - i, i, scrollRange, (int) this.mMaxOverScroll);
        }
        postOnAnimation(this.mReflingAndAnimateScroll);
    }

    public final void applyCurrentState() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ExpandableView childAtIndex = getChildAtIndex(i);
            ExpandableViewState expandableViewState = childAtIndex.mViewState;
            if (!expandableViewState.gone) {
                expandableViewState.applyToView(childAtIndex);
            }
        }
        NotificationLogger$$ExternalSyntheticLambda2 notificationLogger$$ExternalSyntheticLambda2 = this.mListener;
        if (notificationLogger$$ExternalSyntheticLambda2 != null) {
            notificationLogger$$ExternalSyntheticLambda2.f$0.onChildLocationsChanged();
        }
        Iterator it = this.mAnimationFinishedRunnables.iterator();
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
        }
        this.mAnimationFinishedRunnables.clear();
        setAnimationRunning(false);
        updateBackground();
        updateViewShadows();
        updateClippingToTopRoundedCorner();
    }

    public final float calculateAppearFraction(float f) {
        if (this.mSimplifiedAppearFraction) {
            if (!isHeadsUpTransition()) {
                return this.mAmbientState.mExpansionFraction;
            }
            float appearEndPosition = getAppearEndPosition();
            float appearStartPosition = getAppearStartPosition();
            return MathUtils.constrain((f - appearStartPosition) / (appearEndPosition - appearStartPosition), -1.0f, 1.0f);
        }
        if (!this.mShouldUseSplitNotificationShade) {
            float appearEndPosition2 = getAppearEndPosition();
            float appearStartPosition2 = getAppearStartPosition();
            return (f - appearStartPosition2) / (appearEndPosition2 - appearStartPosition2);
        }
        if (!isHeadsUpTransition()) {
            return this.mAmbientState.mExpansionFraction;
        }
        float appearEndPosition3 = getAppearEndPosition();
        float appearStartPosition3 = getAppearStartPosition();
        return MathUtils.constrain((f - appearStartPosition3) / (appearEndPosition3 - appearStartPosition3), -1.0f, 1.0f);
    }

    public final float calculateGapHeight(ExpandableView expandableView, ExpandableView expandableView2, int i) {
        if (onKeyguard()) {
            return 0.0f;
        }
        StackScrollAlgorithm stackScrollAlgorithm = this.mStackScrollAlgorithm;
        NotificationSectionsManager notificationSectionsManager = this.mSectionsManager;
        AmbientState ambientState = this.mAmbientState;
        float f = ambientState.mFractionToShade;
        boolean isOnKeyguard = ambientState.isOnKeyguard();
        stackScrollAlgorithm.getClass();
        if (expandableView instanceof ExpandableView) {
            return NotificationUtils.interpolate(0.0f, stackScrollAlgorithm.mMaxGroupExpandedBottomGap, StackScrollAlgorithm.getPreviousGroupExpandFraction(expandableView));
        }
        if (StackScrollAlgorithm.childNeedsGapHeight(notificationSectionsManager, i, expandableView2, expandableView)) {
            return stackScrollAlgorithm.getGapForLocation(f, isOnKeyguard);
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
        int scrollRange = getScrollRange();
        if (scrollRange >= this.mOwnScrollY || this.mAmbientState.mClearAllInProgress) {
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
                    Iterator it = expandableNotificationRow.getAttachedChildren().iterator();
                    while (it.hasNext()) {
                        ((ExpandableNotificationRow) it.next()).setHeadsUpAnimatingAway(false);
                    }
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x0038, code lost:
    
        if (includeChildInClearAll(r8, r22) != false) goto L15;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0080 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void clearNotifications(final int i, boolean z) {
        int i2;
        int i3;
        boolean z2;
        int childCount = getChildCount();
        ArrayList arrayList = new ArrayList(childCount);
        boolean z3 = false;
        int i4 = 0;
        while (true) {
            i2 = 2;
            if (i4 >= childCount) {
                break;
            }
            View childAt = getChildAt(i4);
            boolean z4 = !this.mController.hasNotifications(2, false);
            if (!(childAt instanceof SectionHeaderView) || !z4) {
                if (childAt instanceof ExpandableNotificationRow) {
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) childAt;
                    if (isVisible(expandableNotificationRow)) {
                    }
                }
                z2 = false;
                if (z2) {
                    arrayList.add(childAt);
                }
                if (!(childAt instanceof ExpandableNotificationRow)) {
                    ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) childAt;
                    if (isVisible(expandableNotificationRow2) && expandableNotificationRow2.getAttachedChildren() != null && expandableNotificationRow2.mChildrenExpanded) {
                        for (ExpandableNotificationRow expandableNotificationRow3 : expandableNotificationRow2.getAttachedChildren()) {
                            if (isVisible(expandableNotificationRow3) && includeChildInClearAll(expandableNotificationRow3, i)) {
                                arrayList.add(expandableNotificationRow3);
                            }
                        }
                    }
                }
                i4++;
            }
            z2 = true;
            if (z2) {
            }
            if (!(childAt instanceof ExpandableNotificationRow)) {
            }
            i4++;
        }
        int childCount2 = getChildCount();
        final ArrayList arrayList2 = new ArrayList(childCount2);
        for (int i5 = 0; i5 < childCount2; i5++) {
            View childAt2 = getChildAt(i5);
            if (childAt2 instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow4 = (ExpandableNotificationRow) childAt2;
                if (includeChildInClearAll(expandableNotificationRow4, i)) {
                    arrayList2.add(expandableNotificationRow4);
                }
                List<ExpandableNotificationRow> attachedChildren = expandableNotificationRow4.getAttachedChildren();
                if (isVisible(expandableNotificationRow4) && attachedChildren != null) {
                    for (ExpandableNotificationRow expandableNotificationRow5 : attachedChildren) {
                        if (includeChildInClearAll(expandableNotificationRow4, i)) {
                            arrayList2.add(expandableNotificationRow5);
                        }
                    }
                }
            }
        }
        C2944xbae1b0c2 c2944xbae1b0c2 = this.mClearAllListener;
        if (c2944xbae1b0c2 != null) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = (NotificationStackScrollLayoutController) c2944xbae1b0c2.f$0;
            notificationStackScrollLayoutController.getClass();
            notificationStackScrollLayoutController.mUiEventLogger.log(i == 0 ? NotificationStackScrollLayoutController.NotificationPanelEvent.DISMISS_ALL_NOTIFICATIONS_PANEL : i == 2 ? NotificationStackScrollLayoutController.NotificationPanelEvent.DISMISS_SILENT_NOTIFICATIONS_PANEL : NotificationStackScrollLayoutController.NotificationPanelEvent.INVALID);
        }
        Consumer consumer = new Consumer() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                final NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                final ArrayList arrayList3 = arrayList2;
                final int i6 = i;
                int i7 = NotificationStackScrollLayout.$r8$clinit;
                notificationStackScrollLayout.getClass();
                if (((Boolean) obj).booleanValue()) {
                    notificationStackScrollLayout.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayout.this;
                            ArrayList arrayList4 = arrayList3;
                            int i8 = i6;
                            int i9 = NotificationStackScrollLayout.$r8$clinit;
                            notificationStackScrollLayout2.onClearAllAnimationsEnd(i8, arrayList4);
                        }
                    });
                } else {
                    notificationStackScrollLayout.onClearAllAnimationsEnd(i6, arrayList3);
                }
            }
        };
        if (arrayList.isEmpty()) {
            consumer.accept(Boolean.TRUE);
            if (this.mIsExpanded && z) {
                postDelayed(new NotificationStackScrollLayout$$ExternalSyntheticLambda2(this, i2), 200L);
                return;
            }
            return;
        }
        setClearAllInProgress(true);
        this.mShadeNeedsToClose = z;
        InteractionJankMonitor.getInstance().begin(this, 62);
        int size = arrayList.size();
        int i6 = size - 1;
        int i7 = 60;
        int i8 = 0;
        while (i6 >= 0) {
            View view = (View) arrayList.get(i6);
            NotificationStackScrollLayout$$ExternalSyntheticLambda1 notificationStackScrollLayout$$ExternalSyntheticLambda1 = i6 == 0 ? consumer : 0;
            if (view instanceof SectionHeaderView) {
                ((StackScrollerDecorView) view).setContentVisible(notificationStackScrollLayout$$ExternalSyntheticLambda1, z3, true);
                i3 = i6;
            } else {
                i3 = i6;
                this.mSwipeHelper.dismissChild(view, 0.0f, notificationStackScrollLayout$$ExternalSyntheticLambda1, i8, true, 200L, true);
            }
            i7 = Math.max(30, i7 - 5);
            i8 += i7;
            i6 = i3 - 1;
            z3 = false;
        }
        SystemUIAnalytics.sendEventCDLog("QPN001", "QPNE0026", "type", i == 0 ? "all" : "silent", "count", Integer.toString(size));
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
        int scrollRange = getScrollRange();
        int i8 = this.mOwnScrollY;
        boolean z3 = i8 <= 0;
        boolean z4 = i8 >= scrollRange;
        if (z3 || z4) {
            if (z3) {
                f = -i8;
                setOwnScrollY(0);
                this.mDontReportNextOverScroll = true;
                z2 = true;
            } else {
                setOwnScrollY(scrollRange);
                f = i8 - scrollRange;
                z2 = false;
            }
            setOverScrollAmount(f, z2, false, true);
            setOverScrollAmount(0.0f, z2, true, true);
            this.mScroller.forceFinished(true);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        if (this.mShouldUseRoundedRectClipping && !this.mLaunchingNotification) {
            canvas.clipPath(this.mRoundedClipPath);
        }
        super.dispatchDraw(canvas);
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x012f, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x015f, code lost:
    
        r10 = scrollAmountForKeyboardFocus(r0, true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0163, code lost:
    
        if (r10 == 0) goto L123;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x016c, code lost:
    
        if ((r9.mOwnScrollY + r10) <= getScrollRange()) goto L118;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x016e, code lost:
    
        r9.mOwnScrollY = getScrollRange();
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x017c, code lost:
    
        if (r9.mAnimationsEnabled == false) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x017e, code lost:
    
        r9.mNeedsAnimation = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0180, code lost:
    
        requestChildrenUpdate();
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x0175, code lost:
    
        r9.mOwnScrollY += r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x0183, code lost:
    
        r3.requestFocus();
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0186, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0072, code lost:
    
        r10 = scrollAmountForKeyboardFocus(r0, false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0076, code lost:
    
        if (r10 == 0) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0078, code lost:
    
        r0 = r9.mOwnScrollY;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x007c, code lost:
    
        if ((r0 + r10) > 0) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x007e, code lost:
    
        r9.mOwnScrollY = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0086, code lost:
    
        if (r9.mAnimationsEnabled == false) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0088, code lost:
    
        r9.mNeedsAnimation = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x008a, code lost:
    
        requestChildrenUpdate();
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0081, code lost:
    
        r9.mOwnScrollY = r0 + r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x008d, code lost:
    
        r3.requestFocus();
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0090, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0106, code lost:
    
        if ((r3 instanceof com.android.systemui.statusbar.NotificationShelf) != false) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0108, code lost:
    
        r10 = scrollAmountForKeyboardFocus(r0, true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x010c, code lost:
    
        if (r10 == 0) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0115, code lost:
    
        if ((r9.mOwnScrollY + r10) <= getScrollRange()) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0117, code lost:
    
        r9.mOwnScrollY = getScrollRange();
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0125, code lost:
    
        if (r9.mAnimationsEnabled == false) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0127, code lost:
    
        r9.mNeedsAnimation = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0129, code lost:
    
        requestChildrenUpdate();
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x011e, code lost:
    
        r9.mOwnScrollY += r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x012c, code lost:
    
        r3.requestFocus();
     */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int i;
        int i2;
        View childAt;
        ExpandableNotificationRow expandableNotificationRow;
        int i3;
        boolean z = keyEvent.getAction() == 0;
        View view = null;
        if (z && keyEvent.getKeyCode() == 19) {
            View findFocus = findFocus();
            if (findFocus instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) findFocus;
                int childCount = getChildCount();
                int i4 = 0;
                while (true) {
                    if (i4 >= childCount) {
                        expandableNotificationRow = null;
                        break;
                    }
                    View childAt2 = getChildAt(i4);
                    if ((childAt2 instanceof ExpandableNotificationRow) && childAt2.getVisibility() != 8 && childAt2 != this.mShelf) {
                        expandableNotificationRow = (ExpandableNotificationRow) childAt2;
                        break;
                    }
                    i4++;
                }
                if (expandableNotificationRow2 != null && expandableNotificationRow2.equals(expandableNotificationRow)) {
                    return super.dispatchKeyEvent(keyEvent);
                }
                int indexOfChild = indexOfChild(expandableNotificationRow2);
                for (int i5 = 1; i5 < getChildCount() - indexOfChild && (i3 = indexOfChild - i5) >= 0 && ((view = getChildAt(i3)) == null || view.getVisibility() != 0); i5++) {
                }
            } else if (this.mShelf.hasFocus() && (childAt = getChildAt(indexOfChild(this.mShelf) - 1)) != null) {
                this.mOwnScrollY = getScrollRange();
                if (this.mAnimationsEnabled) {
                    this.mNeedsAnimation = true;
                }
                requestChildrenUpdate();
                childAt.post(new NotificationStackScrollLayout$$ExternalSyntheticLambda2(childAt, 5));
                return true;
            }
        } else if ((z && keyEvent.getKeyCode() == 20) || (z && keyEvent.getKeyCode() == 61)) {
            View findFocus2 = findFocus();
            if (findFocus2 instanceof ExpandableNotificationRow) {
                int indexOfChild2 = indexOfChild((ExpandableNotificationRow) findFocus2);
                for (int i6 = 1; i6 < getChildCount() - indexOfChild2 && (i2 = indexOfChild2 + i6) < getChildCount() && ((view = getChildAt(i2)) == null || view.getVisibility() != 0); i6++) {
                }
                if (view != null) {
                    this.mShelf.requestFocus();
                    return true;
                }
            } else if (findFocus2 instanceof SectionHeaderView) {
                int indexOfChild3 = indexOfChild((SectionHeaderView) findFocus2);
                for (int i7 = 1; i7 < getChildCount() - indexOfChild3 && (i = indexOfChild3 + i7) < getChildCount() && (view = getChildAt(i)) == null; i7++) {
                }
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
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
        asIndenting.println("Internal state:");
        final int i = 0;
        DumpUtilsKt.withIncreasedIndent(asIndenting, new Runnable(this) { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda6
            public final /* synthetic */ NotificationStackScrollLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i) {
                    case 0:
                        NotificationStackScrollLayout notificationStackScrollLayout = this.f$0;
                        IndentingPrintWriter indentingPrintWriter = asIndenting;
                        DumpUtilsKt.println(indentingPrintWriter, "pulsing", Boolean.valueOf(notificationStackScrollLayout.mPulsing));
                        DumpUtilsKt.println(indentingPrintWriter, "expanded", Boolean.valueOf(notificationStackScrollLayout.mIsExpanded));
                        DumpUtilsKt.println(indentingPrintWriter, "headsUpPinned", Boolean.valueOf(notificationStackScrollLayout.mInHeadsUpPinnedMode));
                        DumpUtilsKt.println(indentingPrintWriter, "qsClipping", Boolean.valueOf(notificationStackScrollLayout.mShouldUseRoundedRectClipping));
                        DumpUtilsKt.println(indentingPrintWriter, "qsClipDismiss", Boolean.valueOf(notificationStackScrollLayout.mDismissUsingRowTranslationX));
                        DumpUtilsKt.println(indentingPrintWriter, "visibility", DumpUtilsKt.visibilityString(notificationStackScrollLayout.getVisibility()));
                        DumpUtilsKt.println(indentingPrintWriter, "alpha", Float.valueOf(notificationStackScrollLayout.getAlpha()));
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
                        DumpUtilsKt.println(indentingPrintWriter, "topPadding", Integer.valueOf(notificationStackScrollLayout.mTopPadding));
                        DumpUtilsKt.println(indentingPrintWriter, "bottomPadding", Integer.valueOf(notificationStackScrollLayout.mBottomPadding));
                        indentingPrintWriter.println("NotificationStackSizeCalculator saveSpaceOnLockscreen=" + notificationStackScrollLayout.mNotificationStackSizeCalculator.saveSpaceOnLockscreen);
                        break;
                    default:
                        NotificationStackScrollLayout notificationStackScrollLayout2 = this.f$0;
                        PrintWriter printWriter2 = asIndenting;
                        String[] strArr2 = strArr;
                        int i2 = NotificationStackScrollLayout.$r8$clinit;
                        int childCount = notificationStackScrollLayout2.getChildCount();
                        printWriter2.println("Number of children: " + childCount);
                        printWriter2.println();
                        int i3 = 0;
                        for (int i4 = 0; i4 < childCount; i4++) {
                            ExpandableView childAtIndex = notificationStackScrollLayout2.getChildAtIndex(i4);
                            childAtIndex.dump(printWriter2, strArr2);
                            if (childAtIndex instanceof FooterView) {
                                DumpUtilsKt.withIncreasedIndent(printWriter2, new NotificationStackScrollLayout$$ExternalSyntheticLambda7(notificationStackScrollLayout2, printWriter2, i3));
                            }
                            printWriter2.println();
                        }
                        int transientViewCount = notificationStackScrollLayout2.getTransientViewCount();
                        printWriter2.println("Transient Views: " + transientViewCount);
                        while (i3 < transientViewCount) {
                            ((ExpandableView) notificationStackScrollLayout2.getTransientView(i3)).dump(printWriter2, strArr2);
                            i3++;
                        }
                        NotificationSwipeHelper notificationSwipeHelper = notificationStackScrollLayout2.mSwipeHelper;
                        ExpandableView expandableView = notificationSwipeHelper.mIsSwiping ? notificationSwipeHelper.mTouchedView : null;
                        printWriter2.println("Swiped view: " + expandableView);
                        if (expandableView instanceof ExpandableView) {
                            expandableView.dump(printWriter2, strArr2);
                            break;
                        }
                        break;
                }
            }
        });
        asIndenting.println();
        asIndenting.println("Contents:");
        final int i2 = 1;
        DumpUtilsKt.withIncreasedIndent(asIndenting, new Runnable(this) { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda6
            public final /* synthetic */ NotificationStackScrollLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i2) {
                    case 0:
                        NotificationStackScrollLayout notificationStackScrollLayout = this.f$0;
                        IndentingPrintWriter indentingPrintWriter = asIndenting;
                        DumpUtilsKt.println(indentingPrintWriter, "pulsing", Boolean.valueOf(notificationStackScrollLayout.mPulsing));
                        DumpUtilsKt.println(indentingPrintWriter, "expanded", Boolean.valueOf(notificationStackScrollLayout.mIsExpanded));
                        DumpUtilsKt.println(indentingPrintWriter, "headsUpPinned", Boolean.valueOf(notificationStackScrollLayout.mInHeadsUpPinnedMode));
                        DumpUtilsKt.println(indentingPrintWriter, "qsClipping", Boolean.valueOf(notificationStackScrollLayout.mShouldUseRoundedRectClipping));
                        DumpUtilsKt.println(indentingPrintWriter, "qsClipDismiss", Boolean.valueOf(notificationStackScrollLayout.mDismissUsingRowTranslationX));
                        DumpUtilsKt.println(indentingPrintWriter, "visibility", DumpUtilsKt.visibilityString(notificationStackScrollLayout.getVisibility()));
                        DumpUtilsKt.println(indentingPrintWriter, "alpha", Float.valueOf(notificationStackScrollLayout.getAlpha()));
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
                        DumpUtilsKt.println(indentingPrintWriter, "topPadding", Integer.valueOf(notificationStackScrollLayout.mTopPadding));
                        DumpUtilsKt.println(indentingPrintWriter, "bottomPadding", Integer.valueOf(notificationStackScrollLayout.mBottomPadding));
                        indentingPrintWriter.println("NotificationStackSizeCalculator saveSpaceOnLockscreen=" + notificationStackScrollLayout.mNotificationStackSizeCalculator.saveSpaceOnLockscreen);
                        break;
                    default:
                        NotificationStackScrollLayout notificationStackScrollLayout2 = this.f$0;
                        PrintWriter printWriter2 = asIndenting;
                        String[] strArr2 = strArr;
                        int i22 = NotificationStackScrollLayout.$r8$clinit;
                        int childCount = notificationStackScrollLayout2.getChildCount();
                        printWriter2.println("Number of children: " + childCount);
                        printWriter2.println();
                        int i3 = 0;
                        for (int i4 = 0; i4 < childCount; i4++) {
                            ExpandableView childAtIndex = notificationStackScrollLayout2.getChildAtIndex(i4);
                            childAtIndex.dump(printWriter2, strArr2);
                            if (childAtIndex instanceof FooterView) {
                                DumpUtilsKt.withIncreasedIndent(printWriter2, new NotificationStackScrollLayout$$ExternalSyntheticLambda7(notificationStackScrollLayout2, printWriter2, i3));
                            }
                            printWriter2.println();
                        }
                        int transientViewCount = notificationStackScrollLayout2.getTransientViewCount();
                        printWriter2.println("Transient Views: " + transientViewCount);
                        while (i3 < transientViewCount) {
                            ((ExpandableView) notificationStackScrollLayout2.getTransientView(i3)).dump(printWriter2, strArr2);
                            i3++;
                        }
                        NotificationSwipeHelper notificationSwipeHelper = notificationStackScrollLayout2.mSwipeHelper;
                        ExpandableView expandableView = notificationSwipeHelper.mIsSwiping ? notificationSwipeHelper.mTouchedView : null;
                        printWriter2.println("Swiped view: " + expandableView);
                        if (expandableView instanceof ExpandableView) {
                            expandableView.dump(printWriter2, strArr2);
                            break;
                        }
                        break;
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
            int i = 0;
            setClearAllInProgress(false);
            if (this.mShadeNeedsToClose) {
                this.mShadeNeedsToClose = false;
                if (this.mIsExpanded) {
                    postDelayed(new NotificationStackScrollLayout$$ExternalSyntheticLambda2(this, i), 0L);
                }
            }
        }
    }

    public final void fling(int i) {
        if (getChildCount() > 0) {
            float currentOverScrollAmount = getCurrentOverScrollAmount(true);
            float currentOverScrollAmount2 = getCurrentOverScrollAmount(false);
            if (i < 0 && currentOverScrollAmount > 0.0f) {
                setOwnScrollY(this.mOwnScrollY - ((int) currentOverScrollAmount));
                if (!this.mShouldUseSplitNotificationShade) {
                    if (!QpRune.QUICK_TABLET) {
                        this.mDontReportNextOverScroll = true;
                    }
                    setOverScrollAmount(0.0f, true, false, true);
                }
                this.mMaxOverScroll = (getRubberBandFactor(true) * (Math.abs(i) / 1000.0f) * this.mOverflingDistance) + currentOverScrollAmount;
            } else if (i <= 0 || currentOverScrollAmount2 <= 0.0f) {
                this.mMaxOverScroll = 0.0f;
            } else {
                setOwnScrollY((int) (this.mOwnScrollY + currentOverScrollAmount2));
                setOverScrollAmount(0.0f, false, false, true);
                this.mMaxOverScroll = (getRubberBandFactor(false) * (Math.abs(i) / 1000.0f) * this.mOverflingDistance) + currentOverScrollAmount2;
            }
            int max = Math.max(0, getScrollRange());
            if (this.mExpandedInThisMotion) {
                max = Math.min(max, this.mMaxScrollAfterExpand);
            }
            this.mScroller.fling(((ViewGroup) this).mScrollX, this.mOwnScrollY, 1, i, 0, 0, i > 0 ? getScrollAmountToScrollBoundary() : 0, max, 0, (!this.mExpandedInThisMotion || this.mOwnScrollY < 0) ? 1073741823 : 0);
            if (i < 0 && this.mScroller.getFinalY() > 0 && this.mScroller.getFinalY() < getScrollAmountToScrollBoundary()) {
                this.mScroller.forceFinished(true);
                OverScroller overScroller = this.mScroller;
                int i2 = ((ViewGroup) this).mScrollX;
                int i3 = this.mOwnScrollY;
                overScroller.startScroll(i2, i3, 0, -i3, 1050);
            }
            animateScroll();
        }
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
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
        PanelScreenShotLogger.addLogItem(arrayList, "mLastGoneCallTrace", this.mLastGoneCallTrace);
        PanelScreenShotLogger.addLogItem(arrayList, "appIconColor", Integer.toHexString(getContext().getColor(com.android.systemui.R.color.notification_app_icon_color)));
        arrayList.add("\n\n");
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) childAt;
                ExpandableViewState expandableViewState = expandableNotificationRow.mViewState;
                PanelScreenShotLogger panelScreenShotLogger = PanelScreenShotLogger.INSTANCE;
                String str = expandableNotificationRow.mLoggingKey;
                panelScreenShotLogger.getClass();
                PanelScreenShotLogger.addLogItem(arrayList, "key", str);
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
                if (expandableViewState != null) {
                    PanelScreenShotLogger.addLogItem(arrayList, GlsIntent.Extras.EXTRA_LOCATION, Integer.valueOf(expandableViewState.location));
                    PanelScreenShotLogger.addLogItem(arrayList, "inShelf", Boolean.valueOf(expandableViewState.inShelf));
                    PanelScreenShotLogger.addLogItem(arrayList, "hideSensitive", Boolean.valueOf(expandableViewState.hideSensitive));
                    PanelScreenShotLogger.addLogItem(arrayList, "gone", Boolean.valueOf(expandableViewState.gone));
                    PanelScreenShotLogger.addLogItem(arrayList, "dimmed", Boolean.valueOf(expandableViewState.dimmed));
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
        if (this.mAnimationsEnabled && (z || this.mHeadsUpGoingAwayAnimationsAllowed)) {
            if (!z && this.mHeadsUpChangeAnimations.remove(new Pair(expandableNotificationRow, Boolean.TRUE))) {
                logHunAnimationSkipped(expandableNotificationRow, "previous hun appear animation cancelled");
                return;
            }
            this.mHeadsUpChangeAnimations.add(new Pair(expandableNotificationRow, Boolean.valueOf(z)));
            this.mNeedsAnimation = true;
            if (!this.mIsExpanded && !this.mWillExpand && !z) {
                expandableNotificationRow.setHeadsUpAnimatingAway(true);
            }
            requestChildrenUpdate();
        }
    }

    public final float getAppearEndPosition() {
        int i = this.mAmbientState.mStackTopMargin;
        int i2 = this.mController.mNotifStats.numActiveNotifs;
        boolean z = NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW;
        if (!z ? !(this.mEmptyShadeView.getVisibility() != 8 || i2 <= 0) : i2 != 0) {
            i = (z || this.mEmptyShadeView.getVisibility() == 8) ? 0 : this.mEmptyShadeView.getHeight();
        } else if (isHeadsUpTransition() || (this.mInHeadsUpPinnedMode && !this.mAmbientState.mDozing)) {
            if (this.mShelf.getVisibility() != 8 && i2 > 1) {
                i += this.mShelf.getHeight() + this.mPaddingBetweenElements;
            }
            i += getPositionInLinearLayout(this.mAmbientState.getTrackedHeadsUpRow()) + getTopHeadsUpPinnedHeight();
        } else if (this.mShelf.getVisibility() != 8) {
            i += this.mShelf.getHeight();
        }
        return i + (onKeyguard() ? this.mTopPadding : this.mIntrinsicPadding);
    }

    public final float getAppearStartPosition() {
        if (!isHeadsUpTransition()) {
            return getMinExpansionHeight();
        }
        return (this.mHeadsUpInset - this.mAmbientState.mStackTopMargin) + (getFirstVisibleSection() != null ? r0.mFirstVisibleChild.getPinnedHeadsUpHeight() : 0);
    }

    public final ExpandableView getChildAtIndex(int i) {
        return (ExpandableView) getChildAt(i);
    }

    public final ExpandableView getChildAtPosition(float f, boolean z, boolean z2, float f2) {
        ExpandableNotificationRow expandableNotificationRow;
        float translationY;
        ExpandableNotificationRow expandableNotificationRow2;
        int childCount = getChildCount();
        int i = 0;
        while (true) {
            expandableNotificationRow = null;
            if (i >= childCount) {
                return null;
            }
            ExpandableView childAtIndex = getChildAtIndex(i);
            if (childAtIndex.getVisibility() == 0 && (!z2 || !(childAtIndex instanceof StackScrollerDecorView))) {
                translationY = childAtIndex.getTranslationY();
                float max = Math.max(0, childAtIndex.mClipTopAmount) + translationY;
                float f3 = (childAtIndex.mActualHeight + translationY) - childAtIndex.mClipBottomAmount;
                int width = getWidth();
                if ((f3 - max >= this.mMinInteractionHeight || !z) && f2 >= max && f2 <= f3 && f >= 0 && f <= width) {
                    if (!(childAtIndex instanceof ExpandableNotificationRow)) {
                        return childAtIndex;
                    }
                    expandableNotificationRow2 = (ExpandableNotificationRow) childAtIndex;
                    NotificationEntry notificationEntry = expandableNotificationRow2.mEntry;
                    if (this.mIsExpanded || !expandableNotificationRow2.mIsHeadsUp || !expandableNotificationRow2.mIsPinned) {
                        break;
                    }
                    NotificationEntry notificationEntry2 = this.mTopHeadsUpEntry;
                    if (notificationEntry2.row == expandableNotificationRow2 || ((GroupMembershipManagerImpl) this.mGroupMembershipManager).getGroupSummary(notificationEntry2) == notificationEntry) {
                        break;
                    }
                }
            }
            i++;
        }
        float f4 = f2 - translationY;
        if (!expandableNotificationRow2.mIsSummaryWithChildren || !expandableNotificationRow2.mChildrenExpanded) {
            return expandableNotificationRow2;
        }
        NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow2.mChildrenContainer;
        int size = ((ArrayList) notificationChildrenContainer.mAttachedChildren).size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                break;
            }
            ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(i2);
            float translationY2 = expandableNotificationRow3.getTranslationY();
            float max2 = Math.max(0, expandableNotificationRow3.mClipTopAmount) + translationY2;
            float f5 = translationY2 + expandableNotificationRow3.mActualHeight;
            if (f4 >= max2 && f4 <= f5) {
                expandableNotificationRow = expandableNotificationRow3;
                break;
            }
            i2++;
        }
        return expandableNotificationRow == null ? expandableNotificationRow2 : expandableNotificationRow;
    }

    public final ExpandableView getChildAtRawPosition(float f, float f2) {
        getLocationOnScreen(this.mTempInt2);
        int[] iArr = this.mTempInt2;
        return getChildAtPosition(f - iArr[0], true, true, f2 - iArr[1]);
    }

    public final List getChildrenWithBackground() {
        ArrayList arrayList = new ArrayList();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ExpandableView childAtIndex = getChildAtIndex(i);
            if (childAtIndex.getVisibility() != 8 && !(childAtIndex instanceof StackScrollerDecorView) && childAtIndex != this.mShelf) {
                arrayList.add(childAtIndex);
            }
        }
        return arrayList;
    }

    public final float getCurrentOverScrollAmount(boolean z) {
        AmbientState ambientState = this.mAmbientState;
        return z ? ambientState.mOverScrollTopAmount : ambientState.mOverScrollBottomAmount;
    }

    public final int getEmptyBottomMargin() {
        return Math.max(this.mMaxLayoutHeight - (this.mShouldUseSplitNotificationShade ? Math.max(this.mSplitShadeMinContentHeight, this.mContentHeight) : this.mContentHeight), 0);
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

    public final ExpandableView getFirstChildWithBackground() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ExpandableView childAtIndex = getChildAtIndex(i);
            if (childAtIndex.getVisibility() != 8 && !(childAtIndex instanceof StackScrollerDecorView) && childAtIndex != this.mShelf) {
                return childAtIndex;
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
        return Math.max(0, this.mBottomInset - ((getRootView().getHeight() - getHeight()) - getLocationOnScreen()[1]));
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

    public final int getMinExpansionHeight() {
        return this.mShelf.getHeight() + 0 + this.mWaterfallTopInset;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getPositionInLinearLayout(View view) {
        boolean z;
        ExpandableNotificationRow expandableNotificationRow;
        ExpandableNotificationRow expandableNotificationRow2;
        int i;
        int i2 = 0;
        if (view instanceof ExpandableNotificationRow) {
            GroupMembershipManager groupMembershipManager = this.mGroupMembershipManager;
            NotificationEntry notificationEntry = ((ExpandableNotificationRow) view).mEntry;
            ((GroupMembershipManagerImpl) groupMembershipManager).getClass();
            if (!(notificationEntry.getParent() == GroupEntry.ROOT_ENTRY)) {
                z = true;
                ExpandableView expandableView = null;
                if (z) {
                    expandableNotificationRow = null;
                    expandableNotificationRow2 = null;
                } else {
                    ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) view;
                    expandableNotificationRow2 = expandableNotificationRow3.mNotificationParent;
                    expandableNotificationRow = expandableNotificationRow3;
                    view = expandableNotificationRow2;
                }
                float f = !this.mAmbientState.isOnKeyguard() ? 0.0f : this.mMinimumPaddings;
                int i3 = (int) f;
                int i4 = -1;
                for (i = 0; i < getChildCount(); i++) {
                    ExpandableView childAtIndex = getChildAtIndex(i);
                    boolean z2 = childAtIndex.getVisibility() != 8;
                    if (z2) {
                        i4++;
                    }
                    if (z2 && !childAtIndex.hasNoContentHeight()) {
                        float f2 = i3;
                        if (f2 != f) {
                            if (expandableView != null) {
                                i3 = (int) (calculateGapHeight(expandableView, childAtIndex, i4) + f2);
                            }
                            i3 += this.mPaddingBetweenElements;
                        }
                    }
                    if (childAtIndex == view) {
                        if (expandableNotificationRow2 == null) {
                            return i3;
                        }
                        if (expandableNotificationRow2.mIsSummaryWithChildren) {
                            NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow2.mChildrenContainer;
                            int i5 = (notificationChildrenContainer.mContainingNotification.isGroupExpanded() ? notificationChildrenContainer.mHeaderExpandedHeight : notificationChildrenContainer.mNotificationHeaderMargin) + 0 + notificationChildrenContainer.mNotificationTopPadding;
                            int i6 = 0;
                            while (true) {
                                if (i6 >= ((ArrayList) notificationChildrenContainer.mAttachedChildren).size()) {
                                    break;
                                }
                                ExpandableNotificationRow expandableNotificationRow4 = (ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(i6);
                                boolean z3 = expandableNotificationRow4.getVisibility() != 8;
                                if (z3) {
                                    i5 += notificationChildrenContainer.mDividerHeight;
                                }
                                if (expandableNotificationRow4 == expandableNotificationRow) {
                                    i2 = i5;
                                    break;
                                }
                                if (z3) {
                                    i5 = expandableNotificationRow4.getIntrinsicHeight() + i5;
                                }
                                i6++;
                            }
                        }
                        return i3 + i2;
                    }
                    if (z2) {
                        i3 = childAtIndex.getIntrinsicHeight() + i3;
                        expandableView = childAtIndex;
                    }
                }
                return 0;
            }
        }
        z = false;
        ExpandableView expandableView2 = null;
        if (z) {
        }
        if (!this.mAmbientState.isOnKeyguard()) {
        }
        int i32 = (int) f;
        int i42 = -1;
        while (i < getChildCount()) {
        }
        return 0;
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
        if (!this.mScrolledToTopOnFirstDown || this.mShouldUseSplitNotificationShade) {
            return RUBBER_BAND_FACTOR_NORMAL;
        }
        return 1.0f;
    }

    public final int getScrollAmountToScrollBoundary() {
        return this.mShouldUseSplitNotificationShade ? this.mSidePaddings : this.mTopPadding - ((ShadeHeaderController) Dependency.get(ShadeHeaderController.class)).header.getHeight();
    }

    public final int getScrollRange() {
        int i = this.mContentHeight;
        if (!this.mIsExpanded && this.mInHeadsUpPinnedMode) {
            i = this.mHeadsUpInset + getTopHeadsUpPinnedHeight();
        }
        int max = Math.max(0, i - this.mMaxLayoutHeight);
        int imeInset = getImeInset();
        int min = Math.min(imeInset, Math.max(0, i - (getHeight() - imeInset))) + max;
        return min > 0 ? Math.max(getScrollAmountToScrollBoundary(), min) : min;
    }

    public final int getSpeedBumpIndex() {
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
                        z = true ^ expandableNotificationRow.mEntry.isAmbient();
                    } else if (expandableNotificationRow.mEntry.mBucket >= 9) {
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
        NotificationEntry notificationEntry = this.mTopHeadsUpEntry;
        if (notificationEntry == null) {
            return 0;
        }
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        if (expandableNotificationRow.isChildInGroup()) {
            NotificationEntry groupSummary = ((GroupMembershipManagerImpl) this.mGroupMembershipManager).getGroupSummary(expandableNotificationRow.mEntry);
            if (groupSummary != null) {
                expandableNotificationRow = groupSummary.row;
            }
        }
        return expandableNotificationRow.getPinnedHeadsUpHeight(true);
    }

    public final float getTotalTranslationLength(View view) {
        if (!this.mDismissUsingRowTranslationX) {
            return view.getMeasuredWidth();
        }
        float measuredWidth = view.getMeasuredWidth();
        float measuredWidth2 = getMeasuredWidth();
        return (measuredWidth2 - ((measuredWidth2 - measuredWidth) / 2.0f)) + 0.0f;
    }

    public final float getTouchSlop(MotionEvent motionEvent) {
        return motionEvent.getClassification() == 1 ? this.mTouchSlop * this.mSlopMultiplier : this.mTouchSlop;
    }

    public final void handleEmptySpaceClick(MotionEvent motionEvent) {
        boolean isBelowLastNotification = isBelowLastNotification(this.mInitialTouchY);
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
                    StringBuilder m76m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m76m("handleEmptySpaceClick: statusBarState: ", int1, " isTouchAClick: ", bool1, " isTouchBelowNotification: ");
                    m76m.append(bool2);
                    m76m.append(" motionEvent: ");
                    m76m.append(str1);
                    return m76m.toString();
                }
            };
            LogBuffer logBuffer = notificationStackScrollLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotificationStackScroll", logLevel, notificationStackScrollLogger$logEmptySpaceClick$2, null);
            obtain.setInt1(i);
            obtain.setBool1(z);
            obtain.setBool2(isBelowLastNotification);
            obtain.setStr1(actionToString);
            logBuffer.commit(obtain);
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1) {
            if (!this.mStateAnimator.mAnimatorSet.isEmpty()) {
                Log.d("StackScroller", "onEmptySpaceClicked is ignored by notification Animating..");
                return;
            }
            if (this.mStatusBarState != 1 && this.mTouchIsClick && isBelowLastNotification(this.mInitialTouchY)) {
                NotificationStackScrollLogger notificationStackScrollLogger2 = this.mLogger;
                if (notificationStackScrollLogger2 != null) {
                    LogBuffer.log$default(notificationStackScrollLogger2.buffer, "NotificationStackScroll", LogLevel.DEBUG, "handleEmptySpaceClick: touch event propagated further", null, 8, null);
                }
                this.mOnEmptySpaceClickListener.f$0.onEmptySpaceClick();
                return;
            }
            return;
        }
        if (actionMasked != 2) {
            NotificationStackScrollLogger notificationStackScrollLogger3 = this.mLogger;
            if (notificationStackScrollLogger3 == null) {
                return;
            }
            LogBuffer.log$default(notificationStackScrollLogger3.buffer, "NotificationStackScroll", LogLevel.DEBUG, "handleEmptySpaceClick: MotionEvent ignored", null, 8, null);
            return;
        }
        float touchSlop = getTouchSlop(motionEvent);
        if (this.mTouchIsClick) {
            if (Math.abs(motionEvent.getY() - this.mInitialTouchY) > touchSlop || Math.abs(motionEvent.getX() - this.mInitialTouchX) > touchSlop) {
                this.mTouchIsClick = false;
            }
        }
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return !this.mForceNoOverlappingRendering && super.hasOverlappingRendering();
    }

    public final void inflateEmptyShadeView() {
        int i;
        if (NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW) {
            return;
        }
        EmptyShadeView emptyShadeView = this.mEmptyShadeView;
        EmptyShadeView emptyShadeView2 = (EmptyShadeView) LayoutInflater.from(((ViewGroup) this).mContext).inflate(com.android.systemui.R.layout.status_bar_no_notifications, (ViewGroup) this, false);
        if (!NotiRune.NOTI_STYLE_EMPTY_SHADE) {
            emptyShadeView2.setOnClickListener(new NotificationStackScrollLayout$$ExternalSyntheticLambda0(this, r3));
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
        updateEmptyShadeView(emptyShadeView == null ? com.android.systemui.R.string.empty_shade_text : emptyShadeView.mText, emptyShadeView == null ? 0 : emptyShadeView.mFooterText, emptyShadeView != null ? emptyShadeView.mFooterIcon : 0);
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
        resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_section_divider_height);
        this.mStackScrollAlgorithm.initView(context);
        AmbientState ambientState = this.mAmbientState;
        ambientState.getClass();
        ambientState.mZDistanceBetweenElements = Math.max(1, context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.z_distance_between_notifications));
        this.mPaddingBetweenElements = Math.max(1, resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_divider_height));
        this.mMinTopOverScrollToEscape = resources.getDimensionPixelSize(com.android.systemui.R.dimen.min_top_overscroll_to_qs);
        this.mStatusBarHeight = SystemBarUtils.getStatusBarHeight(((ViewGroup) this).mContext);
        this.mBottomPadding = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_panel_padding_bottom);
        this.mMinimumPaddings = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_side_paddings);
        resources.getDimensionPixelOffset(com.android.systemui.R.dimen.qs_tile_margin_horizontal);
        resources.getBoolean(com.android.systemui.R.bool.config_skinnyNotifsInLandscape);
        this.mSidePaddings = this.mMinimumPaddings;
        this.mMinInteractionHeight = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_min_interaction_height);
        this.mCornerRadius = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_corner_radius);
        this.mHeadsUpInset = resources.getDimensionPixelSize(com.android.systemui.R.dimen.heads_up_status_bar_padding) + this.mStatusBarHeight;
        SystemBarUtils.getQuickQsOffsetHeight(((ViewGroup) this).mContext);
    }

    public final boolean isBelowLastNotification(float f) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            ExpandableView childAtIndex = getChildAtIndex(childCount);
            if (childAtIndex.getVisibility() != 8) {
                float y = childAtIndex.getY();
                if (y > f) {
                    return false;
                }
                boolean z = f > (y + ((float) childAtIndex.mActualHeight)) - ((float) childAtIndex.mClipBottomAmount);
                if (childAtIndex == this.mEmptyShadeView) {
                    return true;
                }
                if (!z) {
                    return false;
                }
            }
        }
        return f > ((float) this.mTopPadding) + this.mStackTranslation;
    }

    public boolean isDimmed() {
        AmbientState ambientState = this.mAmbientState;
        return ambientState.mDimmed && !(ambientState.isPulseExpanding() && ambientState.mDozeAmount == 1.0f);
    }

    public final boolean isFullySwipedOut(ExpandableView expandableView) {
        return Math.abs(expandableView.getTranslation()) >= Math.abs(getTotalTranslationLength(expandableView));
    }

    public final boolean isHeadsUpTransition() {
        return this.mAmbientState.getTrackedHeadsUpRow() != null;
    }

    public final boolean isRubberbanded(boolean z) {
        return !z || this.mExpandedInThisMotion || this.mIsExpansionChanging || this.mPanelTracking || !this.mScrolledToTopOnFirstDown;
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
                return FontProvider$$ExternalSyntheticOutline0.m32m("heads up animation skipped: key: ", logMessage.getStr1(), " reason: ", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = notificationStackScrollLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotificationStackScroll", logLevel, notificationStackScrollLogger$hunAnimationSkipped$2, null);
        obtain.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        obtain.setStr2(str);
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
    }

    public final void notifyOverscrollTopListener(float f, boolean z) {
        this.mExpandHelper.mOnlyMovements = f > 1.0f;
        if (this.mDontReportNextOverScroll) {
            this.mDontReportNextOverScroll = false;
            return;
        }
        QuickSettingsController.NsslOverscrollTopChangedListener nsslOverscrollTopChangedListener = this.mOverscrollTopChangedListener;
        if (nsslOverscrollTopChangedListener != null) {
            QuickSettingsController quickSettingsController = QuickSettingsController.this;
            if (!quickSettingsController.mSplitShadeEnabled && (quickSettingsController.mAmount != f || quickSettingsController.mIsRubberBanded != z)) {
                quickSettingsController.mAmount = f;
                quickSettingsController.mIsRubberBanded = z;
                ValueAnimator valueAnimator = quickSettingsController.mExpansionAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                float f2 = !quickSettingsController.isExpansionEnabled() ? 0.0f : f;
                if (f2 < 1.0f) {
                    f2 = 0.0f;
                }
                boolean z2 = f2 != 0.0f && z;
                quickSettingsController.mStackScrollerOverscrolling = z2;
                InterfaceC1922QS interfaceC1922QS = quickSettingsController.mQs;
                if (interfaceC1922QS != null) {
                    interfaceC1922QS.setOverscrolling(z2);
                }
                quickSettingsController.mExpansionFromOverscroll = f2 != 0.0f;
                quickSettingsController.mLastOverscroll = f2;
                quickSettingsController.updateQsState();
                quickSettingsController.setExpansionHeight(quickSettingsController.mMinExpansionHeight + f2);
            }
        }
        if (f < 1.0f) {
            f = 0.0f;
        }
        ((FullExpansionPanelNotiAlphaController) Dependency.get(FullExpansionPanelNotiAlphaController.class)).mStackScrollerOverscrolling = f != 0.0f && z;
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (!this.mAnimatedInsets) {
            this.mBottomInset = windowInsets.getInsets(WindowInsets.Type.ime()).bottom;
        }
        this.mWaterfallTopInset = 0;
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if (displayCutout != null) {
            this.mWaterfallTopInset = displayCutout.getWaterfallInsets().top;
        }
        if (this.mAnimatedInsets && !this.mIsInsetAnimationRunning) {
            updateBottomInset(windowInsets);
        }
        if (!this.mAnimatedInsets) {
            if (this.mOwnScrollY > getScrollRange()) {
                removeCallbacks(this.mReclamp);
                postDelayed(this.mReclamp, 50L);
            } else {
                View view = this.mForcedScroll;
                if (view != null) {
                    scrollTo(view);
                }
            }
        }
        return windowInsets;
    }

    public final void onChildHeightChanged(ExpandableView expandableView, boolean z) {
        boolean z2 = this.mAnimateStackYForContentHeightChange;
        if (z) {
            this.mAnimateStackYForContentHeightChange = true;
        }
        updateContentHeight();
        if (this.mOwnScrollY > 0 && (expandableView instanceof ExpandableNotificationRow) && !onKeyguard()) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
            if (expandableNotificationRow.mUserLocked && expandableNotificationRow != getFirstChildNotGone() && !expandableNotificationRow.mIsSummaryWithChildren) {
                float translationY = expandableNotificationRow.getTranslationY() + expandableNotificationRow.mActualHeight;
                if (expandableNotificationRow.isChildInGroup()) {
                    translationY += expandableNotificationRow.mNotificationParent.getTranslationY();
                }
                int i = this.mMaxLayoutHeight + ((int) this.mStackTranslation);
                NotificationSection lastVisibleSection = getLastVisibleSection();
                if (expandableNotificationRow != (lastVisibleSection == null ? null : lastVisibleSection.mLastVisibleChild) && this.mShelf.getVisibility() != 8) {
                    i -= this.mShelf.getHeight() + this.mPaddingBetweenElements;
                }
                float f = i;
                if (translationY > f) {
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
        this.mAnimateStackYForContentHeightChange = z2;
    }

    public final void onClearAllAnimationsEnd(int i, List list) {
        InteractionJankMonitor.getInstance().end(62);
        C2944xbae1b0c2 c2944xbae1b0c2 = this.mClearAllAnimationListener;
        if (c2944xbae1b0c2 != null) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = (NotificationStackScrollLayoutController) c2944xbae1b0c2.f$0;
            NotifCollection notifCollection = notificationStackScrollLayoutController.mNotifCollection;
            if (i == 0) {
                notifCollection.dismissAllNotifications(((NotificationLockscreenUserManagerImpl) notificationStackScrollLayoutController.mLockscreenUserManager).mCurrentUserId, false);
                return;
            }
            ArrayList arrayList = new ArrayList();
            Iterator it = list.iterator();
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
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        boolean z;
        int i;
        int i2;
        int i3;
        if (this.mShouldDrawNotificationBackground && NotiRune.NOTI_STYLE_TABLET_BG && this.mOpaqueBgHelper.needOpaqueBg()) {
            NotificationSection[] notificationSectionArr = this.mSections;
            int i4 = 1;
            if (notificationSectionArr[0].mCurrentBounds.top < notificationSectionArr[notificationSectionArr.length - 1].mCurrentBounds.bottom || this.mAmbientState.mDozing) {
                int i5 = this.mSidePaddings;
                int width = getWidth() - this.mSidePaddings;
                NotificationSection[] notificationSectionArr2 = this.mSections;
                int i6 = notificationSectionArr2[0].mCurrentBounds.top;
                int i7 = notificationSectionArr2[notificationSectionArr2.length - 1].mCurrentBounds.bottom;
                int width2 = getWidth() / 2;
                int i8 = this.mTopPadding;
                float f = 1.0f - this.mInterpolatedHideAmount;
                float interpolation = this.mHideXInterpolator.getInterpolation((1.0f - this.mLinearHideAmount) * this.mBackgroundXFactor);
                int lerp = (int) MathUtils.lerp(width2, i5, interpolation);
                int lerp2 = (int) MathUtils.lerp(width2, width, interpolation);
                int lerp3 = (int) MathUtils.lerp(i8, i6, f);
                this.mBackgroundAnimationRect.set(lerp, lerp3, lerp2, (int) MathUtils.lerp(i8, i7, f));
                int i9 = lerp3 - i6;
                NotificationSection[] notificationSectionArr3 = this.mSections;
                int length = notificationSectionArr3.length;
                int i10 = 0;
                while (true) {
                    if (i10 >= length) {
                        z = false;
                        break;
                    }
                    NotificationSection notificationSection = notificationSectionArr3[i10];
                    if ((notificationSection.mFirstVisibleChild == null || notificationSection.mBucket == 1) ? false : true) {
                        z = true;
                        break;
                    }
                    i10++;
                }
                if ((this.mKeyguardBypassEnabled && onKeyguard()) ? this.mAmbientState.isPulseExpanding() : !this.mAmbientState.mDozing || z) {
                    int i11 = this.mSections[0].mCurrentBounds.bottom + i9;
                    if (onKeyguard()) {
                        i11 = ((int) this.mShelf.getTranslationY()) + this.mShelf.getHeight();
                    }
                    NotificationSection[] notificationSectionArr4 = this.mSections;
                    int length2 = notificationSectionArr4.length;
                    int i12 = lerp;
                    int i13 = 0;
                    int i14 = lerp2;
                    boolean z2 = true;
                    while (i13 < length2) {
                        NotificationSection notificationSection2 = notificationSectionArr4[i13];
                        if (((notificationSection2.mFirstVisibleChild == null || notificationSection2.mBucket == i4) ? 0 : i4) == 0 || onKeyguard() || (NotiRune.NOTI_STYLE_TABLET_BG && this.mOpaqueBgHelper.needOpaqueBg())) {
                            i = lerp;
                            i2 = lerp2;
                            i3 = i4;
                        } else {
                            Rect rect = notificationSection2.mCurrentBounds;
                            int i15 = rect.top + i9;
                            int min = Math.min(Math.max(lerp, rect.left), lerp2);
                            int max = Math.max(Math.min(lerp2, rect.right), min);
                            i = lerp;
                            i2 = lerp2;
                            i3 = 1;
                            if (i15 - i11 > 1 || ((i12 != min || i14 != max) && !z2)) {
                                float f2 = this.mCornerRadius;
                                canvas.drawRoundRect(i12, lerp3, i14, i11, f2, f2, this.mBackgroundPaint);
                                lerp3 = i15;
                            }
                            i11 = rect.bottom + i9;
                            i12 = min;
                            i14 = max;
                            z2 = false;
                        }
                        i13++;
                        i4 = i3;
                        lerp = i;
                        lerp2 = i2;
                    }
                    boolean z3 = NotiRune.NOTI_STYLE_TABLET_BG;
                    if (z3 && this.mOpaqueBgHelper.needOpaqueBg()) {
                        SecNsslOpaqueBgHelper secNsslOpaqueBgHelper = this.mOpaqueBgHelper;
                        float f3 = z3 ? this.mRoundedRectClippingTop : this.mAmbientState.mNotificationScrimTop;
                        float translationY = this.mShelf.getTranslationY();
                        int height = this.mShelf.getHeight();
                        float f4 = this.mExpandedHeight;
                        int width3 = getWidth();
                        float f5 = this.mQsExpansionFraction;
                        int i16 = this.mCornerRadius;
                        Paint paint = this.mBackgroundPaint;
                        secNsslOpaqueBgHelper.getClass();
                        int i17 = translationY <= 0.0f ? (int) f4 : ((int) translationY) + height;
                        float f6 = width3;
                        float f7 = i17;
                        Path path = new Path();
                        float min2 = Math.min(1.0f, Math.max(0.0f, (f5 - 0.5f) / Math.max(0.0f, 0.15f)));
                        float f8 = i16;
                        float f9 = min2 * f8;
                        float f10 = f3 + f9;
                        path.moveTo(0.0f, f10);
                        path.quadTo(0.0f, f3, f9 + 0.0f, f3);
                        path.lineTo(f6 - f9, f3);
                        path.quadTo(f6, f3, f6, f10);
                        float f11 = f7 - f8;
                        path.lineTo(f6, f11);
                        path.quadTo(f6, f7, f6 - f8, f7);
                        path.lineTo(f8 + 0.0f, f7);
                        path.quadTo(0.0f, f7, 0.0f, f11);
                        path.lineTo(0.0f, f10);
                        canvas.drawPath(path, paint);
                    } else {
                        float f12 = this.mCornerRadius;
                        canvas.drawRoundRect(i12, lerp3, i14, i11, f12, f12, this.mBackgroundPaint);
                    }
                }
                updateClipping();
                return;
            }
        }
        if (this.mInHeadsUpPinnedMode || this.mHeadsUpAnimatingAway) {
            int i18 = this.mSidePaddings;
            int width4 = getWidth() - this.mSidePaddings;
            float height2 = getHeight();
            int childCount = getChildCount();
            float f13 = height2;
            float f14 = 0.0f;
            for (int i19 = 0; i19 < childCount; i19++) {
                View childAt = getChildAt(i19);
                if (childAt.getVisibility() != 8 && (childAt instanceof ExpandableNotificationRow)) {
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) childAt;
                    if (expandableNotificationRow.mIsPinned || expandableNotificationRow.mHeadsupDisappearRunning) {
                        if (expandableNotificationRow.getTranslation() < 0.0f && expandableNotificationRow.mMenuRow.shouldShowGutsOnSnapOpen()) {
                            f13 = Math.min(f13, expandableNotificationRow.getTranslationY());
                            f14 = Math.max(f14, expandableNotificationRow.getTranslationY() + expandableNotificationRow.mActualHeight);
                        }
                    }
                }
            }
            if (f13 < f14) {
                float f15 = this.mCornerRadius;
                canvas.drawRoundRect(i18, f13, width4, f14, f15, f15, this.mBackgroundPaint);
            }
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        inflateEmptyShadeView();
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
                int scrollRange = getScrollRange();
                int i = this.mOwnScrollY;
                int i2 = i - verticalScrollFactor;
                int i3 = i2 >= 0 ? i2 > scrollRange ? scrollRange : i2 : 0;
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
                            if (abs > getTouchSlop(motionEvent) && abs > abs2) {
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
            if (this.mScroller.springBack(((ViewGroup) this).mScrollX, this.mOwnScrollY, 0, 0, 0, getScrollRange())) {
                animateScroll();
            }
        } else {
            int y2 = (int) motionEvent.getY();
            this.mScrolledToTopOnFirstDown = NotificationStackScrollLayout.this.mOwnScrollY == 0;
            if (getChildAtPosition(motionEvent.getX(), false, false, y2) == null) {
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
        float width = getWidth() / 2.0f;
        for (int i5 = 0; i5 < getChildCount(); i5++) {
            float measuredWidth = r8.getMeasuredWidth() / 2.0f;
            getChildAt(i5).layout((int) (width - measuredWidth), 0, (int) (measuredWidth + width), r8.getMeasuredHeight());
        }
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
        if (((KeyguardFoldControllerImpl) this.mKeyguardFoldController).isUnlockOnFoldOpened() || ((((KeyguardFoldControllerImpl) this.mKeyguardFoldController).isBouncerOnFoldOpened() && !this.mJustBackFromOcclude) || this.mController.mHasDelayedForceLayout)) {
            if (!this.mForceLayoutFirstMeasure) {
                StringBuilder sb = new StringBuilder("stackScroller Skip measure flag on! by : ");
                sb.append(((KeyguardFoldControllerImpl) this.mKeyguardFoldController).isUnlockOnFoldOpened());
                sb.append(" : ");
                sb.append(((KeyguardFoldControllerImpl) this.mKeyguardFoldController).isBouncerOnFoldOpened());
                sb.append(" : ");
                ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, this.mController.mHasDelayedForceLayout, "StackScroller");
            }
            this.mForceLayoutFirstMeasure = true;
            return;
        }
        this.mJustBackFromOcclude = false;
        if (this.mForceLayoutFirstMeasure) {
            Log.d("StackScroller", "stackScroller forcelayout measure!");
            this.mForceLayoutFirstMeasure = false;
        }
        if (this.mIsVisibleFromGone) {
            this.mIsVisibleFromGone = false;
            Log.d("StackScroller", "visible from gone, fisrt measure!");
        }
        int size = View.MeasureSpec.getSize(i);
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        Context context = ((ViewGroup) this).mContext;
        secQSPanelResourcePicker.getClass();
        int notificationSidePadding = SecQSPanelResourcePicker.getNotificationSidePadding(context);
        this.mSidePaddings = notificationSidePadding;
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size - (notificationSidePadding * 2), View.MeasureSpec.getMode(i));
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), 0);
        int i3 = this.mOnMeasureCount;
        if (i3 == 1) {
            this.mOnMeasureStartTime = System.currentTimeMillis();
        } else if (i3 >= 200) {
            this.mOnMeasureCount = 0;
            if (System.currentTimeMillis() - this.mOnMeasureStartTime < 5000) {
                Log.d("StackScroller", "too many onMeasure for nssl");
            }
        }
        this.mOnMeasureCount++;
        int childCount = getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            measureChild(getChildAt(i4), makeMeasureSpec, makeMeasureSpec2);
        }
        Trace.endSection();
    }

    public final void onMediaPlayerScroll(MotionEvent motionEvent) {
        if (this.mScrollingEnabled) {
            this.mForcedScroll = null;
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement(motionEvent);
            int actionMasked = motionEvent.getActionMasked();
            if (this.mShouldMediaPlayerDraggingStarted) {
                if (getChildCount() == 0) {
                    return;
                }
                if (!(motionEvent.getY() < ((float) (getHeight() - getEmptyBottomMargin())))) {
                    return;
                }
                setIsBeingDragged(!this.mScroller.isFinished());
                if (!this.mScroller.isFinished()) {
                    this.mScroller.forceFinished(true);
                }
                this.mLastMotionY = (int) motionEvent.getY();
                this.mDownX = (int) motionEvent.getX();
                this.mActivePointerId = motionEvent.getPointerId(0);
                this.mShouldMediaPlayerDraggingStarted = false;
            }
            int i = 3;
            if (actionMasked == 1) {
                if (this.mIsBeingDragged) {
                    VelocityTracker velocityTracker = this.mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                    int yVelocity = (int) velocityTracker.getYVelocity(this.mActivePointerId);
                    if (shouldOverScrollFling(yVelocity)) {
                        onOverScrollFling(yVelocity, true);
                    } else if (getChildCount() > 0) {
                        if (Math.abs(yVelocity) > this.mMinimumVelocity) {
                            if (getCurrentOverScrollAmount(true) == 0.0f || yVelocity > 0) {
                                this.mFlingAfterUpEvent = true;
                                this.mFinishScrollingCallback = new NotificationStackScrollLayout$$ExternalSyntheticLambda2(this, i);
                                fling(-yVelocity);
                            } else {
                                onOverScrollFling(yVelocity, false);
                            }
                        } else if (this.mScroller.springBack(((ViewGroup) this).mScrollX, this.mOwnScrollY, 0, 0, 0, getScrollRange())) {
                            animateScroll();
                        } else if (this.mOwnScrollY > 0) {
                            int scrollAmountToScrollBoundary = getScrollAmountToScrollBoundary();
                            int i2 = this.mOwnScrollY;
                            if (scrollAmountToScrollBoundary > i2) {
                                this.mScroller.startScroll(((ViewGroup) this).mScrollX, i2, 0, -i2, 1050);
                                animateScroll();
                            }
                        }
                    }
                    this.mActivePointerId = -1;
                    endDrag();
                    this.mShouldMediaPlayerDraggingStarted = false;
                    return;
                }
                return;
            }
            if (actionMasked != 2) {
                if (actionMasked != 3) {
                    return;
                }
                if (this.mIsBeingDragged && getChildCount() > 0) {
                    if (this.mScroller.springBack(((ViewGroup) this).mScrollX, this.mOwnScrollY, 0, 0, 0, getScrollRange())) {
                        animateScroll();
                    }
                    this.mActivePointerId = -1;
                    endDrag();
                }
                this.mShouldMediaPlayerDraggingStarted = false;
                return;
            }
            int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
            int pointerCount = motionEvent.getPointerCount();
            if (findPointerIndex < 0 || pointerCount <= findPointerIndex) {
                Log.e("StackScroller", "Invalid pointerId=" + this.mActivePointerId + " in onMediaPlayerScroll");
                return;
            }
            int y = (int) motionEvent.getY(findPointerIndex);
            int x = (int) motionEvent.getX(findPointerIndex);
            int i3 = this.mLastMotionY - y;
            int abs = Math.abs(x - this.mDownX);
            int abs2 = Math.abs(i3);
            float touchSlop = getTouchSlop(motionEvent);
            if (!this.mIsBeingDragged && abs2 > touchSlop && abs2 > abs) {
                setIsBeingDragged(true);
                float f = i3;
                i3 = (int) (i3 > 0 ? f - touchSlop : f + touchSlop);
            }
            if (this.mIsBeingDragged) {
                this.mLastMotionY = y;
                int scrollRange = getScrollRange();
                if (this.mExpandedInThisMotion) {
                    scrollRange = Math.min(scrollRange, this.mMaxScrollAfterExpand);
                }
                float overScrollDown = i3 < 0 ? overScrollDown(i3) : overScrollUp(i3, scrollRange);
                if (overScrollDown != 0.0f) {
                    customOverScrollBy((int) overScrollDown, this.mOwnScrollY, scrollRange, getHeight() / 2);
                    this.mController.checkSnoozeLeavebehind();
                }
            }
        }
    }

    public final void onOverScrollFling(int i, boolean z) {
        QuickSettingsController.NsslOverscrollTopChangedListener nsslOverscrollTopChangedListener = this.mOverscrollTopChangedListener;
        if (nsslOverscrollTopChangedListener != null) {
            float f = i;
            QuickSettingsController quickSettingsController = QuickSettingsController.this;
            float f2 = quickSettingsController.mInitialTouchX;
            boolean z2 = quickSettingsController.mSplitShadeEnabled;
            FrameLayout frameLayout = quickSettingsController.mQsFrame;
            if (!((z2 && f2 < frameLayout.getX()) || f2 > frameLayout.getX() + ((float) frameLayout.getWidth()))) {
                quickSettingsController.mLastOverscroll = 0.0f;
                quickSettingsController.mExpansionFromOverscroll = false;
                if (z) {
                    quickSettingsController.mStackScrollerOverscrolling = false;
                    InterfaceC1922QS interfaceC1922QS = quickSettingsController.mQs;
                    if (interfaceC1922QS != null) {
                        interfaceC1922QS.setOverscrolling(false);
                    }
                }
                quickSettingsController.setExpansionHeight(quickSettingsController.mExpansionHeight);
                boolean isExpansionEnabled = quickSettingsController.isExpansionEnabled();
                if (!isExpansionEnabled && z) {
                    f = 0.0f;
                }
                quickSettingsController.flingQs(f, (z && isExpansionEnabled) ? 0 : 1, new QuickSettingsController$$ExternalSyntheticLambda5(nsslOverscrollTopChangedListener, 6), false);
            }
        }
        this.mDontReportNextOverScroll = true;
        setOverScrollAmount(0.0f, true, false, true);
    }

    public final boolean onScrollTouch(MotionEvent motionEvent) {
        if (!this.mScrollingEnabled) {
            return false;
        }
        this.mQsHeader.getBoundsOnScreen(this.mQsHeaderBound);
        this.mQsHeaderBound.offsetTo(Math.round((motionEvent.getRawX() - motionEvent.getX()) + this.mQsHeader.getLeft()), Math.round(motionEvent.getRawY() - motionEvent.getY()));
        if (this.mQsHeaderBound.contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY()) && !this.mIsBeingDragged) {
            return false;
        }
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
        if (actionMasked == 0) {
            if (getChildCount() != 0) {
                if (motionEvent.getY() < ((float) (getHeight() - getEmptyBottomMargin()))) {
                    setIsBeingDragged(!this.mScroller.isFinished());
                    if (!this.mScroller.isFinished()) {
                        this.mScroller.forceFinished(true);
                    }
                    this.mLastMotionY = (int) motionEvent.getY();
                    this.mDownX = (int) motionEvent.getX();
                    this.mActivePointerId = motionEvent.getPointerId(0);
                }
            }
            return false;
        }
        if (actionMasked != 1) {
            if (actionMasked == 2) {
                int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                if (findPointerIndex == -1) {
                    Log.e("StackScroller", "Invalid pointerId=" + this.mActivePointerId + " in onTouchEvent");
                } else {
                    int y = (int) motionEvent.getY(findPointerIndex);
                    int x = (int) motionEvent.getX(findPointerIndex);
                    int i = this.mLastMotionY - y;
                    int abs = Math.abs(x - this.mDownX);
                    int abs2 = Math.abs(i);
                    float touchSlop = getTouchSlop(motionEvent);
                    if (!this.mIsBeingDragged && abs2 > touchSlop && abs2 > abs) {
                        setIsBeingDragged(true);
                        float f = i;
                        i = (int) (i > 0 ? f - touchSlop : f + touchSlop);
                    }
                    if (this.mIsBeingDragged) {
                        this.mLastMotionY = y;
                        int scrollRange = getScrollRange();
                        if (this.mExpandedInThisMotion) {
                            scrollRange = Math.min(scrollRange, this.mMaxScrollAfterExpand);
                        }
                        float overScrollDown = i < 0 ? overScrollDown(i) : overScrollUp(i, scrollRange);
                        if (overScrollDown != 0.0f) {
                            customOverScrollBy((int) overScrollDown, this.mOwnScrollY, scrollRange, getHeight() / 2);
                            this.mController.checkSnoozeLeavebehind();
                        }
                    }
                }
            } else if (actionMasked != 3) {
                if (actionMasked == 5) {
                    int actionIndex = motionEvent.getActionIndex();
                    this.mLastMotionY = (int) motionEvent.getY(actionIndex);
                    this.mDownX = (int) motionEvent.getX(actionIndex);
                    this.mActivePointerId = motionEvent.getPointerId(actionIndex);
                } else if (actionMasked == 6) {
                    onSecondaryPointerUp(motionEvent);
                    this.mLastMotionY = (int) motionEvent.getY(motionEvent.findPointerIndex(this.mActivePointerId));
                    this.mDownX = (int) motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId));
                }
            } else if (this.mIsBeingDragged && getChildCount() > 0) {
                if (this.mScroller.springBack(((ViewGroup) this).mScrollX, this.mOwnScrollY, 0, 0, 0, getScrollRange())) {
                    animateScroll();
                }
                this.mActivePointerId = -1;
                endDrag();
            }
        } else if (this.mIsBeingDragged) {
            VelocityTracker velocityTracker = this.mVelocityTracker;
            velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
            int yVelocity = (int) velocityTracker.getYVelocity(this.mActivePointerId);
            if (shouldOverScrollFling(yVelocity)) {
                onOverScrollFling(yVelocity, true);
            } else if (getChildCount() > 0) {
                if (Math.abs(yVelocity) > this.mMinimumVelocity) {
                    if (getCurrentOverScrollAmount(true) == 0.0f || yVelocity > 0) {
                        this.mFlingAfterUpEvent = true;
                        this.mFinishScrollingCallback = new NotificationStackScrollLayout$$ExternalSyntheticLambda2(this, 4);
                        fling(-yVelocity);
                    } else {
                        onOverScrollFling(yVelocity, false);
                    }
                } else if (this.mScroller.springBack(((ViewGroup) this).mScrollX, this.mOwnScrollY, 0, 0, 0, getScrollRange())) {
                    animateScroll();
                } else if (this.mOwnScrollY > 0) {
                    int scrollAmountToScrollBoundary = getScrollAmountToScrollBoundary();
                    int i2 = this.mOwnScrollY;
                    if (scrollAmountToScrollBoundary > i2) {
                        this.mScroller.startScroll(((ViewGroup) this).mScrollX, i2, 0, -i2, 1050);
                        animateScroll();
                    }
                }
            }
            this.mActivePointerId = -1;
            endDrag();
        }
        return true;
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

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        NotificationStackScrollLayoutController.TouchHandler touchHandler = this.mTouchHandler;
        if (touchHandler == null || !touchHandler.onTouchEvent(motionEvent)) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
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
        FeatureFlags featureFlags = this.mAmbientState.mFeatureFlags;
        Flags flags = Flags.INSTANCE;
        featureFlags.getClass();
        if (this.mOnNotificationRemovedListener != null) {
            expandableView.requestRoundnessReset(NotificationShelf.SHELF_SCROLL);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:65:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onViewRemovedInternal(ExpandableView expandableView, ViewGroup viewGroup) {
        boolean z;
        boolean z2;
        if (this.mChangePositionInProgress) {
            return;
        }
        expandableView.mOnHeightChangedListener = null;
        boolean z3 = expandableView instanceof ExpandableNotificationRow;
        int positionInLinearLayout = getPositionInLinearLayout(expandableView);
        int intrinsicHeight = expandableView.getIntrinsicHeight() + this.mPaddingBetweenElements;
        int i = positionInLinearLayout + intrinsicHeight;
        int scrollAmountToScrollBoundary = getScrollAmountToScrollBoundary();
        boolean z4 = true;
        this.mAnimateStackYForContentHeightChange = true;
        int i2 = this.mOwnScrollY;
        int i3 = i2 - scrollAmountToScrollBoundary;
        if (i <= i3) {
            setOwnScrollY(i2 - intrinsicHeight);
        } else if (positionInLinearLayout < i3) {
            setOwnScrollY(positionInLinearLayout + scrollAmountToScrollBoundary);
        }
        if (viewGroup != null) {
            Iterator it = this.mHeadsUpChangeAnimations.iterator();
            boolean z5 = false;
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) pair.first;
                boolean booleanValue = ((Boolean) pair.second).booleanValue();
                if (expandableView == expandableNotificationRow) {
                    this.mTmpList.add(pair);
                    z5 |= booleanValue;
                }
            }
            if (z5) {
                this.mHeadsUpChangeAnimations.removeAll(this.mTmpList);
                ((ExpandableNotificationRow) expandableView).setHeadsUpAnimatingAway(false);
            }
            this.mTmpList.clear();
            if (z5 && this.mAddedHeadsUpChildren.contains(expandableView)) {
                this.mAddedHeadsUpChildren.remove(expandableView);
            } else {
                Boolean bool = (Boolean) expandableView.getTag(com.android.systemui.R.id.is_clicked_heads_up_tag);
                if (bool != null && bool.booleanValue()) {
                    this.mClearTransientViewsWhenFinished.add(expandableView);
                } else if (this.mIsExpanded && this.mAnimationsEnabled) {
                    if (this.mChildrenToAddAnimated.contains(expandableView)) {
                        this.mChildrenToAddAnimated.remove(expandableView);
                        this.mFromMoreCardAdditions.remove(expandableView);
                    } else {
                        this.mChildrenToRemoveAnimated.add(expandableView);
                        this.mNeedsAnimation = true;
                    }
                }
                z2 = true;
                if (z2) {
                    z = true;
                    if (((NotificationIconTransitionController) Dependency.get(NotificationIconTransitionController.class)).mNeedAnimForRemoval) {
                        if (!z) {
                            this.mSwipedOutViews.remove(expandableView);
                            if (z3) {
                                ((ExpandableNotificationRow) expandableView).removeChildrenWithKeepInParent();
                            }
                        } else if (!this.mSwipedOutViews.contains(expandableView) || !isFullySwipedOut(expandableView)) {
                            Log.d("StackScroller", "onViewRemovedInternal animationGenerated addTransientView : " + expandableView);
                            viewGroup.addTransientView(expandableView, 0);
                            expandableView.mTransientContainer = viewGroup;
                            Log.d("StackScroller", "onViewRemovedInternal enqueue next animation");
                        }
                    }
                    if (z3) {
                        ((ExpandableNotificationRow) expandableView).setAnimationRunning(false);
                    }
                    if (z3) {
                        return;
                    }
                    ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) expandableView;
                    if (!expandableNotificationRow2.mRefocusOnDismiss && !expandableNotificationRow2.isAccessibilityFocused()) {
                        z4 = false;
                    }
                    if (z4) {
                        View view = expandableNotificationRow2.mChildAfterViewWhenDismissed;
                        if (view == null) {
                            ExpandableNotificationRow expandableNotificationRow3 = expandableNotificationRow2.mGroupParentWhenDismissed;
                            view = getFirstChildBelowTranlsationY(expandableNotificationRow3 != null ? expandableNotificationRow3.getTranslationY() : expandableView.getTranslationY());
                        }
                        if (view != null) {
                            view.requestAccessibilityFocus();
                            return;
                        }
                        return;
                    }
                    return;
                }
            }
            z2 = false;
            if (z2) {
            }
        }
        z = false;
        if (((NotificationIconTransitionController) Dependency.get(NotificationIconTransitionController.class)).mNeedAnimForRemoval) {
        }
        if (z3) {
        }
        if (z3) {
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

    public final float overScrollDown(int i) {
        int min = Math.min(i, 0);
        float currentOverScrollAmount = getCurrentOverScrollAmount(false);
        float f = min + currentOverScrollAmount;
        if (currentOverScrollAmount > 0.0f) {
            setOverScrollAmount(f, false, false, true);
        }
        if (f >= 0.0f) {
            f = 0.0f;
        }
        float f2 = this.mOwnScrollY + f;
        if (f2 >= 0.0f) {
            return f;
        }
        setOverScrollAmount(getRubberBandFactor(true) * (this.mOverScrolledTopPixels - f2), true, false, true);
        setOwnScrollY(0);
        return 0.0f;
    }

    public final float overScrollUp(int i, int i2) {
        int max = Math.max(i, 0);
        float currentOverScrollAmount = getCurrentOverScrollAmount(true);
        float f = currentOverScrollAmount - max;
        if (currentOverScrollAmount > 0.0f) {
            setOverScrollAmount(f, true, false, true);
        }
        float f2 = f < 0.0f ? -f : 0.0f;
        float f3 = this.mOwnScrollY + f2;
        float f4 = i2;
        if (f3 <= f4) {
            return f2;
        }
        if (!this.mExpandedInThisMotion) {
            setOverScrollAmount(getRubberBandFactor(false) * ((this.mOverScrolledBottomPixels + f3) - f4), false, false, true);
        }
        setOwnScrollY(i2);
        return 0.0f;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0020, code lost:
    
        if (r5 != 16908346) goto L23;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        int i2;
        int max;
        int i3;
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        if (!isEnabled()) {
            return false;
        }
        if (i != 4096) {
            if (i == 8192 || i == 16908344) {
                i2 = -1;
                max = Math.max(0, Math.min((i2 * ((((getHeight() - ((ViewGroup) this).mPaddingBottom) - this.mTopPadding) - ((ViewGroup) this).mPaddingTop) - this.mShelf.getHeight())) + this.mOwnScrollY, getScrollRange()));
                i3 = this.mOwnScrollY;
                if (max != i3) {
                    this.mScroller.startScroll(((ViewGroup) this).mScrollX, i3, 0, max - i3);
                    animateScroll();
                    return true;
                }
                return false;
            }
        }
        i2 = 1;
        max = Math.max(0, Math.min((i2 * ((((getHeight() - ((ViewGroup) this).mPaddingBottom) - this.mTopPadding) - ((ViewGroup) this).mPaddingTop) - this.mShelf.getHeight())) + this.mOwnScrollY, getScrollRange()));
        i3 = this.mOwnScrollY;
        if (max != i3) {
        }
        return false;
    }

    public final void requestChildrenUpdate() {
        int i;
        if (this.mAnimateNextTopPaddingChange) {
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("requestChildrenUpdate : after mAnimateNextTopPaddingChange :  "), this.mChildrenUpdateRequested, "StackScroller");
        }
        if (this.mChildrenUpdateRequested) {
            return;
        }
        if (!(this.mPanelExpansionStateNotifier.mModel.panelOpenState == 2) || (i = this.mDisplayState) == 4 || i == 3 || getVisibility() == 8) {
            int i2 = this.mChildrenUpdateCount;
            if (i2 == 1) {
                this.mChildrenUpdateStartTime = System.currentTimeMillis();
            } else if (i2 >= 300) {
                this.mChildrenUpdateCount = 0;
                if (System.currentTimeMillis() - this.mChildrenUpdateStartTime < 5000) {
                    Log.d("StackScroller", "too many onPreDraw for nssl by trace : " + Log.getStackTraceString(new Throwable()));
                }
            }
            this.mChildrenUpdateCount++;
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
        Trace.beginSection("NSSL.resetAllSwipeState()");
        this.mSwipeHelper.resetSwipeStates(true);
        for (int i = 0; i < getChildCount(); i++) {
            NotificationSwipeHelper notificationSwipeHelper = this.mSwipeHelper;
            View childAt = getChildAt(i);
            notificationSwipeHelper.getClass();
            if (childAt.getTranslationX() != 0.0f) {
                notificationSwipeHelper.setTranslation(childAt, 0.0f);
                notificationSwipeHelper.updateSwipeProgressFromOffset(childAt, 0.0f, true);
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

    public final int scrollAmountForKeyboardFocus(int i, boolean z) {
        if (z) {
            View childAt = getChildAt(i + 1);
            if ((childAt instanceof ExpandableNotificationRow) && childAt.getY() + childAt.getHeight() > this.mShelf.getY()) {
                return ((ExpandableNotificationRow) childAt).mActualHeight + this.mPaddingBetweenElements;
            }
        }
        return 0;
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
        super.setAlpha(f);
    }

    public void setAnimatedInsetsEnabled(boolean z) {
        this.mAnimatedInsets = z;
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

    public final void setDimmed(boolean z, boolean z2) {
        boolean onKeyguard = z & onKeyguard();
        this.mAmbientState.mDimmed = onKeyguard;
        if (z2 && this.mAnimationsEnabled) {
            this.mDimmedNeedsAnimation = true;
            this.mNeedsAnimation = true;
            ValueAnimator valueAnimator = this.mDimAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            float f = onKeyguard ? 1.0f : 0.0f;
            float f2 = this.mDimAmount;
            if (f != f2) {
                ValueAnimator ofFloat = TimeAnimator.ofFloat(f2, f);
                this.mDimAnimator = ofFloat;
                ofFloat.setDuration(220L);
                this.mDimAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                this.mDimAnimator.addListener(this.mDimEndListener);
                this.mDimAnimator.addUpdateListener(this.mDimUpdateListener);
                this.mDimAnimator.start();
            }
        } else {
            this.mDimAmount = onKeyguard ? 1.0f : 0.0f;
            updateBackgroundDimming();
        }
        requestChildrenUpdate();
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x0190, code lost:
    
        if ((r13 == ((float) r4)) != false) goto L79;
     */
    /* JADX WARN: Removed duplicated region for block: B:37:0x01a0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setExpandedHeight(float f) {
        int i;
        boolean shouldSkipHeightUpdate = shouldSkipHeightUpdate();
        updateStackPosition(false);
        float f2 = 0.0f;
        if (!shouldSkipHeightUpdate) {
            this.mExpandedHeight = f;
            setIsExpanded(f > 0.0f);
            float minExpansionHeight = getMinExpansionHeight();
            if (f >= minExpansionHeight || this.mShouldUseSplitNotificationShade) {
                this.mRequestedClipBounds = null;
                updateClipping();
            } else {
                Rect rect = this.mClipRect;
                rect.left = 0;
                rect.right = getWidth();
                Rect rect2 = this.mClipRect;
                rect2.top = 0;
                rect2.bottom = (int) f;
                this.mRequestedClipBounds = rect2;
                updateClipping();
                f = minExpansionHeight;
            }
        }
        float f3 = 1.0f;
        boolean z = calculateAppearFraction(f) < 1.0f;
        this.mAmbientState.mAppearing = z;
        if (z) {
            f3 = calculateAppearFraction(f);
            float interpolate = f3 >= 0.0f ? NotificationUtils.interpolate((getMinExpansionHeight() + (-this.mTopPadding)) - this.mShelf.getHeight(), 0.0f, f3) : (f - getAppearStartPosition()) + ((getMinExpansionHeight() + (-this.mTopPadding)) - this.mShelf.getHeight());
            int i2 = (int) (f - interpolate);
            if (isHeadsUpTransition() && f3 >= 0.0f) {
                interpolate = MathUtils.lerp(this.mHeadsUpInset - (this.mShouldUseSplitNotificationShade ? this.mAmbientState.mStackTopMargin : this.mTopPadding), 0.0f, f3);
            }
            if (NotiRune.NOTI_STATIC_SHELF_ALPHA_VI && !this.mShelfAlphaOutAnimating) {
                if (LsRune.AOD_FULLSCREEN && ((SecUnlockedScreenOffAnimationHelper) Dependency.get(SecUnlockedScreenOffAnimationHelper.class)).shouldSkipAnimation()) {
                    this.mShelf.mShelfIcons.setAlpha(0.0f);
                } else {
                    this.mShelf.mShelfIcons.animate().alpha(0.0f).setDuration(100L).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.13
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
            f2 = interpolate;
            i = i2;
        } else {
            if (this.mShouldShowShelfOnly) {
                i = this.mShelf.getHeight() + this.mTopPadding;
            } else if (this.mQsFullScreen) {
                int i3 = (this.mContentHeight - this.mTopPadding) + this.mIntrinsicPadding;
                i = this.mMaxTopPadding + this.mShelf.getHeight();
                if (i3 > i) {
                    i = this.mShouldUseSplitNotificationShade ? (int) f : (int) NotificationUtils.interpolate(i3, i, this.mQsExpansionFraction);
                }
            } else {
                i = (int) (shouldSkipHeightUpdate ? this.mExpandedHeight : f);
            }
            if (NotiRune.NOTI_STATIC_SHELF_ALPHA_VI && !this.mShelfAlphaInAnimating) {
                if (LsRune.AOD_FULLSCREEN && ((SecUnlockedScreenOffAnimationHelper) Dependency.get(SecUnlockedScreenOffAnimationHelper.class)).shouldSkipAnimation()) {
                    this.mShelf.mShelfIcons.setAlpha(1.0f);
                } else {
                    this.mShelf.mShelfIcons.animate().alpha(1.0f).setDuration(100L).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.12
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
        if (i == this.mCurrentStackHeight || shouldSkipHeightUpdate) {
            if (NotiRune.NOTI_STYLE_TABLET_BG) {
                SecNsslOpaqueBgHelper secNsslOpaqueBgHelper = this.mOpaqueBgHelper;
                int minExpansionHeight2 = getMinExpansionHeight();
                secNsslOpaqueBgHelper.getClass();
            }
            if (f2 != this.mStackTranslation) {
                this.mStackTranslation = f2;
                this.mAmbientState.mStackTranslation = f2;
                requestChildrenUpdate();
            }
            notifyAppearChangedListeners();
        }
        this.mCurrentStackHeight = i;
        updateAlgorithmHeightAndPadding();
        requestChildrenUpdate();
        if (f2 != this.mStackTranslation) {
        }
        notifyAppearChangedListeners();
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
        boolean z2 = z != this.mIsExpanded;
        this.mIsExpanded = z;
        this.mStackScrollAlgorithm.mIsExpanded = z;
        this.mAmbientState.mShadeExpanded = z;
        this.mStateAnimator.mShadeExpanded = z;
        NotificationSwipeHelper notificationSwipeHelper = this.mSwipeHelper;
        notificationSwipeHelper.mIsExpanded = z;
        if (!this.mInHeadsUpPinnedMode && !z) {
            ExpandableView expandableView = notificationSwipeHelper.mTouchedView;
            if (expandableView != null) {
                notificationSwipeHelper.snapChild(expandableView, 0.0f, 0.0f);
            }
            if (z2 && !this.mChildrenToAddAnimated.isEmpty()) {
                this.mChildrenToAddAnimated.clear();
                Log.d("StackScroller", " mChildrenToAddAnimated will be cleared.. ");
            }
        }
        if (z2) {
            this.mWillExpand = false;
            if (!this.mIsExpanded) {
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

    @Override // android.view.View
    public final void setPivotX(float f) {
        if (((NotificationIconTransitionController) Dependency.get(NotificationIconTransitionController.class)).misTransformAnimating) {
            return;
        }
        super.setPivotX(f);
    }

    @Override // android.view.View
    public final void setPivotY(float f) {
        if (((NotificationIconTransitionController) Dependency.get(NotificationIconTransitionController.class)).misTransformAnimating) {
            return;
        }
        super.setPivotY(f);
    }

    public final float setPulseHeight(float f) {
        float max;
        AmbientState ambientState = this.mAmbientState;
        if (f != ambientState.mPulseHeight) {
            ambientState.mPulseHeight = f;
            Runnable runnable = ambientState.mOnPulseHeightChangedListener;
            if (runnable != null) {
                runnable.run();
            }
        }
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
        this.mStatusBarState = i;
        AmbientState ambientState = this.mAmbientState;
        if (ambientState.mStatusBarState != 1) {
            ambientState.mIsFlingRequiredAfterLockScreenSwipeUp = false;
        }
        ambientState.mStatusBarState = i;
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

    public final boolean shouldOverScrollFling(int i) {
        float currentOverScrollAmount = getCurrentOverScrollAmount(true);
        if (this.mOwnScrollY == 0 && this.mScrolledToTopOnFirstDown && !this.mExpandedInThisMotion && !this.mShouldUseSplitNotificationShade) {
            if (i > this.mMinimumVelocity) {
                return true;
            }
            if (currentOverScrollAmount > this.mMinTopOverScrollToEscape && i > 0) {
                return true;
            }
        }
        return false;
    }

    public final boolean shouldSkipHeightUpdate() {
        if (!this.mAmbientState.isOnKeyguard()) {
            return false;
        }
        AmbientState ambientState = this.mAmbientState;
        if (!ambientState.mUnlockHintRunning && !ambientState.mIsSwipingUp) {
            if (!(ambientState.mIsFlinging && ambientState.mIsFlingRequiredAfterLockScreenSwipeUp)) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:133:0x01e9  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0287  */
    /* JADX WARN: Removed duplicated region for block: B:328:0x0623  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void startAnimationToState$1() {
        boolean z;
        int i;
        HashSet hashSet;
        HashSet hashSet2;
        AnimationFilter animationFilter;
        ArrayList arrayList;
        boolean z2;
        long j;
        Iterator it;
        AnimationFilter animationFilter2;
        ExpandableView expandableView;
        double d;
        long j2;
        boolean z3;
        final String str;
        boolean z4;
        boolean z5;
        final int i2;
        final Runnable runnable;
        float f;
        int i3;
        String str2;
        Runnable runnable2;
        float f2;
        boolean z6;
        Integer num;
        int i4;
        int i5;
        boolean z7;
        String str3;
        NotificationStackScrollLayout notificationStackScrollLayout = this;
        String str4 = null;
        if (notificationStackScrollLayout.mNeedsAnimation) {
            Iterator it2 = notificationStackScrollLayout.mHeadsUpChangeAnimations.iterator();
            while (it2.hasNext()) {
                Pair pair = (Pair) it2.next();
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) pair.first;
                boolean booleanValue = ((Boolean) pair.second).booleanValue();
                boolean z8 = expandableNotificationRow.mIsHeadsUp;
                if (booleanValue != z8) {
                    NotificationStackScrollLogger notificationStackScrollLogger = notificationStackScrollLayout.mLogger;
                    if (notificationStackScrollLogger != null) {
                        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
                        LogLevel logLevel = LogLevel.INFO;
                        NotificationStackScrollLogger$hunSkippedForUnexpectedState$2 notificationStackScrollLogger$hunSkippedForUnexpectedState$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$hunSkippedForUnexpectedState$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                LogMessage logMessage = (LogMessage) obj;
                                return "HUN animation skipped for unexpected hun state: key: " + logMessage.getStr1() + " expected: " + logMessage.getBool1() + " actual: " + logMessage.getBool2();
                            }
                        };
                        LogBuffer logBuffer = notificationStackScrollLogger.buffer;
                        LogMessage obtain = logBuffer.obtain("NotificationStackScroll", logLevel, notificationStackScrollLogger$hunSkippedForUnexpectedState$2, null);
                        obtain.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
                        obtain.setBool1(booleanValue);
                        obtain.setBool2(z8);
                        logBuffer.commit(obtain);
                    }
                } else {
                    boolean z9 = expandableNotificationRow.mIsPinned && !notificationStackScrollLayout.mIsExpanded;
                    if (!(!notificationStackScrollLayout.mIsExpanded || (notificationStackScrollLayout.mKeyguardBypassEnabled && onKeyguard() && notificationStackScrollLayout.mInHeadsUpPinnedMode)) || booleanValue) {
                        ExpandableViewState expandableViewState = expandableNotificationRow.mViewState;
                        if (expandableViewState == null) {
                            notificationStackScrollLayout.logHunAnimationSkipped(expandableNotificationRow, "row has no viewState");
                        } else if (booleanValue && (notificationStackScrollLayout.mAddedHeadsUpChildren.contains(expandableNotificationRow) || z9)) {
                            if (!z9) {
                                if (!(expandableViewState.mYTranslation + ((float) expandableViewState.height) >= notificationStackScrollLayout.mAmbientState.mMaxHeadsUpTranslation)) {
                                    i5 = 0;
                                    z7 = !z9;
                                }
                            }
                            i5 = 11;
                            z7 = !z9;
                        } else {
                            i4 = 14;
                            i5 = i4;
                            z7 = false;
                        }
                    } else {
                        i4 = expandableNotificationRow.mJustClicked ? 13 : 12;
                        if (expandableNotificationRow.isChildInGroup()) {
                            expandableNotificationRow.setHeadsUpAnimatingAway(false);
                            notificationStackScrollLayout.logHunAnimationSkipped(expandableNotificationRow, "row is child in group");
                        } else {
                            i5 = i4;
                            z7 = false;
                        }
                    }
                    AnimationEvent animationEvent = new AnimationEvent(expandableNotificationRow, i5);
                    animationEvent.headsUpFromBottom = z7;
                    notificationStackScrollLayout.mAnimationEvents.add(animationEvent);
                    NotificationStackScrollLogger notificationStackScrollLogger2 = notificationStackScrollLayout.mLogger;
                    if (notificationStackScrollLogger2 != null) {
                        NotificationEntry notificationEntry2 = expandableNotificationRow.mEntry;
                        if (i5 != 0) {
                            switch (i5) {
                                case 11:
                                    str3 = "HEADS_UP_APPEAR";
                                    break;
                                case 12:
                                    str3 = "HEADS_UP_DISAPPEAR";
                                    break;
                                case 13:
                                    str3 = "HEADS_UP_DISAPPEAR_CLICK";
                                    break;
                                case 14:
                                    str3 = "HEADS_UP_OTHER";
                                    break;
                                default:
                                    str3 = String.valueOf(i5);
                                    break;
                            }
                        } else {
                            str3 = "ADD";
                        }
                        LogLevel logLevel2 = LogLevel.INFO;
                        NotificationStackScrollLogger$hunAnimationEventAdded$2 notificationStackScrollLogger$hunAnimationEventAdded$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$hunAnimationEventAdded$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                LogMessage logMessage = (LogMessage) obj;
                                return FontProvider$$ExternalSyntheticOutline0.m32m("heads up animation added: ", logMessage.getStr1(), " with type ", logMessage.getStr2());
                            }
                        };
                        LogBuffer logBuffer2 = notificationStackScrollLogger2.buffer;
                        LogMessage obtain2 = logBuffer2.obtain("NotificationStackScroll", logLevel2, notificationStackScrollLogger$hunAnimationEventAdded$2, null);
                        obtain2.setStr1(NotificationUtilsKt.getLogKey(notificationEntry2));
                        obtain2.setStr2(str3);
                        logBuffer2.commit(obtain2);
                    }
                }
            }
            notificationStackScrollLayout.mHeadsUpChangeAnimations.clear();
            notificationStackScrollLayout.mAddedHeadsUpChildren.clear();
            Iterator it3 = notificationStackScrollLayout.mChildrenToRemoveAnimated.iterator();
            while (it3.hasNext()) {
                ExpandableView expandableView2 = (ExpandableView) it3.next();
                boolean contains = notificationStackScrollLayout.mSwipedOutViews.contains(expandableView2);
                float translationY = expandableView2.getTranslationY();
                if (expandableView2 instanceof ExpandableNotificationRow) {
                    contains |= notificationStackScrollLayout.isFullySwipedOut((ExpandableNotificationRow) expandableView2);
                } else if (expandableView2 instanceof MediaContainerView) {
                    contains = true;
                }
                if (!contains) {
                    Rect clipBounds = expandableView2.getClipBounds();
                    contains = clipBounds != null && clipBounds.height() == 0;
                    if (contains) {
                        expandableView2.removeFromTransientContainer();
                    }
                }
                AnimationEvent animationEvent2 = new AnimationEvent(expandableView2, contains ? 2 : 1);
                animationEvent2.viewAfterChangingView = notificationStackScrollLayout.getFirstChildBelowTranlsationY(translationY);
                notificationStackScrollLayout.mAnimationEvents.add(animationEvent2);
                notificationStackScrollLayout.mSwipedOutViews.remove(expandableView2);
            }
            notificationStackScrollLayout.mChildrenToRemoveAnimated.clear();
            Iterator it4 = notificationStackScrollLayout.mChildrenToAddAnimated.iterator();
            while (it4.hasNext()) {
                ExpandableView expandableView3 = (ExpandableView) it4.next();
                if (notificationStackScrollLayout.mFromMoreCardAdditions.contains(expandableView3)) {
                    notificationStackScrollLayout.mAnimationEvents.add(new AnimationEvent(expandableView3, 0, 360L));
                } else {
                    notificationStackScrollLayout.mAnimationEvents.add(new AnimationEvent(expandableView3, 0));
                }
            }
            notificationStackScrollLayout.mChildrenToAddAnimated.clear();
            notificationStackScrollLayout.mFromMoreCardAdditions.clear();
            Iterator it5 = notificationStackScrollLayout.mChildrenChangingPositions.iterator();
            while (it5.hasNext()) {
                ExpandableView expandableView4 = (ExpandableView) it5.next();
                if (expandableView4 instanceof ExpandableNotificationRow) {
                    ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) expandableView4;
                    if (expandableNotificationRow2.mEntry.mIsMarkedForUserTriggeredMovement) {
                        num = 500;
                        expandableNotificationRow2.mEntry.mIsMarkedForUserTriggeredMovement = false;
                        notificationStackScrollLayout.mAnimationEvents.add(num != null ? new AnimationEvent(expandableView4, 6) : new AnimationEvent(expandableView4, 6, num.intValue()));
                    }
                }
                num = null;
                notificationStackScrollLayout.mAnimationEvents.add(num != null ? new AnimationEvent(expandableView4, 6) : new AnimationEvent(expandableView4, 6, num.intValue()));
            }
            notificationStackScrollLayout.mChildrenChangingPositions.clear();
            if (notificationStackScrollLayout.mTopPaddingNeedsAnimation) {
                notificationStackScrollLayout.mAnimationEvents.add(notificationStackScrollLayout.mAmbientState.mDozing ? new AnimationEvent((ExpandableView) null, 3, 550L) : new AnimationEvent(null, 3));
            }
            notificationStackScrollLayout.mTopPaddingNeedsAnimation = false;
            if (notificationStackScrollLayout.mDimmedNeedsAnimation) {
                notificationStackScrollLayout.mAnimationEvents.add(new AnimationEvent(null, 5));
            }
            notificationStackScrollLayout.mDimmedNeedsAnimation = false;
            if (notificationStackScrollLayout.mHideSensitiveNeedsAnimation) {
                notificationStackScrollLayout.mAnimationEvents.add(new AnimationEvent(null, 8));
            }
            notificationStackScrollLayout.mHideSensitiveNeedsAnimation = false;
            if (notificationStackScrollLayout.mGoToFullShadeNeedsAnimation) {
                notificationStackScrollLayout.mAnimationEvents.add(new AnimationEvent(null, 7));
            }
            if (notificationStackScrollLayout.mController.mProgressingShadeLockedFromNotiIcon) {
                notificationStackScrollLayout.mAnimationEvents.add(new AnimationEvent(null, 16));
            }
            notificationStackScrollLayout.mGoToFullShadeNeedsAnimation = false;
            if (notificationStackScrollLayout.mNeedViewResizeAnimation) {
                Iterator it6 = notificationStackScrollLayout.mAnimationEvents.iterator();
                while (it6.hasNext()) {
                    int i6 = ((AnimationEvent) it6.next()).animationType;
                    if (i6 == 13 || i6 == 12) {
                        z6 = true;
                        if (!z6) {
                            notificationStackScrollLayout.mAnimationEvents.add(new AnimationEvent(null, 9));
                        }
                    }
                }
                z6 = false;
                if (!z6) {
                }
            }
            notificationStackScrollLayout.mNeedViewResizeAnimation = false;
            if (notificationStackScrollLayout.mExpandedGroupView != null) {
                notificationStackScrollLayout.mAnimationEvents.add(new AnimationEvent(notificationStackScrollLayout.mExpandedGroupView, 10));
                notificationStackScrollLayout.mExpandedGroupView = null;
            }
            if (notificationStackScrollLayout.mEverythingNeedsAnimation) {
                notificationStackScrollLayout.mAnimationEvents.add(new AnimationEvent(null, 15));
            }
            notificationStackScrollLayout.mEverythingNeedsAnimation = false;
            notificationStackScrollLayout.mNeedsAnimation = false;
        }
        if (notificationStackScrollLayout.mAnimationEvents.isEmpty()) {
            z = true;
            if (!(!notificationStackScrollLayout.mStateAnimator.mAnimatorSet.isEmpty())) {
                applyCurrentState();
                notificationStackScrollLayout.mGoToFullShadeDelay = 0L;
                return;
            }
        } else {
            z = true;
        }
        notificationStackScrollLayout.setAnimationRunning(z);
        final StackStateAnimator stackStateAnimator = notificationStackScrollLayout.mStateAnimator;
        ArrayList arrayList2 = notificationStackScrollLayout.mAnimationEvents;
        long j3 = notificationStackScrollLayout.mGoToFullShadeDelay;
        stackStateAnimator.getClass();
        Iterator it7 = arrayList2.iterator();
        while (true) {
            boolean hasNext = it7.hasNext();
            ArrayList arrayList3 = stackStateAnimator.mNewAddChildren;
            HashSet hashSet3 = stackStateAnimator.mHeadsUpAppearChildren;
            HashSet hashSet4 = stackStateAnimator.mHeadsUpDisappearChildren;
            ArrayList arrayList4 = stackStateAnimator.mNewEvents;
            StackStateAnimator.C29751 c29751 = stackStateAnimator.mAnimationProperties;
            NotificationStackScrollLayout notificationStackScrollLayout2 = stackStateAnimator.mHostLayout;
            if (!hasNext) {
                int childCount = notificationStackScrollLayout2.getChildCount();
                AnimationFilter animationFilter3 = stackStateAnimator.mAnimationFilter;
                animationFilter3.reset();
                int size = arrayList4.size();
                for (int i7 = 0; i7 < size; i7++) {
                    AnimationEvent animationEvent3 = (AnimationEvent) arrayList4.get(i7);
                    animationFilter3.combineFilter(((AnimationEvent) arrayList4.get(i7)).filter);
                    int i8 = animationEvent3.animationType;
                    if (i8 == 7) {
                        z3 = true;
                        animationFilter3.hasGoToFullShadeEvent = true;
                    } else {
                        z3 = true;
                    }
                    if (i8 == 16) {
                        animationFilter3.shadeLockedFromNotiIcon = z3;
                    }
                }
                stackStateAnimator.mCurrentAdditionalDelay = j3;
                AnimationFilter[] animationFilterArr = AnimationEvent.FILTERS;
                int size2 = arrayList4.size();
                int i9 = 0;
                long j4 = 0;
                while (true) {
                    if (i9 < size2) {
                        AnimationEvent animationEvent4 = (AnimationEvent) arrayList4.get(i9);
                        j4 = Math.max(j4, animationEvent4.length);
                        if (animationEvent4.animationType == 7) {
                            j4 = animationEvent4.length;
                        } else {
                            i9++;
                        }
                    }
                }
                stackStateAnimator.mCurrentLength = j4;
                FeatureFlags featureFlags = notificationStackScrollLayout2.mAmbientState.mFeatureFlags;
                NotificationShelfController.checkRefactorFlagEnabled();
                ExpandableView expandableView5 = (ExpandableView) notificationStackScrollLayout2.getChildAt(notificationStackScrollLayout2.mShelf.mViewState.notGoneIndex);
                stackStateAnimator.mTopYWhenGoToFullShade = expandableView5 != null ? expandableView5.getTranslationY() : notificationStackScrollLayout2.mTopPadding;
                int i10 = 0;
                int i11 = 0;
                while (i10 < childCount) {
                    ExpandableView expandableView6 = (ExpandableView) notificationStackScrollLayout2.getChildAt(i10);
                    ExpandableViewState expandableViewState2 = expandableView6.mViewState;
                    if (expandableViewState2 != null) {
                        if (expandableView6.getVisibility() != 8) {
                            if (!stackStateAnimator.mShadeExpanded || !expandableViewState2.isAnimatable) {
                                if (!(expandableView6.getTag(ViewState.TAG_ANIMATOR_TRANSLATION_Y) != null) && !hashSet4.contains(expandableView6) && !hashSet3.contains(expandableView6) && !isPinnedHeadsUp(expandableView6)) {
                                    expandableViewState2.applyToView(expandableView6);
                                    z2 = true;
                                    if (!z2) {
                                        if (c29751.wasAdded(expandableView6) && i11 < 5) {
                                            i11++;
                                        }
                                        boolean wasAdded = c29751.wasAdded(expandableView6);
                                        c29751.duration = stackStateAnimator.mCurrentLength;
                                        if (animationFilter3.shadeLockedFromNotiIcon) {
                                            expandableView6.setTranslationY(notificationStackScrollLayout2.mTopPadding - stackStateAnimator.mKeyguardStatusBarHeight);
                                        }
                                        boolean z10 = expandableView6 instanceof StackScrollerDecorView;
                                        hashSet = hashSet3;
                                        hashSet2 = hashSet4;
                                        if (animationFilter3.hasGoToFullShadeEvent) {
                                            if (!z10) {
                                                c29751.duration = ((long) (((float) Math.pow(i11, 0.699999988079071d)) * 100.0f)) + 514;
                                            }
                                            if (((SettingsHelper) Dependency.get(SettingsHelper.class)).isNotificationIconsOnlyOn()) {
                                                if (expandableView6 instanceof NotificationShelf) {
                                                    expandableView6.setTranslationY(notificationStackScrollLayout2.mTopPadding);
                                                } else {
                                                    expandableView6.setTranslationY(expandableViewState2.mYTranslation);
                                                }
                                            } else if (expandableView6 instanceof NotificationShelf) {
                                                expandableView6.setTranslationY(stackStateAnimator.mTopYWhenGoToFullShade);
                                            } else {
                                                float f3 = expandableViewState2.mYTranslation;
                                                if (f3 > stackStateAnimator.mTopYWhenGoToFullShade) {
                                                    expandableView6.setTranslationY(f3);
                                                }
                                            }
                                        }
                                        c29751.delay = 0L;
                                        if (wasAdded || (animationFilter3.hasDelays && !(expandableViewState2.mYTranslation == expandableView6.getTranslationY() && expandableViewState2.mZTranslation == expandableView6.getTranslationZ() && expandableViewState2.mAlpha == expandableView6.getAlpha() && expandableViewState2.height == expandableView6.mActualHeight && expandableViewState2.clipTopAmount == expandableView6.mClipTopAmount))) {
                                            long j5 = stackStateAnimator.mCurrentAdditionalDelay;
                                            if (animationFilter3.hasGoToFullShadeEvent) {
                                                int i12 = stackStateAnimator.mShelf.mNotGoneIndex;
                                                float f4 = expandableViewState2.notGoneIndex;
                                                float f5 = i12;
                                                if (f4 > f5) {
                                                    arrayList = arrayList3;
                                                    d = 0.699999988079071d;
                                                    j2 = ((long) (((float) Math.pow(i11, 0.699999988079071d)) * 48.0f * 0.25d)) + 0;
                                                    f4 = f5;
                                                } else {
                                                    arrayList = arrayList3;
                                                    d = 0.699999988079071d;
                                                    j2 = 0;
                                                }
                                                j = j2 + ((long) (((float) Math.pow(f4, d)) * 48.0f));
                                            } else {
                                                arrayList = arrayList3;
                                                long j6 = animationFilter3.customDelay;
                                                if (j6 != -1) {
                                                    j = j6;
                                                } else {
                                                    Iterator it8 = arrayList4.iterator();
                                                    long j7 = 0;
                                                    while (it8.hasNext()) {
                                                        AnimationEvent animationEvent5 = (AnimationEvent) it8.next();
                                                        int i13 = childCount;
                                                        int i14 = animationEvent5.animationType;
                                                        long j8 = 80;
                                                        if (i14 != 0) {
                                                            it = it8;
                                                            if (i14 != 1) {
                                                                if (i14 != 2) {
                                                                    animationFilter2 = animationFilter3;
                                                                } else {
                                                                    j8 = 32;
                                                                }
                                                            }
                                                            int i15 = expandableViewState2.notGoneIndex;
                                                            View view = animationEvent5.viewAfterChangingView;
                                                            if (view == null) {
                                                                int childCount2 = notificationStackScrollLayout2.getChildCount();
                                                                while (true) {
                                                                    int i16 = childCount2 - 1;
                                                                    if (i16 >= 0) {
                                                                        View childAt = notificationStackScrollLayout2.getChildAt(i16);
                                                                        animationFilter2 = animationFilter3;
                                                                        if (childAt.getVisibility() == 8 || childAt == notificationStackScrollLayout2.mShelf) {
                                                                            childCount2 = i16;
                                                                            animationFilter3 = animationFilter2;
                                                                        } else {
                                                                            expandableView = (ExpandableView) childAt;
                                                                        }
                                                                    } else {
                                                                        animationFilter2 = animationFilter3;
                                                                        expandableView = null;
                                                                    }
                                                                }
                                                            } else {
                                                                animationFilter2 = animationFilter3;
                                                                expandableView = (ExpandableView) view;
                                                            }
                                                            if (expandableView != null) {
                                                                if (i15 >= expandableView.mViewState.notGoneIndex) {
                                                                    i15++;
                                                                }
                                                                j7 = Math.max(Math.max(0, Math.min(2, Math.abs(i15 - r2) - 1)) * j8, j7);
                                                            }
                                                        } else {
                                                            it = it8;
                                                            animationFilter2 = animationFilter3;
                                                            j7 = Math.max((2 - Math.max(0, Math.min(2, Math.abs(expandableViewState2.notGoneIndex - animationEvent5.mChangingView.mViewState.notGoneIndex) - 1))) * 80, j7);
                                                        }
                                                        childCount = i13;
                                                        it8 = it;
                                                        animationFilter3 = animationFilter2;
                                                    }
                                                    i = childCount;
                                                    animationFilter = animationFilter3;
                                                    j = j7;
                                                    c29751.delay = j5 + j;
                                                }
                                            }
                                            i = childCount;
                                            animationFilter = animationFilter3;
                                            c29751.delay = j5 + j;
                                        } else {
                                            i = childCount;
                                            animationFilter = animationFilter3;
                                            arrayList = arrayList3;
                                        }
                                        expandableViewState2.animateTo(expandableView6, c29751);
                                    }
                                }
                            }
                            z2 = false;
                            if (!z2) {
                            }
                        } else {
                            i = childCount;
                            hashSet = hashSet3;
                            hashSet2 = hashSet4;
                            animationFilter = animationFilter3;
                            arrayList = arrayList3;
                        }
                        i10++;
                        hashSet3 = hashSet;
                        hashSet4 = hashSet2;
                        childCount = i;
                        arrayList3 = arrayList;
                        animationFilter3 = animationFilter;
                    }
                    i = childCount;
                    hashSet = hashSet3;
                    hashSet2 = hashSet4;
                    animationFilter = animationFilter3;
                    arrayList = arrayList3;
                    i10++;
                    hashSet3 = hashSet;
                    hashSet4 = hashSet2;
                    childCount = i;
                    arrayList3 = arrayList;
                    animationFilter3 = animationFilter;
                }
                HashSet hashSet5 = hashSet3;
                HashSet hashSet6 = hashSet4;
                ArrayList arrayList5 = arrayList3;
                if (!(!stackStateAnimator.mAnimatorSet.isEmpty()) && !notificationStackScrollLayout2.mHeadsUpAnimatingAway) {
                    stackStateAnimator.onAnimationFinished();
                }
                hashSet5.clear();
                hashSet6.clear();
                arrayList4.clear();
                arrayList5.clear();
                notificationStackScrollLayout = this;
                notificationStackScrollLayout.mAnimationEvents.clear();
                updateBackground();
                updateViewShadows();
                updateClippingToTopRoundedCorner();
                notificationStackScrollLayout.mGoToFullShadeDelay = 0L;
                return;
            }
            AnimationEvent animationEvent6 = (AnimationEvent) it7.next();
            final ExpandableView expandableView7 = animationEvent6.mChangingView;
            if (!(expandableView7 instanceof ExpandableNotificationRow) || stackStateAnimator.mLogger == null) {
                str = str4;
                z4 = false;
                z5 = false;
            } else {
                ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) expandableView7;
                boolean z11 = expandableNotificationRow3.mIsHeadsUp;
                str = expandableNotificationRow3.mEntry.mKey;
                z5 = z11;
                z4 = true;
            }
            Iterator it9 = it7;
            int i17 = animationEvent6.animationType;
            if (i17 == 0) {
                ExpandableViewState expandableViewState3 = expandableView7.mViewState;
                if (expandableViewState3 != null && !expandableViewState3.gone) {
                    if (z4 && z5) {
                        StackStateLogger stackStateLogger = stackStateAnimator.mLogger;
                        stackStateLogger.getClass();
                        LogLevel logLevel3 = LogLevel.ERROR;
                        StackStateLogger$logHUNViewAppearingWithAddEvent$2 stackStateLogger$logHUNViewAppearingWithAddEvent$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.stack.StackStateLogger$logHUNViewAppearingWithAddEvent$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                return PathParser$$ExternalSyntheticOutline0.m29m("Heads up view disappearing ", ((LogMessage) obj).getStr1(), " for ANIMATION_TYPE_ADD");
                            }
                        };
                        LogBuffer logBuffer3 = stackStateLogger.buffer;
                        LogMessage obtain3 = logBuffer3.obtain("StackScroll", logLevel3, stackStateLogger$logHUNViewAppearingWithAddEvent$2, null);
                        obtain3.setStr1(NotificationUtils.logKey(str));
                        logBuffer3.commit(obtain3);
                    }
                    expandableViewState3.applyToView(expandableView7);
                    arrayList3.add(expandableView7);
                    str2 = null;
                }
                it7 = it9;
                str4 = null;
            } else {
                if (i17 == 1) {
                    if (expandableView7.getVisibility() != 0) {
                        expandableView7.removeFromTransientContainer();
                        it7 = it9;
                        str4 = null;
                    } else {
                        if (animationEvent6.viewAfterChangingView != null) {
                            float translationY2 = expandableView7.getTranslationY();
                            if (expandableView7 instanceof ExpandableNotificationRow) {
                                View view2 = animationEvent6.viewAfterChangingView;
                                if (view2 instanceof ExpandableNotificationRow) {
                                }
                            }
                            float f6 = expandableView7.mActualHeight;
                            f2 = Math.max(Math.min(((((ExpandableView) animationEvent6.viewAfterChangingView).mViewState.mYTranslation - ((f6 / 2.0f) + translationY2)) * 2.0f) / f6, 1.0f), -1.0f);
                        } else {
                            f2 = -1.0f;
                        }
                        final int i18 = 0;
                        Runnable runnable3 = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda0
                            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
                            @Override // java.lang.Runnable
                            public final void run() {
                                switch (i18) {
                                }
                                expandableView7.removeFromTransientContainer();
                            }
                        };
                        if (z4 && z5) {
                            StackStateLogger stackStateLogger2 = stackStateAnimator.mLogger;
                            stackStateLogger2.getClass();
                            LogLevel logLevel4 = LogLevel.ERROR;
                            StackStateLogger$logHUNViewDisappearingWithRemoveEvent$2 stackStateLogger$logHUNViewDisappearingWithRemoveEvent$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.stack.StackStateLogger$logHUNViewDisappearingWithRemoveEvent$2
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    return PathParser$$ExternalSyntheticOutline0.m29m("Heads up view disappearing ", ((LogMessage) obj).getStr1(), " for ANIMATION_TYPE_REMOVE");
                                }
                            };
                            LogBuffer logBuffer4 = stackStateLogger2.buffer;
                            LogMessage obtain4 = logBuffer4.obtain("StackScroll", logLevel4, stackStateLogger$logHUNViewDisappearingWithRemoveEvent$2, null);
                            obtain4.setStr1(NotificationUtils.logKey(str));
                            logBuffer4.commit(obtain4);
                            final int i19 = 0;
                            runnable3 = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i19) {
                                        case 0:
                                            StackStateAnimator stackStateAnimator2 = stackStateAnimator;
                                            String str5 = str;
                                            ExpandableView expandableView8 = (ExpandableView) expandableView7;
                                            StackStateLogger stackStateLogger3 = stackStateAnimator2.mLogger;
                                            stackStateLogger3.getClass();
                                            LogLevel logLevel5 = LogLevel.INFO;
                                            StackStateLogger$disappearAnimationEnded$2 stackStateLogger$disappearAnimationEnded$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.stack.StackStateLogger$disappearAnimationEnded$2
                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Object obj) {
                                                    return PathParser$$ExternalSyntheticOutline0.m29m("Heads up notification disappear animation ended ", ((LogMessage) obj).getStr1(), " ");
                                                }
                                            };
                                            LogBuffer logBuffer5 = stackStateLogger3.buffer;
                                            LogMessage obtain5 = logBuffer5.obtain("StackScroll", logLevel5, stackStateLogger$disappearAnimationEnded$2, null);
                                            obtain5.setStr1(NotificationUtils.logKey(str5));
                                            logBuffer5.commit(obtain5);
                                            expandableView8.removeFromTransientContainer();
                                            break;
                                        default:
                                            StackStateAnimator stackStateAnimator3 = stackStateAnimator;
                                            String str6 = str;
                                            Runnable runnable4 = (Runnable) expandableView7;
                                            StackStateLogger stackStateLogger4 = stackStateAnimator3.mLogger;
                                            stackStateLogger4.getClass();
                                            LogLevel logLevel6 = LogLevel.INFO;
                                            StackStateLogger$disappearAnimationEnded$2 stackStateLogger$disappearAnimationEnded$22 = new Function1() { // from class: com.android.systemui.statusbar.notification.stack.StackStateLogger$disappearAnimationEnded$2
                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Object obj) {
                                                    return PathParser$$ExternalSyntheticOutline0.m29m("Heads up notification disappear animation ended ", ((LogMessage) obj).getStr1(), " ");
                                                }
                                            };
                                            LogBuffer logBuffer6 = stackStateLogger4.buffer;
                                            LogMessage obtain6 = logBuffer6.obtain("StackScroll", logLevel6, stackStateLogger$disappearAnimationEnded$22, null);
                                            obtain6.setStr1(NotificationUtils.logKey(str6));
                                            logBuffer6.commit(obtain6);
                                            if (runnable4 != null) {
                                                runnable4.run();
                                                break;
                                            }
                                            break;
                                    }
                                }
                            };
                        }
                        expandableView7.performRemoveAnimation(464L, 0L, f2, false, 0.0f, runnable3, null);
                    }
                } else if (i17 == 2) {
                    if (notificationStackScrollLayout2.isFullySwipedOut(expandableView7)) {
                        expandableView7.removeFromTransientContainer();
                    }
                } else if (i17 == 10) {
                    ExpandableNotificationRow expandableNotificationRow4 = (ExpandableNotificationRow) expandableView7;
                    if (expandableNotificationRow4.mIsSummaryWithChildren) {
                        expandableNotificationRow4.mChildrenContainer.getClass();
                    }
                } else {
                    ExpandableViewState expandableViewState4 = stackStateAnimator.mTmpState;
                    if (i17 == 11) {
                        expandableViewState4.copyFrom(expandableView7.mViewState);
                        if (animationEvent6.headsUpFromBottom) {
                            expandableViewState4.setYTranslation(stackStateAnimator.mHeadsUpAppearHeightBottom);
                        } else {
                            expandableView7.performAddAnimation(0L, 400L, true);
                        }
                        hashSet3.add(expandableView7);
                        if (z4) {
                            StackStateLogger stackStateLogger3 = stackStateAnimator.mLogger;
                            stackStateLogger3.getClass();
                            LogLevel logLevel5 = LogLevel.INFO;
                            StackStateLogger$logHUNViewAppearing$2 stackStateLogger$logHUNViewAppearing$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.stack.StackStateLogger$logHUNViewAppearing$2
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    return PathParser$$ExternalSyntheticOutline0.m29m("Heads up notification view appearing ", ((LogMessage) obj).getStr1(), " ");
                                }
                            };
                            LogBuffer logBuffer5 = stackStateLogger3.buffer;
                            LogMessage obtain5 = logBuffer5.obtain("StackScroll", logLevel5, stackStateLogger$logHUNViewAppearing$2, null);
                            obtain5.setStr1(NotificationUtils.logKey(str));
                            logBuffer5.commit(obtain5);
                        }
                        expandableViewState4.applyToView(expandableView7);
                    } else {
                        if (i17 == 12 || i17 == 13) {
                            hashSet4.add(expandableView7);
                            if (expandableView7.getParent() == null) {
                                Log.d("StackScroller", "HEADS_UP_DISAPPEAR addTransientView : " + expandableView7);
                                notificationStackScrollLayout2.addTransientView(expandableView7, 0);
                                expandableView7.mTransientContainer = notificationStackScrollLayout2;
                                expandableViewState4.initFrom(expandableView7);
                                i2 = 1;
                                runnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda0
                                    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        switch (i2) {
                                        }
                                        expandableView7.removeFromTransientContainer();
                                    }
                                };
                            } else {
                                i2 = 1;
                                runnable = null;
                            }
                            if (expandableView7 instanceof ExpandableNotificationRow) {
                                ExpandableNotificationRow expandableNotificationRow5 = (ExpandableNotificationRow) expandableView7;
                                int i20 = (expandableNotificationRow5.mDismissed ? 1 : 0) ^ i2;
                                IconPack iconPack = expandableNotificationRow5.mEntry.mIcons;
                                StatusBarIconView statusBarIconView = iconPack.mStatusBarIcon;
                                StatusBarIconView statusBarIconView2 = iconPack.mCenteredIcon;
                                if (statusBarIconView2 != null && statusBarIconView2.getParent() != null) {
                                    statusBarIconView = statusBarIconView2;
                                }
                                if (statusBarIconView.getParent() != null) {
                                    int[] iArr = stackStateAnimator.mTmpLocation;
                                    statusBarIconView.getLocationOnScreen(iArr);
                                    i3 = i20;
                                    float width = (statusBarIconView.getWidth() * 0.25f) + (iArr[0] - statusBarIconView.getTranslationX()) + (((ValueAnimator) statusBarIconView.getTag(ViewState.TAG_ANIMATOR_TRANSLATION_X)) == null ? statusBarIconView.getTranslationX() : ((Float) statusBarIconView.getTag(ViewState.TAG_END_TRANSLATION_X)).floatValue());
                                    notificationStackScrollLayout2.getLocationOnScreen(iArr);
                                    f = width - iArr[0];
                                } else {
                                    i3 = i20;
                                    f = 0.0f;
                                }
                            } else {
                                f = 0.0f;
                                i3 = 1;
                            }
                            if (i3 != 0) {
                                if (z4) {
                                    StackStateLogger stackStateLogger4 = stackStateAnimator.mLogger;
                                    stackStateLogger4.getClass();
                                    LogLevel logLevel6 = LogLevel.INFO;
                                    StackStateLogger$logHUNViewDisappearing$2 stackStateLogger$logHUNViewDisappearing$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.stack.StackStateLogger$logHUNViewDisappearing$2
                                        @Override // kotlin.jvm.functions.Function1
                                        public final Object invoke(Object obj) {
                                            return PathParser$$ExternalSyntheticOutline0.m29m("Heads up view disappearing ", ((LogMessage) obj).getStr1(), " ");
                                        }
                                    };
                                    LogBuffer logBuffer6 = stackStateLogger4.buffer;
                                    str2 = null;
                                    LogMessage obtain6 = logBuffer6.obtain("StackScroll", logLevel6, stackStateLogger$logHUNViewDisappearing$2, null);
                                    obtain6.setStr1(NotificationUtils.logKey(str));
                                    logBuffer6.commit(obtain6);
                                    final int i21 = 1;
                                    runnable2 = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            switch (i21) {
                                                case 0:
                                                    StackStateAnimator stackStateAnimator2 = stackStateAnimator;
                                                    String str5 = str;
                                                    ExpandableView expandableView8 = (ExpandableView) runnable;
                                                    StackStateLogger stackStateLogger32 = stackStateAnimator2.mLogger;
                                                    stackStateLogger32.getClass();
                                                    LogLevel logLevel52 = LogLevel.INFO;
                                                    StackStateLogger$disappearAnimationEnded$2 stackStateLogger$disappearAnimationEnded$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.stack.StackStateLogger$disappearAnimationEnded$2
                                                        @Override // kotlin.jvm.functions.Function1
                                                        public final Object invoke(Object obj) {
                                                            return PathParser$$ExternalSyntheticOutline0.m29m("Heads up notification disappear animation ended ", ((LogMessage) obj).getStr1(), " ");
                                                        }
                                                    };
                                                    LogBuffer logBuffer52 = stackStateLogger32.buffer;
                                                    LogMessage obtain52 = logBuffer52.obtain("StackScroll", logLevel52, stackStateLogger$disappearAnimationEnded$2, null);
                                                    obtain52.setStr1(NotificationUtils.logKey(str5));
                                                    logBuffer52.commit(obtain52);
                                                    expandableView8.removeFromTransientContainer();
                                                    break;
                                                default:
                                                    StackStateAnimator stackStateAnimator3 = stackStateAnimator;
                                                    String str6 = str;
                                                    Runnable runnable4 = (Runnable) runnable;
                                                    StackStateLogger stackStateLogger42 = stackStateAnimator3.mLogger;
                                                    stackStateLogger42.getClass();
                                                    LogLevel logLevel62 = LogLevel.INFO;
                                                    StackStateLogger$disappearAnimationEnded$2 stackStateLogger$disappearAnimationEnded$22 = new Function1() { // from class: com.android.systemui.statusbar.notification.stack.StackStateLogger$disappearAnimationEnded$2
                                                        @Override // kotlin.jvm.functions.Function1
                                                        public final Object invoke(Object obj) {
                                                            return PathParser$$ExternalSyntheticOutline0.m29m("Heads up notification disappear animation ended ", ((LogMessage) obj).getStr1(), " ");
                                                        }
                                                    };
                                                    LogBuffer logBuffer62 = stackStateLogger42.buffer;
                                                    LogMessage obtain62 = logBuffer62.obtain("StackScroll", logLevel62, stackStateLogger$disappearAnimationEnded$22, null);
                                                    obtain62.setStr1(NotificationUtils.logKey(str6));
                                                    logBuffer62.commit(obtain62);
                                                    if (runnable4 != null) {
                                                        runnable4.run();
                                                        break;
                                                    }
                                                    break;
                                            }
                                        }
                                    };
                                } else {
                                    str2 = null;
                                    runnable2 = runnable;
                                }
                                Stack stack = stackStateAnimator.mAnimationListenerPool;
                                c29751.delay += expandableView7.performRemoveAnimation(400L, 0L, 0.0f, true, f, runnable2, !stack.empty() ? (AnimatorListenerAdapter) stack.pop() : stackStateAnimator.new C29762());
                            } else {
                                str2 = null;
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }
                        }
                        str2 = null;
                    }
                }
                str2 = null;
            }
            arrayList4.add(animationEvent6);
            it7 = it9;
            str4 = str2;
        }
    }

    public final int targetScrollForView(ExpandableView expandableView, int i) {
        int imeInset = ((getImeInset() + (expandableView.getIntrinsicHeight() + i)) - getHeight()) + ((this.mIsExpanded || !isPinnedHeadsUp(expandableView)) ? this.mTopPadding : this.mHeadsUpInset) + this.mMinimumPaddings;
        return ((this.mIsExpanded || !isPinnedHeadsUp(expandableView)) && imeInset > 0 && imeInset < getScrollAmountToScrollBoundary()) ? getScrollAmountToScrollBoundary() : imeInset;
    }

    public final void updateAlgorithmHeightAndPadding() {
        this.mAmbientState.mLayoutHeight = Math.min(this.mMaxLayoutHeight, this.mCurrentStackHeight);
        this.mAmbientState.mLayoutMaxHeight = this.mMaxLayoutHeight;
        updateAlgorithmLayoutMinHeight();
        this.mAmbientState.mTopPadding = this.mTopPadding;
    }

    public final void updateAlgorithmLayoutMinHeight() {
        this.mAmbientState.mLayoutMinHeight = (this.mQsFullScreen || isHeadsUpTransition()) ? getLayoutMinHeight() : 0;
    }

    public final void updateBackground() {
        int i;
        boolean z;
        if (this.mShouldDrawNotificationBackground) {
            int i2 = this.mSidePaddings;
            int width = getWidth() - this.mSidePaddings;
            for (NotificationSection notificationSection : this.mSections) {
                Rect rect = notificationSection.mBounds;
                rect.left = i2;
                rect.right = width;
            }
            if (this.mIsExpanded) {
                NotificationSection lastVisibleSection = getLastVisibleSection();
                boolean z2 = this.mStatusBarState == 1;
                if (!z2) {
                    i = NotiRune.NOTI_STYLE_TABLET_BG ? 0 : (int) (this.mTopPadding + this.mStackTranslation);
                } else if (lastVisibleSection == null) {
                    i = this.mTopPadding;
                } else {
                    NotificationSection firstVisibleSection = getFirstVisibleSection();
                    firstVisibleSection.updateBounds(0, 0, false);
                    i = firstVisibleSection.mBounds.top;
                }
                boolean z3 = this.mNumHeadsUp <= 1 && (this.mAmbientState.mDozing || (this.mKeyguardBypassEnabled && z2));
                NotificationSection[] notificationSectionArr = this.mSections;
                int length = notificationSectionArr.length;
                int i3 = 0;
                while (i3 < length) {
                    NotificationSection notificationSection2 = notificationSectionArr[i3];
                    i = notificationSection2.updateBounds(i, notificationSection2 == lastVisibleSection ? (int) ((ViewState.getFinalTranslationY(notificationSection2.mLastVisibleChild) + notificationSection2.mLastVisibleChild.getIntrinsicHeight()) - notificationSection2.mLastVisibleChild.mClipBottomAmount) : i, z3);
                    i3++;
                    z3 = false;
                }
            } else {
                for (NotificationSection notificationSection3 : this.mSections) {
                    Rect rect2 = notificationSection3.mBounds;
                    rect2.top = 0;
                    rect2.bottom = 0;
                }
            }
            NotificationSection[] notificationSectionArr2 = this.mSections;
            int length2 = notificationSectionArr2.length;
            int i4 = 0;
            while (true) {
                if (i4 >= length2) {
                    z = false;
                    break;
                }
                NotificationSection notificationSection4 = notificationSectionArr2[i4];
                if (!notificationSection4.mCurrentBounds.equals(notificationSection4.mBounds)) {
                    z = true;
                    break;
                }
                i4++;
            }
            if (z) {
                if (!this.mAnimateNextSectionBoundsChange && !this.mAnimateNextBackgroundTop && !this.mAnimateNextBackgroundBottom) {
                    for (NotificationSection notificationSection5 : this.mSections) {
                        if ((notificationSection5.mBottomAnimator == null && notificationSection5.mTopAnimator == null) ? false : true) {
                            break;
                        }
                    }
                }
                if (!this.mIsExpanded) {
                    for (NotificationSection notificationSection6 : this.mSections) {
                        ObjectAnimator objectAnimator = notificationSection6.mBottomAnimator;
                        if (objectAnimator != null) {
                            objectAnimator.cancel();
                        }
                        ObjectAnimator objectAnimator2 = notificationSection6.mTopAnimator;
                        if (objectAnimator2 != null) {
                            objectAnimator2.cancel();
                        }
                    }
                }
                for (NotificationSection notificationSection7 : this.mSections) {
                    notificationSection7.mCurrentBounds.set(notificationSection7.mBounds);
                }
                invalidate();
            } else {
                for (NotificationSection notificationSection8 : this.mSections) {
                    ObjectAnimator objectAnimator3 = notificationSection8.mBottomAnimator;
                    if (objectAnimator3 != null) {
                        objectAnimator3.cancel();
                    }
                    ObjectAnimator objectAnimator4 = notificationSection8.mTopAnimator;
                    if (objectAnimator4 != null) {
                        objectAnimator4.cancel();
                    }
                }
            }
            this.mAnimateNextBackgroundTop = false;
            this.mAnimateNextBackgroundBottom = false;
            this.mAnimateNextSectionBoundsChange = false;
        }
    }

    public final void updateBackgroundDimming() {
        if (this.mShouldDrawNotificationBackground) {
            boolean z = NotiRune.NOTI_STYLE_TABLET_BG;
            if (z && onKeyguard()) {
                return;
            }
            int blendARGB = ColorUtils.blendARGB(this.mBgColor, -1, MathUtils.smoothStep(0.4f, 1.0f, this.mLinearHideAmount));
            if (z && this.mOpaqueBgHelper.needOpaqueBg()) {
                blendARGB = this.mOpaqueBgHelper.mContext.getColor(com.android.systemui.R.color.sec_panel_background_color_tablet);
            }
            if (this.mCachedBackgroundColor != blendARGB) {
                this.mCachedBackgroundColor = blendARGB;
                this.mBackgroundPaint.setColor(blendARGB);
                invalidate();
            }
        }
    }

    public final void updateBgColor() {
        this.mBgColor = Utils.getColorAttr(R.attr.colorBackgroundFloating, ((ViewGroup) this).mContext).getDefaultColor();
        if (NotiRune.NOTI_STYLE_TABLET_BG && this.mOpaqueBgHelper.needOpaqueBg()) {
            this.mBgColor = this.mOpaqueBgHelper.mContext.getColor(com.android.systemui.R.color.sec_panel_background_color_tablet);
        }
        updateBackgroundDimming();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof ActivatableNotificationView) {
                ((ActivatableNotificationView) childAt).updateBackgroundColors();
            }
        }
    }

    public final void updateBottomInset(WindowInsets windowInsets) {
        this.mBottomInset = windowInsets.getInsets(WindowInsets.Type.ime()).bottom;
        if (this.mForcedScroll != null) {
            updateForcedScroll();
        }
        int scrollRange = getScrollRange();
        if (this.mOwnScrollY > scrollRange) {
            setOwnScrollY(scrollRange);
        }
    }

    public final void updateClipping() {
        Rect rect;
        boolean z = (this.mRequestedClipBounds == null || this.mInHeadsUpPinnedMode || this.mHeadsUpAnimatingAway) ? false : true;
        boolean z2 = NotiRune.NOTI_STYLE_TABLET_BG;
        if (z2) {
            z = (!this.mOpaqueBgHelper.needOpaqueBg() || this.mInHeadsUpPinnedMode || this.mHeadsUpAnimatingAway) ? false : true;
        }
        if (this.mIsClipped != z) {
            this.mIsClipped = z;
        }
        if (this.mAmbientState.isHiddenAtAll()) {
            invalidateOutline();
            if (this.mAmbientState.isFullyHidden()) {
                setClipBounds(null);
            }
        } else if (z) {
            if (z2) {
                SecNsslOpaqueBgHelper secNsslOpaqueBgHelper = this.mOpaqueBgHelper;
                int height = getHeight();
                int width = getWidth();
                secNsslOpaqueBgHelper.getClass();
                rect = new Rect();
                rect.bottom = height;
                rect.top = 0;
                rect.left = 0;
                rect.right = width;
            } else {
                rect = this.mRequestedClipBounds;
            }
            setClipBounds(rect);
        } else {
            setClipBounds(null);
        }
        setClipToOutline(false);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0079  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateClippingToTopRoundedCorner() {
        boolean z;
        Float valueOf = Float.valueOf(this.mAmbientState.mNotificationScrimTop);
        Float valueOf2 = Float.valueOf(valueOf.floatValue() + this.mCornerRadius);
        boolean z2 = true;
        for (int i = 0; i < getChildCount(); i++) {
            ExpandableView expandableView = (ExpandableView) getChildAt(i);
            if (expandableView.getVisibility() != 8) {
                float translationY = expandableView.getTranslationY();
                float f = expandableView.mActualHeight + translationY;
                boolean z3 = (valueOf.floatValue() > translationY && valueOf.floatValue() < f) || (valueOf2.floatValue() >= translationY && valueOf2.floatValue() <= f);
                if (z2) {
                    if (NotificationStackScrollLayout.this.mOwnScrollY == 0) {
                        z = false;
                        expandableView.setDistanceToTopRoundness(!(z & z3) ? Math.max(translationY - valueOf.floatValue(), 0.0f) : -1.0f);
                        z2 = false;
                    }
                }
                z = true;
                expandableView.setDistanceToTopRoundness(!(z & z3) ? Math.max(translationY - valueOf.floatValue(), 0.0f) : -1.0f);
                z2 = false;
            }
        }
    }

    public final void updateContentHeight() {
        Object invoke;
        float f = this.mAmbientState.isOnKeyguard() ? 0.0f : this.mMinimumPaddings;
        NotificationShelf notificationShelf = this.mShelf;
        int height = notificationShelf != null ? notificationShelf.getHeight() : 0;
        int i = (int) f;
        NotificationStackSizeCalculator notificationStackSizeCalculator = this.mNotificationStackSizeCalculator;
        int i2 = this.mMaxDisplayedNotifications;
        notificationStackSizeCalculator.getClass();
        final SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 sequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 = new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new C2969xf807607a(notificationStackSizeCalculator, this, height, null));
        Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$computeHeight$3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ((Number) obj).intValue();
                Iterator it = Sequence.this.iterator();
                if (!it.hasNext()) {
                    throw new NoSuchElementException("Sequence is empty.");
                }
                Object next = it.next();
                while (it.hasNext()) {
                    next = it.next();
                }
                return (NotificationStackSizeCalculator.StackHeight) next;
            }
        };
        if (i2 >= 0) {
            Iterator it = sequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1.iterator();
            int i3 = 0;
            while (true) {
                SequenceBuilderIterator sequenceBuilderIterator = (SequenceBuilderIterator) it;
                if (!sequenceBuilderIterator.hasNext()) {
                    invoke = function1.invoke(Integer.valueOf(i2));
                    break;
                }
                Object next = sequenceBuilderIterator.next();
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
        this.mContentHeight = (int) (f4 + Math.max(this.mIntrinsicPadding, this.mTopPadding) + this.mBottomPadding);
        boolean z2 = !this.mQsFullScreen && getScrollRange() > 0;
        if (z2 != this.mScrollable) {
            this.mScrollable = z2;
            setFocusable(z2);
            updateForwardAndBackwardScrollability();
        }
        clampScrollPosition();
        updateStackPosition(false);
        this.mAmbientState.mContentHeight = this.mContentHeight;
    }

    public final void updateContinuousBackgroundDrawing() {
        boolean z = !((this.mAmbientState.mDozeAmount > 0.0f ? 1 : (this.mAmbientState.mDozeAmount == 0.0f ? 0 : -1)) == 0) && this.mSwipeHelper.mIsSwiping;
        if (z != this.mContinuousBackgroundUpdate) {
            this.mContinuousBackgroundUpdate = z;
            if (z) {
                getViewTreeObserver().addOnPreDrawListener(this.mBackgroundUpdater);
            } else {
                getViewTreeObserver().removeOnPreDrawListener(this.mBackgroundUpdater);
            }
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
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(R.attr.textColorPrimary, ((ViewGroup) this).mContext, 0);
        NotificationSectionsManager notificationSectionsManager = this.mSectionsManager;
        SectionHeaderView sectionHeaderView = ((SectionHeaderNodeControllerImpl) notificationSectionsManager.peopleHeaderController)._view;
        if (sectionHeaderView != null) {
            sectionHeaderView.mLabelView.setTextColor(colorAttrDefaultColor);
            sectionHeaderView.mClearAllButton.setImageTintList(ColorStateList.valueOf(colorAttrDefaultColor));
        }
        SectionHeaderView sectionHeaderView2 = ((SectionHeaderNodeControllerImpl) notificationSectionsManager.silentHeaderController)._view;
        if (sectionHeaderView2 != null) {
            sectionHeaderView2.mLabelView.setTextColor(colorAttrDefaultColor);
            sectionHeaderView2.mClearAllButton.setImageTintList(ColorStateList.valueOf(colorAttrDefaultColor));
        }
        SectionHeaderView sectionHeaderView3 = ((SectionHeaderNodeControllerImpl) notificationSectionsManager.alertingHeaderController)._view;
        if (sectionHeaderView3 != null) {
            sectionHeaderView3.mLabelView.setTextColor(colorAttrDefaultColor);
            sectionHeaderView3.mClearAllButton.setImageTintList(ColorStateList.valueOf(colorAttrDefaultColor));
        }
        if (NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW || (emptyShadeView = this.mEmptyShadeView) == null) {
            return;
        }
        if (NotiRune.NOTI_STYLE_EMPTY_SHADE) {
            emptyShadeView.mEmptyText.setTextColor(emptyShadeView.getResources().getColor(com.android.systemui.R.color.sec_no_notification_text_color));
        } else {
            emptyShadeView.mEmptyText.setTextColor(colorAttrDefaultColor);
        }
        emptyShadeView.mEmptyFooterText.setTextColor(colorAttrDefaultColor);
        emptyShadeView.mEmptyFooterText.setCompoundDrawableTintList(ColorStateList.valueOf(colorAttrDefaultColor));
    }

    public final void updateDismissBehavior() {
        boolean z = true;
        if (this.mShouldUseSplitNotificationShade && (this.mStatusBarState == 1 || !this.mIsExpanded)) {
            z = false;
        }
        if (this.mDismissUsingRowTranslationX != z) {
            this.mDismissUsingRowTranslationX = z;
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                if (childAt instanceof ExpandableNotificationRow) {
                    ((ExpandableNotificationRow) childAt).setDismissUsingRowTranslationX(z);
                }
            }
        }
    }

    public final void updateEmptyShadeView(int i, int i2, int i3) {
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
            emptyShadeView4.setSecondaryVisible(false);
        } else {
            EmptyShadeView emptyShadeView5 = this.mEmptyShadeView;
            emptyShadeView5.mFooterVisibility = 0;
            emptyShadeView5.setSecondaryVisible(true);
        }
    }

    public final void updateEmptyShadeViewHeight() {
        int displayHeight = (int) (DeviceState.getDisplayHeight(((ViewGroup) this).mContext) * 0.3d);
        EmptyShadeView emptyShadeView = this.mEmptyShadeView;
        int i = this.mTopPadding;
        ViewGroup.LayoutParams layoutParams = emptyShadeView.getLayoutParams();
        layoutParams.height = displayHeight;
        emptyShadeView.setLayoutParams(layoutParams);
        emptyShadeView.mTopPadding = i;
    }

    public final void updateFirstAndLastBackgroundViews() {
        NotificationSection firstVisibleSection = getFirstVisibleSection();
        NotificationSection lastVisibleSection = getLastVisibleSection();
        ExpandableView expandableView = null;
        ExpandableView expandableView2 = firstVisibleSection == null ? null : firstVisibleSection.mFirstVisibleChild;
        ExpandableView expandableView3 = lastVisibleSection == null ? null : lastVisibleSection.mLastVisibleChild;
        ExpandableView firstChildWithBackground = getFirstChildWithBackground();
        int childCount = getChildCount();
        while (true) {
            childCount--;
            if (childCount < 0) {
                break;
            }
            ExpandableView childAtIndex = getChildAtIndex(childCount);
            if (childAtIndex.getVisibility() != 8 && !(childAtIndex instanceof StackScrollerDecorView) && childAtIndex != this.mShelf) {
                expandableView = childAtIndex;
                break;
            }
        }
        boolean updateFirstAndLastViewsForAllSections = this.mSectionsManager.updateFirstAndLastViewsForAllSections(this.mSections, getChildrenWithBackground());
        if (this.mAnimationsEnabled && this.mIsExpanded) {
            boolean z = true;
            this.mAnimateNextBackgroundTop = firstChildWithBackground != expandableView2;
            if (expandableView == expandableView3 && !this.mAnimateBottomOnLayout) {
                z = false;
            }
            this.mAnimateNextBackgroundBottom = z;
            this.mAnimateNextSectionBoundsChange = updateFirstAndLastViewsForAllSections;
        } else {
            this.mAnimateNextBackgroundTop = false;
            this.mAnimateNextBackgroundBottom = false;
            this.mAnimateNextSectionBoundsChange = false;
        }
        this.mAmbientState.mLastVisibleBackgroundChild = expandableView;
        this.mAnimateBottomOnLayout = false;
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
            int targetScrollForView = targetScrollForView(expandableView, positionInLinearLayout);
            int intrinsicHeight = expandableView.getIntrinsicHeight() + positionInLinearLayout;
            int max = Math.max(0, Math.min(targetScrollForView, getScrollRange()));
            int i = this.mOwnScrollY;
            if (i < max || intrinsicHeight < i) {
                setOwnScrollY(max);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001e  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateForwardAndBackwardScrollability() {
        boolean z;
        boolean z2;
        boolean z3 = true;
        if (this.mScrollable) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
            if (!(notificationStackScrollLayout.mOwnScrollY >= notificationStackScrollLayout.getScrollRange())) {
                z = true;
                if (this.mScrollable) {
                    if (!(NotificationStackScrollLayout.this.mOwnScrollY == 0)) {
                        z2 = true;
                        if (z == this.mForwardScrollable && z2 == this.mBackwardScrollable) {
                            z3 = false;
                        }
                        this.mForwardScrollable = z;
                        this.mBackwardScrollable = z2;
                        if (z3) {
                            sendAccessibilityEvent(2048);
                            return;
                        }
                        return;
                    }
                }
                z2 = false;
                if (z == this.mForwardScrollable) {
                    z3 = false;
                }
                this.mForwardScrollable = z;
                this.mBackwardScrollable = z2;
                if (z3) {
                }
            }
        }
        z = false;
        if (this.mScrollable) {
        }
        z2 = false;
        if (z == this.mForwardScrollable) {
        }
        this.mForwardScrollable = z;
        this.mBackwardScrollable = z2;
        if (z3) {
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
            LaunchAnimator.Companion companion = LaunchAnimator.Companion;
            LaunchAnimator.Timings timings = ActivityLaunchAnimator.TIMINGS;
            float f = launchAnimationParameters.linearProgress;
            companion.getClass();
            int min2 = (int) Math.min(MathUtils.lerp(this.mRoundedRectClippingTop, this.mLaunchAnimationParams.top - iArr[1], ((PathInterpolator) interpolator).getInterpolation(LaunchAnimator.Companion.getProgress(timings, f, 0L, 100L))), this.mRoundedRectClippingTop);
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

    public final void updateSectionColor() {
        int color = ((ViewGroup) this).mContext.getColor(com.android.systemui.R.color.notification_section_header_text_color);
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
                getChildAtIndex(i).setHideSensitiveForIntrinsicHeight(z2);
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
        boolean shouldUseSplitNotificationShade = LargeScreenUtils.shouldUseSplitNotificationShade(getResources());
        if (shouldUseSplitNotificationShade != this.mShouldUseSplitNotificationShade) {
            this.mShouldUseSplitNotificationShade = shouldUseSplitNotificationShade;
            updateDismissBehavior();
            updateUseRoundedRectClipping();
        }
    }

    public void updateStackEndHeightAndStackHeight(float f) {
        float f2 = this.mAmbientState.mStackHeight;
        if (this.mQsExpansionFraction > 0.0f || shouldSkipHeightUpdate()) {
            updateStackHeight(this.mAmbientState.mStackEndHeight, f);
        } else {
            float max = this.mMaxDisplayedNotifications != -1 ? this.mIntrinsicContentHeight : Math.max(0.0f, (getHeight() - getEmptyBottomMargin()) - this.mTopPadding);
            this.mAmbientState.mStackEndHeight = max;
            updateStackHeight(max, f);
        }
        if (f2 != this.mAmbientState.mStackHeight) {
            requestChildrenUpdate();
        }
    }

    public void updateStackHeight(float f, float f2) {
        this.mAmbientState.mStackHeight = MathUtils.lerp(0.5f * f, f, f2);
    }

    public final void updateStackPosition(boolean z) {
        float currentOverScrollAmount = (((this.mTopPadding + this.mExtraTopInsetForFullShadeTransition) + this.mAmbientState.mOverExpansion) + (this.mShouldUseSplitNotificationShade ? getCurrentOverScrollAmount(true) : 0.0f)) - getCurrentOverScrollAmount(false);
        AmbientState ambientState = this.mAmbientState;
        float f = ambientState.mExpansionFraction;
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = ambientState.mStatusBarKeyguardViewManager;
        if ((statusBarKeyguardViewManager != null && statusBarKeyguardViewManager.isPrimaryBouncerInTransit()) && this.mQsExpansionFraction > 0.0f) {
            f = BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(f);
        }
        if (this.mInHeadsUpPinnedMode || this.mHeadsUpAnimatingAway) {
            currentOverScrollAmount = MathUtils.lerp(0.0f, currentOverScrollAmount, f);
        }
        this.mAmbientState.mStackY = currentOverScrollAmount;
        Consumer consumer = this.mOnStackYChanged;
        if (consumer != null) {
            consumer.accept(Boolean.valueOf(z));
        }
        updateStackEndHeightAndStackHeight(f);
    }

    public final void updateUseRoundedRectClipping() {
        boolean z = false;
        boolean z2 = this.mQsExpansionFraction < 0.5f || this.mShouldUseSplitNotificationShade;
        if (this.mIsExpanded && z2) {
            z = true;
        }
        if (z != this.mShouldUseRoundedRectClipping) {
            this.mShouldUseRoundedRectClipping = z;
            invalidate();
        }
    }

    public final void updateViewShadows() {
        for (int i = 0; i < getChildCount(); i++) {
            ExpandableView childAtIndex = getChildAtIndex(i);
            if (childAtIndex.getVisibility() != 8) {
                this.mTmpSortedChildren.add(childAtIndex);
            }
        }
        Collections.sort(this.mTmpSortedChildren, this.mViewPositionComparator);
        ExpandableView expandableView = null;
        int i2 = 0;
        while (i2 < this.mTmpSortedChildren.size()) {
            ExpandableView expandableView2 = (ExpandableView) this.mTmpSortedChildren.get(i2);
            float translationZ = expandableView2.getTranslationZ();
            float translationZ2 = (expandableView == null ? translationZ : expandableView.getTranslationZ()) - translationZ;
            if (translationZ2 <= 0.0f || translationZ2 >= RUBBER_BAND_FACTOR_NORMAL) {
                expandableView2.setFakeShadowIntensity(0, 0.0f, 0.0f, 0);
            } else {
                expandableView2.setFakeShadowIntensity((int) ((expandableView.getTranslationY() + expandableView.mActualHeight) - expandableView2.getTranslationY()), translationZ2 / RUBBER_BAND_FACTOR_NORMAL, expandableView.getOutlineAlpha(), (int) (expandableView.getTranslation() + expandableView.getOutlineTranslation()));
            }
            i2++;
            expandableView = expandableView2;
        }
        this.mTmpSortedChildren.clear();
    }

    public final void updateVisibility() {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mController;
        boolean z = (this.mAmbientState.isFullyHidden() && onKeyguard()) ? false : true;
        notificationStackScrollLayoutController.getClass();
        if (z) {
            if (notificationStackScrollLayoutController.mBarState == 1) {
                if (!(notificationStackScrollLayoutController.mLockscreenNotificationManager.mCurrentNotificationType == 0)) {
                    z = false;
                }
            }
        }
        int i = z ? 0 : 8;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.setVisibility(i);
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
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                StackStateAnimator.this.mHostLayout.setOverScrollAmount(((Float) valueAnimator3.getAnimatedValue()).floatValue(), z, false, false, z4);
            }
        });
        ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (z) {
                    StackStateAnimator.this.mTopOverScrollAnimator = null;
                } else {
                    StackStateAnimator.this.mBottomOverScrollAnimator = null;
                }
            }
        });
        ofFloat.start();
        if (z) {
            stackStateAnimator2.mTopOverScrollAnimator = ofFloat;
        } else {
            stackStateAnimator2.mBottomOverScrollAnimator = ofFloat;
        }
    }

    public final void setOwnScrollY(int i, boolean z) {
        if (this.mAmbientState.mIsClosing) {
            return;
        }
        if ((getContext().getResources().getConfiguration().orientation == 2) && i < 0) {
            i /= 4;
        }
        int i2 = this.mOwnScrollY;
        if (i != i2) {
            int i3 = ((ViewGroup) this).mScrollX;
            onScrollChanged(i3, i, i3, i2);
            this.mOwnScrollY = i;
            AmbientState ambientState = this.mAmbientState;
            ambientState.getClass();
            ambientState.mScrollY = Math.max(i, 0);
            Consumer consumer = this.mScrollListener;
            if (consumer != null) {
                consumer.accept(Integer.valueOf(this.mOwnScrollY));
            }
            updateForwardAndBackwardScrollability();
            requestChildrenUpdate();
            updateStackPosition(z);
        }
    }

    public void inflateFooterView() {
    }

    public void updateFooter() {
    }
}
