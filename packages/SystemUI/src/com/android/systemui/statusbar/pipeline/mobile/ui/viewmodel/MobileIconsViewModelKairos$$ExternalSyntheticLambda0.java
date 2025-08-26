package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairos;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairosImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public final /* synthetic */ class MobileIconsViewModelKairos$$ExternalSyntheticLambda0 implements Function2 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MobileIconsViewModelKairos$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                List list = (List) obj2;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(Integer.valueOf(((SubscriptionModel) it.next()).subscriptionId));
                }
                return arrayList;
            case 1:
                MobileIconInteractorKairos mobileIconInteractorKairos = (MobileIconInteractorKairos) obj2;
                if (mobileIconInteractorKairos != null) {
                    return Integer.valueOf(((MobileIconInteractorKairosImpl) mobileIconInteractorKairos).connectionRepository.getSubId());
                }
                return null;
            case 2:
                return (Integer) CollectionsKt___CollectionsKt.lastOrNull((List) obj2);
            default:
                return Boolean.valueOf(((Icon.Resource) obj2) != null);
        }
    }
}
