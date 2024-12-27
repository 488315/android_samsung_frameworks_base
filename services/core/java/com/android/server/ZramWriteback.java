package com.android.server;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.FileUtils;
import android.os.SystemProperties;
import android.util.Slog;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public final class ZramWriteback extends JobService {
    public static final ComponentName sZramWriteback =
            new ComponentName("android", ZramWriteback.class.getName());

    public static int getWrittenPageCount() {
        String format = String.format("/sys/block/zram%d/bd_stat", 0);
        try {
            return Integer.parseInt(
                    FileUtils.readTextFile(new File(format), 128, "").trim().split("\\s+")[2], 10);
        } catch (IOException unused) {
            Slog.e("ZramWriteback", "Failed to read writeback stats from ".concat(format));
            return -1;
        }
    }

    public static void markPagesAsIdle() {
        String format = String.format("/sys/block/zram%d/idle", 0);
        try {
            FileUtils.stringToFile(new File(format), "all");
        } catch (IOException unused) {
            Slog.e("ZramWriteback", "Failed to write to ".concat(format));
        }
    }

    public static void scheduleZramWriteback(Context context) {
        int i = SystemProperties.getInt("ro.zram.mark_idle_delay_mins", 20);
        int i2 = SystemProperties.getInt("ro.zram.first_wb_delay_mins", 180);
        boolean z = SystemProperties.getBoolean("zram.force_writeback", false);
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        ComponentName componentName = sZramWriteback;
        JobInfo.Builder builder = new JobInfo.Builder(811, componentName);
        TimeUnit timeUnit = TimeUnit.MINUTES;
        jobScheduler.schedule(builder.setMinimumLatency(timeUnit.toMillis(i)).build());
        jobScheduler.schedule(
                new JobInfo.Builder(812, componentName)
                        .setMinimumLatency(timeUnit.toMillis(i2))
                        .setRequiresDeviceIdle(!z)
                        .build());
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(final JobParameters jobParameters) {
        try {
        } catch (IOException unused) {
            Slog.w("ZramWriteback", "Writeback is not enabled on zram");
        }
        if ("none"
                .equals(
                        FileUtils.readTextFile(
                                        new File(String.format("/sys/block/zram%d/backing_dev", 0)),
                                        128,
                                        "")
                                .trim())) {
            Slog.w("ZramWriteback", "Writeback device is not set");
            jobFinished(jobParameters, false);
            return false;
        }
        if (jobParameters.getJobId() != 811) {
            new Thread() { // from class: com.android.server.ZramWriteback.1
                {
                    super("ZramWriteback_WritebackIdlePages");
                }

                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    ZramWriteback zramWriteback = ZramWriteback.this;
                    ComponentName componentName = ZramWriteback.sZramWriteback;
                    zramWriteback.getClass();
                    int writtenPageCount = ZramWriteback.getWrittenPageCount();
                    String format = String.format("/sys/block/zram%d/writeback", 0);
                    try {
                        FileUtils.stringToFile(new File(format), "idle");
                    } catch (IOException unused2) {
                        Slog.e("ZramWriteback", "Failed to write to ".concat(format));
                    }
                    ZramWriteback.markPagesAsIdle();
                    if (writtenPageCount != -1) {
                        Slog.i(
                                "ZramWriteback",
                                "Total pages written to disk is "
                                        + (ZramWriteback.getWrittenPageCount() - writtenPageCount));
                    }
                    ((JobScheduler) ZramWriteback.this.getSystemService("jobscheduler"))
                            .schedule(
                                    new JobInfo.Builder(812, ZramWriteback.sZramWriteback)
                                            .setMinimumLatency(
                                                    TimeUnit.HOURS.toMillis(
                                                            SystemProperties.getInt(
                                                                    "ro.zram.periodic_wb_delay_hours",
                                                                    24)))
                                            .setRequiresDeviceIdle(
                                                    !SystemProperties.getBoolean(
                                                            "zram.force_writeback", false))
                                            .build());
                    ZramWriteback.this.jobFinished(jobParameters, false);
                }
            }.start();
            return true;
        }
        markPagesAsIdle();
        jobFinished(jobParameters, false);
        return false;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
