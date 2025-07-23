package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.Init;
import com.android.systemui.kairos.internal.InitKt$constInit$1;
import com.android.systemui.kairos.internal.PullNodesKt;
import com.android.systemui.kairos.internal.StateImpl;
import com.android.systemui.kairos.internal.StateSource;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class StateKt {
    public static final StateInit flatMap(State state, Function2 function2) {
        return new StateInit(new Init("flatMap", new StateKt$$ExternalSyntheticLambda1(state, function2, 1)));
    }

    public static final EventsInit getChanges(State state) {
        return new EventsInit(new Init(null, new StateKt$$ExternalSyntheticLambda0(state, 0)));
    }

    public static final StateInit map(State state, Function2 function2) {
        return new StateInit(new Init("map", new StateKt$$ExternalSyntheticLambda1(state, function2, 0)));
    }

    public static final StateInit stateOf(Object obj) {
        String str = "stateOf(" + obj + ")";
        return new StateInit(new Init(str, new InitKt$constInit$1(new StateImpl(str, "stateOf", PullNodesKt.neverImpl, new StateSource(obj)))));
    }
}
