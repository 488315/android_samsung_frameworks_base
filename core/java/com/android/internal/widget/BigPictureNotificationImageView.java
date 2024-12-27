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

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class BigPictureNotificationImageView extends ImageView implements NotificationDrawableConsumer {
    private static final String TAG = BigPictureNotificationImageView.class.getSimpleName();
    private NotificationIconManager mIconManager;
    private final int mMaximumDrawableHeight;
    private final int mMaximumDrawableWidth;

    public BigPictureNotificationImageView(Context context) {
        this(context, null, 0, 0);
    }

    public BigPictureNotificationImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public BigPictureNotificationImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BigPictureNotificationImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        boolean isLowRam = ActivityManager.isLowRamDeviceStatic();
        this.mMaximumDrawableWidth = context.getResources().getDimensionPixelSize(isLowRam ? R.dimen.notification_big_picture_max_width_low_ram : R.dimen.notification_big_picture_max_width);
        this.mMaximumDrawableHeight = context.getResources().getDimensionPixelSize(isLowRam ? R.dimen.notification_big_picture_max_height_low_ram : R.dimen.notification_big_picture_max_height);
    }

    public void setIconManager(NotificationIconManager iconManager) {
        this.mIconManager = iconManager;
    }

    @Override // android.widget.ImageView
    @RemotableViewMethod(asyncImpl = "setImageURIAsync")
    public void setImageURI(Uri uri) {
        lambda$setImageURIAsync$0(loadImage(uri));
    }

    @Override // android.widget.ImageView
    public Runnable setImageURIAsync(Uri uri) {
        final Drawable drawable = loadImage(uri);
        return new Runnable() { // from class: com.android.internal.widget.BigPictureNotificationImageView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BigPictureNotificationImageView.this.lambda$setImageURIAsync$0(drawable);
            }
        };
    }

    @Override // android.widget.ImageView
    @RemotableViewMethod(asyncImpl = "setImageIconAsync")
    public void setImageIcon(Icon icon) {
        if (this.mIconManager != null) {
            this.mIconManager.updateIcon(this, icon).run();
        } else {
            lambda$setImageURIAsync$0(loadImage(icon));
        }
    }

    @Override // android.widget.ImageView
    public Runnable setImageIconAsync(Icon icon) {
        if (this.mIconManager != null) {
            return this.mIconManager.updateIcon(this, icon);
        }
        final Drawable drawable = loadImage(icon);
        return new Runnable() { // from class: com.android.internal.widget.BigPictureNotificationImageView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                BigPictureNotificationImageView.this.lambda$setImageIconAsync$1(drawable);
            }
        };
    }

    @Override // android.widget.ImageView, android.inputmethodservice.navigationbar.ButtonInterface
    /* renamed from: setImageDrawable, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$setImageURIAsync$0(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() == null) {
                if (Flags.bigPictureStyleDiscardEmptyIconBitmapDrawables()) {
                    Log.e(TAG, "discarding BitmapDrawable with null Bitmap (invalid image file?)");
                    drawable = null;
                } else {
                    Log.e(TAG, "setting BitmapDrawable with null Bitmap (invalid image file?)");
                }
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

    private Drawable loadImage(Icon icon) {
        Drawable drawable;
        if (icon == null) {
            return null;
        }
        if ((icon.getType() == 1 || icon.getType() == 5) && icon.getBitmap() != null) {
            drawable = LocalImageResolver.resolveImage(icon, this.mContext, icon.getBitmap().getWidth(), icon.getBitmap().getHeight());
        } else {
            drawable = LocalImageResolver.resolveImage(icon, this.mContext, this.mMaximumDrawableWidth, this.mMaximumDrawableHeight);
        }
        if (drawable != null) {
            return drawable;
        }
        Drawable drawable2 = icon.loadDrawable(this.mContext);
        if (drawable2 != null) {
            return drawable2;
        }
        Log.e(TAG, "Couldn't load drawable for icon: " + icon);
        return null;
    }
}
