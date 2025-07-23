package android.hardware.camera2.extension;

import android.annotation.SystemApi;
import java.util.ArrayList;
import java.util.List;

@SystemApi
/* loaded from: classes2.dex */
public class ExtensionOutputConfiguration {
    private final int mOutputConfigId;
    private final String mPhysicalCameraId;
    private final int mSurfaceGroupId;
    private final List<CameraOutputSurface> mSurfaces;

    public ExtensionOutputConfiguration(List<CameraOutputSurface> list, int i, String str, int i2) {
        this.mSurfaces = list;
        this.mPhysicalCameraId = str;
        this.mOutputConfigId = i;
        this.mSurfaceGroupId = i2;
    }

    private void initializeOutputConfig(CameraOutputConfig cameraOutputConfig, CameraOutputSurface cameraOutputSurface) {
        cameraOutputConfig.surface = cameraOutputSurface.getSurface();
        if (cameraOutputSurface.getSize() != null) {
            cameraOutputConfig.size = new Size();
            cameraOutputConfig.size.width = cameraOutputSurface.getSize().getWidth();
            cameraOutputConfig.size.height = cameraOutputSurface.getSize().getHeight();
        }
        cameraOutputConfig.imageFormat = cameraOutputSurface.getImageFormat();
        cameraOutputConfig.type = 0;
        cameraOutputConfig.physicalCameraId = this.mPhysicalCameraId;
        cameraOutputConfig.outputId = new OutputConfigId();
        cameraOutputConfig.outputId.id = this.mOutputConfigId;
        cameraOutputConfig.surfaceGroupId = this.mSurfaceGroupId;
        cameraOutputConfig.dynamicRangeProfile = cameraOutputSurface.getDynamicRangeProfile();
    }

    CameraOutputConfig getOutputConfig() {
        if (this.mSurfaces.isEmpty()) {
            return null;
        }
        CameraOutputConfig cameraOutputConfig = new CameraOutputConfig();
        initializeOutputConfig(cameraOutputConfig, this.mSurfaces.get(0));
        if (this.mSurfaces.size() > 1) {
            cameraOutputConfig.sharedSurfaceConfigs = new ArrayList(this.mSurfaces.size() - 1);
            for (int i = 1; i < this.mSurfaces.size(); i++) {
                CameraOutputConfig cameraOutputConfig2 = new CameraOutputConfig();
                initializeOutputConfig(cameraOutputConfig2, this.mSurfaces.get(i));
                cameraOutputConfig.sharedSurfaceConfigs.add(cameraOutputConfig2);
            }
        }
        return cameraOutputConfig;
    }
}
