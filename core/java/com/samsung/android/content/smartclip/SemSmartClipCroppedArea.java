package com.samsung.android.content.smartclip;

import android.graphics.Rect;
import android.view.View;

/* loaded from: classes5.dex */
public interface SemSmartClipCroppedArea {
  Rect getRect();

  boolean intersects(Rect rect);

  boolean intersects(View view);
}
