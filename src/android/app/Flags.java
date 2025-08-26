package android.app;

/* loaded from: classes.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ACCURATE_WALLPAPER_DOWNSAMPLING = "android.app.accurate_wallpaper_downsampling";
    public static final String FLAG_API_RICH_ONGOING = "android.app.api_rich_ongoing";
    public static final String FLAG_API_RICH_ONGOING_PERMISSION = "android.app.api_rich_ongoing_permission";
    public static final String FLAG_API_TVEXTENDER = "android.app.api_tvextender";
    public static final String FLAG_APP_RESTRICTIONS_API = "android.app.app_restrictions_api";
    public static final String FLAG_APP_START_INFO = "android.app.app_start_info";
    public static final String FLAG_APP_START_INFO_CLEANUP_OLD_RECORDS = "android.app.app_start_info_cleanup_old_records";
    public static final String FLAG_APP_START_INFO_COMPONENT = "android.app.app_start_info_component";
    public static final String FLAG_APP_START_INFO_KEEP_RECORDS_SORTED = "android.app.app_start_info_keep_records_sorted";
    public static final String FLAG_APP_START_INFO_TIMESTAMPS = "android.app.app_start_info_timestamps";
    public static final String FLAG_BACKGROUND_INSTALL_CONTROL_CALLBACK_API = "android.app.background_install_control_callback_api";
    public static final String FLAG_BACKUP_RESTORE_LOGGING = "android.app.backup_restore_logging";
    public static final String FLAG_BIC_CLIENT = "android.app.bic_client";
    public static final String FLAG_CACHE_GET_CURRENT_USER_ID = "android.app.cache_get_current_user_id";
    public static final String FLAG_CATEGORY_VOICEMAIL = "android.app.category_voicemail";
    public static final String FLAG_CHECK_AUTOGROUP_BEFORE_POST = "android.app.check_autogroup_before_post";
    public static final String FLAG_CLEAN_UP_SPANS_AND_NEW_LINES = "android.app.clean_up_spans_and_new_lines";
    public static final String FLAG_CLEAR_DNS_CACHE_ON_NETWORK_RULES_UPDATE = "android.app.clear_dns_cache_on_network_rules_update";
    public static final String FLAG_COMPACT_HEADS_UP_NOTIFICATION = "android.app.compact_heads_up_notification";
    public static final String FLAG_COMPACT_HEADS_UP_NOTIFICATION_REPLY = "android.app.compact_heads_up_notification_reply";
    public static final String FLAG_DEVICE_UNLOCK_LISTENER = "android.app.device_unlock_listener";
    public static final String FLAG_ENABLE_CONNECTED_DISPLAYS_WALLPAPER = "android.app.enable_connected_displays_wallpaper";
    public static final String FLAG_ENABLE_CURRENT_MODE_TYPE_BINDER_CACHE = "android.app.enable_current_mode_type_binder_cache";
    public static final String FLAG_ENABLE_FGS_TIMEOUT_CRASH_BEHAVIOR = "android.app.enable_fgs_timeout_crash_behavior";
    public static final String FLAG_ENABLE_NIGHT_MODE_BINDER_CACHE = "android.app.enable_night_mode_binder_cache";
    public static final String FLAG_ENABLE_PIP_UI_STATE_CALLBACK_ON_ENTERING = "android.app.enable_pip_ui_state_callback_on_entering";
    public static final String FLAG_ENABLE_PROCESS_OBSERVER_BROADCAST_ON_PROCESS_STARTED = "android.app.enable_process_observer_broadcast_on_process_started";
    public static final String FLAG_ENABLE_TV_IMPLICIT_ENTER_PIP_RESTRICTION = "android.app.enable_tv_implicit_enter_pip_restriction";
    public static final String FLAG_ENFORCE_PIC_TESTMODE_PROTOCOL = "android.app.enforce_pic_testmode_protocol";
    public static final String FLAG_EVENLY_DIVIDED_CALL_STYLE_ACTION_LAYOUT = "android.app.evenly_divided_call_style_action_layout";
    public static final String FLAG_EXPANDING_PUBLIC_VIEW = "android.app.expanding_public_view";
    public static final String FLAG_FIX_WALLPAPER_CHANGED = "android.app.fix_wallpaper_changed";
    public static final String FLAG_GET_BINDING_UID_IMPORTANCE = "android.app.get_binding_uid_importance";
    public static final String FLAG_INTRODUCE_NEW_SERVICE_ONTIMEOUT_CALLBACK = "android.app.introduce_new_service_ontimeout_callback";
    public static final String FLAG_JANK_PERCEPTIBLE_NARROW = "android.app.jank_perceptible_narrow";
    public static final String FLAG_JANK_PERCEPTIBLE_NARROW_HOLDBACK = "android.app.jank_perceptible_narrow_holdback";
    public static final String FLAG_KEYGUARD_PRIVATE_NOTIFICATIONS = "android.app.keyguard_private_notifications";
    public static final String FLAG_LIFETIME_EXTENSION_REFACTOR = "android.app.lifetime_extension_refactor";
    public static final String FLAG_LIVE_WALLPAPER_CONTENT_HANDLING = "android.app.live_wallpaper_content_handling";
    public static final String FLAG_MODES_API = "android.app.modes_api";
    public static final String FLAG_MODES_CLEANUP_IMPLICIT = "android.app.modes_cleanup_implicit";
    public static final String FLAG_MODES_HSUM = "android.app.modes_hsum";
    public static final String FLAG_MODES_MULTIUSER = "android.app.modes_multiuser";
    public static final String FLAG_MODES_UI = "android.app.modes_ui";
    public static final String FLAG_MODES_UI_DND_TILE = "android.app.modes_ui_dnd_tile";
    public static final String FLAG_MODES_UI_EMPTY_SHADE = "android.app.modes_ui_empty_shade";
    public static final String FLAG_MODES_UI_ICONS = "android.app.modes_ui_icons";
    public static final String FLAG_NM_BINDER_PERF_CACHE_CHANNELS = "android.app.nm_binder_perf_cache_channels";
    public static final String FLAG_NM_BINDER_PERF_GET_APPS_WITH_CHANNELS = "android.app.nm_binder_perf_get_apps_with_channels";
    public static final String FLAG_NM_BINDER_PERF_LOG_NM_THROTTLING = "android.app.nm_binder_perf_log_nm_throttling";
    public static final String FLAG_NM_BINDER_PERF_PERMISSION_CHECK = "android.app.nm_binder_perf_permission_check";
    public static final String FLAG_NM_BINDER_PERF_THROTTLE_NOTIFY = "android.app.nm_binder_perf_throttle_notify";
    public static final String FLAG_NM_COLLAPSED_LINES = "android.app.nm_collapsed_lines";
    public static final String FLAG_NM_SUMMARIZATION = "android.app.nm_summarization";
    public static final String FLAG_NM_SUMMARIZATION_UI = "android.app.nm_summarization_ui";
    public static final String FLAG_NOTIFICATIONS_REDESIGN_APP_ICONS = "android.app.notifications_redesign_app_icons";
    public static final String FLAG_NOTIFICATIONS_REDESIGN_TEMPLATES = "android.app.notifications_redesign_templates";
    public static final String FLAG_NOTIFICATIONS_REDESIGN_THEMED_APP_ICONS = "android.app.notifications_redesign_themed_app_icons";
    public static final String FLAG_NOTIFICATION_CHANNEL_VIBRATION_EFFECT_API = "android.app.notification_channel_vibration_effect_api";
    public static final String FLAG_NOTIFICATION_CLASSIFICATION_UI = "android.app.notification_classification_ui";
    public static final String FLAG_NOTIFICATION_EXPANSION_OPTIONAL = "android.app.notification_expansion_optional";
    public static final String FLAG_NOTIFICATION_NO_CUSTOM_VIEW_CONVERSATIONS = "android.app.notification_no_custom_view_conversations";
    public static final String FLAG_NOTIFY_KEYGUARD_EVENTS = "android.app.notify_keyguard_events";
    public static final String FLAG_NOTIF_CHANNEL_CROP_VIBRATION_EFFECTS = "android.app.notif_channel_crop_vibration_effects";
    public static final String FLAG_NOTIF_CHANNEL_ESTIMATE_EFFECT_SIZE = "android.app.notif_channel_estimate_effect_size";
    public static final String FLAG_NOTIF_ENTRY_CREATION_TIME_USE_ELAPSED_REALTIME = "android.app.notif_entry_creation_time_use_elapsed_realtime";
    public static final String FLAG_NO_SBNHOLDER = "android.app.no_sbnholder";
    public static final String FLAG_PIC_CACHE_NULLS = "android.app.pic_cache_nulls";
    public static final String FLAG_PIC_ISOLATED_CACHE_STATISTICS = "android.app.pic_isolated_cache_statistics";
    public static final String FLAG_PIC_ISOLATE_CACHE_BY_UID = "android.app.pic_isolate_cache_by_uid";
    public static final String FLAG_PIC_SEPARATE_PERMISSION_NOTIFICATIONS = "android.app.pic_separate_permission_notifications";
    public static final String FLAG_PIC_TEST_MODE = "android.app.pic_test_mode";
    public static final String FLAG_PIC_USES_SHARED_MEMORY = "android.app.pic_uses_shared_memory";
    public static final String FLAG_PINNER_SERVICE_CLIENT_API = "android.app.pinner_service_client_api";
    public static final String FLAG_RATE_LIMIT_GET_MEMORY_INFO = "android.app.rate_limit_get_memory_info";
    public static final String FLAG_RATE_LIMIT_GET_MY_MEMORY_STATE = "android.app.rate_limit_get_my_memory_state";
    public static final String FLAG_RATE_LIMIT_GET_PROCESSES_IN_ERROR_STATE = "android.app.rate_limit_get_processes_in_error_state";
    public static final String FLAG_RATE_LIMIT_GET_RUNNING_APP_PROCESSES = "android.app.rate_limit_get_running_app_processes";
    public static final String FLAG_REDACTION_ON_LOCKSCREEN_METRICS = "android.app.redaction_on_lockscreen_metrics";
    public static final String FLAG_REDACT_SENSITIVE_CONTENT_NOTIFICATIONS_ON_LOCKSCREEN = "android.app.redact_sensitive_content_notifications_on_lockscreen";
    public static final String FLAG_REMOVE_NEXT_WALLPAPER_COMPONENT = "android.app.remove_next_wallpaper_component";
    public static final String FLAG_REMOVE_REMOTE_VIEWS = "android.app.remove_remote_views";
    public static final String FLAG_REPORT_POSTGC_MEMORY_METRICS = "android.app.report_postgc_memory_metrics";
    public static final String FLAG_RESTRICT_AUDIO_ATTRIBUTES_ALARM = "android.app.restrict_audio_attributes_alarm";
    public static final String FLAG_RESTRICT_AUDIO_ATTRIBUTES_CALL = "android.app.restrict_audio_attributes_call";
    public static final String FLAG_RESTRICT_AUDIO_ATTRIBUTES_MEDIA = "android.app.restrict_audio_attributes_media";
    public static final String FLAG_SECURE_ALLOWLIST_TOKEN = "android.app.secure_allowlist_token";
    public static final String FLAG_SKIP_BG_MEM_TRIM_ON_FG_APP = "android.app.skip_bg_mem_trim_on_fg_app";
    public static final String FLAG_SORT_SECTION_BY_TIME = "android.app.sort_section_by_time";
    public static final String FLAG_SYSTEM_TERMS_OF_ADDRESS_ENABLED = "android.app.system_terms_of_address_enabled";
    public static final String FLAG_UID_IMPORTANCE_LISTENER_FOR_UIDS = "android.app.uid_importance_listener_for_uids";
    public static final String FLAG_UI_RICH_ONGOING = "android.app.ui_rich_ongoing";
    public static final String FLAG_USE_APP_INFO_NOT_LAUNCHED = "android.app.use_app_info_not_launched";
    public static final String FLAG_USE_STICKY_BCAST_CACHE = "android.app.use_sticky_bcast_cache";

    public static boolean accurateWallpaperDownsampling() {
        return FEATURE_FLAGS.accurateWallpaperDownsampling();
    }

    public static boolean apiRichOngoing() {
        return FEATURE_FLAGS.apiRichOngoing();
    }

    public static boolean apiRichOngoingPermission() {
        return FEATURE_FLAGS.apiRichOngoingPermission();
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

    public static boolean appStartInfoCleanupOldRecords() {
        return FEATURE_FLAGS.appStartInfoCleanupOldRecords();
    }

    public static boolean appStartInfoComponent() {
        return FEATURE_FLAGS.appStartInfoComponent();
    }

    public static boolean appStartInfoKeepRecordsSorted() {
        return FEATURE_FLAGS.appStartInfoKeepRecordsSorted();
    }

    public static boolean appStartInfoTimestamps() {
        return FEATURE_FLAGS.appStartInfoTimestamps();
    }

    public static boolean backgroundInstallControlCallbackApi() {
        return FEATURE_FLAGS.backgroundInstallControlCallbackApi();
    }

    public static boolean backupRestoreLogging() {
        return FEATURE_FLAGS.backupRestoreLogging();
    }

    public static boolean bicClient() {
        return FEATURE_FLAGS.bicClient();
    }

    public static boolean cacheGetCurrentUserId() {
        return FEATURE_FLAGS.cacheGetCurrentUserId();
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

    public static boolean deviceUnlockListener() {
        return FEATURE_FLAGS.deviceUnlockListener();
    }

    public static boolean enableConnectedDisplaysWallpaper() {
        return FEATURE_FLAGS.enableConnectedDisplaysWallpaper();
    }

    public static boolean enableCurrentModeTypeBinderCache() {
        return FEATURE_FLAGS.enableCurrentModeTypeBinderCache();
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

    public static boolean enableProcessObserverBroadcastOnProcessStarted() {
        return FEATURE_FLAGS.enableProcessObserverBroadcastOnProcessStarted();
    }

    public static boolean enableTvImplicitEnterPipRestriction() {
        return FEATURE_FLAGS.enableTvImplicitEnterPipRestriction();
    }

    public static boolean enforcePicTestmodeProtocol() {
        return FEATURE_FLAGS.enforcePicTestmodeProtocol();
    }

    public static boolean evenlyDividedCallStyleActionLayout() {
        return FEATURE_FLAGS.evenlyDividedCallStyleActionLayout();
    }

    public static boolean expandingPublicView() {
        return FEATURE_FLAGS.expandingPublicView();
    }

    public static boolean fixWallpaperChanged() {
        return FEATURE_FLAGS.fixWallpaperChanged();
    }

    public static boolean getBindingUidImportance() {
        return FEATURE_FLAGS.getBindingUidImportance();
    }

    public static boolean introduceNewServiceOntimeoutCallback() {
        return FEATURE_FLAGS.introduceNewServiceOntimeoutCallback();
    }

    public static boolean jankPerceptibleNarrow() {
        return FEATURE_FLAGS.jankPerceptibleNarrow();
    }

    public static boolean jankPerceptibleNarrowHoldback() {
        return FEATURE_FLAGS.jankPerceptibleNarrowHoldback();
    }

    public static boolean keyguardPrivateNotifications() {
        return FEATURE_FLAGS.keyguardPrivateNotifications();
    }

    public static boolean lifetimeExtensionRefactor() {
        return FEATURE_FLAGS.lifetimeExtensionRefactor();
    }

    public static boolean liveWallpaperContentHandling() {
        return FEATURE_FLAGS.liveWallpaperContentHandling();
    }

    public static boolean modesApi() {
        return FEATURE_FLAGS.modesApi();
    }

    public static boolean modesCleanupImplicit() {
        return FEATURE_FLAGS.modesCleanupImplicit();
    }

    public static boolean modesHsum() {
        return FEATURE_FLAGS.modesHsum();
    }

    public static boolean modesMultiuser() {
        return FEATURE_FLAGS.modesMultiuser();
    }

    public static boolean modesUi() {
        return FEATURE_FLAGS.modesUi();
    }

    public static boolean modesUiDndTile() {
        return FEATURE_FLAGS.modesUiDndTile();
    }

    public static boolean modesUiEmptyShade() {
        return FEATURE_FLAGS.modesUiEmptyShade();
    }

    public static boolean modesUiIcons() {
        return FEATURE_FLAGS.modesUiIcons();
    }

    public static boolean nmBinderPerfCacheChannels() {
        return FEATURE_FLAGS.nmBinderPerfCacheChannels();
    }

    public static boolean nmBinderPerfGetAppsWithChannels() {
        return FEATURE_FLAGS.nmBinderPerfGetAppsWithChannels();
    }

    public static boolean nmBinderPerfLogNmThrottling() {
        return FEATURE_FLAGS.nmBinderPerfLogNmThrottling();
    }

    public static boolean nmBinderPerfPermissionCheck() {
        return FEATURE_FLAGS.nmBinderPerfPermissionCheck();
    }

    public static boolean nmBinderPerfThrottleNotify() {
        return FEATURE_FLAGS.nmBinderPerfThrottleNotify();
    }

    public static boolean nmCollapsedLines() {
        return FEATURE_FLAGS.nmCollapsedLines();
    }

    public static boolean nmSummarization() {
        return FEATURE_FLAGS.nmSummarization();
    }

    public static boolean nmSummarizationUi() {
        return FEATURE_FLAGS.nmSummarizationUi();
    }

    public static boolean noSbnholder() {
        return FEATURE_FLAGS.noSbnholder();
    }

    public static boolean notifChannelCropVibrationEffects() {
        return FEATURE_FLAGS.notifChannelCropVibrationEffects();
    }

    public static boolean notifChannelEstimateEffectSize() {
        return FEATURE_FLAGS.notifChannelEstimateEffectSize();
    }

    public static boolean notifEntryCreationTimeUseElapsedRealtime() {
        return FEATURE_FLAGS.notifEntryCreationTimeUseElapsedRealtime();
    }

    public static boolean notificationChannelVibrationEffectApi() {
        return FEATURE_FLAGS.notificationChannelVibrationEffectApi();
    }

    public static boolean notificationClassificationUi() {
        return FEATURE_FLAGS.notificationClassificationUi();
    }

    public static boolean notificationExpansionOptional() {
        return FEATURE_FLAGS.notificationExpansionOptional();
    }

    public static boolean notificationNoCustomViewConversations() {
        return FEATURE_FLAGS.notificationNoCustomViewConversations();
    }

    public static boolean notificationsRedesignAppIcons() {
        return FEATURE_FLAGS.notificationsRedesignAppIcons();
    }

    public static boolean notificationsRedesignTemplates() {
        return FEATURE_FLAGS.notificationsRedesignTemplates();
    }

    public static boolean notificationsRedesignThemedAppIcons() {
        return FEATURE_FLAGS.notificationsRedesignThemedAppIcons();
    }

    public static boolean notifyKeyguardEvents() {
        return FEATURE_FLAGS.notifyKeyguardEvents();
    }

    public static boolean picCacheNulls() {
        return FEATURE_FLAGS.picCacheNulls();
    }

    public static boolean picIsolateCacheByUid() {
        return FEATURE_FLAGS.picIsolateCacheByUid();
    }

    public static boolean picIsolatedCacheStatistics() {
        return FEATURE_FLAGS.picIsolatedCacheStatistics();
    }

    public static boolean picSeparatePermissionNotifications() {
        return FEATURE_FLAGS.picSeparatePermissionNotifications();
    }

    public static boolean picTestMode() {
        return FEATURE_FLAGS.picTestMode();
    }

    public static boolean picUsesSharedMemory() {
        return FEATURE_FLAGS.picUsesSharedMemory();
    }

    public static boolean pinnerServiceClientApi() {
        return FEATURE_FLAGS.pinnerServiceClientApi();
    }

    public static boolean rateLimitGetMemoryInfo() {
        return FEATURE_FLAGS.rateLimitGetMemoryInfo();
    }

    public static boolean rateLimitGetMyMemoryState() {
        return FEATURE_FLAGS.rateLimitGetMyMemoryState();
    }

    public static boolean rateLimitGetProcessesInErrorState() {
        return FEATURE_FLAGS.rateLimitGetProcessesInErrorState();
    }

    public static boolean rateLimitGetRunningAppProcesses() {
        return FEATURE_FLAGS.rateLimitGetRunningAppProcesses();
    }

    public static boolean redactSensitiveContentNotificationsOnLockscreen() {
        return FEATURE_FLAGS.redactSensitiveContentNotificationsOnLockscreen();
    }

    public static boolean redactionOnLockscreenMetrics() {
        return FEATURE_FLAGS.redactionOnLockscreenMetrics();
    }

    public static boolean removeNextWallpaperComponent() {
        return FEATURE_FLAGS.removeNextWallpaperComponent();
    }

    public static boolean removeRemoteViews() {
        return FEATURE_FLAGS.removeRemoteViews();
    }

    public static boolean reportPostgcMemoryMetrics() {
        return FEATURE_FLAGS.reportPostgcMemoryMetrics();
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

    public static boolean uiRichOngoing() {
        return FEATURE_FLAGS.uiRichOngoing();
    }

    public static boolean uidImportanceListenerForUids() {
        return FEATURE_FLAGS.uidImportanceListenerForUids();
    }

    public static boolean useAppInfoNotLaunched() {
        return FEATURE_FLAGS.useAppInfoNotLaunched();
    }

    public static boolean useStickyBcastCache() {
        return FEATURE_FLAGS.useStickyBcastCache();
    }
}
