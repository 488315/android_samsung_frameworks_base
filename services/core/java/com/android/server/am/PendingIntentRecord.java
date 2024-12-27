package com.android.server.am;

import android.app.BackgroundStartPrivileges;
import android.app.compat.CompatChanges;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerWhitelistManager;
import android.os.RemoteCallbackList;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.TimeUtils;

import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.SafeActivityOptions;

import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PendingIntentRecord extends IIntentSender.Stub {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final PendingIntentController controller;
    public final Key key;
    public String lastTag;
    public String lastTagPrefix;
    public ArrayMap mAllowlistDuration;
    public RemoteCallbackList mCancelCallbacks;
    public String stringName;
    public final int uid;
    public boolean sent = false;
    public boolean canceled = false;
    public int cancelReason = 0;
    public int canceledFromUID = -1;
    public int canceledFromPID = -1;
    public final ArraySet mAllowBgActivityStartsForActivitySender = new ArraySet();
    public final ArraySet mAllowBgActivityStartsForBroadcastSender = new ArraySet();
    public final ArraySet mAllowBgActivityStartsForServiceSender = new ArraySet();
    public final WeakReference ref = new WeakReference(this);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Key {
        public final IBinder activity;
        public Intent[] allIntents;
        public String[] allResolvedTypes;
        public final String featureId;
        public final int flags;
        public final int hashCode;
        public final SafeActivityOptions options;
        public final String packageName;
        public final int requestCode;
        public final Intent requestIntent;
        public final String requestResolvedType;
        public final int type;
        public final int userId;
        public final String who;

        public Key(
                int i,
                String str,
                String str2,
                IBinder iBinder,
                String str3,
                int i2,
                Intent[] intentArr,
                String[] strArr,
                int i3,
                SafeActivityOptions safeActivityOptions,
                int i4) {
            this.type = i;
            this.packageName = str;
            this.featureId = str2;
            this.activity = iBinder;
            this.who = str3;
            this.requestCode = i2;
            Intent intent = intentArr != null ? intentArr[intentArr.length - 1] : null;
            this.requestIntent = intent;
            String str4 = strArr != null ? strArr[strArr.length - 1] : null;
            this.requestResolvedType = str4;
            this.allIntents = intentArr;
            this.allResolvedTypes = strArr;
            this.flags = i3;
            this.options = safeActivityOptions;
            this.userId = i4;
            int i5 = ((((FrameworkStatsLog.VPN_CONNECTION_REPORTED + i3) * 37) + i2) * 37) + i4;
            i5 = str3 != null ? (i5 * 37) + str3.hashCode() : i5;
            i5 = iBinder != null ? (i5 * 37) + iBinder.hashCode() : i5;
            i5 = intent != null ? (i5 * 37) + intent.filterHashCode() : i5;
            this.hashCode =
                    ((((str4 != null ? (i5 * 37) + str4.hashCode() : i5) * 37)
                                            + (str != null ? str.hashCode() : 0))
                                    * 37)
                            + i;
        }

        public final boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            try {
                Key key = (Key) obj;
                if (this.type != key.type
                        || this.userId != key.userId
                        || !Objects.equals(this.packageName, key.packageName)
                        || !Objects.equals(this.featureId, key.featureId)
                        || this.activity != key.activity
                        || !Objects.equals(this.who, key.who)
                        || this.requestCode != key.requestCode) {
                    return false;
                }
                Intent intent = this.requestIntent;
                Intent intent2 = key.requestIntent;
                if (intent != intent2) {
                    if (intent != null) {
                        if (!intent.filterEquals(intent2)) {
                            return false;
                        }
                    } else if (intent2 != null) {
                        return false;
                    }
                }
                if (Objects.equals(this.requestResolvedType, key.requestResolvedType)) {
                    return this.flags == key.flags;
                }
                return false;
            } catch (ClassCastException unused) {
                return false;
            }
        }

        public final int hashCode() {
            return this.hashCode;
        }

        public final String toSecureString(boolean z) {
            StringBuilder sb = new StringBuilder("Key{");
            sb.append(typeName());
            sb.append(" pkg=");
            sb.append(this.packageName);
            String str = this.featureId;
            sb.append(str != null ? "/".concat(str) : "");
            sb.append(" intent=");
            Intent intent = this.requestIntent;
            sb.append(intent != null ? intent.toShortString(z, true, false, false) : "<null>");
            sb.append(" flags=0x");
            BatteryService$$ExternalSyntheticOutline0.m(this.flags, sb, " u=");
            sb.append(this.userId);
            sb.append("} requestCode=");
            sb.append(this.requestCode);
            return sb.toString();
        }

        public final String toString() {
            return toSecureString(false);
        }

        public final String typeName() {
            int i = this.type;
            return i != 1
                    ? i != 2
                            ? i != 3
                                    ? i != 4
                                            ? i != 5
                                                    ? Integer.toString(i)
                                                    : "startForegroundService"
                                            : "startService"
                                    : "activityResult"
                            : "startActivity"
                    : "broadcastIntent";
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TempAllowListDuration {
        public long duration;
        public String reason;
        public int reasonCode;
        public int type;
    }

    public PendingIntentRecord(PendingIntentController pendingIntentController, Key key, int i) {
        this.controller = pendingIntentController;
        this.key = key;
        this.uid = i;
    }

    public static String cancelReasonToString(int i) {
        return i != 0
                ? i != 1
                        ? i != 2
                                ? i != 4
                                        ? i != 8
                                                ? i != 16
                                                        ? i != 32
                                                                ? i != 64
                                                                        ? "UNKNOWN"
                                                                        : "ONE_SHOT_SENT"
                                                                : "SUPERSEDED"
                                                        : "HOSTING_ACTIVITY_DESTROYED"
                                                : "OWNER_CANCELED"
                                        : "OWNER_FORCE_STOPPED"
                                : "OWNER_UNINSTALLED"
                        : "USER_STOPPED"
                : "NULL";
    }

    public static BackgroundStartPrivileges getDefaultBackgroundStartPrivileges(int i, String str) {
        if (i == 0 || i == 1000) {
            return BackgroundStartPrivileges.ALLOW_FGS;
        }
        return str != null
                ? CompatChanges.isChangeEnabled(244637991L, str, UserHandle.getUserHandleForUid(i))
                : CompatChanges.isChangeEnabled(244637991L, i)
                        ? BackgroundStartPrivileges.ALLOW_FGS
                        : BackgroundStartPrivileges.ALLOW_BAL;
    }

    public final void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.print("uid=");
        printWriter.print(this.uid);
        printWriter.print(" packageName=");
        printWriter.print(this.key.packageName);
        printWriter.print(" featureId=");
        printWriter.print(this.key.featureId);
        printWriter.print(" type=");
        printWriter.print(this.key.typeName());
        printWriter.print(" flags=0x");
        printWriter.println(Integer.toHexString(this.key.flags));
        Key key = this.key;
        if (key.activity != null || key.who != null) {
            printWriter.print(str);
            printWriter.print("activity=");
            printWriter.print(this.key.activity);
            printWriter.print(" who=");
            printWriter.println(this.key.who);
        }
        Key key2 = this.key;
        if (key2.requestCode != 0 || key2.requestResolvedType != null) {
            printWriter.print(str);
            printWriter.print("requestCode=");
            printWriter.print(this.key.requestCode);
            printWriter.print(" requestResolvedType=");
            printWriter.println(this.key.requestResolvedType);
        }
        if (this.key.requestIntent != null) {
            printWriter.print(str);
            printWriter.print("requestIntent=");
            printWriter.println(this.key.requestIntent.toShortString(false, true, true, false));
        }
        if (this.sent || this.canceled) {
            printWriter.print(str);
            printWriter.print("sent=");
            printWriter.print(this.sent);
            printWriter.print(" canceled=");
            printWriter.print(this.canceled);
            printWriter.print(" cancelReason=");
            printWriter.println(cancelReasonToString(this.cancelReason));
            if (this.canceledFromUID != -1 || this.canceledFromPID != -1) {
                printWriter.print(str);
                printWriter.print(" cancel uid=");
                BroadcastStats$$ExternalSyntheticOutline0.m(
                        this.canceledFromUID, printWriter, str, " cancel pid=");
                printWriter.println(this.canceledFromPID);
            }
        }
        if (this.mAllowlistDuration != null) {
            printWriter.print(str);
            printWriter.print("allowlistDuration=");
            for (int i = 0; i < this.mAllowlistDuration.size(); i++) {
                if (i != 0) {
                    printWriter.print(", ");
                }
                TempAllowListDuration tempAllowListDuration =
                        (TempAllowListDuration) this.mAllowlistDuration.valueAt(i);
                printWriter.print(
                        Integer.toHexString(
                                System.identityHashCode(this.mAllowlistDuration.keyAt(i))));
                printWriter.print(":");
                TimeUtils.formatDuration(tempAllowListDuration.duration, printWriter);
                printWriter.print("/");
                printWriter.print(tempAllowListDuration.type);
                printWriter.print("/");
                printWriter.print(
                        PowerWhitelistManager.reasonCodeToString(tempAllowListDuration.reasonCode));
                printWriter.print("/");
                printWriter.print(tempAllowListDuration.reason);
            }
            printWriter.println();
        }
        if (this.mCancelCallbacks != null) {
            printWriter.print(str);
            printWriter.println("mCancelCallbacks:");
            for (int i2 = 0; i2 < this.mCancelCallbacks.getRegisteredCallbackCount(); i2++) {
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(this.mCancelCallbacks.getRegisteredCallbackItem(i2));
            }
        }
    }

    public final void finalize() {
        try {
            if (!this.canceled) {
                this.controller.mH.sendMessage(
                        PooledLambda.obtainMessage(
                                new PendingIntentRecord$$ExternalSyntheticLambda0(), this));
            }
        } finally {
            super /*java.lang.Object*/.finalize();
        }
    }

    public final BackgroundStartPrivileges getBackgroundStartPrivilegesForActivitySender(
            ArraySet arraySet, IBinder iBinder, Bundle bundle, int i) {
        int i2;
        if (arraySet.contains(iBinder)) {
            return BackgroundStartPrivileges.allowBackgroundActivityStarts(iBinder);
        }
        if (this.uid == i || !ActivityTaskManagerService.this.hasActiveVisibleWindow(i)) {
            return BackgroundStartPrivileges.NONE;
        }
        if (bundle != null
                && (i2 = bundle.getInt("android.pendingIntent.backgroundActivityAllowed", 0))
                        != 0) {
            return i2 != 2 ? BackgroundStartPrivileges.ALLOW_BAL : BackgroundStartPrivileges.NONE;
        }
        return getDefaultBackgroundStartPrivileges(i, null);
    }

    public final void send(
            int i,
            Intent intent,
            String str,
            IBinder iBinder,
            IIntentReceiver iIntentReceiver,
            String str2,
            Bundle bundle) {
        sendInner(
                null,
                i,
                intent,
                str,
                iBinder,
                iIntentReceiver,
                str2,
                null,
                null,
                0,
                0,
                0,
                bundle,
                -1,
                -1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:141:0x05a3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int sendInner(
            android.app.IApplicationThread r32,
            int r33,
            android.content.Intent r34,
            java.lang.String r35,
            android.os.IBinder r36,
            android.content.IIntentReceiver r37,
            java.lang.String r38,
            android.os.IBinder r39,
            java.lang.String r40,
            int r41,
            int r42,
            int r43,
            android.os.Bundle r44,
            int r45,
            int r46) {
        /*
            Method dump skipped, instructions count: 1492
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.PendingIntentRecord.sendInner(android.app.IApplicationThread,"
                    + " int, android.content.Intent, java.lang.String, android.os.IBinder,"
                    + " android.content.IIntentReceiver, java.lang.String, android.os.IBinder,"
                    + " java.lang.String, int, int, int, android.os.Bundle, int, int):int");
    }

    public final void setAllowlistDurationLocked(
            IBinder iBinder, int i, int i2, String str, long j) {
        if (j > 0) {
            if (this.mAllowlistDuration == null) {
                this.mAllowlistDuration = new ArrayMap();
            }
            ArrayMap arrayMap = this.mAllowlistDuration;
            TempAllowListDuration tempAllowListDuration = new TempAllowListDuration();
            tempAllowListDuration.duration = j;
            tempAllowListDuration.type = i;
            tempAllowListDuration.reasonCode = i2;
            tempAllowListDuration.reason = str;
            arrayMap.put(iBinder, tempAllowListDuration);
        } else {
            ArrayMap arrayMap2 = this.mAllowlistDuration;
            if (arrayMap2 != null) {
                arrayMap2.remove(iBinder);
                if (this.mAllowlistDuration.size() <= 0) {
                    this.mAllowlistDuration = null;
                }
            }
        }
        this.stringName = null;
    }

    public final String toString() {
        String str = this.stringName;
        if (str != null) {
            return str;
        }
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "PendingIntentRecord{");
        m.append(Integer.toHexString(System.identityHashCode(this)));
        m.append(' ');
        m.append(this.key.packageName);
        if (this.key.featureId != null) {
            m.append('/');
            m.append(this.key.featureId);
        }
        m.append(' ');
        m.append(this.key.typeName());
        if (this.mAllowlistDuration != null) {
            m.append(" (allowlist: ");
            for (int i = 0; i < this.mAllowlistDuration.size(); i++) {
                if (i != 0) {
                    m.append(",");
                }
                TempAllowListDuration tempAllowListDuration =
                        (TempAllowListDuration) this.mAllowlistDuration.valueAt(i);
                m.append(
                        Integer.toHexString(
                                System.identityHashCode(this.mAllowlistDuration.keyAt(i))));
                m.append(":");
                TimeUtils.formatDuration(tempAllowListDuration.duration, m);
                m.append("/");
                m.append(tempAllowListDuration.type);
                m.append("/");
                m.append(
                        PowerWhitelistManager.reasonCodeToString(tempAllowListDuration.reasonCode));
                m.append("/");
                m.append(tempAllowListDuration.reason);
            }
            m.append(")");
        }
        m.append('}');
        String sb = m.toString();
        this.stringName = sb;
        return sb;
    }
}
