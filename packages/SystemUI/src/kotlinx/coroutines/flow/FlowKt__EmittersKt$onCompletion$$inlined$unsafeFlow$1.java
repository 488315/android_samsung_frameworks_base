package kotlinx.coroutines.flow;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1 implements Flow {
    public final /* synthetic */ Function3 $action$inlined;
    public final /* synthetic */ Flow $this_onCompletion$inlined;

    /* renamed from: kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1, reason: invalid class name */
    public final class AnonymousClass1 extends ContinuationImpl {
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
            return FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1.this.collect(null, this);
        }
    }

    public FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1(Flow flow, Function3 function3) {
        this.$this_onCompletion$inlined = flow;
        this.$action$inlined = function3;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(13:0|1|(2:3|(9:5|6|7|(1:(1:(1:(5:12|13|14|15|16)(2:22|23))(2:24|25))(2:26|27))(2:38|39)|28|30|31|(3:33|15|16)|34))|46|6|7|(0)(0)|28|30|31|(0)|34|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0065, code lost:
    
        if (r10.collect(r9, r0) == r1) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0051, code lost:
    
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0052, code lost:
    
        r9 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x008d, code lost:
    
        r10 = new kotlinx.coroutines.flow.ThrowingCollector(r9);
        r9 = r9.$action$inlined;
        r0.L$0 = r9;
        r0.L$1 = null;
        r0.label = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x009e, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt__EmittersKt.access$invokeSafely$FlowKt__EmittersKt(r10, r9, r9, r0) == r1) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:?, code lost:
    
        throw r9;
     */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    @Override // kotlinx.coroutines.flow.Flow
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector r9, kotlin.coroutines.Continuation r10) {
        /*
            r8 = this;
            boolean r0 = r10 instanceof kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1.AnonymousClass1
            if (r0 == 0) goto L13
            r0 = r10
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1 r0 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1 r0 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1
            r0.<init>(r10)
        L18:
            java.lang.Object r10 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 3
            r4 = 2
            r5 = 1
            r6 = 0
            if (r2 == 0) goto L56
            if (r2 == r5) goto L44
            if (r2 == r4) goto L3c
            if (r2 != r3) goto L34
            java.lang.Object r8 = r0.L$0
            kotlinx.coroutines.flow.internal.SafeCollector r8 = (kotlinx.coroutines.flow.internal.SafeCollector) r8
            kotlin.ResultKt.throwOnFailure(r10)     // Catch: java.lang.Throwable -> L32
            goto L81
        L32:
            r9 = move-exception
            goto L89
        L34:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L3c:
            java.lang.Object r8 = r0.L$0
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            kotlin.ResultKt.throwOnFailure(r10)
            goto La1
        L44:
            java.lang.Object r8 = r0.L$1
            r9 = r8
            kotlinx.coroutines.flow.FlowCollector r9 = (kotlinx.coroutines.flow.FlowCollector) r9
            java.lang.Object r8 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1 r8 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1) r8
            kotlin.ResultKt.throwOnFailure(r10)     // Catch: java.lang.Throwable -> L51
            goto L68
        L51:
            r9 = move-exception
            r7 = r9
            r9 = r8
            r8 = r7
            goto L8d
        L56:
            kotlin.ResultKt.throwOnFailure(r10)
            kotlinx.coroutines.flow.Flow r10 = r8.$this_onCompletion$inlined     // Catch: java.lang.Throwable -> L51
            r0.L$0 = r8     // Catch: java.lang.Throwable -> L51
            r0.L$1 = r9     // Catch: java.lang.Throwable -> L51
            r0.label = r5     // Catch: java.lang.Throwable -> L51
            java.lang.Object r10 = r10.collect(r9, r0)     // Catch: java.lang.Throwable -> L51
            if (r10 != r1) goto L68
            goto La0
        L68:
            kotlinx.coroutines.flow.internal.SafeCollector r10 = new kotlinx.coroutines.flow.internal.SafeCollector
            kotlin.coroutines.CoroutineContext r2 = r0.getContext()
            r10.<init>(r9, r2)
            kotlin.jvm.functions.Function3 r8 = r8.$action$inlined     // Catch: java.lang.Throwable -> L87
            r0.L$0 = r10     // Catch: java.lang.Throwable -> L87
            r0.L$1 = r6     // Catch: java.lang.Throwable -> L87
            r0.label = r3     // Catch: java.lang.Throwable -> L87
            java.lang.Object r8 = r8.invoke(r10, r6, r0)     // Catch: java.lang.Throwable -> L87
            if (r8 != r1) goto L80
            goto La0
        L80:
            r8 = r10
        L81:
            r8.releaseIntercepted()
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        L87:
            r9 = move-exception
            r8 = r10
        L89:
            r8.releaseIntercepted()
            throw r9
        L8d:
            kotlinx.coroutines.flow.ThrowingCollector r10 = new kotlinx.coroutines.flow.ThrowingCollector
            r10.<init>(r8)
            kotlin.jvm.functions.Function3 r9 = r9.$action$inlined
            r0.L$0 = r8
            r0.L$1 = r6
            r0.label = r4
            java.lang.Object r9 = kotlinx.coroutines.flow.FlowKt__EmittersKt.access$invokeSafely$FlowKt__EmittersKt(r10, r9, r8, r0)
            if (r9 != r1) goto La1
        La0:
            return r1
        La1:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
