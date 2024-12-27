package com.android.systemui.statusbar.notification.row.wrapper;

import android.content.Context;
import android.service.notification.StatusBarNotification;
import android.util.ArraySet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.statusbar.RotationHelper;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.ViewTransformationHelper;
import com.android.systemui.statusbar.notification.CustomInterpolatorTransformation;
import com.android.systemui.statusbar.notification.Roundable;
import com.android.systemui.statusbar.notification.RoundableState;
import com.android.systemui.statusbar.notification.TransformState;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class NotificationOngoingViewWrapper extends NotificationViewWrapper implements Roundable {
    public TextView mChronometer;
    public final Context mContext;
    public OngoingActivityData mData;
    public ImageView mExpandButton;
    public ImageView mLeftIcon;
    public TextView mPrimary;
    public final RoundableState mRoundableState;
    public TextView mSecondary;
    public TextView mTime;
    public final ViewTransformationHelper mTransformationHelper;

    public NotificationOngoingViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
        this.mContext = context;
        ViewTransformationHelper viewTransformationHelper = new ViewTransformationHelper();
        this.mTransformationHelper = viewTransformationHelper;
        this.mRoundableState = new RoundableState(this.mView, this, context.getResources().getDimension(R.dimen.notification_corner_radius));
        viewTransformationHelper.setCustomTransformation(new CustomInterpolatorTransformation(6) { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationOngoingViewWrapper.1
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
                NotificationOngoingViewWrapper notificationOngoingViewWrapper = NotificationOngoingViewWrapper.this;
                ExpandableNotificationRow expandableNotificationRow2 = notificationOngoingViewWrapper.mRow;
                NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow2.mChildrenContainer;
                if (notificationChildrenContainer == null || !notificationChildrenContainer.mWasLowPriorityShowing) {
                    boolean isGroupExpanded = expandableNotificationRow2.mIsSummaryWithChildren ? expandableNotificationRow2.isGroupExpanded() : expandableNotificationRow2.isExpanded(false);
                    if (notificationOngoingViewWrapper.mRow.mUserLocked) {
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
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final TextView getChildrenCountText() {
        return null;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper, com.android.systemui.statusbar.TransformableView
    public final TransformState getCurrentState(int i) {
        return this.mTransformationHelper.getCurrentState(i);
    }

    @Override // com.android.systemui.statusbar.notification.Roundable
    public final RoundableState getRoundableState() {
        return this.mRoundableState;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        ViewTransformationHelper viewTransformationHelper = this.mTransformationHelper;
        viewTransformationHelper.getClass();
        ArraySet arraySet = new ArraySet(viewTransformationHelper.mTransformedViews.values());
        resolveTemplateViews$1(expandableNotificationRow.mEntry.mSbn);
        updateTransformedTypes();
        viewTransformationHelper.addRemainingTransformTypes(this.mView);
        ArraySet arraySet2 = new ArraySet(viewTransformationHelper.mTransformedViews.values());
        for (int i = 0; i < arraySet.size(); i++) {
            View view = (View) arraySet.valueAt(i);
            if (!arraySet2.contains(view)) {
                TransformState createFrom = TransformState.createFrom(view, viewTransformationHelper);
                createFrom.setVisible(true, true);
                createFrom.recycle();
            }
        }
    }

    public void resolveTemplateViews$1(StatusBarNotification statusBarNotification) {
        this.mLeftIcon = (ImageView) this.mView.findViewWithTag("ongoingCollapsedPrimaryIcon");
        this.mPrimary = (TextView) this.mView.findViewWithTag("collapsedPrimary");
        this.mSecondary = (TextView) this.mView.findViewWithTag("collapsedSecondary");
        this.mTime = (TextView) this.mView.findViewWithTag("collapsedHeaderTime");
        this.mExpandButton = (ImageView) this.mView.findViewWithTag("collapsedExpandButton");
        OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
        String key = statusBarNotification.getKey();
        ongoingActivityDataHelper.getClass();
        OngoingActivityData ongoingActivityDataByKey = OngoingActivityDataHelper.getOngoingActivityDataByKey(key);
        this.mData = ongoingActivityDataByKey;
        if (ongoingActivityDataByKey == null || ongoingActivityDataByKey.mChronometerView == null) {
            return;
        }
        this.mChronometer = (TextView) this.mView.findViewWithTag(ongoingActivityDataByKey.mChronometerTag);
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
    public void updateExpandability(boolean z, View.OnClickListener onClickListener, boolean z2) {
        ImageView imageView = this.mExpandButton;
        if (imageView != null) {
            imageView.setVisibility(z ? 0 : 8);
            this.mExpandButton.setOnClickListener(z ? onClickListener : null);
        }
        ImageView imageView2 = this.mLeftIcon;
        if (imageView2 != null) {
            if (!z) {
                onClickListener = null;
            }
            imageView2.setOnClickListener(onClickListener);
        }
    }

    public void updateTransformedTypes() {
        TextView textView = this.mPrimary;
        ViewTransformationHelper viewTransformationHelper = this.mTransformationHelper;
        if (textView != null) {
            viewTransformationHelper.addTransformedView(textView, 1);
        }
        TextView textView2 = this.mSecondary;
        if (textView2 != null) {
            viewTransformationHelper.addTransformedView(textView2, 2);
        }
        ImageView imageView = this.mLeftIcon;
        if (imageView != null) {
            viewTransformationHelper.addTransformedView(imageView, 0);
        }
        TextView textView3 = this.mTime;
        if (textView3 != null) {
            View[] viewArr = new View[1];
            viewTransformationHelper.addTransformedView(textView3);
        }
        TextView textView4 = this.mChronometer;
        if (textView4 != null) {
            View[] viewArr2 = new View[1];
            viewTransformationHelper.addTransformedView(textView4);
        }
        ImageView imageView2 = this.mExpandButton;
        if (imageView2 != null) {
            viewTransformationHelper.addTransformedView(imageView2, 6);
        }
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
