package com.android.internal.os;

import android.util.SparseArray;

import libcore.util.EmptyArray;

import com.samsung.android.core.pm.runtimemanifest.RuntimeManifestUtils;

import java.util.Arrays;

public class CpuScalingPolicies {
    private final SparseArray<int[]> mCpusByPolicy;
    private final SparseArray<int[]> mFreqsByPolicy;
    private final int[] mPolicies;
    private final int mScalingStepCount;

    public CpuScalingPolicies(SparseArray<int[]> cpusByPolicy, SparseArray<int[]> freqsByPolicy) {
        this.mCpusByPolicy = cpusByPolicy;
        this.mFreqsByPolicy = freqsByPolicy;
        this.mPolicies = new int[cpusByPolicy.size()];
        for (int i = 0; i < this.mPolicies.length; i++) {
            this.mPolicies[i] = cpusByPolicy.keyAt(i);
        }
        Arrays.sort(this.mPolicies);
        int count = 0;
        for (int i2 = freqsByPolicy.size() - 1; i2 >= 0; i2--) {
            count += freqsByPolicy.valueAt(i2).length;
        }
        this.mScalingStepCount = count;
    }

    public int[] getPolicies() {
        return this.mPolicies;
    }

    public int[] getRelatedCpus(int policy) {
        return this.mCpusByPolicy.get(policy, EmptyArray.INT);
    }

    public int[] getFrequencies(int policy) {
        return this.mFreqsByPolicy.get(policy, EmptyArray.INT);
    }

    public int getScalingStepCount() {
        return this.mScalingStepCount;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int policy : this.mPolicies) {
            sb.append(RuntimeManifestUtils.TAG_POLICY)
                    .append(policy)
                    .append("\n CPUs: ")
                    .append(Arrays.toString(this.mCpusByPolicy.get(policy)))
                    .append("\n freqs: ")
                    .append(Arrays.toString(this.mFreqsByPolicy.get(policy)))
                    .append("\n");
        }
        return sb.toString();
    }
}
