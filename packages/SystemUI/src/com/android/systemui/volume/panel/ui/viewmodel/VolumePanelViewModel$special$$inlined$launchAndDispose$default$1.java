package com.android.systemui.volume.panel.ui.viewmodel;

import com.android.systemui.util.kotlin.DisposableHandleExtKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DisposableHandle;

/* loaded from: classes3.dex */
public final class VolumePanelViewModel$special$$inlined$launchAndDispose$default$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ VolumePanelViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumePanelViewModel$special$$inlined$launchAndDispose$default$1(Continuation continuation, VolumePanelViewModel volumePanelViewModel) {
        super(2, continuation);
        this.this$0 = volumePanelViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new VolumePanelViewModel$special$$inlined$launchAndDispose$default$1(continuation, this.this$0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumePanelViewModel$special$$inlined$launchAndDispose$default$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            VolumePanelViewModel volumePanelViewModel = this.this$0;
            volumePanelViewModel.dumpManager.registerNormalDumpable("VolumePanelViewModel", volumePanelViewModel);
            final VolumePanelViewModel volumePanelViewModel2 = this.this$0;
            DisposableHandle disposableHandle = new DisposableHandle() { // from class: com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel$1$1
                @Override // kotlinx.coroutines.DisposableHandle
                public final void dispose() {
                    volumePanelViewModel2.dumpManager.unregisterDumpable("VolumePanelViewModel");
                }
            };
            this.label = 1;
            if (DisposableHandleExtKt.awaitCancellationThenDispose(disposableHandle, this) == coroutineSingletons) {
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
