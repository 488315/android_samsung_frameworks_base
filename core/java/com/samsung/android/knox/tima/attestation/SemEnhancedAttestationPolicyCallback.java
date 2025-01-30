package com.samsung.android.knox.tima.attestation;

import android.util.Log;

/* loaded from: classes5.dex */
public abstract class SemEnhancedAttestationPolicyCallback {
  private static final String TAG = "SemEAPolicyCb";
  private SemEnhancedAttestationPolicyCallback mEACallback = this;
  private EnhancedAttestationPolicyCallback mCb =
      new EnhancedAttestationPolicyCallback() { // from class:
                                                // com.samsung.android.knox.tima.attestation.SemEnhancedAttestationPolicyCallback.1
        @Override // com.samsung.android.knox.tima.attestation.EnhancedAttestationPolicyCallback
        public void onAttestationFinished(EnhancedAttestationResult result) {
          Log.m94d(SemEnhancedAttestationPolicyCallback.TAG, "onAttestationFinished");
          if (result.getError() == 0) {
            SemEnhancedAttestationResult semResult = new SemEnhancedAttestationResult(result);
            SemEnhancedAttestationPolicyCallback.this.mEACallback.onSuccess(semResult);
          } else {
            SemEnhancedAttestationError semError = new SemEnhancedAttestationError(result);
            SemEnhancedAttestationPolicyCallback.this.mEACallback.onFailure(semError);
          }
        }
      };

  public abstract void onFailure(SemEnhancedAttestationError semEnhancedAttestationError);

  public abstract void onSuccess(SemEnhancedAttestationResult semEnhancedAttestationResult);

  EnhancedAttestationPolicyCallback getEnhancedAttestationPolicyCallback() {
    return this.mCb;
  }
}
