package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda17 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda17(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        SubscriptionModel subscriptionModel = (SubscriptionModel) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = MobileIconsInteractorKairosImpl.$r8$clinit;
                return Boolean.valueOf(subscriptionModel.profileClass != 1);
            case 1:
                int i2 = MobileIconsInteractorKairosImpl.$r8$clinit;
                return Boolean.valueOf(!subscriptionModel.isExclusivelyNonTerrestrial);
            default:
                int i3 = MobileIconsInteractorKairosImpl.$r8$clinit;
                return Integer.valueOf(subscriptionModel.subscriptionId);
        }
    }
}
