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

public abstract class RestoreUtils {
    public static boolean mPrivilegeApp;

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
