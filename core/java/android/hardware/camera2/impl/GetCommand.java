package android.hardware.camera2.impl;


/* loaded from: classes.dex */
public interface GetCommand {
  <T> T getValue(CameraMetadataNative cameraMetadataNative, CameraMetadataNative.Key<T> key);
}
