package androidx.leanback.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import androidx.core.view.ViewCompat;
import androidx.leanback.R$styleable;
import com.android.systemui.R;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class PagingIndicator extends View {
    public static final TimeInterpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    public static final C02801 DOT_ALPHA;
    public static final C02812 DOT_DIAMETER;
    public static final C02823 DOT_TRANSLATION_X;
    public Bitmap mArrow;
    public final int mArrowDiameter;
    public final int mArrowGap;
    public Paint mArrowPaint;
    public final int mArrowRadius;
    public final float mArrowToBgRatio;
    public final int mDotFgSelectColor;
    public final int mDotGap;
    public final int mDotRadius;
    public boolean mIsLtr;
    public final int mShadowRadius;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Dot {
        public float mAlpha;
        public float mDiameter;
        public float mDirection = 1.0f;
        public float mLayoutDirection;
        public float mTranslationX;

        public Dot() {
            this.mLayoutDirection = PagingIndicator.this.mIsLtr ? 1.0f : -1.0f;
        }

        public final void adjustAlpha() {
            int round = Math.round(this.mAlpha * 255.0f);
            PagingIndicator pagingIndicator = PagingIndicator.this;
            Color.argb(round, Color.red(pagingIndicator.mDotFgSelectColor), Color.green(pagingIndicator.mDotFgSelectColor), Color.blue(pagingIndicator.mDotFgSelectColor));
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.leanback.widget.PagingIndicator$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.leanback.widget.PagingIndicator$2] */
    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.leanback.widget.PagingIndicator$3] */
    static {
        Class<Float> cls = Float.class;
        DOT_ALPHA = new Property(cls, "alpha") { // from class: androidx.leanback.widget.PagingIndicator.1
            @Override // android.util.Property
            public final Object get(Object obj) {
                return Float.valueOf(((Dot) obj).mAlpha);
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                Dot dot = (Dot) obj;
                dot.mAlpha = ((Float) obj2).floatValue();
                dot.adjustAlpha();
                PagingIndicator.this.invalidate();
            }
        };
        DOT_DIAMETER = new Property(cls, "diameter") { // from class: androidx.leanback.widget.PagingIndicator.2
            @Override // android.util.Property
            public final Object get(Object obj) {
                return Float.valueOf(((Dot) obj).mDiameter);
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                Dot dot = (Dot) obj;
                dot.mDiameter = ((Float) obj2).floatValue();
                PagingIndicator pagingIndicator = PagingIndicator.this;
                float f = pagingIndicator.mArrowToBgRatio;
                pagingIndicator.invalidate();
            }
        };
        DOT_TRANSLATION_X = new Property(cls, "translation_x") { // from class: androidx.leanback.widget.PagingIndicator.3
            @Override // android.util.Property
            public final Object get(Object obj) {
                return Float.valueOf(((Dot) obj).mTranslationX);
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                Dot dot = (Dot) obj;
                dot.mTranslationX = ((Float) obj2).floatValue() * dot.mDirection * dot.mLayoutDirection;
                PagingIndicator.this.invalidate();
            }
        };
    }

    public PagingIndicator(Context context) {
        this(context, null, 0);
    }

    public final void calculateDotPositions() {
        int paddingLeft = getPaddingLeft();
        getPaddingTop();
        int width = getWidth() - getPaddingRight();
        int i = this.mDotRadius;
        int i2 = this.mArrowGap;
        int i3 = this.mDotGap;
        int i4 = (i3 * (-3)) + (i2 * 2) + (i * 2);
        int i5 = (paddingLeft + width) / 2;
        int[] iArr = new int[0];
        int[] iArr2 = new int[0];
        int[] iArr3 = new int[0];
        if (this.mIsLtr) {
            int i6 = (i5 - (i4 / 2)) + i;
            iArr[0] = (i6 - i3) + i2;
            iArr2[0] = i6;
            iArr3[0] = (i2 * 2) + (i6 - (i3 * 2));
        } else {
            int i7 = ((i4 / 2) + i5) - i;
            iArr[0] = (i7 + i3) - i2;
            iArr2[0] = i7;
            iArr3[0] = ((i3 * 2) + i7) - (i2 * 2);
        }
        throw null;
    }

    public final Animator createDotTranslationXAnimator() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat((Object) null, DOT_TRANSLATION_X, (-this.mArrowGap) + this.mDotGap, 0.0f);
        ofFloat.setDuration(417L);
        ofFloat.setInterpolator(DECELERATE_INTERPOLATOR);
        return ofFloat;
    }

    public final int getDimensionFromTypedArray(TypedArray typedArray, int i, int i2) {
        return typedArray.getDimensionPixelOffset(i, getResources().getDimensionPixelOffset(i2));
    }

    public final Bitmap loadArrow() {
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.lb_ic_nav_arrow);
        if (this.mIsLtr) {
            return decodeResource;
        }
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        return Bitmap.createBitmap(decodeResource, 0, 0, decodeResource.getWidth(), decodeResource.getHeight(), matrix, false);
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        int paddingBottom = getPaddingBottom() + getPaddingTop() + this.mArrowDiameter + this.mShadowRadius;
        int mode = View.MeasureSpec.getMode(i2);
        if (mode == Integer.MIN_VALUE) {
            paddingBottom = Math.min(paddingBottom, View.MeasureSpec.getSize(i2));
        } else if (mode == 1073741824) {
            paddingBottom = View.MeasureSpec.getSize(i2);
        }
        int paddingRight = getPaddingRight() + (this.mDotGap * (-3)) + (this.mArrowGap * 2) + (this.mDotRadius * 2) + getPaddingLeft();
        int mode2 = View.MeasureSpec.getMode(i);
        if (mode2 == Integer.MIN_VALUE) {
            paddingRight = Math.min(paddingRight, View.MeasureSpec.getSize(i));
        } else if (mode2 == 1073741824) {
            paddingRight = View.MeasureSpec.getSize(i);
        }
        setMeasuredDimension(paddingRight, paddingBottom);
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        boolean z = i == 0;
        if (this.mIsLtr == z) {
            return;
        }
        this.mIsLtr = z;
        this.mArrow = loadArrow();
        calculateDotPositions();
        throw null;
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        setMeasuredDimension(i, i2);
        calculateDotPositions();
        throw null;
    }

    public PagingIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PagingIndicator(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        AnimatorSet animatorSet = new AnimatorSet();
        Resources resources = getResources();
        int[] iArr = R$styleable.PagingIndicator;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, i, 0);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, obtainStyledAttributes, i, 0);
        int dimensionFromTypedArray = getDimensionFromTypedArray(obtainStyledAttributes, 6, R.dimen.lb_page_indicator_dot_radius);
        this.mDotRadius = dimensionFromTypedArray;
        int i2 = dimensionFromTypedArray * 2;
        int dimensionFromTypedArray2 = getDimensionFromTypedArray(obtainStyledAttributes, 2, R.dimen.lb_page_indicator_arrow_radius);
        this.mArrowRadius = dimensionFromTypedArray2;
        int i3 = dimensionFromTypedArray2 * 2;
        this.mArrowDiameter = i3;
        this.mDotGap = getDimensionFromTypedArray(obtainStyledAttributes, 5, R.dimen.lb_page_indicator_dot_gap);
        this.mArrowGap = getDimensionFromTypedArray(obtainStyledAttributes, 4, R.dimen.lb_page_indicator_arrow_gap);
        new Paint(1).setColor(obtainStyledAttributes.getColor(3, getResources().getColor(R.color.lb_page_indicator_dot)));
        this.mDotFgSelectColor = obtainStyledAttributes.getColor(0, getResources().getColor(R.color.lb_page_indicator_arrow_background));
        if (this.mArrowPaint == null && obtainStyledAttributes.hasValue(1)) {
            int color = obtainStyledAttributes.getColor(1, 0);
            if (this.mArrowPaint == null) {
                this.mArrowPaint = new Paint();
            }
            this.mArrowPaint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        }
        obtainStyledAttributes.recycle();
        this.mIsLtr = resources.getConfiguration().getLayoutDirection() == 0;
        int color2 = resources.getColor(R.color.lb_page_indicator_arrow_shadow);
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.lb_page_indicator_arrow_shadow_radius);
        this.mShadowRadius = dimensionPixelSize;
        Paint paint = new Paint(1);
        float dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.lb_page_indicator_arrow_shadow_offset);
        paint.setShadowLayer(dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize2, color2);
        this.mArrow = loadArrow();
        new Rect(0, 0, this.mArrow.getWidth(), this.mArrow.getHeight());
        float f = i3;
        this.mArrowToBgRatio = this.mArrow.getWidth() / f;
        AnimatorSet animatorSet2 = new AnimatorSet();
        C02801 c02801 = DOT_ALPHA;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat((Object) null, c02801, 0.0f, 1.0f);
        ofFloat.setDuration(167L);
        TimeInterpolator timeInterpolator = DECELERATE_INTERPOLATOR;
        ofFloat.setInterpolator(timeInterpolator);
        float f2 = i2;
        C02812 c02812 = DOT_DIAMETER;
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat((Object) null, c02812, f2, f);
        ofFloat2.setDuration(417L);
        ofFloat2.setInterpolator(timeInterpolator);
        animatorSet2.playTogether(ofFloat, ofFloat2, createDotTranslationXAnimator());
        AnimatorSet animatorSet3 = new AnimatorSet();
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat((Object) null, c02801, 1.0f, 0.0f);
        ofFloat3.setDuration(167L);
        ofFloat3.setInterpolator(timeInterpolator);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat((Object) null, c02812, f, f2);
        ofFloat4.setDuration(417L);
        ofFloat4.setInterpolator(timeInterpolator);
        animatorSet3.playTogether(ofFloat3, ofFloat4, createDotTranslationXAnimator());
        animatorSet.playTogether(animatorSet2, animatorSet3);
        setLayerType(1, null);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
    }
}
