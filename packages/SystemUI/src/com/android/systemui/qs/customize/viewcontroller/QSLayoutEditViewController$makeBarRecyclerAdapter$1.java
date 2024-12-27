package com.android.systemui.qs.customize.viewcontroller;

import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
