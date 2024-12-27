package com.android.server.am;

import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.app.compat.CompatChanges;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteCallbackList;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.android.internal.util.RingBuffer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.alarm.AlarmManagerService;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.SafeActivityOptions;

import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PendingIntentController {
    public ActivityManagerInternal mAmInternal;
    public final ActivityManagerConstants mConstants;
    public final Handler mH;
    public final UserController mUserController;
    public final Object mLock = new Object();
    public final HashMap mIntentSenderRecords = new HashMap();
    public final SparseIntArray mIntentsPerUid = new SparseIntArray();
    public final SparseArray mRecentIntentsPerUid = new SparseArray();
    public final ActivityTaskManagerInternal mAtmInternal =
            (ActivityTaskManagerInternal)
                    LocalServices.getService(ActivityTaskManagerInternal.class);

    public PendingIntentController(
            Looper looper,
            UserController userController,
            ActivityManagerConstants activityManagerConstants) {
        this.mH = new Handler(looper);
        this.mUserController = userController;
        this.mConstants = activityManagerConstants;
    }

    public final void cancelIntentSender(
            PendingIntentRecord pendingIntentRecord, boolean z, int i) {
        IBinder iBinder;
        synchronized (this.mLock) {
            try {
                makeIntentSenderCanceled(pendingIntentRecord, i);
                this.mIntentSenderRecords.remove(pendingIntentRecord.key);
                decrementUidStatLocked(pendingIntentRecord);
                if (z && (iBinder = pendingIntentRecord.key.activity) != null) {
                    this.mH.sendMessage(
                            PooledLambda.obtainMessage(
                                    new PendingIntentController$$ExternalSyntheticLambda1(),
                                    this,
                                    iBinder,
                                    pendingIntentRecord.ref));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void decrementUidStatLocked(PendingIntentRecord pendingIntentRecord) {
        int i = pendingIntentRecord.uid;
        int indexOfKey = this.mIntentsPerUid.indexOfKey(i);
        if (indexOfKey >= 0) {
            int valueAt = this.mIntentsPerUid.valueAt(indexOfKey) - 1;
            if (valueAt == this.mConstants.PENDINGINTENT_WARNING_THRESHOLD - 10) {
                this.mRecentIntentsPerUid.delete(i);
            }
            if (valueAt == 0) {
                this.mIntentsPerUid.removeAt(indexOfKey);
            } else {
                this.mIntentsPerUid.setValueAt(indexOfKey, valueAt);
            }
        }
    }

    public final void dumpPendingIntents(PrintWriter printWriter, String str, boolean z) {
        boolean z2;
        synchronized (this.mLock) {
            try {
                printWriter.println("ACTIVITY MANAGER PENDING INTENTS (dumpsys activity intents)");
                if (this.mIntentSenderRecords.size() > 0) {
                    ArrayMap arrayMap = new ArrayMap();
                    ArrayList arrayList = new ArrayList();
                    for (WeakReference weakReference : this.mIntentSenderRecords.values()) {
                        PendingIntentRecord pendingIntentRecord =
                                weakReference != null
                                        ? (PendingIntentRecord) weakReference.get()
                                        : null;
                        if (pendingIntentRecord == null) {
                            arrayList.add(weakReference);
                        } else if (str == null || str.equals(pendingIntentRecord.key.packageName)) {
                            ArrayList arrayList2 =
                                    (ArrayList) arrayMap.get(pendingIntentRecord.key.packageName);
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList();
                                arrayMap.put(pendingIntentRecord.key.packageName, arrayList2);
                            }
                            arrayList2.add(pendingIntentRecord);
                        }
                    }
                    int i = 0;
                    z2 = false;
                    while (i < arrayMap.size()) {
                        ArrayList arrayList3 = (ArrayList) arrayMap.valueAt(i);
                        printWriter.print("  * ");
                        printWriter.print((String) arrayMap.keyAt(i));
                        printWriter.print(": ");
                        printWriter.print(arrayList3.size());
                        printWriter.println(" items");
                        for (int i2 = 0; i2 < arrayList3.size(); i2++) {
                            printWriter.print("    #");
                            printWriter.print(i2);
                            printWriter.print(": ");
                            printWriter.println(arrayList3.get(i2));
                            if (z) {
                                ((PendingIntentRecord) arrayList3.get(i2))
                                        .dump(printWriter, "      ");
                            }
                        }
                        i++;
                        z2 = true;
                    }
                    if (arrayList.size() > 0) {
                        printWriter.println("  * WEAK REFS:");
                        for (int i3 = 0; i3 < arrayList.size(); i3++) {
                            printWriter.print("    #");
                            printWriter.print(i3);
                            printWriter.print(": ");
                            printWriter.println(arrayList.get(i3));
                        }
                        z2 = true;
                    }
                } else {
                    z2 = false;
                }
                int size = this.mIntentsPerUid.size();
                if (size > 0) {
                    for (int i4 = 0; i4 < size; i4++) {
                        printWriter.print("  * UID: ");
                        printWriter.print(this.mIntentsPerUid.keyAt(i4));
                        printWriter.print(" total: ");
                        printWriter.println(this.mIntentsPerUid.valueAt(i4));
                    }
                }
                if (!z2) {
                    printWriter.println("  (nothing)");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final PendingIntentRecord getIntentSender(
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            Bundle bundle,
            IBinder iBinder,
            String str,
            String str2,
            String str3,
            Intent[] intentArr,
            String[] strArr) {
        synchronized (this.mLock) {
            if (intentArr != null) {
                for (Intent intent : intentArr) {
                    try {
                        intent.setDefusable(true);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            Bundle.setDefusable(bundle, true);
            ActivityOptions fromBundle = ActivityOptions.fromBundle(bundle);
            if (fromBundle != null
                    && fromBundle.getPendingIntentBackgroundActivityStartMode() != 0) {
                Slog.wtf(
                        "ActivityManager",
                        "Resetting option setPendingIntentBackgroundActivityStartMode("
                                + fromBundle.getPendingIntentBackgroundActivityStartMode()
                                + ") to SYSTEM_DEFINED from the options provided by the pending"
                                + " intent creator ("
                                + str
                                + ") because this option is meant for the pending intent sender");
                if (CompatChanges.isChangeEnabled(320664730L, i2)) {
                    throw new IllegalArgumentException(
                            "pendingIntentBackgroundActivityStartMode must not be set when creating"
                                + " a PendingIntent");
                }
                fromBundle.setPendingIntentBackgroundActivityStartMode(0);
            }
            if (fromBundle != null
                    && fromBundle.isPendingIntentBackgroundActivityLaunchAllowedByPermission()) {
                Slog.wtf(
                        "ActivityManager",
                        "Resetting option pendingIntentBackgroundActivityLaunchAllowedByPermission"
                            + " which is set by the pending intent creator ("
                                + str
                                + ") because this option is meant for the pending intent sender");
                if (CompatChanges.isChangeEnabled(320664730L, i2)) {
                    throw new IllegalArgumentException(
                            "pendingIntentBackgroundActivityLaunchAllowedByPermission can not be"
                                + " set by creator of a PendingIntent");
                }
                fromBundle.setPendingIntentBackgroundActivityLaunchAllowedByPermission(false);
            }
            boolean z = (i5 & 536870912) != 0;
            boolean z2 = (i5 & 268435456) != 0;
            boolean z3 = (i5 & 134217728) != 0;
            PendingIntentRecord.Key key =
                    new PendingIntentRecord.Key(
                            i,
                            str,
                            str2,
                            iBinder,
                            str3,
                            i4,
                            intentArr,
                            strArr,
                            i5 & (-939524097),
                            new SafeActivityOptions(fromBundle),
                            i3);
            WeakReference weakReference = (WeakReference) this.mIntentSenderRecords.get(key);
            PendingIntentRecord pendingIntentRecord =
                    weakReference != null ? (PendingIntentRecord) weakReference.get() : null;
            if (pendingIntentRecord != null) {
                if (!z2) {
                    if (z3) {
                        Intent intent2 = pendingIntentRecord.key.requestIntent;
                        if (intent2 != null) {
                            intent2.replaceExtras(
                                    intentArr != null ? intentArr[intentArr.length - 1] : null);
                        }
                        if (intentArr != null) {
                            int length = intentArr.length - 1;
                            PendingIntentRecord.Key key2 = pendingIntentRecord.key;
                            intentArr[length] = key2.requestIntent;
                            key2.allIntents = intentArr;
                            key2.allResolvedTypes = strArr;
                        } else {
                            PendingIntentRecord.Key key3 = pendingIntentRecord.key;
                            key3.allIntents = null;
                            key3.allResolvedTypes = null;
                        }
                    }
                    return pendingIntentRecord;
                }
                makeIntentSenderCanceled(pendingIntentRecord, 32);
                this.mIntentSenderRecords.remove(key);
                decrementUidStatLocked(pendingIntentRecord);
            }
            if (z) {
                return pendingIntentRecord;
            }
            PendingIntentRecord pendingIntentRecord2 = new PendingIntentRecord(this, key, i2);
            this.mIntentSenderRecords.put(key, pendingIntentRecord2.ref);
            incrementUidStatLocked(pendingIntentRecord2);
            return pendingIntentRecord2;
        }
    }

    public final void incrementUidStatLocked(PendingIntentRecord pendingIntentRecord) {
        RingBuffer ringBuffer;
        int i = pendingIntentRecord.uid;
        int indexOfKey = this.mIntentsPerUid.indexOfKey(i);
        int i2 = 1;
        if (indexOfKey >= 0) {
            i2 = 1 + this.mIntentsPerUid.valueAt(indexOfKey);
            this.mIntentsPerUid.setValueAt(indexOfKey, i2);
        } else {
            this.mIntentsPerUid.put(i, 1);
        }
        if (i2 > 200 && i2 % 100 == 0) {
            PendingIntentController$$ExternalSyntheticOutline0.m(
                    i, i2, "Too many PendingIntent created for uid ", "->", "ActivityManager");
        }
        ActivityManagerConstants activityManagerConstants = this.mConstants;
        int i3 = activityManagerConstants.PENDINGINTENT_WARNING_THRESHOLD;
        int i4 = i3 - 9;
        if (i2 == i4) {
            ringBuffer = new RingBuffer(String.class, 10);
            this.mRecentIntentsPerUid.put(i, ringBuffer);
        } else {
            ringBuffer =
                    (i2 <= i4 || i2 > i3) ? null : (RingBuffer) this.mRecentIntentsPerUid.get(i);
        }
        if (ringBuffer == null) {
            return;
        }
        ringBuffer.append(pendingIntentRecord.key.toSecureString(false));
        if (i2 == activityManagerConstants.PENDINGINTENT_WARNING_THRESHOLD) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i, "Too many PendingIntent created for uid ", ", recent 10: ");
            m.append(Arrays.toString(ringBuffer.toArray()));
            Slog.wtf("ActivityManager", m.toString());
            this.mRecentIntentsPerUid.remove(i);
        }
    }

    public final void makeIntentSenderCanceled(PendingIntentRecord pendingIntentRecord, int i) {
        pendingIntentRecord.canceledFromUID = Binder.getCallingUid();
        pendingIntentRecord.canceledFromPID = Binder.getCallingPid();
        pendingIntentRecord.canceled = true;
        pendingIntentRecord.cancelReason = i;
        RemoteCallbackList remoteCallbackList = pendingIntentRecord.mCancelCallbacks;
        pendingIntentRecord.mCancelCallbacks = null;
        if (remoteCallbackList != null) {
            this.mH.sendMessage(
                    PooledLambda.obtainMessage(
                            new PendingIntentController$$ExternalSyntheticLambda2(),
                            this,
                            remoteCallbackList));
        }
        AlarmManagerService.LocalService localService =
                (AlarmManagerService.LocalService)
                        LocalServices.getService(AlarmManagerService.LocalService.class);
        AlarmManagerService.this
                .mHandler
                .obtainMessage(7, new PendingIntent(pendingIntentRecord))
                .sendToTarget();
    }
}
