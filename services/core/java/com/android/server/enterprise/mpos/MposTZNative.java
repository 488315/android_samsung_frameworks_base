package com.android.server.enterprise.mpos;

import android.content.Context;
import android.util.Log;

import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;

import com.samsung.android.knox.mpos.TACommandRequest;
import com.samsung.android.knox.mpos.TACommandResponse;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
class MposTZNative {
    public boolean mIsLoaded;
    public long mMOPTZNativePtr_;
    public final String mProcessName;
    public final int mRecvBufSize;
    public final String mRootName;
    public final int mSendBufSize;
    public final int mTAId;
    public final String mTATechnology;

    public MposTZNative(int i, int i2, String str, String str2, String str3, int i3) {
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                i, "MposTZNative constructor: taId = ", "MposTZNative");
        this.mTAId = i;
        this.mMOPTZNativePtr_ = 0L;
        this.mSendBufSize = i2;
        this.mRecvBufSize = i3;
        this.mTATechnology = str;
        this.mRootName = str2;
        this.mProcessName = str3;
        this.mIsLoaded = false;
    }

    private native boolean mposNativeProcessTACommand(
            TACommandRequest tACommandRequest, TACommandResponse tACommandResponse);

    public final boolean loadTA(Context context, int i, long j, long j2) {
        if (this.mMOPTZNativePtr_ != 0) {
            Log.e(
                    "MposTZNative",
                    "MposTZNative::loadTA called for TA that is already loaded. Call Ignored");
            return true;
        }
        if (j > 2147483647L || j2 > 2147483647L) {
            Log.e("MposTZNative", "MposTZNative::loadTA : cannot get ta offset or size");
            return false;
        }
        long mposNativeCreateTLCommunicationContext =
                mposNativeCreateTLCommunicationContext(
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
        this.mMOPTZNativePtr_ = mposNativeCreateTLCommunicationContext;
        if (mposNativeCreateTLCommunicationContext == 0) {
            Log.e("MposTZNative", "Error: mposNativeCreateTLCommunicationContext failed");
            return false;
        }
        synchronized (MposTZNative.class) {
            this.mIsLoaded = true;
        }
        Log.d("MposTZNative", "MposTZNative::loadTA: mMOPTZNativePtr_ = " + this.mMOPTZNativePtr_);
        return true;
    }

    public native long mposNativeCreateTLCommunicationContext(
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

    public native void mposNativeDestroyTLCommunicationContext();

    public final TACommandResponse processTACommand(TACommandRequest tACommandRequest) {
        Log.d(
                "MposTZNative",
                "MposTZNative::processTACommand: request = "
                        + tACommandRequest
                        + "; mMOPTZNativePtr_ = "
                        + this.mMOPTZNativePtr_);
        TACommandResponse tACommandResponse = new TACommandResponse();
        boolean mposNativeProcessTACommand =
                mposNativeProcessTACommand(tACommandRequest, tACommandResponse);
        Log.i(
                "MposTZNative",
                "MposTZNative::processTACommand: ret: "
                        + mposNativeProcessTACommand
                        + ", response: "
                        + tACommandResponse);
        if (mposNativeProcessTACommand) {
            return tACommandResponse;
        }
        return null;
    }

    public final void unloadTA() {
        synchronized (MposTZNative.class) {
            if (this.mMOPTZNativePtr_ != 0 && this.mIsLoaded) {
                this.mIsLoaded = false;
                mposNativeDestroyTLCommunicationContext();
                this.mMOPTZNativePtr_ = 0L;
                Log.d("MposTZNative", "MposTZNative::unloadTA called");
                return;
            }
            Log.e(
                    "MposTZNative",
                    "MposTZNative::unloadTA called for TA that is not loaded. Call Ignored: ta"
                        + " loaded: "
                            + this.mIsLoaded);
        }
    }
}
