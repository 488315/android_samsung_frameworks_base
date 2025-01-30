package com.android.server.sepunion;

import android.app.ActivityManager;
import android.app.IProcessObserver;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import com.samsung.android.app.usage.IUsageStatsWatcher;
import com.samsung.android.sepunion.IPaymentSafetyService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class PaymentSafetyService extends IPaymentSafetyService.Stub
    implements AbsSemSystemService {
  public Context mContext;
  public Handler mHandler;
  public static final String TAG = PaymentSafetyService.class.getSimpleName();
  public static final Uri PAYMENT_APP_URI =
      Uri.parse("content://com.samsung.android.sm/ProtectedApps");
  public static final Uri PAYMENT_APP_CHECK_URI =
      Uri.parse("content://com.samsung.android.sm.payment");
  public static final Uri IMPORT_COMPONENT_LIST_URI =
      Uri.parse("content://com.samsung.android.sm.payment/importantComponentList");
  public static final String[] PROJECTION = {"package_name"};
  public static final String[] ARGS = {"1"};
  public HashSet mProtectedAppSet = new HashSet();
  public ConcurrentHashMap mCheckedAppMap = new ConcurrentHashMap();
  public ConcurrentHashMap mForegroundActivitiesPidMap = new ConcurrentHashMap();
  public volatile boolean mProtectedAppLoaded = false;
  public ConcurrentHashMap mImportantAppLastCheckTimeMap = new ConcurrentHashMap();
  public HashSet mImportantAppSet = new HashSet();
  public ArrayList mImportantComponentList = new ArrayList();
  public BroadcastReceiver mUserActionReceiver =
      new BroadcastReceiver() { // from class: com.android.server.sepunion.PaymentSafetyService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          int intExtra;
          String action = intent.getAction();
          if ("android.intent.action.USER_UNLOCKED".equals(action)) {
            Log.i(PaymentSafetyService.TAG, "onUserUnlocked");
            PaymentSafetyService.this.mHandler.sendEmptyMessage(70);
            return;
          }
          if ("com.samsung.knox.securefolder.SETUP_COMPLETE".equals(action)) {
            intExtra = intent.getIntExtra("userid", -1);
          } else {
            intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
          }
          Message obtain = Message.obtain();
          obtain.what = 60;
          Bundle bundle = new Bundle();
          bundle.putString("action", action);
          bundle.putInt("userid", intExtra);
          obtain.setData(bundle);
          PaymentSafetyService.this.mHandler.sendMessage(obtain);
        }
      };
  public BroadcastReceiver mPkgChangedIntentReceiver =
      new BroadcastReceiver() { // from class: com.android.server.sepunion.PaymentSafetyService.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          String action = intent.getAction();
          if ("android.intent.action.PACKAGE_ADDED".equals(action)
              || "android.intent.action.PACKAGE_REMOVED".equals(action)) {
            int intExtra = intent.getIntExtra("android.intent.extra.UID", 0);
            boolean booleanExtra = intent.getBooleanExtra("android.intent.extra.REPLACING", false);
            Uri data = intent.getData();
            if (booleanExtra || data == null) {
              return;
            }
            Message obtain = Message.obtain();
            obtain.what = 50;
            Bundle bundle = new Bundle();
            bundle.putString("action", action);
            bundle.putInt("uid", intExtra);
            bundle.putString("pkg_name", data.getSchemeSpecificPart());
            obtain.setData(bundle);
            PaymentSafetyService.this.mHandler.sendMessage(obtain);
          }
        }
      };
  public IUsageStatsWatcher.Stub mUsageStatusWatcher =
      new IUsageStatsWatcher
          .Stub() { // from class: com.android.server.sepunion.PaymentSafetyService.3
        public void notePauseComponent(ComponentName componentName, Intent intent, int i, int i2) {}

        public void noteStopComponent(ComponentName componentName, Intent intent, int i, int i2) {}

        public void noteResumeComponent(ComponentName componentName, Intent intent, int i, int i2) {
          if (componentName != null) {
            PaymentSafetyService.this.checkImportantApp(
                componentName.getPackageName(), componentName.getClassName());
          }
        }
      };

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void onCreate(Bundle bundle) {}

  public PaymentSafetyService(Context context) {
    this.mContext = context;
    HandlerThread handlerThread = new HandlerThread("PaymentSafetyPolicy");
    handlerThread.start();
    this.mHandler = new BgWorkerHandler(handlerThread.getLooper());
  }

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void onBootPhase(int i) {
    if (i == 1000) {
      Log.i(TAG, "onBootPhase");
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.sepunion.PaymentSafetyService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              PaymentSafetyService.this.lambda$onBootPhase$0();
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onBootPhase$0() {
    registerReceiver();
    registerRunningProcessObserver();
  }

  public final void registerPretectedAppDBObserver() {
    try {
      this.mContext
          .getContentResolver()
          .registerContentObserver(
              PAYMENT_APP_URI, true, new ProtectedAppDBObserver(this.mHandler));
    } catch (Exception e) {
      Log.e(TAG, "registerObserver cause exception", e);
    }
  }

  public class BgWorkerHandler extends Handler {
    public BgWorkerHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      Log.i(PaymentSafetyService.TAG, "Payment thread msg " + message.what);
      int i = message.what;
      if (i == 10) {
        PaymentSafetyService.this.loadProctedAppSet();
        PaymentSafetyService.this.loadImportantAppComponentList();
        PaymentSafetyService.this.registerWatcherForImportantComponentList();
        return;
      }
      if (i == 20) {
        PaymentSafetyService.this.checkPaymentApp(message.getData());
        return;
      }
      if (i == 40) {
        PaymentSafetyService.this.checkImportantApp(message.getData());
        return;
      }
      if (i == 50) {
        PaymentSafetyService.this.onPkgChanged(message.getData());
      } else if (i == 60) {
        PaymentSafetyService.this.onUserAction(message.getData());
      } else {
        if (i != 70) {
          return;
        }
        PaymentSafetyService.this.onUserUnlocked();
      }
    }
  }

  public class ProtectedAppDBObserver extends ContentObserver {
    public ProtectedAppDBObserver(Handler handler) {
      super(handler);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri) {
      if (uri == null) {
        return;
      }
      PaymentSafetyService.this.mHandler.removeMessages(10);
      PaymentSafetyService.this.mHandler.sendEmptyMessage(10);
    }
  }

  public final void onUserAction(Bundle bundle) {
    try {
      this.mContext
          .getContentResolver()
          .call(PAYMENT_APP_CHECK_URI, "onUserAction", (String) null, bundle);
    } catch (SQLiteException | IllegalArgumentException e) {
      Log.e(TAG, "SmartManager app doesn't support payment policy, please check", e);
    }
  }

  public final void onPkgChanged(Bundle bundle) {
    try {
      this.mContext
          .getContentResolver()
          .call(PAYMENT_APP_CHECK_URI, "onPkgChanged", (String) null, bundle);
    } catch (SQLiteException | IllegalArgumentException e) {
      Log.e(TAG, "SmartManager app doesn't support payment policy, please check", e);
    }
  }

  public final void registerReceiver() {
    try {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
      intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
      intentFilter.addDataScheme("package");
      this.mContext.registerReceiverAsUser(
          this.mPkgChangedIntentReceiver, UserHandle.ALL, intentFilter, null, this.mHandler);
      IntentFilter intentFilter2 = new IntentFilter();
      intentFilter2.addAction("android.intent.action.USER_ADDED");
      intentFilter2.addAction("com.samsung.knox.securefolder.SETUP_COMPLETE");
      intentFilter2.addAction("android.intent.action.USER_STOPPED");
      intentFilter2.addAction("android.intent.action.USER_UNLOCKED");
      this.mContext.registerReceiver(this.mUserActionReceiver, intentFilter2, null, this.mHandler);
    } catch (NullPointerException e) {
      Log.e(TAG, "registerReceiver cause exception", e);
    }
  }

  public final void onUserUnlocked() {
    registerPretectedAppDBObserver();
    loadProctedAppSet();
    loadImportantAppComponentList();
    registerWatcherForImportantComponentList();
  }

  public final void loadImportantAppComponentList() {
    this.mImportantAppSet.clear();
    this.mImportantComponentList.clear();
    try {
      Cursor query =
          this.mContext
              .getContentResolver()
              .query(IMPORT_COMPONENT_LIST_URI, null, null, null, null);
      if (query != null) {
        try {
          if (query.moveToFirst()) {
            do {
              String string = query.getString(0);
              this.mImportantAppSet.add(string);
              this.mImportantComponentList.add(new ComponentName(string, query.getString(1)));
            } while (query.moveToNext());
          }
        } finally {
        }
      }
      if (query != null) {
        query.close();
      }
    } catch (Exception e) {
      Log.e(TAG, "getImportantComponentList", e);
    }
  }

  public final void registerWatcherForImportantComponentList() {
    try {
      UsageStatsManager usageStatsManager =
          (UsageStatsManager) this.mContext.getSystemService("usagestats");
      usageStatsManager.unregisterUsageStatsWatcher(this.mUsageStatusWatcher);
      if (this.mImportantComponentList.isEmpty()) {
        Log.e(TAG, "IMPORTANT_COMPONENT_LIST is empty");
      } else {
        usageStatsManager.registerUsageStatsWatcher(
            this.mUsageStatusWatcher, this.mImportantComponentList);
      }
    } catch (Exception e) {
      Log.e(TAG, "registerWatcherForImportantComponents cause exception", e);
    }
  }

  public final void checkImportantApp(String str, String str2) {
    this.mHandler.removeMessages(40);
    Message obtain = Message.obtain();
    obtain.what = 40;
    Bundle bundle = new Bundle();
    bundle.putString("pkg_name", str);
    bundle.putString("class_name", str2);
    obtain.setData(bundle);
    this.mHandler.sendMessage(obtain);
  }

  public final boolean isProtectedApp(String str) {
    if (!this.mProtectedAppLoaded) {
      loadProctedAppSet();
    }
    synchronized (this.mProtectedAppSet) {
      if (this.mProtectedAppSet.contains(str)) {
        return true;
      }
      Log.d(TAG, str + " not target");
      return false;
    }
  }

  public final void checkImportantApp(Bundle bundle) {
    try {
      String string = bundle.getString("pkg_name");
      long longValue =
          ((Long) this.mImportantAppLastCheckTimeMap.getOrDefault(string, -1L)).longValue();
      long currentTimeMillis = System.currentTimeMillis();
      if (Math.abs(longValue - currentTimeMillis) < 3000) {
        Log.e(TAG, "avoid repeat check in 3 seconds");
        return;
      }
      this.mImportantAppLastCheckTimeMap.put(string, Long.valueOf(currentTimeMillis));
      if (isProtectedApp(string)) {
        this.mContext
            .getContentResolver()
            .call(PAYMENT_APP_CHECK_URI, "startCheck", (String) null, bundle);
      }
    } catch (SQLiteException | IllegalArgumentException e) {
      Log.e(TAG, "SmartManager app doesn't support payment policy, please check", e);
    }
  }

  public final void loadProctedAppSet() {
    synchronized (this.mProtectedAppSet) {
      this.mProtectedAppSet.clear();
      try {
        Cursor query =
            this.mContext
                .getContentResolver()
                .query(PAYMENT_APP_URI, PROJECTION, "mode=?", ARGS, null);
        try {
          this.mProtectedAppLoaded = true;
          if (query != null && !query.isClosed() && query.getCount() > 0) {
            while (query.moveToNext()) {
              Log.d(TAG, query.getString(0));
              this.mProtectedAppSet.add(query.getString(0));
            }
          } else {
            Log.i(TAG, "Protected App is empty");
          }
          if (query != null) {
            query.close();
          }
        } catch (Throwable th) {
          if (query != null) {
            try {
              query.close();
            } catch (Throwable th2) {
              th.addSuppressed(th2);
            }
          }
          throw th;
        }
      } catch (SQLiteException | IllegalArgumentException | NullPointerException e) {
        Log.e(TAG, "SmartManager app doesn't support payment app list, please check", e);
      }
    }
  }

  public final void registerRunningProcessObserver() {
    try {
      ActivityManager.getService().registerProcessObserver(new RunningProcessObserver());
    } catch (RemoteException e) {
      Log.e(TAG, "registerRunningProcessObserver cause exception", e);
    }
  }

  public class RunningProcessObserver extends IProcessObserver.Stub {
    public void onForegroundServicesChanged(int i, int i2, int i3) {}

    public RunningProcessObserver() {}

    public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
      if (z) {
        if (((Boolean)
                PaymentSafetyService.this.mCheckedAppMap.getOrDefault(
                    Integer.valueOf(i2), Boolean.FALSE))
            .booleanValue()) {
          Log.d(PaymentSafetyService.TAG, "already checked " + i2);
          return;
        }
        ConcurrentHashMap concurrentHashMap = PaymentSafetyService.this.mForegroundActivitiesPidMap;
        Integer valueOf = Integer.valueOf(i);
        Boolean bool = Boolean.TRUE;
        concurrentHashMap.put(valueOf, bool);
        PaymentSafetyService.this.mCheckedAppMap.put(Integer.valueOf(i2), bool);
        PaymentSafetyService.this.checkPaymentApp(i, i2);
      }
    }

    public void onProcessDied(int i, int i2) {
      if (((Boolean)
              PaymentSafetyService.this.mForegroundActivitiesPidMap.getOrDefault(
                  Integer.valueOf(i), Boolean.FALSE))
          .booleanValue()) {
        PaymentSafetyService.this.mForegroundActivitiesPidMap.remove(Integer.valueOf(i));
        PaymentSafetyService.this.mCheckedAppMap.remove(Integer.valueOf(i2));
      }
    }
  }

  public final void checkPaymentApp(int i, int i2) {
    this.mHandler.removeMessages(20);
    Message obtain = Message.obtain();
    obtain.what = 20;
    Bundle bundle = new Bundle();
    bundle.putInt("pid", i);
    bundle.putInt("uid", i2);
    obtain.setData(bundle);
    this.mHandler.sendMessage(obtain);
  }

  public final void checkPaymentApp(Bundle bundle) {
    if (bundle == null) {
      return;
    }
    int i = bundle.getInt("pid", 0);
    ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
    if (activityManager == null) {
      return;
    }
    String packageFromAppProcesses = activityManager.getPackageFromAppProcesses(i);
    if (isProtectedApp(packageFromAppProcesses)
        && !this.mImportantAppSet.contains(packageFromAppProcesses)) {
      bundle.putString("pkg_name", packageFromAppProcesses);
      try {
        this.mContext
            .getContentResolver()
            .call(PAYMENT_APP_CHECK_URI, "startCheck", (String) null, bundle);
      } catch (SQLiteException | IllegalArgumentException e) {
        Log.e(TAG, "SmartManager app doesn't support payment policy, please check", e);
      }
    }
  }

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void dump(FileDescriptor fileDescriptor, final PrintWriter printWriter, String[] strArr) {
    printWriter.println("##### PaymentSafetyService  #####");
    printWriter.println(" Current Payment App:");
    synchronized (this.mProtectedAppSet) {
      this.mProtectedAppSet.forEach(
          new Consumer() { // from class:
                           // com.android.server.sepunion.PaymentSafetyService$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              printWriter.println((String) obj);
            }
          });
    }
  }
}
