package android.hardware.camera2.impl;


/* loaded from: classes.dex */
public interface CameraCaptureSessionCore {
  void closeWithoutDraining();

  CameraDeviceImpl.StateCallbackKK getDeviceStateCallback();

  boolean isAborting();

  void replaceSessionClose();
}
