package com.google.android.setupdesign.template;

import android.widget.AbsListView;
import android.widget.ListView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ListViewScrollHandlingDelegate implements AbsListView.OnScrollListener {
    public final RequireScrollMixin requireScrollMixin;

    public ListViewScrollHandlingDelegate(RequireScrollMixin requireScrollMixin, ListView listView) {
        this.requireScrollMixin = requireScrollMixin;
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public final void onScroll(AbsListView absListView, int i, int i2, int i3) {
        if (i + i2 >= i3) {
            this.requireScrollMixin.notifyScrollabilityChange(false);
        } else {
            this.requireScrollMixin.notifyScrollabilityChange(true);
        }
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public final void onScrollStateChanged(AbsListView absListView, int i) {
    }
}
