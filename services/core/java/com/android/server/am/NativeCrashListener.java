package com.android.server.am;

import android.app.ApplicationErrorReport;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructTimeval;
import android.system.UnixSocketAddress;
import android.util.Slog;

import com.samsung.android.knoxguard.service.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class NativeCrashListener extends Thread {
    public final ActivityManagerService mAm;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NativeCrashReporter extends Thread {
        public final ProcessRecord mApp;
        public final String mCrashReport;
        public final boolean mGwpAsanRecoverableCrash;
        public final int mSignal;

        public NativeCrashReporter(ProcessRecord processRecord, int i, boolean z, String str) {
            super("NativeCrashReport");
            this.mApp = processRecord;
            this.mSignal = i;
            this.mGwpAsanRecoverableCrash = z;
            this.mCrashReport = str;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            try {
                ApplicationErrorReport.CrashInfo crashInfo = new ApplicationErrorReport.CrashInfo();
                crashInfo.exceptionClassName = "Native crash";
                crashInfo.exceptionMessage = Os.strsignal(this.mSignal);
                crashInfo.throwFileName = "unknown";
                crashInfo.throwClassName = "unknown";
                crashInfo.throwMethodName = "unknown";
                crashInfo.stackTrace = this.mCrashReport;
                ActivityManagerService activityManagerService = NativeCrashListener.this.mAm;
                String str =
                        this.mGwpAsanRecoverableCrash ? "native_recoverable_crash" : "native_crash";
                ProcessRecord processRecord = this.mApp;
                activityManagerService.handleApplicationCrashInner(
                        str, processRecord, processRecord.processName, crashInfo);
            } catch (Exception e) {
                Slog.e("NativeCrashListener", "Unable to report native crash", e);
            }
        }
    }

    public NativeCrashListener(ActivityManagerService activityManagerService) {
        this.mAm = activityManagerService;
    }

    public static int unpackInt(int i, byte[] bArr) {
        return (bArr[i + 3] & 255)
                | ((bArr[i] & 255) << 24)
                | ((bArr[i + 1] & 255) << 16)
                | ((bArr[i + 2] & 255) << 8);
    }

    public final void consumeNativeCrashData(FileDescriptor fileDescriptor) {
        ProcessRecord processRecord;
        byte[] bArr = new byte[4096];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
        try {
            StructTimeval fromMillis = StructTimeval.fromMillis(10000L);
            int i = OsConstants.SOL_SOCKET;
            Os.setsockoptTimeval(fileDescriptor, i, OsConstants.SO_RCVTIMEO, fromMillis);
            Os.setsockoptTimeval(fileDescriptor, i, OsConstants.SO_SNDTIMEO, fromMillis);
            int i2 = 9;
            int i3 = 0;
            while (true) {
                if (i2 <= 0) {
                    break;
                }
                int read = Os.read(fileDescriptor, bArr, i3, i2);
                if (read <= 0) {
                    i3 = -1;
                    break;
                } else {
                    i2 -= read;
                    i3 += read;
                }
            }
            if (i3 != 9) {
                Slog.e("NativeCrashListener", "Unable to read from debuggerd");
                return;
            }
            int unpackInt = unpackInt(0, bArr);
            int unpackInt2 = unpackInt(4, bArr);
            boolean z = bArr[8] != 0;
            if (unpackInt < 0) {
                Slog.e("NativeCrashListener", "Bogus pid!");
                return;
            }
            synchronized (this.mAm.mPidsSelfLocked) {
                processRecord = this.mAm.mPidsSelfLocked.get(unpackInt);
            }
            if (processRecord == null) {
                Slog.w("NativeCrashListener", "Couldn't find ProcessRecord for pid " + unpackInt);
                return;
            }
            if (!processRecord.mPersistent
                    || Constants.SYSTEMUI_PACKAGE_NAME.equals(processRecord.processName)) {
                while (true) {
                    int read2 = Os.read(fileDescriptor, bArr, 0, 4096);
                    if (read2 > 0) {
                        int i4 = read2 - 1;
                        if (bArr[i4] == 0) {
                            byteArrayOutputStream.write(bArr, 0, i4);
                            break;
                        }
                        byteArrayOutputStream.write(bArr, 0, read2);
                    }
                    if (read2 <= 0) {
                        break;
                    }
                }
                if (!z) {
                    ActivityManagerService activityManagerService = this.mAm;
                    ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService) {
                        try {
                            ActivityManagerProcLock activityManagerProcLock = this.mAm.mProcLock;
                            ActivityManagerService.boostPriorityForProcLockedSection();
                            synchronized (activityManagerProcLock) {
                                try {
                                    ProcessErrorStateRecord processErrorStateRecord =
                                            processRecord.mErrorState;
                                    processErrorStateRecord.mCrashing = true;
                                    processErrorStateRecord
                                                    .mApp
                                                    .mWindowProcessController
                                                    .mCrashing =
                                            true;
                                    processRecord.mErrorState.mForceCrashReport = true;
                                } catch (Throwable th) {
                                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                                    throw th;
                                }
                            }
                            ActivityManagerService.resetPriorityAfterProcLockedSection();
                        } catch (Throwable th2) {
                            ActivityManagerService.resetPriorityAfterLockedSection();
                            throw th2;
                        }
                    }
                    ActivityManagerService.resetPriorityAfterLockedSection();
                }
                new NativeCrashReporter(
                                processRecord,
                                unpackInt2,
                                z,
                                new String(byteArrayOutputStream.toByteArray(), "UTF-8"))
                        .start();
            }
        } catch (Exception e) {
            Slog.e("NativeCrashListener", "Exception dealing with report", e);
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        byte[] bArr = new byte[1];
        File file = new File("/data/system/ndebugsocket");
        if (file.exists()) {
            file.delete();
        }
        try {
            FileDescriptor socket = Os.socket(OsConstants.AF_UNIX, OsConstants.SOCK_STREAM, 0);
            Os.bind(socket, UnixSocketAddress.createFileSystem("/data/system/ndebugsocket"));
            Os.listen(socket, 1);
            Os.chmod("/data/system/ndebugsocket", 511);
            while (true) {
                FileDescriptor fileDescriptor = null;
                try {
                    try {
                        fileDescriptor = Os.accept(socket, null);
                        if (fileDescriptor != null) {
                            consumeNativeCrashData(fileDescriptor);
                        }
                    } catch (Exception e) {
                        Slog.w("NativeCrashListener", "Error handling connection", e);
                        if (fileDescriptor != null) {
                            Os.write(fileDescriptor, bArr, 0, 1);
                        }
                    }
                    if (fileDescriptor != null) {
                        try {
                            Os.write(fileDescriptor, bArr, 0, 1);
                        } catch (Exception unused) {
                        }
                        try {
                            Os.close(fileDescriptor);
                        } catch (ErrnoException unused2) {
                        }
                    }
                } finally {
                    if (fileDescriptor != null) {
                        try {
                            Os.close(fileDescriptor);
                        } catch (ErrnoException unused3) {
                        }
                    }
                }
            }
        } catch (Exception e2) {
            Slog.e("NativeCrashListener", "Unable to init native debug socket!", e2);
        }
    }
}
