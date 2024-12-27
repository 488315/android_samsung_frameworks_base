package com.android.systemui.statusbar.notification.row.wrapper;

import android.app.Notification;
import android.content.Context;
import android.util.ArraySet;
import android.util.Pools;
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
import com.android.systemui.statusbar.notification.ImageTransformState;
import com.android.systemui.statusbar.notification.Roundable;
import com.android.systemui.statusbar.notification.RoundableState;
import com.android.systemui.statusbar.notification.TransformState;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer$$ExternalSyntheticLambda1;
import java.util.Stack;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class NotificationHeaderViewWrapper extends NotificationViewWrapper implements Roundable {
    public static final Interpolator LOW_PRIORITY_HEADER_CLOSE = new PathInterpolator(0.4f, 0.0f, 0.7f, 1.0f);
    public View mAltExpandTarget;
    public TextView mAppNameText;
    public View mAudiblyAlertedIcon;
    public NotificationExpandButton mExpandButton;
    public ImageView mExpandButtonIcon;
    public TextView mExpandButtonNumber;
    public View mFeedbackIcon;
    public TextView mHeaderText;
    public CachingIconView mIcon;
    public View mIconContainer;
    public boolean mIsLowPriority;
    public NotificationHeaderView mNotificationHeader;
    public NotificationTopLineView mNotificationTopLine;
    public final RoundableState mRoundableState;
    public NotificationChildrenContainer$$ExternalSyntheticLambda1 mRoundnessChangedListener;
    public boolean mTransformLowPriorityTitle;
    public final ViewTransformationHelper mTransformationHelper;
    public ImageView mWorkProfileImage;

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
        viewTransformationHelper.setCustomTransformation(new CustomInterpolatorTransformation(6) { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper.4
            @Override // com.android.systemui.statusbar.notification.CustomInterpolatorTransformation, com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public final boolean transformFrom(TransformState transformState, TransformableView transformableView, float f) {
                TransformState currentState = transformableView.getCurrentState(6);
                if (currentState == null) {
                    return false;
                }
                transformState.ensureVisible();
                View view2 = transformState.mTransformedView;
                float rotation = view2.getRotation();
                view2.setRotation(0.0f);
                transformState.transformViewFullyFrom(currentState, f);
                view2.setRotation(rotation);
                view2.setPivotX(view2.getWidth() / 2);
                view2.setPivotY(view2.getHeight() / 2);
                NotificationHeaderViewWrapper notificationHeaderViewWrapper = NotificationHeaderViewWrapper.this;
                ExpandableNotificationRow expandableNotificationRow2 = notificationHeaderViewWrapper.mRow;
                NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow2.mChildrenContainer;
                if (notificationChildrenContainer == null || !notificationChildrenContainer.mWasLowPriorityShowing) {
                    boolean isGroupExpanded = expandableNotificationRow2.mIsSummaryWithChildren ? expandableNotificationRow2.isGroupExpanded() : expandableNotificationRow2.isExpanded(false);
                    if (notificationHeaderViewWrapper.mRow.mUserLocked) {
                        if (isGroupExpanded) {
                            RotationHelper.counterClockWise(1.0f, view2);
                        } else {
                            RotationHelper.counterClockWise(f, view2);
                        }
                    } else if (isGroupExpanded) {
                        RotationHelper.counterClockWise(f, view2);
                    } else {
                        RotationHelper.clockWise(f, view2);
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
        ExpandableNotificationRow$$ExternalSyntheticLambda4 expandableNotificationRow$$ExternalSyntheticLambda4 = expandableNotificationRow.mOnFeedbackClickListener;
        NotificationTopLineView notificationTopLineView = this.mNotificationTopLine;
        if (notificationTopLineView != null) {
            notificationTopLineView.setFeedbackOnClickListener(expandableNotificationRow$$ExternalSyntheticLambda4);
        }
        View view2 = this.mFeedbackIcon;
        if (view2 != null) {
            view2.setOnClickListener(expandableNotificationRow$$ExternalSyntheticLambda4);
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
        NotificationChildrenContainer$$ExternalSyntheticLambda1 notificationChildrenContainer$$ExternalSyntheticLambda1 = this.mRoundnessChangedListener;
        if (notificationChildrenContainer$$ExternalSyntheticLambda1 != null) {
            notificationChildrenContainer$$ExternalSyntheticLambda1.f$0.invalidate();
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
        this.mIsLowPriority = expandableNotificationRow.mEntry.mRanking.isAmbient();
        this.mTransformLowPriorityTitle = (expandableNotificationRow.isChildInGroup() || expandableNotificationRow.mIsSummaryWithChildren) ? false : true;
        ViewTransformationHelper viewTransformationHelper = this.mTransformationHelper;
        viewTransformationHelper.getClass();
        ArraySet arraySet = new ArraySet(viewTransformationHelper.mTransformedViews.values());
        resolveHeaderViews();
        updateTransformedTypes();
        viewTransformationHelper.addRemainingTransformTypes(this.mView);
        Stack stack = new Stack();
        stack.push(this.mView);
        while (!stack.isEmpty()) {
            View view = (View) stack.pop();
            if ((view instanceof ImageView) && view.getId() != 16908955) {
                ((ImageView) view).setCropToPadding(true);
            } else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    stack.push(viewGroup.getChildAt(i));
                }
            }
        }
        Notification notification2 = expandableNotificationRow.mEntry.mSbn.getNotification();
        if (this.mIcon != null) {
            if (notification2.shouldUseAppIcon()) {
                CachingIconView cachingIconView = this.mIcon;
                Pools.SimplePool simplePool = ImageTransformState.sInstancePool;
                cachingIconView.setTag(R.id.image_icon_tag, notification2.getAppIcon());
            } else {
                CachingIconView cachingIconView2 = this.mIcon;
                Pools.SimplePool simplePool2 = ImageTransformState.sInstancePool;
                cachingIconView2.setTag(R.id.image_icon_tag, notification2.getSmallIcon());
            }
        }
        ArraySet arraySet2 = new ArraySet(viewTransformationHelper.mTransformedViews.values());
        for (int i2 = 0; i2 < arraySet.size(); i2++) {
            View view2 = (View) arraySet.valueAt(i2);
            if (!arraySet2.contains(view2)) {
                TransformState createFrom = TransformState.createFrom(view2, viewTransformationHelper);
                createFrom.setVisible(true, true);
                createFrom.recycle();
            }
        }
    }

    public void resolveHeaderViews() {
        this.mIcon = this.mView.findViewById(android.R.id.icon);
        this.mHeaderText = (TextView) this.mView.findViewById(android.R.id.inbox_text0);
        this.mAppNameText = (TextView) this.mView.findViewById(android.R.id.authtoken_type);
        this.mExpandButton = this.mView.findViewById(android.R.id.fill);
        this.mAltExpandTarget = this.mView.findViewById(android.R.id.alwaysUse);
        this.mIconContainer = this.mView.findViewById(android.R.id.date_picker_day_picker);
        this.mWorkProfileImage = (ImageView) this.mView.findViewById(android.R.id.secondary);
        this.mNotificationHeader = this.mView.findViewById(android.R.id.placeholder);
        this.mNotificationTopLine = this.mView.findViewById(android.R.id.progressContainer);
        this.mAudiblyAlertedIcon = this.mView.findViewById(android.R.id.alignMargins);
        this.mFeedbackIcon = this.mView.findViewById(android.R.id.flagDefault);
        this.mExpandButtonIcon = (ImageView) this.mView.findViewById(android.R.id.fill_vertical);
        this.mExpandButtonNumber = (TextView) this.mView.findViewById(android.R.id.find);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public void setFeedbackIcon(FeedbackIcon feedbackIcon) {
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
        DateTimeView findViewById = this.mView.findViewById(16909905);
        if (findViewById instanceof DateTimeView) {
            findViewById.setTime(j);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public void setRecentlyAudiblyAlerted(boolean z) {
        View view = this.mAudiblyAlertedIcon;
        if (view != null) {
            view.setVisibility(8);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper, com.android.systemui.statusbar.TransformableView
    public void setVisible(boolean z) {
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
