package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.RemoteInput;
import android.graphics.drawable.Icon;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListAttachState;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.NotifInflaterImpl;
import com.android.systemui.statusbar.notification.collection.NotifInflaterLogger;
import com.android.systemui.statusbar.notification.collection.NotifInflaterLogger$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManagerImpl;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustment;
import com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.notifcollection.InternalNotifUpdater;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.notifcollection.UpdateSource;
import com.android.systemui.statusbar.notification.collection.render.NotifViewBarn;
import com.android.systemui.statusbar.notification.collection.render.NotifViewController;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager;
import com.android.systemui.statusbar.notification.row.RowContentBindParams;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import com.android.systemui.statusbar.notification.row.icon.AppIconProvider;
import com.android.systemui.statusbar.notification.row.icon.NotificationIconStyleProvider;
import com.android.systemui.statusbar.notification.row.shared.AsyncGroupHeaderViewInflation;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;
import com.android.systemui.statusbar.notification.row.shared.LockscreenOtpRedaction;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.phone.ongoingactivity.HistoryDumpList;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.policy.AppLockNotificationControllerImpl;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.SettingsHelper;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.MergingSequence;
import kotlin.sequences.MergingSequence.AnonymousClass1;
import kotlin.sequences.SequencesKt___SequencesKt;

@CoordinatorScope
/* loaded from: classes3.dex */
public class PreparationCoordinator implements Coordinator {
    private static final int CHILD_BIND_CUTOFF = 9;
    private static final int EXTRA_VIEW_BUFFER_COUNT = 1;
    private static final long MAX_GROUP_INFLATION_DELAY = 500;
    private static final int STATE_ERROR = -1;
    private static final int STATE_INFLATED = 1;
    private static final int STATE_INFLATED_INVALID = 2;
    private static final int STATE_UNINFLATED = 0;
    private static final String TAG = "PreparationCoordinator";
    private final NotifUiAdjustmentProvider mAdjustmentProvider;
    private final AppIconProvider mAppIconProvider;
    private final BindEventManagerImpl mBindEventManager;
    private int mChildBindCutoff;
    private final ArraySet<NotificationEntry> mInflatingNotifs;
    private final ArrayMap<NotificationEntry, NotifUiAdjustment> mInflationAdjustments;
    private final NotifInflationErrorManager.NotifInflationErrorListener mInflationErrorListener;
    private final ArrayMap<NotificationEntry, Integer> mInflationStates;
    private final PreparationCoordinatorLogger mLogger;
    private final long mMaxGroupInflationDelay;
    private final NotifCollectionListener mNotifCollectionListener;
    private final NotifInflationErrorManager mNotifErrorManager;
    private final NotifInflater mNotifInflater;
    private final NotifFilter mNotifInflatingFilter;
    private final NotifFilter mNotifInflationErrorFilter;
    private InternalNotifUpdater mNotifUpdate;
    private final NotificationIconStyleProvider mNotificationIconStyleProvider;
    private final OngoingActivityController mOngoingActivityController;
    private final IStatusBarService mStatusBarService;
    private final NotifViewBarn mViewBarn;

    @Retention(RetentionPolicy.SOURCE)
    @interface InflationState {
    }

