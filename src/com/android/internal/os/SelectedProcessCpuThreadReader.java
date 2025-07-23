package com.android.internal.os;

import android.os.Process;
import com.android.internal.os.KernelSingleProcessCpuThreadReader;

/* loaded from: classes5.dex */
public final class SelectedProcessCpuThreadReader {
    private final String[] mCmdline;
    private KernelSingleProcessCpuThreadReader mKernelCpuThreadReader;
    private int mPid;

    public SelectedProcessCpuThreadReader(String str) {
        this.mCmdline = new String[]{str};
    }

    public KernelSingleProcessCpuThreadReader.ProcessCpuUsage readAbsolute() {
        int[] pidsForCommands = Process.getPidsForCommands(this.mCmdline);
        if (pidsForCommands != null && pidsForCommands.length == 1) {
            int i = pidsForCommands[0];
            if (this.mPid == i) {
                return this.mKernelCpuThreadReader.getProcessCpuUsage();
            }
            this.mPid = i;
            KernelSingleProcessCpuThreadReader create = KernelSingleProcessCpuThreadReader.create(i);
            this.mKernelCpuThreadReader = create;
            create.startTrackingThreadCpuTimes();
        }
        return null;
    }
}
