package android.hardware.camera2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class CameraOfflineSession extends CameraCaptureSession {

    public abstract static class CameraOfflineSessionCallback {
        public static final int STATUS_INTERNAL_ERROR = 0;

        @Retention(RetentionPolicy.SOURCE)
        public @interface StatusCode {}

        public abstract void onClosed(CameraOfflineSession cameraOfflineSession);

        public abstract void onError(CameraOfflineSession cameraOfflineSession, int i);

        public abstract void onIdle(CameraOfflineSession cameraOfflineSession);

        public abstract void onReady(CameraOfflineSession cameraOfflineSession);

        public abstract void onSwitchFailed(CameraOfflineSession cameraOfflineSession);
    }

    @Override // android.hardware.camera2.CameraCaptureSession, java.lang.AutoCloseable
    public abstract void close();
}
