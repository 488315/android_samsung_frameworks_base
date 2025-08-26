package android.hardware.display;

import android.graphics.PointF;
import android.graphics.RectF;
import android.hardware.display.DisplayTopology;
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
        TreeNode treeNodeFindDisplay = findDisplay(i, this.mRoot);
        if (treeNodeFindDisplay == null) {
            return false;
        }
        if (floatEquals(treeNodeFindDisplay.mWidth, f) && floatEquals(treeNodeFindDisplay.mHeight, f2)) {
            return false;
        }
        treeNodeFindDisplay.mWidth = f;
        treeNodeFindDisplay.mHeight = f2;
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
        Map<Integer, TreeNode> mapAllNodesIdMap = allNodesIdMap();
        if (mapAllNodesIdMap.size() != map.size()) {
            throw new IllegalArgumentException("newPos has wrong number of entries: " + map);
        }
        this.mRoot.mChildren.clear();
        Iterator<TreeNode> it = mapAllNodesIdMap.values().iterator();
        while (it.hasNext()) {
            it.next().mChildren.clear();
        }
        mapAllNodesIdMap.remove(Integer.valueOf(this.mRoot.mDisplayId));
        while (!mapAllNodesIdMap.isEmpty()) {
            Iterator<TreeNode> it2 = mapAllNodesIdMap.values().iterator();
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
                    float fMin = Math.min(width2, width) - Math.max(pointF2.x, pointF.x);
                    float fMin2 = Math.min(height2, height) - Math.max(pointF2.y, pointF.y);
                    if (fMin > fMin2) {
                        float fMin3 = Math.min(next.getWidth(), treeNode3.getWidth()) - fMin;
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
                        f3 = fMin3;
                    } else {
                        float fMin4 = Math.min(next.getHeight(), treeNode3.getHeight()) - fMin2;
                        if (pointF.x < pointF2.x) {
                            f = width - pointF2.x;
                            i = 0;
                        } else {
                            f = width2 - pointF.x;
                            i = 2;
                        }
                        f2 = pointF.y - pointF2.y;
                        f3 = f;
                        f4 = fMin4;
                    }
                    double d2 = d;
                    TreeNode treeNode5 = treeNode2;
                    double d3 = f3;
                    PointF pointF3 = pointF;
                    float f6 = width;
                    double dHypot = Math.hypot(d3, f4);
                    if (dHypot >= d2) {
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
                        d = dHypot;
                        treeNode2 = next;
                    }
                }
            }
            treeNode.addChild(treeNode2);
            if (mapAllNodesIdMap.remove(Integer.valueOf(treeNode2.mDisplayId)) == null) {
                throw new IllegalStateException("child not in pending set! " + treeNode2);
            }
            arrayList.add(treeNode2);
        }
        normalize();
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x01d0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x018e A[PHI: r0
      0x018e: PHI (r0v13 int) = (r0v6 int), (r0v15 int) binds: [B:73:0x018c, B:68:0x0177] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0190 A[PHI: r0
      0x0190: PHI (r0v7 int) = (r0v6 int), (r0v6 int), (r0v15 int), (r0v15 int) binds: [B:71:0x0182, B:73:0x018c, B:66:0x016d, B:68:0x0177] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0194  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void normalize() {
        TreeNode treeNode;
        boolean zFloatEquals;
        int i;
        boolean z;
        float f;
        float f2;
        float f3;
        float f4;
        TreeNode treeNode2 = this.mRoot;
        if (treeNode2 == null) {
            return;
        }
        clampOffsets(treeNode2);
        final HashMap map = new HashMap();
        final HashMap map2 = new HashMap();
        HashMap map3 = new HashMap();
        getInfo(map, map2, map3, this.mRoot, 0.0f, 0.0f, 0);
        Comparator comparator = new Comparator() { // from class: android.hardware.display.DisplayTopology$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return DisplayTopology.lambda$normalize$0(map2, map, (DisplayTopology.TreeNode) obj, (DisplayTopology.TreeNode) obj2);
            }
        };
        ArrayList arrayList = new ArrayList(map.keySet());
        arrayList.sort(comparator);
        int i2 = 1;
        int i3 = 1;
        while (i3 < arrayList.size()) {
            TreeNode treeNode3 = (TreeNode) arrayList.get(i3);
            TreeNode treeNode4 = null;
            float f5 = 0.0f;
            float f6 = 0.0f;
            int i4 = 0;
            while (true) {
                if (i4 >= i3) {
                    break;
                }
                TreeNode treeNode5 = (TreeNode) arrayList.get(i4);
                RectF rectF = (RectF) map.get(treeNode5);
                RectF rectF2 = (RectF) map.get(treeNode3);
                if (RectF.intersects(rectF, rectF2)) {
                    if (rectF2.left >= 0.0f) {
                        f = rectF.right;
                        f2 = rectF2.left;
                    } else {
                        f = rectF.left;
                        f2 = rectF2.right;
                    }
                    float f7 = f - f2;
                    if (rectF2.top >= 0.0f) {
                        f3 = rectF.bottom;
                        f4 = rectF2.top;
                    } else {
                        f3 = rectF.top;
                        f4 = rectF2.bottom;
                    }
                    float f8 = f3 - f4;
                    if (Math.abs(f7) <= Math.abs(f8)) {
                        rectF2.left += f7;
                        rectF2.right += f7;
                        if (treeNode3.mPosition == i2 || treeNode3.mPosition == 3) {
                            treeNode3.mOffset += f7;
                        }
                        f6 = 0.0f;
                        f5 = f7;
                    } else {
                        rectF2.top += f8;
                        rectF2.bottom += f8;
                        if (treeNode3.mPosition == 0 || treeNode3.mPosition == 2) {
                            treeNode3.mOffset += f8;
                        }
                        f6 = f8;
                        f5 = 0.0f;
                    }
                    treeNode4 = treeNode5;
                }
                i4++;
            }
            if (treeNode4 != null && (treeNode = (TreeNode) map3.get(treeNode3)) != treeNode4) {
                RectF rectF3 = (RectF) map.get(treeNode3);
                RectF rectF4 = (RectF) map.get(treeNode);
                int i5 = treeNode3.mPosition;
                if (i5 == 0) {
                    zFloatEquals = floatEquals(rectF4.left, rectF3.right);
                } else if (i5 == i2) {
                    zFloatEquals = floatEquals(rectF4.top, rectF3.bottom);
                } else if (i5 == 2) {
                    zFloatEquals = floatEquals(rectF4.right, rectF3.left);
                } else if (i5 == 3) {
                    zFloatEquals = floatEquals(rectF4.bottom, rectF3.top);
                } else {
                    throw new IllegalStateException("Unexpected value: " + treeNode3.mPosition);
                }
                int i6 = treeNode3.mPosition;
                if (i6 == 0) {
                    i = 3;
                    z = rectF3.bottom + 1.0E-4f <= rectF4.top && rectF3.top < rectF4.bottom + 1.0E-4f;
                    if (!(z & zFloatEquals)) {
                        treeNode.mChildren.remove(treeNode3);
                        RectF rectF5 = (RectF) map.get(treeNode4);
                        treeNode4.mChildren.add(treeNode3);
                        if (f5 != 0.0f) {
                            treeNode3.mPosition = f5 <= 0.0f ? 0 : 2;
                            treeNode3.mOffset = rectF3.top - rectF5.top;
                        } else if (f6 != 0.0f) {
                            treeNode3.mPosition = f6 > 0.0f ? i : 1;
                            treeNode3.mOffset = rectF3.left - rectF5.left;
                        }
                    }
                } else {
                    if (i6 != i2) {
                        if (i6 != 2) {
                            i = 3;
                            if (i6 != 3) {
                                throw new IllegalStateException("Unexpected value: " + treeNode3.mPosition);
                            }
                        }
                        i = 3;
                        if (rectF3.bottom + 1.0E-4f <= rectF4.top) {
                        }
                        if (!(z & zFloatEquals)) {
                        }
                    } else {
                        i = 3;
                    }
                    if (rectF3.right + 1.0E-4f <= rectF4.left || rectF3.left >= rectF4.right + 1.0E-4f) {
                    }
                    if (!(z & zFloatEquals)) {
                    }
                }
            }
            i3++;
            i2 = 1;
        }
        Comparator comparator2 = new Comparator() { // from class: android.hardware.display.DisplayTopology$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Integer.compare(((DisplayTopology.TreeNode) obj).mDisplayId, ((DisplayTopology.TreeNode) obj2).mDisplayId);
            }
        };
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((TreeNode) it.next()).mChildren.sort(comparator2);
        }
    }

    static /* synthetic */ int lambda$normalize$0(Map map, Map map2, TreeNode treeNode, TreeNode treeNode2) {
        if (treeNode == treeNode2) {
            return 0;
        }
        int iCompare = Integer.compare(((Integer) map.get(treeNode)).intValue(), ((Integer) map.get(treeNode2)).intValue());
        if (iCompare != 0) {
            return iCompare;
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
        HashMap map = new HashMap();
        getInfo(map, null, null, this.mRoot, 0.0f, 0.0f, 0);
        SparseArray<RectF> sparseArray = new SparseArray<>();
        for (Map.Entry entry : map.entrySet()) {
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
            } else if (i == 1) {
                f2 = (f - treeNode.mWidth) + treeNode2.mOffset + treeNode2.mWidth;
            } else if (i != 2) {
                if (i != 3) {
                    throw new IllegalStateException("Unexpected value: " + treeNode2.mPosition);
                }
                f2 = (f - treeNode.mWidth) + treeNode2.mOffset + treeNode2.mWidth;
            } else {
                f2 = treeNode2.mWidth + f;
            }
            Pair<TreeNode, Float> pairFindRightMostDisplay = findRightMostDisplay(treeNode2, f2);
            if (pairFindRightMostDisplay.second.floatValue() > pair.second.floatValue()) {
                pair = new Pair<>(pairFindRightMostDisplay.first, pairFindRightMostDisplay.second);
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
            TreeNode treeNodeFindDisplay = findDisplay(i, (TreeNode) it.next());
            if (treeNodeFindDisplay != null) {
                return treeNodeFindDisplay;
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
                return DisplayTopology.lambda$getGraph$2(absoluteBounds, (Integer) obj, (Integer) obj2);
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
            int iIntValue = ((Integer) it.next()).intValue();
            if (sparseIntArray.get(iIntValue) == 0) {
                Slog.e(TAG, "Cannot construct graph, no density for display " + iIntValue);
                return null;
            }
            sparseArray2.append(iIntValue, new ArrayList(Math.min(10, arrayList2.size())));
        }
        int i2 = 0;
        while (i2 < arrayList2.size()) {
            int iIntValue2 = ((Integer) arrayList2.get(i2)).intValue();
            RectF rectF = absoluteBounds.get(iIntValue2);
            List list = (List) sparseArray2.get(iIntValue2);
            i2++;
            int i3 = i2;
            while (true) {
                if (i3 >= arrayList2.size()) {
                    sparseArray = absoluteBounds;
                    arrayList = arrayList2;
                    break;
                }
                int iIntValue3 = ((Integer) arrayList2.get(i3)).intValue();
                RectF rectF2 = absoluteBounds.get(iIntValue3);
                List list2 = (List) sparseArray2.get(iIntValue3);
                List<Pair<Integer, Float>> listFindDisplayPlacements = findDisplayPlacements(rectF, rectF2);
                List<Pair<Integer, Float>> listFindDisplayPlacements2 = findDisplayPlacements(rectF2, rectF);
                for (Pair<Integer, Float> pair : listFindDisplayPlacements) {
                    list.add(new DisplayTopologyGraph.AdjacentDisplay(iIntValue3, pair.first.intValue(), pair.second.floatValue()));
                    absoluteBounds = absoluteBounds;
                    arrayList2 = arrayList2;
                }
                sparseArray = absoluteBounds;
                arrayList = arrayList2;
                for (Pair<Integer, Float> pair2 : listFindDisplayPlacements2) {
                    list2.add(new DisplayTopologyGraph.AdjacentDisplay(iIntValue2, pair2.first.intValue(), pair2.second.floatValue()));
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
            int iKeyAt = sparseArray2.keyAt(i4);
            displayNodeArr[i4] = new DisplayTopologyGraph.DisplayNode(iKeyAt, sparseIntArray.get(iKeyAt), (DisplayTopologyGraph.AdjacentDisplay[]) ((List) sparseArray2.valueAt(i4)).toArray(new DisplayTopologyGraph.AdjacentDisplay[0]));
        }
        return new DisplayTopologyGraph(this.mPrimaryDisplayId, displayNodeArr);
    }

    static /* synthetic */ int lambda$getGraph$2(SparseArray sparseArray, Integer num, Integer num2) {
        RectF rectF = (RectF) sparseArray.get(num.intValue());
        RectF rectF2 = (RectF) sparseArray.get(num2.intValue());
        int iCompare = Float.compare(rectF.left, rectF2.left);
        return iCompare != 0 ? iCompare : Float.compare(rectF.top, rectF2.top);
    }

    private static boolean floatEquals(float f, float f2) {
        if (f != f2) {
            return (Float.isNaN(f) && Float.isNaN(f2)) || Math.abs(f - f2) < 1.0E-4f;
        }
        return true;
    }

    private Map<Integer, TreeNode> allNodesIdMap() {
        ArrayDeque arrayDeque = new ArrayDeque();
        HashMap map = new HashMap();
        arrayDeque.push(this.mRoot);
        do {
            TreeNode treeNode = (TreeNode) arrayDeque.pop();
            map.put(Integer.valueOf(treeNode.mDisplayId), treeNode);
            arrayDeque.addAll(treeNode.mChildren);
        } while (!arrayDeque.isEmpty());
        return map;
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
