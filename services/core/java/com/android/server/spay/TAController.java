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

public final class TAController extends ITAController.Stub {
    public static final boolean DEBUG = PaymentManagerService.DEBUG;
    public boolean SET_QSEE_SECURE_UI;
    public Context mContext;
    public PaymentTZNative mNative;
    public int mTAId;

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
