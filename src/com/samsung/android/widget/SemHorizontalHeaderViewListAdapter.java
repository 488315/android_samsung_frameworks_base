package com.samsung.android.widget;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.SemHorizontalListView;
import android.widget.WrapperListAdapter;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class SemHorizontalHeaderViewListAdapter implements WrapperListAdapter, Filterable {
    static final ArrayList<SemHorizontalListView.FixedViewInfo> EMPTY_INFO_LIST = new ArrayList<>();
    private final ListAdapter mAdapter;
    boolean mAreAllFixedViewsSelectable;
    ArrayList<SemHorizontalListView.FixedViewInfo> mFooterViewInfos;
    ArrayList<SemHorizontalListView.FixedViewInfo> mHeaderViewInfos;
    private final boolean mIsFilterable;

    public SemHorizontalHeaderViewListAdapter(ArrayList<SemHorizontalListView.FixedViewInfo> arrayList, ArrayList<SemHorizontalListView.FixedViewInfo> arrayList2, ListAdapter listAdapter) {
        this.mAdapter = listAdapter;
        this.mIsFilterable = listAdapter instanceof Filterable;
        if (arrayList == null) {
            this.mHeaderViewInfos = EMPTY_INFO_LIST;
        } else {
            this.mHeaderViewInfos = arrayList;
        }
        if (arrayList2 == null) {
            this.mFooterViewInfos = EMPTY_INFO_LIST;
        } else {
            this.mFooterViewInfos = arrayList2;
        }
        this.mAreAllFixedViewsSelectable = areAllListInfosSelectable(this.mHeaderViewInfos) && areAllListInfosSelectable(this.mFooterViewInfos);
    }

    public int getHeadersCount() {
        return this.mHeaderViewInfos.size();
    }

    public int getFootersCount() {
        return this.mFooterViewInfos.size();
    }

    @Override // android.widget.Adapter
    public boolean isEmpty() {
        ListAdapter listAdapter = this.mAdapter;
        return listAdapter == null || listAdapter.isEmpty();
    }

    private boolean areAllListInfosSelectable(ArrayList<SemHorizontalListView.FixedViewInfo> arrayList) {
        if (arrayList == null) {
            return true;
        }
        Iterator<SemHorizontalListView.FixedViewInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            if (!it.next().isSelectable) {
                return false;
            }
        }
        return true;
    }

    public boolean removeHeader(View view) {
        boolean z = false;
        for (int i = 0; i < this.mHeaderViewInfos.size(); i++) {
            if (this.mHeaderViewInfos.get(i).view == view) {
                this.mHeaderViewInfos.remove(i);
                if (areAllListInfosSelectable(this.mHeaderViewInfos) && areAllListInfosSelectable(this.mFooterViewInfos)) {
                    z = true;
                }
                this.mAreAllFixedViewsSelectable = z;
                return true;
            }
        }
        return false;
    }

    public boolean removeFooter(View view) {
        boolean z = false;
        for (int i = 0; i < this.mFooterViewInfos.size(); i++) {
            if (this.mFooterViewInfos.get(i).view == view) {
                this.mFooterViewInfos.remove(i);
                if (areAllListInfosSelectable(this.mHeaderViewInfos) && areAllListInfosSelectable(this.mFooterViewInfos)) {
                    z = true;
                }
                this.mAreAllFixedViewsSelectable = z;
                return true;
            }
        }
        return false;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        int footersCount;
        int headersCount;
        if (this.mAdapter != null) {
            footersCount = getFootersCount() + getHeadersCount();
            headersCount = this.mAdapter.getCount();
        } else {
            footersCount = getFootersCount();
            headersCount = getHeadersCount();
        }
        return footersCount + headersCount;
    }

    @Override // android.widget.ListAdapter
    public boolean areAllItemsEnabled() {
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null) {
            return this.mAreAllFixedViewsSelectable && listAdapter.areAllItemsEnabled();
        }
        return true;
    }

    @Override // android.widget.ListAdapter
    public boolean isEnabled(int i) {
        int count;
        int headersCount = getHeadersCount();
        if (i < headersCount) {
            return this.mHeaderViewInfos.get(i).isSelectable;
        }
        int i2 = i - headersCount;
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null) {
            count = listAdapter.getCount();
            if (i2 < count) {
                return this.mAdapter.isEnabled(i2);
            }
        } else {
            count = 0;
        }
        int i3 = i2 - count;
        if (i3 >= this.mFooterViewInfos.size()) {
            return false;
        }
        return this.mFooterViewInfos.get(i3).isSelectable;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        int count;
        int headersCount = getHeadersCount();
        if (i < headersCount) {
            return this.mHeaderViewInfos.get(i).data;
        }
        int i2 = i - headersCount;
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null) {
            count = listAdapter.getCount();
            if (i2 < count) {
                return this.mAdapter.getItem(i2);
            }
        } else {
            count = 0;
        }
        int i3 = i2 - count;
        if (i3 >= this.mFooterViewInfos.size()) {
            return null;
        }
        return this.mFooterViewInfos.get(i3).data;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        int i2;
        int headersCount = getHeadersCount();
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter == null || i < headersCount || (i2 = i - headersCount) >= listAdapter.getCount()) {
            return -1L;
        }
        return this.mAdapter.getItemId(i2);
    }

    @Override // android.widget.Adapter
    public boolean hasStableIds() {
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null) {
            return listAdapter.hasStableIds();
        }
        return false;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        int count;
        int headersCount = getHeadersCount();
        if (i < headersCount) {
            return this.mHeaderViewInfos.get(i).view;
        }
        int i2 = i - headersCount;
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null) {
            count = listAdapter.getCount();
            if (i2 < count) {
                return this.mAdapter.getView(i2, view, viewGroup);
            }
        } else {
            count = 0;
        }
        int i3 = i2 - count;
        if (i3 >= this.mFooterViewInfos.size()) {
            return null;
        }
        return this.mFooterViewInfos.get(i3).view;
    }

    @Override // android.widget.Adapter
    public int getItemViewType(int i) {
        int i2;
        int headersCount = getHeadersCount();
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter == null || i < headersCount || (i2 = i - headersCount) >= listAdapter.getCount()) {
            return -2;
        }
        return this.mAdapter.getItemViewType(i2);
    }

    @Override // android.widget.Adapter
    public int getViewTypeCount() {
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null) {
            return listAdapter.getViewTypeCount();
        }
        return 1;
    }

    @Override // android.widget.Adapter
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null) {
            listAdapter.registerDataSetObserver(dataSetObserver);
        }
    }

    @Override // android.widget.Adapter
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null) {
            listAdapter.unregisterDataSetObserver(dataSetObserver);
        }
    }

    @Override // android.widget.Filterable
    public Filter getFilter() {
        if (this.mIsFilterable) {
            return ((Filterable) this.mAdapter).getFilter();
        }
        return null;
    }

    @Override // android.widget.WrapperListAdapter
    public ListAdapter getWrappedAdapter() {
        return this.mAdapter;
    }
}
