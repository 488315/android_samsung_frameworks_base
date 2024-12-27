package com.android.internal.view;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.CancellationSignal;
import android.view.View;
import android.view.ViewGroup;

import java.util.function.Consumer;

/* loaded from: classes5.dex */
public class ScrollViewCaptureHelper implements ScrollCaptureViewHelper<ViewGroup> {
    private int mOverScrollMode;
    private boolean mScrollBarEnabled;
    private int mStartScrollY;

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public /* bridge */ /* synthetic */ void onScrollRequested(
            ViewGroup viewGroup,
            Rect rect,
            Rect rect2,
            CancellationSignal cancellationSignal,
            Consumer consumer) {
        onScrollRequested2(
                viewGroup,
                rect,
                rect2,
                cancellationSignal,
                (Consumer<ScrollCaptureViewHelper.ScrollResult>) consumer);
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public boolean onAcceptSession(ViewGroup view) {
        return view.isVisibleToUser()
                && (view.canScrollVertically(-1) || view.canScrollVertically(1));
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public void onPrepareForStart(ViewGroup view, Rect scrollBounds) {
        this.mStartScrollY = view.getScrollY();
        this.mOverScrollMode = view.getOverScrollMode();
        if (this.mOverScrollMode != 2) {
            view.setOverScrollMode(2);
        }
        this.mScrollBarEnabled = view.isVerticalScrollBarEnabled();
        if (this.mScrollBarEnabled) {
            view.setVerticalScrollBarEnabled(false);
        }
    }

    /* renamed from: onScrollRequested, reason: avoid collision after fix types in other method */
    public void onScrollRequested2(
            ViewGroup view,
            Rect scrollBounds,
            Rect requestRect,
            CancellationSignal signal,
            Consumer<ScrollCaptureViewHelper.ScrollResult> resultConsumer) {
        int scrollDelta = view.getScrollY() - this.mStartScrollY;
        ScrollCaptureViewHelper.ScrollResult result = new ScrollCaptureViewHelper.ScrollResult();
        result.requestedArea = new Rect(requestRect);
        result.scrollDelta = scrollDelta;
        result.availableArea = new Rect();
        View contentView = view.getChildAt(0);
        if (contentView == null) {
            resultConsumer.accept(result);
            return;
        }
        Rect requestedContainerBounds = new Rect(requestRect);
        requestedContainerBounds.offset(0, -scrollDelta);
        requestedContainerBounds.offset(scrollBounds.left, scrollBounds.top);
        Rect requestedContentBounds = new Rect(requestedContainerBounds);
        requestedContentBounds.offset(
                view.getScrollX() - contentView.getLeft(),
                view.getScrollY() - contentView.getTop());
        Rect input = new Rect(requestedContentBounds);
        int remainingHeight =
                ((view.getHeight() - view.getPaddingTop()) - view.getPaddingBottom())
                        - input.height();
        if (remainingHeight > 0) {
            input.inset(0, (-remainingHeight) / 2);
        }
        contentView.requestRectangleOnScreen(input, true);
        int scrollDelta2 = view.getScrollY() - this.mStartScrollY;
        result.scrollDelta = scrollDelta2;
        Point offset = new Point();
        Rect available = new Rect(requestedContentBounds);
        if (!view.getChildVisibleRect(contentView, available, offset)) {
            available.setEmpty();
            result.availableArea = available;
            resultConsumer.accept(result);
        } else {
            available.offset(-offset.x, -offset.y);
            available.offset(
                    contentView.getLeft() - view.getScrollX(),
                    contentView.getTop() - view.getScrollY());
            available.offset(-scrollBounds.left, -scrollBounds.top);
            available.offset(0, scrollDelta2);
            result.availableArea = new Rect(available);
            resultConsumer.accept(result);
        }
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public void onPrepareForEnd(ViewGroup view) {
        view.scrollTo(0, this.mStartScrollY);
        if (this.mOverScrollMode != 2) {
            view.setOverScrollMode(this.mOverScrollMode);
        }
        if (this.mScrollBarEnabled) {
            view.setVerticalScrollBarEnabled(true);
        }
    }
}
