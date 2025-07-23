package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationChannel;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.notifcollection.UpdateSource;
import com.android.systemui.statusbar.notification.collection.provider.LaunchFullScreenIntentProvider;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderWrapper;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import com.android.systemui.util.time.SystemClock;
import java.util.LinkedHashMap;
import java.util.function.BiFunction;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HeadsUpCoordinator$mNotifCollectionListener$1 implements NotifCollectionListener {
    final /* synthetic */ HeadsUpCoordinator this$0;

    public HeadsUpCoordinator$mNotifCollectionListener$1(HeadsUpCoordinator headsUpCoordinator) {
        this.this$0 = headsUpCoordinator;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final HeadsUpCoordinator.PostedEntry onEntryUpdated$lambda$1(NotificationEntry notificationEntry, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, String str, HeadsUpCoordinator.PostedEntry postedEntry) {
        if (postedEntry == null) {
            return new HeadsUpCoordinator.PostedEntry(notificationEntry, false, true, z || z2, z3, false, z4, z5, 32, null);
        }
        postedEntry.setWasUpdated(true);
        postedEntry.setShouldHeadsUpEver(postedEntry.getShouldHeadsUpEver() || z || z2);
        postedEntry.setShouldHeadsUpAgain(postedEntry.getShouldHeadsUpAgain() || z3);
        postedEntry.setHeadsUpEntry(z4);
        postedEntry.setBinding(z5);
        return postedEntry;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onEntryAdded(NotificationEntry notificationEntry) {
        VisualInterruptionDecisionProvider visualInterruptionDecisionProvider;
        VisualInterruptionDecisionProvider visualInterruptionDecisionProvider2;
        SystemClock systemClock;
        VisualInterruptionDecisionProvider visualInterruptionDecisionProvider3;
        LinkedHashMap linkedHashMap;
        SystemClock systemClock2;
        LaunchFullScreenIntentProvider launchFullScreenIntentProvider;
        visualInterruptionDecisionProvider = this.this$0.mVisualInterruptionDecisionProvider;
        NotificationInterruptStateProviderWrapper.FullScreenIntentDecisionImpl makeUnloggedFullScreenIntentDecision = visualInterruptionDecisionProvider.makeUnloggedFullScreenIntentDecision(notificationEntry);
        visualInterruptionDecisionProvider2 = this.this$0.mVisualInterruptionDecisionProvider;
        visualInterruptionDecisionProvider2.logFullScreenIntentDecision(makeUnloggedFullScreenIntentDecision);
        if (makeUnloggedFullScreenIntentDecision.getShouldInterrupt()) {
            launchFullScreenIntentProvider = this.this$0.mLaunchFullScreenIntentProvider;
            launchFullScreenIntentProvider.launchFullScreenIntent(notificationEntry);
        } else if (makeUnloggedFullScreenIntentDecision.getWouldInterruptWithoutDnd()) {
            HeadsUpCoordinator headsUpCoordinator = this.this$0;
            systemClock = headsUpCoordinator.mSystemClock;
            headsUpCoordinator.addForFSIReconsideration(notificationEntry, systemClock.currentTimeMillis());
        }
        visualInterruptionDecisionProvider3 = this.this$0.mVisualInterruptionDecisionProvider;
        boolean shouldInterrupt = visualInterruptionDecisionProvider3.makeAndLogHeadsUpDecision(notificationEntry).getShouldInterrupt();
        linkedHashMap = this.this$0.mPostedEntries;
        linkedHashMap.put(notificationEntry.mKey, new HeadsUpCoordinator.PostedEntry(notificationEntry, true, false, shouldInterrupt, true, false, false, false, 32, null));
        HeadsUpCoordinator headsUpCoordinator2 = this.this$0;
        systemClock2 = headsUpCoordinator2.mSystemClock;
        headsUpCoordinator2.setUpdateTime(notificationEntry, systemClock2.currentTimeMillis());
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onEntryCleanUp(NotificationEntry notificationEntry) {
        HeadsUpViewBinder headsUpViewBinder;
        headsUpViewBinder = this.this$0.mHeadsUpViewBinder;
        headsUpViewBinder.abortBindCallback(notificationEntry);
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onEntryRemoved(NotificationEntry notificationEntry, int i) {
        LinkedHashMap linkedHashMap;
        ArrayMap arrayMap;
        HeadsUpManager headsUpManager;
        NotificationRemoteInputManager notificationRemoteInputManager;
        HeadsUpManager headsUpManager2;
        linkedHashMap = this.this$0.mPostedEntries;
        linkedHashMap.remove(notificationEntry.mKey);
        arrayMap = this.this$0.mEntriesUpdateTimes;
        String str = notificationEntry.mKey;
        arrayMap.remove(str);
        this.this$0.cancelHeadsUpBind(notificationEntry);
        headsUpManager = this.this$0.mHeadsUpManager;
        if (((HeadsUpManagerImpl) headsUpManager).isHeadsUpEntry(str)) {
            notificationRemoteInputManager = this.this$0.mRemoteInputManager;
            boolean z = notificationRemoteInputManager.isSpinning(str) && !NotificationRemoteInputManager.FORCE_REMOTE_INPUT_HISTORY;
            headsUpManager2 = this.this$0.mHeadsUpManager;
            ((HeadsUpManagerImpl) headsUpManager2).removeNotification(str, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "onEntryRemoved, reason: "), z);
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onEntryUpdated(NotificationEntry notificationEntry, UpdateSource updateSource) {
        onEntryUpdated(notificationEntry);
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onRankingApplied() {
        NotifPipeline notifPipeline;
        boolean isNewEnoughForRankingUpdate;
        boolean isCandidateForFSIReconsideration;
        VisualInterruptionDecisionProvider visualInterruptionDecisionProvider;
        LinkedHashMap linkedHashMap;
        HeadsUpCoordinatorLogger headsUpCoordinatorLogger;
        VisualInterruptionDecisionProvider visualInterruptionDecisionProvider2;
        HeadsUpCoordinatorLogger headsUpCoordinatorLogger2;
        VisualInterruptionDecisionProvider visualInterruptionDecisionProvider3;
        LaunchFullScreenIntentProvider launchFullScreenIntentProvider;
        ArrayMap arrayMap;
        HeadsUpCoordinatorLogger headsUpCoordinatorLogger3;
        VisualInterruptionDecisionProvider visualInterruptionDecisionProvider4;
        ArrayMap arrayMap2;
        notifPipeline = this.this$0.mNotifPipeline;
        if (notifPipeline == null) {
            notifPipeline = null;
        }
        for (NotificationEntry notificationEntry : notifPipeline.getAllNotifs()) {
            isNewEnoughForRankingUpdate = this.this$0.isNewEnoughForRankingUpdate(notificationEntry);
            if (isNewEnoughForRankingUpdate && !notificationEntry.interruption) {
                isCandidateForFSIReconsideration = this.this$0.isCandidateForFSIReconsideration(notificationEntry);
                String str = notificationEntry.mKey;
                if (isCandidateForFSIReconsideration) {
                    visualInterruptionDecisionProvider2 = this.this$0.mVisualInterruptionDecisionProvider;
                    NotificationInterruptStateProviderWrapper.FullScreenIntentDecisionImpl makeUnloggedFullScreenIntentDecision = visualInterruptionDecisionProvider2.makeUnloggedFullScreenIntentDecision(notificationEntry);
                    if (makeUnloggedFullScreenIntentDecision.getShouldInterrupt()) {
                        headsUpCoordinatorLogger2 = this.this$0.mLogger;
                        headsUpCoordinatorLogger2.logEntryUpdatedToFullScreen(str, makeUnloggedFullScreenIntentDecision.getLogReason());
                        visualInterruptionDecisionProvider3 = this.this$0.mVisualInterruptionDecisionProvider;
                        visualInterruptionDecisionProvider3.logFullScreenIntentDecision(makeUnloggedFullScreenIntentDecision);
                        launchFullScreenIntentProvider = this.this$0.mLaunchFullScreenIntentProvider;
                        launchFullScreenIntentProvider.launchFullScreenIntent(notificationEntry);
                        arrayMap = this.this$0.mFSIUpdateCandidates;
                        arrayMap.remove(str);
                    } else if (!makeUnloggedFullScreenIntentDecision.getWouldInterruptWithoutDnd()) {
                        headsUpCoordinatorLogger3 = this.this$0.mLogger;
                        headsUpCoordinatorLogger3.logEntryDisqualifiedFromFullScreen(str, makeUnloggedFullScreenIntentDecision.getLogReason());
                        visualInterruptionDecisionProvider4 = this.this$0.mVisualInterruptionDecisionProvider;
                        visualInterruptionDecisionProvider4.logFullScreenIntentDecision(makeUnloggedFullScreenIntentDecision);
                        arrayMap2 = this.this$0.mFSIUpdateCandidates;
                        arrayMap2.remove(str);
                    }
                }
                visualInterruptionDecisionProvider = this.this$0.mVisualInterruptionDecisionProvider;
                VisualInterruptionDecisionProvider.Decision makeUnloggedHeadsUpDecision = visualInterruptionDecisionProvider.makeUnloggedHeadsUpDecision(notificationEntry);
                boolean shouldInterrupt = makeUnloggedHeadsUpDecision.getShouldInterrupt();
                linkedHashMap = this.this$0.mPostedEntries;
                HeadsUpCoordinator.PostedEntry postedEntry = (HeadsUpCoordinator.PostedEntry) linkedHashMap.get(str);
                if ((postedEntry != null ? postedEntry.getShouldHeadsUpEver() : false) != shouldInterrupt) {
                    headsUpCoordinatorLogger = this.this$0.mLogger;
                    headsUpCoordinatorLogger.logEntryUpdatedByRanking(str, shouldInterrupt, makeUnloggedHeadsUpDecision.getLogReason());
                    onEntryUpdated(notificationEntry);
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onEntryUpdated(final NotificationEntry notificationEntry) {
        VisualInterruptionDecisionProvider visualInterruptionDecisionProvider;
        final boolean shouldHunAgain;
        HeadsUpManager headsUpManager;
        final boolean isEntryBinding;
        LinkedHashMap linkedHashMap;
        SystemClock systemClock;
        HeadsUpManager headsUpManager2;
        visualInterruptionDecisionProvider = this.this$0.mVisualInterruptionDecisionProvider;
        final boolean shouldInterrupt = visualInterruptionDecisionProvider.makeAndLogHeadsUpDecision(notificationEntry).getShouldInterrupt();
        shouldHunAgain = this.this$0.shouldHunAgain(notificationEntry);
        headsUpManager = this.this$0.mHeadsUpManager;
        final boolean isHeadsUpEntry = ((HeadsUpManagerImpl) headsUpManager).isHeadsUpEntry(notificationEntry.mKey);
        isEntryBinding = this.this$0.isEntryBinding(notificationEntry);
        final boolean z = notificationEntry.mIsHeadsUpByBriefExpanding;
        linkedHashMap = this.this$0.mPostedEntries;
        final Function2 function2 = new Function2() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mNotifCollectionListener$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                HeadsUpCoordinator.PostedEntry onEntryUpdated$lambda$1;
                boolean z2 = isHeadsUpEntry;
                boolean z3 = isEntryBinding;
                onEntryUpdated$lambda$1 = HeadsUpCoordinator$mNotifCollectionListener$1.onEntryUpdated$lambda$1(NotificationEntry.this, shouldInterrupt, z, shouldHunAgain, z2, z3, (String) obj, (HeadsUpCoordinator.PostedEntry) obj2);
                return onEntryUpdated$lambda$1;
            }
        };
        HeadsUpCoordinator.PostedEntry postedEntry = (HeadsUpCoordinator.PostedEntry) linkedHashMap.compute(notificationEntry.mKey, new BiFunction() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorKt$sam$java_util_function_BiFunction$0
            @Override // java.util.function.BiFunction
            public final /* synthetic */ Object apply(Object obj, Object obj2) {
                return Function2.this.invoke(obj, obj2);
            }
        });
        if (postedEntry != null && !postedEntry.getShouldHeadsUpEver()) {
            if (postedEntry.isHeadsUpEntry()) {
                headsUpManager2 = this.this$0.mHeadsUpManager;
                ((HeadsUpManagerImpl) headsUpManager2).removeNotification(postedEntry.getKey(), "onEntryUpdated", false);
            } else if (postedEntry.isBinding()) {
                this.this$0.cancelHeadsUpBind(postedEntry.getEntry());
            }
        }
        HeadsUpCoordinator headsUpCoordinator = this.this$0;
        systemClock = headsUpCoordinator.mSystemClock;
        headsUpCoordinator.setUpdateTime(notificationEntry, systemClock.currentTimeMillis());
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public /* bridge */ /* synthetic */ void onEntryInit(NotificationEntry notificationEntry) {
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    @Deprecated
    public /* bridge */ /* synthetic */ void onRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public /* bridge */ /* synthetic */ void onEntryBind(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification) {
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public /* bridge */ /* synthetic */ void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
    }
}
