package com.android.server;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Binder;
import android.os.StatFs;
import android.util.proto.ProtoOutputStream;

import com.android.server.storage.DiskStatsLoggingService;

import java.io.File;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public final class DiskStatsService extends Binder {
    public final Context mContext;

    public DiskStatsService(Context context) {
        this.mContext = context;
        ComponentName componentName = DiskStatsLoggingService.sDiskStatsLoggingService;
        ((JobScheduler) context.getSystemService("jobscheduler"))
                .schedule(
                        new JobInfo.Builder(
                                        1145656139,
                                        DiskStatsLoggingService.sDiskStatsLoggingService)
                                .setRequiresDeviceIdle(true)
                                .setRequiresCharging(true)
                                .setPeriodic(TimeUnit.DAYS.toMillis(1L))
                                .build());
    }

    public static void reportFreeSpace(
            File file,
            String str,
            PrintWriter printWriter,
            ProtoOutputStream protoOutputStream,
            int i) {
        try {
            StatFs statFs = new StatFs(file.getPath());
            long blockSize = statFs.getBlockSize();
            long availableBlocks = statFs.getAvailableBlocks();
            long blockCount = statFs.getBlockCount();
            if (blockSize <= 0 || blockCount <= 0) {
                throw new IllegalArgumentException(
                        "Invalid stat: bsize="
                                + blockSize
                                + " avail="
                                + availableBlocks
                                + " total="
                                + blockCount);
            }
            if (protoOutputStream != null) {
                long start = protoOutputStream.start(2246267895812L);
                protoOutputStream.write(1159641169921L, i);
                protoOutputStream.write(1112396529666L, (availableBlocks * blockSize) / 1024);
                protoOutputStream.write(1112396529667L, (blockCount * blockSize) / 1024);
                protoOutputStream.end(start);
                return;
            }
            printWriter.print(str);
            printWriter.print("-Free: ");
            printWriter.print((availableBlocks * blockSize) / 1024);
            printWriter.print("K / ");
            printWriter.print((blockSize * blockCount) / 1024);
            printWriter.print("K total = ");
            printWriter.print((availableBlocks * 100) / blockCount);
            printWriter.println("% free");
        } catch (IllegalArgumentException e) {
            if (protoOutputStream != null) {
                return;
            }
            printWriter.print(str);
            printWriter.print("-Error: ");
            printWriter.println(e.toString());
        }
    }

    @Override // android.os.Binder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dump(
            java.io.FileDescriptor r25, java.io.PrintWriter r26, java.lang.String[] r27) {
        /*
            Method dump skipped, instructions count: 933
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.DiskStatsService.dump(java.io.FileDescriptor,"
                    + " java.io.PrintWriter, java.lang.String[]):void");
    }
}
