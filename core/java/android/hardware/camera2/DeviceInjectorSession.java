package android.hardware.camera2;

import android.os.RemoteException;
import android.util.Size;
import android.view.Surface;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

public abstract class DeviceInjectorSession implements AutoCloseable {

    public abstract static class CharacteristicBuilder {
        public abstract CharacteristicBuilder addSupportedAEMode(int i);

        public abstract CharacteristicBuilder addSupportedAFMode(int i);

        public abstract CharacteristicBuilder addSupportedAWBMode(int i);

        public abstract CharacteristicBuilder addSupportedCaptureSize(Size size);

        public abstract CharacteristicBuilder addSupportedControlMode(int i);

        public abstract CharacteristicBuilder addSupportedEffectMode(int i);

        public abstract CharacteristicBuilder addSupportedSceneMode(int i);

        public abstract CharacteristicBuilder addSupportedStreamingSize(Size size);

        public abstract CameraCharacteristics build() throws IllegalArgumentException;

        public abstract CharacteristicBuilder setAELockAvailable(boolean z);

        public abstract CharacteristicBuilder setAWBLockAvailable(boolean z);

        public abstract CharacteristicBuilder setActiveArraySize(Size size);

        public abstract CharacteristicBuilder setFlashAvailable(boolean z);

        public abstract CharacteristicBuilder setLensFacing(int i);

        public abstract CharacteristicBuilder setSensorOrientation(int i);
    }

    public abstract static class RemoteDevice {
        public abstract void clearRequest();

        public abstract void close();

        public abstract CaptureRequest createDefaultRequest(CaptureRequest.Builder builder);

        public abstract int createStream(Surface surface, Size size);

        public abstract void deleteStream(int i);

        public abstract CameraCharacteristics getCameraCharacteristic(
                CharacteristicBuilder characteristicBuilder);

        public abstract String open(String str, int i);

        public abstract void setCallback(RemoteDeviceCallback remoteDeviceCallback);

        public abstract void submitRequest(CaptureRequest captureRequest, int[] iArr, boolean z);
    }

    public abstract static class RemoteDeviceCallback {
        public static final int ERROR_REMOTE_BUFFER = 1;
        public static final int ERROR_REMOTE_DEVICE = 0;
        public static final int ERROR_REMOTE_UNKNOWN = 2;

        @Retention(RetentionPolicy.SOURCE)
        public @interface ErrorCode {}

        public abstract void onCaptureResult(Map<CaptureResult.Key, Object> map)
                throws RemoteException;

        public abstract void onError(int i) throws RemoteException;

        public abstract void onOrientationChanged(int i)
                throws RemoteException, IllegalArgumentException;
    }

    public abstract static class StatusCallback {
        public static final int ERROR_INJECTION_INVALID_ERROR = -1;
        public static final int ERROR_INJECTION_SERVICE = 1;
        public static final int ERROR_INJECTION_SESSION = 0;
        public static final int ERROR_INJECTION_UNSUPPORTED = 2;

        @Retention(RetentionPolicy.SOURCE)
        public @interface ErrorCode {}

        public abstract void onClose();

        public abstract void onError(int i);

        public abstract void onInjectionPendingStarted(String str, String str2);

        public abstract void onInjectionPendingStopped(String str, String str2);

        public abstract void onInjectionStarted(String str, String str2, String str3);

        public abstract void onInjectionStopped(String str, String str2, String str3);

        public abstract void onSessionCreated(DeviceInjectorSession deviceInjectorSession);
    }

    @Override // java.lang.AutoCloseable
    public abstract void close();

    public abstract void setDeviceInjectorPending(boolean z)
            throws CameraAccessException, SecurityException;
}
