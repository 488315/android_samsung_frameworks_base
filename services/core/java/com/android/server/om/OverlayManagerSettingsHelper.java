package com.android.server.om;

import android.content.om.OverlayIdentifier;
import android.content.om.OverlayInfo;
import java.util.Set;

/* loaded from: classes2.dex */
public class OverlayManagerSettingsHelper {
  public final OverlayManagerSettings mSettings;

  public OverlayManagerSettingsHelper(OverlayManagerSettings overlayManagerSettings) {
    this.mSettings = overlayManagerSettings;
  }

  public OverlayInfo getNullableOverlayInfo(OverlayIdentifier overlayIdentifier, int i) {
    return this.mSettings.getNullableOverlayInfo(overlayIdentifier, i);
  }

  public Set getAllIdentifiersAndBaseCodePaths() {
    return this.mSettings.getAllIdentifiersAndBaseCodePaths();
  }
}
