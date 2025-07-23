package android.hardware.camera2.extension;

import android.annotation.SystemApi;
import android.hardware.camera2.CaptureRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SystemApi
/* loaded from: classes2.dex */
public class ExtensionConfiguration {
    private int mColorSpace = -1;
    private final List<ExtensionOutputConfiguration> mOutputs;
    private final CaptureRequest mSessionParameters;
    private final int mSessionTemplateId;
    private final int mSessionType;

    public ExtensionConfiguration(int i, int i2, List<ExtensionOutputConfiguration> list, CaptureRequest captureRequest) {
        this.mSessionType = i;
        this.mSessionTemplateId = i2;
        this.mOutputs = list;
        this.mSessionParameters = captureRequest;
    }

    public void setColorSpace(int i) {
        this.mColorSpace = i;
    }

    CameraSessionConfig getCameraSessionConfig() {
        if (this.mOutputs.isEmpty()) {
            return null;
        }
        CameraSessionConfig cameraSessionConfig = new CameraSessionConfig();
        cameraSessionConfig.sessionTemplateId = this.mSessionTemplateId;
        cameraSessionConfig.sessionType = this.mSessionType;
        cameraSessionConfig.outputConfigs = new ArrayList(this.mOutputs.size());
        cameraSessionConfig.colorSpace = this.mColorSpace;
        Iterator<ExtensionOutputConfiguration> it = this.mOutputs.iterator();
        while (it.hasNext()) {
            cameraSessionConfig.outputConfigs.add(it.next().getOutputConfig());
        }
        CaptureRequest captureRequest = this.mSessionParameters;
        if (captureRequest != null) {
            cameraSessionConfig.sessionParameter = captureRequest.getNativeCopy();
        }
        return cameraSessionConfig;
    }
}
