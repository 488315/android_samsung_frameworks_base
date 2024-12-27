package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.Flags;
import android.app.NotificationChannel;
import android.os.Handler;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.RemoteInputNotificationRebuilder;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.notifcollection.InternalNotifUpdater;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;

@CoordinatorScope
public final class RemoteInputCoordinator implements Coordinator, Dumpable {
    public static final int $stable = 8;
    private final NotifCollectionListener mCollectionListener;
    private final Handler mMainHandler;
    private InternalNotifUpdater mNotifUpdater;
    private final NotificationRemoteInputManager mNotificationRemoteInputManager;
    private final RemoteInputNotificationRebuilder mRebuilder;
    private final RemoteInputActiveExtender mRemoteInputActiveExtender;
    private final RemoteInputHistoryExtender mRemoteInputHistoryExtender;
    private final List<SelfTrackingLifetimeExtender> mRemoteInputLifetimeExtenders;
    private final SmartReplyController mSmartReplyController;
    private final SmartReplyHistoryExtender mSmartReplyHistoryExtender;

    public final class RemoteInputActiveExtender extends SelfTrackingLifetimeExtender {
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public RemoteInputActiveExtender() {
            /*
                r3 = this;
                com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator.this = r4
                boolean r0 = com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinatorKt.access$getDEBUG()
                android.os.Handler r4 = com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator.access$getMMainHandler$p(r4)
                java.lang.String r1 = "RemoteInputCoordinator"
                java.lang.String r2 = "RemoteInputActive"
                r3.<init>(r1, r2, r0, r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator.RemoteInputActiveExtender.<init>(com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator):void");
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public boolean queryShouldExtendLifetime(NotificationEntry notificationEntry) {
            RemoteInputController remoteInputController = RemoteInputCoordinator.this.mNotificationRemoteInputManager.mRemoteInputController;
            return remoteInputController != null && remoteInputController.pruneWeakThenRemoveAndContains(notificationEntry, null, null);
        }
    }

    public final class RemoteInputHistoryExtender extends SelfTrackingLifetimeExtender {
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public RemoteInputHistoryExtender() {
            /*
                r3 = this;
                com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator.this = r4
                boolean r0 = com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinatorKt.access$getDEBUG()
                android.os.Handler r4 = com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator.access$getMMainHandler$p(r4)
                java.lang.String r1 = "RemoteInputCoordinator"
                java.lang.String r2 = "RemoteInputHistory"
                r3.<init>(r1, r2, r0, r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator.RemoteInputHistoryExtender.<init>(com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator):void");
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public void onStartedLifetimeExtension(NotificationEntry notificationEntry) {
            RemoteInputNotificationRebuilder remoteInputNotificationRebuilder = RemoteInputCoordinator.this.mRebuilder;
            remoteInputNotificationRebuilder.getClass();
            CharSequence charSequence = notificationEntry.remoteInputText;
            if (TextUtils.isEmpty(charSequence)) {
                charSequence = notificationEntry.remoteInputTextWhenReset;
            }
            StatusBarNotification rebuildWithRemoteInputInserted = remoteInputNotificationRebuilder.rebuildWithRemoteInputInserted(notificationEntry, charSequence, false, notificationEntry.remoteInputMimeType, notificationEntry.remoteInputUri);
            notificationEntry.lastRemoteInputSent = -2000L;
            notificationEntry.remoteInputTextWhenReset = null;
            InternalNotifUpdater internalNotifUpdater = RemoteInputCoordinator.this.mNotifUpdater;
            ((NotifCollection$$ExternalSyntheticLambda0) (internalNotifUpdater != null ? internalNotifUpdater : null)).onInternalNotificationUpdate("Extending lifetime of notification with remote input", rebuildWithRemoteInputInserted);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public boolean queryShouldExtendLifetime(NotificationEntry notificationEntry) {
            return RemoteInputCoordinator.this.mNotificationRemoteInputManager.shouldKeepForRemoteInputHistory(notificationEntry);
        }
    }

    public final class SmartReplyHistoryExtender extends SelfTrackingLifetimeExtender {
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public SmartReplyHistoryExtender() {
            /*
                r3 = this;
                com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator.this = r4
                boolean r0 = com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinatorKt.access$getDEBUG()
                android.os.Handler r4 = com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator.access$getMMainHandler$p(r4)
                java.lang.String r1 = "RemoteInputCoordinator"
                java.lang.String r2 = "SmartReplyHistory"
                r3.<init>(r1, r2, r0, r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator.SmartReplyHistoryExtender.<init>(com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator):void");
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public void onCanceledLifetimeExtension(NotificationEntry notificationEntry) {
            RemoteInputCoordinator.this.mSmartReplyController.stopSending(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public void onStartedLifetimeExtension(NotificationEntry notificationEntry) {
            StatusBarNotification rebuildWithRemoteInputInserted = RemoteInputCoordinator.this.mRebuilder.rebuildWithRemoteInputInserted(notificationEntry, null, false, null, null);
            RemoteInputCoordinator.this.mSmartReplyController.stopSending(notificationEntry);
            InternalNotifUpdater internalNotifUpdater = RemoteInputCoordinator.this.mNotifUpdater;
            if (internalNotifUpdater == null) {
                internalNotifUpdater = null;
            }
            ((NotifCollection$$ExternalSyntheticLambda0) internalNotifUpdater).onInternalNotificationUpdate("Extending lifetime of notification with smart reply", rebuildWithRemoteInputInserted);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public boolean queryShouldExtendLifetime(NotificationEntry notificationEntry) {
            NotificationRemoteInputManager notificationRemoteInputManager = RemoteInputCoordinator.this.mNotificationRemoteInputManager;
            notificationRemoteInputManager.getClass();
            if (!NotificationRemoteInputManager.FORCE_REMOTE_INPUT_HISTORY) {
                return false;
            }
            return ((ArraySet) notificationRemoteInputManager.mSmartReplyController.mSendingKeys).contains(notificationEntry.mKey);
        }
    }

    public RemoteInputCoordinator(DumpManager dumpManager, RemoteInputNotificationRebuilder remoteInputNotificationRebuilder, NotificationRemoteInputManager notificationRemoteInputManager, Handler handler, SmartReplyController smartReplyController) {
        this.mRebuilder = remoteInputNotificationRebuilder;
        this.mNotificationRemoteInputManager = notificationRemoteInputManager;
        this.mMainHandler = handler;
        this.mSmartReplyController = smartReplyController;
        RemoteInputHistoryExtender remoteInputHistoryExtender = new RemoteInputHistoryExtender(this);
        this.mRemoteInputHistoryExtender = remoteInputHistoryExtender;
        SmartReplyHistoryExtender smartReplyHistoryExtender = new SmartReplyHistoryExtender(this);
        this.mSmartReplyHistoryExtender = smartReplyHistoryExtender;
        RemoteInputActiveExtender remoteInputActiveExtender = new RemoteInputActiveExtender(this);
        this.mRemoteInputActiveExtender = remoteInputActiveExtender;
        this.mRemoteInputLifetimeExtenders = CollectionsKt__CollectionsKt.listOf(remoteInputHistoryExtender, smartReplyHistoryExtender, remoteInputActiveExtender);
        dumpManager.registerDumpable(this);
        this.mCollectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator$mCollectionListener$1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                boolean debug;
                NotificationRemoteInputManager notificationRemoteInputManager2;
                RemoteInputController remoteInputController;
                debug = RemoteInputCoordinatorKt.getDEBUG();
                if (debug) {
                    ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("mCollectionListener.onEntryRemoved(entry="), notificationEntry.mKey, ")", "RemoteInputCoordinator");
                }
                RemoteInputCoordinator.this.mSmartReplyController.stopSending(notificationEntry);
                if ((i == 1 || i == 2) && (remoteInputController = (notificationRemoteInputManager2 = RemoteInputCoordinator.this.mNotificationRemoteInputManager).mRemoteInputController) != null && remoteInputController.pruneWeakThenRemoveAndContains(notificationEntry, null, null)) {
                    notificationEntry.mRemoteEditImeVisible = false;
                    notificationRemoteInputManager2.mRemoteInputController.removeRemoteInput(notificationEntry, null, "RemoteInputManager#cleanUpRemoteInputForUserRemoval");
                }
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public /* bridge */ /* synthetic */ void onEntryUpdated(NotificationEntry notificationEntry) {
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryUpdated(NotificationEntry notificationEntry, boolean z) {
                boolean debug;
                debug = RemoteInputCoordinatorKt.getDEBUG();
                if (debug) {
                    Log.d("RemoteInputCoordinator", "mCollectionListener.onEntryUpdated(entry=" + notificationEntry.mKey + ", fromSystem=" + z + ")");
                }
                if (z) {
                    if (!Flags.lifetimeExtensionRefactor()) {
                        RemoteInputCoordinator.this.mSmartReplyController.stopSending(notificationEntry);
                        return;
                    }
                    if ((notificationEntry.mSbn.getNotification().flags & 65536) <= 0) {
                        notificationEntry.remoteInputs = null;
                        return;
                    }
                    if (RemoteInputCoordinator.this.mNotificationRemoteInputManager.shouldKeepForRemoteInputHistory(notificationEntry)) {
                        RemoteInputNotificationRebuilder remoteInputNotificationRebuilder2 = RemoteInputCoordinator.this.mRebuilder;
                        remoteInputNotificationRebuilder2.getClass();
                        CharSequence charSequence = notificationEntry.remoteInputText;
                        if (TextUtils.isEmpty(charSequence)) {
                            charSequence = notificationEntry.remoteInputTextWhenReset;
                        }
                        StatusBarNotification rebuildWithRemoteInputInserted = remoteInputNotificationRebuilder2.rebuildWithRemoteInputInserted(notificationEntry, charSequence, false, notificationEntry.remoteInputMimeType, notificationEntry.remoteInputUri);
                        notificationEntry.lastRemoteInputSent = -2000L;
                        notificationEntry.remoteInputTextWhenReset = null;
                        InternalNotifUpdater internalNotifUpdater = RemoteInputCoordinator.this.mNotifUpdater;
                        ((NotifCollection$$ExternalSyntheticLambda0) (internalNotifUpdater != null ? internalNotifUpdater : null)).onInternalNotificationUpdate("Extending lifetime of notification with remote input", rebuildWithRemoteInputInserted);
                        return;
                    }
                    NotificationRemoteInputManager notificationRemoteInputManager2 = RemoteInputCoordinator.this.mNotificationRemoteInputManager;
                    notificationRemoteInputManager2.getClass();
                    if (!(!NotificationRemoteInputManager.FORCE_REMOTE_INPUT_HISTORY ? false : ((ArraySet) notificationRemoteInputManager2.mSmartReplyController.mSendingKeys).contains(notificationEntry.mKey))) {
                        StatusBarNotification rebuildWithRemoteInputInserted2 = RemoteInputCoordinator.this.mRebuilder.rebuildWithRemoteInputInserted(notificationEntry, null, false, null, null);
                        InternalNotifUpdater internalNotifUpdater2 = RemoteInputCoordinator.this.mNotifUpdater;
                        ((NotifCollection$$ExternalSyntheticLambda0) (internalNotifUpdater2 != null ? internalNotifUpdater2 : null)).onInternalNotificationUpdate("Extending lifetime of notification that has already been lifetime extended.", rebuildWithRemoteInputInserted2);
                    } else {
                        StatusBarNotification rebuildWithRemoteInputInserted3 = RemoteInputCoordinator.this.mRebuilder.rebuildWithRemoteInputInserted(notificationEntry, null, false, null, null);
                        RemoteInputCoordinator.this.mSmartReplyController.stopSending(notificationEntry);
                        InternalNotifUpdater internalNotifUpdater3 = RemoteInputCoordinator.this.mNotifUpdater;
                        ((NotifCollection$$ExternalSyntheticLambda0) (internalNotifUpdater3 != null ? internalNotifUpdater3 : null)).onInternalNotificationUpdate("Extending lifetime of notification with smart reply", rebuildWithRemoteInputInserted3);
                    }
                }
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public /* bridge */ /* synthetic */ void onRankingApplied() {
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public /* bridge */ /* synthetic */ void onEntryAdded(NotificationEntry notificationEntry) {
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public /* bridge */ /* synthetic */ void onEntryCleanUp(NotificationEntry notificationEntry) {
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
        };
    }

    public final void onSmartReplySent(NotificationEntry notificationEntry, CharSequence charSequence) {
        boolean debug;
        debug = RemoteInputCoordinatorKt.getDEBUG();
        if (debug) {
            KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("onSmartReplySent(entry=", notificationEntry.mKey, ")", "RemoteInputCoordinator");
        }
        StatusBarNotification rebuildWithRemoteInputInserted = this.mRebuilder.rebuildWithRemoteInputInserted(notificationEntry, charSequence, true, null, null);
        InternalNotifUpdater internalNotifUpdater = this.mNotifUpdater;
        if (internalNotifUpdater == null) {
            internalNotifUpdater = null;
        }
        ((NotifCollection$$ExternalSyntheticLambda0) internalNotifUpdater).onInternalNotificationUpdate("Adding smart reply spinner for sent", rebuildWithRemoteInputInserted);
        this.mRemoteInputActiveExtender.endLifetimeExtensionAfterDelay(notificationEntry.mKey, 500L);
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        NotificationRemoteInputManager notificationRemoteInputManager = this.mNotificationRemoteInputManager;
        if (notificationRemoteInputManager.mRemoteInputListener != null) {
            throw new IllegalStateException("mRemoteInputListener is already set");
        }
        notificationRemoteInputManager.mRemoteInputListener = this;
        RemoteInputController remoteInputController = notificationRemoteInputManager.mRemoteInputController;
        if (remoteInputController != null) {
            setRemoteInputController(remoteInputController);
        }
        if (Flags.lifetimeExtensionRefactor()) {
            notifPipeline.addNotificationLifetimeExtender(this.mRemoteInputActiveExtender);
        } else {
            Iterator<T> it = this.mRemoteInputLifetimeExtenders.iterator();
            while (it.hasNext()) {
                notifPipeline.addNotificationLifetimeExtender((SelfTrackingLifetimeExtender) it.next());
            }
        }
        NotifCollection notifCollection = notifPipeline.mNotifCollection;
        notifCollection.getClass();
        this.mNotifUpdater = new NotifCollection$$ExternalSyntheticLambda0(notifCollection, "RemoteInputCoordinator");
        notifPipeline.addCollectionListener(this.mCollectionListener);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        Iterator<T> it = this.mRemoteInputLifetimeExtenders.iterator();
        while (it.hasNext()) {
            ((SelfTrackingLifetimeExtender) it.next()).dump(printWriter, strArr);
        }
    }

    public final List<NotifLifetimeExtender> getLifetimeExtenders() {
        return this.mRemoteInputLifetimeExtenders;
    }

    public final NotifCollectionListener getMCollectionListener() {
        return this.mCollectionListener;
    }

    public final RemoteInputActiveExtender getMRemoteInputActiveExtender() {
        return this.mRemoteInputActiveExtender;
    }

    public final RemoteInputHistoryExtender getMRemoteInputHistoryExtender() {
        return this.mRemoteInputHistoryExtender;
    }

    public final SmartReplyHistoryExtender getMSmartReplyHistoryExtender() {
        return this.mSmartReplyHistoryExtender;
    }

    public boolean isNotificationKeptForRemoteInputHistory(String str) {
        if (Flags.lifetimeExtensionRefactor()) {
            return false;
        }
        return this.mRemoteInputHistoryExtender.isExtending(str) || this.mSmartReplyHistoryExtender.isExtending(str);
    }

    public void onPanelCollapsed() {
        this.mRemoteInputActiveExtender.endAllLifetimeExtensions();
    }

    public void onRemoteInputSent(NotificationEntry notificationEntry) {
        boolean debug;
        debug = RemoteInputCoordinatorKt.getDEBUG();
        String str = notificationEntry.mKey;
        if (debug) {
            KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("onRemoteInputSent(entry=", str, ")", "RemoteInputCoordinator");
        }
        if (!Flags.lifetimeExtensionRefactor()) {
            this.mRemoteInputHistoryExtender.endLifetimeExtension(str);
            this.mSmartReplyHistoryExtender.endLifetimeExtension(str);
        }
        this.mRemoteInputActiveExtender.endLifetimeExtensionAfterDelay(str, 500L);
    }

    public void releaseNotificationIfKeptForRemoteInputHistory(NotificationEntry notificationEntry) {
        boolean debug;
        debug = RemoteInputCoordinatorKt.getDEBUG();
        String str = notificationEntry.mKey;
        if (debug) {
            KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("releaseNotificationIfKeptForRemoteInputHistory(entry=", str, ")", "RemoteInputCoordinator");
        }
        if (!Flags.lifetimeExtensionRefactor()) {
            this.mRemoteInputHistoryExtender.endLifetimeExtensionAfterDelay(str, 200L);
            this.mSmartReplyHistoryExtender.endLifetimeExtensionAfterDelay(str, 200L);
        }
        this.mRemoteInputActiveExtender.endLifetimeExtensionAfterDelay(str, 200L);
    }

    public void setRemoteInputController(RemoteInputController remoteInputController) {
        this.mSmartReplyController.mCallback = new SmartReplyController.Callback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator$setRemoteInputController$1
            @Override // com.android.systemui.statusbar.SmartReplyController.Callback
            public final void onSmartReplySent(NotificationEntry notificationEntry, CharSequence charSequence) {
                RemoteInputCoordinator.this.onSmartReplySent(notificationEntry, charSequence);
            }
        };
    }

    public static /* synthetic */ void getMRemoteInputActiveExtender$annotations() {
    }

    public static /* synthetic */ void getMRemoteInputHistoryExtender$annotations() {
    }

    public static /* synthetic */ void getMSmartReplyHistoryExtender$annotations() {
    }
}
