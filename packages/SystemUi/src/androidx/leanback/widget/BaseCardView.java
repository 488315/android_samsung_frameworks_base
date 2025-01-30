package androidx.leanback.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import androidx.leanback.R$styleable;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class BaseCardView extends FrameLayout {
    public static final int[] LB_PRESSED_STATE_SET = {R.attr.state_pressed};
    public final int mActivatedAnimDuration;
    public AnimationBase mAnim;
    public final RunnableC02711 mAnimationTrigger;
    public final int mCardType;
    public boolean mDelaySelectedAnim;
    public final ArrayList mExtraViewList;
    public float mInfoAlpha;
    public float mInfoOffset;
    public final ArrayList mInfoViewList;
    public float mInfoVisFraction;
    public final int mInfoVisibility;
    public final ArrayList mMainViewList;
    public int mMeasuredHeight;
    public int mMeasuredWidth;
    public final int mSelectedAnimDuration;
    public final int mSelectedAnimationDelay;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class AnimationBase extends Animation {
        public AnimationBase(BaseCardView baseCardView) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class InfoAlphaAnimation extends AnimationBase {
        public final float mDelta;
        public final float mStartValue;

        public InfoAlphaAnimation(float f, float f2) {
            super(BaseCardView.this);
            this.mStartValue = f;
            this.mDelta = f2 - f;
        }

        @Override // android.view.animation.Animation
        public final void applyTransformation(float f, Transformation transformation) {
            BaseCardView.this.mInfoAlpha = (f * this.mDelta) + this.mStartValue;
            for (int i = 0; i < BaseCardView.this.mInfoViewList.size(); i++) {
                ((View) BaseCardView.this.mInfoViewList.get(i)).setAlpha(BaseCardView.this.mInfoAlpha);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class InfoHeightAnimation extends AnimationBase {
        public final float mDelta;
        public final float mStartValue;

        public InfoHeightAnimation(float f, float f2) {
            super(BaseCardView.this);
            this.mStartValue = f;
            this.mDelta = f2 - f;
        }

        @Override // android.view.animation.Animation
        public final void applyTransformation(float f, Transformation transformation) {
            BaseCardView baseCardView = BaseCardView.this;
            baseCardView.mInfoVisFraction = (f * this.mDelta) + this.mStartValue;
            baseCardView.requestLayout();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class InfoOffsetAnimation extends AnimationBase {
        public final float mDelta;
        public final float mStartValue;

        public InfoOffsetAnimation(float f, float f2) {
            super(BaseCardView.this);
            this.mStartValue = f;
            this.mDelta = f2 - f;
        }

        @Override // android.view.animation.Animation
        public final void applyTransformation(float f, Transformation transformation) {
            BaseCardView baseCardView = BaseCardView.this;
            baseCardView.mInfoOffset = (f * this.mDelta) + this.mStartValue;
            baseCardView.requestLayout();
        }
    }

    public BaseCardView(Context context) {
        this(context, null);
    }

    public final void animateInfoOffset(boolean z) {
        cancelAnimations();
        int i = 0;
        if (z) {
            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mMeasuredWidth, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
            int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
            int i2 = 0;
            for (int i3 = 0; i3 < this.mExtraViewList.size(); i3++) {
                View view = (View) this.mExtraViewList.get(i3);
                view.setVisibility(0);
                view.measure(makeMeasureSpec, makeMeasureSpec2);
                i2 = Math.max(i2, view.getMeasuredHeight());
            }
            i = i2;
        }
        InfoOffsetAnimation infoOffsetAnimation = new InfoOffsetAnimation(this.mInfoOffset, z ? i : 0.0f);
        this.mAnim = infoOffsetAnimation;
        infoOffsetAnimation.setDuration(this.mSelectedAnimDuration);
        this.mAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        this.mAnim.setAnimationListener(new Animation.AnimationListener() { // from class: androidx.leanback.widget.BaseCardView.2
            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationEnd(Animation animation) {
                if (BaseCardView.this.mInfoOffset == 0.0f) {
                    for (int i4 = 0; i4 < BaseCardView.this.mExtraViewList.size(); i4++) {
                        ((View) BaseCardView.this.mExtraViewList.get(i4)).setVisibility(8);
                    }
                }
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationStart(Animation animation) {
            }
        });
        startAnimation(this.mAnim);
    }

    public final void cancelAnimations() {
        AnimationBase animationBase = this.mAnim;
        if (animationBase != null) {
            animationBase.cancel();
            this.mAnim = null;
            clearAnimation();
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final int[] onCreateDrawableState(int i) {
        boolean z = false;
        boolean z2 = false;
        for (int i2 : super.onCreateDrawableState(i)) {
            if (i2 == 16842919) {
                z = true;
            }
            if (i2 == 16842910) {
                z2 = true;
            }
        }
        return (z && z2) ? View.PRESSED_ENABLED_STATE_SET : z ? LB_PRESSED_STATE_SET : z2 ? View.ENABLED_STATE_SET : View.EMPTY_STATE_SET;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.mAnimationTrigger);
        cancelAnimations();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        float paddingTop = getPaddingTop();
        for (int i5 = 0; i5 < this.mMainViewList.size(); i5++) {
            View view = (View) this.mMainViewList.get(i5);
            if (view.getVisibility() != 8) {
                view.layout(getPaddingLeft(), (int) paddingTop, getPaddingLeft() + this.mMeasuredWidth, (int) (view.getMeasuredHeight() + paddingTop));
                paddingTop += view.getMeasuredHeight();
            }
        }
        if (this.mCardType != 0) {
            float f = 0.0f;
            for (int i6 = 0; i6 < this.mInfoViewList.size(); i6++) {
                f += ((View) this.mInfoViewList.get(i6)).getMeasuredHeight();
            }
            int i7 = this.mCardType;
            if (i7 == 1) {
                paddingTop -= f;
                if (paddingTop < 0.0f) {
                    paddingTop = 0.0f;
                }
            } else if (i7 != 2) {
                paddingTop -= this.mInfoOffset;
            } else if (this.mInfoVisibility == 2) {
                f *= this.mInfoVisFraction;
            }
            for (int i8 = 0; i8 < this.mInfoViewList.size(); i8++) {
                View view2 = (View) this.mInfoViewList.get(i8);
                if (view2.getVisibility() != 8) {
                    int measuredHeight = view2.getMeasuredHeight();
                    if (measuredHeight > f) {
                        measuredHeight = (int) f;
                    }
                    float f2 = measuredHeight;
                    paddingTop += f2;
                    view2.layout(getPaddingLeft(), (int) paddingTop, getPaddingLeft() + this.mMeasuredWidth, (int) paddingTop);
                    f -= f2;
                    if (f <= 0.0f) {
                        break;
                    }
                }
            }
            if (this.mCardType == 3) {
                for (int i9 = 0; i9 < this.mExtraViewList.size(); i9++) {
                    View view3 = (View) this.mExtraViewList.get(i9);
                    if (view3.getVisibility() != 8) {
                        view3.layout(getPaddingLeft(), (int) paddingTop, getPaddingLeft() + this.mMeasuredWidth, (int) (view3.getMeasuredHeight() + paddingTop));
                        paddingTop += view3.getMeasuredHeight();
                    }
                }
            }
        }
        onSizeChanged(0, 0, i3 - i, i4 - i2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0034, code lost:
    
        if (r15.mInfoVisFraction > 0.0f) goto L20;
     */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0164  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x009c A[EDGE_INSN: B:45:0x009c->B:46:0x009c BREAK  A[LOOP:0: B:24:0x005d->B:35:0x0099], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0170  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0179  */
    @Override // android.widget.FrameLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMeasure(int i, int i2) {
        boolean z;
        int i3;
        int i4;
        int i5;
        int i6;
        boolean z2;
        boolean z3 = false;
        this.mMeasuredWidth = 0;
        this.mMeasuredHeight = 0;
        this.mMainViewList.clear();
        this.mInfoViewList.clear();
        this.mExtraViewList.clear();
        int childCount = getChildCount();
        int i7 = this.mCardType;
        if (i7 != 0) {
            int i8 = this.mInfoVisibility;
            if (i8 != 0) {
                if (i8 != 1) {
                    if (i8 == 2) {
                        if (i7 != 2) {
                            z2 = isSelected();
                        }
                    }
                    z2 = false;
                } else {
                    z2 = isActivated();
                }
                if (z2) {
                    z = true;
                    boolean z4 = !(this.mCardType != 3) && this.mInfoOffset > 0.0f;
                    i3 = 0;
                    while (true) {
                        if (i3 < childCount) {
                            break;
                        }
                        View childAt = getChildAt(i3);
                        if (childAt != null) {
                            int i9 = ((LayoutParams) childAt.getLayoutParams()).viewType;
                            if (i9 == 1) {
                                childAt.setAlpha(this.mInfoAlpha);
                                this.mInfoViewList.add(childAt);
                                childAt.setVisibility(z ? 0 : 8);
                            } else if (i9 == 2) {
                                this.mExtraViewList.add(childAt);
                                childAt.setVisibility(z4 ? 0 : 8);
                            } else {
                                this.mMainViewList.add(childAt);
                                childAt.setVisibility(0);
                            }
                        }
                        i3++;
                    }
                    int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                    int i10 = 0;
                    int i11 = 0;
                    for (i4 = 0; i4 < this.mMainViewList.size(); i4++) {
                        View view = (View) this.mMainViewList.get(i4);
                        if (view.getVisibility() != 8) {
                            measureChild(view, makeMeasureSpec, makeMeasureSpec);
                            this.mMeasuredWidth = Math.max(this.mMeasuredWidth, view.getMeasuredWidth());
                            i10 += view.getMeasuredHeight();
                            i11 = View.combineMeasuredStates(i11, view.getMeasuredState());
                        }
                    }
                    setPivotX(this.mMeasuredWidth / 2);
                    setPivotY(i10 / 2);
                    int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mMeasuredWidth, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                    if (this.mCardType == 0) {
                        i5 = 0;
                        i6 = 0;
                    } else {
                        i6 = 0;
                        for (int i12 = 0; i12 < this.mInfoViewList.size(); i12++) {
                            View view2 = (View) this.mInfoViewList.get(i12);
                            if (view2.getVisibility() != 8) {
                                measureChild(view2, makeMeasureSpec2, makeMeasureSpec);
                                if (this.mCardType != 1) {
                                    i6 = view2.getMeasuredHeight() + i6;
                                }
                                i11 = View.combineMeasuredStates(i11, view2.getMeasuredState());
                            }
                        }
                        if (this.mCardType == 3) {
                            i5 = 0;
                            for (int i13 = 0; i13 < this.mExtraViewList.size(); i13++) {
                                View view3 = (View) this.mExtraViewList.get(i13);
                                if (view3.getVisibility() != 8) {
                                    measureChild(view3, makeMeasureSpec2, makeMeasureSpec);
                                    i5 += view3.getMeasuredHeight();
                                    i11 = View.combineMeasuredStates(i11, view3.getMeasuredState());
                                }
                            }
                        } else {
                            i5 = 0;
                        }
                    }
                    if ((this.mCardType == 0) && this.mInfoVisibility == 2) {
                        z3 = true;
                    }
                    float f = i10;
                    float f2 = i6;
                    if (z3) {
                        f2 *= this.mInfoVisFraction;
                    }
                    this.mMeasuredHeight = (int) (((f + f2) + i5) - (z3 ? 0.0f : this.mInfoOffset));
                    setMeasuredDimension(View.resolveSizeAndState(getPaddingRight() + getPaddingLeft() + this.mMeasuredWidth, i, i11), View.resolveSizeAndState(getPaddingBottom() + getPaddingTop() + this.mMeasuredHeight, i2, i11 << 16));
                }
            }
            z2 = true;
            if (z2) {
            }
        }
        z = false;
        if (this.mCardType != 3) {
        }
        i3 = 0;
        while (true) {
            if (i3 < childCount) {
            }
            i3++;
        }
        int makeMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(0, 0);
        int i102 = 0;
        int i112 = 0;
        while (i4 < this.mMainViewList.size()) {
        }
        setPivotX(this.mMeasuredWidth / 2);
        setPivotY(i102 / 2);
        int makeMeasureSpec22 = View.MeasureSpec.makeMeasureSpec(this.mMeasuredWidth, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        if (this.mCardType == 0) {
        }
        if (this.mCardType == 0) {
            z3 = true;
        }
        float f3 = i102;
        float f22 = i6;
        if (z3) {
        }
        this.mMeasuredHeight = (int) (((f3 + f22) + i5) - (z3 ? 0.0f : this.mInfoOffset));
        setMeasuredDimension(View.resolveSizeAndState(getPaddingRight() + getPaddingLeft() + this.mMeasuredWidth, i, i112), View.resolveSizeAndState(getPaddingBottom() + getPaddingTop() + this.mMeasuredHeight, i2, i112 << 16));
    }

    @Override // android.view.View
    public final void setActivated(boolean z) {
        int i;
        if (z != isActivated()) {
            super.setActivated(z);
            if ((this.mCardType != 0) && (i = this.mInfoVisibility) == 1) {
                setInfoViewVisibility(i != 0 ? i != 1 ? i != 2 ? false : isSelected() : isActivated() : true);
            }
        }
    }

    public final void setInfoViewVisibility(boolean z) {
        int i = this.mCardType;
        if (i == 3) {
            if (z) {
                for (int i2 = 0; i2 < this.mInfoViewList.size(); i2++) {
                    ((View) this.mInfoViewList.get(i2)).setVisibility(0);
                }
                return;
            }
            for (int i3 = 0; i3 < this.mInfoViewList.size(); i3++) {
                ((View) this.mInfoViewList.get(i3)).setVisibility(8);
            }
            for (int i4 = 0; i4 < this.mExtraViewList.size(); i4++) {
                ((View) this.mExtraViewList.get(i4)).setVisibility(8);
            }
            this.mInfoOffset = 0.0f;
            return;
        }
        if (i != 2) {
            if (i == 1) {
                cancelAnimations();
                if (z) {
                    for (int i5 = 0; i5 < this.mInfoViewList.size(); i5++) {
                        ((View) this.mInfoViewList.get(i5)).setVisibility(0);
                    }
                }
                if ((z ? 1.0f : 0.0f) == this.mInfoAlpha) {
                    return;
                }
                InfoAlphaAnimation infoAlphaAnimation = new InfoAlphaAnimation(this.mInfoAlpha, z ? 1.0f : 0.0f);
                this.mAnim = infoAlphaAnimation;
                infoAlphaAnimation.setDuration(this.mActivatedAnimDuration);
                this.mAnim.setInterpolator(new DecelerateInterpolator());
                this.mAnim.setAnimationListener(new Animation.AnimationListener() { // from class: androidx.leanback.widget.BaseCardView.4
                    @Override // android.view.animation.Animation.AnimationListener
                    public final void onAnimationEnd(Animation animation) {
                        if (BaseCardView.this.mInfoAlpha == 0.0d) {
                            for (int i6 = 0; i6 < BaseCardView.this.mInfoViewList.size(); i6++) {
                                ((View) BaseCardView.this.mInfoViewList.get(i6)).setVisibility(8);
                            }
                        }
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public final void onAnimationRepeat(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public final void onAnimationStart(Animation animation) {
                    }
                });
                startAnimation(this.mAnim);
                return;
            }
            return;
        }
        if (this.mInfoVisibility != 2) {
            for (int i6 = 0; i6 < this.mInfoViewList.size(); i6++) {
                ((View) this.mInfoViewList.get(i6)).setVisibility(z ? 0 : 8);
            }
            return;
        }
        cancelAnimations();
        if (z) {
            for (int i7 = 0; i7 < this.mInfoViewList.size(); i7++) {
                ((View) this.mInfoViewList.get(i7)).setVisibility(0);
            }
        }
        float f = z ? 1.0f : 0.0f;
        if (this.mInfoVisFraction == f) {
            return;
        }
        InfoHeightAnimation infoHeightAnimation = new InfoHeightAnimation(this.mInfoVisFraction, f);
        this.mAnim = infoHeightAnimation;
        infoHeightAnimation.setDuration(this.mSelectedAnimDuration);
        this.mAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        this.mAnim.setAnimationListener(new Animation.AnimationListener() { // from class: androidx.leanback.widget.BaseCardView.3
            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationEnd(Animation animation) {
                if (BaseCardView.this.mInfoVisFraction == 0.0f) {
                    for (int i8 = 0; i8 < BaseCardView.this.mInfoViewList.size(); i8++) {
                        ((View) BaseCardView.this.mInfoViewList.get(i8)).setVisibility(8);
                    }
                }
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationStart(Animation animation) {
            }
        });
        startAnimation(this.mAnim);
    }

    @Override // android.view.View
    public final void setSelected(boolean z) {
        if (z != isSelected()) {
            super.setSelected(z);
            boolean isSelected = isSelected();
            removeCallbacks(this.mAnimationTrigger);
            if (this.mCardType != 3) {
                if (this.mInfoVisibility == 2) {
                    setInfoViewVisibility(isSelected);
                }
            } else if (!isSelected) {
                animateInfoOffset(false);
            } else if (this.mDelaySelectedAnim) {
                postDelayed(this.mAnimationTrigger, this.mSelectedAnimationDelay);
            } else {
                post(this.mAnimationTrigger);
                this.mDelaySelectedAnim = true;
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override // android.view.View
    public final String toString() {
        return super.toString();
    }

    public BaseCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.baseCardViewStyle);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final FrameLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [androidx.leanback.widget.BaseCardView$1] */
    public BaseCardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAnimationTrigger = new Runnable() { // from class: androidx.leanback.widget.BaseCardView.1
            @Override // java.lang.Runnable
            public final void run() {
                BaseCardView.this.animateInfoOffset(true);
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.lbBaseCardView, i, 0);
        try {
            int integer = obtainStyledAttributes.getInteger(3, 0);
            this.mCardType = integer;
            Drawable drawable = obtainStyledAttributes.getDrawable(2);
            if (drawable != null) {
                setForeground(drawable);
            }
            Drawable drawable2 = obtainStyledAttributes.getDrawable(1);
            if (drawable2 != null) {
                setBackground(drawable2);
            }
            int integer2 = obtainStyledAttributes.getInteger(5, 1);
            this.mInfoVisibility = integer2;
            obtainStyledAttributes.getInteger(4, 2);
            this.mSelectedAnimationDelay = obtainStyledAttributes.getInteger(6, getResources().getInteger(com.android.systemui.R.integer.lb_card_selected_animation_delay));
            this.mSelectedAnimDuration = obtainStyledAttributes.getInteger(7, getResources().getInteger(com.android.systemui.R.integer.lb_card_selected_animation_duration));
            this.mActivatedAnimDuration = obtainStyledAttributes.getInteger(0, getResources().getInteger(com.android.systemui.R.integer.lb_card_activated_animation_duration));
            obtainStyledAttributes.recycle();
            this.mDelaySelectedAnim = true;
            this.mMainViewList = new ArrayList();
            this.mInfoViewList = new ArrayList();
            this.mExtraViewList = new ArrayList();
            this.mInfoOffset = 0.0f;
            this.mInfoVisFraction = (integer == 2 && integer2 == 2 && !isSelected()) ? 0.0f : 1.0f;
            this.mInfoAlpha = (integer == 1 && integer2 == 2 && !isSelected()) ? 0.0f : 1.0f;
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class LayoutParams extends FrameLayout.LayoutParams {
        public final int viewType;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.viewType = 0;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.lbBaseCardView_Layout);
            this.viewType = obtainStyledAttributes.getInt(0, 0);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.viewType = 0;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.viewType = 0;
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams);
            this.viewType = 0;
            this.viewType = layoutParams.viewType;
        }
    }
}
