package com.android.systemui.statusbar.notification.row.wrapper;

import android.content.Context;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.view.NotificationHeaderView;
import android.view.NotificationTopLineView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.DateTimeView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.app.animation.Interpolators;
import com.android.internal.widget.CachingIconView;
import com.android.internal.widget.NotificationExpandButton;
import com.android.systemui.R;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.RotationHelper;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.ViewTransformationHelper;
import com.android.systemui.statusbar.notification.CustomInterpolatorTransformation;
import com.android.systemui.statusbar.notification.FeedbackIcon;
import com.android.systemui.statusbar.notification.Roundable;
import com.android.systemui.statusbar.notification.RoundableState;
import com.android.systemui.statusbar.notification.TransformState;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda3;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer$$ExternalSyntheticLambda1;
import java.util.Stack;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class NotificationHeaderViewWrapper extends NotificationViewWrapper implements Roundable {
    public static final Interpolator LOW_PRIORITY_HEADER_CLOSE = new PathInterpolator(0.4f, 0.0f, 0.7f, 1.0f);
    public View mAltExpandTarget;
    public View mAudiblyAlertedIcon;
    public NotificationExpandButton mExpandButton;
    public ImageView mExpandButtonIcon;
    public TextView mExpandButtonNumber;
    public View mFeedbackIcon;
    public ImageView mGroupIconShadow;
    public TextView mHeaderText;
    public CachingIconView mIcon;
    public View mIconContainer;
    public boolean mIsLowPriority;
    public NotificationHeaderView mNotificationHeader;
    public NotificationTopLineView mNotificationTopLine;
    public final RoundableState mRoundableState;
    public RoundnessChangedListener mRoundnessChangedListener;
    public boolean mTransformLowPriorityTitle;
    public final ViewTransformationHelper mTransformationHelper;
    public ImageView mWorkProfileImage;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface RoundnessChangedListener {
    }

    public NotificationHeaderViewWrapper(Context context, final View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
        this.mRoundableState = new RoundableState(this.mView, this, context.getResources().getDimension(R.dimen.notification_corner_radius));
        ViewTransformationHelper viewTransformationHelper = new ViewTransformationHelper();
        this.mTransformationHelper = viewTransformationHelper;
        viewTransformationHelper.setCustomTransformation(new CustomInterpolatorTransformation(1) { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper.1
            @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public final Interpolator getCustomInterpolator(int i, boolean z) {
                boolean z2 = NotificationHeaderViewWrapper.this.mView instanceof NotificationHeaderView;
                if (i == 16) {
                    return ((!z2 || z) && (z2 || !z)) ? NotificationHeaderViewWrapper.LOW_PRIORITY_HEADER_CLOSE : Interpolators.LINEAR_OUT_SLOW_IN;
                }
                return null;
            }

            @Override // com.android.systemui.statusbar.notification.CustomInterpolatorTransformation
            public final boolean hasCustomTransformation() {
                NotificationHeaderViewWrapper notificationHeaderViewWrapper = NotificationHeaderViewWrapper.this;
                return notificationHeaderViewWrapper.mIsLowPriority && notificationHeaderViewWrapper.mTransformLowPriorityTitle;
            }
        }, 1);
        viewTransformationHelper.setCustomTransformation(new CustomInterpolatorTransformation(this, 7) { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper.2
            @Override // com.android.systemui.statusbar.notification.CustomInterpolatorTransformation, com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public final boolean transformFrom(TransformState transformState, TransformableView transformableView, float f) {
                TransformState currentState = transformableView.getCurrentState(7);
                if (currentState == null) {
                    return false;
                }
                CrossFadeHelper.fadeIn(transformState.mTransformedView, f, true);
                transformState.transformViewFrom(currentState, 16, this, f);
                currentState.recycle();
                return true;
            }

            @Override // com.android.systemui.statusbar.notification.CustomInterpolatorTransformation, com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public final boolean transformTo(TransformState transformState, TransformableView transformableView, float f) {
                TransformState currentState = transformableView.getCurrentState(7);
                if (currentState == null) {
                    return false;
                }
                CrossFadeHelper.fadeOut(transformState.mTransformedView, f, true);
                transformState.transformViewTo(currentState, 16, this, f);
                currentState.recycle();
                return true;
            }
        }, 7);
        viewTransformationHelper.setCustomTransformation(new CustomInterpolatorTransformation(this, 0) { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper.3
            @Override // com.android.systemui.statusbar.notification.CustomInterpolatorTransformation, com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public final boolean transformFrom(TransformState transformState, TransformableView transformableView, float f) {
                TransformState currentState = transformableView.getCurrentState(0);
                if (currentState == null) {
                    return false;
                }
                transformState.ensureVisible();
                transformState.transformViewFrom(currentState, 17, this, f);
                currentState.recycle();
                return true;
            }

            @Override // com.android.systemui.statusbar.notification.CustomInterpolatorTransformation, com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public final boolean transformTo(TransformState transformState, TransformableView transformableView, float f) {
                TransformState currentState = transformableView.getCurrentState(0);
                if (currentState == null) {
                    view.setVisibility(f != 0.0f ? 4 : 0);
                    return true;
                }
                transformState.transformViewTo(currentState, 17, this, f);
                currentState.recycle();
                return false;
            }
        }, 0);
        viewTransformationHelper.setCustomTransformation(new CustomInterpolatorTransformation(this, 8) { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper.4
            @Override // com.android.systemui.statusbar.notification.CustomInterpolatorTransformation, com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public final boolean transformFrom(TransformState transformState, TransformableView transformableView, float f) {
                TransformState currentState = transformableView.getCurrentState(8);
                if (currentState == null) {
                    return false;
                }
                transformState.ensureVisible();
                transformState.transformViewFrom(currentState, 17, this, f);
                currentState.recycle();
                return true;
            }

            @Override // com.android.systemui.statusbar.notification.CustomInterpolatorTransformation, com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public final boolean transformTo(TransformState transformState, TransformableView transformableView, float f) {
                TransformState currentState = transformableView.getCurrentState(8);
                if (currentState == null) {
                    view.setVisibility(f != 0.0f ? 4 : 0);
                    return true;
                }
                transformState.transformViewTo(currentState, 17, this, f);
                currentState.recycle();
                return false;
            }
        }, 8);
        viewTransformationHelper.setCustomTransformation(new CustomInterpolatorTransformation(6) { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper.5
            @Override // com.android.systemui.statusbar.notification.CustomInterpolatorTransformation, com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public final boolean transformFrom(TransformState transformState, TransformableView transformableView, float f) {
                TransformState currentState = transformableView.getCurrentState(6);
                if (currentState == null) {
                    return false;
                }
                transformState.ensureVisible();
                View view2 = transformState.mTransformedView;
                view2.setPivotX(view2.getWidth() / 2);
                view2.setPivotY(view2.getHeight() / 2);
                NotificationHeaderViewWrapper notificationHeaderViewWrapper = NotificationHeaderViewWrapper.this;
                NotificationChildrenContainer notificationChildrenContainer = notificationHeaderViewWrapper.mRow.mChildrenContainer;
                if (!(notificationChildrenContainer != null && (notificationChildrenContainer.mWasLowPriorityShowing || notificationChildrenContainer.showingAsLowPriority()))) {
                    ExpandableNotificationRow expandableNotificationRow2 = notificationHeaderViewWrapper.mRow;
                    boolean isGroupExpanded = expandableNotificationRow2.mIsSummaryWithChildren ? expandableNotificationRow2.isGroupExpanded() : expandableNotificationRow2.isExpanded(false);
                    if (expandableNotificationRow2.mUserLocked) {
                        if (isGroupExpanded) {
                            RotationHelper.counterClockWise(view2, 1.0f);
                        } else {
                            RotationHelper.counterClockWise(view2, f);
                        }
                    } else if (isGroupExpanded) {
                        RotationHelper.counterClockWise(view2, f);
                    } else {
                        Interpolator interpolator = RotationHelper.ROTATION;
                        view2.animate().cancel();
                        view2.setRotation(Math.min((((PathInterpolator) RotationHelper.ROTATION).getInterpolation(Math.min(f / 0.5833333f, 1.0f)) * 180.0f) + 180.0f, 360.0f));
                    }
                }
                currentState.recycle();
                return true;
            }

            @Override // com.android.systemui.statusbar.notification.CustomInterpolatorTransformation, com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public final boolean transformTo(TransformState transformState, TransformableView transformableView, float f) {
                TransformState currentState = transformableView.getCurrentState(6);
                if (currentState == null) {
                    return true;
                }
                transformState.setVisible(false, false);
                currentState.recycle();
                return false;
            }
        }, 6);
        resolveHeaderViews();
        ExpandableNotificationRow$$ExternalSyntheticLambda3 expandableNotificationRow$$ExternalSyntheticLambda3 = expandableNotificationRow.mOnFeedbackClickListener;
        NotificationTopLineView notificationTopLineView = this.mNotificationTopLine;
        if (notificationTopLineView != null) {
            notificationTopLineView.setFeedbackOnClickListener(expandableNotificationRow$$ExternalSyntheticLambda3);
        }
        View view2 = this.mFeedbackIcon;
        if (view2 != null) {
            view2.setOnClickListener(expandableNotificationRow$$ExternalSyntheticLambda3);
        }
    }

    public final void addTransformedViews(View... viewArr) {
        for (View view : viewArr) {
            if (view != null) {
                this.mTransformationHelper.addTransformedView(view);
            }
        }
    }

    public final void addViewsTransformingToSimilar(View... viewArr) {
        for (View view : viewArr) {
            if (view != null) {
                this.mTransformationHelper.addViewTransformingToSimilar(view);
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.Roundable
    public final void applyRoundnessAndInvalidate() {
        RoundnessChangedListener roundnessChangedListener = this.mRoundnessChangedListener;
        if (roundnessChangedListener != null) {
            ((NotificationChildrenContainer$$ExternalSyntheticLambda1) roundnessChangedListener).f$0.invalidate();
        }
        super.applyRoundnessAndInvalidate();
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper, com.android.systemui.statusbar.TransformableView
    public final TransformState getCurrentState(int i) {
        return this.mTransformationHelper.getCurrentState(i);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final View getExpandButton() {
        return this.mExpandButton;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final CachingIconView getIcon() {
        return this.mIcon;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final NotificationHeaderView getNotificationHeader() {
        return this.mNotificationHeader;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final int getOriginalIconColor() {
        CachingIconView cachingIconView = this.mIcon;
        if (cachingIconView != null) {
            return cachingIconView.getOriginalIconColor();
        }
        return 0;
    }

    @Override // com.android.systemui.statusbar.notification.Roundable
    public final RoundableState getRoundableState() {
        return this.mRoundableState;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public View getShelfTransformationTarget() {
        return this.mIcon;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        View view;
        int id;
        this.mIsLowPriority = expandableNotificationRow.mEntry.isAmbient();
        this.mTransformLowPriorityTitle = (expandableNotificationRow.isChildInGroup() || expandableNotificationRow.mIsSummaryWithChildren) ? false : true;
        ViewTransformationHelper viewTransformationHelper = this.mTransformationHelper;
        viewTransformationHelper.getClass();
        ArraySet arraySet = new ArraySet(viewTransformationHelper.mTransformedViews.values());
        resolveHeaderViews();
        updateTransformedTypes();
        ArrayMap arrayMap = viewTransformationHelper.mTransformedViews;
        int size = arrayMap.size();
        int i = 0;
        while (true) {
            view = this.mView;
            if (i >= size) {
                break;
            }
            Object valueAt = arrayMap.valueAt(i);
            while (true) {
                View view2 = (View) valueAt;
                if (view2 != view.getParent()) {
                    view2.setTag(R.id.contains_transformed_view, Boolean.TRUE);
                    valueAt = view2.getParent();
                }
            }
            i++;
        }
        Stack stack = new Stack();
        stack.push(view);
        while (!stack.isEmpty()) {
            View view3 = (View) stack.pop();
            if (((Boolean) view3.getTag(R.id.contains_transformed_view)) != null || (id = view3.getId()) == -1) {
                view3.setTag(R.id.contains_transformed_view, null);
                if ((view3 instanceof ViewGroup) && !arrayMap.containsValue(view3)) {
                    ViewGroup viewGroup = (ViewGroup) view3;
                    for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                        stack.push(viewGroup.getChildAt(i2));
                    }
                }
            } else {
                viewTransformationHelper.addTransformedView(view3, id);
            }
        }
        Stack stack2 = new Stack();
        stack2.push(view);
        while (!stack2.isEmpty()) {
            View view4 = (View) stack2.pop();
            if ((view4 instanceof ImageView) && view4.getId() != 16908948) {
                ((ImageView) view4).setCropToPadding(true);
            } else if (view4 instanceof ViewGroup) {
                ViewGroup viewGroup2 = (ViewGroup) view4;
                for (int i3 = 0; i3 < viewGroup2.getChildCount(); i3++) {
                    stack2.push(viewGroup2.getChildAt(i3));
                }
            }
        }
        ArraySet arraySet2 = new ArraySet(arrayMap.values());
        for (int i4 = 0; i4 < arraySet.size(); i4++) {
            View view5 = (View) arraySet.valueAt(i4);
            if (!arraySet2.contains(view5)) {
                TransformState createFrom = TransformState.createFrom(view5, viewTransformationHelper);
                createFrom.setVisible(true, true);
                createFrom.recycle();
            }
        }
    }

    public final void resolveHeaderViews() {
        View view = this.mView;
        this.mIcon = view.findViewById(android.R.id.icon);
        this.mHeaderText = (TextView) view.findViewById(android.R.id.image);
        this.mExpandButton = view.findViewById(android.R.id.feedbackAudible);
        this.mAltExpandTarget = view.findViewById(android.R.id.alwaysScroll);
        this.mIconContainer = view.findViewById(android.R.id.current_scene);
        this.mWorkProfileImage = (ImageView) view.findViewById(android.R.id.search_go_btn);
        this.mNotificationHeader = view.findViewById(android.R.id.pin_error_message);
        this.mNotificationTopLine = view.findViewById(android.R.id.productivity);
        this.mAudiblyAlertedIcon = view.findViewById(android.R.id.alignBounds);
        this.mFeedbackIcon = view.findViewById(android.R.id.firstStrongLtr);
        this.mExpandButtonIcon = (ImageView) view.findViewById(android.R.id.feedbackVisual);
        this.mExpandButtonNumber = (TextView) view.findViewById(android.R.id.ffwd);
        NotificationHeaderView notificationHeaderView = this.mNotificationHeader;
        if (notificationHeaderView != null) {
            this.mGroupIconShadow = (ImageView) notificationHeaderView.findViewById(android.R.id.horizontal);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void setFeedbackIcon(FeedbackIcon feedbackIcon) {
        View view = this.mFeedbackIcon;
        if (view != null) {
            view.setVisibility(feedbackIcon != null ? 0 : 8);
            if (feedbackIcon != null) {
                View view2 = this.mFeedbackIcon;
                if (view2 instanceof ImageButton) {
                    ((ImageButton) view2).setImageResource(feedbackIcon.iconRes);
                }
                this.mFeedbackIcon.setContentDescription(this.mView.getContext().getString(feedbackIcon.contentDescRes));
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void setIsChildInGroup(boolean z) {
        this.mTransformLowPriorityTitle = !z;
    }

    public final void setNotificationWhen(long j) {
        NotificationHeaderView notificationHeaderView = this.mNotificationHeader;
        if (notificationHeaderView == null) {
            return;
        }
        DateTimeView findViewById = notificationHeaderView.findViewById(16909891);
        if (findViewById instanceof DateTimeView) {
            findViewById.setTime(j);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void setRecentlyAudiblyAlerted(boolean z) {
        View view = this.mAudiblyAlertedIcon;
        if (view != null) {
            view.setVisibility(8);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper, com.android.systemui.statusbar.TransformableView
    public final void setVisible(boolean z) {
        super.setVisible(z);
        this.mTransformationHelper.setVisible(z);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper, com.android.systemui.statusbar.TransformableView
    public final void transformFrom(TransformableView transformableView) {
        this.mTransformationHelper.transformFrom(transformableView);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper, com.android.systemui.statusbar.TransformableView
    public final void transformTo(TransformableView transformableView, Runnable runnable) {
        this.mTransformationHelper.transformTo(transformableView, runnable);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void updateContentDescription() {
        this.mExpandButton.updateContentDescription();
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public void updateExpandability(boolean z, View.OnClickListener onClickListener, boolean z2) {
        this.mExpandButton.setVisibility(z ? 0 : 8);
        this.mExpandButton.setOnClickListener(z ? onClickListener : null);
        View view = this.mAltExpandTarget;
        if (view != null) {
            view.setOnClickListener(z ? onClickListener : null);
        }
        View view2 = this.mIconContainer;
        if (view2 != null) {
            view2.setOnClickListener(z ? onClickListener : null);
        }
        NotificationHeaderView notificationHeaderView = this.mNotificationHeader;
        if (notificationHeaderView != null) {
            if (!z) {
                onClickListener = null;
            }
            notificationHeaderView.setOnClickListener(onClickListener);
        }
        if (z2) {
            this.mExpandButton.getParent().requestLayout();
        }
    }

    public void updateTransformedTypes() {
        TextView textView;
        ViewTransformationHelper viewTransformationHelper = this.mTransformationHelper;
        viewTransformationHelper.mTransformedViews.clear();
        viewTransformationHelper.mKeysTransformingToSimilar.clear();
        CachingIconView cachingIconView = this.mIcon;
        if (cachingIconView != null) {
            viewTransformationHelper.addTransformedView(cachingIconView, 0);
        }
        ImageView imageView = this.mGroupIconShadow;
        if (imageView != null) {
            viewTransformationHelper.addTransformedView(imageView, 8);
        }
        viewTransformationHelper.addTransformedView(this.mExpandButtonIcon, 6);
        if (this.mIsLowPriority && (textView = this.mHeaderText) != null) {
            viewTransformationHelper.addTransformedView(textView, 1);
        }
        viewTransformationHelper.addTransformedView(this.mExpandButtonNumber, 7);
        addViewsTransformingToSimilar(this.mWorkProfileImage, this.mAudiblyAlertedIcon, this.mFeedbackIcon);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper, com.android.systemui.statusbar.TransformableView
    public final void transformFrom(float f, TransformableView transformableView) {
        this.mTransformationHelper.transformFrom(f, transformableView);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper, com.android.systemui.statusbar.TransformableView
    public final void transformTo(float f, TransformableView transformableView) {
        this.mTransformationHelper.transformTo(f, transformableView);
    }
}
