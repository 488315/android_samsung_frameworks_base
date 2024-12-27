package android.hardware.camera2.impl;

public interface GetCommand {
    <T> T getValue(CameraMetadataNative cameraMetadataNative, CameraMetadataNative.Key<T> key);
}
