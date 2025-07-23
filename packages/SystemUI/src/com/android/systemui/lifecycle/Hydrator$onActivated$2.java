package com.android.systemui.lifecycle;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.lifecycle.Hydrator;
import java.util.ArrayList;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class Hydrator$onActivated$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ Hydrator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Hydrator$onActivated$2(Hydrator hydrator, Continuation continuation) {
        super(2, continuation);
        this.this$0 = hydrator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        Hydrator$onActivated$2 hydrator$onActivated$2 = new Hydrator$onActivated$2(this.this$0, continuation);
        hydrator$onActivated$2.L$0 = obj;
        return hydrator$onActivated$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((Hydrator$onActivated$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Hydrator hydrator = this.this$0;
            String str = hydrator.traceName;
            ArrayList arrayList = (ArrayList) hydrator.children;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj2 = arrayList.get(i2);
                i2++;
                Hydrator.NamedActivatable namedActivatable = (Hydrator.NamedActivatable) obj2;
                if (namedActivatable.traceName != null) {
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new Hydrator$onActivated$2$1$1$1(namedActivatable, null), 6);
                } else {
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new Hydrator$onActivated$2$1$1$2(namedActivatable, null), 7);
                }
            }
            this.label = 1;
            if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
