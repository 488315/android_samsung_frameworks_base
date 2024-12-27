package com.android.systemui.statusbar.notification.row.wrapper;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.ViewTransformationHelper;
import com.android.systemui.statusbar.notification.CustomInterpolatorTransformation;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.TransformState;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationGroupHeaderViewWrapper extends NotificationHeaderViewWrapper {
    public NotificationGroupHeaderViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
        this.mTransformationHelper.setCustomTransformation(null, 1);
        this.mTransformationHelper.setCustomTransformation(null, 7);
        this.mTransformationHelper.setCustomTransformation(null, 0);
        this.mTransformationHelper.setCustomTransformation(new CustomInterpolatorTransformation(this, 6) { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationGroupHeaderViewWrapper.1
            @Override // com.android.systemui.statusbar.notification.CustomInterpolatorTransformation, com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public final boolean transformFrom(TransformState transformState, TransformableView transformableView, float f) {
                if (transformableView.getCurrentState(6) == null) {
                    return false;
                }
                transformState.ensureVisible();
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
        this.mTransformationHelper.setCustomTransformation(new CustomInterpolatorTransformation(this, 9) { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationGroupHeaderViewWrapper.2
            @Override // com.android.systemui.statusbar.notification.CustomInterpolatorTransformation, com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public final boolean transformFrom(TransformState transformState, TransformableView transformableView, float f) {
                transformState.ensureVisible();
                View view2 = transformState.mTransformedView;
                view2.setScaleX(NotificationUtils.interpolate(0.85f, 1.0f, f));
                view2.setScaleY(NotificationUtils.interpolate(0.85f, 1.0f, f));
                view2.setAlpha(f);
                return true;
            }
        }, 9);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper
    public final void resolveHeaderViews() {
        super.resolveHeaderViews();
        this.mAppNameText.setTextSize(2, 16.0f);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper
    public final void updateTransformedTypes() {
        super.updateTransformedTypes();
        TextView textView = this.mAppNameText;
        ViewTransformationHelper viewTransformationHelper = this.mTransformationHelper;
        viewTransformationHelper.addTransformedView(textView, 8);
        viewTransformationHelper.addTransformedView(this.mNotificationHeader, 9);
    }
}
