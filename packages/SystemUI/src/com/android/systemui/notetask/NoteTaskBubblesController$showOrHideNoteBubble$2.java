package com.android.systemui.notetask;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.UserHandle;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.infra.ServiceConnector;
import com.android.systemui.log.DebugLogger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class NoteTaskBubblesController$showOrHideNoteBubble$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ NoteTaskBubbleExpandBehavior $bubbleExpandBehavior;
    final /* synthetic */ Icon $icon;
    final /* synthetic */ Intent $intent;
    final /* synthetic */ UserHandle $userHandle;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NoteTaskBubblesController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoteTaskBubblesController$showOrHideNoteBubble$2(NoteTaskBubblesController noteTaskBubblesController, Intent intent, UserHandle userHandle, Icon icon, NoteTaskBubbleExpandBehavior noteTaskBubbleExpandBehavior, Continuation continuation) {
        super(2, continuation);
        this.this$0 = noteTaskBubblesController;
        this.$intent = intent;
        this.$userHandle = userHandle;
        this.$icon = icon;
        this.$bubbleExpandBehavior = noteTaskBubbleExpandBehavior;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NoteTaskBubblesController$showOrHideNoteBubble$2 noteTaskBubblesController$showOrHideNoteBubble$2 = new NoteTaskBubblesController$showOrHideNoteBubble$2(this.this$0, this.$intent, this.$userHandle, this.$icon, this.$bubbleExpandBehavior, continuation);
        noteTaskBubblesController$showOrHideNoteBubble$2.L$0 = obj;
        return noteTaskBubblesController$showOrHideNoteBubble$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NoteTaskBubblesController$showOrHideNoteBubble$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        final CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        ServiceConnector.Impl impl = this.this$0.serviceConnector;
        final Intent intent = this.$intent;
        final UserHandle userHandle = this.$userHandle;
        final Icon icon = this.$icon;
        final NoteTaskBubbleExpandBehavior noteTaskBubbleExpandBehavior = this.$bubbleExpandBehavior;
        AndroidFuture post = impl.post(new ServiceConnector.VoidJob() { // from class: com.android.systemui.notetask.NoteTaskBubblesController$showOrHideNoteBubble$2.1
            public final void runNoResult(Object obj2) {
                ((INoteTaskBubblesService) obj2).showOrHideNoteBubble(intent, userHandle, icon, noteTaskBubbleExpandBehavior);
            }
        });
        final Intent intent2 = this.$intent;
        final UserHandle userHandle2 = this.$userHandle;
        final Icon icon2 = this.$icon;
        return post.whenComplete(new NoteTaskBubblesController$sam$java_util_function_BiConsumer$0(new Function2(intent2, userHandle2, icon2) { // from class: com.android.systemui.notetask.NoteTaskBubblesController$showOrHideNoteBubble$2$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj2, Object obj3) {
                Throwable th = (Throwable) obj3;
                CoroutineScope coroutineScope2 = CoroutineScope.this;
                if (th != null) {
                    DebugLogger debugLogger = DebugLogger.INSTANCE;
                    boolean z = Build.IS_DEBUGGABLE;
                    Reflection.getOrCreateKotlinClass(coroutineScope2.getClass()).getSimpleName();
                } else {
                    DebugLogger debugLogger2 = DebugLogger.INSTANCE;
                    boolean z2 = Build.IS_DEBUGGABLE;
                    Reflection.getOrCreateKotlinClass(coroutineScope2.getClass()).getSimpleName();
                }
                return Unit.INSTANCE;
            }
        }));
    }
}
