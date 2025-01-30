package com.samsung.android.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RecordingCanvas;
import android.util.Log;
import android.view.SemBlurInfo;
import android.view.View;

/* loaded from: classes5.dex */
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

  private static native void nApplyToBitmap(int i, int[] iArr, int[] iArr2, int i2, int i3);

  private static native int nCreate();

  private static native void nDestroy(int i);

  private static native float nGetParam(int i, int i2);

  private static native void nSetParam(int i, int i2, float f);

  public SemGfxImageFilter() {
    this.nativeFunctor = 0;
    this.nativeFunctor = nCreate();
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
      Log.m94d(LOG_TAG, "Will set View.LayerType to View.LAYER_TYPE_HARDWARE!");
      view.setLayerType(2, null);
    }
  }

  public final void onDetachedFromView() {
    this.attachedToView = null;
  }

  public final void draw(Canvas canvas) {
    View view = this.attachedToView;
    if (view == null) {
      Log.m96e(LOG_TAG, "Can't draw SemGfxImageFilter. Should be attached to View!");
      return;
    }
    if (view.getLayerType() != 2) {
      Log.m96e(LOG_TAG, "Can't draw SemGfxImageFilter. LayerType must be 'LAYER_TYPE_HARDWARE'!");
    } else if (!(canvas instanceof RecordingCanvas)) {
      Log.m96e(
          LOG_TAG, "Can't draw SemGfxImageFilter. Canvas should be instance of 'RecordingCanvas'!");
    } else {
      ((RecordingCanvas) canvas).drawWebViewFunctor(this.nativeFunctor);
    }
  }

  public final void setBlurPreset(int preset) {
    if (preset < 11 || preset > 16) {
      Log.m96e(LOG_TAG, "BlurPreset is not valid!");
      return;
    }
    float[] presetAttrs = SemBlurInfo.Builder.getBlurPresetAttrs(preset);
    if (presetAttrs.length != 7) {
      Log.m96e(LOG_TAG, "BlurPreset size is a mismatch with SemGfxImageFilter!");
      return;
    }
    setBlurRadius(presetAttrs[0]);
    setProportionalSaturation(presetAttrs[1]);
    setCurveLevel(presetAttrs[2]);
    setCurveMinX(presetAttrs[3]);
    setCurveMaxX(presetAttrs[4]);
    setCurveMinY(presetAttrs[5]);
    setCurveMaxY(presetAttrs[6]);
  }

  public final void setBlurQuality(float quality) {
    setParam(0, clamp(quality, 0.0f, 1.0f));
  }

  public void setBlurRadius(float radius) {
    setParam(1, clamp(radius, 0.0f, 1000.0f));
  }

  public void setSaturation(float saturation) {
    setParam(2, clamp(saturation, -100.0f, 100.0f));
  }

  public void setVibrance(float vibrance) {
    setParam(3, clamp(vibrance, -100.0f, 100.0f));
  }

  public void setProportionalSaturation(float saturation) {
    setParam(10, clamp(saturation, -1.0f, 1.0f));
  }

  public void setCurveMaxX(float maxX) {
    setParam(4, clamp(maxX, 0.0f, 255.0f));
    setParam(9, 1.0f);
  }

  public void setCurveMinX(float minX) {
    setParam(5, clamp(minX, 0.0f, 255.0f));
    setParam(9, 1.0f);
  }

  public void setCurveMaxY(float maxY) {
    setParam(6, clamp(maxY, 0.0f, 255.0f));
    setParam(9, 1.0f);
  }

  public void setCurveMinY(float minY) {
    setParam(7, clamp(minY, 0.0f, 255.0f));
    setParam(9, 1.0f);
  }

  public void setCurveLevel(float level) {
    setParam(8, clamp(level, -100.0f, 100.0f));
    setParam(9, 1.0f);
  }

  public Bitmap applyToBitmap(Bitmap bitmap) {
    Bitmap res =
        Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
    applyToBitmap(bitmap, res);
    return res;
  }

  public void applyToBitmap(Bitmap srcBitmap, Bitmap dstBitmap) {
    if (srcBitmap.getConfig() != Bitmap.Config.ARGB_8888) {
      throw new IllegalArgumentException("srcBitmap config should be ARGB_8888");
    }
    if (dstBitmap.getConfig() != Bitmap.Config.ARGB_8888) {
      throw new IllegalArgumentException("dstBitmap config should be ARGB_8888");
    }
    int w = srcBitmap.getWidth();
    int h = srcBitmap.getHeight();
    if (w != dstBitmap.getWidth()) {
      throw new IllegalArgumentException("Width of srcBitmap and dstBitmap should be same");
    }
    if (h != dstBitmap.getHeight()) {
      throw new IllegalArgumentException("Height of srcBitmap and dstBitmap should be same");
    }
    int[] src = new int[w * h];
    int[] dst = new int[src.length];
    srcBitmap.getPixels(src, 0, w, 0, 0, w, h);
    nApplyToBitmap(this.nativeFunctor, src, dst, w, h);
    dstBitmap.setPixels(dst, 0, w, 0, 0, w, h);
  }

  private void setParam(int id, float val) {
    nSetParam(this.nativeFunctor, id, val);
    View view = this.attachedToView;
    if (view != null) {
      view.invalidate();
    }
  }

  private float getParam(int id) {
    return nGetParam(this.nativeFunctor, id);
  }

  private static float clamp(float val, float min, float max) {
    return Math.max(min, Math.min(max, val));
  }
}
