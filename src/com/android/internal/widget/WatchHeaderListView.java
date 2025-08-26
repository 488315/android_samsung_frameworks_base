package com.android.internal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.function.Predicate;

/* loaded from: classes6.dex */
public class WatchHeaderListView extends ListView {
    private View mTopPanel;

    public WatchHeaderListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public WatchHeaderListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public WatchHeaderListView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.widget.ListView
    protected HeaderViewListAdapter wrapHeaderListAdapterInternal(ArrayList<ListView.FixedViewInfo> arrayList, ArrayList<ListView.FixedViewInfo> arrayList2, ListAdapter listAdapter) {
        return new WatchHeaderListAdapter(arrayList, arrayList2, listAdapter);
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.ViewManager
    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (this.mTopPanel == null) {
            setTopPanel(view);
            return;
        }
        throw new IllegalStateException("WatchHeaderListView can host only one header");
    }

    public void setTopPanel(View view) {
        this.mTopPanel = view;
        wrapAdapterIfNecessary();
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView
    public void setAdapter(ListAdapter listAdapter) {
        super.setAdapter(listAdapter);
        wrapAdapterIfNecessary();
    }

    @Override // android.widget.ListView, android.view.ViewGroup, android.view.View
    protected View findViewTraversal(int i) {
        View view;
        View viewFindViewTraversal = super.findViewTraversal(i);
        return (viewFindViewTraversal != null || (view = this.mTopPanel) == null || view.isRootNamespace()) ? viewFindViewTraversal : this.mTopPanel.findViewById(i);
    }

    @Override // android.widget.ListView, android.view.ViewGroup, android.view.View
    protected View findViewWithTagTraversal(Object obj) {
        View view;
        View viewFindViewWithTagTraversal = super.findViewWithTagTraversal(obj);
        return (viewFindViewWithTagTraversal != null || (view = this.mTopPanel) == null || view.isRootNamespace()) ? viewFindViewWithTagTraversal : this.mTopPanel.findViewWithTag(obj);
    }

    @Override // android.widget.ListView, android.view.ViewGroup, android.view.View
    protected <T extends View> T findViewByPredicateTraversal(Predicate<View> predicate, View view) {
        View view2;
        T t = (T) super.findViewByPredicateTraversal(predicate, view);
        return (t != null || (view2 = this.mTopPanel) == null || view2 == view || view2.isRootNamespace()) ? t : (T) this.mTopPanel.findViewByPredicate(predicate);
    }

    @Override // android.widget.ListView, android.widget.AbsListView
    public int getHeaderViewsCount() {
        if (this.mTopPanel == null) {
            return super.getHeaderViewsCount();
        }
        return super.getHeaderViewsCount() + (this.mTopPanel.getVisibility() == 8 ? 0 : 1);
    }

    private void wrapAdapterIfNecessary() {
        ListAdapter adapter = getAdapter();
        if (adapter == null || this.mTopPanel == null) {
            return;
        }
        if (!(adapter instanceof WatchHeaderListAdapter)) {
            wrapHeaderListAdapterInternal();
        }
        ((WatchHeaderListAdapter) getAdapter()).setTopPanel(this.mTopPanel);
        dispatchDataSetObserverOnChangedInternal();
    }

    private static class WatchHeaderListAdapter extends HeaderViewListAdapter {
        private View mTopPanel;

        public WatchHeaderListAdapter(ArrayList<ListView.FixedViewInfo> arrayList, ArrayList<ListView.FixedViewInfo> arrayList2, ListAdapter listAdapter) {
            super(arrayList, arrayList2, listAdapter);
        }

        public void setTopPanel(View view) {
            this.mTopPanel = view;
        }

        private int getTopPanelCount() {
            View view = this.mTopPanel;
            return (view == null || view.getVisibility() == 8) ? 0 : 1;
        }

        @Override // android.widget.HeaderViewListAdapter, android.widget.Adapter
        public int getCount() {
            return super.getCount() + getTopPanelCount();
        }

        @Override // android.widget.HeaderViewListAdapter, android.widget.ListAdapter
        public boolean areAllItemsEnabled() {
            return getTopPanelCount() == 0 && super.areAllItemsEnabled();
        }

        @Override // android.widget.HeaderViewListAdapter, android.widget.ListAdapter
        public boolean isEnabled(int i) {
            int topPanelCount = getTopPanelCount();
            if (i < topPanelCount) {
                return false;
            }
            return super.isEnabled(i - topPanelCount);
        }

        @Override // android.widget.HeaderViewListAdapter, android.widget.Adapter
        public Object getItem(int i) {
            int topPanelCount = getTopPanelCount();
            if (i < topPanelCount) {
                return null;
            }
            return super.getItem(i - topPanelCount);
        }

        @Override // android.widget.HeaderViewListAdapter, android.widget.Adapter
        public long getItemId(int i) {
            int i2;
            int headersCount = getHeadersCount() + getTopPanelCount();
            if (getWrappedAdapter() == null || i < headersCount || (i2 = i - headersCount) >= getWrappedAdapter().getCount()) {
                return -1L;
            }
            return getWrappedAdapter().getItemId(i2);
        }

        @Override // android.widget.HeaderViewListAdapter, android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            int topPanelCount = getTopPanelCount();
            return i < topPanelCount ? this.mTopPanel : super.getView(i - topPanelCount, view, viewGroup);
        }

        @Override // android.widget.HeaderViewListAdapter, android.widget.Adapter
        public int getItemViewType(int i) {
            int i2;
            int headersCount = getHeadersCount() + getTopPanelCount();
            if (getWrappedAdapter() == null || i < headersCount || (i2 = i - headersCount) >= getWrappedAdapter().getCount()) {
                return -2;
            }
            return getWrappedAdapter().getItemViewType(i2);
        }
    }
}
