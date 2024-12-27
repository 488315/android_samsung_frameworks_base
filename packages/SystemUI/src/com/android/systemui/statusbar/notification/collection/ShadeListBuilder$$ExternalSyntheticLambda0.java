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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class ShadeListBuilder$$ExternalSyntheticLambda0 implements Pluggable.PluggableListener, SemiStableSort.StableOrder {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ShadeListBuilder f$0;

    public /* synthetic */ ShadeListBuilder$$ExternalSyntheticLambda0(ShadeListBuilder shadeListBuilder, int i) {
        this.$r8$classId = i;
        this.f$0 = shadeListBuilder;
    }

    public Integer getRank(Object obj) {
        int i;
        ListEntry listEntry = (ListEntry) obj;
        if (this.f$0.getStabilityManager().isEntryReorderingAllowed(listEntry)) {
            return null;
        }
        ListAttachState listAttachState = listEntry.mAttachState;
        ListEntry.checkNull(listAttachState);
        NotifSection notifSection = listAttachState.section;
        int i2 = notifSection != null ? notifSection.index : -1;
        NotifSection notifSection2 = listEntry.getPreviousAttachState().section;
        if (i2 == (notifSection2 != null ? notifSection2.index : -1) && (i = listEntry.getPreviousAttachState().stableIndex) != -1) {
            return Integer.valueOf(i);
        }
        return null;
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable.PluggableListener
    public void onPluggableInvalidated(Object obj, String str) {
        switch (this.$r8$classId) {
            case 0:
                ShadeListBuilder shadeListBuilder = this.f$0;
                shadeListBuilder.getClass();
                Assert.isMainThread();
                int i = shadeListBuilder.mPipelineState.mState;
                shadeListBuilder.mLogger.logPluggableInvalidated("Pre-render Invalidator", (Invalidator) obj, i, str);
                shadeListBuilder.rebuildListIfBefore(9);
                break;
            case 1:
                ShadeListBuilder shadeListBuilder2 = this.f$0;
                shadeListBuilder2.getClass();
                Assert.isMainThread();
                int i2 = shadeListBuilder2.mPipelineState.mState;
                shadeListBuilder2.mLogger.logPluggableInvalidated("Finalize NotifFilter", (NotifFilter) obj, i2, str);
                shadeListBuilder2.rebuildListIfBefore(8);
                break;
            case 2:
                ShadeListBuilder shadeListBuilder3 = this.f$0;
                shadeListBuilder3.getClass();
                Assert.isMainThread();
                int i3 = shadeListBuilder3.mPipelineState.mState;
                shadeListBuilder3.mLogger.logPluggableInvalidated("NotifPromoter", (NotifPromoter) obj, i3, str);
                shadeListBuilder3.rebuildListIfBefore(5);
                break;
            case 3:
                ShadeListBuilder shadeListBuilder4 = this.f$0;
                shadeListBuilder4.getClass();
                Assert.isMainThread();
                int i4 = shadeListBuilder4.mPipelineState.mState;
                shadeListBuilder4.mLogger.logPluggableInvalidated("ReorderingNowAllowed", (NotifStabilityManager) obj, i4, str);
                shadeListBuilder4.rebuildListIfBefore(4);
                break;
            case 4:
            default:
                ShadeListBuilder shadeListBuilder5 = this.f$0;
                shadeListBuilder5.getClass();
                Assert.isMainThread();
                int i5 = shadeListBuilder5.mPipelineState.mState;
                shadeListBuilder5.mLogger.logPluggableInvalidated("Pre-group NotifFilter", (NotifFilter) obj, i5, str);
                shadeListBuilder5.rebuildListIfBefore(3);
                break;
            case 5:
                ShadeListBuilder shadeListBuilder6 = this.f$0;
                shadeListBuilder6.getClass();
                Assert.isMainThread();
                int i6 = shadeListBuilder6.mPipelineState.mState;
                shadeListBuilder6.mLogger.logPluggableInvalidated("NotifSection", (NotifSectioner) obj, i6, str);
                shadeListBuilder6.rebuildListIfBefore(7);
                break;
            case 6:
                ShadeListBuilder shadeListBuilder7 = this.f$0;
                shadeListBuilder7.getClass();
                Assert.isMainThread();
                int i7 = shadeListBuilder7.mPipelineState.mState;
                shadeListBuilder7.mLogger.logPluggableInvalidated("NotifComparator", (NotifComparator) obj, i7, str);
                shadeListBuilder7.rebuildListIfBefore(7);
                break;
        }
    }
}
