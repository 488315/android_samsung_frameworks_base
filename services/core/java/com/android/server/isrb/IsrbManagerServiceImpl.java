package com.android.server.isrb;

import android.R;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.util.Calendar;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RecoverySystem;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import com.samsung.android.isrb.IIsrbManager;

/* loaded from: classes2.dex */
public class IsrbManagerServiceImpl extends IIsrbManager.Stub {
  public long mBuildtime;
  public Context mContext;
  public ServiceHandler mHandler;
  public Looper mLooper;
  public BroadcastReceiver mReceiver;
  public boolean mSystemReady = false;
  public boolean mBootComplete = false;
  public boolean mShowUI = false;
  public boolean mIsSystemErrPopup = false;
  public boolean mIsNetworkReady = false;
  public int mErrAlertNum = 0;
  public int mPreviousTipsTime = 0;
  public int mPreviousTipsDate = 0;
  public int mPreviousTipsMonth = 0;
  public int mPreviousTipsYear = 0;

  public IsrbManagerServiceImpl(Context context) {
    this.mContext = context;
  }

  public final class ServiceHandler extends Handler {
    public ServiceHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      int i = message.what;
      if (i == 0) {
        Log.d("IsrbManagerServiceImpl", "Fake time Check it in Handler thread");
        IsrbManagerServiceImpl.this.mBuildtime =
            SystemProperties.getLong("ro.build.date.utc", 1577808000L) * 1000;
        SystemClock.setCurrentTimeMillis(IsrbManagerServiceImpl.this.mBuildtime);
        Settings.Global.putInt(
            IsrbManagerServiceImpl.this.mContext.getContentResolver(), "auto_time", 0);
        Settings.Global.putInt(
            IsrbManagerServiceImpl.this.mContext.getContentResolver(), "auto_time_zone", 0);
        return;
      }
      if (i == 1) {
        Log.d("IsrbManagerServiceImpl", "Set ISRB Disabled");
        if (SystemProperties.getBoolean("sys.isrblevel.callreboot", false)) {
          return;
        }
        SystemProperties.set("persist.sys.enable_isrb", Boolean.toString(false));
        return;
      }
      if (i == 2) {
        Log.d("IsrbManagerServiceImpl", "Set ISRB Disabled");
        IsrbManagerServiceImpl.this.rebootExitisrbInternal();
        return;
      }
      if (i != 3) {
        if (i != 4) {
          return;
        }
        Log.d("IsrbManagerServiceImpl", "SetupwizardFinished, show ISRB alert.");
        IsrbManagerServiceImpl.this.showSystemErrDialog();
        IsrbManagerServiceImpl.this.initBroadcastReceiver();
        return;
      }
      Log.d("IsrbManagerServiceImpl", "Timer is running, waiting for SetupwizardFinished.");
      if (IsrbManagerServiceImpl.this.isSetupwizardFinished()) {
        IsrbManagerServiceImpl.this.mHandler.sendEmptyMessageDelayed(4, 15000L);
      } else {
        IsrbManagerServiceImpl.this.mHandler.sendEmptyMessageDelayed(3, 30000L);
      }
    }
  }

  public void initBroadcastReceiver() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.SCREEN_ON");
    intentFilter.addAction("com.samsung.isrb.SYSTEM_UPDATE");
    BroadcastReceiver broadcastReceiver =
        new BroadcastReceiver() { // from class: com.android.server.isrb.IsrbManagerServiceImpl.1
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.SCREEN_ON".equals(action)
                && !IsrbManagerServiceImpl.this.mIsSystemErrPopup) {
              if (IsrbManagerServiceImpl.this.isNetworkReady()
                  || IsrbManagerServiceImpl.this.mErrAlertNum < 12) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                int i = calendar.get(11);
                if (calendar.get(1) == IsrbManagerServiceImpl.this.mPreviousTipsYear
                    && calendar.get(2) == IsrbManagerServiceImpl.this.mPreviousTipsMonth
                    && calendar.get(5) == IsrbManagerServiceImpl.this.mPreviousTipsDate
                    && i < IsrbManagerServiceImpl.this.mPreviousTipsTime + 6
                    && i >= IsrbManagerServiceImpl.this.mPreviousTipsTime) {
                  return;
                }
                IsrbManagerServiceImpl.this.showSystemErrDialog();
                return;
              }
              return;
            }
            if ("com.samsung.isrb.SYSTEM_UPDATE".equals(action)) {
              Intent intent2 =
                  new Intent("com.samsung.intent.action.LAUNCH_SOFTWARE_UPDATE_NON_SYSTEM");
              intent2.setFlags(268435488);
              IsrbManagerServiceImpl.this.mContext.sendBroadcast(
                  intent2, "com.samsung.permission.LAUNCH_SOFTWARE_UPDATE");
              Log.d("IsrbManagerServiceImpl", "Sent SW update broadcast to FOTA modul.");
            }
          }
        };
    this.mReceiver = broadcastReceiver;
    this.mContext.registerReceiver(broadcastReceiver, intentFilter);
  }

  public void setTipsTime() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    int i = calendar.get(11);
    this.mPreviousTipsYear = calendar.get(1);
    this.mPreviousTipsMonth = calendar.get(2);
    this.mPreviousTipsDate = calendar.get(5);
    if (i >= 18) {
      this.mPreviousTipsTime = 18;
      return;
    }
    if (i >= 12) {
      this.mPreviousTipsTime = 12;
    } else if (i >= 6) {
      this.mPreviousTipsTime = 6;
    } else {
      this.mPreviousTipsTime = 0;
    }
  }

  public boolean isNetworkReady() {
    return !SystemProperties.getBoolean("sys.isrb.networkcrash", false);
  }

  public boolean isSetupwizardFinished() {
    return Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) == 1
        || Settings.Secure.getInt(this.mContext.getContentResolver(), "user_setup_complete", 0)
            == 1;
  }

  public void showSystemErrDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
    this.mIsSystemErrPopup = true;
    this.mIsNetworkReady = isNetworkReady();
    int i = this.mErrAlertNum;
    if (i < 12) {
      this.mErrAlertNum = i + 1;
    }
    builder.setTitle(R.string.month_day_year);
    builder.setCancelable(false);
    if (!this.mIsNetworkReady) {
      builder.setMessage(R.string.mobile_provisioning_apn);
      builder.setPositiveButton(
          R.string.mmiFdnError,
          new DialogInterface
              .OnClickListener() { // from class: com.android.server.isrb.IsrbManagerServiceImpl.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {}
          });
    } else {
      builder.setMessage(R.string.mobile_provisioning_url);
      if (this.mErrAlertNum < 12) {
        builder.setNegativeButton(
            R.string.mmiErrorWhileRoaming,
            new DialogInterface
                .OnClickListener() { // from class: com.android.server.isrb.IsrbManagerServiceImpl.3
              @Override // android.content.DialogInterface.OnClickListener
              public void onClick(DialogInterface dialogInterface, int i2) {}
            });
      }
      builder.setPositiveButton(
          R.string.mobile_no_internet,
          new DialogInterface
              .OnClickListener() { // from class: com.android.server.isrb.IsrbManagerServiceImpl.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
              IsrbManagerServiceImpl.this.mContext.sendBroadcast(
                  new Intent("com.samsung.isrb.SYSTEM_UPDATE"));
            }
          });
    }
    AlertDialog create = builder.create();
    create.setOnDismissListener(
        new DialogInterface
            .OnDismissListener() { // from class: com.android.server.isrb.IsrbManagerServiceImpl.5
          @Override // android.content.DialogInterface.OnDismissListener
          public void onDismiss(DialogInterface dialogInterface) {
            Log.d("IsrbManagerServiceImpl", "showSystemErrDialog() dismissed");
            IsrbManagerServiceImpl.this.mIsSystemErrPopup = false;
            IsrbManagerServiceImpl.this.setTipsTime();
          }
        });
    create.getWindow().setType(2003);
    create.setCanceledOnTouchOutside(false);
    create.show();
  }

  public void systemRunning() {
    if (!this.mSystemReady) {
      this.mSystemReady = true;
    }
    HandlerThread handlerThread = new HandlerThread("MessageISRBThread", 10);
    handlerThread.start();
    this.mLooper = handlerThread.getLooper();
    this.mHandler = new ServiceHandler(this.mLooper);
  }

  public void systemBootComplete() {
    if (!this.mBootComplete) {
      this.mBootComplete = true;
    }
    if (SystemProperties.getBoolean("persist.sys.enable_isrb", false)) {
      this.mHandler.sendEmptyMessageDelayed(3, 500L);
      Log.d("IsrbManagerServiceImpl", "PROP_ENABLE_ISRB:disable");
      ServiceHandler serviceHandler = this.mHandler;
      serviceHandler.sendMessageDelayed(serviceHandler.obtainMessage(1), 45000L);
    }
  }

  public boolean isBootCompleteState() {
    return this.mBootComplete;
  }

  public void setIsrbEnable(boolean z) {
    SystemProperties.set("persist.sys.enable_isrb", Boolean.toString(z));
  }

  public void setFakeTime() {
    Log.d("IsrbManagerServiceImpl", "Fake time Check in Hooker");
    this.mHandler.obtainMessage(0).sendToTarget();
  }

  public void rebootExitisrbInternal() {
    SystemProperties.set("persist.sys.enable_isrb", Boolean.toString(false));
    SystemProperties.set("persist.sys.rescue_level", String.valueOf(7));
    try {
      RecoverySystem.rebootPromptAndWipeAppData(this.mContext, "RescueParty");
    } catch (Throwable unused) {
      Log.d("IsrbManagerServiceImpl", "RecoverySystem Exception");
    }
  }
}
