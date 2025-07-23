package com.android.internal.view;

import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.android.internal.view.ScrollCaptureViewHelper;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class ListViewCaptureHelper implements ScrollCaptureViewHelper<ListView> {
    private static final String TAG = "LVCaptureHelper";
    private int mOverScrollMode;
    private boolean mScrollBarWasEnabled;
    private int mScrollDelta;

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public /* bridge */ /* synthetic */ void onScrollRequested(ListView listView, Rect rect, Rect rect2, CancellationSignal cancellationSignal, Consumer consumer) {
        onScrollRequested2(listView, rect, rect2, cancellationSignal, (Consumer<ScrollCaptureViewHelper.ScrollResult>) consumer);
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public boolean onAcceptSession(ListView listView) {
        if (listView.isVisibleToUser()) {
            return listView.canScrollVertically(-1) || listView.canScrollVertically(1);
        }
        return false;
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public void onPrepareForStart(ListView listView, Rect rect) {
        this.mScrollDelta = 0;
        this.mOverScrollMode = listView.getOverScrollMode();
        listView.setOverScrollMode(2);
        this.mScrollBarWasEnabled = listView.isVerticalScrollBarEnabled();
        listView.setVerticalScrollBarEnabled(false);
    }

    /* renamed from: onScrollRequested, reason: avoid collision after fix types in other method */
    public void onScrollRequested2(ListView listView, Rect rect, Rect rect2, CancellationSignal cancellationSignal, Consumer<ScrollCaptureViewHelper.ScrollResult> consumer) {
        Log.d(TAG, "-----------------------------------------------------------");
        Log.d(TAG, "onScrollRequested(scrollBounds=" + rect + ", requestRect=" + rect2 + NavigationBarInflaterView.KEY_CODE_END);
        ScrollCaptureViewHelper.ScrollResult scrollResult = new ScrollCaptureViewHelper.ScrollResult();
        scrollResult.requestedArea = new Rect(rect2);
        scrollResult.scrollDelta = this.mScrollDelta;
        scrollResult.availableArea = new Rect();
        if (!listView.isVisibleToUser() || listView.getChildCount() == 0) {
            Log.w(TAG, "listView is empty or not visible, cannot continue");
            consumer.accept(scrollResult);
            return;
        }
        Rect transformFromRequestToContainer = ScrollCaptureViewSupport.transformFromRequestToContainer(this.mScrollDelta, rect, rect2);
        Rect rect3 = new Rect();
        listView.getLocalVisibleRect(rect3);
        Rect rect4 = new Rect(transformFromRequestToContainer);
        int height = rect3.height() - transformFromRequestToContainer.height();
        if (height > 0) {
            rect4.inset(0, (-height) / 2);
        }
        int computeScrollAmount = ScrollCaptureViewSupport.computeScrollAmount(rect3, rect4);
        if (computeScrollAmount < 0) {
            Log.d(TAG, "About to scroll UP (content moves down within parent)");
        } else if (computeScrollAmount > 0) {
            Log.d(TAG, "About to scroll DOWN (content moves up within parent)");
        }
        Log.d(TAG, "scrollAmount: " + computeScrollAmount);
        View findScrollingReferenceView = ScrollCaptureViewSupport.findScrollingReferenceView(listView, computeScrollAmount);
        int top = findScrollingReferenceView.getTop();
        listView.scrollListBy(computeScrollAmount);
        int top2 = top - findScrollingReferenceView.getTop();
        Log.d(TAG, "Parent view has scrolled vertically by " + top2 + " px");
        int i = this.mScrollDelta + top2;
        this.mScrollDelta = i;
        scrollResult.scrollDelta = i;
        if (top2 != 0) {
            Log.d(TAG, "Scroll delta is now " + this.mScrollDelta + " px");
        }
        Rect rect5 = new Rect(ScrollCaptureViewSupport.transformFromRequestToContainer(this.mScrollDelta, rect, rect2));
        listView.getLocalVisibleRect(rect3);
        if (rect5.intersect(rect3)) {
            scrollResult.availableArea = ScrollCaptureViewSupport.transformFromContainerToRequest(this.mScrollDelta, rect, rect5);
        }
        Log.d(TAG, "-----------------------------------------------------------");
        consumer.accept(scrollResult);
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public void onPrepareForEnd(ListView listView) {
        listView.scrollListBy(-this.mScrollDelta);
        listView.setOverScrollMode(this.mOverScrollMode);
        listView.setVerticalScrollBarEnabled(this.mScrollBarWasEnabled);
    }
}
