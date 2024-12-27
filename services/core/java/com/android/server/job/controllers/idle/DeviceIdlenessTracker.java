package com.android.server.job.controllers.idle;

import android.R;
import android.app.AlarmManager;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.DeviceConfig;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.server.AppSchedulingModuleThread;
import com.android.server.VcnManagementService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.job.JobSchedulerService;
import com.android.server.job.JobSchedulerService$Constants$$ExternalSyntheticOutline0;
import com.android.server.job.controllers.IdleController;
import java.io.PrintWriter;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DeviceIdlenessTracker extends BroadcastReceiver implements IdlenessTracker {
    public static final boolean DEBUG;
    static final String KEY_INACTIVITY_IDLE_THRESHOLD_MS = "ic_dit_inactivity_idle_threshold_ms";
    static final String KEY_INACTIVITY_STABLE_POWER_IDLE_THRESHOLD_MS = "ic_dit_inactivity_idle_stable_power_threshold_ms";
    public AlarmManager mAlarm;
    public boolean mDockIdle;
    public boolean mIdle;
    public IdleController mIdleListener;
    public long mIdleWindowSlop;
    public long mInactivityIdleThreshold;
    public long mInactivityStablePowerIdleThreshold;
    public boolean mIsStablePower;
    public PowerManager mPowerManager;
    public boolean mProjectionActive;
    public long mIdlenessCheckScheduledElapsed = -1;
    public long mIdleStartElapsed = Long.MAX_VALUE;
    public final DeviceIdlenessTracker$$ExternalSyntheticLambda0 mOnProjectionStateChangedListener = new UiModeManager.OnProjectionStateChangedListener() { // from class: com.android.server.job.controllers.idle.DeviceIdlenessTracker$$ExternalSyntheticLambda0
        public final void onProjectionStateChanged(int i, Set set) {
            DeviceIdlenessTracker deviceIdlenessTracker = DeviceIdlenessTracker.this;
            deviceIdlenessTracker.getClass();
            boolean z = i != 0;
            if (deviceIdlenessTracker.mProjectionActive == z) {
                return;
            }
            if (DeviceIdlenessTracker.DEBUG) {
                Slog.v("JobScheduler.DeviceIdlenessTracker", "Projection state changed: " + z);
            }
            deviceIdlenessTracker.mProjectionActive = z;
            if (z) {
                deviceIdlenessTracker.exitIdle();
            } else {
                deviceIdlenessTracker.maybeScheduleIdlenessCheck("Projection ended");
            }
        }
    };
    public final DeviceIdlenessTracker$$ExternalSyntheticLambda1 mIdleAlarmListener = new AlarmManager.OnAlarmListener() { // from class: com.android.server.job.controllers.idle.DeviceIdlenessTracker$$ExternalSyntheticLambda1
        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            DeviceIdlenessTracker.this.handleIdleTrigger();
        }
    };
    public boolean mScreenOn = true;

    static {
        DEBUG = JobSchedulerService.DEBUG || Log.isLoggable("JobScheduler.DeviceIdlenessTracker", 3);
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public final void dump(ProtoOutputStream protoOutputStream) {
        long start = protoOutputStream.start(1146756268035L);
        long start2 = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1133871366145L, this.mIdle);
        protoOutputStream.write(1133871366146L, this.mScreenOn);
        protoOutputStream.write(1133871366147L, this.mDockIdle);
        protoOutputStream.write(1133871366149L, this.mProjectionActive);
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public final void dump(PrintWriter printWriter) {
        printWriter.print("  mIdle: ");
        printWriter.println(this.mIdle);
        printWriter.print("  mScreenOn: ");
        printWriter.println(this.mScreenOn);
        printWriter.print("  mIsStablePower: ");
        printWriter.println(this.mIsStablePower);
        printWriter.print("  mDockIdle: ");
        printWriter.println(this.mDockIdle);
        printWriter.print("  mProjectionActive: ");
        printWriter.println(this.mProjectionActive);
        printWriter.print("  mIdlenessCheckScheduledElapsed: ");
        printWriter.println(this.mIdlenessCheckScheduledElapsed);
        printWriter.print("  mIdleStartElapsed: ");
        printWriter.println(this.mIdleStartElapsed);
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public final void dumpConstants(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("DeviceIdlenessTracker:");
        indentingPrintWriter.increaseIndent();
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.mInactivityIdleThreshold, indentingPrintWriter, KEY_INACTIVITY_IDLE_THRESHOLD_MS);
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.mInactivityStablePowerIdleThreshold, indentingPrintWriter, KEY_INACTIVITY_STABLE_POWER_IDLE_THRESHOLD_MS);
        indentingPrintWriter.print("ic_dit_idle_window_slop_ms", Long.valueOf(this.mIdleWindowSlop)).println();
        indentingPrintWriter.decreaseIndent();
    }

    public final void exitIdle() {
        this.mAlarm.cancel(this.mIdleAlarmListener);
        this.mIdlenessCheckScheduledElapsed = -1L;
        this.mIdleStartElapsed = Long.MAX_VALUE;
        if (this.mIdle) {
            this.mIdle = false;
            this.mIdleListener.reportNewIdleState(false);
        }
    }

    public final void handleIdleTrigger() {
        if (!this.mIdle && ((!this.mScreenOn || this.mDockIdle) && !this.mProjectionActive)) {
            if (DEBUG) {
                StringBuilder sb = new StringBuilder("Idle trigger fired @ ");
                JobSchedulerService.sElapsedRealtimeClock.getClass();
                sb.append(SystemClock.elapsedRealtime());
                Slog.v("JobScheduler.DeviceIdlenessTracker", sb.toString());
            }
            this.mIdle = true;
            this.mIdleListener.reportNewIdleState(true);
            return;
        }
        if (DEBUG) {
            StringBuilder sb2 = new StringBuilder("TRIGGER_IDLE received but not changing state; idle=");
            sb2.append(this.mIdle);
            sb2.append(" screen=");
            sb2.append(this.mScreenOn);
            sb2.append(" projection=");
            ProxyManager$$ExternalSyntheticOutline0.m("JobScheduler.DeviceIdlenessTracker", sb2, this.mProjectionActive);
        }
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public final boolean isIdle() {
        return this.mIdle;
    }

    public final void maybeScheduleIdlenessCheck(String str) {
        if (this.mIdle) {
            if (DEBUG) {
                Slog.w("JobScheduler.DeviceIdlenessTracker", "Already idle. Redundant reason=".concat(str));
                return;
            }
            return;
        }
        if ((!this.mScreenOn || this.mDockIdle) && !this.mProjectionActive) {
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long j = this.mIsStablePower ? this.mInactivityStablePowerIdleThreshold : this.mInactivityIdleThreshold;
            long j2 = this.mIdlenessCheckScheduledElapsed;
            if (j2 < 0) {
                this.mIdlenessCheckScheduledElapsed = elapsedRealtime;
            } else if (j2 + j <= elapsedRealtime) {
                if (DEBUG) {
                    Slog.v("JobScheduler.DeviceIdlenessTracker", "Previous idle check @ " + this.mIdlenessCheckScheduledElapsed + " allows device to be idle now");
                }
                handleIdleTrigger();
                return;
            }
            long j3 = this.mIdlenessCheckScheduledElapsed + j;
            if (j3 == this.mIdleStartElapsed) {
                if (DEBUG) {
                    Slog.i("JobScheduler.DeviceIdlenessTracker", "No change to idle start time");
                    return;
                }
                return;
            }
            this.mIdleStartElapsed = j3;
            if (DEBUG) {
                Slog.v("JobScheduler.DeviceIdlenessTracker", "Scheduling idle : " + str + " now:" + elapsedRealtime + " checkElapsed=" + this.mIdlenessCheckScheduledElapsed + " when=" + this.mIdleStartElapsed);
            }
            this.mAlarm.setWindow(2, this.mIdleStartElapsed, this.mIdleWindowSlop, "JS idleness", AppSchedulingModuleThread.getExecutor(), this.mIdleAlarmListener);
        }
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public final void onBatteryStateChanged(boolean z, boolean z2) {
        boolean z3 = z && z2;
        if (this.mIsStablePower != z3) {
            this.mIsStablePower = z3;
            maybeScheduleIdlenessCheck("stable power changed");
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0092  */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onReceive(android.content.Context r7, android.content.Intent r8) {
        /*
            r6 = this;
            java.lang.String r7 = "android.intent.action.DOCK_IDLE"
            r0 = 0
            r1 = 1
            java.lang.String r8 = r8.getAction()
            boolean r2 = com.android.server.job.controllers.idle.DeviceIdlenessTracker.DEBUG
            java.lang.String r3 = "JobScheduler.DeviceIdlenessTracker"
            if (r2 == 0) goto L1f
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "Received action: "
            r4.<init>(r5)
            r4.append(r8)
            java.lang.String r4 = r4.toString()
            android.util.Slog.v(r3, r4)
        L1f:
            r8.getClass()
            r4 = -1
            int r5 = r8.hashCode()
            switch(r5) {
                case -2128145023: goto L6c;
                case -1454123155: goto L61;
                case -905264325: goto L58;
                case 244891622: goto L4d;
                case 257757490: goto L42;
                case 1456569541: goto L36;
                case 1689632941: goto L2b;
                default: goto L2a;
            }
        L2a:
            goto L76
        L2b:
            java.lang.String r5 = "android.intent.action.DOCK_ACTIVE"
            boolean r5 = r8.equals(r5)
            if (r5 != 0) goto L34
            goto L76
        L34:
            r4 = 6
            goto L76
        L36:
            java.lang.String r5 = "com.android.server.ACTION_TRIGGER_IDLE"
            boolean r5 = r8.equals(r5)
            if (r5 != 0) goto L40
            goto L76
        L40:
            r4 = 5
            goto L76
        L42:
            java.lang.String r5 = "android.intent.action.DREAMING_STOPPED"
            boolean r5 = r8.equals(r5)
            if (r5 != 0) goto L4b
            goto L76
        L4b:
            r4 = 4
            goto L76
        L4d:
            java.lang.String r5 = "android.intent.action.DREAMING_STARTED"
            boolean r5 = r8.equals(r5)
            if (r5 != 0) goto L56
            goto L76
        L56:
            r4 = 3
            goto L76
        L58:
            boolean r5 = r8.equals(r7)
            if (r5 != 0) goto L5f
            goto L76
        L5f:
            r4 = 2
            goto L76
        L61:
            java.lang.String r5 = "android.intent.action.SCREEN_ON"
            boolean r5 = r8.equals(r5)
            if (r5 != 0) goto L6a
            goto L76
        L6a:
            r4 = r1
            goto L76
        L6c:
            java.lang.String r5 = "android.intent.action.SCREEN_OFF"
            boolean r5 = r8.equals(r5)
            if (r5 != 0) goto L75
            goto L76
        L75:
            r4 = r0
        L76:
            switch(r4) {
                case 0: goto L9c;
                case 1: goto L8c;
                case 2: goto L9c;
                case 3: goto L9c;
                case 4: goto L83;
                case 5: goto L7f;
                case 6: goto L7a;
                default: goto L79;
            }
        L79:
            goto Lb1
        L7a:
            boolean r7 = r6.mScreenOn
            if (r7 != 0) goto L83
            return
        L7f:
            r6.handleIdleTrigger()
            goto Lb1
        L83:
            android.os.PowerManager r7 = r6.mPowerManager
            boolean r7 = r7.isInteractive()
            if (r7 != 0) goto L8c
            return
        L8c:
            r6.mScreenOn = r1
            r6.mDockIdle = r0
            if (r2 == 0) goto L98
            java.lang.String r7 = "exiting idle"
            android.util.Slog.v(r3, r7)
        L98:
            r6.exitIdle()
            goto Lb1
        L9c:
            boolean r7 = r8.equals(r7)
            if (r7 == 0) goto Laa
            boolean r7 = r6.mScreenOn
            if (r7 != 0) goto La7
            return
        La7:
            r6.mDockIdle = r1
            goto Lae
        Laa:
            r6.mScreenOn = r0
            r6.mDockIdle = r0
        Lae:
            r6.maybeScheduleIdlenessCheck(r8)
        Lb1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.job.controllers.idle.DeviceIdlenessTracker.onReceive(android.content.Context, android.content.Intent):void");
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public final void processConstant(DeviceConfig.Properties properties, String str) {
        switch (str) {
            case "ic_dit_idle_window_slop_ms":
                this.mIdleWindowSlop = Math.max(60000L, Math.min(900000L, properties.getLong(str, this.mIdleWindowSlop)));
                break;
            case "ic_dit_inactivity_idle_threshold_ms":
                this.mInactivityIdleThreshold = Math.max(60000L, Math.min(BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS, properties.getLong(str, this.mInactivityIdleThreshold)));
                break;
            case "ic_dit_inactivity_idle_stable_power_threshold_ms":
                this.mInactivityStablePowerIdleThreshold = Math.max(60000L, Math.min(BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS, properties.getLong(str, this.mInactivityStablePowerIdleThreshold)));
                break;
        }
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public final void startTracking(Context context, JobSchedulerService jobSchedulerService, IdleController idleController) {
        this.mIdleListener = idleController;
        this.mInactivityIdleThreshold = context.getResources().getInteger(R.integer.config_mediaOutputSwitchDialogVersion);
        this.mInactivityStablePowerIdleThreshold = context.getResources().getInteger(R.integer.config_mediaRouter_builtInSpeakerSuitability);
        this.mIdleWindowSlop = context.getResources().getInteger(R.integer.config_mdc_initial_max_retry);
        this.mAlarm = (AlarmManager) context.getSystemService("alarm");
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        IntentFilter m = VcnManagementService$$ExternalSyntheticOutline0.m("android.intent.action.SCREEN_ON", "android.intent.action.SCREEN_OFF", "android.intent.action.DREAMING_STARTED", "android.intent.action.DREAMING_STOPPED", "com.android.server.ACTION_TRIGGER_IDLE");
        m.addAction("android.intent.action.DOCK_IDLE");
        m.addAction("android.intent.action.DOCK_ACTIVE");
        context.registerReceiver(this, m, null, AppSchedulingModuleThread.getHandler());
        ((UiModeManager) context.getSystemService(UiModeManager.class)).addOnProjectionStateChangedListener(-1, AppSchedulingModuleThread.getExecutor(), this.mOnProjectionStateChangedListener);
        this.mIsStablePower = jobSchedulerService.isBatteryCharging() && jobSchedulerService.isBatteryNotLow();
    }
}
