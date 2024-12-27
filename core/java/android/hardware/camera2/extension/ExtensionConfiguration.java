package android.hardware.camera2.extension;

import android.annotation.SystemApi;
import android.hardware.camera2.CaptureRequest;
import com.android.internal.camera.flags.Flags;
import java.util.ArrayList;
import java.util.List;

@SystemApi
/* loaded from: classes2.dex */
public class ExtensionConfiguration {
    private int mColorSpace = -1;
    private final List<ExtensionOutputConfiguration> mOutputs;
    private final CaptureRequest mSessionParameters;
    private final int mSessionTemplateId;
    private final int mSessionType;

    public ExtensionConfiguration(int sessionType, int sessionTemplateId, List<ExtensionOutputConfiguration> outputs, CaptureRequest sessionParams) {
        this.mSessionType = sessionType;
        this.mSessionTemplateId = sessionTemplateId;
        this.mOutputs = outputs;
        this.mSessionParameters = sessionParams;
    }

    public void setColorSpace(int colorSpace) {
        this.mColorSpace = colorSpace;
    }

    CameraSessionConfig getCameraSessionConfig() {
        if (this.mOutputs.isEmpty()) {
            return null;
        }
        CameraSessionConfig ret = new CameraSessionConfig();
        ret.sessionTemplateId = this.mSessionTemplateId;
        ret.sessionType = this.mSessionType;
        ret.outputConfigs = new ArrayList(this.mOutputs.size());
        if (Flags.extension10Bit()) {
            ret.colorSpace = this.mColorSpace;
        } else {
            ret.colorSpace = -1;
        }
        for (ExtensionOutputConfiguration outputConfig : this.mOutputs) {
            ret.outputConfigs.add(outputConfig.getOutputConfig());
        }
        if (this.mSessionParameters != null) {
            ret.sessionParameter = this.mSessionParameters.getNativeCopy();
        }
        return ret;
    }
}
