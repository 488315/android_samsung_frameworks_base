package com.android.systemui.statusbar.notification.stack;

import android.app.Notification;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Trace;
import android.service.notification.StatusBarNotification;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.NotificationHeaderView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.android.internal.widget.CachingIconView;
import com.android.internal.widget.NotificationExpandButton;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.NotificationGroupingUtil;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.Roundable;
import com.android.systemui.statusbar.notification.RoundableState;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.HybridGroupManager;
import com.android.systemui.statusbar.notification.row.NotificationBackgroundView;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.row.shared.AsyncGroupHeaderViewInflation;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationGroupHeaderViewWrapper;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.util.PluralMessageFormaterKt;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.List;
import noticolorpicker.NotificationColorPicker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NotificationChildrenContainer extends ViewGroup implements NotificationFadeAware, Roundable {
    public static final SourceType$Companion$from$1 FROM_PARENT;
    static final int NUMBER_OF_CHILDREN_WHEN_COLLAPSED = 1;
    static final int NUMBER_OF_CHILDREN_WHEN_SYSTEM_EXPANDED = 5;
    public int mActualHeight;
    public int mAdditionalExpandedHeaderMargin;
    public final List mAttachedChildren;
    public Path mChildClipPath;
    public int mChildPadding;
    public ArrayList mChildrenCountViewStates;
    public ArrayList mChildrenCountViews;
    public boolean mChildrenExpanded;
    public int mClipBottomAmount;
    public ExpandableNotificationRow mContainingNotification;
    public boolean mContainingNotificationIsFaded;
    public ViewGroup mCurrentHeader;
    public float mDividerAlpha;
    public int mDividerHeight;
    public final List mDividers;
    public boolean mEnableShadowOnChildNotifications;
    public ArrayList mExpanderViewStates;
    public ArrayList mExpanderViews;
    public NotificationHeaderView mGroupHeader;
    public NotificationHeaderViewWrapper mGroupHeaderWrapper;
    public CachingIconView mGroupIconView;
    public ViewState mGroupOverFlowState;
    public NotificationGroupingUtil mGroupingUtil;
    public ExpandableNotificationRow.AnonymousClass1 mHeaderClickListener;
    public int mHeaderExpandedHeight;
    public int mHeaderHeight;
    public ViewState mHeaderViewState;
    public final float mHeaderVisibleAmount;
    public boolean mHideDividersDuringExpand;
    public final HybridGroupManager mHybridGroupManager;
    public boolean mIsConversation;
    public boolean mIsMinimized;
    public NotificationChildrenContainerLogger mLogger;
    public int mMinGroupSummaryHeight;
    public NotificationHeaderView mMinimizedGroupHeader;
    public NotificationHeaderViewWrapper mMinimizedGroupHeaderWrapper;
    public boolean mNeverAppliedGroupState;
    public NotificationHeaderView mNotificationHeaderExpanded;
    public NotificationGroupHeaderViewWrapper mNotificationHeaderWrapperExpanded;
    public int mOverLappedSize;
    public TextView mOverflowNumber;
    public int mRealHeight;
    public boolean mReduceTransparencyAndBlurOn;
    public final RoundableState mRoundableState;
    public boolean mShowDividersWhenExpanded;
    public boolean mShowGroupCountInExpander;
    public float mTranslationYFactor;
    public int mTranslationYForAdd;
    public int mUntruncatedChildCount;
    public boolean mUserLocked;
    public long mWhenMillis;

    static {
        new AnimationProperties() { // from class: com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer.1
            public final AnimationFilter mAnimationFilter;

            {
                AnimationFilter animationFilter = new AnimationFilter();
                animationFilter.animateAlpha = true;
                this.mAnimationFilter = animationFilter;
            }

            @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
            public final AnimationFilter getAnimationFilter() {
                return this.mAnimationFilter;
            }
        }.duration = 200L;
        FROM_PARENT = SourceType.from("FromParent(NCC)");
    }

    public NotificationChildrenContainer(Context context) {
        this(context, null);
    }

    public final void addTransientView(View view, int i) {
        NotificationChildrenContainerLogger notificationChildrenContainerLogger = this.mLogger;
        if (notificationChildrenContainerLogger != null && (view instanceof ExpandableNotificationRow)) {
            String str = ((ExpandableNotificationRow) view).mLoggingKey;
            String str2 = this.mContainingNotification.mLoggingKey;
            notificationChildrenContainerLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            NotificationChildrenContainerLogger$$ExternalSyntheticLambda0 notificationChildrenContainerLogger$$ExternalSyntheticLambda0 = new NotificationChildrenContainerLogger$$ExternalSyntheticLambda0(1);
            LogBuffer logBuffer = notificationChildrenContainerLogger.notificationRenderBuffer;
            LogMessage obtain = logBuffer.obtain("NotifChildrenContainer", logLevel, notificationChildrenContainerLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = str;
            logMessageImpl.str2 = str2;
            logMessageImpl.int1 = i;
            logBuffer.commit(obtain);
        }
        super.addTransientView(view, i);
    }

    @Override // com.android.systemui.statusbar.notification.Roundable
    public final void applyRoundnessAndInvalidate() {
        NotificationHeaderViewWrapper notificationHeaderViewWrapper = this.mGroupHeaderWrapper;
        if (notificationHeaderViewWrapper != null) {
            notificationHeaderViewWrapper.requestTopRoundness(this.mRoundableState.topRoundness, FROM_PARENT, false);
        }
        NotificationHeaderViewWrapper notificationHeaderViewWrapper2 = this.mMinimizedGroupHeaderWrapper;
        if (notificationHeaderViewWrapper2 != null) {
            notificationHeaderViewWrapper2.requestTopRoundness(this.mRoundableState.topRoundness, FROM_PARENT, false);
        }
        for (int size = ((ArrayList) this.mAttachedChildren).size() - 1; size >= 0; size--) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) ((ArrayList) this.mAttachedChildren).get(size);
            if (expandableNotificationRow.getVisibility() != 8) {
                expandableNotificationRow.requestRoundness(1.0f, 1.0f, FROM_PARENT, false);
            }
        }
        super.applyRoundnessAndInvalidate();
    }

    public final void applyState() {
        int size = ((ArrayList) this.mAttachedChildren).size();
        ViewState viewState = new ViewState();
        float groupExpandFraction = this.mUserLocked ? getGroupExpandFraction() : 0.0f;
        boolean z = true;
        boolean z2 = this.mUserLocked || this.mContainingNotification.isGroupExpansionChanging();
        if ((!this.mChildrenExpanded || !this.mShowDividersWhenExpanded) && (!z2 || this.mHideDividersDuringExpand)) {
            z = false;
        }
        for (int i = 0; i < size; i++) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) ((ArrayList) this.mAttachedChildren).get(i);
            ExpandableViewState expandableViewState = expandableNotificationRow.mViewState;
            expandableViewState.applyToView(expandableNotificationRow);
            View view = (View) ((ArrayList) this.mDividers).get(i);
            viewState.initFrom(view);
            viewState.setYTranslation(expandableViewState.mYTranslation - this.mDividerHeight);
            float f = (!this.mChildrenExpanded || expandableViewState.mAlpha == 0.0f) ? 0.0f : this.mDividerAlpha;
            if (this.mUserLocked) {
                float f2 = expandableViewState.mAlpha;
                if (f2 != 0.0f) {
                    f = NotificationUtils.interpolate(0.0f, this.mDividerAlpha, Math.min(f2, groupExpandFraction));
                }
            }
            viewState.hidden = !z;
            viewState.setAlpha(f);
            if (!z) {
                viewState.setAlpha(0.0f);
                view.setAlpha(0.0f);
            }
            viewState.applyToView(view);
            expandableNotificationRow.setFakeShadowIntensity(0, 0.0f, 0.0f, 0);
            if (!this.mContainingNotification.areGutsExposed()) {
                NotificationBackgroundView notificationBackgroundView = expandableNotificationRow.mBackgroundNormal;
                notificationBackgroundView.mBottomClipRounded = false;
                notificationBackgroundView.invalidate();
            }
        }
        if (getFirstChild() != null) {
            for (int i2 = 0; i2 < this.mExpanderViewStates.size() && i2 < getFirstChild().getExpandButtons().size(); i2++) {
                ((ViewState) this.mExpanderViewStates.get(i2)).applyToView((View) getFirstChild().getExpandButtons().get(i2));
            }
            for (int i3 = 0; i3 < this.mChildrenCountViewStates.size() && i3 < getFirstChild().getChildrenCountText().size(); i3++) {
                ((ViewState) this.mChildrenCountViewStates.get(i3)).applyToView((View) getFirstChild().getChildrenCountText().get(i3));
            }
        }
        ViewState viewState2 = this.mGroupOverFlowState;
        if (viewState2 != null) {
            viewState2.applyToView(this.mOverflowNumber);
            this.mNeverAppliedGroupState = false;
        }
        NotificationHeaderView notificationHeaderView = this.mCurrentHeader;
        if (notificationHeaderView != null) {
            if (notificationHeaderView == this.mMinimizedGroupHeader) {
                this.mHeaderViewState.setZTranslation(11.0f);
            }
            this.mHeaderViewState.applyToView(this.mCurrentHeader);
        }
        updateChildrenClipping();
    }

    public final NotificationHeaderView calculateDesiredHeader() {
        return (!showingLowPriorityGroupHeader() || this.mContainingNotification.isGroupExpanded$1()) ? this.mContainingNotification.isGroupExpanded$1() ? this.mNotificationHeaderExpanded : this.mGroupHeader : this.mMinimizedGroupHeader;
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j) {
        boolean z;
        Path path = this.mChildClipPath;
        if (path != null) {
            float translation = view instanceof ExpandableNotificationRow ? ((ExpandableNotificationRow) view).getTranslation() : view.getTranslationX();
            canvas.save();
            if (translation != 0.0f) {
                path.offset(translation, 0.0f);
                canvas.clipPath(path);
                path.offset(-translation, 0.0f);
            } else {
                canvas.clipPath(path);
            }
            z = true;
        } else {
            z = false;
        }
        if (view instanceof NotificationHeaderView) {
            this.mGroupHeaderWrapper.hasRoundedCorner();
        }
        if (!z) {
            return super.drawChild(canvas, view, j);
        }
        boolean drawChild = super.drawChild(canvas, view, j);
        canvas.restore();
        return drawChild;
    }

    public ViewGroup getCurrentHeaderView() {
        return this.mCurrentHeader;
    }

    public final ExpandableNotificationRow getFirstChild() {
        if (((ArrayList) this.mAttachedChildren).size() == 0) {
            return null;
        }
        return (ExpandableNotificationRow) ((ArrayList) this.mAttachedChildren).get(0);
    }

    public final float getGroupExpandFraction() {
        int i = ((this.mContainingNotification.isGroupExpanded$1() || this.mContainingNotification.mUserLocked) ? this.mHeaderExpandedHeight : 0) + this.mAdditionalExpandedHeaderMargin + this.mDividerHeight;
        int size = ((ArrayList) this.mAttachedChildren).size();
        int maxAllowedVisibleChildren = getMaxAllowedVisibleChildren(false);
        int i2 = 0;
        for (int i3 = 0; i3 < size && i2 < maxAllowedVisibleChildren; i3++) {
            i = (int) (i + (((ExpandableNotificationRow) ((ArrayList) this.mAttachedChildren).get(i3)).isExpanded(true) ? r6.getMaxExpandHeight() : r6.getShowingLayout().getMinHeight(true)));
            i2++;
        }
        int minHeight = getMinHeight(getMaxAllowedVisibleChildren(true));
        return Math.max(0.0f, Math.min(1.0f, (this.mActualHeight - minHeight) / (i - minHeight)));
    }

    public final int getIntrinsicHeight() {
        int interpolate;
        float maxAllowedVisibleChildren = getMaxAllowedVisibleChildren();
        int i = this.mContainingNotification.isGroupExpanded$1() ? this.mHeaderExpandedHeight : 0;
        int size = ((ArrayList) this.mAttachedChildren).size();
        float groupExpandFraction = this.mUserLocked ? getGroupExpandFraction() : 0.0f;
        boolean z = this.mChildrenExpanded;
        boolean z2 = true;
        int i2 = 0;
        for (int i3 = 0; i3 < size && i2 < maxAllowedVisibleChildren; i3++) {
            if (z2) {
                interpolate = this.mUserLocked ? (int) (NotificationUtils.interpolate(0.0f, this.mAdditionalExpandedHeaderMargin + this.mDividerHeight, groupExpandFraction) + i) : i + (z ? this.mAdditionalExpandedHeaderMargin + this.mDividerHeight : 0);
                z2 = false;
            } else if (this.mUserLocked) {
                interpolate = (int) (NotificationUtils.interpolate(this.mChildPadding, this.mDividerHeight, groupExpandFraction) + i);
            } else {
                interpolate = i + (z ? this.mDividerHeight : this.mChildPadding);
            }
            i = interpolate + ((ExpandableNotificationRow) ((ArrayList) this.mAttachedChildren).get(i3)).getIntrinsicHeight();
            i2++;
        }
        if (this.mUserLocked) {
            i = (int) (NotificationUtils.interpolate(0.0f, this.mHeaderExpandedHeight, groupExpandFraction) + i);
        } else if (!z) {
            i = (int) (i + 0.0f);
        }
        int i4 = this.mMinGroupSummaryHeight;
        return i < i4 ? i4 : maxAllowedVisibleChildren == 1.0f ? i + this.mOverLappedSize : i;
    }

    public int getMaxAllowedVisibleChildren() {
        return getMaxAllowedVisibleChildren(false);
    }

    public final int getMinHeight(int i) {
        int i2 = this.mContainingNotification.isGroupExpanded$1() ? this.mHeaderExpandedHeight : 0;
        int size = ((ArrayList) this.mAttachedChildren).size();
        int i3 = 0;
        boolean z = true;
        for (int i4 = 0; i4 < size && i3 < i; i4++) {
            if (z) {
                z = false;
            } else {
                i2 += this.mChildPadding;
            }
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) ((ArrayList) this.mAttachedChildren).get(i4);
            if (expandableNotificationRow != null) {
                i2 = expandableNotificationRow.getMinHeight(false) + i2;
            }
            i3++;
        }
        int i5 = (int) (i2 + 0.0f);
        int i6 = this.mMinGroupSummaryHeight;
        return i5 < i6 ? i6 : i == 1 ? i5 + this.mOverLappedSize : i5;
    }

    public final int getNotificationChildCount() {
        return ((ArrayList) this.mAttachedChildren).size();
    }

    @Override // com.android.systemui.statusbar.notification.Roundable
    public final RoundableState getRoundableState() {
        return this.mRoundableState;
    }

    public final NotificationViewWrapper getVisibleWrapper() {
        return showingLowPriorityGroupHeader() ? this.mMinimizedGroupHeaderWrapper : this.mContainingNotification.isGroupExpanded$1() ? this.mNotificationHeaderWrapperExpanded : this.mGroupHeaderWrapper;
    }

    public final NotificationViewWrapper getWrapperForView$1(View view) {
        return view == this.mGroupHeader ? this.mGroupHeaderWrapper : view == this.mNotificationHeaderExpanded ? this.mNotificationHeaderWrapperExpanded : this.mMinimizedGroupHeaderWrapper;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public final View inflateDivider() {
        View inflate = LayoutInflater.from(((ViewGroup) this).mContext).inflate(R.layout.notification_children_divider, (ViewGroup) this, false);
        inflate.setAlpha(0.0f);
        return inflate;
    }

    public final void initDimens$2() {
        Resources resources = getResources();
        this.mChildPadding = resources.getDimensionPixelOffset(R.dimen.notification_children_padding);
        this.mDividerHeight = Math.max(resources.getDimensionPixelOffset(R.dimen.notification_children_container_divider_height), 1);
        this.mDividerAlpha = resources.getFloat(R.dimen.notification_divider_alpha);
        this.mAdditionalExpandedHeaderMargin = resources.getDimensionPixelOffset(R.dimen.notification_children_container_top_padding);
        this.mOverLappedSize = resources.getDimensionPixelOffset(R.dimen.notification_children_overlapped_size);
        this.mHeaderHeight = NotificationUtils.getFontScaledMarginHeight(R.dimen.notification_children_container_header_collapsed_height, ((ViewGroup) this).mContext);
        this.mHeaderExpandedHeight = NotificationUtils.getFontScaledMarginHeight(R.dimen.notification_children_container_header_expanded_height, ((ViewGroup) this).mContext);
        NotificationUtils.getFontScaledMarginHeight(R.dimen.notification_children_container_header_low_priority_height, ((ViewGroup) this).mContext);
        this.mEnableShadowOnChildNotifications = resources.getBoolean(R.bool.config_enableShadowOnChildNotifications);
        this.mShowGroupCountInExpander = resources.getBoolean(R.bool.config_showNotificationGroupCountInExpander);
        this.mShowDividersWhenExpanded = resources.getBoolean(R.bool.config_showDividersWhenGroupNotificationExpanded);
        this.mHideDividersDuringExpand = resources.getBoolean(R.bool.config_hideDividersDuringExpand);
        resources.getDimensionPixelOffset(android.R.dimen.toast_elevation);
        HybridGroupManager hybridGroupManager = this.mHybridGroupManager;
        Resources resources2 = hybridGroupManager.mContext.getResources();
        hybridGroupManager.mOverflowNumberSize = resources2.getDimensionPixelSize(R.dimen.group_overflow_number_size);
        hybridGroupManager.mOverflowNumberPadding = resources2.getDimensionPixelSize(R.dimen.group_overflow_number_padding);
        getResources().getDimensionPixelSize(R.dimen.conversation_single_line_face_pile_size);
        this.mMinGroupSummaryHeight = getResources().getDimensionPixelSize(R.dimen.notification_min_group_summary_height);
        this.mTranslationYFactor = NotificationUtils.getFontScaledMarginHeight(R.dimen.notification_children_container_header_ytranslation_for_heads_up, ((ViewGroup) this).mContext);
        this.mTranslationYForAdd = resources.getDimensionPixelOffset(R.dimen.notification_children_container_ytranslation_for_add);
    }

    public boolean isUserLocked() {
        return this.mUserLocked;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateGroupOverflow();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int min = Math.min(((ArrayList) this.mAttachedChildren).size(), 8);
        if (this.mContainingNotification.isInsignificant()) {
            min = Math.min(((ArrayList) this.mAttachedChildren).size(), 50);
        }
        for (int i5 = 0; i5 < min; i5++) {
            View view = (View) ((ArrayList) this.mAttachedChildren).get(i5);
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            ((View) ((ArrayList) this.mDividers).get(i5)).layout(0, 0, getWidth(), this.mDividerHeight);
        }
        if (this.mOverflowNumber != null) {
            int width = getLayoutDirection() == 1 ? 0 : getWidth() - this.mOverflowNumber.getMeasuredWidth();
            int measuredWidth = this.mOverflowNumber.getMeasuredWidth() + width;
            TextView textView = this.mOverflowNumber;
            textView.layout(width, 0, measuredWidth, textView.getMeasuredHeight());
        }
        NotificationHeaderView notificationHeaderView = this.mGroupHeader;
        if (notificationHeaderView != null) {
            notificationHeaderView.layout(0, 0, notificationHeaderView.getMeasuredWidth(), this.mGroupHeader.getMeasuredHeight());
        }
        NotificationHeaderView notificationHeaderView2 = this.mMinimizedGroupHeader;
        if (notificationHeaderView2 != null) {
            notificationHeaderView2.layout(0, 0, notificationHeaderView2.getMeasuredWidth(), this.mMinimizedGroupHeader.getMeasuredHeight());
        }
        NotificationHeaderView notificationHeaderView3 = this.mNotificationHeaderExpanded;
        if (notificationHeaderView3 != null) {
            notificationHeaderView3.layout(0, 0, notificationHeaderView3.getMeasuredWidth(), this.mNotificationHeaderExpanded.getMeasuredHeight());
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        int i3;
        TextView textView;
        Trace.beginSection("NotificationChildrenContainer#onMeasure");
        int mode = View.MeasureSpec.getMode(i2);
        boolean z = mode == 1073741824;
        boolean z2 = mode == Integer.MIN_VALUE;
        int size = View.MeasureSpec.getSize(i2);
        int makeMeasureSpec = (z || z2) ? View.MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE) : i2;
        int size2 = View.MeasureSpec.getSize(i);
        TextView textView2 = this.mOverflowNumber;
        if (textView2 != null) {
            textView2.measure(View.MeasureSpec.makeMeasureSpec(size2, Integer.MIN_VALUE), makeMeasureSpec);
        }
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mDividerHeight, 1073741824);
        int i4 = this.mHeaderExpandedHeight + this.mAdditionalExpandedHeaderMargin;
        int min = Math.min(((ArrayList) this.mAttachedChildren).size(), 8);
        if (this.mContainingNotification.isInsignificant()) {
            min = Math.min(((ArrayList) this.mAttachedChildren).size(), 50);
        }
        int maxAllowedVisibleChildren = getMaxAllowedVisibleChildren(true);
        int i5 = min > maxAllowedVisibleChildren ? maxAllowedVisibleChildren - 1 : -1;
        int i6 = 0;
        while (i6 < min) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) ((ArrayList) this.mAttachedChildren).get(i6);
            int measuredWidth = (i6 != i5 || (textView = this.mOverflowNumber) == null) ? 0 : textView.getMeasuredWidth();
            NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
            if (measuredWidth != notificationContentView.mSingleLineWidthIndention) {
                notificationContentView.mSingleLineWidthIndention = measuredWidth;
                notificationContentView.mContainingNotification.forceLayout();
                notificationContentView.forceLayout();
            }
            expandableNotificationRow.measure(i, makeMeasureSpec);
            ((View) ((ArrayList) this.mDividers).get(i6)).measure(i, makeMeasureSpec2);
            if (expandableNotificationRow.getVisibility() != 8) {
                i4 = expandableNotificationRow.getMeasuredHeight() + this.mDividerHeight + i4;
            }
            i6++;
        }
        this.mRealHeight = i4;
        if (mode != 0) {
            i4 = Math.min(i4, size);
        }
        int makeMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(this.mHeaderHeight, 1073741824);
        NotificationHeaderView notificationHeaderView = this.mGroupHeader;
        if (notificationHeaderView != null) {
            notificationHeaderView.measure(i, makeMeasureSpec3);
        }
        if (this.mMinimizedGroupHeader != null) {
            if (!this.mContainingNotification.isInsignificant() || getFirstChild() == null || getFirstChild().getMinHeight(false) <= 0) {
                this.mMinGroupSummaryHeight = getResources().getDimensionPixelSize(R.dimen.notification_min_group_summary_height);
            } else {
                this.mMinGroupSummaryHeight = getFirstChild().getMinHeight(false);
            }
            i3 = 1073741824;
            this.mMinimizedGroupHeader.measure(i, View.MeasureSpec.makeMeasureSpec(this.mMinGroupSummaryHeight, 1073741824));
        } else {
            i3 = 1073741824;
        }
        if (this.mNotificationHeaderExpanded != null) {
            this.mNotificationHeaderExpanded.measure(i, View.MeasureSpec.makeMeasureSpec(this.mHeaderExpandedHeight, i3));
        }
        setMeasuredDimension(size2, i4);
        Trace.endSection();
    }

    public final void onNotificationUpdated() {
        if (this.mShowGroupCountInExpander) {
            return;
        }
        ((ViewGroup) this).mContext.getColor(android.R.color.secondary_text_inverse_when_activated_material);
        HybridGroupManager hybridGroupManager = this.mHybridGroupManager;
        TextView textView = this.mOverflowNumber;
        NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class);
        ExpandableNotificationRow expandableNotificationRow = this.mContainingNotification;
        boolean z = expandableNotificationRow.mDimmed;
        int appPrimaryColor = notificationColorPicker.getAppPrimaryColor(expandableNotificationRow);
        hybridGroupManager.mOverflowNumberColor = appPrimaryColor;
        if (textView != null) {
            textView.setTextColor(appPrimaryColor);
        }
    }

    public final boolean pointInView(float f, float f2, float f3) {
        float f4 = -f3;
        return f >= f4 && f2 >= f4 && f < ((float) (((ViewGroup) this).mRight - ((ViewGroup) this).mLeft)) + f3 && f2 < ((float) this.mRealHeight) + f3;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0171 A[Catch: NameNotFoundException -> 0x0155, TryCatch #0 {NameNotFoundException -> 0x0155, blocks: (B:32:0x0108, B:34:0x0139, B:36:0x013f, B:38:0x0147, B:45:0x0171, B:47:0x0184, B:49:0x0192, B:51:0x0198, B:52:0x01ba, B:54:0x01d8, B:55:0x01de, B:57:0x01e4, B:58:0x01e8, B:60:0x01af, B:61:0x01b6, B:62:0x01f8, B:64:0x0158, B:66:0x0160, B:68:0x0168), top: B:31:0x0108 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0184 A[Catch: NameNotFoundException -> 0x0155, TryCatch #0 {NameNotFoundException -> 0x0155, blocks: (B:32:0x0108, B:34:0x0139, B:36:0x013f, B:38:0x0147, B:45:0x0171, B:47:0x0184, B:49:0x0192, B:51:0x0198, B:52:0x01ba, B:54:0x01d8, B:55:0x01de, B:57:0x01e4, B:58:0x01e8, B:60:0x01af, B:61:0x01b6, B:62:0x01f8, B:64:0x0158, B:66:0x0160, B:68:0x0168), top: B:31:0x0108 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01f8 A[Catch: NameNotFoundException -> 0x0155, TRY_LEAVE, TryCatch #0 {NameNotFoundException -> 0x0155, blocks: (B:32:0x0108, B:34:0x0139, B:36:0x013f, B:38:0x0147, B:45:0x0171, B:47:0x0184, B:49:0x0192, B:51:0x0198, B:52:0x01ba, B:54:0x01d8, B:55:0x01de, B:57:0x01e4, B:58:0x01e8, B:60:0x01af, B:61:0x01b6, B:62:0x01f8, B:64:0x0158, B:66:0x0160, B:68:0x0168), top: B:31:0x0108 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void recreateLowPriorityHeader(android.app.Notification.Builder r15) {
        /*
            Method dump skipped, instructions count: 660
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer.recreateLowPriorityHeader(android.app.Notification$Builder):void");
    }

    public final void recreateNotificationHeader(ExpandableNotificationRow.AnonymousClass1 anonymousClass1, boolean z) {
        int i = AsyncGroupHeaderViewInflation.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        Trace.beginSection("NotifChildCont#recreateHeader");
        this.mHeaderClickListener = anonymousClass1;
        this.mIsConversation = z;
        int i2 = NotificationBundleUi.$r8$clinit;
        StatusBarNotification statusBarNotification = this.mContainingNotification.getEntryLegacy().mSbn;
        if (statusBarNotification == null) {
            return;
        }
        Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(getContext(), statusBarNotification.getNotification());
        Trace.beginSection("recreateHeader#makeNotificationGroupHeader");
        RemoteViews makeNotificationGroupHeader = recoverBuilder.makeNotificationGroupHeader();
        Trace.endSection();
        if (this.mGroupHeader == null) {
            Trace.beginSection("recreateHeader#apply");
            this.mGroupHeader = makeNotificationGroupHeader.apply(getContext(), this);
            Trace.endSection();
            this.mGroupHeader.findViewById(android.R.id.flagRetrieveInteractiveWindows).setVisibility(0);
            this.mGroupHeader.setOnClickListener(this.mHeaderClickListener);
            NotificationHeaderViewWrapper notificationHeaderViewWrapper = (NotificationHeaderViewWrapper) NotificationViewWrapper.wrap(getContext(), this.mGroupHeader, this.mContainingNotification);
            this.mGroupHeaderWrapper = notificationHeaderViewWrapper;
            notificationHeaderViewWrapper.mRoundnessChangedListener = new NotificationChildrenContainer$$ExternalSyntheticLambda1(this);
            invalidate();
        } else {
            Trace.beginSection("recreateHeader#reapply");
            makeNotificationGroupHeader.reapply(getContext(), this.mGroupHeader);
            Trace.endSection();
        }
        this.mGroupHeaderWrapper.mExpandButton.setExpanded(false);
        this.mGroupHeaderWrapper.onContentUpdated(this.mContainingNotification);
        RemoteViews makeNotificationGroupHeaderExpanded = recoverBuilder.makeNotificationGroupHeaderExpanded();
        if (this.mNotificationHeaderExpanded == null) {
            NotificationHeaderView apply = makeNotificationGroupHeaderExpanded.apply(getContext(), this);
            this.mNotificationHeaderExpanded = apply;
            apply.findViewById(android.R.id.flagRetrieveInteractiveWindows).setVisibility(0);
            this.mNotificationHeaderExpanded.findViewById(android.R.id.inter_word).setVisibility(8);
            this.mNotificationHeaderExpanded.findViewById(16909967).setVisibility(8);
            this.mNotificationHeaderExpanded.setOnClickListener(this.mHeaderClickListener);
            this.mNotificationHeaderWrapperExpanded = new NotificationGroupHeaderViewWrapper(getContext(), this.mNotificationHeaderExpanded, this.mContainingNotification);
            addView((View) this.mNotificationHeaderExpanded, 0);
            invalidate();
        } else {
            makeNotificationGroupHeaderExpanded.reapply(getContext(), this.mNotificationHeaderExpanded);
        }
        ViewGroup viewGroup = (ViewGroup) this.mNotificationHeaderExpanded.findViewById(android.R.id.resolver_list);
        TypedValue typedValue = new TypedValue();
        getResources().getValue(R.dimen.notification_group_expanded_shadow_radius, typedValue, true);
        int color = getResources().getColor(R.color.notification_no_background_header_text_shadow_color);
        if (viewGroup != null) {
            for (int i3 = 0; i3 < viewGroup.getChildCount(); i3++) {
                View childAt = viewGroup.getChildAt(i3);
                if (childAt instanceof TextView) {
                    ((TextView) childAt).setShadowLayer(typedValue.getFloat(), 0.0f, 0.0f, color);
                }
            }
        }
        NotificationExpandButton findViewById = this.mNotificationHeaderExpanded.findViewById(android.R.id.flagRetrieveInteractiveWindows);
        findViewById.setDefaultTextColor(getResources().getColor(R.color.notification_no_background_header_text_color));
        this.mNotificationHeaderWrapperExpanded.mExpandButton.setExpanded(true);
        this.mNotificationHeaderWrapperExpanded.onContentUpdated(this.mContainingNotification);
        ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).updateIconTag(this.mNotificationHeaderExpanded, this.mContainingNotification);
        ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).updateHeader(this.mNotificationHeaderExpanded, this.mContainingNotification, false);
        recreateLowPriorityHeader(recoverBuilder);
        updateHeaderVisibility(false, false);
        updateChildrenAppearance();
        ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).updateIconTag(this.mGroupHeader, this.mContainingNotification);
        ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).updateHeader(this.mGroupHeader, this.mContainingNotification, true);
        this.mGroupIconView = this.mGroupHeader.findViewById(android.R.id.icon);
        Trace.endSection();
    }

    public final void removeNotification(ExpandableNotificationRow expandableNotificationRow) {
        int indexOf = ((ArrayList) this.mAttachedChildren).indexOf(expandableNotificationRow);
        ((ArrayList) this.mAttachedChildren).remove(expandableNotificationRow);
        removeView(expandableNotificationRow);
        final View view = (View) ((ArrayList) this.mDividers).remove(indexOf);
        removeView(view);
        getOverlay().add(view);
        CrossFadeHelper.fadeOut(210L, view, new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer.2
            @Override // java.lang.Runnable
            public final void run() {
                NotificationChildrenContainer.this.getOverlay().remove(view);
            }
        });
        expandableNotificationRow.getClass();
        expandableNotificationRow.setNotificationFaded(false);
        expandableNotificationRow.setUserLocked(false);
        if ((this.mContainingNotification.isGroupExpanded$1() || this.mContainingNotification.isGroupExpansionChanging() || this.mUserLocked) ? false : true) {
            expandableNotificationRow.mSkipRemovalAnim = true;
        }
        NotificationGroupingUtil notificationGroupingUtil = this.mGroupingUtil;
        for (int i = 0; i < notificationGroupingUtil.mProcessors.size(); i++) {
            ((NotificationGroupingUtil.Processor) notificationGroupingUtil.mProcessors.get(i)).apply(expandableNotificationRow, true);
        }
        notificationGroupingUtil.sanitizeTopLineViews(expandableNotificationRow);
        ViewState viewState = new ViewState();
        viewState.initFrom(expandableNotificationRow);
        if (viewState.mScaleX != 1.0f) {
            viewState.setScaleX(1.0f);
            viewState.applyToView(expandableNotificationRow);
            expandableNotificationRow.setScaleX(1.0f);
        }
        NotificationBackgroundView notificationBackgroundView = expandableNotificationRow.mBackgroundNormal;
        notificationBackgroundView.mBottomClipRounded = false;
        notificationBackgroundView.invalidate();
        expandableNotificationRow.setContentClipTopAmount(0);
        if (indexOf == 0 && !this.mExpanderViewStates.isEmpty()) {
            ArrayList arrayList = this.mExpanderViewStates;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                ((ViewState) obj).setAlpha(1.0f);
            }
        }
        ArrayList expandButtons = expandableNotificationRow.getExpandButtons();
        int size2 = expandButtons.size();
        int i3 = 0;
        while (i3 < size2) {
            Object obj2 = expandButtons.get(i3);
            i3++;
            View view2 = (View) obj2;
            view2.setAlpha(1.0f);
            if (view2.getVisibility() == 4) {
                view2.setVisibility(0);
            }
        }
        expandableNotificationRow.setContentAlphaLocked(false);
        expandableNotificationRow.setContentAlpha(1.0f);
        expandableNotificationRow.requestRoundness(0.0f, 0.0f, FROM_PARENT, false);
        applyRoundnessAndInvalidate();
        if (this.mContainingNotification.isInsignificant() && !this.mContainingNotification.isGroupExpanded$1() && ((ArrayList) this.mAttachedChildren).isEmpty()) {
            this.mContainingNotification.setAlpha(0.0f);
        }
    }

    public final void removeTransientView(View view) {
        NotificationChildrenContainerLogger notificationChildrenContainerLogger = this.mLogger;
        if (notificationChildrenContainerLogger != null && (view instanceof ExpandableNotificationRow)) {
            String str = ((ExpandableNotificationRow) view).mLoggingKey;
            String str2 = this.mContainingNotification.mLoggingKey;
            notificationChildrenContainerLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            NotificationChildrenContainerLogger$$ExternalSyntheticLambda0 notificationChildrenContainerLogger$$ExternalSyntheticLambda0 = new NotificationChildrenContainerLogger$$ExternalSyntheticLambda0(0);
            LogBuffer logBuffer = notificationChildrenContainerLogger.notificationRenderBuffer;
            LogMessage obtain = logBuffer.obtain("NotifChildrenContainer", logLevel, notificationChildrenContainerLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = str;
            logMessageImpl.str2 = str2;
            logBuffer.commit(obtain);
        }
        super.removeTransientView(view);
    }

    public final void resetHeaderVisibilityIfNeeded(View view, View view2) {
        if (view == null) {
            return;
        }
        if (view != this.mCurrentHeader && view != view2) {
            getWrapperForView$1(view).setVisible(false);
            view.setVisibility(4);
        }
        if ((view != this.mMinimizedGroupHeader || (!(this.mUserLocked || this.mContainingNotification.isGroupExpansionChanging()) || this.mContainingNotification.isGroupExpanded$1())) && view == view2 && view.getVisibility() != 0) {
            if (view2 == this.mGroupHeader && isUserLocked()) {
                return;
            }
            getWrapperForView$1(view).setVisible(true);
            view.setVisibility(0);
        }
    }

    public final void setChildrenExpanded(boolean z) {
        this.mChildrenExpanded = z;
        NotificationGroupHeaderViewWrapper notificationGroupHeaderViewWrapper = this.mNotificationHeaderWrapperExpanded;
        if (notificationGroupHeaderViewWrapper != null) {
            notificationGroupHeaderViewWrapper.mExpandButton.setExpanded(true);
        }
        NotificationHeaderViewWrapper notificationHeaderViewWrapper = this.mGroupHeaderWrapper;
        if (notificationHeaderViewWrapper != null) {
            notificationHeaderViewWrapper.mExpandButton.setExpanded(false);
        }
        NotificationHeaderViewWrapper notificationHeaderViewWrapper2 = this.mMinimizedGroupHeaderWrapper;
        if (notificationHeaderViewWrapper2 != null) {
            notificationHeaderViewWrapper2.mExpandButton.setExpanded(false);
        }
        int size = ((ArrayList) this.mAttachedChildren).size();
        for (int i = 0; i < size; i++) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) ((ArrayList) this.mAttachedChildren).get(i);
            expandableNotificationRow.mChildrenExpanded = z;
            NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
            if (notificationChildrenContainer != null) {
                notificationChildrenContainer.setChildrenExpanded(z);
            }
            expandableNotificationRow.updateBackgroundForGroupState();
            expandableNotificationRow.updateClickAndFocus();
        }
        updateHeaderTouchability();
    }

    public final void setNotificationFaded(boolean z) {
        this.mContainingNotificationIsFaded = z;
        NotificationHeaderViewWrapper notificationHeaderViewWrapper = this.mGroupHeaderWrapper;
        if (notificationHeaderViewWrapper != null) {
            notificationHeaderViewWrapper.setNotificationFaded(z);
        }
        NotificationHeaderViewWrapper notificationHeaderViewWrapper2 = this.mMinimizedGroupHeaderWrapper;
        if (notificationHeaderViewWrapper2 != null) {
            notificationHeaderViewWrapper2.setNotificationFaded(z);
        }
        NotificationGroupHeaderViewWrapper notificationGroupHeaderViewWrapper = this.mNotificationHeaderWrapperExpanded;
        if (notificationGroupHeaderViewWrapper != null) {
            notificationGroupHeaderViewWrapper.setNotificationFaded(z);
        }
        ArrayList arrayList = (ArrayList) this.mAttachedChildren;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((ExpandableNotificationRow) obj).setNotificationFaded(z);
        }
    }

    public final void setUserLocked(boolean z) {
        this.mUserLocked = z;
        if (!z) {
            updateHeaderVisibility(false, false);
        }
        int size = ((ArrayList) this.mAttachedChildren).size();
        for (int i = 0; i < size; i++) {
            ((ExpandableNotificationRow) ((ArrayList) this.mAttachedChildren).get(i)).setUserLocked(z);
        }
        updateHeaderTouchability();
    }

    public final boolean showingLowPriorityGroupHeader() {
        return this.mIsMinimized && !this.mContainingNotification.isExpanded(false);
    }

    public final void updateChildrenAppearance() {
        this.mGroupingUtil.updateChildrenAppearance();
        if (!this.mExpanderViewStates.isEmpty()) {
            if (this.mChildrenExpanded || this.mUntruncatedChildCount == 1) {
                ArrayList arrayList = this.mExpanderViewStates;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((ViewState) obj).setAlpha(1.0f);
                }
            }
            for (int i2 = 0; i2 < this.mExpanderViewStates.size(); i2++) {
                ((ViewState) this.mExpanderViewStates.get(i2)).applyToView((View) this.mExpanderViews.get(i2));
            }
        }
        if (!this.mChildrenCountViewStates.isEmpty()) {
            ArrayList arrayList2 = this.mChildrenCountViewStates;
            int size2 = arrayList2.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj2 = arrayList2.get(i3);
                i3++;
                ((ViewState) obj2).hidden = true;
            }
            for (int i4 = 0; i4 < this.mChildrenCountViewStates.size(); i4++) {
                ((ViewState) this.mChildrenCountViewStates.get(i4)).applyToView((View) this.mChildrenCountViews.get(i4));
            }
        }
        if (getFirstChild() != null) {
            this.mExpanderViews = getFirstChild().getExpandButtons();
        }
        ArrayList arrayList3 = new ArrayList();
        if (getFirstChild() != null) {
            ArrayList arrayList4 = this.mExpanderViews;
            int size3 = arrayList4.size();
            int i5 = 0;
            while (i5 < size3) {
                Object obj3 = arrayList4.get(i5);
                i5++;
                ViewState viewState = new ViewState();
                viewState.initFrom((View) obj3);
                viewState.hidden = false;
                arrayList3.add(viewState);
            }
        }
        this.mExpanderViewStates = arrayList3;
        if (getFirstChild() != null) {
            this.mChildrenCountViews = getFirstChild().getChildrenCountText();
        }
        ArrayList arrayList5 = new ArrayList();
        if (getFirstChild() != null) {
            ArrayList arrayList6 = this.mChildrenCountViews;
            int size4 = arrayList6.size();
            int i6 = 0;
            while (i6 < size4) {
                Object obj4 = arrayList6.get(i6);
                i6++;
                ViewState viewState2 = new ViewState();
                viewState2.initFrom((View) obj4);
                viewState2.hidden = false;
                viewState2.gone = false;
                arrayList5.add(viewState2);
            }
        }
        this.mChildrenCountViewStates = arrayList5;
    }

    public final void updateChildrenClipping() {
        int i;
        boolean z;
        if (this.mContainingNotification.mChildIsExpanding) {
            return;
        }
        int size = ((ArrayList) this.mAttachedChildren).size();
        int i2 = this.mContainingNotification.mActualHeight - this.mClipBottomAmount;
        for (int i3 = 0; i3 < size; i3++) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) ((ArrayList) this.mAttachedChildren).get(i3);
            if (expandableNotificationRow.getVisibility() != 8) {
                float translationY = expandableNotificationRow.getTranslationY();
                float f = expandableNotificationRow.mActualHeight + translationY;
                float f2 = i2;
                if (translationY > f2) {
                    i = 0;
                    z = false;
                } else {
                    i = f > f2 ? (int) (f - f2) : 0;
                    z = true;
                }
                if (z != (expandableNotificationRow.getVisibility() == 0)) {
                    expandableNotificationRow.setVisibility(z ? 0 : 4);
                }
                expandableNotificationRow.setClipBottomAmount(i);
            }
        }
    }

    public final void updateGroupOverflow() {
        if (this.mShowGroupCountInExpander) {
            NotificationHeaderViewWrapper notificationHeaderViewWrapper = this.mGroupHeaderWrapper;
            View expandButton = notificationHeaderViewWrapper == null ? null : notificationHeaderViewWrapper.getExpandButton();
            if (expandButton instanceof NotificationExpandButton) {
                ((NotificationExpandButton) expandButton).setNumber(this.mUntruncatedChildCount);
            }
            NotificationHeaderViewWrapper notificationHeaderViewWrapper2 = this.mMinimizedGroupHeaderWrapper;
            View expandButton2 = notificationHeaderViewWrapper2 != null ? notificationHeaderViewWrapper2.getExpandButton() : null;
            if (expandButton2 instanceof NotificationExpandButton) {
                ((NotificationExpandButton) expandButton2).setNumber(this.mUntruncatedChildCount);
            }
            resetHeaderVisibilityIfNeeded(this.mGroupHeader, this.mCurrentHeader);
            resetHeaderVisibilityIfNeeded(this.mMinimizedGroupHeader, this.mCurrentHeader);
            return;
        }
        int maxAllowedVisibleChildren = getMaxAllowedVisibleChildren(true);
        int i = this.mUntruncatedChildCount;
        if (i <= maxAllowedVisibleChildren) {
            View view = this.mOverflowNumber;
            if (view != null) {
                removeView(view);
                if (isShown() && isAttachedToWindow()) {
                    final View view2 = this.mOverflowNumber;
                    addTransientView(view2, getTransientViewCount());
                    CrossFadeHelper.fadeOut(210L, view2, new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer.3
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationChildrenContainer.this.removeTransientView(view2);
                        }
                    });
                }
                this.mOverflowNumber = null;
                this.mGroupOverFlowState = null;
                return;
            }
            return;
        }
        int i2 = i - maxAllowedVisibleChildren;
        HybridGroupManager hybridGroupManager = this.mHybridGroupManager;
        TextView textView = this.mOverflowNumber;
        if (textView == null) {
            textView = (TextView) ((LayoutInflater) hybridGroupManager.mContext.getSystemService(LayoutInflater.class)).inflate(R.layout.hybrid_overflow_number, (ViewGroup) this, false);
            addView(textView);
            textView.setTextColor(hybridGroupManager.mOverflowNumberColor);
        }
        String string = hybridGroupManager.mContext.getResources().getString(R.string.notification_group_overflow_indicator, Integer.valueOf(i2));
        if (!string.equals(textView.getText())) {
            textView.setText(string);
        }
        textView.setContentDescription(PluralMessageFormaterKt.icuMessageFormat(hybridGroupManager.mContext.getResources(), R.string.notification_group_overflow_description, i2));
        textView.setTextSize(0, hybridGroupManager.mOverflowNumberSize);
        textView.setPaddingRelative(textView.getPaddingStart(), textView.getPaddingTop(), hybridGroupManager.mOverflowNumberPadding, textView.getPaddingBottom());
        textView.setTextColor(hybridGroupManager.mOverflowNumberColor);
        this.mOverflowNumber = textView;
        if (this.mGroupOverFlowState == null) {
            this.mGroupOverFlowState = new ViewState();
            this.mNeverAppliedGroupState = true;
        }
    }

    public final void updateHeaderForExpansion(boolean z) {
        NotificationHeaderView notificationHeaderView = this.mNotificationHeaderExpanded;
        if (notificationHeaderView != null) {
            if (z) {
                ColorDrawable colorDrawable = new ColorDrawable();
                NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class);
                int notificationBgColor = notificationColorPicker.getNotificationBgColor();
                colorDrawable.setColor(Color.argb(notificationColorPicker.mCustomedAlpha, Color.red(notificationBgColor), Color.green(notificationBgColor), Color.blue(notificationBgColor)));
                this.mNotificationHeaderExpanded.setHeaderBackgroundDrawable((Drawable) null);
                this.mNotificationHeaderExpanded.findViewById(android.R.id.inter_word).setVisibility(8);
                this.mNotificationHeaderExpanded.findViewById(16909967).setVisibility(8);
            } else {
                notificationHeaderView.setHeaderBackgroundDrawable((Drawable) null);
            }
        }
        updateHeaderVisibility(!isUserLocked(), false);
    }

    public final void updateHeaderTouchability() {
        NotificationHeaderView notificationHeaderView = this.mNotificationHeaderExpanded;
        boolean z = true;
        if (notificationHeaderView != null) {
            if (!this.mChildrenExpanded && !this.mUserLocked) {
                z = false;
            }
            notificationHeaderView.setAcceptAllTouches(z);
            return;
        }
        NotificationHeaderView notificationHeaderView2 = this.mGroupHeader;
        if (notificationHeaderView2 != null) {
            if (!this.mChildrenExpanded && !this.mUserLocked) {
                z = false;
            }
            notificationHeaderView2.setAcceptAllTouches(z);
        }
    }

    public final void updateHeaderVisibility(boolean z, boolean z2) {
        NotificationHeaderView notificationHeaderView = this.mCurrentHeader;
        NotificationHeaderView calculateDesiredHeader = calculateDesiredHeader();
        if (notificationHeaderView != calculateDesiredHeader || z2) {
            int i = AsyncGroupHeaderViewInflation.$r8$clinit;
            if (z) {
                if (calculateDesiredHeader == null || notificationHeaderView == null) {
                    z = false;
                } else {
                    notificationHeaderView.setVisibility(0);
                    calculateDesiredHeader.setVisibility(0);
                    NotificationViewWrapper wrapperForView$1 = getWrapperForView$1(calculateDesiredHeader);
                    NotificationViewWrapper wrapperForView$12 = getWrapperForView$1(notificationHeaderView);
                    wrapperForView$1.transformFrom(wrapperForView$12);
                    wrapperForView$12.transformTo(wrapperForView$1, new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationChildrenContainer notificationChildrenContainer = NotificationChildrenContainer.this;
                            SourceType$Companion$from$1 sourceType$Companion$from$1 = NotificationChildrenContainer.FROM_PARENT;
                            notificationChildrenContainer.updateHeaderVisibility(false, false);
                        }
                    });
                }
            }
            if (!z) {
                if (calculateDesiredHeader != null) {
                    getWrapperForView$1(calculateDesiredHeader).setVisible(true);
                    calculateDesiredHeader.setVisibility(0);
                }
                if (notificationHeaderView != null) {
                    NotificationViewWrapper wrapperForView$13 = getWrapperForView$1(notificationHeaderView);
                    if (wrapperForView$13 != null) {
                        wrapperForView$13.setVisible(false);
                    }
                    notificationHeaderView.setVisibility(4);
                }
            }
            resetHeaderVisibilityIfNeeded(this.mGroupHeader, calculateDesiredHeader);
            resetHeaderVisibilityIfNeeded(this.mNotificationHeaderExpanded, calculateDesiredHeader);
            resetHeaderVisibilityIfNeeded(this.mMinimizedGroupHeader, calculateDesiredHeader);
            this.mCurrentHeader = calculateDesiredHeader;
        }
    }

    public NotificationChildrenContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public int getMaxAllowedVisibleChildren(boolean z) {
        if (!z && (this.mChildrenExpanded || this.mContainingNotification.mUserLocked)) {
            return this.mContainingNotification.isInsignificant() ? 50 : 8;
        }
        ExpandableNotificationRow expandableNotificationRow = this.mContainingNotification;
        if ((expandableNotificationRow.mOnKeyguard || !expandableNotificationRow.isExpanded(false)) && this.mContainingNotification.isHeadsUpState()) {
            this.mContainingNotification.canShowHeadsUp$1();
        }
        return 1;
    }

    public NotificationChildrenContainer(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public NotificationChildrenContainer(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mDividers = new ArrayList();
        this.mAttachedChildren = new ArrayList();
        this.mExpanderViewStates = new ArrayList();
        this.mExpanderViews = new ArrayList();
        this.mChildrenCountViews = new ArrayList();
        this.mChildrenCountViewStates = new ArrayList();
        this.mChildClipPath = null;
        new Path();
        this.mHeaderVisibleAmount = 1.0f;
        this.mContainingNotificationIsFaded = false;
        this.mHybridGroupManager = new HybridGroupManager(getContext());
        this.mRoundableState = new RoundableState(this, this, 0.0f);
        this.mReduceTransparencyAndBlurOn = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isReduceTransparencyEnabled();
        initDimens$2();
        setClipChildren(false);
    }
}
