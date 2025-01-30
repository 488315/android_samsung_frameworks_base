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

  public SemHorizontalHeaderViewListAdapter(
      ArrayList<SemHorizontalListView.FixedViewInfo> headerViewInfos,
      ArrayList<SemHorizontalListView.FixedViewInfo> footerViewInfos,
      ListAdapter adapter) {
    this.mAdapter = adapter;
    this.mIsFilterable = adapter instanceof Filterable;
    if (headerViewInfos == null) {
      this.mHeaderViewInfos = EMPTY_INFO_LIST;
    } else {
      this.mHeaderViewInfos = headerViewInfos;
    }
    if (footerViewInfos == null) {
      this.mFooterViewInfos = EMPTY_INFO_LIST;
    } else {
      this.mFooterViewInfos = footerViewInfos;
    }
    this.mAreAllFixedViewsSelectable =
        areAllListInfosSelectable(this.mHeaderViewInfos)
            && areAllListInfosSelectable(this.mFooterViewInfos);
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

  private boolean areAllListInfosSelectable(ArrayList<SemHorizontalListView.FixedViewInfo> infos) {
    if (infos != null) {
      Iterator<SemHorizontalListView.FixedViewInfo> it = infos.iterator();
      while (it.hasNext()) {
        SemHorizontalListView.FixedViewInfo info = it.next();
        if (!info.isSelectable) {
          return false;
        }
      }
      return true;
    }
    return true;
  }

  public boolean removeHeader(View v) {
    int i = 0;
    while (true) {
      boolean z = false;
      if (i >= this.mHeaderViewInfos.size()) {
        return false;
      }
      SemHorizontalListView.FixedViewInfo info = this.mHeaderViewInfos.get(i);
      if (info.view != v) {
        i++;
      } else {
        this.mHeaderViewInfos.remove(i);
        if (areAllListInfosSelectable(this.mHeaderViewInfos)
            && areAllListInfosSelectable(this.mFooterViewInfos)) {
          z = true;
        }
        this.mAreAllFixedViewsSelectable = z;
        return true;
      }
    }
  }

  public boolean removeFooter(View v) {
    int i = 0;
    while (true) {
      boolean z = false;
      if (i >= this.mFooterViewInfos.size()) {
        return false;
      }
      SemHorizontalListView.FixedViewInfo info = this.mFooterViewInfos.get(i);
      if (info.view != v) {
        i++;
      } else {
        this.mFooterViewInfos.remove(i);
        if (areAllListInfosSelectable(this.mHeaderViewInfos)
            && areAllListInfosSelectable(this.mFooterViewInfos)) {
          z = true;
        }
        this.mAreAllFixedViewsSelectable = z;
        return true;
      }
    }
  }

  @Override // android.widget.Adapter
  public int getCount() {
    if (this.mAdapter != null) {
      return getFootersCount() + getHeadersCount() + this.mAdapter.getCount();
    }
    return getFootersCount() + getHeadersCount();
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
  public boolean isEnabled(int position) {
    int numHeaders = getHeadersCount();
    if (position < numHeaders) {
      return this.mHeaderViewInfos.get(position).isSelectable;
    }
    int adjPosition = position - numHeaders;
    int adapterCount = 0;
    ListAdapter listAdapter = this.mAdapter;
    if (listAdapter != null && adjPosition < (adapterCount = listAdapter.getCount())) {
      return this.mAdapter.isEnabled(adjPosition);
    }
    if (adjPosition - adapterCount >= this.mFooterViewInfos.size()) {
      return false;
    }
    return this.mFooterViewInfos.get(adjPosition - adapterCount).isSelectable;
  }

  @Override // android.widget.Adapter
  public Object getItem(int position) {
    int numHeaders = getHeadersCount();
    if (position < numHeaders) {
      return this.mHeaderViewInfos.get(position).data;
    }
    int adjPosition = position - numHeaders;
    int adapterCount = 0;
    ListAdapter listAdapter = this.mAdapter;
    if (listAdapter != null && adjPosition < (adapterCount = listAdapter.getCount())) {
      return this.mAdapter.getItem(adjPosition);
    }
    if (adjPosition - adapterCount >= this.mFooterViewInfos.size()) {
      return null;
    }
    return this.mFooterViewInfos.get(adjPosition - adapterCount).data;
  }

  @Override // android.widget.Adapter
  public long getItemId(int position) {
    int numHeaders = getHeadersCount();
    ListAdapter listAdapter = this.mAdapter;
    if (listAdapter != null && position >= numHeaders) {
      int adjPosition = position - numHeaders;
      int adapterCount = listAdapter.getCount();
      if (adjPosition < adapterCount) {
        return this.mAdapter.getItemId(adjPosition);
      }
      return -1L;
    }
    return -1L;
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
  public View getView(int position, View convertView, ViewGroup parent) {
    int numHeaders = getHeadersCount();
    if (position < numHeaders) {
      return this.mHeaderViewInfos.get(position).view;
    }
    int adjPosition = position - numHeaders;
    int adapterCount = 0;
    ListAdapter listAdapter = this.mAdapter;
    if (listAdapter != null && adjPosition < (adapterCount = listAdapter.getCount())) {
      return this.mAdapter.getView(adjPosition, convertView, parent);
    }
    if (adjPosition - adapterCount >= this.mFooterViewInfos.size()) {
      return null;
    }
    return this.mFooterViewInfos.get(adjPosition - adapterCount).view;
  }

  @Override // android.widget.Adapter
  public int getItemViewType(int position) {
    int numHeaders = getHeadersCount();
    ListAdapter listAdapter = this.mAdapter;
    if (listAdapter != null && position >= numHeaders) {
      int adjPosition = position - numHeaders;
      int adapterCount = listAdapter.getCount();
      if (adjPosition < adapterCount) {
        return this.mAdapter.getItemViewType(adjPosition);
      }
      return -2;
    }
    return -2;
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
  public void registerDataSetObserver(DataSetObserver observer) {
    ListAdapter listAdapter = this.mAdapter;
    if (listAdapter != null) {
      listAdapter.registerDataSetObserver(observer);
    }
  }

  @Override // android.widget.Adapter
  public void unregisterDataSetObserver(DataSetObserver observer) {
    ListAdapter listAdapter = this.mAdapter;
    if (listAdapter != null) {
      listAdapter.unregisterDataSetObserver(observer);
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