    public PreparationCoordinator(PreparationCoordinatorLogger preparationCoordinatorLogger, NotifInflater notifInflater, NotifInflationErrorManager notifInflationErrorManager, NotifViewBarn notifViewBarn, NotifUiAdjustmentProvider notifUiAdjustmentProvider, IStatusBarService iStatusBarService, BindEventManagerImpl bindEventManagerImpl, AppIconProvider appIconProvider, NotificationIconStyleProvider notificationIconStyleProvider, OngoingActivityController ongoingActivityController) {
        this(preparationCoordinatorLogger, notifInflater, notifInflationErrorManager, notifViewBarn, notifUiAdjustmentProvider, iStatusBarService, bindEventManagerImpl, appIconProvider, notificationIconStyleProvider, 9, MAX_GROUP_INFLATION_DELAY, ongoingActivityController);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void abortInflation(NotificationEntry notificationEntry, String str) {
        NotifInflaterImpl notifInflaterImpl = (NotifInflaterImpl) this.mNotifInflater;
        notifInflaterImpl.getClass();
        boolean zAbortTask = notificationEntry.abortTask();
        if (zAbortTask) {
            NotifInflaterLogger notifInflaterLogger = notifInflaterImpl.mLogger;
            notifInflaterLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            NotifInflaterLogger$$ExternalSyntheticLambda0 notifInflaterLogger$$ExternalSyntheticLambda0 = new NotifInflaterLogger$$ExternalSyntheticLambda0(4);
            LogBuffer logBuffer = notifInflaterLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("NotifInflater", logLevel, notifInflaterLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logBuffer.commit(logMessageObtain);
        }
        boolean zRemove = this.mInflatingNotifs.remove(notificationEntry);
        if (zAbortTask || zRemove) {
            this.mLogger.logInflationAborted(notificationEntry, str);
        }
    }

    private void freeNotifViews(NotificationEntry notificationEntry, String str) {
        this.mLogger.logFreeNotifViews(notificationEntry, str);
        this.mViewBarn.rowMap.remove(notificationEntry.mKey);
        NotifInflaterImpl notifInflaterImpl = (NotifInflaterImpl) this.mNotifInflater;
        NotifInflaterLogger notifInflaterLogger = notifInflaterImpl.mLogger;
        notifInflaterLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotifInflaterLogger$$ExternalSyntheticLambda0 notifInflaterLogger$$ExternalSyntheticLambda0 = new NotifInflaterLogger$$ExternalSyntheticLambda0(3);
        LogBuffer logBuffer = notifInflaterLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("NotifInflater", logLevel, notifInflaterLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(logMessageObtain);
        NotificationRowBinderImpl notificationRowBinderImpl = notifInflaterImpl.mNotificationRowBinder;
        if (notificationRowBinderImpl == null) {
            throw new RuntimeException("NotificationRowBinder must be attached before using NotifInflaterImpl.");
        }
        boolean zRowExists = notificationEntry.rowExists();
        NotificationRowBinderLogger notificationRowBinderLogger = notificationRowBinderImpl.mLogger;
        if (zRowExists) {
            notificationRowBinderLogger.getClass();
            NotificationRowBinderLogger$$ExternalSyntheticLambda0 notificationRowBinderLogger$$ExternalSyntheticLambda0 = new NotificationRowBinderLogger$$ExternalSyntheticLambda0(1);
            LogBuffer logBuffer2 = notificationRowBinderLogger.buffer;
            LogMessage logMessageObtain2 = logBuffer2.obtain("NotificationRowBinder", logLevel, notificationRowBinderLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain2).str1 = NotificationUtils.logKey(notificationEntry);
            logBuffer2.commit(logMessageObtain2);
            RowContentBindStage rowContentBindStage = notificationRowBinderImpl.mRowContentBindStage;
            RowContentBindParams rowContentBindParams = (RowContentBindParams) rowContentBindStage.getStageParams(notificationEntry);
            rowContentBindParams.markContentViewsFreeable(1);
            rowContentBindParams.markContentViewsFreeable(2);
            rowContentBindParams.markContentViewsFreeable(8);
            int i = AsyncHybridViewInflation.$r8$clinit;
            rowContentBindParams.markContentViewsFreeable(16);
            int i2 = LockscreenOtpRedaction.$r8$clinit;
            rowContentBindParams.markContentViewsFreeable(128);
            rowContentBindStage.requestRebind(notificationEntry, null);
        } else {
            notificationRowBinderLogger.getClass();
            NotificationRowBinderLogger$$ExternalSyntheticLambda0 notificationRowBinderLogger$$ExternalSyntheticLambda02 = new NotificationRowBinderLogger$$ExternalSyntheticLambda0(0);
            LogBuffer logBuffer3 = notificationRowBinderLogger.buffer;
            LogMessage logMessageObtain3 = logBuffer3.obtain("NotificationRowBinder", logLevel, notificationRowBinderLogger$$ExternalSyntheticLambda02, null);
            ((LogMessageImpl) logMessageObtain3).str1 = NotificationUtils.logKey(notificationEntry);
            logBuffer3.commit(logMessageObtain3);
        }
        this.mInflationStates.put(notificationEntry, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getInflationState(NotificationEntry notificationEntry) {
        Integer num = this.mInflationStates.get(notificationEntry);
        Objects.requireNonNull(num, "Asking state of a notification preparation coordinator doesn't know about");
        return num.intValue();
    }

    private static Set<String> getPackages(Collection<PipelineEntry> collection) {
        HashSet hashSet = new HashSet();
        for (PipelineEntry pipelineEntry : collection) {
            NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
            if (representativeEntry == null) {
                Log.wtf(TAG, "notification entry " + pipelineEntry.getKey() + " has no representative entry");
            } else {
                hashSet.add(representativeEntry.mSbn.getPackageName());
            }
        }
        return hashSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void inflateAllRequiredViews(List<PipelineEntry> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            PipelineEntry pipelineEntry = list.get(i);
            int i2 = NotificationBundleUi.$r8$clinit;
            if (pipelineEntry instanceof GroupEntry) {
                inflateRequiredGroupViews((GroupEntry) pipelineEntry);
            } else {
                inflateRequiredNotifViews((NotificationEntry) pipelineEntry);
            }
        }
    }

    private void inflateEntry(NotificationEntry notificationEntry, NotifUiAdjustment notifUiAdjustment, String str) {
        Trace.beginSection("PrepCoord.inflateEntry");
        abortInflation(notificationEntry, str);
        this.mInflationAdjustments.put(notificationEntry, notifUiAdjustment);
        this.mInflatingNotifs.add(notificationEntry);
        NotifInflater.Params inflaterParams = getInflaterParams(notifUiAdjustment, str);
        NotifInflater notifInflater = this.mNotifInflater;
        PreparationCoordinator$$ExternalSyntheticLambda0 preparationCoordinator$$ExternalSyntheticLambda0 = new PreparationCoordinator$$ExternalSyntheticLambda0(this);
        NotifInflaterImpl notifInflaterImpl = (NotifInflaterImpl) notifInflater;
        NotifInflaterLogger notifInflaterLogger = notifInflaterImpl.mLogger;
        notifInflaterLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotifInflaterLogger$$ExternalSyntheticLambda0 notifInflaterLogger$$ExternalSyntheticLambda0 = new NotifInflaterLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = notifInflaterLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("NotifInflater", logLevel, notifInflaterLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.str2 = inflaterParams.reason;
        logBuffer.commit(logMessageObtain);
        notifInflaterImpl.inflateViewsImpl(notificationEntry, inflaterParams, preparationCoordinator$$ExternalSyntheticLambda0);
        LogMessage logMessageObtain2 = logBuffer.obtain("NotifInflater", logLevel, new NotifInflaterLogger$$ExternalSyntheticLambda0(6), null);
        ((LogMessageImpl) logMessageObtain2).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(logMessageObtain2);
        Trace.endSection();
    }

    private void inflateRequiredGroupViews(GroupEntry groupEntry) {
        NotificationEntry notificationEntry = groupEntry.mSummary;
        if (notificationEntry != null) {
            int i = AsyncGroupHeaderViewInflation.$r8$clinit;
        }
        List list = groupEntry.mUnmodifiableChildren;
        inflateRequiredNotifViews(notificationEntry);
        if (notificationEntry == null || !notificationEntry.mSbn.getGroupKey().contains("INSIGNIFICANT")) {
            this.mChildBindCutoff = 9;
        } else {
            this.mChildBindCutoff = 60;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            NotificationEntry notificationEntry2 = (NotificationEntry) list.get(i2);
            int i3 = AsyncHybridViewInflation.$r8$clinit;
            notificationEntry2.mHasEverBeenGroupChild = true;
            int i4 = this.mChildBindCutoff;
            String str = notificationEntry2.mKey;
            if (i2 < i4) {
                inflateRequiredNotifViews(notificationEntry2);
                if (notificationEntry2.mIsBindCutOff.booleanValue()) {
                    notificationEntry2.mIsBindCutOff = Boolean.FALSE;
                    OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                    NotificationLockscreenUserManager notificationLockscreenUserManager = (NotificationLockscreenUserManager) Dependency.sDependency.getDependencyInner(NotificationLockscreenUserManager.class);
                    ongoingActivityDataHelper.getClass();
                    OngoingActivityDataHelper.updateOngoingList(notificationLockscreenUserManager);
                    OngoingActivityData ongoingActivityDataByKey = OngoingActivityDataHelper.getOngoingActivityDataByKey(str);
                    if (ongoingActivityDataByKey != null) {
                        ongoingActivityDataHelper.getClass();
                        OngoingActivityDataHelper.notifyUpdateItemNowbarObservers(OngoingActivityDataHelper.convertOngoingActivityData(ongoingActivityDataByKey));
                    } else {
                        Log.i(OngoingActivityDataHelper.TAG, "updateNowbarItemWhenSensitivityChanged() : ongoingActivityData is null");
                    }
                }
            } else {
                if (this.mInflatingNotifs.contains(notificationEntry2)) {
                    abortInflation(notificationEntry2, "Past last visible group child");
                }
                if (isInflated(notificationEntry2)) {
                    freeNotifViews(notificationEntry2, "Past last visible group child");
                    notificationEntry2.mIsBindCutOff = Boolean.TRUE;
                    OngoingActivityDataHelper ongoingActivityDataHelper2 = OngoingActivityDataHelper.INSTANCE;
                    NotificationLockscreenUserManager notificationLockscreenUserManager2 = (NotificationLockscreenUserManager) Dependency.sDependency.getDependencyInner(NotificationLockscreenUserManager.class);
                    ongoingActivityDataHelper2.getClass();
                    OngoingActivityDataHelper.updateOngoingList(notificationLockscreenUserManager2);
                    OngoingActivityData ongoingActivityDataByKey2 = OngoingActivityDataHelper.getOngoingActivityDataByKey(str);
                    if (ongoingActivityDataByKey2 != null) {
                        ongoingActivityDataHelper2.getClass();
                        OngoingActivityDataHelper.notifyUpdateItemNowbarObservers(OngoingActivityDataHelper.convertOngoingActivityData(ongoingActivityDataByKey2));
                    } else {
                        Log.i(OngoingActivityDataHelper.TAG, "updateNowbarItemWhenSensitivityChanged() : ongoingActivityData is null");
                    }
                }
            }
        }
    }

    private void inflateRequiredNotifViews(NotificationEntry notificationEntry) {
        NotifUiAdjustmentProvider notifUiAdjustmentProvider = this.mAdjustmentProvider;
        notifUiAdjustmentProvider.getClass();
        String str = notificationEntry.mKey;
        List<Notification.Action> smartActions = notificationEntry.mRanking.getSmartActions();
        List<CharSequence> smartReplies = notificationEntry.mRanking.getSmartReplies();
        boolean zIsConversation = notificationEntry.mRanking.isConversation();
        boolean z = false;
        boolean z2 = notifUiAdjustmentProvider.isSnoozeSettingsEnabled && !notificationEntry.isCanceled();
        ListAttachState listAttachState = notificationEntry.mAttachState;
        if (listAttachState.section == null) {
            throw new IllegalStateException("Entry must have a section to determine if minimized");
        }
        PipelineEntry pipelineEntry = listAttachState.parent;
        if (pipelineEntry == null) {
            throw new IllegalStateException("Entry must have a parent to determine if minimized");
        }
        boolean z3 = !notifUiAdjustmentProvider.highPriorityProvider.isHighPriority(notificationEntry, true) && notificationEntry.mRanking.isAmbient();
        boolean zEquals = pipelineEntry.equals(GroupEntry.ROOT_ENTRY);
        GroupEntry groupEntry = pipelineEntry instanceof GroupEntry ? (GroupEntry) pipelineEntry : null;
        boolean zAreEqual = Intrinsics.areEqual(groupEntry != null ? groupEntry.mSummary : null, notificationEntry);
        if (z3 && (zEquals || zAreEqual)) {
            z = true;
        }
        NotifUiAdjustment notifUiAdjustment = new NotifUiAdjustment(str, smartActions, smartReplies, zIsConversation, z2, z, (((SensitiveNotificationProtectionControllerImpl) notifUiAdjustmentProvider.sensitiveNotifProtectionController).shouldProtectNotification(notificationEntry) || (NotiRune.NOTI_STYLE_APP_LOCK && ((AppLockNotificationControllerImpl) notifUiAdjustmentProvider.appLockNotificationController).shouldHideNotiForAppLock(notificationEntry))) ? 1 : ((NotificationLockscreenUserManagerImpl) notifUiAdjustmentProvider.lockscreenUserManager).getRedactionType(notificationEntry), notificationEntry.mHasEverBeenGroupChild, false, notificationEntry.mRanking.getSummarization(), notificationEntry.isPromotedState());
        if (this.mInflatingNotifs.contains(notificationEntry)) {
            if (needToReinflate(notificationEntry, notifUiAdjustment, "Inflating notification has no adjustments")) {
                inflateEntry(notificationEntry, notifUiAdjustment, "adjustment changed while inflating");
                return;
            }
            return;
        }
        if (this.mInflationStates.get(notificationEntry) == null) {
            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("entry : "), notificationEntry.mKey, " inflationState is null during inflateRequiredNotifViews", TAG);
            return;
        }
        int iIntValue = this.mInflationStates.get(notificationEntry).intValue();
        if (iIntValue == -1) {
            if (needToReinflate(notificationEntry, notifUiAdjustment, null)) {
                inflateEntry(notificationEntry, notifUiAdjustment, "adjustment changed after error");
            }
        } else {
            if (iIntValue == 0) {
                inflateEntry(notificationEntry, notifUiAdjustment, "entryAdded");
                return;
            }
            if (iIntValue != 1) {
                if (iIntValue != 2) {
                    return;
                }
                rebind(notificationEntry, notifUiAdjustment, "entryUpdated");
            } else if (needToReinflate(notificationEntry, notifUiAdjustment, "Fully inflated notification has no adjustments")) {
                rebind(notificationEntry, notifUiAdjustment, "adjustment changed after inflated");
            }
        }
    }

    private boolean isBeyondGroupInitializationWindow(GroupEntry groupEntry, long j) {
        return j - groupEntry.mCreationTime > this.mMaxGroupInflationDelay;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInflated(NotificationEntry notificationEntry) {
        int inflationState = getInflationState(notificationEntry);
        return inflationState == 1 || inflationState == 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$attach$0() {
        this.mNotifInflatingFilter.invalidateList("adjustmentProviderChanged");
    }

    private boolean needToReinflate(NotificationEntry notificationEntry, NotifUiAdjustment notifUiAdjustment, String str) {
        NotifUiAdjustment notifUiAdjustment2 = this.mInflationAdjustments.get(notificationEntry);
        if (notifUiAdjustment2 == null) {
            if (str == null) {
                return true;
            }
            throw new IllegalStateException(str);
        }
        NotifUiAdjustment.Companion.getClass();
        if (notifUiAdjustment2 == notifUiAdjustment) {
            return false;
        }
        if (notifUiAdjustment2.isConversation != notifUiAdjustment.isConversation || notifUiAdjustment2.isSnoozeEnabled != notifUiAdjustment.isSnoozeEnabled || notifUiAdjustment2.isMinimized != notifUiAdjustment.isMinimized || notifUiAdjustment2.redactionType != notifUiAdjustment.redactionType) {
            return true;
        }
        List list = notifUiAdjustment2.smartActions;
        List list2 = notifUiAdjustment.smartActions;
        if (list != list2) {
            if (list.size() != list2.size()) {
                return true;
            }
            MergingSequence.AnonymousClass1 anonymousClass1 = SequencesKt___SequencesKt.zip(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list2)).new AnonymousClass1();
            while (anonymousClass1.hasNext()) {
                Pair pair = (Pair) anonymousClass1.next();
                if (!TextUtils.equals(((Notification.Action) pair.getFirst()).title, ((Notification.Action) pair.getSecond()).title)) {
                    return true;
                }
                Icon icon = ((Notification.Action) pair.getFirst()).getIcon();
                Icon icon2 = ((Notification.Action) pair.getSecond()).getIcon();
                if ((icon != icon2 && (icon == null || icon2 == null || !icon.sameAs(icon2))) || !Intrinsics.areEqual(((Notification.Action) pair.getFirst()).actionIntent, ((Notification.Action) pair.getSecond()).actionIntent)) {
                    return true;
                }
                RemoteInput[] remoteInputs = ((Notification.Action) pair.getFirst()).getRemoteInputs();
                RemoteInput[] remoteInputs2 = ((Notification.Action) pair.getSecond()).getRemoteInputs();
                if (remoteInputs != remoteInputs2) {
                    if (remoteInputs == null || remoteInputs2 == null || remoteInputs.length != remoteInputs2.length) {
                        return true;
                    }
                    MergingSequence.AnonymousClass1 anonymousClass12 = SequencesKt___SequencesKt.zip(ArraysKt___ArraysKt.asSequence(remoteInputs), ArraysKt___ArraysKt.asSequence(remoteInputs2)).new AnonymousClass1();
                    while (anonymousClass12.hasNext()) {
                        Pair pair2 = (Pair) anonymousClass12.next();
                        if (!TextUtils.equals(((RemoteInput) pair2.getFirst()).getLabel(), ((RemoteInput) pair2.getSecond()).getLabel())) {
                            return true;
                        }
                        CharSequence[] choices = ((RemoteInput) pair2.getFirst()).getChoices();
                        CharSequence[] choices2 = ((RemoteInput) pair2.getSecond()).getChoices();
                        if (choices != choices2) {
                            if (choices == null || choices2 == null || choices.length != choices2.length) {
                                return true;
                            }
                            MergingSequence.AnonymousClass1 anonymousClass13 = SequencesKt___SequencesKt.zip(ArraysKt___ArraysKt.asSequence(choices), ArraysKt___ArraysKt.asSequence(choices2)).new AnonymousClass1();
                            while (anonymousClass13.hasNext()) {
                                Pair pair3 = (Pair) anonymousClass13.next();
                                if (!TextUtils.equals((CharSequence) pair3.getFirst(), (CharSequence) pair3.getSecond())) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (Intrinsics.areEqual(notifUiAdjustment.smartReplies, notifUiAdjustment2.smartReplies)) {
            return ((notifUiAdjustment2.isChildInGroup || !notifUiAdjustment.isChildInGroup) && Intrinsics.areEqual(notifUiAdjustment2.summarization, notifUiAdjustment.summarization) && notifUiAdjustment.isPromoted == notifUiAdjustment2.isPromoted) ? false : true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInflationFinished(NotificationEntry notificationEntry, NotifViewController notifViewController) {
        this.mLogger.logNotifInflated(notificationEntry);
        this.mInflatingNotifs.remove(notificationEntry);
        this.mViewBarn.rowMap.put(notificationEntry.mKey, notifViewController);
        this.mInflationStates.put(notificationEntry, 1);
        Iterator<E> it = this.mBindEventManager.listeners.iterator();
        while (it.hasNext()) {
            ((BindEventManager.Listener) it.next()).onViewBound(notificationEntry);
        }
        this.mNotifInflatingFilter.invalidateList("onInflationFinished for " + NotificationUtils.logKey(notificationEntry));
        OngoingActivityDataHelper.INSTANCE.getClass();
        String str = notificationEntry.mKey;
        if (OngoingActivityDataHelper.getPendingOngoingActivityData(str) != null) {
            OngoingActivityController ongoingActivityController = this.mOngoingActivityController;
            ongoingActivityController.getClass();
            if (OngoingActivityDataHelper.onAsyncInflationFinished(notificationEntry, ongoingActivityController.userManager)) {
                HistoryDumpList historyDumpList = ongoingActivityController.historyDump;
                historyDumpList.getClass();
                historyDumpList.add(str + " is INFLATED ");
            }
        }
    }

    private void purgeCaches(Collection<PipelineEntry> collection) {
        Set<String> packages = getPackages(collection);
        this.mAppIconProvider.purgeCache(packages);
        this.mNotificationIconStyleProvider.purgeCache(packages);
    }

    private void rebind(NotificationEntry notificationEntry, NotifUiAdjustment notifUiAdjustment, String str) {
        this.mInflationAdjustments.put(notificationEntry, notifUiAdjustment);
        this.mInflatingNotifs.add(notificationEntry);
        NotifInflater.Params inflaterParams = getInflaterParams(notifUiAdjustment, str);
        NotifInflater notifInflater = this.mNotifInflater;
        PreparationCoordinator$$ExternalSyntheticLambda0 preparationCoordinator$$ExternalSyntheticLambda0 = new PreparationCoordinator$$ExternalSyntheticLambda0(this);
        NotifInflaterImpl notifInflaterImpl = (NotifInflaterImpl) notifInflater;
        NotifInflaterLogger notifInflaterLogger = notifInflaterImpl.mLogger;
        notifInflaterLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotifInflaterLogger$$ExternalSyntheticLambda0 notifInflaterLogger$$ExternalSyntheticLambda0 = new NotifInflaterLogger$$ExternalSyntheticLambda0(2);
        LogBuffer logBuffer = notifInflaterLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("NotifInflater", logLevel, notifInflaterLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.str2 = inflaterParams.reason;
        logBuffer.commit(logMessageObtain);
        notifInflaterImpl.inflateViewsImpl(notificationEntry, inflaterParams, preparationCoordinator$$ExternalSyntheticLambda0);
        LogMessage logMessageObtain2 = logBuffer.obtain("NotifInflater", logLevel, new NotifInflaterLogger$$ExternalSyntheticLambda0(5), null);
        ((LogMessageImpl) logMessageObtain2).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(logMessageObtain2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldWaitForGroupToInflate(GroupEntry groupEntry, long j) {
        if (groupEntry != GroupEntry.ROOT_ENTRY && !groupEntry.wasAttachedInPreviousPass()) {
            if (isBeyondGroupInitializationWindow(groupEntry, j)) {
                this.mLogger.logGroupInflationTookTooLong(groupEntry);
                return false;
            }
            NotificationEntry notificationEntry = groupEntry.mSummary;
            if (notificationEntry != null && !isInflated(notificationEntry)) {
                this.mLogger.logDelayingGroupRelease(groupEntry, groupEntry.mSummary);
                return true;
            }
            for (NotificationEntry notificationEntry2 : groupEntry.mUnmodifiableChildren) {
                if (this.mInflatingNotifs.contains(notificationEntry2) && !notificationEntry2.wasAttachedInPreviousPass()) {
                    this.mLogger.logDelayingGroupRelease(groupEntry, notificationEntry2);
                    return true;
                }
            }
            this.mLogger.logDoneWaitingForGroupInflation(groupEntry);
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        NotifInflationErrorManager notifInflationErrorManager = this.mNotifErrorManager;
        ((ArrayList) notifInflationErrorManager.mListeners).add(this.mInflationErrorListener);
        NotifUiAdjustmentProvider notifUiAdjustmentProvider = this.mAdjustmentProvider;
        Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$attach$0();
            }
        };
        ListenerSet listenerSet = notifUiAdjustmentProvider.dirtyListeners;
        if (listenerSet.isEmpty()) {
            ((NotificationLockscreenUserManagerImpl) notifUiAdjustmentProvider.lockscreenUserManager).mNotifStateChangedListeners.addIfAbsent(notifUiAdjustmentProvider.notifStateChangedListener);
            ((SensitiveNotificationProtectionControllerImpl) notifUiAdjustmentProvider.sensitiveNotifProtectionController).mListeners.addIfAbsent(notifUiAdjustmentProvider.onSensitiveStateChangedListener);
            notifUiAdjustmentProvider.updateSnoozeEnabled();
            notifUiAdjustmentProvider.secureSettings.registerContentObserverForUserSync(SettingsHelper.INDEX_SNOOZE_SETTING, notifUiAdjustmentProvider.settingsObserver, -1);
            if (NotiRune.NOTI_STYLE_APP_LOCK) {
                ((AppLockNotificationControllerImpl) notifUiAdjustmentProvider.appLockNotificationController).mListeners.addIfAbsent(notifUiAdjustmentProvider.onAppLockPackagesChangedListener);
            }
        }
        listenerSet.addIfAbsent(runnable);
        notifPipeline.addCollectionListener(this.mNotifCollectionListener);
        notifPipeline.addOnBeforeFinalizeFilterListener(new OnBeforeFinalizeFilterListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator$$ExternalSyntheticLambda2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener
            public final void onBeforeFinalizeFilter(List list) {
                this.f$0.inflateAllRequiredViews(list);
            }
        });
        notifPipeline.addFinalizeFilter(this.mNotifInflationErrorFilter);
        notifPipeline.addFinalizeFilter(this.mNotifInflatingFilter);
        NotifCollection notifCollection = notifPipeline.mNotifCollection;
        notifCollection.getClass();
        this.mNotifUpdate = new NotifCollection$$ExternalSyntheticLambda0(notifCollection, TAG);
    }

    public NotifInflater.Params getInflaterParams(NotifUiAdjustment notifUiAdjustment, String str) {
        return new NotifInflater.Params(notifUiAdjustment.isMinimized, str, notifUiAdjustment.isSnoozeEnabled, notifUiAdjustment.isChildInGroup, notifUiAdjustment.isGroupSummary, notifUiAdjustment.redactionType);
    }

    public PreparationCoordinator(PreparationCoordinatorLogger preparationCoordinatorLogger, NotifInflater notifInflater, NotifInflationErrorManager notifInflationErrorManager, NotifViewBarn notifViewBarn, NotifUiAdjustmentProvider notifUiAdjustmentProvider, IStatusBarService iStatusBarService, BindEventManagerImpl bindEventManagerImpl, AppIconProvider appIconProvider, NotificationIconStyleProvider notificationIconStyleProvider, int i, long j, OngoingActivityController ongoingActivityController) {
        this.mInflationStates = new ArrayMap<>();
        this.mInflationAdjustments = new ArrayMap<>();
        this.mInflatingNotifs = new ArraySet<>();
        this.mNotifCollectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator.1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryCleanUp(NotificationEntry notificationEntry) {
                PreparationCoordinator.this.mInflationStates.remove(notificationEntry);
                PreparationCoordinator.this.mViewBarn.rowMap.remove(notificationEntry.mKey);
                PreparationCoordinator.this.mInflationAdjustments.remove(notificationEntry);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryInit(NotificationEntry notificationEntry) {
                PreparationCoordinator.this.mInflationStates.put(notificationEntry, 0);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryRemoved(NotificationEntry notificationEntry, int i2) {
                PreparationCoordinator.this.abortInflation(notificationEntry, "entryRemoved reason=" + i2);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryUpdated(NotificationEntry notificationEntry, UpdateSource updateSource) {
                onEntryUpdated(notificationEntry);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryUpdated(NotificationEntry notificationEntry) {
                PreparationCoordinator.this.abortInflation(notificationEntry, "entryUpdated");
                int inflationState = PreparationCoordinator.this.getInflationState(notificationEntry);
                if (inflationState == 1) {
                    PreparationCoordinator.this.mInflationStates.put(notificationEntry, 2);
                } else if (inflationState == -1) {
                    PreparationCoordinator.this.mInflationStates.put(notificationEntry, 0);
                }
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public /* bridge */ /* synthetic */ void onEntryAdded(NotificationEntry notificationEntry) {
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            @Deprecated
            public /* bridge */ /* synthetic */ void onRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public /* bridge */ /* synthetic */ void onRankingApplied() {
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public /* bridge */ /* synthetic */ void onEntryBind(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification) {
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public /* bridge */ /* synthetic */ void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i2) {
            }
        };
        this.mNotifInflationErrorFilter = new NotifFilter("PreparationCoordinatorInflationError") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator.2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
            public boolean shouldFilterOut(NotificationEntry notificationEntry, long j2) {
                return PreparationCoordinator.this.getInflationState(notificationEntry) == -1;
            }
        };
        this.mNotifInflatingFilter = new NotifFilter("PreparationCoordinatorInflating") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator.3
            private final Map<GroupEntry, Boolean> mIsDelayedGroupCache = new ArrayMap();

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable
            public void onCleanup() {
                this.mIsDelayedGroupCache.clear();
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
            public boolean shouldFilterOut(NotificationEntry notificationEntry, long j2) {
                PipelineEntry pipelineEntry = notificationEntry.mAttachState.parent;
                Objects.requireNonNull(pipelineEntry);
                Boolean boolValueOf = this.mIsDelayedGroupCache.get(pipelineEntry);
                if (boolValueOf == null && (pipelineEntry instanceof GroupEntry)) {
                    GroupEntry groupEntry = (GroupEntry) pipelineEntry;
                    boolValueOf = Boolean.valueOf(PreparationCoordinator.this.shouldWaitForGroupToInflate(groupEntry, j2));
                    this.mIsDelayedGroupCache.put(groupEntry, boolValueOf);
                }
                if (PreparationCoordinator.this.isInflated(notificationEntry)) {
                    return boolValueOf != null && boolValueOf.booleanValue();
                }
                return true;
            }
        };
        this.mInflationErrorListener = new NotifInflationErrorManager.NotifInflationErrorListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator.4
            @Override // com.android.systemui.statusbar.notification.row.NotifInflationErrorManager.NotifInflationErrorListener
            public void onNotifInflationError(NotificationEntry notificationEntry, Exception exc) {
                PreparationCoordinator.this.mViewBarn.rowMap.remove(notificationEntry.mKey);
                PreparationCoordinator.this.mInflationStates.put(notificationEntry, -1);
                if (!(exc instanceof RuntimeException)) {
                    try {
                        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                        PreparationCoordinator.this.mStatusBarService.onNotificationError(statusBarNotification.getPackageName(), statusBarNotification.getTag(), statusBarNotification.getId(), statusBarNotification.getUid(), statusBarNotification.getInitialPid(), exc.getMessage(), statusBarNotification.getUser().getIdentifier());
                    } catch (RemoteException unused) {
                    }
                    PreparationCoordinator.this.mNotifInflationErrorFilter.invalidateList("onNotifInflationError for " + NotificationUtils.logKey(notificationEntry));
                    return;
                }
                Log.i(PreparationCoordinator.TAG, "Notification : " + notificationEntry.mKey + " not inflated because of inflation thread interrupted. " + exc);
                if (notificationEntry.isInsignificant()) {
                    return;
                }
                ((NotifCollection$$ExternalSyntheticLambda0) PreparationCoordinator.this.mNotifUpdate).onInternalNotificationUpdate("inflation error", notificationEntry.mSbn);
            }

            @Override // com.android.systemui.statusbar.notification.row.NotifInflationErrorManager.NotifInflationErrorListener
            public void onNotifInflationErrorCleared(NotificationEntry notificationEntry) {
                PreparationCoordinator.this.mNotifInflationErrorFilter.invalidateList("onNotifInflationErrorCleared for " + NotificationUtils.logKey(notificationEntry));
            }
        };
        this.mLogger = preparationCoordinatorLogger;
        this.mNotifInflater = notifInflater;
        this.mNotifErrorManager = notifInflationErrorManager;
        this.mViewBarn = notifViewBarn;
        this.mAdjustmentProvider = notifUiAdjustmentProvider;
        this.mStatusBarService = iStatusBarService;
        this.mChildBindCutoff = i;
        this.mMaxGroupInflationDelay = j;
        this.mBindEventManager = bindEventManagerImpl;
        this.mAppIconProvider = appIconProvider;
        this.mNotificationIconStyleProvider = notificationIconStyleProvider;
        this.mOngoingActivityController = ongoingActivityController;
    }
}
