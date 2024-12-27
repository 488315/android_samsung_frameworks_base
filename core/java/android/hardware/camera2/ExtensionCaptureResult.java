package android.hardware.camera2;

import android.graphics.PointF;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.impl.ExtensionKey;
import android.hardware.camera2.impl.PublicKey;
import android.util.Pair;

/* loaded from: classes2.dex */
public final class ExtensionCaptureResult {

    @ExtensionKey
    @PublicKey
    public static final CaptureResult.Key<int[]> EFV_PADDING_REGION = CaptureResult.EFV_PADDING_REGION;

    @ExtensionKey
    @PublicKey
    public static final CaptureResult.Key<int[]> EFV_AUTO_ZOOM_PADDING_REGION = CaptureResult.EFV_AUTO_ZOOM_PADDING_REGION;

    @ExtensionKey
    @PublicKey
    public static final CaptureResult.Key<PointF[]> EFV_TARGET_COORDINATES = CaptureResult.EFV_TARGET_COORDINATES;

    @ExtensionKey
    @PublicKey
    public static final CaptureResult.Key<Float> EFV_PADDING_ZOOM_FACTOR = CaptureResult.EFV_PADDING_ZOOM_FACTOR;

    @ExtensionKey
    @PublicKey
    public static final CaptureResult.Key<Integer> EFV_STABILIZATION_MODE = CaptureResult.EFV_STABILIZATION_MODE;

    @ExtensionKey
    @PublicKey
    public static final CaptureResult.Key<Boolean> EFV_AUTO_ZOOM = CaptureResult.EFV_AUTO_ZOOM;

    @ExtensionKey
    @PublicKey
    public static final CaptureResult.Key<Float> EFV_ROTATE_VIEWPORT = CaptureResult.EFV_ROTATE_VIEWPORT;

    @ExtensionKey
    @PublicKey
    public static final CaptureResult.Key<Pair<Integer, Integer>> EFV_TRANSLATE_VIEWPORT = CaptureResult.EFV_TRANSLATE_VIEWPORT;

    @ExtensionKey
    @PublicKey
    public static final CaptureResult.Key<Float> EFV_MAX_PADDING_ZOOM_FACTOR = CaptureResult.EFV_MAX_PADDING_ZOOM_FACTOR;

    private ExtensionCaptureResult() {
    }
}
