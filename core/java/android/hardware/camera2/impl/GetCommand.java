package android.hardware.camera2.impl;

import android.hardware.camera2.impl.CameraMetadataNative;

/* loaded from: classes2.dex */
public interface GetCommand {
    <T> T getValue(CameraMetadataNative cameraMetadataNative, CameraMetadataNative.Key<T> key);
}
