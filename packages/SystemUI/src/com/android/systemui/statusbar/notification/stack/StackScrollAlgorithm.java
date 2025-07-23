package com.android.systemui.statusbar.notification.stack;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.Dependency;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.headsup.AvalancheController;
import com.android.systemui.statusbar.notification.headsup.HeadsUpAnimator;
import com.android.systemui.statusbar.notification.headsup.NotificationsHunSharedAnimationValues;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.NotificationBackgroundView;
import com.android.systemui.statusbar.notification.shared.NotificationHeadsUpCycling;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class StackScrollAlgorithm {
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
    public int mHeadsUpCyclingPadding;
    float mHeadsUpInset;
    public final ViewGroup mHostView;
    public boolean mIsExpanded;
    public float mMaxGroupExpandedBottomGap;
    public float mNotificationScrimPadding;
    public float mOngoingGapHeight;
    public float mPaddingBetweenElements;
    public int mPinnedZTranslationExtra;
    public final StackScrollAlgorithmState mTempAlgorithmState = new StackScrollAlgorithmState();
    public boolean mFavoriteGap = false;
    public float mOverExpansionAmount = 0.0f;
    public boolean mOngoingGap = false;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface BypassController {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface SectionProvider {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class StackScrollAlgorithmState {
        public ExpandableView firstViewInShelf;
        public float mCurrentExpandedYPosition;
        public float mCurrentYPosition;
        public final ArrayList visibleChildren = new ArrayList();
    }

    static {
        SourceType.from("StackScrollAlgorithm");
    }

    public StackScrollAlgorithm(Context context, ViewGroup viewGroup, HeadsUpAnimator headsUpAnimator) {
        this.mHostView = viewGroup;
        this.mContext = context;
        initView(context);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v5, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r17v0 */
    /* JADX WARN: Type inference failed for: r17v1, types: [int] */
    /* JADX WARN: Type inference failed for: r17v2 */
    /* JADX WARN: Type inference failed for: r17v3 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v3, types: [int] */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v19, types: [int] */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v20, types: [int] */
    /* JADX WARN: Type inference failed for: r7v21, types: [int] */
    /* JADX WARN: Type inference failed for: r7v22, types: [int] */
    /* JADX WARN: Type inference failed for: r7v23 */
    /* JADX WARN: Type inference failed for: r7v24 */
    /* JADX WARN: Type inference failed for: r7v25, types: [int] */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v30, types: [int] */
    /* JADX WARN: Type inference failed for: r7v31 */
    /* JADX WARN: Type inference failed for: r7v33, types: [int] */
    /* JADX WARN: Type inference failed for: r7v34, types: [int] */
    /* JADX WARN: Type inference failed for: r7v4, types: [int] */
    /* JADX WARN: Type inference failed for: r7v5, types: [int] */
    /* JADX WARN: Type inference failed for: r7v6 */
    public static void getNotificationChildrenStates(StackScrollAlgorithmState stackScrollAlgorithmState) {
        int i;
        boolean z;
        int i2;
        int i3;
        float f;
        float f2;
        int i4;
        int i5;
        int i6;
        int i7;
        boolean z2;
        char c;
        int i8;
        StackScrollAlgorithmState stackScrollAlgorithmState2 = stackScrollAlgorithmState;
        int size = stackScrollAlgorithmState2.visibleChildren.size();
        boolean z3 = false;
        int i9 = 0;
        while (i9 < size) {
            ExpandableView expandableView = (ExpandableView) stackScrollAlgorithmState2.visibleChildren.get(i9);
            if (expandableView instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
                if (expandableNotificationRow.mIsSummaryWithChildren) {
                    ExpandableViewState expandableViewState = expandableNotificationRow.mViewState;
                    NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
                    int size2 = ((ArrayList) notificationChildrenContainer.mAttachedChildren).size();
                    ?? r7 = notificationChildrenContainer.mContainingNotification.isGroupExpanded$1() ? notificationChildrenContainer.mHeaderExpandedHeight : z3;
                    int maxAllowedVisibleChildren = notificationChildrenContainer.getMaxAllowedVisibleChildren();
                    int i10 = maxAllowedVisibleChildren - 1;
                    boolean z4 = notificationChildrenContainer.mUserLocked;
                    if (z4) {
                        f = notificationChildrenContainer.getGroupExpandFraction();
                        i3 = notificationChildrenContainer.getMaxAllowedVisibleChildren(z3);
                    } else {
                        i3 = maxAllowedVisibleChildren;
                        f = 0.0f;
                    }
                    char c2 = 1;
                    boolean z5 = (!notificationChildrenContainer.mChildrenExpanded || notificationChildrenContainer.mContainingNotification.isGroupExpansionChanging()) ? z3 : true;
                    ?? r17 = z3;
                    int i11 = r17;
                    boolean z6 = true;
                    for (?? r2 = z3; r2 < size2; r2++) {
                        ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(r2);
                        if (z6) {
                            boolean z7 = notificationChildrenContainer.mUntruncatedChildCount > 1 && !notificationChildrenContainer.showingLowPriorityGroupHeader();
                            if (z4) {
                                boolean z8 = z7;
                                int interpolate = (int) (NotificationUtils.interpolate(0.0f, notificationChildrenContainer.mAdditionalExpandedHeaderMargin + notificationChildrenContainer.mHeaderExpandedHeight, f) + ((float) r7));
                                if (z8) {
                                    ArrayList arrayList = notificationChildrenContainer.mExpanderViewStates;
                                    int size3 = arrayList.size();
                                    i6 = interpolate;
                                    int i12 = 0;
                                    while (i12 < size3) {
                                        Object obj = arrayList.get(i12);
                                        int i13 = i12 + 1;
                                        ViewState viewState = (ViewState) obj;
                                        viewState.hidden = false;
                                        viewState.setAlpha(NotificationUtils.interpolate(0.0f, 1.0f, f));
                                        arrayList = arrayList;
                                        i12 = i13;
                                        size = size;
                                        i9 = i9;
                                    }
                                    i4 = size;
                                    i5 = i9;
                                    float f3 = 0.0f;
                                    float f4 = 1.0f;
                                    ArrayList arrayList2 = notificationChildrenContainer.mChildrenCountViewStates;
                                    int size4 = arrayList2.size();
                                    int i14 = 0;
                                    while (i14 < size4) {
                                        Object obj2 = arrayList2.get(i14);
                                        i14++;
                                        ((ViewState) obj2).setAlpha(NotificationUtils.interpolate(f4, f3, f));
                                        size4 = size4;
                                        arrayList2 = arrayList2;
                                        f3 = 0.0f;
                                        f4 = 1.0f;
                                    }
                                } else {
                                    i6 = interpolate;
                                    i4 = size;
                                    i5 = i9;
                                    ArrayList arrayList3 = notificationChildrenContainer.mExpanderViewStates;
                                    int size5 = arrayList3.size();
                                    int i15 = 0;
                                    while (i15 < size5) {
                                        Object obj3 = arrayList3.get(i15);
                                        i15++;
                                        ViewState viewState2 = (ViewState) obj3;
                                        viewState2.hidden = false;
                                        viewState2.setAlpha(1.0f);
                                    }
                                }
                                r7 = i6;
                            } else {
                                boolean z9 = z7;
                                i4 = size;
                                i5 = i9;
                                r7 += notificationChildrenContainer.mChildrenExpanded ? notificationChildrenContainer.mAdditionalExpandedHeaderMargin : 0;
                                if (z9) {
                                    ArrayList arrayList4 = notificationChildrenContainer.mExpanderViewStates;
                                    int size6 = arrayList4.size();
                                    int i16 = 0;
                                    while (i16 < size6) {
                                        Object obj4 = arrayList4.get(i16);
                                        i16++;
                                        ViewState viewState3 = (ViewState) obj4;
                                        ArrayList arrayList5 = arrayList4;
                                        viewState3.hidden = false;
                                        viewState3.setAlpha(notificationChildrenContainer.mChildrenExpanded ? 1.0f : 0.0f);
                                        arrayList4 = arrayList5;
                                    }
                                    ArrayList arrayList6 = notificationChildrenContainer.mChildrenCountViewStates;
                                    int size7 = arrayList6.size();
                                    int i17 = 0;
                                    while (i17 < size7) {
                                        Object obj5 = arrayList6.get(i17);
                                        i17++;
                                        ArrayList arrayList7 = arrayList6;
                                        ((ViewState) obj5).setAlpha(notificationChildrenContainer.mChildrenExpanded ? 0.0f : 1.0f);
                                        arrayList6 = arrayList7;
                                    }
                                } else {
                                    ArrayList arrayList8 = notificationChildrenContainer.mExpanderViewStates;
                                    int size8 = arrayList8.size();
                                    int i18 = 0;
                                    while (i18 < size8) {
                                        Object obj6 = arrayList8.get(i18);
                                        i18++;
                                        ViewState viewState4 = (ViewState) obj6;
                                        viewState4.hidden = false;
                                        viewState4.setAlpha(1.0f);
                                        arrayList8 = arrayList8;
                                    }
                                    ArrayList arrayList9 = notificationChildrenContainer.mChildrenCountViewStates;
                                    int size9 = arrayList9.size();
                                    int i19 = 0;
                                    while (i19 < size9) {
                                        Object obj7 = arrayList9.get(i19);
                                        i19++;
                                        ((ViewState) obj7).setAlpha(0.0f);
                                        arrayList9 = arrayList9;
                                    }
                                }
                            }
                            z6 = false;
                        } else {
                            if (r2 > c2 || notificationChildrenContainer.mChildrenExpanded) {
                                r7 = z4 ? (int) (NotificationUtils.interpolate(notificationChildrenContainer.mChildPadding, notificationChildrenContainer.mDividerHeight, f) + ((float) r7)) : r7 + (notificationChildrenContainer.mChildrenExpanded ? notificationChildrenContainer.mDividerHeight : notificationChildrenContainer.mChildPadding);
                            } else if (z4) {
                                r7 = (int) (NotificationUtils.interpolate(0.0f, notificationChildrenContainer.mDividerHeight, f) + ((float) r7));
                            }
                            i4 = size;
                            i5 = i9;
                        }
                        ExpandableViewState expandableViewState2 = expandableNotificationRow2.mViewState;
                        expandableViewState2.dimmed = expandableViewState.dimmed;
                        int intrinsicHeight = expandableNotificationRow2.getIntrinsicHeight();
                        expandableViewState2.height = intrinsicHeight;
                        if (r2 == 0) {
                            if (!notificationChildrenContainer.mContainingNotification.areGutsExposed()) {
                                expandableViewState2.clipBottomAmount = 0;
                                NotificationBackgroundView notificationBackgroundView = expandableNotificationRow2.mBackgroundNormal;
                                notificationBackgroundView.mBottomClipRounded = false;
                                notificationBackgroundView.invalidate();
                            } else if (intrinsicHeight > notificationChildrenContainer.mContainingNotification.getIntrinsicHeight()) {
                                expandableViewState2.clipBottomAmount = intrinsicHeight - notificationChildrenContainer.mContainingNotification.getIntrinsicHeight();
                                NotificationBackgroundView notificationBackgroundView2 = expandableNotificationRow2.mBackgroundNormal;
                                notificationBackgroundView2.mBottomClipRounded = true;
                                notificationBackgroundView2.invalidate();
                            }
                            r17 = intrinsicHeight;
                        } else if (i11 != 0) {
                            r7 += r17 - intrinsicHeight;
                        }
                        float f5 = (float) r7;
                        expandableViewState2.setYTranslation(f5);
                        expandableViewState2.hidden = false;
                        if (expandableNotificationRow2.mExpandAnimationRunning || notificationChildrenContainer.mContainingNotification.mChildIsExpanding) {
                            expandableViewState2.setZTranslation(expandableNotificationRow2.getTranslationZ());
                        } else if (z5 && notificationChildrenContainer.mEnableShadowOnChildNotifications) {
                            expandableViewState2.setZTranslation(expandableViewState.mZTranslation);
                        } else {
                            expandableViewState2.setZTranslation(10.0f - ((float) r2));
                        }
                        expandableViewState2.hideSensitive = expandableViewState.hideSensitive;
                        if (i11 == 0) {
                            i7 = intrinsicHeight;
                            expandableViewState2.clipTopAmount = 0;
                            expandableNotificationRow2.setContentClipTopAmount(0);
                        } else if (!z4 || f <= 0.0f) {
                            i7 = intrinsicHeight;
                            expandableViewState2.clipTopAmount = i7 - notificationChildrenContainer.mOverLappedSize;
                        } else {
                            expandableViewState2.clipTopAmount = 0;
                            i7 = intrinsicHeight;
                            expandableNotificationRow2.setContentClipTopAmount((int) ((((ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(r2 - 1)).getTranslationY() + r1.getIntrinsicHeight()) - expandableNotificationRow2.getTranslationY()));
                        }
                        if (notificationChildrenContainer.showingLowPriorityGroupHeader() && !notificationChildrenContainer.mChildrenExpanded && !z4 && !notificationChildrenContainer.mContainingNotification.isGroupExpansionChanging()) {
                            expandableNotificationRow2.setContentClipTopAmount(expandableNotificationRow2.getIntrinsicHeight());
                        }
                        if (r2 != 0 || (!notificationChildrenContainer.mUserLocked && notificationChildrenContainer.mContainingNotification.isGroupExpanded$1())) {
                            z2 = true;
                            expandableNotificationRow2.setClickable(true);
                        } else {
                            expandableNotificationRow2.setClickable(false);
                            z2 = true;
                        }
                        expandableViewState2.setAlpha(0.0f);
                        if (r2 < i3) {
                            if (!z4 || r2 <= 0) {
                                expandableViewState2.setAlpha(1.0f);
                                expandableViewState2.setScaleX(1.0f);
                            } else {
                                expandableViewState2.setAlpha(r2 == z2 ? Math.max(notificationChildrenContainer.mReduceTransparencyAndBlurOn ? 0.4f : 0.5f, f) : f);
                                expandableViewState2.setScaleX(NotificationUtils.interpolate(0.92f, 1.0f, f));
                            }
                        } else if (f == 1.0f && r2 <= i10) {
                            expandableViewState2.setAlpha((notificationChildrenContainer.mActualHeight - expandableViewState2.mYTranslation) / expandableViewState2.height);
                            expandableViewState2.setAlpha(Math.max(0.0f, Math.min(1.0f, expandableViewState2.mAlpha)));
                            expandableViewState2.setScaleX(1.0f);
                        } else if (maxAllowedVisibleChildren == 1 && r2 > 0) {
                            expandableViewState2.setAlpha(notificationChildrenContainer.mReduceTransparencyAndBlurOn ? 0.4f : 0.5f);
                            expandableViewState2.setScaleX(0.92f);
                        }
                        expandableViewState2.location = expandableViewState.location;
                        expandableViewState2.inShelf = expandableViewState.inShelf;
                        expandableNotificationRow2.setContentAlphaLocked(false);
                        if (r2 == 0 && notificationChildrenContainer.showingLowPriorityGroupHeader()) {
                            expandableNotificationRow2.setContentAlpha(NotificationUtils.interpolate(0.0f, 1.0f, notificationChildrenContainer.mUserLocked ? f : notificationChildrenContainer.mContainingNotification.isGroupExpanded$1() ? 1.0f : 0.0f));
                            c = 1;
                            if (!z4) {
                                expandableNotificationRow2.setContentAlphaLocked(true);
                            }
                        } else if (!notificationChildrenContainer.mIsMinimized || notificationChildrenContainer.mContainingNotification.isGroupExpanded$1() || z4 || notificationChildrenContainer.mContainingNotification.isGroupExpansionChanging()) {
                            c = 1;
                            expandableNotificationRow2.setContentAlpha(1.0f);
                        } else {
                            expandableNotificationRow2.setContentAlpha(0.0f);
                            c = 1;
                            expandableNotificationRow2.setContentAlphaLocked(true);
                        }
                        if (r2 >= c || notificationChildrenContainer.mChildrenExpanded) {
                            r7 += i7;
                            i11 = 0;
                        } else {
                            if (z4) {
                                i8 = (int) (NotificationUtils.interpolate(notificationChildrenContainer.mOverLappedSize, i7 - (i7 - (r2 + 1 < size2 ? ((ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(r0)).getIntrinsicHeight() : 0)), f) + f5);
                            } else {
                                i8 = notificationChildrenContainer.mOverLappedSize + r7;
                            }
                            r7 = i8;
                            i11 = 1;
                        }
                        if (notificationChildrenContainer.mContainingNotification.isInsignificantSummary()) {
                            expandableNotificationRow2.updateInsignificantAlpha(notificationChildrenContainer.mUserLocked ? f : notificationChildrenContainer.mContainingNotification.isGroupExpanded$1() ? 1.0f : 0.0f);
                        }
                        size = i4;
                        i9 = i5;
                        c2 = 1;
                    }
                    i = size;
                    i2 = i9;
                    if (notificationChildrenContainer.mOverflowNumber != null) {
                        notificationChildrenContainer.mGroupOverFlowState.copyFrom(((ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(Math.min(notificationChildrenContainer.getMaxAllowedVisibleChildren(true), size2) - 1)).mViewState);
                        ViewState viewState5 = notificationChildrenContainer.mGroupOverFlowState;
                        viewState5.setYTranslation(viewState5.mYTranslation + (notificationChildrenContainer.mContainingNotification.isGroupExpanded$1() ? notificationChildrenContainer.mHeaderExpandedHeight : 0));
                        notificationChildrenContainer.mGroupOverFlowState.setAlpha(0.0f);
                    }
                    if (notificationChildrenContainer.mCurrentHeader != null) {
                        if (notificationChildrenContainer.mHeaderViewState == null) {
                            notificationChildrenContainer.mHeaderViewState = new ViewState();
                        }
                        notificationChildrenContainer.mHeaderViewState.initFrom(notificationChildrenContainer.mCurrentHeader);
                        if (notificationChildrenContainer.mContainingNotification.mChildIsExpanding) {
                            notificationChildrenContainer.mHeaderViewState.setZTranslation(notificationChildrenContainer.mGroupHeader.getTranslationZ());
                        } else if (z5) {
                            notificationChildrenContainer.mHeaderViewState.setZTranslation(expandableViewState.mZTranslation);
                        } else if (notificationChildrenContainer.mCurrentHeader == notificationChildrenContainer.mMinimizedGroupHeader) {
                            notificationChildrenContainer.mHeaderViewState.setZTranslation(11.0f);
                        } else {
                            notificationChildrenContainer.mHeaderViewState.setZTranslation(0.0f);
                        }
                        z = false;
                        notificationChildrenContainer.mHeaderViewState.setYTranslation(0);
                        View findViewById = notificationChildrenContainer.mCurrentHeader.findViewById(R.id.resolver_list);
                        View findViewById2 = notificationChildrenContainer.mCurrentHeader.findViewById(R.id.flagRetrieveInteractiveWindows);
                        if (findViewById != null) {
                            findViewById.setAlpha(notificationChildrenContainer.mHeaderVisibleAmount);
                        }
                        if (findViewById2 != null) {
                            findViewById2.setAlpha(notificationChildrenContainer.mHeaderVisibleAmount);
                        }
                        View findViewById3 = notificationChildrenContainer.mCurrentHeader.findViewById(R.id.icon);
                        View findViewById4 = notificationChildrenContainer.mCurrentHeader.findViewById(R.id.input_separator);
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
                    z3 = z;
                    size = i;
                }
            }
            i = size;
            z = z3;
            i2 = i9;
            i9 = i2 + 1;
            stackScrollAlgorithmState2 = stackScrollAlgorithmState;
            z3 = z;
            size = i;
        }
    }

    public static float getPreviousGroupExpandFraction(ExpandableView expandableView) {
        ExpandableNotificationRow expandableNotificationRow;
        NotificationChildrenContainer notificationChildrenContainer;
        if (!(expandableView instanceof ExpandableNotificationRow) || (notificationChildrenContainer = (expandableNotificationRow = (ExpandableNotificationRow) expandableView).mChildrenContainer) == null) {
            return 0.0f;
        }
        return expandableNotificationRow.mUserLocked ? notificationChildrenContainer.getGroupExpandFraction() : expandableNotificationRow.isGroupExpanded$1() ? 1.0f : 0.0f;
    }

    public static boolean isCyclingIn(ExpandableNotificationRow expandableNotificationRow, AmbientState ambientState) {
        int i = NotificationHeadsUpCycling.$r8$clinit;
        AvalancheController avalancheController = ambientState.mAvalancheController;
        String str = avalancheController.previousHunKey;
        return expandableNotificationRow.getKey().equals((str == null || str.isEmpty() || str.equals("HeadsUpEntry null") || str.equals("HeadsUpEntry.mEntry null")) ? "" : AvalancheController.getKey(avalancheController.headsUpEntryShowing));
    }

    public final boolean childNeedsGapHeight(SectionProvider sectionProvider, ExpandableView expandableView, ExpandableView expandableView2) {
        NotificationSectionsManager notificationSectionsManager = (NotificationSectionsManager) sectionProvider;
        Integer bucket = notificationSectionsManager.getBucket(expandableView2);
        Integer bucket2 = notificationSectionsManager.getBucket(expandableView);
        boolean z = false;
        if (bucket != null && bucket.intValue() == 3 && bucket.equals(bucket2)) {
            return false;
        }
        Integer bucket3 = notificationSectionsManager.getBucket(expandableView2);
        boolean z2 = (bucket3 == null || bucket3.intValue() != 3 || bucket3.equals(notificationSectionsManager.getBucket(expandableView))) ? false : true;
        this.mOngoingGap = z2;
        if (z2) {
            return z2;
        }
        Integer bucket4 = notificationSectionsManager.getBucket(expandableView2);
        Integer bucket5 = notificationSectionsManager.getBucket(expandableView);
        if (bucket4 != null && bucket4.intValue() == 2 && !bucket4.equals(bucket5)) {
            z = true;
        }
        this.mFavoriteGap = z;
        return z;
    }

    public void clampHunToTop(float f, float f2, ExpandableViewState expandableViewState) {
        float max = Math.max(f, expandableViewState.mYTranslation);
        float f3 = expandableViewState.height - (max - expandableViewState.mYTranslation);
        expandableViewState.setYTranslation(max);
        expandableViewState.height = (int) Math.max(f3, f2);
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
        this.mCollapsedSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_min_height);
        this.mEnableNotificationClipping = resources.getBoolean(com.android.systemui.R.bool.notification_enable_clipping);
        this.mClipNotificationScrollToTop = resources.getBoolean(com.android.systemui.R.bool.config_clipNotificationScrollToTop);
        this.mHeadsUpInset = resources.getDimensionPixelSize(com.android.systemui.R.dimen.heads_up_status_bar_padding) + SystemBarUtils.getStatusBarHeight(context);
        this.mHeadsUpAppearStartAboveScreen = resources.getDimensionPixelSize(com.android.systemui.R.dimen.heads_up_appear_y_above_screen);
        this.mHeadsUpCyclingPadding = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.heads_up_cycling_padding);
        this.mPinnedZTranslationExtra = resources.getDimensionPixelSize(com.android.systemui.R.dimen.heads_up_pinned_elevation);
        this.mGapHeight = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_section_divider_height);
        this.mGapHeightOnLockscreen = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_section_divider_height_lockscreen);
        this.mNotificationScrimPadding = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_scrim_top_padding);
        resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_panel_margin_bottom);
        SystemBarUtils.getQuickQsOffsetHeight(context);
        resources.getDimension(com.android.systemui.R.dimen.notification_corner_radius_small);
        resources.getDimension(com.android.systemui.R.dimen.notification_corner_radius);
        int i = NotificationsHunSharedAnimationValues.$r8$clinit;
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
        if (!z || z2 || z3) {
            return false;
        }
        return !z4 || z5;
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
        expandableViewState.setYTranslation(Math.min(expandableViewState.mYTranslation, f));
        if (expandableViewState.mYTranslation >= f) {
            expandableViewState.hidden = (expandableView.isExpandAnimationRunning() || expandableView.hasExpandingChild()) ? false : true;
            expandableViewState.inShelf = true;
            expandableViewState.headsUpIsVisible = false;
        }
    }

    public void updateZTranslationForHunInStack(float f, float f2, float f3, ExpandableViewState expandableViewState) {
        SceneContainerFlag.isUnexpectedlyInLegacyMode();
    }

    public void clampHunToTop(float f, float f2, float f3, ExpandableViewState expandableViewState) {
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        clampHunToTop(f + f2, f3, expandableViewState);
    }
}
