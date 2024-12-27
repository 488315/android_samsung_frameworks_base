package com.android.systemui.media.mediaoutput.common;

import androidx.datastore.preferences.core.Preferences;

public final class PreferenceDebugLabsKeys {
    public static final PreferenceDebugLabsKeys INSTANCE = new PreferenceDebugLabsKeys();
    public static final Preferences.Key SHOW_LABS_MENU = new Preferences.Key("show_labs_mode");
    public static final Preferences.Key GRAYSCALE_THUMBNAIL = new Preferences.Key("grayscale_thumbnail");
    public static final Preferences.Key SUPPORT_MULTIPLE_MEDIA_SESSION = new Preferences.Key("support_multiple_media_session");
    public static final Preferences.Key SUPPORT_SELECTABLE_BUDS_TOGETHER = new Preferences.Key("support_selectable_buds_together");
    public static final Preferences.Key SUPPORT_DISPLAY_DEVICE_VOLUME_CONTROL = new Preferences.Key("support_display_device_volume_control");
    public static final Preferences.Key SUPPORT_TRANSFERABLE_ROUTES_WHILE_CONNECTING = new Preferences.Key("support_transferable_routes_while_connecting");
    public static final Preferences.Key SUPPORT_FOR_TRANSFER_DURING_ROUTING = new Preferences.Key("support_for_transfer_during_routing");
    public static final Preferences.Key SUPPORT_SPOTIFY_CHROMECAST = new Preferences.Key("support_spotify_chromecast");
    public static final Preferences.Key SUPPORT_DISPLAY_ONLY_REMOTE_DEVICE = new Preferences.Key("support_display_only_remote_device");
    public static final Preferences.Key SUPPORT_FOR_UNSUPPORTED_TV = new Preferences.Key("support_for_unsupported_tv");

    private PreferenceDebugLabsKeys() {
    }
}
