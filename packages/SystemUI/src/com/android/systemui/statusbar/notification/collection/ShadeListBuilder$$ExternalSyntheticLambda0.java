package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Invalidator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable;
import com.android.systemui.util.Assert;

/* loaded from: classes3.dex */
public final /* synthetic */ class ShadeListBuilder$$ExternalSyntheticLambda0 implements Pluggable.PluggableListener, SemiStableSort.StableOrder {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ShadeListBuilder f$0;

    public /* synthetic */ ShadeListBuilder$$ExternalSyntheticLambda0(ShadeListBuilder shadeListBuilder, int i) {
        this.$r8$classId = i;
        this.f$0 = shadeListBuilder;
    }

    public Integer getRank(Object obj) {
        int i;
        PipelineEntry pipelineEntry = (PipelineEntry) obj;
        int i2 = ShadeListBuilder.MAX_CONSECUTIVE_REENTRANT_REBUILDS;
        if (this.f$0.getStabilityManager().isEntryReorderingAllowed(pipelineEntry)) {
            return null;
        }
        NotifSection notifSection = pipelineEntry.mAttachState.section;
        int i3 = notifSection != null ? notifSection.index : -1;
        ListAttachState listAttachState = pipelineEntry.mPreviousAttachState;
        NotifSection notifSection2 = listAttachState.section;
        if (i3 == (notifSection2 != null ? notifSection2.index : -1) && (i = listAttachState.stableIndex) != -1) {
            return Integer.valueOf(i);
        }
        return null;
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable.PluggableListener
    public void onPluggableInvalidated(Pluggable pluggable, String str) {
        ShadeListBuilder shadeListBuilder = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                int i = ShadeListBuilder.MAX_CONSECUTIVE_REENTRANT_REBUILDS;
                shadeListBuilder.getClass();
                Assert.isMainThread();
                int i2 = shadeListBuilder.mPipelineState.mState;
                shadeListBuilder.mLogger.logPluggableInvalidated("Pre-render Invalidator", (Invalidator) pluggable, i2, str);
                shadeListBuilder.rebuildListIfBefore(9);
                break;
            case 1:
                int i3 = ShadeListBuilder.MAX_CONSECUTIVE_REENTRANT_REBUILDS;
                shadeListBuilder.getClass();
                Assert.isMainThread();
                int i4 = shadeListBuilder.mPipelineState.mState;
                shadeListBuilder.mLogger.logPluggableInvalidated("Finalize NotifFilter", (NotifFilter) pluggable, i4, str);
                shadeListBuilder.rebuildListIfBefore(8);
                break;
            case 2:
                int i5 = ShadeListBuilder.MAX_CONSECUTIVE_REENTRANT_REBUILDS;
                shadeListBuilder.getClass();
                Assert.isMainThread();
                int i6 = shadeListBuilder.mPipelineState.mState;
                shadeListBuilder.mLogger.logPluggableInvalidated("NotifPromoter", (NotifPromoter) pluggable, i6, str);
                shadeListBuilder.rebuildListIfBefore(5);
                break;
            case 3:
                int i7 = ShadeListBuilder.MAX_CONSECUTIVE_REENTRANT_REBUILDS;
                shadeListBuilder.getClass();
                Assert.isMainThread();
                int i8 = shadeListBuilder.mPipelineState.mState;
                shadeListBuilder.mLogger.logPluggableInvalidated("ReorderingNowAllowed", (NotifStabilityManager) pluggable, i8, str);
                shadeListBuilder.rebuildListIfBefore(4);
                break;
            case 4:
            default:
                int i9 = ShadeListBuilder.MAX_CONSECUTIVE_REENTRANT_REBUILDS;
                shadeListBuilder.getClass();
                Assert.isMainThread();
                int i10 = shadeListBuilder.mPipelineState.mState;
                shadeListBuilder.mLogger.logPluggableInvalidated("Pre-group NotifFilter", (NotifFilter) pluggable, i10, str);
                shadeListBuilder.rebuildListIfBefore(3);
                break;
            case 5:
                int i11 = ShadeListBuilder.MAX_CONSECUTIVE_REENTRANT_REBUILDS;
                shadeListBuilder.getClass();
                Assert.isMainThread();
                int i12 = shadeListBuilder.mPipelineState.mState;
                shadeListBuilder.mLogger.logPluggableInvalidated("NotifSection", (NotifSectioner) pluggable, i12, str);
                shadeListBuilder.rebuildListIfBefore(7);
                break;
            case 6:
                int i13 = ShadeListBuilder.MAX_CONSECUTIVE_REENTRANT_REBUILDS;
                shadeListBuilder.getClass();
                Assert.isMainThread();
                int i14 = shadeListBuilder.mPipelineState.mState;
                shadeListBuilder.mLogger.logPluggableInvalidated("NotifComparator", (NotifComparator) pluggable, i14, str);
                shadeListBuilder.rebuildListIfBefore(7);
                break;
        }
    }
}
