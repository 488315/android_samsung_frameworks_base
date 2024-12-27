package android.hardware.camera2.params;

import android.graphics.ColorSpace;
import android.hardware.camera2.CameraExtensionSession;

import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class ExtensionSessionConfiguration {
    private static final String TAG = "ExtensionSessionConfiguration";
    private CameraExtensionSession.StateCallback mCallback;
    private int mColorSpace;
    private Executor mExecutor;
    private int mExtensionType;
    private List<OutputConfiguration> mOutputs;
    private OutputConfiguration mPostviewOutput = null;

    public ExtensionSessionConfiguration(
            int extension,
            List<OutputConfiguration> outputs,
            Executor executor,
            CameraExtensionSession.StateCallback listener) {
        this.mExecutor = null;
        this.mCallback = null;
        this.mExtensionType = extension;
        this.mOutputs = outputs;
        this.mExecutor = executor;
        this.mCallback = listener;
    }

    public int getExtension() {
        return this.mExtensionType;
    }

    public void setPostviewOutputConfiguration(OutputConfiguration postviewOutput) {
        this.mPostviewOutput = postviewOutput;
    }

    public OutputConfiguration getPostviewOutputConfiguration() {
        return this.mPostviewOutput;
    }

    public List<OutputConfiguration> getOutputConfigurations() {
        return this.mOutputs;
    }

    public CameraExtensionSession.StateCallback getStateCallback() {
        return this.mCallback;
    }

    public Executor getExecutor() {
        return this.mExecutor;
    }

    public void setColorSpace(ColorSpace.Named colorSpace) {
        this.mColorSpace = colorSpace.ordinal();
        for (OutputConfiguration outputConfiguration : this.mOutputs) {
            outputConfiguration.setColorSpace(colorSpace);
        }
        if (this.mPostviewOutput != null) {
            this.mPostviewOutput.setColorSpace(colorSpace);
        }
    }

    public void clearColorSpace() {
        this.mColorSpace = -1;
        for (OutputConfiguration outputConfiguration : this.mOutputs) {
            outputConfiguration.clearColorSpace();
        }
        if (this.mPostviewOutput != null) {
            this.mPostviewOutput.clearColorSpace();
        }
    }

    public ColorSpace getColorSpace() {
        if (this.mColorSpace != -1) {
            return ColorSpace.get(ColorSpace.Named.values()[this.mColorSpace]);
        }
        return null;
    }
}
