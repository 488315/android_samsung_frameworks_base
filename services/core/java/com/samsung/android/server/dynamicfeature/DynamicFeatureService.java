package com.samsung.android.server.dynamicfeature;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Slog;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;

import com.samsung.android.provider.Feature;
import com.samsung.android.provider.IDynamicFeatureManager;
import com.samsung.android.provider.SemDynamicFeature;
import com.samsung.android.server.dynamicfeature.db.DynamicFeatureDBHelper;
import com.samsung.android.server.dynamicfeature.dlog.Dlog;
import com.samsung.android.server.dynamicfeature.json.JsonParser;
import com.samsung.android.server.dynamicfeature.network.HttpConnector;
import com.samsung.android.server.dynamicfeature.sentinel.Tracker;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.Callable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DynamicFeatureService extends IDynamicFeatureManager.Stub {
    public static final ArrayList ALLOWED_MCC = new ArrayList(Arrays.asList("450"));
    public static DFeature mCurrentBroadcastFeature;
    public final Butler mButler;
    public final Context mContext;
    public final SQLiteDatabase mDb;
    public final DynamicFeatureDBHelper mDbHelper;
    public ArrayList mDiff;
    public final AnonymousClass2 mDynamicFeatureReceiver;
    public ArrayList mFeatures;
    public final HttpConnector mHttpConnector;
    public String mPrefix;
    public PrintWriter mPw;
    public ArrayList mRemoved;
    public final Tracker mTracker;
    public ArrayList nextFeatures;
    public final JobSchedulerRunnable requestJobSchedulerRunnable;
    public long testOnlyBackupTermHandling;
    public AnonymousClass2 testOnlyReceiver;
    public final AnonymousClass3 updateRunnable;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public boolean isWorking = false;
    public int mStopUpdateRequest = 0;
    public boolean mRequestUpdate = false;
    public final boolean mPreventFlooding = true;
    public String mPrevMcc = "";
    public final AnonymousClass1 mFeatureServiceCallBack = new AnonymousClass1();
    public final AnonymousClass3 updateOnceRunnable = new AnonymousClass3(this, 1);
    public final AnonymousClass3 postNotifyUpdateRunnable = new AnonymousClass3(this, 2);
    public int mStage = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.android.server.dynamicfeature.DynamicFeatureService$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}

        public final void onFeatureUpdateComplete(String str, boolean z) {
            DynamicFeatureService dynamicFeatureService = DynamicFeatureService.this;
            dynamicFeatureService.getClass();
            if (z) {
                Iterator it = dynamicFeatureService.mFeatures.iterator();
                while (it.hasNext()) {
                    DFeature dFeature = (DFeature) it.next();
                    if (dFeature.abType != null) {
                        DFeature.CircularQueue circularQueue = dFeature.abComment;
                        circularQueue.size = 0;
                        circularQueue.front = 0;
                        circularQueue.rear = -1;
                    }
                }
                if (InfoBoard.sParamDirty) {
                    DynamicFeatureDBHelper dynamicFeatureDBHelper = dynamicFeatureService.mDbHelper;
                    SQLiteDatabase sQLiteDatabase = dynamicFeatureService.mDb;
                    dynamicFeatureDBHelper.getClass();
                    DynamicFeatureDBHelper.replaceParamInfo(sQLiteDatabase);
                }
            } else if (InfoBoard.sParamDirty) {
                DynamicFeatureDBHelper dynamicFeatureDBHelper2 = dynamicFeatureService.mDbHelper;
                SQLiteDatabase sQLiteDatabase2 = dynamicFeatureService.mDb;
                dynamicFeatureDBHelper2.getClass();
                DynamicFeatureDBHelper.loadParams(sQLiteDatabase2);
            }
            if (str == null || str.length() <= 0) {
                return;
            }
            dynamicFeatureService.updateFeature(str);
        }

        public final void onSignalFire(final String str) {
            final DFeature dFeature;
            DynamicFeatureService dynamicFeatureService = DynamicFeatureService.this;
            final HttpConnector httpConnector = dynamicFeatureService.mHttpConnector;
            AnonymousClass1 anonymousClass1 = dynamicFeatureService.mFeatureServiceCallBack;
            synchronized (DynamicFeatureService.class) {
                dFeature = DynamicFeatureService.mCurrentBroadcastFeature;
            }
            try {
                if (!((Boolean)
                                httpConnector
                                        .mExecutorService
                                        .submit(
                                                new Callable() { // from class:
                                                    // com.samsung.android.server.dynamicfeature.network.HttpConnector$$ExternalSyntheticLambda1
                                                    @Override // java.util.concurrent.Callable
                                                    public final Object call() {
                                                        String str2 = str;
                                                        HttpConnector httpConnector2 =
                                                                HttpConnector.this;
                                                        httpConnector2.getClass();
                                                        OutputStream outputStream = null;
                                                        boolean z = false;
                                                        try {
                                                            try {
                                                                try {
                                                                    String str3 =
                                                                            "https://dynamicfeature.dvc.samsung.com/dynamic-feature/error-report"
                                                                                    + httpConnector2
                                                                                            .getParams(
                                                                                                    true);
                                                                    Slog.d(
                                                                            "dynamicfeature_HttpConnector",
                                                                            "error report url is "
                                                                                    + str3);
                                                                    JSONObject jSONObject =
                                                                            new JSONObject();
                                                                    DFeature dFeature2 = dFeature;
                                                                    Object obj = "";
                                                                    jSONObject.put(
                                                                            "namespace",
                                                                            dFeature2 == null
                                                                                    ? ""
                                                                                    : dFeature2
                                                                                            .namespace);
                                                                    jSONObject.put(
                                                                            "name",
                                                                            dFeature2 == null
                                                                                    ? ""
                                                                                    : dFeature2
                                                                                            .name);
                                                                    if (dFeature2 != null) {
                                                                        obj =
                                                                                Integer.valueOf(
                                                                                        dFeature2
                                                                                                .version);
                                                                    }
                                                                    jSONObject.put("version", obj);
                                                                    jSONObject.put(
                                                                            "errorMessage", str2);
                                                                    Slog.d(
                                                                            "dynamicfeature_HttpConnector",
                                                                            "error report payload :"
                                                                                + " "
                                                                                    + jSONObject
                                                                                            .toString());
                                                                    Dlog.event(
                                                                            "error report payload :"
                                                                                + " "
                                                                                    + jSONObject
                                                                                            .toString());
                                                                    HttpURLConnection connection =
                                                                            HttpConnector
                                                                                    .getConnection(
                                                                                            str3);
                                                                    OutputStream outputStream2 =
                                                                            connection
                                                                                    .getOutputStream();
                                                                    byte[] bytes =
                                                                            jSONObject
                                                                                    .toString()
                                                                                    .getBytes(
                                                                                            "utf-8");
                                                                    outputStream2.write(
                                                                            bytes, 0, bytes.length);
                                                                    int responseCode =
                                                                            connection
                                                                                    .getResponseCode();
                                                                    httpConnector2.mLastResultCode =
                                                                            responseCode;
                                                                    if (responseCode != 200) {
                                                                        Slog.e(
                                                                                "dynamicfeature_HttpConnector",
                                                                                "Error on"
                                                                                    + " connection"
                                                                                    + " : "
                                                                                        + responseCode);
                                                                    } else {
                                                                        Slog.d(
                                                                                "dynamicfeature_HttpConnector",
                                                                                "Error committed ");
                                                                        z = true;
                                                                    }
                                                                    outputStream2.close();
                                                                } catch (Exception e) {
                                                                    Slog.e(
                                                                            "dynamicfeature_HttpConnector",
                                                                            "sendErrorReport"
                                                                                + " failed: "
                                                                                    + e
                                                                                            .getMessage());
                                                                    if (0 != 0) {
                                                                        outputStream.close();
                                                                    }
                                                                }
                                                            } catch (IOException e2) {
                                                                BootReceiver$$ExternalSyntheticOutline0
                                                                        .m(
                                                                                "Cannot close"
                                                                                    + " handle : ",
                                                                                e2,
                                                                                "dynamicfeature_HttpConnector");
                                                            }
                                                            return Boolean.valueOf(z);
                                                        } catch (Throwable th) {
                                                            if (0 != 0) {
                                                                try {
                                                                    outputStream.close();
                                                                } catch (IOException e3) {
                                                                    BootReceiver$$ExternalSyntheticOutline0
                                                                            .m(
                                                                                    "Cannot close"
                                                                                        + " handle"
                                                                                        + " : ",
                                                                                    e3,
                                                                                    "dynamicfeature_HttpConnector");
                                                                }
                                                            }
                                                            throw th;
                                                        }
                                                    }
                                                })
                                        .get())
                        .booleanValue()) {
                    Slog.e("dynamicfeature_HttpConnector", "Fail to commit error log");
                }
                anonymousClass1.getClass();
            } catch (Exception e) {
                NandswapManager$$ExternalSyntheticOutline0.m(
                        e,
                        new StringBuilder("requestSendErrorConnection error "),
                        "dynamicfeature_HttpConnector");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.android.server.dynamicfeature.DynamicFeatureService$2, reason: invalid class name */
    public final class AnonymousClass2 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ DynamicFeatureService this$0;

        public /* synthetic */ AnonymousClass2(DynamicFeatureService dynamicFeatureService, int i) {
            this.$r8$classId = i;
            this.this$0 = dynamicFeatureService;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:62:0x007f, code lost:

           if (r2.equals("com.samsung.feature.FORCE_START") == false) goto L20;
        */
        @Override // android.content.BroadcastReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onReceive(android.content.Context r7, android.content.Intent r8) {
            /*
                Method dump skipped, instructions count: 472
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.server.dynamicfeature.DynamicFeatureService.AnonymousClass2.onReceive(android.content.Context,"
                        + " android.content.Intent):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.android.server.dynamicfeature.DynamicFeatureService$3, reason: invalid class name */
    public final class AnonymousClass3 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ DynamicFeatureService this$0;

        public /* synthetic */ AnonymousClass3(DynamicFeatureService dynamicFeatureService, int i) {
            this.$r8$classId = i;
            this.this$0 = dynamicFeatureService;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.updateFeatureViaServer();
                    DynamicFeatureService dynamicFeatureService = this.this$0;
                    dynamicFeatureService.mHandler.postDelayed(
                            dynamicFeatureService.updateRunnable,
                            InfoBoard.basicInfo.jobIntervalMill * 60000);
                    break;
                case 1:
                    this.this$0.updateFeatureViaServer();
                    break;
                default:
                    try {
                        DynamicFeatureService.setCurrentBroadcastFeature(null);
                        DynamicFeatureService.m1228$$Nest$mreleaseUpdate(this.this$0);
                        DynamicFeatureService dynamicFeatureService2 = this.this$0;
                        dynamicFeatureService2.mContext.unregisterReceiver(
                                dynamicFeatureService2.mTracker);
                        break;
                    } catch (Exception e) {
                        NandswapManager$$ExternalSyntheticOutline0.m(
                                e, new StringBuilder("FAIL :"), "dynamicfeature_Service");
                    }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeliveryRunnable implements Runnable {
        public DFeature feature;
        public int state;

        public DeliveryRunnable() {}

        @Override // java.lang.Runnable
        public final void run() {
            if (this.feature == null) {
                Slog.e("dynamicfeature_Service", "No feature to broadcast");
                return;
            }
            Intent intent = new Intent(this.feature.getIntentAction());
            DynamicFeatureService.setCurrentBroadcastFeature(this.feature);
            this.feature.loadCargo(intent, this.state == 2);
            DynamicFeatureService.this.mContext.sendBroadcast(intent);
            Slog.d("dynamicfeature_Service", "send broadcast : " + intent.getAction());
            Dlog.event("send broadcast : " + intent.getAction());
            if (this.state == 2) {
                String persistPropertyKey = this.feature.getPersistPropertyKey();
                try {
                    InfoBoard.removeProperty(persistPropertyKey);
                    return;
                } catch (Exception unused) {
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            "Fail to set property : ",
                            persistPropertyKey,
                            "dynamicfeature_Service");
                    return;
                }
            }
            String persistPropertyKey2 = this.feature.getPersistPropertyKey();
            try {
                SystemProperties.set(persistPropertyKey2, this.feature.value);
            } catch (Exception unused2) {
                BootReceiver$$ExternalSyntheticOutline0.m(
                        "Fail to set property : ", persistPropertyKey2, "dynamicfeature_Service");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class JobSchedulerRunnable implements Runnable {
        public final DynamicFeatureService mService;

        public JobSchedulerRunnable(DynamicFeatureService dynamicFeatureService) {
            this.mService = dynamicFeatureService;
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                CheckUpdateJobService.scheduleDynamicFeatureScheduler(
                        DynamicFeatureService.this.mContext, this.mService);
            } catch (Exception e) {
                NandswapManager$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("FAIL :"), "dynamicfeature_Service");
            }
        }
    }

    /* renamed from: -$$Nest$mreleaseUpdate, reason: not valid java name */
    public static void m1228$$Nest$mreleaseUpdate(DynamicFeatureService dynamicFeatureService) {
        dynamicFeatureService.getClass();
        Dlog.event("applyDiff want to releaseUpdate");
        synchronized ("dynamicfeature_Service") {
            try {
                int i = dynamicFeatureService.mStopUpdateRequest - 1;
                dynamicFeatureService.mStopUpdateRequest = i;
                if (i == 0 && dynamicFeatureService.mRequestUpdate) {
                    dynamicFeatureService.mHandler.post(dynamicFeatureService.updateOnceRunnable);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x012b, code lost:

       if (r5 == null) goto L30;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DynamicFeatureService(android.content.Context r9) {
        /*
            Method dump skipped, instructions count: 440
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.server.dynamicfeature.DynamicFeatureService.<init>(android.content.Context):void");
    }

    public static void ASSERT(String str, boolean z) {
        if (!z) {
            throw new Exception(str);
        }
    }

    public static synchronized void setCurrentBroadcastFeature(DFeature dFeature) {
        synchronized (DynamicFeatureService.class) {
            Slog.d("dynamicfeature_Service", "setCurrentBroadcastFeature :" + dFeature);
            mCurrentBroadcastFeature = dFeature;
        }
    }

    public final void applyDiff() {
        ArrayList arrayList = this.mFeatures;
        ArrayList arrayList2 = this.nextFeatures;
        ArrayList arrayList3 = new ArrayList();
        int i = 0;
        if (arrayList == null || arrayList.size() == 0) {
            arrayList3 = (ArrayList) arrayList2.clone();
        } else {
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                DFeature dFeature = (DFeature) it.next();
                Iterator it2 = arrayList.iterator();
                boolean z = false;
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    DFeature dFeature2 = (DFeature) it2.next();
                    if (dFeature2.isThis(dFeature.namespace, dFeature.name)) {
                        if (dFeature.version > dFeature2.version) {
                            arrayList3.add(dFeature);
                            z = true;
                            break;
                        }
                        z = true;
                    }
                }
                if (!z) {
                    arrayList3.add(dFeature);
                }
            }
        }
        this.mDiff = arrayList3;
        ArrayList arrayList4 = this.mFeatures;
        ArrayList arrayList5 = this.nextFeatures;
        ArrayList arrayList6 = new ArrayList();
        Iterator it3 = arrayList4.iterator();
        while (it3.hasNext()) {
            DFeature dFeature3 = (DFeature) it3.next();
            Iterator it4 = arrayList5.iterator();
            while (true) {
                if (!it4.hasNext()) {
                    arrayList6.add(dFeature3);
                    break;
                } else {
                    DFeature dFeature4 = (DFeature) it4.next();
                    if (dFeature3.isThis(dFeature4.namespace, dFeature4.name)) {
                        break;
                    }
                }
            }
        }
        this.mRemoved = arrayList6;
        this.mFeatures = this.nextFeatures;
        this.mContext.registerReceiver(
                this.mTracker,
                BatteryService$$ExternalSyntheticOutline0.m(
                        "android.intent.action.DROPBOX_ENTRY_ADDED"),
                2);
        Dlog.event("applyDiff want to blockUpdate");
        synchronized ("dynamicfeature_Service") {
            this.mStopUpdateRequest++;
        }
        if (this.mPreventFlooding) {
            ArrayList arrayList7 = this.mDiff;
            if (arrayList7 == null && this.mRemoved == null) {
                Slog.e("dynamicfeature_Service", "No changes for intent");
                return;
            }
            if (arrayList7 != null) {
                Iterator it5 = arrayList7.iterator();
                while (it5.hasNext()) {
                    DFeature dFeature5 = (DFeature) it5.next();
                    DeliveryRunnable deliveryRunnable = new DeliveryRunnable();
                    deliveryRunnable.feature = dFeature5;
                    deliveryRunnable.state = 1;
                    StringBuilder sb = new StringBuilder("Start runnable for +");
                    sb.append(dFeature5.name);
                    sb.append(" after ");
                    long j = i;
                    sb.append(InfoBoard.TERM_HANDLING_FEATURE * j);
                    Slog.d("dynamicfeature_Service", sb.toString());
                    i++;
                    this.mHandler.postDelayed(
                            deliveryRunnable, InfoBoard.TERM_HANDLING_FEATURE * j);
                }
            }
            ArrayList arrayList8 = this.mRemoved;
            if (arrayList8 != null) {
                Iterator it6 = arrayList8.iterator();
                while (it6.hasNext()) {
                    DFeature dFeature6 = (DFeature) it6.next();
                    DeliveryRunnable deliveryRunnable2 = new DeliveryRunnable();
                    deliveryRunnable2.feature = dFeature6;
                    deliveryRunnable2.state = 2;
                    StringBuilder sb2 = new StringBuilder("Start runnable for +");
                    sb2.append(dFeature6.name);
                    sb2.append(" after ");
                    long j2 = i;
                    sb2.append(InfoBoard.TERM_HANDLING_FEATURE * j2);
                    Slog.d("dynamicfeature_Service", sb2.toString());
                    i++;
                    this.mHandler.postDelayed(
                            deliveryRunnable2, InfoBoard.TERM_HANDLING_FEATURE * j2);
                }
            }
            StringBuilder sb3 = new StringBuilder("Stop tracking after ");
            long j3 = i;
            sb3.append(InfoBoard.TERM_HANDLING_FEATURE * j3);
            Slog.d("dynamicfeature_Service", sb3.toString());
            this.mHandler.postDelayed(
                    this.postNotifyUpdateRunnable, InfoBoard.TERM_HANDLING_FEATURE * j3);
            return;
        }
        ArrayList arrayList9 = this.mDiff;
        if (arrayList9 == null && this.mRemoved == null) {
            Slog.e("dynamicfeature_Service", "No changes for intent");
        } else {
            if (arrayList9 != null) {
                Iterator it7 = arrayList9.iterator();
                while (it7.hasNext()) {
                    DFeature dFeature7 = (DFeature) it7.next();
                    Intent intent = new Intent(dFeature7.getIntentAction());
                    dFeature7.loadCargo(intent, false);
                    this.mContext.sendBroadcast(intent);
                    StringBuilder sb4 = new StringBuilder("send broadcast for new or edit : ");
                    sb4.append(intent.getAction());
                    SemDynamicFeature.Properties properties =
                            (SemDynamicFeature.Properties)
                                    intent.getParcelableExtra(
                                            "PROPERTY_CARGO", SemDynamicFeature.Properties.class);
                    if (properties == null) {
                        Slog.e("dynamicfeature_Feature", "The cargo is null : " + intent);
                    }
                    sb4.append(properties);
                    Slog.d("dynamicfeature_Service", sb4.toString());
                }
            }
            ArrayList arrayList10 = this.mRemoved;
            if (arrayList10 != null) {
                Iterator it8 = arrayList10.iterator();
                while (it8.hasNext()) {
                    DFeature dFeature8 = (DFeature) it8.next();
                    Intent intent2 = new Intent(dFeature8.getIntentAction());
                    this.mContext.sendBroadcast(intent2);
                    dFeature8.loadCargo(intent2, true);
                    Slog.d(
                            "dynamicfeature_Service",
                            "send broadcast for removed : " + intent2.getAction());
                }
            }
        }
        ArrayList arrayList11 = this.mDiff;
        if (arrayList11 == null && this.mRemoved == null) {
            Slog.e("dynamicfeature_Service", "No changes for intent");
            return;
        }
        if (arrayList11 != null) {
            Iterator it9 = arrayList11.iterator();
            while (it9.hasNext()) {
                DFeature dFeature9 = (DFeature) it9.next();
                String persistPropertyKey = dFeature9.getPersistPropertyKey();
                try {
                    SystemProperties.set(persistPropertyKey, dFeature9.value);
                } catch (Exception unused) {
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            "Fail to set property : ",
                            persistPropertyKey,
                            "dynamicfeature_Service");
                }
            }
        }
        ArrayList arrayList12 = this.mRemoved;
        if (arrayList12 != null) {
            Iterator it10 = arrayList12.iterator();
            while (it10.hasNext()) {
                String persistPropertyKey2 = ((DFeature) it10.next()).getPersistPropertyKey();
                try {
                    InfoBoard.removeProperty(persistPropertyKey2);
                } catch (Exception unused2) {
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            "Fail to set property : ",
                            persistPropertyKey2,
                            "dynamicfeature_Service");
                }
            }
        }
    }

    public final void applyNextToDB() {
        DynamicFeatureDBHelper dynamicFeatureDBHelper = this.mDbHelper;
        SQLiteDatabase sQLiteDatabase = this.mDb;
        dynamicFeatureDBHelper.getClass();
        sQLiteDatabase.delete("DF_FEATURE_TABLE", null, null);
        DynamicFeatureDBHelper dynamicFeatureDBHelper2 = this.mDbHelper;
        SQLiteDatabase sQLiteDatabase2 = this.mDb;
        ArrayList arrayList = this.nextFeatures;
        dynamicFeatureDBHelper2.getClass();
        sQLiteDatabase2.execSQL("DROP TABLE IF EXISTS DF_FEATURE_TABLE");
        sQLiteDatabase2.execSQL(
                "CREATE TABLE IF NOT EXISTS DF_FEATURE_TABLE (namespace TEXT,name TEXT,value"
                    + " TEXT,version INTEGER,property BOOLEAN,reboot BOOLEAN,package TEXT,"
                    + " signature TEXT, type TEXT,  PRIMARY KEY(namespace, name))");
        sQLiteDatabase2.execSQL(
                "CREATE TABLE IF NOT EXISTS DF_INFO_TABLE (_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " virtualid TEXT,jobIntervalMill INTEGER,usertrial TEXT,lastUpdateTime TEXT"
                    + " )");
        sQLiteDatabase2.execSQL(
                "CREATE TABLE IF NOT EXISTS DF_PARAM_TABLE (_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " mcc TEXT,mnc TEXT,csc TEXT,sdkVersion INTEGER,oneUiVersion TEXT, binaryType"
                    + " TEXT )");
        sQLiteDatabase2.beginTransaction();
        for (int i = 0; i < arrayList.size(); i++) {
            try {
                try {
                    DynamicFeatureDBHelper.insertFeature(
                            sQLiteDatabase2, (DFeature) arrayList.get(i));
                } catch (Exception e) {
                    Slog.e(DynamicFeatureDBHelper.TAG, e.getMessage());
                }
            } catch (Throwable th) {
                sQLiteDatabase2.endTransaction();
                throw th;
            }
        }
        sQLiteDatabase2.setTransactionSuccessful();
        sQLiteDatabase2.endTransaction();
    }

    public final synchronized void disableService() {
        CheckUpdateJobService.cancelJob();
        this.isWorking = false;
        Dlog.event("disableService");
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:139:0x03c4  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x03c8  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x05c2 A[LOOP:3: B:202:0x05bc->B:204:0x05c2, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:208:0x06c4  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x06cd  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x06d3  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x05e7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x02c8 A[LOOP:0: B:93:0x02c2->B:95:0x02c8, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x02d4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dump(
            java.io.FileDescriptor r27, java.io.PrintWriter r28, java.lang.String[] r29) {
        /*
            Method dump skipped, instructions count: 2442
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.server.dynamicfeature.DynamicFeatureService.dump(java.io.FileDescriptor,"
                    + " java.io.PrintWriter, java.lang.String[]):void");
    }

    public final void ee(String str) {
        String str2 = this.mPrefix;
        if (str2 == null) {
            str2 = "   ";
        }
        Slog.e("dynamicfeature_Service_test", str2 + str);
        PrintWriter printWriter = this.mPw;
        if (printWriter != null) {
            try {
                printWriter.println(str2 + "##### " + str);
                this.mPw.flush();
            } catch (Exception unused) {
            }
        }
    }

    public final synchronized void enableService() {
        this.mHandler.postDelayed(this.requestJobSchedulerRunnable, InfoBoard.getStartMilFromNow());
        this.isWorking = true;
        Dlog.event("enableService");
    }

    public final SemDynamicFeature.Properties getProperties(String str, String[] strArr) {
        if (!this.isWorking) {
            Slog.e("dynamicfeature_Service", "This is not working");
            return null;
        }
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        boolean z = strArr == null || strArr.length == 0;
        ArrayList arrayList = new ArrayList(Arrays.asList(strArr));
        ArrayList arrayList2 = new ArrayList();
        Iterator it = this.mFeatures.iterator();
        while (it.hasNext()) {
            DFeature dFeature = (DFeature) it.next();
            if (str.equals(dFeature.namespace) && (z || arrayList.contains(dFeature.name))) {
                arrayList2.add(new Feature(dFeature.name, dFeature.value, dFeature.abType != null));
            }
        }
        return new SemDynamicFeature.Properties(str, arrayList2);
    }

    public final void ll(String str) {
        String str2 = this.mPrefix;
        if (str2 == null) {
            str2 = "   ";
        }
        Slog.d("dynamicfeature_Service_test", str2 + str);
        PrintWriter printWriter = this.mPw;
        if (printWriter != null) {
            try {
                printWriter.println(str2 + str);
            } catch (Exception unused) {
            }
        }
    }

    public final void onFeatureListUpdated() {
        this.mButler.processNewFeature(this.mFeatures);
        if (InfoBoard.sDirty) {
            DynamicFeatureDBHelper dynamicFeatureDBHelper = this.mDbHelper;
            SQLiteDatabase sQLiteDatabase = this.mDb;
            dynamicFeatureDBHelper.getClass();
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS DF_INFO_TABLE");
            DynamicFeatureDBHelper dynamicFeatureDBHelper2 = this.mDbHelper;
            SQLiteDatabase sQLiteDatabase2 = this.mDb;
            dynamicFeatureDBHelper2.getClass();
            sQLiteDatabase2.execSQL(
                    "CREATE TABLE IF NOT EXISTS DF_INFO_TABLE (_ID INTEGER PRIMARY KEY"
                        + " AUTOINCREMENT, virtualid TEXT,jobIntervalMill INTEGER,usertrial"
                        + " TEXT,lastUpdateTime TEXT )");
            DynamicFeatureDBHelper dynamicFeatureDBHelper3 = this.mDbHelper;
            SQLiteDatabase sQLiteDatabase3 = this.mDb;
            dynamicFeatureDBHelper3.getClass();
            DynamicFeatureDBHelper.replaceBasicInfo(sQLiteDatabase3);
        }
        if (InfoBoard.sParamDirty) {
            DynamicFeatureDBHelper dynamicFeatureDBHelper4 = this.mDbHelper;
            SQLiteDatabase sQLiteDatabase4 = this.mDb;
            dynamicFeatureDBHelper4.getClass();
            DynamicFeatureDBHelper.replaceParamInfo(sQLiteDatabase4);
            InfoBoard.sParamDirty = false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0162 A[LOOP:2: B:66:0x00f0->B:85:0x0162, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x008a A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean sendAbTestResult(
            java.lang.String r21, java.lang.String r22, java.lang.String r23) {
        /*
            Method dump skipped, instructions count: 489
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.server.dynamicfeature.DynamicFeatureService.sendAbTestResult(java.lang.String,"
                    + " java.lang.String, java.lang.String):boolean");
    }

    public final boolean testCheckProperty(String str, String str2) {
        String str3 = SystemProperties.get(str, "problem");
        try {
            if (str2.equals(str3)) {
                return true;
            }
            ee("right value is:" + str2 + " def is:problem real value is:" + str3);
            return false;
        } catch (Exception e) {
            ll("testCheckProperty : " + e.getMessage());
            return false;
        }
    }

    public final void testSetUp() {
        synchronized (HttpConnector.class) {
            HttpConnector.isStopped++;
            Dlog.event(
                    "HttpConnector::stopUpdate "
                            + Dlog.getCallers(5)
                            + "  "
                            + HttpConnector.isStopped);
        }
        this.testOnlyBackupTermHandling = InfoBoard.TERM_HANDLING_FEATURE;
        InfoBoard.TERM_HANDLING_FEATURE = InfoBoard.TERM_HANDLING_TEST_FEATURE;
        this.testOnlyReceiver = new AnonymousClass2(this, 1);
        this.mContext.registerReceiver(
                this.testOnlyReceiver,
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        "com.sec.df.changed.SEC_UI.MODE_NIGHT",
                        "com.sec.df.changed.SEC_UI.MODE_ANIMATION"),
                2);
    }

    public final void testTearDown() {
        this.mContext.unregisterReceiver(this.testOnlyReceiver);
        synchronized (HttpConnector.class) {
            HttpConnector.isStopped--;
            Dlog.event(
                    "HttpConnector::resumeUpdate "
                            + Dlog.getCallers(5)
                            + "  "
                            + HttpConnector.isStopped);
        }
        InfoBoard.TERM_HANDLING_FEATURE = this.testOnlyBackupTermHandling;
    }

    public final void testVerifyIntent(Intent intent, String str, String str2) {
        ll("Intent1 received " + intent);
        SemDynamicFeature.Properties properties =
                (SemDynamicFeature.Properties)
                        intent.getParcelableExtra(
                                "PROPERTY_CARGO", SemDynamicFeature.Properties.class);
        ll("   Intent1 received properties " + properties);
        if (properties != null) {
            String string = properties.getString(str, "__default");
            if (str2 == null) {
                if ("__default".equals(string)) {
                    ll("no item : may " + str + " is removed");
                    return;
                }
            } else if (str2.equals(string)) {
                StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("   ", str, " : ");
                m.append(properties.getString(str, "problem"));
                ll(m.toString());
                return;
            }
            ee("   ##### testVerifyIntent: properties failed : " + string);
        } else if (str2 == null) {
            ll("   " + str + " is removed ");
            return;
        }
        ee("   ##### testVerifyIntent: properties failed " + properties);
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0064  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean testVerifyProperty() {
        /*
            r4 = this;
            int r0 = r4.mStage
            r1 = 0
            r2 = 1
            if (r0 != r2) goto L2d
            java.util.ArrayList r0 = r4.mFeatures
            java.lang.Object r0 = r0.get(r1)
            com.samsung.android.server.dynamicfeature.DFeature r0 = (com.samsung.android.server.dynamicfeature.DFeature) r0
            java.lang.String r0 = r0.getPersistPropertyKey()
            java.lang.String r1 = "0.2|0.9|false"
            boolean r0 = r4.testCheckProperty(r0, r1)
            java.util.ArrayList r1 = r4.mFeatures
            java.lang.Object r1 = r1.get(r2)
            com.samsung.android.server.dynamicfeature.DFeature r1 = (com.samsung.android.server.dynamicfeature.DFeature) r1
            java.lang.String r1 = r1.getPersistPropertyKey()
            java.lang.String r2 = "NEW_AIRDROP2"
            boolean r1 = r4.testCheckProperty(r1, r2)
        L2a:
            r2 = r0 & r1
            goto L4d
        L2d:
            r3 = 2
            if (r0 != r3) goto L4d
            java.util.ArrayList r0 = r4.mFeatures
            java.lang.Object r0 = r0.get(r1)
            com.samsung.android.server.dynamicfeature.DFeature r0 = (com.samsung.android.server.dynamicfeature.DFeature) r0
            java.lang.String r0 = r0.getPersistPropertyKey()
            java.lang.String r1 = "1|0.9|true"
            boolean r0 = r4.testCheckProperty(r0, r1)
            java.lang.String r1 = "persist.sys.df.SEC_UI.MODE_ANIMATION"
            java.lang.String r2 = "problem"
            boolean r1 = r4.testCheckProperty(r1, r2)
            goto L2a
        L4d:
            if (r2 == 0) goto L64
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "testVerifyProperty success : "
            r0.<init>(r1)
            int r1 = r4.mStage
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r4.ll(r0)
            goto L77
        L64:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "========= testVerifyProperty failed : "
            r0.<init>(r1)
            int r1 = r4.mStage
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r4.ll(r0)
        L77:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.server.dynamicfeature.DynamicFeatureService.testVerifyProperty():boolean");
    }

    public final void updateFeature(String str) {
        try {
            ArrayList converter = JsonParser.converter(str);
            this.nextFeatures = converter;
            if (converter == null) {
                Slog.d("dynamicfeature_Service", "No update from server");
                return;
            }
            if (DFeature.isSame(this.mFeatures, converter)) {
                Slog.d("dynamicfeature_Service", "Same as previous");
                return;
            }
            applyDiff();
            applyNextToDB();
            onFeatureListUpdated();
            Slog.d("dynamicfeature_Service", "Update complete");
        } catch (Exception e) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("updateFeature"), "dynamicfeature_Service");
        }
    }

    public final void updateFeatureViaServer() {
        Slog.d("dynamicfeature_Service", "Check updates from server.");
        synchronized ("dynamicfeature_Service") {
            synchronized (this) {
                synchronized ("dynamicfeature_Service") {
                    if (this.mStopUpdateRequest != 0) {
                        this.mRequestUpdate = true;
                        return;
                    }
                    Slog.d("dynamicfeature_Service", "Start updates from server.");
                    final HttpConnector httpConnector = this.mHttpConnector;
                    httpConnector.mFeatures = this.mFeatures;
                    AnonymousClass1 anonymousClass1 = this.mFeatureServiceCallBack;
                    try {
                        Pair pair =
                                (Pair)
                                        httpConnector
                                                .mExecutorService
                                                .submit(
                                                        new Callable() { // from class:
                                                            // com.samsung.android.server.dynamicfeature.network.HttpConnector$$ExternalSyntheticLambda0
                                                            /* JADX WARN: Can't wrap try/catch for region: R(16:16|17|18|(4:21|(5:26|27|(1:29)|30|31)|32|19)|37|38|(1:40)|41|42|43|(2:45|(5:47|48|49|50|(3:52|53|55)(1:59)))(6:65|66|67|(3:68|69|(1:71)(1:72))|73|74)|64|48|49|50|(0)(0)) */
                                                            /* JADX WARN: Code restructure failed: missing block: B:61:0x01ee, code lost:

                                                               r0 = move-exception;
                                                            */
                                                            /* JADX WARN: Code restructure failed: missing block: B:62:0x01ef, code lost:

                                                               com.android.server.BootReceiver$$ExternalSyntheticOutline0.m("Cannot close handle : ", r0, "dynamicfeature_HttpConnector");
                                                            */
                                                            /* JADX WARN: Multi-variable type inference failed */
                                                            /* JADX WARN: Removed duplicated region for block: B:52:0x01f8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                                                            /* JADX WARN: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
                                                            /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.String] */
                                                            /* JADX WARN: Type inference failed for: r0v1 */
                                                            /* JADX WARN: Type inference failed for: r0v11, types: [java.io.InputStreamReader] */
                                                            /* JADX WARN: Type inference failed for: r0v14 */
                                                            /* JADX WARN: Type inference failed for: r0v15 */
                                                            /* JADX WARN: Type inference failed for: r0v16 */
                                                            /* JADX WARN: Type inference failed for: r0v19 */
                                                            /* JADX WARN: Type inference failed for: r0v2 */
                                                            /* JADX WARN: Type inference failed for: r0v21 */
                                                            /* JADX WARN: Type inference failed for: r0v23 */
                                                            /* JADX WARN: Type inference failed for: r0v3 */
                                                            /* JADX WARN: Type inference failed for: r0v4 */
                                                            /* JADX WARN: Type inference failed for: r0v5, types: [java.io.InputStreamReader] */
                                                            /* JADX WARN: Type inference failed for: r0v7, types: [java.io.InputStreamReader] */
                                                            /* JADX WARN: Type inference failed for: r0v8 */
                                                            /* JADX WARN: Type inference failed for: r3v13, types: [java.lang.String] */
                                                            /* JADX WARN: Type inference failed for: r3v9, types: [java.lang.String] */
                                                            /* JADX WARN: Type inference failed for: r4v5, types: [org.json.JSONObject] */
                                                            @Override // java.util.concurrent.Callable
                                                            /*
                                                                Code decompiled incorrectly, please refer to instructions dump.
                                                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                                                            */
                                                            public final java.lang.Object call() {
                                                                /*
                                                                    Method dump skipped, instructions count: 691
                                                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                                                */
                                                                throw new UnsupportedOperationException(
                                                                        "Method not decompiled:"
                                                                            + " com.samsung.android.server.dynamicfeature.network.HttpConnector$$ExternalSyntheticLambda0.call():java.lang.Object");
                                                            }
                                                        })
                                                .get();
                        if (((String) pair.first).length() == 0) {
                            Slog.e("dynamicfeature_HttpConnector", "No result to update");
                        }
                        anonymousClass1.onFeatureUpdateComplete(
                                (String) pair.first, ((Boolean) pair.second).booleanValue());
                    } catch (Exception e) {
                        NandswapManager$$ExternalSyntheticOutline0.m(
                                e,
                                new StringBuilder("requestConnection error "),
                                "dynamicfeature_HttpConnector");
                    }
                }
            }
        }
    }
}
