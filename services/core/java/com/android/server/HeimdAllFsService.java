package com.android.server;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.IInstalld;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Slog;
import com.android.server.backup.BackupManagerConstants;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HeimdAllFsService {
    public static double THRESHOLD_COMPRESS_TRIGGER = 0.1d;
    public static long THRESHOLD_COMPRESS_UNUSED_MS = 1382400000;
    public static double THRESHOLD_DECOMPRESS_TRIGGER = 0.15d;
    public final Context mContext;
    public static final Boolean[] heimdAllFSSupported = {null, null, null};
    public static boolean mForceService = false;
    public static String mForceCompressService = "";
    public static boolean mForceDefragService = false;
    public static boolean mDryrun = false;
    public static boolean mIsUserTrial = false;
    public List mPackagesInfo = null;
    public AnonymousClass1 mHeimdallFsThread = null;
    public CountDownLatch mHeimdallFsLatch = null;
    public volatile IInstalld mInstalld = IInstalld.Stub.asInterface(ServiceManager.getService("installd"));

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FunctionOrder {
        public static final /* synthetic */ FunctionOrder[] $VALUES;
        public static final FunctionOrder COMPRESS;
        public static final FunctionOrder DEFRAG;

        static {
            FunctionOrder functionOrder = new FunctionOrder("DEFRAG", 0);
            DEFRAG = functionOrder;
            FunctionOrder functionOrder2 = new FunctionOrder("COMPRESS", 1);
            COMPRESS = functionOrder2;
            $VALUES = new FunctionOrder[]{functionOrder, functionOrder2};
        }

        public static FunctionOrder valueOf(String str) {
            return (FunctionOrder) Enum.valueOf(FunctionOrder.class, str);
        }

        public static FunctionOrder[] values() {
            return (FunctionOrder[]) $VALUES.clone();
        }
    }

    public HeimdAllFsService(Context context) {
        this.mContext = context;
        mDryrun = SystemProperties.get("persist.sys.heimdallfs.dryrun").equals("true");
        mForceService = SystemProperties.get("persist.sys.heimdallfs.force").equals("true");
        mForceCompressService = SystemProperties.get("persist.sys.heimdallfs.force.compress.mode");
        mForceDefragService = SystemProperties.get("persist.sys.heimdallfs.force.defrag.mode").equals("true");
        boolean z = false;
        try {
            if (context.getPackageManager().getPackageInfo("com.salab.issuetracker", 0) != null) {
                z = true;
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        mIsUserTrial = z;
        StringBuilder sb = new StringBuilder("DEBUG: TRUE Dry-run: ");
        sb.append(mDryrun ? "TRUE" : "FALSE");
        sb.append(" Last-run: ");
        sb.append(SystemProperties.get("sys.heimdallfs.todayinfo"));
        sb.append(" mForceService: ");
        sb.append(mForceService ? "TRUE" : "FALSE");
        sb.append(" mForceDefragService: ");
        sb.append(mForceDefragService ? "TRUE" : "FALSE");
        sb.append(" mForceCompressService: ");
        sb.append(mForceCompressService);
        sb.append(" mIsUserTrial: ");
        HeimdAllFsService$$ExternalSyntheticOutline0.m("HeimdAllFS", sb, mIsUserTrial);
    }

    public static FunctionOrder getFunctionOrder() {
        return (FunctionOrder) Enum.valueOf(FunctionOrder.class, "COMPRESS");
    }

    /* JADX WARN: Code restructure failed: missing block: B:59:0x008e, code lost:
    
        if (r6.contains("supported") == false) goto L105;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v16 */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r6v2, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v7, types: [java.lang.String] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isServiceActivate(com.android.server.HeimdAllFsService.FunctionOrder r9) {
        /*
            Method dump skipped, instructions count: 290
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.HeimdAllFsService.isServiceActivate(com.android.server.HeimdAllFsService$FunctionOrder):boolean");
    }

    public static void setTriggerThreshold() {
        double d = THRESHOLD_COMPRESS_TRIGGER;
        try {
            int parseInt = Integer.parseInt(SystemProperties.get("debug.sys.heimdallfs.ut.compress.trigger.percentile"));
            if (parseInt >= 1 && parseInt <= 100) {
                d = parseInt / 100.0d;
            }
        } catch (NumberFormatException unused) {
        }
        THRESHOLD_COMPRESS_TRIGGER = d;
        double d2 = THRESHOLD_DECOMPRESS_TRIGGER;
        try {
            int parseInt2 = Integer.parseInt(SystemProperties.get("debug.sys.heimdallfs.ut.decompress.trigger.percentile"));
            if (parseInt2 >= 1 && parseInt2 <= 100) {
                d2 = parseInt2 / 100.0d;
            }
        } catch (NumberFormatException unused2) {
        }
        THRESHOLD_DECOMPRESS_TRIGGER = d2;
        long j = THRESHOLD_COMPRESS_UNUSED_MS;
        try {
            long parseLong = Long.parseLong(SystemProperties.get("debug.sys.heimdallfs.ut.compress.trigger.time")) * BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
            if (parseLong >= 0 && parseLong <= 8640000000L) {
                j = parseLong;
            }
        } catch (NumberFormatException unused3) {
        }
        THRESHOLD_COMPRESS_UNUSED_MS = j;
        mForceCompressService = SystemProperties.get("debug.sys.heimdallfs.force.compress.mode");
        mForceService = SystemProperties.get("debug.sys.heimdallfs.force").equals("true");
        Slog.i("HeimdAllFS", "Change threshold for UT debug");
        StringBuilder sb = new StringBuilder(" THRESHOLD_COMPRESS_TRIGGER: ");
        sb.append(THRESHOLD_COMPRESS_TRIGGER);
        sb.append(" THRESHOLD_DECOMPRESS_TRIGGER: ");
        sb.append(THRESHOLD_DECOMPRESS_TRIGGER);
        sb.append(" THRESHOLD_COMPRESS_UNUSED_MS: ");
        sb.append(THRESHOLD_COMPRESS_UNUSED_MS);
        sb.append(" mForceCompressService: ");
        sb.append(mForceCompressService);
        sb.append(" mForceService: ");
        HeimdAllFsService$$ExternalSyntheticOutline0.m("HeimdAllFS", sb, mForceService);
    }

    public final void doCompressAction(String str, boolean z) {
        try {
            StringBuilder sb = new StringBuilder("doCompressAction: ");
            sb.append(z ? "Comp" : "Decomp");
            sb.append(": ");
            sb.append(str);
            Slog.d("HeimdAllFS", sb.toString());
            if (mDryrun) {
                return;
            }
            this.mInstalld.compressFile(str, z);
        } catch (RemoteException unused) {
            HeimdAllFsService$$ExternalSyntheticOutline0.m("Error: Compress/Decompress RemoteException, ", str, "HeimdAllFS");
        } catch (Exception unused2) {
            BootReceiver$$ExternalSyntheticOutline0.m("Error: Exception!! ", str, "HeimdAllFS");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:81:0x027e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01cc A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void doCompressPackages(java.util.List r12, boolean r13) {
        /*
            Method dump skipped, instructions count: 663
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.HeimdAllFsService.doCompressPackages(java.util.List, boolean):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0135 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0127 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List getPackagesOnUserdata() {
        /*
            Method dump skipped, instructions count: 411
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.HeimdAllFsService.getPackagesOnUserdata():java.util.List");
    }

    public final void scanCompressedFileAction(int i, String str) {
        Slog.d("HeimdAllFS", "Scan Compressed File!! (" + i + "): " + str);
        try {
            this.mInstalld.scanApkStats(str, i);
        } catch (RemoteException unused) {
            HeimdAllFsService$$ExternalSyntheticOutline0.m("Error: scanApkStats RemoteException, ", str, "HeimdAllFS");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0140 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x00f8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startCompress(java.util.List r17) {
        /*
            Method dump skipped, instructions count: 570
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.HeimdAllFsService.startCompress(java.util.List):void");
    }

    public final void waitForFinished() {
        try {
            if (this.mHeimdallFsLatch != null) {
                Slog.i("HeimdAllFS", "waitForFinished, HeimdAllLatch await");
                this.mHeimdallFsLatch.await();
                this.mHeimdallFsLatch = null;
            }
            Slog.i("HeimdAllFS", "waitForFinished, HeimdAllLatch await End");
        } catch (InterruptedException unused) {
            Slog.w("HeimdAllFS", "Interrupt while waiting for heimdallFsLatch:CountDownLatch(1)");
        }
    }
}
