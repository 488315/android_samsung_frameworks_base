package com.android.systemui.statusbar.notification.collection.render;

import android.os.Trace;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Unit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShadeViewManager$viewRenderer$1 {
    public final /* synthetic */ ShadeViewManager this$0;

    public ShadeViewManager$viewRenderer$1(ShadeViewManager shadeViewManager) {
        this.this$0 = shadeViewManager;
    }

    public final NotifViewController getGroupController(GroupEntry groupEntry) {
        NotifViewBarn notifViewBarn = this.this$0.viewBarn;
        NotificationEntry notificationEntry = groupEntry.mSummary;
        if (notificationEntry == null) {
            throw new IllegalStateException(("No Summary: " + groupEntry).toString());
        }
        LinkedHashMap linkedHashMap = (LinkedHashMap) notifViewBarn.rowMap;
        String str = notificationEntry.mKey;
        NotifViewController notifViewController = (NotifViewController) linkedHashMap.get(str);
        if (notifViewController != null) {
            return notifViewController;
        }
        throw new IllegalStateException(("No view has been registered for entry: " + str).toString());
    }

    public final NotifViewController getRowController(NotificationEntry notificationEntry) {
        LinkedHashMap linkedHashMap = (LinkedHashMap) this.this$0.viewBarn.rowMap;
        String str = notificationEntry.mKey;
        NotifViewController notifViewController = (NotifViewController) linkedHashMap.get(str);
        if (notifViewController != null) {
            return notifViewController;
        }
        throw new IllegalStateException(("No view has been registered for entry: " + str).toString());
    }

    public final void onRenderList(List list) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        ShadeViewManager shadeViewManager = this.this$0;
        if (!isTagEnabled) {
            shadeViewManager.viewDiffer.applySpec(shadeViewManager.specBuilder.buildNodeSpec(shadeViewManager.rootController, list));
            return;
        }
        Trace.traceBegin(4096L, "ShadeViewManager.onRenderList");
        try {
            shadeViewManager.viewDiffer.applySpec(shadeViewManager.specBuilder.buildNodeSpec(shadeViewManager.rootController, list));
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }
}
