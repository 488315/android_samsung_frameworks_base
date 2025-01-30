package com.android.server.am;

import android.content.Context;
import android.os.Debug;
import android.util.Slog;

/* loaded from: classes.dex */
public class AbnormalUsageService {
    public final ActivityManagerService mAm;

    public AbnormalUsageService(ActivityManagerService activityManagerService) {
        this.mAm = activityManagerService;
    }

    public void processAbnormalUsage(Context context, ProcessRecord processRecord, int i, int i2) {
        if (i == 1) {
            processRecord.setExcessiveResourceUsage(true);
            printProcessMemoryState(context, processRecord, i2);
        }
    }

    public final void printProcessMemoryState(Context context, ProcessRecord processRecord, int i) {
        String str = processRecord.info.packageName;
        Slog.i("AbnormalUsageService", "print memory info about excessive memory usage for " + str);
        int[] iArr = {i};
        Debug.MemoryInfo[] processMemoryInfo = this.mAm.getProcessMemoryInfo(iArr);
        StringBuilder sb = new StringBuilder();
        sb.append("[pid] : ");
        sb.append(iArr[0]);
        sb.append("[process name] : ");
        sb.append(str);
        Slog.i("AbnormalUsageService", sb.toString());
        for (Debug.MemoryInfo memoryInfo : processMemoryInfo) {
            Slog.i("AbnormalUsageService", "[MemoryInfo] TotalPss: " + memoryInfo.getTotalPss() + ", NativePss: " + memoryInfo.nativePss + ", DalvikPss: " + memoryInfo.dalvikPss);
        }
    }
}
