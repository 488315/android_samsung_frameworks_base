package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.Notification;
import android.os.Handler;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.notifcollection.InternalNotifUpdater;
import com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender;
import com.samsung.android.view.SemWindowManager;
import java.io.PrintWriter;
import java.util.ArrayList;

@CoordinatorScope
/* loaded from: classes3.dex */
public final class SubscreenQuickReplyCoordinator implements Coordinator, Dumpable {
    public static final int $stable = 8;
    private final SubscreenNotificationController mController;
    private boolean mIsFolded;
    private final Handler mMainHandler;
    private InternalNotifUpdater mNotifUpdater;
    private final SubscreenQuickReplyExtender mQuickReplyExtender = new SubscreenQuickReplyExtender();

    public final class SubscreenQuickReplyExtender extends SelfTrackingLifetimeExtender {
        public SubscreenQuickReplyExtender() {
            super("SubscreenQuickReplyCoordinator", "SubscreenQuickReply", SubscreenQuickReplyCoordinatorKt.getDEBUG(), SubscreenQuickReplyCoordinator.this.mMainHandler);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public boolean queryShouldExtendLifetime(NotificationEntry notificationEntry) {
            boolean z = notificationEntry.mIsGhost && SubscreenQuickReplyCoordinator.this.getMIsFolded();
            if (z) {
                ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("   should extend lifetime - "), notificationEntry.mKey, "SubscreenQuickReplyCoordinator");
            }
            return z;
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.SubscreenQuickReplyCoordinator$registerSubscreenStateChangeListener$1, reason: invalid class name and case insensitive filesystem */
    public final class C10791 {
        public C10791() {
        }

        public void onHideDetail(String str) {
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("   end extension - ", str, "SubscreenQuickReplyCoordinator");
            SubscreenQuickReplyExtender mQuickReplyExtender = SubscreenQuickReplyCoordinator.this.getMQuickReplyExtender();
            if (str == null) {
                str = "";
            }
            mQuickReplyExtender.endLifetimeExtension(str);
        }

        public void onReply(NotificationEntry notificationEntry) {
            StatusBarNotification statusBarNotification;
            Notification notification2;
            int iIntValue;
            Notification notification3;
            Notification notification4;
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("   subscreen quick reply - ", notificationEntry != null ? notificationEntry.mKey : null, "SubscreenQuickReplyCoordinator");
            if (notificationEntry != null && (statusBarNotification = notificationEntry.mSbn) != null && (notification2 = statusBarNotification.getNotification()) != null) {
                StatusBarNotification statusBarNotification2 = notificationEntry.mSbn;
                if (statusBarNotification2 == null || (notification4 = statusBarNotification2.getNotification()) == null) {
                    StatusBarNotification statusBarNotification3 = notificationEntry.mSbn;
                    Integer numValueOf = (statusBarNotification3 == null || (notification3 = statusBarNotification3.getNotification()) == null) ? null : Integer.valueOf(notification3.flags);
                    numValueOf.getClass();
                    iIntValue = numValueOf.intValue();
                } else {
                    iIntValue = notification4.flags | 8;
                }
                notification2.flags = iIntValue;
            }
            InternalNotifUpdater internalNotifUpdater = SubscreenQuickReplyCoordinator.this.mNotifUpdater;
            if (internalNotifUpdater == null) {
                internalNotifUpdater = null;
            }
            ((NotifCollection$$ExternalSyntheticLambda0) internalNotifUpdater).onInternalNotificationUpdate("Extending lifetime of notification with subscreen quick reply", notificationEntry != null ? notificationEntry.mSbn : null);
        }
    }

    public SubscreenQuickReplyCoordinator(DumpManager dumpManager, SubscreenNotificationController subscreenNotificationController, Handler handler) {
        this.mController = subscreenNotificationController;
        this.mMainHandler = handler;
        dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addNotificationLifetimeExtender(this.mQuickReplyExtender);
        NotifCollection notifCollection = notifPipeline.mNotifCollection;
        notifCollection.getClass();
        this.mNotifUpdater = new NotifCollection$$ExternalSyntheticLambda0(notifCollection, "SubscreenQuickReplyCoordinator");
        registerFoldStateListener();
        registerSubscreenStateChangeListener();
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        this.mQuickReplyExtender.dump(printWriter, strArr);
    }

    public final SemWindowManager.FoldStateListener getFoldStateListener() {
        return new SemWindowManager.FoldStateListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SubscreenQuickReplyCoordinator.getFoldStateListener.1
            public void onFoldStateChanged(boolean z) {
                SubscreenQuickReplyCoordinator.this.setMIsFolded(z);
                if (z) {
                    return;
                }
                SubscreenQuickReplyCoordinator.this.getMQuickReplyExtender().endAllLifetimeExtensions();
            }

            public void onTableModeChanged(boolean z) {
            }
        };
    }

    public final boolean getMIsFolded() {
        return this.mIsFolded;
    }

    public final SubscreenQuickReplyExtender getMQuickReplyExtender() {
        return this.mQuickReplyExtender;
    }

    public final void registerFoldStateListener() {
        SemWindowManager.getInstance().registerFoldStateListener(getFoldStateListener(), this.mMainHandler);
    }

    public final void registerSubscreenStateChangeListener() {
        SubscreenNotificationController subscreenNotificationController = this.mController;
        ((ArrayList) subscreenNotificationController.subscreenStateListenerList).add(new C10791());
    }

    public final void setMIsFolded(boolean z) {
        this.mIsFolded = z;
    }
}
