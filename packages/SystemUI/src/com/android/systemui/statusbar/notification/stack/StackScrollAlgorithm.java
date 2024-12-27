package com.android.systemui.statusbar.notification.stack;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.NotificationBackgroundView;
import java.util.ArrayList;
import java.util.Iterator;

public final class StackScrollAlgorithm {
    public boolean mClipNotificationScrollToTop;
    public int mCollapsedSize;
    public final Context mContext;
    public boolean mEnableNotificationClipping;
    public float mFavoriteGapHeight;
    public float mGapHeight;
    public float mGapHeightOnLockscreen;
    public float mGroupExpandInterpolationY;
    public int mHeadsUpAppearHeightBottom;
    float mHeadsUpAppearStartAboveScreen;
    float mHeadsUpInset;
    public final ViewGroup mHostView;
    public boolean mIsExpanded;
    public int mMarginBottom;
    public float mMaxGroupExpandedBottomGap;
    public float mNotificationScrimPadding;
    public float mOngoingGapHeight;
    public float mPaddingBetweenElements;
    public int mPinnedZTranslationExtra;
    public final StackScrollAlgorithmState mTempAlgorithmState = new StackScrollAlgorithmState();
    public boolean mFavoriteGap = false;
    public float mOverExpansionAmount = 0.0f;
    public boolean mOngoingGap = false;

    public interface BypassController {
    }

    public interface SectionProvider {
    }

    public final class StackScrollAlgorithmState {
        public ExpandableView firstViewInShelf;
        public float mCurrentExpandedYPosition;
        public float mCurrentYPosition;
        public final ArrayList visibleChildren = new ArrayList();
    }

    static {
        SourceType.from("StackScrollAlgorithm");
    }

    public StackScrollAlgorithm(Context context, ViewGroup viewGroup) {
        this.mContext = context;
        this.mHostView = viewGroup;
        initView(context);
    }

