package com.android.systemui.qs;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;
import com.android.systemui.QpRune;
import com.android.systemui.log.QuickPanelLogger;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.animator.QsAnimatorState;
import com.android.systemui.shade.SecPanelSplitHelper;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class NonInterceptingScrollView extends ScrollView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public float mDownY;
    public final StringBuilder mQuickPanelLogBuilder;
    public final QuickPanelLogger mQuickPanelLogger;
    public boolean mScrollEnabled;
    public final SecNonInterceptingScrollView mSecNonInterceptingScrollView;
    public final int mTouchSlop;

    public static /* synthetic */ Boolean $r8$lambda$uUGrSfrdiiCGUISaKCs0Y4X4VRw(NonInterceptingScrollView nonInterceptingScrollView, MotionEvent motionEvent) {
        return Boolean.valueOf(super.onTouchEvent(motionEvent));
    }

    public NonInterceptingScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mScrollEnabled = true;
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mQuickPanelLogger = new QuickPanelLogger("NISV");
        this.mQuickPanelLogBuilder = new StringBuilder();
        this.mSecNonInterceptingScrollView = new SecNonInterceptingScrollView(new Runnable() { // from class: com.android.systemui.qs.NonInterceptingScrollView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NonInterceptingScrollView nonInterceptingScrollView = NonInterceptingScrollView.this;
                int i = NonInterceptingScrollView.$r8$clinit;
                nonInterceptingScrollView.scrollTo(0, 0);
            }
        }, new Function0() { // from class: com.android.systemui.qs.NonInterceptingScrollView$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Integer.valueOf(NonInterceptingScrollView.this.getScrollRange());
            }
        });
    }

    @Override // android.view.View
    public final boolean canScrollHorizontally(int i) {
        SecNonInterceptingScrollView secNonInterceptingScrollView = this.mSecNonInterceptingScrollView;
        return (secNonInterceptingScrollView == null || secNonInterceptingScrollView.canScroll()) && this.mScrollEnabled && super.canScrollHorizontally(i);
    }

    @Override // android.view.View
    public final boolean canScrollVertically(int i) {
        SecNonInterceptingScrollView secNonInterceptingScrollView = this.mSecNonInterceptingScrollView;
        return (secNonInterceptingScrollView == null || secNonInterceptingScrollView.canScroll()) && this.mScrollEnabled && super.canScrollVertically(i);
    }

    public final int getScrollRange() {
        if (getChildCount() > 0) {
            return Math.max(0, getChildAt(0).getHeight() - ((getHeight() - ((ScrollView) this).mPaddingBottom) - ((ScrollView) this).mPaddingTop));
        }
        return 0;
    }

    @Override // android.widget.ScrollView, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        SecPanelSplitHelper secPanelSplitHelper;
        super.onDetachedFromWindow();
        SecNonInterceptingScrollView secNonInterceptingScrollView = this.mSecNonInterceptingScrollView;
        if (secNonInterceptingScrollView == null || (secPanelSplitHelper = (SecPanelSplitHelper) secNonInterceptingScrollView.panelSplitHelper$delegate.getValue()) == null) {
            return;
        }
        secPanelSplitHelper.removeListener(secNonInterceptingScrollView.panelTransitionStateListener);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        SecPanelSplitHelper secPanelSplitHelper;
        super.onFinishInflate();
        SecNonInterceptingScrollView secNonInterceptingScrollView = this.mSecNonInterceptingScrollView;
        if (secNonInterceptingScrollView != null && (secPanelSplitHelper = (SecPanelSplitHelper) secNonInterceptingScrollView.panelSplitHelper$delegate.getValue()) != null) {
            secPanelSplitHelper.addListener(secNonInterceptingScrollView.panelTransitionStateListener);
        }
        setFocusable(false);
    }

    @Override // android.widget.ScrollView, android.view.View
    public final boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if (!QpRune.QUICK_TABLET && !QpRune.QUICK_PANEL_BLUR_MASSIVE && QsAnimatorState.isDetailPopupShowing) {
            return false;
        }
        if (QpRune.QUICK_PANEL_CODE_FOR_POP_OVER && QsAnimatorState.isDetailShowing) {
            return false;
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    @Override // android.widget.ScrollView, android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        StringBuilder sb;
        QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
        if (quickPanelLogger != null) {
            quickPanelLogger.onInterceptTouchEvent(motionEvent);
        }
        Function0 function0 = this.mSecNonInterceptingScrollView.scrollRange;
        if (((Number) function0.invoke()).intValue() != 0) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                this.mDownY = motionEvent.getY();
            } else if (actionMasked == 2 && ((Number) function0.invoke()).intValue() > 0) {
                float y = ((int) motionEvent.getY()) - this.mDownY;
                if (y >= (-this.mTouchSlop) || canScrollVertically(1)) {
                    double d = y;
                    if (Math.abs(d) > this.mTouchSlop) {
                        QuickPanelLogger quickPanelLogger2 = this.mQuickPanelLogger;
                        if (quickPanelLogger2 != null) {
                            StringBuilder sb2 = this.mQuickPanelLogBuilder;
                            if (sb2 != null) {
                                sb2.setLength(0);
                                sb2.append("abs(yDiff): ");
                                sb2.append(Math.abs(d));
                                sb2.append(" > touchSlop: ");
                                sb2.append(this.mTouchSlop);
                            } else {
                                sb2 = null;
                            }
                            quickPanelLogger2.onInterceptTouchEvent(motionEvent, String.valueOf(sb2), true);
                        }
                        return true;
                    }
                } else {
                    QuickPanelLogger quickPanelLogger3 = this.mQuickPanelLogger;
                    if (quickPanelLogger3 != null && (sb = this.mQuickPanelLogBuilder) != null) {
                        sb.setLength(0);
                        sb.append("yDiff: ");
                        sb.append(y);
                        sb.append(" < -touchSlop: ");
                        quickPanelLogger3.onInterceptTouchEvent(motionEvent, ReorderTile$$ExternalSyntheticOutline0.m(-this.mTouchSlop, " && !canScrollVertically()", sb), false);
                    }
                }
            }
            return super.onInterceptTouchEvent(motionEvent);
        }
        QuickPanelLogger quickPanelLogger4 = this.mQuickPanelLogger;
        if (quickPanelLogger4 != null) {
            quickPanelLogger4.onInterceptTouchEvent(motionEvent, "scrollRange == 0", false);
            return false;
        }
        return false;
    }

    @Override // android.widget.ScrollView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
        if (quickPanelLogger != null) {
            quickPanelLogger.onTouchEvent(motionEvent);
        }
        SecNonInterceptingScrollView secNonInterceptingScrollView = this.mSecNonInterceptingScrollView;
        secNonInterceptingScrollView.getClass();
        if (motionEvent.getAction() == 3) {
            return $r8$lambda$uUGrSfrdiiCGUISaKCs0Y4X4VRw(this, motionEvent).booleanValue();
        }
        if (!QpRune.QUICK_PANEL_CODE_FOR_POP_OVER || !QsAnimatorState.isDetailOpening) {
            if (((Number) secNonInterceptingScrollView.scrollRange.invoke()).intValue() != 0 && ((canScrollVertically(1) || canScrollVertically(-1)) && QsAnimatorState.state != 1)) {
                return $r8$lambda$uUGrSfrdiiCGUISaKCs0Y4X4VRw(this, motionEvent).booleanValue();
            }
            QuickPanelLogger quickPanelLogger2 = this.mQuickPanelLogger;
            if (quickPanelLogger2 != null) {
                quickPanelLogger2.onTouchEvent(motionEvent, "scrollRange == 0", false);
            }
        }
        return false;
    }

    public final void smoothScrollToDescendant(QSTileView qSTileView) {
        Rect rect = new Rect();
        qSTileView.getDrawingRect(rect);
        offsetDescendantRectToMyCoords(qSTileView, rect);
        int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(rect);
        if (computeScrollDeltaToGetChildRectOnScreen != 0) {
            smoothScrollTo(0, computeScrollDeltaToGetChildRectOnScreen);
        }
    }
}
