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
import android.os.Looper;
import android.os.Message;
import android.os.RecoverySystem;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import com.samsung.android.isrb.IIsrbManager;

public final class IsrbManagerServiceImpl extends IIsrbManager.Stub {
    public boolean mBootComplete;
    public long mBuildtime;
    public Context mContext;
    public int mErrAlertNum;
    public ServiceHandler mHandler;
    public boolean mIsNetworkReady;
    public boolean mIsSystemErrPopup;
    public Looper mLooper;
    public int mPreviousTipsDate;
    public int mPreviousTipsMonth;
    public int mPreviousTipsTime;
    public int mPreviousTipsYear;
    public AnonymousClass1 mReceiver;
    public boolean mSystemReady;

    public final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v6, types: [android.content.BroadcastReceiver, com.android.server.isrb.IsrbManagerServiceImpl$1] */
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            final IsrbManagerServiceImpl isrbManagerServiceImpl = IsrbManagerServiceImpl.this;
            if (i == 0) {
                Log.d("IsrbManagerServiceImpl", "Fake time Check it in Handler thread");
                long j = SystemProperties.getLong("ro.build.date.utc", 1577808000L) * 1000;
                isrbManagerServiceImpl.mBuildtime = j;
                SystemClock.setCurrentTimeMillis(j);
                Settings.Global.putInt(isrbManagerServiceImpl.mContext.getContentResolver(), "auto_time", 0);
                Settings.Global.putInt(isrbManagerServiceImpl.mContext.getContentResolver(), "auto_time_zone", 0);
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
                isrbManagerServiceImpl.getClass();
                SystemProperties.set("persist.sys.enable_isrb", Boolean.toString(false));
                SystemProperties.set("persist.sys.rescue_level", String.valueOf(7));
                try {
                    RecoverySystem.rebootPromptAndWipeAppData(isrbManagerServiceImpl.mContext, "RescueParty");
                    return;
                } catch (Throwable unused) {
                    Log.d("IsrbManagerServiceImpl", "RecoverySystem Exception");
                    return;
                }
            }
            if (i == 3) {
                Log.d("IsrbManagerServiceImpl", "Timer is running, waiting for SetupwizardFinished.");
                if (Settings.Global.getInt(isrbManagerServiceImpl.mContext.getContentResolver(), "device_provisioned", 0) == 1 || Settings.Secure.getInt(isrbManagerServiceImpl.mContext.getContentResolver(), "user_setup_complete", 0) == 1) {
                    isrbManagerServiceImpl.mHandler.sendEmptyMessageDelayed(4, 15000L);
                    return;
                } else {
                    isrbManagerServiceImpl.mHandler.sendEmptyMessageDelayed(3, 30000L);
                    return;
                }
            }
            if (i != 4) {
                return;
            }
            Log.d("IsrbManagerServiceImpl", "SetupwizardFinished, show ISRB alert.");
            isrbManagerServiceImpl.showSystemErrDialog();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("com.samsung.isrb.SYSTEM_UPDATE");
            ?? r0 = new BroadcastReceiver() { // from class: com.android.server.isrb.IsrbManagerServiceImpl.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (!"android.intent.action.SCREEN_ON".equals(action) || IsrbManagerServiceImpl.this.mIsSystemErrPopup) {
                        if ("com.samsung.isrb.SYSTEM_UPDATE".equals(action)) {
                            Intent intent2 = new Intent("com.samsung.intent.action.LAUNCH_SOFTWARE_UPDATE_NON_SYSTEM");
                            intent2.setFlags(268435488);
                            IsrbManagerServiceImpl.this.mContext.sendBroadcast(intent2, "com.samsung.permission.LAUNCH_SOFTWARE_UPDATE");
                            Log.d("IsrbManagerServiceImpl", "Sent SW update broadcast to FOTA modul.");
                            return;
                        }
                        return;
                    }
                    if ((!SystemProperties.getBoolean("sys.isrb.networkcrash", false)) || IsrbManagerServiceImpl.this.mErrAlertNum < 12) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        int i2 = calendar.get(11);
                        if (calendar.get(1) == IsrbManagerServiceImpl.this.mPreviousTipsYear && calendar.get(2) == IsrbManagerServiceImpl.this.mPreviousTipsMonth) {
                            int i3 = calendar.get(5);
                            IsrbManagerServiceImpl isrbManagerServiceImpl2 = IsrbManagerServiceImpl.this;
                            if (i3 == isrbManagerServiceImpl2.mPreviousTipsDate) {
                                int i4 = isrbManagerServiceImpl2.mPreviousTipsTime;
                                if (i2 < i4 + 6 && i2 >= i4) {
                                    return;
                                }
                            }
                        }
                        IsrbManagerServiceImpl.this.showSystemErrDialog();
                    }
                }
            };
            isrbManagerServiceImpl.mReceiver = r0;
            isrbManagerServiceImpl.mContext.registerReceiver(r0, intentFilter);
        }
    }

    public final boolean isBootCompleteState() {
        return this.mBootComplete;
    }

    public final void setFakeTime() {
        Log.d("IsrbManagerServiceImpl", "Fake time Check in Hooker");
        this.mHandler.obtainMessage(0).sendToTarget();
    }

    public final void setIsrbEnable(boolean z) {
        SystemProperties.set("persist.sys.enable_isrb", Boolean.toString(z));
    }

    public final void showSystemErrDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        this.mIsSystemErrPopup = true;
        this.mIsNetworkReady = !SystemProperties.getBoolean("sys.isrb.networkcrash", false);
        int i = this.mErrAlertNum;
        if (i < 12) {
            this.mErrAlertNum = i + 1;
        }
        builder.setTitle(R.string.package_installed_device_owner);
        builder.setCancelable(false);
        if (this.mIsNetworkReady) {
            builder.setMessage(R.string.package_deleted_device_owner);
            if (this.mErrAlertNum < 12) {
                final int i2 = 1;
                builder.setNegativeButton(R.string.org_name, new DialogInterface.OnClickListener() { // from class: com.android.server.isrb.IsrbManagerServiceImpl.2
                    private final void onClick$com$android$server$isrb$IsrbManagerServiceImpl$2(DialogInterface dialogInterface, int i3) {
                    }

                    private final void onClick$com$android$server$isrb$IsrbManagerServiceImpl$3(DialogInterface dialogInterface, int i3) {
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i3) {
                        int i4 = i2;
                    }
                });
            }
            builder.setPositiveButton(R.string.other_networks_no_internet, new DialogInterface.OnClickListener() { // from class: com.android.server.isrb.IsrbManagerServiceImpl.4
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i3) {
                    IsrbManagerServiceImpl.this.mContext.sendBroadcast(new Intent("com.samsung.isrb.SYSTEM_UPDATE"));
                }
            });
        } else {
            builder.setMessage(R.string.owner_name);
            final int i3 = 0;
            builder.setPositiveButton(R.string.org_unit, new DialogInterface.OnClickListener() { // from class: com.android.server.isrb.IsrbManagerServiceImpl.2
                private final void onClick$com$android$server$isrb$IsrbManagerServiceImpl$2(DialogInterface dialogInterface, int i32) {
                }

                private final void onClick$com$android$server$isrb$IsrbManagerServiceImpl$3(DialogInterface dialogInterface, int i32) {
                }

                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i32) {
                    int i4 = i3;
                }
            });
        }
        AlertDialog create = builder.create();
        create.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.server.isrb.IsrbManagerServiceImpl.5
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                Log.d("IsrbManagerServiceImpl", "showSystemErrDialog() dismissed");
                IsrbManagerServiceImpl isrbManagerServiceImpl = IsrbManagerServiceImpl.this;
                isrbManagerServiceImpl.mIsSystemErrPopup = false;
                isrbManagerServiceImpl.getClass();
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                int i4 = calendar.get(11);
                isrbManagerServiceImpl.mPreviousTipsYear = calendar.get(1);
                isrbManagerServiceImpl.mPreviousTipsMonth = calendar.get(2);
                isrbManagerServiceImpl.mPreviousTipsDate = calendar.get(5);
                if (i4 >= 18) {
                    isrbManagerServiceImpl.mPreviousTipsTime = 18;
                    return;
                }
                if (i4 >= 12) {
                    isrbManagerServiceImpl.mPreviousTipsTime = 12;
                } else if (i4 >= 6) {
                    isrbManagerServiceImpl.mPreviousTipsTime = 6;
                } else {
                    isrbManagerServiceImpl.mPreviousTipsTime = 0;
                }
            }
        });
        create.getWindow().setType(2003);
        create.setCanceledOnTouchOutside(false);
        create.show();
    }
}
