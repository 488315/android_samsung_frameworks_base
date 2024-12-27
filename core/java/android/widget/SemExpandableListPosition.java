package android.widget;

import java.util.ArrayList;

/* loaded from: classes4.dex */
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

    private SemExpandableListPosition() {}

    long getPackedPosition() {
        if (this.type == 1) {
            return SemExpandableListView.getPackedPositionForChild(this.groupPos, this.childPos);
        }
        return SemExpandableListView.getPackedPositionForGroup(this.groupPos);
    }

    static SemExpandableListPosition obtainGroupPosition(int groupPosition) {
        return obtain(2, groupPosition, 0, 0);
    }

    static SemExpandableListPosition obtainChildPosition(int groupPosition, int childPosition) {
        return obtain(1, groupPosition, childPosition, 0);
    }

    static SemExpandableListPosition obtainPosition(long packedPosition) {
        if (packedPosition == 4294967295L) {
            return null;
        }
        SemExpandableListPosition elp = getRecycledOrCreate();
        elp.groupPos = SemExpandableListView.getPackedPositionGroup(packedPosition);
        if (SemExpandableListView.getPackedPositionType(packedPosition) == 1) {
            elp.type = 1;
            elp.childPos = SemExpandableListView.getPackedPositionChild(packedPosition);
        } else {
            elp.type = 2;
        }
        return elp;
    }

    static SemExpandableListPosition obtain(int type, int groupPos, int childPos, int flatListPos) {
        SemExpandableListPosition elp = getRecycledOrCreate();
        elp.type = type;
        elp.groupPos = groupPos;
        elp.childPos = childPos;
        elp.flatListPos = flatListPos;
        return elp;
    }

    private static SemExpandableListPosition getRecycledOrCreate() {
        synchronized (sPool) {
            if (sPool.size() > 0) {
                SemExpandableListPosition elp = sPool.remove(0);
                elp.resetState();
                return elp;
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
