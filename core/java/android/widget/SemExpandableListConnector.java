package android.widget;

import android.database.DataSetObserver;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.p009os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collections;

/* loaded from: classes4.dex */
class SemExpandableListConnector extends BaseAdapter implements Filterable {
  private ExpandableListAdapter mExpandableListAdapter;
  private ItemDecorator mItemDecorator;
  private int mTotalExpChildrenCount;
  private int mMaxExpGroupCount = Integer.MAX_VALUE;
  private final DataSetObserver mDataSetObserver = new MyDataSetObserver();
  private boolean mIsRegisteredObserver = false;
  private ArrayList<GroupMetadata> mExpGroupMetadataList = new ArrayList<>();

  interface ItemDecorator {
    View onItemDecorate(View view, View view2, PositionMetadata positionMetadata);

    View unfoldDecoratedView(View view);
  }

  public SemExpandableListConnector(ExpandableListAdapter expandableListAdapter) {
    setExpandableListAdapter(expandableListAdapter);
  }

  public void setExpandableListAdapter(ExpandableListAdapter expandableListAdapter) {
    ExpandableListAdapter expandableListAdapter2 = this.mExpandableListAdapter;
    if (expandableListAdapter2 != null) {
      expandableListAdapter2.unregisterDataSetObserver(this.mDataSetObserver);
    }
    this.mExpandableListAdapter = expandableListAdapter;
    expandableListAdapter.registerDataSetObserver(this.mDataSetObserver);
    this.mIsRegisteredObserver = true;
  }

  void setItemDecorator(ItemDecorator itemDecorator) {
    this.mItemDecorator = itemDecorator;
  }

  void semRegisterDataSetObserver() {
    DataSetObserver dataSetObserver;
    ExpandableListAdapter expandableListAdapter = this.mExpandableListAdapter;
    if (expandableListAdapter != null
        && (dataSetObserver = this.mDataSetObserver) != null
        && !this.mIsRegisteredObserver) {
      expandableListAdapter.registerDataSetObserver(dataSetObserver);
      this.mIsRegisteredObserver = true;
    }
  }

  void semUnregisterDataSetObserver() {
    DataSetObserver dataSetObserver;
    ExpandableListAdapter expandableListAdapter = this.mExpandableListAdapter;
    if (expandableListAdapter != null
        && (dataSetObserver = this.mDataSetObserver) != null
        && this.mIsRegisteredObserver) {
      expandableListAdapter.unregisterDataSetObserver(dataSetObserver);
      this.mIsRegisteredObserver = false;
    }
  }

  PositionMetadata getUnflattenedPos(int flPos) {
    int insertPosition;
    int groupPos;
    ArrayList<GroupMetadata> egml = this.mExpGroupMetadataList;
    int numExpGroups = egml.size();
    int leftExpGroupIndex = 0;
    int rightExpGroupIndex = numExpGroups - 1;
    int midExpGroupIndex = 0;
    if (numExpGroups == 0) {
      return PositionMetadata.obtain(flPos, 2, flPos, -1, null, 0);
    }
    while (leftExpGroupIndex <= rightExpGroupIndex) {
      midExpGroupIndex = ((rightExpGroupIndex - leftExpGroupIndex) / 2) + leftExpGroupIndex;
      GroupMetadata midExpGm = egml.get(midExpGroupIndex);
      if (flPos > midExpGm.lastChildFlPos) {
        leftExpGroupIndex = midExpGroupIndex + 1;
      } else if (flPos < midExpGm.flPos) {
        rightExpGroupIndex = midExpGroupIndex - 1;
      } else {
        if (flPos == midExpGm.flPos) {
          return PositionMetadata.obtain(flPos, 2, midExpGm.gPos, -1, midExpGm, midExpGroupIndex);
        }
        if (flPos <= midExpGm.lastChildFlPos) {
          int childPos = flPos - (midExpGm.flPos + 1);
          return PositionMetadata.obtain(
              flPos, 1, midExpGm.gPos, childPos, midExpGm, midExpGroupIndex);
        }
      }
    }
    if (leftExpGroupIndex > midExpGroupIndex) {
      GroupMetadata leftExpGm = egml.get(leftExpGroupIndex - 1);
      int insertPosition2 = leftExpGroupIndex;
      int groupPos2 = (flPos - leftExpGm.lastChildFlPos) + leftExpGm.gPos;
      insertPosition = insertPosition2;
      groupPos = groupPos2;
    } else if (rightExpGroupIndex < midExpGroupIndex) {
      int rightExpGroupIndex2 = rightExpGroupIndex + 1;
      GroupMetadata rightExpGm = egml.get(rightExpGroupIndex2);
      int groupPos3 = rightExpGm.gPos - (rightExpGm.flPos - flPos);
      insertPosition = rightExpGroupIndex2;
      groupPos = groupPos3;
    } else {
      throw new RuntimeException("Unknown state");
    }
    return PositionMetadata.obtain(flPos, 2, groupPos, -1, null, insertPosition);
  }

