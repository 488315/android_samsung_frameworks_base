package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.Handler;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
public final class SubscreenQuickReplyCoordinator implements Coordinator, Dumpable {
    public static final int $stable = 8;
    private final SubscreenNotificationController mController;
    private boolean mIsFolded;
    private final Handler mMainHandler;
    private InternalNotifUpdater mNotifUpdater;
    private final SubscreenQuickReplyExtender mQuickReplyExtender = new SubscreenQuickReplyExtender();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SubscreenQuickReplyExtender extends SelfTrackingLifetimeExtender {
        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public SubscreenQuickReplyExtender() {
            /*
                r3 = this;
                com.android.systemui.statusbar.notification.collection.coordinator.SubscreenQuickReplyCoordinator.this = r4
                boolean r0 = com.android.systemui.statusbar.notification.collection.coordinator.SubscreenQuickReplyCoordinatorKt.access$getDEBUG()
                android.os.Handler r4 = com.android.systemui.statusbar.notification.collection.coordinator.SubscreenQuickReplyCoordinator.access$getMMainHandler$p(r4)
                java.lang.String r1 = "SubscreenQuickReplyCoordinator"
                java.lang.String r2 = "SubscreenQuickReply"
                r3.<init>(r1, r2, r0, r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.SubscreenQuickReplyCoordinator.SubscreenQuickReplyExtender.<init>(com.android.systemui.statusbar.notification.collection.coordinator.SubscreenQuickReplyCoordinator):void");
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
        return new SemWindowManager.FoldStateListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SubscreenQuickReplyCoordinator$getFoldStateListener$1
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
        ((ArrayList) subscreenNotificationController.subscreenStateListenerList).add(new SubscreenQuickReplyCoordinator$registerSubscreenStateChangeListener$1(this));
    }

    public final void setMIsFolded(boolean z) {
        this.mIsFolded = z;
    }
}
