package androidx.compose.material3.internal;

import androidx.compose.runtime.SnapshotStateKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AnchoredDraggableKt$restartable$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $block;
    final /* synthetic */ Function0 $inputs;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.compose.material3.internal.AnchoredDraggableKt$restartable$2$1, reason: invalid class name */
    final class AnonymousClass1<T> implements FlowCollector {
        public final /* synthetic */ CoroutineScope $$this$coroutineScope;
        public final /* synthetic */ Function2 $block;
        public final /* synthetic */ Ref$ObjectRef $previousDrag;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: androidx.compose.material3.internal.AnchoredDraggableKt$restartable$2$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ CoroutineScope $$this$coroutineScope;
            final /* synthetic */ Function2 $block;
            final /* synthetic */ Object $latestInputs;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(Function2 function2, Object obj, CoroutineScope coroutineScope, Continuation continuation) {
                super(2, continuation);
                this.$block = function2;
                this.$latestInputs = obj;
                this.$$this$coroutineScope = coroutineScope;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$block, this.$latestInputs, this.$$this$coroutineScope, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Function2 function2 = this.$block;
                    Object obj2 = this.$latestInputs;
                    this.label = 1;
                    if (function2.invoke(obj2, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                CoroutineScopeKt.cancel(this.$$this$coroutineScope, new AnchoredDragFinishedSignal());
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Ref$ObjectRef<Job> ref$ObjectRef, CoroutineScope coroutineScope, Function2 function2) {
            this.$previousDrag = ref$ObjectRef;
            this.$$this$coroutineScope = coroutineScope;
            this.$block = function2;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x0039  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
            /*
                r5 = this;
                boolean r0 = r7 instanceof androidx.compose.material3.internal.AnchoredDraggableKt$restartable$2$1$emit$1
                if (r0 == 0) goto L13
                r0 = r7
                androidx.compose.material3.internal.AnchoredDraggableKt$restartable$2$1$emit$1 r0 = (androidx.compose.material3.internal.AnchoredDraggableKt$restartable$2$1$emit$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                androidx.compose.material3.internal.AnchoredDraggableKt$restartable$2$1$emit$1 r0 = new androidx.compose.material3.internal.AnchoredDraggableKt$restartable$2$1$emit$1
                r0.<init>(r5, r7)
            L18:
                java.lang.Object r7 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L39
                if (r2 != r3) goto L31
                java.lang.Object r5 = r0.L$2
                kotlinx.coroutines.Job r5 = (kotlinx.coroutines.Job) r5
                java.lang.Object r6 = r0.L$1
                java.lang.Object r5 = r0.L$0
                androidx.compose.material3.internal.AnchoredDraggableKt$restartable$2$1 r5 = (androidx.compose.material3.internal.AnchoredDraggableKt$restartable$2.AnonymousClass1) r5
                kotlin.ResultKt.throwOnFailure(r7)
                goto L5b
            L31:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r6)
                throw r5
            L39:
                kotlin.ResultKt.throwOnFailure(r7)
                kotlin.jvm.internal.Ref$ObjectRef r7 = r5.$previousDrag
                T r7 = r7.element
                kotlinx.coroutines.Job r7 = (kotlinx.coroutines.Job) r7
                if (r7 == 0) goto L5b
                androidx.compose.material3.internal.AnchoredDragFinishedSignal r2 = new androidx.compose.material3.internal.AnchoredDragFinishedSignal
                r2.<init>()
                r7.cancel(r2)
                r0.L$0 = r5
                r0.L$1 = r6
                r0.L$2 = r7
                r0.label = r3
                java.lang.Object r7 = r7.join(r0)
                if (r7 != r1) goto L5b
                return r1
            L5b:
                kotlin.jvm.internal.Ref$ObjectRef r7 = r5.$previousDrag
                kotlinx.coroutines.CoroutineStart r0 = kotlinx.coroutines.CoroutineStart.UNDISPATCHED
                androidx.compose.material3.internal.AnchoredDraggableKt$restartable$2$1$2 r1 = new androidx.compose.material3.internal.AnchoredDraggableKt$restartable$2$1$2
                kotlin.jvm.functions.Function2 r2 = r5.$block
                kotlinx.coroutines.CoroutineScope r5 = r5.$$this$coroutineScope
                r4 = 0
                r1.<init>(r2, r6, r5, r4)
                kotlinx.coroutines.StandaloneCoroutine r5 = kotlinx.coroutines.BuildersKt.launch$default(r5, r4, r0, r1, r3)
                r7.element = r5
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.internal.AnchoredDraggableKt$restartable$2.AnonymousClass1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnchoredDraggableKt$restartable$2(Function0 function0, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$inputs = function0;
        this.$block = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AnchoredDraggableKt$restartable$2 anchoredDraggableKt$restartable$2 = new AnchoredDraggableKt$restartable$2(this.$inputs, this.$block, continuation);
        anchoredDraggableKt$restartable$2.L$0 = obj;
        return anchoredDraggableKt$restartable$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AnchoredDraggableKt$restartable$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            SafeFlow snapshotFlow = SnapshotStateKt.snapshotFlow(this.$inputs);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(ref$ObjectRef, coroutineScope, this.$block);
            this.label = 1;
            if (snapshotFlow.collect(anonymousClass1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
