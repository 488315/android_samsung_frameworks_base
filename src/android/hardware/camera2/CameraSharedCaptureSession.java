package android.hardware.camera2;

import android.annotation.SystemApi;
import android.hardware.camera2.CameraCaptureSession;
import android.view.Surface;
import java.util.List;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes2.dex */
public abstract class CameraSharedCaptureSession extends CameraCaptureSession {
    @SystemApi
    public abstract int startStreaming(List<Surface> list, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException;

    @SystemApi
    public abstract void stopStreaming() throws CameraAccessException;
}
