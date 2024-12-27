package com.android.systemui.qs;

import com.android.systemui.qs.bar.BarItemImpl;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class SecQSPanelControllerBase$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecQSPanelControllerBase f$0;

    public /* synthetic */ SecQSPanelControllerBase$$ExternalSyntheticLambda2(SecQSPanelControllerBase secQSPanelControllerBase, int i) {
        this.$r8$classId = i;
        this.f$0 = secQSPanelControllerBase;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        SecQSPanelControllerBase secQSPanelControllerBase = this.f$0;
        BarItemImpl barItemImpl = (BarItemImpl) obj;
        switch (i) {
            case 0:
                SecQSPanelControllerBase.m2063$r8$lambda$DEp4lo7MDBmfDD2MwoTLB3AMIw(secQSPanelControllerBase, barItemImpl);
                break;
            default:
                SecQSPanelControllerBase.$r8$lambda$1i4lGWbt4JPhc9SgoAITwPQZoew(secQSPanelControllerBase, barItemImpl);
                break;
        }
    }
}
