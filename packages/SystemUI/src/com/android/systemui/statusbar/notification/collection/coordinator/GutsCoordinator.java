package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.ArraySet;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.statusbar.notification.collection.render.NotifGutsViewListener;
import com.android.systemui.statusbar.notification.collection.render.NotifGutsViewManager;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
/* loaded from: classes2.dex */
public final class GutsCoordinator implements Coordinator, Dumpable {
    public static final int $stable = 8;
    private final GutsCoordinatorLogger logger;
    private final NotifGutsViewListener mGutsListener;
    private final NotifLifetimeExtender mLifetimeExtender;
    private final NotifGutsViewManager notifGutsViewManager;
    private NotifLifetimeExtender.OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback;
    private final ArraySet<String> notifsWithOpenGuts = new ArraySet<>();
    private final ArraySet<String> notifsExtendingLifetime = new ArraySet<>();

    public GutsCoordinator(NotifGutsViewManager notifGutsViewManager, GutsCoordinatorLogger gutsCoordinatorLogger, DumpManager dumpManager) {
        this.notifGutsViewManager = notifGutsViewManager;
        this.logger = gutsCoordinatorLogger;
        dumpManager.registerDumpable(this);
        this.mLifetimeExtender = new NotifLifetimeExtender() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GutsCoordinator$mLifetimeExtender$1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
            public void cancelLifetimeExtension(NotificationEntry notificationEntry) {
                ArraySet arraySet;
                arraySet = GutsCoordinator.this.notifsExtendingLifetime;
                arraySet.remove(notificationEntry.mKey);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
            public String getName() {
                return "GutsCoordinator";
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
            public boolean maybeExtendLifetime(NotificationEntry notificationEntry, int i) {
                boolean isCurrentlyShowingGuts;
                ArraySet arraySet;
                isCurrentlyShowingGuts = GutsCoordinator.this.isCurrentlyShowingGuts(notificationEntry);
                if (isCurrentlyShowingGuts) {
                    arraySet = GutsCoordinator.this.notifsExtendingLifetime;
                    arraySet.add(notificationEntry.mKey);
                }
                return isCurrentlyShowingGuts;
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
            public void setCallback(NotifLifetimeExtender.OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback) {
                GutsCoordinator.this.onEndLifetimeExtensionCallback = onEndLifetimeExtensionCallback;
            }
        };
        this.mGutsListener = new NotifGutsViewListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GutsCoordinator$mGutsListener$1
            @Override // com.android.systemui.statusbar.notification.collection.render.NotifGutsViewListener
            public void onGutsClose(NotificationEntry notificationEntry) {
                GutsCoordinatorLogger gutsCoordinatorLogger2;
                gutsCoordinatorLogger2 = GutsCoordinator.this.logger;
                gutsCoordinatorLogger2.logGutsClosed(notificationEntry.mKey);
                GutsCoordinator.this.closeGutsAndEndLifetimeExtension(notificationEntry);
            }

            @Override // com.android.systemui.statusbar.notification.collection.render.NotifGutsViewListener
            public void onGutsOpen(NotificationEntry notificationEntry, NotificationGuts notificationGuts) {
                GutsCoordinatorLogger gutsCoordinatorLogger2;
                ArraySet arraySet;
                gutsCoordinatorLogger2 = GutsCoordinator.this.logger;
                String str = notificationEntry.mKey;
                gutsCoordinatorLogger2.logGutsOpened(str, notificationGuts);
                NotificationGuts.GutsContent gutsContent = notificationGuts.mGutsContent;
                if (gutsContent != null && gutsContent.isLeavebehind()) {
                    GutsCoordinator.this.closeGutsAndEndLifetimeExtension(notificationEntry);
                } else {
                    arraySet = GutsCoordinator.this.notifsWithOpenGuts;
                    arraySet.add(str);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void closeGutsAndEndLifetimeExtension(NotificationEntry notificationEntry) {
        NotifLifetimeExtender.OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback;
        this.notifsWithOpenGuts.remove(notificationEntry.mKey);
        if (!this.notifsExtendingLifetime.remove(notificationEntry.mKey) || (onEndLifetimeExtensionCallback = this.onEndLifetimeExtensionCallback) == null) {
            return;
        }
        ((NotifCollection$$ExternalSyntheticLambda4) onEndLifetimeExtensionCallback).onEndLifetimeExtension(notificationEntry, this.mLifetimeExtender);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isCurrentlyShowingGuts(ListEntry listEntry) {
        return this.notifsWithOpenGuts.contains(listEntry.getKey());
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        ((NotificationGutsManager) this.notifGutsViewManager).mGutsListener = this.mGutsListener;
        notifPipeline.addNotificationLifetimeExtender(this.mLifetimeExtender);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0031, code lost:
    
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0077, code lost:
    
        throw r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x006e, code lost:
    
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x007b, code lost:
    
        throw r3;
     */
    @Override // com.android.systemui.Dumpable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dump(java.io.PrintWriter r4, java.lang.String[] r5) {
        /*
            r3 = this;
            java.lang.String r5 = ": "
            android.util.IndentingPrintWriter r4 = com.android.systemui.util.DumpUtilsKt.asIndenting(r4)
            r4.increaseIndent()
            java.lang.String r0 = "notifsWithOpenGuts"
            android.util.ArraySet<java.lang.String> r1 = r3.notifsWithOpenGuts     // Catch: java.lang.Throwable -> L6e
            java.io.PrintWriter r0 = r4.append(r0)     // Catch: java.lang.Throwable -> L6e
            java.io.PrintWriter r0 = r0.append(r5)     // Catch: java.lang.Throwable -> L6e
            int r2 = r1.size()     // Catch: java.lang.Throwable -> L6e
            r0.println(r2)     // Catch: java.lang.Throwable -> L6e
            r4.increaseIndent()     // Catch: java.lang.Throwable -> L6e
            java.util.Iterator r0 = r1.iterator()     // Catch: java.lang.Throwable -> L31
        L23:
            boolean r1 = r0.hasNext()     // Catch: java.lang.Throwable -> L31
            if (r1 == 0) goto L33
            java.lang.Object r1 = r0.next()     // Catch: java.lang.Throwable -> L31
            r4.println(r1)     // Catch: java.lang.Throwable -> L31
            goto L23
        L31:
            r3 = move-exception
            goto L74
        L33:
            r4.decreaseIndent()     // Catch: java.lang.Throwable -> L6e
            java.lang.String r0 = "notifsExtendingLifetime"
            android.util.ArraySet<java.lang.String> r1 = r3.notifsExtendingLifetime     // Catch: java.lang.Throwable -> L6e
            java.io.PrintWriter r0 = r4.append(r0)     // Catch: java.lang.Throwable -> L6e
            java.io.PrintWriter r5 = r0.append(r5)     // Catch: java.lang.Throwable -> L6e
            int r0 = r1.size()     // Catch: java.lang.Throwable -> L6e
            r5.println(r0)     // Catch: java.lang.Throwable -> L6e
            r4.increaseIndent()     // Catch: java.lang.Throwable -> L6e
            java.util.Iterator r5 = r1.iterator()     // Catch: java.lang.Throwable -> L5e
        L50:
            boolean r0 = r5.hasNext()     // Catch: java.lang.Throwable -> L5e
            if (r0 == 0) goto L60
            java.lang.Object r0 = r5.next()     // Catch: java.lang.Throwable -> L5e
            r4.println(r0)     // Catch: java.lang.Throwable -> L5e
            goto L50
        L5e:
            r3 = move-exception
            goto L70
        L60:
            r4.decreaseIndent()     // Catch: java.lang.Throwable -> L6e
            java.lang.String r5 = "onEndLifetimeExtensionCallback"
            com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender$OnEndLifetimeExtensionCallback r3 = r3.onEndLifetimeExtensionCallback     // Catch: java.lang.Throwable -> L6e
            com.android.systemui.util.DumpUtilsKt.println(r4, r5, r3)     // Catch: java.lang.Throwable -> L6e
            r4.decreaseIndent()
            return
        L6e:
            r3 = move-exception
            goto L78
        L70:
            r4.decreaseIndent()     // Catch: java.lang.Throwable -> L6e
            throw r3     // Catch: java.lang.Throwable -> L6e
        L74:
            r4.decreaseIndent()     // Catch: java.lang.Throwable -> L6e
            throw r3     // Catch: java.lang.Throwable -> L6e
        L78:
            r4.decreaseIndent()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.GutsCoordinator.dump(java.io.PrintWriter, java.lang.String[]):void");
    }
}
