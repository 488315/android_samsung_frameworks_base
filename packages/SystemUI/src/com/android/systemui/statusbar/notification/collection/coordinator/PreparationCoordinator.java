package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.RemoteInput;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.Dependency;
import com.android.systemui.Flags;
import com.android.systemui.NotiRune;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.NotifInflaterImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManagerImpl;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustment;
import com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.notifcollection.InternalNotifUpdater;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.render.NotifViewBarn;
import com.android.systemui.statusbar.notification.collection.render.NotifViewController;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager;
import com.android.systemui.statusbar.notification.row.shared.AsyncGroupHeaderViewInflation;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;
import com.android.systemui.statusbar.policy.AppLockNotificationControllerImpl;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.SettingsHelper;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kotlin.Pair;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.MergingSequence$iterator$1;
import kotlin.sequences.SequencesKt___SequencesKt;

@CoordinatorScope
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
    private final BindEventManagerImpl mBindEventManager;
    private int mChildBindCutoff;
    private final ArraySet<NotificationEntry> mInflatingNotifs;
    private final ArrayMap<NotificationEntry, NotifUiAdjustment> mInflationAdjustments;
    private final NotifInflationErrorManager.NotifInflationErrorListener mInflationErrorListener;
    private final ArrayMap<NotificationEntry, Integer> mInflationStates;
    private boolean mIsChanged;
    private final PreparationCoordinatorLogger mLogger;
    private final long mMaxGroupInflationDelay;
    private final NotifCollectionListener mNotifCollectionListener;
    private final NotifInflationErrorManager mNotifErrorManager;
    private final NotifInflater mNotifInflater;
    private final NotifFilter mNotifInflatingFilter;
    private final NotifFilter mNotifInflationErrorFilter;
    private InternalNotifUpdater mNotifUpdate;
    private SettingsHelper.OnChangedCallback mSettingsHelperCallback;
    private final IStatusBarService mStatusBarService;
    private final NotifViewBarn mViewBarn;

    @Retention(RetentionPolicy.SOURCE)
    @interface InflationState {
    }

    public PreparationCoordinator(PreparationCoordinatorLogger preparationCoordinatorLogger, NotifInflater notifInflater, NotifInflationErrorManager notifInflationErrorManager, NotifViewBarn notifViewBarn, NotifUiAdjustmentProvider notifUiAdjustmentProvider, IStatusBarService iStatusBarService, BindEventManagerImpl bindEventManagerImpl) {
        this(preparationCoordinatorLogger, notifInflater, notifInflationErrorManager, notifViewBarn, notifUiAdjustmentProvider, iStatusBarService, bindEventManagerImpl, 9, MAX_GROUP_INFLATION_DELAY);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void abortInflation(NotificationEntry notificationEntry, String str) {
        boolean abortInflation = ((NotifInflaterImpl) this.mNotifInflater).abortInflation(notificationEntry);
        boolean remove = this.mInflatingNotifs.remove(notificationEntry);
        if (abortInflation || remove) {
            this.mLogger.logInflationAborted(notificationEntry, str);
        }
    }

    private void freeNotifViews(NotificationEntry notificationEntry, String str) {
        this.mLogger.logFreeNotifViews(notificationEntry, str);
        this.mViewBarn.rowMap.remove(notificationEntry.getKey());
        ((NotifInflaterImpl) this.mNotifInflater).releaseViews(notificationEntry);
        this.mInflationStates.put(notificationEntry, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getInflationState(NotificationEntry notificationEntry) {
        Integer num = this.mInflationStates.get(notificationEntry);
        Objects.requireNonNull(num, "Asking state of a notification preparation coordinator doesn't know about");
        return num.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void inflateAllRequiredViews(List<ListEntry> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ListEntry listEntry = list.get(i);
            if (listEntry instanceof GroupEntry) {
                inflateRequiredGroupViews((GroupEntry) listEntry);
            } else {
                inflateRequiredNotifViews((NotificationEntry) listEntry);
            }
        }
        if (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE && this.mIsChanged) {
            this.mIsChanged = false;
        }
    }

    private void inflateEntry(NotificationEntry notificationEntry, NotifUiAdjustment notifUiAdjustment, String str) {
        Trace.beginSection("PrepCoord.inflateEntry");
        abortInflation(notificationEntry, str);
        this.mInflationAdjustments.put(notificationEntry, notifUiAdjustment);
        this.mInflatingNotifs.add(notificationEntry);
        NotifInflater.Params inflaterParams = getInflaterParams(notifUiAdjustment, str);
        ((NotifInflaterImpl) this.mNotifInflater).inflateViews(notificationEntry, inflaterParams, new PreparationCoordinator$$ExternalSyntheticLambda0(this));
        Trace.endSection();
    }

    private void inflateRequiredGroupViews(GroupEntry groupEntry) {
        NotificationEntry notificationEntry = groupEntry.mSummary;
        if (notificationEntry != null) {
            int i = AsyncGroupHeaderViewInflation.$r8$clinit;
            Flags.notificationAsyncGroupHeaderInflation();
        }
        List list = groupEntry.mUnmodifiableChildren;
        inflateRequiredNotifViews(notificationEntry);
        if (NotiRune.NOTI_INSIGNIFICANT && notificationEntry.isInsignificant()) {
            this.mChildBindCutoff = 50;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            NotificationEntry notificationEntry2 = (NotificationEntry) list.get(i2);
            int i3 = AsyncHybridViewInflation.$r8$clinit;
            Flags.notificationAsyncHybridViewInflation();
            if (i2 < this.mChildBindCutoff) {
                inflateRequiredNotifViews(notificationEntry2);
            } else {
                if (this.mInflatingNotifs.contains(notificationEntry2)) {
                    abortInflation(notificationEntry2, "Past last visible group child");
                }
                if (isInflated(notificationEntry2)) {
                    freeNotifViews(notificationEntry2, "Past last visible group child");
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00aa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void inflateRequiredNotifViews(com.android.systemui.statusbar.notification.collection.NotificationEntry r15) {
        /*
            Method dump skipped, instructions count: 283
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator.inflateRequiredNotifViews(com.android.systemui.statusbar.notification.collection.NotificationEntry):void");
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
    public /* synthetic */ void lambda$attach$1() {
        this.mNotifInflatingFilter.invalidateList("adjustmentProviderChanged");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Uri uri) {
        this.mIsChanged = true;
    }

    private boolean needToReinflate(NotificationEntry notificationEntry, NotifUiAdjustment notifUiAdjustment, String str) {
        NotifUiAdjustment notifUiAdjustment2 = this.mInflationAdjustments.get(notificationEntry);
        if (notifUiAdjustment2 == null) {
            if (str == null) {
                return true;
            }
            throw new IllegalStateException(str);
        }
        if (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE && this.mIsChanged && notificationEntry.mSbn.getNotification().publicVersion != null) {
            return true;
        }
        NotifUiAdjustment.Companion.getClass();
        if (notifUiAdjustment2 != notifUiAdjustment) {
            if (notifUiAdjustment2.isConversation != notifUiAdjustment.isConversation || notifUiAdjustment2.isSnoozeEnabled != notifUiAdjustment.isSnoozeEnabled || notifUiAdjustment2.isMinimized != notifUiAdjustment.isMinimized || notifUiAdjustment2.needsRedaction != notifUiAdjustment.needsRedaction) {
                return true;
            }
            List list = notifUiAdjustment2.smartActions;
            List list2 = notifUiAdjustment.smartActions;
            if (list != list2) {
                if (list.size() != list2.size()) {
                    return true;
                }
                MergingSequence$iterator$1 mergingSequence$iterator$1 = new MergingSequence$iterator$1(SequencesKt___SequencesKt.zip(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list2)));
                while (mergingSequence$iterator$1.hasNext()) {
                    Pair pair = (Pair) mergingSequence$iterator$1.next();
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
                        MergingSequence$iterator$1 mergingSequence$iterator$12 = new MergingSequence$iterator$1(SequencesKt___SequencesKt.zip(ArraysKt___ArraysKt.asSequence(remoteInputs), ArraysKt___ArraysKt.asSequence(remoteInputs2)));
                        while (mergingSequence$iterator$12.hasNext()) {
                            Pair pair2 = (Pair) mergingSequence$iterator$12.next();
                            if (!TextUtils.equals(((RemoteInput) pair2.getFirst()).getLabel(), ((RemoteInput) pair2.getSecond()).getLabel())) {
                                return true;
                            }
                            CharSequence[] choices = ((RemoteInput) pair2.getFirst()).getChoices();
                            CharSequence[] choices2 = ((RemoteInput) pair2.getSecond()).getChoices();
                            if (choices != choices2) {
                                if (choices == null || choices2 == null || choices.length != choices2.length) {
                                    return true;
                                }
                                MergingSequence$iterator$1 mergingSequence$iterator$13 = new MergingSequence$iterator$1(SequencesKt___SequencesKt.zip(ArraysKt___ArraysKt.asSequence(choices), ArraysKt___ArraysKt.asSequence(choices2)));
                                while (mergingSequence$iterator$13.hasNext()) {
                                    Pair pair3 = (Pair) mergingSequence$iterator$13.next();
                                    if (!TextUtils.equals((CharSequence) pair3.getFirst(), (CharSequence) pair3.getSecond())) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!Intrinsics.areEqual(notifUiAdjustment.smartReplies, notifUiAdjustment2.smartReplies)) {
                return true;
            }
            Flags.notificationAsyncHybridViewInflation();
            Flags.notificationAsyncGroupHeaderInflation();
            if (notifUiAdjustment.isPromoted != notifUiAdjustment2.isPromoted) {
                return true;
            }
        }
        return false;
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
    }

    private void rebind(NotificationEntry notificationEntry, NotifUiAdjustment notifUiAdjustment, String str) {
        this.mInflationAdjustments.put(notificationEntry, notifUiAdjustment);
        this.mInflatingNotifs.add(notificationEntry);
        NotifInflater.Params inflaterParams = getInflaterParams(notifUiAdjustment, str);
        ((NotifInflaterImpl) this.mNotifInflater).rebindViews(notificationEntry, inflaterParams, new PreparationCoordinator$$ExternalSyntheticLambda0(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldWaitForGroupToInflate(GroupEntry groupEntry, long j) {
        if (groupEntry != GroupEntry.ROOT_ENTRY && groupEntry.getPreviousAttachState().parent == null) {
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
                if (this.mInflatingNotifs.contains(notificationEntry2) && notificationEntry2.getPreviousAttachState().parent == null) {
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
        ((ArrayList) this.mNotifErrorManager.mListeners).add(this.mInflationErrorListener);
        NotifUiAdjustmentProvider notifUiAdjustmentProvider = this.mAdjustmentProvider;
        Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                PreparationCoordinator.this.lambda$attach$1();
            }
        };
        ListenerSet listenerSet = notifUiAdjustmentProvider.dirtyListeners;
        if (listenerSet.isEmpty()) {
            ((NotificationLockscreenUserManagerImpl) notifUiAdjustmentProvider.lockscreenUserManager).mNotifStateChangedListeners.addIfAbsent(notifUiAdjustmentProvider.notifStateChangedListener);
            com.android.server.notification.Flags.screenshareNotificationHiding();
            ((SensitiveNotificationProtectionControllerImpl) notifUiAdjustmentProvider.sensitiveNotifProtectionController).mListeners.addIfAbsent(notifUiAdjustmentProvider.onSensitiveStateChangedListener);
            notifUiAdjustmentProvider.updateSnoozeEnabled();
            notifUiAdjustmentProvider.secureSettings.registerContentObserverForUserSync(SettingsHelper.INDEX_SNOOZE_SETTING, notifUiAdjustmentProvider.settingsObserver, -1);
            if (NotiRune.NOTI_STYLE_APP_LOCK) {
                ((AppLockNotificationControllerImpl) notifUiAdjustmentProvider.appLockNotificationController).mListeners.addIfAbsent(notifUiAdjustmentProvider.onAppLockPackagesChangedListener);
            }
        }
        listenerSet.addIfAbsent(runnable);
        notifPipeline.addCollectionListener(this.mNotifCollectionListener);
        notifPipeline.addOnBeforeFinalizeFilterListener(new OnBeforeFinalizeFilterListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator$$ExternalSyntheticLambda3
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener
            public final void onBeforeFinalizeFilter(List list) {
                PreparationCoordinator.this.inflateAllRequiredViews(list);
            }
        });
        notifPipeline.addFinalizeFilter(this.mNotifInflationErrorFilter);
        notifPipeline.addFinalizeFilter(this.mNotifInflatingFilter);
        if (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE) {
            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(this.mSettingsHelperCallback, Settings.Secure.getUriFor(SettingsHelper.INDEX_SECURE_ALLOW_PRIVATE_NOTIFICATIONS_WHEN_UNSECURE));
        }
        NotifCollection notifCollection = notifPipeline.mNotifCollection;
        notifCollection.getClass();
        this.mNotifUpdate = new NotifCollection$$ExternalSyntheticLambda0(notifCollection, TAG);
    }

    public NotifInflater.Params getInflaterParams(NotifUiAdjustment notifUiAdjustment, String str) {
        return new NotifInflater.Params(notifUiAdjustment.isMinimized, str, notifUiAdjustment.isSnoozeEnabled, notifUiAdjustment.isChildInGroup, notifUiAdjustment.isGroupSummary, notifUiAdjustment.needsRedaction);
    }

    public PreparationCoordinator(PreparationCoordinatorLogger preparationCoordinatorLogger, NotifInflater notifInflater, NotifInflationErrorManager notifInflationErrorManager, NotifViewBarn notifViewBarn, NotifUiAdjustmentProvider notifUiAdjustmentProvider, IStatusBarService iStatusBarService, BindEventManagerImpl bindEventManagerImpl, int i, long j) {
        this.mInflationStates = new ArrayMap<>();
        this.mInflationAdjustments = new ArrayMap<>();
        this.mInflatingNotifs = new ArraySet<>();
        this.mSettingsHelperCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator$$ExternalSyntheticLambda1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                PreparationCoordinator.this.lambda$new$0(uri);
            }
        };
        this.mNotifCollectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator.1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryCleanUp(NotificationEntry notificationEntry) {
                PreparationCoordinator.this.mInflationStates.remove(notificationEntry);
                PreparationCoordinator.this.mViewBarn.rowMap.remove(notificationEntry.getKey());
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
            public void onEntryUpdated(NotificationEntry notificationEntry, boolean z) {
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
            public /* bridge */ /* synthetic */ void onRankingApplied() {
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public /* bridge */ /* synthetic */ void onEntryAdded(NotificationEntry notificationEntry) {
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            @Deprecated
            public /* bridge */ /* synthetic */ void onRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
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
                GroupEntry parent = notificationEntry.getParent();
                Objects.requireNonNull(parent);
                Boolean bool = this.mIsDelayedGroupCache.get(parent);
                if (bool == null) {
                    bool = Boolean.valueOf(PreparationCoordinator.this.shouldWaitForGroupToInflate(parent, j2));
                    this.mIsDelayedGroupCache.put(parent, bool);
                }
                return !PreparationCoordinator.this.isInflated(notificationEntry) || bool.booleanValue();
            }
        };
        this.mInflationErrorListener = new NotifInflationErrorManager.NotifInflationErrorListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator.4
            @Override // com.android.systemui.statusbar.notification.row.NotifInflationErrorManager.NotifInflationErrorListener
            public void onNotifInflationError(NotificationEntry notificationEntry, Exception exc) {
                PreparationCoordinator.this.mViewBarn.rowMap.remove(notificationEntry.getKey());
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
                if (NotiRune.NOTI_INSIGNIFICANT && notificationEntry.isInsignificant()) {
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
    }
}
