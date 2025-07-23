package com.android.systemui.statusbar.pipeline.mobile.data.repository.demo;

import java.util.Map;
import java.util.Set;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda3 implements Function3 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DemoMobileConnectionsRepositoryKairos$$ExternalSyntheticLambda3(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        switch (this.$r8$classId) {
            case 0:
                Integer num = (Integer) obj2;
                num.intValue();
                int i = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                return (DemoMobileConnectionRepositoryKairos) ((Map) obj3).get(num);
            case 1:
                Set set = (Set) obj2;
                Integer num2 = (Integer) obj3;
                int i2 = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                return num2 != null ? SetsKt___SetsKt.plus(set, num2) : set;
            default:
                int i3 = DemoMobileConnectionsRepositoryKairos.$r8$clinit;
                return (Set) ((Function1) obj2).mo779invoke((Set) obj3);
        }
    }
}
