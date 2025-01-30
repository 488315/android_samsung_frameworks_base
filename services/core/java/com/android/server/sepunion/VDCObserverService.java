package com.android.server.sepunion;

import android.app.ActivityManager;
import android.app.IForegroundServiceObserver;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
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
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserManager;
import android.util.Log;
import com.samsung.android.app.usage.IUsageStatsWatcher;
import com.samsung.android.sepunion.IVDCObserverService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public class VDCObserverService extends IVDCObserverService.Stub implements AbsSemSystemService {
  public static final Uri VDC_OBSERVER_URI =
      Uri.parse("content://com.samsung.android.vdc.observer");
  public Context mContext;
  public Handler mHandler;
  public List mRegisteredComponentList;
  public int mObserverRegisterState = 0;
  public final IForegroundServiceObserver mForegroundServiceObserver =
      new IForegroundServiceObserver
          .Stub() { // from class: com.android.server.sepunion.VDCObserverService.1
        public void onForegroundStateChanged(IBinder iBinder, String str, int i, boolean z) {
          Message obtain = Message.obtain();
          obtain.what = 10;
          Bundle bundle = new Bundle();
          bundle.putString("component_name", iBinder == null ? "null" : iBinder.toString());
          bundle.putString("pkg_name", str);
          bundle.putInt("user_id", i);
          bundle.putBoolean("is_foreground", z);
          obtain.setData(bundle);
          VDCObserverService.this.mHandler.sendMessage(obtain);
        }
      };
  public final IUsageStatsWatcher mUsageStatsWatcher =
      new IUsageStatsWatcher
          .Stub() { // from class: com.android.server.sepunion.VDCObserverService.3
        public void noteResumeComponent(ComponentName componentName, Intent intent, int i, int i2) {
          if (componentName != null) {
            VDCObserverService.this.handleUsageStatsChanged(1, i2, componentName);
          }
        }

        public void notePauseComponent(ComponentName componentName, Intent intent, int i, int i2) {
          if (componentName != null) {
            VDCObserverService.this.handleUsageStatsChanged(2, i2, componentName);
          }
        }

        public void noteStopComponent(ComponentName componentName, Intent intent, int i, int i2) {
          if (componentName != null) {
            VDCObserverService.this.handleUsageStatsChanged(23, i2, componentName);
          }
        }
      };

  public interface ObserverType {}

  public VDCObserverService(Context context) {
    this.mContext = context;
    HandlerThread handlerThread = new HandlerThread("VDCObserverService");
    handlerThread.start();
    this.mHandler = new MyHandler(handlerThread.getLooper());
  }

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void onCreate(Bundle bundle) {
    Log.d("VDCObserverService", "onCreate");
  }

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void onBootPhase(int i) {
    if (i == 1000) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.sepunion.VDCObserverService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              VDCObserverService.this.lambda$onBootPhase$0();
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onBootPhase$0() {
    Log.d("VDCObserverService", "init");
    registerUserUnlockedObserver();
    registerForegroundServiceObserver();
    registerVideoCallObserver();
  }

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    printWriter.println(
        "\n##### VDCObserverService #####\n##### (dumpsys sepunion VDCObserverService) #####\n");
    printWriter.println(
        "Observer register state: " + Integer.toBinaryString(this.mObserverRegisterState));
    StringBuilder sb = new StringBuilder();
    sb.append("componentList: ");
    List list = this.mRegisteredComponentList;
    sb.append(list == null ? "null" : list.toString());
    printWriter.println(sb.toString());
  }

  public final void registerForegroundServiceObserver() {
    try {
      Log.d("VDCObserverService", "registerForegroundServiceObserver");
      ActivityManager.getService()
          .registerForegroundServiceObserver(this.mForegroundServiceObserver);
      this.mObserverRegisterState |= 2;
    } catch (RemoteException | SecurityException e) {
      Log.e("VDCObserverService", "registerForegroundServiceObserver: failed ", e);
    }
  }

  /* renamed from: com.android.server.sepunion.VDCObserverService$2 */
  public class C25422 extends BroadcastReceiver {
    public C25422() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, final Intent intent) {
      VDCObserverService.this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.sepunion.VDCObserverService$2$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              VDCObserverService.C25422.this.lambda$onReceive$0(intent);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onReceive$0(Intent intent) {
      Log.d("VDCObserverService", "onReceive: " + intent.getAction());
      if (Objects.equals(intent.getAction(), "android.intent.action.USER_UNLOCKED")) {
        UserManager userManager =
            (UserManager) VDCObserverService.this.mContext.getSystemService("user");
        if (userManager != null && userManager.isUserUnlocked()) {
          VDCObserverService.this.registerUsageStatsWatcher();
        } else {
          Log.w("VDCObserverService", "registerUsageStatsWatcher: failed");
        }
      }
    }
  }

  public final void registerUserUnlockedObserver() {
    Log.d("VDCObserverService", "registerUserUnlockedObserver");
    this.mContext.registerReceiver(
        new C25422(), new IntentFilter("android.intent.action.USER_UNLOCKED"));
    this.mObserverRegisterState |= 1;
  }

  public final void registerUsageStatsWatcher() {
    List componentList = getComponentList();
    if (componentList == null || componentList.isEmpty()) {
      Log.w("VDCObserverService", "registerUsageStatsWatcher: fail");
      return;
    }
    Log.d("VDCObserverService", "registerUsageStatsWatcher: " + componentList);
    UsageStatsManager usageStatsManager =
        (UsageStatsManager) this.mContext.getSystemService("usagestats");
    if (usageStatsManager != null) {
      usageStatsManager.registerUsageStatsWatcher(this.mUsageStatsWatcher, componentList);
      this.mObserverRegisterState |= 8;
      this.mRegisteredComponentList = componentList;
      return;
    }
    Log.e("VDCObserverService", "get UsageStatsManager null");
  }

  public final void unregisterUsageStatsWatcher() {
    Log.d("VDCObserverService", "unregisterUsageStatsWatcher");
    UsageStatsManager usageStatsManager =
        (UsageStatsManager) this.mContext.getSystemService("usagestats");
    if (usageStatsManager != null) {
      usageStatsManager.unregisterUsageStatsWatcher(this.mUsageStatsWatcher);
      this.mObserverRegisterState &= -9;
    } else {
      Log.e("VDCObserverService", "get UsageStatsManager null");
    }
  }

  public final void registerVideoCallObserver() {
    ContentObserver contentObserver =
        new ContentObserver(
            this.mHandler) { // from class: com.android.server.sepunion.VDCObserverService.4
          @Override // android.database.ContentObserver
          public void onChange(boolean z, Uri uri) {
            if (uri == null) {
              return;
            }
            VDCObserverService.this.mHandler.removeMessages(30);
            VDCObserverService.this.mHandler.sendEmptyMessage(30);
          }
        };
    ContentResolver contentResolver = this.mContext.getContentResolver();
    if (contentResolver != null) {
      contentResolver.registerContentObserver(VDC_OBSERVER_URI, true, contentObserver);
      this.mObserverRegisterState |= 4;
    } else {
      Log.e("VDCObserverService", "get ContentResolver null");
    }
  }

  public final void handleUsageStatsChanged(int i, int i2, ComponentName componentName) {
    Message obtain = Message.obtain();
    obtain.what = 20;
    Bundle bundle = new Bundle();
    bundle.putInt("event_type", i);
    bundle.putInt("user_id", i2);
    bundle.putString("pkg_name", componentName.getPackageName());
    bundle.putString("class_name", componentName.getClassName());
    obtain.setData(bundle);
    this.mHandler.sendMessage(obtain);
  }

  public class MyHandler extends Handler {
    public MyHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      Log.d("VDCObserverService", "VDC thread msg " + message.what);
      int i = message.what;
      if (i == 10) {
        VDCObserverService.this.onFgServiceStateChanged(message.getData());
        return;
      }
      if (i == 20) {
        VDCObserverService.this.onUsageStatsChanged(message.getData());
      } else {
        if (i != 30) {
          return;
        }
        VDCObserverService.this.unregisterUsageStatsWatcher();
        VDCObserverService.this.registerUsageStatsWatcher();
      }
    }
  }

  public final void onFgServiceStateChanged(Bundle bundle) {
    try {
      this.mContext
          .getContentResolver()
          .call(VDC_OBSERVER_URI, "onForegroundServiceStateChanged", (String) null, bundle);
    } catch (IllegalArgumentException e) {
      Log.e("VDCObserverService", "onFgServiceStateChanged call failed: ", e);
    }
  }

  public final void onUsageStatsChanged(Bundle bundle) {
    try {
      Log.d("VDCObserverService", "onUsageStatsChanged");
      this.mContext
          .getContentResolver()
          .call(VDC_OBSERVER_URI, "onUsageStatsChanged", (String) null, bundle);
    } catch (IllegalArgumentException e) {
      Log.e("VDCObserverService", "onUsageStatsChanged call failed: ", e);
    }
  }

  public final List getComponentList() {
    ArrayList arrayList = new ArrayList();
    ContentResolver contentResolver = this.mContext.getContentResolver();
    if (contentResolver == null) {
      Log.e("VDCObserverService", "get ContentResolver null");
      return null;
    }
    try {
      Cursor query =
          contentResolver.query(
              Uri.withAppendedPath(VDC_OBSERVER_URI, "video_call_app_info"), null, null, null);
      if (query != null) {
        try {
          if (query.getCount() > 0) {
            Log.d("VDCObserverService", "contentResolver query: " + query.getCount());
            int columnIndex = query.getColumnIndex("package_name");
            int columnIndex2 = query.getColumnIndex("activities_name");
            if (columnIndex >= 0 && columnIndex2 >= 0) {
              while (query.moveToNext()) {
                String string = query.getString(columnIndex);
                String string2 = query.getString(columnIndex2);
                if (string2 != null && !string2.isEmpty() && !string2.equals("null")) {
                  for (String str :
                      string2.substring(1, string2.length() - 1).replace("\"", "").split(",")) {
                    if (str != null && !str.isEmpty()) {
                      arrayList.add(new ComponentName(string, str));
                    }
                  }
                }
              }
            }
            query.close();
            return null;
          }
        } catch (Throwable th) {
          try {
            query.close();
          } catch (Throwable th2) {
            th.addSuppressed(th2);
          }
          throw th;
        }
      }
      if (query != null) {
        query.close();
      }
    } catch (SQLiteException | IllegalArgumentException e) {
      Log.e("VDCObserverService", "getComponentList() failed: ", e);
    }
    return arrayList;
  }
}
