package com.android.internal.os;

/* loaded from: classes5.dex */
public final class KernelAllocationStats {
    public static native ProcessDmabuf[] getDmabufAllocations();

    public static native ProcessGpuMem[] getGpuAllocations();

    private KernelAllocationStats() {
    }

    public static final class ProcessDmabuf {
        public final int oomScore;
        public final String processName;
        public final int retainedBuffersCount;
        public final int retainedSizeKb;
        public final int surfaceFlingerCount;
        public final int surfaceFlingerSizeKb;
        public final int uid;

        ProcessDmabuf(int i, String str, int i2, int i3, int i4, int i5, int i6) {
            this.uid = i;
            this.processName = str;
            this.oomScore = i2;
            this.retainedSizeKb = i3;
            this.retainedBuffersCount = i4;
            this.surfaceFlingerSizeKb = i5;
            this.surfaceFlingerCount = i6;
        }
    }

    public static final class ProcessGpuMem {
        public final int gpuMemoryKb;
        public final int pid;

        ProcessGpuMem(int i, int i2) {
            this.pid = i;
            this.gpuMemoryKb = i2;
        }
    }
}
