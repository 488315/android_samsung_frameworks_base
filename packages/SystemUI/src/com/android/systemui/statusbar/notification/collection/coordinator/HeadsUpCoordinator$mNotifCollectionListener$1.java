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
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderWrapper;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import java.util.LinkedHashMap;
import java.util.function.BiFunction;
import kotlin.jvm.functions.Function2;

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
        NotificationInterruptStateProviderWrapper.FullScreenIntentDecisionImpl fullScreenIntentDecisionImplMakeUnloggedFullScreenIntentDecision = this.this$0.mVisualInterruptionDecisionProvider.makeUnloggedFullScreenIntentDecision(notificationEntry);
        this.this$0.mVisualInterruptionDecisionProvider.logFullScreenIntentDecision(fullScreenIntentDecisionImplMakeUnloggedFullScreenIntentDecision);
        if (fullScreenIntentDecisionImplMakeUnloggedFullScreenIntentDecision.getShouldInterrupt()) {
            this.this$0.mLaunchFullScreenIntentProvider.launchFullScreenIntent(notificationEntry);
        } else if (fullScreenIntentDecisionImplMakeUnloggedFullScreenIntentDecision.getWouldInterruptWithoutDnd()) {
            HeadsUpCoordinator headsUpCoordinator = this.this$0;
            headsUpCoordinator.addForFSIReconsideration(notificationEntry, headsUpCoordinator.mSystemClock.currentTimeMillis());
        }
        this.this$0.mPostedEntries.put(notificationEntry.mKey, new HeadsUpCoordinator.PostedEntry(notificationEntry, true, false, this.this$0.mVisualInterruptionDecisionProvider.makeAndLogHeadsUpDecision(notificationEntry).getShouldInterrupt(), true, false, false, false, 32, null));
        HeadsUpCoordinator headsUpCoordinator2 = this.this$0;
        headsUpCoordinator2.setUpdateTime(notificationEntry, headsUpCoordinator2.mSystemClock.currentTimeMillis());
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onEntryCleanUp(NotificationEntry notificationEntry) {
        this.this$0.mHeadsUpViewBinder.abortBindCallback(notificationEntry);
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onEntryRemoved(NotificationEntry notificationEntry, int i) {
        this.this$0.mPostedEntries.remove(notificationEntry.mKey);
        ArrayMap arrayMap = this.this$0.mEntriesUpdateTimes;
        String str = notificationEntry.mKey;
        arrayMap.remove(str);
        this.this$0.cancelHeadsUpBind(notificationEntry);
        if (((HeadsUpManagerImpl) this.this$0.mHeadsUpManager).isHeadsUpEntry(str)) {
            ((HeadsUpManagerImpl) this.this$0.mHeadsUpManager).removeNotification(str, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "onEntryRemoved, reason: "), this.this$0.mRemoteInputManager.isSpinning(str) && !NotificationRemoteInputManager.FORCE_REMOTE_INPUT_HISTORY);
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onEntryUpdated(NotificationEntry notificationEntry, UpdateSource updateSource) {
        onEntryUpdated(notificationEntry);
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onRankingApplied() {
        NotifPipeline notifPipeline = this.this$0.mNotifPipeline;
        if (notifPipeline == null) {
            notifPipeline = null;
        }
        for (NotificationEntry notificationEntry : notifPipeline.getAllNotifs()) {
            if (this.this$0.isNewEnoughForRankingUpdate(notificationEntry) && !notificationEntry.interruption) {
                boolean zIsCandidateForFSIReconsideration = this.this$0.isCandidateForFSIReconsideration(notificationEntry);
                String str = notificationEntry.mKey;
                if (zIsCandidateForFSIReconsideration) {
                    NotificationInterruptStateProviderWrapper.FullScreenIntentDecisionImpl fullScreenIntentDecisionImplMakeUnloggedFullScreenIntentDecision = this.this$0.mVisualInterruptionDecisionProvider.makeUnloggedFullScreenIntentDecision(notificationEntry);
                    if (fullScreenIntentDecisionImplMakeUnloggedFullScreenIntentDecision.getShouldInterrupt()) {
                        this.this$0.mLogger.logEntryUpdatedToFullScreen(str, fullScreenIntentDecisionImplMakeUnloggedFullScreenIntentDecision.getLogReason());
                        this.this$0.mVisualInterruptionDecisionProvider.logFullScreenIntentDecision(fullScreenIntentDecisionImplMakeUnloggedFullScreenIntentDecision);
                        this.this$0.mLaunchFullScreenIntentProvider.launchFullScreenIntent(notificationEntry);
                        this.this$0.mFSIUpdateCandidates.remove(str);
                    } else if (!fullScreenIntentDecisionImplMakeUnloggedFullScreenIntentDecision.getWouldInterruptWithoutDnd()) {
                        this.this$0.mLogger.logEntryDisqualifiedFromFullScreen(str, fullScreenIntentDecisionImplMakeUnloggedFullScreenIntentDecision.getLogReason());
                        this.this$0.mVisualInterruptionDecisionProvider.logFullScreenIntentDecision(fullScreenIntentDecisionImplMakeUnloggedFullScreenIntentDecision);
                        this.this$0.mFSIUpdateCandidates.remove(str);
                    }
                }
                VisualInterruptionDecisionProvider.Decision decisionMakeUnloggedHeadsUpDecision = this.this$0.mVisualInterruptionDecisionProvider.makeUnloggedHeadsUpDecision(notificationEntry);
                boolean shouldInterrupt = decisionMakeUnloggedHeadsUpDecision.getShouldInterrupt();
                HeadsUpCoordinator.PostedEntry postedEntry = (HeadsUpCoordinator.PostedEntry) this.this$0.mPostedEntries.get(str);
                if ((postedEntry != null ? postedEntry.getShouldHeadsUpEver() : false) != shouldInterrupt) {
                    this.this$0.mLogger.logEntryUpdatedByRanking(str, shouldInterrupt, decisionMakeUnloggedHeadsUpDecision.getLogReason());
                    onEntryUpdated(notificationEntry);
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onEntryUpdated(final NotificationEntry notificationEntry) {
        final boolean shouldInterrupt = this.this$0.mVisualInterruptionDecisionProvider.makeAndLogHeadsUpDecision(notificationEntry).getShouldInterrupt();
        final boolean zShouldHunAgain = this.this$0.shouldHunAgain(notificationEntry);
        final boolean zIsHeadsUpEntry = ((HeadsUpManagerImpl) this.this$0.mHeadsUpManager).isHeadsUpEntry(notificationEntry.mKey);
        final boolean zIsEntryBinding = this.this$0.isEntryBinding(notificationEntry);
        final boolean z = notificationEntry.mIsHeadsUpByBriefExpanding;
        LinkedHashMap linkedHashMap = this.this$0.mPostedEntries;
        final Function2 function2 = new Function2() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mNotifCollectionListener$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                boolean z2 = zIsHeadsUpEntry;
                boolean z3 = zIsEntryBinding;
                return HeadsUpCoordinator$mNotifCollectionListener$1.onEntryUpdated$lambda$1(notificationEntry, shouldInterrupt, z, zShouldHunAgain, z2, z3, (String) obj, (HeadsUpCoordinator.PostedEntry) obj2);
            }
        };
        HeadsUpCoordinator.PostedEntry postedEntry = (HeadsUpCoordinator.PostedEntry) linkedHashMap.compute(notificationEntry.mKey, new BiFunction() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorKt$sam$java_util_function_BiFunction$0
            @Override // java.util.function.BiFunction
            public final /* synthetic */ Object apply(Object obj, Object obj2) {
                return function2.invoke(obj, obj2);
            }
        });
        if (postedEntry != null && !postedEntry.getShouldHeadsUpEver()) {
            if (postedEntry.isHeadsUpEntry()) {
                ((HeadsUpManagerImpl) this.this$0.mHeadsUpManager).removeNotification(postedEntry.getKey(), "onEntryUpdated", false);
            } else if (postedEntry.isBinding()) {
                this.this$0.cancelHeadsUpBind(postedEntry.getEntry());
            }
        }
        HeadsUpCoordinator headsUpCoordinator = this.this$0;
        headsUpCoordinator.setUpdateTime(notificationEntry, headsUpCoordinator.mSystemClock.currentTimeMillis());
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
