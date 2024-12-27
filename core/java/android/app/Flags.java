package android.app;

/* loaded from: classes.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_API_RICH_ONGOING = "android.app.api_rich_ongoing";
    public static final String FLAG_API_TVEXTENDER = "android.app.api_tvextender";
    public static final String FLAG_APP_RESTRICTIONS_API = "android.app.app_restrictions_api";
    public static final String FLAG_APP_START_INFO = "android.app.app_start_info";
    public static final String FLAG_APP_START_INFO_TIMESTAMPS = "android.app.app_start_info_timestamps";
    public static final String FLAG_BIC_CLIENT = "android.app.bic_client";
    public static final String FLAG_CATEGORY_VOICEMAIL = "android.app.category_voicemail";
    public static final String FLAG_CHECK_AUTOGROUP_BEFORE_POST = "android.app.check_autogroup_before_post";
    public static final String FLAG_CLEAN_UP_SPANS_AND_NEW_LINES = "android.app.clean_up_spans_and_new_lines";
    public static final String FLAG_CLEAR_DNS_CACHE_ON_NETWORK_RULES_UPDATE = "android.app.clear_dns_cache_on_network_rules_update";
    public static final String FLAG_COMPACT_HEADS_UP_NOTIFICATION = "android.app.compact_heads_up_notification";
    public static final String FLAG_COMPACT_HEADS_UP_NOTIFICATION_REPLY = "android.app.compact_heads_up_notification_reply";
    public static final String FLAG_ENABLE_FGS_TIMEOUT_CRASH_BEHAVIOR = "android.app.enable_fgs_timeout_crash_behavior";
    public static final String FLAG_ENABLE_NIGHT_MODE_BINDER_CACHE = "android.app.enable_night_mode_binder_cache";
    public static final String FLAG_ENABLE_PIP_UI_STATE_CALLBACK_ON_ENTERING = "android.app.enable_pip_ui_state_callback_on_entering";
    public static final String FLAG_EVENLY_DIVIDED_CALL_STYLE_ACTION_LAYOUT = "android.app.evenly_divided_call_style_action_layout";
    public static final String FLAG_GATE_FGS_TIMEOUT_ANR_BEHAVIOR = "android.app.gate_fgs_timeout_anr_behavior";
    public static final String FLAG_GET_BINDING_UID_IMPORTANCE = "android.app.get_binding_uid_importance";
    public static final String FLAG_INTRODUCE_NEW_SERVICE_ONTIMEOUT_CALLBACK = "android.app.introduce_new_service_ontimeout_callback";
    public static final String FLAG_KEYGUARD_PRIVATE_NOTIFICATIONS = "android.app.keyguard_private_notifications";
    public static final String FLAG_LIFETIME_EXTENSION_REFACTOR = "android.app.lifetime_extension_refactor";
    public static final String FLAG_MODES_API = "android.app.modes_api";
    public static final String FLAG_MODES_UI = "android.app.modes_ui";
    public static final String FLAG_NOTIFICATIONS_USE_APP_ICON = "android.app.notifications_use_app_icon";
    public static final String FLAG_NOTIFICATIONS_USE_APP_ICON_IN_ROW = "android.app.notifications_use_app_icon_in_row";
    public static final String FLAG_NOTIFICATIONS_USE_MONOCHROME_APP_ICON = "android.app.notifications_use_monochrome_app_icon";
    public static final String FLAG_NOTIFICATION_CHANNEL_VIBRATION_EFFECT_API = "android.app.notification_channel_vibration_effect_api";
    public static final String FLAG_NOTIFICATION_EXPANSION_OPTIONAL = "android.app.notification_expansion_optional";
    public static final String FLAG_PINNER_SERVICE_CLIENT_API = "android.app.pinner_service_client_api";
    public static final String FLAG_REDACT_SENSITIVE_CONTENT_NOTIFICATIONS_ON_LOCKSCREEN = "android.app.redact_sensitive_content_notifications_on_lockscreen";
    public static final String FLAG_REMOVE_REMOTE_VIEWS = "android.app.remove_remote_views";
    public static final String FLAG_RESTRICT_AUDIO_ATTRIBUTES_ALARM = "android.app.restrict_audio_attributes_alarm";
    public static final String FLAG_RESTRICT_AUDIO_ATTRIBUTES_CALL = "android.app.restrict_audio_attributes_call";
    public static final String FLAG_RESTRICT_AUDIO_ATTRIBUTES_MEDIA = "android.app.restrict_audio_attributes_media";
    public static final String FLAG_SECURE_ALLOWLIST_TOKEN = "android.app.secure_allowlist_token";
    public static final String FLAG_SKIP_BG_MEM_TRIM_ON_FG_APP = "android.app.skip_bg_mem_trim_on_fg_app";
    public static final String FLAG_SORT_SECTION_BY_TIME = "android.app.sort_section_by_time";
    public static final String FLAG_SYSTEM_TERMS_OF_ADDRESS_ENABLED = "android.app.system_terms_of_address_enabled";
    public static final String FLAG_UID_IMPORTANCE_LISTENER_FOR_UIDS = "android.app.uid_importance_listener_for_uids";
    public static final String FLAG_UPDATE_RANKING_TIME = "android.app.update_ranking_time";
    public static final String FLAG_USE_APP_INFO_NOT_LAUNCHED = "android.app.use_app_info_not_launched";
    public static final String FLAG_VISIT_PERSON_URI = "android.app.visit_person_uri";

    public static boolean apiRichOngoing() {
        return FEATURE_FLAGS.apiRichOngoing();
    }

    public static boolean apiTvextender() {
        return FEATURE_FLAGS.apiTvextender();
    }

    public static boolean appRestrictionsApi() {
        return FEATURE_FLAGS.appRestrictionsApi();
    }

    public static boolean appStartInfo() {
        return FEATURE_FLAGS.appStartInfo();
    }

    public static boolean appStartInfoTimestamps() {
        return FEATURE_FLAGS.appStartInfoTimestamps();
    }

    public static boolean bicClient() {
        return FEATURE_FLAGS.bicClient();
    }

    public static boolean categoryVoicemail() {
        return FEATURE_FLAGS.categoryVoicemail();
    }

    public static boolean checkAutogroupBeforePost() {
        return FEATURE_FLAGS.checkAutogroupBeforePost();
    }

    public static boolean cleanUpSpansAndNewLines() {
        return FEATURE_FLAGS.cleanUpSpansAndNewLines();
    }

    public static boolean clearDnsCacheOnNetworkRulesUpdate() {
        return FEATURE_FLAGS.clearDnsCacheOnNetworkRulesUpdate();
    }

    public static boolean compactHeadsUpNotification() {
        return FEATURE_FLAGS.compactHeadsUpNotification();
    }

    public static boolean compactHeadsUpNotificationReply() {
        return FEATURE_FLAGS.compactHeadsUpNotificationReply();
    }

    public static boolean enableFgsTimeoutCrashBehavior() {
        return FEATURE_FLAGS.enableFgsTimeoutCrashBehavior();
    }

    public static boolean enableNightModeBinderCache() {
        return FEATURE_FLAGS.enableNightModeBinderCache();
    }

    public static boolean enablePipUiStateCallbackOnEntering() {
        return FEATURE_FLAGS.enablePipUiStateCallbackOnEntering();
    }

    public static boolean evenlyDividedCallStyleActionLayout() {
        return FEATURE_FLAGS.evenlyDividedCallStyleActionLayout();
    }

    public static boolean gateFgsTimeoutAnrBehavior() {
        return FEATURE_FLAGS.gateFgsTimeoutAnrBehavior();
    }

    public static boolean getBindingUidImportance() {
        return FEATURE_FLAGS.getBindingUidImportance();
    }

    public static boolean introduceNewServiceOntimeoutCallback() {
        return FEATURE_FLAGS.introduceNewServiceOntimeoutCallback();
    }

    public static boolean keyguardPrivateNotifications() {
        return FEATURE_FLAGS.keyguardPrivateNotifications();
    }

    public static boolean lifetimeExtensionRefactor() {
        return FEATURE_FLAGS.lifetimeExtensionRefactor();
    }

    public static boolean modesApi() {
        return FEATURE_FLAGS.modesApi();
    }

    public static boolean modesUi() {
        return FEATURE_FLAGS.modesUi();
    }

    public static boolean notificationChannelVibrationEffectApi() {
        return FEATURE_FLAGS.notificationChannelVibrationEffectApi();
    }

    public static boolean notificationExpansionOptional() {
        return FEATURE_FLAGS.notificationExpansionOptional();
    }

    public static boolean notificationsUseAppIcon() {
        return FEATURE_FLAGS.notificationsUseAppIcon();
    }

    public static boolean notificationsUseAppIconInRow() {
        return FEATURE_FLAGS.notificationsUseAppIconInRow();
    }

    public static boolean notificationsUseMonochromeAppIcon() {
        return FEATURE_FLAGS.notificationsUseMonochromeAppIcon();
    }

    public static boolean pinnerServiceClientApi() {
        return FEATURE_FLAGS.pinnerServiceClientApi();
    }

    public static boolean redactSensitiveContentNotificationsOnLockscreen() {
        return FEATURE_FLAGS.redactSensitiveContentNotificationsOnLockscreen();
    }

    public static boolean removeRemoteViews() {
        return FEATURE_FLAGS.removeRemoteViews();
    }

    public static boolean restrictAudioAttributesAlarm() {
        return FEATURE_FLAGS.restrictAudioAttributesAlarm();
    }

    public static boolean restrictAudioAttributesCall() {
        return FEATURE_FLAGS.restrictAudioAttributesCall();
    }

    public static boolean restrictAudioAttributesMedia() {
        return FEATURE_FLAGS.restrictAudioAttributesMedia();
    }

    public static boolean secureAllowlistToken() {
        return FEATURE_FLAGS.secureAllowlistToken();
    }

    public static boolean skipBgMemTrimOnFgApp() {
        return FEATURE_FLAGS.skipBgMemTrimOnFgApp();
    }

    public static boolean sortSectionByTime() {
        return FEATURE_FLAGS.sortSectionByTime();
    }

    public static boolean systemTermsOfAddressEnabled() {
        return FEATURE_FLAGS.systemTermsOfAddressEnabled();
    }

    public static boolean uidImportanceListenerForUids() {
        return FEATURE_FLAGS.uidImportanceListenerForUids();
    }

    public static boolean updateRankingTime() {
        return FEATURE_FLAGS.updateRankingTime();
    }

    public static boolean useAppInfoNotLaunched() {
        return FEATURE_FLAGS.useAppInfoNotLaunched();
    }

    public static boolean visitPersonUri() {
        return FEATURE_FLAGS.visitPersonUri();
    }
}
