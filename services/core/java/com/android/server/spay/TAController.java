package com.android.server.spay;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.spay.CertInfo;
import android.spay.ITAController;
import android.spay.TACommandRequest;
import android.spay.TACommandResponse;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TAController extends ITAController.Stub {
    public static final boolean DEBUG = PaymentManagerService.DEBUG;
    public boolean SET_QSEE_SECURE_UI;
    public Context mContext;
    public PaymentTZNative mNative;
    public int mTAId;

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00b9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00af A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v5, types: [java.io.BufferedWriter] */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r4v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.io.FileWriter] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean makeSysCallInternal(int r9) {
        /*
            Method dump skipped, instructions count: 210
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.spay.TAController.makeSysCallInternal(int):boolean");
    }

    public final CertInfo checkCertInfo(List list) {
        PaymentManagerService.checkCallerPermissionFor("checkCertInfo");
        if (DEBUG) {
            Log.d("PaymentManagerService", "TAController::checkCertInfo: Lets fetch them if exist");
        }
        CertInfo certInfo = new CertInfo();
        for (int i = 0; i < list.size(); i++) {
            String str = (String) list.get(i);
            certInfo.mCerts.put(str, Utils.readFile(str));
        }
        return certInfo;
    }

    public final boolean clearDeviceCertificates(String str) {
        PaymentManagerService.checkCallerPermissionFor("clearDeviceCertificates");
        Log.d(
                "PaymentManagerService",
                "TAController::clearDeviceCertificates: Deleting the device certificates for "
                        + str);
        return Utils.deleteDirectory(new File(str));
    }

    public final boolean loadTA(ParcelFileDescriptor parcelFileDescriptor, long j, long j2) {
        synchronized (this) {
            try {
                PaymentManagerService.checkCallerPermissionFor("loadTA");
                boolean z = DEBUG;
                if (z) {
                    Log.d("PaymentManagerService", "TAController::loadTA");
                }
                if (parcelFileDescriptor == null) {
                    return false;
                }
                int fd = parcelFileDescriptor.getFd();
                if (z) {
                    Log.d("PaymentManagerService", "TA fd=" + fd + " offset=" + j + " size=" + j2);
                }
                try {
                    return this.mNative.loadTA(this.mContext, fd, j, j2);
                } finally {
                    try {
                        parcelFileDescriptor.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean makeSystemCall(int i) {
        boolean z = DEBUG;
        if (z) {
            Log.d(
                    "PaymentManagerService",
                    "entered makeSystemCall in TAController - System Server process");
        }
        PaymentManagerService.checkCallerPermissionFor("makeSystemCall");
        if (z) {
            try {
                Log.d(
                        "PaymentManagerService",
                        "makesystemcall - start time: " + System.currentTimeMillis() + " ms");
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        if (!makeSysCallInternal(i)) {
            Log.d("PaymentManagerService", "makeSystemCall: failed to make system call");
            return false;
        }
        if (!z) {
            return true;
        }
        Log.d(
                "PaymentManagerService",
                "makeSystemCall: Successful, end time : " + System.currentTimeMillis() + " ms");
        return true;
    }

    public final TACommandResponse processTACommand(TACommandRequest tACommandRequest) {
        TACommandResponse processTACommand;
        if (!isBinderAlive()) {
            Log.e("PaymentManagerService", "binder for cmd is died");
            return null;
        }
        synchronized (this) {
            try {
                PaymentManagerService.checkCallerPermissionFor("processTACommand");
                if (DEBUG) {
                    Log.d(
                            "PaymentManagerService",
                            "TAController::processTACommand: request = "
                                    + tACommandRequest
                                    + "; request.mCommandId = "
                                    + tACommandRequest.mCommandId
                                    + "; this.mTAId = "
                                    + this.mTAId);
                }
                if (tACommandRequest.mCommandId == 590224) {
                    this.SET_QSEE_SECURE_UI = true;
                }
                processTACommand = this.mNative.processTACommand(tACommandRequest);
                this.SET_QSEE_SECURE_UI = false;
            } catch (Throwable th) {
                throw th;
            }
        }
        return processTACommand;
    }

    public final void unloadTA() {
        synchronized (this) {
            try {
                PaymentManagerService.checkCallerPermissionFor("unloadTA");
                if (DEBUG) {
                    Log.d("PaymentManagerService", "TAController::unloadTA");
                }
                this.SET_QSEE_SECURE_UI = false;
                this.mNative.unloadTA();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
