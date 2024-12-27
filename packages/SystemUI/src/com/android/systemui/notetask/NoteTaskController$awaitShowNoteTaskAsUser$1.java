package com.android.systemui.notetask;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class NoteTaskController$awaitShowNoteTaskAsUser$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ NoteTaskController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoteTaskController$awaitShowNoteTaskAsUser$1(NoteTaskController noteTaskController, Continuation continuation) {
        super(continuation);
        this.this$0 = noteTaskController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return NoteTaskController.access$awaitShowNoteTaskAsUser(this.this$0, null, null, this);
    }
}
