package com.samsung.android.knox.tima.attestation;

import android.os.RemoteException;
import android.util.Log;

public abstract class EnhancedAttestationPolicyCallback {
    private static final String TAG = "SEMEAPolicyCb";
    private EnhancedAttestationPolicyCallback acb = this;

    abstract void onAttestationFinished(EnhancedAttestationResult enhancedAttestationResult);

    EnhancedAttestationPolicyCallback() {}

    private class EaAttestationPolicyCallback extends IEnhancedAttestationPolicyCallback.Stub {
        private String mNonce;

        private EaAttestationPolicyCallback() {
            this.mNonce = "";
        }

        @Override // com.samsung.android.knox.tima.attestation.IEnhancedAttestationPolicyCallback
        public void onAttestationFinished(EnhancedAttestationResult result) throws RemoteException {
            Log.d(
                    EnhancedAttestationPolicyCallback.TAG,
                    "onAttestationFinished: " + this.mNonce.length());
            EnhancedAttestationPolicy.getInstance().removeFromTrackMap(this.mNonce);
            EnhancedAttestationPolicyCallback.this.acb.onAttestationFinished(result);
        }
    }

    IEnhancedAttestationPolicyCallback getEaAttestationCb(String nonce) {
        EaAttestationPolicyCallback eaAttestationCb = new EaAttestationPolicyCallback();
        eaAttestationCb.mNonce = nonce;
        return eaAttestationCb;
    }
}
