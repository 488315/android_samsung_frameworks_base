package androidx.picker.loader;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "androidx.picker.loader.CachedLoader$load$1", m277f = "CachedLoader.kt", m278l = {29, 32}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class CachedLoader$load$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Object $key;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CachedLoader this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CachedLoader$load$1(CachedLoader cachedLoader, Object obj, Continuation<? super CachedLoader$load$1> continuation) {
        super(2, continuation);
        this.this$0 = cachedLoader;
        this.$key = obj;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CachedLoader$load$1 cachedLoader$load$1 = new CachedLoader$load$1(this.this$0, this.$key, continuation);
        cachedLoader$load$1.L$0 = obj;
        return cachedLoader$load$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CachedLoader$load$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0046  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        Unit unit;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            flowCollector = (FlowCollector) this.L$0;
            Object obj2 = this.this$0.cachedMap.get(this.$key);
            if (obj2 == null) {
                unit = null;
                if (unit == null) {
                    Object createValue = this.this$0.createValue(this.$key);
                    this.this$0.cachedMap.put(this.$key, createValue);
                    this.L$0 = null;
                    this.label = 2;
                    if (flowCollector.emit(createValue, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
                return Unit.INSTANCE;
            }
            this.L$0 = flowCollector;
            this.label = 1;
            if (flowCollector.emit(obj2, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        unit = Unit.INSTANCE;
        if (unit == null) {
        }
        return Unit.INSTANCE;
    }
}
