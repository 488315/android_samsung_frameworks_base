package android.app;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ACCURATE_WALLPAPER_DOWNSAMPLING, Flags.FLAG_API_RICH_ONGOING, Flags.FLAG_API_TVEXTENDER, Flags.FLAG_APP_RESTRICTIONS_API, Flags.FLAG_APP_START_INFO, Flags.FLAG_APP_START_INFO_CLEANUP_OLD_RECORDS, Flags.FLAG_APP_START_INFO_COMPONENT, Flags.FLAG_APP_START_INFO_KEEP_RECORDS_SORTED, Flags.FLAG_APP_START_INFO_TIMESTAMPS, Flags.FLAG_BACKGROUND_INSTALL_CONTROL_CALLBACK_API, Flags.FLAG_BACKUP_RESTORE_LOGGING, Flags.FLAG_BIC_CLIENT, Flags.FLAG_CACHE_GET_CURRENT_USER_ID, Flags.FLAG_CATEGORY_VOICEMAIL, Flags.FLAG_CHECK_AUTOGROUP_BEFORE_POST, Flags.FLAG_CLEAN_UP_SPANS_AND_NEW_LINES, Flags.FLAG_CLEAR_DNS_CACHE_ON_NETWORK_RULES_UPDATE, Flags.FLAG_COMPACT_HEADS_UP_NOTIFICATION, Flags.FLAG_COMPACT_HEADS_UP_NOTIFICATION_REPLY, Flags.FLAG_DEVICE_UNLOCK_LISTENER, Flags.FLAG_ENABLE_CONNECTED_DISPLAYS_WALLPAPER, Flags.FLAG_ENABLE_CURRENT_MODE_TYPE_BINDER_CACHE, Flags.FLAG_ENABLE_FGS_TIMEOUT_CRASH_BEHAVIOR, Flags.FLAG_ENABLE_NIGHT_MODE_BINDER_CACHE, Flags.FLAG_ENABLE_PIP_UI_STATE_CALLBACK_ON_ENTERING, Flags.FLAG_ENABLE_PROCESS_OBSERVER_BROADCAST_ON_PROCESS_STARTED, Flags.FLAG_ENABLE_TV_IMPLICIT_ENTER_PIP_RESTRICTION, Flags.FLAG_ENFORCE_PIC_TESTMODE_PROTOCOL, Flags.FLAG_EVENLY_DIVIDED_CALL_STYLE_ACTION_LAYOUT, Flags.FLAG_EXPANDING_PUBLIC_VIEW, Flags.FLAG_FIX_WALLPAPER_CHANGED, Flags.FLAG_GET_BINDING_UID_IMPORTANCE, Flags.FLAG_INTRODUCE_NEW_SERVICE_ONTIMEOUT_CALLBACK, Flags.FLAG_JANK_PERCEPTIBLE_NARROW, Flags.FLAG_JANK_PERCEPTIBLE_NARROW_HOLDBACK, Flags.FLAG_KEYGUARD_PRIVATE_NOTIFICATIONS, Flags.FLAG_LIFETIME_EXTENSION_REFACTOR, Flags.FLAG_LIVE_WALLPAPER_CONTENT_HANDLING, Flags.FLAG_MODES_API, Flags.FLAG_MODES_CLEANUP_IMPLICIT, Flags.FLAG_MODES_HSUM, Flags.FLAG_MODES_MULTIUSER, Flags.FLAG_MODES_UI, Flags.FLAG_MODES_UI_DND_TILE, Flags.FLAG_MODES_UI_EMPTY_SHADE, Flags.FLAG_MODES_UI_ICONS, Flags.FLAG_NM_BINDER_PERF_CACHE_CHANNELS, Flags.FLAG_NM_BINDER_PERF_GET_APPS_WITH_CHANNELS, Flags.FLAG_NM_BINDER_PERF_LOG_NM_THROTTLING, Flags.FLAG_NM_BINDER_PERF_PERMISSION_CHECK, Flags.FLAG_NM_BINDER_PERF_THROTTLE_NOTIFY, Flags.FLAG_NM_COLLAPSED_LINES, Flags.FLAG_NM_SUMMARIZATION, Flags.FLAG_NM_SUMMARIZATION_UI, Flags.FLAG_NO_SBNHOLDER, Flags.FLAG_NOTIF_CHANNEL_CROP_VIBRATION_EFFECTS, Flags.FLAG_NOTIF_CHANNEL_ESTIMATE_EFFECT_SIZE, Flags.FLAG_NOTIF_ENTRY_CREATION_TIME_USE_ELAPSED_REALTIME, Flags.FLAG_NOTIFICATION_CHANNEL_VIBRATION_EFFECT_API, Flags.FLAG_NOTIFICATION_CLASSIFICATION_UI, Flags.FLAG_NOTIFICATION_EXPANSION_OPTIONAL, Flags.FLAG_NOTIFICATION_NO_CUSTOM_VIEW_CONVERSATIONS, Flags.FLAG_NOTIFICATIONS_REDESIGN_APP_ICONS, Flags.FLAG_NOTIFICATIONS_REDESIGN_TEMPLATES, Flags.FLAG_NOTIFICATIONS_REDESIGN_THEMED_APP_ICONS, Flags.FLAG_NOTIFY_KEYGUARD_EVENTS, Flags.FLAG_PIC_CACHE_NULLS, Flags.FLAG_PIC_ISOLATE_CACHE_BY_UID, Flags.FLAG_PIC_ISOLATED_CACHE_STATISTICS, Flags.FLAG_PIC_SEPARATE_PERMISSION_NOTIFICATIONS, Flags.FLAG_PIC_TEST_MODE, Flags.FLAG_PIC_USES_SHARED_MEMORY, Flags.FLAG_PINNER_SERVICE_CLIENT_API, Flags.FLAG_RATE_LIMIT_GET_MEMORY_INFO, Flags.FLAG_RATE_LIMIT_GET_MY_MEMORY_STATE, Flags.FLAG_RATE_LIMIT_GET_PROCESSES_IN_ERROR_STATE, Flags.FLAG_RATE_LIMIT_GET_RUNNING_APP_PROCESSES, Flags.FLAG_REDACT_SENSITIVE_CONTENT_NOTIFICATIONS_ON_LOCKSCREEN, Flags.FLAG_REDACTION_ON_LOCKSCREEN_METRICS, Flags.FLAG_REMOVE_NEXT_WALLPAPER_COMPONENT, Flags.FLAG_REMOVE_REMOTE_VIEWS, Flags.FLAG_REPORT_POSTGC_MEMORY_METRICS, Flags.FLAG_RESTRICT_AUDIO_ATTRIBUTES_ALARM, Flags.FLAG_RESTRICT_AUDIO_ATTRIBUTES_CALL, Flags.FLAG_RESTRICT_AUDIO_ATTRIBUTES_MEDIA, Flags.FLAG_SECURE_ALLOWLIST_TOKEN, Flags.FLAG_SKIP_BG_MEM_TRIM_ON_FG_APP, Flags.FLAG_SORT_SECTION_BY_TIME, Flags.FLAG_SYSTEM_TERMS_OF_ADDRESS_ENABLED, Flags.FLAG_UI_RICH_ONGOING, Flags.FLAG_UID_IMPORTANCE_LISTENER_FOR_UIDS, Flags.FLAG_USE_APP_INFO_NOT_LAUNCHED, Flags.FLAG_USE_STICKY_BCAST_CACHE, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.app.FeatureFlags
    public boolean accurateWallpaperDownsampling() {
        return getValue(Flags.FLAG_ACCURATE_WALLPAPER_DOWNSAMPLING, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).accurateWallpaperDownsampling();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean apiRichOngoing() {
        return getValue(Flags.FLAG_API_RICH_ONGOING, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).apiRichOngoing();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean apiTvextender() {
        return getValue(Flags.FLAG_API_TVEXTENDER, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda35
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).apiTvextender();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean appRestrictionsApi() {
        return getValue(Flags.FLAG_APP_RESTRICTIONS_API, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda79
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).appRestrictionsApi();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean appStartInfo() {
        return getValue(Flags.FLAG_APP_START_INFO, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda82
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).appStartInfo();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean appStartInfoCleanupOldRecords() {
        return getValue(Flags.FLAG_APP_START_INFO_CLEANUP_OLD_RECORDS, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda89
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).appStartInfoCleanupOldRecords();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean appStartInfoComponent() {
        return getValue(Flags.FLAG_APP_START_INFO_COMPONENT, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).appStartInfoComponent();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean appStartInfoKeepRecordsSorted() {
        return getValue(Flags.FLAG_APP_START_INFO_KEEP_RECORDS_SORTED, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda52
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).appStartInfoKeepRecordsSorted();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean appStartInfoTimestamps() {
        return getValue(Flags.FLAG_APP_START_INFO_TIMESTAMPS, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).appStartInfoTimestamps();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean backgroundInstallControlCallbackApi() {
        return getValue(Flags.FLAG_BACKGROUND_INSTALL_CONTROL_CALLBACK_API, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda55
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).backgroundInstallControlCallbackApi();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean backupRestoreLogging() {
        return getValue(Flags.FLAG_BACKUP_RESTORE_LOGGING, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).backupRestoreLogging();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean bicClient() {
        return getValue(Flags.FLAG_BIC_CLIENT, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda77
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).bicClient();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean cacheGetCurrentUserId() {
        return getValue(Flags.FLAG_CACHE_GET_CURRENT_USER_ID, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda31
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cacheGetCurrentUserId();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean categoryVoicemail() {
        return getValue(Flags.FLAG_CATEGORY_VOICEMAIL, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).categoryVoicemail();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean checkAutogroupBeforePost() {
        return getValue(Flags.FLAG_CHECK_AUTOGROUP_BEFORE_POST, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda42
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).checkAutogroupBeforePost();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean cleanUpSpansAndNewLines() {
        return getValue(Flags.FLAG_CLEAN_UP_SPANS_AND_NEW_LINES, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda58
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cleanUpSpansAndNewLines();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean clearDnsCacheOnNetworkRulesUpdate() {
        return getValue(Flags.FLAG_CLEAR_DNS_CACHE_ON_NETWORK_RULES_UPDATE, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).clearDnsCacheOnNetworkRulesUpdate();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean compactHeadsUpNotification() {
        return getValue(Flags.FLAG_COMPACT_HEADS_UP_NOTIFICATION, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda41
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).compactHeadsUpNotification();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean compactHeadsUpNotificationReply() {
        return getValue(Flags.FLAG_COMPACT_HEADS_UP_NOTIFICATION_REPLY, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda70
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).compactHeadsUpNotificationReply();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean deviceUnlockListener() {
        return getValue(Flags.FLAG_DEVICE_UNLOCK_LISTENER, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deviceUnlockListener();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean enableConnectedDisplaysWallpaper() {
        return getValue(Flags.FLAG_ENABLE_CONNECTED_DISPLAYS_WALLPAPER, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda85
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableConnectedDisplaysWallpaper();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean enableCurrentModeTypeBinderCache() {
        return getValue(Flags.FLAG_ENABLE_CURRENT_MODE_TYPE_BINDER_CACHE, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableCurrentModeTypeBinderCache();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean enableFgsTimeoutCrashBehavior() {
        return getValue(Flags.FLAG_ENABLE_FGS_TIMEOUT_CRASH_BEHAVIOR, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda60
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableFgsTimeoutCrashBehavior();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean enableNightModeBinderCache() {
        return getValue(Flags.FLAG_ENABLE_NIGHT_MODE_BINDER_CACHE, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableNightModeBinderCache();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean enablePipUiStateCallbackOnEntering() {
        return getValue(Flags.FLAG_ENABLE_PIP_UI_STATE_CALLBACK_ON_ENTERING, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda33
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enablePipUiStateCallbackOnEntering();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean enableProcessObserverBroadcastOnProcessStarted() {
        return getValue(Flags.FLAG_ENABLE_PROCESS_OBSERVER_BROADCAST_ON_PROCESS_STARTED, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda45
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableProcessObserverBroadcastOnProcessStarted();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean enableTvImplicitEnterPipRestriction() {
        return getValue(Flags.FLAG_ENABLE_TV_IMPLICIT_ENTER_PIP_RESTRICTION, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda44
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableTvImplicitEnterPipRestriction();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean enforcePicTestmodeProtocol() {
        return getValue(Flags.FLAG_ENFORCE_PIC_TESTMODE_PROTOCOL, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda34
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enforcePicTestmodeProtocol();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean evenlyDividedCallStyleActionLayout() {
        return getValue(Flags.FLAG_EVENLY_DIVIDED_CALL_STYLE_ACTION_LAYOUT, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).evenlyDividedCallStyleActionLayout();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean expandingPublicView() {
        return getValue(Flags.FLAG_EXPANDING_PUBLIC_VIEW, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda51
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).expandingPublicView();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean fixWallpaperChanged() {
        return getValue(Flags.FLAG_FIX_WALLPAPER_CHANGED, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda83
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixWallpaperChanged();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean getBindingUidImportance() {
        return getValue(Flags.FLAG_GET_BINDING_UID_IMPORTANCE, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda43
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).getBindingUidImportance();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean introduceNewServiceOntimeoutCallback() {
        return getValue(Flags.FLAG_INTRODUCE_NEW_SERVICE_ONTIMEOUT_CALLBACK, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda30
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).introduceNewServiceOntimeoutCallback();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean jankPerceptibleNarrow() {
        return getValue(Flags.FLAG_JANK_PERCEPTIBLE_NARROW, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda47
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).jankPerceptibleNarrow();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean jankPerceptibleNarrowHoldback() {
        return getValue(Flags.FLAG_JANK_PERCEPTIBLE_NARROW_HOLDBACK, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda91
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).jankPerceptibleNarrowHoldback();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean keyguardPrivateNotifications() {
        return getValue(Flags.FLAG_KEYGUARD_PRIVATE_NOTIFICATIONS, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda40
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).keyguardPrivateNotifications();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean lifetimeExtensionRefactor() {
        return getValue(Flags.FLAG_LIFETIME_EXTENSION_REFACTOR, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda84
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).lifetimeExtensionRefactor();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean liveWallpaperContentHandling() {
        return getValue(Flags.FLAG_LIVE_WALLPAPER_CONTENT_HANDLING, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda76
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).liveWallpaperContentHandling();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean modesApi() {
        return getValue(Flags.FLAG_MODES_API, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda32
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).modesApi();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean modesCleanupImplicit() {
        return getValue(Flags.FLAG_MODES_CLEANUP_IMPLICIT, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda86
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).modesCleanupImplicit();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean modesHsum() {
        return getValue(Flags.FLAG_MODES_HSUM, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).modesHsum();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean modesMultiuser() {
        return getValue(Flags.FLAG_MODES_MULTIUSER, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).modesMultiuser();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean modesUi() {
        return getValue(Flags.FLAG_MODES_UI, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda80
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).modesUi();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean modesUiDndTile() {
        return getValue(Flags.FLAG_MODES_UI_DND_TILE, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).modesUiDndTile();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean modesUiEmptyShade() {
        return getValue(Flags.FLAG_MODES_UI_EMPTY_SHADE, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda56
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).modesUiEmptyShade();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean modesUiIcons() {
        return getValue(Flags.FLAG_MODES_UI_ICONS, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda64
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).modesUiIcons();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean nmBinderPerfCacheChannels() {
        return getValue(Flags.FLAG_NM_BINDER_PERF_CACHE_CHANNELS, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nmBinderPerfCacheChannels();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean nmBinderPerfGetAppsWithChannels() {
        return getValue(Flags.FLAG_NM_BINDER_PERF_GET_APPS_WITH_CHANNELS, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda81
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nmBinderPerfGetAppsWithChannels();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean nmBinderPerfLogNmThrottling() {
        return getValue(Flags.FLAG_NM_BINDER_PERF_LOG_NM_THROTTLING, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda71
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nmBinderPerfLogNmThrottling();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean nmBinderPerfPermissionCheck() {
        return getValue(Flags.FLAG_NM_BINDER_PERF_PERMISSION_CHECK, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda73
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nmBinderPerfPermissionCheck();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean nmBinderPerfThrottleNotify() {
        return getValue(Flags.FLAG_NM_BINDER_PERF_THROTTLE_NOTIFY, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda90
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nmBinderPerfThrottleNotify();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean nmCollapsedLines() {
        return getValue(Flags.FLAG_NM_COLLAPSED_LINES, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nmCollapsedLines();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean nmSummarization() {
        return getValue(Flags.FLAG_NM_SUMMARIZATION, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda67
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nmSummarization();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean nmSummarizationUi() {
        return getValue(Flags.FLAG_NM_SUMMARIZATION_UI, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda54
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nmSummarizationUi();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean noSbnholder() {
        return getValue(Flags.FLAG_NO_SBNHOLDER, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda61
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).noSbnholder();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean notifChannelCropVibrationEffects() {
        return getValue(Flags.FLAG_NOTIF_CHANNEL_CROP_VIBRATION_EFFECTS, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda49
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notifChannelCropVibrationEffects();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean notifChannelEstimateEffectSize() {
        return getValue(Flags.FLAG_NOTIF_CHANNEL_ESTIMATE_EFFECT_SIZE, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda37
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notifChannelEstimateEffectSize();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean notifEntryCreationTimeUseElapsedRealtime() {
        return getValue(Flags.FLAG_NOTIF_ENTRY_CREATION_TIME_USE_ELAPSED_REALTIME, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda87
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notifEntryCreationTimeUseElapsedRealtime();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean notificationChannelVibrationEffectApi() {
        return getValue(Flags.FLAG_NOTIFICATION_CHANNEL_VIBRATION_EFFECT_API, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda69
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notificationChannelVibrationEffectApi();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean notificationClassificationUi() {
        return getValue(Flags.FLAG_NOTIFICATION_CLASSIFICATION_UI, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notificationClassificationUi();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean notificationExpansionOptional() {
        return getValue(Flags.FLAG_NOTIFICATION_EXPANSION_OPTIONAL, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notificationExpansionOptional();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean notificationNoCustomViewConversations() {
        return getValue(Flags.FLAG_NOTIFICATION_NO_CUSTOM_VIEW_CONVERSATIONS, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda57
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notificationNoCustomViewConversations();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean notificationsRedesignAppIcons() {
        return getValue(Flags.FLAG_NOTIFICATIONS_REDESIGN_APP_ICONS, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda88
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notificationsRedesignAppIcons();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean notificationsRedesignTemplates() {
        return getValue(Flags.FLAG_NOTIFICATIONS_REDESIGN_TEMPLATES, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda50
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notificationsRedesignTemplates();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean notificationsRedesignThemedAppIcons() {
        return getValue(Flags.FLAG_NOTIFICATIONS_REDESIGN_THEMED_APP_ICONS, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notificationsRedesignThemedAppIcons();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean notifyKeyguardEvents() {
        return getValue(Flags.FLAG_NOTIFY_KEYGUARD_EVENTS, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda75
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notifyKeyguardEvents();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean picCacheNulls() {
        return getValue(Flags.FLAG_PIC_CACHE_NULLS, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda48
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).picCacheNulls();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean picIsolateCacheByUid() {
        return getValue(Flags.FLAG_PIC_ISOLATE_CACHE_BY_UID, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda53
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).picIsolateCacheByUid();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean picIsolatedCacheStatistics() {
        return getValue(Flags.FLAG_PIC_ISOLATED_CACHE_STATISTICS, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda36
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).picIsolatedCacheStatistics();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean picSeparatePermissionNotifications() {
        return getValue(Flags.FLAG_PIC_SEPARATE_PERMISSION_NOTIFICATIONS, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).picSeparatePermissionNotifications();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean picTestMode() {
        return getValue(Flags.FLAG_PIC_TEST_MODE, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda59
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).picTestMode();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean picUsesSharedMemory() {
        return getValue(Flags.FLAG_PIC_USES_SHARED_MEMORY, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).picUsesSharedMemory();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean pinnerServiceClientApi() {
        return getValue(Flags.FLAG_PINNER_SERVICE_CLIENT_API, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).pinnerServiceClientApi();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean rateLimitGetMemoryInfo() {
        return getValue(Flags.FLAG_RATE_LIMIT_GET_MEMORY_INFO, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).rateLimitGetMemoryInfo();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean rateLimitGetMyMemoryState() {
        return getValue(Flags.FLAG_RATE_LIMIT_GET_MY_MEMORY_STATE, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda38
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).rateLimitGetMyMemoryState();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean rateLimitGetProcessesInErrorState() {
        return getValue(Flags.FLAG_RATE_LIMIT_GET_PROCESSES_IN_ERROR_STATE, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda62
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).rateLimitGetProcessesInErrorState();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean rateLimitGetRunningAppProcesses() {
        return getValue(Flags.FLAG_RATE_LIMIT_GET_RUNNING_APP_PROCESSES, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda78
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).rateLimitGetRunningAppProcesses();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean redactSensitiveContentNotificationsOnLockscreen() {
        return getValue(Flags.FLAG_REDACT_SENSITIVE_CONTENT_NOTIFICATIONS_ON_LOCKSCREEN, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda92
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).redactSensitiveContentNotificationsOnLockscreen();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean redactionOnLockscreenMetrics() {
        return getValue(Flags.FLAG_REDACTION_ON_LOCKSCREEN_METRICS, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda29
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).redactionOnLockscreenMetrics();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean removeNextWallpaperComponent() {
        return getValue(Flags.FLAG_REMOVE_NEXT_WALLPAPER_COMPONENT, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).removeNextWallpaperComponent();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean removeRemoteViews() {
        return getValue(Flags.FLAG_REMOVE_REMOTE_VIEWS, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda46
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).removeRemoteViews();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean reportPostgcMemoryMetrics() {
        return getValue(Flags.FLAG_REPORT_POSTGC_MEMORY_METRICS, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda63
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).reportPostgcMemoryMetrics();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean restrictAudioAttributesAlarm() {
        return getValue(Flags.FLAG_RESTRICT_AUDIO_ATTRIBUTES_ALARM, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).restrictAudioAttributesAlarm();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean restrictAudioAttributesCall() {
        return getValue(Flags.FLAG_RESTRICT_AUDIO_ATTRIBUTES_CALL, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda66
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).restrictAudioAttributesCall();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean restrictAudioAttributesMedia() {
        return getValue(Flags.FLAG_RESTRICT_AUDIO_ATTRIBUTES_MEDIA, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda74
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).restrictAudioAttributesMedia();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean secureAllowlistToken() {
        return getValue(Flags.FLAG_SECURE_ALLOWLIST_TOKEN, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).secureAllowlistToken();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean skipBgMemTrimOnFgApp() {
        return getValue(Flags.FLAG_SKIP_BG_MEM_TRIM_ON_FG_APP, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).skipBgMemTrimOnFgApp();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean sortSectionByTime() {
        return getValue(Flags.FLAG_SORT_SECTION_BY_TIME, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda39
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sortSectionByTime();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean systemTermsOfAddressEnabled() {
        return getValue(Flags.FLAG_SYSTEM_TERMS_OF_ADDRESS_ENABLED, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda65
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).systemTermsOfAddressEnabled();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean uiRichOngoing() {
        return getValue(Flags.FLAG_UI_RICH_ONGOING, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).uiRichOngoing();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean uidImportanceListenerForUids() {
        return getValue(Flags.FLAG_UID_IMPORTANCE_LISTENER_FOR_UIDS, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).uidImportanceListenerForUids();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean useAppInfoNotLaunched() {
        return getValue(Flags.FLAG_USE_APP_INFO_NOT_LAUNCHED, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda72
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useAppInfoNotLaunched();
            }
        });
    }

    @Override // android.app.FeatureFlags
    public boolean useStickyBcastCache() {
        return getValue(Flags.FLAG_USE_STICKY_BCAST_CACHE, new Predicate() { // from class: android.app.CustomFeatureFlags$$ExternalSyntheticLambda68
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useStickyBcastCache();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_ACCURATE_WALLPAPER_DOWNSAMPLING, Flags.FLAG_API_RICH_ONGOING, Flags.FLAG_API_TVEXTENDER, Flags.FLAG_APP_RESTRICTIONS_API, Flags.FLAG_APP_START_INFO, Flags.FLAG_APP_START_INFO_CLEANUP_OLD_RECORDS, Flags.FLAG_APP_START_INFO_COMPONENT, Flags.FLAG_APP_START_INFO_KEEP_RECORDS_SORTED, Flags.FLAG_APP_START_INFO_TIMESTAMPS, Flags.FLAG_BACKGROUND_INSTALL_CONTROL_CALLBACK_API, Flags.FLAG_BACKUP_RESTORE_LOGGING, Flags.FLAG_BIC_CLIENT, Flags.FLAG_CACHE_GET_CURRENT_USER_ID, Flags.FLAG_CATEGORY_VOICEMAIL, Flags.FLAG_CHECK_AUTOGROUP_BEFORE_POST, Flags.FLAG_CLEAN_UP_SPANS_AND_NEW_LINES, Flags.FLAG_CLEAR_DNS_CACHE_ON_NETWORK_RULES_UPDATE, Flags.FLAG_COMPACT_HEADS_UP_NOTIFICATION, Flags.FLAG_COMPACT_HEADS_UP_NOTIFICATION_REPLY, Flags.FLAG_DEVICE_UNLOCK_LISTENER, Flags.FLAG_ENABLE_CONNECTED_DISPLAYS_WALLPAPER, Flags.FLAG_ENABLE_CURRENT_MODE_TYPE_BINDER_CACHE, Flags.FLAG_ENABLE_FGS_TIMEOUT_CRASH_BEHAVIOR, Flags.FLAG_ENABLE_NIGHT_MODE_BINDER_CACHE, Flags.FLAG_ENABLE_PIP_UI_STATE_CALLBACK_ON_ENTERING, Flags.FLAG_ENABLE_PROCESS_OBSERVER_BROADCAST_ON_PROCESS_STARTED, Flags.FLAG_ENABLE_TV_IMPLICIT_ENTER_PIP_RESTRICTION, Flags.FLAG_ENFORCE_PIC_TESTMODE_PROTOCOL, Flags.FLAG_EVENLY_DIVIDED_CALL_STYLE_ACTION_LAYOUT, Flags.FLAG_EXPANDING_PUBLIC_VIEW, Flags.FLAG_FIX_WALLPAPER_CHANGED, Flags.FLAG_GET_BINDING_UID_IMPORTANCE, Flags.FLAG_INTRODUCE_NEW_SERVICE_ONTIMEOUT_CALLBACK, Flags.FLAG_JANK_PERCEPTIBLE_NARROW, Flags.FLAG_JANK_PERCEPTIBLE_NARROW_HOLDBACK, Flags.FLAG_KEYGUARD_PRIVATE_NOTIFICATIONS, Flags.FLAG_LIFETIME_EXTENSION_REFACTOR, Flags.FLAG_LIVE_WALLPAPER_CONTENT_HANDLING, Flags.FLAG_MODES_API, Flags.FLAG_MODES_CLEANUP_IMPLICIT, Flags.FLAG_MODES_HSUM, Flags.FLAG_MODES_MULTIUSER, Flags.FLAG_MODES_UI, Flags.FLAG_MODES_UI_DND_TILE, Flags.FLAG_MODES_UI_EMPTY_SHADE, Flags.FLAG_MODES_UI_ICONS, Flags.FLAG_NM_BINDER_PERF_CACHE_CHANNELS, Flags.FLAG_NM_BINDER_PERF_GET_APPS_WITH_CHANNELS, Flags.FLAG_NM_BINDER_PERF_LOG_NM_THROTTLING, Flags.FLAG_NM_BINDER_PERF_PERMISSION_CHECK, Flags.FLAG_NM_BINDER_PERF_THROTTLE_NOTIFY, Flags.FLAG_NM_COLLAPSED_LINES, Flags.FLAG_NM_SUMMARIZATION, Flags.FLAG_NM_SUMMARIZATION_UI, Flags.FLAG_NO_SBNHOLDER, Flags.FLAG_NOTIF_CHANNEL_CROP_VIBRATION_EFFECTS, Flags.FLAG_NOTIF_CHANNEL_ESTIMATE_EFFECT_SIZE, Flags.FLAG_NOTIF_ENTRY_CREATION_TIME_USE_ELAPSED_REALTIME, Flags.FLAG_NOTIFICATION_CHANNEL_VIBRATION_EFFECT_API, Flags.FLAG_NOTIFICATION_CLASSIFICATION_UI, Flags.FLAG_NOTIFICATION_EXPANSION_OPTIONAL, Flags.FLAG_NOTIFICATION_NO_CUSTOM_VIEW_CONVERSATIONS, Flags.FLAG_NOTIFICATIONS_REDESIGN_APP_ICONS, Flags.FLAG_NOTIFICATIONS_REDESIGN_TEMPLATES, Flags.FLAG_NOTIFICATIONS_REDESIGN_THEMED_APP_ICONS, Flags.FLAG_NOTIFY_KEYGUARD_EVENTS, Flags.FLAG_PIC_CACHE_NULLS, Flags.FLAG_PIC_ISOLATE_CACHE_BY_UID, Flags.FLAG_PIC_ISOLATED_CACHE_STATISTICS, Flags.FLAG_PIC_SEPARATE_PERMISSION_NOTIFICATIONS, Flags.FLAG_PIC_TEST_MODE, Flags.FLAG_PIC_USES_SHARED_MEMORY, Flags.FLAG_PINNER_SERVICE_CLIENT_API, Flags.FLAG_RATE_LIMIT_GET_MEMORY_INFO, Flags.FLAG_RATE_LIMIT_GET_MY_MEMORY_STATE, Flags.FLAG_RATE_LIMIT_GET_PROCESSES_IN_ERROR_STATE, Flags.FLAG_RATE_LIMIT_GET_RUNNING_APP_PROCESSES, Flags.FLAG_REDACT_SENSITIVE_CONTENT_NOTIFICATIONS_ON_LOCKSCREEN, Flags.FLAG_REDACTION_ON_LOCKSCREEN_METRICS, Flags.FLAG_REMOVE_NEXT_WALLPAPER_COMPONENT, Flags.FLAG_REMOVE_REMOTE_VIEWS, Flags.FLAG_REPORT_POSTGC_MEMORY_METRICS, Flags.FLAG_RESTRICT_AUDIO_ATTRIBUTES_ALARM, Flags.FLAG_RESTRICT_AUDIO_ATTRIBUTES_CALL, Flags.FLAG_RESTRICT_AUDIO_ATTRIBUTES_MEDIA, Flags.FLAG_SECURE_ALLOWLIST_TOKEN, Flags.FLAG_SKIP_BG_MEM_TRIM_ON_FG_APP, Flags.FLAG_SORT_SECTION_BY_TIME, Flags.FLAG_SYSTEM_TERMS_OF_ADDRESS_ENABLED, Flags.FLAG_UI_RICH_ONGOING, Flags.FLAG_UID_IMPORTANCE_LISTENER_FOR_UIDS, Flags.FLAG_USE_APP_INFO_NOT_LAUNCHED, Flags.FLAG_USE_STICKY_BCAST_CACHE);
    }
}
