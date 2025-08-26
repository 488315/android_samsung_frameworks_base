package androidx.compose.foundation.gestures;

import androidx.compose.foundation.gestures.DragEvent;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.channels.BufferedChannel;

/* loaded from: classes.dex */
final class DragGestureNode$startListeningForEvents$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ DragGestureNode this$0;

    /* renamed from: androidx.compose.foundation.gestures.DragGestureNode$startListeningForEvents$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Ref$ObjectRef<DragEvent> $event;
        /* synthetic */ Object L$0;
        Object L$1;
        int label;
        final /* synthetic */ DragGestureNode this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Ref$ObjectRef<DragEvent> ref$ObjectRef, DragGestureNode dragGestureNode, Continuation continuation) {
            super(2, continuation);
            this.$event = ref$ObjectRef;
            this.this$0 = dragGestureNode;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$event, this.this$0, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((Function1) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:11:0x002d  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x0045 -> B:25:0x0057). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x0051 -> B:24:0x0054). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Throwable {
            Function1 function1;
            DragEvent dragEvent;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                function1 = (Function1) this.L$0;
                dragEvent = this.$event.element;
                if (dragEvent instanceof DragEvent.DragStopped) {
                }
                return Unit.INSTANCE;
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Ref$ObjectRef<DragEvent> ref$ObjectRef = (Ref$ObjectRef) this.L$1;
            function1 = (Function1) this.L$0;
            ResultKt.throwOnFailure(obj);
            T t = (DragEvent) obj;
            ref$ObjectRef.element = t;
            dragEvent = this.$event.element;
            if (!(dragEvent instanceof DragEvent.DragStopped) || (dragEvent instanceof DragEvent.DragCancelled)) {
                return Unit.INSTANCE;
            }
            t = 0;
            DragEvent.DragDelta dragDelta = dragEvent instanceof DragEvent.DragDelta ? (DragEvent.DragDelta) dragEvent : null;
            if (dragDelta != null) {
                function1.mo781invoke(dragDelta);
            }
            ref$ObjectRef = this.$event;
            BufferedChannel bufferedChannel = this.this$0.channel;
            if (bufferedChannel != null) {
                this.L$0 = function1;
                this.L$1 = ref$ObjectRef;
                this.label = 1;
                obj = bufferedChannel.receive(this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
                T t2 = (DragEvent) obj;
            }
            ref$ObjectRef.element = t2;
            dragEvent = this.$event.element;
            if (dragEvent instanceof DragEvent.DragStopped) {
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DragGestureNode$startListeningForEvents$1(DragGestureNode dragGestureNode, Continuation continuation) {
        super(2, continuation);
        this.this$0 = dragGestureNode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DragGestureNode$startListeningForEvents$1 dragGestureNode$startListeningForEvents$1 = new DragGestureNode$startListeningForEvents$1(this.this$0, continuation);
        dragGestureNode$startListeningForEvents$1.L$0 = obj;
        return dragGestureNode$startListeningForEvents$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DragGestureNode$startListeningForEvents$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x00f2, code lost:
    
        if (androidx.compose.foundation.gestures.DragGestureNode.access$processDragCancel(r7, r6) != r0) goto L11;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Path cross not found for [B:44:0x00d1, B:40:0x00bd], limit reached: 56 */
    /* JADX WARN: Path cross not found for [B:46:0x00d5, B:19:0x005c], limit reached: 56 */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0032 A[PHI: r1 r3
      0x0032: PHI (r1v14 kotlin.jvm.internal.Ref$ObjectRef) = (r1v6 kotlin.jvm.internal.Ref$ObjectRef), (r1v19 kotlin.jvm.internal.Ref$ObjectRef) binds: [B:13:0x002f, B:36:0x00b4] A[DONT_GENERATE, DONT_INLINE]
      0x0032: PHI (r3v8 kotlinx.coroutines.CoroutineScope) = (r3v5 kotlinx.coroutines.CoroutineScope), (r3v10 kotlinx.coroutines.CoroutineScope) binds: [B:13:0x002f, B:36:0x00b4] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x005c A[PHI: r4
      0x005c: PHI (r4v7 kotlinx.coroutines.CoroutineScope) = 
      (r4v0 kotlinx.coroutines.CoroutineScope)
      (r4v3 kotlinx.coroutines.CoroutineScope)
      (r4v3 kotlinx.coroutines.CoroutineScope)
      (r4v3 kotlinx.coroutines.CoroutineScope)
      (r4v5 kotlinx.coroutines.CoroutineScope)
      (r4v8 kotlinx.coroutines.CoroutineScope)
     binds: [B:18:0x0054, B:45:0x00d3, B:47:0x00e2, B:41:0x00cc, B:30:0x008a, B:11:0x0025] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00bd A[Catch: CancellationException -> 0x00cf, TryCatch #0 {CancellationException -> 0x00cf, blocks: (B:38:0x00b7, B:40:0x00bd, B:44:0x00d1, B:46:0x00d5), top: B:55:0x00b7 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00d1 A[Catch: CancellationException -> 0x00cf, TryCatch #0 {CancellationException -> 0x00cf, blocks: (B:38:0x00b7, B:40:0x00bd, B:44:0x00d1, B:46:0x00d5), top: B:55:0x00b7 }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00f5  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:30:0x008a -> B:19:0x005c). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:41:0x00cc -> B:19:0x005c). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:45:0x00d3 -> B:19:0x005c). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:47:0x00e2 -> B:19:0x005c). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:50:0x00f2 -> B:11:0x0025). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineScope coroutineScope;
        Ref$ObjectRef ref$ObjectRef;
        Ref$ObjectRef ref$ObjectRef2;
        Ref$ObjectRef ref$ObjectRef3;
        CoroutineScope coroutineScope2;
        CoroutineScope coroutineScope3;
        T t;
        DragGestureNode dragGestureNode;
        AnonymousClass1 anonymousClass1;
        T t2;
        T t3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                coroutineScope = (CoroutineScope) this.L$0;
                if (!CoroutineScopeKt.isActive(coroutineScope)) {
                    ref$ObjectRef = new Ref$ObjectRef();
                    BufferedChannel bufferedChannel = this.this$0.channel;
                    if (bufferedChannel == null) {
                        ref$ObjectRef2 = ref$ObjectRef;
                        t2 = 0;
                        ref$ObjectRef.element = t2;
                        t3 = ref$ObjectRef2.element;
                        if (t3 instanceof DragEvent.DragStarted) {
                        }
                        return coroutineSingletons;
                    }
                    this.L$0 = coroutineScope;
                    this.L$1 = ref$ObjectRef;
                    this.L$2 = ref$ObjectRef;
                    this.label = 1;
                    obj = bufferedChannel.receive(this);
                    if (obj != coroutineSingletons) {
                        ref$ObjectRef2 = ref$ObjectRef;
                        t2 = (DragEvent) obj;
                        ref$ObjectRef.element = t2;
                        t3 = ref$ObjectRef2.element;
                        if (t3 instanceof DragEvent.DragStarted) {
                            this.L$0 = coroutineScope;
                            this.L$1 = ref$ObjectRef2;
                            this.L$2 = null;
                            this.label = 2;
                            if (DragGestureNode.access$processDragStart(this.this$0, (DragEvent.DragStarted) t3, this) != coroutineSingletons) {
                                ref$ObjectRef3 = ref$ObjectRef2;
                                coroutineScope2 = coroutineScope;
                                dragGestureNode = this.this$0;
                                anonymousClass1 = new AnonymousClass1(ref$ObjectRef3, dragGestureNode, null);
                                this.L$0 = coroutineScope2;
                                this.L$1 = ref$ObjectRef3;
                                this.label = 3;
                                if (dragGestureNode.drag(anonymousClass1, this) != coroutineSingletons) {
                                    coroutineScope = coroutineScope2;
                                    try {
                                    } catch (CancellationException unused) {
                                        coroutineScope3 = coroutineScope;
                                        DragGestureNode dragGestureNode2 = this.this$0;
                                        this.L$0 = coroutineScope3;
                                        this.L$1 = null;
                                        this.label = 6;
                                        break;
                                    }
                                    t = ref$ObjectRef3.element;
                                    if (t instanceof DragEvent.DragStopped) {
                                        this.L$0 = coroutineScope;
                                        this.L$1 = null;
                                        this.label = 4;
                                        if (DragGestureNode.access$processDragStop(this.this$0, (DragEvent.DragStopped) t, this) != coroutineSingletons) {
                                            if (!CoroutineScopeKt.isActive(coroutineScope)) {
                                            }
                                        }
                                    } else {
                                        if (t instanceof DragEvent.DragCancelled) {
                                            DragGestureNode dragGestureNode3 = this.this$0;
                                            this.L$0 = coroutineScope;
                                            this.L$1 = null;
                                            this.label = 5;
                                            if (DragGestureNode.access$processDragCancel(dragGestureNode3, this) != coroutineSingletons) {
                                            }
                                        }
                                        if (!CoroutineScopeKt.isActive(coroutineScope)) {
                                            return Unit.INSTANCE;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return coroutineSingletons;
                }
            case 1:
                ref$ObjectRef = (Ref$ObjectRef) this.L$2;
                ref$ObjectRef2 = (Ref$ObjectRef) this.L$1;
                coroutineScope = (CoroutineScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                t2 = (DragEvent) obj;
                ref$ObjectRef.element = t2;
                t3 = ref$ObjectRef2.element;
                if (t3 instanceof DragEvent.DragStarted) {
                }
                return coroutineSingletons;
            case 2:
                ref$ObjectRef3 = (Ref$ObjectRef) this.L$1;
                coroutineScope2 = (CoroutineScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                dragGestureNode = this.this$0;
                anonymousClass1 = new AnonymousClass1(ref$ObjectRef3, dragGestureNode, null);
                this.L$0 = coroutineScope2;
                this.L$1 = ref$ObjectRef3;
                this.label = 3;
                if (dragGestureNode.drag(anonymousClass1, this) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 3:
                ref$ObjectRef3 = (Ref$ObjectRef) this.L$1;
                coroutineScope2 = (CoroutineScope) this.L$0;
                try {
                    ResultKt.throwOnFailure(obj);
                } catch (CancellationException unused2) {
                    coroutineScope3 = coroutineScope2;
                    DragGestureNode dragGestureNode22 = this.this$0;
                    this.L$0 = coroutineScope3;
                    this.L$1 = null;
                    this.label = 6;
                    break;
                }
                coroutineScope = coroutineScope2;
                t = ref$ObjectRef3.element;
                if (t instanceof DragEvent.DragStopped) {
                }
                break;
            case 4:
                coroutineScope3 = (CoroutineScope) this.L$0;
                try {
                    ResultKt.throwOnFailure(obj);
                } catch (CancellationException unused3) {
                    DragGestureNode dragGestureNode222 = this.this$0;
                    this.L$0 = coroutineScope3;
                    this.L$1 = null;
                    this.label = 6;
                    break;
                }
                coroutineScope = coroutineScope3;
                if (!CoroutineScopeKt.isActive(coroutineScope)) {
                }
                break;
            case 5:
                coroutineScope3 = (CoroutineScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                coroutineScope = coroutineScope3;
                if (!CoroutineScopeKt.isActive(coroutineScope)) {
                }
                break;
            case 6:
                coroutineScope3 = (CoroutineScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                coroutineScope = coroutineScope3;
                if (!CoroutineScopeKt.isActive(coroutineScope)) {
                }
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
