package com.android.server.pm;

import android.util.SparseBooleanArray;

/* loaded from: classes3.dex */
public class PackageVerificationState {
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

  public VerifyingSession getVerifyingSession() {
    return this.mVerifyingSession;
  }

  public void addRequiredVerifierUid(int i) {
    this.mRequiredVerifierUids.put(i, true);
    this.mUnrespondedRequiredVerifierUids.put(i, true);
  }

  public boolean checkRequiredVerifierUid(int i) {
    return this.mRequiredVerifierUids.get(i, false);
  }

  public void addSufficientVerifier(int i) {
    this.mSufficientVerifierUids.put(i, true);
  }

  public boolean checkSufficientVerifierUid(int i) {
    return this.mSufficientVerifierUids.get(i, false);
  }

  public void setVerifierResponseOnTimeout(int i, int i2) {
    if (checkRequiredVerifierUid(i)) {
      this.mSufficientVerifierUids.clear();
      if (this.mUnrespondedRequiredVerifierUids.get(i, false)) {
        setVerifierResponse(i, i2);
      }
    }
  }

  public void setVerifierResponse(int i, int i2) {
    if (this.mRequiredVerifierUids.get(i)) {
      if (i2 != 1) {
        if (i2 == 2) {
          this.mSufficientVerifierUids.clear();
        } else {
          this.mRequiredVerificationPassed = false;
          this.mUnrespondedRequiredVerifierUids.clear();
          this.mSufficientVerifierUids.clear();
          this.mExtendedTimeoutUids.clear();
        }
      }
      this.mExtendedTimeoutUids.delete(i);
      this.mUnrespondedRequiredVerifierUids.delete(i);
      if (this.mUnrespondedRequiredVerifierUids.size() == 0) {
        this.mRequiredVerificationComplete = true;
        return;
      }
      return;
    }
    if (this.mSufficientVerifierUids.get(i)) {
      if (i2 == 1) {
        this.mSufficientVerificationPassed = true;
        this.mSufficientVerificationComplete = true;
      }
      this.mSufficientVerifierUids.delete(i);
      if (this.mSufficientVerifierUids.size() == 0) {
        this.mSufficientVerificationComplete = true;
      }
    }
  }

  public void passRequiredVerification() {
    if (this.mUnrespondedRequiredVerifierUids.size() > 0) {
      throw new RuntimeException("Required verifiers still present.");
    }
    this.mRequiredVerificationPassed = true;
    this.mRequiredVerificationComplete = true;
  }

  public boolean isVerificationComplete() {
    if (!this.mRequiredVerificationComplete) {
      return false;
    }
    if (this.mSufficientVerifierUids.size() == 0) {
      return true;
    }
    return this.mSufficientVerificationComplete;
  }

  public boolean isInstallAllowed() {
    if (!this.mRequiredVerificationComplete || !this.mRequiredVerificationPassed) {
      return false;
    }
    if (this.mSufficientVerificationComplete) {
      return this.mSufficientVerificationPassed;
    }
    return true;
  }

  public boolean extendTimeout(int i) {
    if (!checkRequiredVerifierUid(i) || timeoutExtended(i)) {
      return false;
    }
    this.mExtendedTimeoutUids.append(i, true);
    return true;
  }

  public boolean timeoutExtended(int i) {
    return this.mExtendedTimeoutUids.get(i, false);
  }

  public void setIntegrityVerificationResult(int i) {
    this.mIntegrityVerificationComplete = true;
  }

  public boolean isIntegrityVerificationComplete() {
    return this.mIntegrityVerificationComplete;
  }

  public boolean areAllVerificationsComplete() {
    return this.mIntegrityVerificationComplete && isVerificationComplete();
  }
}
