package com.android.server.enterprise.email;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import android.util.secutil.Slog;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.accounts.IExchangeAccountPolicy;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AccountsReceiver {
    public static final Map mSmimeCerticateList = new HashMap();
    public Context mContext;
    public AnonymousClass1 mReceiver;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SMIMEThread extends Thread {
        public long mAccId;
        public AccountSMIMECertificate mSMIMECertificate;

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            AccountSMIMECertificate accountSMIMECertificate = this.mSMIMECertificate;
            String str = accountSMIMECertificate.mPath;
            String str2 = accountSMIMECertificate.mPassword;
            int i = accountSMIMECertificate.sMode;
            ContextInfo contextInfo = accountSMIMECertificate.mCxtInfo;
            Map map = AccountsReceiver.mSmimeCerticateList;
            Log.i("AccountsReceiver", "SMIME Certificate as Account Creation Time >>>>>>> " + this.mAccId + " , " + i);
            int i2 = 0;
            try {
                IExchangeAccountPolicy asInterface = IExchangeAccountPolicy.Stub.asInterface(ServiceManager.getService("eas_account_policy"));
                if (asInterface != null) {
                    if (i == 1) {
                        i2 = asInterface.setForceSMIMECertificate(contextInfo, this.mAccId, str, str2);
                    } else if (i == 2) {
                        i2 = asInterface.setForceSMIMECertificateForEncryption(contextInfo, this.mAccId, str, str2);
                    } else if (i == 3) {
                        i2 = asInterface.setForceSMIMECertificateForSigning(contextInfo, this.mAccId, str, str2);
                    }
                }
            } catch (RemoteException e) {
                Map map2 = AccountsReceiver.mSmimeCerticateList;
                Log.e("AccountsReceiver", "SMIMEThread : Failed talking with exchange account policy", e);
            } catch (Exception e2) {
                Map map3 = AccountsReceiver.mSmimeCerticateList;
                Log.e("AccountsReceiver", "SMIMEThread : unexpected Exception occurs. ", e2);
            }
            DirEncryptService$$ExternalSyntheticOutline0.m(i2, "<<<<<<< SMIME Certificate as Account Creation Time : ", "AccountsReceiver");
        }
    }

    /* renamed from: -$$Nest$msendClientAuthResultIntent, reason: not valid java name */
    public static void m505$$Nest$msendClientAuthResultIntent(AccountsReceiver accountsReceiver, long j, int i) {
        if (j < 0) {
            Log.d("AccountsReceiver", "sendClientAuthResultIntent() : invalid accountId = " + j);
        }
        Intent intent = new Intent("com.samsung.android.knox.intent.action.CBA_INSTALL_STATUS");
        intent.putExtra("com.samsung.android.knox.intent.extra.STATUS", i);
        accountsReceiver.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_EXCHANGE");
    }

    public static AccountSMIMECertificate getSMIMECertificate(String str) {
        try {
            Map map = mSmimeCerticateList;
            AccountSMIMECertificate accountSMIMECertificate = (AccountSMIMECertificate) ((HashMap) map).get(str);
            ((HashMap) map).remove(str);
            Slog.d("AccountsReceiver", "getSMIMECertificate() success.");
            return accountSMIMECertificate;
        } catch (Exception e) {
            Log.e("AccountsReceiver", "getSMIMECertificate() failed", e);
            return null;
        }
    }

    public static boolean pushSMIMECertificate(String str, AccountSMIMECertificate accountSMIMECertificate) {
        try {
            ((HashMap) mSmimeCerticateList).put(str, accountSMIMECertificate);
            Slog.d("AccountsReceiver", "pushSMIMECertificate() success");
            return true;
        } catch (Exception e) {
            Log.e("AccountsReceiver", "pushSMIMECertificate() failed", e);
            return false;
        }
    }
}
