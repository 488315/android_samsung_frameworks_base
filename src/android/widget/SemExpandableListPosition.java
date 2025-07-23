package android.widget;

import java.util.ArrayList;

/* loaded from: classes5.dex */
class SemExpandableListPosition {
    public static final int CHILD = 1;
    public static final int GROUP = 2;
    private static final int MAX_POOL_SIZE = 5;
    private static ArrayList<SemExpandableListPosition> sPool = new ArrayList<>(5);
    public int childPos;
    int flatListPos;
    public int groupPos;
    public int type;

    private void resetState() {
        this.groupPos = 0;
        this.childPos = 0;
        this.flatListPos = 0;
        this.type = 0;
    }

    private SemExpandableListPosition() {
    }

    long getPackedPosition() {
        if (this.type == 1) {
            return SemExpandableListView.getPackedPositionForChild(this.groupPos, this.childPos);
        }
        return SemExpandableListView.getPackedPositionForGroup(this.groupPos);
    }

    static SemExpandableListPosition obtainGroupPosition(int i) {
        return obtain(2, i, 0, 0);
    }

    static SemExpandableListPosition obtainChildPosition(int i, int i2) {
        return obtain(1, i, i2, 0);
    }

    static SemExpandableListPosition obtainPosition(long j) {
        if (j == 4294967295L) {
            return null;
        }
        SemExpandableListPosition recycledOrCreate = getRecycledOrCreate();
        recycledOrCreate.groupPos = SemExpandableListView.getPackedPositionGroup(j);
        if (SemExpandableListView.getPackedPositionType(j) == 1) {
            recycledOrCreate.type = 1;
            recycledOrCreate.childPos = SemExpandableListView.getPackedPositionChild(j);
            return recycledOrCreate;
        }
        recycledOrCreate.type = 2;
        return recycledOrCreate;
    }

    static SemExpandableListPosition obtain(int i, int i2, int i3, int i4) {
        SemExpandableListPosition recycledOrCreate = getRecycledOrCreate();
        recycledOrCreate.type = i;
        recycledOrCreate.groupPos = i2;
        recycledOrCreate.childPos = i3;
        recycledOrCreate.flatListPos = i4;
        return recycledOrCreate;
    }

    private static SemExpandableListPosition getRecycledOrCreate() {
        synchronized (sPool) {
            if (sPool.size() > 0) {
                SemExpandableListPosition remove = sPool.remove(0);
                remove.resetState();
                return remove;
            }
            return new SemExpandableListPosition();
        }
    }

    public void recycle() {
        synchronized (sPool) {
            if (sPool.size() < 5) {
                sPool.add(this);
            }
        }
    }
}
