package android.app;

/* loaded from: classes.dex */
public interface FeatureFlags {
    boolean apiRichOngoing();

    boolean apiTvextender();

    boolean appRestrictionsApi();

    boolean appStartInfo();

    boolean appStartInfoTimestamps();

    boolean bicClient();

    boolean categoryVoicemail();

    boolean checkAutogroupBeforePost();

    boolean cleanUpSpansAndNewLines();

    boolean clearDnsCacheOnNetworkRulesUpdate();

    boolean compactHeadsUpNotification();

    boolean compactHeadsUpNotificationReply();

    boolean enableFgsTimeoutCrashBehavior();

    boolean enableNightModeBinderCache();

    boolean enablePipUiStateCallbackOnEntering();

    boolean evenlyDividedCallStyleActionLayout();

    boolean gateFgsTimeoutAnrBehavior();

    boolean getBindingUidImportance();

    boolean introduceNewServiceOntimeoutCallback();

    boolean keyguardPrivateNotifications();

    boolean lifetimeExtensionRefactor();

    boolean modesApi();

    boolean modesUi();

    boolean notificationChannelVibrationEffectApi();

    boolean notificationExpansionOptional();

    boolean notificationsUseAppIcon();

    boolean notificationsUseAppIconInRow();

    boolean notificationsUseMonochromeAppIcon();

    boolean pinnerServiceClientApi();

    boolean redactSensitiveContentNotificationsOnLockscreen();

    boolean removeRemoteViews();

    boolean restrictAudioAttributesAlarm();

    boolean restrictAudioAttributesCall();

    boolean restrictAudioAttributesMedia();

    boolean secureAllowlistToken();

    boolean skipBgMemTrimOnFgApp();

    boolean sortSectionByTime();

    boolean systemTermsOfAddressEnabled();

    boolean uidImportanceListenerForUids();

    boolean updateRankingTime();

    boolean useAppInfoNotLaunched();

    boolean visitPersonUri();
}
