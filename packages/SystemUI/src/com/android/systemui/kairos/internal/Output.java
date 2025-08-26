package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.Schedulable;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class Output {
    public final CoroutineContext context;
    public final Function0 onDeath;
    public final Function2 onEmit;
    public volatile Object result;
    public final Schedulable.O schedulable;
    public volatile NodeConnection upstream;

    public final class NoResult {
        public static final NoResult INSTANCE = new NoResult();

        private NoResult() {
        }
    }

    public Output(CoroutineContext coroutineContext, Function0 function0, Function2 function2) {
        this.context = coroutineContext;
        this.onDeath = function0;
        this.onEmit = function2;
        this.schedulable = new Schedulable.O(this);
        this.result = NoResult.INSTANCE;
    }

    public /* synthetic */ Output(CoroutineContext coroutineContext, Function0 function0, Function2 function2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? EmptyCoroutineContext.INSTANCE : coroutineContext, (i & 2) != 0 ? new Output$$ExternalSyntheticLambda0() : function0, function2);
    }
}
