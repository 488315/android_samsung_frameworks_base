package com.android.server.blockchain;

import android.blockchain.BlockchainTZServiceConfig;
import android.blockchain.ITAController;
import android.blockchain.TACommandRequest;
import android.blockchain.TACommandResponse;
import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.IOException;

/* loaded from: classes.dex */
public class TAController extends ITAController.Stub {
    public static final boolean DEBUG = BlockchainTZService.DEBUG;
    public boolean SET_QSEE_SECURE_UI = false;
    public Context mContext;
    public BlockchainTZNative mNative;
    public int mTAId;

    public TAController(Context context, int i, BlockchainTZServiceConfig.TAConfig tAConfig) {
        if (DEBUG) {
            Log.d("BlockchainTZService", "TAController constructor: taId = " + i + "; maxSendCmdSize = " + tAConfig.maxSendCmdSize + "; maxRecvRespSize = " + tAConfig.maxRecvRespSize);
        }
        this.mContext = context;
        this.mTAId = i;
        this.mNative = new BlockchainTZNative(i, tAConfig.taTechnology, tAConfig.rootName, tAConfig.processName, tAConfig.maxSendCmdSize, tAConfig.maxRecvRespSize);
    }

    public TACommandResponse processTACommand(TACommandRequest tACommandRequest) {
        TACommandResponse processTACommand;
        if (isBinderAlive()) {
            synchronized (this) {
                BlockchainTZService.checkCallerPermissionFor("processTACommand");
                if (DEBUG) {
                    Log.d("BlockchainTZService", "TAController::processTACommand: request = " + tACommandRequest + "; request.mCommandId = " + tACommandRequest.mCommandId + "; this.mTAId = " + this.mTAId);
                }
                if (tACommandRequest.mCommandId == 590224) {
                    this.SET_QSEE_SECURE_UI = true;
                }
                processTACommand = this.mNative.processTACommand(tACommandRequest);
                this.SET_QSEE_SECURE_UI = false;
            }
            return processTACommand;
        }
        Log.e("BlockchainTZService", "binder for cmd is died");
        return null;
    }

    public boolean loadTA(ParcelFileDescriptor parcelFileDescriptor, long j, long j2) {
        synchronized (this) {
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
        }
    }

    public void unloadTA() {
        synchronized (this) {
            BlockchainTZService.checkCallerPermissionFor("unloadTA");
            if (DEBUG) {
                Log.d("BlockchainTZService", "TAController::unloadTA");
            }
            this.SET_QSEE_SECURE_UI = false;
            this.mNative.unloadTA();
        }
    }
}
