package android.content.pm.split;

import android.content.pm.parsing.PackageLite;
import android.util.IntArray;
import android.util.SparseArray;
import java.lang.Exception;
import java.util.Arrays;
import java.util.BitSet;
import libcore.util.EmptyArray;

/* loaded from: classes.dex */
public abstract class SplitDependencyLoader<E extends Exception> {
    private final SparseArray<int[]> mDependencies;

    protected abstract void constructSplit(int i, int[] iArr, int i2) throws Exception;

    protected abstract boolean isSplitCached(int i);

    protected SplitDependencyLoader(SparseArray<int[]> sparseArray) {
        this.mDependencies = sparseArray;
    }

    protected void loadDependenciesForSplit(int i) throws Exception {
        if (isSplitCached(i)) {
            return;
        }
        if (i == 0) {
            constructSplit(0, collectConfigSplitIndices(0), -1);
            return;
        }
        IntArray intArray = new IntArray();
        intArray.add(i);
        while (true) {
            int[] iArr = this.mDependencies.get(i);
            i = (iArr == null || iArr.length <= 0) ? -1 : iArr[0];
            if (i < 0 || isSplitCached(i)) {
                break;
            } else {
                intArray.add(i);
            }
        }
        int size = intArray.size() - 1;
        while (size >= 0) {
            int i2 = intArray.get(size);
            constructSplit(i2, collectConfigSplitIndices(i2), i);
            size--;
            i = i2;
        }
    }

    private int[] collectConfigSplitIndices(int i) {
        int[] iArr = this.mDependencies.get(i);
        if (iArr == null || iArr.length <= 1) {
            return EmptyArray.INT;
        }
        return Arrays.copyOfRange(iArr, 1, iArr.length);
    }

    public static class IllegalDependencyException extends Exception {
        private IllegalDependencyException(String str) {
            super(str);
        }
    }

    private static int[] append(int[] iArr, int i) {
        if (iArr == null) {
            return new int[]{i};
        }
        int[] iArrCopyOf = Arrays.copyOf(iArr, iArr.length + 1);
        iArrCopyOf[iArr.length] = i;
        return iArrCopyOf;
    }

    public static SparseArray<int[]> createDependenciesFromPackage(PackageLite packageLite) throws IllegalDependencyException {
        int i;
        int i2;
        SparseArray<int[]> sparseArray = new SparseArray<>();
        sparseArray.put(0, new int[]{-1});
        int i3 = 0;
        while (true) {
            if (i3 < packageLite.getSplitNames().length) {
                if (packageLite.getIsFeatureSplits()[i3]) {
                    String str = packageLite.getUsesSplitNames()[i3];
                    if (str != null) {
                        int iBinarySearch = Arrays.binarySearch(packageLite.getSplitNames(), str);
                        if (iBinarySearch < 0) {
                            throw new IllegalDependencyException("Split '" + packageLite.getSplitNames()[i3] + "' requires split '" + str + "', which is missing.");
                        }
                        i2 = iBinarySearch + 1;
                    } else {
                        i2 = 0;
                    }
                    sparseArray.put(i3 + 1, new int[]{i2});
                }
                i3++;
            } else {
                int length = packageLite.getSplitNames().length;
                for (int i4 = 0; i4 < length; i4++) {
                    if (!packageLite.getIsFeatureSplits()[i4]) {
                        String str2 = packageLite.getConfigForSplit()[i4];
                        if (str2 != null) {
                            int iBinarySearch2 = Arrays.binarySearch(packageLite.getSplitNames(), str2);
                            if (iBinarySearch2 < 0) {
                                throw new IllegalDependencyException("Split '" + packageLite.getSplitNames()[i4] + "' targets split '" + str2 + "', which is missing.");
                            }
                            if (!packageLite.getIsFeatureSplits()[iBinarySearch2]) {
                                throw new IllegalDependencyException("Split '" + packageLite.getSplitNames()[i4] + "' declares itself as configuration split for a non-feature split '" + packageLite.getSplitNames()[iBinarySearch2] + "'");
                            }
                            i = iBinarySearch2 + 1;
                        } else {
                            i = 0;
                        }
                        sparseArray.put(i, append(sparseArray.get(i), i4 + 1));
                    }
                }
                BitSet bitSet = new BitSet();
                int size = sparseArray.size();
                for (int i5 = 0; i5 < size; i5++) {
                    int iKeyAt = sparseArray.keyAt(i5);
                    bitSet.clear();
                    while (iKeyAt != -1) {
                        if (bitSet.get(iKeyAt)) {
                            throw new IllegalDependencyException("Cycle detected in split dependencies.");
                        }
                        bitSet.set(iKeyAt);
                        int[] iArr = sparseArray.get(iKeyAt);
                        iKeyAt = iArr != null ? iArr[0] : -1;
                    }
                }
                return sparseArray;
            }
        }
    }
}
