package com.android.server.enterprise.email;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import android.util.secutil.Slog;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.accounts.IExchangeAccountPolicy;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class AccountsReceiver {
  public static String TAG = "AccountsReceiver";
  public static Map mSmimeCerticateList = new HashMap();
  public Context mContext;
  public BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class:
        // com.android.server.enterprise.email.AccountsReceiver.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          try {
            String action = intent.getAction();
            int intExtra =
                intent.getIntExtra(
                    "com.samsung.android.knox.intent.extra.USER_HANDLE_ID_INTERNAL",
                    intent.getIntExtra("user_handle_id", 0));
            if (action == null) {
              Log.i(AccountsReceiver.TAG, "onReceive() : Action is null");
              return;
            }
            Log.i(AccountsReceiver.TAG, "onReceive() userId = " + intExtra);
            if ("com.samsung.android.knox.intent.action.CBA_INSTALL_STATUS_INTERNAL"
                .equals(action)) {
              int intExtra2 = intent.getIntExtra("com.samsung.android.knox.intent.extra.STATUS", 1);
              int intExtra3 =
                  intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", 0);
              long longExtra =
                  intent.getLongExtra(
                      "com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL", -1L);
              if (intExtra2 == 1) {
                Log.i(
                    AccountsReceiver.TAG,
                    "onReceive() : failed to install cba on accountId : " + longExtra);
              }
              AccountsReceiver.this.sendClientAuthResultIntent(longExtra, intExtra3, intExtra2);
              return;
            }
            if (!action.equals("edm.intent.action.sec.MDM_ACCOUNT_SETUP_RESULT")
                && !action.equals(
                    "com.samsung.android.knox.intent.action.MDM_ACCOUNT_SETUP_RESULT_INTERNAL")) {
              if (action.equals("edm.intent.action.sec.MDM_ACCOUNT_DELETE_RESULT")
                  || action.equals(
                      "com.samsung.android.knox.intent.action.MDM_ACCOUNT_DELETE_RESULT_INTERNAL")) {
                int intExtra4 = intent.getIntExtra("status", 8);
                String stringExtra = intent.getStringExtra("user_id");
                String stringExtra2 = intent.getStringExtra("service");
                long longExtra2 = intent.getLongExtra("account_id", -1L);
                int intExtra5 =
                    intent.getIntExtra(
                        "com.samsung.android.knox.intent.extra.ACCOUNT_SETUP_RESULT_STATUS_INTERNAL",
                        intExtra4);
                long longExtra3 =
                    intent.getLongExtra(
                        "com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL", longExtra2);
                if (stringExtra == null) {
                  stringExtra =
                      intent.getStringExtra(
                          "com.samsung.android.knox.intent.extra.USER_ID_INTERNAL");
                }
                if (stringExtra2 == null) {
                  stringExtra2 =
                      intent.getStringExtra(
                          "com.samsung.android.knox.intent.extra.SERVICE_INTERNAL");
                }
                if ("eas".equalsIgnoreCase(stringExtra2)) {
                  String stringExtra3 = intent.getStringExtra("server_name");
                  if (stringExtra3 == null) {
                    stringExtra3 =
                        intent.getStringExtra(
                            "com.samsung.android.knox.intent.extra.SERVICE_NAME_INTERNAL");
                  }
                  Intent intent2 =
                      new Intent(
                          "com.samsung.android.knox.intent.action.EXCHANGE_ACCOUNT_DELETE_RESULT");
                  intent2.putExtra(
                      "com.samsung.android.knox.intent.extra.EMAIL_ADDRESS", stringExtra);
                  intent2.putExtra("com.samsung.android.knox.intent.extra.RESULT", intExtra5);
                  intent2.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID", longExtra3);
                  intent2.putExtra(
                      "com.samsung.android.knox.intent.extra.SERVER_ADDRESS", stringExtra3);
                  intent2.putExtra("containerid", intExtra);
                  AccountsReceiver.this.mContext.sendBroadcastAsUser(
                      intent2, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_EXCHANGE");
                  return;
                }
                String stringExtra4 = intent.getStringExtra("receive_host");
                if (stringExtra4 == null) {
                  stringExtra4 =
                      intent.getStringExtra(
                          "com.samsung.android.knox.intent.extra.RECEIVE_HOST_INTERNAL");
                }
                Intent intent3 =
                    new Intent(
                        "com.samsung.android.knox.intent.action.EMAIL_ACCOUNT_DELETE_RESULT");
                intent3.putExtra(
                    "com.samsung.android.knox.intent.extra.EMAIL_ADDRESS", stringExtra);
                intent3.putExtra("com.samsung.android.knox.intent.extra.RESULT", intExtra5);
                intent3.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID", longExtra3);
                intent3.putExtra(
                    "com.samsung.android.knox.intent.extra.INCOMING_PROTOCOL", stringExtra2);
                intent3.putExtra(
                    "com.samsung.android.knox.intent.extra.INCOMING_SERVER_ADDRESS", stringExtra4);
                intent3.putExtra("containerid", intExtra);
                AccountsReceiver.this.mContext.sendBroadcastAsUser(
                    intent3, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_EMAIL");
                return;
              }
              return;
            }
            int intExtra6 = intent.getIntExtra("status", 8);
            String stringExtra5 = intent.getStringExtra("user_id");
            String stringExtra6 = intent.getStringExtra("service");
            long longExtra4 = intent.getLongExtra("account_id", -1L);
            int intExtra7 =
                intent.getIntExtra(
                    "com.samsung.android.knox.intent.extra.ACCOUNT_SETUP_RESULT_STATUS_INTERNAL",
                    intExtra6);
            long longExtra5 =
                intent.getLongExtra(
                    "com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL", longExtra4);
            if (stringExtra5 == null) {
              stringExtra5 =
                  intent.getStringExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL");
            }
            if (stringExtra6 == null) {
              stringExtra6 =
                  intent.getStringExtra("com.samsung.android.knox.intent.extra.SERVICE_INTERNAL");
            }
            if ("eas".equalsIgnoreCase(stringExtra6)) {
              String stringExtra7 = intent.getStringExtra("server_name");
              if (stringExtra7 == null) {
                stringExtra7 =
                    intent.getStringExtra(
                        "com.samsung.android.knox.intent.extra.SERVICE_NAME_INTERNAL");
              }
              Intent intent4 =
                  new Intent("com.samsung.android.knox.intent.action.EXCHANGE_ACCOUNT_ADD_RESULT");
              intent4.putExtra("com.samsung.android.knox.intent.extra.EMAIL_ADDRESS", stringExtra5);
              intent4.putExtra("com.samsung.android.knox.intent.extra.RESULT", intExtra7);
              intent4.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID", longExtra5);
              intent4.putExtra(
                  "com.samsung.android.knox.intent.extra.SERVER_ADDRESS", stringExtra7);
              intent4.putExtra("containerid", intExtra);
              AccountsReceiver.this.mContext.sendBroadcastAsUser(
                  intent4, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_EXCHANGE");
              AccountSMIMECertificate sMIMECertificate =
                  AccountsReceiver.getSMIMECertificate(intExtra + "#" + stringExtra5);
              if (sMIMECertificate == null || intExtra7 != 0 || longExtra5 == -1) {
                return;
              }
              new SMIMEThread(sMIMECertificate, longExtra5).start();
              Log.i(AccountsReceiver.TAG, "onRecieve() : SMIMECertificate install called.");
              return;
            }
            String stringExtra8 = intent.getStringExtra("receive_host");
            if (stringExtra8 == null) {
              stringExtra8 =
                  intent.getStringExtra(
                      "com.samsung.android.knox.intent.extra.RECEIVE_HOST_INTERNAL");
            }
            Intent intent5 =
                new Intent("com.samsung.android.knox.intent.action.EMAIL_ACCOUNT_ADD_RESULT");
            intent5.putExtra("com.samsung.android.knox.intent.extra.EMAIL_ADDRESS", stringExtra5);
            intent5.putExtra("com.samsung.android.knox.intent.extra.RESULT", intExtra7);
            intent5.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID", longExtra5);
            intent5.putExtra(
                "com.samsung.android.knox.intent.extra.INCOMING_PROTOCOL", stringExtra6);
            intent5.putExtra(
                "com.samsung.android.knox.intent.extra.INCOMING_SERVER_ADDRESS", stringExtra8);
            intent5.putExtra("containerid", intExtra);
            AccountsReceiver.this.mContext.sendBroadcastAsUser(
                intent5, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_EMAIL");
          } catch (Exception e) {
            Log.e(AccountsReceiver.TAG, "onRecieve() failed. ", e);
          }
        }
      };

  public AccountsReceiver(Context context) {
    this.mContext = context;
    try {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("edm.intent.action.sec.MDM_ACCOUNT_SETUP_RESULT");
      intentFilter.addAction("edm.intent.action.sec.MDM_ACCOUNT_DELETE_RESULT");
      intentFilter.addAction(
          "com.samsung.android.knox.intent.action.MDM_ACCOUNT_SETUP_RESULT_INTERNAL");
      intentFilter.addAction(
          "com.samsung.android.knox.intent.action.MDM_ACCOUNT_DELETE_RESULT_INTERNAL");
      intentFilter.addAction("com.samsung.android.knox.intent.action.CBA_INSTALL_STATUS_INTERNAL");
      context.registerReceiverAsUser(
          this.mReceiver,
          UserHandle.ALL,
          intentFilter,
          "com.samsung.android.knox.permission.KNOX_EMAIL",
          null);
    } catch (Exception e) {
      Log.e(TAG, "register Account Receiver : failed. ", e);
    }
  }

  public static boolean pushSMIMECertificate(
      String str, AccountSMIMECertificate accountSMIMECertificate) {
    try {
      mSmimeCerticateList.put(str, accountSMIMECertificate);
      Slog.d(TAG, "pushSMIMECertificate() success");
      return true;
    } catch (Exception e) {
      Log.e(TAG, "pushSMIMECertificate() failed", e);
      return false;
    }
  }

  public static AccountSMIMECertificate getSMIMECertificate(String str) {
    try {
      AccountSMIMECertificate accountSMIMECertificate =
          (AccountSMIMECertificate) mSmimeCerticateList.get(str);
      mSmimeCerticateList.remove(str);
      Slog.d(TAG, "getSMIMECertificate() success.");
      return accountSMIMECertificate;
    } catch (Exception e) {
      Log.e(TAG, "getSMIMECertificate() failed", e);
      return null;
    }
  }

  public class SMIMEThread extends Thread {
    public long mAccId;
    public AccountSMIMECertificate mSMIMECertificate;

    public SMIMEThread(AccountSMIMECertificate accountSMIMECertificate, long j) {
      this.mSMIMECertificate = accountSMIMECertificate;
      this.mAccId = j;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
      AccountSMIMECertificate accountSMIMECertificate = this.mSMIMECertificate;
      String str = accountSMIMECertificate.mPath;
      String str2 = accountSMIMECertificate.mPassword;
      int i = accountSMIMECertificate.sMode;
      ContextInfo contextInfo = accountSMIMECertificate.mCxtInfo;
      Log.i(
          AccountsReceiver.TAG,
          "SMIME Certificate as Account Creation Time >>>>>>> " + this.mAccId + " , " + i);
      int i2 = 0;
      try {
        IExchangeAccountPolicy asInterface =
            IExchangeAccountPolicy.Stub.asInterface(
                ServiceManager.getService("eas_account_policy"));
        if (asInterface != null) {
          if (i == 1) {
            i2 = asInterface.setForceSMIMECertificate(contextInfo, this.mAccId, str, str2);
          } else if (i == 2) {
            i2 =
                asInterface.setForceSMIMECertificateForEncryption(
                    contextInfo, this.mAccId, str, str2);
          } else if (i == 3) {
            i2 =
                asInterface.setForceSMIMECertificateForSigning(contextInfo, this.mAccId, str, str2);
          }
        }
      } catch (RemoteException e) {
        Log.e(AccountsReceiver.TAG, "SMIMEThread : Failed talking with exchange account policy", e);
      } catch (Exception e2) {
        Log.e(AccountsReceiver.TAG, "SMIMEThread : unexpected Exception occurs. ", e2);
      }
      Log.i(AccountsReceiver.TAG, "<<<<<<< SMIME Certificate as Account Creation Time : " + i2);
    }
  }

  public final void sendClientAuthResultIntent(long j, int i, int i2) {
    if (j < 0) {
      Log.d(TAG, "sendClientAuthResultIntent() : invalid accountId = " + j);
    }
    Intent intent = new Intent("com.samsung.android.knox.intent.action.CBA_INSTALL_STATUS");
    intent.putExtra("com.samsung.android.knox.intent.extra.STATUS", i2);
    this.mContext.sendBroadcastAsUser(
        intent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_EXCHANGE");
  }
}
