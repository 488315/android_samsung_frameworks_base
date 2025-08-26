package com.android.systemui.qs.customize.viewcontroller;

import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class QSLayoutEditViewController$4$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
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
