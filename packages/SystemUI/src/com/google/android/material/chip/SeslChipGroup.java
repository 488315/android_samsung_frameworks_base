package com.google.android.material.chip;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ValueAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import com.google.android.material.chip.SeslChipGroup;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Locale;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SeslChipGroup extends ChipGroup {
    public static int sChipInitialWidth;
    public int mChipMaxWidth;
    public final boolean mDynamicChipTextTruncation;
    public int mEmptyContainerHeight;
    public final LayoutTransition mLayoutTransition;
    public int mRowCount;

    public SeslChipGroup(Context context) {
        this(context, null);
    }

    public final void addRemoveAnim() {
        if (!isLineAddedOrRemoved()) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.height = -2;
            this.mEmptyContainerHeight = 0;
            setLayoutParams(layoutParams);
            return;
        }
        final int height = getHeight();
        final int internalHeight = getInternalHeight(getWidth()) - height;
        if (Math.abs(internalHeight) < getContext().getResources().getDimension(R.dimen.chip_height)) {
            return;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(getContext().getResources().getInteger(R.integer.sesl_chip_default_anim_duration));
        ofFloat.setInterpolator(AnimationUtils.loadInterpolator(getContext(), R.interpolator.sesl_chip_default_interpolator));
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.chip.SeslChipGroup.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                ViewGroup.LayoutParams layoutParams2 = SeslChipGroup.this.getLayoutParams();
                layoutParams2.height = -2;
                SeslChipGroup seslChipGroup = SeslChipGroup.this;
                seslChipGroup.mEmptyContainerHeight = 0;
                seslChipGroup.setLayoutParams(layoutParams2);
            }
        });
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.chip.SeslChipGroup$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SeslChipGroup seslChipGroup = SeslChipGroup.this;
                int i = height;
                int i2 = internalHeight;
                int i3 = SeslChipGroup.sChipInitialWidth;
                ViewGroup.LayoutParams layoutParams2 = seslChipGroup.getLayoutParams();
                int floatValue = i + ((int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * i2));
                layoutParams2.height = floatValue;
                seslChipGroup.mEmptyContainerHeight = floatValue;
                seslChipGroup.setLayoutParams(layoutParams2);
            }
        });
        ofFloat.start();
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            setLayoutTransition(this.mLayoutTransition);
        } else {
            setLayoutTransition(null);
        }
        super.addView(view instanceof SeslChip ? (SeslChip) view : view, i, layoutParams);
        if (isLineAddedOrRemoved()) {
            setLayoutTransition(null);
        }
        addRemoveAnim();
        if (view instanceof Chip) {
            Chip chip = (Chip) view;
            if (this.mDynamicChipTextTruncation) {
                int i2 = this.mChipMaxWidth;
                if (i2 > 0) {
                    chip.setMaxWidth(i2);
                }
                chip.setEllipsize(TextUtils.TruncateAt.END);
            }
        }
    }

    public final int getInternalHeight(float f) {
        int i;
        int childCount = getChildCount();
        if (childCount == 0) {
            return 0;
        }
        int paddingStart = getPaddingStart();
        int paddingEnd = getPaddingEnd();
        int i2 = this.chipSpacingHorizontal;
        int width = getChildAt(0).getWidth() + paddingStart + paddingEnd + i2;
        int i3 = 1;
        for (int i4 = 1; i4 < childCount; i4++) {
            int intrinsicWidth = ((Chip) getChildAt(i4)).chipDrawable.getIntrinsicWidth();
            if (width + intrinsicWidth < f) {
                i = intrinsicWidth + i2 + width;
            } else {
                i = intrinsicWidth + i2 + paddingStart + paddingEnd;
                i3++;
            }
            width = i;
        }
        int i5 = this.chipSpacingVertical;
        return (getPaddingTop() + (getPaddingBottom() + ((getChildAt(0).getHeight() + i5) * i3))) - i5;
    }

    @Override // com.google.android.material.internal.FlowLayout
    public final int getRowCount() {
        return this.mRowCount;
    }

    public final boolean isLineAddedOrRemoved() {
        if (getHeight() == getInternalHeight(getWidth())) {
            return false;
        }
        boolean z = this.singleLine;
        if (z) {
            return z && getChildCount() == 0;
        }
        return true;
    }

    @Override // com.google.android.material.internal.FlowLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        boolean z2 = true;
        int i7 = 0;
        if (getChildCount() == 0) {
            this.mRowCount = 0;
            return;
        }
        this.mRowCount = 1;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        boolean z3 = getLayoutDirection() == 1;
        int paddingRight = z3 ? getPaddingRight() : getPaddingLeft();
        int paddingLeft = z3 ? getPaddingLeft() : getPaddingRight();
        int paddingTop = getPaddingTop();
        int i8 = this.lineSpacing;
        int i9 = this.itemSpacing;
        int i10 = i3 - i;
        int i11 = i10 - paddingLeft;
        if (!z3) {
            i10 = i11;
        }
        int i12 = 0;
        int i13 = paddingRight;
        int i14 = paddingTop;
        while (i12 < getChildCount()) {
            View childAt = getChildAt(i12);
            boolean z4 = z2;
            if (childAt.getVisibility() == 8) {
                childAt.setTag(R.id.row_index_key, -1);
            } else {
                ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                    i6 = marginLayoutParams.getMarginStart();
                    i5 = marginLayoutParams.getMarginEnd();
                } else {
                    i5 = i7;
                    i6 = i5;
                }
                int measuredWidth = childAt.getMeasuredWidth() + i13 + i6;
                if (!this.singleLine && measuredWidth > i11) {
                    i14 = paddingTop + i8;
                    this.mRowCount++;
                    i13 = paddingRight;
                }
                childAt.setTag(R.id.row_index_key, Integer.valueOf(this.mRowCount - 1));
                int i15 = i13 + i6;
                int measuredWidth2 = childAt.getMeasuredWidth() + i15;
                paddingTop = childAt.getMeasuredHeight() + i14;
                if (z3) {
                    childAt.layout(i10 - measuredWidth2, i14, (i10 - i13) - i6, paddingTop);
                } else {
                    childAt.layout(i15, i14, measuredWidth2, paddingTop);
                }
                i13 += childAt.getMeasuredWidth() + i6 + i5 + i9;
            }
            i12++;
            z2 = z4;
            i7 = 0;
        }
    }

    @Override // com.google.android.material.internal.FlowLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (getChildCount() <= 0) {
            setMeasuredDimension(getWidth(), this.mEmptyContainerHeight);
        }
    }

    @Override // android.view.ViewGroup
    public final void removeAllViews() {
        setStaticHeight();
        super.removeAllViews();
        addRemoveAnim();
    }

    @Override // android.view.ViewGroup
    public final void removeAllViewsInLayout() {
        setStaticHeight();
        super.removeAllViewsInLayout();
        addRemoveAnim();
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public final void removeView(View view) {
        if (getChildCount() > 1) {
            setLayoutTransition(this.mLayoutTransition);
        } else {
            setLayoutTransition(null);
        }
        setStaticHeight();
        super.removeView(view);
        addRemoveAnim();
    }

    @Override // android.view.ViewGroup
    public final void removeViewAt(int i) {
        setStaticHeight();
        super.removeViewAt(i);
        addRemoveAnim();
    }

    @Override // android.view.ViewGroup
    public final void removeViewInLayout(View view) {
        setStaticHeight();
        super.removeViewInLayout(view);
        addRemoveAnim();
    }

    @Override // android.view.ViewGroup
    public final void removeViews(int i, int i2) {
        setStaticHeight();
        super.removeViews(i, i2);
        addRemoveAnim();
    }

    @Override // android.view.ViewGroup
    public final void removeViewsInLayout(int i, int i2) {
        setStaticHeight();
        super.removeViewsInLayout(i, i2);
        addRemoveAnim();
    }

    public final void setStaticHeight() {
        this.mEmptyContainerHeight = getHeight();
    }

    public SeslChipGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.chipGroupStyle);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SeslValueAnimator extends ValueAnimator {
        public ArrayList mSeslListeners;
        public ArrayList mSeslUpdateListeners;
        public WeakReference mTargetView;
        public float[] mValues;

        private SeslValueAnimator() {
        }

        public static SeslValueAnimator ofFloat(float... fArr) {
            SeslValueAnimator seslValueAnimator = new SeslValueAnimator();
            seslValueAnimator.setFloatValues(fArr);
            seslValueAnimator.mValues = fArr;
            seslValueAnimator.mSeslUpdateListeners = new ArrayList();
            seslValueAnimator.mSeslListeners = new ArrayList();
            return seslValueAnimator;
        }

        @Override // android.animation.Animator
        public final void addListener(Animator.AnimatorListener animatorListener) {
            super.addListener(animatorListener);
            this.mSeslListeners.add(animatorListener);
        }

        @Override // android.animation.ValueAnimator
        public final void addUpdateListener(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
            super.addUpdateListener(animatorUpdateListener);
            this.mSeslUpdateListeners.add(animatorUpdateListener);
        }

        @Override // android.animation.Animator
        public final void setTarget(Object obj) {
            this.mTargetView = new WeakReference((View) obj);
            super.setTarget(obj);
        }

        @Override // android.animation.ValueAnimator, android.animation.Animator
        public final SeslValueAnimator clone() {
            SeslValueAnimator ofFloat = ofFloat(this.mValues);
            ArrayList arrayList = this.mSeslUpdateListeners;
            int i = 0;
            if (arrayList != null) {
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    ofFloat.addUpdateListener((ValueAnimator.AnimatorUpdateListener) obj);
                }
            }
            ArrayList arrayList2 = this.mSeslListeners;
            if (arrayList2 != null) {
                int size2 = arrayList2.size();
                while (i < size2) {
                    Object obj2 = arrayList2.get(i);
                    i++;
                    ofFloat.addListener((Animator.AnimatorListener) obj2);
                }
            }
            return ofFloat;
        }
    }

    public SeslChipGroup(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        final int i2 = 1;
        this.mDynamicChipTextTruncation = true;
        LayoutTransition layoutTransition = new LayoutTransition();
        this.mLayoutTransition = layoutTransition;
        final int i3 = 0;
        this.mEmptyContainerHeight = 0;
        sChipInitialWidth = (int) getResources().getDimension(R.dimen.chip_start_width);
        setLayoutDirection(TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()));
        layoutTransition.enableTransitionType(2);
        layoutTransition.enableTransitionType(3);
        layoutTransition.enableTransitionType(4);
        layoutTransition.enableTransitionType(0);
        layoutTransition.enableTransitionType(1);
        layoutTransition.setStartDelay(2, 0L);
        layoutTransition.setStartDelay(3, 0L);
        layoutTransition.setStartDelay(4, 0L);
        layoutTransition.setStartDelay(0, 0L);
        layoutTransition.setStartDelay(1, 0L);
        int integer = getContext().getResources().getInteger(R.integer.sesl_chip_default_anim_duration);
        SeslValueAnimator ofFloat = SeslValueAnimator.ofFloat(0.0f, 1.0f);
        long j = integer;
        ofFloat.setDuration(j);
        ofFloat.setStartDelay(0L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.chip.SeslChipGroup$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i3) {
                    case 0:
                        int i4 = SeslChipGroup.sChipInitialWidth;
                        View view = (View) ((SeslChipGroup.SeslValueAnimator) valueAnimator).mTargetView.get();
                        if (view != null) {
                            if (!(view instanceof SeslChip)) {
                                view.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                                break;
                            } else {
                                SeslChip seslChip = (SeslChip) view;
                                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                seslChip.setRight(seslChip.getLeft() + SeslChipGroup.sChipInitialWidth + ((int) ((seslChip.chipDrawable.getIntrinsicWidth() - SeslChipGroup.sChipInitialWidth) * floatValue)));
                                seslChip.setBottom(((int) seslChip.chipDrawable.chipMinHeight) + seslChip.getTop());
                                seslChip.setInternalsAlpha((int) (MathUtils.clamp((((int) valueAnimator.getCurrentPlayTime()) - 100) / 200.0f, 0.0f, 1.0f) * 255.0f));
                                seslChip.chipDrawable.setAlpha((int) (floatValue * 255.0f));
                                ChipDrawable chipDrawable = seslChip.chipDrawable;
                                if (chipDrawable != null && chipDrawable.textEndPadding != 0.0f) {
                                    chipDrawable.textEndPadding = 0.0f;
                                    chipDrawable.invalidateSelf();
                                    chipDrawable.onSizeChange();
                                }
                                seslChip.setEllipsize(null);
                                seslChip.chipDrawable.isSeslFullText = true;
                                seslChip.invalidate();
                                break;
                            }
                        }
                        break;
                    default:
                        int i5 = SeslChipGroup.sChipInitialWidth;
                        View view2 = (View) ((SeslChipGroup.SeslValueAnimator) valueAnimator).mTargetView.get();
                        if (view2 != null) {
                            if (!(view2 instanceof SeslChip)) {
                                view2.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                                break;
                            } else {
                                SeslChip seslChip2 = (SeslChip) view2;
                                float floatValue2 = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                seslChip2.setRight(seslChip2.getLeft() + ((int) (((seslChip2.chipDrawable.getIntrinsicWidth() - SeslChipGroup.sChipInitialWidth) * floatValue2) + SeslChipGroup.sChipInitialWidth)));
                                seslChip2.setBottom(((int) seslChip2.chipDrawable.chipMinHeight) + seslChip2.getTop());
                                seslChip2.setInternalsAlpha((int) (MathUtils.clamp(1.0f - (((int) valueAnimator.getCurrentPlayTime()) / 200.0f), 0.0f, 1.0f) * 255.0f));
                                seslChip2.chipDrawable.setAlpha((int) (floatValue2 * 255.0f));
                                ChipDrawable chipDrawable2 = seslChip2.chipDrawable;
                                if (chipDrawable2 != null && chipDrawable2.textEndPadding != 0.0f) {
                                    chipDrawable2.textEndPadding = 0.0f;
                                    chipDrawable2.invalidateSelf();
                                    chipDrawable2.onSizeChange();
                                }
                                seslChip2.setEllipsize(null);
                                seslChip2.chipDrawable.isSeslFullText = true;
                                seslChip2.invalidate();
                                break;
                            }
                        }
                        break;
                }
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter(this) { // from class: com.google.android.material.chip.SeslChipGroup.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                View view = (View) ((SeslValueAnimator) animator).mTargetView.get();
                if (view == null) {
                    return;
                }
                if (!(view instanceof SeslChip)) {
                    view.setAlpha(1.0f);
                    return;
                }
                SeslChip seslChip = (SeslChip) view;
                seslChip.setInternalsAlpha(255);
                seslChip.chipDrawable.setAlpha(255);
                seslChip.chipDrawable.isSeslFullText = false;
                seslChip.invalidate();
            }
        });
        layoutTransition.setAnimator(2, ofFloat);
        SeslValueAnimator ofFloat2 = SeslValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat2.setDuration(j);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.chip.SeslChipGroup$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i2) {
                    case 0:
                        int i4 = SeslChipGroup.sChipInitialWidth;
                        View view = (View) ((SeslChipGroup.SeslValueAnimator) valueAnimator).mTargetView.get();
                        if (view != null) {
                            if (!(view instanceof SeslChip)) {
                                view.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                                break;
                            } else {
                                SeslChip seslChip = (SeslChip) view;
                                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                seslChip.setRight(seslChip.getLeft() + SeslChipGroup.sChipInitialWidth + ((int) ((seslChip.chipDrawable.getIntrinsicWidth() - SeslChipGroup.sChipInitialWidth) * floatValue)));
                                seslChip.setBottom(((int) seslChip.chipDrawable.chipMinHeight) + seslChip.getTop());
                                seslChip.setInternalsAlpha((int) (MathUtils.clamp((((int) valueAnimator.getCurrentPlayTime()) - 100) / 200.0f, 0.0f, 1.0f) * 255.0f));
                                seslChip.chipDrawable.setAlpha((int) (floatValue * 255.0f));
                                ChipDrawable chipDrawable = seslChip.chipDrawable;
                                if (chipDrawable != null && chipDrawable.textEndPadding != 0.0f) {
                                    chipDrawable.textEndPadding = 0.0f;
                                    chipDrawable.invalidateSelf();
                                    chipDrawable.onSizeChange();
                                }
                                seslChip.setEllipsize(null);
                                seslChip.chipDrawable.isSeslFullText = true;
                                seslChip.invalidate();
                                break;
                            }
                        }
                        break;
                    default:
                        int i5 = SeslChipGroup.sChipInitialWidth;
                        View view2 = (View) ((SeslChipGroup.SeslValueAnimator) valueAnimator).mTargetView.get();
                        if (view2 != null) {
                            if (!(view2 instanceof SeslChip)) {
                                view2.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                                break;
                            } else {
                                SeslChip seslChip2 = (SeslChip) view2;
                                float floatValue2 = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                seslChip2.setRight(seslChip2.getLeft() + ((int) (((seslChip2.chipDrawable.getIntrinsicWidth() - SeslChipGroup.sChipInitialWidth) * floatValue2) + SeslChipGroup.sChipInitialWidth)));
                                seslChip2.setBottom(((int) seslChip2.chipDrawable.chipMinHeight) + seslChip2.getTop());
                                seslChip2.setInternalsAlpha((int) (MathUtils.clamp(1.0f - (((int) valueAnimator.getCurrentPlayTime()) / 200.0f), 0.0f, 1.0f) * 255.0f));
                                seslChip2.chipDrawable.setAlpha((int) (floatValue2 * 255.0f));
                                ChipDrawable chipDrawable2 = seslChip2.chipDrawable;
                                if (chipDrawable2 != null && chipDrawable2.textEndPadding != 0.0f) {
                                    chipDrawable2.textEndPadding = 0.0f;
                                    chipDrawable2.invalidateSelf();
                                    chipDrawable2.onSizeChange();
                                }
                                seslChip2.setEllipsize(null);
                                seslChip2.chipDrawable.isSeslFullText = true;
                                seslChip2.invalidate();
                                break;
                            }
                        }
                        break;
                }
            }
        });
        ofFloat2.addListener(new AnimatorListenerAdapter(this) { // from class: com.google.android.material.chip.SeslChipGroup.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                View view = (View) ((SeslValueAnimator) animator).mTargetView.get();
                if (view == null) {
                    return;
                }
                if (!(view instanceof SeslChip)) {
                    view.setAlpha(1.0f);
                    return;
                }
                SeslChip seslChip = (SeslChip) view;
                seslChip.setInternalsAlpha(255);
                seslChip.chipDrawable.setAlpha(255);
                seslChip.chipDrawable.isSeslFullText = false;
                seslChip.invalidate();
            }
        });
        layoutTransition.setAnimator(3, ofFloat2);
        Interpolator loadInterpolator = AnimationUtils.loadInterpolator(getContext(), R.interpolator.sesl_chip_default_interpolator);
        layoutTransition.setInterpolator(3, loadInterpolator);
        layoutTransition.setInterpolator(2, loadInterpolator);
        layoutTransition.setInterpolator(4, loadInterpolator);
        layoutTransition.setInterpolator(0, loadInterpolator);
        layoutTransition.setInterpolator(1, loadInterpolator);
        layoutTransition.addTransitionListener(new LayoutTransition.TransitionListener(this) { // from class: com.google.android.material.chip.SeslChipGroup.2
            @Override // android.animation.LayoutTransition.TransitionListener
            public final void endTransition(LayoutTransition layoutTransition2, ViewGroup viewGroup, View view, int i4) {
                if (view instanceof SeslChip) {
                    SeslChip seslChip = (SeslChip) view;
                    if (i4 == 2 || i4 == 3) {
                        seslChip.chipDrawable.isSeslFullText = false;
                        seslChip.setEllipsize(TextUtils.TruncateAt.END);
                    }
                }
            }

            @Override // android.animation.LayoutTransition.TransitionListener
            public final void startTransition(LayoutTransition layoutTransition2, ViewGroup viewGroup, View view, int i4) {
                if (view instanceof SeslChip) {
                    SeslChip seslChip = (SeslChip) view;
                    if (i4 == 2 || i4 == 3) {
                        ChipDrawable chipDrawable = seslChip.chipDrawable;
                        if (chipDrawable != null && chipDrawable.textEndPadding != 0.0f) {
                            chipDrawable.textEndPadding = 0.0f;
                            chipDrawable.invalidateSelf();
                            chipDrawable.onSizeChange();
                        }
                        seslChip.setEllipsize(null);
                        seslChip.chipDrawable.seslFinalWidth = r0.getIntrinsicWidth();
                        seslChip.chipDrawable.isSeslFullText = true;
                    }
                }
            }
        });
        setLayoutTransition(null);
    }
}
