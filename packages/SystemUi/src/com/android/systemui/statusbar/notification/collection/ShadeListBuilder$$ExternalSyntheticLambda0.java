package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShadeListBuilder$$ExternalSyntheticLambda0 implements SemiStableSort.StableOrder, Pluggable.PluggableListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ShadeListBuilder f$0;

    public /* synthetic */ ShadeListBuilder$$ExternalSyntheticLambda0(ShadeListBuilder shadeListBuilder, int i) {
        this.$r8$classId = i;
        this.f$0 = shadeListBuilder;
    }

    public final Integer getRank(Object obj) {
        int i;
        ListEntry listEntry = (ListEntry) obj;
        if (!this.f$0.getStabilityManager().isEntryReorderingAllowed(listEntry)) {
            ListAttachState listAttachState = listEntry.mAttachState;
            ListEntry.checkNull(listAttachState);
            NotifSection notifSection = listAttachState.section;
            int i2 = notifSection != null ? notifSection.index : -1;
            ListAttachState listAttachState2 = listEntry.mPreviousAttachState;
            ListEntry.checkNull(listAttachState2);
            NotifSection notifSection2 = listAttachState2.section;
            if (i2 == (notifSection2 != null ? notifSection2.index : -1) && (i = listAttachState2.stableIndex) != -1) {
                return Integer.valueOf(i);
            }
        }
        return null;
    }
}
