package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class MediaCardKt$DeviceListItem$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ AudioDevice $device;
    final /* synthetic */ MutableFloatState $volume$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaCardKt$DeviceListItem$1(AudioDevice audioDevice, MutableFloatState mutableFloatState, Continuation continuation) {
        super(2, continuation);
        this.$device = audioDevice;
        this.$volume$delegate = mutableFloatState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaCardKt$DeviceListItem$1(this.$device, this.$volume$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaCardKt$DeviceListItem$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ((SnapshotMutableFloatStateImpl) this.$volume$delegate).setFloatValue(this.$device.getVolume());
        return Unit.INSTANCE;
    }
}
