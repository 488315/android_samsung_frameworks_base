package com.android.internal.os;

/* loaded from: classes5.dex */
public final class KernelCpuBpfTracking {
    private static long[] sFreqs = null;
    private static int[] sFreqsClusters = null;
    private static boolean sTracking = false;

    private static native int[] getFreqsClustersInternal();

    static native long[] getFreqsInternal();

    public static native boolean isSupported();

    private static native boolean startTrackingInternal();

    private KernelCpuBpfTracking() {
    }

    public static boolean startTracking() {
        if (!sTracking) {
            sTracking = startTrackingInternal();
        }
        return sTracking;
    }

    public static long[] getFreqs() {
        if (sFreqs == null) {
            long[] freqsInternal = getFreqsInternal();
            if (freqsInternal == null) {
                return new long[0];
            }
            sFreqs = freqsInternal;
        }
        return sFreqs;
    }

    public static int[] getFreqsClusters() {
        if (sFreqsClusters == null) {
            int[] freqsClustersInternal = getFreqsClustersInternal();
            if (freqsClustersInternal == null) {
                return new int[0];
            }
            sFreqsClusters = freqsClustersInternal;
        }
        return sFreqsClusters;
    }

    public static int getClusters() {
        int[] freqsClusters = getFreqsClusters();
        if (freqsClusters.length > 0) {
            return freqsClusters[freqsClusters.length - 1] + 1;
        }
        return 0;
    }
}
