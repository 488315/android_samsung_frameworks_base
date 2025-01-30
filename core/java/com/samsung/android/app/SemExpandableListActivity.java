package com.samsung.android.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.SemExpandableListView;

/* loaded from: classes5.dex */
public class SemExpandableListActivity extends Activity
    implements View.OnCreateContextMenuListener,
        SemExpandableListView.OnChildClickListener,
        SemExpandableListView.OnGroupCollapseListener,
        SemExpandableListView.OnGroupExpandListener {
  ExpandableListAdapter mAdapter;
  boolean mFinishedStart = false;
  SemExpandableListView mList;

  @Override // android.app.Activity, android.view.View.OnCreateContextMenuListener
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {}

  @Override // android.widget.SemExpandableListView.OnChildClickListener
  public boolean onChildClick(
      SemExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
    return false;
  }

  @Override // android.widget.SemExpandableListView.OnGroupCollapseListener
  public void onGroupCollapse(int groupPosition) {}

  @Override // android.widget.SemExpandableListView.OnGroupExpandListener
  public void onGroupExpand(int groupPosition) {}

  @Override // android.app.Activity
  protected void onRestoreInstanceState(Bundle state) {
    ensureList();
    super.onRestoreInstanceState(state);
  }

  @Override // android.app.Activity, android.view.Window.Callback
  public void onContentChanged() {
    super.onContentChanged();
    View emptyView = findViewById(16908292);
    SemExpandableListView semExpandableListView = (SemExpandableListView) findViewById(16908298);
    this.mList = semExpandableListView;
    if (semExpandableListView == null) {
      throw new RuntimeException(
          "Your content must have a SemExpandableListView whose id attribute is 'R.id.list'");
    }
    if (emptyView != null) {
      semExpandableListView.setEmptyView(emptyView);
    }
    this.mList.setOnChildClickListener(this);
    this.mList.setOnGroupExpandListener(this);
    this.mList.setOnGroupCollapseListener(this);
    if (this.mFinishedStart) {
      setListAdapter(this.mAdapter);
    }
    this.mFinishedStart = true;
  }

  public void setListAdapter(ExpandableListAdapter adapter) {
    synchronized (this) {
      ensureList();
      this.mAdapter = adapter;
      this.mList.setAdapter(adapter);
    }
  }

  public SemExpandableListView getExpandableListView() {
    ensureList();
    return this.mList;
  }

  public ExpandableListAdapter getExpandableListAdapter() {
    return this.mAdapter;
  }

  private void ensureList() {
    if (this.mList != null) {
      return;
    }
    setContentView(17367041);
  }

  public long getSelectedId() {
    return this.mList.getSelectedId();
  }

  public long getSelectedPosition() {
    return this.mList.getSelectedPosition();
  }

  public boolean setSelectedChild(int groupPosition, int childPosition, boolean shouldExpandGroup) {
    return this.mList.setSelectedChild(groupPosition, childPosition, shouldExpandGroup);
  }

  public void setSelectedGroup(int groupPosition) {
    this.mList.setSelectedGroup(groupPosition);
  }
}
