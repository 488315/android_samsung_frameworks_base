package com.android.systemui.statusbar.notification.stack;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.QpRune;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.FooterView;
import com.android.systemui.statusbar.notification.row.HybridNotificationView;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StackScrollAlgorithm {
    public boolean mClipNotificationScrollToTop;
    public int mCollapsedSize;
    public boolean mEnableNotificationClipping;
    public float mGapHeight;
    public float mGapHeightOnLockscreen;
    public float mGroupExpandInterpolationY;
    float mHeadsUpInset;
    public final ViewGroup mHostView;
    public boolean mIsExpanded;
    public int mMarginBottom;
    public float mMaxGroupExpandedBottomGap;
    public float mPaddingBetweenElements;
    public int mPinnedZTranslationExtra;
    public final StackScrollAlgorithmState mTempAlgorithmState = new StackScrollAlgorithmState();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface BypassController {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface SectionProvider {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        this.mHostView = viewGroup;
        initView(context);
    }

    public static boolean childNeedsGapHeight(SectionProvider sectionProvider, int i, View view, View view2) {
        return ((NotificationSectionsManager) sectionProvider).beginsSection(view, view2) && i > 0 && !(view2 instanceof SectionHeaderView) && !(view instanceof FooterView);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v16, types: [android.view.View, android.widget.TextView] */
    /* JADX WARN: Type inference failed for: r6v22, types: [android.widget.TextView] */
    public static void getNotificationChildrenStates(StackScrollAlgorithmState stackScrollAlgorithmState) {
        ArrayList arrayList;
        int i;
        float f;
        float f2;
        int i2;
        ArrayList arrayList2 = stackScrollAlgorithmState.visibleChildren;
        int size = arrayList2.size();
        int i3 = 0;
        int i4 = 0;
        while (i4 < size) {
            ExpandableView expandableView = (ExpandableView) arrayList2.get(i4);
            if (expandableView instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
                if (expandableNotificationRow.mIsSummaryWithChildren) {
                    ExpandableViewState expandableViewState = expandableNotificationRow.mViewState;
                    NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
                    int size2 = ((ArrayList) notificationChildrenContainer.mAttachedChildren).size();
                    int i5 = notificationChildrenContainer.mContainingNotification.isGroupExpanded() ? notificationChildrenContainer.mHeaderExpandedHeight : notificationChildrenContainer.mNotificationHeaderMargin + i3;
                    int maxAllowedVisibleChildren = notificationChildrenContainer.getMaxAllowedVisibleChildren() - 1;
                    int i6 = maxAllowedVisibleChildren + 1;
                    int i7 = (!notificationChildrenContainer.mUserLocked || notificationChildrenContainer.showingAsLowPriority()) ? i3 : 1;
                    if (notificationChildrenContainer.mUserLocked) {
                        f = notificationChildrenContainer.getGroupExpandFraction();
                        i = notificationChildrenContainer.getMaxAllowedVisibleChildren(true);
                    } else {
                        i = i6;
                        f = 0.0f;
                    }
                    int i8 = (!notificationChildrenContainer.mChildrenExpanded || notificationChildrenContainer.mContainingNotification.isGroupExpansionChanging()) ? i3 : 1;
                    int i9 = i3;
                    boolean z = true;
                    while (i9 < size2) {
                        ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(i9);
                        if (z) {
                            if (i7 != 0) {
                                i2 = (int) (NotificationUtils.interpolate(0.0f, notificationChildrenContainer.mNotificationTopPadding + 0, f) + i5);
                            } else {
                                i2 = i5 + (notificationChildrenContainer.mChildrenExpanded ? notificationChildrenContainer.mNotificationTopPadding + 0 : 0);
                            }
                            z = false;
                        } else if (i7 != 0) {
                            i2 = (int) (NotificationUtils.interpolate(notificationChildrenContainer.mChildPadding, notificationChildrenContainer.mDividerHeight, f) + i5);
                        } else {
                            i2 = (notificationChildrenContainer.mChildrenExpanded ? notificationChildrenContainer.mDividerHeight : notificationChildrenContainer.mChildPadding) + i5;
                        }
                        ExpandableViewState expandableViewState2 = expandableNotificationRow2.mViewState;
                        int intrinsicHeight = expandableNotificationRow2.getIntrinsicHeight();
                        expandableViewState2.height = intrinsicHeight;
                        ArrayList arrayList3 = arrayList2;
                        expandableViewState2.setYTranslation(i2 + 0);
                        expandableViewState2.hidden = false;
                        if (expandableNotificationRow2.mExpandAnimationRunning || notificationChildrenContainer.mContainingNotification.mChildIsExpanding) {
                            expandableViewState2.setZTranslation(expandableNotificationRow2.getTranslationZ());
                        } else if (i8 == 0 || !notificationChildrenContainer.mEnableShadowOnChildNotifications) {
                            expandableViewState2.setZTranslation(0.0f);
                        } else {
                            expandableViewState2.setZTranslation(expandableViewState.mZTranslation);
                        }
                        expandableViewState2.dimmed = expandableViewState.dimmed;
                        expandableViewState2.hideSensitive = expandableViewState.hideSensitive;
                        expandableViewState2.belowSpeedBump = expandableViewState.belowSpeedBump;
                        expandableViewState2.clipTopAmount = 0;
                        expandableViewState2.setAlpha(0.0f);
                        if (i9 < i) {
                            expandableViewState2.setAlpha(notificationChildrenContainer.showingAsLowPriority() ? f : 1.0f);
                        } else if (f == 1.0f && i9 <= maxAllowedVisibleChildren) {
                            expandableViewState2.setAlpha((notificationChildrenContainer.mActualHeight - expandableViewState2.mYTranslation) / expandableViewState2.height);
                            expandableViewState2.setAlpha(Math.max(0.0f, Math.min(1.0f, expandableViewState2.mAlpha)));
                        }
                        expandableViewState2.location = expandableViewState.location;
                        expandableViewState2.inShelf = expandableViewState.inShelf;
                        i5 = i2 + intrinsicHeight;
                        i9++;
                        arrayList2 = arrayList3;
                    }
                    arrayList = arrayList2;
                    if (notificationChildrenContainer.mOverflowNumber != null) {
                        ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(Math.min(notificationChildrenContainer.getMaxAllowedVisibleChildren(true), size2) - 1);
                        notificationChildrenContainer.mGroupOverFlowState.copyFrom(expandableNotificationRow3.mViewState);
                        if (notificationChildrenContainer.mChildrenExpanded) {
                            ViewState viewState = notificationChildrenContainer.mGroupOverFlowState;
                            viewState.setYTranslation(viewState.mYTranslation + (notificationChildrenContainer.mContainingNotification.isGroupExpanded() ? notificationChildrenContainer.mHeaderExpandedHeight : notificationChildrenContainer.mNotificationHeaderMargin));
                            notificationChildrenContainer.mGroupOverFlowState.setAlpha(0.0f);
                        } else {
                            HybridNotificationView hybridNotificationView = expandableNotificationRow3.mPrivateLayout.mSingleLineView;
                            if (hybridNotificationView != null) {
                                ?? r6 = hybridNotificationView.mTextView;
                                int visibility = r6.getVisibility();
                                HybridNotificationView hybridNotificationView2 = r6;
                                if (visibility == 8) {
                                    hybridNotificationView2 = hybridNotificationView.mTitleView;
                                }
                                if (hybridNotificationView2.getVisibility() != 8) {
                                    hybridNotificationView = hybridNotificationView2;
                                }
                                notificationChildrenContainer.mGroupOverFlowState.setAlpha(hybridNotificationView.getAlpha());
                                float f3 = notificationChildrenContainer.mGroupOverFlowState.mYTranslation;
                                expandableNotificationRow3.getLocationOnScreen(NotificationUtils.sLocationBase);
                                hybridNotificationView.getLocationOnScreen(NotificationUtils.sLocationOffset);
                                notificationChildrenContainer.mGroupOverFlowState.setYTranslation(f3 + (r0[1] - r7[1]));
                            }
                        }
                    }
                    if (notificationChildrenContainer.mCurrentHeader != null) {
                        if (notificationChildrenContainer.mHeaderViewState == null) {
                            notificationChildrenContainer.mHeaderViewState = new ViewState();
                        }
                        notificationChildrenContainer.mHeaderViewState.initFrom(notificationChildrenContainer.mCurrentHeader);
                        if (notificationChildrenContainer.mContainingNotification.mChildIsExpanding) {
                            notificationChildrenContainer.mHeaderViewState.setZTranslation(notificationChildrenContainer.mNotificationHeader.getTranslationZ());
                        } else if (i8 != 0) {
                            notificationChildrenContainer.mHeaderViewState.setZTranslation(expandableViewState.mZTranslation);
                        } else {
                            notificationChildrenContainer.mHeaderViewState.setZTranslation(0.0f);
                        }
                        i3 = 0;
                        notificationChildrenContainer.mHeaderViewState.setYTranslation(0);
                        View findViewById = notificationChildrenContainer.mCurrentHeader.findViewById(R.id.productivity);
                        View findViewById2 = notificationChildrenContainer.mCurrentHeader.findViewById(R.id.feedbackAudible);
                        if (findViewById != null) {
                            findViewById.setAlpha(notificationChildrenContainer.mHeaderVisibleAmount);
                        }
                        if (findViewById2 != null) {
                            findViewById2.setAlpha(notificationChildrenContainer.mHeaderVisibleAmount);
                        }
                        View findViewById3 = notificationChildrenContainer.mCurrentHeader.findViewById(R.id.icon);
                        View findViewById4 = notificationChildrenContainer.mCurrentHeader.findViewById(R.id.horizontal);
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
                        i3 = 0;
                    }
                    i4++;
                    arrayList2 = arrayList;
                }
            }
            arrayList = arrayList2;
            i4++;
            arrayList2 = arrayList;
        }
    }

    public static float getPreviousGroupExpandFraction(ExpandableView expandableView) {
        ExpandableNotificationRow expandableNotificationRow;
        NotificationChildrenContainer notificationChildrenContainer;
        if (!(expandableView instanceof ExpandableNotificationRow) || (notificationChildrenContainer = (expandableNotificationRow = (ExpandableNotificationRow) expandableView).mChildrenContainer) == null) {
            return 0.0f;
        }
        return expandableNotificationRow.mUserLocked ? notificationChildrenContainer.getGroupExpandFraction() : expandableNotificationRow.isGroupExpanded() ? 1.0f : 0.0f;
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
        return f > 0.0f ? MathUtils.lerp(this.mGapHeightOnLockscreen, this.mGapHeight, f) : z ? this.mGapHeightOnLockscreen : this.mGapHeight;
    }

    public final void initView(Context context) {
        Resources resources = context.getResources();
        this.mPaddingBetweenElements = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_divider_height);
        this.mCollapsedSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_min_height);
        this.mEnableNotificationClipping = resources.getBoolean(com.android.systemui.R.bool.notification_enable_clipping);
        this.mClipNotificationScrollToTop = resources.getBoolean(com.android.systemui.R.bool.config_clipNotificationScrollToTop);
        this.mHeadsUpInset = resources.getDimensionPixelSize(com.android.systemui.R.dimen.heads_up_status_bar_padding) + SystemBarUtils.getStatusBarHeight(context);
        this.mPinnedZTranslationExtra = resources.getDimensionPixelSize(QpRune.QUICK_TABLET ? com.android.systemui.R.dimen.heads_up_pinned_elevation_tablet : com.android.systemui.R.dimen.heads_up_pinned_elevation);
        this.mGapHeight = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_section_divider_height);
        this.mGapHeightOnLockscreen = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_section_divider_height_lockscreen);
        this.mMarginBottom = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_panel_margin_bottom);
        SystemBarUtils.getQuickQsOffsetHeight(context);
        resources.getDimension(com.android.systemui.R.dimen.notification_corner_radius_small);
        resources.getDimension(com.android.systemui.R.dimen.notification_corner_radius);
        this.mMaxGroupExpandedBottomGap = resources.getDimension(com.android.systemui.R.dimen.notification_group_expanded_max_bottom_gap);
    }

    public void maybeUpdateHeadsUpIsVisible(ExpandableViewState expandableViewState, boolean z, boolean z2, boolean z3, float f, float f2) {
        if (z && z2 && z3) {
            expandableViewState.headsUpIsVisible = f < f2;
        }
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

    public void updateViewWithShelf(ExpandableView expandableView, ExpandableViewState expandableViewState, float f) {
        expandableViewState.setYTranslation(Math.min(expandableViewState.mYTranslation, f));
        if (expandableViewState.mYTranslation >= f) {
            expandableViewState.hidden = (expandableView.isExpandAnimationRunning() || expandableView.hasExpandingChild()) ? false : true;
            expandableViewState.inShelf = true;
            expandableViewState.headsUpIsVisible = false;
        }
    }
}
