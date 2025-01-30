package com.android.server.wm;

import android.graphics.Rect;
import com.samsung.android.core.SizeCompatInfo;
import com.samsung.android.multiwindow.FreeformResizeGuide;

/* loaded from: classes3.dex */
public interface SizeCompatDragPolicy {
  FreeformResizeGuide createCompatResizeGuide();

  boolean ensureDragBounds(Rect rect);

  void fillSizeCompatInfoForDrag(SizeCompatInfo sizeCompatInfo);

  void getTargetDragBounds(Rect rect, Rect rect2, SizeCompatInfo sizeCompatInfo, int i);

  boolean supportsToFreeformByCornerGesture();
}
