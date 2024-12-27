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
import com.android.server.sepunion.VDCObserverService.AnonymousClass2;

import com.samsung.android.app.usage.IUsageStatsWatcher;
import com.samsung.android.sepunion.IVDCObserverService;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class VDCObserverService extends IVDCObserverService.Stub
        implements AbsSemSystemService {
    public static final Uri VDC_OBSERVER_URI =
            Uri.parse("content://com.samsung.android.vdc.observer");
    public final Context mContext;
    public List mRegisteredComponentList;
    public int mObserverRegisterState = 0;
    public final AnonymousClass1 mForegroundServiceObserver =
            new IForegroundServiceObserver
                    .Stub() { // from class: com.android.server.sepunion.VDCObserverService.1
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
                    VDCObserverService.this.mHandler.sendMessage(obtain);
                }
            };
    public final AnonymousClass3 mUsageStatsWatcher =
            new IUsageStatsWatcher
                    .Stub() { // from class: com.android.server.sepunion.VDCObserverService.3
                public final void notePauseComponent(
                        ComponentName componentName, Intent intent, int i, int i2) {
                    if (componentName != null) {
                        VDCObserverService.m874$$Nest$mhandleUsageStatsChanged(
                                VDCObserverService.this, 2, i2, componentName);
                    }
                }

                public final void noteResumeComponent(
                        ComponentName componentName, Intent intent, int i, int i2) {
                    if (componentName != null) {
                        VDCObserverService.m874$$Nest$mhandleUsageStatsChanged(
                                VDCObserverService.this, 1, i2, componentName);
                    }
                }

                public final void noteStopComponent(
                        ComponentName componentName, Intent intent, int i, int i2) {
                    if (componentName != null) {
                        VDCObserverService.m874$$Nest$mhandleUsageStatsChanged(
                                VDCObserverService.this, 23, i2, componentName);
                    }
                }
            };
    public final MyHandler mHandler =
            new MyHandler(
                    KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("VDCObserverService")
                            .getLooper());

    /* renamed from: com.android.server.sepunion.VDCObserverService$2, reason: invalid class name */
    public final class AnonymousClass2 extends BroadcastReceiver {
        public static final /* synthetic */ int $r8$clinit = 0;

        public AnonymousClass2() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, final Intent intent) {
            VDCObserverService.this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.sepunion.VDCObserverService$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            VDCObserverService.AnonymousClass2 anonymousClass2 =
                                    VDCObserverService.AnonymousClass2.this;
                            Intent intent2 = intent;
                            int i = VDCObserverService.AnonymousClass2.$r8$clinit;
                            anonymousClass2.getClass();
                            Log.d("VDCObserverService", "onReceive: " + intent2.getAction());
                            if (Objects.equals(
                                    intent2.getAction(), "android.intent.action.USER_UNLOCKED")) {
                                UserManager userManager =
                                        (UserManager)
                                                VDCObserverService.this.mContext.getSystemService(
                                                        "user");
                                if (userManager == null || !userManager.isUserUnlocked()) {
                                    Log.w(
                                            "VDCObserverService",
                                            "registerUsageStatsWatcher: failed");
                                } else {
                                    VDCObserverService.m875$$Nest$mregisterUsageStatsWatcher(
                                            VDCObserverService.this);
                                }
                            }
                        }
                    });
        }
    }

    public final class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("VDC thread msg "), message.what, "VDCObserverService");
            int i = message.what;
            VDCObserverService vDCObserverService = VDCObserverService.this;
            if (i == 10) {
                Bundle data = message.getData();
                Uri uri = VDCObserverService.VDC_OBSERVER_URI;
                vDCObserverService.getClass();
                try {
                    vDCObserverService
                            .mContext
                            .getContentResolver()
                            .call(
                                    VDCObserverService.VDC_OBSERVER_URI,
                                    "onForegroundServiceStateChanged",
                                    (String) null,
                                    data);
                    return;
                } catch (IllegalArgumentException e) {
                    Log.e("VDCObserverService", "onFgServiceStateChanged call failed: ", e);
                    return;
                }
            }
            if (i == 20) {
                Bundle data2 = message.getData();
                Uri uri2 = VDCObserverService.VDC_OBSERVER_URI;
                vDCObserverService.getClass();
                try {
                    Log.d("VDCObserverService", "onUsageStatsChanged");
                    vDCObserverService
                            .mContext
                            .getContentResolver()
                            .call(
                                    VDCObserverService.VDC_OBSERVER_URI,
                                    "onUsageStatsChanged",
                                    (String) null,
                                    data2);
                    return;
                } catch (IllegalArgumentException e2) {
                    Log.e("VDCObserverService", "onUsageStatsChanged call failed: ", e2);
                    return;
                }
            }
            if (i != 30) {
                return;
            }
            Uri uri3 = VDCObserverService.VDC_OBSERVER_URI;
            vDCObserverService.getClass();
            Log.d("VDCObserverService", "unregisterUsageStatsWatcher");
            UsageStatsManager usageStatsManager =
                    (UsageStatsManager) vDCObserverService.mContext.getSystemService("usagestats");
            if (usageStatsManager != null) {
                usageStatsManager.unregisterUsageStatsWatcher(
                        vDCObserverService.mUsageStatsWatcher);
                vDCObserverService.mObserverRegisterState &= -9;
            } else {
                Log.e("VDCObserverService", "get UsageStatsManager null");
            }
            VDCObserverService.m875$$Nest$mregisterUsageStatsWatcher(vDCObserverService);
        }
    }

    public interface ObserverType {}

    /* renamed from: -$$Nest$mhandleUsageStatsChanged, reason: not valid java name */
    public static void m874$$Nest$mhandleUsageStatsChanged(
            VDCObserverService vDCObserverService, int i, int i2, ComponentName componentName) {
        vDCObserverService.getClass();
        Message obtain = Message.obtain();
        obtain.what = 20;
        Bundle bundle = new Bundle();
        bundle.putInt("event_type", i);
        bundle.putInt("user_id", i2);
        bundle.putString("pkg_name", componentName.getPackageName());
        bundle.putString("class_name", componentName.getClassName());
        obtain.setData(bundle);
        vDCObserverService.mHandler.sendMessage(obtain);
    }

    /* renamed from: -$$Nest$mregisterUsageStatsWatcher, reason: not valid java name */
    public static void m875$$Nest$mregisterUsageStatsWatcher(
            VDCObserverService vDCObserverService) {
        vDCObserverService.getClass();
        ArrayList arrayList = new ArrayList();
        ContentResolver contentResolver = vDCObserverService.mContext.getContentResolver();
        if (contentResolver != null) {
            try {
                Cursor query =
                        contentResolver.query(
                                Uri.withAppendedPath(VDC_OBSERVER_URI, "video_call_app_info"),
                                null,
                                null,
                                null);
                if (query != null) {
                    try {
                        if (query.getCount() > 0) {
                            Log.d(
                                    "VDCObserverService",
                                    "contentResolver query: " + query.getCount());
                            int columnIndex = query.getColumnIndex("package_name");
                            int columnIndex2 = query.getColumnIndex("activities_name");
                            if (columnIndex >= 0 && columnIndex2 >= 0) {
                                while (query.moveToNext()) {
                                    String string = query.getString(columnIndex);
                                    String string2 = query.getString(columnIndex2);
                                    if (string2 != null
                                            && !string2.isEmpty()
                                            && !string2.equals("null")) {
                                        for (String str :
                                                string2.substring(1, string2.length() - 1)
                                                        .replace("\"", "")
                                                        .split(",")) {
                                            if (str != null && !str.isEmpty()) {
                                                arrayList.add(new ComponentName(string, str));
                                            }
                                        }
                                    }
                                }
                            }
                            query.close();
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
            if (arrayList != null || arrayList.isEmpty()) {
                Log.w("VDCObserverService", "registerUsageStatsWatcher: fail");
            }
            Log.d("VDCObserverService", "registerUsageStatsWatcher: " + arrayList);
            UsageStatsManager usageStatsManager =
                    (UsageStatsManager) vDCObserverService.mContext.getSystemService("usagestats");
            if (usageStatsManager == null) {
                Log.e("VDCObserverService", "get UsageStatsManager null");
                return;
            }
            usageStatsManager.registerUsageStatsWatcher(
                    vDCObserverService.mUsageStatsWatcher, arrayList);
            vDCObserverService.mObserverRegisterState |= 8;
            vDCObserverService.mRegisteredComponentList = arrayList;
            return;
        }
        Log.e("VDCObserverService", "get ContentResolver null");
        arrayList = null;
        if (arrayList != null) {}
        Log.w("VDCObserverService", "registerUsageStatsWatcher: fail");
    }

    public VDCObserverService(Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder m$1 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                        printWriter,
                        "\n"
                            + "##### VDCObserverService #####\n"
                            + "##### (dumpsys sepunion VDCObserverService) #####\n",
                        "Observer register state: ");
        m$1.append(Integer.toBinaryString(this.mObserverRegisterState));
        printWriter.println(m$1.toString());
        StringBuilder sb = new StringBuilder("componentList: ");
        List list = this.mRegisteredComponentList;
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
                                     // com.android.server.sepunion.VDCObserverService$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            final VDCObserverService vDCObserverService = VDCObserverService.this;
                            Uri uri = VDCObserverService.VDC_OBSERVER_URI;
                            vDCObserverService.getClass();
                            Log.d("VDCObserverService", "init");
                            Log.d("VDCObserverService", "registerUserUnlockedObserver");
                            if (vDCObserverService.mContext.getContentResolver() != null) {
                                vDCObserverService.mContext.registerReceiver(
                                        vDCObserverService.new AnonymousClass2(),
                                        new IntentFilter("android.intent.action.USER_UNLOCKED"));
                                vDCObserverService.mObserverRegisterState |= 1;
                            } else {
                                Log.e("VDCObserverService", "get ContentResolver null");
                            }
                            try {
                                Log.d("VDCObserverService", "registerForegroundServiceObserver");
                                ActivityManager.getService()
                                        .registerForegroundServiceObserver(
                                                vDCObserverService.mForegroundServiceObserver);
                                vDCObserverService.mObserverRegisterState |= 2;
                            } catch (RemoteException | SecurityException e) {
                                Log.e(
                                        "VDCObserverService",
                                        "registerForegroundServiceObserver: failed ",
                                        e);
                            }
                            ContentObserver contentObserver =
                                    new ContentObserver(
                                            vDCObserverService
                                                    .mHandler) { // from class:
                                                                 // com.android.server.sepunion.VDCObserverService.4
                                        @Override // android.database.ContentObserver
                                        public final void onChange(boolean z, Uri uri2) {
                                            if (uri2 == null) {
                                                return;
                                            }
                                            VDCObserverService.this.mHandler.removeMessages(30);
                                            VDCObserverService.this.mHandler.sendEmptyMessage(30);
                                        }
                                    };
                            ContentResolver contentResolver =
                                    vDCObserverService.mContext.getContentResolver();
                            if (contentResolver == null) {
                                Log.e("VDCObserverService", "get ContentResolver null");
                            } else {
                                contentResolver.registerContentObserver(
                                        VDCObserverService.VDC_OBSERVER_URI, true, contentObserver);
                                vDCObserverService.mObserverRegisterState |= 4;
                            }
                        }
                    });
        }
    }

    public final void onCleanupUser(int i) {}

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onCreate(Bundle bundle) {
        Log.d("VDCObserverService", "onCreate");
    }

    public final void onDestroy() {
        Log.d("VDCObserverService", "onDestroy");
    }

    public final void onStart() {
        Log.d("VDCObserverService", "onStart");
    }

    public final void onStartUser(int i) {}

    public final void onStopUser(int i) {}

    public final void onSwitchUser(int i) {}

    public final void onUnlockUser(int i) {}
}
