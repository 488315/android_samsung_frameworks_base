package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.Notification;
import android.app.RemoteInput;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.NotificationClicker$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.notification.collection.NotifInflaterImpl;
import com.android.systemui.statusbar.notification.collection.NotifInflaterImpl.C27911;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManagerImpl;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustment;
import com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.render.NotifViewBarn;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager;
import com.android.systemui.statusbar.notification.row.RowContentBindParams;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kotlin.Pair;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.MergingSequence$iterator$1;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PreparationCoordinator implements Coordinator {
    public final NotifUiAdjustmentProvider mAdjustmentProvider;
    public final BindEventManagerImpl mBindEventManager;
    public final int mChildBindCutoff;
    public final ArraySet mInflatingNotifs;
    public final ArrayMap mInflationAdjustments;
    public final C28264 mInflationErrorListener;
    public final ArrayMap mInflationStates;
    public boolean mIsChanged;
    public final PreparationCoordinatorLogger mLogger;
    public final long mMaxGroupInflationDelay;
    public final C28231 mNotifCollectionListener;
    public final NotifInflationErrorManager mNotifErrorManager;
    public final NotifInflater mNotifInflater;
    public final C28253 mNotifInflatingFilter;
    public final C28242 mNotifInflationErrorFilter;
    public NotifCollection$$ExternalSyntheticLambda4 mNotifUpdate;
    public final PreparationCoordinator$$ExternalSyntheticLambda0 mSettingsHelperCallback;
    public final IStatusBarService mStatusBarService;
    public final NotifViewBarn mViewBarn;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator$4 */
    public final class C28264 {
        public C28264() {
        }
    }

    public static void $r8$lambda$T1DwXSSxf_XS7CenlmlbkE5FMFw(PreparationCoordinator preparationCoordinator, NotificationEntry notificationEntry, ExpandableNotificationRowController expandableNotificationRowController) {
        PreparationCoordinatorLogger preparationCoordinatorLogger = preparationCoordinator.mLogger;
        preparationCoordinatorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        PreparationCoordinatorLogger$logNotifInflated$2 preparationCoordinatorLogger$logNotifInflated$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$logNotifInflated$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m21m("Inflation completed for notif ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = preparationCoordinatorLogger.buffer;
        LogMessage obtain = logBuffer.obtain("PreparationCoordinator", logLevel, preparationCoordinatorLogger$logNotifInflated$2, null);
        NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain, logBuffer, obtain);
        preparationCoordinator.mInflatingNotifs.remove(notificationEntry);
        preparationCoordinator.mViewBarn.rowMap.put(notificationEntry.mKey, expandableNotificationRowController);
        preparationCoordinator.mInflationStates.put(notificationEntry, 1);
        Iterator it = preparationCoordinator.mBindEventManager.listeners.iterator();
        while (it.hasNext()) {
            ((BindEventManager.Listener) it.next()).onViewBound(notificationEntry);
        }
        preparationCoordinator.mNotifInflatingFilter.invalidateList("onInflationFinished for " + NotificationUtils.logKey(notificationEntry));
    }

    public PreparationCoordinator(PreparationCoordinatorLogger preparationCoordinatorLogger, NotifInflater notifInflater, NotifInflationErrorManager notifInflationErrorManager, NotifViewBarn notifViewBarn, NotifUiAdjustmentProvider notifUiAdjustmentProvider, IStatusBarService iStatusBarService, BindEventManagerImpl bindEventManagerImpl) {
        this(preparationCoordinatorLogger, notifInflater, notifInflationErrorManager, notifViewBarn, notifUiAdjustmentProvider, iStatusBarService, bindEventManagerImpl, 9, 500L);
    }

    public final void abortInflation(NotificationEntry notificationEntry, String str) {
        ((NotifInflaterImpl) this.mNotifInflater).getClass();
        boolean abortTask = notificationEntry.abortTask();
        boolean remove = this.mInflatingNotifs.remove(notificationEntry);
        if (abortTask || remove) {
            PreparationCoordinatorLogger preparationCoordinatorLogger = this.mLogger;
            preparationCoordinatorLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            PreparationCoordinatorLogger$logInflationAborted$2 preparationCoordinatorLogger$logInflationAborted$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$logInflationAborted$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    return FontProvider$$ExternalSyntheticOutline0.m32m("Infation aborted for notif ", logMessage.getStr1(), " reason=", logMessage.getStr2());
                }
            };
            LogBuffer logBuffer = preparationCoordinatorLogger.buffer;
            LogMessage obtain = logBuffer.obtain("PreparationCoordinator", logLevel, preparationCoordinatorLogger$logInflationAborted$2, null);
            obtain.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
            obtain.setStr2(str);
            logBuffer.commit(obtain);
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        ((ArrayList) this.mNotifErrorManager.mListeners).add(this.mInflationErrorListener);
        Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                invalidateList("adjustmentProviderChanged");
            }
        };
        NotifUiAdjustmentProvider notifUiAdjustmentProvider = this.mAdjustmentProvider;
        ListenerSet listenerSet = notifUiAdjustmentProvider.dirtyListeners;
        if (listenerSet.isEmpty()) {
            ((NotificationLockscreenUserManagerImpl) notifUiAdjustmentProvider.lockscreenUserManager).mNotifStateChangedListeners.addIfAbsent(notifUiAdjustmentProvider.notifStateChangedListener);
            notifUiAdjustmentProvider.updateSnoozeEnabled();
            notifUiAdjustmentProvider.secureSettings.registerContentObserverForUser("show_notification_snooze", notifUiAdjustmentProvider.settingsObserver, -1);
        }
        listenerSet.addIfAbsent(runnable);
        notifPipeline.addCollectionListener(this.mNotifCollectionListener);
        notifPipeline.addOnBeforeFinalizeFilterListener(new OnBeforeFinalizeFilterListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator$$ExternalSyntheticLambda2
            /* JADX WARN: Code restructure failed: missing block: B:37:0x00c2, code lost:
            
                continue;
             */
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onBeforeFinalizeFilter(List list) {
                PreparationCoordinator preparationCoordinator = PreparationCoordinator.this;
                preparationCoordinator.getClass();
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    ListEntry listEntry = (ListEntry) list.get(i);
                    if (listEntry instanceof GroupEntry) {
                        GroupEntry groupEntry = (GroupEntry) listEntry;
                        preparationCoordinator.inflateRequiredNotifViews(groupEntry.mSummary);
                        int i2 = 0;
                        while (true) {
                            List list2 = groupEntry.mUnmodifiableChildren;
                            if (i2 < list2.size()) {
                                NotificationEntry notificationEntry = (NotificationEntry) list2.get(i2);
                                if (i2 < preparationCoordinator.mChildBindCutoff) {
                                    preparationCoordinator.inflateRequiredNotifViews(notificationEntry);
                                } else {
                                    if (preparationCoordinator.mInflatingNotifs.contains(notificationEntry)) {
                                        preparationCoordinator.abortInflation(notificationEntry, "Past last visible group child");
                                    }
                                    int inflationState = preparationCoordinator.getInflationState(notificationEntry);
                                    if (inflationState == 1 || inflationState == 2) {
                                        PreparationCoordinatorLogger preparationCoordinatorLogger = preparationCoordinator.mLogger;
                                        preparationCoordinatorLogger.getClass();
                                        LogLevel logLevel = LogLevel.DEBUG;
                                        PreparationCoordinatorLogger$logFreeNotifViews$2 preparationCoordinatorLogger$logFreeNotifViews$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$logFreeNotifViews$2
                                            @Override // kotlin.jvm.functions.Function1
                                            public final Object invoke(Object obj) {
                                                LogMessage logMessage = (LogMessage) obj;
                                                return FontProvider$$ExternalSyntheticOutline0.m32m("Freeing content views for notif ", logMessage.getStr1(), " reason=", logMessage.getStr2());
                                            }
                                        };
                                        LogBuffer logBuffer = preparationCoordinatorLogger.buffer;
                                        LogMessage obtain = logBuffer.obtain("PreparationCoordinator", logLevel, preparationCoordinatorLogger$logFreeNotifViews$2, null);
                                        obtain.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
                                        obtain.setStr2("Past last visible group child");
                                        logBuffer.commit(obtain);
                                        preparationCoordinator.mViewBarn.rowMap.remove(notificationEntry.getKey());
                                        NotificationRowBinderImpl notificationRowBinderImpl = ((NotifInflaterImpl) preparationCoordinator.mNotifInflater).mNotificationRowBinder;
                                        if (notificationRowBinderImpl == null) {
                                            throw new RuntimeException("NotificationRowBinder must be attached before using NotifInflaterImpl.");
                                        }
                                        if (notificationEntry.rowExists()) {
                                            RowContentBindStage rowContentBindStage = notificationRowBinderImpl.mRowContentBindStage;
                                            RowContentBindParams rowContentBindParams = (RowContentBindParams) rowContentBindStage.getStageParams(notificationEntry);
                                            rowContentBindParams.markContentViewsFreeable(1);
                                            rowContentBindParams.markContentViewsFreeable(2);
                                            rowContentBindParams.markContentViewsFreeable(8);
                                            rowContentBindStage.requestRebind(notificationEntry, null);
                                        }
                                        preparationCoordinator.mInflationStates.put(notificationEntry, 0);
                                    } else {
                                        continue;
                                    }
                                }
                                i2++;
                            }
                        }
                    } else {
                        preparationCoordinator.inflateRequiredNotifViews((NotificationEntry) listEntry);
                    }
                }
                if (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE && preparationCoordinator.mIsChanged) {
                    preparationCoordinator.mIsChanged = false;
                }
            }
        });
        notifPipeline.addFinalizeFilter(this.mNotifInflationErrorFilter);
        notifPipeline.addFinalizeFilter(this.mNotifInflatingFilter);
        if (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE) {
            ((SettingsHelper) Dependency.get(SettingsHelper.class)).registerCallback(this.mSettingsHelperCallback, Settings.Secure.getUriFor("lock_screen_allow_private_notifications_when_unsecure"));
        }
        NotifCollection notifCollection = notifPipeline.mNotifCollection;
        notifCollection.getClass();
        this.mNotifUpdate = new NotifCollection$$ExternalSyntheticLambda4(notifCollection, "PreparationCoordinator");
    }

    public final int getInflationState(NotificationEntry notificationEntry) {
        Integer num = (Integer) this.mInflationStates.get(notificationEntry);
        Objects.requireNonNull(num, "Asking state of a notification preparation coordinator doesn't know about");
        return num.intValue();
    }

    public final void inflateEntry(NotificationEntry notificationEntry, NotifUiAdjustment notifUiAdjustment, String str) {
        abortInflation(notificationEntry, str);
        this.mInflationAdjustments.put(notificationEntry, notifUiAdjustment);
        this.mInflatingNotifs.add(notificationEntry);
        NotifInflater.Params params = new NotifInflater.Params(notifUiAdjustment.isMinimized, str, notifUiAdjustment.isSnoozeEnabled);
        PreparationCoordinator$$ExternalSyntheticLambda3 preparationCoordinator$$ExternalSyntheticLambda3 = new PreparationCoordinator$$ExternalSyntheticLambda3(this, 0);
        NotifInflaterImpl notifInflaterImpl = (NotifInflaterImpl) this.mNotifInflater;
        NotifInflationErrorManager notifInflationErrorManager = notifInflaterImpl.mNotifErrorManager;
        try {
            NotificationRowBinderImpl notificationRowBinderImpl = notifInflaterImpl.mNotificationRowBinder;
            if (notificationRowBinderImpl == null) {
                throw new RuntimeException("NotificationRowBinder must be attached before using NotifInflaterImpl.");
            }
            notificationRowBinderImpl.inflateViews(notificationEntry, params, notifInflaterImpl.new C27911(preparationCoordinator$$ExternalSyntheticLambda3));
        } catch (InflationException e) {
            notifInflationErrorManager.setInflationError(notificationEntry, e);
        } catch (RuntimeException e2) {
            notifInflationErrorManager.setInflationError(notificationEntry, e2);
        }
    }

    public final void inflateRequiredNotifViews(NotificationEntry notificationEntry) {
        NotifUiAdjustmentProvider notifUiAdjustmentProvider = this.mAdjustmentProvider;
        notifUiAdjustmentProvider.getClass();
        String str = notificationEntry.mKey;
        List<Notification.Action> smartActions = notificationEntry.mRanking.getSmartActions();
        List<CharSequence> smartReplies = notificationEntry.mRanking.getSmartReplies();
        boolean isConversation = notificationEntry.mRanking.isConversation();
        boolean z = notifUiAdjustmentProvider.isSnoozeSettingsEnabled && !notificationEntry.isCanceled();
        if (notificationEntry.getSection() == null) {
            throw new IllegalStateException("Entry must have a section to determine if minimized".toString());
        }
        GroupEntry parent = notificationEntry.getParent();
        if (parent == null) {
            throw new IllegalStateException("Entry must have a parent to determine if minimized".toString());
        }
        NotifUiAdjustment notifUiAdjustment = new NotifUiAdjustment(str, smartActions, smartReplies, isConversation, z, (!notifUiAdjustmentProvider.highPriorityProvider.isHighPriority(notificationEntry, true) && notificationEntry.isAmbient()) && (Intrinsics.areEqual(parent, GroupEntry.ROOT_ENTRY) || Intrinsics.areEqual(parent.mSummary, notificationEntry)), ((NotificationLockscreenUserManagerImpl) notifUiAdjustmentProvider.lockscreenUserManager).needsRedaction(notificationEntry));
        if (this.mInflatingNotifs.contains(notificationEntry)) {
            if (needToReinflate(notificationEntry, notifUiAdjustment, "Inflating notification has no adjustments")) {
                inflateEntry(notificationEntry, notifUiAdjustment, "adjustment changed while inflating");
                return;
            }
            return;
        }
        ArrayMap arrayMap = this.mInflationStates;
        if (((Integer) arrayMap.get(notificationEntry)) == null) {
            ExifInterface$$ExternalSyntheticOutline0.m36m(new StringBuilder("entry : "), notificationEntry.mKey, " inflationState is null during inflateRequiredNotifViews", "PreparationCoordinator");
            return;
        }
        int intValue = ((Integer) arrayMap.get(notificationEntry)).intValue();
        if (intValue == -1) {
            if (needToReinflate(notificationEntry, notifUiAdjustment, null)) {
                inflateEntry(notificationEntry, notifUiAdjustment, "adjustment changed after error");
            }
        } else {
            if (intValue == 0) {
                inflateEntry(notificationEntry, notifUiAdjustment, "entryAdded");
                return;
            }
            if (intValue != 1) {
                if (intValue != 2) {
                    return;
                }
                rebind(notificationEntry, notifUiAdjustment, "entryUpdated");
            } else if (needToReinflate(notificationEntry, notifUiAdjustment, "Fully inflated notification has no adjustments")) {
                rebind(notificationEntry, notifUiAdjustment, "adjustment changed after inflated");
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:74:0x0190  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0198 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:78:? A[LOOP:1: B:54:0x0112->B:78:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01a1 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:89:? A[LOOP:0: B:33:0x007d->B:89:?, LOOP_END, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean needToReinflate(NotificationEntry notificationEntry, NotifUiAdjustment notifUiAdjustment, String str) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        NotifUiAdjustment notifUiAdjustment2 = (NotifUiAdjustment) this.mInflationAdjustments.get(notificationEntry);
        if (notifUiAdjustment2 == null) {
            if (str == null) {
                return true;
            }
            throw new IllegalStateException(str);
        }
        if (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE && this.mIsChanged) {
            if (notificationEntry.mSbn.getNotification().publicVersion != null) {
                return true;
            }
        }
        NotifUiAdjustment.Companion.getClass();
        if (notifUiAdjustment2 != notifUiAdjustment) {
            if (notifUiAdjustment2.isConversation != notifUiAdjustment.isConversation || notifUiAdjustment2.isSnoozeEnabled != notifUiAdjustment.isSnoozeEnabled || notifUiAdjustment2.isMinimized != notifUiAdjustment.isMinimized || notifUiAdjustment2.needsRedaction != notifUiAdjustment.needsRedaction) {
                return true;
            }
            List list = notifUiAdjustment2.smartActions;
            List list2 = notifUiAdjustment.smartActions;
            if (list != list2) {
                if (list.size() == list2.size()) {
                    MergingSequence$iterator$1 mergingSequence$iterator$1 = new MergingSequence$iterator$1(SequencesKt___SequencesKt.zip(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list2)));
                    while (mergingSequence$iterator$1.hasNext()) {
                        Pair pair = (Pair) mergingSequence$iterator$1.next();
                        if (TextUtils.equals(((Notification.Action) pair.getFirst()).title, ((Notification.Action) pair.getSecond()).title)) {
                            Icon icon = ((Notification.Action) pair.getFirst()).getIcon();
                            Icon icon2 = ((Notification.Action) pair.getSecond()).getIcon();
                            if (!(icon != icon2 && (icon == null || icon2 == null || !icon.sameAs(icon2))) && Intrinsics.areEqual(((Notification.Action) pair.getFirst()).actionIntent, ((Notification.Action) pair.getSecond()).actionIntent)) {
                                RemoteInput[] remoteInputs = ((Notification.Action) pair.getFirst()).getRemoteInputs();
                                RemoteInput[] remoteInputs2 = ((Notification.Action) pair.getSecond()).getRemoteInputs();
                                if (remoteInputs != remoteInputs2) {
                                    if (remoteInputs != null && remoteInputs2 != null && remoteInputs.length == remoteInputs2.length) {
                                        MergingSequence$iterator$1 mergingSequence$iterator$12 = new MergingSequence$iterator$1(SequencesKt___SequencesKt.zip(ArraysKt___ArraysKt.asSequence(remoteInputs), ArraysKt___ArraysKt.asSequence(remoteInputs2)));
                                        while (mergingSequence$iterator$12.hasNext()) {
                                            Pair pair2 = (Pair) mergingSequence$iterator$12.next();
                                            if (TextUtils.equals(((RemoteInput) pair2.getFirst()).getLabel(), ((RemoteInput) pair2.getSecond()).getLabel())) {
                                                CharSequence[] choices = ((RemoteInput) pair2.getFirst()).getChoices();
                                                CharSequence[] choices2 = ((RemoteInput) pair2.getSecond()).getChoices();
                                                if (choices != choices2) {
                                                    if (choices != null && choices2 != null && choices.length == choices2.length) {
                                                        MergingSequence$iterator$1 mergingSequence$iterator$13 = new MergingSequence$iterator$1(SequencesKt___SequencesKt.zip(ArraysKt___ArraysKt.asSequence(choices), ArraysKt___ArraysKt.asSequence(choices2)));
                                                        while (mergingSequence$iterator$13.hasNext()) {
                                                            Pair pair3 = (Pair) mergingSequence$iterator$13.next();
                                                            if (!TextUtils.equals((CharSequence) pair3.getFirst(), (CharSequence) pair3.getSecond())) {
                                                            }
                                                        }
                                                    }
                                                    z5 = true;
                                                    if (!z5) {
                                                        z4 = false;
                                                        if (!z4) {
                                                        }
                                                    }
                                                }
                                                z5 = false;
                                                if (!z5) {
                                                }
                                            }
                                            z4 = true;
                                            if (!z4) {
                                            }
                                        }
                                    }
                                    z3 = true;
                                    if (!z3) {
                                        z2 = false;
                                        if (!z2) {
                                        }
                                    }
                                }
                                z3 = false;
                                if (!z3) {
                                }
                            }
                        }
                        z2 = true;
                        if (!z2) {
                        }
                    }
                }
                z = true;
                if (!z || !Intrinsics.areEqual(notifUiAdjustment.smartReplies, notifUiAdjustment2.smartReplies)) {
                }
            }
            z = false;
            return !z ? true : true;
        }
        return false;
    }

    public final void rebind(NotificationEntry notificationEntry, NotifUiAdjustment notifUiAdjustment, String str) {
        this.mInflationAdjustments.put(notificationEntry, notifUiAdjustment);
        this.mInflatingNotifs.add(notificationEntry);
        NotifInflater.Params params = new NotifInflater.Params(notifUiAdjustment.isMinimized, str, notifUiAdjustment.isSnoozeEnabled);
        PreparationCoordinator$$ExternalSyntheticLambda3 preparationCoordinator$$ExternalSyntheticLambda3 = new PreparationCoordinator$$ExternalSyntheticLambda3(this, 1);
        NotifInflaterImpl notifInflaterImpl = (NotifInflaterImpl) this.mNotifInflater;
        NotifInflationErrorManager notifInflationErrorManager = notifInflaterImpl.mNotifErrorManager;
        try {
            NotificationRowBinderImpl notificationRowBinderImpl = notifInflaterImpl.mNotificationRowBinder;
            if (notificationRowBinderImpl == null) {
                throw new RuntimeException("NotificationRowBinder must be attached before using NotifInflaterImpl.");
            }
            notificationRowBinderImpl.inflateViews(notificationEntry, params, notifInflaterImpl.new C27911(preparationCoordinator$$ExternalSyntheticLambda3));
        } catch (InflationException e) {
            notifInflationErrorManager.setInflationError(notificationEntry, e);
        } catch (RuntimeException e2) {
            notifInflationErrorManager.setInflationError(notificationEntry, e2);
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator$1] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator$2] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator$3] */
    public PreparationCoordinator(PreparationCoordinatorLogger preparationCoordinatorLogger, NotifInflater notifInflater, NotifInflationErrorManager notifInflationErrorManager, NotifViewBarn notifViewBarn, NotifUiAdjustmentProvider notifUiAdjustmentProvider, IStatusBarService iStatusBarService, BindEventManagerImpl bindEventManagerImpl, int i, long j) {
        this.mInflationStates = new ArrayMap();
        this.mInflationAdjustments = new ArrayMap();
        this.mInflatingNotifs = new ArraySet();
        this.mSettingsHelperCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                PreparationCoordinator.this.mIsChanged = true;
            }
        };
        this.mNotifCollectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator.1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryCleanUp(NotificationEntry notificationEntry) {
                PreparationCoordinator preparationCoordinator = PreparationCoordinator.this;
                preparationCoordinator.mInflationStates.remove(notificationEntry);
                preparationCoordinator.mViewBarn.rowMap.remove(notificationEntry.getKey());
                preparationCoordinator.mInflationAdjustments.remove(notificationEntry);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryInit(NotificationEntry notificationEntry) {
                PreparationCoordinator.this.mInflationStates.put(notificationEntry, 0);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryRemoved(NotificationEntry notificationEntry, int i2) {
                PreparationCoordinator.this.abortInflation(notificationEntry, "entryRemoved reason=" + i2);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryUpdated(NotificationEntry notificationEntry) {
                PreparationCoordinator preparationCoordinator = PreparationCoordinator.this;
                preparationCoordinator.abortInflation(notificationEntry, "entryUpdated");
                int inflationState = preparationCoordinator.getInflationState(notificationEntry);
                if (inflationState == 1) {
                    preparationCoordinator.mInflationStates.put(notificationEntry, 2);
                } else if (inflationState == -1) {
                    preparationCoordinator.mInflationStates.put(notificationEntry, 0);
                }
            }
        };
        this.mNotifInflationErrorFilter = new NotifFilter("PreparationCoordinatorInflationError") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator.2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
            public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j2) {
                return PreparationCoordinator.this.getInflationState(notificationEntry) == -1;
            }
        };
        this.mNotifInflatingFilter = new NotifFilter("PreparationCoordinatorInflating") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator.3
            public final Map mIsDelayedGroupCache = new ArrayMap();

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable
            public final void onCleanup() {
                ((ArrayMap) this.mIsDelayedGroupCache).clear();
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
            public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j2) {
                boolean z;
                GroupEntry parent = notificationEntry.getParent();
                Objects.requireNonNull(parent);
                ArrayMap arrayMap = (ArrayMap) this.mIsDelayedGroupCache;
                Boolean bool = (Boolean) arrayMap.get(parent);
                PreparationCoordinator preparationCoordinator = PreparationCoordinator.this;
                if (bool == null) {
                    preparationCoordinator.getClass();
                    if (parent != GroupEntry.ROOT_ENTRY && !parent.wasAttachedInPreviousPass()) {
                        boolean z2 = j2 - parent.mCreationTime > preparationCoordinator.mMaxGroupInflationDelay;
                        PreparationCoordinatorLogger preparationCoordinatorLogger2 = preparationCoordinator.mLogger;
                        if (z2) {
                            preparationCoordinatorLogger2.getClass();
                            LogLevel logLevel = LogLevel.WARNING;
                            PreparationCoordinatorLogger$logGroupInflationTookTooLong$2 preparationCoordinatorLogger$logGroupInflationTookTooLong$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$logGroupInflationTookTooLong$2
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    return PathParser$$ExternalSyntheticOutline0.m29m("Group inflation took too long for ", ((LogMessage) obj).getStr1(), ", releasing children early");
                                }
                            };
                            LogBuffer logBuffer = preparationCoordinatorLogger2.buffer;
                            LogMessage obtain = logBuffer.obtain("PreparationCoordinator", logLevel, preparationCoordinatorLogger$logGroupInflationTookTooLong$2, null);
                            obtain.setStr1(NotificationUtilsKt.getLogKey(parent));
                            logBuffer.commit(obtain);
                        } else {
                            NotificationEntry notificationEntry2 = parent.mSummary;
                            if (notificationEntry2 != null) {
                                int inflationState = preparationCoordinator.getInflationState(notificationEntry2);
                                if (!(inflationState == 1 || inflationState == 2)) {
                                    preparationCoordinatorLogger2.logDelayingGroupRelease(parent, parent.mSummary);
                                    z = true;
                                    bool = Boolean.valueOf(z);
                                    arrayMap.put(parent, bool);
                                }
                            }
                            for (NotificationEntry notificationEntry3 : parent.mUnmodifiableChildren) {
                                if (preparationCoordinator.mInflatingNotifs.contains(notificationEntry3) && !notificationEntry3.wasAttachedInPreviousPass()) {
                                    preparationCoordinatorLogger2.logDelayingGroupRelease(parent, notificationEntry3);
                                    z = true;
                                    break;
                                }
                            }
                            preparationCoordinatorLogger2.getClass();
                            LogLevel logLevel2 = LogLevel.DEBUG;
                            PreparationCoordinatorLogger$logDoneWaitingForGroupInflation$2 preparationCoordinatorLogger$logDoneWaitingForGroupInflation$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$logDoneWaitingForGroupInflation$2
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    return PathParser$$ExternalSyntheticOutline0.m29m("Finished inflating all members of group ", ((LogMessage) obj).getStr1(), ", releasing group");
                                }
                            };
                            LogBuffer logBuffer2 = preparationCoordinatorLogger2.buffer;
                            LogMessage obtain2 = logBuffer2.obtain("PreparationCoordinator", logLevel2, preparationCoordinatorLogger$logDoneWaitingForGroupInflation$2, null);
                            obtain2.setStr1(NotificationUtilsKt.getLogKey(parent));
                            logBuffer2.commit(obtain2);
                        }
                    }
                    z = false;
                    bool = Boolean.valueOf(z);
                    arrayMap.put(parent, bool);
                }
                int inflationState2 = preparationCoordinator.getInflationState(notificationEntry);
                return !(inflationState2 == 1 || inflationState2 == 2) || bool.booleanValue();
            }
        };
        this.mInflationErrorListener = new C28264();
        this.mLogger = preparationCoordinatorLogger;
        this.mNotifInflater = notifInflater;
        this.mNotifErrorManager = notifInflationErrorManager;
        this.mViewBarn = notifViewBarn;
        this.mAdjustmentProvider = notifUiAdjustmentProvider;
        this.mStatusBarService = iStatusBarService;
        this.mChildBindCutoff = i;
        this.mMaxGroupInflationDelay = j;
        this.mBindEventManager = bindEventManagerImpl;
    }
}
