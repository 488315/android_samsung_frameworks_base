package com.android.systemui.flags;

import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class Flags {
    public static final ReleasedFlag ALWAYS_SHOW_HOME_CONTROLS_ON_DREAMS;
    public static final SysPropBooleanFlag BUILDER_EXTRAS_OVERRIDE;
    public static final ResourceBooleanFlag CHARGING_RIPPLE;
    public static final ResourceBooleanFlag COMMUNAL_SERVICE_ENABLED;
    public static final UnreleasedFlag ENABLE_DARK_VIGNETTE_WHEN_FOLDING;
    public static final UnreleasedFlag ENABLE_NOTIFICATIONS_SIMULATE_SLOW_MEASURE;
    public static final UnreleasedFlag ENABLE_UNFOLD_STATUS_BAR_ANIMATIONS;
    public static final ReleasedFlag ENABLE_USI_BATTERY_NOTIFICATIONS;
    public static final ReleasedFlag ENABLE_WALLET_CONTEXTUAL_LOYALTY_CARDS;
    public static final ReleasedFlag FILTER_PROVISIONING_NETWORK_SUBSCRIPTIONS;
    public static final ResourceBooleanFlag FULL_SCREEN_USER_SWITCHER;
    public static final ReleasedFlag HIDE_SMARTSPACE_ON_DREAM_OVERLAY;
    public static final ReleasedFlag INCOMPATIBLE_CHARGING_BATTERY_ICON;
    public static final Flags INSTANCE = new Flags();
    public static final ReleasedFlag KEYBOARD_BACKLIGHT_INDICATOR;
    public static final ResourceBooleanFlag LOCKSCREEN_CUSTOM_CLOCKS;
    public static final UnreleasedFlag LOCKSCREEN_ENABLE_LANDSCAPE;
    public static final ResourceBooleanFlag MONET;
    public static final ReleasedFlag MONOCHROMATIC_THEME;
    public static final ReleasedFlag NEW_UNLOCK_SWIPE_ANIMATION;
    public static final ReleasedFlag NOTE_TASKS;
    public static final ResourceBooleanFlag NOTIFICATION_DRAG_TO_CONTENTS;
    public static final UnreleasedFlag NULL_FLAG;
    public static final ReleasedFlag POWER_MENU_LITE;
    public static final ReleasedFlag QS_SECONDARY_DATA_SUB_INFO;
    public static final ReleasedFlag ROAMING_INDICATOR_VIA_DISPLAY_INFO;
    public static final ReleasedFlag SCREENSHOT_APP_CLIPS;
    public static final ReleasedFlag SHARE_WIFI_QS_BUTTON;
    public static final ReleasedFlag SHORTCUT_LIST_SEARCH_LAYOUT;
    public static final ReleasedFlag SIGNAL_CALLBACK_DEPRECATION;
    public static final ReleasedFlag SMARTSPACE_SHARED_ELEMENT_TRANSITION_ENABLED;
    public static final ReleasedFlag TRACK_STYLUS_EVER_USED;
    public static final ReleasedFlag WALLPAPER_FULLSCREEN_PREVIEW;
    public static final ReleasedFlag WALLPAPER_PICKER_PAGE_TRANSITIONS;
    public static final ReleasedFlag WALLPAPER_PICKER_PREVIEW_ANIMATION;
    public static final ReleasedFlag WALLPAPER_PICKER_UI_FOR_AIWP;
    public static final SysPropBooleanFlag WM_ALWAYS_ENFORCE_PREDICTIVE_BACK;
    public static final SysPropBooleanFlag WM_ENABLE_PREDICTIVE_BACK_ANIM;
    public static final SysPropBooleanFlag WM_ENABLE_SHELL_TRANSITIONS;

    static {
        FlagsFactory flagsFactory = FlagsFactory.INSTANCE;
        NULL_FLAG = FlagsFactory.unreleasedFlag$default(6, flagsFactory, "null_flag");
        FlagsFactory.unreleasedFlag$default(6, flagsFactory, "notification_pipeline_developer_logging");
        FlagsFactory.unreleasedFlag$default(6, flagsFactory, "nssl_debug_lines");
        FlagsFactory.unreleasedFlag$default(6, flagsFactory, "nssl_debug_remove_animation");
        NOTIFICATION_DRAG_TO_CONTENTS = FlagsFactory.resourceBooleanFlag$default(R.bool.config_notificationToContents, flagsFactory, "notification_drag_to_contents");
        BUILDER_EXTRAS_OVERRIDE = FlagsFactory.sysPropBooleanFlag$default(flagsFactory, "persist.sysui.notification.builder_extras_override", true);
        NEW_UNLOCK_SWIPE_ANIMATION = FlagsFactory.releasedFlag$default(flagsFactory, "new_unlock_swipe_animation");
        CHARGING_RIPPLE = FlagsFactory.resourceBooleanFlag$default(R.bool.flag_charging_ripple, flagsFactory, "charging_ripple");
        LOCKSCREEN_CUSTOM_CLOCKS = FlagsFactory.resourceBooleanFlag$default(R.bool.config_enableLockScreenCustomClocks, flagsFactory, "lockscreen_custom_clocks");
        FlagsFactory.unreleasedFlag$default(6, flagsFactory, "biometrics_animation_revamp");
        ENABLE_WALLET_CONTEXTUAL_LOYALTY_CARDS = FlagsFactory.releasedFlag$default(flagsFactory, "enable_wallet_contextual_loyalty_cards");
        WALLPAPER_FULLSCREEN_PREVIEW = FlagsFactory.releasedFlag$default(flagsFactory, "wallpaper_fullscreen_preview");
        WALLPAPER_PICKER_UI_FOR_AIWP = FlagsFactory.releasedFlag$default(flagsFactory, "wallpaper_picker_ui_for_aiwp");
        WALLPAPER_PICKER_PAGE_TRANSITIONS = FlagsFactory.releasedFlag$default(flagsFactory, "wallpaper_picker_page_transitions");
        FlagsFactory.unreleasedFlag$default(6, flagsFactory, "wallpaper_picker_grid_apply_button");
        FlagsFactory.unreleasedFlag$default(6, flagsFactory, "keyguard_talkback_fix");
        WALLPAPER_PICKER_PREVIEW_ANIMATION = FlagsFactory.releasedFlag$default(flagsFactory, "wallpaper_picker_preview_animation");
        POWER_MENU_LITE = FlagsFactory.releasedFlag$default(flagsFactory, "power_menu_lite");
        SMARTSPACE_SHARED_ELEMENT_TRANSITION_ENABLED = FlagsFactory.releasedFlag$default(flagsFactory, "smartspace_shared_element_transition_enabled");
        HIDE_SMARTSPACE_ON_DREAM_OVERLAY = FlagsFactory.releasedFlag$default(flagsFactory, "hide_smartspace_on_dream_overlay");
        FlagsFactory.releasedFlag$default(flagsFactory, "show_weather_complication_on_dream_overlay");
        FlagsFactory.resourceBooleanFlag$default(R.bool.flag_conversations, flagsFactory, "people_tile");
        FlagsFactory.resourceBooleanFlag$default(R.bool.flag_lockscreen_qs_user_detail_shortcut, flagsFactory, "qs_user_detail_shortcut");
        FULL_SCREEN_USER_SWITCHER = FlagsFactory.resourceBooleanFlag$default(R.bool.config_enableFullscreenUserSwitcher, flagsFactory, "full_screen_user_switcher");
        QS_SECONDARY_DATA_SUB_INFO = FlagsFactory.releasedFlag$default(flagsFactory, "qs_secondary_data_sub_info");
        FlagsFactory.unreleasedFlag$default(2, flagsFactory, "enable_new_qs_edit_mode");
        SIGNAL_CALLBACK_DEPRECATION = FlagsFactory.releasedFlag$default(flagsFactory, "signal_callback_deprecation");
        ROAMING_INDICATOR_VIA_DISPLAY_INFO = FlagsFactory.releasedFlag$default(flagsFactory, "roaming_indicator_via_display_info");
        FILTER_PROVISIONING_NETWORK_SUBSCRIPTIONS = FlagsFactory.releasedFlag$default(flagsFactory, "filter_provisioning_network_subscriptions");
        INCOMPATIBLE_CHARGING_BATTERY_ICON = FlagsFactory.releasedFlag$default(flagsFactory, "incompatible_charging_battery_icon");
        FlagsFactory.unreleasedFlag$default(2, flagsFactory, "new_shade_carrier_group_mobile_icons");
        MONET = FlagsFactory.resourceBooleanFlag$default(R.bool.flag_monet, flagsFactory, "monet");
        FlagsFactory.unreleasedFlag$default(6, flagsFactory, "region_sampling");
        FlagsFactory.unreleasedFlag$default(6, flagsFactory, "screen_contents_translation");
        MONOCHROMATIC_THEME = FlagsFactory.releasedFlag$default(flagsFactory, "monochromatic");
        FlagsFactory.unreleasedFlag$default(6, flagsFactory, "color_fidelity");
        FlagsFactory.unreleasedFlag$default(6, flagsFactory, "dream_media_complication");
        FlagsFactory.unreleasedFlag$default(6, flagsFactory, "dream_media_tap_to_open");
        FlagsFactory.unreleasedFlag$default(6, flagsFactory, "media_retain_sessions");
        FlagsFactory.releasedFlag$default(flagsFactory, "simulate_dock_through_charging");
        ALWAYS_SHOW_HOME_CONTROLS_ON_DREAMS = FlagsFactory.releasedFlag$default(flagsFactory, "always_show_home_controls_on_dreams");
        WM_ENABLE_SHELL_TRANSITIONS = FlagsFactory.sysPropBooleanFlag$default(flagsFactory, "persist.wm.debug.shell_transit", true);
        LOCKSCREEN_ENABLE_LANDSCAPE = FlagsFactory.unreleasedFlag$default(2, flagsFactory, "lockscreen.enable_landscape");
        WM_ENABLE_PREDICTIVE_BACK_ANIM = FlagsFactory.sysPropBooleanFlag$default(flagsFactory, "persist.wm.debug.predictive_back_anim", true);
        WM_ALWAYS_ENFORCE_PREDICTIVE_BACK = FlagsFactory.sysPropBooleanFlag$default(flagsFactory, "persist.wm.debug.predictive_back_always_enforce", false);
        SCREENSHOT_APP_CLIPS = FlagsFactory.releasedFlag$default(flagsFactory, "screenshot_app_clips");
        FlagsFactory.releasedFlag$default(flagsFactory, "quick_tap_in_pcc");
        FlagsFactory.unreleasedFlag$default(2, flagsFactory, "quick_tap_flow_framework");
        FlagsFactory.releasedFlag$default(flagsFactory, "clipboard_remote_behavior");
        FlagsFactory.unreleasedFlag$default(2, flagsFactory, "clipboard_image_timeout");
        NOTE_TASKS = FlagsFactory.releasedFlag$default(flagsFactory, "keycode_flag");
        TRACK_STYLUS_EVER_USED = FlagsFactory.releasedFlag$default(flagsFactory, "track_stylus_ever_used");
        FlagsFactory.releasedFlag$default(flagsFactory, "enable_stylus_charging_ui");
        ENABLE_USI_BATTERY_NOTIFICATIONS = FlagsFactory.releasedFlag$default(flagsFactory, "enable_usi_battery_notifications");
        FlagsFactory.releasedFlag$default(flagsFactory, "enable_stylus_education");
        FlagsFactory.unreleasedFlag$default(6, flagsFactory, "warn_on_blocking_binder_transactions");
        FlagsFactory.unreleasedFlag$default(6, flagsFactory, "user_tracker_background_callbacks");
        ENABLE_DARK_VIGNETTE_WHEN_FOLDING = FlagsFactory.unreleasedFlag$default(6, flagsFactory, "enable_dark_vignette_when_folding");
        ENABLE_UNFOLD_STATUS_BAR_ANIMATIONS = FlagsFactory.unreleasedFlag$default(6, flagsFactory, "enable_unfold_status_bar_animations");
        ENABLE_NOTIFICATIONS_SIMULATE_SLOW_MEASURE = FlagsFactory.unreleasedFlag$default(6, flagsFactory, "enable_notifications_simulate_slow_measure");
        FlagsFactory.unreleasedFlag$default(6, flagsFactory, "exp_flag_release");
        SHORTCUT_LIST_SEARCH_LAYOUT = FlagsFactory.releasedFlag$default(flagsFactory, "shortcut_list_search_layout");
        KEYBOARD_BACKLIGHT_INDICATOR = FlagsFactory.releasedFlag$default(flagsFactory, "keyboard_backlight_indicator");
        FlagsFactory.unreleasedFlag$default(6, flagsFactory, "split_shade_subpixel_optimization");
        FlagsFactory.releasedFlag$default(flagsFactory, "use_new_activity_starter");
        FlagsFactory.unreleasedFlag$default(6, flagsFactory, "bigpicture_notification_lazy_loading");
        FlagsFactory.releasedFlag$default(flagsFactory, "enable_new_privacy_dialog");
        FlagsFactory.unreleasedFlag$default(6, flagsFactory, "decouple_remote_input_delegate_and_callback_update");
        FlagsFactory.releasedFlag$default(flagsFactory, "enable_clock_keyguard_presentation");
        SHARE_WIFI_QS_BUTTON = FlagsFactory.releasedFlag$default(flagsFactory, "share_wifi_qs_button");
        FlagsFactory.releasedFlag$default(flagsFactory, "bluetooth_qs_tile_dialog");
        COMMUNAL_SERVICE_ENABLED = FlagsFactory.resourceBooleanFlag$default(R.bool.config_communalServiceEnabled, flagsFactory, "communal_service_enabled");
    }

    private Flags() {
    }
}
