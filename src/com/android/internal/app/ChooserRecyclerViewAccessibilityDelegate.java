package com.android.internal.app;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import com.android.internal.widget.RecyclerView;
import com.android.internal.widget.RecyclerViewAccessibilityDelegate;

/* loaded from: classes5.dex */
class ChooserRecyclerViewAccessibilityDelegate extends RecyclerViewAccessibilityDelegate {
    private final int[] mConsumed;
    private final Rect mTempRect;

    ChooserRecyclerViewAccessibilityDelegate(RecyclerView recyclerView) {
        super(recyclerView);
        this.mTempRect = new Rect();
        this.mConsumed = new int[2];
    }

    @Override // android.view.View.AccessibilityDelegate
    public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) throws Resources.NotFoundException {
        boolean zOnRequestSendAccessibilityEvent = super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
        if (zOnRequestSendAccessibilityEvent && accessibilityEvent.getEventType() == 32768) {
            ensureViewOnScreenVisibility((RecyclerView) viewGroup, view);
        }
        return zOnRequestSendAccessibilityEvent;
    }

    private void ensureViewOnScreenVisibility(RecyclerView recyclerView, View view) throws Resources.NotFoundException {
        int i;
        View viewFindContainingItemView = recyclerView.findContainingItemView(view);
        if (viewFindContainingItemView == null) {
            return;
        }
        recyclerView.getBoundsOnScreen(this.mTempRect, true);
        int i2 = this.mTempRect.top;
        int i3 = this.mTempRect.bottom;
        viewFindContainingItemView.getBoundsOnScreen(this.mTempRect);
        if (this.mTempRect.top < i2) {
            i = this.mTempRect.bottom - i3;
        } else {
            i = this.mTempRect.bottom > i3 ? this.mTempRect.top - i2 : 0;
        }
        nestedVerticalScrollBy(recyclerView, i);
    }

    private void nestedVerticalScrollBy(RecyclerView recyclerView, int i) throws Resources.NotFoundException {
        if (i == 0) {
            return;
        }
        recyclerView.startNestedScroll(2);
        if (recyclerView.dispatchNestedPreScroll(0, i, this.mConsumed, null)) {
            i -= this.mConsumed[1];
        }
        recyclerView.scrollBy(0, i);
        recyclerView.stopNestedScroll();
    }
}
