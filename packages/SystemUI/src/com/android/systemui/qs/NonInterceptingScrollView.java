package com.android.systemui.qs;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import com.android.systemui.log.QuickPanelLogger;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.shade.SecPanelSplitHelper;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class NonInterceptingScrollView extends ScrollView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public float mDownY;
    public final StringBuilder mQuickPanelLogBuilder;
    public final QuickPanelLogger mQuickPanelLogger;
    public boolean mScrollEnabled;
    public final SecNonInterceptingScrollView mSecNonInterceptingScrollView;
    public final int mTouchSlop;

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

    @Override // android.widget.ScrollView, android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        StringBuilder sb;
        QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
        if (quickPanelLogger != null) {
            quickPanelLogger.onInterceptTouchEvent(motionEvent);
        }
        Function0 function0 = this.mSecNonInterceptingScrollView.scrollRange;
        if (((Number) function0.invoke()).intValue() == 0) {
            QuickPanelLogger quickPanelLogger2 = this.mQuickPanelLogger;
            if (quickPanelLogger2 == null) {
                return false;
            }
            quickPanelLogger2.onInterceptTouchEvent(motionEvent, "scrollRange == 0", false);
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mDownY = Float.valueOf(motionEvent.getY()).floatValue();
        } else if (actionMasked == 2 && ((Number) function0.invoke()).intValue() > 0) {
            float y = ((int) motionEvent.getY()) - Float.valueOf(this.mDownY).floatValue();
            if (y < (-Integer.valueOf(this.mTouchSlop).intValue())) {
                Integer num = 1;
                if (!Boolean.valueOf(canScrollVertically(num.intValue())).booleanValue()) {
                    QuickPanelLogger quickPanelLogger3 = this.mQuickPanelLogger;
                    if (quickPanelLogger3 == null || (sb = this.mQuickPanelLogBuilder) == null) {
                        return false;
                    }
                    sb.setLength(0);
                    sb.append("yDiff: ");
                    sb.append(y);
                    sb.append(" < -touchSlop: ");
                    quickPanelLogger3.onInterceptTouchEvent(motionEvent, Anchor$$ExternalSyntheticOutline0.m(-Integer.valueOf(this.mTouchSlop).intValue(), " && !canScrollVertically()", sb), false);
                    return false;
                }
            }
            double d = y;
            if (Math.abs(d) > Integer.valueOf(this.mTouchSlop).intValue()) {
                QuickPanelLogger quickPanelLogger4 = this.mQuickPanelLogger;
                if (quickPanelLogger4 != null) {
                    StringBuilder sb2 = this.mQuickPanelLogBuilder;
                    if (sb2 != null) {
                        sb2.setLength(0);
                        sb2.append("abs(yDiff): ");
                        sb2.append(Math.abs(d));
                        sb2.append(" > touchSlop: ");
                        sb2.append(Integer.valueOf(this.mTouchSlop).intValue());
                    } else {
                        sb2 = null;
                    }
                    quickPanelLogger4.onInterceptTouchEvent(motionEvent, String.valueOf(sb2), true);
                }
                return true;
            }
        }
        return Boolean.valueOf(super.onInterceptTouchEvent(motionEvent)).booleanValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0043, code lost:
    
        if (java.lang.Boolean.valueOf(canScrollVertically(r1.intValue())).booleanValue() != false) goto L11;
     */
    @Override // android.widget.ScrollView, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onTouchEvent(android.view.MotionEvent r3) {
        /*
            r2 = this;
            com.android.systemui.log.QuickPanelLogger r0 = r2.mQuickPanelLogger
            if (r0 == 0) goto L7
            r0.onTouchEvent(r3)
        L7:
            com.android.systemui.qs.SecNonInterceptingScrollView r0 = r2.mSecNonInterceptingScrollView
            kotlin.jvm.functions.Function0 r0 = r0.scrollRange
            java.lang.Object r0 = r0.invoke()
            java.lang.Number r0 = (java.lang.Number) r0
            int r0 = r0.intValue()
            if (r0 == 0) goto L57
            r0 = 1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r0)
            int r1 = r1.intValue()
            boolean r1 = r2.canScrollVertically(r1)
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
            boolean r1 = r1.booleanValue()
            if (r1 != 0) goto L45
            r1 = -1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            int r1 = r1.intValue()
            boolean r1 = r2.canScrollVertically(r1)
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L57
        L45:
            int r1 = com.android.systemui.qs.animator.QsAnimatorState.state
            if (r1 != r0) goto L4a
            goto L57
        L4a:
            boolean r2 = super.onTouchEvent(r3)
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)
            boolean r2 = r2.booleanValue()
            goto L63
        L57:
            com.android.systemui.log.QuickPanelLogger r2 = r2.mQuickPanelLogger
            r0 = 0
            if (r2 == 0) goto L62
            java.lang.String r1 = "scrollRange == 0"
            r2.onTouchEvent(r3, r1, r0)
        L62:
            r2 = r0
        L63:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.NonInterceptingScrollView.onTouchEvent(android.view.MotionEvent):boolean");
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