  PositionMetadata getFlattenedPos(SemExpandableListPosition pos) {
    ArrayList<GroupMetadata> egml = this.mExpGroupMetadataList;
    int numExpGroups = egml.size();
    int leftExpGroupIndex = 0;
    int rightExpGroupIndex = numExpGroups - 1;
    int midExpGroupIndex = 0;
    if (numExpGroups == 0) {
      return PositionMetadata.obtain(pos.groupPos, pos.type, pos.groupPos, pos.childPos, null, 0);
    }
    while (leftExpGroupIndex <= rightExpGroupIndex) {
      midExpGroupIndex = ((rightExpGroupIndex - leftExpGroupIndex) / 2) + leftExpGroupIndex;
      GroupMetadata midExpGm = egml.get(midExpGroupIndex);
      if (pos.groupPos > midExpGm.gPos) {
        leftExpGroupIndex = midExpGroupIndex + 1;
      } else if (pos.groupPos < midExpGm.gPos) {
        rightExpGroupIndex = midExpGroupIndex - 1;
      } else if (pos.groupPos == midExpGm.gPos) {
        if (pos.type == 2) {
          return PositionMetadata.obtain(
              midExpGm.flPos, pos.type, pos.groupPos, pos.childPos, midExpGm, midExpGroupIndex);
        }
        if (pos.type == 1) {
          return PositionMetadata.obtain(
              midExpGm.flPos + pos.childPos + 1,
              pos.type,
              pos.groupPos,
              pos.childPos,
              midExpGm,
              midExpGroupIndex);
        }
        return null;
      }
    }
    if (pos.type != 2) {
      return null;
    }
    if (leftExpGroupIndex > midExpGroupIndex) {
      GroupMetadata leftExpGm = egml.get(leftExpGroupIndex - 1);
      int flPos = leftExpGm.lastChildFlPos + (pos.groupPos - leftExpGm.gPos);
      return PositionMetadata.obtain(
          flPos, pos.type, pos.groupPos, pos.childPos, null, leftExpGroupIndex);
    }
    if (rightExpGroupIndex >= midExpGroupIndex) {
      return null;
    }
    int rightExpGroupIndex2 = rightExpGroupIndex + 1;
    GroupMetadata rightExpGm = egml.get(rightExpGroupIndex2);
    int flPos2 = rightExpGm.flPos - (rightExpGm.gPos - pos.groupPos);
    return PositionMetadata.obtain(
        flPos2, pos.type, pos.groupPos, pos.childPos, null, rightExpGroupIndex2);
  }

  @Override // android.widget.BaseAdapter, android.widget.ListAdapter
  public boolean areAllItemsEnabled() {
    return this.mExpandableListAdapter.areAllItemsEnabled();
  }

