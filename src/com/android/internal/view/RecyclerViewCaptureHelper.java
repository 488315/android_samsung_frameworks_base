package com.android.internal.view;

import android.graphics.Rect;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.view.ScrollCaptureViewHelper;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class RecyclerViewCaptureHelper implements ScrollCaptureViewHelper<ViewGroup> {
    private static final String TAG = "RVCaptureHelper";
    private int mOverScrollMode;
    private boolean mScrollBarWasEnabled;
    private int mScrollDelta;

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public /* bridge */ /* synthetic */ void onScrollRequested(ViewGroup viewGroup, Rect rect, Rect rect2, CancellationSignal cancellationSignal, Consumer consumer) {
        onScrollRequested2(viewGroup, rect, rect2, cancellationSignal, (Consumer<ScrollCaptureViewHelper.ScrollResult>) consumer);
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public boolean onAcceptSession(ViewGroup viewGroup) {
        if (viewGroup.isVisibleToUser()) {
            return viewGroup.canScrollVertically(-1) || viewGroup.canScrollVertically(1);
        }
        return false;
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public void onPrepareForStart(ViewGroup viewGroup, Rect rect) {
        this.mScrollDelta = 0;
        this.mOverScrollMode = viewGroup.getOverScrollMode();
        viewGroup.setOverScrollMode(2);
        this.mScrollBarWasEnabled = viewGroup.isVerticalScrollBarEnabled();
        viewGroup.setVerticalScrollBarEnabled(false);
    }

    /* renamed from: onScrollRequested, reason: avoid collision after fix types in other method */
    public void onScrollRequested2(ViewGroup viewGroup, Rect rect, Rect rect2, CancellationSignal cancellationSignal, Consumer<ScrollCaptureViewHelper.ScrollResult> consumer) {
        ScrollCaptureViewHelper.ScrollResult scrollResult = new ScrollCaptureViewHelper.ScrollResult();
        scrollResult.requestedArea = new Rect(rect2);
        scrollResult.scrollDelta = this.mScrollDelta;
        scrollResult.availableArea = new Rect();
        if (!viewGroup.isVisibleToUser() || viewGroup.getChildCount() == 0) {
            Log.w(TAG, "recyclerView is empty or not visible, cannot continue");
            consumer.accept(scrollResult);
            return;
        }
        Rect rect3 = new Rect(rect2);
        rect3.offset(0, -this.mScrollDelta);
        rect3.offset(rect.left, rect.top);
        View findChildNearestTarget = findChildNearestTarget(viewGroup, rect3);
        if (findChildNearestTarget == null) {
            Log.w(TAG, "Failed to locate anchor view");
            consumer.accept(scrollResult);
            return;
        }
        Rect rect4 = new Rect(rect3);
        viewGroup.offsetRectIntoDescendantCoords(findChildNearestTarget, rect4);
        int top = findChildNearestTarget.getTop();
        Rect rect5 = new Rect(rect4);
        int height = ((viewGroup.getHeight() - viewGroup.getPaddingTop()) - viewGroup.getPaddingBottom()) - rect5.height();
        if (height > 0) {
            rect5.inset(0, (-height) / 2);
        }
        if (viewGroup.requestChildRectangleOnScreen(findChildNearestTarget, rect5, true)) {
            if (findChildNearestTarget.getParent() == null) {
                Log.w(TAG, "Bug: anchor view " + findChildNearestTarget + " is detached after scrolling");
                consumer.accept(scrollResult);
                return;
            }
            int top2 = this.mScrollDelta + (top - findChildNearestTarget.getTop());
            this.mScrollDelta = top2;
            scrollResult.scrollDelta = top2;
        }
        rect3.set(rect4);
        viewGroup.offsetDescendantRectToMyCoords(findChildNearestTarget, rect3);
        Rect rect6 = new Rect(rect);
        viewGroup.getLocalVisibleRect(rect6);
        if (!rect3.intersect(rect6)) {
            consumer.accept(scrollResult);
            return;
        }
        Rect rect7 = new Rect(rect3);
        rect7.offset(-rect.left, -rect.top);
        rect7.offset(0, this.mScrollDelta);
        scrollResult.availableArea = rect7;
        consumer.accept(scrollResult);
    }

    static View findChildNearestTarget(ViewGroup viewGroup, Rect rect) {
        int height = (int) (rect.height() * 0.25f);
        viewGroup.getLocalVisibleRect(new Rect());
        Rect rect2 = new Rect();
        View view = null;
        int i = Integer.MAX_VALUE;
        for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
            View childAt = viewGroup.getChildAt(i2);
            childAt.getHitRect(rect2);
            if (childAt.getVisibility() == 0) {
                int abs = Math.abs(rect.centerY() - rect2.centerY());
                if (abs < i) {
                    view = childAt;
                    i = abs;
                } else if (rect2.intersect(rect) && rect2.height() > height) {
                    view = childAt;
                }
            }
        }
        return view;
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public void onPrepareForEnd(ViewGroup viewGroup) {
        viewGroup.scrollBy(0, -this.mScrollDelta);
        viewGroup.setOverScrollMode(this.mOverScrollMode);
        viewGroup.setVerticalScrollBarEnabled(this.mScrollBarWasEnabled);
    }
}
