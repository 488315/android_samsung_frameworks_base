package android.hardware.display;

import android.graphics.PointF;
import android.graphics.RectF;
import android.hardware.display.DisplayTopologyGraph;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.IndentingPrintWriter;
import android.util.MathUtils;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.samsung.android.wallpaperbackup.GenerateXML;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public final class DisplayTopology implements Parcelable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final Parcelable.Creator<DisplayTopology> CREATOR = new Parcelable.Creator<DisplayTopology>() { // from class: android.hardware.display.DisplayTopology.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisplayTopology createFromParcel(Parcel parcel) {
            return new DisplayTopology(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisplayTopology[] newArray(int i) {
            return new DisplayTopology[i];
        }
    };
    private static final float EPSILON = 1.0E-4f;
    private static final float MAX_GAP = 5.0f;
    private static final String TAG = "DisplayTopology";
    private int mPrimaryDisplayId;
    private TreeNode mRoot;

    public static float dpToPx(float f, int i) {
        return (f * i) / 160.0f;
    }

    public static float pxToDp(float f, int i) {
        return (f * 160.0f) / i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DisplayTopology() {
        this.mPrimaryDisplayId = -1;
    }

    public DisplayTopology(TreeNode treeNode, int i) {
        this.mPrimaryDisplayId = -1;
        this.mRoot = treeNode;
        if (treeNode != null) {
            treeNode.mPosition = 0;
            this.mRoot.mOffset = 0.0f;
        }
        this.mPrimaryDisplayId = i;
    }

    public DisplayTopology(Parcel parcel) {
        this((TreeNode) parcel.readTypedObject(TreeNode.CREATOR), parcel.readInt());
    }

    public TreeNode getRoot() {
        return this.mRoot;
    }

    public int getPrimaryDisplayId() {
        return this.mPrimaryDisplayId;
    }

    public void addDisplay(int i, float f, float f2) {
        if (findDisplay(i, this.mRoot) != null) {
            return;
        }
        TreeNode treeNode = this.mRoot;
        if (treeNode == null) {
            this.mRoot = new TreeNode(i, f, f2, 0, 0.0f);
            this.mPrimaryDisplayId = i;
        } else if (treeNode.mChildren.isEmpty()) {
            this.mRoot.mChildren.add(new TreeNode(i, f, f2, 0, this.mRoot.getHeight() - f2));
        } else {
            TreeNode treeNode2 = this.mRoot;
            TreeNode treeNode3 = findRightMostDisplay(treeNode2, treeNode2.mWidth).first;
            treeNode3.mChildren.add(new TreeNode(i, f, f2, 2, 0.0f));
        }
    }

    public boolean updateDisplay(int i, float f, float f2) {
        TreeNode findDisplay = findDisplay(i, this.mRoot);
        if (findDisplay == null) {
            return false;
        }
        if (floatEquals(findDisplay.mWidth, f) && floatEquals(findDisplay.mHeight, f2)) {
            return false;
        }
        findDisplay.mWidth = f;
        findDisplay.mHeight = f2;
        normalize();
        Slog.i(TAG, "Display with ID " + i + " updated, new width: " + f + ", new height: " + f2);
        return true;
    }

    public boolean removeDisplay(int i) {
        if (findDisplay(i, this.mRoot) == null) {
            return false;
        }
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.add(this.mRoot);
        this.mRoot = null;
        while (!arrayDeque.isEmpty()) {
            TreeNode treeNode = (TreeNode) arrayDeque.poll();
            if (treeNode.mDisplayId != i) {
                addDisplay(treeNode.mDisplayId, treeNode.mWidth, treeNode.mHeight);
            }
            arrayDeque.addAll(treeNode.mChildren);
        }
        if (this.mPrimaryDisplayId != i) {
            return true;
        }
        TreeNode treeNode2 = this.mRoot;
        if (treeNode2 != null) {
            this.mPrimaryDisplayId = treeNode2.mDisplayId;
            return true;
        }
        this.mPrimaryDisplayId = -1;
        return true;
    }

    public void rearrange(Map<Integer, PointF> map) {
        float f;
        int i;
        float f2;
        float f3;
        float f4;
        float f5;
        int i2;
        if (this.mRoot == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addLast(this.mRoot);
        Map<Integer, TreeNode> allNodesIdMap = allNodesIdMap();
        if (allNodesIdMap.size() != map.size()) {
            throw new IllegalArgumentException("newPos has wrong number of entries: " + map);
        }
        this.mRoot.mChildren.clear();
        Iterator<TreeNode> it = allNodesIdMap.values().iterator();
        while (it.hasNext()) {
            it.next().mChildren.clear();
        }
        allNodesIdMap.remove(Integer.valueOf(this.mRoot.mDisplayId));
        while (!allNodesIdMap.isEmpty()) {
            Iterator<TreeNode> it2 = allNodesIdMap.values().iterator();
            TreeNode treeNode = null;
            double d = Double.POSITIVE_INFINITY;
            TreeNode treeNode2 = null;
            while (it2.hasNext()) {
                TreeNode next = it2.next();
                PointF pointF = map.get(Integer.valueOf(next.mDisplayId));
                float width = pointF.x + next.getWidth();
                float height = pointF.y + next.getHeight();
                Iterator it3 = arrayList.iterator();
                while (it3.hasNext()) {
                    TreeNode treeNode3 = (TreeNode) it3.next();
                    PointF pointF2 = map.get(Integer.valueOf(treeNode3.mDisplayId));
                    float width2 = pointF2.x + treeNode3.getWidth();
                    Iterator<TreeNode> it4 = it2;
                    float height2 = pointF2.y + treeNode3.getHeight();
                    TreeNode treeNode4 = treeNode;
                    float min = Math.min(width2, width) - Math.max(pointF2.x, pointF.x);
                    float min2 = Math.min(height2, height) - Math.max(pointF2.y, pointF.y);
                    if (min > min2) {
                        float min3 = Math.min(next.getWidth(), treeNode3.getWidth()) - min;
                        if (pointF.y < pointF2.y) {
                            f5 = height - pointF2.y;
                            i2 = 1;
                        } else {
                            f5 = height2 - pointF.y;
                            i2 = 3;
                        }
                        f2 = pointF.x - pointF2.x;
                        i = i2;
                        f4 = f5;
                        f3 = min3;
                    } else {
                        float min4 = Math.min(next.getHeight(), treeNode3.getHeight()) - min2;
                        if (pointF.x < pointF2.x) {
                            f = width - pointF2.x;
                            i = 0;
                        } else {
                            f = width2 - pointF.x;
                            i = 2;
                        }
                        f2 = pointF.y - pointF2.y;
                        f3 = f;
                        f4 = min4;
                    }
                    double d2 = d;
                    TreeNode treeNode5 = treeNode2;
                    double d3 = f3;
                    PointF pointF3 = pointF;
                    float f6 = width;
                    double hypot = Math.hypot(d3, f4);
                    if (hypot >= d2) {
                        pointF = pointF3;
                        width = f6;
                        it2 = it4;
                        treeNode2 = treeNode5;
                        treeNode = treeNode4;
                        d = d2;
                    } else {
                        next.mPosition = i;
                        next.mOffset = f2;
                        pointF = pointF3;
                        width = f6;
                        treeNode = treeNode3;
                        it2 = it4;
                        d = hypot;
                        treeNode2 = next;
                    }
                }
            }
            treeNode.addChild(treeNode2);
            if (allNodesIdMap.remove(Integer.valueOf(treeNode2.mDisplayId)) == null) {
                throw new IllegalStateException("child not in pending set! " + treeNode2);
            }
            arrayList.add(treeNode2);
        }
        normalize();
    }

    /* JADX WARN: Code restructure failed: missing block: B:56:0x0177, code lost:
    
        if (r9.left < (r14.right + 1.0E-4f)) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x018e, code lost:
    
        r7 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x018c, code lost:
    
        if (r9.top < (r14.bottom + 1.0E-4f)) goto L74;
     */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01d0 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void normalize() {
        /*
            Method dump skipped, instructions count: 499
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.display.DisplayTopology.normalize():void");
    }

    static /* synthetic */ int lambda$normalize$0(Map map, Map map2, TreeNode treeNode, TreeNode treeNode2) {
        if (treeNode == treeNode2) {
            return 0;
        }
        int compare = Integer.compare(((Integer) map.get(treeNode)).intValue(), ((Integer) map.get(treeNode2)).intValue());
        if (compare != 0) {
            return compare;
        }
        RectF rectF = (RectF) map2.get(treeNode);
        RectF rectF2 = (RectF) map2.get(treeNode2);
        return Double.compare(Math.hypot(rectF.left, rectF.top), Math.hypot(rectF2.left, rectF2.top));
    }

    public DisplayTopology copy() {
        TreeNode treeNode = this.mRoot;
        return new DisplayTopology(treeNode == null ? null : treeNode.copy(), this.mPrimaryDisplayId);
    }

    public SparseArray<RectF> getAbsoluteBounds() {
        HashMap hashMap = new HashMap();
        getInfo(hashMap, null, null, this.mRoot, 0.0f, 0.0f, 0);
        SparseArray<RectF> sparseArray = new SparseArray<>();
        for (Map.Entry entry : hashMap.entrySet()) {
            sparseArray.append(((TreeNode) entry.getKey()).mDisplayId, (RectF) entry.getValue());
        }
        return sparseArray;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mRoot, i);
        parcel.writeInt(this.mPrimaryDisplayId);
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("DisplayTopology:");
        printWriter.println("--------------------");
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mPrimaryDisplayId: " + this.mPrimaryDisplayId);
        indentingPrintWriter.println("Topology tree:");
        if (this.mRoot != null) {
            indentingPrintWriter.increaseIndent();
            this.mRoot.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof DisplayTopology) {
            return obj.toString().equals(toString());
        }
        return false;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        StringWriter stringWriter = new StringWriter();
        dump(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    private static Pair<TreeNode, Float> findRightMostDisplay(TreeNode treeNode, float f) {
        float f2;
        Pair<TreeNode, Float> pair = new Pair<>(treeNode, Float.valueOf(f));
        for (TreeNode treeNode2 : treeNode.mChildren) {
            int i = treeNode2.mPosition;
            if (i == 0) {
                f2 = f - treeNode.mWidth;
            } else {
                if (i != 1) {
                    if (i == 2) {
                        f2 = treeNode2.mWidth + f;
                    } else if (i != 3) {
                        throw new IllegalStateException("Unexpected value: " + treeNode2.mPosition);
                    }
                }
                f2 = (f - treeNode.mWidth) + treeNode2.mOffset + treeNode2.mWidth;
            }
            Pair<TreeNode, Float> findRightMostDisplay = findRightMostDisplay(treeNode2, f2);
            if (findRightMostDisplay.second.floatValue() > pair.second.floatValue()) {
                pair = new Pair<>(findRightMostDisplay.first, findRightMostDisplay.second);
            }
        }
        return pair;
    }

    public static TreeNode findDisplay(int i, TreeNode treeNode) {
        if (treeNode == null) {
            return null;
        }
        if (treeNode.mDisplayId == i) {
            return treeNode;
        }
        Iterator it = treeNode.mChildren.iterator();
        while (it.hasNext()) {
            TreeNode findDisplay = findDisplay(i, (TreeNode) it.next());
            if (findDisplay != null) {
                return findDisplay;
            }
        }
        return null;
    }

    private static void getInfo(Map<TreeNode, RectF> map, Map<TreeNode, Integer> map2, Map<TreeNode, TreeNode> map3, TreeNode treeNode, float f, float f2, int i) {
        Map<TreeNode, RectF> map4;
        Map<TreeNode, Integer> map5;
        Map<TreeNode, TreeNode> map6;
        if (treeNode == null) {
            return;
        }
        if (map != null) {
            map.put(treeNode, new RectF(f, f2, treeNode.mWidth + f, treeNode.mHeight + f2));
        }
        if (map2 != null) {
            map2.put(treeNode, Integer.valueOf(i));
        }
        for (TreeNode treeNode2 : treeNode.mChildren) {
            if (map3 != null) {
                map3.put(treeNode2, treeNode);
            }
            if (treeNode2.mPosition == 0) {
                map4 = map;
                map5 = map2;
                map6 = map3;
                getInfo(map4, map5, map6, treeNode2, f - treeNode2.mWidth, f2 + treeNode2.mOffset, i + 1);
            } else {
                map4 = map;
                map5 = map2;
                map6 = map3;
                if (treeNode2.mPosition == 2) {
                    getInfo(map4, map5, map6, treeNode2, f + treeNode.mWidth, f2 + treeNode2.mOffset, i + 1);
                } else if (treeNode2.mPosition == 1) {
                    getInfo(map4, map5, map6, treeNode2, f + treeNode2.mOffset, f2 - treeNode2.mHeight, i + 1);
                } else if (treeNode2.mPosition == 3) {
                    getInfo(map4, map5, map6, treeNode2, f + treeNode2.mOffset, f2 + treeNode.mHeight, i + 1);
                }
            }
            map = map4;
            map2 = map5;
            map3 = map6;
        }
    }

    private List<Pair<Integer, Float>> findDisplayPlacements(RectF rectF, RectF rectF2) {
        ArrayList arrayList = new ArrayList();
        if (rectF.top <= rectF2.bottom + MAX_GAP && rectF2.top <= rectF.bottom + MAX_GAP) {
            if (MathUtils.abs(rectF.left - rectF2.right) <= MAX_GAP) {
                arrayList.add(new Pair(0, Float.valueOf(rectF2.top - rectF.top)));
            }
            if (MathUtils.abs(rectF.right - rectF2.left) <= MAX_GAP) {
                arrayList.add(new Pair(2, Float.valueOf(rectF2.top - rectF.top)));
            }
        }
        if (rectF.left <= rectF2.right + MAX_GAP && rectF2.left <= rectF.right + MAX_GAP) {
            if (MathUtils.abs(rectF.top - rectF2.bottom) < MAX_GAP) {
                arrayList.add(new Pair(1, Float.valueOf(rectF2.left - rectF.left)));
            }
            if (MathUtils.abs(rectF.bottom - rectF2.top) < MAX_GAP) {
                arrayList.add(new Pair(3, Float.valueOf(rectF2.left - rectF.left)));
            }
        }
        return arrayList;
    }

    public DisplayTopologyGraph getGraph(SparseIntArray sparseIntArray) {
        SparseArray<RectF> sparseArray;
        ArrayList arrayList;
        final SparseArray<RectF> absoluteBounds = getAbsoluteBounds();
        Comparator comparator = new Comparator() { // from class: android.hardware.display.DisplayTopology$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return DisplayTopology.lambda$getGraph$2(SparseArray.this, (Integer) obj, (Integer) obj2);
            }
        };
        ArrayList arrayList2 = new ArrayList(absoluteBounds.size());
        for (int i = 0; i < absoluteBounds.size(); i++) {
            arrayList2.add(Integer.valueOf(absoluteBounds.keyAt(i)));
        }
        arrayList2.sort(comparator);
        SparseArray sparseArray2 = new SparseArray();
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            if (sparseIntArray.get(intValue) == 0) {
                Slog.e(TAG, "Cannot construct graph, no density for display " + intValue);
                return null;
            }
            sparseArray2.append(intValue, new ArrayList(Math.min(10, arrayList2.size())));
        }
        int i2 = 0;
        while (i2 < arrayList2.size()) {
            int intValue2 = ((Integer) arrayList2.get(i2)).intValue();
            RectF rectF = absoluteBounds.get(intValue2);
            List list = (List) sparseArray2.get(intValue2);
            i2++;
            int i3 = i2;
            while (true) {
                if (i3 >= arrayList2.size()) {
                    sparseArray = absoluteBounds;
                    arrayList = arrayList2;
                    break;
                }
                int intValue3 = ((Integer) arrayList2.get(i3)).intValue();
                RectF rectF2 = absoluteBounds.get(intValue3);
                List list2 = (List) sparseArray2.get(intValue3);
                List<Pair<Integer, Float>> findDisplayPlacements = findDisplayPlacements(rectF, rectF2);
                List<Pair<Integer, Float>> findDisplayPlacements2 = findDisplayPlacements(rectF2, rectF);
                for (Pair<Integer, Float> pair : findDisplayPlacements) {
                    list.add(new DisplayTopologyGraph.AdjacentDisplay(intValue3, pair.first.intValue(), pair.second.floatValue()));
                    absoluteBounds = absoluteBounds;
                    arrayList2 = arrayList2;
                }
                sparseArray = absoluteBounds;
                arrayList = arrayList2;
                for (Pair<Integer, Float> pair2 : findDisplayPlacements2) {
                    list2.add(new DisplayTopologyGraph.AdjacentDisplay(intValue2, pair2.first.intValue(), pair2.second.floatValue()));
                }
                if (rectF2.left >= rectF.right + 1.0E-4f) {
                    break;
                }
                i3++;
                absoluteBounds = sparseArray;
                arrayList2 = arrayList;
            }
            absoluteBounds = sparseArray;
            arrayList2 = arrayList;
        }
        int size = sparseArray2.size();
        DisplayTopologyGraph.DisplayNode[] displayNodeArr = new DisplayTopologyGraph.DisplayNode[size];
        for (int i4 = 0; i4 < size; i4++) {
            int keyAt = sparseArray2.keyAt(i4);
            displayNodeArr[i4] = new DisplayTopologyGraph.DisplayNode(keyAt, sparseIntArray.get(keyAt), (DisplayTopologyGraph.AdjacentDisplay[]) ((List) sparseArray2.valueAt(i4)).toArray(new DisplayTopologyGraph.AdjacentDisplay[0]));
        }
        return new DisplayTopologyGraph(this.mPrimaryDisplayId, displayNodeArr);
    }

    static /* synthetic */ int lambda$getGraph$2(SparseArray sparseArray, Integer num, Integer num2) {
        RectF rectF = (RectF) sparseArray.get(num.intValue());
        RectF rectF2 = (RectF) sparseArray.get(num2.intValue());
        int compare = Float.compare(rectF.left, rectF2.left);
        return compare != 0 ? compare : Float.compare(rectF.top, rectF2.top);
    }

    private static boolean floatEquals(float f, float f2) {
        if (f != f2) {
            return (Float.isNaN(f) && Float.isNaN(f2)) || Math.abs(f - f2) < 1.0E-4f;
        }
        return true;
    }

    private Map<Integer, TreeNode> allNodesIdMap() {
        ArrayDeque arrayDeque = new ArrayDeque();
        HashMap hashMap = new HashMap();
        arrayDeque.push(this.mRoot);
        do {
            TreeNode treeNode = (TreeNode) arrayDeque.pop();
            hashMap.put(Integer.valueOf(treeNode.mDisplayId), treeNode);
            arrayDeque.addAll(treeNode.mChildren);
        } while (!arrayDeque.isEmpty());
        return hashMap;
    }

    private void clampOffsets(TreeNode treeNode) {
        if (treeNode == null) {
            return;
        }
        for (TreeNode treeNode2 : treeNode.mChildren) {
            if (treeNode2.mPosition == 0 || treeNode2.mPosition == 2) {
                treeNode2.mOffset = treeNode.mHeight - treeNode2.mHeight;
            } else if (treeNode2.mPosition == 1 || treeNode2.mPosition == 3) {
                treeNode2.mOffset = (treeNode.mWidth / 2.0f) - (treeNode2.mWidth / 2.0f);
            }
            clampOffsets(treeNode2);
        }
    }

    public static final class TreeNode implements Parcelable {
        public static final Parcelable.Creator<TreeNode> CREATOR = new Parcelable.Creator<TreeNode>() { // from class: android.hardware.display.DisplayTopology.TreeNode.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TreeNode createFromParcel(Parcel parcel) {
                return new TreeNode(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TreeNode[] newArray(int i) {
                return new TreeNode[i];
            }
        };
        public static final int POSITION_BOTTOM = 3;
        public static final int POSITION_LEFT = 0;
        public static final int POSITION_RIGHT = 2;
        public static final int POSITION_TOP = 1;
        private final List<TreeNode> mChildren;
        private final int mDisplayId;
        private float mHeight;
        private float mOffset;
        private int mPosition;
        private float mWidth;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Position {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public TreeNode(int i, float f, float f2, int i2, float f3) {
            this(i, f, f2, i2, f3, Collections.EMPTY_LIST);
        }

        public TreeNode(int i, float f, float f2, int i2, float f3, List<TreeNode> list) {
            this.mDisplayId = i;
            this.mWidth = f;
            this.mHeight = f2;
            this.mPosition = i2;
            this.mOffset = f3;
            this.mChildren = new ArrayList(list);
        }

        public TreeNode(Parcel parcel) {
            this(parcel.readInt(), parcel.readFloat(), parcel.readFloat(), parcel.readInt(), parcel.readFloat());
            parcel.readTypedList(this.mChildren, CREATOR);
        }

        public int getDisplayId() {
            return this.mDisplayId;
        }

        public float getWidth() {
            return this.mWidth;
        }

        public float getHeight() {
            return this.mHeight;
        }

        public int getPosition() {
            return this.mPosition;
        }

        public float getOffset() {
            return this.mOffset;
        }

        public List<TreeNode> getChildren() {
            return Collections.unmodifiableList(this.mChildren);
        }

        public TreeNode copy() {
            TreeNode treeNode = new TreeNode(this.mDisplayId, this.mWidth, this.mHeight, this.mPosition, this.mOffset);
            Iterator<TreeNode> it = this.mChildren.iterator();
            while (it.hasNext()) {
                treeNode.mChildren.add(it.next().copy());
            }
            return treeNode;
        }

        public String toString() {
            return "Display {id=" + this.mDisplayId + ", width=" + this.mWidth + ", height=" + this.mHeight + ", position=" + positionToString(this.mPosition) + ", offset=" + this.mOffset + "}";
        }

        public static String positionToString(int i) {
            if (i == 0) {
                return "left";
            }
            if (i == 1) {
                return GenerateXML.TOP;
            }
            if (i == 2) {
                return "right";
            }
            if (i == 3) {
                return GenerateXML.BOTTOM;
            }
            throw new IllegalStateException("Unexpected value: " + i);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mDisplayId);
            parcel.writeFloat(this.mWidth);
            parcel.writeFloat(this.mHeight);
            parcel.writeInt(this.mPosition);
            parcel.writeFloat(this.mOffset);
            parcel.writeTypedList(this.mChildren);
        }

        public void dump(IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println(this);
            indentingPrintWriter.increaseIndent();
            Iterator<TreeNode> it = this.mChildren.iterator();
            while (it.hasNext()) {
                it.next().dump(indentingPrintWriter);
            }
            indentingPrintWriter.decreaseIndent();
        }

        public void addChild(TreeNode treeNode) {
            this.mChildren.add(treeNode);
        }
    }
}
