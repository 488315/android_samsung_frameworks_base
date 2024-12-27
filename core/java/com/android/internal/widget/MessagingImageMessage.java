package com.android.internal.widget;

import android.app.Notification;
import android.content.Context;
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
/* loaded from: classes5.dex */
public class MessagingImageMessage extends ImageView implements MessagingMessage {
    private static final String TAG = "MessagingImageMessage";
    private static final MessagingPool<MessagingImageMessage> sInstancePool =
            new MessagingPool<>(10);
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

    public MessagingImageMessage(Context context) {
        this(context, null);
    }

    public MessagingImageMessage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessagingImageMessage(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MessagingImageMessage(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mState = new MessagingMessageState(this);
        this.mPath = new Path();
        this.mMinImageHeight =
                context.getResources().getDimensionPixelSize(R.dimen.messaging_image_min_size);
        this.mMaxImageHeight =
                context.getResources().getDimensionPixelSize(R.dimen.messaging_image_max_height);
        this.mImageRounding =
                context.getResources().getDimensionPixelSize(R.dimen.messaging_image_rounding);
        this.mExtraSpacing =
                context.getResources().getDimensionPixelSize(R.dimen.messaging_image_extra_spacing);
        setMaxHeight(this.mMaxImageHeight);
        this.mIsolatedSize = getResources().getDimensionPixelSize(R.dimen.messaging_avatar_size);
    }

    @Override // com.android.internal.widget.MessagingMessage
    public MessagingMessageState getState() {
        return this.mState;
    }

    @Override // com.android.internal.widget.MessagingMessage
    public boolean setMessage(
            Notification.MessagingStyle.Message message, boolean usePrecomputedText) {
        super.setMessage(message, usePrecomputedText);
        try {
            Uri uri = message.getDataUri();
            Drawable drawable =
                    this.mImageResolver != null
                            ? this.mImageResolver.loadImage(uri)
                            : LocalImageResolver.resolveImage(uri, getContext());
            if (drawable == null) {
                return false;
            }
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicHeight == 0) {
                Log.w(TAG, "Drawable with 0 intrinsic height was returned");
                return false;
            }
            this.mDrawable = drawable;
            this.mAspectRatio = this.mDrawable.getIntrinsicWidth() / intrinsicHeight;
            if (!usePrecomputedText) {
                finalizeInflate();
                return true;
            }
            return true;
        } catch (IOException | SecurityException e) {
            e.printStackTrace();
            return false;
        }
    }

    static MessagingMessage createMessage(
            IMessagingLayout layout,
            Notification.MessagingStyle.Message m,
            ImageResolver resolver,
            boolean usePrecomputedText) {
        MessagingLinearLayout messagingLinearLayout = layout.getMessagingLinearLayout();
        MessagingImageMessage createdMessage = sInstancePool.acquire();
        if (createdMessage == null) {
            createdMessage =
                    (MessagingImageMessage)
                            LayoutInflater.from(layout.getContext())
                                    .inflate(
                                            R.layout.notification_template_messaging_image_message,
                                            (ViewGroup) messagingLinearLayout,
                                            false);
            createdMessage.addOnLayoutChangeListener(MessagingLayout.MESSAGING_PROPERTY_ANIMATOR);
        }
        createdMessage.setImageResolver(resolver);
        boolean populated = createdMessage.setMessage(m, false);
        if (!populated) {
            createdMessage.recycle();
            return MessagingTextMessage.createMessage(layout, m, usePrecomputedText);
        }
        return createdMessage;
    }

    @Override // com.android.internal.widget.MessagingMessage
    public void finalizeInflate() {
        lambda$setImageURIAsync$0(this.mDrawable);
        setContentDescription(getMessage().getText());
    }

