package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationChannel;
import android.os.Handler;
import android.os.SystemClock;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
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
import com.android.systemui.statusbar.notification.collection.notifcollection.UpdateSource;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@CoordinatorScope
/* loaded from: classes3.dex */
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
        public RemoteInputActiveExtender() {
            super("RemoteInputCoordinator", "RemoteInputActive", RemoteInputCoordinatorKt.getDEBUG(), RemoteInputCoordinator.this.mMainHandler);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public boolean queryShouldExtendLifetime(NotificationEntry notificationEntry) {
            RemoteInputController remoteInputController = RemoteInputCoordinator.this.mNotificationRemoteInputManager.mRemoteInputController;
            return remoteInputController != null && remoteInputController.pruneWeakThenRemoveAndContains(notificationEntry, null, null);
        }
    }

    public final class RemoteInputHistoryExtender extends SelfTrackingLifetimeExtender {
        public RemoteInputHistoryExtender() {
            super("RemoteInputCoordinator", "RemoteInputHistory", RemoteInputCoordinatorKt.getDEBUG(), RemoteInputCoordinator.this.mMainHandler);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public void onStartedLifetimeExtension(NotificationEntry notificationEntry) {
            RemoteInputNotificationRebuilder remoteInputNotificationRebuilder = RemoteInputCoordinator.this.mRebuilder;
            remoteInputNotificationRebuilder.getClass();
            CharSequence charSequence = notificationEntry.remoteInputText;
            if (TextUtils.isEmpty(charSequence)) {
                charSequence = notificationEntry.remoteInputTextWhenReset;
            }
            StatusBarNotification statusBarNotificationRebuildWithRemoteInputInserted = remoteInputNotificationRebuilder.rebuildWithRemoteInputInserted(notificationEntry, charSequence, false, notificationEntry.remoteInputMimeType, notificationEntry.remoteInputUri);
            notificationEntry.lastRemoteInputSent = -2000L;
            notificationEntry.remoteInputTextWhenReset = null;
            InternalNotifUpdater internalNotifUpdater = RemoteInputCoordinator.this.mNotifUpdater;
            ((NotifCollection$$ExternalSyntheticLambda0) (internalNotifUpdater != null ? internalNotifUpdater : null)).onInternalNotificationUpdate("Extending lifetime of notification with remote input", statusBarNotificationRebuildWithRemoteInputInserted);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public boolean queryShouldExtendLifetime(NotificationEntry notificationEntry) {
            NotificationRemoteInputManager notificationRemoteInputManager = RemoteInputCoordinator.this.mNotificationRemoteInputManager;
            notificationRemoteInputManager.getClass();
            if (NotificationRemoteInputManager.FORCE_REMOTE_INPUT_HISTORY) {
                return notificationRemoteInputManager.isSpinning(notificationEntry.mKey) || SystemClock.elapsedRealtime() < notificationEntry.lastRemoteInputSent + 500;
            }
            return false;
        }
    }

    public final class SmartReplyHistoryExtender extends SelfTrackingLifetimeExtender {
        public SmartReplyHistoryExtender() {
            super("RemoteInputCoordinator", "SmartReplyHistory", RemoteInputCoordinatorKt.getDEBUG(), RemoteInputCoordinator.this.mMainHandler);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public void onCanceledLifetimeExtension(NotificationEntry notificationEntry) {
            RemoteInputCoordinator.this.mSmartReplyController.stopSending(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public void onStartedLifetimeExtension(NotificationEntry notificationEntry) {
            StatusBarNotification statusBarNotificationRebuildWithRemoteInputInserted = RemoteInputCoordinator.this.mRebuilder.rebuildWithRemoteInputInserted(notificationEntry, null, false, null, null);
            RemoteInputCoordinator.this.mSmartReplyController.stopSending(notificationEntry);
            InternalNotifUpdater internalNotifUpdater = RemoteInputCoordinator.this.mNotifUpdater;
            if (internalNotifUpdater == null) {
                internalNotifUpdater = null;
            }
            ((NotifCollection$$ExternalSyntheticLambda0) internalNotifUpdater).onInternalNotificationUpdate("Extending lifetime of notification with smart reply", statusBarNotificationRebuildWithRemoteInputInserted);
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
        RemoteInputHistoryExtender remoteInputHistoryExtender = new RemoteInputHistoryExtender();
        this.mRemoteInputHistoryExtender = remoteInputHistoryExtender;
        SmartReplyHistoryExtender smartReplyHistoryExtender = new SmartReplyHistoryExtender();
        this.mSmartReplyHistoryExtender = smartReplyHistoryExtender;
        RemoteInputActiveExtender remoteInputActiveExtender = new RemoteInputActiveExtender();
        this.mRemoteInputActiveExtender = remoteInputActiveExtender;
        this.mRemoteInputLifetimeExtenders = Arrays.asList(remoteInputHistoryExtender, smartReplyHistoryExtender, remoteInputActiveExtender);
        dumpManager.registerDumpable(this);
        this.mCollectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator$mCollectionListener$1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                NotificationRemoteInputManager notificationRemoteInputManager2;
                RemoteInputController remoteInputController;
                if (RemoteInputCoordinatorKt.getDEBUG()) {
                    KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("mCollectionListener.onEntryRemoved(entry=", notificationEntry.mKey, ")", "RemoteInputCoordinator");
                }
                this.this$0.mSmartReplyController.stopSending(notificationEntry);
                if ((i == 1 || i == 2) && (remoteInputController = (notificationRemoteInputManager2 = this.this$0.mNotificationRemoteInputManager).mRemoteInputController) != null && remoteInputController.pruneWeakThenRemoveAndContains(notificationEntry, null, null)) {
                    notificationEntry.mRemoteEditImeVisible = false;
                    notificationRemoteInputManager2.mRemoteInputController.removeRemoteInput(notificationEntry, null, "RemoteInputManager#cleanUpRemoteInputForUserRemoval");
                }
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public /* bridge */ /* synthetic */ void onEntryUpdated(NotificationEntry notificationEntry) {
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryUpdated(NotificationEntry notificationEntry, UpdateSource updateSource) {
                if (RemoteInputCoordinatorKt.getDEBUG()) {
                    Log.d("RemoteInputCoordinator", "mCollectionListener.onEntryUpdated(entry=" + notificationEntry.mKey + ", source=" + updateSource + ")");
                }
                if (updateSource != UpdateSource.SystemUi) {
                    this.this$0.mSmartReplyController.stopSending(notificationEntry);
                }
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
            public /* bridge */ /* synthetic */ void onRankingApplied() {
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public /* bridge */ /* synthetic */ void onEntryBind(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification) {
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public /* bridge */ /* synthetic */ void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onSmartReplySent(NotificationEntry notificationEntry, CharSequence charSequence) {
        if (RemoteInputCoordinatorKt.getDEBUG()) {
            KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("onSmartReplySent(entry=", notificationEntry.mKey, ")", "RemoteInputCoordinator");
        }
        StatusBarNotification statusBarNotificationRebuildWithRemoteInputInserted = this.mRebuilder.rebuildWithRemoteInputInserted(notificationEntry, charSequence, true, null, null);
        InternalNotifUpdater internalNotifUpdater = this.mNotifUpdater;
        if (internalNotifUpdater == null) {
            internalNotifUpdater = null;
        }
        ((NotifCollection$$ExternalSyntheticLambda0) internalNotifUpdater).onInternalNotificationUpdate("Adding smart reply spinner for sent", statusBarNotificationRebuildWithRemoteInputInserted);
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
        Iterator<T> it = this.mRemoteInputLifetimeExtenders.iterator();
        while (it.hasNext()) {
            notifPipeline.addNotificationLifetimeExtender((SelfTrackingLifetimeExtender) it.next());
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
        return false;
    }

    public void onPanelCollapsed() {
        this.mRemoteInputActiveExtender.endAllLifetimeExtensions();
    }

    public void onRemoteInputSent(NotificationEntry notificationEntry) {
        if (RemoteInputCoordinatorKt.getDEBUG()) {
            KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("onRemoteInputSent(entry=", notificationEntry.mKey, ")", "RemoteInputCoordinator");
        }
        this.mRemoteInputActiveExtender.endLifetimeExtensionAfterDelay(notificationEntry.mKey, 500L);
    }

    public void releaseNotificationIfKeptForRemoteInputHistory(String str) {
        if (RemoteInputCoordinatorKt.getDEBUG()) {
            KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("releaseNotificationIfKeptForRemoteInputHistory(entry=", str, ")", "RemoteInputCoordinator");
        }
        this.mRemoteInputActiveExtender.endLifetimeExtensionAfterDelay(str, 200L);
    }

    public void setRemoteInputController(RemoteInputController remoteInputController) {
        this.mSmartReplyController.mCallback = new SmartReplyController.Callback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator.setRemoteInputController.1
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
