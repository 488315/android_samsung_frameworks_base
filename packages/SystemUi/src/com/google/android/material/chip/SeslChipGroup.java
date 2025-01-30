package com.google.android.material.chip;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import androidx.core.math.MathUtils;
import androidx.core.text.TextUtilsCompat;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import com.google.android.material.chip.SeslChipGroup;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SeslChipGroup extends ChipGroup {
    public static int sChipInitialWidth;
    public SeslPeoplePicker$$ExternalSyntheticLambda1 mChipAddListener;
    public int mChipMaxWidth;
    public SeslPeoplePicker$$ExternalSyntheticLambda1 mChipRemoveListener;
    public final boolean mDynamicChipTextTruncation;
    public int mEmptyContainerHeight;
    public final LayoutTransition mLayoutTransition;
    public int mRowCount;

    public SeslChipGroup(Context context) {
        this(context, null);
    }

    public final void addRemoveAnim() {
        final int height = getHeight();
        int internalHeight = getInternalHeight(getWidth());
        if (height != internalHeight) {
            boolean z = this.singleLine;
            if (!z || (z && getChildCount() == 0)) {
                final int i = internalHeight - height;
                if (Math.abs(i) < getContext().getResources().getDimension(R.dimen.chip_height)) {
                    return;
                }
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                ofFloat.setDuration(getContext().getResources().getInteger(R.integer.sesl_chip_default_anim_duration));
                ofFloat.setInterpolator(AnimationUtils.loadInterpolator(getContext(), R.interpolator.sesl_chip_default_interpolator));
                ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.chip.SeslChipGroup.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        ViewGroup.LayoutParams layoutParams = SeslChipGroup.this.getLayoutParams();
                        layoutParams.height = -2;
                        SeslChipGroup seslChipGroup = SeslChipGroup.this;
                        seslChipGroup.mEmptyContainerHeight = 0;
                        seslChipGroup.setLayoutParams(layoutParams);
                    }
                });
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.chip.SeslChipGroup$$ExternalSyntheticLambda2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        SeslChipGroup seslChipGroup = SeslChipGroup.this;
                        int i2 = height;
                        int i3 = i;
                        int i4 = SeslChipGroup.sChipInitialWidth;
                        ViewGroup.LayoutParams layoutParams = seslChipGroup.getLayoutParams();
                        int floatValue = i2 + ((int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * i3));
                        layoutParams.height = floatValue;
                        seslChipGroup.mEmptyContainerHeight = floatValue;
                        seslChipGroup.setLayoutParams(layoutParams);
                    }
                });
                ofFloat.start();
                return;
            }
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = -2;
        this.mEmptyContainerHeight = 0;
        setLayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        super.addView(view instanceof SeslChip ? (SeslChip) view : view, i, layoutParams);
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
            SeslPeoplePicker$$ExternalSyntheticLambda1 seslPeoplePicker$$ExternalSyntheticLambda1 = this.mChipAddListener;
            if (seslPeoplePicker$$ExternalSyntheticLambda1 != null) {
                final SeslPeoplePicker seslPeoplePicker = seslPeoplePicker$$ExternalSyntheticLambda1.f$0;
                boolean z = seslPeoplePicker.mInitialized;
                Context context = seslPeoplePicker$$ExternalSyntheticLambda1.f$1;
                int i3 = 1;
                int i4 = 0;
                if (!z) {
                    seslPeoplePicker.mInitialized = true;
                    SeslExpansionButton seslExpansionButton = seslPeoplePicker.mContainer.mExpansionButton;
                    RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) seslExpansionButton.getLayoutParams();
                    int dimension = (int) context.getResources().getDimension(R.dimen.expansion_button_margin_top);
                    int dimension2 = (int) context.getResources().getDimension(R.dimen.expansion_button_margin_right);
                    if (seslPeoplePicker.mIsRtl) {
                        layoutParams2.setMargins(dimension2, dimension, 0, 0);
                    } else {
                        layoutParams2.setMargins(0, dimension, dimension2, 0);
                    }
                    seslExpansionButton.setLayoutParams(layoutParams2);
                }
                if (seslPeoplePicker.mChipGroup.getChildCount() == 1) {
                    ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                    ofFloat.setDuration(context.getResources().getInteger(R.integer.sesl_chip_default_anim_duration));
                    ofFloat.setInterpolator(AnimationUtils.loadInterpolator(context, R.interpolator.sesl_chip_default_interpolator));
                    ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.chip.SeslPeoplePicker.2
                        public C42552() {
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            ViewGroup.LayoutParams layoutParams3 = SeslPeoplePicker.this.mContainer.getLayoutParams();
                            layoutParams3.height = -2;
                            SeslPeoplePicker.this.mContainer.setLayoutParams(layoutParams3);
                        }
                    });
                    ofFloat.addUpdateListener(new SeslPeoplePicker$$ExternalSyntheticLambda3(seslPeoplePicker, context.getResources().getDimension(R.dimen.chip_height) + seslPeoplePicker.mChipGroup.getPaddingTop() + seslPeoplePicker.mChipGroup.getPaddingBottom(), i3));
                    ofFloat.start();
                }
                if (seslPeoplePicker.mContainer.mExpanded) {
                    seslPeoplePicker.mChipGroup.post(new SeslPeoplePicker$$ExternalSyntheticLambda2(seslPeoplePicker, i4));
                } else {
                    seslPeoplePicker.post(new SeslPeoplePicker$$ExternalSyntheticLambda2(seslPeoplePicker, i3));
                }
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
            float f2 = width + intrinsicWidth;
            int i5 = intrinsicWidth + i2;
            if (f2 < f) {
                i = i5 + width;
            } else {
                i = i5 + paddingStart + paddingEnd;
                i3++;
            }
            width = i;
        }
        int i6 = this.chipSpacingVertical;
        return (getPaddingTop() + (getPaddingBottom() + ((getChildAt(0).getHeight() + i6) * i3))) - i6;
    }

    @Override // com.google.android.material.internal.FlowLayout
    public final int getRowCount() {
        return this.mRowCount;
    }

    public final int getTotalWidth() {
        int paddingEnd = getPaddingEnd() + getPaddingStart();
        int childCount = getChildCount();
        if (childCount <= 0) {
            return paddingEnd;
        }
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            paddingEnd += childAt instanceof SeslChip ? ((SeslChip) childAt).chipDrawable.getIntrinsicWidth() : childAt.getWidth();
        }
        if (childCount > 1) {
            return paddingEnd + ((childCount - 2) * this.chipSpacingHorizontal);
        }
        return paddingEnd;
    }

    @Override // com.google.android.material.internal.FlowLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        if (getChildCount() == 0) {
            this.mRowCount = 0;
            return;
        }
        this.mRowCount = 1;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        boolean z2 = ViewCompat.Api17Impl.getLayoutDirection(this) == 1;
        int paddingRight = z2 ? getPaddingRight() : getPaddingLeft();
        int paddingLeft = z2 ? getPaddingLeft() : getPaddingRight();
        int paddingTop = getPaddingTop();
        int i8 = this.lineSpacing;
        int i9 = this.itemSpacing;
        int i10 = i3 - i;
        int i11 = i10 - paddingLeft;
        if (!z2) {
            i10 = i11;
        }
        int i12 = paddingRight;
        int i13 = paddingTop;
        for (int i14 = 0; i14 < getChildCount(); i14++) {
            View childAt = getChildAt(i14);
            if (childAt.getVisibility() == 8) {
                childAt.setTag(R.id.row_index_key, -1);
            } else {
                ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                    i6 = marginLayoutParams.getMarginStart();
                    i5 = marginLayoutParams.getMarginEnd();
                } else {
                    i5 = 0;
                    i6 = 0;
                }
                int measuredWidth = childAt.getMeasuredWidth() + i12 + i6;
                if (this.singleLine || measuredWidth <= i11) {
                    i7 = 1;
                } else {
                    i13 = paddingTop + i8;
                    i7 = 1;
                    this.mRowCount++;
                    i12 = paddingRight;
                }
                childAt.setTag(R.id.row_index_key, Integer.valueOf(this.mRowCount - i7));
                int i15 = i12 + i6;
                int measuredWidth2 = childAt.getMeasuredWidth() + i15;
                int measuredHeight = childAt.getMeasuredHeight() + i13;
                if (z2) {
                    childAt.layout(i10 - measuredWidth2, i13, (i10 - i12) - i6, measuredHeight);
                } else {
                    childAt.layout(i15, i13, measuredWidth2, measuredHeight);
                }
                i12 += childAt.getMeasuredWidth() + i6 + i5 + i9;
                paddingTop = measuredHeight;
            }
        }
    }

    @Override // com.google.android.material.internal.FlowLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (getChildCount() <= 0) {
            setMeasuredDimension(getWidth(), this.mEmptyContainerHeight);
        }
    }

    public final void postRemoveView() {
        if (this.mChipRemoveListener != null) {
            post(new Runnable() { // from class: com.google.android.material.chip.SeslChipGroup$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SeslPeoplePicker$$ExternalSyntheticLambda1 seslPeoplePicker$$ExternalSyntheticLambda1 = SeslChipGroup.this.mChipRemoveListener;
                    SeslPeoplePicker seslPeoplePicker = seslPeoplePicker$$ExternalSyntheticLambda1.f$0;
                    SeslExpandableContainer seslExpandableContainer = seslPeoplePicker.mContainer;
                    if (seslExpandableContainer.mExpanded) {
                        seslExpandableContainer.post(new SeslPeoplePicker$$ExternalSyntheticLambda2(seslPeoplePicker, 2));
                    }
                    if (seslPeoplePicker.mChipGroup.getChildCount() == 0) {
                        seslPeoplePicker.mContainer.mExpansionButton.setVisibility(8);
                        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
                        Context context = seslPeoplePicker$$ExternalSyntheticLambda1.f$1;
                        ofFloat.setDuration(context.getResources().getInteger(R.integer.sesl_chip_default_anim_duration));
                        ofFloat.setInterpolator(AnimationUtils.loadInterpolator(context, R.interpolator.sesl_chip_default_interpolator));
                        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.chip.SeslPeoplePicker.3
                            public C42563() {
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                super.onAnimationEnd(animator);
                                ViewGroup.LayoutParams layoutParams = SeslPeoplePicker.this.mContainer.getLayoutParams();
                                layoutParams.height = -2;
                                SeslPeoplePicker.this.mContainer.setLayoutParams(layoutParams);
                            }
                        });
                        ofFloat.addUpdateListener(new SeslPeoplePicker$$ExternalSyntheticLambda3(seslPeoplePicker, seslPeoplePicker.mContainer.getHeight(), 0));
                        ofFloat.start();
                    }
                }
            });
        }
    }

    @Override // android.view.ViewGroup
    public final void removeAllViews() {
        setStaticHeight();
        super.removeAllViews();
        addRemoveAnim();
        postRemoveView();
    }

    @Override // android.view.ViewGroup
    public final void removeAllViewsInLayout() {
        setStaticHeight();
        super.removeAllViewsInLayout();
        addRemoveAnim();
        postRemoveView();
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public final void removeView(View view) {
        setStaticHeight();
        super.removeView(view);
        addRemoveAnim();
        postRemoveView();
    }

    @Override // android.view.ViewGroup
    public final void removeViewAt(int i) {
        setStaticHeight();
        super.removeViewAt(i);
        addRemoveAnim();
        postRemoveView();
    }

    @Override // android.view.ViewGroup
    public final void removeViewInLayout(View view) {
        setStaticHeight();
        super.removeViewInLayout(view);
        addRemoveAnim();
        postRemoveView();
    }

    @Override // android.view.ViewGroup
    public final void removeViews(int i, int i2) {
        setStaticHeight();
        super.removeViews(i, i2);
        addRemoveAnim();
        postRemoveView();
    }

    @Override // android.view.ViewGroup
    public final void removeViewsInLayout(int i, int i2) {
        setStaticHeight();
        super.removeViewsInLayout(i, i2);
        addRemoveAnim();
        postRemoveView();
    }

    public final void setStaticHeight() {
        this.mEmptyContainerHeight = getHeight();
    }

    public SeslChipGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.chipGroupStyle);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SeslValueAnimator extends ValueAnimator {
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
            if (arrayList != null) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ofFloat.addUpdateListener((ValueAnimator.AnimatorUpdateListener) it.next());
                }
            }
            ArrayList arrayList2 = this.mSeslListeners;
            if (arrayList2 != null) {
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    ofFloat.addListener((Animator.AnimatorListener) it2.next());
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
        Locale locale = Locale.getDefault();
        int i4 = TextUtilsCompat.$r8$clinit;
        setLayoutDirection(TextUtils.getLayoutDirectionFromLocale(locale));
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
                        int i5 = SeslChipGroup.sChipInitialWidth;
                        View view = (View) ((SeslChipGroup.SeslValueAnimator) valueAnimator).mTargetView.get();
                        if (view != null) {
                            if (!(view instanceof SeslChip)) {
                                view.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                                break;
                            } else {
                                SeslChip seslChip = (SeslChip) view;
                                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                seslChip.setRight(seslChip.getLeft() + SeslChipGroup.sChipInitialWidth + ((int) ((seslChip.chipDrawable.getIntrinsicWidth() - SeslChipGroup.sChipInitialWidth) * floatValue)));
                                seslChip.setBottom(seslChip.getTop() + ((int) seslChip.chipDrawable.chipMinHeight));
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
                        int i6 = SeslChipGroup.sChipInitialWidth;
                        View view2 = (View) ((SeslChipGroup.SeslValueAnimator) valueAnimator).mTargetView.get();
                        if (view2 != null) {
                            if (!(view2 instanceof SeslChip)) {
                                view2.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                                break;
                            } else {
                                SeslChip seslChip2 = (SeslChip) view2;
                                float floatValue2 = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                seslChip2.setRight(seslChip2.getLeft() + ((int) (((seslChip2.chipDrawable.getIntrinsicWidth() - SeslChipGroup.sChipInitialWidth) * floatValue2) + SeslChipGroup.sChipInitialWidth)));
                                seslChip2.setBottom(seslChip2.getTop() + ((int) seslChip2.chipDrawable.chipMinHeight));
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
                        int i5 = SeslChipGroup.sChipInitialWidth;
                        View view = (View) ((SeslChipGroup.SeslValueAnimator) valueAnimator).mTargetView.get();
                        if (view != null) {
                            if (!(view instanceof SeslChip)) {
                                view.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                                break;
                            } else {
                                SeslChip seslChip = (SeslChip) view;
                                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                seslChip.setRight(seslChip.getLeft() + SeslChipGroup.sChipInitialWidth + ((int) ((seslChip.chipDrawable.getIntrinsicWidth() - SeslChipGroup.sChipInitialWidth) * floatValue)));
                                seslChip.setBottom(seslChip.getTop() + ((int) seslChip.chipDrawable.chipMinHeight));
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
                        int i6 = SeslChipGroup.sChipInitialWidth;
                        View view2 = (View) ((SeslChipGroup.SeslValueAnimator) valueAnimator).mTargetView.get();
                        if (view2 != null) {
                            if (!(view2 instanceof SeslChip)) {
                                view2.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                                break;
                            } else {
                                SeslChip seslChip2 = (SeslChip) view2;
                                float floatValue2 = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                seslChip2.setRight(seslChip2.getLeft() + ((int) (((seslChip2.chipDrawable.getIntrinsicWidth() - SeslChipGroup.sChipInitialWidth) * floatValue2) + SeslChipGroup.sChipInitialWidth)));
                                seslChip2.setBottom(seslChip2.getTop() + ((int) seslChip2.chipDrawable.chipMinHeight));
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
        TimeInterpolator loadInterpolator = AnimationUtils.loadInterpolator(getContext(), R.interpolator.sesl_chip_default_interpolator);
        layoutTransition.setInterpolator(3, loadInterpolator);
        layoutTransition.setInterpolator(2, loadInterpolator);
        layoutTransition.setInterpolator(4, loadInterpolator);
        layoutTransition.setInterpolator(0, loadInterpolator);
        layoutTransition.setInterpolator(1, loadInterpolator);
        layoutTransition.addTransitionListener(new LayoutTransition.TransitionListener(this) { // from class: com.google.android.material.chip.SeslChipGroup.2
            @Override // android.animation.LayoutTransition.TransitionListener
            public final void endTransition(LayoutTransition layoutTransition2, ViewGroup viewGroup, View view, int i5) {
                if (view instanceof SeslChip) {
                    SeslChip seslChip = (SeslChip) view;
                    if (i5 == 2 || i5 == 3) {
                        seslChip.chipDrawable.isSeslFullText = false;
                        seslChip.setEllipsize(TextUtils.TruncateAt.END);
                    }
                }
            }

            @Override // android.animation.LayoutTransition.TransitionListener
            public final void startTransition(LayoutTransition layoutTransition2, ViewGroup viewGroup, View view, int i5) {
                if (view instanceof SeslChip) {
                    SeslChip seslChip = (SeslChip) view;
                    if (i5 == 2 || i5 == 3) {
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
        setLayoutTransition(layoutTransition);
    }
}
