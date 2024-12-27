package android.hardware.camera2.extension;

import android.annotation.SystemApi;
import com.android.internal.camera.flags.Flags;
import java.util.ArrayList;
import java.util.List;

@SystemApi
/* loaded from: classes2.dex */
public class ExtensionOutputConfiguration {
    private final int mOutputConfigId;
    private final String mPhysicalCameraId;
    private final int mSurfaceGroupId;
    private final List<CameraOutputSurface> mSurfaces;

    public ExtensionOutputConfiguration(List<CameraOutputSurface> outputs, int outputConfigId, String physicalCameraId, int surfaceGroupId) {
        this.mSurfaces = outputs;
        this.mPhysicalCameraId = physicalCameraId;
        this.mOutputConfigId = outputConfigId;
        this.mSurfaceGroupId = surfaceGroupId;
    }

    private void initializeOutputConfig(CameraOutputConfig config, CameraOutputSurface surface) {
        config.surface = surface.getSurface();
        if (surface.getSize() != null) {
            config.size = new Size();
            config.size.width = surface.getSize().getWidth();
            config.size.height = surface.getSize().getHeight();
        }
        config.imageFormat = surface.getImageFormat();
        config.type = 0;
        config.physicalCameraId = this.mPhysicalCameraId;
        config.outputId = new OutputConfigId();
        config.outputId.id = this.mOutputConfigId;
        config.surfaceGroupId = this.mSurfaceGroupId;
        if (Flags.extension10Bit()) {
            config.dynamicRangeProfile = surface.getDynamicRangeProfile();
        } else {
            config.dynamicRangeProfile = 1L;
        }
    }

    CameraOutputConfig getOutputConfig() {
        if (this.mSurfaces.isEmpty()) {
            return null;
        }
        CameraOutputConfig ret = new CameraOutputConfig();
        initializeOutputConfig(ret, this.mSurfaces.get(0));
        if (this.mSurfaces.size() > 1) {
            ret.sharedSurfaceConfigs = new ArrayList(this.mSurfaces.size() - 1);
            for (int i = 1; i < this.mSurfaces.size(); i++) {
                CameraOutputConfig sharedConfig = new CameraOutputConfig();
                initializeOutputConfig(sharedConfig, this.mSurfaces.get(i));
                ret.sharedSurfaceConfigs.add(sharedConfig);
            }
        }
        return ret;
    }
}
