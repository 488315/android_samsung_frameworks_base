package androidx.recyclerview.widget;

import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AdapterListUpdateCallback implements ListUpdateCallback {
    public final RecyclerView.Adapter mAdapter;

    public AdapterListUpdateCallback(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public final void onChanged(int i, int i2, Object obj) {
        this.mAdapter.mObservable.notifyItemRangeChanged(i, i2, obj);
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public final void onInserted(int i, int i2) {
        this.mAdapter.mObservable.notifyItemRangeInserted(i, i2);
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public final void onMoved(int i, int i2) {
        this.mAdapter.notifyItemMoved(i, i2);
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public final void onRemoved(int i, int i2) {
        this.mAdapter.mObservable.notifyItemRangeRemoved(i, i2);
    }
}
