package com.android.server.job.controllers.idle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.DeviceConfig;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;

import com.android.server.AppSchedulingModuleThread;
import com.android.server.VcnManagementService$$ExternalSyntheticOutline0;
import com.android.server.job.JobSchedulerService;
import com.android.server.job.controllers.IdleController;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CarIdlenessTracker extends BroadcastReceiver implements IdlenessTracker {
    public static final boolean DEBUG;
    public boolean mForced;
    public boolean mGarageModeOn;
    public boolean mIdle;
    public IdleController mIdleListener;
    public boolean mScreenOn;

    static {
        DEBUG = JobSchedulerService.DEBUG || Log.isLoggable("JobScheduler.CarIdlenessTracker", 3);
    }

    public static void logIfDebug(String str) {
        if (DEBUG) {
            Slog.v("JobScheduler.CarIdlenessTracker", str);
        }
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public final void dump(ProtoOutputStream protoOutputStream) {
        long start = protoOutputStream.start(1146756268035L);
        long start2 = protoOutputStream.start(1146756268034L);
        protoOutputStream.write(1133871366145L, this.mIdle);
        protoOutputStream.write(1133871366146L, this.mGarageModeOn);
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public final void dump(PrintWriter printWriter) {
        printWriter.print("  mIdle: ");
        printWriter.println(this.mIdle);
        printWriter.print("  mGarageModeOn: ");
        printWriter.println(this.mGarageModeOn);
        printWriter.print("  mForced: ");
        printWriter.println(this.mForced);
        printWriter.print("  mScreenOn: ");
        printWriter.println(this.mScreenOn);
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public final void dumpConstants(IndentingPrintWriter indentingPrintWriter) {}

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public final boolean isIdle() {
        return this.mIdle;
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public final void onBatteryStateChanged(boolean z, boolean z2) {}

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        logIfDebug("Received action: " + action);
        if (action.equals("com.android.server.jobscheduler.FORCE_IDLE")) {
            logIfDebug("Forcing idle...");
            this.mForced = true;
            updateIdlenessState();
            return;
        }
        if (action.equals("com.android.server.jobscheduler.UNFORCE_IDLE")) {
            logIfDebug("Unforcing idle...");
            this.mForced = false;
            updateIdlenessState();
            return;
        }
        if (action.equals("android.intent.action.SCREEN_ON")) {
            logIfDebug("Screen is on...");
            this.mScreenOn = true;
            if (this.mForced || this.mGarageModeOn) {
                logIfDebug("Screen is on, but device cannot exit idle");
                return;
            } else {
                if (!this.mIdle) {
                    logIfDebug("Device is already non-idle");
                    return;
                }
                logIfDebug("Device is exiting idle");
                this.mIdle = false;
                this.mIdleListener.reportNewIdleState(false);
                return;
            }
        }
        if (action.equals("android.intent.action.SCREEN_OFF")) {
            logIfDebug("Screen is off...");
            this.mScreenOn = false;
            return;
        }
        if (action.equals("com.android.server.jobscheduler.GARAGE_MODE_ON")) {
            logIfDebug("GarageMode is on...");
            this.mGarageModeOn = true;
            updateIdlenessState();
            return;
        }
        if (action.equals("com.android.server.jobscheduler.GARAGE_MODE_OFF")) {
            logIfDebug("GarageMode is off...");
            this.mGarageModeOn = false;
            updateIdlenessState();
            return;
        }
        if (action.equals("com.android.server.ACTION_TRIGGER_IDLE")) {
            if (this.mGarageModeOn) {
                logIfDebug(
                        "TRIGGER_IDLE received but not changing state; mIdle="
                                + this.mIdle
                                + " mGarageModeOn="
                                + this.mGarageModeOn);
                return;
            }
            logIfDebug("Idle trigger fired...");
            if (this.mIdle) {
                logIfDebug("Device is already idle");
                return;
            }
            if (!this.mScreenOn) {
                logIfDebug("Device is going idle");
                this.mIdle = true;
                this.mIdleListener.reportNewIdleState(true);
            } else {
                logIfDebug(
                        "TRIGGER_IDLE received but not changing state: mIdle = "
                                + this.mIdle
                                + ", mScreenOn = "
                                + this.mScreenOn);
            }
        }
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public final void processConstant(DeviceConfig.Properties properties, String str) {}

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public final void startTracking(
            Context context,
            JobSchedulerService jobSchedulerService,
            IdleController idleController) {
        this.mIdleListener = idleController;
        IntentFilter m =
                VcnManagementService$$ExternalSyntheticOutline0.m(
                        "android.intent.action.SCREEN_ON",
                        "android.intent.action.SCREEN_OFF",
                        "com.android.server.jobscheduler.GARAGE_MODE_ON",
                        "com.android.server.jobscheduler.GARAGE_MODE_OFF",
                        "com.android.server.jobscheduler.FORCE_IDLE");
        m.addAction("com.android.server.jobscheduler.UNFORCE_IDLE");
        m.addAction("com.android.server.ACTION_TRIGGER_IDLE");
        context.registerReceiver(this, m, null, AppSchedulingModuleThread.getHandler());
    }

    public final void updateIdlenessState() {
        boolean z = this.mForced || this.mGarageModeOn;
        if (this.mIdle == z) {
            logIfDebug("Device idleness is the same. Current idle=" + z);
        } else {
            logIfDebug("Device idleness changed. New idle=" + z);
            this.mIdle = z;
            this.mIdleListener.reportNewIdleState(z);
        }
    }
}
