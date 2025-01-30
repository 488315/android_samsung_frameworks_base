package com.android.systemui.util.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TransitionLayout extends ConstraintLayout implements LaunchableView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Rect boundsRect;
    public final TransitionViewState currentState;
    public final LaunchableViewDelegate delegate;
    public boolean isPreDrawApplicatorRegistered;
    public final Set originalGoneChildrenSet;
    public final Map originalViewAlphas;
    public final TransitionLayout$preDrawApplicator$1 preDrawApplicator;

    public TransitionLayout(Context context) {
        this(context, null, 0, 6, null);
    }

    public final void applyCurrentState() {
        int i;
        int childCount = getChildCount();
        PointF pointF = this.currentState.contentTranslation;
        int i2 = (int) pointF.x;
        int i3 = (int) pointF.y;
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            WidgetState widgetState = (WidgetState) ((LinkedHashMap) this.currentState.widgetStates).get(Integer.valueOf(childAt.getId()));
            if (widgetState != null) {
                boolean z = childAt instanceof TextView;
                int i5 = widgetState.width;
                int i6 = widgetState.measureWidth;
                Integer valueOf = (!z || i5 >= i6) ? null : Integer.valueOf(((TextView) childAt).getLayout().getParagraphDirection(0) == -1 ? i6 - i5 : 0);
                int measuredWidth = childAt.getMeasuredWidth();
                int i7 = widgetState.measureHeight;
                if (measuredWidth != i6 || childAt.getMeasuredHeight() != i7) {
                    childAt.measure(View.MeasureSpec.makeMeasureSpec(i6, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(i7, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
                    childAt.layout(0, 0, childAt.getMeasuredWidth(), childAt.getMeasuredHeight());
                }
                int intValue = valueOf != null ? valueOf.intValue() : 0;
                int i8 = (((int) widgetState.f392x) + i2) - intValue;
                int i9 = ((int) widgetState.f393y) + i3;
                boolean z2 = valueOf != null;
                if (!z2) {
                    i6 = i5;
                }
                int i10 = widgetState.height;
                if (!z2) {
                    i7 = i10;
                }
                childAt.setLeftTopRightBottom(i8, i9, i6 + i8, i7 + i9);
                float f = widgetState.scale;
                childAt.setScaleX(f);
                childAt.setScaleY(f);
                Rect clipBounds = childAt.getClipBounds();
                if (clipBounds == null) {
                    clipBounds = new Rect();
                }
                clipBounds.set(intValue, 0, i5 + intValue, i10);
                childAt.setClipBounds(clipBounds);
                float f2 = widgetState.alpha;
                CrossFadeHelper.fadeIn(childAt, f2, false);
                if (!widgetState.gone) {
                    if (!(f2 == 0.0f)) {
                        i = 0;
                        childAt.setVisibility(i);
                    }
                }
                i = 4;
                childAt.setVisibility(i);
            }
        }
        int left = getLeft();
        int top = getTop();
        this.currentState.getClass();
        this.currentState.getClass();
        setLeftTopRightBottom(left, top, left + 0, top + 0);
        this.boundsRect.set(0, 0, getWidth(), getHeight());
        setTranslationX(this.currentState.translation.x);
        setTranslationY(this.currentState.translation.y);
        CrossFadeHelper.fadeIn((View) this, this.currentState.alpha, false);
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        if (canvas != null) {
            canvas.save();
        }
        if (canvas != null) {
            canvas.clipRect(this.boundsRect);
        }
        super.dispatchDraw(canvas);
        if (canvas != null) {
            canvas.restore();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.isPreDrawApplicatorRegistered) {
            getViewTreeObserver().removeOnPreDrawListener(this.preDrawApplicator);
            this.isPreDrawApplicatorRegistered = false;
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
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
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            childAt.layout(0, 0, childAt.getMeasuredWidth(), childAt.getMeasuredHeight());
        }
        applyCurrentState();
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            WidgetState widgetState = (WidgetState) ((LinkedHashMap) this.currentState.widgetStates).get(Integer.valueOf(childAt.getId()));
            if (widgetState != null) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(widgetState.measureWidth, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(widgetState.measureHeight, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
            }
        }
        setMeasuredDimension(0, 0);
    }

    @Override // com.android.systemui.animation.LaunchableView
    public final void setShouldBlockVisibilityChanges(boolean z) {
        this.delegate.setShouldBlockVisibilityChanges(z);
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
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
            public final Object invoke(Object obj) {
                super/*android.view.ViewGroup*/.setVisibility(((Number) obj).intValue());
                return Unit.INSTANCE;
            }
        });
        new TransitionViewState();
        this.preDrawApplicator = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.util.animation.TransitionLayout$preDrawApplicator$1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                TransitionLayout transitionLayout = TransitionLayout.this;
                int i2 = TransitionLayout.$r8$clinit;
                transitionLayout.getClass();
                TransitionLayout.this.getViewTreeObserver().removeOnPreDrawListener(this);
                TransitionLayout transitionLayout2 = TransitionLayout.this;
                transitionLayout2.isPreDrawApplicatorRegistered = false;
                transitionLayout2.applyCurrentState();
                return true;
            }
        };
    }
}
