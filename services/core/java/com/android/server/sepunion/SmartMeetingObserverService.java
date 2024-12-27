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
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserManager;
import android.util.Log;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.sepunion.SmartMeetingObserverService.AnonymousClass2;

import com.samsung.android.app.usage.IUsageStatsWatcher;
import com.samsung.android.sepunion.ISmartMeetingObserverService;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SmartMeetingObserverService extends ISmartMeetingObserverService.Stub
        implements AbsSemSystemService {
    public static final Uri SMMT_OBSERVER_URI =
            Uri.parse("content://com.samsung.android.smartmeeting.observer");
    public final Context mContext;
    public List mPackageNameList;
    public int mObserverRegisterState = 0;
    public final AnonymousClass1 mForegroundServiceObserver =
            new IForegroundServiceObserver
                    .Stub() { // from class:
                              // com.android.server.sepunion.SmartMeetingObserverService.1
                public final void onForegroundStateChanged(
                        IBinder iBinder, String str, int i, boolean z) {
                    Message obtain = Message.obtain();
                    obtain.what = 10;
                    Bundle bundle = new Bundle();
                    bundle.putString(
                            "component_name", iBinder == null ? "null" : iBinder.toString());
                    bundle.putString("pkg_name", str);
                    bundle.putInt("user_id", i);
                    bundle.putBoolean("is_foreground", z);
                    obtain.setData(bundle);
                    SmartMeetingObserverService.this.mHandler.sendMessage(obtain);
                }
            };
    public final AnonymousClass3 mUsageStatsWatcher =
            new IUsageStatsWatcher
                    .Stub() { // from class:
                              // com.android.server.sepunion.SmartMeetingObserverService.3
                public final void notePauseComponent(
                        ComponentName componentName, Intent intent, int i, int i2) {
                    List list;
                    if (componentName == null
                            || (list = SmartMeetingObserverService.this.mPackageNameList) == null
                            || !list.contains(componentName.getPackageName())) {
                        return;
                    }
                    SmartMeetingObserverService.m871$$Nest$mhandleUsageStatsChanged(
                            SmartMeetingObserverService.this, 2, i2, componentName);
                }

                public final void noteResumeComponent(
                        ComponentName componentName, Intent intent, int i, int i2) {
                    List list;
                    if (componentName == null
                            || (list = SmartMeetingObserverService.this.mPackageNameList) == null
                            || !list.contains(componentName.getPackageName())) {
                        return;
                    }
                    SmartMeetingObserverService.m871$$Nest$mhandleUsageStatsChanged(
                            SmartMeetingObserverService.this, 1, i2, componentName);
                }

                public final void noteStopComponent(
                        ComponentName componentName, Intent intent, int i, int i2) {
                    List list;
                    if (componentName == null
                            || (list = SmartMeetingObserverService.this.mPackageNameList) == null
                            || !list.contains(componentName.getPackageName())) {
                        return;
                    }
                    SmartMeetingObserverService.m871$$Nest$mhandleUsageStatsChanged(
                            SmartMeetingObserverService.this, 23, i2, componentName);
                }
            };
    public final MyHandler mHandler =
            new MyHandler(
                    KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(
                                    "SmartMeetingObserverService")
                            .getLooper());

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.sepunion.SmartMeetingObserverService$2, reason: invalid class name */
    public final class AnonymousClass2 extends BroadcastReceiver {
        public static final /* synthetic */ int $r8$clinit = 0;

        public AnonymousClass2() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, final Intent intent) {
            SmartMeetingObserverService.this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.sepunion.SmartMeetingObserverService$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SmartMeetingObserverService.AnonymousClass2 anonymousClass2 =
                                    SmartMeetingObserverService.AnonymousClass2.this;
                            Intent intent2 = intent;
                            int i = SmartMeetingObserverService.AnonymousClass2.$r8$clinit;
                            anonymousClass2.getClass();
                            Log.d(
                                    "SmartMeetingObserverService",
                                    "onReceive: " + intent2.getAction());
                            if (Objects.equals(
                                    intent2.getAction(), "android.intent.action.USER_UNLOCKED")) {
                                UserManager userManager =
                                        (UserManager)
                                                SmartMeetingObserverService.this.mContext
                                                        .getSystemService("user");
                                if (userManager == null || !userManager.isUserUnlocked()) {
                                    Log.w(
                                            "SmartMeetingObserverService",
                                            "registerUsageStatsWatcher: failed");
                                } else {
                                    SmartMeetingObserverService
                                            .m872$$Nest$mregisterUsageStatsWatcher(
                                                    SmartMeetingObserverService.this);
                                }
                            }
                        }
                    });
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("VDC thread msg "),
                    message.what,
                    "SmartMeetingObserverService");
            int i = message.what;
            SmartMeetingObserverService smartMeetingObserverService =
                    SmartMeetingObserverService.this;
            if (i == 10) {
                Bundle data = message.getData();
                Uri uri = SmartMeetingObserverService.SMMT_OBSERVER_URI;
                smartMeetingObserverService.getClass();
                try {
                    smartMeetingObserverService
                            .mContext
                            .getContentResolver()
                            .call(
                                    SmartMeetingObserverService.SMMT_OBSERVER_URI,
                                    "onForegroundServiceStateChanged",
                                    (String) null,
                                    data);
                    return;
                } catch (IllegalArgumentException e) {
                    Log.e(
                            "SmartMeetingObserverService",
                            "onFgServiceStateChanged call failed: ",
                            e);
                    return;
                }
            }
            if (i == 20) {
                Bundle data2 = message.getData();
                Uri uri2 = SmartMeetingObserverService.SMMT_OBSERVER_URI;
                smartMeetingObserverService.getClass();
                try {
                    Log.d("SmartMeetingObserverService", "onUsageStatsChanged");
                    smartMeetingObserverService
                            .mContext
                            .getContentResolver()
                            .call(
                                    SmartMeetingObserverService.SMMT_OBSERVER_URI,
                                    "onUsageStatsChanged",
                                    (String) null,
                                    data2);
                    return;
                } catch (IllegalArgumentException e2) {
                    Log.e("SmartMeetingObserverService", "onUsageStatsChanged call failed: ", e2);
                    return;
                }
            }
            if (i != 30) {
                return;
            }
            Uri uri3 = SmartMeetingObserverService.SMMT_OBSERVER_URI;
            smartMeetingObserverService.getClass();
            Log.d("SmartMeetingObserverService", "unregisterUsageStatsWatcher");
            UsageStatsManager usageStatsManager =
                    (UsageStatsManager)
                            smartMeetingObserverService.mContext.getSystemService("usagestats");
            if (usageStatsManager != null) {
                usageStatsManager.unregisterUsageStatsWatcher(
                        smartMeetingObserverService.mUsageStatsWatcher);
                smartMeetingObserverService.mObserverRegisterState &= -9;
            } else {
                Log.e("SmartMeetingObserverService", "get UsageStatsManager null");
            }
            SmartMeetingObserverService.m872$$Nest$mregisterUsageStatsWatcher(
                    smartMeetingObserverService);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface ObserverType {}

    /* renamed from: -$$Nest$mhandleUsageStatsChanged, reason: not valid java name */
    public static void m871$$Nest$mhandleUsageStatsChanged(
            SmartMeetingObserverService smartMeetingObserverService,
            int i,
            int i2,
            ComponentName componentName) {
        smartMeetingObserverService.getClass();
        Message obtain = Message.obtain();
        obtain.what = 20;
        Bundle bundle = new Bundle();
        bundle.putInt("event_type", i);
        bundle.putInt("user_id", i2);
        bundle.putString("pkg_name", componentName.getPackageName());
        bundle.putString("class_name", componentName.getClassName());
        obtain.setData(bundle);
        smartMeetingObserverService.mHandler.sendMessage(obtain);
    }

    /* renamed from: -$$Nest$mregisterUsageStatsWatcher, reason: not valid java name */
    public static void m872$$Nest$mregisterUsageStatsWatcher(
            SmartMeetingObserverService smartMeetingObserverService) {
        smartMeetingObserverService.getClass();
        ArrayList arrayList = new ArrayList();
        ContentResolver contentResolver = smartMeetingObserverService.mContext.getContentResolver();
        if (contentResolver != null) {
            try {
                Cursor query =
                        contentResolver.query(
                                Uri.withAppendedPath(SMMT_OBSERVER_URI, "video_call_app_info"),
                                null,
                                null,
                                null);
                if (query != null) {
                    try {
                        if (query.getCount() > 0) {
                            Log.d(
                                    "SmartMeetingObserverService",
                                    "contentResolver query: " + query.getCount());
                            int columnIndex = query.getColumnIndex("package_name");
                            if (columnIndex < 0) {
                                query.close();
                            } else {
                                while (query.moveToNext()) {
                                    String string = query.getString(columnIndex);
                                    if (string != null && !string.isEmpty()) {
                                        arrayList.add(string);
                                    }
                                }
                            }
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
                Log.e("SmartMeetingObserverService", "getPackageNameList() failed: ", e);
            }
            if (arrayList != null || arrayList.isEmpty()) {
                Log.w("SmartMeetingObserverService", "registerUsageStatsWatcher: fail");
            }
            Log.d("SmartMeetingObserverService", "registerUsageStatsWatcher: " + arrayList);
            UsageStatsManager usageStatsManager =
                    (UsageStatsManager)
                            smartMeetingObserverService.mContext.getSystemService("usagestats");
            if (usageStatsManager == null) {
                Log.e("SmartMeetingObserverService", "get UsageStatsManager null");
                return;
            }
            usageStatsManager.registerUsageStatsWatcher(
                    smartMeetingObserverService.mUsageStatsWatcher);
            smartMeetingObserverService.mObserverRegisterState |= 8;
            smartMeetingObserverService.mPackageNameList = arrayList;
            return;
        }
        Log.e("SmartMeetingObserverService", "get ContentResolver null");
        arrayList = null;
        if (arrayList != null) {}
        Log.w("SmartMeetingObserverService", "registerUsageStatsWatcher: fail");
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.sepunion.SmartMeetingObserverService$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.sepunion.SmartMeetingObserverService$3] */
    public SmartMeetingObserverService(Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder m$1 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                        printWriter,
                        "\n"
                            + "##### SmartMeetingObserverService #####\n"
                            + "##### (dumpsys sepunion SmartMeetingObserverService) #####\n",
                        "Observer register state: ");
        m$1.append(Integer.toBinaryString(this.mObserverRegisterState));
        printWriter.println(m$1.toString());
        StringBuilder sb = new StringBuilder("packageNameList: ");
        List list = this.mPackageNameList;
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                sb, list == null ? "null" : list.toString(), printWriter);
    }

    public final AbsSemSystemService getSemSystemService(String str) {
        return null;
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onBootPhase(int i) {
        if (i == 1000) {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.sepunion.SmartMeetingObserverService$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            final SmartMeetingObserverService smartMeetingObserverService =
                                    SmartMeetingObserverService.this;
                            Uri uri = SmartMeetingObserverService.SMMT_OBSERVER_URI;
                            smartMeetingObserverService.getClass();
                            Log.d("SmartMeetingObserverService", "init");
                            Log.d("SmartMeetingObserverService", "registerUserUnlockedObserver");
                            smartMeetingObserverService.mContext.registerReceiver(
                                    smartMeetingObserverService.new AnonymousClass2(),
                                    new IntentFilter("android.intent.action.USER_UNLOCKED"));
                            smartMeetingObserverService.mObserverRegisterState |= 1;
                            try {
                                Log.d(
                                        "SmartMeetingObserverService",
                                        "registerForegroundServiceObserver");
                                ActivityManager.getService()
                                        .registerForegroundServiceObserver(
                                                smartMeetingObserverService
                                                        .mForegroundServiceObserver);
                                smartMeetingObserverService.mObserverRegisterState |= 2;
                            } catch (RemoteException | SecurityException e) {
                                Log.e(
                                        "SmartMeetingObserverService",
                                        "registerForegroundServiceObserver: failed ",
                                        e);
                            }
                            ContentObserver contentObserver =
                                    new ContentObserver(
                                            smartMeetingObserverService
                                                    .mHandler) { // from class:
                                                                 // com.android.server.sepunion.SmartMeetingObserverService.4
                                        @Override // android.database.ContentObserver
                                        public final void onChange(boolean z, Uri uri2) {
                                            if (uri2 == null) {
                                                return;
                                            }
                                            SmartMeetingObserverService.this.mHandler
                                                    .removeMessages(30);
                                            SmartMeetingObserverService.this.mHandler
                                                    .sendEmptyMessage(30);
                                        }
                                    };
                            ContentResolver contentResolver =
                                    smartMeetingObserverService.mContext.getContentResolver();
                            if (contentResolver == null) {
                                Log.e("SmartMeetingObserverService", "get ContentResolver null");
                            } else {
                                contentResolver.registerContentObserver(
                                        SmartMeetingObserverService.SMMT_OBSERVER_URI,
                                        true,
                                        contentObserver);
                                smartMeetingObserverService.mObserverRegisterState |= 4;
                            }
                        }
                    });
        }
    }

    public final void onCleanupUser(int i) {}

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onCreate(Bundle bundle) {
        Log.d("SmartMeetingObserverService", "onCreate");
    }

    public final void onDestroy() {
        Log.d("SmartMeetingObserverService", "onDestroy");
    }

    public final void onStart() {
        Log.d("SmartMeetingObserverService", "onStart");
    }

    public final void onStartUser(int i) {}

    public final void onStopUser(int i) {}

    public final void onSwitchUser(int i) {}

    public final void onUnlockUser(int i) {}
}
