package com.android.systemui.statusbar.notification;

import android.util.Pools;
import android.view.View;
import com.android.internal.widget.MessagingImageMessage;
import com.android.systemui.R;
import com.android.systemui.statusbar.ViewTransformationHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MessagingImageTransformState extends ImageTransformState {
    public MessagingImageMessage mImageMessage;
    public static final Pools.SimplePool sInstancePool = new Pools.SimplePool(40);
    public static final int START_ACTUAL_WIDTH = R.id.transformation_start_actual_width;
    public static final int START_ACTUAL_HEIGHT = R.id.transformation_start_actual_height;

    @Override // com.android.systemui.statusbar.notification.ImageTransformState, com.android.systemui.statusbar.notification.TransformState
    public final void initFrom(View view, ViewTransformationHelper viewTransformationHelper) {
        super.initFrom(view, viewTransformationHelper);
        this.mImageMessage = (MessagingImageMessage) view;
    }

    @Override // com.android.systemui.statusbar.notification.ImageTransformState, com.android.systemui.statusbar.notification.TransformState
    public final void recycle() {
        super.recycle();
        sInstancePool.release(this);
    }

    @Override // com.android.systemui.statusbar.notification.ImageTransformState, com.android.systemui.statusbar.notification.TransformState
    public final void reset() {
        super.reset();
        this.mImageMessage = null;
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void resetTransformedView() {
        super.resetTransformedView();
        MessagingImageMessage messagingImageMessage = this.mImageMessage;
        messagingImageMessage.setActualWidth(messagingImageMessage.getWidth());
        MessagingImageMessage messagingImageMessage2 = this.mImageMessage;
        messagingImageMessage2.setActualHeight(messagingImageMessage2.getHeight());
    }

    @Override // com.android.systemui.statusbar.notification.ImageTransformState, com.android.systemui.statusbar.notification.TransformState
    public final boolean sameAs(TransformState transformState) {
        if (super.sameAs(transformState)) {
            return true;
        }
        if (transformState instanceof MessagingImageTransformState) {
            return this.mImageMessage.sameAs(((MessagingImageTransformState) transformState).mImageMessage);
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final boolean transformScale(TransformState transformState) {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void transformViewFrom(TransformState transformState, int i, ViewTransformationHelper.CustomTransformation customTransformation, float f) {
        super.transformViewFrom(transformState, i, customTransformation, f);
        float interpolation = this.mDefaultInterpolator.getInterpolation(f);
        if ((transformState instanceof MessagingImageTransformState) && sameAs(transformState)) {
            MessagingImageMessage messagingImageMessage = ((MessagingImageTransformState) transformState).mImageMessage;
            int i2 = START_ACTUAL_HEIGHT;
            int i3 = START_ACTUAL_WIDTH;
            if (f == 0.0f) {
                this.mTransformedView.setTag(i3, Integer.valueOf(messagingImageMessage.getActualWidth()));
                this.mTransformedView.setTag(i2, Integer.valueOf(messagingImageMessage.getActualHeight()));
            }
            Object tag = this.mTransformedView.getTag(i3);
            int intValue = tag == null ? -1 : ((Integer) tag).intValue();
            this.mImageMessage.setActualWidth((int) NotificationUtils.interpolate(intValue, r0.getWidth(), interpolation));
            Object tag2 = this.mTransformedView.getTag(i2);
            int intValue2 = tag2 != null ? ((Integer) tag2).intValue() : -1;
            this.mImageMessage.setActualHeight((int) NotificationUtils.interpolate(intValue2, r2.getHeight(), interpolation));
        }
    }
}
