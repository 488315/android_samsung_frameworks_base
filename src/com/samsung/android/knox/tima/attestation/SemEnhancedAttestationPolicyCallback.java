package com.samsung.android.knox.tima.attestation;

import android.util.Log;

/* loaded from: classes6.dex */
public abstract class SemEnhancedAttestationPolicyCallback {
    private static final String TAG = "SemEAPolicyCb";
    private SemEnhancedAttestationPolicyCallback mEACallback = this;
    private EnhancedAttestationPolicyCallback mCb = new EnhancedAttestationPolicyCallback() { // from class: com.samsung.android.knox.tima.attestation.SemEnhancedAttestationPolicyCallback.1
        @Override // com.samsung.android.knox.tima.attestation.EnhancedAttestationPolicyCallback
        public void onAttestationFinished(EnhancedAttestationResult enhancedAttestationResult) {
            Log.d(SemEnhancedAttestationPolicyCallback.TAG, "onAttestationFinished");
            if (enhancedAttestationResult.getError() == 0) {
                SemEnhancedAttestationPolicyCallback.this.mEACallback.onSuccess(new SemEnhancedAttestationResult(enhancedAttestationResult));
            } else {
                SemEnhancedAttestationPolicyCallback.this.mEACallback.onFailure(new SemEnhancedAttestationError(enhancedAttestationResult));
            }
        }
    };

    public abstract void onFailure(SemEnhancedAttestationError semEnhancedAttestationError);

    public abstract void onSuccess(SemEnhancedAttestationResult semEnhancedAttestationResult);

    EnhancedAttestationPolicyCallback getEnhancedAttestationPolicyCallback() {
        return this.mCb;
    }
}
