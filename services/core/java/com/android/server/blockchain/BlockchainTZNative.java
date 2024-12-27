package com.android.server.blockchain;

import android.blockchain.TACommandRequest;
import android.blockchain.TACommandResponse;
import android.content.Context;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
class BlockchainTZNative {
    public static final boolean DEBUG = BlockchainTZService.DEBUG;
    public boolean mIsLoaded;
    public long mMOPTZNativePtr_;
    public String mProcessName;
    public int mRecvBufSize;
    public String mRootName;
    public int mSendBufSize;
    public int mTAId;
    public String mTATechnology;

    private native boolean nativeBlockchainProcessTACommand(
            TACommandRequest tACommandRequest, TACommandResponse tACommandResponse);

    public final boolean loadTA(Context context, int i, long j, long j2) {
        if (this.mMOPTZNativePtr_ != 0) {
            Log.e(
                    "BlockchainTZService",
                    "BlockchainTZNative::loadTA called for TA that is already loaded. Call"
                        + " Ignored");
            return true;
        }
        if (j > 2147483647L || j2 > 2147483647L) {
            Log.e("BlockchainTZService", "BlockchainFw_loadTA: cannot get ta offset or size");
            return false;
        }
        long nativeBlockchainCreateTLCommunicationContext =
                nativeBlockchainCreateTLCommunicationContext(
                        context,
                        i,
                        (int) j,
                        (int) j2,
                        this.mTAId,
                        this.mSendBufSize,
                        this.mRecvBufSize,
                        this.mTATechnology,
                        this.mRootName,
                        this.mProcessName);
        this.mMOPTZNativePtr_ = nativeBlockchainCreateTLCommunicationContext;
        if (nativeBlockchainCreateTLCommunicationContext == 0) {
            Log.e(
                    "BlockchainTZService",
                    "Error: nativeBlockchainCreateTLCommunicationContext failed");
            return false;
        }
        synchronized (BlockchainTZNative.class) {
            this.mIsLoaded = true;
        }
        if (DEBUG) {
            Log.d(
                    "BlockchainTZService",
                    "BlockchainTZNative::loadTA: mMOPTZNativePtr_ = " + this.mMOPTZNativePtr_);
        }
        return true;
    }

    public native long nativeBlockchainCreateTLCommunicationContext(
            Context context,
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            int i6,
            String str,
            String str2,
            String str3);

    public native void nativeBlockchainDestroyTLCommunicationContext();

    public final TACommandResponse processTACommand(TACommandRequest tACommandRequest) {
        if (DEBUG) {
            Log.d(
                    "BlockchainTZService",
                    "BlockchainTZNative::processTACommand: request = "
                            + tACommandRequest
                            + "; mMOPTZNativePtr_ = "
                            + this.mMOPTZNativePtr_);
        }
        TACommandResponse tACommandResponse = new TACommandResponse();
        if (nativeBlockchainProcessTACommand(tACommandRequest, tACommandResponse)) {
            return tACommandResponse;
        }
        Log.e(
                "BlockchainTZService",
                "BlockchainTZNative::processTACommand: Error: nativeBlockchainProcessTACommand"
                    + " returned failure");
        return null;
    }

    public final void unloadTA() {
        synchronized (BlockchainTZNative.class) {
            if (this.mMOPTZNativePtr_ != 0 && this.mIsLoaded) {
                this.mIsLoaded = false;
                nativeBlockchainDestroyTLCommunicationContext();
                this.mMOPTZNativePtr_ = 0L;
                if (DEBUG) {
                    Log.d("BlockchainTZService", "BlockchainTZNative::unloadTA called");
                    return;
                }
                return;
            }
            Log.e(
                    "BlockchainTZService",
                    "BlockchainTZNative::unloadTA called for TA that is not loaded. Call Ignored:"
                        + " ta loaded: "
                            + this.mIsLoaded);
        }
    }
}
