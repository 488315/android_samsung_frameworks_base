package com.android.server.enterprise.restriction;

import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.PinResult;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ISemTelephony;
import com.android.internal.telephony.ITelephony;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;

public final class EnterpriseSimPin {
    public SubscriptionManager mSubscriptionManager;
    public TelephonyManager mTelephonyManager;

    public static void enforceCaller() {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("Can only be called by System");
        }
    }

    public static int getIccLockRetryNumber(int i) {
        NetworkScoreService$$ExternalSyntheticOutline0.m(
                i, "getIccLockRetryNumber(", ")", "EnterpriseSimPin");
        int i2 = 0;
        try {
            ISemTelephony asInterface =
                    ISemTelephony.Stub.asInterface(ServiceManager.getService("isemtelephony"));
            if (asInterface != null) {
                i2 = asInterface.getSimPinRetryForSubscriber(i);
            }
        } catch (RemoteException e) {
            ActivityManagerService$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("RemoteException for getIccLockRetryNumber: "),
                    "EnterpriseSimPin");
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                i2, "getIccLockRetryNumber() = ", "EnterpriseSimPin");
        return i2;
    }

    public static int handlePinResult(PinResult pinResult) {
        Log.d("EnterpriseSimPin", "handlePinResult() = " + pinResult);
        int result = pinResult.getResult();
        if (result == 0) {
            return 0;
        }
        if (result != 1) {
            return result != 2 ? 100 : 1;
        }
        return 3;
    }

    public static void supplyPinReportResultForSubscriber(int i, String str) {
        Log.d("EnterpriseSimPin", "supplyPinReportResultForSubscriber(" + i + ")");
        enforceCaller();
        try {
            ITelephony asInterface =
                    ITelephony.Stub.asInterface(ServiceManager.checkService("phone"));
            if (asInterface == null) {
                Log.e("EnterpriseSimPin", "Null IBinder for phone service. Aborting...");
                return;
            }
            int[] supplyPinReportResultForSubscriber =
                    asInterface.supplyPinReportResultForSubscriber(i, str);
            Log.d(
                    "EnterpriseSimPin",
                    "supplyPinReportResult returned: "
                            + supplyPinReportResultForSubscriber[0]
                            + " "
                            + supplyPinReportResultForSubscriber[1]);
            int i2 = supplyPinReportResultForSubscriber[0];
        } catch (RemoteException e) {
            ActivityManagerService$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("RemoteException for supplyPinReportResult: "),
                    "EnterpriseSimPin");
        }
    }

    public final int changeSimPinCode(int i, String str, String str2) {
        Log.d("EnterpriseSimPin", "changeSimPinCode(" + i + ")");
        enforceCaller();
        TelephonyManager createTelephonyForSubId = createTelephonyForSubId(i);
        if (createTelephonyForSubId.getSimState() == 3) {
            return 6;
        }
        if (!createTelephonyForSubId.isIccLockEnabled()) {
            return 5;
        }
        if (getIccLockRetryNumber(i) <= 1) {
            return 8;
        }
        return handlePinResult(createTelephonyForSubId.changeIccLockPin(str, str2));
    }

    public final TelephonyManager createTelephonyForSubId(int i) {
        NetworkScoreService$$ExternalSyntheticOutline0.m(
                i, "createTelephonyForSubId(", ")", "EnterpriseSimPin");
        return this.mSubscriptionManager.isActiveSubId(i)
                ? this.mTelephonyManager.createForSubscriptionId(i)
                : this.mTelephonyManager;
    }

    public final boolean isSimLocked(int i) {
        Log.d("EnterpriseSimPin", "isSimLocked(" + i + ")");
        enforceCaller();
        return createTelephonyForSubId(i).isIccLockEnabled();
    }

    public final int setSubIdLockEnabled(int i, String str, boolean z) {
        Log.d("EnterpriseSimPin", "setSubIdLockEnabled(" + i + ", " + z + ")");
        enforceCaller();
        TelephonyManager createTelephonyForSubId = createTelephonyForSubId(i);
        if (createTelephonyForSubId.getSimState() == 3) {
            return 6;
        }
        boolean isIccLockEnabled = createTelephonyForSubId.isIccLockEnabled();
        if (isIccLockEnabled && z) {
            return 4;
        }
        if (!isIccLockEnabled && !z) {
            return 5;
        }
        if (getIccLockRetryNumber(i) <= 1) {
            return 8;
        }
        return handlePinResult(createTelephonyForSubId.setIccLockEnabled(z, str));
    }
}
