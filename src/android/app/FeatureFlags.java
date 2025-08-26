package android.app;

/* loaded from: classes.dex */
public interface FeatureFlags {
    boolean accurateWallpaperDownsampling();

    boolean apiRichOngoing();

    boolean apiRichOngoingPermission();

    boolean apiTvextender();

    boolean appRestrictionsApi();

    boolean appStartInfo();

    boolean appStartInfoCleanupOldRecords();

    boolean appStartInfoComponent();

    boolean appStartInfoKeepRecordsSorted();

    boolean appStartInfoTimestamps();

    boolean backgroundInstallControlCallbackApi();

    boolean backupRestoreLogging();

    boolean bicClient();

    boolean cacheGetCurrentUserId();

    boolean categoryVoicemail();

    boolean checkAutogroupBeforePost();

    boolean cleanUpSpansAndNewLines();

    boolean clearDnsCacheOnNetworkRulesUpdate();

    boolean compactHeadsUpNotification();

    boolean compactHeadsUpNotificationReply();

    boolean deviceUnlockListener();

    boolean enableConnectedDisplaysWallpaper();

    boolean enableCurrentModeTypeBinderCache();

    boolean enableFgsTimeoutCrashBehavior();

    boolean enableNightModeBinderCache();

    boolean enablePipUiStateCallbackOnEntering();

    boolean enableProcessObserverBroadcastOnProcessStarted();

    boolean enableTvImplicitEnterPipRestriction();

    boolean enforcePicTestmodeProtocol();

    boolean evenlyDividedCallStyleActionLayout();

    boolean expandingPublicView();

    boolean fixWallpaperChanged();

    boolean getBindingUidImportance();

    boolean introduceNewServiceOntimeoutCallback();

    boolean jankPerceptibleNarrow();

    boolean jankPerceptibleNarrowHoldback();

    boolean keyguardPrivateNotifications();

    boolean lifetimeExtensionRefactor();

    boolean liveWallpaperContentHandling();

    boolean modesApi();

    boolean modesCleanupImplicit();

    boolean modesHsum();

    boolean modesMultiuser();

    boolean modesUi();

    boolean modesUiDndTile();

    boolean modesUiEmptyShade();

    boolean modesUiIcons();

    boolean nmBinderPerfCacheChannels();

    boolean nmBinderPerfGetAppsWithChannels();

    boolean nmBinderPerfLogNmThrottling();

    boolean nmBinderPerfPermissionCheck();

    boolean nmBinderPerfThrottleNotify();

    boolean nmCollapsedLines();

    boolean nmSummarization();

    boolean nmSummarizationUi();

    boolean noSbnholder();

    boolean notifChannelCropVibrationEffects();

    boolean notifChannelEstimateEffectSize();

    boolean notifEntryCreationTimeUseElapsedRealtime();

    boolean notificationChannelVibrationEffectApi();

    boolean notificationClassificationUi();

    boolean notificationExpansionOptional();

    boolean notificationNoCustomViewConversations();

    boolean notificationsRedesignAppIcons();

    boolean notificationsRedesignTemplates();

    boolean notificationsRedesignThemedAppIcons();

    boolean notifyKeyguardEvents();

    boolean picCacheNulls();

    boolean picIsolateCacheByUid();

    boolean picIsolatedCacheStatistics();

    boolean picSeparatePermissionNotifications();

    boolean picTestMode();

    boolean picUsesSharedMemory();

    boolean pinnerServiceClientApi();

    boolean rateLimitGetMemoryInfo();

    boolean rateLimitGetMyMemoryState();

    boolean rateLimitGetProcessesInErrorState();

    boolean rateLimitGetRunningAppProcesses();

    boolean redactSensitiveContentNotificationsOnLockscreen();

    boolean redactionOnLockscreenMetrics();

    boolean removeNextWallpaperComponent();

    boolean removeRemoteViews();

    boolean reportPostgcMemoryMetrics();

    boolean restrictAudioAttributesAlarm();

    boolean restrictAudioAttributesCall();

    boolean restrictAudioAttributesMedia();

    boolean secureAllowlistToken();

    boolean skipBgMemTrimOnFgApp();

    boolean sortSectionByTime();

    boolean systemTermsOfAddressEnabled();

    boolean uiRichOngoing();

    boolean uidImportanceListenerForUids();

    boolean useAppInfoNotLaunched();

    boolean useStickyBcastCache();
}
