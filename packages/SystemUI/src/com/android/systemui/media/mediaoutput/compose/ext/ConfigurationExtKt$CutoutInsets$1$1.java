package com.android.systemui.media.mediaoutput.compose.ext;

import android.view.DisplayCutout;
import android.view.WindowInsets;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class ConfigurationExtKt$CutoutInsets$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $calculation;
    final /* synthetic */ MutableState<Integer> $insets;
    final /* synthetic */ WindowInsets $rootWindowInsets;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConfigurationExtKt$CutoutInsets$1$1(WindowInsets windowInsets, MutableState<Integer> mutableState, Function1 function1, Continuation continuation) {
        super(2, continuation);
        this.$rootWindowInsets = windowInsets;
        this.$insets = mutableState;
        this.$calculation = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ConfigurationExtKt$CutoutInsets$1$1(this.$rootWindowInsets, this.$insets, this.$calculation, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ConfigurationExtKt$CutoutInsets$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final WindowInsets windowInsets = this.$rootWindowInsets;
            FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(SnapshotStateKt.snapshotFlow(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.ext.ConfigurationExtKt$CutoutInsets$1$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return windowInsets.getDisplayCutout();
                }
            }));
            final MutableState<Integer> mutableState = this.$insets;
            final Function1 function1 = this.$calculation;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.compose.ext.ConfigurationExtKt$CutoutInsets$1$1.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    mutableState.setValue(function1.mo779invoke((DisplayCutout) obj2));
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(flowCollector, this) == coroutineSingletons) {
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
