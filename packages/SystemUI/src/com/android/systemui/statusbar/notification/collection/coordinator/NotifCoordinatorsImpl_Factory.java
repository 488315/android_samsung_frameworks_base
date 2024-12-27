package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotifCoordinatorsImpl_Factory implements Provider {
    private final javax.inject.Provider bubbleCoordinatorProvider;
    private final javax.inject.Provider colorizedFgsCoordinatorProvider;
    private final javax.inject.Provider conversationCoordinatorProvider;
    private final javax.inject.Provider dataStoreCoordinatorProvider;
    private final javax.inject.Provider debugModeCoordinatorProvider;
    private final javax.inject.Provider deviceProvisionedCoordinatorProvider;
    private final javax.inject.Provider dismissibilityCoordinatorProvider;
    private final javax.inject.Provider dreamCoordinatorProvider;
    private final javax.inject.Provider edgeLightingCoordnatorProvider;
    private final javax.inject.Provider favoriteNotifCoordnatorProvider;
    private final javax.inject.Provider featureFlagsProvider;
    private final javax.inject.Provider groupCountCoordinatorProvider;
    private final javax.inject.Provider groupWhenCoordinatorProvider;
    private final javax.inject.Provider gutsCoordinatorProvider;
    private final javax.inject.Provider headsUpCoordinatorProvider;
    private final javax.inject.Provider hideLocallyDismissedNotifsCoordinatorProvider;
    private final javax.inject.Provider hideNotifsForOtherUsersCoordinatorProvider;
    private final javax.inject.Provider highlightsCoordinatorProvider;
    private final javax.inject.Provider insignificantCoordinatorProvider;
    private final javax.inject.Provider keyguardCoordinatorProvider;
    private final javax.inject.Provider lockScreenNotiIconCoordinatorProvider;
    private final javax.inject.Provider mediaCoordinatorProvider;
    private final javax.inject.Provider notifCounterCoordinatorProvider;
    private final javax.inject.Provider notifHeaderCoordinatorProvider;
    private final javax.inject.Provider notifTimeSortCoordnatorProvider;
    private final javax.inject.Provider notificationControlActionCoordinatorProvider;
    private final javax.inject.Provider notilusCoordinatorProvider;
    private final javax.inject.Provider ongoingActivityCoordinatorProvider;
    private final javax.inject.Provider onlyShowNewNotifCoordnatorProvider;
    private final javax.inject.Provider preparationCoordinatorProvider;
    private final javax.inject.Provider rankingCoordinatorProvider;
    private final javax.inject.Provider remoteInputCoordinatorProvider;
    private final javax.inject.Provider rowAlertTimeCoordinatorProvider;
    private final javax.inject.Provider rowAppearanceCoordinatorProvider;
    private final javax.inject.Provider sectionStyleProvider;
    private final javax.inject.Provider semPriorityCoordinatorProvider;
    private final javax.inject.Provider sensitiveContentCoordinatorProvider;
    private final javax.inject.Provider settingsChangedCoordinatorProvider;
    private final javax.inject.Provider shadeEventCoordinatorProvider;
    private final javax.inject.Provider smartspaceDedupingCoordinatorProvider;
    private final javax.inject.Provider stackCoordinatorProvider;
    private final javax.inject.Provider statsLoggerCoordinatorProvider;
    private final javax.inject.Provider subscreenNotificationListCoordinatorProvider;
    private final javax.inject.Provider subscreenQuickReplyCoordinatorProvider;
    private final javax.inject.Provider summarizeCoordinatorProvider;
    private final javax.inject.Provider viewConfigCoordinatorProvider;
    private final javax.inject.Provider visualStabilityCoordinatorProvider;

    public NotifCoordinatorsImpl_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10, javax.inject.Provider provider11, javax.inject.Provider provider12, javax.inject.Provider provider13, javax.inject.Provider provider14, javax.inject.Provider provider15, javax.inject.Provider provider16, javax.inject.Provider provider17, javax.inject.Provider provider18, javax.inject.Provider provider19, javax.inject.Provider provider20, javax.inject.Provider provider21, javax.inject.Provider provider22, javax.inject.Provider provider23, javax.inject.Provider provider24, javax.inject.Provider provider25, javax.inject.Provider provider26, javax.inject.Provider provider27, javax.inject.Provider provider28, javax.inject.Provider provider29, javax.inject.Provider provider30, javax.inject.Provider provider31, javax.inject.Provider provider32, javax.inject.Provider provider33, javax.inject.Provider provider34, javax.inject.Provider provider35, javax.inject.Provider provider36, javax.inject.Provider provider37, javax.inject.Provider provider38, javax.inject.Provider provider39, javax.inject.Provider provider40, javax.inject.Provider provider41, javax.inject.Provider provider42, javax.inject.Provider provider43, javax.inject.Provider provider44, javax.inject.Provider provider45, javax.inject.Provider provider46, javax.inject.Provider provider47) {
        this.sectionStyleProvider = provider;
        this.featureFlagsProvider = provider2;
        this.dataStoreCoordinatorProvider = provider3;
        this.hideLocallyDismissedNotifsCoordinatorProvider = provider4;
        this.hideNotifsForOtherUsersCoordinatorProvider = provider5;
        this.keyguardCoordinatorProvider = provider6;
        this.rankingCoordinatorProvider = provider7;
        this.colorizedFgsCoordinatorProvider = provider8;
        this.deviceProvisionedCoordinatorProvider = provider9;
        this.bubbleCoordinatorProvider = provider10;
        this.headsUpCoordinatorProvider = provider11;
        this.gutsCoordinatorProvider = provider12;
        this.conversationCoordinatorProvider = provider13;
        this.debugModeCoordinatorProvider = provider14;
        this.groupCountCoordinatorProvider = provider15;
        this.groupWhenCoordinatorProvider = provider16;
        this.mediaCoordinatorProvider = provider17;
        this.preparationCoordinatorProvider = provider18;
        this.remoteInputCoordinatorProvider = provider19;
        this.rowAlertTimeCoordinatorProvider = provider20;
        this.rowAppearanceCoordinatorProvider = provider21;
        this.stackCoordinatorProvider = provider22;
        this.shadeEventCoordinatorProvider = provider23;
        this.smartspaceDedupingCoordinatorProvider = provider24;
        this.viewConfigCoordinatorProvider = provider25;
        this.visualStabilityCoordinatorProvider = provider26;
        this.sensitiveContentCoordinatorProvider = provider27;
        this.dismissibilityCoordinatorProvider = provider28;
        this.dreamCoordinatorProvider = provider29;
        this.statsLoggerCoordinatorProvider = provider30;
        this.semPriorityCoordinatorProvider = provider31;
        this.lockScreenNotiIconCoordinatorProvider = provider32;
        this.ongoingActivityCoordinatorProvider = provider33;
        this.edgeLightingCoordnatorProvider = provider34;
        this.notifTimeSortCoordnatorProvider = provider35;
        this.notilusCoordinatorProvider = provider36;
        this.notifCounterCoordinatorProvider = provider37;
        this.highlightsCoordinatorProvider = provider38;
        this.summarizeCoordinatorProvider = provider39;
        this.insignificantCoordinatorProvider = provider40;
        this.favoriteNotifCoordnatorProvider = provider41;
        this.settingsChangedCoordinatorProvider = provider42;
        this.notifHeaderCoordinatorProvider = provider43;
        this.onlyShowNewNotifCoordnatorProvider = provider44;
        this.subscreenQuickReplyCoordinatorProvider = provider45;
        this.subscreenNotificationListCoordinatorProvider = provider46;
        this.notificationControlActionCoordinatorProvider = provider47;
    }

    public static NotifCoordinatorsImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10, javax.inject.Provider provider11, javax.inject.Provider provider12, javax.inject.Provider provider13, javax.inject.Provider provider14, javax.inject.Provider provider15, javax.inject.Provider provider16, javax.inject.Provider provider17, javax.inject.Provider provider18, javax.inject.Provider provider19, javax.inject.Provider provider20, javax.inject.Provider provider21, javax.inject.Provider provider22, javax.inject.Provider provider23, javax.inject.Provider provider24, javax.inject.Provider provider25, javax.inject.Provider provider26, javax.inject.Provider provider27, javax.inject.Provider provider28, javax.inject.Provider provider29, javax.inject.Provider provider30, javax.inject.Provider provider31, javax.inject.Provider provider32, javax.inject.Provider provider33, javax.inject.Provider provider34, javax.inject.Provider provider35, javax.inject.Provider provider36, javax.inject.Provider provider37, javax.inject.Provider provider38, javax.inject.Provider provider39, javax.inject.Provider provider40, javax.inject.Provider provider41, javax.inject.Provider provider42, javax.inject.Provider provider43, javax.inject.Provider provider44, javax.inject.Provider provider45, javax.inject.Provider provider46, javax.inject.Provider provider47) {
        return new NotifCoordinatorsImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18, provider19, provider20, provider21, provider22, provider23, provider24, provider25, provider26, provider27, provider28, provider29, provider30, provider31, provider32, provider33, provider34, provider35, provider36, provider37, provider38, provider39, provider40, provider41, provider42, provider43, provider44, provider45, provider46, provider47);
    }

    public static NotifCoordinatorsImpl newInstance(SectionStyleProvider sectionStyleProvider, FeatureFlags featureFlags, DataStoreCoordinator dataStoreCoordinator, HideLocallyDismissedNotifsCoordinator hideLocallyDismissedNotifsCoordinator, HideNotifsForOtherUsersCoordinator hideNotifsForOtherUsersCoordinator, KeyguardCoordinator keyguardCoordinator, RankingCoordinator rankingCoordinator, ColorizedFgsCoordinator colorizedFgsCoordinator, DeviceProvisionedCoordinator deviceProvisionedCoordinator, BubbleCoordinator bubbleCoordinator, HeadsUpCoordinator headsUpCoordinator, GutsCoordinator gutsCoordinator, ConversationCoordinator conversationCoordinator, DebugModeCoordinator debugModeCoordinator, GroupCountCoordinator groupCountCoordinator, GroupWhenCoordinator groupWhenCoordinator, MediaCoordinator mediaCoordinator, PreparationCoordinator preparationCoordinator, RemoteInputCoordinator remoteInputCoordinator, RowAlertTimeCoordinator rowAlertTimeCoordinator, RowAppearanceCoordinator rowAppearanceCoordinator, StackCoordinator stackCoordinator, ShadeEventCoordinator shadeEventCoordinator, SmartspaceDedupingCoordinator smartspaceDedupingCoordinator, ViewConfigCoordinator viewConfigCoordinator, VisualStabilityCoordinator visualStabilityCoordinator, SensitiveContentCoordinator sensitiveContentCoordinator, DismissibilityCoordinator dismissibilityCoordinator, DreamCoordinator dreamCoordinator, NotificationStatsLoggerCoordinator notificationStatsLoggerCoordinator, SemPriorityCoordinator semPriorityCoordinator, LockScreenNotiIconCoordinator lockScreenNotiIconCoordinator, OngoingActivityCoordinator ongoingActivityCoordinator, EdgeLightingCoordnator edgeLightingCoordnator, NotifTimeSortCoordnator notifTimeSortCoordnator, NotilusCoordinator notilusCoordinator, NotifCounterCoordinator notifCounterCoordinator, HighlightsCoordinator highlightsCoordinator, SummarizeCoordinator summarizeCoordinator, InsignificantCoordinator insignificantCoordinator, FavoriteNotifCoordnator favoriteNotifCoordnator, SettingsChangedCoordinator settingsChangedCoordinator, NotifHeaderCoordinator notifHeaderCoordinator, OnlyShowNewNotifCoordnator onlyShowNewNotifCoordnator, SubscreenQuickReplyCoordinator subscreenQuickReplyCoordinator, SubscreenNotificationListCoordinator subscreenNotificationListCoordinator, NotificationControlActionCoordinator notificationControlActionCoordinator) {
        return new NotifCoordinatorsImpl(sectionStyleProvider, featureFlags, dataStoreCoordinator, hideLocallyDismissedNotifsCoordinator, hideNotifsForOtherUsersCoordinator, keyguardCoordinator, rankingCoordinator, colorizedFgsCoordinator, deviceProvisionedCoordinator, bubbleCoordinator, headsUpCoordinator, gutsCoordinator, conversationCoordinator, debugModeCoordinator, groupCountCoordinator, groupWhenCoordinator, mediaCoordinator, preparationCoordinator, remoteInputCoordinator, rowAlertTimeCoordinator, rowAppearanceCoordinator, stackCoordinator, shadeEventCoordinator, smartspaceDedupingCoordinator, viewConfigCoordinator, visualStabilityCoordinator, sensitiveContentCoordinator, dismissibilityCoordinator, dreamCoordinator, notificationStatsLoggerCoordinator, semPriorityCoordinator, lockScreenNotiIconCoordinator, ongoingActivityCoordinator, edgeLightingCoordnator, notifTimeSortCoordnator, notilusCoordinator, notifCounterCoordinator, highlightsCoordinator, summarizeCoordinator, insignificantCoordinator, favoriteNotifCoordnator, settingsChangedCoordinator, notifHeaderCoordinator, onlyShowNewNotifCoordnator, subscreenQuickReplyCoordinator, subscreenNotificationListCoordinator, notificationControlActionCoordinator);
    }

    @Override // javax.inject.Provider
    public NotifCoordinatorsImpl get() {
        return newInstance((SectionStyleProvider) this.sectionStyleProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (DataStoreCoordinator) this.dataStoreCoordinatorProvider.get(), (HideLocallyDismissedNotifsCoordinator) this.hideLocallyDismissedNotifsCoordinatorProvider.get(), (HideNotifsForOtherUsersCoordinator) this.hideNotifsForOtherUsersCoordinatorProvider.get(), (KeyguardCoordinator) this.keyguardCoordinatorProvider.get(), (RankingCoordinator) this.rankingCoordinatorProvider.get(), (ColorizedFgsCoordinator) this.colorizedFgsCoordinatorProvider.get(), (DeviceProvisionedCoordinator) this.deviceProvisionedCoordinatorProvider.get(), (BubbleCoordinator) this.bubbleCoordinatorProvider.get(), (HeadsUpCoordinator) this.headsUpCoordinatorProvider.get(), (GutsCoordinator) this.gutsCoordinatorProvider.get(), (ConversationCoordinator) this.conversationCoordinatorProvider.get(), (DebugModeCoordinator) this.debugModeCoordinatorProvider.get(), (GroupCountCoordinator) this.groupCountCoordinatorProvider.get(), (GroupWhenCoordinator) this.groupWhenCoordinatorProvider.get(), (MediaCoordinator) this.mediaCoordinatorProvider.get(), (PreparationCoordinator) this.preparationCoordinatorProvider.get(), (RemoteInputCoordinator) this.remoteInputCoordinatorProvider.get(), (RowAlertTimeCoordinator) this.rowAlertTimeCoordinatorProvider.get(), (RowAppearanceCoordinator) this.rowAppearanceCoordinatorProvider.get(), (StackCoordinator) this.stackCoordinatorProvider.get(), (ShadeEventCoordinator) this.shadeEventCoordinatorProvider.get(), (SmartspaceDedupingCoordinator) this.smartspaceDedupingCoordinatorProvider.get(), (ViewConfigCoordinator) this.viewConfigCoordinatorProvider.get(), (VisualStabilityCoordinator) this.visualStabilityCoordinatorProvider.get(), (SensitiveContentCoordinator) this.sensitiveContentCoordinatorProvider.get(), (DismissibilityCoordinator) this.dismissibilityCoordinatorProvider.get(), (DreamCoordinator) this.dreamCoordinatorProvider.get(), (NotificationStatsLoggerCoordinator) this.statsLoggerCoordinatorProvider.get(), (SemPriorityCoordinator) this.semPriorityCoordinatorProvider.get(), (LockScreenNotiIconCoordinator) this.lockScreenNotiIconCoordinatorProvider.get(), (OngoingActivityCoordinator) this.ongoingActivityCoordinatorProvider.get(), (EdgeLightingCoordnator) this.edgeLightingCoordnatorProvider.get(), (NotifTimeSortCoordnator) this.notifTimeSortCoordnatorProvider.get(), (NotilusCoordinator) this.notilusCoordinatorProvider.get(), (NotifCounterCoordinator) this.notifCounterCoordinatorProvider.get(), (HighlightsCoordinator) this.highlightsCoordinatorProvider.get(), (SummarizeCoordinator) this.summarizeCoordinatorProvider.get(), (InsignificantCoordinator) this.insignificantCoordinatorProvider.get(), (FavoriteNotifCoordnator) this.favoriteNotifCoordnatorProvider.get(), (SettingsChangedCoordinator) this.settingsChangedCoordinatorProvider.get(), (NotifHeaderCoordinator) this.notifHeaderCoordinatorProvider.get(), (OnlyShowNewNotifCoordnator) this.onlyShowNewNotifCoordnatorProvider.get(), (SubscreenQuickReplyCoordinator) this.subscreenQuickReplyCoordinatorProvider.get(), (SubscreenNotificationListCoordinator) this.subscreenNotificationListCoordinatorProvider.get(), (NotificationControlActionCoordinator) this.notificationControlActionCoordinatorProvider.get());
    }
}
