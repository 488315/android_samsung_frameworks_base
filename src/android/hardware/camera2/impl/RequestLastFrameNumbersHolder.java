package android.hardware.camera2.impl;

import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.utils.SubmitInfo;
import java.util.List;

/* loaded from: classes2.dex */
public class RequestLastFrameNumbersHolder {
    private boolean mInflightCompleted;
    private final long mLastRegularFrameNumber;
    private final long mLastReprocessFrameNumber;
    private final long mLastZslStillFrameNumber;
    private final int mRequestId;
    private boolean mSequenceCompleted;

    public RequestLastFrameNumbersHolder(List<CaptureRequest> list, SubmitInfo submitInfo) {
        long lastFrameNumber = submitInfo.getLastFrameNumber();
        if (submitInfo.getLastFrameNumber() < list.size() - 1) {
            throw new IllegalArgumentException("lastFrameNumber: " + submitInfo.getLastFrameNumber() + " should be at least " + (list.size() - 1) + " for the number of  requests in the list: " + list.size());
        }
        long j = -1;
        long j2 = -1;
        long j3 = -1;
        for (int size = list.size() - 1; size >= 0; size--) {
            int requestType = list.get(size).getRequestType();
            if (requestType == 1 && j == -1) {
                j = lastFrameNumber;
            } else if (requestType == 2 && j2 == -1) {
                j2 = lastFrameNumber;
            } else if (requestType == 0 && j3 == -1) {
                j3 = lastFrameNumber;
            }
            if (j != -1 && j2 != -1 && j3 != -1) {
                break;
            }
            lastFrameNumber--;
        }
        this.mLastRegularFrameNumber = j3;
        this.mLastReprocessFrameNumber = j;
        this.mLastZslStillFrameNumber = j2;
        this.mRequestId = submitInfo.getRequestId();
        this.mSequenceCompleted = false;
        this.mInflightCompleted = false;
    }

    RequestLastFrameNumbersHolder(int i, long j, int[] iArr) {
        if (iArr == null) {
            throw new IllegalArgumentException("repeatingRequest list must not be null");
        }
        if (j < iArr.length - 1) {
            StringBuilder sb = new StringBuilder("lastFrameNumber: ");
            sb.append(j);
            sb.append(" should be at least ");
            sb.append(iArr.length - 1);
            sb.append(" for the number of requests in the list: ");
            sb.append(iArr.length);
            throw new IllegalArgumentException(sb.toString());
        }
        long j2 = -1;
        long j3 = -1;
        for (int length = iArr.length - 1; length >= 0; length--) {
            int i2 = iArr[length];
            if (i2 == 2 && j2 == -1) {
                j2 = j;
            } else if (i2 == 0 && j3 == -1) {
                j3 = j;
            }
            if (j2 != -1 && j3 != -1) {
                break;
            }
            j--;
        }
        this.mLastRegularFrameNumber = j3;
        this.mLastZslStillFrameNumber = j2;
        this.mLastReprocessFrameNumber = -1L;
        this.mRequestId = i;
        this.mSequenceCompleted = false;
        this.mInflightCompleted = false;
    }

    public long getLastRegularFrameNumber() {
        return this.mLastRegularFrameNumber;
    }

    public long getLastReprocessFrameNumber() {
        return this.mLastReprocessFrameNumber;
    }

    public long getLastZslStillFrameNumber() {
        return this.mLastZslStillFrameNumber;
    }

    public long getLastFrameNumber() {
        return Math.max(this.mLastZslStillFrameNumber, Math.max(this.mLastRegularFrameNumber, this.mLastReprocessFrameNumber));
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public boolean isSequenceCompleted() {
        return this.mSequenceCompleted;
    }

    public void markSequenceCompleted() {
        this.mSequenceCompleted = true;
    }

    public boolean isInflightCompleted() {
        return this.mInflightCompleted;
    }

    public void markInflightCompleted() {
        this.mInflightCompleted = true;
    }
}
