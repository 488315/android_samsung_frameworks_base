package com.android.internal.widget;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.RemotableViewMethod;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.flags.Flags;
import com.android.internal.R;
import java.io.IOException;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public class BigPictureNotificationImageView extends ImageView implements NotificationDrawableConsumer {
    private static final String TAG = "BigPictureNotificationImageView";
    private NotificationIconManager mIconManager;
    private final int mMaximumDrawableHeight;
    private final int mMaximumDrawableWidth;

    public BigPictureNotificationImageView(Context context) {
        this(context, null, 0, 0);
    }

    public BigPictureNotificationImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }

    public BigPictureNotificationImageView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public BigPictureNotificationImageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        boolean zIsLowRamDeviceStatic = ActivityManager.isLowRamDeviceStatic();
        this.mMaximumDrawableWidth = context.getResources().getDimensionPixelSize(zIsLowRamDeviceStatic ? R.dimen.notification_big_picture_max_width_low_ram : R.dimen.notification_big_picture_max_width);
        this.mMaximumDrawableHeight = context.getResources().getDimensionPixelSize(zIsLowRamDeviceStatic ? R.dimen.notification_big_picture_max_height_low_ram : R.dimen.notification_big_picture_max_height);
    }

    public void setIconManager(NotificationIconManager notificationIconManager) {
        this.mIconManager = notificationIconManager;
    }

    @Override // android.widget.ImageView
    @RemotableViewMethod(asyncImpl = "setImageURIAsync")
    public void setImageURI(Uri uri) {
        lambda$setImageURIAsync$0(loadImage(uri));
    }

    @Override // android.widget.ImageView
    public Runnable setImageURIAsync(Uri uri) {
        final Drawable drawableLoadImage = loadImage(uri);
        return new Runnable() { // from class: com.android.internal.widget.BigPictureNotificationImageView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setImageURIAsync$0(drawableLoadImage);
            }
        };
    }

    @Override // android.widget.ImageView
    @RemotableViewMethod(asyncImpl = "setImageIconAsync")
    public void setImageIcon(Icon icon) {
        NotificationIconManager notificationIconManager = this.mIconManager;
        if (notificationIconManager != null) {
            notificationIconManager.updateIcon(this, icon).run();
        } else {
            lambda$setImageURIAsync$0(loadImage(icon));
        }
    }

    @Override // android.widget.ImageView
    public Runnable setImageIconAsync(Icon icon) throws IOException {
        NotificationIconManager notificationIconManager = this.mIconManager;
        if (notificationIconManager != null) {
            return notificationIconManager.updateIcon(this, icon);
        }
        final Drawable drawableLoadImage = loadImage(icon);
        return new Runnable() { // from class: com.android.internal.widget.BigPictureNotificationImageView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setImageIconAsync$1(drawableLoadImage);
            }
        };
    }

    @Override // android.widget.ImageView, android.inputmethodservice.navigationbar.ButtonInterface
    /* renamed from: setImageDrawable, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$setImageURIAsync$0(Drawable drawable) {
        if ((drawable instanceof BitmapDrawable) && ((BitmapDrawable) drawable).getBitmap() == null) {
            if (Flags.bigPictureStyleDiscardEmptyIconBitmapDrawables()) {
                Log.e(TAG, "discarding BitmapDrawable with null Bitmap (invalid image file?)");
                drawable = null;
            } else {
                Log.e(TAG, "setting BitmapDrawable with null Bitmap (invalid image file?)");
            }
        }
        super.lambda$setImageURIAsync$0(drawable);
    }

    private Drawable loadImage(Uri uri) {
        if (uri == null) {
            return null;
        }
        return LocalImageResolver.resolveImage(uri, this.mContext, this.mMaximumDrawableWidth, this.mMaximumDrawableHeight);
    }

    private Drawable loadImage(Icon icon) throws IOException {
        Drawable drawableResolveImage;
        if (icon == null) {
            return null;
        }
        if ((icon.getType() == 1 || icon.getType() == 5) && icon.getBitmap() != null) {
            drawableResolveImage = LocalImageResolver.resolveImage(icon, this.mContext, icon.getBitmap().getWidth(), icon.getBitmap().getHeight());
        } else {
            drawableResolveImage = LocalImageResolver.resolveImage(icon, this.mContext, this.mMaximumDrawableWidth, this.mMaximumDrawableHeight);
        }
        if (drawableResolveImage != null) {
            return drawableResolveImage;
        }
        Drawable drawableLoadDrawable = icon.loadDrawable(this.mContext);
        if (drawableLoadDrawable != null) {
            return drawableLoadDrawable;
        }
        Log.e(TAG, "Couldn't load drawable for icon: " + icon);
        return null;
    }
}
