package com.android.server.job.controllers.idle;

import android.content.Context;
import android.provider.DeviceConfig;
import android.util.IndentingPrintWriter;
import android.util.proto.ProtoOutputStream;

import com.android.server.job.JobSchedulerService;
import com.android.server.job.controllers.IdleController;

import java.io.PrintWriter;

public interface IdlenessTracker {
    void dump(ProtoOutputStream protoOutputStream);

    void dump(PrintWriter printWriter);

    void dumpConstants(IndentingPrintWriter indentingPrintWriter);

    boolean isIdle();

    void onBatteryStateChanged(boolean z, boolean z2);

    void processConstant(DeviceConfig.Properties properties, String str);

    void startTracking(
            Context context,
            JobSchedulerService jobSchedulerService,
            IdleController idleController);
}
