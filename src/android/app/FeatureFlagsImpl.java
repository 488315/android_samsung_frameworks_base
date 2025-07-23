package android.app;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.app.FeatureFlags
    public boolean accurateWallpaperDownsampling() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean apiRichOngoing() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean apiTvextender() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean appRestrictionsApi() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean appStartInfo() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean appStartInfoCleanupOldRecords() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean appStartInfoComponent() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean appStartInfoKeepRecordsSorted() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean appStartInfoTimestamps() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean backgroundInstallControlCallbackApi() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean backupRestoreLogging() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean bicClient() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean cacheGetCurrentUserId() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean categoryVoicemail() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean checkAutogroupBeforePost() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean cleanUpSpansAndNewLines() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean clearDnsCacheOnNetworkRulesUpdate() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean compactHeadsUpNotification() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean compactHeadsUpNotificationReply() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean deviceUnlockListener() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean enableConnectedDisplaysWallpaper() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean enableCurrentModeTypeBinderCache() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean enableFgsTimeoutCrashBehavior() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean enableNightModeBinderCache() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean enablePipUiStateCallbackOnEntering() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean enableProcessObserverBroadcastOnProcessStarted() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean enableTvImplicitEnterPipRestriction() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean enforcePicTestmodeProtocol() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean evenlyDividedCallStyleActionLayout() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean expandingPublicView() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean fixWallpaperChanged() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean getBindingUidImportance() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean introduceNewServiceOntimeoutCallback() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean jankPerceptibleNarrow() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean jankPerceptibleNarrowHoldback() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean keyguardPrivateNotifications() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean lifetimeExtensionRefactor() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean liveWallpaperContentHandling() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean modesApi() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean modesCleanupImplicit() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean modesHsum() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean modesMultiuser() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean modesUi() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean modesUiDndTile() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean modesUiEmptyShade() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean modesUiIcons() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean nmBinderPerfCacheChannels() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean nmBinderPerfGetAppsWithChannels() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean nmBinderPerfLogNmThrottling() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean nmBinderPerfPermissionCheck() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean nmBinderPerfThrottleNotify() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean nmCollapsedLines() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean nmSummarization() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean nmSummarizationUi() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean noSbnholder() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean notifChannelCropVibrationEffects() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean notifChannelEstimateEffectSize() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean notifEntryCreationTimeUseElapsedRealtime() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean notificationChannelVibrationEffectApi() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean notificationClassificationUi() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean notificationExpansionOptional() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean notificationNoCustomViewConversations() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean notificationsRedesignAppIcons() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean notificationsRedesignTemplates() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean notificationsRedesignThemedAppIcons() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean notifyKeyguardEvents() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean picCacheNulls() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean picIsolateCacheByUid() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean picIsolatedCacheStatistics() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean picSeparatePermissionNotifications() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean picTestMode() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean picUsesSharedMemory() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean pinnerServiceClientApi() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean rateLimitGetMemoryInfo() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean rateLimitGetMyMemoryState() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean rateLimitGetProcessesInErrorState() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean rateLimitGetRunningAppProcesses() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean redactSensitiveContentNotificationsOnLockscreen() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean redactionOnLockscreenMetrics() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean removeNextWallpaperComponent() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean removeRemoteViews() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean reportPostgcMemoryMetrics() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean restrictAudioAttributesAlarm() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean restrictAudioAttributesCall() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean restrictAudioAttributesMedia() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean secureAllowlistToken() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean skipBgMemTrimOnFgApp() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean sortSectionByTime() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean systemTermsOfAddressEnabled() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean uiRichOngoing() {
        return false;
    }

    @Override // android.app.FeatureFlags
    public boolean uidImportanceListenerForUids() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean useAppInfoNotLaunched() {
        return true;
    }

    @Override // android.app.FeatureFlags
    public boolean useStickyBcastCache() {
        return true;
    }
}
