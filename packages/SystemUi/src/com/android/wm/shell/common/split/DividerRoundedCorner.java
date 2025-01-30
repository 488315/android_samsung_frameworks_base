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
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import com.android.wm.shell.C3767R;
import com.android.systemui.R;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class DividerRoundedCorner extends View {
    public static final /* synthetic */ int $r8$clinit = 0;
    public InvertedRoundedCornerDrawInfo mBottomLeftCorner;
    public InvertedRoundedCornerDrawInfo mBottomRightCorner;
    public final Paint mDividerBarBackground;
    public int mDividerWidth;
    public final int mHandleType;
    public final boolean mIsHorizontalDivision;
    public boolean mNeedRadiusAnim;
    public ValueAnimator mRadiusAnimator;
    public final Point mStartPos;
    public InvertedRoundedCornerDrawInfo mTopLeftCorner;
    public InvertedRoundedCornerDrawInfo mTopRightCorner;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class InvertedRoundedCornerDrawInfo {
        public final int mCornerPosition;
        public final int mDeviceRadius;
        public final Path mPath;
        public int mRadius;

        /* renamed from: -$$Nest$mcalculateStartPos, reason: not valid java name */
        public static void m2742$$Nest$mcalculateStartPos(InvertedRoundedCornerDrawInfo invertedRoundedCornerDrawInfo, Point point) {
            invertedRoundedCornerDrawInfo.getClass();
            int i = DividerRoundedCorner.$r8$clinit;
            DividerRoundedCorner dividerRoundedCorner = DividerRoundedCorner.this;
            dividerRoundedCorner.getClass();
            boolean z = true;
            boolean z2 = (CoreRune.MW_MULTI_SPLIT_DIVIDER && dividerRoundedCorner.mHandleType == 1) ? !dividerRoundedCorner.mIsHorizontalDivision : dividerRoundedCorner.getResources().getConfiguration().orientation == 2;
            int i2 = invertedRoundedCornerDrawInfo.mCornerPosition;
            if (!z2) {
                point.x = i2 == 0 || i2 == 3 ? 0 : dividerRoundedCorner.getWidth() - invertedRoundedCornerDrawInfo.mRadius;
                point.y = ((i2 == 0 || i2 == 1) ? 1 : 0) != 0 ? (dividerRoundedCorner.mDividerWidth / 2) + (dividerRoundedCorner.getHeight() / 2) : ((dividerRoundedCorner.getHeight() / 2) - (dividerRoundedCorner.mDividerWidth / 2)) - invertedRoundedCornerDrawInfo.mRadius;
                return;
            }
            point.x = i2 == 0 || i2 == 3 ? (dividerRoundedCorner.mDividerWidth / 2) + (dividerRoundedCorner.getWidth() / 2) : ((dividerRoundedCorner.getWidth() / 2) - (dividerRoundedCorner.mDividerWidth / 2)) - invertedRoundedCornerDrawInfo.mRadius;
            if (i2 != 0 && i2 != 1) {
                z = false;
            }
            point.y = z ? 0 : dividerRoundedCorner.getHeight() - invertedRoundedCornerDrawInfo.mRadius;
        }

        public InvertedRoundedCornerDrawInfo(DividerRoundedCorner dividerRoundedCorner, int i) {
            this(i, null);
        }

        public InvertedRoundedCornerDrawInfo(int i, RoundedCorners roundedCorners) {
            Path path = new Path();
            this.mPath = path;
            this.mCornerPosition = i;
            RoundedCorner roundedCorner = DividerRoundedCorner.this.getDisplay().getRoundedCorner(i);
            this.mRadius = roundedCorner == null ? 0 : roundedCorner.getRadius();
            int radius = roundedCorner == null ? 0 : roundedCorner.getRadius();
            this.mDeviceRadius = radius;
            if (roundedCorners != null && roundedCorners.getRoundedCorner(i) != null && !DividerRoundedCorner.this.mNeedRadiusAnim) {
                radius = roundedCorners.getRoundedCorner(i).getRadius();
            }
            this.mRadius = radius;
            Path path2 = new Path();
            float f = this.mRadius;
            path2.addRect(0.0f, 0.0f, f, f, Path.Direction.CW);
            Path path3 = new Path();
            path3.addCircle(i == 0 || i == 3 ? this.mRadius : 0.0f, i == 0 || i == 1 ? this.mRadius : 0.0f, this.mRadius, Path.Direction.CW);
            path.op(path2, path3, Path.Op.DIFFERENCE);
        }
    }

    public DividerRoundedCorner(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mStartPos = new Point();
        this.mDividerWidth = getResources().getDimensionPixelSize(CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? R.dimen.split_divider_bar_width_fold : R.dimen.split_divider_bar_width);
        Paint paint = new Paint();
        this.mDividerBarBackground = paint;
        paint.setColor(getResources().getColor(17171493, null));
        boolean z = true;
        paint.setFlags(1);
        paint.setStyle(Paint.Style.FILL);
        if (CoreRune.MW_MULTI_SPLIT) {
            paint.setColor(0);
        }
        if (CoreRune.MW_MULTI_SPLIT_DIVIDER) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C3767R.styleable.DividerHandleView, 0, 0);
            try {
                int i = obtainStyledAttributes.getInt(0, 0);
                this.mHandleType = i;
                if (i != 0) {
                    z = obtainStyledAttributes.getBoolean(1, true);
                } else if (getResources().getConfiguration().orientation != 1) {
                    z = false;
                }
                this.mIsHorizontalDivision = z;
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
    }

    public static void createTmpPath(InvertedRoundedCornerDrawInfo invertedRoundedCornerDrawInfo, float f, float f2) {
        float f3 = invertedRoundedCornerDrawInfo.mDeviceRadius;
        int m20m = (int) DependencyGraph$$ExternalSyntheticOutline0.m20m(f, f3, f2, f3);
        Path path = new Path();
        Path path2 = new Path();
        float f4 = m20m;
        path2.addRect(0.0f, 0.0f, f4, f4, Path.Direction.CW);
        Path path3 = new Path();
        int i = invertedRoundedCornerDrawInfo.mCornerPosition;
        path3.addCircle(i == 0 || i == 3 ? f4 : 0.0f, i == 0 || i == 1 ? f4 : 0.0f, f4, Path.Direction.CW);
        path.op(path2, path3, Path.Op.DIFFERENCE);
        invertedRoundedCornerDrawInfo.mPath.set(path);
        invertedRoundedCornerDrawInfo.mRadius = m20m;
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
        RoundedCorners fromRadii = RoundedCorners.fromRadii(new Pair(Integer.valueOf(roundedCornerRadius), Integer.valueOf(roundedCornerRadius)), displayInfo.logicalWidth, displayInfo.logicalHeight);
        this.mTopLeftCorner = new InvertedRoundedCornerDrawInfo(0, fromRadii);
        this.mTopRightCorner = new InvertedRoundedCornerDrawInfo(1, fromRadii);
        this.mBottomLeftCorner = new InvertedRoundedCornerDrawInfo(3, fromRadii);
        this.mBottomRightCorner = new InvertedRoundedCornerDrawInfo(2, fromRadii);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        canvas.save();
        InvertedRoundedCornerDrawInfo.m2742$$Nest$mcalculateStartPos(this.mTopLeftCorner, this.mStartPos);
        Point point = this.mStartPos;
        canvas.translate(point.x, point.y);
        canvas.drawPath(this.mTopLeftCorner.mPath, this.mDividerBarBackground);
        Point point2 = this.mStartPos;
        canvas.translate(-point2.x, -point2.y);
        InvertedRoundedCornerDrawInfo.m2742$$Nest$mcalculateStartPos(this.mTopRightCorner, this.mStartPos);
        Point point3 = this.mStartPos;
        canvas.translate(point3.x, point3.y);
        canvas.drawPath(this.mTopRightCorner.mPath, this.mDividerBarBackground);
        Point point4 = this.mStartPos;
        canvas.translate(-point4.x, -point4.y);
        InvertedRoundedCornerDrawInfo.m2742$$Nest$mcalculateStartPos(this.mBottomLeftCorner, this.mStartPos);
        Point point5 = this.mStartPos;
        canvas.translate(point5.x, point5.y);
        canvas.drawPath(this.mBottomLeftCorner.mPath, this.mDividerBarBackground);
        Point point6 = this.mStartPos;
        canvas.translate(-point6.x, -point6.y);
        InvertedRoundedCornerDrawInfo.m2742$$Nest$mcalculateStartPos(this.mBottomRightCorner, this.mStartPos);
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
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mRadiusAnimator = ofFloat;
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.common.split.DividerRoundedCorner$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    DividerRoundedCorner dividerRoundedCorner = DividerRoundedCorner.this;
                    float f = roundedCornerRadius;
                    int i = DividerRoundedCorner.$r8$clinit;
                    dividerRoundedCorner.getClass();
                    float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                    DividerRoundedCorner.createTmpPath(dividerRoundedCorner.mTopLeftCorner, f, floatValue);
                    DividerRoundedCorner.createTmpPath(dividerRoundedCorner.mTopRightCorner, f, floatValue);
                    DividerRoundedCorner.createTmpPath(dividerRoundedCorner.mBottomLeftCorner, f, floatValue);
                    DividerRoundedCorner.createTmpPath(dividerRoundedCorner.mBottomRightCorner, f, floatValue);
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
