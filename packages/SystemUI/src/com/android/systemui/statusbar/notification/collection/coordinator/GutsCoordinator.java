package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.collection.EntryAdapter;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.statusbar.notification.collection.render.NotifGutsViewListener;
import com.android.systemui.statusbar.notification.collection.render.NotifGutsViewManager;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;
import java.util.Iterator;

@CoordinatorScope
/* loaded from: classes3.dex */
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
                this.this$0.notifsExtendingLifetime.remove(notificationEntry.mKey);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
            public String getName() {
                return "GutsCoordinator";
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
            public boolean maybeExtendLifetime(NotificationEntry notificationEntry, int i) {
                boolean zIsCurrentlyShowingGuts = this.this$0.isCurrentlyShowingGuts(notificationEntry);
                if (zIsCurrentlyShowingGuts) {
                    this.this$0.notifsExtendingLifetime.add(notificationEntry.mKey);
                }
                return zIsCurrentlyShowingGuts;
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
            public void setCallback(NotifLifetimeExtender.OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback) {
                this.this$0.onEndLifetimeExtensionCallback = onEndLifetimeExtensionCallback;
            }
        };
        this.mGutsListener = new NotifGutsViewListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GutsCoordinator$mGutsListener$1
            @Override // com.android.systemui.statusbar.notification.collection.render.NotifGutsViewListener
            public void onGutsClose(NotificationEntry notificationEntry) {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i = NotificationBundleUi.$r8$clinit;
                this.this$0.logger.logGutsClosed(notificationEntry.mKey);
                this.this$0.closeGutsAndEndLifetimeExtension(notificationEntry);
            }

            @Override // com.android.systemui.statusbar.notification.collection.render.NotifGutsViewListener
            public void onGutsOpen(NotificationEntry notificationEntry, NotificationGuts notificationGuts) {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i = NotificationBundleUi.$r8$clinit;
                this.this$0.logger.logGutsOpened(notificationEntry.mKey, notificationGuts);
                if (notificationGuts.isLeavebehind()) {
                    this.this$0.closeGutsAndEndLifetimeExtension(notificationEntry);
                } else {
                    this.this$0.notifsWithOpenGuts.add(notificationEntry.mKey);
                }
            }

            public void onGutsClose(EntryAdapter entryAdapter) {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i = NotificationBundleUi.$r8$clinit;
                refactorFlagUtils.getClass();
                RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notification_bundle_ui to be enabled.");
                this.this$0.logger.logGutsClosed(entryAdapter.getKey());
                this.this$0.closeGutsAndEndLifetimeExtension(entryAdapter);
            }

            public void onGutsOpen(EntryAdapter entryAdapter, NotificationGuts notificationGuts) {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i = NotificationBundleUi.$r8$clinit;
                refactorFlagUtils.getClass();
                RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notification_bundle_ui to be enabled.");
                this.this$0.logger.logGutsOpened(entryAdapter.getKey(), notificationGuts);
                if (notificationGuts.isLeavebehind()) {
                    this.this$0.closeGutsAndEndLifetimeExtension(entryAdapter);
                } else {
                    this.this$0.notifsWithOpenGuts.add(entryAdapter.getKey());
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
        ((NotifCollection$$ExternalSyntheticLambda4) onEndLifetimeExtensionCallback).onEndLifetimeExtension(this.mLifetimeExtender, notificationEntry);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isCurrentlyShowingGuts(PipelineEntry pipelineEntry) {
        return this.notifsWithOpenGuts.contains(pipelineEntry.getKey());
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        ((NotificationGutsManager) this.notifGutsViewManager).mGutsListener = this.mGutsListener;
        notifPipeline.addNotificationLifetimeExtender(this.mLifetimeExtender);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x006e, code lost:
    
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0077, code lost:
    
        throw r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x007b, code lost:
    
        throw r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0031, code lost:
    
        r3 = move-exception;
     */
    @Override // com.android.systemui.Dumpable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        indentingPrintWriterAsIndenting.increaseIndent();
        try {
            ArraySet<String> arraySet = this.notifsWithOpenGuts;
            indentingPrintWriterAsIndenting.append("notifsWithOpenGuts").append((CharSequence) ": ").println(arraySet.size());
            indentingPrintWriterAsIndenting.increaseIndent();
            Iterator<T> it = arraySet.iterator();
            while (it.hasNext()) {
                indentingPrintWriterAsIndenting.println(it.next());
            }
            indentingPrintWriterAsIndenting.decreaseIndent();
            ArraySet<String> arraySet2 = this.notifsExtendingLifetime;
            indentingPrintWriterAsIndenting.append("notifsExtendingLifetime").append((CharSequence) ": ").println(arraySet2.size());
            indentingPrintWriterAsIndenting.increaseIndent();
            Iterator<T> it2 = arraySet2.iterator();
            while (it2.hasNext()) {
                indentingPrintWriterAsIndenting.println(it2.next());
            }
            indentingPrintWriterAsIndenting.decreaseIndent();
            DumpUtilsKt.println(indentingPrintWriterAsIndenting, "onEndLifetimeExtensionCallback", this.onEndLifetimeExtensionCallback);
        } finally {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void closeGutsAndEndLifetimeExtension(EntryAdapter entryAdapter) {
        this.notifsWithOpenGuts.remove(entryAdapter.getKey());
        if (this.notifsExtendingLifetime.remove(entryAdapter.getKey())) {
            entryAdapter.endLifetimeExtension(this.onEndLifetimeExtensionCallback, this.mLifetimeExtender);
        }
    }
}
