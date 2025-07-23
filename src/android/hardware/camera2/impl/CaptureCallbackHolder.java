package android.hardware.camera2.impl;

import android.hardware.camera2.CaptureRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class CaptureCallbackHolder {
    private final CaptureCallback mCallback;
    private final Executor mExecutor;
    private final boolean mHasBatchedOutputs;
    private final boolean mRepeating;
    private final List<CaptureRequest> mRequestList;
    private final int mSessionId;

    CaptureCallbackHolder(CaptureCallback captureCallback, List<CaptureRequest> list, Executor executor, boolean z, int i) {
        if (captureCallback == null || executor == null) {
            throw new UnsupportedOperationException("Must have a valid handler and a valid callback");
        }
        this.mRepeating = z;
        this.mExecutor = executor;
        this.mRequestList = new ArrayList(list);
        this.mCallback = captureCallback;
        this.mSessionId = i;
        boolean z2 = false;
        int i2 = 0;
        while (true) {
            if (i2 >= list.size()) {
                z2 = true;
                break;
            }
            CaptureRequest captureRequest = list.get(i2);
            if (!captureRequest.isPartOfCRequestList() || (i2 == 0 && captureRequest.getTargets().size() != 2)) {
                break;
            } else {
                i2++;
            }
        }
        this.mHasBatchedOutputs = z2;
    }

    public boolean isRepeating() {
        return this.mRepeating;
    }

    public CaptureCallback getCallback() {
        return this.mCallback;
    }

    public CaptureRequest getRequest(int i) {
        if (i >= this.mRequestList.size()) {
            throw new IllegalArgumentException(String.format("Requested subsequenceId %d is larger than request list size %d.", Integer.valueOf(i), Integer.valueOf(this.mRequestList.size())));
        }
        if (i < 0) {
            throw new IllegalArgumentException(String.format("Requested subsequenceId %d is negative", Integer.valueOf(i)));
        }
        return this.mRequestList.get(i);
    }

    public CaptureRequest getRequest() {
        return getRequest(0);
    }

    public Executor getExecutor() {
        return this.mExecutor;
    }

    public int getSessionId() {
        return this.mSessionId;
    }

    public int getRequestCount() {
        return this.mRequestList.size();
    }

    public boolean hasBatchedOutputs() {
        return this.mHasBatchedOutputs;
    }
}
