package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import java.util.Map;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.builders.ListBuilder;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class StackedMobileIconViewModelKairos$$ExternalSyntheticLambda0 implements Function3 {
    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MobileIconViewModelKairos mobileIconViewModelKairos;
        Map map = (Map) obj2;
        final Integer num = (Integer) obj3;
        ListBuilder createListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
        if (num != null && (mobileIconViewModelKairos = (MobileIconViewModelKairos) map.get(num)) != null) {
            createListBuilder.add(mobileIconViewModelKairos);
        }
        CollectionsKt__MutableCollectionsKt.addAll(createListBuilder, SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(map.values()), new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelKairos$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj4) {
                int i = ((MobileIconViewModelKairos) obj4).subscriptionId;
                Integer num2 = num;
                return Boolean.valueOf(num2 == null || i != num2.intValue());
            }
        }));
        return createListBuilder.build();
    }
}
