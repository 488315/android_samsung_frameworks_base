package com.android.systemui.keyguard.ui.viewmodel;

import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class ShadeDependentFlows$transitionFlow$filteredFlowWhenShadeIsExpanded$2 extends AdaptedFunctionReference implements Function3 {
    public static final ShadeDependentFlows$transitionFlow$filteredFlowWhenShadeIsExpanded$2 INSTANCE = new ShadeDependentFlows$transitionFlow$filteredFlowWhenShadeIsExpanded$2();

    public ShadeDependentFlows$transitionFlow$filteredFlowWhenShadeIsExpanded$2() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        Boolean bool = (Boolean) obj2;
        bool.booleanValue();
        return new Pair(obj, bool);
    }
}