    private void setImageResolver(ImageResolver resolver) {
        this.mImageResolver = resolver;
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.mDrawable == null) {
            Log.e(TAG, "onDraw() after recycle()!");
            return;
        }
        canvas.save();
        canvas.clipPath(getRoundedRectPath());
        int width =
                (int)
                        Math.max(
                                Math.min(getHeight(), getActualHeight()) * this.mAspectRatio,
                                getActualWidth());
        int height =
                (int)
                        Math.max(
                                (int)
                                        Math.max(
                                                Math.min(getWidth(), getActualWidth())
                                                        / this.mAspectRatio,
                                                getActualHeight()),
                                width / this.mAspectRatio);
        int left = (int) ((getActualWidth() - width) / 2.0f);
        int top = (int) ((getActualHeight() - height) / 2.0f);
        this.mDrawable.setBounds(left, top, left + width, top + height);
        this.mDrawable.draw(canvas);
        canvas.restore();
    }

    public Path getRoundedRectPath() {
        int right = getActualWidth();
        int bottom = getActualHeight();
        this.mPath.reset();
        int width = right - 0;
        float roundnessX = this.mImageRounding;
        float roundnessY = this.mImageRounding;
        float roundnessX2 = Math.min(width / 2, roundnessX);
        float roundnessY2 = Math.min((bottom - 0) / 2, roundnessY);
        this.mPath.moveTo(0, 0 + roundnessY2);
        this.mPath.quadTo(0, 0, 0 + roundnessX2, 0);
        this.mPath.lineTo(right - roundnessX2, 0);
        this.mPath.quadTo(right, 0, right, 0 + roundnessY2);
        this.mPath.lineTo(right, bottom - roundnessY2);
        this.mPath.quadTo(right, bottom, right - roundnessX2, bottom);
        this.mPath.lineTo(0 + roundnessX2, bottom);
        this.mPath.quadTo(0, bottom, 0, bottom - roundnessY2);
        this.mPath.close();
        return this.mPath;
    }

    @Override // com.android.internal.widget.MessagingMessage,
              // com.android.internal.widget.MessagingLinearLayout.MessagingChild
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
        int minImageHeight;
        if (this.mDrawable == null) {
            Log.e(TAG, "getMeasuredType() after recycle()!");
            return 0;
        }
        int measuredHeight = getMeasuredHeight();
        if (this.mIsIsolated) {
            minImageHeight = this.mIsolatedSize;
        } else {
            minImageHeight = this.mMinImageHeight;
        }
        boolean measuredTooSmall =
                measuredHeight < minImageHeight
                        && measuredHeight != this.mDrawable.getIntrinsicHeight();
        if (measuredTooSmall) {
            return 2;
        }
        return (this.mIsIsolated || measuredHeight == this.mDrawable.getIntrinsicHeight()) ? 0 : 1;
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public void setMaxDisplayedLines(int lines) {}

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mDrawable == null) {
            Log.e(TAG, "onMeasure() after recycle()!");
            setMeasuredDimension(0, 0);
        } else {
            if (this.mIsIsolated) {
                setMeasuredDimension(
                        View.MeasureSpec.getSize(widthMeasureSpec),
                        View.MeasureSpec.getSize(heightMeasureSpec));
                return;
            }
            int width =
                    Math.min(
                            View.MeasureSpec.getSize(widthMeasureSpec),
                            this.mDrawable.getIntrinsicWidth());
            int height =
                    (int)
                            Math.min(
                                    View.MeasureSpec.getSize(heightMeasureSpec),
                                    width / this.mAspectRatio);
            setMeasuredDimension(width, height);
        }
    }

    @Override // android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setActualWidth(getWidth());
        setActualHeight(getHeight());
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public int getConsumedLines() {
        return 3;
    }

    public void setActualWidth(int actualWidth) {
        this.mActualWidth = actualWidth;
        invalidate();
    }

    public int getActualWidth() {
        return this.mActualWidth;
    }

    public void setActualHeight(int actualHeight) {
        this.mActualHeight = actualHeight;
        invalidate();
    }

    public int getActualHeight() {
        return this.mActualHeight;
    }

    public void setIsolated(boolean isolated) {
        if (this.mIsIsolated != isolated) {
            this.mIsIsolated = isolated;
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) getLayoutParams();
            layoutParams.topMargin = isolated ? 0 : this.mExtraSpacing;
            setLayoutParams(layoutParams);
        }
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public int getExtraSpacing() {
        return this.mExtraSpacing;
    }
}
