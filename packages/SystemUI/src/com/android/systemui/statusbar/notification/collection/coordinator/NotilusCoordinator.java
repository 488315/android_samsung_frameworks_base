package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.Trace;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.Dependency;
import com.android.systemui.noticenter.NotiCenterPlugin;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Invalidator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;

@CoordinatorScope
/* loaded from: classes3.dex */
public final class NotilusCoordinator extends Invalidator implements Coordinator {
    public static final int $stable = 0;
    private final String TAG;

    public NotilusCoordinator() {
        super("NotilusCoordinator");
        this.TAG = "NotilusCoordinator";
    }

    private final boolean calculateClearableNotifStats(List<? extends PipelineEntry> list) {
        Iterator<T> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                return false;
            }
            PipelineEntry pipelineEntry = (PipelineEntry) it.next();
            NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
            if (representativeEntry == null) {
                throw new IllegalStateException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Null notif entry for ", pipelineEntry.getKey()).toString());
            }
            if (representativeEntry.isClearable()) {
                NotiCenterPlugin notiCenterPlugin = NotiCenterPlugin.INSTANCE;
                String packageName = representativeEntry.mSbn.getPackageName();
                notiCenterPlugin.getClass();
                HashSet hashSet = NotiCenterPlugin.noclearAppList;
                if (!(hashSet != null ? hashSet.contains(packageName) : false)) {
                    return true;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onAfterRenderList(List<? extends PipelineEntry> list) {
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("NotilusCoordinator.onAfterRenderList");
        }
        try {
            NotiCenterPlugin.INSTANCE.getClass();
            if (NotiCenterPlugin.isNotiCenterPluginConnected() && NotiCenterPlugin.noclearEnabled) {
                NotiCenterPlugin.clearableNotifications = calculateClearableNotifStats(list);
                ((NotificationShelfManager) Dependency.sDependency.getDependencyInner(NotificationShelfManager.class)).updateClearButton();
            }
            Unit unit = Unit.INSTANCE;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        NotiCenterPlugin.INSTANCE.getClass();
        NotiCenterPlugin.mListener = this;
        notifPipeline.addPreRenderInvalidator(this);
        notifPipeline.addOnAfterRenderListListener(new OnAfterRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotilusCoordinator.attach.1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener
            public final void onAfterRenderList(List<? extends PipelineEntry> list) {
                NotilusCoordinator.this.onAfterRenderList(list);
            }
        });
    }

    public void onUpdateNotiList() {
        invalidateList("onUpdateNotiList");
    }
}
