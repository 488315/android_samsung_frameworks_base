package com.android.server.blockchain;

import android.blockchain.ITAController;
import android.blockchain.TACommandRequest;
import android.blockchain.TACommandResponse;
import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.IOException;

public final class TAController extends ITAController.Stub {
    public static final boolean DEBUG = BlockchainTZService.DEBUG;
    public boolean SET_QSEE_SECURE_UI;
    public Context mContext;
    public BlockchainTZNative mNative;
    public int mTAId;

    public final boolean loadTA(ParcelFileDescriptor parcelFileDescriptor, long j, long j2) {
        synchronized (this) {
            try {
                BlockchainTZService.checkCallerPermissionFor("loadTA");
                boolean z = DEBUG;
                if (z) {
                    Log.d("BlockchainTZService", "TAController::loadTA");
                }
                if (parcelFileDescriptor == null) {
                    return false;
                }
                int fd = parcelFileDescriptor.getFd();
                if (z) {
                    Log.d("BlockchainTZService", "TA fd=" + fd + " offset=" + j + " size=" + j2);
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

    public final TACommandResponse processTACommand(TACommandRequest tACommandRequest) {
        TACommandResponse processTACommand;
        if (!isBinderAlive()) {
            Log.e("BlockchainTZService", "binder for cmd is died");
            return null;
        }
        synchronized (this) {
            try {
                BlockchainTZService.checkCallerPermissionFor("processTACommand");
                if (DEBUG) {
                    Log.d(
                            "BlockchainTZService",
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
                BlockchainTZService.checkCallerPermissionFor("unloadTA");
                if (DEBUG) {
                    Log.d("BlockchainTZService", "TAController::unloadTA");
                }
                this.SET_QSEE_SECURE_UI = false;
                this.mNative.unloadTA();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
