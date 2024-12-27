package android.hardware.camera2.extension;

import android.annotation.SystemApi;
import android.hardware.camera2.utils.SurfaceUtils;
import android.view.Surface;

@SystemApi
public final class CameraOutputSurface {
    private final OutputSurface mOutputSurface;

    CameraOutputSurface(OutputSurface surface) {
        this.mOutputSurface = surface;
    }

    public CameraOutputSurface(Surface surface, android.util.Size size) {
        this.mOutputSurface = new OutputSurface();
        this.mOutputSurface.surface = surface;
        this.mOutputSurface.imageFormat = SurfaceUtils.getSurfaceFormat(surface);
        this.mOutputSurface.size = new Size();
        this.mOutputSurface.size.width = size.getWidth();
        this.mOutputSurface.size.height = size.getHeight();
        this.mOutputSurface.dynamicRangeProfile = 1L;
        this.mOutputSurface.colorSpace = -1;
    }

    public Surface getSurface() {
        return this.mOutputSurface.surface;
    }

    public android.util.Size getSize() {
        if (this.mOutputSurface.size != null) {
            return new android.util.Size(
                    this.mOutputSurface.size.width, this.mOutputSurface.size.height);
        }
        return null;
    }

    public int getImageFormat() {
        return this.mOutputSurface.imageFormat;
    }

    public long getDynamicRangeProfile() {
        return this.mOutputSurface.dynamicRangeProfile;
    }

    public int getColorSpace() {
        return this.mOutputSurface.colorSpace;
    }

    public void setDynamicRangeProfile(long dynamicRangeProfile) {
        this.mOutputSurface.dynamicRangeProfile = dynamicRangeProfile;
    }
}
