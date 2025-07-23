package com.android.systemui.qs.customize.viewcontroller;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSLayoutEditViewController$4$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        Integer num = (Integer) obj;
        switch (this.$r8$classId) {
            case 0:
                return Boolean.valueOf(num.intValue() > 0);
            case 1:
                return Integer.valueOf(num.intValue() - 1);
            case 2:
                return Boolean.valueOf(num.intValue() > 0);
            case 3:
                num.getClass();
                return 0;
            default:
                return Integer.valueOf(num.intValue() + 1);
        }
    }
}
