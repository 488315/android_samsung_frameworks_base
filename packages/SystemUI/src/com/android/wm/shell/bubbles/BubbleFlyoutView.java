package com.android.wm.shell.bubbles;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.Configuration;
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
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.bubbles.Bubble;
import com.android.wm.shell.shared.TriangleShape;
import com.android.wm.shell.shared.TypefaceUtils;
import com.android.wm.shell.shared.animation.Interpolators;
import com.sec.ims.volte2.data.VolteConstants;

/* loaded from: classes3.dex */
public class BubbleFlyoutView extends FrameLayout {
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
    public int mFloatingBackgroundColor;
    public final int mFlyoutElevation;
    public final int mFlyoutPadding;
    public final int mFlyoutSpaceFromBubble;
    public final ViewGroup mFlyoutTextContainer;
    public float mFlyoutToDotHeightDelta;
    public float mFlyoutToDotWidthDelta;
    public float mFlyoutY;
    public final ShapeDrawable mLeftTriangleShape;
    public final TextView mMessageText;
    public float mNewDotRadius;
    public float mNewDotSize;
    public int mNightModeFlags;
    public BubbleStackView$$ExternalSyntheticLambda3 mOnHide;
    public float mOriginalDotSize;
    public float mPercentStillFlyout;
    public float mPercentTransitionedToDot;
    public final BubblePositioner mPositioner;
    public float mRestingTranslationX;
    public final ShapeDrawable mRightTriangleShape;
    public final ImageView mSenderAvatar;
    public final TextView mSenderText;
    public float mTranslationXWhenDot;
    public float mTranslationYWhenDot;

