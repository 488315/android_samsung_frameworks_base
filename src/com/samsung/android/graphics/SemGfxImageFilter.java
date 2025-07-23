package com.samsung.android.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RecordingCanvas;
import android.util.Log;
import android.view.SemBlurInfo;
import android.view.View;
import java.util.List;

/* loaded from: classes6.dex */
public class SemGfxImageFilter {
    private static final String LOG_TAG = "SemGfxImageFilter";
    private static final int PARAM_INDEX_BLUR_QUALITY = 0;
    private static final int PARAM_INDEX_BLUR_RADIUS = 1;
    private static final int PARAM_INDEX_CURVE_CHANGED = 9;
    private static final int PARAM_INDEX_CURVE_LEVEL = 8;
    private static final int PARAM_INDEX_CURVE_MAXX = 4;
    private static final int PARAM_INDEX_CURVE_MAXY = 6;
    private static final int PARAM_INDEX_CURVE_MINX = 5;
    private static final int PARAM_INDEX_CURVE_MINY = 7;
    private static final int PARAM_INDEX_PROSATURATION = 10;
    private static final int PARAM_INDEX_SATURATION = 2;
    private static final int PARAM_INDEX_VIBRANCE = 3;
    private View attachedToView = null;
    private int nativeFunctor;

    private static native void nApplyMeshGradient(int i, int i2, int i3, int[] iArr, PointF[] pointFArr);

    private static native void nApplyToBitmap(int i, int[] iArr, int[] iArr2, int i2, int i3);

    private static native int nCreate();

    private static native void nDestroy(int i);

    private static native void nDestroyContext();

    private static native float nGetParam(int i, int i2);

    private static native void nSetIndexedColor(int i, int i2, int i3);

    private static native void nSetIndexedPoint(int i, int i2, float f, float f2);

    private static native void nSetMeshHandles(int i, int i2, float f, float f2, float f3, float f4);

    private static native void nSetParam(int i, int i2, float f);

    @Deprecated(forRemoval = true, since = "17.0")
    public SemGfxImageFilter() {
        this.nativeFunctor = 0;
        this.nativeFunctor = nCreate();
    }

    public static void destroyContext() {
        nDestroyContext();
    }

    protected void finalize() throws Throwable {
        super.finalize();
        nDestroy(this.nativeFunctor);
    }

    public final void onAttachToView(View view) {
        View view2 = this.attachedToView;
        if (view2 != null) {
            view2.semSetGfxImageFilter(null);
        }
        this.attachedToView = view;
        if (view.getLayerType() != 2) {
            Log.d(LOG_TAG, "Will set View.LayerType to View.LAYER_TYPE_HARDWARE!");
            view.setLayerType(2, null);
        }
    }

    public final void onDetachedFromView() {
        this.attachedToView = null;
    }

    public final void draw(Canvas canvas) {
        View view = this.attachedToView;
        if (view == null) {
            Log.e(LOG_TAG, "Can't draw SemGfxImageFilter. Should be attached to View!");
            return;
        }
        if (view.getLayerType() != 2) {
            Log.e(LOG_TAG, "Can't draw SemGfxImageFilter. LayerType must be 'LAYER_TYPE_HARDWARE'!");
        } else if (!(canvas instanceof RecordingCanvas)) {
            Log.e(LOG_TAG, "Can't draw SemGfxImageFilter. Canvas should be instance of 'RecordingCanvas'!");
        } else {
            ((RecordingCanvas) canvas).drawWebViewFunctor(this.nativeFunctor);
        }
    }

    @Deprecated(forRemoval = true, since = "17.0")
    public final void setBlurPreset(int i) {
        float[] blurPresetAttrs = SemBlurInfo.Builder.getBlurPresetAttrs(i);
        if (blurPresetAttrs == null) {
            Log.e(LOG_TAG, "BlurPresetAttrs was null");
            return;
        }
        if (blurPresetAttrs.length != 7) {
            Log.e(LOG_TAG, "BlurPreset size is a mismatch with SemGfxImageFilter!");
            return;
        }
        setBlurRadius(blurPresetAttrs[0]);
        setProportionalSaturation(blurPresetAttrs[1]);
        setCurveLevel(blurPresetAttrs[2]);
        setCurveMinX(blurPresetAttrs[3]);
        setCurveMaxX(blurPresetAttrs[4]);
        setCurveMinY(blurPresetAttrs[5]);
        setCurveMaxY(blurPresetAttrs[6]);
    }

    @Deprecated(forRemoval = true, since = "17.0")
    public final void setBlurQuality(float f) {
        setParam(0, clamp(f, 0.0f, 1.0f));
    }

