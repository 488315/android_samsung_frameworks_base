package android.hardware.camera2.params;

import java.util.Objects;

/* loaded from: classes2.dex */
public class MultiResolutionStreamInfo {
    private String mPhysicalCameraId;
    private int mStreamHeight;
    private int mStreamWidth;

    public MultiResolutionStreamInfo(int i, int i2, String str) {
        if (i <= 0) {
            throw new IllegalArgumentException("Invalid stream width " + i);
        }
        if (i2 <= 0) {
            throw new IllegalArgumentException("Invalid stream height " + i2);
        }
        this.mStreamWidth = i;
        this.mStreamHeight = i2;
        this.mPhysicalCameraId = str;
    }

    public int getWidth() {
        return this.mStreamWidth;
    }

    public int getHeight() {
        return this.mStreamHeight;
    }

    public String getPhysicalCameraId() {
        return this.mPhysicalCameraId;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof MultiResolutionStreamInfo) {
            MultiResolutionStreamInfo multiResolutionStreamInfo = (MultiResolutionStreamInfo) obj;
            if (this.mStreamWidth == multiResolutionStreamInfo.mStreamWidth && this.mStreamHeight == multiResolutionStreamInfo.mStreamHeight && this.mPhysicalCameraId.equals(multiResolutionStreamInfo.mPhysicalCameraId)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mStreamWidth), Integer.valueOf(this.mStreamHeight), this.mPhysicalCameraId);
    }
}
