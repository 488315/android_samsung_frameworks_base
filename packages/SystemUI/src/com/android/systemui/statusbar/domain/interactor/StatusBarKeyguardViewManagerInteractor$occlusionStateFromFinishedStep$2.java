package com.android.systemui.statusbar.domain.interactor;

import com.android.systemui.keyguard.shared.model.TransitionStep;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class StatusBarKeyguardViewManagerInteractor$occlusionStateFromFinishedStep$2 extends AdaptedFunctionReference implements Function3 {
    public static final StatusBarKeyguardViewManagerInteractor$occlusionStateFromFinishedStep$2 INSTANCE = new StatusBarKeyguardViewManagerInteractor$occlusionStateFromFinishedStep$2();

    public StatusBarKeyguardViewManagerInteractor$occlusionStateFromFinishedStep$2() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        Boolean bool = (Boolean) obj2;
        bool.booleanValue();
        return new Pair((TransitionStep) obj, bool);
    }
}
