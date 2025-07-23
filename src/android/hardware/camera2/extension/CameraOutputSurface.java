package android.hardware.camera2.extension;

import android.annotation.SystemApi;
import android.hardware.camera2.utils.SurfaceUtils;
import android.view.Surface;

@SystemApi
/* loaded from: classes2.dex */
public final class CameraOutputSurface {
    private final OutputSurface mOutputSurface;

    CameraOutputSurface(OutputSurface outputSurface) {
        this.mOutputSurface = outputSurface;
    }

    public CameraOutputSurface(Surface surface, android.util.Size size) {
        OutputSurface outputSurface = new OutputSurface();
        this.mOutputSurface = outputSurface;
        outputSurface.surface = surface;
        outputSurface.imageFormat = SurfaceUtils.getSurfaceFormat(surface);
        outputSurface.size = new Size();
        outputSurface.size.width = size.getWidth();
        outputSurface.size.height = size.getHeight();
        outputSurface.dynamicRangeProfile = 1L;
        outputSurface.colorSpace = -1;
    }

    public Surface getSurface() {
        return this.mOutputSurface.surface;
    }

    public android.util.Size getSize() {
        if (this.mOutputSurface.size != null) {
            return new android.util.Size(this.mOutputSurface.size.width, this.mOutputSurface.size.height);
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

    public void setDynamicRangeProfile(long j) {
        this.mOutputSurface.dynamicRangeProfile = j;
    }
}
