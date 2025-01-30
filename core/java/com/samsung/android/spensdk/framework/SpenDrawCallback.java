package com.samsung.android.spensdk.framework;

import android.graphics.RectF;

/* loaded from: classes5.dex */
public interface SpenDrawCallback {
  RectF onDraw(SpenDrawGlInfo spenDrawGlInfo);

  void onProcessWithNoContext();

  void onProcessWithoutScreenUpdate();

  void onSync();
}