    public BubbleFlyoutView(Context context, BubblePositioner bubblePositioner) throws Resources.NotFoundException {
        super(context);
        this.mBgPaint = new Paint(3);
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
        TypefaceUtils.FontFamily fontFamily = TypefaceUtils.FontFamily.GSF_TITLE_MEDIUM;
        TypefaceUtils.setTypeface();
        this.mSenderAvatar = (ImageView) findViewById(R.id.bubble_flyout_avatar);
        this.mMessageText = (TextView) viewGroup.findViewById(R.id.bubble_flyout_text);
        TypefaceUtils.setTypeface();
        Resources resources = getResources();
        this.mFlyoutPadding = resources.getDimensionPixelSize(R.dimen.bubble_flyout_padding_x);
        this.mFlyoutSpaceFromBubble = resources.getDimensionPixelSize(R.dimen.bubble_flyout_space_from_bubble);
        this.mBubbleElevation = resources.getDimensionPixelSize(R.dimen.bubble_elevation);
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.bubble_flyout_elevation);
        this.mFlyoutElevation = dimensionPixelSize;
        TypedArray typedArrayObtainStyledAttributes = ((FrameLayout) this).mContext.obtainStyledAttributes(new int[]{android.R.attr.dialogCornerRadius});
        this.mFloatingBackgroundColor = getContext().getResources().getColor(R.color.sec_bubble_flyout_color);
        this.mCornerRadius = getContext().getResources().getDimensionPixelSize(R.dimen.sec_noti_bubble_flyout_radius);
        typedArrayObtainStyledAttributes.recycle();
        setPadding(0, 0, 0, 0);
        setWillNotDraw(false);
        setClipChildren(true);
        setTranslationZ(dimensionPixelSize);
        setLayoutDirection(3);
        float f = 0;
        ShapeDrawable shapeDrawable = new ShapeDrawable(TriangleShape.createHorizontal(f, f, true));
        this.mLeftTriangleShape = shapeDrawable;
        shapeDrawable.setBounds(0, 0, 0, 0);
        ShapeDrawable shapeDrawable2 = new ShapeDrawable(TriangleShape.createHorizontal(f, f, false));
        this.mRightTriangleShape = shapeDrawable2;
        shapeDrawable2.setBounds(0, 0, 0, 0);
        applyConfigurationColors();
    }

    public final void applyConfigurationColors() {
        this.mFloatingBackgroundColor = ((FrameLayout) this).mContext.getColor(android.R.color.sliding_tab_text_color_active);
        this.mSenderText.setTextColor(((FrameLayout) this).mContext.getColor(android.R.color.search_url_text_material_light));
        this.mMessageText.setTextColor(((FrameLayout) this).mContext.getColor(android.R.color.search_url_text_normal));
        this.mBgPaint.setColor(this.mFloatingBackgroundColor);
        this.mLeftTriangleShape.getPaint().setColor(this.mFloatingBackgroundColor);
        this.mRightTriangleShape.getPaint().setColor(this.mFloatingBackgroundColor);
    }

    public final void fade(boolean z, PointF pointF, boolean z2, Runnable runnable) {
        this.mFlyoutY = ((this.mBubbleSize - this.mFlyoutTextContainer.getHeight()) / 2.0f) + pointF.y;
        setAlpha(z ? 0.0f : 1.0f);
        float f = this.mFlyoutY;
        if (z) {
            f += 40.0f;
        }
        setTranslationY(f);
        updateFlyoutX(pointF.x);
        setTranslationX(this.mRestingTranslationX);
        updateDot(pointF, z2);
        animate().alpha(z ? 1.0f : 0.0f).setDuration(z ? 250L : 150L).setInterpolator(z ? Interpolators.ALPHA_IN : Interpolators.ALPHA_OUT);
        ViewPropertyAnimator viewPropertyAnimatorAnimate = animate();
        float f2 = this.mFlyoutY;
        if (!z) {
            f2 -= 40.0f;
        }
        viewPropertyAnimatorAnimate.translationY(f2).setDuration(z ? 250L : 150L).setInterpolator(z ? Interpolators.ALPHA_IN : Interpolators.ALPHA_OUT).withEndAction(runnable);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        int i = configuration.uiMode & 48;
        boolean z = i != this.mNightModeFlags;
        if (z) {
            this.mNightModeFlags = i;
            applyConfigurationColors();
        }
        if (z) {
            invalidate();
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        float width = getWidth() - (this.mFlyoutToDotWidthDelta * this.mPercentTransitionedToDot);
        float height = getHeight();
        float f = this.mFlyoutToDotHeightDelta;
        float f2 = this.mPercentTransitionedToDot;
        float f3 = height - (f * f2);
        float fM$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(1.0f, f2, this.mCornerRadius, this.mNewDotRadius * f2);
        this.mBgTranslationX = this.mTranslationXWhenDot * f2;
        this.mBgTranslationY = this.mTranslationYWhenDot * f2;
        RectF rectF = this.mBgRect;
        float f4 = 0 * this.mPercentStillFlyout;
        rectF.set(f4, 0.0f, width - f4, f3);
        this.mBgPaint.setColor(((Integer) this.mArgbEvaluator.evaluate(this.mPercentTransitionedToDot, Integer.valueOf(this.mFloatingBackgroundColor), Integer.valueOf(this.mDotColor))).intValue());
        canvas.save();
        canvas.translate(this.mBgTranslationX, this.mBgTranslationY);
        canvas.drawRoundRect(this.mBgRect, fM$1, fM$1, this.mBgPaint);
        canvas.restore();
        invalidateOutline();
        super.onDraw(canvas);
    }

    public final void setCollapsePercent(float f) {
        if (Float.isNaN(f)) {
            return;
        }
        float fMax = Math.max(0.0f, Math.min(f, 1.0f));
        this.mPercentTransitionedToDot = fMax;
        this.mPercentStillFlyout = 1.0f - fMax;
        float width = fMax * (this.mArrowPointingLeft ? -getWidth() : getWidth());
        float fMin = Math.min(1.0f, Math.max(0.0f, (this.mPercentStillFlyout - 0.75f) / 0.25f));
        this.mMessageText.setTranslationX(width);
        this.mMessageText.setAlpha(fMin);
        this.mSenderText.setTranslationX(width);
        this.mSenderText.setAlpha(fMin);
        this.mSenderAvatar.setTranslationX(width);
        this.mSenderAvatar.setAlpha(fMin);
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
        int iMax = ((int) (this.mPositioner.mDeviceConfig.isLargeScreen ? Math.max(r0.mScreenRect.width() * 0.3f, r0.mMinimumFlyoutWidthLargeScreen) : r0.mScreenRect.width() * 0.42f)) - (this.mFlyoutPadding * 2);
        if (TextUtils.isEmpty(flyoutMessage.senderName)) {
            this.mSenderText.setVisibility(8);
        } else {
            this.mSenderText.setMaxWidth(iMax);
            this.mSenderText.setText(flyoutMessage.senderName);
            this.mSenderText.setTypeface(Typeface.create(Typeface.create("sec", 0), VolteConstants.ErrorCode.BUSY_EVERYWHERE, false));
            this.mSenderText.setVisibility(0);
        }
        this.mMessageText.setMaxWidth(iMax);
        this.mMessageText.setText(flyoutMessage.message);
        this.mMessageText.setTypeface(Typeface.create(Typeface.create("sec", 0), 400, false));
        updateFontSize();
    }

    public final void updateFlyoutX(float f) {
        this.mRestingTranslationX = this.mArrowPointingLeft ? f + this.mBubbleSize + this.mFlyoutSpaceFromBubble : (f - getWidth()) - this.mFlyoutSpaceFromBubble;
    }

    public final void updateFontSize() {
        float dimensionPixelSize = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.sec_noti_bubble_flyout_message_text_size);
        float dimensionPixelSize2 = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.sec_noti_bubble_flyout_name_text_size);
        this.mMessageText.setTextSize(0, dimensionPixelSize);
        this.mSenderText.setTextSize(0, dimensionPixelSize2);
    }
}
