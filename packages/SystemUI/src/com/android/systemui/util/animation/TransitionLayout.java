package com.android.systemui.util.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import com.android.systemui.statusbar.CrossFadeHelper;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TransitionLayout extends ConstraintLayout implements LaunchableView {
    public static final int $stable = 8;
    private final Rect boundsRect;
    private TransitionViewState currentState;
    private final LaunchableViewDelegate delegate;
    private int desiredMeasureHeight;
    private int desiredMeasureWidth;
    private boolean isPreDrawApplicatorRegistered;
    private boolean measureAsConstraint;
    private TransitionViewState measureState;
    private final Set<Integer> originalGoneChildrenSet;
    private final Map<Integer, Float> originalViewAlphas;
    private final TransitionLayout$preDrawApplicator$1 preDrawApplicator;
    private boolean updateScheduled;

    public TransitionLayout(Context context) {
        this(context, null, 0, 6, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void applyCurrentState() {
        int childCount = getChildCount();
        int i = (int) this.currentState.getContentTranslation().x;
        int i2 = (int) this.currentState.getContentTranslation().y;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            WidgetState widgetState = this.currentState.getWidgetStates().get(Integer.valueOf(childAt.getId()));
            if (widgetState != null) {
                Integer valueOf = (!(childAt instanceof TextView) || widgetState.getWidth() >= widgetState.getMeasureWidth()) ? null : Integer.valueOf(((TextView) childAt).getLayout().getParagraphDirection(0) == -1 ? widgetState.getMeasureWidth() - widgetState.getWidth() : 0);
                if (childAt.getMeasuredWidth() != widgetState.getMeasureWidth() || childAt.getMeasuredHeight() != widgetState.getMeasureHeight()) {
                    childAt.measure(View.MeasureSpec.makeMeasureSpec(widgetState.getMeasureWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(widgetState.getMeasureHeight(), 1073741824));
                    childAt.layout(0, 0, childAt.getMeasuredWidth(), childAt.getMeasuredHeight());
                }
                int intValue = valueOf != null ? valueOf.intValue() : 0;
                int x = (((int) widgetState.getX()) + i) - intValue;
                int y = ((int) widgetState.getY()) + i2;
                boolean z = valueOf != null;
                childAt.setLeftTopRightBottom(x, y, (z ? widgetState.getMeasureWidth() : widgetState.getWidth()) + x, (z ? widgetState.getMeasureHeight() : widgetState.getHeight()) + y);
                childAt.setScaleX(widgetState.getScale());
                childAt.setScaleY(widgetState.getScale());
                Rect clipBounds = childAt.getClipBounds();
                if (clipBounds == null) {
                    clipBounds = new Rect();
                }
                clipBounds.set(intValue, 0, widgetState.getWidth() + intValue, widgetState.getHeight());
                childAt.setClipBounds(clipBounds);
                CrossFadeHelper.fadeIn(childAt, widgetState.getAlpha(), false);
                childAt.setVisibility((widgetState.getGone() || widgetState.getAlpha() == 0.0f) ? 4 : 0);
            }
        }
        updateBounds();
        setTranslationX(this.currentState.getTranslation().x);
        setTranslationY(this.currentState.getTranslation().y);
        CrossFadeHelper.fadeIn((View) this, this.currentState.getAlpha(), false);
    }

    private final void applyCurrentStateOnPredraw() {
        if (this.updateScheduled) {
            return;
        }
        this.updateScheduled = true;
        if (this.isPreDrawApplicatorRegistered) {
            return;
        }
        getViewTreeObserver().addOnPreDrawListener(this.preDrawApplicator);
        this.isPreDrawApplicatorRegistered = true;
    }

    private final void applySetToFullLayout(ConstraintSet constraintSet) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (this.originalGoneChildrenSet.contains(Integer.valueOf(childAt.getId()))) {
                childAt.setVisibility(8);
            }
            Float f = this.originalViewAlphas.get(Integer.valueOf(childAt.getId()));
            childAt.setAlpha(f != null ? f.floatValue() : 1.0f);
        }
        constraintSet.applyTo(this);
    }

    public static /* synthetic */ TransitionViewState calculateViewState$default(TransitionLayout transitionLayout, MeasurementInput measurementInput, ConstraintSet constraintSet, TransitionViewState transitionViewState, int i, Object obj) {
        if ((i & 4) != 0) {
            transitionViewState = null;
        }
        return transitionLayout.calculateViewState(measurementInput, constraintSet, transitionViewState);
    }

    private final void ensureViewsNotGone() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            WidgetState widgetState = this.currentState.getWidgetStates().get(Integer.valueOf(childAt.getId()));
            childAt.setVisibility((widgetState == null || widgetState.getGone()) ? 4 : 0);
        }
    }

    private final void updateBounds() {
        int left = getLeft();
        int top = getTop();
        setLeftTopRightBottom(left, top, this.currentState.getWidth() + left, this.currentState.getHeight() + top);
        this.boundsRect.set(0, 0, getWidth(), getHeight());
    }

    public final TransitionViewState calculateViewState(MeasurementInput measurementInput, ConstraintSet constraintSet, TransitionViewState transitionViewState) {
        if (transitionViewState == null) {
            transitionViewState = new TransitionViewState();
        }
        applySetToFullLayout(constraintSet);
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        this.measureAsConstraint = true;
        measure(measurementInput.getWidthMeasureSpec(), measurementInput.getHeightMeasureSpec());
        int left = getLeft();
        int top = getTop();
        layout(left, top, getMeasuredWidth() + left, getMeasuredHeight() + top);
        this.measureAsConstraint = false;
        transitionViewState.initFromLayout(this);
        ensureViewsNotGone();
        setMeasuredDimension(measuredWidth, measuredHeight);
        applyCurrentStateOnPredraw();
        return transitionViewState;
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.clipRect(this.boundsRect);
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    public final TransitionViewState getMeasureState() {
        return this.measureState;
    }

    @Override // com.android.systemui.animation.LaunchableView
    public Rect getPaddingForLaunchAnimation() {
        return new Rect();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.isPreDrawApplicatorRegistered) {
            getViewTreeObserver().removeOnPreDrawListener(this.preDrawApplicator);
            this.isPreDrawApplicatorRegistered = false;
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getId() == -1) {
                childAt.setId(i);
            }
            if (childAt.getVisibility() == 8) {
                this.originalGoneChildrenSet.add(Integer.valueOf(childAt.getId()));
            }
            this.originalViewAlphas.put(Integer.valueOf(childAt.getId()), Float.valueOf(childAt.getAlpha()));
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.measureAsConstraint) {
            super.onLayout(z, getLeft(), getTop(), getRight(), getBottom());
            return;
        }
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            childAt.layout(0, 0, childAt.getMeasuredWidth(), childAt.getMeasuredHeight());
        }
        applyCurrentState();
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.View
    public void onMeasure(int i, int i2) {
        if (this.measureAsConstraint) {
            super.onMeasure(i, i2);
            return;
        }
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            WidgetState widgetState = this.currentState.getWidgetStates().get(Integer.valueOf(childAt.getId()));
            if (widgetState != null) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(widgetState.getMeasureWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(widgetState.getMeasureHeight(), 1073741824));
            }
        }
        setMeasuredDimension(this.desiredMeasureWidth, this.desiredMeasureHeight);
    }

    public final void setMeasureState(TransitionViewState transitionViewState) {
        int measureWidth = transitionViewState.getMeasureWidth();
        int measureHeight = transitionViewState.getMeasureHeight();
        if (measureWidth == this.desiredMeasureWidth && measureHeight == this.desiredMeasureHeight) {
            return;
        }
        this.desiredMeasureWidth = measureWidth;
        this.desiredMeasureHeight = measureHeight;
        if (isInLayout()) {
            forceLayout();
        } else {
            requestLayout();
        }
    }

    @Override // com.android.systemui.animation.LaunchableView
    public void setShouldBlockVisibilityChanges(boolean z) {
        this.delegate.setShouldBlockVisibilityChanges(z);
    }

    public final void setState(TransitionViewState transitionViewState) {
        this.currentState = transitionViewState;
        applyCurrentState();
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        this.delegate.setVisibility(i);
    }

    public TransitionLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ TransitionLayout(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.util.animation.TransitionLayout$preDrawApplicator$1] */
    public TransitionLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.boundsRect = new Rect();
        this.originalGoneChildrenSet = new LinkedHashSet();
        this.originalViewAlphas = new LinkedHashMap();
        this.currentState = new TransitionViewState();
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.util.animation.TransitionLayout$delegate$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke(((Number) obj).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i2) {
                super/*android.view.ViewGroup*/.setVisibility(i2);
            }
        });
        this.measureState = new TransitionViewState();
        this.preDrawApplicator = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.util.animation.TransitionLayout$preDrawApplicator$1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                TransitionLayout.this.updateScheduled = false;
                TransitionLayout.this.getViewTreeObserver().removeOnPreDrawListener(this);
                TransitionLayout.this.isPreDrawApplicatorRegistered = false;
                TransitionLayout.this.applyCurrentState();
                return true;
            }
        };
    }

    @Override // com.android.systemui.animation.LaunchableView
    public /* bridge */ /* synthetic */ void onActivityLaunchAnimationEnd() {
    }
}