  @Override // android.widget.BaseAdapter, android.widget.ListAdapter
  public boolean isEnabled(int flatListPos) {
    boolean retValue;
    PositionMetadata metadata = getUnflattenedPos(flatListPos);
    SemExpandableListPosition pos = metadata.position;
    if (pos.type == 1) {
      retValue = this.mExpandableListAdapter.isChildSelectable(pos.groupPos, pos.childPos);
    } else {
      retValue = true;
    }
    metadata.recycle();
    return retValue;
  }

  @Override // android.widget.Adapter
  public int getCount() {
    int groupCount = this.mExpandableListAdapter.getGroupCount();
    if (groupCount == 0) {
      return 0;
    }
    return this.mTotalExpChildrenCount + groupCount;
  }

  @Override // android.widget.Adapter
  public Object getItem(int flatListPos) {
    Object retValue;
    PositionMetadata posMetadata = getUnflattenedPos(flatListPos);
    if (posMetadata.position.type == 2) {
      retValue = this.mExpandableListAdapter.getGroup(posMetadata.position.groupPos);
    } else if (posMetadata.position.type == 1) {
      retValue =
          this.mExpandableListAdapter.getChild(
              posMetadata.position.groupPos, posMetadata.position.childPos);
    } else {
      throw new RuntimeException("Flat list position is of unknown type");
    }
    posMetadata.recycle();
    return retValue;
  }

  @Override // android.widget.Adapter
  public long getItemId(int flatListPos) {
    long retValue;
    PositionMetadata posMetadata = getUnflattenedPos(flatListPos);
    long groupId = this.mExpandableListAdapter.getGroupId(posMetadata.position.groupPos);
    if (posMetadata.position.type == 2) {
      retValue = this.mExpandableListAdapter.getCombinedGroupId(groupId);
    } else if (posMetadata.position.type == 1) {
      long childId =
          this.mExpandableListAdapter.getChildId(
              posMetadata.position.groupPos, posMetadata.position.childPos);
      retValue = this.mExpandableListAdapter.getCombinedChildId(groupId, childId);
    } else {
      throw new RuntimeException("Flat list position is of unknown type");
    }
    posMetadata.recycle();
    return retValue;
  }

  @Override // android.widget.Adapter
  public View getView(int flatListPos, View convertView, ViewGroup parent) {
    View retValue;
    PositionMetadata posMetadata = getUnflattenedPos(flatListPos);
    View originalConvertView = convertView;
    ItemDecorator itemDecorator = this.mItemDecorator;
    if (itemDecorator != null) {
      originalConvertView = itemDecorator.unfoldDecoratedView(convertView);
    }
    if (posMetadata.position.type == 2) {
      retValue =
          this.mExpandableListAdapter.getGroupView(
              posMetadata.position.groupPos, posMetadata.isExpanded(), originalConvertView, parent);
    } else if (posMetadata.position.type == 1) {
      boolean isLastChild = posMetadata.groupMetadata.lastChildFlPos == flatListPos;
      retValue =
          this.mExpandableListAdapter.getChildView(
              posMetadata.position.groupPos,
              posMetadata.position.childPos,
              isLastChild,
              originalConvertView,
              parent);
    } else {
      throw new RuntimeException("Flat list position is of unknown type");
    }
    ItemDecorator itemDecorator2 = this.mItemDecorator;
    if (itemDecorator2 != null) {
      retValue = itemDecorator2.onItemDecorate(convertView, retValue, posMetadata);
    }
    posMetadata.recycle();
    return retValue;
  }

  @Override // android.widget.BaseAdapter, android.widget.Adapter
  public int getItemViewType(int flatListPos) {
    int retValue;
    PositionMetadata metadata = getUnflattenedPos(flatListPos);
    SemExpandableListPosition pos = metadata.position;
    ExpandableListAdapter expandableListAdapter = this.mExpandableListAdapter;
    if (expandableListAdapter instanceof HeterogeneousExpandableList) {
      HeterogeneousExpandableList adapter = (HeterogeneousExpandableList) expandableListAdapter;
      if (pos.type == 2) {
        retValue = adapter.getGroupType(pos.groupPos);
      } else {
        int retValue2 = pos.groupPos;
        int childType = adapter.getChildType(retValue2, pos.childPos);
        retValue = adapter.getGroupTypeCount() + childType;
      }
    } else if (pos.type == 2) {
      retValue = 0;
    } else {
      retValue = 1;
    }
    metadata.recycle();
    return retValue;
  }

