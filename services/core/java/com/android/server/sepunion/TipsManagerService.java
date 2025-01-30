package com.android.server.sepunion;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.sepunion.ITipsManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class TipsManagerService extends ITipsManager.Stub implements AbsSemSystemService {
  public static final String TAG = TipsManagerService.class.getSimpleName();
  public static Long mLastDeviceConnectMsgTime = new Long(0);
  public boolean mBootupCompleted;
  public Context mContext;
  public int mDialCount;
  public int mHUNDisplayCount;
  public final TipsPackageReceiver mPackageReceiver;
  public final TipsReceiver mTipReceiver;
  public boolean mTipsHUNAlreadyShown;
  public TipsHandler mTipsHandler;
  public boolean mTipsNetworkGranted;
  public boolean mTipsOnBoot;
  public boolean mTipsPackageExist;
  public TipsManagerServiceThread mTipsServiceThread;
  public boolean mWaitingCallEnd;

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void onCreate(Bundle bundle) {}

  public class TipsPackageReceiver extends BroadcastReceiver {
    public TipsPackageReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      if (intent.getAction().equals("android.intent.action.PACKAGE_INSTALL")
          || intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
        if ("com.samsung.android.app.tips"
            .equals(intent.getData().getEncodedSchemeSpecificPart())) {
          Log.e(
              TipsManagerService.TAG,
              "[GalaxyTips] Tips was installed. Start to register all filters");
          TipsManagerService.this.mTipsPackageExist = true;
          if (TipsManagerService.this.mTipsOnBoot) {
            context.unregisterReceiver(TipsManagerService.this.mTipReceiver);
          }
          IntentFilter intentFilter = new IntentFilter();
          intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
          intentFilter.addAction("samsung.galaxy.tips.application.terminated");
          intentFilter.addAction("android.intent.action.USER_SWITCHED");
          if (!TipsManagerService.this.mTipsNetworkGranted) {
            intentFilter.addAction("samsung.galaxy.tips.network_granted");
          }
          if (!TipsManagerService.this.mTipsHUNAlreadyShown) {
            intentFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
            intentFilter.addAction("android.intent.action.PHONE_STATE");
          }
          context.registerReceiverAsUser(
              TipsManagerService.this.mTipReceiver, UserHandle.ALL, intentFilter, null, null);
          TipsManagerService.this.mTipsOnBoot = true;
          return;
        }
        return;
      }
      if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")
          && "com.samsung.android.app.tips"
              .equals(intent.getData().getEncodedSchemeSpecificPart())) {
        Log.e(TipsManagerService.TAG, "[GalaxyTips] Tips was uninstalled.");
        TipsManagerService.this.mTipsPackageExist = false;
      }
    }
  }

  public class TipsReceiver extends BroadcastReceiver {
    public TipsReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      int intForUser =
          Settings.Secure.getIntForUser(
              TipsManagerService.this.mContext.getContentResolver(), "user_setup_complete", 0, -2);
      boolean isEmergencyMode =
          SemEmergencyManager.getInstance(context) != null
              ? SemEmergencyManager.isEmergencyMode(context)
              : false;
      if (intForUser != 1 || isEmergencyMode || !TipsManagerService.this.mTipsPackageExist) {
        Log.e(
            TipsManagerService.TAG,
            "[GalaxyTips] Got a "
                + intent.getAction()
                + ". But can't perform.(completeSetupWizard= "
                + intForUser
                + " EmergencyMode= "
                + isEmergencyMode
                + " TipsExist= "
                + TipsManagerService.this.mTipsPackageExist
                + ")");
        return;
      }
      try {
        String action = intent.getAction();
        if (!TipsManagerService.this.mTipsHUNAlreadyShown
            && !TipsManagerService.this.mWaitingCallEnd
            && action.equals("android.intent.action.NEW_OUTGOING_CALL")) {
          TipsManagerService.this.mWaitingCallEnd = true;
          return;
        }
        if (TipsManagerService.this.mWaitingCallEnd
            && action.equals("android.intent.action.PHONE_STATE")) {
          Bundle extras = intent.getExtras();
          if (extras == null
              || !extras
                  .getString(LauncherConfigurationInternal.KEY_STATE_BOOLEAN)
                  .equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            return;
          }
          TipsManagerService.this.mWaitingCallEnd = false;
          if (TipsManagerService.this.mDialCount == 5) {
            Log.d(TipsManagerService.TAG, "[GalaxyTips]TIPS Activitation!! WAKE UP");
            Settings.System.putIntForUser(
                TipsManagerService.this.mContext.getContentResolver(),
                "tips_trigger_count",
                1000,
                -2);
            if (!TipsManagerService.this.mTipsNetworkGranted) {
              TipsManagerService.sendMsg(
                  TipsManagerService.this.mTipsHandler, 10001, 0, 0, 0, null, 60000);
            }
            TipsManagerService.this.mTipsHUNAlreadyShown = true;
            return;
          }
          ContentResolver contentResolver = TipsManagerService.this.mContext.getContentResolver();
          TipsManagerService tipsManagerService = TipsManagerService.this;
          int i = tipsManagerService.mDialCount + 1;
          tipsManagerService.mDialCount = i;
          Settings.System.putIntForUser(contentResolver, "tips_trigger_count", i, -2);
          return;
        }
        if (!TipsManagerService.this.mTipsNetworkGranted
            && action.equals("samsung.galaxy.tips.network_granted")) {
          TipsManagerService.this.mTipsNetworkGranted = true;
          Settings.System.putIntForUser(
              TipsManagerService.this.mContext.getContentResolver(),
              "gtips_network_granted",
              1,
              -2);
          return;
        }
        if (action.equals("samsung.galaxy.tips.application.terminated")) {
          Settings.System.putIntForUser(
              TipsManagerService.this.mContext.getContentResolver(),
              "tips_regular_hour_timer_renewal_count",
              0,
              -2);
          return;
        }
        if (TipsManagerService.this.mBootupCompleted
            && action.equals("android.intent.action.LOCALE_CHANGED")) {
          Intent intent2 = new Intent();
          intent2.setClassName(
              "com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
          intent2.putExtra("tips_extras", 1);
          try {
            TipsManagerService.this.mContext.startForegroundServiceAsUser(
                intent2, UserHandle.CURRENT);
            return;
          } catch (Exception unused) {
            Log.d(
                TipsManagerService.TAG,
                "[GalaxyTips] Fail to send MSG_NEW_DATA_UPDATED intent to Tips.");
            return;
          }
        }
        if (TipsManagerService.this.mBootupCompleted
            && action.equals("android.intent.action.USER_SWITCHED")) {
          if (Settings.System.getIntForUser(
                  TipsManagerService.this.mContext.getContentResolver(),
                  "gtips_network_granted",
                  0,
                  -2)
              == 1) {
            TipsManagerService.this.mTipsNetworkGranted = true;
          } else {
            TipsManagerService.this.mTipsNetworkGranted = false;
          }
          Log.d(
              TipsManagerService.TAG,
              "USER_SWITCHED isNetworkGranted " + TipsManagerService.this.mTipsNetworkGranted);
          return;
        }
        if (TipsManagerService.this.mBootupCompleted
            && action.equals("com.sec.android.app.secsetupwizard.FOTA_SUW_COMPLETE")
            && TipsManagerService.this.mTipsPackageExist) {
          TipsManagerService.sendMsg(
              TipsManagerService.this.mTipsHandler, 10004, 0, 0, 0, null, 120000);
        }
      } catch (Exception unused2) {
        Log.d(TipsManagerService.TAG, "Fail to send intent to Tips.");
      }
    }
  }

  public TipsManagerService(Context context) {
    TipsReceiver tipsReceiver = new TipsReceiver();
    this.mTipReceiver = tipsReceiver;
    this.mPackageReceiver = new TipsPackageReceiver();
    this.mDialCount = 1;
    this.mHUNDisplayCount = 0;
    this.mWaitingCallEnd = false;
    this.mTipsHUNAlreadyShown = false;
    this.mTipsNetworkGranted = false;
    this.mTipsPackageExist = true;
    this.mBootupCompleted = false;
    this.mTipsOnBoot = true;
    this.mContext = context;
    Log.d(TAG, "[GalaxyTips] TipsManagerService");
    try {
      this.mContext.getPackageManager().getPackageInfo("com.samsung.android.app.tips", 1);
      IntentFilter intentFilter = new IntentFilter();
      int intForUser =
          Settings.System.getIntForUser(
              this.mContext.getContentResolver(), "tips_trigger_count", 0, -2);
      this.mDialCount = intForUser;
      if (intForUser == 1000) {
        this.mTipsHUNAlreadyShown = true;
      }
      if (Settings.System.getIntForUser(
              this.mContext.getContentResolver(), "gtips_network_granted", 0, -2)
          == 1) {
        this.mTipsNetworkGranted = true;
      }
      intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
      intentFilter.addAction("samsung.galaxy.tips.application.terminated");
      intentFilter.addAction("android.intent.action.USER_SWITCHED");
      intentFilter.addAction("com.sec.android.app.secsetupwizard.FOTA_SUW_COMPLETE");
      if (!this.mTipsNetworkGranted) {
        intentFilter.addAction("samsung.galaxy.tips.network_granted");
      }
      if (!this.mTipsHUNAlreadyShown) {
        intentFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        intentFilter.addAction("android.intent.action.PHONE_STATE");
      }
      this.mContext.registerReceiverAsUser(tipsReceiver, UserHandle.ALL, intentFilter, null, null);
    } catch (PackageManager.NameNotFoundException unused) {
      this.mTipsPackageExist = false;
      this.mTipsOnBoot = false;
    }
    IntentFilter intentFilter2 = new IntentFilter();
    intentFilter2.addAction("android.intent.action.PACKAGE_INSTALL");
    intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
    intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
    intentFilter2.addDataScheme("package");
    this.mContext.registerReceiverAsUser(
        this.mPackageReceiver, UserHandle.ALL, intentFilter2, null, null);
    TipsManagerServiceThread tipsManagerServiceThread = new TipsManagerServiceThread();
    this.mTipsServiceThread = tipsManagerServiceThread;
    tipsManagerServiceThread.start();
  }

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void onBootPhase(int i) {
    if (i == 1000) {
      this.mBootupCompleted = true;
    }
  }

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (strArr != null && strArr.length > 0) {
      String str = strArr[0];
      if ("welcome".equals(str)) {
        Intent intent = new Intent();
        intent.setClassName(
            "com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
        intent.putExtra("tips_extras", 2);
        this.mContext.startForegroundServiceAsUser(intent, UserHandle.CURRENT);
        return;
      }
      if ("clearall".equals(str)) {
        Intent intent2 = new Intent();
        intent2.setClassName(
            "com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
        intent2.putExtra("tips_extras", 1);
        this.mContext.startForegroundServiceAsUser(intent2, UserHandle.CURRENT);
        return;
      }
      if ("showbasic".equals(str)) {
        Intent intent3 = new Intent();
        intent3.setClassName(
            "com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
        intent3.putExtra("tips_extras", 3);
        this.mContext.startForegroundServiceAsUser(intent3, UserHandle.CURRENT);
        return;
      }
      if ("getcustom".equals(str)) {
        Intent intent4 = new Intent();
        intent4.setAction("android.intent.action.MAIN");
        intent4.setFlags(402653184);
        intent4.setClassName(
            "com.samsung.android.app.tips",
            "com.samsung.android.app.tips.TipsSmartItemDetailActivity");
        intent4.putExtra("custom_inventory", strArr[1]);
        this.mContext.startActivity(intent4);
        return;
      }
      if ("fotadone".equals(str)) {
        Intent intent5 = new Intent();
        intent5.setClassName(
            "com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
        intent5.putExtra("tips_extras", 5);
        this.mContext.startForegroundServiceAsUser(intent5, UserHandle.CURRENT);
        return;
      }
      if ("showrecent".equals(str)) {
        Intent intent6 = new Intent();
        intent6.setClassName(
            "com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
        intent6.putExtra("tips_extras", 6);
        this.mContext.startForegroundServiceAsUser(intent6, UserHandle.CURRENT);
        return;
      }
      if ("showcontent".equals(str)) {
        Intent intent7 = new Intent();
        intent7.setClassName(
            "com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
        intent7.putExtra("tips_extras", 7);
        intent7.putExtra("tips_extras2", strArr[1]);
        this.mContext.startForegroundServiceAsUser(intent7, UserHandle.CURRENT);
        return;
      }
      if ("showjit".equals(str)) {
        Intent intent8 = new Intent();
        intent8.setClassName(
            "com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
        intent8.putExtra("tips_extras", 8);
        intent8.putExtra("tips_extras2", strArr[1]);
        if (strArr.length == 2) {
          intent8.putExtra("tips_extras4", "");
          intent8.putExtra("tips_extras3", "");
          intent8.putExtra("tips_extras5", "");
          intent8.putExtra("tips_extras6", "");
        } else if (strArr.length == 3) {
          intent8.putExtra("tips_extras4", strArr[2]);
          intent8.putExtra("tips_extras3", "");
          intent8.putExtra("tips_extras5", "");
          intent8.putExtra("tips_extras6", "");
        } else if (strArr.length == 4) {
          intent8.putExtra("tips_extras4", strArr[2]);
          intent8.putExtra("tips_extras3", strArr[3]);
          intent8.putExtra("tips_extras5", "");
          intent8.putExtra("tips_extras6", "");
        } else if (strArr.length == 5) {
          intent8.putExtra("tips_extras4", strArr[2]);
          intent8.putExtra("tips_extras3", strArr[3]);
          intent8.putExtra("tips_extras5", strArr[4]);
          intent8.putExtra("tips_extras6", "");
        } else if (strArr.length == 6) {
          intent8.putExtra("tips_extras4", strArr[2]);
          intent8.putExtra("tips_extras3", strArr[3]);
          intent8.putExtra("tips_extras5", strArr[4]);
          intent8.putExtra("tips_extras6", strArr[5]);
        }
        this.mContext.startForegroundServiceAsUser(intent8, UserHandle.CURRENT);
        return;
      }
      if ("showtip".equals(str)) {
        Intent intent9 = new Intent();
        intent9.setClassName(
            "com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
        intent9.putExtra("tips_extras", 9);
        intent9.putExtra("tips_id", strArr[1]);
        intent9.putExtra("tips_title", strArr[2]);
        intent9.putExtra("tips_text", strArr[3]);
        intent9.putExtra("tips_condition", Integer.parseInt(strArr[4]));
        this.mContext.startForegroundServiceAsUser(intent9, UserHandle.CURRENT);
        return;
      }
    }
    printWriter.print("\n\n");
    printWriter.println("[Galaxy Tips]");
    if (!this.mTipsPackageExist) {
      printWriter.println("Galaxy Tips is not exist this device");
      return;
    }
    printWriter.println("\n\n");
    printWriter.println("ETC Values");
    printWriter.println(
        "==========================================================================");
    printWriter.print("mTipsNetworkGranted = ");
    printWriter.println(this.mTipsNetworkGranted);
    printWriter.print("mTipsHUNAlreadyShown = ");
    printWriter.println(this.mTipsHUNAlreadyShown);
    printWriter.print("mDialCount = ");
    printWriter.println(this.mDialCount);
  }

  public static void sendMsg(Handler handler, int i, int i2, int i3, int i4, Object obj, int i5) {
    if (i2 == 0) {
      handler.removeMessages(i);
    } else if (i2 == 1 && handler.hasMessages(i)) {
      return;
    }
    synchronized (mLastDeviceConnectMsgTime) {
      handler.sendMessageAtTime(
          handler.obtainMessage(i, i3, i4, obj), SystemClock.uptimeMillis() + i5);
    }
  }

  public class TipsManagerServiceThread extends Thread {
    public TipsManagerServiceThread() {
      super("TipsManagerService");
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
      Looper.prepare();
      synchronized (TipsManagerService.this) {
        TipsManagerService.this.mTipsHandler = TipsManagerService.this.new TipsHandler();
        TipsManagerService.this.notify();
      }
      Looper.loop();
    }
  }

  public class TipsHandler extends Handler {
    public TipsHandler() {}

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      if (SemEmergencyManager.getInstance(TipsManagerService.this.mContext) != null
          ? SemEmergencyManager.isEmergencyMode(TipsManagerService.this.mContext)
          : false) {
        Log.d(
            TipsManagerService.TAG, "[GalaxyTips] Fail to send intent to Tips at emergency mode.");
        return;
      }
      int i = message.what;
      if (i == 10001) {
        if (TipsManagerService.this.mTipsNetworkGranted
            || TipsManagerService.this.mHUNDisplayCount >= 3) {
          return;
        }
        Intent intent = new Intent();
        intent.setClassName(
            "com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
        intent.putExtra("tips_extras", 2);
        try {
          TipsManagerService.this.mContext.startForegroundServiceAsUser(intent, UserHandle.CURRENT);
        } catch (Exception unused) {
          Log.d(
              TipsManagerService.TAG,
              "[GalaxyTips] Fail to send MSG_START_TIPS_HUN intent to Tips.");
        }
        TipsManagerService.this.mHUNDisplayCount++;
        TipsManagerService.sendMsg(
            TipsManagerService.this.mTipsHandler, 10001, 0, 0, 0, null, 604800000);
        return;
      }
      if (i != 10003) {
        if (i != 10004) {
          return;
        }
        Log.e(TipsManagerService.TAG, "[GalaxyTips] Send FOTA DONE.");
        Intent intent2 = new Intent();
        intent2.setClassName(
            "com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
        intent2.putExtra("tips_extras", 5);
        try {
          TipsManagerService.this.mContext.startForegroundServiceAsUser(
              intent2, UserHandle.CURRENT);
          return;
        } catch (Exception unused2) {
          Log.d(TipsManagerService.TAG, "[GalaxyTips] Fail to send FOTA DONE intent to Tips.");
          return;
        }
      }
      int intForUser =
          Settings.System.getIntForUser(
              TipsManagerService.this.mContext.getContentResolver(),
              "tips_regular_hour_timer_renewal_count",
              0,
              -2);
      if (intForUser != 0 && intForUser % 720 == 0) {
        Intent intent3 = new Intent();
        intent3.setClassName(
            "com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
        intent3.putExtra("tips_extras", 6);
        try {
          TipsManagerService.this.mContext.startForegroundServiceAsUser(
              intent3, UserHandle.CURRENT);
        } catch (Exception unused3) {
          Log.d(
              TipsManagerService.TAG,
              "[GalaxyTips] Fail to send MSG_CHECK_REGULAR_HOUR intent to Tips.");
        }
      }
      Settings.System.putIntForUser(
          TipsManagerService.this.mContext.getContentResolver(),
          "tips_regular_hour_timer_renewal_count",
          intForUser + 1,
          -2);
      TipsManagerService.sendMsg(
          TipsManagerService.this.mTipsHandler, 10003, 0, 0, 0, null, 3600000);
    }
  }
}
