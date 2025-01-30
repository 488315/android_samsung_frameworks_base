package android.service.controls;

/* loaded from: classes3.dex */
public class ControlsManager {
  int mVersion = 6000;

  public int getVersion() {
    return this.mVersion;
  }

  public boolean hasFeature(String feature) {
    return "CONTROLS_SAMSUNG_STYLE".equals(feature)
        || "CONTROLS_LOTTIE_ICON_ANIMATION".equals(feature)
        || "CONTROLS_PROVIDER_INFO".equals(feature)
        || "CONTROLS_CUSTOM_MAIN_ACTION_ICON".equals(feature)
        || "CONTROLS_USE_FULL_SCREEN_DETAIL_DIALOG".equals(feature)
        || "CONTROLS_ALLOW_BASIC_ACTION_WHEN_LOCKED".equals(feature)
        || "CONTROLS_CUSTOM_STATUS".equals(feature)
        || "CONTROLS_AUTO_ADD".equals(feature)
        || "CONTROLS_AUTO_REMOVE".equals(feature)
        || "CONTROLS_USE_CUSTOM_ICON_WITHOUT_SHADOW_BG".equals(feature)
        || "CONTROLS_USE_CUSTOM_ICON_WITHOUT_PADDING".equals(feature)
        || "CONTROLS_DYNAMIC_ORDERING".equals(feature)
        || "CONTROLS_CUSTOM_STATUS_ICON".equals(feature)
        || "CONTROLS_LAYOUT_TYPE".equals(feature)
        || "CONTROLS_AUI".equals(feature)
        || "CONTROLS_OVERLAY_CUSTOM_ICON".equals(feature);
  }
}