  @Override // android.widget.BaseAdapter, android.widget.Adapter
  public int getViewTypeCount() {
    ExpandableListAdapter expandableListAdapter = this.mExpandableListAdapter;
    if (expandableListAdapter instanceof HeterogeneousExpandableList) {
      HeterogeneousExpandableList adapter = (HeterogeneousExpandableList) expandableListAdapter;
      return adapter.getGroupTypeCount() + adapter.getChildTypeCount();
    }
    return 2;
  }

  @Override // android.widget.BaseAdapter, android.widget.Adapter
  public boolean hasStableIds() {
    return this.mExpandableListAdapter.hasStableIds();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void refreshExpGroupMetadataList(
      boolean forceChildrenCountRefresh, boolean syncGroupPositions) {
    int gChildrenCount;
    ArrayList<GroupMetadata> egml = this.mExpGroupMetadataList;
    int egmlSize = egml.size();
    int curFlPos = 0;
    this.mTotalExpChildrenCount = 0;
    if (syncGroupPositions) {
      boolean positionsChanged = false;
      for (int i = egmlSize - 1; i >= 0; i--) {
        GroupMetadata curGm = egml.get(i);
        int newGPos = findGroupPosition(curGm.gId, curGm.gPos);
        if (newGPos != curGm.gPos) {
          if (newGPos == -1) {
            egml.remove(i);
            egmlSize--;
          }
          curGm.gPos = newGPos;
          if (!positionsChanged) {
            positionsChanged = true;
          }
        }
      }
      if (positionsChanged) {
        Collections.sort(egml);
      }
    }
    int lastGPos = 0;
    for (int i2 = 0; i2 < egmlSize; i2++) {
      GroupMetadata curGm2 = egml.get(i2);
      if (curGm2.lastChildFlPos == -1 || forceChildrenCountRefresh) {
        gChildrenCount = this.mExpandableListAdapter.getChildrenCount(curGm2.gPos);
      } else {
        gChildrenCount = curGm2.lastChildFlPos - curGm2.flPos;
      }
      this.mTotalExpChildrenCount += gChildrenCount;
      int curFlPos2 = curFlPos + (curGm2.gPos - lastGPos);
      lastGPos = curGm2.gPos;
      curGm2.flPos = curFlPos2;
      curFlPos = curFlPos2 + gChildrenCount;
      curGm2.lastChildFlPos = curFlPos;
    }
  }

  boolean collapseGroup(int groupPos) {
    SemExpandableListPosition elGroupPos = SemExpandableListPosition.obtain(2, groupPos, -1, -1);
    PositionMetadata pm = getFlattenedPos(elGroupPos);
    elGroupPos.recycle();
    if (pm == null) {
      return false;
    }
    boolean retValue = collapseGroup(pm);
    pm.recycle();
    return retValue;
  }

  boolean collapseGroup(PositionMetadata posMetadata) {
    if (posMetadata.groupMetadata == null) {
      return false;
    }
    this.mExpGroupMetadataList.remove(posMetadata.groupMetadata);
    refreshExpGroupMetadataList(false, false);
    notifyDataSetChanged();
    this.mExpandableListAdapter.onGroupCollapsed(posMetadata.groupMetadata.gPos);
    return true;
  }

  boolean expandGroup(int groupPos) {
    SemExpandableListPosition elGroupPos = SemExpandableListPosition.obtain(2, groupPos, -1, -1);
    PositionMetadata pm = getFlattenedPos(elGroupPos);
    if (pm == null) {
      return false;
    }
    elGroupPos.recycle();
    boolean retValue = expandGroup(pm);
    pm.recycle();
    return retValue;
  }

  boolean expandGroup(PositionMetadata posMetadata) {
    if (posMetadata.position.groupPos < 0) {
      throw new RuntimeException("Need group");
    }
    if (this.mMaxExpGroupCount == 0 || posMetadata.groupMetadata != null) {
      return false;
    }
    if (this.mExpGroupMetadataList.size() >= this.mMaxExpGroupCount) {
      GroupMetadata collapsedGm = this.mExpGroupMetadataList.get(0);
      int collapsedIndex = this.mExpGroupMetadataList.indexOf(collapsedGm);
      collapseGroup(collapsedGm.gPos);
      if (posMetadata.groupInsertIndex > collapsedIndex) {
        posMetadata.groupInsertIndex--;
      }
    }
    if (posMetadata.groupInsertIndex > this.mExpGroupMetadataList.size()) {
      return false;
    }
    GroupMetadata expandedGm =
        GroupMetadata.obtain(
            -1,
            -1,
            posMetadata.position.groupPos,
            this.mExpandableListAdapter.getGroupId(posMetadata.position.groupPos));
    this.mExpGroupMetadataList.add(posMetadata.groupInsertIndex, expandedGm);
    refreshExpGroupMetadataList(false, false);
    notifyDataSetChanged();
    this.mExpandableListAdapter.onGroupExpanded(expandedGm.gPos);
    return true;
  }

  public boolean isGroupExpanded(int groupPosition) {
    for (int i = this.mExpGroupMetadataList.size() - 1; i >= 0; i--) {
      GroupMetadata groupMetadata = this.mExpGroupMetadataList.get(i);
      if (groupMetadata.gPos == groupPosition) {
        return true;
      }
    }
    return false;
  }

  public void setMaxExpGroupCount(int maxExpGroupCount) {
    this.mMaxExpGroupCount = maxExpGroupCount;
  }

  ExpandableListAdapter getAdapter() {
    return this.mExpandableListAdapter;
  }

  @Override // android.widget.Filterable
  public Filter getFilter() {
    ExpandableListAdapter adapter = getAdapter();
    if (adapter instanceof Filterable) {
      return ((Filterable) adapter).getFilter();
    }
    return null;
  }

  ArrayList<GroupMetadata> getExpandedGroupMetadataList() {
    return this.mExpGroupMetadataList;
  }

  void setExpandedGroupMetadataList(ArrayList<GroupMetadata> expandedGroupMetadataList) {
    ExpandableListAdapter expandableListAdapter;
    if (expandedGroupMetadataList == null
        || (expandableListAdapter = this.mExpandableListAdapter) == null) {
      return;
    }
    int numGroups = expandableListAdapter.getGroupCount();
    for (int i = expandedGroupMetadataList.size() - 1; i >= 0; i--) {
      if (expandedGroupMetadataList.get(i).gPos >= numGroups) {
        return;
      }
    }
    this.mExpGroupMetadataList = expandedGroupMetadataList;
    refreshExpGroupMetadataList(true, false);
  }

  @Override // android.widget.BaseAdapter, android.widget.Adapter
  public boolean isEmpty() {
    ExpandableListAdapter adapter = getAdapter();
    if (adapter != null) {
      return adapter.isEmpty();
    }
    return true;
  }

  int findGroupPosition(long groupIdToMatch, int seedGroupPosition) {
    int count = this.mExpandableListAdapter.getGroupCount();
    if (count == 0 || groupIdToMatch == Long.MIN_VALUE) {
      return -1;
    }
    int seedGroupPosition2 = Math.min(count - 1, Math.max(0, seedGroupPosition));
    long endTime = SystemClock.uptimeMillis() + 100;
    int first = seedGroupPosition2;
    int last = seedGroupPosition2;
    boolean next = false;
    ExpandableListAdapter adapter = getAdapter();
    if (adapter == null) {
      return -1;
    }
    while (SystemClock.uptimeMillis() <= endTime) {
      long rowId = adapter.getGroupId(seedGroupPosition2);
      if (rowId == groupIdToMatch) {
        return seedGroupPosition2;
      }
      boolean hitLast = last == count + (-1);
      boolean hitFirst = first == 0;
      if (hitLast && hitFirst) {
        break;
      }
      if (hitFirst || (next && !hitLast)) {
        last++;
        seedGroupPosition2 = last;
        next = false;
      } else if (hitLast || (!next && !hitFirst)) {
        first--;
        seedGroupPosition2 = first;
        next = true;
      }
    }
    return -1;
  }

  protected class MyDataSetObserver extends DataSetObserver {
    protected MyDataSetObserver() {}

    @Override // android.database.DataSetObserver
    public void onChanged() {
      SemExpandableListConnector.this.refreshExpGroupMetadataList(true, true);
      SemExpandableListConnector.this.notifyDataSetChanged();
    }

    @Override // android.database.DataSetObserver
    public void onInvalidated() {
      SemExpandableListConnector.this.refreshExpGroupMetadataList(true, true);
      SemExpandableListConnector.this.notifyDataSetInvalidated();
    }
  }

  static class GroupMetadata implements Parcelable, Comparable<GroupMetadata> {
    public static final Parcelable.Creator<GroupMetadata> CREATOR =
        new Parcelable.Creator<
            GroupMetadata>() { // from class:
                               // android.widget.SemExpandableListConnector.GroupMetadata.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public GroupMetadata createFromParcel(Parcel in) {
            GroupMetadata gm =
                GroupMetadata.obtain(in.readInt(), in.readInt(), in.readInt(), in.readLong());
            return gm;
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public GroupMetadata[] newArray(int size) {
            return new GroupMetadata[size];
          }
        };
    static final int REFRESH = -1;
    int flPos;
    long gId;
    int gPos;
    int lastChildFlPos;

    private GroupMetadata() {}

    static GroupMetadata obtain(int flPos, int lastChildFlPos, int gPos, long gId) {
      GroupMetadata gm = new GroupMetadata();
      gm.flPos = flPos;
      gm.lastChildFlPos = lastChildFlPos;
      gm.gPos = gPos;
      gm.gId = gId;
      return gm;
    }

    @Override // java.lang.Comparable
    public int compareTo(GroupMetadata another) {
      if (another == null) {
        throw new IllegalArgumentException();
      }
      return this.gPos - another.gPos;
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
      return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.flPos);
      dest.writeInt(this.lastChildFlPos);
      dest.writeInt(this.gPos);
      dest.writeLong(this.gId);
    }
  }

