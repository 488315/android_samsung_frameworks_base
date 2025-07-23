package com.samsung.android.knox.integrity;

import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.knox.integrity.IEnhancedAttestationPolicyCallback;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class EnhancedAttestationPolicyCallback {
    public static final String TAG = "EAPolicyCb";
    public EnhancedAttestationPolicyCallback acb = this;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    class EaAttestationPolicyCallback extends IEnhancedAttestationPolicyCallback.Stub {
        public String mNonce;

        public /* synthetic */ EaAttestationPolicyCallback(EnhancedAttestationPolicyCallback enhancedAttestationPolicyCallback, int i) {
            this();
        }

        @Override // com.samsung.android.knox.integrity.IEnhancedAttestationPolicyCallback
        public void onAttestationFinished(EnhancedAttestationResult enhancedAttestationResult) throws RemoteException {
            Log.d(EnhancedAttestationPolicyCallback.TAG, "onAttestationFinished: " + this.mNonce.length());
            EnhancedAttestationPolicy.getInstance().removeFromTrackMap(this.mNonce);
            EnhancedAttestationPolicyCallback.this.acb.onAttestationFinished(enhancedAttestationResult);
        }

        private EaAttestationPolicyCallback() {
            this.mNonce = "";
        }
    }

    public final IEnhancedAttestationPolicyCallback getEaAttestationCb(String str) {
        EaAttestationPolicyCallback eaAttestationPolicyCallback = new EaAttestationPolicyCallback(this, 0);
        eaAttestationPolicyCallback.mNonce = str;
        return eaAttestationPolicyCallback;
    }

    public abstract void onAttestationFinished(EnhancedAttestationResult enhancedAttestationResult);
}
