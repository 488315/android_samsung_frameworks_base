package com.android.internal.widget;

import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.android.internal.R;
import java.io.IOException;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public class MessagingImageMessage extends ImageView implements MessagingMessage {
    private static final String TAG = "MessagingImageMessage";
    private static final MessagingPool<MessagingImageMessage> sInstancePool = new MessagingPool<>(10);
    private int mActualHeight;
    private int mActualWidth;
    private float mAspectRatio;
    private Drawable mDrawable;
    private final int mExtraSpacing;
    private ImageResolver mImageResolver;
    private final int mImageRounding;
    private boolean mIsIsolated;
    private final int mIsolatedSize;
    private final int mMaxImageHeight;
    private final int mMinImageHeight;
    private final Path mPath;
    private final MessagingMessageState mState;

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public int getConsumedLines() {
        return 3;
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public void setMaxDisplayedLines(int i) {
    }

    public MessagingImageMessage(Context context) {
        this(context, null);
    }

    public MessagingImageMessage(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MessagingImageMessage(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MessagingImageMessage(Context context, AttributeSet attributeSet, int i, int i2) throws Resources.NotFoundException {
        super(context, attributeSet, i, i2);
        this.mState = new MessagingMessageState(this);
        this.mPath = new Path();
        this.mMinImageHeight = context.getResources().getDimensionPixelSize(R.dimen.messaging_image_min_size);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.messaging_image_max_height);
        this.mMaxImageHeight = dimensionPixelSize;
        this.mImageRounding = context.getResources().getDimensionPixelSize(R.dimen.messaging_image_rounding);
        this.mExtraSpacing = context.getResources().getDimensionPixelSize(R.dimen.messaging_image_extra_spacing);
        setMaxHeight(dimensionPixelSize);
        this.mIsolatedSize = getResources().getDimensionPixelSize(R.dimen.messaging_avatar_size);
    }

    @Override // com.android.internal.widget.MessagingMessage
    public MessagingMessageState getState() {
        return this.mState;
    }

    @Override // com.android.internal.widget.MessagingMessage
    public boolean setMessage(Notification.MessagingStyle.Message message, boolean z) {
        super.setMessage(message, z);
        try {
            Uri dataUri = message.getDataUri();
            ImageResolver imageResolver = this.mImageResolver;
            Drawable drawableLoadImage = imageResolver != null ? imageResolver.loadImage(dataUri) : LocalImageResolver.resolveImage(dataUri, getContext());
            if (drawableLoadImage == null) {
                return false;
            }
            int intrinsicHeight = drawableLoadImage.getIntrinsicHeight();
            if (intrinsicHeight == 0) {
                Log.w(TAG, "Drawable with 0 intrinsic height was returned");
                return false;
            }
            this.mDrawable = drawableLoadImage;
            this.mAspectRatio = drawableLoadImage.getIntrinsicWidth() / intrinsicHeight;
            if (z) {
                return true;
            }
            finalizeInflate();
            return true;
        } catch (IOException | SecurityException e) {
            e.printStackTrace();
            return false;
        }
    }

    static MessagingMessage createMessage(IMessagingLayout iMessagingLayout, Notification.MessagingStyle.Message message, ImageResolver imageResolver, boolean z) {
        MessagingLinearLayout messagingLinearLayout = iMessagingLayout.getMessagingLinearLayout();
        MessagingImageMessage messagingImageMessage = (MessagingImageMessage) sInstancePool.acquire();
        if (messagingImageMessage == null) {
            messagingImageMessage = (MessagingImageMessage) LayoutInflater.from(iMessagingLayout.getContext()).inflate(R.layout.notification_template_messaging_image_message, (ViewGroup) messagingLinearLayout, false);
            messagingImageMessage.addOnLayoutChangeListener(MessagingLayout.MESSAGING_PROPERTY_ANIMATOR);
        }
        messagingImageMessage.setImageResolver(imageResolver);
        if (messagingImageMessage.setMessage(message, false)) {
            return messagingImageMessage;
        }
        messagingImageMessage.recycle();
        return MessagingTextMessage.createMessage(iMessagingLayout, message, z);
    }

    @Override // com.android.internal.widget.MessagingMessage
    public void finalizeInflate() {
        lambda$setImageURIAsync$0(this.mDrawable);
        setContentDescription(getMessage().getText());
    }

    private void setImageResolver(ImageResolver imageResolver) {
        this.mImageResolver = imageResolver;
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.mDrawable == null) {
            Log.e(TAG, "onDraw() after recycle()!");
            return;
        }
        canvas.save();
        canvas.clipPath(getRoundedRectPath());
        int iMax = (int) Math.max(Math.min(getHeight(), getActualHeight()) * this.mAspectRatio, getActualWidth());
        int actualWidth = (int) ((getActualWidth() - iMax) / 2.0f);
        int actualHeight = (int) ((getActualHeight() - r1) / 2.0f);
        this.mDrawable.setBounds(actualWidth, actualHeight, iMax + actualWidth, ((int) Math.max((int) Math.max(Math.min(getWidth(), getActualWidth()) / this.mAspectRatio, getActualHeight()), iMax / this.mAspectRatio)) + actualHeight);
        this.mDrawable.draw(canvas);
        canvas.restore();
    }

    public Path getRoundedRectPath() {
        int actualWidth = getActualWidth();
        int actualHeight = getActualHeight();
        this.mPath.reset();
        int i = this.mImageRounding;
        float fMin = Math.min(actualWidth / 2, i);
        float fMin2 = Math.min(actualHeight / 2, i);
        float f = 0;
        float f2 = f + fMin2;
        this.mPath.moveTo(f, f2);
        float f3 = f + fMin;
        this.mPath.quadTo(f, f, f3, f);
        float f4 = actualWidth;
        float f5 = f4 - fMin;
        this.mPath.lineTo(f5, f);
        this.mPath.quadTo(f4, f, f4, f2);
        float f6 = actualHeight;
        float f7 = f6 - fMin2;
        this.mPath.lineTo(f4, f7);
        this.mPath.quadTo(f4, f6, f5, f6);
        this.mPath.lineTo(f3, f6);
        this.mPath.quadTo(f, f6, f, f7);
        this.mPath.close();
        return this.mPath;
    }

    @Override // com.android.internal.widget.MessagingMessage, com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public void recycle() {
        super.recycle();
        setImageBitmap(null);
        this.mDrawable = null;
        sInstancePool.release((MessagingPool<MessagingImageMessage>) this);
    }

    public static void dropCache() {
        sInstancePool.clear();
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public int getMeasuredType() {
        int i;
        if (this.mDrawable == null) {
            Log.e(TAG, "getMeasuredType() after recycle()!");
            return 0;
        }
        int measuredHeight = getMeasuredHeight();
        if (this.mIsIsolated) {
            i = this.mIsolatedSize;
        } else {
            i = this.mMinImageHeight;
        }
        if (measuredHeight >= i || measuredHeight == this.mDrawable.getIntrinsicHeight()) {
            return (this.mIsIsolated || measuredHeight == this.mDrawable.getIntrinsicHeight()) ? 0 : 1;
        }
        return 2;
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mDrawable == null) {
            Log.e(TAG, "onMeasure() after recycle()!");
            setMeasuredDimension(0, 0);
        } else if (this.mIsIsolated) {
            setMeasuredDimension(View.MeasureSpec.getSize(i), View.MeasureSpec.getSize(i2));
        } else {
            int iMin = Math.min(View.MeasureSpec.getSize(i), this.mDrawable.getIntrinsicWidth());
            setMeasuredDimension(iMin, (int) Math.min(View.MeasureSpec.getSize(i2), iMin / this.mAspectRatio));
        }
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        setActualWidth(getWidth());
        setActualHeight(getHeight());
    }

    public void setActualWidth(int i) {
        this.mActualWidth = i;
        invalidate();
    }

    public int getActualWidth() {
        return this.mActualWidth;
    }

    public void setActualHeight(int i) {
        this.mActualHeight = i;
        invalidate();
    }

    public int getActualHeight() {
        return this.mActualHeight;
    }

    public void setIsolated(boolean z) {
        if (this.mIsIsolated != z) {
            this.mIsIsolated = z;
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
            marginLayoutParams.topMargin = z ? 0 : this.mExtraSpacing;
            setLayoutParams(marginLayoutParams);
        }
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public int getExtraSpacing() {
        return this.mExtraSpacing;
    }
}
