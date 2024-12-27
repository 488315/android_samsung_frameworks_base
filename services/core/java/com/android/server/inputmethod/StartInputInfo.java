package com.android.server.inputmethod;

import android.os.IBinder;
import android.os.SystemClock;
import android.view.inputmethod.EditorInfo;

import java.util.concurrent.atomic.AtomicInteger;

public final class StartInputInfo {
    public static final AtomicInteger sSequenceNumber = new AtomicInteger(0);
    public final int mClientBindSequenceNumber;
    public final EditorInfo mEditorInfo;
    public final int mImeDisplayId;
    public final String mImeId;
    public final IBinder mImeToken;
    public final int mImeUserId;
    public final boolean mRestarting;
    public final int mStartInputReason;
    public final int mTargetDisplayId;
    public final int mTargetUserId;
    public final IBinder mTargetWindow;
    public final int mTargetWindowSoftInputMode;
    public final int mSequenceNumber = sSequenceNumber.getAndIncrement();
    public final long mTimestamp = SystemClock.uptimeMillis();
    public final long mWallTime = System.currentTimeMillis();

    public StartInputInfo(
            int i,
            IBinder iBinder,
            int i2,
            String str,
            int i3,
            boolean z,
            int i4,
            int i5,
            IBinder iBinder2,
            EditorInfo editorInfo,
            int i6,
            int i7) {
        this.mImeUserId = i;
        this.mImeToken = iBinder;
        this.mImeDisplayId = i2;
        this.mImeId = str;
        this.mStartInputReason = i3;
        this.mRestarting = z;
        this.mTargetUserId = i4;
        this.mTargetDisplayId = i5;
        this.mTargetWindow = iBinder2;
        this.mEditorInfo = editorInfo;
        this.mTargetWindowSoftInputMode = i6;
        this.mClientBindSequenceNumber = i7;
    }
}
