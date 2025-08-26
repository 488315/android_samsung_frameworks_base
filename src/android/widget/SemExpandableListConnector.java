package android.widget;

import android.database.DataSetObserver;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collections;

/* loaded from: classes5.dex */
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
        if (expandableListAdapter == null || (dataSetObserver = this.mDataSetObserver) == null || this.mIsRegisteredObserver) {
            return;
        }
        expandableListAdapter.registerDataSetObserver(dataSetObserver);
        this.mIsRegisteredObserver = true;
    }

    void semUnregisterDataSetObserver() {
        DataSetObserver dataSetObserver;
        ExpandableListAdapter expandableListAdapter = this.mExpandableListAdapter;
        if (expandableListAdapter == null || (dataSetObserver = this.mDataSetObserver) == null || !this.mIsRegisteredObserver) {
            return;
        }
        expandableListAdapter.unregisterDataSetObserver(dataSetObserver);
        this.mIsRegisteredObserver = false;
    }

    PositionMetadata getUnflattenedPos(int i) {
        int i2;
        ArrayList<GroupMetadata> arrayList = this.mExpGroupMetadataList;
        int size = arrayList.size();
        int i3 = size - 1;
        if (size == 0) {
            return PositionMetadata.obtain(i, 2, i, -1, null, 0);
        }
        int i4 = 0;
        int i5 = i3;
        int i6 = 0;
        while (i4 <= i5) {
            int i7 = ((i5 - i4) / 2) + i4;
            GroupMetadata groupMetadata = arrayList.get(i7);
            if (i > groupMetadata.lastChildFlPos) {
                i4 = i7 + 1;
            } else if (i < groupMetadata.flPos) {
                i5 = i7 - 1;
            } else {
                if (i == groupMetadata.flPos) {
                    return PositionMetadata.obtain(i, 2, groupMetadata.gPos, -1, groupMetadata, i7);
                }
                if (i <= groupMetadata.lastChildFlPos) {
                    return PositionMetadata.obtain(i, 1, groupMetadata.gPos, i - (groupMetadata.flPos + 1), groupMetadata, i7);
                }
            }
            i6 = i7;
        }
        if (i4 > i6) {
            GroupMetadata groupMetadata2 = arrayList.get(i4 - 1);
            i2 = (i - groupMetadata2.lastChildFlPos) + groupMetadata2.gPos;
        } else if (i5 < i6) {
            i4 = i5 + 1;
            GroupMetadata groupMetadata3 = arrayList.get(i4);
            i2 = groupMetadata3.gPos - (groupMetadata3.flPos - i);
        } else {
            throw new RuntimeException("Unknown state");
        }
        return PositionMetadata.obtain(i, 2, i2, -1, null, i4);
    }

    PositionMetadata getFlattenedPos(SemExpandableListPosition semExpandableListPosition) {
        ArrayList<GroupMetadata> arrayList = this.mExpGroupMetadataList;
        int size = arrayList.size();
        int i = size - 1;
        if (size == 0) {
            return PositionMetadata.obtain(semExpandableListPosition.groupPos, semExpandableListPosition.type, semExpandableListPosition.groupPos, semExpandableListPosition.childPos, null, 0);
        }
        int i2 = 0;
        int i3 = 0;
        while (i3 <= i) {
            int i4 = ((i - i3) / 2) + i3;
            GroupMetadata groupMetadata = arrayList.get(i4);
            if (semExpandableListPosition.groupPos > groupMetadata.gPos) {
                i3 = i4 + 1;
            } else if (semExpandableListPosition.groupPos < groupMetadata.gPos) {
                i = i4 - 1;
            } else if (semExpandableListPosition.groupPos == groupMetadata.gPos) {
                if (semExpandableListPosition.type == 2) {
                    return PositionMetadata.obtain(groupMetadata.flPos, semExpandableListPosition.type, semExpandableListPosition.groupPos, semExpandableListPosition.childPos, groupMetadata, i4);
                }
                if (semExpandableListPosition.type == 1) {
                    return PositionMetadata.obtain(groupMetadata.flPos + semExpandableListPosition.childPos + 1, semExpandableListPosition.type, semExpandableListPosition.groupPos, semExpandableListPosition.childPos, groupMetadata, i4);
                }
                return null;
            }
            i2 = i4;
        }
        if (semExpandableListPosition.type != 2) {
            return null;
        }
        if (i3 > i2) {
            GroupMetadata groupMetadata2 = arrayList.get(i3 - 1);
            return PositionMetadata.obtain(groupMetadata2.lastChildFlPos + (semExpandableListPosition.groupPos - groupMetadata2.gPos), semExpandableListPosition.type, semExpandableListPosition.groupPos, semExpandableListPosition.childPos, null, i3);
        }
        if (i >= i2) {
            return null;
        }
        int i5 = i + 1;
        GroupMetadata groupMetadata3 = arrayList.get(i5);
        return PositionMetadata.obtain(groupMetadata3.flPos - (groupMetadata3.gPos - semExpandableListPosition.groupPos), semExpandableListPosition.type, semExpandableListPosition.groupPos, semExpandableListPosition.childPos, null, i5);
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean areAllItemsEnabled() {
        return this.mExpandableListAdapter.areAllItemsEnabled();
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean isEnabled(int i) {
        PositionMetadata unflattenedPos = getUnflattenedPos(i);
        SemExpandableListPosition semExpandableListPosition = unflattenedPos.position;
        boolean zIsChildSelectable = semExpandableListPosition.type == 1 ? this.mExpandableListAdapter.isChildSelectable(semExpandableListPosition.groupPos, semExpandableListPosition.childPos) : true;
        unflattenedPos.recycle();
        return zIsChildSelectable;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        int groupCount = this.mExpandableListAdapter.getGroupCount();
        if (groupCount == 0) {
            return 0;
        }
        return groupCount + this.mTotalExpChildrenCount;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        Object child;
        PositionMetadata unflattenedPos = getUnflattenedPos(i);
        if (unflattenedPos.position.type == 2) {
            child = this.mExpandableListAdapter.getGroup(unflattenedPos.position.groupPos);
        } else if (unflattenedPos.position.type == 1) {
            child = this.mExpandableListAdapter.getChild(unflattenedPos.position.groupPos, unflattenedPos.position.childPos);
        } else {
            throw new RuntimeException("Flat list position is of unknown type");
        }
        unflattenedPos.recycle();
        return child;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        long combinedChildId;
        PositionMetadata unflattenedPos = getUnflattenedPos(i);
        long groupId = this.mExpandableListAdapter.getGroupId(unflattenedPos.position.groupPos);
        if (unflattenedPos.position.type == 2) {
            combinedChildId = this.mExpandableListAdapter.getCombinedGroupId(groupId);
        } else if (unflattenedPos.position.type == 1) {
            combinedChildId = this.mExpandableListAdapter.getCombinedChildId(groupId, this.mExpandableListAdapter.getChildId(unflattenedPos.position.groupPos, unflattenedPos.position.childPos));
        } else {
            throw new RuntimeException("Flat list position is of unknown type");
        }
        unflattenedPos.recycle();
        return combinedChildId;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        View childView;
        PositionMetadata unflattenedPos = getUnflattenedPos(i);
        ItemDecorator itemDecorator = this.mItemDecorator;
        View viewUnfoldDecoratedView = itemDecorator != null ? itemDecorator.unfoldDecoratedView(view) : view;
        if (unflattenedPos.position.type == 2) {
            childView = this.mExpandableListAdapter.getGroupView(unflattenedPos.position.groupPos, unflattenedPos.isExpanded(), viewUnfoldDecoratedView, viewGroup);
        } else {
            if (unflattenedPos.position.type == 1) {
                childView = this.mExpandableListAdapter.getChildView(unflattenedPos.position.groupPos, unflattenedPos.position.childPos, unflattenedPos.groupMetadata.lastChildFlPos == i, viewUnfoldDecoratedView, viewGroup);
            } else {
                throw new RuntimeException("Flat list position is of unknown type");
            }
        }
        ItemDecorator itemDecorator2 = this.mItemDecorator;
        if (itemDecorator2 != null) {
            childView = itemDecorator2.onItemDecorate(view, childView, unflattenedPos);
        }
        unflattenedPos.recycle();
        return childView;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int i) {
        int groupTypeCount;
        PositionMetadata unflattenedPos = getUnflattenedPos(i);
        SemExpandableListPosition semExpandableListPosition = unflattenedPos.position;
        ExpandableListAdapter expandableListAdapter = this.mExpandableListAdapter;
        if (expandableListAdapter instanceof HeterogeneousExpandableList) {
            HeterogeneousExpandableList heterogeneousExpandableList = (HeterogeneousExpandableList) expandableListAdapter;
            if (semExpandableListPosition.type == 2) {
                groupTypeCount = heterogeneousExpandableList.getGroupType(semExpandableListPosition.groupPos);
            } else {
                groupTypeCount = heterogeneousExpandableList.getGroupTypeCount() + heterogeneousExpandableList.getChildType(semExpandableListPosition.groupPos, semExpandableListPosition.childPos);
            }
        } else {
            groupTypeCount = semExpandableListPosition.type == 2 ? 0 : 1;
        }
        unflattenedPos.recycle();
        return groupTypeCount;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        ExpandableListAdapter expandableListAdapter = this.mExpandableListAdapter;
        if (!(expandableListAdapter instanceof HeterogeneousExpandableList)) {
            return 2;
        }
        HeterogeneousExpandableList heterogeneousExpandableList = (HeterogeneousExpandableList) expandableListAdapter;
        return heterogeneousExpandableList.getGroupTypeCount() + heterogeneousExpandableList.getChildTypeCount();
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        return this.mExpandableListAdapter.hasStableIds();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshExpGroupMetadataList(boolean z, boolean z2) {
        int childrenCount;
        ArrayList<GroupMetadata> arrayList = this.mExpGroupMetadataList;
        int size = arrayList.size();
        this.mTotalExpChildrenCount = 0;
        if (z2) {
            boolean z3 = false;
            for (int i = size - 1; i >= 0; i--) {
                GroupMetadata groupMetadata = arrayList.get(i);
                int iFindGroupPosition = findGroupPosition(groupMetadata.gId, groupMetadata.gPos);
                if (iFindGroupPosition != groupMetadata.gPos) {
                    if (iFindGroupPosition == -1) {
                        arrayList.remove(i);
                        size--;
                    }
                    groupMetadata.gPos = iFindGroupPosition;
                    if (!z3) {
                        z3 = true;
                    }
                }
            }
            if (z3) {
                Collections.sort(arrayList);
            }
        }
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            GroupMetadata groupMetadata2 = arrayList.get(i4);
            if (groupMetadata2.lastChildFlPos == -1 || z) {
                childrenCount = this.mExpandableListAdapter.getChildrenCount(groupMetadata2.gPos);
            } else {
                childrenCount = groupMetadata2.lastChildFlPos - groupMetadata2.flPos;
            }
            this.mTotalExpChildrenCount += childrenCount;
            int i5 = i2 + (groupMetadata2.gPos - i3);
            i3 = groupMetadata2.gPos;
            groupMetadata2.flPos = i5;
            i2 = i5 + childrenCount;
            groupMetadata2.lastChildFlPos = i2;
        }
    }

    boolean collapseGroup(int i) {
        SemExpandableListPosition semExpandableListPositionObtain = SemExpandableListPosition.obtain(2, i, -1, -1);
        PositionMetadata flattenedPos = getFlattenedPos(semExpandableListPositionObtain);
        semExpandableListPositionObtain.recycle();
        if (flattenedPos == null) {
            return false;
        }
        boolean zCollapseGroup = collapseGroup(flattenedPos);
        flattenedPos.recycle();
        return zCollapseGroup;
    }

    boolean collapseGroup(PositionMetadata positionMetadata) {
        if (positionMetadata.groupMetadata == null) {
            return false;
        }
        this.mExpGroupMetadataList.remove(positionMetadata.groupMetadata);
        refreshExpGroupMetadataList(false, false);
        notifyDataSetChanged();
        this.mExpandableListAdapter.onGroupCollapsed(positionMetadata.groupMetadata.gPos);
        return true;
    }

    boolean expandGroup(int i) {
        SemExpandableListPosition semExpandableListPositionObtain = SemExpandableListPosition.obtain(2, i, -1, -1);
        PositionMetadata flattenedPos = getFlattenedPos(semExpandableListPositionObtain);
        if (flattenedPos == null) {
            return false;
        }
        semExpandableListPositionObtain.recycle();
        boolean zExpandGroup = expandGroup(flattenedPos);
        flattenedPos.recycle();
        return zExpandGroup;
    }

    boolean expandGroup(PositionMetadata positionMetadata) {
        if (positionMetadata.position.groupPos < 0) {
            throw new RuntimeException("Need group");
        }
        if (this.mMaxExpGroupCount == 0 || positionMetadata.groupMetadata != null) {
            return false;
        }
        if (this.mExpGroupMetadataList.size() >= this.mMaxExpGroupCount) {
            GroupMetadata groupMetadata = this.mExpGroupMetadataList.get(0);
            int iIndexOf = this.mExpGroupMetadataList.indexOf(groupMetadata);
            collapseGroup(groupMetadata.gPos);
            if (positionMetadata.groupInsertIndex > iIndexOf) {
                positionMetadata.groupInsertIndex--;
            }
        }
        if (positionMetadata.groupInsertIndex > this.mExpGroupMetadataList.size()) {
            return false;
        }
        GroupMetadata groupMetadataObtain = GroupMetadata.obtain(-1, -1, positionMetadata.position.groupPos, this.mExpandableListAdapter.getGroupId(positionMetadata.position.groupPos));
        this.mExpGroupMetadataList.add(positionMetadata.groupInsertIndex, groupMetadataObtain);
        refreshExpGroupMetadataList(false, false);
        notifyDataSetChanged();
        this.mExpandableListAdapter.onGroupExpanded(groupMetadataObtain.gPos);
        return true;
    }

    public boolean isGroupExpanded(int i) {
        for (int size = this.mExpGroupMetadataList.size() - 1; size >= 0; size--) {
            if (this.mExpGroupMetadataList.get(size).gPos == i) {
                return true;
            }
        }
        return false;
    }

    public void setMaxExpGroupCount(int i) {
        this.mMaxExpGroupCount = i;
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

    void setExpandedGroupMetadataList(ArrayList<GroupMetadata> arrayList) {
        ExpandableListAdapter expandableListAdapter;
        if (arrayList == null || (expandableListAdapter = this.mExpandableListAdapter) == null) {
            return;
        }
        int groupCount = expandableListAdapter.getGroupCount();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (arrayList.get(size).gPos >= groupCount) {
                return;
            }
        }
        this.mExpGroupMetadataList = arrayList;
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

    int findGroupPosition(long j, int i) {
        int groupCount = this.mExpandableListAdapter.getGroupCount();
        if (groupCount == 0 || j == Long.MIN_VALUE) {
            return -1;
        }
        int i2 = groupCount - 1;
        int iMin = Math.min(i2, Math.max(0, i));
        long jUptimeMillis = SystemClock.uptimeMillis() + 100;
        ExpandableListAdapter adapter = getAdapter();
        if (adapter == null) {
            return -1;
        }
        int i3 = iMin;
        int i4 = i3;
        boolean z = false;
        while (SystemClock.uptimeMillis() <= jUptimeMillis) {
            if (adapter.getGroupId(iMin) != j) {
                boolean z2 = i3 == i2;
                boolean z3 = i4 == 0;
                if (z2 && z3) {
                    break;
                }
                if (z3 || (z && !z2)) {
                    i3++;
                    z = false;
                    iMin = i3;
                } else if (z2 || (!z && !z3)) {
                    i4--;
                    z = true;
                    iMin = i4;
                }
            } else {
                return iMin;
            }
        }
        return -1;
    }

    protected class MyDataSetObserver extends DataSetObserver {
        protected MyDataSetObserver() {
        }

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
        public static final Parcelable.Creator<GroupMetadata> CREATOR = new Parcelable.Creator<GroupMetadata>() { // from class: android.widget.SemExpandableListConnector.GroupMetadata.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public GroupMetadata createFromParcel(Parcel parcel) {
                return GroupMetadata.obtain(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readLong());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public GroupMetadata[] newArray(int i) {
                return new GroupMetadata[i];
            }
        };
        static final int REFRESH = -1;
        int flPos;
        long gId;
        int gPos;
        int lastChildFlPos;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private GroupMetadata() {
        }

        static GroupMetadata obtain(int i, int i2, int i3, long j) {
            GroupMetadata groupMetadata = new GroupMetadata();
            groupMetadata.flPos = i;
            groupMetadata.lastChildFlPos = i2;
            groupMetadata.gPos = i3;
            groupMetadata.gId = j;
            return groupMetadata;
        }

        @Override // java.lang.Comparable
        public int compareTo(GroupMetadata groupMetadata) {
            if (groupMetadata == null) {
                throw new IllegalArgumentException();
            }
            return this.gPos - groupMetadata.gPos;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.flPos);
            parcel.writeInt(this.lastChildFlPos);
            parcel.writeInt(this.gPos);
            parcel.writeLong(this.gId);
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

        private PositionMetadata() {
        }

        static PositionMetadata obtain(int i, int i2, int i3, int i4, GroupMetadata groupMetadata, int i5) {
            PositionMetadata recycledOrCreate = getRecycledOrCreate();
            recycledOrCreate.position = SemExpandableListPosition.obtain(i2, i3, i4, i);
            recycledOrCreate.groupMetadata = groupMetadata;
            recycledOrCreate.groupInsertIndex = i5;
            return recycledOrCreate;
        }

        private static PositionMetadata getRecycledOrCreate() {
            synchronized (sPool) {
                if (sPool.size() > 0) {
                    PositionMetadata positionMetadataRemove = sPool.remove(0);
                    positionMetadataRemove.resetState();
                    return positionMetadataRemove;
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
