package com.android.server.knox.dar.ddar.ta;

import com.android.server.knox.dar.ddar.DDLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
class TZNative {
    public long mDDARTZNativePtr_;
    public boolean mIsLoaded;
    public String mProcessName;
    public int mRecvBufSize;
    public String mRootName;
    public int mSendBufSize;
    public int mTAId;
    public String mTATechnology;

    private native boolean nativeDDARProcessTACommand(
            TACommandRequest tACommandRequest, TACommandResponse tACommandResponse);

    public final boolean loadTA(int i, long j, long j2) {
        if (this.mDDARTZNativePtr_ != 0) {
            DDLog.e(
                    "DualDAR:TZNative",
                    "TZNative::loadTA called for TA that is already loaded. Call Ignored",
                    new Object[0]);
            return true;
        }
        if (j > 2147483647L || j2 > 2147483647L) {
            DDLog.e("DualDAR:TZNative", "loadTA: cannot get ta offset or size", new Object[0]);
            return false;
        }
        long nativeDDARCreateTLCommunicationContext =
                nativeDDARCreateTLCommunicationContext(
                        i,
                        (int) j,
                        (int) j2,
                        this.mTAId,
                        this.mSendBufSize,
                        this.mRecvBufSize,
                        this.mTATechnology,
                        this.mRootName,
                        this.mProcessName);
        this.mDDARTZNativePtr_ = nativeDDARCreateTLCommunicationContext;
        if (nativeDDARCreateTLCommunicationContext == 0) {
            DDLog.e(
                    "DualDAR:TZNative",
                    "Error: nativeDDARCreateTLCommunicationContext failed",
                    new Object[0]);
            return false;
        }
        synchronized (TZNative.class) {
            this.mIsLoaded = true;
        }
        DDLog.d(
                "DualDAR:TZNative",
                "TZNative::loadTA: mDDARTZNativePtr_ = " + this.mDDARTZNativePtr_,
                new Object[0]);
        return true;
    }

    public native long nativeDDARCreateTLCommunicationContext(
            int i, int i2, int i3, int i4, int i5, int i6, String str, String str2, String str3);

    public native void nativeDDARDestroyTLCommunicationContext();

    public final TACommandResponse processTACommand(TACommandRequest tACommandRequest) {
        DDLog.d(
                "DualDAR:TZNative",
                "TZNative::processTACommand: request = "
                        + tACommandRequest
                        + "; mDDARTZNativePtr_ = "
                        + this.mDDARTZNativePtr_,
                new Object[0]);
        TACommandResponse tACommandResponse = new TACommandResponse();
        if (nativeDDARProcessTACommand(tACommandRequest, tACommandResponse)) {
            return tACommandResponse;
        }
        DDLog.e(
                "DualDAR:TZNative",
                "TZNative::processTACommand: Error: nativeDDARProcessTACommand returned failure",
                new Object[0]);
        return null;
    }
}
