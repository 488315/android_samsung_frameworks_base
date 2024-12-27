package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import kotlin.jvm.functions.Function0;

public final class DreamCoordinator$filter$1 extends NotifFilter {
    private boolean isFiltering;
    final /* synthetic */ DreamCoordinator this$0;

    public DreamCoordinator$filter$1(DreamCoordinator dreamCoordinator) {
        super("LockscreenHostedDreamFilter");
        this.this$0 = dreamCoordinator;
    }

    public final boolean isFiltering() {
        return this.isFiltering;
    }

    public final void setFiltering(boolean z) {
        this.isFiltering = z;
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
    public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
        return this.isFiltering;
    }

    public final void update(Function0 function0) {
        boolean isFiltering = isFiltering();
        setFiltering(this.this$0.isLockscreenHostedDream && this.this$0.isOnKeyguard);
        if (isFiltering != isFiltering()) {
            invalidateList((String) function0.invoke());
        }
    }
}
