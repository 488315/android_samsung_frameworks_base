package android.hardware.camera2;

import android.hardware.camera2.impl.ExtensionKey;
import android.hardware.camera2.impl.PublicKey;
import android.util.Pair;

public final class ExtensionCaptureRequest {
    public static final int EFV_STABILIZATION_MODE_GIMBAL = 1;
    public static final int EFV_STABILIZATION_MODE_LOCKED = 2;
    public static final int EFV_STABILIZATION_MODE_OFF = 0;

    @ExtensionKey @PublicKey
    public static final CaptureRequest.Key<Float> EFV_PADDING_ZOOM_FACTOR =
            CaptureRequest.EFV_PADDING_ZOOM_FACTOR;

    @ExtensionKey @PublicKey
    public static final CaptureRequest.Key<Boolean> EFV_AUTO_ZOOM = CaptureRequest.EFV_AUTO_ZOOM;

    @ExtensionKey @PublicKey
    public static final CaptureRequest.Key<Float> EFV_MAX_PADDING_ZOOM_FACTOR =
            CaptureRequest.EFV_MAX_PADDING_ZOOM_FACTOR;

    @ExtensionKey @PublicKey
    public static final CaptureRequest.Key<Integer> EFV_STABILIZATION_MODE =
            CaptureRequest.EFV_STABILIZATION_MODE;

    @ExtensionKey @PublicKey
    public static final CaptureRequest.Key<Pair<Integer, Integer>> EFV_TRANSLATE_VIEWPORT =
            CaptureRequest.EFV_TRANSLATE_VIEWPORT;

    @ExtensionKey @PublicKey
    public static final CaptureRequest.Key<Float> EFV_ROTATE_VIEWPORT =
            CaptureRequest.EFV_ROTATE_VIEWPORT;

    private ExtensionCaptureRequest() {}
}
