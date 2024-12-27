package com.android.server.sepunion;

import android.app.ActivityManager;
import android.app.IProcessObserver;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.power.PowerHistorian;
import com.android.server.power.PowerHistorian$$ExternalSyntheticLambda0;
import com.android.server.power.PowerHistorian$$ExternalSyntheticLambda1;

import com.samsung.android.sepunion.IGoodCatchDispatcher;
import com.samsung.android.sepunion.IGoodCatchManager;
import com.samsung.android.sepunion.Log;
import com.samsung.android.sepunion.SemGoodCatchManager;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class SemGoodCatchService extends IGoodCatchManager.Stub
        implements AbsSemSystemService {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public FeatureDetectAds mFeatureDetectAds;
    public FeatureEvent mFeatureEvent;
    public FeatureSetting mFeatureSetting;
    public FeatureSettingsProvider mFeatureSettingsProvider;
    public FeatureWakeUp mFeatureWakeUp;
    public final HashMap mGoodCatchClients = new HashMap();
    public GoodCatchHandler mGoodCatchHandler;
    public GoodCatchObserver mGoodCatchObserver;
    public final AnonymousClass1 mIntentReceiver;

    public final class FeatureDetectAds extends SecFeature {
        public boolean mActiveState;
        public int mForegroundUid;
        public final IntentFilter mIntentFilter;
        public final AnonymousClass3 mOnStateListener;
        public final PackageManager mPackageManager;
        public int mPreviousUid;
        public final AnonymousClass1 mProcessObserver;
        public final AnonymousClass2 mReceiver;
        public SemGoodCatchManager mSemGoodCatchManager;

        /* JADX WARN: Type inference failed for: r1v2, types: [com.android.server.sepunion.SemGoodCatchService$FeatureDetectAds$1] */
        /* JADX WARN: Type inference failed for: r1v3, types: [com.android.server.sepunion.SemGoodCatchService$FeatureDetectAds$2] */
        /* JADX WARN: Type inference failed for: r1v4, types: [com.android.server.sepunion.SemGoodCatchService$FeatureDetectAds$3] */
        public FeatureDetectAds() {
            super("FeatureDetectAds");
            IntentFilter intentFilter = new IntentFilter();
            this.mIntentFilter = intentFilter;
            this.mActiveState = false;
            this.mPreviousUid = -1;
            this.mForegroundUid = -1;
            this.mProcessObserver =
                    new IProcessObserver
                            .Stub() { // from class:
                                      // com.android.server.sepunion.SemGoodCatchService.FeatureDetectAds.1
                        public final void onForegroundActivitiesChanged(int i, int i2, boolean z) {
                            String[] packagesForUid;
                            if (z) {
                                FeatureDetectAds featureDetectAds = FeatureDetectAds.this;
                                featureDetectAds.mForegroundUid = i2;
                                if (i2 == 1000) {
                                    featureDetectAds.getClass();
                                    packagesForUid = new String[] {"android"};
                                } else {
                                    packagesForUid =
                                            featureDetectAds.mPackageManager.getPackagesForUid(i2);
                                }
                                if (packagesForUid == null) {
                                    packagesForUid = new String[] {""};
                                }
                                String str = packagesForUid[0];
                                FeatureDetectAds featureDetectAds2 = FeatureDetectAds.this;
                                if (!featureDetectAds2.mActiveState
                                        || featureDetectAds2.mPreviousUid
                                                == featureDetectAds2.mForegroundUid) {
                                    return;
                                }
                                int i3 = SemGoodCatchService.$r8$clinit;
                                Log.d(
                                        "SemGoodCatchService",
                                        "mForegroundUid = "
                                                + FeatureDetectAds.this.mForegroundUid
                                                + "("
                                                + str
                                                + ")");
                                FeatureDetectAds.this.mSemGoodCatchManager.update(
                                        "detectads", str, 0, "", "");
                            }
                        }

                        public final void onForegroundServicesChanged(int i, int i2, int i3) {}

                        public final void onProcessDied(int i, int i2) {}

                        public final void onProcessStarted(
                                int i, int i2, int i3, String str, String str2) {}
                    };
            this.mReceiver =
                    new BroadcastReceiver() { // from class:
                                              // com.android.server.sepunion.SemGoodCatchService.FeatureDetectAds.2
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            String action = intent.getAction();
                            if (action.equals("android.intent.action.SCREEN_ON")) {
                                int i = SemGoodCatchService.$r8$clinit;
                                Log.d("SemGoodCatchService", "ACTION_SCREEN_ON");
                                SemGoodCatchService.sendMsg(
                                        SemGoodCatchService.this.mGoodCatchHandler, 4, null, 2000);
                            } else if (action.equals("android.intent.action.SCREEN_OFF")) {
                                int i2 = SemGoodCatchService.$r8$clinit;
                                Log.d("SemGoodCatchService", "ACTION_SCREEN_OFF");
                                FeatureDetectAds featureDetectAds = FeatureDetectAds.this;
                                featureDetectAds.mPreviousUid = featureDetectAds.mForegroundUid;
                                Log.d("SemGoodCatchService", "setActiveState, true");
                                featureDetectAds.mActiveState = true;
                            }
                        }
                    };
            this.mOnStateListener =
                    new SemGoodCatchManager
                            .OnStateChangeListener() { // from class:
                                                       // com.android.server.sepunion.SemGoodCatchService.FeatureDetectAds.3
                        public final void onStart(String str) {
                            int i = SemGoodCatchService.$r8$clinit;
                            Log.d("SemGoodCatchService", "onStart " + str);
                            FeatureDetectAds featureDetectAds = FeatureDetectAds.this;
                            if (featureDetectAds.mOn) {
                                return;
                            }
                            featureDetectAds.mOn = true;
                            featureDetectAds.process(true);
                        }

                        public final void onStop(String str) {
                            int i = SemGoodCatchService.$r8$clinit;
                            Log.d("SemGoodCatchService", "onStop " + str);
                            FeatureDetectAds featureDetectAds = FeatureDetectAds.this;
                            if (featureDetectAds.mOn) {
                                featureDetectAds.mOn = false;
                                featureDetectAds.process(false);
                            }
                        }
                    };
            this.mPackageManager = SemGoodCatchService.this.mContext.getPackageManager();
            SemGoodCatchService.this.mContext.registerReceiver(
                    new BroadcastReceiver() { // from class:
                        // com.android.server.sepunion.SemGoodCatchService.FeatureDetectAds.4
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            if (intent.getAction()
                                    .equals(
                                            "com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED")) {
                                FeatureDetectAds featureDetectAds = FeatureDetectAds.this;
                                FeatureDetectAds featureDetectAds2 = FeatureDetectAds.this;
                                featureDetectAds.mSemGoodCatchManager =
                                        new SemGoodCatchManager(
                                                SemGoodCatchService.this.mContext,
                                                "FeatureDetectAds",
                                                new String[] {"detectads"},
                                                featureDetectAds2.mOnStateListener);
                            }
                        }
                    },
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED"));
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
        }

        public final void process(boolean z) {
            AnonymousClass2 anonymousClass2 = this.mReceiver;
            AnonymousClass1 anonymousClass1 = this.mProcessObserver;
            SemGoodCatchService semGoodCatchService = SemGoodCatchService.this;
            try {
                if (z) {
                    semGoodCatchService.mContext.registerReceiver(
                            anonymousClass2, this.mIntentFilter);
                    ActivityManager.getService().registerProcessObserver(anonymousClass1);
                } else {
                    semGoodCatchService.mContext.unregisterReceiver(anonymousClass2);
                    ActivityManager.getService().unregisterProcessObserver(anonymousClass1);
                }
            } catch (Exception e) {
                int i = SemGoodCatchService.$r8$clinit;
                Log.e("SemGoodCatchService", "Exception - ProcessObserver");
                e.printStackTrace();
            }
        }
    }

    public final class FeatureEvent extends SecFeature {
        public FeatureEvent() {
            super("FeatureEvent");
        }
    }

    public final class FeatureSetting extends SecFeature {
        public FeatureSetting() {
            super("FeatureSetting");
            this.mOn = true;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addDataScheme("package");
            intentFilter.addDataSchemeSpecificPart("com.samsung.android.app.goodcatch", 0);
            SemGoodCatchService.this.mContext.registerReceiver(
                    new BroadcastReceiver() { // from class:
                        // com.android.server.sepunion.SemGoodCatchService.FeatureSetting.1
                        /* JADX WARN: Code restructure failed: missing block: B:36:0x00b8, code lost:

                           r4 = move-exception;
                        */
                        /* JADX WARN: Code restructure failed: missing block: B:39:0x00bd, code lost:

                           throw r4;
                        */
                        @Override // android.content.BroadcastReceiver
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final void onReceive(
                                android.content.Context r5, android.content.Intent r6) {
                            /*
                                r4 = this;
                                r5 = 0
                                if (r6 == 0) goto L18
                                java.lang.String r0 = r6.getAction()
                                android.net.Uri r1 = r6.getData()
                                if (r1 == 0) goto L16
                                android.net.Uri r6 = r6.getData()
                                java.lang.String r6 = r6.getSchemeSpecificPart()
                                goto L1a
                            L16:
                                r6 = r5
                                goto L1a
                            L18:
                                r6 = r5
                                r0 = r6
                            L1a:
                                int r1 = com.android.server.sepunion.SemGoodCatchService.$r8$clinit
                                java.lang.String r1 = "SemGoodCatchService"
                                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                                java.lang.String r3 = "intent received, action:"
                                r2.<init>(r3)
                                r2.append(r0)
                                java.lang.String r2 = r2.toString()
                                com.samsung.android.sepunion.Log.d(r1, r2)
                                java.lang.String r1 = "android.intent.action.PACKAGE_REMOVED"
                                boolean r0 = r1.equals(r0)
                                if (r0 == 0) goto Lbe
                                java.lang.String r0 = "com.samsung.android.app.goodcatch"
                                boolean r0 = r0.equals(r6)
                                if (r0 == 0) goto Lbe
                                java.lang.String r0 = "SemGoodCatchService"
                                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                                java.lang.String r2 = "package "
                                r1.<init>(r2)
                                r1.append(r6)
                                java.lang.String r6 = " removed"
                                r1.append(r6)
                                java.lang.String r6 = r1.toString()
                                com.samsung.android.sepunion.Log.d(r0, r6)
                                com.android.server.sepunion.SemGoodCatchService$FeatureSetting r6 = com.android.server.sepunion.SemGoodCatchService.FeatureSetting.this
                                r6.getClass()
                                java.lang.String r0 = "SemGoodCatchService"
                                java.lang.String r1 = "reset"
                                com.samsung.android.sepunion.Log.d(r0, r1)
                                r6.mUri = r5
                                com.android.server.sepunion.SemGoodCatchService$FeatureSetting r6 = com.android.server.sepunion.SemGoodCatchService.FeatureSetting.this
                                com.android.server.sepunion.SemGoodCatchService r6 = com.android.server.sepunion.SemGoodCatchService.this
                                com.android.server.sepunion.SemGoodCatchService$FeatureEvent r0 = r6.mFeatureEvent
                                r1 = 0
                                r0.mOn = r1
                                r0.mUri = r5
                                java.util.HashMap r5 = r6.mGoodCatchClients
                                monitor-enter(r5)
                                com.android.server.sepunion.SemGoodCatchService$FeatureSetting r4 = com.android.server.sepunion.SemGoodCatchService.FeatureSetting.this     // Catch: java.lang.Throwable -> Lb8
                                com.android.server.sepunion.SemGoodCatchService r4 = com.android.server.sepunion.SemGoodCatchService.this     // Catch: java.lang.Throwable -> Lb8
                                java.util.HashMap r4 = r4.mGoodCatchClients     // Catch: java.lang.Throwable -> Lb8
                                java.util.Collection r4 = r4.values()     // Catch: java.lang.Throwable -> Lb8
                                java.util.Iterator r4 = r4.iterator()     // Catch: java.lang.Throwable -> Lb8
                            L83:
                                boolean r6 = r4.hasNext()     // Catch: java.lang.Throwable -> Lb8
                                if (r6 == 0) goto Lba
                                java.lang.Object r6 = r4.next()     // Catch: java.lang.Throwable -> Lb8
                                com.android.server.sepunion.SemGoodCatchService$GoodCatchClient r6 = (com.android.server.sepunion.SemGoodCatchService.GoodCatchClient) r6     // Catch: java.lang.Throwable -> Lb8
                                java.util.HashMap r0 = r6.mFunctions     // Catch: java.lang.Throwable -> Lb8
                                monitor-enter(r0)     // Catch: java.lang.Throwable -> Lb8
                                java.util.HashMap r1 = r6.mFunctions     // Catch: java.lang.Throwable -> Lb2
                                java.util.Set r1 = r1.entrySet()     // Catch: java.lang.Throwable -> Lb2
                                java.util.Iterator r1 = r1.iterator()     // Catch: java.lang.Throwable -> Lb2
                            L9c:
                                boolean r2 = r1.hasNext()     // Catch: java.lang.Throwable -> Lb2
                                if (r2 == 0) goto Lb4
                                java.lang.Object r2 = r1.next()     // Catch: java.lang.Throwable -> Lb2
                                java.util.Map$Entry r2 = (java.util.Map.Entry) r2     // Catch: java.lang.Throwable -> Lb2
                                java.lang.Object r2 = r2.getKey()     // Catch: java.lang.Throwable -> Lb2
                                java.lang.String r2 = (java.lang.String) r2     // Catch: java.lang.Throwable -> Lb2
                                r6.off(r2)     // Catch: java.lang.Throwable -> Lb2
                                goto L9c
                            Lb2:
                                r4 = move-exception
                                goto Lb6
                            Lb4:
                                monitor-exit(r0)     // Catch: java.lang.Throwable -> Lb2
                                goto L83
                            Lb6:
                                monitor-exit(r0)     // Catch: java.lang.Throwable -> Lb2
                                throw r4     // Catch: java.lang.Throwable -> Lb8
                            Lb8:
                                r4 = move-exception
                                goto Lbc
                            Lba:
                                monitor-exit(r5)     // Catch: java.lang.Throwable -> Lb8
                                goto Lbe
                            Lbc:
                                monitor-exit(r5)     // Catch: java.lang.Throwable -> Lb8
                                throw r4
                            Lbe:
                                return
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.server.sepunion.SemGoodCatchService.FeatureSetting.AnonymousClass1.onReceive(android.content.Context,"
                                        + " android.content.Intent):void");
                        }
                    },
                    intentFilter);
        }
    }

    public final class FeatureSettingsProvider extends SecFeature {
        public FeatureSettingsProvider() {
            super("FeatureSettingsProvider");
        }
    }

    public final class FeatureWakeUp extends SecFeature {
        public final AnonymousClass1 mOnStateListener;

        /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.sepunion.SemGoodCatchService$FeatureWakeUp$1] */
        public FeatureWakeUp() {
            super("FeatureWakeUp");
            this.mOnStateListener =
                    new SemGoodCatchManager.OnStateChangeListener() { // from class:
                        // com.android.server.sepunion.SemGoodCatchService.FeatureWakeUp.1
                        public final void onStart(String str) {
                            int i = SemGoodCatchService.$r8$clinit;
                            Log.d("SemGoodCatchService", "onStart " + str);
                            final FeatureWakeUp featureWakeUp = FeatureWakeUp.this;
                            featureWakeUp.mOn = true;
                            new LinkedList(
                                            (List)
                                                    ((PowerHistorian.RecordBuffer)
                                                                    PowerHistorian.INSTANCE
                                                                            .mRecordCache.get(1))
                                                            .mBuffer.stream()
                                                                    .filter(
                                                                            new PowerHistorian$$ExternalSyntheticLambda0())
                                                                    .map(
                                                                            new PowerHistorian$$ExternalSyntheticLambda1())
                                                                    .collect(Collectors.toList()))
                                    .forEach(
                                            new Consumer() { // from class:
                                                // com.android.server.sepunion.SemGoodCatchService$FeatureWakeUp$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Consumer
                                                public final void accept(Object obj) {
                                                    SemGoodCatchService.FeatureWakeUp
                                                            featureWakeUp2 =
                                                                    SemGoodCatchService
                                                                            .FeatureWakeUp.this;
                                                    PowerHistorian.WakeUpRecord wakeUpRecord =
                                                            (PowerHistorian.WakeUpRecord) obj;
                                                    featureWakeUp2.getClass();
                                                    if (wakeUpRecord.mIsOn) {
                                                        int i2 = SemGoodCatchService.$r8$clinit;
                                                        StringBuilder sb =
                                                                new StringBuilder(
                                                                        "MODULE : FeatureWakeUp,"
                                                                            + " FUNCTION : wakeup,"
                                                                            + " opPackageName : ");
                                                        String str2 = wakeUpRecord.mOpPackageName;
                                                        sb.append(str2);
                                                        sb.append(", reasonInt : ");
                                                        int i3 = wakeUpRecord.mReasonInt;
                                                        sb.append(i3);
                                                        sb.append(", reasonStr : ");
                                                        String str3 = wakeUpRecord.mReasonStr;
                                                        sb.append(str3);
                                                        sb.append(", foregroundPackageName : ");
                                                        String str4 =
                                                                wakeUpRecord.mForegroundPackageName;
                                                        sb.append(str4);
                                                        Log.d("SemGoodCatchService", sb.toString());
                                                        SemGoodCatchService.sendMsg(
                                                                SemGoodCatchService.this
                                                                        .mGoodCatchHandler,
                                                                2,
                                                                new String[] {
                                                                    "FeatureWakeUp",
                                                                    "wakeup",
                                                                    str2,
                                                                    Long.toString(
                                                                            wakeUpRecord
                                                                                    .mRecordedTimeMillis),
                                                                    Integer.toString(i3),
                                                                    str3,
                                                                    str4
                                                                },
                                                                0);
                                                    }
                                                }
                                            });
                            SemGoodCatchService.sendMsg(
                                    SemGoodCatchService.this.mGoodCatchHandler, 5, null, 0);
                        }

                        public final void onStop(String str) {
                            int i = SemGoodCatchService.$r8$clinit;
                            Log.d("SemGoodCatchService", "onStop " + str);
                            FeatureWakeUp.this.mOn = false;
                        }
                    };
            SemGoodCatchService.this.mContext.registerReceiver(
                    new BroadcastReceiver() { // from class:
                        // com.android.server.sepunion.SemGoodCatchService.FeatureWakeUp.2
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            if (intent.getAction()
                                    .equals(
                                            "com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED")) {
                                FeatureWakeUp featureWakeUp = FeatureWakeUp.this;
                                FeatureWakeUp featureWakeUp2 = FeatureWakeUp.this;
                                new SemGoodCatchManager(
                                        SemGoodCatchService.this.mContext,
                                        "FeatureWakeUp",
                                        new String[] {"wakeup"},
                                        featureWakeUp2.mOnStateListener);
                                featureWakeUp.getClass();
                            }
                        }
                    },
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED"));
        }
    }

    public final class GoodCatchClient implements IBinder.DeathRecipient {
        public IBinder mCb;
        public IGoodCatchDispatcher mDispatcher;
        public final HashMap mFunctions;
        public final String mModule;

        public GoodCatchClient(
                String str,
                String[] strArr,
                IGoodCatchDispatcher iGoodCatchDispatcher,
                IBinder iBinder) {
            HashMap hashMap = new HashMap();
            this.mFunctions = hashMap;
            this.mModule = str;
            this.mCb = iBinder;
            this.mDispatcher = iGoodCatchDispatcher;
            synchronized (hashMap) {
                for (String str2 : strArr) {
                    try {
                        this.mFunctions.put(str2, Boolean.FALSE);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (SemGoodCatchService.this.mGoodCatchClients) {
                SemGoodCatchService.this.mGoodCatchClients.remove(this.mCb);
                IBinder iBinder = this.mCb;
                if (iBinder != null) {
                    this.mDispatcher = null;
                    iBinder.unlinkToDeath(this, 0);
                    this.mCb = null;
                }
            }
        }

        public final void dump(PrintWriter printWriter) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("  Module:"), this.mModule, printWriter);
            synchronized (this.mFunctions) {
                try {
                    for (Map.Entry entry : this.mFunctions.entrySet()) {
                        printWriter.println(
                                "    Function:"
                                        + ((String) entry.getKey())
                                        + ", "
                                        + entry.getValue());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean has(String str, String str2) {
            synchronized (this.mFunctions) {
                try {
                    return this.mModule.equals(str) && this.mFunctions.containsKey(str2);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean isOn(String str) {
            boolean booleanValue;
            synchronized (this.mFunctions) {
                booleanValue = ((Boolean) this.mFunctions.get(str)).booleanValue();
            }
            return booleanValue;
        }

        public final void off(String str) {
            synchronized (this.mFunctions) {
                this.mFunctions.replace(str, Boolean.FALSE);
            }
            try {
                this.mDispatcher.onStop(str);
            } catch (RemoteException e) {
                int i = SemGoodCatchService.$r8$clinit;
                Log.e("SemGoodCatchService", "onStop " + e);
            }
        }

        public final void on(String str) {
            synchronized (this.mFunctions) {
                this.mFunctions.replace(str, Boolean.TRUE);
            }
            try {
                this.mDispatcher.onStart(str);
            } catch (RemoteException e) {
                int i = SemGoodCatchService.$r8$clinit;
                Log.e("SemGoodCatchService", "onStart " + e);
            }
        }
    }

    public final class GoodCatchHandler extends Handler {
        public GoodCatchHandler() {}

        /* JADX WARN: Code restructure failed: missing block: B:101:0x02a9, code lost:

           if (r3 != null) goto L82;
        */
        /* JADX WARN: Code restructure failed: missing block: B:102:0x02ab, code lost:

           r3.close();
        */
        /* JADX WARN: Code restructure failed: missing block: B:112:0x02ca, code lost:

           if (r3 == null) goto L89;
        */
        /* JADX WARN: Code restructure failed: missing block: B:78:0x024b, code lost:

           if (r6.equals("on") == false) goto L70;
        */
        /* JADX WARN: Code restructure failed: missing block: B:80:0x0251, code lost:

           if (r8.isOn(r5) != false) goto L73;
        */
        /* JADX WARN: Code restructure failed: missing block: B:81:0x0253, code lost:

           r8.on(r5);
        */
        /* JADX WARN: Code restructure failed: missing block: B:82:0x0256, code lost:

           r12 = true;
        */
        /* JADX WARN: Code restructure failed: missing block: B:83:0x0262, code lost:

           if (r12 == false) goto L119;
        */
        /* JADX WARN: Code restructure failed: missing block: B:85:0x0264, code lost:

           r12 = com.android.server.sepunion.SemGoodCatchService.$r8$clinit;
           r6 = new java.lang.StringBuilder();
           r6.append("GoodCatchClient has module='");
           r6.append(r4);
           r6.append("', function='");
           r6.append(r5);
           r6.append("', value -> '");
        */
        /* JADX WARN: Code restructure failed: missing block: B:86:0x0286, code lost:

           if (r8.isOn(r5) == false) goto L77;
        */
        /* JADX WARN: Code restructure failed: missing block: B:87:0x0288, code lost:

           r4 = "on";
        */
        /* JADX WARN: Code restructure failed: missing block: B:90:0x028c, code lost:

           r4 = "off";
        */
        /* JADX WARN: Code restructure failed: missing block: B:94:0x025c, code lost:

           if (r8.isOn(r5) == false) goto L73;
        */
        /* JADX WARN: Code restructure failed: missing block: B:95:0x025e, code lost:

           r8.off(r5);
        */
        /* JADX WARN: Code restructure failed: missing block: B:97:0x01c8, code lost:

           continue;
        */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r12) {
            /*
                Method dump skipped, instructions count: 770
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.sepunion.SemGoodCatchService.GoodCatchHandler.handleMessage(android.os.Message):void");
        }
    }

    public final class GoodCatchObserver extends ContentObserver {
        public GoodCatchObserver() {
            super(new Handler());
            SemGoodCatchService.this.mContentResolver.registerContentObserver(
                    SemGoodCatchService.this.mFeatureSetting.mUri, false, this, -2);
            int i = SemGoodCatchService.$r8$clinit;
            Log.v(
                    "SemGoodCatchService",
                    "mContentResolver.registerContentObserver(mFeatureSetting.getUri() : "
                            + SemGoodCatchService.this.mFeatureSetting.mUri);
            SemGoodCatchService.sendMsg(SemGoodCatchService.this.mGoodCatchHandler, 1, null, 0);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            super.onChange(z);
            int i = SemGoodCatchService.$r8$clinit;
            Log.v(
                    "SemGoodCatchService",
                    "GoodCatchObserver.onChange(boolean selfChange : " + z + ")");
            SemGoodCatchService.sendMsg(SemGoodCatchService.this.mGoodCatchHandler, 1, null, 0);
        }
    }

    public final class GoodCatchThread extends Thread {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public GoodCatchThread() {
            super("SemGoodCatchService");
            int i = SemGoodCatchService.$r8$clinit;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            Looper.prepare();
            synchronized (SemGoodCatchService.this) {
                SemGoodCatchService.this.mGoodCatchHandler =
                        SemGoodCatchService.this.new GoodCatchHandler();
                SemGoodCatchService.this.notify();
            }
            Looper.loop();
        }
    }

    public class SecFeature {
        public final String mName;
        public boolean mOn = false;
        public Uri mUri;

        public SecFeature(String str) {
            this.mName = str;
        }

        public void dump(PrintWriter printWriter) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mName);
            sb.append(" = ");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb, this.mOn, printWriter);
            if (this.mUri != null) {
                printWriter.println("  Uri:" + this.mUri);
            }
        }
    }

    public SemGoodCatchService(Context context) {
        GoodCatchHandler goodCatchHandler;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class:
                    // com.android.server.sepunion.SemGoodCatchService.1
                    public final Signature[] trustedSignatures = {
                        new Signature(
                                "308204d4308203bca003020102020900d20995a79c0daad6300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531325a170d3338313130373132323531325a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100c986384a3e1f2fb206670e78ef232215c0d26f45a22728db99a44da11c35ac33a71fe071c4a2d6825a9b4c88b333ed96f3c5e6c666d60f3ee94c490885abcf8dc660f707aabc77ead3e2d0d8aee8108c15cd260f2e85042c28d2f292daa3c6da0c7bf2391db7841aade8fdf0c9d0defcf77124e6d2de0a9e0d2da746c3670e4ffcdc85b701bb4744861b96ff7311da3603c5a10336e55ffa34b4353eedc85f51015e1518c67e309e39f87639ff178107f109cd18411a6077f26964b6e63f8a70b9619db04306a323c1a1d23af867e19f14f570ffe573d0e3a0c2b30632aaec3173380994be1e341e3a90bd2e4b615481f46db39ea83816448ec35feb1735c1f3020103a382010b30820107301d0603551d0e04160414932c3af70b627a0c7610b5a0e7427d6cfaea3f1e3081d70603551d230481cf3081cc8014932c3af70b627a0c7610b5a0e7427d6cfaea3f1ea181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900d20995a79c0daad6300c0603551d13040530030101ff300d06092a864886f70d01010505000382010100329601fe40e036a4a86cc5d49dd8c1b5415998e72637538b0d430369ac51530f63aace8c019a1a66616a2f1bb2c5fabd6f313261f380e3471623f053d9e3c53f5fd6d1965d7b000e4dc244c1b27e2fe9a323ff077f52c4675e86247aa801187137e30c9bbf01c567a4299db4bf0b25b7d7107a7b81ee102f72ff47950164e26752e114c42f8b9d2a42e7308897ec640ea1924ed13abbe9d120912b62f4926493a86db94c0b46f44c6161d58c2f648164890c512dfb28d42c855bf470dbee2dab6960cad04e81f71525ded46cdd0f359f99c460db9f007d96ce83b4b218ac2d82c48f12608d469733f05a3375594669ccbf8a495544d6c5701e9369c08c810158"),
                        new Signature(
                                "308204d4308203bca003020102020900b161f3869153be27300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531325a170d3338313130373132323531325a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100e93d8694c493d50a6224a473d70ddcecd84a2f40ac48bb8206c83a09a94f2db98aaa34f9fcc343b91a87c61254c3a43b0caed03cd839a63037253ea77d949a284dd0b44ebfbabbc2cea838213609d9a5813e88863210ee62c0c0e415611aa7f938ad2bc627c147ac6cf558002028d2e38b1d31aba794867717ddcfcadbeeac6bd345a7bf6433e52cfc93a2157cb048298bd33bf30c143b777e3f074897bcf3b5b181316b678256fd3accf64e88160b0781efd90711ef4acae86848d87e1c10a1747e780c48bcb378a7b437e0405ec54ed7e22c4dbc39f8b03ab1d5eeb7cf4804455fbcab35afb775d79e8f4c4fa4da00b2ce48c991fd94020f7ad089fba13003020103a382010b30820107301d0603551d0e04160414b58d96dcf0127466098625e3ffb03a4f8d0654743081d70603551d230481cf3081cc8014b58d96dcf0127466098625e3ffb03a4f8d065474a181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900b161f3869153be27300c0603551d13040530030101ff300d06092a864886f70d0101050500038201010091327721aa614451a785e200349ce2f402049371943001266827c29abdf975dc7b3e6eaa02c41a07b445bb9de0bc43ce25c3c98928a94ff67ad81eec822cbd083ae686cd7126860655adb8d6a6228cf1f7a4a196699669c05b506efa1fca2cad1a150cabd01380e56bb1842651b4ff33bcb619b3c6e65a10cfd99350ea777c3866135523c1bece17f59fba76a2eb429453f7a2a9e6a6cc9e62e5f4b56706ba4c74cb86975aa865bead2209787b33261b9fa222a7117b1724ea3217ad680fd0408c5634278fbdfca0e32b16dc1a6cc245e931cbe84fc7cccdaa7778459e3003a082662ac6d84d485dd368e0eb4c2c9019420c82d1cd0fbd6fcc097353b059baea"),
                        new Signature(
                                "308204d4308203bca003020102020900b830e7f5ede090a8300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531325a170d3338313130373132323531325a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d003082010802820101009a280ff8cebd5954fbac141d450be91a980a6597b379cb64a19bc4ab39aecb5f06fe2599d3767bb0c27e3e8ac3846cf0b80c09817f8d22be8a55418a068c6983958ffc233a99cd793bc468b0bda139b87ff1550e5ce184647214a1fa4fe2121a0ecdbb1cd33c644c06e7b70455ff097a4f8c51eca2ebefb4602b5d8bb6ed811ec959c1e99e8f353667703563c3c3277bbbd872fe7fa84bd8041efa98d32bb35c44d9c55aa8e766da065176722103fdb63677392c94bd20f5a5ac5c780046bc729a2eec3575a05ddb39836235c8c939f95493aa8f32dd7e7016392716219f0c5fe48874f283af0c217b4c08536b5df7bc302c9e2af08db61ecb49a198c7c4bd2b020103a382010b30820107301d0603551d0e041604144d2270829d5cf4a65bf55a756224bea659c2dfda3081d70603551d230481cf3081cc80144d2270829d5cf4a65bf55a756224bea659c2dfdaa181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900b830e7f5ede090a8300c0603551d13040530030101ff300d06092a864886f70d01010505000382010100751ea54edeb751de01436db8009352bee64209020fe40641ac09d0016c807fd89258aca374299520e30bc79e77a161c98ddb8ccfc9c8184969114e4478d1b1b374a97e52e07e056dd6b6de5b063c12203e55e284d1de58af2fc6e43c198857b87ac9a472633b8a1cd7e6ebc4e2d675b680d1844d86ab7569129d24e2bcf10cddb2e66c85c1335a3d6479749152058a27135440b795bf509d78009fbda18a6c0cb31b741f79a4ac189d44fd04f65887bb9d950cc2b6f43275e71900fba03b06a9ab9ecd58af0f8c2e0b3569197b043da0601563b0af26a0f52c4b7e834c7ccf5dec4d330d8fd0a049360cd3d9ef0bff09b9812c9ba406c8a6650688b0919a040b"),
                        new Signature(
                                "308204d4308203bca003020102020900e5eff0a8f66d92b3300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531335a170d3338313130373132323531335a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100e9f1edb42423201dce62e68f2159ed8ea766b43a43d348754841b72e9678ce6b03d06d31532d88f2ef2d5ba39a028de0857983cd321f5b7786c2d3699df4c0b40c8d856f147c5dc54b9d1d671d1a51b5c5364da36fc5b0fe825afb513ec7a2db862c48a6046c43c3b71a1e275155f6c30aed2a68326ac327f60160d427cf55b617230907a84edbff21cc256c628a16f15d55d49138cdf2606504e1591196ed0bdc25b7cc4f67b33fb29ec4dbb13dbe6f3467a0871a49e620067755e6f095c3bd84f8b7d1e66a8c6d1e5150f7fa9d95475dc7061a321aaf9c686b09be23ccc59b35011c6823ffd5874d8fa2a1e5d276ee5aa381187e26112c7d5562703b36210b020103a382010b30820107301d0603551d0e041604145b115b23db35655f9f77f78756961006eebe3a9e3081d70603551d230481cf3081cc80145b115b23db35655f9f77f78756961006eebe3a9ea181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900e5eff0a8f66d92b3300c0603551d13040530030101ff300d06092a864886f70d0101050500038201010039c91877eb09c2c84445443673c77a1219c5c02e6552fa2fbad0d736bc5ab6ebaf0375e520fe9799403ecb71659b23afda1475a34ef4b2e1ffcba8d7ff385c21cb6482540bce3837e6234fd4f7dd576d7fcfe9cfa925509f772c494e1569fe44e6fcd4122e483c2caa2c639566dbcfe85ed7818d5431e73154ad453289fb56b607643919cf534fbeefbdc2009c7fcb5f9b1fa97490462363fa4bedc5e0b9d157e448e6d0e7cfa31f1a2faa9378d03c8d1163d3803bc69bf24ec77ce7d559abcaf8d345494abf0e3276f0ebd2aa08e4f4f6f5aaea4bc523d8cc8e2c9200ba551dd3d4e15d5921303ca9333f42f992ddb70c2958e776c12d7e3b7bd74222eb5c7a")
                    };

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int i;
                        SigningInfo signingInfo;
                        int i2 = SemGoodCatchService.$r8$clinit;
                        Log.v(
                                "SemGoodCatchService",
                                "onReceive((Context context, Intent intent : " + intent.toString());
                        if (intent.getAction()
                                .equals("com.samsung.android.app.goodcatch.GOOD_CATCH_URI")) {
                            String stringExtra = intent.getStringExtra("ONOFF_SETTING_URI");
                            String stringExtra2 = intent.getStringExtra("EVENT_LIST_URI");
                            String stringExtra3 = intent.getStringExtra("SETTING_LIST_URI");
                            StringBuilder m =
                                    InitialConfiguration$$ExternalSyntheticOutline0.m(
                                            "settingUri : ",
                                            stringExtra,
                                            ", eventUri : ",
                                            stringExtra2,
                                            ", settingProviderUri : ");
                            m.append(stringExtra3);
                            Log.v("SemGoodCatchService", m.toString());
                            if (stringExtra == null
                                    || stringExtra2 == null
                                    || stringExtra3 == null) {
                                return;
                            }
                            String[] strArr = {stringExtra, stringExtra2, stringExtra3};
                            PackageManager packageManager =
                                    SemGoodCatchService.this.mContext.getPackageManager();
                            int i3 = 3;
                            int i4 = 0;
                            if (packageManager != null) {
                                int i5 = 0;
                                i = 1;
                                while (i5 < i3) {
                                    String str = strArr[i5];
                                    int i6 = SemGoodCatchService.$r8$clinit;
                                    Log.i("SemGoodCatchService", "Verify authority: " + str);
                                    String authority = Uri.parse(str).getAuthority();
                                    if (authority == null) {
                                        Log.w(
                                                "SemGoodCatchService",
                                                "Verifying failed. Authority not found: " + str);
                                    } else {
                                        ProviderInfo resolveContentProvider =
                                                packageManager.resolveContentProvider(
                                                        authority, i4);
                                        if (resolveContentProvider == null) {
                                            Log.w(
                                                    "SemGoodCatchService",
                                                    "Verifying failed. Provider not found: "
                                                            .concat(authority));
                                        } else {
                                            String str2 =
                                                    resolveContentProvider
                                                            .applicationInfo
                                                            .packageName;
                                            if ("com.samsung.android.app.goodcatch".equals(str2)) {
                                                try {
                                                    signingInfo =
                                                            packageManager.getPackageInfo(
                                                                            str2, 134217728)
                                                                    .signingInfo;
                                                } catch (PackageManager.NameNotFoundException e) {
                                                    int i7 = SemGoodCatchService.$r8$clinit;
                                                    Log.w(
                                                            "SemGoodCatchService",
                                                            "Verifying failed. Package not found: "
                                                                    + str2,
                                                            e);
                                                }
                                                if (signingInfo == null) {
                                                    Log.w(
                                                            "SemGoodCatchService",
                                                            "Verifying failed. SigningInfo not"
                                                                + " found: "
                                                                    + str2);
                                                } else {
                                                    Signature[] apkContentsSigners =
                                                            signingInfo.hasMultipleSigners()
                                                                    ? signingInfo
                                                                            .getApkContentsSigners()
                                                                    : signingInfo
                                                                            .getSigningCertificateHistory();
                                                    if (apkContentsSigners == null) {
                                                        Log.w(
                                                                "SemGoodCatchService",
                                                                "Verifying failed. Signature array"
                                                                    + " is null.");
                                                    } else {
                                                        int length = apkContentsSigners.length;
                                                        for (int i8 = i4; i8 < length; i8++) {
                                                            Signature signature =
                                                                    apkContentsSigners[i8];
                                                            Signature[] signatureArr =
                                                                    this.trustedSignatures;
                                                            int length2 = signatureArr.length;
                                                            int i9 = 0;
                                                            while (i9 < length2) {
                                                                Signature[] signatureArr2 =
                                                                        apkContentsSigners;
                                                                if (signatureArr[i9].equals(
                                                                        signature)) {
                                                                    int i10 =
                                                                            SemGoodCatchService
                                                                                    .$r8$clinit;
                                                                    Log.i(
                                                                            "SemGoodCatchService",
                                                                            "Trusted signature"
                                                                                + " found.");
                                                                    i4 = 1;
                                                                    break;
                                                                }
                                                                i9++;
                                                                apkContentsSigners = signatureArr2;
                                                            }
                                                        }
                                                        int i11 = SemGoodCatchService.$r8$clinit;
                                                        Log.w(
                                                                "SemGoodCatchService",
                                                                "Trusted signature not found.");
                                                        i4 = 0;
                                                    }
                                                }
                                            } else {
                                                Log.w(
                                                        "SemGoodCatchService",
                                                        "Verifying failed. packageName:" + str2);
                                            }
                                        }
                                    }
                                    i &= i4;
                                    i5++;
                                    i3 = 3;
                                    i4 = 0;
                                }
                                if (Debug.semIsProductDev()) {
                                    int i12 = SemGoodCatchService.$r8$clinit;
                                    Log.d(
                                            "SemGoodCatchService",
                                            "Since it will not be shipped, continues whatever"
                                                + " verified or not.");
                                    i = 1;
                                }
                            } else {
                                Log.e("SemGoodCatchService", "Verifying failed. Unexpected error.");
                                i = 0;
                            }
                            if (i != 0) {
                                SemGoodCatchService.this.mFeatureSetting.mUri =
                                        Uri.parse(stringExtra);
                                SemGoodCatchService.this.mFeatureEvent.mUri =
                                        Uri.parse(stringExtra2);
                                SemGoodCatchService.this.mFeatureSettingsProvider.mUri =
                                        Uri.parse(stringExtra3);
                                SemGoodCatchService.this.mFeatureEvent.mOn = true;
                                int i13 = SemGoodCatchService.$r8$clinit;
                                Log.v(
                                        "SemGoodCatchService",
                                        "sendMsg(mGoodCatchHandler, MSG_CREATE_OBSERVER,...)");
                                SemGoodCatchService.sendMsg(
                                        SemGoodCatchService.this.mGoodCatchHandler, 3, null, 0);
                            }
                        }
                    }
                };
        this.mGoodCatchObserver = null;
        this.mContext = context;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.android.app.goodcatch.GOOD_CATCH_URI");
        context.registerReceiver(broadcastReceiver, intentFilter, 2);
        this.mContentResolver = context.getContentResolver();
        new GoodCatchThread().start();
        synchronized (this) {
            while (true) {
                goodCatchHandler = this.mGoodCatchHandler;
                if (goodCatchHandler == null) {
                    try {
                        wait();
                    } catch (InterruptedException unused) {
                        Log.e(
                                "SemGoodCatchService",
                                "Interrupted while waiting on vibrator handler.");
                    }
                }
            }
        }
        sendMsg(goodCatchHandler, 0, null, 0);
    }

    public static void sendMsg(Handler handler, int i, Object obj, int i2) {
        handler.sendMessageAtTime(
                handler.obtainMessage(i, 0, 0, obj), SystemClock.uptimeMillis() + i2);
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(
                "\n##### GoodCatchService #####\n##### (dumpsys sepunion goodcatch) #####\n");
        this.mFeatureSetting.dump(printWriter);
        this.mFeatureEvent.dump(printWriter);
        this.mFeatureDetectAds.dump(printWriter);
        this.mFeatureWakeUp.dump(printWriter);
        this.mFeatureSettingsProvider.dump(printWriter);
        synchronized (this.mGoodCatchClients) {
            try {
                printWriter.println("\nGoodCatchClient size = " + this.mGoodCatchClients.size());
                Iterator it = this.mGoodCatchClients.values().iterator();
                while (it.hasNext()) {
                    ((GoodCatchClient) it.next()).dump(printWriter);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final List getSelectedSettingKey() {
        FeatureSettingsProvider featureSettingsProvider = this.mFeatureSettingsProvider;
        featureSettingsProvider.getClass();
        ArrayList arrayList = new ArrayList();
        String[] strArr = {"db_key"};
        Cursor cursor = null;
        try {
            SemGoodCatchService semGoodCatchService = SemGoodCatchService.this;
            cursor =
                    semGoodCatchService.mContentResolver.query(
                            semGoodCatchService.mFeatureSettingsProvider.mUri,
                            strArr,
                            null,
                            null,
                            null);
            if (cursor != null && cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    arrayList.add(cursor.getString(cursor.getColumnIndex("db_key")));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("SemGoodCatchService", "getSelectedSettingKey() error : " + e);
        }
        if (cursor != null) {
            cursor.close();
        }
        Log.d(
                "SemGoodCatchService",
                "getSelectedSettingKey() returns " + arrayList.size() + " keys.");
        return arrayList;
    }

    public final AbsSemSystemService getSemSystemService(String str) {
        return null;
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onBootPhase(int i) {
        if (i == 1000) {
            Intent intent =
                    new Intent(
                            "com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED");
            UserHandle userHandle = UserHandle.CURRENT;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mContext.sendBroadcastAsUser(intent, userHandle);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                Log.d(
                        "SemGoodCatchService",
                        "PHASE_BOOT_COMPLETED, send"
                            + " com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED");
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    public final void onCleanupUser(int i) {}

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onCreate(Bundle bundle) {}

    public final void onDestroy() {}

    public final void onStart() {}

    public final void onStartUser(int i) {}

    public final void onStopUser(int i) {}

    public final void onSwitchUser(int i) {}

    public final void onUnlockUser(int i) {}

    public final void registerListener(
            String str,
            String[] strArr,
            IGoodCatchDispatcher iGoodCatchDispatcher,
            IBinder iBinder) {
        Log.d("SemGoodCatchService", "registerListener, " + str + ", " + iBinder);
        synchronized (this.mGoodCatchClients) {
            this.mGoodCatchClients.put(
                    iBinder, new GoodCatchClient(str, strArr, iGoodCatchDispatcher, iBinder));
        }
    }

    public final void update(String[] strArr) {
        sendMsg(this.mGoodCatchHandler, 2, strArr, 0);
    }
}
