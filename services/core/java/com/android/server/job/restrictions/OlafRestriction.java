package com.android.server.job.restrictions;

import android.os.UserHandle;
import android.util.IndentingPrintWriter;
import com.android.server.job.controllers.JobStatus;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class OlafRestriction extends JobRestriction {
    public boolean mForceDisabled;
    public boolean mForceRestricted;
    public boolean mOlafOn;
    public AnonymousClass1 mOlafTracker;
    public int mOlafUid;

    @Override // com.android.server.job.restrictions.JobRestriction
    public final void dumpConstants(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.print("Olaf status: ");
        indentingPrintWriter.print(this.mOlafOn);
        if (this.mOlafOn) {
            indentingPrintWriter.print("(" + this.mOlafUid + ")");
        }
        if (this.mForceRestricted) {
            indentingPrintWriter.print(" | F");
        }
        if (this.mForceDisabled) {
            indentingPrintWriter.print(" | D");
        }
        indentingPrintWriter.println("");
    }

    public boolean getOlafStatus() {
        return this.mOlafOn;
    }

    @Override // com.android.server.job.restrictions.JobRestriction
    public final boolean isJobRestricted(JobStatus jobStatus, int i) {
        if (this.mForceDisabled) {
            return false;
        }
        int i2 = jobStatus.overrideState;
        int i3 = jobStatus.sourceUid;
        if (i2 == 2 && ("android".equals(jobStatus.sourcePackageName) || UserHandle.isCore(i3))) {
            return false;
        }
        if (this.mForceRestricted) {
            return true;
        }
        if (!this.mOlafOn || this.mOlafUid == i3 || jobStatus.shouldTreatAsUserInitiatedJob()) {
            return false;
        }
        if (jobStatus.getEffectivePriority() >= 400 || jobStatus.shouldTreatAsExpeditedJob()) {
            return !this.mService.isCurrentlyRunningLocked(jobStatus);
        }
        return true;
    }
}
