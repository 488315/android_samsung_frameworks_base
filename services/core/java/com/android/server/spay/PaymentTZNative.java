package com.android.server.spay;

import android.content.Context;
import android.spay.TACommandRequest;
import android.spay.TACommandResponse;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
class PaymentTZNative {
    public static final boolean DEBUG = PaymentManagerService.DEBUG;
    public boolean mIsLoaded;
    public long mMOPTZNativePtr_;
    public String mProcessName;
    public int mRecvBufSize;
    public String mRootName;
    public int mSendBufSize;
    public int mTAId;
    public String mTATechnology;

    private native boolean nativeProcessTACommand(TACommandRequest tACommandRequest, TACommandResponse tACommandResponse);

    public final boolean loadTA(Context context, int i, long j, long j2) {
        if (this.mMOPTZNativePtr_ != 0) {
            Log.e("PaymentManagerService", "PaymentTZNative::loadTA called for TA that is already loaded. Call Ignored");
            return true;
        }
        if (j > 2147483647L || j2 > 2147483647L) {
            Log.e("PaymentManagerService", "SpayFw_loadTA: cannot get ta offset or size");
            return false;
        }
        long nativeCreateTLCommunicationContext = nativeCreateTLCommunicationContext(context, i, (int) j, (int) j2, this.mTAId, this.mSendBufSize, this.mRecvBufSize, this.mTATechnology, this.mRootName, this.mProcessName);
        this.mMOPTZNativePtr_ = nativeCreateTLCommunicationContext;
        if (nativeCreateTLCommunicationContext == 0) {
            Log.e("PaymentManagerService", "Error: nativeCreateTLCommunicationContext failed");
            return false;
        }
        synchronized (PaymentTZNative.class) {
            this.mIsLoaded = true;
        }
        if (DEBUG) {
            Log.d("PaymentManagerService", "PaymentTZNative::loadTA: mMOPTZNativePtr_ = " + this.mMOPTZNativePtr_);
        }
        return true;
    }

    public native long nativeCreateTLCommunicationContext(Context context, int i, int i2, int i3, int i4, int i5, int i6, String str, String str2, String str3);

    public native void nativeDestroyTLCommunicationContext();

    public final TACommandResponse processTACommand(TACommandRequest tACommandRequest) {
        if (DEBUG) {
            Log.d("PaymentManagerService", "PaymentTZNative::processTACommand: request = " + tACommandRequest + "; mMOPTZNativePtr_ = " + this.mMOPTZNativePtr_);
        }
        TACommandResponse tACommandResponse = new TACommandResponse();
        if (nativeProcessTACommand(tACommandRequest, tACommandResponse)) {
            return tACommandResponse;
        }
        Log.e("PaymentManagerService", "PaymentTZNative::processTACommand: Error: nativeProcessTACommand returned failure");
        return null;
    }

    public final void unloadTA() {
        synchronized (PaymentTZNative.class) {
            if (this.mMOPTZNativePtr_ != 0 && this.mIsLoaded) {
                this.mIsLoaded = false;
                nativeDestroyTLCommunicationContext();
                this.mMOPTZNativePtr_ = 0L;
                if (DEBUG) {
                    Log.d("PaymentManagerService", "PaymentTZNative::unloadTA called");
                    return;
                }
                return;
            }
            Log.e("PaymentManagerService", "PaymentTZNative::unloadTA called for TA that is not loaded. Call Ignored: ta loaded: " + this.mIsLoaded);
        }
    }
}
