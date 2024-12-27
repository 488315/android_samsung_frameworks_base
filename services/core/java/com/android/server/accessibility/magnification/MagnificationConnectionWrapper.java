package com.android.server.accessibility.magnification;

import android.os.RemoteException;
import android.view.accessibility.IMagnificationConnection;
import android.view.accessibility.IMagnificationConnectionCallback;
import android.view.accessibility.IRemoteMagnificationAnimationCallback;
import android.view.accessibility.MagnificationAnimationCallback;

import com.android.server.accessibility.AccessibilityTraceManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MagnificationConnectionWrapper {
    public final IMagnificationConnection mConnection;
    public final AccessibilityTraceManager mTrace;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RemoteAnimationCallback extends IRemoteMagnificationAnimationCallback.Stub {
        public final MagnificationAnimationCallback mCallback;
        public final AccessibilityTraceManager mTrace;

        public RemoteAnimationCallback(
                MagnificationAnimationCallback magnificationAnimationCallback,
                AccessibilityTraceManager accessibilityTraceManager) {
            this.mCallback = magnificationAnimationCallback;
            this.mTrace = accessibilityTraceManager;
            if (accessibilityTraceManager.isA11yTracingEnabledForTypes(64L)) {
                accessibilityTraceManager.logTrace(
                        "RemoteAnimationCallback.constructor",
                        64L,
                        "callback=" + magnificationAnimationCallback);
            }
        }

        public final void onResult(boolean z) {
            this.mCallback.onResult(z);
            if (this.mTrace.isA11yTracingEnabledForTypes(64L)) {
                this.mTrace.logTrace("RemoteAnimationCallback.onResult", 64L, "success=" + z);
            }
        }
    }

    public MagnificationConnectionWrapper(
            IMagnificationConnection iMagnificationConnection,
            AccessibilityTraceManager accessibilityTraceManager) {
        this.mConnection = iMagnificationConnection;
        this.mTrace = accessibilityTraceManager;
    }

    public final void setConnectionCallback(
            IMagnificationConnectionCallback iMagnificationConnectionCallback) {
        AccessibilityTraceManager accessibilityTraceManager = this.mTrace;
        if (accessibilityTraceManager.isA11yTracingEnabledForTypes(384L)) {
            accessibilityTraceManager.logTrace(
                    "MagnificationConnectionWrapper.setConnectionCallback",
                    384L,
                    "callback=" + iMagnificationConnectionCallback);
        }
        try {
            this.mConnection.setConnectionCallback(iMagnificationConnectionCallback);
        } catch (RemoteException unused) {
        }
    }
}
