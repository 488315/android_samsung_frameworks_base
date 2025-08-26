package com.android.wm.shell.bubbles;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.PathParser;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.launcher3.icons.DotRenderer;
import com.android.systemui.R;
import com.android.wm.shell.bubbles.Bubble;
import com.android.wm.shell.shared.animation.Interpolators;
import java.util.EnumSet;

/* loaded from: classes3.dex */
public class BadgedImageView extends ConstraintLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public float mAnimatingToDotScale;
    public BubbleViewProvider mBubble;
    public final ImageView mBubbleIcon;
    public int mDotColor;
    public boolean mDotIsAnimating;
    public boolean mDotOnLeft;
    public float mDotScale;
    public int mDotSize;
    public final EnumSet mDotSuppressionFlags;
    public float mDotX;
    public float mDotY;
    public BubblePositioner mPositioner;

    enum SuppressionFlag {
        FLYOUT_VISIBLE,
        BEHIND_STACK
    }

    public BadgedImageView(Context context) {
        this(context, null);
    }

    public final void animateDotScale(float f, final Runnable runnable) {
        this.mDotIsAnimating = true;
        if (this.mAnimatingToDotScale == f || !shouldDrawDot()) {
            this.mDotIsAnimating = false;
            return;
        }
        this.mAnimatingToDotScale = f;
        final boolean z = f > 0.0f;
        clearAnimation();
        animate().setDuration(200L).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.bubbles.BadgedImageView$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                BadgedImageView badgedImageView = this.f$0;
                boolean z2 = z;
                int i = BadgedImageView.$r8$clinit;
                badgedImageView.getClass();
                float animatedFraction = valueAnimator.getAnimatedFraction();
                if (!z2) {
                    animatedFraction = 1.0f - animatedFraction;
                }
                badgedImageView.mDotScale = animatedFraction;
                badgedImageView.invalidate();
            }
        }).withEndAction(new Runnable() { // from class: com.android.wm.shell.bubbles.BadgedImageView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                BadgedImageView badgedImageView = this.f$0;
                boolean z2 = z;
                Runnable runnable2 = runnable;
                int i = BadgedImageView.$r8$clinit;
                badgedImageView.getClass();
                badgedImageView.mDotScale = z2 ? 1.0f : 0.0f;
                badgedImageView.invalidate();
                badgedImageView.mDotIsAnimating = false;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        }).start();
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) throws Resources.NotFoundException, NumberFormatException {
        super.dispatchDraw(canvas);
        if (shouldDrawDot()) {
            Paint paint = new Paint();
            paint.setColor(this.mDotColor);
            paint.setAntiAlias(true);
            canvas.save();
            canvas.drawARGB(0, 0, 0, 0);
            getDotCenter();
            canvas.drawCircle(this.mDotX, this.mDotY, (this.mDotSize / 2) * this.mDotScale, paint);
            canvas.restore();
        }
    }

    public final float[] getDotCenter() throws Resources.NotFoundException {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.bubble_size);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.sec_noti_bubble_badge_dot_size);
        this.mDotSize = dimensionPixelSize2;
        float f = dimensionPixelSize2 / 2;
        this.mDotX = f;
        float f2 = dimensionPixelSize2 / 2;
        this.mDotY = f2;
        if (!this.mDotOnLeft) {
            this.mDotX = dimensionPixelSize - f;
        }
        return new float[]{this.mDotX, f2};
    }

    public final void hideDotAndBadge(boolean z) {
        if (this.mDotSuppressionFlags.add(SuppressionFlag.BEHIND_STACK)) {
            updateDotVisibility(true);
        }
        this.mDotOnLeft = z;
        this.mBubbleIcon.setImageBitmap(this.mBubble.getBubbleIcon());
    }

    public final void initialize(BubblePositioner bubblePositioner) {
        this.mPositioner = bubblePositioner;
        new DotRenderer(this.mPositioner.mBubbleSize, PathParser.createPathFromPathData(getResources().getString(android.R.string.eventTypeCustom)), 100);
    }

    public final void removeDotSuppressionFlag(SuppressionFlag suppressionFlag) {
        if (this.mDotSuppressionFlags.remove(suppressionFlag)) {
            updateDotVisibility(suppressionFlag == SuppressionFlag.BEHIND_STACK);
        }
    }

    public final void setRenderedBubble(BubbleViewProvider bubbleViewProvider) throws Resources.NotFoundException {
        this.mBubble = bubbleViewProvider;
        this.mBubbleIcon.setImageBitmap(bubbleViewProvider.getBubbleIcon());
        if (this.mDotSuppressionFlags.contains(SuppressionFlag.BEHIND_STACK)) {
            this.mBubbleIcon.setImageBitmap(this.mBubble.getBubbleIcon());
        } else {
            showBadge();
        }
        this.mDotColor = bubbleViewProvider.getDotColor();
        new DotRenderer(this.mPositioner.mBubbleSize, bubbleViewProvider.getDotPath(), 100);
        invalidate();
    }

    public final boolean shouldDrawDot() {
        if (this.mDotIsAnimating) {
            return true;
        }
        return this.mBubble.showDot() && this.mDotSuppressionFlags.isEmpty();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void showBadge() throws Resources.NotFoundException {
        boolean z;
        Bitmap appBadge = this.mBubble.getAppBadge();
        BubbleViewProvider bubbleViewProvider = this.mBubble;
        if (bubbleViewProvider instanceof Bubble) {
            Bubble bubble = (Bubble) bubbleViewProvider;
            z = bubble.isChat() || bubble.mType == Bubble.BubbleType.TYPE_SHORTCUT || bubble.isNote();
        }
        if (appBadge == null || !z) {
            this.mBubbleIcon.setImageBitmap(this.mBubble.getBubbleIcon());
            return;
        }
        Canvas canvas = new Canvas();
        Bitmap bubbleIcon = this.mBubble.getBubbleIcon();
        Bitmap bitmapCopy = bubbleIcon.copy(bubbleIcon.getConfig(), true);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(4, 2));
        canvas.setBitmap(bitmapCopy);
        int width = bitmapCopy.getWidth() - getResources().getDimensionPixelSize(R.dimen.sec_noti_bubble_icon_outline_border);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.sec_noti_bubble_badge_size);
        Rect rect = new Rect();
        int i = width - dimensionPixelSize;
        rect.set(i, i, width, width);
        canvas.drawBitmap(appBadge, (Rect) null, rect, (Paint) null);
        canvas.setBitmap(null);
        this.mBubbleIcon.setImageBitmap(bitmapCopy);
    }

    public final void showDotAndBadge(boolean z) {
        removeDotSuppressionFlag(SuppressionFlag.BEHIND_STACK);
        if (z != this.mDotOnLeft && this.mBubble.showDot() && this.mDotSuppressionFlags.isEmpty()) {
            if (shouldDrawDot()) {
                this.mDotOnLeft = z;
                animateDotScale(0.0f, new Runnable() { // from class: com.android.wm.shell.bubbles.BadgedImageView$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        BadgedImageView badgedImageView = this.f$0;
                        int i = BadgedImageView.$r8$clinit;
                        badgedImageView.invalidate();
                        badgedImageView.animateDotScale(1.0f, null);
                    }
                });
            } else {
                this.mDotOnLeft = z;
            }
        }
        showBadge();
    }

    @Override // android.view.View
    public final String toString() {
        return "BadgedImageView{" + this.mBubble + "}";
    }

    public final void updateDotVisibility(boolean z) {
        float f = (this.mBubble.showDot() && this.mDotSuppressionFlags.isEmpty()) ? 1.0f : 0.0f;
        if (z) {
            animateDotScale(f, null);
            return;
        }
        this.mDotScale = f;
        this.mAnimatingToDotScale = f;
        invalidate();
    }

    public BadgedImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BadgedImageView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public BadgedImageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mDotSuppressionFlags = EnumSet.of(SuppressionFlag.FLYOUT_VISIBLE);
        this.mDotScale = 0.0f;
        this.mAnimatingToDotScale = 0.0f;
        this.mDotIsAnimating = false;
        new Rect();
        setLayoutDirection(0);
        LayoutInflater.from(context).inflate(R.layout.badged_image_view, this);
        ImageView imageView = (ImageView) findViewById(R.id.icon_view);
        this.mBubbleIcon = imageView;
        TypedArray typedArrayObtainStyledAttributes = ((ViewGroup) this).mContext.obtainStyledAttributes(attributeSet, new int[]{android.R.attr.src}, i, i2);
        imageView.setImageResource(typedArrayObtainStyledAttributes.getResourceId(0, 0));
        typedArrayObtainStyledAttributes.recycle();
        new DotRenderer.DrawParams();
        setFocusable(true);
        setClickable(true);
        setOutlineProvider(new ViewOutlineProvider() { // from class: com.android.wm.shell.bubbles.BadgedImageView.1
            @Override // android.view.ViewOutlineProvider
            public final void getOutline(View view, Outline outline) {
                int i3 = BadgedImageView.this.mPositioner.mBubbleSize;
                int iRound = Math.round(i3 * 0.92f);
                int i4 = (i3 - iRound) / 2;
                int i5 = iRound + i4;
                outline.setOval(i4, i4, i5, i5);
            }
        });
    }
}
