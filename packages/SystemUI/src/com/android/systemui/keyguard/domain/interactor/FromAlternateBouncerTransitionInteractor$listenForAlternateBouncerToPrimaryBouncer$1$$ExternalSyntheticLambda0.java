package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.util.kotlin.Septuple;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToPrimaryBouncer$1$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Boolean bool = (Boolean) obj;
                bool.booleanValue();
                return bool;
            default:
                Septuple septuple = (Septuple) obj;
                return Boolean.valueOf((((Boolean) septuple.component1()).booleanValue() || ((Boolean) septuple.component2()).booleanValue()) ? false : true);
        }
    }
}
