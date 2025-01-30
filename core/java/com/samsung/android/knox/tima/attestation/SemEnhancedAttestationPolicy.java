package com.samsung.android.knox.tima.attestation;

import android.content.Context;
import android.util.Log;

/* loaded from: classes5.dex */
public class SemEnhancedAttestationPolicy {
    private static final String TAG = "SemEAPolicy";
    private static SemEnhancedAttestationPolicy mEnhancedAttestationPolicy = null;
    private Context mContext;
    private EnhancedAttestationPolicy mEaPolicy;

    private SemEnhancedAttestationPolicy(Context context) {
        Log.m94d(TAG, "SemEnhancedAttestationPolicy");
        this.mContext = context.getApplicationContext();
        this.mEaPolicy = getEaPolicy();
    }

    public static synchronized boolean isSupported(Context context) {
        synchronized (SemEnhancedAttestationPolicy.class) {
            Log.m94d(TAG, "isSupported: ");
            if (context == null) {
                Log.m96e(TAG, "isSupported: Context null");
                return false;
            }
            return EnhancedAttestationPolicy.getInstance(context).isSupported();
        }
    }

    public static synchronized SemEnhancedAttestationPolicy getInstance(Context context) {
        synchronized (SemEnhancedAttestationPolicy.class) {
            Log.m94d(TAG, "getInstance");
            if (context == null) {
                Log.m96e(TAG, "getInstance: Context null");
                return null;
            }
            if (!isSupported(context)) {
                Log.m96e(TAG, "getInstance: not supported");
                return null;
            }
            if (mEnhancedAttestationPolicy == null) {
                mEnhancedAttestationPolicy = new SemEnhancedAttestationPolicy(context);
            }
            return mEnhancedAttestationPolicy;
        }
    }

    public void attest(String nonce, SemEnhancedAttestationPolicyCallback cb) {
        Log.m94d(TAG, "attest on-prem");
        this.mEaPolicy.startAttestation(nonce, cb.getEnhancedAttestationPolicyCallback());
    }

    public void attest(String auk, String nonce, SemEnhancedAttestationPolicyCallback cb) {
        Log.m94d(TAG, "attest");
        this.mEaPolicy.startAttestation(auk, nonce, cb.getEnhancedAttestationPolicyCallback());
    }

    private EnhancedAttestationPolicy getEaPolicy() {
        Log.m94d(TAG, "getEaPolicy");
        return EnhancedAttestationPolicy.getInstance(this.mContext);
    }
}
