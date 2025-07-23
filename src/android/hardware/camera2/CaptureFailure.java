package android.hardware.camera2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public class CaptureFailure {
    public static final int REASON_ERROR = 0;
    public static final int REASON_FLUSHED = 1;
    private final String mErrorPhysicalCameraId;
    private final long mFrameNumber;
    private final int mReason;
    private final CaptureRequest mRequest;
    private final int mSequenceId;
    private final boolean mWasImageCaptured;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FailureReason {
    }

    public CaptureFailure(CaptureRequest captureRequest, int i, boolean z, int i2, long j, String str) {
        this.mRequest = captureRequest;
        this.mReason = i;
        this.mWasImageCaptured = z;
        this.mSequenceId = i2;
        this.mFrameNumber = j;
        this.mErrorPhysicalCameraId = str;
    }

    public CaptureRequest getRequest() {
        return this.mRequest;
    }

    public long getFrameNumber() {
        return this.mFrameNumber;
    }

    public int getReason() {
        return this.mReason;
    }

    public boolean wasImageCaptured() {
        return this.mWasImageCaptured;
    }

    public int getSequenceId() {
        return this.mSequenceId;
    }

    public String getPhysicalCameraId() {
        return this.mErrorPhysicalCameraId;
    }
}
