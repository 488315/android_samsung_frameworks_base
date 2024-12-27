package com.android.server.os;

import android.app.AppOpsManager;
import android.app.admin.flags.Flags;
import android.app.role.RoleManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.IDumpstate;
import android.os.IDumpstateListener;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.LocalLog;
import android.util.Pair;
import android.util.Slog;
import android.util.Xml;

import com.android.internal.util.DumpUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.utils.Slogf;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public final class BugreportManagerServiceImpl extends IDumpstate.Stub {
    public static final long DEFAULT_BUGREPORT_CONSENTLESS_GRACE_PERIOD_MILLIS =
            TimeUnit.MINUTES.toMillis(2);
    public static final FeatureFlagsImpl sFeatureFlags = new FeatureFlagsImpl();
    public final AppOpsManager mAppOps;
    public final ArraySet mBugreportAllowlistedPackages;
    public final BugreportFileManager mBugreportFileManager;
    public final Context mContext;
    public DumpstateListener mCurrentDumpstateListener;
    public final Injector mInjector;
    public int mNumberFinishedBugreports;
    public final TelephonyManager mTelephonyManager;
    public final Object mLock = new Object();
    public OptionalInt mPreDumpedDataUid = OptionalInt.empty();
    public final LocalLog mFinishedBugreports = new LocalLog(20);

    class BugreportFileManager {
        public final AtomicFile mMappingFile;
        public final Object mLock = new Object();
        public boolean mReadBugreportMapping = false;
        public ArrayMap mBugreportFiles = new ArrayMap();
        public final Map mConsentGranted = new HashMap();
        final Set mBugreportFilesToPersist = new HashSet();

        public BugreportFileManager(AtomicFile atomicFile) {
            this.mMappingFile = atomicFile;
        }

        public static void throwInvalidBugreportFileForCallerException(String str, String str2) {
            throw new IllegalArgumentException(
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            "File ",
                            str,
                            " was not generated on behalf of calling package ",
                            str2));
        }

        public final void addBugreportMapping(Pair pair, String str) {
            synchronized (this.mLock) {
                try {
                    if (!this.mBugreportFiles.containsKey(pair)) {
                        this.mBugreportFiles.put(pair, new ArraySet());
                    }
                    ((ArraySet) this.mBugreportFiles.get(pair)).add(str);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean canSkipConsentScreen(String str, boolean z) {
            if (!Flags.onboardingConsentlessBugreports() || !Build.IS_DEBUGGABLE) {
                return false;
            }
            synchronized (this.mLock) {
                try {
                    if (!((HashMap) this.mConsentGranted).containsKey(str)) {
                        return false;
                    }
                    if (((Long) ((Pair) ((HashMap) this.mConsentGranted).get(str)).first)
                                            .longValue()
                                    + BugreportManagerServiceImpl
                                            .DEFAULT_BUGREPORT_CONSENTLESS_GRACE_PERIOD_MILLIS
                            >= System.currentTimeMillis()) {
                        return !z
                                || ((Boolean)
                                                ((Pair) ((HashMap) this.mConsentGranted).get(str))
                                                        .second)
                                        .booleanValue();
                    }
                    ((HashMap) this.mConsentGranted).remove(str);
                    return false;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void ensureCallerPreviouslyGeneratedFile(
                Context context,
                final PackageManager packageManager,
                final Pair pair,
                final int i,
                final String str) {
            synchronized (this.mLock) {
                try {
                    if (Flags.onboardingBugreportV2Enabled()) {
                        Integer num =
                                (Integer)
                                        Binder.withCleanCallingIdentity(
                                                new FunctionalUtils
                                                        .ThrowingSupplier() { // from class:
                                                                              // com.android.server.os.BugreportManagerServiceImpl$BugreportFileManager$$ExternalSyntheticLambda0
                                                    public final Object getOrThrow() {
                                                        PackageManager packageManager2 =
                                                                packageManager;
                                                        Pair pair2 = pair;
                                                        int i2 = i;
                                                        String str2 = str;
                                                        try {
                                                            return Integer.valueOf(
                                                                    packageManager2
                                                                            .getPackageUidAsUser(
                                                                                    (String)
                                                                                            pair2.second,
                                                                                    i2));
                                                        } catch (
                                                                PackageManager.NameNotFoundException
                                                                        unused) {
                                                            BugreportManagerServiceImpl
                                                                    .BugreportFileManager
                                                                    .throwInvalidBugreportFileForCallerException(
                                                                            str2,
                                                                            (String) pair2.second);
                                                            throw null;
                                                        }
                                                    }
                                                });
                        if (num.intValue() != ((Integer) pair.first).intValue()
                                && context.checkCallingOrSelfPermission(
                                                "android.permission.INTERACT_ACROSS_USERS")
                                        != 0) {
                            throw new SecurityException(
                                    ((String) pair.second)
                                            + " does not hold the INTERACT_ACROSS_USERS permission"
                                            + " to access cross-user bugreports.");
                        }
                        if (!this.mReadBugreportMapping) {
                            readBugreportMappingLocked();
                        }
                        ArraySet arraySet =
                                (ArraySet)
                                        this.mBugreportFiles.get(
                                                new Pair(num, (String) pair.second));
                        if (arraySet == null || !arraySet.contains(str)) {
                            throwInvalidBugreportFileForCallerException(str, (String) pair.second);
                            throw null;
                        }
                        if (!(Flags.onboardingBugreportV2Enabled()
                                ? this.mBugreportFilesToPersist.contains(str)
                                : false)) {
                            arraySet.remove(str);
                        }
                    } else {
                        ArraySet arraySet2 = (ArraySet) this.mBugreportFiles.get(pair);
                        if (arraySet2 == null || !arraySet2.contains(str)) {
                            throwInvalidBugreportFileForCallerException(str, (String) pair.second);
                            throw null;
                        }
                        arraySet2.remove(str);
                        if (arraySet2.isEmpty()) {
                            this.mBugreportFiles.remove(pair);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void readBugreportMappingLocked() {
            /*
                r7 = this;
                android.util.ArrayMap r0 = new android.util.ArrayMap
                r0.<init>()
                r7.mBugreportFiles = r0
                android.util.AtomicFile r0 = r7.mMappingFile     // Catch: java.lang.Throwable -> Lad java.io.FileNotFoundException -> Lb3
                java.io.FileInputStream r0 = r0.openRead()     // Catch: java.lang.Throwable -> Lad java.io.FileNotFoundException -> Lb3
                com.android.modules.utils.TypedXmlPullParser r1 = android.util.Xml.resolvePullParser(r0)     // Catch: java.lang.Throwable -> L3e
                java.lang.String r2 = "bugreport-data"
                com.android.internal.util.XmlUtils.beginDocument(r1, r2)     // Catch: java.lang.Throwable -> L3e
                int r2 = r1.getDepth()     // Catch: java.lang.Throwable -> L3e
            L1a:
                boolean r3 = com.android.internal.util.XmlUtils.nextElementWithin(r1, r2)     // Catch: java.lang.Throwable -> L3e
                r4 = 1
                if (r3 == 0) goto L9a
                java.lang.String r3 = r1.getName()     // Catch: java.lang.Throwable -> L3e
                int r5 = r3.hashCode()     // Catch: java.lang.Throwable -> L3e
                r6 = -1731556110(0xffffffff98ca8cf2, float:-5.2358095E-24)
                if (r5 == r6) goto L40
                r6 = 761901751(0x2d69b2b7, float:1.3284199E-11)
                if (r5 == r6) goto L34
                goto L4b
            L34:
                java.lang.String r5 = "bugreport-map"
                boolean r5 = r3.equals(r5)     // Catch: java.lang.Throwable -> L3e
                if (r5 == 0) goto L4b
                r5 = 0
                goto L4c
            L3e:
                r1 = move-exception
                goto La2
            L40:
                java.lang.String r5 = "persistent-bugreport"
                boolean r5 = r3.equals(r5)     // Catch: java.lang.Throwable -> L3e
                if (r5 == 0) goto L4b
                r5 = r4
                goto L4c
            L4b:
                r5 = -1
            L4c:
                r6 = 0
                if (r5 == 0) goto L7b
                if (r5 == r4) goto L68
                java.lang.String r4 = "BugreportManagerService"
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3e
                r5.<init>()     // Catch: java.lang.Throwable -> L3e
                java.lang.String r6 = "Unknown tag while reading bugreport mapping file: "
                r5.append(r6)     // Catch: java.lang.Throwable -> L3e
                r5.append(r3)     // Catch: java.lang.Throwable -> L3e
                java.lang.String r3 = r5.toString()     // Catch: java.lang.Throwable -> L3e
                android.util.Slog.e(r4, r3)     // Catch: java.lang.Throwable -> L3e
                goto L1a
            L68:
                java.lang.String r3 = "bugreport-file"
                java.lang.String r3 = r1.getAttributeValue(r6, r3)     // Catch: java.lang.Throwable -> L3e
                java.lang.Object r4 = r7.mLock     // Catch: java.lang.Throwable -> L3e
                monitor-enter(r4)     // Catch: java.lang.Throwable -> L3e
                java.util.Set r5 = r7.mBugreportFilesToPersist     // Catch: java.lang.Throwable -> L78
                r5.add(r3)     // Catch: java.lang.Throwable -> L78
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L78
                goto L1a
            L78:
                r1 = move-exception
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L78
                throw r1     // Catch: java.lang.Throwable -> L3e
            L7b:
                java.lang.String r3 = "calling-uid"
                int r3 = r1.getAttributeInt(r6, r3)     // Catch: java.lang.Throwable -> L3e
                java.lang.String r4 = "calling-package"
                java.lang.String r4 = r1.getAttributeValue(r6, r4)     // Catch: java.lang.Throwable -> L3e
                java.lang.String r5 = "bugreport-file"
                java.lang.String r5 = r1.getAttributeValue(r6, r5)     // Catch: java.lang.Throwable -> L3e
                android.util.Pair r6 = new android.util.Pair     // Catch: java.lang.Throwable -> L3e
                java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch: java.lang.Throwable -> L3e
                r6.<init>(r3, r4)     // Catch: java.lang.Throwable -> L3e
                r7.addBugreportMapping(r6, r5)     // Catch: java.lang.Throwable -> L3e
                goto L1a
            L9a:
                r7.mReadBugreportMapping = r4     // Catch: java.lang.Throwable -> L3e
                if (r0 == 0) goto Lba
                r0.close()     // Catch: java.lang.Throwable -> Lad java.lang.Throwable -> Lad java.io.FileNotFoundException -> Lb3
                goto Lba
            La2:
                if (r0 == 0) goto Lac
                r0.close()     // Catch: java.lang.Throwable -> La8
                goto Lac
            La8:
                r0 = move-exception
                r1.addSuppressed(r0)     // Catch: java.lang.Throwable -> Lad java.lang.Throwable -> Lad java.io.FileNotFoundException -> Lb3
            Lac:
                throw r1     // Catch: java.lang.Throwable -> Lad java.lang.Throwable -> Lad java.io.FileNotFoundException -> Lb3
            Lad:
                android.util.AtomicFile r7 = r7.mMappingFile
                r7.delete()
                goto Lba
            Lb3:
                java.lang.String r7 = "BugreportManagerService"
                java.lang.String r0 = "Bugreport mapping file does not exist"
                android.util.Slog.i(r7, r0)
            Lba:
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.os.BugreportManagerServiceImpl.BugreportFileManager.readBugreportMappingLocked():void");
        }

        public final void writeBugreportDataLocked() {
            if (this.mBugreportFiles.isEmpty() && this.mBugreportFilesToPersist.isEmpty()) {
                return;
            }
            try {
                FileOutputStream startWrite = this.mMappingFile.startWrite();
                try {
                    TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                    resolveSerializer.startDocument((String) null, Boolean.TRUE);
                    resolveSerializer.startTag((String) null, "bugreport-data");
                    for (Map.Entry entry : this.mBugreportFiles.entrySet()) {
                        Pair pair = (Pair) entry.getKey();
                        Iterator it = ((ArraySet) entry.getValue()).iterator();
                        while (it.hasNext()) {
                            String str = (String) it.next();
                            resolveSerializer.startTag((String) null, "bugreport-map");
                            resolveSerializer.attributeInt(
                                    (String) null,
                                    "calling-uid",
                                    ((Integer) pair.first).intValue());
                            resolveSerializer.attribute(
                                    (String) null, "calling-package", (String) pair.second);
                            resolveSerializer.attribute((String) null, "bugreport-file", str);
                            resolveSerializer.endTag((String) null, "bugreport-map");
                        }
                    }
                    for (String str2 : this.mBugreportFilesToPersist) {
                        resolveSerializer.startTag((String) null, "persistent-bugreport");
                        resolveSerializer.attribute((String) null, "bugreport-file", str2);
                        resolveSerializer.endTag((String) null, "persistent-bugreport");
                    }
                    resolveSerializer.endTag((String) null, "bugreport-data");
                    resolveSerializer.endDocument();
                    this.mMappingFile.finishWrite(startWrite);
                    if (startWrite != null) {
                        startWrite.close();
                    }
                } finally {
                }
            } catch (IOException e) {
                Slog.e("BugreportManagerService", "Failed to write bugreport mapping file", e);
            }
        }
    }

    public final class DumpstateListener extends IDumpstateListener.Stub
            implements IBinder.DeathRecipient {
        public static int sNextId;
        public final Pair mCaller;
        public final boolean mConsentGranted;
        public boolean mDone;
        public final IDumpstate mDs;
        public final int mId;
        public final boolean mIsDeferredReport;
        public final boolean mKeepBugreportOnRetrieval;
        public final IDumpstateListener mListener;
        public int mProgress;
        public final boolean mReportFinishedFile;

        /* renamed from: -$$Nest$mdump, reason: not valid java name */
        public static void m742$$Nest$mdump(
                DumpstateListener dumpstateListener, PrintWriter printWriter) {
            dumpstateListener.getClass();
            printWriter.println("DumpstateListener:");
            printWriter.printf("  id: %d\n", Integer.valueOf(dumpstateListener.mId));
            printWriter.printf(
                    "  caller: %s\n",
                    BugreportManagerServiceImpl.callerToString(dumpstateListener.mCaller));
            printWriter.printf(
                    "  reports finished file: %b\n",
                    Boolean.valueOf(dumpstateListener.mReportFinishedFile));
            printWriter.printf("  progress: %d\n", Integer.valueOf(dumpstateListener.mProgress));
            printWriter.printf("  done: %b\n", Boolean.valueOf(dumpstateListener.mDone));
        }

        public DumpstateListener(
                IDumpstateListener iDumpstateListener,
                IDumpstate iDumpstate,
                Pair pair,
                boolean z,
                boolean z2,
                boolean z3,
                boolean z4) {
            int i = sNextId + 1;
            sNextId = i;
            this.mId = i;
            this.mListener = iDumpstateListener;
            this.mDs = iDumpstate;
            this.mCaller = pair;
            this.mReportFinishedFile = z;
            this.mKeepBugreportOnRetrieval = z2;
            this.mConsentGranted = z3;
            this.mIsDeferredReport = z4;
            try {
                iDumpstate.asBinder().linkToDeath(this, 0);
            } catch (RemoteException e) {
                Slog.e(
                        "BugreportManagerService",
                        "Unable to register Death Recipient for IDumpstate",
                        e);
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException unused) {
            }
            synchronized (BugreportManagerServiceImpl.this.mLock) {
                if (!this.mDone) {
                    Slog.e(
                            "BugreportManagerService",
                            "IDumpstate likely crashed. Notifying listener");
                    try {
                        this.mListener.onError(2);
                    } catch (RemoteException unused2) {
                    }
                }
            }
            this.mDs.asBinder().unlinkToDeath(this, 0);
        }

        @Override // android.os.IDumpstateListener
        public final void onError(int i) {
            Slogf.e("BugreportManagerService", "onError(): %d", Integer.valueOf(i));
            synchronized (BugreportManagerServiceImpl.this.mLock) {
                releaseItselfLocked();
                reportFinishedLocked("ErroCode: " + i);
            }
            this.mListener.onError(i);
        }

        @Override // android.os.IDumpstateListener
        public final void onFinished(String str) {
            Slogf.i("BugreportManagerService", "onFinished(): %s", str);
            synchronized (BugreportManagerServiceImpl.this.mLock) {
                releaseItselfLocked();
                reportFinishedLocked("File: " + str);
            }
            if (this.mReportFinishedFile) {
                BugreportFileManager bugreportFileManager =
                        BugreportManagerServiceImpl.this.mBugreportFileManager;
                Pair pair = this.mCaller;
                boolean z = this.mKeepBugreportOnRetrieval;
                bugreportFileManager.addBugreportMapping(pair, str);
                synchronized (bugreportFileManager.mLock) {
                    try {
                        if (Flags.onboardingBugreportV2Enabled()) {
                            if (z) {
                                bugreportFileManager.mBugreportFilesToPersist.add(str);
                            }
                            bugreportFileManager.writeBugreportDataLocked();
                        }
                    } finally {
                    }
                }
            }
            BugreportFileManager bugreportFileManager2 =
                    BugreportManagerServiceImpl.this.mBugreportFileManager;
            String str2 = (String) this.mCaller.second;
            boolean z2 = this.mConsentGranted;
            boolean z3 = this.mIsDeferredReport;
            bugreportFileManager2.getClass();
            if (Flags.onboardingConsentlessBugreports() && Build.IS_DEBUGGABLE) {
                synchronized (bugreportFileManager2.mLock) {
                    try {
                        if (z2) {
                            ((HashMap) bugreportFileManager2.mConsentGranted)
                                    .put(
                                            str2,
                                            new Pair(
                                                    Long.valueOf(System.currentTimeMillis()),
                                                    Boolean.valueOf(z3)));
                        } else if (!z3) {
                            if (((HashMap) bugreportFileManager2.mConsentGranted)
                                    .containsKey(str2)) {
                                ((HashMap) bugreportFileManager2.mConsentGranted)
                                        .put(
                                                str2,
                                                new Pair(
                                                        (Long)
                                                                ((Pair)
                                                                                ((HashMap)
                                                                                                bugreportFileManager2
                                                                                                        .mConsentGranted)
                                                                                        .get(str2))
                                                                        .first,
                                                        Boolean.FALSE));
                            } else {
                                Slog.e(
                                        "BugreportManagerService",
                                        "Previous consent from package: "
                                                + str2
                                                + " shouldhave been logged.");
                            }
                        }
                    } finally {
                    }
                }
            }
            this.mListener.onFinished(str);
        }

        @Override // android.os.IDumpstateListener
        public final void onProgress(int i) {
            this.mProgress = i;
            this.mListener.onProgress(i);
        }

        @Override // android.os.IDumpstateListener
        public final void onScreenshotTaken(boolean z) {
            this.mListener.onScreenshotTaken(z);
        }

        @Override // android.os.IDumpstateListener
        public final void onUiIntensiveBugreportDumpsFinished() {
            this.mListener.onUiIntensiveBugreportDumpsFinished();
        }

        public final void releaseItselfLocked() {
            this.mDone = true;
            BugreportManagerServiceImpl bugreportManagerServiceImpl =
                    BugreportManagerServiceImpl.this;
            if (bugreportManagerServiceImpl.mCurrentDumpstateListener == this) {
                bugreportManagerServiceImpl.mCurrentDumpstateListener = null;
                return;
            }
            Slogf.w(
                    "BugreportManagerService",
                    "releaseItselfLocked(): "
                            + this
                            + " is finished, but current listener is "
                            + BugreportManagerServiceImpl.this.mCurrentDumpstateListener);
        }

        public final void reportFinishedLocked(String str) {
            BugreportManagerServiceImpl bugreportManagerServiceImpl =
                    BugreportManagerServiceImpl.this;
            bugreportManagerServiceImpl.mNumberFinishedBugreports++;
            bugreportManagerServiceImpl.mFinishedBugreports.log(
                    "Caller: "
                            + BugreportManagerServiceImpl.callerToString(this.mCaller)
                            + " "
                            + str);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("DumpstateListener[id=");
            sb.append(this.mId);
            sb.append(", progress=");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mProgress, sb, "]");
        }
    }

    public final class Injector {
        public ArraySet mAllowlistedPackages;
        public Context mContext;
        public AtomicFile mMappingFile;
        public RoleManagerWrapper mRoleManagerWrapper;

        public final class RoleManagerWrapper {
            public RoleManagerWrapper() {}
        }
    }

    public BugreportManagerServiceImpl(Injector injector) {
        this.mInjector = injector;
        Context context = injector.mContext;
        this.mContext = context;
        this.mAppOps = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mTelephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        this.mBugreportFileManager = new BugreportFileManager(injector.mMappingFile);
        this.mBugreportAllowlistedPackages = injector.mAllowlistedPackages;
    }

    public static String callerToString(Pair pair) {
        if (pair == null) {
            return "N/A";
        }
        return ((String) pair.second) + "/" + pair.first;
    }

    public static void reportError(IDumpstateListener iDumpstateListener, int i) {
        try {
            iDumpstateListener.onError(i);
        } catch (RemoteException e) {
            Slog.w(
                    "BugreportManagerService",
                    "onError() transaction threw RemoteException: " + e.getMessage());
        }
    }

    @Override // android.os.IDumpstate
    public final void cancelBugreport(int i, String str) {
        int callingUid = Binder.getCallingUid();
        enforcePermission(callingUid, str, true);
        Slogf.i(
                "BugreportManagerService",
                "Cancelling bugreport for %s / %d",
                str,
                Integer.valueOf(callingUid));
        synchronized (this.mLock) {
            IDumpstate asInterface =
                    IDumpstate.Stub.asInterface(ServiceManager.getService("dumpstate"));
            if (asInterface == null) {
                Slog.w(
                        "BugreportManagerService",
                        "cancelBugreport: Could not find native dumpstate service");
                return;
            }
            try {
                asInterface.cancelBugreport(callingUid, str);
            } catch (RemoteException e) {
                Slog.e("BugreportManagerService", "RemoteException in cancelBugreport", e);
            }
            stopDumpstateBinderServiceLocked();
        }
    }

    @Override // android.os.Binder
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "BugreportManagerService", printWriter)) {
            printWriter.printf("Allow-listed packages: %s\n", this.mBugreportAllowlistedPackages);
            synchronized (this.mLock) {
                try {
                    printWriter.print("Pre-dumped data UID: ");
                    if (this.mPreDumpedDataUid.isEmpty()) {
                        printWriter.println("none");
                    } else {
                        printWriter.println(this.mPreDumpedDataUid.getAsInt());
                    }
                    DumpstateListener dumpstateListener = this.mCurrentDumpstateListener;
                    if (dumpstateListener == null) {
                        printWriter.println("Not taking a bug report");
                    } else {
                        DumpstateListener.m742$$Nest$mdump(dumpstateListener, printWriter);
                    }
                    int i = this.mNumberFinishedBugreports;
                    if (i == 0) {
                        printWriter.println("No finished bugreports");
                    } else {
                        Integer valueOf = Integer.valueOf(i);
                        int i2 = this.mNumberFinishedBugreports;
                        printWriter.printf(
                                "%d finished bugreport%s. Last %d:\n",
                                valueOf, i2 > 1 ? "s" : "", Integer.valueOf(Math.min(i2, 20)));
                        this.mFinishedBugreports.dump("  ", printWriter);
                    }
                } finally {
                }
            }
            synchronized (this.mBugreportFileManager.mLock) {
                try {
                    if (!this.mBugreportFileManager.mReadBugreportMapping) {
                        printWriter.println("Has not read bugreport mapping");
                    }
                    int size = this.mBugreportFileManager.mBugreportFiles.size();
                    printWriter.printf(
                            "%d pending file%s", Integer.valueOf(size), size > 1 ? "s" : "");
                    if (size > 0) {
                        for (int i3 = 0; i3 < size; i3++) {
                            printWriter.printf(
                                    "  %s: %s\n",
                                    callerToString(
                                            (Pair)
                                                    this.mBugreportFileManager.mBugreportFiles
                                                            .keyAt(i3)),
                                    (ArraySet)
                                            this.mBugreportFileManager.mBugreportFiles.valueAt(i3));
                        }
                    } else {
                        printWriter.println();
                    }
                } finally {
                }
            }
        }
    }

    public final void enforcePermission(int i, String str, boolean z) {
        long clearCallingIdentity;
        this.mAppOps.checkPackage(i, str);
        boolean contains = this.mBugreportAllowlistedPackages.contains(str);
        if (!contains) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                contains =
                        ((RoleManager) Injector.this.mContext.getSystemService(RoleManager.class))
                                .getRoleHolders("android.app.role.SYSTEM_AUTOMOTIVE_PROJECTION")
                                .contains(str);
            } finally {
            }
        }
        if (contains
                && this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) {
            return;
        }
        clearCallingIdentity = Binder.clearCallingIdentity();
        if (z) {
            try {
                if (this.mTelephonyManager.checkCarrierPrivilegesForPackageAnyPhone(str) == 1) {
                    return;
                }
            } finally {
            }
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(
                " does not hold the DUMP permission or is not bugreport-whitelisted or does not"
                    + " have an allowed role ");
        sb.append(z ? "and does not have carrier privileges " : "");
        sb.append("to request a bugreport");
        String sb2 = sb.toString();
        Slog.w("BugreportManagerService", sb2);
        throw new SecurityException(sb2);
    }

    @Override // android.os.IDumpstate
    public final void preDumpUiData(String str) {
        enforcePermission(Binder.getCallingUid(), str, true);
        synchronized (this.mLock) {
            preDumpUiDataLocked(str);
        }
    }

    public final void preDumpUiDataLocked(String str) {
        this.mPreDumpedDataUid = OptionalInt.empty();
        if (IDumpstate.Stub.asInterface(ServiceManager.getService("dumpstate")) != null) {
            Slog.e(
                    "BugreportManagerService",
                    "'dumpstate' is already running. Cannot pre-dump data while another operation"
                        + " is currently in progress.");
            return;
        }
        IDumpstate startAndGetDumpstateBinderServiceLocked =
                startAndGetDumpstateBinderServiceLocked();
        if (startAndGetDumpstateBinderServiceLocked == null) {
            Slog.e("BugreportManagerService", "Unable to get bugreport service");
            return;
        }
        try {
            startAndGetDumpstateBinderServiceLocked.preDumpUiData(str);
            stopDumpstateBinderServiceLocked();
            this.mPreDumpedDataUid = OptionalInt.of(Binder.getCallingUid());
        } catch (RemoteException unused) {
            stopDumpstateBinderServiceLocked();
        } catch (Throwable th) {
            stopDumpstateBinderServiceLocked();
            throw th;
        }
    }

    @Override // android.os.IDumpstate
    public final void retrieveBugreport(
            int i,
            String str,
            int i2,
            FileDescriptor fileDescriptor,
            String str2,
            boolean z,
            boolean z2,
            IDumpstateListener iDumpstateListener) {
        int callingUid = Binder.getCallingUid();
        enforcePermission(callingUid, str, false);
        Slogf.i(
                "BugreportManagerService",
                "Retrieving bugreport for %s / %d",
                str,
                Integer.valueOf(callingUid));
        try {
            BugreportFileManager bugreportFileManager = this.mBugreportFileManager;
            Context context = this.mContext;
            bugreportFileManager.ensureCallerPreviouslyGeneratedFile(
                    context,
                    context.getPackageManager(),
                    new Pair(Integer.valueOf(callingUid), str),
                    i2,
                    str2);
            synchronized (this.mLock) {
                try {
                    if (IDumpstate.Stub.asInterface(ServiceManager.getService("dumpstate"))
                            != null) {
                        Slog.w(
                                "BugreportManagerService",
                                "'dumpstate' is already running. Cannot retrieve a bugreport while"
                                    + " another one is currently in progress.");
                        reportError(iDumpstateListener, 5);
                        return;
                    }
                    IDumpstate startAndGetDumpstateBinderServiceLocked =
                            startAndGetDumpstateBinderServiceLocked();
                    if (startAndGetDumpstateBinderServiceLocked == null) {
                        Slog.w("BugreportManagerService", "Unable to get bugreport service");
                        reportError(iDumpstateListener, 2);
                        return;
                    }
                    boolean canSkipConsentScreen =
                            this.mBugreportFileManager.canSkipConsentScreen(str, false);
                    DumpstateListener dumpstateListener =
                            new DumpstateListener(
                                    iDumpstateListener,
                                    startAndGetDumpstateBinderServiceLocked,
                                    new Pair(Integer.valueOf(callingUid), str),
                                    true,
                                    false,
                                    !canSkipConsentScreen,
                                    true);
                    boolean contains =
                            Flags.onboardingBugreportV2Enabled()
                                    ? this.mBugreportFileManager.mBugreportFilesToPersist.contains(
                                            str2)
                                    : false;
                    DumpstateListener dumpstateListener2 = this.mCurrentDumpstateListener;
                    if (dumpstateListener2 != null) {
                        Slogf.w(
                                "BugreportManagerService",
                                "setCurrentDumpstateListenerLocked(%s): called when"
                                    + " mCurrentDumpstateListener is already set (%s)",
                                dumpstateListener,
                                dumpstateListener2);
                    }
                    this.mCurrentDumpstateListener = dumpstateListener;
                    try {
                        startAndGetDumpstateBinderServiceLocked.retrieveBugreport(
                                callingUid,
                                str,
                                i2,
                                fileDescriptor,
                                str2,
                                contains,
                                canSkipConsentScreen,
                                dumpstateListener);
                    } catch (RemoteException e) {
                        Slog.e(
                                "BugreportManagerService",
                                "RemoteException in retrieveBugreport",
                                e);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (IllegalArgumentException e2) {
            Slog.e("BugreportManagerService", e2.getMessage());
            reportError(iDumpstateListener, 6);
        }
    }

    public final IDumpstate startAndGetDumpstateBinderServiceLocked() {
        this.mInjector.getClass();
        SystemProperties.set("ctl.start", "bugreportd");
        IDumpstate iDumpstate = null;
        int i = 500;
        boolean z = false;
        int i2 = 0;
        while (true) {
            if (z) {
                break;
            }
            iDumpstate = IDumpstate.Stub.asInterface(ServiceManager.getService("dumpstate"));
            if (iDumpstate != null) {
                Slog.i("BugreportManagerService", "Got bugreport service handle.");
                break;
            }
            SystemClock.sleep(i);
            Slog.i(
                    "BugreportManagerService",
                    "Waiting to get dumpstate service handle (" + i2 + "ms)");
            i2 += i;
            i *= 2;
            z = ((long) i2) > 30000;
        }
        if (z) {
            BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                    i2,
                    "Timed out waiting to get dumpstate service handle (",
                    "ms)",
                    "BugreportManagerService");
        }
        return iDumpstate;
    }

    @Override // android.os.IDumpstate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startBugreport(
            int r20,
            java.lang.String r21,
            java.io.FileDescriptor r22,
            java.io.FileDescriptor r23,
            int r24,
            int r25,
            android.os.IDumpstateListener r26,
            boolean r27,
            boolean r28) {
        /*
            Method dump skipped, instructions count: 477
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.os.BugreportManagerServiceImpl.startBugreport(int,"
                    + " java.lang.String, java.io.FileDescriptor, java.io.FileDescriptor, int, int,"
                    + " android.os.IDumpstateListener, boolean, boolean):void");
    }

    public final void stopDumpstateBinderServiceLocked() {
        this.mInjector.getClass();
        SystemProperties.set("ctl.stop", "bugreportd");
    }
}
