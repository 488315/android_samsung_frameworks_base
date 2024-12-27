package com.android.systemui.statusbar.notification.row.wrapper;

import android.R;
import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.service.notification.StatusBarNotification;
import android.util.Pools;
import android.view.View;
import android.widget.ImageView;
import com.android.systemui.statusbar.notification.ImageTransformState;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationBigPictureTemplateViewWrapper extends NotificationTemplateViewWrapper {
    public ImageView mImageView;

    public NotificationBigPictureTemplateViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void onContentShown(boolean z) {
        this.mRow.getClass();
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        Bitmap bitmap;
        super.onContentUpdated(expandableNotificationRow);
        this.mImageView = (ImageView) this.mView.findViewById(R.id.button4);
        StatusBarNotification statusBarNotification = expandableNotificationRow.mEntry.mSbn;
        Icon icon = (Icon) statusBarNotification.getNotification().extras.getParcelable("android.largeIcon.big", Icon.class);
        if (icon != null) {
            ImageView imageView = this.mRightIcon;
            Pools.SimplePool simplePool = ImageTransformState.sInstancePool;
            imageView.setTag(com.android.systemui.R.id.image_icon_tag, icon);
            this.mLeftIcon.setTag(com.android.systemui.R.id.image_icon_tag, icon);
            return;
        }
        ImageView imageView2 = this.mRightIcon;
        Pools.SimplePool simplePool2 = ImageTransformState.sInstancePool;
        Notification notification2 = statusBarNotification.getNotification();
        Icon largeIcon = notification2.getLargeIcon();
        if (largeIcon == null && (bitmap = notification2.largeIcon) != null) {
            largeIcon = Icon.createWithBitmap(bitmap);
        }
        imageView2.setTag(com.android.systemui.R.id.image_icon_tag, largeIcon);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void setAnimationsRunning(boolean z) {
        ImageView imageView = this.mImageView;
        if (imageView == null) {
            return;
        }
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof AnimatedImageDrawable) {
            AnimatedImageDrawable animatedImageDrawable = (AnimatedImageDrawable) drawable;
            if (z) {
                animatedImageDrawable.start();
            } else {
                animatedImageDrawable.stop();
            }
        }
    }
}
