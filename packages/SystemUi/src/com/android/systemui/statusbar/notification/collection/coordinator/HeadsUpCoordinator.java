package com.android.systemui.statusbar.notification.collection.coordinator;

import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.ArrayMap;
import android.util.ArraySet;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.AbstractC0950x8906c950;
import com.android.systemui.Dependency;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.statusbar.AlertingNotificationManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda8;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.statusbar.notification.collection.provider.LaunchFullScreenIntentProvider;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderImpl;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderWrapper;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.util.Assert;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.EmptyList;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt$sortedWith$1;
import noticolorpicker.NotificationColorPicker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HeadsUpCoordinator implements Coordinator {
    public static final /* synthetic */ int $r8$clinit = 0;
    public NotifCollection$$ExternalSyntheticLambda8 mEndLifetimeExtension;
    public final DelayableExecutor mExecutor;
    public final HeadsUpManager mHeadsUpManager;
    public final HeadsUpViewBinder mHeadsUpViewBinder;
    public final LaunchFullScreenIntentProvider mLaunchFullScreenIntentProvider;
    public final HeadsUpCoordinatorLogger mLogger;
    public NotifPipeline mNotifPipeline;
    public final NotificationRemoteInputManager mRemoteInputManager;
    public final SystemClock mSystemClock;
    public final VisualInterruptionDecisionProvider mVisualInterruptionDecisionProvider;
    public final ArrayMap mEntriesBindingUntil = new ArrayMap();
    public final ArrayMap mEntriesUpdateTimes = new ArrayMap();
    public final ArrayMap mFSIUpdateCandidates = new ArrayMap();
    public long mNow = -1;
    public final LinkedHashMap mPostedEntries = new LinkedHashMap();
    public final ArrayMap mNotifsExtendingLifetime = new ArrayMap();
    public final HeadsUpCoordinator$mNotifCollectionListener$1 mNotifCollectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mNotifCollectionListener$1
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public final void onEntryAdded(NotificationEntry notificationEntry) {
            HeadsUpCoordinator headsUpCoordinator = HeadsUpCoordinator.this;
            NotificationInterruptStateProviderWrapper.FullScreenIntentDecisionImpl makeUnloggedFullScreenIntentDecision = ((NotificationInterruptStateProviderWrapper) headsUpCoordinator.mVisualInterruptionDecisionProvider).makeUnloggedFullScreenIntentDecision(notificationEntry);
            VisualInterruptionDecisionProvider visualInterruptionDecisionProvider = headsUpCoordinator.mVisualInterruptionDecisionProvider;
            ((NotificationInterruptStateProviderWrapper) visualInterruptionDecisionProvider).logFullScreenIntentDecision(makeUnloggedFullScreenIntentDecision);
            SystemClock systemClock = headsUpCoordinator.mSystemClock;
            if (makeUnloggedFullScreenIntentDecision.shouldInterrupt) {
                headsUpCoordinator.mLaunchFullScreenIntentProvider.launchFullScreenIntent(notificationEntry);
            } else if (makeUnloggedFullScreenIntentDecision.wouldInterruptWithoutDnd) {
                ((SystemClockImpl) systemClock).getClass();
                headsUpCoordinator.addForFSIReconsideration(notificationEntry, System.currentTimeMillis());
            }
            boolean checkHeadsUp = ((NotificationInterruptStateProviderImpl) ((NotificationInterruptStateProviderWrapper) visualInterruptionDecisionProvider).wrapped).checkHeadsUp(notificationEntry, true);
            NotificationInterruptStateProviderWrapper.DecisionImpl.Companion.getClass();
            headsUpCoordinator.mPostedEntries.put(notificationEntry.mKey, new HeadsUpCoordinator.PostedEntry(notificationEntry, true, false, (checkHeadsUp ? NotificationInterruptStateProviderWrapper.DecisionImpl.SHOULD_INTERRUPT : NotificationInterruptStateProviderWrapper.DecisionImpl.SHOULD_NOT_INTERRUPT).getShouldInterrupt(), true, false, false));
            ((SystemClockImpl) systemClock).getClass();
            headsUpCoordinator.setUpdateTime(notificationEntry, System.currentTimeMillis());
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public final void onEntryCleanUp(NotificationEntry notificationEntry) {
            HeadsUpCoordinator.this.mHeadsUpViewBinder.abortBindCallback(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
            HeadsUpCoordinator headsUpCoordinator = HeadsUpCoordinator.this;
            LinkedHashMap linkedHashMap = headsUpCoordinator.mPostedEntries;
            String str = notificationEntry.mKey;
            linkedHashMap.remove(str);
            headsUpCoordinator.mEntriesUpdateTimes.remove(str);
            headsUpCoordinator.cancelHeadsUpBind(notificationEntry);
            HeadsUpManager headsUpManager = headsUpCoordinator.mHeadsUpManager;
            if (headsUpManager.isAlerting(str)) {
                RemoteInputController remoteInputController = headsUpCoordinator.mRemoteInputManager.mRemoteInputController;
                headsUpManager.removeNotification(str, (remoteInputController != null && remoteInputController.mSpinning.containsKey(str)) && !NotificationRemoteInputManager.FORCE_REMOTE_INPUT_HISTORY);
            }
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public final void onEntryUpdated(final NotificationEntry notificationEntry) {
            HeadsUpCoordinator headsUpCoordinator = HeadsUpCoordinator.this;
            boolean checkHeadsUp = ((NotificationInterruptStateProviderImpl) ((NotificationInterruptStateProviderWrapper) headsUpCoordinator.mVisualInterruptionDecisionProvider).wrapped).checkHeadsUp(notificationEntry, true);
            NotificationInterruptStateProviderWrapper.DecisionImpl.Companion.getClass();
            final boolean shouldInterrupt = (checkHeadsUp ? NotificationInterruptStateProviderWrapper.DecisionImpl.SHOULD_INTERRUPT : NotificationInterruptStateProviderWrapper.DecisionImpl.SHOULD_NOT_INTERRUPT).getShouldInterrupt();
            final boolean z = !notificationEntry.interruption || (notificationEntry.mSbn.getNotification().flags & 8) == 0;
            HeadsUpManager headsUpManager = headsUpCoordinator.mHeadsUpManager;
            String str = notificationEntry.mKey;
            final boolean isAlerting = headsUpManager.isAlerting(str);
            final boolean isEntryBinding = headsUpCoordinator.isEntryBinding(notificationEntry);
            final boolean z2 = notificationEntry.mIsHeadsUpByBriefExpanding;
            HeadsUpCoordinator.PostedEntry postedEntry = (HeadsUpCoordinator.PostedEntry) headsUpCoordinator.mPostedEntries.compute(str, new BiFunction() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mNotifCollectionListener$1$onEntryUpdated$posted$1
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    HeadsUpCoordinator.PostedEntry postedEntry2 = (HeadsUpCoordinator.PostedEntry) obj2;
                    if (postedEntry2 != null) {
                        boolean z3 = shouldInterrupt;
                        boolean z4 = z2;
                        boolean z5 = z;
                        boolean z6 = isAlerting;
                        boolean z7 = isEntryBinding;
                        postedEntry2.wasUpdated = true;
                        postedEntry2.shouldHeadsUpEver = postedEntry2.shouldHeadsUpEver || z3 || z4;
                        postedEntry2.shouldHeadsUpAgain = postedEntry2.shouldHeadsUpAgain || z5;
                        postedEntry2.isAlerting = z6;
                        postedEntry2.isBinding = z7;
                    } else {
                        postedEntry2 = new HeadsUpCoordinator.PostedEntry(NotificationEntry.this, false, true, shouldInterrupt || z2, z, isAlerting, isEntryBinding);
                    }
                    return postedEntry2;
                }
            });
            if ((postedEntry == null || postedEntry.shouldHeadsUpEver) ? false : true) {
                if (postedEntry.isAlerting) {
                    headsUpManager.removeNotification(postedEntry.key, false);
                } else if (postedEntry.isBinding) {
                    headsUpCoordinator.cancelHeadsUpBind(postedEntry.entry);
                }
            }
            ((SystemClockImpl) headsUpCoordinator.mSystemClock).getClass();
            headsUpCoordinator.setUpdateTime(notificationEntry, System.currentTimeMillis());
        }

        /* JADX WARN: Removed duplicated region for block: B:21:0x005e  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x007b  */
        /* JADX WARN: Removed duplicated region for block: B:34:0x00d5  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x00e8  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x00ee  */
        /* JADX WARN: Removed duplicated region for block: B:41:0x00f1 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:45:0x0010 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:46:0x00eb  */
        /* JADX WARN: Removed duplicated region for block: B:47:0x00d8  */
        /* JADX WARN: Removed duplicated region for block: B:51:0x00c4 A[SYNTHETIC] */
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onRankingApplied() {
            boolean z;
            Long l;
            boolean z2;
            boolean shouldInterrupt;
            HeadsUpCoordinator.PostedEntry postedEntry;
            Long l2;
            HeadsUpCoordinator headsUpCoordinator = HeadsUpCoordinator.this;
            NotifPipeline notifPipeline = headsUpCoordinator.mNotifPipeline;
            if (notifPipeline == null) {
                notifPipeline = null;
            }
            for (NotificationEntry notificationEntry : notifPipeline.getAllNotifs()) {
                String str = notificationEntry.mKey;
                ArrayMap arrayMap = headsUpCoordinator.mEntriesUpdateTimes;
                boolean containsKey = arrayMap.containsKey(str);
                SystemClock systemClock = headsUpCoordinator.mSystemClock;
                String str2 = notificationEntry.mKey;
                if (containsKey && (l2 = (Long) arrayMap.get(str2)) != null) {
                    long longValue = l2.longValue();
                    ((SystemClockImpl) systemClock).getClass();
                    if (System.currentTimeMillis() - longValue <= 2000) {
                        z = true;
                        if (z && !notificationEntry.interruption) {
                            ArrayMap arrayMap2 = headsUpCoordinator.mFSIUpdateCandidates;
                            l = (Long) arrayMap2.get(str2);
                            if (l != null) {
                                long longValue2 = l.longValue();
                                ((SystemClockImpl) systemClock).getClass();
                                if (System.currentTimeMillis() - longValue2 <= 2000) {
                                    z2 = true;
                                    HeadsUpCoordinatorLogger headsUpCoordinatorLogger = headsUpCoordinator.mLogger;
                                    VisualInterruptionDecisionProvider visualInterruptionDecisionProvider = headsUpCoordinator.mVisualInterruptionDecisionProvider;
                                    if (z2) {
                                        NotificationInterruptStateProviderWrapper.FullScreenIntentDecisionImpl makeUnloggedFullScreenIntentDecision = ((NotificationInterruptStateProviderWrapper) visualInterruptionDecisionProvider).makeUnloggedFullScreenIntentDecision(notificationEntry);
                                        boolean z3 = makeUnloggedFullScreenIntentDecision.shouldInterrupt;
                                        String str3 = makeUnloggedFullScreenIntentDecision.logReason;
                                        if (z3) {
                                            headsUpCoordinatorLogger.getClass();
                                            LogLevel logLevel = LogLevel.DEBUG;
                                            HeadsUpCoordinatorLogger$logEntryUpdatedToFullScreen$2 headsUpCoordinatorLogger$logEntryUpdatedToFullScreen$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$logEntryUpdatedToFullScreen$2
                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Object obj) {
                                                    LogMessage logMessage = (LogMessage) obj;
                                                    return FontProvider$$ExternalSyntheticOutline0.m32m("updating entry to launch full screen intent: ", logMessage.getStr1(), " because ", logMessage.getStr2());
                                                }
                                            };
                                            LogBuffer logBuffer = headsUpCoordinatorLogger.buffer;
                                            LogMessage obtain = logBuffer.obtain("HeadsUpCoordinator", logLevel, headsUpCoordinatorLogger$logEntryUpdatedToFullScreen$2, null);
                                            CarrierTextManagerLogger$$ExternalSyntheticOutline0.m83m(obtain, str2, str3, logBuffer, obtain);
                                            ((NotificationInterruptStateProviderWrapper) visualInterruptionDecisionProvider).logFullScreenIntentDecision(makeUnloggedFullScreenIntentDecision);
                                            headsUpCoordinator.mLaunchFullScreenIntentProvider.launchFullScreenIntent(notificationEntry);
                                            arrayMap2.remove(str2);
                                        } else if (!makeUnloggedFullScreenIntentDecision.wouldInterruptWithoutDnd) {
                                            headsUpCoordinatorLogger.getClass();
                                            LogLevel logLevel2 = LogLevel.DEBUG;
                                            HeadsUpCoordinatorLogger$logEntryDisqualifiedFromFullScreen$2 headsUpCoordinatorLogger$logEntryDisqualifiedFromFullScreen$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$logEntryDisqualifiedFromFullScreen$2
                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Object obj) {
                                                    LogMessage logMessage = (LogMessage) obj;
                                                    return FontProvider$$ExternalSyntheticOutline0.m32m("updated entry no longer qualifies for full screen intent: ", logMessage.getStr1(), " because ", logMessage.getStr2());
                                                }
                                            };
                                            LogBuffer logBuffer2 = headsUpCoordinatorLogger.buffer;
                                            LogMessage obtain2 = logBuffer2.obtain("HeadsUpCoordinator", logLevel2, headsUpCoordinatorLogger$logEntryDisqualifiedFromFullScreen$2, null);
                                            CarrierTextManagerLogger$$ExternalSyntheticOutline0.m83m(obtain2, str2, str3, logBuffer2, obtain2);
                                            ((NotificationInterruptStateProviderWrapper) visualInterruptionDecisionProvider).logFullScreenIntentDecision(makeUnloggedFullScreenIntentDecision);
                                            arrayMap2.remove(str2);
                                        }
                                    }
                                    boolean checkHeadsUp = ((NotificationInterruptStateProviderImpl) ((NotificationInterruptStateProviderWrapper) visualInterruptionDecisionProvider).wrapped).checkHeadsUp(notificationEntry, false);
                                    NotificationInterruptStateProviderWrapper.DecisionImpl.Companion.getClass();
                                    NotificationInterruptStateProviderWrapper.DecisionImpl decisionImpl = checkHeadsUp ? NotificationInterruptStateProviderWrapper.DecisionImpl.SHOULD_INTERRUPT : NotificationInterruptStateProviderWrapper.DecisionImpl.SHOULD_NOT_INTERRUPT;
                                    shouldInterrupt = decisionImpl.getShouldInterrupt();
                                    postedEntry = (HeadsUpCoordinator.PostedEntry) headsUpCoordinator.mPostedEntries.get(str2);
                                    if ((postedEntry != null ? postedEntry.shouldHeadsUpEver : false) != shouldInterrupt) {
                                        String logReason = decisionImpl.getLogReason();
                                        headsUpCoordinatorLogger.getClass();
                                        LogLevel logLevel3 = LogLevel.DEBUG;
                                        HeadsUpCoordinatorLogger$logEntryUpdatedByRanking$2 headsUpCoordinatorLogger$logEntryUpdatedByRanking$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$logEntryUpdatedByRanking$2
                                            @Override // kotlin.jvm.functions.Function1
                                            public final Object invoke(Object obj) {
                                                LogMessage logMessage = (LogMessage) obj;
                                                return "updating entry via ranking applied: " + logMessage.getStr1() + " updated shouldHeadsUp=" + logMessage.getBool1() + " because " + logMessage.getStr2();
                                            }
                                        };
                                        LogBuffer logBuffer3 = headsUpCoordinatorLogger.buffer;
                                        LogMessage obtain3 = logBuffer3.obtain("HeadsUpCoordinator", logLevel3, headsUpCoordinatorLogger$logEntryUpdatedByRanking$2, null);
                                        obtain3.setStr1(str2);
                                        obtain3.setBool1(shouldInterrupt);
                                        obtain3.setStr2(logReason);
                                        logBuffer3.commit(obtain3);
                                        onEntryUpdated(notificationEntry);
                                    }
                                }
                            }
                            z2 = false;
                            HeadsUpCoordinatorLogger headsUpCoordinatorLogger2 = headsUpCoordinator.mLogger;
                            VisualInterruptionDecisionProvider visualInterruptionDecisionProvider2 = headsUpCoordinator.mVisualInterruptionDecisionProvider;
                            if (z2) {
                            }
                            boolean checkHeadsUp2 = ((NotificationInterruptStateProviderImpl) ((NotificationInterruptStateProviderWrapper) visualInterruptionDecisionProvider2).wrapped).checkHeadsUp(notificationEntry, false);
                            NotificationInterruptStateProviderWrapper.DecisionImpl.Companion.getClass();
                            if (checkHeadsUp2) {
                            }
                            shouldInterrupt = decisionImpl.getShouldInterrupt();
                            postedEntry = (HeadsUpCoordinator.PostedEntry) headsUpCoordinator.mPostedEntries.get(str2);
                            if ((postedEntry != null ? postedEntry.shouldHeadsUpEver : false) != shouldInterrupt) {
                            }
                        }
                    }
                }
                z = false;
                if (z) {
                    ArrayMap arrayMap22 = headsUpCoordinator.mFSIUpdateCandidates;
                    l = (Long) arrayMap22.get(str2);
                    if (l != null) {
                    }
                    z2 = false;
                    HeadsUpCoordinatorLogger headsUpCoordinatorLogger22 = headsUpCoordinator.mLogger;
                    VisualInterruptionDecisionProvider visualInterruptionDecisionProvider22 = headsUpCoordinator.mVisualInterruptionDecisionProvider;
                    if (z2) {
                    }
                    boolean checkHeadsUp22 = ((NotificationInterruptStateProviderImpl) ((NotificationInterruptStateProviderWrapper) visualInterruptionDecisionProvider22).wrapped).checkHeadsUp(notificationEntry, false);
                    NotificationInterruptStateProviderWrapper.DecisionImpl.Companion.getClass();
                    if (checkHeadsUp22) {
                    }
                    shouldInterrupt = decisionImpl.getShouldInterrupt();
                    postedEntry = (HeadsUpCoordinator.PostedEntry) headsUpCoordinator.mPostedEntries.get(str2);
                    if ((postedEntry != null ? postedEntry.shouldHeadsUpEver : false) != shouldInterrupt) {
                    }
                }
            }
        }
    };
    public final HeadsUpCoordinator$mActionPressListener$1 mActionPressListener = new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mActionPressListener$1
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            final NotificationEntry notificationEntry = (NotificationEntry) obj;
            HeadsUpManager headsUpManager = HeadsUpCoordinator.this.mHeadsUpManager;
            headsUpManager.getClass();
            HeadsUpManager.HeadsUpEntry headsUpEntry = headsUpManager.getHeadsUpEntry(notificationEntry.mKey);
            if (headsUpEntry != null) {
                headsUpEntry.userActionMayIndirectlyRemove = true;
            }
            final HeadsUpCoordinator headsUpCoordinator = HeadsUpCoordinator.this;
            ((ExecutorImpl) headsUpCoordinator.mExecutor).execute(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mActionPressListener$1.1
                @Override // java.lang.Runnable
                public final void run() {
                    HeadsUpCoordinator.access$endNotifLifetimeExtensionIfExtended(HeadsUpCoordinator.this, notificationEntry);
                }
            });
        }
    };
    public final HeadsUpCoordinator$mLifetimeExtender$1 mLifetimeExtender = new NotifLifetimeExtender() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mLifetimeExtender$1
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
        public final void cancelLifetimeExtension(NotificationEntry notificationEntry) {
            Runnable runnable = (Runnable) HeadsUpCoordinator.this.mNotifsExtendingLifetime.remove(notificationEntry);
            if (runnable != null) {
                runnable.run();
            }
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
        public final String getName() {
            return "HeadsUpCoordinator";
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
        public final boolean maybeExtendLifetime(final NotificationEntry notificationEntry, int i) {
            final HeadsUpCoordinator headsUpCoordinator = HeadsUpCoordinator.this;
            HeadsUpManager headsUpManager = headsUpCoordinator.mHeadsUpManager;
            String str = notificationEntry.mKey;
            if (headsUpManager.canRemoveImmediately(str)) {
                return false;
            }
            HeadsUpManager headsUpManager2 = headsUpCoordinator.mHeadsUpManager;
            AlertingNotificationManager.AlertEntry alertEntry = (AlertingNotificationManager.AlertEntry) headsUpManager2.mAlertEntries.get(str);
            boolean isSticky = alertEntry != null ? alertEntry.isSticky() : false;
            ArrayMap arrayMap = headsUpCoordinator.mNotifsExtendingLifetime;
            DelayableExecutor delayableExecutor = headsUpCoordinator.mExecutor;
            if (!isSticky) {
                ((ExecutorImpl) delayableExecutor).execute(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mLifetimeExtender$1$maybeExtendLifetime$2
                    @Override // java.lang.Runnable
                    public final void run() {
                        HeadsUpCoordinator.this.mHeadsUpManager.removeNotification(notificationEntry.mKey, false);
                    }
                });
                arrayMap.put(notificationEntry, null);
                return true;
            }
            AlertingNotificationManager.AlertEntry alertEntry2 = (AlertingNotificationManager.AlertEntry) headsUpManager2.mAlertEntries.get(str);
            long j = 0;
            if (alertEntry2 != null) {
                long j2 = alertEntry2.mEarliestRemovaltime;
                headsUpManager2.mClock.getClass();
                j = Math.max(0L, j2 - android.os.SystemClock.elapsedRealtime());
            }
            arrayMap.put(notificationEntry, delayableExecutor.executeDelayed(j, new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mLifetimeExtender$1$maybeExtendLifetime$1
                @Override // java.lang.Runnable
                public final void run() {
                    HeadsUpCoordinator.this.mHeadsUpManager.removeNotification(notificationEntry.mKey, true);
                }
            }));
            return true;
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
        public final void setCallback(NotifCollection$$ExternalSyntheticLambda8 notifCollection$$ExternalSyntheticLambda8) {
            HeadsUpCoordinator.this.mEndLifetimeExtension = notifCollection$$ExternalSyntheticLambda8;
        }
    };
    public final HeadsUpCoordinator$mNotifPromoter$1 mNotifPromoter = new NotifPromoter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mNotifPromoter$1
        {
            super("HeadsUpCoordinator");
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter
        public final boolean shouldPromoteToTopLevel(NotificationEntry notificationEntry) {
            return HeadsUpCoordinator.access$isGoingToShowHunNoRetract(HeadsUpCoordinator.this, notificationEntry);
        }
    };
    public final HeadsUpCoordinator$sectioner$1 sectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$sectioner$1
        {
            super("HeadsUp", 4);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public final NotifComparator getComparator() {
            final HeadsUpCoordinator headsUpCoordinator = HeadsUpCoordinator.this;
            return new NotifComparator() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$sectioner$1$getComparator$1
                {
                    super("HeadsUp");
                }

                @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator, java.util.Comparator
                public final int compare(ListEntry listEntry, ListEntry listEntry2) {
                    HeadsUpManager headsUpManager = HeadsUpCoordinator.this.mHeadsUpManager;
                    NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
                    NotificationEntry representativeEntry2 = listEntry2.getRepresentativeEntry();
                    headsUpManager.getClass();
                    if (representativeEntry == null || representativeEntry2 == null) {
                        return Boolean.compare(representativeEntry == null, representativeEntry2 == null);
                    }
                    HeadsUpManager.HeadsUpEntry headsUpEntry = headsUpManager.getHeadsUpEntry(representativeEntry.mKey);
                    HeadsUpManager.HeadsUpEntry headsUpEntry2 = headsUpManager.getHeadsUpEntry(representativeEntry2.mKey);
                    if (headsUpEntry == null || headsUpEntry2 == null) {
                        return Boolean.compare(headsUpEntry == null, headsUpEntry2 == null);
                    }
                    return headsUpEntry.compareTo((AlertingNotificationManager.AlertEntry) headsUpEntry2);
                }
            };
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public final NodeController getHeaderNodeController() {
            return null;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public final boolean isInSection(ListEntry listEntry) {
            return HeadsUpCoordinator.access$isGoingToShowHunNoRetract(HeadsUpCoordinator.this, listEntry);
        }
    };
    public final HeadsUpCoordinator$mOnHeadsUpChangedListener$1 mOnHeadsUpChangedListener = new OnHeadsUpChangedListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mOnHeadsUpChangedListener$1
        @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
        public final void onHeadsUpPinned(NotificationEntry notificationEntry) {
            if (notificationEntry.mIsHeadsUpByBriefExpanding) {
                HeadsUpCoordinator headsUpCoordinator = HeadsUpCoordinator.this;
                HeadsUpCoordinator.PostedEntry postedEntry = (HeadsUpCoordinator.PostedEntry) headsUpCoordinator.mPostedEntries.get(notificationEntry.mKey);
                if (postedEntry != null) {
                    postedEntry.isHeadsUpByBriefExpanding = true;
                }
                headsUpCoordinator.mNotifPromoter.invalidateList("headsUpFromBrief: " + NotificationUtilsKt.getLogKey(notificationEntry));
            }
        }

        @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
        public final void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
            if (z) {
                return;
            }
            HeadsUpCoordinator headsUpCoordinator = HeadsUpCoordinator.this;
            headsUpCoordinator.mNotifPromoter.invalidateList("headsUpEnded: " + NotificationUtilsKt.getLogKey(notificationEntry));
            headsUpCoordinator.mHeadsUpViewBinder.unbindHeadsUpView(notificationEntry);
            HeadsUpCoordinator.access$endNotifLifetimeExtensionIfExtended(headsUpCoordinator, notificationEntry);
        }

        @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
        public final void onHeadsUpUnPinned(NotificationEntry notificationEntry) {
            HeadsUpCoordinator.PostedEntry postedEntry = (HeadsUpCoordinator.PostedEntry) HeadsUpCoordinator.this.mPostedEntries.get(notificationEntry.mKey);
            if (postedEntry == null) {
                return;
            }
            postedEntry.isHeadsUpByBriefExpanding = false;
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PostedEntry {
        public final NotificationEntry entry;
        public boolean isAlerting;
        public boolean isBinding;
        public boolean isHeadsUpByBriefExpanding;
        public final String key;
        public boolean shouldHeadsUpAgain;
        public boolean shouldHeadsUpEver;
        public final boolean wasAdded;
        public boolean wasUpdated;

        public PostedEntry(NotificationEntry notificationEntry, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
            this.entry = notificationEntry;
            this.wasAdded = z;
            this.wasUpdated = z2;
            this.shouldHeadsUpEver = z3;
            this.shouldHeadsUpAgain = z4;
            this.isAlerting = z5;
            this.isBinding = z6;
            this.key = notificationEntry.mKey;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PostedEntry)) {
                return false;
            }
            PostedEntry postedEntry = (PostedEntry) obj;
            return Intrinsics.areEqual(this.entry, postedEntry.entry) && this.wasAdded == postedEntry.wasAdded && this.wasUpdated == postedEntry.wasUpdated && this.shouldHeadsUpEver == postedEntry.shouldHeadsUpEver && this.shouldHeadsUpAgain == postedEntry.shouldHeadsUpAgain && this.isAlerting == postedEntry.isAlerting && this.isBinding == postedEntry.isBinding;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final int hashCode() {
            int hashCode = this.entry.hashCode() * 31;
            boolean z = this.wasAdded;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            int i2 = (hashCode + i) * 31;
            boolean z2 = this.wasUpdated;
            int i3 = z2;
            if (z2 != 0) {
                i3 = 1;
            }
            int i4 = (i2 + i3) * 31;
            boolean z3 = this.shouldHeadsUpEver;
            int i5 = z3;
            if (z3 != 0) {
                i5 = 1;
            }
            int i6 = (i4 + i5) * 31;
            boolean z4 = this.shouldHeadsUpAgain;
            int i7 = z4;
            if (z4 != 0) {
                i7 = 1;
            }
            int i8 = (i6 + i7) * 31;
            boolean z5 = this.isAlerting;
            int i9 = z5;
            if (z5 != 0) {
                i9 = 1;
            }
            int i10 = (i8 + i9) * 31;
            boolean z6 = this.isBinding;
            return i10 + (z6 ? 1 : z6 ? 1 : 0);
        }

        public final String toString() {
            boolean z = this.wasUpdated;
            boolean z2 = this.shouldHeadsUpEver;
            boolean z3 = this.shouldHeadsUpAgain;
            boolean z4 = this.isAlerting;
            boolean z5 = this.isBinding;
            StringBuilder sb = new StringBuilder("PostedEntry(entry=");
            sb.append(this.entry);
            sb.append(", wasAdded=");
            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, this.wasAdded, ", wasUpdated=", z, ", shouldHeadsUpEver=");
            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, z2, ", shouldHeadsUpAgain=", z3, ", isAlerting=");
            sb.append(z4);
            sb.append(", isBinding=");
            sb.append(z5);
            sb.append(")");
            return sb.toString();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mNotifPromoter$1] */
    /* JADX WARN: Type inference failed for: r1v11, types: [com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$sectioner$1] */
    /* JADX WARN: Type inference failed for: r1v12, types: [com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mOnHeadsUpChangedListener$1] */
    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mNotifCollectionListener$1] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mActionPressListener$1] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mLifetimeExtender$1] */
    public HeadsUpCoordinator(HeadsUpCoordinatorLogger headsUpCoordinatorLogger, SystemClock systemClock, HeadsUpManager headsUpManager, HeadsUpViewBinder headsUpViewBinder, VisualInterruptionDecisionProvider visualInterruptionDecisionProvider, NotificationRemoteInputManager notificationRemoteInputManager, LaunchFullScreenIntentProvider launchFullScreenIntentProvider, NotifPipelineFlags notifPipelineFlags, NodeController nodeController, DelayableExecutor delayableExecutor) {
        this.mLogger = headsUpCoordinatorLogger;
        this.mSystemClock = systemClock;
        this.mHeadsUpManager = headsUpManager;
        this.mHeadsUpViewBinder = headsUpViewBinder;
        this.mVisualInterruptionDecisionProvider = visualInterruptionDecisionProvider;
        this.mRemoteInputManager = notificationRemoteInputManager;
        this.mLaunchFullScreenIntentProvider = launchFullScreenIntentProvider;
        this.mExecutor = delayableExecutor;
    }

    public static final void access$endNotifLifetimeExtensionIfExtended(HeadsUpCoordinator headsUpCoordinator, NotificationEntry notificationEntry) {
        ArrayMap arrayMap = headsUpCoordinator.mNotifsExtendingLifetime;
        if (arrayMap.containsKey(notificationEntry)) {
            Runnable runnable = (Runnable) arrayMap.remove(notificationEntry);
            if (runnable != null) {
                runnable.run();
            }
            NotifCollection$$ExternalSyntheticLambda8 notifCollection$$ExternalSyntheticLambda8 = headsUpCoordinator.mEndLifetimeExtension;
            if (notifCollection$$ExternalSyntheticLambda8 != null) {
                notifCollection$$ExternalSyntheticLambda8.onEndLifetimeExtension(notificationEntry, headsUpCoordinator.mLifetimeExtender);
            }
        }
    }

    public static final void access$handlePostedEntry(HeadsUpCoordinator headsUpCoordinator, PostedEntry postedEntry, HunMutatorImpl hunMutatorImpl, String str) {
        HeadsUpCoordinatorLogger headsUpCoordinatorLogger = headsUpCoordinator.mLogger;
        if (headsUpCoordinatorLogger.verbose) {
            LogLevel logLevel = LogLevel.VERBOSE;
            HeadsUpCoordinatorLogger$logPostedEntryWillEvaluate$2 headsUpCoordinatorLogger$logPostedEntryWillEvaluate$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$logPostedEntryWillEvaluate$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    String str1 = logMessage.getStr1();
                    String str2 = logMessage.getStr2();
                    return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m74m(AbstractC0866xb1ce8deb.m87m("will evaluate posted entry ", str1, ": reason=", str2, " shouldHeadsUpEver="), logMessage.getBool1(), " shouldHeadsUpAgain=", logMessage.getBool2());
                }
            };
            LogBuffer logBuffer = headsUpCoordinatorLogger.buffer;
            LogMessage obtain = logBuffer.obtain("HeadsUpCoordinator", logLevel, headsUpCoordinatorLogger$logPostedEntryWillEvaluate$2, null);
            obtain.setStr1(postedEntry.key);
            obtain.setStr2(str);
            obtain.setBool1(postedEntry.shouldHeadsUpEver);
            obtain.setBool2(postedEntry.shouldHeadsUpAgain);
            logBuffer.commit(obtain);
        }
        if (postedEntry.wasAdded) {
            if (postedEntry.shouldHeadsUpEver) {
                headsUpCoordinator.bindForAsyncHeadsUp(postedEntry);
                return;
            }
            return;
        }
        boolean z = postedEntry.isAlerting;
        if (!(z || postedEntry.isBinding)) {
            if (postedEntry.shouldHeadsUpEver && postedEntry.shouldHeadsUpAgain) {
                headsUpCoordinator.bindForAsyncHeadsUp(postedEntry);
                return;
            }
            return;
        }
        boolean z2 = postedEntry.shouldHeadsUpEver;
        String str2 = postedEntry.key;
        if (z2) {
            if (z) {
                hunMutatorImpl.headsUpManager.updateNotification(str2, postedEntry.shouldHeadsUpAgain);
            }
        } else if (!z) {
            headsUpCoordinator.cancelHeadsUpBind(postedEntry.entry);
        } else {
            hunMutatorImpl.getClass();
            ((ArrayList) hunMutatorImpl.deferred).add(new Pair(str2, Boolean.FALSE));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0028, code lost:
    
        if (r1.shouldHeadsUpAgain == false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x003f, code lost:
    
        if (r5.isEntryBinding(r6) == false) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final boolean access$isGoingToShowHunNoRetract(HeadsUpCoordinator headsUpCoordinator, ListEntry listEntry) {
        boolean z;
        LinkedHashMap linkedHashMap = headsUpCoordinator.mPostedEntries;
        PostedEntry postedEntry = (PostedEntry) linkedHashMap.get(listEntry.getKey());
        if (postedEntry != null) {
            if (!(postedEntry.isAlerting || postedEntry.isBinding)) {
                if (postedEntry.shouldHeadsUpEver) {
                    if (!postedEntry.wasAdded) {
                    }
                }
                z = false;
            }
            z = true;
        } else {
            if (!headsUpCoordinator.mHeadsUpManager.isAlerting(listEntry.getKey())) {
            }
            z = true;
        }
        if (z) {
            return true;
        }
        PostedEntry postedEntry2 = (PostedEntry) linkedHashMap.get(listEntry.getKey());
        return postedEntry2 != null ? postedEntry2.isHeadsUpByBriefExpanding : false;
    }

    public final void addForFSIReconsideration(NotificationEntry notificationEntry, long j) {
        this.mFSIUpdateCandidates.put(notificationEntry.mKey, Long.valueOf(j));
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        this.mNotifPipeline = notifPipeline;
        HeadsUpManager headsUpManager = this.mHeadsUpManager;
        headsUpManager.addListener(this.mOnHeadsUpChangedListener);
        notifPipeline.addCollectionListener(this.mNotifCollectionListener);
        HeadsUpCoordinator$attach$1 headsUpCoordinator$attach$1 = new HeadsUpCoordinator$attach$1(this);
        ShadeListBuilder shadeListBuilder = notifPipeline.mShadeListBuilder;
        shadeListBuilder.getClass();
        Assert.isMainThread();
        shadeListBuilder.mPipelineState.requireState();
        ((ArrayList) shadeListBuilder.mOnBeforeTransformGroupsListeners).add(headsUpCoordinator$attach$1);
        notifPipeline.addOnBeforeFinalizeFilterListener(new OnBeforeFinalizeFilterListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$attach$2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener
            public final void onBeforeFinalizeFilter(final List list) {
                final HeadsUpCoordinator headsUpCoordinator = HeadsUpCoordinator.this;
                headsUpCoordinator.getClass();
                HeadsUpCoordinatorKt.access$modifyHuns(headsUpCoordinator.mHeadsUpManager, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$onBeforeFinalizeFilter$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(1);
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:144:0x0188, code lost:
                    
                        if (r6.isEntryBinding(r12) == false) goto L70;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:67:0x0177, code lost:
                    
                        if ((r11.isAlerting || r11.isBinding) != false) goto L71;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:86:0x01fc, code lost:
                    
                        if ((r4.invoke(r11.mKey) == com.android.systemui.statusbar.notification.collection.coordinator.GroupLocation.Isolated && r11.mSbn.getNotification().getGroupAlertBehavior() == 1) != false) goto L94;
                     */
                    /* JADX WARN: Removed duplicated region for block: B:115:0x030a  */
                    /* JADX WARN: Removed duplicated region for block: B:126:0x0337  */
                    @Override // kotlin.jvm.functions.Function1
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj) {
                        Object obj2;
                        Iterator it;
                        boolean z;
                        NotificationEntry notificationEntry;
                        Lazy lazy;
                        LinkedHashMap linkedHashMap;
                        HeadsUpManager headsUpManager2;
                        boolean z2;
                        boolean z3;
                        boolean z4;
                        Throwable th;
                        FilteringSequence$iterator$1 filteringSequence$iterator$1;
                        boolean z5;
                        HeadsUpManager headsUpManager3;
                        HeadsUpCoordinator$onBeforeFinalizeFilter$1 headsUpCoordinator$onBeforeFinalizeFilter$1 = this;
                        HunMutatorImpl hunMutatorImpl = (HunMutatorImpl) obj;
                        if (!HeadsUpCoordinator.this.mPostedEntries.isEmpty()) {
                            Collection values = HeadsUpCoordinator.this.mPostedEntries.values();
                            final LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                            for (Object obj3 : values) {
                                String groupKey = ((HeadsUpCoordinator.PostedEntry) obj3).entry.mSbn.getGroupKey();
                                Object obj4 = linkedHashMap2.get(groupKey);
                                if (obj4 == null) {
                                    obj4 = new ArrayList();
                                    linkedHashMap2.put(groupKey, obj4);
                                }
                                ((List) obj4).add(obj3);
                            }
                            NotifPipeline notifPipeline2 = HeadsUpCoordinator.this.mNotifPipeline;
                            Throwable th2 = null;
                            if (notifPipeline2 == null) {
                                notifPipeline2 = null;
                            }
                            FilteringSequence filter = SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(notifPipeline2.getAllNotifs()), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$onBeforeFinalizeFilter$1$logicalMembersByGroup$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                /* JADX WARN: Multi-variable type inference failed */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj5) {
                                    return Boolean.valueOf(linkedHashMap2.containsKey(((NotificationEntry) obj5).mSbn.getGroupKey()));
                                }
                            });
                            LinkedHashMap linkedHashMap3 = new LinkedHashMap();
                            FilteringSequence$iterator$1 filteringSequence$iterator$12 = new FilteringSequence$iterator$1(filter);
                            while (filteringSequence$iterator$12.hasNext()) {
                                Object next = filteringSequence$iterator$12.next();
                                String groupKey2 = ((NotificationEntry) next).mSbn.getGroupKey();
                                Object obj5 = linkedHashMap3.get(groupKey2);
                                if (obj5 == null) {
                                    obj5 = new ArrayList();
                                    linkedHashMap3.put(groupKey2, obj5);
                                }
                                ((List) obj5).add(next);
                            }
                            final HeadsUpCoordinator headsUpCoordinator2 = HeadsUpCoordinator.this;
                            final List<ListEntry> list2 = list;
                            Lazy lazy2 = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$onBeforeFinalizeFilter$1$groupLocationsByKey$2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                /* JADX WARN: Multi-variable type inference failed */
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    HeadsUpCoordinator headsUpCoordinator3 = HeadsUpCoordinator.this;
                                    List<ListEntry> list3 = list2;
                                    int i = HeadsUpCoordinator.$r8$clinit;
                                    headsUpCoordinator3.getClass();
                                    LinkedHashMap linkedHashMap4 = new LinkedHashMap();
                                    for (ListEntry listEntry : list3) {
                                        if (listEntry instanceof NotificationEntry) {
                                            linkedHashMap4.put(((NotificationEntry) listEntry).mKey, GroupLocation.Isolated);
                                        } else {
                                            if (!(listEntry instanceof GroupEntry)) {
                                                throw new IllegalStateException(("unhandled type " + listEntry).toString());
                                            }
                                            GroupEntry groupEntry = (GroupEntry) listEntry;
                                            NotificationEntry notificationEntry2 = groupEntry.mSummary;
                                            if (notificationEntry2 != null) {
                                                linkedHashMap4.put(notificationEntry2.mKey, GroupLocation.Summary);
                                            }
                                            Iterator it2 = groupEntry.mUnmodifiableChildren.iterator();
                                            while (it2.hasNext()) {
                                                linkedHashMap4.put(((NotificationEntry) it2.next()).mKey, GroupLocation.Child);
                                            }
                                        }
                                    }
                                    return linkedHashMap4;
                                }
                            });
                            HeadsUpCoordinatorLogger headsUpCoordinatorLogger = HeadsUpCoordinator.this.mLogger;
                            int size = linkedHashMap2.size();
                            if (headsUpCoordinatorLogger.verbose) {
                                LogLevel logLevel = LogLevel.VERBOSE;
                                HeadsUpCoordinatorLogger$logEvaluatingGroups$2 headsUpCoordinatorLogger$logEvaluatingGroups$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$logEvaluatingGroups$2
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj6) {
                                        return AbstractC0000x2c234b15.m0m("evaluating groups for alert transfer: ", ((LogMessage) obj6).getInt1());
                                    }
                                };
                                LogBuffer logBuffer = headsUpCoordinatorLogger.buffer;
                                LogMessage obtain = logBuffer.obtain("HeadsUpCoordinator", logLevel, headsUpCoordinatorLogger$logEvaluatingGroups$2, null);
                                obtain.setInt1(size);
                                logBuffer.commit(obtain);
                            }
                            final HeadsUpCoordinator headsUpCoordinator3 = HeadsUpCoordinator.this;
                            Iterator it2 = linkedHashMap2.entrySet().iterator();
                            while (it2.hasNext()) {
                                Map.Entry entry = (Map.Entry) it2.next();
                                String str = (String) entry.getKey();
                                List list3 = (List) entry.getValue();
                                List list4 = (List) linkedHashMap3.get(str);
                                if (list4 == null) {
                                    list4 = EmptyList.INSTANCE;
                                }
                                Iterator it3 = list4.iterator();
                                while (true) {
                                    if (!it3.hasNext()) {
                                        obj2 = th2;
                                        break;
                                    }
                                    obj2 = it3.next();
                                    if (((NotificationEntry) obj2).mSbn.getNotification().isGroupSummary()) {
                                        break;
                                    }
                                }
                                final NotificationEntry notificationEntry2 = (NotificationEntry) obj2;
                                HeadsUpCoordinatorLogger headsUpCoordinatorLogger2 = headsUpCoordinator3.mLogger;
                                int size2 = list3.size();
                                int size3 = list4.size();
                                if (headsUpCoordinatorLogger2.verbose) {
                                    LogLevel logLevel2 = LogLevel.VERBOSE;
                                    it = it2;
                                    HeadsUpCoordinatorLogger$logEvaluatingGroup$2 headsUpCoordinatorLogger$logEvaluatingGroup$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$logEvaluatingGroup$2
                                        @Override // kotlin.jvm.functions.Function1
                                        public final Object invoke(Object obj6) {
                                            LogMessage logMessage = (LogMessage) obj6;
                                            String str1 = logMessage.getStr1();
                                            int int1 = logMessage.getInt1();
                                            int int2 = logMessage.getInt2();
                                            StringBuilder m92m = AbstractC0950x8906c950.m92m("evaluating group for alert transfer: ", str1, " numPostedEntries=", int1, " logicalGroupSize=");
                                            m92m.append(int2);
                                            return m92m.toString();
                                        }
                                    };
                                    LogBuffer logBuffer2 = headsUpCoordinatorLogger2.buffer;
                                    LogMessage obtain2 = logBuffer2.obtain("HeadsUpCoordinator", logLevel2, headsUpCoordinatorLogger$logEvaluatingGroup$2, th2);
                                    obtain2.setStr1(str);
                                    obtain2.setInt1(size2);
                                    obtain2.setInt2(size3);
                                    logBuffer2.commit(obtain2);
                                } else {
                                    it = it2;
                                }
                                if (notificationEntry2 == null) {
                                    Iterator it4 = list3.iterator();
                                    while (it4.hasNext()) {
                                        HeadsUpCoordinator.access$handlePostedEntry(headsUpCoordinator3, (HeadsUpCoordinator.PostedEntry) it4.next(), hunMutatorImpl, "logical-summary-missing");
                                    }
                                } else {
                                    LinkedHashMap linkedHashMap4 = headsUpCoordinator3.mPostedEntries;
                                    String str2 = notificationEntry2.mKey;
                                    HeadsUpCoordinator.PostedEntry postedEntry = (HeadsUpCoordinator.PostedEntry) linkedHashMap4.get(str2);
                                    HeadsUpManager headsUpManager4 = headsUpCoordinator3.mHeadsUpManager;
                                    if (postedEntry != null) {
                                        if (postedEntry.shouldHeadsUpEver) {
                                            if (!postedEntry.wasAdded && !postedEntry.shouldHeadsUpAgain) {
                                            }
                                            z = true;
                                        }
                                        z = false;
                                    } else {
                                        if (!headsUpManager4.isAlerting(notificationEntry2.getKey())) {
                                        }
                                        z = true;
                                    }
                                    if (z) {
                                        HeadsUpCoordinator$onBeforeFinalizeFilter$1$1$3 headsUpCoordinator$onBeforeFinalizeFilter$1$1$3 = new HeadsUpCoordinator$onBeforeFinalizeFilter$1$1$3((Map) lazy2.getValue());
                                        Iterator it5 = new SequencesKt___SequencesKt$sortedWith$1(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list3), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findAlertOverride$1
                                            @Override // kotlin.jvm.functions.Function1
                                            public final Object invoke(Object obj6) {
                                                return Boolean.valueOf(!((HeadsUpCoordinator.PostedEntry) obj6).entry.mSbn.getNotification().isGroupSummary());
                                            }
                                        }), new Comparator() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findAlertOverride$$inlined$sortedBy$1
                                            @Override // java.util.Comparator
                                            public final int compare(Object obj6, Object obj7) {
                                                return ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(-((HeadsUpCoordinator.PostedEntry) obj6).entry.mSbn.getNotification().when), Long.valueOf(-((HeadsUpCoordinator.PostedEntry) obj7).entry.mSbn.getNotification().when));
                                            }
                                        }).iterator();
                                        HeadsUpCoordinator.PostedEntry postedEntry2 = (HeadsUpCoordinator.PostedEntry) (!it5.hasNext() ? null : it5.next());
                                        if (postedEntry2 != null) {
                                            notificationEntry = postedEntry2.entry;
                                        }
                                        notificationEntry = null;
                                        String str3 = notificationEntry != null ? "alertOverride" : "undefined";
                                        boolean containsKey = ((Map) lazy2.getValue()).containsKey(str2);
                                        if (!containsKey && notificationEntry == null) {
                                            final HeadsUpCoordinator$onBeforeFinalizeFilter$1$1$4 headsUpCoordinator$onBeforeFinalizeFilter$1$1$4 = new HeadsUpCoordinator$onBeforeFinalizeFilter$1$1$4((Map) lazy2.getValue());
                                            Iterator it6 = new SequencesKt___SequencesKt$sortedWith$1(SequencesKt___SequencesKt.filter(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list4), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findBestTransferChild$1
                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Object obj6) {
                                                    return Boolean.valueOf(!((NotificationEntry) obj6).mSbn.getNotification().isGroupSummary());
                                                }
                                            }), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findBestTransferChild$2
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Object obj6) {
                                                    return Boolean.valueOf(Function1.this.invoke(((NotificationEntry) obj6).mKey) != GroupLocation.Detached);
                                                }
                                            }), ComparisonsKt__ComparisonsKt.compareBy(new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findBestTransferChild$3
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Object obj6) {
                                                    return Boolean.valueOf(!HeadsUpCoordinator.this.mPostedEntries.containsKey(((NotificationEntry) obj6).mKey));
                                                }
                                            }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findBestTransferChild$4
                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Object obj6) {
                                                    return Long.valueOf(-((NotificationEntry) obj6).mSbn.getNotification().when);
                                                }
                                            })).iterator();
                                            notificationEntry = (NotificationEntry) (!it6.hasNext() ? null : it6.next());
                                            if (notificationEntry != null) {
                                                str3 = "bestChild";
                                            }
                                        }
                                        if (notificationEntry == null) {
                                            Iterator it7 = list3.iterator();
                                            while (it7.hasNext()) {
                                                HeadsUpCoordinator.access$handlePostedEntry(headsUpCoordinator3, (HeadsUpCoordinator.PostedEntry) it7.next(), hunMutatorImpl, "no-transfer-target");
                                            }
                                            lazy = lazy2;
                                            th2 = null;
                                            linkedHashMap = linkedHashMap3;
                                            headsUpCoordinator$onBeforeFinalizeFilter$1 = this;
                                            it2 = it;
                                            lazy2 = lazy;
                                            linkedHashMap3 = linkedHashMap;
                                        } else {
                                            HeadsUpCoordinator.PostedEntry postedEntry3 = (HeadsUpCoordinator.PostedEntry) linkedHashMap4.get(str2);
                                            notificationEntry2.interruption = true;
                                            HeadsUpCoordinatorLogger headsUpCoordinatorLogger3 = headsUpCoordinator3.mLogger;
                                            headsUpCoordinatorLogger3.getClass();
                                            LogLevel logLevel3 = LogLevel.DEBUG;
                                            lazy = lazy2;
                                            HeadsUpCoordinatorLogger$logSummaryMarkedInterrupted$2 headsUpCoordinatorLogger$logSummaryMarkedInterrupted$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$logSummaryMarkedInterrupted$2
                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Object obj6) {
                                                    LogMessage logMessage = (LogMessage) obj6;
                                                    return FontProvider$$ExternalSyntheticOutline0.m32m("marked group summary as interrupted: ", logMessage.getStr1(), " for alert transfer to child: ", logMessage.getStr2());
                                                }
                                            };
                                            linkedHashMap = linkedHashMap3;
                                            LogBuffer logBuffer3 = headsUpCoordinatorLogger3.buffer;
                                            LogMessage obtain3 = logBuffer3.obtain("HeadsUpCoordinator", logLevel3, headsUpCoordinatorLogger$logSummaryMarkedInterrupted$2, null);
                                            obtain3.setStr1(str2);
                                            String str4 = notificationEntry.mKey;
                                            obtain3.setStr2(str4);
                                            logBuffer3.commit(obtain3);
                                            if (containsKey) {
                                                headsUpManager2 = headsUpManager4;
                                                z2 = false;
                                                z3 = true;
                                                if (postedEntry3 == null || !headsUpCoordinatorLogger3.verbose) {
                                                    z4 = true;
                                                } else {
                                                    th = null;
                                                    LogMessage obtain4 = logBuffer3.obtain("HeadsUpCoordinator", LogLevel.VERBOSE, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$logPostedEntryWillNotEvaluate$2
                                                        @Override // kotlin.jvm.functions.Function1
                                                        public final Object invoke(Object obj6) {
                                                            LogMessage logMessage = (LogMessage) obj6;
                                                            return FontProvider$$ExternalSyntheticOutline0.m32m("will not evaluate posted entry ", logMessage.getStr1(), ": reason=", logMessage.getStr2());
                                                        }
                                                    }, null);
                                                    CarrierTextManagerLogger$$ExternalSyntheticOutline0.m83m(obtain4, postedEntry3.key, "attached-summary-transferred", logBuffer3, obtain4);
                                                    filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list3), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$onBeforeFinalizeFilter$1$1$6
                                                        {
                                                            super(1);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function1
                                                        public final Object invoke(Object obj6) {
                                                            return Boolean.valueOf(!Intrinsics.areEqual(((HeadsUpCoordinator.PostedEntry) obj6).key, NotificationEntry.this.mKey));
                                                        }
                                                    }));
                                                    while (filteringSequence$iterator$1.hasNext()) {
                                                        HeadsUpCoordinator.PostedEntry postedEntry4 = (HeadsUpCoordinator.PostedEntry) filteringSequence$iterator$1.next();
                                                        if (Intrinsics.areEqual(str4, postedEntry4.key)) {
                                                            postedEntry4.shouldHeadsUpEver = z3;
                                                            postedEntry4.shouldHeadsUpAgain = z3;
                                                            HeadsUpCoordinator.access$handlePostedEntry(headsUpCoordinator3, postedEntry4, hunMutatorImpl, "child-alert-transfer-target-" + ((Object) str3));
                                                            z2 = z3;
                                                        } else {
                                                            HeadsUpCoordinator.access$handlePostedEntry(headsUpCoordinator3, postedEntry4, hunMutatorImpl, "child-alert-non-target");
                                                        }
                                                    }
                                                    if (!z2) {
                                                        HeadsUpCoordinator.access$handlePostedEntry(headsUpCoordinator3, new HeadsUpCoordinator.PostedEntry(notificationEntry, false, false, true, true, headsUpManager2.isAlerting(str4), headsUpCoordinator3.isEntryBinding(notificationEntry)), hunMutatorImpl, "non-posted-child-alert-transfer-target-" + ((Object) str3));
                                                    }
                                                    th2 = th;
                                                    headsUpCoordinator$onBeforeFinalizeFilter$1 = this;
                                                    it2 = it;
                                                    lazy2 = lazy;
                                                    linkedHashMap3 = linkedHashMap;
                                                }
                                            } else {
                                                if (postedEntry3 != null) {
                                                    z5 = false;
                                                    postedEntry3.shouldHeadsUpEver = false;
                                                    z4 = true;
                                                    headsUpManager3 = headsUpManager4;
                                                } else {
                                                    z5 = false;
                                                    headsUpManager3 = headsUpManager4;
                                                    postedEntry3 = new HeadsUpCoordinator.PostedEntry(notificationEntry2, false, false, false, false, headsUpManager4.isAlerting(str2), headsUpCoordinator3.isEntryBinding(notificationEntry2));
                                                    z4 = true;
                                                }
                                                HeadsUpCoordinator.access$handlePostedEntry(headsUpCoordinator3, postedEntry3, hunMutatorImpl, "detached-summary-remove-alert");
                                                z2 = z5;
                                                headsUpManager2 = headsUpManager3;
                                            }
                                            th = null;
                                            z3 = z4;
                                            filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list3), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$onBeforeFinalizeFilter$1$1$6
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Object obj6) {
                                                    return Boolean.valueOf(!Intrinsics.areEqual(((HeadsUpCoordinator.PostedEntry) obj6).key, NotificationEntry.this.mKey));
                                                }
                                            }));
                                            while (filteringSequence$iterator$1.hasNext()) {
                                            }
                                            if (!z2) {
                                            }
                                            th2 = th;
                                            headsUpCoordinator$onBeforeFinalizeFilter$1 = this;
                                            it2 = it;
                                            lazy2 = lazy;
                                            linkedHashMap3 = linkedHashMap;
                                        }
                                    } else {
                                        Iterator it8 = list3.iterator();
                                        while (it8.hasNext()) {
                                            HeadsUpCoordinator.access$handlePostedEntry(headsUpCoordinator3, (HeadsUpCoordinator.PostedEntry) it8.next(), hunMutatorImpl, "logical-summary-not-alerting");
                                        }
                                    }
                                }
                                lazy = lazy2;
                                linkedHashMap = linkedHashMap3;
                                headsUpCoordinator$onBeforeFinalizeFilter$1 = this;
                                it2 = it;
                                lazy2 = lazy;
                                linkedHashMap3 = linkedHashMap;
                            }
                            HeadsUpCoordinator.this.mPostedEntries.clear();
                            HeadsUpCoordinator headsUpCoordinator4 = HeadsUpCoordinator.this;
                            ((SystemClockImpl) headsUpCoordinator4.mSystemClock).getClass();
                            long currentTimeMillis = System.currentTimeMillis() - 2000;
                            ArraySet arraySet = new ArraySet();
                            ArrayMap arrayMap = headsUpCoordinator4.mEntriesUpdateTimes;
                            for (Map.Entry entry2 : arrayMap.entrySet()) {
                                String str5 = (String) entry2.getKey();
                                Long l = (Long) entry2.getValue();
                                if (l == null || currentTimeMillis > l.longValue()) {
                                    arraySet.add(str5);
                                }
                            }
                            arrayMap.removeAll(arraySet);
                            ArraySet arraySet2 = new ArraySet();
                            ArrayMap arrayMap2 = headsUpCoordinator4.mFSIUpdateCandidates;
                            for (Map.Entry entry3 : arrayMap2.entrySet()) {
                                String str6 = (String) entry3.getKey();
                                Long l2 = (Long) entry3.getValue();
                                if (l2 == null || currentTimeMillis > l2.longValue()) {
                                    arraySet2.add(str6);
                                }
                            }
                            arrayMap2.removeAll(arraySet2);
                        }
                        return Unit.INSTANCE;
                    }
                });
            }
        });
        notifPipeline.addPromoter(this.mNotifPromoter);
        notifPipeline.addNotificationLifetimeExtender(this.mLifetimeExtender);
        this.mRemoteInputManager.mActionPressListeners.addIfAbsent(this.mActionPressListener);
        ((ArrayList) headsUpManager.mCallbacks).add(new HeadsUpCoordinator$attach$3(this));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$bindForAsyncHeadsUp$2] */
    public final void bindForAsyncHeadsUp(PostedEntry postedEntry) {
        this.mEntriesBindingUntil.put(postedEntry.key, Long.valueOf(this.mNow + 1000));
        this.mHeadsUpViewBinder.bindHeadsUpView(postedEntry.entry, new NotifBindPipeline.BindCallback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$bindForAsyncHeadsUp$2
            @Override // com.android.systemui.statusbar.notification.row.NotifBindPipeline.BindCallback
            public final void onBindFinished(NotificationEntry notificationEntry) {
                HeadsUpCoordinator headsUpCoordinator = HeadsUpCoordinator.this;
                HeadsUpManager headsUpManager = headsUpCoordinator.mHeadsUpManager;
                headsUpManager.mLogger.logShowNotification(notificationEntry);
                AlertingNotificationManager.AlertEntry createAlertEntry = headsUpManager.createAlertEntry();
                createAlertEntry.setEntry(notificationEntry);
                ArrayMap arrayMap = headsUpManager.mAlertEntries;
                String str = notificationEntry.mKey;
                arrayMap.put(str, createAlertEntry);
                headsUpManager.onAlertEntryAdded(createAlertEntry);
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                if (expandableNotificationRow != null) {
                    expandableNotificationRow.sendAccessibilityEvent(2048);
                }
                notificationEntry.mIsAlerting = true;
                headsUpManager.updateNotification(str, true);
                notificationEntry.interruption = true;
                headsUpCoordinator.mEntriesBindingUntil.remove(str);
                if (notificationEntry.row.mIsPinned) {
                    NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.get(NotificationColorPicker.class);
                    ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
                    notificationColorPicker.getClass();
                    notificationEntry.row.applyHeadsUpBackground(NotificationColorPicker.isCustom(expandableNotificationRow2));
                }
            }
        });
    }

    public final void cancelHeadsUpBind(NotificationEntry notificationEntry) {
        this.mEntriesBindingUntil.remove(notificationEntry.mKey);
        this.mHeadsUpViewBinder.abortBindCallback(notificationEntry);
    }

    public final boolean isEntryBinding(ListEntry listEntry) {
        Long l = (Long) this.mEntriesBindingUntil.get(listEntry.getKey());
        return l != null && l.longValue() >= this.mNow;
    }

    public final void setUpdateTime(NotificationEntry notificationEntry, long j) {
        this.mEntriesUpdateTimes.put(notificationEntry.mKey, Long.valueOf(j));
    }
}
