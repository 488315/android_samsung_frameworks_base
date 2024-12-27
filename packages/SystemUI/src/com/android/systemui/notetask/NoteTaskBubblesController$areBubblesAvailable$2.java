package com.android.systemui.notetask;

import android.os.Build;
import com.android.internal.infra.ServiceConnector;
import com.android.systemui.log.DebugLogger;
import java.util.function.BiConsumer;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class NoteTaskBubblesController$areBubblesAvailable$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ NoteTaskBubblesController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoteTaskBubblesController$areBubblesAvailable$2(NoteTaskBubblesController noteTaskBubblesController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = noteTaskBubblesController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NoteTaskBubblesController$areBubblesAvailable$2 noteTaskBubblesController$areBubblesAvailable$2 = new NoteTaskBubblesController$areBubblesAvailable$2(this.this$0, continuation);
        noteTaskBubblesController$areBubblesAvailable$2.L$0 = obj;
        return noteTaskBubblesController$areBubblesAvailable$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NoteTaskBubblesController$areBubblesAvailable$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            NoteTaskBubblesController noteTaskBubblesController = this.this$0;
            this.L$0 = coroutineScope;
            this.L$1 = noteTaskBubblesController;
            this.label = 1;
            final SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this));
            noteTaskBubblesController.serviceConnector.postForResult(new ServiceConnector.Job() { // from class: com.android.systemui.notetask.NoteTaskBubblesController$areBubblesAvailable$2$1$1
                public final Object run(Object obj2) {
                    return Boolean.valueOf(((INoteTaskBubblesService) obj2).areBubblesAvailable());
                }
            }).whenComplete(new BiConsumer() { // from class: com.android.systemui.notetask.NoteTaskBubblesController$areBubblesAvailable$2$1$2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj2, Object obj3) {
                    Boolean bool = (Boolean) obj2;
                    if (((Throwable) obj3) != null) {
                        int i2 = DebugLogger.$r8$clinit;
                        CoroutineScope coroutineScope2 = CoroutineScope.this;
                        boolean z = Build.IS_DEBUGGABLE;
                        Reflection.getOrCreateKotlinClass(coroutineScope2.getClass()).getSimpleName();
                    }
                    Continuation continuation = safeContinuation;
                    int i3 = Result.$r8$clinit;
                    continuation.resumeWith(Boolean.valueOf(bool == null ? false : bool.booleanValue()));
                }
            });
            obj = safeContinuation.getOrThrow();
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return obj;
    }
}
