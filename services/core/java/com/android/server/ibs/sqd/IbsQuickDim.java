package com.android.server.ibs.sqd;

import android.app.ActivityManagerNative;
import android.app.IProcessObserver;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import android.view.IWindowManager;
import android.view.MotionEvent;
import android.view.WindowManagerPolicyConstants;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.LocalServices;
import com.android.server.display.DisplayPowerController2;
import com.android.server.ibs.sleepmode.SharePrefUtils;
import com.android.server.wm.WindowManagerService;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class IbsQuickDim {
  public static HandlerThread mHandlerThread = new HandlerThread("IbsQuickDim", 1);
  public Context mContext;
  public IntentFilter mFilter;
  public IntentFilter mPkgFilter;
  public PowerManagerInternal mPowerManagerInternal;
  public ContentResolver mResolver;
  public long mScreenOffTimeoutSetting;
  public boolean mSmartStayEnabledSetting;
  public IntentReceiver receiver;
  public final boolean DEBUG = "eng".equals(Build.TYPE);
  public final Object mLock = new Object();
  public float mSQDPowerSave = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  public int mBrightness = 0;
  public List mBlockUnDimUidList = null;
  public IBinder mSFSevices = null;
  public Map SQDGainMap = new HashMap() { // from class: com.android.server.ibs.sqd.IbsQuickDim.1
        {
          put(41, 0);
          put(60, 10);
          put(80, 19);
          put(100, 27);
          put(130, 54);
          put(150, 71);
          put(180, 85);
          put(205, 109);
          put(230, 122);
          put(
              Integer.valueOf(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT),
              Integer.valueOf(FrameworkStatsLog.f680x89db317));
        }
      };
  public boolean mCharging = false;
  public long mChargingFinishTime = 0;
  public long mDimStartTime = 0;
  public WindowManagerService mWMS = null;
  public SQLiteSQDwhilteList mSQLiteSQDwhilteList = null;
  public SettingsObserver mSettingsObserver = null;
  public long mLastPagecount = 0;
  public long mLastTime = 0;
  public boolean mUiControlEnabled = false;
  public int mQuickDimMode = 2;
  public qkDimHandler mQkDimHandler = null;
  public final WindowManagerPolicyConstants.PointerEventListener mPointerEventListener =
      new WindowManagerPolicyConstants
          .PointerEventListener() { // from class: com.android.server.ibs.sqd.IbsQuickDim.2
        public void onPointerEvent(MotionEvent motionEvent) {
          if (motionEvent.getAction() == 0 && IbsQuickDim.this.mQuickDimMode == 2) {
            IbsQuickDim.this.mQkDimHandler.sendEmptyMessage(3);
          }
        }
      };
  public int mLastFPS = -1;
  public boolean mScreenOn = false;
  public boolean mQuickdimEnable = true;
  public final IProcessObserver mProcessObserver =
      new IProcessObserver.Stub() { // from class: com.android.server.ibs.sqd.IbsQuickDim.3
        public void onForegroundServicesChanged(int i, int i2, int i3) {}

        public void onProcessDied(int i, int i2) {}

        public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
          if (IbsQuickDim.this.mQuickdimEnable
              && IbsQuickDim.this.mScreenOn
              && IbsQuickDim.this.mUiControlEnabled
              && !IbsQuickDim.this.mCharging) {
            synchronized (IbsQuickDim.this.mLock) {
              if (IbsQuickDim.this.mQuickDimMode == 1) {
                return;
              }
              if (z) {
                if (IbsQuickDim.this.mBlockUnDimUidList.contains(Integer.valueOf(i2))) {
                  if (IbsQuickDim.this.mQuickDimMode != 3) {
                    IbsQuickDim.this.mQuickDimMode = 3;
                    if (IbsQuickDim.this.DEBUG) {
                      Slog.d(
                          "IbsQuickDim",
                          " stop dim detect because fg click mQuickDimMode = "
                              + IbsQuickDim.this.mQuickDimMode);
                    }
                    IbsQuickDim.this.mQkDimHandler.sendEmptyMessage(4);
                  }
                } else if (IbsQuickDim.this.mQuickDimMode == 3
                    || IbsQuickDim.this.mQuickDimMode == 4) {
                  IbsQuickDim.this.mQuickDimMode = 2;
                  IbsQuickDim.this.mQkDimHandler.sendEmptyMessage(1);
                }
              }
            }
          }
        }
      };

  public IbsQuickDim(Context context) {
    this.mContext = context;
  }

  public void init() {
    mHandlerThread.start();
    this.mQkDimHandler = new qkDimHandler(mHandlerThread.getLooper());
    this.mResolver = this.mContext.getContentResolver();
    try {
      WindowManagerService asInterface =
          IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
      this.mWMS = asInterface;
      asInterface.registerPointerEventListener(this.mPointerEventListener, 0);
    } catch (Exception e) {
      Log.e("IbsQuickDim", "Exception - registerPointerEventListener");
      e.printStackTrace();
    }
    this.mPowerManagerInternal =
        (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
    this.mBlockUnDimUidList = new ArrayList();
    this.mScreenOffTimeoutSetting =
        Settings.System.getIntForUser(this.mResolver, "screen_off_timeout", 60000, -2);
    this.mSmartStayEnabledSetting =
        Settings.System.getIntForUser(this.mResolver, "intelligent_sleep_mode", 0, -2) != 0;
    this.mBrightness = Settings.System.getIntForUser(this.mResolver, "screen_brightness", 0, -2);
    this.mSettingsObserver = new SettingsObserver(this.mQkDimHandler);
    IntentReceiver intentReceiver = new IntentReceiver();
    this.receiver = intentReceiver;
    this.mContext.registerReceiver(intentReceiver, this.mFilter);
    this.mContext.registerReceiver(this.receiver, this.mPkgFilter);
    this.mResolver.registerContentObserver(
        Settings.System.getUriFor("screen_off_timeout"), false, this.mSettingsObserver, -1);
    this.mResolver.registerContentObserver(
        Settings.System.getUriFor("intelligent_sleep_mode"), false, this.mSettingsObserver, -1);
    this.mResolver.registerContentObserver(
        Settings.System.getUriFor("screen_brightness"), false, this.mSettingsObserver, -1);
    this.mSQLiteSQDwhilteList = new SQLiteSQDwhilteList(this.mContext);
    try {
      ActivityManagerNative.getDefault().registerProcessObserver(this.mProcessObserver);
    } catch (RemoteException e2) {
      Log.e("IbsQuickDim", "RemoteException - registerProcessObserver");
      e2.printStackTrace();
    }
    readDBWhiteList();
    this.mSFSevices = getSurfaceFlingerServices();
  }

  public final void checkQuickDimStatus() {
    int fpsFromSurfaceFlinger = getFpsFromSurfaceFlinger();
    if (fpsFromSurfaceFlinger == -2) {
      return;
    }
    int i = this.mLastFPS;
    if (i == -1) {
      Slog.d("IbsQuickDim", "checkQuickDimStatus first check!");
      this.mLastFPS = fpsFromSurfaceFlinger;
      this.mQkDimHandler.sendEmptyMessageDelayed(1, 8000L);
    } else if (i == fpsFromSurfaceFlinger && i < 1) {
      this.mQkDimHandler.sendEmptyMessageDelayed(2, 8000L);
    } else {
      this.mLastFPS = fpsFromSurfaceFlinger;
      this.mQkDimHandler.sendEmptyMessageDelayed(1, 8000L);
    }
  }

  public final int getFpsFromSurfaceFlinger() {
    Parcel obtain = Parcel.obtain();
    Parcel obtain2 = Parcel.obtain();
    try {
      try {
        if (this.mSFSevices == null) {
          getSurfaceFlingerServices();
        }
        int i = 0;
        if (this.mSFSevices != null) {
          obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
          if (this.mSFSevices.transact(1013, obtain, obtain2, 0)) {
            int readInt = obtain2.readInt();
            long currentTimeMillis = System.currentTimeMillis();
            long j = this.mLastTime;
            if (j != 0) {
              if (this.mLastPagecount < readInt && j < currentTimeMillis) {
                i = (int) ((r10 - r8) / ((currentTimeMillis - j) / 1000.0f));
              }
            }
            this.mLastPagecount = readInt;
            this.mLastTime = currentTimeMillis;
          }
        }
        return i;
      } catch (Exception e) {
        e.printStackTrace();
        obtain.recycle();
        obtain2.recycle();
        return -2;
      }
    } finally {
      obtain.recycle();
      obtain2.recycle();
    }
  }

  public final void initFilter() {
    IntentFilter intentFilter = new IntentFilter();
    this.mFilter = intentFilter;
    intentFilter.addAction("android.intent.action.SCREEN_OFF");
    this.mFilter.addAction("android.intent.action.SCREEN_ON");
    this.mFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
    this.mFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
    this.mFilter.addAction("android.intent.action.BOOT_COMPLETED");
    this.mFilter.addAction("com.samsung.server.PowerManagerService.action.USER_ACTIVITY");
    IntentFilter intentFilter2 = new IntentFilter();
    this.mPkgFilter = intentFilter2;
    intentFilter2.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
    this.mPkgFilter.addDataScheme("package");
  }

  public final void setQuickDimModeFromPms(int i) {
    synchronized (this.mLock) {
      if (this.DEBUG) {
        Slog.d("IbsQuickDim", "BroadcastReceiver flag = " + i);
      }
      if (i == 2) {
        removeAllmessage();
        if (this.DEBUG) {
          Slog.d("IbsQuickDim", "PMS set dim");
        }
        this.mQuickDimMode = 1;
      } else if (i == 1) {
        quitDimMode();
        removeAllmessage();
        this.mLastFPS = -1;
        if (this.DEBUG) {
          Slog.d("IbsQuickDim", "pause check dim");
        }
        this.mQuickDimMode = 4;
      }
    }
  }

  public final void quitDimMode() {
    if (this.mQuickDimMode == 1) {
      if (this.DEBUG) {
        Slog.d("IbsQuickDim", " quit dim mode mQuickDimMode = " + this.mQuickDimMode);
      }
      this.mPowerManagerInternal.setScreenDimDurationOverrideFromSqd(false);
      calcDimActionGain();
    }
  }

  public final void calcDimActionGain() {
    if (this.mDimStartTime == -1) {
      return;
    }
    long currentTimeMillis = System.currentTimeMillis();
    int i = 0;
    for (Map.Entry entry : this.SQDGainMap.entrySet()) {
      if (this.mBrightness <= ((Integer) entry.getKey()).intValue()) {
        break;
      } else {
        i = ((Integer) entry.getValue()).intValue();
      }
    }
    this.mSQDPowerSave += (((currentTimeMillis - this.mDimStartTime) / 1000.0f) / 3600.0f) * i;
    this.mDimStartTime = -1L;
  }

  public final void removeAllmessage() {
    this.mQkDimHandler.removeMessages(2);
    this.mQkDimHandler.removeMessages(1);
    this.mQkDimHandler.removeMessages(3);
  }

  public final void handleBootComplete() {
    Slog.d("IbsQuickDim", "handleBootComplete");
    this.mQkDimHandler.post(
        new Runnable() { // from class:
                         // com.android.server.ibs.sqd.IbsQuickDim$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            IbsQuickDim.this.lambda$handleBootComplete$0();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$handleBootComplete$0() {
    if (SharePrefUtils.getBoolean(this.mContext, "pref_sqd_enabled_key", false)) {
      setUicontrolEnable(true);
    }
  }

  public final void handlePkgRemoved() {
    Slog.d("IbsQuickDim", "handlePkgRemoved");
    this.mQkDimHandler.post(
        new Runnable() { // from class:
                         // com.android.server.ibs.sqd.IbsQuickDim$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            IbsQuickDim.this.lambda$handlePkgRemoved$1();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$handlePkgRemoved$1() {
    try {
      if (this.mUiControlEnabled) {
        setUicontrolEnable(false);
      }
      SharePrefUtils.clear(this.mContext);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public IBinder getSurfaceFlingerServices() {
    if (this.mSFSevices != null) {
      return null;
    }
    IBinder service = ServiceManager.getService("SurfaceFlinger");
    this.mSFSevices = service;
    if (service != null) {
      return service;
    }
    return null;
  }

  public boolean addBlockList(int i, String str) {
    Log.d("IbsQuickDim", " addBlockList uid = " + i + " pkgName = " + str);
    if (!this.mBlockUnDimUidList.contains(Integer.valueOf(i))) {
      this.mBlockUnDimUidList.add(Integer.valueOf(i));
    }
    ContentValues contentValues = new ContentValues();
    contentValues.put("PackageName", str);
    contentValues.put("Uid", Integer.valueOf(i));
    long insert = this.mSQLiteSQDwhilteList.insert(contentValues);
    Log.d("IbsQuickDim", " ret = " + insert);
    return insert != -1;
  }

  public boolean removeBlockList(int i, String str) {
    Log.d("IbsQuickDim", " removeBlockList uid = " + i + " pkgName = " + str);
    if (this.mBlockUnDimUidList.contains(Integer.valueOf(i))) {
      this.mBlockUnDimUidList.remove(Integer.valueOf(i));
    }
    int delete =
        this.mSQLiteSQDwhilteList.delete(
            "PackageName = ? AND Uid = ?", new String[] {str, Integer.toString(i)});
    Log.d("IbsQuickDim", " ret = " + delete);
    return delete != -1;
  }

  public Map getBlockList() {
    HashMap hashMap = new HashMap();
    SQLiteSQDwhilteList sQLiteSQDwhilteList = this.mSQLiteSQDwhilteList;
    Cursor query = sQLiteSQDwhilteList.query(sQLiteSQDwhilteList.mDb, null, null, null, null);
    if (query != null) {
      while (query.moveToNext()) {
        try {
          String string = query.getString(query.getColumnIndex("PackageName"));
          String string2 = query.getString(query.getColumnIndex("Uid"));
          hashMap.put(string + "_" + string2, string2);
        } finally {
          query.close();
        }
      }
    }
    return hashMap;
  }

  public void readDBWhiteList() {
    this.mBlockUnDimUidList.clear();
    SQLiteSQDwhilteList sQLiteSQDwhilteList = this.mSQLiteSQDwhilteList;
    Cursor query = sQLiteSQDwhilteList.query(sQLiteSQDwhilteList.mDb, null, null, null, null);
    if (query != null) {
      while (query.moveToNext()) {
        try {
          this.mBlockUnDimUidList.add(
              Integer.valueOf(Integer.parseInt(query.getString(query.getColumnIndex("Uid")))));
        } finally {
          query.close();
        }
      }
    }
  }

  public void setUicontrolEnable(boolean z) {
    Slog.d("IbsQuickDim", "UI set SQD switch");
    boolean z2 = this.mUiControlEnabled;
    if (z2 != z) {
      if (!z2) {
        this.mQuickDimMode = 2;
        this.mQkDimHandler.sendEmptyMessage(4);
      } else if (this.mQuickdimEnable
          && this.mScreenOn
          && this.mQuickDimMode == 2
          && !this.mCharging) {
        this.mQkDimHandler.sendEmptyMessage(1);
      }
      SharePrefUtils.putBoolean(this.mContext, "pref_sqd_enabled_key", z);
    }
    this.mUiControlEnabled = z;
  }

  public boolean getSQDUiControlEnable() {
    return this.mUiControlEnabled;
  }

  public boolean isSqdSupport() {
    Parcel obtain = Parcel.obtain();
    Parcel obtain2 = Parcel.obtain();
    boolean z = false;
    try {
      try {
        if (this.mSFSevices == null) {
          getSurfaceFlingerServices();
        }
        if (this.mSFSevices != null) {
          obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
          z = this.mSFSevices.transact(1013, obtain, obtain2, 0);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      return z;
    } finally {
      obtain.recycle();
      obtain2.recycle();
    }
  }

  public long[] getGain() {
    return new long[] {this.mChargingFinishTime, ((long) this.mSQDPowerSave) + 1};
  }

  public class qkDimHandler extends Handler {
    public qkDimHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      int fpsFromSurfaceFlinger;
      if (IbsQuickDim.this.mQuickdimEnable
          && IbsQuickDim.this.mScreenOn
          && IbsQuickDim.this.mUiControlEnabled
          && !IbsQuickDim.this.mCharging) {
        int i = message.what;
        if (i == 1) {
          if (IbsQuickDim.this.mQuickDimMode == 2) {
            IbsQuickDim.this.checkQuickDimStatus();
            return;
          }
          return;
        }
        if (i != 2) {
          if (i == 3) {
            IbsQuickDim.this.removeAllmessage();
            IbsQuickDim.this.mQkDimHandler.sendEmptyMessage(1);
            return;
          } else {
            if (i != 4) {
              return;
            }
            IbsQuickDim.this.mLastFPS = -1;
            IbsQuickDim.this.removeAllmessage();
            return;
          }
        }
        if (IbsQuickDim.this.mQuickDimMode == 1
            || (fpsFromSurfaceFlinger = IbsQuickDim.this.getFpsFromSurfaceFlinger()) == -2) {
          return;
        }
        if (fpsFromSurfaceFlinger != IbsQuickDim.this.mLastFPS) {
          IbsQuickDim.this.mLastFPS = fpsFromSurfaceFlinger;
          IbsQuickDim.this.mQkDimHandler.sendEmptyMessageDelayed(1, 8000L);
          return;
        }
        Slog.d("IbsQuickDim", "setScreenBright!!!");
        IbsQuickDim.this.mQuickDimMode = 1;
        IbsQuickDim.this.mLastFPS = -1;
        IbsQuickDim.this.mPowerManagerInternal.setScreenDimDurationOverrideFromSqd(true);
        IbsQuickDim.this.mDimStartTime = System.currentTimeMillis();
        IbsQuickDim.this.removeAllmessage();
      }
    }
  }

  public class IntentReceiver extends BroadcastReceiver {
    public IntentReceiver() {
      IbsQuickDim.this.initFilter();
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
        synchronized (IbsQuickDim.this.mLock) {
          IbsQuickDim.this.mScreenOn = false;
          IbsQuickDim.this.quitDimMode();
          IbsQuickDim.this.mQkDimHandler.sendEmptyMessage(4);
          if (IbsQuickDim.this.DEBUG) {
            Slog.d("IbsQuickDim", "screen off ");
          }
          IbsQuickDim.this.mQuickDimMode = 2;
        }
        return;
      }
      if ("android.intent.action.SCREEN_ON".equals(intent.getAction())) {
        IbsQuickDim.this.mScreenOn = true;
        return;
      }
      if ("android.intent.action.ACTION_POWER_CONNECTED".equals(intent.getAction())) {
        if (IbsQuickDim.this.DEBUG) {
          Slog.d("IbsQuickDim", "POWER_CONNECTED");
        }
        IbsQuickDim.this.mCharging = true;
        return;
      }
      if ("android.intent.action.ACTION_POWER_DISCONNECTED".equals(intent.getAction())) {
        if (IbsQuickDim.this.DEBUG) {
          Slog.d("IbsQuickDim", "POWER_DISCONNECTED");
        }
        IbsQuickDim.this.mCharging = false;
        IbsQuickDim.this.mChargingFinishTime = System.currentTimeMillis();
        IbsQuickDim.this.mSQDPowerSave = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        return;
      }
      if ("com.samsung.server.PowerManagerService.action.USER_ACTIVITY"
          .equals(intent.getAction())) {
        IbsQuickDim.this.setQuickDimModeFromPms(intent.getIntExtra("status", -1));
      } else if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
        IbsQuickDim.this.handleBootComplete();
      } else if ("android.intent.action.PACKAGE_FULLY_REMOVED".equals(intent.getAction())) {
        Optional.ofNullable(intent.getData())
            .ifPresent(
                new Consumer() { // from class:
                                 // com.android.server.ibs.sqd.IbsQuickDim$IntentReceiver$$ExternalSyntheticLambda0
                  @Override // java.util.function.Consumer
                  public final void accept(Object obj) {
                    IbsQuickDim.IntentReceiver.this.lambda$onReceive$0((Uri) obj);
                  }
                });
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onReceive$0(Uri uri) {
      if ("com.samsung.android.statsd".equals(uri.getSchemeSpecificPart())) {
        IbsQuickDim.this.handlePkgRemoved();
      }
    }
  }

  public final class SettingsObserver extends ContentObserver {
    public SettingsObserver(Handler handler) {
      super(handler);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri) {
      IbsQuickDim.this.mScreenOffTimeoutSetting =
          Settings.System.getIntForUser(r6.mResolver, "screen_off_timeout", 60000, -2);
      IbsQuickDim ibsQuickDim = IbsQuickDim.this;
      ibsQuickDim.mSmartStayEnabledSetting =
          Settings.System.getIntForUser(ibsQuickDim.mResolver, "intelligent_sleep_mode", 0, -2)
              != 0;
      if (IbsQuickDim.this.mScreenOffTimeoutSetting < 60000
          || IbsQuickDim.this.mSmartStayEnabledSetting) {
        IbsQuickDim.this.mQuickDimMode = 2;
        IbsQuickDim.this.mQkDimHandler.sendEmptyMessage(4);
        IbsQuickDim.this.mQuickdimEnable = false;
      } else if (!IbsQuickDim.this.mQuickdimEnable
          && IbsQuickDim.this.mScreenOffTimeoutSetting >= 60000
          && !IbsQuickDim.this.mSmartStayEnabledSetting) {
        IbsQuickDim.this.mQuickdimEnable = true;
        IbsQuickDim.this.mQkDimHandler.sendEmptyMessage(1);
      }
      if (IbsQuickDim.this.mQuickDimMode != 1) {
        IbsQuickDim ibsQuickDim2 = IbsQuickDim.this;
        ibsQuickDim2.mBrightness =
            Settings.System.getIntForUser(ibsQuickDim2.mResolver, "screen_brightness", 0, -2);
      }
    }
  }

  public class SQLiteSQDwhilteList extends SQLiteOpenHelper {
    public SQLiteDatabase mDb;

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {}

    public SQLiteSQDwhilteList(Context context) {
      super(context, "SQD_whilte_list", (SQLiteDatabase.CursorFactory) null, 1);
      this.mDb = null;
      this.mDb = getWritableDatabase();
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
      sQLiteDatabase.execSQL(
          "CREATE TABLE IF NOT EXISTS  whilteList(_id INTEGER PRIMARY KEY AUTOINCREMENT,PackageName"
              + " TEXT,Uid INT)");
    }

    public Cursor query(
        SQLiteDatabase sQLiteDatabase, String[] strArr, String str, String[] strArr2, String str2) {
      SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
      sQLiteQueryBuilder.setTables("whilteList");
      return sQLiteQueryBuilder.query(sQLiteDatabase, strArr, str, strArr2, null, null, str2);
    }

    public long insert(ContentValues contentValues) {
      if (IbsQuickDim.this.DEBUG) {
        Slog.d("IbsQuickDim", "SQLiteSQDwhilteList: ready to add whiteList!");
      }
      return this.mDb.insert("whilteList", "", contentValues);
    }

    public int delete(String str, String[] strArr) {
      return this.mDb.delete("whilteList", str, strArr);
    }
  }

  public void dump(PrintWriter printWriter, String[] strArr) {
    printWriter.println("");
    printWriter.println("SQD Version: 1.0");
    printWriter.println("SQD swich status : ");
    printWriter.println("UI on-off : " + this.mUiControlEnabled);
    printWriter.println("mCharging : " + this.mCharging + " mSQDPowerSave : " + this.mSQDPowerSave);
    StringBuilder sb = new StringBuilder();
    sb.append("policy status : ");
    sb.append(this.mQuickDimMode);
    printWriter.println(sb.toString());
    printWriter.println("block List:");
    for (int i = 0; i < this.mBlockUnDimUidList.size(); i++) {
      printWriter.println("Line " + i + " : " + this.mBlockUnDimUidList.get(i));
    }
    if (strArr.length <= 1 || !strArr[0].equals("sqd_swich")) {
      return;
    }
    if (strArr[1].equals("true")) {
      this.mUiControlEnabled = true;
    } else {
      this.mUiControlEnabled = false;
    }
  }
}
