package com.android.server.accessibility.magnification;

import android.provider.DeviceConfig;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class MagnificationThumbnailFeatureFlag extends MagnificationFeatureFlagBase {
  @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
  public boolean getDefaultValue() {
    return true;
  }

  @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
  public String getFeatureName() {
    return "enable_magnifier_thumbnail";
  }

  @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
  public String getNamespace() {
    return "accessibility";
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
}
