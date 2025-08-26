package com.samsung.android.knox.integrity;

import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.knox.integrity.IEnhancedAttestationPolicyCallback;

/* loaded from: classes4.dex */
public abstract class EnhancedAttestationPolicyCallback {
    public static final String TAG = "EAPolicyCb";
    public EnhancedAttestationPolicyCallback acb = this;

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
