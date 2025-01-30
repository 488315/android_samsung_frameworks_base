package com.samsung.android.knox.threatdefense;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.threatdefense.IThreatDefenseService;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ThreatDefensePolicy {
    public static final String ACTION_PACKAGE_RULES_REMOVED = "com.samsung.android.knox.intent.action.MTDL_PACKAGE_RULES_REMOVED";
    public static final int ERROR_CAST_CLASS = -104;
    public static final int ERROR_INIT_JSON_OBJECT = -107;
    public static final int ERROR_INVALID_ARGUMENT = -106;
    public static final int ERROR_INVALID_PKG = -101;
    public static final int ERROR_INVALID_PROC = -105;
    public static final int ERROR_NO_PACKAGE_RULES = -102;
    public static final int ERROR_POLICY_VERSION = -100;
    public static final int ERROR_RESTRICT_CHAR = -103;
    public static final int ERROR_SIGNATURE = -108;
    public static final String KNOX_MOBILE_THREAT_DEFENSE_PERMISSION = "com.samsung.android.knox.permission.KNOX_MOBILE_THREAT_DEFENSE";
    public static final String TAG = "ThreatDefensePolicy";
    public static final Object mSynchronized = new Object();
    public Context mContext;
    public ContextInfo mContextInfo;
    public IThreatDefenseService mThreatDefenseService = null;

    public ThreatDefensePolicy(ContextInfo contextInfo, Context context) {
        this.mContextInfo = contextInfo;
        this.mContext = context;
    }

    public final List<Integer> getProcessId(String str) {
        if (getThreatDefenseService() == null) {
            return null;
        }
        try {
            ArrayList arrayList = new ArrayList();
            int[] processId = this.mThreatDefenseService.getProcessId(this.mContextInfo, str);
            if (processId != null) {
                for (int i : processId) {
                    arrayList.add(Integer.valueOf(i));
                }
            }
            return arrayList;
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to call getProcessId()");
            e.printStackTrace();
            return null;
        }
    }

    public final synchronized IThreatDefenseService getThreatDefenseService() {
        if (this.mThreatDefenseService == null) {
            this.mThreatDefenseService = IThreatDefenseService.Stub.asInterface(ServiceManager.getService("threat_defense_service"));
        }
        return this.mThreatDefenseService;
    }

    public final boolean hasPackageRules() {
        if (getThreatDefenseService() == null) {
            return false;
        }
        try {
            return this.mThreatDefenseService.hasPackageRules(this.mContextInfo);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to call hasPackageRules()");
            e.printStackTrace();
            return false;
        }
    }

    public final String procReader(String str) {
        if (getThreatDefenseService() == null) {
            return null;
        }
        try {
            return this.mThreatDefenseService.procReader(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to call procReader()");
            e.printStackTrace();
            return null;
        }
    }

    public final String processProcReader(String str, int i) {
        if (getThreatDefenseService() == null) {
            return null;
        }
        try {
            return this.mThreatDefenseService.processProcReader(this.mContextInfo, str, i);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to call processProcReader()");
            e.printStackTrace();
            return null;
        }
    }

    public final int setPackageRules(String str) {
        if (getThreatDefenseService() == null) {
            return -1;
        }
        try {
            return this.mThreatDefenseService.setPackageRules(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to call setPackageRules()");
            e.printStackTrace();
            return -1;
        }
    }
}
