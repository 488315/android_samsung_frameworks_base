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
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_API_RICH_ONGOING,
                            Flags.FLAG_API_TVEXTENDER,
                            Flags.FLAG_APP_RESTRICTIONS_API,
                            Flags.FLAG_APP_START_INFO,
                            Flags.FLAG_APP_START_INFO_TIMESTAMPS,
                            Flags.FLAG_BIC_CLIENT,
                            Flags.FLAG_CATEGORY_VOICEMAIL,
                            Flags.FLAG_CHECK_AUTOGROUP_BEFORE_POST,
                            Flags.FLAG_CLEAN_UP_SPANS_AND_NEW_LINES,
                            Flags.FLAG_CLEAR_DNS_CACHE_ON_NETWORK_RULES_UPDATE,
                            Flags.FLAG_COMPACT_HEADS_UP_NOTIFICATION,
                            Flags.FLAG_COMPACT_HEADS_UP_NOTIFICATION_REPLY,
                            Flags.FLAG_ENABLE_FGS_TIMEOUT_CRASH_BEHAVIOR,
                            Flags.FLAG_ENABLE_NIGHT_MODE_BINDER_CACHE,
                            Flags.FLAG_ENABLE_PIP_UI_STATE_CALLBACK_ON_ENTERING,
                            Flags.FLAG_EVENLY_DIVIDED_CALL_STYLE_ACTION_LAYOUT,
                            Flags.FLAG_GATE_FGS_TIMEOUT_ANR_BEHAVIOR,
                            Flags.FLAG_GET_BINDING_UID_IMPORTANCE,
                            Flags.FLAG_INTRODUCE_NEW_SERVICE_ONTIMEOUT_CALLBACK,
                            Flags.FLAG_KEYGUARD_PRIVATE_NOTIFICATIONS,
                            Flags.FLAG_LIFETIME_EXTENSION_REFACTOR,
                            Flags.FLAG_MODES_API,
                            Flags.FLAG_MODES_UI,
                            Flags.FLAG_NOTIFICATION_CHANNEL_VIBRATION_EFFECT_API,
                            Flags.FLAG_NOTIFICATION_EXPANSION_OPTIONAL,
                            Flags.FLAG_NOTIFICATIONS_USE_APP_ICON,
                            Flags.FLAG_NOTIFICATIONS_USE_APP_ICON_IN_ROW,
                            Flags.FLAG_NOTIFICATIONS_USE_MONOCHROME_APP_ICON,
                            Flags.FLAG_PINNER_SERVICE_CLIENT_API,
                            Flags.FLAG_REDACT_SENSITIVE_CONTENT_NOTIFICATIONS_ON_LOCKSCREEN,
                            Flags.FLAG_REMOVE_REMOTE_VIEWS,
                            Flags.FLAG_RESTRICT_AUDIO_ATTRIBUTES_ALARM,
                            Flags.FLAG_RESTRICT_AUDIO_ATTRIBUTES_CALL,
                            Flags.FLAG_RESTRICT_AUDIO_ATTRIBUTES_MEDIA,
                            Flags.FLAG_SECURE_ALLOWLIST_TOKEN,
                            Flags.FLAG_SKIP_BG_MEM_TRIM_ON_FG_APP,
                            Flags.FLAG_SORT_SECTION_BY_TIME,
                            Flags.FLAG_SYSTEM_TERMS_OF_ADDRESS_ENABLED,
                            Flags.FLAG_UID_IMPORTANCE_LISTENER_FOR_UIDS,
                            Flags.FLAG_UPDATE_RANKING_TIME,
                            Flags.FLAG_USE_APP_INFO_NOT_LAUNCHED,
                            Flags.FLAG_VISIT_PERSON_URI,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.app.FeatureFlags
    public boolean apiRichOngoing() {
        return getValue(
                Flags.FLAG_API_RICH_ONGOING,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda8
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).apiRichOngoing();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean apiTvextender() {
        return getValue(
                Flags.FLAG_API_TVEXTENDER,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda17
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).apiTvextender();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean appRestrictionsApi() {
        return getValue(
                Flags.FLAG_APP_RESTRICTIONS_API,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda37
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).appRestrictionsApi();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean appStartInfo() {
        return getValue(
                Flags.FLAG_APP_START_INFO,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda39
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).appStartInfo();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean appStartInfoTimestamps() {
        return getValue(
                Flags.FLAG_APP_START_INFO_TIMESTAMPS,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).appStartInfoTimestamps();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean bicClient() {
        return getValue(
                Flags.FLAG_BIC_CLIENT,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda35
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).bicClient();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean categoryVoicemail() {
        return getValue(
                Flags.FLAG_CATEGORY_VOICEMAIL,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).categoryVoicemail();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean checkAutogroupBeforePost() {
        return getValue(
                Flags.FLAG_CHECK_AUTOGROUP_BEFORE_POST,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda21
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).checkAutogroupBeforePost();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean cleanUpSpansAndNewLines() {
        return getValue(
                Flags.FLAG_CLEAN_UP_SPANS_AND_NEW_LINES,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda24
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).cleanUpSpansAndNewLines();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean clearDnsCacheOnNetworkRulesUpdate() {
        return getValue(
                Flags.FLAG_CLEAR_DNS_CACHE_ON_NETWORK_RULES_UPDATE,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).clearDnsCacheOnNetworkRulesUpdate();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean compactHeadsUpNotification() {
        return getValue(
                Flags.FLAG_COMPACT_HEADS_UP_NOTIFICATION,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda20
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).compactHeadsUpNotification();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean compactHeadsUpNotificationReply() {
        return getValue(
                Flags.FLAG_COMPACT_HEADS_UP_NOTIFICATION_REPLY,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda30
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).compactHeadsUpNotificationReply();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean enableFgsTimeoutCrashBehavior() {
        return getValue(
                Flags.FLAG_ENABLE_FGS_TIMEOUT_CRASH_BEHAVIOR,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda25
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableFgsTimeoutCrashBehavior();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean enableNightModeBinderCache() {
        return getValue(
                Flags.FLAG_ENABLE_NIGHT_MODE_BINDER_CACHE,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableNightModeBinderCache();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean enablePipUiStateCallbackOnEntering() {
        return getValue(
                Flags.FLAG_ENABLE_PIP_UI_STATE_CALLBACK_ON_ENTERING,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda16
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enablePipUiStateCallbackOnEntering();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean evenlyDividedCallStyleActionLayout() {
        return getValue(
                Flags.FLAG_EVENLY_DIVIDED_CALL_STYLE_ACTION_LAYOUT,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda12
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).evenlyDividedCallStyleActionLayout();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean gateFgsTimeoutAnrBehavior() {
        return getValue(
                Flags.FLAG_GATE_FGS_TIMEOUT_ANR_BEHAVIOR,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda33
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).gateFgsTimeoutAnrBehavior();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean getBindingUidImportance() {
        return getValue(
                Flags.FLAG_GET_BINDING_UID_IMPORTANCE,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda22
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).getBindingUidImportance();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean introduceNewServiceOntimeoutCallback() {
        return getValue(
                Flags.FLAG_INTRODUCE_NEW_SERVICE_ONTIMEOUT_CALLBACK,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda14
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).introduceNewServiceOntimeoutCallback();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean keyguardPrivateNotifications() {
        return getValue(
                Flags.FLAG_KEYGUARD_PRIVATE_NOTIFICATIONS,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda19
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).keyguardPrivateNotifications();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean lifetimeExtensionRefactor() {
        return getValue(
                Flags.FLAG_LIFETIME_EXTENSION_REFACTOR,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda40
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).lifetimeExtensionRefactor();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean modesApi() {
        return getValue(
                Flags.FLAG_MODES_API,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda15
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).modesApi();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean modesUi() {
        return getValue(
                Flags.FLAG_MODES_UI,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda38
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).modesUi();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean notificationChannelVibrationEffectApi() {
        return getValue(
                Flags.FLAG_NOTIFICATION_CHANNEL_VIBRATION_EFFECT_API,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda29
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).notificationChannelVibrationEffectApi();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean notificationExpansionOptional() {
        return getValue(
                Flags.FLAG_NOTIFICATION_EXPANSION_OPTIONAL,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda13
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).notificationExpansionOptional();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean notificationsUseAppIcon() {
        return getValue(
                Flags.FLAG_NOTIFICATIONS_USE_APP_ICON,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda34
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).notificationsUseAppIcon();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean notificationsUseAppIconInRow() {
        return getValue(
                Flags.FLAG_NOTIFICATIONS_USE_APP_ICON_IN_ROW,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda11
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).notificationsUseAppIconInRow();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean notificationsUseMonochromeAppIcon() {
        return getValue(
                Flags.FLAG_NOTIFICATIONS_USE_MONOCHROME_APP_ICON,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda36
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).notificationsUseMonochromeAppIcon();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean pinnerServiceClientApi() {
        return getValue(
                Flags.FLAG_PINNER_SERVICE_CLIENT_API,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).pinnerServiceClientApi();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean redactSensitiveContentNotificationsOnLockscreen() {
        return getValue(
                Flags.FLAG_REDACT_SENSITIVE_CONTENT_NOTIFICATIONS_ON_LOCKSCREEN,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda41
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj)
                                .redactSensitiveContentNotificationsOnLockscreen();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean removeRemoteViews() {
        return getValue(
                Flags.FLAG_REMOVE_REMOTE_VIEWS,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda23
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).removeRemoteViews();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean restrictAudioAttributesAlarm() {
        return getValue(
                Flags.FLAG_RESTRICT_AUDIO_ATTRIBUTES_ALARM,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).restrictAudioAttributesAlarm();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean restrictAudioAttributesCall() {
        return getValue(
                Flags.FLAG_RESTRICT_AUDIO_ATTRIBUTES_CALL,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda28
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).restrictAudioAttributesCall();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean restrictAudioAttributesMedia() {
        return getValue(
                Flags.FLAG_RESTRICT_AUDIO_ATTRIBUTES_MEDIA,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda32
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).restrictAudioAttributesMedia();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean secureAllowlistToken() {
        return getValue(
                Flags.FLAG_SECURE_ALLOWLIST_TOKEN,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).secureAllowlistToken();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean skipBgMemTrimOnFgApp() {
        return getValue(
                Flags.FLAG_SKIP_BG_MEM_TRIM_ON_FG_APP,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).skipBgMemTrimOnFgApp();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean sortSectionByTime() {
        return getValue(
                Flags.FLAG_SORT_SECTION_BY_TIME,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda18
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).sortSectionByTime();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean systemTermsOfAddressEnabled() {
        return getValue(
                Flags.FLAG_SYSTEM_TERMS_OF_ADDRESS_ENABLED,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda27
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).systemTermsOfAddressEnabled();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean uidImportanceListenerForUids() {
        return getValue(
                Flags.FLAG_UID_IMPORTANCE_LISTENER_FOR_UIDS,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).uidImportanceListenerForUids();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean updateRankingTime() {
        return getValue(
                Flags.FLAG_UPDATE_RANKING_TIME,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).updateRankingTime();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean useAppInfoNotLaunched() {
        return getValue(
                Flags.FLAG_USE_APP_INFO_NOT_LAUNCHED,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda31
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).useAppInfoNotLaunched();
                    }
                });
    }

    @Override // android.app.FeatureFlags
    public boolean visitPersonUri() {
        return getValue(
                Flags.FLAG_VISIT_PERSON_URI,
                new Predicate() { // from class:
                    // android.app.CustomFeatureFlags$$ExternalSyntheticLambda26
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).visitPersonUri();
                    }
                });
    }

    public boolean isFlagReadOnlyOptimized(String flagName) {
        if (this.mReadOnlyFlagsSet.contains(flagName) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean isOptimizationEnabled() {
        return false;
    }

    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        return this.mGetValueImpl.test(flagName, getter);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(
                Flags.FLAG_API_RICH_ONGOING,
                Flags.FLAG_API_TVEXTENDER,
                Flags.FLAG_APP_RESTRICTIONS_API,
                Flags.FLAG_APP_START_INFO,
                Flags.FLAG_APP_START_INFO_TIMESTAMPS,
                Flags.FLAG_BIC_CLIENT,
                Flags.FLAG_CATEGORY_VOICEMAIL,
                Flags.FLAG_CHECK_AUTOGROUP_BEFORE_POST,
                Flags.FLAG_CLEAN_UP_SPANS_AND_NEW_LINES,
                Flags.FLAG_CLEAR_DNS_CACHE_ON_NETWORK_RULES_UPDATE,
                Flags.FLAG_COMPACT_HEADS_UP_NOTIFICATION,
                Flags.FLAG_COMPACT_HEADS_UP_NOTIFICATION_REPLY,
                Flags.FLAG_ENABLE_FGS_TIMEOUT_CRASH_BEHAVIOR,
                Flags.FLAG_ENABLE_NIGHT_MODE_BINDER_CACHE,
                Flags.FLAG_ENABLE_PIP_UI_STATE_CALLBACK_ON_ENTERING,
                Flags.FLAG_EVENLY_DIVIDED_CALL_STYLE_ACTION_LAYOUT,
                Flags.FLAG_GATE_FGS_TIMEOUT_ANR_BEHAVIOR,
                Flags.FLAG_GET_BINDING_UID_IMPORTANCE,
                Flags.FLAG_INTRODUCE_NEW_SERVICE_ONTIMEOUT_CALLBACK,
                Flags.FLAG_KEYGUARD_PRIVATE_NOTIFICATIONS,
                Flags.FLAG_LIFETIME_EXTENSION_REFACTOR,
                Flags.FLAG_MODES_API,
                Flags.FLAG_MODES_UI,
                Flags.FLAG_NOTIFICATION_CHANNEL_VIBRATION_EFFECT_API,
                Flags.FLAG_NOTIFICATION_EXPANSION_OPTIONAL,
                Flags.FLAG_NOTIFICATIONS_USE_APP_ICON,
                Flags.FLAG_NOTIFICATIONS_USE_APP_ICON_IN_ROW,
                Flags.FLAG_NOTIFICATIONS_USE_MONOCHROME_APP_ICON,
                Flags.FLAG_PINNER_SERVICE_CLIENT_API,
                Flags.FLAG_REDACT_SENSITIVE_CONTENT_NOTIFICATIONS_ON_LOCKSCREEN,
                Flags.FLAG_REMOVE_REMOTE_VIEWS,
                Flags.FLAG_RESTRICT_AUDIO_ATTRIBUTES_ALARM,
                Flags.FLAG_RESTRICT_AUDIO_ATTRIBUTES_CALL,
                Flags.FLAG_RESTRICT_AUDIO_ATTRIBUTES_MEDIA,
                Flags.FLAG_SECURE_ALLOWLIST_TOKEN,
                Flags.FLAG_SKIP_BG_MEM_TRIM_ON_FG_APP,
                Flags.FLAG_SORT_SECTION_BY_TIME,
                Flags.FLAG_SYSTEM_TERMS_OF_ADDRESS_ENABLED,
                Flags.FLAG_UID_IMPORTANCE_LISTENER_FOR_UIDS,
                Flags.FLAG_UPDATE_RANKING_TIME,
                Flags.FLAG_USE_APP_INFO_NOT_LAUNCHED,
                Flags.FLAG_VISIT_PERSON_URI);
    }
}