    public static void getNotificationChildrenStates(StackScrollAlgorithmState stackScrollAlgorithmState) {
        int i;
        boolean z;
        int i2;
        int i3;
        float f;
        float f2;
        int i4;
        boolean z2;
        int i5;
        boolean z3;
        int i6;
        boolean z4;
        char c;
        int i7;
        int i8;
        StackScrollAlgorithmState stackScrollAlgorithmState2 = stackScrollAlgorithmState;
        int size = stackScrollAlgorithmState2.visibleChildren.size();
        boolean z5 = false;
        int i9 = 0;
        while (i9 < size) {
            ExpandableView expandableView = (ExpandableView) stackScrollAlgorithmState2.visibleChildren.get(i9);
            if (expandableView instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
                if (expandableNotificationRow.mIsSummaryWithChildren) {
                    ExpandableViewState expandableViewState = expandableNotificationRow.mViewState;
                    NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
                    int size2 = ((ArrayList) notificationChildrenContainer.mAttachedChildren).size();
                    ?? r7 = notificationChildrenContainer.mContainingNotification.isGroupExpanded() ? notificationChildrenContainer.mHeaderExpandedHeight : z5;
                    int maxAllowedVisibleChildren = notificationChildrenContainer.getMaxAllowedVisibleChildren();
                    int i10 = maxAllowedVisibleChildren - 1;
                    boolean z6 = notificationChildrenContainer.mUserLocked;
                    if (z6) {
                        f = notificationChildrenContainer.getGroupExpandFraction();
                        i3 = notificationChildrenContainer.getMaxAllowedVisibleChildren(z5);
                    } else {
                        i3 = maxAllowedVisibleChildren;
                        f = 0.0f;
                    }
                    char c2 = 1;
                    boolean z7 = (!notificationChildrenContainer.mChildrenExpanded || notificationChildrenContainer.mContainingNotification.isGroupExpansionChanging()) ? z5 : true;
                    ?? r17 = z5;
                    int i11 = r17;
                    boolean z8 = true;
                    boolean z9 = z7;
                    for (?? r2 = z5; r2 < size2; r2++) {
                        ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(r2);
                        if (z8) {
                            boolean z10 = notificationChildrenContainer.mUntruncatedChildCount > 1 && !notificationChildrenContainer.showingLowPriorityGroupHeader();
                            if (z6) {
                                i4 = size;
                                int interpolate = (int) (NotificationUtils.interpolate(0.0f, notificationChildrenContainer.mNotificationTopPadding + notificationChildrenContainer.mHeaderExpandedHeight, f) + ((float) r7));
                                if (z10) {
                                    Iterator it = notificationChildrenContainer.mExpanderViewStates.iterator();
                                    while (it.hasNext()) {
                                        ViewState viewState = (ViewState) it.next();
                                        viewState.hidden = false;
                                        viewState.setAlpha(NotificationUtils.interpolate(0.0f, 1.0f, f));
                                        it = it;
                                        interpolate = interpolate;
                                    }
                                    i5 = interpolate;
                                    float f3 = 1.0f;
                                    float f4 = 0.0f;
                                    Iterator it2 = notificationChildrenContainer.mChildrenCountViewStates.iterator();
                                    while (it2.hasNext()) {
                                        ((ViewState) it2.next()).setAlpha(NotificationUtils.interpolate(f3, f4, f));
                                        it2 = it2;
                                        f3 = 1.0f;
                                        f4 = 0.0f;
                                    }
                                } else {
                                    i5 = interpolate;
                                    Iterator it3 = notificationChildrenContainer.mExpanderViewStates.iterator();
                                    while (it3.hasNext()) {
                                        ViewState viewState2 = (ViewState) it3.next();
                                        viewState2.hidden = false;
                                        viewState2.setAlpha(1.0f);
                                    }
                                }
                                r7 = i5;
                            } else {
                                i4 = size;
                                r7 += notificationChildrenContainer.mChildrenExpanded ? notificationChildrenContainer.mNotificationTopPadding : 0;
                                if (z10) {
                                    Iterator it4 = notificationChildrenContainer.mExpanderViewStates.iterator();
                                    while (it4.hasNext()) {
                                        ViewState viewState3 = (ViewState) it4.next();
                                        viewState3.hidden = false;
                                        viewState3.setAlpha(notificationChildrenContainer.mChildrenExpanded ? 1.0f : 0.0f);
                                    }
                                    Iterator it5 = notificationChildrenContainer.mChildrenCountViewStates.iterator();
                                    while (it5.hasNext()) {
                                        ((ViewState) it5.next()).setAlpha(notificationChildrenContainer.mChildrenExpanded ? 0.0f : 1.0f);
                                    }
                                } else {
                                    Iterator it6 = notificationChildrenContainer.mExpanderViewStates.iterator();
                                    while (it6.hasNext()) {
                                        ViewState viewState4 = (ViewState) it6.next();
                                        viewState4.hidden = false;
                                        viewState4.setAlpha(1.0f);
                                    }
                                    Iterator it7 = notificationChildrenContainer.mChildrenCountViewStates.iterator();
                                    while (it7.hasNext()) {
                                        ((ViewState) it7.next()).setAlpha(0.0f);
                                    }
                                }
                            }
                            z2 = false;
                        } else {
                            if (r2 > c2 || notificationChildrenContainer.mChildrenExpanded) {
                                r7 = z6 ? (int) (NotificationUtils.interpolate(notificationChildrenContainer.mChildPadding, notificationChildrenContainer.mDividerHeight, f) + ((float) r7)) : r7 + (notificationChildrenContainer.mChildrenExpanded ? notificationChildrenContainer.mDividerHeight : notificationChildrenContainer.mChildPadding);
                            } else if (z6) {
                                r7 = (int) (NotificationUtils.interpolate(0.0f, notificationChildrenContainer.mDividerHeight, f) + ((float) r7));
                            }
                            z2 = z8;
                            i4 = size;
                        }
                        ExpandableViewState expandableViewState2 = expandableNotificationRow2.mViewState;
                        expandableViewState2.dimmed = expandableViewState.dimmed;
                        int intrinsicHeight = expandableNotificationRow2.getIntrinsicHeight();
                        expandableViewState2.height = intrinsicHeight;
                        if (r2 == 0) {
                            z3 = z2;
                            if (!notificationChildrenContainer.mContainingNotification.areGutsExposed()) {
                                i6 = i9;
                                expandableViewState2.clipBottomAmount = 0;
                                NotificationBackgroundView notificationBackgroundView = expandableNotificationRow2.mBackgroundNormal;
                                notificationBackgroundView.mBottomClipRounded = false;
                                notificationBackgroundView.invalidate();
                            } else if (intrinsicHeight > notificationChildrenContainer.mContainingNotification.getIntrinsicHeight()) {
                                expandableViewState2.clipBottomAmount = intrinsicHeight - notificationChildrenContainer.mContainingNotification.getIntrinsicHeight();
                                NotificationBackgroundView notificationBackgroundView2 = expandableNotificationRow2.mBackgroundNormal;
                                i6 = i9;
                                notificationBackgroundView2.mBottomClipRounded = true;
                                notificationBackgroundView2.invalidate();
                            } else {
                                i6 = i9;
                            }
                            r17 = intrinsicHeight;
                        } else {
                            z3 = z2;
                            i6 = i9;
                            if (i11 != 0) {
                                r7 += r17 - intrinsicHeight;
                            }
                        }
                        float f5 = (float) r7;
                        expandableViewState2.setYTranslation(f5);
                        expandableViewState2.hidden = false;
                        if (expandableNotificationRow2.mExpandAnimationRunning || notificationChildrenContainer.mContainingNotification.mChildIsExpanding) {
                            expandableViewState2.setZTranslation(expandableNotificationRow2.getTranslationZ());
                        } else if (z9 && notificationChildrenContainer.mEnableShadowOnChildNotifications) {
                            expandableViewState2.setZTranslation(expandableViewState.mZTranslation);
                        } else {
                            expandableViewState2.setZTranslation(10.0f - ((float) r2));
                        }
                        expandableViewState2.hideSensitive = expandableViewState.hideSensitive;
                        expandableViewState2.belowSpeedBump = expandableViewState.belowSpeedBump;
                        if (i11 == 0) {
                            z4 = z9;
                            expandableViewState2.clipTopAmount = 0;
                            expandableNotificationRow2.setContentClipTopAmount(0);
                        } else if (!z6 || f <= 0.0f) {
                            z4 = z9;
                            expandableViewState2.clipTopAmount = intrinsicHeight - notificationChildrenContainer.mOverLappedSize;
                        } else {
                            expandableViewState2.clipTopAmount = 0;
                            z4 = z9;
                            expandableNotificationRow2.setContentClipTopAmount((int) ((((ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(r2 - 1)).getTranslationY() + r3.getIntrinsicHeight()) - expandableNotificationRow2.getTranslationY()));
                        }
                        if (notificationChildrenContainer.showingLowPriorityGroupHeader() && !notificationChildrenContainer.mChildrenExpanded && !z6) {
                            expandableNotificationRow2.setContentClipTopAmount(expandableNotificationRow2.getIntrinsicHeight());
                        }
                        expandableViewState2.setAlpha(0.0f);
                        if (r2 < i3) {
                            if (!z6 || r2 <= 0) {
                                expandableViewState2.setAlpha(1.0f);
                                expandableViewState2.setScaleX(1.0f);
                            } else {
                                expandableViewState2.setAlpha(r2 == 1 ? Math.max(notificationChildrenContainer.mContainingNotification.mDimmed ? 0.5f : 0.275f, f) : f);
                                expandableViewState2.setScaleX(NotificationUtils.interpolate(0.92f, 1.0f, f));
                            }
                        } else if (f == 1.0f && r2 <= i10) {
                            expandableViewState2.setAlpha((notificationChildrenContainer.mActualHeight - expandableViewState2.mYTranslation) / expandableViewState2.height);
                            expandableViewState2.setAlpha(Math.max(0.0f, Math.min(1.0f, expandableViewState2.mAlpha)));
                            expandableViewState2.setScaleX(1.0f);
                        } else if (maxAllowedVisibleChildren == 1 && r2 > 0) {
                            expandableViewState2.setAlpha(notificationChildrenContainer.mContainingNotification.mDimmed ? 0.5f : 0.275f);
                            expandableViewState2.setScaleX(0.92f);
                        }
                        expandableViewState2.location = expandableViewState.location;
                        expandableViewState2.inShelf = expandableViewState.inShelf;
                        expandableNotificationRow2.setContentAlphaLocked(false);
                        if (r2 == 0 && notificationChildrenContainer.showingLowPriorityGroupHeader()) {
                            expandableNotificationRow2.setContentAlpha(NotificationUtils.interpolate(0.0f, 1.0f, notificationChildrenContainer.mUserLocked ? f : notificationChildrenContainer.mContainingNotification.isGroupExpanded() ? 1.0f : 0.0f));
                            c = 1;
                            if (!z6) {
                                expandableNotificationRow2.setContentAlphaLocked(true);
                            }
                        } else if (!notificationChildrenContainer.mIsMinimized || notificationChildrenContainer.mContainingNotification.isGroupExpanded() || z6) {
                            c = 1;
                            expandableNotificationRow2.setContentAlpha(1.0f);
                        } else {
                            expandableNotificationRow2.setContentAlpha(0.0f);
                            c = 1;
                            expandableNotificationRow2.setContentAlphaLocked(true);
                        }
                        if (r2 >= c || notificationChildrenContainer.mChildrenExpanded) {
                            r7 += intrinsicHeight;
                            i7 = 0;
                        } else {
                            if (z6) {
                                i8 = (int) (NotificationUtils.interpolate(notificationChildrenContainer.mOverLappedSize, intrinsicHeight - (intrinsicHeight - (r2 + 1 < size2 ? ((ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(r1)).getIntrinsicHeight() : 0)), f) + f5);
                            } else {
                                i8 = notificationChildrenContainer.mOverLappedSize + r7;
                            }
                            r7 = i8;
                            i7 = 1;
                        }
                        if (NotiRune.NOTI_INSIGNIFICANT && notificationChildrenContainer.mContainingNotification.isInsignificantSummary()) {
                            expandableNotificationRow2.updateInsignificantAlpha(notificationChildrenContainer.mUserLocked ? f : notificationChildrenContainer.mContainingNotification.isGroupExpanded() ? 1.0f : 0.0f);
                        }
                        size = i4;
                        z9 = z4;
                        z8 = z3;
                        i9 = i6;
                        c2 = 1;
                        i11 = i7;
                    }
                    i = size;
                    i2 = i9;
                    boolean z11 = z9;
                    if (notificationChildrenContainer.mOverflowNumber != null) {
                        notificationChildrenContainer.mGroupOverFlowState.copyFrom(((ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(Math.min(notificationChildrenContainer.getMaxAllowedVisibleChildren(true), size2) - 1)).mViewState);
                        ViewState viewState5 = notificationChildrenContainer.mGroupOverFlowState;
                        viewState5.setYTranslation(viewState5.mYTranslation + (notificationChildrenContainer.mContainingNotification.isGroupExpanded() ? notificationChildrenContainer.mHeaderExpandedHeight : 0));
                        notificationChildrenContainer.mGroupOverFlowState.setAlpha(0.0f);
                    }
                    if (notificationChildrenContainer.mCurrentHeader != null) {
                        if (notificationChildrenContainer.mHeaderViewState == null) {
                            notificationChildrenContainer.mHeaderViewState = new ViewState();
                        }
                        notificationChildrenContainer.mHeaderViewState.initFrom(notificationChildrenContainer.mCurrentHeader);
                        if (notificationChildrenContainer.mContainingNotification.mChildIsExpanding) {
                            notificationChildrenContainer.mHeaderViewState.setZTranslation(notificationChildrenContainer.mGroupHeader.getTranslationZ());
                        } else if (z11) {
                            notificationChildrenContainer.mHeaderViewState.setZTranslation(expandableViewState.mZTranslation);
                        } else if (notificationChildrenContainer.mCurrentHeader == notificationChildrenContainer.mMinimizedGroupHeader) {
                            notificationChildrenContainer.mHeaderViewState.setZTranslation(11.0f);
                        } else {
                            notificationChildrenContainer.mHeaderViewState.setZTranslation(0.0f);
                        }
                        z = false;
                        notificationChildrenContainer.mHeaderViewState.setYTranslation(0);
                        View findViewById = notificationChildrenContainer.mCurrentHeader.findViewById(R.id.progressContainer);
                        View findViewById2 = notificationChildrenContainer.mCurrentHeader.findViewById(R.id.fill);
                        if (findViewById != null) {
                            findViewById.setAlpha(notificationChildrenContainer.mHeaderVisibleAmount);
                        }
                        if (findViewById2 != null) {
                            findViewById2.setAlpha(notificationChildrenContainer.mHeaderVisibleAmount);
                        }
                        View findViewById3 = notificationChildrenContainer.mCurrentHeader.findViewById(R.id.icon);
                        View findViewById4 = notificationChildrenContainer.mCurrentHeader.findViewById(R.id.ifContentScrolls);
                        if (findViewById3 != null) {
                            f2 = 1.0f;
                            findViewById3.setTranslationY((1.0f - notificationChildrenContainer.mHeaderVisibleAmount) * notificationChildrenContainer.mTranslationYFactor);
                        } else {
                            f2 = 1.0f;
                        }
                        if (findViewById4 != null) {
                            findViewById4.setTranslationY((f2 - notificationChildrenContainer.mHeaderVisibleAmount) * notificationChildrenContainer.mTranslationYFactor);
                        }
                    } else {
                        z = false;
                    }
                    i9 = i2 + 1;
                    stackScrollAlgorithmState2 = stackScrollAlgorithmState;
                    z5 = z;
                    size = i;
                }
            }
            i = size;
            z = z5;
            i2 = i9;
            i9 = i2 + 1;
            stackScrollAlgorithmState2 = stackScrollAlgorithmState;
            z5 = z;
            size = i;
        }
    }

    public static float getPreviousGroupExpandFraction(ExpandableView expandableView) {
        ExpandableNotificationRow expandableNotificationRow;
        NotificationChildrenContainer notificationChildrenContainer;
        if ((expandableView instanceof ExpandableNotificationRow) && (notificationChildrenContainer = (expandableNotificationRow = (ExpandableNotificationRow) expandableView).mChildrenContainer) != null) {
            if (expandableNotificationRow.mUserLocked) {
                return notificationChildrenContainer.getGroupExpandFraction();
            }
            if (expandableNotificationRow.isGroupExpanded()) {
                return 1.0f;
            }
        }
        return 0.0f;
    }

    public final boolean childNeedsGapHeight(SectionProvider sectionProvider, View view, View view2) {
        NotificationSectionsManager notificationSectionsManager = (NotificationSectionsManager) sectionProvider;
        Integer bucket = notificationSectionsManager.getBucket(view2);
        Integer bucket2 = notificationSectionsManager.getBucket(view);
        boolean z = false;
        if (bucket != null && bucket.intValue() == 3 && bucket.equals(bucket2)) {
            return false;
        }
        Integer bucket3 = notificationSectionsManager.getBucket(view2);
        boolean z2 = (bucket3 == null || bucket3.intValue() != 3 || bucket3.equals(notificationSectionsManager.getBucket(view))) ? false : true;
        this.mOngoingGap = z2;
        if (z2) {
            return z2;
        }
        Integer bucket4 = notificationSectionsManager.getBucket(view2);
        Integer bucket5 = notificationSectionsManager.getBucket(view);
        if (bucket4 != null && bucket4.intValue() == 2 && !bucket4.equals(bucket5)) {
            z = true;
        }
        this.mFavoriteGap = z;
        return z;
    }

    public void clampHunToTop(float f, float f2, float f3, ExpandableViewState expandableViewState) {
        float max = Math.max(f + f2, expandableViewState.mYTranslation);
        expandableViewState.height = (int) Math.max(expandableViewState.height - (max - expandableViewState.mYTranslation), f3);
        expandableViewState.setYTranslation(max);
    }

    public float computeCornerRoundnessForPinnedHun(float f, float f2, float f3, float f4) {
        return MathUtils.lerp(f4, 1.0f, Math.min(1.0f, Math.max(0.0f, f2 - (f - f3)) / f3));
    }

    public float getGapForLocation(float f, boolean z) {
        if (z && !((AmbientState) Dependency.sDependency.getDependencyInner(AmbientState.class)).isNeedsToExpandLocksNoti()) {
            return this.mGapHeightOnLockscreen;
        }
        if (this.mOngoingGap) {
            this.mOngoingGap = false;
            return this.mOngoingGapHeight;
        }
        if (f > 0.0f) {
            return MathUtils.lerp(this.mGapHeightOnLockscreen, this.mGapHeight, f);
        }
        if (!this.mFavoriteGap) {
            return this.mGapHeight;
        }
        this.mFavoriteGap = false;
        return this.mFavoriteGapHeight;
    }

    public final void initView(Context context) {
        Resources resources = context.getResources();
        this.mPaddingBetweenElements = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_divider_height);
        resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_min_height);
        this.mEnableNotificationClipping = resources.getBoolean(com.android.systemui.R.bool.notification_enable_clipping);
        this.mClipNotificationScrollToTop = resources.getBoolean(com.android.systemui.R.bool.config_clipNotificationScrollToTop);
        this.mHeadsUpInset = resources.getDimensionPixelSize(com.android.systemui.R.dimen.heads_up_status_bar_padding) + SystemBarUtils.getStatusBarHeight(context);
        this.mHeadsUpAppearStartAboveScreen = resources.getDimensionPixelSize(com.android.systemui.R.dimen.heads_up_appear_y_above_screen);
        context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.heads_up_cycling_padding);
        this.mPinnedZTranslationExtra = resources.getDimensionPixelSize(com.android.systemui.R.dimen.heads_up_pinned_elevation);
        this.mGapHeight = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_section_divider_height);
        this.mGapHeightOnLockscreen = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_section_divider_height_lockscreen);
        this.mNotificationScrimPadding = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_scrim_top_padding);
        this.mMarginBottom = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_panel_margin_bottom);
        SystemBarUtils.getQuickQsOffsetHeight(context);
        resources.getDimension(com.android.systemui.R.dimen.notification_corner_radius_small);
        resources.getDimension(com.android.systemui.R.dimen.notification_corner_radius);
        this.mMaxGroupExpandedBottomGap = resources.getDimension(com.android.systemui.R.dimen.notification_group_expanded_max_bottom_gap);
        this.mOngoingGapHeight = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_section_ongoing_gap_height);
        this.mFavoriteGapHeight = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_section_favorite_gap_height);
    }

    public void maybeUpdateHeadsUpIsVisible(ExpandableViewState expandableViewState, boolean z, boolean z2, boolean z3, float f, float f2) {
        if (z && z2 && z3) {
            expandableViewState.headsUpIsVisible = f < f2;
        }
    }

    public boolean shouldHunBeVisibleWhenScrolled(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        return z && !z2 && !z3 && (!z4 || z5);
    }

    public void updatePulsingStates(StackScrollAlgorithmState stackScrollAlgorithmState, AmbientState ambientState) {
        int size = stackScrollAlgorithmState.visibleChildren.size();
        ExpandableNotificationRow expandableNotificationRow = null;
        for (int i = 0; i < size; i++) {
            View view = (View) stackScrollAlgorithmState.visibleChildren.get(i);
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) view;
                if (expandableNotificationRow2.showingPulsing() && (i != 0 || !ambientState.isPulseExpanding())) {
                    expandableNotificationRow2.mViewState.hidden = false;
                    expandableNotificationRow = expandableNotificationRow2;
                }
            }
        }
        float f = ambientState.mDozeAmount;
        if (f == 0.0f || f == 1.0f) {
            ambientState.mPulsingRow = expandableNotificationRow;
        }
    }

    public void updateViewWithShelf(ExpandableView expandableView, ExpandableViewState expandableViewState, float f, float f2) {
        expandableViewState.setYTranslation(Math.min(expandableViewState.mYTranslation, (!expandableView.isPinned() ? f2 + this.mPaddingBetweenElements : 0.0f) + f));
        if (expandableViewState.mYTranslation >= f) {
            expandableViewState.hidden = (expandableView.isExpandAnimationRunning() || expandableView.hasExpandingChild()) ? false : true;
            expandableViewState.inShelf = true;
            expandableViewState.headsUpIsVisible = false;
        }
    }
}
