package com.android.systemui.volume.dialog.ui.binder;

import android.graphics.Insets;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import com.android.systemui.common.ui.view.ViewExtKt$onApplyWindowInsets$1;
import com.android.systemui.util.kotlin.DisposableHandleExtKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.MutableStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialogViewBinder$bind$6 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableStateFlow $insets;
    final /* synthetic */ ViewGroup $root;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogViewBinder$bind$6(ViewGroup viewGroup, MutableStateFlow mutableStateFlow, Continuation continuation) {
        super(2, continuation);
        this.$root = viewGroup;
        this.$insets = mutableStateFlow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new VolumeDialogViewBinder$bind$6(this.$root, this.$insets, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogViewBinder$bind$6) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ViewGroup viewGroup = this.$root;
            final MutableStateFlow mutableStateFlow = this.$insets;
            viewGroup.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.volume.dialog.ui.binder.VolumeDialogViewBinder$bind$6.1
                @Override // android.view.View.OnApplyWindowInsetsListener
                public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                    Insets insets = windowInsets.getInsets(WindowInsets.Type.displayCutout());
                    view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
                    MutableStateFlow.this.setValue(windowInsets);
                    return WindowInsets.CONSUMED;
                }
            });
            ViewExtKt$onApplyWindowInsets$1 viewExtKt$onApplyWindowInsets$1 = new ViewExtKt$onApplyWindowInsets$1(viewGroup);
            this.label = 1;
            if (DisposableHandleExtKt.awaitCancellationThenDispose(viewExtKt$onApplyWindowInsets$1, this) == coroutineSingletons) {
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
