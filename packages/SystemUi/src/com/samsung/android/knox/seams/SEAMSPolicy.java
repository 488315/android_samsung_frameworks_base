package com.samsung.android.knox.seams;

import android.content.Context;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.seams.ISEAMS;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SEAMSPolicy {
    public static final int BBC_SECURED_APPTYPE = 5;
    public static final int CLIPBOARD_DISABLE_BIDIRECTIONAL = 1;
    public static final int CLIPBOARD_ENABLE_BIDIRECTIONAL = 0;
    public static final int ERROR_ALREADY_CONTAINER_APP = -9;
    public static final int ERROR_CERTS_NOT_MATCHED = -13;
    public static final int ERROR_CONTAINER_COUNTS_OVERFLOW = -7;
    public static final int ERROR_CONTAINER_ID_MISMATCH = -12;
    public static final int ERROR_CONTAINER_NOT_EMPTY = -11;
    public static final int ERROR_NOT_SUPPORTED = -3;
    public static final int ERROR_NO_CERTS = -14;
    public static final int FALSE = 0;
    public static final int GENERIC_SECURED_APPTYPE = 3;
    public static final int GENERIC_TRUSTED_APPTYPE = 4;
    public static final int GET_SERVICE_ERROR = -10;
    public static final int IRM_PLATFORM_APPTYPE = 7;
    public static final int IRM_UNTRUST_APPTYPE = 8;
    public static final int NOT_INSTALLED = -4;
    public static final int POLICY_FAILED = -1;
    public static final int POLICY_OK = 0;
    public static final int POLICY_REFUSED = -2;
    public static final int RUNNING = -8;
    public static final int SET_DEFAULT_MASK = 0;
    public static final String TAG = "SEAMS";
    public static final int TRUE = 1;
    public static SEAMSPolicy mSEAMS;
    public ISEAMS mSEAMService;
    public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public static final Object mSync = new Object();

    private SEAMSPolicy(ContextInfo contextInfo, Context context) {
    }

    public static SEAMSPolicy createInstance(ContextInfo contextInfo, Context context) {
        return new SEAMSPolicy(contextInfo, context.getApplicationContext());
    }

    public static SEAMSPolicy getInstance(Context context) {
        SEAMSPolicy sEAMSPolicy;
        if (context == null) {
            return null;
        }
        synchronized (mSync) {
            if (mSEAMS == null) {
                mSEAMS = new SEAMSPolicy(new ContextInfo(Process.myUid()), context.getApplicationContext());
            }
            sEAMSPolicy = mSEAMS;
        }
        return sEAMSPolicy;
    }

    public final int activateDomain() {
        return -1;
    }

    public final int addAppToContainer(String str, String str2, int i, int i2) {
        return -1;
    }

    public final int changeAppDomain(String str, int i, String str2, boolean z) {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mSEAMService.changeAppDomain(str, z);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to change the app domain");
            return -1;
        }
    }

    public final int createSEContainer() {
        return -1;
    }

    public final int deActivateDomain() {
        return -1;
    }

    public final int forceAuthorized(int i, int i2, String str, String str2) {
        if (getService() != null) {
            return isAuthorized(i, i2, str, str2);
        }
        Log.w(TAG, "Service is null");
        if (Process.myPid() == i) {
            return 0;
        }
        Log.w(TAG, "Process ID rejected.");
        return -1;
    }

    public final String getAMSLog() {
        return null;
    }

    public final int getAMSLogLevel() {
        return -1;
    }

    public final int getAMSMode() {
        return -1;
    }

    public final String getAVCLog() {
        return null;
    }

    public final int getActivationStatus() {
        return -1;
    }

    public final String getDataType(String str) {
        return null;
    }

    public final String getDomain(String str) {
        return null;
    }

    public final String[] getPackageNamesFromSEContainer(int i, int i2) {
        return null;
    }

    public final String getSEAMSLog() {
        return null;
    }

    public final int[] getSEContainerIDs() {
        return null;
    }

    public final int[] getSEContainerIDsFromPackageName(String str, int i) {
        return null;
    }

    public final int getSELinuxMode() {
        return -1;
    }

    public final String getSepolicyVersion() {
        return null;
    }

    public final synchronized ISEAMS getService() {
        if (this.mSEAMService == null) {
            this.mSEAMService = ISEAMS.Stub.asInterface(ServiceManager.getService("SEAMService"));
        }
        return this.mSEAMService;
    }

    public final String getSignatureFromCertificate(byte[] bArr) {
        return null;
    }

    public final String getSignatureFromMac(String str) {
        return null;
    }

    public final String getSignatureFromPackage(String str) {
        return null;
    }

    public final int hasKnoxContainers() {
        return -1;
    }

    public final int hasSEContainers() {
        return -1;
    }

    public final int isAuthorized(int i, int i2, String str, String str2) {
        if (getService() != null) {
            try {
                return this.mSEAMService.isAuthorized(i, i2, str, str2);
            } catch (RemoteException unused) {
                Log.w(TAG, "Failed to check the authenticity of the caller");
            }
        }
        Log.w(TAG, "SystemService null. Returning GET_SERVICE_ERROR.");
        return -10;
    }

    public final int isSEAndroidLogDumpStateInclude() {
        return -1;
    }

    public final int isSEPolicyAutoUpdateEnabled() {
        return -1;
    }

    public final int loadContainerSetting(String str) {
        return -1;
    }

    public final int relabelAppDir(String str) {
        return -1;
    }

    public final int relabelData() {
        return -1;
    }

    public final int removeAppFromContainer(String str, String str2, int i, int i2) {
        return -1;
    }

    public final int removeSEContainer(int i) {
        return -1;
    }

    public final int setAMSLogLevel(int i) {
        return -1;
    }

    public final int setSEAndroidLogDumpStateInclude(boolean z) {
        return -1;
    }

    public final int activateDomain(boolean z) {
        return -1;
    }

    public final String getDataType(String str, int i) {
        return null;
    }

    public final String getDomain(String str, int i) {
        return null;
    }

    public final int removeAppFromContainer(String str, String[] strArr) {
        return -1;
    }

    public static SEAMSPolicy getInstance(ContextInfo contextInfo, Context context) {
        SEAMSPolicy sEAMSPolicy;
        synchronized (mSync) {
            if (mSEAMS == null) {
                mSEAMS = new SEAMSPolicy(contextInfo, context.getApplicationContext());
            }
            sEAMSPolicy = mSEAMS;
        }
        return sEAMSPolicy;
    }
}
