package com.google.android.material.chip;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
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
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration(getContext().getResources().getInteger(R.integer.sesl_chip_default_anim_duration));
        valueAnimatorOfFloat.setInterpolator(AnimationUtils.loadInterpolator(getContext(), R.interpolator.sesl_chip_default_interpolator));
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.chip.SeslChipGroup.1
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
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.chip.SeslChipGroup$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SeslChipGroup seslChipGroup = this.f$0;
                int i = height;
                int i2 = internalHeight;
                int i3 = SeslChipGroup.sChipInitialWidth;
                ViewGroup.LayoutParams layoutParams2 = seslChipGroup.getLayoutParams();
                int iFloatValue = i + ((int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * i2));
                layoutParams2.height = iFloatValue;
                seslChipGroup.mEmptyContainerHeight = iFloatValue;
                seslChipGroup.setLayoutParams(layoutParams2);
            }
        });
        valueAnimatorOfFloat.start();
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
        int marginEnd;
        int marginStart;
        boolean z2 = true;
        int i5 = 0;
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
        int i6 = this.lineSpacing;
        int i7 = this.itemSpacing;
        int i8 = i3 - i;
        int i9 = i8 - paddingLeft;
        if (!z3) {
            i8 = i9;
        }
        int i10 = 0;
        int measuredWidth = paddingRight;
        int i11 = paddingTop;
        while (i10 < getChildCount()) {
            View childAt = getChildAt(i10);
            boolean z4 = z2;
            if (childAt.getVisibility() == 8) {
                childAt.setTag(R.id.row_index_key, -1);
            } else {
                ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                    marginStart = marginLayoutParams.getMarginStart();
                    marginEnd = marginLayoutParams.getMarginEnd();
                } else {
                    marginEnd = i5;
                    marginStart = marginEnd;
                }
                int measuredWidth2 = childAt.getMeasuredWidth() + measuredWidth + marginStart;
                if (!this.singleLine && measuredWidth2 > i9) {
                    i11 = paddingTop + i6;
                    this.mRowCount++;
                    measuredWidth = paddingRight;
                }
                childAt.setTag(R.id.row_index_key, Integer.valueOf(this.mRowCount - 1));
                int i12 = measuredWidth + marginStart;
                int measuredWidth3 = childAt.getMeasuredWidth() + i12;
                paddingTop = childAt.getMeasuredHeight() + i11;
                if (z3) {
                    childAt.layout(i8 - measuredWidth3, i11, (i8 - measuredWidth) - marginStart, paddingTop);
                } else {
                    childAt.layout(i12, i11, measuredWidth3, paddingTop);
                }
                measuredWidth += childAt.getMeasuredWidth() + marginStart + marginEnd + i7;
            }
            i10++;
            z2 = z4;
            i5 = 0;
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
            SeslValueAnimator seslValueAnimatorOfFloat = ofFloat(this.mValues);
            ArrayList arrayList = this.mSeslUpdateListeners;
            int i = 0;
            if (arrayList != null) {
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    seslValueAnimatorOfFloat.addUpdateListener((ValueAnimator.AnimatorUpdateListener) obj);
                }
            }
            ArrayList arrayList2 = this.mSeslListeners;
            if (arrayList2 != null) {
                int size2 = arrayList2.size();
                while (i < size2) {
                    Object obj2 = arrayList2.get(i);
                    i++;
                    seslValueAnimatorOfFloat.addListener((Animator.AnimatorListener) obj2);
                }
            }
            return seslValueAnimatorOfFloat;
        }
    }

    public SeslChipGroup(Context context, AttributeSet attributeSet, int i) throws Resources.NotFoundException {
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
        SeslValueAnimator seslValueAnimatorOfFloat = SeslValueAnimator.ofFloat(0.0f, 1.0f);
        long j = integer;
        seslValueAnimatorOfFloat.setDuration(j);
        seslValueAnimatorOfFloat.setStartDelay(0L);
        seslValueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.chip.SeslChipGroup$$ExternalSyntheticLambda0
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
                                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                seslChip.setRight(seslChip.getLeft() + SeslChipGroup.sChipInitialWidth + ((int) ((seslChip.chipDrawable.getIntrinsicWidth() - SeslChipGroup.sChipInitialWidth) * fFloatValue)));
                                seslChip.setBottom(((int) seslChip.chipDrawable.chipMinHeight) + seslChip.getTop());
                                seslChip.setInternalsAlpha((int) (MathUtils.clamp((((int) valueAnimator.getCurrentPlayTime()) - 100) / 200.0f, 0.0f, 1.0f) * 255.0f));
                                seslChip.chipDrawable.setAlpha((int) (fFloatValue * 255.0f));
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
                                float fFloatValue2 = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                seslChip2.setRight(seslChip2.getLeft() + ((int) (((seslChip2.chipDrawable.getIntrinsicWidth() - SeslChipGroup.sChipInitialWidth) * fFloatValue2) + SeslChipGroup.sChipInitialWidth)));
                                seslChip2.setBottom(((int) seslChip2.chipDrawable.chipMinHeight) + seslChip2.getTop());
                                seslChip2.setInternalsAlpha((int) (MathUtils.clamp(1.0f - (((int) valueAnimator.getCurrentPlayTime()) / 200.0f), 0.0f, 1.0f) * 255.0f));
                                seslChip2.chipDrawable.setAlpha((int) (fFloatValue2 * 255.0f));
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
        seslValueAnimatorOfFloat.addListener(new AnimatorListenerAdapter(this) { // from class: com.google.android.material.chip.SeslChipGroup.3
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
        layoutTransition.setAnimator(2, seslValueAnimatorOfFloat);
        SeslValueAnimator seslValueAnimatorOfFloat2 = SeslValueAnimator.ofFloat(1.0f, 0.0f);
        seslValueAnimatorOfFloat2.setDuration(j);
        seslValueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.chip.SeslChipGroup$$ExternalSyntheticLambda0
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
                                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                seslChip.setRight(seslChip.getLeft() + SeslChipGroup.sChipInitialWidth + ((int) ((seslChip.chipDrawable.getIntrinsicWidth() - SeslChipGroup.sChipInitialWidth) * fFloatValue)));
                                seslChip.setBottom(((int) seslChip.chipDrawable.chipMinHeight) + seslChip.getTop());
                                seslChip.setInternalsAlpha((int) (MathUtils.clamp((((int) valueAnimator.getCurrentPlayTime()) - 100) / 200.0f, 0.0f, 1.0f) * 255.0f));
                                seslChip.chipDrawable.setAlpha((int) (fFloatValue * 255.0f));
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
                                float fFloatValue2 = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                seslChip2.setRight(seslChip2.getLeft() + ((int) (((seslChip2.chipDrawable.getIntrinsicWidth() - SeslChipGroup.sChipInitialWidth) * fFloatValue2) + SeslChipGroup.sChipInitialWidth)));
                                seslChip2.setBottom(((int) seslChip2.chipDrawable.chipMinHeight) + seslChip2.getTop());
                                seslChip2.setInternalsAlpha((int) (MathUtils.clamp(1.0f - (((int) valueAnimator.getCurrentPlayTime()) / 200.0f), 0.0f, 1.0f) * 255.0f));
                                seslChip2.chipDrawable.setAlpha((int) (fFloatValue2 * 255.0f));
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
        seslValueAnimatorOfFloat2.addListener(new AnimatorListenerAdapter(this) { // from class: com.google.android.material.chip.SeslChipGroup.3
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
        layoutTransition.setAnimator(3, seslValueAnimatorOfFloat2);
        Interpolator interpolatorLoadInterpolator = AnimationUtils.loadInterpolator(getContext(), R.interpolator.sesl_chip_default_interpolator);
        layoutTransition.setInterpolator(3, interpolatorLoadInterpolator);
        layoutTransition.setInterpolator(2, interpolatorLoadInterpolator);
        layoutTransition.setInterpolator(4, interpolatorLoadInterpolator);
        layoutTransition.setInterpolator(0, interpolatorLoadInterpolator);
        layoutTransition.setInterpolator(1, interpolatorLoadInterpolator);
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
