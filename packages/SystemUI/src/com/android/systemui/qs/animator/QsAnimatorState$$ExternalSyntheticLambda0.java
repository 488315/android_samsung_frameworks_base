package com.android.systemui.qs.animator;

import com.android.systemui.Dependency;
import com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class QsAnimatorState$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        QsAnimatorState qsAnimatorState = QsAnimatorState.INSTANCE;
        return (SecQSExpansionStateInteractor) Dependency.sDependency.getDependencyInner(SecQSExpansionStateInteractor.class);
    }
}
