package com.android.server.job.restrictions;

import android.os.PowerManager;
import android.util.IndentingPrintWriter;

import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.job.Flags;
import com.android.server.job.controllers.JobStatus;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ThermalStatusRestriction extends JobRestriction {
    public boolean mForceRestricted;
    public boolean mIncreased;
    public AnonymousClass2 mSIOPTracker;
    public volatile int mThermalStatus;

    @Override // com.android.server.job.restrictions.JobRestriction
    public final void dumpConstants(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.print("Thermal status: ");
        indentingPrintWriter.println(this.mThermalStatus);
    }

    public int getThermalStatus() {
        return this.mThermalStatus;
    }

    @Override // com.android.server.job.restrictions.JobRestriction
    public final boolean isJobRestricted(JobStatus jobStatus, int i) {
        Flags.thermalRestrictionsToFgsJobs();
        if (i >= 35) {
            return false;
        }
        if (jobStatus.overrideState == 2
                && "android".equals(jobStatus.sourcePackageName)
                && (jobStatus.job.getId() == 800 || jobStatus.job.getId() == 801)) {
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
                return jobStatus.getNumPreviousAttempts() > 0
                        || (this.mService.isCurrentlyRunningLocked(jobStatus)
                                && this.mService.isJobInOvertimeLocked(jobStatus));
            }
            Flags.thermalRestrictionsToFgsJobs();
            if (effectivePriority == 400) {
                return !this.mService.isCurrentlyRunningLocked(jobStatus)
                        || this.mService.isJobInOvertimeLocked(jobStatus);
            }
            return true;
        }
        if (this.mThermalStatus < 1) {
            return false;
        }
        Flags.thermalRestrictionsToFgsJobs();
        if (effectivePriority != 100) {
            if (effectivePriority != 200) {
                return false;
            }
            if (this.mService.isCurrentlyRunningLocked(jobStatus)
                    && !this.mService.isJobInOvertimeLocked(jobStatus)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.server.job.restrictions.JobRestriction
    public final void onSystemServicesReady() {
        ((PowerManager) this.mService.getContext().getSystemService(PowerManager.class))
                .addThermalStatusListener(
                        new PowerManager
                                .OnThermalStatusChangedListener() { // from class:
                                                                    // com.android.server.job.restrictions.ThermalStatusRestriction.1
                            @Override // android.os.PowerManager.OnThermalStatusChangedListener
                            public final void onThermalStatusChanged(int i) {
                                boolean z =
                                        (i >= 1 && i <= 3)
                                                || (ThermalStatusRestriction.this.mThermalStatus
                                                                >= 1
                                                        && i < 1)
                                                || (ThermalStatusRestriction.this.mThermalStatus < 3
                                                        && i > 3);
                                ThermalStatusRestriction thermalStatusRestriction =
                                        ThermalStatusRestriction.this;
                                thermalStatusRestriction.mIncreased =
                                        thermalStatusRestriction.mThermalStatus < i;
                                ThermalStatusRestriction.this.mThermalStatus = i;
                                if (z) {
                                    SystemServiceManager$$ExternalSyntheticOutline0.m(
                                            new StringBuilder("ThermalStatus changed to "),
                                            ThermalStatusRestriction.this.mThermalStatus,
                                            "ThermalStatusRestriction");
                                    ThermalStatusRestriction thermalStatusRestriction2 =
                                            ThermalStatusRestriction.this;
                                    thermalStatusRestriction2.mService.onRestrictionStateChanged(
                                            thermalStatusRestriction2,
                                            thermalStatusRestriction2.mIncreased);
                                }
                            }
                        });
    }
}
