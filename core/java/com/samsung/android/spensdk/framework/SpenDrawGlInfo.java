package com.samsung.android.spensdk.framework;

import android.graphics.Rect;

/* loaded from: classes5.dex */
public class SpenDrawGlInfo {
  public Rect mClipRect;
  public int mScreenHeight;
  public int mScreenWidth;
  public float[] mTransformMatrix = new float[16];

  public SpenDrawGlInfo(Rect clipRect, int screenWidth, int screenHeight, float[] transform) {
    this.mClipRect = clipRect;
    this.mScreenWidth = screenWidth;
    this.mScreenHeight = screenHeight;
    setMatrix(transform);
  }

  private void setClipRect(Rect clipRect) {
    this.mClipRect = clipRect;
  }

  private void setSize(int width, int height) {
    this.mScreenWidth = width;
    this.mScreenHeight = height;
  }

  private void setMatrix(float[] transform) {
    if (transform.length != this.mTransformMatrix.length) {
      return;
    }
    int i = 0;
    while (true) {
      float[] fArr = this.mTransformMatrix;
      if (i < fArr.length) {
        fArr[i] = transform[i];
        i++;
      } else {
        return;
      }
    }
  }
}
