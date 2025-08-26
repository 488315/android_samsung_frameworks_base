package com.android.wm.shell.common.split;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.DisplayInfo;
import android.view.RoundedCorner;
import android.view.RoundedCorners;
import android.view.View;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public class DividerRoundedCorner extends View {
    public static final /* synthetic */ int $r8$clinit = 0;
    public InvertedRoundedCornerDrawInfo mBottomLeftCorner;
    public InvertedRoundedCornerDrawInfo mBottomRightCorner;
    public final Paint mDividerBarBackground;
    public int mDividerWidth;
    public boolean mIsLeftRightSplit;
    public boolean mNeedRadiusAnim;
    public ValueAnimator mRadiusAnimator;
    public final Point mStartPos;
    public InvertedRoundedCornerDrawInfo mTopLeftCorner;
    public InvertedRoundedCornerDrawInfo mTopRightCorner;

    public class InvertedRoundedCornerDrawInfo {
        public final int mCornerPosition;
        public final int mDeviceRadius;
        public final Path mPath;
        public int mRadius;

        /* renamed from: -$$Nest$mcalculateStartPos, reason: not valid java name */
        public static void m3238$$Nest$mcalculateStartPos(InvertedRoundedCornerDrawInfo invertedRoundedCornerDrawInfo, Point point) {
            DividerRoundedCorner dividerRoundedCorner = DividerRoundedCorner.this;
            boolean z = dividerRoundedCorner.mIsLeftRightSplit;
            int i = invertedRoundedCornerDrawInfo.mCornerPosition;
            int width = 0;
            if (z) {
                point.x = (i == 0 || i == 3) ? (dividerRoundedCorner.getWidth() / 2) + (dividerRoundedCorner.mDividerWidth / 2) : ((dividerRoundedCorner.getWidth() / 2) - (dividerRoundedCorner.mDividerWidth / 2)) - invertedRoundedCornerDrawInfo.mRadius;
                if (i != 0 && i != 1) {
                    width = dividerRoundedCorner.getHeight() - invertedRoundedCornerDrawInfo.mRadius;
                }
                point.y = width;
                return;
            }
            if (i != 0 && i != 3) {
                width = dividerRoundedCorner.getWidth() - invertedRoundedCornerDrawInfo.mRadius;
            }
            point.x = width;
            point.y = (i == 0 || i == 1) ? (dividerRoundedCorner.mDividerWidth / 2) + (dividerRoundedCorner.getHeight() / 2) : ((dividerRoundedCorner.getHeight() / 2) - (dividerRoundedCorner.mDividerWidth / 2)) - invertedRoundedCornerDrawInfo.mRadius;
        }

        public InvertedRoundedCornerDrawInfo(DividerRoundedCorner dividerRoundedCorner, int i) {
            this(i, null);
        }

        public InvertedRoundedCornerDrawInfo(int i, RoundedCorners roundedCorners) {
            Path path = new Path();
            this.mPath = path;
            this.mCornerPosition = i;
            RoundedCorner roundedCorner = DividerRoundedCorner.this.getDisplay().getRoundedCorner(i);
            this.mRadius = ((View) DividerRoundedCorner.this).mContext.getResources().getDimensionPixelSize(R.dimen.desktop_windowing_freeform_rounded_corner_radius);
            int radius = roundedCorner == null ? 0 : roundedCorner.getRadius();
            this.mDeviceRadius = radius;
            if (roundedCorners != null && roundedCorners.getRoundedCorner(i) != null && !DividerRoundedCorner.this.mNeedRadiusAnim) {
                radius = roundedCorners.getRoundedCorner(i).getRadius();
            }
            this.mRadius = radius;
            Path path2 = new Path();
            float f = this.mRadius;
            Path.Direction direction = Path.Direction.CW;
            path2.addRect(0.0f, 0.0f, f, f, direction);
            Path path3 = new Path();
            path3.addCircle(i == 0 || i == 3 ? this.mRadius : 0.0f, (i == 0 || i == 1) ? this.mRadius : 0.0f, this.mRadius, direction);
            path.op(path2, path3, Path.Op.DIFFERENCE);
        }
    }

    public DividerRoundedCorner(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mStartPos = new Point();
        this.mDividerWidth = getResources().getDimensionPixelSize(CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? R.dimen.split_divider_bar_width_fold : R.dimen.split_divider_bar_width);
        Paint paint = new Paint();
        this.mDividerBarBackground = paint;
        paint.setColor(getResources().getColor(17171593, null));
        paint.setFlags(1);
        paint.setStyle(Paint.Style.FILL);
        if (CoreRune.MW_MULTI_SPLIT) {
            paint.setColor(0);
        }
        if (CoreRune.MW_MULTI_SPLIT_DIVIDER) {
            TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, com.android.wm.shell.R.styleable.DividerHandleView, 0, 0);
            try {
                if (typedArrayObtainStyledAttributes.getInt(0, 0) == 0) {
                    int i = getResources().getConfiguration().orientation;
                } else {
                    typedArrayObtainStyledAttributes.getBoolean(1, true);
                }
                typedArrayObtainStyledAttributes.recycle();
            } catch (Throwable th) {
                typedArrayObtainStyledAttributes.recycle();
                throw th;
            }
        }
    }

    public static void createTmpPath(InvertedRoundedCornerDrawInfo invertedRoundedCornerDrawInfo, float f, float f2) {
        float f3 = invertedRoundedCornerDrawInfo.mDeviceRadius;
        int iM$1 = (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f, f3, f2, f3);
        Path path = new Path();
        Path path2 = new Path();
        float f4 = iM$1;
        Path.Direction direction = Path.Direction.CW;
        path2.addRect(0.0f, 0.0f, f4, f4, direction);
        Path path3 = new Path();
        int i = invertedRoundedCornerDrawInfo.mCornerPosition;
        path3.addCircle(i == 0 || i == 3 ? f4 : 0.0f, (i == 0 || i == 1) ? f4 : 0.0f, f4, direction);
        path.op(path2, path3, Path.Op.DIFFERENCE);
        invertedRoundedCornerDrawInfo.mPath.set(path);
        invertedRoundedCornerDrawInfo.mRadius = iM$1;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        DisplayInfo displayInfo = new DisplayInfo();
        getDisplay().getDisplayInfo(displayInfo);
        int roundedCornerRadius = MultiWindowUtils.getRoundedCornerRadius(((View) this).mContext);
        RoundedCorners roundedCornersFromRadii = RoundedCorners.fromRadii(new Pair(Integer.valueOf(roundedCornerRadius), Integer.valueOf(roundedCornerRadius)), displayInfo.logicalWidth, displayInfo.logicalHeight);
        this.mTopLeftCorner = new InvertedRoundedCornerDrawInfo(0, roundedCornersFromRadii);
        this.mTopRightCorner = new InvertedRoundedCornerDrawInfo(1, roundedCornersFromRadii);
        this.mBottomLeftCorner = new InvertedRoundedCornerDrawInfo(3, roundedCornersFromRadii);
        this.mBottomRightCorner = new InvertedRoundedCornerDrawInfo(2, roundedCornersFromRadii);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        canvas.save();
        InvertedRoundedCornerDrawInfo.m3238$$Nest$mcalculateStartPos(this.mTopLeftCorner, this.mStartPos);
        Point point = this.mStartPos;
        canvas.translate(point.x, point.y);
        canvas.drawPath(this.mTopLeftCorner.mPath, this.mDividerBarBackground);
        Point point2 = this.mStartPos;
        canvas.translate(-point2.x, -point2.y);
        InvertedRoundedCornerDrawInfo.m3238$$Nest$mcalculateStartPos(this.mTopRightCorner, this.mStartPos);
        Point point3 = this.mStartPos;
        canvas.translate(point3.x, point3.y);
        canvas.drawPath(this.mTopRightCorner.mPath, this.mDividerBarBackground);
        Point point4 = this.mStartPos;
        canvas.translate(-point4.x, -point4.y);
        InvertedRoundedCornerDrawInfo.m3238$$Nest$mcalculateStartPos(this.mBottomLeftCorner, this.mStartPos);
        Point point5 = this.mStartPos;
        canvas.translate(point5.x, point5.y);
        canvas.drawPath(this.mBottomLeftCorner.mPath, this.mDividerBarBackground);
        Point point6 = this.mStartPos;
        canvas.translate(-point6.x, -point6.y);
        InvertedRoundedCornerDrawInfo.m3238$$Nest$mcalculateStartPos(this.mBottomRightCorner, this.mStartPos);
        Point point7 = this.mStartPos;
        canvas.translate(point7.x, point7.y);
        canvas.drawPath(this.mBottomRightCorner.mPath, this.mDividerBarBackground);
        canvas.restore();
    }

    public final void startRadiusAnimation() {
        if (this.mNeedRadiusAnim) {
            this.mNeedRadiusAnim = false;
            ValueAnimator valueAnimator = this.mRadiusAnimator;
            if (valueAnimator != null) {
                valueAnimator.end();
            }
            final float roundedCornerRadius = MultiWindowUtils.getRoundedCornerRadius(((View) this).mContext);
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mRadiusAnimator = valueAnimatorOfFloat;
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.common.split.DividerRoundedCorner$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    DividerRoundedCorner dividerRoundedCorner = this.f$0;
                    float f = roundedCornerRadius;
                    int i = DividerRoundedCorner.$r8$clinit;
                    dividerRoundedCorner.getClass();
                    float fFloatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                    DividerRoundedCorner.createTmpPath(dividerRoundedCorner.mTopLeftCorner, f, fFloatValue);
                    DividerRoundedCorner.createTmpPath(dividerRoundedCorner.mTopRightCorner, f, fFloatValue);
                    DividerRoundedCorner.createTmpPath(dividerRoundedCorner.mBottomLeftCorner, f, fFloatValue);
                    DividerRoundedCorner.createTmpPath(dividerRoundedCorner.mBottomRightCorner, f, fFloatValue);
                    dividerRoundedCorner.invalidate();
                }
            });
            this.mRadiusAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.common.split.DividerRoundedCorner.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    DividerRoundedCorner.this.mRadiusAnimator = null;
                }
            });
            this.mRadiusAnimator.start();
        }
    }
}
