package com.android.wm.shell.compatui.api;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class CompatUILifecyclePredicates {
    public final Function2 creationPredicate;
    public final Function3 removalPredicate;
    public final Function2 stateBuilder;

    public CompatUILifecyclePredicates(Function2 function2, Function3 function3, Function2 function22) {
        this.creationPredicate = function2;
        this.removalPredicate = function3;
        this.stateBuilder = function22;
    }

    public /* synthetic */ CompatUILifecyclePredicates(Function2 function2, Function3 function3, Function2 function22, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(function2, function3, (i & 4) != 0 ? new Function2() { // from class: com.android.wm.shell.compatui.api.CompatUILifecyclePredicates.1
            @Override // kotlin.jvm.functions.Function2
            public final /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return null;
            }
        } : function22);
    }
}
