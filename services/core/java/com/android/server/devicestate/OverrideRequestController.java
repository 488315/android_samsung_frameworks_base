package com.android.server.devicestate;

import android.hardware.display.DisplayManagerInternal;
import android.os.IBinder;
import android.util.Slog;

import com.android.server.LocalServices;

import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class OverrideRequestController {
    public OverrideRequest mBaseStateRequest;
    public final DeviceStateManagerService$$ExternalSyntheticLambda1 mListener;
    public OverrideRequest mRequest;
    public boolean mStickyRequest;
    public boolean mStickyRequestsAllowed;

    public OverrideRequestController(
            DeviceStateManagerService$$ExternalSyntheticLambda1
                    deviceStateManagerService$$ExternalSyntheticLambda1) {
        this.mListener = deviceStateManagerService$$ExternalSyntheticLambda1;
    }

    public final void cancelCurrentBaseStateRequestLocked(int i) {
        OverrideRequest overrideRequest = this.mBaseStateRequest;
        if (overrideRequest == null) {
            Slog.w("OverrideRequestController", "Attempted to cancel a null OverrideRequest");
        } else {
            cancelRequestLocked(overrideRequest, i);
            this.mBaseStateRequest = null;
        }
    }

    public final void cancelCurrentRequestLocked(int i) {
        OverrideRequest overrideRequest = this.mRequest;
        if (overrideRequest == null) {
            Slog.w("OverrideRequestController", "Attempted to cancel a null OverrideRequest");
            return;
        }
        this.mStickyRequest = false;
        cancelRequestLocked(overrideRequest, i);
        this.mRequest = null;
    }

    public final void cancelRequest(OverrideRequest overrideRequest) {
        if (hasRequest(overrideRequest.mRequestType, overrideRequest.mToken)) {
            cancelCurrentRequestLocked(0);
        }
    }

    public final void cancelRequestLocked(OverrideRequest overrideRequest, int i) {
        this.mListener.onStatusChanged(overrideRequest, 2, i);
        if (CoreRune.FW_FLEXIBLE_DUAL_MODE) {
            if (overrideRequest.mRequestedState.getIdentifier() == 4
                    || overrideRequest.mRequestedState.getIdentifier() == 5) {
                ((DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class))
                        .setForceListenProcess(overrideRequest.mPid);
            }
        }
    }

    public final void dumpInternal(PrintWriter printWriter) {
        OverrideRequest overrideRequest = this.mRequest;
        boolean z = overrideRequest != null;
        printWriter.println();
        printWriter.println("Override Request active: " + z);
        if (z) {
            printWriter.println(
                    "Request: mPid="
                            + overrideRequest.mPid
                            + ", mRequestedState="
                            + overrideRequest.mRequestedState.getIdentifier()
                            + ", mFlags="
                            + overrideRequest.mFlags
                            + ", mStatus=ACTIVE");
        }
    }

    public final boolean hasRequest(int i, IBinder iBinder) {
        if (i == 1) {
            OverrideRequest overrideRequest = this.mBaseStateRequest;
            return overrideRequest != null && iBinder == overrideRequest.mToken;
        }
        OverrideRequest overrideRequest2 = this.mRequest;
        return overrideRequest2 != null && iBinder == overrideRequest2.mToken;
    }
}
