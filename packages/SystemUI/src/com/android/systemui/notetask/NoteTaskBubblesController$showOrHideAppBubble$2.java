package com.android.systemui.notetask;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.UserHandle;
import com.android.internal.infra.ServiceConnector;
import com.android.systemui.log.DebugLogger;
import java.util.function.BiConsumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.CoroutineScope;

final class NoteTaskBubblesController$showOrHideAppBubble$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Icon $icon;
    final /* synthetic */ Intent $intent;
    final /* synthetic */ UserHandle $userHandle;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NoteTaskBubblesController this$0;

    public NoteTaskBubblesController$showOrHideAppBubble$2(NoteTaskBubblesController noteTaskBubblesController, Intent intent, UserHandle userHandle, Icon icon, Continuation continuation) {
        super(2, continuation);
        this.this$0 = noteTaskBubblesController;
        this.$intent = intent;
        this.$userHandle = userHandle;
        this.$icon = icon;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NoteTaskBubblesController$showOrHideAppBubble$2 noteTaskBubblesController$showOrHideAppBubble$2 = new NoteTaskBubblesController$showOrHideAppBubble$2(this.this$0, this.$intent, this.$userHandle, this.$icon, continuation);
        noteTaskBubblesController$showOrHideAppBubble$2.L$0 = obj;
        return noteTaskBubblesController$showOrHideAppBubble$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NoteTaskBubblesController$showOrHideAppBubble$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        ServiceConnector.Impl impl = this.this$0.serviceConnector;
        final Intent intent = this.$intent;
        final UserHandle userHandle = this.$userHandle;
        final Icon icon = this.$icon;
        return impl.post(new ServiceConnector.VoidJob() { // from class: com.android.systemui.notetask.NoteTaskBubblesController$showOrHideAppBubble$2.1
            public final void runNoResult(Object obj2) {
                ((INoteTaskBubblesService) obj2).showOrHideAppBubble(intent, userHandle, icon);
            }
        }).whenComplete(new BiConsumer(this.$intent, this.$userHandle, this.$icon) { // from class: com.android.systemui.notetask.NoteTaskBubblesController$showOrHideAppBubble$2.2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj2, Object obj3) {
                if (((Throwable) obj3) != null) {
                    int i = DebugLogger.$r8$clinit;
                    CoroutineScope coroutineScope2 = CoroutineScope.this;
                    boolean z = Build.IS_DEBUGGABLE;
                    Reflection.getOrCreateKotlinClass(coroutineScope2.getClass()).getSimpleName();
                    return;
                }
                int i2 = DebugLogger.$r8$clinit;
                CoroutineScope coroutineScope3 = CoroutineScope.this;
                boolean z2 = Build.IS_DEBUGGABLE;
                Reflection.getOrCreateKotlinClass(coroutineScope3.getClass()).getSimpleName();
            }
        });
    }
}
