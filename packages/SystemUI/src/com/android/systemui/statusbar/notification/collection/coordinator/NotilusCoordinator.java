package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.Trace;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.Dependency;
import com.android.systemui.noticenter.NotiCenterPlugin;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Invalidator;
import com.android.systemui.statusbar.notification.collection.render.NotifStackController;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
public final class NotilusCoordinator extends Invalidator implements Coordinator {
    public static final int $stable = 0;
    private final String TAG;

    public NotilusCoordinator() {
        super("NotilusCoordinator");
        this.TAG = "NotilusCoordinator";
    }

    private final boolean calculateClearableNotifStats(List<? extends ListEntry> list) {
        Iterator<T> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                return false;
            }
            ListEntry listEntry = (ListEntry) it.next();
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            if (representativeEntry == null) {
                throw new IllegalStateException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Null notif entry for ", listEntry.getKey()).toString());
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
    public final void onAfterRenderList(List<? extends ListEntry> list, NotifStackController notifStackController) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("NotilusCoordinator.onAfterRenderList");
        }
        try {
            NotiCenterPlugin.INSTANCE.getClass();
            if (NotiCenterPlugin.isNotiCenterPluginConnected() && NotiCenterPlugin.noclearEnabled) {
                NotiCenterPlugin.clearableNotifications = calculateClearableNotifStats(list);
                ((NotificationShelfManager) Dependency.sDependency.getDependencyInner(NotificationShelfManager.class)).updateClearButton();
            }
            Unit unit = Unit.INSTANCE;
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (isEnabled) {
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
        notifPipeline.addOnAfterRenderListListener(new OnAfterRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotilusCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener
            public final void onAfterRenderList(List<? extends ListEntry> list, NotifStackController notifStackController) {
                NotilusCoordinator.this.onAfterRenderList(list, notifStackController);
            }
        });
    }

    public void onUpdateNotiList() {
        invalidateList("onUpdateNotiList");
    }
}
