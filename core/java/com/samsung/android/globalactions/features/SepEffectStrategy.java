package com.samsung.android.globalactions.features;

import android.view.Window;
import android.view.WindowManager;
import com.samsung.android.globalactions.presentation.strategies.WindowDecorationStrategy;

/* loaded from: classes5.dex */
public class SepEffectStrategy implements WindowDecorationStrategy {
  private final float BLUR_DIM_AMOUNT = 0.3f;

  @Override // com.samsung.android.globalactions.presentation.strategies.WindowDecorationStrategy
  public void onDecorateWindow(Window window) {
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.dimAmount = 0.3f;
    layoutParams.samsungFlags |= 64;
    window.setAttributes(layoutParams);
  }
}
