package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import com.android.systemui.statusbar.notification.promoted.AutomaticPromotionCoordinator;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotifCoordinatorsImpl_Factory implements Provider {
    private final Provider automaticPromotionCoordinatorProvider;
    private final Provider bubbleCoordinatorProvider;
    private final Provider bundleCoordinatorProvider;
    private final Provider colorizedFgsCoordinatorProvider;
    private final Provider conversationCoordinatorProvider;
    private final Provider dataStoreCoordinatorProvider;
    private final Provider debugModeCoordinatorProvider;
    private final Provider deviceProvisionedCoordinatorProvider;
    private final Provider dismissibilityCoordinatorProvider;
    private final Provider edgeLightingCoordnatorProvider;
    private final Provider favoriteNotifCoordnatorProvider;
    private final Provider groupCountCoordinatorProvider;
    private final Provider groupWhenCoordinatorProvider;
    private final Provider gutsCoordinatorProvider;
    private final Provider headsUpCoordinatorProvider;
    private final Provider hideLocallyDismissedNotifsCoordinatorProvider;
    private final Provider hideNotifsForOtherUsersCoordinatorProvider;
    private final Provider insignificantCoordinatorProvider;
    private final Provider keyguardCoordinatorProvider;
    private final Provider lockScreenMinimalismCoordinatorProvider;
    private final Provider lockScreenNotiIconCoordinatorProvider;
    private final Provider mediaCoordinatorProvider;
    private final Provider notifCounterCoordinatorProvider;
    private final Provider notifHeaderCoordinatorProvider;
    private final Provider notifTimeSortCoordnatorProvider;
    private final Provider notificationControlActionCoordinatorProvider;
    private final Provider notilusCoordinatorProvider;
    private final Provider ongoingActivityCoordinatorProvider;
    private final Provider onlyShowNewNotifCoordnatorProvider;
    private final Provider preparationCoordinatorProvider;
    private final Provider rankingCoordinatorProvider;
    private final Provider remoteInputCoordinatorProvider;
    private final Provider rowAlertTimeCoordinatorProvider;
    private final Provider rowAppearanceCoordinatorProvider;
    private final Provider sectionStyleProvider;
    private final Provider semPriorityCoordinatorProvider;
    private final Provider sensitiveContentCoordinatorProvider;
    private final Provider settingsChangedCoordinatorProvider;
    private final Provider shadeEventCoordinatorProvider;
    private final Provider smartspaceDedupingCoordinatorProvider;
    private final Provider stackCoordinatorProvider;
    private final Provider statsLoggerCoordinatorProvider;
    private final Provider subscreenNotificationListCoordinatorProvider;
    private final Provider subscreenQuickReplyCoordinatorProvider;
    private final Provider unseenKeyguardCoordinatorProvider;
    private final Provider viewConfigCoordinatorProvider;
    private final Provider visualStabilityCoordinatorProvider;
    private final Provider voipCallPopUpCoordinatorProvider;

    public NotifCoordinatorsImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30, Provider provider31, Provider provider32, Provider provider33, Provider provider34, Provider provider35, Provider provider36, Provider provider37, Provider provider38, Provider provider39, Provider provider40, Provider provider41, Provider provider42, Provider provider43, Provider provider44, Provider provider45, Provider provider46, Provider provider47, Provider provider48) {
        this.sectionStyleProvider = provider;
        this.dataStoreCoordinatorProvider = provider2;
        this.hideLocallyDismissedNotifsCoordinatorProvider = provider3;
        this.hideNotifsForOtherUsersCoordinatorProvider = provider4;
        this.keyguardCoordinatorProvider = provider5;
        this.unseenKeyguardCoordinatorProvider = provider6;
        this.lockScreenMinimalismCoordinatorProvider = provider7;
        this.rankingCoordinatorProvider = provider8;
        this.colorizedFgsCoordinatorProvider = provider9;
        this.deviceProvisionedCoordinatorProvider = provider10;
        this.bubbleCoordinatorProvider = provider11;
        this.headsUpCoordinatorProvider = provider12;
        this.gutsCoordinatorProvider = provider13;
        this.conversationCoordinatorProvider = provider14;
        this.debugModeCoordinatorProvider = provider15;
        this.groupCountCoordinatorProvider = provider16;
        this.groupWhenCoordinatorProvider = provider17;
        this.mediaCoordinatorProvider = provider18;
        this.preparationCoordinatorProvider = provider19;
        this.remoteInputCoordinatorProvider = provider20;
        this.rowAlertTimeCoordinatorProvider = provider21;
        this.rowAppearanceCoordinatorProvider = provider22;
        this.stackCoordinatorProvider = provider23;
        this.shadeEventCoordinatorProvider = provider24;
        this.smartspaceDedupingCoordinatorProvider = provider25;
        this.viewConfigCoordinatorProvider = provider26;
        this.visualStabilityCoordinatorProvider = provider27;
        this.sensitiveContentCoordinatorProvider = provider28;
        this.dismissibilityCoordinatorProvider = provider29;
        this.statsLoggerCoordinatorProvider = provider30;
        this.bundleCoordinatorProvider = provider31;
        this.automaticPromotionCoordinatorProvider = provider32;
        this.semPriorityCoordinatorProvider = provider33;
        this.lockScreenNotiIconCoordinatorProvider = provider34;
        this.ongoingActivityCoordinatorProvider = provider35;
        this.edgeLightingCoordnatorProvider = provider36;
        this.notifTimeSortCoordnatorProvider = provider37;
        this.notilusCoordinatorProvider = provider38;
        this.notifCounterCoordinatorProvider = provider39;
        this.insignificantCoordinatorProvider = provider40;
        this.favoriteNotifCoordnatorProvider = provider41;
        this.settingsChangedCoordinatorProvider = provider42;
        this.notifHeaderCoordinatorProvider = provider43;
        this.onlyShowNewNotifCoordnatorProvider = provider44;
        this.subscreenQuickReplyCoordinatorProvider = provider45;
        this.subscreenNotificationListCoordinatorProvider = provider46;
        this.notificationControlActionCoordinatorProvider = provider47;
        this.voipCallPopUpCoordinatorProvider = provider48;
    }

    public static NotifCoordinatorsImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10, javax.inject.Provider provider11, javax.inject.Provider provider12, javax.inject.Provider provider13, javax.inject.Provider provider14, javax.inject.Provider provider15, javax.inject.Provider provider16, javax.inject.Provider provider17, javax.inject.Provider provider18, javax.inject.Provider provider19, javax.inject.Provider provider20, javax.inject.Provider provider21, javax.inject.Provider provider22, javax.inject.Provider provider23, javax.inject.Provider provider24, javax.inject.Provider provider25, javax.inject.Provider provider26, javax.inject.Provider provider27, javax.inject.Provider provider28, javax.inject.Provider provider29, javax.inject.Provider provider30, javax.inject.Provider provider31, javax.inject.Provider provider32, javax.inject.Provider provider33, javax.inject.Provider provider34, javax.inject.Provider provider35, javax.inject.Provider provider36, javax.inject.Provider provider37, javax.inject.Provider provider38, javax.inject.Provider provider39, javax.inject.Provider provider40, javax.inject.Provider provider41, javax.inject.Provider provider42, javax.inject.Provider provider43, javax.inject.Provider provider44, javax.inject.Provider provider45, javax.inject.Provider provider46, javax.inject.Provider provider47, javax.inject.Provider provider48) {
        return new NotifCoordinatorsImpl_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5), Providers.asDaggerProvider(provider6), Providers.asDaggerProvider(provider7), Providers.asDaggerProvider(provider8), Providers.asDaggerProvider(provider9), Providers.asDaggerProvider(provider10), Providers.asDaggerProvider(provider11), Providers.asDaggerProvider(provider12), Providers.asDaggerProvider(provider13), Providers.asDaggerProvider(provider14), Providers.asDaggerProvider(provider15), Providers.asDaggerProvider(provider16), Providers.asDaggerProvider(provider17), Providers.asDaggerProvider(provider18), Providers.asDaggerProvider(provider19), Providers.asDaggerProvider(provider20), Providers.asDaggerProvider(provider21), Providers.asDaggerProvider(provider22), Providers.asDaggerProvider(provider23), Providers.asDaggerProvider(provider24), Providers.asDaggerProvider(provider25), Providers.asDaggerProvider(provider26), Providers.asDaggerProvider(provider27), Providers.asDaggerProvider(provider28), Providers.asDaggerProvider(provider29), Providers.asDaggerProvider(provider30), Providers.asDaggerProvider(provider31), Providers.asDaggerProvider(provider32), Providers.asDaggerProvider(provider33), Providers.asDaggerProvider(provider34), Providers.asDaggerProvider(provider35), Providers.asDaggerProvider(provider36), Providers.asDaggerProvider(provider37), Providers.asDaggerProvider(provider38), Providers.asDaggerProvider(provider39), Providers.asDaggerProvider(provider40), Providers.asDaggerProvider(provider41), Providers.asDaggerProvider(provider42), Providers.asDaggerProvider(provider43), Providers.asDaggerProvider(provider44), Providers.asDaggerProvider(provider45), Providers.asDaggerProvider(provider46), Providers.asDaggerProvider(provider47), Providers.asDaggerProvider(provider48));
    }

    public static NotifCoordinatorsImpl newInstance(SectionStyleProvider sectionStyleProvider, DataStoreCoordinator dataStoreCoordinator, HideLocallyDismissedNotifsCoordinator hideLocallyDismissedNotifsCoordinator, HideNotifsForOtherUsersCoordinator hideNotifsForOtherUsersCoordinator, KeyguardCoordinator keyguardCoordinator, OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator, LockScreenMinimalismCoordinator lockScreenMinimalismCoordinator, RankingCoordinator rankingCoordinator, ColorizedFgsCoordinator colorizedFgsCoordinator, DeviceProvisionedCoordinator deviceProvisionedCoordinator, BubbleCoordinator bubbleCoordinator, HeadsUpCoordinator headsUpCoordinator, GutsCoordinator gutsCoordinator, ConversationCoordinator conversationCoordinator, DebugModeCoordinator debugModeCoordinator, GroupCountCoordinator groupCountCoordinator, GroupWhenCoordinator groupWhenCoordinator, MediaCoordinator mediaCoordinator, PreparationCoordinator preparationCoordinator, RemoteInputCoordinator remoteInputCoordinator, RowAlertTimeCoordinator rowAlertTimeCoordinator, RowAppearanceCoordinator rowAppearanceCoordinator, StackCoordinator stackCoordinator, ShadeEventCoordinator shadeEventCoordinator, SmartspaceDedupingCoordinator smartspaceDedupingCoordinator, ViewConfigCoordinator viewConfigCoordinator, VisualStabilityCoordinator visualStabilityCoordinator, SensitiveContentCoordinator sensitiveContentCoordinator, DismissibilityCoordinator dismissibilityCoordinator, NotificationStatsLoggerCoordinator notificationStatsLoggerCoordinator, BundleCoordinator bundleCoordinator, AutomaticPromotionCoordinator automaticPromotionCoordinator, SemPriorityCoordinator semPriorityCoordinator, LockScreenNotiIconCoordinator lockScreenNotiIconCoordinator, OngoingActivityCoordinator ongoingActivityCoordinator, EdgeLightingCoordnator edgeLightingCoordnator, NotifTimeSortCoordnator notifTimeSortCoordnator, NotilusCoordinator notilusCoordinator, NotifCounterCoordinator notifCounterCoordinator, InsignificantCoordinator insignificantCoordinator, FavoriteNotifCoordnator favoriteNotifCoordnator, SettingsChangedCoordinator settingsChangedCoordinator, NotifHeaderCoordinator notifHeaderCoordinator, OnlyShowNewNotifCoordnator onlyShowNewNotifCoordnator, SubscreenQuickReplyCoordinator subscreenQuickReplyCoordinator, SubscreenNotificationListCoordinator subscreenNotificationListCoordinator, NotificationControlActionCoordinator notificationControlActionCoordinator, VoipCallPopUpCoordinator voipCallPopUpCoordinator) {
        return new NotifCoordinatorsImpl(sectionStyleProvider, dataStoreCoordinator, hideLocallyDismissedNotifsCoordinator, hideNotifsForOtherUsersCoordinator, keyguardCoordinator, originalUnseenKeyguardCoordinator, lockScreenMinimalismCoordinator, rankingCoordinator, colorizedFgsCoordinator, deviceProvisionedCoordinator, bubbleCoordinator, headsUpCoordinator, gutsCoordinator, conversationCoordinator, debugModeCoordinator, groupCountCoordinator, groupWhenCoordinator, mediaCoordinator, preparationCoordinator, remoteInputCoordinator, rowAlertTimeCoordinator, rowAppearanceCoordinator, stackCoordinator, shadeEventCoordinator, smartspaceDedupingCoordinator, viewConfigCoordinator, visualStabilityCoordinator, sensitiveContentCoordinator, dismissibilityCoordinator, notificationStatsLoggerCoordinator, bundleCoordinator, automaticPromotionCoordinator, semPriorityCoordinator, lockScreenNotiIconCoordinator, ongoingActivityCoordinator, edgeLightingCoordnator, notifTimeSortCoordnator, notilusCoordinator, notifCounterCoordinator, insignificantCoordinator, favoriteNotifCoordnator, settingsChangedCoordinator, notifHeaderCoordinator, onlyShowNewNotifCoordnator, subscreenQuickReplyCoordinator, subscreenNotificationListCoordinator, notificationControlActionCoordinator, voipCallPopUpCoordinator);
    }

    public static NotifCoordinatorsImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30, Provider provider31, Provider provider32, Provider provider33, Provider provider34, Provider provider35, Provider provider36, Provider provider37, Provider provider38, Provider provider39, Provider provider40, Provider provider41, Provider provider42, Provider provider43, Provider provider44, Provider provider45, Provider provider46, Provider provider47, Provider provider48) {
        return new NotifCoordinatorsImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18, provider19, provider20, provider21, provider22, provider23, provider24, provider25, provider26, provider27, provider28, provider29, provider30, provider31, provider32, provider33, provider34, provider35, provider36, provider37, provider38, provider39, provider40, provider41, provider42, provider43, provider44, provider45, provider46, provider47, provider48);
    }

    @Override // javax.inject.Provider
    public NotifCoordinatorsImpl get() {
        return newInstance((SectionStyleProvider) this.sectionStyleProvider.get(), (DataStoreCoordinator) this.dataStoreCoordinatorProvider.get(), (HideLocallyDismissedNotifsCoordinator) this.hideLocallyDismissedNotifsCoordinatorProvider.get(), (HideNotifsForOtherUsersCoordinator) this.hideNotifsForOtherUsersCoordinatorProvider.get(), (KeyguardCoordinator) this.keyguardCoordinatorProvider.get(), (OriginalUnseenKeyguardCoordinator) this.unseenKeyguardCoordinatorProvider.get(), (LockScreenMinimalismCoordinator) this.lockScreenMinimalismCoordinatorProvider.get(), (RankingCoordinator) this.rankingCoordinatorProvider.get(), (ColorizedFgsCoordinator) this.colorizedFgsCoordinatorProvider.get(), (DeviceProvisionedCoordinator) this.deviceProvisionedCoordinatorProvider.get(), (BubbleCoordinator) this.bubbleCoordinatorProvider.get(), (HeadsUpCoordinator) this.headsUpCoordinatorProvider.get(), (GutsCoordinator) this.gutsCoordinatorProvider.get(), (ConversationCoordinator) this.conversationCoordinatorProvider.get(), (DebugModeCoordinator) this.debugModeCoordinatorProvider.get(), (GroupCountCoordinator) this.groupCountCoordinatorProvider.get(), (GroupWhenCoordinator) this.groupWhenCoordinatorProvider.get(), (MediaCoordinator) this.mediaCoordinatorProvider.get(), (PreparationCoordinator) this.preparationCoordinatorProvider.get(), (RemoteInputCoordinator) this.remoteInputCoordinatorProvider.get(), (RowAlertTimeCoordinator) this.rowAlertTimeCoordinatorProvider.get(), (RowAppearanceCoordinator) this.rowAppearanceCoordinatorProvider.get(), (StackCoordinator) this.stackCoordinatorProvider.get(), (ShadeEventCoordinator) this.shadeEventCoordinatorProvider.get(), (SmartspaceDedupingCoordinator) this.smartspaceDedupingCoordinatorProvider.get(), (ViewConfigCoordinator) this.viewConfigCoordinatorProvider.get(), (VisualStabilityCoordinator) this.visualStabilityCoordinatorProvider.get(), (SensitiveContentCoordinator) this.sensitiveContentCoordinatorProvider.get(), (DismissibilityCoordinator) this.dismissibilityCoordinatorProvider.get(), (NotificationStatsLoggerCoordinator) this.statsLoggerCoordinatorProvider.get(), (BundleCoordinator) this.bundleCoordinatorProvider.get(), (AutomaticPromotionCoordinator) this.automaticPromotionCoordinatorProvider.get(), (SemPriorityCoordinator) this.semPriorityCoordinatorProvider.get(), (LockScreenNotiIconCoordinator) this.lockScreenNotiIconCoordinatorProvider.get(), (OngoingActivityCoordinator) this.ongoingActivityCoordinatorProvider.get(), (EdgeLightingCoordnator) this.edgeLightingCoordnatorProvider.get(), (NotifTimeSortCoordnator) this.notifTimeSortCoordnatorProvider.get(), (NotilusCoordinator) this.notilusCoordinatorProvider.get(), (NotifCounterCoordinator) this.notifCounterCoordinatorProvider.get(), (InsignificantCoordinator) this.insignificantCoordinatorProvider.get(), (FavoriteNotifCoordnator) this.favoriteNotifCoordnatorProvider.get(), (SettingsChangedCoordinator) this.settingsChangedCoordinatorProvider.get(), (NotifHeaderCoordinator) this.notifHeaderCoordinatorProvider.get(), (OnlyShowNewNotifCoordnator) this.onlyShowNewNotifCoordnatorProvider.get(), (SubscreenQuickReplyCoordinator) this.subscreenQuickReplyCoordinatorProvider.get(), (SubscreenNotificationListCoordinator) this.subscreenNotificationListCoordinatorProvider.get(), (NotificationControlActionCoordinator) this.notificationControlActionCoordinatorProvider.get(), (VoipCallPopUpCoordinator) this.voipCallPopUpCoordinatorProvider.get());
    }
}
