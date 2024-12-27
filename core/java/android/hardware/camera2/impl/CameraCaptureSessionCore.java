package android.hardware.camera2.impl;

public interface CameraCaptureSessionCore {
    void closeWithoutDraining();

    CameraDeviceImpl.StateCallbackKK getDeviceStateCallback();

    boolean isAborting();

    void replaceSessionClose();
}
