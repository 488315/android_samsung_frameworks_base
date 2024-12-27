package com.android.systemui.keyboard.shortcut.ui.view;

import androidx.lifecycle.FlowExtKt$flowWithLifecycle$1;
import androidx.lifecycle.Lifecycle;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.CallbackFlowBuilder;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

final class ShortcutHelperActivity$observeFinishRequired$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ShortcutHelperActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutHelperActivity$observeFinishRequired$1(ShortcutHelperActivity shortcutHelperActivity, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shortcutHelperActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShortcutHelperActivity$observeFinishRequired$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShortcutHelperActivity$observeFinishRequired$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CallbackFlowBuilder callbackFlow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ShortcutHelperActivity shortcutHelperActivity = this.this$0;
            callbackFlow = FlowKt.callbackFlow(new FlowExtKt$flowWithLifecycle$1(shortcutHelperActivity.mLifecycleRegistry, Lifecycle.State.STARTED, shortcutHelperActivity.viewModel.shouldShow, null));
            final ShortcutHelperActivity shortcutHelperActivity2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyboard.shortcut.ui.view.ShortcutHelperActivity$observeFinishRequired$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    if (!((Boolean) obj2).booleanValue()) {
                        ShortcutHelperActivity.this.finish();
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (callbackFlow.collect(flowCollector, this) == coroutineSingletons) {
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