    @Deprecated(forRemoval = true, since = "17.0")
    public void setBlurRadius(float f) {
        setParam(1, clamp(f, 0.0f, 1000.0f));
    }

    public void applyMeshGradient(int i, int i2, int[] iArr, List<PointF> list) {
        int i3 = i * i2;
        if (iArr.length != i3) {
            throw new IllegalArgumentException("Colors array must have exactly width * height elements.");
        }
        if (list.size() != i3) {
            throw new IllegalArgumentException("Points list must have exactly width * height elements.");
        }
        nApplyMeshGradient(this.nativeFunctor, i, i2, iArr, (PointF[]) list.toArray(new PointF[0]));
    }

    public void setIndexedColor(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("Index must be greater than or equal to 0.");
        }
        nSetIndexedColor(this.nativeFunctor, i, i2);
    }

    public void setIndexedPoint(int i, PointF pointF) {
        if (i < 0) {
            throw new IllegalArgumentException("Index must be greater than or equal to 0.");
        }
        nSetIndexedPoint(this.nativeFunctor, i, pointF.x, pointF.y);
    }

    public void setIndexedMeshHandles(int i, PointF pointF, PointF pointF2) {
        if (i < 0) {
            throw new IllegalArgumentException("Index must be greater than or equal to 0.");
        }
        nSetMeshHandles(this.nativeFunctor, i, pointF.x, pointF.y, pointF2.x, pointF2.y);
    }

    @Deprecated(forRemoval = true, since = "17.0")
    public void setSaturation(float f) {
        setParam(2, clamp(f, -100.0f, 100.0f));
    }

    @Deprecated(forRemoval = true, since = "17.0")
    public void setVibrance(float f) {
        setParam(3, clamp(f, -100.0f, 100.0f));
    }

    @Deprecated(forRemoval = true, since = "17.0")
    public void setProportionalSaturation(float f) {
        setParam(10, clamp(f, -1.0f, 1.0f));
    }

    @Deprecated(forRemoval = true, since = "17.0")
    public void setCurveMaxX(float f) {
        setParam(4, clamp(f, 0.0f, 255.0f));
        setParam(9, 1.0f);
    }

    @Deprecated(forRemoval = true, since = "17.0")
    public void setCurveMinX(float f) {
        setParam(5, clamp(f, 0.0f, 255.0f));
        setParam(9, 1.0f);
    }

    @Deprecated(forRemoval = true, since = "17.0")
    public void setCurveMaxY(float f) {
        setParam(6, clamp(f, 0.0f, 255.0f));
        setParam(9, 1.0f);
    }

    @Deprecated(forRemoval = true, since = "17.0")
    public void setCurveMinY(float f) {
        setParam(7, clamp(f, 0.0f, 255.0f));
        setParam(9, 1.0f);
    }

    @Deprecated(forRemoval = true, since = "17.0")
    public void setCurveLevel(float f) {
        setParam(8, clamp(f, -100.0f, 100.0f));
        setParam(9, 1.0f);
    }

    @Deprecated(forRemoval = true, since = "17.0")
    public Bitmap applyToBitmap(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        applyToBitmap(bitmap, createBitmap);
        return createBitmap;
    }

    public void applyToBitmap(Bitmap bitmap, Bitmap bitmap2) {
        if (bitmap.getConfig() != Bitmap.Config.ARGB_8888) {
            throw new IllegalArgumentException("srcBitmap config should be ARGB_8888");
        }
        if (bitmap2.getConfig() != Bitmap.Config.ARGB_8888) {
            throw new IllegalArgumentException("dstBitmap config should be ARGB_8888");
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width != bitmap2.getWidth()) {
            throw new IllegalArgumentException("Width of srcBitmap and dstBitmap should be same");
        }
        if (height != bitmap2.getHeight()) {
            throw new IllegalArgumentException("Height of srcBitmap and dstBitmap should be same");
        }
        int i = width * height;
        int[] iArr = new int[i];
        int[] iArr2 = new int[i];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        nApplyToBitmap(this.nativeFunctor, iArr, iArr2, width, height);
        bitmap2.setPixels(iArr2, 0, width, 0, 0, width, height);
    }

    private void setParam(int i, float f) {
        nSetParam(this.nativeFunctor, i, f);
        View view = this.attachedToView;
        if (view != null) {
            view.invalidate();
        }
    }

    private float getParam(int i) {
        return nGetParam(this.nativeFunctor, i);
    }

    private static float clamp(float f, float f2, float f3) {
        return Math.max(f2, Math.min(f3, f));
    }
}
