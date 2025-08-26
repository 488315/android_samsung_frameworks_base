package androidx.compose.foundation.gestures;

import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerInputScope;
import java.util.List;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.JobKt;

/* loaded from: classes.dex */
public abstract class ForEachGestureKt {

    /* renamed from: androidx.compose.foundation.gestures.ForEachGestureKt$awaitAllPointersUp$3, reason: invalid class name */
    final class AnonymousClass3 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass3(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ForEachGestureKt.awaitAllPointersUp(null, null, this);
        }
    }

    /* renamed from: androidx.compose.foundation.gestures.ForEachGestureKt$awaitEachGesture$2, reason: invalid class name */
    final class AnonymousClass2 extends RestrictedSuspendLambda implements Function2 {
        final /* synthetic */ Function2 $block;
        final /* synthetic */ CoroutineContext $currentContext;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(CoroutineContext coroutineContext, Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$currentContext = coroutineContext;
            this.$block = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$currentContext, this.$block, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:26:0x0059, code lost:
        
            if (r8 != r0) goto L12;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0072, code lost:
        
            if (r8 == r0) goto L34;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:32:0x0068  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x0075  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x0076  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x0041 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r1v0, types: [int] */
        /* JADX WARN: Type inference failed for: r1v1 */
        /* JADX WARN: Type inference failed for: r1v10 */
        /* JADX WARN: Type inference failed for: r1v2, types: [androidx.compose.ui.input.pointer.AwaitPointerEventScope, java.lang.Object] */
        /* JADX WARN: Type inference failed for: r1v22 */
        /* JADX WARN: Type inference failed for: r1v3, types: [androidx.compose.ui.input.pointer.AwaitPointerEventScope, java.lang.Object] */
        /* JADX WARN: Type inference failed for: r1v7 */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x0059 -> B:12:0x0026). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:33:0x0072 -> B:12:0x0026). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            AwaitPointerEventScope awaitPointerEventScope;
            AwaitPointerEventScope awaitPointerEventScope2;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            ?? r1 = this.label;
            try {
            } catch (CancellationException e) {
                e = e;
                if (JobKt.isActive(this.$currentContext)) {
                }
            }
            if (r1 == 0) {
                ResultKt.throwOnFailure(obj);
                awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                if (JobKt.isActive(this.$currentContext)) {
                }
            } else {
                if (r1 != 1) {
                    if (r1 == 2) {
                        AwaitPointerEventScope awaitPointerEventScope3 = (AwaitPointerEventScope) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        awaitPointerEventScope2 = awaitPointerEventScope3;
                    } else {
                        if (r1 != 3) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        AwaitPointerEventScope awaitPointerEventScope4 = (AwaitPointerEventScope) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        awaitPointerEventScope2 = awaitPointerEventScope4;
                    }
                    awaitPointerEventScope = awaitPointerEventScope2;
                    if (JobKt.isActive(this.$currentContext)) {
                        return Unit.INSTANCE;
                    }
                    try {
                    } catch (CancellationException e2) {
                        r1 = awaitPointerEventScope;
                        e = e2;
                        if (JobKt.isActive(this.$currentContext)) {
                            throw e;
                        }
                        this.L$0 = r1;
                        this.label = 3;
                        Object objAwaitAllPointersUp = ForEachGestureKt.awaitAllPointersUp(r1, PointerEventPass.Final, this);
                        awaitPointerEventScope2 = r1;
                    }
                    Function2 function2 = this.$block;
                    this.L$0 = awaitPointerEventScope;
                    this.label = 1;
                    if (function2.invoke(awaitPointerEventScope, this) != coroutineSingletons) {
                        r1 = awaitPointerEventScope;
                        this.L$0 = r1;
                        this.label = 2;
                        Object objAwaitAllPointersUp2 = ForEachGestureKt.awaitAllPointersUp(r1, PointerEventPass.Final, this);
                        awaitPointerEventScope2 = r1;
                    }
                    return coroutineSingletons;
                }
                AwaitPointerEventScope awaitPointerEventScope5 = (AwaitPointerEventScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                r1 = awaitPointerEventScope5;
                this.L$0 = r1;
                this.label = 2;
                Object objAwaitAllPointersUp22 = ForEachGestureKt.awaitAllPointersUp(r1, PointerEventPass.Final, this);
                awaitPointerEventScope2 = r1;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0064 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x0062 -> B:21:0x0065). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object awaitAllPointersUp(AwaitPointerEventScope awaitPointerEventScope, PointerEventPass pointerEventPass, BaseContinuationImpl baseContinuationImpl) {
        AnonymousClass3 anonymousClass3;
        int size;
        int i;
        if (baseContinuationImpl instanceof AnonymousClass3) {
            anonymousClass3 = (AnonymousClass3) baseContinuationImpl;
            int i2 = anonymousClass3.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                anonymousClass3.label = i2 - Integer.MIN_VALUE;
            } else {
                anonymousClass3 = new AnonymousClass3(baseContinuationImpl);
            }
        }
        Object objAwaitPointerEvent = anonymousClass3.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = anonymousClass3.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(objAwaitPointerEvent);
            List list = awaitPointerEventScope.getCurrentEvent().changes;
            int size2 = list.size();
            for (int i4 = 0; i4 < size2; i4++) {
                if (((PointerInputChange) list.get(i4)).pressed) {
                    anonymousClass3.L$0 = awaitPointerEventScope;
                    anonymousClass3.L$1 = pointerEventPass;
                    anonymousClass3.label = 1;
                    objAwaitPointerEvent = awaitPointerEventScope.awaitPointerEvent(pointerEventPass, anonymousClass3);
                    if (objAwaitPointerEvent == coroutineSingletons) {
                    }
                    List list2 = ((PointerEvent) objAwaitPointerEvent).changes;
                    size = list2.size();
                    i = 0;
                    while (i < size) {
                    }
                    return Unit.INSTANCE;
                }
            }
            return Unit.INSTANCE;
        }
        if (i3 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        PointerEventPass pointerEventPass2 = (PointerEventPass) anonymousClass3.L$1;
        AwaitPointerEventScope awaitPointerEventScope2 = (AwaitPointerEventScope) anonymousClass3.L$0;
        ResultKt.throwOnFailure(objAwaitPointerEvent);
        pointerEventPass = pointerEventPass2;
        awaitPointerEventScope = awaitPointerEventScope2;
        List list22 = ((PointerEvent) objAwaitPointerEvent).changes;
        size = list22.size();
        i = 0;
        while (i < size) {
            if (((PointerInputChange) list22.get(i)).pressed) {
                anonymousClass3.L$0 = awaitPointerEventScope;
                anonymousClass3.L$1 = pointerEventPass;
                anonymousClass3.label = 1;
                objAwaitPointerEvent = awaitPointerEventScope.awaitPointerEvent(pointerEventPass, anonymousClass3);
                if (objAwaitPointerEvent == coroutineSingletons) {
                    return coroutineSingletons;
                }
                List list222 = ((PointerEvent) objAwaitPointerEvent).changes;
                size = list222.size();
                i = 0;
                while (i < size) {
                }
            } else {
                i++;
            }
        }
        return Unit.INSTANCE;
    }

    public static final Object awaitEachGesture(PointerInputScope pointerInputScope, Function2 function2, Continuation continuation) {
        Object objAwaitPointerEventScope = pointerInputScope.awaitPointerEventScope(new AnonymousClass2(continuation.getContext(), function2, null), continuation);
        return objAwaitPointerEventScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objAwaitPointerEventScope : Unit.INSTANCE;
    }
}
