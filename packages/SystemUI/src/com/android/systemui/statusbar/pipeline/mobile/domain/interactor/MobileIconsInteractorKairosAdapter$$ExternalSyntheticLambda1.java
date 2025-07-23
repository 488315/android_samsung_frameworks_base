package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import java.util.Map;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class MobileIconsInteractorKairosAdapter$$ExternalSyntheticLambda1 implements Function2 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MobileIconsInteractorKairosAdapter$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                return CollectionsKt___CollectionsKt.toList(((Map) obj2).values());
            default:
                Map.Entry entry = (Map.Entry) obj2;
                ((Number) entry.getKey()).intValue();
                return new MobileIconsInteractorKairosAdapter$$ExternalSyntheticLambda4((MobileIconInteractorKairos) entry.getValue(), 0);
        }
    }
}
