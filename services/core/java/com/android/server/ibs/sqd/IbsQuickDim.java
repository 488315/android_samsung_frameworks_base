package com.android.server.ibs.sqd;

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
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.Slog;
import android.view.MotionEvent;
import android.view.WindowManagerPolicyConstants;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.ibs.sleepmode.SharePrefUtils;

import com.samsung.android.knoxguard.service.utils.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class IbsQuickDim {
    public static final boolean DEBUG = "eng".equals(Build.TYPE);
    public ArraySet mAllowDimUidSet;
    public ArraySet mBlockUnDimUidSet;
    public final Context mContext;
    public IntentFilter mFilter;
    public HandlerThread mHandlerThread;
    public IntentFilter mPkgFilter;
    public PowerManagerInternal mPowerManagerInternal;
    public ContentResolver mResolver;
    public IBinder mSFSevices;
    public long mScreenOffTimeoutSetting;
    public boolean mSmartStayEnabledSetting;
    public IntentReceiver receiver;
    public final Object mLock = new Object();
    public float mSQDPowerSave = FullScreenMagnificationGestureHandler.MAX_SCALE;
    public int mBrightness = 0;
    public final Map SQDGainMap =
            new HashMap() { // from class: com.android.server.ibs.sqd.IbsQuickDim.1
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
                            Integer.valueOf(
                                    IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT),
                            Integer.valueOf(
                                    FrameworkStatsLog
                                            .DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_EMPTY_STATE_NO_SHARING_TO_WORK));
                }
            };
    public boolean mCharging = false;
    public long mChargingFinishTime = 0;
    public long mDimStartTime = 0;
    public SQLiteSQDwhilteList mSQLiteSQDwhilteList = null;
    public SettingsObserver mSettingsObserver = null;
    public long mLastPagecount = 0;
    public long mLastTime = 0;
    public boolean mUiControlEnabled = false;
    public int mQuickDimMode = 2;
    public qkDimHandler mQkDimHandler = null;
    public final AnonymousClass2 mPointerEventListener =
            new WindowManagerPolicyConstants
                    .PointerEventListener() { // from class:
                                              // com.android.server.ibs.sqd.IbsQuickDim.2
                public final void onPointerEvent(MotionEvent motionEvent) {
                    if (motionEvent.getAction() == 0) {
                        IbsQuickDim ibsQuickDim = IbsQuickDim.this;
                        if (ibsQuickDim.mQuickDimMode == 2) {
                            ibsQuickDim.mQkDimHandler.sendEmptyMessage(3);
                        }
                    }
                }
            };
    public int mLastFPS = -1;
    public boolean mScreenOn = false;
    public boolean mQuickdimEnable = true;
    public final AnonymousClass3 mProcessObserver =
            new IProcessObserver.Stub() { // from class: com.android.server.ibs.sqd.IbsQuickDim.3
                public final void onForegroundActivitiesChanged(int i, int i2, boolean z) {
                    if (IbsQuickDim.m583$$Nest$misSqdCanWork(IbsQuickDim.this)) {
                        synchronized (IbsQuickDim.this.mLock) {
                            try {
                                IbsQuickDim ibsQuickDim = IbsQuickDim.this;
                                if (ibsQuickDim.mQuickDimMode == 1) {
                                    return;
                                }
                                if (z) {
                                    if (!ibsQuickDim.mBlockUnDimUidSet.contains(
                                            Integer.valueOf(i2))) {
                                        IbsQuickDim ibsQuickDim2 = IbsQuickDim.this;
                                        if (ibsQuickDim2.mUiControlEnabled
                                                || ibsQuickDim2.mAllowDimUidSet.contains(
                                                        Integer.valueOf(i2))) {
                                            IbsQuickDim ibsQuickDim3 = IbsQuickDim.this;
                                            if (((ibsQuickDim3.mUiControlEnabled
                                                                    || ibsQuickDim3.mAllowDimUidSet
                                                                            .contains(
                                                                                    Integer.valueOf(
                                                                                            i2)))
                                                            && IbsQuickDim.this.mQuickDimMode == 3)
                                                    || IbsQuickDim.this.mQuickDimMode == 4) {
                                                IbsQuickDim ibsQuickDim4 = IbsQuickDim.this;
                                                ibsQuickDim4.mQuickDimMode = 2;
                                                ibsQuickDim4.mQkDimHandler.sendEmptyMessage(1);
                                            }
                                        }
                                    }
                                    IbsQuickDim ibsQuickDim5 = IbsQuickDim.this;
                                    if (ibsQuickDim5.mQuickDimMode != 3) {
                                        ibsQuickDim5.mQuickDimMode = 3;
                                        if (IbsQuickDim.DEBUG) {
                                            Slog.d(
                                                    "IbsQuickDim",
                                                    " stop dim detect because fg click"
                                                        + " mQuickDimMode = "
                                                            + IbsQuickDim.this.mQuickDimMode);
                                        }
                                        IbsQuickDim.this.mQkDimHandler.sendEmptyMessage(4);
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

                public final void onForegroundServicesChanged(int i, int i2, int i3) {}

                public final void onProcessDied(int i, int i2) {}

                public final void onProcessStarted(
                        int i, int i2, int i3, String str, String str2) {}
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IntentReceiver extends BroadcastReceiver {
        public IntentReceiver() {
            IntentFilter intentFilter = new IntentFilter();
            IbsQuickDim.this.mFilter = intentFilter;
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            IbsQuickDim.this.mFilter.addAction("android.intent.action.SCREEN_ON");
            IbsQuickDim.this.mFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
            IbsQuickDim.this.mFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
            IbsQuickDim.this.mFilter.addAction("android.intent.action.BOOT_COMPLETED");
            IbsQuickDim.this.mFilter.addAction(
                    "com.samsung.server.PowerManagerService.action.USER_ACTIVITY");
            IntentFilter intentFilter2 = new IntentFilter();
            IbsQuickDim.this.mPkgFilter = intentFilter2;
            intentFilter2.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
            IbsQuickDim.this.mPkgFilter.addDataScheme("package");
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                synchronized (IbsQuickDim.this.mLock) {
                    try {
                        IbsQuickDim ibsQuickDim = IbsQuickDim.this;
                        ibsQuickDim.mScreenOn = false;
                        ibsQuickDim.quitDimMode();
                        IbsQuickDim.this.mQkDimHandler.sendEmptyMessage(4);
                        if (IbsQuickDim.DEBUG) {
                            Slog.d("IbsQuickDim", "screen off ");
                        }
                        IbsQuickDim.this.mQuickDimMode = 2;
                    } finally {
                    }
                }
                return;
            }
            if ("android.intent.action.SCREEN_ON".equals(intent.getAction())) {
                IbsQuickDim.this.mScreenOn = true;
                return;
            }
            if ("android.intent.action.ACTION_POWER_CONNECTED".equals(intent.getAction())) {
                if (IbsQuickDim.DEBUG) {
                    Slog.d("IbsQuickDim", "POWER_CONNECTED");
                }
                IbsQuickDim.this.mCharging = true;
                return;
            }
            if ("android.intent.action.ACTION_POWER_DISCONNECTED".equals(intent.getAction())) {
                if (IbsQuickDim.DEBUG) {
                    Slog.d("IbsQuickDim", "POWER_DISCONNECTED");
                }
                IbsQuickDim ibsQuickDim2 = IbsQuickDim.this;
                ibsQuickDim2.mCharging = false;
                ibsQuickDim2.mChargingFinishTime = System.currentTimeMillis();
                IbsQuickDim.this.mSQDPowerSave = FullScreenMagnificationGestureHandler.MAX_SCALE;
                return;
            }
            if (!"com.samsung.server.PowerManagerService.action.USER_ACTIVITY"
                    .equals(intent.getAction())) {
                if (!"android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                    if ("android.intent.action.PACKAGE_FULLY_REMOVED".equals(intent.getAction())) {
                        Optional.ofNullable(intent.getData())
                                .ifPresent(new IbsQuickDim$$ExternalSyntheticLambda1(1, this));
                        return;
                    }
                    return;
                } else {
                    IbsQuickDim ibsQuickDim3 = IbsQuickDim.this;
                    ibsQuickDim3.getClass();
                    Slog.d("IbsQuickDim", "handleBootComplete");
                    ibsQuickDim3.mQkDimHandler.post(
                            new IbsQuickDim$$ExternalSyntheticLambda0(ibsQuickDim3, 1));
                    return;
                }
            }
            int intExtra = intent.getIntExtra(Constants.JSON_CLIENT_DATA_STATUS, -1);
            IbsQuickDim ibsQuickDim4 = IbsQuickDim.this;
            synchronized (ibsQuickDim4.mLock) {
                try {
                    boolean z = IbsQuickDim.DEBUG;
                    if (z) {
                        Slog.d("IbsQuickDim", "BroadcastReceiver flag = " + intExtra);
                    }
                    if (intExtra == 2) {
                        ibsQuickDim4.removeAllmessage();
                        if (z) {
                            Slog.d("IbsQuickDim", "PMS set dim");
                        }
                        ibsQuickDim4.mQuickDimMode = 1;
                    } else if (intExtra == 1) {
                        ibsQuickDim4.quitDimMode();
                        ibsQuickDim4.removeAllmessage();
                        ibsQuickDim4.mLastFPS = -1;
                        if (z) {
                            Slog.d("IbsQuickDim", "pause check dim");
                        }
                        ibsQuickDim4.mQuickDimMode = 4;
                    }
                } finally {
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SQLiteSQDwhilteList extends SQLiteOpenHelper {
        public AllowDataOperator mAllowDataOperator;
        public AllowDataOperator mBlockDataOperator;
        public SQLiteDatabase mDb;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class AllowDataOperator {
            public final /* synthetic */ int $r8$classId;
            public final /* synthetic */ SQLiteSQDwhilteList this$1;

            public /* synthetic */ AllowDataOperator(
                    SQLiteSQDwhilteList sQLiteSQDwhilteList, int i) {
                this.$r8$classId = i;
                this.this$1 = sQLiteSQDwhilteList;
            }

            public final int delete(String[] strArr) {
                switch (this.$r8$classId) {
                    case 0:
                        return this.this$1.mDb.delete(
                                "allowList", "PackageName = ? AND Uid = ?", strArr);
                    default:
                        return this.this$1.mDb.delete(
                                "whilteList", "PackageName = ? AND Uid = ?", strArr);
                }
            }

            public final long insert(ContentValues contentValues) {
                switch (this.$r8$classId) {
                    case 0:
                        if (IbsQuickDim.DEBUG) {
                            Slog.d("IbsQuickDim", "SQLiteSQDwhilteList: ready to add whiteList!");
                        }
                        return this.this$1.mDb.insert("allowList", "", contentValues);
                    default:
                        if (IbsQuickDim.DEBUG) {
                            Slog.d("IbsQuickDim", "SQLiteSQDwhilteList: ready to add whiteList!");
                        }
                        return this.this$1.mDb.insert("whilteList", "", contentValues);
                }
            }

            public final Cursor query(SQLiteDatabase sQLiteDatabase) {
                switch (this.$r8$classId) {
                    case 0:
                        SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
                        sQLiteQueryBuilder.setTables("allowList");
                        return sQLiteQueryBuilder.query(
                                sQLiteDatabase, null, null, null, null, null, null);
                    default:
                        SQLiteQueryBuilder sQLiteQueryBuilder2 = new SQLiteQueryBuilder();
                        sQLiteQueryBuilder2.setTables("whilteList");
                        return sQLiteQueryBuilder2.query(
                                sQLiteDatabase, null, null, null, null, null, null);
                }
            }
        }

        public final AllowDataOperator getDataOperator(int i) {
            return i == 0 ? this.mBlockDataOperator : this.mAllowDataOperator;
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(
                    "CREATE TABLE IF NOT EXISTS  whilteList(_id INTEGER PRIMARY KEY"
                        + " AUTOINCREMENT,PackageName TEXT,Uid INT)");
            sQLiteDatabase.execSQL(
                    "CREATE TABLE IF NOT EXISTS  allowList(_id INTEGER PRIMARY KEY"
                        + " AUTOINCREMENT,PackageName TEXT,Uid INT)");
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            Slog.w(
                    "IbsQuickDim",
                    DualAppManagerService$$ExternalSyntheticOutline0.m(
                            i,
                            i2,
                            "Upgrading database from version ",
                            " to ",
                            ", which will destroy all old data"));
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS suggestions");
            onCreate(sQLiteDatabase);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            boolean z2;
            IbsQuickDim.this.mScreenOffTimeoutSetting =
                    Settings.System.getIntForUser(r9.mResolver, "screen_off_timeout", 60000, -2);
            IbsQuickDim ibsQuickDim = IbsQuickDim.this;
            ibsQuickDim.mSmartStayEnabledSetting =
                    Settings.System.getIntForUser(
                                    ibsQuickDim.mResolver, "intelligent_sleep_mode", 0, -2)
                            != 0;
            IbsQuickDim ibsQuickDim2 = IbsQuickDim.this;
            long j = ibsQuickDim2.mScreenOffTimeoutSetting;
            if (j < 60000 || (z2 = ibsQuickDim2.mSmartStayEnabledSetting)) {
                ibsQuickDim2.mQuickDimMode = 2;
                ibsQuickDim2.mQkDimHandler.sendEmptyMessage(4);
                IbsQuickDim.this.mQuickdimEnable = false;
            } else if (!ibsQuickDim2.mQuickdimEnable && j >= 60000 && !z2) {
                ibsQuickDim2.mQuickdimEnable = true;
                ibsQuickDim2.mQkDimHandler.sendEmptyMessage(1);
            }
            IbsQuickDim ibsQuickDim3 = IbsQuickDim.this;
            if (ibsQuickDim3.mQuickDimMode != 1) {
                ibsQuickDim3.mBrightness =
                        Settings.System.getIntForUser(
                                ibsQuickDim3.mResolver, "screen_brightness", 0, -2);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class qkDimHandler extends Handler {
        public qkDimHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int fpsFromSurfaceFlinger;
            int fpsFromSurfaceFlinger2;
            IbsQuickDim ibsQuickDim = IbsQuickDim.this;
            if (IbsQuickDim.m583$$Nest$misSqdCanWork(ibsQuickDim)) {
                int i = message.what;
                if (i == 1) {
                    if (ibsQuickDim.mQuickDimMode != 2
                            || (fpsFromSurfaceFlinger = ibsQuickDim.getFpsFromSurfaceFlinger())
                                    == -2) {
                        return;
                    }
                    int i2 = ibsQuickDim.mLastFPS;
                    if (i2 == -1) {
                        Slog.d("IbsQuickDim", "checkQuickDimStatus first check!");
                        ibsQuickDim.mLastFPS = fpsFromSurfaceFlinger;
                        ibsQuickDim.mQkDimHandler.sendEmptyMessageDelayed(1, 8000L);
                        return;
                    } else if (i2 == fpsFromSurfaceFlinger && i2 < 1) {
                        ibsQuickDim.mQkDimHandler.sendEmptyMessageDelayed(2, 8000L);
                        return;
                    } else {
                        ibsQuickDim.mLastFPS = fpsFromSurfaceFlinger;
                        ibsQuickDim.mQkDimHandler.sendEmptyMessageDelayed(1, 8000L);
                        return;
                    }
                }
                if (i != 2) {
                    if (i == 3) {
                        ibsQuickDim.removeAllmessage();
                        ibsQuickDim.mQkDimHandler.sendEmptyMessage(1);
                        return;
                    } else {
                        if (i != 4) {
                            return;
                        }
                        ibsQuickDim.mLastFPS = -1;
                        ibsQuickDim.removeAllmessage();
                        return;
                    }
                }
                if (ibsQuickDim.mQuickDimMode == 1
                        || (fpsFromSurfaceFlinger2 = ibsQuickDim.getFpsFromSurfaceFlinger())
                                == -2) {
                    return;
                }
                if (fpsFromSurfaceFlinger2 != ibsQuickDim.mLastFPS) {
                    ibsQuickDim.mLastFPS = fpsFromSurfaceFlinger2;
                    ibsQuickDim.mQkDimHandler.sendEmptyMessageDelayed(1, 8000L);
                    return;
                }
                Slog.d("IbsQuickDim", "setScreenBright!!!");
                ibsQuickDim.mQuickDimMode = 1;
                ibsQuickDim.mLastFPS = -1;
                ibsQuickDim.mPowerManagerInternal.setScreenDimDurationOverrideFromSqd(true);
                ibsQuickDim.mDimStartTime = System.currentTimeMillis();
                ibsQuickDim.removeAllmessage();
            }
        }
    }

    /* renamed from: -$$Nest$misSqdCanWork, reason: not valid java name */
    public static boolean m583$$Nest$misSqdCanWork(IbsQuickDim ibsQuickDim) {
        return ibsQuickDim.mQuickdimEnable
                && ibsQuickDim.mScreenOn
                && (ibsQuickDim.mUiControlEnabled || !ibsQuickDim.mAllowDimUidSet.isEmpty())
                && !ibsQuickDim.mCharging;
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.ibs.sqd.IbsQuickDim$3] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.server.ibs.sqd.IbsQuickDim$2] */
    public IbsQuickDim(Context context) {
        this.mContext = context;
    }

    public final int getFpsFromSurfaceFlinger() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            try {
                IBinder iBinder = this.mSFSevices;
                if (iBinder == null && iBinder == null) {
                    this.mSFSevices = ServiceManager.getService("SurfaceFlinger");
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
                obtain.recycle();
                obtain2.recycle();
                return i;
            } catch (Exception e) {
                e.printStackTrace();
                obtain.recycle();
                obtain2.recycle();
                return -2;
            }
        } catch (Throwable th) {
            obtain.recycle();
            obtain2.recycle();
            throw th;
        }
    }

    public final void quitDimMode() {
        if (this.mQuickDimMode == 1) {
            if (DEBUG) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        new StringBuilder(" quit dim mode mQuickDimMode = "),
                        this.mQuickDimMode,
                        "IbsQuickDim");
            }
            int i = 0;
            this.mPowerManagerInternal.setScreenDimDurationOverrideFromSqd(false);
            if (this.mDimStartTime == -1) {
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            for (Map.Entry entry : ((HashMap) this.SQDGainMap).entrySet()) {
                if (this.mBrightness <= ((Integer) entry.getKey()).intValue()) {
                    break;
                } else {
                    i = ((Integer) entry.getValue()).intValue();
                }
            }
            this.mSQDPowerSave +=
                    (((currentTimeMillis - this.mDimStartTime) / 1000.0f) / 3600.0f) * i;
            this.mDimStartTime = -1L;
        }
    }

    public final void removeAllmessage() {
        this.mQkDimHandler.removeMessages(2);
        this.mQkDimHandler.removeMessages(1);
        this.mQkDimHandler.removeMessages(3);
    }

    public final void setUicontrolEnable(boolean z) {
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
}
