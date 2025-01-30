package com.android.server.job.restrictions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import com.android.server.job.JobSchedulerService;
import com.android.server.job.controllers.JobStatus;

/* loaded from: classes2.dex */
public class ThermalStatusRestriction extends JobRestriction {
    public boolean mForceRestricted;
    public boolean mIncrease;
    public BroadcastReceiver mSIOPTracker;
    public volatile int mThermalStatus;

    public ThermalStatusRestriction(JobSchedulerService jobSchedulerService) {
        super(jobSchedulerService, 4, 12, 5);
        this.mThermalStatus = 0;
        this.mSIOPTracker = new BroadcastReceiver() { // from class: com.android.server.job.restrictions.ThermalStatusRestriction.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                boolean booleanExtra = intent.getBooleanExtra("job_restriction", false);
                if (ThermalStatusRestriction.this.mForceRestricted != booleanExtra) {
                    ThermalStatusRestriction.this.mForceRestricted = booleanExtra;
                    Slog.i("ThermalStatusRestriction", "Receieved: forceRestrict = " + booleanExtra);
                    ThermalStatusRestriction thermalStatusRestriction = ThermalStatusRestriction.this;
                    thermalStatusRestriction.mService.onRestrictionStateChanged(thermalStatusRestriction, thermalStatusRestriction.mForceRestricted || ThermalStatusRestriction.this.mIncrease);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.intent.action.SIOP_LEVEL_CHANGED");
        this.mService.getContext().registerReceiver(this.mSIOPTracker, intentFilter);
    }

    @Override // com.android.server.job.restrictions.JobRestriction
    public void onSystemServicesReady() {
        ((PowerManager) this.mService.getTestableContext().getSystemService(PowerManager.class)).addThermalStatusListener(new PowerManager.OnThermalStatusChangedListener() { // from class: com.android.server.job.restrictions.ThermalStatusRestriction.1
            @Override // android.os.PowerManager.OnThermalStatusChangedListener
            public void onThermalStatusChanged(int i) {
                boolean z = (i >= 1 && i <= 3) || (ThermalStatusRestriction.this.mThermalStatus >= 1 && i < 1) || (ThermalStatusRestriction.this.mThermalStatus < 3 && i > 3);
                ThermalStatusRestriction thermalStatusRestriction = ThermalStatusRestriction.this;
                thermalStatusRestriction.mIncrease = thermalStatusRestriction.mThermalStatus < i;
                ThermalStatusRestriction.this.mThermalStatus = i;
                if (z) {
                    Slog.i("ThermalStatusRestriction", "ThermalStatus changed to " + ThermalStatusRestriction.this.mThermalStatus);
                    ThermalStatusRestriction thermalStatusRestriction2 = ThermalStatusRestriction.this;
                    thermalStatusRestriction2.mService.onRestrictionStateChanged(thermalStatusRestriction2, thermalStatusRestriction2.mIncrease);
                }
            }
        });
    }

    @Override // com.android.server.job.restrictions.JobRestriction
    public boolean isJobRestricted(JobStatus jobStatus) {
        if (jobStatus.overrideState == 2 && "android".equals(jobStatus.getSourcePackageName()) && (jobStatus.getJobId() == 800 || jobStatus.getJobId() == 801)) {
            return false;
        }
        if (this.mThermalStatus >= 3 || this.mForceRestricted) {
            return true;
        }
        int effectivePriority = jobStatus.getEffectivePriority();
        if (this.mThermalStatus >= 2) {
            if (jobStatus.shouldTreatAsUserInitiatedJob()) {
                return false;
            }
            if (jobStatus.shouldTreatAsExpeditedJob()) {
                return jobStatus.getNumPreviousAttempts() > 0 || (this.mService.isCurrentlyRunningLocked(jobStatus) && this.mService.isJobInOvertimeLocked(jobStatus));
            }
            if (effectivePriority == 400) {
                return !this.mService.isCurrentlyRunningLocked(jobStatus) || this.mService.isJobInOvertimeLocked(jobStatus);
            }
            return true;
        }
        if (this.mThermalStatus < 1) {
            return false;
        }
        if (effectivePriority != 100) {
            if (effectivePriority != 200) {
                return false;
            }
            if (this.mService.isCurrentlyRunningLocked(jobStatus) && !this.mService.isJobInOvertimeLocked(jobStatus)) {
                return false;
            }
        }
        return true;
    }

    public int getThermalStatus() {
        return this.mThermalStatus;
    }

    @Override // com.android.server.job.restrictions.JobRestriction
    public void dumpConstants(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.print("Thermal status: ");
        indentingPrintWriter.println(this.mThermalStatus);
        indentingPrintWriter.print("  Force restrict: ");
        indentingPrintWriter.println(this.mForceRestricted);
    }
}
