package kotlinx.coroutines.flow;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes4.dex */
public final class FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1 implements Flow {
    public final /* synthetic */ Object[] $elements$inlined;

    /* renamed from: kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1$1, reason: invalid class name */
    public final class AnonymousClass1 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1.this.collect(null, this);
        }
    }

    public FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1(Object[] objArr) {
        this.$elements$inlined = objArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x005e -> B:20:0x0060). Please report as a decompilation issue!!! */
    @Override // kotlinx.coroutines.flow.Flow
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        int i;
        FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1;
        int length;
        FlowCollector flowCollector2;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i2 = anonymousClass1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i2 - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = anonymousClass1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            i = 0;
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1 = this;
            length = this.$elements$inlined.length;
            flowCollector2 = flowCollector;
            if (i < length) {
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            length = anonymousClass1.I$1;
            int i4 = anonymousClass1.I$0;
            FlowCollector flowCollector3 = (FlowCollector) anonymousClass1.L$1;
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$12 = (FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
            flowCollector2 = flowCollector3;
            i = i4 + 1;
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1 = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$12;
            if (i < length) {
                Object obj2 = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1.$elements$inlined[i];
                anonymousClass1.L$0 = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1;
                anonymousClass1.L$1 = flowCollector2;
                anonymousClass1.I$0 = i;
                anonymousClass1.I$1 = length;
                anonymousClass1.label = 1;
                if (flowCollector2.emit(obj2, anonymousClass1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$12 = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1;
                i4 = i;
                i = i4 + 1;
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1 = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$12;
                if (i < length) {
                    return Unit.INSTANCE;
                }
            }
        }
    }
}
