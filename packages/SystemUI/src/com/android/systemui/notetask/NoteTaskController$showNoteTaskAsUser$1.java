package com.android.systemui.notetask;

import android.os.UserHandle;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class NoteTaskController$showNoteTaskAsUser$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ NoteTaskEntryPoint $entryPoint;
    final /* synthetic */ UserHandle $user;
    int label;
    final /* synthetic */ NoteTaskController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoteTaskController$showNoteTaskAsUser$1(NoteTaskController noteTaskController, NoteTaskEntryPoint noteTaskEntryPoint, UserHandle userHandle, Continuation continuation) {
        super(2, continuation);
        this.this$0 = noteTaskController;
        this.$entryPoint = noteTaskEntryPoint;
        this.$user = userHandle;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new NoteTaskController$showNoteTaskAsUser$1(this.this$0, this.$entryPoint, this.$user, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NoteTaskController$showNoteTaskAsUser$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            NoteTaskController noteTaskController = this.this$0;
            NoteTaskEntryPoint noteTaskEntryPoint = this.$entryPoint;
            UserHandle userHandle = this.$user;
            this.label = 1;
            if (NoteTaskController.access$awaitShowNoteTaskAsUser(noteTaskController, noteTaskEntryPoint, userHandle, this) == coroutineSingletons) {
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
