package com.android.systemui.flags;

import com.android.systemui.flags.RefactorFlag;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class RefactorFlag$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        FeatureFlags featureFlags = (FeatureFlags) obj;
        switch (this.$r8$classId) {
            case 0:
                RefactorFlag.Companion companion = RefactorFlag.Companion;
                featureFlags.getClass();
                break;
            default:
                featureFlags.getClass();
                break;
        }
        return Boolean.FALSE;
    }
}
