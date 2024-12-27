package com.android.server.pm;

import android.util.SparseBooleanArray;

public final class PackageVerificationState {
    public boolean mIntegrityVerificationComplete;
    public boolean mSufficientVerificationComplete;
    public boolean mSufficientVerificationPassed;
    public final VerifyingSession mVerifyingSession;
    public final SparseBooleanArray mSufficientVerifierUids = new SparseBooleanArray();
    public final SparseBooleanArray mRequiredVerifierUids = new SparseBooleanArray();
    public final SparseBooleanArray mUnrespondedRequiredVerifierUids = new SparseBooleanArray();
    public final SparseBooleanArray mExtendedTimeoutUids = new SparseBooleanArray();
    public boolean mRequiredVerificationComplete = false;
    public boolean mRequiredVerificationPassed = true;

    public PackageVerificationState(VerifyingSession verifyingSession) {
        this.mVerifyingSession = verifyingSession;
    }

    public final boolean areAllVerificationsComplete() {
        return this.mIntegrityVerificationComplete && isVerificationComplete();
    }

    public final boolean isVerificationComplete() {
        if (!this.mRequiredVerificationComplete) {
            return false;
        }
        if (this.mSufficientVerifierUids.size() == 0) {
            return true;
        }
        return this.mSufficientVerificationComplete;
    }

    public final void setVerifierResponse(int i, int i2) {
        if (!this.mRequiredVerifierUids.get(i)) {
            if (this.mSufficientVerifierUids.get(i)) {
                if (i2 == 1) {
                    this.mSufficientVerificationPassed = true;
                    this.mSufficientVerificationComplete = true;
                }
                this.mSufficientVerifierUids.delete(i);
                if (this.mSufficientVerifierUids.size() == 0) {
                    this.mSufficientVerificationComplete = true;
                    return;
                }
                return;
            }
            return;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                this.mRequiredVerificationPassed = false;
                this.mUnrespondedRequiredVerifierUids.clear();
                this.mSufficientVerifierUids.clear();
                this.mExtendedTimeoutUids.clear();
            } else {
                this.mSufficientVerifierUids.clear();
            }
        }
        this.mExtendedTimeoutUids.delete(i);
        this.mUnrespondedRequiredVerifierUids.delete(i);
        if (this.mUnrespondedRequiredVerifierUids.size() == 0) {
            this.mRequiredVerificationComplete = true;
        }
    }
}
