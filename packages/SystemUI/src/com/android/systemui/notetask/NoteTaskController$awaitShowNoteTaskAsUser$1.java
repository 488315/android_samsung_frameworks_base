package com.android.systemui.notetask;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class NoteTaskController$awaitShowNoteTaskAsUser$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ NoteTaskController this$0;

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
