package com.android.internal.os;

import android.util.SparseArray;
import com.samsung.android.core.pm.runtimemanifest.RuntimeManifestUtils;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.Arrays;
import libcore.util.EmptyArray;

/* loaded from: classes5.dex */
public class CpuScalingPolicies {
    private final SparseArray<int[]> mCpusByPolicy;
    private final SparseArray<int[]> mFreqsByPolicy;
    private final int[] mPolicies;
    private final int mScalingStepCount;

    public CpuScalingPolicies(SparseArray<int[]> sparseArray, SparseArray<int[]> sparseArray2) {
        int[] iArr;
        this.mCpusByPolicy = sparseArray;
        this.mFreqsByPolicy = sparseArray2;
        this.mPolicies = new int[sparseArray.size()];
        int length = 0;
        int i = 0;
        while (true) {
            iArr = this.mPolicies;
            if (i >= iArr.length) {
                break;
            }
            iArr[i] = sparseArray.keyAt(i);
            i++;
        }
        Arrays.sort(iArr);
        for (int size = sparseArray2.size() - 1; size >= 0; size--) {
            length += sparseArray2.valueAt(size).length;
        }
        this.mScalingStepCount = length;
    }

    public int[] getPolicies() {
        return this.mPolicies;
    }

    public int[] getRelatedCpus(int i) {
        return this.mCpusByPolicy.get(i, EmptyArray.INT);
    }

    public int[] getFrequencies(int i) {
        return this.mFreqsByPolicy.get(i, EmptyArray.INT);
    }

    public int getScalingStepCount() {
        return this.mScalingStepCount;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i : this.mPolicies) {
            sb.append(RuntimeManifestUtils.TAG_POLICY);
            sb.append(i);
            sb.append("\n CPUs: ");
            sb.append(Arrays.toString(this.mCpusByPolicy.get(i)));
            sb.append("\n freqs: ");
            sb.append(Arrays.toString(this.mFreqsByPolicy.get(i)));
            sb.append(ShaderAssembler.NEWLINE);
        }
        return sb.toString();
    }
}
