package com.samsung.android.knox.tima.attestation;

import android.content.Context;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemEnhancedAttestationPolicy {
    private static final String TAG = "SemEAPolicy";
    private static SemEnhancedAttestationPolicy mEnhancedAttestationPolicy;
    private Context mContext;
    private EnhancedAttestationPolicy mEaPolicy;

    private SemEnhancedAttestationPolicy(Context context) {
        Log.d(TAG, "SemEnhancedAttestationPolicy");
        this.mContext = context.getApplicationContext();
        this.mEaPolicy = getEaPolicy();
    }

    public static synchronized boolean isSupported(Context context) {
        Log.d(TAG, "isSupported: ");
        if (context == null) {
            Log.e(TAG, "isSupported: Context null");
            return false;
        }
        return EnhancedAttestationPolicy.getInstance(context).isSupported();
    }

    public static synchronized SemEnhancedAttestationPolicy getInstance(Context context) {
        Log.d(TAG, "getInstance");
        if (context == null) {
            Log.e(TAG, "getInstance: Context null");
            return null;
        }
        if (!isSupported(context)) {
            Log.e(TAG, "getInstance: not supported");
            return null;
        }
        if (mEnhancedAttestationPolicy == null) {
            mEnhancedAttestationPolicy = new SemEnhancedAttestationPolicy(context);
        }
        return mEnhancedAttestationPolicy;
    }

    public void attest(String str, SemEnhancedAttestationPolicyCallback semEnhancedAttestationPolicyCallback) {
        Log.d(TAG, "attest on-prem");
        this.mEaPolicy.startAttestation(str, semEnhancedAttestationPolicyCallback.getEnhancedAttestationPolicyCallback());
    }

    public void attest(String str, String str2, SemEnhancedAttestationPolicyCallback semEnhancedAttestationPolicyCallback) {
        Log.d(TAG, "attest");
        this.mEaPolicy.startAttestation(str, str2, semEnhancedAttestationPolicyCallback.getEnhancedAttestationPolicyCallback());
    }

    private EnhancedAttestationPolicy getEaPolicy() {
        Log.d(TAG, "getEaPolicy");
        return EnhancedAttestationPolicy.getInstance(this.mContext);
    }
}
