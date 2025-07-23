package com.android.systemui.unfold;

import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class UnfoldTransitionModule$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                FoldStateLoggingProvider foldStateLoggingProvider = (FoldStateLoggingProvider) obj;
                foldStateLoggingProvider.getClass();
                return new FoldStateLogger(foldStateLoggingProvider);
            default:
                return new ScopedUnfoldTransitionProgressProvider((NaturalRotationUnfoldProgressProvider) obj);
        }
    }
}
