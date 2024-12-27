package com.android.server.am;

import android.app.ActivityManager;
import android.app.IUidObserver;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.UserHandle;
import android.util.SparseIntArray;
import android.util.proto.ProtoOutputStream;
import android.util.proto.ProtoUtils;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class UidObserverController {
    public final Handler mHandler;
    public int mUidChangeDispatchCount;
    public final Object mLock = new Object();
    public final RemoteCallbackList mUidObservers = new RemoteCallbackList();
    public final ArrayList mPendingUidChanges = new ArrayList();
    public final ArrayList mAvailUidChanges = new ArrayList();
    public ChangeRecord[] mActiveUidChanges = new ChangeRecord[5];
    public final UidObserverController$$ExternalSyntheticLambda0 mDispatchRunnable =
            new Runnable() { // from class:
                             // com.android.server.am.UidObserverController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    UidObserverController.this.dispatchUidsChanged();
                }
            };
    public final ActiveUids mValidateUids = new ActiveUids(null, false);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ChangeRecord {
        public int capability;
        public int change;
        public boolean ephemeral;
        public boolean isPending;
        public int procAdj;
        public int procState;
        public long procStateSeq;
        public int uid;

        public final void copyTo(ChangeRecord changeRecord) {
            changeRecord.isPending = this.isPending;
            changeRecord.uid = this.uid;
            changeRecord.change = this.change;
            changeRecord.procState = this.procState;
            changeRecord.procAdj = this.procAdj;
            changeRecord.capability = this.capability;
            changeRecord.ephemeral = this.ephemeral;
            changeRecord.procStateSeq = this.procStateSeq;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UidObserverRegistration {
        public static final int[] ORIG_ENUMS = {4, 8, 2, 1, 32, 64};
        public static final int[] PROTO_ENUMS = {3, 4, 2, 1, 6, 7};
        public final boolean mCanInteractAcrossUsers;
        public final int mCutpoint;
        public final SparseIntArray mLastProcStates;
        public int mMaxDispatchTime;
        public final String mPkg;
        public int mSlowDispatchCount;
        public final IBinder mToken;
        public final int mUid;
        public int[] mUids;
        public final int mWhich;

        public UidObserverRegistration(
                int i, String str, int i2, int i3, boolean z, int[] iArr, IBinder iBinder) {
            this.mUid = i;
            this.mPkg = str;
            this.mWhich = i2;
            this.mCutpoint = i3;
            this.mCanInteractAcrossUsers = z;
            if (iArr != null) {
                int[] iArr2 = (int[]) iArr.clone();
                this.mUids = iArr2;
                Arrays.sort(iArr2);
            } else {
                this.mUids = null;
            }
            this.mToken = iBinder;
            this.mLastProcStates = i3 >= 0 ? new SparseIntArray() : null;
        }

        public final void dump(PrintWriter printWriter, IUidObserver iUidObserver) {
            printWriter.print("    ");
            UserHandle.formatUid(printWriter, this.mUid);
            printWriter.print(" ");
            printWriter.print(this.mPkg);
            printWriter.print(" ");
            printWriter.print(iUidObserver.getClass().getTypeName());
            printWriter.print(":");
            int i = this.mWhich;
            if ((i & 4) != 0) {
                printWriter.print(" IDLE");
            }
            if ((i & 8) != 0) {
                printWriter.print(" ACT");
            }
            if ((i & 2) != 0) {
                printWriter.print(" GONE");
            }
            if ((i & 32) != 0) {
                printWriter.print(" CAP");
            }
            if ((i & 1) != 0) {
                printWriter.print(" STATE");
                printWriter.print(" (cut=");
                printWriter.print(this.mCutpoint);
                printWriter.print(")");
            }
            printWriter.println();
            SparseIntArray sparseIntArray = this.mLastProcStates;
            if (sparseIntArray != null) {
                int size = sparseIntArray.size();
                for (int i2 = 0; i2 < size; i2++) {
                    printWriter.print("      Last ");
                    UserHandle.formatUid(printWriter, this.mLastProcStates.keyAt(i2));
                    printWriter.print(": ");
                    printWriter.println(this.mLastProcStates.valueAt(i2));
                }
            }
        }

        public final void dumpDebug(ProtoOutputStream protoOutputStream) {
            long start = protoOutputStream.start(2246267895831L);
            protoOutputStream.write(1120986464257L, this.mUid);
            protoOutputStream.write(1138166333442L, this.mPkg);
            ProtoUtils.writeBitWiseFlagsToProtoEnum(
                    protoOutputStream, 2259152797699L, this.mWhich, ORIG_ENUMS, PROTO_ENUMS);
            protoOutputStream.write(1120986464260L, this.mCutpoint);
            SparseIntArray sparseIntArray = this.mLastProcStates;
            if (sparseIntArray != null) {
                int size = sparseIntArray.size();
                for (int i = 0; i < size; i++) {
                    long start2 = protoOutputStream.start(2246267895813L);
                    protoOutputStream.write(1120986464257L, this.mLastProcStates.keyAt(i));
                    protoOutputStream.write(1120986464258L, this.mLastProcStates.valueAt(i));
                    protoOutputStream.end(start2);
                }
            }
            protoOutputStream.end(start);
        }
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.server.am.UidObserverController$$ExternalSyntheticLambda0] */
    public UidObserverController(ActivityManagerService.UiHandler uiHandler) {
        this.mHandler = uiHandler;
    }

    public static int mergeWithPendingChange(int i, int i2) {
        if ((i & 6) == 0) {
            i |= i2 & 6;
        }
        if ((i & 24) == 0) {
            i |= i2 & 24;
        }
        if ((i & 1) != 0) {
            i &= -13;
        }
        if ((i2 & 32) != 0) {
            i |= 32;
        }
        if ((i2 & Integer.MIN_VALUE) != 0) {
            i |= Integer.MIN_VALUE;
        }
        return (i2 & 64) != 0 ? i | 64 : i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:91:0x0130, code lost:

       if (r14.procState != 20) goto L90;
    */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0143 A[Catch: RemoteException -> 0x0192, TryCatch #0 {RemoteException -> 0x0192, blocks: (B:27:0x007a, B:30:0x0091, B:32:0x009f, B:50:0x00c3, B:51:0x00d8, B:53:0x00dc, B:55:0x00e0, B:56:0x00e6, B:58:0x00ea, B:59:0x00ef, B:61:0x00f5, B:63:0x00f9, B:64:0x0100, B:66:0x0104, B:68:0x0178, B:70:0x0182, B:72:0x0186, B:79:0x0114, B:83:0x0124, B:90:0x012e, B:93:0x0135, B:95:0x0139, B:98:0x0140, B:101:0x0143, B:103:0x0147, B:104:0x014e, B:105:0x0169, B:107:0x016d, B:109:0x0171, B:112:0x00cb, B:114:0x00cf, B:116:0x00d3, B:117:0x008b), top: B:26:0x007a }] */
    /* JADX WARN: Removed duplicated region for block: B:107:0x016d A[Catch: RemoteException -> 0x0192, TryCatch #0 {RemoteException -> 0x0192, blocks: (B:27:0x007a, B:30:0x0091, B:32:0x009f, B:50:0x00c3, B:51:0x00d8, B:53:0x00dc, B:55:0x00e0, B:56:0x00e6, B:58:0x00ea, B:59:0x00ef, B:61:0x00f5, B:63:0x00f9, B:64:0x0100, B:66:0x0104, B:68:0x0178, B:70:0x0182, B:72:0x0186, B:79:0x0114, B:83:0x0124, B:90:0x012e, B:93:0x0135, B:95:0x0139, B:98:0x0140, B:101:0x0143, B:103:0x0147, B:104:0x014e, B:105:0x0169, B:107:0x016d, B:109:0x0171, B:112:0x00cb, B:114:0x00cf, B:116:0x00d3, B:117:0x008b), top: B:26:0x007a }] */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0139 A[Catch: RemoteException -> 0x0192, TryCatch #0 {RemoteException -> 0x0192, blocks: (B:27:0x007a, B:30:0x0091, B:32:0x009f, B:50:0x00c3, B:51:0x00d8, B:53:0x00dc, B:55:0x00e0, B:56:0x00e6, B:58:0x00ea, B:59:0x00ef, B:61:0x00f5, B:63:0x00f9, B:64:0x0100, B:66:0x0104, B:68:0x0178, B:70:0x0182, B:72:0x0186, B:79:0x0114, B:83:0x0124, B:90:0x012e, B:93:0x0135, B:95:0x0139, B:98:0x0140, B:101:0x0143, B:103:0x0147, B:104:0x014e, B:105:0x0169, B:107:0x016d, B:109:0x0171, B:112:0x00cb, B:114:0x00cf, B:116:0x00d3, B:117:0x008b), top: B:26:0x007a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dispatchUidsChanged() {
        /*
            Method dump skipped, instructions count: 527
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.UidObserverController.dispatchUidsChanged():void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0025, code lost:

       r8.println("  mUidObservers:");
       r4 = true;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dump(java.io.PrintWriter r8, java.lang.String r9) {
        /*
            r7 = this;
            java.lang.Object r0 = r7.mLock
            monitor-enter(r0)
            android.os.RemoteCallbackList r1 = r7.mUidObservers     // Catch: java.lang.Throwable -> L21
            int r1 = r1.getRegisteredCallbackCount()     // Catch: java.lang.Throwable -> L21
            r2 = 0
            r3 = r2
            r4 = r3
        Lc:
            if (r3 >= r1) goto L39
            android.os.RemoteCallbackList r5 = r7.mUidObservers     // Catch: java.lang.Throwable -> L21
            java.lang.Object r5 = r5.getRegisteredCallbackCookie(r3)     // Catch: java.lang.Throwable -> L21
            com.android.server.am.UidObserverController$UidObserverRegistration r5 = (com.android.server.am.UidObserverController.UidObserverRegistration) r5     // Catch: java.lang.Throwable -> L21
            if (r9 == 0) goto L23
            java.lang.String r6 = r5.mPkg     // Catch: java.lang.Throwable -> L21
            boolean r6 = r9.equals(r6)     // Catch: java.lang.Throwable -> L21
            if (r6 == 0) goto L36
            goto L23
        L21:
            r7 = move-exception
            goto L91
        L23:
            if (r4 != 0) goto L2b
            java.lang.String r4 = "  mUidObservers:"
            r8.println(r4)     // Catch: java.lang.Throwable -> L21
            r4 = 1
        L2b:
            android.os.RemoteCallbackList r6 = r7.mUidObservers     // Catch: java.lang.Throwable -> L21
            android.os.IInterface r6 = r6.getRegisteredCallbackItem(r3)     // Catch: java.lang.Throwable -> L21
            android.app.IUidObserver r6 = (android.app.IUidObserver) r6     // Catch: java.lang.Throwable -> L21
            r5.dump(r8, r6)     // Catch: java.lang.Throwable -> L21
        L36:
            int r3 = r3 + 1
            goto Lc
        L39:
            if (r9 != 0) goto L8f
            r8.println()     // Catch: java.lang.Throwable -> L21
            java.lang.String r9 = "  mUidChangeDispatchCount="
            r8.print(r9)     // Catch: java.lang.Throwable -> L21
            int r9 = r7.mUidChangeDispatchCount     // Catch: java.lang.Throwable -> L21
            r8.print(r9)     // Catch: java.lang.Throwable -> L21
            r8.println()     // Catch: java.lang.Throwable -> L21
            java.lang.String r9 = "  Slow UID dispatches:"
            r8.println(r9)     // Catch: java.lang.Throwable -> L21
        L50:
            if (r2 >= r1) goto L8f
            android.os.RemoteCallbackList r9 = r7.mUidObservers     // Catch: java.lang.Throwable -> L21
            java.lang.Object r9 = r9.getRegisteredCallbackCookie(r2)     // Catch: java.lang.Throwable -> L21
            com.android.server.am.UidObserverController$UidObserverRegistration r9 = (com.android.server.am.UidObserverController.UidObserverRegistration) r9     // Catch: java.lang.Throwable -> L21
            java.lang.String r3 = "    "
            r8.print(r3)     // Catch: java.lang.Throwable -> L21
            android.os.RemoteCallbackList r3 = r7.mUidObservers     // Catch: java.lang.Throwable -> L21
            android.os.IInterface r3 = r3.getRegisteredCallbackItem(r2)     // Catch: java.lang.Throwable -> L21
            android.app.IUidObserver r3 = (android.app.IUidObserver) r3     // Catch: java.lang.Throwable -> L21
            java.lang.Class r3 = r3.getClass()     // Catch: java.lang.Throwable -> L21
            java.lang.String r3 = r3.getTypeName()     // Catch: java.lang.Throwable -> L21
            r8.print(r3)     // Catch: java.lang.Throwable -> L21
            java.lang.String r3 = ": "
            r8.print(r3)     // Catch: java.lang.Throwable -> L21
            int r3 = r9.mSlowDispatchCount     // Catch: java.lang.Throwable -> L21
            r8.print(r3)     // Catch: java.lang.Throwable -> L21
            java.lang.String r3 = " / Max "
            r8.print(r3)     // Catch: java.lang.Throwable -> L21
            int r9 = r9.mMaxDispatchTime     // Catch: java.lang.Throwable -> L21
            r8.print(r9)     // Catch: java.lang.Throwable -> L21
            java.lang.String r9 = "ms"
            r8.println(r9)     // Catch: java.lang.Throwable -> L21
            int r2 = r2 + 1
            goto L50
        L8f:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L21
            return
        L91:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L21
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.UidObserverController.dump(java.io.PrintWriter,"
                    + " java.lang.String):void");
    }

    public final void dumpDebug(ProtoOutputStream protoOutputStream, String str) {
        int i;
        synchronized (this.mLock) {
            try {
                int registeredCallbackCount = this.mUidObservers.getRegisteredCallbackCount();
                while (i < registeredCallbackCount) {
                    UidObserverRegistration uidObserverRegistration =
                            (UidObserverRegistration)
                                    this.mUidObservers.getRegisteredCallbackCookie(i);
                    i = (str == null || str.equals(uidObserverRegistration.mPkg)) ? 0 : i + 1;
                    uidObserverRegistration.dumpDebug(protoOutputStream);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final IBinder register(
            IUidObserver iUidObserver, int i, int i2, String str, int i3, int[] iArr) {
        StringBuilder m =
                DumpUtils$$ExternalSyntheticOutline0.m(
                        "UidObserver-", str, PackageManagerShellCommandDataLoader.STDIN_PATH);
        m.append(UUID.randomUUID().toString());
        Binder binder = new Binder(m.toString());
        synchronized (this.mLock) {
            this.mUidObservers.register(
                    iUidObserver,
                    new UidObserverRegistration(
                            i3,
                            str,
                            i,
                            i2,
                            ActivityManager.checkUidPermission(
                                            "android.permission.INTERACT_ACROSS_USERS_FULL", i3)
                                    == 0,
                            iArr,
                            binder));
        }
        return binder;
    }
}
