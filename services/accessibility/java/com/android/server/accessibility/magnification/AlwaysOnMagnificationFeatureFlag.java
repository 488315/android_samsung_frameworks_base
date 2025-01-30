package com.android.server.accessibility.magnification;

import android.R;
import android.content.Context;
import android.provider.DeviceConfig;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class AlwaysOnMagnificationFeatureFlag extends MagnificationFeatureFlagBase {
  public Context mContext;

  @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
  public String getFeatureName() {
    return "AlwaysOnMagnifier__enable_always_on_magnifier";
  }

  @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
  public String getNamespace() {
    return "window_manager";
  }

  @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
  public /* bridge */ /* synthetic */ DeviceConfig.OnPropertiesChangedListener addOnChangedListener(
      Executor executor, Runnable runnable) {
    return super.addOnChangedListener(executor, runnable);
  }

  @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
  public /* bridge */ /* synthetic */ boolean isFeatureFlagEnabled() {
    return super.isFeatureFlagEnabled();
  }

  @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
  public /* bridge */ /* synthetic */ boolean setFeatureFlagEnabled(boolean z) {
    return super.setFeatureFlagEnabled(z);
  }

  public AlwaysOnMagnificationFeatureFlag(Context context) {
    this.mContext = context;
  }

  @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
  public boolean getDefaultValue() {
    return this.mContext
        .getResources()
        .getBoolean(R.bool.config_isWindowManagerCameraCompatSplitScreenAspectRatioEnabled);
  }
}
