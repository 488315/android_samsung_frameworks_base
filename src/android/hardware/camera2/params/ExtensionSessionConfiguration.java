package android.hardware.camera2.params;

import android.graphics.ColorSpace;
import android.hardware.camera2.CameraExtensionSession;
import java.util.Iterator;
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

    public ExtensionSessionConfiguration(int i, List<OutputConfiguration> list, Executor executor, CameraExtensionSession.StateCallback stateCallback) {
        this.mExtensionType = i;
        this.mOutputs = list;
        this.mExecutor = executor;
        this.mCallback = stateCallback;
    }

    public int getExtension() {
        return this.mExtensionType;
    }

    public void setPostviewOutputConfiguration(OutputConfiguration outputConfiguration) {
        this.mPostviewOutput = outputConfiguration;
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

    public void setColorSpace(ColorSpace.Named named) {
        this.mColorSpace = named.ordinal();
        Iterator<OutputConfiguration> it = this.mOutputs.iterator();
        while (it.hasNext()) {
            it.next().setColorSpace(named);
        }
        OutputConfiguration outputConfiguration = this.mPostviewOutput;
        if (outputConfiguration != null) {
            outputConfiguration.setColorSpace(named);
        }
    }

    public void clearColorSpace() {
        this.mColorSpace = -1;
        Iterator<OutputConfiguration> it = this.mOutputs.iterator();
        while (it.hasNext()) {
            it.next().clearColorSpace();
        }
        OutputConfiguration outputConfiguration = this.mPostviewOutput;
        if (outputConfiguration != null) {
            outputConfiguration.clearColorSpace();
        }
    }

    public ColorSpace getColorSpace() {
        if (this.mColorSpace != -1) {
            return ColorSpace.get(ColorSpace.Named.values()[this.mColorSpace]);
        }
        return null;
    }
}
