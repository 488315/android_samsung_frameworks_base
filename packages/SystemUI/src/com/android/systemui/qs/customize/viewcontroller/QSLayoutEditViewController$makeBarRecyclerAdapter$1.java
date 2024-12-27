package com.android.systemui.qs.customize.viewcontroller;

import androidx.recyclerview.widget.RecyclerView;

public final class QSLayoutEditViewController$makeBarRecyclerAdapter$1 extends RecyclerView.AdapterDataObserver {
    public final /* synthetic */ QSLayoutEditViewController this$0;

    public QSLayoutEditViewController$makeBarRecyclerAdapter$1(QSLayoutEditViewController qSLayoutEditViewController) {
        this.this$0 = qSLayoutEditViewController;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
    public final void onItemRangeMoved(int i, int i2) {
        if (i == 0 || i2 == 0) {
            this.this$0.recyclerView.scrollToPosition(0);
        }
    }
}
