package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.NotiRune;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.PipelineDumper;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import com.android.systemui.statusbar.notification.promoted.AutomaticPromotionCoordinator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

@CoordinatorScope
/* loaded from: classes3.dex */
public final class NotifCoordinatorsImpl implements NotifCoordinators {
    private static final String TAG = "NotifCoordinators";
    private final List<Coordinator> mCoordinators;
    private final List<CoreCoordinator> mCoreCoordinators;
    private final List<NotifSectioner> mOrderedSections;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public NotifCoordinatorsImpl(SectionStyleProvider sectionStyleProvider, DataStoreCoordinator dataStoreCoordinator, HideLocallyDismissedNotifsCoordinator hideLocallyDismissedNotifsCoordinator, HideNotifsForOtherUsersCoordinator hideNotifsForOtherUsersCoordinator, KeyguardCoordinator keyguardCoordinator, OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator, LockScreenMinimalismCoordinator lockScreenMinimalismCoordinator, RankingCoordinator rankingCoordinator, ColorizedFgsCoordinator colorizedFgsCoordinator, DeviceProvisionedCoordinator deviceProvisionedCoordinator, BubbleCoordinator bubbleCoordinator, HeadsUpCoordinator headsUpCoordinator, GutsCoordinator gutsCoordinator, ConversationCoordinator conversationCoordinator, DebugModeCoordinator debugModeCoordinator, GroupCountCoordinator groupCountCoordinator, GroupWhenCoordinator groupWhenCoordinator, MediaCoordinator mediaCoordinator, PreparationCoordinator preparationCoordinator, RemoteInputCoordinator remoteInputCoordinator, RowAlertTimeCoordinator rowAlertTimeCoordinator, RowAppearanceCoordinator rowAppearanceCoordinator, StackCoordinator stackCoordinator, ShadeEventCoordinator shadeEventCoordinator, SmartspaceDedupingCoordinator smartspaceDedupingCoordinator, ViewConfigCoordinator viewConfigCoordinator, VisualStabilityCoordinator visualStabilityCoordinator, SensitiveContentCoordinator sensitiveContentCoordinator, DismissibilityCoordinator dismissibilityCoordinator, NotificationStatsLoggerCoordinator notificationStatsLoggerCoordinator, BundleCoordinator bundleCoordinator, AutomaticPromotionCoordinator automaticPromotionCoordinator, SemPriorityCoordinator semPriorityCoordinator, LockScreenNotiIconCoordinator lockScreenNotiIconCoordinator, OngoingActivityCoordinator ongoingActivityCoordinator, EdgeLightingCoordnator edgeLightingCoordnator, NotifTimeSortCoordnator notifTimeSortCoordnator, NotilusCoordinator notilusCoordinator, NotifCounterCoordinator notifCounterCoordinator, InsignificantCoordinator insignificantCoordinator, FavoriteNotifCoordnator favoriteNotifCoordnator, SettingsChangedCoordinator settingsChangedCoordinator, NotifHeaderCoordinator notifHeaderCoordinator, OnlyShowNewNotifCoordnator onlyShowNewNotifCoordnator, SubscreenQuickReplyCoordinator subscreenQuickReplyCoordinator, SubscreenNotificationListCoordinator subscreenNotificationListCoordinator, NotificationControlActionCoordinator notificationControlActionCoordinator, VoipCallPopUpCoordinator voipCallPopUpCoordinator) {
        ArrayList arrayList = new ArrayList();
        this.mCoreCoordinators = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.mCoordinators = arrayList2;
        ArrayList arrayList3 = new ArrayList();
        this.mOrderedSections = arrayList3;
        arrayList.add(dataStoreCoordinator);
        arrayList2.add(hideLocallyDismissedNotifsCoordinator);
        arrayList2.add(hideNotifsForOtherUsersCoordinator);
        arrayList2.add(keyguardCoordinator);
        arrayList2.add(originalUnseenKeyguardCoordinator);
        arrayList2.add(rankingCoordinator);
        arrayList2.add(colorizedFgsCoordinator);
        arrayList2.add(deviceProvisionedCoordinator);
        arrayList2.add(bubbleCoordinator);
        arrayList2.add(debugModeCoordinator);
        arrayList2.add(conversationCoordinator);
        arrayList2.add(groupCountCoordinator);
        arrayList2.add(groupWhenCoordinator);
        arrayList2.add(mediaCoordinator);
        arrayList2.add(rowAlertTimeCoordinator);
        arrayList2.add(rowAppearanceCoordinator);
        arrayList2.add(stackCoordinator);
        arrayList2.add(shadeEventCoordinator);
        arrayList2.add(viewConfigCoordinator);
        arrayList2.add(visualStabilityCoordinator);
        arrayList2.add(sensitiveContentCoordinator);
        arrayList2.add(smartspaceDedupingCoordinator);
        arrayList2.add(headsUpCoordinator);
        arrayList2.add(gutsCoordinator);
        arrayList2.add(preparationCoordinator);
        arrayList2.add(remoteInputCoordinator);
        arrayList2.add(dismissibilityCoordinator);
        arrayList2.add(automaticPromotionCoordinator);
        arrayList2.add(notificationStatsLoggerCoordinator);
        arrayList2.add(lockScreenNotiIconCoordinator);
        arrayList2.add(edgeLightingCoordnator);
        arrayList2.add(notifTimeSortCoordnator);
        arrayList2.add(notilusCoordinator);
        arrayList2.add(favoriteNotifCoordnator);
        arrayList3.add(favoriteNotifCoordnator.getFavoriteSectioner());
        arrayList2.add(ongoingActivityCoordinator);
        arrayList3.add(ongoingActivityCoordinator.getOngoingActivitySectioner());
        arrayList2.add(voipCallPopUpCoordinator);
        arrayList3.add(notifTimeSortCoordnator.getSectionerForPriority());
        arrayList3.add(notifTimeSortCoordnator.getSectioner());
        arrayList2.add(notifCounterCoordinator);
        arrayList2.add(notifHeaderCoordinator);
        arrayList2.add(settingsChangedCoordinator);
        arrayList2.add(onlyShowNewNotifCoordnator);
        if (NotiRune.NOTI_SUBSCREEN_GHOST_NOTIFICATION) {
            arrayList2.add(subscreenQuickReplyCoordinator);
        }
        if (NotiRune.NOTI_SUBSCREEN_ALL) {
            arrayList2.add(subscreenNotificationListCoordinator);
        }
        arrayList2.add(notificationControlActionCoordinator);
        arrayList3.add(headsUpCoordinator.getSectioner());
        arrayList3.add(semPriorityCoordinator.getSectioner());
        arrayList3.add(colorizedFgsCoordinator.getSectioner());
        arrayList3.add(conversationCoordinator.getPriorityPeopleSectioner());
        arrayList3.add(conversationCoordinator.getPeopleAlertingSectioner());
        arrayList3.add(rankingCoordinator.getAlertingSectioner());
        arrayList3.add(bundleCoordinator.getNewsSectioner());
        arrayList3.add(bundleCoordinator.getSocialSectioner());
        arrayList3.add(bundleCoordinator.getRecsSectioner());
        arrayList3.add(bundleCoordinator.getPromoSectioner());
        arrayList2.add(insignificantCoordinator);
        arrayList3.add(insignificantCoordinator.getInsignificantSectioner());
        arrayList3.add(rankingCoordinator.getMinimizedSectioner());
        Set setSingleton = Collections.singleton(rankingCoordinator.getMinimizedSectioner());
        sectionStyleProvider.getClass();
        sectionStyleProvider.lowPrioritySections = CollectionsKt___CollectionsKt.toSet(setSingleton);
        sectionStyleProvider.silentSections = CollectionsKt___CollectionsKt.toSet(Collections.singleton(insignificantCoordinator.getInsignificantSectioner()));
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        Iterator<CoreCoordinator> it = this.mCoreCoordinators.iterator();
        while (it.hasNext()) {
            it.next().attach(notifPipeline);
        }
        Iterator<Coordinator> it2 = this.mCoordinators.iterator();
        while (it2.hasNext()) {
            it2.next().attach(notifPipeline);
        }
        notifPipeline.mShadeListBuilder.setSectioners(this.mOrderedSections);
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators, com.android.systemui.statusbar.notification.collection.PipelineDumpable
    public void dumpPipeline(PipelineDumper pipelineDumper) {
        pipelineDumper.dump(this.mCoreCoordinators, "core coordinators");
        pipelineDumper.dump(this.mCoordinators, "normal coordinators");
    }
}
