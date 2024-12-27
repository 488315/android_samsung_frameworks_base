package com.android.server.backup.utils;

import android.content.Context;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Slog;

import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.backup.FileMetadata;
import com.android.server.backup.restore.FullRestoreEngine$$ExternalSyntheticLambda0;

import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class RestoreUtils {
    public static boolean mPrivilegeApp;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalIntentReceiver {
        public final Object mLock = new Object();
        public Intent mResult = null;
        public final AnonymousClass1 mLocalSender =
                new IIntentSender
                        .Stub() { // from class:
                                  // com.android.server.backup.utils.RestoreUtils.LocalIntentReceiver.1
                    public final void send(
                            int i,
                            Intent intent,
                            String str,
                            IBinder iBinder,
                            IIntentReceiver iIntentReceiver,
                            String str2,
                            Bundle bundle) {
                        synchronized (LocalIntentReceiver.this.mLock) {
                            LocalIntentReceiver localIntentReceiver = LocalIntentReceiver.this;
                            localIntentReceiver.mResult = intent;
                            localIntentReceiver.mLock.notifyAll();
                        }
                    }
                };

        public final Intent getResult() {
            Intent intent;
            synchronized (this.mLock) {
                while (true) {
                    intent = this.mResult;
                    if (intent == null) {
                        try {
                            this.mLock.wait();
                        } catch (InterruptedException unused) {
                        }
                    }
                }
            }
            return intent;
        }
    }

    public static int createSession(Context context, String str) {
        PackageInstaller packageInstaller = context.getPackageManager().getPackageInstaller();
        PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(1);
        sessionParams.setInstallerPackageName(str);
        try {
            return packageInstaller.createSession(sessionParams);
        } catch (Exception e) {
            KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(
                    e, "Exception in session id created", "BackupManagerService");
            return 0;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:110:0x0217 A[Catch: Exception -> 0x0215, TRY_LEAVE, TryCatch #8 {Exception -> 0x0215, blocks: (B:110:0x0217, B:118:0x0211, B:113:0x020b), top: B:108:0x0209, inners: #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:112:0x020b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00da A[Catch: IOException -> 0x00ec, TryCatch #11 {IOException -> 0x00ec, blocks: (B:50:0x00cd, B:52:0x00da, B:58:0x00f0, B:60:0x00fe, B:63:0x01e2, B:65:0x01e4, B:79:0x0120, B:81:0x0134, B:83:0x0138, B:86:0x0155, B:89:0x017e, B:91:0x0188, B:93:0x018e, B:94:0x01a8, B:95:0x01c3), top: B:49:0x00cd, inners: #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00f0 A[Catch: IOException -> 0x00ec, TryCatch #11 {IOException -> 0x00ec, blocks: (B:50:0x00cd, B:52:0x00da, B:58:0x00f0, B:60:0x00fe, B:63:0x01e2, B:65:0x01e4, B:79:0x0120, B:81:0x0134, B:83:0x0138, B:86:0x0155, B:89:0x017e, B:91:0x0188, B:93:0x018e, B:94:0x01a8, B:95:0x01c3), top: B:49:0x00cd, inners: #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01e2 A[Catch: IOException -> 0x00ec, TryCatch #11 {IOException -> 0x00ec, blocks: (B:50:0x00cd, B:52:0x00da, B:58:0x00f0, B:60:0x00fe, B:63:0x01e2, B:65:0x01e4, B:79:0x0120, B:81:0x0134, B:83:0x0138, B:86:0x0155, B:89:0x017e, B:91:0x0188, B:93:0x018e, B:94:0x01a8, B:95:0x01c3), top: B:49:0x00cd, inners: #5 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean installApk(
            java.io.InputStream r21,
            android.content.Context r22,
            com.android.server.backup.restore.RestoreDeleteObserver r23,
            java.util.HashMap r24,
            java.util.HashMap r25,
            com.android.server.backup.FileMetadata r26,
            java.lang.String r27,
            com.android.server.backup.restore.FullRestoreEngine$$ExternalSyntheticLambda0 r28,
            int r29) {
        /*
            Method dump skipped, instructions count: 553
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.backup.utils.RestoreUtils.installApk(java.io.InputStream,"
                    + " android.content.Context,"
                    + " com.android.server.backup.restore.RestoreDeleteObserver, java.util.HashMap,"
                    + " java.util.HashMap, com.android.server.backup.FileMetadata,"
                    + " java.lang.String,"
                    + " com.android.server.backup.restore.FullRestoreEngine$$ExternalSyntheticLambda0,"
                    + " int):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0153 A[Catch: IOException -> 0x005c, TryCatch #6 {IOException -> 0x005c, blocks: (B:3:0x001b, B:9:0x003e, B:11:0x004b, B:18:0x005f, B:20:0x006d, B:24:0x0153, B:25:0x0155, B:30:0x015e, B:34:0x0167, B:38:0x008c, B:40:0x00a0, B:42:0x00a4, B:43:0x00c2, B:46:0x00e9, B:49:0x00f4, B:51:0x00fe, B:53:0x0104, B:56:0x011d, B:59:0x0138, B:75:0x017b, B:76:0x017e, B:27:0x0156, B:28:0x015b, B:5:0x0028, B:8:0x003b, B:72:0x017a, B:71:0x0177, B:66:0x0171, B:7:0x002c), top: B:2:0x001b, inners: #2, #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean installApkSplitSupport(
            android.content.Context r20,
            com.android.server.backup.restore.RestoreDeleteObserver r21,
            java.util.HashMap r22,
            java.util.HashMap r23,
            com.android.server.backup.FileMetadata r24,
            int r25,
            int r26) {
        /*
            Method dump skipped, instructions count: 389
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.backup.utils.RestoreUtils.installApkSplitSupport(android.content.Context,"
                    + " com.android.server.backup.restore.RestoreDeleteObserver, java.util.HashMap,"
                    + " java.util.HashMap, com.android.server.backup.FileMetadata, int,"
                    + " int):boolean");
    }

    public static void setPrivilegeApp(boolean z) {
        mPrivilegeApp = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v2, types: [android.content.pm.PackageInstaller] */
    /* JADX WARN: Type inference failed for: r7v6, types: [android.content.pm.PackageInstaller$Session] */
    public static boolean writeSession(
            Context context,
            InputStream inputStream,
            FileMetadata fileMetadata,
            FullRestoreEngine$$ExternalSyntheticLambda0 fullRestoreEngine$$ExternalSyntheticLambda0,
            int i) {
        PackageInstaller.Session packageInstaller =
                context.getPackageManager().getPackageInstaller();
        try {
            try {
                packageInstaller = packageInstaller.openSession(i);
                try {
                    OutputStream openWrite =
                            packageInstaller.openWrite(fileMetadata.path, 0L, fileMetadata.size);
                    try {
                        byte[] bArr = new byte[32768];
                        long j = fileMetadata.size;
                        while (j > 0) {
                            long j2 = 32768;
                            if (j2 >= j) {
                                j2 = j;
                            }
                            int read = inputStream.read(bArr, 0, (int) j2);
                            openWrite.write(bArr, 0, read);
                            j -= read;
                        }
                        packageInstaller.fsync(openWrite);
                        if (openWrite != null) {
                            openWrite.close();
                        }
                    } catch (Throwable th) {
                        if (openWrite != null) {
                            try {
                                openWrite.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (InterruptedIOException e) {
                    Slog.e(
                            "BackupManagerService",
                            " InterruptedIOException in apkStream.close()" + e);
                }
                if (packageInstaller != 0) {
                    packageInstaller.close();
                }
                return true;
            } finally {
            }
        } catch (Exception e2) {
            Slog.e("BackupManagerService", " Exception in writeSession " + e2);
            e2.printStackTrace();
            return false;
        }
    }
}
