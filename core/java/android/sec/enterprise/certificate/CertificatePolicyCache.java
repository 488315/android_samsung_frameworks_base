package android.sec.enterprise.certificate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Bundle;
import android.os.Process;
import android.os.UserHandle;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.util.Log;

public class CertificatePolicyCache {
    public static final String ACTION_CERTIFICATE_POLICY_CHANGED_INTERNAL =
            "com.samsung.android.knox.intent.action.CERTIFICATE_POLICY_CHANGED_INTERNAL";
    public static final String EXTRA_CERTIFICATE_POLICY_TYPE_CHANGED_INTERNAL =
            "com.samsung.android.knox.intent.extra.CERTIFICATE_POLICY_TYPE_CHANGED_INTERNAL";
    public static final String TYPE_CERTIFICATE_TRUSTED_UNTRUSTED = "CERTIFICATE_TRUSTED_UNTRUSTED";
    public static final String TYPE_CERTIFICATE_VALIDATION = "CERTIFICATE_VALIDATION";
    private CertificatePolicy mCertificatePolicy =
            EnterpriseDeviceManager.getInstance().getCertificatePolicy();
    private boolean mCertificateValidationEnabled;
    private Context mContext;
    private boolean mTrustedUntrustedEnabled;
    private static String TAG = "CertificatePolicyCache";
    private static final Object mSync = new Object();
    private static CertificatePolicyCache sInstance = null;

    public static CertificatePolicyCache getInstance(Context context) {
        CertificatePolicyCache certificatePolicyCache;
        synchronized (mSync) {
            if (sInstance == null) {
                sInstance = new CertificatePolicyCache(context);
            }
            certificatePolicyCache = sInstance;
        }
        return certificatePolicyCache;
    }

    private CertificatePolicyCache(Context context) {
        this.mContext = context;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_CERTIFICATE_POLICY_CHANGED_INTERNAL);
        context.registerReceiver(
                new BroadcastReceiver() { // from class:
                                          // android.sec.enterprise.certificate.CertificatePolicyCache.1
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context2, Intent intent) {
                        String action = intent.getAction();
                        if (CertificatePolicyCache.ACTION_CERTIFICATE_POLICY_CHANGED_INTERNAL
                                .equals(action)) {
                            Log.d(CertificatePolicyCache.TAG, "Intent received to refresh cache");
                            Bundle extras = intent.getExtras();
                            if (extras != null) {
                                String type =
                                        extras.getString(
                                                CertificatePolicyCache
                                                        .EXTRA_CERTIFICATE_POLICY_TYPE_CHANGED_INTERNAL);
                                CertificatePolicyCache.this.readVariables(type);
                            } else {
                                CertificatePolicyCache.this.readVariables(null);
                            }
                        }
                    }
                },
                intentFilter);
        Log.d(
                TAG,
                "Creating new instance of CertificatePolicyCache myUid: "
                        + Process.myUid()
                        + " callingUid: "
                        + Binder.getCallingUid());
        readVariables(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void readVariables(String type) {
        int userId = UserHandle.myUserId();
        Log.d(TAG, "readVariables type: " + type + " userId: " + userId);
        if (type == null) {
            this.mTrustedUntrustedEnabled =
                    this.mCertificatePolicy.isCertificateTrustedUntrustedEnabledAsUser(userId);
            this.mCertificateValidationEnabled =
                    this.mCertificatePolicy.isCertificateValidationAtInstallEnabledAsUser(userId);
        } else if (type.equals(TYPE_CERTIFICATE_TRUSTED_UNTRUSTED)) {
            this.mTrustedUntrustedEnabled =
                    this.mCertificatePolicy.isCertificateTrustedUntrustedEnabledAsUser(userId);
        } else if (type.equals(TYPE_CERTIFICATE_VALIDATION)) {
            this.mCertificateValidationEnabled =
                    this.mCertificatePolicy.isCertificateValidationAtInstallEnabledAsUser(userId);
        }
    }

    public boolean isCertificateTrustedUntrustedEnabled() {
        return this.mTrustedUntrustedEnabled;
    }

    public boolean isCertificateValidationAtInstallEnabled() {
        return this.mCertificateValidationEnabled;
    }
}
