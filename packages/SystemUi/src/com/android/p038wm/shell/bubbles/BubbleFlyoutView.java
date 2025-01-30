package com.android.p038wm.shell.bubbles;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.animation.Interpolators;
import com.android.p038wm.shell.bubbles.Bubble;
import com.android.p038wm.shell.common.TriangleShape;
import com.android.systemui.R;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BubbleFlyoutView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArgbEvaluator mArgbEvaluator;
    public boolean mArrowPointingLeft;
    public final Paint mBgPaint;
    public final RectF mBgRect;
    public float mBgTranslationX;
    public float mBgTranslationY;
    public final int mBubbleElevation;
    public int mBubbleSize;
    public final float mCornerRadius;
    public float[] mDotCenter;
    public int mDotColor;
    public final int mFloatingBackgroundColor;
    public final int mFlyoutElevation;
    public final int mFlyoutPadding;
    public final int mFlyoutSpaceFromBubble;
    public final ViewGroup mFlyoutTextContainer;
    public float mFlyoutToDotHeightDelta;
    public float mFlyoutToDotWidthDelta;
    public float mFlyoutY;
    public final TextView mMessageText;
    public float mNewDotRadius;
    public float mNewDotSize;
    public Runnable mOnHide;
    public float mOriginalDotSize;
    public float mPercentStillFlyout;
    public float mPercentTransitionedToDot;
    public final BubblePositioner mPositioner;
    public float mRestingTranslationX;
    public final ImageView mSenderAvatar;
    public final TextView mSenderText;
    public float mTranslationXWhenDot;
    public float mTranslationYWhenDot;

    public BubbleFlyoutView(Context context, BubblePositioner bubblePositioner) {
        super(context);
        Paint paint = new Paint(3);
        this.mBgPaint = paint;
        this.mArgbEvaluator = new ArgbEvaluator();
        this.mArrowPointingLeft = true;
        new Outline();
        this.mBgRect = new RectF();
        this.mFlyoutY = 0.0f;
        this.mPercentTransitionedToDot = 1.0f;
        this.mPercentStillFlyout = 0.0f;
        this.mFlyoutToDotWidthDelta = 0.0f;
        this.mFlyoutToDotHeightDelta = 0.0f;
        this.mTranslationXWhenDot = 0.0f;
        this.mTranslationYWhenDot = 0.0f;
        this.mRestingTranslationX = 0.0f;
        this.mPositioner = bubblePositioner;
        LayoutInflater.from(context).inflate(R.layout.bubble_flyout, (ViewGroup) this, true);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.bubble_flyout_text_container);
        this.mFlyoutTextContainer = viewGroup;
        this.mSenderText = (TextView) findViewById(R.id.bubble_flyout_name);
        this.mSenderAvatar = (ImageView) findViewById(R.id.bubble_flyout_avatar);
        this.mMessageText = (TextView) viewGroup.findViewById(R.id.bubble_flyout_text);
        Resources resources = getResources();
        this.mFlyoutPadding = resources.getDimensionPixelSize(R.dimen.bubble_flyout_padding_x);
        this.mFlyoutSpaceFromBubble = resources.getDimensionPixelSize(R.dimen.bubble_flyout_space_from_bubble);
        this.mBubbleElevation = resources.getDimensionPixelSize(R.dimen.bubble_elevation);
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.bubble_flyout_elevation);
        this.mFlyoutElevation = dimensionPixelSize;
        TypedArray obtainStyledAttributes = ((FrameLayout) this).mContext.obtainStyledAttributes(new int[]{android.R.^attr-private.materialColorOnSecondaryFixedVariant, android.R.attr.dialogCornerRadius});
        int color = getContext().getResources().getColor(R.color.sec_bubble_flyout_color);
        this.mFloatingBackgroundColor = color;
        this.mCornerRadius = getContext().getResources().getDimensionPixelSize(R.dimen.sec_noti_bubble_flyout_radius);
        obtainStyledAttributes.recycle();
        setPadding(0, 0, 0, 0);
        setWillNotDraw(false);
        setClipChildren(true);
        setTranslationZ(dimensionPixelSize);
        setLayoutDirection(3);
        paint.setColor(color);
        float f = 0;
        ShapeDrawable shapeDrawable = new ShapeDrawable(TriangleShape.createHorizontal(f, f, true));
        shapeDrawable.setBounds(0, 0, 0, 0);
        shapeDrawable.getPaint().setColor(color);
        ShapeDrawable shapeDrawable2 = new ShapeDrawable(TriangleShape.createHorizontal(f, f, false));
        shapeDrawable2.setBounds(0, 0, 0, 0);
        shapeDrawable2.getPaint().setColor(color);
    }

    public final void fade(boolean z, PointF pointF, boolean z2, Runnable runnable) {
        this.mFlyoutY = ((this.mBubbleSize - this.mFlyoutTextContainer.getHeight()) / 2.0f) + pointF.y;
        setAlpha(z ? 0.0f : 1.0f);
        float f = this.mFlyoutY;
        if (z) {
            f += 40.0f;
        }
        setTranslationY(f);
        float f2 = pointF.x;
        float width = this.mArrowPointingLeft ? f2 + this.mBubbleSize + this.mFlyoutSpaceFromBubble : (f2 - getWidth()) - this.mFlyoutSpaceFromBubble;
        this.mRestingTranslationX = width;
        setTranslationX(width);
        updateDot(pointF, z2);
        animate().alpha(z ? 1.0f : 0.0f).setDuration(z ? 250L : 150L).setInterpolator(z ? Interpolators.ALPHA_IN : Interpolators.ALPHA_OUT);
        ViewPropertyAnimator animate = animate();
        float f3 = this.mFlyoutY;
        if (!z) {
            f3 -= 40.0f;
        }
        animate.translationY(f3).setDuration(z ? 250L : 150L).setInterpolator(z ? Interpolators.ALPHA_IN : Interpolators.ALPHA_OUT).withEndAction(runnable);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        float width = getWidth() - (this.mFlyoutToDotWidthDelta * this.mPercentTransitionedToDot);
        float height = getHeight();
        float f = this.mFlyoutToDotHeightDelta;
        float f2 = this.mPercentTransitionedToDot;
        float f3 = height - (f * f2);
        float m20m = DependencyGraph$$ExternalSyntheticOutline0.m20m(1.0f, f2, this.mCornerRadius, this.mNewDotRadius * f2);
        this.mBgTranslationX = this.mTranslationXWhenDot * f2;
        this.mBgTranslationY = this.mTranslationYWhenDot * f2;
        RectF rectF = this.mBgRect;
        float f4 = 0 * this.mPercentStillFlyout;
        rectF.set(f4, 0.0f, width - f4, f3);
        this.mBgPaint.setColor(((Integer) this.mArgbEvaluator.evaluate(this.mPercentTransitionedToDot, Integer.valueOf(this.mFloatingBackgroundColor), Integer.valueOf(this.mDotColor))).intValue());
        this.mBgPaint.setShadowLayer(m20m, 0.0f, 2.0f, getContext().getResources().getColor(R.color.sec_bubble_icon_outline_border_color));
        canvas.save();
        canvas.translate(this.mBgTranslationX, this.mBgTranslationY);
        canvas.drawRoundRect(this.mBgRect, m20m, m20m, this.mBgPaint);
        canvas.restore();
        invalidateOutline();
        super.onDraw(canvas);
    }

    public final void setCollapsePercent(float f) {
        if (Float.isNaN(f)) {
            return;
        }
        float max = Math.max(0.0f, Math.min(f, 1.0f));
        this.mPercentTransitionedToDot = max;
        this.mPercentStillFlyout = 1.0f - max;
        float width = max * (this.mArrowPointingLeft ? -getWidth() : getWidth());
        float min = Math.min(1.0f, Math.max(0.0f, (this.mPercentStillFlyout - 0.75f) / 0.25f));
        this.mMessageText.setTranslationX(width);
        this.mMessageText.setAlpha(min);
        this.mSenderText.setTranslationX(width);
        this.mSenderText.setAlpha(min);
        this.mSenderAvatar.setTranslationX(width);
        this.mSenderAvatar.setAlpha(min);
        setTranslationZ(this.mFlyoutElevation - ((r5 - this.mBubbleElevation) * this.mPercentTransitionedToDot));
        invalidate();
    }

    public final void updateDot(PointF pointF, boolean z) {
        float f = z ? 0.0f : this.mNewDotSize;
        this.mFlyoutToDotWidthDelta = getWidth() - f;
        this.mFlyoutToDotHeightDelta = getHeight() - f;
        float f2 = z ? 0.0f : this.mOriginalDotSize / 2.0f;
        float f3 = pointF.x;
        float[] fArr = this.mDotCenter;
        float f4 = (f3 + fArr[0]) - f2;
        float f5 = (pointF.y + fArr[1]) - f2;
        float f6 = this.mRestingTranslationX - f4;
        float f7 = this.mFlyoutY - f5;
        this.mTranslationXWhenDot = -f6;
        this.mTranslationYWhenDot = -f7;
    }

    public final void updateFlyoutMessage(Bubble.FlyoutMessage flyoutMessage) {
        Drawable drawable = flyoutMessage.senderAvatar;
        if (drawable == null || !flyoutMessage.isGroupChat) {
            this.mSenderAvatar.setVisibility(8);
            this.mSenderAvatar.setTranslationX(0.0f);
            this.mMessageText.setTranslationX(0.0f);
            this.mSenderText.setTranslationX(0.0f);
        } else {
            this.mSenderAvatar.setVisibility(0);
            this.mSenderAvatar.setImageDrawable(drawable);
        }
        int width = ((int) (this.mPositioner.mScreenRect.width() * 0.42f)) - (this.mFlyoutPadding * 2);
        if (TextUtils.isEmpty(flyoutMessage.senderName)) {
            this.mSenderText.setVisibility(8);
        } else {
            this.mSenderText.setMaxWidth(width);
            this.mSenderText.setText(flyoutMessage.senderName);
            this.mSenderText.setTypeface(Typeface.create(Typeface.create("sec", 0), VolteConstants.ErrorCode.BUSY_EVERYWHERE, false));
            this.mSenderText.setVisibility(0);
        }
        this.mMessageText.setMaxWidth(width);
        this.mMessageText.setText(flyoutMessage.message);
        this.mMessageText.setTypeface(Typeface.create(Typeface.create("sec", 0), 400, false));
        updateFontSize();
    }

    public final void updateFontSize() {
        float dimensionPixelSize = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.sec_noti_bubble_flyout_message_text_size);
        float dimensionPixelSize2 = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.sec_noti_bubble_flyout_name_text_size);
        this.mMessageText.setTextSize(0, dimensionPixelSize);
        this.mSenderText.setTextSize(0, dimensionPixelSize2);
    }
}
