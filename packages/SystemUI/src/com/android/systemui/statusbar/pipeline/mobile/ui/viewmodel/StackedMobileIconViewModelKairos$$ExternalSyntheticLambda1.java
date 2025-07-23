package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.kairos.CombineKt$$ExternalSyntheticLambda3;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.kairos.internal.Init;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class StackedMobileIconViewModelKairos$$ExternalSyntheticLambda1 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StackedMobileIconViewModelKairos f$0;

    public /* synthetic */ StackedMobileIconViewModelKairos$$ExternalSyntheticLambda1(StackedMobileIconViewModelKairos stackedMobileIconViewModelKairos, int i) {
        this.$r8$classId = i;
        this.f$0 = stackedMobileIconViewModelKairos;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        SignalIconModel.Cellular cellular;
        List<Object> list = (List) obj2;
        switch (this.$r8$classId) {
            case 0:
                List list2 = list;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    arrayList.add(((MobileIconViewModelKairos) it.next()).icon);
                }
                return StateKt.map(new StateInit(new Init("combine", new CombineKt$$ExternalSyntheticLambda3((Iterable) arrayList))), new StackedMobileIconViewModelKairos$$ExternalSyntheticLambda1(this.f$0, 1));
            default:
                this.f$0.getClass();
                Object obj3 = null;
                Object obj4 = null;
                for (Object obj5 : list) {
                    if (obj5 instanceof SignalIconModel.Cellular) {
                        if (obj3 == null) {
                            obj3 = obj5;
                        } else {
                            if (obj4 != null) {
                                return null;
                            }
                            obj4 = obj5;
                        }
                    }
                }
                SignalIconModel.Cellular cellular2 = (SignalIconModel.Cellular) obj3;
                if (cellular2 == null || (cellular = (SignalIconModel.Cellular) obj4) == null) {
                    return null;
                }
                return new StackedMobileIconViewModel.DualSim(cellular2, cellular);
        }
    }
}
