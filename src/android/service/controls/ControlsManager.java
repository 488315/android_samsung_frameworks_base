package android.service.controls;

import com.sec.android.allshare.iface.message.EventMsg;

/* loaded from: classes3.dex */
public class ControlsManager {
    int mVersion = EventMsg.UEVENT_UNZIP_PROFILE;

    public int getVersion() {
        return this.mVersion;
    }

    public boolean hasFeature(String str) {
        return "CONTROLS_SAMSUNG_STYLE".equals(str) || "CONTROLS_LOTTIE_ICON_ANIMATION".equals(str) || "CONTROLS_PROVIDER_INFO".equals(str) || "CONTROLS_CUSTOM_MAIN_ACTION_ICON".equals(str) || "CONTROLS_USE_FULL_SCREEN_DETAIL_DIALOG".equals(str) || "CONTROLS_ALLOW_BASIC_ACTION_WHEN_LOCKED".equals(str) || "CONTROLS_CUSTOM_STATUS".equals(str) || "CONTROLS_AUTO_ADD".equals(str) || "CONTROLS_AUTO_REMOVE".equals(str) || "CONTROLS_USE_CUSTOM_ICON_WITHOUT_SHADOW_BG".equals(str) || "CONTROLS_USE_CUSTOM_ICON_WITHOUT_PADDING".equals(str) || "CONTROLS_DYNAMIC_ORDERING".equals(str) || "CONTROLS_CUSTOM_STATUS_ICON".equals(str) || "CONTROLS_LAYOUT_TYPE".equals(str) || "CONTROLS_AUI".equals(str) || "CONTROLS_OVERLAY_CUSTOM_ICON".equals(str);
    }
}
