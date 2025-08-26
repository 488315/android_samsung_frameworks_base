package com.android.systemui.statusbar.notification.collection.coordinator;

import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

@CoordinatorScope
/* loaded from: classes3.dex */
public final class VoipCallPopUpCoordinator implements Coordinator {
    private static final long HEADS_UP_DELAY = 1000;
    private static final String TAG = "VoipCallPopUpCoordinator";
    private final DelayableExecutor executor;
    private final HeadsUpManager headsUpManager;
    private final SecQSExpansionStateInteractor interactor;
    private final ShadeController shadeController;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public VoipCallPopUpCoordinator(SecQSExpansionStateInteractor secQSExpansionStateInteractor, DelayableExecutor delayableExecutor, HeadsUpManager headsUpManager, ShadeController shadeController) {
        this.interactor = secQSExpansionStateInteractor;
        this.executor = delayableExecutor;
        this.headsUpManager = headsUpManager;
        this.shadeController = shadeController;
    }

    private final boolean isOngoingCall(NotificationEntry notificationEntry) {
        return StringsKt__StringsKt.contains(notificationEntry.mSbn.getNotification().extras.getString("android.template", ""), "CallStyle", false) && (notificationEntry.mSbn.getNotification().extras.getInt("android.callType", -1) == 2 || notificationEntry.mSbn.getNotification().extras.getInt("android.callType", -1) == 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onBeforeFinalizeFilterList(List<? extends PipelineEntry> list) {
        if (((Boolean) this.interactor.getRepository().expanded.$$delegate_0.getValue()).booleanValue()) {
            for (final PipelineEntry pipelineEntry : list) {
                if (pipelineEntry instanceof NotificationEntry) {
                    NotificationEntry notificationEntry = (NotificationEntry) pipelineEntry;
                    if (notificationEntry.mWillBeHUN && isOngoingCall(notificationEntry)) {
                        ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("Collapse panel because of call notification "), notificationEntry.mKey, TAG);
                        this.shadeController.animateCollapseShade(1.0f, 0, false, true);
                        this.executor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.VoipCallPopUpCoordinator$onBeforeFinalizeFilterList$1$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                HeadsUpManager headsUpManager = this.this$0.headsUpManager;
                                NotificationEntry notificationEntry2 = (NotificationEntry) pipelineEntry;
                                ArrayList arrayList = (ArrayList) ((HeadsUpManagerImpl) headsUpManager).mCallbacks;
                                int size = arrayList.size();
                                int i = 0;
                                while (i < size) {
                                    Object obj = arrayList.get(i);
                                    i++;
                                    ((HeadsUpCoordinator.AnonymousClass4) obj).turnToHeadsUp(notificationEntry2);
                                }
                            }
                        }, 1000L);
                        notificationEntry.mWillBeHUN = false;
                    }
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnBeforeFinalizeFilterListener(new OnBeforeFinalizeFilterListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.VoipCallPopUpCoordinator.attach.1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener
            public final void onBeforeFinalizeFilter(List<? extends PipelineEntry> list) {
                VoipCallPopUpCoordinator.this.onBeforeFinalizeFilterList(list);
            }
        });
    }
}