  public static class PositionMetadata {
    private static final int MAX_POOL_SIZE = 5;
    private static ArrayList<PositionMetadata> sPool = new ArrayList<>(5);
    public int groupInsertIndex;
    public GroupMetadata groupMetadata;
    public SemExpandableListPosition position;

    private void resetState() {
      SemExpandableListPosition semExpandableListPosition = this.position;
      if (semExpandableListPosition != null) {
        semExpandableListPosition.recycle();
        this.position = null;
      }
      this.groupMetadata = null;
      this.groupInsertIndex = 0;
    }

    private PositionMetadata() {}

    static PositionMetadata obtain(
        int flatListPos,
        int type,
        int groupPos,
        int childPos,
        GroupMetadata groupMetadata,
        int groupInsertIndex) {
      PositionMetadata pm = getRecycledOrCreate();
      pm.position = SemExpandableListPosition.obtain(type, groupPos, childPos, flatListPos);
      pm.groupMetadata = groupMetadata;
      pm.groupInsertIndex = groupInsertIndex;
      return pm;
    }

    private static PositionMetadata getRecycledOrCreate() {
      synchronized (sPool) {
        if (sPool.size() > 0) {
          PositionMetadata pm = sPool.remove(0);
          pm.resetState();
          return pm;
        }
        return new PositionMetadata();
      }
    }

    public void recycle() {
      resetState();
      synchronized (sPool) {
        if (sPool.size() < 5) {
          sPool.add(this);
        }
      }
    }

    public boolean isExpanded() {
      return this.groupMetadata != null;
    }
  }
}
