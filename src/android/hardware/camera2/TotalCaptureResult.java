package android.hardware.camera2;

import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.impl.CaptureResultExtras;
import android.hardware.camera2.impl.PhysicalCaptureResultInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public final class TotalCaptureResult extends CaptureResult {
    private final List<CaptureResult> mPartialResults;
    private final HashMap<String, TotalCaptureResult> mPhysicalCaptureResults;
    private final int mSessionId;

    public TotalCaptureResult(String str, CameraMetadataNative cameraMetadataNative, CaptureRequest captureRequest, CaptureResultExtras captureResultExtras, List<CaptureResult> list, int i, PhysicalCaptureResultInfo[] physicalCaptureResultInfoArr) {
        super(str, cameraMetadataNative, captureRequest, captureResultExtras);
        if (list == null) {
            this.mPartialResults = new ArrayList();
        } else {
            this.mPartialResults = list;
        }
        int i2 = i;
        this.mSessionId = i2;
        this.mPhysicalCaptureResults = new HashMap<>();
        int length = physicalCaptureResultInfoArr.length;
        int i3 = 0;
        while (i3 < length) {
            PhysicalCaptureResultInfo physicalCaptureResultInfo = physicalCaptureResultInfoArr[i3];
            this.mPhysicalCaptureResults.put(physicalCaptureResultInfo.getCameraId(), new TotalCaptureResult(physicalCaptureResultInfo.getCameraId(), physicalCaptureResultInfo.getCameraMetadata(), captureRequest, captureResultExtras, null, i2, new PhysicalCaptureResultInfo[0]));
            i3++;
            i2 = i;
        }
    }

    public TotalCaptureResult(String str, CameraMetadataNative cameraMetadataNative, CaptureRequest captureRequest, int i, long j, List<CaptureResult> list, int i2, PhysicalCaptureResultInfo[] physicalCaptureResultInfoArr) {
        super(str, cameraMetadataNative, captureRequest, i, j);
        if (list == null) {
            this.mPartialResults = new ArrayList();
        } else {
            this.mPartialResults = list;
        }
        int i3 = i2;
        this.mSessionId = i3;
        this.mPhysicalCaptureResults = new HashMap<>();
        int length = physicalCaptureResultInfoArr.length;
        int i4 = 0;
        while (i4 < length) {
            PhysicalCaptureResultInfo physicalCaptureResultInfo = physicalCaptureResultInfoArr[i4];
            this.mPhysicalCaptureResults.put(physicalCaptureResultInfo.getCameraId(), new TotalCaptureResult(physicalCaptureResultInfo.getCameraId(), physicalCaptureResultInfo.getCameraMetadata(), captureRequest, i, j, null, i3, new PhysicalCaptureResultInfo[0]));
            i4++;
            i3 = i2;
        }
    }

    public TotalCaptureResult(CameraMetadataNative cameraMetadataNative, int i) {
        super(cameraMetadataNative, i);
        this.mPartialResults = new ArrayList();
        this.mSessionId = -1;
        this.mPhysicalCaptureResults = new HashMap<>();
    }

    public List<CaptureResult> getPartialResults() {
        return Collections.unmodifiableList(this.mPartialResults);
    }

    public int getSessionId() {
        return this.mSessionId;
    }

    public Map<String, CaptureResult> getPhysicalCameraResults() {
        return Collections.unmodifiableMap(this.mPhysicalCaptureResults);
    }

    public Map<String, TotalCaptureResult> getPhysicalCameraTotalResults() {
        return Collections.unmodifiableMap(this.mPhysicalCaptureResults);
    }
}
