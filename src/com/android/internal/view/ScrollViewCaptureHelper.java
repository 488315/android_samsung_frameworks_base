package com.android.internal.view;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.CancellationSignal;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.view.ScrollCaptureViewHelper;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class ScrollViewCaptureHelper implements ScrollCaptureViewHelper<ViewGroup> {
    private int mOverScrollMode;
    private boolean mScrollBarEnabled;
    private int mStartScrollY;

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
        this.mStartScrollY = viewGroup.getScrollY();
        int overScrollMode = viewGroup.getOverScrollMode();
        this.mOverScrollMode = overScrollMode;
        if (overScrollMode != 2) {
            viewGroup.setOverScrollMode(2);
        }
        boolean isVerticalScrollBarEnabled = viewGroup.isVerticalScrollBarEnabled();
        this.mScrollBarEnabled = isVerticalScrollBarEnabled;
        if (isVerticalScrollBarEnabled) {
            viewGroup.setVerticalScrollBarEnabled(false);
        }
    }

    /* renamed from: onScrollRequested, reason: avoid collision after fix types in other method */
    public void onScrollRequested2(ViewGroup viewGroup, Rect rect, Rect rect2, CancellationSignal cancellationSignal, Consumer<ScrollCaptureViewHelper.ScrollResult> consumer) {
        int scrollY = viewGroup.getScrollY() - this.mStartScrollY;
        ScrollCaptureViewHelper.ScrollResult scrollResult = new ScrollCaptureViewHelper.ScrollResult();
        scrollResult.requestedArea = new Rect(rect2);
        scrollResult.scrollDelta = scrollY;
        scrollResult.availableArea = new Rect();
        View childAt = viewGroup.getChildAt(0);
        if (childAt == null) {
            consumer.accept(scrollResult);
            return;
        }
        Rect rect3 = new Rect(rect2);
        rect3.offset(0, -scrollY);
        rect3.offset(rect.left, rect.top);
        Rect rect4 = new Rect(rect3);
        rect4.offset(viewGroup.getScrollX() - childAt.getLeft(), viewGroup.getScrollY() - childAt.getTop());
        Rect rect5 = new Rect(rect4);
        int height = ((viewGroup.getHeight() - viewGroup.getPaddingTop()) - viewGroup.getPaddingBottom()) - rect5.height();
        if (height > 0) {
            rect5.inset(0, (-height) / 2);
        }
        childAt.requestRectangleOnScreen(rect5, true);
        int scrollY2 = viewGroup.getScrollY() - this.mStartScrollY;
        scrollResult.scrollDelta = scrollY2;
        Point point = new Point();
        Rect rect6 = new Rect(rect4);
        if (!viewGroup.getChildVisibleRect(childAt, rect6, point)) {
            rect6.setEmpty();
            scrollResult.availableArea = rect6;
            consumer.accept(scrollResult);
        } else {
            rect6.offset(-point.x, -point.y);
            rect6.offset(childAt.getLeft() - viewGroup.getScrollX(), childAt.getTop() - viewGroup.getScrollY());
            rect6.offset(-rect.left, -rect.top);
            rect6.offset(0, scrollY2);
            scrollResult.availableArea = new Rect(rect6);
            consumer.accept(scrollResult);
        }
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public void onPrepareForEnd(ViewGroup viewGroup) {
        viewGroup.scrollTo(0, this.mStartScrollY);
        int i = this.mOverScrollMode;
        if (i != 2) {
            viewGroup.setOverScrollMode(i);
        }
        if (this.mScrollBarEnabled) {
            viewGroup.setVerticalScrollBarEnabled(true);
        }
    }
}
