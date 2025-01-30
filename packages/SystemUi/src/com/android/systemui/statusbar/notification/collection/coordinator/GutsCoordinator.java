package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.ArraySet;
import com.android.keyguard.FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda8;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.statusbar.notification.collection.render.NotifGutsViewManager;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import java.io.PrintWriter;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class GutsCoordinator implements Coordinator, Dumpable {
    public final GutsCoordinatorLogger logger;
    public final GutsCoordinator$mGutsListener$1 mGutsListener;
    public final GutsCoordinator$mLifetimeExtender$1 mLifetimeExtender;
    public final NotifGutsViewManager notifGutsViewManager;
    public NotifCollection$$ExternalSyntheticLambda8 onEndLifetimeExtensionCallback;
    public final ArraySet notifsWithOpenGuts = new ArraySet();
    public final ArraySet notifsExtendingLifetime = new ArraySet();

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.notification.collection.coordinator.GutsCoordinator$mLifetimeExtender$1] */
    public GutsCoordinator(NotifGutsViewManager notifGutsViewManager, GutsCoordinatorLogger gutsCoordinatorLogger, DumpManager dumpManager) {
        this.notifGutsViewManager = notifGutsViewManager;
        this.logger = gutsCoordinatorLogger;
        DumpManager.registerDumpable$default(dumpManager, "GutsCoordinator", this);
        this.mLifetimeExtender = new NotifLifetimeExtender() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GutsCoordinator$mLifetimeExtender$1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
            public final void cancelLifetimeExtension(NotificationEntry notificationEntry) {
                GutsCoordinator.this.notifsExtendingLifetime.remove(notificationEntry.mKey);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
            public final String getName() {
                return "GutsCoordinator";
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
            public final boolean maybeExtendLifetime(NotificationEntry notificationEntry, int i) {
                GutsCoordinator gutsCoordinator = GutsCoordinator.this;
                boolean contains = gutsCoordinator.notifsWithOpenGuts.contains(notificationEntry.mKey);
                if (contains) {
                    gutsCoordinator.notifsExtendingLifetime.add(notificationEntry.mKey);
                }
                return contains;
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
            public final void setCallback(NotifCollection$$ExternalSyntheticLambda8 notifCollection$$ExternalSyntheticLambda8) {
                GutsCoordinator.this.onEndLifetimeExtensionCallback = notifCollection$$ExternalSyntheticLambda8;
            }
        };
        this.mGutsListener = new GutsCoordinator$mGutsListener$1(this);
    }

    public static final void access$closeGutsAndEndLifetimeExtension(GutsCoordinator gutsCoordinator, NotificationEntry notificationEntry) {
        NotifCollection$$ExternalSyntheticLambda8 notifCollection$$ExternalSyntheticLambda8;
        gutsCoordinator.getClass();
        gutsCoordinator.notifsWithOpenGuts.remove(notificationEntry.mKey);
        if (!gutsCoordinator.notifsExtendingLifetime.remove(notificationEntry.mKey) || (notifCollection$$ExternalSyntheticLambda8 = gutsCoordinator.onEndLifetimeExtensionCallback) == null) {
            return;
        }
        notifCollection$$ExternalSyntheticLambda8.onEndLifetimeExtension(notificationEntry, gutsCoordinator.mLifetimeExtender);
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        ((NotificationGutsManager) this.notifGutsViewManager).mGutsListener = this.mGutsListener;
        notifPipeline.addNotificationLifetimeExtender(this.mLifetimeExtender);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ArraySet arraySet = this.notifsWithOpenGuts;
        printWriter.println("  notifsWithOpenGuts: " + arraySet.size());
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0.m60m("   * ", (String) it.next(), printWriter);
        }
        ArraySet arraySet2 = this.notifsExtendingLifetime;
        printWriter.println("  notifsExtendingLifetime: " + arraySet2.size());
        Iterator it2 = arraySet2.iterator();
        while (it2.hasNext()) {
            FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0.m60m("   * ", (String) it2.next(), printWriter);
        }
        printWriter.println("  onEndLifetimeExtensionCallback: " + this.onEndLifetimeExtensionCallback);
    }
}
